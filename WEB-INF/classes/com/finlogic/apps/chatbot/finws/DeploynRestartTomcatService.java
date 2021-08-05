/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.apps.chatbot.finws;

import com.finlogic.util.Logger;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Nitin Chauhan
 */
public class DeploynRestartTomcatService {
//    Logger logger = LogManager.getLogger(DeploynRestartTomcatService.class);

//    public static void main(String[] args) {
//        System.out.println("Start");
//
//        System.out.println("Done");
//    }
    public DeploynRestartTomcatRespBean deploynRestart(DeploynRestartTomcatReqBean reqBean) throws ClassNotFoundException, SQLException, IOException, InterruptedException, Exception {
        DeploynRestartTomcatRespBean respBean = new DeploynRestartTomcatRespBean();
        respBean.setId(reqBean.getId());
        DeploynRestartTomcatService service = new DeploynRestartTomcatService();
        DeploynRestartTomcatDataManager dataManager = new DeploynRestartTomcatDataManager();
        String selfServerName = service.getSelfServerName();
        List lstTomcat = dataManager.getTomcatListByServer(selfServerName);
        String jarpath = reqBean.getFilePath();
        for (int i = 0; i < lstTomcat.size(); i++) {
            Map mapTemp = (Map) lstTomcat.get(i);
            Logger.DataLogger("Processing Tomcat:" + mapTemp.get("TOMCAT_NAME"));
            Logger.DataLogger(selfServerName + " TomcatLoop :" + mapTemp.get("TOMCAT_NAME"));
            String serverJarPath = "/opt/" + mapTemp.get("TOMCAT_NAME").toString() + "/lib/finws.jar";
            Logger.DataLogger("copying from :" + jarpath + " To :" + serverJarPath);
            service.terminalCommand("mv " + serverJarPath + " " + serverJarPath + ".backup_" + new SimpleDateFormat("dd_MM_yyyy").format(Calendar.getInstance().getTime()) + System.currentTimeMillis(), false);
            Files.copy(Paths.get(jarpath), Paths.get(serverJarPath), StandardCopyOption.REPLACE_EXISTING);

//            String initCommand = mapTemp.get("SERVER_INIT_CMD").toString() +"restart " + mapTemp.get("TOMCAT_NAME");
         
/*Forwarded to CRON
        if (selfServerName.equalsIgnoreCase("devhoweb3.nj")) {

                service.terminalCommand("chown tomcat:tomcat " + serverJarPath, false);
            } else {

                service.terminalCommand("chown tomcat:root " + serverJarPath, false);
            }
            service.terminalCommand("chmod 775 " + serverJarPath, false);
*/
//            Logger.DataLogger("Restart command :" + initCommand);
//            Logger.DataLogger("Restarting tomcat" + mapTemp.get("TOMCAT_NAME") + " on Server :" + selfServerName);
//            service.terminalCommand(initCommand, false);
            DeploynRestartTomcatUtil util = new DeploynRestartTomcatUtil();
            util.writeRestartFile("/opt/application_storage/temp_files/chatbot/finws/restart/restart.txt", mapTemp.get("TOMCAT_NAME").toString());
        }
//        service.terminalCommand("/home/namrata/restart-alltomcat.sh", false);
//        Logger.DataLogger("Restarting all tomcats on Server :" + selfServerName);
//        service.terminalCommand("/home/namrata/restart-alltomcat.sh", false);

        return respBean;
    }

    public String getSelfServerName() throws UnknownHostException {
        String serverName;
//        serverName = finpack.FinPack.getProperty("java_server_name");
        serverName = InetAddress.getLocalHost().getHostName();
        Logger.DataLogger("CHATBOT_SELFSERVERNAME :" + serverName);
        System.out.println("CHATBOT_SELFSERVERNAME :" + serverName);
        return serverName;
    }

    public String terminalCommand(String command, boolean multiFlag) throws IOException, InterruptedException, Exception {
        Logger.DataLogger("COMMAND TERMINAL :" + command);
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
            Logger.DataLogger("TERMINAL OP :" + strResult.toString());
            return strResult.toString();
        } else {
            Logger.DataLogger("TERMINAL OP ERROR :" + strErrResult.toString());
            throw new Exception("Terminal Exception :" + strErrResult);
        }

    }

    public String convFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return "";
    }

}
