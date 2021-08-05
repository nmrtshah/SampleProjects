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
public class AddJSPGenerator
{

    public void generateAddJSP(final FinMasterFormBean formBean) throws FileNotFoundException
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
        pw = new PrintWriter(projectPath + "add.jsp");

        String[] mstAddFields;
        mstAddFields = formBean.getHdnAddField();
        String[] mstAddLabels;
        mstAddLabels = formBean.getHdntxtAddLabel();
        int mstAddLen;
        mstAddLen = mstAddFields.length;
        String[] mstAddControls;
        mstAddControls = formBean.getHdnAddControl();
        boolean[] mstAddSel;
        mstAddSel = formBean.getHdnChkShowAdd();
        boolean[] mstEditSel;
        mstEditSel = formBean.getHdnChkShowEdit();
        boolean[] mstDelSel;
        mstDelSel = formBean.getHdnChkShowDel();
        String[] mstAddMandtry;
        mstAddMandtry = formBean.getHdnchkAddMandatory();

        int[] tabIndex = new int[mstAddLen];
        String[] tabIndexes;
        tabIndexes = formBean.getHdnAddTabIndex();
        int tabIdxLen;
        tabIdxLen = tabIndex.length;
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

        String[] allColumns;
        allColumns = formBean.getHdnMstAllColumns();
        String[] allDataTypes;
        allDataTypes = formBean.getHdnMstAllDataTypes();
        int allColLen;
        allColLen = allColumns.length;

        FinMasterManager manager;
        manager = new FinMasterManager();

        int k;
        boolean isFmt = false;
        boolean isCmnFile = false;
        for (int i = 0; i < tabIdxLen; i++)
        {
            for (k = 0; k < allColLen; k++)
            {
                if (mstAddFields[tabIndex[i]].equals(allColumns[k]) && allDataTypes[k].equals("Date") && (mstAddControls[tabIndex[i]].equals("ComboBox") || mstAddControls[tabIndex[i]].equals("TextLikeCombo")) && (formBean.getHdnrdoAddDataSrc()[tabIndex[i]].equals("Query") || formBean.getHdnrdoAddDataSrc()[tabIndex[i]].equals("CommonCmb")))
                {
                    isFmt = true;
                }
                else if (mstAddControls[tabIndex[i]].equals("FileBox"))
                {
                    isCmnFile = true;
                }
            }
        }

        pw.println("<%-- TCIGBF --%>");
        pw.println();
        pw.println("<%@taglib prefix=\"c\" uri=\"http://java.sun.com/jsp/jstl/core\" %>");
        if (isFmt)
        {
            pw.println("<%@taglib prefix=\"fmt\" uri=\"http://java.sun.com/jsp/jstl/fmt\" %>");
        }
        if (isCmnFile)
        {
            pw.println("<%@taglib prefix=\"common\" uri=\"http://www.njtechnologies.in/tags/filebox\" %>");
        }
        pw.println();

        String[] mstAddOnchanges;
        mstAddOnchanges = formBean.getHdncmbAddOnchange();
        String subproc = "";
        String subprocIdx = "";
        for (int j = 0; j < mstAddOnchanges.length; j++)
        {
            if (mstAddOnchanges[j] != null && !"".equals(mstAddOnchanges[j]) && !"-1".equals(mstAddOnchanges[j]))
            {
                subproc += " fill" + formBean.getHdntxtAddId()[j];
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
        pw.println("    <div id=\"divAddCaption\" class=\"menu_caption_text\">Add</div>");
        pw.println("</div>");
        pw.println("<div class=\"report_content\">");

        int counter = 0;
        boolean flag = false;
        boolean isDate;
        String controlId, controlName, ctrlPos;
        ctrlPos = formBean.getCmbAddControlPos();

        if (Integer.parseInt(ctrlPos) == 1)
        {
            pw.println("    <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"2\" class=\"menu_subcaption\" width=\"50%\">");
        }
        else if (Integer.parseInt(ctrlPos) == 2)
        {
            pw.println("    <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"2\" class=\"menu_subcaption\" width=\"90%\">");
        }
        pw.println("        <tr>");

        for (int i = 0; i < mstAddLen; i++)
        {
            counter++;
            if (counter > Integer.parseInt(ctrlPos))
            {
                pw.println("        </tr>");
                pw.println("        <tr>");
                counter = 1;
            }
            if (mstAddFields[tabIndex[i]].equals(formBean.getCmbMasterTablePrimKey()))
            {
                flag = true;
            }
            StringBuilder tdLabel;
            tdLabel = new StringBuilder();
            StringBuilder tdControl;
            tdControl = new StringBuilder();

            if ("1".equals(ctrlPos))
            {
                tdLabel.append("<td class=\"report_content_caption\"");
                tdControl.append("<td class=\"report_content_value\"");
            }
            else
            {
                tdLabel.append("<td class=\"report_content_text\"");
                tdControl.append("<td class=\"report_content_form\"");
            }

            if (!mstAddSel[tabIndex[i]])
            {
                tdLabel.append(" <c:if test=\"${masterTask eq 'add'}\">style=\"display: none\"</c:if>");
                tdControl.append(" <c:if test=\"${masterTask eq 'add'}\">style=\"display: none\"</c:if>");
            }
            if (!mstEditSel[tabIndex[i]])
            {
                tdLabel.append(" <c:if test=\"${masterTask eq 'edit'}\">style=\"display: none\"</c:if>");
                tdControl.append(" <c:if test=\"${masterTask eq 'edit'}\">style=\"display: none\"</c:if>");
            }
            if (formBean.isChkDelete() && !mstDelSel[tabIndex[i]])
            {
                tdLabel.append(" <c:if test=\"${masterTask eq 'delete'}\">style=\"display: none\"</c:if>");
                tdControl.append(" <c:if test=\"${masterTask eq 'delete'}\">style=\"display: none\"</c:if>");
            }
            pw.println("            " + tdLabel + ">");

            StringBuilder strMndtry;
            strMndtry = new StringBuilder();
            if (mstAddMandtry[tabIndex[i]].equals("true"))
            {
                strMndtry.append("<span class=\"astriek\"> *</span>");
            }
            if (mstAddLabels[tabIndex[i]].equals(""))
            {
                pw.println("                " + mstAddFields[tabIndex[i]] + strMndtry + " : ");
            }
            else
            {
                pw.println("                " + mstAddLabels[tabIndex[i]] + strMndtry + " : ");
            }
            pw.println("            </td>");
            pw.println("            " + tdControl + ">");
            isDate = false;
            for (k = 0; k < allColLen; k++)
            {
                if (mstAddFields[tabIndex[i]].equals(allColumns[k]) && allDataTypes[k].equals("Date"))
                {
                    isDate = true;
                    break;
                }
            }

            controlId = formBean.getHdntxtAddId()[tabIndex[i]];
            controlName = formBean.getHdntxtAddName()[tabIndex[i]];

            if (mstAddControls[tabIndex[i]].equals("ComboBox") || mstAddControls[tabIndex[i]].equals("TextLikeCombo"))
            {
                if (mstAddOnchanges[tabIndex[i]] != null && !mstAddOnchanges[tabIndex[i]].equals("") && !mstAddOnchanges[tabIndex[i]].equals("-1"))
                {
                    pw.println("                <input type=\"hidden\" id=\"fintxt" + controlId + "\" name=\"fintxt" + controlId + "\" value=\"${" + mstAddFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + "}\" />");
                }
                String openTag;
                openTag = "<select ";
                String param;
                param = "id=\"" + controlId + "\" name=\"" + controlName + "\" " + callMe("class=\"", formBean.getHdntxtAddClass()[tabIndex[i]]) + callMe("style=\"", formBean.getHdntxtAddStyle()[tabIndex[i]]) + callMe("tabindex=\"", tabIndexes[i]) + callMe("size=\"", formBean.getHdntxtAddSize()[tabIndex[i]]);
                String onchange = "";
                for (int j = 0; j < mstAddOnchanges.length; j++)
                {
                    if (controlId.equals(mstAddOnchanges[j]))
                    {
                        onchange += " fill" + formBean.getHdntxtAddId()[j] + "onchange" + controlId + "();";
                    }
                }
                if (!"".equals(onchange))
                {
                    onchange = "onchange=\"javascript:" + onchange + "\"";
                }
                if (formBean.getHdnrbtnAddMultiple()[tabIndex[i]].equals("true"))
                {
                    pw.println("                " + openTag + "multiple " + param + onchange + ">");
                }
                else
                {
                    pw.println("                " + openTag + param + onchange + ">");
                    if (mstAddLabels[tabIndex[i]].equals(""))
                    {
                        pw.println("                    <option value=\"-1\">-- Select " + mstAddFields[tabIndex[i]] + " --</option>");
                    }
                    else
                    {
                        pw.println("                    <option value=\"-1\">-- Select " + mstAddLabels[tabIndex[i]] + " --</option>");
                    }
                }
                if (formBean.getHdnchkAddSrc()[tabIndex[i]].equals("true"))
                {
                    if (formBean.getHdnrdoAddDataSrc()[tabIndex[i]].equals("Query") || formBean.getHdnrdoAddDataSrc()[tabIndex[i]].equals("WebService") || formBean.getHdnrdoAddDataSrc()[tabIndex[i]].equals("CommonCmb"))
                    {
                        if ("None".equals(mstAddFields[tabIndex[i]]))
                        {
                            pw.println("                    <c:forEach items=\"${" + mstAddFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + controlId + "s}\" var=\"var" + mstAddFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + controlId + "\">");
                        }
                        else
                        {
                            pw.println("                    <c:forEach items=\"${" + mstAddFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + "s}\" var=\"var" + mstAddFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + "\">");
                        }
                        if (formBean.getHdnrdoAddDataSrc()[tabIndex[i]].equals("Query"))
                        {
                            String[] cols;
                            cols = manager.getColumnsOfQuery(formBean, formBean.getHdntxtAddSrcQuery()[tabIndex[i]]);
                            if (isDate)
                            {
                                if ("None".equals(mstAddFields[tabIndex[i]]))
                                {
                                    pw.println("                        <option value=\"<fmt:formatDate pattern=\"dd-MM-yyyy\" value=\"${var" + mstAddFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + controlId + "." + cols[0] + "}\"></fmt:formatDate>\">");
                                    pw.println("                            <fmt:formatDate pattern=\"dd-MM-yyyy\" value=\"${var" + mstAddFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + controlId + "." + cols[1] + "}\"></fmt:formatDate>");
                                    pw.println("                        </option>");
                                }
                                else
                                {
                                    pw.println("                        <fmt:formatDate pattern=\"dd-MM-yyyy\" value=\"${var" + mstAddFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + "." + cols[0] + "}\" var=\"temp" + mstAddFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + "\"></fmt:formatDate>");
                                    pw.println("                        <c:if test=\"${" + mstAddFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + " eq temp" + mstAddFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + "}\">");
                                    pw.println("                            <option value=\"<fmt:formatDate pattern=\"dd-MM-yyyy\" value=\"${var" + mstAddFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + "." + cols[0] + "}\"></fmt:formatDate>\" selected=\"true\">");
                                    pw.println("                                <fmt:formatDate pattern=\"dd-MM-yyyy\" value=\"${var" + mstAddFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + "." + cols[1] + "}\"></fmt:formatDate>");
                                    pw.println("                            </option>");
                                    pw.println("                        </c:if>");
                                    pw.println("                        <c:if test=\"${" + mstAddFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + " ne temp" + mstAddFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + "}\">");
                                    pw.println("                            <option value=\"<fmt:formatDate pattern=\"dd-MM-yyyy\" value=\"${var" + mstAddFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + "." + cols[0] + "}\"></fmt:formatDate>\">");
                                    pw.println("                                <fmt:formatDate pattern=\"dd-MM-yyyy\" value=\"${var" + mstAddFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + "." + cols[1] + "}\"></fmt:formatDate>");
                                    pw.println("                            </option>");
                                    pw.println("                        </c:if>");
                                }
                            }
                            else
                            {
                                if ("None".equals(mstAddFields[tabIndex[i]]))
                                {
                                    pw.println("                        <option value=\"${var" + mstAddFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + controlId + "." + cols[0] + "}\">${var" + mstAddFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + controlId + "." + cols[1] + "}</option>");
                                }
                                else
                                {
                                    pw.println("                        <c:if test=\"${" + mstAddFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + " eq var" + mstAddFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + "." + cols[0] + "}\">");
                                    pw.println("                            <option value=\"${var" + mstAddFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + "." + cols[0] + "}\" selected=\"true\">${var" + mstAddFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + "." + cols[1] + "}</option>");
                                    pw.println("                        </c:if>");
                                    pw.println("                        <c:if test=\"${" + mstAddFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + " ne var" + mstAddFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + "." + cols[0] + "}\">");
                                    pw.println("                            <option value=\"${var" + mstAddFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + "." + cols[0] + "}\">${var" + mstAddFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + "." + cols[1] + "}</option>");
                                    pw.println("                        </c:if>");
                                }
                            }
                        }
                        else if (formBean.getHdnrdoAddDataSrc()[tabIndex[i]].equals("CommonCmb"))
                        {
                            if ("None".equals(mstAddFields[tabIndex[i]]))
                            {
                                pw.println("                            <option value=\"${var" + mstAddFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + controlId + ".KEY}\">${var" + mstAddFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + controlId + ".VALUE}</option>");
                            }
                            else
                            {
                                pw.println("                        <c:if test=\"${" + mstAddFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + " eq var" + mstAddFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + ".KEY}\">");
                                pw.println("                            <option value=\"${var" + mstAddFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + ".KEY}\" selected=\"true\">${var" + mstAddFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + ".VALUE}</option>");
                                pw.println("                        </c:if>");
                                pw.println("                        <c:if test=\"${" + mstAddFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + " ne var" + mstAddFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + ".KEY}\">");
                                pw.println("                            <option value=\"${var" + mstAddFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + ".KEY}\">${var" + mstAddFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + ".VALUE}</option>");
                                pw.println("                        </c:if>");
                            }
                        }
                        else
                        {
                            String cmbVal, cmbText;
                            cmbVal = formBean.getHdntxtAddWsCmbValue()[tabIndex[i]];
                            cmbText = formBean.getHdntxtAddWsCmbText()[tabIndex[i]];
                            if ("None".equals(mstAddFields[tabIndex[i]]))
                            {
                                pw.println("                        <option value=\"${var" + mstAddFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + controlId + "." + cmbVal + "}\">${var" + mstAddFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + controlId + "." + cmbText + "}</option>");
                            }
                            else
                            {
                                pw.println("                        <c:if test=\"${" + mstAddFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + " eq var" + mstAddFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + "." + cmbVal + "}\">");
                                pw.println("                            <option value=\"${var" + mstAddFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + "." + cmbVal + "}\" selected=\"true\">${var" + mstAddFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + "." + cmbText + "}</option>");
                                pw.println("                        </c:if>");
                                pw.println("                        <c:if test=\"${" + mstAddFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + " ne var" + mstAddFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + "." + cmbVal + "}\">");
                                pw.println("                            <option value=\"${var" + mstAddFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + "." + cmbVal + "}\">${var" + mstAddFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + "." + cmbText + "}</option>");
                                pw.println("                        </c:if>");
                            }
                        }
                        pw.println("                    </c:forEach>");
                    }
                    else if (formBean.getHdnrdoAddDataSrc()[tabIndex[i]].equals("Static"))
                    {
                        String[] vals;
                        vals = formBean.getHdntxtAddSrcStatic()[tabIndex[i]].split(";");
                        for (int j = 0; j < vals.length; j++)
                        {
                            String[] key;
                            key = vals[j].split(",");
                            pw.println("                    <c:if test=\"${" + mstAddFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + " eq '" + key[0].trim() + "'}\">");
                            pw.println("                        <option value=\"" + key[0].trim() + "\" selected=\"true\">" + key[1].trim() + "</option>");
                            pw.println("                    </c:if>");
                            pw.println("                    <c:if test=\"${" + mstAddFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + " ne '" + key[0].trim() + "'}\">");
                            pw.println("                        <option value=\"" + key[0].trim() + "\">" + key[1].trim() + "</option>");
                            pw.println("                    </c:if>");
                        }
                    }
                }
                pw.println("                </select>");
            }
            else if (mstAddControls[tabIndex[i]].equals("TextBox"))
            {
                String format = "";
                if (isDate)
                {
                    format = "[DD-MM-YYYY]";
                }
                String openTag;
                openTag = "<input type=\"text\" ";
                String read = "";
                String param, value;
                param = "id=\"" + controlId + "\" name=\"" + controlName + "\" " + callMe("align=\"", formBean.getHdncmbAddAlign()[tabIndex[i]]) + callMe("maxlength=\"", formBean.getHdntxtAddMaxLength()[tabIndex[i]]) + callMe("class=\"", formBean.getHdntxtAddClass()[tabIndex[i]]) + callMe("style=\"", formBean.getHdntxtAddStyle()[tabIndex[i]]) + callMe("tabindex=\"", tabIndexes[i]) + callMe("size=\"", formBean.getHdntxtAddSize()[tabIndex[i]]) + "value=\"${" + mstAddFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + "}\">";
                if (formBean.getHdnrbtnAddReadonly()[tabIndex[i]].equals("true"))
                {
                    read = "readonly ";
                }
                if (!formBean.getHdntxtAddValue()[tabIndex[i]].equals(""))
                {
                    value = formBean.getHdntxtAddValue()[tabIndex[i]];
                    pw.println("                <c:if test=\"${" + mstAddFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + " eq null}\">");
                    pw.println("                    <c:set var=\"" + mstAddFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + "\" value=\"" + value + "\"></c:set>");
                    pw.println("                </c:if>");
                }
                pw.println("                " + openTag + read + param + format);
            }
            else if (mstAddControls[tabIndex[i]].equals("Radio"))
            {
                String openTag, param, check;
                openTag = "<input type=\"radio\" ";
                final int total = Integer.parseInt(formBean.getHdntxtAddTotalRdo()[tabIndex[i]]);
                String[] allCap, allVal;
                allCap = formBean.getHdntxtAddRdoCap()[tabIndex[i]].split("/");
                allVal = formBean.getHdntxtAddRdoVal()[tabIndex[i]].split("/");
                for (int j = 0; j < total; j++)
                {
                    if (!formBean.getHdntxtAddRdoDefVal()[tabIndex[i]].equals("") && Integer.parseInt(formBean.getHdntxtAddRdoDefVal()[tabIndex[i]]) == (j + 1))
                    {
                        check = "<c:if test=\"${" + mstAddFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + " eq '" + allVal[j] + "' or param.cmdAction eq 'addLoader'}\">checked</c:if> ";
                    }
                    else
                    {
                        check = "<c:if test=\"${" + mstAddFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + " eq '" + allVal[j] + "'}\">checked</c:if> ";
                    }
                    param = "id=\"" + controlId + allVal[j] + "\" class=\"radio\" name=\"" + controlName + "\" " + callMe("class=\"", formBean.getHdntxtAddClass()[tabIndex[i]]) + callMe("style=\"", formBean.getHdntxtAddStyle()[tabIndex[i]]) + check + callMe("tabindex=\"", tabIndexes[i]) + callMe("size=\"", formBean.getHdntxtAddSize()[tabIndex[i]]) + "value=\"" + allVal[j] + "\">";
                    pw.println("                " + openTag + param);
                    pw.println("                <label> " + allCap[j] + " </label>");
                }
            }
            else if (mstAddControls[tabIndex[i]].equals("CheckBox"))
            {
                String openTag, param, check;
                openTag = "<input type=\"checkbox\" id=\"" + controlId + "\" name=\"" + controlName + "\" ";
                param = callMe("class=\"", formBean.getHdntxtAddClass()[tabIndex[i]]) + callMe("style=\"", formBean.getHdntxtAddStyle()[tabIndex[i]]) + callMe("tabindex=\"", tabIndexes[i]) + callMe("size=\"", formBean.getHdntxtAddSize()[tabIndex[i]]) + ">";
                String boolTrue = "1";
                String boolFalse = "0";
                if (formBean.getHdnrbtnAddValueFormat()[tabIndex[i]].equals("yesno"))
                {
                    boolTrue = "YES";
                    boolFalse = "NO";
                }
                else if (formBean.getHdnrbtnAddValueFormat()[tabIndex[i]].equals("yn"))
                {
                    boolTrue = "Y";
                    boolFalse = "N";
                }
                if (formBean.getHdnrbtnAddChecked()[tabIndex[i]].equals("true"))
                {
                    if ("1".equals(boolTrue))
                    {
                        check = "<c:if test=\"${" + mstAddFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + " ne '" + boolFalse + "' and " + mstAddFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + " ne 'false'}\">checked</c:if> ";
                    }
                    else
                    {
                        check = "<c:if test=\"${" + mstAddFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + " ne '" + boolFalse + "'}\">checked</c:if> ";
                    }
                }
                else
                {
                    if ("1".equals(boolTrue))
                    {
                        check = "<c:if test=\"${" + mstAddFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + " eq '" + boolTrue + "' or " + mstAddFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + " eq 'true'}\">checked</c:if> ";
                    }
                    else
                    {
                        check = "<c:if test=\"${" + mstAddFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + " eq '" + boolTrue + "'}\">checked</c:if> ";
                    }
                }
                pw.println("                " + openTag + check + param);
            }
            else if (mstAddControls[tabIndex[i]].equals("DatePicker"))
            {
                pw.println("                <input type=\"text\" class=\"datepickerclass\" id=\"" + controlId + "\" name=\"" + controlName + "\" value=\"${" + mstAddFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + "}\" " + callMe("tabindex=\"", tabIndexes[i]) + ">");
            }
            else if (mstAddControls[tabIndex[i]].equals("Password"))
            {
                String format = "";
                if (isDate)
                {
                    format = "[DD-MM-YYYY]";
                }
                String openTag, param, value;
                openTag = "<input type=\"password\" ";
                String read = "";
                param = "id=\"" + controlId + "\" name=\"" + controlName + "\" " + callMe("align=\"", formBean.getHdncmbAddAlign()[tabIndex[i]]) + callMe("maxlength=\"", formBean.getHdntxtAddMaxLength()[tabIndex[i]]) + callMe("class=\"", formBean.getHdntxtAddClass()[tabIndex[i]]) + callMe("style=\"", formBean.getHdntxtAddStyle()[tabIndex[i]]) + callMe("tabindex=\"", tabIndexes[i]) + callMe("size=\"", formBean.getHdntxtAddSize()[tabIndex[i]]) + "value=\"${" + mstAddFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + "}\" >";
                if (formBean.getHdnrbtnAddReadonly()[tabIndex[i]].equals("true"))
                {
                    read = "readonly ";
                }
                if (!formBean.getHdntxtAddValue()[tabIndex[i]].equals(""))
                {
                    value = formBean.getHdntxtAddValue()[tabIndex[i]];
                    pw.println("                <c:if test=\"${" + mstAddFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + " eq null}\">");
                    pw.println("                    <c:set var=\"" + mstAddFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + "\" value=\"" + value + "\"></c:set>");
                    pw.println("                </c:if>");
                }
                pw.println("                " + openTag + read + param + format);
            }
            else if (mstAddControls[tabIndex[i]].equals("TextArea"))
            {
                String format = "";
                if (isDate)
                {
                    format = "[DD-MM-YYYY]";
                }
                StringBuilder row;
                row = new StringBuilder();
                StringBuilder col;
                col = new StringBuilder();
                if (formBean.getHdntxtAddRows()[tabIndex[i]] == null || formBean.getHdntxtAddRows()[tabIndex[i]].equals(""))
                {
                    row.append("rows=\"2\"");
                }
                else
                {
                    row.append("rows=\"");
                    row.append(formBean.getHdntxtAddRows()[tabIndex[i]]);
                    row.append("\"");
                }
                if (formBean.getHdntxtAddCols()[tabIndex[i]] == null || formBean.getHdntxtAddCols()[tabIndex[i]].equals(""))
                {
                    col.append("cols=\"2\"");
                }
                else
                {
                    col.append("cols=\"");
                    col.append(formBean.getHdntxtAddCols()[tabIndex[i]]);
                    col.append("\"");
                }
                pw.println("                <textarea " + row + " " + col + " id=\"" + controlId + "\" name=\"" + controlName + "\" " + callMe("class=\"", formBean.getHdntxtAddClass()[tabIndex[i]]) + callMe("style=\"", formBean.getHdntxtAddStyle()[tabIndex[i]]) + callMe("tabindex=\"", tabIndexes[i]) + ">${" + mstAddFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + "}</textarea>" + format);
            }
            else if (mstAddControls[tabIndex[i]].equals("File"))
            {
                pw.println("                <input type=\"file\" id=\"" + controlId + "\" name=\"" + controlName + "\" class=\"input_brows\" " + callMe("tabindex=\"", tabIndexes[i]) + ">");
            }
            else if (mstAddControls[tabIndex[i]].equals("FileBox"))
            {
                String formName;
                formName = formBean.getTxtModuleName().toLowerCase(Locale.getDefault()) + "Form";
                pw.println("                <common:filebox controlid=\"" + controlId + "\" name=\"" + controlName + "\" " + callMe("maxsize=\"", formBean.getHdntxtAddMaxsize()[tabIndex[i]]) + callMe("maxfiles=\"", formBean.getHdntxtAddMaxfiles()[tabIndex[i]]) + callMe("value=\"", formBean.getHdntxtAddDispTxt()[tabIndex[i]]) + "elementname=\"" + formBean.getHdntxtAddEleName()[tabIndex[i]] + "\" formid=\"" + formName + "\" type=\"" + formBean.getHdntxtAddType()[tabIndex[i]] + "\" " + callMe("onremovecallback=\"", formBean.getHdntxtAddOnremoveCall()[tabIndex[i]]) + "/>");
            }
            pw.println("            </td>");
        }

        pw.println("        </tr>");
        pw.println("        <tr>");
        pw.println("            <td align=\"center\" id=\"tdAddButtons\" colspan=\"" + Integer.parseInt(ctrlPos) * 2 + "\">");
        pw.println("                <input class=\"button\" type=\"button\" id=\"btnAdd\" name=\"btnAdd\" Value=\"Add\" onclick=\"javascript: validateData();\" >");
        pw.println("                <input class=\"button\" type=\"button\" id=\"btnReset\" name=\"btnReset\" Value=\"Reset\" onclick=\"javascript: resetCheck();\" >");
        pw.println("            </td>");
        pw.println("        </tr>");
        pw.println("    </table>");
        if (!flag)
        {
            pw.println("    <input type=\"hidden\" id=\"" + formBean.getCmbMasterTablePrimKey().toLowerCase(Locale.getDefault()) + "PrimeKey\" name=\"" + formBean.getCmbMasterTablePrimKey().toLowerCase(Locale.getDefault()) + "PrimeKey\" value=\"${" + formBean.getCmbMasterTablePrimKey().toLowerCase(Locale.getDefault()) + "}\" >");
        }
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
                    if (mstAddFields[idx].equals(allColumns[k]) && allDataTypes[k].equals("Date"))
                    {
                        isDate = true;
                        break;
                    }
                }

                controlId = formBean.getHdntxtAddId()[idx];

                if (!formBean.getHdnrbtnAddMultiple()[idx].equals("true"))
                {
                    if (mstAddLabels[idx].equals(""))
                    {
                        pw.println("        <option value=\"-1\">-- Select " + mstAddFields[idx] + " --</option>");
                    }
                    else
                    {
                        pw.println("        <option value=\"-1\">-- Select " + mstAddLabels[idx] + " --</option>");
                    }
                }
                if (formBean.getHdnchkAddSrc()[idx].equals("true"))
                {
                    if (formBean.getHdnrdoAddDataSrc()[idx].equals("Query") || formBean.getHdnrdoAddDataSrc()[idx].equals("WebService") || formBean.getHdnrdoAddDataSrc()[idx].equals("CommonCmb"))
                    {
                        if ("None".equals(mstAddFields[idx]))
                        {
                            pw.println("        <c:forEach items=\"${" + mstAddFields[idx].toLowerCase(Locale.getDefault()) + controlId + "s}\" var=\"var" + mstAddFields[idx].toLowerCase(Locale.getDefault()) + controlId + "\">");
                        }
                        else
                        {
                            pw.println("        <c:forEach items=\"${" + mstAddFields[idx].toLowerCase(Locale.getDefault()) + "s}\" var=\"var" + mstAddFields[idx].toLowerCase(Locale.getDefault()) + "\">");
                        }
                        if (formBean.getHdnrdoAddDataSrc()[idx].equals("Query"))
                        {
                            String[] cols;
                            cols = manager.getColumnsOfQuery(formBean, formBean.getHdntxtAddSrcQuery()[idx]);
                            if (isDate)
                            {
                                if ("None".equals(mstAddFields[idx]))
                                {
                                    pw.println("            <option value=\"<fmt:formatDate pattern=\"dd-MM-yyyy\" value=\"${var" + mstAddFields[idx].toLowerCase(Locale.getDefault()) + controlId + "." + cols[0] + "}\"></fmt:formatDate>\">");
                                    pw.println("                <fmt:formatDate pattern=\"dd-MM-yyyy\" value=\"${var" + mstAddFields[idx].toLowerCase(Locale.getDefault()) + controlId + "." + cols[1] + "}\"></fmt:formatDate>");
                                    pw.println("            </option>");
                                }
                                else
                                {
                                    pw.println("            <fmt:formatDate pattern=\"dd-MM-yyyy\" value=\"${var" + mstAddFields[idx].toLowerCase(Locale.getDefault()) + "." + cols[0] + "}\" var=\"temp" + mstAddFields[idx].toLowerCase(Locale.getDefault()) + "\"></fmt:formatDate>");
                                    pw.println("            <c:if test=\"${" + mstAddFields[idx].toLowerCase(Locale.getDefault()) + " eq temp" + mstAddFields[idx].toLowerCase(Locale.getDefault()) + "}\">");
                                    pw.println("                <option value=\"<fmt:formatDate pattern=\"dd-MM-yyyy\" value=\"${var" + mstAddFields[idx].toLowerCase(Locale.getDefault()) + "." + cols[0] + "}\"></fmt:formatDate>\" selected=\"true\">");
                                    pw.println("                    <fmt:formatDate pattern=\"dd-MM-yyyy\" value=\"${var" + mstAddFields[idx].toLowerCase(Locale.getDefault()) + "." + cols[1] + "}\"></fmt:formatDate>");
                                    pw.println("                </option>");
                                    pw.println("            </c:if>");
                                    pw.println("            <c:if test=\"${" + mstAddFields[idx].toLowerCase(Locale.getDefault()) + " ne temp" + mstAddFields[idx].toLowerCase(Locale.getDefault()) + "}\">");
                                    pw.println("                <option value=\"<fmt:formatDate pattern=\"dd-MM-yyyy\" value=\"${var" + mstAddFields[idx].toLowerCase(Locale.getDefault()) + "." + cols[0] + "}\"></fmt:formatDate>\">");
                                    pw.println("                    <fmt:formatDate pattern=\"dd-MM-yyyy\" value=\"${var" + mstAddFields[idx].toLowerCase(Locale.getDefault()) + "." + cols[1] + "}\"></fmt:formatDate>");
                                    pw.println("                </option>");
                                    pw.println("            </c:if>");
                                }
                            }
                            else
                            {
                                if ("None".equals(mstAddFields[idx]))
                                {
                                    pw.println("            <option value=\"${var" + mstAddFields[idx].toLowerCase(Locale.getDefault()) + controlId + "." + cols[0] + "}\">${var" + mstAddFields[idx].toLowerCase(Locale.getDefault()) + controlId + "." + cols[1] + "}</option>");
                                }
                                else
                                {
                                    pw.println("            <c:if test=\"${" + mstAddFields[idx].toLowerCase(Locale.getDefault()) + " eq var" + mstAddFields[idx].toLowerCase(Locale.getDefault()) + "." + cols[0] + "}\">");
                                    pw.println("                <option value=\"${var" + mstAddFields[idx].toLowerCase(Locale.getDefault()) + "." + cols[0] + "}\" selected=\"true\">${var" + mstAddFields[idx].toLowerCase(Locale.getDefault()) + "." + cols[1] + "}</option>");
                                    pw.println("            </c:if>");
                                    pw.println("            <c:if test=\"${" + mstAddFields[idx].toLowerCase(Locale.getDefault()) + " ne var" + mstAddFields[idx].toLowerCase(Locale.getDefault()) + "." + cols[0] + "}\">");
                                    pw.println("                <option value=\"${var" + mstAddFields[idx].toLowerCase(Locale.getDefault()) + "." + cols[0] + "}\">${var" + mstAddFields[idx].toLowerCase(Locale.getDefault()) + "." + cols[1] + "}</option>");
                                    pw.println("            </c:if>");
                                }
                            }
                        }
                        else if (formBean.getHdnrdoAddDataSrc()[idx].equals("CommonCmb"))
                        {
                            if ("None".equals(mstAddFields[idx]))
                            {
                                pw.println("                <option value=\"${var" + mstAddFields[idx].toLowerCase(Locale.getDefault()) + controlId + ".KEY}\">${var" + mstAddFields[idx].toLowerCase(Locale.getDefault()) + controlId + ".VALUE}</option>");
                            }
                            else
                            {
                                pw.println("            <c:if test=\"${" + mstAddFields[idx].toLowerCase(Locale.getDefault()) + " eq var" + mstAddFields[idx].toLowerCase(Locale.getDefault()) + ".KEY}\">");
                                pw.println("                <option value=\"${var" + mstAddFields[idx].toLowerCase(Locale.getDefault()) + ".KEY}\" selected=\"true\">${var" + mstAddFields[idx].toLowerCase(Locale.getDefault()) + ".VALUE}</option>");
                                pw.println("            </c:if>");
                                pw.println("            <c:if test=\"${" + mstAddFields[idx].toLowerCase(Locale.getDefault()) + " ne var" + mstAddFields[idx].toLowerCase(Locale.getDefault()) + ".KEY}\">");
                                pw.println("                <option value=\"${var" + mstAddFields[idx].toLowerCase(Locale.getDefault()) + ".KEY}\">${var" + mstAddFields[idx].toLowerCase(Locale.getDefault()) + ".VALUE}</option>");
                                pw.println("            </c:if>");
                            }
                        }
                        else
                        {
                            String cmbVal, cmbText;
                            cmbVal = formBean.getHdntxtAddWsCmbValue()[idx];
                            cmbText = formBean.getHdntxtAddWsCmbText()[idx];
                            if ("None".equals(mstAddFields[idx]))
                            {
                                pw.println("            <option value=\"${var" + mstAddFields[idx].toLowerCase(Locale.getDefault()) + controlId + "." + cmbVal + "}\">${var" + mstAddFields[idx].toLowerCase(Locale.getDefault()) + controlId + "." + cmbText + "}</option>");
                            }
                            else
                            {
                                pw.println("            <c:if test=\"${" + mstAddFields[idx].toLowerCase(Locale.getDefault()) + " eq var" + mstAddFields[idx].toLowerCase(Locale.getDefault()) + "." + cmbVal + "}\">");
                                pw.println("                <option value=\"${var" + mstAddFields[idx].toLowerCase(Locale.getDefault()) + "." + cmbVal + "}\" selected=\"true\">${var" + mstAddFields[idx].toLowerCase(Locale.getDefault()) + "." + cmbText + "}</option>");
                                pw.println("            </c:if>");
                                pw.println("            <c:if test=\"${" + mstAddFields[idx].toLowerCase(Locale.getDefault()) + " ne var" + mstAddFields[idx].toLowerCase(Locale.getDefault()) + "." + cmbVal + "}\">");
                                pw.println("                <option value=\"${var" + mstAddFields[idx].toLowerCase(Locale.getDefault()) + "." + cmbVal + "}\">${var" + mstAddFields[idx].toLowerCase(Locale.getDefault()) + "." + cmbText + "}</option>");
                                pw.println("            </c:if>");
                            }
                        }
                        pw.println("        </c:forEach>");
                    }
                    else if (formBean.getHdnrdoAddDataSrc()[idx].equals("Static"))
                    {
                        String[] vals;
                        vals = formBean.getHdntxtAddSrcStatic()[idx].split(";");
                        for (int j = 0; j < vals.length; j++)
                        {
                            String[] key;
                            key = vals[j].split(",");
                            pw.println("        <c:if test=\"${" + mstAddFields[idx].toLowerCase(Locale.getDefault()) + " eq '" + key[0].trim() + "'}\">");
                            pw.println("            <option value=\"" + key[0].trim() + "\" selected=\"true\">" + key[1].trim() + "</option>");
                            pw.println("        </c:if>");
                            pw.println("        <c:if test=\"${" + mstAddFields[idx].toLowerCase(Locale.getDefault()) + " ne '" + key[0].trim() + "'}\">");
                            pw.println("            <option value=\"" + key[0].trim() + "\">" + key[1].trim() + "</option>");
                            pw.println("        </c:if>");
                        }
                    }
                }
                pw.println("    </c:when>");
            }
            pw.println("</c:choose>");
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
