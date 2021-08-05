/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.business.finstudio.ETLJobList;

/**
 *
 * @author Siddharth Patel
 */
public class ETLJobListEntityBean
{
    String tableName;
    String fromServer;
    String toServer;
    String fromSchema;
    String toTable;
    String toSchema;
    String kjb;
    String ktr;
    String paths;
    String cronFile;
    String mainJob;
    String type;
    String cronTab;

    public String getTableName()
    {
        return tableName;
    }

    public void setTableName(String tableName)
    {
        this.tableName = tableName;
    }

    public String getFromServer()
    {
        return fromServer;
    }

    public void setFromServer(String fromServer)
    {
        this.fromServer = fromServer;
    }

    public String getToServer()
    {
        return toServer;
    }

    public void setToServer(String toServer)
    {
        this.toServer = toServer;
    }

    public String getFromSchema()
    {
        return fromSchema;
    }

    public void setFromSchema(String fromSchema)
    {
        this.fromSchema = fromSchema;
    }

    public String getToTable()
    {
        return toTable;
    }

    public void setToTable(String toTable)
    {
        this.toTable = toTable;
    }

    public String getToSchema()
    {
        return toSchema;
    }

    public void setToSchema(String toSchema)
    {
        this.toSchema = toSchema;
    }

    public String getKjb()
    {
        return kjb;
    }

    public void setKjb(String kjb)
    {
        this.kjb = kjb;
    }

    public String getKtr()
    {
        return ktr;
    }

    public void setKtr(String ktr)
    {
        this.ktr = ktr;
    }

    public String getPaths()
    {
        return paths;
    }

    public void setPaths(String paths)
    {
        this.paths = paths;
    }

    public String getCronFile()
    {
        return cronFile;
    }

    public void setCronFile(String cronFile)
    {
        this.cronFile = cronFile;
    }

    public String getMainJob()
    {
        return mainJob;
    }

    public void setMainJob(String mainJob)
    {
        this.mainJob = mainJob;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getCronTab()
    {
        return cronTab;
    }

    public void setCronTab(String cronTab)
    {
        this.cronTab = cronTab;
    }




}