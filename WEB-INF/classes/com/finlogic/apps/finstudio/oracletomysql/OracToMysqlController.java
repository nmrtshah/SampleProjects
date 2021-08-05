/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.apps.finstudio.oracletomysql;

import com.finlogic.util.Logger;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 *
 * @author njuser
 */
public class OracToMysqlController extends MultiActionController
{

    public ModelAndView getMenu(final HttpServletRequest request, final HttpServletResponse response)
    {
        ModelAndView mav = new ModelAndView();
        try
        {
            mav.setViewName("oractomysql/Main");
            String finlib_path = finpack.FinPack.getProperty("finlib_path");
            mav.addObject("process", "main");
            mav.addObject("finlib_path", finlib_path);
        }
        catch (Exception e)
        {
            mav.addObject("action", "error");
            mav.addObject("error", e.getMessage());
            Logger.ErrorLogger(e);
        }
        return mav;
    }

    public ModelAndView getSchema(final HttpServletRequest request, final HttpServletResponse response, final OracToMysqlFormbean formBean)
    {
        ModelAndView mav = new ModelAndView();
        OracToMysqlConverter converter = new OracToMysqlConverter();
        List schema;
        try
        {
            String server = formBean.getServer();
            schema = converter.getSchema(server);
            mav.addObject("schema", schema);
            mav.addObject("process", "schema");
            mav.setViewName("oractomysql/Main");
        }
        catch (Exception e)
        {
            mav.addObject("action", "error");
            mav.addObject("error", e.getMessage());
            Logger.ErrorLogger(e);
        }
        return mav;
    }

    public ModelAndView getItemnList(final HttpServletRequest request, final HttpServletResponse response, final OracToMysqlFormbean formBean)
    {
        ModelAndView mav = new ModelAndView();
        OracToMysqlConverter converter = new OracToMysqlConverter();
        List itemNm;
        try
        {
            String server = formBean.getServer();
            String owner = formBean.getSchema();
            String item = formBean.getItem();
            itemNm = converter.getItemList(item, owner, server);
            mav.addObject("itemNm", itemNm);
            mav.addObject("process", "item");
            mav.setViewName("oractomysql/Main");
        }
        catch (Exception e)
        {
            mav.addObject("action", "error");
            mav.addObject("error", e.getMessage());
            Logger.ErrorLogger(e);
        }
        return mav;
    }

    public ModelAndView getNewQuery(final HttpServletRequest request, final HttpServletResponse response, final OracToMysqlFormbean formBean)
    {
        ModelAndView mav = new ModelAndView();
        OracToMysqlConverter converter = new OracToMysqlConverter();
        String itemText;
        try
        {
            mav.setViewName("oractomysql/Main");

            String server = formBean.getServer();
            String owner = formBean.getSchema();
            String itemType = formBean.getItem();
            String itemNm = formBean.getItemNmCmb();

            HttpSession session = request.getSession(false);
            if (session.getAttribute("ACLEmpCode") == null)
            {
                formBean.setEmp_code("");
            }
            else
            {
                formBean.setEmp_code(session.getAttribute("ACLEmpCode").toString());
            }
            //set alias name
            if (formBean.getRdoGetSourceType().equals("usingConn"))
            {
                //converter.setAliasName(owner);
                itemText = converter.getNewQuery(itemType, owner, server, itemNm);
            }
            else
            {
                itemText = formBean.getTxtaOraQuery();
                server = "dev_tran";
            }
            //remove extra line from procedure
            while (itemText.contains("\n\n") || itemText.contains("  "))
            {
                itemText = itemText.replaceAll("  ", " ");
                itemText = itemText.replaceAll(" \n", "\n");
                itemText = itemText.replaceAll("\n ", "\n");
                itemText = itemText.replaceAll("\n\n", "\n");
            }

            String[] query = itemText.split("\n");
            String strBlock = "PL/SQL BLOCK";
            if (formBean.getRdoGetSourceType().equals("usingBlock"))
            {
                formBean.setItem(strBlock);
                formBean.setSchema(strBlock);
                formBean.setItemNmCmb(strBlock);
                formBean.setServer(strBlock);
            }
            //get converted result            
            int result = 0;
            if (formBean.getRdoGetSourceType().equals("usingConn"))
            {
                result = converter.validateProc(itemNm, server, itemText, owner, formBean.getRdoGetSourceType(), itemType);
            }
            else
            {
                result = 0;
            }
            String finalQuery = "";
            if (result == 0)
            {
                query = converter.mainMethod(query, server, owner, formBean.getRdoGetSourceType(), itemType);
                //insert into DB
                converter.insert(formBean);
                StringBuilder tmpSqlLines = new StringBuilder();
                for (int i = 0; i < query.length; i++)
                {
                    query[i] = query[i].replace("ABCPQRXYZ", "\n ");
                    tmpSqlLines.append(query[i]);
                    tmpSqlLines.append(" \n");
                    //finalQuery += query[i] + " \n";
                }
                finalQuery = tmpSqlLines.toString();
                while (finalQuery.contains("\n\n\n") || finalQuery.contains("  "))
                {
                    finalQuery = finalQuery.replaceAll("  ", " ");
                    finalQuery = finalQuery.replaceAll(" \n", "\n");
                    finalQuery = finalQuery.replaceAll("\n ", "\n");
                    finalQuery = finalQuery.replaceAll("\n\n\n", "\n\n");
                    finalQuery = finalQuery.replaceAll("\n;", ";");
                    finalQuery = finalQuery.replaceAll("\n=", "=");
                }
            }
            else if (result > 0)
            {
                finalQuery = "Please check your source...It has syntax error";
            }
            mav.addObject("itemText", finalQuery);
            mav.addObject("process", "query");
        }
        catch (Exception e)
        {
            mav.addObject("action", "error");
            mav.addObject("error", e.getMessage());
            Logger.ErrorLogger(e);
        }
        return mav;
    }
}
