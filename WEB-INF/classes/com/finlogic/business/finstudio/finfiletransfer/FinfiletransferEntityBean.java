package com.finlogic.business.finstudio.finfiletransfer;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class FinfiletransferEntityBean
{
    private int masterpkDB;
    private String srcdestDB;
    private int reqnoDB;
    private String purposeDB;
    private String masterpk;
    private String empcode;
    private String project;
    private String viewreqno;
    private String viewpurpose;
    private String masterTask;
    private String hdnGenId;
    private String fromdate;
    private String todate;
    private String lang;
    private String txtReqId;
    private String fileName;
    private String projectname;
    private String srcdest;

    public String getSrcdest()
    {
        return srcdest;
    }

    public void setSrcdest(String srcdest)
    {
        this.srcdest = srcdest;
    }

    public String getProjectname()
    {
        return projectname;
    }

    public void setProjectname(String projectname)
    {
        this.projectname = projectname;
    }

    public String getFileName()
    {
        return fileName;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }


    public String getLang()
    {
        return lang;
    }

    public void setLang(String lang)
    {
        this.lang = lang;
    }

    public String getFromdate()
    {
        return fromdate;
    }

    public void setFromdate(String fromdate)
    {
        this.fromdate = fromdate;
    }

    public String getTodate()
    {
        return todate;
    }

    public void setTodate(String todate)
    {
        this.todate = todate;
    }

    public String getHdnGenId()
    {
        return hdnGenId;
    }

    public void setHdnGenId(String hdnGenId)
    {
        this.hdnGenId = hdnGenId;
    }
    private List<Map<String, String>> l = new LinkedList<Map<String, String>>();

    public List<Map<String, String>> getL()
    {
        return l;
    }

    public void setL(List<Map<String, String>> l)
    {
        this.l = l;
    }

    public int getReqnoDB()
    {
        return reqnoDB;
    }

    public void setReqnoDB(final int reqnoDB)
    {
        this.reqnoDB = reqnoDB;
    }

    public String getPurposeDB()
    {
        return purposeDB;
    }

    public void setPurposeDB(final String purposeDB)
    {
        this.purposeDB = purposeDB;
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

    public int getMasterpkDB()
    {
        return masterpkDB;
    }

    public void setMasterpkDB(final int masterpkDB)
    {
        this.masterpkDB = masterpkDB;
    }

    public String getSrcdestDB()
    {
        return srcdestDB;
    }

    public void setSrcdestDB(final String srcdestDB)
    {
        this.srcdestDB = srcdestDB;
    }

    public String getMasterpk()
    {
        return masterpk;
    }

    public void setMasterpk(final String masterpk)
    {
        this.masterpk = masterpk;
    }

    public String getViewreqno()
    {
        return viewreqno;
    }

    public void setViewreqno(final String viewreqno)
    {
        this.viewreqno = viewreqno;
    }

    public String getViewpurpose()
    {
        return viewpurpose;
    }

    public void setViewpurpose(final String viewpurpose)
    {
        this.viewpurpose = viewpurpose;
    }

    public String getMasterTask()
    {
        return masterTask;
    }

    public void setMasterTask(final String masterTask)
    {
        this.masterTask = masterTask;
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