/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.business.finstudio.blockmodules;

import com.finlogic.util.persistence.SQLTranUtility;
import com.finlogic.util.persistence.SQLUtility;
import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

/**
 *
 * @author Jigna Patel
 */
public class BlockModulesDataManager 
{
    
    private static final String ALIASNAME = "finstudio_dbaudit_common";
    private final SQLUtility sqlUtility = new SQLUtility();
    
    public int insertBlockEntry(final BlockModulesEntityBean bmeb) throws Exception 
    {
        StringBuilder sb = new StringBuilder();
        sb.append(" INSERT INTO BLOCK_MODULE_DETAIL ");
        sb.append(" (USER_NAME,SERVER_NAME,URI,PARAMETER_NAMES,PARAMETER_VALUES,SESSION_VAR_NAMES,SESSION_VAR_VALUES, ");
        sb.append(" ACCESS_LOG,ALLOW_IP,FROM_DATE,TO_DATE,MESSAGE,STATUS,REMARKS,ENTRY_DATE,LAST_UPDATED_DATE) VALUES( ");
        sb.append(" :userName,:serverName,:uri,:paramNames,:paramValues,:sesVarNames,:sesVarValues, ");
        sb.append(" :accessLog,:allowIpAddress, ");
        // if Block Period is set "urgently" then no need to set from date and to date
        if(bmeb.getFromDateStr() != null && !bmeb.getFromDateStr().isEmpty()
           && bmeb.getToDateStr() != null && !bmeb.getToDateStr().isEmpty())
        {
            sb.append(" STR_TO_DATE('").append(bmeb.getFromDateStr()).append("','%Y-%m-%d %H:%i:%s'), ");
            sb.append(" STR_TO_DATE('").append(bmeb.getToDateStr()).append("','%Y-%m-%d %H:%i:%s'), ");
        }
        else
        {
            sb.append("NULL, NULL,");
        }
        sb.append(" :message,'B-PENDING',:remarks,SYSDATE(),SYSDATE() ");
        sb.append(" ) ");
        int result = sqlUtility.persist(ALIASNAME, sb.toString(), new BeanPropertySqlParameterSource(bmeb));
        return result;
    }
    
    public List getReportTabData(final BlockModulesEntityBean bmeb) throws Exception 
    {
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT ID,CONCAT('<b>User Name - </b>',USER_NAME,'\\n','<b>Server Name - </b>',SERVER_NAME,'\\n','<b>URI - </b>',URI,'\\n', ");
        sb.append("                  '<b>Parameter Names - </b>',PARAMETER_NAMES,'\\n','<b>Parameter Values - </b>',PARAMETER_VALUES,'\\n', ");
        sb.append("                  '<b>Session Variable Names - </b>',SESSION_VAR_NAMES,'\\n','<b>Session Variable Values - </b>',SESSION_VAR_VALUES,'\\n', ");
        sb.append("                  '<b>Access Log - </b>',ACCESS_LOG,'\\n','<b>Allow IP - </b>',ALLOW_IP,'\\n', ");
        sb.append("                  CASE WHEN (FROM_DATE IS NULL AND TO_DATE IS NULL) THEN '' ELSE ");
        sb.append("                  CONCAT('<b>Block Period - </b>',DATE_FORMAT(FROM_DATE,'%d/%m %h:%i %p'),' to ',DATE_FORMAT(TO_DATE,'%d/%m %h:%i %p'),'\\n') END, ");
        sb.append("                  '<b>Block Message - </b>',MESSAGE), ");
        sb.append(" DATE_FORMAT(ENTRY_DATE,'%d-%b-%Y %H:%i') AS ENTRY_DATE, ");
        sb.append(" DATE_FORMAT(LAST_UPDATED_DATE,'%d-%b-%Y %H:%i') AS LAST_UPDATED_DATE, ");
        sb.append(" CASE WHEN STATUS = 'B-PENDING' THEN CONCAT('Block (Pending)',CASE WHEN (REMARKS IS NOT NULL AND REMARKS != '') THEN CONCAT(' - ',REMARKS) ELSE '' END) ");
        sb.append("      WHEN STATUS = 'B-AUTHORISED' THEN CONCAT('Block (Authorized)',CASE WHEN (REMARKS IS NOT NULL AND REMARKS != '') THEN CONCAT(' - ',REMARKS) ELSE '' END) ");
        sb.append("      WHEN STATUS = 'B-CONFIGURED' THEN CONCAT('Block (Configured)',CASE WHEN (REMARKS IS NOT NULL AND REMARKS != '') THEN CONCAT(' - ',REMARKS) ELSE '' END) ");
        sb.append("      WHEN STATUS = 'B-REJECTED' THEN CONCAT('Block (Rejected)',CASE WHEN (REMARKS IS NOT NULL AND REMARKS != '') THEN CONCAT(' - ',REMARKS) ELSE '' END) ");
        sb.append("      WHEN STATUS = 'UB-PENDING' THEN CONCAT('Un-Block (Pending)',CASE WHEN (REMARKS IS NOT NULL AND REMARKS != '') THEN CONCAT(' - ',REMARKS) ELSE '' END) ");
        sb.append("      WHEN STATUS = 'UB-AUTHORISED' THEN CONCAT('Un-Block (Authorized)',CASE WHEN (REMARKS IS NOT NULL AND REMARKS != '') THEN CONCAT(' - ',REMARKS) ELSE '' END) ");
        sb.append("      WHEN STATUS = 'UB-CONFIGURED' THEN CONCAT('Un-Block (Configured)',CASE WHEN (REMARKS IS NOT NULL AND REMARKS != '') THEN CONCAT(' - ',REMARKS) ELSE '' END) ");
        sb.append("      WHEN STATUS = 'UB-REJECTED' THEN CONCAT('Un-Block (Rejected)',CASE WHEN (REMARKS IS NOT NULL AND REMARKS != '') THEN CONCAT(' - ',REMARKS) ELSE '' END) ");
        sb.append(" END AS STATUS, ");
        sb.append(" IF(STATUS='B-CONFIGURED',CONCAT('<input type=button class=button value=Un-Block onclick=javascript:updateReportTabEntry(',ID,'); >'),'-') AS UNBLOCK_BTN ");
        sb.append(" FROM BLOCK_MODULE_DETAIL BMD ");
        
        sb.append(" WHERE 1 = 1 ");
        if(bmeb.getServerName() != null && !bmeb.getServerName().isEmpty() && !bmeb.getServerName().equalsIgnoreCase("-1"))
        {
            sb.append(" AND SERVER_NAME = :serverName ");
        }
        if(bmeb.getUserName() != null && !bmeb.getUserName().isEmpty() && !bmeb.getUserName().equalsIgnoreCase("-1"))
        {
            sb.append(" AND USER_NAME = :userName ");
        }
        if(bmeb.getBlockAction() != null && !bmeb.getBlockAction().isEmpty() && bmeb.getBlockAction().equalsIgnoreCase("actnBlock"))
        {
            sb.append(" AND STATUS LIKE 'B-%' ");
        }
        if(bmeb.getBlockAction() != null && !bmeb.getBlockAction().isEmpty() && bmeb.getBlockAction().equalsIgnoreCase("actnUnblock"))
        {
            sb.append(" AND STATUS LIKE 'UB-%' ");
        }
        if(bmeb.getBlockStatus() != null && !bmeb.getBlockStatus().isEmpty() && !bmeb.getBlockStatus().equalsIgnoreCase("-1"))
        {
            switch(bmeb.getBlockStatus())
            {
                case "sts_pen":
                    sb.append(" AND STATUS LIKE '%PENDING' ");
                    break;
                case "sts_auth":
                    sb.append(" AND STATUS LIKE '%AUTHORISED' ");
                    break;
                case "sts_config":
                    sb.append(" AND STATUS LIKE '%CONFIGURED' ");
                    break;
                case "sts_rej":
                    sb.append(" AND STATUS LIKE '%REJECTED' ");
                    break;
            }
        }
        if(bmeb.getFilterFromDate() != null && !bmeb.getFilterFromDate().isEmpty()
           && bmeb.getFilterToDate() != null && !bmeb.getFilterToDate().isEmpty())
        {
            sb.append("AND STR_TO_DATE(DATE_FORMAT(ENTRY_DATE,'%d-%m-%Y'),'%d-%m-%Y') BETWEEN STR_TO_DATE(:filterFromDate,'%d-%m-%Y') AND STR_TO_DATE(:filterToDate,'%d-%m-%Y') ");
        }
        
        sb.append(" ORDER BY BMD.LAST_UPDATED_DATE DESC ");
        sb.append(" LIMIT 30 ");
        return sqlUtility.getList(ALIASNAME, sb.toString(), new BeanPropertySqlParameterSource(bmeb));
    }
    
    public List getAuthoriseTabData(final BlockModulesEntityBean bmeb) throws Exception 
    {
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT ID,CONCAT('<b>User Name - </b>',USER_NAME,'\\n','<b>Server Name - </b>',SERVER_NAME,'\\n','<b>URI - </b>',URI,'\\n', ");
        sb.append("                  '<b>Parameter Names - </b>',PARAMETER_NAMES,'\\n','<b>Parameter Values - </b>',PARAMETER_VALUES,'\\n', ");
        sb.append("                  '<b>Session Variable Names - </b>',SESSION_VAR_NAMES,'\\n','<b>Session Variable Values - </b>',SESSION_VAR_VALUES,'\\n', ");
        sb.append("                  '<b>Access Log - </b>',ACCESS_LOG,'\\n','<b>Allow IP - </b>',ALLOW_IP,'\\n', ");
        sb.append("                  CASE WHEN (FROM_DATE IS NULL AND TO_DATE IS NULL) THEN '' ELSE ");
        sb.append("                  CONCAT('<b>Block Period - </b>',DATE_FORMAT(FROM_DATE,'%d/%m %h:%i %p'),' to ',DATE_FORMAT(TO_DATE,'%d/%m %h:%i %p'),'\\n') END, ");
        sb.append("                  '<b>Block Message - </b>',MESSAGE), ");
        sb.append(" CASE WHEN STATUS = 'B-PENDING' THEN 'Block (Pending)' ");
        sb.append("      WHEN STATUS = 'B-AUTHORISED' THEN 'Block (Authorized)' ");
        sb.append("      WHEN STATUS = 'B-CONFIGURED' THEN 'Block (Configured)' ");
        sb.append("      WHEN STATUS = 'B-REJECTED' THEN 'Block (Rejected)' ");
        sb.append("      WHEN STATUS = 'UB-PENDING' THEN 'Un-Block (Pending)' ");
        sb.append("      WHEN STATUS = 'UB-AUTHORISED' THEN 'Un-Block (Authorized)' ");
        sb.append("      WHEN STATUS = 'UB-CONFIGURED' THEN 'Un-Block (Configured)' ");
        sb.append("      WHEN STATUS = 'UB-REJECTED' THEN 'Un-Block (Rejected)' ");
        sb.append(" END AS STATUS, ");
        sb.append(" CONCAT('<textarea rows=5 style=width:200px  id=''','rem_',ID,''' >',IFNULL(REMARKS,''),'</textarea>') AS REMARKS, ");
        sb.append(" CONCAT('<input type=button class=button value=Authorize onclick=javascript:updateAuthoriseTabEntry(\\'authorised\\',',ID,'); >', ");
        sb.append("        '&nbsp;&nbsp;<input type=button class=button value=Reject onclick=javascript:updateAuthoriseTabEntry(\\'rejected\\',',ID,'); >') AS SELECT_CHK ");
        sb.append(" FROM BLOCK_MODULE_DETAIL BMD ");
        sb.append(" WHERE STATUS IN ('B-PENDING','UB-PENDING') ");
        sb.append(" ORDER BY BMD.LAST_UPDATED_DATE DESC ");
        return sqlUtility.getList(ALIASNAME, sb.toString());
    }
    
    public int updateBlockEntry(final String unBlockedId, final SQLTranUtility tran) throws Exception 
    {
        int result = 0;
        StringBuilder sb = new StringBuilder();
        if(unBlockedId != null && !unBlockedId.equalsIgnoreCase(""))
        {
            sb.setLength(0);
            sb.append(" UPDATE BLOCK_MODULE_DETAIL ");
            sb.append(" SET STATUS = 'UB-PENDING', LAST_UPDATED_DATE = SYSDATE() ");
            sb.append(" WHERE ID =  :unBlockedId ");
            Map map = new HashMap();
            map.put("unBlockedId",unBlockedId);
            result = tran.persist(sb.toString(), new MapSqlParameterSource(map));
            tran.commitChanges();
        }
        return result;
    }
    
    public int updateAuthStatus(final String blockedId, final SQLTranUtility tran) throws Exception 
    {
        int result = 0;
        StringBuilder sb = new StringBuilder();
        if(blockedId != null && !blockedId.equalsIgnoreCase(""))
        {
            Map idMap = new HashMap();
            idMap.put("blockedId",blockedId);
            sb.setLength(0);
            sb.append(" SELECT SERVER_NAME,URI,PARAMETER_NAMES,PARAMETER_VALUES, ");
            sb.append("        SESSION_VAR_NAMES,SESSION_VAR_VALUES, ");
            sb.append("        ACCESS_LOG,ALLOW_IP,STATUS, ");
            sb.append("        IFNULL(CONCAT(DATE_FORMAT(FROM_DATE,'%i %H %d %m'),' *:', ");
            sb.append("               DATE_FORMAT(TO_DATE,'%i %H %d %m'),' * combined'),'') AS BLOCK_PERIOD, ");
            sb.append("        MESSAGE ");
            sb.append(" FROM BLOCK_MODULE_DETAIL ");
            sb.append(" WHERE ID = :blockedId ");
            String status = "";
            List resList = tran.getList(sb.toString(), new MapSqlParameterSource(idMap));
            Object resObj = resList.get(0);
            Map map = (Map) resObj;
            status = map.get("STATUS").toString();

            sb.setLength(0);
            sb.append(" UPDATE BLOCK_MODULE_DETAIL ");
            if(status.equalsIgnoreCase("B-PENDING"))
            {
                idMap.put("blockEntry",makeBlockXml(resList));
                sb.append(" SET STATUS = 'B-AUTHORISED', ");
                sb.append("     BLOCK_ENTRY = :blockEntry, ");
            }
            else if(status.equalsIgnoreCase("UB-PENDING"))
            {
                sb.append(" SET STATUS = 'UB-AUTHORISED', ");
            }
            sb.append(" LAST_UPDATED_DATE = SYSDATE() ");
            sb.append(" WHERE ID =  :blockedId ");
            result = tran.persist(sb.toString(), new MapSqlParameterSource(idMap));
            tran.commitChanges();
        }
        return result;
    }
    
    public int updateRejectStatus(final String blockedId, final String remarks, final SQLTranUtility tran) throws Exception 
    {
        int result = 0;
        StringBuilder sb = new StringBuilder();
        if(blockedId != null && !blockedId.equalsIgnoreCase(""))
        {
            String[] idArr = blockedId.split("~");
            String[] remarksArr = remarks.split("~");
            for(int i=0; i<idArr.length; i++)
            {
                Map map = new HashMap();
                String remarksStr = remarksArr[i];
                remarksStr = remarksStr.replaceAll("&", "and");
                map.put("blockedId",idArr[i]);
                map.put("remarks",remarksStr);
                sb.setLength(0);
                sb.append(" SELECT STATUS FROM BLOCK_MODULE_DETAIL WHERE ID = :blockedId ");
                String status = tran.getString(sb.toString(), new MapSqlParameterSource(map));
                
                sb.setLength(0);
                sb.append(" UPDATE BLOCK_MODULE_DETAIL ");
                if(status.equalsIgnoreCase("B-PENDING"))
                {
                    sb.append(" SET STATUS = 'B-REJECTED', ");
                }
                else if(status.equalsIgnoreCase("UB-PENDING"))
                {
                    sb.append(" SET STATUS = 'UB-REJECTED', ");
                }
                sb.append(" LAST_UPDATED_DATE = SYSDATE(),REMARKS = :remarks ");
                sb.append(" WHERE ID =  :blockedId ");
                result = tran.persist(sb.toString(), new MapSqlParameterSource(map));
                tran.commitChanges();
            }
        }
        return result;
    }
    
    private String makeBlockXml(List resList) throws Exception
    {
        StringBuilder sb = new StringBuilder();
        Object resObj = resList.get(0);
        Map map = (Map) resObj;
        String blockPeriod = map.get("BLOCK_PERIOD").toString();

        sb.append("<block>");
        sb.append("<host>NA</host>");
        sb.append("<uri>").append(map.get("URI").toString()).append("</uri>");
        sb.append("<paramNames>").append(map.get("PARAMETER_NAMES").toString()).append("</paramNames>");
        sb.append("<paramValues>").append(map.get("PARAMETER_VALUES").toString()).append("</paramValues>");
        sb.append("<sessionAttributeNames>").append(map.get("SESSION_VAR_NAMES").toString()).append("</sessionAttributeNames>");
        sb.append("<sessionAttributeValues>").append(map.get("SESSION_VAR_VALUES").toString()).append("</sessionAttributeValues>");
        sb.append("<accessFlag>").append(map.get("ACCESS_LOG").toString()).append("</accessFlag>");
        sb.append("<allowedIP>").append(map.get("ALLOW_IP").toString()).append("</allowedIP>");
        sb.append("<title>Blocked</title>");
        if(!blockPeriod.isEmpty())
        {
            sb.append("<blockPeriod>").append(blockPeriod).append("</blockPeriod>");
        }
        sb.append("<message>").append(map.get("MESSAGE").toString()).append("</message>");
        sb.append("</block>");
        
        return sb.toString();
    }
    
    public int setBlockEntry(final SQLTranUtility tran) throws Exception
    {
        int result = 0;
        try
        {
            File srcFile = new File("/tomcat/BlockUrl.xml");
            PrintWriter pw = new PrintWriter(srcFile, "UTF-8");
            
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT ID,BLOCK_ENTRY ");
            sb.append(" FROM BLOCK_MODULE_DETAIL ");
            sb.append(" WHERE STATUS = 'B-AUTHORISED' ");

            List entryList = tran.getList(sb.toString());
            for(Object entryObj:entryList)
            {
                Map map = (Map) entryObj;
                pw.print(map.get("BLOCK_ENTRY").toString());
            }
            pw.close();
            result = 1;
        }
        catch(Exception e)
        {
            result = 0;
            throw e;
        }
        return result;
    }
    
    public List getUserNameList() throws Exception
    {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT DISTINCT BMD.USER_NAME ");
        sb.append("FROM BLOCK_MODULE_DETAIL BMD ");
        sb.append("ORDER BY UPPER(BMD.USER_NAME) ");
        List rptList = sqlUtility.getList(ALIASNAME, sb.toString());
        return rptList;
    }
}