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
public class ControllerGenerator
{
    public ControllerGenerator(ReportGeneratorFormBean reportGeneratorForm) throws Exception
    {
        String moduleName = reportGeneratorForm.getModuleName();
        long number = reportGeneratorForm.getSerialNo();
        String tomcatPath = finpack.FinPack.getProperty("tomcat1_path");
        String filePath = tomcatPath + "/webapps/finstudio/generated/" + number + "/";
        String projectPath = filePath +reportGeneratorForm.getProjectName() + "/WEB-INF/classes/com/finlogic/apps/"
                + reportGeneratorForm.getProjectName().toLowerCase()+"/controller/";

        File file = new File(projectPath);
        file.mkdirs();
        PrintWriter pw = new PrintWriter(projectPath + moduleName + "Controller.java");

        pw.println("package com.finlogic.apps." + reportGeneratorForm.getProjectName().toLowerCase() + ".controller;");
        pw.println("import javax.servlet.http.*;");
        pw.println("import org.springframework.web.servlet.ModelAndView;");
        pw.println("import org.springframework.web.servlet.mvc.multiaction.MultiActionController;");
        pw.println("import com.finlogic.apps."+ reportGeneratorForm.getProjectName().toLowerCase()+".formbeans."+moduleName+"FormBean;");
        pw.println("import com.finlogic.apps."+ reportGeneratorForm.getProjectName().toLowerCase()+".service."+moduleName+"Service;");
        pw.println("public class " + moduleName + "Controller extends MultiActionController");
        pw.println("{");
        pw.println("    public ModelAndView getMenu(HttpServletRequest request, HttpServletResponse response) throws Exception");
        pw.println("    {");
        pw.println("        ModelAndView mv=new ModelAndView();");
        pw.println("        String finlibPath = finpack.FinPack.getProperty(\"finlib_path\");");
        pw.println("        mv.addObject(\"finlibPath\", finlibPath);");
        pw.println("        mv.addObject(\"process\",\"getmenu\");");
        pw.println("        mv.setViewName(\""+moduleName+"/" + moduleName + "\");");
        pw.println("        return mv;");
        pw.println("    }");

        if(reportGeneratorForm.isGrid()==true)
        {
            pw.println("    public ModelAndView getReportGrid(HttpServletRequest request, HttpServletResponse response, "+moduleName+"FormBean formBean) throws Exception");
            pw.println("    {");
            pw.println("        ModelAndView mv = new ModelAndView();");
            pw.println("        " + moduleName + "Service service=new " + moduleName + "Service();");
            pw.println("        mv.addObject(\"process\",\"getreportGrid\");");
            if (reportGeneratorForm.isPaging() == true)
            {
                pw.println("        mv.addObject(\"json\", service.getDataGrid(formBean, request.getParameter(\"page\"),request.getParameter(\"rows\")));");
            } else
            {
                pw.println("        mv.addObject(\"json\", service.getDataGrid(formBean));");
            }
            pw.println("        mv.setViewName(\""+moduleName+"/" + moduleName + "\");");
            pw.println("        return mv;");
            pw.println("    }");
        }

        if(reportGeneratorForm.isPdf()==true)
        {
            pw.println("    public ModelAndView getReportPdf(HttpServletRequest request, HttpServletResponse response, "+moduleName+"FormBean formBean) throws Exception");
            pw.println("    {");
            pw.println("        ModelAndView mv = new ModelAndView();");
            pw.println("        " + moduleName + "Service service=new " + moduleName + "Service();");
            pw.println("        mv.addObject(\"process\",\"getreport\");");
            pw.println("        mv.addObject(\"jaspertopdf\", service.getDataPdf(formBean));");
            pw.println("        mv.setViewName(\""+moduleName+"/" + moduleName + "\");");
            pw.println("        return mv;");
            pw.println("    }");
        }
        
        if(reportGeneratorForm.isXls()==true)
        {
            pw.println("    public ModelAndView getReportExcel(HttpServletRequest request, HttpServletResponse response, "+moduleName+"FormBean formBean) throws Exception");
            pw.println("    {");
            pw.println("        ModelAndView mv = new ModelAndView();");
            pw.println("        " + moduleName + "Service service=new " + moduleName + "Service();");
            pw.println("        mv.addObject(\"process\",\"getreport\");");
            pw.println("        mv.addObject(\"jaspertoexcel\", service.getDataXls(formBean));");
            pw.println("        mv.setViewName(\""+moduleName+"/" + moduleName + "\");");
            pw.println("        return mv;");
            pw.println("    }");
        }
        pw.println("}");
        pw.close();
    }
}
