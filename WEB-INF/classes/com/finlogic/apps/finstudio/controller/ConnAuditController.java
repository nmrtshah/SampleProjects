/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.apps.finstudio.controller;

import com.finlogic.apps.finstudio.service.ConnAuditService;
import com.finlogic.apps.finstudio.formbean.ConnAuditFormBean;
import com.finlogic.util.Logger;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class ConnAuditController extends MultiActionController
{

    String finlibPath = finpack.FinPack.getProperty("finlib_path");

    public ModelAndView connAuditMenu(HttpServletRequest request, HttpServletResponse responce) throws Exception
    {
        ModelAndView mv = new ModelAndView();
        try
        {

            mv.addObject("finlibPath", finlibPath);
            mv.addObject("action", "connAuditMenu");
            mv.setViewName("connauditreport");
        }
        catch (Exception e)
        {
            mv.addObject("error", Logger.ErrorLogger(e));
            mv.setViewName("error");
        }
        return mv;
    }

    public ModelAndView connAuditReport(HttpServletRequest request, HttpServletResponse responce, ConnAuditFormBean conn_audit_frmbn) throws Exception
    {
        ModelAndView mv = new ModelAndView();
        try
        {
            mv.addObject("finlibPath", finlibPath);
            mv.addObject("action", "connAuditReport");
            ConnAuditService conService = new ConnAuditService();
            mv.addObject("json", conService.getData(conn_audit_frmbn));
            mv.setViewName("connauditreport");
        }
        catch (Exception e)
        {
            mv.addObject("error", Logger.ErrorLogger(e));
            mv.setViewName("error");
        }
        return mv;
    }

    public ModelAndView connAuditTimeReport(HttpServletRequest request, HttpServletResponse responce) throws Exception
    {
        ModelAndView mv = new ModelAndView();
        try
        {
            mv.addObject("finlibPath", finlibPath);
            mv.addObject("action", "connAuditTimeReport");
            ConnAuditService conService = new ConnAuditService();
            mv.addObject("json", conService.getTimeData(request.getParameter("hourMin")));
            mv.addObject("dt", request.getParameter("date1"));
            mv.setViewName("connauditreport");
        }
        catch (Exception e)
        {
            mv.addObject("error", Logger.ErrorLogger(e));
            mv.setViewName("error");
        }
        return mv;
    }

    public ModelAndView chartMainMenu(HttpServletRequest request, HttpServletResponse responce) throws Exception
    {
        ConnAuditService conService = new ConnAuditService();
        ModelAndView mv = new ModelAndView();
        try
        {
            List prjList = conService.getProjects(null);

            mv.addObject("prjList", prjList);
            mv.addObject("action", "ChartMainMenu");
            mv.setViewName("connAuditChart");
        }
        catch (Exception e)
        {
            mv.addObject("error", Logger.ErrorLogger(e));
            mv.setViewName("error");
        }
        return mv;
    }

    public ModelAndView chartData(HttpServletRequest request, HttpServletResponse responce) throws Exception
    {
        ConnAuditService conService = new ConnAuditService();
        ModelAndView mv = new ModelAndView();
        try
        {
            String[] prjNames = request.getParameterValues("prjList");

            String formDate = request.getParameter("datePicker");
            List prjList = new ArrayList();
            List dateList = new ArrayList();
            List leakList = new ArrayList();
            for (int i = 0; i < prjNames.length; i++)
            {
                prjList.add(prjNames[i]);
            }
            conService.getChartData(prjList, dateList, leakList, formDate);

            mv.addObject("prjList", prjList);
            mv.addObject("dateList", dateList);
            mv.addObject("leakList", leakList);
            mv.addObject("action", "drawChart");
            mv.setViewName("connAuditChart");
        }
        catch (Exception e)
        {
            mv.addObject("error", Logger.ErrorLogger(e));
            mv.setViewName("error");
        }
        return mv;
    }

    public ModelAndView defaultMethod(HttpServletRequest request, HttpServletResponse responce) throws Exception
    {
        return connAuditMenu(request, responce); 
    }
}
