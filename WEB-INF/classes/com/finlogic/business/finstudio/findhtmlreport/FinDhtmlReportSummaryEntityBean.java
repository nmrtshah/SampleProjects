/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.business.finstudio.findhtmlreport;

/**
 *
 * @author njuser
 */
public class FinDhtmlReportSummaryEntityBean
{
    //Project Spec 

    private String aliasName[];
    private String allQuery[];
    private boolean grouping = false;
    private String groupField;
    private boolean groupFooter = false;
    private String groupFtrCol[];
    private String groupFtrCal[];
    //Tab Selection
    private String reportTitle;
    private boolean columns;
    //export
    private boolean pdf = false;
    private boolean excel = false;
    private boolean html = false;
    //Columns
    private String reportColumns[];
    private String primaryKey;
    private String empCode;
    private boolean addControl = false;
    private String columnControl[];
    private String gridColumn[];
    //in rpt_data
    private String selectedColumns[];
    private String mainQueryColumns[];
    private String childQueryColumns[];
    private String allColumns[];
    private String allColumnTypes[];
    private boolean pageFooter;
    private boolean grandTotal;
    private String pageFooterColumn[];
    private String conType;
    private String devServer;
    private String methodName;
    private String detailRefNo;
    private int srno;

    public String[] getAliasName()
    {
        return aliasName;
    }

    public void setAliasName(String[] aliasName)
    {
        this.aliasName = aliasName;
    }

    public String[] getAllQuery()
    {
        return allQuery;
    }

    public void setAllQuery(String[] allQuery)
    {
        this.allQuery = allQuery;
    }

    public boolean isGrouping()
    {
        return grouping;
    }

    public void setGrouping(boolean grouping)
    {
        this.grouping = grouping;
    }

    public String getGroupField()
    {
        return groupField;
    }

    public void setGroupField(String groupField)
    {
        this.groupField = groupField;
    }

    public boolean isGroupFooter()
    {
        return groupFooter;
    }

    public void setGroupFooter(boolean groupFooter)
    {
        this.groupFooter = groupFooter;
    }

    public String[] getGroupFtrCol()
    {
        return groupFtrCol;
    }

    public void setGroupFtrCol(String[] groupFtrCol)
    {
        this.groupFtrCol = groupFtrCol;
    }

    public String[] getGroupFtrCal()
    {
        return groupFtrCal;
    }

    public void setGroupFtrCal(String[] groupFtrCal)
    {
        this.groupFtrCal = groupFtrCal;
    }

    public String getReportTitle()
    {
        return reportTitle;
    }

    public void setReportTitle(String reportTitle)
    {
        this.reportTitle = reportTitle;
    }

    public String[] getReportColumns()
    {
        return reportColumns;
    }

    public void setReportColumns(String[] reportColumns)
    {
        this.reportColumns = reportColumns;
    }

    public String getPrimaryKey()
    {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey)
    {
        this.primaryKey = primaryKey;
    }

    public String getEmpCode()
    {
        return empCode;
    }

    public void setEmpCode(String empCode)
    {
        this.empCode = empCode;
    }

    public boolean isAddControl()
    {
        return addControl;
    }

    public void setAddControl(boolean addControl)
    {
        this.addControl = addControl;
    }

    public String[] getColumnControl()
    {
        return columnControl;
    }

    public void setColumnControl(String[] columnControl)
    {
        this.columnControl = columnControl;
    }

    public String[] getGridColumn()
    {
        return gridColumn;
    }

    public void setGridColumn(String[] gridColumn)
    {
        this.gridColumn = gridColumn;
    }

    public String[] getMainQueryColumns()
    {
        return mainQueryColumns;
    }

    public void setMainQueryColumns(String[] mainQueryColumns)
    {
        this.mainQueryColumns = mainQueryColumns;
    }

    public String[] getChildQueryColumns()
    {
        return childQueryColumns;
    }

    public void setChildQueryColumns(String[] childQueryColumns)
    {
        this.childQueryColumns = childQueryColumns;
    }

    public String[] getAllColumns()
    {
        return allColumns;
    }

    public void setAllColumns(String[] allColumns)
    {
        this.allColumns = allColumns;
    }

    public String[] getAllColumnTypes()
    {
        return allColumnTypes;
    }

    public void setAllColumnTypes(String[] allColumnTypes)
    {
        this.allColumnTypes = allColumnTypes;
    }

    public String[] getPageFooterColumn()
    {
        return pageFooterColumn;
    }

    public void setPageFooterColumn(String[] pageFooterColumn)
    {
        this.pageFooterColumn = pageFooterColumn;
    }

    public boolean isPageFooter()
    {
        return pageFooter;
    }

    public void setPageFooter(boolean pageFooter)
    {
        this.pageFooter = pageFooter;
    }

    public boolean isGrandTotal()
    {
        return grandTotal;
    }

    public void setGrandTotal(boolean grandTotal)
    {
        this.grandTotal = grandTotal;
    }

    public String getConType()
    {
        return conType;
    }

    public void setConType(String conType)
    {
        this.conType = conType;
    }

    public String getDevServer()
    {
        return devServer;
    }

    public void setDevServer(String devServer)
    {
        this.devServer = devServer;
    }

    public String[] getSelectedColumns()
    {
        return selectedColumns;
    }

    public void setSelectedColumns(String[] selectedColumns)
    {
        this.selectedColumns = selectedColumns;
    }

    public String getMethodName()
    {
        return methodName;
    }

    public void setMethodName(String methodName)
    {
        this.methodName = methodName;
    }

    public boolean isPdf()
    {
        return pdf;
    }

    public void setPdf(boolean pdf)
    {
        this.pdf = pdf;
    }

    public boolean isExcel()
    {
        return excel;
    }

    public void setExcel(boolean excel)
    {
        this.excel = excel;
    }

    public boolean isHtml()
    {
        return html;
    }

    public void setHtml(boolean html)
    {
        this.html = html;
    }

    public String getDetailRefNo()
    {
        return detailRefNo;
    }

    public void setDetailRefNo(String detailRefNo)
    {
        this.detailRefNo = detailRefNo;
    }

    public boolean isColumns()
    {
        return columns;
    }

    public void setColumns(boolean columns)
    {
        this.columns = columns;
    }

    public int getSrno()
    {
        return srno;
    }

    public void setSrno(int srno)
    {
        this.srno = srno;
    }
}
