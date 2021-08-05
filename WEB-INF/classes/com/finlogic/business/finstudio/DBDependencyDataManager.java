/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.business.finstudio;

import com.finlogic.util.Logger;
import com.finlogic.util.findatareqexecutor.DBObjectInfoBean;
import com.finlogic.util.findatareqexecutor.DependencyInfoBean;
import com.finlogic.util.findatareqexecutor.QueryInfoBean;
import com.finlogic.util.findatareqexecutor.ServerPropertyReader;
import com.finlogic.util.persistence.SQLTranUtility;
import com.finlogic.util.persistence.SQLUtility;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.support.rowset.SqlRowSet;

/**
 *
 * @author Sonam Patel
 */
/**
 *
 * @Edited by Jeegar Kumar Patel
 */
public class DBDependencyDataManager
{

    private static final String ALIASNAME = "finstudio_mysql";
    private static SQLUtility sqlUtility = new SQLUtility();

    public Map getDatabaseInfo(final String dbID) throws ClassNotFoundException, SQLException
    {
        Map map = new HashMap();
        StringBuilder query = new StringBuilder();
        query.append("SELECT SMAST.SERVER_ID, SERVER_NAME, SERVER_TYPE, DATABASE_NAME FROM DATABASE_MASTER DBMAST ");
        query.append("INNER JOIN SERVER_MASTER SMAST ON SMAST.SERVER_ID = DBMAST.SERVER_ID ");
        query.append("WHERE DATABASE_ID = ").append(dbID);
        SqlRowSet srs = sqlUtility.getRowSet("finstudio_mysql", query.toString());
        if (srs != null && srs.next())
        {
            map.put("serverid", srs.getString("SERVER_ID"));
            map.put("servername", srs.getString("SERVER_NAME"));
            map.put("servertype", srs.getString("SERVER_TYPE"));
            map.put("databasename", srs.getString("DATABASE_NAME"));
            ServerPropertyReader propReader = new ServerPropertyReader();
            map.put("serveralias", propReader.getActualServer(srs.getString("SERVER_ID")));
            return map;
        }
        else
        {
            return null;
        }
    }
    public Map getDatabaseInfoObj(final String dbID, final String server, String serv) throws ClassNotFoundException, SQLException
    {
        Map map = new HashMap();
        StringBuilder query = new StringBuilder();
        query.append("SELECT SMAST.SERVER_ID, SERVER_NAME, SERVER_TYPE, DATABASE_NAME FROM DATABASE_MASTER DBMAST ");
        query.append("INNER JOIN SERVER_MASTER SMAST ON SMAST.SERVER_ID = DBMAST.SERVER_ID ");
        query.append("WHERE DATABASE_ID = ").append(dbID);
        SqlRowSet srs = sqlUtility.getRowSet("finstudio_mysql", query.toString());
        if (srs != null && srs.next())
        {
            map.put("serverid", srs.getString("SERVER_ID"));
            map.put("servername", srs.getString("SERVER_NAME"));
            map.put("servertype", srs.getString("SERVER_TYPE"));
            map.put("databasename", srs.getString("DATABASE_NAME"));
            ServerPropertyReader propReader = new ServerPropertyReader();
            map.put("serveralias", propReader.getActualServerboth(srs.getString("SERVER_ID"), serv));
            return map;
        }
        else
        {
            return null;
        }
    }
    public Map getDatabaseInfoboth(final String dbID, final String serv) throws ClassNotFoundException, SQLException
    {
        Map map = new HashMap();
        StringBuilder query = new StringBuilder();
        query.append("SELECT SMAST.SERVER_ID, SERVER_NAME, SERVER_TYPE, DATABASE_NAME FROM DATABASE_MASTER DBMAST ");
        query.append("INNER JOIN SERVER_MASTER SMAST ON SMAST.SERVER_ID = DBMAST.SERVER_ID ");
        query.append("WHERE DATABASE_ID = ").append(dbID);
        SqlRowSet srs = sqlUtility.getRowSet("finstudio_mysql", query.toString());
        if (srs != null && srs.next())
        {
            map.put("serverid", srs.getString("SERVER_ID"));
            map.put("servername", srs.getString("SERVER_NAME"));
            map.put("servertype", srs.getString("SERVER_TYPE"));
            map.put("databasename", srs.getString("DATABASE_NAME"));
            ServerPropertyReader propReader = new ServerPropertyReader();
            map.put("serveralias", propReader.getActualServerboth(srs.getString("SERVER_ID"), serv));
            if(serv.equals("Prod"))
            {
                map.put("server", "Prod");
            }
            else
            {
                map.put("server", "Other");
            }
            return map;
        }
        else
        {
            return null;
        }
    }

    public List<DependencyInfoBean> isSensitive(final String dbID, final QueryInfoBean qBean) throws ClassNotFoundException, SQLException
    {
        List<DependencyInfoBean> depBeanList = new ArrayList<>();
        DependencyInfoBean depBean = null;
        String selQuery = "SELECT SERVER_NAME, DATABASE_NAME FROM (SELECT SERVER_ID, DATABASE_NAME FROM DATABASE_MASTER WHERE DATABASE_ID = " + dbID + ") DBMAST "
                + "INNER JOIN SERVER_MASTER SMAST ON SMAST.SERVER_ID = DBMAST.SERVER_ID";
        SqlRowSet srsSrvrDb = sqlUtility.getRowSet(ALIASNAME, selQuery);

        StringBuilder query;
        query = new StringBuilder();

        //Check For Sensitive Objects
        query.append("SELECT DATABASE_ID, OBJ_TYPE, OBJ_NAME, CLAUSE, SUB_CLAUSE, SUB_CLAUSE_TARGET FROM DATA_REQUEST_SENSITIVE_OBJECT ");
        query.append("WHERE DATABASE_ID = ").append(dbID);
        if (qBean.getDbObjInfo() != null && qBean.getDbObjInfo().getObjType() != null)
        {
            query.append(" AND (OBJ_TYPE IS NULL OR OBJ_TYPE = '").append(qBean.getDbObjInfo().getObjType()).append("')");
        }
        if (qBean.getDbObjInfo() != null && qBean.getDbObjInfo().getObjName() != null)
        {
            query.append(" AND (OBJ_NAME IS NULL OR OBJ_NAME = '").append(qBean.getDbObjInfo().getObjName()).append("')");
        }
        if (qBean.getClause() != null)
        {
            query.append(" AND (CLAUSE IS NULL OR CLAUSE = '").append(qBean.getClause()).append("')");
        }
        if (qBean.getSubClause() != null && !"".equals(qBean.getSubClause()))
        {
            query.append(" AND (SUB_CLAUSE IS NULL OR");
            if (qBean.getSubClause().contains(","))
            {
                query.append(" SUB_CLAUSE IN (");
                String[] subClsTrg = qBean.getSubClause().split(",");
                for (int i = 0; i < subClsTrg.length; i++)
                {
                    if (i < subClsTrg.length - 1)
                    {
                        query.append("'").append(subClsTrg[i]).append("', ");
                    }
                    else
                    {
                        query.append("'").append(subClsTrg[i]).append("'");
                    }
                }
                query.append(")");
            }
            else
            {
                query.append(" SUB_CLAUSE = '").append(qBean.getSubClause()).append("'");
            }
            query.append(")");
        }
        if (qBean.getSubClauseTarget() != null && !"".equals(qBean.getSubClauseTarget()) && qBean.getSubClauseTarget().trim().replaceAll("\\s{1,}", "").replaceAll(",", "").matches("[A-Za-z0-9_]+"))
        {
            query.append(" AND (SUB_CLAUSE_TARGET IS NULL OR");
            if (qBean.getSubClauseTarget().contains(","))
            {
                query.append(" SUB_CLAUSE_TARGET IN (");
                String[] subClsTrg = qBean.getSubClauseTarget().split(",");
                for (int i = 0; i < subClsTrg.length; i++)
                {
                    if (i < subClsTrg.length - 1)
                    {
                        query.append("'").append(subClsTrg[i]).append("', ");
                    }
                    else
                    {
                        query.append("'").append(subClsTrg[i]).append("'");
                    }
                }
                query.append(")");
            }
            else
            {
                query.append(" SUB_CLAUSE_TARGET = '").append(qBean.getSubClauseTarget()).append("'");
            }
            query.append(")");
        }

        SqlRowSet srs = sqlUtility.getRowSet(ALIASNAME, query.toString());

        if (srs != null)
        {
            while (srs.next())
            {
                depBean = new DependencyInfoBean();
                depBean.setDependency("Sensitive Object");
                if (srsSrvrDb != null)
                {
                    srsSrvrDb.beforeFirst();
                    if (srsSrvrDb.next())
                    {
                        depBean.setDepServer(srsSrvrDb.getString("SERVER_NAME"));
                        depBean.setDepSchema(srsSrvrDb.getString("DATABASE_NAME"));
                    }
                }

                depBean.setDepObjType(srs.getString("OBJ_TYPE"));
                depBean.setDepObjName(srs.getString("OBJ_NAME"));
                depBean.setDepColName(srs.getString("SUB_CLAUSE_TARGET"));
                depBeanList.add(depBean);
            }
        }
        return depBeanList;
    }

    public void checkSensitiveKeyword(final String dbID, final QueryInfoBean qBean, final String dbType, final String schema) throws ClassNotFoundException, SQLException
    {
        String subClsTrg = "";
        if ("DROP".equals(qBean.getClause()) || "DELETE".equals(qBean.getClause()))
        {
            SQLTranUtility tran = new SQLTranUtility();
            ServerPropertyReader propReader = new ServerPropertyReader();
            String serverAlias = sqlUtility.getString(ALIASNAME, "SELECT SERVER_ID FROM DATABASE_MASTER WHERE DATABASE_ID = " + dbID);
            String serverName = propReader.getActualServer(serverAlias);
            SqlRowSet colRows = null;
            try
            {
                tran.openConn(serverName);
                if ("MYSQL".equals(dbType))
                {
                    colRows = tran.getRowSet("SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = '" + schema + "' AND TABLE_NAME = '" + qBean.getDbObjInfo().getObjName() + "'");
                }
                else if ("ORACLE".equals(dbType))
                {
                    colRows = tran.getRowSet("SELECT COLUMN_NAME FROM ALL_TAB_COLS WHERE OWNER = '" + qBean.getDbObjInfo().getSchema() + "' AND TABLE_NAME = '" + qBean.getDbObjInfo().getObjName() + "'");
                }
            }
            finally
            {
                tran.closeConn();
            }

            if (colRows != null)
            {
                while (colRows.next())
                {
                    subClsTrg += colRows.getString("COLUMN_NAME") + ",";
                }
            }
            if (subClsTrg.endsWith(","))
            {
                subClsTrg = subClsTrg.substring(0, subClsTrg.lastIndexOf(","));
            }
        }
        else if (qBean.getSubClauseTarget() != null && !"".equals(qBean.getSubClauseTarget()))
        {
            subClsTrg = qBean.getSubClauseTarget();
        }

        String serverName = sqlUtility.getString(ALIASNAME, "SELECT SERVER_NAME FROM SERVER_MASTER WHERE SERVER_ID = (SELECT SERVER_ID FROM DATABASE_MASTER WHERE DATABASE_ID = " + dbID + ")");

        if (!"".equals(subClsTrg))
        {
            String[] columns = subClsTrg.split(",");
            SqlRowSet srs = sqlUtility.getRowSet(ALIASNAME, "SELECT KEYWORD FROM DATA_REQUEST_SENSITIVE_KEYWORD");
            if (srs != null)
            {
                String dep = "Target Column Name is containing Sensitive Keyword";
                String depColName = "";
                for (int i = 0; i < columns.length; i++)
                {
//                  Tokenizing logic removed by Vivek on 20-9-2013.

//                    String[] tokens = columns[i].split("[^a-zA-Z]");
//                    srs.beforeFirst();
//                    while (srs.next())
//                    {
//                        String keyword = srs.getString("KEYWORD");
//                        for (int j = 0; j < tokens.length; j++)
//                        {
//                            if (keyword.equalsIgnoreCase(tokens[j]))
//                            {
//                                depColName += columns[i] + ",";
//                                break;
//                            }
//                        }
//                    }
//                    
//                  Direct match of keyword will result in sensitivity                    
//
                    srs.beforeFirst();
                    while (srs.next())
                    {
                        String keyword = srs.getString("KEYWORD");
                        if (columns[i].toUpperCase().contains(keyword.toUpperCase()))
                        {
                            depColName += columns[i] + ",";
                            break;
                        }
                    }
                }
                if (depColName.endsWith(","))
                {
                    depColName = depColName.substring(0, depColName.lastIndexOf(","));
                }
                if (!"".equals(depColName))
                {
                    List<DependencyInfoBean> depBeanList = new ArrayList<DependencyInfoBean>();
                    DependencyInfoBean depBean = new DependencyInfoBean();
                    depBean.setDependency(dep);
                    depBean.setDepServer(serverName);
                    depBean.setDepSchema(qBean.getDbObjInfo().getSchema());
                    depBean.setDepObjType(qBean.getDbObjInfo().getObjType());
                    depBean.setDepObjName(qBean.getDbObjInfo().getObjName());
                    depBean.setDepColName(depColName);
                    depBeanList.add(depBean);
                    qBean.setDepInfo(depBeanList);
                }
            }
        }
    }

    public List<DependencyInfoBean> checkOracleDep(final List allServers, final DBObjectInfoBean dbObjBean) throws ClassNotFoundException, SQLException
    {
        List<DependencyInfoBean> depBeanList = new ArrayList<>();
        //SQLTranUtility tran = new SQLTranUtility();
        ServerPropertyReader propReader = new ServerPropertyReader();
        String query = "SELECT OWNER, NAME, TYPE, REFERENCED_OWNER FROM DBA_DEPENDENCIES WHERE TYPE IN ('VIEW', 'SYNONYM', 'MATERIALIZED VIEW', 'TABLE', 'TYPE') AND REFERENCED_OWNER = '" + dbObjBean.getSchema() + "' AND REFERENCED_NAME = '" + dbObjBean.getObjName() + "'";

        String dep = "Any one or more Oracle Objects are depenedent on Target Object";

        for (int i = 0; i < allServers.size(); i++)
        {
            try
            {
                String serverAlias = propReader.getActualServer(allServers.get(i).toString().split(":")[0]);
                //tran.openConn(serverAlias);
                //String dbType = tran.getConnection().getMetaData().getDatabaseProductName().toUpperCase();
                //if ("ORACLE".equals(dbType))
                if(allServers.get(i).toString().split(":")[0].equals("1")||
                        allServers.get(i).toString().split(":")[0].equals("2")||
                        allServers.get(i).toString().split(":")[0].equals("3")||
                        allServers.get(i).toString().split(":")[0].equals("4"))
                {
                    //SqlRowSet srs = tran.getRowSet(query);
                    SqlRowSet srs = sqlUtility.getRowSet(serverAlias, query);
                    if (srs != null)
                    {
                        while (srs.next())
                        {
                            DependencyInfoBean depBean = new DependencyInfoBean();
                            depBean.setDependency(dep);
                            depBean.setDepServer(allServers.get(i).toString().split(":")[1].trim());
                            depBean.setDepSchema(srs.getString("OWNER").trim());
                            depBean.setDepObjType(srs.getString("TYPE").trim());
                            depBean.setDepObjName(srs.getString("NAME").trim());
                            depBeanList.add(depBean);
                        }
                    }
                }
            }
            catch (Exception e)
            {
                Logger.ErrorLogger(e);
            }
            finally
            {
                try
                {
//                    if (tran.getConnection() != null && !tran.getConnection().isClosed())
//                    {
//                        tran.closeConn();
//                    }
                }
                catch (Exception e)
                {
                    Logger.ErrorLogger(e);
                }
            }
        }
        return depBeanList;
    }

    public List<DependencyInfoBean> checkOracleLogTableDep(final List allServers, final DBObjectInfoBean dbObjBean) throws ClassNotFoundException, SQLException
    {
        List<DependencyInfoBean> depBeanList = new ArrayList<>();
        //SQLTranUtility tran = new SQLTranUtility();
        ServerPropertyReader propReader = new ServerPropertyReader();
        String query = "SELECT OWNER, TABLE_NAME FROM DBA_TABLES WHERE OWNER = '" + dbObjBean.getSchema() + "_LOG' AND TABLE_NAME = '" + dbObjBean.getObjName() + "'";

        String dep = "Log table exist on Oracle";

        for (int i = 0; i < allServers.size(); i++)
        {
            try
            {
                String serverAlias = propReader.getActualServer(allServers.get(i).toString().split(":")[0]);
                //tran.openConn(serverAlias);
                //String dbType = tran.getConnection().getMetaData().getDatabaseProductName().toUpperCase();
//                if ("ORACLE".equals(dbType))
                if(allServers.get(i).toString().split(":")[0].equals("1")||
                        allServers.get(i).toString().split(":")[0].equals("2")||
                        allServers.get(i).toString().split(":")[0].equals("3")||
                        allServers.get(i).toString().split(":")[0].equals("4"))                
                {
                    //SqlRowSet srs = tran.getRowSet(query);
                    SqlRowSet srs = sqlUtility.getRowSet(serverAlias, query);
                    if (srs != null)
                    {
                        while (srs.next())
                        {
                            DependencyInfoBean depBean = new DependencyInfoBean();
                            depBean.setDependency(dep);
                            depBean.setDepServer(allServers.get(i).toString().split(":")[1].trim());
                            depBean.setDepSchema(srs.getString("OWNER").trim());
                            depBean.setDepObjType("TABLE");
                            depBean.setDepObjName(srs.getString("TABLE_NAME").trim());
                            depBeanList.add(depBean);
                        }
                    }
                }
            }
            catch (Exception e)
            {
                Logger.ErrorLogger(e);
            }
            finally
            {
                try
                {
//                    if (tran.getConnection() != null && !tran.getConnection().isClosed())
//                    {
//                        tran.closeConn();
//                    }
                }
                catch (Exception e)
                {
                    Logger.ErrorLogger(e);
                }
            }
        }
        return depBeanList;
    }

    public List<DependencyInfoBean> checkOracleNjtranETLDep(final List allServers, final DBObjectInfoBean dbObjBean) throws ClassNotFoundException, SQLException
    {
        List<DependencyInfoBean> depBeanList = new ArrayList<>();
        //SQLTranUtility tran = new SQLTranUtility();
        ServerPropertyReader propReader = new ServerPropertyReader();
        String query = "SELECT TABLENAME, TYPE, FROMSERVER, TOSERVER, FROMSCHEMA, TOSCHEMA FROM DATAUPLOAD.ETLDATAUPLOAD WHERE TYPE = 'FULLDUMP' AND TABLENAME = '" + dbObjBean.getObjName() + "'";

        String dep = "Oracle NJTRAN ETL contains entry of Target Table";

        for (int i = 0; i < allServers.size(); i++)
        {
            try
            {
                String serverAlias = propReader.getActualServer(allServers.get(i).toString().split(":")[0]);
                //tran.openConn(serverAlias);
                //String dbType = tran.getConnection().getMetaData().getDatabaseProductName().toUpperCase();
//                if ("ORACLE".equals(dbType))
                if(allServers.get(i).toString().split(":")[0].equals("1")||
                        allServers.get(i).toString().split(":")[0].equals("2")||
                        allServers.get(i).toString().split(":")[0].equals("3")||
                        allServers.get(i).toString().split(":")[0].equals("4"))
                {                
                    //SqlRowSet srs = tran.getRowSet(query);
                    SqlRowSet srs = sqlUtility.getRowSet(serverAlias, query);
                    if (srs != null)
                    {
                        while (srs.next())
                        {
                            DependencyInfoBean depBean = new DependencyInfoBean();
                            depBean.setDependency(dep);
                            depBean.setDepServer(srs.getString("FROMSERVER").trim());
                            depBean.setDepSchema(srs.getString("FROMSCHEMA").trim());
                            depBean.setDepObjType("TABLE");
                            depBean.setDepObjName(srs.getString("TABLENAME").trim());
                            depBeanList.add(depBean);
                        }
                    }
                }
            }
            catch (Exception e)
            {
                Logger.ErrorLogger(e);
            }
            finally
            {
                try
                {
//                    if (tran.getConnection() != null && !tran.getConnection().isClosed())
//                    {
//                        tran.closeConn();
//                    }
                }
                catch (Exception e)
                {
                    Logger.ErrorLogger(e);
                }
            }
        }
        return depBeanList;
    }

    public List<DependencyInfoBean> checkOracleNjtranIncreDataUploadDep(final List allServers, final DBObjectInfoBean dbObjBean) throws ClassNotFoundException, SQLException
    {
        List<DependencyInfoBean> depBeanList = new ArrayList<>();
        //SQLTranUtility tran = new SQLTranUtility();
        ServerPropertyReader propReader = new ServerPropertyReader();
        String query = "SELECT OWNER, TABLE_NAME FROM DBA_TABLES WHERE OWNER = 'DATAUPLOAD' AND TABLE_NAME LIKE '%LOG1_%' AND TABLE_NAME = '" + dbObjBean.getObjName() + "'";

        String dep = "Oracle NJTRAN Incremental DataUpload contains entry of Target Table";

        for (int i = 0; i < allServers.size(); i++)
        {
            try
            {
                String serverAlias = propReader.getActualServer(allServers.get(i).toString().split(":")[0]);
                //tran.openConn(serverAlias);
                //String dbType = tran.getConnection().getMetaData().getDatabaseProductName().toUpperCase();
//                if ("ORACLE".equals(dbType))
                if(allServers.get(i).toString().split(":")[0].equals("1")||
                        allServers.get(i).toString().split(":")[0].equals("2")||
                        allServers.get(i).toString().split(":")[0].equals("3")||
                        allServers.get(i).toString().split(":")[0].equals("4"))                 
                {
                    //SqlRowSet srs = tran.getRowSet(query);
                    SqlRowSet srs = sqlUtility.getRowSet(serverAlias, query);
                    if (srs != null)
                    {
                        while (srs.next())
                        {
                            DependencyInfoBean depBean = new DependencyInfoBean();
                            depBean.setDependency(dep);
                            depBean.setDepServer(allServers.get(i).toString().split(":")[1].trim());
                            depBean.setDepSchema(srs.getString("OWNER").trim());
                            depBean.setDepObjType("TABLE");
                            depBean.setDepObjName(srs.getString("TABLE_NAME").trim());
                            depBeanList.add(depBean);
                        }
                    }
                }
            }
            catch (Exception e)
            {
                Logger.ErrorLogger(e);
            }
            finally
            {
                try
                {
//                    if (tran.getConnection() != null && !tran.getConnection().isClosed())
//                    {
//                        tran.closeConn();
//                    }
                }
                catch (Exception e)
                {
                    Logger.ErrorLogger(e);
                }
            }
        }
        return depBeanList;
    }

    public List<DependencyInfoBean> checkMySQLLogTableDep(final List allServers, final DBObjectInfoBean dbObjBean, final String schema) throws ClassNotFoundException, SQLException
    {
        List<DependencyInfoBean> depBeanList = new ArrayList<>();
        //SQLTranUtility tran = new SQLTranUtility();
        ServerPropertyReader propReader = new ServerPropertyReader();
        String query = "SELECT TABLE_SCHEMA, TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = '" + schema + "_LOG' AND TABLE_NAME = '" + dbObjBean.getObjName() + "'";

        String dep = "Log table exist on MySQL";

        for (int i = 0; i < allServers.size(); i++)
        {
            try
            {
                String serverAlias = propReader.getActualServer(allServers.get(i).toString().split(":")[0]);
                //tran.openConn(serverAlias);
                //String dbType = tran.getConnection().getMetaData().getDatabaseProductName().toUpperCase();
                //if ("MYSQL".equals(dbType))
                if(allServers.get(i).toString().split(":")[0].equals("5")||
                        allServers.get(i).toString().split(":")[0].equals("6")||
                        allServers.get(i).toString().split(":")[0].equals("8")||
                        allServers.get(i).toString().split(":")[0].equals("11"))
                {
                    //SqlRowSet srs = tran.getRowSet(query);
                    SqlRowSet srs = sqlUtility.getRowSet(serverAlias, query);
                    if (srs != null)
                    {
                        while (srs.next())
                        {
                            DependencyInfoBean depBean = new DependencyInfoBean();
                            depBean.setDependency(dep);
                            depBean.setDepServer(allServers.get(i).toString().split(":")[1].trim());
                            depBean.setDepSchema(srs.getString("TABLE_SCHEMA").trim());
                            depBean.setDepObjType("TABLE");
                            depBean.setDepObjName(srs.getString("TABLE_NAME").trim());
                            depBeanList.add(depBean);
                        }
                    }
                }
            }
            catch (Exception e)
            {
                Logger.ErrorLogger(e);
            }
            finally
            {
                try
                {
//                    if (tran.getConnection() != null && !tran.getConnection().isClosed())
//                    {
//                        tran.closeConn();
//                    }
                }
                catch (Exception e)
                {
                    Logger.ErrorLogger(e);
                }
            }
        }
        return depBeanList;
    }

    public List<DependencyInfoBean> checkMySQLViewDep(final List allServers, final DBObjectInfoBean dbObjBean) throws ClassNotFoundException, SQLException
    {
        List<DependencyInfoBean> depBeanList = new ArrayList<>();
        //SQLTranUtility tran = new SQLTranUtility();
        ServerPropertyReader propReader = new ServerPropertyReader();
        String query = "SELECT TABLE_SCHEMA, TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME LIKE '%" + dbObjBean.getObjName() + "%'";

        String dep = "View exist on MySQL";

        for (int i = 0; i < allServers.size(); i++)
        {
            try
            {
                String serverAlias = propReader.getActualServer(allServers.get(i).toString().split(":")[0]);
                //tran.openConn(serverAlias);
                //String dbType = tran.getConnection().getMetaData().getDatabaseProductName().toUpperCase();
                //if ("MYSQL".equals(dbType))
                if(allServers.get(i).toString().split(":")[0].equals("5")||
                        allServers.get(i).toString().split(":")[0].equals("6")||
                        allServers.get(i).toString().split(":")[0].equals("8")||
                        allServers.get(i).toString().split(":")[0].equals("11"))
                {
                    //SqlRowSet srs = tran.getRowSet(query);
                    SqlRowSet srs = sqlUtility.getRowSet(serverAlias, query);
                    if (srs != null)
                    {
                        while (srs.next())
                        {
                            DependencyInfoBean depBean = new DependencyInfoBean();
                            depBean.setDependency(dep);
                            depBean.setDepServer(allServers.get(i).toString().split(":")[1].trim());
                            depBean.setDepSchema(srs.getString("TABLE_SCHEMA").trim());
                            depBean.setDepObjType("VIEW");
                            depBean.setDepObjName(srs.getString("TABLE_NAME").trim());
                            depBeanList.add(depBean);
                        }
                    }
                }
            }
            catch (Exception e)
            {
                Logger.ErrorLogger(e);
            }
            finally
            {
                try
                {
//                    if (tran.getConnection() != null && !tran.getConnection().isClosed())
//                    {
//                        tran.closeConn();
//                    }
                }
                catch (Exception e)
                {
                    Logger.ErrorLogger(e);
                }
            }
        }
        return depBeanList;
    }

    public List<DependencyInfoBean> checkMySQLInfrobrightServer(final List allServers, final DBObjectInfoBean dbObjBean, final String schema) throws ClassNotFoundException, SQLException
    {
        List<DependencyInfoBean> depBeanList = new ArrayList<>();
        //SQLTranUtility tran = new SQLTranUtility();
        ServerPropertyReader propReader = new ServerPropertyReader();
        String query = "SELECT TABLE_SCHEMA, TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = '" + schema + "' AND TABLE_NAME = '" + dbObjBean.getObjName() + "'";

        String dep = "MySQL Infrobright Server has table of same name as Target Table";

        for (int i = 0; i < allServers.size(); i++)
        {
            try
            {
                String serverAlias = propReader.getActualServer(allServers.get(i).toString().split(":")[0]);
                //tran.openConn(serverAlias);
                //String dbType = tran.getConnection().getMetaData().getDatabaseProductName().toUpperCase();
                //if ("MYSQL".equals(dbType))
                if(allServers.get(i).toString().split(":")[0].equals("5")||
                        allServers.get(i).toString().split(":")[0].equals("6")||
                        allServers.get(i).toString().split(":")[0].equals("8")||
                        allServers.get(i).toString().split(":")[0].equals("11"))
                {
                    //SqlRowSet srs = tran.getRowSet(query);
                    SqlRowSet srs = sqlUtility.getRowSet(serverAlias, query);
                    if (srs != null)
                    {
                        while (srs.next())
                        {
                            DependencyInfoBean depBean = new DependencyInfoBean();
                            depBean.setDependency(dep);
                            depBean.setDepServer(allServers.get(i).toString().split(":")[1].trim());
                            depBean.setDepSchema(srs.getString("TABLE_SCHEMA").trim());
                            depBean.setDepObjType("TABLE");
                            depBean.setDepObjName(srs.getString("TABLE_NAME").trim());
                            depBeanList.add(depBean);
                        }
                    }
                }
            }
            catch (Exception e)
            {
                Logger.ErrorLogger(e);
            }
            finally
            {
                try
                {
//                    if (tran.getConnection() != null && !tran.getConnection().isClosed())
//                    {
//                        tran.closeConn();
//                    }
                }
                catch (Exception e)
                {
                    Logger.ErrorLogger(e);
                }
            }
        }
        return depBeanList;
    }

    public List<DependencyInfoBean> checkOracleMViewDep(final List allServers, final DBObjectInfoBean dbObjBean) throws ClassNotFoundException, SQLException
    {
        List<DependencyInfoBean> depBeanList = new ArrayList<>();
        //SQLTranUtility tran = new SQLTranUtility();
        ServerPropertyReader propReader = new ServerPropertyReader();
        String query = "SELECT LOG_OWNER, MASTER, LOG_TABLE FROM DBA_MVIEW_LOGS WHERE LOG_OWNER = '" + dbObjBean.getSchema() + "' AND MASTER = '" + dbObjBean.getObjName() + "'";

        String dep = "Materialized Views exist on Oracle";

        for (int i = 0; i < allServers.size(); i++)
        {
            try
            {
                String serverAlias = propReader.getActualServer(allServers.get(i).toString().split(":")[0]);
                //tran.openConn(serverAlias);
                //String dbType = tran.getConnection().getMetaData().getDatabaseProductName().toUpperCase();
                //if ("ORACLE".equals(dbType))
                if(allServers.get(i).toString().split(":")[0].equals("1")||
                        allServers.get(i).toString().split(":")[0].equals("2")||
                        allServers.get(i).toString().split(":")[0].equals("3")||
                        allServers.get(i).toString().split(":")[0].equals("4"))                
                {
                    //SqlRowSet srs = tran.getRowSet(query);
                    SqlRowSet srs = sqlUtility.getRowSet(serverAlias, query);
                    if (srs != null)
                    {
                        while (srs.next())
                        {
                            DependencyInfoBean depBean = new DependencyInfoBean();
                            depBean.setDependency(dep);
                            depBean.setDepServer(allServers.get(i).toString().split(":")[1].trim());
                            depBean.setDepSchema(srs.getString("LOG_OWNER").trim());
                            depBean.setDepObjType("VIEW");
                            depBean.setDepObjName(srs.getString("LOG_TABLE").trim());
                            depBeanList.add(depBean);
                        }
                    }
                }
            }
            catch (Exception e)
            {
                Logger.ErrorLogger(e);
            }
            finally
            {
                try
                {
                    //if (tran.getConnection() != null && !tran.getConnection().isClosed())
                    //{
//                        tran.closeConn();
//                    }
                }
                catch (Exception e)
                {
                    Logger.ErrorLogger(e);
                }
            }
        }
        return depBeanList;
    }

    public List<DependencyInfoBean> checkMysqlSameNmTableDep(final List allServers, final DBObjectInfoBean dbObjBean) throws ClassNotFoundException, SQLException
    {
        List<DependencyInfoBean> depBeanList = new ArrayList<>();
        //SQLTranUtility tran = new SQLTranUtility();
        ServerPropertyReader propReader = new ServerPropertyReader();
        String query = "SELECT TABLE_SCHEMA, TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = '" + dbObjBean.getSchema() + "' AND TABLE_NAME = '" + dbObjBean.getObjName() + "'";

        String dep = "Mysql have the table of same name as target table";

        for (int i = 0; i < allServers.size(); i++)
        {
            try
            {
                String serverAlias = propReader.getActualServer(allServers.get(i).toString().split(":")[0]);
                //tran.openConn(serverAlias);
                //String dbType = tran.getConnection().getMetaData().getDatabaseProductName().toUpperCase();
                //if ("MYSQL".equals(dbType))
                if(allServers.get(i).toString().split(":")[0].equals("5")||
                        allServers.get(i).toString().split(":")[0].equals("6")||
                        allServers.get(i).toString().split(":")[0].equals("8")||
                        allServers.get(i).toString().split(":")[0].equals("11"))
                {
                    //SqlRowSet srs = tran.getRowSet(query);
                    SqlRowSet srs = sqlUtility.getRowSet(serverAlias, query);
                    if (srs != null)
                    {
                        while (srs.next())
                        {
                            DependencyInfoBean depBean = new DependencyInfoBean();
                            depBean.setDependency(dep);
                            depBean.setDepServer(allServers.get(i).toString().split(":")[1].trim());
                            depBean.setDepSchema(srs.getString("TABLE_SCHEMA").trim());
                            depBean.setDepObjType("TABLE");
                            depBean.setDepObjName(srs.getString("TABLE_NAME").trim());
                            depBeanList.add(depBean);
                        }
                    }
                }
            }
            catch (Exception e)
            {
                Logger.ErrorLogger(e);
            }
            finally
            {
                try
                {
                    //if (tran.getConnection() != null && !tran.getConnection().isClosed())
                    //{
                        //tran.closeConn();
                    //}
                }
                catch (Exception e)
                {
                    Logger.ErrorLogger(e);
                }
            }
        }
        return depBeanList;
    }
}
