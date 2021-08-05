/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.business.finstudio.oracletomysql;

import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.support.rowset.SqlRowSet;

/**
 *
 * @author njuser
 */
public class OracleToMysqlManager
{

    private OracleToMysqlDataManager dataManager = new OracleToMysqlDataManager();

    public int insert(OracleToMysqlEntityBean entityBean) throws ClassNotFoundException, SQLException
    {
        return dataManager.insert(entityBean);
    }

    public List getSchema(String server) throws ClassNotFoundException, SQLException
    {
        return dataManager.getSchema(server);
    }

    public List getItemList(String item, String owner, String server) throws ClassNotFoundException, SQLException
    {
        return dataManager.getItemList(item, owner, server);
    }

    public String getNewQuery(String item, String owner, String server, String itemNm) throws ClassNotFoundException, SQLException
    {
        return dataManager.getNewQuery(item, owner, server, itemNm);
    }

    public SqlRowSet getFieldName(String typeName, String server, String schema) throws ClassNotFoundException, SQLException
    {
        return dataManager.getFieldName(typeName, server, schema);
    }

    public SqlRowSet getAllTypeNames(String server, String schema) throws SQLException
    {
        return dataManager.getAllTypeNames(server, schema);
    }

    public int validateProc(String procLines, String server, String schema, String procName, String itemType) throws SQLException, ClassNotFoundException
    {
        return dataManager.validateProc(procLines, server, schema, procName, itemType);
    }
}
