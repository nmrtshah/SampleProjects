/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.business.finstudio.finmaster;

import com.finlogic.apps.finstudio.finmaster.FinMasterFormBean;
import com.finlogic.util.Logger;
import com.finlogic.util.finmaster.ServerConnection;
import com.finlogic.util.persistence.DBManager;
import com.finlogic.util.persistence.SQLConnService;
import com.finlogic.util.persistence.SQLService;
import com.finlogic.util.persistence.SQLUtility;
import finutils.directconn.DBConnManager;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Locale;
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
public class FinMasterDataManager
{

    private static SQLService sqlService = new SQLService();
    private static SQLUtility sqlUtility = new SQLUtility();
    private static SQLConnService sqlConnSrvc = new SQLConnService();
    private static final String ALIASNAME = "finstudio_mysql";
    private static final String STRALIAS = "Alias";

    public SqlRowSet getProjectArray() throws ClassNotFoundException, SQLException
    {
        String query;
        query = "SELECT PRJ_ID, PRJ_NAME, DOMAIN_NAME FROM PROJECT_MST WHERE DOMAIN_NAME IS NOT NULL ORDER BY BINARY PRJ_NAME";
        return sqlService.getRowSet("wfm", query);
    }

    public String[] getAliasArray()
    {
        DBConnManager dbcm;
        dbcm = new DBConnManager();
        return dbcm.getConnAliasArray();
    }

    public String getDatabaseType(final FinMasterFormBean formBean) throws ClassNotFoundException, SQLException
    {
        if (STRALIAS.equals(formBean.getRdoAliasServer()))
        {
            DBConnManager conMgr = new DBConnManager();
            Connection conn = null;
            try
            {
                conn = conMgr.getFinConn(formBean.getCmbAliasName());
                return conn.getMetaData().getDatabaseProductName().toUpperCase(Locale.getDefault());
            }
            finally
            {
                if (conn != null && !conn.isClosed())
                {
                    conn.close();
                }
            }
        }
        else
        {
            Connection conn = null;
            try
            {
                conn = ServerConnection.getConnection(formBean.getCmbServerName());
                return conn.getMetaData().getDatabaseProductName().toUpperCase(Locale.getDefault());
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

    public SqlRowSet getTableNames(final FinMasterFormBean formBean) throws ClassNotFoundException, SQLException
    {
        String query = "";
        String tableName;
        SqlRowSet srs = null;
        tableName = formBean.getTxtMasterTable();
        MapSqlParameterSource param = null;
        if (STRALIAS.equals(formBean.getRdoAliasServer()))
        {
            if ("MYSQL".equals(formBean.getTxtDataBaseType()))
            {
                query = "SELECT CONCAT(TABLE_SCHEMA,'.',TABLE_NAME) TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME LIKE :tblName";
                HashMap hmap;
                hmap = new HashMap();
                hmap.put("tblName", tableName + "%");
                param = new MapSqlParameterSource(hmap);
                srs = sqlService.getRowSet(formBean.getCmbAliasName(), query, param);
            }
            else if ("ORACLE".equals(formBean.getTxtDataBaseType()))
            {
                query = "SELECT TNAME TABLE_NAME FROM SYS.TAB WHERE TNAME LIKE :tblName";
                HashMap hmap;
                hmap = new HashMap();
                hmap.put("tblName", tableName.toUpperCase(Locale.getDefault()) + "%");
                param = new MapSqlParameterSource(hmap);
                srs = sqlService.getRowSet(formBean.getCmbAliasName(), query, param);
            }
            return srs;
        }
        else
        {
            Connection conn = null;
            try
            {
                conn = ServerConnection.getConnection(formBean.getCmbServerName());
                if ("dev_mysql".equals(formBean.getCmbServerName()) || "dev_db2_mysql".equals(formBean.getCmbServerName()))
                {
                    query = "SELECT CONCAT(TABLE_SCHEMA,'.',TABLE_NAME) TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME LIKE :tblName";
                    HashMap hmap;
                    hmap = new HashMap();
                    hmap.put("tblName", tableName + "%");
                    param = new MapSqlParameterSource(hmap);
                }
                else if ("dev_tran".equals(formBean.getCmbServerName()) || "dev_brok".equals(formBean.getCmbServerName()))
                {
                    //query = "SELECT TNAME TABLE_NAME FROM SYS.TAB WHERE TNAME LIKE '" + tableName.toUpperCase(Locale.getDefault()) + "%' ORDER BY TNAME";
                    query = "SELECT OWNER||'.'||TABLE_NAME TABLE_NAME FROM DBA_TABLES WHERE TABLE_NAME LIKE :tblName";
                    HashMap hmap;
                    hmap = new HashMap();
                    hmap.put("tblName", tableName.toUpperCase(Locale.getDefault()) + "%");
                    param = new MapSqlParameterSource(hmap);
                }
                srs = sqlConnSrvc.getRowSet(conn, query, param);
            }
            catch (Exception e)
            {
                Logger.ErrorLogger(e);
            }
            finally
            {
                if (conn != null && !conn.isClosed())
                {
                    conn.close();
                }
            }
            return srs;
        }
    }

    public SqlRowSet getTableColumnsNames(final FinMasterFormBean formBean) throws ClassNotFoundException, SQLException
    {
        String query;
        String tableName;
        String ownerName;
        SqlRowSet srs = null;
        tableName = formBean.getCmbMasterTable();
        ownerName = formBean.getCmbAliasName().toUpperCase();
        String[] schematable;
        schematable = tableName.split("\\.");
        if (STRALIAS.equals(formBean.getRdoAliasServer()))
        {
            if ("MYSQL".equals(formBean.getTxtDataBaseType()))
            {
                query = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = '" + schematable[0] + "' AND TABLE_NAME = '" + schematable[1] + "'";
                srs = sqlService.getRowSet(formBean.getCmbAliasName(), query);
            }
            else if ("ORACLE".equals(formBean.getTxtDataBaseType()))
            {
                query = "SELECT COLUMN_NAME FROM ALL_TAB_COLS WHERE OWNER = '" + ownerName + "' AND TABLE_NAME = '" + tableName + "' ORDER BY COLUMN_ID";
                srs = sqlService.getRowSet(formBean.getCmbAliasName(), query);
            }
            return srs;
        }
        else
        {
            Connection conn = null;
            try
            {
                conn = ServerConnection.getConnection(formBean.getCmbServerName());
                if ("dev_mysql".equals(formBean.getCmbServerName()) || "dev_db2_mysql".equals(formBean.getCmbServerName()))
                {
                    query = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = '" + schematable[0] + "' AND TABLE_NAME = '" + schematable[1] + "'";
                    srs = sqlConnSrvc.getRowSet(conn, query);
                }
                else if ("dev_tran".equals(formBean.getCmbServerName()) || "dev_brok".equals(formBean.getCmbServerName()))
                {
                    //query = "SELECT CNAME COLUMN_NAME FROM SYS.COL WHERE TNAME = '" + schematable[1] + "'";
                    query = "SELECT COLUMN_NAME FROM ALL_TAB_COLS WHERE OWNER = '" + schematable[0] + "' AND TABLE_NAME = '" + schematable[1] + "' ORDER BY COLUMN_ID";
                    srs = sqlConnSrvc.getRowSet(conn, query);
                }
            }
            catch (Exception e)
            {
                Logger.ErrorLogger(e);
            }
            finally
            {
                if (conn != null && !conn.isClosed())
                {
                    conn.close();
                }
            }
            return srs;
        }
    }

    public String getPrimaryColumn(final FinMasterFormBean formBean) throws ClassNotFoundException, SQLException
    {
        String query;
        String tableName;
        SqlRowSet srs = null;
        tableName = formBean.getCmbMasterTable();
        if (STRALIAS.equals(formBean.getRdoAliasServer()))
        {
            if ("MYSQL".equals(formBean.getTxtDataBaseType()))
            {
                String[] schematable;
                schematable = formBean.getCmbMasterTable().split("\\.");
                query = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE (TABLE_SCHEMA = '" + schematable[0] + "') AND (TABLE_NAME = '" + schematable[1] + "') AND (COLUMN_KEY = 'PRI')";
                srs = sqlService.getRowSet(formBean.getCmbAliasName(), query);
                if (srs.next())
                {
                    return srs.getString("COLUMN_NAME");
                }
                else
                {
                    return "";
                }
            }
            else if ("ORACLE".equals(formBean.getTxtDataBaseType()))
            {
                Connection conn = null;
                ResultSet rs = null;
                DBManager DBmgr;
                DBmgr = new DBManager();
                try
                {
                    conn = DBmgr.getOracleConnection(formBean.getCmbAliasName());
                    rs = conn.getMetaData().getPrimaryKeys(null, null, tableName);

                    if (rs.next())
                    {
                        return rs.getString("COLUMN_NAME");
                    }
                    else
                    {
                        return "";
                    }
                }
                finally
                {
                    try
                    {
                        rs.close();
                    }
                    catch (Exception ex)
                    {
                        Logger.ErrorLogger(ex);
                    }
                    try
                    {
                        DBmgr.releaseOracleConnection(conn);
                    }
                    catch (Exception ex)
                    {
                        Logger.ErrorLogger(ex);
                    }
                }
            }
        }
        else
        {
            Connection conn = null;
            try
            {
                String[] schematable;
                schematable = formBean.getCmbMasterTable().split("\\.");

                conn = ServerConnection.getConnection(formBean.getCmbServerName());
                if ("dev_mysql".equals(formBean.getCmbServerName()) || "dev_db2_mysql".equals(formBean.getCmbServerName()))
                {
                    query = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE (TABLE_SCHEMA = '" + schematable[0] + "') AND (TABLE_NAME = '" + schematable[1] + "') AND (COLUMN_KEY = 'PRI')";
                    srs = sqlConnSrvc.getRowSet(conn, query);
                    if (srs.next())
                    {
                        return srs.getString("COLUMN_NAME");
                    }
                    else
                    {
                        return "";
                    }
                }
                else if ("dev_tran".equals(formBean.getCmbServerName()) || "dev_brok".equals(formBean.getCmbServerName()))
                {
                    ResultSet rs = null;
                    try
                    {
                        rs = conn.getMetaData().getPrimaryKeys(null, schematable[0], schematable[1]);

                        if (rs.next())
                        {
                            return rs.getString("COLUMN_NAME");
                        }
                        else
                        {
                            return "";
                        }
                    }
                    finally
                    {
                        try
                        {
                            if (rs != null)
                            {
                                rs.close();
                            }
                        }
                        catch (Exception ex)
                        {
                            Logger.ErrorLogger(ex);
                        }
                        if (conn != null && !conn.isClosed())
                        {
                            conn.close();
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
                if (conn != null && !conn.isClosed())
                {
                    conn.close();
                }
            }
        }
        return "";
    }

    public SqlRowSet getMstTableColumnWidth(final FinMasterFormBean formBean) throws ClassNotFoundException, SQLException
    {
        String query;
        String tableName;
        String ownerName;
        SqlRowSet srs = null;
        tableName = formBean.getCmbMasterTable();
        ownerName = formBean.getCmbAliasName().toUpperCase();
        if (STRALIAS.equals(formBean.getRdoAliasServer()))
        {
            if ("MYSQL".equals(formBean.getTxtDataBaseType()))
            {
                query = "SELECT COLUMN_NAME, DATA_TYPE, (CASE WHEN (DATA_TYPE = 'TEXT') THEN 0 WHEN (DATA_TYPE = 'DATE') THEN 10 ELSE (CASE WHEN (CHARACTER_MAXIMUM_LENGTH IS NOT NULL) THEN CHARACTER_MAXIMUM_LENGTH ELSE NUMERIC_PRECISION END) END) LENGTH FROM INFORMATION_SCHEMA.COLUMNS WHERE CONCAT(TABLE_SCHEMA,'.',TABLE_NAME) = '" + tableName + "'";
                srs = sqlService.getRowSet(formBean.getCmbAliasName(), query);
            }
            else if ("ORACLE".equals(formBean.getTxtDataBaseType()))
            {
                query = "SELECT COLUMN_NAME FROM ALL_TAB_COLS WHERE OWNER = '" + ownerName + "' AND TABLE_NAME = '" + tableName + "' ORDER BY COLUMN_ID";
                srs = sqlService.getRowSet(formBean.getCmbAliasName(), query);
            }
            return srs;
        }
        else
        {
            Connection conn = null;
            try
            {
                conn = ServerConnection.getConnection(formBean.getCmbServerName());
                String[] schematable;
                schematable = formBean.getCmbMasterTable().split("\\.");

                if ("dev_mysql".equals(formBean.getCmbServerName()) || "dev_db2_mysql".equals(formBean.getCmbServerName()))
                {
                    query = "SELECT COLUMN_NAME, DATA_TYPE, (CASE WHEN (DATA_TYPE = 'TEXT') THEN 0 WHEN (DATA_TYPE = 'DATE') THEN 10 ELSE (CASE WHEN (CHARACTER_MAXIMUM_LENGTH IS NOT NULL) THEN CHARACTER_MAXIMUM_LENGTH ELSE NUMERIC_PRECISION END) END) LENGTH FROM INFORMATION_SCHEMA.COLUMNS WHERE CONCAT(TABLE_SCHEMA,'.',TABLE_NAME) = '" + tableName + "'";
                    srs = sqlConnSrvc.getRowSet(conn, query);
                }
                else if ("dev_tran".equals(formBean.getCmbServerName()) || "dev_brok".equals(formBean.getCmbServerName()))
                {
                    query = "SELECT COLUMN_NAME, DATA_TYPE, (CASE WHEN (DATA_TYPE = 'DATE') THEN 10 ELSE DATA_LENGTH END) LENGTH FROM ALL_TAB_COLS WHERE OWNER = '" + schematable[0] + "' AND TABLE_NAME = '" + schematable[1] + "'";
                    srs = sqlConnSrvc.getRowSet(conn, query);
                }
            }
            catch (Exception e)
            {
                Logger.ErrorLogger(e);
            }
            finally
            {
                if (conn != null && !conn.isClosed())
                {
                    conn.close();
                }
            }
            return srs;
        }
    }

    public SqlRowSet checkSequence(final FinMasterFormBean formBean) throws ClassNotFoundException, SQLException
    {
        SqlRowSet srs;
        String query = "SELECT " + formBean.getTxtSequence() + ".CURRVAL FROM DUAL";
        if (STRALIAS.equals(formBean.getRdoAliasServer()))
        {
            srs = sqlUtility.getRowSet(formBean.getCmbAliasName(), query);
        }
        else
        {
            Connection conn = null;
            try
            {
                conn = ServerConnection.getConnection(formBean.getCmbServerName());
                srs = sqlConnSrvc.getRowSet(conn, query);
            }
            finally
            {
                if (conn != null)
                {
                    conn.close();
                }
            }
        }
        return srs;
    }

    public SqlRowSet checkQuery(final FinMasterFormBean formBean, final String tab) throws ClassNotFoundException, SQLException
    {
        SqlRowSet srs;
        String strQ = "";
        if ("Add".equals(tab))
        {
            strQ = formBean.getTxtAddSrcQuery();
        }
        else if ("Edit".equals(tab))
        {
            strQ = formBean.getTxtEditSrcQuery();
        }
        else if ("Delete".equals(tab))
        {
            strQ = formBean.getTxtDeleteSrcQuery();
        }
        else if ("View".equals(tab))
        {
            strQ = formBean.getTxtViewSrcQuery();
        }
        strQ = strQ.trim();
        if (strQ.endsWith(";"))
        {
            strQ = strQ.substring(0, strQ.length() - 1).trim();
        }
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM (").append(strQ).append(") X WHERE 1 = 2");

        if (STRALIAS.equals(formBean.getRdoAliasServer()))
        {
            srs = sqlUtility.getRowSet(formBean.getCmbAliasName(), query.toString());
        }
        else
        {
            Connection conn = null;
            try
            {
                conn = ServerConnection.getConnection(formBean.getCmbServerName());
                srs = sqlConnSrvc.getRowSet(conn, query.toString());
            }
            finally
            {
                if (conn != null)
                {
                    conn.close();
                }
            }
        }
        return srs;
    }

    public int insertIntoDataBase(final FinMasterEntityBean entityBean) throws ClassNotFoundException, SQLException
    {
        SqlParameterSource sps;
        sps = new BeanPropertySqlParameterSource(entityBean);
        KeyHolder keys;
        keys = new GeneratedKeyHolder();

        //MST_MAIN Entry
        String mst_main_query;
        if (entityBean.getRequestNo().equals(""))
        {
            mst_main_query = "INSERT INTO MST_MAIN(PROJECT_NAME,MODULE_NAME,PROBLEM_STATEMENT,SOLUTION_OBJECTIVE,EXISTING_PRACTICE,PLACEMENT,ALIAS_NAME,MASTER_TABLE_NAME,MASTER_PRIMARY_KEY,ADDTAB,EDITTAB,DELETETAB,VIEWTAB,EMP_CODE,ON_DATE,VIEW_COLUMNS,VIEW_ONSCREEN,VIEW_PDF,VIEW_XLS)"
                    + "VALUES(:projectName,:moduleName,:problemStatement,:solutionObjective,:existingPractice,:placement,:aliasName,:masterTableName,:masterPrimaryKey,:addTab,:editTab,:deleteTab,:viewTab,:empCode,SYSDATE(),:viewColumns,:viewOnScreen,:viewPDF,:viewXLS)";
        }
        else
        {
            mst_main_query = "INSERT INTO MST_MAIN(PROJECT_NAME,MODULE_NAME,REQUEST_NO,PROBLEM_STATEMENT,SOLUTION_OBJECTIVE,EXISTING_PRACTICE,PLACEMENT,ALIAS_NAME,MASTER_TABLE_NAME,MASTER_PRIMARY_KEY,ADDTAB,EDITTAB,DELETETAB,VIEWTAB,EMP_CODE,ON_DATE,VIEW_COLUMNS,VIEW_ONSCREEN,VIEW_PDF,VIEW_XLS)"
                    + "VALUES(:projectName,:moduleName,:requestNo,:problemStatement,:solutionObjective,:existingPractice,:placement,:aliasName,:masterTableName,:masterPrimaryKey,:addTab,:editTab,:deleteTab,:viewTab,:empCode,SYSDATE(),:viewColumns,:viewOnScreen,:viewPDF,:viewXLS)";
        }
        sqlService.persist(ALIASNAME, mst_main_query, keys, sps);

        //get SRNO to insert into other tables
        int mstMainSrno = sqlService.getInt(ALIASNAME, "SELECT SRNO FROM MST_MAIN ORDER BY SRNO DESC LIMIT 1");

        //MST_MASTERFIELD Entry
        StringBuilder ins_Mst;
        ins_Mst = new StringBuilder();
        ins_Mst.append("INSERT INTO MST_MASTERFIELD(SRNO_OF_MST_MAIN,TAB_NAME,FIELD_NAME,CONTROL,VALIDATION,SELECTION_NATURE,REMARKS)");
        ins_Mst.append(" VALUES(").append(mstMainSrno);
        if (entityBean.isAddTab())
        {
            for (int i = 0; i < entityBean.getMstAddFieldName().length; i++)
            {
                String mstField_query;
                mstField_query = ins_Mst + ",'ADD','" + entityBean.getMstAddFieldName()[i] + "','" + entityBean.getMstAddControl()[i] + "','" + entityBean.getMstAddValidation()[i] + "','" + entityBean.getMstAddSelNature()[i] + "','" + entityBean.getMstAddRemarks()[i] + "')";
                sqlUtility.persist(ALIASNAME, mstField_query);
            }
        }
        if (entityBean.isEditTab() && entityBean.getMstEditFieldName() != null)
        {
            for (int i = 0; i < entityBean.getMstEditFieldName().length; i++)
            {
                String mstField_query;
                mstField_query = ins_Mst + ",'EDIT','" + entityBean.getMstEditFieldName()[i] + "','" + entityBean.getMstEditControl()[i] + "','" + entityBean.getMstEditValidation()[i] + "','" + entityBean.getMstEditSelNature()[i] + "','" + entityBean.getMstEditRemarks()[i] + "')";
                sqlUtility.persist(ALIASNAME, mstField_query);
            }
        }
        if (entityBean.isDeleteTab() && entityBean.getMstDelFieldName() != null)
        {
            for (int i = 0; i < entityBean.getMstDelFieldName().length; i++)
            {
                String mstField_query;
                mstField_query = ins_Mst + ",'DELETE','" + entityBean.getMstDelFieldName()[i] + "','" + entityBean.getMstDelControl()[i] + "','" + entityBean.getMstDelValidation()[i] + "','" + entityBean.getMstDelSelNature()[i] + "','" + entityBean.getMstDelRemarks()[i] + "')";
                sqlUtility.persist(ALIASNAME, mstField_query);
            }
        }
        if (entityBean.isViewTab() && entityBean.getMstViewFieldName() != null)
        {
            for (int i = 0; i < entityBean.getMstViewFieldName().length; i++)
            {
                String mstField_query;
                mstField_query = ins_Mst + ",'VIEW','" + entityBean.getMstViewFieldName()[i] + "','" + entityBean.getMstViewControl()[i] + "','" + entityBean.getMstViewValidation()[i] + "','" + entityBean.getMstViewSelNature()[i] + "','" + entityBean.getMstViewRemarks()[i] + "')";
                sqlUtility.persist(ALIASNAME, mstField_query);
            }
        }
        return mstMainSrno;
    }

    public SqlRowSet getRecordsByTable(final FinMasterFormBean formBean) throws ClassNotFoundException, SQLException
    {
        String query;
        query = "SELECT * FROM " + formBean.getCmbMasterTable() + " WHERE 1 = 2";
        if (STRALIAS.equals(formBean.getRdoAliasServer()))
        {
            return sqlUtility.getRowSet(formBean.getCmbAliasName(), query);
        }
        else
        {
            Connection conn = null;
            try
            {
                conn = ServerConnection.getConnection(formBean.getCmbServerName());
                return sqlConnSrvc.getRowSet(conn, query);
            }
            catch (Exception e)
            {
                Logger.ErrorLogger(e);
            }
            finally
            {
                if (conn != null && !conn.isClosed())
                {
                    conn.close();
                }
            }
        }
        return null;
    }

    public SqlRowSet getRecordsByQuery(final FinMasterFormBean formBean, final String query) throws ClassNotFoundException, SQLException
    {
        if (STRALIAS.equals(formBean.getRdoAliasServer()))
        {
            String databaseType = formBean.getTxtDataBaseType();
            String queryWithCondition = query;

            if ("ORACLE".equalsIgnoreCase(databaseType))
            {
                queryWithCondition += " WHERE ROWNUM < 100";
            }
            else if ("MYSQL".equalsIgnoreCase(databaseType))
            {
                queryWithCondition += " LIMIT 100";
            }

            return sqlUtility.getRowSet(formBean.getCmbAliasName(), queryWithCondition);
        }
        else
        {
            Connection conn = null;
            try
            {
                conn = ServerConnection.getConnection(formBean.getCmbServerName());
                return sqlConnSrvc.getRowSet(conn, query);
            }
            catch (Exception e)
            {
                Logger.ErrorLogger(e);
            }
            finally
            {
                if (conn != null && !conn.isClosed())
                {
                    conn.close();
                }
            }
        }
        return null;
    }

    public SqlRowSet getColumnsOfQuery(final FinMasterFormBean formBean, final String query) throws ClassNotFoundException, SQLException, UnsupportedEncodingException
    {
        SqlRowSet srs;
        String decodeQuery, strQ;
        strQ = URLDecoder.decode(query, "UTF-8");
        strQ = strQ.trim();
        if (strQ.endsWith(";"))
        {
            strQ = strQ.substring(0, strQ.length() - 1).trim();
        }
        decodeQuery = "SELECT * FROM (" + strQ + ") X WHERE 1 = 2";
        if (STRALIAS.equals(formBean.getRdoAliasServer()))
        {
            srs = sqlUtility.getRowSet(formBean.getCmbAliasName(), decodeQuery);
        }
        else
        {
            Connection conn = null;
            try
            {
                conn = ServerConnection.getConnection(formBean.getCmbServerName());
                srs = sqlConnSrvc.getRowSet(conn, decodeQuery);
            }
            finally
            {
                if (conn != null)
                {
                    conn.close();
                }
            }
        }
        return srs;
    }

    public void setSchema(final FinMasterFormBean formBean) throws ClassNotFoundException, SQLException
    {
        DBManager dbMngr;
        dbMngr = new DBManager();
        Connection conn = null;
        formBean.setSchemaisalias(true);
        try
        {
            if ("MYSQL".equals(formBean.getTxtDataBaseType()))
            {
                conn = dbMngr.getMySQLConnection(formBean.getCmbAliasName());
            }
            else
            {
                conn = dbMngr.getOracleConnection(formBean.getCmbAliasName());
            }

            String[] schematable;
            schematable = formBean.getCmbMasterTable().split("\\.");
            if (schematable.length == 2)
            {
                if (conn.getCatalog() != null)
                {
                    if (!conn.getCatalog().equals(schematable[0]))
                    {
                        formBean.setSchemaisalias(false);
                    }
                }
                else if (conn.getMetaData().getUserName() != null && !conn.getMetaData().getUserName().equals(schematable[0]))
                {
                    formBean.setSchemaisalias(false);
                }
            }
        }
        finally
        {
            if ("MYSQL".equals(formBean.getTxtDataBaseType()))
            {
                dbMngr.releaseMySQLConnection(conn);
            }
            else
            {
                dbMngr.releaseOracleConnection(conn);
            }
        }
    }
}