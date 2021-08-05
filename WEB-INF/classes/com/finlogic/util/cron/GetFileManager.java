/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.cron;

import com.finlogic.util.Logger;
import com.finlogic.util.persistence.SQLUtility;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.util.List;
import java.util.Map;

/**
 *
 * @author satish7
 */
public class GetFileManager
{

    private static final SQLUtility sqlUtility = new SQLUtility();
    private static final String FINALIAS = "finstudio_dbaudit_common";

    public static void main(String[] args)
    {
        try
        {
            org.apache.log4j.Logger.getRootLogger().setLevel(org.apache.log4j.Level.ERROR);
            String server = getServerName();
            String retrieveRecords = "";
            if (server.equals("prodhoweb3.nj"))
            {
                retrieveRecords = " SELECT SRNO, PATH, SERVER_NAME, PROCESS, COMMENT FROM FILEDIR_AUTO_REQUEST WHERE  LASTUPDATE >= DATE_SUB(NOW(),INTERVAL 1 HOUR) AND STATUS = 'done' AND COMMENT LIKE 'cache%'";
            }
            List list = sqlUtility.getList(FINALIAS, retrieveRecords);
            if (list.size() > 0)
            {
                String update_query_part1=" UPDATE FILEDIR_AUTO_REQUEST SET COMMENT = '";
                String update_query_part2="' WHERE SRNO = ";
                
                Map m = null;
                for (Object row : list)
                {
                    m.clear();
                    m = (Map) row;

                    int SRNO = (Integer) m.get("SRNO");

                    StringBuilder tempCachePath = new StringBuilder(finpack.FinPack.getProperty("tempfiles_path"));
                    String filePath = tempCachePath.append("cache/").append((String) m.get("COMMENT")).toString();

                    File file = new File(filePath);
                    String comment = null;
                    try
                    {
                        if (file.delete())
                        {
                            comment = "removed";
                        }            
                    }
                    catch (Exception ex)
                    {
                        comment = "error";
                        Logger.DataLogger("Error : " + ex.toString());
                    }
                    sqlUtility.persist(FINALIAS, update_query_part1 + comment + update_query_part2 + SRNO);
                }
            }
            else
            {
                
                    Logger.DataLogger("No data found.");
            }
        }
        catch (Exception e)
        {
            Logger.DataLogger("Error : " + e.toString());
            Logger.ErrorLogger(e);
        }
    }

    public static String getServerName() throws FileNotFoundException, IOException
    {

        String servername = InetAddress.getLocalHost().getHostName();

        if (servername == null)
        {
            return "NA";
        }
        servername = servername.trim().toLowerCase();
        if (!servername.contains(".nj"))
        {
            servername = servername + ".nj";
        }
        if (servername.equals("prodhoweb3.nj"))
        {
            return servername;
        }
        else
        {
            Logger.DataLogger("UnAuthorized server : " + servername);
            return "NA";
        }
    }

}
