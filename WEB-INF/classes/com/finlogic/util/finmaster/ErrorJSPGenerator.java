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
public class ErrorJSPGenerator
{

    public void generateErrorJSP(final FinMasterFormBean formBean) throws FileNotFoundException
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
        pw = new PrintWriter(projectPath + "error.jsp");

        pw.println("<%-- TCIGBF --%>");
        pw.println();
        pw.println("<%@page contentType=\"text/html\" pageEncoding=\"UTF-8\"%>");
        pw.println("<%@taglib uri=\"http://java.sun.com/jsp/jstl/core\" prefix=\"c\" %>");
        pw.println();
        pw.println("<html>");
        pw.println("    <head>");
        pw.println("        <title>JSP Error Page</title>");
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
