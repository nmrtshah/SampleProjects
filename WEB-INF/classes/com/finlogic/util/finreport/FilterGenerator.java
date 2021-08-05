/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.finreport;

import com.finlogic.business.finstudio.finreport.FinReportDataManager;
import com.finlogic.business.finstudio.finreport.FinReportDetailEntityBean;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;

/**
 *
 * @author njuser
 */
public class FilterGenerator
{

    public void generateFilterJsp(final FinReportDetailEntityBean entityBean) throws FileNotFoundException, ClassNotFoundException, SQLException, UnsupportedEncodingException
    {
        String projectName = entityBean.getProjectName();
        String tomcatPath = finpack.FinPack.getProperty("tomcat1_path");
        String moduleName = entityBean.getModuleName();
        String number = entityBean.getSRNo() + "RGV2";
        String filePath = tomcatPath + "/webapps/finstudio/generated/" + number + "/";
        String projectPath = filePath + projectName.toLowerCase(Locale.getDefault()) + "/WEB-INF/jsp/" + moduleName.toLowerCase(Locale.getDefault()) + "/";
        String mandetory;

        File file = new File(projectPath);
        file.mkdirs();
        PrintWriter pw = new PrintWriter(projectPath + "filter.jsp");

        String[] label = entityBean.getFltrLabel();
        String[] control = entityBean.getFltrControl();

        pw.println("<%-- TCIGBF --%>");
        pw.println();
        pw.println("<%@taglib uri=\"http://java.sun.com/jsp/jstl/core\" prefix=\"c\" %>");
        pw.println("<div class=\"report_text\">Filter</div>");
        pw.println("<div class=\"report_content\" align=\"center\">");

        String callGetColumn = "";
        if (entityBean.isColumns())
        {
            callGetColumn = " onchange=\"getColumns();\"";
        }

        if ("1".equals(entityBean.getFltrControlPos()))
        {
            pw.println("    <table align=\"center\" cellpadding=\"2\" cellspacing=\"2\" class=\"menu_subcaption\" width=\"50%\">");
        }
        else if ("2".equals(entityBean.getFltrControlPos()))
        {
            pw.println("    <table align=\"center\" cellpadding=\"2\" cellspacing=\"2\" class=\"menu_subcaption\" width=\"90%\">");
        }

        pw.println("        <tr>");
        int[] tabIndex = new int[entityBean.getFltrTxtTabIndex().length];
        int tabIndexLen = tabIndex.length;
        String[] tabIndexes = entityBean.getFltrTxtTabIndex();
        String labelClass, controlClass;
        if ("1".equals(entityBean.getFltrControlPos()))
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
            if (counter > Integer.parseInt(entityBean.getFltrControlPos()))
            {
                pw.println("        </tr>");
                pw.println("        <tr>");
                counter = 1;
            }
            if (entityBean.getFltrMandatory() != null && entityBean.getFltrMandatory()[tabIndex[i]].equals("true"))
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
                if (entityBean.getFltrRbtnMultiple()[tabIndex[i]].equals("true"))
                {
                    pw.println("                <select multiple " + callMe("id=\"", entityBean.getFltrTxtId()[tabIndex[i]]) + callMe("name=\"", entityBean.getFltrTxtName()[tabIndex[i]]) + callMe("class=\"", entityBean.getFltrTxtClass()[tabIndex[i]]) + callMe("style=\"", entityBean.getFltrTxtStyle()[tabIndex[i]]) + callMe("tabindex=\"", entityBean.getFltrTxtTabIndex()[i]) + callMe("size=\"", entityBean.getFltrTxtSize()[tabIndex[i]]) + ">");
                }
                else
                {
                    pw.println("                <select " + callMe("id=\"", entityBean.getFltrTxtId()[tabIndex[i]]) + callMe("name=\"", entityBean.getFltrTxtName()[tabIndex[i]]) + callMe("class=\"", entityBean.getFltrTxtClass()[tabIndex[i]]) + callMe("style=\"", entityBean.getFltrTxtStyle()[tabIndex[i]]) + callMe("tabindex=\"", entityBean.getFltrTxtTabIndex()[i]) + callMe("size=\"", entityBean.getFltrTxtSize()[tabIndex[i]]) + ">");
                    pw.println("                    <option value=\"\">-- Select " + label[tabIndex[i]] + " --</option>");
                }

                if ("fltrCmbSrcQuery".equals(entityBean.getFltrCmbSource()[tabIndex[i]]) && !"".equals(entityBean.getFltrTxtSrcQuery()[tabIndex[i]]))
                {
                    FinReportDataManager frs = new FinReportDataManager();
                    DirectConnection conn = new DirectConnection();
                    Connection con = null;
                    SqlRowSet column;
                    SqlRowSetMetaData srsmd;
                    String columns[];
                    String decodeQuery;

                    if ("usingAlias".equals(entityBean.getConType()))
                    {
                        decodeQuery = URLDecoder.decode(entityBean.getFltrTxtSrcQuery()[tabIndex[i]], "UTF-8").trim();
                        if (decodeQuery.endsWith(";"))
                        {
                            decodeQuery = decodeQuery.substring(0, decodeQuery.length() - 1).trim();
                        }
                        decodeQuery = "SELECT * FROM (" + decodeQuery + ") X WHERE 1 = 2";
                        column = frs.getReportData(entityBean.getAlias()[0], decodeQuery, entityBean.getConType(), null);
                        srsmd = column.getMetaData();
                        columns = srsmd.getColumnNames();
                    }
                    else
                    {
                        //direct connection                
                        decodeQuery = URLDecoder.decode(entityBean.getFltrTxtSrcQuery()[tabIndex[i]], "UTF-8").trim();
                        if (decodeQuery.endsWith(";"))
                        {
                            decodeQuery = decodeQuery.substring(0, decodeQuery.length() - 1).trim();
                        }
                        decodeQuery = "SELECT * FROM (" + decodeQuery + ") X WHERE 1 = 2";
                        try
                        {
                            con = conn.getConnection(entityBean.getDevServer());
                        }
                        finally
                        {
                            con.close();
                        }
                        column = frs.getReportData(entityBean.getAlias()[0], decodeQuery, entityBean.getConType(), con);
                        srsmd = column.getMetaData();
                        columns = srsmd.getColumnNames();
                    }

                    pw.println("                    <c:forEach items=\"${" + entityBean.getFltrTxtName()[tabIndex[i]] + "List}\" var=\"lst\" >");
                    pw.println("                        <option value=\"${lst." + columns[0].trim() + "}\">${lst." + columns[1].trim() + "}</option>");
                    pw.println("                    </c:forEach>");
                }
                else if ("fltrCmbSrcStatic".equals(entityBean.getFltrCmbSource()[tabIndex[i]]) && !"".equals(entityBean.getFltrTxtSrcStatic()[tabIndex[i]]))
                {
                    String staticData = entityBean.getFltrTxtSrcStatic()[tabIndex[i]];
                    String first[] = staticData.split(";");
                    for (int j = 0; j < first.length; j++)
                    {
                        String second[] = first[j].split(",");
                        pw.println("                    <option value=\"" + second[0] + "\">" + second[1] + "</option>");
                    }
                }
                else if ("fltrCmbSrcWS".equals(entityBean.getFltrCmbSource()[tabIndex[i]]))
                {
                    pw.println("                    <c:forEach items=\"${" + entityBean.getFltrTxtName()[tabIndex[i]] + "List}\" var=\"lst\" >");
                    pw.println("                        <option value=\"${lst." + entityBean.getFltrTxtWsCmbValue()[tabIndex[i]] + "}\">${lst." + entityBean.getFltrTxtWsCmbText()[tabIndex[i]] + "}</option>");
                    pw.println("                    </c:forEach>");
                }
                else if ("fltrCmbSrcCommonCmb".equals(entityBean.getFltrCmbSource()[tabIndex[i]]))
                {
                    pw.println("                    <c:forEach items=\"${" + entityBean.getFltrTxtName()[tabIndex[i]] + "List}\" var=\"lst\" >");
                    pw.println("                        <option value=\"${lst.KEY" + "}\">${lst.VALUE" + "}</option>");
                    pw.println("                    </c:forEach>");
                }
                pw.println("                </select>");
                pw.println("            </td>");
            }
            else if (control[tabIndex[i]].equals("TextBox"))
            {
                pw.println("            <td class=\"" + controlClass + "\">");
                if (entityBean.getFltrRbtnReadOnly()[tabIndex[i]].equals("true"))
                {
                    pw.println("                <input type=\"text\" readonly " + callMe("id=\"", entityBean.getFltrTxtId()[tabIndex[i]]) + callMe("name=\"", entityBean.getFltrTxtName()[tabIndex[i]]) + callMe("value=\"", entityBean.getFltrTxtValue()[tabIndex[i]]) + callMe("align=\"", entityBean.getFltrCmbAlign()[tabIndex[i]]) + callMe("maxlength=\"", entityBean.getFltrTxtMaxLen()[tabIndex[i]]) + callMe("class=\"", entityBean.getFltrTxtClass()[tabIndex[i]]) + callMe("style=\"", entityBean.getFltrTxtStyle()[tabIndex[i]]) + callMe("tabindex=\"", entityBean.getFltrTxtTabIndex()[i]) + callMe("size=\"", entityBean.getFltrTxtSize()[tabIndex[i]]) + ">");
                }
                else
                {
                    pw.println("                <input type=\"text\" " + callMe("id=\"", entityBean.getFltrTxtId()[tabIndex[i]]) + callMe("name=\"", entityBean.getFltrTxtName()[tabIndex[i]]) + callMe("value=\"", entityBean.getFltrTxtValue()[tabIndex[i]]) + callMe("align=\"", entityBean.getFltrCmbAlign()[tabIndex[i]]) + callMe("maxlength=\"", entityBean.getFltrTxtMaxLen()[tabIndex[i]]) + callMe("class=\"", entityBean.getFltrTxtClass()[tabIndex[i]]) + callMe("style=\"", entityBean.getFltrTxtStyle()[tabIndex[i]]) + callMe("tabindex=\"", entityBean.getFltrTxtTabIndex()[i]) + callMe("size=\"", entityBean.getFltrTxtSize()[tabIndex[i]]) + ">");
                }
                pw.println("            </td>");
            }
            else if (control[tabIndex[i]].equals("Radio"))
            {
                pw.println("            <td class=\"" + controlClass + "\">");
                int total = Integer.parseInt(entityBean.getFltrTxtTotalRadio()[tabIndex[i]]);
                String check;
                String[] allVal = entityBean.getFltrTxtRdoValue()[tabIndex[i]].split("/");
                for (int j = 0; j < total; j++)
                {
                    if (!entityBean.getFltrTxtDefRdoValue()[tabIndex[i]].equals("") && !entityBean.getFltrTxtDefRdoValue()[tabIndex[i]].equals("-") && Integer.parseInt(entityBean.getFltrTxtDefRdoValue()[tabIndex[i]]) == (j + 1))
                    {
                        check = "checked ";
                    }
                    else
                    {
                        check = "";
                    }
                    pw.println("                <input type=\"radio\" class=\"radio\" id=\"" + entityBean.getFltrTxtId()[tabIndex[i]] + allVal[j] + "\" " + callMe("name=\"", entityBean.getFltrTxtName()[tabIndex[i]]) + check + "value=\"" + allVal[j] + "\" " + callMe(" tabindex=\"", entityBean.getFltrTxtTabIndex()[i]) + callMe("align=\"", entityBean.getFltrCmbAlign()[tabIndex[i]]) + callMe("maxlength=\"", entityBean.getFltrTxtMaxLen()[tabIndex[i]]) + callMe("class=\"", entityBean.getFltrTxtClass()[tabIndex[i]]) + callMe("style=\"", entityBean.getFltrTxtStyle()[tabIndex[i]]) + ">");
                    pw.println("                <label> " + allVal[j] + " </label>");
                }
                pw.println("            </td>");
            }
            else if (control[tabIndex[i]].equals("CheckBox"))
            {
                pw.println("            <td class=\"" + controlClass + "\">");
                if (entityBean.getFltrRbtnChecked()[tabIndex[i]].equals("true"))
                {
                    pw.println("                <input type=\"checkbox\" " + callMe("id=\"", entityBean.getFltrTxtId()[tabIndex[i]]) + callMe("name=\"", entityBean.getFltrTxtName()[tabIndex[i]]) + "checked=\"true\" " + callMe("value=\"", entityBean.getFltrTxtValue()[tabIndex[i]]) + callMe("align=\"", entityBean.getFltrCmbAlign()[tabIndex[i]]) + callMe("maxlength=\"", entityBean.getFltrTxtMaxLen()[tabIndex[i]]) + callMe("class=\"", entityBean.getFltrTxtClass()[tabIndex[i]]) + callMe("style=\"", entityBean.getFltrTxtStyle()[tabIndex[i]]) + callMe("tabindex=\"", entityBean.getFltrTxtTabIndex()[i]) + ">");
                }
                else
                {
                    pw.println("                <input type=\"checkbox\" " + callMe("id=\"", entityBean.getFltrTxtId()[tabIndex[i]]) + callMe("name=\"", entityBean.getFltrTxtName()[tabIndex[i]]) + callMe("value=\"", entityBean.getFltrTxtValue()[tabIndex[i]]) + callMe("align=\"", entityBean.getFltrCmbAlign()[tabIndex[i]]) + callMe("maxlength=\"", entityBean.getFltrTxtMaxLen()[tabIndex[i]]) + callMe("class=\"", entityBean.getFltrTxtClass()[tabIndex[i]]) + callMe("style=\"", entityBean.getFltrTxtStyle()[tabIndex[i]]) + callMe("tabindex=\"", entityBean.getFltrTxtTabIndex()[i]) + ">");
                }
                pw.println("            </td>");
            }
            else if (control[tabIndex[i]].equals("DatePicker"))
            {
                pw.println("            <td class=\"from_date\" style=\"padding: 0; float: none;\">");
                pw.println("                <input type=\"text\" value=\"${dateString}\" class=\"datepickerclass\" " + callMe("id=\"", entityBean.getFltrTxtId()[tabIndex[i]]) + callMe("name=\"", entityBean.getFltrTxtName()[tabIndex[i]]) + callMe("value=\"", entityBean.getFltrTxtValue()[tabIndex[i]]) + callMe("align=\"", entityBean.getFltrCmbAlign()[tabIndex[i]]) + callMe("maxlength=\"", entityBean.getFltrTxtMaxLen()[tabIndex[i]]) + callMe("style=\"", entityBean.getFltrTxtStyle()[tabIndex[i]]) + callMe("tabindex=\"", entityBean.getFltrTxtTabIndex()[i]) + callMe("size=\"", entityBean.getFltrTxtSize()[tabIndex[i]]) + " >");
                pw.println("                <script type=\"text/javascript\">loadDatePicker(\"" + entityBean.getFltrTxtId()[tabIndex[i]] + "\");</script>");
                pw.println("            </td>");
            }
            else if (control[tabIndex[i]].equals("Submit Button"))
            {
                pw.println("            <td class=\"" + controlClass + "\">");
                pw.println("                <input type=\"submit\" " + callMe("id=\"", entityBean.getFltrTxtId()[tabIndex[i]]) + callMe("name=\"", entityBean.getFltrTxtName()[tabIndex[i]]) + callMe("value=\"", entityBean.getFltrTxtValue()[tabIndex[i]]) + callMe("align=\"", entityBean.getFltrCmbAlign()[tabIndex[i]]) + callMe("maxlength=\"", entityBean.getFltrTxtMaxLen()[tabIndex[i]]) + callMe("class=\"", entityBean.getFltrTxtClass()[tabIndex[i]]) + callMe("style=\"", entityBean.getFltrTxtStyle()[tabIndex[i]]) + callMe("tabindex=\"", entityBean.getFltrTxtTabIndex()[i]) + callMe("size=\"", entityBean.getFltrTxtSize()[tabIndex[i]]) + ">");
                pw.println("            </td>");
            }
            else if (control[tabIndex[i]].equals("Reset Button"))
            {
                pw.println("            <td class=\"" + controlClass + "\">");
                pw.println("                <input type=\"reset\" " + callMe("id=\"", entityBean.getFltrTxtId()[tabIndex[i]]) + callMe("name=\"", entityBean.getFltrTxtName()[tabIndex[i]]) + callMe("value=\"", entityBean.getFltrTxtValue()[tabIndex[i]]) + callMe("align=\"", entityBean.getFltrCmbAlign()[tabIndex[i]]) + callMe("maxlength=\"", entityBean.getFltrTxtMaxLen()[tabIndex[i]]) + callMe("class=\"", entityBean.getFltrTxtClass()[tabIndex[i]]) + callMe("style=\"", entityBean.getFltrTxtStyle()[tabIndex[i]]) + callMe("tabindex=\"", entityBean.getFltrTxtTabIndex()[i]) + callMe("size=\"", entityBean.getFltrTxtSize()[tabIndex[i]]) + ">");
                pw.println("            </td>");
            }
            else if (control[tabIndex[i]].equals("File"))
            {
                pw.println("            <td class=\"" + controlClass + "\">");
                pw.println("                <input type=\"file\" " + callMe("id=\"", entityBean.getFltrTxtId()[tabIndex[i]]) + callMe("name=\"", entityBean.getFltrTxtName()[tabIndex[i]]) + callMe("value=\"", entityBean.getFltrTxtValue()[tabIndex[i]]) + callMe("align=\"", entityBean.getFltrCmbAlign()[tabIndex[i]]) + callMe("maxlength=\"", entityBean.getFltrTxtMaxLen()[tabIndex[i]]) + "class=\"input_brows\"" + callMe("style=\"", entityBean.getFltrTxtStyle()[tabIndex[i]]) + callMe("tabindex=\"", entityBean.getFltrTxtTabIndex()[i]) + callMe("size=\"", entityBean.getFltrTxtSize()[tabIndex[i]]) + ">");
                pw.println("            </td>");
            }
            else if (control[tabIndex[i]].equals("Password"))
            {
                pw.println("            <td class=\"" + controlClass + "\">");
                pw.println("                <input type=\"password\" " + callMe("id=\"", entityBean.getFltrTxtId()[tabIndex[i]]) + callMe("name=\"", entityBean.getFltrTxtName()[tabIndex[i]]) + callMe("value=\"", entityBean.getFltrTxtValue()[tabIndex[i]]) + callMe("align=\"", entityBean.getFltrCmbAlign()[tabIndex[i]]) + callMe("maxlength=\"", entityBean.getFltrTxtMaxLen()[tabIndex[i]]) + callMe("class=\"", entityBean.getFltrTxtClass()[tabIndex[i]]) + callMe("style=\"", entityBean.getFltrTxtStyle()[tabIndex[i]]) + callMe("tabindex=\"", entityBean.getFltrTxtTabIndex()[i]) + callMe("size=\"", entityBean.getFltrTxtSize()[tabIndex[i]]) + ">");
                pw.println("            </td>");
            }
            else if (control[tabIndex[i]].equals("TextArea"))
            {
                pw.println("            <td>");
                pw.println("                <textarea rows=\"2\" cols=\"2\" " + callMe("id=\"", entityBean.getFltrTxtId()[tabIndex[i]]) + callMe("name=\"", entityBean.getFltrTxtName()[tabIndex[i]]) + callMe("value=\"", entityBean.getFltrTxtValue()[tabIndex[i]]) + callMe("align=\"", entityBean.getFltrCmbAlign()[tabIndex[i]]) + callMe("maxlength=\"", entityBean.getFltrTxtMaxLen()[tabIndex[i]]) + callMe("class=\"", entityBean.getFltrTxtClass()[tabIndex[i]]) + callMe("style=\"", entityBean.getFltrTxtStyle()[tabIndex[i]]) + callMe("tabindex=\"", entityBean.getFltrTxtTabIndex()[i]) + callMe("size=\"", entityBean.getFltrTxtSize()[tabIndex[i]]) + "></textarea>");
                pw.println("            </td>");
            }
            else
            {
                pw.println("            <td>");
                pw.println("                <input type=\"" + control[i] + "\" " + callMe("id=\"", entityBean.getFltrTxtId()[tabIndex[i]]) + callMe("name=\"", entityBean.getFltrTxtName()[tabIndex[i]]) + ">");
                pw.println("            </td>");
            }
        }
        pw.println("        </tr>");
        if (entityBean.getCmbRefNo() != null && !entityBean.getCmbRefNo().equals(""))
        {
            pw.println("        <tr>");
            pw.println("            <td class=\"" + labelClass + "\">");
            pw.println("                Report Type ");
            pw.println("            </td>");
            pw.println("            <td class=\"" + controlClass + "\">");
            pw.println("                Detail Report <input type=\"radio\" id=\"rdoDetailReport\" checked=\"\" name=\"rdoReportType\" value=\"detailReport\" style=\"width: 20px\" " + callGetColumn + " >");
            pw.println("                Summary Report <input type=\"radio\" id=\"rdoSummaryReport\" name=\"rdoReportType\" value=\"summaryReport\" style=\"width: 20px\" " + callGetColumn + ">");
            pw.println("            </td>");
            pw.println("        </tr>");
        }

        pw.println("    </table>");
        pw.println("</div>");
        pw.close();
    }

    private String callMe(final String str, final String field)
    {
        if (field != null && !field.equals("-1") && !field.equals("") && !field.equals("-"))
        {
            return str + field + "\"  ";
        }
        else
        {
            return "";
        }
    }
}
