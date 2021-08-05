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
public class ControllerGenerator {
    public ControllerGenerator(MasterGeneratorV2FormBean formBean) throws Exception
    {
        generateController(formBean);
    }

    public void generateController(MasterGeneratorV2FormBean formBean) throws Exception
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

        pw.println("import com.finlogic.apps."+ formBean.getProjectName().toLowerCase()+".formbeans."+moduleName+"FormBeanMaster;");
        pw.println("import com.finlogic.apps."+ formBean.getProjectName().toLowerCase()+".formbeans."+moduleName+"FormBeanDetail;");
        pw.println("import com.finlogic.apps."+ formBean.getProjectName().toLowerCase()+".service."+moduleName+"Service;");
        pw.println("import javax.servlet.http.HttpServletRequest;");
        pw.println("import javax.servlet.http.HttpServletResponse;");
        pw.println("import org.springframework.web.servlet.ModelAndView;");
        pw.println("import org.springframework.web.servlet.mvc.multiaction.MultiActionController;");
        pw.println("import java.util.ArrayList;");

        pw.println("public class " + moduleName + "Controller extends MultiActionController");
        pw.println("{");
        pw.println("    public ModelAndView getMenu(HttpServletRequest request, HttpServletResponse response) throws Exception");
        pw.println("    {");
        pw.println("        ModelAndView mv = new ModelAndView();");
        pw.println("        try");
        pw.println("        {");
        pw.println("            String finlibPath = finpack.FinPack.getProperty(\"finlib_path\");");
        pw.println("            mv.setViewName(\""+moduleName+"/" + moduleName + "\");");
        pw.println("            mv.addObject(\"finlibPath\", finlibPath);");
        pw.println("            mv.addObject(\"process\",\"getmenu\");");
        pw.println("        }");
        pw.println("        catch (Exception e)");
        pw.println("        {");
        pw.println("        }");
        pw.println("        return mv;");
        pw.println("    }");

        pw.println("    public ModelAndView getMasterGrid(HttpServletRequest request, HttpServletResponse response) throws Exception");
        pw.println("    {");
        pw.println("        ModelAndView mv = new ModelAndView();");
        pw.println("        try");
        pw.println("        {");
        pw.println("            "+formBean.getModuleName()+"Service service=new "+moduleName+"Service();");
        pw.println("            mv.setViewName(\""+moduleName+"/" + moduleName + "\");");
        pw.println("            mv.addObject(\"process\",\"getMasterGrid\");");
        pw.println("            mv.addObject(\"jsonmaster\", service.getDataMaster(request.getParameter(\"page\"),request.getParameter(\"totalrows\")));");
        pw.println("        }");
        pw.println("        catch (Exception e)");
        pw.println("        {");
        pw.println("        }");
        pw.println("        return mv;");
        pw.println("    }");

        pw.println("    public ModelAndView getDetailGrid(HttpServletRequest request, HttpServletResponse response) throws Exception");
        pw.println("    {");
        pw.println("        ModelAndView mv = new ModelAndView();");
        pw.println("        try");
        pw.println("        {");
        pw.println("            "+formBean.getModuleName()+"Service service=new "+moduleName+"Service();");
        pw.println("            mv.setViewName(\""+moduleName+"/" + moduleName + "\");");
        pw.println("            mv.addObject(\"process\",\"getDetailGrid\");");
        pw.println("            mv.addObject(\"jsondetail\", service.getDataDetail(request.getParameter(\"foreignKey\"), request.getParameter(\"page\"),request.getParameter(\"totalrows\")));");
        pw.println("        }");
        pw.println("        catch (Exception e)");
        pw.println("        {");
        pw.println("        }");
        pw.println("        return mv;");
        pw.println("    }");

        pw.println("    public ModelAndView doOperMasterGrid(HttpServletRequest request, HttpServletResponse response, "+formBean.getModuleName()+"FormBeanMaster formBean) throws Exception");
        pw.println("    {");
        pw.println("        ModelAndView mv = new ModelAndView();");
        pw.println("        try");
        pw.println("        {");
        pw.println("            "+formBean.getModuleName()+"Service service=new "+moduleName+"Service();");
        pw.println("            String oper = request.getParameter(\"oper\");");
        pw.println("            mv.setViewName(\""+moduleName+"/" + moduleName + "\");");
        pw.println("            mv.addObject(\"process\",\"getMasterGrid\");");
        pw.println("            if(oper.equals(\"del\"))");
        pw.println("            {");
        pw.println("                String ids = request.getParameter(\"id\");");
        pw.println("                String id[] = ids.split(\",\");");
        pw.println("                ArrayList<String> undeletedid = service.deleteRecordsMaster(id);");
        pw.println("                if (!undeletedid.isEmpty())");
        pw.println("                {");
        pw.println("                    mv.addObject(\"undeletedid\", undeletedid);");
        pw.println("                    mv.addObject(\"process\", \"childExists\");");
        pw.println("                }");
        pw.println("            }");
        pw.println("            else if(oper.equals(\"edit\"))");
        pw.println("            {");
        pw.println("                service.updateMaster(request.getParameter(\"id\"), formBean);");
        pw.println("            }");
        pw.println("            else if(oper.equals(\"add\"))");
        pw.println("            {");
        pw.println("                service.insertMaster(request.getParameter(\"id\"), formBean);");
        pw.println("            }");
        pw.println("        }");
        pw.println("        catch (Exception e)");
        pw.println("        {");
        pw.println("        }");
        pw.println("        return mv;");
        pw.println("    }");

        pw.println("    public ModelAndView doOperDetailGrid(HttpServletRequest request, HttpServletResponse response, "+moduleName+"FormBeanDetail formBean) throws Exception");
        pw.println("    {");
        pw.println("        ModelAndView mv = new ModelAndView();");
        pw.println("        try");
        pw.println("        {");
        pw.println("            "+formBean.getModuleName()+"Service service=new "+moduleName+"Service();");
        pw.println("            mv.setViewName(\""+moduleName+"/" + moduleName + "\");");
        pw.println("            mv.addObject(\"process\",\"getDetailGrid\");");
        pw.println("            String oper = request.getParameter(\"oper\");");
        pw.println("            if(oper.equals(\"del\"))");
        pw.println("            {");
        pw.println("                String ids = request.getParameter(\"id\");");
        pw.println("                String id[] = ids.split(\",\");");
        pw.println("                service.deleteRecordsDetail(id);");
        pw.println("            }");
        pw.println("            else if(oper.equals(\"edit\"))");
        pw.println("            {");
        pw.println("                service.updateDetail(request.getParameter(\"id\"), formBean);");
        pw.println("            }");
        pw.println("            else if(oper.equals(\"add\"))");
        pw.println("            {");
        pw.println("                service.insertDetail(request.getParameter(\"id\"), formBean);");
        pw.println("            }");
        pw.println("        }");
        pw.println("        catch (Exception e)");
        pw.println("        {");
        pw.println("        }");
        pw.println("        return mv;");
        pw.println("    }");
        pw.println("}");
        pw.close();
    }
}
