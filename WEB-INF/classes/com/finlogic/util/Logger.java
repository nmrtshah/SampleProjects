/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util;

import finutils.errorhandler.ErrorHandler;

/**
 *
 * @author njuser
 */
public final class Logger
{

    private Logger()
    {
    }

    public static void DataLogger(final String data)
    {
        ErrorHandler.PrintInLog("/opt/apache-tomcat1/webapps/finstudio/log/finstudio-data.txt", data);
    }

    public static long ErrorLogger(final Exception e)
    {
        long min;
        min = System.currentTimeMillis();
        ErrorHandler.PrintErrorInFile(e, "/opt/apache-tomcat1/webapps/finstudio/log/finstudio-error.txt", "Error No.=" + min + e.getMessage());
        return min;
    }

    public static void CommandLogger(final String data)
    {
        ErrorHandler.PrintInLog("/opt/apache-tomcat1/webapps/finstudio/log/finstudio-output.txt", data);
    }
}
