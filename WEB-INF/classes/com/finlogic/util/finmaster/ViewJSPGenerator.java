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
public class ViewJSPGenerator
{

    public void generateViewJSP(final FinMasterFormBean formBean) throws FileNotFoundException
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
        PrintWriter pw = null;

        try
        {
            pw = new PrintWriter(projectPath + "view.jsp");
            pw.println("<%-- TCIGBF --%>");
            pw.println();
            pw.println("<div class=\"collapsible_menu_tab\">");
            pw.println("    <ul id=\"mainTab2\">");
            pw.println("        <li class=\"\" id=\"mainTab2_1\" onclick=\"selectTab(this.id);\">");
            pw.println("            <a rel=\"rel1\" href=\"javascript:void(0)\" onclick=\"javascript:showTab('divViewFilter');\">Filter</a>");
            pw.println("        </li>");
            FilterJSPGenerator fltrGen;
            fltrGen = new FilterJSPGenerator();
            fltrGen.generateFilterJSP(formBean);

            if (formBean.isChkOnScreen() || formBean.isChkPdf() || formBean.isChkXls())
            {
                pw.println("        <li class=\"\" id=\"mainTab2_2\" onclick=\"selectTab(this.id);\">");
                pw.println("            <a rel=\"rel1\" href=\"javascript:void(0)\" onclick=\"javascript:showTab('divViewExport');\">Export</a>");
                pw.println("        </li>");
                ExportJSPGenerator expGen;
                expGen = new ExportJSPGenerator();
                expGen.generateExportJSP(formBean);
            }

            if (formBean.isChkColumns())
            {
                pw.println("        <li class=\"\" id=\"mainTab2_3\" onclick=\"selectTab(this.id);\">");
                pw.println("            <a rel=\"rel1\" href=\"javascript:void(0)\" onclick=\"javascript:showTab('divViewColumns');\">Columns</a>");
                pw.println("        </li>");
                ColumnJSPGenerator colGen;
                colGen = new ColumnJSPGenerator();
                colGen.generateColumnJSP(formBean);
            }

            pw.println("    </ul>");
            pw.println("</div>");

            pw.println("<div id=\"divViewFilter\" style=\"display: none;\">");
            pw.println("    <jsp:include page=\"filter.jsp\"/>");
            pw.println("</div>");

            if (formBean.isChkOnScreen() || formBean.isChkPdf() || formBean.isChkXls())
            {
                pw.println("<div id=\"divViewExport\" style=\"display: none;\">");
                pw.println("    <jsp:include page=\"export.jsp\"/>");
                pw.println("</div>");
            }

            if (formBean.isChkColumns())
            {
                pw.println("<div id=\"divViewColumns\" style=\"display: none;\">");
                pw.println("    <jsp:include page=\"columns.jsp\"/>");
                pw.println("</div>");
            }

            pw.println("<div id=\"divButton\" style=\"display: none\">");
            pw.println("    <input class=\"button\" type=\"button\" id=\"btnApply\" name=\"btnApply\" Value=\"Apply\" onclick=\"javascript: showReport('View', this.form);\" >");
            pw.println("    <input class=\"button\" type=\"button\" id=\"btnPrint\" name=\"btnPrint\" value=\"Print\" onclick=\"javascript: showReport('Print', this.form);\" >");
            pw.println("</div>");
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
