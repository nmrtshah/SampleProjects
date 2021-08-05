/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.findhtmlreport;

import com.finlogic.business.finstudio.findhtmlreport.FinDhtmlReportDataManager;
import com.finlogic.business.finstudio.findhtmlreport.FinDhtmlReportDetailEntityBean;
import com.finlogic.business.finstudio.findhtmlreport.FinDhtmlReportSummaryEntityBean;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;

/**
 *
 * @author njuser
 */
public class FinDhtmlReportJSGenerator
{

    public void generateReportJS(final FinDhtmlReportDetailEntityBean dEntityBean, final FinDhtmlReportSummaryEntityBean sEntityBean) throws FileNotFoundException, ClassNotFoundException, SQLException
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
        pw.println("var mygrid;");
        if (isComboPresent)
        {
            pw.println("window.onload = function()");
            pw.println("{");
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

        pw.println("function showReport(rptfor)");
        pw.println("{");
        pw.println("    if (!validations())");
        pw.println("    {");
        pw.println("        return false;");
        pw.println("    }");
        pw.println("        document.getElementById('basicReportDiv').style.display = \"\";");
        if (dEntityBean.isPieChart() || dEntityBean.isBarChart() || dEntityBean.isSymbolLineChart() || dEntityBean.isThreedLineChart())
        {
            pw.println("        if (document.getElementById(\"chartImage\") !== null)");
            pw.println("        {");
            pw.println("            document.getElementById(\"chartImage\").innerHTML = \"\";");
            pw.println("        }");
        }
        if (sEntityBean != null && !sEntityBean.getDetailRefNo().equals(""))
        {
            pw.println("        if(document.getElementById(\"rdoDetailReport\").checked)");
            pw.println("        {");
        }
        if (dEntityBean.isPieChart())
        {
            pw.println("            if (document.getElementById(\"pieChart\").checked)");
            pw.println("            {");
            pw.println("                getSynchronousData('" + moduleName + ".fin?" + dEntityBean.getMethodName() + "=getDetailPieChart',\"param\",\"chartImage\");");
            pw.println("            }");
        }
        if (dEntityBean.isBarChart())
        {
            pw.println("            if (document.getElementById(\"barChart\").checked)");
            pw.println("            {");
            pw.println("                getSynchronousData('" + moduleName + ".fin?" + dEntityBean.getMethodName() + "=getDetailBarChart',\"param\",\"chartImage\");");
            pw.println("            }");
        }
        if (dEntityBean.isSymbolLineChart())
        {
            pw.println("            if (document.getElementById(\"lineChart\").checked)");
            pw.println("            {");
            pw.println("                getSynchronousData('" + moduleName + ".fin?" + dEntityBean.getMethodName() + "=getDetailLineChart',\"param\",\"chartImage\");");
            pw.println("            }");
        }
        if (dEntityBean.isThreedLineChart())
        {
            pw.println("            if (document.getElementById(\"areaChart\").checked)");
            pw.println("            {");
            pw.println("                getSynchronousData('" + moduleName + ".fin?" + dEntityBean.getMethodName() + "=getDetailAreaChart',\"param\",\"chartImage\");");
            pw.println("            }");
        }
        pw.println("                showDetailReportGrid(rptfor);");
        if (sEntityBean != null && !sEntityBean.getDetailRefNo().equals(""))
        {
            pw.println("        }");
            pw.println("        else");
            pw.println("        {");
            pw.println("            showSummaryReportGrid(rptfor);");
            pw.println("        }");
        }
        if (dEntityBean.isColumns())
        {
            pw.println("            reArrangeColumns();");
        }
        pw.println("    hide_menu('show_hide','hide_menu', 'nav_show','nav_hide');");
        pw.println("        return true;");
        pw.println("}");
        pw.println();

        pw.println("function printGridReport()");
        pw.println("{");
        pw.println("        var paramArr = new Array();");
        pw.println("        paramArr[\"footerContentId\"] = \"footer_print\";");
        pw.println("        paramArr[\"headerContentId\"] = \"header_print\";");
        pw.println("        paramArr[\"projectName\"] = \"" + dEntityBean.getProjectName() + "\";");
        pw.println("        paramArr[\"headerImgSrc\"] = \"http://dev.njtechdesk.com/finlibrary/resource/images/logo_njindiainvest.gif\";");
        pw.println("        paramArr[\"footerImgSrc\"] = \"\";");
        pw.println("        paramArr[\"breadcrumId\"] = \"\";");
        pw.println("        paramArr[\"printTitleContent\"] = \"" + dEntityBean.getReportTitle() + "\";");
        pw.println("        mygrid.printView(getHeaderForPrint(paramArr),getFooterForPrint(paramArr));");
        pw.println("    }");

        if (dEntityBean.isColumns() && sEntityBean != null)
        {
            pw.println("function setReportTitle()");
            pw.println("{");
            pw.println("    if(document.getElementById(\"rdoDetailReport\").checked)");
            pw.println("    {");
            pw.println("        document.getElementById(\"rptTitle\").innerHTML = \"" + dEntityBean.getReportTitle() + "\";");
            pw.println("    }");
            pw.println("    else");
            pw.println("    {");
            pw.println("        document.getElementById(\"rptTitle\").innerHTML = \"" + sEntityBean.getReportTitle() + "\";");
            pw.println("    }");
            pw.println("}");

            pw.println("function getColumns()");
            pw.println("{");
            pw.println("    setReportTitle();");
            pw.println("    document.getElementById(\"col_cmb2\").innerHTML = \"\";");
            pw.println("    var combo = document.getElementById(\"col_cmb1\");");
            pw.println("    combo.innerHTML = \"\";");
            pw.println("    if(document.getElementById(\"rdoDetailReport\").checked)");
            pw.println("    {");
            for (int i = 0; i < dEntityBean.getAllColumns().length; i++)
            {
                for (int j = 0; j < dEntityBean.getSelectedColumns().length; j++)
                {
                    if (dEntityBean.getSelectedColumns()[j].equals(dEntityBean.getAllColumns()[i]))
                    {
                        pw.println("        combo.innerHTML += '<option value=\" " + i + "\">" + dEntityBean.getSelectedColumns()[j] + "</option>';");
                        break;
                    }
                }
            }
            pw.println("    }");
            pw.println("    else");
            pw.println("    {");
            for (int i = 0; i < sEntityBean.getAllColumns().length; i++)
            {
                for (int j = 0; j < sEntityBean.getSelectedColumns().length; j++)
                {
                    if (sEntityBean.getSelectedColumns()[j].equals(sEntityBean.getAllColumns()[i]))
                    {
                        pw.println("        combo.innerHTML += '<option value=\"" + i + "\">" + sEntityBean.getSelectedColumns()[j] + "</option>';");
                        break;
                    }
                }
            }
            pw.println("    }");
            pw.println("}");
        }
        pw.println("function hideLoadingImg()");
        pw.println("{");
        pw.println("    document.getElementById(\"loadingImageID\").style.display = \"none\";");
        pw.println("}");

        pw.println("function showLoadingImg()");
        pw.println("{");
        pw.println("    document.getElementById(\"loadingImageID\").style.display = \"\";");
        pw.println("}");

        String depComboArry[] = null;

        // Generate Js code 
        if (dEntityBean.getFltrCmbSource() != null)
        {
            control = dEntityBean.getFltrControl();
            int controlLen = control.length;
            String funcName = null;
            StringBuilder sb = new StringBuilder("");
            List<String> lst = new LinkedList<String>();
            for (int i = 0; i < controlLen; i++)
            {
                if (("ComboBox".equals(control[i]) || "TextLikeCombo".equals(control[i])))
                {
                    if (dEntityBean.getFltrCmbDependent() != null)
                    {
                        depComboArry = dEntityBean.getFltrCmbDependent().split(",");
                        if (!depComboArry[i].equalsIgnoreCase(""))
                        {
                            if (!depComboArry[i].equalsIgnoreCase(dEntityBean.getFltrTxtName()[i]))
                            {
                                ListIterator itr = lst.listIterator();
                                int idx = -1;
                                while (itr.hasNext())
                                {
                                    funcName = itr.next().toString();
                                    if (funcName.toString().equals("function onChange" + depComboArry[i].toLowerCase() + "()"))
                                    {
                                        idx = itr.previousIndex();
                                        break;
                                    }
                                }
                                if (idx != -1)
                                {
                                    sb.setLength(0);
                                    sb.append("  if(document.getElementById(\"" + depComboArry[i].toLowerCase() + "\"))\n");
                                    sb.append("  {\n");
                                    if ("TextLikeCombo".equals(control[i]))
                                    {
                                        sb.append(" document.getElementById(\"input" + dEntityBean.getFltrTxtId()[i] + "\").value=\"\";");
                                    }
                                    sb.append("     getSynchronousData(\"" + moduleName + ".fin?" + dEntityBean.getMethodName() + "=get" + dEntityBean.getFltrTxtId()[i] + "\", param, '" + dEntityBean.getFltrTxtId()[i] + "');\n");
                                    sb.append("  }");
                                    lst.add(idx + 3, sb.toString());
                                }
                                else if (idx == -1)
                                {
                                    lst.add("function onChange" + depComboArry[i].toLowerCase() + "()");
                                    lst.add("{");
                                    lst.add("  var param = getFormData(document." + moduleName + "Form);");
                                    lst.add("  if (document.getElementById(\"" + depComboArry[i].toLowerCase() + "\"))");
                                    lst.add("  {");
                                    if ("TextLikeCombo".equals(control[i]))
                                    {
                                        lst.add(" document.getElementById(\"input" + dEntityBean.getFltrTxtId()[i] + "\").value=\"\";");
                                    }
                                    lst.add("    getSynchronousData(\"" + moduleName + ".fin?" + dEntityBean.getMethodName() + "=get" + dEntityBean.getFltrTxtId()[i] + "\", param, '" + dEntityBean.getFltrTxtId()[i] + "');");
                                    lst.add("   }");
                                    lst.add("}");
                                }
                            }
                            else
                            {
                                break;
                            }
                        }
                    }
                }
            }
            for (int i = 0; i < lst.size(); i++)
            {
                pw.println(lst.get(i));
            }
            sb.setLength(0);
        }


        //for getting max length              
        List wid = new ArrayList();
        StringBuilder newWidth = new StringBuilder();

        wid = getColumnWidth(dEntityBean.getSelectedColumns(), dEntityBean.getConType(), dEntityBean.getDevServer(), dEntityBean.getAlias(), dEntityBean.getQuery());
        newWidth = getHiddenFieldWidth(dEntityBean.getAllColumns(), dEntityBean.getSelectedColumns(), wid);

        getReportGrid(pw, moduleName, dEntityBean.getSelectedColumns(), dEntityBean.getAllColumns(), dEntityBean.getAllColumnTypes(), dEntityBean.isAddControl(), dEntityBean.getCol(), dEntityBean.getControl(),
                dEntityBean.isPageFooter(), dEntityBean.isGrandTotal(), dEntityBean.getPageFooterColumn(), dEntityBean.isGrouping(), dEntityBean.isGroupFooter(), dEntityBean.getGroupFtrCal(), dEntityBean.getGroupFtrCol(),
                dEntityBean.getMethodName(), dEntityBean.getReportTitle(), dEntityBean.getPrimaryKey(), dEntityBean.getGroupField(), newWidth, "showDetailReportGrid", "detailColConfig", dEntityBean.isPdf(), dEntityBean.isExcel());

        if (sEntityBean != null && !sEntityBean.getDetailRefNo().equals(""))
        {
            //for getting max length
            wid = getColumnWidth(sEntityBean.getAllColumns(), sEntityBean.getConType(), sEntityBean.getDevServer(), sEntityBean.getAliasName(), sEntityBean.getAllQuery());
            newWidth = getHiddenFieldWidth(sEntityBean.getAllColumns(), sEntityBean.getSelectedColumns(), wid);

            getReportGrid(pw, moduleName, sEntityBean.getSelectedColumns(), sEntityBean.getAllColumns(), sEntityBean.getAllColumnTypes(), sEntityBean.isAddControl(), sEntityBean.getGridColumn(), sEntityBean.getColumnControl(),
                    sEntityBean.isPageFooter(), sEntityBean.isGrandTotal(), sEntityBean.getPageFooterColumn(), sEntityBean.isGrouping(), sEntityBean.isGroupFooter(), sEntityBean.getGroupFtrCal(), sEntityBean.getGroupFtrCol(),
                    sEntityBean.getMethodName(), sEntityBean.getReportTitle(), sEntityBean.getPrimaryKey(), sEntityBean.getGroupField(), newWidth, "showSummaryReportGrid", "summaryColConfig", dEntityBean.isPdf(), dEntityBean.isExcel());
        }

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
        if (dEntityBean.isDateTimePicker())
        {
            pw.println("      if(!validateDate_txt(\"fromDate\",\"From Date\",true))");
            pw.println("      {  ");
            pw.println("        document." + moduleName + "Form.fromDate.focus();");
            pw.println("        return false;");
            pw.println("      }  ");

            pw.println("      if(!validateDate_txt(\"toDate\",\"To Date\",true))");
            pw.println("      {  ");
            pw.println("        document." + moduleName + "Form.toDate.focus();");
            pw.println("        return false;");
            pw.println("      }  ");

            pw.println("      if(!checkFutureDateNotAllow(document." + moduleName + "Form,\"fromDate\",\"From Date\", \"To Date\", document." + moduleName + "Form.toDate.value))");
            pw.println("      {");
            pw.println("        document." + moduleName + "Form.fromDate.focus();");
            pw.println("        return false;");
            pw.println("      }");
        }
        pw.println("        return true;");
        pw.println("}");

        if (dEntityBean.isPdf() || dEntityBean.isExcel())
        {
            pw.println("function changeExport()");
            pw.println("{");
            pw.println("    if(document.getElementById(\"rdoOnScreen\").checked)");
            pw.println("    {");
            pw.println("        document.getElementById(\"btnPrint\").disabled = false;");
            pw.println("    }");
            pw.println("    else");
            pw.println("    {");
            pw.println("        document.getElementById(\"btnPrint\").disabled = true;");
            pw.println("    }");
            pw.println("    if(document.getElementById(\"rdoPdf\").checked)");
            pw.println("    {");
            pw.println("        document.getElementById(\"pdfdisplaymode\").style.display = \"\";");
            pw.println("    }");
            pw.println("    else");
            pw.println("    {");
            pw.println("        document.getElementById(\"pdfdisplaymode\").style.display = \"none\";");
            pw.println("        document.getElementById(\"displayMode\").options[0].selected = true;");
            pw.println("    }");
            pw.println("}");

            pw.println("function insertNoRecordsFoundRow(gridObj)");
            pw.println("{");
            pw.println("    if(gridObj.getRowsNum()==0)");
            pw.println("    {");
            pw.println("        gridObj.addRow(0, ['']);");
            pw.println("        var colNumber=Math.round(gridObj.hdr.rows[0].cells.length/2);");
            pw.println("        gridObj.cells(0,colNumber).setValue(\"No Records Found.\");");
            pw.println("        gridObj.setColWidth(colNumber,\"120\");");
            if (dEntityBean.isPageFooter() || dEntityBean.isGrandTotal())
            {
                pw.println("    gridObj.detachFooter(0);");
            }
            pw.println("    }");
            pw.println("}");
        }

        if (dEntityBean.isPdf())
        {
            pw.println("function generatePDF()");
            pw.println("{");
            pw.println("    insertNoRecordsFoundRow(mygrid);");
            pw.println("    var param = getFormData(document." + moduleName + "Form);");
            pw.println("    param+=\"&reportTitle=" + dEntityBean.getReportTitle() + "\";");
            pw.println("    mygrid.toPDF('" + moduleName + ".fin?cmdAction=generatePDF&'+param,\"gray\",true,true);");
            pw.println("    if(!document.getElementById(\"rdoOnScreen\").checked)");
            pw.println("    {");
            pw.println("        hideDiv(\"basicReportDiv\");");
            pw.println("    }");
            pw.println("}");
        }
        if (dEntityBean.isExcel())
        {
            pw.println("function generateEXCEL()");
            pw.println("{");
            pw.println("    insertNoRecordsFoundRow(mygrid);");
            pw.println("    var param = getFormData(document." + moduleName + "Form);");
            pw.println("    mygrid.toExcel('" + moduleName + ".fin?cmdAction=generateExcel&'+param,\"gray\");");
            pw.println("    if(!document.getElementById(\"rdoOnScreen\").checked)");
            pw.println("    {");
            pw.println("        hideDiv(\"basicReportDiv\");");
            pw.println("    }");
            pw.println("}");
        }
        pw.println();
        if (dEntityBean.isColumns())
        {
            pw.println("function reArrangeColumns()");
            pw.println("{");
            pw.println("    var col=document.getElementById(\"col_cmb1\");");
            pw.println("    var col2=document.getElementById(\"col_cmb2\");");
            pw.println("    if(col2 !== null && col2.length>0)");
            pw.println("    {");
            pw.println("        var val=\"\";");
            pw.println("        for(var i=0;i<col.length;i++)");
            pw.println("        {");
            pw.println("            val=col[i].value;");
            pw.println("            mygrid.setColumnHidden(parseInt(val)+1,true);");
            pw.println("        }");
            pw.println("    }");
            pw.println("}");
        }
        pw.println();
        pw.close();
    }

    public List getColumnWidth(String[] tabColumns, String conType, String devServer, String[] alias, String[] query) throws ClassNotFoundException, SQLException
    {
        FinDhtmlReportDataManager dmgr = new FinDhtmlReportDataManager();
        FinDhtmlReportDirectConnection conn = new FinDhtmlReportDirectConnection();
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
            int temp = (int) (Integer.parseInt(wid.get(i).toString()) * 1004 / total);
            wid.set(i, temp);
            cnt += Integer.parseInt(wid.get(i).toString());
        }
        wid.set(tabColumns.length - 1, 1004 - cnt);
        return wid;
    }

    //set width of non selected fields
    public StringBuilder getHiddenFieldWidth(String[] allColumns, String[] selectedColumns, List oldWidth)
    {
        StringBuilder newWidth = new StringBuilder();
        for (int i = 0; i < allColumns.length; i++)
        {
            int j = 0;
            for (; j < selectedColumns.length; j++)
            {
                if (selectedColumns[j].equals(allColumns[i]))
                {
                    newWidth.append(oldWidth.get(j));
                    newWidth.append(",");
                    break;
                }
            }
            if (j == selectedColumns.length)
            {
                newWidth.append(0);
                newWidth.append(",");
            }
        }
        newWidth.deleteCharAt(newWidth.length() - 1);
        return newWidth;
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
            colnm.append("<b>");
            colnm.append(colNames[i]);
            colnm.append("</b>,");
        }
        colnm = colnm.deleteCharAt(colnm.length() - 1);
        return colnm;
    }

    public void getReportGrid(PrintWriter pw, String moduleName, String[] selectedColumns, String[] allColumns, String[] allDataTypes, boolean addControl, String[] gridColumn, String[] gridControl,
            boolean pageFooter, boolean grandTotal, String[] pageFooterColumn, boolean grouping, boolean groupFooter, String[] groupFooterCalc, String[] groupFooterCol,
            String methodName, String reportTitle, String primaryKey, String groupField, StringBuilder colWidth, String funcName, String colConfigNM, boolean isPdf, boolean isExcel)
    {
        pw.println("function " + funcName + "(rptfor)");
        pw.println("{");
        String tab = "\t";
        StringBuilder colAlign = new StringBuilder();
        StringBuilder colSorting = new StringBuilder();
        StringBuilder footerAlign = new StringBuilder();

        for (int i = 0; i < allDataTypes.length; i++)
        {
            if (allDataTypes[i].equals("Integer") || allDataTypes[i].equals("Float")
                    || allDataTypes[i].equals("Double") || allDataTypes[i].equals("Long") || allDataTypes[i].equals("Number") || allDataTypes[i].equals("BigDecimal"))
            {
                colAlign.append("right,");
                colSorting.append("int,");
            }
            else if (allDataTypes[i].equals("Date"))
            {
                colAlign.append("center,");
                colSorting.append("date,");
            }
            else
            {
                colAlign.append("left,");
                colSorting.append("str,");
            }
        }
        colAlign.deleteCharAt(colAlign.length() - 1);
        colSorting.deleteCharAt(colSorting.length() - 1);

        for (int i = 0; i < allColumns.length; i++)
        {
            int j = 0;
            for (; j < selectedColumns.length; j++)
            {
                if (selectedColumns[j].equals(allColumns[i]))
                {
                    if (allDataTypes[i].equals("Integer") || allDataTypes[i].equals("Float")
                            || allDataTypes[i].equals("Double") || allDataTypes[i].equals("Long") || allDataTypes[i].equals("Number") || allDataTypes[i].equals("BigDecimal"))
                    {
                        footerAlign.append("\"text-align:right\",");
                    }
                    else if (allDataTypes[i].equals("Date"))
                    {
                        footerAlign.append("\"text-align:center\",");
                    }
                    else
                    {
                        footerAlign.append("\"\",");
                    }
                    break;
                }
            }
            if (j == selectedColumns.length)
            {
                //footerAlign.append("\"display:none\",");
                footerAlign.append("\"\",");
            }
        }
        footerAlign.deleteCharAt(footerAlign.length() - 1);

        pw.println(tab + "var param = getFormData(document." + moduleName + "Form);");
        pw.println(tab + "mygrid = new dhtmlXGridObject(\"gridbox\");");
        pw.println(tab + "mygrid.setImagePath(getFinLibPath()+\"dhtmlxSuite/dhtmlxGrid/codebase/imgs/\");");
        pw.println(tab + "mygrid.setHeader(\"<b>SrNo.</b>," + setColumnHeader(allColumns).toString() + "\");");
        pw.println(tab + "mygrid.setInitWidths(\"60," + colWidth.toString() + "\");");
        pw.println(tab + "mygrid.setColAlign(\"center," + colAlign.toString() + "\");");
        pw.println(tab + "mygrid.setColSorting(\"int," + colSorting.toString() + "\");");
        if (gridColumn != null && gridControl != null)
        {
            StringBuilder gridCntrlStr = new StringBuilder();
            gridCntrlStr.append("ro,");
            for (int i = 0; i < allColumns.length; i++)
            {
                int j = 0;
                for (; j < gridColumn.length; j++)
                {
                    if (allColumns[i].equals(gridColumn[j]))
                    {
                        gridCntrlStr.append(gridControl[j]);
                        gridCntrlStr.append(",");
                        break;
                    }
                }
                if (j == gridColumn.length)
                {
                    gridCntrlStr.append("ro,");
                }
            }
            gridCntrlStr.deleteCharAt(gridCntrlStr.length() - 1);
            pw.println(tab + "mygrid.setColTypes(\"" + gridCntrlStr.toString() + "\");");
        }
        pw.println(tab + "mygrid.enableAutoWidth(true);");
        pw.println(tab + "mygrid.enableAutoHeight(true);");
        pw.println(tab + "mygrid.enableMultiline(true);");
        pw.println(tab + "mygrid.setDateFormat(\"%d-%m-%Y\");");
        pw.println(tab + "mygrid.enableDragAndDrop(true);");
        pw.println(tab + "mygrid.enableColumnMove(true);");
        pw.println(tab + "mygrid.enableMultiselect(true);");
        pw.println(tab + "mygrid.enableCollSpan(true);");
        pw.println(tab + "mygrid.setSkin(\"dhx_web\");");
        if (!grouping)
        {
            //for paging
            pw.println(tab + "document.getElementById(\"pagingArea\").innerHTML = \"\";");
            pw.println(tab + "document.getElementById(\"recinfoArea\").innerHTML = \"\";");
            pw.println(tab + "mygrid.enablePaging(true,10,10,\"pagingArea\",true,\"recinfoArea\");");
            pw.println(tab + "mygrid.setPagingWTMode(true,true,true,[50,100,200]);");
            pw.println(tab + "mygrid.setPagingSkin(\"toolbar\",\"dhx_web\");");
        }
        //set non-selected fields invisible
        int hdnColCnt = 0;
        for (int i = 0; i < allColumns.length; i++)
        {
            int j = 0;
            for (; j < selectedColumns.length; j++)
            {
                if (allColumns[i].equals(selectedColumns[j]))
                {
                    break;
                }
            }
            if (j == selectedColumns.length)
            {
                hdnColCnt++;
                pw.println(tab + "mygrid.setColumnHidden(" + (i + 1) + ",true);");
            }
        }

        //grouping and group footer
        StringBuilder grpStr = new StringBuilder();
        if (grouping)
        {
            for (int i = 0; i < allColumns.length; i++)
            {
                if (allColumns[i].equals(groupField))
                {
                    grpStr.append("mygrid.groupBy(");
                    grpStr.append(i + 1);
                    grpStr.append(",");
                }
            }
        }
        if (groupFooter)
        {
            grpStr.append("[\"#title\",");
            for (int i = 0; i < allColumns.length; i++)
            {
                int j = 0;
                for (; j < groupFooterCol.length; j++)
                {
                    if (allColumns[i].equals(groupFooterCol[j]))
                    {
                        grpStr.append("\"#");
                        grpStr.append(groupFooterCalc[j]);
                        grpStr.append("\",");
                        break;
                    }
                }
                if (j == groupFooterCol.length)
                {
                    grpStr.append("\"\",");
                }
            }
            grpStr.deleteCharAt(grpStr.length() - 1);
            grpStr.append("]");
        }
        if (grouping && !groupFooter)
        {
            grpStr.deleteCharAt(grpStr.length() - 1);
        }
        if (grouping)
        {
            grpStr.append(");");
            pw.println(tab + grpStr.toString());
        }

        pw.println(tab + "mygrid.init();");
        //page footer or grand footer
        if (pageFooter || grandTotal)
        {
            StringBuilder pgftrStr = new StringBuilder();
            pgftrStr.append("Grand Total,");
            for (int i = 0; i < allColumns.length; i++)
            {
                int j = 0;
                for (; j < pageFooterColumn.length; j++)
                {
                    if (allColumns[i].equals(pageFooterColumn[j]))
                    {
                        pgftrStr.append("{#stat_total},");
                        break;
                    }
                }
                if (j == pageFooterColumn.length)
                {
                    pgftrStr.append("-,");
                }
            }
            pgftrStr.deleteCharAt(pgftrStr.length() - 1);
            pw.println(tab + "mygrid.attachFooter(\"" + pgftrStr.toString() + "\",[\"\"," + footerAlign.toString() + "]);");
        }
        pw.println(tab + "if(rptfor === \"Print\")");
        pw.println(tab + "{");
        pw.println(tab + "  mygrid.load(\"" + moduleName + ".fin?" + methodName + "=getReportGrid&\"+param,printGridReport,\"json\");");
        pw.println(tab + "}");
        pw.println(tab + "else");
        pw.println(tab + "{");
        pw.println(tab + "  if(document.getElementById(\"rdoOnScreen\").checked)");
        pw.println(tab + "  {");
        pw.println(tab + "      showLoadingImg();");
        pw.println(tab + "      mygrid.load(\"" + moduleName + ".fin?" + methodName + "=getReportGrid&\"+param,hideLoadingImg,\"json\");");
        pw.println(tab + "  }");
        if (isPdf)
        {
            pw.println(tab + "  else if(document.getElementById(\"rdoPdf\").checked)");
            pw.println(tab + "  {");
            pw.println(tab + "      mygrid.post(\"" + moduleName + ".fin?cmdAction=getReportGrid\",param,generatePDF,\"json\");");
            //pw.println(tab + "      document.getElementById(\"basicReportDiv\").style.display = \"none\";");
            pw.println(tab + "  }");
        }
        if (isExcel)
        {
            pw.println(tab + "  else if(document.getElementById(\"rdoExcel\").checked)");
            pw.println(tab + "  {");
            pw.println(tab + "      mygrid.post(\"" + moduleName + ".fin?cmdAction=getReportGrid\",param,generateEXCEL,\"json\");");
            //pw.println(tab + "      document.getElementById(\"basicReportDiv\").style.display = \"none\";");
            pw.println(tab + "  }");
        }
        pw.println("}");
        pw.println(tab + "$('div.gridbox div.ftr table').css('padding-right','0');");
        pw.println(tab + "$('div.gridbox table.hdr ').css('padding-right','0');");
        pw.println(tab + "dhtmlxError.catchError(\"LoadXML\", function(a, b, data) {");
        pw.println(tab + "      document.getElementById(\"basicReportDiv\").style.display=\"none\";");
        pw.println(tab + "      document.getElementById(\"dispError\").style.display=\"\";");
        pw.println(tab + "      document.getElementById(\"dispError\").innerHTML=data[0].responseText;");
        pw.println(tab + "});");
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
