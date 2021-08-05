/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.apps.finstudio.blockmodules;

import com.finlogic.util.Logger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 *
 * @author Jigna Patel
 */
public class BlockModulesController extends MultiActionController 
{
    
    private final BlockModulesService service = new BlockModulesService();
    private final String emp_name = "username";

    public ModelAndView showMenu(final HttpServletRequest request, final HttpServletResponse response) 
    {
        ModelAndView mv = new ModelAndView("blockmodules/Main");
        try
        {
            String finlibPath = finpack.FinPack.getProperty("finlib_path");
            mv.addObject("finlib_path", finlibPath);
        }
        catch (Exception e)
        {
            Logger.ErrorLogger(e);
            mv.addObject("error", e.getMessage());
            mv.setViewName("blockmodules/Error");
            finutils.errorhandler.ErrorHandler.PrintInFile(e, request);
        }
        return mv;
    }
    
    public ModelAndView getAddTab(final HttpServletRequest request, final HttpServletResponse response) 
    {
        ModelAndView mv = new ModelAndView("blockmodules/TabView");
        try
        {
            mv.addObject("action","addTab");
        }
        catch (Exception e)
        {
            Logger.ErrorLogger(e);
            mv.addObject("error", e.getMessage());
            mv.setViewName("blockmodules/Error");
            finutils.errorhandler.ErrorHandler.PrintInFile(e, request);
        }
        return mv;
    }
    
    public ModelAndView getReportTab(final HttpServletRequest request, final HttpServletResponse response) 
    {
        ModelAndView mv = new ModelAndView("blockmodules/TabView");
        try
        {
            mv.addObject("action","reportTab");
            mv.addObject("userNameList",service.getUserNameList());
            DateFormat dateFormat;
            dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date date = new Date();
            mv.addObject("none", dateFormat.format(date));
        }
        catch (Exception e)
        {
            Logger.ErrorLogger(e);
            mv.addObject("error", e.getMessage());
            mv.setViewName("blockmodules/Error");
            finutils.errorhandler.ErrorHandler.PrintInFile(e, request);
        }
        return mv;
    }
    
    public ModelAndView getReportData(final HttpServletRequest request, final HttpServletResponse response,BlockModulesFormBean bmfb) 
    {
        ModelAndView mv = new ModelAndView("blockmodules/TabView");
        try
        {
            mv.addObject("action","reportTabJson");
            mv.addObject("reportJson",service.getReportTabData(bmfb));
        }
        catch (Exception e)
        {
            Logger.ErrorLogger(e);
            mv.addObject("error", e.getMessage());
            mv.setViewName("blockmodules/Error");
            finutils.errorhandler.ErrorHandler.PrintInFile(e, request);
        }
        return mv;
    }
    
    public ModelAndView getAuthoriseTab(final HttpServletRequest request, final HttpServletResponse response) 
    {
        ModelAndView mv = new ModelAndView("blockmodules/TabView");
        try
        {
            mv.addObject("action","authoriseTab");
        }
        catch (Exception e)
        {
            Logger.ErrorLogger(e);
            mv.addObject("error", e.getMessage());
            mv.setViewName("blockmodules/Error");
            finutils.errorhandler.ErrorHandler.PrintInFile(e, request);
        }
        return mv;
    }
    
    public ModelAndView getAuthoriseData(final HttpServletRequest request, final HttpServletResponse response,BlockModulesFormBean bmfb) 
    {
        ModelAndView mv = new ModelAndView("blockmodules/TabView");
        try
        {
            mv.addObject("action","authoriseTabJson");
            mv.addObject("reportJson",service.getAuthoriseTabData(bmfb));
        }
        catch (Exception e)
        {
            Logger.ErrorLogger(e);
            mv.addObject("error", e.getMessage());
            mv.setViewName("blockmodules/Error");
            finutils.errorhandler.ErrorHandler.PrintInFile(e, request);
        }
        return mv;
    }
    
    public ModelAndView insertBlockEntry(final HttpServletRequest request, final HttpServletResponse response,BlockModulesFormBean bmfb) 
    {
        ModelAndView mv = new ModelAndView("blockmodules/TabView");
        mv.addObject("action", "insertEntry");
        try
        {
            String empName = request.getSession().getAttribute(emp_name).toString();
            bmfb.setUserName(empName);
            if(service.insertBlockEntry(bmfb) > 0)
            {
                mv.addObject("DBopration", "success");
            }
            else
            {
                mv.addObject("DBopration", "failure");
            }
        }
        catch (Exception e)
        {
            Logger.ErrorLogger(e);
            mv.addObject("error", e.getMessage());
            mv.setViewName("blockmodules/Error");
            finutils.errorhandler.ErrorHandler.PrintInFile(e, request);
        }
        return mv;
    }
    
    public ModelAndView updateBlockEntry(final HttpServletRequest request, final HttpServletResponse response,BlockModulesFormBean bmfb) 
    {
        ModelAndView mv = new ModelAndView("blockmodules/TabView");
        mv.addObject("action", "updateEntry");
        try
        {
            if(service.updateBlockEntry(bmfb.getHdnUnBlockedId()) > 0)
            {
                mv.addObject("DBopration", "success");
            }
            else
            {
                mv.addObject("DBopration", "failure");
            }
        }
        catch (Exception e)
        {
            Logger.ErrorLogger(e);
            mv.addObject("error", e.getMessage());
            mv.setViewName("blockmodules/Error");
            finutils.errorhandler.ErrorHandler.PrintInFile(e, request);
        }
        return mv;
    }
    
    public ModelAndView updateAuthStatus(final HttpServletRequest request, final HttpServletResponse response,BlockModulesFormBean bmfb) 
    {
        ModelAndView mv = new ModelAndView("blockmodules/TabView");
        mv.addObject("action", "updateEntry");
        try
        {
            if(service.updateAuthStatus(bmfb.getHdnUnBlockedId()) > 0)
            {
                mv.addObject("DBopration", "success");
            }
            else
            {
                mv.addObject("DBopration", "failure");
            }
        }
        catch (Exception e)
        {
            Logger.ErrorLogger(e);
            mv.addObject("error", e.getMessage());
            mv.setViewName("blockmodules/Error");
            finutils.errorhandler.ErrorHandler.PrintInFile(e, request);
        }
        return mv;
    }
    
    public ModelAndView updateRejectStatus(final HttpServletRequest request, final HttpServletResponse response,BlockModulesFormBean bmfb) 
    {
        ModelAndView mv = new ModelAndView("blockmodules/TabView");
        mv.addObject("action", "updateEntry");
        try
        {
            if(service.updateRejectStatus(bmfb.getHdnUnBlockedId(), bmfb.getHdnRemarks()) > 0)
            {
                mv.addObject("DBopration", "success");
            }
            else
            {
                mv.addObject("DBopration", "failure");
            }
        }
        catch (Exception e)
        {
            Logger.ErrorLogger(e);
            mv.addObject("error", e.getMessage());
            mv.setViewName("blockmodules/Error");
            finutils.errorhandler.ErrorHandler.PrintInFile(e, request);
        }
        return mv;
    }
    
    public ModelAndView blockEntry(final HttpServletRequest request, final HttpServletResponse response,BlockModulesFormBean bmfb)
    {
        ModelAndView mv = new ModelAndView("blockmodules/TabView");
        mv.addObject("action", "updateEntry");
        try
        {
            if(service.setBlockEntry() > 0)
            {
                mv.addObject("DBopration", "success");
            }
            else
            {
                mv.addObject("DBopration", "failure");
            }
        }
        catch (Exception e)
        {
            Logger.ErrorLogger(e);
            mv.addObject("error", e.getMessage());
            mv.setViewName("blockmodules/Error");
            finutils.errorhandler.ErrorHandler.PrintInFile(e, request);
        }
        return mv;
    }
    
    private int getNoOfDays(String month) 
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, Integer.parseInt(month) - 1);
        int days = calendar.getActualMaximum(Calendar.DATE);
        return days;
    }
    
    public ModelAndView getFromMonthwiseDays(final HttpServletRequest request, final HttpServletResponse response, BlockModulesFormBean bmfb) 
    {
        ModelAndView mv = new ModelAndView("blockmodules/TabView");
        try
        {
            mv.addObject("action", "monthwiseDays");
            mv.addObject("daysCount", getNoOfDays(bmfb.getMonthFromDate()));
        }
        catch (Exception e)
        {
            Logger.ErrorLogger(e);
            mv.addObject("error", e.getMessage());
            mv.setViewName("blockmodules/Error");
            finutils.errorhandler.ErrorHandler.PrintInFile(e, request);
        }
        return mv;
    }
    
    public ModelAndView getToMonthwiseDays(final HttpServletRequest request, final HttpServletResponse response, BlockModulesFormBean bmfb) 
    {
        ModelAndView mv = new ModelAndView("blockmodules/TabView");
        try
        {
            mv.addObject("action", "monthwiseDays");
            mv.addObject("daysCount", getNoOfDays(bmfb.getMonthToDate()));
        }
        catch (Exception e)
        {
            Logger.ErrorLogger(e);
            mv.addObject("error", e.getMessage());
            mv.setViewName("blockmodules/Error");
            finutils.errorhandler.ErrorHandler.PrintInFile(e, request);
        }
        return mv;
    }
}