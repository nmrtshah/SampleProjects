package com.finlogic.apps.finstudio.finfiletransfer;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class FinfiletransferFormBean
{

    //Common Fields Starts
    private String hdnGenId;
    private String reqno;
    private String srcdest;
    private String purpose;
    private String filelist;
    private String filelistdlt;
    private String masterpkPrimeKey;
    //Common Fields Ends
    //Edit Filtering Fields Starts
    private String masterpk;
    //Edit Filtering Fields Ends
    //View Filtering Fields Starts
    private String empcode;
    private String project;
    private String viewreqno;
    private String viewpurpose;
    private String fromdate;
    private String todate;
    //View Filtering Fields Ends
    private String masterTask;
    private String reportName;
    private String rdoRptFormate;
    private String sidx;
    private int rows;
    private int page;
    private String filelistupld1;
    private String filelistupld2;
    private String filelistupld3;
    private String fileLoc1;
    private String fileLoc2;
    private String fileLoc3;
    private String reqnoTxtLike=null;
    private String lang;
    private List<Map<String, String>> l = new LinkedList<Map<String, String>>();
    private String txtReqId;
    private String txtFilename;
    private String cmbprojectName;
    private String cmbsrcdest;

    public String getCmbsrcdest()
    {
        return cmbsrcdest;
    }

    public void setCmbsrcdest(String cmbsrcdest)
    {
        this.cmbsrcdest = cmbsrcdest;
    }

    public String getCmbprojectName()
    {
        return cmbprojectName;
    }

    public void setCmbprojectName(String cmbprojectName)
    {
        this.cmbprojectName = cmbprojectName;
    }

    public String getTxtFilename()
    {
        return txtFilename;
    }

    public void setTxtFilename(String txtFilename)
    {
        this.txtFilename = txtFilename;
    }

    public String getLang()
    {
        return lang;
    }

    public void setLang(String lang)
    {
        this.lang = lang;
    }

    public String getReqnoTxtLike()
    {
        return reqnoTxtLike;
    }

    public void setReqnoTxtLike(String reqnoTxtLike)
    {
        this.reqnoTxtLike = reqnoTxtLike;
    }

    public String getFilelistupld1()
    {
        return filelistupld1;
    }

    public void setFilelistupld1(String filelistupld1)
    {
        this.filelistupld1 = filelistupld1;
    }

    public String getFilelistupld2()
    {
        return filelistupld2;
    }

    public void setFilelistupld2(String filelistupld2)
    {
        this.filelistupld2 = filelistupld2;
    }

    public String getFilelistupld3()
    {
        return filelistupld3;
    }

    public void setFilelistupld3(String filelistupld3)
    {
        this.filelistupld3 = filelistupld3;
    }

    public String getFileLoc1()
    {
        return fileLoc1;
    }

    public void setFileLoc1(String fileLoc1)
    {
        this.fileLoc1 = fileLoc1;
    }

    public String getFileLoc2()
    {
        return fileLoc2;
    }

    public void setFileLoc2(String fileLoc2)
    {
        this.fileLoc2 = fileLoc2;
    }

    public String getFileLoc3()
    {
        return fileLoc3;
    }

    public void setFileLoc3(String fileLoc3)
    {
        this.fileLoc3 = fileLoc3;
    }

    public String getFilelistdlt()
    {
        return filelistdlt;
    }

    public void setFilelistdlt(String filelistdlt)
    {
        this.filelistdlt = filelistdlt;
    }

    public String getHdnGenId()
    {
        return hdnGenId;
    }

    public void setHdnGenId(String hdnGenId)
    {
        this.hdnGenId = hdnGenId;
    }

    public List<Map<String, String>> getL()
    {
        return l;
    }

    public void setL(List<Map<String, String>> l)
    {
        this.l = l;
    }

    public String getReqno()
    {
        return reqno;
    }

    public void setReqno(final String reqno)
    {
        this.reqno = reqno;
    }

    public String getSrcdest()
    {
        return srcdest;
    }

    public void setSrcdest(String srcdest)
    {
        this.srcdest = srcdest;
    }

    public String getMasterpkPrimeKey()
    {
        return masterpkPrimeKey;
    }

    public void setMasterpkPrimeKey(String masterpkPrimeKey)
    {
        this.masterpkPrimeKey = masterpkPrimeKey;
    }

    public String getMasterpk()
    {
        return masterpk;
    }

    public void setMasterpk(String masterpk)
    {
        this.masterpk = masterpk;
    }

    public String getViewreqno()
    {
        return viewreqno;
    }

    public void setViewreqno(String viewreqno)
    {
        this.viewreqno = viewreqno;
    }

    public String getViewpurpose()
    {
        return viewpurpose;
    }

    public void setViewpurpose(String viewpurpose)
    {
        this.viewpurpose = viewpurpose;
    }

    public String getPurpose()
    {
        return purpose;
    }

    public void setPurpose(final String purpose)
    {
        this.purpose = purpose;
    }

    public String getFilelist()
    {
        return filelist;
    }

    public void setFilelist(final String filelist)
    {
        this.filelist = filelist;
    }

    public String getEmpcode()
    {
        return empcode;
    }

    public void setEmpcode(final String empcode)
    {
        this.empcode = empcode;
    }

    public String getProject()
    {
        return project;
    }

    public void setProject(final String project)
    {
        this.project = project;
    }



    public String getFromdate()
    {
        return fromdate;
    }

    public void setFromdate(final String fromdate)
    {
        this.fromdate = fromdate;
    }

    public String getTodate()
    {
        return todate;
    }

    public void setTodate(final String todate)
    {
        this.todate = todate;
    }

    public String getMasterTask()
    {
        return masterTask;
    }

    public void setMasterTask(final String masterTask)
    {
        this.masterTask = masterTask;
    }

    public String getReportName()
    {
        return reportName;
    }

    public void setReportName(final String reportName)
    {
        this.reportName = reportName;
    }

    public String getRdoRptFormate()
    {
        return rdoRptFormate;
    }

    public void setRdoRptFormate(final String rdoRptFormate)
    {
        this.rdoRptFormate = rdoRptFormate;
    }

    public int getPage()
    {
        return page;
    }

    public void setPage(final int page)
    {
        this.page = page;
    }

    public int getRows()
    {
        return rows;
    }

    public void setRows(final int rows)
    {
        this.rows = rows;
    }

    public String getSidx()
    {
        return sidx;
    }

    public void setSidx(final String sidx)
    {
        this.sidx = sidx;
    }

    public String getTxtReqId()
    {
        return txtReqId;
    }

    public void setTxtReqId(String txtReqId)
    {
        this.txtReqId = txtReqId;
    }
}