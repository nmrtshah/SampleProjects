/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.finmaster;

import com.finlogic.apps.finstudio.finmaster.FinMasterFormBean;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Locale;

/**
 *
 * @author Sonam Patel
 */
public class MasterDataManagerGenerator
{

    private static final String CHK = "CheckBox";
    private static final String COMBO = "ComboBox";
    private static final String TXTLIKECOMBO = "TextLikeCombo";
    private static final String FILE = "File";
    private static final String FILEBOX = "FileBox";
    private static final String YESNO = "yesno";
    private static final String YN = "yn";
    private static final String TRUE = "true";
    private static final String QUERY = "Query";

    public void generateMasterDataManager(final FinMasterFormBean formBean) throws FileNotFoundException, UnsupportedEncodingException
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
        pw = new PrintWriter(projectPath + moduleName + "DataManager.java");

        String[] schematable;
        schematable = formBean.getCmbMasterTable().split("\\.");
        String mstTable, mstPrimKey;
        mstTable = schematable[schematable.length - 1];
        mstPrimKey = formBean.getCmbMasterTablePrimKey();
        String[] allMstColumns;
        allMstColumns = formBean.getHdnMstAllColumns();
        int mstColLen;
        mstColLen = allMstColumns.length;

        pw.println("package com.finlogic.business." + projectName.toLowerCase(Locale.getDefault()) + "." + moduleName.toLowerCase(Locale.getDefault()) + ";");
        pw.println();
        pw.println("import com.finlogic.util.persistence.SQLUtility;");
        pw.println("import java.sql.SQLException;");
        if (formBean.isChkEdit() || formBean.isChkDelete())
        {
            pw.println("import java.util.HashMap;");
        }
        pw.println("import java.util.List;");
        pw.println("import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;");
        if (formBean.isChkEdit() || formBean.isChkDelete())
        {
            pw.println("import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;");
        }
        pw.println("import org.springframework.jdbc.core.namedparam.SqlParameterSource;");
        pw.println("import org.springframework.jdbc.support.rowset.SqlRowSet;");
        pw.println();

        pw.println("public class " + moduleName + "DataManager");
        pw.println("{");
        pw.println();
        pw.println("    private static final String ALIASNAME = \"" + formBean.getCmbAliasName() + "\";");
        pw.println("    private final SQLUtility sqlUtility = new SQLUtility();");
        pw.println();
        pw.println("    public List getGridData(" + moduleName + "EntityBean entityBean) throws ClassNotFoundException, SQLException");
        pw.println("    {");
        pw.println("        SqlParameterSource sps;");
        pw.println("        sps = new BeanPropertySqlParameterSource(entityBean);");
        pw.println("        StringBuilder query;");
        pw.println("        query = new StringBuilder();");
        pw.print("        query.append(\"SELECT ");

        for (int i = 0; i < mstColLen; i++)
        {
            if (i == mstColLen - 1)
            {
                pw.print(allMstColumns[i]);
            }
            else
            {
                pw.print(allMstColumns[i] + ", ");
            }
        }
        if (formBean.isSchemaisalias())
        {
            pw.println(" FROM " + mstTable + " WHERE 1=1\");");
        }
        else
        {
            pw.println(" FROM " + formBean.getCmbMasterTable() + " WHERE 1=1\");");
        }

        if (formBean.isChkEdit())
        {
            if (formBean.getHdnEditField() != null)
            {
                String[] editFields;
                editFields = formBean.getHdnEditField();
                String[] editControls;
                editControls = formBean.getHdnEditControl();
                String[] editNames;
                editNames = formBean.getHdntxtEditName();
                String[] editValFormats;
                editValFormats = formBean.getHdnrbtnEditValueFormat();
                int editLen;
                editLen = editFields.length;
                for (int i = 0; i < editLen; i++)
                {
                    if (!"None".equals(editFields[i]))
                    {
                        String methodName;
                        if (editControls[i].equals(COMBO) || editControls[i].equals(TXTLIKECOMBO))
                        {
                            methodName = "get" + editNames[i].substring(0, 1).toUpperCase() + editNames[i].substring(1);
                            if (formBean.getHdnrbtnEditMultiple()[i].equals(TRUE))
                            {
                                comboCode(pw, formBean, editFields[i], methodName);
                            }
                            else
                            {
                                textCode(pw, formBean, editFields[i], methodName, editNames[i], false);
                            }
                        }
                        else if (editControls[i].equals(CHK))
                        {
                            methodName = "is" + editNames[i].substring(0, 1).toUpperCase() + editNames[i].substring(1);
                            checkboxCode(pw, editFields[i], methodName, editValFormats[i], "edit", true);
                        }
                        else
                        {
                            if (!(editControls[i].equals(FILE) || editControls[i].equals(FILEBOX)))
                            {
                                methodName = "get" + editNames[i].substring(0, 1).toUpperCase() + editNames[i].substring(1);
                                textCode(pw, formBean, editFields[i], methodName, editNames[i], true);
                            }
                        }
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
                String[] delNames;
                delNames = formBean.getHdntxtDeleteName();
                String[] delValFormats;
                delValFormats = formBean.getHdnrbtnDeleteValueFormat();
                int delLen;
                delLen = delFields.length;
                for (int i = 0; i < delLen; i++)
                {
                    if (!"None".equals(delFields[i]))
                    {
                        String methodName;
                        if (delControls[i].equals(COMBO) || delControls[i].equals(TXTLIKECOMBO))
                        {
                            methodName = "get" + delNames[i].substring(0, 1).toUpperCase() + delNames[i].substring(1);
                            if (formBean.getHdnrbtnDeleteMultiple()[i].equals(TRUE))
                            {
                                comboCode(pw, formBean, delFields[i], methodName);
                            }
                            else
                            {
                                textCode(pw, formBean, delFields[i], methodName, delNames[i], false);
                            }
                        }
                        else if (delControls[i].equals(CHK))
                        {
                            methodName = "is" + delNames[i].substring(0, 1).toUpperCase() + delNames[i].substring(1);
                            checkboxCode(pw, delFields[i], methodName, delValFormats[i], "delete", true);
                        }
                        else
                        {
                            if (!(delControls[i].equals(FILE) || delControls[i].equals(FILEBOX)))
                            {
                                methodName = "get" + delNames[i].substring(0, 1).toUpperCase() + delNames[i].substring(1);
                                textCode(pw, formBean, delFields[i], methodName, delNames[i], true);
                            }
                        }
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
                String[] viewNames;
                viewNames = formBean.getHdntxtViewName();
                String[] viewValFormats;
                viewValFormats = formBean.getHdnrbtnViewValueFormat();
                int viewLen;
                viewLen = viewFields.length;
                for (int i = 0; i < viewLen; i++)
                {
                    if (!"None".equals(viewFields[i]))
                    {
                        String methodName;
                        if (viewControls[i].equals(COMBO) || viewControls[i].equals(TXTLIKECOMBO))
                        {
                            methodName = "get" + viewNames[i].substring(0, 1).toUpperCase() + viewNames[i].substring(1);
                            if (formBean.getHdnrbtnViewMultiple()[i].equals(TRUE))
                            {
                                comboCode(pw, formBean, viewFields[i], methodName);
                            }
                            else
                            {
                                textCode(pw, formBean, viewFields[i], methodName, viewNames[i], false);
                            }
                        }
                        else if (viewControls[i].equals(CHK))
                        {
                            methodName = "is" + viewNames[i].substring(0, 1).toUpperCase() + viewNames[i].substring(1);
                            checkboxCode(pw, viewFields[i], methodName, viewValFormats[i], "view", true);
                        }
                        else
                        {
                            if (!(viewControls[i].equals(FILE) || viewControls[i].equals(FILEBOX)))
                            {
                                methodName = "get" + viewNames[i].substring(0, 1).toUpperCase() + viewNames[i].substring(1);
                                textCode(pw, formBean, viewFields[i], methodName, viewNames[i], true);
                            }
                        }
                    }
                }
            }
        }
        pw.println("        return sqlUtility.getList(ALIASNAME, query.toString(), sps);");
        pw.println("    }");
        pw.println();

        String[] mstAddFields;
        mstAddFields = formBean.getHdnAddField();
        boolean[] mstAddSel;
        mstAddSel = formBean.getHdnChkShowAdd();
        int mstAddLen;
        mstAddLen = mstAddFields.length;
        if (formBean.isChkAdd())
        {
            pw.println("    public int insertMaster(" + moduleName + "EntityBean entityBean) throws ClassNotFoundException, SQLException");
            pw.println("    {");
            pw.println("        SqlParameterSource sps;");
            pw.println("        sps = new BeanPropertySqlParameterSource(entityBean);");
            pw.println("        StringBuilder query;");
            pw.println("        query = new StringBuilder();");
            StringBuilder query;
            query = new StringBuilder();
            if (formBean.isSchemaisalias())
            {
                query.append("        query.append(\"INSERT INTO ").append(mstTable).append(" (");
            }
            else
            {
                query.append("        query.append(\"INSERT INTO ").append(formBean.getCmbMasterTable()).append(" (");
            }

            if (formBean.getTxtDataBaseType().equals("ORACLE") && !formBean.getTxtSequence().equals(""))
            {
                query.append(mstPrimKey).append(", ");
            }

            for (int i = 0; i < mstAddLen; i++)
            {
                if (mstAddSel[i] && !"None".equals(mstAddFields[i]))
                {
                    if (formBean.getTxtDataBaseType().equals("ORACLE") && !formBean.getTxtSequence().equals(""))
                    {
                        if (!mstAddFields[i].equals(mstPrimKey))
                        {
                            query.append(mstAddFields[i]).append(", ");
                        }
                    }
                    else
                    {
                        query.append(mstAddFields[i]).append(", ");
                    }
                }
            }
            query.deleteCharAt(query.length() - 1);
            query.deleteCharAt(query.length() - 1);
            query.append(") VALUES (\");");
            pw.println(query);

            StringBuilder fieldList;
            fieldList = new StringBuilder();
            fieldList.append("        query.append(\"");
            if (formBean.getTxtDataBaseType().equals("ORACLE") && !formBean.getTxtSequence().equals(""))
            {
                fieldList.append(formBean.getTxtSequence()).append(".NEXTVAL, ");
            }
            for (int i = 0; i < mstAddLen; i++)
            {
                if (mstAddSel[i] && !"None".equals(mstAddFields[i]))
                {
                    if (formBean.getTxtDataBaseType().equals("ORACLE") && !formBean.getTxtSequence().equals(""))
                    {
                        if (!mstAddFields[i].equals(mstPrimKey))
                        {
                            fieldList.append(":").append(mstAddFields[i].toLowerCase(Locale.getDefault())).append("DB, ");
                        }
                    }
                    else
                    {
                        fieldList.append(":").append(mstAddFields[i].toLowerCase(Locale.getDefault())).append("DB, ");
                    }
                }
            }
            fieldList.deleteCharAt(fieldList.length() - 1);
            fieldList.deleteCharAt(fieldList.length() - 1);
            fieldList.append(")\");");
            pw.println(fieldList);
            pw.println("        return sqlUtility.persist(ALIASNAME, query.toString(), sps);");
            pw.println("    }");
            pw.println();
        }
        if (formBean.isChkEdit() || formBean.isChkDelete())
        {
            pw.println("    public SqlRowSet getRecByPrimeKey(String key) throws ClassNotFoundException, SQLException");
            pw.println("    {");
            pw.println("        MapSqlParameterSource param;");
            pw.println("        HashMap hmap;");
            pw.println("        hmap = new HashMap();");
            pw.println("        hmap.put(\"key\", key);");
            pw.println("        param = new MapSqlParameterSource(hmap);");
            pw.println("        StringBuilder query;");
            pw.println("        query = new StringBuilder();");
            StringBuilder query;
            query = new StringBuilder();
            query.append("        query.append(\"SELECT ");
            for (int i = 0; i < mstColLen; i++)
            {
                query.append(allMstColumns[i]).append(", ");
            }
            query.deleteCharAt(query.length() - 1);
            query.deleteCharAt(query.length() - 1);
            if (formBean.isSchemaisalias())
            {
                query.append(" FROM ").append(mstTable);
            }
            else
            {
                query.append(" FROM ").append(formBean.getCmbMasterTable());
            }
            query.append(" WHERE ").append(mstPrimKey).append(" = :key\");");
            pw.println(query);
            pw.println("        return sqlUtility.getRowSet(ALIASNAME, query.toString(), param);");
            pw.println("    }");
            pw.println();
        }
        if (formBean.isChkEdit())
        {
            pw.println("    public int updateMaster(" + moduleName + "EntityBean entityBean) throws ClassNotFoundException, SQLException");
            pw.println("    {");
            pw.println("        SqlParameterSource sps;");
            pw.println("        sps = new BeanPropertySqlParameterSource(entityBean);");
            pw.println("        StringBuilder query;");
            pw.println("        query = new StringBuilder();");
            StringBuilder query;
            query = new StringBuilder();
            if (formBean.isSchemaisalias())
            {
                query.append("        query.append(\"UPDATE ").append(mstTable).append(" SET \");");
            }
            else
            {
                query.append("        query.append(\"UPDATE ").append(formBean.getCmbMasterTable()).append(" SET \");");
            }
            pw.println(query);

            boolean[] mstEditSel;
            mstEditSel = formBean.getHdnChkShowEdit();
            int mstEditLen;
            mstEditLen = mstAddFields.length;
            StringBuilder fieldList;
            fieldList = new StringBuilder();
            fieldList.append("        query.append(\"");
            for (int i = 0; i < mstEditLen; i++)
            {
                if (mstEditSel[i] && !mstAddFields[i].equals(mstPrimKey) && !"None".equals(mstAddFields[i]))
                {
                    fieldList.append(mstAddFields[i]).append("=:").append(mstAddFields[i].toLowerCase(Locale.getDefault())).append("DB, ");
                }
            }
            fieldList.deleteCharAt(fieldList.length() - 1);
            fieldList.deleteCharAt(fieldList.length() - 1);
            fieldList.append("\");");
            pw.println(fieldList);
            pw.println("        query.append(\" WHERE " + mstPrimKey + "=:" + mstPrimKey.toLowerCase(Locale.getDefault()) + "DB\");");
            pw.println("        return sqlUtility.persist(ALIASNAME, query.toString(), sps);");
            pw.println("    }");
            pw.println();
        }
        if (formBean.isChkDelete())
        {
            pw.println("    public int deleteMaster(String key) throws ClassNotFoundException, SQLException");
            pw.println("    {");
            pw.println("        MapSqlParameterSource param;");
            pw.println("        HashMap hmap;");
            pw.println("        hmap = new HashMap();");
            pw.println("        hmap.put(\"key\", key);");
            pw.println("        param = new MapSqlParameterSource(hmap);");
            pw.println("        StringBuilder query;");
            pw.println("        query = new StringBuilder();");
            if (formBean.isSchemaisalias())
            {
                pw.println("        query.append(\"DELETE FROM " + mstTable + " WHERE " + mstPrimKey + " = :key\");");
            }
            else
            {
                pw.println("        query.append(\"DELETE FROM " + formBean.getCmbMasterTable() + " WHERE " + mstPrimKey + " = :key\");");
            }
            pw.println("        return sqlUtility.persist(ALIASNAME, query.toString(), param);");
            pw.println("    }");
            pw.println();
        }

        if (formBean.isChkAdd())
        {
            String[] addControls;
            addControls = formBean.getHdnAddControl();
            String[] addRbtnQuery;
            addRbtnQuery = formBean.getHdnrdoAddDataSrc();
            for (int i = 0; i < mstAddLen; i++)
            {
                if ((addControls[i].equals(COMBO) || addControls[i].equals(TXTLIKECOMBO)) && addRbtnQuery[i].equals(QUERY))
                {
                    String methodName;
                    if ("None".equals(mstAddFields[i]))
                    {
                        String id = formBean.getHdntxtAddId()[i];
                        methodName = "getAdd" + Character.toUpperCase(mstAddFields[i].charAt(0)) + mstAddFields[i].substring(1).toLowerCase(Locale.getDefault()) + Character.toUpperCase(id.charAt(0)) + id.substring(1).toLowerCase(Locale.getDefault());
                    }
                    else
                    {
                        methodName = "getAdd" + Character.toUpperCase(mstAddFields[i].charAt(0)) + mstAddFields[i].substring(1).toLowerCase(Locale.getDefault());
                    }
                    if ("".equals(formBean.getHdncmbAddOnchange()[i]) || "-1".equals(formBean.getHdncmbAddOnchange()[i]))
                    {
                        pw.println("    public List " + methodName + "() throws ClassNotFoundException, SQLException");
                    }
                    else
                    {
                        pw.println("    public List " + methodName + "(final String " + formBean.getHdncmbAddOnchange()[i] + ") throws ClassNotFoundException, SQLException");
                    }
                    pw.println("    {");
                    pw.println("        StringBuilder query;");
                    pw.println("        query = new StringBuilder();");
                    String query;
                    query = formBean.getHdntxtAddSrcQuery()[i];
                    query = URLDecoder.decode(query, "UTF-8");
                    query = query.replace("\"", "\\\"");

                    String tempQuery;
                    tempQuery = query;
                    while (tempQuery.contains("\r") || tempQuery.contains("\n"))
                    {
                        tempQuery = tempQuery.replaceAll("\n", "@@@");
                        tempQuery = tempQuery.replaceAll("\r", "@@@");
                    }
                    while (tempQuery.contains("@@@@@@"))
                    {
                        tempQuery = tempQuery.replaceAll("@@@@@@", "@@@");
                    }
                    tempQuery = tempQuery.replaceAll("@@@", " \");\n        query.append(\"");

                    if ("".equals(formBean.getHdncmbAddOnchange()[i]) || "-1".equals(formBean.getHdncmbAddOnchange()[i]))
                    {
                        pw.println("        query.append(\"" + tempQuery + "\");");
                    }
                    else
                    {
                        pw.println("        query.append(\"" + tempQuery.replace("|VARIABLE|", "\").append(" + formBean.getHdncmbAddOnchange()[i] + ").append(\"") + "\");");
                    }
                    pw.println("        return sqlUtility.getList(ALIASNAME, query.toString());");
                    pw.println("    }");
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
                        }
                        else
                        {
                            pw.println("    public List " + methodName + "(final String " + formBean.getHdncmbEditOnchange()[i] + ") throws ClassNotFoundException, SQLException");
                        }
                        pw.println("    {");
                        pw.println("        StringBuilder query;");
                        pw.println("        query = new StringBuilder();");
                        String query;
                        query = formBean.getHdntxtEditSrcQuery()[i];
                        query = URLDecoder.decode(query, "UTF-8");
                        query = query.replace("\"", "\\\"");

                        String tempQuery;
                        tempQuery = query;
                        while (tempQuery.contains("\r") || tempQuery.contains("\n"))
                        {
                            tempQuery = tempQuery.replaceAll("\n", "@@@");
                            tempQuery = tempQuery.replaceAll("\r", "@@@");
                        }
                        while (tempQuery.contains("@@@@@@"))
                        {
                            tempQuery = tempQuery.replaceAll("@@@@@@", "@@@");
                        }
                        tempQuery = tempQuery.replaceAll("@@@", " \");\n        query.append(\"");

                        if ("".equals(formBean.getHdncmbEditOnchange()[i]) || "-1".equals(formBean.getHdncmbEditOnchange()[i]))
                        {
                            pw.println("        query.append(\"" + tempQuery + "\");");
                        }
                        else
                        {
                            pw.println("        query.append(\"" + tempQuery.replace("|VARIABLE|", "\").append(" + formBean.getHdncmbEditOnchange()[i] + ").append(\"") + "\");");
                        }
                        pw.println("        return sqlUtility.getList(ALIASNAME, query.toString());");
                        pw.println("    }");
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
                        }
                        else
                        {
                            pw.println("    public List " + methodName + "(final String " + formBean.getHdncmbDeleteOnchange()[i] + ") throws ClassNotFoundException, SQLException");
                        }
                        pw.println("    {");
                        pw.println("        StringBuilder query;");
                        pw.println("        query = new StringBuilder();");
                        String query;
                        query = formBean.getHdntxtDeleteSrcQuery()[i];
                        query = URLDecoder.decode(query, "UTF-8");
                        query = query.replace("\"", "\\\"");

                        String tempQuery;
                        tempQuery = query;
                        while (tempQuery.contains("\r") || tempQuery.contains("\n"))
                        {
                            tempQuery = tempQuery.replaceAll("\n", "@@@");
                            tempQuery = tempQuery.replaceAll("\r", "@@@");
                        }
                        while (tempQuery.contains("@@@@@@"))
                        {
                            tempQuery = tempQuery.replaceAll("@@@@@@", "@@@");
                        }
                        tempQuery = tempQuery.replaceAll("@@@", " \");\n        query.append(\"");

                        if ("".equals(formBean.getHdncmbDeleteOnchange()[i]) || "-1".equals(formBean.getHdncmbDeleteOnchange()[i]))
                        {
                            pw.println("        query.append(\"" + tempQuery + "\");");
                        }
                        else
                        {
                            pw.println("        query.append(\"" + tempQuery.replace("|VARIABLE|", "\").append(" + formBean.getHdncmbDeleteOnchange()[i] + ").append(\"") + "\");");
                        }
                        pw.println("        return sqlUtility.getList(ALIASNAME, query.toString());");
                        pw.println("    }");
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
                        }
                        else
                        {
                            pw.println("    public List " + methodName + "(final String " + formBean.getHdncmbViewOnchange()[i] + ") throws ClassNotFoundException, SQLException");
                        }
                        pw.println("    {");
                        pw.println("        StringBuilder query;");
                        pw.println("        query = new StringBuilder();");
                        String query;
                        query = formBean.getHdntxtViewSrcQuery()[i];
                        query = URLDecoder.decode(query, "UTF-8");
                        query = query.replace("\"", "\\\"");

                        String tempQuery;
                        tempQuery = query;
                        while (tempQuery.contains("\r") || tempQuery.contains("\n"))
                        {
                            tempQuery = tempQuery.replaceAll("\n", "@@@");
                            tempQuery = tempQuery.replaceAll("\r", "@@@");
                        }
                        while (tempQuery.contains("@@@@@@"))
                        {
                            tempQuery = tempQuery.replaceAll("@@@@@@", "@@@");
                        }
                        tempQuery = tempQuery.replaceAll("@@@", " \");\n        query.append(\"");

                        if ("".equals(formBean.getHdncmbViewOnchange()[i]) || "-1".equals(formBean.getHdncmbViewOnchange()[i]))
                        {
                            pw.println("        query.append(\"" + tempQuery + "\");");
                        }
                        else
                        {
                            pw.println("        query.append(\"" + tempQuery.replace("|VARIABLE|", "\").append(" + formBean.getHdncmbViewOnchange()[i] + ").append(\"") + "\");");
                        }
                        pw.println("        return sqlUtility.getList(ALIASNAME, query.toString());");
                        pw.println("    }");
                        pw.println();
                    }
                }
            }
        }

        if (formBean.isChkView() && formBean.isChkPdf() || formBean.isChkXls())
        {
            pw.println("    public List getJasperData(" + moduleName + "EntityBean entityBean) throws ClassNotFoundException, SQLException");
            pw.println("    {");
            pw.println("        SqlParameterSource sps;");
            pw.println("        sps = new BeanPropertySqlParameterSource(entityBean);");
            pw.println("        StringBuilder query;");
            pw.println("        query = new StringBuilder();");
            StringBuilder query;
            query = new StringBuilder();
            query.append("        query.append(\"SELECT ");
            String[] mstViewFields;
            mstViewFields = formBean.getChkViewColumn();
            int mstViewLen;
            mstViewLen = mstViewFields.length;
            boolean prime = false;
            for (int i = 0; i < mstViewLen; i++)
            {
                query.append(mstViewFields[i]).append(", ");
                if (mstViewFields[i].equals(mstPrimKey))
                {
                    prime = true;
                }
            }
            query.deleteCharAt(query.length() - 1);
            query.deleteCharAt(query.length() - 1);
            if (formBean.isSchemaisalias())
            {
                pw.println(query + " FROM " + mstTable + " WHERE 1=1\");");
            }
            else
            {
                pw.println(query + " FROM " + formBean.getCmbMasterTable() + " WHERE 1=1\");");
            }

            if (formBean.getHdnViewField() != null)
            {
                String[] viewFields;
                viewFields = formBean.getHdnViewField();
                String[] viewControls;
                viewControls = formBean.getHdnViewControl();
                String[] viewNames;
                viewNames = formBean.getHdntxtViewName();
                String[] viewValFormats;
                viewValFormats = formBean.getHdnrbtnViewValueFormat();
                int viewLen;
                viewLen = viewFields.length;
                for (int i = 0; i < viewLen; i++)
                {
                    if (!"None".equals(viewFields[i]))
                    {
                        String methodName;
                        if (viewControls[i].equals(COMBO) || viewControls[i].equals(TXTLIKECOMBO))
                        {
                            methodName = "get" + viewNames[i].substring(0, 1).toUpperCase() + viewNames[i].substring(1);
                            if (formBean.getHdnrbtnViewMultiple()[i].equals(TRUE))
                            {
                                comboCode(pw, formBean, viewFields[i], methodName);
                            }
                            else
                            {
                                textCode(pw, formBean, viewFields[i], methodName, viewNames[i], false);
                            }
                        }
                        else if (viewControls[i].equals(CHK))
                        {
                            methodName = "is" + viewNames[i].substring(0, 1).toUpperCase() + viewNames[i].substring(1);
                            checkboxCode(pw, viewFields[i], methodName, viewValFormats[i], "view", false);
                        }
                        else
                        {
                            methodName = "get" + viewNames[i].substring(0, 1).toUpperCase() + viewNames[i].substring(1);
                            textCode(pw, formBean, viewFields[i], methodName, viewNames[i], true);
                        }
                    }
                }
            }
            if (prime)
            {
                pw.println("        query.append(\" ORDER BY " + mstPrimKey + "\");");
            }
            pw.println("        return sqlUtility.getList(ALIASNAME, query.toString(), sps);");
            pw.println("    }");
            pw.println();
        }
        pw.println("}");
        pw.close();
    }

    public void comboCode(final PrintWriter pw, final FinMasterFormBean formBean, final String field, final String methName)
    {
        pw.println("        if (entityBean." + methName + "() != null)");
        pw.println("        {");
        String[] allColumns;
        allColumns = formBean.getHdnMstAllColumns();
        String[] allDataTypes;
        allDataTypes = formBean.getHdnMstAllDataTypes();
        int allColLen;
        allColLen = allColumns.length;
        int j = 0;
        for (; j < allColLen; j++)
        {
            if (field.equals(allColumns[j]) && allDataTypes[j].equals("Date"))
            {
                break;
            }
        }
        if (formBean.getTxtDataBaseType().equals("ORACLE"))
        {
            if (j == allColLen)
            {
                pw.println("            query.append(\" AND " + field + " IN(\");");
            }
            else
            {
                pw.println("            query.append(\" AND TO_CHAR(" + field + ",'DD-MM-YYYY') IN(\");");
            }
            pw.println("            int len;");
            pw.println("            len = entityBean." + methName + "().length;");
            pw.println("            for (int i = 0; i < len - 1; i++)");
            pw.println("            {");
            pw.println("                query.append(\"'\").append(entityBean." + methName + "()[i]).append(\"', \");");
            pw.println("            }");
            pw.println("            query.append(\"'\").append(entityBean." + methName + "()[len - 1]).append(\"')\");");
        }
        else
        {
            pw.println("            query.append(\" AND " + field + " IN(\");");
            pw.println("            int len;");
            pw.println("            len = entityBean." + methName + "().length;");
            pw.println("            for (int i = 0; i < len - 1; i++)");
            pw.println("            {");
            if (j == allColLen)
            {
                pw.println("                query.append(\"'\").append(entityBean." + methName + "()[i]).append(\"', \");");
                pw.println("            }");
                pw.println("            query.append(\"'\").append(entityBean." + methName + "()[len - 1]).append(\"')\");");
            }
            else
            {
                pw.println("                query.append(\"STR_TO_DATE('\").append(entityBean." + methName + "()[i]).append(\"','%d-%m-%Y'), \");");
                pw.println("            }");
                pw.println("            query.append(\"STR_TO_DATE('\").append(entityBean." + methName + "()[len - 1]).append(\"','%d-%m-%Y'))\");");
            }
        }
        pw.println("        }");
    }

    public void checkboxCode(final PrintWriter pw, final String field, final String methName, final String value, final String task, final boolean applyCondition)
    {
        if (applyCondition)
        {
            pw.println("        if (entityBean.getMasterTask().equals(\"" + task + "\"))");
            pw.println("        {");

            if (value.equals(YESNO) || value.equals(YN))
            {
                String boolTrue = "";
                String boolFalse = "";
                if (value.equals(YESNO))
                {
                    boolTrue = "YES";
                    boolFalse = "NO";
                }
                else if (value.equals(YN))
                {
                    boolTrue = "Y";
                    boolFalse = "N";
                }
                pw.println("            if (entityBean." + methName + "())");
                pw.println("            {");
                pw.println("                query.append(\" AND " + field + " = '" + boolTrue + "'\");");
                pw.println("            }");
                pw.println("            else");
                pw.println("            {");
                pw.println("                query.append(\" AND " + field + " = '" + boolFalse + "'\");");
                pw.println("            }");
            }
            else
            {
                pw.println("            query.append(\" AND " + field + " = \").append(entityBean." + methName + "());");
            }
            pw.println("        }");
        }
        else
        {
            if (value.equals(YESNO) || value.equals(YN))
            {
                String boolTrue = "";
                String boolFalse = "";
                if (value.equals(YESNO))
                {
                    boolTrue = "YES";
                    boolFalse = "NO";
                }
                else if (value.equals(YN))
                {
                    boolTrue = "Y";
                    boolFalse = "N";
                }
                pw.println("        if (entityBean." + methName + "())");
                pw.println("        {");
                pw.println("            query.append(\" AND " + field + " = '" + boolTrue + "'\");");
                pw.println("        }");
                pw.println("        else");
                pw.println("        {");
                pw.println("            query.append(\" AND " + field + " = '" + boolFalse + "'\");");
                pw.println("        }");
            }
            else
            {
                pw.println("        query.append(\" AND " + field + " = \").append(entityBean." + methName + "());");
            }
        }
    }

    public void textCode(final PrintWriter pw, final FinMasterFormBean formBean, final String field, final String methName, final String ctrlId, boolean isText)
    {
        if (isText)
        {
            pw.println("        if (entityBean." + methName + "() != null && !entityBean." + methName + "().equals(\"\"))");
        }
        else
        {
            pw.println("        if (entityBean." + methName + "() != null)");
        }
        pw.println("        {");

        String[] allColumns;
        allColumns = formBean.getHdnMstAllColumns();
        String[] allDataTypes;
        allDataTypes = formBean.getHdnMstAllDataTypes();
        int allColLen;
        allColLen = allColumns.length;
        int j = 0;
        for (; j < allColLen; j++)
        {
            if (field.equals(allColumns[j]) && allDataTypes[j].equals("Date"))
            {
                break;
            }
        }

        if (j == allColLen)
        {
            pw.println("            query.append(\" AND " + field + " = :" + ctrlId + "\");");
        }
        else
        {
            if (formBean.getTxtDataBaseType().equals("ORACLE"))
            {
                pw.println("            query.append(\" AND TO_CHAR(" + field + ",'DD-MM-YYYY') = :" + ctrlId + "\");");
            }
            else
            {
                pw.println("            query.append(\" AND " + field + " = STR_TO_DATE(:" + ctrlId + ",'%d-%m-%Y')\");");
            }
        }
        pw.println("        }");
    }
}
