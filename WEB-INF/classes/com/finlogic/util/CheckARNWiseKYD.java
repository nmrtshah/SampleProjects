/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util;

import finutils.directconn.DBConnManager;
import finutils.errorhandler.ErrorHandler;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
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
//http://www.amfiindia.com/ArnSearch_Rpt_Po.aspx?strArn=155&strName=&strCity=&strPin=&strAdd=&strOpt=C
//http://portal.amfiindia.com/ArnSearch_Frm_Po.aspx?strArn=155&strName=&strCity=&strPin=&strAdd=&strOpt=C
//http://portal.amfiindia.com/ArnSearch_Rpt_Po.aspx?strArn=155&strName=&strCity=&strPin=&strAdd=&strOpt=C
//This program has been written to check KYD (Know your distributor using AMFI ARN site)
//http://www.amfiindia.com/modules/NearestFinancialAdvisorsDetails?nfaARN=155&nfaARNName=&nfaAddress=&nfaCity=&nfaPin=&nfaType=All
public class CheckARNWiseKYD
{

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
//                Logger.DataLogger("Ignore Cron 1.");
//                return;
//            }
//        }

        Logger.DataLogger("Start :" + new java.util.Date());
        try
        {
            new CheckARNWiseKYD().startProcess();
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

        java.io.FileWriter fw = null;
        java.io.PrintWriter out = null;

        try
        {
            DBConnManager dbConnManager;
            dbConnManager = new DBConnManager();

            String sqlSelect;

            sqlSelect = "SELECT X1.ARNNO,DATE_FORMAT(NJ_ARN_START_DATE,'%d-%b-%Y') NJ_ARN_START_DATE, "
                    + "DATE_FORMAT(NJ_ARN_EXP_DATE,'%d-%b-%Y') NJ_ARN_EXP_DATE,"
                    + "GRPMST.NJBRCODE,GRPMST.NAME,REGION.REGNAME FROM "
                    + "( "
                    + "SELECT GRPCODE,ARNNO,MAX(ARN_START_DATE) NJ_ARN_START_DATE,MAX(ARN_EXP_DATE) NJ_ARN_EXP_DATE FROM GRP_ARN "
                    + "GROUP BY GRPCODE,ARNNO "
                    + ") X1 "
                    + "INNER JOIN GRPMST ON X1.GRPCODE=GRPMST.GRPCODE "
                    + "INNER JOIN REGION ON REGION.REGCODE=GRPMST.BRNCODE "
                    + "WHERE GRPMST.NJBRCODE IS NOT NULL "
                    + "ORDER BY GRPMST.NJBRCODE ";

            oracleConn = dbConnManager.getFinConn("njindiainvest_offline");

            st = oracleConn.createStatement();

            rs = st.executeQuery(sqlSelect);

            //String str_file_name = "/opt/apache-tomcat1/webapps/log/log/arn-data.csv";
            String str_file_name = "/opt/apache-tomcat1/webapps/log/log/finstudio-cron-amfi-arn-" + CommonFunction.getCurrentDate() + ".txt";

            java.io.File f1;
            f1 = new java.io.File(str_file_name);

            if (f1.exists())
            {
                f1.delete();
            }

            fw = new java.io.FileWriter(str_file_name, false);
            out = new java.io.PrintWriter(fw);

            out.println("ROWNUM,ARNNO,BROKER CODE,BROKER NAME,CENTER NAME,NJ_ARN_START_DATE,NJ_ARN_EXP_DATE,AMFI_ARN_VALID_TILL,AMFI_ARN_VALID_FROM,KYD,EUIN");

            // Loop For Records
            while (rs.next())
            {
                String data = "";
                try
                {
                    data = getData(replaceString(rs.getString("ARNNO")) + "");

                    if (data.indexOf("No records to display") > -1
                            || data.indexOf("No data found on the basis of selected parameters for this report") > -1)
                    {
                        data = ",NOT-FOUND,NOT-FOUND,NOT-FOUND,NOT-FOUND";
                    }
                    else
                    {
                        data = data.substring(data.indexOf(">Sr No<"));
                        data = data.substring(0, data.indexOf("</tbody>"));
                        data = data.substring(data.indexOf("<tr style"));

                        String data1[] = null;
                        data1 = data.split("</td>");

//                        System.out.println("data1[9]=" + data1[10]);
//                        System.out.println("data1[9]=" + data1[11]);
//                        System.out.println("data1[9]=" + data1[12]);

                        data = ",@" + data1[9].substring(data1[9].lastIndexOf('>') + 1).trim();
                        data += ",@" + data1[10].substring(data1[10].lastIndexOf('>') + 1).trim();
                        data += "," + data1[11].substring(data1[11].lastIndexOf('>') + 1).trim();
                        data += "," + data1[12].substring(data1[12].lastIndexOf('>') + 1).trim();
                        //Logger.DataLogger(data);
                    }
                }
                catch (Exception e)
                {
                    //e.printStackTrace();
                    //System.out.println("data=" + data);
                    data = ",NOT-FOUND,NOT-FOUND,NOT-FOUND,NOT-FOUND";
                }

                out.println(rs.getRow() + "," + rs.getString("ARNNO") + "," + rs.getString("NJBRCODE") + ","
                        + rs.getString("NAME") + "," + rs.getString("REGNAME")
                        + ",@" + rs.getString("NJ_ARN_START_DATE")
                        + ",@" + rs.getString("NJ_ARN_EXP_DATE") + data);
            }
            sendMail(str_file_name);
        }
        finally
        {
            try
            {
                if (out != null)
                {
                    out.close();
                }
            }
            catch (Exception e)
            {
                Logger.DataLogger("Error " + Logger.ErrorLogger(e));
            }
            try
            {
                if (fw != null)
                {
                    fw.close();
                }
            }
            catch (Exception e)
            {
                Logger.DataLogger("Error " + Logger.ErrorLogger(e));
            }
            try
            {
                if (rs != null)
                {
                    rs.close();
                }
            }
            catch (Exception e)
            {
                Logger.DataLogger("Error " + Logger.ErrorLogger(e));
            }
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
            try
            {
                if (oracleConn != null)
                {
                    oracleConn.close();
                }
            }
            catch (Exception e)
            {
                Logger.DataLogger("Error " + Logger.ErrorLogger(e));
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
        String subject = "ARN Data AMFI - " + CommonFunction.getCurrentDate();
        String[] files = file.split("/");
        String content = "Download file using following link.<br>http://howeb3.nj:8081/log/log/" + files[files.length - 1];
        email.sendMail("fin@njtechdesk.com", to, cc, bcc, subject, content, null);

    }

    public String getData(String arnNo)
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
            String targetURL;
            targetURL = "http://www.amfiindia.com/modules/NearestFinancialAdvisorsDetails";
            String urlParameters = "nfaARN=" + URLEncoder.encode(arnNo, "UTF-8") + "&nfaARNName=&nfaAddress=&nfaCity=&nfaPin=&nfaType=All";
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

    public String getData1(final String arn) throws IOException
    {
        StringBuilder lines;
        lines = new StringBuilder();
        BufferedReader in = null;

        try
        {

            String strUrl = "nfaARN=" + URLEncoder.encode(arn, "UTF-8") + "&nfaARNName=&nfaAddress=&nfaCity=&nfaPin=&nfaType=All";
            //strUrl = "http://www.amfiindia.com/ArnSearch_Rpt_Po.aspx?" + strUrl;
            //strUrl = "http://portal.amfiindia.com/ArnSearch_Rpt_Po.aspx?" + strUrl;
            strUrl = "http://www.amfiindia.com/modules/NearestFinancialAdvisorsDetails?" + strUrl;

            //Logger.DataLogger("strUrl=" + strUrl);
            URL url;
            url = new URL(strUrl);
            URLConnection urlConn;
            urlConn = url.openConnection();
            in = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null)
            {
                lines.append(inputLine);
            }
        }
        catch (Exception e)
        {
            ErrorHandler.PrintInSystem(e);
        }
        finally
        {
            if (in != null)
            {
                in.close();
            }
        }
        return lines.toString();
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
        String s1;
        s1 = new String(c);
        s1 = s1.replace("=", "");
        return Integer.parseInt(s1);
    }
}
