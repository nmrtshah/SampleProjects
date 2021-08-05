/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.finlogic.business.finstudio.entitybean;

/**
 *
 * @author njuser
 */
public class ReportGeneratorEntityBean
{
    private long serialNo;
    private String userName;
    private String moduleName;
    private String aliasName;
    private String query;
    private String recordCountQuery;

    private boolean  grid;
    private String gridColumnPK;
    private boolean  paging;
    private boolean  searching;
    private boolean addControl;

    private boolean pdf;
    private boolean xls;
    private String  grouping;

    private boolean GroupFooter;
    private String selectGroupFooter[];
    private String calculation[];

    private String selectControl[];
    private String selectColumn[];
    private String indexNumber[];

    public boolean isGroupFooter()
    {
        return GroupFooter;
    }

    public void setGroupFooter(boolean GroupFooter)
    {
        this.GroupFooter = GroupFooter;
    }

    public String[] getCalculation()
    {
        return calculation;
    }

    public void setCalculation(String[] calculation)
    {
        this.calculation = calculation;
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

    public boolean isAddControl()
    {
        return addControl;
    }

    public void setAddControl(boolean addControl)
    {
        this.addControl = addControl;
    }

    public String getRecordCountQuery()
    {
        return recordCountQuery;
    }

    public void setRecordCountQuery(String recordCountQuery)
    {
        this.recordCountQuery = recordCountQuery;
    }
    public String[] getIndexNumber()
    {
        return indexNumber;
    }

    public void setIndexNumber(String[] indexNumber)
    {
        this.indexNumber = indexNumber;
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

    public boolean isPdf()
    {
        return pdf;
    }

    public void setPdf(boolean pdf)
    {
        this.pdf = pdf;
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
