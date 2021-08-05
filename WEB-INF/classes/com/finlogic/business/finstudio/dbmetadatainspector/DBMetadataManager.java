/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.business.finstudio.dbmetadatainspector;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.support.rowset.SqlRowSet;

/**
 *
 * @author Nehal
 */
public class DBMetadataManager
{

    private DBMetadataDataManager dbManager = new DBMetadataDataManager();

    public List getDBNames(final DBMetadataEntityBean entityBean) throws SQLException, ClassNotFoundException
    {
        return dbManager.getDBNames(entityBean);
    }

    public String getDBType(String dbId) throws ClassNotFoundException, SQLException
    {
        return dbManager.getDBType(dbId);
    }

    public List getObjName(DBMetadataEntityBean entityBean) throws ClassNotFoundException, SQLException
    {
        return dbManager.getObjName(entityBean);
    }

    public Map<String, List> getDBDefinition(DBMetadataEntityBean entityBean) throws SQLException, ClassNotFoundException
    {
        return dbManager.getDBDefinition(entityBean);
    }

    public SqlRowSet getAllServers() throws SQLException, ClassNotFoundException
    {
        return dbManager.getAllServers();
    }

    public String getDataBaseName(String dbId) throws ClassNotFoundException, SQLException
    {
        return dbManager.getDataBaseName(dbId);
    }

    public String getObjDefination(String objType, String[] objName, String dbId, String hdnObjType) throws ClassNotFoundException, SQLException
    {
        return dbManager.getObjDefination(objType, objName, dbId, hdnObjType);
    }

    public String getTriggerDef(String dbId, String objType, String[] objName) throws ClassNotFoundException, SQLException
    {
        return dbManager.getTriggerDef(dbId, objType, objName);
    }

    public List getSequenceDef(String dbId, String objType, String[] objName) throws ClassNotFoundException, SQLException
    {
        return dbManager.getSequenceDef(dbId, objType, objName);
    }
}
