/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.apps.finstudio.dbmcomparator;

import com.finlogic.util.Logger;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 *
 * @author Jeegar Kumar Patel
 */
public class DBMcomparatorController extends MultiActionController
{

    private DBMcomparatorService srvc = new DBMcomparatorService();

    public ModelAndView viewPage(final HttpServletRequest request, final HttpServletResponse response)
    {
        ModelAndView mv = new ModelAndView();
        try
        {
            String finlib_path;
            finlib_path = finpack.FinPack.getProperty("finlib_path");
            mv.addObject("finlib_path", finlib_path);
            mv.setViewName("dbmcomparator/dbmComparator");
        }
        catch (Exception e)
        {
            mv.addObject("error", Logger.ErrorLogger(e) + e.getMessage());
            mv.setViewName("error");
        }
        return mv;
    }

    public ModelAndView getDBNames(final HttpServletRequest request, final HttpServletResponse response, final DBMcomparatorFormBean formBean)
    {
        ModelAndView mv = new ModelAndView();
        try
        {
            mv.addObject("process", "DBComboFill");
            mv.addObject("DBList", srvc.getDBNames(formBean));
            mv.setViewName("dbmcomparator/dbMetadataAjax");
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
            String dbtype = srvc.getDBType(request.getParameter("cmbDB"));
            if (dbtype == null || dbtype.equalsIgnoreCase(""))
            {
                mv.addObject("process", "NoDBType");
            }
            else
            {
                mv.addObject("process", dbtype);
            }
            mv.setViewName("dbmcomparator/dbMetadataAjax");
        }
        catch (Exception e)
        {
            mv.addObject("error", Logger.ErrorLogger(e) + e.getMessage());
            mv.setViewName("error");
        }
        return mv;
    }

    public ModelAndView getObjName(final HttpServletRequest request, final HttpServletResponse response, final DBMcomparatorFormBean formBean)
    {
        ModelAndView mv = new ModelAndView();
        try
        {
            mv.addObject("process", "ObjComboFill");
            String server = request.getParameter("server");
            Set objset = srvc.getObjName(formBean, server);
            mv.addObject("ObjList", objset);
            mv.setViewName("dbmcomparator/dbMetadataAjax");

        }
        catch (Exception e)
        {
            mv.addObject("error", Logger.ErrorLogger(e) + e.getMessage());
            mv.setViewName("error");
        }
        return mv;
    }

    public ModelAndView getDBDefinition(final HttpServletRequest request, final HttpServletResponse response, final DBMcomparatorFormBean formBean)
    {
        ModelAndView mv = new ModelAndView();
        try
        {
            mv.addObject("process", "ListDefination1");
            mv.addObject("process2", "ListDefination2");

            mv.setViewName("dbmcomparator/dbMetadataAjax");

            Map<String, List> res1 = new HashMap<String, List>();
            Map<String, List> res2 = new HashMap<String, List>();
            String server = request.getParameter("server");
            String serv1 = "Dev";
            String serv2 = "Test";
            String serv3 = "Prod";
            int list_flag1 = 0;
            int list_flag2 = 0;
            if (server.equals("DT"))
            {
                res1 = srvc.getDBDefinition(formBean, serv1);
                if (res1.get("Columns").isEmpty())
                {
                    list_flag1 = 1;
                }
                res2 = srvc.getDBDefinition(formBean, serv2);
                if (res2.get("Columns").isEmpty())
                {
                    list_flag2 = 1;
                }

                Map<String, List> prefinallist = srvc.getTableDiff(res1, res2);

                Map<String, List> finallist = srvc.getColumnDiff(prefinallist);

                mv.addObject("Columns", finallist.get("Columns"));
                mv.addObject("Indexes", finallist.get("Indexes"));
                mv.addObject("Constraints", finallist.get("Constraints"));
                mv.addObject("Triggers", finallist.get("Triggers"));

                mv.addObject("list_flag1", list_flag1);
                mv.addObject("list_flag2", list_flag2);
                mv.addObject("server1", "Developement Server");
                mv.addObject("server2", "Testing Server");
            }
            else if (server.equals("TP"))
            {
                res1 = srvc.getDBDefinition(formBean, serv2);
                if (res1.get("Columns").isEmpty())
                {
                    list_flag1 = 1;
                }
                res2 = srvc.getDBDefinition(formBean, serv3);
                if (res2.get("Columns").isEmpty())
                {
                    list_flag2 = 1;
                }
                Map<String, List> prefinallist = srvc.getTableDiff(res1, res2);
                Map<String, List> finallist = srvc.getColumnDiff(prefinallist);

                mv.addObject("Columns", finallist.get("Columns"));
                mv.addObject("Indexes", finallist.get("Indexes"));
                mv.addObject("Constraints", finallist.get("Constraints"));
                mv.addObject("Triggers", finallist.get("Triggers"));

                mv.addObject("list_flag1", list_flag1);
                mv.addObject("list_flag2", list_flag2);
                mv.addObject("server1", "Testing Server");
                mv.addObject("server2", "Production Server");
            }
        }
        catch (Exception e)
        {
            mv.addObject("error", Logger.ErrorLogger(e) + e.getMessage());
            mv.setViewName("error");
        }
        return mv;
    }

    public ModelAndView getNewObjDefination(final HttpServletRequest request, final HttpServletResponse response, final DBMcomparatorFormBean formBean)
    {
        ModelAndView mv = new ModelAndView();
        Map<String, List> result1 = new HashMap<String, List>();
        Map<String, List> result2 = new HashMap<String, List>();
        String server = request.getParameter("server");
        String serv1 = "Dev";
        String serv2 = "Test";
        String serv3 = "Prod";
        String type = request.getParameter("type");
        List listData1 = new LinkedList();
        List listData2 = new LinkedList();

        try
        {
            if ("MTRIGGER".equalsIgnoreCase(formBean.getCmbObjType()) || "OTRIGGER".equalsIgnoreCase(formBean.getCmbObjType()))
            {
                if (server.equals("DT"))
                {
                    result1 = srvc.getObjDefination(formBean.getCmbObjType(), formBean.getCmbObjName(), formBean.getCmbDB(), type, serv1, formBean);
                    result2 = srvc.getObjDefination(formBean.getCmbObjType(), formBean.getCmbObjName(), formBean.getCmbDB(), type, serv2, formBean);
                    mv.addObject("server1", "Developement Server");
                    mv.addObject("server2", "Testing Server");
                    listData1 = (List) result1.get("AllData1");
                    listData2 = (List) result2.get("AllData2");

                }
                else if (server.equals("TP"))
                {

                    result1 = srvc.getObjDefination(formBean.getCmbObjType(), formBean.getCmbObjName(), formBean.getCmbDB(), type, serv2, formBean);
                    result2 = srvc.getObjDefination(formBean.getCmbObjType(), formBean.getCmbObjName(), formBean.getCmbDB(), type, serv3, formBean);
                    mv.addObject("server1", "Testing Server");
                    mv.addObject("server2", "Production Server");
                    listData1 = (List) result1.get("AllData2");
                    listData2 = (List) result2.get("AllData3");
                }

                mv.addObject("process", "uniqueData");
                mv.addObject("DevData", listData1);
                mv.addObject("TestData", listData2);

            }
            else
            {

                if (server.equals("DT"))
                {
                    result1 = srvc.getObjDefination(formBean.getCmbObjType(), formBean.getCmbObjName(), formBean.getCmbDB(), type, serv1, formBean);
                    result2 = srvc.getObjDefination(formBean.getCmbObjType(), formBean.getCmbObjName(), formBean.getCmbDB(), type, serv2, formBean);
                    mv.addObject("server1", "Developement Server");
                    mv.addObject("server2", "Testing Server");
                    listData1 = (List) result1.get("AllData1");
                    listData2 = (List) result2.get("AllData2");
                }
                else if (server.equals("TP"))
                {
                    result1 = srvc.getObjDefination(formBean.getCmbObjType(), formBean.getCmbObjName(), formBean.getCmbDB(), type, serv2, formBean);
                    result2 = srvc.getObjDefination(formBean.getCmbObjType(), formBean.getCmbObjName(), formBean.getCmbDB(), type, serv3, formBean);
                    mv.addObject("server1", "Testing Server");
                    mv.addObject("server2", "Production Server");
                    listData1 = (List) result1.get("AllData2");
                    listData2 = (List) result2.get("AllData3");
                }

                mv.addObject("process", "uniqueData");
                mv.addObject("DevData", listData1);
                mv.addObject("TestData", listData2);


            }

            mv.setViewName("dbmcomparator/dbMetadataAjax");
        }
        catch (Exception e)
        {
            mv.addObject("error", Logger.ErrorLogger(e) + e.getMessage());
            mv.setViewName("error");
        }

        return mv;
    }

    public ModelAndView angularDemo ( HttpServletRequest request , HttpServletResponse response )
            throws Exception
    {
        ModelAndView view = new ModelAndView();

        view.setViewName("angularjsdemo/index");

        return view;
    }


    public ModelAndView getdata ( HttpServletRequest request , HttpServletResponse response )
            throws Exception
    {
        ModelAndView view = new ModelAndView("angularjsdemo/ajax");

        view.addObject("json", srvc.getProjectList());

        return view;
    }

    public ModelAndView getmdata ( HttpServletRequest request , HttpServletResponse response )
            throws Exception
    {
        ModelAndView view = new ModelAndView("angularjsdemo/ajax");

        view.addObject("json", srvc.getModuleList(request.getParameter("p")));

        return view;
    }

    public ModelAndView gettdata ( HttpServletRequest request , HttpServletResponse response )
            throws Exception
    {
        ModelAndView view = new ModelAndView("angularjsdemo/ajax");

        view.addObject("json", srvc.getReportData());

        return view;
    }

    public ModelAndView table ( HttpServletRequest request , HttpServletResponse response )
            throws Exception
    {
        ModelAndView view = new ModelAndView("angularjsdemo/table");

        view.addObject("tabledata", srvc.getBigReportData());

        return view;
    }

}