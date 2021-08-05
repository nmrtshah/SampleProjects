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
public class MasterEntityBeanGenerator
{

    private static final String DATE = "Date";
    private static final String TIME = "Time";
    private static final String TIMESTAMP = "Timestamp";
    private static final String CHK = "CheckBox";
    private static final String COMBO = "ComboBox";
    private static final String TXTLIKECOMBO = "TextLikeCombo";
    private static final String FILE = "File";
    private static final String FILEBOX = "FileBox";
    private static final String TRUE = "true";
    private static final String NUMBER = "Number";
    private static final String INT = "Integer";
    private static final String LONG = "Long";
    private static final String DOUBLE = "Double";
    private static final String FLOAT = "Float";

    public void generateMasterEntityBean(final FinMasterFormBean formBean) throws FileNotFoundException
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
        projectPath = filePath + projectName.toLowerCase(Locale.getDefault()) + "/WEB-INF/classes/com/finlogic/business/"
                + projectName.toLowerCase(Locale.getDefault()) + "/" + moduleName.toLowerCase(Locale.getDefault()) + "/";

        File file;
        file = new File(projectPath);
        file.mkdirs();
        PrintWriter pw;
        pw = new PrintWriter(projectPath + moduleName + "EntityBean.java");

        String[] allMstColumns;
        allMstColumns = formBean.getHdnMstAllColumns();
        String[] allMstDataTypes;
        allMstDataTypes = formBean.getHdnMstAllDataTypes();
        int allLen;
        allLen = allMstColumns.length;

        pw.println("package com.finlogic.business." + projectName.toLowerCase(Locale.getDefault()) + "." + moduleName.toLowerCase(Locale.getDefault()) + ";");
        pw.println();

        String[] mstAEDFields;
        mstAEDFields = formBean.getHdnAddField();
        int mstAEDLen;
        mstAEDLen = mstAEDFields.length;

        for (int i = 0; i < allLen; i++)
        {
            for (int j = 0; j < mstAEDLen; j++)
            {
                if (allMstColumns[i].equals(mstAEDFields[j]) && (allMstDataTypes[i].equals("BigDecimal")))
                {
                    pw.println("import java.math.BigDecimal;");
                    break;
                }
            }
        }
        for (int i = 0; i < allLen; i++)
        {
            for (int j = 0; j < mstAEDLen; j++)
            {
                if (allMstColumns[i].equals(mstAEDFields[j]) && (allMstDataTypes[i].equals(DATE) || allMstDataTypes[i].equals(TIME) || allMstDataTypes[i].equals(TIMESTAMP)))
                {
                    pw.println("import java.util.Date;");
                    break;
                }
            }
        }

        pw.println();
        pw.println("public class " + moduleName + "EntityBean");
        pw.println("{");
        pw.println();
        pw.println("    //Common Fields Starts");

        for (int i = 0; i < allLen; i++)
        {
            if (allMstColumns[i].equals(formBean.getCmbMasterTablePrimKey()))
            {
                if (allMstDataTypes[i].equals(DATE) || allMstDataTypes[i].equals(TIME) || allMstDataTypes[i].equals(TIMESTAMP))
                {
                    pw.println("    private Date " + allMstColumns[i].toLowerCase(Locale.getDefault()) + "DB;");
                }
                else if (allMstDataTypes[i].equals(NUMBER) || allMstDataTypes[i].equals(INT))
                {
                    pw.println("    private int " + allMstColumns[i].toLowerCase(Locale.getDefault()) + "DB;");
                }
                else if (allMstDataTypes[i].equals(LONG))
                {
                    pw.println("    private long " + allMstColumns[i].toLowerCase(Locale.getDefault()) + "DB;");
                }
                else if (allMstDataTypes[i].equals(DOUBLE))
                {
                    pw.println("    private double " + allMstColumns[i].toLowerCase(Locale.getDefault()) + "DB;");
                }
                else if (allMstDataTypes[i].equals(FLOAT))
                {
                    pw.println("    private float " + allMstColumns[i].toLowerCase(Locale.getDefault()) + "DB;");
                }
                else
                {
                    pw.println("    private " + allMstDataTypes[i] + " " + allMstColumns[i].toLowerCase(Locale.getDefault()) + "DB;");
                }
                break;
            }
        }

        for (int i = 0; i < allLen; i++)
        {
            if (!allMstColumns[i].equals(formBean.getCmbMasterTablePrimKey()))
            {
                for (int j = 0; j < mstAEDLen; j++)
                {
                    if (allMstColumns[i].equals(mstAEDFields[j]))
                    {
                        if (!(formBean.getHdnAddControl()[j].equals("File") || formBean.getHdnAddControl()[j].equals("FileBox")))
                        {
                            if (allMstDataTypes[i].equals(DATE) || allMstDataTypes[i].equals(TIME) || allMstDataTypes[i].equals(TIMESTAMP))
                            {
                                pw.println("    private Date " + mstAEDFields[j].toLowerCase(Locale.getDefault()) + "DB;");
                            }
                            else if (allMstDataTypes[i].equals(NUMBER) || allMstDataTypes[i].equals(INT))
                            {
                                pw.println("    private int " + mstAEDFields[j].toLowerCase(Locale.getDefault()) + "DB;");
                            }
                            else if (allMstDataTypes[i].equals(LONG))
                            {
                                pw.println("    private long " + mstAEDFields[j].toLowerCase(Locale.getDefault()) + "DB;");
                            }
                            else if (allMstDataTypes[i].equals(DOUBLE))
                            {
                                pw.println("    private double " + mstAEDFields[j].toLowerCase(Locale.getDefault()) + "DB;");
                            }
                            else if (allMstDataTypes[i].equals(FLOAT))
                            {
                                pw.println("    private float " + mstAEDFields[j].toLowerCase(Locale.getDefault()) + "DB;");
                            }
                            else
                            {
                                pw.println("    private " + allMstDataTypes[i] + " " + mstAEDFields[j].toLowerCase(Locale.getDefault()) + "DB;");
                            }
                            break;
                        }
                    }
                }
            }
        }
        pw.println("    //Common Fields Ends");
        pw.println();

        String[] mstEditFields = null;
        String[] mstEditControls = null;
        String[] mstEditNames = null;
        int mstEditLen = 0;
        String[] mstDelFields = null;
        String[] mstDelControls = null;
        String[] mstDelNames = null;
        int mstDelLen = 0;
        String[] mstViewFields = null;
        String[] mstViewControls = null;
        String[] mstViewNames = null;
        int mstViewLen = 0;

        if (formBean.isChkEdit())
        {
            if (formBean.getHdnEditField() != null)
            {
                mstEditFields = formBean.getHdnEditField();
                mstEditControls = formBean.getHdnEditControl();
                mstEditNames = formBean.getHdntxtEditName();
                mstEditLen = mstEditControls.length;
                pw.println("    //Edit Filtering Fields Starts");
                for (int i = 0; i < mstEditLen; i++)
                {
                    if (!"None".equals(mstEditFields[i]))
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
                        else
                        {
                            if (!(mstEditControls[i].equals(FILE) || mstEditControls[i].equals(FILEBOX)))
                            {
                                pw.println("    private String " + mstEditNames[i] + ";");
                            }
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
                mstDelFields = formBean.getHdnDeleteField();
                mstDelControls = formBean.getHdnDeleteControl();
                mstDelNames = formBean.getHdntxtDeleteName();
                mstDelLen = mstDelControls.length;
                pw.println("    //Delete Filtering Fields Starts");
                for (int i = 0; i < mstDelLen; i++)
                {
                    if (!"None".equals(mstDelFields[i]))
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
                        else
                        {
                            if (!(mstDelControls[i].equals(FILE) || mstDelControls[i].equals(FILEBOX)))
                            {
                                pw.println("    private String " + mstDelNames[i] + ";");
                            }
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
                mstViewFields = formBean.getHdnViewField();
                mstViewControls = formBean.getHdnViewControl();
                mstViewNames = formBean.getHdntxtViewName();
                mstViewLen = mstViewControls.length;
                pw.println("    //View Filtering Fields Starts");
                for (int i = 0; i < mstViewLen; i++)
                {
                    if (!"None".equals(mstViewFields[i]))
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
                        else
                        {
                            if (!(mstViewControls[i].equals(FILE) || mstViewControls[i].equals(FILEBOX)))
                            {
                                pw.println("    private String " + mstViewNames[i] + ";");
                            }
                        }
                    }
                }
                pw.println("    //View Filtering Fields Ends");
                pw.println();
            }
        }
        pw.println("    private String masterTask;");
        pw.println();

        for (int i = 0; i < allLen; i++)
        {
            if (allMstColumns[i].equals(formBean.getCmbMasterTablePrimKey()))
            {
                String type;
                if (allMstDataTypes[i].equals(DATE) || allMstDataTypes[i].equals(TIME) || allMstDataTypes[i].equals(TIMESTAMP))
                {
                    type = DATE;
                }
                else if (allMstDataTypes[i].equals(NUMBER) || allMstDataTypes[i].equals(INT))
                {
                    type = "int";
                }
                else if (allMstDataTypes[i].equals(LONG))
                {
                    type = "long";
                }
                else if (allMstDataTypes[i].equals(DOUBLE))
                {
                    type = "double";
                }
                else if (allMstDataTypes[i].equals(FLOAT))
                {
                    type = "float";
                }
                else
                {
                    type = allMstDataTypes[i];
                }
                String methodName;
                methodName = formBean.getCmbMasterTablePrimKey().substring(0, 1).toUpperCase() + formBean.getCmbMasterTablePrimKey().substring(1).toLowerCase(Locale.getDefault());
                pw.println();
                pw.println("    public " + type + " get" + methodName + "DB()");
                pw.println("    {");
                pw.println("        return " + formBean.getCmbMasterTablePrimKey().toLowerCase(Locale.getDefault()) + "DB;");
                pw.println("    }");

                pw.println();
                pw.println("    public void set" + methodName + "DB(final " + type + " " + formBean.getCmbMasterTablePrimKey().toLowerCase(Locale.getDefault()) + "DB)");
                pw.println("    {");
                pw.println("        this." + formBean.getCmbMasterTablePrimKey().toLowerCase(Locale.getDefault()) + "DB = " + formBean.getCmbMasterTablePrimKey().toLowerCase(Locale.getDefault()) + "DB;");
                pw.println("    }");
                break;
            }
        }

        for (int i = 0; i < allLen; i++)
        {
            if (!allMstColumns[i].equals(formBean.getCmbMasterTablePrimKey()))
            {
                for (int j = 0; j < mstAEDLen; j++)
                {
                    if (allMstColumns[i].equals(mstAEDFields[j]))
                    {
                        if (!(formBean.getHdnAddControl()[j].equals("File") || formBean.getHdnAddControl()[j].equals("FileBox")))
                        {
                            String type;
                            if (allMstDataTypes[i].equals(DATE) || allMstDataTypes[i].equals(TIME) || allMstDataTypes[i].equals(TIMESTAMP))
                            {
                                type = DATE;
                            }
                            else if (allMstDataTypes[i].equals(NUMBER) || allMstDataTypes[i].equals(INT))
                            {
                                type = "int";
                            }
                            else if (allMstDataTypes[i].equals(LONG))
                            {
                                type = "long";
                            }
                            else if (allMstDataTypes[i].equals(DOUBLE))
                            {
                                type = "double";
                            }
                            else if (allMstDataTypes[i].equals(FLOAT))
                            {
                                type = "float";
                            }
                            else
                            {
                                type = allMstDataTypes[i];
                            }
                            String methodName;
                            methodName = mstAEDFields[j].substring(0, 1).toUpperCase() + mstAEDFields[j].substring(1).toLowerCase(Locale.getDefault());
                            pw.println();
                            pw.println("    public " + type + " get" + methodName + "DB()");
                            pw.println("    {");
                            pw.println("        return " + mstAEDFields[j].toLowerCase(Locale.getDefault()) + "DB;");
                            pw.println("    }");

                            pw.println();
                            pw.println("    public void set" + methodName + "DB(final " + type + " " + mstAEDFields[j].toLowerCase(Locale.getDefault()) + "DB)");
                            pw.println("    {");
                            pw.println("        this." + mstAEDFields[j].toLowerCase(Locale.getDefault()) + "DB = " + mstAEDFields[j].toLowerCase(Locale.getDefault()) + "DB;");
                            pw.println("    }");
                        }
                    }
                }
            }
        }

        String methName;
        if (formBean.isChkEdit())
        {
            for (int i = 0; i < mstEditLen; i++)
            {
                if (!"None".equals(mstEditFields[i]))
                {
                    methName = mstEditNames[i].substring(0, 1).toUpperCase(Locale.getDefault()) + mstEditNames[i].substring(1);
                    if (mstEditControls[i].equals(COMBO) || mstEditControls[i].equals(TXTLIKECOMBO))
                    {
                        String type = "String";
                        if (formBean.getHdnrbtnEditMultiple()[i].equals(TRUE))
                        {
                            type += "[]";
                        }
                        pw.println();
                        pw.println("    public " + type + " get" + methName + "()");
                        pw.println("    {");
                        pw.println("        return " + mstEditNames[i] + ";");
                        pw.println("    }");

                        pw.println();
                        pw.println("    public void set" + methName + "(final " + type + " " + mstEditNames[i] + ")");
                        pw.println("    {");
                        pw.println("        this." + mstEditNames[i] + " = " + mstEditNames[i] + ";");
                        pw.println("    }");
                    }
                    else if (mstEditControls[i].equals(CHK))
                    {
                        pw.println();
                        pw.println("    public boolean is" + methName + "()");
                        pw.println("    {");
                        pw.println("        return " + mstEditNames[i] + ";");
                        pw.println("    }");

                        pw.println();
                        pw.println("    public void set" + methName + "(final boolean " + mstEditNames[i] + ")");
                        pw.println("    {");
                        pw.println("        this." + mstEditNames[i] + " = " + mstEditNames[i] + ";");
                        pw.println("    }");
                    }
                    else
                    {
                        if (!(mstEditControls[i].equals(FILE) || mstEditControls[i].equals(FILEBOX)))
                        {
                            pw.println();
                            pw.println("    public String get" + methName + "()");
                            pw.println("    {");
                            pw.println("        return " + mstEditNames[i] + ";");
                            pw.println("    }");

                            pw.println();
                            pw.println("    public void set" + methName + "(final String " + mstEditNames[i] + ")");
                            pw.println("    {");
                            pw.println("        this." + mstEditNames[i] + " = " + mstEditNames[i] + ";");
                            pw.println("    }");
                        }
                    }
                }
            }
        }

        if (formBean.isChkDelete())
        {
            for (int i = 0; i < mstDelLen; i++)
            {
                if (!"None".equals(mstDelFields[i]))
                {
                    methName = mstDelNames[i].substring(0, 1).toUpperCase(Locale.getDefault()) + mstDelNames[i].substring(1);
                    if (mstDelControls[i].equals(COMBO) || mstDelControls[i].equals(TXTLIKECOMBO))
                    {
                        String type = "String";
                        if (formBean.getHdnrbtnDeleteMultiple()[i].equals(TRUE))
                        {
                            type += "[]";
                        }
                        pw.println();
                        pw.println("    public " + type + " get" + methName + "()");
                        pw.println("    {");
                        pw.println("        return " + mstDelNames[i] + ";");
                        pw.println("    }");

                        pw.println();
                        pw.println("    public void set" + methName + "(final " + type + " " + mstDelNames[i] + ")");
                        pw.println("    {");
                        pw.println("        this." + mstDelNames[i] + " = " + mstDelNames[i] + ";");
                        pw.println("    }");
                    }
                    else if (mstDelControls[i].equals(CHK))
                    {
                        pw.println();
                        pw.println("    public boolean is" + methName + "()");
                        pw.println("    {");
                        pw.println("        return " + mstDelNames[i] + ";");
                        pw.println("    }");

                        pw.println();
                        pw.println("    public void set" + methName + "(final boolean " + mstDelNames[i] + ")");
                        pw.println("    {");
                        pw.println("        this." + mstDelNames[i] + " = " + mstDelNames[i] + ";");
                        pw.println("    }");
                    }
                    else
                    {
                        if (!(mstDelControls[i].equals(FILE) || mstDelControls[i].equals(FILEBOX)))
                        {
                            pw.println();
                            pw.println("    public String get" + methName + "()");
                            pw.println("    {");
                            pw.println("        return " + mstDelNames[i] + ";");
                            pw.println("    }");

                            pw.println();
                            pw.println("    public void set" + methName + "(final String " + mstDelNames[i] + ")");
                            pw.println("    {");
                            pw.println("        this." + mstDelNames[i] + " = " + mstDelNames[i] + ";");
                            pw.println("    }");
                        }
                    }
                }
            }
        }

        if (formBean.isChkView())
        {
            for (int i = 0; i < mstViewLen; i++)
            {
                if (!"None".equals(mstViewFields[i]))
                {
                    methName = mstViewNames[i].substring(0, 1).toUpperCase(Locale.getDefault()) + mstViewNames[i].substring(1);
                    if (mstViewControls[i].equals(COMBO) || mstViewControls[i].equals(TXTLIKECOMBO))
                    {
                        String type = "String";
                        if (formBean.getHdnrbtnViewMultiple()[i].equals(TRUE))
                        {
                            type += "[]";
                        }
                        pw.println();
                        pw.println("    public " + type + " get" + methName + "()");
                        pw.println("    {");
                        pw.println("        return " + mstViewNames[i] + ";");
                        pw.println("    }");

                        pw.println();
                        pw.println("    public void set" + methName + "(final " + type + " " + mstViewNames[i] + ")");
                        pw.println("    {");
                        pw.println("        this." + mstViewNames[i] + " = " + mstViewNames[i] + ";");
                        pw.println("    }");
                    }
                    else if (mstViewControls[i].equals(CHK))
                    {
                        pw.println();
                        pw.println("    public boolean is" + methName + "()");
                        pw.println("    {");
                        pw.println("        return " + mstViewNames[i] + ";");
                        pw.println("    }");

                        pw.println();
                        pw.println("    public void set" + methName + "(final boolean " + mstViewNames[i] + ")");
                        pw.println("    {");
                        pw.println("        this." + mstViewNames[i] + " = " + mstViewNames[i] + ";");
                        pw.println("    }");
                    }
                    else
                    {
                        if (!(mstViewControls[i].equals(FILE) || mstViewControls[i].equals(FILEBOX)))
                        {
                            pw.println();
                            pw.println("    public String get" + methName + "()");
                            pw.println("    {");
                            pw.println("        return " + mstViewNames[i] + ";");
                            pw.println("    }");

                            pw.println();
                            pw.println("    public void set" + methName + "(final String " + mstViewNames[i] + ")");
                            pw.println("    {");
                            pw.println("        this." + mstViewNames[i] + " = " + mstViewNames[i] + ";");
                            pw.println("    }");
                        }
                    }
                }
            }
        }

        pw.println();
        pw.println("    public String getMasterTask()");
        pw.println("    {");
        pw.println("        return masterTask;");
        pw.println("    }");

        pw.println();
        pw.println("    public void setMasterTask(final String masterTask)");
        pw.println("    {");
        pw.println("        this.masterTask = masterTask;");
        pw.println("    }");
        pw.println("}");
        pw.close();
    }
}
