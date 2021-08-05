/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.findhtmlreport;

import com.finlogic.util.properties.HardCodeProperty;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author njuser
 */
public class FinDhtmlReportDirectConnection
{
    public Connection getConnection(final String servernm) throws ClassNotFoundException, SQLException
    {
        HardCodeProperty hcp = new HardCodeProperty();
        String drivernm = hcp.getProperty(servernm.concat("_driver"));
        String usernm = hcp.getProperty(servernm.concat("_user"));
        String passwd = hcp.getProperty(servernm.concat("_password"));
        String url = hcp.getProperty(servernm.concat("_url"));
        Class.forName(drivernm);
        return DriverManager.getConnection(url, usernm, passwd);

    }
}
