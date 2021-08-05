/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.apps.finstudio.service;

import com.finlogic.util.JSONParser;
import com.finlogic.business.finstudio.model.ConnAuditModel;
import com.finlogic.apps.finstudio.formbean.ConnAuditFormBean;
import java.util.Calendar;
import java.util.List;
import jxl.write.DateTime;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class ConnAuditService
{

    public String getData(ConnAuditFormBean conn_audit_frmbn) throws Exception
    {

        ConnAuditModel conauditModel = new ConnAuditModel();
        String type = conn_audit_frmbn.getReportType();

        SqlRowSet srs = null;
        JSONParser jsonParser = new JSONParser();
        String jsonData = null;

        if (type.equals("Page Wise"))
        {
            srs = conauditModel.getData(conn_audit_frmbn);
            jsonData = jsonParser.Parse(srs, "STACK_TRACE");
        }
        else if (type.equals("Date Wise"))
        {
            srs = conauditModel.getData1(conn_audit_frmbn);
            jsonData = jsonParser.Parse(srs, "PROJECT_NAME");
        }
        else if (type.equals("Time Wise"))
        {
            srs = conauditModel.getDataTimeWise(conn_audit_frmbn);
            jsonData = jsonParser.Parse(srs, "HOUR_MIN");
        }
        else if (type.equals("Open Close Time Difference"))
        {
            srs = conauditModel.getDataTimeDiff(conn_audit_frmbn);
            jsonData = jsonParser.Parse(srs, "ON_DATE");
        }
        return jsonData;
    }

    public String getTimeData(String datetime1) throws Exception 
    {
        ConnAuditModel conauditModel = new ConnAuditModel(); 
        SqlRowSet srs = null;
        JSONParser jsonParser = new JSONParser();
        srs = conauditModel.getDataTime(datetime1);
        return jsonParser.Parse(srs, "PROJECT_NAME");
    }

    public List getProjects(String formDate) throws Exception
    {
        ConnAuditModel conauditModel = new ConnAuditModel();

        if (formDate == null)
        {
            Calendar c = Calendar.getInstance();
            formDate = c.get(Calendar.DAY_OF_MONTH) + "-" + (c.get(Calendar.MONTH)+1) + "-" + c.get(Calendar.YEAR);

        }
        return conauditModel.getProjects(formDate);
    }

    public void getChartData(List prjList, List dateList, List leakList, String formDate) throws Exception
    {
        ConnAuditModel conauditModel = new ConnAuditModel();
        conauditModel.getChartData(prjList, dateList, leakList, formDate);
    }
}
