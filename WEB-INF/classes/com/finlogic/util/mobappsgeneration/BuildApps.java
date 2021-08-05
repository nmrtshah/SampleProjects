/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.mobappsgeneration;

import com.finlogic.util.Logger;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 *
 * @author njuser
 */
public class BuildApps
{

    public boolean compileApp(final List comm, final int srno)
    {
        boolean next = true;
        Runtime runTime;
        runTime = Runtime.getRuntime();
        Process proc = null;

        InputStream stdout;
        InputStreamReader isr = null;
        BufferedReader br = null;
        String line = null;


        InputStream stderr;
        InputStreamReader isr1 = null;
        BufferedReader br1 = null;
        String line1 = null;

        try
        {
            int i;
            for (i = 0; i < comm.size(); i++)
            {
                if (next)
                {
                    String[] cmd = new String[]
                    {
                        "/bin/bash", "-c", comm.get(i).toString()
                    };
                    proc = runTime.exec(cmd, null);

                    stdout = proc.getInputStream();

                    isr = new InputStreamReader(stdout);
                    br = new BufferedReader(isr);
                    proc.waitFor();

                    Logger.CommandLogger("Command" + srno + ": " + cmd[2] + "\n");
                    Logger.CommandLogger("OutPut" + srno + ": ------------------------------------------------------------------------------------------\n");
                    while ((line = br.readLine()) != null)
                    {
                        Logger.CommandLogger(line);
                        next = true;
                    }
                    Logger.CommandLogger("---------------------------------------------------------------------------------------------------\n");

                    stderr = proc.getErrorStream();
                    isr1 = new InputStreamReader(stderr);
                    br1 = new BufferedReader(isr1);
                    Logger.CommandLogger("Error" + srno + ": -------------------------------------------------------------------------------------------\n");
                    while ((line1 = br1.readLine()) != null)
                    {
                        Logger.CommandLogger(line1);
                        next = false;
                    }
                    Logger.CommandLogger("---------------------------------------------------------------------------------------------------\n");
                }
                else
                {
                    break;
                }
            }
            if (i == comm.size() && next)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        catch (Exception e)
        {
            Logger.ErrorLogger(e);
            return false;
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
                if (isr1 != null)
                {
                    isr1.close();
                }
            }
            catch (Exception e)
            {
                Logger.DataLogger("Error " + Logger.ErrorLogger(e));
            }
            try
            {
                if (br1 != null)
                {
                    br1.close();
                }
            }
            catch (Exception e)
            {
                Logger.DataLogger("Error " + Logger.ErrorLogger(e));
            }
        }
    }
}
