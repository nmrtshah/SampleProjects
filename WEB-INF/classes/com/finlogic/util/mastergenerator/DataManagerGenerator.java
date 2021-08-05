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
public class DataManagerGenerator
{
    public DataManagerGenerator(MasterGeneratorFormBean formBean, ArrayList<String> allColumns, ArrayList<String> selectedColumns) throws Exception
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
        String selectTebleName = "";
        if (dbType.equals("MYSQL"))
        {
            String sch_tab[] = formBean.getSelectTableName().split("-");
            selectTebleName = sch_tab[1];
        }
        else
        {
            selectTebleName = formBean.getSelectTableName();
        }
        pw.println("package com.finlogic.business." + formBean.getProjectName().toLowerCase() + ".datamanager;");

        pw.println("import com.finlogic.apps." + formBean.getProjectName().toLowerCase() + ".formbeans." + moduleName + "FormBean;");
        pw.println("import com.finlogic.business." + formBean.getProjectName().toLowerCase() + ".entitybeans." + moduleName + "EntityBean;");
        pw.println("import com.finlogic.util.SQLService;");
        pw.println("import java.util.List;");
        pw.println("import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;");
        pw.println("import org.springframework.jdbc.core.namedparam.SqlParameterSource;");
        pw.println("import org.springframework.jdbc.support.rowset.SqlRowSet;");

        pw.println("public class " + moduleName + "DataManager");
        pw.println("{");

        pw.println("    private SQLService sqlService = new SQLService();");
        pw.println("    private String alias = \"" + formBean.getAliasName() + "\";");

        pw.println("    public void insert(" + moduleName + "EntityBean entityBean) throws Exception");
        pw.println("    {");
        pw.println("        SqlParameterSource sps = null;");
        pw.println("        sps = new BeanPropertySqlParameterSource(entityBean);");

        pw.print("        String sqlQuery = \"insert into " + selectTebleName + " (");
        for (int i = 0; i < selectedColumns.size(); i++)
        {
            if (i == selectedColumns.size() - 1)
            {
                pw.print(selectedColumns.get(i));
            }
            else
            {
                pw.print(selectedColumns.get(i) + ", ");
            }
        }
        pw.print(") values (");
        for (int i = 0; i < selectedColumns.size(); i++)
        {
            if (i == selectedColumns.size() - 1)
            {
                pw.print(":" + selectedColumns.get(i).toLowerCase());
            }
            else
            {
                pw.print(":" + selectedColumns.get(i).toLowerCase() + ", ");
            }
        }
        pw.println(")\";");

        pw.println("        sqlService.persist(alias, sqlQuery, sps);");
        pw.println("    }");


        pw.println("    public SqlRowSet getRecords(" + moduleName + "FormBean formBean, String page, String rows)");
        pw.println("    {");
        if (dbType.equals("MYSQL"))
        {
            pw.print("          String query=\"select ");
            for (int i = 0; i < allColumns.size(); i++)
            {
                if (i == allColumns.size() - 1)
                {
                    pw.print(allColumns.get(i));
                }
                else
                {
                    pw.print(allColumns.get(i) + ", ");
                }
            }
            pw.println(" from " + selectTebleName + " limit \"+((Long.parseLong(page)*Long.parseLong(rows))-Long.parseLong(rows)) + \",\" + rows;");
        }
        else
        {
            pw.print("        String query=\"SELECT * FROM (SELECT ROWNUM ROWNUM1,X1.* FROM ( select");

            for (int i = 0; i < allColumns.size(); i++)
            {
                if (i == allColumns.size() - 1)
                {
                    pw.print(allColumns.get(i));
                }
                else
                {
                    pw.print(allColumns.get(i) + ", ");
                }
            }
            pw.print(" from " + selectTebleName);
            pw.print(") X1) ");
            pw.println("WHERE ROWNUM1 BETWEEN \"+((Long.parseLong(page)*Long.parseLong(rows))-Long.parseLong(rows)+1) + \" AND \" + (Long.parseLong(page)*Long.parseLong(rows));");
        }
        pw.println("        return sqlService.getRowSet(alias, query)  ;");
        pw.println("    }");

        pw.println("    public SqlRowSet getDataRowCount(" + moduleName + "FormBean formBean)");
        pw.println("    {");
        pw.print("          String query=\"select count(*) from (select ");
        for (int i = 0; i < allColumns.size(); i++)
        {
            if (i == allColumns.size() - 1)
            {
                pw.print(allColumns.get(i));
            }
            else
            {
                pw.print(allColumns.get(i) + ", ");
            }
        }
        pw.println(" from " + selectTebleName + ")X\";");
        pw.println("        return sqlService.getRowSet(alias, query);");
        pw.println("    }");

        pw.println("    public List getRecord(String primarykey) throws Exception");
        pw.println("    {");
        pw.print("        String query = \"select " + formBean.getPrimarykey() + ", ");
        for (int i = 0; i < selectedColumns.size(); i++)
        {
            if (!(selectedColumns.get(i).equals(formBean.getPrimarykey())))
            {
                if (i == selectedColumns.size() - 1)
                {
                    pw.print(selectedColumns.get(i));
                }
                else
                {
                    pw.print(selectedColumns.get(i) + ", ");
                }
            }
        }
        pw.print(" from " + selectTebleName + " ");
        pw.println(" where " + formBean.getPrimarykey() + " = '\" + primarykey + \"'\";");
        pw.println("        return sqlService.getList(alias, query);");
        pw.println("    }");

        pw.println("    public void update(" + moduleName + "EntityBean entityBean) throws Exception");
        pw.println("    {");
        pw.println("        SqlParameterSource sps = null;");
        pw.println("        sps = new BeanPropertySqlParameterSource(entityBean);");

        pw.print("      String sqlQuery = \"update " + selectTebleName + " set ");
        for (int i = 0; i < selectedColumns.size(); i++)
        {
            if (i == selectedColumns.size() - 1)
            {
                pw.print(selectedColumns.get(i) + " = :" + selectedColumns.get(i).toLowerCase());
            }
            else
            {
                pw.print(selectedColumns.get(i) + " = :" + selectedColumns.get(i).toLowerCase() + ", ");
            }
        }
        pw.println("    where " + formBean.getPrimarykey() + " = :" + formBean.getPrimarykey().toLowerCase() + "\";");
        pw.println("        sqlService.persist(alias, sqlQuery, sps);");
        pw.println("    }");

        pw.println("    public void deleteRecord(String pk) throws Exception");
        pw.println("    {");
        pw.println("        String sqlQuery = \"delete from " + selectTebleName + " where " + formBean.getPrimarykey() + " = '\"+pk+\"'\";");
        pw.println("        sqlService.persist(alias, sqlQuery);");
        pw.println("    }");
        pw.println("}");
        pw.close();   
    }
}
