/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.business.finstudio.webservice;

/**
 *
 * @author Sonam Patel
 */
public class WebServiceEntityBean
{
    private String projectName;
    private String moduleName;
    private String interfaceName;
    private String requestNo;
    private String empCode;

    public String getProjectName()
    {
        return projectName;
    }

    public void setProjectName(final String projectName)
    {
        this.projectName = projectName;
    }

    public String getModuleName()
    {
        return moduleName;
    }

    public void setModuleName(final String moduleName)
    {
        this.moduleName = moduleName;
    }

    public String getInterfaceName()
    {
        return interfaceName;
    }

    public void setInterfaceName(final String interfaceName)
    {
        this.interfaceName = interfaceName;
    }

    public String getRequestNo()
    {
        return requestNo;
    }

    public void setRequestNo(final String requestNo)
    {
        this.requestNo = requestNo;
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
