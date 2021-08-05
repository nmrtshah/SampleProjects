/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.mastergenerator;

import com.finlogic.apps.finstudio.formbean.MasterGeneratorFormBean;
import java.io.File;
import java.io.PrintWriter;

/**
 *
 * @author njuser
 */
public class ModelGenerator
{
    public ModelGenerator(MasterGeneratorFormBean formBean) throws Exception
    {
        String tomcatPath = finpack.FinPack.getProperty("tomcat1_path");
        long number = formBean.getSerialNo();
        String moduleName = formBean.getModuleName();
        String filePath = tomcatPath + "/webapps/finstudio/generated/" + number + "/";
        String projectPath = filePath + formBean.getProjectName() + "/WEB-INF/classes/com/finlogic/business/"
                + formBean.getProjectName().toLowerCase() + "/datamanager/";

        File file = new File(projectPath);
        file.mkdirs();
        PrintWriter pw = new PrintWriter(projectPath + moduleName + "Manager.java");

        pw.println("package com.finlogic.business." + formBean.getProjectName().toLowerCase() + ".datamanager;");

        pw.println("import com.finlogic.apps." + formBean.getProjectName().toLowerCase() + ".formbeans." + formBean.getModuleName() + "FormBean;");
        pw.println("import com.finlogic.business." + formBean.getProjectName().toLowerCase() + ".entitybeans." + formBean.getModuleName() + "EntityBean;");
        pw.println("import java.util.List;");
        pw.println("import org.springframework.jdbc.support.rowset.SqlRowSet;");
        pw.println("public class " + moduleName + "Manager {");
        pw.println("    public void insert(" + moduleName + "EntityBean entityBean) throws Exception");
        pw.println("    {");
        pw.println("        " + moduleName + "DataManager dataManager = new " + moduleName + "DataManager();");
        pw.println("        dataManager.insert(entityBean);");
        pw.println("    }");

        pw.println("    public SqlRowSet getRecords(" + moduleName + "FormBean formBean, String page, String rows)");
        pw.println("    {");
        pw.println("        " + moduleName + "DataManager dataManager = new " + moduleName + "DataManager();");
        pw.println("        return dataManager.getRecords(formBean, page, rows);");
        pw.println("    }");
        pw.println("    public int getRowCount(" + moduleName + "FormBean formBean)");
        pw.println("    {");
        pw.println("        " + moduleName + "DataManager dataManager = new " + moduleName + "DataManager();");
        pw.println("        SqlRowSet srs = dataManager.getDataRowCount(formBean);");
        pw.println("        if(srs.next())");
        pw.println("            return srs.getInt(1);");
        pw.println("        else");
        pw.println("            return 0;");
        pw.println("    }");
        pw.println("    public List getRecord(String primarykey) throws Exception");
        pw.println("    {");
        pw.println("        " + moduleName + "DataManager dataManager = new " + moduleName + "DataManager();");
        pw.println("        return dataManager.getRecord(primarykey);");
        pw.println("    }");
        pw.println("    public void update(" + moduleName + "EntityBean entityBean) throws Exception");
        pw.println("    {");
        pw.println("        " + moduleName + "DataManager dataManager = new " + moduleName + "DataManager();");
        pw.println("        dataManager.update(entityBean);");
        pw.println("    }");
        pw.println("    public void deleteRecords(String []pkValues) throws Exception");
        pw.println("    {");
        pw.println("       " + moduleName + "DataManager dataManager = new " + moduleName + "DataManager();");
        pw.println("       for(String pk : pkValues)");
        pw.println("           dataManager.deleteRecord(pk);");
        pw.println("    }");
        pw.println("}");
        pw.close();
    }
}
