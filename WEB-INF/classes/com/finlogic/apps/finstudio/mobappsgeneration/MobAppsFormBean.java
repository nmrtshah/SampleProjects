/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.apps.finstudio.mobappsgeneration;

import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author njuser
 */
public class MobAppsFormBean
{

    private static final String TOMCAT = finpack.FinPack.getProperty("tomcat1_path");
    private static final String UPLOAD = TOMCAT + "/webapps/finstudio/upload";
    private static final String TEMPLATE = TOMCAT + "/webapps/finstudio/templates";
    private static final String GENERATED = TOMCAT + "/webapps/finstudio/generated";
    private String txtAppName;
    private String txtVersion;
    private String txtPackage;
    private String txtWelcomeFile;
    private MultipartFile fSource;
    private boolean chkAndroid;
    private boolean chkBBerry;
    private boolean chkSymbian;
    private String empCode;

    public static String getTOMCAT()
    {
        return TOMCAT;
    }

    public static String getGENERATED()
    {
        return GENERATED;
    }

    public static String getTEMPLATE()
    {
        return TEMPLATE;
    }

    public static String getUPLOAD()
    {
        return UPLOAD;
    }

    public String getTxtAppName()
    {
        return txtAppName;
    }

    public void setTxtAppName(final String txtAppName)
    {
        this.txtAppName = txtAppName;
    }

    public String getTxtVersion()
    {
        return txtVersion;
    }

    public void setTxtVersion(final String txtVersion)
    {
        this.txtVersion = txtVersion;
    }

    public String getTxtPackage()
    {
        return txtPackage;
    }

    public void setTxtPackage(final String txtPackage)
    {
        this.txtPackage = txtPackage;
    }

    public String getTxtWelcomeFile()
    {
        return txtWelcomeFile;
    }

    public void setTxtWelcomeFile(final String txtWelcomeFile)
    {
        this.txtWelcomeFile = txtWelcomeFile;
    }

    public MultipartFile getfSource()
    {
        return fSource;
    }

    public void setfSource(final MultipartFile fSource)
    {
        this.fSource = fSource;
    }

    public boolean isChkAndroid()
    {
        return chkAndroid;
    }

    public void setChkAndroid(final boolean chkAndroid)
    {
        this.chkAndroid = chkAndroid;
    }

    public boolean isChkBBerry()
    {
        return chkBBerry;
    }

    public void setChkBBerry(final boolean chkBBerry)
    {
        this.chkBBerry = chkBBerry;
    }

    public boolean isChkSymbian()
    {
        return chkSymbian;
    }

    public void setChkSymbian(final boolean chkSymbian)
    {
        this.chkSymbian = chkSymbian;
    }

    public String getEmpCode()
    {
        return empCode;
    }

    public void setEmpCode(final String empCode)
    {
        this.empCode = empCode;
    }
}
