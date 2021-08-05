/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.findatareqexecutor;

import com.finlogic.util.properties.HardCodeProperty;

/**
 *
 * @author Sonam Patel
 */
/**
 *
 * @Edited by Jeegar Kumar Patel
 */
public class ServerPropertyReader
{

    public String getActualServer(final String serverID)
    {
        //Read Actual Server's Property And Get Appropriate Server ID For Server Name
        HardCodeProperty hcp;
        hcp = new HardCodeProperty();
        String serverStr = hcp.getProperty("actual_server");
        String[] servers = serverStr.split(";");
        String serverName = "";
        for (int i = 0; i < servers.length; i++)
        {
            if (servers[i].split(":")[0].equals(serverID))
            {
                serverName = servers[i].split(":")[1];
                break;
            }
        }
        return serverName;
    }

    public String getActualServerboth(final String serverID, final String server)
    {
        //Read Actual Server's Property And Get Appropriate Server ID For Server Name
        HardCodeProperty hcp;
        hcp = new HardCodeProperty();
        String serverStr = null;
        if(server.equalsIgnoreCase("Dev"))
        {
            serverStr = hcp.getProperty("dbdiff_dev_server");
        }
        else if(server.equalsIgnoreCase("Test"))
        {
            serverStr = hcp.getProperty("dbdiff_test_server");
        }
        else if(server.equalsIgnoreCase("Prod"))
        {
            serverStr = hcp.getProperty("actual_server");
        }
        String[] servers = serverStr.split(";");
        String serverName = "";
        for (int i = 0; i < servers.length; i++)
        {
            if (servers[i].split(":")[0].equals(serverID))
            {
                serverName = servers[i].split(":")[1];
                break;
            }
        }
        return serverName;
    }

    public String getVerificationServer(final String actualServerID, final String verifyServerType)
    {
        //Read Verify Syntax Server's Property And Get Appropriate Verify Server For Actual Server
        HardCodeProperty hcp;
        hcp = new HardCodeProperty();
        String serverStr = hcp.getProperty("verify_server");
        String[] serverTypes = serverStr.split(";");
        String verifyServer = "";
        for (int i = 0; i < serverTypes.length; i++)
        {
            if (serverTypes[i].split(":")[0].equals(actualServerID))
            {
                String verifyServerStr = serverTypes[i].split(":")[1];
                String[] allVerifyServers = verifyServerStr.split(",");
                for (int j = 0; j < allVerifyServers.length; j++)
                {
                    if (allVerifyServers[j].startsWith(verifyServerType))
                    {
                        verifyServer = allVerifyServers[j].replace('-', ':');
                        break;
                    }
                }
                if (!"".equals(verifyServer))
                {
                    break;
                }
            }
        }
        return verifyServer;
    }
}
