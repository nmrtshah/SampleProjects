/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.finlogic.util.reportgenerator;

import com.finlogic.apps.finstudio.formbean.ReportGeneratorFormBean;
import java.io.File;
import java.io.PrintWriter;

/**
 *
 * @author njuser
 */
public class FormBeanGenerator
{
    public FormBeanGenerator(ReportGeneratorFormBean reportGeneratorForm) throws Exception
    {
        String moduleName = reportGeneratorForm.getModuleName();
        long number = reportGeneratorForm.getSerialNo();
        String tomcatPath = finpack.FinPack.getProperty("tomcat1_path");
        String filePath = tomcatPath + "/webapps/finstudio/generated/" + number + "/";
        String projectPath = filePath +reportGeneratorForm.getProjectName() + "/WEB-INF/classes/com/finlogic/apps/"
                + reportGeneratorForm.getProjectName().toLowerCase()+"/formbeans/";

        File file = new File(projectPath);
        file.mkdirs();
        PrintWriter pw = new PrintWriter(projectPath + moduleName + "FormBean.java");

        String inputNames[] = reportGeneratorForm.getInputName();
        pw.println("package com.finlogic.apps." + reportGeneratorForm.getProjectName().toLowerCase() + ".formbeans;");
        pw.println("public class "+moduleName+"FormBean ");
        pw.println("{");
        for(String inputName: inputNames)
        {
            inputName = inputName.toLowerCase();
            pw.println("    String "+inputName+";");
        }
        for(String inputName: inputNames)
        {
            String methodName=inputName.substring(0,1).toUpperCase()+inputName.substring(1).toLowerCase();

            pw.println("    public String get"+methodName+"()");
            pw.println("    {");
            pw.println("        return "+inputName.toLowerCase()+";");
            pw.println("    }");

            pw.println("    public void set"+methodName+"(String "+inputName.toLowerCase()+"Var)");
            pw.println("    {");
            pw.println("        "+inputName.toLowerCase()+" = "+inputName.toLowerCase()+"Var;");
            pw.println("    }");
        }
        pw.println("}");
        pw.close();
    }
}
