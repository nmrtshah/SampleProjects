/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.finlogic.util.mastergeneratorv2;

import com.finlogic.apps.finstudio.formbean.MasterGeneratorV2FormBean;
import java.io.File;
import java.io.PrintWriter;

/**
 *
 * @author njuser
 */
public class ManagerGenerator {
    public ManagerGenerator(MasterGeneratorV2FormBean formBean) throws Exception
    {
        generateManager(formBean);
    }

    public void generateManager(MasterGeneratorV2FormBean formBean) throws Exception
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
        pw.println("import com.finlogic.business." + formBean.getProjectName().toLowerCase() + ".entitybeans." + formBean.getModuleName() + "EntityBeanMaster;");
        pw.println("import com.finlogic.business." + formBean.getProjectName().toLowerCase() + ".entitybeans." + formBean.getModuleName() + "EntityBeanDetail;");
        pw.println("import org.springframework.jdbc.support.rowset.SqlRowSet;");
        pw.println("import java.util.ArrayList;");
        pw.println("import org.springframework.dao.DataIntegrityViolationException;");
        pw.println("public class " + moduleName + "Manager ");
        pw.println("{");
        pw.println("    public SqlRowSet getDataMaster(String page, String rows) throws Exception");
        pw.println("    {");
        pw.println("        " + moduleName + "DataManager dataManager=new " + moduleName + "DataManager();");
        pw.println("        return dataManager.getDataMaster(page, rows);");
        pw.println("    }");
        pw.println("    public int getRowCountMaster() throws Exception");
        pw.println("    {");
        pw.println("        " + moduleName + "DataManager dataManager=new " + moduleName + "DataManager();");
        pw.println("        SqlRowSet srs = dataManager.getRowCountMaster();");
        pw.println("        if(srs.next())");
        pw.println("            return srs.getInt(1);");
        pw.println("        else");
        pw.println("            return 0;");
        pw.println("    }");
        pw.println("    public SqlRowSet getDataDetail(String foreignKey, String page, String rows) throws Exception");
        pw.println("    {");
        pw.println("        " + moduleName + "DataManager dataManager=new " + moduleName + "DataManager();");
        pw.println("        return dataManager.getDataDetail(foreignKey, page, rows);");
        pw.println("    }");
        pw.println("    public int getRowCountDetail(String foreignKey) throws Exception");
        pw.println("    {");
        pw.println("        " + moduleName + "DataManager dataManager=new " + moduleName + "DataManager();");
        pw.println("        SqlRowSet srs = dataManager.getRowCountDetail(foreignKey);");
        pw.println("        if(srs.next())");
        pw.println("            return srs.getInt(1);");
        pw.println("        else");
        pw.println("            return 0;");
        pw.println("    }");
        pw.println("    public ArrayList<String> deleteRecordsMaster(String id[]) throws Exception");
        pw.println("    {");
        pw.println("        " + moduleName + "DataManager dataManager=new " + moduleName + "DataManager();");
        pw.println("        ArrayList<String> undeletedId = new ArrayList<String>();");
        pw.println("        for (int i = 0; i < id.length; i++)");
        pw.println("        {");
        pw.println("            try");
        pw.println("            {");
        pw.println("                dataManager.deleteRecordMaster(id[i]);");
        pw.println("            }");
        pw.println("            catch (DataIntegrityViolationException ex)");
        pw.println("            {");
        pw.println("                undeletedId.add(id[i]);");
        pw.println("            }");
        pw.println("        }");
        pw.println("        return undeletedId;");
        pw.println("    }");
        pw.println("    public void deleteRecordsDetail(String id[]) throws Exception");
        pw.println("    {");
        pw.println("        " + moduleName + "DataManager dataManager=new " + moduleName + "DataManager();");
        pw.println("        for(int i=0;i<id.length;i++)");
        pw.println("        {");
        pw.println("            dataManager.deleteRecordDetail(id[i]);");
        pw.println("        }");
        pw.println("    }");
        pw.println("    public void updateMaster(String id, " + moduleName + "EntityBeanMaster entityBean) throws Exception");
        pw.println("    {");
        pw.println("        " + moduleName + "DataManager dataManager=new " + moduleName + "DataManager();");
        pw.println("        dataManager.updateMaster(id, entityBean);");
        pw.println("    }");
        pw.println("    public void updateDetail(String id, " + moduleName + "EntityBeanDetail entityBean) throws Exception");
        pw.println("    {");
        pw.println("        " + moduleName + "DataManager dataManager=new " + moduleName + "DataManager();");
        pw.println("        dataManager.updateDetail(id, entityBean);");
        pw.println("    }");
        pw.println("    public void insertMaster(String id, " + moduleName + "EntityBeanMaster entityBean) throws Exception");
        pw.println("    {");
        pw.println("        " + moduleName + "DataManager dataManager=new " + moduleName + "DataManager();");
        pw.println("        dataManager.insertMaster(id, entityBean);");
        pw.println("    }");
        pw.println("    public void insertDetail(String id, " + moduleName + "EntityBeanDetail entityBean) throws Exception");
        pw.println("    {");
        pw.println("        " + moduleName + "DataManager dataManager=new " + moduleName + "DataManager();");
        pw.println("        dataManager.insertDetail(id, entityBean);");
        pw.println("    }");
        pw.println("}");
        pw.close();
    }
}
