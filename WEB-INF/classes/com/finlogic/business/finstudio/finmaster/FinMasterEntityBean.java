/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.business.finstudio.finmaster;

/**
 *
 * @author Sonam Patel
 */
public class FinMasterEntityBean
{

    private String projectName;
    private String moduleName;
    private String requestNo;
    private String problemStatement;
    private String solutionObjective;
    private String existingPractice;
    private String placement;
    private String aliasName;
    private String masterTableName;
    private String masterPrimaryKey;
    private boolean addTab;
    private boolean editTab;
    private boolean deleteTab;
    private boolean viewTab;
    private String empCode;
    private boolean viewColumns;
    private boolean viewOnScreen;
    private boolean viewPDF;
    private boolean viewXLS;
    //Master Table-ADD Tab Fields
    private String mstAddFieldName[];
    private String mstAddControl[];
    private String mstAddValidation[];
    private String mstAddSelNature[];
    private String mstAddRemarks[];
    //Master Table-EDIT Tab Fields
    private String mstEditFieldName[];
    private String mstEditControl[];
    private String mstEditValidation[];
    private String mstEditSelNature[];
    private String mstEditRemarks[];
    //Master Table-DELETE Tab Fields
    private String mstDelFieldName[];
    private String mstDelControl[];
    private String mstDelValidation[];
    private String mstDelSelNature[];
    private String mstDelRemarks[];
    //Master Table-VIEW Tab Fields
    private String mstViewFieldName[];
    private String mstViewControl[];
    private String mstViewValidation[];
    private String mstViewSelNature[];
    private String mstViewRemarks[];

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

    public String getRequestNo()
    {
        return requestNo;
    }

    public void setRequestNo(final String requestNo)
    {
        this.requestNo = requestNo;
    }

    public String getProblemStatement()
    {
        return problemStatement;
    }

    public void setProblemStatement(final String problemStatement)
    {
        this.problemStatement = problemStatement;
    }

    public String getSolutionObjective()
    {
        return solutionObjective;
    }

    public void setSolutionObjective(final String solutionObjective)
    {
        this.solutionObjective = solutionObjective;
    }

    public String getExistingPractice()
    {
        return existingPractice;
    }

    public void setExistingPractice(final String existingPractice)
    {
        this.existingPractice = existingPractice;
    }

    public String getPlacement()
    {
        return placement;
    }

    public void setPlacement(final String placement)
    {
        this.placement = placement;
    }

    public String getAliasName()
    {
        return aliasName;
    }

    public void setAliasName(final String aliasName)
    {
        this.aliasName = aliasName;
    }

    public String getMasterTableName()
    {
        return masterTableName;
    }

    public void setMasterTableName(final String masterTableName)
    {
        this.masterTableName = masterTableName;
    }

    public String getMasterPrimaryKey()
    {
        return masterPrimaryKey;
    }

    public void setMasterPrimaryKey(final String masterPrimaryKey)
    {
        this.masterPrimaryKey = masterPrimaryKey;
    }

    public boolean isAddTab()
    {
        return addTab;
    }

    public void setAddTab(final boolean addTab)
    {
        this.addTab = addTab;
    }

    public boolean isEditTab()
    {
        return editTab;
    }

    public void setEditTab(final boolean editTab)
    {
        this.editTab = editTab;
    }

    public boolean isDeleteTab()
    {
        return deleteTab;
    }

    public void setDeleteTab(final boolean deleteTab)
    {
        this.deleteTab = deleteTab;
    }

    public boolean isViewTab()
    {
        return viewTab;
    }

    public void setViewTab(final boolean viewTab)
    {
        this.viewTab = viewTab;
    }

    public String getEmpCode()
    {
        return empCode;
    }

    public void setEmpCode(final String empCode)
    {
        this.empCode = empCode;
    }

    public boolean isViewColumns()
    {
        return viewColumns;
    }

    public void setViewColumns(final boolean viewColumns)
    {
        this.viewColumns = viewColumns;
    }

    public boolean isViewOnScreen()
    {
        return viewOnScreen;
    }

    public void setViewOnScreen(final boolean viewOnScreen)
    {
        this.viewOnScreen = viewOnScreen;
    }

    public boolean isViewPDF()
    {
        return viewPDF;
    }

    public void setViewPDF(final boolean viewPDF)
    {
        this.viewPDF = viewPDF;
    }

    public boolean isViewXLS()
    {
        return viewXLS;
    }

    public void setViewXLS(final boolean viewXLS)
    {
        this.viewXLS = viewXLS;
    }

    public String[] getMstAddFieldName()
    {
        return mstAddFieldName;
    }

    public void setMstAddFieldName(final String[] mstAddFieldName)
    {
        this.mstAddFieldName = mstAddFieldName;
    }

    public String[] getMstAddControl()
    {
        return mstAddControl;
    }

    public void setMstAddControl(final String[] mstAddControl)
    {
        this.mstAddControl = mstAddControl;
    }

    public String[] getMstAddValidation()
    {
        return mstAddValidation;
    }

    public void setMstAddValidation(final String[] mstAddValidation)
    {
        this.mstAddValidation = mstAddValidation;
    }

    public String[] getMstAddSelNature()
    {
        return mstAddSelNature;
    }

    public void setMstAddSelNature(final String[] mstAddSelNature)
    {
        this.mstAddSelNature = mstAddSelNature;
    }

    public String[] getMstAddRemarks()
    {
        return mstAddRemarks;
    }

    public void setMstAddRemarks(final String[] mstAddRemarks)
    {
        this.mstAddRemarks = mstAddRemarks;
    }

    public String[] getMstEditFieldName()
    {
        return mstEditFieldName;
    }

    public void setMstEditFieldName(final String[] mstEditFieldName)
    {
        this.mstEditFieldName = mstEditFieldName;
    }

    public String[] getMstEditControl()
    {
        return mstEditControl;
    }

    public void setMstEditControl(final String[] mstEditControl)
    {
        this.mstEditControl = mstEditControl;
    }

    public String[] getMstEditValidation()
    {
        return mstEditValidation;
    }

    public void setMstEditValidation(final String[] mstEditValidation)
    {
        this.mstEditValidation = mstEditValidation;
    }

    public String[] getMstEditSelNature()
    {
        return mstEditSelNature;
    }

    public void setMstEditSelNature(final String[] mstEditSelNature)
    {
        this.mstEditSelNature = mstEditSelNature;
    }

    public String[] getMstEditRemarks()
    {
        return mstEditRemarks;
    }

    public void setMstEditRemarks(final String[] mstEditRemarks)
    {
        this.mstEditRemarks = mstEditRemarks;
    }

    public String[] getMstDelFieldName()
    {
        return mstDelFieldName;
    }

    public void setMstDelFieldName(final String[] mstDelFieldName)
    {
        this.mstDelFieldName = mstDelFieldName;
    }

    public String[] getMstDelControl()
    {
        return mstDelControl;
    }

    public void setMstDelControl(final String[] mstDelControl)
    {
        this.mstDelControl = mstDelControl;
    }

    public String[] getMstDelValidation()
    {
        return mstDelValidation;
    }

    public void setMstDelValidation(final String[] mstDelValidation)
    {
        this.mstDelValidation = mstDelValidation;
    }

    public String[] getMstDelSelNature()
    {
        return mstDelSelNature;
    }

    public void setMstDelSelNature(final String[] mstDelSelNature)
    {
        this.mstDelSelNature = mstDelSelNature;
    }

    public String[] getMstDelRemarks()
    {
        return mstDelRemarks;
    }

    public void setMstDelRemarks(final String[] mstDelRemarks)
    {
        this.mstDelRemarks = mstDelRemarks;
    }

    public String[] getMstViewFieldName()
    {
        return mstViewFieldName;
    }

    public void setMstViewFieldName(final String[] mstViewFieldName)
    {
        this.mstViewFieldName = mstViewFieldName;
    }

    public String[] getMstViewControl()
    {
        return mstViewControl;
    }

    public void setMstViewControl(final String[] mstViewControl)
    {
        this.mstViewControl = mstViewControl;
    }

    public String[] getMstViewValidation()
    {
        return mstViewValidation;
    }

    public void setMstViewValidation(final String[] mstViewValidation)
    {
        this.mstViewValidation = mstViewValidation;
    }

    public String[] getMstViewSelNature()
    {
        return mstViewSelNature;
    }

    public void setMstViewSelNature(final String[] mstViewSelNature)
    {
        this.mstViewSelNature = mstViewSelNature;
    }

    public String[] getMstViewRemarks()
    {
        return mstViewRemarks;
    }

    public void setMstViewRemarks(final String[] mstViewRemarks)
    {
        this.mstViewRemarks = mstViewRemarks;
    }
}
