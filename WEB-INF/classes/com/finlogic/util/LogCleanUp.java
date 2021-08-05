/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util;

import com.finlogic.util.properties.HardCodeProperty;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Calendar;
import javax.mail.MessagingException;

/**
 *
 * @author njuser
 */
public final class LogCleanUp
{

    private static HardCodeProperty hcp = new HardCodeProperty();

    private LogCleanUp()
    {
    }

    public static void main(String[] args)
    {
        try
        {
            //LogCleanUp.start();
            //LogCleanUp.cleanup();
            Calendar cal;
            cal = Calendar.getInstance();
            int weekly_mail_day;
            weekly_mail_day = Integer.parseInt(hcp.getProperty("log_cleanup_audit_weekly_mail_day"));
            if (cal.get(Calendar.DAY_OF_WEEK) == weekly_mail_day || weekly_mail_day == 0)
            {
                LogCleanUp.sendMail();
            }
        }
        catch (Exception ex)
        {
            Logger.DataLogger("Error " + Logger.ErrorLogger(ex));
        }
    }

    private static void sendMail() throws GeneralSecurityException, MessagingException, UnsupportedEncodingException
    {
        StringBuilder maxDays = new StringBuilder();
        StringBuilder maxSize = new StringBuilder();
        StringBuilder isDir = new StringBuilder();
        StringBuilder logDir = new StringBuilder();

        long day;
        day = 1000 * 60 * 60 * 24;
        int count = 0;
        String serverName;
        serverName = finpack.FinPack.getProperty("java_server_name");
        if (serverName.equalsIgnoreCase("njtestweb1") || serverName.equalsIgnoreCase("njimage"))
        {
            count = 1;
        }
        else
        {
            if (serverName.equalsIgnoreCase("njtest") || serverName.equalsIgnoreCase("njapps"))
            {
                count = 2;
            }
        }

        String[] dirName = new String[2];
        dirName[0] = finpack.FinPack.getProperty("tomcat1_path") + "/webapps";
        dirName[1] = finpack.FinPack.getProperty("tomcat2_path") + "/webapps";

        int propMaxDays = Integer.parseInt(hcp.getProperty("logcleanup_audit_max_days"));
        int propMaxSize = Integer.parseInt(hcp.getProperty("logcleanup_audit_max_size"));

        // check old file,max size file and folder

        for (int k = 0; k < count; k++)
        {
            File f1;
            f1 = new File(dirName[k]);
            if (f1.exists() && f1.isDirectory())
            {
                File[] f2;
                f2 = f1.listFiles();
                for (int i = 0; i < f2.length; i++)
                {
                    String logDirName;
                    logDirName = f2[i].getAbsolutePath() + "/log";
                    File logFiles;
                    logFiles = new File(logDirName);
                    if (logFiles.exists() && logFiles.isDirectory())
                    {
                        File[] logFileList;
                        logFileList = logFiles.listFiles();

                        for (int j = 0; j < logFileList.length; j++)
                        {
                            if ((System.currentTimeMillis() - logFileList[j].lastModified()) > (day * propMaxDays))
                            {
                                maxDays.append(logFileList[j].getAbsolutePath() + "<br/>");
                            }
                            // check file size
                            if (!logFileList[j].isDirectory() && logFileList[j].length() > 1024L * 1024 * propMaxSize)
                            {
                                // ignore jar- files
                                if (!logFileList[j].getAbsolutePath().contains("log/log/jar-"))
                                {
                                    maxSize.append(logFileList[j].getAbsolutePath() + "<br/>");
                                }
                            }
                            if (logFileList[j].isDirectory())
                            {
                                isDir.append(logFileList[j].getAbsolutePath() + "<br/>");
                            }
                        }
                    }
                }
            }
        }
        // check /log folder in projects dir

        for (int k = 0; k < count; k++)
        {
            File f1 = new File(dirName[k]);
            if (f1.exists() && f1.isDirectory())
            {
                File[] f2 = f1.listFiles();
                for (int i = 0; i < f2.length; i++)
                {
                    if (f2[i].getAbsolutePath().contains("/log"))
                    {
                        continue;
                    }
                    File f3 = new File(f2[i].getAbsolutePath() + "/log");
                    if (f3.exists() && f3.isDirectory())
                    {
                        logDir.append(f2[i].getAbsolutePath() + "/log" + "<br/>");
                    }
                }
            }
        }

        EmailManager email;
        email = new EmailManager();

        String to[] = null;
        String cc[] = null;

        String subject;
        subject = "Audit-Log cleanup report on " + getCurrentDate() + " - " + finpack.FinPack.getProperty("server_type");
        String content = "";
        if (maxDays != null && maxDays.length() != 0)
        {
            content += "<br/><b>Files Older Than " + propMaxDays + " Days</b><br/><br/>";
            content += maxDays.toString() + "<br/>";
        }
        if (maxSize != null && maxSize.length() != 0)
        {
            content += "<br/><b>Files Greater Than " + propMaxSize + " MB</b><br/><br/>";
            content += maxSize.toString() + "<br/>";
        }
        if (isDir != null && isDir.length() != 0)
        {
            content += "<br/><b>Invalid Folders</b><br/><br/>";
            content += isDir.toString() + "<br/>";
        }
        if (logDir != null && logDir.length() != 0)
        {
            content += "<br/><b>Invalid Log Folders</b><br/><br/>";
            content += logDir.toString();
        }
        if (!"".equals(content))
        {
            if (hcp.getProperty("log_cleanup_to") != null || hcp.getProperty("log_cleanup_cc") != null)
            {
                to = hcp.getProperty("log_cleanup_to").split(",");
                cc = hcp.getProperty("log_cleanup_cc").split(",");
            }
            email.sendMail("fin@njtechdesk.com", to, cc, null, subject, content, null);
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
}
