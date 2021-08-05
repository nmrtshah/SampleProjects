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
public class EditJSPGenerator
{

    public void generateEditJSP(final FinMasterFormBean formBean) throws FileNotFoundException
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
        pw = new PrintWriter(projectPath + "edit.jsp");

        pw.println("<%-- TCIGBF --%>");
        pw.println();

        if (formBean.getHdnEditField() != null)
        {
            String[] allEditFields;
            allEditFields = formBean.getHdnEditField();
            String[] allEditControls;
            allEditControls = formBean.getHdnEditControl();
            String[] allEditLabels;
            allEditLabels = formBean.getHdntxtEditLabel();
            String[] allEditMandtry;
            allEditMandtry = formBean.getHdnchkEditMandatory();

            int[] tabIndex = new int[formBean.getHdnEditTabIndex().length];
            String[] tabIndexes;
            tabIndexes = formBean.getHdnEditTabIndex();
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
                    if ((allEditControls[tabIndex[i]].equals("ComboBox") || allEditControls[tabIndex[i]].equals("TextLikeCombo")) && (formBean.getHdnrdoEditDataSrc()[tabIndex[i]].equals("Query") || formBean.getHdnrdoEditDataSrc()[tabIndex[i]].equals("WebService") || formBean.getHdnrdoEditDataSrc()[tabIndex[i]].equals("CommonCmb")))
                    {
                        isC = true;
                        if (allEditFields[tabIndex[i]].equals(allColumns[k]) && allDataTypes[k].equals("Date"))
                        {
                            isFmt = true;
                        }
                    }
                    else if (allEditControls[tabIndex[i]].equals("FileBox"))
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

            String[] allEditOnchanges;
            allEditOnchanges = formBean.getHdncmbEditOnchange();
            String subproc = "";
            String subprocIdx = "";
            for (int j = 0; j < allEditOnchanges.length; j++)
            {
                if (allEditOnchanges[j] != null && !"".equals(allEditOnchanges[j]) && !"-1".equals(allEditOnchanges[j]))
                {
                    subproc += " fill" + formBean.getHdntxtEditId()[j];
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
            pw.println("    <div class=\"menu_caption_text\">Edit</div>");
            pw.println("</div>");
            pw.println();
            pw.println("<div class=\"report_content\">");

            int counter = 0;
            boolean isDate;
            String controlId, controlName, ctrlPos;
            ctrlPos = formBean.getCmbEditControlPos();

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
                if (allEditMandtry[tabIndex[i]].equals("true"))
                {
                    strMndtry.append("<span class=\"astriek\"> *</span>");
                }
                if (allEditLabels[tabIndex[i]].equals(""))
                {
                    pw.println("                " + allEditFields[tabIndex[i]] + strMndtry + " : ");
                }
                else
                {
                    pw.println("                " + allEditLabels[tabIndex[i]] + strMndtry + " : ");
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
                    if (allEditFields[tabIndex[i]].equals(allColumns[k]) && allDataTypes[k].equals("Date"))
                    {
                        isDate = true;
                        break;
                    }
                }

                controlId = formBean.getHdntxtEditId()[tabIndex[i]];
                controlName = formBean.getHdntxtEditName()[tabIndex[i]];

                if (allEditControls[tabIndex[i]].equals("ComboBox") || allEditControls[tabIndex[i]].equals("TextLikeCombo"))
                {
                    String onchange = "";
                    for (int j = 0; j < allEditOnchanges.length; j++)
                    {
                        if (controlId.equals(allEditOnchanges[j]))
                        {
                            onchange += " fill" + formBean.getHdntxtEditId()[j] + "onchange" + controlId + "();";
                        }
                    }
                    if (!"".equals(onchange))
                    {
                        onchange = "onchange=\"javascript:" + onchange + "\"";
                    }
                    if (formBean.getHdnrbtnEditMultiple()[tabIndex[i]].equals("true"))
                    {
                        pw.println("                <select multiple id=\"" + controlId + "\" name=\"" + controlName + "\" " + callMe("class=\"", formBean.getHdntxtEditClass()[tabIndex[i]]) + callMe("style=\"", formBean.getHdntxtEditStyle()[tabIndex[i]]) + callMe("tabindex=\"", tabIndexes[i]) + callMe("size=\"", formBean.getHdntxtEditSize()[tabIndex[i]]) + onchange + ">");
                    }
                    else
                    {
                        pw.println("                <select id=\"" + controlId + "\" name=\"" + controlName + "\" " + callMe("class=\"", formBean.getHdntxtEditClass()[tabIndex[i]]) + callMe("style=\"", formBean.getHdntxtEditStyle()[tabIndex[i]]) + callMe("tabindex=\"", tabIndexes[i]) + callMe("size=\"", formBean.getHdntxtEditSize()[tabIndex[i]]) + onchange + ">");
                        if (allEditLabels[tabIndex[i]].equals(""))
                        {
                            pw.println("                    <option value=\"-1\">-- Select " + allEditFields[tabIndex[i]] + " --</option>");
                        }
                        else
                        {
                            pw.println("                    <option value=\"-1\">-- Select " + allEditLabels[tabIndex[i]] + " --</option>");
                        }
                    }
                    if (formBean.getHdnchkEditSrc()[tabIndex[i]].equals("true"))
                    {
                        if (formBean.getHdnrdoEditDataSrc()[tabIndex[i]].equals("Query") || formBean.getHdnrdoEditDataSrc()[tabIndex[i]].equals("WebService") || formBean.getHdnrdoEditDataSrc()[tabIndex[i]].equals("CommonCmb"))
                        {
                            if ("None".equals(allEditFields[tabIndex[i]]))
                            {
                                pw.println("                    <c:forEach items=\"${" + allEditFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + controlId + "s}\" var=\"" + allEditFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + controlId + "\">");
                            }
                            else
                            {
                                pw.println("                    <c:forEach items=\"${" + allEditFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + "s}\" var=\"" + allEditFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + "\">");
                            }
                            if (formBean.getHdnrdoEditDataSrc()[tabIndex[i]].equals("Query"))
                            {
                                String[] cols;
                                cols = manager.getColumnsOfQuery(formBean, formBean.getHdntxtEditSrcQuery()[tabIndex[i]]);
                                if (isDate)
                                {
                                    if ("None".equals(allEditFields[tabIndex[i]]))
                                    {
                                        pw.println("                        <option value=\"<fmt:formatDate pattern=\"dd-MM-yyyy\" value=\"${" + allEditFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + controlId + "." + cols[0] + "}\"></fmt:formatDate>\">");
                                        pw.println("                            <fmt:formatDate pattern=\"dd-MM-yyyy\" value=\"${" + allEditFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + controlId + "." + cols[1] + "}\"></fmt:formatDate>");
                                        pw.println("                        </option>");
                                    }
                                    else
                                    {
                                        pw.println("                        <option value=\"<fmt:formatDate pattern=\"dd-MM-yyyy\" value=\"${" + allEditFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + "." + cols[0] + "}\"></fmt:formatDate>\">");
                                        pw.println("                            <fmt:formatDate pattern=\"dd-MM-yyyy\" value=\"${" + allEditFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + "." + cols[1] + "}\"></fmt:formatDate>");
                                        pw.println("                        </option>");
                                    }
                                }
                                else
                                {
                                    if ("None".equals(allEditFields[tabIndex[i]]))
                                    {
                                        pw.println("                        <option value=\"${" + allEditFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + controlId + "." + cols[0] + "}\">${" + allEditFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + controlId + "." + cols[1] + "}</option>");
                                    }
                                    else
                                    {
                                        pw.println("                        <option value=\"${" + allEditFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + "." + cols[0] + "}\">${" + allEditFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + "." + cols[1] + "}</option>");
                                    }
                                }
                            }
                            else if (formBean.getHdnrdoEditDataSrc()[tabIndex[i]].equals("CommonCmb"))
                            {
                                if ("None".equals(allEditFields[tabIndex[i]]))
                                {
                                    pw.println("                        <option value=\"${" + allEditFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + controlId + ".KEY}\">${" + allEditFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + controlId + ".VALUE}</option>");
                                }
                                else
                                {
                                    pw.println("                        <option value=\"${" + allEditFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + ".KEY}\">${" + allEditFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + ".VALUE}</option>");
                                }
                            }
                            else
                            {
                                String cmbVal, cmbText;
                                cmbVal = formBean.getHdntxtEditWsCmbValue()[tabIndex[i]];
                                cmbText = formBean.getHdntxtEditWsCmbText()[tabIndex[i]];
                                if ("None".equals(allEditFields[tabIndex[i]]))
                                {
                                    pw.println("                        <option value=\"${" + allEditFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + controlId + "." + cmbVal + "}\">${" + allEditFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + controlId + "." + cmbText + "}</option>");
                                }
                                else
                                {
                                    pw.println("                        <option value=\"${" + allEditFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + "." + cmbVal + "}\">${" + allEditFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + "." + cmbText + "}</option>");
                                }
                            }
                            pw.println("                    </c:forEach>");
                        }
                        else if (formBean.getHdnrdoEditDataSrc()[tabIndex[i]].equals("Static"))
                        {
                            String[] vals;
                            vals = formBean.getHdntxtEditSrcStatic()[tabIndex[i]].split(";");
                            for (int j = 0; j < vals.length; j++)
                            {
                                String[] key;
                                key = vals[j].split(",");
                                pw.println("                    <option value=\"" + key[0].trim() + "\">" + key[1].trim() + "</option>");
                            }
                        }
                    }
                    pw.println("                </select>");
                }
                else if (allEditControls[tabIndex[i]].equals("TextBox"))
                {
                    String format = "";
                    if (isDate)
                    {
                        format = "[DD-MM-YYYY]";
                    }
                    if (formBean.getHdnrbtnEditReadonly()[tabIndex[i]].equals("true"))
                    {
                        pw.println("                <input type=\"text\" readonly id=\"" + controlId + "\" name=\"" + controlName + "\" " + callMe("value=\"", formBean.getHdntxtEditValue()[tabIndex[i]]) + callMe("align=\"", formBean.getHdncmbEditAlign()[tabIndex[i]]) + callMe("maxlength=\"", formBean.getHdntxtEditMaxLength()[tabIndex[i]]) + callMe("class=\"", formBean.getHdntxtEditClass()[tabIndex[i]]) + callMe("style=\"", formBean.getHdntxtEditStyle()[tabIndex[i]]) + callMe("tabindex=\"", tabIndexes[i]) + callMe("size=\"", formBean.getHdntxtEditSize()[tabIndex[i]]) + ">" + format);
                    }
                    else
                    {
                        pw.println("                <input type=\"text\" id=\"" + controlId + "\" name=\"" + controlName + "\" " + callMe("value=\"", formBean.getHdntxtEditValue()[tabIndex[i]]) + callMe("align=\"", formBean.getHdncmbEditAlign()[tabIndex[i]]) + callMe("maxlength=\"", formBean.getHdntxtEditMaxLength()[tabIndex[i]]) + callMe("class=\"", formBean.getHdntxtEditClass()[tabIndex[i]]) + callMe("style=\"", formBean.getHdntxtEditStyle()[tabIndex[i]]) + callMe("tabindex=\"", tabIndexes[i]) + callMe("size=\"", formBean.getHdntxtEditSize()[tabIndex[i]]) + ">" + format);
                    }
                }
                else if (allEditControls[tabIndex[i]].equals("Radio"))
                {
                    int total;
                    total = Integer.parseInt(formBean.getHdntxtEditTotalRdo()[tabIndex[i]]);
                    String[] allCap, allVal;
                    allCap = formBean.getHdntxtEditRdoCap()[tabIndex[i]].split("/");
                    allVal = formBean.getHdntxtEditRdoVal()[tabIndex[i]].split("/");
                    String openTag, param, check;
                    openTag = "<input type=\"radio\" ";
                    for (int j = 0; j < total; j++)
                    {
                        if (!formBean.getHdntxtEditRdoDefVal()[tabIndex[i]].equals("") && Integer.parseInt(formBean.getHdntxtEditRdoDefVal()[tabIndex[i]]) == (j + 1))
                        {
                            check = "checked ";
                        }
                        else
                        {
                            check = "";
                        }
                        param = "id=\"" + controlId + allVal[j] + "\" class=\"radio\" name=\"" + controlName + "\" " + callMe("class=\"", formBean.getHdntxtEditClass()[tabIndex[i]]) + check + callMe("style=\"", formBean.getHdntxtEditStyle()[tabIndex[i]]) + callMe("tabindex=\"", tabIndexes[i]) + callMe("size=\"", formBean.getHdntxtEditSize()[tabIndex[i]]) + "value=\"" + allVal[j] + "\" >";
                        pw.println("                " + openTag + param);
                        pw.println("                <label> " + allCap[j] + " </label>");
                    }
                }
                else if (allEditControls[tabIndex[i]].equals("CheckBox"))
                {
                    if (formBean.getHdnrbtnEditChecked()[tabIndex[i]].equals("true"))
                    {
                        pw.println("                <input type=\"checkbox\" id=\"" + controlId + "\" name=\"" + controlName + "\" checked=\"true\" " + callMe("class=\"", formBean.getHdntxtEditClass()[tabIndex[i]]) + callMe("style=\"", formBean.getHdntxtEditStyle()[tabIndex[i]]) + callMe("tabindex=\"", tabIndexes[i]) + callMe("size=\"", formBean.getHdntxtEditSize()[tabIndex[i]]) + ">");
                    }
                    else
                    {
                        pw.println("                <input type=\"checkbox\" id=\"" + controlId + "\" name=\"" + controlName + "\" " + callMe("class=\"", formBean.getHdntxtEditClass()[tabIndex[i]]) + callMe("style=\"", formBean.getHdntxtEditStyle()[tabIndex[i]]) + callMe("tabindex=\"", tabIndexes[i]) + callMe("size=\"", formBean.getHdntxtEditSize()[tabIndex[i]]) + ">");
                    }
                }
                else if (allEditControls[tabIndex[i]].equals("DatePicker"))
                {
                    pw.println("                <input type=\"text\" class=\"datepickerclass\" id=\"" + controlId + "\" name=\"" + controlName + "\" value=\"${" + allEditFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + "}\" " + callMe("tabindex=\"", tabIndexes[i]) + ">");
                }
                else if (allEditControls[tabIndex[i]].equals("Password"))
                {
                    String format = "";
                    if (isDate)
                    {
                        format = "[DD-MM-YYYY]";
                    }
                    if (formBean.getHdnrbtnEditReadonly()[tabIndex[i]].equals("true"))
                    {
                        pw.println("                <input type=\"password\" readonly id=\"" + controlId + "\" name=\"" + controlName + "\" " + callMe("value=\"", formBean.getHdntxtEditValue()[tabIndex[i]]) + callMe("align=\"", formBean.getHdncmbEditAlign()[tabIndex[i]]) + callMe("maxlength=\"", formBean.getHdntxtEditMaxLength()[tabIndex[i]]) + callMe("class=\"", formBean.getHdntxtEditClass()[tabIndex[i]]) + callMe("style=\"", formBean.getHdntxtEditStyle()[tabIndex[i]]) + callMe("tabindex=\"", tabIndexes[i]) + callMe("size=\"", formBean.getHdntxtEditSize()[tabIndex[i]]) + ">" + format);
                    }
                    else
                    {
                        pw.println("                <input type=\"password\" id=\"" + controlId + "\" name=\"" + controlName + "\" " + callMe("value=\"", formBean.getHdntxtEditValue()[tabIndex[i]]) + callMe("align=\"", formBean.getHdncmbEditAlign()[tabIndex[i]]) + callMe("maxlength=\"", formBean.getHdntxtEditMaxLength()[tabIndex[i]]) + callMe("class=\"", formBean.getHdntxtEditClass()[tabIndex[i]]) + callMe("style=\"", formBean.getHdntxtEditStyle()[tabIndex[i]]) + callMe("tabindex=\"", tabIndexes[i]) + callMe("size=\"", formBean.getHdntxtEditSize()[tabIndex[i]]) + ">" + format);
                    }
                }
                else if (allEditControls[tabIndex[i]].equals("TextArea"))
                {
                    StringBuilder row;
                    row = new StringBuilder();
                    StringBuilder col;
                    col = new StringBuilder();
                    if (formBean.getHdntxtEditRows()[tabIndex[i]] == null || formBean.getHdntxtEditRows()[tabIndex[i]].equals(""))
                    {
                        row.append("rows=\"2\"");
                    }
                    else
                    {
                        row.append("rows=\"");
                        row.append(formBean.getHdntxtEditRows()[tabIndex[i]]);
                        row.append("\"");
                    }
                    if (formBean.getHdntxtEditCols()[tabIndex[i]] == null || formBean.getHdntxtEditCols()[tabIndex[i]].equals(""))
                    {
                        col.append("cols=\"2\"");
                    }
                    else
                    {
                        col.append("cols=\"");
                        col.append(formBean.getHdntxtEditCols()[tabIndex[i]]);
                        col.append("\"");
                    }
                    String format = "";
                    if (isDate)
                    {
                        format = "[DD-MM-YYYY]";
                    }
                    pw.println("                <textarea " + row + " " + col + " id=\"" + controlId + "\" name=\"" + controlName + "\" " + callMe("class=\"", formBean.getHdntxtEditClass()[tabIndex[i]]) + callMe("style=\"", formBean.getHdntxtEditStyle()[tabIndex[i]]) + callMe("tabindex=\"", tabIndexes[i]) + "></textarea>" + format);
                }
                else if (allEditControls[tabIndex[i]].equals("File"))
                {
                    pw.println("        <input type=\"file\" id=\"" + controlId + "\" name=\"" + controlName + "\" class=\"input_brows\"" + callMe("tabindex=\"", tabIndexes[i]) + ">");
                }
                else if (allEditControls[tabIndex[i]].equals("FileBox"))
                {
                    String formName;
                    formName = formBean.getTxtModuleName().toLowerCase(Locale.getDefault()) + "Form";
                    pw.println("                <common:filebox controlid=\"" + controlId + "\" name=\"" + controlName + "\" " + callMe("maxsize=\"", formBean.getHdntxtEditMaxsize()[tabIndex[i]]) + callMe("maxfiles=\"", formBean.getHdntxtEditMaxfiles()[tabIndex[i]]) + callMe("value=\"", formBean.getHdntxtEditDispTxt()[tabIndex[i]]) + "elementname=\"" + formBean.getHdntxtEditEleName()[tabIndex[i]] + "\" formid=\"" + formName + "\" type=\"" + formBean.getHdntxtEditType()[tabIndex[i]] + "\" " + callMe("onremovecallback=\"", formBean.getHdntxtEditOnremoveCall()[tabIndex[i]]) + "/>");
                }
                pw.println("            </td>");
            }

            pw.println("        </tr>");
            pw.println("        <tr>");
            pw.println("            <td align=\"center\" colspan=\"" + Integer.parseInt(ctrlPos) * 2 + "\">");
            pw.println("                <input class=\"button\" type=\"button\" id=\"btnApply\" name=\"btnApply\" Value=\"Apply\" onclick=\"javascript: displayEditMasterGrid()\" >");
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
                        if (allEditFields[idx].equals(allColumns[k]) && allDataTypes[k].equals("Date"))
                        {
                            isDate = true;
                            break;
                        }
                    }

                    controlId = formBean.getHdntxtEditId()[idx];

                    if (!formBean.getHdnrbtnEditMultiple()[idx].equals("true"))
                    {
                        if (allEditLabels[idx].equals(""))
                        {
                            pw.println("        <option value=\"-1\">-- Select " + allEditFields[idx] + " --</option>");
                        }
                        else
                        {
                            pw.println("        <option value=\"-1\">-- Select " + allEditLabels[idx] + " --</option>");
                        }
                    }
                    if (formBean.getHdnchkEditSrc()[idx].equals("true"))
                    {
                        if (formBean.getHdnrdoEditDataSrc()[idx].equals("Query") || formBean.getHdnrdoEditDataSrc()[idx].equals("WebService") || formBean.getHdnrdoEditDataSrc()[idx].equals("CommonCmb"))
                        {
                            if ("None".equals(allEditFields[idx]))
                            {
                                pw.println("        <c:forEach items=\"${" + allEditFields[idx].toLowerCase(Locale.getDefault()) + controlId + "s}\" var=\"" + allEditFields[idx].toLowerCase(Locale.getDefault()) + controlId + "\">");
                            }
                            else
                            {
                                pw.println("        <c:forEach items=\"${" + allEditFields[idx].toLowerCase(Locale.getDefault()) + "s}\" var=\"" + allEditFields[idx].toLowerCase(Locale.getDefault()) + "\">");
                            }
                            if (formBean.getHdnrdoEditDataSrc()[idx].equals("Query"))
                            {
                                String[] cols;
                                cols = manager.getColumnsOfQuery(formBean, formBean.getHdntxtEditSrcQuery()[idx]);
                                if (isDate)
                                {
                                    if ("None".equals(allEditFields[idx]))
                                    {
                                        pw.println("            <option value=\"<fmt:formatDate pattern=\"dd-MM-yyyy\" value=\"${" + allEditFields[idx].toLowerCase(Locale.getDefault()) + controlId + "." + cols[0] + "}\"></fmt:formatDate>\">");
                                        pw.println("                <fmt:formatDate pattern=\"dd-MM-yyyy\" value=\"${" + allEditFields[idx].toLowerCase(Locale.getDefault()) + controlId + "." + cols[1] + "}\"></fmt:formatDate>");
                                        pw.println("            </option>");
                                    }
                                    else
                                    {
                                        pw.println("            <option value=\"<fmt:formatDate pattern=\"dd-MM-yyyy\" value=\"${" + allEditFields[idx].toLowerCase(Locale.getDefault()) + "." + cols[0] + "}\"></fmt:formatDate>\">");
                                        pw.println("                <fmt:formatDate pattern=\"dd-MM-yyyy\" value=\"${" + allEditFields[idx].toLowerCase(Locale.getDefault()) + "." + cols[1] + "}\"></fmt:formatDate>");
                                        pw.println("            </option>");
                                    }
                                }
                                else
                                {
                                    if ("None".equals(allEditFields[idx]))
                                    {
                                        pw.println("            <option value=\"${" + allEditFields[idx].toLowerCase(Locale.getDefault()) + controlId + "." + cols[0] + "}\">${" + allEditFields[idx].toLowerCase(Locale.getDefault()) + controlId + "." + cols[1] + "}</option>");
                                    }
                                    else
                                    {
                                        pw.println("            <option value=\"${" + allEditFields[idx].toLowerCase(Locale.getDefault()) + "." + cols[0] + "}\">${" + allEditFields[idx].toLowerCase(Locale.getDefault()) + "." + cols[1] + "}</option>");
                                    }
                                }
                            }
                            else if (formBean.getHdnrdoEditDataSrc()[idx].equals("CommonCmb"))
                            {
                                if ("None".equals(allEditFields[idx]))
                                {
                                    pw.println("            <option value=\"${" + allEditFields[idx].toLowerCase(Locale.getDefault()) + controlId + ".KEY}\">${" + allEditFields[idx].toLowerCase(Locale.getDefault()) + controlId + ".VALUE}</option>");
                                }
                                else
                                {
                                    pw.println("            <option value=\"${" + allEditFields[idx].toLowerCase(Locale.getDefault()) + ".KEY}\">${" + allEditFields[idx].toLowerCase(Locale.getDefault()) + ".VALUE}</option>");
                                }
                            }
                            else
                            {
                                String cmbVal, cmbText;
                                cmbVal = formBean.getHdntxtEditWsCmbValue()[idx];
                                cmbText = formBean.getHdntxtEditWsCmbText()[idx];
                                if ("None".equals(allEditFields[idx]))
                                {
                                    pw.println("            <option value=\"${" + allEditFields[idx].toLowerCase(Locale.getDefault()) + controlId + "." + cmbVal + "}\">${" + allEditFields[idx].toLowerCase(Locale.getDefault()) + controlId + "." + cmbText + "}</option>");
                                }
                                else
                                {
                                    pw.println("            <option value=\"${" + allEditFields[idx].toLowerCase(Locale.getDefault()) + "." + cmbVal + "}\">${" + allEditFields[idx].toLowerCase(Locale.getDefault()) + "." + cmbText + "}</option>");
                                }
                            }
                            pw.println("        </c:forEach>");
                        }
                        else if (formBean.getHdnrdoEditDataSrc()[idx].equals("Static"))
                        {
                            String[] vals;
                            vals = formBean.getHdntxtEditSrcStatic()[idx].split(";");
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
            pw.println("    <div class=\"menu_caption_text\">Edit</div>");
            pw.println("</div>");
            pw.println();
            pw.println("<div class=\"report_content\">");
            pw.println("    <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"2\" class=\"menu_subcaption\" width=\"50%\">");
            pw.println("        <tr>");
            pw.println("            <td align=\"center\">");
            pw.println("                <input class=\"button\" type=\"button\" id=\"btnApply\" name=\"btnApply\" Value=\"Apply\" onclick=\"javascript: displayEditMasterGrid()\" >");
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
