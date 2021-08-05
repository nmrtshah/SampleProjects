/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.apps.finstudio.finmaster;

import com.finlogic.util.DirectoryService;
import com.finlogic.util.FolderZipper;
import com.finlogic.util.Logger;
import com.finlogic.util.WebServiceConsumerParser;
import com.finlogic.util.WsdlParser;
import com.finlogic.util.finmaster.MasterGenerator;
import com.finlogic.util.finmaster.MasterSRSGeneration;
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
 * @author Sonam Patel
 */
public class FinMasterController extends MultiActionController
{

    private final FinMasterService service = new FinMasterService();

    public ModelAndView defaultMethod(final HttpServletRequest request, final HttpServletResponse response)
    {
        return setFinMaster(request, response);
    }

    public ModelAndView setFinMaster(final HttpServletRequest request, final HttpServletResponse response)
    {
        ModelAndView mav;
        mav = new ModelAndView();
        try
        {
            mav.addObject("process", "main");
            mav.setViewName("finmaster/Main");
            String finlib_path;
            finlib_path = finpack.FinPack.getProperty("finlib_path");
            mav.addObject("finlib_path", finlib_path);
            SqlRowSet srs;
            srs = service.getProjectArray();
            List<String> values;
            values = new ArrayList<String>();
            List<String> projects;
            projects = new ArrayList<String>();
            while (srs.next())
            {
                values.add(srs.getString(3));
                projects.add(srs.getString(2));
            }
            mav.addObject("projects", projects);
            mav.addObject("aliases", service.getAliasArray());
            mav.addObject("values", values);
        }
        catch (Exception e)
        {
            mav.addObject("error", Logger.ErrorLogger(e) + e.getMessage());
            mav.setViewName("error");
        }
        return mav;
    }

    public ModelAndView getDataBaseType(final HttpServletRequest request, final HttpServletResponse response, final FinMasterFormBean formBean)
    {
        ModelAndView mav;
        mav = new ModelAndView();
        try
        {
            String database;
            database = service.getDataBaseType(formBean);
            mav.addObject("process", "databaseType");
            mav.addObject("database", database);
            mav.setViewName("finmaster/ProjectMasterSpecification");
        }
        catch (Exception e)
        {
            mav.addObject("error", Logger.ErrorLogger(e) + e.getMessage());
            mav.setViewName("error");
        }
        return mav;
    }

    public ModelAndView getMstTableNames(final HttpServletRequest request, final HttpServletResponse response, final FinMasterFormBean formBean)
    {
        ModelAndView mav;
        mav = new ModelAndView();
        try
        {
            List tableNames;
            tableNames = service.getTableNames(formBean);
            mav.addObject("process", "tables");
            mav.addObject("tableNames", tableNames);
            mav.setViewName("finmaster/ProjectMasterSpecification");
        }
        catch (Exception e)
        {
            mav.addObject("error", Logger.ErrorLogger(e) + e.getMessage());
            mav.setViewName("error");
        }
        return mav;
    }

    public ModelAndView getMstTableColumnsNames(final HttpServletRequest request, final HttpServletResponse response, final FinMasterFormBean formBean)
    {
        ModelAndView mav;
        mav = new ModelAndView();
        try
        {
            mav.addObject("process", "msttablecolumns");
            mav.setViewName("finmaster/ProjectMasterSpecification");
            List mstColumnNames;
            mstColumnNames = service.getTableColumnsNames(formBean);
            mav.addObject("mstColumnNames", mstColumnNames);
            mav.addObject("primeKey", service.getPrimaryColumn(formBean));
        }
        catch (Exception e)
        {
            mav.addObject("error", Logger.ErrorLogger(e) + e.getMessage());
            mav.setViewName("error");
        }
        return mav;
    }

    public ModelAndView getMstTableColumnWidth(final HttpServletRequest request, final HttpServletResponse response, final FinMasterFormBean formBean)
    {
        ModelAndView mav;
        mav = new ModelAndView();
        try
        {
            mav.addObject("process", "msttablecolumnwidth");
            mav.setViewName("finmaster/ProjectMasterSpecification");
            mav.addObject("mstColumnWidth", service.getMstTableColumnWidth(formBean));
        }
        catch (Exception e)
        {
            mav.addObject("error", Logger.ErrorLogger(e) + e.getMessage());
            mav.setViewName("error");
        }
        return mav;
    }

    public ModelAndView checkSequence(final HttpServletRequest request, final HttpServletResponse response, final FinMasterFormBean formBean)
    {
        ModelAndView mav;
        mav = new ModelAndView();
        try
        {
            String str;
            str = service.checkSequence(formBean);
            mav.addObject("process", "chkSequence");
            mav.addObject("seqStatus", str);
            mav.setViewName("finmaster/ProjectMasterSpecification");
        }
        catch (Exception e)
        {
            mav.addObject("error", Logger.ErrorLogger(e) + e.getMessage());
            mav.setViewName("error");
        }
        return mav;
    }

    public ModelAndView getColumnTypes(final HttpServletRequest request, final HttpServletResponse response, final FinMasterFormBean formBean)
    {
        ModelAndView mav;
        mav = new ModelAndView();
        try
        {
            mav.addObject("process", "mstColumnTypes");
            mav.setViewName("finmaster/ProjectMasterSpecification");
            String mstColumnTypes;
            mstColumnTypes = service.getColumnsTypes(formBean);
            mav.addObject("mstColumnTypes", mstColumnTypes);
        }
        catch (Exception e)
        {
            mav.addObject("error", Logger.ErrorLogger(e) + e.getMessage());
            mav.setViewName("error");
        }
        return mav;
    }

    public ModelAndView checkQuery(final HttpServletRequest request, final HttpServletResponse response, final FinMasterFormBean formBean)
    {
        ModelAndView mav;
        mav = new ModelAndView();
        try
        {
            String str;
            str = service.checkQuery(formBean, request.getParameter("qTab"));
            mav.addObject("process", "chkQuery");
            mav.addObject("qStatus", str);
            mav.setViewName("finmaster/ProjectMasterSpecification");
        }
        catch (Exception e)
        {
            mav.addObject("error", Logger.ErrorLogger(e) + e.getMessage());
            mav.setViewName("error");
        }
        return mav;
    }

    public ModelAndView getWsDetails(final HttpServletRequest request, final HttpServletResponse response, final FinMasterFormBean formBean)
    {
        ModelAndView mav;
        mav = new ModelAndView();
        try
        {
            String tab = request.getParameter("ForTab");
            String wsUrl = "";
            if ("Add".equals(tab))
            {
                wsUrl = formBean.getTxtAddWsdlUrl();
            }
            else if ("Edit".equals(tab))
            {
                wsUrl = formBean.getTxtEditWsdlUrl();
            }
            else if ("Delete".equals(tab))
            {
                wsUrl = formBean.getTxtDeleteWsdlUrl();
            }
            else if ("View".equals(tab))
            {
                wsUrl = formBean.getTxtViewWsdlUrl();
            }
            String xmlFile = finpack.FinPack.getProperty("tomcat1_path") + "/webapps/finstudio/generated/MstWsdlUrlResponse" + System.currentTimeMillis() + ".xml";
            WsdlParser browser = new WsdlParser(wsUrl, xmlFile);
            Map detail = browser.getDetail();
            if (detail != null)
            {
                String[] pkg = detail.get("package").toString().split("\\.");
                String data = "";
                if (pkg.length >= 4)
                {
                    data = "<input type=\"hidden\" id=\"txt" + tab + "WsProject\" name=\"txt" + tab + "WsProject\" readonly value=\"" + pkg[pkg.length - 4] + "\" />";
                }
                data += "<input type=\"hidden\" id=\"txt" + tab + "WsIntrface\" name=\"txt" + tab + "WsIntrface\" readonly value=\"" + detail.get("interface") + "\" />";
                mav.addObject("wsdlData", data);
                mav.addObject("process", "wsdlParse");
            }
            mav.setViewName("finmaster/ProjectMasterSpecification");
        }
        catch (Exception e)
        {
            mav.addObject("error", Logger.ErrorLogger(e) + e.getMessage());
            mav.setViewName("error");
        }
        return mav;
    }

    public ModelAndView getWsMethods(final HttpServletRequest request, final HttpServletResponse response, final FinMasterFormBean formBean)
    {
        ModelAndView mav;
        mav = new ModelAndView();
        try
        {
            String tab = request.getParameter("ForTab");
            String wsProject = "", wsIntrface = "";
            if ("Add".equals(tab))
            {
                wsProject = formBean.getTxtAddWsProject();
                wsIntrface = formBean.getTxtAddWsIntrface();
            }
            else if ("Edit".equals(tab))
            {
                wsProject = formBean.getTxtEditWsProject();
                wsIntrface = formBean.getTxtEditWsIntrface();
            }
            else if ("Delete".equals(tab))
            {
                wsProject = formBean.getTxtDeleteWsProject();
                wsIntrface = formBean.getTxtDeleteWsIntrface();
            }
            else if ("View".equals(tab))
            {
                wsProject = formBean.getTxtViewWsProject();
                wsIntrface = formBean.getTxtViewWsIntrface();
            }
            StringBuilder data = new StringBuilder();
            data.append("<input type=\"hidden\" id=\"txt").append(tab).append("WsProject\" name=\"txt").append(tab).append("WsProject\" readonly value=\"").append(wsProject).append("\" />");
            data.append("<input type=\"hidden\" id=\"txt").append(tab).append("WsIntrface\" name=\"txt").append(tab).append("WsIntrface\" readonly value=\"").append(wsIntrface).append("\" />");
            WebServiceConsumerParser parser = new WebServiceConsumerParser(wsIntrface, wsProject);
            List methods = parser.parseForMethods();
            if (methods != null)
            {
                data.append("<select id=\"tmpCmb").append(tab).append("WsMethod\" name=\"tmpCmb").append(tab).append("WsMethod\">");
                for (int i = 0; i < methods.size(); i++)
                {
                    data.append("<option value=\"").append(methods.get(i)).append("\">").append(methods.get(i)).append("</option>");
                }
                data.append("</select>");
            }
            mav.addObject("wsdlData", data.toString());
            mav.addObject("process", "wsdlParse");
            mav.setViewName("finmaster/ProjectMasterSpecification");
        }
        catch (Exception e)
        {
            mav.addObject("error", Logger.ErrorLogger(e) + e.getMessage());
            mav.setViewName("error");
        }
        return mav;
    }

    public ModelAndView getWsMethodParams(final HttpServletRequest request, final HttpServletResponse response, final FinMasterFormBean formBean)
    {
        ModelAndView mav;
        mav = new ModelAndView();
        try
        {
            String tab = request.getParameter("ForTab");
            String data = "", wsProject = "", wsIntrface = "", wsMethod = "";
            if ("Add".equals(tab))
            {
                wsProject = formBean.getTxtAddWsProject();
                wsIntrface = formBean.getTxtAddWsIntrface();
                wsMethod = formBean.getCmbAddWsMethod();
            }
            else if ("Edit".equals(tab))
            {
                wsProject = formBean.getTxtEditWsProject();
                wsIntrface = formBean.getTxtEditWsIntrface();
                wsMethod = formBean.getCmbEditWsMethod();
            }
            else if ("Delete".equals(tab))
            {
                wsProject = formBean.getTxtDeleteWsProject();
                wsIntrface = formBean.getTxtDeleteWsIntrface();
                wsMethod = formBean.getCmbDeleteWsMethod();
            }
            else if ("View".equals(tab))
            {
                wsProject = formBean.getTxtViewWsProject();
                wsIntrface = formBean.getTxtViewWsIntrface();
                wsMethod = formBean.getCmbViewWsMethod();
            }
            data = "<input type=\"hidden\" id=\"txt" + tab + "WsProject\" name=\"txt" + tab + "WsProject\" readonly value=\"" + wsProject + "\" />";
            data += "<input type=\"hidden\" id=\"txt" + tab + "WsIntrface\" name=\"txt" + tab + "WsIntrface\" readonly value=\"" + wsIntrface + "\" />";
            WebServiceConsumerParser parser = new WebServiceConsumerParser(wsIntrface, wsProject);
            Map params = parser.parseForParams(wsMethod);
            if (params != null)
            {
                data += "<input type=\"hidden\" id=\"txt" + tab + "WsRetType\" name=\"txt" + tab + "WsRetType\" readonly value=\"" + params.get("returnType") + "\" />";
                data += "<input type=\"hidden\" id=\"txt" + tab + "WsParams\" name=\"txt" + tab + "WsParams\" readonly value=\"" + params.get("parameters") + "\" />";
                data += "<input type=\"hidden\" id=\"txt" + tab + "WsExps\" name=\"txt" + tab + "WsExps\" readonly value=\"" + params.get("exceptions") + "\" />";
            }
            mav.addObject("wsdlData", data);
            mav.addObject("process", "wsdlParse");
            mav.setViewName("finmaster/ProjectMasterSpecification");
        }
        catch (Exception e)
        {
            mav.addObject("error", Logger.ErrorLogger(e) + e.getMessage());
            mav.setViewName("error");
        }
        return mav;
    }

    public ModelAndView setFinish(final HttpServletRequest request, final HttpServletResponse response, final FinMasterFormBean formBean)
    {
        ModelAndView mav;
        mav = new ModelAndView();
        mav.setViewName("finmaster/ProjectMasterSpecification");
        try
        {
            HttpSession session;
            session = request.getSession(false);
            if (session.getAttribute("ACLEmpCode") == null)
            {
                formBean.setEmpCode("");
            }
            else
            {
                formBean.setEmpCode(session.getAttribute("ACLEmpCode").toString());
            }

            int srno = -1;
            srno = service.insertIntoDataBase(formBean);
            if (srno > 0)
            {
                formBean.setSrNo(srno);
                String srsFile = null;
                String codeFile = null;
                String tomcatPath;
                tomcatPath = finpack.FinPack.getProperty("tomcat1_path");
                try
                {
                    try
                    {
                        //Set Whether the table belongs to the Alias selected or it is from other Schema
                        service.setSchema(formBean);
                    }
                    catch (Exception e)
                    {
                        Logger.ErrorLogger(e);
                    }

                    //Generate SRS
                    MasterSRSGeneration srs;
                    srs = new MasterSRSGeneration();
                    srsFile = srs.writeSRS(formBean);
                    mav.addObject("FileName", formBean.getSrNo() + "MGV2.xls");
                    //Generate Code
                    MasterGenerator mstGen;
                    mstGen = new MasterGenerator();
                    codeFile = mstGen.generateMasterFiles(formBean);
                    //Zip Folder
                    FolderZipper.zipFolder(codeFile, tomcatPath + "/webapps/finstudio/generated/" + formBean.getSrNo() + "MGV2.zip");
                    //Delete SRNO MGV2 Folder From generated
                    DirectoryService dSrvc;
                    dSrvc = new DirectoryService();
                    dSrvc.deleteFolder(codeFile);
                    mav.addObject("process", "finish");
                    mav.addObject("SRNO", formBean.getSrNo());
                    mav.addObject("codeFileName", formBean.getSrNo() + "MGV2.zip");
                }
                catch (Exception e)
                {
                    if ("".equals(srsFile))
                    {
                        mav.addObject("error", "Error In SRS Generation :" + e.getMessage());
                    }
                    else if ("".equals(codeFile))
                    {
                        mav.addObject("error", "Error In Code Generation :" + e.getMessage());
                    }
                    //Delete SRNO MGV2 Folder From generated
                    DirectoryService dSrvc;
                    dSrvc = new DirectoryService();
                    dSrvc.deleteFolder(tomcatPath + "/webapps/finstudio/generated/" + formBean.getSrNo() + "MGV2");
                    mav.addObject("error", Logger.ErrorLogger(e) + e.getMessage());
                    mav.setViewName("error");
                }
            }
        }
        catch (Exception e)
        {
            mav.addObject("error", Logger.ErrorLogger(e) + e.getMessage());
            mav.setViewName("error");
        }
        return mav;
    }
}
