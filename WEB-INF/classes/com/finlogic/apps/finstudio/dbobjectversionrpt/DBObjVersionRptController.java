/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.apps.finstudio.dbobjectversionrpt;

import com.finlogic.util.Logger;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 *
 * @author njuser
 */
public class DBObjVersionRptController extends MultiActionController
{

    private final DBObjVersionRptService dbObjService = new DBObjVersionRptService();

    public ModelAndView viewPage(final HttpServletRequest request, final HttpServletResponse response)
    {
        ModelAndView mv = new ModelAndView();
        try
        {
            String finlib_path;
            finlib_path = finpack.FinPack.getProperty("finlib_path");
            mv.addObject("finlib_path", finlib_path);
            mv.setViewName("dbobjectversionrpt/dbobjectversion");
        }
        catch (Exception e)
        {
            mv.addObject("error", Logger.ErrorLogger(e) + e.getMessage());
            mv.setViewName("error");
        }
        return mv;
    }

    public ModelAndView getDBNames(final HttpServletRequest request, final HttpServletResponse response, final DBObjVersionRptFormBean formBean)
    {
        ModelAndView mv = new ModelAndView();
        try
        {
            mv.addObject("process", "DBComboFill");
            mv.addObject("DBList", dbObjService.getDBNames(formBean));
            mv.setViewName("dbobjectversionrpt/dbobjectversionajax");
        }
        catch (SQLException | ClassNotFoundException e)
        {
            mv.addObject("error", Logger.ErrorLogger(e) + e.getMessage());
            mv.setViewName("error");
        }
        return mv;
    }

    public ModelAndView getDBType(final HttpServletRequest request, final HttpServletResponse response)
    {
        ModelAndView mv = new ModelAndView();
        try
        {
            String dbtype = dbObjService.getDBType(request.getParameter("cmbDB"));
            if (dbtype == null || dbtype.equalsIgnoreCase(""))
            {
                mv.addObject("process", "NoDBType");
            }
            else
            {
                mv.addObject("process", dbtype);
            }
            mv.setViewName("dbobjectversionrpt/dbobjectversionajax");
        }
        catch (ClassNotFoundException | SQLException e)
        {
            mv.addObject("error", Logger.ErrorLogger(e) + e.getMessage());
            mv.setViewName("error");
        }
        return mv;
    }

    public ModelAndView getObjName(final HttpServletRequest request, final HttpServletResponse response, final DBObjVersionRptFormBean formBean)
    {
        ModelAndView mv = new ModelAndView();
        try
        {
            mv.addObject("process", "ObjComboFill");
            List objset = dbObjService.getObjName(formBean.getTxtObjName(),formBean.getCmbDB());
            mv.addObject("ObjList", objset);
            mv.setViewName("dbobjectversionrpt/dbobjectversionajax");

        }
        catch (ClassNotFoundException | SQLException e)
        {
            mv.addObject("error", Logger.ErrorLogger(e) + e.getMessage());
            mv.setViewName("error");
        }
        return mv;
    }

    public ModelAndView getReport(final HttpServletRequest request, final HttpServletResponse response, final DBObjVersionRptFormBean formBean)
    {
        ModelAndView mv = new ModelAndView();
        try
        {
            mv.addObject("process", "Report");
            mv.addObject("rptLst", dbObjService.getReport(formBean));
            mv.setViewName("dbobjectversionrpt/dbobjectversionajax");
        }
        catch (ClassNotFoundException | SQLException e)
        {
            mv.addObject("error", Logger.ErrorLogger(e) + e.getMessage());
            mv.setViewName("error");
        }
        return mv;
    }

    public ModelAndView getdiff(final HttpServletRequest request, final HttpServletResponse response, final DBObjVersionRptFormBean formBean) throws ClassNotFoundException, SQLException
    {
        ModelAndView mv = new ModelAndView();
        String finlib_path;
        finlib_path = finpack.FinPack.getProperty("finlib_path");
        mv.addObject("finlib_path", finlib_path);
        mv.setViewName("dbobjectversionrpt/diffpage");
        String queryID = request.getParameter("dataReqid");
        List<Map> defLst = dbObjService.getDefList(queryID);
        mv.addObject("objName", defLst.get(0).get("OBJ_NAME"));
        if (defLst.get(0).get("PURPOSE").equals("ALTER"))
        {
            mv.addObject("newDef", defLst.get(0).get("NEW_QUERY_TEXT"));
        }
        else
        {
            mv.addObject("newDef", defLst.get(0).get("QUERY_TEXT"));
        }
        mv.addObject("oldDef", defLst.get(0).get("OLD_QUERY_TEXT"));
        return mv;
    }
}
