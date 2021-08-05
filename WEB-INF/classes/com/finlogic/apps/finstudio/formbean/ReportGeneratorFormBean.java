/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.apps.finstudio.formbean;

/**
 *
 * @author njuser
 */
public class ReportGeneratorFormBean 
{

    private long serialNo;
    private String userName;
    private String projectName;
    private String databaseType;
    private String moduleName;
    private String aliasName;
    private String query;
    private String recordCountQuery;
    private String inputName[];
    private String inputControl[];
    private String grouping;
    private boolean GroupFooter;
    private String selectGroupFooter[];
    private String calculation[];
    private boolean grid;
    private String gridColumnPK;
    private boolean paging;
    private boolean searching;
    private boolean addControl;
    private String selectControl[];
    private String selectColumn[];
    private String indexNumber[];
    private boolean pdf;
    private boolean xls;

    public String[] getInputControl()
    {
        return inputControl;
    }

    public void setInputControl(String[] inputControl)
    {
        this.inputControl = inputControl;
    }

    public String[] getInputName()
    {
        return inputName;
    }

    public void setInputName(String[] inputNameP)
    {
        this.inputName = inputNameP;

        for (int i = 0; i < inputName.length; i++)
        {
            inputName[i] = inputName[i].replaceAll(" ", "_").trim();
            char charac[] = new char[1];
            charac[0] = inputName[i].charAt(0);
            inputName[i] = inputName[i].replaceFirst(new String(charac),new String(charac).toLowerCase());
        }
    }

    public String getProjectName()
    {
        return projectName;
    }

    public void setProjectName(String projectName)
    {
        this.projectName = projectName;
    }

    public String getDatabaseType()
    {
        return databaseType;
    }

    public void setDatabaseType(String databaseType)
    {
        this.databaseType = databaseType;
    }

    public String[] getCalculation()
    {
        return calculation;
    }

    public void setCalculation(String[] calculation)
    {
        this.calculation = calculation;
    }

    public boolean isGroupFooter()
    {
        return GroupFooter;
    }

    public void setGroupFooter(boolean GroupFooter)
    {
        this.GroupFooter = GroupFooter;
    }

    public String[] getSelectGroupFooter()
    {
        return selectGroupFooter;
    }

    public void setSelectGroupFooter(String[] selectGroupFooter)
    {
        this.selectGroupFooter = selectGroupFooter;
    }

    public boolean isXls()
    {
        return xls;
    }

    public void setXls(boolean xls)
    {
        this.xls = xls;
    }

    public long getSerialNo()
    {
        return serialNo;
    }

    public void setSerialNo(long serialNo)
    {
        this.serialNo = serialNo;
    }

    public String getRecordCountQuery()
    {
        return recordCountQuery;
    }

    public void setRecordCountQuery(String recordCountQuery)
    {
        this.recordCountQuery = recordCountQuery;
    }

    public boolean isPdf()
    {
        return pdf;
    }

    public void setPdf(boolean pdf)
    {
        this.pdf = pdf;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public boolean isAddControl()
    {
        return addControl;
    }

    public void setAddControl(boolean addControl)
    {
        this.addControl = addControl;
    }

    public String getAliasName()
    {
        return aliasName;
    }

    public void setAliasName(String aliasName)
    {
        this.aliasName = aliasName;
    }

    public boolean isGrid()
    {
        return grid;
    }

    public void setGrid(boolean grid)
    {
        this.grid = grid;
    }

    public String getGridColumnPK()
    {
        return gridColumnPK;
    }

    public void setGridColumnPK(String gridColumnPK)
    {
        this.gridColumnPK = gridColumnPK;
    }

    public String getGrouping()
    {
        return grouping;
    }

    public void setGrouping(String grouping)
    {
        this.grouping = grouping;
    }

    public String[] getIndexNumber()
    {
        return indexNumber;
    }

    public void setIndexNumber(String[] indexNumber)
    {
        this.indexNumber = indexNumber;
    }

    public String getModuleName()
    {
        return moduleName;
    }

    public void setModuleName(String moduleName)
    {
        this.moduleName = moduleName;
    }

    public boolean isPaging()
    {
        return paging;
    }

    public void setPaging(boolean paging)
    {
        this.paging = paging;
    }

    public String getQuery()
    {
        return query;
    }

    public void setQuery(String query)
    {
        this.query = query;
    }

    public boolean isSearching()
    {
        return searching;
    }

    public void setSearching(boolean searching)
    {
        this.searching = searching;
    }

    public String[] getSelectColumn()
    {
        return selectColumn;
    }

    public void setSelectColumn(String[] selectColumn)
    {
        this.selectColumn = selectColumn;
    }

    public String[] getSelectControl()
    {
        return selectControl;
    }

    public void setSelectControl(String[] selectControl)
    {
        this.selectControl = selectControl;
    }
}
