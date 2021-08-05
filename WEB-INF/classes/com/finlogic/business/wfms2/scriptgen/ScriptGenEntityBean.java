/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.business.wfms2.scriptgen;

/**
 *
 * @author Ankur Mistry
 */
public class ScriptGenEntityBean
{

    private int prjID;
    private int moduleID;
    private int testCaseID;
    //private int browserID;
    private String cmbServerName;
    private String txtUserName;
    private String txtPassword;
    private String chkloginOnce;
    private int[] htmlControlID;
    private int[] accessID;
    private int[] eventID;
    private int[] testCaseNatureID;
    private String[] controlNameID;
    private String[] access;
    private String[] controlValue;
    private String[] message;
    private String[] confirmMsg;
    private String[] xpath;
    private String[] csvHeaderString;
    private int[] eventArray;
    private String empCode;
    private String fromDate;
    private String toDate;

    public String getChkloginOnce()
    {
        return chkloginOnce;
        
    }

    public void setChkloginOnce(String chkloginOnce)
    {
        this.chkloginOnce = chkloginOnce;
    }

    
    
    public String[] getXpath()
    {
        return xpath;
    }

    public void setXpath(String[] xpath)
    {
        this.xpath = xpath;
    }

    public String getCmbServerName()
    {
        return cmbServerName;
    }

    public void setCmbServerName(String cmbServerName)
    {
        this.cmbServerName = cmbServerName;
    }

    public String getTxtPassword()
    {
        return txtPassword;
    }

    public void setTxtPassword(String txtPassword)
    {
        this.txtPassword = txtPassword;
    }

    public String getTxtUserName()
    {
        return txtUserName;
    }

    public void setTxtUserName(String txtUserName)
    {
        this.txtUserName = txtUserName;
    }

    public String[] getConfirmMsg()
    {
        return confirmMsg;
    }

    public void setConfirmMsg(String[] confirmMsg)
    {
        this.confirmMsg = confirmMsg;
    }

    public String[] getAccess()
    {
        return access;
    }

    public void setAccess(final String[] access)
    {
        this.access = access;
    }

    public int[] getAccessID()
    {
        return accessID;
    }

    public void setAccessID(final int[] accessID)
    {
        this.accessID = accessID;
    }

//    public int getBrowserID()
//    {
//        return browserID;
//    }
//
//    public void setBrowserID(final int browserID)
//    {
//        this.browserID = browserID;
//    }
    public String[] getControlNameID()
    {
        return controlNameID;
    }

    public void setControlNameID(final String[] controlNameID)
    {
        this.controlNameID = controlNameID;
    }

    public String[] getControlValue()
    {
        return controlValue;
    }

    public void setControlValue(final String[] controlValue)
    {
        this.controlValue = controlValue;
    }

    public int[] getEventID()
    {
        return eventID;
    }

    public void setEventID(final int[] eventID)
    {
        this.eventID = eventID;
    }

    public int[] getHtmlControlID()
    {
        return htmlControlID;
    }

    public void setHtmlControlID(final int[] htmlControlID)
    {
        this.htmlControlID = htmlControlID;
    }

    public String[] getMessage()
    {
        return message;
    }

    public void setMessage(final String[] message)
    {
        this.message = message;
    }

    public int getModuleID()
    {
        return moduleID;
    }

    public void setModuleID(final int moduleID)
    {
        this.moduleID = moduleID;
    }

    public int getPrjID()
    {
        return prjID;
    }

    public void setPrjID(final int prjID)
    {
        this.prjID = prjID;
    }

    public int getTestCaseID()
    {
        return testCaseID;
    }

    public void setTestCaseID(final int testCaseID)
    {
        this.testCaseID = testCaseID;
    }

    public int[] getTestCaseNatureID()
    {
        return testCaseNatureID;
    }

    public void setTestCaseNatureID(final int[] testCaseNatureID)
    {
        this.testCaseNatureID = testCaseNatureID;
    }

    public int[] getEventArray()
    {
        return eventArray;
    }

    public void setEventArray(int[] eventArray)
    {
        this.eventArray = eventArray;
    }

    public String getEmpCode()
    {
        return empCode;
    }

    public void setEmpCode(String empCode)
    {
        this.empCode = empCode;
    }

    public String getFromDate()
    {
        return fromDate;
    }

    public void setFromDate(String fromDate)
    {
        this.fromDate = fromDate;
    }

    public String getToDate()
    {
        return toDate;
    }

    public void setToDate(String toDate)
    {
        this.toDate = toDate;
    }

    public String[] getCsvHeaderString()
    {
        return csvHeaderString;
    }

    public void setCsvHeaderString(String[] csvHeaderString)
    {
        this.csvHeaderString = csvHeaderString;
    }
    
    
}