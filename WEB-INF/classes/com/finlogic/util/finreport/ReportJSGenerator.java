/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.finreport;

import com.finlogic.business.finstudio.finreport.FinReportDataManager;
import com.finlogic.business.finstudio.finreport.FinReportDetailEntityBean;
import com.finlogic.business.finstudio.finreport.FinReportSummaryEntityBean;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;

/**
 *
 * @author njuser
 */
public class ReportJSGenerator
{

    public void generateReportJS(final FinReportDetailEntityBean dEntityBean, final FinReportSummaryEntityBean sEntityBean) throws FileNotFoundException, ClassNotFoundException, SQLException
    {
        String projectName = dEntityBean.getProjectName();
        String moduleName = dEntityBean.getModuleName().toLowerCase(Locale.getDefault());
        String tomcatPath = finpack.FinPack.getProperty("tomcat1_path");
        String number = dEntityBean.getSRNo() + "RGV2";
        String filePath = tomcatPath + "/webapps/finstudio/generated/" + number + "/";
        String projectPath = filePath + projectName.toLowerCase(Locale.getDefault()) + "/js/";

        File file = new File(projectPath);
        file.mkdirs();
        PrintWriter pw = new PrintWriter(projectPath + moduleName + ".js");

        //for validation
        String validation[];
        String label[];
        String control[];

//        if (dEntityBean.isHeaderReq())
//        {
//            pw.println("function padlength(what)");
//            pw.println("{");
//            pw.println("    var output = (what.toString().length == 1)? \"0\"+what : what;");
//            pw.println("    return output;");
//            pw.println("}");
//            pw.println();
//            pw.println("function displaytime()");
//            pw.println("{");
//            pw.println("    serverdate.setSeconds(serverdate.getSeconds()+1);");
//            pw.println("    var datestring = padlength(serverdate.getDate()) + \"/\" + (serverdate.getMonth()+1) + \"/\" + serverdate.getFullYear();");
//            pw.println("    var timestring = padlength(serverdate.getHours()) + \":\" + padlength(serverdate.getMinutes()) + \":\" + padlength(serverdate.getSeconds());");
//            pw.println("    document.getElementById(\"servertime\").innerHTML = datestring+\" \" + timestring;");
//            pw.println("}");
//            pw.println();
//        }        

        boolean isComboPresent = false;

        if (dEntityBean.isReportType() && dEntityBean.getRptControl() != null)
        {
            for (int i = 0; i < dEntityBean.getRptControl().length; i++)
            {
                if (dEntityBean.getRptControl()[i].equals("ComboBox") || dEntityBean.getRptControl()[i].equals("TextLikeCombo"))
                {
                    isComboPresent = true;
                    break;
                }
            }
        }
        if (!isComboPresent && dEntityBean.isFilter() && dEntityBean.getFltrControl() != null)
        {
            for (int i = 0; i < dEntityBean.getFltrControl().length; i++)
            {
                if (dEntityBean.getFltrControl()[i].equals("ComboBox") || dEntityBean.getFltrControl()[i].equals("TextLikeCombo"))
                {
                    isComboPresent = true;
                    break;
                }
            }
        }

        if (isComboPresent)
        {
            pw.println("window.onload = function()");
            pw.println("{");
            //        if (dEntityBean.isHeaderReq())
            //        {
            //            pw.println("    setInterval(\"displaytime()\", 1000);");
            //        }
            pw.println("    loadAllCombo();");
            pw.println("}");
            pw.println();

            pw.println("function loadAllCombo()");
            pw.println("{");
            if (dEntityBean.isReportType() && dEntityBean.getRptControl() != null)
            {
                for (int i = 0; i < dEntityBean.getRptControl().length; i++)
                {
                    if (dEntityBean.getRptControl()[i].equals("ComboBox"))
                    {
                        pw.println("    loadComboNew(\"" + dEntityBean.getRptTxtId()[i] + "\",\"\",\"\",\"" + moduleName + "Form\");");
                    }
                    else if (dEntityBean.getRptControl()[i].equals("TextLikeCombo"))
                    {
                        pw.println("    loadComboNew(\"" + dEntityBean.getRptTxtId()[i] + "\",\"\",\"filterClient\",\"" + moduleName + "Form\")");
                    }
                }
            }
            if (dEntityBean.isFilter() && dEntityBean.getFltrControl() != null)
            {
                for (int i = 0; i < dEntityBean.getFltrControl().length; i++)
                {
                    if (dEntityBean.getFltrControl()[i].equals("ComboBox"))
                    {
                        pw.println("    loadComboNew(\"" + dEntityBean.getFltrTxtId()[i] + "\",\"\",\"\",\"" + moduleName + "Form\");");
                    }
                    else if (dEntityBean.getFltrControl()[i].equals("TextLikeCombo"))
                    {
                        pw.println("    loadComboNew(\"" + dEntityBean.getFltrTxtId()[i] + "\",\"\",\"filterClient\",\"" + moduleName + "Form\");");
                    }
                }
            }
            pw.println("}");
            pw.println();
        }
        //for pagefooter config
        if ((dEntityBean.isPageFooter() || dEntityBean.isGrandTotal()) && dEntityBean.getPageFooterColumn() != null)
        {
            setPageFooter(pw, dEntityBean.getPageFooterColumn(), dEntityBean.getAllColumns(), dEntityBean.getAllColumnTypes(), dEntityBean.getSelectedColumns().length, "detailColConfig");
        }
        if (sEntityBean != null && ((sEntityBean.isPageFooter() || sEntityBean.isGrandTotal()) && sEntityBean.getPageFooterColumn() != null))
        {
            setPageFooter(pw, sEntityBean.getPageFooterColumn(), sEntityBean.getAllColumns(), sEntityBean.getAllColumnTypes(), sEntityBean.getSelectedColumns().length, "summaryColConfig");
        }

        pw.println("function showReport(rptfor)");
        pw.println("{");
        pw.println("    if (!validations())");
        pw.println("    {");
        pw.println("        return;");
        pw.println("    }");
        //pw.println("    if (rptfor == 'View')");
        //pw.println("    {");

        if (sEntityBean != null && !sEntityBean.getDetailRefNo().equals(""))
        {
            pw.println("        if(document.getElementById(\"rdoDetailReport\").checked)");
            pw.println("        {");
            pw.println("            showReportData(\"Detail\");");
            pw.println("        }");
            pw.println("        else");
            pw.println("        {");
            pw.println("            showReportData(\"Summary\");");
            pw.println("        }");
        }
        else
        {
            pw.println("    showReportData(\"Detail\");");
        }
        //pw.println("    }");
        pw.println("    if (rptfor === 'Print')");
        pw.println("    {");
        pw.println("        document.getElementById('trn_print').style.display = \"none\";");
        pw.println("        jQuery(\"#rptgrid\").setGridParam({rowNum:10000}).trigger(\"reloadGrid\");");
        pw.println("        if (confirm(\"Please confirm that you want to print\"))");
        pw.println("            printReport(\"report_display\",\"" + dEntityBean.getReportTitle() + "\");");
        pw.println("    }");
        pw.println("    hide_menu('show_hide','hide_menu', 'nav_show','nav_hide');");
        pw.println("}");
        pw.println();

        pw.println("function showReportData(reportType)");
        pw.println("{");
        pw.println("    if (document.getElementById(\"rdoOnScreen\").checked)          /*~~~~~~~ ON SCREEN ~~~~~~~~*/");
        pw.println("    {");
        pw.println("        document.getElementById(\"apply\").type = \"button\";");
        pw.println("        document.getElementById('trn_print').style.display = \"\";");
        if (dEntityBean.isPieChart() || dEntityBean.isBarChart() || dEntityBean.isSymbolLineChart() || dEntityBean.isThreedLineChart())
        {
            pw.println("        if (document.getElementById(\"chartImage\") !== null)");
            pw.println("        {");
            pw.println("            document.getElementById(\"chartImage\").innerHTML = \"\";");
            pw.println("        }");
        }
        if (sEntityBean != null && !sEntityBean.getDetailRefNo().equals(""))
        {
            pw.println("        if(reportType === \"Detail\")");
            pw.println("        {");
        }
        if (dEntityBean.isPieChart())
        {
            pw.println("            if (document.getElementById(\"pieChart\").checked)");
            pw.println("            {");
            pw.println("                getSynchronousData('" + moduleName + ".fin?" + dEntityBean.getMethodName() + "=get'+ reportType +'PieChart',\"param\",\"chartImage\");");
            pw.println("            }");
        }
        if (dEntityBean.isBarChart())
        {
            pw.println("            if (document.getElementById(\"barChart\").checked)");
            pw.println("            {");
            pw.println("                getSynchronousData('" + moduleName + ".fin?" + dEntityBean.getMethodName() + "=get'+ reportType +'BarChart',\"param\",\"chartImage\");");
            pw.println("            }");
        }
        if (dEntityBean.isSymbolLineChart())
        {
            pw.println("            if (document.getElementById(\"lineChart\").checked)");
            pw.println("            {");
            pw.println("                getSynchronousData('" + moduleName + ".fin?" + dEntityBean.getMethodName() + "=get'+ reportType +'LineChart',\"param\",\"chartImage\");");
            pw.println("            }");
        }
        if (dEntityBean.isThreedLineChart())
        {
            pw.println("            if (document.getElementById(\"areaChart\").checked)");
            pw.println("            {");
            pw.println("                getSynchronousData('" + moduleName + ".fin?" + dEntityBean.getMethodName() + "=get'+ reportType +'AreaChart',\"param\",\"chartImage\");");
            pw.println("            }");
        }
        pw.println("            showDetailReportGrid();");
        if (sEntityBean != null && !sEntityBean.getDetailRefNo().equals(""))
        {
            pw.println("        }");
            pw.println("        else if(reportType === \"Summary\")");
            pw.println("        {");
            pw.println("            showSummaryReportGrid();");
            pw.println("        }");
        }
        if (dEntityBean.isColumns())
        {
            pw.println("            reArrangeColumn('rptgrid');");
        }
        pw.println("    }");
        if (dEntityBean.isPdf() || dEntityBean.isExcel() || dEntityBean.isHtml())
        {
            pw.println("    else                                                     /*~~~~~~~ PDF OR EXCEL ~~~~~~~~*/");
            pw.println("    {");
            pw.println("        if (checkRptName())");
            pw.println("        {");
            {
                if (dEntityBean.isPdf() && dEntityBean.isExcel())
                {
                    pw.println("            if (document.getElementById(\"rdoPdf\").checked || document.getElementById(\"rdoXLS\").checked)");
                }
                else if (dEntityBean.isExcel())
                {
                    pw.println("            if (document.getElementById(\"rdoXLS\").checked)");
                }
                else if (dEntityBean.isPdf())
                {
                    pw.println("            if (document.getElementById(\"rdoPdf\").checked)");
                }
                pw.println("            {");
                pw.println("                document.getElementById(\"apply\").type = \"submit\";");
                pw.println("                document." + moduleName + "Form.action = '" + moduleName + ".fin?" + dEntityBean.getMethodName() + "=getReportExportFile';");
                pw.println("                document." + moduleName + "Form.submit();");
                pw.println("            }");
            }
            if (dEntityBean.isHtml())
            {
                pw.println("            if (document.getElementById(\"rdoHtml\").checked)");
                pw.println("            {");
                pw.println("                var param = getFormData(document." + moduleName + "Form);");
                pw.println("                document.getElementById(\"apply\").type = \"button\";");
                pw.println("                getSynchronousData('" + moduleName + ".fin?" + dEntityBean.getMethodName() + "=getReportExportFile',param,\"html_out\");");
                pw.println("                var fileName = document.getElementById(\"html_out\").innerHTML;");
                pw.println("                if (fileName.match(\"html\") === \"html\")");
                pw.println("                {");
                pw.println("                    window.open(fileName,\"mywindow\");");
                pw.println("                    document.getElementById(\"html_out\").innerHTML = \"\";");
                pw.println("                 }");
                pw.println("             }");
            }
            pw.println("            }");
            pw.println("        }");
        }
        pw.println("}");
        if (dEntityBean.isColumns() && sEntityBean != null)
        {
            pw.println("function getColumns()");
            pw.println("{");
            pw.println("    document.getElementById(\"col_cmb2\").innerHTML = \"\";");
            pw.println("    var combo = document.getElementById(\"col_cmb1\");");
            pw.println("    combo.innerHTML = \"\";");
            pw.println("    if(document.getElementById(\"rdoDetailReport\").checked)");
            pw.println("    {");
            for (int i = 0; i < dEntityBean.getSelectedColumns().length; i++)
            {
                pw.println("        combo.innerHTML += '<option value=\"" + i + "\">" + dEntityBean.getSelectedColumns()[i] + "</option>';");
            }
            pw.println("    }");

            pw.println("    else");
            pw.println("    {");
            for (int i = 0; i < sEntityBean.getSelectedColumns().length; i++)
            {
                pw.println("        combo.innerHTML += '<option value=\"" + i + "\">" + sEntityBean.getSelectedColumns()[i] + "</option>';");
            }
            pw.println("    }");
            pw.println("}");
        }
        //for getting max length              
        List wid = new ArrayList();

        wid = getColumnWidth(dEntityBean.getSelectedColumns(), dEntityBean.getConType(), dEntityBean.getDevServer(), dEntityBean.getAlias(), dEntityBean.getQuery());

        getReportGrid(pw, moduleName, dEntityBean.getSelectedColumns(), dEntityBean.getAllColumns(), dEntityBean.getAllColumnTypes(), dEntityBean.isAddControl(), dEntityBean.getCol(), dEntityBean.getControl(),
                dEntityBean.isPageFooter(), dEntityBean.isGrandTotal(), dEntityBean.getPageFooterColumn(), dEntityBean.isGrouping(), dEntityBean.isGroupFooter(), dEntityBean.getGroupFtrCal(), dEntityBean.getGroupFtrCol(),
                dEntityBean.getMethodName(), dEntityBean.getReportTitle(), dEntityBean.getPrimaryKey(), dEntityBean.getGroupField(), wid, "showDetailReportGrid", "detailColConfig");

        if (sEntityBean != null && !sEntityBean.getDetailRefNo().equals(""))
        {
            //for getting max length
            wid = getColumnWidth(sEntityBean.getSelectedColumns(), sEntityBean.getConType(), sEntityBean.getDevServer(), sEntityBean.getAliasName(), sEntityBean.getAllQuery());

            getReportGrid(pw, moduleName, sEntityBean.getSelectedColumns(), sEntityBean.getAllColumns(), sEntityBean.getAllColumnTypes(), sEntityBean.isAddControl(), sEntityBean.getGridColumn(), sEntityBean.getColumnControl(),
                    sEntityBean.isPageFooter(), sEntityBean.isGrandTotal(), sEntityBean.getPageFooterColumn(), sEntityBean.isGrouping(), sEntityBean.isGroupFooter(), sEntityBean.getGroupFtrCal(), sEntityBean.getGroupFtrCol(),
                    sEntityBean.getMethodName(), sEntityBean.getReportTitle(), sEntityBean.getPrimaryKey(), sEntityBean.getGroupField(), wid, "showSummaryReportGrid", "summaryColConfig");
        }

        if (dEntityBean.isPdf() || dEntityBean.isExcel() || dEntityBean.isHtml())
        {
            pw.println("function checkRptName()");
            pw.println("{");
            //pw.println("    document.getElementById(\"reportName\").value = document.getElementById(\"reportName\").value.replace(/\\s/g,'');");
            pw.println("    if (document.getElementById(\"reportName\").value === \"\")");
            pw.println("    {");
            pw.println("        alert(\"Please Enter Report Name\");");
            pw.println("        document.getElementById(\"reportName\").focus();");
            pw.println("        return false;");
            pw.println("    }");
            pw.println("    return true;");
            pw.println("}");
        }
        pw.println();
        pw.println("function isSessionExpired(responseText)");
        pw.println("{");
        pw.println("    if (responseText.indexOf(\"ACCESS DENIED\",0) > 0)");
        pw.println("    {");
        pw.println("        return true;");
        pw.println("    }");
        pw.println("    return false;");
        pw.println("}");
        pw.println();
        pw.println("function validations() ");
        pw.println("{");
        if (dEntityBean.isReportType())
        {
            pw.println("        displayReportMenu('report_type_out');");
            validation = dEntityBean.getRptValidation();
            label = dEntityBean.getRptLabel();
            control = dEntityBean.getRptTxtId();
            Validation(pw, moduleName + "Form", validation, control, label, dEntityBean.getRptTxtTabIndex(), dEntityBean.getRptMandatory());
        }
        if (dEntityBean.isFilter())
        {
            pw.println("        displayReportMenu('report_filter_out');");
            validation = dEntityBean.getFltrValidation();
            label = dEntityBean.getFltrLabel();
            control = dEntityBean.getFltrTxtId();
            Validation(pw, moduleName + "Form", validation, control, label, dEntityBean.getFltrTxtTabIndex(), dEntityBean.getFltrMandatory());
        }
        pw.println("        return true;");
        pw.println("}");
        pw.println();
        pw.println();
        pw.close();
    }

    public List getColumnWidth(String[] tabColumns, String conType, String devServer, String[] alias, String[] query) throws ClassNotFoundException, SQLException
    {
        FinReportDataManager dmgr = new FinReportDataManager();
        DirectConnection conn = new DirectConnection();
        Map<String, Integer> colsWithLen = new HashMap<String, Integer>();

        int queryLen = query.length;
        int tabColumnLen = tabColumns.length;
        for (int i = 0; i < queryLen; i++)
        {
            String current_query = query[i].trim();
            String where_query = current_query;
            if (where_query.endsWith(";"))
            {
                where_query = where_query.substring(0, where_query.length() - 1).trim();
            }
            where_query = "SELECT * FROM (" + where_query + ") X WHERE 1 = 2";
            Connection con = null;
            SqlRowSet srs;
            try
            {
                if ("usingAlias".equals(conType))
                {
                    srs = dmgr.getReportData(alias[i], where_query, conType, null);
                }
                else
                {
                    con = conn.getConnection(devServer);
                    srs = dmgr.getReportData(alias[i], where_query, conType, con);
                }
                SqlRowSetMetaData rsmd = srs.getMetaData();
                StringBuilder lengthQuery = new StringBuilder("SELECT ");
                for (int j = 0; j < rsmd.getColumnCount(); j++)
                {
                    lengthQuery.append("MAX(LENGTH(\"").append(rsmd.getColumnName(j + 1)).append("\")),");
                }
                lengthQuery.deleteCharAt(lengthQuery.length() - 1);
                lengthQuery.append(" FROM (");
                lengthQuery.append(current_query);
                lengthQuery.append(") E");
                if ("usingAlias".equals(conType))
                {
                    srs = dmgr.getReportData(alias[i], lengthQuery.toString(), conType, null);
                }
                else
                {
                    con = conn.getConnection(devServer);
                    srs = dmgr.getReportData(alias[i], lengthQuery.toString(), conType, con);
                }
                srs.next();
                for (int j = 0; j < rsmd.getColumnCount(); j++)
                {
                    colsWithLen.put(rsmd.getColumnName(j + 1), srs.getInt(j + 1));
                }
            }
            finally
            {
                if (con != null)
                {
                    con.close();
                }
            }
        }
        List wid = new ArrayList();
        int total = 0;
        for (int i = 0; i < tabColumnLen; i++)
        {
            if (colsWithLen.get(tabColumns[i]) == 0 || colsWithLen.get(tabColumns[i]) > tabColumns[i].length())
            {
                //wid.add(1);
                wid.add(tabColumns[i].length());
            }
            else
            {
                wid.add(colsWithLen.get(tabColumns[i]));
            }
            total += Integer.parseInt(wid.get(i).toString());
        }
        int cnt = 0;
        for (int i = 0; i < tabColumns.length - 1; i++)
        {
            int temp = (int) (Integer.parseInt(wid.get(i).toString()) * 1024 / total);
            wid.set(i, temp);
            cnt += Integer.parseInt(wid.get(i).toString());
        }
        wid.set(tabColumns.length - 1, 1024 - cnt);
        return wid;
    }

    public void setPageFooter(PrintWriter pw, String[] pageFooterColumn, String[] allColumns, String[] allColumnTypes, int selectedColumnLen, String pgftrConfigName)
    {
        List pageFooterColType = new ArrayList();
        int idx = 0;
        //getting footer column types
        for (int i = 0; i < pageFooterColumn.length; i++)
        {
            for (int j = 0; j < allColumns.length; j++)
            {
                if (allColumns[j].equals(pageFooterColumn[i]))
                {
                    pageFooterColType.add(allColumnTypes[j]);
                }
            }
        }
            
        if (pageFooterColType.contains("Integer") || pageFooterColType.contains("Float") || pageFooterColType.contains("Double") || pageFooterColType.contains("Long") || pageFooterColType.contains("Number") || pageFooterColType.contains("BigDecimal"))
        {
            //if pagefooter or grandtotal
            pw.println("var " + pgftrConfigName + "={");
            pw.println("        colModel:[");
            for (int i = 0; i < pageFooterColumn.length; i++)
            {
                for (int j = 0; j < allColumns.length; j++)
                {
                    idx = 0;
                    if (pageFooterColumn[i].equals(allColumns[j]))
                    {
                        idx = j;
                        break;
                    }
                }
                if ("Integer".equals(pageFooterColType.get(i)) || "Float".equals(pageFooterColType.get(i)) || "Double".equals(pageFooterColType.get(i)) || "Long".equals(pageFooterColType.get(i)) || "Number".equals(pageFooterColType.get(i)) || "BigDecimal".equals(pageFooterColType.get(i)) )
                {
                    pw.println("            {colName:\"" + pageFooterColumn[i] + "\",colIndex:" + idx + ",fixedPrecision:2},");
                }
            }
            pw.println("        ],");
            pw.println("      gridId:'rptgrid',");
            pw.println("      totalGridCol:" + selectedColumnLen + ",");
            pw.println("      storeSummary:0");
            pw.println("};");
        }
    }

    public StringBuilder setColumnHeader(String[] selectedColumns)
    {
        String[] colNames = new String[selectedColumns.length];
        System.arraycopy(selectedColumns, 0, colNames, 0, selectedColumns.length);
        StringBuilder colnm = new StringBuilder();
        for (int i = 0; i < colNames.length; i++)
        {
            colNames[i] = Character.toUpperCase(colNames[i].charAt(0)) + colNames[i].substring(1).toLowerCase(Locale.getDefault());
            if (colNames[i].contains("_"))
            {
                colNames[i] = colNames[i].replace("_", " ");
                StringBuilder tmpstr;
                tmpstr = new StringBuilder(colNames[i].toLowerCase(Locale.getDefault()));
                int j = 0;
                do
                {
                    tmpstr.replace(j, j + 1, tmpstr.substring(j, j + 1).toUpperCase(Locale.getDefault()));
                    j = tmpstr.indexOf(" ", j) + 1;
                }
                while (j > 0 && j < tmpstr.length());
                colNames[i] = tmpstr.toString();
            }
            colnm.append("\"");
            colnm.append(colNames[i]);
            colnm.append("\",");
        }
        colnm = colnm.deleteCharAt(colnm.length() - 1);
        return colnm;
    }

    public void getReportGrid(PrintWriter pw, String moduleName, String[] selectedColumns, String[] allColumns, String[] allDataTypes, boolean addControl, String[] gridColumn, String[] gridControl,
            boolean pageFooter, boolean grandTotal, String[] pageFooterColumn, boolean grouping, boolean groupFooter, String[] groupFooterCalc, String[] groupFooterCol,
            String methodName, String reportTitle, String primaryKey, String groupField, List colWidth, String funcName, String colConfigNM)
    {
        List<String> tabColumnType = new ArrayList<String>();
        int idx = 0;
        List pageFooterColType = new ArrayList();
        //getting footer column types
        if (pageFooterColumn != null)
        {
            for (int i = 0; i < pageFooterColumn.length; i++)
            {
                for (int j = 0; j < allColumns.length; j++)
                {
                    if (allColumns[j].equals(pageFooterColumn[i]))
                    {
                        pageFooterColType.add(allDataTypes[j]);
                    }
                }
            }
        }

        pw.println("function " + funcName + "()");
        pw.println("{");
        if (pageFooter || grandTotal && (pageFooterColType.contains("Integer") || pageFooterColType.contains("Float") || pageFooterColType.contains("Double") || pageFooterColType.contains("Long") || pageFooterColType.contains("Number")))
        {
            pw.println(colConfigNM + ".storeSummary = 0;");
        }
        pw.println("    $('#rptgrid').GridUnload();");
        pw.println("    $('#errorBox').hide();");
        pw.println("    var param = getFormData(document." + moduleName + "Form);");
        pw.println("    $(\"#rptgrid\").jqGrid({");
        pw.println("    url:'" + moduleName + ".fin?" + methodName + "=getReportGrid&'+param,");
        pw.println("    datatype: \"json\",");

        for (int i = 0; i < selectedColumns.length; i++)
        {
            for (int j = 0; j < allColumns.length; j++)
            {
                if (allColumns[j].equals(selectedColumns[i]))
                {
                    tabColumnType.add(allDataTypes[j]);
                    break;
                }
            }
        }
        pw.println("    colNames:[" + setColumnHeader(selectedColumns).toString() + "],");
        pw.println("    colModel:[");
        String[] cal =
        {
        };
        if (groupFooterCalc != null)
        {
            cal = new String[groupFooterCalc.length];
            for (int i = 0; i < groupFooterCalc.length; i++)
            {
                if (groupFooterCalc[i].equals("lowest"))
                {
                    cal[i] = "min";
                }
                if (groupFooterCalc[i].equals("highest"))
                {
                    cal[i] = "max";
                }
                if (groupFooterCalc[i].equals("sum"))
                {
                    cal[i] = "sum";
                }
                if (groupFooterCalc[i].equals("count"))
                {
                    cal[i] = "count";
                }
                if (groupFooterCalc[i].equals("average"))
                {
                    cal[i] = "avg";
                }
            }
        }
        for (int k = 0; k < selectedColumns.length; k++)
        {
            //for position in json map
            int len;
            len = allColumns.length;
            int[] jsonId;
            jsonId = new int[len];
            for (int i = 0; i < len; i++)
            {
                jsonId[i] = i;
            }
            for (int i = 0; i < len; i++)
            {
                for (int j = i + 1; j < len; j++)
                {
                    if (allColumns[i].equals(allColumns[j]))
                    {
                        jsonId[j] = jsonId[i];
                        for (int x = j; x < len; x++)
                        {
                            jsonId[x]--;
                        }
                    }
                }
            }
            for (int j = 0; j < allColumns.length; j++)
            {
                idx = 0;
                if (allColumns[j].equals(selectedColumns[k]))
                {
                    idx = j;
                    break;
                }
            }
            StringBuilder colModel;
            colModel = new StringBuilder();
            colModel.append("           {").append("name:'");
            colModel.append(selectedColumns[k]);
            colModel.append("',index:'");
            colModel.append(selectedColumns[k]);
            colModel.append("',width:'");
            colModel.append(colWidth.get(k));
            colModel.append("',jsonmap:\"cell.");
            colModel.append(jsonId[idx]);
            colModel.append("\"");
            //if add control
            if (addControl && gridColumn != null)
            {
                for (int j = 0; j < gridColumn.length; j++)
                {
                    if (gridColumn[j].equals(selectedColumns[k]))
                    {
                        if (gridControl[j].equals("link"))
                        {
                            colModel.append(",formatter:function (cellvalue, options, rowObject) {").append("if (rowObject[0] === undefined){").append("if (typeof cellvalue !== \"number\"){return '<div style=\"float:left\"><a href=\"javascript:void(0);\" style=\"color:blue\">'+ cellvalue +'</a></div>';}else{return cellvalue;}}else{return cellvalue;}").append("}");
                        }
                        else if (gridControl[j].equals("select"))
                        {
                            colModel.append(",formatter:function (cellvalue, options, rowObject) {").append("if (rowObject[0] === undefined){").append("if (typeof cellvalue !== \"number\"){return '<div style=\"float:left\"><select><option value='+ cellvalue +'>'+ cellvalue +'</option></select></div>' + cellvalue;}else{return cellvalue;}}else{return cellvalue;}").append("}");
                        }
                        else
                        {
                            colModel.append(",formatter:function (cellvalue, options, rowObject) {").append("if (rowObject[0] === undefined){").append("if (typeof cellvalue !== \"number\"){return '<div style=\"float:left\"><input type=\"");
                            colModel.append(gridControl[j]);
                            colModel.append("\" name=\"selectedCall\" value=\"'+ cellvalue +'\"/></div>' + cellvalue;}else{return cellvalue;}}else{return cellvalue;}").append("}");
                        }
                    }
                }
            }
            //if groupfooter
            if (groupFooter && groupFooterCol != null)
            {
                for (int j = 0; j < groupFooterCol.length; j++)
                {
                    if (groupFooterCol[j].equals(selectedColumns[k]))
                    {
                        colModel.append(",summaryType:'");
                        colModel.append(cal[j]);
                        colModel.append("',summaryTpl : '");
                        colModel.append(cal[j]);
                        colModel.append(":({0})'");
                    }
                }
            }

            if (tabColumnType.get(k).equals("Integer") || tabColumnType.get(k).equals("Float") || tabColumnType.get(k).equals("Double") || tabColumnType.get(k).equals("Long") || tabColumnType.get(k).equals("Number") || tabColumnType.get(k).equals("BigDecimal"))
            {
                pw.println(colModel.append(",sorttype:'int',align:\"right\"").append("},"));
            }
            else if (tabColumnType.get(k).equals("Date"))
            {
                pw.println(colModel.append(",sorttype:'date',align:\"center\"").append("},"));
            }
            else
            {
                pw.println(colModel.append("},"));
            }
        }

        pw.println("    ],");
        pw.println("    jsonReader: { repeatitems : false },");
        pw.println("    rowNum:10,");
        pw.println("    rowTotal:10000,");
        pw.println("    rowList:[10,20,30],");
        pw.println("    rownumbers:true,");
        pw.println("    gridview:true,");
        pw.println("    height:'auto',");
        pw.println("    width: '985',");
        pw.println("    altRows:true,");
        pw.println("    pager: '#pagerrptgrid',");
        pw.println("    sortname: '" + primaryKey + "',");
        pw.println("    viewrecords: true,");
        pw.println("    loadonce: true,");
        pw.println("    sortorder: \"asc\",");
        pw.println("    caption:\"" + reportTitle + "\",");
        pw.println("    userDataOnFooter: true,");
        if (grouping)
        {
            pw.println("    grouping: true,");
            pw.println("    groupingView : {");
            pw.println("         groupField : ['" + groupField + "'],");
            if (groupFooter)
            {
                pw.println("         groupSummary : [true],");
            }
            else
            {
                pw.println("         groupSummary : [false],");
            }
            pw.println("         groupColumnShow : [true],");
            pw.println("         groupText : ['{0}'],");
            pw.println("         groupCollapse : false,");
            pw.println("         groupOrder: ['asc']");
            pw.println("    }");
        }
        else
        {
            pw.println("    footerrow: false,");
            pw.println("    grouping: false");
        }
        if ((pageFooter || grandTotal) && pageFooterColumn != null)
        {
            pw.println("    ,");
            pw.println("    gridComplete : function()");
            pw.println("    {");
            if (pageFooter && (pageFooterColType.contains("Integer") || pageFooterColType.contains("Float") || pageFooterColType.contains("Double") || pageFooterColType.contains("Long") || pageFooterColType.contains("Number") || pageFooterColType.contains("BigDecimal")))
            {
                pw.println("        addPageWiseTotal(" + colConfigNM + ");");
            }
            if (grandTotal && (pageFooterColType.contains("Integer") || pageFooterColType.contains("Float") || pageFooterColType.contains("Double") || pageFooterColType.contains("Long") || pageFooterColType.contains("Number") || pageFooterColType.contains("BigDecimal")))
            {
                pw.println("        addGrandTotal(" + colConfigNM + ");");
            }
            pw.println("    }");
        }
        pw.println("    ,");
        pw.println("    loadComplete : function(data)");
        pw.println("    {");
        if (grandTotal && pageFooterColumn != null && (pageFooterColType.contains("Integer") || pageFooterColType.contains("Float") || pageFooterColType.contains("Double") || pageFooterColType.contains("Long") || pageFooterColType.contains("Number") || pageFooterColType.contains("BigDecimal")))
        {
            pw.println("        calcGrandTotal(" + colConfigNM + ",data);");
        }
        pw.println("        var $this = $(this),");
        pw.println("            datatype = $this.getGridParam('datatype');");
        pw.println("        if (datatype === \"xml\" || datatype === \"json\")");
        pw.println("        {");
        pw.println("            setTimeout(function ()");
        pw.println("            {");
        pw.println("                $this.trigger(\"reloadGrid\");");
        pw.println("            }, 100);");
        pw.println("        }");
        pw.println("    }");
        pw.println("    ,");
        pw.println("    loadError : function(xhr,st,err)");
        pw.println("    {");
        pw.println("        $('#errorBox').show();");
        pw.println("        if (isSessionExpired(xhr.responseText))");
        pw.println("        {");
        pw.println("            $('#errorBox').html(xhr.responseText);");
        pw.println("        }");
        pw.println("        else");
        pw.println("        {");
        pw.println("            $('#errorBox').html(\"<center><b>Under Maintenance. Please Try Later.</b></center>\");");
        pw.println("        }");
        pw.println("        $('#gbox_rptgrid').hide();");
        pw.println("    }");
        pw.println("    }).navGrid('#pagerrptgrid',{");
        pw.println("        edit:false,");
        pw.println("        add:false,");
        pw.println("        del:false,");
        pw.println("        search:true");
        pw.println("    });");
        pw.println("}");
        pw.println();
    }

    public void Validation(final PrintWriter pw, final String form, final String[] validation, final String[] control, final String[] label, final String[] tempTabIndex, final String[] isCompulsory)
    {
        String[] tabIndexes;
        int[] tabIndex;
        tabIndexes = new String[validation.length];
        tabIndex = new int[validation.length];
        for (int i = 0; i < validation.length; i++)
        {
            tabIndexes[i] = tempTabIndex[i];
            tabIndex[i] = i;
        }

        for (int i = 0; i < validation.length; i++)
        {
            for (int j = i; j < validation.length; j++)
            {
                if (Integer.parseInt(tabIndexes[j]) < Integer.parseInt(tabIndexes[i]))
                {
                    String tmpStr;
                    tmpStr = tabIndexes[j];
                    tabIndexes[j] = tabIndexes[i];
                    tabIndexes[i] = tmpStr;
                    int tmpStr1;
                    tmpStr1 = tabIndex[j];
                    tabIndex[j] = tabIndex[i];
                    tabIndex[i] = tmpStr1;
                }
            }
        }
        //for sorting
        int len = tabIndex.length;
        for (int j = 0; j < len; j++)
        {
            String compulsory = "false";
            if ("true".equals(isCompulsory[tabIndex[j]]))
            {
                compulsory = "true";
            }
            if ("Numeric".equals(validation[tabIndex[j]]))
            {
                pw.println("        if (!validate_only_number(document." + form + ",\"" + control[tabIndex[j]] + "\",\"" + label[tabIndex[j]] + "\"," + compulsory + "))");
                pw.println("        {");
                pw.println("            return false;");
                pw.println("        }");
            }
            else if ("Alphabet".equals(validation[tabIndex[j]]))
            {
                pw.println("        if (!validate_ind(document." + form + ",\"" + control[tabIndex[j]] + "\",\"" + label[tabIndex[j]] + "\"," + compulsory + "))");
                pw.println("        {");
                pw.println("            return false;");
                pw.println("        }");
            }
            else if ("Company".equals(validation[tabIndex[j]]))
            {
                pw.println("        if (!validate_comp(document." + form + ",\"" + control[tabIndex[j]] + "\",\"" + label[tabIndex[j]] + "\"," + compulsory + "))");
                pw.println("        {");
                pw.println("            return false;");
                pw.println("        }");
            }
            else if ("Age".equals(validation[tabIndex[j]]))
            {
                pw.println("        if (! validate_age(document." + form + ",\"" + control[tabIndex[j]] + "\",true,\"" + label[tabIndex[j]] + "\"," + compulsory + "))");
                pw.println("        {");
                pw.println("            return false;");
                pw.println("        }");
            }
            else if ("Password".equals(validation[tabIndex[j]]))
            {
                pw.println("        if (!validate_password(document." + form + ",\"" + control[tabIndex[j]] + "\",\"" + label[tabIndex[j]] + "\"," + compulsory + "))");
                pw.println("        {");
                pw.println("            return false;");
                pw.println("        }");
            }
            else if ("PANNo".equals(validation[tabIndex[j]]))
            {
                pw.println("        if (!validate_panno(document." + form + ",\"" + control[tabIndex[j]] + "\",\"" + label[tabIndex[j]] + "\"," + compulsory + "))");
                pw.println("        {");
                pw.println("            return false;");
                pw.println("        }");
            }
            else if ("FolioNo".equals(validation[tabIndex[j]]))
            {
                pw.println("        if (!validate_foliono(document." + form + ",\"" + control[tabIndex[j]] + "\",\"" + label[tabIndex[j]] + "\"," + compulsory + "))");
                pw.println("        {");
                pw.println("            return false;");
                pw.println("        }");
            }
            else if ("FAXNo".equals(validation[tabIndex[j]]) || "mobno".equals(validation[tabIndex[j]]) || "phoneno".equals(validation[tabIndex[j]]))
            {
                pw.println("        if (!validate_phonno(document." + form + ",\"" + control[tabIndex[j]] + "\",\"" + label[tabIndex[j]] + "\"," + compulsory + "))");
                pw.println("        {");
                pw.println("            return false;");
                pw.println("        }");
            }
            else if ("Pincode".equals(validation[tabIndex[j]]))
            {
                pw.println("        if (!validate_pincode(document." + form + ",\"" + control[tabIndex[j]] + "\",\"" + label[tabIndex[j]] + "\",no," + compulsory + "))");
                pw.println("        {");
                pw.println("            return false;");
                pw.println("        }");
            }
            else if ("NAV".equals(validation[tabIndex[j]]))
            {
                pw.println("        if (!validate_nav(document." + form + ",\"" + control[tabIndex[j]] + "\",\"" + label[tabIndex[j]] + "\"," + compulsory + "))");
                pw.println("        {");
                pw.println("            return false;");
                pw.println("        }");
            }
            else if ("ChequeNo".equals(validation[tabIndex[j]]))
            {
                pw.println("        if (!validate_chequeno(document." + form + ",\"" + control[tabIndex[j]] + "\",\"" + label[tabIndex[j]] + "\"," + compulsory + "))");
                pw.println("        {");
                pw.println("            return false;");
                pw.println("        }");
            }
            else if ("Email".equals(validation[tabIndex[j]]))
            {
                pw.println("        if (!validate_email(document." + form + ",\"" + control[tabIndex[j]] + "\",\"" + label[tabIndex[j]] + "\"," + compulsory + "))");
                pw.println("        {");
                pw.println("            return false;");
                pw.println("        }");
            }
            else if ("DateValidation".equals(validation[tabIndex[j]]))
            {
                pw.println("        if (!validateDate_txt(\"" + control[tabIndex[j]] + "\",\"" + label[tabIndex[j]] + "\"," + compulsory + "))");
                pw.println("        {");
                pw.println("            return false;");
                pw.println("        }");
            }
            else if ("LessThanFutureDate".equals(validation[tabIndex[j]]))
            {
                pw.println("        if (!checkFutureDateNotAllow(document." + form + ",\"" + control[tabIndex[j]] + "\",\"" + label[tabIndex[j]] + "\",\"sysdate\",\"\"))");
                pw.println("        {");
                pw.println("            return false;");
                pw.println("        }");
            }
            else if ("GreaterThanFutureDate".equals(validation[tabIndex[j]]))
            {
                pw.println("        if (!checkOnlyFutureDateAllow(document." + form + ",\"" + control[tabIndex[j]] + "\",\"" + label[tabIndex[j]] + "\",\"sysdate\",\"\"))");
                pw.println("        {");
                pw.println("            return false;");
                pw.println("        }");
            }
            else if ("CheckBoxValidation".equals(validation[tabIndex[j]]))
            {
                pw.println("        if (!validate_checkbox(document." + form + ",\"" + control[tabIndex[j]] + "\",\"" + label[tabIndex[j]] + "\"," + compulsory + "))");
                pw.println("        {");
                pw.println("            return false;");
                pw.println("        }");
            }
            else if ("RadioValidation".equals(validation[tabIndex[j]]))
            {
                pw.println("        if (!validate_radio(document." + form + ",\"" + control[tabIndex[j]] + "\",\"" + label[tabIndex[j]] + "\"," + compulsory + "))");
                pw.println("        {");
                pw.println("            return false;");
                pw.println("        }");
            }
            else if ("DropDown".equals(validation[tabIndex[j]]))
            {
                pw.println("        if (!validate_dropdown(document." + form + ",\"" + control[tabIndex[j]] + "\",\"" + label[tabIndex[j]] + "\"," + compulsory + "))");
                pw.println("        {");
                pw.println("            return false;");
                pw.println("        }");
            }
            else if ("SelectLimit".equals(validation[tabIndex[j]]))
            {
                pw.println("        if (!validate_select_limit(document." + form + ",\"" + control[tabIndex[j]] + "\",\"" + label[tabIndex[j]] + "\",20," + compulsory + "))");
                pw.println("        {");
                pw.println("            return false;");
                pw.println("        }");
            }
            else if ("NJbrcode".equals(validation[tabIndex[j]]))
            {
                pw.println("        if (!validate_njbrcode(document." + form + ",\"" + control[tabIndex[j]] + "\",\"" + label[tabIndex[j]] + "\"," + compulsory + "))");
                pw.println("        {");
                pw.println("            return false;");
                pw.println("        }");
            }
        }
    }
}
