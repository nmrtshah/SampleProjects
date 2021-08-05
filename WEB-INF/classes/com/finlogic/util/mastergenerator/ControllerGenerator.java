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
public class ControllerGenerator {
    public ControllerGenerator(MasterGeneratorFormBean formBean) throws Exception
    {
        String moduleName = formBean.getModuleName();
        long number = formBean.getSerialNo();
        String tomcatPath = finpack.FinPack.getProperty("tomcat1_path");
        String filePath = tomcatPath + "/webapps/finstudio/generated/" + number + "/";
        String projectPath = filePath +formBean.getProjectName() + "/WEB-INF/classes/com/finlogic/apps/"
                + formBean.getProjectName().toLowerCase()+"/controller/";

        File file = new File(projectPath);
        file.mkdirs();
        PrintWriter pw = new PrintWriter(projectPath + moduleName + "Controller.java");

        pw.println("package com.finlogic.apps." + formBean.getProjectName().toLowerCase() + ".controller;");
        
        pw.println("import com.finlogic.apps."+ formBean.getProjectName().toLowerCase()+".formbeans."+moduleName+"FormBean;");
        pw.println("import com.finlogic.apps."+ formBean.getProjectName().toLowerCase()+".service."+moduleName+"Service;");
        pw.println("import java.util.List;");
        pw.println("import javax.servlet.http.HttpServletRequest;");
        pw.println("import javax.servlet.http.HttpServletResponse;");
        pw.println("import org.springframework.web.servlet.ModelAndView;");
        pw.println("import org.springframework.web.servlet.mvc.multiaction.MultiActionController;");

        pw.println("public class " + moduleName + "Controller extends MultiActionController");
        pw.println("{");
        pw.println("    public ModelAndView getMenu(HttpServletRequest request, HttpServletResponse response) throws Exception");
        pw.println("    {");
        pw.println("        ModelAndView mv = new ModelAndView();");
        pw.println("        String finlibPath = finpack.FinPack.getProperty(\"finlib_path\");");
        pw.println("        mv.addObject(\"finlibPath\", finlibPath);");
        pw.println("        mv.addObject(\"process\",\"getmenu\");");
        pw.println("        mv.setViewName(\""+moduleName+"/" + moduleName + "\");");
        pw.println("        return mv;");
        pw.println("    }");

        pw.println("    public ModelAndView showInsert(HttpServletRequest request, HttpServletResponse response, "+moduleName+"FormBean formBean) throws Exception");
        pw.println("    {");
        pw.println("        ModelAndView mv=new ModelAndView();");
        pw.println("        mv.addObject(\"process\",\"showinsert\");");
        pw.println("        mv.setViewName(\""+moduleName+"/" + moduleName + "\");");
        pw.println("        return mv;");
        pw.println("    }");
        pw.println("    public ModelAndView insert(HttpServletRequest request, HttpServletResponse response, "+moduleName+"FormBean formBean) throws Exception");
        pw.println("    {");
        pw.println("        ModelAndView mv = new ModelAndView();");
        pw.println("        try");
        pw.println("        {");
        pw.println("            "+moduleName+"Service mgts = new "+moduleName+"Service();");
        pw.println("            mgts.insert(formBean);");
        pw.println("            mv.addObject(\"status\", \"inserted\");");
        pw.println("        }");
        pw.println("        catch(Exception e)");
        pw.println("        {");
        pw.println("            mv.addObject(\"status\",\"error\");");
        pw.println("        }");
        pw.println("        mv.setViewName(\""+moduleName+"/" + moduleName + "\");");
        pw.println("        return mv;");
        pw.println("    }");
        pw.println("    public ModelAndView view(HttpServletRequest request, HttpServletResponse response, "+moduleName+"FormBean formBean) throws Exception");
        pw.println("    {");
        pw.println("        ModelAndView mv = new ModelAndView();");
        pw.println("        try");
        pw.println("        {");
        pw.println("            "+moduleName+"Service mgts = new "+moduleName+"Service();");
        pw.println("            mv.addObject(\"json\",mgts.getRecords(formBean, request.getParameter(\"page\"),request.getParameter(\"rows\")));");
        pw.println("            mv.addObject(\"process\",\"view\");");
        pw.println("        }");
        pw.println("        catch(Exception e)");
        pw.println("        {");
        pw.println("            mv.addObject(\"process\",\"error\");");
        pw.println("        }");
        pw.println("        mv.setViewName(\""+moduleName+"/" + moduleName + "\");");
        pw.println("        return mv;");
        pw.println("    }");
        pw.println("    public ModelAndView getRecord(HttpServletRequest request, HttpServletResponse response, "+moduleName+"FormBean formBean) throws Exception");
        pw.println("    {");
        pw.println("        ModelAndView mv = new ModelAndView();");
        pw.println("        try");
        pw.println("        {");
        pw.println("            "+moduleName+"Service mgts = new "+moduleName+"Service();");
        pw.println("            String primarykey = request.getParameter(\"primarykey\");");
        pw.println("            List record = mgts.getRecord(primarykey);");
        pw.println("            mv.addObject(\"record\",record);");
        pw.println("            mv.addObject(\"process\",\"showinsert\");");
        pw.println("        }");
        pw.println("        catch(Exception e)");
        pw.println("        {");
        pw.println("            mv.addObject(\"process\",\"error\");");
        pw.println("        }");
        pw.println("        mv.setViewName(\""+moduleName+"/" + moduleName + "\");");
        pw.println("        return mv;");
        pw.println("    }");
        pw.println("    public ModelAndView update(HttpServletRequest request, HttpServletResponse response, "+moduleName+"FormBean formBean) throws Exception");
        pw.println("    {");
        pw.println("        ModelAndView mv = new ModelAndView();");
        pw.println("        try");
        pw.println("        {");
        pw.println("            "+moduleName+"Service mgts = new "+moduleName+"Service();");
        pw.println("            mgts.update(formBean);");
        pw.println("            mv.addObject(\"status\", \"updated\");");
        pw.println("        }");
        pw.println("        catch(Exception e)");
        pw.println("        {");
        pw.println("            mv.addObject(\"status\",\"error\");");
        pw.println("        }");
        pw.println("        mv.setViewName(\""+moduleName+"/" + moduleName + "\");");
        pw.println("        return mv;");
        pw.println("    }");
        pw.println("    public ModelAndView deleteRecords(HttpServletRequest request, HttpServletResponse response, "+moduleName+"FormBean formBean) throws Exception");
        pw.println("    {");
        pw.println("        ModelAndView mv = new ModelAndView();");
        pw.println("        try");
        pw.println("        {");
        pw.println("            "+moduleName+"Service mgts = new "+moduleName+"Service();");
        pw.println("            String pkValues [] = request.getParameterValues(\"delete\");");
        pw.println("            mgts.deleteRecords(pkValues);");
        pw.println("            mv.addObject(\"deleteCount\", pkValues.length);");
        pw.println("            mv.addObject(\"status\", \"deleted\");");
        pw.println("        }");
        pw.println("        catch(Exception e)");
        pw.println("        {");
        pw.println("            mv.addObject(\"status\",\"error\");");
        pw.println("        }");
        pw.println("        mv.setViewName(\""+moduleName+"/" + moduleName + "\");");
        pw.println("        return mv;");
        pw.println("    }");
        pw.println("}");
        pw.close();
     }
}
