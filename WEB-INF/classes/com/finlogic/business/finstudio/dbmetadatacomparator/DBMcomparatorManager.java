/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.business.finstudio.dbmetadatacomparator;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;
/**
 *
 * @author Jeegar Kumar Patel
 */
public class DBMcomparatorManager
{
    private DBMcomparatorDataManager dbmngr = new DBMcomparatorDataManager();

    public List getDBNames(final DBMcomparatorEntityBean entityBean) throws SQLException, ClassNotFoundException
    {
        return dbmngr.getDBNames(entityBean);
    }

    public Set getObjName(DBMcomparatorEntityBean entityBean, String serv) throws ClassNotFoundException, SQLException
    {
        return dbmngr.getObjName(entityBean,serv);
    }

    public Map<String, List> getDBDefinition(DBMcomparatorEntityBean entityBean, String serv) throws SQLException, ClassNotFoundException
    {
        return dbmngr.getDBDefinition(entityBean,serv);
    }

    public Map<String, List> getObjDefination(String objType, String objName, String dbId, String hdnObjType, String server, DBMcomparatorEntityBean entityBean) throws ClassNotFoundException, SQLException
    {
        return dbmngr.getObjDefination(objType, objName, dbId, hdnObjType, server, entityBean);
    }
}