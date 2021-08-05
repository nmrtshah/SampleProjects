/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.findhtmlreport;

import com.finlogic.business.finstudio.findhtmlreport.FinDhtmlReportDataManager;
import com.finlogic.business.finstudio.findhtmlreport.FinDhtmlReportDetailEntityBean;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;

/**
 *
 * @author njuser
 */
public class FinDhtmlReportAjaxGenerator
{

    public void generateAjaxJsp(final FinDhtmlReportDetailEntityBean entityBean) throws FileNotFoundException, UnsupportedEncodingException, ClassNotFoundException, SQLException
    {
        String projectName = entityBean.getProjectName();
        String tomcatPath = finpack.FinPack.getProperty("tomcat1_path");
        String moduleName = entityBean.getModuleName();
        String number = entityBean.getSRNo() + "RGV2";
        String filePath = tomcatPath + "/webapps/finstudio/generated/" + number + "/";
        String projectPath = filePath + projectName.toLowerCase(Locale.getDefault()) + "/WEB-INF/jsp/" + moduleName.toLowerCase(Locale.getDefault()) + "/";

        File file = new File(projectPath);
        file.mkdirs();
        PrintWriter pw = new PrintWriter(projectPath + "ajax.jsp");

        pw.println("<%-- TCIGBF --%>");
        pw.println();
        pw.println("<%@taglib uri=\"http://java.sun.com/jsp/jstl/core\" prefix=\"c\" %>");
        pw.println();
        pw.println("<c:choose>");

        String depComboArry[] = null;
        String[] label = entityBean.getFltrLabel();
        if (entityBean.getFltrCmbSource() != null)
        {
            String control[] = entityBean.getFltrControl();
            int controlLen = control.length;
            for (int i = 0; i < controlLen; i++)
            {
                if (("ComboBox".equals(control[i]) || "TextLikeCombo".equals(control[i])))
                {
                    if (entityBean.getFltrchkForDependentCombo() != null && !entityBean.getFltrchkForDependentCombo()[i].equalsIgnoreCase("false") && entityBean.getFltrchkForDependentCombo().length > 0)
                    {
                        if (entityBean.getFltrCmbDependent() != null)
                        {
                            depComboArry = entityBean.getFltrCmbDependent().split(",");
                            if (!depComboArry[i].equalsIgnoreCase(""))
                            {
                                if ("fltrCmbSrcQuery".equals(entityBean.getFltrCmbSource()[i]) && !"".equals(entityBean.getFltrTxtSrcQuery()[i]))
                                {
                                    FinDhtmlReportDataManager frs = new FinDhtmlReportDataManager();
                                    FinDhtmlReportDirectConnection conn = new FinDhtmlReportDirectConnection();
                                    Connection con = null;
                                    SqlRowSet column;
                                    SqlRowSetMetaData srsmd;
                                    String columns[];
                                    String decodeQuery;
                                    if ("usingAlias".equals(entityBean.getConType()))
                                    {
                                        decodeQuery = URLDecoder.decode(entityBean.getFltrTxtSrcQuery()[i], "UTF-8").trim();
                                        if (decodeQuery.endsWith(";"))
                                        {
                                            decodeQuery = decodeQuery.substring(0, decodeQuery.length() - 1).trim();
                                        }
                                        decodeQuery = "SELECT * FROM (" + decodeQuery + ") X WHERE 1 = 2";
                                        column = frs.getReportData(entityBean.getAlias()[0], decodeQuery, entityBean.getConType(), null);
                                        srsmd = column.getMetaData();
                                        columns = srsmd.getColumnNames();
                                    }
                                    else
                                    {
                                        //direct connection                
                                        decodeQuery = URLDecoder.decode(entityBean.getFltrTxtSrcQuery()[i], "UTF-8").trim();
                                        if (decodeQuery.endsWith(";"))
                                        {
                                            decodeQuery = decodeQuery.substring(0, decodeQuery.length() - 1).trim();
                                        }
                                        decodeQuery = "SELECT * FROM (" + decodeQuery + ") X WHERE 1 = 2";
                                        con = conn.getConnection(entityBean.getDevServer());
                                        column = frs.getReportData(entityBean.getAlias()[0], decodeQuery, entityBean.getConType(), con);
                                        srsmd = column.getMetaData();
                                        columns = srsmd.getColumnNames();
                                    }
                                    if (con != null)
                                    {
                                        con.close();
                                    }
                                    if (!depComboArry[i].equalsIgnoreCase(entityBean.getFltrTxtName()[i]))
                                    {
                                        pw.println("    <c:when test=\"${process eq '" + entityBean.getFltrTxtName()[i] + "'}\">");

                                        pw.println("      <option value=\"\">--Select " + label[i] + "--</option>");
                                        pw.println("      <c:forEach items=\"${" + entityBean.getFltrTxtName()[i] + "List}\" var=\"list\">");
                                        pw.println("      <option value=\"${list." + columns[0].trim() + "}\">${list." + columns[1].trim() + "}</option>");
                                        pw.println("    </c:forEach>");
                                        pw.println("    </c:when>");
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        pw.println("</c:choose>");

        pw.close();
    }
}
