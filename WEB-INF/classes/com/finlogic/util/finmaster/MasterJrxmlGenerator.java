/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.finmaster;

import com.finlogic.apps.finstudio.finmaster.FinMasterFormBean;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 *
 * @author njuser
 */
public class MasterJrxmlGenerator
{

    public void generateMasterJrxml(final FinMasterFormBean formBean) throws FileNotFoundException
    {
        String projectName;
        projectName = formBean.getCmbProjectName();
        String moduleName;
        moduleName = formBean.getTxtModuleName();
        String tomcatPath;
        tomcatPath = finpack.FinPack.getProperty("tomcat1_path");
        String number;
        number = formBean.getSrNo() + "MGV2";
        String filePath;
        filePath = tomcatPath + "/webapps/finstudio/generated/" + number + "/";
        String projectPath;
        projectPath = filePath + projectName.toLowerCase(Locale.getDefault()) + "/WEB-INF/jrxml/";

        File file;
        file = new File(projectPath);
        file.mkdirs();
        PrintWriter pw;
        pw = new PrintWriter(projectPath + moduleName.toLowerCase(Locale.getDefault()) + ".jrxml");

        String[] mstViewFields;
        mstViewFields = formBean.getChkViewColumn();
        int mstViewLen;
        mstViewLen = mstViewFields.length;

        String[] allMstColumns;
        allMstColumns = formBean.getHdnMstAllColumns();
        String[] allMstColumnTypes;
        allMstColumnTypes = formBean.getHdnMstAllDataTypes();
        int allMstLen;
        allMstLen = allMstColumns.length;

        List<String> exportColumnTypes;
        exportColumnTypes = new ArrayList<String>();

        for (int i = 0; i < mstViewLen; i++)
        {
            for (int j = 0; j < allMstLen; j++)
            {
                if (mstViewFields[i].equals(allMstColumns[j]))
                {
                    exportColumnTypes.add(allMstColumnTypes[j]);
                }
            }
        }

        int columnWidth;
        columnWidth = 984;
        int columnSize;

        pw.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        pw.println("<jasperReport xmlns=\"http://jasperreports.sourceforge.net/jasperreports\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://jasperreports.sourceforge.net/jasperreports http://jasperreports.sourceforge.net/xsd/jasperreport.xsd\" name=\"report name\" pageWidth=\"1024\" pageHeight=\"842\" columnWidth=\"" + columnWidth + "\" leftMargin=\"20\" rightMargin=\"20\" topMargin=\"20\" bottomMargin=\"20\">");
        pw.println("    <property name=\"ireport.zoom\" value=\"1.0\"/>");
        pw.println("    <property name=\"ireport.x\" value=\"0\"/>");
        pw.println("    <property name=\"ireport.y\" value=\"0\"/>");
        for (int i = 0; i < mstViewLen; i++)
        {
            if (exportColumnTypes.get(i).equals("Date"))
            {
                pw.println("    <field name=\"" + mstViewFields[i] + "\" class=\"java.util." + exportColumnTypes.get(i) + "\"/>");
            }
            else if (exportColumnTypes.get(i).equals("Time") || exportColumnTypes.get(i).equals("Timestamp"))
            {
                pw.println("    <field name=\"" + mstViewFields[i] + "\" class=\"java.sql." + exportColumnTypes.get(i) + "\"/>");
            }
            else if (exportColumnTypes.get(i).equals("BigDecimal"))
            {
                pw.println("    <field name=\"" + mstViewFields[i] + "\" class=\"java.math." + exportColumnTypes.get(i) + "\"/>");
            }
            else
            {
                pw.println("    <field name=\"" + mstViewFields[i] + "\" class=\"java.lang." + exportColumnTypes.get(i) + "\"/>");
            }

        }

        pw.println("    <title>");
        pw.println("        <band height=\"40\" splitType=\"Stretch\">");
        pw.println("            <staticText>");
        pw.println("                <reportElement mode=\"Opaque\" x=\"188\" y=\"5\" width=\"500\" height=\"20\"/>");
        pw.println("                    <box leftPadding=\"8\"/>");
        pw.println("                    <textElement rotation=\"None\" lineSpacing=\"Single\" markup=\"none\">");
        pw.println("                        <font size=\"16\" isBold=\"true\" isUnderline=\"true\" isStrikeThrough=\"false\" isPdfEmbedded=\"false\"/>");
        pw.println("                    </textElement>");
        pw.println("                <text><![CDATA[" + formBean.getTxtOrigModulName() + " Report]]></text>");
        pw.println("            </staticText>");
        pw.println("        </band>");
        pw.println("    </title>");
        pw.println("    <columnHeader>");
        int x = 0;
        columnSize = columnWidth / mstViewLen;
        pw.println("        <band height=\"28\" splitType=\"Stretch\">");
        for (int i = 0; i < mstViewLen; i++)
        {
            if (i == (mstViewLen - 1))
            {
                columnSize = columnWidth - ((mstViewLen - 1) * columnSize);
            }
            pw.println("            <staticText>");
            pw.println("                <reportElement x=\"" + x + "\" y=\"0\" width=\"" + columnSize + "\" height=\"28\"/>");
            pw.println("                <textElement/>");
            pw.println("                <text><![CDATA[" + mstViewFields[i] + "]]></text>");
            pw.println("            </staticText>");
            x += columnSize;
        }

        pw.println("        </band>");
        pw.println("    </columnHeader>");
        pw.println("    <detail>");
        pw.println("        <band height=\"14\" splitType=\"Stretch\">");
        x = 0;
        columnSize = columnWidth / mstViewLen;
        for (int i = 0; i < mstViewLen; i++)
        {
            if (i == (mstViewLen - 1))
            {
                columnSize = columnWidth - ((mstViewLen - 1) * columnSize);
            }
            pw.println("            <textField>");
            pw.println("                <reportElement x=\"" + x + "\" y=\"0\" width=\"" + columnSize + "\" height=\"14\"/>");
            pw.println("                <textElement/>");

            if (exportColumnTypes.get(i).equals("Date"))
            {
                pw.println("                <textFieldExpression class=\"java.util." + exportColumnTypes.get(i) + "\"><![CDATA[$F{" + mstViewFields[i] + "}]]></textFieldExpression>");
            }
            else if (exportColumnTypes.get(i).equals("Time") || exportColumnTypes.get(i).equals("Timestamp"))
            {
                pw.println("                <textFieldExpression class=\"java.sql." + exportColumnTypes.get(i) + "\"><![CDATA[$F{" + mstViewFields[i] + "}]]></textFieldExpression>");
            }
            else if (exportColumnTypes.get(i).equals("BigDecimal"))
            {
                pw.println("                <textFieldExpression class=\"java.math." + exportColumnTypes.get(i) + "\"><![CDATA[$F{" + mstViewFields[i] + "}]]></textFieldExpression>");
            }
            else
            {
                pw.println("                <textFieldExpression class=\"java.lang." + exportColumnTypes.get(i) + "\"><![CDATA[$F{" + mstViewFields[i] + "}]]></textFieldExpression>");
            }
            pw.println("            </textField>");
            x += columnSize;
        }
        pw.println("        </band>");
        pw.println("    </detail>");
        pw.println("    <columnFooter>");
        pw.println("        <band height=\"14\" splitType=\"Stretch\">");
        pw.println("        </band>");
        pw.println("    </columnFooter>");
        pw.println("</jasperReport>");
        pw.close();
    }
}
