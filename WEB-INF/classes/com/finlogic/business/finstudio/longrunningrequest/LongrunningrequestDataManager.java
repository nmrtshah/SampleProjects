/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.business.finstudio.longrunningrequest;

import com.finlogic.util.persistence.SQLUtility;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
/**
 *
 * @author Jigna Patel
 */
public class LongrunningrequestDataManager
{
    //private static final String FINSTUDIOALIASNAME = "finstudio_dbaudit_common";
    private static final String FINSTUDIOALIASNAME = "finstudio_mysql";
    private static final String WFMALIASNAME = "wfm";
    private final SQLUtility sqlUtility = new SQLUtility();
    
    public List getProject() throws ClassNotFoundException, SQLException
    {
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT PRJ_ID, DOMAIN_NAME, PRJ_NAME FROM ");
        sb.append(" ( ");
        sb.append("     SELECT PRJ_ID, DOMAIN_NAME, PRJ_NAME FROM PROJECT_MST ");
        sb.append("     WHERE ISACTIVE = 'Y' ");
        sb.append("     UNION ");
        sb.append("     SELECT PDM.PRJ_ID, PDM.DOMAIN_NAME, PM.PRJ_NAME FROM PROJECT_DOMAIN_MST PDM ");
        sb.append("         INNER JOIN PROJECT_MST PM ON PM.PRJ_ID = PDM.PRJ_ID ");
        sb.append("     WHERE ISACTIVE = 'Y' ");
        sb.append(" ) X ");
        sb.append(" WHERE DOMAIN_NAME IS NOT NULL ");
        sb.append(" ORDER BY PRJ_NAME ");
        return sqlUtility.getList(WFMALIASNAME, sb.toString());
    }
    
    /*
    Server Name ::
    prodhoweb1.nj :: 192.168.3.82
    prodhoweb2.nj :: 192.168.3.84
    prodhoweb3.nj :: 192.168.3.85
    prodhoweb4.nj :: 192.168.3.49
    prodgroupemail.nj :: 192.168.3.132
    */
    private String getServerIp(String serverName)
    {
        String ipAddr;
        switch(serverName)
        {
            case "prodhoweb1.nj":
                ipAddr = "192.168.3.82";
                break;
            case "prodhoweb2.nj":
                ipAddr = "192.168.3.84";
                break;
            case "prodhoweb3.nj":
                ipAddr = "192.168.3.85";
                break;
            case "prodhoweb4.nj":
                ipAddr = "192.168.3.49";
                break;
            case "prodgroupemail.nj":
                ipAddr = "192.168.3.132";
                break;
            default:
                ipAddr = "";
                break;
        }
        return ipAddr;
    }
    
    private String getStatusName(String status)
    {
        String statusName;
        switch(status)
        {
            case "sts_pending":
                statusName = "PENDING";
                break;
            case "sts_assigned":
                statusName = "ASSIGNED";
                break;
            case "sts_solved":
                statusName = "SOLVED";
                break;
            case "sts_cancelled":
                statusName = "CANCELLED";
                break;
            case "sts_falsepositv":
                statusName = "FALSE_POSITIVE";
                break;
            default:
                statusName = "";
                break;
        }
        return statusName;
    }
    
    public List getViewList(Map param) throws ClassNotFoundException, SQLException
    {
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT SRNO, IFNULL(ASSIGNED_TO,'') ASSIGNED_TO,IFNULL(EM.EMP_NAME,' - ') EMP_NAME,VHOST,STATUS, REQUEST_URI NEW_URI, ");
        sb.append("        SINCE_HOURS,REQUEST_COUNT,DATE_FORMAT(LAST_UPDATED_DATE, \"%d-%b-%Y %h:%i %p\") LAST_UPDATED_DATE ");
        sb.append(" FROM LONG_RUNNING_REQUEST LRM ");
        sb.append("     LEFT JOIN WFM.EMP_MST EM ON EM.EMP_CODE = LRM.ASSIGNED_TO ");
        sb.append(" WHERE 1 = 1 ");
        if(!param.get("SERVERNAME").toString().equals(""))
        {
            sb.append(" AND VHOST LIKE '").append(getServerIp(param.get("SERVERNAME").toString())).append("%' ");
        }
        if(!param.get("PROJNAME").toString().equals(""))
        {
            sb.append(" AND REQUEST_URI LIKE '%").append(param.get("PROJNAME").toString()).append("%' ");
        }
        if(!param.get("STATUSNAME").toString().equals(""))
        {
            sb.append(" AND STATUS = '").append(getStatusName(param.get("STATUSNAME").toString())).append("'");
        }
        if(!param.get("ASSIGNTOCODE").toString().equals(""))
        {
            sb.append(" AND ASSIGNED_TO = :ASSIGNTOCODE ");
        }
        sb.append(" ORDER BY SINCE_HOURS DESC ");
        return sqlUtility.getList(FINSTUDIOALIASNAME, sb.toString(), new MapSqlParameterSource(param));
    }
    
    public int updateStatus(Map param) throws ClassNotFoundException, SQLException
    {
        StringBuilder sb = new StringBuilder();
        sb.append(" UPDATE LONG_RUNNING_REQUEST ");
        sb.append(" SET STATUS = '").append(getStatusName(param.get("STATUSNAME").toString())).append("',LAST_UPDATED_DATE = SYSDATE() ");
        if(!param.get("REMARKS").toString().equals(""))
        {
            sb.append(" ,REMARKS = :REMARKS ");
        }
        sb.append(" WHERE SRNO = :REQID ");
        return sqlUtility.persist(FINSTUDIOALIASNAME, sb.toString(), new MapSqlParameterSource(param));
    }
    
    public int updateAssignStatus(Map param) throws ClassNotFoundException, SQLException
    {
        StringBuilder sb = new StringBuilder();
        sb.append(" UPDATE LONG_RUNNING_REQUEST ");
        sb.append(" SET STATUS = '").append(getStatusName(param.get("STATUSNAME").toString())).append("',LAST_UPDATED_DATE = SYSDATE() ");
        if(!param.get("REMARKS").toString().equals(""))
        {
            sb.append(" ,REMARKS = :REMARKS ");
        }
        if(!param.get("ASSIGNTO").toString().equals(""))
        {
            sb.append(" ,ASSIGNED_TO = :ASSIGNTO ");
        }
        sb.append(" WHERE SRNO IN (").append(param.get("REQID").toString()).append(")");
        return sqlUtility.persist(FINSTUDIOALIASNAME, sb.toString(), new MapSqlParameterSource(param));
    }
    
    public List getAssignList() throws ClassNotFoundException, SQLException
    {
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT SRNO, IFNULL(REMARKS,' - ') REMARKS, SINCE_HOURS, STATUS, REQUEST_URI NEW_URI, ");
        sb.append("        REQUEST_COUNT,DATE_FORMAT(LAST_UPDATED_DATE, \"%d-%b-%Y %h:%i %p\") LAST_UPDATED_DATE ");
        sb.append(" FROM LONG_RUNNING_REQUEST ");
        sb.append(" WHERE STATUS IN ('PENDING','CANCELLED') ");
        sb.append(" ORDER BY SINCE_HOURS DESC ");
        return sqlUtility.getList(FINSTUDIOALIASNAME, sb.toString());
    }
    
    public List getAssignEmpList() throws ClassNotFoundException, SQLException
    {
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT EM.EMP_CODE,EM.EMP_NAME ");
        sb.append(" FROM RESOURCE_MST RM ");
        sb.append("     INNER JOIN EMP_MST EM ON EM.EMP_CODE = RM.MECODE ");
        sb.append(" WHERE RESOURCE_TYPE_ID = 13 AND EM.LEFTDATE IS NULL ");
        sb.append(" ORDER BY EM.EMP_NAME ");
        return sqlUtility.getList(WFMALIASNAME, sb.toString());
    }
    
    public String getLastUpdatedDate() throws ClassNotFoundException, SQLException
    {
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT DATE_FORMAT(ENTRY_DATE, \"%d-%b-%Y %h:%i %p\") ENTRY_DATE ");
        sb.append(" FROM LONG_RUNNING_REQUEST ");
        sb.append(" ORDER BY ENTRY_DATE DESC ");
        sb.append(" LIMIT 1 ");
        return sqlUtility.getString(FINSTUDIOALIASNAME, sb.toString());
    }
}