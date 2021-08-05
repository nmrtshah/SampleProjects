/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.apps.wfms2.scriptgen;

import com.finlogic.util.DirectoryService;
import com.finlogic.util.FolderZipper;
import com.finlogic.util.scriptgen.HTMLParserService;
import finpack.FinPack;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.multipart.support.StringMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 * Controller for scriptgen.jsp to handle request and AJAX call to provide<br>
 * specified data back to view.
 *
 * @author Ankur Mistry
 */
public class ScriptGenController extends MultiActionController
{

    /*------- Edited by Divyang Kankotiya -------- */
    private static final String TOMCAT_PATH = FinPack.getProperty("tomcat1_path");
    /*------- End Edited by Divyang Kankotiya -------- */
    private static ScriptGenService scriptGenService = new ScriptGenService();

    /**
     * First request to scriptgen.jsp page. Fills Project List and Browser List.
     *
     * @param request
     * @param response
     * @return ModelAndView
     */
    public ModelAndView showRequest(final HttpServletRequest request, final HttpServletResponse response)
    {
        ModelAndView view = new ModelAndView("scriptgen/scriptgen");
        String finlibPath = finpack.FinPack.getProperty("finlib_path");
        try
        {
            view.addObject("projectList", scriptGenService.getProjectList());
            view.addObject("finlibPath", finlibPath);
            String getPageLoad = request.getParameter("pageLoad");
            if (getPageLoad != null)
            {
                view.addObject("pageLoad", getPageLoad);
            }
        }
        catch (Exception exp)
        {
            finutils.errorhandler.ErrorHandler.PrintInFile(exp, request,
                    "Error Report : "
                    + " Date : " + Calendar.getInstance().getTime().toString()
                    + " ID : " + Calendar.getInstance().getTimeInMillis());
            view.setViewName("error");
            view.addObject("error", "Error Processing Request.");
        }
        return view;
    }

    /**
     * Returns Module Tree List
     *
     * @param request
     * @param response
     * @return ModelAndView
     */
    public ModelAndView getModuleTreeList(final HttpServletRequest request, final HttpServletResponse response)
    {
        ModelAndView view = new ModelAndView("scriptgen/scriptgenajax");
        try
        {
            String prjID;
            prjID = request.getParameter("prjID");
            view.addObject("fillCombo", "moduleList");
            view.addObject("moduleList", scriptGenService.getModuleTreeList(prjID));
        }
        catch (Exception exp)
        {
            finutils.errorhandler.ErrorHandler.PrintInFile(exp, request,
                    "Error Report : "
                    + " Date : " + Calendar.getInstance().getTime().toString()
                    + " ID : " + Calendar.getInstance().getTimeInMillis());
            view.setViewName("error");
            view.addObject("error", "Error Processing Request.");
        }
        return view;
    }

    /**
     * Returns Test Case List.
     *
     * @param request
     * @param response
     * @return ModelAndView
     */
    public ModelAndView getTestCaseList(final HttpServletRequest request, final HttpServletResponse response)
    {
        ModelAndView view = new ModelAndView("scriptgen/scriptgenajax");
        try
        {
            String projectID = request.getParameter("prjID");
            String moduleID = request.getParameter("moduleID");
            view.addObject("fillCombo", "testCaseList");
            view.addObject("testCaseList", scriptGenService.getTestCaseList(projectID, moduleID));
        }
        catch (Exception exp)
        {
            finutils.errorhandler.ErrorHandler.PrintInFile(exp, request,
                    "Error Report : "
                    + " Date : " + Calendar.getInstance().getTime().toString()
                    + " ID : " + Calendar.getInstance().getTimeInMillis());
            view.setViewName("error");
            view.addObject("error", "Error Processing Request.");
        }
        return view;
    }

    /**
     * Returns HTML Control Access Type List.
     *
     * @param request
     * @param response
     * @return ModelAndView
     */
    public ModelAndView getAccessTypeList(final HttpServletRequest request, final HttpServletResponse response)
    {
        ModelAndView view = new ModelAndView("scriptgen/scriptgenajax");
        try
        {
            int controlID = Integer.parseInt(request.getParameter("controlID"));
            view.addObject("fillCombo", "accessTypeList");
            view.addObject("accessList", scriptGenService.getAccessTypeList(controlID));
        }
        catch (Exception exp)
        {
            finutils.errorhandler.ErrorHandler.PrintInFile(exp, request,
                    "Error Report : "
                    + " Date : " + Calendar.getInstance().getTime().toString()
                    + " ID : " + Calendar.getInstance().getTimeInMillis());
            view.setViewName("error");
            view.addObject("error", "Error Processing Request.");
        }
        return view;
    }

    /**
     * Generates Selenium Script and redirects to seleniumscript.jsp page.
     *
     * @param request
     * @param response
     * @param testScriptFormBean
     * @return
     */
    public ModelAndView generateScript(final HttpServletRequest request, final HttpServletResponse response, final ScriptGenFormBean formBean)
    {
        ModelAndView view = new ModelAndView();

        try
        {
            /**
             * Every html control can have zero or more javascript event
             * associated with it.<br> All events are stored with each control
             * in array.<br> Which helps to identify number of controls to be
             * considered and events, test case nature<br> associated with each
             * control.
             */
            HttpSession session = request.getSession(false);
            if (session.getAttribute("ACLEmpCode") == null)
            {
                formBean.setEmpCode("-");
            }
            else
            {
                formBean.setEmpCode(session.getAttribute("ACLEmpCode").toString());
            }

            StringTokenizer controlsDetail = new StringTokenizer(request.getParameter("arr"), ",");
            int arraySize = (int) controlsDetail.countTokens();
            int[] eventArray = new int[arraySize];
            int counter = 0;
            boolean pass = true;
            while (controlsDetail.hasMoreElements())
            {
                try
                {
                    eventArray[counter++] = Integer.parseInt(controlsDetail.nextToken());
                }
                catch (NumberFormatException nfe)
                {
                    finutils.errorhandler.ErrorHandler.PrintInFile(nfe, request,
                            "Error Report : "
                            + " Date : " + Calendar.getInstance().getTime().toString()
                            + " ID : " + Calendar.getInstance().getTimeInMillis());
                    view.setViewName("error");
                    view.addObject("error", "Error Processing Request.");
                    pass = false;
                }
                catch (Exception exp)
                {
                    finutils.errorhandler.ErrorHandler.PrintInFile(exp, request,
                            "Error Report : "
                            + " Date : " + Calendar.getInstance().getTime().toString()
                            + " ID : " + Calendar.getInstance().getTimeInMillis());
                    view.setViewName("error");
                    view.addObject("error", "Error Processing Request.");
                    pass = false;
                }
            }

            if (pass)
            {
                //insert into database        
                formBean.setEventArray(eventArray);
                int srno = scriptGenService.insertIntoDB(formBean);

                //generate java class
                String fileName = scriptGenService.generateScript(srno, eventArray, formBean);

                if (!fileName.equals(""))
                {

                    /*------- Edited by Divyang Kankotiya -------- */
                    FolderZipper.zipFolder(TOMCAT_PATH + "/webapps/finstudio/generated/" + fileName, TOMCAT_PATH + "/webapps/finstudio/generated/" + fileName + ".zip");
                    //view.setViewName("scriptgen/seleniumscript");

                    view.setViewName("scriptgen/seleniumscript");
                    view.addObject("pageID", srno);
                    view.addObject("getzipfile", fileName + ".zip");
                    /*------- End Edited by Divyang Kankotiya -------- */
//                    view.addObject("javaFileName", fileName + ".java");
//                    view.agenerateFilesddObject("csvFileName", fileName + ".csv");

                    //Delete SRNO Folder From generated
                    DirectoryService dSrvc;
                    dSrvc = new DirectoryService();
                    dSrvc.deleteFolder(TOMCAT_PATH + "/webapps/finstudio/generated/" + fileName);
                }
                else
                {
                    view.setViewName("error");
                    view.addObject("error", "Error Processing Request.");
                }
            }
            else
            {
                view.setViewName("error");
                view.addObject("error", "Error Processing Request.");
            }
        }
        catch (Exception exp)
        {
            finutils.errorhandler.ErrorHandler.PrintInFile(exp, request,
                    "Error Report : "
                    + " Date : " + Calendar.getInstance().getTime().toString()
                    + " ID : " + Calendar.getInstance().getTimeInMillis());
            view.setViewName("error");
            view.addObject("error", "Error Processing Request.");
        }
        return view;
    }

    /**
     * Redirects to seleniumscript.jsp page.
     *
     * @param request
     * @param response
     * @return ModelAndView
     */
    public ModelAndView showLink(final HttpServletRequest request, final HttpServletResponse response)
    {
        ModelAndView view = new ModelAndView("scriptgen/seleniumscript");
        try
        {
            view.addObject("pageID", request.getAttribute("pageID").toString());
        }
        catch (Exception exp)
        {
            finutils.errorhandler.ErrorHandler.PrintInFile(exp, request,
                    "Error Report : "
                    + " Date : " + Calendar.getInstance().getTime().toString()
                    + " ID : " + Calendar.getInstance().getTimeInMillis());
            view.setViewName("error");
            view.addObject("error", "Error Processing Request.");
        }
        return view;
    }

    /**
     * Returns HTML control list of HTML Source or URL
     *
     * @param request
     * @param response
     * @return ModelAndView
     */
    public ModelAndView getControlList(final HttpServletRequest request, final HttpServletResponse response)
    {
        ModelAndView view = new ModelAndView("scriptgen/scriptgenajax");
        try
        {
            boolean isURL = false;
            String pageURL = "";
            if ("opturl".equals(request.getParameter("option")))
            {
                isURL = true;
                pageURL = request.getParameter("pageURL");
            }
            else if ("optsource".equals(request.getParameter("option")))
            {
                pageURL = request.getParameter("pageSource");
            }
            else if ("optsourcefile".equals(request.getParameter("option")))
            {
                pageURL = request.getParameter("pageSourceFile");
                HTMLParserService.copyPageSourceFile(pageURL);
                pageURL = HTMLParserService.readPageSource(pageURL);
            }
            view.addObject("controlList", scriptGenService.getControlList(isURL, pageURL));
            view.addObject("eventList", scriptGenService.getControlEventList());
            view.addObject("testCaseNatureList", scriptGenService.getTestCaseNatureList());
            view.addObject("fillCombo", "controlList");
        }
        catch (Exception exp)
        {
            finutils.errorhandler.ErrorHandler.PrintInFile(exp, request,
                    "Error Report : "
                    + " Date : " + Calendar.getInstance().getTime().toString()
                    + " ID : " + Calendar.getInstance().getTimeInMillis());
            view.addObject("error", "Error Processing Request.");
        }
        return view;
    }

    /**
     * Returns HTML Control List of two HTML Sources.
     *
     * @param request
     * @param response
     * @return ModelAndView
     */
    public ModelAndView matchUIControl(final HttpServletRequest request, final HttpServletResponse response)
    {
        ModelAndView view = new ModelAndView("scriptgen/scriptgenajax");
        try
        {
            StringBuilder oldSource = new StringBuilder(request.getParameter("oldSource"));
            StringBuilder newSource = new StringBuilder(request.getParameter("newSource"));
            List oldControlList = scriptGenService.getControlList(false, oldSource.toString());
            List newControlList = scriptGenService.getControlList(false, newSource.toString());
            view.addObject("oldControlList", oldControlList);
            view.addObject("newControlList", newControlList);
            if (oldControlList.size() > newControlList.size())
            {
                view.addObject("length", oldControlList.size());
            }
            else
            {
                view.addObject("length", newControlList.size());
            }
            view.addObject("fillCombo", "matchcontrol");
            view.addObject("opcode", request.getParameter("opcode"));
        }
        catch (Exception exp)
        {
            finutils.errorhandler.ErrorHandler.PrintInFile(exp, request,
                    "Error Report : "
                    + " Date : " + Calendar.getInstance().getTime().toString()
                    + " ID : " + Calendar.getInstance().getTimeInMillis());
            view.addObject("error", "Error Processing Request.");
        }
        return view;
    }

    /**
     *
     * @param request
     * @param response
     * @return ModelAndView
     */
    @Override
    protected void initBinder(final HttpServletRequest request, final ServletRequestDataBinder binder)
    {
        binder.registerCustomEditor(String.class, new StringMultipartFileEditor());
    }

    public ModelAndView getGenClassList(final HttpServletRequest request, final HttpServletResponse response, final ScriptGenFormBean formBean)
    {
        ModelAndView view = new ModelAndView("scriptgen/scriptgenajax");
        try
        {
            List ls = scriptGenService.getGenClassList(formBean.getPrjID(), formBean.getModuleID(), formBean.getFromDate(), formBean.getToDate());
            view.addObject("fillList", "genClassList");
            view.addObject("getClassList", ls);
        }
        catch (Exception exp)
        {
            finutils.errorhandler.ErrorHandler.PrintInFile(exp, request,
                    "Error Report : "
                    + " Date : " + Calendar.getInstance().getTime().toString()
                    + " ID : " + Calendar.getInstance().getTimeInMillis());
            view.setViewName("error");
            view.addObject("error", "Error Processing Request.");
        }
        return view;
    }

    public ModelAndView getAddViewPage(final HttpServletRequest request, final HttpServletResponse response, final ScriptGenFormBean formBean)
    {
        ModelAndView view = new ModelAndView();
        try
        {
            if (request.getParameter("showTab").equals("Add"))
            {
                view.setViewName("scriptgen/Add");
            }
            else
            {
                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                Date date = new Date();
                String dateString = dateFormat.format(date);
                view.addObject("dateString", dateString);
                view.setViewName("scriptgen/View");
            }
            view.addObject("projectList", scriptGenService.getProjectList());
        }
        catch (Exception exp)
        {
            finutils.errorhandler.ErrorHandler.PrintInFile(exp, request,
                    "Error Report : "
                    + " Date : " + Calendar.getInstance().getTime().toString()
                    + " ID : " + Calendar.getInstance().getTimeInMillis());
            view.setViewName("error");
            view.addObject("error", "Error Processing Request.");
        }
        return view;
    }

    public ModelAndView generateFiles(final HttpServletRequest request, final HttpServletResponse response, final ScriptGenFormBean formBean)
    {
        ModelAndView view = new ModelAndView("scriptgen/scriptgenajax");
        try
        {
            /*------- Edited by Divyang Kankotiya -------- */
            int sno = Integer.parseInt(request.getParameter("srno"));
            String fileName = scriptGenService.generateFiles(sno);
            FolderZipper.zipFolder(TOMCAT_PATH + "/webapps/finstudio/generated/" + fileName, TOMCAT_PATH + "/webapps/finstudio/generated/" + fileName + ".zip");

            view.addObject("classGenerated", "success");
            view.addObject("getzipfile", fileName + ".zip");
            /*------- End Edited by Divyang Kankotiya -------- */
//            view.addObject("javaFileName", fileName + ".java");
//            view.addObject("csvFileName", fileName + ".csv");

        }
        catch (Exception exp)
        {
            finutils.errorhandler.ErrorHandler.PrintInFile(exp, request,
                    "Error Report : "
                    + " Date : " + Calendar.getInstance().getTime().toString()
                    + " ID : " + Calendar.getInstance().getTimeInMillis());
            view.setViewName("error");
            view.addObject("error", "Error Processing Request.");
        }
        return view;
    }
}