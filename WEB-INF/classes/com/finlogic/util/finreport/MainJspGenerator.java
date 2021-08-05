/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.finreport;

import com.finlogic.apps.finstudio.finreport.FinReportFormBean;
import java.io.File;
import java.io.PrintWriter;

/**
 *
 * @author njuser
 */
public class MainJspGenerator
{

    public MainJspGenerator(FinReportFormBean formBean) throws Exception
    {
        String projectName = formBean.getCmbProjectName();
        String tomcatPath = finpack.FinPack.getProperty("tomcat1_path");
        String moduleName = formBean.getTxtModuleName();
        String number = formBean.getSRNo() + "RGV2";
        String filePath = tomcatPath + "/webapps/finstudio/generated/" + number + "/";
        String projectPath = filePath + projectName.toLowerCase() + "/WEB-INF/jsp/" + moduleName.toLowerCase() + "/";
        
        File file = new File(projectPath);
        file.mkdirs();
        PrintWriter pw = new PrintWriter(projectPath + "main.jsp");
        pw.println("<%@page contentType=\"text/html\" pageEncoding=\"UTF-8\"%>");
        pw.println("<%");
        pw.println("    String finlib_path = finpack.FinPack.getProperty(\"finlib_path\");");
        pw.println("%>");
        pw.println("<html>");
        pw.println("    <head>");
        pw.println("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
        pw.println("        <link href=\"<%=finlib_path%>/resource/main.css\" rel=\"stylesheet\" type=\"text/css\"/>");
        pw.println("        <script type=\"text/javascript\" src=\"<%=finlib_path%>/resource/serverCombo.js\"></script>");
        pw.println("        <script type=\"text/javascript\" src=\"js/"+ formBean.getTxtModuleName() +".js\"></script>");
        pw.println("        <script type=\"text/javascript\" src=\"<%=finlib_path%>/resource/codegenfunc.js\"></script>");
        pw.println("        <script type=\"text/javascript\" src=\"<%=finlib_path%>/resource/ajax.js\"></script>");
        pw.println("        <script type=\"text/javascript\" src=\"<%=finlib_path%>/resource/common_functions.js\"></script>");

        pw.println("        <script type=\"text/javascript\" src=\"<%=finlib_path%>/jquery/jquery-1.6.1/jquery-1.6.1.min.js\"></script>");
        pw.println("        <script type=\"text/javascript\" src=\"<%=finlib_path%>/jquery/jquery-ui-1.8.13.custom/js/jquery-ui-1.8.13.custom.min.js\"></script>");
        pw.println("        <link rel=\"stylesheet\" type=\"text/css\" href=\"<%=finlib_path%>/jquery/jquery-ui-1.8.13.custom/css/smoothness/jquery-ui-1.8.13.custom.css\"/>");

        pw.println("        <!-- date picker-->");
        pw.println("        <script type=\"text/javascript\" src=\"<%=finlib_path%>/jquery/jquery-ui-1.8.13.custom/development-bundle/ui/jquery.ui.datepicker.js\"></script>");
        pw.println("        <!-- date picker ends-->");

        pw.println("        <!-- multi select combo--->");
        pw.println("        <script type=\"text/javascript\" src=\"<%=finlib_path%>/jquery/jquery.jqGrid-3.8.2/js/i18n/grid.locale-en.js\"></script>");
        pw.println("        <script type=\"text/javascript\" src=\"<%=finlib_path%>/jquery/jquery.jqGrid-3.8.2/js/jquery.jqGrid.min.js\"></script>");
        pw.println("        <!--//-->");

        pw.println("        <!-- Grid Report -->");
        pw.println("        <link rel=\"stylesheet\" type=\"text/css\" href=\"<%=finlib_path%>/jquery/jquery-ui-multiselect-widget/jquery.multiselect.css\"/>");
        pw.println("        <link rel=\"stylesheet\" type=\"text/css\" href=\"<%=finlib_path%>/jquery/jquery-ui-multiselect-widget/jquery.multiselect.filter.css\"/>");

        pw.println("        <script type=\"text/javascript\" src=\"<%=finlib_path%>/jquery/jquery-ui-multiselect-widget/src/jquery.multiselect.js\"></script>");
        pw.println("        <script type=\"text/javascript\" src=\"<%=finlib_path%>/jquery/jquery-ui-multiselect-widget/src/jquery.multiselect.filter.js\"></script>");
        pw.println("        <script type=\"text/javascript\" src=\"<%=finlib_path%>/jquery/jquery-ui-multiselect-widget/src/jquery.multiselect.filter.min.js\"></script>");

        pw.println("    </head>");
        pw.println("    <body>");
        pw.println("        <div class=\"container\">");
        pw.println("            <jsp:include page=\"header.jsp\"/>");
        pw.println("            <div class=\"content\">");
        pw.println("                <jsp:include page=\"report_menu.jsp\"/>");
        pw.println("                <div id=\"pdf_out\"></div>");
        pw.println("            </div>");
        pw.println("            <jsp:include page=\"bottom.jsp\"/>");
        pw.println("        </div>");
        pw.println("    </body>");
        pw.println("</html>");
        pw.close();
    }
}
