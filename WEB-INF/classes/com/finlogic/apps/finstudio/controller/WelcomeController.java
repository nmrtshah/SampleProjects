package com.finlogic.apps.finstudio.controller;

import com.finlogic.apps.finstudio.service.WelcomeService;
import com.finlogic.util.Logger;
import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class WelcomeController extends MultiActionController
{

    public ModelAndView welcome(final HttpServletRequest req, final HttpServletResponse res)
    {
        ModelAndView mav;
        mav = new ModelAndView();
        try
        {
            mav.setViewName("welcome");
        }
        catch (Exception e)
        {
            Logger.ErrorLogger(e);
        }
        return mav;
    }

    public String getMonth(final int idx)
    {
        String[] montharray;
        montharray = new String[]
        {
            "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"
        };
        return montharray[idx];
    }

    public ModelAndView header(final HttpServletRequest req, final HttpServletResponse res)
    {
        ModelAndView mav;
        mav = new ModelAndView();
        try
        {
            mav.setViewName("header");
            Calendar cal;
            cal = Calendar.getInstance();
            String mydate;
            mydate = getMonth(cal.get(Calendar.MONTH)) + " "
                    + cal.get(Calendar.DAY_OF_MONTH) + ","
                    + cal.get(Calendar.YEAR) + " "
                    + cal.get(Calendar.HOUR_OF_DAY) + ":"
                    + cal.get(Calendar.MINUTE) + ":"
                    + cal.get(Calendar.SECOND);
            List ls = new WelcomeService().getStatistics();
            mav.addObject("statistics", ls);
            String finlibPath;
            finlibPath = finpack.FinPack.getProperty("finlib_path");
            mav.addObject("finlibPath", finlibPath);
            mav.addObject("mydate", mydate);
        }
        catch (Exception e)
        {
            Logger.ErrorLogger(e);
        }
        return mav;
    }

    public ModelAndView content(final HttpServletRequest req, final HttpServletResponse res)
    {
        ModelAndView mav;
        mav = new ModelAndView();
        try
        {
            String finlibPath;
            finlibPath = finpack.FinPack.getProperty("finlib_path");
            mav.addObject("finlibPath", finlibPath);
            mav.setViewName("content");
        }
        catch (Exception e)
        {
            Logger.ErrorLogger(e);
        }
        return mav;
    }

    public ModelAndView subMenu(final HttpServletRequest req, final HttpServletResponse res)
    {
        ModelAndView mav;
        mav = new ModelAndView();
        try
        {
            String action;
            action = req.getParameter("action");
            String finlibPath;
            finlibPath = finpack.FinPack.getProperty("finlib_path");
            mav.addObject("finlibPath", finlibPath);
            mav.addObject("action", action);
            mav.setViewName("submenu");
        }
        catch (Exception e)
        {
            Logger.ErrorLogger(e);
        }
        return mav;
    }
}
