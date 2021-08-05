package com.finlogic.apps.finstudio.filedirautorequest;

import com.finlogic.util.Logger;
import com.finlogic.util.disposition.ContentDisposition;
import com.finlogic.util.disposition.ContentDispositionType;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 *
 * @author njuser
 */
public class FileDirAutoRequestController extends MultiActionController
{

    private final FileDirAutoRequestService service = new FileDirAutoRequestService();
    private final String aclEmpCode = "ACLEmpCode";
    private final String emp_name = "emp_name";
    private ContentDisposition disposition= new ContentDisposition();
    private final String tempFilesPath = finpack.FinPack.getProperty("tempfiles_path");

    
    public ModelAndView getMainPage(final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {
        ModelAndView mav;
        mav = new ModelAndView();
        String finlibPath = finpack.FinPack.getProperty("finlib_path");
        mav.addObject("finlib_path", finlibPath);
        mav.addObject("cur_date", new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime()));
        mav.addObject("projectListForReport", service.getWFMProjectList(1));
        mav.setViewName("filedirautorequest/Main");
        return mav;
    }

    public ModelAndView addLoader(final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {
        ModelAndView mav;
        mav = new ModelAndView();
        
        String finlibPath = finpack.FinPack.getProperty("finlib_path");
        mav.addObject("finlib_path", finlibPath);
        mav.addObject("action", "addLoader");
        mav.addObject("projectList", service.getWFMProjectList(1));
        String empCode = request.getSession().getAttribute(aclEmpCode).toString();
        //mav.addObject("desigCode", service.getDesigCode(empCode));
        mav.setViewName("filedirautorequest/Add");
        return mav;
    }

    public ModelAndView reportLoader(final HttpServletRequest request, final HttpServletResponse response, final FileDirAutoRequestBean formBean)
    {
        ModelAndView mav = new ModelAndView();
        String finlibPath = finpack.FinPack.getProperty("finlib_path");
        mav.addObject("finlib_path", finlibPath);
        try {
            String empCode = request.getSession().getAttribute(aclEmpCode).toString();
            formBean.setRpt_empcode(empCode);
            mav.addObject("jsonString", service.reportLoader(formBean));
            mav.setViewName("filedirautorequest/Report");
        }
        catch(Exception e) {
            Logger.ErrorLogger(e);
            mav.addObject("error", e.getMessage());
            mav.setViewName("filedirautorequest/Error");
        }
        return mav;
    }

    public ModelAndView authorizeMainPage(final HttpServletRequest request, final HttpServletResponse response) throws ClassNotFoundException, SQLException
    {
        ModelAndView mav;
        mav = new ModelAndView();
        String finlibPath = finpack.FinPack.getProperty("finlib_path");
        mav.addObject("finlib_path", finlibPath);
        if (request.getRequestURI().contains("filedirarexe.fin"))
        {
            mav.addObject("action", "authorizeMainPage");
        }
        else
        {
            mav.addObject("update_msg", "Access Denied..!!");
        }
        mav.setViewName("filedirautorequest/Authorize");
        return mav;
    }

    public ModelAndView authorizeLoader(final HttpServletRequest request, final HttpServletResponse response) throws ClassNotFoundException, SQLException
    {
        ModelAndView mav;
        mav = new ModelAndView();
        String finlibPath = finpack.FinPack.getProperty("finlib_path");
        mav.addObject("finlib_path", finlibPath);
        if (request.getRequestURI().contains("filedirarexe.fin"))
        {
            mav.addObject("action", "authorizeLoader");
            mav.addObject("jsonString", service.authorizeLoader());
        }
        else
        {
            mav.addObject("update_msg", "Access Denied");
        }
        mav.setViewName("filedirautorequest/Report");
        return mav;
    }

    public ModelAndView authorizeUpdate(final HttpServletRequest request, final HttpServletResponse response) throws ClassNotFoundException, SQLException
    {
        ModelAndView mav;
        mav = new ModelAndView();
        String finlibPath = finpack.FinPack.getProperty("finlib_path");
        mav.addObject("finlib_path", finlibPath);

        if (request.getRequestURI().contains("filedirarexe.fin"))
        {
            mav.addObject("action", "authorizeUpdate");
            mav.addObject("update_msg", service.authorizeUpdate(request.getParameter("idVal")) + " record(s) Authorized");
        }
        else
        {
            mav.addObject("update_msg", "Access Denied");
        }

        mav.setViewName("filedirautorequest/Authorize");
        return mav;
    }

    public ModelAndView rejectUpdate(final HttpServletRequest request, final HttpServletResponse response) throws ClassNotFoundException, SQLException
    {
        ModelAndView mav;
        mav = new ModelAndView();
        String finlibPath = finpack.FinPack.getProperty("finlib_path");
        mav.addObject("finlib_path", finlibPath);

        if (request.getRequestURI().contains("filedirarexe.fin"))
        {
            mav.addObject("action", "rejectUpdate");
            mav.addObject("reject_msg", service.rejectUpdate(request.getParameter("idVal"), request.getParameter("txtVal")) + " record(s) Rejected");
        }
        else
        {
            mav.addObject("update_msg", "Access Denied");
        }
        
        mav.setViewName("filedirautorequest/Authorize");
        return mav;
    }

    public ModelAndView insertData(final HttpServletRequest request, final HttpServletResponse response, final FileDirAutoRequestBean formBean)
    {
        ModelAndView mav = new ModelAndView();
        try {
            String finlibPath = finpack.FinPack.getProperty("finlib_path");
            mav.addObject("finlib_path", finlibPath);

            String empCode = request.getSession().getAttribute(aclEmpCode).toString();
            String empName = request.getSession().getAttribute(emp_name).toString();

            if (service.insertData(formBean, empCode, empName) > 0)
            {
                mav.addObject("msg", "Request is inserted successfully");
                mav.setViewName("filedirautorequest/Add");
            }
            mav.addObject("action", "insertData");
        }
        catch(Exception ex) {
            Logger.DataLogger("FileDirAutoRequestController | insertData Error : " + ex.toString());
        }

        return mav;
    }

    public void provideFile(HttpServletRequest request, HttpServletResponse response)
    {
        try
        {           
            StringBuilder tempCachePath = new StringBuilder(tempFilesPath);
            disposition.setFilePath(tempCachePath.append("cache/").append(request.getParameter("outPutFile")).toString());
            disposition.setRequestType(ContentDispositionType.ATTACHMENT);
            disposition.setResponse(response);
            disposition.process();     
        }
        catch (Exception ex)
        {
            Logger.DataLogger("Error : " + ex.toString());
        }
    }
    
    public ModelAndView provideGetListFile(HttpServletRequest request, HttpServletResponse response)
    {
        ModelAndView mv = new ModelAndView("filedirautorequest/getlistgrid");
        try {
            mv.addObject("filePath", request.getParameter("outPutFile"));
            mv.addObject("finlib_path", finpack.FinPack.getProperty("finlib_path"));
        }
        catch(Exception e) {
            Logger.DataLogger("FileDirAutoRequestController Error : " + e.toString());
        }
        return mv;
    }
}