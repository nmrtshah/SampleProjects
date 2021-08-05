/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util;

import finutils.directconn.DBConnManager;
import finutils.errorhandler.ErrorHandler;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.mail.MessagingException;

/**
 *
 * @author njuser
 */
public class CheckARNWiseKYDOnCamps
{

    private static String dynamicParam = "";
    private static String targetURL;

    public static void main(String[] args)
    {
        if (args != null && args.length > 0 && args[0].equalsIgnoreCase("TEST"))
        {
            Logger.DataLogger("Test Successful");
            return;
        }
//        if (args != null && args.length > 0)
//        {
//            if (args[0].equalsIgnoreCase("TEST"))
//            {
//                Logger.DataLogger("Test Successful");
//                return;
//            }
//            if (args[0].equals("ARNCron") && Calendar.getInstance().get(Calendar.DAY_OF_WEEK) != 1)
//            {
//                Logger.DataLogger("Ignore Cron 2.");
//                return;
//            }
//        }

        Logger.DataLogger("Start :" + new java.util.Date());
        try
        {
            //targetURL = "https://www.camsonline.com/invonline/chkPanArnValidity.aspx";
            //targetURL = "http://www.camsonline.com/InvOnline/COL_CnkPanKycValidity.aspx";
            targetURL = "https://www.camsonline.com/InvOnline/COL_CnkPanKycValidity.aspx";

            CheckARNWiseKYDOnCamps obj = new CheckARNWiseKYDOnCamps();
            dynamicParam = obj.getParam();
            //obj.startProcess();
        }
        catch (Exception e)
        {
            ErrorHandler.PrintInSystem(e);
        }
        Logger.DataLogger("Done :" + new java.util.Date());
    }

    public void startProcess() throws SQLException, ClassNotFoundException, IOException, GeneralSecurityException, MessagingException
    {
        Connection oracleConn = null;
        Statement st = null;
        ResultSet rs = null;

        FileWriter fw = null;
        PrintWriter out = null;

        try
        {
            DBConnManager dbConnManager = new DBConnManager();

            String sqlSelect = "SELECT X1.ARNNO,DATE_FORMAT(NJ_ARN_START_DATE,'%d-%b-%Y') NJ_ARN_START_DATE, "
                    + "DATE_FORMAT(NJ_ARN_EXP_DATE,'%d-%b-%Y') NJ_ARN_EXP_DATE,"
                    + "GRPMST.NJBRCODE,GRPMST.NAME,REGION.REGNAME FROM "
                    + "( "
                    + "SELECT GRPCODE,ARNNO,MAX(ARN_START_DATE) NJ_ARN_START_DATE,MAX(ARN_EXP_DATE) NJ_ARN_EXP_DATE FROM GRP_ARN "
                    + "GROUP BY GRPCODE,ARNNO "
                    + ") X1 "
                    + "INNER JOIN GRPMST ON X1.GRPCODE=GRPMST.GRPCODE "
                    + "INNER JOIN REGION ON REGION.REGCODE=GRPMST.BRNCODE "
                    + "WHERE GRPMST.NJBRCODE IS NOT NULL "
                    + "ORDER BY GRPMST.NJBRCODE LIMIT 20";

            oracleConn = dbConnManager.getFinConn("njindiainvest_offline");

            st = oracleConn.createStatement();

            rs = st.executeQuery(sqlSelect);

            String str_file_name = "/opt/apache-tomcat1/webapps/log/log/finstudio-cron-cams-arn-" + CommonFunction.getCurrentDate() + ".txt";

            java.io.File f1 = new java.io.File(str_file_name);

            if (f1.exists())
            {
                f1.delete();
            }

            fw = new java.io.FileWriter(str_file_name, false);
            out = new java.io.PrintWriter(fw);

            out.println("ROWNUM,ARNNO,BROKER_CODE,BROKER_NAME,CENTER_NAME,NJ_ARN_VALID_FROM,NJ_ARN_VALID_TILL,NAME_OF_THE_DISTRIBUTOR,STATUS,ARN_VALID_FROM,ARN_VALID_TILL");

            // Loop For Records
            while (rs.next())
            {
                StringBuilder dataW = new StringBuilder();
                String data = "";
                try
                {
                    data = excutePost(replaceString(rs.getString("ARNNO")) + "");
                    //data = excutePost("123844");
                    String[] fields =
                    {
                        "Name of the Distributor", "Status :", "ARN valid from", "ARN valid till"
                    };
                    if (data.indexOf("No records were retrieved") > -1)
                    {
                        dataW.append(",NOT-FOUND,NOT-FOUND,NOT-FOUND,NOT-FOUND");
                    }
                    else
                    {
                        String data1 = null;
                        for (int i = 0; i < fields.length; i++)
                        {
                            data1 = data;
                            data1 = data1.substring(data1.indexOf(fields[i]));

                            data1 = data1.substring(data1.indexOf("<td"));
                            data1 = data1.substring(data1.indexOf('>'), data1.indexOf("</td>"));
                            if (fields[i].equalsIgnoreCase("ARN valid from") || fields[i].equalsIgnoreCase("ARN valid till"))
                            {
                                dataW.append(",");
                                dataW.append(data1.replace(">", "@"));
                            }
                            else
                            {
                                dataW.append(",");
                                dataW.append(data1.replace(">", ""));
                            }
                        }
                    }
                }
                catch (Exception e)
                {
                    dataW.append(",ERROR,ERROR,ERROR,ERROR");
                    Logger.DataLogger("ERROR : " + e.getMessage());
                }

                out.println(rs.getRow() + "," + rs.getString("ARNNO") + "," + rs.getString("NJBRCODE") + ","
                        + rs.getString("NAME") + "," + rs.getString("REGNAME") + ",@"
                        + rs.getString("NJ_ARN_START_DATE") + ",@"
                        + rs.getString("NJ_ARN_EXP_DATE") + dataW);
            }
            //sendMail(str_file_name);
        }
        finally
        {
            try
            {
                out.close();
            }
            catch (Exception e)
            {
                Logger.ErrorLogger(e);
            }
            try
            {
                fw.close();
            }
            catch (Exception e)
            {
                Logger.ErrorLogger(e);
            }
            try
            {
                rs.close();
            }
            catch (Exception e)
            {
                Logger.ErrorLogger(e);
            }
            try
            {
                st.close();
            }
            catch (Exception e)
            {
                Logger.ErrorLogger(e);
            }
            try
            {
                oracleConn.close();
            }
            catch (Exception e)
            {
                Logger.ErrorLogger(e);
            }
        }
    }

    private static void sendMail(String file) throws GeneralSecurityException, MessagingException, UnsupportedEncodingException
    {
        EmailManager email = new EmailManager();
        String[] to =
        {
            "sagar.mahadik@njgroup.in"
        };
        String[] cc =
        {
            "dilip@njgroup.in"
        };
        String[] bcc =
        {
            "bhatu.mali@njgroup.in"
        };
        String subject = "ARN Data CAMS - " + CommonFunction.getCurrentDate();
        String[] files = file.split("/");
        String content = "Download file using following link.<br>http://howeb3.nj:8081/log/log/" + files[files.length - 1];
        email.sendMail("fin@njtechdesk.com", to, cc, bcc, subject, content, null);

    }

    public String excutePost(String arnNo)
    {
        if (arnNo == null || arnNo.trim().length() == 0)
        {
            return null;
        }

        URL url;
        HttpURLConnection connection = null;
        DataOutputStream wr = null;
        InputStream is = null;
        BufferedReader rd = null;
        try
        {

            //String urlParameters = dynamicParam + "&btnSubmit=Submit&txtARN=" + arnNo + "&txtPAN=";
            String urlParameters = dynamicParam + "&ctl00%24PageContent%24btnSubmit.x=42&ctl00%24PageContent%24btnSubmit.y=8&ctl00%24PageContent%24txtARN=" + arnNo + "&ctl00%24PageContent%24txtPAN=";
            //urlParameters = "__EVENTVALIDATION=%2FwEWBQLH1qmUDAK6%2B%2F6OCgKn%2B9KcDgLCi9reAwKgt7D9CrvSULBiDLUcNUR%2B4OVfiPH%2BY5lg&__VIEWSTATE=%2FwEPDwUJMTQyNTQyNzY5ZGQhLDlpFhBa7sSAWtsNHu7NCLmM7Q%3D%3D&btnSubmit=Submit&txtARN=" + arnNo + "&txtPAN=";
            //urlParameters = "__EVENTVALIDATION=%2FwEWBQKS3p3WAgK6%2B%2F6OCgKn%2B9KcDgLCi9reAwKgt7D9CqcnbOCWdWU9DyOSB4RObAGZm0pO&__VIEWSTATE=%2FwEPDwUJMTQyNTQyNzY5ZGSzj%2BtlyoeUA%2B1gRKx%2FBnrQLuMFjw%3D%3D&btnSubmit=Submit&txtARN=" + arnNo + "&txtPAN=";
            //Create connection
            url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(1000 * 60);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            connection.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));
            connection.setRequestProperty("Content-Language", "en-US");

            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            connection.setReadTimeout(1000 * 60);

            //Send request
            wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            //Get Response
            is = connection.getInputStream();
            rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuilder response;
            response = new StringBuilder();
            while ((line = rd.readLine()) != null)
            {
                response.append(line);
            }
            return response.toString();
        }
        catch (Exception e)
        {
            Logger.ErrorLogger(e);
            return null;
        }
        finally
        {
            try
            {
                rd.close();
            }
            catch (Exception e)
            {
                Logger.ErrorLogger(e);
            }
            try
            {
                is.close();
            }
            catch (Exception e)
            {
                Logger.ErrorLogger(e);
            }
            try
            {
                wr.close();
            }
            catch (Exception e)
            {
                Logger.ErrorLogger(e);
            }
            try
            {
                connection.disconnect();
            }
            catch (Exception e)
            {
                Logger.ErrorLogger(e);
            }
        }
    }

    public String getParam()
    {
        URL url;
        HttpURLConnection connection = null;
        DataOutputStream wr = null;
        InputStream is = null;
        BufferedReader rd = null;
        try
        {
            url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Language", "en-US");

            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            connection.setReadTimeout(1000 * 60);

            //Send request
            wr = new DataOutputStream(connection.getOutputStream());
            wr.flush();
            wr.close();

            //Get Response
            is = connection.getInputStream();
            rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuilder response;
            response = new StringBuilder();
            while ((line = rd.readLine()) != null)
            {
                response.append(line);
            }
            String param1 = response.toString();
            param1 = param1.substring(param1.indexOf("__VIEWSTATE"));
            param1 = param1.substring(param1.indexOf("value"));
            param1 = param1.replaceAll("value=\"", "");
            param1 = param1.replaceAll("value='", "");
            param1 = param1.replaceAll("value=", "");
            param1 = param1.substring(0, param1.indexOf("\""));

            String param2 = response.toString();
            param2 = param2.substring(param2.indexOf("__EVENTVALIDATION"));
            param2 = param2.substring(param2.indexOf("value"));
            param2 = param2.replaceAll("value=\"", "");
            param2 = param2.replaceAll("value='", "");
            param2 = param2.replaceAll("value=", "");
            param2 = param2.substring(0, param2.indexOf("\""));

            return "__EVENTARGUMENT=&__EVENTTARGET=&__EVENTVALIDATION=" + URLEncoder.encode(param2, "UTF-8") + "&__VIEWSTATE=" + URLEncoder.encode(param1, "UTF-8");
        }
        catch (Exception e)
        {
            Logger.ErrorLogger(e);
            return null;
        }
        finally
        {
            try
            {
                rd.close();
            }
            catch (Exception e)
            {
                Logger.ErrorLogger(e);
            }
            try
            {
                is.close();
            }
            catch (Exception e)
            {
                Logger.ErrorLogger(e);
            }
            try
            {
                wr.close();
            }
            catch (Exception e)
            {
                Logger.ErrorLogger(e);
            }
            try
            {
                connection.disconnect();
            }
            catch (Exception e)
            {
                Logger.ErrorLogger(e);
            }
        }
    }

    private static int replaceString(String s)
    {
        char[] c = s.toCharArray();

        for (int i = 0; i < s.length(); i++)
        {
            if (s.charAt(i) == '0'
                    || s.charAt(i) == '1'
                    || s.charAt(i) == '2'
                    || s.charAt(i) == '3'
                    || s.charAt(i) == '4'
                    || s.charAt(i) == '5'
                    || s.charAt(i) == '6'
                    || s.charAt(i) == '7'
                    || s.charAt(i) == '8'
                    || s.charAt(i) == '9')
            {
                continue;
            }
            else
            {
                c[i] = '=';
            }
        }
        String s1 = new String(c);
        s1 = s1.replace("=", "");
        return Integer.parseInt(s1);
    }
}
