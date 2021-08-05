/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.apps.finstudio.controller;

import com.finlogic.apps.finstudio.formbean.DataImportFormBean;
import com.finlogic.apps.finstudio.service.DataImportService;
import com.finlogic.business.finstudio.model.DataImportModel;
import com.finlogic.util.Logger;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class DataImportController extends MultiActionController
{
    String finlibPath = finpack.FinPack.getProperty("finlib_path");
    public ModelAndView selectFile(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        ModelAndView mv = new ModelAndView();
        try
        {
            mv.addObject("finlibPath", finlibPath);
            mv.addObject("process", "selectFile");
            mv.setViewName("dataimport");
        } catch (Exception e)
        {
            mv.addObject("error", Logger.ErrorLogger(e));
            mv.setViewName("error");
        }
        return mv;
    }

    public ModelAndView selectTable(HttpServletRequest request, HttpServletResponse response, DataImportFormBean data_import_frmbn) throws Exception
    {
        ModelAndView mv = new ModelAndView();

        try
        {
            DataImportModel md = new DataImportModel();
            DataImportService ser = new DataImportService();
            String fnm = null;
            mv.addObject("process", "selectTable");
            fnm = md.uploadFile(data_import_frmbn);
            String[] array_fields=null;
            if((fnm.substring(fnm.length()-4).toLowerCase()).equals(".csv"))
            {
                array_fields = md.getCsvHeader(fnm, data_import_frmbn);
            }
            else if((fnm.substring(fnm.length()-4).toLowerCase()).equals(".xls"))
            {
                array_fields=md.getXlsHeader(fnm, data_import_frmbn);
            }
            else if((fnm.substring(fnm.length()-4).toLowerCase()).equals(".dbf"))
            {
                array_fields=md.getDbfHeader(fnm, data_import_frmbn);
            }
            mv.addObject("finlibPath", finlibPath);
            mv.addObject("FileName", fnm);
            mv.addObject("header1", data_import_frmbn.getHeader());
            mv.addObject("fields", array_fields);
            mv.setViewName("dataimport");
        } catch (Exception e)
        {
            mv.addObject("error",Logger.ErrorLogger(e));
            mv.setViewName("error");
        }
        return mv;
    }

    public ModelAndView fillTable(HttpServletRequest request, HttpServletResponse response, DataImportFormBean data_import_frmbn) throws Exception
    {
        ModelAndView mv = new ModelAndView();

        try
        {
            DataImportService ser = new DataImportService();
            List tbl_list = null;
            mv.addObject("process", "fillTable");
            tbl_list = ser.getTables(data_import_frmbn);
            mv.addObject("finlibPath", finlibPath);
            mv.addObject("table_list", tbl_list);
            mv.setViewName("dataimport");
        } catch (Exception e)
        {
            mv.addObject("error", Logger.ErrorLogger(e));
            mv.setViewName("error");
        }
        return mv;
    }

    public ModelAndView fillColumn(HttpServletRequest request, HttpServletResponse response, DataImportFormBean data_import_frmbn) throws Exception
    {
        ModelAndView mv = new ModelAndView();

        try
        {
            DataImportService ser = new DataImportService();
            List col_list = null;

            mv.addObject("process", "fillColumn");

            col_list = ser.getColumn(data_import_frmbn);
            mv.addObject("finlibPath", finlibPath);
            mv.addObject("column_list", col_list);
            mv.setViewName("dataimport");
        } catch (Exception e)
        {
            mv.addObject("error", Logger.ErrorLogger(e));
            mv.setViewName("error");
        }
        return mv;
    }

    public ModelAndView importData(HttpServletRequest request, HttpServletResponse response, DataImportFormBean data_import_frmbn) throws Exception
    {
        ModelAndView mv = new ModelAndView();

        try
        {
            DataImportService ser = new DataImportService();
            mv.addObject("process", "importData");
            String fnm = request.getParameter("FileName1");
            String hdr = request.getParameter("header1");
            String dest = data_import_frmbn.getDest();
            String tabnm = data_import_frmbn.getTables();
            String path = ser.importData(data_import_frmbn, fnm, hdr);
            mv.addObject("finlibPath", finlibPath);
            mv.addObject("path", path);
            mv.addObject("destination", dest);
            mv.addObject("tablenm", tabnm);
            mv.setViewName("dataimport");
        } catch (Exception e)
        {
            mv.addObject("error", Logger.ErrorLogger(e));
            mv.setViewName("error");
        }
        return mv;
    }
}
