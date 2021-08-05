/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.mastergenerator;

import com.finlogic.apps.finstudio.formbean.MasterGeneratorFormBean;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author njuser
 */
public class JSPGenerator
{

    public JSPGenerator(MasterGeneratorFormBean formBean, ArrayList<String> allColumns, ArrayList<String> selectedColumns, ArrayList<String> selectedControls) throws Exception
    {
        String tomcatPath = finpack.FinPack.getProperty("tomcat1_path");
        long number = formBean.getSerialNo();
        String moduleName = formBean.getModuleName();
        String filePath = tomcatPath + "/webapps/finstudio/generated/" + number + "/";
        String projectPath = filePath + formBean.getProjectName() + "/WEB-INF/jsp/" + moduleName + "/";
        File file = new File(projectPath);
        file.mkdirs();
        PrintWriter pw = new PrintWriter(projectPath + moduleName + ".jsp");

        pw.println("<%@page contentType=\"text/html\" pageEncoding=\"UTF-8\"%>");
        pw.println("<%@page import=\"java.io.*\" %>");
        pw.println("<%@page import=\"java.util.List\" %>");
        pw.println("<%@page import=\"java.util.HashMap\" %>");
        pw.println("<%@taglib uri=\"http://java.sun.com/jsp/jstl/core\" prefix=\"c\" %>");
        pw.println("<%@taglib uri=\"http://java.sun.com/jsp/jstl/functions\" prefix=\"fn\" %>");

        pw.println("<c:choose>");
        pw.println("    <c:when test=\"${status eq 'inserted'}\">");
        pw.println("        <font color=\"BLUE\" size=\"2\"> Record inserted Successfully... </font>");
        pw.println("        <br>");
        pw.println("        <input type=\"button\" name=\"back\" id=\"back\" value=\"Back\" onclick=\"javascript:showLastInsertUpdate();\">");
        pw.println("    </c:when>");
        pw.println("    <c:when test=\"${status eq 'updated'}\">");
        pw.println("        <font color=\"BLUE\" size=\"2\"> Record Updated Sucessfully...</font>");
        pw.println("        <br>");
        pw.println("        <input type=\"button\" name=\"back\" id=\"back\" value=\"Back\" onclick=\"javascript:showLastInsertUpdate();\">");
        pw.println("    </c:when>");
        pw.println("    <c:when test=\"${status eq 'deleted'}\">");
        pw.println("        <c:if test=\"${deleteCount eq 1}\">");
        pw.println("            <font color=\"BLUE\" size=\"2\"> 1 Record Sucessfully Deleted.</font>");
        pw.println("        </c:if>");
        pw.println("        <c:if test=\"${deleteCount gt 1}\">");
        pw.println("             <font color=\"BLUE\" size=\"2\"> ${deleteCount} Records Sucessfully Deleted.</font>");
        pw.println("        </c:if>");
        pw.println("    </c:when>");
        pw.println("    <c:when test=\"${status eq 'error'}\">");
        pw.println("        <font color=\"BLUE\" size=\"2\"> Error...</font>");
        pw.println("    </c:when>");
        pw.println("    <c:when test=\"${process eq 'getmenu'}\">");
        pw.println("        <html>");
        pw.println("            <head>");
        pw.println("                <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");

        pw.println("                <link rel=\"stylesheet\" type=\"text/css\" href=\"${finlibPath}/jquery/jquery.jqGrid-3.8.2/css/ui.jqgrid.css\">");
        pw.println("                <link rel=\"stylesheet\" type=\"text/css\" href=\"${finlibPath}/jquery/jquery-ui-1.8.2/development-bundle/themes/njred/jquery.ui.all.css\"/>");
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
        pw.println("                    function showInsertMenu()");
        pw.println("                    {");
        pw.println("                        document.getElementById(\"insertUpdateDiv\").style.display=\"\";");
        pw.println("                        document.getElementById(\"statusDiv\").style.display=\"none\";");
        pw.println("                        document.getElementById(\"gridDiv\").style.display=\"none\";");
        pw.println("                        getAsynchronousData(\"" + formBean.getModuleName() + ".fin?cmdAction=showInsert\",null,\"insertUpdateDiv\");");
        pw.println("                    }");
        pw.println("                    function showMenu()");
        pw.println("                    {");
        pw.println("                        document.getElementById(\"menuDiv\").style.display=\"\";");
        pw.println("                        document.getElementById(\"insertUpdateDiv\").style.display=\"none\";");
        pw.println("                        document.getElementById(\"statusDiv\").style.display=\"none\";");
        pw.println("                        document.getElementById(\"gridDiv\").style.display=\"none\";");
        pw.println("                    }");
        pw.println("                    function insertRecord()");
        pw.println("                    {");
        pw.println("                        if(validate())");
        pw.println("                        {");
        pw.println("                            var params=getFormData(document.masterForm);");
        pw.println("                            document.getElementById(\"insertUpdateDiv\").style.display=\"none\";");
        pw.println("                            document.getElementById(\"statusDiv\").style.display=\"\";");
        pw.println("                            getAsynchronousData(\"" + formBean.getModuleName() + ".fin?cmdAction=insert\",params,\"statusDiv\");");
        pw.println("                        }");
        pw.println("                    }");
        pw.println("                    function validate()");
        pw.println("                    {");
        pw.println("                        //write validations here..");
        pw.println("                        return true;");
        pw.println("                    }");
        pw.println("                    function showLastInsertUpdate()");
        pw.println("                    {");
        pw.println("                        document.getElementById(\"insertUpdateDiv\").style.display=\"\";");
        pw.println("                        document.getElementById(\"statusDiv\").style.display=\"none\";");
        pw.println("                    }");
        pw.println("                    function showList()");
        pw.println("                    {");
        pw.println("                        document.getElementById(\"statusDiv\").style.display=\"none\";");
        pw.println("                        document.getElementById(\"insertUpdateDiv\").style.display=\"none\";");
        pw.println("                        document.getElementById(\"gridDiv\").style.display=\"\";");
        pw.println("                        showGrid();");
        pw.println("                    }");
        pw.println("                    function edit(pk)");
        pw.println("                    {");
        pw.println("                        document.getElementById(\"insertUpdateDiv\").style.display=\"\";");
        pw.println("                        document.getElementById(\"gridDiv\").style.display=\"none\";");
        pw.println("                        getAsynchronousData(\"" + formBean.getModuleName() + ".fin?cmdAction=getRecord\"+\"&primarykey=\"+pk,null,\"insertUpdateDiv\");");
        pw.println("                    }");
        pw.println("                    function updateRecord()");
        pw.println("                    {");
        pw.println("                        var params=getFormData(document.masterForm);");
        pw.println("                        getAsynchronousData(\"" + formBean.getModuleName() + ".fin?cmdAction=update\",params,\"statusDiv\");");
        pw.println("                        document.getElementById(\"insertUpdateDiv\").style.display=\"none\";");
        pw.println("                        document.getElementById(\"statusDiv\").style.display=\"\";");
        pw.println("                    }");
        pw.println("                    function deleteRecords()");
        pw.println("                    {");
        pw.println("                        var d = document.getElementsByName(\"delete\");");
        pw.println("                        var len = 0;");
        pw.println("                        for(var i=0;i<d.length;i++)");
        pw.println("                        {");
        pw.println("                            if(d.item(i).checked)");
        pw.println("                            {len++;}");
        pw.println("                        }");
        pw.println("                        if(len>0)");
        pw.println("                        {");
        pw.println("                            var params=getFormData(document.masterForm);");
        pw.println("                            getAsynchronousData(\"" + formBean.getModuleName() + ".fin?cmdAction=deleteRecords&\"+params,null,\"statusDiv\");");
        pw.println("                            document.getElementById(\"gridDiv\").style.display=\"none\";");
        pw.println("                            document.getElementById(\"statusDiv\").style.display=\"\";");
        pw.println("                        }");
        pw.println("                        else");
        pw.println("                        {");
        pw.println("                            alert(\"Select Records To Delete\");");
        pw.println("                        }");
        pw.println("                    }");
        pw.println("                    function showGrid()");
        pw.println("                    {");
        pw.println("                        $('#list').GridUnload();");
        pw.println("                        jQuery(\"#list\").jqGrid({");
        pw.println("                            url:'" + formBean.getModuleName() + ".fin?cmdAction=view',");
        pw.println("                            datatype: \"json\",");
        pw.print("                            colNames:[");
        for (String column : allColumns)
        {
            pw.print("'" + column + "',");
        }
        pw.print("'EDIT',");
        pw.print("'SELECT'");
        pw.println("],");
        pw.println("                              colModel:[");
        for (int i = 0; i < allColumns.size(); i++)
        {
            String name = allColumns.get(i);
            pw.println("                                {name:'" + name + "',index:'" + name + "', width:150},");
        }
        pw.println("                                {name:'EDIT',index:'EDIT', width:150},");
        pw.println("                                {name:'SELECT',index:'SELECT', width:150},");
        pw.println("                              ],");
        pw.println("                            rowNum: 10,");
        pw.println("                            rowList:[10,20,30],");
        pw.println("                            height: 'auto',");
        pw.println("                            pager: '#pager',");
        pw.println("                            sortname: '"+formBean.getPrimarykey().toLowerCase()+"',");
        pw.println("                            viewrecords: true,");
        pw.println("                            sortorder: \"desc\",");
        pw.println("                            caption:\"Grid View\",");
        pw.println("                            grouping: true,");
        pw.println("                            footerrow: true,");
        pw.println("                            userDataOnFooter: true,");
        pw.println("                            gridComplete:function(){");
        pw.println("                                var ids = jQuery(\"#list\").jqGrid(\"getDataIDs\");");
        pw.println("                                for(var i=0; i<ids.length; i++)");
        pw.println("                                {");
        pw.println("                                    var cl = ids[i];");
        pw.println("                                    var column = $('#list').getCell(cl, '" + formBean.getPrimarykey() + "');");
        pw.println("                                    var vcontrol1=\"<a href='#' onclick=\\\"javascript: edit(\"+column+\");\\\"> <font color='BLUE'>Edit</font></a>\";");
        pw.println("                                    var vcontrol2=\"<input name='delete' id='delete' type='checkbox' value='\"+ column +\"'>\";");
        pw.println("                                    jQuery(\"#list\").jqGrid('setRowData',ids[i],{");
        pw.println("                                        EDIT : vcontrol1,");
        pw.println("                                        SELECT : vcontrol2");
        pw.println("                                    });");
        pw.println("                                }");
        pw.println("                            }");
        pw.println("                        });");
        pw.println("                        jQuery(\"#list\").jqGrid('navGrid','#pager',{add:false,edit:false,del:false});");
        pw.println("                    }");
        pw.println("                </script>");
        pw.println("            </head>");
        pw.println("            <body>");
        pw.println("                <form action=\"\" name=\"masterForm\" id=\"masterForm\" method=\"post\">");
        pw.println("                    <div id=\"menuDiv\">");
        pw.println("                        <table class=\"tbl_border1\" border=\"0\" align=\"center\" id=\"menuTable\" width=\"20%\">");
        pw.println("                            <tr class=\"tbl_h1_bg\">");
        pw.println("                                <th colspan=\"100%\">Master</th>");
        pw.println("                            </tr>");
        pw.println("                            <tr>");
        pw.println("                                <td align=\"center\">");
        pw.println("                                    <input type=\"button\" name=\"insertButton\" id=\"insertButton\" value=\"Insert\" onclick=\"javascript: showInsertMenu();\">");
        pw.println("                                </td>");
        pw.println("                                <td align=\"center\">");
        pw.println("                                    <input type=\"button\" name=\"viewButton\" id=\"viewButton\" value=\"View\" onclick=\"javascript: showList();\">");
        pw.println("                                </td>");
        pw.println("                            </tr>");
        pw.println("                        </table>");
        pw.println("                    </div>");
        pw.println("                    <br>");
        pw.println("                    <div id=\"insertUpdateDiv\">");
        pw.println("                    </div>");

        pw.println("                    <div id=\"gridDiv\" align=\"center\" style=\"display: none\">");
        pw.println("                        <table id=\"list\" align=\"center\">");
        pw.println("                        </table>");
        pw.println("                        <div id=\"pager\">");
        pw.println("                        </div>");
        pw.println("                        <input type=\"button\" name=\"delete\" id=\"delete\" value=\"Delete\" onclick=\"javascript:deleteRecords();\">");
        pw.println("                        <input type=\"button\" name=\"back\" id=\"back\" value=\"Back\" onclick=\"javascript:showMenu();\">");
        pw.println("                    </div>");
        pw.println("                    <br>");

        pw.println("                    <div id=\"statusDiv\" align=\"center\">");
        pw.println("                    </div>");

        pw.println("                </form>");
        pw.println("            </body>");
        pw.println("        </html>");
        pw.println("    </c:when>");
        pw.println("    <c:when test=\"${process eq 'view'}\">");
        pw.println("        ${json}");
        pw.println("    </c:when>");
        pw.println("    <c:when test=\"${process eq 'showinsert'}\">");
        pw.println("        <div id=\"insertUpdateDiv\">");
        pw.println("            <div id =\"insertUpdateTableDiv\">");
        pw.println("                <table class=\"tbl_border1\" border=\"0\" align=\"center\" id=\"insertUpdateTable\">");
        pw.println("                    <c:choose>");
        pw.println("                        <c:when test=\"${record eq null}\">");
        pw.println("                            <tr class=\"tbl_h1_bg\">");
        pw.println("                                <th colspan=\"100%\">Insert</th>");
        pw.println("                            </tr>");
        pw.println("                        </c:when>");
        pw.println("                        <c:otherwise>");
        pw.println("                            <tr class=\"tbl_h1_bg\">");
        pw.println("                                <th colspan=\"100%\">Update</th>");
        pw.println("                            </tr>");
        pw.println("                        </c:otherwise>");
        pw.println("                    </c:choose>");
        pw.println("                    <%");
        pw.println("                        List list = (List) request.getAttribute(\"record\");");
        pw.println("                        HashMap map = null;");
        //pw.println("                        String address = "", name = "", deptNo = "", number = "";");
        for (String column : selectedColumns)
        {
            if (!(column.equals(formBean.getPrimarykey())))
            {
                pw.println("                        String " + column.toLowerCase() + " = \"\";");
            }
        }
        pw.println("                        String " + formBean.getPrimarykey().toLowerCase() + " = \"\";");
        pw.println("                        if (list != null)");
        pw.println("                        {");
        pw.println("                            map = (HashMap) list.get(0);");
        for (String column : selectedColumns)
        {
            if (!(column.equals(formBean.getPrimarykey())))
            {
                pw.println("                            if (map.get(\"" + column + "\") != null)");
                pw.println("                            {");
                pw.println("                                " + column.toLowerCase() + " = map.get(\"" + column + "\").toString();");
                pw.println("                            }");
            }
        }
        pw.println("                            if (map.get(\"" + formBean.getPrimarykey() + "\") != null)");
        pw.println("                            {");
        pw.println("                                " + formBean.getPrimarykey().toLowerCase() + " = map.get(\"" + formBean.getPrimarykey() + "\").toString();");
        pw.println("                            }");
        pw.println("                        }");
        pw.println("                    %>");

        for (int i = 0; i < selectedColumns.size(); i++)
        {
            if (selectedColumns.get(i).equals(formBean.getPrimarykey()))
            {
                pw.println("                     <c:if test=\"${record eq null}\">");
                pw.println("                        <tr>");
                pw.println("                            <td align=\"right\" width=\"50%\">");
                pw.println("                            " + selectedColumns.get(i) + " :");
                pw.println("                            </td>");

                pw.println("                            <td align=\"left\">");
                //pw.println("                            <input type=\"text\" name=\number\" id=\"number\" value=\"<%= number%>\">");
                String name = selectedColumns.get(i).toLowerCase();

                if (selectedControls.get(i).equals("Textbox"))
                {
                    pw.println("                                <input type=\"text\" name=\"" + name + "\" id=\"" + name + "\">");
                }
                else if (selectedControls.get(i).equals("Checkbox"))
                {
                    pw.println("                                <input type=\"checkbox\" name=\"" + name + "\" id=\"" + name + "\">");
                }
                else if (selectedControls.get(i).equals("Combobox"))
                {
                    pw.println("                                <select id=\"" + name + "\" name=\"" + name + "\">");
                    pw.println("                                    <option value=\"\"></option>");
                    pw.println("                                </select>");
                }
                else if (selectedControls.get(i).equals("File"))
                {
                    pw.println("                                <input type=\"file\" name=\"" + name + "\" id=\"" + name + "\">");
                }
                else if (selectedControls.get(i).equals("Radio"))
                {
                    pw.println("                                <input type=\"radio\" name=\"" + name + "\" id=\"" + name + "\">");
                }
                pw.println("                            </td>");
                pw.println("                        </tr>");
                pw.println("                    </c:if>");
            }
            else
            {
                pw.println("                     <tr>");
                pw.println("                        <td align=\"right\" width=\"50%\">");
                pw.println("                            " + selectedColumns.get(i) + " :");
                pw.println("                        </td>");

                pw.println("                        <td align=\"left\">");
                //pw.println("                            <input type=\"text\" name=\number\" id=\"number\" value=\"<%= number%>\">");
                String name = selectedColumns.get(i).toLowerCase();

                if (selectedControls.get(i).equals("Textbox"))
                {
                    pw.println("                            <input type=\"text\" name=\"" + name + "\" id=\"" + name + "\" value=\"<%= " + name + "%>\">");
                }
                else if (selectedControls.get(i).equals("Checkbox"))
                {
                    pw.println("                            <input type=\"checkbox\" name=\"" + name + "\" id=\"" + name + "\" value=\"<%= " + name + "%>\">");
                }
                else if (selectedControls.get(i).equals("Combobox"))
                {
                    pw.println("                            <select id=\"" + name + "\" name=\"" + name + "\">");
                    pw.println("                                <option value=\"\"></option>");
                    pw.println("                            </select>");
                }
                else if (selectedControls.get(i).equals("File"))
                {
                    pw.println("                            <input type=\"file\" name=\"" + name + "\" id=\"" + name + "\" value=\"<%= " + name + "%>\">");
                }
                else if (selectedControls.get(i).equals("Radio"))
                {
                    pw.println("                            <input type=\"radio\" name=\"" + name + "\" id=\"" + name + "\" value=\"<%= " + name + "%>\">");
                }
                pw.println("                        </td>");
                pw.println("                    </tr>");
            }
        }
        pw.println("                    <c:if test=\"${record ne null}\">");
        pw.println("                        <tr>");
        pw.println("                            <td colspan=\"100%\">");
        pw.println("                                <input type=\"hidden\" name=\"" + formBean.getPrimarykey().toLowerCase() + "\" id=\"" + formBean.getPrimarykey().toLowerCase() + "\" value=\"<%=" + formBean.getPrimarykey().toLowerCase() + "%>\"");
        pw.println("                            </td>");
        pw.println("                        </tr>");
        pw.println("                    </c:if>");
        pw.println("                    <c:choose>");
        pw.println("                       <c:when test=\"${record eq null}\">");
        pw.println("                            <tr>");
        pw.println("                                <td align=\"center\" colspan=\"100%\">");
        pw.println("                                    <input type=\"button\" name=\"submit\" id=\"submit\" value=\"Submit\" size=\"40\" onclick=\"javascript: insertRecord();\">");
        pw.println("                                    <input type=\"reset\" name=\"reset\" id=\"reset\" value=\"Reset\">");
        pw.println("                                    <input type=\"button\" name=\"back\" id=\"back\" value=\"Back\" onclick=\"javascript: showMenu();\">");
        pw.println("                                </td>");
        pw.println("                            </tr>");
        pw.println("                       </c:when>");
        pw.println("                       <c:otherwise>");
        pw.println("                            <tr>");
        pw.println("                                <td align=\"center\" colspan=\"100%\">");
        pw.println("                                    <input type=\"button\" name=\"update\" id=\"update\" value=\"Update\" onclick=\"javascript: updateRecord();\">");
        pw.println("                                    <input type=\"reset\" name=\"reset\" id=\"reset\" value=\"Reset\">");
        pw.println("                                    <input type=\"button\" name=\"back\" id=\"back\" value=\"Back\" onclick=\"javascript: showList();\">");
        pw.println("                                </td>");
        pw.println("                            </tr>");
        pw.println("                       </c:otherwise>");
        pw.println("                    </c:choose>");
        pw.println("                </table>");
        pw.println("            </div>");
        pw.println("        </div>");
        pw.println("    </c:when>");
        pw.println("</c:choose>");
        pw.close();
    }
}
