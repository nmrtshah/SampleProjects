/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.findhtmlreport;

import com.finlogic.business.finstudio.findhtmlreport.FinDhtmlReportDetailEntityBean;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Locale;

/**
 *
 * @author njuser
 */
public class FinDhtmlReportGridGenerator
{

    public void generateGridJsp(final FinDhtmlReportDetailEntityBean entityBean) throws FileNotFoundException
    {
        String projectName = entityBean.getProjectName();
        String tomcatPath = finpack.FinPack.getProperty("tomcat1_path");
        String moduleName = entityBean.getModuleName();
        String number = entityBean.getSRNo() + "RGV2";
        String filePath = tomcatPath + "/webapps/finstudio/generated/" + number + "/";
        String projectPath = filePath + projectName.toLowerCase(Locale.getDefault()) + "/WEB-INF/jsp/" + moduleName.toLowerCase(Locale.getDefault()) + "/";

        File file = new File(projectPath);
        file.mkdirs();
        PrintWriter pw = new PrintWriter(projectPath + "grid.jsp");

        pw.println("<%-- TCIGBF --%>");
        pw.println();
        pw.println("<div id=\"basicReportDiv\" style=\"display: none\">");

        pw.println("    <table cellpadding=\"0\" cellspacing=\"0\" width=\"1000\" class=\"dhtml-main-report\">");
        pw.println("        <tr>");
        pw.println("            <td align=\"center\">");
        pw.println("                <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" class=\"tbl_h1_bg\"  onclick=\"javascript: hide_menu('report_hide','maingridDiv');\"> ");
        pw.println("                    <tr>");
        pw.println("                        <td class=\"menu_caption_text\" id=\"rptTitle\">" + entityBean.getReportTitle() + "</td>");
        if (entityBean.isPdf())
        {
            pw.println("                        <td width=\"25\"><span class=\"export_radio\" style=\"margin-right: 0\"><a id=\"export_pdf\" href=\"javascript: generatePDF();\"><span>&nbsp;</span></a></span></td>");
        }
        if (entityBean.isExcel())
        {
            pw.println("                        <td width=\"25\"><span class=\"export_radio\" style=\"margin-right: 2px\"><a id=\"export_xls\" href=\"javascript: generateEXCEL();\"><span>&nbsp;</span></a></span></td>");
        }
        pw.println("                        <td width=\"25\"><span><a href=\"javascript:void(0);\" onclick=\"javascript: showReport('Print');\"><img src=\"${finlibPath}/resource/images/print_icon.gif\" alt=\"Print\" title=\"Print\"/></a></span></td>");
        pw.println("                        <td width=\"25\"><span><a href=\"javascript:void(0);\" class=\"nav_hide\" id=\"report_hide\" name=\"report_hide\"></a></span></td>");
        pw.println("                    </tr>");
        pw.println("                </table>");
        pw.println("            </td>");
        pw.println("        </tr>");
        pw.println("        <tbody id=\"maingridDiv\">");
        pw.println("            <tr>");
        pw.println("                <td valign=\"top\">");
        pw.println("                    <div id=\"gridbox\" style=\"width:100%;background-color:white;margin: 5px auto;\"></div>");
        pw.println("                </td>");
        pw.println("            </tr>");
        pw.println("            <tr>");
        pw.println("                <td valign=\"top\">");
        pw.println("                    <div id=\"pagingArea\" align=\"center\" style=\"display: block\"></div>");
        pw.println("                </td>");
        pw.println("            </tr>");
        pw.println("            <tr>");
        pw.println("                <td valign=\"top\">");
        pw.println("                    <div id=\"recinfoArea\" align=\"center\" style=\"display: block;\"></div>");
        pw.println("                </td>");
        pw.println("            </tr>");
        pw.println("        </tbody>");
//        pw.println("    </table>");        
        if (entityBean.isPieChart() || entityBean.isBarChart() || entityBean.isSymbolLineChart() || entityBean.isThreedLineChart())
        {
            pw.println("    <tr>");
            pw.println("        <td>");
            pw.println("            <div id=\"chartImage\" align=\"center\"></div>");
            pw.println("        </td>");
            pw.println("    </tr>");
        }
        pw.println("</table>");
        pw.println("</div>");
        pw.close();
    }
}
