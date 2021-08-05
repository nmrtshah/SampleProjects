/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.reportgenerator;

import com.finlogic.apps.finstudio.formbean.ReportGeneratorFormBean;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import com.finlogic.util.Logger;

/**
 *
 * @author njuser
 */
public class JSPGenerator
{

    private ArrayList<String> columnNames = new ArrayList<String>();
    private ArrayList<String> columnTypes = new ArrayList<String>();

    public JSPGenerator(ReportGeneratorFormBean reportGeneratorForm, ArrayList<String> columnNames, ArrayList<String> columnTypes) throws Exception
    {
        this.columnNames = columnNames;
        this.columnTypes = columnTypes;
        generateMenuJSP(reportGeneratorForm);
    }

    public void generateMenuJSP(ReportGeneratorFormBean reportGeneratorForm) throws Exception
    {
        String calculationList[] = null;
        if (reportGeneratorForm.isGroupFooter())
        {
            calculationList = new String[columnNames.size()];
            for (int i = 0; i < calculationList.length; i++)
            {
                calculationList[i] = "";
            }
            String footerColumn[] = reportGeneratorForm.getSelectGroupFooter();
            String calculation[] = reportGeneratorForm.getCalculation();
            for (int i = 0; i < footerColumn.length; i++)
            {
                int ind = columnNames.indexOf(footerColumn[i].toString());
                calculationList[ind] = calculation[i];
            }
        }


        String selectColumn[] = reportGeneratorForm.getSelectColumn();
        String selectControl[] = reportGeneratorForm.getSelectControl();
        int selectIndexNumber[];

        try
        {
            String tomcatPath = finpack.FinPack.getProperty("tomcat1_path");
            long number = reportGeneratorForm.getSerialNo();
            String moduleName = reportGeneratorForm.getModuleName();
            String filePath = tomcatPath + "/webapps/finstudio/generated/" + number + "/";
            String projectPath = filePath + reportGeneratorForm.getProjectName() + "/WEB-INF/jsp/" + moduleName + "/";

            if (reportGeneratorForm.isAddControl() == true)
            {
                String selectIndexNum[] = reportGeneratorForm.getIndexNumber();

                selectIndexNumber = new int[selectIndexNum.length];

                int index = 0;
                for (String s : selectIndexNum)
                {
                    if (s == null || s.equals(""))
                    {
                        s = "0";
                    }
                    selectIndexNumber[index++] = Integer.parseInt(s);
                }

                for (int i = 0; i < selectIndexNumber.length - 1; i++)
                {
                    if (selectIndexNumber[i] > selectIndexNumber[i + 1])
                    {
                        int temp1;
                        temp1 = selectIndexNumber[i];
                        selectIndexNumber[i] = selectIndexNumber[i + 1];
                        selectIndexNumber[i + 1] = temp1;

                        String temp2;
                        temp2 = selectColumn[i];
                        selectColumn[i] = selectColumn[i + 1];
                        selectColumn[i + 1] = temp2;

                        temp2 = selectControl[i];
                        selectControl[i] = selectControl[i + 1];
                        selectControl[i + 1] = temp2;
                    }
                }
            }
            else
            {
                selectIndexNumber = new int[1];
            }
            File file = new File(projectPath);
            file.mkdirs();
            PrintWriter pw = new PrintWriter(projectPath + moduleName + ".jsp");

            pw.println("<%@page contentType=\"text/html\" pageEncoding=\"UTF-8\"%>");
            pw.println("<%@page import=\"java.io.*\" %>");
            pw.println("<%@taglib uri=\"http://java.sun.com/jsp/jstl/core\" prefix=\"c\" %>");

            pw.println("<c:choose>");
            if (reportGeneratorForm.isGrid())
            {
                pw.println("    <c:when test=\"${process eq 'getreportGrid'}\">");
                pw.println("        ${json}");
                pw.println("    </c:when>");
            }
            if (reportGeneratorForm.isPdf() || reportGeneratorForm.isXls())
            {
                pw.println("    <c:when test=\"${process eq 'getreport'}\">");
                pw.println("        <%");
                pw.println("           ServletOutputStream out1 = response.getOutputStream();");
                pw.println("           String outFileName = null;");
                pw.println("        %>");
                if (reportGeneratorForm.isPdf())
                {
                    pw.println("        <c:if test=\"${jaspertopdf ne null}\">");
                    pw.println("            <%");
                    pw.println("                String tomcatPath1 = finpack.FinPack.getProperty(\"tomcat1_path\");");
                    pw.println("                outFileName = tomcatPath1 +\"/webapps/" + reportGeneratorForm.getProjectName() + "/WEB-INF/classes/com/finlogic/business/" + reportGeneratorForm.getProjectName().toLowerCase() + "/datamanager/" + moduleName + ".pdf\";");
                    pw.println("                response.setContentType(\"application/pdf\");");
                    pw.println("                response.setHeader(\"Content-disposition\", \"attachment; filename=" + moduleName + ".pdf\");");
                    pw.println("             %>");
                    pw.println("        </c:if>");
                }
                if (reportGeneratorForm.isXls())
                {
                    pw.println("        <c:if test=\"${jaspertoexcel ne null}\">");
                    pw.println("            <%");
                    pw.println("                String tomcatPath2 = finpack.FinPack.getProperty(\"tomcat1_path\");");
                    pw.println("                outFileName = tomcatPath2 +\"/webapps/" + reportGeneratorForm.getProjectName() + "/WEB-INF/classes/com/finlogic/business/" + reportGeneratorForm.getProjectName().toLowerCase() + "/datamanager/" + moduleName + ".xls\";");
                    pw.println("                response.setContentType(\"application/vnd.ms-excel\");");
                    pw.println("                response.setHeader(\"Content-disposition\", \"attachment; filename=" + moduleName + ".xls\");");
                    pw.println("            %>");
                    pw.println("        </c:if>");
                }
                pw.println("        <%");
                pw.println("           BufferedInputStream bis = null;");
                pw.println("           BufferedOutputStream bos = null;");
                pw.println("           try");
                pw.println("           {");
                pw.println("               bis = new BufferedInputStream(new FileInputStream(outFileName));");
                pw.println("               bos = new BufferedOutputStream(out1);");
                pw.println("               byte[] buff = new byte[2048];");
                pw.println("               int bytesRead;");

                pw.println("               while (-1 != (bytesRead = bis.read(buff, 0, buff.length)))");
                pw.println("               {");
                pw.println("                   bos.write(buff, 0, bytesRead);");
                pw.println("               }");
                pw.println("           }");
                pw.println("           catch (Exception e)");
                pw.println("           {");
                pw.println("               throw new ServletException(e);");
                pw.println("           }");
                pw.println("           finally");
                pw.println("           {");
                pw.println("               if (bis != null)");
                pw.println("               {");
                pw.println("                   bis.close();");
                pw.println("               }");
                pw.println("               if (bos != null)");
                pw.println("               {");
                pw.println("                   bos.close();");
                pw.println("               }");
                pw.println("           }");
                pw.println("           out.close();");
                pw.println("       %>");
                pw.println("    </c:when>");
            }
            pw.println("    <c:when test=\"${process eq 'getmenu'}\">");
            pw.println("        <html>");
            pw.println("            <head>");
            pw.println("                <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
            pw.println("                <title>Welcome to Spring Web MVC project</title>");

            pw.println("                <link rel=\"stylesheet\" type=\"text/css\" href=\"${finlibPath}/jquery/jquery.jqGrid-3.8.2/css/ui.jqgrid.css\">");
            pw.println("                <link rel=\"stylesheet\" type=\"text/css\" href=\"${finlibPath}/jquery/jquery-ui-1.8.2/development-bundle/themes/njred/jquery.ui.all.css\"  />");
            pw.println("                <link rel=\"stylesheet\" type=\"text/css\" href=\"${finlibPath}/resource/main.css\"/>");

            pw.println("                <script type=\"text/javascript\" src=\"${finlibPath}/jquery/jquery-ui-1.8.2/development-bundle/jquery-1.4.2.js\"></script>");
            pw.println("                <script type=\"text/javascript\" src=\"${finlibPath}/jquery/jquery-ui-1.8.2/development-bundle/ui/jquery.ui.core.js\"></script>");
            pw.println("                <script type=\"text/javascript\" src=\"${finlibPath}/jquery/jquery-ui-1.8.2/development-bundle/ui/jquery.ui.datepicker.js\"></script>");
            pw.println("                <script type=\"text/javascript\" src=\"${finlibPath}/jquery/jquery.jqGrid-3.8.2/js/i18n/grid.locale-en.js\"></script>");
            pw.println("                <script type=\"text/javascript\" src=\"${finlibPath}/jquery/jquery.jqGrid-3.8.2/js/jquery.jqGrid.min.js\"></script>");
            pw.println("                <script type=\"text/javascript\" src=\"${finlibPath}/jquery/jquery.jqGrid-3.8.2/js/jquery.jqGrid.min.js\"></script>");
            pw.println("                <script type=\"text/javascript\" src=\"${finlibPath}/jquery/jquery.jqGrid-3.8.2/src/grid.celledit.js\"></script>");
            pw.println("                <script type=\"text/javascript\" src=\"${finlibPath}/resource/ajax.js\"></script>");
            pw.println("                <script type=\"text/javascript\">");
            if (reportGeneratorForm.isGrid())
            {
                pw.println("                function showGrid()");
                pw.println("                {");
                pw.println("                    $('#list2').GridUnload();");
                pw.println("                    var params=getFormData(document.viewform);");
                pw.println("                    jQuery(\"#list2\").jqGrid({");

                pw.println("                    url:'" + moduleName + ".fin?cmdAction=getReportGrid&'+params,");

                pw.println("                    datatype: \"json\",");

                pw.print("                      colNames:[");


                int cnt = 1;
                int i = 0;
                for (int k = 0; k < columnNames.size();)
                {
                    if (reportGeneratorForm.isAddControl() == true
                            && i < selectIndexNumber.length
                            && cnt == selectIndexNumber[i])
                    {
                        if (!(selectControl[i].equals("link")))
                        {
                            pw.print("'" + selectColumn[i] + "_CONTROL', ");
                        }
                        i++;
                    }
                    else
                    {
                        String name = columnNames.get(k);
                        pw.print("'" + name + "', ");
                        k++;
                    }
                    cnt++;
                }

                while (reportGeneratorForm.isAddControl() == true && i < selectIndexNumber.length)
                {
                    if (!(selectControl[i].equals("link")))
                    {
                        pw.print("'" + selectColumn[i] + "_CONTROL',");
                    }
                    i++;
                }

                pw.println("],");


                pw.println("                    colModel:[");

                cnt = 1;
                i = 0;
                for (int k = 0; k < columnNames.size(); k++)
                {
                    if (reportGeneratorForm.isAddControl() == true
                            && i < selectIndexNumber.length
                            && cnt == selectIndexNumber[i])
                    {
                        if (!(selectControl[i].equals("link")))
                        {
                            pw.println("                        {name:'" + selectColumn[i] + "_CONTROL',index:'" + selectColumn[i] + "_CONTROL', width:150},");
                        }
                        i++;
                        k--;
                    }
                    else
                    {
                        String name = columnNames.get(k);
                        if ((reportGeneratorForm.isGroupFooter())
                                && !(calculationList[k].equals("")) && (calculationList[k] != null))
                        {
                            if (columnTypes.get(k).equals("java.lang.Integer"))
                            {
                                pw.println("                        {name:'" + name + "',index:'" + name + "',  summaryType:'" + calculationList[k] + "', summaryTpl : '" + calculationList[k] + "({0})', align:\"right\", sorttype:'int', width:150},");
                            }
                            else if (columnTypes.get(k).equals("java.lang.Float"))
                            {
                                pw.println("                        {name:'" + name + "',index:'" + name + "',  summaryType:'" + calculationList[k] + "', summaryTpl : '" + calculationList[k] + "({0})', align:\"right\", formatter:'number', formatoptions:{decimalPlaces: 4}, sorttype:'int', width:150},");
                            }
                            else
                            {
                                pw.println("                        {name:'" + name + "',index:'" + name + "',  summaryType:'" + calculationList[k] + "', summaryTpl : '" + calculationList[k] + "({0})', width:150},");
                            }
                        }
                        else
                        {
                            if (columnTypes.get(k).equals("java.lang.Integer"))
                            {
                                pw.println("                        {name:'" + name + "',index:'" + name + "', sorttype:'int', align:\"right\", width:150},");
                            }
                            else if (columnTypes.get(k).equals("java.lang.Float"))
                            {
                                pw.println("                        {name:'" + name + "',index:'" + name + "', formatter:'number', formatoptions:{decimalPlaces: 4}, sorttype:'int', align:\"right\", width:150},");
                            }
                            else
                            {
                                pw.println("                        {name:'" + name + "',index:'" + name + "', width:150},");
                            }
                        }
                    }
                    cnt++;
                }
                while (reportGeneratorForm.isAddControl() == true && i < selectIndexNumber.length)
                {
                    if (!(selectControl[i].equals("link")))
                    {
                        pw.println("                        {name:'" + selectColumn[i] + "_CONTROL',index:'" + selectColumn[i] + "_CONTROL', width:150},");
                    }
                    i++;
                }

                pw.println("                    ],");
                if (reportGeneratorForm.isPaging() == true)
                {
                    pw.println("                    rowNum: 10,");
                    pw.println("                    rowList:[10,20,30],");
                    pw.println("                    pager: '#pager2',");
                }
                else
                {
                    pw.println("                    rowNum: 1000,");
                }

                pw.println("                    height: 'auto',");
                pw.println("                    sortname: '" + reportGeneratorForm.getGridColumnPK() + "',");
                pw.println("                    viewrecords: true,");
                pw.println("                    sortorder: \"desc\",");
                pw.println("                    caption:\"Grid View\",");
                if (!reportGeneratorForm.getGrouping().equals("none"))
                {
                    pw.println("                    grouping: true,");
                    pw.println("                    groupingView : {");
                    pw.println("                        groupField : ['" + reportGeneratorForm.getGrouping() + "'],");
                    pw.println("                        groupSummary : [true],");
                    pw.println("                        groupColumnShow : [true],");
                    pw.println("                        groupText : ['{0}'],");
                    pw.println("                        groupCollapse : false,");
                    pw.println("                        groupOrder: ['asc']");
                    pw.println("                    },");
                }
                else
                {
                    pw.println("                    grouping: false,");
                }
                pw.println("                    footerrow: true,");

                if (reportGeneratorForm.isAddControl() == true)
                {
                    pw.println("                    userDataOnFooter: true,");
                    pw.println("                    gridComplete:function() {");
                    pw.println("                        var ids = jQuery(\"#list2\").jqGrid(\"getDataIDs\");");
                    pw.println("                        for(var i=0; i<ids.length; i++)");
                    pw.println("                        {");
                    pw.println("                            var cl = ids[i];");
                    for (int j = 0; j < selectColumn.length; j++)
                    {
                        pw.println("                            var column" + j + " = $('#list2').getCell(cl, '" + selectColumn[j] + "');");
                        if (selectControl[j].equals("link"))
                        {
                            pw.println("                            var vcontrol" + j + "=\"<a href='#'> <font color='BLUE'>\"+ column" + j + " +\" </font></a>\";");
                        }
                        else
                        {
                            pw.println("                            var vcontrol" + j + "=\"<input type='" + selectControl[j] + "' value='\"+ column" + j + " +\"'>\";");
                        }
                    }
                    pw.println("                            jQuery(\"#list2\").jqGrid('setRowData',ids[i],{");
                    for (int j = 0; j < selectColumn.length; j++)
                    {
                        if (!(selectControl[j].equals("link")))
                        {
                            if (j == (selectColumn.length - 1))
                            {
                                pw.println("                                " + selectColumn[j] + "_CONTROL : vcontrol" + j);
                            }
                            else
                            {
                                pw.println("                                " + selectColumn[j] + "_CONTROL : vcontrol" + j + ",");
                            }
                        }
                        else
                        {
                            if (j == (selectColumn.length - 1))
                            {
                                pw.println("                                " + selectColumn[j] + " : vcontrol" + j);
                            }
                            else
                            {
                                pw.println("                                " + selectColumn[j] + " : vcontrol" + j + ",");
                            }
                        }
                    }

                    pw.println("                            });");
                    pw.println("                           ");
                    pw.println("                        }");
                    pw.println("                    }");
                }
                else
                {
                    pw.println("                    userDataOnFooter: true");
                }
                pw.println("                    });");
                if (reportGeneratorForm.isPaging() == true)
                {
                    pw.println("                    jQuery(\"#list2\").jqGrid('navGrid','#pager2',{add:true,edit:true,del:true});");
                }
                pw.println("                }");
            }
            if (reportGeneratorForm.isPdf())
            {
                pw.println("                function showPdf()");
                pw.println("                {");
                pw.println("                    document.getElementById('viewform').action=\"" + moduleName + ".fin?cmdAction=getReportPdf\";");
                pw.println("                    document.getElementById('viewform').submit();");
                pw.println("                }");
            }
            if (reportGeneratorForm.isXls())
            {
                pw.println("                function showExcel()");
                pw.println("                {");
                pw.println("                    document.getElementById('viewform').action=\"" + moduleName + ".fin?cmdAction=getReportExcel\";");
                pw.println("                    document.getElementById('viewform').submit();");
                pw.println("                }");
            }
            pw.println("                function showReport()");
            pw.println("                {");
            pw.println("                    var view = document.getElementsByName('view');");

            int indexCount = 0;
            if (reportGeneratorForm.isGrid())
            {
                pw.println("                    if(view[" + indexCount++ + "].checked==true)");
                pw.println("                    {");
                pw.println("                        document.getElementById('viewdiv').style.display=\"none\";");
                pw.println("                        document.getElementById('griddiv').style.display=\"\";");
                pw.println("                        showGrid();");
                pw.println("                        return true;");
                pw.println("                    }");
            }
            if (reportGeneratorForm.isPdf())
            {
                pw.println("                    if(view[" + indexCount++ + "].checked==true)");
                pw.println("                    {");
                pw.println("                        showPdf();");
                pw.println("                        return true;");
                pw.println("                    }");
            }
            if (reportGeneratorForm.isXls())
            {
                pw.println("                    if(view[" + indexCount++ + "].checked==true)");
                pw.println("                    {");
                pw.println("                        showExcel();");
                pw.println("                        return true;");
                pw.println("                    }");
            }
            pw.println("                }");
            pw.println("                function showMenu()");
            pw.println("                {");
            pw.println("                    document.getElementById(\"viewdiv\").style.display=\"\";");
            pw.println("                    document.getElementById(\"griddiv\").style.display=\"none\";");
            pw.println("                }");
            pw.println("                </script>");
            pw.println("            </head>");
            pw.println("            <body>");
            pw.println("                <form name=\"viewform\" id=\"viewform\" action=\"" + moduleName + ".fin?cmdAction=getReport\" method=\"post\">");
            pw.println("                    <div id=\"viewdiv\">");
            pw.println("                        <table border=\"0\"align=\"center\" class=\"tbl_border1\" id=\"viewtable\" width=\"40%\">");
            pw.println("                            <tr class=\"tbl_h1_bg\">");
            pw.println("                                <th colspan=\"100%\">Select View</th>");
            pw.println("                            </tr>");
            String inputNames[] = reportGeneratorForm.getInputName();
            String inputControls[] = reportGeneratorForm.getInputControl();
            for (int i = 0; i < inputNames.length; i++)
            {
                pw.println("                            <tr>");
                pw.println("                                <td width=\"50%\" align=\"right\">");
                pw.println("                                    Enter " + inputNames[i].toLowerCase() + " :");
                pw.println("                                </td>");
                pw.println("                                <td align=\"left\">");
                if (inputControls[i].equals("combobox"))
                {
                    pw.println("                                    <select id=\"" + inputNames[i].toLowerCase() + "\" name=\"" + inputNames[i].toLowerCase() + "\">");
                    pw.println("                                        <option value=\"\"></option>");
                    pw.println("                                    </select>");
                }
                else
                {
                    pw.println("                                    <input type=\"" + inputControls[i].toLowerCase() + "\" name=\"" + inputNames[i].toLowerCase() + "\" id=\"" + inputNames[i] + "\">");
                }
                pw.println("                                </td>");
                pw.println("                            </tr>");
            }
            pw.println("                            <tr>");
            pw.println("                                <td align=\"right\">");
            pw.println("                                    Select Report Type :");
            pw.println("                                </td>");
            pw.println("                                <td colspan=\"100%\" align=\"left\">");
            if (reportGeneratorForm.isGrid())
            {
                pw.println("                                    Grid : <input type=\"radio\" name=\"view\" id=\"view\" value=\"grid\" checked=\"1\">");
            }
            if (reportGeneratorForm.isPdf())
            {
                pw.println("                                    PDF : <input type=\"radio\" name=\"view\" id=\"view\" value=\"pdf\">");
            }
            if (reportGeneratorForm.isXls())
            {
                pw.println("                                    Excel : <input type=\"radio\" name=\"view\" id=\"view\" value=\"xls\">");
            }
            pw.println("                                </td>");
            pw.println("                            </tr>");
            pw.println("                            <tr>");
            pw.println("                                <td colspan=\"100%\" align=\"center\">");
            pw.println("                                    <input type=\"button\" name=\"btnsubmit\" value=\"Show Report\" onclick=\"javascript: showReport();\" />");
            pw.println("                                </td>");
            pw.println("                            </tr>");
            pw.println("                        </table>");
            pw.println("                    </div>");
            pw.println("                    <div id=\"griddiv\" align=\"center\" style=\"display: none\">");
            pw.println("                        <table id=\"list2\" align=\"center\">");
            pw.println("                        </table>");
            pw.println("                        <div id=\"pager2\">");
            pw.println("                        </div>");
            pw.println("                        <input type=\"button\" name=\"back\" id=\"back\" value=\"Back\" onclick=\"javascript:showMenu();\">");
            pw.println("                    </div>");
            pw.println("                </form>");
            pw.println("            </body>");
            pw.println("        </html>");
            pw.println("    </c:when>");
            pw.println("</c:choose>");
            pw.close();
        }
        catch (Exception ex)
        {
            Logger.ErrorLogger(ex);
        }
    }
}
