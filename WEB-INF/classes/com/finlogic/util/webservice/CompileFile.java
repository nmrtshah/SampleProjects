/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.webservice;

import com.finlogic.apps.finstudio.webservice.WebServiceFormBean;
import com.finlogic.util.Logger;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Sonam Patel
 */
public class CompileFile
{

    public Map compile(final String filePath)
    {
        String output;
        output = WebServiceFormBean.getTOMCAT() + "/webapps/log/log/finstudio-ws-error.txt";
        Set set;
        set = new HashSet();
        Map<String, Set> map;
        map = new HashMap<String, Set>();
        boolean otherErr = false;
        int total = 0, cnt = 0;
        Runtime runTime;
        runTime = Runtime.getRuntime();
        Process proc = null;

        InputStreamReader isr = null;
        BufferedReader br = null;
        String line = null;
        InputStream stderr;
        FileWriter fw = null;

        try
        {
            fw = new FileWriter(output, true);
//            String[] cmd = new String[]
//            {
//                "/bin/bash", "-c", "cd " + filePath + ";javac -d " + WebServiceFormBean.getTOMCAT() + "/webapps/finstudio/WEB-INF/classes *.java"
//            };
            String[] cmd = new String[]
            {
                //"/bin/bash", "-c", "cd " + filePath + ";/usr/java/jdk1.6.0_13/bin/javac -cp /opt/apache-tomcat1/lib/*:. -d " + WebServiceFormBean.getTOMCAT() + "/webapps/finstudio/WEB-INF/classes *.java"
                "/bin/bash", "-c", "cd " + filePath + ";/usr/java/jdk1.6.0_13/bin/javac -cp /opt/apache-tomcat1/lib/*:. *.java"
            };

            proc = runTime.exec(cmd, null);
            proc.waitFor();

            fw.write("Command: " + cmd[2] + "\n\n");
            stderr = proc.getErrorStream();
            isr = new InputStreamReader(stderr);
            br = new BufferedReader(isr);
            StringBuilder strErr;
            strErr = new StringBuilder();
            String line1;

            fw.write("Error: ------------------------------------------------------\n");
            while ((line = br.readLine()) != null)
            {
                line1 = line.replace(" ", "&nbsp;");
                line1 = line1.replace("<", "&lt;");
                line1 = line1.replace(">", "&gt;");
                strErr.append(line1).append("<br/>");
                fw.write(line + "\n");

                while (line != null && line.contains("Service.java") && line.endsWith("cannot find symbol"))
                {
                    StringBuilder error = new StringBuilder();
                    while ((line = br.readLine()) != null && !line.contains("Service.java"))
                    {
                        error.append(line).append("\n");
                        if (line.startsWith("symbol  : "))
                        {
                            cnt++;
                            String temp[];
                            temp = line.split(":");
                            set.add(temp[temp.length - 1].trim());
                            //set.add(line.substring(10));
                        }
                        if (line.endsWith(" errors"))
                        {
                            total = Integer.parseInt(line.substring(0, line.length() - 7));
                        }
                        else if (line.endsWith(" error"))
                        {
                            total = Integer.parseInt(line.substring(0, line.length() - 6));
                        }
                    }
                    if (line != null)
                    {
                        error.append(line).append("\n");
                    }
                    fw.write(error + "\n");
                    String str = error.toString();
                    str = str.replace(" ", "&nbsp;");
                    str = str.replace("<", "&lt;");
                    str = str.replace(">", "&gt;");
                    str = str.replace("\n", "<br/>");
                    strErr.append(str);
                }
                if (line != null)
                {
                    if (line.contains("Service.java"))
                    {
                        otherErr = true;
                    }
                    if (line.endsWith(" errors"))
                    {
                        total = Integer.parseInt(line.substring(0, line.length() - 7));
                    }
                    else if (line.endsWith(" error"))
                    {
                        total = Integer.parseInt(line.substring(0, line.length() - 6));
                    }
                }
            }
            fw.write("-------------------------------------------------------------\n\n");

            if (cnt == total && !otherErr)
            {
                map.put("success", set);
            }
            else
            {
                Set errSet;
                errSet = new HashSet();
                errSet.add(strErr);
                map.put("failure", errSet);
            }
            return map;
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
                if (isr != null)
                {
                    isr.close();
                }
            }
            catch (Exception e)
            {
                Logger.DataLogger("Error " + Logger.ErrorLogger(e));
            }
            try
            {
                if (br != null)
                {
                    br.close();
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
        }
    }
}
