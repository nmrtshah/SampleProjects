/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.business.finstudio.webservice;

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
 * @author Sonam Patel
 */
public class WebServiceDataManager
{

    private static final String ALIASNAME = "finstudio_mysql";
    private static SQLService sqlService = new SQLService();

    public SqlRowSet getProjectArray() throws ClassNotFoundException, SQLException
    {
        String query;
        query = "SELECT PRJ_ID, PRJ_NAME, DOMAIN_NAME FROM PROJECT_MST WHERE DOMAIN_NAME IS NOT NULL";
        return sqlService.getRowSet("wfm", query);
    }

    public int insertIntoDataBase(final WebServiceEntityBean entityBean) throws ClassNotFoundException, SQLException
    {
        SqlParameterSource sps;
        sps = new BeanPropertySqlParameterSource(entityBean);
        KeyHolder keys;
        keys = new GeneratedKeyHolder();

        //WEBSRVC_MAIN Entry
        String mst_main_query;
        if (entityBean.getRequestNo().equals(""))
        {
            mst_main_query = "INSERT INTO WEBSRVC_MAIN(PROJECT_NAME, MODULE_NAME, INTERFACE_NAME, EMP_CODE, ON_DATE)"
                    + "VALUES(:projectName, :moduleName, :interfaceName, :empCode, SYSDATE())";
        }
        else
        {
            mst_main_query = "INSERT INTO WEBSRVC_MAIN(PROJECT_NAME, MODULE_NAME, INTERFACE_NAME, REQUEST_NO, EMP_CODE, ON_DATE)"
                    + "VALUES(:projectName, :moduleName, :interfaceName, :requestNo, :empCode, SYSDATE())";
        }
        sqlService.persist(ALIASNAME, mst_main_query, keys, sps);

        //return SRNO
        return sqlService.getInt(ALIASNAME, "SELECT SRNO FROM WEBSRVC_MAIN ORDER BY SRNO DESC LIMIT 1");
    }

    public SqlRowSet fetchRecord(final String srno) throws ClassNotFoundException, SQLException
    {
        MapSqlParameterSource param;
        HashMap hmap;
        hmap = new HashMap();
        hmap.put("srno", srno);
        param = new MapSqlParameterSource(hmap);
        String query;
        query = "SELECT SRNO, PROJECT_NAME, MODULE_NAME, INTERFACE_NAME FROM WEBSRVC_MAIN WHERE SRNO = :srno";
        return sqlService.getRowSet(ALIASNAME, query, param);
    }

    public void deleteRecord(final String srno) throws ClassNotFoundException, SQLException
    {
        MapSqlParameterSource param;
        HashMap hmap;
        hmap = new HashMap();
        hmap.put("srno", srno);
        param = new MapSqlParameterSource(hmap);
        String query;
        query = "DELETE FROM WEBSRVC_MAIN WHERE SRNO = :srno";
        sqlService.persist(ALIASNAME, query, param);
    }
}
