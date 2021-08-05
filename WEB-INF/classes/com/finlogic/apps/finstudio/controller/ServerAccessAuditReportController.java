package com.finlogic.apps.finstudio.controller;

import javax.servlet.http.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import com.finlogic.apps.finstudio.formbean.ServerAccessAuditReportFormBean;
import com.finlogic.apps.finstudio.service.ServerAccessAuditReportService;

public class ServerAccessAuditReportController extends MultiActionController
{
    String finlibPath = finpack.FinPack.getProperty("finlib_path");
    public ModelAndView getMenu(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        ModelAndView mv = new ModelAndView();
        mv.addObject("finlibPath", finlibPath);
        mv.addObject("process", "getmenu");
        mv.setViewName("serverAccessAuditReport");
        return mv;
    }

    public ModelAndView getRequestURLWiseReport(HttpServletRequest request, HttpServletResponse response, ServerAccessAuditReportFormBean formBean) throws Exception
    {
        ModelAndView mv = new ModelAndView();
        ServerAccessAuditReportService service = new ServerAccessAuditReportService();
        mv.addObject("finlibPath", finlibPath);
        mv.addObject("process", "getreportGrid");
        mv.addObject("json", service.getRequestURLWiseReport(formBean));
        mv.setViewName("serverAccessAuditReport");
        return mv;
    }

    public ModelAndView getProjectWiseReport(HttpServletRequest request, HttpServletResponse response, ServerAccessAuditReportFormBean formBean) throws Exception
    {
        ModelAndView mv = new ModelAndView();
        ServerAccessAuditReportService service = new ServerAccessAuditReportService();
        mv.addObject("finlibPath", finlibPath);
        mv.addObject("process", "getreportGrid");
        mv.addObject("json", service.getProjectWiseReport(formBean));
        mv.setViewName("serverAccessAuditReport");
        return mv;
    }
    public ModelAndView getRequestURLReport(HttpServletRequest request, HttpServletResponse response, ServerAccessAuditReportFormBean formBean) throws Exception
    {
        ModelAndView mv = new ModelAndView();
        ServerAccessAuditReportService service = new ServerAccessAuditReportService();
        mv.addObject("finlibPath", finlibPath);
        mv.addObject("process", "getreportGrid");
        mv.addObject("json", service.getRequestURLReport(formBean));
        mv.setViewName("serverAccessAuditReport");
        return mv;
    }

}
