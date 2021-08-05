/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.finmaster;

import com.finlogic.apps.finstudio.finmaster.FinMasterFormBean;
import com.finlogic.util.Logger;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Locale;

/**
 *
 * @author Sonam Patel
 */
public class MainJSPGenerator
{

    public String getMonth(final int idx)
    {
        String[] montharray;
        montharray = new String[]
        {
            "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
        };
        return montharray[idx];
    }

    public String getAMPM(final int idx)
    {
        String[] array;
        array = new String[]
        {
            "AM", "PM"
        };
        return array[idx];
    }

    public String getDate()
    {
        Calendar cal;
        String mydate = "";
        try
        {
            cal = Calendar.getInstance();
            mydate = getMonth(cal.get(Calendar.MONTH)) + " "
                    + cal.get(Calendar.DAY_OF_MONTH) + ", "
                    + cal.get(Calendar.YEAR) + ", "
                    + cal.get(Calendar.HOUR_OF_DAY) + ":"
                    + cal.get(Calendar.MINUTE) + ":"
                    + cal.get(Calendar.SECOND) + " "
                    + getAMPM(cal.get(Calendar.AM_PM));
        }
        catch (Exception e)
        {
            Logger.ErrorLogger(e);
        }
        return mydate;
    }

    public void generateMainJSP(final FinMasterFormBean formBean) throws FileNotFoundException
    {
        String projectName;
        projectName = formBean.getCmbProjectName();
        String tomcatPath;
        tomcatPath = finpack.FinPack.getProperty("tomcat1_path");
        String moduleName;
        moduleName = formBean.getTxtModuleName().toLowerCase(Locale.getDefault());
        String number;
        number = formBean.getSrNo() + "MGV2";
        String filePath;
        filePath = tomcatPath + "/webapps/finstudio/generated/" + number + "/";
        String projectPath;
        projectPath = filePath + projectName.toLowerCase(Locale.getDefault()) + "/WEB-INF/jsp/" + moduleName + "/";

        File file;
        file = new File(projectPath);
        file.mkdirs();
        PrintWriter pw = null;

        try
        {
            pw = new PrintWriter(projectPath + "main.jsp");
            pw.println("<%-- TCIGBF --%>");
            pw.println();
            pw.println("<%-- ");
            pw.println("    Document   : main");
            pw.println("    Created on : " + getDate());
            pw.println("    Author     : njuser");
            pw.println("--%>");
            pw.println();
            pw.println("<%@page contentType=\"text/html\" pageEncoding=\"UTF-8\"%>");
            pw.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\"");
            pw.println("    \"http://www.w3.org/TR/html4/loose.dtd\">");
            pw.println("<html>");
            pw.println("    <head>");
            pw.println("        <title>" + formBean.getTxtOrigModulName() + "</title>");
            pw.println("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" >");
            pw.println("        <link rel=\"stylesheet\" type=\"text/css\" href=\"${finlib_path}/resource/main_offline.css\" >");

            pw.println("        <link rel=\"stylesheet\" type=\"text/css\" href=\"${finlib_path}/jquery/jquery-ui-1.8.13.custom/css/smoothness/jquery-ui-1.8.13.custom.css\" >");
            pw.println("        <link rel=\"stylesheet\" type=\"text/css\" href=\"${finlib_path}/jquery/jquery.jqGrid-3.8.2/css/ui.jqgrid.css\" >");

            pw.println("        <script type=\"text/javascript\" src=\"${finlib_path}/jquery/jquery-1.6.1/jquery-1.6.1.min.js\"></script>");
            pw.println("        <script type=\"text/javascript\" src=\"${finlib_path}/resource/common_functions.min.js\"></script>");

            pw.println("        <script type=\"text/javascript\" src=\"${finlib_path}/jquery/jquery-ui-1.8.13.custom/js/jquery-ui-1.8.13.custom.min.js\"></script>");
            pw.println("        <script type=\"text/javascript\" src=\"${finlib_path}/jquery/jquery.jqGrid-3.8.2/js/i18n/grid.locale-en.js\"></script>");
            pw.println("        <script type=\"text/javascript\" src=\"${finlib_path}/jquery/jquery.jqGrid-3.8.2/js/jquery.jqGrid.min.js\"></script>");

            //validation
            pw.println("        <script type=\"text/javascript\" src=\"${finlib_path}/resource/validate.js\"></script>");
            pw.println("        <script type=\"text/javascript\" src=\"${finlib_path}/resource/validate_date.js\"></script>");
            //validation ends

            boolean dtPkr = false;
            boolean CmnFile = false;
            if (formBean.isChkAdd())
            {
                String[] mstAddCtrls;
                mstAddCtrls = formBean.getHdnAddControl();
                int mstAddLen;
                mstAddLen = mstAddCtrls.length;
                for (int i = 0; i < mstAddLen; i++)
                {
                    if ("DatePicker".equals(mstAddCtrls[i]))
                    {
                        dtPkr = true;
                        //break;
                    }
                    else if ("FileBox".equals(mstAddCtrls[i]))
                    {
                        CmnFile = true;
                        //break;
                    }
                }
            }
            if (formBean.getHdnEditField() != null)
            {
                if ((!dtPkr || !CmnFile) && formBean.isChkEdit())
                {
                    String[] mstEditCtrls;
                    mstEditCtrls = formBean.getHdnEditControl();
                    int mstEditLen;
                    mstEditLen = mstEditCtrls.length;
                    for (int i = 0; i < mstEditLen; i++)
                    {
                        if ("DatePicker".equals(mstEditCtrls[i]))
                        {
                            dtPkr = true;
                            //break;
                        }
                        else if ("FileBox".equals(mstEditCtrls[i]))
                        {
                            CmnFile = true;
                            //break;
                        }
                    }
                }
            }
            if (formBean.getHdnDeleteField() != null)
            {
                if ((!dtPkr || !CmnFile) && formBean.isChkDelete())
                {
                    String[] mstDelCtrls;
                    mstDelCtrls = formBean.getHdnDeleteControl();
                    int mstDelLen;
                    mstDelLen = mstDelCtrls.length;
                    for (int i = 0; i < mstDelLen; i++)
                    {
                        if ("DatePicker".equals(mstDelCtrls[i]))
                        {
                            dtPkr = true;
                            //break;
                        }
                        else if ("FileBox".equals(mstDelCtrls[i]))
                        {
                            CmnFile = true;
                            //break;
                        }
                    }
                }
            }
            if (formBean.getHdnViewField() != null)
            {
                if ((!dtPkr || !CmnFile) && formBean.isChkView())
                {
                    String[] mstViewCtrls;
                    mstViewCtrls = formBean.getHdnViewControl();
                    int mstViewLen;
                    mstViewLen = mstViewCtrls.length;
                    for (int i = 0; i < mstViewLen; i++)
                    {
                        if ("DatePicker".equals(mstViewCtrls[i]))
                        {
                            dtPkr = true;
                            //break;
                        }
                        else if ("FileBox".equals(mstViewCtrls[i]))
                        {
                            CmnFile = true;
                            //break;
                        }
                    }
                }
            }
            if (dtPkr)
            {
                //date picker
                pw.println("        <script type=\"text/javascript\" src=\"${finlib_path}/jquery/jquery-ui-1.8.13.custom/development-bundle/ui/jquery.ui.datepicker.js\"></script>");
                //date picker ends
            }
            if (CmnFile)
            {
                pw.println("        <script type=\"text/javascript\" src=\"${finlib_path}/resource/finstudio-utility-functions.js\"></script>");
            }

            pw.println("        <script type=\"text/javascript\" src=\"${finlib_path}/resource/serverCombo.js\"></script>");
            pw.println("        <script type=\"text/javascript\" src=\"${finlib_path}/resource/ajax.js\"></script>");
            pw.println("        <script type=\"text/javascript\" src=\"js/" + moduleName + ".js\"></script>");

            pw.println("    </head>");
            pw.println("    <body>");
            pw.println("        <div class=\"container\">");
            if (formBean.isChkHeader())
            {
                HeaderJSPGenerator hdrGen;
                hdrGen = new HeaderJSPGenerator();
                hdrGen.generateHeaderJSP(formBean);
                pw.println("            <jsp:include page=\"header.jsp\" />");
            }
            pw.println("            <div class=\"content\" id=\"menuLoader\">");
            pw.println("                <div class=\"menu_new\">");
            pw.println("                    <div class=\"menu_caption_bg cursor-pointer\" onclick=\"javascript:hide_menu('show_hide','load', 'nav_show','nav_hide')\">");
            pw.println("                        <div class=\"menu_caption_text\">" + formBean.getTxtOrigModulName() + "</div>");
            pw.println("                        <span><a href=\"javascript:void(0)\" name=\"show_hide\" id=\"show_hide\" class=\"nav_hide\"></a></span>");
            pw.println("                    </div>");
            pw.println("                    <form name=\"" + moduleName + "Form\" id=\"" + moduleName + "Form\" method=\"post\" action=\"\">");
            pw.println("                        <div class=\"collapsible_menu_tab fullwidth\">");
            pw.println("                            <ul id=\"mainTab1\">");
            if (formBean.isChkAdd())
            {
                AddJSPGenerator addGen;
                addGen = new AddJSPGenerator();
                addGen.generateAddJSP(formBean);
                pw.println("                                <li class=\"\" id=\"mainTab_1\" onclick=\"selectTab(this.id);\">");
                pw.println("                                    <a rel=\"rel0\" href=\"javascript:void(0)\" onclick=\"javascript:AddLoader();\">Add</a>");
                pw.println("                                </li>");
            }
            if (formBean.isChkEdit())
            {
                EditJSPGenerator editGen;
                editGen = new EditJSPGenerator();
                editGen.generateEditJSP(formBean);
                pw.println("                                <li class=\"\" id=\"mainTab_2\" onclick=\"selectTab(this.id);\">");
                pw.println("                                    <a rel=\"rel0\" href=\"javascript:void(0)\" onclick=\"javascript:EditLoader();\">Edit</a>");
                pw.println("                                </li>");
            }
            if (formBean.isChkDelete())
            {
                DeleteJSPGenerator delGen;
                delGen = new DeleteJSPGenerator();
                delGen.generateDeleteJSP(formBean);
                pw.println("                                <li class=\"\" id=\"mainTab_3\" onclick=\"selectTab(this.id);\">");
                pw.println("                                    <a rel=\"rel0\" href=\"javascript:void(0)\" onclick=\"javascript:DeleteLoader();\">Delete</a>");
                pw.println("                                </li>");
            }
            if (formBean.isChkView())
            {
                ViewJSPGenerator viewGen;
                viewGen = new ViewJSPGenerator();
                viewGen.generateViewJSP(formBean);
                pw.println("                                <li class=\"\" id=\"mainTab_4\" onclick=\"selectTab(this.id);\">");
                pw.println("                                    <a rel=\"rel0\" href=\"javascript:void(0)\" onclick=\"javascript:ViewLoader();\">View</a>");
                pw.println("                                </li>");
            }
            pw.println("                            </ul>");
            pw.println("                        </div>");
            pw.println("                        <div align=\"center\" class=\"report_content\">");
            pw.println("                            <div id=\"load\" style=\"display: block;\"></div>");
            pw.println("                        </div>");

            GridJSPGenerator gridJspGen;
            gridJspGen = new GridJSPGenerator();
            gridJspGen.generateGridJSP(formBean);
            pw.println("                        <jsp:include page=\"grid.jsp\" />");

            pw.println("                        <div id=\"divFinLibPath\" style=\"display: none\">${finlib_path}</div>");
            pw.println("                    </form>");
            pw.println("                </div>");
            pw.println("            </div>");
            if (formBean.isChkFooter())
            {
                BottomJSPGenerator btmGen;
                btmGen = new BottomJSPGenerator();
                btmGen.generateBottomJSP(formBean);
                pw.println("            <jsp:include page=\"bottom.jsp\" />");
            }
            pw.println("        </div>");
            pw.println("    </body>");
            pw.println("</html>");
        }
        finally
        {
            if (pw != null)
            {
                pw.close();
            }
        }
    }
}
