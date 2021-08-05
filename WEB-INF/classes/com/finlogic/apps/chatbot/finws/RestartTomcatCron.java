/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.apps.chatbot.finws;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * @author Nitin Chauhan
 */

public class RestartTomcatCron {
    public static void main(String[] args) throws Exception {
        RestartTomcatCron service = new RestartTomcatCron();
        String fileName= "/opt/application_storage/temp_files/chatbot/finws/restart/restart.txt";
        File f= new File(fileName);
        String serverJarPath = "/opt/tomcat10/lib/finws.jar";
        if(!f.exists()){
            throw new Exception("Restart file doesn't exist");
        }
         String selfServerName = service.getSelfServerName();
        if (selfServerName.equalsIgnoreCase("devhoweb3.nj")) {

                service.terminalCommand("chown tomcat:tomcat " + serverJarPath, false);
            } else {

                service.terminalCommand("chown tomcat:root " + serverJarPath, false);
            }
            service.terminalCommand("chmod 775 " + serverJarPath, false);
        
//        terminalCommand("sh /opt/application_storage/permanent_files/chatbot/customRestartScript.sh /opt/application_storage/temp_files/chatbot/finws/restart/restart.txt", false);
        terminalCommand("sh /cron_script/customRestartScript.sh /opt/application_storage/temp_files/chatbot/finws/restart/restart.txt", false);
    }
    public static String terminalCommand(String command, boolean multiFlag) throws IOException, InterruptedException, Exception {
        Process p;
        if (multiFlag) {

            p = Runtime.getRuntime().exec(new String[]{"bash", "-c", command});
        } else {

            p = Runtime.getRuntime().exec(command);
        }
        BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
        BufferedReader inErr = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        String ret, Err;

        StringBuilder strResult = new StringBuilder();
        strResult.setLength(0);
        StringBuilder strErrResult = new StringBuilder();
        strErrResult.setLength(0);
        while ((ret = in.readLine()) != null) {
            strResult.append(ret);
        }
        while ((Err = inErr.readLine()) != null) {
            strErrResult.append(Err);
        }
        p.waitFor();
        if (strErrResult.length() == 0) {
            return strResult.toString();
        } else {
            throw new Exception("Terminal Exception :" + strErrResult);
        }

    }
    public String getSelfServerName() throws UnknownHostException {
        String serverName;
//        serverName = finpack.FinPack.getProperty("java_server_name");
        serverName = InetAddress.getLocalHost().getHostName();
        System.out.println("CHATBOT_SELFSERVERNAME :" + serverName);
        return serverName;
    }
    
}
