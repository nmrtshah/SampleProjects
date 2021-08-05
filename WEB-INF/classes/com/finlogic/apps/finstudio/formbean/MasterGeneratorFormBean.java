/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.finlogic.apps.finstudio.formbean;

/**
 *
 * @author njuser
 */
public class MasterGeneratorFormBean {
    private long serialNo;
    private String userName;
    private String projectName;
    private String databaseType;
    private String moduleName;
    private String aliasName;
    private String tableName;
    private String selectTableName;
    private String inputControl[];
    private String primarykey;

    public String getPrimarykey()
    {
        return primarykey;
    }

    public void setPrimarykey(String primarykey)
    {
        this.primarykey = primarykey;
    }


    public String getGridColumnPrimarykey()
    {
        return gridColumnPrimarykey;
    }

    public void setGridColumnPrimarykey(String gridColumnPrimarykey)
    {
        this.gridColumnPrimarykey = gridColumnPrimarykey;
    }
    private String gridColumnPrimarykey;


    public String[] getInputControl()
    {
        return inputControl;
    }

    public void setInputControl(String[] inputControl)
    {
        this.inputControl = inputControl;
    }

   
    public String getTableName()
    {
        return tableName;
    }

    public void setTableName(String tableName)
    {
        this.tableName = tableName;
    }

    public String getSelectTableName()
    {
        return selectTableName;
    }

    public void setSelectTableName(String selectTableName)
    {
        this.selectTableName = selectTableName;
    }

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

    public String getModuleName()
    {
        return moduleName;
    }

    public void setModuleName(String moduleName)
    {
        this.moduleName = moduleName;
    }

    public String getProjectName()
    {
        return projectName;
    }

    public void setProjectName(String projectName)
    {
        this.projectName = projectName;
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
