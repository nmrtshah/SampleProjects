/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.finreport;

import com.finlogic.business.finstudio.finreport.FinReportDetailEntityBean;
import com.finlogic.business.finstudio.finreport.FinReportSummaryEntityBean;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Locale;

/**
 *
 * @author njuser
 */
public class GraphGenerator
{

    public void generateGraphJsp(final FinReportDetailEntityBean dEntityBean, final FinReportSummaryEntityBean sEntityBean) throws FileNotFoundException
    {
        String projectName = dEntityBean.getProjectName();
        String tomcatPath = finpack.FinPack.getProperty("tomcat1_path");
        String moduleName = dEntityBean.getModuleName();
        String number = dEntityBean.getSRNo() + "RGV2";
        String filePath = tomcatPath + "/webapps/finstudio/generated/" + number + "/";
        String projectPath = filePath + projectName.toLowerCase(Locale.getDefault()) + "/WEB-INF/jsp/" + moduleName.toLowerCase(Locale.getDefault()) + "/";

        File file = new File(projectPath);
        file.mkdirs();
        PrintWriter pw = new PrintWriter(projectPath + "graph.jsp");

        pw.println("<%-- TCIGBF --%>");
        pw.println();
        pw.println("<div class=\"report_text\">Graph</div>");
        pw.println("<div class=\"report_content\" align=\"center\">");
        pw.println("    <table align=\"center\" cellpadding=\"0\" cellspacing=\"0\" class=\"menu_subcaption graph_selection\" width=\"50%\">");
        pw.println("        <tr>");
        if (dEntityBean.isNoChart())
        {
            pw.println("            <td align=\"center\"><input type=\"radio\" value=\"no_chart\" name=\"chart\" id=\"noChart\" checked=\"checked\"></td>");
        }
        if (dEntityBean.isPieChart())
        {
            pw.println("            <td align=\"center\"><input type=\"radio\" value=\"pie_chart\" name=\"chart\" id=\"pieChart\"></td>");
        }
        if (dEntityBean.isBarChart())
        {
            pw.println("            <td align=\"center\"><input type=\"radio\" value=\"bar_chart\" name=\"chart\" id=\"barChart\"></td>");
        }
        if (dEntityBean.isSymbolLineChart())
        {
            pw.println("            <td align=\"center\"><input type=\"radio\" value=\"line_chart\" name=\"chart\" id=\"lineChart\"></td>");
        }
        if (dEntityBean.isThreedLineChart())
        {
            pw.println("            <td align=\"center\"><input type=\"radio\" value=\"area_chart\" name=\"chart\" id=\"areaChart\"></td>");
        }
        pw.println("        </tr>");
        pw.println("        <tr>");
        if (dEntityBean.isNoChart())
        {
            pw.println("            <td valign=\"top\" align=\"center\">No Chart</td>");
        }
        if (dEntityBean.isPieChart())
        {
            pw.println("            <td valign=\"top\" align=\"center\"><span id=\"graph1\"></span></td>");
        }
        if (dEntityBean.isBarChart())
        {
            pw.println("            <td valign=\"top\" align=\"center\"><span id=\"graph2\"></span></td>");
        }
        if (dEntityBean.isSymbolLineChart())
        {
            pw.println("            <td valign=\"top\" nowrap align=\"center\"><span id=\"graph3\"></span></td>");
        }
        if (dEntityBean.isThreedLineChart())
        {
            pw.println("            <td valign=\"top\" align=\"center\"><span id=\"graph4\"></span></td>");
        }
        pw.println("        </tr>");
        pw.println("        <tr>");
        if (dEntityBean.isNoChart())
        {
            pw.println("            <td valign=\"top\"></td>");
        }
        if (dEntityBean.isPieChart())
        {
            pw.println("            <td valign=\"top\" align=\"center\">Pie Chart</td>");
        }
        if (dEntityBean.isBarChart())
        {
            pw.println("            <td valign=\"top\" align=\"center\">Bar Chart</td>");
        }
        if (dEntityBean.isSymbolLineChart())
        {
            pw.println("            <td valign=\"top\" nowrap align=\"center\">Symbol Line Chart</td>");
        }
        if (dEntityBean.isThreedLineChart())
        {
            pw.println("            <td valign=\"top\" align=\"center\">3d Line Chart</td>");
        }
        pw.println("        </tr>");
        pw.println("    </table>");
        pw.println("</div>");
        pw.close();
    }
}
