/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.finlogic.business.finstudio.entitybean;

/**
 *
 * @author njuser
 */
public class MasterGeneratorV2EntityBean {
    private long serialNo;
    private String userName;
    private String projectName;
    private String databaseType;
    private String moduleName;
    private String aliasName;
    private String selectMasterTableName;
    private String selectDetailTableName;
    private String primarykeyMaster;
    private String primarykeyDetail;
    private String foreignkeyDetail;

    public String getAliasName()
    {
        return aliasName;
    }

    public void setAliasName(String aliasName)
    {
        this.aliasName = aliasName;
    }

    public String getDatabaseType()
    {
        return databaseType;
    }

    public void setDatabaseType(String databaseType)
    {
        this.databaseType = databaseType;
    }

    public String getForeignkeyDetail()
    {
        return foreignkeyDetail;
    }

    public void setForeignkeyDetail(String foreignkeyDetail)
    {
        this.foreignkeyDetail = foreignkeyDetail;
    }

    public String getModuleName()
    {
        return moduleName;
    }

    public void setModuleName(String moduleName)
    {
        this.moduleName = moduleName;
    }

    public String getPrimarykeyDetail()
    {
        return primarykeyDetail;
    }

    public void setPrimarykeyDetail(String primarykeyDetail)
    {
        this.primarykeyDetail = primarykeyDetail;
    }

    public String getPrimarykeyMaster()
    {
        return primarykeyMaster;
    }

    public void setPrimarykeyMaster(String primarykeyMaster)
    {
        this.primarykeyMaster = primarykeyMaster;
    }

    public String getProjectName()
    {
        return projectName;
    }

    public void setProjectName(String projectName)
    {
        this.projectName = projectName;
    }

    public String getSelectDetailTableName()
    {
        return selectDetailTableName;
    }

    public void setSelectDetailTableName(String selectDetailTableName)
    {
        this.selectDetailTableName = selectDetailTableName;
    }

    public String getSelectMasterTableName()
    {
        return selectMasterTableName;
    }

    public void setSelectMasterTableName(String selectMasterTableName)
    {
        this.selectMasterTableName = selectMasterTableName;
    }

    public long getSerialNo()
    {
        return serialNo;
    }

    public void setSerialNo(long serialNo)
    {
        this.serialNo = serialNo;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }
    

}
