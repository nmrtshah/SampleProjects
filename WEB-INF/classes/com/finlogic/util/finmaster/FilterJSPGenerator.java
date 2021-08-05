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
public class FilterJSPGenerator
{

    public void generateFilterJSP(final FinMasterFormBean formBean) throws FileNotFoundException
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
        pw = new PrintWriter(projectPath + "filter.jsp");

        pw.println("<%-- TCIGBF --%>");
        pw.println();

        if (formBean.getHdnViewField() != null)
        {
            String[] allViewFields;
            allViewFields = formBean.getHdnViewField();
            String[] allViewControls;
            allViewControls = formBean.getHdnViewControl();
            String[] allViewLabels;
            allViewLabels = formBean.getHdntxtViewLabel();
            String[] allViewMandtry;
            allViewMandtry = formBean.getHdnchkViewMandatory();

            int[] tabIndex = new int[formBean.getHdnViewTabIndex().length];
            String[] tabIndexes;
            tabIndexes = formBean.getHdnViewTabIndex();
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
                    if ((allViewControls[tabIndex[i]].equals("ComboBox") || allViewControls[tabIndex[i]].equals("TextLikeCombo")) && (formBean.getHdnrdoViewDataSrc()[tabIndex[i]].equals("Query") || formBean.getHdnrdoViewDataSrc()[tabIndex[i]].equals("WebService") || formBean.getHdnrdoViewDataSrc()[tabIndex[i]].equals("CommonCmb")))
                    {
                        isC = true;
                        if (allViewFields[tabIndex[i]].equals(allColumns[k]) && allDataTypes[k].equals("Date"))
                        {
                            isFmt = true;
                        }
                    }
                    else if (allViewControls[tabIndex[i]].equals("FileBox"))
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

            String[] allViewOnchanges;
            allViewOnchanges = formBean.getHdncmbViewOnchange();
            String subproc = "";
            String subprocIdx = "";
            for (int j = 0; j < allViewOnchanges.length; j++)
            {
                if (allViewOnchanges[j] != null && !"".equals(allViewOnchanges[j]) && !"-1".equals(allViewOnchanges[j]))
                {
                    subproc += " fill" + formBean.getHdntxtViewId()[j];
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
            pw.println("    <div class=\"menu_caption_text\">Filter</div>");
            pw.println("</div>");
            pw.println("<div class=\"report_content\">");

            int counter = 0;
            boolean isDate;
            String controlId, controlName, ctrlPos;
            ctrlPos = formBean.getCmbViewControlPos();

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
                if (allViewMandtry[tabIndex[i]].equals("true"))
                {
                    strMndtry.append("<span class=\"astriek\"> *</span>");
                }
                if (allViewLabels[tabIndex[i]].equals(""))
                {
                    pw.println("                " + allViewFields[tabIndex[i]] + strMndtry + " : ");
                }
                else
                {
                    pw.println("                " + allViewLabels[tabIndex[i]] + strMndtry + " : ");
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
                    if (allViewFields[tabIndex[i]].equals(allColumns[k]) && allDataTypes[k].equals("Date"))
                    {
                        isDate = true;
                        break;
                    }
                }

                controlId = formBean.getHdntxtViewId()[tabIndex[i]];
                controlName = formBean.getHdntxtViewName()[tabIndex[i]];

                if (allViewControls[tabIndex[i]].equals("ComboBox") || allViewControls[tabIndex[i]].equals("TextLikeCombo"))
                {
                    String onchange = "";
                    for (int j = 0; j < allViewOnchanges.length; j++)
                    {
                        if (controlId.equals(allViewOnchanges[j]))
                        {
                            onchange += " fill" + formBean.getHdntxtViewId()[j] + "onchange" + controlId + "();";
                        }
                    }
                    if (!"".equals(onchange))
                    {
                        onchange = "onchange=\"javascript:" + onchange + "\"";
                    }
                    if (formBean.getHdnrbtnViewMultiple()[tabIndex[i]].equals("true"))
                    {
                        pw.println("                <select multiple id=\"" + controlId + "\" name=\"" + controlName + "\" " + callMe("class=\"", formBean.getHdntxtViewClass()[tabIndex[i]]) + callMe("style=\"", formBean.getHdntxtViewStyle()[tabIndex[i]]) + callMe("tabindex=\"", tabIndexes[i]) + callMe("size=\"", formBean.getHdntxtViewSize()[tabIndex[i]]) + onchange + ">");
                    }
                    else
                    {
                        pw.println("                <select id=\"" + controlId + "\" name=\"" + controlName + "\" " + callMe("class=\"", formBean.getHdntxtViewClass()[tabIndex[i]]) + callMe("style=\"", formBean.getHdntxtViewStyle()[tabIndex[i]]) + callMe("tabindex=\"", tabIndexes[i]) + callMe("size=\"", formBean.getHdntxtViewSize()[tabIndex[i]]) + onchange + ">");
                        if (allViewLabels[tabIndex[i]].equals(""))
                        {
                            pw.println("                    <option value=\"-1\">-- Select " + allViewFields[tabIndex[i]] + " --</option>");
                        }
                        else
                        {
                            pw.println("                    <option value=\"-1\">-- Select " + allViewLabels[tabIndex[i]] + " --</option>");
                        }
                    }
                    if (formBean.getHdnchkViewSrc()[tabIndex[i]].equals("true"))
                    {
                        if (formBean.getHdnrdoViewDataSrc()[tabIndex[i]].equals("Query") || formBean.getHdnrdoViewDataSrc()[tabIndex[i]].equals("WebService") || formBean.getHdnrdoViewDataSrc()[tabIndex[i]].equals("CommonCmb"))
                        {
                            if ("None".equals(allViewFields[tabIndex[i]]))
                            {
                                pw.println("                    <c:forEach items=\"${" + allViewFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + controlId + "s}\" var=\"" + allViewFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + controlId + "\">");
                            }
                            else
                            {
                                pw.println("                    <c:forEach items=\"${" + allViewFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + "s}\" var=\"" + allViewFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + "\">");
                            }
                            if (formBean.getHdnrdoViewDataSrc()[tabIndex[i]].equals("Query"))
                            {
                                String[] cols;
                                cols = manager.getColumnsOfQuery(formBean, formBean.getHdntxtViewSrcQuery()[tabIndex[i]]);
                                if (isDate)
                                {
                                    if ("None".equals(allViewFields[tabIndex[i]]))
                                    {
                                        pw.println("                        <option value=\"<fmt:formatDate pattern=\"dd-MM-yyyy\" value=\"${" + allViewFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + controlId + "." + cols[0] + "}\"></fmt:formatDate>\">");
                                        pw.println("                            <fmt:formatDate pattern=\"dd-MM-yyyy\" value=\"${" + allViewFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + controlId + "." + cols[1] + "}\"></fmt:formatDate>");
                                        pw.println("                        </option>");
                                    }
                                    else
                                    {
                                        pw.println("                        <option value=\"<fmt:formatDate pattern=\"dd-MM-yyyy\" value=\"${" + allViewFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + "." + cols[0] + "}\"></fmt:formatDate>\">");
                                        pw.println("                            <fmt:formatDate pattern=\"dd-MM-yyyy\" value=\"${" + allViewFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + "." + cols[1] + "}\"></fmt:formatDate>");
                                        pw.println("                        </option>");
                                    }
                                }
                                else
                                {
                                    if ("None".equals(allViewFields[tabIndex[i]]))
                                    {
                                        pw.println("                        <option value=\"${" + allViewFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + controlId + "." + cols[0] + "}\">${" + allViewFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + controlId + "." + cols[1] + "}</option>");
                                    }
                                    else
                                    {
                                        pw.println("                        <option value=\"${" + allViewFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + "." + cols[0] + "}\">${" + allViewFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + "." + cols[1] + "}</option>");
                                    }
                                }
                            }
                            else if (formBean.getHdnrdoViewDataSrc()[tabIndex[i]].equals("CommonCmb"))
                            {
                                if ("None".equals(allViewFields[tabIndex[i]]))
                                {
                                    pw.println("                        <option value=\"${" + allViewFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + controlId + ".KEY}\">${" + allViewFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + controlId + ".VALUE}</option>");
                                }
                                else
                                {
                                    pw.println("                        <option value=\"${" + allViewFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + ".KEY}\">${" + allViewFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + ".VALUE}</option>");
                                }
                            }
                            else
                            {
                                String cmbVal, cmbText;
                                cmbVal = formBean.getHdntxtViewWsCmbValue()[tabIndex[i]];
                                cmbText = formBean.getHdntxtViewWsCmbText()[tabIndex[i]];
                                if ("None".equals(allViewFields[tabIndex[i]]))
                                {
                                    pw.println("                        <option value=\"${" + allViewFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + controlId + "." + cmbVal + "}\">${" + allViewFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + controlId + "." + cmbText + "}</option>");
                                }
                                else
                                {
                                    pw.println("                        <option value=\"${" + allViewFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + "." + cmbVal + "}\">${" + allViewFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + "." + cmbText + "}</option>");
                                }
                            }
                            pw.println("                    </c:forEach>");
                        }
                        else if (formBean.getHdnrdoViewDataSrc()[tabIndex[i]].equals("Static"))
                        {
                            String[] vals;
                            vals = formBean.getHdntxtViewSrcStatic()[tabIndex[i]].split(";");
                            for (int j = 0; j < vals.length; j++)
                            {
                                String[] key;
                                key = vals[j].split(",");
                                pw.println("                            <option value=\"" + key[0].trim() + "\">" + key[1].trim() + "</option>");
                            }
                        }
                    }
                    pw.println("                </select>");
                }
                else if (allViewControls[tabIndex[i]].equals("TextBox"))
                {
                    String format = "";
                    if (isDate)
                    {
                        format = "[DD-MM-YYYY]";
                    }
                    if (formBean.getHdnrbtnViewReadonly()[tabIndex[i]].equals("true"))
                    {
                        pw.println("                <input type=\"text\" readonly id=\"" + controlId + "\" name=\"" + controlName + "\" " + callMe("value=\"", formBean.getHdntxtViewValue()[tabIndex[i]]) + callMe("align=\"", formBean.getHdncmbViewAlign()[tabIndex[i]]) + callMe("maxlength=\"", formBean.getHdntxtViewMaxLength()[tabIndex[i]]) + callMe("class=\"", formBean.getHdntxtViewClass()[tabIndex[i]]) + callMe("style=\"", formBean.getHdntxtViewStyle()[tabIndex[i]]) + callMe("tabindex=\"", tabIndexes[i]) + callMe("size=\"", formBean.getHdntxtViewSize()[tabIndex[i]]) + ">" + format);
                    }
                    else
                    {
                        pw.println("                <input type=\"text\" id=\"" + controlId + "\" name=\"" + controlName + "\" " + callMe("value=\"", formBean.getHdntxtViewValue()[tabIndex[i]]) + callMe("align=\"", formBean.getHdncmbViewAlign()[tabIndex[i]]) + callMe("maxlength=\"", formBean.getHdntxtViewMaxLength()[tabIndex[i]]) + callMe("class=\"", formBean.getHdntxtViewClass()[tabIndex[i]]) + callMe("style=\"", formBean.getHdntxtViewStyle()[tabIndex[i]]) + callMe("tabindex=\"", tabIndexes[i]) + callMe("size=\"", formBean.getHdntxtViewSize()[tabIndex[i]]) + ">" + format);
                    }
                }
                else if (allViewControls[tabIndex[i]].equals("Radio"))
                {
                    int total;
                    total = Integer.parseInt(formBean.getHdntxtViewTotalRdo()[tabIndex[i]]);
                    String[] allCap, allVal;
                    allCap = formBean.getHdntxtViewRdoCap()[tabIndex[i]].split("/");
                    allVal = formBean.getHdntxtViewRdoVal()[tabIndex[i]].split("/");
                    String openTag, param, check;
                    openTag = "<input type=\"radio\" ";
                    for (int j = 0; j < total; j++)
                    {
                        if (!formBean.getHdntxtViewRdoDefVal()[tabIndex[i]].equals("") && Integer.parseInt(formBean.getHdntxtViewRdoDefVal()[tabIndex[i]]) == (j + 1))
                        {
                            check = "checked ";
                        }
                        else
                        {
                            check = "";
                        }
                        param = "id=\"" + controlId + allVal[j] + "\" class=\"radio\" name=\"" + controlName + "\" " + callMe("class=\"", formBean.getHdntxtViewClass()[tabIndex[i]]) + check + callMe("style=\"", formBean.getHdntxtViewStyle()[tabIndex[i]]) + callMe("tabindex=\"", tabIndexes[i]) + callMe("size=\"", formBean.getHdntxtViewSize()[tabIndex[i]]) + "value=\"" + allVal[j] + "\" >";
                        pw.println("                " + openTag + param);
                        pw.println("                <label> " + allCap[j] + " </label>");
                    }
                }
                else if (allViewControls[tabIndex[i]].equals("CheckBox"))
                {
                    if (formBean.getHdnrbtnViewChecked()[tabIndex[i]].equals("true"))
                    {
                        pw.println("                <input type=\"checkbox\" id=\"" + controlId + "\" name=\"" + controlName + "\" checked=\"true\" " + callMe("class=\"", formBean.getHdntxtViewClass()[tabIndex[i]]) + callMe("style=\"", formBean.getHdntxtViewStyle()[tabIndex[i]]) + callMe("tabindex=\"", tabIndexes[i]) + callMe("size=\"", formBean.getHdntxtViewSize()[tabIndex[i]]) + ">");
                    }
                    else
                    {
                        pw.println("                <input type=\"checkbox\" id=\"" + controlId + "\" name=\"" + controlName + "\" " + callMe("class=\"", formBean.getHdntxtViewClass()[tabIndex[i]]) + callMe("style=\"", formBean.getHdntxtViewStyle()[tabIndex[i]]) + callMe("tabindex=\"", tabIndexes[i]) + callMe("size=\"", formBean.getHdntxtViewSize()[tabIndex[i]]) + ">");
                    }
                }
                else if (allViewControls[tabIndex[i]].equals("DatePicker"))
                {
                    pw.println("                <input type=\"text\" class=\"datepickerclass\" id=\"" + controlId + "\" name=\"" + controlName + "\" value=\"${" + allViewFields[tabIndex[i]].toLowerCase(Locale.getDefault()) + "}\" " + callMe("tabindex=\"", tabIndexes[i]) + ">");
                }
                else if (allViewControls[tabIndex[i]].equals("Password"))
                {
                    String format = "";
                    if (isDate)
                    {
                        format = "[DD-MM-YYYY]";
                    }
                    pw.println("                <input type=\"password\" id=\"" + controlId + "\" name=\"" + controlName + "\" " + callMe("value=\"", formBean.getHdntxtViewValue()[tabIndex[i]]) + callMe("align=\"", formBean.getHdncmbViewAlign()[tabIndex[i]]) + callMe("maxlength=\"", formBean.getHdntxtViewMaxLength()[tabIndex[i]]) + callMe("class=\"", formBean.getHdntxtViewClass()[tabIndex[i]]) + callMe("style=\"", formBean.getHdntxtViewStyle()[tabIndex[i]]) + callMe("tabindex=\"", tabIndexes[i]) + callMe("size=\"", formBean.getHdntxtViewSize()[tabIndex[i]]) + ">" + format);
                }
                else if (allViewControls[tabIndex[i]].equals("TextArea"))
                {
                    StringBuilder row;
                    row = new StringBuilder();
                    StringBuilder col;
                    col = new StringBuilder();
                    if (formBean.getHdntxtViewRows()[tabIndex[i]] == null || formBean.getHdntxtViewRows()[tabIndex[i]].equals(""))
                    {
                        row.append("rows=\"2\"");
                    }
                    else
                    {
                        row.append("rows=\"");
                        row.append(formBean.getHdntxtViewRows()[tabIndex[i]]);
                        row.append("\"");
                    }
                    if (formBean.getHdntxtViewCols()[tabIndex[i]] == null || formBean.getHdntxtViewCols()[tabIndex[i]].equals(""))
                    {
                        col.append("cols=\"2\"");
                    }
                    else
                    {
                        col.append("cols=\"");
                        col.append(formBean.getHdntxtViewCols()[tabIndex[i]]);
                        col.append("\"");
                    }
                    String format = "";
                    if (isDate)
                    {
                        format = "[DD-MM-YYYY]";
                    }
                    pw.println("                <textarea " + row + " " + col + " id=\"" + controlId + "\" name=\"" + controlName + "\" " + callMe("class=\"", formBean.getHdntxtViewClass()[tabIndex[i]]) + callMe("style=\"", formBean.getHdntxtViewStyle()[tabIndex[i]]) + callMe("tabindex=\"", tabIndexes[i]) + "></textarea>" + format);
                }
                else if (allViewControls[tabIndex[i]].equals("File"))
                {
                    pw.println("                <input type=\"file\" id=\"" + controlId + "\" name=\"" + controlName + "\" class=\"input_brows\"" + callMe("tabindex=\"", tabIndexes[i]) + ">");
                }
                else if (allViewControls[tabIndex[i]].equals("FileBox"))
                {
                    String formName;
                    formName = formBean.getTxtModuleName().toLowerCase(Locale.getDefault()) + "Form";
                    pw.println("                <common:filebox controlid=\"" + controlId + "\" name=\"" + controlName + "\" " + callMe("maxsize=\"", formBean.getHdntxtViewMaxsize()[tabIndex[i]]) + callMe("maxfiles=\"", formBean.getHdntxtViewMaxfiles()[tabIndex[i]]) + callMe("value=\"", formBean.getHdntxtViewDispTxt()[tabIndex[i]]) + "elementname=\"" + formBean.getHdntxtViewEleName()[tabIndex[i]] + "\" formid=\"" + formName + "\" type=\"" + formBean.getHdntxtViewType()[tabIndex[i]] + "\" " + callMe("onremovecallback=\"", formBean.getHdntxtViewOnremoveCall()[tabIndex[i]]) + "/>");
                }
                pw.println("            </td>");
            }

            pw.println("        </tr>");
            pw.println("    </table>");
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
                        if (allViewFields[idx].equals(allColumns[k]) && allDataTypes[k].equals("Date"))
                        {
                            isDate = true;
                            break;
                        }
                    }

                    controlId = formBean.getHdntxtViewId()[idx];

                    if (!formBean.getHdnrbtnViewMultiple()[idx].equals("true"))
                    {
                        if (allViewLabels[idx].equals(""))
                        {
                            pw.println("        <option value=\"-1\">-- Select " + allViewFields[idx] + " --</option>");
                        }
                        else
                        {
                            pw.println("        <option value=\"-1\">-- Select " + allViewLabels[idx] + " --</option>");
                        }
                    }
                    if (formBean.getHdnchkViewSrc()[idx].equals("true"))
                    {
                        if (formBean.getHdnrdoViewDataSrc()[idx].equals("Query") || formBean.getHdnrdoViewDataSrc()[idx].equals("WebService") || formBean.getHdnrdoViewDataSrc()[idx].equals("CommonCmb"))
                        {
                            if ("None".equals(allViewFields[idx]))
                            {
                                pw.println("        <c:forEach items=\"${" + allViewFields[idx].toLowerCase(Locale.getDefault()) + controlId + "s}\" var=\"" + allViewFields[idx].toLowerCase(Locale.getDefault()) + controlId + "\">");
                            }
                            else
                            {
                                pw.println("        <c:forEach items=\"${" + allViewFields[idx].toLowerCase(Locale.getDefault()) + "s}\" var=\"" + allViewFields[idx].toLowerCase(Locale.getDefault()) + "\">");
                            }
                            if (formBean.getHdnrdoViewDataSrc()[idx].equals("Query"))
                            {
                                String[] cols;
                                cols = manager.getColumnsOfQuery(formBean, formBean.getHdntxtViewSrcQuery()[idx]);
                                if (isDate)
                                {
                                    if ("None".equals(allViewFields[idx]))
                                    {
                                        pw.println("            <option value=\"<fmt:formatDate pattern=\"dd-MM-yyyy\" value=\"${" + allViewFields[idx].toLowerCase(Locale.getDefault()) + controlId + "." + cols[0] + "}\"></fmt:formatDate>\">");
                                        pw.println("                <fmt:formatDate pattern=\"dd-MM-yyyy\" value=\"${" + allViewFields[idx].toLowerCase(Locale.getDefault()) + controlId + "." + cols[1] + "}\"></fmt:formatDate>");
                                        pw.println("            </option>");
                                    }
                                    else
                                    {
                                        pw.println("            <option value=\"<fmt:formatDate pattern=\"dd-MM-yyyy\" value=\"${" + allViewFields[idx].toLowerCase(Locale.getDefault()) + "." + cols[0] + "}\"></fmt:formatDate>\">");
                                        pw.println("                <fmt:formatDate pattern=\"dd-MM-yyyy\" value=\"${" + allViewFields[idx].toLowerCase(Locale.getDefault()) + "." + cols[1] + "}\"></fmt:formatDate>");
                                        pw.println("            </option>");
                                    }
                                }
                                else
                                {
                                    if ("None".equals(allViewFields[idx]))
                                    {
                                        pw.println("            <option value=\"${" + allViewFields[idx].toLowerCase(Locale.getDefault()) + controlId + "." + cols[0] + "}\">${" + allViewFields[idx].toLowerCase(Locale.getDefault()) + controlId + "." + cols[1] + "}</option>");
                                    }
                                    else
                                    {
                                        pw.println("            <option value=\"${" + allViewFields[idx].toLowerCase(Locale.getDefault()) + "." + cols[0] + "}\">${" + allViewFields[idx].toLowerCase(Locale.getDefault()) + "." + cols[1] + "}</option>");
                                    }
                                }
                            }
                            else if (formBean.getHdnrdoViewDataSrc()[idx].equals("CommonCmb"))
                            {
                                if ("None".equals(allViewFields[idx]))
                                {
                                    pw.println("            <option value=\"${" + allViewFields[idx].toLowerCase(Locale.getDefault()) + controlId + ".KEY}\">${" + allViewFields[idx].toLowerCase(Locale.getDefault()) + controlId + ".VALUE}</option>");
                                }
                                else
                                {
                                    pw.println("            <option value=\"${" + allViewFields[idx].toLowerCase(Locale.getDefault()) + ".KEY}\">${" + allViewFields[idx].toLowerCase(Locale.getDefault()) + ".VALUE}</option>");
                                }
                            }
                            else
                            {
                                String cmbVal, cmbText;
                                cmbVal = formBean.getHdntxtViewWsCmbValue()[idx];
                                cmbText = formBean.getHdntxtViewWsCmbText()[idx];
                                if ("None".equals(allViewFields[idx]))
                                {
                                    pw.println("            <option value=\"${" + allViewFields[idx].toLowerCase(Locale.getDefault()) + controlId + "." + cmbVal + "}\">${" + allViewFields[idx].toLowerCase(Locale.getDefault()) + controlId + "." + cmbText + "}</option>");
                                }
                                else
                                {
                                    pw.println("            <option value=\"${" + allViewFields[idx].toLowerCase(Locale.getDefault()) + "." + cmbVal + "}\">${" + allViewFields[idx].toLowerCase(Locale.getDefault()) + "." + cmbText + "}</option>");
                                }
                            }
                            pw.println("        </c:forEach>");
                        }
                        else if (formBean.getHdnrdoViewDataSrc()[idx].equals("Static"))
                        {
                            String[] vals;
                            vals = formBean.getHdntxtViewSrcStatic()[idx].split(";");
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
            pw.println("    <div class=\"menu_caption_text\">Filter</div>");
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
