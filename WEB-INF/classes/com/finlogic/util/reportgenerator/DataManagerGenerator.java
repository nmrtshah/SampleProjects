/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.reportgenerator;

import com.finlogic.apps.finstudio.formbean.ReportGeneratorFormBean;
import java.io.File;
import java.io.PrintWriter;

/**
 *
 * @author njuser
 */
public class DataManagerGenerator
{

    private String alias = null;
    private String query = null;
    private String recordCountQuery = null;

    public DataManagerGenerator(ReportGeneratorFormBean reportGeneratorForm) throws Exception
    {
        this.alias = reportGeneratorForm.getAliasName();
        this.query = reportGeneratorForm.getQuery();
        this.recordCountQuery = reportGeneratorForm.getRecordCountQuery();
        generateDataManager(reportGeneratorForm, reportGeneratorForm.getModuleName());
    }

    public void generateDataManager(ReportGeneratorFormBean reportGeneratorForm, String moduleName) throws Exception
    {
        long number = reportGeneratorForm.getSerialNo();
        String tomcatPath = finpack.FinPack.getProperty("tomcat1_path");
        String filePath = tomcatPath + "/webapps/finstudio/generated/" + number + "/";
        String projectPath = filePath + reportGeneratorForm.getProjectName() + "/WEB-INF/classes/com/finlogic/business/"
                + reportGeneratorForm.getProjectName().toLowerCase()+"/datamanager/";
        
        File file = new File(projectPath);
        file.mkdirs();
        PrintWriter pw = new PrintWriter(projectPath + moduleName + "DataManager.java");

        pw.println("package com.finlogic.business."+reportGeneratorForm.getProjectName().toLowerCase()+".datamanager;");
        if(reportGeneratorForm.isGrid()==true)
        {
            pw.println("import org.springframework.jdbc.support.rowset.SqlRowSet;");

        }
        if((reportGeneratorForm.isPdf()==true)||(reportGeneratorForm.isXls()==true))
        {
            pw.println("import com.finlogic.util.persistence.ResultSetImpl;");
            pw.println("import net.sf.jasperreports.engine.JRResultSetDataSource;");
        }
        pw.println("import com.finlogic.util.persistence.SQLService;");
        pw.println("import com.finlogic.apps."+ reportGeneratorForm.getProjectName().toLowerCase()+".formbeans."+moduleName+"FormBean;");
        pw.println("public class " + moduleName + "DataManager");
        pw.println("{");
        query=query.replaceAll("\n", " \"\n+ \" ");
        recordCountQuery=recordCountQuery.replaceAll("\n", " \"\n+ \" ");
        pw.println("    SQLService sqlService = new SQLService();");
        pw.println("    String aliasName = \"" + alias + "\";");
        if(reportGeneratorForm.isGrid()==true)
        {
            if (reportGeneratorForm.isPaging() == true) 
            {
                pw.println("    public SqlRowSet getDataGrid("+moduleName+"FormBean formBean, String page, String rows) throws Exception");
                pw.println("    {");
                if(reportGeneratorForm.getDatabaseType().equals("Oracle"))
                {
                    pw.println("        String query=\"SELECT * FROM (SELECT ROWNUM ROWNUM1,X1.* FROM (" + query + ") X1) WHERE ROWNUM1 BETWEEN \"+((Long.parseLong(page)*Long.parseLong(rows))-Long.parseLong(rows)+1) + \" AND \" + (Long.parseLong(page)*Long.parseLong(rows));");
                }
                else
                {
                    pw.println("        String query=\"" + query + " limit \"+((Long.parseLong(page)*Long.parseLong(rows))-Long.parseLong(rows)) + \",\" + rows;");
                }
                

                pw.println("        return sqlService.getRowSet(aliasName, query)  ;");
                pw.println("    }");
                
                pw.println("    public SqlRowSet getDataRowCount("+moduleName+"FormBean formBean) throws Exception");
                pw.println("    {");
                pw.println("        String recordCountQuery=\"" + recordCountQuery+ "\";");
                pw.println("        return sqlService.getRowSet(aliasName, recordCountQuery);");
                pw.println("    }");
            } else
            {
                pw.println("    public SqlRowSet getDataGrid("+moduleName+"FormBean formBean) throws Exception");
                pw.println("    {");
                pw.println("        String query=\"" + query + "\";");
                pw.println("        return sqlService.getRowSet(aliasName, query)  ;");
                pw.println("    }");
            }
        }
        if(reportGeneratorForm.isPdf()==true)
        {
            pw.println("    public JRResultSetDataSource getDataPdf("+moduleName+"FormBean formBean) throws Exception");
            pw.println("    {");
            if((reportGeneratorForm.getGrouping().equals("none")))
                pw.println("        String query = \""+ query +"\";"); 
            else
                pw.println("        String query = \""+ query +" ORDER BY "+reportGeneratorForm.getGrouping()+"\";");
            pw.println("        ResultSetImpl resultSetImpl = new ResultSetImpl(sqlService.getRowSet(aliasName, query));");
            pw.println("        JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(resultSetImpl);");
            pw.println("        return resultSetDataSource;");
            pw.println("    }");
        }
        if(reportGeneratorForm.isXls()==true)
        {
            pw.println("    public JRResultSetDataSource getDataXls("+moduleName+"FormBean formBean) throws Exception");
            pw.println("    {");
            if((reportGeneratorForm.getGrouping().equals("none")))
                pw.println("        String query = \""+ query +"\";");
            else
                pw.println("        String query = \""+ query +" ORDER BY "+reportGeneratorForm.getGrouping()+"\";");
            pw.println("        ResultSetImpl resultSetImpl = new ResultSetImpl(sqlService.getRowSet(aliasName, query));");
            pw.println("        JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(resultSetImpl);");
            pw.println("        return resultSetDataSource;");
            pw.println("    }");
        }
        pw.println("}");
        pw.close();
    }
}
