/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.business.finstudio.findatareqexecutor;

import com.finlogic.util.Logger;
import com.finlogic.util.dbm.DBLogUtility;
import com.finlogic.util.findatareqexecutor.DataExportUtility;
import com.finlogic.util.findatareqexecutor.DependencyInfoBean;
import com.finlogic.util.findatareqexecutor.QueryInfoBean;
import com.finlogic.util.findatareqexecutor.QueryMaker;
import com.finlogic.util.findatareqexecutor.ServerConnection;
import com.finlogic.util.findatareqexecutor.ServerPropertyReader;
import com.finlogic.util.persistence.SQLTranUtility;
import com.finlogic.util.persistence.SQLUtility;
import com.finlogic.util.properties.HardCodeProperty;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

/**
 *
 * @author Sonam Patel
 */
public class FinDataReqExecutorDataManager
{

    private static final String ALIASNAME = "finstudio_mysql";
    private static final String WFMALIASNAME = "wfm";
    private static SQLUtility sqlUtility = new SQLUtility();

    public List getAddProjects() throws ClassNotFoundException, SQLException
    {
        StringBuilder query;
        query = new StringBuilder();
        query.append("SELECT PRJ_ID, PRJ_NAME, DOMAIN_NAME FROM PROJECT_MST WHERE ISACTIVE = 'Y' ORDER BY PRJ_NAME");
        return sqlUtility.getList(WFMALIASNAME, query.toString());
    }

    public List getAddRequests(final String reqFltr, final String project) throws ClassNotFoundException, SQLException
    {
        StringBuilder query;
        query = new StringBuilder();
        query.append("SELECT WORK_REQUEST_ID, TITLE FROM WORK_REQUEST_MST ");
        query.append("WHERE STATUS_ID IN (12, 14, 61) ");
        query.append("AND POST_DATE >= DATE_ADD(SUBSTRING(CAST(SYSDATE() AS CHAR), 1, 10), INTERVAL -1 YEAR) ");

        MapSqlParameterSource param = null;
        HashMap hmap = new HashMap();
        if (reqFltr != null)
        {
            hmap.put("reqFltr", reqFltr + "%");
            query.append("AND WORK_REQUEST_ID LIKE :reqFltr ");
        }
        else
        {
            hmap.put("project", project);
            query.append("AND PRJ_ID = :project ");
        }
        param = new MapSqlParameterSource(hmap);

        query.append("ORDER BY WORK_REQUEST_ID");
        return sqlUtility.getList(WFMALIASNAME, query.toString(), param);
    }

    public SqlRowSet checkProjectUserMapping(final String empCode) throws ClassNotFoundException, SQLException
    {
        StringBuilder query;
        query = new StringBuilder();
        query.append("SELECT PM.PRJ_ID FROM RESOURCE_PROJECT_MAP RPM ");
        query.append("INNER JOIN RESOURCE_MST RM ON RM.RESOURCE_ID = RPM.RESOURCE_ID ");
        query.append("INNER JOIN PROJECT_MST PM ON PM.PRJ_ID = RPM.PRJ_ID ");
        query.append("WHERE RM.MECODE = '").append(empCode).append("'");
        return sqlUtility.getRowSet(WFMALIASNAME, query.toString());
    }

    public List getActualServers(final String project) throws ClassNotFoundException, SQLException
    {
        StringBuilder query;
        query = new StringBuilder();
        query.append("SELECT DBMAST.DATABASE_ID, SERVER_NAME, SERVER_TYPE, DATABASE_NAME ");
        query.append("FROM DATABASE_MASTER DBMAST ");
        query.append("INNER JOIN PROJECT_DB_MAPPING PDMAP ON PDMAP.DATABASE_ID = DBMAST.DATABASE_ID ");
        query.append("INNER JOIN SERVER_MASTER SMAST ON SMAST.SERVER_ID = DBMAST.SERVER_ID ");
        query.append("WHERE PDMAP.PROJECT_ID = ").append(project);
        query.append(" ORDER BY UPPER(SERVER_NAME), UPPER(DATABASE_NAME)");
        return sqlUtility.getList(ALIASNAME, query.toString());
    }

    public SqlRowSet checkProjectDatabaseMapping(final String project, final String dbId) throws ClassNotFoundException, SQLException
    {
        StringBuilder query;
        query = new StringBuilder();
        query.append("SELECT (CASE WHEN COUNT(*) > 0 THEN 'Y' ELSE 'N' END) RESULT FROM DATABASE_MASTER DBMAST ");
        query.append("INNER JOIN PROJECT_DB_MAPPING PDMAP ON PDMAP.DATABASE_ID = DBMAST.DATABASE_ID ");
        query.append("WHERE PDMAP.PROJECT_ID = ").append(project).append(" AND DBMAST.DATABASE_ID = ").append(dbId);
        return sqlUtility.getRowSet(ALIASNAME, query.toString());
    }

    public SqlRowSet getDbTypeName(final String dbID) throws ClassNotFoundException, SQLException
    {
        StringBuilder query;
        query = new StringBuilder();
        query.append("SELECT SMAST.SERVER_ID, SERVER_TYPE, DATABASE_NAME FROM DATABASE_MASTER DBMAST ");
        query.append("INNER JOIN SERVER_MASTER SMAST ON SMAST.SERVER_ID = DBMAST.SERVER_ID ");
        query.append("WHERE DATABASE_ID = ").append(dbID);
        return sqlUtility.getRowSet(ALIASNAME, query.toString());
    }

    public SqlRowSet getServerID(final String dbID) throws ClassNotFoundException, SQLException
    {
        StringBuilder query;
        query = new StringBuilder();
        query.append("SELECT SERVER_ID FROM DATABASE_MASTER WHERE DATABASE_ID = ").append(dbID);
        return sqlUtility.getRowSet(ALIASNAME, query.toString());
    }

    public List getViewProjects() throws ClassNotFoundException, SQLException
    {
        StringBuilder query;
        query = new StringBuilder();
        query.append("SELECT PRJ_ID, PRJ_NAME FROM PROJECT_MST WHERE DOMAIN_NAME IS NOT NULL ORDER BY PRJ_NAME");
        SqlRowSet prjList = sqlUtility.getRowSet(WFMALIASNAME, query.toString());

        query.setLength(0);
        query.append("SELECT DISTINCT PROJ_ID FROM DATA_REQUEST_MASTER");
        SqlRowSet drePrjList = sqlUtility.getRowSet(ALIASNAME, query.toString());

        List ls = null;
        if (prjList != null && drePrjList != null)
        {
            Map m = null;
            ls = new ArrayList();
            while (prjList.next())
            {
                drePrjList.beforeFirst();
                while (drePrjList.next())
                {
                    if (drePrjList.getString("PROJ_ID").equalsIgnoreCase(prjList.getString("PRJ_ID")))
                    {
                        m = new LinkedHashMap();
                        m.put("PRJ_ID", prjList.getString("PRJ_ID"));
                        m.put("PRJ_NAME", prjList.getString("PRJ_NAME"));
                        ls.add(m);
                    }
                }
            }
        }
        return ls;
    }

    public List getViewEmployees() throws ClassNotFoundException, SQLException
    {
        StringBuilder query;
        query = new StringBuilder();
        query.append("SELECT DISTINCT EMP_CODE, EMP_NAME FROM NJHR.EMP_MAST ");
        query.append("INNER JOIN DATA_REQUEST_MASTER ON ENTRY_BY = EMP_CODE ");
        query.append("ORDER BY UPPER(EMP_NAME)");
        return sqlUtility.getList(ALIASNAME, query.toString());
    }

    public List getViewRequests(final String project, final String reqFltr) throws ClassNotFoundException, SQLException
    {
        StringBuilder query;
        query = new StringBuilder();
        query.append("SELECT DISTINCT REQ_ID FROM DATA_REQUEST_MASTER");
        query.append(" WHERE 1 = 1");

        MapSqlParameterSource param = null;
        HashMap hmap = new HashMap();
        if (!"-1".equals(project))
        {
            hmap.put("project", project);
            query.append(" AND PROJ_ID = :project");
        }
        if (reqFltr != null)
        {
            hmap.put("reqFltr", reqFltr + "%");
            query.append(" AND REQ_ID LIKE :reqFltr");
        }
        param = new MapSqlParameterSource(hmap);

        query.append(" ORDER BY REQ_ID");
        return sqlUtility.getList(ALIASNAME, query.toString(), param);
    }

    public Map<String, String[]> validateQueries(final String server, final String queryText) throws ClassNotFoundException, SQLException
    {
        String[] queries = queryText.split("\nGO\n");
        String[] results = new String[queries.length];
        String[] status = new String[queries.length];
        String[] startTime = new String[queries.length];
        String[] endTime = new String[queries.length];
        Map<String, String[]> map = new HashMap<String, String[]>();
        if (server.equals(""))
        {
            for (int i = 0; i < queries.length; i++)
            {
                results[i] = "-";
                status[i] = "-";
                startTime[i] = "-";
                endTime[i] = "-";
            }
        }
        else
        {
            Connection conn = null;
            com.finlogic.util.persistence.finstudio.SQLTranUtility tran = new com.finlogic.util.persistence.finstudio.SQLTranUtility();
            try
            {
                conn = ServerConnection.getConnection(server);
                tran.openConn(conn);

                for (int i = 0; i < queries.length; i++)
                {
                    queries[i] = queries[i].trim();
                    results[i] = "";
                    status[i] = "";
                    startTime[i] = "";
                    endTime[i] = "";

                    try
                    {
                        startTime[i] = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(Calendar.getInstance().getTime());
                        results[i] = tran.persist(queries[i]) + " Records Affected.";
                        endTime[i] = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(Calendar.getInstance().getTime());
                        status[i] = "PASS";
                    }
                    catch (Exception e)
                    {
                        results[i] = e.getMessage();
                        endTime[i] = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(Calendar.getInstance().getTime());
                        status[i] = "FAIL";
                    }
                }
            }
            finally
            {
                if (conn != null && !conn.isClosed())
                {
                    tran.rollbackChanges();
                    tran.closeConn();
                }
            }
        }
        map.put("results", results);
        map.put("status", status);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        return map;
    }

    public void insertTempData(final FinDataReqExecutorEntityBean entityBean, final Map qBeanMap, final String dbType) throws ClassNotFoundException, SQLException
    {
        String dbID = entityBean.getDatabaseID();
        //Get Maximum Batch ID
        String batchQuery = "SELECT IFNULL(MAX(BATCH_TMP_ID)+1,1) BATCH_TMP_ID FROM DATA_REQUEST_TMP";
        //Get QUERY_TMP_ID Of Last Inserted Record
        String selectQuery = "SELECT QUERY_TMP_ID FROM DATA_REQUEST_TMP ORDER BY QUERY_TMP_ID DESC LIMIT 1";
        StringBuilder insQuery;
        insQuery = new StringBuilder();
        int batchId = 1;
        batchId = sqlUtility.getInt(ALIASNAME, batchQuery);

        //Get All Actual Servers From SERVER_MASTER
        SqlRowSet serverRec = sqlUtility.getRowSet(ALIASNAME, "SELECT SERVER_ID, SERVER_NAME FROM SERVER_MASTER");
        List allServers = new ArrayList();
        if (serverRec != null)
        {
            while (serverRec.next())
            {
                allServers.add(serverRec.getString("SERVER_ID") + ":" + serverRec.getString("SERVER_NAME"));
            }
        }

        String mysqlSchema = "";
        QueryEntityBean[] queryBean = entityBean.getQueryBean();
        MapSqlParameterSource param = null;
        for (int i = 0; i < queryBean.length; i++)
        {
            if (i == 0 && "MYSQL".equals(dbType))
            {
                String str = queryBean[i].getQuery().replaceAll("\\s{1,}", " ").trim();
                while (str.endsWith(";"))
                {
                    str = str.substring(0, str.length() - 1).trim();
                }
                if ("USE".equals(str.split(" ")[0]))
                {
                    mysqlSchema = str.substring(4);
                }
            }
            HashMap hmap = new HashMap();
            hmap.put("uSessionId", entityBean.getuSessionId());
            hmap.put("databaseID", entityBean.getDatabaseID());
            if (entityBean.getSyntaxVerifiedOn() != null && !"".equals(entityBean.getSyntaxVerifiedOn()))
            {
                hmap.put("syntaxVerifiedOn", entityBean.getSyntaxVerifiedOn());
            }
            hmap.put("query", queryBean[i].getQuery());
            hmap.put("result", queryBean[i].getDevExeResult());
            hmap.put("exeStatus", queryBean[i].getDevExeStatus());
            hmap.put("startTime", queryBean[i].getDevExeStartTime());
            hmap.put("endTime", queryBean[i].getDevExeEndTime());
            String backup = "NA", logTable = "NA";
            if (queryBean[i].getQuery().trim().toUpperCase().startsWith("UPDATE ") || queryBean[i].getQuery().trim().toUpperCase().startsWith("DELETE "))
            {
                backup = "Backup Not Required";
                if (qBeanMap != null)
                {
                    QueryInfoBean qBean = null;
                    qBean = (QueryInfoBean) qBeanMap.get(i);
                    if (qBean != null && qBean.isNeedConfirmation() && !qBean.isIsWherePresent())
                    {
                        backup = "Backup Not Possible";
                    }
                }
            }
            else if (queryBean[i].getQuery().trim().toUpperCase().startsWith("CREATE OR REPLACE ") || queryBean[i].getQuery().trim().toUpperCase().startsWith("DROP"))
            {
                backup = "Backup Required";
            }
            else if (queryBean[i].getQuery().trim().toUpperCase().startsWith("CREATE TABLE "))
            {
                logTable = "Log Table Not Required";
            }
            hmap.put("backup", backup);
            hmap.put("logTable", logTable);
            param = new MapSqlParameterSource(hmap);
            insQuery.append("INSERT INTO DATA_REQUEST_TMP(BATCH_TMP_ID, U_SESSION_ID, DATABASE_ID, SYNTAX_VERIFIED_ON, ");
            insQuery.append("QUERY_TEXT, DEV_EXE_RESULT, DEV_EXE_START_DATE, DEV_EXE_END_DATE, DEV_EXE_STATUS, BACKUP, LOG_TABLE) VALUES(");
            insQuery.append(batchId).append(", :uSessionId, :databaseID, ");
            if (entityBean.getSyntaxVerifiedOn() == null || "".equals(entityBean.getSyntaxVerifiedOn()))
            {
                insQuery.append("'-'");
            }
            else
            {
                insQuery.append(":syntaxVerifiedOn");
            }
            insQuery.append(", :query, :result ,:startTime ,:endTime, :exeStatus, :backup, :logTable)");
            sqlUtility.persist(ALIASNAME, insQuery.toString(), param);
            int queryID = sqlUtility.getInt(ALIASNAME, selectQuery);
            insQuery.delete(0, insQuery.length());

            try
            {
                if (qBeanMap != null && qBeanMap.get(i) != null)
                {
                    DBDependencyAnalyzer depMgr = new DBDependencyAnalyzer();
                    QueryInfoBean qBean = (QueryInfoBean) qBeanMap.get(i);

                    //Sensitive Object Dependency Check
                    List<DependencyInfoBean> depBeanList = depMgr.isSensitive(dbID, qBean);
                    if (depBeanList != null && depBeanList.size() > 0)
                    {
                        //Insert Dependency into DATA_REQUEST_DEPENDENCY_TMP
                        for (int j = 0; j < depBeanList.size(); j++)
                        {
                            DependencyInfoBean depBean = depBeanList.get(j);
                            insQuery.append("INSERT INTO DATA_REQUEST_DEPENDENCY_TMP(QUERY_TMP_ID, DEPENDENCY, SERVER_NAME, SCHEMA_NAME");
                            if (depBean.getDepObjType() != null)
                            {
                                insQuery.append(", OBJ_TYPE");
                            }
                            if (depBean.getDepObjName() != null)
                            {
                                insQuery.append(", OBJ_NAME");
                            }
                            if (depBean.getDepColName() != null)
                            {
                                insQuery.append(", COLUMN_NAME");
                            }
                            insQuery.append(") VALUES(");
                            insQuery.append(queryID).append(", '");
                            insQuery.append(depBean.getDependency()).append("', '");
                            insQuery.append(depBean.getDepServer()).append("', '");
                            insQuery.append(depBean.getDepSchema()).append("'");
                            if (depBean.getDepObjType() != null)
                            {
                                insQuery.append(", '").append(depBean.getDepObjType()).append("'");
                            }
                            if (depBean.getDepObjName() != null)
                            {
                                insQuery.append(", '").append(depBean.getDepObjName()).append("'");
                            }
                            if (depBean.getDepColName() != null)
                            {
                                insQuery.append(", '").append(depBean.getDepColName()).append("'");
                            }
                            insQuery.append(")");

                            sqlUtility.persist(ALIASNAME, insQuery.toString());
                            insQuery.delete(0, insQuery.length());
                        }
                    }
                    depBeanList = null;

                    if (qBean != null && qBean.getClause() != null
                            && ("ALTER".equals(qBean.getClause()) || "DROP".equals(qBean.getClause())
                            || "UPDATE".equals(qBean.getClause()) || "DELETE".equals(qBean.getClause())))
                    {
                        if (allServers != null && allServers.size() > 0)
                        {
                            if ("ALTER".equals(qBean.getClause()) || "DROP".equals(qBean.getClause()))
                            {
                                //Oracle-Dependency Check
                                depBeanList = depMgr.checkOracleDep(allServers, qBean.getDbObjInfo());
                                if (depBeanList != null && depBeanList.size() > 0)
                                {
                                    //Insert Dependency into DATA_REQUEST_DEPENDENCY_TMP
                                    for (int j = 0; j < depBeanList.size(); j++)
                                    {
                                        DependencyInfoBean depBean = depBeanList.get(j);
                                        insQuery.append("INSERT INTO DATA_REQUEST_DEPENDENCY_TMP(QUERY_TMP_ID, DEPENDENCY, SERVER_NAME, SCHEMA_NAME, OBJ_TYPE, OBJ_NAME) VALUES(");
                                        insQuery.append(queryID).append(",'").append(depBean.getDependency()).append("', '");
                                        insQuery.append(depBean.getDepServer()).append("', '");
                                        insQuery.append(depBean.getDepSchema()).append("', '");
                                        insQuery.append(depBean.getDepObjType()).append("', '");
                                        insQuery.append(depBean.getDepObjName()).append("')");
                                        sqlUtility.persist(ALIASNAME, insQuery.toString());
                                        insQuery.delete(0, insQuery.length());
                                    }
                                }
                                qBean.setDepInfo(null);
                                depBeanList = null;
                            }

                            if (("ALTER".equals(qBean.getClause()) || "DROP".equals(qBean.getClause())) && "ORACLE".equals(dbType))
                            {
                                //Oracle-Log Tables Dependency Check
                                depBeanList = depMgr.checkOracleLogTableDep(allServers, qBean.getDbObjInfo());
                                if (depBeanList != null && depBeanList.size() > 0)
                                {
                                    //Insert Dependency into DATA_REQUEST_DEPENDENCY_TMP
                                    for (int j = 0; j < depBeanList.size(); j++)
                                    {
                                        DependencyInfoBean depBean = depBeanList.get(j);
                                        insQuery.append("INSERT INTO DATA_REQUEST_DEPENDENCY_TMP(QUERY_TMP_ID, DEPENDENCY, SERVER_NAME, SCHEMA_NAME, OBJ_TYPE, OBJ_NAME) VALUES(");
                                        insQuery.append(queryID).append(",'").append(depBean.getDependency()).append("', '");
                                        insQuery.append(depBean.getDepServer()).append("', '");
                                        insQuery.append(depBean.getDepSchema()).append("', '");
                                        insQuery.append(depBean.getDepObjType()).append("', '");
                                        insQuery.append(depBean.getDepObjName()).append("')");
                                        sqlUtility.persist(ALIASNAME, insQuery.toString());
                                        insQuery.delete(0, insQuery.length());
                                    }
                                }
                                qBean.setDepInfo(null);
                                depBeanList = null;
                            }

                            if ("ALTER".equals(qBean.getClause()) || "DROP".equals(qBean.getClause()))
                            {
                                //Oracle NJTRAN ETL Dependency Check
                                depBeanList = depMgr.checkOracleNjtranETLDep(allServers, qBean.getDbObjInfo());
                                if (depBeanList != null && depBeanList.size() > 0)
                                {
                                    //Insert Dependency into DATA_REQUEST_DEPENDENCY_TMP
                                    for (int j = 0; j < depBeanList.size(); j++)
                                    {
                                        DependencyInfoBean depBean = depBeanList.get(j);
                                        insQuery.append("INSERT INTO DATA_REQUEST_DEPENDENCY_TMP(QUERY_TMP_ID, DEPENDENCY, SERVER_NAME, SCHEMA_NAME, OBJ_TYPE, OBJ_NAME) VALUES(");
                                        insQuery.append(queryID).append(",'").append(depBean.getDependency()).append("', '");
                                        insQuery.append(depBean.getDepServer()).append("', '");
                                        insQuery.append(depBean.getDepSchema()).append("', '");
                                        insQuery.append(depBean.getDepObjType()).append("', '");
                                        insQuery.append(depBean.getDepObjName()).append("')");
                                        sqlUtility.persist(ALIASNAME, insQuery.toString());
                                        insQuery.delete(0, insQuery.length());
                                    }
                                }
                                qBean.setDepInfo(null);
                                depBeanList = null;
                            }

                            if ("ALTER".equals(qBean.getClause()) || "DROP".equals(qBean.getClause()))
                            {
                                //Oracle NJTRAN Incremental-DataUpload Dependency Check
                                depBeanList = depMgr.checkOracleNjtranIncreDataUploadDep(allServers, qBean.getDbObjInfo());
                                if (depBeanList != null && depBeanList.size() > 0)
                                {
                                    //Insert Dependency into DATA_REQUEST_DEPENDENCY_TMP
                                    for (int j = 0; j < depBeanList.size(); j++)
                                    {
                                        DependencyInfoBean depBean = depBeanList.get(j);
                                        insQuery.append("INSERT INTO DATA_REQUEST_DEPENDENCY_TMP(QUERY_TMP_ID, DEPENDENCY, SERVER_NAME, SCHEMA_NAME, OBJ_TYPE, OBJ_NAME) VALUES(");
                                        insQuery.append(queryID).append(",'").append(depBean.getDependency()).append("', '");
                                        insQuery.append(depBean.getDepServer()).append("', '");
                                        insQuery.append(depBean.getDepSchema()).append("', '");
                                        insQuery.append(depBean.getDepObjType()).append("', '");
                                        insQuery.append(depBean.getDepObjName()).append("')");
                                        sqlUtility.persist(ALIASNAME, insQuery.toString());
                                        insQuery.delete(0, insQuery.length());
                                    }
                                }
                                qBean.setDepInfo(null);
                                depBeanList = null;
                            }

                            if (("ALTER".equals(qBean.getClause()) || "DROP".equals(qBean.getClause())) && "MYSQL".equals(dbType))
                            {
                                //MySQL-Log Tables Dependency Check
                                depBeanList = depMgr.checkMySQLLogTableDep(allServers, qBean.getDbObjInfo(), mysqlSchema);
                                if (depBeanList != null && depBeanList.size() > 0)
                                {
                                    //Insert Dependency into DATA_REQUEST_DEPENDENCY_TMP
                                    for (int j = 0; j < depBeanList.size(); j++)
                                    {
                                        DependencyInfoBean depBean = depBeanList.get(j);
                                        insQuery.append("INSERT INTO DATA_REQUEST_DEPENDENCY_TMP(QUERY_TMP_ID, DEPENDENCY, SERVER_NAME, SCHEMA_NAME, OBJ_TYPE, OBJ_NAME) VALUES(");
                                        insQuery.append(queryID).append(",'").append(depBean.getDependency()).append("', '");
                                        insQuery.append(depBean.getDepServer()).append("', '");
                                        insQuery.append(depBean.getDepSchema()).append("', '");
                                        insQuery.append(depBean.getDepObjType()).append("', '");
                                        insQuery.append(depBean.getDepObjName()).append("')");
                                        sqlUtility.persist(ALIASNAME, insQuery.toString());
                                        insQuery.delete(0, insQuery.length());
                                    }
                                }
                                qBean.setDepInfo(null);
                                depBeanList = null;
                            }

                            if (("ALTER".equals(qBean.getClause()) || "DROP".equals(qBean.getClause())) && "MYSQL".equals(dbType))
                            {
                                //MySQL-View Dependency Check
                                depBeanList = depMgr.checkMySQLViewDep(allServers, qBean.getDbObjInfo());
                                if (depBeanList != null && depBeanList.size() > 0)
                                {
                                    //Insert Dependency into DATA_REQUEST_DEPENDENCY_TMP
                                    for (int j = 0; j < depBeanList.size(); j++)
                                    {
                                        DependencyInfoBean depBean = depBeanList.get(j);
                                        insQuery.append("INSERT INTO DATA_REQUEST_DEPENDENCY_TMP(QUERY_TMP_ID, DEPENDENCY, SERVER_NAME, SCHEMA_NAME, OBJ_TYPE, OBJ_NAME) VALUES(");
                                        insQuery.append(queryID).append(",'").append(depBean.getDependency()).append("', '");
                                        insQuery.append(depBean.getDepServer()).append("', '");
                                        insQuery.append(depBean.getDepSchema()).append("', '");
                                        insQuery.append(depBean.getDepObjType()).append("', '");
                                        insQuery.append(depBean.getDepObjName()).append("')");
                                        sqlUtility.persist(ALIASNAME, insQuery.toString());
                                        insQuery.delete(0, insQuery.length());
                                    }
                                }
                                qBean.setDepInfo(null);
                                depBeanList = null;
                            }

//                            if (("ALTER".equals(qBean.getClause()) || "DROP".equals(qBean.getClause())) && "MYSQL".equals(dbType))
//                            {
//                                //MySQL Infrobright Server Dependency Check
//                                depBeanList = depMgr.checkMySQLInfrobrightServer(allServers, qBean.getDbObjInfo(), mysqlSchema);
//                                if (depBeanList != null && depBeanList.size() > 0)
//                                {
//                                    //Insert Dependency into DATA_REQUEST_DEPENDENCY_TMP
//                                    for (int j = 0; j < depBeanList.size(); j++)
//                                    {
//                                        DependencyInfoBean depBean = depBeanList.get(j);
//                                        insQuery.append("INSERT INTO DATA_REQUEST_DEPENDENCY_TMP(QUERY_TMP_ID, DEPENDENCY, SERVER_NAME, SCHEMA_NAME, OBJ_TYPE, OBJ_NAME) VALUES(");
//                                        insQuery.append(queryID).append(",'").append(depBean.getDependency()).append("', '");
//                                        insQuery.append(depBean.getDepServer()).append("', '");
//                                        insQuery.append(depBean.getDepSchema()).append("', '");
//                                        insQuery.append(depBean.getDepObjType()).append("', '");
//                                        insQuery.append(depBean.getDepObjName()).append("')");
//                                        sqlUtility.persist(ALIASNAME, insQuery.toString());
//                                        insQuery.delete(0, insQuery.length());
//                                    }
//                                }
//                                qBean.setDepInfo(null);
//                                depBeanList = null;
//                            }
                        }

                        if (("ALTER".equals(qBean.getClause()) || "DROP".equals(qBean.getClause())) && "MYSQL".equals(dbType))
                        {
                            //Oracle Materialized Views existance check
                            depBeanList = depMgr.checkOracleMViewDep(allServers, qBean.getDbObjInfo());
                            if (depBeanList != null && depBeanList.size() > 0)
                            {
                                //Insert Dependency into DATA_REQUEST_DEPENDENCY_TMP
                                for (int j = 0; j < depBeanList.size(); j++)
                                {
                                    DependencyInfoBean depBean = depBeanList.get(j);
                                    insQuery.append("INSERT INTO DATA_REQUEST_DEPENDENCY_TMP(QUERY_TMP_ID, DEPENDENCY, SERVER_NAME, SCHEMA_NAME, OBJ_TYPE, OBJ_NAME) VALUES(");
                                    insQuery.append(queryID).append(",'").append(depBean.getDependency()).append("', '");
                                    insQuery.append(depBean.getDepServer()).append("', '");
                                    insQuery.append(depBean.getDepSchema()).append("', '");
                                    insQuery.append(depBean.getDepObjType()).append("', '");
                                    insQuery.append(depBean.getDepObjName()).append("')");
                                    sqlUtility.persist(ALIASNAME, insQuery.toString());
                                    insQuery.delete(0, insQuery.length());
                                }
                            }
                            qBean.setDepInfo(null);
                            depBeanList = null;
                        }

                        if (("ALTER".equals(qBean.getClause()) || "DROP".equals(qBean.getClause())) && "ORACLE".equals(dbType))
                        {
                            //MySQL Same Table existance check
                            depBeanList = depMgr.checkMysqlSameNmTableDep(allServers, qBean.getDbObjInfo());
                            if (depBeanList != null && depBeanList.size() > 0)
                            {
                                //Insert Dependency into DATA_REQUEST_DEPENDENCY_TMP
                                for (int j = 0; j < depBeanList.size(); j++)
                                {
                                    DependencyInfoBean depBean = depBeanList.get(j);
                                    insQuery.append("INSERT INTO DATA_REQUEST_DEPENDENCY_TMP(QUERY_TMP_ID, DEPENDENCY, SERVER_NAME, SCHEMA_NAME, OBJ_TYPE, OBJ_NAME) VALUES(");
                                    insQuery.append(queryID).append(",'").append(depBean.getDependency()).append("', '");
                                    insQuery.append(depBean.getDepServer()).append("', '");
                                    insQuery.append(depBean.getDepSchema()).append("', '");
                                    insQuery.append(depBean.getDepObjType()).append("', '");
                                    insQuery.append(depBean.getDepObjName()).append("')");
                                    sqlUtility.persist(ALIASNAME, insQuery.toString());
                                    insQuery.delete(0, insQuery.length());
                                }
                            }
                            qBean.setDepInfo(null);
                            depBeanList = null;
                        }

                        if ("ALTER".equals(qBean.getClause()) || "DROP".equals(qBean.getClause())
                                || "UPDATE".equals(qBean.getClause()) || "DELETE".equals(qBean.getClause()))
                        {
                            //Sensitive Keyword Check
                            depMgr.checkSensitiveKeyword(dbID, qBean, dbType, mysqlSchema);
                            if (qBean.getDepInfo() != null)
                            {
                                DependencyInfoBean depBean = qBean.getDepInfo().get(0);
                                if (depBean.getDependency() != null)
                                {
                                    //Insert Dependency into DATA_REQUEST_DEPENDENCY_TMP
                                    String[] depColName = depBean.getDepColName().split(",");
                                    for (int j = 0; j < depColName.length; j++)
                                    {
                                        insQuery.append("INSERT INTO DATA_REQUEST_DEPENDENCY_TMP(QUERY_TMP_ID, DEPENDENCY, SERVER_NAME, SCHEMA_NAME, OBJ_TYPE, OBJ_NAME, COLUMN_NAME) VALUES(");
                                        insQuery.append(queryID).append(", '").append(depBean.getDependency()).append("', '");
                                        insQuery.append(depBean.getDepServer()).append("', '");
                                        if (depBean.getDepSchema() == null)
                                        {
                                            depBean.setDepSchema(mysqlSchema);
                                        }
                                        insQuery.append(depBean.getDepSchema()).append("', '");
                                        insQuery.append(depBean.getDepObjType()).append("', '");
                                        insQuery.append(depBean.getDepObjName()).append("', '");
                                        insQuery.append(depColName[j].trim()).append("')");
                                        sqlUtility.persist(ALIASNAME, insQuery.toString());
                                        insQuery.delete(0, insQuery.length());
                                    }
                                }
                                qBean.setDepInfo(null);
                            }
                        }
                    }
                }
            }
            catch (Exception e)
            {
                Logger.ErrorLogger(e);
            }
        }
    }

    public List getAddRecords(final String sessionId) throws ClassNotFoundException, SQLException
    {
        HashMap hmap = new HashMap();
        hmap.put("uSessionId", sessionId);
        MapSqlParameterSource param = new MapSqlParameterSource(hmap);
        StringBuilder fetchQuery = new StringBuilder();
        fetchQuery.append("SELECT DTMP.QUERY_TMP_ID, DTMP.BATCH_TMP_ID, (CASE WHEN (QUERY_TMP_ID = MIN_ID.FIRST_ID) THEN TT.CNT ELSE -1 END) rowspan, ");
        fetchQuery.append("DBMAST.SERVER_NAME, DBMAST.DATABASE_NAME, DBMAST.SERVER_ID, DBMAST.SERVER_TYPE, ");
        fetchQuery.append("SUBSTRING_INDEX(DTMP.SYNTAX_VERIFIED_ON,':',-1) SYNTAX_VERIFIED_ON, ");
        fetchQuery.append("DTMP.QUERY_TEXT, DTMP.DEV_EXE_STATUS, DTMP.DEV_EXE_RESULT, ");
        fetchQuery.append("(CASE WHEN (DEV_EXE_END_DATE IS NULL OR DEV_EXE_START_DATE IS NULL OR DEV_EXE_END_DATE = '' OR DEV_EXE_START_DATE = '') THEN '---' ELSE TIME_TO_SEC(SUBSTRING(SUBTIME(SUBSTRING(DEV_EXE_END_DATE,12), SUBSTRING(DEV_EXE_START_DATE,12)),8))*1000 + SUBSTRING(SUBTIME(SUBSTRING(DEV_EXE_END_DATE,12), SUBSTRING(DEV_EXE_START_DATE,12)),10,3) END) TIMETAKEN, ");
        fetchQuery.append("BACKUP, LOG_TABLE, (CASE WHEN (QUERY_TMP_ID IN (SELECT QUERY_TMP_ID FROM DATA_REQUEST_DEPENDENCY_TMP WHERE QUERY_TMP_ID IN (SELECT QUERY_TMP_ID FROM DATA_REQUEST_TMP))) THEN 'YES' ELSE 'NO' END) DEPENDENCY ");
        fetchQuery.append("FROM DATA_REQUEST_TMP DTMP ");
        fetchQuery.append("INNER JOIN");
        fetchQuery.append("(");
        fetchQuery.append("    SELECT BATCH_TMP_ID, COUNT(BATCH_TMP_ID) CNT FROM DATA_REQUEST_TMP WHERE U_SESSION_ID = :uSessionId ");
        fetchQuery.append("    GROUP BY BATCH_TMP_ID ");
        fetchQuery.append(") TT ON TT.BATCH_TMP_ID = DTMP.BATCH_TMP_ID ");
        fetchQuery.append("INNER JOIN");
        fetchQuery.append("(");
        fetchQuery.append("    SELECT BATCH_TMP_ID, MIN(QUERY_TMP_ID) FIRST_ID FROM DATA_REQUEST_TMP WHERE U_SESSION_ID = :uSessionId");
        fetchQuery.append("    GROUP BY BATCH_TMP_ID");
        fetchQuery.append(") MIN_ID ON MIN_ID.BATCH_TMP_ID = DTMP.BATCH_TMP_ID ");
        fetchQuery.append("INNER JOIN");
        fetchQuery.append("(");
        fetchQuery.append("    SELECT DISTINCT DB.DATABASE_ID, SRVR.SERVER_ID, SRVR.SERVER_NAME, DB.DATABASE_NAME, SRVR.SERVER_TYPE FROM SERVER_MASTER SRVR ");//Remove SRVR. & DB.
        fetchQuery.append("    INNER JOIN DATABASE_MASTER DB ON DB.SERVER_ID = SRVR.SERVER_ID ");
        fetchQuery.append("    INNER JOIN DATA_REQUEST_TMP DRTMP ON DRTMP.DATABASE_ID = DB.DATABASE_ID ");
        fetchQuery.append(") DBMAST ON DBMAST.DATABASE_ID = DTMP.DATABASE_ID ");
        fetchQuery.append("WHERE U_SESSION_ID = :uSessionId");
        List records = sqlUtility.getList(ALIASNAME, fetchQuery.toString(), param);

        //Check for Log Schema Existance
        checkLogSchemaExistance(records);
        return records;
    }

    public List getDependencyRecords(final String queryID, final String tab) throws ClassNotFoundException, SQLException
    {
        StringBuilder fetchQuery = new StringBuilder();
        if ("Add".equals(tab))
        {
            fetchQuery.append("SELECT DEPENDENCY, SERVER_NAME, SCHEMA_NAME, OBJ_NAME, COLUMN_NAME FROM DATA_REQUEST_DEPENDENCY_TMP WHERE QUERY_TMP_ID = ").append(queryID);
        }
        else if ("View".equals(tab))
        {
            fetchQuery.append("SELECT DEPENDENCY, SERVER_NAME, SCHEMA_NAME, OBJ_NAME, COLUMN_NAME FROM DATA_REQUEST_DEPENDENCY WHERE QUERY_ID = ").append(queryID);
        }
        return sqlUtility.getList(ALIASNAME, fetchQuery.toString());
    }

    public void deleteBatch(final String sessionId, final String batchId) throws ClassNotFoundException, SQLException
    {
        HashMap hmap = new HashMap();
        hmap.put("uSessionId", sessionId);
        hmap.put("batchId", batchId);
        MapSqlParameterSource param = new MapSqlParameterSource(hmap);
        StringBuilder query = new StringBuilder();

        //Select QUERY_TMP_ID From DATA_REQUEST_TMP For the Batch in Current SessionID
        query.append("SELECT QUERY_TMP_ID FROM DATA_REQUEST_TMP WHERE U_SESSION_ID = :uSessionId  AND BATCH_TMP_ID = :batchId");
        SqlRowSet queryIDs = sqlUtility.getRowSet(ALIASNAME, query.toString(), param);
        if (queryIDs != null)
        {
            //Delete Record From DATA_REQUEST_DEPENDENCY_TMP For Current SessionID
            query.delete(0, query.length());
            query.append("DELETE FROM DATA_REQUEST_DEPENDENCY_TMP WHERE QUERY_TMP_ID IN (");
            while (queryIDs.next())
            {
                query.append(queryIDs.getInt("QUERY_TMP_ID")).append(", ");
            }
            if (query.toString().endsWith(", "))
            {
                query.delete(query.length() - 2, query.length());
            }
            query.append(")");
            sqlUtility.persist(ALIASNAME, query.toString());

            //Delete Record From DATA_REQUEST_TMP For Current SessionID
            query.delete(0, query.length());
            query.append("DELETE FROM DATA_REQUEST_TMP WHERE U_SESSION_ID = :uSessionId AND BATCH_TMP_ID = :batchId");
            sqlUtility.persist(ALIASNAME, query.toString(), param);
        }
    }

    public synchronized int confirmRequest(final FinDataReqExecutorEntityBean entityBean, final String host) throws ClassNotFoundException, SQLException
    {
        int dataReqID = 0;
        SqlParameterSource sps;
        sps = new BeanPropertySqlParameterSource(entityBean);
        StringBuilder query = new StringBuilder();
        MapSqlParameterSource param = null;
        boolean isCritical = false;

        //Update BACKUP field in DATA_REQUEST_TMP
        String[] backups = entityBean.getQueryBackup();
        if (backups != null)
        {
            for (int i = 0; i < backups.length; i++)
            {
                if (!"NA".equals(backups[i]))
                {
                    query.delete(0, query.length());
                    query.append("UPDATE DATA_REQUEST_TMP ");
                    query.append("SET BACKUP = 'Backup Required' ");
                    query.append("WHERE QUERY_TMP_ID = ").append(backups[i]);
                    sqlUtility.persist(ALIASNAME, query.toString());
                }
            }
        }

        //Update LOG_TABLE field in DATA_REQUEST_TMP
        String[] logTables = entityBean.getQueryLogTable();
        if (logTables != null)
        {
            for (int i = 0; i < logTables.length; i++)
            {
                if (!"NA".equals(logTables[i]))
                {
                    query.delete(0, query.length());
                    query.append("UPDATE DATA_REQUEST_TMP ");
                    query.append("SET LOG_TABLE = 'Log Table Required' ");
                    query.append("WHERE QUERY_TMP_ID = ").append(logTables[i]);
                    sqlUtility.persist(ALIASNAME, query.toString());
                }
            }
        }

        query.delete(0, query.length());
        query.append("SELECT DISTINCT BATCH_TMP_ID FROM DATA_REQUEST_TMP WHERE U_SESSION_ID = :uSessionId");
        SqlRowSet batches = sqlUtility.getRowSet(ALIASNAME, query.toString(), sps);

        if (batches != null)
        {
            //Insert Into DATA_REQUEST_MASTER
            query.delete(0, query.length());
            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(Calendar.getInstance().getTime());
            query.append("INSERT INTO DATA_REQUEST_MASTER(PROJ_ID, REQ_ID, PURPOSE, EXECUTION_TYPE, ENTRY_BY, ENTRY_DATE, EXECUTION_STATUS) ");
            query.append("VALUES(:projId, :reqId, :purpose, :executionType, :entryBy, '").append(date).append("', 'Pending')");
            int masterIns = sqlUtility.persist(ALIASNAME, query.toString(), sps);

            if (masterIns > 0)
            {
                //Get DATA_REQ_ID To Insert Into DATA_REQUEST_BATCH
                dataReqID = sqlUtility.getInt(ALIASNAME, "SELECT DATA_REQ_ID FROM DATA_REQUEST_MASTER ORDER BY DATA_REQ_ID DESC LIMIT 1");

                while (batches.next())
                {
                    //Select Records Of One Batch From DATA_REQUEST_TMP
                    query.delete(0, query.length());
                    query.append("SELECT QUERY_TMP_ID, DATABASE_ID, SYNTAX_VERIFIED_ON, QUERY_TEXT, DEV_EXE_RESULT, DEV_EXE_START_DATE, DEV_EXE_END_DATE, DEV_EXE_STATUS, BACKUP, LOG_TABLE ");
                    query.append("FROM DATA_REQUEST_TMP WHERE U_SESSION_ID = :uSessionId AND BATCH_TMP_ID = ").append(batches.getString("BATCH_TMP_ID"));
                    SqlRowSet batchRows = sqlUtility.getRowSet(ALIASNAME, query.toString(), sps);

                    if (batchRows != null)
                    {
                        batchRows.next();
                        //Insert One Batch Entry Into DATA_REQUEST_BATCH
                        query.delete(0, query.length());
                        query.append("INSERT INTO DATA_REQUEST_BATCH(DATA_REQ_ID, DATABASE_ID, SYNTAX_VERIFIED_ON) ");
                        query.append("VALUES(").append(dataReqID).append(", '").append(batchRows.getString("DATABASE_ID")).append("', '");
                        query.append(batchRows.getString("SYNTAX_VERIFIED_ON")).append("')");
                        int batchIns = sqlUtility.persist(ALIASNAME, query.toString(), sps);

                        if (batchIns > 0)
                        {
                            //Get BATCH_ID To Insert Into DATA_REQUEST_QUERY
                            int batchID = sqlUtility.getInt(ALIASNAME, "SELECT BATCH_ID FROM DATA_REQUEST_BATCH ORDER BY BATCH_ID DESC LIMIT 1");

                            batchRows.beforeFirst();
                            while (batchRows.next())
                            {
                                //Insert All Queries Of One Batch Into DATA_REQUEST_QUERY
                                query.delete(0, query.length());
                                query.append("INSERT INTO DATA_REQUEST_QUERY(BATCH_ID, QUERY_TEXT, DEV_EXE_RESULT, DEV_EXE_START_DATE, DEV_EXE_END_DATE, DEV_EXE_STATUS, BACKUP, LOG_TABLE) ");
                                query.append("VALUES(").append(batchID).append(", :query, :result, :startTime, :endTime, :exeStatus, :backup, :logTable)");
                                HashMap hmap = new HashMap();
                                hmap.put("query", batchRows.getString("QUERY_TEXT"));
                                hmap.put("result", batchRows.getString("DEV_EXE_RESULT"));
                                hmap.put("startTime", batchRows.getString("DEV_EXE_START_DATE"));
                                hmap.put("endTime", batchRows.getString("DEV_EXE_END_DATE"));
                                hmap.put("exeStatus", batchRows.getString("DEV_EXE_STATUS"));
                                hmap.put("backup", batchRows.getString("BACKUP"));
                                hmap.put("logTable", batchRows.getString("LOG_TABLE"));
                                param = new MapSqlParameterSource(hmap);
                                int queryIns = sqlUtility.persist(ALIASNAME, query.toString(), param);

                                if (queryIns > 0)
                                {
                                    //Get QUERY_ID To Insert Into DATA_REQUEST_DEPENDENCY
                                    int queryID = sqlUtility.getInt(ALIASNAME, "SELECT QUERY_ID FROM DATA_REQUEST_QUERY ORDER BY QUERY_ID DESC LIMIT 1");

                                    //Select All Dependency Records For Current QUERY_TMP_ID
                                    query.delete(0, query.length());
                                    query.append("SELECT DEPENDENCY, SERVER_NAME, SCHEMA_NAME, OBJ_TYPE, OBJ_NAME, COLUMN_NAME ");
                                    query.append("FROM DATA_REQUEST_DEPENDENCY_TMP WHERE QUERY_TMP_ID = ").append(batchRows.getString("QUERY_TMP_ID"));
                                    SqlRowSet depRows = sqlUtility.getRowSet(ALIASNAME, query.toString());

                                    if (depRows != null)
                                    {
                                        while (depRows.next())
                                        {
                                            isCritical = true;
                                            //Insert Dependency Entry Into DATA_REQUEST_DEPENDENCY
                                            query.delete(0, query.length());
                                            query.append("INSERT INTO DATA_REQUEST_DEPENDENCY(QUERY_ID, DEPENDENCY, SERVER_NAME, SCHEMA_NAME");
                                            if (depRows.getString("OBJ_TYPE") != null)
                                            {
                                                query.append(", OBJ_TYPE, OBJ_NAME");
                                                if (depRows.getString("COLUMN_NAME") != null)
                                                {
                                                    query.append(", COLUMN_NAME");
                                                }
                                            }
                                            query.append(") VALUES(").append(queryID).append(", '").append(depRows.getString("DEPENDENCY")).append("'");
                                            query.append(", '").append(depRows.getString("SERVER_NAME")).append("'");
                                            query.append(", '").append(depRows.getString("SCHEMA_NAME")).append("'");
                                            if (depRows.getString("OBJ_TYPE") != null)
                                            {
                                                query.append(", '").append(depRows.getString("OBJ_TYPE")).append("'");
                                                query.append(", '").append(depRows.getString("OBJ_NAME")).append("'");
                                                if (depRows.getString("COLUMN_NAME") != null)
                                                {
                                                    query.append(", '").append(depRows.getString("COLUMN_NAME")).append("'");
                                                }
                                            }
                                            query.append(")");
                                            sqlUtility.persist(ALIASNAME, query.toString());
                                        }
                                    }
                                }
                                else
                                {
                                    Logger.DataLogger("Unable To Insert Reocrd Into DATA_REQUEST_QUERY");
                                }
                            }
                        }
                        else
                        {
                            Logger.DataLogger("Unable To Insert Reocrd Into DATA_REQUEST_BATCH");
                        }
                    }
                }
            }
            else
            {
                Logger.DataLogger("Unable To Insert Reocrd Into DATA_REQUEST_MASTER");
            }

            //Delete All Records Of Current Session From DATA_REQUEST_TMP
            deleteAddRequest(entityBean.getuSessionId());

            //Halt request
            if (entityBean.getCurrentStatus() != null && entityBean.getCurrentStatus().equalsIgnoreCase("Halt"))
            {
                HashMap hmap = new HashMap();
                hmap.put("executedBy", entityBean.getEntryBy());
                hmap.put("host", host);
                param = new MapSqlParameterSource(hmap);

                query.delete(0, query.length());
                query.append("UPDATE DATA_REQUEST_MASTER ");
                query.append("SET EXECUTED_BY = :executedBy, ");
                query.append("EXECUTED_FROM_HOST = :host, ");
                query.append("EXECUTION_DATE = '").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(Calendar.getInstance().getTime())).append("', ");
                query.append("EXECUTION_STATUS = 'Halted' ");
                query.append("WHERE DATA_REQ_ID = ").append(dataReqID);
                sqlUtility.persist(ALIASNAME, query.toString(), param);
            }

            if (isCritical)
            {
                //Update IS_CRITICAL field in DATA_REQUEST_MASTER
                query.delete(0, query.length());
                query.append("UPDATE DATA_REQUEST_MASTER ");
                query.append("SET IS_CRITICAL = 'Y' ");
                query.append("WHERE DATA_REQ_ID = ").append(dataReqID);
                sqlUtility.persist(ALIASNAME, query.toString());
            }
        }
        return dataReqID;
    }

    public void deleteAddRequest(final String sessionId) throws ClassNotFoundException, SQLException
    {
        HashMap hmap = new HashMap();
        hmap.put("uSessionId", sessionId);
        MapSqlParameterSource param = new MapSqlParameterSource(hmap);
        StringBuilder query = new StringBuilder();

        //Select QUERY_TMP_ID From DATA_REQUEST_TMP For Current SessionID
        query.append("SELECT QUERY_TMP_ID FROM DATA_REQUEST_TMP WHERE U_SESSION_ID = :uSessionId");
        SqlRowSet queryIDs = sqlUtility.getRowSet(ALIASNAME, query.toString(), param);
        if (queryIDs != null)
        {
            //Delete Record From DATA_REQUEST_DEPENDENCY_TMP For Current SessionID
            query.delete(0, query.length());
            query.append("DELETE FROM DATA_REQUEST_DEPENDENCY_TMP WHERE QUERY_TMP_ID IN (");
            while (queryIDs.next())
            {
                query.append(queryIDs.getInt("QUERY_TMP_ID")).append(", ");
            }
            if (query.toString().endsWith(", "))
            {
                query.delete(query.length() - 2, query.length());
            }
            query.append(")");
            sqlUtility.persist(ALIASNAME, query.toString());

            //Delete Record From DATA_REQUEST_TMP For Current SessionID
            query.delete(0, query.length());
            query.append("DELETE FROM DATA_REQUEST_TMP WHERE U_SESSION_ID = :uSessionId");
            sqlUtility.persist(ALIASNAME, query.toString(), param);
        }
    }

    public List getViewRecords(final FinDataReqExecutorEntityBean entityBean) throws ClassNotFoundException, SQLException
    {
        SqlParameterSource sps;
        sps = new BeanPropertySqlParameterSource(entityBean);
        StringBuilder fetchQuery = new StringBuilder();
        fetchQuery.append("SELECT DATA_REQ_ID, IFNULL(EMP1.EMP_NAME, '---') ENTRY_BY, PROJ_ID,");
        fetchQuery.append(" (CASE WHEN (REQ_ID = '' AND REQ_ID <> 0) THEN '---' ELSE REQ_ID END) REQ_ID,");
        fetchQuery.append(" (CASE WHEN (PURPOSE = '') THEN '---' ELSE PURPOSE END) PURPOSE,");
        fetchQuery.append(" (CASE WHEN (EXECUTION_TYPE = '1') THEN 'Execute ALL' WHEN (EXECUTION_TYPE = '2') THEN 'Stop On Error' WHEN (EXECUTION_TYPE = '3') THEN 'Rollback On Error' ELSE EXECUTION_TYPE END) EXECUTION_TYPE,");
        fetchQuery.append(" STR_TO_DATE(ENTRY_DATE,'%Y-%m-%d') ENTRY_DATE, EXECUTION_STATUS,");
        fetchQuery.append(" IFNULL(EMP2.EMP_NAME, '---') EXECUTED_BY,");
        fetchQuery.append(" IFNULL(EXECUTED_FROM_HOST, '---') EXECUTED_FROM_HOST,");
        fetchQuery.append(" STR_TO_DATE(EXECUTION_DATE,'%Y-%m-%d') EXECUTION_DATE,");
        fetchQuery.append(" REPLACEMENT_ID, IS_CRITICAL ");
        fetchQuery.append("FROM DATA_REQUEST_MASTER ");
        fetchQuery.append("LEFT JOIN NJHR.EMP_MAST EMP1 ON ENTRY_BY = EMP1.EMP_CODE ");
        fetchQuery.append("LEFT JOIN NJHR.EMP_MAST EMP2 ON EXECUTED_BY = EMP2.EMP_CODE ");
        if (entityBean.getTxtViewDataReqId() != null && !"".equals(entityBean.getTxtViewDataReqId()))
        {
            fetchQuery.append("WHERE DATA_REQ_ID = :txtViewDataReqId");
        }
        else
        {
            fetchQuery.append("WHERE 1 = 1");
            if (entityBean.getCmbViewProjId() != null && !"".equals(entityBean.getCmbViewProjId()))
            {
                fetchQuery.append(" AND PROJ_ID = :cmbViewProjId");
            }
            if (entityBean.getCmbViewEmp() != null && !"".equals(entityBean.getCmbViewEmp()))
            {
                fetchQuery.append(" AND ENTRY_BY = :cmbViewEmp");
            }
            if (entityBean.getCmbViewReqId() != null && !"".equals(entityBean.getCmbViewReqId()))
            {
                fetchQuery.append(" AND REQ_ID = :cmbViewReqId");
            }
            if (entityBean.getTxtViewPurpose() != null && !"".equals(entityBean.getTxtViewPurpose()))
            {
                fetchQuery.append(" AND PURPOSE = :txtViewPurpose");
            }
            if (entityBean.getCmbViewStatus() != null && !"".equals(entityBean.getCmbViewStatus()))
            {
                fetchQuery.append(" AND EXECUTION_STATUS = :cmbViewStatus");
            }
            if (entityBean.getDtFromDate() != null && !"".equals(entityBean.getDtFromDate())
                    && entityBean.getDtToDate() != null && !"".equals(entityBean.getDtToDate()))
            {
                fetchQuery.append(" AND STR_TO_DATE(ENTRY_DATE,'%Y-%m-%d') >= STR_TO_DATE('").append(entityBean.getDtFromDate()).append("','%d-%m-%Y')");
                fetchQuery.append(" AND STR_TO_DATE(ENTRY_DATE,'%Y-%m-%d') <= STR_TO_DATE('").append(entityBean.getDtToDate()).append("','%d-%m-%Y')");
            }
        }
        SqlRowSet srs = sqlUtility.getRowSet(ALIASNAME, fetchQuery.toString(), sps);

        fetchQuery.setLength(0);
        fetchQuery.append("SELECT PRJ_ID, PRJ_NAME FROM PROJECT_MST");
        SqlRowSet psrs = sqlUtility.getRowSet(WFMALIASNAME, fetchQuery.toString());

        List res = null;
        if (srs != null && psrs != null)
        {
            Map m = null;
            res = new ArrayList();
            while (srs.next())
            {
                psrs.beforeFirst();
                while (psrs.next())
                {
                    if (psrs.getString("PRJ_ID").equalsIgnoreCase(srs.getString("PROJ_ID")))
                    {
                        m = new LinkedHashMap();
                        m.put("DATA_REQ_ID", srs.getString("DATA_REQ_ID"));
                        m.put("ENTRY_BY", srs.getString("ENTRY_BY"));
                        m.put("PRJ_NAME", psrs.getString("PRJ_NAME"));
                        m.put("REQ_ID", srs.getString("REQ_ID"));
                        m.put("PURPOSE", srs.getString("PURPOSE"));
                        m.put("EXECUTION_TYPE", srs.getString("EXECUTION_TYPE"));
                        m.put("ENTRY_DATE", srs.getDate("ENTRY_DATE"));
                        m.put("EXECUTION_STATUS", srs.getString("EXECUTION_STATUS"));
                        m.put("EXECUTED_BY", srs.getString("EXECUTED_BY"));
                        m.put("EXECUTED_FROM_HOST", srs.getString("EXECUTED_FROM_HOST"));
                        m.put("EXECUTION_DATE", srs.getDate("EXECUTION_DATE"));
                        m.put("REPLACEMENT_ID", srs.getString("REPLACEMENT_ID"));
                        m.put("IS_CRITICAL", srs.getString("IS_CRITICAL"));
                        res.add(m);
                    }
                }
            }
        }
        return res;
    }

    public List viewQueryDetails(final String dataReqId) throws ClassNotFoundException, SQLException
    {
        HashMap hmap = new HashMap();
        hmap.put("dataReqId", dataReqId);
        MapSqlParameterSource param = new MapSqlParameterSource(hmap);
        StringBuilder fetchQuery = new StringBuilder();
        fetchQuery.append("SELECT (CASE WHEN (QRY.QUERY_ID = INN.MIN_QUERY_ID) THEN INN.TOTAL ELSE -1 END) rowspan,");
        fetchQuery.append(" QRY.QUERY_ID QUERY_ID,");
        fetchQuery.append(" DBMAST.SERVER_NAME, DBMAST.DATABASE_NAME,");
        fetchQuery.append(" SUBSTRING_INDEX(SYNTAX_VERIFIED_ON,':',-1) SYNTAX_VERIFIED_ON,");
        fetchQuery.append(" QUERY_TEXT, DEV_EXE_STATUS, DEV_EXE_RESULT,");
        fetchQuery.append(" (CASE WHEN (DEV_EXE_END_DATE IS NULL OR DEV_EXE_START_DATE IS NULL OR DEV_EXE_END_DATE = '' OR DEV_EXE_START_DATE = '') THEN '---' ELSE TIME_TO_SEC(SUBSTRING(SUBTIME(SUBSTRING(DEV_EXE_END_DATE,12), SUBSTRING(DEV_EXE_START_DATE,12)),8))*1000 + SUBSTRING(SUBTIME(SUBSTRING(DEV_EXE_END_DATE,12), SUBSTRING(DEV_EXE_START_DATE,12)),10,3) END) DEV_TIMETAKEN,");
        fetchQuery.append(" (CASE WHEN (PROD_EXE_STATUS IS NULL OR PROD_EXE_STATUS = '') THEN '---' ELSE PROD_EXE_STATUS END) PROD_EXE_STATUS,");
        fetchQuery.append(" (CASE WHEN (PROD_EXE_RESULT IS NULL OR PROD_EXE_RESULT = '') THEN '---' ELSE PROD_EXE_RESULT END) PROD_EXE_RESULT,");
        fetchQuery.append(" (CASE WHEN (PROD_EXE_END_DATE IS NULL OR PROD_EXE_START_DATE IS NULL OR PROD_EXE_END_DATE = '' OR PROD_EXE_START_DATE = '') THEN '---' ELSE TIME_TO_SEC(SUBSTRING(SUBTIME(SUBSTRING(PROD_EXE_END_DATE,12), SUBSTRING(PROD_EXE_START_DATE,12)),8))*1000 + SUBSTRING(SUBTIME(SUBSTRING(PROD_EXE_END_DATE,12), SUBSTRING(PROD_EXE_START_DATE,12)),10,3) END) PROD_TIMETAKEN,");
        fetchQuery.append(" BACKUP, LOG_TABLE, (CASE WHEN (QRY.QUERY_ID IN (SELECT QUERY_ID FROM DATA_REQUEST_DEPENDENCY WHERE QUERY_ID IN (SELECT QUERY_ID FROM DATA_REQUEST_QUERY))) THEN 'YES' ELSE 'NO' END) DEPENDENCY ");
        fetchQuery.append("FROM DATA_REQUEST_MASTER MST ");
        fetchQuery.append("INNER JOIN DATA_REQUEST_BATCH BTC ON MST.DATA_REQ_ID = BTC.DATA_REQ_ID ");
        fetchQuery.append("INNER JOIN");
        fetchQuery.append("(");
        fetchQuery.append("    SELECT TOTAL, MIN_QUERY_ID, Q.BATCH_ID FROM ( SELECT COUNT(*) TOTAL, MIN(QUERY_ID) MIN_QUERY_ID, BATCH_ID FROM DATA_REQUEST_QUERY GROUP BY BATCH_ID ) Q");
        fetchQuery.append("    INNER JOIN DATA_REQUEST_BATCH B ON Q.BATCH_ID = B.BATCH_ID");
        fetchQuery.append("    WHERE B.DATA_REQ_ID = :dataReqId");
        fetchQuery.append(") INN ON INN.BATCH_ID = BTC.BATCH_ID ");
        fetchQuery.append("INNER JOIN DATA_REQUEST_QUERY QRY ON BTC.BATCH_ID = QRY.BATCH_ID ");
        fetchQuery.append("INNER JOIN ");
        fetchQuery.append("(");
        fetchQuery.append("    SELECT DISTINCT DB.DATABASE_ID, SRVR.SERVER_NAME, DB.DATABASE_NAME FROM SERVER_MASTER SRVR ");//Remove SRVR. & DB.
        fetchQuery.append("    INNER JOIN DATABASE_MASTER DB ON DB.SERVER_ID = SRVR.SERVER_ID ");
        fetchQuery.append(") DBMAST ON DBMAST.DATABASE_ID = BTC.DATABASE_ID ");
        fetchQuery.append("WHERE MST.DATA_REQ_ID = :dataReqId");
        return sqlUtility.getList(ALIASNAME, fetchQuery.toString(), param);
    }

    public String executeRequest(final String dataReqId, final String executedBy, final String host) throws ClassNotFoundException, SQLException
    {
        HardCodeProperty hcp = new HardCodeProperty();
        String validExecutors = hcp.getProperty("data_request_valid_executor");
        String[] executors = validExecutors.split(",");
        int exe = 0;
        for (; exe < executors.length; exe++)
        {
            if (executors[exe].trim().equals(executedBy))
            {
                break;
            }
        }
//        if (exe == executors.length)
//        {
//            return "Authentication Failed ! You do not have rights to execute this Data Request.";
//        }

        HashMap hmap = new HashMap();
        hmap.put("dataReqId", dataReqId);
        hmap.put("executedBy", executedBy);
        hmap.put("host", host);
        MapSqlParameterSource param = new MapSqlParameterSource(hmap);
        StringBuilder query = new StringBuilder();
        String exeStatus = "";
        StringBuilder updateOBJ = new StringBuilder();
        updateOBJ.append("  UPDATE DATA_REQUEST_QUERY SET OBJ_NAME =:OBJ_NAME,");
        updateOBJ.append("  OBJ_TYPE=:OBJ_TYPE , PURPOSE=:PURPOSE");
        updateOBJ.append("  WHERE QUERY_ID =:QUERY_ID");
        //Check Execution Status
        query.append("SELECT EXECUTION_STATUS FROM DATA_REQUEST_MASTER WHERE DATA_REQ_ID = :dataReqId");
        String reqStatus = sqlUtility.getString(ALIASNAME, query.toString(), param);
        if (reqStatus.equalsIgnoreCase("Pending"))
        {
            //Get EXECUTION_TYPE status To Commit/Rollback Transaction
            query.delete(0, query.length());
            query.append("SELECT EXECUTION_TYPE FROM DATA_REQUEST_MASTER WHERE DATA_REQ_ID = :dataReqId");
            String execType = sqlUtility.getString(ALIASNAME, query.toString(), param);

            //Get All The BATCH_ID Particular DATA_REQ_ID
            query.delete(0, query.length());
            query.append("SELECT BATCH_ID, SMAST.SERVER_ID, DBMAST.DATABASE_NAME FROM DATA_REQUEST_BATCH BTC ");//Remove SMAST. & DBMAST.
            query.append("INNER JOIN DATABASE_MASTER DBMAST ON DBMAST.DATABASE_ID = BTC.DATABASE_ID ");
            query.append("INNER JOIN SERVER_MASTER SMAST ON SMAST.SERVER_ID = DBMAST.SERVER_ID ");
            query.append("WHERE DATA_REQ_ID = :dataReqId");
            SqlRowSet batches = sqlUtility.getRowSet(ALIASNAME, query.toString(), param);
            if (batches != null)
            {
                while (batches.next())
                {
                    int batch_Id = batches.getInt("BATCH_ID");
                    List<String> queryID = new ArrayList<String>();
                    List<String> queries = new ArrayList<String>();
                    List<String> results = new ArrayList<String>();
                    List<String> statuses = new ArrayList<String>();
                    List<String> startTimes = new ArrayList<String>();
                    List<String> endTimes = new ArrayList<String>();
                    List<String> backup = new ArrayList<String>();
                    List<String> logTable = new ArrayList<String>();
                    boolean anyFail = false;

                    //Get All The Queries For Particular DATA_REQ_ID
                    query.delete(0, query.length());
                    query.append("SELECT QUERY_ID, QUERY_TEXT, BACKUP, LOG_TABLE ");
                    query.append("FROM DATA_REQUEST_BATCH BTC ");
                    query.append("INNER JOIN DATA_REQUEST_QUERY QRY ON BTC.BATCH_ID = QRY.BATCH_ID ");
                    query.append("WHERE BTC.BATCH_ID = ").append(batch_Id);
                    SqlRowSet srs = sqlUtility.getRowSet(ALIASNAME, query.toString(), param);
                    if (srs != null)
                    {
                        while (srs.next())
                        {
                            queryID.add(srs.getString("QUERY_ID"));
                            queries.add(srs.getString("QUERY_TEXT"));
                            backup.add(srs.getString("BACKUP"));
                            logTable.add(srs.getString("LOG_TABLE"));
                        }

                        if (queryID.size() > 0)
                        {
                            com.finlogic.util.persistence.SQLTranUtility tran = new com.finlogic.util.persistence.SQLTranUtility();
                            String serverName = "", databaseType = "";
                            try
                            {
                                for (int i = 0; i < queryID.size(); i++)
                                {
                                    if (i == 0)
                                    {
                                        ServerPropertyReader propReader = new ServerPropertyReader();
                                        serverName = propReader.getActualServer(batches.getString("SERVER_ID"));
                                        tran.openConn(serverName);
                                        databaseType = tran.getConnection().getMetaData().getDatabaseProductName().toUpperCase(Locale.getDefault());
                                    }

                                    String result = "";
                                    String status = "";
                                    String startTime = "";
                                    String endTime = "";
                                    updateForDataVrs(tran, queries.get(i), queryID.get(i), databaseType, "OLD_QUERY_TEXT");
                                    if ("1".equals(execType) || (("2".equals(execType) || "3".equals(execType)) && !anyFail))
                                    {
                                        if ("Backup Required".equals(backup.get(i)))
                                        {
                                            try
                                            {
                                                //Make SELECT Query From UPDATE or DELETE or CREATE OR REPLACE or DROP Query
                                                SqlRowSet selectRows = null;
                                                QueryMaker qMaker = new QueryMaker();
                                                String selectQ = queries.get(i);
                                                selectQ = selectQ.replaceAll("\\s{1,}", " ");
                                                selectQ = selectQ.trim();
                                                selectQ = selectQ.replace(")SET ", ") SET ");
                                                selectQ = selectQ.replace(")WHERE ", ") WHERE ");
                                                int afterIndex = selectQ.toUpperCase().indexOf(" SET ") + 8;
                                                selectQ = qMaker.makeSelectQuery(selectQ, databaseType, batches.getString("DATABASE_NAME"));
                                                if (selectQ != null && !"".equals(selectQ))
                                                {
                                                    if (queries.get(i).trim().toUpperCase().startsWith("UPDATE ") || queries.get(i).trim().toUpperCase().startsWith("DELETE "))
                                                    {
                                                        while (true)
                                                        {
                                                            try
                                                            {
                                                                //Create Excel For Query Result
                                                                String fileName = finpack.FinPack.getProperty("tomcat1_path") + "/webapps/finstudio/generated/DRE_" + queryID.get(i) + ".xls";
                                                                selectRows = tran.getRowSet(selectQ);
                                                                DataExportUtility exporter = new DataExportUtility();
                                                                exporter.createExcel(fileName, selectRows, databaseType);

                                                                //Update BACKUP field with 'Backup Taken' In DATA_REQUEST_QUERY
                                                                query.delete(0, query.length());
                                                                query.append("UPDATE DATA_REQUEST_QUERY ");
                                                                query.append("SET BACKUP = 'Backup Taken' ");
                                                                query.append("WHERE QUERY_ID = ").append(queryID.get(i));
                                                                sqlUtility.persist(ALIASNAME, query.toString());
                                                                break;
                                                            }
                                                            catch (Exception e)
                                                            {
                                                                Logger.ErrorLogger(e);

                                                                if (queries.get(i).trim().toUpperCase().startsWith("UPDATE "))
                                                                {
                                                                    if (selectQ.toUpperCase().indexOf("WHERE ") != selectQ.toUpperCase().lastIndexOf("WHERE "))
                                                                    {
                                                                        selectQ = qMaker.skipFirstWhere(selectQ, afterIndex);
                                                                        continue;
                                                                    }
                                                                }

                                                                //Update BACKUP field with 'Backup Not Taken' In DATA_REQUEST_QUERY
                                                                query.delete(0, query.length());
                                                                query.append("UPDATE DATA_REQUEST_QUERY ");
                                                                query.append("SET BACKUP = 'Backup Not Taken' ");
                                                                query.append("WHERE QUERY_ID = ").append(queryID.get(i));
                                                                sqlUtility.persist(ALIASNAME, query.toString());
                                                                break;
                                                            }
                                                        }
                                                    }
                                                    else if (queries.get(i).trim().toUpperCase().startsWith("CREATE OR REPLACE ") || queries.get(i).trim().toUpperCase().startsWith("DROP "))
                                                    {
                                                        try
                                                        {
                                                            //Create Text File For Query Result
                                                            String fileName = finpack.FinPack.getProperty("tomcat1_path") + "/webapps/finstudio/generated/DRE_" + queryID.get(i) + ".txt";
                                                            selectRows = tran.getRowSet(selectQ);
                                                            DataExportUtility exporter = new DataExportUtility();
                                                            exporter.createTextFile(fileName, selectRows);

                                                            //Update BACKUP field with 'Backup Taken' In DATA_REQUEST_QUERY
                                                            query.delete(0, query.length());
                                                            query.append("UPDATE DATA_REQUEST_QUERY ");
                                                            query.append("SET BACKUP = 'Backup Taken' ");
                                                            query.append("WHERE QUERY_ID = ").append(queryID.get(i));
                                                            sqlUtility.persist(ALIASNAME, query.toString());
                                                        }
                                                        catch (Exception e)
                                                        {
                                                            Logger.ErrorLogger(e);
                                                            //Update BACKUP field with 'Backup Not Taken' In DATA_REQUEST_QUERY
                                                            query.delete(0, query.length());
                                                            query.append("UPDATE DATA_REQUEST_QUERY ");
                                                            query.append("SET BACKUP = 'Backup Not Taken' ");
                                                            query.append("WHERE QUERY_ID = ").append(queryID.get(i));
                                                            sqlUtility.persist(ALIASNAME, query.toString());
                                                        }
                                                    }
                                                }
                                                else
                                                {
                                                    //Update BACKUP field with 'Backup Not Taken' In DATA_REQUEST_QUERY
                                                    query.delete(0, query.length());
                                                    query.append("UPDATE DATA_REQUEST_QUERY ");
                                                    query.append("SET BACKUP = 'Backup Not Taken' ");
                                                    query.append("WHERE QUERY_ID = ").append(queryID.get(i));
                                                    sqlUtility.persist(ALIASNAME, query.toString());
                                                }
                                            }
                                            catch (Exception e)
                                            {
                                                Logger.ErrorLogger(e);
                                            }
                                        }

                                        //Execute Query
                                        try
                                        {
                                            startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(Calendar.getInstance().getTime());
                                            result = tran.persist(queries.get(i)) + " Records Affected.";
                                            endTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(Calendar.getInstance().getTime());
                                            status = "PASS";
                                            if (queries.get(i).toUpperCase().startsWith("ALTER"))
                                            {
                                                updateForDataVrs(tran, queries.get(i), queryID.get(i), databaseType, "NEW_QUERY_TEXT");
                                            }
                                            if (!queries.get(i).toUpperCase().startsWith("USE") && !queries.get(i).toUpperCase().startsWith("INSERT") && !queries.get(i).toUpperCase().startsWith("UPDATE") && !queries.get(i).toUpperCase().startsWith("DELETE"))
                                            {
                                                String[] objName = getObjName(queries.get(i).toUpperCase(), databaseType).split(" ");
                                                String purpose = "";
                                                if (queries.get(i).toUpperCase().contains("CREATE OR REPLACE"))
                                                {
                                                    purpose = "CREATE OR REPLACE";
                                                }
                                                else
                                                {
                                                    purpose = queries.get(i).toUpperCase().split(" ")[0];
                                                }
                                                Map updateOBJmap = new HashMap();

                                                updateOBJmap.put("OBJ_TYPE", objName[0]);
                                                updateOBJmap.put("PURPOSE", purpose);
                                                updateOBJmap.put("OBJ_NAME", objName[1].replaceAll("`", ""));
                                                updateOBJmap.put("QUERY_ID", queryID.get(i));
                                                sqlUtility.persist(ALIASNAME, updateOBJ.toString(), new MapSqlParameterSource(updateOBJmap));
                                            }
                                        }
                                        catch (Exception ex)
                                        {
                                            result = ex.getMessage();
                                            StringBuilder sbStackTrace = new StringBuilder();
                                            sbStackTrace.append(ex.toString());
                                            for (int j = 0; j < ex.getStackTrace().length; j++)
                                            {
                                                sbStackTrace.append(j).append(" ClassName : ").append(ex.getStackTrace()[j].getClassName()).append("\n");
                                                sbStackTrace.append(j).append(" MethodName : ").append(ex.getStackTrace()[j].getMethodName()).append("\n");
                                                sbStackTrace.append(j).append(" LineNumber : ").append(ex.getStackTrace()[j].getLineNumber()).append("\n");
                                            }

//                                            exeStatus = "Error";
                                            endTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(Calendar.getInstance().getTime());
                                            status = "FAIL";
                                            anyFail = true;
                                        }

                                        if ("Log Table Required".equals(logTable.get(i)) && "PASS".equals(status))
                                        {
                                            //Call Procedures For Creating Log Table & Trigger for CREATE TABLE Query
                                            if (queries.get(i).trim().toUpperCase().startsWith("CREATE TABLE "))
                                            {
                                                try
                                                {
                                                    QueryMaker qMaker = new QueryMaker();
                                                    DBLogUtility dbLogUtil = new DBLogUtility();
                                                    String table = qMaker.getTableNameFromCreateTableQuery(queries.get(i).trim());
                                                    if ("MYSQL".equals(databaseType))
                                                    {
                                                        //Call Procedure for Creating Log Table
                                                        dbLogUtil.createMySQLLogTable(serverName, batches.getString("DATABASE_NAME") + "_LOG", batches.getString("DATABASE_NAME"), table, true);

                                                        //Create Log Trigger in MYSQL
                                                        dbLogUtil.createMySQLLogTrigger(serverName, batches.getString("DATABASE_NAME") + "_LOG", batches.getString("DATABASE_NAME"), table, true);
                                                    }
                                                    else if ("ORACLE".equals(databaseType))
                                                    {
                                                        //Call Procedure for Creating Log Table
                                                        dbLogUtil.createOracleLogTable(serverName, batches.getString("DATABASE_NAME") + "_LOG", batches.getString("DATABASE_NAME"), table, true);

                                                        //Call Procedure for Creating Log Trigger
                                                        dbLogUtil.createOracleLogTrigger(serverName, batches.getString("DATABASE_NAME") + "_LOG", batches.getString("DATABASE_NAME"), table, true);
                                                    }

                                                    //Update LOG_TABLE field with 'Log Table Created' In DATA_REQUEST_QUERY
                                                    query.delete(0, query.length());
                                                    query.append("UPDATE DATA_REQUEST_QUERY ");
                                                    query.append("SET LOG_TABLE = 'Log Table Created' ");
                                                    query.append("WHERE QUERY_ID = ").append(queryID.get(i));
                                                    sqlUtility.persist(ALIASNAME, query.toString());
                                                }
                                                catch (InvalidResultSetAccessException | ClassNotFoundException | SQLException e)
                                                {
                                                    Logger.ErrorLogger(e);

                                                    //Update LOG_TABLE field with 'Log Table Not Created' In DATA_REQUEST_QUERY
                                                    query.delete(0, query.length());
                                                    query.append("UPDATE DATA_REQUEST_QUERY ");
                                                    query.append("SET LOG_TABLE = 'Log Table Not Created' ");
                                                    query.append("WHERE QUERY_ID = ").append(queryID.get(i));
                                                    sqlUtility.persist(ALIASNAME, query.toString());
                                                }
                                            }
                                        }
                                    }

                                    results.add(result);
                                    statuses.add(status);
                                    startTimes.add(startTime);
                                    endTimes.add(endTime);
                                }
                            }
                            catch (SQLException e)
                            {
                                exeStatus = "Error";
                                throw new SQLException("Error in Executing Data Request", e);
                            }
                            finally
                            {
                                try
                                {
                                    if (tran.getConnection() != null && !tran.getConnection().isClosed())
                                    {
                                        if ("3".equals(execType) && anyFail)
                                        {
                                            tran.rollbackChanges();
                                        }
                                        else
                                        {
                                            tran.commitChanges();
                                        }
                                        tran.closeConn();
                                    }
                                }
                                catch (Exception e)
                                {
                                    Logger.ErrorLogger(e);
                                }
                            }

                            for (int i = 0; i < queryID.size(); i++)
                            {
                                //Put In Values On The Current Index Of All List In A Map
                                HashMap hmap1 = new HashMap();
                                hmap1.put("queryId", queryID.get(i));
                                hmap1.put("result", results.get(i));
                                hmap1.put("status", statuses.get(i));
                                hmap1.put("startTime", startTimes.get(i));
                                hmap1.put("endTime", endTimes.get(i));
                                MapSqlParameterSource param1 = new MapSqlParameterSource(hmap1);
                                //Update Production Server's Data In DATA_REQUEST_QUERY
                                query.delete(0, query.length());
                                query.append("UPDATE DATA_REQUEST_QUERY ");
                                query.append("SET PROD_EXE_RESULT = :result, ");
                                query.append("PROD_EXE_STATUS = :status, ");
                                query.append("PROD_EXE_START_DATE = :startTime, ");
                                query.append("PROD_EXE_END_DATE = :endTime ");
                                query.append("WHERE QUERY_ID = :queryId");
                                sqlUtility.persist(ALIASNAME, query.toString(), param1);
                            }
                        }
                    }
                    //Set EXECUTION_STATUS Of The Request
                    for (int i = 0; i < statuses.size(); i++)
                    {
                        if ("FAIL".equals(statuses.get(i)))
                        {
                            exeStatus = "Partial";
                            break;
                        }
                    }
                    if ("".equals(exeStatus))
                    {
                        exeStatus = "Done";
                    }
                    if ("3".equals(execType) && anyFail)
                    {
                        exeStatus = "Rollbacked";
                    }
                }

                //Update Production Server's Data In DATA_REQUEST_MASTER
                query.delete(0, query.length());
                query.append("UPDATE DATA_REQUEST_MASTER ");
                query.append("SET EXECUTED_BY = :executedBy, ");
                query.append("EXECUTED_FROM_HOST = :host, ");
                query.append("EXECUTION_DATE = '").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(Calendar.getInstance().getTime())).append("', ");
                query.append("EXECUTION_STATUS = '").append(exeStatus).append("' ");
                query.append("WHERE DATA_REQ_ID = :dataReqId");
                sqlUtility.persist(ALIASNAME, query.toString(), param);
            }
        }
        else
        {
            exeStatus = "Already " + reqStatus;
        }
        return exeStatus;
    }

    public String getObjName(String query, String databaseType)
    {
        String reqQuery = query.toUpperCase();
        boolean dropCmd = false;
        if (reqQuery.startsWith("CREATE OR REPLACE"))
        {
            query = reqQuery.substring(18).trim();
        }
        else if (reqQuery.startsWith("ALTER"))
        {
            query = reqQuery.substring(6).trim();
        }
        else if (reqQuery.startsWith("DROP"))
        {
            query = reqQuery.substring(5).trim();
            dropCmd = true;
        }
        else if (reqQuery.startsWith("CREATE"))
        {
            query = reqQuery.substring(7).trim();
        }

        String objName = "";
        Map<String, String> objType = new HashMap();
        objType.put("TABLE", " ");
        objType.put("FUNCTION", "(");
        objType.put("PROCEDURE", "AS");
        objType.put("INDEX", " ");
        objType.put("TRIGGER", " ");
        objType.put("PACKAGE", "AS");
        objType.put("PACKAGE BODY", "IS");
        objType.put("TYPE", "AS");
        objType.put("TYPE BODY", "AS");
        objType.put("VIEW", " ");
        Set<String> keys = objType.keySet();
        if ("MYSQL".equals(databaseType))
        {
            for (String key : keys)
            {
                String strKey = key;
                if (strKey.equalsIgnoreCase(query.substring(0, query.indexOf(" "))))
                {
                    query = query.substring(strKey.length() + 1).trim();
                    if (dropCmd)
                    {
                        objName = key + " " + query;
                    }
                    else
                    {
                        objName = key + " " + query.substring(0, query.indexOf(objType.get(key))).trim();
                    }
                    break;
                }
            }
        }
        else if ("ORACLE".equals(databaseType))
        {
            for (String key : keys)
            {
                String strKey = key;
                String value = objType.get(key);
                if (query.startsWith(strKey))
                {
                    query = query.substring(strKey.length() + 1).trim();
                    if (!dropCmd)
                    {
                        query = query.substring(0, query.indexOf(value)).trim();
                    }
                    query = query.substring(query.indexOf(".") + 1).trim();
                    objName = key + " " + query;
                    break;
                }
            }
        }
        return objName.replace(";", "");
    }

    public String removewhiteSpace(String query)
    {
        query = query.replaceAll("\t", " ");
        query = query.replaceAll("\r", " ");
        query = query.replaceAll("\n", " ");
        while (query.contains("  "))
        {
            query = query.replaceAll("  ", " ");
        }
        return query.trim();
    }

    public void updateForDataVrs(SQLTranUtility tran, String query, String queryID, String databaseType, String colName) throws ClassNotFoundException, SQLException
    {
        String reqQuery = query.toUpperCase();
        StringBuilder insertQuery = new StringBuilder();
        insertQuery.append(" UPDATE DATA_REQUEST_QUERY SET ");
        insertQuery.append(colName + " =:" + colName);
        insertQuery.append(" WHERE QUERY_ID = :QUERY_ID");
        Map map = new HashMap();
        map.put("QUERY_ID", queryID);
        if ("MYSQL".equals(databaseType))
        {
            map.put(colName, getQueryDtl(tran, reqQuery, databaseType, colName));
            sqlUtility.persist(ALIASNAME, insertQuery.toString(), new MapSqlParameterSource(map));

        }
        else if ("ORACLE".equals(databaseType))
        {
            StringBuilder getCreatStmnt = new StringBuilder();
            getCreatStmnt.append("SELECT SYS.DBMS_METADATA.GET_DDL('TABLE',U.TABLE_NAME) FROM USER_ALL_TABLES U ");
            getCreatStmnt.append(" WHERE TABLE_NAME=:TABLE");
            StringBuilder getINDStmnt = new StringBuilder();
            getINDStmnt.append("  SELECT USER_TABLES.TABLE_NAME, USER_INDEXES.INDEX_NAME,USER_IND_COLUMNS.COLUMN_NAME ");
            getINDStmnt.append("  FROM USER_TABLES JOIN USER_INDEXES ON USER_INDEXES.TABLE_NAME = USER_TABLES.TABLE_NAME");
            getINDStmnt.append("  JOIN USER_IND_COLUMNS ON USER_INDEXES.INDEX_NAME = USER_IND_COLUMNS.INDEX_NAME");
            getINDStmnt.append("  WHERE USER_INDEXES.INDEX_NAME=:INDEX_NAME");
            getINDStmnt.append("  ORDER BY USER_TABLES.TABLE_NAME,USER_INDEXES.INDEX_NAME");

            StringBuilder getcreateStmt = new StringBuilder();
            getcreateStmt.append("  SELECT DBMS_METADATA.GET_DDL(:OBJTYPE,:OBJNAME) DDL FROM DUAL");

            if (reqQuery.contains("CREATE OR REPLACE") && !colName.equalsIgnoreCase("NEW_QUERY_TEXT"))
            {
                map.put(colName, getQueryDtl(tran, reqQuery, databaseType, colName));
                sqlUtility.persist(ALIASNAME, insertQuery.toString(), new MapSqlParameterSource(map));
            }
            else if (reqQuery.contains("ALTER"))
            {
                map.put(colName, getQueryDtl(tran, reqQuery, databaseType, colName));
                sqlUtility.persist(ALIASNAME, insertQuery.toString(), new MapSqlParameterSource(map));
            }
            if (reqQuery.contains("DROP") && !colName.equalsIgnoreCase("NEW_QUERY_TEXT"))
            {
                map.put(colName, getQueryDtl(tran, reqQuery, databaseType, colName));
                sqlUtility.persist(ALIASNAME, insertQuery.toString(), new MapSqlParameterSource(map));
            }
        }
    }

    public String getQueryDtl(SQLTranUtility tran, String query, String databaseType, String colName)
    {
        try
        {
            List<Map> data = null;
            String strQryDtl = "";
            query = removewhiteSpace(query);
            if ("MYSQL".equals(databaseType))
            {
                if (query.startsWith("USE"))
                {
                    tran.persist(query);
                }
                else
                {
                    String objName = getObjName(query, databaseType);
                    data = tran.getList("SHOW CREATE " + objName);
                    if (objName.contains("TRIGGER"))
                    {
                        strQryDtl = data.get(0).get("SQL Original Statement").toString();
                    }
                    else
                    {
                        objName = objName.substring(0, objName.indexOf(" "));
                        strQryDtl = data.get(0).get("Create " + objName.substring(0, 1).toUpperCase() + objName.substring(1)).toString();
                    }
                }
            }
            else if ("ORACLE".equals(databaseType))
            {
                StringBuilder getINDCreatStmnt = new StringBuilder();
                getINDCreatStmnt.append("  SELECT USER_TABLES.TABLE_NAME, USER_INDEXES.INDEX_NAME,USER_IND_COLUMNS.COLUMN_NAME ");
                getINDCreatStmnt.append("  FROM USER_TABLES JOIN USER_INDEXES ON USER_INDEXES.TABLE_NAME = USER_TABLES.TABLE_NAME");
                getINDCreatStmnt.append("  JOIN USER_IND_COLUMNS ON USER_INDEXES.INDEX_NAME = USER_IND_COLUMNS.INDEX_NAME");
                getINDCreatStmnt.append("  WHERE USER_INDEXES.INDEX_NAME=:OBJNAME");
                getINDCreatStmnt.append("  ORDER BY USER_TABLES.TABLE_NAME,USER_INDEXES.INDEX_NAME");
                StringBuilder getcreateStmt = new StringBuilder();
                getcreateStmt.append("  SELECT DBMS_METADATA.GET_DDL(:OBJTYPE, :OBJNAME) DDL FROM DUAL");
                Map map = new HashMap();
                String objName = getObjName(query, databaseType);
                if (query.startsWith("INDEX"))
                {
                    if (colName.equalsIgnoreCase("NEW_QUERY_TEXT") && query.contains("RENAME TO"))
                    {
                        map.put("OBJNAME", query.substring(query.lastIndexOf(" ")).trim());
                    }
                    else
                    {
                        map.put("OBJNAME", objName.substring(objName.indexOf(" ")).trim());
                    }
                    map.put("OBJTYPE", objName.substring(0, objName.indexOf(" ")).trim());
                    data = tran.getList(getINDCreatStmnt.toString(), new MapSqlParameterSource(map));
                    StringBuilder createQury = new StringBuilder();
                    for (int i = 0; i < data.size(); i++)
                    {
                        createQury.append(" CREATE INDEX ").append(data.get(i).get("INDEX_NAME").toString());
                        createQury.append(" ON ").append(data.get(i).get("TABLE_NAME")).append(" (").append(data.get(i).get("COLUMN_NAME").toString()).append(");");
                    }
                    strQryDtl = createQury.toString();
                }
                else
                {
                    if (colName.equalsIgnoreCase("NEW_QUERY_TEXT") && query.contains("RENAME TO"))
                    {
                        map.put("OBJNAME", query.substring(query.lastIndexOf(" ")).trim());
                    }
                    else
                    {
                        map.put("OBJNAME", objName.substring(objName.indexOf(" ")).trim());
                    }
                    map.put("OBJTYPE", objName.substring(0, objName.indexOf(" ")).trim());
                    data = tran.getList(getcreateStmt.toString(), new MapSqlParameterSource(map));
                    strQryDtl = data.get(0).get("DDL").toString();
                }
            }
            return strQryDtl;
        }
        catch (Exception ex)
        {
            return "NA";
        }
    }

    public String cancelViewRequest(final String dataReqId, final String executedBy, final String host) throws ClassNotFoundException, SQLException
    {
        StringBuilder query = new StringBuilder();
        query.append("SELECT ENTRY_BY FROM DATA_REQUEST_MASTER WHERE DATA_REQ_ID =");
        query.append(dataReqId);

        String result = sqlUtility.getString(ALIASNAME, query.toString());
        if (!result.equals(executedBy))
        {
            HardCodeProperty hcp = new HardCodeProperty();
            String validExecutors = hcp.getProperty("data_request_valid_request_processor");

            String[] executors = validExecutors.split(",");
            int exe = 0;
            for (; exe < executors.length; exe++)
            {
                if (executors[exe].trim().equals(executedBy))
                {
                    break;
                }
            }
            if (exe == executors.length)
            {
                return "Authentication Failed ! You do not have rights to cancel this Data Request.";
            }
        }

        HashMap hmap = new HashMap();
        hmap.put("dataReqId", dataReqId);
        hmap.put("executedBy", executedBy);
        hmap.put("host", host);
        MapSqlParameterSource param = new MapSqlParameterSource(hmap);

        //Check Execution Status
        query.delete(0, query.length());
        query.append("SELECT EXECUTION_STATUS FROM DATA_REQUEST_MASTER WHERE DATA_REQ_ID = :dataReqId");
        String reqStatus = sqlUtility.getString(ALIASNAME, query.toString(), param);
        if (reqStatus.equalsIgnoreCase("Pending"))
        {
            //Update Request In DATA_REQUEST_MASTER
            query.delete(0, query.length());
            query.append("UPDATE DATA_REQUEST_MASTER ");
            query.append("SET EXECUTED_BY = :executedBy, ");
            query.append("EXECUTED_FROM_HOST = :host, ");
            query.append("EXECUTION_DATE = '").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(Calendar.getInstance().getTime())).append("', ");
            query.append("EXECUTION_STATUS = 'Cancelled' ");
            query.append("WHERE DATA_REQ_ID = :dataReqId");
            int res = sqlUtility.persist(ALIASNAME, query.toString(), param);
            if (res == 1)
            {
                reqStatus = "Cancelled";
            }
            else
            {
                reqStatus = "Error";
            }
        }
        else
        {
            reqStatus = "Already " + reqStatus;
        }

        return reqStatus;
    }

    public String resumeViewRequest(final String dataReqId, final String executedBy, final String host) throws ClassNotFoundException, SQLException
    {
        StringBuilder query = new StringBuilder();
        query.append("SELECT ENTRY_BY FROM DATA_REQUEST_MASTER WHERE DATA_REQ_ID =");
        query.append(dataReqId);

        String result = sqlUtility.getString(ALIASNAME, query.toString());

        if (!result.equals(executedBy))
        {
            HardCodeProperty hcp = new HardCodeProperty();
            String validExecutors = hcp.getProperty("data_request_valid_request_processor");

            String[] executors = validExecutors.split(",");
            int exe = 0;
            for (; exe < executors.length; exe++)
            {
                if (executors[exe].trim().equals(executedBy))
                {
                    break;
                }
            }
            if (exe == executors.length)
            {
                return "Authentication Failed ! You do not have rights to Resume this Data Request.";
            }
        }

        HashMap hmap = new HashMap();
        hmap.put("dataReqId", dataReqId);
        hmap.put("executedBy", executedBy);
        hmap.put("host", host);
        MapSqlParameterSource param = new MapSqlParameterSource(hmap);

        //Check Execution Status
        query.delete(0, query.length());
        query.append("SELECT EXECUTION_STATUS FROM DATA_REQUEST_MASTER WHERE DATA_REQ_ID = :dataReqId");
        String reqStatus = sqlUtility.getString(ALIASNAME, query.toString(), param);

        if (reqStatus.equalsIgnoreCase("Halted"))
        {
            //Update Request In DATA_REQUEST_MASTER
            query.delete(0, query.length());
            query.append("UPDATE DATA_REQUEST_MASTER ");
            query.append("SET EXECUTED_BY = :executedBy, ");
            query.append("EXECUTED_FROM_HOST = :host, ");
            query.append("EXECUTION_DATE = '").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(Calendar.getInstance().getTime())).append("', ");
            query.append("EXECUTION_STATUS = 'Pending' ");
            query.append("WHERE DATA_REQ_ID = :dataReqId");
            int res = sqlUtility.persist(ALIASNAME, query.toString(), param);
            if (res == 1)
            {
                reqStatus = "Resumed";
            }
            else
            {
                reqStatus = "Error";
            }
        }
        else
        {
            reqStatus = "Already " + reqStatus;
        }

        return reqStatus;
    }

    public String haltViewRequest(final String dataReqId, final String executedBy, final String host) throws ClassNotFoundException, SQLException
    {
        StringBuilder query = new StringBuilder();
        query.append("SELECT ENTRY_BY FROM DATA_REQUEST_MASTER WHERE DATA_REQ_ID =");
        query.append(dataReqId);

        String result = sqlUtility.getString(ALIASNAME, query.toString());
        if (!result.equals(executedBy))
        {
            HardCodeProperty hcp = new HardCodeProperty();
            String validExecutors = hcp.getProperty("data_request_valid_request_processor");

            String[] executors = validExecutors.split(",");
            int exe = 0;
            for (; exe < executors.length; exe++)
            {
                if (executors[exe].trim().equals(executedBy))
                {
                    break;
                }
            }
            if (exe == executors.length)
            {
                return "Authentication Failed ! You do not have rights to Halt this Data Request.";
            }
        }

        HashMap hmap = new HashMap();
        hmap.put("dataReqId", dataReqId);
        hmap.put("executedBy", executedBy);
        hmap.put("host", host);
        MapSqlParameterSource param = new MapSqlParameterSource(hmap);

        //Check Execution Status
        query.delete(0, query.length());
        query.append("SELECT EXECUTION_STATUS FROM DATA_REQUEST_MASTER WHERE DATA_REQ_ID = :dataReqId");
        String reqStatus = sqlUtility.getString(ALIASNAME, query.toString(), param);

        if (reqStatus.equalsIgnoreCase("Pending"))
        {
            //Update Request In DATA_REQUEST_MASTER
            query.delete(0, query.length());
            query.append("UPDATE DATA_REQUEST_MASTER ");
            query.append("SET EXECUTED_BY = :executedBy, ");
            query.append("EXECUTED_FROM_HOST = :host, ");
            query.append("EXECUTION_DATE = '").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(Calendar.getInstance().getTime())).append("', ");
            query.append("EXECUTION_STATUS = 'Halted' ");
            query.append("WHERE DATA_REQ_ID = :dataReqId");
            int res = sqlUtility.persist(ALIASNAME, query.toString(), param);
            if (res == 1)
            {
                reqStatus = "Halted";
            }
            else
            {
                reqStatus = "Error";
            }
        }
        else
        {
            reqStatus = "Already " + reqStatus;
        }

        return reqStatus;
    }

    public synchronized int createNewRequest(final String dataReqId, final String entryBy) throws ClassNotFoundException, SQLException
    {
        HashMap hmap = new HashMap();
        hmap.put("dataReqId", dataReqId);
        hmap.put("entryBy", entryBy);
        MapSqlParameterSource param = new MapSqlParameterSource(hmap);
        StringBuilder query = new StringBuilder();

        //Select Request From DATA_REQUEST_MASTER Table
        query.append("SELECT PROJ_ID, REQ_ID, PURPOSE, EXECUTION_TYPE FROM DATA_REQUEST_MASTER WHERE DATA_REQ_ID = :dataReqId");
        SqlRowSet srs = sqlUtility.getRowSet(ALIASNAME, query.toString(), param);
        if (srs != null && srs.next())
        {
            //Check REPLACEMENT_ID Is 'Null' Or 'Not Null'
            query.delete(0, query.length());
            query.append("SELECT REPLACEMENT_ID FROM DATA_REQUEST_MASTER WHERE DATA_REQ_ID = :dataReqId");
            int replac_Id = sqlUtility.getInt(ALIASNAME, query.toString(), param);

            if (replac_Id == 0)
            {
                int newReqId = 0;
                hmap.put("projId", srs.getString("PROJ_ID"));
                hmap.put("reqId", srs.getString("REQ_ID"));
                hmap.put("purpose", srs.getString("PURPOSE"));
                hmap.put("execType", srs.getString("EXECUTION_TYPE"));
                param = new MapSqlParameterSource(hmap);

                String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(Calendar.getInstance().getTime());
                //Copy Request In DATA_REQUEST_MASTER Table
                query.delete(0, query.length());
                query.append("INSERT INTO DATA_REQUEST_MASTER(PROJ_ID, REQ_ID, PURPOSE, EXECUTION_TYPE, ENTRY_BY, ENTRY_DATE, EXECUTION_STATUS) ");
                query.append("VALUES(:projId, :reqId, :purpose, :execType, :entryBy, '").append(date).append("', 'Pending')");
                int masterIns = sqlUtility.persist(ALIASNAME, query.toString(), param);

                if (masterIns > 0)
                {
                    //Get DATA_REQ_ID To Update Newly Copied Request And Insert Into DATA_REQUEST_BATCH Table
                    newReqId = sqlUtility.getInt(ALIASNAME, "SELECT DATA_REQ_ID FROM DATA_REQUEST_MASTER ORDER BY DATA_REQ_ID DESC LIMIT 1");

                    //Update REPLACEMENT_ID In DATA_REQUEST_MASTER Table
                    query.delete(0, query.length());
                    query.append("UPDATE DATA_REQUEST_MASTER ");
                    query.append("SET REPLACEMENT_ID = ").append(newReqId).append(" ");
                    query.append("WHERE DATA_REQ_ID = ").append(dataReqId);
                    masterIns = sqlUtility.persist(ALIASNAME, query.toString());

                    if (masterIns > 0)
                    {
                        //Get BATCH_ID(s) For Those Queries Which Not Executed Yet
                        query.delete(0, query.length());
                        query.append("SELECT DISTINCT BTC.BATCH_ID BATCH_ID ");
                        query.append("FROM DATA_REQUEST_BATCH BTC ");
                        query.append("INNER JOIN DATA_REQUEST_QUERY QRY ON QRY.BATCH_ID = BTC.BATCH_ID ");
                        query.append("WHERE DATA_REQ_ID = ").append(dataReqId);
                        if (hmap.get("execType").equals("1") || hmap.get("execType").equals("2"))
                        {
                            query.append(" AND PROD_EXE_STATUS<>'PASS'");
                        }
                        srs = sqlUtility.getRowSet(ALIASNAME, query.toString());

                        if (srs != null)
                        {
                            SqlRowSet batchRows = null;
                            while (srs.next())
                            {
                                query.delete(0, query.length());
                                query.append("SELECT DATABASE_ID, SYNTAX_VERIFIED_ON, ");
                                query.append("QUERY_ID, QUERY_TEXT, ");
                                query.append("DEV_EXE_RESULT, DEV_EXE_START_DATE, DEV_EXE_END_DATE, DEV_EXE_STATUS, ");
                                query.append("PROD_EXE_RESULT, PROD_EXE_START_DATE, PROD_EXE_END_DATE, PROD_EXE_STATUS, BACKUP, LOG_TABLE ");
                                query.append("FROM DATA_REQUEST_BATCH BTC ");
                                query.append("INNER JOIN DATA_REQUEST_QUERY QRY ON QRY.BATCH_ID = BTC.BATCH_ID ");
                                query.append("WHERE DATA_REQ_ID = ").append(dataReqId);
                                query.append(" AND BTC.BATCH_ID = ").append(srs.getString("BATCH_ID"));
                                if (hmap.get("execType").equals("1") || hmap.get("execType").equals("2"))
                                {
                                    query.append(" AND (PROD_EXE_STATUS<>'PASS' OR UPPER(QUERY_TEXT LIKE 'USE %'))");
                                }
                                batchRows = sqlUtility.getRowSet(ALIASNAME, query.toString());

                                if (batchRows != null)
                                {
                                    batchRows.next();
                                    //Insert One Batch Into DATA_REQUEST_BATCH Table
                                    query.delete(0, query.length());
                                    query.append("INSERT INTO DATA_REQUEST_BATCH(DATA_REQ_ID, DATABASE_ID, SYNTAX_VERIFIED_ON) ");
                                    query.append("VALUES(").append(newReqId).append(", '").append(batchRows.getString("DATABASE_ID")).append("', '");
                                    query.append(batchRows.getString("SYNTAX_VERIFIED_ON")).append("')");
                                    int batchIns = sqlUtility.persist(ALIASNAME, query.toString());

                                    if (batchIns > 0)
                                    {
                                        //Get BATCH_ID To Insert Into DATA_REQUEST_QUERY Table
                                        int batchID = sqlUtility.getInt(ALIASNAME, "SELECT BATCH_ID FROM DATA_REQUEST_BATCH ORDER BY BATCH_ID DESC LIMIT 1");

                                        batchRows.beforeFirst();
                                        while (batchRows.next())
                                        {
                                            //Insert All Queries Of One Batch Into DATA_REQUEST_QUERY
                                            query.delete(0, query.length());
                                            query.append("INSERT INTO DATA_REQUEST_QUERY(BATCH_ID, QUERY_TEXT, DEV_EXE_RESULT, DEV_EXE_START_DATE, DEV_EXE_END_DATE, DEV_EXE_STATUS, BACKUP, LOG_TABLE) ");
                                            query.append("VALUES(").append(batchID).append(", :query, :result, :startTime, :endTime, :exeStatus, :backup, :logTable)");
                                            HashMap qmap = new HashMap();
                                            qmap.put("query", batchRows.getString("QUERY_TEXT"));
                                            qmap.put("result", batchRows.getString("DEV_EXE_RESULT"));
                                            qmap.put("startTime", batchRows.getString("DEV_EXE_START_DATE"));
                                            qmap.put("endTime", batchRows.getString("DEV_EXE_END_DATE"));
                                            qmap.put("exeStatus", batchRows.getString("DEV_EXE_STATUS"));
                                            if ("Backup Taken".equals(batchRows.getString("BACKUP")) || "Backup Not Taken".equals(batchRows.getString("BACKUP")))
                                            {
                                                qmap.put("backup", "Backup Required");
                                            }
                                            else
                                            {
                                                qmap.put("backup", batchRows.getString("BACKUP"));
                                            }
                                            qmap.put("logTable", batchRows.getString("LOG_TABLE"));
                                            param = new MapSqlParameterSource(qmap);
                                            sqlUtility.persist(ALIASNAME, query.toString(), param);
                                        }
                                    }
                                    else
                                    {
                                        Logger.DataLogger("Unable To Insert Reocrd Into DATA_REQUEST_BATCH");
                                    }
                                }
                                else
                                {
                                    Logger.DataLogger("Unable To Get Details For Batch Id " + srs.getString("BATCH_ID"));
                                }
                            }
                        }
                        else
                        {
                            Logger.DataLogger("BATCH_ID Not Found For Those Queries Which Not Executed Yet");
                        }
                    }
                    else
                    {
                        Logger.DataLogger("Unable To Update Reocrd Into DATA_REQUEST_MASTER");
                    }
                }
                else
                {
                    Logger.DataLogger("Unable To Insert Reocrd Into DATA_REQUEST_MASTER");
                }
                return newReqId;
            }
            else
            {
                return replac_Id;
            }
        }
        else
        {
            Logger.DataLogger("Unable To Find The Data Request Id " + dataReqId);
            return 0;
        }
    }

    private void checkLogSchemaExistance(final List records) throws ClassNotFoundException, SQLException
    {
        if (records != null && !records.isEmpty())
        {
            List<String> batchList = new ArrayList<String>();

            Map r = null;
            ServerPropertyReader propReader = new ServerPropertyReader();
            int avail = -1;
            for (int i = 0; i < records.size(); i++)
            {
                r = (Map) records.get(i);
                String query = r.get("QUERY_TEXT").toString().replaceAll("\\s{1,}", " ").trim();
                if (query.toUpperCase().startsWith("CREATE TABLE "))
                {
                    if (!batchList.contains(r.get("BATCH_TMP_ID").toString().trim()))
                    {
                        batchList.add(r.get("BATCH_TMP_ID").toString().trim());
                        if ("MYSQL".equals(r.get("SERVER_TYPE").toString().trim()))
                        {
                            avail = sqlUtility.getInt(propReader.getActualServer(r.get("SERVER_ID").toString().trim()), "SELECT COUNT(SCHEMA_NAME) FROM information_schema.schemata WHERE SCHEMA_NAME = '" + r.get("DATABASE_NAME").toString().trim() + "_LOG'");
                        }
                        else if ("ORACLE".equals(r.get("SERVER_TYPE").toString().trim()))
                        {
                            avail = sqlUtility.getInt(propReader.getActualServer(r.get("SERVER_ID").toString().trim()), "SELECT COUNT(USERNAME) FROM SYS.DBA_USERS WHERE USERNAME = '" + r.get("DATABASE_NAME").toString().trim() + "_LOG'");
                        }
                    }
                    if (avail == 0)
                    {
                        r.put("LOG_TABLE", "Log Schema Not Available");
                    }
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getReportDataLoad() throws ClassNotFoundException, SQLException
    {
        StringBuilder query = new StringBuilder();

        query.append(" SELECT SM.SERVER_ID, SM.SERVER_NAME, SM.SERVER_TYPE,")
                .append(" DM.DATABASE_ID, DM.DATABASE_NAME, PM.PRJ_ID, PM.PRJ_NAME")
                .append(" FROM SERVER_MASTER SM")
                .append(" INNER JOIN DATABASE_MASTER DM ON")
                .append(" DM.SERVER_ID = SM.SERVER_ID")
                .append(" INNER JOIN PROJECT_DB_MAPPING PDM ON")
                .append(" PDM.DATABASE_ID = DM.DATABASE_ID")
                .append(" INNER JOIN WFM.PROJECT_MST PM ON")
                .append(" PM.PRJ_ID = PDM.PROJECT_ID")
                .append(" ORDER BY UPPER(PM.PRJ_NAME)");

        return sqlUtility.getList(ALIASNAME, query.toString());
    }
}