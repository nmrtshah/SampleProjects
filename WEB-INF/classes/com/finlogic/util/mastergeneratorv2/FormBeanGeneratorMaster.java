/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.mastergeneratorv2;

import com.finlogic.apps.finstudio.formbean.MasterGeneratorV2FormBean;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author njuser
 */
public class FormBeanGeneratorMaster
{

    public FormBeanGeneratorMaster(MasterGeneratorV2FormBean formBean) throws Exception
    {
        generateFormBeanMaster(formBean);
    }

    public void generateFormBeanMaster(MasterGeneratorV2FormBean formBean) throws Exception
    {
        String moduleName = formBean.getModuleName();
        long number = formBean.getSerialNo();
        String tomcatPath = finpack.FinPack.getProperty("tomcat1_path");
        String filePath = tomcatPath + "/webapps/finstudio/generated/" + number + "/";
        String projectPath = filePath + formBean.getProjectName() + "/WEB-INF/classes/com/finlogic/apps/"
                + formBean.getProjectName().toLowerCase() + "/formbeans/";

        File file = new File(projectPath);
        file.mkdirs();
        PrintWriter pw = new PrintWriter(projectPath + moduleName + "FormBeanMaster.java");

        pw.println("package com.finlogic.apps." + formBean.getProjectName().toLowerCase() + ".formbeans;");
        pw.println("public class " + moduleName + "FormBeanMaster ");
        pw.println("{");
        ArrayList<String> selectedColumns = formBean.getSelectedMasterColumns();
        for (String column : selectedColumns)
        {
//            if (!(column.equals(formBean.getPrimarykeyMaster())))
//            {
                column = column.toLowerCase();
                pw.println("    String " + column + ";");
//            }
        }
//        pw.println("    String " + formBean.getPrimarykeyMaster().toLowerCase() + ";");
        for (String column : selectedColumns)
        {
//            if (!(column.equals(formBean.getPrimarykeyMaster())))
//            {
                String methodName = column.substring(0, 1).toUpperCase() + column.substring(1).toLowerCase();

                pw.println("    public String get" + methodName + "()");
                pw.println("    {");
                pw.println("        return " + column.toLowerCase() + ";");
                pw.println("    }");

                pw.println("    public void set" + methodName + "(String " + column.toLowerCase() + "Var)");
                pw.println("    {");
                pw.println("        " + column.toLowerCase() + " = " + column.toLowerCase() + "Var;");
                pw.println("    }");
//            }
        }
//        String methodName = formBean.getPrimarykeyMaster().substring(0, 1).toUpperCase() + formBean.getPrimarykeyMaster().substring(1).toLowerCase();
//
//        pw.println("    public String get" + methodName + "()");
//        pw.println("    {");
//        pw.println("        return " + formBean.getPrimarykeyMaster().toLowerCase() + ";");
//        pw.println("    }");
//
//        pw.println("    public void set" + methodName + "(String " + formBean.getPrimarykeyMaster().toLowerCase() + "Var)");
//        pw.println("    {");
//        pw.println("        " + formBean.getPrimarykeyMaster().toLowerCase() + " = " + formBean.getPrimarykeyMaster().toLowerCase() + "Var;");
//        pw.println("    }");
          pw.println("}");
        pw.close();
    }
}
