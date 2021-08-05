/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.business.finstudio.mobappsgeneration;

import java.sql.SQLException;
import org.springframework.jdbc.support.rowset.SqlRowSet;

/**
 *
 * @author njuser
 */
public class MobAppsManager
{

    private MobAppsDataManager dataMngr = new MobAppsDataManager();

    public String getVersion(final String appName) throws ClassNotFoundException, SQLException
    {
        SqlRowSet srs;
        srs = dataMngr.getVersion(appName);
        String version;
        version = "";
        while (srs.next())
        {
            version = srs.getString("APP_VERSION");
        }
        return version;
    }

    public int insertIntoDataBase(final MobAppsEntityBean entityean) throws ClassNotFoundException, SQLException
    {
        return dataMngr.insertIntoDataBase(entityean);
    }
}
