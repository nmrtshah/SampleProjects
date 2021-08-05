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
public class EntityBeanGenerator {
    public EntityBeanGenerator(MasterGeneratorFormBean formBean, ArrayList<String> allColumns) throws Exception
    {
        String moduleName = formBean.getModuleName();
        long number = formBean.getSerialNo();
        String tomcatPath = finpack.FinPack.getProperty("tomcat1_path");
        String filePath = tomcatPath + "/webapps/finstudio/generated/" + number + "/";
        String projectPath = filePath +formBean.getProjectName() + "/WEB-INF/classes/com/finlogic/business/"
                + formBean.getProjectName().toLowerCase()+"/entitybeans/";

        File file = new File(projectPath);
        file.mkdirs();
        PrintWriter pw = new PrintWriter(projectPath + moduleName + "EntityBean.java");

        pw.println("package com.finlogic.business."+formBean.getProjectName().toLowerCase()+".entitybeans;");

        pw.println("public class "+moduleName+"EntityBean ");
        pw.println("{");
        for(String column: allColumns)
        {
            column = column.toLowerCase();
            pw.println("    String "+column+";");
        }
        for(String column: allColumns)
        {
            String methodName=column.substring(0,1).toUpperCase()+column.substring(1).toLowerCase();

            pw.println("    public String get"+methodName+"()");
            pw.println("    {");
            pw.println("        return "+column.toLowerCase()+";");
            pw.println("    }");

            pw.println("    public void set"+methodName+"(String "+column.toLowerCase()+"Var)");
            pw.println("    {");
            pw.println("        "+column.toLowerCase()+" = "+column.toLowerCase()+"Var;");
            pw.println("    }");
        }
        pw.println("}");
        pw.close();
    }
}
