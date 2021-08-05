/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.apps.finstudio.finreport;

import com.finlogic.util.Logger;
import com.finlogic.util.WebServiceConsumerParser;
import com.finlogic.util.WsdlParser;
import com.finlogic.util.finreport.DirectConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 *
 * @author njuser
 */
public class FinReportController extends MultiActionController
{

    private final FinReportService service = new FinReportService();
    private final DirectConnection conn = new DirectConnection();

    public ModelAndView defaultMethod(final HttpServletRequest request, final HttpServletResponse response)
    {
        return setFinReport(request, response);
    }

    public ModelAndView setFinReport(final HttpServletRequest request, final HttpServletResponse response)
    {
        ModelAndView mav;
        mav = new ModelAndView();
        try
        {
            String finlibPath = finpack.FinPack.getProperty("finlib_path");
            SqlRowSet srs = service.getProjectList();
            List<String> values = new ArrayList<String>();
            List<String> projects = new ArrayList<String>();
            String temp;
            while (srs.next())
            {
                values.add(srs.getString(3));
                projects.add(srs.getString(2));
            }
            int projectSize = projects.size();
            int idx;
            for (int j = 0; j < projectSize; j++)
            {
                idx = j;
                for (int k = j; k < projectSize; k++)
                {
                    if (projects.get(k).compareTo(projects.get(idx)) < 0)
                    {
                        idx = k;
                    }

                }
                if (idx != j)
                {
                    temp = values.get(j);
                    values.set(j, values.get(idx));
                    values.set(idx, temp);

                    temp = projects.get(j);
                    projects.set(j, projects.get(idx));
                    projects.set(idx, temp);
                }
            }
            mav.addObject("values", values);
            mav.addObject("projectnm", projects);

            String[] aliasList = service.getAliasList();
            mav.addObject("aliaslist", aliasList);
            mav.addObject("finlibPath", finlibPath);
            mav.addObject("process", "main");
            mav.setViewName("finreport/main");
        }
        catch (Exception e)
        {
            mav.addObject("error", Logger.ErrorLogger(e) + e.getMessage());
            mav.setViewName("error");
        }
        return mav;
    }

    public ModelAndView setFinish(final HttpServletRequest request, final HttpServletResponse response, final FinReportFormBean formBean)
    {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("finreport/ProjectModuleSpecification");
        try
        {
            HttpSession session = request.getSession(false);
            if (session.getAttribute("ACLEmpCode") == null)
            {
                formBean.setEmp_code("");
            }
            else
            {
                formBean.setEmp_code(session.getAttribute("ACLEmpCode").toString());
            }

            String tomcatPath = finpack.FinPack.getProperty("tomcat1_path");
            int srNo = service.insertData(formBean);
            formBean.setSRNo(srNo);
            String srsFile = "";
            String codeFile = "";
            try
            {
                srsFile = service.generateSRSFiles(formBean);
                mav.addObject("FileName", formBean.getSRNo() + "RGV2.xls");

                codeFile = service.generateReportFiles(formBean);
                //Zip Folder
                service.zipFolders(codeFile, tomcatPath, formBean.getSRNo());
                service.deleteFolder(codeFile);

                mav.addObject("process", "finish");
                mav.addObject("SRNo", formBean.getSRNo());
                mav.addObject("codeFileName", formBean.getSRNo() + "RGV2.zip");
            }
            catch (Exception e)
            {
                if (srsFile.length() == 0)
                {
                    mav.addObject("error", "Error In SRS Generation :" + Logger.ErrorLogger(e) + e.getMessage());
                }
                else if (codeFile.length() == 0)
                {
                    mav.addObject("error", "Error In Code Generation :" + Logger.ErrorLogger(e) + e.getMessage());
                }
                service.deleteFolder(codeFile);
                mav.addObject("error", Logger.ErrorLogger(e) + e.getMessage());
                mav.setViewName("error");
            }

        }
        catch (Exception e)
        {
            mav.addObject("error", Logger.ErrorLogger(e) + e.getMessage());
            mav.setViewName("error");
        }
        return mav;
    }

    public ModelAndView checkQuery(final HttpServletRequest request, final HttpServletResponse response, final FinReportFormBean formBean) throws SQLException
    {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("finreport/ProjectModuleSpecification");
        Connection con = null;
        try
        {
            String str;
            if ("usingAlias".equals(formBean.getRdoConType()))
            {
                str = service.validateQuery(formBean.getCmbAliasName()[Integer.parseInt(formBean.getQueryIndex())], formBean.getTxtQuery()[Integer.parseInt(formBean.getQueryIndex())], formBean.getRdoConType(), null);
            }
            else
            {
                //direct connection                
                con = conn.getConnection(formBean.getCmbDevServer());
                str = service.validateQuery(formBean.getCmbAliasName()[Integer.parseInt(formBean.getQueryIndex())], formBean.getTxtQuery()[Integer.parseInt(formBean.getQueryIndex())], formBean.getRdoConType(), con);
            }
            mav.addObject("process", "check");
            if (str.contains("Invalid Query"))
            {
                mav.addObject("qStatus", "Invalid Alias or Query " + str);
            }
            else
            {
                mav.addObject("qStatus", "");
            }
        }
        catch (Exception e)
        {
            mav.addObject("error", "Exception:" + Logger.ErrorLogger(e) + e.getMessage());
            mav.setViewName("error");
        }
        finally
        {
            if (con != null)
            {
                con.close();
            }
        }
        return mav;
    }

    public ModelAndView getColumnDetail(final HttpServletRequest request, final HttpServletResponse response, final FinReportFormBean formBean) throws SQLException
    {
        ModelAndView mav = new ModelAndView();
        Connection con = null;
        try
        {
            String columnDetail;
            Map<String, StringBuilder> colDeatilsMap;
            StringBuilder colName = new StringBuilder();
            StringBuilder colType = new StringBuilder();
            int txtQueryLen = formBean.getTxtQuery().length;
            for (int i = 0; i < txtQueryLen; i++)
            {
                if ("usingAlias".equals(formBean.getRdoConType()))
                {
                    colDeatilsMap = service.getColumnDetail(formBean.getCmbAliasName()[i], formBean.getTxtQuery()[i], formBean.getRdoConType(), null);
                }
                else
                {
                    //direct connection
                    con = conn.getConnection(formBean.getCmbDevServer());
                    colDeatilsMap = service.getColumnDetail(formBean.getCmbAliasName()[i], formBean.getTxtQuery()[i], formBean.getRdoConType(), con);
                }
                colName.append(colDeatilsMap.get("colNames"));
                colType.append(colDeatilsMap.get("colTypes"));
            }
            colName.deleteCharAt(colName.length() - 1);
            colType.deleteCharAt(colType.length() - 1);

            columnDetail = "{colNames:[" + colName.toString() + "],colTypes:[" + colType.toString() + "]}";
            mav.addObject("process", "group");
            mav.addObject("columnDetail", columnDetail);
            mav.setViewName("finreport/ProjectModuleSpecification");
        }
        catch (Exception e)
        {
            mav.addObject("error", "Invalid Query");
            mav.setViewName("error");
        }
        finally
        {
            if (con != null)
            {
                con.close();
            }
        }
        return mav;
    }

    public ModelAndView getColumnNames(final HttpServletRequest request, final HttpServletResponse response, final FinReportFormBean formBean) throws SQLException
    {
        ModelAndView mav = new ModelAndView();
        Connection con = null;
        try
        {
            List colNames;
            if ("usingAlias".equals(formBean.getRdoConType()))
            {
                colNames = service.getColumnNames(formBean.getCmbAliasName()[Integer.parseInt(formBean.getQueryIndex())], formBean.getTxtQuery()[Integer.parseInt(formBean.getQueryIndex())], formBean.getRdoConType(), null);
            }
            else
            {
                //direct connection
                con = conn.getConnection(formBean.getCmbDevServer());
                colNames = service.getColumnNames(formBean.getCmbAliasName()[Integer.parseInt(formBean.getQueryIndex())], formBean.getTxtQuery()[Integer.parseInt(formBean.getQueryIndex())], formBean.getRdoConType(), con);
            }
            mav.addObject("process", "columnname");
            mav.addObject("columnNames", colNames);
            mav.setViewName("finreport/ProjectModuleSpecification");
        }
        catch (Exception e)
        {
            mav.addObject("error", "Invalid Query");
            mav.setViewName("error");
        }
        finally
        {
            if (con != null)
            {
                con.close();
            }
        }
        return mav;
    }

    public ModelAndView comboFillCheckQuery(final HttpServletRequest request, final HttpServletResponse response, final FinReportFormBean formBean) throws SQLException
    {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("finreport/ProjectModuleSpecification");
        Connection con = null;
        try
        {
            String str;
            if ("usingAlias".equals(formBean.getRdoConType()))
            {
                str = service.validateQuery(formBean.getCmbAliasName()[0], formBean.getFltrTxtSrcQuery(), formBean.getRdoConType(), null);
            }
            else
            {
                //direct connection                
                con = conn.getConnection(formBean.getCmbDevServer());
                str = service.validateQuery(formBean.getCmbAliasName()[0], formBean.getFltrTxtSrcQuery(), formBean.getRdoConType(), con);
            }
            mav.addObject("process", "check");
            if (str.contains("Invalid Query"))
            {
                mav.addObject("qStatus", "Invalid Alias or Query " + str);
            }
            else if (str.contains("column length"))
            {
                mav.addObject("qStatus", "Query should contains only two columns");
            }
            else
            {
                mav.addObject("qStatus", "");
            }
        }
        catch (Exception e)
        {
            mav.addObject("error", "Exception:" + Logger.ErrorLogger(e) + e.getMessage());
            mav.setViewName("error");
        }
        finally
        {
            if (con != null)
            {
                con.close();
            }
        }
        return mav;
    }

    public ModelAndView getRefNumber(final HttpServletRequest request, final HttpServletResponse response, final FinReportFormBean formBean) throws SQLException, ClassNotFoundException
    {
        ModelAndView mav = new ModelAndView();
        try
        {
            List refNoList = service.getRefNumber(formBean.getCmbProjectName());
            mav.addObject("process", "referenceNo");
            mav.addObject("refNo", refNoList);
            mav.setViewName("finreport/ProjectModuleSpecification");
        }
        catch (Exception e)
        {
            mav.addObject("error", Logger.ErrorLogger(e) + e.getMessage());
            mav.setViewName("error");
        }
        return mav;
    }

    public ModelAndView getWsDetails(final HttpServletRequest request, final HttpServletResponse response, final FinReportFormBean formBean)
    {
        ModelAndView mav = new ModelAndView();
        try
        {
            String wsUrl = formBean.getFltrTxtWsdlUrl();
            String xmlFile = finpack.FinPack.getProperty("tomcat1_path") + "/webapps/finstudio/generated/RptWsdlUrlResponse" + System.currentTimeMillis() + ".xml";
            WsdlParser browser = new WsdlParser(wsUrl, xmlFile);
            Map detail = browser.getDetail();
            if (detail != null)
            {
                String[] pkg = detail.get("package").toString().split("\\.");
                String data = "";
                if (pkg.length >= 4)
                {
                    data = "<input type=\"hidden\" id=\"fltrTxtWsProject\" name=\"fltrTxtWsProject\" readonly value=\"" + pkg[pkg.length - 4] + "\" />";
                }
                data += "<input type=\"hidden\" id=\"fltrTxtWsIntrface\" name=\"fltrTxtWsIntrface\" readonly value=\"" + detail.get("interface") + "\" />";
                mav.addObject("wsdlData", data);
                mav.addObject("process", "wsdlParse");
            }
            mav.setViewName("finreport/ProjectModuleSpecification");
        }
        catch (Exception e)
        {
            mav.addObject("error", Logger.ErrorLogger(e) + e.getMessage());
            mav.setViewName("error");
        }
        return mav;
    }

    public ModelAndView getWsMethods(final HttpServletRequest request, final HttpServletResponse response, final FinReportFormBean formBean)
    {
        ModelAndView mav = new ModelAndView();
        try
        {
            String wsProject = formBean.getFltrTxtWsProject();
            String wsIntrface = formBean.getFltrTxtWsIntrface();
            StringBuilder data = new StringBuilder();
            data.append("<input type=\"hidden\" id=\"fltrTxtWsProject\" name=\"fltrTxtWsProject\" readonly value=\"");
            data.append(wsProject);
            data.append("\">");
            data.append("<input type=\"hidden\" id=\"fltrTxtWsIntrface\" name=\"fltrTxtWsIntrface\" readonly value=\"");
            data.append(wsIntrface);
            data.append("\">");

            WebServiceConsumerParser parser = new WebServiceConsumerParser(wsIntrface, wsProject);
            List methods = parser.parseForMethods();
            if (methods != null)
            {
                data.append("<select id=\"fltrTmpCmbWsMethod\" name=\"fltrTmpCmbWsMethod\">");
                for (int i = 0; i < methods.size(); i++)
                {
                    data.append("<option value=\"");
                    data.append(methods.get(i));
                    data.append("\">");
                    data.append(methods.get(i));
                    data.append("</option>");
                }
                data.append("</select>");
            }
            mav.addObject("wsdlData", data.toString());
            mav.addObject("process", "wsdlParse");
            mav.setViewName("finreport/ProjectModuleSpecification");
        }
        catch (Exception e)
        {
            mav.addObject("error", Logger.ErrorLogger(e) + e.getMessage());
            mav.setViewName("error");
        }
        return mav;
    }

    public ModelAndView getWsMethodParams(final HttpServletRequest request, final HttpServletResponse response, final FinReportFormBean formBean)
    {
        ModelAndView mav = new ModelAndView();
        try
        {
            String wsProject = formBean.getFltrTxtWsProject();
            String wsIntrface = formBean.getFltrTxtWsIntrface();
            String wsMethod = formBean.getFltrCmbWsMethod();

            String data = "<input type=\"hidden\" id=\"fltrTxtWsProject\" name=\"fltrTxtWsProject\" readonly value=\"" + wsProject + "\" />";
            data += "<input type=\"hidden\" id=\"fltrTxtWsIntrface\" name=\"fltrTxtWsIntrface\" readonly value=\"" + wsIntrface + "\" />";

            WebServiceConsumerParser parser = new WebServiceConsumerParser(wsIntrface, wsProject);
            Map params = parser.parseForParams(wsMethod);
            if (params != null)
            {
                data += "<input type=\"hidden\" id=\"fltrTxtWsRetType\" name=\"fltrTxtWsRetType\" readonly value=\"" + params.get("returnType") + "\" />";
                data += "<input type=\"hidden\" id=\"fltrTxtWsParams\" name=\"fltrTxtWsParams\" readonly value=\"" + params.get("parameters") + "\" />";
                data += "<input type=\"hidden\" id=\"fltrTxtWsExps\" name=\"fltrTxtWsExps\" readonly value=\"" + params.get("exceptions") + "\" />";
            }
            mav.addObject("wsdlData", data);
            mav.addObject("process", "wsdlParse");
            mav.setViewName("finreport/ProjectModuleSpecification");
        }
        catch (Exception e)
        {
            mav.addObject("error", Logger.ErrorLogger(e) + e.getMessage());
            mav.setViewName("error");
        }
        return mav;
    }
}
