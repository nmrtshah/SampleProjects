/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.business.finstudio.mobappsgeneration;

import com.finlogic.util.persistence.SQLService;
import java.sql.SQLException;
import java.util.HashMap;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;

/**
 *
 * @author njuser
 */
public class MobAppsDataManager
{

    private static SQLService sqlService = new SQLService();
    private static final String ALIASNAME = "finstudio_mysql";

    public SqlRowSet getVersion(final String appName) throws ClassNotFoundException, SQLException
    {
        MapSqlParameterSource param;
        HashMap hmap;
        hmap = new HashMap();
        hmap.put("appName", appName);
        param = new MapSqlParameterSource(hmap);
        String query;
        query = "SELECT IFNULL(MAX(APP_VERSION) + 0.1, '1.0') APP_VERSION FROM MOBAPPS_MAIN WHERE APP_NAME = :appName";
        return sqlService.getRowSet(ALIASNAME, query, param);
    }

    public int insertIntoDataBase(final MobAppsEntityBean entityBean) throws ClassNotFoundException, SQLException
    {
        SqlParameterSource sps;
        sps = new BeanPropertySqlParameterSource(entityBean);
        KeyHolder keys;
        keys = new GeneratedKeyHolder();
        int srno = -99;

        //MOBAPPS_MAIN Entry
        String query;
        query = "INSERT INTO MOBAPPS_MAIN(APP_NAME,APP_VERSION,PACKAGE,WELCOME_FILE,TARGET,EMP_CODE) VALUES(:appName,:appVersion,:appPackage,:welcomeFile,:target,:empCode)";
        sqlService.persist(ALIASNAME, query, keys, sps);

        //return SRNO to make zip product
        srno = sqlService.getInt(ALIASNAME, "SELECT SRNO FROM MOBAPPS_MAIN ORDER BY SRNO DESC LIMIT 1");
        return srno;
    }
}
