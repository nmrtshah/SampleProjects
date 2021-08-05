/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.business.finstudio.dbmetadatainspector;

import com.finlogic.business.finstudio.DBDependencyDataManager;
import com.finlogic.util.Logger;
import com.finlogic.util.persistence.SQLService;
import com.finlogic.util.persistence.SQLUtility;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

/**
 *
 * @author Bhumika Dodiya
 */
public class DBMetadataDataManager
{

    SQLService sqlService = new SQLService();
    private final SQLUtility sqlUtility = new SQLUtility();
    String ALIASNAME = "finstudio_mysql";
    SqlParameterSource param;

    public List getDBNames(final DBMetadataEntityBean entityBean) throws SQLException, ClassNotFoundException
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

    public List getObjName(DBMetadataEntityBean entityBean) throws ClassNotFoundException, SQLException
    {
        StringBuilder SQL = new StringBuilder();

        String SCHEMALIASNAME = "";
        Map m = new DBDependencyDataManager().getDatabaseInfo(entityBean.getCmbDB());
        SCHEMALIASNAME = m.get("serveralias").toString();
        
        Logger.DataLogger("1: m : "+m);

        Map map = new HashMap();
        String objType = entityBean.getCmbObjType();
        map.put("OBJNAME", entityBean.getTxtObjName() + "%");
        map.put("SCHEMANAME", m.get("databasename").toString());

        param = new MapSqlParameterSource(map);
        List objNamelst;

        if (objType.equalsIgnoreCase("MTABLE") || objType.equalsIgnoreCase("MFUNCTION") || objType.equalsIgnoreCase("MPROCEDURE")
                || objType.equalsIgnoreCase("MTRIGGER") || objType.equalsIgnoreCase("MVIEW"))
        {
            SQL.setLength(0);
            if (objType.equalsIgnoreCase("MTABLE"))
            {
                SQL.append(" SELECT ");
                SQL.append(" DISTINCT TABLE_NAME OPT_VALUE, TABLE_NAME OPT_TEXT,'TABLE' OBJECT_TYPE ");
                SQL.append(" FROM INFORMATION_SCHEMA.TABLES ");
                SQL.append(" WHERE 1=1 ");
                SQL.append(" AND TABLE_TYPE = 'BASE TABLE' ");
                SQL.append(" AND TABLE_SCHEMA LIKE UPPER(:SCHEMANAME) ");
                SQL.append(" AND TABLE_NAME LIKE UPPER(:OBJNAME) ");            
                SQL.append(" UNION ALL ");
                SQL.append(" SELECT TABLE_NAME OPT_VALUE,TABLE_NAME OPT_TEXT,'VIEW' OBJECT_TYPE  FROM INFORMATION_SCHEMA.VIEWS");
                SQL.append(" WHERE TABLE_SCHEMA = :SCHEMANAME  AND TABLE_NAME LIKE UPPER(:OBJNAME)");
            }
            if(objType.equalsIgnoreCase("MTRIGGER"))
            {                
                SQL.append(" SELECT TRIGGER_NAME OPT_VALUE,TRIGGER_NAME OPT_TEXT,'TRIGGER' OBJECT_TYPE  FROM INFORMATION_SCHEMA.TRIGGERS");
                SQL.append(" WHERE TRIGGER_SCHEMA = :SCHEMANAME  AND TRIGGER_NAME LIKE UPPER(:OBJNAME)");
            }

            if (objType.equalsIgnoreCase("MFUNCTION"))
            {
                SQL.append("SELECT ROUTINE_NAME OPT_VALUE,ROUTINE_NAME OPT_TEXT FROM INFORMATION_SCHEMA.ROUTINES ");
                SQL.append(" WHERE ROUTINE_SCHEMA = :SCHEMANAME AND ROUTINE_TYPE = 'FUNCTION' AND ROUTINE_NAME LIKE UPPER(:OBJNAME)");
            }
            else if (objType.equalsIgnoreCase("MPROCEDURE"))
            {
                SQL.append("SELECT ROUTINE_NAME OPT_VALUE,ROUTINE_NAME OPT_TEXT FROM INFORMATION_SCHEMA.ROUTINES ");
                SQL.append(" WHERE ROUTINE_SCHEMA = :SCHEMANAME AND ROUTINE_TYPE = 'PROCEDURE' AND ROUTINE_NAME LIKE (:OBJNAME)");
            }
            objNamelst = sqlService.getList(SCHEMALIASNAME, SQL.toString(), param);
        }
        else
        {
            SQL.setLength(0);
            if (objType.equalsIgnoreCase("OSEQUENCE"))
            {
                SQL.append("SELECT SEQUENCE_NAME OPT_VALUE,SEQUENCE_NAME OPT_TEXT FROM ALL_SEQUENCES ");
                SQL.append(" WHERE SEQUENCE_OWNER = UPPER(:SCHEMANAME) AND SEQUENCE_NAME LIKE UPPER(:OBJNAME)");
            }
            else
            {
                SQL.append(" SELECT DISTINCT OBJECT_NAME AS OPT_VALUE, OBJECT_NAME AS OPT_TEXT,OBJECT_TYPE ");
                SQL.append(" FROM ALL_OBJECTS ");
                SQL.append(" WHERE 1=1 ");
                if (objType.equalsIgnoreCase("OTABLE") || objType.equalsIgnoreCase("OTRIGGER"))
                {
                    SQL.append(" AND OBJECT_TYPE = 'TABLE' ");
                }
                else if (objType.equalsIgnoreCase("OFUNCTION"))
                {
                    SQL.append(" AND OBJECT_TYPE = 'FUNCTION' ");
                }
                else if (objType.equalsIgnoreCase("OPROCEDURE"))
                {
                    SQL.append(" AND OBJECT_TYPE = 'PROCEDURE' ");
                }
                else if (objType.equalsIgnoreCase("OPACKAGEBODY"))
                {
                    SQL.append(" AND OBJECT_TYPE = 'PACKAGE BODY' ");
                }
                else if (objType.equalsIgnoreCase("OPACKAGE"))
                {
                    SQL.append(" AND OBJECT_TYPE = 'PACKAGE' ");
                }
                SQL.append(" AND OWNER LIKE UPPER( :SCHEMANAME ) ");
                SQL.append(" AND OBJECT_NAME LIKE UPPER( :OBJNAME ) ");
                if (objType.equalsIgnoreCase("OTABLE"))
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
                if (objType.equalsIgnoreCase("OTRIGGER"))
                {
                    SQL.append(" MINUS ");
                    SQL.append(" SELECT MVIEW_NAME OPT_VALUE,MVIEW_NAME OPT_TEXT,'TABLE' AS OBJECT_TYPE FROM ALL_MVIEWS ");
                    SQL.append(" WHERE OWNER = UPPER(:SCHEMANAME) AND MVIEW_NAME LIKE UPPER(:OBJNAME)");
                }
                SQL.append(" ORDER BY OPT_VALUE");
                //Logger.DataLogger(SQL.toString() + " \n" + map.toString());
            }
            objNamelst = sqlService.getList(SCHEMALIASNAME, SQL.toString(), param);
        }
        return objNamelst;
    }

    public Map<String, List> getDBDefinition(DBMetadataEntityBean entityBean) throws SQLException, ClassNotFoundException
    {
        Map m = new DBDependencyDataManager().getDatabaseInfo(entityBean.getCmbDB());
        String serverAlias = m.get("serveralias").toString();
        String serverType = m.get("servertype").toString();
        String dbName = m.get("databasename").toString();
        String tblName = entityBean.getCmbObjName()[0];
        String objType = entityBean.getCmbObjType();
        Map<String, List> res = new HashMap<String, List>();
        if (objType.equalsIgnoreCase("MTABLE") || objType.equalsIgnoreCase("OTABLE"))
        {
            if (serverType.equalsIgnoreCase("mysql"))
            {
                res = mysqlFun(dbName, tblName, serverAlias);
            }
            else if (serverType.equalsIgnoreCase("oracle"))
            {
                res = oracleFun(dbName, tblName, serverAlias);
            }
        }
        return res;

    }

    private Map<String, List> mysqlFun(String databaseName, String tableName, String connAlias) throws SQLException, ClassNotFoundException
    {
        SqlRowSet srs, srs1;
        Map<String, List> main = new HashMap<String, List>();
        //Find Columns Metadata...
        StringBuilder query = new StringBuilder();

        query.append("SELECT COLUMN_NAME, DATA_TYPE, COLUMN_TYPE DATA_LENGTH, IS_NULLABLE, COLUMN_DEFAULT ");
        query.append("FROM INFORMATION_SCHEMA.COLUMNS ");
        query.append("WHERE table_name = '");
        query.append(tableName);
        query.append("' ");
        query.append("AND table_schema = '");
        query.append(databaseName);
        query.append("' ");
        query.append(" ORDER BY ORDINAL_POSITION");
        srs = sqlUtility.getRowSet(connAlias, query.toString());

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
        query.setLength(0);
        query.append("SELECT INDEX_NAME,GROUP_CONCAT(COLUMN_NAME) COLUMN_NAME ");
        query.append("FROM INFORMATION_SCHEMA.STATISTICS ");
        query.append("WHERE table_name ='");
        query.append(tableName);
        query.append("' ");
        query.append("AND table_schema ='");
        query.append(databaseName);
        query.append("' ");
        query.append("GROUP BY INDEX_NAME");

        srs = sqlUtility.getRowSet(connAlias, query.toString());
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
        query.append("' ");
        srs = sqlUtility.getRowSet(connAlias, query.toString());

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
            if (conType.equalsIgnoreCase("PRIMARY KEY"))
            {
                query.setLength(0);
                query.append("SELECT DISTINCT KCU.COLUMN_NAME ");
                query.append("FROM information_schema.TABLE_CONSTRAINTS TC ");
                query.append("INNER JOIN information_schema.KEY_COLUMN_USAGE KCU ON KCU.TABLE_SCHEMA = TC.TABLE_SCHEMA AND KCU.TABLE_NAME = TC.TABLE_NAME ");
                query.append("WHERE KCU.CONSTRAINT_NAME = 'PRIMARY' AND TC.table_name ='");
                query.append(tableName);
                query.append("' ");
                query.append("AND TC.table_schema  ='");
                query.append(databaseName);
                query.append("' ");

                SqlRowSet pkColumnName = sqlUtility.getRowSet(connAlias, query.toString());
                StringBuilder colName = new StringBuilder();

                while (pkColumnName.next())
                {
                    colName.append(pkColumnName.getString("COLUMN_NAME"));
                    colName.append(",");
                }
                if (colName.length() > 0)
                {
                    colName.deleteCharAt(colName.length() - 1);
                }
                conMap.put("Constraint_Column", colName.toString());
            }
            if (conType.equalsIgnoreCase("FOREIGN KEY"))
            {
                query.setLength(0);
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
                srs1 = sqlUtility.getRowSet(connAlias, query.toString());
                while (srs1.next())
                {
                    conMap.put("Referenced_Table", srs1.getString("REFERENCED_TABLE_NAME"));
                    conMap.put("Referenced_Column", srs1.getString("REFERENCED_COLUMN_NAME"));
                }

                query.setLength(0);
                query.append("SELECT DISTINCT COLUMN_NAME ");
                query.append("FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE ");
                query.append("WHERE CONSTRAINT_NAME = '");
                query.append(data);
                query.append("' AND REFERENCED_TABLE_NAME IS NOT NULL AND TABLE_SCHEMA = '");
                query.append(databaseName);
                query.append("' AND TABLE_NAME ='");
                query.append(tableName);
                query.append("'");
                SqlRowSet fkColumnName = sqlUtility.getRowSet(connAlias, query.toString());
                while (fkColumnName.next())
                {
                    conMap.put("Constraint_Column", fkColumnName.getString("COLUMN_NAME"));
                }
            }
            else if (conType.equalsIgnoreCase("UNIQUE"))
            {
                query.setLength(0);
                query.append("SELECT DISTINCT KCU.COLUMN_NAME ");
                query.append("FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS TC ");
                query.append("INNER JOIN INFORMATION_SCHEMA.KEY_COLUMN_USAGE KCU ON TC.CONSTRAINT_NAME = KCU.CONSTRAINT_NAME AND TC.CONSTRAINT_SCHEMA = KCU.CONSTRAINT_SCHEMA ");
                query.append("WHERE KCU.CONSTRAINT_NAME != 'PRIMARY' AND KCU.CONSTRAINT_SCHEMA = '");
                query.append(databaseName);
                query.append("' AND KCU.TABLE_NAME = '");
                query.append(tableName);
                query.append("' AND KCU.CONSTRAINT_NAME = '");
                query.append(data);
                query.append("'");

                srs1 = sqlUtility.getRowSet(connAlias, query.toString());
                StringBuilder colName = new StringBuilder();

                while (srs1.next())
                {
                    colName.append(srs1.getString("COLUMN_NAME"));
                    colName.append(",");
                    conMap.put("Constraint_Detail", "Unique Constraint On " + srs1.getString("COLUMN_NAME"));
                }
                if (colName.length() > 0)
                {
                    colName.deleteCharAt(colName.length() - 1);
                }
                conMap.put("Constraint_Column", colName.toString());
            }
            constList.add(conMap);
        }
        main.put("Constraints", constList);

        //Find Triggers..
        query.setLength(0);
        query.append("SELECT TRIGGER_NAME,CONCAT(ACTION_TIMING,' ',ACTION_ORIENTATION) TRIGGERING_EVENT,EVENT_MANIPULATION,'-' STATUS ");
        query.append(" FROM information_schema.TRIGGERS ");
        query.append("WHERE EVENT_OBJECT_TABLE ='");
        query.append(tableName);
        query.append("' ");
        query.append("AND trigger_schema  ='");
        query.append(databaseName);
        query.append("' ");
        srs = sqlUtility.getRowSet(connAlias, query.toString());

        List<Map> triList = new LinkedList<Map>();
        Map triMap = null;
        while (srs.next())
        {
            triMap = new HashMap();
            triMap.put("trigger_name", srs.getString("TRIGGER_NAME"));
            triMap.put("trigger_type", srs.getString("TRIGGERING_EVENT"));
            triMap.put("triggering_event", srs.getString("EVENT_MANIPULATION"));
            triMap.put("status", srs.getString("STATUS"));
            triList.add(triMap);
        }
        main.put("Triggers", triList);
        return main;
    }

    private Map<String, List> oracleFun(String databaseName, String tableName, String connAlias) throws SQLException, ClassNotFoundException
    {
        SqlRowSet srs, srsRef;
        Map<String, List> main = new HashMap<String, List>();
        //Find Columns Metadata...
        StringBuilder query = new StringBuilder();
        query.append("select COLUMN_NAME,DATA_TYPE,CASE WHEN DATA_TYPE = 'NUMBER' OR DATA_TYPE = 'FLOAT' THEN DATA_PRECISION ELSE DATA_LENGTH END DATA_LENGTH,NULLABLE,DATA_DEFAULT ");
        query.append(",CASE WHEN DATA_TYPE = 'NUMBER' OR DATA_TYPE = 'FLOAT' THEN DATA_SCALE ELSE NULL END DATA_SCALE ");
        query.append("from all_tab_columns ");
        query.append("where OWNER='");
        query.append(databaseName);
        query.append("' and TABLE_NAME= '");
        query.append(tableName);
        query.append("'");
        query.append(" ORDER BY COLUMN_ID");
        srs = sqlUtility.getRowSet(connAlias, query.toString());
        Map<String, String> colMap = null;
        List<Map> colList = new LinkedList<Map>();
        while (srs.next())
        {
            colMap = new LinkedHashMap<String, String>();
            colMap.put("COLUMN_NAME", srs.getString("COLUMN_NAME"));
            colMap.put("DATA_TYPE", srs.getString("DATA_TYPE"));
            if ((srs.getString("DATA_TYPE").equalsIgnoreCase("NUMBER")
                    || srs.getString("DATA_TYPE").equalsIgnoreCase("FLOAT"))
                    && srs.getString("DATA_SCALE") != null)
            {
                colMap.put("DATA_LENGTH", srs.getString("DATA_LENGTH") + "," + srs.getString("DATA_SCALE"));
            }
            else
            {
                colMap.put("DATA_LENGTH", srs.getString("DATA_LENGTH"));
            }
            colMap.put("NULLABLE", srs.getString("NULLABLE"));
            colMap.put("DATA_DEFAULT", srs.getString("DATA_DEFAULT"));
            colList.add(colMap);
        }
        main.put("Columns", colList);

        //Find indexes..
        query.setLength(0);
        query.append("SELECT DISTINCT INDEX_NAME ");
        query.append("FROM ALL_IND_COLUMNS ");
        query.append("WHERE TABLE_OWNER='");
        query.append(databaseName);
        query.append("' and TABLE_NAME = '");
        query.append(tableName);
        query.append("'");

        srs = sqlUtility.getRowSet(connAlias, query.toString());
        List<Map> indList = new LinkedList<Map>();
        String data;
        Map<String, String> indMap = null;
        while (srs.next())
        {
            query.setLength(0);
            query.append("SELECT COLUMN_NAME ");
            query.append("FROM ALL_IND_COLUMNS ");
            query.append("WHERE TABLE_OWNER='");
            query.append(databaseName);
            query.append("' and TABLE_NAME = '");
            query.append(tableName);
            query.append("' AND INDEX_NAME = '");
            query.append(srs.getString("INDEX_NAME"));
            query.append("'");

            SqlRowSet indexColumn = sqlUtility.getRowSet(connAlias, query.toString());
            StringBuilder colName = new StringBuilder();
            while (indexColumn.next())
            {
                colName.append(indexColumn.getString("COLUMN_NAME"));
                colName.append(",");
            }
            if (colName.length() > 1)
            {
                colName.deleteCharAt(colName.length() - 1);
            }
            indMap = new LinkedHashMap<String, String>();
            indMap.put("INDEX_NAME", srs.getString("INDEX_NAME"));
            indMap.put("COLUMN_NAME", colName.toString());
            indList.add(indMap);
        }
        main.put("Indexes", indList);

        //Find Constraints..
        query.setLength(0);
        query.append("SELECT CONSTRAINT_NAME,CONSTRAINT_TYPE ");
        query.append("FROM ALL_CONSTRAINTS ");
        query.append("WHERE OWNER='");
        query.append(databaseName);
        query.append("' and TABLE_NAME  = '");
        query.append(tableName);
        query.append("'");

        srs = sqlUtility.getRowSet(connAlias, query.toString());
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
                query.setLength(0);
                query.append("SELECT SEARCH_CONDITION FROM ALL_CONSTRAINTS ");
                query.append("WHERE TABLE_NAME = '");
                query.append(tableName);
                query.append("' AND CONSTRAINT_TYPE = 'C' AND OWNER = '");
                query.append(databaseName);
                query.append("' AND CONSTRAINT_NAME = '");
                query.append(data);
                query.append("'");

                srsRef = sqlUtility.getRowSet(connAlias, query.toString());
                while (srsRef.next())
                {
                    conMap.put("Constraint_Detail", srsRef.getString("SEARCH_CONDITION"));
                }
            }
            else if ("P".equalsIgnoreCase(conType))
            {
                query.setLength(0);
                query.append("SELECT AC.CONSTRAINT_NAME,AC.CONSTRAINT_TYPE,UCC.COLUMN_NAME ");
                query.append("FROM ALL_CONSTRAINTS AC ");
                query.append("INNER JOIN ALL_CONS_COLUMNS UCC ON UCC.OWNER = AC.OWNER AND UCC.TABLE_NAME = AC.TABLE_NAME ");
                query.append("WHERE AC.OWNER = '");
                query.append(databaseName);
                query.append("' AND AC.TABLE_NAME = '");
                query.append(tableName);
                query.append("' AND CONSTRAINT_TYPE = 'P' AND UCC.CONSTRAINT_NAME = AC.CONSTRAINT_NAME ");

                SqlRowSet pkColumnName = sqlUtility.getRowSet(connAlias, query.toString());
                StringBuilder colName = new StringBuilder();

                while (pkColumnName.next())
                {
                    colName.append(pkColumnName.getString("COLUMN_NAME"));
                    colName.append(",");
                }
                if (colName.length() > 1)
                {
                    colName.deleteCharAt(colName.length() - 1);
                }
                conMap.put("Constraint_Column", colName.toString());
                conTp = "Primary Key Constraint";
            }
            else if ("U".equalsIgnoreCase(conType))
            {
                conTp = "Unique Key Constraint";
                query.setLength(0);
                query.append("SELECT ACC.COLUMN_NAME FROM ALL_CONSTRAINTS AC  ");
                query.append("INNER JOIN ALL_CONS_COLUMNS ACC ON AC.OWNER = ACC.OWNER AND AC.CONSTRAINT_NAME = ACC.CONSTRAINT_NAME ");
                query.append("WHERE AC.CONSTRAINT_TYPE = 'U' AND AC.OWNER = '");
                query.append(databaseName);
                query.append("' AND ACC.TABLE_NAME = '");
                query.append(tableName);
                query.append("' AND ACC.CONSTRAINT_NAME = '");
                query.append(data);
                query.append("'");

                srsRef = sqlUtility.getRowSet(connAlias, query.toString());
                StringBuilder colName = new StringBuilder();

                while (srsRef.next())
                {
                    conMap.put("Constraint_Detail", "Unique Constraint On " + srsRef.getString("COLUMN_NAME"));
                    colName.append(srsRef.getString("COLUMN_NAME"));
                    colName.append(",");
                }
                if (colName.length() > 1)
                {
                    colName.deleteCharAt(colName.length() - 1);
                }
                conMap.put("Constraint_Column", colName.toString());
            }
            else if ("R".equalsIgnoreCase(conType))
            {
                conTp = "Foreign Key Constraint";
                query.setLength(0);
                query.append("SELECT UCC.TABLE_NAME,UCC.COLUMN_NAME ");
                query.append("FROM ALL_CONSTRAINTS  UC,ALL_CONS_COLUMNS UCC ");
                query.append("WHERE UC.R_CONSTRAINT_NAME = UCC.CONSTRAINT_NAME AND UC.CONSTRAINT_TYPE = 'R' AND UC.OWNER = '");
                query.append(databaseName);
                query.append("' AND UCC.OWNER = '");
                query.append(databaseName);
                query.append("' AND UC.CONSTRAINT_NAME ='");
                query.append(data);
                query.append("'");

                srsRef = sqlUtility.getRowSet(connAlias, query.toString());
                while (srsRef.next())
                {
                    conMap.put("Referenced_Table", srsRef.getString("TABLE_NAME"));
                    conMap.put("Referenced_Column", srsRef.getString("COLUMN_NAME"));
                }

                query.setLength(0);
                query.append("SELECT ACC1.COLUMN_NAME ");
                query.append("FROM ALL_CONSTRAINTS AC ");
                query.append("INNER JOIN ALL_CONS_COLUMNS ACC ON ACC.OWNER = AC.OWNER AND ACC.CONSTRAINT_NAME = AC.CONSTRAINT_NAME ");
                query.append("LEFT JOIN ALL_CONS_COLUMNS ACC1 ON ACC1.CONSTRAINT_NAME = AC.R_CONSTRAINT_NAME ");
                query.append("WHERE AC.CONSTRAINT_NAME = '");
                query.append(data);
                query.append("' AND AC.OWNER = '");
                query.append(databaseName);
                query.append("' AND AC.TABLE_NAME ='");
                query.append(tableName);
                query.append("'");

                srsRef = sqlUtility.getRowSet(connAlias, query.toString());
                StringBuilder colName = new StringBuilder();

                while (srsRef.next())
                {
                    colName.append(srsRef.getString("COLUMN_NAME"));
                    colName.append(",");
                }
                if (colName.length() > 1)
                {
                    colName.deleteCharAt(colName.length() - 1);
                }
                conMap.put("Constraint_Column", colName.toString());
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
        query.setLength(0);
        query.append("SELECT DISTINCT trigger_name,trigger_type,triggering_event,status ");
        query.append("FROM all_triggers ");
        query.append("WHERE OWNER='");
        query.append(databaseName);
        query.append("' and TABLE_NAME = '");
        query.append(tableName);
        query.append("'");

        srs = sqlUtility.getRowSet(connAlias, query.toString());
        List<Map> triList = new LinkedList<Map>();
        Map triMap = null;
        while (srs.next())
        {
            triMap = new HashMap();
            triMap.put("trigger_name", srs.getString("trigger_name"));
            triMap.put("trigger_type", srs.getString("trigger_type"));
            triMap.put("triggering_event", srs.getString("triggering_event"));
            triMap.put("status", srs.getString("status"));
            triList.add(triMap);
        }
        main.put("Triggers", triList);
        return main;
    }

    //to get table dependencies
    public SqlRowSet getAllServers() throws ClassNotFoundException, SQLException
    {
        return sqlUtility.getRowSet(ALIASNAME, "SELECT SERVER_ID, SERVER_NAME FROM SERVER_MASTER");
    }

    public String getDataBaseName(String dbId) throws ClassNotFoundException, SQLException
    {
        return sqlUtility.getString(ALIASNAME, "SELECT DATABASE_NAME FROM DATABASE_MASTER WHERE DATABASE_ID = " + dbId);
    }

    public String getObjDefination(String objType, String[] objName, String dbId, String hdnObjType) throws ClassNotFoundException, SQLException
    {
        StringBuilder text = new StringBuilder();
        StringBuilder query = new StringBuilder();

        String dbName = getDataBaseName(dbId);
        String SCHEMALIASNAME = "";
        Map m = new DBDependencyDataManager().getDatabaseInfo(dbId);
        SCHEMALIASNAME = m.get("serveralias").toString();
        String pattern = "";
        Map map = new HashMap();
        for (int i = 0; i < objName.length; i++)
        {
            if (objName[i].equals("-1"))
            {
                continue;
            }
            map.clear();
            pattern = "\n-----------------------------------" + "\n---------  " + objName[i] + "\n----------------------------------- \n";
            map.put("OBJNAME", objName[i]);
            map.put("SCHEMANAME", dbName);
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
            else if (objType.equalsIgnoreCase("OTABLE") && !hdnObjType.equalsIgnoreCase("MVIEW"))
            {
                query.append("SELECT TEXT FROM ALL_VIEWS ");
                query.append("WHERE VIEW_NAME = :OBJNAME AND OWNER = :SCHEMANAME");
            }
            else if (objType.equalsIgnoreCase("OTABLE") && hdnObjType.equalsIgnoreCase("MVIEW"))
            {
                query.append("SELECT QUERY FROM ALL_MVIEWS ");
                query.append("WHERE MVIEW_NAME = :OBJNAME AND OWNER = :SCHEMANAME");
            }
            else if (objType.equalsIgnoreCase("OPACKAGEBODY"))
            {
                query.append("SELECT TEXT FROM ALL_SOURCE WHERE NAME = :OBJNAME ");
                query.append(" AND OWNER = :SCHEMANAME AND TYPE = 'PACKAGE BODY' ");
            }
            else if (objType.equalsIgnoreCase("OPACKAGE"))
            {
                query.append("SELECT TEXT FROM ALL_SOURCE WHERE NAME = :OBJNAME ");
                query.append(" AND OWNER = :SCHEMANAME AND TYPE = 'PACKAGE' ");
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
            else if (objType.equalsIgnoreCase("MTABLE"))
            {
                query.append(" SELECT VIEW_DEFINITION FROM INFORMATION_SCHEMA.VIEWS ");
                query.append(" WHERE TABLE_SCHEMA = :SCHEMANAME  AND TABLE_NAME = :OBJNAME");
            }

            SqlRowSet rs = sqlUtility.getRowSet(SCHEMALIASNAME, query.toString(), param);
            text.append(pattern);

            while (rs.next())
            {
                text.append(rs.getString(1));
            }
            text.append("\nGO");
        }
        return text.toString();
    }

    public String getTriggerDef(String dbId, String objType, String[] objName) throws ClassNotFoundException, SQLException
    {
        StringBuilder query = new StringBuilder();

        String dbName = getDataBaseName(dbId);
        String SCHEMALIASNAME = "";
        Map m = new DBDependencyDataManager().getDatabaseInfo(dbId);
        SCHEMALIASNAME = m.get("serveralias").toString();

        Map map = new HashMap();
        StringBuilder triggerText = new StringBuilder();
        String pattern = "";
        map.clear();
        map.put("OBJNAME", objName[0]);
        map.put("SCHEMANAME", dbName);
        param = new MapSqlParameterSource(map);

        if (objType.equalsIgnoreCase("MTRIGGER"))
        {
            query.append("SELECT CONCAT(TRIGGER_NAME,' (',ACTION_TIMING,' ',EVENT_MANIPULATION,')') TNAME,ACTION_STATEMENT TEXT FROM INFORMATION_SCHEMA.TRIGGERS ");
            query.append(" WHERE TRIGGER_SCHEMA = :SCHEMANAME AND TRIGGER_NAME = :OBJNAME");
        }
        else if (objType.equalsIgnoreCase("OTRIGGER"))
        {
            query.append("SELECT TRIGGER_NAME||' ('||TRIGGER_TYPE||' '||TRIGGERING_EVENT||')' TNAME,TRIGGER_BODY TEXT FROM ALL_TRIGGERS ");
            query.append(" WHERE OWNER = :SCHEMANAME AND TABLE_NAME = :OBJNAME");
        }

        SqlRowSet srs = sqlUtility.getRowSet(SCHEMALIASNAME, query.toString(), param);
        while (srs.next())
        {
            pattern = "\n------------------------------------------------" + "\n------- " + srs.getString("TNAME") + "\n------------------------------------------------ \n";
            triggerText.append(pattern);
            triggerText.append(srs.getString("TEXT"));
            triggerText.append("\nGO");
        }
        return triggerText.toString();
    }

    public List getSequenceDef(String dbId, String objType, String[] objName) throws ClassNotFoundException, SQLException
    {
        StringBuilder query = new StringBuilder();
        String dbName = getDataBaseName(dbId);
        Map m = new DBDependencyDataManager().getDatabaseInfo(dbId);
        String SCHEMALIASNAME = m.get("serveralias").toString();

        Map map = new HashMap();
        map.clear();
        map.put("OBJNAME", objName[0]);
        map.put("SCHEMANAME", dbName);
        param = new MapSqlParameterSource(map);

        query.append("SELECT SEQUENCE_OWNER,SEQUENCE_NAME,MIN_VALUE,MAX_VALUE,INCREMENT_BY,CYCLE_FLAG,ORDER_FLAG,CACHE_SIZE,LAST_NUMBER FROM ALL_SEQUENCES ");
        query.append(" WHERE SEQUENCE_NAME = :OBJNAME AND SEQUENCE_OWNER = :SCHEMANAME ");
        return sqlUtility.getList(SCHEMALIASNAME, query.toString(), param);
    }
}
