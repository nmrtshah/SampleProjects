/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.finlogic.business.finstudio.entitybean;

/**
 *
 * @author njuser
 */
public class ReportGeneratorDetailEntityBean {
    private long serialNo;
    private long rgSerialNo;
    private String controlName;
    private String controlField;
    private String controlIndex;

    public String getControlField()
    {
        return controlField;
    }

    public void setControlField(String controlField)
    {
        this.controlField = controlField;
    }

    public String getControlIndex()
    {
        return controlIndex;
    }

    public void setControlIndex(String controlIndex)
    {
        this.controlIndex = controlIndex;
    }

    public String getControlName()
    {
        return controlName;
    }

    public void setControlName(String controlName)
    {
        this.controlName = controlName;
    }

    public long getRgSerialNo()
    {
        return rgSerialNo;
    }

    public void setRgSerialNo(long rgSerialNo)
    {
        this.rgSerialNo = rgSerialNo;
    }

    public long getSerialNo()
    {
        return serialNo;
    }

    public void setSerialNo(long serialNo)
    {
        this.serialNo = serialNo;
    }

    

}
