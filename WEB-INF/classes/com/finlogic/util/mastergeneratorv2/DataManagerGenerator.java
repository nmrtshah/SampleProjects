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
public class DataManagerGenerator
{

    public DataManagerGenerator(MasterGeneratorV2FormBean formBean) throws Exception
    {
        generateDataManager(formBean);
    }

    public void generateDataManager(MasterGeneratorV2FormBean formBean) throws Exception
    {
        String moduleName = formBean.getModuleName();
        long number = formBean.getSerialNo();
        String tomcatPath = finpack.FinPack.getProperty("tomcat1_path");
        String filePath = tomcatPath + "/webapps/finstudio/generated/" + number + "/";
        String projectPath = filePath + formBean.getProjectName() + "/WEB-INF/classes/com/finlogic/business/"
                + formBean.getProjectName().toLowerCase() + "/datamanager/";

        File file = new File(projectPath);
        file.mkdirs();
        String datamanagerPath = projectPath + moduleName + "DataManager.java";
        PrintWriter pw = new PrintWriter(datamanagerPath);

        String dbType = formBean.getDatabaseType();
        String selectMasterTebleName = "";
        String selectDetailTebleName = "";
        if (dbType.equals("MYSQL"))
        {
            String sch_tab[] = formBean.getSelectMasterTableName().split("-");
            selectMasterTebleName = sch_tab[1];
        }
        else
        {
            selectMasterTebleName = formBean.getSelectMasterTableName();
        }
        if (dbType.equals("MYSQL"))
        {
            String sch_tab[] = formBean.getSelectDetailTableName().split("-");
            selectDetailTebleName = sch_tab[1];
        }
        else
        {
            selectDetailTebleName = formBean.getSelectDetailTableName();
        }
        pw.println("package com.finlogic.business." + formBean.getProjectName().toLowerCase() + ".datamanager;");

        pw.println("import com.finlogic.business." + formBean.getProjectName().toLowerCase() + ".entitybeans." + moduleName + "EntityBeanMaster;");
        pw.println("import com.finlogic.business." + formBean.getProjectName().toLowerCase() + ".entitybeans." + moduleName + "EntityBeanDetail;");
        pw.println("import com.finlogic.util.SQLService;");
        pw.println("import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;");
        pw.println("import org.springframework.jdbc.core.namedparam.SqlParameterSource;");
        pw.println("import org.springframework.jdbc.support.rowset.SqlRowSet;");

        pw.println("public class " + moduleName + "DataManager");
        pw.println("{");

        pw.println("    private SQLService sqlService = new SQLService();");
        pw.println("    private String aliasName = \"" + formBean.getAliasName() + "\";");
        pw.println("    public SqlRowSet getDataMaster(String page, String rows) throws Exception");
        pw.println("    {");
        ArrayList<String> allMasterColumns = formBean.getMasterColumns();
        if (dbType.equals("MYSQL"))
        {
            pw.print("      String query=\"select ");
            for (int i = 0; i < allMasterColumns.size(); i++)
            {
                if (i == allMasterColumns.size() - 1)
                {
                    pw.print(allMasterColumns.get(i));
                }
                else
                {
                    pw.print(allMasterColumns.get(i) + ", ");
                }
            }
            pw.println(" from " + selectMasterTebleName + " limit \"+((Long.parseLong(page)*Long.parseLong(rows))-Long.parseLong(rows)) + \",\" + rows;");
        }
        else
        {
            pw.print("      String query=\"SELECT * FROM (SELECT ROWNUM ROWNUM1,X1.* FROM ( select");

            for (int i = 0; i < allMasterColumns.size(); i++)
            {
                if (i == allMasterColumns.size() - 1)
                {
                    pw.print(allMasterColumns.get(i));
                }
                else
                {
                    pw.print(allMasterColumns.get(i) + ", ");
                }
            }
            pw.print(" from " + selectMasterTebleName);
            pw.print(") X1) ");
            pw.println("WHERE ROWNUM1 BETWEEN \"+((Long.parseLong(page)*Long.parseLong(rows))-Long.parseLong(rows)+1) + \" AND \" + (Long.parseLong(page)*Long.parseLong(rows));");
        }
        pw.println("        return sqlService.getRowSet(aliasName, query);");
        pw.println("    }");

        pw.println("    public SqlRowSet getRowCountMaster() throws Exception");
        pw.println("    {");
        pw.print("        String query=\"select count(*) from (select ");
        for (int i = 0; i < allMasterColumns.size(); i++)
        {
            if (i == allMasterColumns.size() - 1)
            {
                pw.print(allMasterColumns.get(i));
            }
            else
            {
                pw.print(allMasterColumns.get(i) + ", ");
            }
        }
        pw.println(" from " + selectMasterTebleName + ")X\";");
        pw.println("        return sqlService.getRowSet(aliasName, query);");
        pw.println("    }");

        pw.println("    public SqlRowSet getDataDetail(String foreignKey, String page, String rows) throws Exception");
        pw.println("    {");
        ArrayList<String> allDetailColumns = formBean.getDetailColumns();
        if (dbType.equals("MYSQL"))
        {
            pw.print("        String query=\"select ");
            for (int i = 0; i < allDetailColumns.size(); i++)
            {
                if (i == allDetailColumns.size() - 1)
                {
                    pw.print(allDetailColumns.get(i));
                }
                else
                {
                    pw.print(allDetailColumns.get(i) + ", ");
                }
            }
            pw.println(" from " + selectDetailTebleName + " where " + formBean.getForeignkeyDetail() + " = '\"+foreignKey+\"' limit \"+((Long.parseLong(page)*Long.parseLong(rows))-Long.parseLong(rows)) + \",\" + rows;");
        }
        else
        {
            pw.print("        String query=\"SELECT * FROM (SELECT ROWNUM ROWNUM1,X1.* FROM ( select");

            for (int i = 0; i < allDetailColumns.size(); i++)
            {
                if (i == allDetailColumns.size() - 1)
                {
                    pw.print(allDetailColumns.get(i));
                }
                else
                {
                    pw.print(allDetailColumns.get(i) + ", ");
                }
            }
            pw.print(" from " + selectDetailTebleName);
            pw.print(" WHERE " + formBean.getForeignkeyDetail() + " = '\"+foreignKey+\"') X1) ");
            pw.println("WHERE ROWNUM1 BETWEEN \"+((Long.parseLong(page)*Long.parseLong(rows))-Long.parseLong(rows)+1) + \" AND \" + (Long.parseLong(page)*Long.parseLong(rows));");
        }
        pw.println("        return sqlService.getRowSet(aliasName, query);");
        pw.println("    }");

        pw.println("    public SqlRowSet getRowCountDetail(String foreignKey) throws Exception");
        pw.println("    {");
        pw.print("        String query=\"select count(*) from (select ");
        for (int i = 0; i < allDetailColumns.size(); i++)
        {
            if (i == allDetailColumns.size() - 1)
            {
                pw.print(allDetailColumns.get(i));
            }
            else
            {
                pw.print(allDetailColumns.get(i) + ", ");
            }
        }
        pw.println(" from " + selectDetailTebleName + " WHERE " + formBean.getForeignkeyDetail() + " = '\"+foreignKey+\"')X\";");
        pw.println("        return sqlService.getRowSet(aliasName, query);");
        pw.println("    }");

        pw.println("    public void deleteRecordMaster(String id) throws Exception");
        pw.println("    {");
        pw.println("        String sqlQuery = \"delete from " + selectMasterTebleName + " where " + formBean.getPrimarykeyMaster() + " = '\"+id+\"' \";");
        pw.println("        sqlService.persist(aliasName, sqlQuery);");
        pw.println("    }");

        pw.println("    public void deleteRecordDetail(String id) throws Exception");
        pw.println("    {");
        pw.println("        String sqlQuery = \"delete from " + selectDetailTebleName + " where " + formBean.getPrimarykeyDetail() + " = '\"+id+\"' \";");
        pw.println("        sqlService.persist(aliasName, sqlQuery);");
        pw.println("    }");

        pw.println("    public void updateMaster(String id, " + moduleName + "EntityBeanMaster entityBean) throws Exception");
        pw.println("    {");
        pw.println("        SqlParameterSource sps = null;");
        pw.println("        sps = new BeanPropertySqlParameterSource(entityBean);");
        pw.print("        String sqlQuery = \"update " + selectMasterTebleName + " set ");
        ArrayList<String> selectedMasterColumns = formBean.getSelectedMasterColumns();
        for (int i = 0; i < selectedMasterColumns.size(); i++)
        {
            if (i == selectedMasterColumns.size() - 1)
            {
                pw.print(selectedMasterColumns.get(i) + " = :" + selectedMasterColumns.get(i).toLowerCase());
            }
            else
            {
                pw.print(selectedMasterColumns.get(i) + " = :" + selectedMasterColumns.get(i).toLowerCase() + ", ");
            }
        }
        pw.println("    where " + formBean.getPrimarykeyMaster() + " = '\"+id+\"'\";");
        pw.println("        sqlService.persist(aliasName, sqlQuery, sps);");
        pw.println("    }");

        pw.println("    public void updateDetail(String id, " + moduleName + "EntityBeanDetail entityBean) throws Exception");
        pw.println("    {");
        pw.println("        SqlParameterSource sps = null;");
        pw.println("        sps = new BeanPropertySqlParameterSource(entityBean);");
        pw.print("        String sqlQuery = \"update " + selectDetailTebleName + " set ");
        ArrayList<String> selectedDetailColumns = formBean.getSelectedDetailColumns();
        for (int i = 0; i < selectedDetailColumns.size(); i++)
        {
            if (i == selectedDetailColumns.size() - 1)
            {
                pw.print(selectedDetailColumns.get(i) + " = :" + selectedDetailColumns.get(i).toLowerCase());
            }
            else
            {
                pw.print(selectedDetailColumns.get(i) + " = :" + selectedDetailColumns.get(i).toLowerCase() + ", ");
            }
        }
        pw.println("    where " + formBean.getPrimarykeyDetail() + " = '\"+id+\"'\";");
        pw.println("        sqlService.persist(aliasName, sqlQuery, sps);");
        pw.println("    }");

        pw.println("    public void insertMaster(String id, " + moduleName + "EntityBeanMaster entityBean) throws Exception");
        pw.println("    {");
        pw.println("        SqlParameterSource sps = null;");
        pw.println("        sps = new BeanPropertySqlParameterSource(entityBean);");
        pw.print("        String sqlQuery = \"insert into " + selectMasterTebleName + " (");
        for (int i = 0; i < selectedMasterColumns.size(); i++)
        {
            if (i == selectedMasterColumns.size() - 1)
            {
                pw.print(selectedMasterColumns.get(i));
            }
            else
            {
                pw.print(selectedMasterColumns.get(i) + ", ");
            }
        }
        pw.print(") values (");
        for (int i = 0; i < selectedMasterColumns.size(); i++)
        {
            if (i == selectedMasterColumns.size() - 1)
            {
                pw.print(":" + selectedMasterColumns.get(i).toLowerCase());
            }
            else
            {
                pw.print(":" + selectedMasterColumns.get(i).toLowerCase() + ", ");
            }
        }
        pw.println(")\";");
        pw.println("        sqlService.persist(aliasName, sqlQuery, sps);");
        pw.println("    }");

        pw.println("    public void insertDetail(String id, " + moduleName + "EntityBeanDetail entityBean) throws Exception");
        pw.println("    {");
        pw.println("        SqlParameterSource sps = null;");
        pw.println("        sps = new BeanPropertySqlParameterSource(entityBean);");
        pw.print("        String sqlQuery = \"insert into " + selectDetailTebleName + " (");
        for (int i = 0; i < selectedDetailColumns.size(); i++)
        {
            if (i == selectedDetailColumns.size() - 1)
            {
                pw.print(selectedDetailColumns.get(i));
            }
            else
            {
                pw.print(selectedDetailColumns.get(i) + ", ");
            }
        }
        pw.print(") values (");
        for (int i = 0; i < selectedDetailColumns.size(); i++)
        {
            if (i == selectedDetailColumns.size() - 1)
            {
                pw.print(":" + selectedDetailColumns.get(i).toLowerCase());
            }
            else
            {
                pw.print(":" + selectedDetailColumns.get(i).toLowerCase() + ", ");
            }
        }
        pw.println(")\";");
        pw.println("        sqlService.persist(aliasName, sqlQuery, sps);");
        pw.println("    }");
        pw.println("}");
        pw.close();
    }
}
