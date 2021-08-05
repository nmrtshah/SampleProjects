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
public class BottomJSPGenerator
{

    public void generateBottomJSP(final FinMasterFormBean formBean) throws FileNotFoundException
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
        pw = new PrintWriter(projectPath + "bottom.jsp");

        pw.println("<%-- TCIGBF --%>");
        pw.println();
        pw.println("<div class=\"footer\">");
        pw.println("    <div class=\"footer_allrights\">All Rights Reserved to NJ INDIA INVEST</div>");
        pw.println("    <div class=\"footer_links\"><a href=\"#\">Privacy Policy</a> | <a href=\"#\">Terms of Use</a></div>");
        pw.println("</div>");
        pw.close();
    }
}
