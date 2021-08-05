/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.business.finstudio.welcome;

import com.finlogic.util.persistence.SQLService;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author njuser
 */
public class welcomeDataManager
{
    SQLService sqlService = new SQLService();
    
    public List getStatistics() throws ClassNotFoundException, SQLException
    {
        StringBuilder SQL = new StringBuilder();
        SQL.append(" SELECT STATS_STRING  FROM FINSTUDIO_STATISTICS ");
        SQL.append(" WHERE UPPER(IS_ACTIVE) = 'Y' ");
        String aliasName = "finstudio_mysql";
        return sqlService.getList(aliasName, SQL.toString());
    }
}
