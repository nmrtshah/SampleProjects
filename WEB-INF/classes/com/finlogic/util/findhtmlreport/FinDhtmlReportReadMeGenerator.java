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
public class FinDhtmlReportReadMeGenerator
{
     public void generateReadMe(final FinDhtmlReportDetailEntityBean dEntityBean,final FinDhtmlReportSummaryEntityBean sEntityBean) throws FileNotFoundException
    {
        String tomcatPath = finpack.FinPack.getProperty("tomcat1_path");
        String number = dEntityBean.getSRNo() + "RGV2";
        String filePath = tomcatPath + "/webapps/finstudio/generated/" + number + "/";

        File file = new File(filePath);
        file.mkdirs();
        PrintWriter pw = new PrintWriter(filePath + ".Plugin.xml");
        pw.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        pw.println();
        pw.println("<data>");
        pw.println("    <project>" + dEntityBean.getProjectName() + "</project>");
        pw.println("    <module>" + dEntityBean.getModuleName().toLowerCase(Locale.getDefault()) + "</module>");
        pw.println("    <paramName>" + dEntityBean.getMethodName() + "</paramName>");
        pw.println("</data>");
        pw.close();
    }
}
