/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.business.finstudio.finstatistics;

import com.finlogic.util.persistence.SQLUtility;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

/**
 *
 * @author njuser
 */
public class finStatistics
{

    private static final String ALIASNAME = "finstudio_mysql";
    SQLUtility SqlUtil = new SQLUtility();

    public static void main(String[] args) throws ClassNotFoundException, SQLException
    {
        finStatistics obj = new finStatistics();
        obj.getRGV2_Statistics();
        obj.getRGV3_Statistics();
        obj.getMGV2_Statistics();
        obj.getWSGEN_Statistics();
        obj.getORATOSQL_Statistics();

        obj.finFileTransferTotalReqExecuted();
        obj.finFileTransferTop3Users();
        obj.finFileTransferTop3Projects();

        obj.getDRETotalReqExecuted();
        obj.getDRETop3Users();
        obj.getDRETop3Projects();
    }

    private String getRGV2_Statistics() throws ClassNotFoundException, SQLException
    {
        StringBuilder query = new StringBuilder();
        query.append("SELECT COUNT(DISTINCT MODULE_NAME) MODULE_NAME,COUNT(DISTINCT EMP_CODE) EMP_CODE, COUNT(DISTINCT PROJECT_NAME) PROJECT_NAME FROM RPT_MAIN WHERE REMARKS = 'Report Generation V2'");
        String result = "";
        SqlRowSet srs = SqlUtil.getRowSet("finstudio_mysql", query.toString());

        while (srs.next())
        {
            result = "Report Generation V2 (JqGrid) is used in " + srs.getString("PROJECT_NAME") + " Projects, " + srs.getString("MODULE_NAME") + " Modules and by " + srs.getString("EMP_CODE") + " distinct users.";
        }

        //check if record exist update existing else insert new
        query.setLength(0);
        query.append("SELECT SRNO,GROUP_NAME,STATS_NAME FROM FINSTUDIO_STATISTICS WHERE GROUP_NAME = 'Report Generation V2'");
        srs = SqlUtil.getRowSet(ALIASNAME, query.toString());
        if (srs != null && srs.next())
        {
            srs.beforeFirst();
            while (srs.next())
            {
                String strGrpName = srs.getString("GROUP_NAME");
                String strStatsName = srs.getString("STATS_NAME");
                if (strGrpName.equalsIgnoreCase("Report Generation V2") && strStatsName.equalsIgnoreCase("RGV2 Detail"))
                {
                    query.setLength(0);
                    query.append("UPDATE FINSTUDIO_STATISTICS SET STATS = 0,STATS_STRING = '");
                    query.append(result);
                    query.append("', ON_DATE = SYSDATE(),IS_ACTIVE = 'Y' WHERE SRNO =");
                    query.append(srs.getString("SRNO"));
                    SqlUtil.persist(ALIASNAME, query.toString());
                }
            }
        }
        else
        {
            query.setLength(0);
            query.append("INSERT INTO FINSTUDIO_STATISTICS (GROUP_NAME,STATS_NAME,STATS,STATS_STRING,ON_DATE,IS_ACTIVE) VALUES(");
            query.append("'Report Generation V2','RGV2 Detail',0,'");
            query.append(result);
            query.append("',SYSDATE(),'Y')");
            SqlUtil.persist(ALIASNAME, query.toString());
        }
        return result;
    }

    private String getRGV3_Statistics() throws ClassNotFoundException, SQLException
    {
        StringBuilder query = new StringBuilder();
        query.append("SELECT COUNT(DISTINCT MODULE_NAME) MODULE_NAME,COUNT(DISTINCT EMP_CODE) EMP_CODE, COUNT(DISTINCT PROJECT_NAME) PROJECT_NAME FROM RPT_MAIN WHERE REMARKS = 'Report Generation V3'");
        String result = "";
        SqlRowSet srs = SqlUtil.getRowSet("finstudio_mysql", query.toString());

        while (srs.next())
        {
            result = "Report Generation V3 (DHTMLx) is used in " + srs.getString("PROJECT_NAME") + " Projects, " + srs.getString("MODULE_NAME") + " Modules and by " + srs.getString("EMP_CODE") + " distinct users.";
        }

        //check if record exist update existing else insert new
        query.setLength(0);
        query.append("SELECT SRNO,GROUP_NAME,STATS_NAME FROM FINSTUDIO_STATISTICS WHERE GROUP_NAME = 'Report Generation V3'");
        srs = SqlUtil.getRowSet(ALIASNAME, query.toString());
        if (srs != null && srs.next())
        {
            srs.beforeFirst();
            while (srs.next())
            {
                String strGrpName = srs.getString("GROUP_NAME");
                String strStatsName = srs.getString("STATS_NAME");
                if (strGrpName.equalsIgnoreCase("Report Generation V3") && strStatsName.equalsIgnoreCase("RGV3 Detail"))
                {
                    query.setLength(0);
                    query.append("UPDATE FINSTUDIO_STATISTICS SET STATS = 0,STATS_STRING = '");
                    query.append(result);
                    query.append("', ON_DATE = SYSDATE(),IS_ACTIVE = 'Y' WHERE SRNO =");
                    query.append(srs.getString("SRNO"));
                    SqlUtil.persist(ALIASNAME, query.toString());
                }
            }
        }
        else
        {
            query = new StringBuilder();
            query.append("INSERT INTO FINSTUDIO_STATISTICS (GROUP_NAME,STATS_NAME,STATS,STATS_STRING,ON_DATE,IS_ACTIVE) VALUES(");
            query.append("'Report Generation V3','RGV3 Detail',0,'");
            query.append(result);
            query.append("',SYSDATE(),'Y')");
            SqlUtil.persist(ALIASNAME, query.toString());
        }
        return result;
    }

    private String getMGV2_Statistics() throws ClassNotFoundException, SQLException
    {
        StringBuilder query = new StringBuilder();
        query.append("SELECT COUNT(DISTINCT PROJECT_NAME) PROJECTS, COUNT(DISTINCT MODULE_NAME) MODULES, COUNT(DISTINCT EMP_CODE) USERS FROM MST_MAIN");
        String result = "";
        SqlRowSet srs = SqlUtil.getRowSet(ALIASNAME, query.toString());
        if (srs != null && srs.next())
        {
            result = "Master Generation V2 is used in " + srs.getString("PROJECTS") + " Projects, " + srs.getString("MODULES") + " Modules and by " + srs.getString("USERS") + " distinct users.";
        }

        //Check If record exist Then update existing record Else insert new
        query.setLength(0);
        query.append("SELECT SRNO, GROUP_NAME, STATS_NAME FROM FINSTUDIO_STATISTICS WHERE GROUP_NAME = 'Master Generation V2'");
        srs = SqlUtil.getRowSet(ALIASNAME, query.toString());
        if (srs != null && srs.next())
        {
            srs.beforeFirst();
            while (srs.next())
            {
                String strGrpName = srs.getString("GROUP_NAME");
                String strStatsName = srs.getString("STATS_NAME");
                if (strGrpName.equalsIgnoreCase("Master Generation V2") && strStatsName.equalsIgnoreCase("MGV2 Detail"))
                {
                    query.setLength(0);
                    query.append("UPDATE FINSTUDIO_STATISTICS ");
                    query.append("SET STATS = 0, STATS_STRING = '").append(result).append("', ON_DATE = SYSDATE(), IS_ACTIVE = 'Y' ");
                    query.append("WHERE SRNO = ").append(srs.getString("SRNO"));
                    SqlUtil.persist(ALIASNAME, query.toString());
                }
            }
        }
        else
        {
            query.setLength(0);
            query.append("INSERT INTO FINSTUDIO_STATISTICS (GROUP_NAME, STATS_NAME, STATS,STATS_STRING, ON_DATE, IS_ACTIVE) VALUES(");
            query.append("'Master Generation V2', 'MGV2 Detail', 0, '");
            query.append(result);
            query.append("', SYSDATE(), 'Y')");
            SqlUtil.persist(ALIASNAME, query.toString());
        }
        return result;
    }

    private String getWSGEN_Statistics() throws ClassNotFoundException, SQLException
    {
        StringBuilder query = new StringBuilder();
        query.append("SELECT COUNT(DISTINCT PROJECT_NAME) PROJECTS, COUNT(DISTINCT MODULE_NAME) MODULES, COUNT(DISTINCT EMP_CODE) USERS FROM WEBSRVC_MAIN");
        String result = "";
        SqlRowSet srs = SqlUtil.getRowSet(ALIASNAME, query.toString());
        if (srs != null && srs.next())
        {
            result = "Web Service Generation is used in " + srs.getString("PROJECTS") + " Projects, " + srs.getString("MODULES") + " Modules and by " + srs.getString("USERS") + " distinct users.";
        }

        //Check If record exist Then update existing record Else insert new
        query.setLength(0);
        query.append("SELECT SRNO, GROUP_NAME, STATS_NAME FROM FINSTUDIO_STATISTICS WHERE GROUP_NAME = 'Web Service Generation'");
        srs = SqlUtil.getRowSet(ALIASNAME, query.toString());
        if (srs != null && srs.next())
        {
            srs.beforeFirst();
            while (srs.next())
            {
                String strGrpName = srs.getString("GROUP_NAME");
                String strStatsName = srs.getString("STATS_NAME");
                if (strGrpName.equalsIgnoreCase("Web Service Generation") && strStatsName.equalsIgnoreCase("WSGEN Detail"))
                {
                    query.setLength(0);
                    query.append("UPDATE FINSTUDIO_STATISTICS ");
                    query.append("SET STATS = 0, STATS_STRING = '").append(result).append("', ON_DATE = SYSDATE(), IS_ACTIVE = 'Y' ");
                    query.append("WHERE SRNO = ").append(srs.getString("SRNO"));
                    SqlUtil.persist(ALIASNAME, query.toString());
                }
            }
        }
        else
        {
            query.setLength(0);
            query.append("INSERT INTO FINSTUDIO_STATISTICS (GROUP_NAME, STATS_NAME, STATS,STATS_STRING, ON_DATE, IS_ACTIVE) VALUES(");
            query.append("'Web Service Generation', 'WSGEN Detail', 0, '");
            query.append(result);
            query.append("', SYSDATE(), 'Y')");
            SqlUtil.persist(ALIASNAME, query.toString());
        }
        return result;
    }

    private String getORATOSQL_Statistics() throws ClassNotFoundException, SQLException
    {
        StringBuilder query = new StringBuilder();
        query.append("SELECT COUNT(DISTINCT ITEM_NAME) FUNC_COUNT FROM ORACLETOMYSQL WHERE ITEM_TYPE = 'FUNCTION'");
        String funCount = SqlUtil.getString("finstudio_mysql", query.toString());
        query.setLength(0);
        query.append("SELECT COUNT(DISTINCT ITEM_NAME) PROC_COUNT FROM ORACLETOMYSQL WHERE ITEM_TYPE = 'PROCEDURE'");
        String procCount = SqlUtil.getString("finstudio_mysql", query.toString());
        query.setLength(0);
        query.append("SELECT COUNT(DISTINCT EMP_CODE) EMP_COUNT FROM ORACLETOMYSQL");
        String userCount = SqlUtil.getString("finstudio_mysql", query.toString());

        String result = "OracleToMysql is used for converting total " + procCount + " Procedure and " + funCount + " Functions by " + userCount + " distinct users.";

        //check if record exist update existing else insert new
        query.setLength(0);
        query.append("SELECT SRNO,GROUP_NAME,STATS_NAME FROM FINSTUDIO_STATISTICS WHERE GROUP_NAME = 'Oracle To Mysql'");
        SqlRowSet srs = SqlUtil.getRowSet(ALIASNAME, query.toString());
        if (srs != null && srs.next())
        {
            srs.beforeFirst();
            while (srs.next())
            {
                String strGrpName = srs.getString("GROUP_NAME");
                String strStatsName = srs.getString("STATS_NAME");
                if (strGrpName.equalsIgnoreCase("Oracle To Mysql") && strStatsName.equalsIgnoreCase("OracToMysql Detail"))
                {
                    query.setLength(0);
                    query.append("UPDATE FINSTUDIO_STATISTICS SET STATS = 0,STATS_STRING = '");
                    query.append(result);
                    query.append("', ON_DATE = SYSDATE(),IS_ACTIVE = 'Y' WHERE SRNO =");
                    query.append(srs.getString("SRNO"));
                    SqlUtil.persist(ALIASNAME, query.toString());
                }
            }
        }
        else
        {
            query = new StringBuilder();
            query.append("INSERT INTO FINSTUDIO_STATISTICS (GROUP_NAME,STATS_NAME,STATS,STATS_STRING,ON_DATE,IS_ACTIVE) VALUES(");
            query.append("'Oracle To Mysql','OracToMysql Detail',0,'");
            query.append(result);
            query.append("',SYSDATE(),'Y')");
            SqlUtil.persist(ALIASNAME, query.toString());
        }
        return result;
    }

    private int finFileTransferTotalReqExecuted() throws ClassNotFoundException, SQLException
    {
        Map map = new HashMap();
        String desc = "";
        String avgDesc = "";
        String avgPerDayDesc = "";
        int k = 0, cnt = 0;
        StringBuilder SQL = new StringBuilder();
        StringBuilder SQLCHK = new StringBuilder();
        StringBuilder SQLInsert = new StringBuilder();
        StringBuilder SQLUpdate = new StringBuilder();
        Map paramMap = new HashMap();
        SqlParameterSource param;
        SQL.setLength(0);
        SQL.append(" SELECT CONCAT('Total ',REQ_EXECUTED,' Fin File Transfer Requests are executed for ',DIST_PROJECTS,' projects by ',DIST_USERS,' total users. ') DESCCRIPTION, ");
        SQL.append(" CONCAT(TOTAL_FILES,' total files are transferred with an average of ',AVG_FILES,' per day in ',TOTAL_DAYS,' days. ') AVG_PER_DAY_DESC, ");
        SQL.append(" CONCAT('Fin File transfer executes average ',AVG_REQ,' requests per day.') AVG_DESCRIPTION ");
        SQL.append(" FROM ");
        SQL.append(" ( ");
        SQL.append(" SELECT ");
        SQL.append(" *,TOTAL_FILES/TOTAL_DAYS AVG_FILES,REQ_EXECUTED/TOTAL_DAYS AVG_REQ ");
        SQL.append(" FROM ");
        SQL.append(" ( ");
        SQL.append(" SELECT COUNT(DISTINCT(FTM.MASTER_PK)) REQ_EXECUTED, ");
        SQL.append(" COUNT(DISTINCT(EMPCODE)) DIST_USERS, ");
        SQL.append(" COUNT(DISTINCT(PROJECT)) DIST_PROJECTS, ");
        SQL.append(" COUNT(FILE_NAME) TOTAL_FILES, ");
        SQL.append(" COUNT(DISTINCT(DATE_FORMAT(ENTDATE,'%d-%m-%Y'))) TOTAL_DAYS ");
        SQL.append(" FROM finstudio.FILE_TRANSFER_MASTER FTM ");
        SQL.append(" INNER JOIN finstudio.FILE_TRANSFER_DETAIL FTD ON FTD.MASTER_PK=FTM.MASTER_PK ");
        SQL.append(" ) X ");
        SQL.append(" ) Y ");

        List list = SqlUtil.getList(ALIASNAME, SQL.toString());
        for (int i = 0; i < list.size(); i++)
        {
            map = (Map) list.get(i);
            desc = map.get("DESCCRIPTION").toString();
            avgPerDayDesc = map.get("AVG_PER_DAY_DESC").toString();
            avgDesc = map.get("AVG_DESCRIPTION").toString();
            SQLCHK.setLength(0);
            String grpName = "Fin File Deployment";
            String statsName = "Fin File Deployment Detail1";
            String statsNameAvg = "Fin File Deployment Detail2";
            String stasNamePerDayAvg = "Fin File Deployment Detail3";
            SQLCHK.append(" SELECT COUNT(*) FROM FINSTUDIO_STATISTICS ");
            SQLCHK.append(" WHERE GROUP_NAME LIKE :GRPNAME AND STATS_NAME LIKE :STATSNAME ");

            SQLInsert.setLength(0);
            SQLInsert.append(" INSERT INTO FINSTUDIO_STATISTICS (GROUP_NAME,STATS_NAME,STATS,STATS_STRING,ON_DATE,IS_ACTIVE) ");
            SQLInsert.append(" VALUES(:GRPNAME , :STATSNAME ,0, :DESC ,SYSDATE(),'Y'); ");

            SQLUpdate.setLength(0);
            SQLUpdate.append(" UPDATE FINSTUDIO_STATISTICS SET STATS_STRING = :DESC ,ON_DATE = SYSDATE() ");
            SQLUpdate.append(" WHERE GROUP_NAME LIKE :GRPNAME AND STATS_NAME LIKE :STATSNAME ");

            paramMap.clear();
            paramMap.put("GRPNAME", grpName);
            paramMap.put("STATSNAME", statsName);
            paramMap.put("DESC", desc);
            param = new MapSqlParameterSource(paramMap);
            cnt = SqlUtil.getInt(ALIASNAME, SQLCHK.toString(), param);

            if (cnt <= 0)
            {
                k = SqlUtil.persist(ALIASNAME, SQLInsert.toString(), param);
            }
            else
            {
                k = SqlUtil.persist(ALIASNAME, SQLUpdate.toString(), param);
            }
            paramMap.clear();
            paramMap.put("GRPNAME", grpName);
            paramMap.put("STATSNAME", statsNameAvg);
            paramMap.put("DESC", avgDesc);
            param = new MapSqlParameterSource(paramMap);

            cnt = SqlUtil.getInt(ALIASNAME, SQLCHK.toString(), param);
            if (cnt <= 0)
            {
                k = SqlUtil.persist(ALIASNAME, SQLInsert.toString(), param);
            }
            else
            {
                k = SqlUtil.persist(ALIASNAME, SQLUpdate.toString(), param);
            }
            paramMap.clear();
            paramMap.put("GRPNAME", grpName);
            paramMap.put("STATSNAME", stasNamePerDayAvg);
            paramMap.put("DESC", avgPerDayDesc);
            param = new MapSqlParameterSource(paramMap);
            cnt = SqlUtil.getInt(ALIASNAME, SQLCHK.toString(), param);
            if (cnt <= 0)
            {
                k = SqlUtil.persist(ALIASNAME, SQLInsert.toString(), param);
            }
            else
            {
                k = SqlUtil.persist(ALIASNAME, SQLUpdate.toString(), param);
            }
        }
        return k;
    }

    private int finFileTransferTop3Users() throws ClassNotFoundException, SQLException
    {
        StringBuilder SQL = new StringBuilder();
        SQL.setLength(0);
        SQL.append(" SELECT COUNT(EMPCODE) CNT,EMPCODE,EMP_NAME FROM ");
        SQL.append(" finstudio.FILE_TRANSFER_MASTER ");
        SQL.append(" INNER JOIN NJHR.EMP_MAST EMP ON EMP.EMP_CODE=EMPCODE ");
        SQL.append(" GROUP BY EMPCODE,EMP_NAME ");
        SQL.append(" ORDER BY CNT DESC ");
        SQL.append(" LIMIT 3 ");

        List list = SqlUtil.getList(ALIASNAME, SQL.toString());
        String strTopUser = "";
        Map map = new HashMap();
        for (int i = 0; i < list.size(); i++)
        {
            map = (Map) list.get(i);
            if (i == 0)
            {
                strTopUser = "Top 3 users of Fin File Transfer Module are : " + map.get("EMP_NAME").toString();
            }
            else
            {
                strTopUser = strTopUser + "," + map.get("EMP_NAME").toString();
            }
        }
        SQL.setLength(0);
        String grpName = "Fin File Deployment";
        String statsName = "Fin File Deployment Detail4";
        SQL.append(" SELECT COUNT(*) FROM FINSTUDIO_STATISTICS ");
        SQL.append(" WHERE GROUP_NAME LIKE '" + grpName + "' AND STATS_NAME LIKE '" + statsName + "' ");
        int cnt = SqlUtil.getInt(ALIASNAME, SQL.toString());
        if (cnt <= 0)
        {
            SQL.setLength(0);
            SQL.append(" INSERT INTO FINSTUDIO_STATISTICS (GROUP_NAME,STATS_NAME,STATS,STATS_STRING,ON_DATE,IS_ACTIVE) ");
            SQL.append(" VALUES('" + grpName + "','" + statsName + "',0,'" + strTopUser + "',SYSDATE(),'Y'); ");
        }
        else
        {
            SQL.setLength(0);
            SQL.append(" UPDATE FINSTUDIO_STATISTICS SET STATS_STRING='" + strTopUser + "',ON_DATE = SYSDATE() ");
            SQL.append(" WHERE GROUP_NAME LIKE '" + grpName + "' AND STATS_NAME LIKE '" + statsName + "' ");
        }
        return SqlUtil.persist(ALIASNAME, SQL.toString());
    }

    private int finFileTransferTop3Projects() throws ClassNotFoundException, SQLException
    {
        StringBuilder SQL = new StringBuilder();
        SQL.append(" SELECT COUNT(FILE_NAME) TOTAL_FILES,PROJECT,PRJ_NAME,DOMAIN_NAME ");
        SQL.append(" FROM finstudio.FILE_TRANSFER_MASTER FTM ");
        SQL.append(" INNER JOIN finstudio.FILE_TRANSFER_DETAIL FTD ON FTD.MASTER_PK=FTM.MASTER_PK ");
        SQL.append(" left join WFM.PROJECT_MST PM ON PM.PRJ_ID=FTM.PROJECT ");
        SQL.append(" GROUP BY PROJECT,PRJ_NAME,DOMAIN_NAME ");
        SQL.append(" ORDER BY TOTAL_FILES DESC ");
        SQL.append(" LIMIT 3 ");

        List list = SqlUtil.getList(ALIASNAME, SQL.toString());
        String strTopPrj = "";
        Map map = new HashMap();
        for (int i = 0; i < list.size(); i++)
        {
            map = (Map) list.get(i);
            if (i == 0)
            {
                strTopPrj = "Top 3 Projects using Fin File Transfer deployment are : " + map.get("PRJ_NAME").toString();
            }
            else
            {
                strTopPrj = strTopPrj + "," + map.get("PRJ_NAME").toString();
            }
        }
        SQL.setLength(0);
        String grpName = "Fin File Deployment";
        String statsName = "Fin File Deployment Detail5";
        SQL.append(" SELECT COUNT(*) FROM FINSTUDIO_STATISTICS ");
        SQL.append(" WHERE GROUP_NAME LIKE '" + grpName + "' AND STATS_NAME LIKE '" + statsName + "' ");
        int cnt = SqlUtil.getInt(ALIASNAME, SQL.toString());
        if (cnt <= 0)
        {
            SQL.setLength(0);
            SQL.append(" INSERT INTO FINSTUDIO_STATISTICS (GROUP_NAME,STATS_NAME,STATS,STATS_STRING,ON_DATE,IS_ACTIVE) ");
            SQL.append(" VALUES('" + grpName + "','" + statsName + "',0,'" + strTopPrj + "',SYSDATE(),'Y'); ");
        }
        else
        {
            SQL.setLength(0);
            SQL.append(" UPDATE FINSTUDIO_STATISTICS SET STATS_STRING='" + strTopPrj + "',ON_DATE = SYSDATE() ");
            SQL.append(" WHERE GROUP_NAME LIKE '" + grpName + "' AND STATS_NAME LIKE '" + statsName + "' ");
        }
        return SqlUtil.persist(ALIASNAME, SQL.toString());
    }

    private int getDRETotalReqExecuted() throws ClassNotFoundException, SQLException
    {
        Map map = null;
        String desc = "";
        String avgDesc = "";
        String avgPerDayDesc = "";
        int k = 0, cnt = 0;
        StringBuilder SQL = new StringBuilder();
        StringBuilder SQLCHK = new StringBuilder();
        StringBuilder SQLInsert = new StringBuilder();
        StringBuilder SQLUpdate = new StringBuilder();
        Map paramMap = new HashMap();
        SqlParameterSource param;
        SQL.setLength(0);
        SQL.append("SELECT CONCAT('Total ',REQ_EXECUTED,' Data Requests are executed for ',DIST_PROJECTS,' projects by ',DIST_USERS,' total users. ') DESCCRIPTION, ");
        SQL.append("CONCAT('Data Request Executor executes average ',AVG_REQ,' requests per day.') AVG_DESCRIPTION, ");
        SQL.append("CONCAT(TOTAL_QUERIES,' total queries are executed with an average of ',AVG_QUERIES,' per day in ',TOTAL_DAYS,' days. ') AVG_PER_DAY_DESC ");
        SQL.append("FROM ");
        SQL.append("( ");
        SQL.append("SELECT ");
        SQL.append("*, TOTAL_QUERIES/TOTAL_DAYS AVG_QUERIES, REQ_EXECUTED/TOTAL_DAYS AVG_REQ ");
        SQL.append("FROM ");
        SQL.append("( ");
        SQL.append("SELECT COUNT(DISTINCT(DRM.DATA_REQ_ID)) REQ_EXECUTED, ");
        SQL.append("COUNT(DISTINCT(ENTRY_BY)) DIST_USERS, ");
        SQL.append("COUNT(DISTINCT(PROJ_ID)) DIST_PROJECTS, ");
        SQL.append("COUNT(QUERY_ID) TOTAL_QUERIES, ");
        SQL.append("COUNT(DISTINCT(DATE_FORMAT(ENTRY_DATE,'%d-%m-%Y'))) TOTAL_DAYS ");
        SQL.append("FROM finstudio.DATA_REQUEST_MASTER DRM ");
        SQL.append("INNER JOIN finstudio.DATA_REQUEST_BATCH DRB ON DRB.DATA_REQ_ID = DRM.DATA_REQ_ID ");
        SQL.append("INNER JOIN finstudio.DATA_REQUEST_QUERY DRQ ON DRQ.BATCH_ID = DRB.BATCH_ID ");
        SQL.append(") X ");
        SQL.append(") Y");

        List list = SqlUtil.getList(ALIASNAME, SQL.toString());
        for (int i = 0; i < list.size(); i++)
        {
            map = (Map) list.get(i);
            desc = map.get("DESCCRIPTION").toString();
            avgDesc = map.get("AVG_DESCRIPTION").toString();
            avgPerDayDesc = map.get("AVG_PER_DAY_DESC").toString();
            SQLCHK.setLength(0);
            String grpName = "Data Request Executor";
            String statsName = "Data Request Executor Detail1";
            String statsNameAvg = "Data Request Executor Detail2";
            String stasNamePerDayAvg = "Data Request Executor Detail5";
            SQLCHK.append(" SELECT COUNT(*) FROM FINSTUDIO_STATISTICS ");
            SQLCHK.append("WHERE GROUP_NAME LIKE :GRPNAME AND STATS_NAME LIKE :STATSNAME");

            SQLInsert.setLength(0);
            SQLInsert.append("INSERT INTO FINSTUDIO_STATISTICS (GROUP_NAME, STATS_NAME, STATS, STATS_STRING, ON_DATE, IS_ACTIVE) ");
            SQLInsert.append("VALUES(:GRPNAME, :STATSNAME, 0, :DESC, SYSDATE(), 'Y')");

            SQLUpdate.setLength(0);
            SQLUpdate.append("UPDATE FINSTUDIO_STATISTICS SET STATS_STRING = :DESC, ON_DATE = SYSDATE() ");
            SQLUpdate.append("WHERE GROUP_NAME LIKE :GRPNAME AND STATS_NAME LIKE :STATSNAME");

            paramMap.clear();
            paramMap.put("GRPNAME", grpName);
            paramMap.put("STATSNAME", statsName);
            paramMap.put("DESC", desc);
            param = new MapSqlParameterSource(paramMap);
            cnt = SqlUtil.getInt(ALIASNAME, SQLCHK.toString(), param);
            if (cnt <= 0)
            {
                k = SqlUtil.persist(ALIASNAME, SQLInsert.toString(), param);
            }
            else
            {
                k = SqlUtil.persist(ALIASNAME, SQLUpdate.toString(), param);
            }

            paramMap.clear();
            paramMap.put("GRPNAME", grpName);
            paramMap.put("STATSNAME", statsNameAvg);
            paramMap.put("DESC", avgDesc);
            param = new MapSqlParameterSource(paramMap);
            cnt = SqlUtil.getInt(ALIASNAME, SQLCHK.toString(), param);
            if (cnt <= 0)
            {
                k = SqlUtil.persist(ALIASNAME, SQLInsert.toString(), param);
            }
            else
            {
                k = SqlUtil.persist(ALIASNAME, SQLUpdate.toString(), param);
            }

            paramMap.clear();
            paramMap.put("GRPNAME", grpName);
            paramMap.put("STATSNAME", stasNamePerDayAvg);
            paramMap.put("DESC", avgPerDayDesc);
            param = new MapSqlParameterSource(paramMap);
            cnt = SqlUtil.getInt(ALIASNAME, SQLCHK.toString(), param);
            if (cnt <= 0)
            {
                k = SqlUtil.persist(ALIASNAME, SQLInsert.toString(), param);
            }
            else
            {
                k = SqlUtil.persist(ALIASNAME, SQLUpdate.toString(), param);
            }
        }
        return k;
    }

    private int getDRETop3Users() throws ClassNotFoundException, SQLException
    {
        StringBuilder SQL = new StringBuilder();
        SQL.setLength(0);
        SQL.append("SELECT COUNT(ENTRY_BY) CNT, ENTRY_BY, EMP_NAME ");
        SQL.append("FROM finstudio.DATA_REQUEST_MASTER ");
        SQL.append("INNER JOIN NJHR.EMP_MAST EMP ON EMP.EMP_CODE = ENTRY_BY ");
        SQL.append("GROUP BY ENTRY_BY, EMP_NAME ");
        SQL.append("ORDER BY CNT DESC ");
        SQL.append("LIMIT 3");

        List list = SqlUtil.getList(ALIASNAME, SQL.toString());
        String strTopUser = "";
        Map map = null;
        for (int i = 0; i < list.size(); i++)
        {
            map = (Map) list.get(i);
            if (i == 0)
            {
                strTopUser = "Top 3 users of Data Request Executor Module are : " + map.get("EMP_NAME").toString();
            }
            else
            {
                strTopUser = strTopUser + "," + map.get("EMP_NAME").toString();
            }
        }
        SQL.setLength(0);
        String grpName = "Data Request Executor";
        String statsName = "Data Request Executor Detail3";
        SQL.append("SELECT COUNT(*) FROM FINSTUDIO_STATISTICS ");
        SQL.append("WHERE GROUP_NAME LIKE '").append(grpName).append("' AND STATS_NAME LIKE '").append(statsName).append("'");
        int cnt = SqlUtil.getInt(ALIASNAME, SQL.toString());
        if (cnt <= 0)
        {
            SQL.setLength(0);
            SQL.append("INSERT INTO FINSTUDIO_STATISTICS (GROUP_NAME, STATS_NAME, STATS, STATS_STRING, ON_DATE, IS_ACTIVE) ");
            SQL.append("VALUES('").append(grpName).append("', '").append(statsName).append("', 0, '").append(strTopUser).append("', SYSDATE(), 'Y')");
        }
        else
        {
            SQL.setLength(0);
            SQL.append("UPDATE FINSTUDIO_STATISTICS SET STATS_STRING = '").append(strTopUser).append("', ON_DATE = SYSDATE() ");
            SQL.append("WHERE GROUP_NAME LIKE '").append(grpName).append("' AND STATS_NAME LIKE '").append(statsName).append("'");
        }
        return SqlUtil.persist(ALIASNAME, SQL.toString());
    }

    private int getDRETop3Projects() throws ClassNotFoundException, SQLException
    {
        StringBuilder SQL = new StringBuilder();
        SQL.append("SELECT COUNT(DATA_REQ_ID) TOTAL, PROJ_ID, PRJ_NAME, DOMAIN_NAME ");
        SQL.append("FROM finstudio.DATA_REQUEST_MASTER DRM ");
        SQL.append("LEFT JOIN WFM.PROJECT_MST PM ON PM.PRJ_ID = DRM.PROJ_ID ");
        SQL.append("GROUP BY PROJ_ID, PRJ_NAME, DOMAIN_NAME ");
        SQL.append("ORDER BY TOTAL DESC ");
        SQL.append("LIMIT 3");

        List list = SqlUtil.getList(ALIASNAME, SQL.toString());
        String strTopPrj = "";
        Map map = null;
        for (int i = 0; i < list.size(); i++)
        {
            map = (Map) list.get(i);
            if (i == 0)
            {
                strTopPrj = "Top 3 Projects using Data Request Executor deployment are : " + map.get("PRJ_NAME").toString();
            }
            else
            {
                strTopPrj = strTopPrj + "," + map.get("PRJ_NAME").toString();
            }
        }
        SQL.setLength(0);
        String grpName = "Data Request Executor";
        String statsName = "Data Request Executor Detail4";
        SQL.append("SELECT COUNT(*) FROM FINSTUDIO_STATISTICS ");
        SQL.append("WHERE GROUP_NAME LIKE '").append(grpName).append("' AND STATS_NAME LIKE '").append(statsName).append("'");
        int cnt = SqlUtil.getInt(ALIASNAME, SQL.toString());
        if (cnt <= 0)
        {
            SQL.setLength(0);
            SQL.append("INSERT INTO FINSTUDIO_STATISTICS (GROUP_NAME, STATS_NAME, STATS, STATS_STRING, ON_DATE, IS_ACTIVE) ");
            SQL.append("VALUES('").append(grpName).append("', '").append(statsName).append("', 0, '").append(strTopPrj).append("', SYSDATE(), 'Y')");
        }
        else
        {
            SQL.setLength(0);
            SQL.append("UPDATE FINSTUDIO_STATISTICS SET STATS_STRING = '").append(strTopPrj).append("', ON_DATE = SYSDATE() ");
            SQL.append("WHERE GROUP_NAME LIKE '").append(grpName).append("' AND STATS_NAME LIKE '").append(statsName).append("'");
        }
        return SqlUtil.persist(ALIASNAME, SQL.toString());
    }
}