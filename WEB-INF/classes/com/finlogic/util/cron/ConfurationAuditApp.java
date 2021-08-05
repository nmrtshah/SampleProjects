/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.cron;

import com.finlogic.util.Logger;
import com.finlogic.util.finreport.DirectConnection;
import com.finlogic.util.persistence.SQLConnService;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author njuser
 */
public class ConfurationAuditApp
{

    private static DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
    private static String server_name;
    private static StringBuilder persistQuery = new StringBuilder();

    public static void main(String[] args) throws UnknownHostException
    {
        try
        {
            server_name = InetAddress.getLocalHost().getHostName();

            persistQuery.setLength(0);
            persistQuery.append("DELETE FROM finstudio.CONFIG_AUDIT where ENTRYTYPE LIKE '%").append(server_name).append("'");
            persistDataQuery(persistQuery.toString());

            File checkFExist;

            checkFExist = new File("/tomcat/property_config.txt");
            if (checkFExist.exists())
            {
                readPropAndSvnConfigFile(checkFExist, "property");
            }

            checkFExist = null;
            checkFExist = new File("/techfin/license.xml");
            if (checkFExist.exists())
            {
                readConfigFiles(checkFExist, "conninfo");
            }

            checkFExist = null;
            checkFExist = new File("/tomcat/MailConfig.xml");
            if (checkFExist.exists())
            {
                readConfigFiles(checkFExist, "mailinfo");
            }

//            checkFExist = new File("/etc/httpd/conf.d/subversion.conf");
//            
//            if (checkFExist.exists()) 
//            {
//                readPropAndSvnConfigFile(checkFExist, "svn");
//            } 
            // code for read subversion file
            HttpURLConnection urlConnection = null;
            checkFExist = null;
            try {
                URL urlObject = new URL("http://utilsvnmaster.nj:8080/svnconf/subversion.conf");
                urlConnection = (HttpURLConnection) urlObject.openConnection();
                if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    try ( InputStream inputStream = urlConnection.getInputStream();  
                          OutputStream outputStream = new FileOutputStream("/tmp/subversion.conf");) {
                          IOUtils.copy(inputStream, outputStream);
                    }
                    checkFExist = new File("/tmp/subversion.conf");
                    readPropAndSvnConfigFile(checkFExist, "svn");
                }
            } catch (IOException e) {
                Logger.ErrorLogger(e);
            } finally {
                if (new File("/tmp/subversion.conf").exists()) {
                    new File("/tmp/subversion.conf").delete();
                }
                urlConnection.disconnect();
            }

            checkFExist = null;
            checkFExist = new File("/etc/samba/smb.conf");

            if (checkFExist.exists())
            {
                readSmbConfigFile(checkFExist, "smb");
            }

            checkFExist = null;
            checkFExist = new File("/opt");
            if (checkFExist.exists())
            {
                List<File> tomcatInstances = new ArrayList<File>();
                String[] tomcatNames = checkFExist.list();

                for (String tomcatIns : tomcatNames)
                {
                    if (tomcatIns.startsWith("tomcat")
                            || tomcatIns.startsWith("apache-tomcat"))
                    {
                        File f1 = new File("/opt/" + tomcatIns + "/bin");
                        File f2 = new File("/opt/" + tomcatIns + "/conf");
                        if (f1.exists() && f2.exists() && f1.isDirectory() && f2.isDirectory())
                        {
                            tomcatInstances.add(new File("/opt/" + tomcatIns));
                        }
                    }
                }

                if (tomcatInstances.size() > 0)
                {
                    readJarAppDetails(tomcatInstances, "jars");
                    readJarAppDetails(tomcatInstances, "apps");
                }
            }

            checkFExist = null;
            checkFExist = new File("/tomcat/WSConsumer.xml");
            if (checkFExist.exists())
            {
                readWSConfigFile(checkFExist);

                // consuers could exist only if WSConsumer.xml file exist
                // to execute once in day - after 3 PM only
                //Calendar c = Calendar.getInstance();
                //int hour = c.get(Calendar.HOUR_OF_DAY);
                //if (hour > 15)
                {
                    updateServiceUsageRecords();
                }
            }
        }
        catch (Exception e)
        {
            Logger.ErrorLogger(e);
        }

    }

    public static void readPropAndSvnConfigFile(File f, String identifykey)
    {
        BufferedReader br = null;

        try
        {
            br = new BufferedReader(new FileReader(f));
            String strLine;
            long entryId = 0;

            persistQuery.setLength(0);
            persistQuery.append("INSERT INTO finstudio.CONFIG_AUDIT(ENTRYID,ENTRYFIELD,ENTRYVALUE,ENTRYTYPE) VALUES");

            if (identifykey.equals("property"))
            {
                String[] vProperty;

                while ((strLine = br.readLine()) != null)
                {
                    if (!strLine.startsWith("#"))
                    {
                        entryId = System.nanoTime();
                        vProperty = strLine.split("=");
                        persistQuery.append("('").append(entryId).append("','propertyname','").append(vProperty[0]).append("','property_").append(server_name).append("'),")
                                .append("('").append(entryId).append("','propertyvalue','").append(vProperty[1]).append("','property_").append(server_name).append("'),");
                    }
                }
            }
            else
            {
                int i = 0;

                while ((strLine = br.readLine()) != null)
                {
                    strLine = strLine.trim();
                    if (strLine.startsWith("<Location"))
                    {
                        i = i + 1;
                        entryId = System.nanoTime();
                        if (i == 1)
                        {
                            persistQuery.append("('").append(entryId).append("','svnProjectUrl','").append(strLine.substring(strLine.lastIndexOf(" ") + 1, strLine.length() - 1)).append("','svn_").append(server_name).append("'),");
                        }
                        else
                        {
                            Logger.ErrorLogger(new Exception("Syntactical mistake at subversion.conf"));
                            return;
                        }
                    }
                    else if (strLine.startsWith("SVNPath"))
                    {
                        i = i - 1;
                        if (i == 0)
                        {
                            persistQuery.append("('").append(entryId).append("','svnrepoPath','").append(strLine.substring(strLine.lastIndexOf(" ") + 1)).append("','svn_").append(server_name).append("'),");
                        }
                        else
                        {
                            Logger.ErrorLogger(new Exception("Syntactical mistake at subversion.conf"));
                            return;
                        }
                    }
                }
            }
            persistDataQuery(persistQuery.deleteCharAt(persistQuery.length() - 1).toString());

        }
        catch (Exception e)
        {
            Logger.ErrorLogger(e);
        }
        finally
        {
            try
            {
                br.close();
            }
            catch (Exception e)
            {
                Logger.ErrorLogger(e);
            }
        }
    }

    public static void readSmbConfigFile(File f, String identifykey)
    {
        BufferedReader br = null;

        try
        {
            br = new BufferedReader(new FileReader(f));
            String strLine;
            long entryId = 0;

            persistQuery.setLength(0);
            persistQuery.append(" INSERT INTO finstudio.CONFIG_AUDIT (ENTRYID,ENTRYFIELD,ENTRYVALUE,ENTRYTYPE) VALUES ");

            String findData = "path,valid users,writeable,read list,hosts allow";
            int findDataLength = findData.split(",").length;

            int count = 0;
            strLine = br.readLine();

            while (strLine != null)
            {
                strLine = strLine.trim();

                if (strLine.startsWith("[") || strLine.endsWith("]"))
                {
                    StringBuilder smbData = new StringBuilder();
                    smbData.append("share=").append(strLine.substring(1, strLine.length() - 1)).append(";");
                    count = 0;
                    while ((strLine = br.readLine()) != null)
                    {
                        if (strLine.startsWith("[") || strLine.endsWith("]"))
                        {
                            break;
                        }
                        else
                        {
                            if (strLine.trim().length() > 0 && findData.contains(strLine.split("=")[0].toLowerCase().trim()))
                            {
                                count++;
                                if (strLine.contains("valid users"))
                                {
                                    String allUsers = strLine.split("=")[1].replaceAll(" ", "");
                                    List list = new ArrayList(Arrays.asList(allUsers.split("\\s*,\\s*")));
                                    Collections.sort(list);
                                    strLine = "users=" + list.toString().substring(1, list.toString().length() - 1);
                                }
                                else if (strLine.contains("read list"))
                                {
                                    String readList = strLine.split("=")[1].replaceAll(" ", "");
                                    List list = new ArrayList(Arrays.asList(readList.split("\\s*,\\s*")));
                                    Collections.sort(list);
                                    strLine = "readonly=" + list.toString().substring(1, list.toString().length() - 1);
                                }
                                else if (strLine.contains("hosts allow"))
                                {
                                    String allowHost = strLine.split("=")[1].replaceAll(" ", "");
                                    List list = new ArrayList(Arrays.asList(allowHost.split("\\s*,\\s*")));
                                    Collections.sort(list);
                                    strLine = "allowhost=" + list.toString().substring(1, list.toString().length() - 1);
                                }
                                smbData.append(strLine).append(";");
                            }
                        }
                    }
                    if (!smbData.toString().contains("readonly="))
                    {
                        smbData.append("readonly=NA").append(";");
                        count++;
                    }
                    if (!smbData.toString().contains("allowhost="))
                    {
                        smbData.append("allowhost=NA").append(";");
                        count++;
                    }
                    if (count == findDataLength)
                    {
                        entryId = System.nanoTime();
                        String data[] = smbData.toString().split(";");

                        for (String finalData : data)
                        {
                            if (finalData.trim().startsWith("path"))
                            {
                                String status;
                                String checkPath = finalData.split("=")[1].trim();
                                if (new File(checkPath).exists())
                                {
                                    status = "valid";
                                }
                                else
                                {
                                    status = "invalid";
                                }
                                persistQuery.append("('").append(entryId).append("','").append(finalData.split("=")[0].trim()).append("','").append(finalData.split("=")[1].trim()).append("','").append(identifykey).append("_").append(server_name).append("'),");
                                persistQuery.append("('").append(entryId).append("','").append("status").append("','").append(status).append("','").append(identifykey).append("_").append(server_name).append("'),");
                            }
                            else
                            {
                                persistQuery.append("('").append(entryId).append("','").append(finalData.split("=")[0].trim()).append("','").append(finalData.split("=")[1].trim()).append("','").append(identifykey).append("_").append(server_name).append("'),");
                            }
                        }
                    }
                }
                else
                {
                    strLine = br.readLine();
                }
            }
            persistDataQuery(persistQuery.deleteCharAt(persistQuery.length() - 1).toString());
//            new SQLUtility().persist("finstudio_mysql", persistQuery.deleteCharAt(persistQuery.length() - 1).toString());
        }
        catch (Exception e)
        {
            Logger.ErrorLogger(e);
        }
        finally
        {
            try
            {
                br.close();
            }
            catch (Exception e)
            {
                Logger.ErrorLogger(e);
            }
        }
    }

    public static void readConfigFiles(File f, String identifykey)
    {
        try
        {
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(f);
            doc.getDocumentElement().normalize();

            persistQuery.setLength(0);
            persistQuery.append("INSERT INTO finstudio.CONFIG_AUDIT(ENTRYID,ENTRYFIELD,ENTRYVALUE,ENTRYTYPE) VALUES");

            NodeList listOfNodes = doc.getElementsByTagName(identifykey);
            long entryId;

            if (identifykey.equals("conninfo"))
            {
                for (int s = 0; s < listOfNodes.getLength(); s++)
                {
                    entryId = System.nanoTime();
                    Element eElement = (Element) listOfNodes.item(s);
                    persistQuery.append("('").append(entryId).append("','alias','").append(eElement.getElementsByTagName("connalias").item(0).getTextContent()).append("','connection_").append(server_name).append("'),")
                            .append("('").append(entryId).append("','loginname','").append(eElement.getElementsByTagName("loginname").item(0).getTextContent()).append("','connection_").append(server_name).append("'),")
                            .append("('").append(entryId).append("','url','").append(eElement.getElementsByTagName("url").item(0).getTextContent()).append("','connection_").append(server_name).append("'),")
                            .append("('").append(entryId).append("','driver','").append(eElement.getElementsByTagName("driver").item(0).getTextContent()).append("','connection_").append(server_name).append("'),");
                }
            }
            else if (identifykey.equals("mailinfo"))
            {
                for (int s = 0; s < listOfNodes.getLength(); s++)
                {
                    entryId = System.nanoTime();
                    Element eElement = (Element) listOfNodes.item(s);
                    persistQuery.append("('").append(entryId).append("','mailalias','").append(eElement.getElementsByTagName("mailalias").item(0).getTextContent()).append("','mail_").append(server_name).append("'),")
                            .append("('").append(entryId).append("','username','").append(eElement.getElementsByTagName("username").item(0).getTextContent()).append("','mail_").append(server_name).append("'),")
                            .append("('").append(entryId).append("','host','").append(eElement.getElementsByTagName("host").item(0).getTextContent()).append("','mail_").append(server_name).append("'),")
                            .append("('").append(entryId).append("','port','").append(eElement.getElementsByTagName("port").item(0).getTextContent()).append("','mail_").append(server_name).append("'),")
                            .append("('").append(entryId).append("','ssl','").append(eElement.getElementsByTagName("is_ssl").item(0).getTextContent()).append("','mail_").append(server_name).append("'),");
                }
            }
            persistDataQuery(persistQuery.deleteCharAt(persistQuery.length() - 1).toString());
        }
        catch (Exception e)
        {
            Logger.ErrorLogger(e);

        }
    }

    public static void readWSConfigFile(File f)
    {
        try
        {
            long entryId;

            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(f);

            XPath xpath = XPathFactory.newInstance().newXPath();
            XPathExpression expr = xpath.compile("//bean[@class=\"org.apache.cxf.jaxws.JaxWsProxyFactoryBean\"]");
            Object exprResult = expr.evaluate(doc, XPathConstants.NODESET);
            NodeList listOfNodes = (NodeList) exprResult;

            persistQuery.setLength(0);
            persistQuery.append("INSERT INTO finstudio.CONFIG_AUDIT(ENTRYID,ENTRYFIELD,ENTRYVALUE,ENTRYTYPE) VALUES");

            for (int s = 0; s < listOfNodes.getLength(); s++)
            {
                NodeList childProNodeList = ((Element) listOfNodes.item(s)).getElementsByTagName("property");

                entryId = System.nanoTime();
                String serviceClass = ((Element) childProNodeList.item(0)).getAttribute("value");
                persistQuery.append("('").append(entryId).append("','").append(((Element) childProNodeList.item(0)).getAttribute("name")).append("','").append(serviceClass).append("','ws_").append(server_name).append("'),")
                        .append("('").append(entryId).append("','").append(((Element) childProNodeList.item(1)).getAttribute("name")).append("','").append(((Element) childProNodeList.item(1)).getAttribute("value")).append("','ws_").append(server_name).append("'),")
                        .append("('").append(entryId).append("','serviceId','").append(serviceClass.substring(serviceClass.lastIndexOf(".") + 1)).append("','ws_").append(server_name).append("'),");

            }

            persistDataQuery(persistQuery.deleteCharAt(persistQuery.length() - 1).toString());

        }
        catch (Exception e)
        {
            Logger.ErrorLogger(e);
        }
    }

    public static void readJarAppDetails(List<File> files, String identifykey)
    {
        try
        {
            long entryId = 0;

            persistQuery.setLength(0);
            persistQuery.append("INSERT INTO finstudio.CONFIG_AUDIT(ENTRYID,ENTRYFIELD,ENTRYVALUE,ENTRYTYPE) VALUES");

            if (identifykey.equals("jars"))
            {
                SimpleDateFormat sDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date lDate = new Date();
                for (File file : files)
                {
                    File[] jarList = new File(file.getAbsolutePath() + "/lib").listFiles();

                    for (File jar : jarList)
                    {
                        if (jar.getName().endsWith(".jar"))
                        {
                            entryId = System.nanoTime();
                            lDate.setTime(jar.lastModified());

                            persistQuery.append("('").append(entryId).append("','jarPath','").append(jar.getAbsolutePath()).append("','jar_").append(server_name).append("'),")
                                    .append("('").append(entryId).append("','jarSize','").append(jar.length()).append("','jar_").append(server_name).append("'),")
                                    .append("('").append(entryId).append("','jarLastModified','").append(sDateFormat.format(lDate)).append("','jar_").append(server_name).append("'),");
                        }
                    }
                }
            }
            else
            {
                String appName;
                for (File file : files)
                {
                    File[] appList = new File(file.getAbsolutePath() + "/webapps").listFiles();

                    for (File app : appList)
                    {
                        appName = app.getName();
                        if (!appName.equals("ROOT") && !appName.equals("host-manager") && !appName.equals("manager") && !appName.equals("zapcat"))
                        {
                            entryId = System.nanoTime();
                            persistQuery.append("('").append(entryId).append("','appName','").append(appName).append("','app_").append(server_name).append("'),")
                                    .append("('").append(entryId).append("','appPath','").append(app.getAbsolutePath()).append("','app_").append(server_name).append("'),");
                        }
                    }
                }
            }
            persistDataQuery(persistQuery.deleteCharAt(persistQuery.length() - 1).toString());

        }
        catch (Exception e)
        {
            Logger.ErrorLogger(e);
        }
    }

    public static void updateServiceUsageRecords()
    {
        try
        {
            List serviceList = listDataQuery("SELECT distinct ENTRYVALUE FROM finstudio.CONFIG_AUDIT "
                    + "where ENTRYFIELD='serviceId' "
                    + "and ENTRYTYPE LIKE 'ws_devhoweb%'");

            BufferedReader stdInput = null;
            String aLine, serviceName, consumerName;
            String hostName = InetAddress.getLocalHost().getHostName(), pQueryonAService = "INSERT INTO finstudio.CONFIG_AUDIT(ENTRYID,ENTRYFIELD,ENTRYVALUE,ENTRYTYPE) VALUES";
            int counter, no_services = serviceList.size();
            long entryId;

            Process p;
            StringBuilder pQueryAppendRec = new StringBuilder();

            for (int i = 0; i < no_services; i++)
            {
                counter = 1000;
                pQueryAppendRec.setLength(0);
                serviceName = (((Map) serviceList.get(i)).get("ENTRYVALUE")).toString();
                consumerName = serviceName.replace("Service", "Consumer");

                p = Runtime.getRuntime().exec("grep -lR " + consumerName + " --include=*.java --exclude-dir=.svn --exclude-dir=finstudio/download /opt/apache-tomcat1/webapps/");

                try
                {
                    stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));

                    while ((aLine = stdInput.readLine()) != null)
                    {
                        // to skip line like you have mail

                        if (aLine.startsWith("/opt"))
                        {
                            counter = counter + 1;
                            entryId = System.nanoTime();

                            pQueryAppendRec.append("('").append(entryId).append("','ServiceName','")
                                    .append(serviceName).append("_Consumer").append(String.valueOf(counter).substring(2)).append("','consumer_").append(hostName).append("'),");

                            pQueryAppendRec.append("('").append(entryId).append("','ServiceUser','")
                                    .append(aLine.replace("/opt/apache-tomcat1/webapps/", "")).append("','consumer_").append(hostName).append("'),");
                        }
                    }
                }
                finally
                {
                    stdInput.close();
                }

                if (pQueryAppendRec.length() > 0)
                {
                    persistDataQuery(pQueryonAService + pQueryAppendRec.deleteCharAt(pQueryAppendRec.length() - 1).toString());
                }
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public static void persistDataQuery(String query)
    {
        Connection conn = null;
        try
        {
            SQLConnService sq = new SQLConnService();
            DirectConnection dc = new DirectConnection();
            conn = dc.getConnection("dev_db2_mysql");
            sq.persist(conn, query);
        }
        catch (Exception e)
        {
            Logger.ErrorLogger(e);
        }
        finally
        {
            try
            {
                conn.close();
            }
            catch (Exception e)
            {
                Logger.ErrorLogger(e);
            }

        }
    }

    public static List listDataQuery(String query)
    {
        Connection conn = null;
        try
        {
            SQLConnService sq = new SQLConnService();
            DirectConnection dc = new DirectConnection();
            conn = dc.getConnection("dev_db2_mysql");
            return sq.getList(conn, query);
        }
        catch (Exception e)
        {
            Logger.ErrorLogger(e);
        }
        finally
        {
            try
            {
                if(conn != null)
                    conn.close();
                else
                    throw new IOException("ConfurationAuditApp | Connection is null Error");
            }
            catch (Exception e)
            {
                Logger.ErrorLogger(e);
            }

        }
        return null;
    }
}