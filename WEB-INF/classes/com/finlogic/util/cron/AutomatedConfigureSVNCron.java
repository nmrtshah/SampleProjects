/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.cron;

import com.finlogic.util.Logger;
import com.finlogic.util.persistence.SQLUtility;
import java.io.IOException;
import java.net.InetAddress;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author njuser
 */
public class AutomatedConfigureSVNCron {

    private static final SQLUtility sqlUtility = new SQLUtility();
    private static final String FINALIAS = "finstudio_dbaudit_common";

    public static void main(String[] args) {
        try {
            org.apache.log4j.Logger.getRootLogger().setLevel(org.apache.log4j.Level.ERROR);

            String server = InetAddress.getLocalHost().getHostName();

            String serverName = server.substring(3, server.indexOf("."));
            System.out.println("server name : " + serverName);
            String authRecords = " SELECT SRNO, PROJECT_NAME, APP_SERVER_NAME,TOMCAT,TOMCAT_STATUS FROM SVN_INFO WHERE STATUS = 'created' and APP_SERVER_NAME like '%" + serverName + "%'";

            System.out.println("query - " + authRecords);
            List list = sqlUtility.getList(FINALIAS, authRecords);

            if (list.size() > 0) {
                for (Object row : list) {
                    Map m = (Map) row;

                    int srNo = (Integer) m.get("SRNO");
                    String projectName = (String) m.get("PROJECT_NAME");
                    String appServerName = (String) m.get("APP_SERVER_NAME");
                    String tomcatName = (String) m.get("TOMCAT");
                    String tomcatStatus = (String) m.get("TOMCAT_STATUS");

                    System.out.println("SR NO : " + srNo);
                    System.out.println("project name : " + projectName);
                    System.out.println("server name : " + appServerName);
                    System.out.println("tomcat name : " + tomcatName);
                    System.out.println("tomcat status : " + tomcatStatus);

                    if (tomcatStatus != null && !tomcatStatus.equalsIgnoreCase("")
                            && tomcatStatus.equals("existing")) {

                        if (server.contains("devhoweb") || server.contains("devgroupemail")) {
                            configureSVNDev(m);
                        } else if (server.contains("testhoweb")
                                || server.contains("prodhoweb")) {
                            configureSVNTestProd(m);
                        }
                    } else if (tomcatStatus != null && !tomcatStatus.equalsIgnoreCase("")
                            && tomcatStatus.equals("new")) {

                        if (server.contains("devhoweb") || server.contains("devgroupemail")) {
                            configureSVNDevNewTomcat(m);
                        } else if (server.contains("testhoweb")
                                || server.contains("prodhoweb")) {
                            configureSVNTestProdNewTomcat(m);
                        }
                    }
                }
            } else {
                if (args != null && args.length > 0 && args[0].equals("debug")) {
                    Logger.DataLogger("No data found.");
                }
            }
        } catch (Exception e) {
            Logger.DataLogger("Error : " + e.toString());
            Logger.ErrorLogger(e);
        }
    }

    public static void configureSVNDev(Map m) throws ClassNotFoundException, SQLException, IOException {
        int SRNO = (Integer) m.get("SRNO");
        String projectName = (String) m.get("PROJECT_NAME");
        String appServerName = (String) m.get("APP_SERVER_NAME");
        String tomcatName = (String) m.get("TOMCAT");

        Process p;

        System.out.println("appservername : " + appServerName);
        Logger.DataLogger("SR NO : " + SRNO);
        Logger.DataLogger("Project Name : " + projectName);

        String serverName = StringUtils.substring(appServerName, 0, 7);
        System.out.println("app server name:" + serverName);
        System.out.println("tomcat name : " + tomcatName);

        try {

            //      String[] cmd = {"bash", "-c", "sed -i '/#svn_check_out_file/asvn_check_out file:///svnrepos/t_" + projectName + "/" + projectName + " /opt/apache-tomcat1/webapps/" + projectName + " /cron_script/svn_checkout_" + serverName + ".sh"};
            String[] cmd = {"sed", "/#svn_check_out_file/asvn_check_out file:///svnrepos/t_" + projectName + "/" + projectName + " /opt/apache-tomcat1/webapps/" + projectName + " /cron_script/svn_checkout_" + serverName + ".sh"};
            p = Runtime.getRuntime().exec(cmd);

            Thread.sleep(180000);

            p = Runtime.getRuntime().exec("ln -s /opt/apache-tomcat1/webapps/" + projectName + " /opt/" + tomcatName + "/webapps/" + projectName + "");

            System.out.println("ln -s /opt/apache-tomcat1/webapps/" + projectName + " /opt/" + tomcatName + "/webapps/" + projectName + "");

        } catch (Exception e) {
            Logger.DataLogger("Error : " + e.toString());
            Logger.ErrorLogger(e);
        }

    }

    public static void configureSVNTestProd(Map m) throws ClassNotFoundException, SQLException, IOException {
        int SRNO = (Integer) m.get("SRNO");
        String projectName = (String) m.get("PROJECT_NAME");
        String appServerName = (String) m.get("APP_SERVER_NAME");
        String tomcatName = (String) m.get("TOMCAT");

        Boolean svnConfig = false;

        Process p;

        System.out.println("appservername : " + appServerName);
        Logger.DataLogger("SR NO : " + SRNO);
        Logger.DataLogger("Project Name : " + projectName);

        try {

            p = Runtime.getRuntime().exec("mkdir /opt/apache-tomcat1/webapps/" + projectName + "");

            Thread.sleep(5000);

            p = Runtime.getRuntime().exec("ln -s /opt/apache-tomcat1/webapps/" + projectName + " /opt/" + tomcatName + "/webapps/" + projectName + "");
            svnConfig = true;
        } catch (Exception e) {
            Logger.DataLogger("Error : " + e.toString());
            Logger.ErrorLogger(e);
        }
        if (svnConfig) {
            sqlUtility.persist(FINALIAS, " UPDATE SVN_INFO SET STATUS = 'done' WHERE SRNO = " + SRNO);
        }

    }

    public static void configureSVNDevNewTomcat(Map m) throws ClassNotFoundException, SQLException, IOException {
        int SRNO = (Integer) m.get("SRNO");
        String projectName = (String) m.get("PROJECT_NAME");
        String appServerName = (String) m.get("APP_SERVER_NAME");
        String tomcatName = (String) m.get("TOMCAT");

        Process p;

        System.out.println("appservername : " + appServerName);
        Logger.DataLogger("SR NO : " + SRNO);
        Logger.DataLogger("Project Name : " + projectName);

        String serverName = StringUtils.substring(appServerName, 0, 7);
        System.out.println("app server name:" + serverName);
        System.out.println("tomcat name : " + tomcatName);

        try {

            p = Runtime.getRuntime().exec("cp -r /home/satish/tomcat000/ /opt/" + tomcatName + "");

            Thread.sleep(8000);

            String twoDigit = tomcatName.substring(Math.max(tomcatName.length() - 2, 0));

            System.out.println("last two digit is = " + twoDigit);
            int shutDwnPortNo = 8300;

            shutDwnPortNo += Integer.parseInt(twoDigit);

            DecimalFormat format = new DecimalFormat("0.#");
            String[] cmdArray1 = {"sed", "s:8300:" + format.format(shutDwnPortNo) + ":g", "-i", "/opt/" + tomcatName + "/conf/server.xml"};

            int cntrPortNo = 8080;
            cntrPortNo += Integer.parseInt(twoDigit);
            format = new DecimalFormat("0.#");
            String[] cmdArray2 = {"sed", "s:8080:" + format.format(cntrPortNo) + ":g", "-i", "/opt/" + tomcatName + "/conf/server.xml"};

            int cntrRdPortNo = 8400;
            cntrRdPortNo += Integer.parseInt(twoDigit);
            format = new DecimalFormat("0.#");
            String[] cmdArray3 = {"sed", "s:8400:" + format.format(cntrRdPortNo) + ":g", "-i", "/opt/" + tomcatName + "/conf/server.xml"};

            int cntrAJPPortNo = 8200;
            cntrAJPPortNo += Integer.parseInt(twoDigit);
            format = new DecimalFormat("0.#");
            String[] cmdArray4 = {"sed", "s:8200:" + format.format(cntrAJPPortNo) + ":g", "-i", "/opt/" + tomcatName + "/conf/server.xml"};

            p = Runtime.getRuntime().exec(cmdArray1);
            Thread.sleep(2000);

            p = Runtime.getRuntime().exec(cmdArray2);
            Thread.sleep(2000);

            p = Runtime.getRuntime().exec(cmdArray3);
            Thread.sleep(2000);

            p = Runtime.getRuntime().exec(cmdArray4);
            Thread.sleep(2000);

            p = Runtime.getRuntime().exec("cp -r /home/satish/tomcat00 /etc/init.d/" + tomcatName + "");
            Thread.sleep(2000);

            String[] cmdArray5 = {"sed", "s:/opt/tomcat00:/opt/" + tomcatName + ":g", "-i", "/etc/init.d/" + tomcatName + ""};
            p = Runtime.getRuntime().exec(cmdArray5);

            //      String[] cmd = {"bash", "-c", "sed -i '/#svn_check_out_file/asvn_check_out file:///svnrepos/t_" + projectName + "/" + projectName + " /opt/apache-tomcat1/webapps/" + projectName + " /cron_script/svn_checkout_" + serverName + ".sh"};
            String[] cmd = {"sed", "/#svn_check_out_file/asvn_check_out file:///svnrepos/t_" + projectName + "/" + projectName + " /opt/apache-tomcat1/webapps/" + projectName + " /cron_script/svn_checkout_" + serverName + ".sh"};
            p = Runtime.getRuntime().exec(cmd);

            Thread.sleep(190000);

            p = Runtime.getRuntime().exec("ln -s /opt/apache-tomcat1/webapps/" + projectName + " /opt/" + tomcatName + "/webapps/" + projectName + "");

        } catch (Exception e) {
            Logger.DataLogger("Error : " + e.toString());
            Logger.ErrorLogger(e);
        }

    }

    public static void configureSVNTestProdNewTomcat(Map m) throws ClassNotFoundException, SQLException, IOException {
        int SRNO = (Integer) m.get("SRNO");
        String projectName = (String) m.get("PROJECT_NAME");
        String appServerName = (String) m.get("APP_SERVER_NAME");
        String tomcatName = (String) m.get("TOMCAT");

        Boolean svnConfig = false;

        Process p;

        System.out.println("appservername : " + appServerName);
        Logger.DataLogger("SR NO : " + SRNO);
        Logger.DataLogger("Project Name : " + projectName);

        try {

            p = Runtime.getRuntime().exec("cp -r /home/satish/tomcat000/ /opt/" + tomcatName + "");

            Thread.sleep(8000);

            String twoDigit = tomcatName.substring(Math.max(tomcatName.length() - 2, 0));

            System.out.println("last two digit is = " + twoDigit);
            int shutDwnPortNo = 8300;

            shutDwnPortNo += Integer.parseInt(twoDigit);

            DecimalFormat format = new DecimalFormat("0.#");
            String[] cmdArray1 = {"sed", "s:8300:" + format.format(shutDwnPortNo) + ":g", "-i", "/opt/" + tomcatName + "/conf/server.xml"};

            int cntrPortNo = 8080;
            cntrPortNo += Integer.parseInt(twoDigit);
            format = new DecimalFormat("0.#");
            String[] cmdArray2 = {"sed", "s:8080:" + format.format(cntrPortNo) + ":g", "-i", "/opt/" + tomcatName + "/conf/server.xml"};

            int cntrRdPortNo = 8400;
            cntrRdPortNo += Integer.parseInt(twoDigit);
            format = new DecimalFormat("0.#");
            String[] cmdArray3 = {"sed", "s:8400:" + format.format(cntrRdPortNo) + ":g", "-i", "/opt/" + tomcatName + "/conf/server.xml"};

            int cntrAJPPortNo = 8200;
            cntrAJPPortNo += Integer.parseInt(twoDigit);
            format = new DecimalFormat("0.#");
            String[] cmdArray4 = {"sed", "s:8200:" + format.format(cntrAJPPortNo) + ":g", "-i", "/opt/" + tomcatName + "/conf/server.xml"};

            p = Runtime.getRuntime().exec(cmdArray1);
            Thread.sleep(2000);

            p = Runtime.getRuntime().exec(cmdArray2);
            Thread.sleep(2000);

            p = Runtime.getRuntime().exec(cmdArray3);
            Thread.sleep(2000);

            p = Runtime.getRuntime().exec(cmdArray4);
            Thread.sleep(2000);

            p = Runtime.getRuntime().exec("cp -r /home/satish/tomcat00 /etc/init.d/" + tomcatName + "");
            Thread.sleep(2000);

            
            String[] cmdArray5 = {"sed", "s:/opt/tomcat00:/opt/" + tomcatName + ":g", "-i", "/etc/init.d/" + tomcatName + ""};
            p = Runtime.getRuntime().exec(cmdArray5);

            p = Runtime.getRuntime().exec("mkdir /opt/apache-tomcat1/webapps/" + projectName + "");

            Thread.sleep(5000);

            p = Runtime.getRuntime().exec("ln -s /opt/apache-tomcat1/webapps/" + projectName + " /opt/" + tomcatName + "/webapps/" + projectName + "");
            svnConfig = true;
        } catch (Exception e) {
            Logger.DataLogger("Error : " + e.toString());
            Logger.ErrorLogger(e);
        }

        if (svnConfig) {
            sqlUtility.persist(FINALIAS, " UPDATE SVN_INFO SET STATUS = 'done',TOMCAT_STATUS = 'existing'  WHERE SRNO = " + SRNO);
        }

    }

}
