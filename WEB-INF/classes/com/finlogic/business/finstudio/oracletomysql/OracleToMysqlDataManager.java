/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.business.finstudio.oracletomysql;

import com.finlogic.util.Logger;
import com.finlogic.util.finreport.DirectConnection;
import com.finlogic.util.persistence.SQLConnService;
import com.finlogic.util.persistence.SQLService;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.support.rowset.SqlRowSet;

/**
 *
 * @author njuser
 */
public class OracleToMysqlDataManager
{

    public int insert(OracleToMysqlEntityBean entityBean) throws ClassNotFoundException, SQLException
    {
        String insertQuery = "INSERT INTO ORACLETOMYSQL(SERVER_NAME,SCHEMA_NAME,ITEM_TYPE,ITEM_NAME,EMP_CODE,ON_DATE)"
                + " VALUES('" + entityBean.getServer() + "','" + entityBean.getSchema() + "','" + entityBean.getItem() + "','" + entityBean.getItemNmCmb() + "','" + entityBean.getEmp_code() + "',SYSDATE())";

        SQLService SQLS = new SQLService();

        return SQLS.persist("finstudio_mysql", insertQuery);
    }

    public List getSchema(String server) throws ClassNotFoundException, SQLException
    {
        List list = new ArrayList();
        Connection conn = null;
        try
        {
            String query = "SELECT DISTINCT OWNER FROM DBA_SEGMENTS WHERE OWNER IN (SELECT USERNAME FROM DBA_USERS WHERE DEFAULT_TABLESPACE NOT IN('SYSTEM','SYSAUX')) ORDER BY OWNER";

            DirectConnection dc = new DirectConnection();
            conn = dc.getConnection(server);

            SQLConnService sqlcs = new SQLConnService();
            list = sqlcs.getList(conn, query);
        }
        finally
        {
            conn.close();
            return list;
        }
    }

    public List getItemList(String item, String owner, String server) throws ClassNotFoundException, SQLException
    {
        List list = new ArrayList();
        Connection conn = null;
        try
        {
            String query = "SELECT OBJECT_NAME FROM ALL_OBJECTS WHERE OBJECT_TYPE='" + item + "' and OWNER='" + owner + "' ORDER BY OBJECT_NAME";

            DirectConnection dc = new DirectConnection();
            conn = dc.getConnection(server);

            SQLConnService sqlcs = new SQLConnService();
            list = sqlcs.getList(conn, query);
        }
        finally
        {
            conn.close();
            return list;
        }
    }

    public String getNewQuery(String item, String owner, String server, String itemNm) throws ClassNotFoundException, SQLException
    {
        Connection conn = null;
        StringBuilder text = new StringBuilder();
        try
        {
            String query = "SELECT text FROM ALL_SOURCE WHERE NAME= '" + itemNm + "' and OWNER='" + owner + "' and TYPE='" + item + "'";
            DirectConnection dc = new DirectConnection();
            conn = dc.getConnection(server);

            SQLConnService sqlcs = new SQLConnService();
            SqlRowSet rs = sqlcs.getRowSet(conn, query);
            while (rs.next())
            {
                text.append(rs.getString(1));
            }
        }
        finally
        {
            conn.close();
            return text.toString();
        }
    }

    public SqlRowSet getFieldName(String typeName, String server, String schema) throws ClassNotFoundException, SQLException
    {
        Connection conn = null;
        SqlRowSet srs = null;
        try
        {
            DirectConnection dc = new DirectConnection();
            conn = dc.getConnection(server);

            SQLConnService sqlcs = new SQLConnService();
            srs = sqlcs.getRowSet(conn, "SELECT TEXT FROM ALL_SOURCE WHERE TYPE = 'TYPE' AND OWNER = '" + schema.trim() + "' AND NAME='" + typeName.trim() + "'");
        }
        finally
        {
            conn.close();
            return srs;
        }
    }

    public SqlRowSet getAllTypeNames(String server, String schema) throws SQLException
    {
        Connection conn = null;
        SqlRowSet srs = null;
        try
        {
            String query = "SELECT DISTINCT NAME FROM ALL_SOURCE WHERE TYPE = 'TYPE' AND OWNER = '" + schema + "'";

            DirectConnection dc = new DirectConnection();
            conn = dc.getConnection(server);

            SQLConnService sqlcs = new SQLConnService();
            srs = sqlcs.getRowSet(conn, query);
        }
        finally
        {
            conn.close();
            return srs;
        }
    }

    public int validateProc(String procLines, String server, String schema, String procName, String itemType) throws SQLException, ClassNotFoundException
    {
        Connection conn = null;
        int result = 1;
        try
        {
            DirectConnection dc = new DirectConnection();
            conn = dc.getConnection(server);
            Statement stmt = conn.createStatement();
            result = stmt.executeUpdate(procLines);
            if (result == 0)
            {
                String line = "DROP " + itemType + " " + procName;
                stmt.execute(line);
            }
            stmt.close();
        }
        catch (SQLException e)
        {
            Logger.ErrorLogger(e);
            result = 9999;
        }
        finally
        {
            SQLService SQLS = new SQLService();
            int count = SQLS.getInt("MFTRAN", "SELECT COUNT(*) FROM ALL_SOURCE WHERE OWNER = '" + schema + "' AND TYPE = '" + itemType + "' AND NAME = '" + procName + "'");            
            Statement stmt = conn.createStatement();
            if (count > 0)
            {
                String line = "DROP " + itemType + " " + procName;
                stmt.execute(line);
            }
            stmt.close();
            if (conn != null)
            {
                conn.close();
            }
            return result;
        }
    }
}
