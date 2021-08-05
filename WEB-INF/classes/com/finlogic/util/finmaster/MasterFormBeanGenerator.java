/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.finmaster;

import com.finlogic.apps.finstudio.finmaster.FinMasterFormBean;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Locale;

/**
 *
 * @author Sonam Patel
 */
public class MasterFormBeanGenerator
{

    private static final String CHK = "CheckBox";
    private static final String COMBO = "ComboBox";
    private static final String TXTLIKECOMBO = "TextLikeCombo";
    private static final String FILE = "File";
    private static final String FILEBOX = "FileBox";
    private static final String TRUE = "true";

    public void generateMasterFormBean(final FinMasterFormBean formBean) throws FileNotFoundException
    {
        String projectName;
        projectName = formBean.getCmbProjectName();
        String moduleName;
        moduleName = Character.toUpperCase(formBean.getTxtModuleName().charAt(0)) + formBean.getTxtModuleName().substring(1).toLowerCase(Locale.getDefault());
        String tomcatPath;
        tomcatPath = finpack.FinPack.getProperty("tomcat1_path");
        String number;
        number = formBean.getSrNo() + "MGV2";
        String filePath;
        filePath = tomcatPath + "/webapps/finstudio/generated/" + number + "/";
        String projectPath;
        projectPath = filePath + projectName.toLowerCase(Locale.getDefault()) + "/WEB-INF/classes/com/finlogic/apps/"
                + projectName.toLowerCase(Locale.getDefault()) + "/" + moduleName.toLowerCase(Locale.getDefault()) + "/";

        File file;
        file = new File(projectPath);
        file.mkdirs();
        PrintWriter pw;
        pw = new PrintWriter(projectPath + moduleName + "FormBean.java");

        String[] mstAEDFields;
        mstAEDFields = formBean.getHdnAddField();
        String[] mstAEDControls;
        mstAEDControls = formBean.getHdnAddControl();
        String[] mstAEDNames;
        mstAEDNames = formBean.getHdntxtAddName();
        int mstAEDLen;
        mstAEDLen = mstAEDFields.length;

        pw.println("package com.finlogic.apps." + projectName.toLowerCase(Locale.getDefault()) + "." + moduleName.toLowerCase(Locale.getDefault()) + ";");
        pw.println();

        pw.println("public class " + moduleName + "FormBean");
        pw.println("{");
        pw.println();
        pw.println("    //Common Fields Starts");

        boolean flag = false;
        for (int i = 0; i < mstAEDLen; i++)
        {
            if (mstAEDFields[i].equals(formBean.getCmbMasterTablePrimKey()))
            {
                flag = true;
            }
            if (mstAEDControls[i].equals(CHK))
            {
                pw.println("    private boolean " + mstAEDNames[i] + ";");
            }
            else if (mstAEDControls[i].equals(COMBO) || mstAEDControls[i].equals(TXTLIKECOMBO))
            {
                if (formBean.getHdnrbtnAddMultiple()[i].equals(TRUE))
                {
                    pw.println("    private String[] " + mstAEDNames[i] + ";");
                }
                else
                {
                    pw.println("    private String " + mstAEDNames[i] + ";");
                }
            }
            else if (mstAEDControls[i].equals(FILEBOX))
            {
                pw.println("    private String[] " + formBean.getHdntxtAddEleName()[i] + ";");
            }
            else
            {
                if (!mstAEDControls[i].equals(FILE))
                {
                    pw.println("    private String " + mstAEDNames[i] + ";");
                }
            }
        }
        if (!flag)
        {
            pw.println("    private String " + formBean.getCmbMasterTablePrimKey().toLowerCase(Locale.getDefault()) + "PrimeKey;");
        }
        pw.println("    //Common Fields Ends");
        pw.println();

        String[] mstEditControls = null;
        String[] mstEditNames = null;
        int mstEditLen = 0;
        String[] mstDelControls = null;
        String[] mstDelNames = null;
        int mstDelLen = 0;
        String[] mstViewControls = null;
        String[] mstViewNames = null;
        int mstViewLen = 0;

        if (formBean.isChkEdit())
        {
            if (formBean.getHdnEditField() != null)
            {
                mstEditControls = formBean.getHdnEditControl();
                mstEditNames = formBean.getHdntxtEditName();
                mstEditLen = mstEditControls.length;
                pw.println("    //Edit Filtering Fields Starts");
                for (int i = 0; i < mstEditLen; i++)
                {
                    if (mstEditControls[i].equals(CHK))
                    {
                        pw.println("    private boolean " + mstEditNames[i] + ";");
                    }
                    else if (mstEditControls[i].equals(COMBO) || mstEditControls[i].equals(TXTLIKECOMBO))
                    {
                        if (formBean.getHdnrbtnEditMultiple()[i].equals(TRUE))
                        {
                            pw.println("    private String[] " + mstEditNames[i] + ";");
                        }
                        else
                        {
                            pw.println("    private String " + mstEditNames[i] + ";");
                        }
                    }
                    else if (mstEditControls[i].equals(FILEBOX))
                    {
                        pw.println("    private String[] " + formBean.getHdntxtEditEleName()[i] + ";");
                    }
                    else
                    {
                        if (!mstEditControls[i].equals(FILE))
                        {
                            pw.println("    private String " + mstEditNames[i] + ";");
                        }
                    }
                }
                pw.println("    //Edit Filtering Fields Ends");
                pw.println();
            }
        }

        if (formBean.isChkDelete())
        {
            if (formBean.getHdnDeleteField() != null)
            {
                mstDelControls = formBean.getHdnDeleteControl();
                mstDelNames = formBean.getHdntxtDeleteName();
                mstDelLen = mstDelControls.length;
                pw.println("    //Delete Filtering Fields Starts");
                for (int i = 0; i < mstDelLen; i++)
                {
                    if (mstDelControls[i].equals(CHK))
                    {
                        pw.println("    private boolean " + mstDelNames[i] + ";");
                    }
                    else if (mstDelControls[i].equals(COMBO) || mstDelControls[i].equals(TXTLIKECOMBO))
                    {
                        if (formBean.getHdnrbtnDeleteMultiple()[i].equals(TRUE))
                        {
                            pw.println("    private String[] " + mstDelNames[i] + ";");
                        }
                        else
                        {
                            pw.println("    private String " + mstDelNames[i] + ";");
                        }
                    }
                    else if (mstDelControls[i].equals(FILEBOX))
                    {
                        pw.println("    private String[] " + formBean.getHdntxtDeleteEleName()[i] + ";");
                    }
                    else
                    {
                        if (!mstDelControls[i].equals(FILE))
                        {
                            pw.println("    private String " + mstDelNames[i] + ";");
                        }
                    }
                }
                pw.println("    //Delete Filtering Fields Ends");
                pw.println();
            }
        }

        if (formBean.isChkView())
        {
            if (formBean.getHdnViewField() != null)
            {
                mstViewControls = formBean.getHdnViewControl();
                mstViewNames = formBean.getHdntxtViewName();
                mstViewLen = mstViewControls.length;
                pw.println("    //View Filtering Fields Starts");
                for (int i = 0; i < mstViewLen; i++)
                {
                    if (mstViewControls[i].equals(CHK))
                    {
                        pw.println("    private boolean " + mstViewNames[i] + ";");
                    }
                    else if (mstViewControls[i].equals(COMBO) || mstViewControls[i].equals(TXTLIKECOMBO))
                    {
                        if (formBean.getHdnrbtnViewMultiple()[i].equals(TRUE))
                        {
                            pw.println("    private String[] " + mstViewNames[i] + ";");
                        }
                        else
                        {
                            pw.println("    private String " + mstViewNames[i] + ";");
                        }
                    }
                    else if (mstViewControls[i].equals(FILEBOX))
                    {
                        pw.println("    private String[] " + formBean.getHdntxtViewEleName()[i] + ";");
                    }
                    else if (!mstViewControls[i].equals(FILE))
                    {
                        pw.println("    private String " + mstViewNames[i] + ";");
                    }
                }
                pw.println("    //View Filtering Fields Ends");
                pw.println();
            }
        }

        pw.println("    private String masterTask;");
        if (formBean.isChkPdf() || formBean.isChkXls())
        {
            pw.println("    private String reportName;");
            pw.println("    private String rdoRptFormate;");
        }

        pw.println("    private String sidx;");
        pw.println("    private int rows;");
        pw.println("    private int page;");
        pw.println();

        for (int i = 0; i < mstAEDLen; i++)
        {
            String methodName;
            methodName = mstAEDNames[i].substring(0, 1).toUpperCase() + mstAEDNames[i].substring(1);
            if (mstAEDControls[i].equals(CHK))
            {
                pw.println("    public boolean is" + methodName + "()");
                pw.println("    {");
                pw.println("        return " + mstAEDNames[i] + ";");
                pw.println("    }");
                pw.println();

                pw.println("    public void set" + methodName + "(final boolean " + mstAEDNames[i] + ")");
                pw.println("    {");
                pw.println("        this." + mstAEDNames[i] + " = " + mstAEDNames[i] + ";");
                pw.println("    }");
                pw.println();
            }
            else if (mstAEDControls[i].equals(COMBO) || mstAEDControls[i].equals(TXTLIKECOMBO))
            {
                String type = "String";
                if (formBean.getHdnrbtnAddMultiple()[i].equals(TRUE))
                {
                    type += "[]";
                }
                pw.println("    public " + type + " get" + methodName + "()");
                pw.println("    {");
                pw.println("        return " + mstAEDNames[i] + ";");
                pw.println("    }");
                pw.println();

                pw.println("    public void set" + methodName + "(final " + type + " " + mstAEDNames[i] + ")");
                pw.println("    {");
                pw.println("        this." + mstAEDNames[i] + " = " + mstAEDNames[i] + ";");
                pw.println("    }");
                pw.println();
            }
            else if (mstAEDControls[i].equals(FILEBOX))
            {
                String type = "String[]";
                String eleName = formBean.getHdntxtAddEleName()[i];
                String mthName = eleName.substring(0, 1).toUpperCase() + eleName.substring(1);

                pw.println("    public " + type + " get" + mthName + "()");
                pw.println("    {");
                pw.println("        return " + eleName + ";");
                pw.println("    }");
                pw.println();

                pw.println("    public void set" + mthName + "(final " + type + " " + eleName + ")");
                pw.println("    {");
                pw.println("        this." + eleName + " = " + eleName + ";");
                pw.println("    }");
                pw.println();
            }
            else if (!mstAEDControls[i].equals(FILE))
            {
                pw.println("    public String get" + methodName + "()");
                pw.println("    {");
                pw.println("        return " + mstAEDNames[i] + ";");
                pw.println("    }");
                pw.println();

                pw.println("    public void set" + methodName + "(final String " + mstAEDNames[i] + ")");
                pw.println("    {");
                pw.println("        this." + mstAEDNames[i] + " = " + mstAEDNames[i] + ";");
                pw.println("    }");
                pw.println();
            }
        }
        if (!flag)
        {
            String methodName;
            methodName = formBean.getCmbMasterTablePrimKey().substring(0, 1).toUpperCase(Locale.getDefault()) + formBean.getCmbMasterTablePrimKey().substring(1).toLowerCase(Locale.getDefault());
            pw.println("    public String get" + methodName + "PrimeKey()");
            pw.println("    {");
            pw.println("        return " + formBean.getCmbMasterTablePrimKey().toLowerCase(Locale.getDefault()) + "PrimeKey;");
            pw.println("    }");
            pw.println();

            pw.println("    public void set" + methodName + "PrimeKey(final String " + formBean.getCmbMasterTablePrimKey().toLowerCase(Locale.getDefault()) + "PrimeKey)");
            pw.println("    {");
            pw.println("        this." + formBean.getCmbMasterTablePrimKey().toLowerCase(Locale.getDefault()) + "PrimeKey = " + formBean.getCmbMasterTablePrimKey().toLowerCase(Locale.getDefault()) + "PrimeKey;");
            pw.println("    }");
            pw.println();
        }

        String methName;
        if (formBean.isChkEdit())
        {
            for (int i = 0; i < mstEditLen; i++)
            {
                methName = mstEditNames[i].substring(0, 1).toUpperCase(Locale.getDefault()) + mstEditNames[i].substring(1);
                if (mstEditControls[i].equals(CHK))
                {
                    pw.println("    public boolean is" + methName + "()");
                    pw.println("    {");
                    pw.println("        return " + mstEditNames[i] + ";");
                    pw.println("    }");
                    pw.println();

                    pw.println("    public void set" + methName + "(final boolean " + mstEditNames[i] + ")");
                    pw.println("    {");
                    pw.println("        this." + mstEditNames[i] + " = " + mstEditNames[i] + ";");
                    pw.println("    }");
                    pw.println();
                }
                else if (mstEditControls[i].equals(COMBO) || mstEditControls[i].equals(TXTLIKECOMBO))
                {
                    String type = "String";
                    if (formBean.getHdnrbtnEditMultiple()[i].equals(TRUE))
                    {
                        type += "[]";
                    }
                    pw.println("    public " + type + " get" + methName + "()");
                    pw.println("    {");
                    pw.println("        return " + mstEditNames[i] + ";");
                    pw.println("    }");
                    pw.println();

                    pw.println("    public void set" + methName + "(final " + type + " " + mstEditNames[i] + ")");
                    pw.println("    {");
                    pw.println("        this." + mstEditNames[i] + " = " + mstEditNames[i] + ";");
                    pw.println("    }");
                    pw.println();
                }
                else if (mstEditControls[i].equals(FILEBOX))
                {
                    String type = "String[]";
                    String eleName = formBean.getHdntxtEditEleName()[i];
                    String mthName = eleName.substring(0, 1).toUpperCase(Locale.getDefault()) + eleName.substring(1);

                    pw.println("    public " + type + " get" + mthName + "()");
                    pw.println("    {");
                    pw.println("        return " + eleName + ";");
                    pw.println("    }");
                    pw.println();

                    pw.println("    public void set" + mthName + "(final " + type + " " + eleName + ")");
                    pw.println("    {");
                    pw.println("        this." + eleName + " = " + eleName + ";");
                    pw.println("    }");
                    pw.println();
                }
                else
                {
                    if (!mstEditControls[i].equals(FILE))
                    {
                        pw.println("    public String get" + methName + "()");
                        pw.println("    {");
                        pw.println("        return " + mstEditNames[i] + ";");
                        pw.println("    }");
                        pw.println();

                        pw.println("    public void set" + methName + "(final String " + mstEditNames[i] + ")");
                        pw.println("    {");
                        pw.println("        this." + mstEditNames[i] + " = " + mstEditNames[i] + ";");
                        pw.println("    }");
                        pw.println();
                    }
                }
            }
        }

        if (formBean.isChkDelete())
        {
            for (int i = 0; i < mstDelLen; i++)
            {
                methName = mstDelNames[i].substring(0, 1).toUpperCase(Locale.getDefault()) + mstDelNames[i].substring(1);
                if (mstDelControls[i].equals(CHK))
                {
                    pw.println("    public boolean is" + methName + "()");
                    pw.println("    {");
                    pw.println("        return " + mstDelNames[i] + ";");
                    pw.println("    }");
                    pw.println();

                    pw.println("    public void set" + methName + "(final boolean " + mstDelNames[i] + ")");
                    pw.println("    {");
                    pw.println("        this." + mstDelNames[i] + " = " + mstDelNames[i] + ";");
                    pw.println("    }");
                    pw.println();
                }
                else if (mstDelControls[i].equals(COMBO) || mstDelControls[i].equals(TXTLIKECOMBO))
                {
                    String type = "String";
                    if (formBean.getHdnrbtnDeleteMultiple()[i].equals(TRUE))
                    {
                        type += "[]";
                    }
                    pw.println("    public " + type + " get" + methName + "()");
                    pw.println("    {");
                    pw.println("        return " + mstDelNames[i] + ";");
                    pw.println("    }");
                    pw.println();

                    pw.println("    public void set" + methName + "(final " + type + " " + mstDelNames[i] + ")");
                    pw.println("    {");
                    pw.println("        this." + mstDelNames[i] + " = " + mstDelNames[i] + ";");
                    pw.println("    }");
                    pw.println();
                }
                else if (mstDelControls[i].equals(FILEBOX))
                {
                    String type = "String[]";
                    String eleName = formBean.getHdntxtDeleteEleName()[i];
                    String mthName = eleName.substring(0, 1).toUpperCase(Locale.getDefault()) + eleName.substring(1);

                    pw.println("    public " + type + " get" + mthName + "()");
                    pw.println("    {");
                    pw.println("        return " + eleName + ";");
                    pw.println("    }");
                    pw.println();

                    pw.println("    public void set" + mthName + "(final " + type + " " + eleName + ")");
                    pw.println("    {");
                    pw.println("        this." + eleName + " = " + eleName + ";");
                    pw.println("    }");
                    pw.println();
                }
                else
                {
                    if (!mstDelControls[i].equals(FILE))
                    {
                        pw.println("    public String get" + methName + "()");
                        pw.println("    {");
                        pw.println("        return " + mstDelNames[i] + ";");
                        pw.println("    }");
                        pw.println();

                        pw.println("    public void set" + methName + "(final String " + mstDelNames[i] + ")");
                        pw.println("    {");
                        pw.println("        this." + mstDelNames[i] + " = " + mstDelNames[i] + ";");
                        pw.println("    }");
                        pw.println();
                    }
                }
            }
        }

        if (formBean.isChkView())
        {
            for (int i = 0; i < mstViewLen; i++)
            {
                methName = mstViewNames[i].substring(0, 1).toUpperCase(Locale.getDefault()) + mstViewNames[i].substring(1);
                if (mstViewControls[i].equals(CHK))
                {
                    pw.println("    public boolean is" + methName + "()");
                    pw.println("    {");
                    pw.println("        return " + mstViewNames[i] + ";");
                    pw.println("    }");
                    pw.println();

                    pw.println("    public void set" + methName + "(final boolean " + mstViewNames[i] + ")");
                    pw.println("    {");
                    pw.println("        this." + mstViewNames[i] + " = " + mstViewNames[i] + ";");
                    pw.println("    }");
                    pw.println();
                }
                else if (mstViewControls[i].equals(COMBO) || mstViewControls[i].equals(TXTLIKECOMBO))
                {
                    String type = "String";
                    if (formBean.getHdnrbtnViewMultiple()[i].equals(TRUE))
                    {
                        type += "[]";
                    }
                    pw.println("    public " + type + " get" + methName + "()");
                    pw.println("    {");
                    pw.println("        return " + mstViewNames[i] + ";");
                    pw.println("    }");
                    pw.println();

                    pw.println("    public void set" + methName + "(final " + type + " " + mstViewNames[i] + ")");
                    pw.println("    {");
                    pw.println("        this." + mstViewNames[i] + " = " + mstViewNames[i] + ";");
                    pw.println("    }");
                    pw.println();
                }
                else if (mstViewControls[i].equals(FILEBOX))
                {
                    String type = "String[]";
                    String eleName = formBean.getHdntxtViewEleName()[i];
                    String mthName = eleName.substring(0, 1).toUpperCase(Locale.getDefault()) + eleName.substring(1);

                    pw.println("    public " + type + " get" + mthName + "()");
                    pw.println("    {");
                    pw.println("        return " + eleName + ";");
                    pw.println("    }");
                    pw.println();

                    pw.println("    public void set" + mthName + "(final " + type + " " + eleName + ")");
                    pw.println("    {");
                    pw.println("        this." + eleName + " = " + eleName + ";");
                    pw.println("    }");
                    pw.println();
                }
                else if (!mstViewControls[i].equals(FILE))
                {
                    pw.println("    public String get" + methName + "()");
                    pw.println("    {");
                    pw.println("        return " + mstViewNames[i] + ";");
                    pw.println("    }");
                    pw.println();

                    pw.println("    public void set" + methName + "(final String " + mstViewNames[i] + ")");
                    pw.println("    {");
                    pw.println("        this." + mstViewNames[i] + " = " + mstViewNames[i] + ";");
                    pw.println("    }");
                    pw.println();
                }
            }
        }

        pw.println("    public String getMasterTask()");
        pw.println("    {");
        pw.println("        return masterTask;");
        pw.println("    }");
        pw.println();

        pw.println("    public void setMasterTask(final String masterTask)");
        pw.println("    {");
        pw.println("        this.masterTask = masterTask;");
        pw.println("    }");
        pw.println();

        if (formBean.isChkPdf() || formBean.isChkXls())
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
        pw.println("}");
        pw.close();
    }
}
