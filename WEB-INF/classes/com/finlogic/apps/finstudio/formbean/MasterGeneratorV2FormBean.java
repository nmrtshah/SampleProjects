/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.finlogic.apps.finstudio.formbean;

import java.util.ArrayList;

/**
 *
 * @author njuser
 */
public class MasterGeneratorV2FormBean {
    private long serialNo;
    private String userName;
    private String projectName;
    private String databaseType;
    private String moduleName;
    private String aliasName;
    private String masterTableName;
    private String selectMasterTableName;
    private String detailTableName;
    private String selectDetailTableName;

    private String primarykeyMaster;
    private String[] inputControlMaster;
    private String primarykeyDetail;
    private String foreignkeyDetail;
    private String[] inputControlDetail;

    private ArrayList<String> masterColumns;
    private ArrayList<String> detailColumns;
    private ArrayList<String> selectedMasterColumns;
    private ArrayList<String> selectedDetailColumns;
    private ArrayList<String> selectedMasterControls;
    private ArrayList<String> selectedDetailControls;

    public ArrayList<String> getDetailColumns()
    {
        return detailColumns;
    }

    public void setDetailColumns(ArrayList<String> detailColumns)
    {
        this.detailColumns = detailColumns;
    }

    public ArrayList<String> getMasterColumns()
    {
        return masterColumns;
    }

    public void setMasterColumns(ArrayList<String> masterColumns)
    {
        this.masterColumns = masterColumns;
    }

    public ArrayList<String> getSelectedDetailColumns()
    {
        return selectedDetailColumns;
    }

    public void setSelectedDetailColumns(ArrayList<String> selectedDetailColumns)
    {
        this.selectedDetailColumns = selectedDetailColumns;
    }

    public ArrayList<String> getSelectedDetailControls()
    {
        return selectedDetailControls;
    }

    public void setSelectedDetailControls(ArrayList<String> selectedDetailControls)
    {
        this.selectedDetailControls = selectedDetailControls;
    }

    public ArrayList<String> getSelectedMasterColumns()
    {
        return selectedMasterColumns;
    }

    public void setSelectedMasterColumns(ArrayList<String> selectedMasterColumns)
    {
        this.selectedMasterColumns = selectedMasterColumns;
    }

    public ArrayList<String> getSelectedMasterControls()
    {
        return selectedMasterControls;
    }

    public void setSelectedMasterControls(ArrayList<String> selectedMasterControls)
    {
        this.selectedMasterControls = selectedMasterControls;
    }
    
    public String getForeignkeyDetail()
    {
        return foreignkeyDetail;
    }

    public void setForeignkeyDetail(String foreignkeyDetail)
    {
        this.foreignkeyDetail = foreignkeyDetail;
    }

    public String[] getInputControlDetail()
    {
        return inputControlDetail;
    }

    public void setInputControlDetail(String[] inputControlDetail)
    {
        this.inputControlDetail = inputControlDetail;
    }

    public String[] getInputControlMaster()
    {
        return inputControlMaster;
    }

    public void setInputControlMaster(String[] inputControlMaster)
    {
        this.inputControlMaster = inputControlMaster;
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

    public String getDetailTableName()
    {
        return detailTableName;
    }

    public void setDetailTableName(String detailTableName)
    {
        this.detailTableName = detailTableName;
    }

    public String getMasterTableName()
    {
        return masterTableName;
    }

    public void setMasterTableName(String masterTableName)
    {
        this.masterTableName = masterTableName;
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
