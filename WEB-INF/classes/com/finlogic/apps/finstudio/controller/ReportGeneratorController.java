/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.apps.finstudio.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.finlogic.apps.finstudio.formbean.ReportGeneratorFormBean;
import com.finlogic.apps.finstudio.service.ReportGeneratorService;
import com.finlogic.util.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 *
 * @author njuser
 */
public class ReportGeneratorController extends MultiActionController
{

    public ModelAndView createMenu(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        ModelAndView mv = new ModelAndView();
        try
        {
            String finlibPath = finpack.FinPack.getProperty("finlib_path");
            mv.addObject("finlibPath", finlibPath);
            mv.addObject("process", "createMenu");
            mv.addObject("ACLEmpCode", request.getSession().getAttribute("ACLEmpCode"));
            mv.setViewName("reportgenerator");

        }
        catch (Exception ex)
        {
            mv.setViewName("error");
            mv.addObject("error", Logger.ErrorLogger(ex));
        }

        return mv;
    }
    /*
    public ModelAndView validateMenu(HttpServletRequest request, HttpServletResponse response, ReportGeneratorFormBean rgfBean) throws Exception
    {
    ModelAndView mv = new ModelAndView();
    try
    {
    mv.addObject("process", "validateMenu");
    ReportGeneratorService reportGeneratorService = new ReportGeneratorService();
    String msg = reportGeneratorService.validate(rgfBean);
    mv.addObject("message", msg);

    if (msg.contains("Invalid"))
    {
    mv.addObject("process", "validateMenu");
    }
    else
    {
    mv.addObject("validation", "true");
    }

    mv.setViewName("reportgenerator");
    }
    catch (Exception ex)
    {
    mv.setViewName("error");
    mv.addObject("error", Logger.ErrorLogger(ex));
    }

    return mv;
    }
     */

    public ModelAndView specifications(HttpServletRequest request, HttpServletResponse response, ReportGeneratorFormBean rgfBean) throws Exception
    {
        ModelAndView mv = new ModelAndView();
        try
        {
            ReportGeneratorService reportGeneratorService = new ReportGeneratorService();
            String msg = reportGeneratorService.validate(rgfBean);

            if (msg.contains("Invalid"))
            {
                mv.addObject("process", "validateMenu");
                mv.addObject("message", msg);
            }
            else
            {
                mv.addObject("process", "specifications");
                reportGeneratorService = new ReportGeneratorService();
                mv.addObject("columnnames", reportGeneratorService.getColumnNames(rgfBean));
            }
            mv.setViewName("reportgenerator");

        }
        catch (Exception ex)
        {
            mv.setViewName("error");
            mv.addObject("error", Logger.ErrorLogger(ex));
        }

        return mv;
    }

    public ModelAndView view(HttpServletRequest request, HttpServletResponse response, ReportGeneratorFormBean rgfBean) throws Exception
    {
        ModelAndView mv = new ModelAndView();
        try
        {
            mv.addObject("process", "view");

            ReportGeneratorService reportGeneratorService = new ReportGeneratorService();

            mv.addObject("columnnames", reportGeneratorService.getColumnNames(rgfBean));

            mv.setViewName("reportgenerator");

        }
        catch (Exception ex)
        {
            mv.setViewName("error");
            mv.addObject("error", Logger.ErrorLogger(ex));
        }

        return mv;
    }

    public ModelAndView generateReport(HttpServletRequest request, HttpServletResponse responce, ReportGeneratorFormBean rgfBean) throws Exception
    {
        ModelAndView mv = new ModelAndView();
        try
        {
            mv.addObject("process", "generateReport");

            ReportGeneratorService reportGeneratorService = new ReportGeneratorService();

            mv.addObject("reportNo", reportGeneratorService.insertReportGenerator(rgfBean));

            mv.setViewName("reportgenerator");
        }
        catch (Exception ex)
        {
            mv.setViewName("error");
            mv.addObject("error", Logger.ErrorLogger(ex));
        }

        return mv;
    }
}
