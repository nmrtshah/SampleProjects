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
 * @author njuser
 */
public class ReadMeGenerator
{

    public void generateReadMe(final FinMasterFormBean formBean) throws FileNotFoundException
    {
        String tomcatPath;
        tomcatPath = finpack.FinPack.getProperty("tomcat1_path");
        String number;
        number = formBean.getSrNo() + "MGV2";
        String filePath;
        filePath = tomcatPath + "/webapps/finstudio/generated/" + number + "/";

        File file;
        file = new File(filePath);
        file.mkdirs();
        PrintWriter pw;
        pw = new PrintWriter(filePath + ".Plugin.xml");
        pw.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        pw.println();
        pw.println("<data>");
        pw.println("    <project>" + formBean.getCmbProjectName() + "</project>");
        pw.println("    <module>" + formBean.getTxtModuleName().toLowerCase(Locale.getDefault()) + "</module>");
        pw.println("    <paramName>" + formBean.getTxtParamName() + "</paramName>");
        if (formBean.isChkDelete())
        {
            pw.println("    <deleteTab>TRUE</deleteTab>");
        }
        else
        {
            pw.println("    <deleteTab>FALSE</deleteTab>");
        }
        if (formBean.isChkView())
        {
            pw.println("    <viewTab>TRUE</viewTab>");
        }
        else
        {
            pw.println("    <viewTab>FALSE</viewTab>");
        }
        pw.println("</data>");
        pw.close();
    }
}
