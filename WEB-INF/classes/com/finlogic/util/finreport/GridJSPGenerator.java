/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.finreport;

import com.finlogic.business.finstudio.finreport.FinReportDetailEntityBean;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Locale;

/**
 *
 * @author njuser
 */
public class GridJSPGenerator
{

    public void generateGridJsp(final FinReportDetailEntityBean entityBean) throws FileNotFoundException
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
        pw.println("<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">");

        pw.println("    <tr>");
        pw.println("        <td>");
        pw.println("            <div id=\"trn_print\" class=\"grid-main-report\" style=\"display: none;\" align=\"center\">");
        pw.println("                <div id=\"report_display\" class=\"report-main-content\" style=\"display: block;\" align=\"center\">");
        pw.println("                    <table id=\"rptgrid\" width=\"100%\"></table>");
        pw.println("                    <div id=\"pagerrptgrid\"></div>");
        pw.println("                    <div id=\"errorBox\"></div>");
        pw.println("                </div>");
        pw.println("            </div>");
        pw.println("        </td>");
        pw.println("    </tr>");
        if (entityBean.isPieChart() || entityBean.isBarChart() || entityBean.isSymbolLineChart() || entityBean.isThreedLineChart())
        {
            pw.println("    <tr>");
            pw.println("        <td>");
            pw.println("            <div id=\"chartImage\" align=\"center\"></div>");
            pw.println("        </td>");
            pw.println("    </tr>");
        }
        pw.println("</table>");
        pw.close();
    }
}
