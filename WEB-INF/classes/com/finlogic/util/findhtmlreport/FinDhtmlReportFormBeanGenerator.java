/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.findhtmlreport;

import com.finlogic.business.finstudio.findhtmlreport.FinDhtmlReportDetailEntityBean;
import com.finlogic.business.finstudio.findhtmlreport.FinDhtmlReportSummaryEntityBean;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Locale;

/**
 *
 * @author njuser
 */
public class FinDhtmlReportFormBeanGenerator
{

    public void generateReportFormBean(final FinDhtmlReportDetailEntityBean dEntityBean, final FinDhtmlReportSummaryEntityBean sEntityBean) throws FileNotFoundException
    {
        String projectName = dEntityBean.getProjectName();
        String moduleName = Character.toUpperCase(dEntityBean.getModuleName().charAt(0)) + dEntityBean.getModuleName().substring(1).toLowerCase(Locale.getDefault());
        String tomcatPath = finpack.FinPack.getProperty("tomcat1_path");
        String number = dEntityBean.getSRNo() + "RGV2";
        String filePath = tomcatPath + "/webapps/finstudio/generated/" + number + "/";
        String projectPath = filePath + projectName.toLowerCase(Locale.getDefault()) + "/WEB-INF/classes/com/finlogic/apps/"
                + projectName.toLowerCase(Locale.getDefault()) + "/" + moduleName.toLowerCase(Locale.getDefault()) + "/";
        File file = new File(projectPath);
        file.mkdirs();
        PrintWriter pw = new PrintWriter(projectPath + moduleName + "FormBean.java");

        pw.println("package com.finlogic.apps." + projectName.toLowerCase(Locale.getDefault()) + "." + moduleName.toLowerCase(Locale.getDefault()) + ";");
        pw.println();
        pw.println("public class " + moduleName + "FormBean");
        pw.println("{");
        String[] rptFields = null;
        String[] fltrFields = null;
        if (dEntityBean.isReportType())
        {
            rptFields = dEntityBean.getRptTxtName();
            /*for (String inputName : rptFields)
             {
             //inputName = inputName.toLowerCase(Locale.getDefault());
             pw.println("    private String " + inputName + ";");
             }*/
            for (int i = 0; i < rptFields.length; i++)
            {
                if ("CheckBox".equals(dEntityBean.getRptControl()[i]))
                {
                    pw.println("    private boolean " + rptFields[i] + ";");
                }
                else if (("ComboBox".equals(dEntityBean.getRptControl()[i]) || "TextLikeCombo".equals(dEntityBean.getRptControl()[i])) && "true".equals(dEntityBean.getRptRbtnMultiple()[i]))
                {
                    pw.println("    private String[] " + rptFields[i] + ";");
                }
                else
                {
                    pw.println("    private String " + rptFields[i] + ";");
                }
            }
        }
        if (dEntityBean.isFilter())
        {
            fltrFields = dEntityBean.getFltrTxtName();
            for (int i = 0; i < fltrFields.length; i++)
            {
                if ("CheckBox".equals(dEntityBean.getFltrControl()[i]))
                {
                    pw.println("    private boolean " + fltrFields[i] + ";");
                }
                else if (("ComboBox".equals(dEntityBean.getFltrControl()[i]) || "TextLikeCombo".equals(dEntityBean.getFltrControl()[i])) && "true".equals(dEntityBean.getFltrRbtnMultiple()[i]))
                {
                    pw.println("    private String[] " + fltrFields[i] + ";");
                }
                else
                {
                    pw.println("    private String " + fltrFields[i] + ";");
                }
            }
        }
        if (dEntityBean.isChart())
        {
            pw.println("    private String chart;");
        }
        if (dEntityBean.isPdf() || dEntityBean.isExcel())
        {
            pw.println("    private String reportName;");
            pw.println("    private String rdoRptFormate;");
            pw.println("    private String reportTitle;");
        }
        if (dEntityBean.isPdf())
        {
            pw.println("    private String displayMode;");
        }
        pw.println("    private String sidx;");
        pw.println("    private int rows;");
        pw.println("    private int page;");
        if (dEntityBean.isDateTimePicker())
        {
            pw.println("    private String fromDate;");
            pw.println("    private String toDate;");
        }
        if (sEntityBean != null && !sEntityBean.getDetailRefNo().equals(""))
        {
            pw.println("    private String rdoReportType;");
        }
        pw.println();
        if (dEntityBean.isChart())
        {
            pw.println("    public String getChart()");
            pw.println("    {");
            pw.println("        return chart;");
            pw.println("    }");
            pw.println();

            pw.println("    public void setChart(final String chart)");
            pw.println("    {");
            pw.println("        this.chart = chart;");
            pw.println("    }");
            pw.println();
        }
        if (dEntityBean.isReportType())
        {
            for (int i = 0; i < rptFields.length; i++)
            {
                String methodName = rptFields[i].substring(0, 1).toUpperCase(Locale.getDefault()) + rptFields[i].substring(1).toLowerCase(Locale.getDefault());
                if ("CheckBox".equals(dEntityBean.getRptControl()[i]))
                {
                    pw.println("    public boolean is" + methodName + "()");
                }
                else if (("ComboBox".equals(dEntityBean.getRptControl()[i]) || "TextLikeCombo".equals(dEntityBean.getRptControl()[i])) && "true".equals(dEntityBean.getRptRbtnMultiple()[i]))
                {
                    pw.println("    public String[] get" + methodName + "()");
                }
                else
                {
                    pw.println("    public String get" + methodName + "()");
                }
                pw.println("    {");
                pw.println("        return " + dEntityBean.getRptTxtName()[i] + ";");
                pw.println("    }");
                pw.println();
            }
            for (int i = 0; i < rptFields.length; i++)
            {
                String methodName = rptFields[i].substring(0, 1).toUpperCase(Locale.getDefault()) + rptFields[i].substring(1).toLowerCase(Locale.getDefault());
                if ("CheckBox".equals(dEntityBean.getRptControl()[i]))
                {
                    pw.println("    public void set" + methodName + "(final boolean " + methodName + "Var)");
                }
                else if (("ComboBox".equals(dEntityBean.getRptControl()[i]) || "TextLikeCombo".equals(dEntityBean.getRptControl()[i])) && "true".equals(dEntityBean.getRptRbtnMultiple()[i]))
                {
                    pw.println("    public void set" + methodName + "(final String[] " + methodName + "Var)");
                }
                else
                {
                    pw.println("    public void set" + methodName + "(final String " + methodName + "Var)");
                }
                pw.println("    {");
                pw.println("        " + dEntityBean.getRptTxtName()[i] + " = " + methodName + "Var;");
                pw.println("    }");
                pw.println();
            }
        }
        if (dEntityBean.isFilter())
        {
            for (int i = 0; i < fltrFields.length; i++)
            {
                String methodName = fltrFields[i].substring(0, 1).toUpperCase(Locale.getDefault()) + fltrFields[i].substring(1).toLowerCase(Locale.getDefault());
                if ("CheckBox".equals(dEntityBean.getFltrControl()[i]))
                {
                    pw.println("    public boolean is" + methodName + "()");
                }
                else if (("ComboBox".equals(dEntityBean.getFltrControl()[i]) || "TextLikeCombo".equals(dEntityBean.getFltrControl()[i])) && "true".equals(dEntityBean.getFltrRbtnMultiple()[i]))
                {
                    pw.println("    public String[] get" + methodName + "()");
                }
                else
                {
                    pw.println("    public String get" + methodName + "()");
                }
                pw.println("    {");
                pw.println("        return " + dEntityBean.getFltrTxtName()[i] + ";");
                pw.println("    }");
                pw.println();
            }
            for (int i = 0; i < fltrFields.length; i++)
            {
                String methodName = fltrFields[i].substring(0, 1).toUpperCase(Locale.getDefault()) + fltrFields[i].substring(1).toLowerCase(Locale.getDefault());
                if ("CheckBox".equals(dEntityBean.getFltrControl()[i]))
                {
                    pw.println("    public void set" + methodName + "(final boolean " + methodName + "Var)");
                }
                else if (("ComboBox".equals(dEntityBean.getFltrControl()[i]) || "TextLikeCombo".equals(dEntityBean.getFltrControl()[i])) && "true".equals(dEntityBean.getFltrRbtnMultiple()[i]))
                {
                    pw.println("    public void set" + methodName + "(final String[] " + methodName + "Var)");
                }
                else
                {
                    pw.println("    public void set" + methodName + "(final String " + methodName + "Var)");
                }
                pw.println("    {");
                pw.println("        " + dEntityBean.getFltrTxtName()[i] + " = " + methodName + "Var;");
                pw.println("    }");
                pw.println();
            }
        }
        if (dEntityBean.isPdf())
        {
            pw.println("    public String getDisplayMode()");
            pw.println("    {");
            pw.println("        return displayMode;");
            pw.println("    }");
            pw.println("    public void setDisplayMode(String displayMode)");
            pw.println("    {");
            pw.println("        this.displayMode = displayMode;");
            pw.println("    }");
        }

        if (dEntityBean.isPdf() || dEntityBean.isExcel())
        {
            pw.println("    public String getReportName()");
            pw.println("    {");
            pw.println("        return reportName;");
            pw.println("    }");
            pw.println();

            pw.println("    public void setReportName(final String reportName)");
            pw.println("    {");
            pw.println("        this.reportName = reportName;");
            pw.println("    }");
            pw.println();

            pw.println("    public String getRdoRptFormate()");
            pw.println("    {");
            pw.println("        return rdoRptFormate;");
            pw.println("    }");
            pw.println();

            pw.println("    public void setRdoRptFormate(final String rdoRptFormate)");
            pw.println("    {");
            pw.println("        this.rdoRptFormate = rdoRptFormate;");
            pw.println("    }");
            pw.println();
            
            pw.println("    public String getReportTitle()");
            pw.println("    {");
            pw.println("        return reportTitle;");
            pw.println("    }");
            pw.println();

            pw.println("    public void setReportTitle(final String reportTitle)");
            pw.println("    {");
            pw.println("        this.reportTitle = reportTitle;");
            pw.println("    }");
            pw.println();
        }

        if (sEntityBean != null && !sEntityBean.getDetailRefNo().equals(""))
        {
            pw.println("    public String getRdoReportType()");
            pw.println("    {");
            pw.println("        return rdoReportType;");
            pw.println("    }");
            pw.println();
            pw.println("    public void setRdoReportType(String rdoReportType)");
            pw.println("    {");
            pw.println("       this.rdoReportType = rdoReportType;");
            pw.println("    }");
        }

        pw.println("    public int getPage()");
        pw.println("    {");
        pw.println("        return page;");
        pw.println("    }");
        pw.println();

        pw.println("    public void setPage(final int page)");
        pw.println("    {");
        pw.println("        this.page = page;");
        pw.println("    }");
        pw.println();

        pw.println("    public int getRows()");
        pw.println("    {");
        pw.println("        return rows;");
        pw.println("    }");
        pw.println();

        pw.println("    public void setRows(final int rows)");
        pw.println("    {");
        pw.println("        this.rows = rows;");
        pw.println("    }");
        pw.println();

        pw.println("    public String getSidx()");
        pw.println("    {");
        pw.println("        return sidx;");
        pw.println("    }");
        pw.println();

        pw.println("    public void setSidx(final String sidx)");
        pw.println("    {");
        pw.println("        this.sidx = sidx;");
        pw.println("    }");

        if (dEntityBean.isDateTimePicker())
        {
            pw.println("    public String getFromDate()");
            pw.println("    {");
            pw.println("        return fromDate;");
            pw.println("    }");
            pw.println();

            pw.println("    public void setFromDate(final String fromDate)");
            pw.println("    {");
            pw.println("        this.fromDate = fromDate;");
            pw.println("    }");

            pw.println("    public String getToDate()");
            pw.println("    {");
            pw.println("        return toDate;");
            pw.println("    }");
            pw.println();

            pw.println("    public void setToDate(final String toDate)");
            pw.println("    {");
            pw.println("        this.toDate = toDate;");
            pw.println("    }");
        }

        pw.println("}");
        pw.close();
    }
}
