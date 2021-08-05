/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.apps.finstudio.controller;

import com.finlogic.util.finreport.DirectConnection;
import com.finlogic.util.persistence.SQLConnService;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author njuser
 */
public class ConfigAuditServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print("<!DOCTYPE html>");
        out.print("<html>");
        out.print("<head>");
        out.print("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'/>");
        out.print("<link href='css/main.css' rel='stylesheet' type='text/css'/>");
        out.print("<title>Config Audit Report</title>");
        out.println("</head>");
        out.print("<body>");
        try {

            if (request.getParameter("cmdAction") == null) {
                displayMenu(out);
            } else {
                String[] serverNames = request.getParameterValues("serverNames");
                if (serverNames == null || serverNames.length == 0) {
                    out.print("<br><center>Please select atleast one server.</center>");
                } else {
                    Map params = new HashMap();
                    params.put("serverNames", serverNames);
                    params.put("reportType", request.getParameter("reportType"));
                    params.put("keyLike", request.getParameter("keyLike"));
                    Map m = getReport(params);
                    displayReport(m, out);
                }

            }

        } catch (Exception e) {
            out.print("<!--");
            out.print(e.getMessage());
            e.printStackTrace(out);
            out.print("-->");
        } finally {
            out.print("</body>");
            out.print("</html>");
            out.close();
        }
    }

    public static void displayMenu(PrintWriter out) {
        out.print("<br>");
        out.print("<form id='configaudit' name='configaudit' method='POST' action='configaudit.do?cmdAction=report'>");
        out.print("<div id='selReport'>");
        out.print("<table class='tbl_border1' id='selReportType' align='center'>");
        out.print("<tbody><tr class='tbl_h1_bg'>");
        out.print("<th colspan='100%'>Config Report</th>");
        out.print("</tr>");
        out.print("<tr>");
        out.print("<th colspan='100%'>&nbsp;</th>");
        out.print("</tr>");
        out.print("<tr>");
        out.print("<td align='right'>");
        out.print("Select Report Type :");
        out.print("</td>");
        out.print("<td align='left'>");
        out.print("<select name='reportType' id='reportType'>");
        out.print("<option value='connection_alias'>Connection Alias</option>");
        out.print("<option value='mail_mailalias'>Mail Alias</option>");
        out.print("<option value='ws_serviceId'>Web Service</option>");
        out.print("<option value='consumer_ServiceName'>Web Service Consumer</option>");
        out.print("<option value='property_propertyname'>Property</option>");
        out.print("<option value='jar_jarPath'>Jar File</option>");
        out.print("<option value='app_appName'>Project by Name</option>");
        out.print("<option value='app_appPath'>Project by Path</option>");
        out.print("<option value='svn_svnrepoPath'>SVN Repo</option>");
        out.print("<option value='smb_path'>SMB By Path</option>");
        out.print("<option value='smb_users'>SMB By User</option>");
        out.print("</select>");
        out.print("</td>");
        out.print("</tr>");
        out.print("<tr>");
        out.print("<td align='right'>");
        out.print("Select Server :");
        out.print("</td>");
        out.print("<td align='left'>");
        out.print("<select name='serverNames' id='serverNames' size='10' multiple>");
        out.print("<option value='devhoweb1.nj'>devhoweb1.nj</option>");
        out.print("<option value='devhoweb2.nj'>devhoweb2.nj</option>");
        out.print("<option value='devhoweb3.nj'>devhoweb3.nj</option>");
        out.print("<option value='devhoweb4.nj'>devhoweb4.nj</option>");
        out.print("<option value='testhoweb1.nj'>testhoweb1.nj</option>");
        out.print("<option value='testhoweb2.nj'>testhoweb2.nj</option>");
        out.print("<option value='testhoweb3.nj'>testhoweb3.nj</option>");
        out.print("<option value='testhoweb4.nj'>testhoweb4.nj</option>");
        out.print("<option value='devgroupemail.nj'>devgroupemail.nj</option>");
        out.print("<option value='prodhoweb1.nj'>prodhoweb1.nj</option>");
        out.print("<option value='prodhoweb2.nj'>prodhoweb2.nj</option>");
        out.print("<option value='prodhoweb3.nj'>prodhoweb3.nj</option>");
        out.print("<option value='prodhoweb4.nj'>prodhoweb4.nj</option>");
        out.print("<option value='prodgroupemail.nj'>prodgroupemail.nj</option>");
        out.print("</select>");
        out.print("</td>");
        out.print("</tr>");

        out.print("<td align='right'>");
        out.print("Key Like :");
        out.print("</td>");
        out.print("<td align='left'>");
        out.print("<input type='textbox' name='keyLike'>");
        out.print("</td>");
        out.print("</tr>");

        out.print("<tr>");
        out.print("<td colspan='100%' align='center'>");
        out.print("<input name='btnSubmit' value='Submit' onclick='javascript: showReport();' type='submit'>");
        out.print("&nbsp;");
        out.print("<input name='btnReset' value='Reset' type='reset'>");
        out.print("</td>");
        out.print("</tr>");

        out.print("</tbody></table>");
        out.print("</div>");
        out.print("</form>");
    }

    public static void displayReport(Map pMap, PrintWriter out) {
        List result = (List) pMap.get("resultList");
        Set serverSet = (Set) pMap.get("serverSet");
        Set fieldSet = (Set) pMap.get("fieldSet");
        String commonField = (String) pMap.get("commonField");
        String lastUpdate = (String) pMap.get("lastUpdate");

        if (fieldSet.isEmpty()) {
            out.print("<br><center>No record found.</center>");
            return;
        }
        int records = result.size();

        out.print("<br><center><b>Last Updated on :</b> " + lastUpdate + "</center>.");
        out.print("<br><center><b>Note :</b> Bold value denotes difference in case of multiple servers selected.</center>");
        out.print("<br><center><b>Total records :</b> " + records + ".</center><br>");
        out.print("<table align=\"center\" border=\"1\" >");
        out.print("<thead>");
        out.print("<tr class=\"tbl_h1_bg\">");
        out.print("<td>" + commonField.toUpperCase() + "</td>");
        for (Iterator it = fieldSet.iterator(); it.hasNext();) {
            String fieldName = (String) it.next();
            for (Iterator it1 = serverSet.iterator(); it1.hasNext();) {
                String serverName = (String) it1.next();
                out.print("<td>" + serverName.toUpperCase() + "_" + fieldName.toUpperCase() + "</td>");
            }
        }
        out.print("</tr>");
        out.print("</thead>");
        out.print("<tbody align=\"left\">");

        for (int i = 0; i < records; i++) {
            Map m = (Map) result.get(i);
            String commonFieldValue = (String) m.get("commonField");
            if (commonFieldValue.contains("")) {
                out.print("<tr>");
                out.print("<td>" + commonFieldValue + "</td>");
                int j = 1;
                for (Iterator it = fieldSet.iterator(); it.hasNext(); j++) {
                    String fieldName = (String) it.next();
                    Set compare = new HashSet();
                    for (Iterator it1 = serverSet.iterator(); it1.hasNext();) {
                        String serverName = (String) it1.next();
                        compare.add(m.get(serverName + "_" + fieldName));
                    }
                    int compareSize = compare.size();
                    for (Iterator it1 = serverSet.iterator(); it1.hasNext();) {
                        String serverName = (String) it1.next();
                        String colour = "";
                        String value = (String) m.get(serverName + "_" + fieldName);
//                        String title = "";
//                        if (value != null && value.length() > 80)
//                        {
//                            title = " title='" + value + "' ";
//                            value = "..." + value.substring(value.length() - 70, value.length());
//                        }
//
//                        if ((j % 2) == 1)
//                        {
//                            colour = " bgcolor='#F2F2F2' ";
//                        }
//                        if (compareSize == 1)
//                        {
//                            out.print("<td style='width:70px;word-wrap:break-word;'" + colour + title + ">" + value + "</td>");
//                        }
//                        else
//                        {
//                            out.print("<td style='width:70px;word-wrap:break-word;'" + colour + title + "><b>" + value + "</b></td>");
//                        }

                        if ((j % 2) == 1) {
                            colour = " bgcolor='#F2F2F2' ";
                        }
                        if (compareSize == 1) {
                            out.print("<td style='width:70px;word-wrap:break-word;'" + colour + ">" + value + "</td>");
                        } else {
                            out.print("<td style='width:70px;word-wrap:break-word;'" + colour + "><b>" + value + "</b></td>");
                        }

                    }
                }
                out.print("</tr>");
            }
        }
        out.print("</tbody>");
        out.print("</table>");
        out.print("<br><br>");
    }

    public static void main(String[] args) {
        try {
            DirectConnection dc = new DirectConnection();
            String[] s
                    = {
                        "devhoweb1.nj", "devhoweb2.nj", "devhoweb3.nj"
                    };
        } catch (Exception e) {
        }
    }

    public static Map getReport(Map param) throws ClassNotFoundException, SQLException {

        String[] servers = (String[]) param.get("serverNames");
        String reportType = ((String) param.get("reportType")).replace("'", "");
        String keyLike = ((String) param.get("keyLike")).replace("'", "");

        Map mOut = new HashMap();
        String commonField = "NA";
        SQLConnService sqlcs = new SQLConnService();
        Connection conn = null;
        DirectConnection dc = new DirectConnection();

        try {
            conn = dc.getConnection("dev_db2_mysql");

            commonField = reportType.substring(reportType.indexOf("_") + 1);
            reportType = reportType.substring(0, reportType.indexOf("_"));
//
//            if (reportType.equals("ws"))
//            {
//                commonField = "serviceId";
//            }
//            else if (reportType.equals("connection"))
//            {
//                commonField = "alias";
//            }
//            else if (reportType.equals("mail"))
//            {
//                commonField = "mailalias";
//            }
//            else if (reportType.equals("property"))
//            {
//                commonField = "propertyname";
//            }
//            else if (reportType.equals("jar"))
//            {
//                commonField = "jarPath";
//            }
//            else if (reportType.equals("app"))
//            {
//                commonField = "appName ";
//            }
//            else if (reportType.equals("tomcat"))
//            {
//                commonField = "appPath";
//            }
//            else if (reportType.equals("svn"))
//            {
//                commonField = "svnrepoPath";
//            }
//            else if (reportType.equals("consumer"))
//            {
//                commonField = "ServiceName";
//            }

            Set serverSet = new TreeSet();
            String strReportType = "";

            for (int i = 0; i < servers.length; i++) {
                servers[i] = servers[i].replace("'", "");
                serverSet.add(servers[i]);
                strReportType += "'" + reportType + "_" + servers[i] + "',";

                if (!servers[i].contains("groupemail.nj")) {
                    //if (servers[i].startsWith("howeb")) {
                    if (servers[i].startsWith("prod")) {
                        strReportType += "'" + reportType + "_" + "p" + servers[i] + "',";
                    } else {
                        strReportType += "'" + reportType + "_" + servers[i].charAt(0) + "howeb" + servers[i].substring(servers[i].indexOf(".") - 1) + "',";
                    }
                }
            }
            strReportType = strReportType.substring(0, strReportType.length() - 1);

            String keyLikeCondition = "";
            if (keyLike != null && keyLike.trim().length() > 0) {
                keyLikeCondition = "AND ENTRYVALUE LIKE '%" + keyLike + "%' ";
            }

            String sqlCommonField = "SELECT DISTINCT ENTRYVALUE "
                    + "FROM finstudio.CONFIG_AUDIT "
                    + "WHERE ENTRYFIELD='" + commonField + "' "
                    + keyLikeCondition
                    + "AND ENTRYTYPE IN (" + strReportType + ") ";
            //+ "AND ENTRYTYPE LIKE '" + reportType + "_%' ";

            List commonFieldValues = sqlcs.getList(conn, sqlCommonField);

            String sqlLastUpdate = "SELECT date_format(MIN(CURR_TIMESTAMP),'%d/%m/%Y %H:%i') LAST_UPDATE "
                    + "FROM finstudio.CONFIG_AUDIT "
                    + "WHERE ENTRYTYPE IN (" + strReportType + ") ";

            String lastUpdate = sqlcs.getString(conn, sqlLastUpdate);

            List result = new ArrayList();
            Set fieldSet = new HashSet();

            int records = commonFieldValues.size();
            Set<String> commonFieldSet = new TreeSet<String>();
            for (int i = 0; i < records; i++) {
                Map m = (Map) commonFieldValues.get(i);
                commonFieldSet.add((String) m.get("ENTRYVALUE"));
            }
            String newHostserver = "";
            for (Iterator<String> it = commonFieldSet.iterator(); it.hasNext();) {
                String commonFieldValue = it.next();

                Map mRow = new HashMap();
                mRow.put("commonField", commonFieldValue);

                for (int j = 0; j < servers.length; j++) {
                    // this query throws error while having duplicate records

//                    String sqlRow = "SELECT ENTRYFIELD,ENTRYVALUE FROM finstudio.CONFIG_AUDIT "
//                            + "WHERE ENTRYTYPE='" + reportType + "_" + servers[j] + "' "
//                            + "AND ENTRYID = "
//                            + "("
//                            + "SELECT ENTRYID FROM finstudio.CONFIG_AUDIT "
//                            + "WHERE ENTRYTYPE='" + reportType + "_" + servers[j] + "' "
//                            + "AND ENTRYFIELD='" + commonField + "' "
//                            + "AND BINARY ENTRYVALUE='" + commonFieldValue + "' "
//                            + ") ";
                    String sqlRow = "SELECT ENTRYFIELD,ENTRYVALUE FROM finstudio.CONFIG_AUDIT "
                            + "WHERE ENTRYTYPE='" + reportType + "_" + servers[j] + "' "
                            + "AND ENTRYID IN "
                            + "("
                            + "SELECT ENTRYID FROM finstudio.CONFIG_AUDIT "
                            + "WHERE ENTRYTYPE='" + reportType + "_" + servers[j] + "' "
                            + "AND ENTRYFIELD='" + commonField + "' "
                            + "AND BINARY ENTRYVALUE='" + commonFieldValue + "' "
                            + ") ";

                    List lRow = sqlcs.getList(conn, sqlRow);
                    if (lRow.isEmpty()) {
                        if (!servers[j].contains("groupemail.nj")) {
                            //if (servers[j].startsWith("howeb")) {
                            if (servers[j].startsWith("prod")) {
                                newHostserver = "p" + servers[j];
                            } else {
                                newHostserver = servers[j].charAt(0) + "howeb" + servers[j].substring(servers[j].indexOf(".") - 1);
                            }
                            sqlRow = "SELECT ENTRYFIELD,ENTRYVALUE FROM finstudio.CONFIG_AUDIT "
                                    + "WHERE ENTRYTYPE='" + reportType + "_" + newHostserver + "' "
                                    + "AND ENTRYID IN "
                                    + "("
                                    + "SELECT ENTRYID FROM finstudio.CONFIG_AUDIT "
                                    + "WHERE ENTRYTYPE='" + reportType + "_" + newHostserver + "' "
                                    + "AND ENTRYFIELD='" + commonField + "' "
                                    + "AND BINARY ENTRYVALUE='" + commonFieldValue + "' "
                                    + ") ";
                            lRow = sqlcs.getList(conn, sqlRow);
                        }
                    }
                    for (int k = 0; k < lRow.size(); k++) {
                        Map mTempMap = (Map) lRow.get(k);
                        if (!(mTempMap.get("ENTRYFIELD").equals(commonField))) {
                            mRow.put(servers[j] + "_" + mTempMap.get("ENTRYFIELD"), mTempMap.get("ENTRYVALUE"));
                            fieldSet.add(mTempMap.get("ENTRYFIELD"));
                        }
                    }
                }
                result.add(mRow);
            }
            mOut.put("resultList", result);
            mOut.put("serverSet", serverSet);
            mOut.put("fieldSet", fieldSet);
            mOut.put("commonField", commonField);
            mOut.put("lastUpdate", lastUpdate);
        } finally {
            try {
                conn.close();
            } catch (Exception e) {
            }
        }
        return mOut;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
