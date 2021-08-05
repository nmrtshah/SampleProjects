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
public class ServiceGenerator
{

    public ServiceGenerator(MasterGeneratorFormBean formBean, ArrayList<String> selectedColumns) throws Exception
    {
        String tomcatPath = finpack.FinPack.getProperty("tomcat1_path");
        long number = formBean.getSerialNo();
        String moduleName = formBean.getModuleName();
        String filePath = tomcatPath + "/webapps/finstudio/generated/" + number + "/";
        String projectPath = filePath + formBean.getProjectName() + "/WEB-INF/classes/com/finlogic/apps/"
                + formBean.getProjectName().toLowerCase() + "/service/";

        File file = new File(projectPath);
        file.mkdirs();
        PrintWriter pw = new PrintWriter(projectPath + moduleName + "Service.java");

        pw.println("package com.finlogic.apps."+formBean.getProjectName().toLowerCase()+".service;");

        pw.println("import com.finlogic.apps." + formBean.getProjectName().toLowerCase() + ".formbeans." + moduleName + "FormBean;");
        pw.println("import com.finlogic.business." + formBean.getProjectName().toLowerCase() + ".entitybeans." + moduleName + "EntityBean;");
        pw.println("import com.finlogic.business." + formBean.getProjectName().toLowerCase() + ".datamanager." + moduleName + "Manager;");
        pw.println("import com.finlogic.util.JSONParser;");
        pw.println("import java.util.List;");
        pw.println("import org.springframework.jdbc.support.rowset.SqlRowSet;");

        pw.println("public class " + moduleName + "Service ");
        pw.println("{");
        pw.println("    public void insert(" + moduleName + "FormBean formBean) throws Exception");
        pw.println("    {");
        pw.println("        " + moduleName + "EntityBean entityBean = fBeanToEBean(formBean);");
        pw.println("        " + moduleName + "Manager manager = new " + moduleName + "Manager();");
        pw.println("        manager.insert(entityBean);");
        pw.println("    }");
        pw.println("    public " + moduleName + "EntityBean fBeanToEBean(" + moduleName + "FormBean formBean) throws Exception");
        pw.println("    {");
        pw.println("        " + moduleName + "EntityBean entityBean = new " + moduleName + "EntityBean();");
        for (String column : selectedColumns)
        {
            if (!(column.equals(formBean.getPrimarykey())))
            {
                String methodName = column.substring(0,1).toUpperCase() + column.substring(1).toLowerCase();
                pw.println("        if(formBean.get" + methodName + "().equals(\"\") || formBean.get" + methodName + "()==null)");
                pw.println("            entityBean.set" + methodName + "(null);");
                pw.println("        else");
                pw.println("            entityBean.set" + methodName + "(formBean.get" + methodName + "());");
            }
        }
        String methodName = formBean.getPrimarykey().substring(0,1).toUpperCase() + formBean.getPrimarykey().substring(1).toLowerCase();
        pw.println("        if(formBean.get" + methodName + "().equals(\"\") || formBean.get" + methodName + "()==null)");
        pw.println("            entityBean.set" + methodName + "(null);");
        pw.println("        else");
        pw.println("            entityBean.set" + methodName + "(formBean.get" + methodName + "());");
        pw.println("        return entityBean;");
        pw.println("    }");
        pw.println("    public String getRecords(" + moduleName + "FormBean formBean, String page, String rows) throws Exception");
        pw.println("    {");
        pw.println("        " + moduleName + "Manager manager = new " + moduleName + "Manager();");
        pw.println("        SqlRowSet srs = manager.getRecords(formBean,page,rows);");
        pw.println("        int rowCount = manager.getRowCount(formBean);");
        pw.println("        JSONParser jsonParser = new JSONParser();");
        pw.println("        return jsonParser.jsonParser(srs, \"" + formBean.getPrimarykey() + "\", rowCount, Integer.parseInt(page), Integer.parseInt(rows));");
        pw.println("    }");
        pw.println("    public List getRecord(String primarykey) throws Exception");
        pw.println("    {");
        pw.println("        " + moduleName + "Manager manager = new " + moduleName + "Manager();");
        pw.println("        return manager.getRecord(primarykey);");
        pw.println("    }");
        pw.println("    public void update(" + moduleName + "FormBean formBean) throws Exception");
        pw.println("    {");
        pw.println("        " + moduleName + "EntityBean entityBean = fBeanToEBean(formBean);");
        pw.println("        " + moduleName + "Manager manager = new " + moduleName + "Manager();");
        pw.println("        manager.update(entityBean);");
        pw.println("    }");
        pw.println("    public void deleteRecords(String []pkValues) throws Exception");
        pw.println("    {");
        pw.println("        " + moduleName + "Manager manager = new " + moduleName + "Manager();");
        pw.println("        manager.deleteRecords(pkValues);");
        pw.println("    }");
        pw.println("}");
        pw.close();
    }
}
