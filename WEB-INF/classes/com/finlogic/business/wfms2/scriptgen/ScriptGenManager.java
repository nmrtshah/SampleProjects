/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.business.wfms2.scriptgen;

import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.support.rowset.SqlRowSet;

/**
 *
 * @author Ankur Mistry
 */
public class ScriptGenManager
{

    ScriptGenDataManager dbmManager = new ScriptGenDataManager();

    public List getProjectList() throws SQLException
    {
        return dbmManager.getProjectList();
    }

    public List getHTMLControlList() throws SQLException
    {
        return dbmManager.getHTMLControlList();
    }

    public List getModuleTreeList(final String projectID) throws SQLException
    {
        return dbmManager.getModuleTreeList(projectID);
    }

    public List getTestCaseList(final String projectID, final String moduleID) throws SQLException
    {
        return dbmManager.getTestCaseList(projectID, moduleID);
    }

    public List getControlEventList() throws SQLException
    {
        return dbmManager.getControlEventList();
    }

    public List getTestCaseNatureList() throws SQLException
    {
        return dbmManager.getTestCaseNatureList();
    }

    public List getAccessTypeList(final int controlID) throws SQLException
    {
        return dbmManager.getAccessTypeList(controlID);
    }

    public List getAccessTypeList() throws SQLException
    {
        return dbmManager.getAccessTypeList();
    }

    public List getTestCaseValuesList(final String projectID, final String moduleID, final String testCaseID) throws SQLException
    {
        return dbmManager.getTestCaseValuesList(projectID, moduleID, testCaseID);
    }

    public SqlRowSet getParentModuleList(final int moduleID) throws SQLException
    {
        return dbmManager.getParentModuleList(moduleID);
    }

    public int inertIntoDB(ScriptGenEntityBean entityBean) throws ClassNotFoundException, SQLException
    {
        return dbmManager.insertIntoDB(entityBean);
    }

    public List getGenClassList(int projectID, int moduleID, String fromDate, String toDate) throws ClassNotFoundException, SQLException
    {
        return dbmManager.getGenClassList(projectID, moduleID, fromDate, toDate);
    }

    public List getPrjctModule(String projectID) throws ClassNotFoundException, SQLException
    {
        return dbmManager.getPrjctModule(projectID);
    }
///*------- Edited by Divyang Kankotiya -------- */
    public List getTestCaseList(String testCaseID) throws ClassNotFoundException, SQLException
    {
        return dbmManager.getTestCaseList(testCaseID);
    }

//    public SqlRowSet getAllTestCaseID(String projectID, int moduleID) throws ClassNotFoundException, SQLException
//    {
//        return dbmManager.getAllTestCaseID(projectID, moduleID);
//    }
/*-------End Edited by Divyang Kankotiya -------- */
    public List getAllData(int srno) throws ClassNotFoundException, SQLException
    {
        return dbmManager.getAllData(srno);
    }

    public List getModuleList(String projectID, String moduleID) throws ClassNotFoundException, SQLException
    {
        return dbmManager.getModuleList(projectID, moduleID);
    }
}