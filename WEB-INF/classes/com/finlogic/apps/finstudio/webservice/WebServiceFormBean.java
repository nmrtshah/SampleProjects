/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.apps.finstudio.webservice;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Sonam Patel
 */
public class WebServiceFormBean
{

    private static final String TOMCAT = finpack.FinPack.getProperty("tomcat1_path");
    private static final String WSLOC = TOMCAT + "/webapps/finstudio/upload/ws";
    private String cmbProjectName;
    private String txtModuleName;
    private String txtReqNo;
    private MultipartFile interfaceFile;
    private String intrfcName;
    private String empCode;
    private String txtSrno;
    private List<String> beans = new ArrayList<String>();

    public static String getTOMCAT()
    {
        return TOMCAT;
    }

    public static String getWSLOC()
    {
        return WSLOC;
    }

    public String getCmbProjectName()
    {
        return cmbProjectName;
    }

    public void setCmbProjectName(final String cmbProjectName)
    {
        this.cmbProjectName = cmbProjectName;
    }

    public String getTxtModuleName()
    {
        return txtModuleName;
    }

    public void setTxtModuleName(final String txtModuleName)
    {
        this.txtModuleName = txtModuleName;
    }

    public String getTxtReqNo()
    {
        return txtReqNo;
    }

    public void setTxtReqNo(final String txtReqNo)
    {
        this.txtReqNo = txtReqNo;
    }

    public MultipartFile getInterfaceFile()
    {
        return interfaceFile;
    }

    public void setInterfaceFile(final MultipartFile interfaceFile)
    {
        this.interfaceFile = interfaceFile;
    }

    public String getIntrfcName()
    {
        return intrfcName;
    }

    public void setIntrfcName(final String intrfcName)
    {
        this.intrfcName = intrfcName;
    }

    public String getEmpCode()
    {
        return empCode;
    }

    public void setEmpCode(final String empCode)
    {
        this.empCode = empCode;
    }

    public String getTxtSrno()
    {
        return txtSrno;
    }

    public void setTxtSrno(final String txtSrno)
    {
        this.txtSrno = txtSrno;
    }

    public List<String> getBeans()
    {
        return beans;
    }

    public void setBeans(final List<String> beans)
    {
        this.beans = beans;
    }
}
