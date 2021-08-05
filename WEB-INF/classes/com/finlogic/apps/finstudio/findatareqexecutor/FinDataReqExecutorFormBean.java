/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.apps.finstudio.findatareqexecutor;

/**
 *
 * @author Sonam Patel
 */
public class FinDataReqExecutorFormBean
{

    private static final String FILEUPLOAD_PATH = finpack.FinPack.getProperty("tomcat1_path") + "/webapps/finstudio/upload/";
    //Common Fields Starts
    private String cmbAddProjId;
    private String txtAddReqId;
    private String cmbAddReqId;
    private String txtAddPurpose;
    private String cmbAddExecType;
    private String empCode;
    private String cmbAddDatabase;
    private String rdoAddVerifyServer;
    private String verifyServer;
    private String rdoInput;
    private String txtAddQuery;
    private String queryFileName;
    private String devExeResult[];
    private String devExeStatus[];
    private String devExeStartTime[];
    private String devExeEndTime[];
    private String hdnUSessionId;
    private String[] hdnAddBackup;
    private String[] hdnAddLogTable;
    //Common Fields Ends
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
    //for current stutus of req. halt or submit
    private String currentStatus;

    public static String getFILEUPLOAD_PATH()
    {
        return FILEUPLOAD_PATH;
    }

    public String getCmbAddProjId()
    {
        return cmbAddProjId;
    }

    public void setCmbAddProjId(final String cmbAddProjId)
    {
        this.cmbAddProjId = cmbAddProjId;
    }

    public String getTxtAddReqId()
    {
        return txtAddReqId;
    }

    public void setTxtAddReqId(final String txtAddReqId)
    {
        this.txtAddReqId = txtAddReqId;
    }

    public String getCmbAddReqId()
    {
        return cmbAddReqId;
    }

    public void setCmbAddReqId(final String cmbAddReqId)
    {
        this.cmbAddReqId = cmbAddReqId;
    }

    public String getTxtAddPurpose()
    {
        return txtAddPurpose;
    }

    public void setTxtAddPurpose(final String txtAddPurpose)
    {
        this.txtAddPurpose = txtAddPurpose;
    }

    public String getCmbAddExecType()
    {
        return cmbAddExecType;
    }

    public void setCmbAddExecType(final String cmbAddExecType)
    {
        this.cmbAddExecType = cmbAddExecType;
    }

    public String getEmpCode()
    {
        return empCode;
    }

    public void setEmpCode(final String empCode)
    {
        this.empCode = empCode;
    }

    public String getCmbAddDatabase()
    {
        return cmbAddDatabase;
    }

    public void setCmbAddDatabase(final String cmbAddDatabase)
    {
        this.cmbAddDatabase = cmbAddDatabase;
    }

    public String getRdoAddVerifyServer()
    {
        return rdoAddVerifyServer;
    }

    public void setRdoAddVerifyServer(final String rdoAddVerifyServer)
    {
        this.rdoAddVerifyServer = rdoAddVerifyServer;
    }

    public String getVerifyServer()
    {
        return verifyServer;
    }

    public void setVerifyServer(final String verifyServer)
    {
        this.verifyServer = verifyServer;
    }

    public String getRdoInput()
    {
        return rdoInput;
    }

    public void setRdoInput(final String rdoInput)
    {
        this.rdoInput = rdoInput;
    }

    public String getTxtAddQuery()
    {
        return txtAddQuery;
    }

    public void setTxtAddQuery(final String txtAddQuery)
    {
        this.txtAddQuery = txtAddQuery;
    }

    public String getQueryFileName()
    {
        return queryFileName;
    }

    public void setQueryFileName(final String queryFileName)
    {
        this.queryFileName = queryFileName;
    }

    public String[] getDevExeResult()
    {
        return devExeResult;
    }

    public void setDevExeResult(final String[] devExeResult)
    {
        this.devExeResult = devExeResult;
    }

    public String[] getDevExeStatus()
    {
        return devExeStatus;
    }

    public void setDevExeStatus(final String[] devExeStatus)
    {
        this.devExeStatus = devExeStatus;
    }

    public String[] getDevExeStartTime()
    {
        return devExeStartTime;
    }

    public void setDevExeStartTime(final String[] devExeStartTime)
    {
        this.devExeStartTime = devExeStartTime;
    }

    public String[] getDevExeEndTime()
    {
        return devExeEndTime;
    }

    public void setDevExeEndTime(final String[] devExeEndTime)
    {
        this.devExeEndTime = devExeEndTime;
    }

    public String getHdnUSessionId()
    {
        return hdnUSessionId;
    }

    public void setHdnUSessionId(final String hdnUSessionId)
    {
        this.hdnUSessionId = hdnUSessionId;
    }

    public String[] getHdnAddBackup()
    {
        return hdnAddBackup;
    }

    public void setHdnAddBackup(final String[] hdnAddBackup)
    {
        this.hdnAddBackup = hdnAddBackup;
    }

    public String[] getHdnAddLogTable()
    {
        return hdnAddLogTable;
    }

    public void setHdnAddLogTable(final String[] hdnAddLogTable)
    {
        this.hdnAddLogTable = hdnAddLogTable;
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
