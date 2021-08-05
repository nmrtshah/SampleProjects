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
public class ModelGenerator
{

    public ModelGenerator(ReportGeneratorFormBean reportGeneratorForm) throws Exception
    {
        String tomcatPath = finpack.FinPack.getProperty("tomcat1_path");
        long number = reportGeneratorForm.getSerialNo();
        String moduleName = reportGeneratorForm.getModuleName();
        String filePath=tomcatPath+"/webapps/finstudio/generated/"+number+"/";
        String projectPath = filePath +reportGeneratorForm.getProjectName() + "/WEB-INF/classes/com/finlogic/business/"
                + reportGeneratorForm.getProjectName().toLowerCase()+"/datamanager/";

        File file = new File(projectPath);
        file.mkdirs();
        PrintWriter pw = new PrintWriter(projectPath + moduleName + "Manager.java");

        pw.println("package com.finlogic.business."+reportGeneratorForm.getProjectName().toLowerCase()+".datamanager;");
        if(reportGeneratorForm.isGrid()==true)
        {
            pw.println("import org.springframework.jdbc.support.rowset.SqlRowSet;");
        }
        if((reportGeneratorForm.isPdf()==true)||(reportGeneratorForm.isXls()==true))
        {
            pw.println("import java.util.HashMap;");
            pw.println("import net.sf.jasperreports.engine.JRExporter;");
            pw.println("import net.sf.jasperreports.engine.JRExporterParameter;");
            pw.println("import net.sf.jasperreports.engine.JRResultSetDataSource;");
            pw.println("import net.sf.jasperreports.engine.JasperFillManager;");
            pw.println("import net.sf.jasperreports.engine.JasperPrint;");
            pw.println("import net.sf.jasperreports.engine.JasperCompileManager;");
            pw.println("import net.sf.jasperreports.engine.JasperReport;");
            if((reportGeneratorForm.isPdf()==true))
                pw.println("import net.sf.jasperreports.engine.export.JRPdfExporter;");
            if((reportGeneratorForm.isXls()==true))
                pw.println("import net.sf.jasperreports.engine.export.oasis.JROdsExporter;");
        }
        pw.println("import com.finlogic.apps."+ reportGeneratorForm.getProjectName().toLowerCase()+".formbeans."+moduleName+"FormBean;");

        pw.println("public class "+moduleName+"Manager");
        pw.println("{");
        pw.println("    "+moduleName+"DataManager dataManager=new "+moduleName+"DataManager();");
        pw.println("    String tomcatPath = finpack.FinPack.getProperty(\"tomcat1_path\");");
        if(reportGeneratorForm.isGrid()==true)
        {
            if(reportGeneratorForm.isPaging()==true)
            {
                pw.println("    public SqlRowSet getDataGrid("+moduleName+"FormBean formBean, String page, String rows) throws Exception");
                pw.println("    {");

                pw.println("        return dataManager.getDataGrid(formBean, page, rows);");
                pw.println("    }");
                pw.println("    public int getRowCount("+moduleName+"FormBean formBean) throws Exception");
                pw.println("    {");
                pw.println("        SqlRowSet srs = dataManager.getDataRowCount(formBean);");
                pw.println("        if(srs.next())");
                pw.println("            return srs.getInt(1);");
                pw.println("        else");
                pw.println("            return 0;");
                pw.println("    }");
            }
            else
            {
                pw.println("    public SqlRowSet getDataGrid("+moduleName+"FormBean formBean) throws Exception");
                pw.println("    {");
                pw.println("        return dataManager.getDataGrid(formBean);");
                pw.println("    }");
            }
        }
        if(reportGeneratorForm.isPdf()==true)
        {
            pw.println("    public String jasperToPdf("+moduleName+"FormBean formBean) throws Exception");
            pw.println("    {");
            pw.println("        String fileName = tomcatPath +\"/webapps/"+reportGeneratorForm.getProjectName()+"/WEB-INF/classes/com/finlogic/business/"+reportGeneratorForm.getProjectName().toLowerCase()+"/datamanager/"+moduleName+".jrxml\";");
            pw.println("        String outFileName = tomcatPath +\"/webapps/"+reportGeneratorForm.getProjectName()+"/WEB-INF/classes/com/finlogic/business/"+reportGeneratorForm.getProjectName().toLowerCase()+"/datamanager/"+moduleName+".pdf\";");
            pw.println("        JRExporter exporter = null;");

            pw.println("        exporter = new JRPdfExporter();");
            pw.println("        HashMap hm = new HashMap();");
            pw.println("        JRResultSetDataSource resultSetDataSource = dataManager.getDataPdf(formBean);");
            pw.println("        JasperReport jasperReport = JasperCompileManager.compileReport(fileName);");
            pw.println("        JasperPrint print = JasperFillManager.fillReport(jasperReport, hm, resultSetDataSource);");
            pw.println("        exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outFileName);");
            pw.println("        exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);");
            pw.println("        exporter.exportReport();");
            pw.println("        return outFileName;");
            pw.println("    }");
        }
        if(reportGeneratorForm.isXls()==true)
        {
            pw.println("    public String jasperToXls("+moduleName+"FormBean formBean) throws Exception");
            pw.println("    {");
            pw.println("        String fileName = tomcatPath +\"/webapps/"+reportGeneratorForm.getProjectName()+"/WEB-INF/classes/com/finlogic/business/"+reportGeneratorForm.getProjectName().toLowerCase()+"/datamanager/"+moduleName+".jrxml\";");
            pw.println("        String outFileName = tomcatPath +\"/webapps/"+reportGeneratorForm.getProjectName()+"/WEB-INF/classes/com/finlogic/business/"+reportGeneratorForm.getProjectName().toLowerCase()+"/datamanager/"+moduleName+".xls\";");
            pw.println("        JRExporter exporter = null;");

            pw.println("        exporter = new JROdsExporter();");
            pw.println("        HashMap hm = new HashMap();");
            pw.println("        JRResultSetDataSource resultSetDataSource = dataManager.getDataXls(formBean);");
            pw.println("        JasperReport jasperReport = JasperCompileManager.compileReport(fileName);");
            pw.println("        JasperPrint print = JasperFillManager.fillReport(jasperReport, hm, resultSetDataSource);");
            pw.println("        exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outFileName);");
            pw.println("        exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);");
            pw.println("        exporter.exportReport();");
            pw.println("        return outFileName;");
            pw.println("    }");
        }
        pw.println("}");
        pw.close();
    }
}