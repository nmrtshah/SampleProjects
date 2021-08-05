/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.apps.finstudio.findatareqexecutor;

import com.finlogic.apps.finstudio.finfiletransfer.FinfiletransferFormBean;
import com.finlogic.util.Logger;
import com.finlogic.util.findatareqexecutor.ServerPropertyReader;
import com.finlogic.util.properties.HardCodeProperty;
import java.io.File;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 *
 * @author Sonam Patel
 */
public class FinDataReqExecutorController extends MultiActionController
{

    private final FinDataReqExecutorService service = new FinDataReqExecutorService();
    private final String finlib_path = finpack.FinPack.getProperty("finlib_path");

    public ModelAndView defaultMethod(final HttpServletRequest request, final HttpServletResponse response)
    {
        return getMenu(request, response);
    }

    public ModelAndView getMenu(final HttpServletRequest request, final HttpServletResponse response)
    {
        ModelAndView mav;
        mav = new ModelAndView();
        mav.addObject("finlib_path", finlib_path);
        mav.setViewName("findatareqexecutor/Main");
        return mav;
    }

    public ModelAndView addLoader(final HttpServletRequest request, final HttpServletResponse response)
    {
        ModelAndView mav;
        mav = new ModelAndView();
        try
        {
            mav.addObject("proj_ids", service.getAddProjects());
            mav.addObject("process", "main");
            mav.setViewName("findatareqexecutor/Add");
        }
        catch (Exception e)
        {
            mav.addObject("error", e.getMessage());
            mav.setViewName("findatareqexecutor/Error");
            finutils.errorhandler.ErrorHandler.PrintInFile(e, request);
        }
        return mav;
    }


    public ModelAndView viewLoader(final HttpServletRequest request, final HttpServletResponse response)
    {
        ModelAndView mav;
        mav = new ModelAndView();
        try
        {
            mav.addObject("proj_ids", service.getViewProjects());
            mav.addObject("entry_bys", service.getViewEmployees());
            mav.addObject("req_ids", service.getViewRequests("-1", null));
            DateFormat dateFormat;
            dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date date;
            date = new Date();
            String dateString;
            dateString = dateFormat.format(date);
            mav.addObject("fromDate", dateString);
            mav.addObject("toDate", dateString);
            mav.setViewName("findatareqexecutor/View");
        }
        catch (Exception e)
        {
            mav.addObject("error", e.getMessage());
            mav.setViewName("findatareqexecutor/Error");
            finutils.errorhandler.ErrorHandler.PrintInFile(e, request);
        }
        return mav;
    }

    public ModelAndView getAddRequests(final HttpServletRequest request, final HttpServletResponse response)
    {
        ModelAndView mav;
        mav = new ModelAndView();
        try
        {
            mav.addObject("req_nos", service.getAddRequests(request.getParameter("reqFilter"), request.getParameter("projId")));
            mav.addObject("process", "addReq");
            mav.setViewName("findatareqexecutor/Add");
        }
        catch (Exception e)
        {
            mav.addObject("error", e.getMessage());
            mav.setViewName("findatareqexecutor/Error");
            finutils.errorhandler.ErrorHandler.PrintInFile(e, request);
        }
        return mav;
    }

    public ModelAndView getActualServers(final HttpServletRequest request, final HttpServletResponse response)
    {
        ModelAndView mav;
        mav = new ModelAndView();
        try
        {
            HttpSession session;
            session = request.getSession(false);
            String empCode = "";
            if (session.getAttribute("ACLEmpCode") != null)
            {
                empCode = session.getAttribute("ACLEmpCode").toString();
            }

            mav.addObject("process", "actualServer");
            mav.setViewName("findatareqexecutor/Add");
            if (!"".equals(empCode))
            {
                //Check Whether The Project Is Mapped With Current User Or Not
                if (service.checkProjectUserMapping(empCode, request.getParameter("projId")))
                {
                    mav.addObject("actual_server", service.getActualServers(request.getParameter("projId")));
            }
            else
            {
                    mav.addObject("prjUsrMapResult", "Selected Project is not mapped with the Current User");
                }
            }
            else
            {
                mav.addObject("prjUsrMapResult", "MECODE not found in Current Session. Please login again.");
            }
        }
        catch (Exception e)
        {
            mav.addObject("error", e.getMessage());
            mav.setViewName("findatareqexecutor/Error");
            finutils.errorhandler.ErrorHandler.PrintInFile(e, request);
        }
        return mav;
    }

    public ModelAndView formatQuery(final HttpServletRequest request, final HttpServletResponse response)
    {
        ModelAndView mav;
        mav = new ModelAndView();
        try
        {
            //Format The Query Text
            mav.addObject("queryText", service.formatQueryText(request.getParameter("queryText").trim()));
            mav.addObject("process", "formatQuery");
            mav.setViewName("findatareqexecutor/Add");
        }
        catch (Exception e)
        {
            mav.addObject("error", e.getMessage());
            mav.setViewName("findatareqexecutor/Error");
            finutils.errorhandler.ErrorHandler.PrintInFile(e, request);
        }
        return mav;
    }

    public ModelAndView insertTempData(final HttpServletRequest request, final HttpServletResponse response, final FinDataReqExecutorFormBean formBean)
    {
        ModelAndView mav;
        mav = new ModelAndView();
        try
        {
            mav.addObject("process", "analysis");

            HttpSession session;
            session = request.getSession(false);
            String empCode = "";
            if (session.getAttribute("ACLEmpCode") != null)
            {
                empCode = session.getAttribute("ACLEmpCode").toString();
            }

            if (!"".equals(empCode))
            {
                //Check Whether The Project Is Mapped With Current User Or Not
                if (!service.checkProjectUserMapping(empCode, formBean.getCmbAddProjId()))
                {
                    mav.addObject("prjDbMapResult", "Selected Project is not mapped with the Current User");
                    mav.setViewName("findatareqexecutor/Add");
                }
                //Check Whether The Databse Is Mapped With Current Project Or Not
                else if (!service.checkProjectDatabaseMapping(formBean.getCmbAddProjId(), formBean.getCmbAddDatabase()))
                {
                    mav.addObject("prjDbMapResult", "Selected Database is not mapped with Selected Project");
                    mav.setViewName("findatareqexecutor/Add");
                }
                else
                {
                    if ("textarea".equals(formBean.getRdoInput()))
                    {
                        //Decode Query Text And Set In FormBean
                        String encodeQuery = formBean.getTxtAddQuery();
                        String decodeQuery = URLDecoder.decode(encodeQuery, "UTF-8");
                        formBean.setTxtAddQuery(decodeQuery);

                        //Analyze The Query Text
                        List lsDb = service.getDbTypeName(formBean.getCmbAddDatabase());
                        ServerPropertyReader propReader = new ServerPropertyReader();
                        String server = propReader.getActualServer(lsDb.get(0).toString());
                        Map alertMap = service.analyzeQuery(lsDb.get(1).toString(), formBean.getTxtAddQuery().trim(), server, lsDb.get(2).toString());
                        if (alertMap.get("message") != null && !"".equals(alertMap.get("message")))
                        {
                            //Return Error Message On JSP And Alert Message
                            mav.addObject("result", alertMap);
                        }
                        else
                        {
                            Map qBeanMap = null;
                            if (alertMap.get("beanMap") != null)
                            {
                                qBeanMap = (Map) alertMap.get("beanMap");
                            }

                            //Validate All Queries And Get It's Results & Timings And Set In FormBean
                            String verifyServer = "";
                            if (!"none".equals(formBean.getRdoAddVerifyServer()))
                            {
                                String actualServerID = service.getServerID(formBean.getCmbAddDatabase());
                                verifyServer = service.getVerificationServer(actualServerID, formBean.getRdoAddVerifyServer());
                                formBean.setVerifyServer(verifyServer);
                            }
                            Map<String, String[]> qMap = service.validateQueries(verifyServer.split(":")[0], formBean.getTxtAddQuery());
                            formBean.setDevExeResult(qMap.get("results"));
                            formBean.setDevExeStatus(qMap.get("status"));
                            formBean.setDevExeStartTime(qMap.get("startTime"));
                            formBean.setDevExeEndTime(qMap.get("endTime"));

                            //Insert Data In Temp Table
                            service.insertTempData(formBean, qBeanMap, lsDb.get(1).toString());
                        }
                        mav.setViewName("findatareqexecutor/Add");
                    }
                    else if ("file".equals(formBean.getRdoInput()))
                    {
                        if (formBean.getQueryFileName() != null || "".equals(formBean.getQueryFileName()))
                        {
                            String fileName = formBean.getQueryFileName();
                            //Copy File from /filebox/ to /finstudio/upload/
                            if (service.copyFile(fileName))
                            {
                                //Get File content
                                String queryText = service.getFileQuery(fileName);

                                //Format The Query Text
                                String formatedQuery = service.formatQueryText(queryText.trim());

                                //Analyze The Query Text
                                List lsDb = service.getDbTypeName(formBean.getCmbAddDatabase());
                                ServerPropertyReader propReader = new ServerPropertyReader();
                                String server = propReader.getActualServer(lsDb.get(0).toString());
                                Map alertMap = service.analyzeQuery(lsDb.get(1).toString(), formatedQuery, server, lsDb.get(2).toString());

                                //Delete File from /finstudio/upload/
                                File f = new File(FinDataReqExecutorFormBean.getFILEUPLOAD_PATH() + fileName);
                                if (f.exists())
                                {
                                    f.delete();
                                }

                                if (alertMap.get("message") != null && !"".equals(alertMap.get("message")))
                                {
                                    //Return Error Message On JSP And Alert Message
                                    mav.addObject("result", alertMap);
                                }
                                else
                                {
                                    Map qBeanMap = null;
                                    if (alertMap.get("beanMap") != null)
                                    {
                                        qBeanMap = (Map) alertMap.get("beanMap");
                                    }

                                    //Set Formated Query Text In FormBean
                                    formBean.setTxtAddQuery(formatedQuery);

                                    //Validate All Queries And Get It's Results & Timings And Set In FormBean
                                    String verifyServer = "";
                                    if (!"none".equals(formBean.getRdoAddVerifyServer()))
                                    {
                                        String actualServerID = service.getServerID(formBean.getCmbAddDatabase());
                                        verifyServer = service.getVerificationServer(actualServerID, formBean.getRdoAddVerifyServer());
                                        formBean.setVerifyServer(verifyServer);
                                    }
                                    Map<String, String[]> qMap = service.validateQueries(verifyServer.split(":")[0], formBean.getTxtAddQuery());
                                    formBean.setDevExeResult(qMap.get("results"));
                                    formBean.setDevExeStatus(qMap.get("status"));
                                    formBean.setDevExeStartTime(qMap.get("startTime"));
                                    formBean.setDevExeEndTime(qMap.get("endTime"));

                                    //Insert Data In Temp Table
                                    service.insertTempData(formBean, qBeanMap, lsDb.get(1).toString());
                                }
                                mav.setViewName("findatareqexecutor/Add");
                            }
                            else
                            {
                                Logger.DataLogger("Error While Copying Query File");
                                mav.setViewName("findatareqexecutor/Error");
                            }
                        }
                        else
                        {
                            Logger.DataLogger("Query File's name is not available");
                            mav.setViewName("findatareqexecutor/Error");
                        }
                    }
                }
            }
            else
            {
                mav.addObject("prjDbMapResult", "MECODE not found in Current Session. Please login again.");
            }
        }
        catch (Exception e)
        {
            mav.addObject("error", e.getMessage());
            mav.setViewName("findatareqexecutor/Error");
            finutils.errorhandler.ErrorHandler.PrintInFile(e, request);
        }
        return mav;
    }

    public ModelAndView getAddRecords(final HttpServletRequest request, final HttpServletResponse response)
    {
        ModelAndView mav;
        mav = new ModelAndView();
        try
        {
            //Get Requests Of Current User Session
            List ls = service.getAddRecords(request.getParameter("hdnUserSession"));
            if (ls != null)
            {
                mav.addObject("records", ls.get(0));
                mav.addObject("confirmation", ls.get(1));
                mav.addObject("backup", ls.get(2));
                mav.addObject("logTable", ls.get(3));
            }
            mav.addObject("finlib_path", finlib_path);
            mav.setViewName("findatareqexecutor/AddReport");
        }
        catch (Exception e)
        {
            mav.addObject("error", e.getMessage());
            mav.setViewName("findatareqexecutor/Error");
            finutils.errorhandler.ErrorHandler.PrintInFile(e, request);
        }
        return mav;
    }

    public ModelAndView getDependencyRecords(final HttpServletRequest request, final HttpServletResponse response)
    {
        ModelAndView mav;
        mav = new ModelAndView();
        try
        {
            //Get Dependency Of Query
            List ls = service.getDependencyRecords(request.getParameter("queryId"), request.getParameter("tab"));
            if (ls != null && ls.size() > 0)
            {
                mav.addObject("depRecords", ls);
            }
            mav.setViewName("findatareqexecutor/AddReport");
        }
        catch (Exception e)
        {
            mav.addObject("error", e.getMessage());
            mav.setViewName("findatareqexecutor/Error");
            finutils.errorhandler.ErrorHandler.PrintInFile(e, request);
        }
        return mav;
    }

    public ModelAndView deleteBatch(final HttpServletRequest request, final HttpServletResponse response, final FinDataReqExecutorFormBean formBean)
    {
        ModelAndView mav;
        mav = new ModelAndView();
        try
        {
            //Delete Batch
            String batchId = request.getParameter("batchId");
            String sessionId = formBean.getHdnUSessionId();
            service.deleteBatch(sessionId, batchId);

            //Get Requests Of Current User Session
            List ls = service.getAddRecords(formBean.getHdnUSessionId());
            if (ls != null)
            {
                mav.addObject("records", ls.get(0));
                mav.addObject("confirmation", ls.get(1));
                mav.addObject("backup", ls.get(2));
                mav.addObject("logTable", ls.get(3));
            }
            mav.addObject("finlib_path", finlib_path);
            mav.setViewName("findatareqexecutor/AddReport");
        }
        catch (Exception e)
        {
            mav.addObject("error", e.getMessage());
            mav.setViewName("findatareqexecutor/Error");
            finutils.errorhandler.ErrorHandler.PrintInFile(e, request);
        }
        return mav;
    }

    public ModelAndView confirmRequest(final HttpServletRequest request, final HttpServletResponse response, final FinDataReqExecutorFormBean formBean)
    {
        ModelAndView mav;
        mav = new ModelAndView();
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

            mav.addObject("dataReqID", service.confirmRequest(formBean, request.getRemoteAddr()));
            mav.addObject("process", "main");
            mav.setViewName("findatareqexecutor/Add");
        }
        catch (Exception e)
        {
            mav.addObject("error", e.getMessage());
            mav.setViewName("findatareqexecutor/Error");
            finutils.errorhandler.ErrorHandler.PrintInFile(e, request);
        }
        return mav;
    }

    public ModelAndView deleteAddRequest(final HttpServletRequest request, final HttpServletResponse response, final FinDataReqExecutorFormBean formBean)
    {
        ModelAndView mav;
        mav = new ModelAndView();
        try
        {
            //Delete Request
            service.deleteAddRequest(formBean.getHdnUSessionId());
            mav.addObject("proj_ids", service.getAddProjects());
            mav.addObject("process", "main");
            mav.setViewName("findatareqexecutor/Add");
        }
        catch (Exception e)
        {
            mav.addObject("error", e.getMessage());
            mav.setViewName("findatareqexecutor/Error");
            finutils.errorhandler.ErrorHandler.PrintInFile(e, request);
        }
        return mav;
    }

    public ModelAndView getViewRequests(final HttpServletRequest request, final HttpServletResponse response)
    {
        ModelAndView mav;
        mav = new ModelAndView();
        try
        {
            mav.addObject("req_ids", service.getViewRequests(request.getParameter("projId"), request.getParameter("reqFilter")));
            mav.addObject("process", "viewReq");
            mav.setViewName("findatareqexecutor/View");
        }
        catch (Exception e)
        {
            mav.addObject("error", e.getMessage());
            mav.setViewName("findatareqexecutor/Error");
            finutils.errorhandler.ErrorHandler.PrintInFile(e, request);
        }
        return mav;
    }

    public ModelAndView viewReport(final HttpServletRequest request, final HttpServletResponse response, final FinDataReqExecutorFormBean formBean)
    {
        ModelAndView mav;
        mav = new ModelAndView();
        try
        {
            //Get Requests
            mav.addObject("records", service.getViewRecords(formBean));
            mav.setViewName("findatareqexecutor/Report");
        }
        catch (Exception e)
        {
            mav.addObject("error", e.getMessage());
            mav.setViewName("findatareqexecutor/Error");
            finutils.errorhandler.ErrorHandler.PrintInFile(e, request);
        }
        return mav;
    }

    public ModelAndView viewQueryReport(final HttpServletRequest request, final HttpServletResponse response)
    {
        ModelAndView mav;
        mav = new ModelAndView();
        try
        {
            HttpSession session;
            session = request.getSession(false);
            boolean flag = false;
            if (session.getAttribute("ACLEmpCode") != null)
            {
                String empCode = session.getAttribute("ACLEmpCode").toString();

                HardCodeProperty hcp = new HardCodeProperty();
                String validExecutors = hcp.getProperty("data_request_valid_executor");
               
                String[] executors = validExecutors.split(",");
                int exe = 0;
                for (; exe < executors.length; exe++)
                {
                    if (executors[exe].trim().equals(empCode))
                    {
                        break;
                    }
                }

                if (exe < executors.length)
                {
                    flag = true;
                }
            }

            //Get Query Details
            String id = request.getParameter("dataReqId");
            String status = request.getParameter("exeStatus");
            mav.addObject("dataReqId", id);
            mav.addObject("exeStatus", status);
            mav.addObject("finlib_path", finlib_path);
            List ls = service.viewQueryDetails(id);
            mav.addObject("records", ls.get(0));
            List span = (List) ls.get(0);
            mav.addObject("rowspan", span.size() + 1);
            mav.addObject("showLink", ls.get(1));
            if (flag)
            {
                mav.addObject("validUser", "YES");
            }
            else
            {
                mav.addObject("validUser", "NO");
            }
            mav.setViewName("findatareqexecutor/QueryDetail");
        }
        catch (Exception e)
        {
            mav.addObject("error", e.getMessage());
            mav.setViewName("findatareqexecutor/Error");
            finutils.errorhandler.ErrorHandler.PrintInFile(e, request);
        }
        return mav;
    }

    public ModelAndView executeRequest(final HttpServletRequest request, final HttpServletResponse response)
    {
        ModelAndView mav;
        mav = new ModelAndView();
        try
        {
            if (request.getRequestURI().equals("/finstudio/exec_findatareqexec.fin"))
            {
                HttpSession session;
                session = request.getSession(false);
                String empCode = "";
                if (session.getAttribute("ACLEmpCode") != null)
                {
                    empCode = session.getAttribute("ACLEmpCode").toString();
                }

                //Execute Data Request
                StringBuilder result = new StringBuilder();
                result.append("<input type=\"hidden\" id=\"executeResult\" name=\"executeResult\" disabled value=\"");
                result.append(service.processRequest(request.getParameter("dataReqId"), empCode, request.getRemoteAddr(), "Execute"));
                result.append("\"/>");
                mav.addObject("result", result);
            }
            else
            {
                mav.addObject("AccessMsg", "Authentication Failed ! You do not have rights to execute this Data Request.");
            }
            mav.setViewName("findatareqexecutor/Report");
        }
        catch (Exception e)
        {
            mav.addObject("error", e.getMessage());
            mav.setViewName("findatareqexecutor/Error");
            finutils.errorhandler.ErrorHandler.PrintInFile(e, request);
        }
        return mav;
    }

    public ModelAndView cancelViewRequest(final HttpServletRequest request, final HttpServletResponse response)
    {
        ModelAndView mav;
        mav = new ModelAndView();
        try
        {
            HttpSession session;
            session = request.getSession(false);
            String empCode = "";
            if (session.getAttribute("ACLEmpCode") != null)
            {
                empCode = session.getAttribute("ACLEmpCode").toString();
            }

            //Cancel Data Request
            StringBuilder result = new StringBuilder();
            result.append("<input type=\"hidden\" id=\"cancelViewResult\" name=\"cancelViewResult\" disabled value=\"");
            result.append(service.processRequest(request.getParameter("dataReqId"), empCode, request.getRemoteAddr(), "Cancel"));
            result.append("\"/>");
            mav.addObject("result", result);
            mav.setViewName("findatareqexecutor/Report");
        }
        catch (Exception e)
        {
            mav.addObject("error", e.getMessage());
            mav.setViewName("findatareqexecutor/Error");
            finutils.errorhandler.ErrorHandler.PrintInFile(e, request);
        }
        return mav;
    }

    public ModelAndView haltViewRequest(final HttpServletRequest request, final HttpServletResponse response)
    {
        ModelAndView mav = new ModelAndView();
        try
        {
            HttpSession session = request.getSession(false);
            String empCode = "";
            if (session.getAttribute("ACLEmpCode") != null)
            {
                empCode = session.getAttribute("ACLEmpCode").toString();
            }

            //Halt Data Request
            StringBuilder result = new StringBuilder();
            result.append("<input type=\"hidden\" id=\"haltViewResult\" name=\"haltViewResult\" disabled value=\"");
            result.append(service.processRequest(request.getParameter("dataReqId"), empCode, request.getRemoteAddr(), "Halt"));
            result.append("\"/>");
            mav.addObject("result", result);
            mav.setViewName("findatareqexecutor/Report");
        }
        catch (Exception e)
        {
            mav.addObject("error", e.getMessage());
            mav.setViewName("findatareqexecutor/Error");
            finutils.errorhandler.ErrorHandler.PrintInFile(e, request);
        }
        return mav;
    }

    public ModelAndView resumeViewRequest(final HttpServletRequest request, final HttpServletResponse response)
    {
        ModelAndView mav = new ModelAndView();
        try
        {
            HttpSession session = request.getSession(false);
            String empCode = "";
            if (session.getAttribute("ACLEmpCode") != null)
            {
                empCode = session.getAttribute("ACLEmpCode").toString();
            }

            //Resume Data Request
            StringBuilder result = new StringBuilder();
            result.append("<input type=\"hidden\" id=\"resumeViewResult\" name=\"resumeViewResult\" disabled value=\"");
            result.append(service.processRequest(request.getParameter("dataReqId"), empCode, request.getRemoteAddr(), "Resume"));
            result.append("\"/>");
            mav.addObject("result", result);
            mav.setViewName("findatareqexecutor/Report");
        }
        catch (Exception e)
        {
            mav.addObject("error", e.getMessage());
            mav.setViewName("findatareqexecutor/Error");
            finutils.errorhandler.ErrorHandler.PrintInFile(e, request);
        }
        return mav;
    }

    public ModelAndView createNewRequest(final HttpServletRequest request, final HttpServletResponse response)
    {
        ModelAndView mav;
        mav = new ModelAndView();
        try
        {
            HttpSession session;
            session = request.getSession(false);
            String empCode = "";
            if (session.getAttribute("ACLEmpCode") != null)
            {
                empCode = session.getAttribute("ACLEmpCode").toString();
            }

            //Create New Data Request
            StringBuilder result = new StringBuilder();
            result.append("<input type=\"hidden\" id=\"createNewReqResult\" name=\"createNewReqResult\" disabled value=\"");
            result.append(service.createNewRequest(request.getParameter("dataReqId"), empCode));
            result.append("\"/>");
            mav.addObject("result", result);
            mav.setViewName("findatareqexecutor/Report");
        }
        catch (Exception e)
        {
            mav.addObject("error", e.getMessage());
            mav.setViewName("findatareqexecutor/Error");
            finutils.errorhandler.ErrorHandler.PrintInFile(e, request);
        }
        return mav;
    }

    /**
     * It returns the values to print a report
     * @param request
     * @param response
     * @return
     */
    public ModelAndView reportLoader(final HttpServletRequest request, final HttpServletResponse response)
    {
        ModelAndView mav;
        mav= new ModelAndView();
        try
        {
            mav.addObject("action","reportLoad");
            mav.addObject("jsonmaster", service.getReportDataLoad());
            mav.setViewName("findatareqexecutor/Report");
        }
        catch (Exception e)
        {
            mav.addObject("error", e.getMessage());
            mav.setViewName("finfiletransfer/Error");
            finutils.errorhandler.ErrorHandler.PrintInFile(e, request);
        }
        return mav;
    }

}


