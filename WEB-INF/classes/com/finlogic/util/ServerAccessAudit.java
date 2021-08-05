/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util;

import com.finlogic.util.properties.HardCodeProperty;
import finutils.directconn.DBConnManager;
import java.io.*;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.sql.*;
import java.util.Calendar;
import javax.mail.MessagingException;

/**
 *
 * @author njuser
 */
public final class ServerAccessAudit
{

    private static DBConnManager dbConnManager = new DBConnManager();
    private static HardCodeProperty property;
    private static String logFileDate;

    private ServerAccessAudit()
    {
    }

    public static void main(String[] args)
    {
        try
        {
            Logger.DataLogger("Start : " + args[0] + (new java.util.Date()));
            if (args[0].equals("Insert"))
            {
                if (args.length == 2 && args[1] != null)
                {
                    logFileDate = args[1];
                }
                else
                {
                    logFileDate = getCurrentDate();
                }
                //insertIntoServerAccessAudit();
            }
            else if (args[0].equals("Status404Mail"))
            {
                status404WiseMail();
            }
            else if (args[0].equals("ExecutionTimeWiseMail"))
            {
                timeWiseMail();
            }
            else if (args[0].equals("ByteSentWiseMail"))
            {
                byteSentWiseMail();
            }
            else if (args[0].equals("JQueryPathMail"))
            {
                invalidJQueryPathMail();
            }
            else
            {
                Logger.DataLogger("Please Enter Proper Arguments.." + (new java.util.Date()));
            }
            Logger.DataLogger("End : " + args[0] + (new java.util.Date()));
        }
        catch (Exception ex)
        {
            Logger.DataLogger("Error " + Logger.ErrorLogger(ex));
        }
    }

    private static void insertIntoServerAccessAudit()
    {
        PreparedStatement psInsert = null;

        FileInputStream fs = null;
        DataInputStream in = null;
        BufferedReader br = null;
        Connection mysqlConn = null;
        try
        {
            String auditFile = "localhost_access_log." + logFileDate + ".txt";
            String serverName = finpack.FinPack.getProperty("java_server_name");

            mysqlConn = dbConnManager.getFinConn("finstudio_mysql");

            String sql = "delete from SERVER_ACCESS_AUDIT where DATEDIFF(sysdate(),ENTDATE)>=15";
            Statement st = null;
            try
            {
                st = mysqlConn.createStatement();
                st.executeUpdate(sql);
            }
            finally
            {
                st.close();
            }

            sql = "INSERT INTO SERVER_ACCESS_AUDIT "
                    + "(SERVER_NAME,SERVER_PATH,IPADDRESS, REQUEST_TIME, PROJECT_NAME, "
                    + "REQUEST_URL,REFERER_URL,REQUEST_METHOD,REQUEST_PARAMS, HTTPSTATUS, BYTES_SENT, TOTAL_EXECUTION_TIME,ENTDATE) "
                    + "VALUES(? ,? ,?, ?, ?, ?, ?, ?, ?, ?, ?, ROUND(?,5),SYSDATE())";

            psInsert = mysqlConn.prepareStatement(sql);
            String requestTime = "";

            File[] fTomcats = new File("/opt/").listFiles();
            for (int i = 0; i < fTomcats.length; i++)
            {
                String tomcatPath = fTomcats[i].getAbsolutePath();
                if (tomcatPath.contains("tomcat") && new File(tomcatPath + "/conf").exists() && new File(tomcatPath + "/bin").exists())
                {
                    File f1 = new File(tomcatPath + "/logs/" + auditFile);
                    File f2 = new File(tomcatPath + "/logs/" + auditFile + ".1");
                    File f = null;
                    if (f1.exists())
                    {
                        f = f1;
                    }
                    else if (f2.exists())
                    {
                        f = f2;
                    }
                    else
                    {
                        //System.out.println("Access File not Exist for " + tomcatPath + auditFile);
                        Logger.DataLogger("Access File not Exist for " + tomcatPath + auditFile);
                        continue;
                    }
                    try
                    {
                        //System.out.println("Working on " + f.getAbsolutePath());
                        Logger.DataLogger("Working on " + f.getAbsolutePath());
                        fs = new FileInputStream(f);
                        in = new DataInputStream(fs);
                        br = new BufferedReader(new InputStreamReader(in));

                        String str = null;
                        int errorCounter = 0;
                        int recordCounter = 0;
                        while ((str = br.readLine()) != null)
                        {
                            try
                            {
                                while (str.contains("//"))
                                {
                                    str = str.replaceAll("//", "/");
                                }

                                while (str.contains("  "))
                                {
                                    str = str.replaceAll("  ", " ");
                                }

                                String[] field;
                                field = str.split(" ");
                                String IPAddress = field[0];

                                String reqTime = field[3];
                                reqTime = reqTime.replace('[', ' ').trim();
                                reqTime = reqTime.replaceFirst(":", " ");

                                String reqTimeAry[] = reqTime.split(" ");
                                String reqdate[] = reqTimeAry[0].split("/");
                                String day = reqdate[0];
                                int month = getMonth(reqdate[1]);
                                String year = reqdate[2];
                                requestTime = year + "/" + month + "/" + day + " " + reqTimeAry[1];

                                String[] prjName = field[6].split("/");
                                String requestUrl = field[6];

                                if (requestUrl.contains("?"))
                                {
                                    requestUrl = requestUrl.substring(0, requestUrl.indexOf('?'));
                                }
                                else if (requestUrl.contains(";"))
                                {
                                    requestUrl = requestUrl.substring(0, requestUrl.indexOf(';'));
                                }

                                String requestMethod = field[5].replaceAll("'", "");
                                String requestParams = field[6].replaceAll(requestUrl, "");
                                if (requestParams != null && requestParams.length() > 999)
                                {
                                    requestParams = requestParams.substring(0, 998);
                                }

                                int httpStatus = Integer.parseInt(field[8]);
                                int bytesSent;
                                if (field[9].equalsIgnoreCase("-"))
                                {
                                    bytesSent = 0;
                                }
                                else
                                {
                                    bytesSent = Integer.parseInt(field[9]);
                                }
                                float totExeTime = 0;
                                if (field.length >= 11)
                                {
                                    totExeTime = Float.parseFloat(field[10]);
                                }
                                String refererUrl = "";
                                if (field.length >= 11)
                                {
                                    refererUrl = (field[11] == null) ? "" : field[11];
                                }

                                if (refererUrl.contains("?"))
                                {
                                    refererUrl = refererUrl.substring(0, refererUrl.indexOf('?'));
                                }
                                else if (refererUrl.contains(";"))
                                {
                                    refererUrl = refererUrl.substring(0, refererUrl.indexOf(';'));
                                }

                                psInsert.clearParameters();
                                psInsert.setString(1, serverName);
                                psInsert.setString(2, tomcatPath);
                                psInsert.setString(3, IPAddress);
                                psInsert.setString(4, requestTime);
                                psInsert.setString(5, prjName[1]);
                                psInsert.setString(6, requestUrl);
                                psInsert.setString(7, refererUrl);
                                psInsert.setString(8, requestMethod);
                                psInsert.setString(9, requestParams);
                                psInsert.setInt(10, httpStatus);
                                psInsert.setInt(11, bytesSent);
                                psInsert.setDouble(12, totExeTime);

                                psInsert.executeUpdate();
                                recordCounter++;
                            }
                            catch (Exception ex)
                            {
                                errorCounter++;
                                if ((recordCounter - errorCounter) < 0)
                                {
                                    Logger.ErrorLogger(ex);
                                    Logger.DataLogger("Error while Inserting: " + ex.getMessage() + " Line =" + str);

                                    if ((recordCounter - errorCounter) <= -100)
                                    {
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    finally
                    {
                        if (br != null)
                        {
                            br.close();
                        }
                        if (in != null)
                        {
                            in.close();
                        }
                        if (fs != null)
                        {
                            fs.close();
                        }
                    }
                }

            }
            requestTime = requestTime.split(" ")[0];
            String sqlURLWiseAccessUpdate = "UPDATE URL_WISE_ACCESS X2 "
                    + "INNER JOIN "
                    + "( "
                    + "SELECT SERVER_NAME,PROJECT_NAME,HTTPSTATUS,REQUEST_URL,MAX(REQUEST_TIME) LA,COUNT(REQUEST_URL) TH "
                    + "FROM SERVER_ACCESS_AUDIT "
                    // following conversion is used to trim time from datetime field
                    + "WHERE STR_TO_DATE(DATE_FORMAT(REQUEST_TIME,'%Y/%m/%d'),'%Y/%m/%d')=STR_TO_DATE('" + requestTime + "','%Y/%m/%d') "
                    + "GROUP BY SERVER_NAME,PROJECT_NAME,HTTPSTATUS,REQUEST_URL "
                    + ") X1 "
                    + "ON X1.SERVER_NAME=X2.SERVER_NAME AND X1.PROJECT_NAME=X2.PROJECT_NAME  "
                    + "AND X1.HTTPSTATUS=X2.HTTP_STATUS AND X1.REQUEST_URL=X2.REQUEST_URL "
                    + "SET X2.LAST_ACCESS=X1.LA,X2.TOTAL_HITS=TOTAL_HITS+X1.TH";

            Statement stURLWiseAccessUpdate = null;
            try
            {
                stURLWiseAccessUpdate = mysqlConn.createStatement();
                int count = stURLWiseAccessUpdate.executeUpdate(sqlURLWiseAccessUpdate);
                Logger.DataLogger("URL_WISE_ACCESS UPDATE Count = " + count);
            }
            finally
            {
                stURLWiseAccessUpdate.close();
            }
            String sqlURLWiseAccessInsert = "INSERT INTO URL_WISE_ACCESS "
                    + " SELECT X1.SRNO,X1.SERVER_NAME,X1.PROJECT_NAME,X1.HTTPSTATUS,X1.REQUEST_URL,X1.FA,X1.LA,X1.TH FROM "
                    + "( "
                    + "SELECT NULL SRNO,SERVER_NAME,PROJECT_NAME,HTTPSTATUS,REQUEST_URL,MIN(REQUEST_TIME) FA,MAX(REQUEST_TIME) LA,COUNT(REQUEST_URL) TH "
                    + "FROM SERVER_ACCESS_AUDIT "
                    // following conversion is used to trim time from datetime field
                    + "WHERE STR_TO_DATE(DATE_FORMAT(REQUEST_TIME,'%Y/%m/%d'),'%Y/%m/%d')=STR_TO_DATE('" + requestTime + "','%Y/%m/%d') "
                    + "GROUP BY SERVER_NAME,PROJECT_NAME,HTTPSTATUS,REQUEST_URL "
                    + ") X1 "
                    + "LEFT JOIN URL_WISE_ACCESS X2 "
                    + "ON X1.SERVER_NAME=X2.SERVER_NAME AND X1.PROJECT_NAME=X2.PROJECT_NAME  "
                    + "AND X1.HTTPSTATUS=X2.HTTP_STATUS AND X1.REQUEST_URL=X2.REQUEST_URL "
                    + "WHERE X2.SRNO IS NULL";

            Statement stURLWiseAccessInsert = null;
            try
            {
                stURLWiseAccessInsert = mysqlConn.createStatement();
                int count = stURLWiseAccessInsert.executeUpdate(sqlURLWiseAccessInsert);
                Logger.DataLogger("URL_WISE_ACCESS INSERT Count = " + count);
            }
            finally
            {
                stURLWiseAccessInsert.close();
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
                psInsert.close();
            }
            catch (Exception e)
            {
            }
            try
            {
                mysqlConn.close();
            }
            catch (Exception e)
            {
            }

        }
    }

    private static void status404WiseMail() throws Exception
    {
        EmailManager email = new EmailManager();
        Statement stSelect = null;
        ResultSet resultSet = null;
        property = new HardCodeProperty();
        String to[] = null;
        String cc[] = null;
        Connection mysqlConn = null;
        try
        {
            //mysqlConn = dbConnManager.getFinConn("finstudio_mysql");
            mysqlConn = dbConnManager.getFinConn("dbaudit");

            stSelect = mysqlConn.createStatement();
            resultSet = stSelect.executeQuery("select SERVER_NAME,'NA' SERVER_PATH,PROJECT_NAME,REQUEST_URL,REFERER_URL,substring(REQUEST_PARAMS,1,50) REQUEST_PARAMS1,sum(BYTES_SENT) BYTES_SENT,"
                    + "ROUND(sum(TOTAL_EXECUTION_TIME),5) EXECUTION_TIME,count(*) HITS "
                    //+ "FROM SERVER_ACCESS_AUDIT "
                    + "from SA_" + getPreviousDateYYYYMMDD() + " "
                    + "where HTTPSTATUS=404 "
                    //+ "and date_format(ENTDATE,'%e-%c-%Y')='" + getPreviousDate() + "' "
                    + "group by SERVER_NAME,SERVER_PATH,PROJECT_NAME,REQUEST_URL,REFERER_URL,REQUEST_PARAMS1 "
                    + "having count(*) >= 50 "
                    + "ORDER BY HITS DESC");
            if (resultSet.isBeforeFirst() == true)
            {
                final String subject = "Audit-Server Access Audit Report-404 on Date " + getPreviousDate() + " (" + finpack.FinPack.getProperty("java_server_name") + ")";
                final StringBuilder content = new StringBuilder();
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
                content.append("<tr><th colspan='100%'><u>Report for 404 Request Status</u></th></tr>");
                content.append("<tr align=\"center\"><td><b>Server Name</b></td><td><b>Server Path</b></td><td><b>Project Name</b></td><td><b>Request URL</b></td><td><b>Referer URL</b></td><td><b>Parameters</b></td><td><b>Total Bytes Sent</b></td><td><b>Total Execution Time</b></td><td><b>Total Hit Count</b></td></tr>");
                while (resultSet.next())
                {
                    content.append("<tr><td>").append(resultSet.getString(1)).append("</td><td>");
                    content.append(resultSet.getString(2)).append("</td><td>");
                    content.append(resultSet.getString(3)).append("</td><td>");
                    content.append(resultSet.getString(4)).append("</td><td>");
                    content.append(resultSet.getString(5)).append("</td><td>");
                    content.append(resultSet.getString(6)).append("</td><td>");
                    content.append(resultSet.getString(7)).append("</td><td>");
                    content.append(resultSet.getString(8)).append("</td><td>");
                    content.append(resultSet.getString(9)).append("</td></tr>");
                }

                content.append("</table></body></html>");
                if (property.getProperty("server_access_audit_404_to") != null || property.getProperty("server_access_audit_404_cc") != null)
                {
                    to = property.getProperty("server_access_audit_404_to").split(",");
                    cc = property.getProperty("server_access_audit_404_cc").split(",");
                }
                email.sendMail("fin@njtechdesk.com", to, cc, null, subject, content.toString(), null);
                Logger.DataLogger("Mail Send....");
            }
            else
            {
                Logger.DataLogger("No Data Found..");
            }
            resultSet.close();
            stSelect.close();

        }
        catch (Exception ex)
        {
            Logger.DataLogger("Error " + Logger.ErrorLogger(ex));
        }
        finally
        {
            try
            {
                resultSet.close();
            }
            catch (Exception e)
            {
            }
            try
            {
                stSelect.close();
            }
            catch (Exception e)
            {
            }
            try
            {
                mysqlConn.close();
            }
            catch (Exception e)
            {
            }
        }
    }

    private static void timeWiseMail()
            throws ClassNotFoundException, SQLException, MessagingException, GeneralSecurityException, UnsupportedEncodingException
    {
        EmailManager email = new EmailManager();
        Statement statement = null;
        ResultSet resultSet = null;
        property = new HardCodeProperty();
        int minExTime = 0;
        if (property.getProperty("min_execution_time_sec") != null)
        {
            minExTime = Integer.parseInt(property.getProperty("min_execution_time_sec"));
        }

        String to[] = null;
        String cc[] = null;
        Connection mysqlConn = null;
        try
        {

            //mysqlConn = dbConnManager.getFinConn("finstudio_mysql");
            mysqlConn = dbConnManager.getFinConn("dbaudit");

            statement = mysqlConn.createStatement();
            resultSet = statement.executeQuery("SELECT SERVER_NAME,'NA' SERVER_PATH,IPADDRESS,REQUEST_TIME,PROJECT_NAME,REQUEST_URL,HTTPSTATUS,"
                    + "REQUEST_METHOD,substring(REQUEST_PARAMS,1,50) REQUEST_PARAMS,BYTES_SENT,"
                    + "ROUND((TOTAL_EXECUTION_TIME/60),5) EXECUTION_TIME  "
                    + "from SA_" + getPreviousDateYYYYMMDD() + " "
                    //+ "FROM SERVER_ACCESS_AUDIT "
                    + "where ROUND((TOTAL_EXECUTION_TIME),5) >= " + minExTime + " "
                    //+ "and date_format(REQUEST_TIME,'%e-%c-%Y')='" + getPreviousDate() + "' "
                    + "ORDER BY TOTAL_EXECUTION_TIME desc "
                    + "LIMIT 0,10 ");

            if (resultSet.isBeforeFirst())
            {
                final String subject = "Audit-Server Access Audit Report-Total Execution Time on Date " + getPreviousDate() + " (" + finpack.FinPack.getProperty("java_server_name") + ")";
                final StringBuilder content = new StringBuilder();

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
                content.append("<tr><th colspan='100%'><u>Report for Pagewise Total Execution Time(above " + minExTime + " seconds)</u></th></tr>");
                content.append("<tr align=\"center\"><td><b>Server Name</b></td><td><b>Server Path</b></td><td><b>IP Address</b></td><td><b>Request Time</b></td><td><b>Project Name</b></td><td><b>Request URL</b></td><td><b>HTTP Status</b></td><td><b>Request Method</b></td><td><b>Parameters</b></td><td><b>Total Bytes Sent</b></td><td><b>Total Execution Time(MINUTE)</b></td></tr>");

                while (resultSet.next())
                {
                    content.append("<tr><td>").append(resultSet.getString(1)).append(" </td><td>");
                    content.append(resultSet.getString(2)).append("</td><td>").append(resultSet.getString(3)).append(" </td><td> ");
                    content.append(resultSet.getString(4)).append(" </td><td> ").append(resultSet.getString(5)).append(" </td><td> ");
                    content.append(resultSet.getString(6)).append(" </td><td> ").append(resultSet.getString(7)).append(" </td><td> ");
                    content.append(resultSet.getString(8)).append(" </td><td> ").append(resultSet.getString(9)).append(" </td><td> ");
                    content.append(resultSet.getString(10)).append(" </td><td> ").append(resultSet.getString(11)).append(" </td></tr>");
                }

                content.append("</table></body></html>");

                if (property.getProperty("server_access_audit_extime_to") != null || property.getProperty("server_access_audit_extime_cc") != null)
                {
                    to = property.getProperty("server_access_audit_extime_to").split(",");
                    cc = property.getProperty("server_access_audit_extime_cc").split(",");
                }
                email.sendMail("fin@njtechdesk.com", to, cc, null, subject, content.toString(), null);
                Logger.DataLogger("Mail Send....");
            }
            else
            {
                Logger.DataLogger("No Data Found..");
            }
        }
        finally
        {
            if (resultSet != null)
            {
                resultSet.close();
            }
            if (statement != null)
            {
                statement.close();
            }
            try
            {
                mysqlConn.close();
            }
            catch (Exception e)
            {
            }
        }


    }

    private static void byteSentWiseMail()
            throws SQLException, MessagingException, ClassNotFoundException, GeneralSecurityException, UnsupportedEncodingException
    {
        EmailManager email = new EmailManager();
        Statement statement = null;
        ResultSet resultSet = null;
        property = new HardCodeProperty();
        int minBytes = 0;
        if (property.getProperty("min_bytes_sent") != null)
        {
            minBytes = Integer.parseInt(property.getProperty("min_bytes_sent"));
        }
        String to[] = null;
        String cc[] = null;
        Connection mysqlConn = null;
        try
        {
            //mysqlConn = dbConnManager.getFinConn("finstudio_mysql");
            mysqlConn = dbConnManager.getFinConn("dbaudit");

            statement = mysqlConn.createStatement();
            resultSet = statement.executeQuery("SELECT SERVER_NAME,'NA' SERVER_PATH,IPADDRESS,REQUEST_TIME,PROJECT_NAME,REQUEST_URL,HTTPSTATUS,"
                    + "REQUEST_METHOD,substring(REQUEST_PARAMS,1,50) REQUEST_PARAMS,(BYTES_SENT/1024)/1024 BYTES_MB,"
                    + "ROUND(TOTAL_EXECUTION_TIME,5) EXECUTION_TIME  "
                    //+ "FROM SERVER_ACCESS_AUDIT "
                    + "from SA_" + getPreviousDateYYYYMMDD() + " "
                    + "where BYTES_SENT >= " + minBytes + " "
                    //+ "and date_format(REQUEST_TIME,'%e-%c-%Y')='" + getPreviousDate() + "' "
                    + "ORDER BY BYTES_SENT DESC "
                    + "LIMIT 0,10");

            if (resultSet.isBeforeFirst())
            {
                final String subject = "Audit-Server Access Audit Report-Total Bytes Sent on Date " + getPreviousDate() + " (" + finpack.FinPack.getProperty("java_server_name") + ")";
                final StringBuilder content = new StringBuilder();
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
                content.append("<tr><th colspan='100%'><u>Report for Pagewise Total Bytes Sent(above 15728640 bytes)</u></th></tr>");
                content.append("<tr align=\"center\"><td><b>Server Name</b></td><td><b>Server Path</b></td><td><b>IP Address</b></td><td><b>Request Time</b></td><td><b>Project Name</b></td><td><b>Request URL</b></td><td><b>HTTP Status</b></td><td><b>Request Method</b></td><td><b>Parameters</b></td><td><b>Total Bytes Sent(MB)</b></td><td><b>Total Execution Time</b></td></tr>");

                while (resultSet.next())
                {
                    content.append("<tr><td>").append(resultSet.getString(1)).append(" </td><td>");
                    content.append(resultSet.getString(2)).append(" </td><td>").append(resultSet.getString(3)).append(" </td><td> ");
                    content.append(resultSet.getString(4)).append(" </td><td> ").append(resultSet.getString(5)).append(" </td><td> ");
                    content.append(resultSet.getString(6)).append(" </td><td> ").append(resultSet.getString(7)).append(" </td><td> ");
                    content.append(resultSet.getString(8)).append(" </td><td> ").append(resultSet.getString(9)).append(" </td><td> ");
                    content.append(resultSet.getString(10)).append(" </td><td> ").append(resultSet.getString(11)).append(" </td></tr>");
                }

                content.append("</table></body></html>");
                if (property.getProperty("server_access_audit_bytes_to") != null || property.getProperty("server_access_audit_bytes_cc") != null)
                {
                    to = property.getProperty("server_access_audit_bytes_to").split(",");
                    cc = property.getProperty("server_access_audit_bytes_cc").split(",");
                }
                email.sendMail("fin@njtechdesk.com", to, cc, null, subject, content.toString(), null);
                Logger.DataLogger("Mail Send....");
            }
            else
            {
                Logger.DataLogger("Mail Send....");
            }

            resultSet.close();
            statement.close();

        }
        finally
        {
            if (resultSet != null)
            {
                resultSet.close();
            }
            if (statement != null)
            {
                statement.close();
            }
            try
            {
                mysqlConn.close();
            }
            catch (Exception e)
            {
            }
        }

    }

    private static void invalidJQueryPathMail() throws Exception
    {
        EmailManager email = new EmailManager();
        Statement statement = null;
        ResultSet resultSet = null;
        property = new HardCodeProperty();
        String to[] = null;
        String cc[] = null;
        Connection mysqlConn = null;
        try
        {
            //mysqlConn = dbConnManager.getFinConn("finstudio_mysql");
            mysqlConn = dbConnManager.getFinConn("dbaudit");

            statement = mysqlConn.createStatement();
            String query = "SELECT DISTINCT SERVER_NAME,'NA' SERVER_PATH,PROJECT_NAME,"
                    + " SUBSTRING(REQUEST_URL,1,GREATEST(INSTR(REQUEST_URL, 'jquery-ui')+15,INSTR(REQUEST_URL, 'dhtmlx')+15)) JQUERY_PATH "
                    + "from SA_" + getPreviousDateYYYYMMDD() + " "
                    //+ " FROM SERVER_ACCESS_AUDIT "
                    + " where PROJECT_NAME!='finlibrary' "
                    + " and (REQUEST_URL like '%jquery-ui%' OR REQUEST_URL like '%dhtmlx%') "
                    //+ " and date_format(REQUEST_TIME,'%e-%c-%Y') ='" + getPreviousDate() + "' "
                    + " and HTTPSTATUS!=404 ";

            resultSet = statement.executeQuery(query);

            if (resultSet.isBeforeFirst())
            {
                String subject = "Audit-Server Access Audit Report-Invalid Finlibrary Path " + getPreviousDate() + " (" + finpack.FinPack.getProperty("java_server_name") + ")";
                StringBuilder content = new StringBuilder();

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
                content.append("<body><table border='1' align=\"center\"> ");
                content.append("<tr><th colspan='100%'><u>Report for Invalid JQuery Path</u></th></tr>");
                content.append("<tr align=\"center\"><td><b>Server Name</b></td><td><b>Server Path</b></td><td><b>Project Name</b></td><td><b>JQuery Path</b></td></tr>");
                while (resultSet.next())
                {
                    content.append("<tr><td>").append(resultSet.getString("SERVER_NAME"));
                    content.append("</td><td>").append(resultSet.getString("SERVER_PATH"));
                    content.append("</td><td>").append(resultSet.getString("PROJECT_NAME"));
                    content.append("</td><td>").append(URLEncoder.encode(resultSet.getString("JQUERY_PATH"), "UTF-8")).append("</td></tr>");
                }
                content.append("</table></body></html>");

                if (property.getProperty("server_access_audit_jquery_path_to") != null || property.getProperty("server_access_audit_jquery_path_cc") != null)
                {
                    to = property.getProperty("server_access_audit_jquery_path_to").split(",");
                    cc = property.getProperty("server_access_audit_jquery_path_cc").split(",");
                }
                email.sendMail("fin@njtechdesk.com", to, cc, null, subject, content.toString(), null);
                Logger.DataLogger("JQUERY PATH Mail Send....");
            }
            else
            {
                Logger.DataLogger("No Data Found..");
            }
        }
        finally
        {
            if (resultSet != null)
            {
                resultSet.close();
            }
            if (statement != null)
            {
                statement.close();
            }
            try
            {
                mysqlConn.close();
            }
            catch (Exception e)
            {
            }
        }

    }

    private static String getCurrentDate()
    {
        Calendar c = Calendar.getInstance();

        String month = "" + (c.get(Calendar.MONTH) + 1);
        String day = "" + c.get(Calendar.DAY_OF_MONTH);

        if ((c.get(Calendar.MONTH) + 1) > 0 && (c.get(Calendar.MONTH) + 1) < 10)
        {
            month = "0" + (c.get(Calendar.MONTH) + 1);
        }
        if (c.get(Calendar.DAY_OF_MONTH) > 0 && c.get(Calendar.DAY_OF_MONTH) < 10)
        {
            day = "0" + c.get(Calendar.DAY_OF_MONTH);
        }
        String strDate = c.get(Calendar.YEAR) + "-"
                + month + "-"
                + day;

        return strDate;
    }

    private static String getPreviousDateYYYYMMDD()
    {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, -1);

        String month = "" + (c.get(Calendar.MONTH) + 1);
        String day = "" + c.get(Calendar.DAY_OF_MONTH);

        if ((c.get(Calendar.MONTH) + 1) > 0 && (c.get(Calendar.MONTH) + 1) < 10)
        {
            month = "0" + (c.get(Calendar.MONTH) + 1);
        }
        if (c.get(Calendar.DAY_OF_MONTH) > 0 && c.get(Calendar.DAY_OF_MONTH) < 10)
        {
            day = "0" + c.get(Calendar.DAY_OF_MONTH);
        }
        String strDate = c.get(Calendar.YEAR) + month + day;

        return strDate;
    }

    private static int getMonth(String month)
    {
        int mnth = 0;


        if (month.equalsIgnoreCase("Jan"))
        {
            mnth = 1;


        }
        else if (month.equalsIgnoreCase("Feb"))
        {
            mnth = 2;


        }
        else if (month.equalsIgnoreCase("Mar"))
        {
            mnth = 3;


        }
        else if (month.equalsIgnoreCase("Apr"))
        {
            mnth = 4;


        }
        else if (month.equalsIgnoreCase("May"))
        {
            mnth = 5;


        }
        else if (month.equalsIgnoreCase("Jun"))
        {
            mnth = 6;


        }
        else if (month.equalsIgnoreCase("Jul"))
        {
            mnth = 7;


        }
        else if (month.equalsIgnoreCase("Aug"))
        {
            mnth = 8;


        }
        else if (month.equalsIgnoreCase("Sep"))
        {
            mnth = 9;


        }
        else if (month.equalsIgnoreCase("Oct"))
        {
            mnth = 10;


        }
        else if (month.equalsIgnoreCase("Nov"))
        {
            mnth = 11;


        }
        else if (month.equalsIgnoreCase("Dec"))
        {
            mnth = 12;


        }
        return mnth;


    }

    private static String getPreviousDate()
    {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, -1);
        String strDate =
                c.get(Calendar.DAY_OF_MONTH) + "-"
                + (c.get(Calendar.MONTH) + 1) + "-"
                + c.get(Calendar.YEAR);



        return strDate;


    }

    private static void cleanup()
    {
        String[] cleanupFolders =
        {
            finpack.FinPack.getProperty("tomcat1_path") + "/webapps/log/log",
            finpack.FinPack.getProperty("tomcat1_path") + "/webapps/finstudio/log"
        };

        int[] cleanupByDays =
        {
            7, 7
        };

        for (int i = 0; i < cleanupFolders.length; i++)
        {
            cleanupOldFolder(cleanupFolders[i], cleanupByDays[i]);
        }
    }

    private static void cleanupOldFolder(String cleanupFolders, int cleanupByDay)
    {
        try
        {
            long day = 1000 * 60 * 60 * 24;

            File f1 = new File(cleanupFolders);



            if (f1.exists() && f1.isDirectory())
            {

                File[] f2 = f1.listFiles();

                // delete old files



                for (int i = 0; i
                        < f2.length; i++)
                {
                    if (f2[i].isDirectory())
                    {
                        //System.out.println("DIR=" + f2[i].getAbsolutePath());
                        cleanupOldFolder(f2[i].getAbsolutePath(), cleanupByDay);


                    }
                    if ((System.currentTimeMillis() - f2[i].lastModified()) > (day * cleanupByDay))
                    {
                        //System.out.println("FILE=" + f2[i].getAbsolutePath());
                        f2[i].delete();


                    }
                }
            }


        }
        catch (Exception ex)
        {
            Logger.DataLogger("Error " + Logger.ErrorLogger(ex));
        }

    }
}