/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.finreport;

import com.finlogic.business.finstudio.finreport.FinReportSummaryEntityBean;
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
public class SummaryReportJrxmlGenerator
{

    public void generateSummaryReportJrxml(String projectName, String moduleName, int refNumber, boolean chart, final FinReportSummaryEntityBean sEntityBean) throws FileNotFoundException
    {
        String tomcatPath = finpack.FinPack.getProperty("tomcat1_path");
        String filePath = tomcatPath + "/webapps/finstudio/generated/" + refNumber + "RGV2/";
        String projectPath = filePath + projectName.toLowerCase(Locale.getDefault()) + "/WEB-INF/jrxml/";

        File file = new File(projectPath);
        file.mkdirs();
        PrintWriter pw = new PrintWriter(projectPath + moduleName.toLowerCase(Locale.getDefault()) + "Summary.jrxml");

        List FooterColumnType = new ArrayList();
        List<String> tabColumnTypes = new ArrayList<String>();
        boolean Grouping = sEntityBean.isGrouping();
        boolean GroupFooter = sEntityBean.isGroupFooter();
        String GroupField = sEntityBean.getGroupField();
        String[] FooterColumn = sEntityBean.getGroupFtrCol();
        String[] FooterColumnCalc = sEntityBean.getGroupFtrCal();
        String[] tabColumns = sEntityBean.getSelectedColumns();
        int tabColumnLen = tabColumns.length;
        String[] allColumns = sEntityBean.getAllColumns();
        int allColumnLen = allColumns.length;
        String[] allColumnTypes = sEntityBean.getAllColumnTypes();
        String GroupFieldType = null;

        for (int i = 0; i < allColumnLen; i++)
        {
            if (allColumns[i].equals(GroupField))
            {
                GroupFieldType = allColumnTypes[i];
                break;
            }
        }

        int numOfColumns = tabColumnLen;
        int columnWidth = 984;
        int columnSize = columnWidth / (numOfColumns);
        for (int i = 0; i < tabColumnLen; i++)
        {
            for (int j = 0; j < allColumnLen; j++)
            {
                if (tabColumns[i].equals(allColumns[j]))
                {
                    tabColumnTypes.add(allColumnTypes[j]);
                    break;
                }
            }
        }

        pw.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        pw.println("<jasperReport xmlns=\"http://jasperreports.sourceforge.net/jasperreports\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://jasperreports.sourceforge.net/jasperreports http://jasperreports.sourceforge.net/xsd/jasperreport.xsd\" name=\"report name\" pageWidth=\"1024\" pageHeight=\"842\" columnWidth=\"" + columnWidth + "\" leftMargin=\"20\" rightMargin=\"20\" topMargin=\"20\" bottomMargin=\"20\" isIgnorePagination=\"true\">");
        pw.println("    <property name=\"ireport.zoom\" value=\"1.0\"/>");
        pw.println("    <property name=\"ireport.x\" value=\"0\"/>");
        pw.println("    <property name=\"ireport.y\" value=\"0\"/>");
        
        if (chart)
        {
            pw.println("    <parameter name=\"imgPath\" class=\"java.lang.String\"></parameter>");
        }
        for (int i = 0; i < numOfColumns; i++)
        {
            if ("Date".equals(tabColumnTypes.get(i)))
            {
                pw.println("    <field name=\"" + tabColumns[i] + "\" class=\"java.util." + tabColumnTypes.get(i) + "\"/>");
            }
            else if ("Time".equals(tabColumnTypes.get(i)) || "Timestamp".equals(tabColumnTypes.get(i)))
            {
                pw.println("    <field name=\"" + tabColumns[i] + "\" class=\"java.sql." + tabColumnTypes.get(i) + "\"/>");
            }
            else if ("BigDecimal".equals(tabColumnTypes.get(i)))
            {
                pw.println("    <field name=\"" + tabColumns[i] + "\" class=\"java.math." + tabColumnTypes.get(i) + "\"/>");
            }
            else
            {
                pw.println("    <field name=\"" + tabColumns[i] + "\" class=\"java.lang." + tabColumnTypes.get(i) + "\"/>");
            }

        }
        String[] cal;
        if (Grouping && GroupFooter && FooterColumn != null)
        {
            int footerColumnLen = FooterColumn.length;
            cal = new String[FooterColumnCalc.length];
            for (int i = 0; i < FooterColumnCalc.length; i++)
            {
                if (FooterColumnCalc[i].equals("lowest"))
                {
                    cal[i] = "Lowest";
                }
                else if (FooterColumnCalc[i].equals("highest"))
                {
                    cal[i] = "Highest";
                }
                else if (FooterColumnCalc[i].equals("sum"))
                {
                    cal[i] = "Sum";
                }
                else if (FooterColumnCalc[i].equals("count"))
                {
                    cal[i] = "Count";
                }
                else if (FooterColumnCalc[i].equals("average"))
                {
                    cal[i] = "Average";
                }
            }
            for (int i = 0; i < footerColumnLen; i++)
            {
                for (int j = 0; j < allColumnLen; j++)
                {
                    if (FooterColumn[i].equals(allColumns[j]))
                    {
                        FooterColumnType.add(allColumnTypes[j]);
                        break;
                    }
                }
            }
            int k = 0;
            for (int j = 0; j < footerColumnLen; j++)
            {
                for (k = 0; k < numOfColumns; k++)
                {
                    if (tabColumns[k].equals(FooterColumn[j]))
                    {
                        break;
                    }
                }
                if (k == numOfColumns)
                {
                    if (FooterColumnType.get(j).equals("Date"))
                    {
                        pw.println("    <field name=\"" + FooterColumn[j] + "\" class=\"java.util." + FooterColumnType.get(j) + "\"/>");
                    }
                    else if (FooterColumnType.get(j).equals("Time") || FooterColumnType.get(j).equals("Timestamp"))
                    {
                        pw.println("    <field name=\"" + FooterColumn[j] + "\" class=\"java.sql." + FooterColumnType.get(j) + "\"/>");
                    }
                    else if (FooterColumnType.get(j).equals("BigDecimal"))
                    {
                        pw.println("    <field name=\"" + FooterColumn[j] + "\" class=\"java.math." + FooterColumnType.get(j) + "\"/>");
                    }
                    else
                    {
                        pw.println("    <field name=\"" + FooterColumn[j] + "\" class=\"java.lang." + FooterColumnType.get(j) + "\"/>");
                    }

                }
            }

            for (int i = 0; i < footerColumnLen; i++)
            {
                if (FooterColumnType.get(i).equals("Date"))
                {
                    pw.print("    <variable name=\"" + FooterColumn[i] + "_VAR\" class=\"java.util." + FooterColumnType.get(i) + "\" ");
                }
                else if (FooterColumnType.get(i).equals("BigDecimal"))
                {
                    pw.print("    <variable name=\"" + FooterColumn[i] + "_VAR\" class=\"java.math." + FooterColumnType.get(i) + "\" ");
                }
                else if (FooterColumnType.get(i).equals("Time") || FooterColumnType.get(i).equals("Timestamp"))
                {
                    pw.print("    <variable name=\"" + FooterColumn[i] + "_VAR\" class=\"java.sql." + FooterColumnType.get(i) + "\" ");
                }
                else if (FooterColumnType.get(i).equals("String") && cal[i].equals("Count"))
                {
                    pw.print("    <variable name=\"" + FooterColumn[i] + "_VAR\" class=\"java.lang.Integer\" ");
                }
                else
                {
                    pw.print("    <variable name=\"" + FooterColumn[i] + "_VAR\" class=\"java.lang." + FooterColumnType.get(i) + "\" ");
                }
                if (Grouping)
                {
                    pw.print("resetType=\"Group\" resetGroup=\"" + GroupField + "Wise\" ");
                }
                pw.println("calculation=\"" + cal[i] + "\">");
                pw.println("            <variableExpression><![CDATA[$F{" + FooterColumn[i] + "}]]></variableExpression>");
                pw.println("    </variable>");
            }
        }
        if (Grouping)
        {
            pw.println("    <group name=\"" + GroupField + "Wise\">");
            pw.println("        <groupExpression><![CDATA[$F{" + GroupField + "}]]></groupExpression>");
            pw.println("        <groupHeader>");
            pw.println("            <band height=\"15\">");
            pw.println("    		<line>");
            pw.println("    			<reportElement x=\"0\" y=\"0\" width=\"" + columnWidth + "\" height=\"1\"/>");
            pw.println("    		</line>");
            pw.println("                <staticText>");
            pw.println("                    <reportElement x=\"0\" y=\"1\" width=\"" + columnSize + "\" height=\"14\"/>");
            pw.println("                        <textElement>");
            pw.println("                            <font size=\"8\"/>");
            pw.println("                        </textElement>");
            pw.println("                        <text><![CDATA[" + GroupField + "]]></text>");
            pw.println("                </staticText>");
            pw.println("                <textField>");
            pw.println("                    <reportElement x=\"" + columnSize + "\" y=\"1\" width=\"" + columnSize + "\" height=\"14\"/>");
            pw.println("                    <textElement>");
            pw.println("    			<font size=\"10\"/>");
            pw.println("                    </textElement>");
            if ("Date".equals(GroupFieldType))
            {
                pw.println("                    <textFieldExpression class=\"java.util." + GroupFieldType + "\"><![CDATA[$F{" + GroupField + "}]]></textFieldExpression>");
            }
            else if ("Time".equals(GroupFieldType) || "Timestamp".equals(GroupFieldType))
            {
                pw.println("                    <textFieldExpression class=\"java.sql." + GroupFieldType + "\"><![CDATA[$F{" + GroupField + "}]]></textFieldExpression>");
            }
            else if ("BigDecimal".equals(GroupFieldType))
            {
                pw.println("                    <textFieldExpression class=\"java.math." + GroupFieldType + "\"><![CDATA[$F{" + GroupField + "}]]></textFieldExpression>");
            }
            else
            {
                pw.println("                    <textFieldExpression class=\"java.lang." + GroupFieldType + "\"><![CDATA[$F{" + GroupField + "}]]></textFieldExpression>");
            }

            pw.println("                </textField>");
            pw.println("            </band>");
            pw.println("        </groupHeader>");
            if (GroupFooter && FooterColumn != null)
            {
                int footerColumnLen;
                footerColumnLen = FooterColumn.length;
                pw.println("        <groupFooter>");
                pw.println("            <band height=\"14\">");
                //addded line
                int x1 = 0;
                for (int i = 0; i < numOfColumns; i++)
                {

                    columnSize = columnWidth / (numOfColumns);
                    if (i == (numOfColumns - 1))
                    {
                        columnSize = columnWidth - ((numOfColumns - 1) * columnSize);
                    }
                    int j;
                    for (j = 0; j < footerColumnLen; j++)
                    {

                        if (tabColumns[i].equals(FooterColumn[j]))
                        {
                            pw.println("                <textField>");
                            pw.println("                    <reportElement x=\"" + x1 + "\" y=\"0\" width=\"" + columnSize + "\" height=\"14\"/>");
                            pw.println("                    <textElement textAlignment=\"Left\">");
                            pw.println("                        <font size=\"12\"/>");
                            pw.println("                    </textElement>");
                            if (FooterColumnType.get(j).equals("Date"))
                            {
                                pw.println("                    <textFieldExpression class=\"java.util." + FooterColumnType.get(j) + "\"><![CDATA[$V{" + FooterColumn[j] + "_VAR}]]></textFieldExpression>");
                            }
                            else if (FooterColumnType.get(j).equals("BigDecimal"))
                            {
                                pw.println("                    <textFieldExpression class=\"java.math." + FooterColumnType.get(j) + "\"><![CDATA[$V{" + FooterColumn[j] + "_VAR}]]></textFieldExpression>");
                            }
                            else if (FooterColumnType.get(j).equals("Time") || FooterColumnType.get(j).equals("Timestamp"))
                            {
                                pw.println("                    <textFieldExpression class=\"java.sql." + FooterColumnType.get(j) + "\"><![CDATA[$V{" + FooterColumn[j] + "_VAR}]]></textFieldExpression>");
                            }
                            else if (FooterColumnType.get(j).equals("String") && FooterColumnCalc[j].equals("count"))
                            {
                                pw.println("                    <textFieldExpression class=\"java.lang.Integer\"><![CDATA[$V{" + FooterColumn[j] + "_VAR}]]></textFieldExpression>");
                            }
                            else
                            {
                                pw.println("                    <textFieldExpression class=\"java.lang." + FooterColumnType.get(j) + "\"><![CDATA[$V{" + FooterColumn[j] + "_VAR}]]></textFieldExpression>");
                            }

                            pw.println("                </textField>");
                            x1 += columnSize;
                            break;
                        }
                    }
                    if (j == footerColumnLen)
                    {
                        x1 += columnSize;
                    }
                }
                pw.println("    		<line>");
                pw.println("    			<reportElement x=\"0\" y=\"0\" width=\"" + columnWidth + "\" height=\"1\"/>");
                pw.println("    		</line>");
                pw.println("            </band>");
                pw.println("        </groupFooter>");
            }
            pw.println("    </group>");
        }
        pw.println("    <title>");
        pw.println("        <band height=\"40\" splitType=\"Stretch\">");
        pw.println("            <staticText>");
        pw.println("                <reportElement mode=\"Opaque\" x=\"420\" y=\"20\" width=\"" + (sEntityBean.getReportTitle().length() * 12) + "\" height=\"20\"/>");
        pw.println("                    <box leftPadding=\"8\"/>");
        pw.println("                    <textElement rotation=\"None\" lineSpacing=\"Single\" markup=\"none\">");
        pw.println("                        <font size=\"16\" isBold=\"true\" isUnderline=\"true\" isStrikeThrough=\"false\" isPdfEmbedded=\"false\"/>");
        pw.println("                    </textElement>");
        pw.println("                <text><![CDATA[" + sEntityBean.getReportTitle() + "]]></text>");
        pw.println("            </staticText>");
        pw.println("        </band>");
        pw.println("    </title>");
        pw.println("    <columnHeader>");
        int x = 0;
        columnSize = columnWidth / (numOfColumns);
        pw.println("        <band height=\"28\" splitType=\"Stretch\">");

        //for titlecasae of column name
        String[] colNames = new String[sEntityBean.getSelectedColumns().length];
        System.arraycopy(sEntityBean.getSelectedColumns(), 0, colNames, 0, sEntityBean.getSelectedColumns().length);

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
        }
        //for titlecasae of column name
        for (int i = 0; i < numOfColumns; i++)
        {
            if (i == (numOfColumns - 1))
            {
                columnSize = columnWidth - ((numOfColumns - 1) * columnSize);
            }
            pw.println("            <staticText>");
            pw.println("                <reportElement x=\"" + x + "\" y=\"0\" width=\"" + columnSize + "\" height=\"28\"/>");
            pw.println("                <textElement/>");
            pw.println("                <text><![CDATA[" + colNames[i] + "]]></text>");
            pw.println("            </staticText>");
            x += columnSize;
        }

        pw.println("        </band>");
        pw.println("    </columnHeader>");
        pw.println("    <detail>");
        pw.println("        <band height=\"14\" splitType=\"Stretch\">");
        x = 0;
        columnSize = columnWidth / (numOfColumns);
        for (int i = 0; i < numOfColumns; i++)
        {
            if (i == (numOfColumns - 1))
            {
                columnSize = columnWidth - ((numOfColumns - 1) * columnSize);
            }
            pw.println("            <textField isStretchWithOverflow=\"true\">");
            pw.println("                <reportElement x=\"" + x + "\" y=\"0\" width=\"" + columnSize + "\" height=\"14\"/>");
            pw.println("                <textElement/>");

            if (tabColumnTypes.get(i).equals("Date"))
            {
                pw.println("                <textFieldExpression class=\"java.util." + tabColumnTypes.get(i) + "\"><![CDATA[$F{" + tabColumns[i] + "}]]></textFieldExpression>");
            }
            else if (tabColumnTypes.get(i).equals("Time") || tabColumnTypes.get(i).equals("Timestamp"))
            {
                pw.println("                <textFieldExpression class=\"java.sql." + tabColumnTypes.get(i) + "\"><![CDATA[$F{" + tabColumns[i] + "}]]></textFieldExpression>");
            }
            else if (tabColumnTypes.get(i).equals("BigDecimal"))
            {
                pw.println("                <textFieldExpression class=\"java.math." + tabColumnTypes.get(i) + "\"><![CDATA[$F{" + tabColumns[i] + "}]]></textFieldExpression>");
            }
            else
            {
                pw.println("                <textFieldExpression class=\"java.lang." + tabColumnTypes.get(i) + "\"><![CDATA[$F{" + tabColumns[i] + "}]]></textFieldExpression>");
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
        //if (sEntityBean.isPieChart() || sEntityBean.isBarChart() || sEntityBean.isLineChart() || sEntityBean.isAreaChart())
        if (chart)
        {
            pw.println("    <summary>");
            pw.println("        <band height=\"470\" splitType=\"Stretch\">");
            pw.println("            <image scaleImage=\"RealSize\" hAlign=\"Center\" vAlign=\"Middle\">");
            pw.println("                <reportElement x=\"110\" y=\"50\" width=\"780\" height=\"400\" isRemoveLineWhenBlank=\"true\"/>");
            pw.println("                    <imageExpression class=\"java.lang.String\"><![CDATA[$P{imgPath}]]>");
            pw.println("                    </imageExpression>");
            pw.println("            </image>");
            pw.println("        </band>");
            pw.println("    </summary>");
        }
        pw.println("</jasperReport>");
        pw.close();

    }
}
