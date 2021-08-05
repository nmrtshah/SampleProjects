/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.business.finstudio.findatareqexecutor;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.support.rowset.SqlRowSet;

/**
 *
 * @author Sonam Patel
 */
public class FinDataReqExecutorManager
{

    private final FinDataReqExecutorDataManager dataManager = new FinDataReqExecutorDataManager();

    public List getAddProjects() throws ClassNotFoundException, SQLException
    {
        return dataManager.getAddProjects();
    }

    public List getAddRequests(final String reqFltr, final String project) throws ClassNotFoundException, SQLException
    {
        return dataManager.getAddRequests(reqFltr, project);
    }

    public boolean checkProjectUserMapping(final String empCode, final String prjId) throws ClassNotFoundException, SQLException
    {
        SqlRowSet prjSrs = dataManager.checkProjectUserMapping(empCode);
        boolean mapped = false;
        if (prjSrs != null)
        {
            while (prjSrs.next())
            {
                if (prjSrs.getString("PRJ_ID").equals(prjId))
                {
                    mapped = true;
                    break;
                }
            }
        }
        return mapped;
    }

    public List getActualServers(final String project) throws ClassNotFoundException, SQLException
    {
        return dataManager.getActualServers(project);
    }

    public boolean checkProjectDatabaseMapping(final String project, final String dbId) throws ClassNotFoundException, SQLException
    {
        SqlRowSet srs = dataManager.checkProjectDatabaseMapping(project, dbId);
        boolean mapped = false;
        if (srs != null && srs.next())
        {
            if (srs.getString("RESULT").equals("Y"))
            {
                mapped = true;
            }
        }
        return mapped;
    }

    public List getDbTypeName(final String dbID) throws ClassNotFoundException, SQLException
    {
        SqlRowSet srs;
        srs = dataManager.getDbTypeName(dbID);
        List<String> dbTypeName;
        dbTypeName = new ArrayList<String>();
        if (srs != null)
        {
            if (srs.next())
            {
                dbTypeName.add(srs.getString("SERVER_ID"));
                dbTypeName.add(srs.getString("SERVER_TYPE"));
                dbTypeName.add(srs.getString("DATABASE_NAME"));
            }
        }
        return dbTypeName;
    }

    public String getServerID(final String dbID) throws ClassNotFoundException, SQLException
    {
        SqlRowSet srs;
        srs = dataManager.getServerID(dbID);
        if (srs != null)
        {
            if (srs.next())
            {
                return srs.getString("SERVER_ID");
            }
        }
        return "";
    }

    public List getViewProjects() throws ClassNotFoundException, SQLException
    {
        return dataManager.getViewProjects();
    }

    public List getViewEmployees() throws ClassNotFoundException, SQLException
    {
        return dataManager.getViewEmployees();
    }

    public List getViewRequests(final String project, final String reqFltr) throws ClassNotFoundException, SQLException
    {
        return dataManager.getViewRequests(project, reqFltr);
    }

    public Map<String, String[]> validateQueries(final String server, final String queryText) throws ClassNotFoundException, SQLException
    {
        return dataManager.validateQueries(server, queryText);
    }

    public void insertTempData(final FinDataReqExecutorEntityBean entityBean, final Map qBeanMap, final String dbType) throws ClassNotFoundException, SQLException
    {
        dataManager.insertTempData(entityBean, qBeanMap, dbType);
    }

    public List getAddRecords(final String sessionId) throws ClassNotFoundException, SQLException
    {
        List finalList = null;
        List lst = dataManager.getAddRecords(sessionId);
        if (lst != null && lst.size() > 0)
        {
            finalList = new ArrayList();
            String confirm = "NO";
            String backup = "NO";
            String logTable = "NO";

            Map m = null;
            int len = lst.size();
            for (int i = 0; i < len; i++)
            {
                m = (Map) lst.get(i);
                if ("YES".equals(m.get("DEPENDENCY")))
                {
                    confirm = "YES";
                }
                if ("Backup Not Required".equals(m.get("BACKUP")))
                {
                    backup = "YES";
                }
                if ("Log Table Not Required".equals(m.get("LOG_TABLE")))
                {
                    logTable = "YES";
                }
            }

            finalList.add(lst); // list.(0) = list
            finalList.add(confirm); // list.(1) = confirm variable (YES/NO)
            finalList.add(backup); // list.(2) = backup variable (YES/NO)
            finalList.add(logTable); // list.(3) = logTable variable (YES/NO)
        }
        return finalList;
    }

    public List getDependencyRecords(final String queryID, final String tab) throws ClassNotFoundException, SQLException
    {
        return dataManager.getDependencyRecords(queryID, tab);
    }

    public void deleteBatch(final String sessionId, final String batchId) throws ClassNotFoundException, SQLException
    {
        dataManager.deleteBatch(sessionId, batchId);
    }

    public int confirmRequest(final FinDataReqExecutorEntityBean entityBean, final String host) throws ClassNotFoundException, SQLException
    {
        return dataManager.confirmRequest(entityBean, host);
    }

    public void deleteAddRequest(final String sessionId) throws ClassNotFoundException, SQLException
    {
        dataManager.deleteAddRequest(sessionId);
    }

    public List getViewRecords(final FinDataReqExecutorEntityBean entityBean) throws ClassNotFoundException, SQLException
    {
        return dataManager.getViewRecords(entityBean);
    }

    public List viewQueryDetails(final String dataReqId) throws ClassNotFoundException, SQLException
    {
        List finalList = new ArrayList();
        List lst = dataManager.viewQueryDetails(dataReqId);
        String show = "N";

        Map m = null;
        int len = lst.size();
        for (int i = 0; i < len; i++)
        {
            m = (Map) lst.get(i);
            if (!"-".equals(m.get("SYNTAX_VERIFIED_ON")))
            {
                show = "Y";
                break;
            }
        }

        finalList.add(lst); // list.(0) = list
        finalList.add(show); // list.(1) = show variable (Y/N)

        return finalList;
    }

    public String processRequest(final String dataReqId, final String executedBy, final String host, final String operation) throws ClassNotFoundException, SQLException
    {
        String retVal = "";
        if ("Execute".equals(operation))
        {
            retVal = dataManager.executeRequest(dataReqId, executedBy, host);
        }
        else if ("Cancel".equals(operation))
        {
            retVal = dataManager.cancelViewRequest(dataReqId, executedBy, host);
        }
        else if ("Halt".equals(operation))
        {
            retVal = dataManager.haltViewRequest(dataReqId, executedBy, host);
        }
        else if ("Resume".equals(operation))
        {
            retVal = dataManager.resumeViewRequest(dataReqId, executedBy, host);
        }
        return retVal;
    }

    public int createNewRequest(final String dataReqId, final String entryBy) throws ClassNotFoundException, SQLException
    {
        return dataManager.createNewRequest(dataReqId, entryBy);
    }

    public List<Map<String,Object>>  getReportDataLoad()  throws ClassNotFoundException, SQLException
    {
        return dataManager.getReportDataLoad();
    }
}
