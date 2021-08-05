/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.finlogic.util.reportgenerator;

import com.finlogic.apps.finstudio.formbean.ReportGeneratorFormBean;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author njuser
 */
public class JrxmlGenerator {
    public JrxmlGenerator(ReportGeneratorFormBean reportGeneratorForm, ArrayList<String> columnNames, ArrayList<String> columnTypes) throws Exception
    {
        generateJrxml(reportGeneratorForm,columnNames,columnTypes);
    }
    public void generateJrxml(ReportGeneratorFormBean reportGeneratorForm, ArrayList<String> columnNames, ArrayList<String> columnTypes) throws Exception
    {
            String tomcatPath = finpack.FinPack.getProperty("tomcat1_path");
            long number = reportGeneratorForm.getSerialNo();
            String moduleName = reportGeneratorForm.getModuleName();
            String filePath = tomcatPath + "/webapps/finstudio/generated/" + number + "/";
            String projectPath = filePath + reportGeneratorForm.getProjectName() + "/WEB-INF/classes/com/finlogic/business/"
                + reportGeneratorForm.getProjectName().toLowerCase()+"/datamanager/";

            File file = new File(projectPath);
            file.mkdirs();
            PrintWriter pw = new PrintWriter(projectPath + moduleName + ".jrxml");

            int numOfColumns = columnNames.size();
            int columnWidth = 555;
            int columnSize = columnWidth/(numOfColumns);

            String footerColumn[] = null;
            String footerDataType[] = null;
            String calculation[] = null;
            int footerColumnX[] = null;
            if(reportGeneratorForm.isGroupFooter())
            {
                footerColumn = reportGeneratorForm.getSelectGroupFooter();
                calculation = reportGeneratorForm.getCalculation();
                for(int i=0; i<calculation.length; i++)
                {
                    if(calculation[i].equals("min"))
                        calculation[i]="Lowest";
                    if(calculation[i].equals("max"))
                        calculation[i]="Highest";
                    if(calculation[i].equals("sum"))
                        calculation[i]="Sum";
                    if(calculation[i].equals("count"))
                        calculation[i]="Count";
                    if(calculation[i].equals("avg"))
                        calculation[i]="Average";
                }
                footerDataType = new String[footerColumn.length];
                footerColumnX = new int[footerColumn.length];
                int pos =0;

                for(int i=0; i<numOfColumns; i++)
                {
                    pos= i*columnSize;
                    for(int j=0; j<footerColumn.length; j++)
                    {
                        if(columnNames.get(i).equals(footerColumn[j]))
                        {
                            footerDataType[j] = columnTypes.get(i);
                            footerColumnX[j] = pos;
                        }
                    }
                }
            }
            pw.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            pw.println("<jasperReport xmlns=\"http://jasperreports.sourceforge.net/jasperreports\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://jasperreports.sourceforge.net/jasperreports http://jasperreports.sourceforge.net/xsd/jasperreport.xsd\" name=\"report name\" pageWidth=\"595\" pageHeight=\"842\" columnWidth=\""+columnWidth+"\" leftMargin=\"20\" rightMargin=\"20\" topMargin=\"20\" bottomMargin=\"20\">");
            pw.println("    <property name=\"ireport.zoom\" value=\"1.0\"/>");
            pw.println("    <property name=\"ireport.x\" value=\"0\"/>");
            pw.println("    <property name=\"ireport.y\" value=\"0\"/>");

            for(int i=0;i<numOfColumns;i++)
                pw.println("    <field name=\""+columnNames.get(i)+"\" class=\""+columnTypes.get(i)+"\"/>");

            if(reportGeneratorForm.isGroupFooter())
            {
                for(int i=0; i<footerColumn.length;i++)
                {
                    pw.print("    <variable name=\""+footerColumn[i]+"_VAR\" class=\""+footerDataType[i]+"\" ");
                    if(!reportGeneratorForm.getGrouping().equals("none"))
                    {
                        pw.print("resetType=\"Group\" resetGroup=\""+reportGeneratorForm.getGrouping()+"Wise\" ");
                    }
                    pw.println("calculation=\""+calculation[i]+"\">");
                    pw.println("            <variableExpression><![CDATA[$F{"+footerColumn[i]+"}]]></variableExpression>");
                    pw.println("    </variable>");
                }
            }

            if(!(reportGeneratorForm.getGrouping().equals("none")))
            {
                int index = 0;
                for(String column:columnNames)
                {
                    if(column.equals(reportGeneratorForm.getGrouping()))
                    {
                        break;
                    }
                    index++;
                }
                pw.println("    <group name=\""+reportGeneratorForm.getGrouping()+"Wise\">");
                pw.println("        <groupExpression><![CDATA[$F{"+reportGeneratorForm.getGrouping()+"}]]></groupExpression>");
                pw.println("        <groupHeader>");
                pw.println("            <band height=\"15\">");
                pw.println("    		<line>");
                pw.println("    			<reportElement x=\"0\" y=\"0\" width=\""+columnWidth+"\" height=\"1\"/>");
                pw.println("    		</line>");
                pw.println("                <staticText>");
                pw.println("                    <reportElement x=\"0\" y=\"1\" width=\""+columnSize+"\" height=\"14\"/>");
                pw.println("                        <textElement>");
                pw.println("                            <font size=\"8\"/>");
                pw.println("                        </textElement>");
                pw.println("                        <text><![CDATA["+reportGeneratorForm.getGrouping()+"]]></text>");
                pw.println("                </staticText>");
                pw.println("                <textField>");
                pw.println("                    <reportElement x=\""+columnSize+"\" y=\"1\" width=\""+columnSize+"\" height=\"14\"/>");
                pw.println("                    <textElement>");
                pw.println("    			<font size=\"10\"/>");
                pw.println("                    </textElement>");
                pw.println("                    <textFieldExpression class=\""+columnTypes.get(index)+"\"><![CDATA[$F{"+reportGeneratorForm.getGrouping()+"}]]></textFieldExpression>");
                pw.println("                </textField>");
                pw.println("            </band>");
                pw.println("        </groupHeader>");
                if(reportGeneratorForm.isGroupFooter())
                {
                    pw.println("        <groupFooter>");
                    pw.println("            <band height=\"14\">");
                    
                    for(int i=0; i<numOfColumns; i++)
                    {
                        if(i==(numOfColumns-1))
                            columnSize=columnWidth-((numOfColumns-1)*columnSize);
                        for(int j=0; j<footerColumn.length;j++)
                        {
                            if(columnNames.get(i).equals(footerColumn[j]))
                            {
                                pw.println("                <textField>");
                                pw.println("                    <reportElement x=\""+footerColumnX[j]+"\" y=\"0\" width=\""+columnSize+"\" height=\"14\"/>");
                                pw.println("                    <textElement textAlignment=\"Left\">");
                                pw.println("                        <font size=\"12\"/>");
                                pw.println("                    </textElement>");
                                pw.println("                    <textFieldExpression class=\""+footerDataType[j]+"\"><![CDATA[$V{"+footerColumn[j]+"_VAR}]]></textFieldExpression>");
                                pw.println("                </textField>");
                            }
                        }
                    }
                    
    //                pw.println("    		<line>");
    //                pw.println("    			<reportElement x=\"0\" y=\"0\" width=\""+columnWidth+"\" height=\"1\"/>");
    //                pw.println("    		</line>");
                    pw.println("            </band>");
                    pw.println("        </groupFooter>");
                }
                pw.println("    </group>");
            }
            pw.println("    <title>");
            pw.println("        <band height=\"20\" splitType=\"Stretch\">");
            pw.println("            <staticText>");
            pw.println("                <reportElement x=\"0\" y=\"0\" width=\"555\" height=\"20\"/>");
            pw.println("                    <textElement textAlignment=\"Center\"/>");
            pw.println("                    <text><![CDATA[Report]]></text>");
            pw.println("            </staticText>");
            pw.println("        </band>");
            pw.println("    </title>");
            
            pw.println("    <columnHeader>");
            int x=0;
            columnSize = columnWidth/(numOfColumns);
            pw.println("        <band height=\"28\" splitType=\"Stretch\">");
            for(int i=0;i<numOfColumns;i++)
            {
                if(i==(numOfColumns-1))
                    columnSize=columnWidth-((numOfColumns-1)*columnSize);
                pw.println("            <staticText>");
                pw.println("                <reportElement x=\""+x+"\" y=\"0\" width=\""+columnSize+"\" height=\"28\"/>");
                pw.println("                <textElement/>");
                pw.println("                <text><![CDATA["+columnNames.get(i)+"]]></text>");
                pw.println("            </staticText>");
                x+=columnSize;
            }
            pw.println("        </band>");
            pw.println("    </columnHeader>");
            pw.println("    <detail>");
            pw.println("        <band height=\"14\" splitType=\"Stretch\">");
            x=0;
            columnSize = columnWidth/(numOfColumns);
            for(int i=0;i<numOfColumns;i++)
            {
                if(i==(numOfColumns-1))
                    columnSize=columnWidth-((numOfColumns-1)*columnSize);
                pw.println("            <textField>");
                pw.println("                <reportElement x=\""+x+"\" y=\"0\" width=\""+columnSize+"\" height=\"14\"/>");
                pw.println("                <textElement/>");
                pw.println("                <textFieldExpression class=\""+columnTypes.get(i)+"\"><![CDATA[$F{"+columnNames.get(i)+"}]]></textFieldExpression>");
                pw.println("            </textField>");
                x+=columnSize;
            }
            pw.println("        </band>");
            pw.println("    </detail>");
            pw.println("    <columnFooter>");
            pw.println("        <band splitType=\"Stretch\"/>");
            pw.println("    </columnFooter>");
            pw.println("</jasperReport>");
            pw.close();
        }
}
