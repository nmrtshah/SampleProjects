/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.apps.finstudio.controller;

import com.finlogic.apps.finstudio.formbean.DataExportFormbean;
import com.finlogic.apps.finstudio.service.DataExportService;
import com.finlogic.apps.finstudio.service.DataImportService;
import com.finlogic.util.Logger;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 *
 * @author njuser
 */
public class DataExportController extends MultiActionController
{
    DataExportService service=new DataExportService();
    String finlibPath = finpack.FinPack.getProperty("finlib_path");
    public ModelAndView selectTable(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        ModelAndView mv = new ModelAndView();
        try
        {
            mv.addObject("finlibPath", finlibPath);
            mv.addObject("process", "selectTable");
            mv.setViewName("dataexport");
        }
        catch (Exception e)
        {
            mv.addObject("error", Logger.ErrorLogger(e));
            mv.setViewName("error");
        }
        return mv;
    }
    public ModelAndView submitQuery(HttpServletRequest request, HttpServletResponse response,DataExportFormbean defb) throws Exception
    {
        ModelAndView mv = new ModelAndView();
        try
        {
            String message=service.submitQuery(defb);
            mv.addObject("finlibPath", finlibPath);
            mv.addObject("filename", defb.getFilenm());
            mv.addObject("message", message);
            mv.addObject("process", "message");
            mv.setViewName("dataexport");
        }
        catch (Exception e)
        {
            mv.addObject("error", Logger.ErrorLogger(e));
            mv.setViewName("error");
        }
        return mv;
    }

}
