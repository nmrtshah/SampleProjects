/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.finreport;

import com.finlogic.apps.finstudio.finreport.FinReportFormBean;
import java.io.File;
import java.io.PrintWriter;

/**
 *
 * @author njuser
 */
public class ReportOutPutGenerator
{

    public ReportOutPutGenerator(FinReportFormBean formBean) throws Exception
    {
        String projectName = formBean.getCmbProjectName();
        String tomcatPath = finpack.FinPack.getProperty("tomcat1_path");
        String moduleName = formBean.getTxtModuleName();
        String number = formBean.getSRNo() + "RGV2";
        String filePath = tomcatPath + "/webapps/finstudio/generated/" + number + "/";
        String projectPath = filePath + projectName.toLowerCase() + "/WEB-INF/jsp/" + moduleName.toLowerCase() + "/";

        File file = new File(projectPath);
        file.mkdirs();
        PrintWriter pw = new PrintWriter(projectPath + "report.jsp");

        pw.println("${json}");
        pw.println("${jaspertopdf}");
        pw.println("${jaspertoexcel}");

        pw.close();
    }
}
