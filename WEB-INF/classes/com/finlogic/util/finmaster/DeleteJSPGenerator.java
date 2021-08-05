/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.finmaster;

import com.finlogic.apps.finstudio.finmaster.FinMasterFormBean;
import com.finlogic.business.finstudio.finmaster.FinMasterManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Locale;

/**
 *
 * @author Sonam Patel
 */
public class DeleteJSPGenerator
{

    public void generateDeleteJSP(final FinMasterFormBean formBean) throws FileNotFoundException
    {
        String projectName;
        projectName = formBean.getCmbProjectName();
        String tomcatPath;
        tomcatPath = finpack.FinPack.getProperty("tomcat1_path");
        String moduleName;
        moduleName = formBean.getTxtModuleName();
        String number;
        number = formBean.getSrNo() + "MGV2";
        String filePath;
        filePath = tomcatPath + "/webapps/finstudio/generated/" + number + "/";
        String projectPath;
        projectPath = filePath + projectName.toLowerCase(Locale.getDefault()) + "/WEB-INF/jsp/" + moduleName.toLowerCase(Locale.getDefault()) + "/";

        File file;
        file = new File(projectPath);
        file.mkdirs();
        PrintWriter pw;
        pw = new PrintWriter(projectPath + "delete.jsp");

        pw.println("<%-- TCIGBF --%>");
        pw.println();

        if (formBean.getHdnDeleteField() != null)
        {
            String[] allDelFields;
            allDelFields = formBean.getHdnDeleteField();
            String[] allDelControls;
            allDelControls = formBean.getHdnDeleteControl();
            String[] allDelLabels;
            allDelLabels = formBean.getHdntxtDeleteLabel();
            String[] allDelMandtry;
            allDelMandtry = formBean.getHdnchkDeleteMandatory();

            int[] tabIndex = new int[formBean.getHdnDeleteTabIndex().length];
            String[] tabIndexes;
            tabIndexes = formBean.getHdnDeleteTabIndex();
            int tabIdxLen;
            tabIdxLen = tabIndex.length;
            for (int i = 0; i < tabIdxLen; i++)
            {
                tabIndex[i] = i;
            }

            for (int i = 0; i < tabIdxLen; i++)
            {
                for (int j = i; j < tabIdxLen; j++)
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

            String[] allColumns;
            allColumns = formBean.getHdnMstAllColumns();
            String[] allDataTypes;
            allDataTypes = formBean.getHdnMstAllDataTypes();
            int allColLen;
            allColLen = allColumns.length;

            FinMasterManager manager;
            manager = new FinMasterManager();

            int k;
            boolean isC = false, isFmt = false;
            boolean isCmnFile = false;
            for (int i = 0; i < tabIdxLen; i++)
            {
                for (k = 0; k < allColLen; k++)
                {
                    if ((allDelControls[tabIndex[i]].equals("ComboBox") || allDelControls[tabIndex[i]].equals("TextLikeCombo")) && (formBean.getHdnrdoDeleteDataSrc()[tabIndex[i]].equals("Query") || formBean.getHdnrdoDeleteDataSrc()[tabIndex[i]].equals("WebService") || formBean.getHdnrdoDeleteDataSrc()[tabIndex[i]].equals("CommonCmb")))
                    {
                        isC = true;
                        if (allDelFields[tabIndex[i]].equals(allColumns[k]) && allDataTypes[k].equals("Date"))
                        {
                            isFmt = true;
                        }
                    }
                    else if (allDelControls[tabIndex[i]].equals("FileBox"))
                    {
                        isCmnFile = true;
                    }
                }
            }

            if (isC)
            {
                pw.println("<%@taglib prefix=\"c\" uri=\"http://java.sun.com/jsp/jstl/core\" %>");
                if (isFmt)
                {
                    pw.println("<%@taglib prefix=\"fmt\" uri=\"http://java.sun.com/jsp/jstl/fmt\" %>");
                }
                pw.println();
            }
            if (isCmnFile)
            {
                pw.println("<%@taglib prefix=\"common\" uri=\"http://www.njtechnologies.in/tags/filebox\" %>");
                pw.println();
            }

            String[] allDelOnchanges;
            allDelOnchanges = formBean.getHdncmbDeleteOnchange();
            String subproc = "";
            String subprocIdx = "";
            for (int j = 0; j < allDelOnchanges.length; j++)
            {
                if (allDelOnchanges[j] != null && !"".equals(allDelOnchanges[j]) && !"-1".equals(allDelOnchanges[j]))
                {
                    subproc += " fill" + formBean.getHdntxtDeleteId()[j];
                    subprocIdx += " " + j;
                }
            }
            subproc = subproc.trim();
            subprocIdx = subprocIdx.trim();
            if (!"".equals(subproc))
            {
                String arrsubproc[] = subproc.split(" ");
                String strChoose = "";
                for (int i = 0; i < arrsubproc.length; i++)
                {
                    if (i == 0)
                    {
                        strChoose = "<c:when test=\"${subproc ne '" + arrsubproc[i] + "'";
                    }
                    else
                    {
                        strChoose += " and subproc ne '" + arrsubproc[i] + "'";
                    }
                }
                strChoose += "}\">";
                pw.println("<c:choose>");
                pw.println("    " + strChoose);
            }

            pw.println("<div class=\"menu_caption_bg\">");
            pw.println("    <div class=\"menu_caption_text\">Delete</div>");
            pw.println("</div>");
            pw.println();
            pw.println("<div class=\"report_content\">");

            int counter = 0;
            boolean isDate;
            String controlId, controlName, ctrlPos;
            ctrlPos = formBean.getCmbDeleteControlPos();

            if (Integer.parseInt(ctrlPos) == 1)
            {
                pw.println("    <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"2\" class=\"menu_subcaption\" width=\"50%\">");
            }
            else if (Integer.parseInt(ctrlPos) == 2)
            {
                pw.println("    <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"2\" class=\"menu_subcaption\" width=\"90%\">");
            }
            pw.println("        <tr>");

            for (int i = 0; i < tabIdxLen; i++)
            {
                counter++;
                if (counter > Integer.parseInt(ctrlPos))
                {
                    pw.println("        </tr>");
                    pw.println("        <tr>");
                    counter = 1;
                }

                if ("1".equals(ctrlPos))
                {
                    pw.println("            <td class=\"report_content_caption\">");
                }
                else
                {
                    pw.println("            <td class=\"report_content_text\">");
                }

                StringBuilder strMndtry;
                strMndtry = new StringBuilder();
                if (allDelMandtry[tabIndex[i]].equals("true"))
                {
                    strMndtry.append("<span class=\"astriek\"> *</span>");
                }
                if (allDelLabels[tabIndex[i]].equals(""))
                {
                    pw.println("                " + allDelFields[tabIndex[i]] + strMndtry + " : ");
                }
                else
                {
                    pw.println("                " + allDelLabels[tabIndex[i]] + strMndtry + " : ");
                }
                pw.println("            </td>");
                if ("1".equals(ctrlPos))
                {
                    pw.println("            <td class=\"report_content_value\">");
                }
                else
                {
                    pw.println("            <td class=\"report_content_form\">");
                }

                isDate = false;
                for (k = 0; k < allColLen; k++)
                {
                    if (allDelFields[tabIndex[i]].equals(allColumns[k]) && allDataTypes[k].equals("Date"))
                    {
                        isDate = true;
                        break;
                    }
                }

                controlId = formBean.getHdntxtDeleteId()[tabIndex[i]];
                controlName = formBean.getHdntxtDeleteName()[tabIndex[i]];

                if (allDelControls[tabIndex[i]].equals("ComboBox") || allDelControls[tabIndex[i]].equals("TextLikeCombo"))
                {
                    String onchange = "";
                    for (int j = 0; j < allDelOnchanges.length; j++)
                    {
                        if (controlId.equals(allDelOnchanges[j]))
                        {
                            onchange += " fill" + formBean.getHdntxtDeleteId()[j] + "onchange" + controlId + "();";
                        }
                    }
                    if (!"".equals(onchange))
                    {
                        onchange = "onchange=\"javascript:" + onchange + "\"";
                    }
                    if (formBean.getHdnrbtnDeleteMultiple()[tabIndex[i]].equals("true"))
                    {
                        pw.println("                <select multiple id=\"" + controlId + "\" name=\"" + controlName + "\" " + callMe("class=\"", formBean.getHdntxtDeleteClass()[tabIndex[i]]) + callMe("style=\"", formBean.getHdntxtDeleteStyle()[tabIndex[i]]) + callMe("tabindex=\"", tabIndexes[i]) + callMe("size=\"", formBean.getHdntxtDeleteSize()[tabIndex[i]]) + onchange + ">");
                    }
                    else
                    {
                        pw.println("                <select id=\"" + controlId + "\" name=\"" + controlName + "\" " + callMe("class=\"", formBean.getHdntxtDeleteClass()[tabIndex[i]]) + callMe("style=\"", formBean.getHdntxtDeleteStyle()[tabIndex[i]]) + callMe("tabindex=\"", tabIndexes[i]) + callMe("size=\"", formBean.getHdntxtDeleteSize()[tabIndex[i]]) + onchange + ">");
                        if (allDelLabels[tabIndex[i]].equals(""))
                        {
                            pw.println("                    <option value=\"-1\">-- Select " + allDelFields[tabIndex[i]] + " --</option>");
                        }
                        else
                        {
                            pw.println("                    <option value=\"-1\">-- Select " + allDelLabels[tabIndex[i]] + " --</option>");
                        }
                    }
                    if (formBean.getHdnrdoDeleteDataSrc()[tabIndex[i]].equals("Query") || formBean.getHdnrdoDeleteDataSrc()[tabIndex[i]].equals("WebService") || formBean.getHdnrdoDeleteDataSrc()[tabIndex[i]].equals("CommonCmb"))
                    {
                        if ("None".equals(allDelFields[tabIndex[i]]))
                        {
                            pw.println("                    <c:forEach items=\"${" + allDelFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + controlId + "s}\" var=\"" + allDelFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + controlId + "\">");
                        }
                        else
                        {
                            pw.println("                    <c:forEach items=\"${" + allDelFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + "s}\" var=\"" + allDelFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + "\">");
                        }
                        if (formBean.getHdnrdoDeleteDataSrc()[tabIndex[i]].equals("Query"))
                        {
                            String[] cols;
                            cols = manager.getColumnsOfQuery(formBean, formBean.getHdntxtDeleteSrcQuery()[tabIndex[i]]);
                            if (isDate)
                            {
                                if ("None".equals(allDelFields[tabIndex[i]]))
                                {
                                    pw.println("                        <option value=\"<fmt:formatDate pattern=\"dd-MM-yyyy\" value=\"${" + allDelFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + controlId + "." + cols[0] + "}\"></fmt:formatDate>\">");
                                    pw.println("                            <fmt:formatDate pattern=\"dd-MM-yyyy\" value=\"${" + allDelFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + controlId + "." + cols[1] + "}\"></fmt:formatDate>");
                                    pw.println("                        </option>");
                                }
                                else
                                {
                                    pw.println("                        <option value=\"<fmt:formatDate pattern=\"dd-MM-yyyy\" value=\"${" + allDelFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + "." + cols[0] + "}\"></fmt:formatDate>\">");
                                    pw.println("                            <fmt:formatDate pattern=\"dd-MM-yyyy\" value=\"${" + allDelFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + "." + cols[1] + "}\"></fmt:formatDate>");
                                    pw.println("                        </option>");
                                }
                            }
                            else
                            {
                                if ("None".equals(allDelFields[tabIndex[i]]))
                                {
                                    pw.println("                        <option value=\"${" + allDelFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + controlId + "." + cols[0] + "}\">${" + allDelFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + controlId + "." + cols[1] + "}</option>");
                                }
                                else
                                {
                                    pw.println("                        <option value=\"${" + allDelFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + "." + cols[0] + "}\">${" + allDelFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + "." + cols[1] + "}</option>");
                                }
                            }
                        }
                        else if (formBean.getHdnrdoDeleteDataSrc()[tabIndex[i]].equals("CommonCmb"))
                        {
                            if ("None".equals(allDelFields[tabIndex[i]]))
                            {
                                pw.println("                        <option value=\"${" + allDelFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + controlId + ".KEY}\">${" + allDelFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + controlId + ".VALUE}</option>");
                            }
                            else
                            {
                                pw.println("                        <option value=\"${" + allDelFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + ".KEY}\">${" + allDelFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + ".VALUE}</option>");
                            }
                        }
                        else
                        {
                            String cmbVal, cmbText;
                            cmbVal = formBean.getHdntxtDeleteWsCmbValue()[tabIndex[i]];
                            cmbText = formBean.getHdntxtDeleteWsCmbText()[tabIndex[i]];
                            if ("None".equals(allDelFields[tabIndex[i]]))
                            {
                                pw.println("                        <option value=\"${" + allDelFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + controlId + "." + cmbVal + "}\">${" + allDelFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + controlId + "." + cmbText + "}</option>");
                            }
                            else
                            {
                                pw.println("                        <option value=\"${" + allDelFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + "." + cmbVal + "}\">${" + allDelFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + "." + cmbText + "}</option>");
                            }
                        }
                        pw.println("                    </c:forEach>");
                    }
                    else if (formBean.getHdnrdoDeleteDataSrc()[tabIndex[i]].equals("Static"))
                    {
                        String[] vals;
                        vals = formBean.getHdntxtDeleteSrcStatic()[tabIndex[i]].split(";");
                        for (int j = 0; j < vals.length; j++)
                        {
                            String[] key;
                            key = vals[j].split(",");
                            pw.println("                    <option value=\"" + key[0].trim() + "\">" + key[1].trim() + "</option>");
                        }
                    }
                    pw.println("                </select>");
                }
                else if (allDelControls[tabIndex[i]].equals("TextBox"))
                {
                    String format = "";
                    if (isDate)
                    {
                        format = "[DD-MM-YYYY]";
                    }
                    if (formBean.getHdnrbtnDeleteReadonly()[tabIndex[i]].equals("true"))
                    {
                        pw.println("                <input type=\"text\" readonly id=\"" + controlId + "\" name=\"" + controlName + "\" " + callMe("value=\"", formBean.getHdntxtDeleteValue()[tabIndex[i]]) + callMe("align=\"", formBean.getHdncmbDeleteAlign()[tabIndex[i]]) + callMe("maxlength=\"", formBean.getHdntxtDeleteMaxLength()[tabIndex[i]]) + callMe("class=\"", formBean.getHdntxtDeleteClass()[tabIndex[i]]) + callMe("style=\"", formBean.getHdntxtDeleteStyle()[tabIndex[i]]) + callMe("tabindex=\"", tabIndexes[i]) + callMe("size=\"", formBean.getHdntxtDeleteSize()[tabIndex[i]]) + ">" + format);
                    }
                    else
                    {
                        pw.println("                <input type=\"text\" id=\"" + controlId + "\" name=\"" + controlName + "\" " + callMe("value=\"", formBean.getHdntxtDeleteValue()[tabIndex[i]]) + callMe("align=\"", formBean.getHdncmbDeleteAlign()[tabIndex[i]]) + callMe("maxlength=\"", formBean.getHdntxtDeleteMaxLength()[tabIndex[i]]) + callMe("class=\"", formBean.getHdntxtDeleteClass()[tabIndex[i]]) + callMe("style=\"", formBean.getHdntxtDeleteStyle()[tabIndex[i]]) + callMe("tabindex=\"", tabIndexes[i]) + callMe("size=\"", formBean.getHdntxtDeleteSize()[tabIndex[i]]) + ">" + format);
                    }
                }
                else if (allDelControls[tabIndex[i]].equals("Radio"))
                {
                    int total;
                    total = Integer.parseInt(formBean.getHdntxtDeleteTotalRdo()[tabIndex[i]]);
                    String[] allCap, allVal;
                    allCap = formBean.getHdntxtDeleteRdoCap()[tabIndex[i]].split("/");
                    allVal = formBean.getHdntxtDeleteRdoVal()[tabIndex[i]].split("/");
                    String openTag, param, check;
                    openTag = "<input type=\"radio\" ";
                    for (int j = 0; j < total; j++)
                    {
                        if (!formBean.getHdntxtDeleteRdoDefVal()[tabIndex[i]].equals("") && Integer.parseInt(formBean.getHdntxtDeleteRdoDefVal()[tabIndex[i]]) == (j + 1))
                        {
                            check = "checked ";
                        }
                        else
                        {
                            check = "";
                        }
                        param = "id=\"" + controlId + allVal[j] + "\" class=\"radio\" name=\"" + controlName + "\" " + callMe("class=\"", formBean.getHdntxtDeleteClass()[tabIndex[i]]) + check + callMe("style=\"", formBean.getHdntxtDeleteStyle()[tabIndex[i]]) + callMe("tabindex=\"", tabIndexes[i]) + callMe("size=\"", formBean.getHdntxtDeleteSize()[tabIndex[i]]) + "value=\"" + allVal[j] + "\" >";
                        pw.println("                " + openTag + param);
                        pw.println("                <label> " + allCap[j] + " </label>");
                    }
                }
                else if (allDelControls[tabIndex[i]].equals("CheckBox"))
                {
                    if (formBean.getHdnrbtnDeleteChecked()[tabIndex[i]].equals("true"))
                    {
                        pw.println("                <input type=\"checkbox\" id=\"" + controlId + "\" name=\"" + controlName + "\" checked=\"true\" " + callMe("class=\"", formBean.getHdntxtDeleteClass()[tabIndex[i]]) + callMe("style=\"", formBean.getHdntxtDeleteStyle()[tabIndex[i]]) + callMe("tabindex=\"", tabIndexes[i]) + callMe("size=\"", formBean.getHdntxtDeleteSize()[tabIndex[i]]) + ">");
                    }
                    else
                    {
                        pw.println("                <input type=\"checkbox\" id=\"" + controlId + "\" name=\"" + controlName + "\" " + callMe("class=\"", formBean.getHdntxtDeleteClass()[tabIndex[i]]) + callMe("style=\"", formBean.getHdntxtDeleteStyle()[tabIndex[i]]) + callMe("tabindex=\"", tabIndexes[i]) + callMe("size=\"", formBean.getHdntxtDeleteSize()[tabIndex[i]]) + ">");
                    }
                }
                else if (allDelControls[tabIndex[i]].equals("DatePicker"))
                {
                    pw.println("                <input type=\"text\" class=\"datepickerclass\" id=\"" + controlId + "\" name=\"" + controlName + "\" value=\"${" + allDelFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + "}\" " + callMe("tabindex=\"", tabIndexes[i]) + ">");
                }
                else if (allDelControls[tabIndex[i]].equals("Password"))
                {
                    String format = "";
                    if (isDate)
                    {
                        format = "[DD-MM-YYYY]";
                    }
                    pw.println("                <input type=\"password\" id=\"" + controlId + "\" name=\"" + controlName + "\" " + callMe("value=\"", formBean.getHdntxtDeleteValue()[tabIndex[i]]) + callMe("align=\"", formBean.getHdncmbDeleteAlign()[tabIndex[i]]) + callMe("maxlength=\"", formBean.getHdntxtDeleteMaxLength()[tabIndex[i]]) + callMe("class=\"", formBean.getHdntxtDeleteClass()[tabIndex[i]]) + callMe("style=\"", formBean.getHdntxtDeleteStyle()[tabIndex[i]]) + callMe("tabindex=\"", tabIndexes[i]) + callMe("size=\"", formBean.getHdntxtDeleteSize()[tabIndex[i]]) + ">" + format);
                }
                else if (allDelControls[tabIndex[i]].equals("TextArea"))
                {
                    StringBuilder row;
                    row = new StringBuilder();
                    StringBuilder col;
                    col = new StringBuilder();
                    if (formBean.getHdntxtDeleteRows()[tabIndex[i]] == null || formBean.getHdntxtDeleteRows()[tabIndex[i]].equals(""))
                    {
                        row.append("rows=\"2\"");
                    }
                    else
                    {
                        row.append("rows=\"");
                        row.append(formBean.getHdntxtDeleteRows()[tabIndex[i]]);
                        row.append("\"");
                    }
                    if (formBean.getHdntxtDeleteCols()[tabIndex[i]] == null || formBean.getHdntxtDeleteCols()[tabIndex[i]].equals(""))
                    {
                        col.append("cols=\"2\"");
                    }
                    else
                    {
                        col.append("cols=\"");
                        col.append(formBean.getHdntxtDeleteCols()[tabIndex[i]]);
                        col.append("\"");
                    }
                    String format = "";
                    if (isDate)
                    {
                        format = "[DD-MM-YYYY]";
                    }
                    pw.println("                <textarea " + row + " " + col + " id=\"" + controlId + "\" name=\"" + controlName + "\" " + callMe("class=\"", formBean.getHdntxtDeleteClass()[tabIndex[i]]) + callMe("style=\"", formBean.getHdntxtDeleteStyle()[tabIndex[i]]) + callMe("tabindex=\"", tabIndexes[i]) + "></textarea>" + format);
                }
                else if (allDelControls[tabIndex[i]].equals("File"))
                {
                    pw.println("        <input type=\"file\" id=\"" + controlId + "\" name=\"" + controlName + "\" class=\"input_brows\"" + callMe("tabindex=\"", tabIndexes[i]) + ">");
                }
                else if (allDelControls[tabIndex[i]].equals("FileBox"))
                {
                    String formName;
                    formName = formBean.getTxtModuleName().toLowerCase(Locale.getDefault()) + "Form";
                    pw.println("                <common:filebox controlid=\"" + controlId + "\" name=\"" + controlName + "\" " + callMe("maxsize=\"", formBean.getHdntxtDeleteMaxsize()[tabIndex[i]]) + callMe("maxfiles=\"", formBean.getHdntxtDeleteMaxfiles()[tabIndex[i]]) + callMe("value=\"", formBean.getHdntxtDeleteDispTxt()[tabIndex[i]]) + "elementname=\"" + formBean.getHdntxtDeleteEleName()[tabIndex[i]] + "\" formid=\"" + formName + "\" type=\"" + formBean.getHdntxtDeleteType()[tabIndex[i]] + "\" " + callMe("onremovecallback=\"", formBean.getHdntxtDeleteOnremoveCall()[tabIndex[i]]) + "/>");
                }
                pw.println("            </td>");
            }

            pw.println("        </tr>");
            pw.println("        <tr>");
            pw.println("            <td align=\"center\" colspan=\"" + Integer.parseInt(ctrlPos) * 2 + "\">");
            pw.println("                <input class=\"button\" type=\"button\" id=\"btnApply\" name=\"btnApply\" Value=\"Apply\" onclick=\"javascript: displayDeleteMasterGrid()\" >");
            pw.println("            </td>");
            pw.println("        </tr>");
            pw.println("    </table>");
            pw.println("    <div id=\"divMsg\" style=\"display: none\">");
            pw.println("        <input type=\"hidden\" id=\"hdnDbMsg\" name=\"hdnDbMsg\" value=\"${DBoperation}\" >");
            pw.println("    </div>");
            pw.println("</div>");

            if (!"".equals(subproc))
            {
                pw.println("    </c:when>");
                String arrsubproc[] = subproc.split(" ");
                String arrsubprocIdx[] = subprocIdx.split(" ");
                for (int i = 0; i < arrsubproc.length; i++)
                {
                    pw.println("    <c:when test=\"${subproc eq '" + arrsubproc[i] + "'}\">");
                    int idx = Integer.parseInt(arrsubprocIdx[i]);

                    isDate = false;
                    for (k = 0; k < allColLen; k++)
                    {
                        if (allDelFields[idx].equals(allColumns[k]) && allDataTypes[k].equals("Date"))
                        {
                            isDate = true;
                            break;
                        }
                    }

                    controlId = formBean.getHdntxtDeleteId()[idx];

                    if (!formBean.getHdnrbtnDeleteMultiple()[idx].equals("true"))
                    {
                        if (allDelLabels[idx].equals(""))
                        {
                            pw.println("        <option value=\"-1\">-- Select " + allDelFields[idx] + " --</option>");
                        }
                        else
                        {
                            pw.println("        <option value=\"-1\">-- Select " + allDelLabels[idx] + " --</option>");
                        }
                    }
                    if (formBean.getHdnchkDeleteSrc()[idx].equals("true"))
                    {
                        if (formBean.getHdnrdoDeleteDataSrc()[idx].equals("Query") || formBean.getHdnrdoDeleteDataSrc()[idx].equals("WebService") || formBean.getHdnrdoDeleteDataSrc()[idx].equals("CommonCmb"))
                        {
                            if ("None".equals(allDelFields[idx]))
                            {
                                pw.println("        <c:forEach items=\"${" + allDelFields[idx].toLowerCase(Locale.getDefault()) + controlId + "s}\" var=\"" + allDelFields[idx].toLowerCase(Locale.getDefault()) + controlId + "\">");
                            }
                            else
                            {
                                pw.println("        <c:forEach items=\"${" + allDelFields[idx].toLowerCase(Locale.getDefault()) + "s}\" var=\"" + allDelFields[idx].toLowerCase(Locale.getDefault()) + "\">");
                            }
                            if (formBean.getHdnrdoDeleteDataSrc()[idx].equals("Query"))
                            {
                                String[] cols;
                                cols = manager.getColumnsOfQuery(formBean, formBean.getHdntxtDeleteSrcQuery()[idx]);
                                if (isDate)
                                {
                                    if ("None".equals(allDelFields[idx]))
                                    {
                                        pw.println("            <option value=\"<fmt:formatDate pattern=\"dd-MM-yyyy\" value=\"${" + allDelFields[idx].toLowerCase(Locale.getDefault()) + controlId + "." + cols[0] + "}\"></fmt:formatDate>\">");
                                        pw.println("                <fmt:formatDate pattern=\"dd-MM-yyyy\" value=\"${" + allDelFields[idx].toLowerCase(Locale.getDefault()) + controlId + "." + cols[1] + "}\"></fmt:formatDate>");
                                        pw.println("            </option>");
                                    }
                                    else
                                    {
                                        pw.println("            <option value=\"<fmt:formatDate pattern=\"dd-MM-yyyy\" value=\"${" + allDelFields[idx].toLowerCase(Locale.getDefault()) + "." + cols[0] + "}\"></fmt:formatDate>\">");
                                        pw.println("                <fmt:formatDate pattern=\"dd-MM-yyyy\" value=\"${" + allDelFields[idx].toLowerCase(Locale.getDefault()) + "." + cols[1] + "}\"></fmt:formatDate>");
                                        pw.println("            </option>");
                                    }
                                }
                                else
                                {
                                    if ("None".equals(allDelFields[idx]))
                                    {
                                        pw.println("            <option value=\"${" + allDelFields[idx].toLowerCase(Locale.getDefault()) + controlId + "." + cols[0] + "}\">${" + allDelFields[idx].toLowerCase(Locale.getDefault()) + controlId + "." + cols[1] + "}</option>");
                                    }
                                    else
                                    {
                                        pw.println("            <option value=\"${" + allDelFields[idx].toLowerCase(Locale.getDefault()) + "." + cols[0] + "}\">${" + allDelFields[idx].toLowerCase(Locale.getDefault()) + "." + cols[1] + "}</option>");
                                    }
                                }
                            }
                            else if (formBean.getHdnrdoDeleteDataSrc()[idx].equals("CommonCmb"))
                            {
                                if ("None".equals(allDelFields[idx]))
                                {
                                    pw.println("            <option value=\"${" + allDelFields[idx].toLowerCase(Locale.getDefault()) + controlId + ".KEY}\">${" + allDelFields[idx].toLowerCase(Locale.getDefault()) + controlId + ".VALUE}</option>");
                                }
                                else
                                {
                                    pw.println("            <option value=\"${" + allDelFields[idx].toLowerCase(Locale.getDefault()) + ".KEY}\">${" + allDelFields[idx].toLowerCase(Locale.getDefault()) + ".VALUE}</option>");
                                }
                            }
                            else
                            {
                                String cmbVal, cmbText;
                                cmbVal = formBean.getHdntxtDeleteWsCmbValue()[idx];
                                cmbText = formBean.getHdntxtDeleteWsCmbText()[idx];
                                if ("None".equals(allDelFields[idx]))
                                {
                                    pw.println("            <option value=\"${" + allDelFields[idx].toLowerCase(Locale.getDefault()) + controlId + "." + cmbVal + "}\">${" + allDelFields[idx].toLowerCase(Locale.getDefault()) + controlId + "." + cmbText + "}</option>");
                                }
                                else
                                {
                                    pw.println("            <option value=\"${" + allDelFields[idx].toLowerCase(Locale.getDefault()) + "." + cmbVal + "}\">${" + allDelFields[idx].toLowerCase(Locale.getDefault()) + "." + cmbText + "}</option>");
                                }
                            }
                            pw.println("        </c:forEach>");
                        }
                        else if (formBean.getHdnrdoDeleteDataSrc()[idx].equals("Static"))
                        {
                            String[] vals;
                            vals = formBean.getHdntxtDeleteSrcStatic()[idx].split(";");
                            for (int j = 0; j < vals.length; j++)
                            {
                                String[] key;
                                key = vals[j].split(",");
                                pw.println("        <option value=\"" + key[0].trim() + "\">" + key[1].trim() + "</option>");
                            }
                        }
                    }
                    pw.println("    </c:when>");
                }
                pw.println("</c:choose>");
            }
        }
        else
        {
            pw.println("<div class=\"menu_caption_bg\">");
            pw.println("    <div class=\"menu_caption_text\">Delete</div>");
            pw.println("</div>");
            pw.println();
            pw.println("<div class=\"report_content\">");
            pw.println("    <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"2\" class=\"menu_subcaption\" width=\"50%\">");
            pw.println("        <tr>");
            pw.println("            <td align=\"center\">");
            pw.println("                <input class=\"button\" type=\"button\" id=\"btnApply\" name=\"btnApply\" Value=\"Apply\" onclick=\"javascript: displayDeleteMasterGrid()\" >");
            pw.println("            </td>");
            pw.println("        </tr>");
            pw.println("    </table>");
            pw.println("    <div id=\"divMsg\" style=\"display: none\">");
            pw.println("        <input type=\"hidden\" id=\"hdnDbMsg\" name=\"hdnDbMsg\" value=\"${DBoperation}\" >");
            pw.println("    </div>");
            pw.println("</div>");
        }
        pw.close();
    }

    private String callMe(final String str, final String field)
    {
        if ("-1".equals(field) || "".equals(field))
        {
            return "";
        }
        else
        {
            return str + field + "\" ";
        }
    }
}
