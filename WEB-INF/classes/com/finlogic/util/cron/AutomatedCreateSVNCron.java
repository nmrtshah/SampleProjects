/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.cron;

import com.finlogic.util.Logger;
import com.finlogic.util.persistence.SQLUtility;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author njuser
 */
public class AutomatedCreateSVNCron {

    private static final SQLUtility sqlUtility = new SQLUtility();
    private static final String FINALIAS = "finstudio_dbaudit_common";

    public static void main(String[] args) {
        try {
            org.apache.log4j.Logger.getRootLogger().setLevel(org.apache.log4j.Level.ERROR);

            String authRecords = " SELECT SRNO, PROJECT_NAME, APP_SERVER_NAME, TOMCAT_STATUS FROM SVN_INFO WHERE STATUS = 'authorized'";

            //System.out.println("query - " + authRecords);
            List list = sqlUtility.getList(FINALIAS, authRecords);

            if (list.size() > 0) {
                for (Object row : list) {
                    Map m = (Map) row;

                    int srNo = (Integer) m.get("SRNO");
                    String projectName = (String) m.get("PROJECT_NAME");
                    String appServerName = (String) m.get("APP_SERVER_NAME");
                    String tomcatStatus = (String) m.get("TOMCAT_STATUS");

                    //System.out.println("SR NO : " + srNo);
                    //System.out.println("project name : " + projectName);
                    //System.out.println("server name : " + appServerName);
                    //System.out.println("tomcat status : " + tomcatStatus);

                    if (projectName != null && !projectName.equalsIgnoreCase("")) {
                        createSVN(m);
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

//        try {
//            org.apache.log4j.Logger.getRootLogger().setLevel(org.apache.log4j.Level.ERROR);
//
//            String authRecords = " select tomcat from TOMCAT_MASTER order by  srno desc limit 1";
//
//            System.out.println("query - " + authRecords);
//            String record = sqlUtility.getString(FINALIAS, authRecords);
//             String serverName = StringUtils.substring(record, 6, 15);
//            int s= (Integer.parseInt(serverName)) + 1;
//            System.out.println("record is - "+s);
//        } catch (Exception e) {
//            Logger.DataLogger("Error : " + e.toString());
//            Logger.ErrorLogger(e);
//        }
    }
//    
//    public static void createSVN(Map m) throws ClassNotFoundException, SQLException, IOException {
//        int SRNO = (Integer) m.get("SRNO");
//        String projectName = (String) m.get("PROJECT_NAME");
//        System.out.println("project names are - "+projectName);
//        Boolean svnCreated = false;
//        String pname = "P44";
//         String url = null;
//       String host="utilsvnmaster.nj";
//	    String user="root";
//	    String password="Svn@#123%";
//	    String command1="ls -ltr;cd ..;cd /home/namrata;cp -r sample_new_project "+projectName+";sh svn-script_bkup.sh "+projectName;
//            //String command1="cp -r /home/namrata/sample_new_project /home/namrata/"+projectName+";sh /home/namrata/svn-script_bkup.sh "+projectName;
//            //String command2="sh /home/namrata/svn-script_bkup.sh "+projectName;
//
//            
//            
//            System.out.println("command1 = "+command1);
//	    try{
//	    	
//	    	java.util.Properties config = new java.util.Properties(); 
//	    	config.put("StrictHostKeyChecking", "no");
//	    	JSch jsch = new JSch();
//	    	Session session=jsch.getSession(user, host, 22);
//	    	session.setPassword(password);
//	    	session.setConfig(config);
//	    	session.connect();
//	    	System.out.println("Connected");
//	    	
//	    	com.jcraft.jsch.Channel channel=session.openChannel("exec");
//                ((ChannelExec)channel).setCommand(command1);
//	        channel.setInputStream(null);
//	        ((ChannelExec)channel).setErrStream(System.err);
//	        
//	        InputStream in=channel.getInputStream();
//	        channel.connect();
//	        byte[] tmp=new byte[1024];
//	        while(true){
//	          while(in.available()>0){
//	            int i=in.read(tmp, 0, 1024);
//	            if(i<0)break;
//	            System.out.print(new String(tmp, 0, i));
//                    if(new String(tmp, 0, i).contains("WEB-INF"))
//                    {
//                         System.out.println("data matched");
//                          url = "http://svn.njtechdesk.com/dev_t_"+projectName;
//                          
//                          svnCreated = true;
//                    }
//                   
//	          }
//	          if(channel.isClosed()){
//	            System.out.println("exit-status: "+channel.getExitStatus());
//	            break;
//	          }
//	          try{Thread.sleep(1000);}catch(Exception ee){}
//	        }
//	        channel.disconnect();
//	        session.disconnect();
//	        System.out.println("DONE");
//	    }catch(Exception e){
//	    	e.printStackTrace();
//	    }
//        System.out.println("flag value = "+svnCreated);
//        System.out.println("URL IS = "+url);
//        System.out.println("SRNO IS = "+SRNO);
//        if(svnCreated)
//        {
//            System.out.println("--query is updated---");
//              sqlUtility.persist(FINALIAS, " UPDATE SVN_INFO SET STATUS = 'created', CHECKOUT_URL = '" + url + "' WHERE SRNO = " + SRNO);
//               System.out.println("query is updated");
//        }
//
////        if (PATH.trim().matches(Decimalpattern) && new File(existPath).exists()) {
////            Logger.DataLogger("srno : " + SRNO);
////            Logger.DataLogger("path : " + PATH);
////
////            File f = new File(PATH);
////
////            if (f.exists()) {
////                sqlUtility.persist(FINALIAS, " UPDATE FILEDIR_AUTO_REQUEST SET STATUS = 'done', COMMENT = 'already exist' WHERE SRNO = " + SRNO);
////                Logger.DataLogger("Folder already Exist : " + PATH);
////            } else {
////                f.mkdirs();
////                Runtime.getRuntime().exec("/bin/chmod 777 -R " + f.getAbsolutePath());
////                sqlUtility.persist(FINALIAS, " UPDATE FILEDIR_AUTO_REQUEST SET STATUS = 'done', COMMENT = 'created' WHERE SRNO = " + SRNO);
////                Logger.DataLogger("created : " + PATH);
////            }
////        } else {
////            sqlUtility.persist(FINALIAS, " UPDATE FILEDIR_AUTO_REQUEST SET STATUS = 'done', COMMENT = 'invalid path' WHERE SRNO = " + SRNO);
////            Logger.DataLogger("Path is invalid : " + PATH);
////        }
//    }
//    
    

    public static void createSVN(Map m) throws ClassNotFoundException, SQLException, IOException {
        int SRNO = (Integer) m.get("SRNO");
        String projectName = (String) m.get("PROJECT_NAME");
        Boolean svnCreated = false;
        String op;
        Process p;
        String url = "";

        Logger.DataLogger("SR NO : " + SRNO);
        Logger.DataLogger("Project Name : " + projectName);

        try {
            p = Runtime.getRuntime().exec("cp -r /home/namrata/sample_new_project /home/namrata/" + projectName);
            p = Runtime.getRuntime().exec("sh /home/namrata/svn-script_bkup.sh " + projectName + " " + projectName + " " + projectName);

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(p.getInputStream()));
            while ((op = br.readLine()) != null) {
                System.out.println("line: " + op);

                if (op.contains("WEB-INF")) {
                    url = "http://svn.njtechdesk.com/dev_t_" + projectName;
                    svnCreated = true;
                }
            }
            p.waitFor();
            System.out.println("exit: " + p.exitValue());
            p.destroy();

        } catch (Exception e) {
            Logger.DataLogger("Error : " + e.toString());
            Logger.ErrorLogger(e);
        }
        if (svnCreated) {
            sqlUtility.persist(FINALIAS, " UPDATE SVN_INFO SET STATUS = 'created', CHECKOUT_URL = '" + url + "' WHERE SRNO = " + SRNO);
        }
    }

}
