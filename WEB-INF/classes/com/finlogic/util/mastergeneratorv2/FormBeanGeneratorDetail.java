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
public class FormBeanGeneratorDetail
{

    public FormBeanGeneratorDetail(MasterGeneratorV2FormBean formBean) throws Exception
    {
        generateFormBeanDetail(formBean);
    }

    public void generateFormBeanDetail(MasterGeneratorV2FormBean formBean) throws Exception
    {
        String moduleName = formBean.getModuleName();
        long number = formBean.getSerialNo();
        String tomcatPath = finpack.FinPack.getProperty("tomcat1_path");
        String filePath = tomcatPath + "/webapps/finstudio/generated/" + number + "/";
        String projectPath = filePath + formBean.getProjectName() + "/WEB-INF/classes/com/finlogic/apps/"
                + formBean.getProjectName().toLowerCase() + "/formbeans/";

        File file = new File(projectPath);
        file.mkdirs();
        PrintWriter pw = new PrintWriter(projectPath + moduleName + "FormBeanDetail.java");

        pw.println("package com.finlogic.apps." + formBean.getProjectName().toLowerCase() + ".formbeans;");
        pw.println("public class " + moduleName + "FormBeanDetail ");
        pw.println("{");
        ArrayList<String> selectedColumns = formBean.getSelectedDetailColumns();
        for (String column : selectedColumns)
        {
//            if (!(column.equals(formBean.getPrimarykeyDetail())) && !(column.equals(formBean.getForeignkeyDetail())))
//            {
                column = column.toLowerCase();
                pw.println("    String " + column + ";");
//            }
        }
//        pw.println("    String " + formBean.getPrimarykeyDetail().toLowerCase() + ";");
//        pw.println("    String " + formBean.getForeignkeyDetail().toLowerCase() + ";");
        for (String column : selectedColumns)
        {
//            if (!(column.equals(formBean.getPrimarykeyDetail())) && !(column.equals(formBean.getForeignkeyDetail())))
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
//        String methodName = formBean.getPrimarykeyDetail().substring(0, 1).toUpperCase() + formBean.getPrimarykeyDetail().substring(1).toLowerCase();
//
//        pw.println("    public String get" + methodName + "()");
//        pw.println("    {");
//        pw.println("        return " + formBean.getPrimarykeyDetail().toLowerCase() + ";");
//        pw.println("    }");
//
//        pw.println("    public void set" + methodName + "(String " + formBean.getPrimarykeyDetail().toLowerCase() + "Var)");
//        pw.println("    {");
//        pw.println("        " + formBean.getPrimarykeyDetail().toLowerCase() + " = " + formBean.getPrimarykeyDetail().toLowerCase() + "Var;");
//        pw.println("    }");
//
//        methodName = formBean.getForeignkeyDetail().substring(0, 1).toUpperCase() + formBean.getForeignkeyDetail().substring(1).toLowerCase();
//
//        pw.println("    public String get" + methodName + "()");
//        pw.println("    {");
//        pw.println("        return " + formBean.getForeignkeyDetail().toLowerCase() + ";");
//        pw.println("    }");
//
//        pw.println("    public void set" + methodName + "(String " + formBean.getForeignkeyDetail().toLowerCase() + "Var)");
//        pw.println("    {");
//        pw.println("        " + formBean.getForeignkeyDetail().toLowerCase() + " = " + formBean.getForeignkeyDetail().toLowerCase() + "Var;");
//        pw.println("    }");
        pw.println("}");
        pw.close();
    }
}
