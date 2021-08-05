/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.business.finstudio.findatareqexecutor;

/**
 *
 * @author Sonam Patel
 */
public class FinDataReqExecutorEntityBean
{

    //Common Fields Starts
    //DATA_REQUEST_MASTER Table's Fields Starts
    private int projId;
    private int reqId;
    private String purpose;
    private String executionType;
    private String entryBy;
    //DATA_REQUEST_MASTER Table's Fields Ends
    //DATA_REQUEST_BATCH Table's Fields Starts
    private String databaseID;
    private String syntaxVerifiedOn;
    //DATA_REQUEST_BATCH Table's Fields Ends
    //DATA_REQUEST_QUERY Table's Fields Starts
    private QueryEntityBean queryBean[];
    private String queryBackup[];
    private String queryLogTable[];
    //DATA_REQUEST_QUERY Table's Fields Ends
    //Common Fields Ends
    private String uSessionId;
    //View Filtering Fields Starts
    private String txtViewDataReqId;
    private String cmbViewProjId;
    private String cmbViewEmp;
    private String cmbViewReqId;
    private String txtViewPurpose;
    private String cmbViewStatus;
    private String dtFromDate;
    private String dtToDate;
    //View Filtering Fields Ends
    //for checking status of req
    private String currentStatus;

    public int getProjId()
    {
        return projId;
    }

    public void setProjId(final int projId)
    {
        this.projId = projId;
    }

    public int getReqId()
    {
        return reqId;
    }

    public void setReqId(final int reqId)
    {
        this.reqId = reqId;
    }

    public String getPurpose()
    {
        return purpose;
    }

    public void setPurpose(final String purpose)
    {
        this.purpose = purpose;
    }

    public String getExecutionType()
    {
        return executionType;
    }

    public void setExecutionType(final String executionType)
    {
        this.executionType = executionType;
    }

    public String getEntryBy()
    {
        return entryBy;
    }

    public void setEntryBy(final String entryBy)
    {
        this.entryBy = entryBy;
    }

    public String getDatabaseID()
    {
        return databaseID;
    }

    public void setDatabaseID(final String databaseID)
    {
        this.databaseID = databaseID;
    }

    public String getSyntaxVerifiedOn()
    {
        return syntaxVerifiedOn;
    }

    public void setSyntaxVerifiedOn(final String syntaxVerifiedOn)
    {
        this.syntaxVerifiedOn = syntaxVerifiedOn;
    }

    public QueryEntityBean[] getQueryBean()
    {
        return queryBean;
    }

    public void setQueryBean(final QueryEntityBean[] queryBean)
    {
        this.queryBean = queryBean;
    }

    public String[] getQueryBackup()
    {
        return queryBackup;
    }

    public void setQueryBackup(final String[] queryBackup)
    {
        this.queryBackup = queryBackup;
    }

    public String[] getQueryLogTable()
    {
        return queryLogTable;
    }

    public void setQueryLogTable(final String[] queryLogTable)
    {
        this.queryLogTable = queryLogTable;
    }

    public String getuSessionId()
    {
        return uSessionId;
    }

    public void setuSessionId(final String uSessionId)
    {
        this.uSessionId = uSessionId;
    }

    public String getTxtViewDataReqId()
    {
        return txtViewDataReqId;
    }

    public void setTxtViewDataReqId(final String txtViewDataReqId)
    {
        this.txtViewDataReqId = txtViewDataReqId;
    }

    public String getCmbViewProjId()
    {
        return cmbViewProjId;
    }

    public void setCmbViewProjId(final String cmbViewProjId)
    {
        this.cmbViewProjId = cmbViewProjId;
    }

    public String getCmbViewEmp()
    {
        return cmbViewEmp;
    }

    public void setCmbViewEmp(final String cmbViewEmp)
    {
        this.cmbViewEmp = cmbViewEmp;
    }

    public String getCmbViewReqId()
    {
        return cmbViewReqId;
    }

    public void setCmbViewReqId(final String cmbViewReqId)
    {
        this.cmbViewReqId = cmbViewReqId;
    }

    public String getTxtViewPurpose()
    {
        return txtViewPurpose;
    }

    public void setTxtViewPurpose(final String txtViewPurpose)
    {
        this.txtViewPurpose = txtViewPurpose;
    }

    public String getCmbViewStatus()
    {
        return cmbViewStatus;
    }

    public void setCmbViewStatus(final String cmbViewStatus)
    {
        this.cmbViewStatus = cmbViewStatus;
    }

    public String getDtFromDate()
    {
        return dtFromDate;
    }

    public void setDtFromDate(final String dtFromDate)
    {
        this.dtFromDate = dtFromDate;
    }

    public String getDtToDate()
    {
        return dtToDate;
    }

    public void setDtToDate(final String dtToDate)
    {
        this.dtToDate = dtToDate;
    }

    public String getCurrentStatus()
    {
        return currentStatus;
    }

    public void setCurrentStatus(final String currentStatus)
    {
        this.currentStatus = currentStatus;
    }
}
