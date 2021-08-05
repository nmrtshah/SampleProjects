/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.apps.finstudio.controller;

import com.finlogic.util.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class JQueryDemoController extends MultiActionController
{

    public ModelAndView autoComplete(HttpServletRequest request, HttpServletResponse responce) throws Exception
    {
        ModelAndView mv = new ModelAndView();
        try
        {
            mv.setViewName("jquerydemo");
            mv.addObject("action", "autoComplete");
        }
        catch (Exception e)
        {
            mv.addObject("error", Logger.ErrorLogger(e));
            mv.setViewName("error");
        }
        return mv;
    }

}
