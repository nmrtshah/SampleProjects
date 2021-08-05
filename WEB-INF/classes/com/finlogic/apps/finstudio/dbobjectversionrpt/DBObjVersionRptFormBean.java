/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.apps.finstudio.dbobjectversionrpt;

/**
 *
 * @author njuser
 */
public class DBObjVersionRptFormBean
{
    private String txtDB;
    private String cmbDB;
    private String cmbObjType;
    private String txtObjName;
    private String cmbObjName;
    private String cmbfrmObjVersion;
    private String cmbtoObjVersion;

    public String getCmbfrmObjVersion()
    {
        return cmbfrmObjVersion;
    }

    public void setCmbfrmObjVersion(String cmbfrmObjVersion)
    {
        this.cmbfrmObjVersion = cmbfrmObjVersion;
    }

    public String getCmbtoObjVersion()
    {
        return cmbtoObjVersion;
    }

    public void setCmbtoObjVersion(String cmbtoObjVersion)
    {
        this.cmbtoObjVersion = cmbtoObjVersion;
    }

    public String getTxtDB()
    {
        return txtDB;
    }

    public void setTxtDB(String txtDB)
    {
        this.txtDB = txtDB;
    }

    public String getCmbDB()
    {
        return cmbDB;
    }

    public void setCmbDB(String cmbDB)
    {
        this.cmbDB = cmbDB;
    }

    public String getCmbObjType()
    {
        return cmbObjType;
    }

    public void setCmbObjType(String cmbObjType)
    {
        this.cmbObjType = cmbObjType;
    }

    public String getTxtObjName()
    {
        return txtObjName;
    }

    public void setTxtObjName(String txtObjName)
    {
        this.txtObjName = txtObjName;
    }

    public String getCmbObjName()
    {
        return cmbObjName;
    }

    public void setCmbObjName(String cmbObjName)
    {
        this.cmbObjName = cmbObjName;
    }
}
