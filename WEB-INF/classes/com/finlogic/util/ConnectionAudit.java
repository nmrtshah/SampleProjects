/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util;

import com.finlogic.util.properties.HardCodeProperty;
import finpack.FinPack;
import finutils.directconn.DBConnManager;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author njuser
 */
public final class ConnectionAudit
{

    private static List<String> fileArrayT1 = new ArrayList<String>();
    private static List<String> fileArrayT2 = new ArrayList<String>();
    private static int tomcatNumber = 0;
    private static int currD = 0;
    private static int currM = 0;
    private static int currY = 0;
    private static DBConnManager dbConnManager = new DBConnManager();
    private static Connection mysqlConn = null;
    private static HardCodeProperty hcp = new HardCodeProperty();

    private ConnectionAudit()
    {
    }

    static
    {
        Calendar cal;
        cal = Calendar.getInstance();

        currD = cal.get(Calendar.DAY_OF_MONTH);
        currM = (cal.get(Calendar.MONTH) + 1);
        currY = cal.get(Calendar.YEAR);
    }

    public static void main(String[] args)
    {
        try
        {
            if (args[0].equals("Insert"))
            {
                Logger.DataLogger("Start Process insertIntoConnAudit : " + (new java.util.Date()));
                insertIntoConnAudit();
                Logger.DataLogger("End Process insertIntoConnAudit : " + (new java.util.Date()));
            }
            else if (args[0].equals("Mail"))
            {
                Logger.DataLogger("Start Process insertIntoConnAuditSummary,sendMail :" + (new java.util.Date()));
                insertIntoConnAuditSummary();
                sendMail();
                Logger.DataLogger("End Process insertIntoConnAuditSummary,sendMail :" + (new java.util.Date()));
            }
            else
            {
                Logger.DataLogger("Please Enter Proper Arguments.." + (new java.util.Date()));
            }
        }
        catch (Exception ex)
        {
            Logger.DataLogger("Error " + Logger.ErrorLogger(ex));
        }
        finally
        {
            try
            {
                mysqlConn.close();
            }
            catch (Exception e)
            {
                Logger.DataLogger("Error " + Logger.ErrorLogger(e));
            }
        }
    }

    private static void insertIntoConnAudit() throws ClassNotFoundException, IOException, SQLException
    {

        PreparedStatement psInsert = null, psUpdate = null;

        FileInputStream fileInputStream = null;
        DataInputStream dataInputStream = null;
        BufferedReader bufferedReader = null;

        try
        {

            String[] extensions =
            {
                "java"
            };

            fileArrayT1 = FinPack.searchFileRecursive(FinPack.getProperty("tomcat1_path"), extensions);

            for (int i = 0; i < fileArrayT1.size(); i++)
            {
                fileArrayT1.set(i, fileArrayT1.get(i).replaceAll(FinPack.getProperty("tomcat1_path") + "/webapps/", ""));
                fileArrayT1.set(i, fileArrayT1.get(i).replaceAll(FinPack.getProperty("tomcat1_path") + "/work/Catalina/localhost/", ""));
            }

            fileArrayT2 = finpack.FinPack.searchFileRecursive(FinPack.getProperty("tomcat2_path"), extensions);

            for (int i = 0; i < fileArrayT2.size(); i++)
            {
                fileArrayT2.set(i, fileArrayT2.get(i).replaceAll(FinPack.getProperty("tomcat2_path") + "/webapps/", ""));
                fileArrayT2.set(i, fileArrayT2.get(i).replaceAll(FinPack.getProperty("tomcat2_path") + "/work/Catalina/localhost/", ""));
            }

            mysqlConn = dbConnManager.getFinConn("finstudio_mysql");

            psInsert = mysqlConn.prepareStatement("insert into CONN_AUDIT"
                    + "(CONN_NUMBER,STACK_TRACE,OPEN_TIME,PROJECT_NAME,SERVER_NAME,"
                    + "TOMCAT_PATH,DB_SERVER_TYPE,CONN_MECHANISM,ENTDATE) "
                    + "values(?,?,?,?,?,?,?,?,SYSDATE())");

            psUpdate = mysqlConn.prepareStatement("update CONN_AUDIT set CLOSE_TIME=? "
                    + "where CONN_NUMBER=?");

            String strConAuditFolder;
            strConAuditFolder = finpack.FinPack.getProperty("tomcat1_path") + "/webapps/log/log";

            String[] fileList = new String[2];

            String fileName;
            {
                fileName = "/opt/apache-tomcat1/webapps/log/log/jar-conn-open-" + getCurrentDate() + ".txt";
                if (new File(fileName).exists())
                {
                    fileList[0] = "jar-conn-open-" + getCurrentDate() + ".txt";
                }
                fileName = "/opt/apache-tomcat1/webapps/log/log/jar-conn-open-" + getCurrentDate() + ".txt.1";
                if (new File(fileName).exists())
                {
                    fileList[0] = "jar-conn-open-" + getCurrentDate() + ".txt.1";
                }
            }
            {
                fileName = "/opt/apache-tomcat1/webapps/log/log/jar-conn-close-" + getCurrentDate() + ".txt";
                if (new File(fileName).exists())
                {
                    fileList[1] = "jar-conn-close-" + getCurrentDate() + ".txt";
                }
                fileName = "/opt/apache-tomcat1/webapps/log/log/jar-conn-close-" + getCurrentDate() + ".txt.1";
                if (new File(fileName).exists())
                {
                    fileList[1] = "jar-conn-close-" + getCurrentDate() + ".txt.1";
                }
            }

            for (int x = 0; x < fileList.length; x++)
            {

                Logger.DataLogger("Reading File=" + fileList[x]);

                try
                {

                    fileInputStream = new FileInputStream(strConAuditFolder + "/" + fileList[x]);
                    dataInputStream = new DataInputStream(fileInputStream);
                    bufferedReader = new BufferedReader(new InputStreamReader(dataInputStream));

                    String str, servertype = null, conn_mechanism = null;

                    while ((str = bufferedReader.readLine()) != null)
                    {

                        String[] field, ar1;
                        field = str.split(",");

                        if (field.length > 2)
                        {

                            if (field[4].contains("mysql"))
                            {
                                servertype = "MYSQL";
                            }
                            else if (field[4].contains("oracle"))
                            {
                                servertype = "ORACLE";
                            }

                            if (field[0].startsWith("10"))
                            {
                                conn_mechanism = "DIRECT";
                            }
                            else if (field[0].startsWith("20"))
                            {
                                conn_mechanism = "MANUAL POOL";
                            }
                            else if (field[0].startsWith("30"))
                            {
                                conn_mechanism = "C3P0 POOL";
                            }

                            if (field[3].contains("tomcat1"))
                            {
                                tomcatNumber = 1;
                            }
                            else
                            {
                                tomcatNumber = 0;
                            }
                            ar1 = field[5].split("Class Name :");

                            String[] ar2;
                            ar2 = new String[ar1.length];

                            for (int i = 1; i < ar1.length && i < 6; i++)
                            {
                                ar2[i - 1] = getProjectName("Class Name :" + ar1[i]);
                            }

                            String prj = getDistictProjects(ar2);

                            psInsert.clearParameters();
                            psInsert.setString(1, field[0]);
                            psInsert.setString(2, field[5]);
                            psInsert.setString(3, field[1]);
                            psInsert.setString(4, prj);
                            psInsert.setString(5, field[2]);
                            psInsert.setString(6, field[3]);
                            psInsert.setString(7, servertype);
                            psInsert.setString(8, conn_mechanism);

                            try
                            {
                                psInsert.executeUpdate();
                            }
                            catch (Exception ex)
                            {
                                Logger.DataLogger("Error " + Logger.ErrorLogger(ex));
                            }
                        }
                        else if (field.length == 2)
                        {
                            psUpdate.setString(1, field[1]);
                            psUpdate.setString(2, field[0]);
                            psUpdate.executeUpdate();
                        }
                    }

                }
                finally
                {
                    try
                    {
                        if (bufferedReader != null)
                        {
                            bufferedReader.close();
                        }
                    }
                    catch (Exception e)
                    {
                        Logger.DataLogger("Error " + Logger.ErrorLogger(e));
                    }
                    try
                    {
                        if (dataInputStream != null)
                        {
                            dataInputStream.close();
                        }
                    }
                    catch (Exception e)
                    {
                        Logger.DataLogger("Error " + Logger.ErrorLogger(e));
                    }
                    try
                    {
                        if (fileInputStream != null)
                        {
                            fileInputStream.close();
                        }
                    }
                    catch (Exception e)
                    {
                        Logger.DataLogger("Error " + Logger.ErrorLogger(e));
                    }
                }
            }
        }
        finally
        {
            try
            {
                if (psInsert != null)
                {
                    psInsert.close();
                }
            }
            catch (Exception e)
            {
                Logger.DataLogger("Error " + Logger.ErrorLogger(e));
            }
            try
            {
                if (psUpdate != null)
                {
                    psUpdate.close();
                }
            }
            catch (Exception e)
            {
                Logger.DataLogger("Error " + Logger.ErrorLogger(e));
            }
        }
    }

    private static void insertIntoConnAuditSummary()
    {
        try
        {
            mysqlConn = dbConnManager.getFinConn("finstudio_mysql");
            Statement st = null;
            String file1;
            file1 = finpack.FinPack.getProperty("tomcat1_path") + "/webapps/finstudio/log/Data_" + getCurrentStaticDate() + ".txt";
            File f1;
            f1 = new File(file1);

            if (f1.exists())
            {
                File f2;
                f2 = new File(file1 + System.currentTimeMillis());
                f1.renameTo(f2);
            }
            try
            {
                st = mysqlConn.createStatement();
                st.executeUpdate("INSERT INTO CONN_AUDIT_SUMMARY(ON_DATE, REPORT_TYPE, "
                        + "SERVER_NAME, TOMCAT_PATH, DB_SERVER_TYPE, PROJECT_NAME, "
                        + "STACK_TRACE, CONN_MECHANISM, NO_OF_LEAKS, TOTAL_CONNECTION, ENTDATE) "
                        + "select date_format(OPEN_TIME,'%Y/%m/%e') OPEN_TIME,'Page Date Wise',SERVER_NAME,TOMCAT_PATH,"
                        + "DB_SERVER_TYPE,PROJECT_NAME,STACK_TRACE,CONN_MECHANISM,COUNT(*)-COUNT(CLOSE_TIME) TOTAL_LEAKS,"
                        + "COUNT(*) TOT_CONN,SYSDATE() "
                        + "FROM CONN_AUDIT "
                        + "where date_format(OPEN_TIME,'%e-%c-%Y')= '" + getPreviousDate() + "' "
                        + "GROUP BY SERVER_NAME,TOMCAT_PATH,PROJECT_NAME,CONN_MECHANISM,DB_SERVER_TYPE,STACK_TRACE");
            }
            catch (SQLException ex)
            {
                Logger.DataLogger("Error " + Logger.ErrorLogger(ex));
            }
            finally
            {
                try
                {
                    if (st != null)
                    {
                        st.close();
                    }
                }
                catch (Exception e)
                {
                    Logger.DataLogger("Error " + Logger.ErrorLogger(e));
                }
            }
//
//            try
//            {
//                st = mysqlConn.createStatement();
//                st.executeUpdate("INSERT INTO CONN_AUDIT_SUMMARY(ON_DATE, REPORT_TYPE, "
//                        + "SERVER_NAME, TOMCAT_PATH, DB_SERVER_TYPE, PROJECT_NAME, "
//                        + "STACK_TRACE, CONN_MECHANISM, NO_OF_LEAKS, TOTAL_CONNECTION, ENTDATE) "
//                        + "select date_format(OPEN_TIME,'%Y-%m-%d %H:%i') HOUR_MIN,'Time Wise',SERVER_NAME,TOMCAT_PATH,"
//                        + "DB_SERVER_TYPE,PROJECT_NAME,STACK_TRACE,"
//                        + "CONN_MECHANISM,COUNT(*)-COUNT(CLOSE_TIME) TOTAL_LEAKS,COUNT(*) TOT_CONN,SYSDATE() "
//                        + "FROM CONN_AUDIT "
//                        + "where date_format(OPEN_TIME,'%e-%c-%Y')='" + getPreviousDate() + "'  "
//                        + "group by HOUR_MIN,SERVER_NAME,TOMCAT_PATH,DB_SERVER_TYPE,CONN_MECHANISM,"
//                        + "PROJECT_NAME,STACK_TRACE");
//            }
//            catch (SQLException ex)
//            {
//                Logger.DataLogger("Error " + Logger.ErrorLogger(ex));
//            }
//            finally
//            {
//                try
//                {
//                    if (st != null)
//                    {
//                        st.close();
//                    }
//                }
//                catch (Exception e)
//                {
//                    Logger.DataLogger("Error " + Logger.ErrorLogger(e));
//                }
//            }

            try
            {
                st = mysqlConn.createStatement();
                st.executeUpdate("INSERT INTO CONN_AUDIT_SUMMARY(ON_DATE, REPORT_TYPE, "
                        + "SERVER_NAME, TOMCAT_PATH, DB_SERVER_TYPE, PROJECT_NAME, "
                        + "STACK_TRACE, CONN_MECHANISM, NO_OF_LEAKS, TOTAL_CONNECTION, ENTDATE) "
                        + "SELECT date_format(OPEN_TIME,'%Y-%m-%d %H:%i') HOUR_MIN,'Open Close Time Diff',SERVER_NAME,TOMCAT_PATH,"
                        + "DB_SERVER_TYPE,PROJECT_NAME,STACK_TRACE,CONN_MECHANISM,null NO_OF_LEAKS,"
                        + "TIMESTAMPDIFF(MINUTE,OPEN_TIME,CLOSE_TIME) TOTAL_CONNECTION,SYSDATE() "
                        + "FROM CONN_AUDIT "
                        + "where date_format(OPEN_TIME,'%e-%c-%Y')='" + getPreviousDate() + "' "
                        + "AND CLOSE_TIME IS NOT NULL "
                        + "group by HOUR_MIN,SERVER_NAME,TOMCAT_PATH,DB_SERVER_TYPE,CONN_MECHANISM,"
                        + "PROJECT_NAME,STACK_TRACE "
                        + "ORDER BY TOTAL_CONNECTION desc LIMIT 0,100");
            }
            catch (SQLException ex)
            {
                Logger.DataLogger("Error " + Logger.ErrorLogger(ex));
            }
            finally
            {
                try
                {
                    if (st != null)
                    {
                        st.close();
                    }
                }
                catch (Exception e)
                {
                    Logger.DataLogger("Error " + Logger.ErrorLogger(e));
                }
            }

            try
            {
                st = mysqlConn.createStatement();
                st.executeUpdate("delete from CONN_AUDIT where DATEDIFF(sysdate(),ENTDATE)>=3");
            }
            catch (SQLException ex)
            {
                Logger.DataLogger("Error " + Logger.ErrorLogger(ex));
            }
            finally
            {
                try
                {
                    if (st != null)
                    {
                        st.close();
                    }
                }
                catch (Exception e)
                {
                    Logger.DataLogger("Error " + Logger.ErrorLogger(e));
                }
            }

            try
            {
                st = mysqlConn.createStatement();
                st.executeUpdate("delete from CONN_AUDIT_SUMMARY where DATEDIFF(sysdate(),ENTDATE)>=15");
            }
            catch (SQLException ex)
            {
                Logger.DataLogger("Error " + Logger.ErrorLogger(ex));
            }
            finally
            {
                try
                {
                    if (st != null)
                    {
                        st.close();
                    }
                }
                catch (Exception e)
                {
                    Logger.DataLogger("Error " + Logger.ErrorLogger(e));
                }
            }
        }
        catch (Exception ex)
        {
            Logger.DataLogger("Error " + Logger.ErrorLogger(ex));
        }
    }

    private static String getProjectName(String strLine2)
    {
        String strLine1 = "";
        StringBuilder strLine3;
        strLine3 = new StringBuilder();

        if (strLine2.contains("Class Name"))
        {
            strLine1 = strLine2.substring(strLine2.indexOf("Class Name :"), strLine2.indexOf("Method Name :"));
            strLine1 = strLine1.replace("Class Name :", "").trim().replaceAll("\\.", "/");
            strLine1 += ".java";
        }

        try
        {
            List<String> fileArray;
            if (tomcatNumber == 1)
            {
                fileArray = fileArrayT1;
            }
            else
            {
                fileArray = fileArrayT2;
            }

            String strLine;

            int totalFiles = fileArray.size();

            for (int i = 0; i < totalFiles; i++)
            {
                strLine = fileArray.get(i);

                if (strLine.contains(strLine1))
                {
                    strLine = strLine.substring(0, strLine.indexOf('/'));

                    if (!strLine3.toString().contains("~" + strLine + "~"))
                    {
                        strLine3.append(" ~").append(strLine).append("~");
                    }
                }
            }
        }
        catch (Exception ex)
        {
            Logger.DataLogger("Error " + Logger.ErrorLogger(ex));
        }
        return "{" + strLine3.toString().trim().replaceAll(" ", ",").replaceAll("~", "") + "}";
    }

    private static String getDistictProjects(String[] data)
    {
        StringBuilder out = new StringBuilder();
        int total = 0;
        for (int i = 0; i < data.length; i++)
        {

            if (data[i] != null && data[i].trim().length() > 0 && !(data[i].trim().contains("{}")))
            {
                out.append(data[i]);
                total++;
            }
        }

        String s[] = out.toString().replaceAll("\\}\\{", ",").replaceAll("\\{", "").replaceAll("\\}", "").trim().split(",");
        int counter;
        out = new StringBuilder();

        for (int i = 0; i < s.length; i++)
        {
            counter = 0;
            for (int j = 0; j < s.length; j++)
            {
                if (s[i].equals(s[j]))
                {
                    counter++;
                }
            }

            if (counter == total && !out.toString().contains("~" + s[i] + "~"))
            {
                out.append(" ~").append(s[i]).append("~");
            }
        }
        return out.toString().trim().replaceAll(" ", ",").replaceAll("~", "");
    }

    private static void sendMail()
    {
        EmailManager email;
        email = new EmailManager();
        Statement st_select = null;
        ResultSet rs = null;

        String to[] = null;
        String cc[] = null;
        String subject, query = "";
        StringBuilder content;
        content = new StringBuilder();

        subject = "Audit-Connection Leaks on (" + finpack.FinPack.getProperty("java_server_name") + ") " + getPreviousDate();
        try
        {
            st_select = mysqlConn.createStatement();
            query = "select SERVER_NAME,TOMCAT_PATH,PROJECT_NAME,DB_SERVER_TYPE,CONN_MECHANISM,sum(TOTAL_CONNECTION) TOTAL_CONNECTION "
                    + "from CONN_AUDIT_SUMMARY "
                    + "where date_format(ON_DATE,'%e-%c-%Y')='" + getPreviousDate() + "' "
                    + "and REPORT_TYPE='Page Date Wise' "
                    + "group by SERVER_NAME,TOMCAT_PATH,PROJECT_NAME,DB_SERVER_TYPE,CONN_MECHANISM "
                    + "having sum(TOTAL_CONNECTION) > 15000 "
                    + "order by sum(TOTAL_CONNECTION) desc";
            rs = st_select.executeQuery(query);

            if (rs.isBeforeFirst())
            {
                content.append("<html><style type=\"text/css\"> table {");
                content.append("border-collapse: collapse;");
                content.append("font-family: Arial, Helvetica, sans-serif;");
                content.append("font-size: 14px;");
                content.append("font-style: normal;");
                content.append("line-height: normal;");
                content.append("font-variant: normal;");
                content.append("text-transform: none;");
                content.append("text-decoration: none;");
                content.append("border-collapse: collapse; } </style>");

                content.append("<body> <table border='1' align=\"center\"> ");
                content.append("<tr><th colspan='100%'><u> Too many Connections Project Wise</u></th></tr>");
                content.append("<tr align=\"center\">");
                content.append("<td><b>Server Name</b></td>");
                content.append("<td><b>Tomcat Path</b></td><td>");
                content.append("<b>Project Name</b></td>");
                content.append("<td><b>DB Server</b></td>");
                content.append("<td><b>Conn. Mech.</b></td>");
                content.append("<td><b><u>Total Conn.</u></b></td>");
                content.append("</tr>");
                while (rs.next())
                {
                    content.append("<tr><td>").append(rs.getString(1));
                    content.append(" </td><td>").append(rs.getString(2));
                    content.append(" </td><td>").append(rs.getString(3));
                    content.append(" </td><td> ").append(rs.getString(4));
                    content.append(" </td><td> ").append(rs.getString(5));
                    content.append(" </td><td> ").append(rs.getInt(6)).append(" </td></tr>");
                }
                content.append("</table>");
            }
        }
        catch (Exception ex)
        {
            Logger.DataLogger("Error " + Logger.ErrorLogger(ex));
        }
        finally
        {
            try
            {
                st_select.close();
            }
            catch (Exception e)
            {
                Logger.DataLogger("Error " + Logger.ErrorLogger(e));
            }
            try
            {
                rs.close();
            }
            catch (Exception e)
            {
                Logger.DataLogger("Error " + Logger.ErrorLogger(e));
            }
        }

        String queryConnLeaks = "";
        try
        {
            st_select = mysqlConn.createStatement();
            queryConnLeaks = "select STACK_TRACE,PROJECT_NAME,CONN_MECHANISM,SERVER_NAME,TOMCAT_PATH,sum(NO_OF_LEAKS) NO_OF_LEAKS "
                    + "from CONN_AUDIT_SUMMARY "
                    + "where date_format(ON_DATE,'%e-%c-%Y')='" + getPreviousDate() + "' "
                    + "and NO_OF_LEAKS > 0 "
                    + "and REPORT_TYPE='Page Date Wise' "
                    + "and not ( STACK_TRACE like '%com.finlogic.util.ConnectionAudit%' "
                    + "and PROJECT_NAME='finstudio' ) "
                    + "group by STACK_TRACE,PROJECT_NAME,CONN_MECHANISM,SERVER_NAME,TOMCAT_PATH "
                    + "having sum(NO_OF_LEAKS) > 1 "
                    + "order by sum(NO_OF_LEAKS) desc";
            query = "select SERVER_NAME,TOMCAT_PATH,PROJECT_NAME,SUM(NO_OF_LEAKS) FROM ( " + queryConnLeaks + " ) X "
                    + "group by SERVER_NAME,TOMCAT_PATH,PROJECT_NAME "
                    + "order by sum(NO_OF_LEAKS) desc";

            rs = st_select.executeQuery(query);

            /*
             * rs = st_select.executeQuery("select
             * SERVER_NAME,TOMCAT_PATH,PROJECT_NAME,SUM(NO_OF_LEAKS) AS
             * TOTAL_LEAKS " + "from CONN_AUDIT_SUMMARY " + "where
             * date_format(ON_DATE,'%e-%c-%Y')='" + getPreviousDate() + "' " +
             * "and NO_OF_LEAKS > 0 " + "and REPORT_TYPE='Page Date Wise' " +
             * "and not ( STACK_TRACE like '%com.finlogic.util.ConnectionAudit%'
             * " + "and PROJECT_NAME='finstudio' ) " + "group by
             * SERVER_NAME,TOMCAT_PATH,PROJECT_NAME " + "order by
             * sum(NO_OF_LEAKS) desc");
             */
            if (rs.isBeforeFirst())
            {

                content.append("<br><br><table border='1' align=\"center\"> ");
                content.append("<tr><th colspan='100%'><u> No Of Leaks Project Wise</u></th></tr>");
                content.append("<tr align=\"center\"><td><b>Server Name</b></td><td><b>Tomcat Path</b></td><td><b>Project Name</b></td><td><b>No of Leaks</b></td></tr>");
                while (rs.next())
                {
                    content.append("<tr><td>" + rs.getString(1) + " </td><td>" + rs.getString(2) + " </td><td>" + rs.getString(3) + " </td><td> " + rs.getInt(4) + " </td></tr>");
                }
                content.append("</table>");
            }
        }
        catch (Exception ex)
        {
            Logger.DataLogger("Error " + Logger.ErrorLogger(ex));
        }
        finally
        {
            try
            {
                st_select.close();
            }
            catch (Exception e)
            {
                Logger.DataLogger("Error " + Logger.ErrorLogger(e));
            }
            try
            {
                rs.close();
            }
            catch (Exception e)
            {
                Logger.DataLogger("Error " + Logger.ErrorLogger(e));
            }
        }

        try
        {
            st_select = mysqlConn.createStatement();
            rs = st_select.executeQuery(queryConnLeaks);
            /*
             * rs = st_select.executeQuery("select
             * STACK_TRACE,PROJECT_NAME,CONN_MECHANISM,sum(NO_OF_LEAKS) " +
             * "from CONN_AUDIT_SUMMARY " + "where
             * date_format(ON_DATE,'%e-%c-%Y')='" + getPreviousDate() + "' " +
             * "and NO_OF_LEAKS > 0 " + "and REPORT_TYPE='Page Date Wise' " +
             * "and not ( STACK_TRACE like '%com.finlogic.util.ConnectionAudit%'
             * " + "and PROJECT_NAME='finstudio' ) " + "group by
             * STACK_TRACE,PROJECT_NAME,CONN_MECHANISM " + "order by
             * sum(NO_OF_LEAKS) desc");
             */
            if (rs.isBeforeFirst())
            {

                content.append("<br/><br/><br/>");
                content.append("<table border='1' align=\"center\"> ");
                content.append("<tr><th colspan='100%'><u> No Of Leaks Page Wise</u></th></tr>");
                content.append("<tr align=\"center\"><td><b>Project Name</b></td><td><b>Connection Mechanism</b></td><td><b>No of Leaks</b></td><td><b>Stack Trace</b></td></tr>");

                while (rs.next())
                {
                    String stkTrc = rs.getString(1);
                    stkTrc = stkTrc.replace(" Class Name :", "<br/>Class Name : ");
                    stkTrc = stkTrc.replace("Class Name :", "Class Name : ");
                    stkTrc = stkTrc.replace(" Method Name :", "<br/>Method Name : ");
                    stkTrc = stkTrc.replace(" Line No. :", "<br/>Line No : ");

                    content.append("<tr><td>").append(rs.getString(2));
                    content.append("</td><td>").append(rs.getString(3));
                    content.append("</td><td>").append(rs.getInt(6));
                    content.append("</td><td>").append(stkTrc).append("</td></tr>");
                }
                content.append("</table></body></html>");
            }
            if (content != null && !content.toString().equalsIgnoreCase(""))
            {
                if (hcp.getProperty("connection_audit_to") != null || hcp.getProperty("connection_audit_cc") != null)
                {
                    to = hcp.getProperty("connection_audit_to").split(",");
                    cc = hcp.getProperty("connection_audit_cc").split(",");
                }
                email.sendMail("fin@njtechdesk.com", to, cc, null, subject, content.toString(), null);
                Logger.DataLogger("Mail Send....");
            }
        }
        catch (Exception ex)
        {
            Logger.DataLogger("Error " + Logger.ErrorLogger(ex));
        }
        finally
        {
            try
            {
                st_select.close();
            }
            catch (Exception e)
            {
                Logger.DataLogger("Error " + Logger.ErrorLogger(e));
            }
            try
            {
                rs.close();
            }
            catch (Exception e)
            {
                Logger.DataLogger("Error " + Logger.ErrorLogger(e));
            }
        }
    }

    private static String getCurrentDate()
    {
        Calendar cal;
        cal = Calendar.getInstance();
        String strDate;
        strDate = cal.get(Calendar.DAY_OF_MONTH) + "-"
                + (cal.get(Calendar.MONTH) + 1) + "-"
                + cal.get(Calendar.YEAR);
        return strDate;
    }

    private static String getPreviousDate()
    {
        Calendar cal;
        cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -1);
        String strDate;
        strDate = cal.get(Calendar.DAY_OF_MONTH) + "-"
                + (cal.get(Calendar.MONTH) + 1) + "-"
                + cal.get(Calendar.YEAR);
        return strDate;
    }

    private static String getCurrentStaticDate()
    {
        return currD + "-" + currM + "-" + currY;
    }
}
