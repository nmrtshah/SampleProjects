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
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import javax.mail.MessagingException;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author njuser
 */
public class ACLClassFileAudit
{

    private static HardCodeProperty hcp = new HardCodeProperty();
    private static EmailManager email;
    private static StringBuilder sbInvalid = new StringBuilder();

    public static void main(String[] args)
    {
        try
        {
            Calendar cal;
            cal = Calendar.getInstance();
            int weekly_mail_day;
            weekly_mail_day = Integer.parseInt(hcp.getProperty("aclagent_audit_weekly_mail_day"));
            if (cal.get(Calendar.DAY_OF_WEEK) == weekly_mail_day || weekly_mail_day == 0)
            {
                ACLClassFileAudit obj;
                obj = new ACLClassFileAudit();
                obj.start();
            }
        }
        catch (Exception ex)
        {
            Logger.DataLogger("Error " + Logger.ErrorLogger(ex));
        }
    }

    private void start() throws FileNotFoundException, GeneralSecurityException, MessagingException, UnsupportedEncodingException
    {
        String[] extensions =
        {
            "class"
        };

        searchFileRecursive(finpack.FinPack.getProperty("tomcat1_path") + "/webapps", extensions);
        if (!(finpack.FinPack.getProperty("tomcat2_path") != null
                && finpack.FinPack.getProperty("tomcat2_path").equals(finpack.FinPack.getProperty("tomcat1_path"))))
        {
            searchFileRecursive(finpack.FinPack.getProperty("tomcat2_path") + "/webapps", extensions);
        }

        sendMail();
    }

    private static void searchFileRecursive(final String dirPath, final String[] extensions)
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

            //String projectLib = file.getName();
            String projectLib;
            projectLib = file.getAbsolutePath();

            if (file.getName().toLowerCase().contains("aclagent"))
            {
                sbInvalid.append(projectLib);
                sbInvalid.append("<br/>");
                sbInvalid.append(projectLib.replace(".class", ".java"));
                sbInvalid.append("<br/>");
            }
        }
    }

    private static void sendMail() throws GeneralSecurityException, MessagingException, UnsupportedEncodingException
    {
        email = new EmailManager();
        String to[] = null;
        String cc[] = null;
        String content = "";

        if (sbInvalid.length() != 0)
        {
            String subject;
            subject = " Audit-Invalid ACLAgent Usage on (" + finpack.FinPack.getProperty("java_server_name") + ") " + getCurrentDate();
            content = "<b>Invalid ACLAgent Usage. Should be Deleted.</b><br/><br/>";
            content += sbInvalid.toString();
            if (hcp.getProperty("aclagent_audit_to") != null || hcp.getProperty("aclagent_audit_cc") != null)
            {
                to = hcp.getProperty("aclagent_audit_to").split(",");
                cc = hcp.getProperty("aclagent_audit_cc").split(",");
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
