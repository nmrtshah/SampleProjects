/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.business.finstudio.dbmetadatacomparator;

import com.finlogic.business.finstudio.DBDependencyDataManager;
import com.finlogic.util.finreport.DirectConnection;
import com.finlogic.util.persistence.SQLConnService;
import com.finlogic.util.persistence.SQLService;
import com.finlogic.util.persistence.SQLUtility;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

/**
 *
 * @author Jeegar Kumar Patel
 */
public class DBMcomparatorDataManager
{
    private final SQLService sqlService = new SQLService();
    private static final String ALIASNAME = "finstudio_mysql";
    SqlParameterSource param;
    private final DirectConnection conn = new DirectConnection();
    private static final SQLConnService SQLCS = new SQLConnService();

    public List getDBNames(final DBMcomparatorEntityBean entityBean) throws SQLException, ClassNotFoundException
    {
        StringBuilder SQL = new StringBuilder();
        SQL.append(" SELECT DATABASE_ID, ");
        SQL.append(" CONCAT(DATABASE_NAME,' (',SERVER_NAME,')') DATABASENAME FROM SERVER_MASTER SM ");
        SQL.append(" INNER JOIN DATABASE_MASTER DBM ");
        SQL.append(" ON SM.SERVER_ID = DBM.SERVER_ID ");
        SQL.append(" WHERE DATABASE_NAME LIKE :DBNAME ");

        Map map = new HashMap();
        map.put("DBNAME", entityBean.getTxtDB() + "%");

        param = new MapSqlParameterSource(map);
        return sqlService.getList(ALIASNAME, SQL.toString(), param);
    }

    public Set getObjName(DBMcomparatorEntityBean entityBean, String server) throws ClassNotFoundException, SQLException
    {
        StringBuilder SQL = new StringBuilder();
        Set<String> objNameset1 = null;
        Set<String> objNameset2 = null;
        List objNamelst1 = null;
        List objNamelst2 = null;
        String serv1 = "Dev";
        String serv2 = "Test";
        String serv3 = "Prod";
        Connection con1 = null;
        Connection con2 = null;
        try
        {
            if (server.equals("DT"))
            {
                Map m = new DBDependencyDataManager().getDatabaseInfoObj(entityBean.getCmbDB(), entityBean.getServer(), serv1);
                con1 = conn.getConnection(m.get("serveralias").toString());
                Map m1 = new DBDependencyDataManager().getDatabaseInfoObj(entityBean.getCmbDB(), entityBean.getServer(), serv2);
                con2 = conn.getConnection(m1.get("serveralias").toString());
                Map map = new HashMap();
                String objType = getDBType(entityBean.getCmbDB());
                String cmbObjType = entityBean.getCmbObjType();
                map.put("OBJNAME", entityBean.getTxtObjName() + "%");
                map.put("SCHEMANAME", m.get("databasename").toString());

                param = new MapSqlParameterSource(map);
//
//                if (objType.equalsIgnoreCase("MYSQL"))
//                {
//                    SQL.setLength(0);
//                    SQL.append(" SELECT ");
//                    SQL.append(" DISTINCT   OPT_VALUE, TABLE_NAME OPT_TEXT,'TABLE' OBJECT_TYPE ");
//                    SQL.append(" FROM INFORMATION_SCHEMA.TABLES ");
//                    SQL.append(" WHERE 1=1 ");
//                    SQL.append(" AND TABLE_TYPE = 'BASE TABLE' ");
//                    SQL.append(" AND TABLE_SCHEMA LIKE UPPER(:SCHEMANAME) ");
//                    SQL.append(" AND TABLE_NAME LIKE UPPER(:OBJNAME) ");
//                    SQL.append(" UNION ALL ");
//                    SQL.append(" SELECT TABLE_NAME OPT_VALUE,TABLE_NAME OPT_TEXT,'VIEW' OBJECT_TYPE  FROM INFORMATION_SCHEMA.VIEWS");
//                    SQL.append(" WHERE TABLE_SCHEMA = :SCHEMANAME  AND TABLE_NAME LIKE UPPER(:OBJNAME)");
//
//                    objNamelst1 = SQLCS.getList(con1, SQL.toString(), param);
//                    objNameset1 = new HashSet<String>(objNamelst1);
//                    objNamelst2 = SQLCS.getList(con2, SQL.toString(), param);
//                    objNameset2 = new HashSet<String>(objNamelst2);
//
//                    objNameset2.addAll(objNameset1);
//                }
//                else if (objType.equalsIgnoreCase("ORACLE"))
//                {
//                    SQL.setLength(0);
//                    SQL.append(" SELECT DISTINCT OBJECT_NAME AS OPT_VALUE, OBJECT_NAME AS OPT_TEXT,OBJECT_TYPE ");
//                    SQL.append(" FROM DBA_OBJECTS ");
//                    SQL.append(" WHERE 1=1 ");
//                    SQL.append(" AND OBJECT_TYPE = 'TABLE' ");
//                    SQL.append(" AND OWNER LIKE UPPER( :SCHEMANAME ) ");
//                    SQL.append(" AND OBJECT_NAME LIKE UPPER(:OBJNAME) ");
//                    SQL.append(" UNION ALL ");
//                    SQL.append(" SELECT VIEW_NAME OPT_VALUE,VIEW_NAME OPT_TEXT,'VIEW' AS OBJECT_TYPE FROM DBA_VIEWS ");
//                    SQL.append(" WHERE OWNER = UPPER(:SCHEMANAME) AND VIEW_NAME LIKE UPPER(:OBJNAME)");
//                    SQL.append(" UNION ALL ");
//                    SQL.append(" SELECT MVIEW_NAME OPT_VALUE,MVIEW_NAME OPT_TEXT,'MVIEW' AS OBJECT_TYPE FROM DBA_MVIEWS ");
//                    SQL.append(" WHERE OWNER = UPPER(:SCHEMANAME) AND MVIEW_NAME LIKE UPPER(:OBJNAME)");
//                    SQL.append(" MINUS ");
//                    SQL.append(" SELECT MVIEW_NAME OPT_VALUE,MVIEW_NAME OPT_TEXT,'TABLE' AS OBJECT_TYPE FROM DBA_MVIEWS ");
//                    SQL.append(" WHERE OWNER = UPPER(:SCHEMANAME) AND MVIEW_NAME LIKE UPPER(:OBJNAME)");
//                    SQL.append(" ORDER BY OPT_VALUE");
//
//                    objNamelst1 = SQLCS.getList(con1, SQL.toString(), param);
//                    objNameset1 = new HashSet<String>(objNamelst1);
//                    objNamelst2 = SQLCS.getList(con2, SQL.toString(), param);
//                    objNameset2 = new HashSet<String>(objNamelst2);
//
//                    objNameset2.addAll(objNameset1);
//                }
                if (cmbObjType.equalsIgnoreCase("MTABLE") || cmbObjType.equalsIgnoreCase("MFUNCTION") || cmbObjType.equalsIgnoreCase("MPROCEDURE")
                        || cmbObjType.equalsIgnoreCase("MTRIGGER") || cmbObjType.equalsIgnoreCase("MVIEW"))
                {
                    SQL.setLength(0);
                    if (cmbObjType.equalsIgnoreCase("MTABLE"))
                    {
                        SQL.append(" SELECT ");
                        SQL.append(" DISTINCT TABLE_NAME OPT_VALUE, TABLE_NAME OPT_TEXT,'TABLE' OBJECT_TYPE ");
                        SQL.append(" FROM INFORMATION_SCHEMA.TABLES ");
                        SQL.append(" WHERE 1=1 ");
                        SQL.append(" AND TABLE_TYPE = 'BASE TABLE' ");
                        SQL.append(" AND TABLE_SCHEMA LIKE UPPER(:SCHEMANAME) ");
                        SQL.append(" AND TABLE_NAME LIKE UPPER(:OBJNAME) ");
                    }
                    if (cmbObjType.equalsIgnoreCase("MTABLE"))
                    {
                        SQL.append(" UNION ALL ");
                        SQL.append(" SELECT TABLE_NAME OPT_VALUE,TABLE_NAME OPT_TEXT,'VIEW' OBJECT_TYPE  FROM INFORMATION_SCHEMA.VIEWS");
                        SQL.append(" WHERE TABLE_SCHEMA = :SCHEMANAME  AND TABLE_NAME LIKE UPPER(:OBJNAME)");
                    }

                    if (cmbObjType.equalsIgnoreCase("MFUNCTION"))
                    {
                        SQL.append("SELECT ROUTINE_NAME OPT_VALUE,ROUTINE_NAME OPT_TEXT FROM INFORMATION_SCHEMA.ROUTINES ");
                        SQL.append(" WHERE ROUTINE_SCHEMA = :SCHEMANAME AND ROUTINE_TYPE = 'FUNCTION' AND ROUTINE_NAME LIKE UPPER(:OBJNAME)");
                    }
                    else if (cmbObjType.equalsIgnoreCase("MPROCEDURE"))
                    {
                        SQL.append("SELECT ROUTINE_NAME OPT_VALUE,ROUTINE_NAME OPT_TEXT FROM INFORMATION_SCHEMA.ROUTINES ");
                        SQL.append(" WHERE ROUTINE_SCHEMA = :SCHEMANAME AND ROUTINE_TYPE = 'PROCEDURE' AND ROUTINE_NAME LIKE (:OBJNAME)");
                    }
                    else if (cmbObjType.equalsIgnoreCase("MTRIGGER"))
                    {
                        SQL.append("SELECT TRIGGER_NAME OPT_VALUE,TRIGGER_NAME OPT_TEXT FROM INFORMATION_SCHEMA.TRIGGERS ");
                        SQL.append(" WHERE TRIGGER_SCHEMA = :SCHEMANAME AND TRIGGER_NAME LIKE (:OBJNAME)");
                    }
                    objNamelst1 = SQLCS.getList(con1, SQL.toString(), param);
                    objNameset1 = new HashSet<String>(objNamelst1);
                    objNamelst2 = SQLCS.getList(con2, SQL.toString(), param);
                    objNameset2 = new HashSet<String>(objNamelst2);

                    objNameset2.addAll(objNameset1);

                }
                else
                {
                    SQL.append(" SELECT DISTINCT OBJECT_NAME AS OPT_VALUE, OBJECT_NAME AS OPT_TEXT,OBJECT_TYPE ");
                    SQL.append(" FROM ALL_OBJECTS ");
                    SQL.append(" WHERE 1=1 ");
                    if (cmbObjType.equalsIgnoreCase("OTABLE"))
                    {
                        SQL.append(" AND OBJECT_TYPE = 'TABLE' ");
                    }
                    else if (cmbObjType.equalsIgnoreCase("OFUNCTION"))
                    {
                        SQL.append(" AND OBJECT_TYPE = 'FUNCTION' ");
                    }
                    else if (cmbObjType.equalsIgnoreCase("OPROCEDURE"))
                    {
                        SQL.append(" AND OBJECT_TYPE = 'PROCEDURE' ");
                    }
                    else if (cmbObjType.equalsIgnoreCase("OPACKAGEBODY"))
                    {
                        SQL.append(" AND OBJECT_TYPE = 'PACKAGE BODY' ");
                    }
                    else if (cmbObjType.equalsIgnoreCase("OPACKAGE"))
                    {
                        SQL.append(" AND OBJECT_TYPE = 'PACKAGE' ");
                    }
                    else if (cmbObjType.equalsIgnoreCase("OTRIGGER"))
                    {
                        SQL.append(" AND OBJECT_TYPE = 'TRIGGER' ");
                    }
                    SQL.append(" AND OWNER LIKE UPPER( :SCHEMANAME ) ");
                    SQL.append(" AND OBJECT_NAME LIKE UPPER( :OBJNAME ) ");
                    if (cmbObjType.equalsIgnoreCase("OTABLE"))
                    {
                        SQL.append(" UNION ALL ");
                        SQL.append(" SELECT VIEW_NAME OPT_VALUE,VIEW_NAME OPT_TEXT,'VIEW' AS OBJECT_TYPE FROM ALL_VIEWS ");
                        SQL.append(" WHERE OWNER = UPPER(:SCHEMANAME) AND VIEW_NAME LIKE UPPER(:OBJNAME)");
                        SQL.append(" UNION ALL ");
                        SQL.append(" SELECT MVIEW_NAME OPT_VALUE,MVIEW_NAME OPT_TEXT,'MVIEW' AS OBJECT_TYPE FROM ALL_MVIEWS ");
                        SQL.append(" WHERE OWNER = UPPER(:SCHEMANAME) AND MVIEW_NAME LIKE UPPER(:OBJNAME)");
                        SQL.append(" MINUS ");
                        SQL.append(" SELECT MVIEW_NAME OPT_VALUE,MVIEW_NAME OPT_TEXT,'TABLE' AS OBJECT_TYPE FROM ALL_MVIEWS ");
                        SQL.append(" WHERE OWNER = UPPER(:SCHEMANAME) AND MVIEW_NAME LIKE UPPER(:OBJNAME)");
                    }
//                if (cmbObjType.equalsIgnoreCase("OTRIGGER"))
//                {
//                    SQL.append(" MINUS ");
//                    SQL.append(" SELECT MVIEW_NAME OPT_VALUE,MVIEW_NAME OPT_TEXT,'TABLE' AS OBJECT_TYPE FROM ALL_MVIEWS ");
//                    SQL.append(" WHERE OWNER = UPPER(:SCHEMANAME) AND MVIEW_NAME LIKE UPPER(:OBJNAME)");
//
//                }
                    SQL.append(" ORDER BY OPT_VALUE");
                    objNamelst1 = SQLCS.getList(con1, SQL.toString(), param);
                    objNameset1 = new HashSet<String>(objNamelst1);
                    objNamelst2 = SQLCS.getList(con2, SQL.toString(), param);
                    objNameset2 = new HashSet<String>(objNamelst2);

                    objNameset2.addAll(objNameset1);

                }
            }
            else if (server.equals("TP"))
            {
                Map m = new DBDependencyDataManager().getDatabaseInfoObj(entityBean.getCmbDB(), entityBean.getServer(), serv2);
                con1 = conn.getConnection(m.get("serveralias").toString());
                Map m1 = new DBDependencyDataManager().getDatabaseInfoObj(entityBean.getCmbDB(), entityBean.getServer(), serv3);
//                 con2 = conn.getConnection(m1.get("serveralias").toString());
                String cmbObjType = entityBean.getCmbObjType();
                Map map = new HashMap();
                String objType = getDBType(entityBean.getCmbDB());
                map.put("OBJNAME", entityBean.getTxtObjName() + "%");
                map.put("SCHEMANAME", m.get("databasename").toString());

                param = new MapSqlParameterSource(map);

//                if (objType.equalsIgnoreCase("MYSQL")) {
//                    SQL.setLength(0);
//                    SQL.append(" SELECT ");
//                    SQL.append(" DISTINCT TABLE_NAME OPT_VALUE, TABLE_NAME OPT_TEXT,'TABLE' OBJECT_TYPE ");
//                    SQL.append(" FROM INFORMATION_SCHEMA.TABLES ");
//                    SQL.append(" WHERE 1=1 ");
//                    SQL.append(" AND TABLE_TYPE = 'BASE TABLE' ");
//                    SQL.append(" AND TABLE_SCHEMA LIKE UPPER(:SCHEMANAME) ");
//                    SQL.append(" AND TABLE_NAME LIKE UPPER(:OBJNAME) ");
//                    SQL.append(" UNION ALL ");
//                    SQL.append(" SELECT TABLE_NAME OPT_VALUE,TABLE_NAME OPT_TEXT,'VIEW' OBJECT_TYPE  FROM INFORMATION_SCHEMA.VIEWS");
//                    SQL.append(" WHERE TABLE_SCHEMA = :SCHEMANAME  AND TABLE_NAME LIKE UPPER(:OBJNAME)");
//
//                    objNamelst1 = SQLCS.getList(con1, SQL.toString(), param);
//                    objNameset1 = new HashSet<String>(objNamelst1);
//                    String Schemaname = m1.get("serveralias").toString();
//                    objNamelst2 = sqlService.getList(Schemaname, SQL.toString(), param);
//                    objNameset2 = new HashSet<String>(objNamelst2);
//
//                    objNameset2.addAll(objNameset1);
//                } else if (objType.equalsIgnoreCase("ORACLE")) {
//                    SQL.setLength(0);
//                    SQL.append(" SELECT DISTINCT OBJECT_NAME AS OPT_VALUE, OBJECT_NAME AS OPT_TEXT,OBJECT_TYPE ");
//                    SQL.append(" FROM DBA_OBJECTS ");
//                    SQL.append(" WHERE 1=1 ");
//                    SQL.append(" AND OBJECT_TYPE = 'TABLE' ");
//                    SQL.append(" AND OWNER LIKE UPPER( :SCHEMANAME ) ");
//                    SQL.append(" AND OBJECT_NAME LIKE UPPER(:OBJNAME) ");
//                    SQL.append(" UNION ALL ");
//                    SQL.append(" SELECT VIEW_NAME OPT_VALUE,VIEW_NAME OPT_TEXT,'VIEW' AS OBJECT_TYPE FROM DBA_VIEWS ");
//                    SQL.append(" WHERE OWNER = UPPER(:SCHEMANAME) AND VIEW_NAME LIKE UPPER(:OBJNAME)");
//                    SQL.append(" UNION ALL ");
//                    SQL.append(" SELECT MVIEW_NAME OPT_VALUE,MVIEW_NAME OPT_TEXT,'MVIEW' AS OBJECT_TYPE FROM DBA_MVIEWS ");
//                    SQL.append(" WHERE OWNER = UPPER(:SCHEMANAME) AND MVIEW_NAME LIKE UPPER(:OBJNAME)");
//                    SQL.append(" MINUS ");
//                    SQL.append(" SELECT MVIEW_NAME OPT_VALUE,MVIEW_NAME OPT_TEXT,'TABLE' AS OBJECT_TYPE FROM DBA_MVIEWS ");
//                    SQL.append(" WHERE OWNER = UPPER(:SCHEMANAME) AND MVIEW_NAME LIKE UPPER(:OBJNAME)");
//                    SQL.append(" ORDER BY OPT_VALUE");
//
//                    objNamelst1 = SQLCS.getList(con1, SQL.toString(), param);
//                    objNameset1 = new HashSet<String>(objNamelst1);
//                    objNamelst2 = sqlService.getList(m1.get("serveralias").toString(), SQL.toString(), param);
//                    objNameset2 = new HashSet<String>(objNamelst2);
//
//                    objNameset2.addAll(objNameset1);
//                }
                if (cmbObjType.equalsIgnoreCase("MTABLE") || cmbObjType.equalsIgnoreCase("MFUNCTION") || cmbObjType.equalsIgnoreCase("MPROCEDURE")
                        || cmbObjType.equalsIgnoreCase("MTRIGGER") || cmbObjType.equalsIgnoreCase("MVIEW"))
                {
                    SQL.setLength(0);
                    if (cmbObjType.equalsIgnoreCase("MTABLE"))
                    {
                        SQL.append(" SELECT ");
                        SQL.append(" DISTINCT TABLE_NAME OPT_VALUE, TABLE_NAME OPT_TEXT,'TABLE' OBJECT_TYPE ");
                        SQL.append(" FROM INFORMATION_SCHEMA.TABLES ");
                        SQL.append(" WHERE 1=1 ");
                        SQL.append(" AND TABLE_TYPE = 'BASE TABLE' ");
                        SQL.append(" AND TABLE_SCHEMA LIKE UPPER(:SCHEMANAME) ");
                        SQL.append(" AND TABLE_NAME LIKE UPPER(:OBJNAME) ");
                    }
                    if (cmbObjType.equalsIgnoreCase("MTABLE"))
                    {
                        SQL.append(" UNION ALL ");
                        SQL.append(" SELECT TABLE_NAME OPT_VALUE,TABLE_NAME OPT_TEXT,'VIEW' OBJECT_TYPE  FROM INFORMATION_SCHEMA.VIEWS");
                        SQL.append(" WHERE TABLE_SCHEMA = :SCHEMANAME  AND TABLE_NAME LIKE UPPER(:OBJNAME)");
                    }

                    if (cmbObjType.equalsIgnoreCase("MFUNCTION"))
                    {
                        SQL.append("SELECT ROUTINE_NAME OPT_VALUE,ROUTINE_NAME OPT_TEXT FROM INFORMATION_SCHEMA.ROUTINES ");
                        SQL.append(" WHERE ROUTINE_SCHEMA = :SCHEMANAME AND ROUTINE_TYPE = 'FUNCTION' AND ROUTINE_NAME LIKE UPPER(:OBJNAME)");
                    }
                    else if (cmbObjType.equalsIgnoreCase("MPROCEDURE"))
                    {
                        SQL.append("SELECT ROUTINE_NAME OPT_VALUE,ROUTINE_NAME OPT_TEXT FROM INFORMATION_SCHEMA.ROUTINES ");
                        SQL.append(" WHERE ROUTINE_SCHEMA = :SCHEMANAME AND ROUTINE_TYPE = 'PROCEDURE' AND ROUTINE_NAME LIKE (:OBJNAME)");
                    }
                    else if (cmbObjType.equalsIgnoreCase("MTRIGGER"))
                    {
                        SQL.append("SELECT TRIGGER_NAME OPT_VALUE,TRIGGER_NAME OPT_TEXT FROM INFORMATION_SCHEMA.TRIGGERS ");
                        SQL.append(" WHERE TRIGGER_SCHEMA = :SCHEMANAME AND TRIGGER_NAME LIKE (:OBJNAME)");
                    }

                    objNamelst1 = SQLCS.getList(con1, SQL.toString(), param);
                    objNameset1 = new HashSet<String>(objNamelst1);
                    String Schemaname = m1.get("serveralias").toString();
                    objNamelst2 = sqlService.getList(Schemaname, SQL.toString(), param);
                    objNameset2 = new HashSet<String>(objNamelst2);

                    objNameset2.addAll(objNameset1);

                }
                else
                {
                    SQL.append(" SELECT DISTINCT OBJECT_NAME AS OPT_VALUE, OBJECT_NAME AS OPT_TEXT,OBJECT_TYPE ");
                    SQL.append(" FROM ALL_OBJECTS ");
                    SQL.append(" WHERE 1=1 ");
                    if (cmbObjType.equalsIgnoreCase("OTABLE"))
                    {
                        SQL.append(" AND OBJECT_TYPE = 'TABLE' ");
                    }
                    else if (cmbObjType.equalsIgnoreCase("OFUNCTION"))
                    {
                        SQL.append(" AND OBJECT_TYPE = 'FUNCTION' ");
                    }
                    else if (cmbObjType.equalsIgnoreCase("OPROCEDURE"))
                    {
                        SQL.append(" AND OBJECT_TYPE = 'PROCEDURE' ");
                    }
                    else if (cmbObjType.equalsIgnoreCase("OPACKAGEBODY"))
                    {
                        SQL.append(" AND OBJECT_TYPE = 'PACKAGE BODY' ");
                    }
                    else if (cmbObjType.equalsIgnoreCase("OPACKAGE"))
                    {
                        SQL.append(" AND OBJECT_TYPE = 'PACKAGE' ");
                    }
                    else if (cmbObjType.equalsIgnoreCase("OTRIGGER"))
                    {
                        SQL.append(" AND OBJECT_TYPE = 'TRIGGER' ");
                    }
                    SQL.append(" AND OWNER LIKE UPPER( :SCHEMANAME ) ");
                    SQL.append(" AND OBJECT_NAME LIKE UPPER( :OBJNAME ) ");
                    if (cmbObjType.equalsIgnoreCase("OTABLE"))
                    {
                        SQL.append(" UNION ALL ");
                        SQL.append(" SELECT VIEW_NAME OPT_VALUE,VIEW_NAME OPT_TEXT,'VIEW' AS OBJECT_TYPE FROM ALL_VIEWS ");
                        SQL.append(" WHERE OWNER = UPPER(:SCHEMANAME) AND VIEW_NAME LIKE UPPER(:OBJNAME)");
                        SQL.append(" UNION ALL ");
                        SQL.append(" SELECT MVIEW_NAME OPT_VALUE,MVIEW_NAME OPT_TEXT,'MVIEW' AS OBJECT_TYPE FROM ALL_MVIEWS ");
                        SQL.append(" WHERE OWNER = UPPER(:SCHEMANAME) AND MVIEW_NAME LIKE UPPER(:OBJNAME)");
                        SQL.append(" MINUS ");
                        SQL.append(" SELECT MVIEW_NAME OPT_VALUE,MVIEW_NAME OPT_TEXT,'TABLE' AS OBJECT_TYPE FROM ALL_MVIEWS ");
                        SQL.append(" WHERE OWNER = UPPER(:SCHEMANAME) AND MVIEW_NAME LIKE UPPER(:OBJNAME)");
                    }
//                if (cmbObjType.equalsIgnoreCase("OTRIGGER"))
//                {
//                    SQL.append(" MINUS ");
//                    SQL.append(" SELECT MVIEW_NAME OPT_VALUE,MVIEW_NAME OPT_TEXT,'TABLE' AS OBJECT_TYPE FROM ALL_MVIEWS ");
//                    SQL.append(" WHERE OWNER = UPPER(:SCHEMANAME) AND MVIEW_NAME LIKE UPPER(:OBJNAME)");
//
//                }
                    SQL.append(" ORDER BY OPT_VALUE");

                    objNamelst1 = SQLCS.getList(con1, SQL.toString(), param);
                    objNameset1 = new HashSet<String>(objNamelst1);
                    objNamelst2 = sqlService.getList(m1.get("serveralias").toString(), SQL.toString(), param);
                    objNameset2 = new HashSet<String>(objNamelst2);

                    objNameset2.addAll(objNameset1);

                }
            }

        }
        finally
        {
            if (con1 != null && !con1.isClosed())
            {
                con1.close();
            }
            if (con2 != null && !con2.isClosed())
            {
                con2.close();
            }
        }
        return objNameset2;
    }

    public String getDBType(String dbId) throws ClassNotFoundException, SQLException
    {
        StringBuilder SQL = new StringBuilder();
        SQL.append(" SELECT SERVER_TYPE FROM SERVER_MASTER SM ");
        SQL.append(" INNER JOIN DATABASE_MASTER DBM ");
        SQL.append(" ON SM.SERVER_ID = DBM.SERVER_ID ");
        SQL.append(" WHERE DBM.DATABASE_ID = :DBID ");

        Map map = new HashMap();
        map.put("DBID", dbId);

        param = new MapSqlParameterSource(map);

        return sqlService.getString(ALIASNAME, SQL.toString(), param);
    }

    public Map<String, List> getDBDefinition(DBMcomparatorEntityBean entityBean, String serv) throws SQLException, ClassNotFoundException
    {
        Map m = new DBDependencyDataManager().getDatabaseInfoboth(entityBean.getCmbDB(), serv);
        String serverAlias = m.get("serveralias").toString();
        String serverType = m.get("servertype").toString();
        String dbName = m.get("databasename").toString();
        String tblName = entityBean.getCmbObjName();
        String objType = entityBean.getCmbObjType();
        Map<String, List> res = new HashMap<String, List>();
        if (objType.equalsIgnoreCase("MTABLE") || objType.equalsIgnoreCase("OTABLE"))
        {
            if (serverType.equalsIgnoreCase("mysql"))
            {
                if (m.get("server").equals("Prod"))
                {
                    res = mysqlFun(dbName, tblName, serverAlias, "Prod");
                }
                else
                {
                    res = mysqlFun(dbName, tblName, serverAlias, "Other");
                }
            }
            else if (serverType.equalsIgnoreCase("oracle"))
            {
                if (m.get("server").equals("Prod"))
                {
                    res = oracleFun(dbName, tblName, serverAlias, "Prod");
                }
                else
                {
                    res = oracleFun(dbName, tblName, serverAlias, "Other");
                }
            }
        }
        return res;
    }

    private Map<String, List> mysqlFun(String databaseName, String tableName, String connAlias, String serv) throws SQLException, ClassNotFoundException
    {
        SqlRowSet srs, srs1;
        Map<String, List> main = new HashMap<String, List>();
        Connection con1 = null;
        //Find Columns Metadata...
        StringBuilder query = new StringBuilder();
        try
        {
            query.append("SELECT COLUMN_NAME, DATA_TYPE, COLUMN_TYPE DATA_LENGTH, IS_NULLABLE, COLUMN_DEFAULT ");
            query.append("FROM INFORMATION_SCHEMA.COLUMNS ");
            query.append("WHERE table_name = '");
            query.append(tableName);
            query.append("' ");
            query.append("AND table_schema = '");
            query.append(databaseName);
            query.append("' ");

            if (!serv.equals("Prod"))
            {
                con1 = conn.getConnection(connAlias);
                srs = SQLCS.getRowSet(con1, query.toString());
            }
            else
            {
                srs = sqlService.getRowSet(connAlias, query.toString(), param);
            }

            Map<String, String> colMap = null;
            List<Map> colList = new LinkedList<Map>();
            while (srs.next())
            {
                colMap = new LinkedHashMap<String, String>();
                colMap.put("COLUMN_NAME", srs.getString("COLUMN_NAME"));
                colMap.put("DATA_TYPE", srs.getString("DATA_TYPE"));
                colMap.put("DATA_LENGTH", srs.getString("DATA_LENGTH"));
                colMap.put("NULLABLE", srs.getString("IS_NULLABLE"));
                colMap.put("DATA_DEFAULT", srs.getString("COLUMN_DEFAULT"));
                colList.add(colMap);
            }
            main.put("Columns", colList);

            //Find indexes..
            query = new StringBuilder();
            query.append("SELECT INDEX_NAME,COLUMN_NAME ");
            query.append("FROM INFORMATION_SCHEMA.STATISTICS ");
            query.append("WHERE table_name ='");
            query.append(tableName);
            query.append("' ");
            query.append("AND table_schema ='");
            query.append(databaseName);
            query.append("' ORDER BY COLUMN_NAME ");

            if (!serv.equals("Prod"))
            {
                con1 = conn.getConnection(connAlias);
                srs = SQLCS.getRowSet(con1, query.toString());
            }
            else
            {
                srs = sqlService.getRowSet(connAlias, query.toString(), param);
            }

            List<Map> indList = new LinkedList<Map>();
            String data;
            Map<String, String> indMap = null;
            while (srs.next())
            {
                indMap = new LinkedHashMap<String, String>();
                indMap.put("INDEX_NAME", srs.getString("INDEX_NAME"));
                indMap.put("COLUMN_NAME", srs.getString("COLUMN_NAME"));
                indList.add(indMap);
            }
            main.put("Indexes", indList);

            //Find Constraints..
            query = new StringBuilder();
            query.append("SELECT CONSTRAINT_NAME,CONSTRAINT_TYPE ");
            query.append("FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS ");
            query.append("WHERE table_name ='");
            query.append(tableName);
            query.append("' ");
            query.append("AND table_schema  ='");
            query.append(databaseName);
            query.append("' ORDER BY CONSTRAINT_TYPE,CONSTRAINT_NAME");

            if (!serv.equals("Prod"))
            {
                con1 = conn.getConnection(connAlias);
                srs = SQLCS.getRowSet(con1, query.toString());
            }
            else
            {
                srs = sqlService.getRowSet(connAlias, query.toString(), param);
            }

            List<Map> constList = new LinkedList<Map>();
            String conType;
            Map<String, String> conMap = null;
            while (srs.next())
            {
                conMap = new LinkedHashMap<String, String>();
                data = srs.getString("CONSTRAINT_NAME");
                conMap.put("Constraint_Name", data);
                conType = srs.getString("CONSTRAINT_TYPE");
                conMap.put("Constraint_Type", conType);
                if (conType.equalsIgnoreCase("FOREIGN KEY"))
                {
                    query = new StringBuilder();
                    query.append("SELECT REFERENCED_TABLE_NAME,REFERENCED_COLUMN_NAME ");
                    query.append("FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE ");
                    query.append("WHERE TABLE_NAME ='");
                    query.append(tableName);
                    query.append("' ");
                    query.append("AND TABLE_SCHEMA  ='");
                    query.append(databaseName);
                    query.append("' ");
                    query.append("AND CONSTRAINT_NAME='");
                    query.append(data);
                    query.append("' ");
                    query.append("AND REFERENCED_TABLE_NAME IS NOT NULL ");

                    if (!serv.equals("Prod"))
                    {
                        con1 = conn.getConnection(connAlias);
                        srs1 = SQLCS.getRowSet(con1, query.toString());
                    }
                    else
                    {
                        srs1 = sqlService.getRowSet(connAlias, query.toString(), param);
                    }
                    while (srs1.next())
                    {
                        conMap.put("Referenced_Table", srs1.getString("REFERENCED_TABLE_NAME"));
                        conMap.put("Referenced_Column", srs1.getString("REFERENCED_COLUMN_NAME"));
                    }
                }
                else if (conType.equalsIgnoreCase("UNIQUE"))
                {
                    query = new StringBuilder();
                    query.append("SELECT KCU.COLUMN_NAME ");
                    query.append("FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS TC ");
                    query.append("INNER JOIN INFORMATION_SCHEMA.KEY_COLUMN_USAGE KCU ON TC.CONSTRAINT_NAME = KCU.CONSTRAINT_NAME AND TC.CONSTRAINT_SCHEMA = KCU.CONSTRAINT_SCHEMA ");
                    query.append("WHERE KCU.CONSTRAINT_SCHEMA = '");
                    query.append(databaseName);
                    query.append("' AND KCU.TABLE_NAME = '");
                    query.append(tableName);
                    query.append("' AND KCU.CONSTRAINT_NAME = '");
                    query.append(data);
                    query.append("'");

                    if (!serv.equals("Prod"))
                    {
                        con1 = conn.getConnection(connAlias);
                        srs1 = SQLCS.getRowSet(con1, query.toString());
                    }
                    else
                    {
                        srs1 = sqlService.getRowSet(connAlias, query.toString(), param);
                    }
                    while (srs1.next())
                    {
                        conMap.put("Constraint_Detail", "Unique Constraint On " + srs1.getString("COLUMN_NAME"));
                    }
                }
                constList.add(conMap);
            }
            main.put("Constraints", constList);

            //Find Triggers..
            query = new StringBuilder();
            query.append("select TRIGGER_NAME  ");
            query.append("from information_schema.TRIGGERS ");
            query.append("WHERE EVENT_OBJECT_TABLE ='");
            query.append(tableName);
            query.append("' ");
            query.append("AND trigger_schema  ='");
            query.append(databaseName);
            query.append("' ORDER BY TRIGGER_NAME");

            if (!serv.equals("Prod"))
            {
                con1 = conn.getConnection(connAlias);
                srs = SQLCS.getRowSet(con1, query.toString());
            }
            else
            {
                srs = sqlService.getRowSet(connAlias, query.toString(), param);
            }

            List<String> triList = new LinkedList<String>();

            while (srs.next())
            {
                data = srs.getString("TRIGGER_NAME");
                triList.add(data);
            }
            main.put("Triggers", triList);
        }
        finally
        {
            if (con1 != null && !con1.isClosed())
            {
                con1.close();
            }
        }
        return main;
    }

    private Map<String, List> oracleFun(String databaseName, String tableName, String connAlias, String serv) throws SQLException, ClassNotFoundException
    {
        SqlRowSet srs, srsRef;
        Map<String, List> main = new HashMap<String, List>();
        Connection con1 = null;

        try
        {
            //Find Columns Metadata...
            StringBuilder query = new StringBuilder();
            query.append("select COLUMN_NAME,DATA_TYPE,DATA_LENGTH,NULLABLE,DATA_DEFAULT ");
            query.append("from all_tab_columns ");
            query.append("where OWNER='");
            query.append(databaseName);
            query.append("' and TABLE_NAME= '");
            query.append(tableName);
            query.append("'");

            if (!serv.equals("Prod"))
            {
                con1 = conn.getConnection(connAlias);
                srs = SQLCS.getRowSet(con1, query.toString());
            }
            else
            {
                srs = sqlService.getRowSet(connAlias, query.toString(), param);
            }

            Map<String, String> colMap = null;
            List<Map> colList = new LinkedList<Map>();
            while (srs.next())
            {
                colMap = new LinkedHashMap<String, String>();
                colMap.put("COLUMN_NAME", srs.getString("COLUMN_NAME"));
                colMap.put("DATA_TYPE", srs.getString("DATA_TYPE"));
                colMap.put("DATA_LENGTH", srs.getString("DATA_LENGTH"));
                colMap.put("NULLABLE", srs.getString("NULLABLE"));
                colMap.put("DATA_DEFAULT", srs.getString("DATA_DEFAULT"));
                colList.add(colMap);
            }
            main.put("Columns", colList);

            //Find indexes..
            query = new StringBuilder();
            query.append("SELECT INDEX_NAME,COLUMN_NAME ");
            query.append("FROM ALL_IND_COLUMNS ");
            query.append("WHERE TABLE_OWNER='");
            query.append(databaseName);
            query.append("' and TABLE_NAME = '");
            query.append(tableName);
            query.append("'ORDER BY COLUMN_NAME");

            if (!serv.equals("Prod"))
            {
                con1 = conn.getConnection(connAlias);
                srs = SQLCS.getRowSet(con1, query.toString());
            }
            else
            {
                srs = sqlService.getRowSet(connAlias, query.toString(), param);
            }

            List<Map> indList = new LinkedList<Map>();
            String data;
            Map<String, String> indMap = null;
            while (srs.next())
            {
                indMap = new LinkedHashMap<String, String>();
                indMap.put("INDEX_NAME", srs.getString("INDEX_NAME"));
                indMap.put("COLUMN_NAME", srs.getString("COLUMN_NAME"));
                indList.add(indMap);
            }
            main.put("Indexes", indList);

            //Find Constraints..
            query = new StringBuilder();
            query.append("SELECT CONSTRAINT_NAME,CONSTRAINT_TYPE ");
            query.append("FROM ALL_CONSTRAINTS ");
            query.append("WHERE OWNER='");
            query.append(databaseName);
            query.append("' and TABLE_NAME  = '");
            query.append(tableName);
            query.append("' ORDER BY CONSTRAINT_TYPE,CONSTRAINT_NAME");

            if (!serv.equals("Prod"))
            {
                con1 = conn.getConnection(connAlias);
                srs = SQLCS.getRowSet(con1, query.toString());
            }
            else
            {
                srs = sqlService.getRowSet(connAlias, query.toString(), param);
            }

            List<Map> constList = new LinkedList<Map>();
            Map<String, String> conMap = null;
            String conType, conTp = null;
            while (srs.next())
            {
                conMap = new LinkedHashMap<String, String>();
                data = srs.getString("CONSTRAINT_NAME");
                conMap.put("Constraint_Name", data);
                conType = srs.getString("CONSTRAINT_TYPE");
                if ("C".equalsIgnoreCase(conType))
                {
                    conTp = "Check Constraint";
                    query = new StringBuilder();
                    query.append("SELECT SEARCH_CONDITION FROM ALL_CONSTRAINTS ");
                    query.append("WHERE TABLE_NAME = '");
                    query.append(tableName);
                    query.append("' AND CONSTRAINT_TYPE = 'C' AND OWNER = '");
                    query.append(databaseName);
                    query.append("' AND CONSTRAINT_NAME = '");
                    query.append(data);
                    query.append("'");

                    if (!serv.equals("Prod"))
                    {
                        con1 = conn.getConnection(connAlias);
                        srsRef = SQLCS.getRowSet(con1, query.toString());
                    }
                    else
                    {
                        srsRef = sqlService.getRowSet(connAlias, query.toString(), param);
                    }

                    while (srsRef.next())
                    {
                        conMap.put("Constraint_Detail", srsRef.getString("SEARCH_CONDITION"));
                    }
                }
                else if ("P".equalsIgnoreCase(conType))
                {
                    conTp = "Primary Key Constraint";
                }
                else if ("U".equalsIgnoreCase(conType))
                {
                    conTp = "Unique Key Constraint";
                    query = new StringBuilder();
                    query.append("SELECT ACC.COLUMN_NAME FROM ALL_CONSTRAINTS AC  ");
                    query.append("INNER JOIN ALL_CONS_COLUMNS ACC ON AC.OWNER = ACC.OWNER AND AC.CONSTRAINT_NAME = ACC.CONSTRAINT_NAME ");
                    query.append("WHERE AC.CONSTRAINT_TYPE = 'U' AND AC.OWNER = '");
                    query.append(databaseName);
                    query.append("' AND ACC.TABLE_NAME = '");
                    query.append(tableName);
                    query.append("' AND ACC.CONSTRAINT_NAME = '");
                    query.append(data);
                    query.append("'");

                    if (!serv.equals("Prod"))
                    {
                        con1 = conn.getConnection(connAlias);
                        srsRef = SQLCS.getRowSet(con1, query.toString());
                    }
                    else
                    {
                        srsRef = sqlService.getRowSet(connAlias, query.toString(), param);
                    }

                    while (srsRef.next())
                    {
                        conMap.put("Constraint_Detail", "Unique Constraint On " + srsRef.getString("COLUMN_NAME"));
                    }
                }
                else if ("R".equalsIgnoreCase(conType))
                {
                    conTp = "Foreign Key Constraint";
                    query = new StringBuilder();
                    query.append("SELECT UCC.TABLE_NAME,UCC.COLUMN_NAME ");
                    query.append("FROM ALL_CONSTRAINTS  UC,ALL_CONS_COLUMNS UCC ");
                    query.append("WHERE UC.R_CONSTRAINT_NAME = UCC.CONSTRAINT_NAME AND UC.CONSTRAINT_TYPE = 'R' AND UC.OWNER = '");
                    query.append(databaseName);
                    query.append("' AND UCC.OWNER = '");
                    query.append(databaseName);
                    query.append("' AND UC.CONSTRAINT_NAME ='");
                    query.append(data);
                    query.append("'");

                    if (!serv.equals("Prod"))
                    {
                        con1 = conn.getConnection(connAlias);
                        srsRef = SQLCS.getRowSet(con1, query.toString());
                    }
                    else
                    {
                        srsRef = sqlService.getRowSet(connAlias, query.toString(), param);
                    }

                    while (srsRef.next())
                    {
                        conMap.put("Referenced_Table", srsRef.getString("TABLE_NAME"));
                        conMap.put("Referenced_Column", srsRef.getString("COLUMN_NAME"));
                    }
                }
                else if ("V".equalsIgnoreCase(conType))
                {
                    conTp = "Check Option,On a View";
                }
                else if ("O".equalsIgnoreCase(conType))
                {
                    conTp = "Read Only, On a View";
                }
                conMap.put("Constraint_Type", conTp);
                constList.add(conMap);
            }
            main.put("Constraints", constList);

            //Find Triggers..
            query = new StringBuilder();
            query.append("SELECT DISTINCT trigger_name ");
            query.append("FROM all_triggers ");
            query.append("WHERE OWNER='");
            query.append(databaseName);
            query.append("' and TABLE_NAME = '");
            query.append(tableName);
            query.append("'ORDER BY trigger_name");

            if (!serv.equals("Prod"))
            {
                con1 = conn.getConnection(connAlias);
                srs = SQLCS.getRowSet(con1, query.toString());
            }
            else
            {
                srs = sqlService.getRowSet(connAlias, query.toString(), param);
            }

            List<String> triList = new LinkedList<String>();
            while (srs.next())
            {
                data = srs.getString("trigger_name");
                triList.add(data);
            }
            main.put("Triggers", triList);
        }
        finally
        {
            if (con1 != null && !con1.isClosed())
            {
                con1.close();
            }
        }
        return main;
    }

    public String getDataBaseName(String dbId) throws ClassNotFoundException, SQLException
    {
        return sqlService.getString(ALIASNAME, "SELECT DATABASE_NAME FROM DATABASE_MASTER WHERE DATABASE_ID = " + dbId);
    }

    public Map<String, List> getObjDefination(String objType, String objName, String dbId, String hdnObjType, String serv, DBMcomparatorEntityBean entityBean) throws ClassNotFoundException, SQLException
    {
        //StringBuilder text = new StringBuilder();

        StringBuilder query = new StringBuilder();
        Map<String, List> main = new HashMap<String, List>();
        SqlRowSet srs;
        Connection con1 = null;
        String dbName = getDataBaseName(dbId);
        String SCHEMALIASNAME = "";

        // Map m = new DBDependencyDataManager().getDatabaseInfo(dbId);
        try
        {
            Map m = new DBDependencyDataManager().getDatabaseInfoboth(entityBean.getCmbDB(), serv);
            SCHEMALIASNAME = m.get("serveralias").toString();
            Map mprod = new DBDependencyDataManager().getDatabaseInfoObj(entityBean.getCmbDB(), entityBean.getServer(), serv);
            //String pattern = "";
            Map map = new HashMap();

            for (int i = 0; i < objName.length(); i++)
            {
                if (objName.equals("-1"))
                {
                    continue;
                }
                map.clear();
                //pattern = "\n-----------------------------------" + "\n---------  " + objName+ "\n----------------------------------- \n";
                map.put("OBJNAME", objName);
                map.put("SCHEMANAME", dbName);
                //Logger.DataLogger(objName + "obj" + dbName.toString());
                param = new MapSqlParameterSource(map);
                query.setLength(0);
                if (objType.equalsIgnoreCase("OFUNCTION"))
                {
                    query.append("SELECT TEXT FROM ALL_SOURCE ");
                    query.append("WHERE NAME = :OBJNAME AND TYPE = 'FUNCTION' AND OWNER = :SCHEMANAME");
                }
                else if (objType.equalsIgnoreCase("OPROCEDURE"))
                {
                    query.append("SELECT TEXT FROM ALL_SOURCE ");
                    query.append("WHERE NAME = :OBJNAME AND TYPE = 'PROCEDURE' AND OWNER = :SCHEMANAME");
                }
                else if (objType.equalsIgnoreCase("OTRIGGER"))
                {
                    query.append("SELECT  TEXT FROM ALL_SOURCE ");
                    query.append(" WHERE OWNER = :SCHEMANAME AND TYPE = 'TRIGGER' AND NAME = :OBJNAME");
                }
                else if (objType.equalsIgnoreCase("MFUNCTION"))
                {
                    query.append("SELECT ROUTINE_DEFINITION FROM INFORMATION_SCHEMA.ROUTINES ");
                    query.append("WHERE ROUTINE_SCHEMA = :SCHEMANAME AND ROUTINE_TYPE = 'FUNCTION' AND ROUTINE_NAME = :OBJNAME");
                }
                else if (objType.equalsIgnoreCase("MPROCEDURE"))
                {
                    query.append("SELECT ROUTINE_DEFINITION FROM INFORMATION_SCHEMA.ROUTINES ");
                    query.append("WHERE ROUTINE_SCHEMA = :SCHEMANAME AND ROUTINE_TYPE = 'PROCEDURE' AND ROUTINE_NAME = :OBJNAME");
                }
                else if (objType.equalsIgnoreCase("MTRIGGER"))
                {
                    query.append("SELECT ACTION_STATEMENT ROUTINE_DEFINITION FROM INFORMATION_SCHEMA.TRIGGERS ");
                    query.append(" WHERE TRIGGER_SCHEMA = :SCHEMANAME AND TRIGGER_NAME = :OBJNAME");
                }
                main.clear();
                if (!serv.equals("Prod"))
                {
                    con1 = conn.getConnection(SCHEMALIASNAME);
                    srs = SQLCS.getRowSet(con1, query.toString(), param);
                    Map<String, String> colMap1 = null;
                    List<Map> colList1 = new LinkedList<Map>();
                    while (srs.next())
                    {
                        colMap1 = new LinkedHashMap<String, String>();
                        colMap1.put("Data1", srs.getString(1));
                        colList1.add(colMap1);

                    }
                    if (serv.equalsIgnoreCase("Dev"))
                    {
                        main.put("AllData1", colList1);
                    }
                    else
                    {
                        main.put("AllData2", colList1);
                    }

                }
                else
                {
                    srs = sqlService.getRowSet(SCHEMALIASNAME, query.toString(), param);

                    Map<String, String> colMap2 = null;
                    List<Map> colList2 = new LinkedList<Map>();
                    while (srs.next())
                    {
                        colMap2 = new LinkedHashMap<String, String>();
                        colMap2.put("Data1", srs.getString(1));
                        colList2.add(colMap2);
                    }
                    main.put("AllData3", colList2);


                }

            }
        }
        finally
        {

            if (con1 != null && !con1.isClosed())
            {
                con1.close();
            }
        }
        return main;
    }
}