/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.finmaster;

import com.finlogic.apps.finstudio.finmaster.FinMasterFormBean;
import com.finlogic.util.WsdlParser;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

/**
 *
 * @author Sonam Patel
 */
public class MasterServiceGenerator
{

    private static final String CHK = "CheckBox";
    private static final String COMBO = "ComboBox";
    private static final String TXTLIKECOMBO = "TextLikeCombo";
    private static final String FILE = "File";
    private static final String FILEBOX = "FileBox";
    private static final String QUERY = "Query";
    private static final String WEBSERVICE = "WebService";
    private static final String COMMONCMB = "CommonCmb";
    private static final String BIGDEC = "BigDecimal";

    public void generateMasterService(final FinMasterFormBean formBean) throws FileNotFoundException
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
        pw = new PrintWriter(projectPath + moduleName + "Service.java");

        String[] allMstColumns;
        allMstColumns = formBean.getHdnMstAllColumns();
        String[] allMstDataTypes;
        allMstDataTypes = formBean.getHdnMstAllDataTypes();
        int mstLen;
        mstLen = allMstColumns.length;
        String[] mstAEDFields;
        mstAEDFields = formBean.getHdnAddField();
        String[] mstAEDNames;
        mstAEDNames = formBean.getHdntxtAddName();
        String[] mstAEDControls;
        mstAEDControls = formBean.getHdnAddControl();
        String[] mstAEDChkValues;
        mstAEDChkValues = formBean.getHdnrbtnAddValueFormat();
        int mstAEDLen;
        mstAEDLen = mstAEDFields.length;

        pw.println("package com.finlogic.apps." + projectName.toLowerCase(Locale.getDefault()) + "." + moduleName.toLowerCase(Locale.getDefault()) + ";");
        pw.println();
        pw.println("import com.finlogic.business." + projectName.toLowerCase(Locale.getDefault()) + "." + moduleName.toLowerCase(Locale.getDefault()) + "." + moduleName + "EntityBean;");
        pw.println("import com.finlogic.business." + projectName.toLowerCase(Locale.getDefault()) + "." + moduleName.toLowerCase(Locale.getDefault()) + "." + moduleName + "Manager;");
        if (formBean.isChkEdit() || formBean.isChkDelete() || formBean.isChkView())
        {
            pw.println("import com.finlogic.util.datastructure.JSONParser;");
        }
        for (int i = 0; i < mstLen; i++)
        {
            int j = 0;
            for (; j < mstAEDLen; j++)
            {
                if (allMstColumns[i].equals(mstAEDFields[j]) && (allMstDataTypes[i].equals(BIGDEC)))
                {
                    pw.println("import java.math.BigDecimal;");
                    break;
                }
            }
            if (j < mstAEDLen)
            {
                break;
            }
        }
        pw.println("import java.sql.SQLException;");

        boolean isParseEx = false;
        for (int i = 0; i < mstLen; i++)
        {
            int j = 0;
            for (; j < mstAEDLen; j++)
            {
                if (allMstColumns[i].equals(mstAEDFields[j]) && (allMstDataTypes[i].equals("Date") || allMstDataTypes[i].equals("Time") || allMstDataTypes[i].equals("Timestamp")))
                {
                    pw.println("import java.text.ParseException;");
                    pw.println("import java.text.SimpleDateFormat;");
                    isParseEx = true;
                    break;
                }
            }
            if (j < mstAEDLen)
            {
                break;
            }
        }

        pw.println("import java.util.List;");
        pw.println("import org.springframework.jdbc.support.rowset.SqlRowSet;");

        //webservice's import
        Set wsImports = new HashSet();
        WsdlParser wsdlParser = new WsdlParser();
        Set cmbVal = new HashSet();
        if (formBean.isChkAdd())
        {
            String[] addRbtnQuery;
            addRbtnQuery = formBean.getHdnrdoAddDataSrc();
            int addLen;
            addLen = mstAEDControls.length;
            for (int i = 0; i < addLen; i++)
            {
                if (mstAEDControls[i].equals(COMBO) || mstAEDControls[i].equals(TXTLIKECOMBO))
                {
                    if (addRbtnQuery[i].equals(WEBSERVICE))
                    {
                        String consumer = formBean.getHdntxtAddWsIntrface()[i].replace("Service", "Consumer");
                        wsImports.add("com.finlogic.eai.ws.consumer." + formBean.getHdntxtAddWsProject()[i] + "." + consumer);
                        wsdlParser.getWsImports(formBean.getHdntxtAddWsExps()[i], wsImports);
                        Set exps = wsdlParser.getWsThrows(formBean.getHdntxtAddWsExps()[i]);
                        if (exps.size() > 0)
                        {
                            wsImports.add("javax.xml.ws.WebServiceException");
                        }
                    }
                    else if (addRbtnQuery[i].equals(COMMONCMB))
                    {
                        String str = formBean.getHdncmbAddCommonQuery()[i].substring(0, 1).toUpperCase().concat(formBean.getHdncmbAddCommonQuery()[i].substring(1));
                        wsImports.add("com.finlogic.service." + str + "Service");
                        cmbVal.add(formBean.getHdncmbAddCommonQuery()[i]);
                    }
                }
            }
        }
        if (formBean.isChkEdit())
        {
            if (formBean.getHdnEditField() != null)
            {
                String[] editControls;
                editControls = formBean.getHdnEditControl();
                String[] editRbtnQuery;
                editRbtnQuery = formBean.getHdnrdoEditDataSrc();
                int editLen;
                editLen = editControls.length;
                for (int i = 0; i < editLen; i++)
                {
                    if (editControls[i].equals(COMBO) || editControls[i].equals(TXTLIKECOMBO))
                    {
                        if (editRbtnQuery[i].equals(WEBSERVICE))
                        {
                            String consumer = formBean.getHdntxtEditWsIntrface()[i].replace("Service", "Consumer");
                            wsImports.add("com.finlogic.eai.ws.consumer." + formBean.getHdntxtEditWsProject()[i] + "." + consumer);
                            wsdlParser.getWsImports(formBean.getHdntxtEditWsExps()[i], wsImports);
                            Set exps = wsdlParser.getWsThrows(formBean.getHdntxtEditWsExps()[i]);
                            if (exps.size() > 0)
                            {
                                wsImports.add("javax.xml.ws.WebServiceException");
                            }
                        }
                        else if (editRbtnQuery[i].equals(COMMONCMB))
                        {
                            String str = formBean.getHdncmbEditCommonQuery()[i].substring(0, 1).toUpperCase().concat(formBean.getHdncmbEditCommonQuery()[i].substring(1));
                            wsImports.add("com.finlogic.service." + str + "Service");
                            cmbVal.add(formBean.getHdncmbEditCommonQuery()[i]);
                        }
                    }
                }
            }
        }
        if (formBean.isChkDelete())
        {
            if (formBean.getHdnDeleteField() != null)
            {
                String[] delControls;
                delControls = formBean.getHdnDeleteControl();
                String[] delRbtnQuery;
                delRbtnQuery = formBean.getHdnrdoDeleteDataSrc();
                int delLen;
                delLen = delControls.length;
                for (int i = 0; i < delLen; i++)
                {
                    if (delControls[i].equals(COMBO) || delControls[i].equals(TXTLIKECOMBO))
                    {
                        if (delRbtnQuery[i].equals(WEBSERVICE))
                        {
                            String consumer = formBean.getHdntxtDeleteWsIntrface()[i].replace("Service", "Consumer");
                            wsImports.add("com.finlogic.eai.ws.consumer." + formBean.getHdntxtDeleteWsProject()[i] + "." + consumer);
                            wsdlParser.getWsImports(formBean.getHdntxtDeleteWsExps()[i], wsImports);
                            Set exps = wsdlParser.getWsThrows(formBean.getHdntxtDeleteWsExps()[i]);
                            if (exps.size() > 0)
                            {
                                wsImports.add("javax.xml.ws.WebServiceException");
                            }
                        }
                        else if (delRbtnQuery[i].equals(COMMONCMB))
                        {
                            String str = formBean.getHdncmbDeleteCommonQuery()[i].substring(0, 1).toUpperCase().concat(formBean.getHdncmbDeleteCommonQuery()[i].substring(1));
                            wsImports.add("com.finlogic.service." + str + "Service");
                            cmbVal.add(formBean.getHdncmbDeleteCommonQuery()[i]);
                        }
                    }
                }
            }
        }
        if (formBean.isChkView())
        {
            if (formBean.getHdnViewField() != null)
            {
                String[] viewControls;
                viewControls = formBean.getHdnViewControl();
                String[] viewRbtnQuery;
                viewRbtnQuery = formBean.getHdnrdoViewDataSrc();
                int viewLen;
                viewLen = viewControls.length;
                for (int i = 0; i < viewLen; i++)
                {
                    if (viewControls[i].equals(COMBO) || viewControls[i].equals(TXTLIKECOMBO))
                    {
                        if (viewRbtnQuery[i].equals(WEBSERVICE))
                        {
                            String consumer = formBean.getHdntxtViewWsIntrface()[i].replace("Service", "Consumer");
                            wsImports.add("com.finlogic.eai.ws.consumer." + formBean.getHdntxtViewWsProject()[i] + "." + consumer);
                            wsdlParser.getWsImports(formBean.getHdntxtViewWsExps()[i], wsImports);
                            Set exps = wsdlParser.getWsThrows(formBean.getHdntxtViewWsExps()[i]);
                            if (exps.size() > 0)
                            {
                                wsImports.add("javax.xml.ws.WebServiceException");
                            }
                        }
                        if (viewRbtnQuery[i].equals(COMMONCMB))
                        {
                            String str = formBean.getHdncmbViewCommonQuery()[i].substring(0, 1).toUpperCase().concat(formBean.getHdncmbViewCommonQuery()[i].substring(1));
                            wsImports.add("com.finlogic.service." + str + "Service");
                            cmbVal.add(formBean.getHdncmbViewCommonQuery()[i]);
                        }
                    }
                }
            }
        }
        Iterator iter;
        iter = wsImports.iterator();
        while (iter.hasNext())
        {
            Object element;
            element = iter.next();
            pw.println("import " + element + ";");
        }

        pw.println();
        pw.println("public class " + moduleName + "Service");
        pw.println("{");
        pw.println();
        pw.println("    private final " + moduleName + "Manager manager = new " + moduleName + "Manager();");

        Iterator val = cmbVal.iterator();
        while (val.hasNext())
        {
            Object element = val.next();
            String str = element.toString().substring(0, 1).toUpperCase().concat(element.toString().substring(1));
            pw.println("    private final " + str + "Service " + element + "Service = new " + str + "Service();");
        }

        pw.println();

        if (isParseEx)
        {
            pw.println("    public " + moduleName + "EntityBean formBeanToEntityBean(" + moduleName + "FormBean formBean) throws ParseException");
        }
        else
        {
            pw.println("    public " + moduleName + "EntityBean formBeanToEntityBean(" + moduleName + "FormBean formBean)");
        }
        pw.println("    {");
        pw.println("        " + moduleName + "EntityBean entityBean;");
        pw.println("        entityBean = new " + moduleName + "EntityBean();");

        boolean primeflag = false;
        boolean chkflag = false;
        for (int i = 0; i < mstLen; i++)
        {
            for (int j = 0; j < mstAEDLen; j++)
            {
                if (allMstColumns[i].equals(mstAEDFields[j]))
                {
                    if (!(formBean.getHdnAddControl()[j].equals("File") || formBean.getHdnAddControl()[j].equals("FileBox")))
                    {
                        String formMeth;
                        String entMeth;
                        entMeth = "set" + allMstColumns[i].substring(0, 1).toUpperCase() + allMstColumns[i].substring(1).toLowerCase(Locale.getDefault()) + "DB";
                        if (mstAEDControls[j].equals(CHK))
                        {
                            chkflag = true;
                            formMeth = "is" + mstAEDNames[j].substring(0, 1).toUpperCase() + mstAEDNames[j].substring(1);
                        }
                        else
                        {
                            chkflag = false;
                            formMeth = "get" + mstAEDNames[j].substring(0, 1).toUpperCase() + mstAEDNames[j].substring(1);
                        }
                        if (mstAEDFields[j].equals(formBean.getCmbMasterTablePrimKey()))
                        {
                            primeflag = true;
                        }
                        if ((mstAEDControls[j].equals(COMBO) || mstAEDControls[j].equals(TXTLIKECOMBO)) && formBean.getHdnrbtnAddMultiple()[j].equals("true"))
                        {
                            pw.println("        if (formBean." + formMeth + "() != null)");
                            pw.println("        {");
                            pw.println("            String[] arr;");
                            pw.println("            arr = formBean." + formMeth + "();");
                            pw.println("            StringBuilder data;");
                            pw.println("            data = new StringBuilder();");
                            pw.println("            for (int i = 0; i < arr.length; i++)");
                            pw.println("            {");
                            pw.println("                if (i == arr.length - 1)");
                            pw.println("                {");
                            pw.println("                    data.append(arr[i]);");
                            pw.println("                }");
                            pw.println("                else");
                            pw.println("                {");
                            pw.println("                    data.append(arr[i]).append(\",\");");
                            pw.println("                }");
                            pw.println("            }");
                            pw.println("            entityBean." + entMeth + "(data.toString());");
                            pw.println("        }");
                            pw.println("        else");
                            pw.println("        {");
                            pw.println("            entityBean." + entMeth + "(null);");
                            pw.println("        }");
                        }
                        else
                        {
                            if (allMstDataTypes[i].equals("Date") || allMstDataTypes[i].equals("Time") || allMstDataTypes[i].equals("Timestamp"))
                            {
                                if (mstAEDControls[j].equals(COMBO) || mstAEDControls[j].equals(TXTLIKECOMBO))
                                {
                                    pw.println("        if (formBean." + formMeth + "() != null && !formBean." + formMeth + "().equals(\"-1\"))");
                                }
                                else
                                {
                                    pw.println("        if (formBean." + formMeth + "() != null && !formBean." + formMeth + "().equals(\"\"))");
                                }
                                pw.println("        {");
                                pw.println("            entityBean." + entMeth + "(new SimpleDateFormat(\"dd-MM-yyyy\").parse(formBean." + formMeth + "()));");
                                pw.println("        }");
                            }
                            else if (allMstDataTypes[i].equals("boolean"))
                            {
                                if (mstAEDControls[j].equals(COMBO) || mstAEDControls[j].equals(TXTLIKECOMBO))
                                {
                                    pw.println("        if (formBean." + formMeth + "() != null && !formBean." + formMeth + "().equals(\"-1\"))");
                                    pw.println("        {");
                                    pw.println("            entityBean." + entMeth + "(formBean." + formMeth + "());");
                                    pw.println("        }");
                                }
                                else
                                {
                                    pw.println("        entityBean." + entMeth + "(formBean." + formMeth + "());");
                                }
                            }
                            else if (allMstDataTypes[i].equals("Integer") || allMstDataTypes[i].equals("Number"))
                            {
                                if (chkflag)
                                {
                                    pw.println("        if (formBean." + formMeth + "())");
                                    pw.println("        {");
                                    pw.println("            entityBean." + entMeth + "(1);");
                                    pw.println("        }");
                                    pw.println("        else");
                                    pw.println("        {");
                                    pw.println("            entityBean." + entMeth + "(0);");
                                    pw.println("        }");
                                }
                                else
                                {
                                    if (mstAEDControls[j].equals(COMBO) || mstAEDControls[j].equals(TXTLIKECOMBO))
                                    {
                                        pw.println("        if (formBean." + formMeth + "() != null && !formBean." + formMeth + "().equals(\"-1\"))");
                                    }
                                    else
                                    {
                                        pw.println("        if (formBean." + formMeth + "() != null && !formBean." + formMeth + "().equals(\"\"))");
                                    }
                                    pw.println("        {");
                                    pw.println("            entityBean." + entMeth + "(Integer.parseInt(formBean." + formMeth + "()));");
                                    pw.println("        }");
                                }
                            }
                            else if (allMstDataTypes[i].equals("Long"))
                            {
                                if (chkflag)
                                {
                                    pw.println("        if (formBean." + formMeth + "())");
                                    pw.println("        {");
                                    pw.println("            entityBean." + entMeth + "(1);");
                                    pw.println("        }");
                                    pw.println("        else");
                                    pw.println("        {");
                                    pw.println("            entityBean." + entMeth + "(0);");
                                    pw.println("        }");
                                }
                                else
                                {
                                    if (mstAEDControls[j].equals(COMBO) || mstAEDControls[j].equals(TXTLIKECOMBO))
                                    {
                                        pw.println("        if (formBean." + formMeth + "() != null && !formBean." + formMeth + "().equals(\"-1\"))");
                                    }
                                    else
                                    {
                                        pw.println("        if (formBean." + formMeth + "() != null && !formBean." + formMeth + "().equals(\"\"))");
                                    }
                                    pw.println("        {");
                                    pw.println("            entityBean." + entMeth + "(Long.parseLong(formBean." + formMeth + "()));");
                                    pw.println("        }");
                                }
                            }
                            else if (allMstDataTypes[i].equals("Float"))
                            {
                                if (chkflag)
                                {
                                    pw.println("        if (formBean." + formMeth + "())");
                                    pw.println("        {");
                                    pw.println("            entityBean." + entMeth + "(1);");
                                    pw.println("        }");
                                    pw.println("        else");
                                    pw.println("        {");
                                    pw.println("            entityBean." + entMeth + "(0);");
                                    pw.println("        }");
                                }
                                else
                                {
                                    if (mstAEDControls[j].equals(COMBO) || mstAEDControls[j].equals(TXTLIKECOMBO))
                                    {
                                        pw.println("        if (formBean." + formMeth + "() != null && !formBean." + formMeth + "().equals(\"-1\"))");
                                    }
                                    else
                                    {
                                        pw.println("        if (formBean." + formMeth + "() != null && !formBean." + formMeth + "().equals(\"\"))");
                                    }
                                    pw.println("        {");
                                    pw.println("            entityBean." + entMeth + "(Float.parseFloat(formBean." + formMeth + "()));");
                                    pw.println("        }");
                                }
                            }
                            else if (allMstDataTypes[i].equals("Double") || allMstDataTypes[i].equals(BIGDEC))
                            {
                                if (chkflag)
                                {
                                    pw.println("        if (formBean." + formMeth + "())");
                                    pw.println("        {");
                                    pw.println("            entityBean." + entMeth + "(1);");
                                    pw.println("        }");
                                    pw.println("        else");
                                    pw.println("        {");
                                    pw.println("            entityBean." + entMeth + "(0);");
                                    pw.println("        }");
                                }
                                else
                                {
                                    if (mstAEDControls[j].equals(COMBO) || mstAEDControls[j].equals(TXTLIKECOMBO))
                                    {
                                        pw.println("        if (formBean." + formMeth + "() != null && !formBean." + formMeth + "().equals(\"-1\"))");
                                    }
                                    else
                                    {
                                        pw.println("        if (formBean." + formMeth + "() != null && !formBean." + formMeth + "().equals(\"\"))");
                                    }
                                    pw.println("        {");
                                    if (allMstDataTypes[i].equals("Double"))
                                    {
                                        pw.println("            entityBean." + entMeth + "(Double.parseDouble(formBean." + formMeth + "()));");
                                    }
                                    else
                                    {
                                        pw.println("            entityBean." + entMeth + "(BigDecimal.valueOf(Double.parseDouble(formBean." + formMeth + "())));");
                                    }
                                    pw.println("        }");
                                }
                            }
                            else
                            {
                                if (mstAEDControls[j].equals(CHK))
                                {
                                    if (mstAEDChkValues[j].equals("yesno"))
                                    {
                                        pw.println("        if (formBean." + formMeth + "())");
                                        pw.println("        {");
                                        pw.println("            entityBean." + entMeth + "(\"YES\");");
                                        pw.println("        }");
                                        pw.println("        else");
                                        pw.println("        {");
                                        pw.println("            entityBean." + entMeth + "(\"NO\");");
                                        pw.println("        }");
                                    }
                                    else if (mstAEDChkValues[j].equals("yn"))
                                    {
                                        pw.println("        if (formBean." + formMeth + "())");
                                        pw.println("        {");
                                        pw.println("            entityBean." + entMeth + "(\"Y\");");
                                        pw.println("        }");
                                        pw.println("        else");
                                        pw.println("        {");
                                        pw.println("            entityBean." + entMeth + "(\"N\");");
                                        pw.println("        }");
                                    }
                                    else
                                    {
                                        pw.println("        entityBean." + entMeth + "(formBean." + formMeth + "());");
                                    }
                                }
                                else
                                {
                                    if (mstAEDControls[j].equals(COMBO) || mstAEDControls[j].equals(TXTLIKECOMBO))
                                    {
                                        pw.println("        if (formBean." + formMeth + "() != null && !formBean." + formMeth + "().equals(\"-1\"))");
                                        pw.println("        {");
                                        pw.println("            entityBean." + entMeth + "(formBean." + formMeth + "());");
                                        pw.println("        }");
                                    }
                                    else
                                    {
                                        pw.println("        entityBean." + entMeth + "(formBean." + formMeth + "());");
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (!primeflag)
        {
            String formMeth;
            formMeth = "get" + formBean.getCmbMasterTablePrimKey().substring(0, 1).toUpperCase() + formBean.getCmbMasterTablePrimKey().substring(1).toLowerCase(Locale.getDefault()) + "PrimeKey";
            String entMeth;
            entMeth = "set" + formBean.getCmbMasterTablePrimKey().substring(0, 1).toUpperCase() + formBean.getCmbMasterTablePrimKey().substring(1).toLowerCase(Locale.getDefault()) + "DB";
            for (int i = 0; i < mstLen; i++)
            {
                if (allMstColumns[i].equals(formBean.getCmbMasterTablePrimKey()))
                {
                    if (allMstDataTypes[i].equals("Date") || allMstDataTypes[i].equals("Time") || allMstDataTypes[i].equals("Timestamp"))
                    {
                        pw.println("        if (formBean." + formMeth + "() != null && !formBean." + formMeth + "().equals(\"\"))");
                        pw.println("        {");
                        pw.println("            entityBean." + entMeth + "(new SimpleDateFormat(\"dd-MM-yyyy\").parse(formBean." + formMeth + "()));");
                        pw.println("        }");
                    }
                    else if (allMstDataTypes[i].equals("boolean"))
                    {
                        pw.println("        entityBean." + entMeth + "(formBean." + formMeth + "());");
                    }
                    else if (allMstDataTypes[i].equals("Integer") || allMstDataTypes[i].equals("Number"))
                    {
                        pw.println("        if (formBean." + formMeth + "() != null && !formBean." + formMeth + "().equals(\"\"))");
                        pw.println("        {");
                        pw.println("            entityBean." + entMeth + "(Integer.parseInt(formBean." + formMeth + "()));");
                        pw.println("        }");
                    }
                    else if (allMstDataTypes[i].equals("Long"))
                    {
                        pw.println("        if (formBean." + formMeth + "() != null && !formBean." + formMeth + "().equals(\"\"))");
                        pw.println("        {");
                        pw.println("            entityBean." + entMeth + "(Long.parseLong(formBean." + formMeth + "()));");
                        pw.println("        }");
                    }
                    else if (allMstDataTypes[i].equals("Float"))
                    {
                        pw.println("        if (formBean." + formMeth + "() != null && !formBean." + formMeth + "().equals(\"\"))");
                        pw.println("        {");
                        pw.println("            entityBean." + entMeth + "(Float.parseFloat(formBean." + formMeth + "()));");
                        pw.println("        }");
                    }
                    else if (allMstDataTypes[i].equals("Double"))
                    {
                        pw.println("        if (formBean." + formMeth + "() != null && !formBean." + formMeth + "().equals(\"\"))");
                        pw.println("        {");
                        pw.println("            entityBean." + entMeth + "(Double.parseDouble(formBean." + formMeth + "()));");
                        pw.println("        }");
                    }
                    else
                    {
                        pw.println("        entityBean." + entMeth + "(formBean." + formMeth + "());");
                    }
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
                String[] editNames;
                editNames = formBean.getHdntxtEditName();
                int editLen;
                editLen = editFields.length;
                for (int i = 0; i < editLen; i++)
                {
                    if (!"None".equals(editFields[i]))
                    {
                        String methodName;
                        methodName = editNames[i].substring(0, 1).toUpperCase() + editNames[i].substring(1);
                        if (editControls[i].equals(CHK))
                        {
                            pw.println("        entityBean.set" + methodName + "(formBean.is" + methodName + "());");
                        }
                        else if ((editControls[i].equals(COMBO) || editControls[i].equals(TXTLIKECOMBO)) && formBean.getHdnrbtnEditMultiple()[i].equals("false"))
                        {
                            pw.println("        if (formBean.get" + methodName + "() != null && !formBean.get" + methodName + "().equals(\"-1\"))");
                            pw.println("        {");
                            pw.println("            entityBean.set" + methodName + "(formBean.get" + methodName + "());");
                            pw.println("        }");
                        }
                        else
                        {
                            if (!(editControls[i].equals(FILE) || editControls[i].equals(FILEBOX)))
                            {
                                pw.println("        entityBean.set" + methodName + "(formBean.get" + methodName + "());");
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
                int delLen;
                delLen = delFields.length;
                for (int i = 0; i < delLen; i++)
                {
                    if (!"None".equals(delFields[i]))
                    {
                        String methodName;
                        methodName = delNames[i].substring(0, 1).toUpperCase() + delNames[i].substring(1);
                        if (delControls[i].equals(CHK))
                        {
                            pw.println("        entityBean.set" + methodName + "(formBean.is" + methodName + "());");
                        }
                        else if ((delControls[i].equals(COMBO) || delControls[i].equals(TXTLIKECOMBO)) && formBean.getHdnrbtnDeleteMultiple()[i].equals("false"))
                        {
                            pw.println("        if (formBean.get" + methodName + "() != null && !formBean.get" + methodName + "().equals(\"-1\"))");
                            pw.println("        {");
                            pw.println("            entityBean.set" + methodName + "(formBean.get" + methodName + "());");
                            pw.println("        }");
                        }
                        else
                        {
                            if (!(delControls[i].equals(FILE) || delControls[i].equals(FILEBOX)))
                            {
                                pw.println("        entityBean.set" + methodName + "(formBean.get" + methodName + "());");
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
                int viewLen;
                viewLen = viewFields.length;
                for (int i = 0; i < viewLen; i++)
                {
                    if (!"None".equals(viewFields[i]))
                    {
                        String methodName;
                        methodName = viewNames[i].substring(0, 1).toUpperCase() + viewNames[i].substring(1);
                        if (viewControls[i].equals(CHK))
                        {
                            pw.println("        entityBean.set" + methodName + "(formBean.is" + methodName + "());");
                        }
                        else if ((viewControls[i].equals(COMBO) || viewControls[i].equals(TXTLIKECOMBO)) && formBean.getHdnrbtnViewMultiple()[i].equals("false"))
                        {
                            pw.println("        if (formBean.get" + methodName + "() != null && !formBean.get" + methodName + "().equals(\"-1\"))");
                            pw.println("        {");
                            pw.println("            entityBean.set" + methodName + "(formBean.get" + methodName + "());");
                            pw.println("        }");
                        }
                        else
                        {
                            if (!(viewControls[i].equals(FILE) || viewControls[i].equals(FILEBOX)))
                            {
                                pw.println("        entityBean.set" + methodName + "(formBean.get" + methodName + "());");
                            }
                        }
                    }
                }
            }
        }

        pw.println("        entityBean.setMasterTask(formBean.getMasterTask());");
        pw.println("        return entityBean;");
        pw.println("    }");
        pw.println();
        if (isParseEx)
        {
            pw.println("    public String getGridData(" + moduleName + "FormBean formBean) throws ClassNotFoundException, ParseException, SQLException");
        }
        else
        {
            pw.println("    public String getGridData(" + moduleName + "FormBean formBean) throws ClassNotFoundException, SQLException");
        }

        pw.println("    {");
        pw.println("        List lst;");
        pw.println("        lst = manager.getGridData(formBeanToEntityBean(formBean));");
        pw.println("        JSONParser jsonParser;");
        pw.println("        jsonParser = new JSONParser();");
        pw.println("        String sidx;");
        pw.println("        sidx = formBean.getSidx();");
        pw.println("        int page;");
        pw.println("        page = formBean.getPage();");
        pw.println("        int rows;");
        pw.println("        rows = formBean.getRows();");
        pw.println("        return jsonParser.parse(lst, sidx, page, rows);");
        pw.println("    }");
        pw.println();

        if (formBean.isChkAdd())
        {
            if (isParseEx)
            {
                pw.println("    public int insertMaster(" + moduleName + "FormBean formBean) throws ClassNotFoundException, ParseException, SQLException");
            }
            else
            {
                pw.println("    public int insertMaster(" + moduleName + "FormBean formBean) throws ClassNotFoundException, SQLException");
            }
            pw.println("    {");
            pw.println("        return manager.insertMaster(formBeanToEntityBean(formBean));");
            pw.println("    }");
            pw.println();
        }

        if (formBean.isChkEdit() || formBean.isChkDelete())
        {
            pw.println("    public SqlRowSet getRecByPrimeKey(" + moduleName + "FormBean formBean) throws ClassNotFoundException, SQLException");
            pw.println("    {");
            if (!primeflag)
            {
                pw.println("        return manager.getRecByPrimeKey(formBean.get" + formBean.getCmbMasterTablePrimKey().substring(0, 1).toUpperCase() + formBean.getCmbMasterTablePrimKey().substring(1).toLowerCase(Locale.getDefault()) + "PrimeKey());");
            }
            else
            {
                for (int i = 0; i < mstLen; i++)
                {
                    if (allMstColumns[i].equals(formBean.getCmbMasterTablePrimKey()))
                    {
                        for (int j = 0; j < mstAEDLen; j++)
                        {
                            if (allMstColumns[i].equals(mstAEDFields[j]))
                            {
                                pw.println("        return manager.getRecByPrimeKey(formBean.get" + mstAEDNames[j].substring(0, 1).toUpperCase() + mstAEDNames[j].substring(1) + "());");
                                break;
                            }
                        }
                    }
                }
            }
            pw.println("    }");
            pw.println();
        }

        if (formBean.isChkEdit())
        {
            if (isParseEx)
            {
                pw.println("    public int updateMaster(" + moduleName + "FormBean formBean) throws ClassNotFoundException, ParseException, SQLException");
            }
            else
            {
                pw.println("    public int updateMaster(" + moduleName + "FormBean formBean) throws ClassNotFoundException, SQLException");
            }
            pw.println("    {");
            pw.println("        return manager.updateMaster(formBeanToEntityBean(formBean));");
            pw.println("    }");
            pw.println();
        }

        if (formBean.isChkDelete())
        {
            pw.println("    public int deleteMaster(" + moduleName + "FormBean formBean) throws ClassNotFoundException, SQLException");
            pw.println("    {");
            if (!primeflag)
            {
                pw.println("        return manager.deleteMaster(formBean.get" + formBean.getCmbMasterTablePrimKey().substring(0, 1).toUpperCase() + formBean.getCmbMasterTablePrimKey().substring(1).toLowerCase(Locale.getDefault()) + "PrimeKey());");
            }
            else
            {
                for (int i = 0; i < mstLen; i++)
                {
                    if (allMstColumns[i].equals(formBean.getCmbMasterTablePrimKey()))
                    {
                        for (int j = 0; j < mstAEDLen; j++)
                        {
                            if (allMstColumns[i].equals(mstAEDFields[j]))
                            {
                                pw.println("        return manager.deleteMaster(formBean.get" + mstAEDNames[j].substring(0, 1).toUpperCase() + mstAEDNames[j].substring(1) + "());");
                                break;
                            }
                        }
                    }
                }
            }
            pw.println("    }");
            pw.println();
        }

        if (formBean.isChkAdd())
        {
            String[] addRbtnQuery;
            addRbtnQuery = formBean.getHdnrdoAddDataSrc();
            for (int i = 0; i < mstAEDLen; i++)
            {
                if (mstAEDControls[i].equals(COMBO) || mstAEDControls[i].equals(TXTLIKECOMBO))
                {
                    String methodName;
                    if ("None".equals(mstAEDFields[i]))
                    {
                        String id = formBean.getHdntxtAddId()[i];
                        methodName = "getAdd" + Character.toUpperCase(mstAEDFields[i].charAt(0)) + mstAEDFields[i].substring(1).toLowerCase(Locale.getDefault()) + Character.toUpperCase(id.charAt(0)) + id.substring(1).toLowerCase(Locale.getDefault());
                    }
                    else
                    {
                        methodName = "getAdd" + Character.toUpperCase(mstAEDFields[i].charAt(0)) + mstAEDFields[i].substring(1).toLowerCase(Locale.getDefault());
                    }
                    if (addRbtnQuery[i].equals(QUERY))
                    {
                        if ("".equals(formBean.getHdncmbAddOnchange()[i]) || "-1".equals(formBean.getHdncmbAddOnchange()[i]))
                        {
                            pw.println("    public List " + methodName + "() throws ClassNotFoundException, SQLException");
                            pw.println("    {");
                            pw.println("        return manager." + methodName + "();");
                            pw.println("    }");
                        }
                        else
                        {
                            pw.println("    public List " + methodName + "(final String " + formBean.getHdncmbAddOnchange()[i] + ") throws ClassNotFoundException, SQLException");
                            pw.println("    {");
                            pw.println("        return manager." + methodName + "(" + formBean.getHdncmbAddOnchange()[i] + ");");
                            pw.println("    }");
                        }
                        pw.println();
                    }
                    else if (addRbtnQuery[i].equals(WEBSERVICE))
                    {
                        String consumer;
                        consumer = formBean.getHdntxtAddWsIntrface()[i].replace("Service", "Consumer");
                        String wsMethod, wsParams, wsException;
                        wsMethod = formBean.getHdncmbAddWsMethod()[i];
                        wsParams = formBean.getHdntxtAddWsParams()[i];
                        wsException = formBean.getHdntxtAddWsExps()[i];
                        makeWsMethodCode(pw, methodName, consumer, wsMethod, wsParams, wsException);
                    }
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
                    if (editControls[i].equals(COMBO) || editControls[i].equals(TXTLIKECOMBO))
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
                        if (editRbtnQuery[i].equals(QUERY))
                        {
                            if ("".equals(formBean.getHdncmbEditOnchange()[i]) || "-1".equals(formBean.getHdncmbEditOnchange()[i]))
                            {
                                pw.println("    public List " + methodName + "() throws ClassNotFoundException, SQLException");
                                pw.println("    {");
                                pw.println("        return manager." + methodName + "();");
                                pw.println("    }");
                            }
                            else
                            {
                                pw.println("    public List " + methodName + "(final String " + formBean.getHdncmbEditOnchange()[i] + ") throws ClassNotFoundException, SQLException");
                                pw.println("    {");
                                pw.println("        return manager." + methodName + "(" + formBean.getHdncmbEditOnchange()[i] + ");");
                                pw.println("    }");
                            }
                            pw.println();
                        }
                        else if (editRbtnQuery[i].equals(WEBSERVICE))
                        {
                            String consumer;
                            consumer = formBean.getHdntxtEditWsIntrface()[i].replace("Service", "Consumer");
                            String wsMethod, wsParams, wsException;
                            wsMethod = formBean.getHdncmbEditWsMethod()[i];
                            wsParams = formBean.getHdntxtEditWsParams()[i];
                            wsException = formBean.getHdntxtEditWsExps()[i];
                            makeWsMethodCode(pw, methodName, consumer, wsMethod, wsParams, wsException);
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
                String[] delRbtnQuery;
                delRbtnQuery = formBean.getHdnrdoDeleteDataSrc();
                int delLen;
                delLen = delFields.length;
                for (int i = 0; i < delLen; i++)
                {
                    if (delControls[i].equals(COMBO) || delControls[i].equals(TXTLIKECOMBO))
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
                        if (delRbtnQuery[i].equals(QUERY))
                        {
                            if ("".equals(formBean.getHdncmbDeleteOnchange()[i]) || "-1".equals(formBean.getHdncmbDeleteOnchange()[i]))
                            {
                                pw.println("    public List " + methodName + "() throws ClassNotFoundException, SQLException");
                                pw.println("    {");
                                pw.println("        return manager." + methodName + "();");
                                pw.println("    }");
                            }
                            else
                            {
                                pw.println("    public List " + methodName + "(final String " + formBean.getHdncmbDeleteOnchange()[i] + ") throws ClassNotFoundException, SQLException");
                                pw.println("    {");
                                pw.println("        return manager." + methodName + "(" + formBean.getHdncmbDeleteOnchange()[i] + ");");
                                pw.println("    }");
                            }
                            pw.println();
                        }
                        else if (delRbtnQuery[i].equals(WEBSERVICE))
                        {
                            String consumer;
                            consumer = formBean.getHdntxtDeleteWsIntrface()[i].replace("Service", "Consumer");
                            String wsMethod, wsParams, wsException;
                            wsMethod = formBean.getHdncmbDeleteWsMethod()[i];
                            wsParams = formBean.getHdntxtDeleteWsParams()[i];
                            wsException = formBean.getHdntxtDeleteWsExps()[i];
                            makeWsMethodCode(pw, methodName, consumer, wsMethod, wsParams, wsException);
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
                String[] viewRbtnQuery;
                viewRbtnQuery = formBean.getHdnrdoViewDataSrc();
                int viewLen;
                viewLen = viewFields.length;
                for (int i = 0; i < viewLen; i++)
                {
                    if (viewControls[i].equals(COMBO) || viewControls[i].equals(TXTLIKECOMBO))
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
                        if (viewRbtnQuery[i].equals(QUERY))
                        {
                            if ("".equals(formBean.getHdncmbViewOnchange()[i]) || "-1".equals(formBean.getHdncmbViewOnchange()[i]))
                            {
                                pw.println("    public List " + methodName + "() throws ClassNotFoundException, SQLException");
                                pw.println("    {");
                                pw.println("        return manager." + methodName + "();");
                                pw.println("    }");
                            }
                            else
                            {
                                pw.println("    public List " + methodName + "(final String " + formBean.getHdncmbViewOnchange()[i] + ") throws ClassNotFoundException, SQLException");
                                pw.println("    {");
                                pw.println("        return manager." + methodName + "(" + formBean.getHdncmbViewOnchange()[i] + ");");
                                pw.println("    }");
                            }
                            pw.println();
                        }
                        else if (viewRbtnQuery[i].equals(WEBSERVICE))
                        {
                            String consumer;
                            consumer = formBean.getHdntxtViewWsIntrface()[i].replace("Service", "Consumer");
                            String wsMethod, wsParams, wsException;
                            wsMethod = formBean.getHdncmbViewWsMethod()[i];
                            wsParams = formBean.getHdntxtViewWsParams()[i];
                            wsException = formBean.getHdntxtViewWsExps()[i];
                            makeWsMethodCode(pw, methodName, consumer, wsMethod, wsParams, wsException);
                        }
                    }
                }
            }
        }

        if (formBean.isChkView() && (formBean.isChkPdf() || formBean.isChkXls()))
        {
            if (isParseEx)
            {
                pw.println("    public List getJasperData(" + moduleName + "FormBean formBean) throws ClassNotFoundException, ParseException, SQLException");
            }
            else
            {
                pw.println("    public List getJasperData(" + moduleName + "FormBean formBean) throws ClassNotFoundException, SQLException");
            }
            pw.println("    {");
            pw.println("        return manager.getJasperData(formBeanToEntityBean(formBean));");
            pw.println("    }");
            pw.println();
        }

        val = cmbVal.iterator();
        while (val.hasNext())
        {
            Object element = val.next();
            String str = element.toString().substring(0, 1).toUpperCase().concat(element.toString().substring(1));
            pw.println("    public List get" + str + "() throws SQLException,ClassNotFoundException");
            pw.println("    {");
            pw.println("        return " + element + "Service.get" + str + "();");
            pw.println("    }");
        }

        pw.println("}");
        pw.close();
    }

    private void makeWsMethodCode(PrintWriter pw, final String methodName, final String consumer, final String wsMethod, final String wsParams, final String wsException)
    {
        WsdlParser wsdlParser = new WsdlParser();
        Set exceptions = wsdlParser.getWsThrows(wsException);

        pw.println("    public List " + methodName + "()");
        pw.println("    {");
        if (exceptions.size() > 0)
        {
            pw.println("        try");
            pw.println("        {");
        }
        pw.println("            " + consumer + " consumer = new " + consumer + "();");

        StringBuilder paramList = new StringBuilder();
        if (wsParams != null)
        {
            String str = wsParams;
            str = str.replace("[", "");
            str = str.replace("]", "");
            if (!"".equals(str))
            {
                String[] params = str.split(",");
                for (int j = 0; j < params.length; j++)
                {
                    params[j] = params[j].trim();
                    if ("String".equals(params[j]))
                    {
                        paramList.append("\"\", ");
                    }
                    else if ("int".equals(params[j]) || "byte".equals(params[j]) || "short".equals(params[j]))
                    {
                        paramList.append("0, ");
                    }
                    else if ("long".equals(params[j]))
                    {
                        paramList.append("0L, ");
                    }
                    else if ("float".equals(params[j]))
                    {
                        paramList.append("0.0f, ");
                    }
                    else if ("double".equals(params[j]))
                    {
                        paramList.append("0.0, ");
                    }
                    else if ("boolean".equals(params[j]))
                    {
                        paramList.append("false, ");
                    }
                    else if ("char".equals(params[j]))
                    {
                        paramList.append("'', ");
                    }
                    else
                    {
                        paramList.append(params[j]).append(", ");
                    }
                }
                if (paramList.length() > 2)
                {
                    paramList.deleteCharAt(paramList.length() - 1);
                    paramList.deleteCharAt(paramList.length() - 1);
                }
            }
        }

        pw.println("            return consumer." + wsMethod + "(" + paramList + ");");
        if (exceptions.size() > 0)
        {
            pw.println("        }");
            Iterator expIter;
            expIter = exceptions.iterator();
            while (expIter.hasNext())
            {
                // Get element
                Object element;
                element = expIter.next();
                pw.println("        catch (" + element + " e)");
                pw.println("        {");
                pw.println("            throw new WebServiceException(e.getMessage(), e);");
                pw.println("        }");
            }
        }
        pw.println("    }");
    }
}
