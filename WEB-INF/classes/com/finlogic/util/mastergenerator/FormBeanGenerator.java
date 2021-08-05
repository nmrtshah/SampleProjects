/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.mastergenerator;

import com.finlogic.apps.finstudio.formbean.MasterGeneratorFormBean;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author njuser
 */
public class FormBeanGenerator
{

    public FormBeanGenerator(MasterGeneratorFormBean formBean, ArrayList<String> selectedColumns) throws Exception
    {
        String moduleName = formBean.getModuleName();
        long number = formBean.getSerialNo();
        String tomcatPath = finpack.FinPack.getProperty("tomcat1_path");
        String filePath = tomcatPath + "/webapps/finstudio/generated/" + number + "/";
        String projectPath = filePath + formBean.getProjectName() + "/WEB-INF/classes/com/finlogic/apps/"
                + formBean.getProjectName().toLowerCase() + "/formbeans/";

        File file = new File(projectPath);
        file.mkdirs();
        PrintWriter pw = new PrintWriter(projectPath + moduleName + "FormBean.java");

        pw.println("package com.finlogic.apps." + formBean.getProjectName().toLowerCase() + ".formbeans;");
        pw.println("public class " + moduleName + "FormBean ");
        pw.println("{");
        for (String column : selectedColumns)
        {
            if (!(column.equals(formBean.getPrimarykey())))
            {
                column = column.toLowerCase();
                pw.println("    String " + column + ";");
            }
        }
        pw.println("    String " + formBean.getPrimarykey().toLowerCase() + ";");
        for (String column : selectedColumns)
        {
            if (!(column.equals(formBean.getPrimarykey())))
            {
                String methodName = column.substring(0, 1).toUpperCase() + column.substring(1).toLowerCase();

                pw.println("    public String get" + methodName + "()");
                pw.println("    {");
                pw.println("        return " + column.toLowerCase() + ";");
                pw.println("    }");

                pw.println("    public void set" + methodName + "(String " + column.toLowerCase() + "Var)");
                pw.println("    {");
                pw.println("        " + column.toLowerCase() + " = " + column.toLowerCase() + "Var;");
                pw.println("    }");
            }
        }
        String methodName = formBean.getPrimarykey().substring(0, 1).toUpperCase() + formBean.getPrimarykey().substring(1).toLowerCase();

        pw.println("    public String get" + methodName + "()");
        pw.println("    {");
        pw.println("        return " + formBean.getPrimarykey().toLowerCase() + ";");
        pw.println("    }");

        pw.println("    public void set" + methodName + "(String " + formBean.getPrimarykey().toLowerCase() + "Var)");
        pw.println("    {");
        pw.println("        " + formBean.getPrimarykey().toLowerCase() + " = " + formBean.getPrimarykey().toLowerCase() + "Var;");
        pw.println("    }");
        pw.println("}");
        pw.close();
    }
}
