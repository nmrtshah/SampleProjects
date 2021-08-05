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
public class ColumnGenerator
{

    public void generateColumnJsp(final FinReportDetailEntityBean dEntityBean, FinReportSummaryEntityBean sEntityBean) throws FileNotFoundException
    {
        String projectName = dEntityBean.getProjectName();
        String tomcatPath = finpack.FinPack.getProperty("tomcat1_path");
        String moduleName = dEntityBean.getModuleName();
        String number = dEntityBean.getSRNo() + "RGV2";
        String filePath = tomcatPath + "/webapps/finstudio/generated/" + number + "/";
        String projectPath = filePath + projectName.toLowerCase(Locale.getDefault()) + "/WEB-INF/jsp/" + moduleName.toLowerCase(Locale.getDefault()) + "/";

        File file = new File(projectPath);
        file.mkdirs();
        PrintWriter pw = new PrintWriter(projectPath + "columns.jsp");

        pw.println("<%-- TCIGBF --%>");
        pw.println();
        pw.println("<div class=\"report_text\">Columns</div>");
        pw.println("<div class=\"report_content\" align=\"center\">");
        pw.println("    <table align=\"center\" cellpadding=\"2\" cellspacing=\"2\" class=\"menu_subcaption\" width=\"60%\">");
        pw.println("        <tr>");
        pw.println("            <td align=\"right\" valign=\"center\">");
        pw.println("                <select id=\"col_cmb1\" name=\"col_cmb1[]\" multiple=\"multiple\" size=\"3\" class=\"input_columns\">");
        //if (sEntityBean == null || (sEntityBean != null && !sEntityBean.isColumns()))
        //{
        String[] columns;
        columns = dEntityBean.getSelectedColumns();
        for (int i = 0; i < columns.length; i++)
        {
            pw.println("                    <option value=\"" + i + "\">" + columns[i] + "</option>");
        }
        //}
        pw.println("                </select>");
        pw.println("            </td>");
        pw.println("            <td valign=\"middle\" align=\"center\" nowrap>");
        pw.println("                <a href=\"javascript:void(0)\" onclick=\"move(true, 'col_cmb1','col_cmb2');\" class=\"content_link\">Add >></a>");
        pw.println("                <br>");
        pw.println("                <a href=\"javascript:void(0)\" onclick=\"move(true, 'col_cmb2','col_cmb1');\" class=\"content_link\"><< Remove</a>");
        pw.println("            </td>");
        pw.println("            <td align=\"left\" valign=\"center\">");
        pw.println("                <select id=\"col_cmb2\" name=\"col_cmb2[]\"  multiple=\"multiple\" size=\"3\" class=\"input_columns\"></select>");
        pw.println("            </td>");
        pw.println("        </tr>");
        pw.println("    </table>");
        pw.println("</div>");
        pw.close();
    }
}
