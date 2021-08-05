/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.apps.finstudio.controller;

import com.finlogic.apps.finstudio.formbean.MasterGeneratorV2FormBean;
import com.finlogic.apps.finstudio.service.MasterGeneratorV2Service;
import com.finlogic.util.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 *
 * @author njuser
 */
public class MasterGeneratorV2Controller extends MultiActionController
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
            mv.setViewName("mastergeneratorV2");
        }
        catch (Exception ex)
        {
            mv.setViewName("error");
            mv.addObject("error", Logger.ErrorLogger(ex));
        }
        return mv;
    }

    public ModelAndView validateAlias(HttpServletRequest request, HttpServletResponse response, MasterGeneratorV2FormBean rgfBean) throws Exception
    {
        ModelAndView mv = new ModelAndView();
        try
        {
            mv.addObject("process", "validate");
            MasterGeneratorV2Service service = new MasterGeneratorV2Service();
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
        mv.setViewName("mastergeneratorV2");
        return mv;
    }

    public ModelAndView getMasterTableNames(HttpServletRequest request, HttpServletResponse response, MasterGeneratorV2FormBean rgfBean) throws Exception
    {
        ModelAndView mv = new ModelAndView();
        try
        {
            MasterGeneratorV2Service service = new MasterGeneratorV2Service();
            mv.addObject("process", "getMasterTableNames");
            mv.addObject("masterTableNames", service.getTableNames(rgfBean, "master"));
        }
        catch (Exception ex)
        {
            mv.setViewName("error");
            mv.addObject("error", Logger.ErrorLogger(ex));
        }
        mv.setViewName("mastergeneratorV2");
        return mv;
    }

    public ModelAndView getDetailTableNames(HttpServletRequest request, HttpServletResponse response, MasterGeneratorV2FormBean rgfBean) throws Exception
    {
        ModelAndView mv = new ModelAndView();
        try
        {
            MasterGeneratorV2Service service = new MasterGeneratorV2Service();
            mv.addObject("process", "getDetailTableNames");
            mv.addObject("detailTableNames", service.getTableNames(rgfBean, "detail"));
        }
        catch (Exception ex)
        {
            mv.setViewName("error");
            mv.addObject("error", Logger.ErrorLogger(ex));
        }
        mv.setViewName("mastergeneratorV2");
        return mv;
    }

    public ModelAndView showSpecification(HttpServletRequest request, HttpServletResponse response, MasterGeneratorV2FormBean rgfBean) throws Exception
    {
        ModelAndView mv = new ModelAndView();
        try
        {
            mv.addObject("process", "showSpecification");
            MasterGeneratorV2Service service = new MasterGeneratorV2Service();
            mv.addObject("columnNamesMaster", service.getColumnNamesOfTable(rgfBean, "master"));
            mv.addObject("columnNamesDetail", service.getColumnNamesOfTable(rgfBean, "detail"));
        }
        catch (Exception ex)
        {
            mv.setViewName("error");
            mv.addObject("error", Logger.ErrorLogger(ex));
        }
        mv.setViewName("mastergeneratorV2");
        return mv;
    }

    public ModelAndView generateMaster(HttpServletRequest request, HttpServletResponse response, MasterGeneratorV2FormBean formBean) throws Exception
    {
        ModelAndView mv = new ModelAndView();
        try
        {
            mv.addObject("process", "generateMasterV2");
            MasterGeneratorV2Service service = new MasterGeneratorV2Service();
            mv.addObject("moduleNo",service.generateMaster(formBean));
        }
        catch (Exception ex)
        {
            mv.setViewName("error");
            mv.addObject("error", Logger.ErrorLogger(ex));
        }
        mv.setViewName("mastergeneratorV2");
        return mv;
    }
}
