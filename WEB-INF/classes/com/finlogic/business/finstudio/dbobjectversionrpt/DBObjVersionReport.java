/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.business.finstudio.dbobjectversionrpt;

import com.finlogic.apps.finstudio.dbobjectversionrpt.DBObjVersionRptFormBean;
import com.finlogic.util.persistence.SQLService;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

/**
 *
 * @author njuser
 */
public class DBObjVersionReport
{

    private static final String ALIASNAME = "finstudio_mysql";
    private final SQLService sqlService = new SQLService();

    public List getReport(DBObjVersionRptFormBean formBean) throws ClassNotFoundException, SQLException
    {
        StringBuilder SQL = new StringBuilder();

        SQL.append("  SELECT DRQ.QUERY_ID,OBJ_NAME ,DRM.DATA_REQ_ID,");
        SQL.append("  EMP.EMP_NAME REQUESTER,EMP1.EMP_NAME EXECUTOR,");
        SQL.append("  DATE_FORMAT(DRM.ENTRY_DATE,'%d-%m-%Y %h:%i %p') ENTRYDATE ,");
        SQL.append("  DATE_FORMAT(DRM.EXECUTION_DATE,'%d-%m-%Y %h:%i %p')EXECUTION_DATE,DRQ.PURPOSE,");
        SQL.append("  CONCAT('<a href='' javascript:void(0);'' onclick=''getDiff(',DRQ.QUERY_ID,");
        SQL.append("  ')''>Compare</a>') LINK  ");
        SQL.append("  FROM DATA_REQUEST_QUERY DRQ  INNER JOIN DATA_REQUEST_BATCH DRB ON DRQ.BATCH_ID=DRB.BATCH_ID  ");
        SQL.append("  INNER JOIN DATA_REQUEST_MASTER DRM ON DRM.DATA_REQ_ID = DRB.DATA_REQ_ID ");
        SQL.append("  LEFT JOIN NJHR.EMP_MAST EMP ON EMP.EMP_CODE=DRM.ENTRY_BY");
        SQL.append("  LEFT JOIN NJHR.EMP_MAST EMP1 ON EMP1.EMP_CODE=DRM.EXECUTED_BY");
        SQL.append("  WHERE OBJ_TYPE =:OBJ_TYPE AND OBJ_NAME=:OBJ_NAME AND DRM.EXECUTION_STATUS='Done' AND DRQ.PURPOSE ");
        SQL.append("  IN ('CREATE OR REPLACE','DROP','CREATE','ALTER') AND DRB.DATABASE_ID=:DATABASE_ID");
        SQL.append("  ORDER BY DRM.EXECUTION_DATE DESC");
        Map map = new HashMap();
        map.put("DATABASE_ID", formBean.getCmbDB());
        map.put("OBJ_NAME", formBean.getCmbObjName());
        map.put("OBJ_TYPE", formBean.getCmbObjType().substring(1));

        return sqlService.getList(ALIASNAME, SQL.toString(), new MapSqlParameterSource(map));
    }

    public List getDefList(String queryID) throws ClassNotFoundException, SQLException
    {
        StringBuilder SQL = new StringBuilder();

        SQL.append("  SELECT PURPOSE,OBJ_NAME, IFNULL(TRIM(OLD_QUERY_TEXT),'NA') OLD_QUERY_TEXT,IFNULL(QUERY_TEXT,'NA')QUERY_TEXT,IFNULL(NEW_QUERY_TEXT,'NA') ");
        SQL.append("  NEW_QUERY_TEXT FROM DATA_REQUEST_QUERY WHERE QUERY_ID=:QUERY_ID ");

        Map map = new HashMap();
        map.put("QUERY_ID", queryID);

        return sqlService.getList(ALIASNAME, SQL.toString(), new MapSqlParameterSource(map));
    }

    public List getobjName(String objName,String dbID) throws ClassNotFoundException, SQLException
    {
        StringBuilder SQL = new StringBuilder();

        SQL.append("  SELECT DISTINCT OBJ_NAME FROM DATA_REQUEST_QUERY DRQ");
        SQL.append("  INNER JOIN DATA_REQUEST_BATCH DRB ON DRB.BATCH_ID=DRQ.BATCH_ID");
        SQL.append("  WHERE OBJ_NAME LIKE :OBJ_NAME AND DRB.DATABASE_ID=:DATABASE_ID");
        
        Map map = new HashMap();
        map.put("OBJ_NAME", "%" + objName + "%");
        map.put("DATABASE_ID", dbID);

        return sqlService.getList(ALIASNAME, SQL.toString(), new MapSqlParameterSource(map));
    }
}
