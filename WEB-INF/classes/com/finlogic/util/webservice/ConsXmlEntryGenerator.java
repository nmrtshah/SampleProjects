/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.webservice;

import com.finlogic.apps.finstudio.webservice.WebServiceFormBean;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

/**
 *
 * @author Sonam Patel
 */
public class ConsXmlEntryGenerator
{

    public void createEntry(final WebServiceFormBean formBean) throws IOException
    {
        String fileName;
        fileName = formBean.getIntrfcName().substring(0, formBean.getIntrfcName().length() - 5);

        String filePath;
        filePath = WebServiceFormBean.getTOMCAT() + "/webapps/finstudio/generated/" + formBean.getTxtSrno() + "WS/consumer/";
        File file;
        file = new File(filePath);
        file.mkdirs();

        String pkg;
        pkg = FileService.getPackage(WebServiceFormBean.getWSLOC() + "/" + formBean.getTxtSrno() + "tmp/" + formBean.getTxtSrno() + ".txt");

        PrintWriter pw;
        pw = new PrintWriter(filePath + "ConsXmlEntry.txt");
        try
        {
            String endPointId;
            endPointId = Character.toLowerCase(fileName.charAt(0)) + fileName.substring(1);
            String addr;
            addr = endPointId.substring(0, endPointId.length() - 7);

            //Make WSConsumer.xml Entry
            pw.println("/* Copy these Entries and paste in WSConsumer.xml under /tomcat Directory, if web.xml does not contain these. */");
            pw.println("/* WSConsumer.xml Entries Start */");
            pw.println("    <bean id=\"" + addr + "Factory\" class=\"org.apache.cxf.jaxws.JaxWsProxyFactoryBean\">");
            pw.println("        <property name=\"serviceClass\" value=\"" + pkg + "." + fileName + "\"/>");
            String server = finpack.FinPack.getProperty("java_server_name");
            if ("njapps".equals(server))
            {
                pw.println("        <property name=\"address\" value=\"http://" + server + ":8080/" + formBean.getCmbProjectName() + "/ws/" + endPointId.toLowerCase(Locale.getDefault()) + "\"/>");
            }
            else
            {
                pw.println("        <property name=\"address\" value=\"http://" + server + "/" + formBean.getCmbProjectName() + "/ws/" + endPointId.toLowerCase(Locale.getDefault()) + "\"/>");
            }
            pw.println("        <property name=\"serviceFactory\" ref=\"jaxwsAndAegisServiceFactory\"/>");
            pw.println("    </bean>");
            pw.println();
            pw.println("    <bean id=\"" + endPointId + "\"");
            pw.println("          class=\"" + pkg + "." + fileName + "\"");
            pw.println("          factory-bean=\"" + addr + "Factory\"");
            pw.println("          factory-method=\"create\"/>");
            pw.println("/* WSConsumer.xml Entries Over */");
            pw.println();
        }
        finally
        {
            pw.close();
        }
    }
}
