/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.finmaster;

import com.finlogic.apps.finstudio.finmaster.FinMasterFormBean;
import com.finlogic.business.finstudio.finmaster.FinMasterDataManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;

/**
 *
 * @author Sonam Patel
 */
public class MasterJSGenerator
{

    private static final String DATE = "Date";
    private static final String DTPKR = "DatePicker";
    private static final String COMBO = "ComboBox";
    private static final String TXTLIKECOMBO = "TextLikeCombo";
    private static final String INT = "Integer";
    private static final String LONG = "Long";
    private static final String FLOAT = "Float";
    private static final String DOUBLE = "Double";
    private static final String NUMBER = "Number";

    public void generateMasterJS(final FinMasterFormBean formBean) throws ClassNotFoundException, FileNotFoundException, SQLException
    {
        String projectName;
        projectName = formBean.getCmbProjectName();
        String moduleName;
        moduleName = formBean.getTxtModuleName().toLowerCase(Locale.getDefault());
        String tomcatPath;
        tomcatPath = finpack.FinPack.getProperty("tomcat1_path");
        String number;
        number = formBean.getSrNo() + "MGV2";
        String filePath;
        filePath = tomcatPath + "/webapps/finstudio/generated/" + number + "/";
        String projectPath;
        projectPath = filePath + projectName.toLowerCase(Locale.getDefault()) + "/js/";

        File file;
        file = new File(projectPath);
        file.mkdirs();
        PrintWriter pw;
        pw = new PrintWriter(projectPath + moduleName + ".js");
        StringBuilder colNames;

        String[] allMstColumns;
        allMstColumns = formBean.getHdnMstAllColumns();
        int mstLen;
        mstLen = allMstColumns.length;
        String[] allMstDataTypes;
        allMstDataTypes = formBean.getHdnMstAllDataTypes();

        StringBuilder lengthQuery;
        lengthQuery = new StringBuilder("SELECT ");
        for (int i = 0; i < mstLen; i++)
        {
            lengthQuery.append("MAX(LENGTH(").append(allMstColumns[i]).append(")),");
        }
        lengthQuery.deleteCharAt(lengthQuery.length() - 1);
        lengthQuery.append(" FROM ").append(formBean.getCmbMasterTable());

        FinMasterDataManager dataMgr;
        dataMgr = new FinMasterDataManager();
        SqlRowSet srs;
        srs = dataMgr.getRecordsByQuery(formBean, lengthQuery.toString());
        SqlRowSetMetaData rsmd;
        rsmd = srs.getMetaData();

        List wid;
        wid = new ArrayList();
        if (srs.next())
        {
            int col;
            col = rsmd.getColumnCount();
            int total = 0;
            for (int i = 0; i < col; i++)
            {
                if (srs.getInt(i + 1) == 0)
                {
                    wid.add(1);
                }
                else
                {
                    wid.add(srs.getInt(i + 1));
                }
                total += Integer.parseInt(wid.get(i).toString());
            }
            int cnt = 0;
            for (int i = 0; i < col - 1; i++)
            {
                int t;
                t = (int) (Integer.parseInt(wid.get(i).toString()) * 994 / total);
                wid.set(i, t);
                cnt += Integer.parseInt(wid.get(i).toString());
            }
            wid.set(col - 1, 994 - cnt);
        }

        ControlValidations valid;
        valid = new ControlValidations();

        String[] mstAddFields;
        mstAddFields = formBean.getHdnAddField();
        String[] mstAddControls;
        mstAddControls = formBean.getHdnAddControl();
        String[] mstAddIds;
        mstAddIds = formBean.getHdntxtAddId();
        int mstAddLen;
        mstAddLen = mstAddFields.length;
        boolean[] mstAddSel;
        mstAddSel = formBean.getHdnChkShowAdd();
        boolean[] mstEditSel;
        mstEditSel = formBean.getHdnChkShowEdit();
        boolean[] mstDelSel = null;
        if (formBean.isChkDelete())
        {
            mstDelSel = formBean.getHdnChkShowDel();
        }
        String[] mstViewColumns = null;
        int mstViewLen = 0;
        if (formBean.isChkView())
        {
            mstViewColumns = formBean.getChkViewColumn();
            mstViewLen = mstViewColumns.length;
        }
        String mstPrimeKey;
        mstPrimeKey = formBean.getCmbMasterTablePrimKey();

//        if (formBean.isChkHeader())
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
//            pw.println("    document.getElementById(\"servertime\").innerHTML = datestring + \" \" + timestring;");
//            pw.println("}");
//            pw.println();
//            pw.println("window.onload = function()");
//            pw.println("{");
//            pw.println("    setInterval(\"displaytime()\", 1000);");
//            pw.println("}");
//            pw.println();
//        }
        pw.println("function isSessionExpired(responseText)");
        pw.println("{");
        pw.println("    if (responseText.indexOf(\"ACCESS DENIED\", 0) > 0)");
        pw.println("    {");
        pw.println("        return true;");
        pw.println("    }");
        pw.println("    return false;");
        pw.println("}");
        pw.println();

        if (formBean.isChkEdit())
        {
            pw.println("function displayEditMasterGrid()");
            pw.println("{");
            pw.println("    if (!validateEditFilter())");
            pw.println("    {");
            pw.println("        return;");
            pw.println("    }");
            pw.println("    var param = getFormData(document." + moduleName + "Form);");
            pw.println("    param += \"&masterTask=edit\";");
            pw.println("    $('#mstgrid').GridUnload();");
            pw.println("    document.getElementById(\"errorBox\").innerHTML = \"\";");
            pw.println("    $(\"#mstgrid\").jqGrid({");
            pw.println("        url:'edit_" + formBean.getTxtModuleName().toLowerCase(Locale.getDefault()) + ".fin?" + formBean.getTxtParamName() + "=getMasterGrid&'+param,");
            pw.println("        datatype: \"json\",");

            colNames = new StringBuilder();
            boolean primeFlag = false;
            colNames.append("\"");
            colNames.append(mstPrimeKey);
            colNames.append("\",");
            for (int i = 0; i < mstAddLen; i++)
            {
                if (mstEditSel[i])
                {
                    if (mstAddFields[i].equals(mstPrimeKey))
                    {
                        primeFlag = true;
                    }
                    else if (!"None".equals(mstAddFields[i]))
                    {
                        colNames.append("\"");
                        colNames.append(mstAddFields[i]);
                        colNames.append("\",");
                    }
                }
            }
            colNames.append("\"EDIT\"");

            pw.println("        colNames:[" + colNames + "],");
            pw.println("        colModel:[");

            for (int j = 0; j < mstLen; j++)
            {
                if (allMstColumns[j].equals(mstPrimeKey))
                {
                    if (primeFlag)
                    {
                        if (allMstDataTypes[j].equals(INT) || allMstDataTypes[j].equals(LONG) || allMstDataTypes[j].equals(FLOAT) || allMstDataTypes[j].equals(DOUBLE) || allMstDataTypes[j].equals(NUMBER))
                        {
                            pw.println("            {name:'" + mstPrimeKey + "',index:'" + mstPrimeKey + "',width:'" + wid.get(j) + "',sorttype:'int',jsonmap:'cell." + j + "',align:'right'},");
                        }
                        else if (allMstDataTypes[j].equals(DATE))
                        {
                            pw.println("            {name:'" + mstPrimeKey + "',index:'" + mstPrimeKey + "',width:'" + wid.get(j) + "',jsonmap:'cell." + j + "',align:'center'},");
                        }
                        else
                        {
                            pw.println("            {name:'" + mstPrimeKey + "',index:'" + mstPrimeKey + "',width:'" + wid.get(j) + "',jsonmap:'cell." + j + "'},");
                        }
                    }
                    else
                    {
                        if (allMstDataTypes[j].equals(INT) || allMstDataTypes[j].equals(LONG) || allMstDataTypes[j].equals(FLOAT) || allMstDataTypes[j].equals(DOUBLE) || allMstDataTypes[j].equals(NUMBER))
                        {
                            pw.println("            {name:'" + mstPrimeKey + "',index:'" + mstPrimeKey + "',width:'" + wid.get(j) + "',sorttype:'int',jsonmap:'cell." + j + "',align:'right',hidden:true},");
                        }
                        else if (allMstDataTypes[j].equals(DATE))
                        {
                            pw.println("            {name:'" + mstPrimeKey + "',index:'" + mstPrimeKey + "',width:'" + wid.get(j) + "',jsonmap:'cell." + j + "',align:'center',hidden:true},");
                        }
                        else
                        {
                            pw.println("            {name:'" + mstPrimeKey + "',index:'" + mstPrimeKey + "',width:'" + wid.get(j) + "',jsonmap:'cell." + j + "',hidden:true},");
                        }
                    }
                    break;
                }
            }
            for (int k = 0; k < mstAddLen; k++)
            {
                if (mstEditSel[k] && !mstAddFields[k].equals(mstPrimeKey))
                {
                    for (int j = 0; j < mstLen; j++)
                    {
                        if (allMstColumns[j].equals(mstAddFields[k]))
                        {
                            if (allMstDataTypes[j].equals(INT) || allMstDataTypes[j].equals(LONG) || allMstDataTypes[j].equals(FLOAT) || allMstDataTypes[j].equals(DOUBLE) || allMstDataTypes[j].equals(NUMBER))
                            {
                                pw.println("            {name:'" + mstAddFields[k] + "',index:'" + mstAddFields[k] + "',width:'" + wid.get(j) + "',sorttype:'int',jsonmap:'cell." + j + "',align:'right'},");
                            }
                            else if (allMstDataTypes[j].equals(DATE))
                            {
                                pw.println("            {name:'" + mstAddFields[k] + "',index:'" + mstAddFields[k] + "',width:'" + wid.get(j) + "',jsonmap:'cell." + j + "',align:'center'},");
                            }
                            else
                            {
                                pw.println("            {name:'" + mstAddFields[k] + "',index:'" + mstAddFields[k] + "',width:'" + wid.get(j) + "',jsonmap:'cell." + j + "'},");
                            }
                            break;
                        }
                    }
                }
            }
            pw.println("            {name:'EDIT',index:'EDIT',width:'30',align:'center',sortable:false},");
            pw.println("        ],");
            pw.println("        jsonReader: { repeatitems : false },");
            pw.println("        rowNum: 10,");
            pw.println("        rowList: [10,20,30],");
            pw.println("        rowTotal: 10000,");
            pw.println("        rownumbers: true,");
            pw.println("        gridview: true,");
            pw.println("        height: 'auto',");
            pw.println("        width: '985',");
            pw.println("        altRows: true,");
            pw.println("        pager: '#pagermstgrid',");
            pw.println("        sortname: '" + mstPrimeKey + "',");
            pw.println("        sortorder: \"asc\",");
            pw.println("        viewrecords: true,");
            pw.println("        loadonce: true,");
            pw.println("        userDataOnFooter: true,");
            pw.println("        footerrow: false,");
            pw.println("        grouping: false,");
            pw.println("        gridComplete : function()");
            pw.println("        {");
            pw.println("            var ids = jQuery(\"#mstgrid\").jqGrid(\"getDataIDs\");");
            pw.println("            for (var i = 0; i < ids.length; i++)");
            pw.println("            {");
            pw.println("                var column = $('#mstgrid').getCell(ids[i], '" + mstPrimeKey + "');");
            pw.println("                var vcontrol0 = '<a href=\"javascript:void(0);\" onclick=\"editData(\\\'' + column + '\\\')\"><img src=\"' + document.getElementById(\"divFinLibPath\").innerHTML + '/resource/images/edit.gif\" style=\"height: 20px; width: 20px\"></a>';");
            pw.println("                jQuery(\"#mstgrid\").jqGrid('setRowData',ids[i],{");
            pw.println("                    EDIT:vcontrol0");
            pw.println("                });");
            pw.println("            }");
            pw.println("        },");
            pw.println("        loadComplete: function()");
            pw.println("        {");
            pw.println("            var $this = $(this), datatype = $this.getGridParam('datatype');");
            pw.println("            if (datatype === \"xml\" || datatype === \"json\")");
            pw.println("            {");
            pw.println("                setTimeout(function ()");
            pw.println("                {");
            pw.println("                    $this.trigger(\"reloadGrid\");");
            pw.println("                }, 100);");
            pw.println("            }");
            pw.println("        },");
            pw.println("        loadError : function(xhr)");
            pw.println("        {");
            pw.println("            $('#errorBox').show();");
            pw.println("            if (isSessionExpired(xhr.responseText))");
            pw.println("            {");
            pw.println("                $('#errorBox').html(xhr.responseText);");
            pw.println("            }");
            pw.println("            else");
            pw.println("            {");
            pw.println("                $('#errorBox').html(\"<center><b>Under Maintenance. Please Try Later.</b></center>\");");
            pw.println("            }");
            pw.println("            $('#gbox_mstgrid').hide();");
            pw.println("        }");
            pw.println("    });");
            pw.println("}");
            pw.println();
        }

        if (formBean.isChkDelete())
        {
            pw.println("function displayDeleteMasterGrid()");
            pw.println("{");
            pw.println("    if (!validateDeleteFilter())");
            pw.println("    {");
            pw.println("        return;");
            pw.println("    }");
            pw.println("    var param = getFormData(document." + moduleName + "Form);");
            pw.println("    param += \"&masterTask=delete\";");
            pw.println("    $('#mstgrid').GridUnload();");
            pw.println("    document.getElementById(\"errorBox\").innerHTML = \"\";");
            pw.println("    $(\"#mstgrid\").jqGrid({");
            pw.println("        url:'delete_" + formBean.getTxtModuleName().toLowerCase(Locale.getDefault()) + ".fin?" + formBean.getTxtParamName() + "=getMasterGrid&'+param,");
            pw.println("        datatype: \"json\",");

            colNames = new StringBuilder();
            boolean primeFlag = false;
            colNames.append("\"");
            colNames.append(mstPrimeKey);
            colNames.append("\",");
            for (int i = 0; i < mstAddLen; i++)
            {
                if (mstDelSel[i])
                {
                    if (mstAddFields[i].equals(mstPrimeKey))
                    {
                        primeFlag = true;
                    }
                    else if (!"None".equals(mstAddFields[i]))
                    {
                        colNames.append("\"");
                        colNames.append(mstAddFields[i]);
                        colNames.append("\",");
                    }
                }
            }
            colNames.append("\"DELETE\"");

            pw.println("        colNames:[" + colNames + "],");
            pw.println("        colModel:[");

            for (int j = 0; j < mstLen; j++)
            {
                if (allMstColumns[j].equals(mstPrimeKey))
                {
                    if (primeFlag)
                    {
                        if (allMstDataTypes[j].equals(INT) || allMstDataTypes[j].equals(LONG) || allMstDataTypes[j].equals(FLOAT) || allMstDataTypes[j].equals(DOUBLE) || allMstDataTypes[j].equals(NUMBER))
                        {
                            pw.println("            {name:'" + mstPrimeKey + "',index:'" + mstPrimeKey + "',width:'" + wid.get(j) + "',sorttype:'int',jsonmap:'cell." + j + "',align:'right'},");
                        }
                        else if (allMstDataTypes[j].equals(DATE))
                        {
                            pw.println("            {name:'" + mstPrimeKey + "',index:'" + mstPrimeKey + "',width:'" + wid.get(j) + "',jsonmap:'cell." + j + "',align:'center'},");
                        }
                        else
                        {
                            pw.println("            {name:'" + mstPrimeKey + "',index:'" + mstPrimeKey + "',width:'" + wid.get(j) + "',jsonmap:'cell." + j + "'},");
                        }
                    }
                    else
                    {
                        if (allMstDataTypes[j].equals(INT) || allMstDataTypes[j].equals(LONG) || allMstDataTypes[j].equals(FLOAT) || allMstDataTypes[j].equals(DOUBLE) || allMstDataTypes[j].equals(NUMBER))
                        {
                            pw.println("            {name:'" + mstPrimeKey + "',index:'" + mstPrimeKey + "',width:'" + wid.get(j) + "',sorttype:'int',jsonmap:'cell." + j + "',align:'right',hidden:true},");
                        }
                        else if (allMstDataTypes[j].equals(DATE))
                        {
                            pw.println("            {name:'" + mstPrimeKey + "',index:'" + mstPrimeKey + "',width:'" + wid.get(j) + "',jsonmap:'cell." + j + "',align:'center',hidden:true},");
                        }
                        else
                        {
                            pw.println("            {name:'" + mstPrimeKey + "',index:'" + mstPrimeKey + "',width:'" + wid.get(j) + "',jsonmap:'cell." + j + "',hidden:true},");
                        }
                    }
                    break;
                }
            }

            for (int k = 0; k < mstAddLen; k++)
            {
                if (mstDelSel[k] && !mstAddFields[k].equals(mstPrimeKey))
                {
                    for (int j = 0; j < mstLen; j++)
                    {
                        if (allMstColumns[j].equals(mstAddFields[k]))
                        {
                            if (allMstDataTypes[j].equals(INT) || allMstDataTypes[j].equals(LONG) || allMstDataTypes[j].equals(FLOAT) || allMstDataTypes[j].equals(DOUBLE) || allMstDataTypes[j].equals(NUMBER))
                            {
                                pw.println("            {name:'" + mstAddFields[k] + "',index:'" + mstAddFields[k] + "',width:'" + wid.get(j) + "',sorttype:'int',jsonmap:'cell." + j + "',align:'right'},");
                            }
                            else if (allMstDataTypes[j].equals(DATE))
                            {
                                pw.println("            {name:'" + mstAddFields[k] + "',index:'" + mstAddFields[k] + "',width:'" + wid.get(j) + "',jsonmap:'cell." + j + "',align:'center'},");
                            }
                            else
                            {
                                pw.println("            {name:'" + mstAddFields[k] + "',index:'" + mstAddFields[k] + "',width:'" + wid.get(j) + "',jsonmap:'cell." + j + "'},");
                            }
                            break;
                        }
                    }
                }
            }
            pw.println("            {name:'DELETE',index:'DELETE',width:'30',align:'center',sortable:false},");
            pw.println("        ],");
            pw.println("        jsonReader: { repeatitems : false },");
            pw.println("        rowNum:10,");
            pw.println("        rowList:[10,20,30],");
            pw.println("        rowTotal: 10000,");
            pw.println("        rownumbers: true,");
            pw.println("        gridview: true,");
            pw.println("        height: 'auto',");
            pw.println("        width: '985',");
            pw.println("        altRows: true,");
            pw.println("        pager: '#pagermstgrid',");
            pw.println("        sortname: '" + mstPrimeKey + "',");
            pw.println("        sortorder: \"asc\",");
            pw.println("        viewrecords: true,");
            pw.println("        loadonce: true,");
            pw.println("        userDataOnFooter: true,");
            pw.println("        footerrow: false,");
            pw.println("        grouping: false,");
            pw.println("        gridComplete : function()");
            pw.println("        {");
            pw.println("            var ids = jQuery(\"#mstgrid\").jqGrid(\"getDataIDs\");");
            pw.println("            for (var i = 0; i < ids.length; i++)");
            pw.println("            {");
            pw.println("                var column = $('#mstgrid').getCell(ids[i], '" + mstPrimeKey + "');");
            pw.println("                var vcontrol0 = '<a href=\"javascript:void(0);\" onclick=\"deleteData(\\\'' + column + '\\\')\"><img src=\"' + document.getElementById(\"divFinLibPath\").innerHTML + '/resource/images/delete.gif\" style=\"height: 20px; width: 20px\"></a>';");
            pw.println("                jQuery(\"#mstgrid\").jqGrid('setRowData',ids[i],{");
            pw.println("                    DELETE:vcontrol0");
            pw.println("                });");
            pw.println("            }");
            pw.println("        },");
            pw.println("        loadComplete: function()");
            pw.println("        {");
            pw.println("            var $this = $(this), datatype = $this.getGridParam('datatype');");
            pw.println("            if (datatype === \"xml\" || datatype === \"json\")");
            pw.println("            {");
            pw.println("                setTimeout(function ()");
            pw.println("                {");
            pw.println("                    $this.trigger(\"reloadGrid\");");
            pw.println("                }, 100);");
            pw.println("            }");
            pw.println("        },");
            pw.println("        loadError : function(xhr)");
            pw.println("        {");
            pw.println("            $('#errorBox').show();");
            pw.println("            if (isSessionExpired(xhr.responseText))");
            pw.println("            {");
            pw.println("                $('#errorBox').html(xhr.responseText);");
            pw.println("            }");
            pw.println("            else");
            pw.println("            {");
            pw.println("                $('#errorBox').html(\"<center><b>Under Maintenance. Please Try Later.</b></center>\");");
            pw.println("            }");
            pw.println("            $('#gbox_mstgrid').hide();");
            pw.println("        }");
            pw.println("    });");
            pw.println("}");
            pw.println();
        }

        if (formBean.isChkView())
        {
            pw.println("function displayViewMasterGrid()");
            pw.println("{");
            pw.println("    if (!validateViewFilter())");
            pw.println("    {");
            pw.println("        return;");
            pw.println("    }");
            pw.println("    var param = getFormData(document." + moduleName + "Form);");
            pw.println("    param += \"&masterTask=view\";");
            pw.println("    $('#mstgrid').GridUnload();");
            pw.println("    document.getElementById(\"errorBox\").innerHTML = \"\";");
            pw.println("    $(\"#mstgrid\").jqGrid({");
            pw.println("        url:'view_" + formBean.getTxtModuleName().toLowerCase(Locale.getDefault()) + ".fin?" + formBean.getTxtParamName() + "=getMasterGrid&'+param,");
            pw.println("        datatype: \"json\",");

            colNames = new StringBuilder();
            boolean primeFlag = false;
            for (int i = 0; i < mstViewLen; i++)
            {
                colNames.append("\"");
                colNames.append(mstViewColumns[i]);
                colNames.append("\",");
                if (mstViewColumns[i].equals(mstPrimeKey))
                {
                    primeFlag = true;
                }
            }
            colNames.deleteCharAt(colNames.length() - 1);
            pw.println("        colNames:[" + colNames + "],");
            pw.println("        colModel:[");

            for (int k = 0; k < mstViewLen; k++)
            {
                for (int j = 0; j < mstLen; j++)
                {
                    if (allMstColumns[j].equals(mstViewColumns[k]))
                    {
                        if (allMstDataTypes[j].equals(INT) || allMstDataTypes[j].equals(LONG) || allMstDataTypes[j].equals(FLOAT) || allMstDataTypes[j].equals(DOUBLE) || allMstDataTypes[j].equals(NUMBER))
                        {
                            pw.println("            {name:'" + mstViewColumns[k] + "',index:'" + mstViewColumns[k] + "',width:'" + wid.get(j) + "',sorttype:'int',jsonmap:'cell." + j + "',align:'right'},");
                        }
                        else if (allMstDataTypes[j].equals(DATE))
                        {
                            pw.println("            {name:'" + mstViewColumns[k] + "',index:'" + mstViewColumns[k] + "',width:'" + wid.get(j) + "',jsonmap:'cell." + j + "',align:'center'},");
                        }
                        else
                        {
                            pw.println("            {name:'" + mstViewColumns[k] + "',index:'" + mstViewColumns[k] + "',width:'" + wid.get(j) + "',jsonmap:'cell." + j + "'},");
                        }
                        break;
                    }
                }
            }
            pw.println("        ],");
            pw.println("        jsonReader: { repeatitems : false },");
            pw.println("        rowNum:10,");
            pw.println("        rowList:[10,20,30],");
            pw.println("        rowTotal: 10000,");
            pw.println("        rownumbers: true,");
            pw.println("        gridview: true,");
            pw.println("        height: 'auto',");
            pw.println("        width: '985',");
            pw.println("        altRows: true,");
            pw.println("        pager: '#pagermstgrid',");
            if (primeFlag)
            {
                pw.println("        sortname: '" + mstPrimeKey + "',");
                pw.println("        sortorder: \"asc\",");
            }
            pw.println("        viewrecords: true,");
            pw.println("        loadonce: true,");
            pw.println("        userDataOnFooter: true,");
            pw.println("        footerrow: false,");
            pw.println("        grouping: false,");
            if (primeFlag)
            {
                pw.println("        loadComplete: function()");
                pw.println("        {");
                pw.println("            var $this = $(this), datatype = $this.getGridParam('datatype');");
                pw.println("            if (datatype === \"xml\" || datatype === \"json\")");
                pw.println("            {");
                pw.println("                setTimeout(function ()");
                pw.println("                {");
                pw.println("                    $this.trigger(\"reloadGrid\");");
                pw.println("                }, 100);");
                pw.println("            }");
                pw.println("        },");
            }
            pw.println("        loadError : function(xhr)");
            pw.println("        {");
            pw.println("            $('#errorBox').show();");
            pw.println("            if (isSessionExpired(xhr.responseText))");
            pw.println("            {");
            pw.println("                $('#errorBox').html(xhr.responseText);");
            pw.println("            }");
            pw.println("            else");
            pw.println("            {");
            pw.println("                $('#errorBox').html(\"<center><b>Under Maintenance. Please Try Later.</b></center>\");");
            pw.println("            }");
            pw.println("            $('#gbox_mstgrid').hide();");
            pw.println("        }");
            pw.println("    });");
            pw.println("}");
            pw.println();
        }

        if (formBean.isChkAdd())
        {
            pw.println("function AddLoader()");
            pw.println("{");
            pw.println("    $('#mstgrid').GridUnload();");
            pw.println("    document.getElementById(\"errorBox\").innerHTML = \"\";");
            pw.println("    var param = getFormData(document." + moduleName + "Form);");
            pw.println("    getSynchronousData('add_" + moduleName + ".fin?" + formBean.getTxtParamName() + "=addLoader', param, 'load');");
            for (int i = 0; i < mstAddLen; i++)
            {
                if (mstAddSel[i])
                {
                    if (mstAddControls[i].equals(COMBO))
                    {
                        pw.println("    loadComboNew(\"" + mstAddIds[i] + "\", \"\", \"\", \"" + moduleName + "Form\");");
                    }
                    else if (mstAddControls[i].equals(TXTLIKECOMBO))
                    {
                        pw.println("    loadComboNew(\"" + mstAddIds[i] + "\", \"\", \"filterClient\", \"" + moduleName + "Form\");");
                    }
                    else if (mstAddControls[i].equals(DTPKR))
                    {
                        pw.println("    loadDatePicker(\"" + mstAddIds[i] + "\");");
                    }
                }
            }
            pw.println("}");
            pw.println();
        }

        if (formBean.isChkEdit())
        {
            pw.println("function EditLoader()");
            pw.println("{");
            pw.println("    $('#mstgrid').GridUnload();");
            pw.println("    document.getElementById(\"errorBox\").innerHTML = \"\";");
            pw.println("    var param = getFormData(document." + moduleName + "Form);");
            pw.println("    getSynchronousData('edit_" + moduleName + ".fin?" + formBean.getTxtParamName() + "=editLoader', param, 'load');");

            if (formBean.getHdnEditField() != null)
            {
                String[] editFields;
                editFields = formBean.getHdnEditField();
                String[] editControls;
                editControls = formBean.getHdnEditControl();
                String[] editIds;
                editIds = formBean.getHdntxtEditId();
                int editLen;
                editLen = editFields.length;
                for (int i = 0; i < editLen; i++)
                {
                    if (editControls[i].equals(COMBO))
                    {
                        pw.println("    loadComboNew(\"" + editIds[i] + "\", \"\", \"\", \"" + moduleName + "Form\");");
                    }
                    else if (editControls[i].equals(TXTLIKECOMBO))
                    {
                        pw.println("    loadComboNew(\"" + editIds[i] + "\", \"\", \"filterClient\", \"" + moduleName + "Form\");");
                    }
                    else if (editControls[i].equals(DTPKR))
                    {
                        pw.println("    loadDatePicker(\"" + editIds[i] + "\");");
                    }
                }
            }
            pw.println("}");
            pw.println();
        }

        if (formBean.isChkDelete())
        {
            pw.println("function DeleteLoader()");
            pw.println("{");
            pw.println("    $('#mstgrid').GridUnload();");
            pw.println("    document.getElementById(\"errorBox\").innerHTML = \"\";");
            pw.println("    var param = getFormData(document." + moduleName + "Form);");
            pw.println("    getSynchronousData('delete_" + moduleName + ".fin?" + formBean.getTxtParamName() + "=deleteLoader', param, 'load');");

            if (formBean.getHdnDeleteField() != null)
            {
                String[] delFields;
                delFields = formBean.getHdnDeleteField();
                String[] delControls;
                delControls = formBean.getHdnDeleteControl();
                String[] delIds;
                delIds = formBean.getHdntxtDeleteId();
                int delLen;
                delLen = delFields.length;
                for (int i = 0; i < delLen; i++)
                {
                    if (delControls[i].equals(COMBO))
                    {
                        pw.println("    loadComboNew(\"" + delIds[i] + "\", \"\", \"\", \"" + moduleName + "Form\");");
                    }
                    else if (delControls[i].equals(TXTLIKECOMBO))
                    {
                        pw.println("    loadComboNew(\"" + delIds[i] + "\", \"\", \"filterClient\", \"" + moduleName + "Form\");");
                    }
                    else if (delControls[i].equals(DTPKR))
                    {
                        pw.println("    loadDatePicker(\"" + delIds[i] + "\");");
                    }
                }
            }
            pw.println("}");
            pw.println();
        }

        if (formBean.isChkView())
        {
            pw.println("function ViewLoader()");
            pw.println("{");
            pw.println("    $('#mstgrid').GridUnload();");
            pw.println("    document.getElementById(\"errorBox\").innerHTML = \"\";");
            pw.println("    var param = getFormData(document." + moduleName + "Form);");
            pw.println("    getSynchronousData('view_" + moduleName + ".fin?" + formBean.getTxtParamName() + "=viewLoader', param, 'load');");

            if (formBean.getHdnViewField() != null)
            {
                String[] viewFields;
                viewFields = formBean.getHdnViewField();
                String[] viewControls;
                viewControls = formBean.getHdnViewControl();
                String[] viewIds;
                viewIds = formBean.getHdntxtViewId();
                int viewLen;
                viewLen = viewFields.length;
                int i = 0;
                for (; i < viewLen; i++)
                {
                    if (viewControls[i].equals(COMBO) || viewControls[i].equals(TXTLIKECOMBO) || viewControls[i].equals(DTPKR))
                    {
                        break;
                    }
                }

                for (; i < viewLen; i++)
                {
                    if (viewControls[i].equals(COMBO))
                    {
                        pw.println("        loadComboNew(\"" + viewIds[i] + "\", \"\", \"\", \"" + moduleName + "Form\");");
                    }
                    else if (viewControls[i].equals(TXTLIKECOMBO))
                    {
                        pw.println("        loadComboNew(\"" + viewIds[i] + "\", \"\", \"filterClient\", \"" + moduleName + "Form\");");
                    }
                    else if (viewControls[i].equals(DTPKR))
                    {
                        pw.println("        loadDatePicker(\"" + viewIds[i] + "\");");
                    }
                }
            }
            pw.println("}");
            pw.println();

            pw.println("function showTab(tab)");
            pw.println("{");
            pw.println("    $('#mstgrid').GridUnload();");
            pw.println("    document.getElementById(\"errorBox\").innerHTML = \"\";");
            pw.println("    document.getElementById(\"divViewFilter\").style.display = \"none\";");
            if (formBean.isChkOnScreen() || formBean.isChkPdf() || formBean.isChkXls())
            {
                pw.println("    document.getElementById(\"divViewExport\").style.display = \"none\";");
            }
            if (formBean.isChkColumns())
            {
                pw.println("    document.getElementById(\"divViewColumns\").style.display = \"none\";");
            }
            pw.println("    document.getElementById(tab).style.display = \"\";");
            pw.println("    document.getElementById(\"divButton\").style.display = \"\";");
            pw.println("}");
            pw.println();
        }

        if (formBean.isChkEdit())
        {
            pw.println("function validateEditFilter()");
            pw.println("{");
            if (formBean.getHdnEditField() != null)
            {
                String[] mstEditFields;
                mstEditFields = formBean.getHdnEditField();
                String[] mstEditValidations;
                mstEditValidations = formBean.getHdncmbEditValidation();
                String[] mstEditIds;
                mstEditIds = formBean.getHdntxtEditId();
                String[] mstEditLabels;
                mstEditLabels = formBean.getHdntxtEditLabel();
                String[] mstEditMandatory;
                mstEditMandatory = formBean.getHdnchkEditMandatory();
                int mstEditLen;
                mstEditLen = mstEditFields.length;

                String[] tabIndexes = null;
                tabIndexes = new String[mstEditLen];
                for (int i = 0; i < mstEditLen; i++)
                {
                    tabIndexes[i] = formBean.getHdnEditTabIndex()[i];
                }
                int[] tabIndex = new int[mstEditLen];
                for (int i = 0; i < mstEditLen; i++)
                {
                    tabIndex[i] = i;
                }
                for (int i = 0; i < mstEditLen; i++)
                {
                    for (int j = i; j < mstEditLen; j++)
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

                for (int i = 0; i < mstEditLen; i++)
                {
                    if (!"".equals(mstEditLabels[tabIndex[i]]))
                    {
                        if ("true".equals(mstEditMandatory[tabIndex[i]]))
                        {
                            valid.validations(pw, moduleName + "Form", mstEditIds[tabIndex[i]], mstEditValidations[tabIndex[i]], mstEditLabels[tabIndex[i]], true);
                        }
                        else
                        {
                            valid.validations(pw, moduleName + "Form", mstEditIds[tabIndex[i]], mstEditValidations[tabIndex[i]], mstEditLabels[tabIndex[i]], false);
                        }
                    }
                    else
                    {
                        if ("true".equals(mstEditMandatory[tabIndex[i]]))
                        {
                            valid.validations(pw, moduleName + "Form", mstEditIds[tabIndex[i]], mstEditValidations[tabIndex[i]], mstEditFields[tabIndex[i]], true);
                        }
                        else
                        {
                            valid.validations(pw, moduleName + "Form", mstEditIds[tabIndex[i]], mstEditValidations[tabIndex[i]], mstEditFields[tabIndex[i]], false);
                        }
                    }
                }
            }
            pw.println("    return true;");
            pw.println("}");
            pw.println();
        }

        if (formBean.isChkDelete())
        {
            pw.println("function validateDeleteFilter()");
            pw.println("{");
            if (formBean.getHdnDeleteField() != null)
            {
                String[] mstDeleteFields;
                mstDeleteFields = formBean.getHdnDeleteField();
                String[] mstDeleteValidations;
                mstDeleteValidations = formBean.getHdncmbDeleteValidation();
                String[] mstDeleteIds;
                mstDeleteIds = formBean.getHdntxtDeleteId();
                String[] mstDeleteLabels;
                mstDeleteLabels = formBean.getHdntxtDeleteLabel();
                String[] mstDeleteMandatory;
                mstDeleteMandatory = formBean.getHdnchkDeleteMandatory();
                int mstDeleteLen;
                mstDeleteLen = mstDeleteFields.length;

                String[] tabIndexes;
                tabIndexes = new String[mstDeleteLen];
                for (int i = 0; i < mstDeleteLen; i++)
                {
                    tabIndexes[i] = formBean.getHdnDeleteTabIndex()[i];
                }
                int[] tabIndex = new int[mstDeleteLen];
                for (int i = 0; i < mstDeleteLen; i++)
                {
                    tabIndex[i] = i;
                }
                for (int i = 0; i < mstDeleteLen; i++)
                {
                    for (int j = i; j < mstDeleteLen; j++)
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

                for (int i = 0; i < mstDeleteLen; i++)
                {
                    if (!"".equals(mstDeleteLabels[tabIndex[i]]))
                    {
                        if ("true".equals(mstDeleteMandatory[tabIndex[i]]))
                        {
                            valid.validations(pw, moduleName + "Form", mstDeleteIds[tabIndex[i]], mstDeleteValidations[tabIndex[i]], mstDeleteLabels[tabIndex[i]], true);
                        }
                        else
                        {
                            valid.validations(pw, moduleName + "Form", mstDeleteIds[tabIndex[i]], mstDeleteValidations[tabIndex[i]], mstDeleteLabels[tabIndex[i]], false);
                        }
                    }
                    else
                    {
                        if ("true".equals(mstDeleteMandatory[tabIndex[i]]))
                        {
                            valid.validations(pw, moduleName + "Form", mstDeleteIds[tabIndex[i]], mstDeleteValidations[tabIndex[i]], mstDeleteFields[tabIndex[i]], true);
                        }
                        else
                        {
                            valid.validations(pw, moduleName + "Form", mstDeleteIds[tabIndex[i]], mstDeleteValidations[tabIndex[i]], mstDeleteFields[tabIndex[i]], false);
                        }
                    }
                }
            }
            pw.println("    return true;");
            pw.println("}");
            pw.println();
        }

        if (formBean.isChkView())
        {
            pw.println("function validateViewFilter()");
            pw.println("{");
            if (formBean.getHdnViewField() != null)
            {
                String[] mstViewFields;
                mstViewFields = formBean.getHdnViewField();
                String[] mstViewValidations;
                mstViewValidations = formBean.getHdncmbViewValidation();
                String[] mstViewIds;
                mstViewIds = formBean.getHdntxtViewId();
                String[] mstViewLabels;
                mstViewLabels = formBean.getHdntxtViewLabel();
                String[] mstViewMandatory;
                mstViewMandatory = formBean.getHdnchkViewMandatory();
                int mstViewFltrLen;
                mstViewFltrLen = mstViewFields.length;

                String[] tabIndexes = null;
                tabIndexes = new String[mstViewFltrLen];
                for (int i = 0; i < mstViewFltrLen; i++)
                {
                    tabIndexes[i] = formBean.getHdnViewTabIndex()[i];
                }
                int[] tabIndex = new int[mstViewFltrLen];
                for (int i = 0; i < mstViewFltrLen; i++)
                {
                    tabIndex[i] = i;
                }
                for (int i = 0; i < mstViewFltrLen; i++)
                {
                    for (int j = i; j < mstViewFltrLen; j++)
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

                for (int i = 0; i < mstViewFltrLen; i++)
                {
                    if (!"".equals(mstViewLabels[tabIndex[i]]))
                    {
                        if ("true".equals(mstViewMandatory[tabIndex[i]]))
                        {
                            valid.validations(pw, moduleName + "Form", mstViewIds[tabIndex[i]], mstViewValidations[tabIndex[i]], mstViewLabels[tabIndex[i]], true);
                        }
                        else
                        {
                            valid.validations(pw, moduleName + "Form", mstViewIds[tabIndex[i]], mstViewValidations[tabIndex[i]], mstViewLabels[tabIndex[i]], false);
                        }
                    }
                    else
                    {
                        if ("true".equals(mstViewMandatory[tabIndex[i]]))
                        {
                            valid.validations(pw, moduleName + "Form", mstViewIds[tabIndex[i]], mstViewValidations[tabIndex[i]], mstViewFields[tabIndex[i]], true);
                        }
                        else
                        {
                            valid.validations(pw, moduleName + "Form", mstViewIds[tabIndex[i]], mstViewValidations[tabIndex[i]], mstViewFields[tabIndex[i]], false);
                        }
                    }
                }
            }
            pw.println("    return true;");
            pw.println("}");
            pw.println();
        }

        if (formBean.isChkAdd())
        {
            String[] addOnchange;
            addOnchange = formBean.getHdncmbAddOnchange();
            int addLen;
            addLen = mstAddControls.length;

            for (int i = 0; i < addLen; i++)
            {
                if ((mstAddControls[i].equals(COMBO) || mstAddControls[i].equals(TXTLIKECOMBO)) && (!"".equals(addOnchange[i]) && !"-1".equals(addOnchange[i])))
                {
                    pw.println("function fill" + mstAddIds[i] + "onchange" + addOnchange[i] + "()");
                    pw.println("{");
                    pw.println("    if (document.getElementById(\"" + addOnchange[i] + "\").value !== \"\" && document.getElementById(\"" + addOnchange[i] + "\").value !== \"-1\")");
                    pw.println("    {");
                    pw.println("        var param = \"" + addOnchange[i] + "=\" + document.getElementById(\"" + addOnchange[i] + "\").value;");
                    pw.println("        getSynchronousData('add_test.fin?cmdAction=fill" + mstAddIds[i] + "onchange" + addOnchange[i] + "', param, '" + mstAddIds[i] + "');");
                    pw.println("    }");
                    pw.println("    else");
                    pw.println("    {");
                    if (formBean.getHdntxtAddLabel()[i].equals(""))
                    {
                        pw.println("        document.getElementById(\"" + mstAddIds[i] + "\").innerHTML = \"<option value=\\\"-1\\\">-- Select " + mstAddFields[i] + " --</option>\";");
                    }
                    else
                    {
                        pw.println("        document.getElementById(\"" + mstAddIds[i] + "\").innerHTML = \"<option value=\\\"-1\\\">-- Select " + formBean.getHdntxtAddLabel()[i] + " --</option>\";");
                    }
                    pw.println("    }");
                    pw.println("}");
                    pw.println();
                }
            }
        }

        if (formBean.isChkEdit() && formBean.getHdnEditControl() != null)
        {
            String[] editControls;
            editControls = formBean.getHdnEditControl();
            String[] editIds;
            editIds = formBean.getHdntxtEditId();
            String[] editOnchange;
            editOnchange = formBean.getHdncmbEditOnchange();
            int editLen;
            editLen = editControls.length;

            for (int i = 0; i < editLen; i++)
            {
                if ((editControls[i].equals(COMBO) || editControls[i].equals(TXTLIKECOMBO)) && (!"".equals(editOnchange[i]) && !"-1".equals(editOnchange[i])))
                {
                    pw.println("function fill" + editIds[i] + "onchange" + editOnchange[i] + "()");
                    pw.println("{");
                    pw.println("    if (document.getElementById(\"" + editOnchange[i] + "\").value !== \"\" && document.getElementById(\"" + editOnchange[i] + "\").value !== \"-1\")");
                    pw.println("    {");
                    pw.println("        var param = \"" + editOnchange[i] + "=\" + document.getElementById(\"" + editOnchange[i] + "\").value;");
                    pw.println("        getSynchronousData('edit_test.fin?cmdAction=fill" + editIds[i] + "onchange" + editOnchange[i] + "', param, '" + editIds[i] + "');");
                    pw.println("    }");
                    pw.println("    else");
                    pw.println("    {");
                    if (formBean.getHdntxtEditLabel()[i].equals(""))
                    {
                        pw.println("        document.getElementById(\"" + editIds[i] + "\").innerHTML = \"<option value=\\\"-1\\\">-- Select " + formBean.getHdnEditField()[i] + " --</option>\";");
                    }
                    else
                    {
                        pw.println("        document.getElementById(\"" + editIds[i] + "\").innerHTML = \"<option value=\\\"-1\\\">-- Select " + formBean.getHdntxtEditLabel()[i] + " --</option>\";");
                    }
                    pw.println("    }");
                    pw.println("}");
                    pw.println();
                }
            }
        }

        if (formBean.isChkDelete() && formBean.getHdnDeleteControl() != null)
        {
            String[] delControls;
            delControls = formBean.getHdnDeleteControl();
            String[] delIds;
            delIds = formBean.getHdntxtDeleteId();
            String[] delOnchange;
            delOnchange = formBean.getHdncmbDeleteOnchange();
            int delLen;
            delLen = delControls.length;

            for (int i = 0; i < delLen; i++)
            {
                if ((delControls[i].equals(COMBO) || delControls[i].equals(TXTLIKECOMBO)) && (!"".equals(delOnchange[i]) && !"-1".equals(delOnchange[i])))
                {
                    pw.println("function fill" + delIds[i] + "onchange" + delOnchange[i] + "()");
                    pw.println("{");
                    pw.println("    if (document.getElementById(\"" + delOnchange[i] + "\").value !== \"\" && document.getElementById(\"" + delOnchange[i] + "\").value !== \"-1\")");
                    pw.println("    {");
                    pw.println("        var param = \"" + delOnchange[i] + "=\" + document.getElementById(\"" + delOnchange[i] + "\").value;");
                    pw.println("        getSynchronousData('delete_test.fin?cmdAction=fill" + delIds[i] + "onchange" + delOnchange[i] + "', param, '" + delIds[i] + "');");
                    pw.println("    }");
                    pw.println("    else");
                    pw.println("    {");
                    if (formBean.getHdntxtDeleteLabel()[i].equals(""))
                    {
                        pw.println("        document.getElementById(\"" + delIds[i] + "\").innerHTML = \"<option value=\\\"-1\\\">-- Select " + formBean.getHdnDeleteField()[i] + " --</option>\";");
                    }
                    else
                    {
                        pw.println("        document.getElementById(\"" + delIds[i] + "\").innerHTML = \"<option value=\\\"-1\\\">-- Select " + formBean.getHdntxtDeleteLabel()[i] + " --</option>\";");
                    }
                    pw.println("    }");
                    pw.println("}");
                    pw.println();
                }
            }
        }

        if (formBean.isChkView() && formBean.getHdnViewControl() != null)
        {
            String[] viewControls;
            viewControls = formBean.getHdnViewControl();
            String[] viewIds;
            viewIds = formBean.getHdntxtViewId();
            String[] viewOnchange;
            viewOnchange = formBean.getHdncmbViewOnchange();
            int viewLen;
            viewLen = viewControls.length;

            for (int i = 0; i < viewLen; i++)
            {
                if ((COMBO.equals(viewControls[i]) || TXTLIKECOMBO.equals(viewControls[i])) && (!"".equals(viewOnchange[i]) && !"-1".equals(viewOnchange[i])))
                {
                    pw.println("function fill" + viewIds[i] + "onchange" + viewOnchange[i] + "()");
                    pw.println("{");
                    pw.println("    if (document.getElementById(\"" + viewOnchange[i] + "\").value !== \"\" && document.getElementById(\"" + viewOnchange[i] + "\").value !== \"-1\")");
                    pw.println("    {");
                    pw.println("        var param = \"" + viewOnchange[i] + "=\" + document.getElementById(\"" + viewOnchange[i] + "\").value;");
                    pw.println("        getSynchronousData('view_test.fin?cmdAction=fill" + viewIds[i] + "onchange" + viewOnchange[i] + "', param, '" + viewIds[i] + "');");
                    pw.println("    }");
                    pw.println("    else");
                    pw.println("    {");
                    if (formBean.getHdntxtViewLabel()[i].equals(""))
                    {
                        pw.println("        document.getElementById(\"" + viewIds[i] + "\").innerHTML = \"<option value=\\\"-1\\\">-- Select " + formBean.getHdnViewField()[i] + " --</option>\";");
                    }
                    else
                    {
                        pw.println("        document.getElementById(\"" + viewIds[i] + "\").innerHTML = \"<option value=\\\"-1\\\">-- Select " + formBean.getHdntxtViewLabel()[i] + " --</option>\";");
                    }
                    pw.println("    }");
                    pw.println("}");
                    pw.println();
                }
            }
        }

        pw.println("function validateData()");
        pw.println("{");
        String[] mstAddValidations;
        mstAddValidations = formBean.getHdncmbAddValidation();
        String[] mstAddLabels;
        mstAddLabels = formBean.getHdntxtAddLabel();
        String[] mstAddMandatory;
        mstAddMandatory = formBean.getHdnchkAddMandatory();

        String[] tabIndexes;
        tabIndexes = new String[mstAddLen];
        for (int i = 0; i < mstAddLen; i++)
        {
            tabIndexes[i] = formBean.getHdnAddTabIndex()[i];
        }
        int[] tabIndex = new int[mstAddLen];
        for (int i = 0; i < mstAddLen; i++)
        {
            tabIndex[i] = i;
        }
        for (int i = 0; i < mstAddLen; i++)
        {
            for (int j = i; j < mstAddLen; j++)
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

        if (formBean.isChkAdd())
        {
            pw.println("    if (document.getElementById(\"btnAdd\").value === \"Add\")");
            pw.println("    {");
            for (int i = 0; i < mstAddLen; i++)
            {
                if (mstAddSel[i] && mstAddFields[i].equals(mstPrimeKey) && !(formBean.getTxtDataBaseType().equals("ORACLE") && !formBean.getTxtSequence().equals("")))
                {
                    String str;
                    if (COMBO.equals(mstAddControls[i]) || TXTLIKECOMBO.equals(mstAddControls[i]))
                    {
                        str = "select ";
                        if (formBean.getHdnrbtnAddMultiple()[i].equals("true"))
                        {
                            pw.println("        if (document.getElementById(\"" + mstAddIds[i] + "\").value === \"\")");
                        }
                        else
                        {
                            pw.println("        if (document.getElementById(\"" + mstAddIds[i] + "\").value === \"-1\")");
                        }
                    }
                    else
                    {
                        pw.println("        if (document.getElementById(\"" + mstAddIds[i] + "\").value === \"\")");
                        str = "enter ";
                    }
                    pw.println("        {");
                    if (!"".equals(mstAddLabels[i]))
                    {
                        pw.println("            alert(\"Please " + str + mstAddLabels[i] + "\");");
                    }
                    else
                    {
                        pw.println("            alert(\"Please " + str + mstAddFields[i] + "\");");
                    }
                    pw.println("            document.getElementById(\"" + mstAddIds[i] + "\").focus();");
                    pw.println("            return;");
                    pw.println("        }");
                    if (!"".equals(mstAddLabels[i]))
                    {
                        if ("true".equals(mstAddMandatory[i]))
                        {
                            valid.validations(pw, moduleName + "Form", mstAddIds[i], mstAddValidations[i], mstAddLabels[i], true);
                        }
                        else
                        {
                            valid.validations(pw, moduleName + "Form", mstAddIds[i], mstAddValidations[i], mstAddLabels[i], false);
                        }
                    }
                    else
                    {
                        if ("true".equals(mstAddMandatory[i]))
                        {
                            valid.validations(pw, moduleName + "Form", mstAddIds[i], mstAddValidations[i], mstAddFields[i], true);
                        }
                        else
                        {
                            valid.validations(pw, moduleName + "Form", mstAddIds[i], mstAddValidations[i], mstAddFields[i], false);
                        }
                    }
                }
            }
            for (int i = 0; i < mstAddLen; i++)
            {
                if (mstAddSel[tabIndex[i]] && !mstAddFields[tabIndex[i]].equals(mstPrimeKey))
                {
                    if (!"".equals(mstAddLabels[tabIndex[i]]))
                    {
                        if ("true".equals(mstAddMandatory[tabIndex[i]]))
                        {
                            valid.validations(pw, moduleName + "Form", mstAddIds[tabIndex[i]], mstAddValidations[tabIndex[i]], mstAddLabels[tabIndex[i]], true);
                        }
                        else
                        {
                            valid.validations(pw, moduleName + "Form", mstAddIds[tabIndex[i]], mstAddValidations[tabIndex[i]], mstAddLabels[tabIndex[i]], false);
                        }
                    }
                    else
                    {
                        if ("true".equals(mstAddMandatory[tabIndex[i]]))
                        {
                            valid.validations(pw, moduleName + "Form", mstAddIds[tabIndex[i]], mstAddValidations[tabIndex[i]], mstAddFields[tabIndex[i]], true);
                        }
                        else
                        {
                            valid.validations(pw, moduleName + "Form", mstAddIds[tabIndex[i]], mstAddValidations[tabIndex[i]], mstAddFields[tabIndex[i]], false);
                        }
                    }
                }
            }
            pw.println("        if (confirm(\"Are you sure you want to add this record ?\"))");
            pw.println("        {");
            pw.println("            insertData();");
            pw.println("        }");
            pw.println("        else");
            pw.println("        {");
            pw.println("            AddLoader();");
            pw.println("        }");
            pw.println("    }");
        }
        if (formBean.isChkEdit())
        {
            pw.println("    else if (document.getElementById(\"btnAdd\").value === \"Update\")");
            pw.println("    {");
            for (int i = 0; i < mstAddLen; i++)
            {
                if (mstEditSel[i] && !mstAddFields[i].equals(mstPrimeKey))
                {
                    if (!"".equals(mstAddLabels[i]))
                    {
                        if ("true".equals(mstAddMandatory[i]))
                        {
                            valid.validations(pw, moduleName + "Form", mstAddIds[i], mstAddValidations[i], mstAddLabels[i], true);
                        }
                        else
                        {
                            valid.validations(pw, moduleName + "Form", mstAddIds[i], mstAddValidations[i], mstAddLabels[i], false);
                        }
                    }
                    else
                    {
                        if ("true".equals(mstAddMandatory[i]))
                        {
                            valid.validations(pw, moduleName + "Form", mstAddIds[i], mstAddValidations[i], mstAddFields[i], true);
                        }
                        else
                        {
                            valid.validations(pw, moduleName + "Form", mstAddIds[i], mstAddValidations[i], mstAddFields[i], false);
                        }
                    }
                }
            }
            pw.println("        if (confirm(\"Are you sure you want to edit this record ?\"))");
            pw.println("        {");
            pw.println("            insertData();");
            pw.println("        }");
            pw.println("        else");
            pw.println("        {");
            pw.println("            EditLoader();");
            pw.println("        }");
            pw.println("    }");
        }
        if (formBean.isChkDelete())
        {
            pw.println("    else if (document.getElementById(\"btnAdd\").value === \"Delete\")");
            pw.println("    {");
            pw.println("        if (confirm(\"Are you sure you want to delete this record ?\"))");
            pw.println("        {");
            pw.println("            insertData();");
            pw.println("        }");
            pw.println("        else");
            pw.println("        {");
            pw.println("            DeleteLoader();");
            pw.println("        }");
            pw.println("    }");
        }
        pw.println("}");
        pw.println();

        if (formBean.isChkAdd() || formBean.isChkEdit() || formBean.isChkDelete())
        {
            pw.println("function insertData()");
            pw.println("{");
            pw.println("    if (document.getElementById(\"btnAdd\").value === \"Add\")");
            pw.println("    {");
            pw.println("        var param = getFormData(document." + moduleName + "Form);");
            pw.println("        getSynchronousData('add_" + moduleName + ".fin?" + formBean.getTxtParamName() + "=insertData', param, 'load');");

            pw.println("        if (document.getElementById(\"divMsg\"))");
            pw.println("        {");
            pw.println("            if (document.getElementById(\"hdnDbMsg\").value === \"success\")");
            pw.println("            {");
            pw.println("                alert(\"Record has been inserted successfully\");");
            pw.println("            }");
            pw.println("            else if (document.getElementById(\"hdnDbMsg\").value === \"failure\")");
            pw.println("            {");
            pw.println("                alert(\"Problem in record insertion\");");
            pw.println("            }");
            for (int i = 0; i < mstAddLen; i++)
            {
                if (mstAddControls[i].equals(COMBO))
                {
                    pw.println("            loadComboNew(\"" + mstAddIds[i] + "\", \"\", \"\", \"" + moduleName + "Form\");");
                }
                else if (mstAddControls[i].equals(TXTLIKECOMBO))
                {
                    pw.println("            loadComboNew(\"" + mstAddIds[i] + "\", \"\", \"filterClient\", \"" + moduleName + "Form\");");
                }
                else if (mstAddControls[i].equals(DTPKR))
                {
                    pw.println("            loadDatePicker(\"" + mstAddIds[i] + "\");");
                }
            }
            pw.println("        }");

            pw.println("    }");
            if (formBean.isChkEdit())
            {
                pw.println("    else if (document.getElementById(\"btnAdd\").value === \"Update\")");
                pw.println("    {");
                pw.println("        param = getFormData(document." + moduleName + "Form);");
                pw.println("        getSynchronousData('edit_" + moduleName + ".fin?" + formBean.getTxtParamName() + "=editData', param, 'load');");

                pw.println("        if (document.getElementById(\"divMsg\"))");
                pw.println("        {");
                pw.println("            if (document.getElementById(\"hdnDbMsg\").value === \"success\")");
                pw.println("            {");
                pw.println("                alert(\"Record has been updated successfully\");");
                pw.println("            }");
                pw.println("            else if (document.getElementById(\"hdnDbMsg\").value === \"failure\")");
                pw.println("            {");
                pw.println("                alert(\"Problem in record updation\");");
                pw.println("            }");
                if (formBean.getHdnEditField() != null)
                {
                    String[] editFields;
                    editFields = formBean.getHdnEditField();
                    String[] editControls;
                    editControls = formBean.getHdnEditControl();
                    String[] editIds;
                    editIds = formBean.getHdntxtEditId();
                    int editLen;
                    editLen = editFields.length;
                    for (int i = 0; i < editLen; i++)
                    {
                        if (editControls[i].equals(COMBO))
                        {
                            pw.println("            loadComboNew(\"" + editIds[i] + "\", \"\", \"\", \"" + moduleName + "Form\");");
                        }
                        else if (editControls[i].equals(TXTLIKECOMBO))
                        {
                            pw.println("            loadComboNew(\"" + editIds[i] + "\", \"\", \"filterClient\", \"" + moduleName + "Form\");");
                        }
                        else if (editControls[i].equals(DTPKR))
                        {
                            pw.println("            loadDatePicker(\"" + editIds[i] + "\");");
                        }
                    }
                }
                pw.println("        }");
                pw.println("    }");
            }
            if (formBean.isChkDelete())
            {
                pw.println("    else if (document.getElementById(\"btnAdd\").value === \"Delete\")");
                pw.println("    {");
                pw.println("        param = getFormData(document." + moduleName + "Form);");
                pw.println("        getSynchronousData('delete_" + moduleName + ".fin?" + formBean.getTxtParamName() + "=deleteData', param, 'load');");

                pw.println("        if (document.getElementById(\"divMsg\"))");
                pw.println("        {");
                pw.println("            if (document.getElementById(\"hdnDbMsg\").value === \"success\")");
                pw.println("            {");
                pw.println("                alert(\"Record has been deleted successfully\");");
                pw.println("            }");
                pw.println("            else if (document.getElementById(\"hdnDbMsg\").value === \"failure\")");
                pw.println("            {");
                pw.println("                alert(\"Problem in record deletion\");");
                pw.println("            }");
                if (formBean.getHdnDeleteField() != null)
                {
                    String[] delFields;
                    delFields = formBean.getHdnDeleteField();
                    String[] delControls;
                    delControls = formBean.getHdnDeleteControl();
                    String[] delIds;
                    delIds = formBean.getHdntxtDeleteId();
                    int delLen;
                    delLen = delFields.length;
                    for (int i = 0; i < delLen; i++)
                    {
                        if (delControls[i].equals(COMBO))
                        {
                            pw.println("            loadComboNew(\"" + delIds[i] + "\", \"\", \"\", \"" + moduleName + "Form\");");
                        }
                        else if (delControls[i].equals(TXTLIKECOMBO))
                        {
                            pw.println("            loadComboNew(\"" + delIds[i] + "\", \"\", \"filterClient\", \"" + moduleName + "Form\");");
                        }
                        else if (delControls[i].equals(DTPKR))
                        {
                            pw.println("            loadDatePicker(\"" + delIds[i] + "\");");
                        }
                    }
                }
                pw.println("        }");
                pw.println("    }");
            }
            pw.println("}");
            pw.println();
        }

        pw.println("function resetCheck()");
        pw.println("{");
        pw.println("    if (confirm(\"Are you sure you want to reset all changes ?\"))");
        pw.println("    {");
        pw.println("        if (document.getElementById(\"btnAdd\").value === \"Add\")");
        pw.println("        {");
        pw.println("            AddLoader();");
        pw.println("        }");
        pw.println("        else");
        pw.println("        {");
        pw.println("            var tmp = document.getElementById(\"tdAddButtons\").innerHTML;");
        pw.println("            document.getElementById(\"btnReset\").type = \"reset\";");
        pw.println("            document.getElementById(\"btnReset\").onclick = \"\";");
        pw.println("            document.getElementById(\"btnReset\").click();");
        pw.println("            document.getElementById(\"tdAddButtons\").innerHTML = tmp;");
        String[] addOnchange;
        addOnchange = formBean.getHdncmbAddOnchange();
        for (int i = 0; i < mstAddLen; i++)
        {
            if ((mstAddControls[i].equals(COMBO) || mstAddControls[i].equals(TXTLIKECOMBO)) && (!"".equals(addOnchange[i]) && !"-1".equals(addOnchange[i])))
            {
                pw.println("            fill" + mstAddIds[i] + "onchange" + addOnchange[i] + "();");
                pw.println("            document.getElementById(\"" + mstAddIds[i] + "\").value = document.getElementById(\"fintxt" + mstAddIds[i] + "\").value;");
            }
        }
        pw.println("        }");
        pw.println("    }");
        pw.println("}");
        pw.println();

        if (formBean.isChkEdit())
        {
            pw.println("function editData(val)");
            pw.println("{");
            pw.println("    $('#mstgrid').GridUnload();");
            pw.println("    document.getElementById(\"errorBox\").innerHTML = \"\";");
            pw.println("    var param = getFormData(document." + moduleName + "Form);");
            pw.println("    getSynchronousData('edit_" + moduleName + ".fin?" + formBean.getTxtParamName() + "=addLoader', param, 'load');");
            pw.println("    param = getFormData(document." + moduleName + "Form);");
            pw.println("    param += \"&primekey=\" + val;");
            pw.println("    param += \"&masterTask=edit\";");
            pw.println("    getSynchronousData('edit_" + moduleName + ".fin?" + formBean.getTxtParamName() + "=getRecByPrimeKey', param, 'load');");
            pw.println("    document.getElementById(\"divAddCaption\").innerHTML = \"Edit\";");
            pw.println("    document.getElementById(\"btnAdd\").value = \"Update\";");
            for (int i = 0; i < mstAddLen; i++)
            {
                if (mstAddFields[i].equals(mstPrimeKey))
                {
                    pw.println("    document.getElementById(\"" + mstAddIds[i] + "\").disabled = true;");
                }
            }
            for (int i = 0; i < mstAddLen; i++)
            {
                if (mstEditSel[i])
                {
                    if (mstAddControls[i].equals(COMBO))
                    {
                        pw.println("    loadComboNew(\"" + mstAddIds[i] + "\", \"\", \"\", \"" + moduleName + "Form\");");
                    }
                    else if (mstAddControls[i].equals(TXTLIKECOMBO))
                    {
                        pw.println("    loadComboNew(\"" + mstAddIds[i] + "\", \"\", \"filterClient\", \"" + moduleName + "Form\");");
                    }
                    else if (mstAddControls[i].equals(DTPKR))
                    {
                        pw.println("    loadDatePicker(\"" + mstAddIds[i] + "\");");
                    }
                }
            }

            for (int i = 0; i < mstAddLen; i++)
            {
                if ((mstAddControls[i].equals(COMBO) || mstAddControls[i].equals(TXTLIKECOMBO)) && (!"".equals(addOnchange[i]) && !"-1".equals(addOnchange[i])))
                {
                    pw.println("    fill" + mstAddIds[i] + "onchange" + addOnchange[i] + "();");
                    pw.println("    document.getElementById(\"" + mstAddIds[i] + "\").value = document.getElementById(\"fintxt" + mstAddIds[i] + "\").value;");
                }
            }
            pw.println("}");
            pw.println();
        }

        if (formBean.isChkDelete())
        {
            pw.println("function deleteData(val)");
            pw.println("{");
            pw.println("    $('#mstgrid').GridUnload();");
            pw.println("    document.getElementById(\"errorBox\").innerHTML = \"\";");
            pw.println("    var param = getFormData(document." + moduleName + "Form);");
            pw.println("    getSynchronousData('delete_" + moduleName + ".fin?" + formBean.getTxtParamName() + "=addLoader', param, 'load');");
            pw.println("    param = getFormData(document." + moduleName + "Form);");
            pw.println("    param += \"&primekey=\" + val;");
            pw.println("    param += \"&masterTask=delete\";");
            pw.println("    getSynchronousData('delete_" + moduleName + ".fin?" + formBean.getTxtParamName() + "=getRecByPrimeKey', param, 'load');");
            pw.println("    document.getElementById(\"divAddCaption\").innerHTML = \"Delete\";");
            pw.println("    document.getElementById(\"btnAdd\").value = \"Delete\";");
            for (int i = 0; i < mstAddLen; i++)
            {
                if (mstDelSel[i])
                {
                    if (mstAddControls[i].equals("Radio"))
                    {
                        String[] allVal;
                        allVal = formBean.getHdntxtAddRdoVal()[i].split("/");
                        for (int j = 0; j < allVal.length; j++)
                        {
                            pw.println("    document.getElementById(\"" + mstAddIds[i] + allVal[j] + "\").disabled = true;");
                        }
                    }
                    else
                    {
                        pw.println("    document.getElementById(\"" + mstAddIds[i] + "\").disabled = true;");
                    }
                }
            }
            for (int i = 0; i < mstAddLen; i++)
            {
                if (mstDelSel[i])
                {
                    if (mstAddControls[i].equals(COMBO))
                    {
                        pw.println("    loadComboNew(\"" + mstAddIds[i] + "\", \"\", \"\", \"" + moduleName + "Form\");");
                    }
                    else if (mstAddControls[i].equals(TXTLIKECOMBO))
                    {
                        pw.println("    loadComboNew(\"" + mstAddIds[i] + "\", \"\", \"filterClient\", \"" + moduleName + "Form\");");
                    }
                    else if (mstEditSel[i] && mstAddControls[i].equals(DTPKR))
                    {
                        pw.println("    loadDatePicker(\"" + mstAddIds[i] + "\");");
                    }
                }
            }

            for (int i = 0; i < mstAddLen; i++)
            {
                if ((mstAddControls[i].equals(COMBO) || mstAddControls[i].equals(TXTLIKECOMBO)) && (!"".equals(addOnchange[i]) && !"-1".equals(addOnchange[i])))
                {
                    pw.println("    fill" + mstAddIds[i] + "onchange" + addOnchange[i] + "();");
                    pw.println("    document.getElementById(\"" + mstAddIds[i] + "\").value = document.getElementById(\"fintxt" + mstAddIds[i] + "\").value;");
                }
            }
            pw.println("}");
            pw.println();
        }

        if (formBean.isChkView())
        {
            if (formBean.isChkOnScreen() || formBean.isChkPdf() || formBean.isChkXls())
            {
                pw.println("function showReport(rptfor, f)");
                pw.println("{");
                pw.println("    if (rptfor === 'View')");
                pw.println("    {");
                pw.println("        if (document.getElementById(\"rdoOnScreen\").checked)          /*~~~~~~~ ON SCREEN ~~~~~~~~*/");
                pw.println("        {");
                pw.println("            document.getElementById(\"btnApply\").type = \"button\";");
                pw.println("            displayViewMasterGrid();");
                if (formBean.isChkColumns())
                {
                    pw.println("            reArrangeColumn();");
                }
                pw.println("        }");
                if (formBean.isChkPdf() || formBean.isChkXls())
                {
                    pw.println("        else                                                     /*~~~~~~~ PDF OR EXCEL ~~~~~~~~*/");
                    pw.println("        {");
                    pw.println("            if (checkRptName())");
                    pw.println("            {");
                    pw.println("                document.getElementById(\"btnApply\").type = \"submit\";");
                    pw.println("                f.action = \"view_" + formBean.getTxtModuleName().toLowerCase(Locale.getDefault()) + ".fin?" + formBean.getTxtParamName() + "=getReportFile\";");
                    pw.println("                f.submit();");
                    pw.println("            }");
                    pw.println("        }");
                }
                pw.println("    }");
                pw.println("    else if (rptfor === 'Print')");
                pw.println("    {");
                pw.println("        jQuery(\"#mstgrid\").setGridParam({rowNum:10000}).trigger(\"reloadGrid\");");
                pw.println("        if (confirm(\"Please Confirm That You Want To Print Report\"))");
                pw.println("        {");
                pw.println("            printReport(\"report_display\",\"Report\");");
                pw.println("        }");
                pw.println("    }");
                pw.println("}");
                pw.println();
            }

            if (formBean.isChkPdf() || formBean.isChkXls())
            {
                pw.println("function checkRptName()");
                pw.println("{");
                pw.println("    document.getElementById(\"reportName\").value = document.getElementById(\"reportName\").value.replace(/^\\s+|\\s+$/g,'');");
                pw.println("    if (document.getElementById(\"reportName\").value === \"\")");
                pw.println("    {");
                pw.println("        alert(\"Please enter report name\");");
                pw.println("        document.getElementById(\"reportName\").focus();");
                pw.println("        return false;");
                pw.println("    }");
                pw.println("    return true;");
                pw.println("}");
                pw.println();
            }

            if (formBean.isChkColumns())
            {
                pw.println("function reArrangeColumn()");
                pw.println("{");
                pw.println("    var col1 = document.getElementById(\"col_cmb1\");");
                pw.println("    var col2 = document.getElementById(\"col_cmb2\");");
                pw.println("    if (col2 !== null && col2.length > 0)");
                pw.println("    {");
                pw.println("        var val = \"\";");
                pw.println("        for (var i = 0; i < col1.length; i++)");
                pw.println("        {");
                pw.println("            val = col1[i].text;");
                pw.println("            jQuery(\"#mstgrid\").jqGrid('hideCol', val);");
                pw.println("        }");
                pw.println("        var val2 = new Array();");
                pw.println("        val2[0] = 0;");
                pw.println("        var k = 1;");
                pw.println("        for (var j = 0; j < col2.length; j++)");
                pw.println("        {");
                pw.println("            val2[k++] = col2[j].value;");
                pw.println("        }");
                pw.println("        for (i = 0; i < col1.length; i++)");
                pw.println("        {");
                pw.println("            val2[k++] = col1[i].value;");
                pw.println("        }");
                pw.println("        jQuery(\"#mstgrid\").jqGrid('remapColumns', val2, true, false);");
                pw.println("    }");
                pw.println("}");
                pw.println();
            }
        }
        pw.close();
    }
}
