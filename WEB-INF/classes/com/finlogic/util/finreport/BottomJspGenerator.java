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
public class BottomJspGenerator
{

    public void generateBottomJsp(final FinReportDetailEntityBean dEntityBean, final FinReportSummaryEntityBean sEntityBean) throws FileNotFoundException
    {
        String projectName = dEntityBean.getProjectName();
        String tomcatPath = finpack.FinPack.getProperty("tomcat1_path");
        String moduleName = dEntityBean.getModuleName();
        String number = dEntityBean.getSRNo() + "RGV2";
        String filePath = tomcatPath + "/webapps/finstudio/generated/" + number + "/";
        String projectPath = filePath + projectName.toLowerCase(Locale.getDefault()) + "/WEB-INF/jsp/" + moduleName.toLowerCase(Locale.getDefault()) + "/";

        File file = new File(projectPath);
        file.mkdirs();
        PrintWriter pw = new PrintWriter(projectPath + "bottom.jsp");

        pw.println("<%-- TCIGBF --%>");
        pw.println();
        pw.println("<div class=\"footer\">");
        pw.println("    <div class=\"footer_allrights\">All Rights Reserved to NJ INDIA INVEST</div>");
        pw.println("    <div class=\"footer_links\"><a href=\"#\">Privacy Policy</a> | <a href=\"#\">Terms of Use</a></div>");
        pw.println("</div>");
        pw.close();
    }
}
