/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.finreport;

import com.finlogic.business.finstudio.finreport.FinReportDetailEntityBean;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Locale;

/**
 *
 * @author njuser
 */
public class ReportTypeGenerator
{

    public void generateReportTypeJsp(FinReportDetailEntityBean entityBean) throws FileNotFoundException
    {
        String projectName = entityBean.getProjectName();
        String tomcatPath = finpack.FinPack.getProperty("tomcat1_path");
        String moduleName = entityBean.getModuleName();
        String number = entityBean.getSRNo() + "RGV2";
        String filePath = tomcatPath + "/webapps/finstudio/generated/" + number + "/";
        String projectPath = filePath + projectName.toLowerCase(Locale.getDefault()) + "/WEB-INF/jsp/" + moduleName.toLowerCase(Locale.getDefault()) + "/";

        File file = new File(projectPath);
        file.mkdirs();
        PrintWriter pw = new PrintWriter(projectPath + "reporttype.jsp");
        String[] label = entityBean.getRptLabel();
        String[] control = entityBean.getRptControl();
        String mandetory;

        pw.println("<%-- TCIGBF --%>");
        pw.println();
        pw.println("<div class=\"report_text\">Report Type</div>");
        pw.println("<div class=\"report_content\" align=\"center\">");

        if ("1".equals(entityBean.getRptControlPos()))
        {
            pw.println("    <table align=\"center\" cellpadding=\"2\" cellspacing=\"2\" class=\"menu_subcaption\" width=\"50%\">");
        }
        else if ("2".equals(entityBean.getRptControlPos()))
        {
            pw.println("    <table align=\"center\" cellpadding=\"2\" cellspacing=\"2\" class=\"menu_subcaption\" width=\"90%\">");
        }
        pw.println("        <tr>");

        int[] tabIndex = new int[entityBean.getRptTxtTabIndex().length];
        String[] tabIndexes = new String[entityBean.getRptTxtTabIndex().length];
        int tabIndexLen = tabIndex.length;
        tabIndexes = entityBean.getRptTxtTabIndex();
        String labelClass, controlClass;

        if ("1".equals(entityBean.getRptControlPos()))
        {
            labelClass = "report_content_caption";
            controlClass = "report_content_value";
        }
        else
        {
            labelClass = "report_content_text";
            controlClass = "report_content_form";
        }

        for (int i = 0; i < tabIndexLen; i++)
        {
            tabIndex[i] = i;
        }

        for (int i = 0; i < tabIndexLen; i++)
        {
            for (int j = i; j < tabIndexLen; j++)
            {
                if (Integer.parseInt(tabIndexes[j]) < Integer.parseInt(tabIndexes[i]))
                {
                    String tmp = tabIndexes[j];
                    tabIndexes[j] = tabIndexes[i];
                    tabIndexes[i] = tmp;
                    int tmp1 = tabIndex[j];
                    tabIndex[j] = tabIndex[i];
                    tabIndex[i] = tmp1;
                }
            }
        }

        int counter = 0;
        for (int i = 0; i < tabIndexLen; i++)
        {
            counter++;
            if (counter > Integer.parseInt(entityBean.getRptControlPos()))
            {
                pw.println("        </tr>");
                pw.println("        <tr>");
                counter = 1;
            }
            if (entityBean.getRptMandatory() != null && entityBean.getRptMandatory()[tabIndex[i]].equals("true"))
            {
                mandetory = "<span class=\"astriek\"> *</span>";
            }
            else
            {
                mandetory = "";
            }
            pw.println("            <td class=\"" + labelClass + "\">");
            pw.println("                " + label[tabIndex[i]] + mandetory + " : ");
            pw.println("            </td>");

            if (control[tabIndex[i]].equals("ComboBox") || control[tabIndex[i]].equals("TextLikeCombo"))
            {
                pw.println("            <td class=\"" + controlClass + "\">");
                if (entityBean.getRptRbtnMultiple()[tabIndex[i]].equals("true"))
                {
                    pw.println("                <select multiple " + callMe("id=\"", entityBean.getRptTxtId()[tabIndex[i]]) + callMe("name=\"", entityBean.getRptTxtName()[tabIndex[i]]) + callMe("class=\"", entityBean.getRptTxtClass()[tabIndex[i]]) + callMe("style=\"", entityBean.getRptTxtStyle()[tabIndex[i]]) + callMe("tabindex=\"", entityBean.getRptTxtTabIndex()[i]) + callMe("size=\"", entityBean.getRptTxtStyle()[tabIndex[i]]) + "style=\"height: 50px\"></select>");
                }
                else
                {
                    pw.println("                <select " + callMe("id=\"", entityBean.getRptTxtId()[tabIndex[i]]) + callMe("name=\"", entityBean.getRptTxtName()[tabIndex[i]]) + callMe("class=\"", entityBean.getRptTxtClass()[tabIndex[i]]) + callMe("style=\"", entityBean.getRptTxtStyle()[tabIndex[i]]) + callMe("tabindex=\"", entityBean.getRptTxtTabIndex()[i]) + callMe("size=\"", entityBean.getRptTxtStyle()[tabIndex[i]]) + ">");
                    pw.println("                    <option value=\"\">-- Select " + label[tabIndex[i]] + " --</option>");
                    pw.println("                </select>");
                }
                pw.println("            </td>");
            }
            else if (control[tabIndex[i]].equals("TextBox"))
            {
                pw.println("            <td class=\"" + controlClass + "\">");

                if (entityBean.getRptRbtnReadOnly()[tabIndex[i]].equals("true"))
                {
                    pw.println("                <input type=\"text\" readonly " + callMe("id=\"", entityBean.getRptTxtId()[tabIndex[i]]) + callMe("name=\"", entityBean.getRptTxtName()[tabIndex[i]]) + callMe("value=\"", entityBean.getRptTxtValue()[tabIndex[i]]) + callMe("align=\"", entityBean.getRptCmbAlign()[tabIndex[i]]) + callMe("maxlength=\"", entityBean.getRptTxtMaxLen()[tabIndex[i]]) + callMe("class=\"", entityBean.getRptTxtClass()[tabIndex[i]]) + callMe("style=\"", entityBean.getRptTxtStyle()[tabIndex[i]]) + callMe("tabindex=\"", entityBean.getRptTxtTabIndex()[i]) + callMe("size=\"", entityBean.getRptTxtSize()[tabIndex[i]]) + ">");
                }
                else
                {
                    pw.println("                <input type=\"text\" " + callMe("id=\"", entityBean.getRptTxtId()[tabIndex[i]]) + callMe("name=\"", entityBean.getRptTxtName()[tabIndex[i]]) + callMe("value=\"", entityBean.getRptTxtValue()[tabIndex[i]]) + callMe("align=\"", entityBean.getRptCmbAlign()[tabIndex[i]]) + callMe("maxlength=\"", entityBean.getRptTxtMaxLen()[tabIndex[i]]) + callMe("class=\"", entityBean.getRptTxtClass()[tabIndex[i]]) + callMe("style=\"", entityBean.getRptTxtStyle()[tabIndex[i]]) + callMe("tabindex=\"", entityBean.getRptTxtTabIndex()[i]) + callMe("size=\"", entityBean.getRptTxtSize()[tabIndex[i]]) + ">");
                }
                pw.println("            </td>");
            }
            else if (control[tabIndex[i]].equals("Radio"))
            {
                pw.println("            <td class=\"" + controlClass + "\">");
                int total = Integer.parseInt(entityBean.getRptTxtTotalRadio()[tabIndex[i]]);
                String check;
                String[] allVal = entityBean.getRptTxtRdoValue()[tabIndex[i]].split("/");
                for (int j = 0; j < total; j++)
                {
                    if (!entityBean.getRptTxtDefRdoValue()[tabIndex[i]].equals("") && !entityBean.getRptTxtDefRdoValue()[tabIndex[i]].equals("-") && Integer.parseInt(entityBean.getRptTxtDefRdoValue()[tabIndex[i]]) == (j + 1))
                    {
                        check = "checked ";
                    }
                    else
                    {
                        check = "";
                    }
                    pw.println("                <input type=\"radio\" class=\"radio\" id=\"" + entityBean.getRptTxtId()[tabIndex[i]] + allVal[j] + "\" " + callMe("name=\"", entityBean.getRptTxtName()[tabIndex[i]]) + check + "value=\"" + allVal[j] + "\" " + callMe("tabindex=\"", entityBean.getRptTxtTabIndex()[i]) + callMe("align=\"", entityBean.getRptCmbAlign()[tabIndex[i]]) + callMe("maxlength=\"", entityBean.getRptTxtMaxLen()[tabIndex[i]]) + callMe("class=\"", entityBean.getRptTxtClass()[tabIndex[i]]) + callMe("style=\"", entityBean.getRptTxtStyle()[tabIndex[i]]) + callMe("size=\"", entityBean.getRptTxtSize()[tabIndex[i]]) + ">");
                    pw.println("                <label> " + allVal[j] + " </label>");
                }
                pw.println("            </td>");
            }
            else if (control[tabIndex[i]].equals("CheckBox"))
            {
                pw.println("            <td class=\"" + controlClass + "\">");
                if (entityBean.getRptRbtnChecked()[tabIndex[i]].equals("true"))
                {
                    pw.println("                <input type=\"checkbox\" " + callMe("id=\"", entityBean.getRptTxtId()[tabIndex[i]]) + callMe("name=\"", entityBean.getRptTxtName()[tabIndex[i]]) + "checked=\"true\" " + callMe("value=\"", entityBean.getRptTxtValue()[tabIndex[i]]) + callMe("align=\"", entityBean.getRptCmbAlign()[tabIndex[i]]) + callMe("maxlength=\"", entityBean.getRptTxtMaxLen()[tabIndex[i]]) + callMe("class=\"", entityBean.getRptTxtClass()[tabIndex[i]]) + callMe("style=\"", entityBean.getRptTxtStyle()[tabIndex[i]]) + callMe("tabindex=\"", entityBean.getRptTxtTabIndex()[i]) + callMe("size=\"", entityBean.getRptTxtSize()[tabIndex[i]]) + ">");
                }
                else
                {
                    pw.println("                <input type=\"checkbox\" " + callMe("id=\"", entityBean.getRptTxtId()[tabIndex[i]]) + callMe("name=\"", entityBean.getRptTxtName()[tabIndex[i]]) + callMe("value=\"", entityBean.getRptTxtValue()[tabIndex[i]]) + callMe("align=\"", entityBean.getRptCmbAlign()[tabIndex[i]]) + callMe("maxlength=\"", entityBean.getRptTxtMaxLen()[tabIndex[i]]) + callMe("class=\"", entityBean.getRptTxtClass()[tabIndex[i]]) + callMe("style=\"", entityBean.getRptTxtStyle()[tabIndex[i]]) + callMe("tabindex=\"", entityBean.getRptTxtTabIndex()[i]) + callMe("size=\"", entityBean.getRptTxtSize()[tabIndex[i]]) + ">");
                }
                pw.println("            </td>");
            }
            else if (control[tabIndex[i]].equals("DatePicker"))
            {
                pw.println("            <td width=\"35%\" class=\"from_date " + controlClass + "\">");
                pw.println("                        <input type=\"text\" value=\"${dateString}\" class=\"datepickerclass\" " + callMe("id=\"", entityBean.getRptTxtId()[tabIndex[i]]) + callMe("name=\"", entityBean.getRptTxtName()[tabIndex[i]]) + callMe("value=\"", entityBean.getRptTxtValue()[tabIndex[i]]) + callMe("align=\"", entityBean.getRptCmbAlign()[tabIndex[i]]) + callMe("maxlength=\"", entityBean.getRptTxtMaxLen()[tabIndex[i]]) + callMe("style=\"", entityBean.getRptTxtStyle()[tabIndex[i]]) + callMe("tabindex=\"", entityBean.getRptTxtTabIndex()[i]) + callMe("size=\"", entityBean.getRptTxtSize()[tabIndex[i]]) + " >");
                pw.println("                        <script type=\"text/javascript\">loadDatePicker(\"" + entityBean.getRptTxtId()[tabIndex[i]] + "\");</script>");
                pw.println("            </td>");

            }
            else if (control[tabIndex[i]].equals("Submit Button"))
            {
                pw.println("            <td class=\"" + controlClass + "\">");
                pw.println("                <input type=\"submit\" " + callMe("id=\"", entityBean.getRptTxtId()[tabIndex[i]]) + callMe("name=\"", entityBean.getRptTxtName()[tabIndex[i]]) + callMe("value=\"", entityBean.getRptTxtValue()[tabIndex[i]]) + callMe("align=\"", entityBean.getRptCmbAlign()[tabIndex[i]]) + callMe("maxlength=\"", entityBean.getRptTxtMaxLen()[tabIndex[i]]) + callMe("class=\"", entityBean.getRptTxtClass()[tabIndex[i]]) + callMe("style=\"", entityBean.getRptTxtStyle()[tabIndex[i]]) + callMe("tabindex=\"", entityBean.getRptTxtTabIndex()[i]) + callMe("size=\"", entityBean.getRptTxtSize()[tabIndex[i]]) + ">");
                pw.println("            </td>");
            }
            else if (control[tabIndex[i]].equals("Reset Button"))
            {
                pw.println("            <td class=\"" + controlClass + "\">");
                pw.println("                <input type=\"reset\" " + callMe("id=\"", entityBean.getRptTxtId()[tabIndex[i]]) + callMe("name=\"", entityBean.getRptTxtName()[tabIndex[i]]) + callMe("value=\"", entityBean.getRptTxtValue()[tabIndex[i]]) + callMe("align=\"", entityBean.getRptCmbAlign()[tabIndex[i]]) + callMe("maxlength=\"", entityBean.getRptTxtMaxLen()[tabIndex[i]]) + callMe("class=\"", entityBean.getRptTxtClass()[tabIndex[i]]) + callMe("style=\"", entityBean.getRptTxtStyle()[tabIndex[i]]) + callMe("tabindex=\"", entityBean.getRptTxtTabIndex()[i]) + callMe("size=\"", entityBean.getRptTxtSize()[tabIndex[i]]) + ">");
                pw.println("            </td>");
            }
            else if (control[tabIndex[i]].equals("File"))
            {
                pw.println("            <td class=\"" + controlClass + "\">");
                pw.println("                <input type=\"file\" " + callMe("id=\"", entityBean.getRptTxtId()[tabIndex[i]]) + callMe("name=\"", entityBean.getRptTxtName()[tabIndex[i]]) + callMe("value=\"", entityBean.getRptTxtValue()[tabIndex[i]]) + callMe("align=\"", entityBean.getRptCmbAlign()[tabIndex[i]]) + callMe("maxlength=\"", entityBean.getRptTxtMaxLen()[tabIndex[i]]) + "class=\"input_brows\"" + callMe("style=\"", entityBean.getRptTxtStyle()[tabIndex[i]]) + callMe("tabindex=\"", entityBean.getRptTxtTabIndex()[i]) + callMe("size=\"", entityBean.getRptTxtSize()[tabIndex[i]]) + ">");
                pw.println("            </td>");
            }
            else if (control[tabIndex[i]].equals("Password"))
            {
                pw.println("            <td class=\"" + controlClass + "\">");
                pw.println("                <input type=\"password\" " + callMe("id=\"", entityBean.getRptTxtId()[tabIndex[i]]) + callMe("name=\"", entityBean.getRptTxtName()[tabIndex[i]]) + callMe("value=\"", entityBean.getRptTxtValue()[tabIndex[i]]) + callMe("align=\"", entityBean.getRptCmbAlign()[tabIndex[i]]) + callMe("maxlength=\"", entityBean.getRptTxtMaxLen()[tabIndex[i]]) + callMe("class=\"", entityBean.getRptTxtClass()[tabIndex[i]]) + callMe("style=\"", entityBean.getRptTxtStyle()[tabIndex[i]]) + callMe("tabindex=\"", entityBean.getRptTxtTabIndex()[i]) + callMe("size=\"", entityBean.getRptTxtSize()[tabIndex[i]]) + ">");
                pw.println("            </td>");
            }
            else if (control[tabIndex[i]].equals("TextArea"))
            {
                pw.println("            <td class=\"" + controlClass + "\">");
                pw.println("                <textarea rows=\"2\" cols=\"2\" " + callMe("id=\"", entityBean.getRptTxtId()[tabIndex[i]]) + callMe("name=\"", entityBean.getRptTxtName()[tabIndex[i]]) + callMe("value=\"", entityBean.getRptTxtValue()[tabIndex[i]]) + callMe("align=\"", entityBean.getRptCmbAlign()[tabIndex[i]]) + callMe("maxlength=\"", entityBean.getRptTxtMaxLen()[tabIndex[i]]) + callMe("class=\"", entityBean.getRptTxtClass()[tabIndex[i]]) + callMe("style=\"", entityBean.getRptTxtStyle()[tabIndex[i]]) + callMe("tabindex=\"", entityBean.getRptTxtTabIndex()[i]) + callMe("size=\"", entityBean.getRptTxtSize()[tabIndex[i]]) + "></textarea>");
                pw.println("            </td>");
            }
            else
            {
                pw.println("            <td class=\"" + controlClass + "\">");
                pw.println("                <input type=\"" + control[tabIndex[i]] + "\" " + callMe("id=\"", entityBean.getRptTxtId()[tabIndex[i]]) + callMe("name=\"", entityBean.getRptTxtName()[tabIndex[i]]) + ">");
                pw.println("            </td>");
            }
        }

        pw.println("        </tr>");
        pw.println("    </table>");
        pw.println("</div>");
        pw.close();
    }

    private String callMe(String str, String field)
    {
        if (field != null && !field.equals("-1") && !field.equals("") && !field.equals("-"))
        {
            return str + field + "\" ";
        }
        else
        {
            return "";
        }
    }
}
