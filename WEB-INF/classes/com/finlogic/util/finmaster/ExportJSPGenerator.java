/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.finmaster;

import com.finlogic.apps.finstudio.finmaster.FinMasterFormBean;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Locale;

/**
 *
 * @author Sonam Patel
 */
public class ExportJSPGenerator
{

    public void generateExportJSP(final FinMasterFormBean formBean) throws FileNotFoundException
    {
        String projectName;
        projectName = formBean.getCmbProjectName();
        String tomcatPath;
        tomcatPath = finpack.FinPack.getProperty("tomcat1_path");
        String moduleName;
        moduleName = formBean.getTxtModuleName();
        String number;
        number = formBean.getSrNo() + "MGV2";
        String filePath;
        filePath = tomcatPath + "/webapps/finstudio/generated/" + number + "/";
        String projectPath;
        projectPath = filePath + projectName.toLowerCase(Locale.getDefault()) + "/WEB-INF/jsp/" + moduleName.toLowerCase(Locale.getDefault()) + "/";

        File file;
        file = new File(projectPath);
        file.mkdirs();
        PrintWriter pw;
        pw = new PrintWriter(projectPath + "export.jsp");

        pw.println("<%-- TCIGBF --%>");
        pw.println();
        pw.println("<div class=\"menu_caption_bg\">");
        pw.println("    <div class=\"menu_caption_text\">Export</div>");
        pw.println("</div>");
        pw.println("<div class=\"report_content\">");
        pw.println("    <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"2\" class=\"menu_subcaption\" width=\"60%\">");
        if (formBean.isChkPdf() || formBean.isChkXls())
        {
            pw.println("        <tr>");
            pw.println("            <td width=\"20%\">Report name :</td>");
            pw.println("            <td width=\"80%\"><input type=\"text\" name=\"reportName\" id=\"reportName\" ></td>");
            pw.println("        </tr>");
        }
        pw.println("        <tr>");
        pw.println("            <td>Format :</td>");
        pw.println("            <td>");
        pw.println("                <div class=\"export_radio\">");
        pw.println("                    <input type=\"radio\" class=\"radio\" id=\"rdoOnScreen\" name=\"rdoRptFormate\" value=\"Onscreen\" checked >");
        pw.println("                    <a href=\"#\" id=\"export_onscreen\">On screen</a>");
        pw.println("                </div>");

        if (formBean.isChkPdf())
        {
            pw.println("                <div class=\"export_radio\">");
            pw.println("                    <input type=\"radio\" class=\"radio\" id=\"rdoPdf\" name=\"rdoRptFormate\" value=\"Pdf\">");
            pw.println("                    <a href=\"#\" id=\"export_pdf\"><span>&nbsp;</span>Pdf</a>");
            pw.println("                </div>");
        }
        if (formBean.isChkXls())
        {
            pw.println("                <div class=\"export_radio\">");
            pw.println("                    <input type=\"radio\" class=\"radio\" id=\"rdoXLS\" name=\"rdoRptFormate\" value=\"XLS\">");
            pw.println("                    <a href=\"#\" id=\"export_xls\"><span>&nbsp;</span>XLS</a>");
            pw.println("                </div>");
        }
        pw.println("            </td>");
        pw.println("        </tr>");

        pw.println("    </table>");
        pw.println("</div>");
        pw.close();
    }
}
