/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.findhtmlreport;

import com.finlogic.business.finstudio.findhtmlreport.FinDhtmlReportDetailEntityBean;
import com.finlogic.business.finstudio.findhtmlreport.FinDhtmlReportSummaryEntityBean;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Locale;

/**
 *
 * @author njuser
 */
public class FinDhtmlReportErrorGenerator
{

    public void generateReportError(final FinDhtmlReportDetailEntityBean dEntityBean, final FinDhtmlReportSummaryEntityBean sEntityBean) throws FileNotFoundException
    {
        String projectName = dEntityBean.getProjectName();
        String tomcatPath = finpack.FinPack.getProperty("tomcat1_path");
        String moduleName = dEntityBean.getModuleName();
        String number = dEntityBean.getSRNo() + "RGV2";
        String filePath = tomcatPath + "/webapps/finstudio/generated/" + number + "/";
        String projectPath = filePath + projectName.toLowerCase(Locale.getDefault()) + "/WEB-INF/jsp/" + moduleName.toLowerCase(Locale.getDefault()) + "/";

        File file = new File(projectPath);
        file.mkdirs();
        PrintWriter pw = new PrintWriter(projectPath + "error.jsp");

        pw.println("<%-- TCIGBF --%>");
        pw.println();
        pw.println("<%@page contentType=\"text/html\" pageEncoding=\"UTF-8\"%>");
        pw.println("<%@taglib uri=\"http://java.sun.com/jsp/jstl/core\" prefix=\"c\" %>");
        pw.println();
        pw.println("<html>");
        pw.println("    <head>");
        pw.println("        <title>JSP error Page</title>");
        pw.println("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" >");
        pw.println("    </head>");
        pw.println("    <body>");
        pw.println("        <div>");
        pw.println("            <c:if test=\"${error ne null}\">");
        pw.println("                Technical Problem Occurred while processing Request. Please Try Later.");
        pw.println("            </c:if>");
        pw.println("        </div>");
        pw.println("    </body>");
        pw.println("</html>");
        pw.close();
    }
}
