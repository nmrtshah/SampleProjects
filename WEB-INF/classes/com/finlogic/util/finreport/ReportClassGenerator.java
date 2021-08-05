/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.finreport;

import com.finlogic.apps.finstudio.finreport.FinReportService;
import com.finlogic.business.finstudio.finreport.FinReportDetailEntityBean;
import com.finlogic.business.finstudio.finreport.FinReportSummaryEntityBean;
import com.finlogic.util.Logger;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Map;

/**
 *
 * @author njuser
 */
public class ReportClassGenerator
{

    public void generateReportClass(final FinReportDetailEntityBean dEntityBean, final FinReportSummaryEntityBean sEntityBean) throws FileNotFoundException, ClassNotFoundException, SQLException, UnsupportedEncodingException
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
        PrintWriter pw = new PrintWriter(projectPath + moduleName + "Report.java");
        try
        {
            pw.println("package com.finlogic.apps." + projectName.toLowerCase(Locale.getDefault()) + "." + moduleName.toLowerCase(Locale.getDefault()) + ";");
            pw.println();
            pw.println("import com.finlogic.util.persistence.SQLUtility;");
            pw.println("import java.util.List;");
            pw.println("import java.util.Map;");
            pw.println("import java.sql.SQLException;");
            pw.println("public class " + moduleName + "Report");
            pw.println("{");
            pw.println("    public static final String ALIAS = \"" + dEntityBean.getAlias()[0] + "\"; ");
            pw.println("    private final SQLUtility sqlService = new SQLUtility();");
            if (sEntityBean != null && !sEntityBean.getDetailRefNo().equals(""))
            {
                pw.println("    public static final String SUMMARYALIAS = \"" + sEntityBean.getAliasName()[0] + "\"; ");
            }
            pw.println();

            //generate DetailReport Data function
            commonReportData(pw, moduleName, dEntityBean.getAlias(), dEntityBean.getQuery(), dEntityBean.getMainQueryColumns(), dEntityBean.getChildQueryColumns(), dEntityBean.getConType(), dEntityBean.getDevServer(), dEntityBean.isGrouping(), dEntityBean.getGroupField(), dEntityBean.getPrimaryKey(), "getDetailReportData");
            if (sEntityBean != null && !sEntityBean.getDetailRefNo().equals(""))
            {
                commonReportData(pw, moduleName, sEntityBean.getAliasName(), sEntityBean.getAllQuery(), sEntityBean.getMainQueryColumns(), sEntityBean.getChildQueryColumns(), sEntityBean.getConType(), sEntityBean.getDevServer(), sEntityBean.isGrouping(), sEntityBean.getGroupField(), sEntityBean.getPrimaryKey(), "getSummaryReportData");
            }
            pw.println();
            //for combo filling by query
            if (dEntityBean.getFltrCmbSource() != null && dEntityBean.getFltrTxtSrcQuery().length > 0)
            {
                String decodeQuery;
                String tmpQuery[] = new String[dEntityBean.getFltrTxtSrcQuery().length];
                for (int i = 0; i < dEntityBean.getFltrTxtSrcQuery().length; i++)
                {
                    decodeQuery = URLDecoder.decode(dEntityBean.getFltrTxtSrcQuery()[i], "UTF-8");
                    tmpQuery[i] = decodeQuery;
                    tmpQuery[i] = tmpQuery[i].replace("\"", "\\\"");
                    while (tmpQuery[i].contains("\r") || tmpQuery[i].contains("\n"))
                    {
                        tmpQuery[i] = tmpQuery[i].replaceAll("\n", "@@@");
                        tmpQuery[i] = tmpQuery[i].replaceAll("\r", "@@@");
                    }
                    while (tmpQuery[i].contains("@@@@@@"))
                    {
                        tmpQuery[i] = tmpQuery[i].replaceAll("@@@@@@", "@@@");
                    }

                    tmpQuery[i] = tmpQuery[i].replaceAll("@@@", " \");\n        queryMast.append(\"");
                }

                String control[] = dEntityBean.getFltrControl();
                int controlLen = control.length;
                for (int i = 0; i < controlLen; i++)
                {
                    if (("ComboBox".equals(control[i]) || "TextLikeCombo".equals(control[i])) && dEntityBean.getFltrCmbSource() != null && "fltrCmbSrcQuery".equals(dEntityBean.getFltrCmbSource()[i]))
                    {
                        pw.println("    public List get" + dEntityBean.getFltrTxtName()[i] + "List() throws SQLException,ClassNotFoundException");
                        pw.println("    {");
                        pw.println("        StringBuilder queryMast = new StringBuilder();");
                        pw.println("        queryMast.append(\"" + tmpQuery[i] + "\");");
                        pw.println("        return sqlService.getList(ALIAS, queryMast.toString());");
                        pw.println("    }");
                    }
                }
            }
            pw.println("}");
        }
        finally
        {
            try
            {
                pw.close();
            }
            catch (Exception e)
            {
                Logger.ErrorLogger(e);
            }
        }
    }

    public void commonReportData(PrintWriter pw, String moduleName, String[] alias, String[] query, String[] mainQueryColumns, String[] childQueryColumns, String conType, String serverName, boolean grouping, String groupField, String primaryKey, String methodName) throws ClassNotFoundException, SQLException
    {
        FinReportService frs = new FinReportService();
        DirectConnection conn = new DirectConnection();
        Connection con = null;

        try
        {
            int queryLen = query.length;
            String tempQuery[] = new String[queryLen];
            for (int i = 0; i < queryLen; i++)
            {
                tempQuery[i] = query[i];
                tempQuery[i] = tempQuery[i].replace("\"", "\\\"");
                while (tempQuery[i].contains("\r") || tempQuery[i].contains("\n"))
                {
                    tempQuery[i] = tempQuery[i].replaceAll("\n", "@@@");
                    tempQuery[i] = tempQuery[i].replaceAll("\r", "@@@");
                }
                while (tempQuery[i].contains("@@@@@@"))
                {
                    tempQuery[i] = tempQuery[i].replaceAll("@@@@@@", "@@@");
                }
                if (i > 0)
                {
                    tempQuery[i] = tempQuery[i].replaceAll("@@@", " \");\n              queryChild.append(\"");
                }
                else
                {
                    tempQuery[i] = tempQuery[i].replaceAll("@@@", " \");\n        queryMast.append(\"");
                }

            }

            pw.println("    public List<Map> " + methodName + "(" + moduleName + "FormBean formBean) throws SQLException, ClassNotFoundException");
            pw.println("    {");
            pw.println("        StringBuilder queryMast;");
            pw.println("        queryMast = new StringBuilder();");
            if (grouping)
            {
                pw.println("        queryMast.append(\"SELECT * FROM ( \");");
            }
            pw.println("        queryMast.append(\"" + tempQuery[0] + "\");");
            if (grouping)
            {
                pw.println("        queryMast.append(\") REPORT ORDER BY REPORT." + groupField + "," + primaryKey + "\");");
            }
            pw.println("        List<Map> lsMast;");
            if (methodName.equals("getSummaryReportData"))
            {
                pw.println("        lsMast = sqlService.getList(SUMMARYALIAS, queryMast.toString());");
            }
            else
            {
                pw.println("        lsMast = sqlService.getList(ALIAS, queryMast.toString());");
            }

            if (queryLen > 1)
            {
                pw.println("        StringBuilder queryChild;");
                pw.println("        int lsMastLen = lsMast.size();");
                pw.println("        for (int i = 0; i < lsMastLen; i++)");
                pw.println("        {");
                pw.println("            Map mMast = lsMast.get(i);");
                for (int i = 1; i < queryLen; i++)
                {
                    pw.println("            {");
                    pw.println("                queryChild = new StringBuilder();");
                    //before condition was:::formBean.getCmbMainQueryColumn() == null && formBean.getCmbChildQueryColumns() == null
                    if (mainQueryColumns == null)
                    {
                        pw.println("                queryChild.append(\"" + tempQuery[i] + "\");");
                    }
                    else
                    {
                        if ("-1".equals(mainQueryColumns[i - 1]) && "-1".equals(childQueryColumns[i - 1]))
                        {
                            pw.println("                queryChild.append(\"" + tempQuery[i] + "\");");
                        }
                        else
                        {
                            String Query = tempQuery[i].concat(" q1 INNER JOIN(" + tempQuery[0] + ")q2 ON q1." + childQueryColumns[i - 1] + "=q2." + mainQueryColumns[i - 1]);
                            String queryOutPut;
                            if ("usingAlias".equals(conType))
                            {
                                queryOutPut = frs.validateQuery(alias[i], Query, conType, null);
                            }
                            else
                            {
                                con = conn.getConnection(serverName);
                                queryOutPut = frs.validateQuery(alias[i], Query, conType, con);
                            }

                            if ("Valid".equals(queryOutPut))
                            {
                                pw.println("                queryChild.append(\"" + tempQuery[i] + "\");");
                                pw.println("                queryChild.append(\" WHERE " + childQueryColumns[i - 1] + " = '\");");
                                pw.println("                queryChild.append(mMast.get(\"" + mainQueryColumns[i - 1] + "\"));");
                                pw.println("                queryChild.append(\"'\");");
                            }
                            else
                            {
                                pw.println("                queryChild.append(\"" + tempQuery[i] + "\"); //WHERE " + childQueryColumns[i - 1] + " = '\"+ mMast.get(\"" + mainQueryColumns[i - 1] + "\") +\"'\" ");
                            }
                        }
                    }

                    pw.println("                List<Map> lsChild;");
                    pw.println("                lsChild = sqlService.getList(\"" + alias[i] + "\", queryChild.toString());");
                    pw.println("                if (lsChild.isEmpty())");
                    pw.println("                {");
                    Map colNames;
                    if ("usingAlias".equals(conType))
                    {
                        colNames = frs.getColumnDetail(alias[i], query[i], conType, null);
                    }
                    else
                    {
                        colNames = frs.getColumnDetail(alias[i], query[i], conType, con);
                    }

                    String colnmResult = colNames.get("colNames").toString();
                    String colnmList[] = colnmResult.split(",");
                    int lsLen = colnmList.length;
                    for (int j = 0; j < lsLen; j++)
                    {
                        pw.println("                    mMast.put(" + colnmList[j] + ",null);");
                    }
                    pw.println("                }");
                    pw.println("                else");
                    pw.println("                {");
                    pw.println("                    Map mChild;");
                    pw.println("                    mChild = lsChild.get(0);");
                    pw.println("                    mMast.putAll(mChild);");
                    pw.println("                }");
                    pw.println("            }");
                }
                pw.println("        }");
            }
        }
        finally
        {
            try
            {
                if (con != null)
                {
                    con.close();
                }
            }
            catch (Exception e)
            {
                Logger.ErrorLogger(e);
            }
        }
        pw.println("        return lsMast;");
        pw.println("    }");
    }
}
