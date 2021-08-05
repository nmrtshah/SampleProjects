/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.apps.finstudio.dbmetadatainspector;

import com.finlogic.util.Logger;
import com.finlogic.util.findatareqexecutor.DependencyInfoBean;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 *
 * @author Bhumika Dodiya
 */
public class DBMetadataController extends MultiActionController
{

    private DBMetadataService service = new DBMetadataService();

    public ModelAndView viewPage(final HttpServletRequest request, final HttpServletResponse response)
    {
        ModelAndView mv = new ModelAndView();
        try
        {
            String finlib_path;
            finlib_path = finpack.FinPack.getProperty("finlib_path");
            mv.addObject("finlib_path", finlib_path);
            mv.setViewName("dbmetadatainspector/dbmetadata");
        }
        catch (Exception e)
        {
            mv.addObject("error", Logger.ErrorLogger(e) + e.getMessage());
            mv.setViewName("error");
        }
        return mv;
    }

    public ModelAndView getDBNames(final HttpServletRequest request, final HttpServletResponse response, final DBMetadataFormBean formBean)
    {
        ModelAndView mv = new ModelAndView();
        try
        {
            mv.addObject("process", "DBComboFill");
            mv.addObject("DBList", service.getDBNames(formBean));
            mv.setViewName("dbmetadatainspector/dbMetadataAjax");
        }
        catch (Exception e)
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
            String dbtype = service.getDBType(request.getParameter("cmbDB"));
            if (dbtype == null || dbtype.equalsIgnoreCase(""))
            {
                mv.addObject("process", "NoDBType");
            }
            else
            {
                mv.addObject("process", dbtype);
            }
            mv.setViewName("dbmetadatainspector/dbMetadataAjax");
        }
        catch (Exception e)
        {
            mv.addObject("error", Logger.ErrorLogger(e) + e.getMessage());
            mv.setViewName("error");
        }
        return mv;
    }

    public ModelAndView getObjName(final HttpServletRequest request, final HttpServletResponse response, final DBMetadataFormBean formBean)
    {
        ModelAndView mv = new ModelAndView();
        try
        {
            mv.addObject("process", "ObjComboFill");
            mv.addObject("ObjList", service.getObjName(formBean));
            mv.setViewName("dbmetadatainspector/dbMetadataAjax");
        }
        catch (Exception e)
        {
            mv.addObject("error", Logger.ErrorLogger(e) + e.getMessage());
            mv.setViewName("error");
        }
        return mv;
    }

    public ModelAndView getDBDefinition(final HttpServletRequest request, final HttpServletResponse response, final DBMetadataFormBean formBean)
    {
        ModelAndView mv = new ModelAndView();
        try
        {
            mv.addObject("process", "ListDefination");
            mv.setViewName("dbmetadatainspector/dbMetadataAjax");
            Map<String, List> res = new HashMap<String, List>();
            res = service.getDBDefinition(formBean);
            mv.addObject("Columns", res.get("Columns"));
            mv.addObject("Indexes", res.get("Indexes"));
            mv.addObject("Constraints", res.get("Constraints"));
            mv.addObject("Triggers", res.get("Triggers"));
        }
        catch (Exception e)
        {
            mv.addObject("error", Logger.ErrorLogger(e) + e.getMessage());
            mv.setViewName("error");
        }
        return mv;
    }

    public ModelAndView getDependencies(final HttpServletRequest request, final HttpServletResponse response, final DBMetadataFormBean formBean)
    {
        ModelAndView mv = new ModelAndView();
        mv.addObject("process", "tblDependencies");
        try
        {
            Map<String, List<DependencyInfoBean>> tblDepMap = service.getTableDependencies(formBean);
            mv.addObject("oraDep", tblDepMap.get("oraDep"));
            mv.addObject("oraLogDep", tblDepMap.get("oraLogDep"));
            mv.addObject("oraEtlDep", tblDepMap.get("oraEtlDep"));
            mv.addObject("oraDUDep", tblDepMap.get("oraDUDep"));
            mv.addObject("msqlLogDep", tblDepMap.get("msqlLogDep"));
            mv.addObject("msqlViewDep", tblDepMap.get("msqlViewDep"));
            mv.addObject("msqlInfoBrightDep", tblDepMap.get("msqlInfoBrightDep"));
            mv.addObject("MViewDep", tblDepMap.get("MViewDep"));
            mv.addObject("msqlSameNmTabDep", tblDepMap.get("msqlSameNmTabDep"));
            mv.addObject("sensitiveObj", tblDepMap.get("sensitiveObj"));
            mv.addObject("sensitiveKeyword", tblDepMap.get("sensitiveKeyword"));
            mv.setViewName("dbmetadatainspector/dbMetadataAjax");
        }
        catch (Exception e)
        {
            mv.addObject("error", Logger.ErrorLogger(e) + e.getMessage());
            mv.setViewName("error");
        }
        return mv;
    }

    public ModelAndView getObjDefination(final HttpServletRequest request, final HttpServletResponse response, final DBMetadataFormBean formBean)
    {
        ModelAndView mv = new ModelAndView();
        try
        {
            if ("MTRIGGER".equalsIgnoreCase(formBean.getCmbObjType()) || "OTRIGGER".equalsIgnoreCase(formBean.getCmbObjType()))
            {
                mv.addObject("objDef", service.getTriggerDef(formBean.getCmbDB(), formBean.getCmbObjType(), formBean.getCmbObjName()));
                mv.addObject("process", "objDefination");
            }
            else if ("OSEQUENCE".equalsIgnoreCase(formBean.getCmbObjType()))
            {
                List seqDef = service.getSequenceDef(formBean.getCmbDB(), formBean.getCmbObjType(), formBean.getCmbObjName());
                for (int i = 0; i < seqDef.size(); i++)
                {
                    Map m = (Map) seqDef.get(i);
                    mv.addObject("minValue", m.get("MIN_VALUE"));
                    mv.addObject("maxValue", m.get("MAX_VALUE"));
                    mv.addObject("incrementBy", m.get("INCREMENT_BY"));
                    mv.addObject("cycleFlag", m.get("CYCLE_FLAG"));
                    mv.addObject("orderFlag", m.get("ORDER_FLAG"));
                    mv.addObject("cacheSize", m.get("CACHE_SIZE"));
                    mv.addObject("lastNumber", m.get("LAST_NUMBER"));
                }
                mv.addObject("process", "seqDefination");
            }
            else
            {
                String type = request.getParameter("type");
                mv.addObject("objDef", service.getObjDefination(formBean.getCmbObjType(), formBean.getCmbObjName(), formBean.getCmbDB(), type));
                mv.addObject("process", "objDefination");
            }
            mv.setViewName("dbmetadatainspector/dbMetadataAjax");
        }
        catch (Exception e)
        {
            mv.addObject("error", Logger.ErrorLogger(e) + e.getMessage());
            mv.setViewName("error");
        }

        return mv;
    }
}
