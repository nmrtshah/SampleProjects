/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util;

import com.finlogic.util.properties.HardCodeProperty;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.*;
import javax.mail.MessagingException;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author njuser
 */
public class JarFileAudit
{

    private static HardCodeProperty hcp = new HardCodeProperty();
    private static EmailManager email;
    private static String content = "";

    public static void main(String[] args)
    {
        try
        {
            Calendar cal;
            cal = Calendar.getInstance();
            int weekly_mail_day;
            weekly_mail_day = Integer.parseInt(hcp.getProperty("jar_audit_weekly_mail_day"));
            if (cal.get(Calendar.DAY_OF_WEEK) == weekly_mail_day || weekly_mail_day == 0)
            {
                JarFileAudit rpll;
                rpll = new JarFileAudit();
                Logger.DataLogger("Jar File Audit Start....." + (new java.util.Date()));
                rpll.start();
                Logger.DataLogger("Jar File Audit End....." + (new java.util.Date()));
            }
        }
        catch (Exception ex)
        {
            Logger.DataLogger("Error in Main() : " + ex.getMessage());
            finutils.errorhandler.ErrorHandler.PrintInFile(ex, finpack.FinPack.getProperty("tomcat1_path") + "/webapps/finstudio/log");
        }
    }

    public void start() throws FileNotFoundException, GeneralSecurityException, MessagingException, UnsupportedEncodingException
    {
        String[] extensions =
        {
            "jar"
        };

        List<String> tomcatLibs;
        tomcatLibs = new ArrayList<String>();

        addFileRecursive(finpack.FinPack.getProperty("tomcat1_path") + "/lib", extensions, tomcatLibs);

        File[] fTomcats = new File("/opt/").listFiles();
        for (int i = 0; i < fTomcats.length; i++)
        {
            String tomcatPath = fTomcats[i].getAbsolutePath();
            if (tomcatPath.contains("tomcat") && new File(tomcatPath + "/conf").exists() && new File(tomcatPath + "/bin").exists())
            {
                searchFileRecursive(tomcatPath + "/webapps", extensions, tomcatLibs);
            }
        }
//        searchFileRecursive(finpack.FinPack.getProperty("tomcat1_path") + "/webapps", extensions, tomcatLibs);
//        if (!(finpack.FinPack.getProperty("tomcat2_path") != null
//                && finpack.FinPack.getProperty("tomcat2_path").equals(finpack.FinPack.getProperty("tomcat1_path"))))
//        {
//            searchFileRecursive(finpack.FinPack.getProperty("tomcat2_path") + "/webapps", extensions, tomcatLibs);
//        }
        sendMail();
    }

    private static void searchFileRecursive(final String dirPath, final String[] extensions, final List<String> tomcatLibs)
    {
        StringBuilder sbInvalid;
        sbInvalid = new StringBuilder();
        StringBuilder sbNotPresent;
        sbNotPresent = new StringBuilder();
        StringBuilder sbRenamed;
        sbRenamed = new StringBuilder();

        File root;
        root = new File(dirPath);

        boolean recursive;
        recursive = true;

        Collection files;
        files = FileUtils.listFiles(root, extensions, recursive);

        for (Iterator iterator = files.iterator(); iterator.hasNext();)
        {
            boolean isProjectLib = false;

            File file;
            file = (File) iterator.next();

            if (file.getAbsolutePath().contains("finstudio"))
            {
                continue;
            }
            String projectLib;
            projectLib = file.getName();

            boolean tomcatLibPresent = false;
            for (Iterator it = tomcatLibs.iterator(); it.hasNext();)
            {
                String tomcatLib;
                tomcatLib = (String) it.next();
                if (projectLib.equals(tomcatLib))
                {
                    tomcatLibPresent = true;
                }
            }
            projectLib = file.getAbsolutePath();
            if (projectLib.contains("/WEB-INF/lib")
                    && projectLib.split("/").length == 8)
            {
                isProjectLib = true;
            }

            if (!tomcatLibPresent && isProjectLib)
            {
                sbNotPresent.append(projectLib);
                sbNotPresent.append("<br/>");
            }
            else if (tomcatLibPresent && isProjectLib)
            {
                sbRenamed.append(projectLib);
                sbRenamed.append("<br/>");
            }
            else if (!projectLib.endsWith("FTScan.jar") && !projectLib.endsWith("JTwain.jar")
                    && !projectLib.endsWith("MyTextPrinter.jar"))
            {
                sbInvalid.append(projectLib);
                sbInvalid.append("<br/>");
            }
        }

        if (sbInvalid.length() != 0 || sbNotPresent.length() != 0 || sbRenamed.length() != 0)
        {
            content += "<br/><b>For " + dirPath + " </b><br/>";
        }
        if (sbInvalid.length() != 0)
        {
            content += "<br/><b>These .jar files are present on invalid location - Should be deleted.</b><br/>" + sbInvalid.toString();
        }

        if (sbNotPresent.length() != 0)
        {
            content += "<br/><b>These .jar files are not present in tomcat/lib.<br/>(Check whether updated .jar is already there in tomcat/lib ? If yes, delete this file else add in tomcat/lib and then delete.) </b><br/>" + sbNotPresent.toString();
        }

        if (sbRenamed.length() != 0)
        {
            content += "<br/><b>These .jar files are already present in tomcat/lib. Should be deleted.</b> <br/>" + sbRenamed.toString();
        }

    }

    private static void addFileRecursive(final String dirPath, final String[] extensions, final List<String> tomcatLibs)
    {
        File root;
        root = new File(dirPath);

        boolean recursive;
        recursive = true;

        Collection files;
        files = FileUtils.listFiles(root, extensions, recursive);

        for (Iterator iterator = files.iterator(); iterator.hasNext();)
        {
            File file;
            file = (File) iterator.next();
            String s;
            s = file.getName();
            tomcatLibs.add(s);
        }
    }

    private static void sendMail() throws GeneralSecurityException, MessagingException, UnsupportedEncodingException
    {
        email = new EmailManager();
        String to[] = null;
        String cc[] = null;

        String subject;
        subject = "Audit-Invalid Location for Jar Files " + getCurrentDate() + " - " + finpack.FinPack.getProperty("server_type");

        if (!"".equals(content))
        {
            if (hcp.getProperty("jar_file_audit_to") != null || hcp.getProperty("jar_file_audit_cc") != null)
            {
                to = hcp.getProperty("jar_file_audit_to").split(",");
                cc = hcp.getProperty("jar_file_audit_cc").split(",");
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
