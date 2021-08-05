/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.business.finstudio.model;

import com.finlogic.apps.finstudio.formbean.ConnAuditFormBean;
import com.finlogic.util.Logger;
import com.finlogic.util.persistence.SQLService;
import java.util.List;
import org.springframework.jdbc.support.rowset.SqlRowSet;

/**
 *
 * @author njuser
 */
public class ConnAuditDataManager
{

    SQLService sqlService = new SQLService();

    public SqlRowSet getDataPageWise(ConnAuditFormBean conn_audit_frmbn) throws Exception
    {

        String query = "select CONN_AUDIT_SUMMARY.STACK_TRACE,SERVER_NAME,TOMCAT_PATH,PROJECT_NAME,CONN_MECHANISM,DB_SERVER_TYPE,sum(NO_OF_LEAKS) AS TOTAL_LEAKS,sum(TOTAL_CONNECTION) TOTAL_CONNECTION,TOTAL_DAYS "
                + "from CONN_AUDIT_SUMMARY "
                + "LEFT join "
                + "( "
                + "select STACK_TRACE,COUNT(*) as TOTAL_DAYS from CONN_AUDIT_SUMMARY "
                + "where ON_DATE <= STR_TO_DATE('" + conn_audit_frmbn.getDatePicker() + "','%e-%c-%Y') "
                + "and REPORT_TYPE='Page Date Wise' "
                + "group by STACK_TRACE "
                + ") X2 on CONN_AUDIT_SUMMARY.STACK_TRACE=X2.STACK_TRACE "
                + "where date_format(ON_DATE,'%e-%c-%Y')='" + conn_audit_frmbn.getDatePicker() + "' "
                + "and REPORT_TYPE='Page Date Wise' "
                + "group by STACK_TRACE,SERVER_NAME,TOMCAT_PATH,PROJECT_NAME,DB_SERVER_TYPE,CONN_MECHANISM "
                + "having sum(NO_OF_LEAKS) > 0 or sum(TOTAL_CONNECTION) > 100 "
                + "order by sum(NO_OF_LEAKS) desc";

        return sqlService.getRowSet("finstudio_mysql", query);
    }

    public SqlRowSet getDataDateWise(ConnAuditFormBean conn_audit_frmbn) throws Exception
    {

        String query = "select SERVER_NAME,TOMCAT_PATH,PROJECT_NAME,DB_SERVER_TYPE,CONN_MECHANISM,SUM(NO_OF_LEAKS) AS TOTAL_LEAKS,sum(TOTAL_CONNECTION) TOTAL_CONNECTION "
                + "from CONN_AUDIT_SUMMARY "
                + "where date_format(ON_DATE,'%e-%c-%Y')='" + conn_audit_frmbn.getDatePicker() + "' "
                + "and REPORT_TYPE='Page Date Wise' "
                + "group by SERVER_NAME,TOMCAT_PATH,PROJECT_NAME,DB_SERVER_TYPE,CONN_MECHANISM "
                + "order by sum(NO_OF_LEAKS) desc";

        return sqlService.getRowSet("finstudio_mysql", query);

    }

    public SqlRowSet getDataTimeWise(ConnAuditFormBean conn_audit_frmbn) throws Exception
    {

        String query = "select HOUR_MIN,COUNT(*) NO_OF_CONN FROM "
                + "("
                + "SELECT date_format(ON_DATE,'%e-%c-%Y  %H:%i') HOUR_MIN FROM CONN_AUDIT_SUMMARY "
                + "WHERE date_format(ON_DATE,'%e-%c-%Y') = '" + conn_audit_frmbn.getDatePicker() + "' "
                + "AND REPORT_TYPE='Time Wise' "
                + ") X1 "
                + "group by HOUR_MIN "
                + "order by HOUR_MIN";

        return sqlService.getRowSet("finstudio_mysql", query);
    }

    public SqlRowSet getTimeData(String dateTime) throws Exception
    {

        String query = "SELECT '" + dateTime + "' HourMin,SERVER_NAME,TOMCAT_PATH,DB_SERVER_TYPE,CONN_MECHANISM,PROJECT_NAME,COUNT(*) CNT,STACK_TRACE FROM CONN_AUDIT_SUMMARY "
                + "where date_format(ON_DATE,'%e-%c-%Y  %H:%i') = '" + dateTime + "' "
                + "and REPORT_TYPE='Time Wise' "
                + "group by SERVER_NAME,TOMCAT_PATH,PROJECT_NAME,DB_SERVER_TYPE,CONN_MECHANISM";

        return sqlService.getRowSet("finstudio_mysql", query);
    }

    public SqlRowSet getTimeDiff(ConnAuditFormBean conn_audit_frmbn) throws Exception
    {

        String query = "select date_format(ON_DATE,'%e-%c-%Y  %H:%i') ON_DATE,SERVER_NAME,TOMCAT_PATH,PROJECT_NAME,DB_SERVER_TYPE,CONN_MECHANISM,TOTAL_CONNECTION,STACK_TRACE "
                + "from CONN_AUDIT_SUMMARY "
                + "where date_format(ON_DATE,'%e-%c-%Y')='" + conn_audit_frmbn.getDatePicker() + "' "
                + "and REPORT_TYPE='Open Close Time Diff' "
                + "order by TOTAL_CONNECTION desc"; 
        return sqlService.getRowSet("finstudio_mysql", query);
    }

    public List getProjectRows(String formDate) throws Exception
    {
        String query = "SELECT PROJECT_NAME,CONCAT('(',SUM(NO_OF_LEAKS),') ',PROJECT_NAME) PROJECT_LABEL FROM CONN_AUDIT_SUMMARY  "
                + "where REPORT_TYPE='Page Date Wise'  "
                + "AND DATEDIFF(str_to_date('" + formDate + "','%e-%c-%Y'),ON_DATE) <= 15  "
                + "and DATEDIFF(str_to_date('" + formDate + "','%e-%c-%Y'),ON_DATE) >= 0  "
                + "and PROJECT_NAME !='' "
                + "GROUP BY PROJECT_NAME "  
                + "having SUM(NO_OF_LEAKS) > 0 "  
                + "order by SUM(NO_OF_LEAKS) DESC "
                + "LIMIT 15";

        return sqlService.getList("finstudio_mysql", query);
    }

    public SqlRowSet getDateRows(String formDate) throws Exception
    {
        String query = "select DISTINCT date_format(ON_DATE,'%e-%c-%Y') ON_DATE1 "
                + "from CONN_AUDIT_SUMMARY "
                + "WHERE DATEDIFF(str_to_date('" + formDate + "','%e-%c-%Y'),ON_DATE)<=15 "
                + "and DATEDIFF(str_to_date('" + formDate + "','%e-%c-%Y'),ON_DATE) >= 0 "
                + "and REPORT_TYPE='Page Date Wise' "
                + "group by PROJECT_NAME,ON_DATE "
                + "ORDER BY ON_DATE ";

        return sqlService.getRowSet("finstudio_mysql", query);
    }

    public int getLeaksRows(String prjName, String formDate) throws Exception
    {
        String query = "select sum(NO_OF_LEAKS) " +
                "from CONN_AUDIT_SUMMARY " +
                "where PROJECT_NAME='" + prjName + "' " +
                "and REPORT_TYPE='Page Date Wise' " +
                "and date_format(ON_DATE,'%e-%c-%Y')='" + formDate + "' ";

        return sqlService.getInt("finstudio_mysql", query);
    }
}
