/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.apps.finstudio.ETLJobList;

import com.finlogic.util.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 *
 * @author Siddharth Patel
 */
public class ETLJobListController extends MultiActionController
{

    public ModelAndView getMenu(HttpServletRequest request, HttpServletResponse response)
    {
        ModelAndView mv = new ModelAndView();
        try
        {
            mv.addObject("finlibPath", finpack.FinPack.getProperty("finlib_path"));
            if (request.getParameter("page").equals("report"))
            {
                mv.setViewName("etljoblist/etljobreport");
            }
            else
            {
                mv.setViewName("etljoblist/etljobmaster");
            }
        }
        catch (Exception e)
        {
            mv.addObject("error", Logger.ErrorLogger(e) + e.getMessage());
            mv.setViewName("etljoblist/error");
        }
        return mv;
    }

    public ModelAndView viewReport(HttpServletRequest request, HttpServletResponse response)
    {
        ETLJobListService service = new ETLJobListService();
        ModelAndView mv = new ModelAndView("etljoblist/ajaxetljob");
        try
        {
            mv.addObject("action", "getReport");
            mv.addObject("data", service.getGirdData());
        }
        catch (Exception e)
        {
            mv.addObject("error", Logger.ErrorLogger(e) + e.getMessage());
            mv.setViewName("etljoblist/error");
        }
        return mv;
    }

    public synchronized ModelAndView insertETLJob(final HttpServletRequest request, final HttpServletResponse response, final ETLJobListFormBean formBean)
    {
        ModelAndView mv = new ModelAndView("etljoblist/ajaxetljob");
        ETLJobListService service = new ETLJobListService();
        try
        {
            mv.addObject("action", "addETL");
            int result = service.insertETLJob(formBean);
            if (result > 0)
            {
                mv.addObject("status", "true");
            }
        }
        catch (Exception e)
        {
            mv.addObject("error", Logger.ErrorLogger(e) + e.getMessage());
            mv.setViewName("etljoblist/error");
        }
        return mv;
    }
}
