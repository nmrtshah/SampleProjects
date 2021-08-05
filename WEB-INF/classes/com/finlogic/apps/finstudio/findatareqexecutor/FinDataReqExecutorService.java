/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.apps.finstudio.findatareqexecutor;

import com.finlogic.business.finstudio.findatareqexecutor.FinDataReqExecutorEntityBean;
import com.finlogic.business.finstudio.findatareqexecutor.FinDataReqExecutorManager;
import com.finlogic.business.finstudio.findatareqexecutor.QueryEntityBean;
import com.finlogic.util.Logger;
import com.finlogic.util.datastructure.JSONParser;
import com.finlogic.util.findatareqexecutor.FileService;
import com.finlogic.util.findatareqexecutor.MysqlAnalyzer;
import com.finlogic.util.findatareqexecutor.OracleAnalyzer;
import com.finlogic.util.findatareqexecutor.QueryInfoBean;
import com.finlogic.util.findatareqexecutor.ServerPropertyReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Sonam Patel
 */
public class FinDataReqExecutorService
{

    private final FinDataReqExecutorManager manager = new FinDataReqExecutorManager();

    private FinDataReqExecutorEntityBean formBeanToEntityBean(FinDataReqExecutorFormBean formBean)
    {
        FinDataReqExecutorEntityBean entityBean;
        entityBean = new FinDataReqExecutorEntityBean();
        if (formBean.getCmbAddProjId() != null && !formBean.getCmbAddProjId().equals("-1"))
        {
            int n = Integer.parseInt(formBean.getCmbAddProjId().split("-")[0].trim());
            entityBean.setProjId(n);
        }
        if (formBean.getCmbAddReqId() != null && !formBean.getCmbAddReqId().equals("-1"))
        {
            int n = Integer.parseInt(formBean.getCmbAddReqId().split("-")[0].trim());
            entityBean.setReqId(n);
        }
        entityBean.setPurpose(formBean.getTxtAddPurpose());
        if (formBean.getCmbAddExecType() != null && !formBean.getCmbAddExecType().equals("-1"))
        {
            entityBean.setExecutionType(formBean.getCmbAddExecType());
        }
        entityBean.setEntryBy(formBean.getEmpCode());

        if (formBean.getCmbAddDatabase() != null && !formBean.getCmbAddDatabase().equals("-1"))
        {
            entityBean.setDatabaseID(formBean.getCmbAddDatabase());
        }
        entityBean.setSyntaxVerifiedOn(formBean.getVerifyServer());

        if (formBean.getTxtAddQuery() != null && !"".equals(formBean.getTxtAddQuery()))
        {
            String[] queries = formBean.getTxtAddQuery().split("\nGO\n");
            String[] devResults = formBean.getDevExeResult();
            String[] devStatus = formBean.getDevExeStatus();
            String[] devStartTimes = formBean.getDevExeStartTime();
            String[] devEndTimes = formBean.getDevExeEndTime();
            int len = devResults.length;
            QueryEntityBean queryBean[] = new QueryEntityBean[len];
            for (int i = 0; i < len; i++)
            {
                queryBean[i] = new QueryEntityBean();
                queryBean[i].setQuery(queries[i].trim());
                queryBean[i].setDevExeResult(devResults[i].trim());
                queryBean[i].setDevExeStatus(devStatus[i].trim());
                queryBean[i].setDevExeStartTime(devStartTimes[i].trim());
                queryBean[i].setDevExeEndTime(devEndTimes[i].trim());
            }
            entityBean.setQueryBean(queryBean);
        }
        entityBean.setQueryBackup(formBean.getHdnAddBackup());
        entityBean.setQueryLogTable(formBean.getHdnAddLogTable());

        entityBean.setuSessionId(formBean.getHdnUSessionId());

        entityBean.setTxtViewDataReqId(formBean.getTxtViewDataReqId());
        if (formBean.getCmbViewProjId() != null && !formBean.getCmbViewProjId().equals("-1"))
        {
            entityBean.setCmbViewProjId(formBean.getCmbViewProjId());
        }
        if (formBean.getCmbViewEmp() != null && !formBean.getCmbViewEmp().equals("-1"))
        {
            entityBean.setCmbViewEmp(formBean.getCmbViewEmp());
        }
        if (formBean.getCmbViewReqId() != null && !formBean.getCmbViewReqId().equals("-1"))
        {
            entityBean.setCmbViewReqId(formBean.getCmbViewReqId());
        }
        entityBean.setTxtViewPurpose(formBean.getTxtViewPurpose());
        if (formBean.getCmbViewStatus() != null && !formBean.getCmbViewStatus().equals("-1"))
        {
            entityBean.setCmbViewStatus(formBean.getCmbViewStatus());
        }
        entityBean.setDtFromDate(formBean.getDtFromDate());
        entityBean.setDtToDate(formBean.getDtToDate());
        entityBean.setCurrentStatus(formBean.getCurrentStatus());
        return entityBean;
    }

    public List getAddProjects() throws ClassNotFoundException, SQLException
    {
        return manager.getAddProjects();
    }

    public List getAddRequests(final String reqFltr, final String project) throws ClassNotFoundException, SQLException
    {
        return manager.getAddRequests(reqFltr, project);
    }

    public boolean checkProjectUserMapping(final String empCode, final String prjId) throws ClassNotFoundException, SQLException
    {
        return manager.checkProjectUserMapping(empCode, prjId);
    }

    public List getActualServers(final String project) throws ClassNotFoundException, SQLException
    {
        return manager.getActualServers(project);
    }

    public boolean checkProjectDatabaseMapping(final String project, final String dbId) throws ClassNotFoundException, SQLException
    {
        return manager.checkProjectDatabaseMapping(project, dbId);
    }

    public List getDbTypeName(final String dbID) throws ClassNotFoundException, SQLException
    {
        return manager.getDbTypeName(dbID);
    }

    public String getServerID(final String dbID) throws ClassNotFoundException, SQLException
    {
        return manager.getServerID(dbID);
    }

    public boolean copyFile(final String fileName)
    {
        FileService fService = new FileService();
        return fService.copyFile(fileName);
    }

    public String getFileQuery(final String fileName) throws IOException
    {
        FileService fService = new FileService();
        return fService.getFileQuery(fileName);
    }

    public String getVerificationServer(final String actualServerID, final String verifyServerType)
    {
        ServerPropertyReader propReader = new ServerPropertyReader();
        return propReader.getVerificationServer(actualServerID, verifyServerType);
    }

    public List getViewProjects() throws ClassNotFoundException, SQLException
    {
        return manager.getViewProjects();
    }

    public List getViewEmployees() throws ClassNotFoundException, SQLException
    {
        return manager.getViewEmployees();
    }

    public List getViewRequests(final String project, final String reqFltr) throws ClassNotFoundException, SQLException
    {
        return manager.getViewRequests(project, reqFltr);
    }

    public String formatQueryText(final String queryText)
    {
        String query = "\n" + queryText.trim() + "\n";

        query = query.replaceAll("\n[\\s]*go[\\s]*\n", "\nGO\n");
        query = query.replaceAll("\n[\\s]*Go[\\s]*\n", "\nGO\n");
        query = query.replaceAll("\n[\\s]*gO[\\s]*\n", "\nGO\n");
        query = query.replaceAll("\n[\\s]*GO[\\s]*\n", "\nGO\n");

        //Remove Blank Lines From Query Text
        query = query.replaceAll("\n[\\s]*\n", "\n");
        query = query.trim();

        //Replace 'GO' From The Start Of Query Text
        while (query.toUpperCase().startsWith("GO\n"))
        {
            query = query.substring(3);
            query = query.trim();
        }

        //Remove Unnecessary 'GO' From End Of Query Text
        while (query.toUpperCase().endsWith("\nGO"))
        {
            query = query.substring(0, query.length() - 3);
            query = query.trim();
        }

        //Replace Multiple 'GO' Between Queries By Single 'GO'
        while (query.contains("\nGO\nGO\n"))
        {
            query = query.replace("\nGO\nGO\n", "\nGO\n");
            query = query.trim();
        }
        return query.trim();
    }

    public Map analyzeQuery(final String dbType, final String query, final String server, final String database)
    {
        String[] queries = query.split("\nGO\n");

        Map map = new HashMap();
        Map qBeanMap = new HashMap();
        String message = "";
        String queryText = "";
        if ("MYSQL".equals(dbType))
        {
            MysqlAnalyzer analyser = new MysqlAnalyzer();
            //Analyze Each Query
            for (int i = 0; i < queries.length; i++)
            {
                try
                {
                    QueryInfoBean qBean = analyser.analyzeQuery(queries[i], server, database);
                    //USE Statement's Validations
                    if (queries.length == 1 && qBean.getClause() != null && "USE".equals(qBean.getClause()))
                    {
                        message = "You Cannot Use Only USE Statement";
                    }
                    else if (i == 0 && (qBean.getClause() == null || !"USE".equals(qBean.getClause())))
                    {
                        message = "Mysql Queries must contain USE statement before all queries";
                    }
                    else if (i == 0 && qBean.getDbObjInfo() != null && (qBean.getDbObjInfo().getSchema() == null || !qBean.getDbObjInfo().getSchema().equals(database)))
                    {
                        message = "Invalid USE Statement. Please use correct schema name : " + database;
                    }
                    else if (i == 0)
                    {
                        for (int j = 1; j < queries.length; j++)
                        {
                            if (queries[j].trim().toUpperCase().startsWith("USE "))
                            {
                                message = "MySQL Statement cannot contain more then one USE statements";
                            }
                        }
                    }
                    else if (queries[i].replace("\\s", " ").trim().toUpperCase().startsWith("CREATE OR REPLACE") && (qBean.getDbObjInfo() == null || qBean.getDbObjInfo().getObjType() == null))
                    {
                        message = "Unsupported Keyword Found. [ CREATE OR REPLACE ]";
                        queryText = queries[i];
                    }
                    else if (queries[i].replace("\\s", " ").trim().toUpperCase().startsWith("CREATE OR REPLACE") && qBean.getDbObjInfo() != null && qBean.getDbObjInfo().getObjType() != null && !"VIEW".equals(qBean.getDbObjInfo().getObjType()))
                    {
                        message = "Unsupported Keyword Found. [ CREATE OR REPLACE ]";
                        queryText = queries[i];
                    }
                    else if (qBean.getClause() == null)
                    {
                        message = "Unsupported Keyword Found. [" + queries[i].trim().split(" ")[0] + "]";
                        queryText = queries[i];
                    }
                    else if ("UPDATE".equals(qBean.getClause()) && (qBean.getSubClause() == null || !"SET".equals(qBean.getSubClause())))
                    {
                        message = "[ " + qBean.getClause() + " ] Operation Can't be Performed Without 'SET' Clause";
                        queryText = queries[i];
                    }
                    else if (("UPDATE".equals(qBean.getClause()) || "DELETE".equals(qBean.getClause())) && !qBean.isIsWherePresent() && !qBean.isNeedConfirmation())
                    {
                        message = "[ " + qBean.getClause() + " ] Operation Can't be Performed Without 'WHERE' Condition";
                        queryText = queries[i];
                    }
                    else if ("CREATE".equals(qBean.getClause()) && (qBean.getDbObjInfo() == null || qBean.getDbObjInfo().getObjType() == null
                            || !("FUNCTION".equals(qBean.getDbObjInfo().getObjType()) || "PROCEDURE".equals(qBean.getDbObjInfo().getObjType())
                            || "TABLE".equals(qBean.getDbObjInfo().getObjType()) || "TRIGGER".equals(qBean.getDbObjInfo().getObjType())
                            || "INDEX".equals(qBean.getDbObjInfo().getObjType()))))
                    {
                        message = "[ CREATE ] is currently supported with only [ FUNCTION, PROCEDURE, TABLE, TRIGGER ]";
                        queryText = queries[i];
                    }
                    else if ("ALTER".equals(qBean.getClause()) && (qBean.getDbObjInfo() == null || qBean.getDbObjInfo().getObjType() == null
                            || !("TABLE".equals(qBean.getDbObjInfo().getObjType()) || "VIEW".equals(qBean.getDbObjInfo().getObjType()))))
                    {
                        message = "[ ALTER ] is currently supported with only [ TABLE, VIEW ]";
                        queryText = queries[i];
                    }
                    else if ("ALTER".equals(qBean.getClause()) && qBean.getDbObjInfo() != null && qBean.getDbObjInfo().getObjType() != null
                            && ("TABLE".equals(qBean.getDbObjInfo().getObjType()) || "VIEW".equals(qBean.getDbObjInfo().getObjType()))
                            && qBean.getSubClause() == null)
                    {
                        message = "Unsupported Clause Found in [ ALTER " + qBean.getDbObjInfo().getObjType() + " ]";
                        queryText = queries[i];
                    }
                    else if ("DROP".equals(qBean.getClause()) && (qBean.getDbObjInfo() == null || qBean.getDbObjInfo().getObjType() == null
                            || !("FUNCTION".equals(qBean.getDbObjInfo().getObjType()) || "PROCEDURE".equals(qBean.getDbObjInfo().getObjType()) || "TRIGGER".equals(qBean.getDbObjInfo().getObjType()))))
                    {
                        message = "[ DROP ] is currently supported with only [ FUNCTION, PROCEDURE, TRIGGER ]";
                        queryText = queries[i];
                    }
                    else if (qBean.getDbObjInfo() != null && qBean.getDbObjInfo().getSchema() != null)
                    {
                        message = "Schema Name should not be used in any statement";
                        queryText = queries[i];
                    }
                    else if (qBean.getDbObjInfo() == null || qBean.getDbObjInfo().getObjName() == null || "".equals(qBean.getDbObjInfo().getObjName()))
                    {
                        message = "Table Name is missing";
                        queryText = queries[i];
                    }
                    else if ("DROP".equals(qBean.getClause()) && qBean.getDbObjInfo() != null && qBean.getDbObjInfo().getObjType() != null && "TRIGGER".equals(qBean.getDbObjInfo().getObjType()))
                    {
                        for (int j = i + 2; j < queries.length; j++)
                        {
                            String qry = queries[j].replaceAll("\\s", " ").trim().toUpperCase();
                            while (qry.contains("  "))
                            {
                                qry = qry.replace("  ", " ");
                            }
                            if (qry.startsWith("CREATE TRIGGER " + qBean.getDbObjInfo().getObjName().toUpperCase() + " "))
                            {
                                message = "CREATE TRIGGER " + qBean.getDbObjInfo().getObjName() + " must be the immediate statement after DROP TRIGGER " + qBean.getDbObjInfo().getObjName();
                                queryText = queries[j];
                                break;
                            }
                        }
                    }
                    if (!"".equals(message) || !"".equals(queryText))
                    {
                        map.put("message", message);
                        map.put("query", queryText);
                        return map;
                    }
                    else if (i > 0)
                    {
                        qBeanMap.put(i, qBean);
                    }
                }
                catch (Exception e)
                {
                    message = "Invalid Query";
                    queryText = queries[i];
                    Logger.ErrorLogger(e);
                    map.put("message", message);
                    map.put("query", queryText);
                    return map;
                }
            }
        }
        else if ("ORACLE".equals(dbType))
        {
            OracleAnalyzer analyser = new OracleAnalyzer();
            //Analyze Each Query
            for (int i = 0; i < queries.length; i++)
            {
                try
                {
                    QueryInfoBean qBean = analyser.analyzeQuery(queries[i], server);
                    if (qBean.getClause() == null)
                    {
                        message = "Unsupported Keyword Found. [" + queries[i].trim().split(" ")[0] + "]";
                        queryText = queries[i];
                    }
                    else if ("UPDATE".equals(qBean.getClause()) && (qBean.getSubClause() == null || !"SET".equals(qBean.getSubClause())))
                    {
                        message = "[ " + qBean.getClause() + " ] Operation Can't be Performed Without 'SET' Clause";
                        queryText = queries[i];
                    }
                    else if (("UPDATE".equals(qBean.getClause()) || "DELETE".equals(qBean.getClause())) && !qBean.isIsWherePresent() && !qBean.isNeedConfirmation())
                    {
                        message = "[ " + qBean.getClause() + " ] Operation Can't be Performed Without 'WHERE' Condition";
                        queryText = queries[i];
                    }
                    else if ("CREATE".equals(qBean.getClause()) && (qBean.getDbObjInfo() == null || qBean.getDbObjInfo().getObjType() == null
                            || !("FUNCTION".equals(qBean.getDbObjInfo().getObjType()) || "INDEX".equals(qBean.getDbObjInfo().getObjType())
                            || "PACKAGE".equals(qBean.getDbObjInfo().getObjType()) || "PACKAGE BODY".equals(qBean.getDbObjInfo().getObjType())
                            || "PROCEDURE".equals(qBean.getDbObjInfo().getObjType()) || "SEQUENCE".equals(qBean.getDbObjInfo().getObjType())
                            || "TABLE".equals(qBean.getDbObjInfo().getObjType()) || "TRIGGER".equals(qBean.getDbObjInfo().getObjType())
                            || "TYPE".equals(qBean.getDbObjInfo().getObjType()))))
                    {
                        message = "[ CREATE ] is currently supported with only [ FUNCTION, PACKAGE, PACKAGE BODY, PROCEDURE, SEQUENCE, TABLE, TRIGGER, TYPE ]";
                        message += "\n[ CREATE OR REPLACE ] is currently supported with only [ FUNCTION, PACKAGE, PACKAGE BODY, PROCEDURE, TRIGGER, TYPE ]";
                        queryText = queries[i];
                    }
                    else if ("ALTER".equals(qBean.getClause()) && (qBean.getDbObjInfo() == null || qBean.getDbObjInfo().getObjType() == null
                            || !("INDEX".equals(qBean.getDbObjInfo().getObjType()) || "SEQUENCE".equals(qBean.getDbObjInfo().getObjType())
                            || "TABLE".equals(qBean.getDbObjInfo().getObjType()) || "VIEW".equals(qBean.getDbObjInfo().getObjType()))))
                    {
                        message = "[ ALTER ] is currently supported with only [ INDEX, SEQUENCE, TABLE, VIEW ]";
                        queryText = queries[i];
                    }
                    else if ("ALTER".equals(qBean.getClause()) && qBean.getDbObjInfo() != null && qBean.getDbObjInfo().getObjType() != null
                            && ("INDEX".equals(qBean.getDbObjInfo().getObjType()) || "SEQUENCE".equals(qBean.getDbObjInfo().getObjType())
                            || "TABLE".equals(qBean.getDbObjInfo().getObjType()) || "VIEW".equals(qBean.getDbObjInfo().getObjType()))
                            && qBean.getSubClause() == null)
                    {
                        message = "Unsupported Clause Found in [ ALTER " + qBean.getDbObjInfo().getObjType() + " ]";
                        queryText = queries[i];
                    }
                    else if ("DROP".equals(qBean.getClause()) && (qBean.getDbObjInfo() == null || qBean.getDbObjInfo().getObjType() == null
                            || !("FUNCTION".equals(qBean.getDbObjInfo().getObjType()) || "PROCEDURE".equals(qBean.getDbObjInfo().getObjType())
                            || "SEQUENCE".equals(qBean.getDbObjInfo().getObjType()) || "PACKAGE".equals(qBean.getDbObjInfo().getObjType())
                            || "TRIGGER".equals(qBean.getDbObjInfo().getObjType()) || "TYPE".equals(qBean.getDbObjInfo().getObjType()))))
                    {
                        message = "[ DROP ] is currently supported with only [ FUNCTION, PROCEDURE, SEQUENCE, PACKAGE, TRIGGER, TYPE ]";
                        queryText = queries[i];
                    }
                    else if (qBean.getDbObjInfo() == null || qBean.getDbObjInfo().getObjName() == null || "".equals(qBean.getDbObjInfo().getObjName()))
                    {
                        message = "Table Name is missing";
                        queryText = queries[i];
                    }
                    else if (qBean.getDbObjInfo() == null || qBean.getDbObjInfo().getSchema() == null || !database.equals(qBean.getDbObjInfo().getSchema()))
                    {
                        message = "Specify correct Schema Name in statement";
                        queryText = queries[i];
                    }
                    else if ("DROP".equals(qBean.getClause()) && qBean.getDbObjInfo() != null && qBean.getDbObjInfo().getObjType() != null && "TRIGGER".equals(qBean.getDbObjInfo().getObjType()))
                    {
                        for (int j = i + 2; j < queries.length; j++)
                        {
                            String qry = queries[j].replaceAll("\\s", " ").trim().toUpperCase();
                            while (qry.contains("  "))
                            {
                                qry = qry.replace("  ", " ");
                            }
                            if (qry.startsWith("CREATE TRIGGER " + database + "." + qBean.getDbObjInfo().getObjName().toUpperCase() + " ")
                                    || qry.startsWith("CREATE OR REPLACE TRIGGER " + database.toUpperCase() + "." + qBean.getDbObjInfo().getObjName().toUpperCase() + " "))
                            {
                                message = "CREATE TRIGGER " + qBean.getDbObjInfo().getObjName() + " must be the immediate statement after DROP TRIGGER " + qBean.getDbObjInfo().getObjName();
                                queryText = queries[j];
                                break;
                            }
                        }
                    }
                    if (!"".equals(message) || !"".equals(queryText))
                    {
                        map.put("message", message);
                        map.put("query", queryText);
                        return map;
                    }
                    else
                    {
                        qBeanMap.put(i, qBean);
                    }
                }
                catch (Exception e)
                {
                    message = "Invalid Query";
                    queryText = queries[i];
                    Logger.ErrorLogger(e);
                    map.put("message", message);
                    map.put("query", queryText);
                    return map;
                }
            }
        }
        else
        {
            message = "Database Type Not Found";
            map.put("message", message);
            return map;
        }
        map.put("beanMap", qBeanMap);
        return map;
    }

    public Map<String, String[]> validateQueries(final String server, final String queryText) throws ClassNotFoundException, SQLException
    {
        return manager.validateQueries(server, queryText);
    }

    public void insertTempData(final FinDataReqExecutorFormBean formBean, final Map qBeanMap, final String dbType) throws ClassNotFoundException, SQLException
    {
        manager.insertTempData(formBeanToEntityBean(formBean), qBeanMap, dbType);
    }

    public List getAddRecords(final String sessionId) throws ClassNotFoundException, SQLException
    {
        return manager.getAddRecords(sessionId);
    }

    public List getDependencyRecords(final String queryID, final String tab) throws ClassNotFoundException, SQLException
    {
        return manager.getDependencyRecords(queryID, tab);
    }

    public void deleteBatch(final String sessionId, final String batchId) throws ClassNotFoundException, SQLException
    {
        manager.deleteBatch(sessionId, batchId);
    }

    public int confirmRequest(final FinDataReqExecutorFormBean formBean, final String host) throws ClassNotFoundException, SQLException
    {
        return manager.confirmRequest(formBeanToEntityBean(formBean), host);
    }

    public void deleteAddRequest(final String sessionId) throws ClassNotFoundException, SQLException
    {
        manager.deleteAddRequest(sessionId);
    }

    public List getViewRecords(final FinDataReqExecutorFormBean formBean) throws ClassNotFoundException, SQLException
    {
        return manager.getViewRecords(formBeanToEntityBean(formBean));
    }

    public List viewQueryDetails(final String dataReqId) throws ClassNotFoundException, SQLException
    {
        return manager.viewQueryDetails(dataReqId);
    }

    public synchronized String processRequest(final String dataReqId, final String executedBy, final String host, final String operation) throws ClassNotFoundException, SQLException
    {
        String status = manager.processRequest(dataReqId, executedBy, host, operation);
        if (status != null && !"Error".equals(status))
        {
            if ("Done".equals(status))
            {
                status = "Data Request " + dataReqId + " is Executed Successfully";
            }
            else if ("Partial".equals(status))
            {
                status = "Data Request " + dataReqId + " is Partially Executed";
            }
            else if ("Rollbacked".equals(status))
            {
                status = "Data Request " + dataReqId + " is Rollbacked";
            }
            else if ("Cancelled".equals(status))
            {
                status = "Data Request " + dataReqId + " is Cancelled";
            }
            else if ("Halted".equals(status))
            {
                status = "Data Request " + dataReqId + " is On Halt";
            }
            else if ("Resumed".equals(status))
            {
                status = "Data Request " + dataReqId + " is Resumed";
            }
            else if ("Already Done".equals(status))
            {
                status = "Data Request " + dataReqId + " is Already Processed. Current status : Executed Successfully";
            }
            else if ("Already Partial".equals(status))
            {
                status = "Data Request " + dataReqId + " is Already Processed. Current status : Partially Executed";
            }
            else if ("Already Rollbacked".equals(status))
            {
                status = "Data Request " + dataReqId + " is Already Processed. Current status : Rollbacked";
            }
            else if ("Already Cancelled".equals(status))
            {
                status = "Data Request " + dataReqId + " is Already Processed. Current status : Cancelled";
            }
            else if ("Already Halted".equals(status))
            {
                status = "Data Request " + dataReqId + " is Already Processed. Current status : On Halt";
            }
            else if ("Already Pending".equals(status))
            {
                status = "Data Request " + dataReqId + " is Already Processed. Current status : Pending";
            }
        }
        else
        {
            status = "Some Technical Problem Arise while executing Data Request " + dataReqId + ".";
        }
        return status;
    }

    public String createNewRequest(final String dataReqId, final String entryBy) throws ClassNotFoundException, SQLException
    {
        int reqId = manager.createNewRequest(dataReqId, entryBy);
        if (reqId == 0)
        {
            return "Problem in Creating New Request For Request ID " + dataReqId;
        }
        else
        {
            return "New Data Request ID is " + reqId;
        }
    }

    public List<Map<String,Object>> getReportDataLoad() throws ClassNotFoundException,SQLException
    {
        return manager.getReportDataLoad();
    }
}
