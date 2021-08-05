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
public class MasterManagerGenerator
{

    private static final String COMBO = "ComboBox";
    private static final String TXTLIKECOMBO = "TextLikeCombo";
    private static final String QUERY = "Query";

    public void generateMasterManager(final FinMasterFormBean formBean) throws FileNotFoundException
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
        pw = new PrintWriter(projectPath + moduleName + "Manager.java");

        pw.println("package com.finlogic.business." + projectName.toLowerCase(Locale.getDefault()) + "." + moduleName.toLowerCase(Locale.getDefault()) + ";");
        pw.println();
        pw.println("import java.sql.SQLException;");
        pw.println("import java.util.List;");
        pw.println("import org.springframework.jdbc.support.rowset.SqlRowSet;");
        pw.println();

        pw.println("public class " + moduleName + "Manager");
        pw.println("{");
        pw.println();
        pw.println("    private final " + moduleName + "DataManager dataManager = new " + moduleName + "DataManager();");
        pw.println();
        pw.println("    public List getGridData(" + moduleName + "EntityBean entityBean) throws ClassNotFoundException, SQLException");
        pw.println("    {");
        pw.println("        return dataManager.getGridData(entityBean);");
        pw.println("    }");
        pw.println();

        if (formBean.isChkAdd())
        {
            pw.println("    public int insertMaster(" + moduleName + "EntityBean entityBean) throws ClassNotFoundException, SQLException");
            pw.println("    {");
            pw.println("        return dataManager.insertMaster(entityBean);");
            pw.println("    }");
            pw.println();
        }

        if (formBean.isChkEdit() || formBean.isChkDelete())
        {
            pw.println("    public SqlRowSet getRecByPrimeKey(String key) throws ClassNotFoundException, SQLException");
            pw.println("    {");
            pw.println("        return dataManager.getRecByPrimeKey(key);");
            pw.println("    }");
            pw.println();
        }

        if (formBean.isChkEdit())
        {
            pw.println("    public int updateMaster(" + moduleName + "EntityBean entityBean) throws ClassNotFoundException, SQLException");
            pw.println("    {");
            pw.println("        return dataManager.updateMaster(entityBean);");
            pw.println("    }");
            pw.println();
        }

        if (formBean.isChkDelete())
        {
            pw.println("    public int deleteMaster(String key) throws ClassNotFoundException, SQLException");
            pw.println("    {");
            pw.println("        return dataManager.deleteMaster(key);");
            pw.println("    }");
            pw.println();
        }

        if (formBean.isChkAdd())
        {
            String[] addFields;
            addFields = formBean.getHdnAddField();
            String[] addControls;
            addControls = formBean.getHdnAddControl();
            String[] addRbtnQuery;
            addRbtnQuery = formBean.getHdnrdoAddDataSrc();
            int addLen;
            addLen = addFields.length;
            for (int i = 0; i < addLen; i++)
            {
                if ((addControls[i].equals(COMBO) || addControls[i].equals(TXTLIKECOMBO)) && addRbtnQuery[i].equals(QUERY))
                {
                    String methodName;
                    if ("None".equals(addFields[i]))
                    {
                        String id = formBean.getHdntxtAddId()[i];
                        methodName = "getAdd" + Character.toUpperCase(addFields[i].charAt(0)) + addFields[i].substring(1).toLowerCase(Locale.getDefault()) + Character.toUpperCase(id.charAt(0)) + id.substring(1).toLowerCase(Locale.getDefault());
                    }
                    else
                    {
                        methodName = "getAdd" + Character.toUpperCase(addFields[i].charAt(0)) + addFields[i].substring(1).toLowerCase(Locale.getDefault());
                    }

                    if ("".equals(formBean.getHdncmbAddOnchange()[i]) || "-1".equals(formBean.getHdncmbAddOnchange()[i]))
                    {
                        pw.println("    public List " + methodName + "() throws ClassNotFoundException, SQLException");
                        pw.println("    {");
                        pw.println("        return dataManager." + methodName + "();");
                        pw.println("    }");
                    }
                    else
                    {
                        pw.println("    public List " + methodName + "(final String " + formBean.getHdncmbAddOnchange()[i] + ") throws ClassNotFoundException, SQLException");
                        pw.println("    {");
                        pw.println("        return dataManager." + methodName + "(" + formBean.getHdncmbAddOnchange()[i] + ");");
                        pw.println("    }");
                    }
                    pw.println();
                }
            }
        }

        if (formBean.isChkEdit())
        {
            if (formBean.getHdnEditField() != null)
            {
                String[] editFields;
                editFields = formBean.getHdnEditField();
                String[] editControls;
                editControls = formBean.getHdnEditControl();
                String[] editRbtnQuery;
                editRbtnQuery = formBean.getHdnrdoEditDataSrc();
                int editLen;
                editLen = editFields.length;
                for (int i = 0; i < editLen; i++)
                {
                    if ((editControls[i].equals(COMBO) || editControls[i].equals(TXTLIKECOMBO)) && editRbtnQuery[i].equals(QUERY))
                    {
                        String methodName;
                        if ("None".equals(editFields[i]))
                        {
                            String id = formBean.getHdntxtEditId()[i];
                            methodName = "getEdit" + Character.toUpperCase(editFields[i].charAt(0)) + editFields[i].substring(1).toLowerCase(Locale.getDefault()) + Character.toUpperCase(id.charAt(0)) + id.substring(1).toLowerCase(Locale.getDefault());
                        }
                        else
                        {
                            methodName = "getEdit" + Character.toUpperCase(editFields[i].charAt(0)) + editFields[i].substring(1).toLowerCase(Locale.getDefault());
                        }

                        if ("".equals(formBean.getHdncmbEditOnchange()[i]) || "-1".equals(formBean.getHdncmbEditOnchange()[i]))
                        {
                            pw.println("    public List " + methodName + "() throws ClassNotFoundException, SQLException");
                            pw.println("    {");
                            pw.println("        return dataManager." + methodName + "();");
                            pw.println("    }");
                        }
                        else
                        {
                            pw.println("    public List " + methodName + "(final String " + formBean.getHdncmbEditOnchange()[i] + ") throws ClassNotFoundException, SQLException");
                            pw.println("    {");
                            pw.println("        return dataManager." + methodName + "(" + formBean.getHdncmbEditOnchange()[i] + ");");
                            pw.println("    }");
                        }
                        pw.println();
                    }
                }
            }
        }

        if (formBean.isChkDelete())
        {
            if (formBean.getHdnDeleteField() != null)
            {
                String[] delFields;
                delFields = formBean.getHdnDeleteField();
                String[] delControls;
                delControls = formBean.getHdnDeleteControl();
                String[] delRbtnQuery;
                delRbtnQuery = formBean.getHdnrdoDeleteDataSrc();
                int delLen;
                delLen = delFields.length;
                for (int i = 0; i < delLen; i++)
                {
                    if ((delControls[i].equals(COMBO) || delControls[i].equals(TXTLIKECOMBO)) && delRbtnQuery[i].equals(QUERY))
                    {
                        String methodName;
                        if ("None".equals(delFields[i]))
                        {
                            String id = formBean.getHdntxtDeleteId()[i];
                            methodName = "getDel" + Character.toUpperCase(delFields[i].charAt(0)) + delFields[i].substring(1).toLowerCase(Locale.getDefault()) + Character.toUpperCase(id.charAt(0)) + id.substring(1).toLowerCase(Locale.getDefault());
                        }
                        else
                        {
                            methodName = "getDel" + Character.toUpperCase(delFields[i].charAt(0)) + delFields[i].substring(1).toLowerCase(Locale.getDefault());
                        }

                        if ("".equals(formBean.getHdncmbDeleteOnchange()[i]) || "-1".equals(formBean.getHdncmbDeleteOnchange()[i]))
                        {
                            pw.println("    public List " + methodName + "() throws ClassNotFoundException, SQLException");
                            pw.println("    {");
                            pw.println("        return dataManager." + methodName + "();");
                            pw.println("    }");
                        }
                        else
                        {
                            pw.println("    public List " + methodName + "(final String " + formBean.getHdncmbDeleteOnchange()[i] + ") throws ClassNotFoundException, SQLException");
                            pw.println("    {");
                            pw.println("        return dataManager." + methodName + "(" + formBean.getHdncmbDeleteOnchange()[i] + ");");
                            pw.println("    }");
                        }
                        pw.println();
                    }
                }
            }
        }

        if (formBean.isChkView())
        {
            if (formBean.getHdnViewField() != null)
            {
                String[] viewFields;
                viewFields = formBean.getHdnViewField();
                String[] viewControls;
                viewControls = formBean.getHdnViewControl();
                String[] viewRbtnQuery;
                viewRbtnQuery = formBean.getHdnrdoViewDataSrc();
                int viewLen;
                viewLen = viewFields.length;
                for (int i = 0; i < viewLen; i++)
                {
                    if ((viewControls[i].equals(COMBO) || viewControls[i].equals(TXTLIKECOMBO)) && viewRbtnQuery[i].equals(QUERY))
                    {
                        String methodName;
                        if ("None".equals(viewFields[i]))
                        {
                            String id = formBean.getHdntxtViewId()[i];
                            methodName = "getView" + Character.toUpperCase(viewFields[i].charAt(0)) + viewFields[i].substring(1).toLowerCase(Locale.getDefault()) + Character.toUpperCase(id.charAt(0)) + id.substring(1).toLowerCase(Locale.getDefault());
                        }
                        else
                        {
                            methodName = "getView" + Character.toUpperCase(viewFields[i].charAt(0)) + viewFields[i].substring(1).toLowerCase(Locale.getDefault());
                        }

                        if ("".equals(formBean.getHdncmbViewOnchange()[i]) || "-1".equals(formBean.getHdncmbViewOnchange()[i]))
                        {
                            pw.println("    public List " + methodName + "() throws ClassNotFoundException, SQLException");
                            pw.println("    {");
                            pw.println("        return dataManager." + methodName + "();");
                            pw.println("    }");
                        }
                        else
                        {
                            pw.println("    public List " + methodName + "(final String " + formBean.getHdncmbViewOnchange()[i] + ") throws ClassNotFoundException, SQLException");
                            pw.println("    {");
                            pw.println("        return dataManager." + methodName + "(" + formBean.getHdncmbViewOnchange()[i] + ");");
                            pw.println("    }");
                        }
                        pw.println();
                    }
                }
            }
        }

        if (formBean.isChkView() && formBean.isChkPdf() || formBean.isChkXls())
        {
            pw.println("    public List getJasperData(" + moduleName + "EntityBean entityBean) throws ClassNotFoundException, SQLException");
            pw.println("    {");
            pw.println("        return dataManager.getJasperData(entityBean);");
            pw.println("    }");
            pw.println();
        }
        pw.println("}");
        pw.close();
    }
}
