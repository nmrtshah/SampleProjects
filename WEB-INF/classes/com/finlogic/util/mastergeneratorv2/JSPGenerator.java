/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.mastergeneratorv2;

import com.finlogic.apps.finstudio.formbean.MasterGeneratorV2FormBean;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author njuser
 */
public class JSPGenerator
{

    public JSPGenerator(MasterGeneratorV2FormBean formBean) throws Exception
    {
        generateJSP(formBean);
    }

    public void generateJSP(MasterGeneratorV2FormBean formBean) throws Exception
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
        pw.println("<%@taglib uri=\"http://java.sun.com/jsp/jstl/core\" prefix=\"c\" %>");
        pw.println("<%@taglib uri=\"http://java.sun.com/jsp/jstl/functions\" prefix=\"fn\" %>");

        pw.println("<c:choose>");
        pw.println("    <c:when test=\"${process eq 'childExists'}\">");
        pw.println("        ${undeletedid}");
        pw.println("    </c:when>");
        pw.println("    <c:when test=\"${process eq 'getMasterGrid'}\">");
        pw.println("        ${jsonmaster}");
        pw.println("    </c:when>");
        pw.println("    <c:when test=\"${process eq 'getDetailGrid'}\">");
        pw.println("        ${jsondetail}");
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


        pw.println("                function checkDel(postdata, formid)");
        pw.println("                {");
        pw.println("                    var childrec = postdata.responseText.toString().trim();");
        pw.println("                    showMasterGrid();");
        pw.println("                    if(childrec.length > 0)");
        pw.println("                    {");
        pw.println("                        return[false, \"Unable To Delete Record(s) For \" + childrec + \" As Child Record Exits\"];");
        pw.println("                    }");
        pw.println("                    else");
        pw.println("                    {");
        pw.println("                        return[true, \"Deleted\"];");
        pw.println("                    }");
        pw.println("                }");

        pw.println("                function showMasterGrid()");
        pw.println("                {");
        pw.println("                    document.getElementById('mastergriddiv').style.display=\"\";");
        pw.println("                    $('#masterTable').GridUnload();");
        pw.println("                    var params=getFormData(document.viewform);");
        pw.println("                    jQuery(\"#masterTable\").jqGrid({");
        pw.println("                        url:'" + formBean.getModuleName() + ".fin?cmdAction=getMasterGrid&'+params,");
        pw.println("                        editurl: '" + formBean.getModuleName() + ".fin?cmdAction=doOperMasterGrid',");
        pw.println("                        datatype: \"json\",");
        pw.print("                            colNames:[");
        ArrayList<String> allColumnsMaster = formBean.getMasterColumns();
        ArrayList<String> selectedColumnsMaster = formBean.getSelectedMasterColumns();
        for (String column : allColumnsMaster)
        {
            pw.print("'" + column.toLowerCase() + "',");
        }
        pw.print("'Detail',");
        pw.println("],");
        pw.println("                              colModel:[");
        for (int i = 0; i < allColumnsMaster.size(); i++)
        {
            int index = -1;
            String name = allColumnsMaster.get(i).toLowerCase();
            for (int j = 0; j < selectedColumnsMaster.size(); j++)
            {
                if (name.equals(selectedColumnsMaster.get(j).toLowerCase()))
                {
                    index = j;
                    break;
                }
            }
            if (index != -1)
            {
                String control = formBean.getSelectedMasterControls().get(index);
                if(control.equals("Textbox"))
                    pw.println("                                {name:'" + name + "',index:'" + name + "', width:150, editable: true, edittype: 'text', editrules: {required: true}},");
                else if(control.equals("Checkbox"))
                    pw.println("                                {name:'" + name + "',index:'" + name + "', width:150, editable: true, edittype: 'checkbox', editoptions:{value:\"Yes:No\"}, editrules: {required: true} },");
                else if(control.equals("Combobox"))
                    pw.println("                                {name:'" + name + "',index:'" + name + "', width:150, editable: true, edittype: 'select', editoptions:{value:\"--Select--:--Select--\"}, editrules: {required: true} },");
                else if(control.equals("File"))
                    pw.println("                                {name:'" + name + "',index:'" + name + "', width:150, editable: true, edittype: 'file', editoptions:{alt:'Alt Text'}, editrules: {required: true} },");
            }
            else
            {
                pw.println("                                {name:'" + name + "',index:'" + name + "', width:150, editable:false},");

            }
        }
        pw.println("                                {name:'Detail',index:'Detail', width:100},");
        pw.println("                            ],");
        pw.println("                            multiselect: true,");
        pw.println("                            loadonce:true,");
        pw.println("                            rowNum: 10,");
        pw.println("                            rowTotal: 2000,");
        pw.println("                            rowList:[10,20,30,2000],");
        pw.println("                            pager: '#masterTablePager',");
        pw.println("                            height: 'auto',");
        pw.println("                            sortname: '"+formBean.getPrimarykeyMaster().toLowerCase()+"',");
        pw.println("                            viewrecords: true,");
        pw.println("                            sortorder: \"desc\",");
        pw.println("                            caption:\"Grid View Master\",");
        pw.println("                            grouping: false,");
        pw.println("                            footerrow: true,");
        pw.println("                            userDataOnFooter: true,");
        pw.println("                            gridComplete:function() {");
        pw.println("                                var ids = jQuery(\"#masterTable\").jqGrid(\"getDataIDs\");");
        pw.println("                                for(var i=0; i<ids.length; i++)");
        pw.println("                                {");
        pw.println("                                    var cl = ids[i];");
        pw.println("                                    var column0 = $('#masterTable').getCell(cl, '"+formBean.getPrimarykeyMaster().toLowerCase()+"');");
        pw.println("                                    var vcontrol0=\"<a href='#' onclick=\\\"javascript:showDetailGrid('\"+column0+\"');\\\"> <font color='BLUE'>Detail</font></a>\";");
        pw.println("                                    jQuery(\"#masterTable\").jqGrid('setRowData',ids[i],{");
        pw.println("                                        Detail : vcontrol0");
        pw.println("                                    });");
        pw.println("                                }");
        pw.println("                                $(\"option[value=2000]\").text('All');");
        pw.println("                            }");
        pw.println("                        });");
        pw.println("                        jQuery(\"#masterTable\").jqGrid('navGrid','#masterTablePager',{add:true,edit:true,del:true},{},{},{afterSubmit:checkDel});");
        pw.println("                    }");

        pw.println("                    function showDetailGrid(foreignKey)");
        pw.println("                    {");
        pw.println("                        document.getElementById('detailgriddiv').style.display=\"\";");
        pw.println("                        $('#detailTable').GridUnload();");
        pw.println("                        var params=getFormData(document.viewform);");
        pw.println("                        jQuery(\"#detailTable\").jqGrid({");
        pw.println("                            url:'" + formBean.getModuleName() + ".fin?cmdAction=getDetailGrid&'+params+'&foreignKey='+foreignKey,");
        pw.println("                            editurl: '" + formBean.getModuleName() + ".fin?cmdAction=doOperDetailGrid',");
        pw.println("                            datatype: \"json\",");
        pw.print("                            colNames:[");
        ArrayList<String> allColumnsDetail = formBean.getDetailColumns();
        ArrayList<String> selectedColumnsDetail = formBean.getSelectedDetailColumns();
        for (String column : allColumnsDetail)
        {
            pw.print("'" + column.toLowerCase() + "',");
        }
        pw.println("],");
        pw.println("                              colModel:[");
        for (int i = 0; i < allColumnsDetail.size(); i++)
        {
            int index = -1;
            String name = allColumnsDetail.get(i).toLowerCase();
            for (int j = 0; j < selectedColumnsDetail.size(); j++)
            {
                if (name.equals(selectedColumnsDetail.get(j).toLowerCase()))
                {
                    index = j;
                    break;
                }
            }
            if (index != -1)
            {
                String control = formBean.getSelectedDetailControls().get(index);
                if(control.equals("Textbox"))
                    pw.println("                                {name:'" + name + "',index:'" + name + "', width:150, editable: true, edittype: 'text', editrules: {required: true}},");
                else if(control.equals("Checkbox"))
                    pw.println("                                {name:'" + name + "',index:'" + name + "', width:150, editable: true, edittype: 'checkbox', editoptions:{value:\"Yes:No\"}, editrules: {required: true} },");
                else if(control.equals("Combobox"))
                    pw.println("                                {name:'" + name + "',index:'" + name + "', width:150, editable: true, edittype: 'select', editoptions:{value:\"--Select--:--Select--\"}, editrules: {required: true} },");
                else if(control.equals("File"))
                    pw.println("                                {name:'" + name + "',index:'" + name + "', width:150, editable: true, edittype: 'file', editoptions:{alt:'Alt Text'}, editrules: {required: true} },");
            }
            else
            {
                pw.println("                                {name:'" + name + "',index:'" + name + "', width:150, editable:false},");

            }
        }
        pw.println("                            ],");
        pw.println("                            multiselect: true,");
        pw.println("                            loadonce: true,");
        pw.println("                            rowNum: 10,");
        pw.println("                            rowTotal: 2000,");
        pw.println("                            rowList:[10,20,30,2000],");
        pw.println("                            pager: '#detailTablePager',");
        pw.println("                            height: 'auto',");
        pw.println("                            sortname: '"+formBean.getPrimarykeyDetail().toLowerCase()+"',");
        pw.println("                            viewrecords: true,");
        pw.println("                            sortorder: \"desc\",");
        pw.println("                            caption:\"Grid View Detail\",");
        pw.println("                            grouping: false,");
        pw.println("                            footerrow: true,");
        pw.println("                            userDataOnFooter: true,");
        pw.println("                            gridComplete:function()");
        pw.println("                            {");
        pw.println("                                $(\"option[value=2000]\").text('All');");
        pw.println("                            }");
        pw.println("                        });");
        pw.println("                        jQuery(\"#detailTable\").jqGrid('navGrid','#detailTablePager',{add:true,edit:true,del:true});");
        pw.println("                    }");

        pw.println("                    function showMenu()");
        pw.println("                    {");
        pw.println("                        document.getElementById(\"viewdiv\").style.display=\"\";");
        pw.println("                        document.getElementById(\"detailgriddiv\").style.display=\"none\";");
        pw.println("                        document.getElementById(\"mastergriddiv\").style.display=\"none\";");
        pw.println("                    }");
        pw.println("                    function showMasterGridDiv()");
        pw.println("                    {");
        pw.println("                        document.getElementById(\"detailgriddiv\").style.display=\"none\";");
        pw.println("                    }");

        pw.println("                </script>");
        pw.println("            </head>");
        pw.println("            <body>");
        pw.println("                <form name=\"viewform\" id=\"viewform\" action=\"\" method=\"post\">");
        pw.println("                    <div id=\"viewdiv\">");
        pw.println("                        <table border=\"0\"align=\"center\" class=\"tbl_border1\" id=\"viewtable\" width=\"40%\">");
        pw.println("                            <tr class=\"tbl_h1_bg\">");
        pw.println("                                <th colspan=\"100%\">Master</th>");
        pw.println("                            </tr>");
        pw.println("                            <tr>");
        pw.println("                                <td colspan=\"100%\" align=\"center\">");
        pw.println("                                    <input type=\"button\" name=\"btnsubmit\" value=\"View\" onclick=\"javascript: showMasterGrid();\" />");
        pw.println("                                </td>");
        pw.println("                            </tr>");
        pw.println("                        </table>");
        pw.println("                    </div>");
        pw.println("                    <br>");
        pw.println("                    <div id=\"mastergriddiv\" align=\"center\" style=\"display: none\">");
        pw.println("                        <table id=\"masterTable\" align=\"center\">");
        pw.println("                        </table>");
        pw.println("                        <div id=\"masterTablePager\">");
        pw.println("                        </div>");
        pw.println("                        <input type=\"button\" name=\"back\" id=\"back\" value=\"Back\" onclick=\"javascript:showMenu();\">");
        pw.println("                    </div>");
        pw.println("                    <div id=\"detailgriddiv\" align=\"center\" style=\"display: none\">");
        pw.println("                        <br>");
        pw.println("                        <table id=\"detailTable\" align=\"center\">");
        pw.println("                        </table>");
        pw.println("                        <div id=\"detailTablePager\">");
        pw.println("                        </div>");
        pw.println("                        <input type=\"button\" name=\"back\" id=\"back\" value=\"Back\" onclick=\"javascript:showMasterGridDiv();\">");
        pw.println("                    </div>");
        pw.println("                </form>");
        pw.println("            </body>");
        pw.println("        </html>");
        pw.println("    </c:when>");
        pw.println("</c:choose>");
        pw.close();
    }
}
