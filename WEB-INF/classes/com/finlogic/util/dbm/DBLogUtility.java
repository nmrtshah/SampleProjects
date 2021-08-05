/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.dbm;

import finutils.directconn.DBConnManager;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Sonam Patel
 */
public class DBLogUtility
{

    public void createMySQLLogTable(final String serverName, final String logSchema, final String schema, final String table, final boolean isReplace) throws ClassNotFoundException, SQLException
    {
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        DatabaseMetaData dbmd = null;

        try
        {
            DBConnManager objDBConnMgr = new DBConnManager();
            conn = objDBConnMgr.getFinConn(serverName);
            dbmd = conn.getMetaData();
            rs = dbmd.getColumns(schema, null, table, null);

            String cols = "";
            String old_col = "";
            String new_col = "";
            while (rs.next())
            {
                cols = cols + rs.getString(4) + " , ";
                old_col = old_col + " OLD." + rs.getString(4) + " , ";
                new_col = new_col + " NEW." + rs.getString(4) + " , ";
            }
            rs.close();

            StringBuilder query = new StringBuilder();
            if (isReplace)
            {
                try
                {
                    query.append("DROP TABLE IF EXISTS ").append(logSchema).append(".").append(table);
                    psmt = conn.prepareStatement(query.toString());
                    psmt.execute();
                }
                catch (Exception e)
                {
                }
            }

            query.setLength(0);
            query.append("CREATE TABLE ").append(logSchema).append(".").append(table);
            query.append(" AS ");
            query.append("SELECT * FROM ").append(schema).append(".").append(table).append(" WHERE 1=2");
            psmt = conn.prepareStatement(query.toString());
            psmt.execute();

            query.setLength(0);
            query.append("ALTER TABLE ").append(logSchema).append(".").append(table);
            query.append(" ADD COLUMN LOG_ENTDATE TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,");
            query.append(" ADD COLUMN LOG_ACTION CHAR(1) NULL");
            psmt = conn.prepareStatement(query.toString());
            psmt.execute();
        }
        finally
        {
            if (conn != null && !conn.isClosed())
            {
                conn.close();
            }
        }
    }

    public void createOracleLogTable(final String serverName, final String logSchema, final String schema, final String table, final boolean isReplace) throws ClassNotFoundException, SQLException
    {
        Connection conn = null;
        PreparedStatement psmt = null;

        try
        {
            DBConnManager objDBConnMgr = new DBConnManager();
            conn = objDBConnMgr.getFinConn(serverName);

            StringBuilder query = new StringBuilder();
            query.append("CALL DBADMIN.BUILD_LOG_TABLE('").append(logSchema).append("', '").append(schema).append("', '").append(table);
            if (isReplace)
            {
                query.append("', 0)");
            }
            else
            {
                query.append("', 1)");
            }
            psmt = conn.prepareStatement(query.toString());
            psmt.execute();
        }
        finally
        {
            if (conn != null && !conn.isClosed())
            {
                conn.close();
            }
        }
    }

    public void createMySQLLogTrigger(final String serverName, final String logSchema, final String schema, final String table, final boolean isReplace) throws ClassNotFoundException, SQLException
    {
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        DatabaseMetaData dbmd = null;

        try
        {
            DBConnManager objDBConnMgr = new DBConnManager();
            conn = objDBConnMgr.getFinConn(serverName);
            dbmd = conn.getMetaData();
            rs = dbmd.getColumns(schema, null, table, null);

            String cols = "";
            String old_col = "";
            String new_col = "";
            while (rs.next())
            {
                cols = cols + rs.getString(4) + " , ";
                old_col = old_col + " OLD." + rs.getString(4) + " , ";
                new_col = new_col + " NEW." + rs.getString(4) + " , ";
            }
            rs.close();

            StringBuilder query = new StringBuilder();
            if (isReplace)
            {
                try
                {
                    query.append("DROP TRIGGER ").append(schema).append(".T_I_").append(table).append(" ");
                    psmt = conn.prepareStatement(query.toString());
                    psmt.execute();
                }
                catch (Exception e)
                {
                }

                try
                {
                    query.setLength(0);
                    query.append("DROP TRIGGER ").append(schema).append(".T_U_").append(table).append(" ");
                    psmt = conn.prepareStatement(query.toString());
                    psmt.execute();
                }
                catch (Exception e)
                {
                }

                try
                {
                    query.setLength(0);
                    query.append("DROP TRIGGER ").append(schema).append(".T_D_").append(table).append(" ");
                    psmt = conn.prepareStatement(query.toString());
                    psmt.execute();
                }
                catch (Exception e)
                {
                }
            }

            query.setLength(0);
            query.append("CREATE TRIGGER ").append(schema).append(".T_I_").append(table).append(" AFTER INSERT ON ").append(schema).append(".").append(table);
            query.append(" FOR EACH ROW");
            query.append(" BEGIN");
            query.append(" INSERT INTO ").append(logSchema).append(".").append(table).append(" (").append(cols).append(" LOG_ACTION)");
            query.append(" VALUES ( ").append(new_col).append(" 'A' );");
            query.append(" END");
            psmt = conn.prepareStatement(query.toString());
            psmt.execute();

            query.setLength(0);
            query.append("CREATE TRIGGER ").append(schema).append(".T_U_").append(table).append(" AFTER UPDATE ON ").append(schema).append(".").append(table);
            query.append(" FOR EACH ROW");
            query.append(" BEGIN");
            query.append(" INSERT INTO ").append(logSchema).append(".").append(table).append(" (").append(cols).append(" LOG_ACTION)");
            query.append(" VALUES ( ").append(new_col).append(" 'E' ); ");
            query.append(" END");
            psmt = conn.prepareStatement(query.toString());
            psmt.execute();

            query.setLength(0);
            query.append("CREATE TRIGGER ").append(schema).append(".T_D_").append(table).append(" AFTER DELETE ON ").append(schema).append(".").append(table);
            query.append(" FOR EACH ROW");
            query.append(" BEGIN");
            query.append(" INSERT INTO ").append(logSchema).append(".").append(table).append(" (").append(cols).append(" LOG_ACTION)");
            query.append(" VALUES ( ").append(old_col).append(" 'D' ); ");
            query.append(" END");
            psmt = conn.prepareStatement(query.toString());
            psmt.execute();
        }
        finally
        {
            if (conn != null && !conn.isClosed())
            {
                conn.close();
            }
        }
    }

    public void createOracleLogTrigger(final String serverName, final String logSchema, final String schema, final String table, final boolean isReplace) throws ClassNotFoundException, SQLException
    {
        Connection conn = null;
        PreparedStatement psmt = null;

        try
        {
            DBConnManager objDBConnMgr = new DBConnManager();
            conn = objDBConnMgr.getFinConn(serverName);

            StringBuilder query = new StringBuilder();
            query.append("CALL DBADMIN.BUILD_LOG_TRIGGER('").append(logSchema).append("', '").append(schema).append("', '").append(table);
            if (isReplace)
            {
                query.append("', 0)");
            }
            else
            {
                query.append("', 1)");
            }
            psmt = conn.prepareStatement(query.toString());
            psmt.execute();
        }
        finally
        {
            if (conn != null && !conn.isClosed())
            {
                conn.close();
            }
        }
    }
}
