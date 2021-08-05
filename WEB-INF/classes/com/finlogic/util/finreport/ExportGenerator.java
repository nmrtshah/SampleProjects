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
public class ExportGenerator
{

    public void generateExportJsp(final FinReportDetailEntityBean entityBean) throws FileNotFoundException
    {
        String projectName = entityBean.getProjectName();
        String tomcatPath = finpack.FinPack.getProperty("tomcat1_path");
        String moduleName = entityBean.getModuleName();
        String number = entityBean.getSRNo() + "RGV2";
        String filePath = tomcatPath + "/webapps/finstudio/generated/" + number + "/";
        String projectPath = filePath + projectName.toLowerCase(Locale.getDefault()) + "/WEB-INF/jsp/" + moduleName.toLowerCase(Locale.getDefault()) + "/";

        File file = new File(projectPath);
        file.mkdirs();
        PrintWriter pw = new PrintWriter(projectPath + "export.jsp");

        pw.println("<%-- TCIGBF --%>");
        pw.println();
        pw.println("<div class=\"report_text\">Export</div>");
        pw.println("<div class=\"report_content\" align=\"center\">");
        pw.println("    <table cellspacing=\"2\" cellpadding=\"0\" border=\"0\" align=\"center\" width=\"60%\" class=\"menu_subcaption\">");
        if (entityBean.isPdf() || entityBean.isExcel() || entityBean.isHtml())
        {
            pw.println("        <tr>");
            pw.println("            <td>Report name :</td>");
            pw.println("            <td><input type=\"text\" name=\"reportName\" id=\"reportName\" ></td>");
            pw.println("        </tr>");
        }
        pw.println("        <tr>");
        pw.println("            <td>Format</td>");
        pw.println("            <td>");
        pw.println("                <div class=\"export_radio\"><input type=\"radio\" class=\"radio\" id=\"rdoOnScreen\" name=\"rdoRptFormate\" value=\"Onscreen\" checked >");
        pw.println("                   <label><a href=\"#\" id=\"export_onscreen\">On screen</a></label></div>");

        if (entityBean.isPdf())
        {
            pw.println("                <div class=\"export_radio\"><input type=\"radio\" class=\"radio\" id=\"rdoPdf\" name=\"rdoRptFormate\" value=\"Pdf\"><label><a href=\"#\" id=\"export_pdf\"><span></span>Pdf</a></label></div>");
        }
        if (entityBean.isExcel())
        {
            pw.println("                <div class=\"export_radio\"><input type=\"radio\" class=\"radio\" value=\"XLS\" id=\"rdoXLS\" name=\"rdoRptFormate\"><label><a href=\"#\" id=\"export_xls\"><span></span>XLS</a></label></div>");
        }
        if (entityBean.isHtml())
        {
            pw.println("                <div class=\"export_radio\"><input type=\"radio\" id=\"rdoHtml\" class=\"radio\" name=\"rdoRptFormate\" value=\"Html\"><label>Html</label></div>");
        }
        pw.println("            </td>");
        pw.println("        </tr>");
        pw.println("    </table>");
        pw.println("</div>");
        pw.close();
    }
}
