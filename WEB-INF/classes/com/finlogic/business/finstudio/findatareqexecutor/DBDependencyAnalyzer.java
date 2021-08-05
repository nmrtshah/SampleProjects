/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.business.finstudio.findatareqexecutor;

import com.finlogic.business.finstudio.DBDependencyDataManager;
import com.finlogic.util.findatareqexecutor.DBObjectInfoBean;
import com.finlogic.util.findatareqexecutor.DependencyInfoBean;
import com.finlogic.util.findatareqexecutor.QueryInfoBean;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Sonam Patel
 */
public class DBDependencyAnalyzer
{

    private final DBDependencyDataManager depDataMgr = new DBDependencyDataManager();

    public List<DependencyInfoBean> isSensitive(final String dbID, final QueryInfoBean qBean) throws ClassNotFoundException, SQLException
    {
        return depDataMgr.isSensitive(dbID, qBean);
    }

    public void checkSensitiveKeyword(final String dbID, final QueryInfoBean qBean, final String dbType, final String schema) throws ClassNotFoundException, SQLException
    {
        depDataMgr.checkSensitiveKeyword(dbID, qBean, dbType, schema);
    }

    public List<DependencyInfoBean> checkOracleDep(final List allServers, final DBObjectInfoBean dbObjBean) throws ClassNotFoundException, SQLException
    {
        return depDataMgr.checkOracleDep(allServers, dbObjBean);
    }

    public List<DependencyInfoBean> checkOracleLogTableDep(final List allServers, final DBObjectInfoBean dbObjBean) throws ClassNotFoundException, SQLException
    {
        return depDataMgr.checkOracleLogTableDep(allServers, dbObjBean);
    }

    public List<DependencyInfoBean> checkOracleNjtranETLDep(final List allServers, final DBObjectInfoBean dbObjBean) throws ClassNotFoundException, SQLException
    {
        return depDataMgr.checkOracleNjtranETLDep(allServers, dbObjBean);
    }

    public List<DependencyInfoBean> checkOracleNjtranIncreDataUploadDep(final List allServers, final DBObjectInfoBean dbObjBean) throws ClassNotFoundException, SQLException
    {
        return depDataMgr.checkOracleNjtranIncreDataUploadDep(allServers, dbObjBean);
    }

    public List<DependencyInfoBean> checkMySQLLogTableDep(final List allServers, final DBObjectInfoBean dbObjBean, final String schema) throws ClassNotFoundException, SQLException
    {
        return depDataMgr.checkMySQLLogTableDep(allServers, dbObjBean, schema);
    }

    public List<DependencyInfoBean> checkMySQLViewDep(final List allServers, final DBObjectInfoBean dbObjBean) throws ClassNotFoundException, SQLException
    {
        return depDataMgr.checkMySQLViewDep(allServers, dbObjBean);
    }

    public List<DependencyInfoBean> checkMySQLInfrobrightServer(final List allServers, final DBObjectInfoBean dbObjBean, final String schema) throws ClassNotFoundException, SQLException
    {
        return depDataMgr.checkMySQLInfrobrightServer(allServers, dbObjBean, schema);
    }

    public List<DependencyInfoBean> checkOracleMViewDep(final List allServers, final DBObjectInfoBean dbObjBean) throws ClassNotFoundException, SQLException
    {
        return depDataMgr.checkOracleMViewDep(allServers, dbObjBean);
    }

    public List<DependencyInfoBean> checkMysqlSameNmTableDep(final List allServers, final DBObjectInfoBean dbObjBean) throws ClassNotFoundException, SQLException
    {
        return depDataMgr.checkMysqlSameNmTableDep(allServers, dbObjBean);
    }
}
