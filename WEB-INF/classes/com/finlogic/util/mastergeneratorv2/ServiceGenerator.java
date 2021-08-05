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
public class ServiceGenerator
{

    public ServiceGenerator(MasterGeneratorV2FormBean formBean) throws Exception
    {
        generateService(formBean);
    }

    public void generateService(MasterGeneratorV2FormBean formBean) throws Exception
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

        pw.println("package com.finlogic.apps." + formBean.getProjectName().toLowerCase() + ".service;");

        pw.println("import com.finlogic.apps." + formBean.getProjectName().toLowerCase() + ".formbeans." + moduleName + "FormBeanMaster;");
        pw.println("import com.finlogic.apps." + formBean.getProjectName().toLowerCase() + ".formbeans." + moduleName + "FormBeanDetail;");
        pw.println("import com.finlogic.business." + formBean.getProjectName().toLowerCase() + ".entitybeans." + moduleName + "EntityBeanMaster;");
        pw.println("import com.finlogic.business." + formBean.getProjectName().toLowerCase() + ".entitybeans." + moduleName + "EntityBeanDetail;");
        pw.println("import com.finlogic.business." + formBean.getProjectName().toLowerCase() + ".datamanager." + moduleName + "Manager;");
        pw.println("import com.finlogic.util.JSONParser;");
        pw.println("import org.springframework.jdbc.support.rowset.SqlRowSet;");
        pw.println("import java.util.ArrayList;");

        pw.println("public class " + moduleName + "Service ");
        pw.println("{");
        pw.println("    public String getDataMaster(String page, String rows) throws Exception");
        pw.println("    {");
        pw.println("        " + moduleName + "Manager manager = new " + moduleName + "Manager();");
        pw.println("        SqlRowSet srs = manager.getDataMaster(page,rows);");
        pw.println("        int rowCount = manager.getRowCountMaster();");
        pw.println("        JSONParser jsonParser = new JSONParser();");
        pw.println("        return jsonParser.jsonParser(srs, \"" + formBean.getPrimarykeyMaster() + "\", rowCount, Integer.parseInt(page), Integer.parseInt(rows));");
        pw.println("    }");
        pw.println("    public String getDataDetail(String foreignKey, String page, String rows) throws Exception");
        pw.println("    {");
        pw.println("        " + moduleName + "Manager manager = new " + moduleName + "Manager();");
        pw.println("        SqlRowSet srs = manager.getDataDetail(foreignKey, page, rows);");
        pw.println("        int rowCount = manager.getRowCountDetail(foreignKey);");
        pw.println("        JSONParser jsonParser = new JSONParser();");
        pw.println("        return jsonParser.jsonParser(srs, \"" + formBean.getPrimarykeyDetail() + "\", rowCount, Integer.parseInt(page), Integer.parseInt(rows));");
        pw.println("    }");
        pw.println("    public ArrayList<String> deleteRecordsMaster(String id[]) throws Exception");
        pw.println("    {");
        pw.println("        " + moduleName + "Manager manager = new " + moduleName + "Manager();");
        pw.println("        return manager.deleteRecordsMaster(id);");
        pw.println("    }");
        pw.println("    public void deleteRecordsDetail(String id[]) throws Exception");
        pw.println("    {");
        pw.println("        " + moduleName + "Manager manager = new " + moduleName + "Manager();");
        pw.println("        manager.deleteRecordsDetail(id);");
        pw.println("    }");
        pw.println("    public void updateMaster(String id, " + moduleName + "FormBeanMaster formBean) throws Exception");
        pw.println("    {");
        pw.println("        " + moduleName + "Manager manager = new " + moduleName + "Manager();");
        pw.println("        " + moduleName + "EntityBeanMaster entityBean = formBeanToEntityBeanMaster(formBean);");
        pw.println("        manager.updateMaster(id, entityBean);");
        pw.println("    }");
        pw.println("    public void updateDetail(String id, " + moduleName + "FormBeanDetail formBean) throws Exception");
        pw.println("    {");
        pw.println("        " + moduleName + "Manager manager = new " + moduleName + "Manager();");
        pw.println("        " + moduleName + "EntityBeanDetail entityBean = formBeanToEntityBeanDetail(formBean);");
        pw.println("        manager.updateDetail(id, entityBean);");
        pw.println("    }");
        pw.println("    public void insertMaster(String id, " + moduleName + "FormBeanMaster formBean) throws Exception");
        pw.println("    {");
        pw.println("        " + moduleName + "Manager manager = new " + moduleName + "Manager();");
        pw.println("        " + moduleName + "EntityBeanMaster entityBean = formBeanToEntityBeanMaster(formBean);");
        pw.println("        manager.insertMaster(id, entityBean);");
        pw.println("    }");
        pw.println("    public void insertDetail(String id, " + moduleName + "FormBeanDetail formBean) throws Exception");
        pw.println("    {");
        pw.println("        " + moduleName + "Manager manager = new " + moduleName + "Manager();");
        pw.println("        " + moduleName + "EntityBeanDetail entityBean = formBeanToEntityBeanDetail(formBean);");
        pw.println("        manager.insertDetail(id, entityBean);");
        pw.println("    }");

        pw.println("    public " + moduleName + "EntityBeanMaster formBeanToEntityBeanMaster(" + moduleName + "FormBeanMaster formBean) throws Exception");
        pw.println("    {");
        pw.println("        " + moduleName + "EntityBeanMaster entityBean = new " + moduleName + "EntityBeanMaster();");
        for (String column : formBean.getSelectedMasterColumns())
        {
//            if (!(column.equals(formBean.getPrimarykeyMaster())))
//            {
                String methodName = column.substring(0, 1).toUpperCase() + column.substring(1).toLowerCase();
                pw.println("        if(formBean.get" + methodName + "().equals(\"\") || formBean.get" + methodName + "()==null)");
                pw.println("            entityBean.set" + methodName + "(null);");
                pw.println("        else");
                pw.println("            entityBean.set" + methodName + "(formBean.get" + methodName + "());");
//            }
        }
//        String methodName = formBean.getPrimarykeyMaster().substring(0, 1).toUpperCase() + formBean.getPrimarykeyMaster().substring(1).toLowerCase();
//        pw.println("        if(formBean.get" + methodName + "().equals(\"\") || formBean.get" + methodName + "()==null)");
//        pw.println("            entityBean.set" + methodName + "(null);");
//        pw.println("        else");
//        pw.println("            entityBean.set" + methodName + "(formBean.get" + methodName + "());");
        pw.println("        return entityBean;");
        pw.println("    }");

        pw.println("    public " + moduleName + "EntityBeanDetail formBeanToEntityBeanDetail(" + moduleName + "FormBeanDetail formBean) throws Exception");
        pw.println("    {");
        pw.println("        " + moduleName + "EntityBeanDetail entityBean = new " + moduleName + "EntityBeanDetail();");
        for (String column : formBean.getSelectedDetailColumns())
        {
//            if (!(column.equals(formBean.getPrimarykeyDetail())) && !(column.equals(formBean.getForeignkeyDetail())))
//            {
                String methodNameDetail = column.substring(0, 1).toUpperCase() + column.substring(1).toLowerCase();
                pw.println("        if(formBean.get" + methodNameDetail + "().equals(\"\") || formBean.get" + methodNameDetail + "()==null)");
                pw.println("            entityBean.set" + methodNameDetail + "(null);");
                pw.println("        else");
                pw.println("            entityBean.set" + methodNameDetail + "(formBean.get" + methodNameDetail + "());");
//            }
        }
//        String methodNameDetail = formBean.getPrimarykeyDetail().substring(0, 1).toUpperCase() + formBean.getPrimarykeyDetail().substring(1).toLowerCase();
//        pw.println("        if(formBean.get" + methodNameDetail + "().equals(\"\") || formBean.get" + methodNameDetail + "()==null)");
//        pw.println("            entityBean.set" + methodNameDetail + "(null);");
//        pw.println("        else");
//        pw.println("            entityBean.set" + methodNameDetail + "(formBean.get" + methodNameDetail + "());");
//
//        methodNameDetail = formBean.getForeignkeyDetail().substring(0, 1).toUpperCase() + formBean.getForeignkeyDetail().substring(1).toLowerCase();
//        pw.println("        if(formBean.get" + methodNameDetail + "().equals(\"\") || formBean.get" + methodNameDetail + "()==null)");
//        pw.println("            entityBean.set" + methodNameDetail + "(null);");
//        pw.println("        else");
//        pw.println("            entityBean.set" + methodNameDetail + "(formBean.get" + methodNameDetail + "());");

        pw.println("        return entityBean;");
        pw.println("    }");

        pw.println("}");
        pw.close();
    }
}
