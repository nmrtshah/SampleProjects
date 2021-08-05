/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.business.finstudio.model;

import com.finlogic.apps.finstudio.formbean.ConnAuditFormBean;
import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class ConnAuditModel
{

    public SqlRowSet getData(ConnAuditFormBean conn_audit_frmbn) throws Exception
    {
        ConnAuditDataManager conauditDataManager = new ConnAuditDataManager();
        return conauditDataManager.getDataPageWise(conn_audit_frmbn);
    }

    public SqlRowSet getData1(ConnAuditFormBean conn_audit_frmbn) throws Exception
    {
        ConnAuditDataManager conauditDataManager = new ConnAuditDataManager();
        return conauditDataManager.getDataDateWise(conn_audit_frmbn);
    }

    public SqlRowSet getDataTimeWise(ConnAuditFormBean conn_audit_frmbn) throws Exception
    {
        ConnAuditDataManager conauditDataManager = new ConnAuditDataManager();
        return conauditDataManager.getDataTimeWise(conn_audit_frmbn);
    }

    public SqlRowSet getDataTime(String datetime) throws Exception
    {
        ConnAuditDataManager conauditDataManager = new ConnAuditDataManager();
        return conauditDataManager.getTimeData(datetime);
    }

    public SqlRowSet getDataTimeDiff(ConnAuditFormBean conn_audit_frmbn) throws Exception
    {
        ConnAuditDataManager conauditDataManager = new ConnAuditDataManager();
        return conauditDataManager.getTimeDiff(conn_audit_frmbn);
    }

    public void getChartData(List prjList,List dateList, List leakList, String formDate) throws Exception
    {
        ConnAuditDataManager conauditDataManager = new ConnAuditDataManager();
        //SqlRowSet prjRowSet = conauditDataManager.getProjectRows(formDate);
        SqlRowSet dateRowSet = conauditDataManager.getDateRows(formDate); 
        int leakCount = 0;

        while (dateRowSet.next())
        {
            dateList.add(dateRowSet.getString("ON_DATE1")); 
        }
        //prjRowSet.beforeFirst();

        for(int i=0;i<prjList.size();i++) 
        {
            List leakListP = new ArrayList();
            dateRowSet.beforeFirst();
            while (dateRowSet.next())
            {
                leakCount = conauditDataManager.getLeaksRows(prjList.get(i).toString(), dateRowSet.getString("ON_DATE1"));
                leakListP.add(leakCount);
            }
            leakList.add(leakListP);
        }
    }
    public List getProjects(String formDate) throws Exception
    {
        ConnAuditDataManager conauditDataManager = new ConnAuditDataManager();
        return conauditDataManager.getProjectRows(formDate);
    }
}

