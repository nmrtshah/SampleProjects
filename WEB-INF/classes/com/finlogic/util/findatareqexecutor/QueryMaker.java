/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.findatareqexecutor;

import com.finlogic.util.Logger;
import com.finlogic.util.persistence.SQLTranUtility;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Sonam Patel
 *
 */
public class QueryMaker
{

    public String makeSelectQuery(String query, final String databaseType, final String schema)
    {
        query = query.replaceAll("\\s", " ");
        query = query.trim();
        String selectQ = "";
        if (query.toUpperCase().startsWith("UPDATE "))
        {
            selectQ = "SELECT * FROM ";
            query = query.substring(7);
            query = query.trim();
            selectQ = selectQ + query;
            int startIndex = selectQ.toUpperCase().indexOf(" SET ");
            int endIndex = selectQ.toUpperCase().indexOf(" WHERE ", startIndex);
            selectQ = selectQ.substring(0, startIndex) + " " + selectQ.substring(endIndex);
            selectQ = selectQ.replaceAll("\\s{1,}", " ");
        }
        else if (query.toUpperCase().startsWith("DELETE "))
        {
            selectQ = "SELECT * FROM ";
            query = query.substring(7);
            query = query.trim();
            if (query.toUpperCase().startsWith("FROM ") || query.toUpperCase().contains(" FROM "))
            {
                if (!query.toUpperCase().startsWith("FROM ") && query.toUpperCase().contains(" FROM "))
                {
                    query = query.substring(query.toUpperCase().indexOf(" FROM ")).trim();
                }
                if (query.toUpperCase().startsWith("FROM "))
                {
                    query = query.substring(5);
                    query = query.trim();
                }
            }
            selectQ = selectQ + query;
        }
        else if ("ORACLE".equals(databaseType) && (query.toUpperCase().startsWith("CREATE OR REPLACE ") || query.toUpperCase().startsWith("DROP ")))
        {
            OracleAnalyzer analyzer = new OracleAnalyzer();
            QueryInfoBean qBean = analyzer.analyzeQuery(query, null);
            //selectQ = "SELECT DBMS_METADATA.GET_DDL('" + qBean.getObjType() + "','" + qBean.getObjName() + "','" + schema + "') FROM DUAL";
            selectQ = "SELECT TEXT BACKUP FROM ALL_SOURCE WHERE TYPE = '" + qBean.getDbObjInfo().getObjType() + "' AND NAME = '" + qBean.getDbObjInfo().getObjName() + "' AND OWNER = '" + schema + "'";
        }
        else if ("MYSQL".equals(databaseType) && query.toUpperCase().startsWith("DROP "))
        {
            MysqlAnalyzer analyzer = new MysqlAnalyzer();
            QueryInfoBean qBean = analyzer.analyzeQuery(query, null, null);
//            selectQ = "SELECT ROUTINE_DEFINITION BACKUP FROM INFORMATION_SCHEMA.ROUTINES WHERE ROUTINE_SCHEMA = '" + schema + "' AND ROUTINE_TYPE = '" + qBean.getObjType() + "' AND ROUTINE_NAME = '" + qBean.getObjName() + "'";
            selectQ = "SHOW CREATE " + qBean.getDbObjInfo().getObjType() + " " + schema + "." + qBean.getDbObjInfo().getObjName();
        }
        return selectQ;
    }

    public String skipFirstWhere(String query, int afterIndex)
    {
        query = query.replaceAll("\\s", " ");
        query = query.trim();
        String selectQ = "";
        if (query.toUpperCase().startsWith("SELECT * FROM "))
        {
            selectQ = "SELECT * FROM ";
            if (afterIndex <= -1)
            {
                query = query.substring(14);
                query = query.trim();
                selectQ = selectQ + query.substring(0, query.indexOf(' ')) + " ";
                query = query.substring(query.toUpperCase().indexOf("WHERE ", query.toUpperCase().indexOf("WHERE ") + 1)).trim();
                selectQ = selectQ + query;
            }
            else
            {
                selectQ = selectQ + query.substring(13, afterIndex).trim() + " ";
                query = query.substring(afterIndex).trim();
                query = query.substring(query.toUpperCase().indexOf("WHERE ", query.toUpperCase().indexOf("WHERE ") + 1)).trim();
                selectQ = selectQ + query;
            }
            selectQ = selectQ.replaceAll("\\s", " ");
        }
        return selectQ;
    }

    public String getTableNameFromCreateTableQuery(String query)
    {
        query = query.replaceAll("\\s", " ");
        query = query.trim();
        if (query.toUpperCase().startsWith("CREATE TABLE "))
        {
            query = query.substring(13);
            if (query.contains(" "))
            {
                String tmp = query.substring(0, query.indexOf(' ')).trim();
                if (tmp.contains("("))
                {
                    tmp = tmp.substring(0, tmp.indexOf('(')).trim();
                }
                if (tmp.contains("."))
                {
                    query = tmp.split("\\.")[1].trim();
                }
                else
                {
                    query = tmp;
                }
            }
            else if (query.contains("."))
            {
                query = query.split("\\.")[1].trim();
            }
            else
            {
                query = query.trim();
            }
        }
        else
        {
            query = "";
        }
        return query;
    }

    public Map executeExplain(final String server, final String database, final String query)
    {
        SQLTranUtility tran = null;
        Map map = new HashMap();
        try
        {
            tran = new SQLTranUtility();
            tran.openConn(server);
            String dbType = tran.getConnection().getMetaData().getDatabaseProductName().toUpperCase();
            if ("MYSQL".equals(dbType))
            {
                tran.persist("USE " + database);
            }
            try
            {
                if ("MYSQL".equals(dbType))
                {
                    tran.getRowSet(query);
                }
                else if ("ORACLE".equals(dbType))
                {
                    tran.persist(query);
                }
                map.put("valid", "true");
                return map;
            }
            catch (Exception e)
            {
                map.put("valid", "false");
                map.put("error", e.getMessage());
                return map;
            }
        }
        catch (Exception e)
        {
            Logger.ErrorLogger(e);
            map.put("valid", "false");
            map.put("error", e.getMessage());
            return map;
        }
        finally
        {
            try
            {
                if (tran != null && tran.getConnection() != null && !tran.getConnection().isClosed())
                {
                    tran.closeConn();
                }
            }
            catch (Exception e)
            {
                Logger.ErrorLogger(e);
            }
        }
    }
}
