package com.finlogic.business.finstudio.model;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import com.finlogic.util.persistence.SQLService;
import com.finlogic.apps.finstudio.formbean.ServerAccessAuditReportFormBean;
import com.finlogic.util.Logger;
import com.finlogic.util.persistence.SQLSchemaFactory;

public class ServerAccessAuditReportDataManager
{

    SQLService sqlService = new SQLService();
    String aliasName = new SQLSchemaFactory().getFinstudioAlias();

    public SqlRowSet getRequestURLWiseReport(ServerAccessAuditReportFormBean formBean) throws Exception
    {
        String query = "SELECT SERVER_NAME,SERVER_PATH,PROJECT_NAME,REQUEST_URL, "
                + "HTTPSTATUS,COUNT(1) TOTAL_REQUEST,SUM(BYTES_SENT) TOT_BYTES_SENT, " 
                + "ROUND(SUM(TOTAL_EXECUTION_TIME),4) TOT_EXECUTION_TIME  FROM SERVER_ACCESS_AUDIT "
                + "where date_format(REQUEST_TIME,'%d-%m-%Y') = '"+formBean.getAccessdate()+"'"
                + "group by SERVER_NAME,SERVER_PATH,PROJECT_NAME,REQUEST_URL,HTTPSTATUS "
                + "having TOTAL_REQUEST > 10 "
                + "or TOT_BYTES_SENT > 10000 "
                + "or TOT_EXECUTION_TIME > 1";

        return sqlService.getRowSet(aliasName, query);
    }

    public SqlRowSet getProjectWiseReport(ServerAccessAuditReportFormBean formBean) throws Exception
    {
        String query = "SELECT SERVER_NAME,SERVER_PATH,PROJECT_NAME,HTTPSTATUS, "
                + "COUNT(1) TOTAL_REQUEST,SUM(BYTES_SENT) TOT_BYTES_SENT, "
                + "ROUND(SUM(TOTAL_EXECUTION_TIME),4) TOT_EXECUTION_TIME  FROM SERVER_ACCESS_AUDIT "
                                + "where date_format(REQUEST_TIME,'%d-%m-%Y') = '"+formBean.getAccessdate()+"'"
                + "group by SERVER_NAME,SERVER_PATH,PROJECT_NAME,HTTPSTATUS";

        return sqlService.getRowSet(aliasName, query);
    }

    public SqlRowSet getRequestURLReport(ServerAccessAuditReportFormBean formBean) throws Exception
    {
        String query = "SELECT SERVER_NAME,SERVER_PATH,IPADDRESS,REQUEST_TIME, PROJECT_NAME,REQUEST_URL, "
                + " ROUND(TOTAL_EXECUTION_TIME,4) TOTAL_EXECUTION_TIME, "
                + " ROUND(TOTAL_EXECUTION_TIME/60,1) TOTAL_EXECUTION_MIN, "
                + " BYTES_SENT,BYTES_SENT/1024 KB_SENT,BYTES_SENT/(1024*1024) MB_SENT "
                + " FROM SERVER_ACCESS_AUDIT "
                + " where (date_format(REQUEST_TIME,'%d-%m-%Y') = '"+formBean.getAccessdate()+"') "
                + " AND (TOTAL_EXECUTION_TIME > 60 or BYTES_SENT > 10000 ) "
                + " order by TOTAL_EXECUTION_TIME desc "
                + " limit 100";
        return sqlService.getRowSet(aliasName, query);
    }

}
