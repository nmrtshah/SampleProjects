/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.findatareqexecutor;

import com.finlogic.util.properties.HardCodeProperty;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Sonam Patel
 */
public final class ServerConnection
{

    private ServerConnection()
    {
    }

    public static Connection getConnection(final String server) throws ClassNotFoundException, SQLException
    {
        HardCodeProperty hcp;
        hcp = new HardCodeProperty();
        String drivernm;
        drivernm = hcp.getProperty(server.concat("_driver"));
        String usernm;
        usernm = hcp.getProperty(server.concat("_user"));
        String passwd;
        passwd = hcp.getProperty(server.concat("_password"));
        String url;
        url = hcp.getProperty(server.concat("_url"));
        Class.forName(drivernm);
        return DriverManager.getConnection(url, usernm, passwd);
    }
}
