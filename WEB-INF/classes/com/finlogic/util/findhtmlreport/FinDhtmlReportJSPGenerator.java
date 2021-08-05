/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.findhtmlreport;

import com.finlogic.business.finstudio.findhtmlreport.FinDhtmlReportDetailEntityBean;
import com.finlogic.business.finstudio.findhtmlreport.FinDhtmlReportSummaryEntityBean;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Locale;

/**
 *
 * @author njuser
 */
public class FinDhtmlReportJSPGenerator
{

    public void generateReportJSP(final FinDhtmlReportDetailEntityBean dEntityBean, final FinDhtmlReportSummaryEntityBean sEntityBean) throws FileNotFoundException, ClassNotFoundException, SQLException, UnsupportedEncodingException
    {
        String projectName = dEntityBean.getProjectName();
        String tomcatPath = finpack.FinPack.getProperty("tomcat1_path");
        String moduleName = dEntityBean.getModuleName();
        String number = dEntityBean.getSRNo() + "RGV2";
        String filePath = tomcatPath + "/webapps/finstudio/generated/" + number + "/";
        String projectPath = filePath + projectName.toLowerCase(Locale.getDefault()) + "/WEB-INF/jsp/" + moduleName.toLowerCase(Locale.getDefault()) + "/";

        File file = new File(projectPath);
        file.mkdirs();
        PrintWriter pw = new PrintWriter(projectPath + "report_menu.jsp");

        boolean flag = false;
        try
        {
            pw.println("<%-- TCIGBF --%>");
            pw.println();
            pw.println("<%@page contentType=\"text/html\" pageEncoding=\"UTF-8\"%>");
            pw.println("<%@taglib uri=\"http://java.sun.com/jsp/jstl/core\" prefix=\"c\" %>");
            pw.println();
            pw.println("<c:choose>");
            pw.println("    <c:when test=\"${process eq 'getmenu'}\">");
            pw.println("    <!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\"");
            pw.println("    \"http://www.w3.org/TR/html4/loose.dtd\">");
            pw.println("        <html>");
            pw.println("            <head>");
            pw.println("                <title>Report-Menu</title>");
            pw.println("                <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" >");
            pw.println("                <link rel=\"stylesheet\" type=\"text/css\" href=\"${finlibPath}/resource/main_offline.css\" >");
            pw.println("                <link rel=\"stylesheet\" type=\"text/css\" href=\"${finlibPath}/jquery/jquery-ui-1.8.13.custom/css/smoothness/jquery-ui-1.8.13.custom.css\" >");
            pw.println("                <%--DhtmlxGrid--%>");
            pw.println("                <link rel=\"stylesheet\" type=\"text/css\" href=\"${finlibPath}/dhtmlxSuite/dhtmlxGrid/codebase/dhtmlxgrid.css\">");
            pw.println("                <link rel=\"stylesheet\" type=\"text/css\" href=\"${finlibPath}/dhtmlxSuite/dhtmlxGrid/codebase/skins/dhtmlxgrid_dhx_web.css\">");
            pw.println("                <link rel=\"stylesheet\" type=\"text/css\" href=\"${finlibPath}/dhtmlxSuite/dhtmlxGrid/codebase/skins/dhtmlxgrid_dhx_black.css\">");
            pw.println("                <link rel=\"stylesheet\" type=\"text/css\" href=\"${finlibPath}/dhtmlxSuite/dhtmlxGrid/codebase/skins/dhtmlxgrid_dhx_terrace.css\">");
            pw.println("                <link rel=\"stylesheet\" type=\"text/css\" href=\"${finlibPath}/dhtmlxSuite/dhtmlxToolbar/codebase/skins/dhtmlxtoolbar_dhx_web.css\">");
            pw.println("                <link rel=\"STYLESHEET\" type=\"text/css\" href=\"${finlibPath}/dhtmlxSuite/dhtmlxChart/codebase/dhtmlxchart.css\">");

            pw.println("                <script type=\"text/javascript\" src=\"${finlibPath}/dhtmlxSuite/dhtmlxGrid/codebase/dhtmlxcommon.js\"></script>");
            pw.println("                <script type=\"text/javascript\" src=\"${finlibPath}/dhtmlxSuite/dhtmlxGrid/codebase/dhtmlxcommonfunctions.js\"></script>");
            pw.println("                <script type=\"text/javascript\" src=\"${finlibPath}/dhtmlxSuite/dhtmlxGrid/codebase/dhtmlxgrid.js\"></script>");
            pw.println("                <script type=\"text/javascript\" src=\"${finlibPath}/dhtmlxSuite/dhtmlxGrid/codebase/dhtmlxgridcell.js\"></script>");
            pw.println("                <script type=\"text/javascript\" src=\"${finlibPath}/dhtmlxSuite/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js\"></script>");
            pw.println("                <%--For splitting,serial number,grouping--%>");
            pw.println("                <script type=\"text/javascript\" src=\"${finlibPath}/dhtmlxSuite/dhtmlxGrid/codebase/ext/dhtmlxgrid_splt.js\"></script>");
            pw.println("                <%--For PDF & Excel--%>");
            pw.println("                <script type=\"text/javascript\" src=\"${finlibPath}/dhtmlxSuite/dhtmlxGrid/codebase/ext/dhtmlxgrid_export.js\"></script>");
            pw.println("                <%--Group by--%>");
            pw.println("                <script type=\"text/javascript\" src=\"${finlibPath}/dhtmlxSuite/dhtmlxGrid/codebase/ext/dhtmlxgrid_group.js\"></script>");
            pw.println("                <%--Filtering--%>");
            pw.println("                <script type=\"text/javascript\" src=\"${finlibPath}/dhtmlxSuite/dhtmlxGrid/codebase/ext/dhtmlxgrid_filter.js\"></script>");
            pw.println("                <%--Smart Rendering--%>");
            pw.println("                <script type=\"text/javascript\" src=\"${finlibPath}/dhtmlxSuite/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js\"></script>");
            pw.println("                <%--Paging--%>");
            pw.println("                <script type=\"text/javascript\" src=\"${finlibPath}/dhtmlxSuite/dhtmlxToolbar/codebase/dhtmlxtoolbar.js\"></script>");
            pw.println("                <script type=\"text/javascript\" src=\"${finlibPath}/dhtmlxSuite/dhtmlxGrid/codebase/ext/dhtmlxgrid_pgn.js\"></script>");

            pw.println("                <%--For links in rows--%>");
            pw.println("                <script type=\"text/javascript\" src=\"${finlibPath}/dhtmlxSuite/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_link.js\"></script>");
            pw.println("                <%--For serial Number--%>");
            pw.println("                <script type=\"text/javascript\" src=\"${finlibPath}/dhtmlxSuite/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_cntr.js\"></script>");
            pw.println("                <%--For printable view--%>");
            pw.println("                <script type=\"text/javascript\" src=\"${finlibPath}/dhtmlxSuite/dhtmlxGrid/codebase/ext/dhtmlxgrid_nxml.js\"></script>");
            pw.println("                <%--For rowspan in record--%>");
            pw.println("                <script type=\"text/javascript\" src=\"${finlibPath}/dhtmlxSuite/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js\"></script>");
            pw.println("                <%--Drag n Drop--%>");
            pw.println("                <script type=\"text/javascript\" src=\"${finlibPath}/dhtmlxSuite/dhtmlxGrid/codebase/ext/dhtmlxgrid_drag.js\"></script>");
            pw.println("                <%--Move Columns--%>");
            pw.println("                <script type=\"text/javascript\" src=\"${finlibPath}/dhtmlxSuite/dhtmlxGrid/codebase/ext/dhtmlxgrid_mcol.js\"></script>");
            pw.println("                <script type=\"text/javascript\" src=\"${finlibPath}/dhtmlxSuite/dhtmlxGrid/codebase/ext/dhtmlxgrid_post.js\"></script>");
            pw.println("                <%--for dhtml charts--%>");
            pw.println("                <script src=\"${finlibPath}/dhtmlxSuite/dhtmlxChart/codebase/dhtmlxchart.js\" type=\"text/javascript\"></script>");

            if (dEntityBean.getRptControl() != null)
            {
                for (int i = 0; i < dEntityBean.getRptControl().length; i++)
                {
                    if ("DatePicker".equals(dEntityBean.getRptControl()[i]))
                    {
                        flag = true;
                        break;
                    }
                }
            }
            if (dEntityBean.getFltrControl() != null)
            {
                for (int i = 0; i < dEntityBean.getFltrControl().length; i++)
                {
                    if ("DatePicker".equals(dEntityBean.getFltrControl()[i]))
                    {
                        flag = true;
                        break;
                    }
                }
            }
            if (dEntityBean.isDateTimePicker() || flag)
            {
                pw.println("                <%--Date Picker--%>");
                pw.println("                <link rel=\"stylesheet\" href=\"${finlibPath}/dhtmlxSuite/dhtmlxCalendar/codebase/dhtmlxcalendar.css\" type=\"text/css\"/>");
                pw.println("                <link rel=\"stylesheet\" type=\"text/css\" href=\"${finlibPath}/dhtmlxSuite/dhtmlxCalendar/codebase/skins/dhtmlxcalendar_omega.css\"/>");
                pw.println("                <link rel=\"stylesheet\" type=\"text/css\" href=\"${finlibPath}/dhtmlxSuite/dhtmlxCalendar/codebase/skins/dhtmlxcalendar_dhx_skyblue.css\"/>");
                pw.println("                <script type=\"text/javascript\" src=\"${finlibPath}/dhtmlxSuite/dhtmlxCalendar/codebase/dhtmlxcalendar.js\"></script>");
                pw.println("                <script type=\"text/javascript\" src=\"${finlibPath}/dhtmlxSuite/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_dhxcalendar.js\"></script>");
            }
            //pw.println("                <link rel=\"stylesheet\" type=\"text/css\" href=\"${finlibPath}/jquery/jquery.jqGrid-3.8.2/css/ui.jqgrid.css\">");
            pw.println("                <script type=\"text/javascript\" src=\"${finlibPath}/jquery/jquery-1.6.1/jquery-1.6.1.min.js\"></script>");
            pw.println("                <script type=\"text/javascript\" src=\"${finlibPath}/jquery/jquery-ui-1.8.13.custom/js/jquery-ui-1.8.13.custom.min.js\"></script>");
            pw.println("                <script type=\"text/javascript\" src=\"${finlibPath}/resource/common_functions.min.js\"></script>");
            pw.println("                <script type=\"text/javascript\" src=\"${finlibPath}/resource/serverCombo.js\"></script>");
            pw.println("                <script type=\"text/javascript\" src=\"js/" + dEntityBean.getModuleName().toLowerCase(Locale.getDefault()) + ".js\"></script>");
//            pw.println("                <script type=\"text/javascript\" src=\"${finlibPath}/resource/codegenfunc.js\"></script>");
            pw.println("                <script type=\"text/javascript\" src=\"${finlibPath}/resource/validations.min.js\"></script>");
            pw.println("                <script type=\"text/javascript\" src=\"${finlibPath}/resource/ajax.js\"></script>");

            pw.println("            </head>");
            pw.println("            <body>");
            pw.println("                <table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">");
            pw.println("                    <tr>");
            pw.println("                        <td>");
            pw.println("                <div class=\"container\">");
            if (dEntityBean.isHeaderReq())
            {
                pw.println("                    <jsp:include page=\"header.jsp\"/>");
            }
            pw.println("                    <div class=\"content\">");
            pw.println("                        <form name=\"" + moduleName.toLowerCase(Locale.getDefault()) + "Form\" id=\"" + moduleName.toLowerCase(Locale.getDefault()) + "Form\" method=\"post\" action=\"#\" onsubmit=\"return false;\">");
            pw.println("                            <div class=\"menu_new\">");
            pw.println("                                <div class=\"menu_caption_bg cursor-pointer\" onclick=\"javascript:hide_menu('show_hide','hide_menu', 'nav_show','nav_hide')\">");
            pw.println("                                    <div class=\"menu_caption_text\">" + dEntityBean.getReportTitle() + "</div>");
            pw.println("                                    <span>");
            pw.println("                                        <a href=\"javascript:void(0)\" name=\"show_hide\" id=\"show_hide\" class=\"nav_hide\"></a>");
            pw.println("                                    </span>");
            pw.println("                                </div>");
            pw.println("                                <div class=\"report_filter_bg\">");
            pw.println("                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" class=\"report_filter\">");
            pw.println("                                        <tr>");
            if (dEntityBean.isReportType())
            {
                pw.println("                                            <td><a href=\"javascript:void(0);\" onclick=\"displayReportMenu('report_type_out');\">Report Type</a>");
                pw.println("                                                <span id=\"report_type\" class=\"report_filter\"></span>");
                pw.println("                                            </td>");
            }
            if (dEntityBean.isFilter())
            {
                pw.println("                                            <td><a href=\"javascript:void(0);\" onclick=\"displayReportMenu('report_filter_out');\">Filter</a>");
                pw.println("                                                <span id=\"filter\" class=\"report_filter\"></span>");
                pw.println("                                            </td>");
            }
            if (dEntityBean.isChart())
            {
                pw.println("                                            <td><a href=\"javascript:void(0);\" onclick=\"displayReportMenu('report_graph_out');\">Graph</a>");
                pw.println("                                                <span id=\"graph\" class=\"report_filter\"></span>");
                pw.println("                                            </td>");
            }
            pw.println("                                                <td><a href=\"javascript:void(0);\" onclick=\"displayReportMenu('report_export_out');\">Export</a>");
            pw.println("                                                    <span id=\"export\" class=\"report_filter\"></span>");
            pw.println("                                                </td>");
            if (dEntityBean.isColumns())
            {
                pw.println("                                            <td><a href=\"javascript:void(0);\" onclick=\"displayReportMenu('report_column_out');\">Columns</a>");
                pw.println("                                                <span id=\"columns\" class=\"report_filter\"></span>");
                pw.println("                                            </td>");
            }
            pw.println("                                        </tr>");
            pw.println("                                    </table>");
            if (dEntityBean.isDateTimePicker())
            {
                pw.println("                                    <table class=\"input_date\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
                pw.println("                                        <tr>");
                pw.println("                                            <td class=\"from_date\">");
                pw.println("                                                <label>From :</label><input type=\"text\" value=\"${dateString}\" class=\"datepickerclass\" id=\"fromDate\" name=\"fromDate\" >");
                pw.println("                                                <script type=\"text/javascript\">loadDatePicker(\"fromDate\");</script>");
                pw.println("                                            </td>");
                pw.println("                                            <td class=\"to_date\">");
                pw.println("                                                <label>To :</label><input type=\"text\" value=\"${dateString}\" class=\"datepickerclass\" id=\"toDate\" name=\"toDate\" >");
                pw.println("                                                <script type=\"text/javascript\">loadDatePicker(\"toDate\");</script>");
                pw.println("                                            </td>");
                pw.println("                                            <td class=\"submit_date\">");
                pw.println("                                                <input type=\"button\" value=\"GO\" class=\"button\" onclick=\"javascript: showReport('View');\" >");
                pw.println("                                            </td>");
                pw.println("                                        </tr>");
                pw.println("                                    </table>");
            }
            pw.println("                                </div>");
            pw.println("                                <div id=\"hide_menu\" style=\"display: block;\">");
            if (dEntityBean.isReportType())
            {
                FinDhtmlReportTypeGenerator rtg = new FinDhtmlReportTypeGenerator();
                rtg.generateReportTypeJsp(dEntityBean);
                pw.println("                                    <div id=\"report_type_out\" class=\"report_out\" style=\"display: block\">");
                pw.println("                                        <jsp:include page=\"reporttype.jsp\"/>");
                pw.println("                                    </div>");
            }
            if (dEntityBean.isFilter())
            {
                FinDhtmlReportFilterGenerator filterGenerator = new FinDhtmlReportFilterGenerator();
                filterGenerator.generateFilterJsp(dEntityBean);
                pw.println("                                    <div id=\"report_filter_out\" class=\"report_out\" style=\"display: none\">");
                pw.println("                                        <jsp:include page=\"filter.jsp\"/>");
                pw.println("                                    </div>");
            }


            if (dEntityBean.getFltrchkForDependentCombo() != null && dEntityBean.getFltrchkForDependentCombo().length > 0 && dEntityBean.getFltrCmbDependent() != null && !dEntityBean.getFltrCmbDependent().equalsIgnoreCase("-1"))
            {
                for (int i = 0; i < dEntityBean.getFltrchkForDependentCombo().length; i++)
                {
                    if (dEntityBean.getFltrchkForDependentCombo()[i].equalsIgnoreCase("false"))
                    {
                        flag = false;
                    }
                    else
                    {
                        flag = true;
                        break;
                    }
                }
                if (flag)
                {
                    FinDhtmlReportAjaxGenerator ajaxGenerator = new FinDhtmlReportAjaxGenerator();
                    ajaxGenerator.generateAjaxJsp(dEntityBean);
                }
            }

            FinDhtmlReportExportGenerator exportGenerator = new FinDhtmlReportExportGenerator();
            exportGenerator.generateExportJsp(dEntityBean);
            pw.println("                                        <div id=\"report_export_out\" class=\"report_out\" style=\"display: none\">");
            pw.println("                                            <jsp:include page=\"export.jsp\"/>");
            pw.println("                                        </div>");
            if (dEntityBean.isChart())
            {
                FinDhtmlReportGraphGenerator graphGenerator = new FinDhtmlReportGraphGenerator();
                graphGenerator.generateGraphJsp(dEntityBean, sEntityBean);
                pw.println("                                    <div id=\"report_graph_out\" class=\"report_out\" style=\"display: none\">");
                pw.println("                                        <jsp:include page=\"graph.jsp\"/>");
                pw.println("                                    </div>");
            }

            if (dEntityBean.isColumns())
            {
                FinDhtmlReportColumnGenerator columnGenerator = new FinDhtmlReportColumnGenerator();
                columnGenerator.generateColumnJsp(dEntityBean, sEntityBean);
                pw.println("                                    <div id=\"report_column_out\" class=\"report_out\" style=\"display: none\">");
                pw.println("                                        <jsp:include page=\"columns.jsp\"/>");
                pw.println("                                    </div>");
            }
            pw.println("                                    <div class=\"report_but\">");
            pw.println("                                        <input type=\"hidden\" name=\"rptfor\" id=\"rptfor\" >");
            pw.println("                                        <input class=\"button\" type=\"button\" value=\"Apply\" id=\"apply\" name=\"apply\" onclick=\"javascript: showReport('View');\" >");
            pw.println("                                        <input class=\"button\" type=\"button\" value=\"Print\" id=\"btnPrint\" name=\"print\" onclick=\"javascript: showReport('Print');\">");
            pw.println("                                        <input class=\"button\" type=\"button\" value=\"Close\" name=\"close\"");
            pw.println("                                            onclick=\"javascript: hide_menu('show_hide', 'hide_menu', 'nav_show', 'nav_hide')\" >");
            pw.println("                                    </div>");
            pw.println("                                </div>");
            pw.println("                            </div>");
            pw.println("                            <div id=\"dispError\" style=\"display: none;\" align=\"center\"></div>");
            pw.println("                            <div class=\"clear\"></div>");

            FinDhtmlReportGridGenerator gridJsp = new FinDhtmlReportGridGenerator();
            gridJsp.generateGridJsp(dEntityBean);
            pw.println("                            <jsp:include page=\"grid.jsp\"/>");
//            pw.println("                            <input type=\"hidden\" name=\"grid_xml\" id=\"grid_xmld\">");
            pw.println("                        </form>");
            pw.println("                    </div>");
            pw.println("                    <div id=\"html_out\"></div>");
            if (dEntityBean.isFooterReq())
            {
                pw.println("                    <jsp:include page=\"bottom.jsp\"/>");
            }
            pw.println("                </div>");
            pw.println("                    </td>");
            pw.println("                </tr>");
            pw.println("            </table>");
            pw.println("            <div style=\"display: none;\" id=\"loadingImageID\" class=\"cmbLoading\" align=\"center\"><span>Loading...</span></div>");
            pw.println("            </body>");
            pw.println("        </html>");
            pw.println("    </c:when>");
            pw.println("    <c:when test=\"${process eq 'getreportGrid'}\">");
            pw.println("        ${json}");
            pw.println("    </c:when>");
            if (dEntityBean.isChart())
            {
                pw.println("    <c:when test=\"${process eq 'getChart'}\">");
                pw.println("        <img alt=\"chartImage\" src=\"${imagePath}\" height=\"500px\" width=\"780px\" >");
                pw.println("    </c:when>");
            }
            if (dEntityBean.isHtml())
            {
                pw.println("    <c:when test=\"${process eq 'getHtmlReport'}\">");
                pw.println("        ${filePath}");
                pw.println("    </c:when>");
            }
            pw.println("</c:choose>");
        }
        finally
        {
            pw.close();
        }
    }
}
