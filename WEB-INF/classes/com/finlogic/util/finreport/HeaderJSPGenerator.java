/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.finreport;

import com.finlogic.business.finstudio.finreport.FinReportDetailEntityBean;
import com.finlogic.business.finstudio.finreport.FinReportSummaryEntityBean;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Locale;

/**
 *
 * @author njuser
 */
public class HeaderJSPGenerator
{

    public void generateHeaderJSP(final FinReportDetailEntityBean dEntityBean, final FinReportSummaryEntityBean sEntityBean) throws FileNotFoundException
    {
        String projectName = dEntityBean.getProjectName();
        String tomcatPath = finpack.FinPack.getProperty("tomcat1_path");
        String moduleName = dEntityBean.getModuleName();
        String number = dEntityBean.getSRNo() + "RGV2";
        String filePath = tomcatPath + "/webapps/finstudio/generated/" + number + "/";
        String projectPath = filePath + projectName.toLowerCase(Locale.getDefault()) + "/WEB-INF/jsp/" + moduleName.toLowerCase(Locale.getDefault()) + "/";

        File file = new File(projectPath);
        file.mkdirs();
        PrintWriter pw = new PrintWriter(projectPath + "header.jsp");

        pw.println("<%-- TCIGBF --%>");
        pw.println();
        pw.println("<%@taglib uri=\"http://java.sun.com/jsp/jstl/fmt\" prefix=\"fmt\" %>");
        pw.println();
//        pw.println("<script language=\"javascript\" type=\"text/javascript\">");
//        pw.println("    var currenttime = \"${mydate}\"; //Java method of getting server date");
//        pw.println("    var serverdate = new Date(currenttime);");
//        pw.println("</script>");
        pw.println("<div id=\"header_print\">");
        pw.println("    <div class=\"header_bg\">");
        pw.println("        <div class=\"header header_bg\">");
        pw.println("            <div style=\"float: left\">");
        pw.println("                <a href=\"#\"><img width=\"228\" height=\"50\" class=\"logo_company\" src=\"${finlibPath}/resource/images/logo_njindiainvest.gif\" alt=\"NJIndiaInvest\"></a>");
        pw.println("            </div>");
        pw.println("            <div style=\"float: right;margin-top: 30px;font-size:16px;font-weight:bolder;color: white\">");
        pw.println("             " + projectName);
        pw.println("            </div>");
        pw.println("        </div>");
        pw.println("    </div>");
        pw.println("    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">");
        pw.println("        <tr class=\"Row_Header\">");
        pw.println("            <td align=\"left\" height=\"20\">&nbsp;</td>");
        pw.println("            <td align=\"left\" nowrap class=\"clsbtnon\" >&nbsp;&nbsp;Welcome To " + projectName + "</td>");
//        pw.println("            <td align=\"right\" nowrap class=\"clsbtnon\">");
//        pw.println("                <span id=\"servertime\"></span>&nbsp;&nbsp;</td>");
        pw.println("        </tr>");
        pw.println("        <tr class=\"Line_Header\"><td align=\"center\" nowrap></td></tr>");
        pw.println("    </table>");
        pw.println("</div>");
        pw.println("<div class=\"deskbar\">");
        pw.println("    <span>" + projectName + "-" + moduleName + "</span>");
        pw.println("    <div class=\"deskbar_logout\">Logout</div>");
        pw.println("</div>");
        pw.println("<div class=\"breadcrum\">Home<a href=\"javascript:history.back();\" style=\"float:right; padding-right:10px; text-decoration:none;\">Back</a></div>");

        pw.close();
    }
}
