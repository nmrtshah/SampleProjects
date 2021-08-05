package com.finlogic.util.reportgenerator;
import com.finlogic.apps.finstudio.formbean.ReportGeneratorFormBean;
import java.io.File;
import java.io.PrintWriter;
/**
 *
 * @author njuser
 */
public class ServiceGenerator
{
    public  ServiceGenerator(ReportGeneratorFormBean reportGeneratorForm) throws Exception
    {
        String tomcatPath = finpack.FinPack.getProperty("tomcat1_path");
        long number = reportGeneratorForm.getSerialNo();
        String moduleName = reportGeneratorForm.getModuleName();
        String filePath=tomcatPath+"/webapps/finstudio/generated/"+number+"/";
        String projectPath = filePath +reportGeneratorForm.getProjectName() + "/WEB-INF/classes/com/finlogic/apps/"
                + reportGeneratorForm.getProjectName().toLowerCase()+"/service/";

        File file = new File(projectPath);
        file.mkdirs();
        PrintWriter pw = new PrintWriter(projectPath + moduleName + "Service.java");

        pw.println("package com.finlogic.apps." + reportGeneratorForm.getProjectName().toLowerCase() + ".service;");
        if(reportGeneratorForm.isGrid()==true)
        {
            pw.println("import org.springframework.jdbc.support.rowset.SqlRowSet;");
            pw.println("import com.finlogic.util.JSONParser;");
        }
        pw.println("import com.finlogic.business."+reportGeneratorForm.getProjectName().toLowerCase()+".datamanager."+moduleName+"Manager;");
        pw.println("import com.finlogic.apps."+ reportGeneratorForm.getProjectName().toLowerCase()+".formbeans."+moduleName+"FormBean;");
        pw.println("public class "+moduleName+"Service ");
        pw.println("{");
        pw.println("    "+moduleName+"Manager manager = new "+moduleName+"Manager();");
        if(reportGeneratorForm.isGrid()==true)
        {
            if(reportGeneratorForm.isPaging()==true)
            {
                pw.println("    public String getDataGrid("+moduleName+"FormBean formBean, String page, String rows) throws Exception");
                pw.println("    {");
                pw.println("        SqlRowSet srs = manager.getDataGrid(formBean,page,rows);");
                pw.println("        int rowCount = manager.getRowCount(formBean);");
                pw.println("        JSONParser jsonParser = new JSONParser();");
                pw.println("        return jsonParser.jsonParser(srs, \""+reportGeneratorForm.getGridColumnPK()+"\", rowCount, Integer.parseInt(page), Integer.parseInt(rows));");
            }
            else
            {
                pw.println("    public String getDataGrid("+moduleName+"FormBean formBean) throws Exception");
                pw.println("    {");
                pw.println("        SqlRowSet srs = manager.getDataGrid(formBean);");
                pw.println("        JSONParser jsonParser = new JSONParser();");
                pw.println("        return jsonParser.Parse(srs, \""+reportGeneratorForm.getGridColumnPK()+"\");");
            }
            pw.println("    }");
        }
        if(reportGeneratorForm.isPdf()==true)
        {
            pw.println("    public String getDataPdf("+moduleName+"FormBean formBean) throws Exception");
            pw.println("    {");
            pw.println("        return manager.jasperToPdf(formBean);");
            pw.println("    }");
        }
        if(reportGeneratorForm.isXls()==true)
        {
            pw.println("    public String getDataXls("+moduleName+"FormBean formBean) throws Exception");
            pw.println("    {");
            pw.println("        return manager.jasperToXls(formBean);");
            pw.println("    }");
        }
        pw.println("}");
        pw.close();
    }
}
