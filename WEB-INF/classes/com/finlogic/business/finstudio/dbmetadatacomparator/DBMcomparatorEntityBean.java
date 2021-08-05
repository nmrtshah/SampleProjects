/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.business.finstudio.dbmetadatacomparator;

/**
 *
 * @author Jeegar Kumar Patel
 */
public class DBMcomparatorEntityBean
{

    private String txtDB;
    private String cmbDB;
    private String cmbObjType;
    private String txtObjName;
    private String cmbObjName;
    private String server;

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

    public String getServer()
    {
        return server;
    }

    public void setServer(String server)
    {
        this.server = server;
    }
}