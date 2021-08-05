/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.apps.finstudio.controller;

import com.finlogic.apps.finstudio.formbean.MasterGeneratorFormBean;
import com.finlogic.apps.finstudio.service.MasterGeneratorService;
import com.finlogic.util.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 *
 * @author njuser
 */
public class MasterGeneratorController extends MultiActionController
{

    public ModelAndView menu(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        ModelAndView mv = new ModelAndView();
        try
        {
            String finlibPath = finpack.FinPack.getProperty("finlib_path");
            mv.addObject("finlibPath", finlibPath);
            mv.addObject("process", "menu");
            mv.addObject("ACLEmpCode", request.getSession().getAttribute("ACLEmpCode"));
            mv.setViewName("mastergenerator");

        }
        catch (Exception ex)
        {
            mv.setViewName("error");
            mv.addObject("error", Logger.ErrorLogger(ex));
        }
        return mv;
    }

    public ModelAndView validateAlias(HttpServletRequest request, HttpServletResponse response, MasterGeneratorFormBean rgfBean) throws Exception
    {
        ModelAndView mv = new ModelAndView();
        try
        {
            mv.addObject("process", "validate");
            MasterGeneratorService service = new MasterGeneratorService();
            String msg = service.validate(rgfBean);
            mv.addObject("message", msg);

            if (msg.contains("InvalidAlias"))
            {
                mv.addObject("validation", "InvalidAlias");
            }
            else
            {
                mv.addObject("aliasName", rgfBean.getAliasName());
            }
        }
        catch (Exception ex)
        {
            mv.setViewName("error");
            mv.addObject("error", Logger.ErrorLogger(ex));
        }
        mv.setViewName("mastergenerator");
        return mv;
    }

    public ModelAndView getTableNames(HttpServletRequest request, HttpServletResponse response, MasterGeneratorFormBean rgfBean) throws Exception
    {
        ModelAndView mv = new ModelAndView();
        try
        {

            MasterGeneratorService service = new MasterGeneratorService();
            mv.addObject("process", "getTableNames");
            mv.addObject("tableNames", service.getTableNames(rgfBean));
        }
        catch (Exception ex)
        {
            mv.setViewName("error");
            mv.addObject("error", Logger.ErrorLogger(ex));
        }
        mv.setViewName("mastergenerator");
        return mv;
    }

    public ModelAndView showSpecifications(HttpServletRequest request, HttpServletResponse response, MasterGeneratorFormBean rgfBean) throws Exception
    {
        ModelAndView mv = new ModelAndView();
        try
        {
            mv.addObject("process", "specifications");
            MasterGeneratorService service = new MasterGeneratorService();
            mv.addObject("columnNames", service.getColumnNamesOfTable(rgfBean));
        }
        catch (Exception ex)
        {
            mv.setViewName("error");
            mv.addObject("error", Logger.ErrorLogger(ex));
        }
        mv.setViewName("mastergenerator");
        return mv;
    }

    public ModelAndView generateMaster(HttpServletRequest request, HttpServletResponse response, MasterGeneratorFormBean rgfBean) throws Exception
    {
        ModelAndView mv = new ModelAndView();
        try
        {
            mv.addObject("process", "generateMaster");
            MasterGeneratorService service = new MasterGeneratorService();
            mv.addObject("moduleNo", service.generateMaster(rgfBean));
            mv.setViewName("mastergenerator");
        }
        catch (Exception ex)
        {
            mv.setViewName("error");
            mv.addObject("error", Logger.ErrorLogger(ex));
        }
        return mv;
    }
}
