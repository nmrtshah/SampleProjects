/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.business.finstudio.mobappsgeneration;

/**
 *
 * @author njuser
 */
public class MobAppsEntityBean
{

    private String appName;
    private String appVersion;
    private String appPackage;
    private String welcomeFile;
    private String target;
    private String empCode;

    public String getAppName()
    {
        return appName;
    }

    public void setAppName(final String appName)
    {
        this.appName = appName;
    }

    public String getAppVersion()
    {
        return appVersion;
    }

    public void setAppVersion(final String appVersion)
    {
        this.appVersion = appVersion;
    }

    public String getAppPackage()
    {
        return appPackage;
    }

    public void setAppPackage(final String appPackage)
    {
        this.appPackage = appPackage;
    }

    public String getWelcomeFile()
    {
        return welcomeFile;
    }

    public void setWelcomeFile(final String welcomeFile)
    {
        this.welcomeFile = welcomeFile;
    }

    public String getTarget()
    {
        return target;
    }

    public void setTarget(final String target)
    {
        this.target = target;
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
