/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.business.finstudio.finreport;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author njuser
 */
public class FinReportDetailEntityBean
{
    //Project Spec 
    private String projectName;
    private String moduleName;
    private String requestNo;
    private String aliasName;
    private String mainQuery;
    private boolean grouping = false;
    private String groupField;
    private boolean groupFooter = false;
    private String groupFtrCol[];
    private String groupFtrCal[];
    private String alias[];
    private String[] query;
    
    //SRS Spec
    private String problemStatement;
    private String solutionObjective;
    private String existingPractise;
    private String placement;
    //Tab Selection
    private String reportTitle;
    private boolean dateTimePicker = false;
    private boolean reportType = false;
    private boolean export = false;
    private boolean columns = false;
    private boolean chart = false;
    private boolean filter = false;
    //Report Type
    private String rptLabel[];
    private String rptControl[];
    private String rptValidation[];
    private String rptSelNature[];
    private String rptRemarks[];
    //Filter
    private String fltrLabel[];
    private String fltrControl[];
    private String fltrValidation[];
    private String fltrSelNature[];
    private String fltrRemarks[];
    //Export
    private boolean grid = false;
    private boolean pdf = false;
    private boolean excel = false;
    private boolean html = false;
    //Chart
    private boolean noChart = false;
    private boolean pieChart = false;
    private boolean barChart = false;
    private boolean symbolLineChart = false;
    private boolean threedLineChart = false;
    //Columns
    private String reportColumns[];
    private String primaryKey;
    private String empCode;
    private boolean addControl = false;
    private String control[];
    private String col[];
    private String childQueryAlias[];
    private String childQuery[];
    //remaining fields to database
    private String conType;
    private String devServer;
    private boolean headerReq;
    private boolean footerReq;
    private String methodName;
    //in rpt_field for validation
    private String rptMandatory[];
    private String fltrMandatory[];
    //in rpt_data
    private String selectedColumns[];
    private String mainQueryColumns[];
    private String childQueryColumns[];
    private String allColumns[];
    private String allColumnTypes[];
    private String pageFooterColumn[];
    private boolean pageFooter;
    private boolean grandTotal;
    //private String chartName[];
    private List chartName = new ArrayList();
    //report type code properties
    private String rptTxtId[];
    private String rptTxtName[];
    private String rptTxtValue[];
    private String rptTxtTabIndex[];
    private String rptTxtClass[];
    private String rptRbtnChecked[];
    private String rptTxtMaxLen[];
    private String rptRbtnMultiple[];
    private String rptCmbAlign[];
    private String rptTxtSize[];
    private String rptTxtStyle[];
    private String rptRbtnReadOnly[];
    private String rptTxtRows[];
    private String rptTxtCols[];
    private String rptTxtTotalRadio[];
    private String rptTxtRdoValue[];
    private String rptTxtDefRdoValue[];
    //filter code properties
    private String fltrTxtId[];
    private String fltrTxtName[];
    private String fltrTxtValue[];
    private String fltrTxtTabIndex[];
    private String fltrTxtClass[];
    private String fltrRbtnChecked[];
    private String fltrTxtMaxLen[];
    private String fltrRbtnMultiple[];
    private String fltrCmbAlign[];
    private String fltrTxtSize[];
    private String fltrTxtStyle[];
    private String fltrRbtnReadOnly[];
    private String fltrTxtRows[];
    private String fltrTxtCols[];
    private String fltrTxtTotalRadio[];
    private String fltrTxtRdoValue[];
    private String fltrTxtDefRdoValue[];
    private String rptControlPos;
    private String fltrControlPos; 
    private String fltrCmbSource[];
    private String fltrTxtSrcQuery[];
    private String fltrTxtSrcStatic[];
    //for combo fill using webservice
    private String fltrTxtWsdlUrl[];
    private String fltrCmbWsMethod[];
    private String fltrTxtWsCmbValue[];
    private String fltrTxtWsCmbText[];
    private String fltrTxtWsProject[];
    private String fltrTxtWsIntrface[];
    private String fltrTxtWsRetType[];
    private String fltrTxtWsParams[];
    private String fltrTxtWsExps[];    
    //for combo fill using webservice
    private String fltrCmbCommonQuery[];
    //for chart
    private String pieTxtChartTitle;
    private String pieCmbXaxisColumn;
    private String pieCmbYaxisColumn;
    private String barTxtChartTitle;
    private String barTxtXLabel;
    private String barTxtYLabel;
    private String barCmbXaxisColumn;
    private String barCmbYaxisColumn[];
    private String bartxtLegendName[];
    private String lineTxtXLabel;
    private String lineTxtYLabel;
    private String lineTxtChartTitle;
    private String lineCmbXaxisColumn;
    private String lineCmbYaxisColumn[];
    private String linetxtLegendName[];
    private String areaTxtXLabel;
    private String areaTxtYLabel;
    private String areaTxtChartTitle;
    private String areaCmbXaxisColumn;
    private String areaCmbYaxisColumn[];
    private String areatxtLegendName[];
    private int SRNo;
    private String cmbRefNo;
    
    public String getProjectName()
    {
        return projectName;
    }

    public void setProjectName(final String projectName)
    {
        this.projectName = projectName;
    }

    public String getAliasName()
    {
        return aliasName;
    }

    public void setAliasName(final String aliasName)
    {
        this.aliasName = aliasName;
    }

    public String getExistingPractise()
    {
        return existingPractise;
    }

    public void setExistingPractise(final String existingPractise)
    {
        this.existingPractise = existingPractise;
    }

    public String getGroupField()
    {
        return groupField;
    }

    public void setGroupField(final String groupField)
    {
        this.groupField = groupField;
    }

    public boolean isGroupFooter()
    {
        return groupFooter;
    }

    public void setGroupFooter(final boolean groupFooter)
    {
        this.groupFooter = groupFooter;
    }

    public String[] getGroupFtrCal()
    {
        return groupFtrCal;
    }

    public void setGroupFtrCal(final String[] groupFtrCal)
    {
        this.groupFtrCal = groupFtrCal;
    }

    public String[] getGroupFtrCol()
    {
        return groupFtrCol;
    }

    public void setGroupFtrCol(final String[] groupFtrCol)
    {
        this.groupFtrCol = groupFtrCol;
    }

    public boolean isGrouping()
    {
        return grouping;
    }

    public void setGrouping(final boolean grouping)
    {
        this.grouping = grouping;
    }

    public String getMainQuery()
    {
        return mainQuery;
    }

    public void setMainQuery(final String mainQuery)
    {
        this.mainQuery = mainQuery;
    }

    public String getModuleName()
    {
        return moduleName;
    }

    public void setModuleName(final String moduleName)
    {
        this.moduleName = moduleName;
    }

    public String getPlacement()
    {
        return placement;
    }

    public void setPlacement(final String placement)
    {
        this.placement = placement;
    }

    public String getProblemStatement()
    {
        return problemStatement;
    }

    public void setProblemStatement(final String problemStatement)
    {
        this.problemStatement = problemStatement;
    }

    public String getReportTitle()
    {
        return reportTitle;
    }

    public void setReportTitle(final String reportTitle)
    {
        this.reportTitle = reportTitle;
    }

    public String getRequestNo()
    {
        return requestNo;
    }

    public void setRequestNo(final String requestNo)
    {
        this.requestNo = requestNo;
    }

    public String getSolutionObjective()
    {
        return solutionObjective;
    }

    public void setSolutionObjective(final String solutionObjective)
    {
        this.solutionObjective = solutionObjective;
    }

    public boolean isDateTimePicker()
    {
        return dateTimePicker;
    }

    public void setDateTimePicker(final boolean dateTimePicker)
    {
        this.dateTimePicker = dateTimePicker;
    }

    public boolean isAddControl()
    {
        return addControl;
    }

    public void setAddControl(final boolean addControl)
    {
        this.addControl = addControl;
    }

    public boolean isBarChart()
    {
        return barChart;
    }

    public void setBarChart(final boolean barChart)
    {
        this.barChart = barChart;
    }

    public boolean isChart()
    {
        return chart;
    }

    public void setChart(final boolean chart)
    {
        this.chart = chart;
    }

    public String[] getChildQuery()
    {
        return childQuery;
    }

    public String[] getChildQueryAlias()
    {
        return childQueryAlias;
    }

    public String[] getCol()
    {
        return col;
    }

    public void setChildQuery(final String[] childQuery)
    {
        this.childQuery = childQuery;
    }

    public void setChildQueryAlias(final String[] childQueryAlias)
    {
        this.childQueryAlias = childQueryAlias;
    }

    public void setCol(final String[] col)
    {
        this.col = col;
    }

    public void setControl(final String[] control)
    {
        this.control = control;
    }

    public void setFltrControl(final String[] fltrControl)
    {
        this.fltrControl = fltrControl;
    }

    public void setFltrLabel(final String[] fltrLabel)
    {
        this.fltrLabel = fltrLabel;
    }

    public void setFltrRemarks(final String[] fltrRemarks)
    {
        this.fltrRemarks = fltrRemarks;
    }

    public void setFltrSelNature(final String[] fltrSelNature)
    {
        this.fltrSelNature = fltrSelNature;
    }

    public void setFltrValidation(final String[] fltrValidation)
    {
        this.fltrValidation = fltrValidation;
    }

    public void setReportColumns(final String[] reportColumns)
    {
        this.reportColumns = reportColumns;
    }

    public void setRptControl(final String[] rptControl)
    {
        this.rptControl = rptControl;
    }

    public void setRptRemarks(final String[] rptRemarks)
    {
        this.rptRemarks = rptRemarks;
    }

    public void setRptSelNature(final String[] rptSelNature)
    {
        this.rptSelNature = rptSelNature;
    }

    public void setRptValidation(final String[] rptValidation)
    {
        this.rptValidation = rptValidation;
    }

    public boolean isColumns()
    {
        return columns;
    }

    public void setColumns(final boolean columns)
    {
        this.columns = columns;
    }

    public String[] getControl()
    {
        return control;
    }

    public String getEmpCode()
    {
        return empCode;
    }

    public void setEmpCode(final String empCode)
    {
        this.empCode = empCode;
    }

    public boolean isExcel()
    {
        return excel;
    }

    public void setExcel(final boolean excel)
    {
        this.excel = excel;
    }

    public boolean isExport()
    {
        return export;
    }

    public void setExport(final boolean export)
    {
        this.export = export;
    }

    public boolean isFilter()
    {
        return filter;
    }

    public void setFilter(final boolean filter)
    {
        this.filter = filter;
    }

    public String[] getFltrControl()
    {
        return fltrControl;
    }

    public String[] getFltrLabel()
    {
        return fltrLabel;
    }

    public String[] getFltrRemarks()
    {
        return fltrRemarks;
    }

    public String[] getFltrSelNature()
    {
        return fltrSelNature;
    }

    public String[] getFltrValidation()
    {
        return fltrValidation;
    }

    public boolean isGrid()
    {
        return grid;
    }

    public void setGrid(final boolean grid)
    {
        this.grid = grid;
    }


    public boolean isNoChart()
    {
        return noChart;
    }

    public void setNoChart(final boolean noChart)
    {
        this.noChart = noChart;
    }

    public boolean isPdf()
    {
        return pdf;
    }

    public void setPdf(final boolean pdf)
    {
        this.pdf = pdf;
    }

    public boolean isPieChart()
    {
        return pieChart;
    }

    public void setPieChart(final boolean pieChart)
    {
        this.pieChart = pieChart;
    }

    public String getPrimaryKey()
    {
        return primaryKey;
    }

    public void setPrimaryKey(final String primaryKey)
    {
        this.primaryKey = primaryKey;
    }

    public String[] getReportColumns()
    {
        return reportColumns;
    }

    public boolean isReportType()
    {
        return reportType;
    }

    public void setReportType(final boolean reportType)
    {
        this.reportType = reportType;
    }

    public String[] getRptControl()
    {
        return rptControl;
    }

    public String[] getRptLabel()
    {
        return rptLabel;
    }

    public void setRptLabel(final String[] rptLabel)
    {
        this.rptLabel = rptLabel;
    }

    public String[] getRptRemarks()
    {
        return rptRemarks;
    }

    public String[] getRptSelNature()
    {
        return rptSelNature;
    }

    public String[] getRptValidation()
    {
        return rptValidation;
    }

    public boolean isSymbolLineChart()
    {
        return symbolLineChart;
    }

    public void setSymbolLineChart(final boolean symbolLineChart)
    {
        this.symbolLineChart = symbolLineChart;
    }

    public boolean isThreedLineChart()
    {
        return threedLineChart;
    }

    public void setThreedLineChart(final boolean threedLineChart)
    {
        this.threedLineChart = threedLineChart;
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

    public boolean isHeaderReq()
    {
        return headerReq;
    }

    public void setHeaderReq(boolean headerReq)
    {
        this.headerReq = headerReq;
    }

    public boolean isFooterReq()
    {
        return footerReq;
    }

    public void setFooterReq(boolean footerReq)
    {
        this.footerReq = footerReq;
    }

    public String getMethodName()
    {
        return methodName;
    }

    public void setMethodName(String methodName)
    {
        this.methodName = methodName;
    }

    public String[] getRptMandatory()
    {
        return rptMandatory;
    }

    public void setRptMandatory(String[] rptMandatory)
    {
        this.rptMandatory = rptMandatory;
    }

    public String[] getFltrMandatory()
    {
        return fltrMandatory;
    }

    public void setFltrMandatory(String[] fltrMandatory)
    {
        this.fltrMandatory = fltrMandatory;
    }

    public String[] getSelectedColumns()
    {
        return selectedColumns;
    }

    public void setSelectedColumns(String[] selectedColumns)
    {
        this.selectedColumns = selectedColumns;
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

    public List getChartName()
    {
        return chartName;
    }

    public void setChartName(String chartName)
    {
        this.chartName.add(chartName);
    }

    public String[] getRptTxtId()
    {
        return rptTxtId;
    }

    public void setRptTxtId(String[] rptTxtId)
    {
        this.rptTxtId = rptTxtId;
    }

    public String[] getRptTxtName()
    {
        return rptTxtName;
    }

    public void setRptTxtName(String[] rptTxtName)
    {
        this.rptTxtName = rptTxtName;
    }

    public String[] getRptTxtValue()
    {
        return rptTxtValue;
    }

    public void setRptTxtValue(String[] rptTxtValue)
    {
        this.rptTxtValue = rptTxtValue;
    }

    public String[] getRptTxtTabIndex()
    {
        return rptTxtTabIndex;
    }

    public void setRptTxtTabIndex(String[] rptTxtTabIndex)
    {
        this.rptTxtTabIndex = rptTxtTabIndex;
    }

    public String[] getRptTxtClass()
    {
        return rptTxtClass;
    }

    public void setRptTxtClass(String[] rptTxtClass)
    {
        this.rptTxtClass = rptTxtClass;
    }

    public String[] getRptRbtnChecked()
    {
        return rptRbtnChecked;
    }

    public void setRptRbtnChecked(String[] rptRbtnChecked)
    {
        this.rptRbtnChecked = rptRbtnChecked;
    }

    public String[] getRptTxtMaxLen()
    {
        return rptTxtMaxLen;
    }

    public void setRptTxtMaxLen(String[] rptTxtMaxLen)
    {
        this.rptTxtMaxLen = rptTxtMaxLen;
    }

    public String[] getRptRbtnMultiple()
    {
        return rptRbtnMultiple;
    }

    public void setRptRbtnMultiple(String[] rptRbtnMultiple)
    {
        this.rptRbtnMultiple = rptRbtnMultiple;
    }

    public String[] getRptCmbAlign()
    {
        return rptCmbAlign;
    }

    public void setRptCmbAlign(String[] rptCmbAlign)
    {
        this.rptCmbAlign = rptCmbAlign;
    }

    public String[] getRptTxtSize()
    {
        return rptTxtSize;
    }

    public void setRptTxtSize(String[] rptTxtSize)
    {
        this.rptTxtSize = rptTxtSize;
    }

    public String[] getRptTxtStyle()
    {
        return rptTxtStyle;
    }

    public void setRptTxtStyle(String[] rptTxtStyle)
    {
        this.rptTxtStyle = rptTxtStyle;
    }

    public String[] getRptRbtnReadOnly()
    {
        return rptRbtnReadOnly;
    }

    public void setRptRbtnReadOnly(String[] rptRbtnReadOnly)
    {
        this.rptRbtnReadOnly = rptRbtnReadOnly;
    }

    public String[] getRptTxtRows()
    {
        return rptTxtRows;
    }

    public void setRptTxtRows(String[] rptTxtRows)
    {
        this.rptTxtRows = rptTxtRows;
    }

    public String[] getRptTxtCols()
    {
        return rptTxtCols;
    }

    public void setRptTxtCols(String[] rptTxtCols)
    {
        this.rptTxtCols = rptTxtCols;
    }

    public String[] getRptTxtTotalRadio()
    {
        return rptTxtTotalRadio;
    }

    public void setRptTxtTotalRadio(String[] rptTxtTotalRadio)
    {
        this.rptTxtTotalRadio = rptTxtTotalRadio;
    }

    public String[] getRptTxtRdoValue()
    {
        return rptTxtRdoValue;
    }

    public void setRptTxtRdoValue(String[] rptTxtRdoValue)
    {
        this.rptTxtRdoValue = rptTxtRdoValue;
    }

    public String[] getRptTxtDefRdoValue()
    {
        return rptTxtDefRdoValue;
    }

    public void setRptTxtDefRdoValue(String[] rptTxtDefRdoValue)
    {
        this.rptTxtDefRdoValue = rptTxtDefRdoValue;
    }

    public String[] getFltrTxtId()
    {
        return fltrTxtId;
    }

    public void setFltrTxtId(String[] fltrTxtId)
    {
        this.fltrTxtId = fltrTxtId;
    }

    public String[] getFltrTxtName()
    {
        return fltrTxtName;
    }

    public void setFltrTxtName(String[] fltrTxtName)
    {
        this.fltrTxtName = fltrTxtName;
    }

    public String[] getFltrTxtValue()
    {
        return fltrTxtValue;
    }

    public void setFltrTxtValue(String[] fltrTxtValue)
    {
        this.fltrTxtValue = fltrTxtValue;
    }

    public String[] getFltrTxtTabIndex()
    {
        return fltrTxtTabIndex;
    }

    public void setFltrTxtTabIndex(String[] fltrTxtTabIndex)
    {
        this.fltrTxtTabIndex = fltrTxtTabIndex;
    }

    public String[] getFltrTxtClass()
    {
        return fltrTxtClass;
    }

    public void setFltrTxtClass(String[] fltrTxtClass)
    {
        this.fltrTxtClass = fltrTxtClass;
    }

    public String[] getFltrRbtnChecked()
    {
        return fltrRbtnChecked;
    }

    public void setFltrRbtnChecked(String[] fltrRbtnChecked)
    {
        this.fltrRbtnChecked = fltrRbtnChecked;
    }

    public String[] getFltrTxtMaxLen()
    {
        return fltrTxtMaxLen;
    }

    public void setFltrTxtMaxLen(String[] fltrTxtMaxLen)
    {
        this.fltrTxtMaxLen = fltrTxtMaxLen;
    }

    public String[] getFltrRbtnMultiple()
    {
        return fltrRbtnMultiple;
    }

    public void setFltrRbtnMultiple(String[] fltrRbtnMultiple)
    {
        this.fltrRbtnMultiple = fltrRbtnMultiple;
    }

    public String[] getFltrCmbAlign()
    {
        return fltrCmbAlign;
    }

    public void setFltrCmbAlign(String[] fltrCmbAlign)
    {
        this.fltrCmbAlign = fltrCmbAlign;
    }

    public String[] getFltrTxtSize()
    {
        return fltrTxtSize;
    }

    public void setFltrTxtSize(String[] fltrTxtSize)
    {
        this.fltrTxtSize = fltrTxtSize;
    }

    public String[] getFltrTxtStyle()
    {
        return fltrTxtStyle;
    }

    public void setFltrTxtStyle(String[] fltrTxtStyle)
    {
        this.fltrTxtStyle = fltrTxtStyle;
    }

    public String[] getFltrRbtnReadOnly()
    {
        return fltrRbtnReadOnly;
    }

    public void setFltrRbtnReadOnly(String[] fltrRbtnReadOnly)
    {
        this.fltrRbtnReadOnly = fltrRbtnReadOnly;
    }

    public String[] getFltrTxtRows()
    {
        return fltrTxtRows;
    }

    public void setFltrTxtRows(String[] fltrTxtRows)
    {
        this.fltrTxtRows = fltrTxtRows;
    }

    public String[] getFltrTxtCols()
    {
        return fltrTxtCols;
    }

    public void setFltrTxtCols(String[] fltrTxtCols)
    {
        this.fltrTxtCols = fltrTxtCols;
    }

    public String[] getFltrTxtTotalRadio()
    {
        return fltrTxtTotalRadio;
    }

    public void setFltrTxtTotalRadio(String[] fltrTxtTotalRadio)
    {
        this.fltrTxtTotalRadio = fltrTxtTotalRadio;
    }

    public String[] getFltrTxtRdoValue()
    {
        return fltrTxtRdoValue;
    }

    public void setFltrTxtRdoValue(String[] fltrTxtRdoValue)
    {
        this.fltrTxtRdoValue = fltrTxtRdoValue;
    }

    public String[] getFltrTxtDefRdoValue()
    {
        return fltrTxtDefRdoValue;
    }

    public void setFltrTxtDefRdoValue(String[] fltrTxtDefRdoValue)
    {
        this.fltrTxtDefRdoValue = fltrTxtDefRdoValue;
    }

    public String getRptControlPos()
    {
        return rptControlPos;
    }

    public void setRptControlPos(String rptControlPos)
    {
        this.rptControlPos = rptControlPos;
    }

    public String getFltrControlPos()
    {
        return fltrControlPos;
    }

    public void setFltrControlPos(String fltrControlPos)
    {
        this.fltrControlPos = fltrControlPos;
    }

    public String[] getFltrCmbSource()
    {
        return fltrCmbSource;
    }

    public void setFltrCmbSource(String[] fltrCmbSource)
    {
        this.fltrCmbSource = fltrCmbSource;
    }

    public String[] getFltrTxtSrcQuery()
    {
        return fltrTxtSrcQuery;
    }

    public void setFltrTxtSrcQuery(String[] fltrTxtSrcQuery)
    {
        this.fltrTxtSrcQuery = fltrTxtSrcQuery;
    }

    public String[] getFltrTxtSrcStatic()
    {
        return fltrTxtSrcStatic;
    }

    public void setFltrTxtSrcStatic(String[] fltrTxtSrcStatic)
    {
        this.fltrTxtSrcStatic = fltrTxtSrcStatic;
    }

    public String getPieTxtChartTitle()
    {
        return pieTxtChartTitle;
    }

    public void setPieTxtChartTitle(String pieTxtChartTitle)
    {
        this.pieTxtChartTitle = pieTxtChartTitle;
    }

    public String getPieCmbXaxisColumn()
    {
        return pieCmbXaxisColumn;
    }

    public void setPieCmbXaxisColumn(String pieCmbXaxisColumn)
    {
        this.pieCmbXaxisColumn = pieCmbXaxisColumn;
    }

    public String getPieCmbYaxisColumn()
    {
        return pieCmbYaxisColumn;
    }

    public void setPieCmbYaxisColumn(String pieCmbYaxisColumn)
    {
        this.pieCmbYaxisColumn = pieCmbYaxisColumn;
    }

    public String getBarTxtChartTitle()
    {
        return barTxtChartTitle;
    }

    public void setBarTxtChartTitle(String barTxtChartTitle)
    {
        this.barTxtChartTitle = barTxtChartTitle;
    }

    public String getBarTxtXLabel()
    {
        return barTxtXLabel;
    }

    public void setBarTxtXLabel(String barTxtXLabel)
    {
        this.barTxtXLabel = barTxtXLabel;
    }

    public String getBarTxtYLabel()
    {
        return barTxtYLabel;
    }

    public void setBarTxtYLabel(String barTxtYLabel)
    {
        this.barTxtYLabel = barTxtYLabel;
    }

    public String getBarCmbXaxisColumn()
    {
        return barCmbXaxisColumn;
    }

    public void setBarCmbXaxisColumn(String barCmbXaxisColumn)
    {
        this.barCmbXaxisColumn = barCmbXaxisColumn;
    }

    public String[] getBarCmbYaxisColumn()
    {
        return barCmbYaxisColumn;
    }

    public void setBarCmbYaxisColumn(String[] barCmbYaxisColumn)
    {
        this.barCmbYaxisColumn = barCmbYaxisColumn;
    }

    public String[] getBartxtLegendName()
    {
        return bartxtLegendName;
    }

    public void setBartxtLegendName(String[] bartxtLegendName)
    {
        this.bartxtLegendName = bartxtLegendName;
    }

    public String getLineTxtXLabel()
    {
        return lineTxtXLabel;
    }

    public void setLineTxtXLabel(String lineTxtXLabel)
    {
        this.lineTxtXLabel = lineTxtXLabel;
    }

    public String getLineTxtYLabel()
    {
        return lineTxtYLabel;
    }

    public void setLineTxtYLabel(String lineTxtYLabel)
    {
        this.lineTxtYLabel = lineTxtYLabel;
    }

    public String getLineTxtChartTitle()
    {
        return lineTxtChartTitle;
    }

    public void setLineTxtChartTitle(String lineTxtChartTitle)
    {
        this.lineTxtChartTitle = lineTxtChartTitle;
    }

    public String getLineCmbXaxisColumn()
    {
        return lineCmbXaxisColumn;
    }

    public void setLineCmbXaxisColumn(String lineCmbXaxisColumn)
    {
        this.lineCmbXaxisColumn = lineCmbXaxisColumn;
    }

    public String[] getLineCmbYaxisColumn()
    {
        return lineCmbYaxisColumn;
    }

    public void setLineCmbYaxisColumn(String[] lineCmbYaxisColumn)
    {
        this.lineCmbYaxisColumn = lineCmbYaxisColumn;
    }

    public String[] getLinetxtLegendName()
    {
        return linetxtLegendName;
    }

    public void setLinetxtLegendName(String[] linetxtLegendName)
    {
        this.linetxtLegendName = linetxtLegendName;
    }

    public String getAreaTxtXLabel()
    {
        return areaTxtXLabel;
    }

    public void setAreaTxtXLabel(String areaTxtXLabel)
    {
        this.areaTxtXLabel = areaTxtXLabel;
    }

    public String getAreaTxtYLabel()
    {
        return areaTxtYLabel;
    }

    public void setAreaTxtYLabel(String areaTxtYLabel)
    {
        this.areaTxtYLabel = areaTxtYLabel;
    }

    public String getAreaTxtChartTitle()
    {
        return areaTxtChartTitle;
    }

    public void setAreaTxtChartTitle(String areaTxtChartTitle)
    {
        this.areaTxtChartTitle = areaTxtChartTitle;
    }

    public String getAreaCmbXaxisColumn()
    {
        return areaCmbXaxisColumn;
    }

    public void setAreaCmbXaxisColumn(String areaCmbXaxisColumn)
    {
        this.areaCmbXaxisColumn = areaCmbXaxisColumn;
    }

    public String[] getAreaCmbYaxisColumn()
    {
        return areaCmbYaxisColumn;
    }

    public void setAreaCmbYaxisColumn(String[] areaCmbYaxisColumn)
    {
        this.areaCmbYaxisColumn = areaCmbYaxisColumn;
    }

    public String[] getAreatxtLegendName()
    {
        return areatxtLegendName;
    }

    public void setAreatxtLegendName(String[] areatxtLegendName)
    {
        this.areatxtLegendName = areatxtLegendName;
    }

    public int getSRNo()
    {
        return SRNo;
    }

    public void setSRNo(int SRNo)
    {
        this.SRNo = SRNo;
    }   

    public String[] getAlias()
    {
        return alias;
    }

    public void setAlias(String[] alias)
    {
        this.alias = alias;
    }

    public String[] getQuery()
    {
        return query;
    }

    public void setQuery(String[] query)
    {
        this.query = query;
    }
    
    public boolean isHtml()
    {
        return html;
    }

    public void setHtml(boolean html)
    {
        this.html = html;
    }  

    public String getCmbRefNo()
    {
        return cmbRefNo;
    }

    public void setCmbRefNo(String cmbRefNo)
    {
        this.cmbRefNo = cmbRefNo;
    }   

    public String[] getFltrTxtWsdlUrl()
    {
        return fltrTxtWsdlUrl;
    }

    public void setFltrTxtWsdlUrl(String[] fltrTxtWsdlUrl)
    {
        this.fltrTxtWsdlUrl = fltrTxtWsdlUrl;
    }

    public String[] getFltrCmbWsMethod()
    {
        return fltrCmbWsMethod;
    }

    public void setFltrCmbWsMethod(String[] fltrCmbWsMethod)
    {
        this.fltrCmbWsMethod = fltrCmbWsMethod;
    }

    public String[] getFltrTxtWsCmbValue()
    {
        return fltrTxtWsCmbValue;
    }

    public void setFltrTxtWsCmbValue(String[] fltrTxtWsCmbValue)
    {
        this.fltrTxtWsCmbValue = fltrTxtWsCmbValue;
    }

    public String[] getFltrTxtWsCmbText()
    {
        return fltrTxtWsCmbText;
    }

    public void setFltrTxtWsCmbText(String[] fltrTxtWsCmbText)
    {
        this.fltrTxtWsCmbText = fltrTxtWsCmbText;
    }

    public String[] getFltrTxtWsProject()
    {
        return fltrTxtWsProject;
    }

    public void setFltrTxtWsProject(String[] fltrTxtWsProject)
    {
        this.fltrTxtWsProject = fltrTxtWsProject;
    }

    public String[] getFltrTxtWsIntrface()
    {
        return fltrTxtWsIntrface;
    }

    public void setFltrTxtWsIntrface(String[] fltrTxtWsIntrface)
    {
        this.fltrTxtWsIntrface = fltrTxtWsIntrface;
    }

    public String[] getFltrTxtWsRetType()
    {
        return fltrTxtWsRetType;
    }

    public void setFltrTxtWsRetType(String[] fltrTxtWsRetType)
    {
        this.fltrTxtWsRetType = fltrTxtWsRetType;
    }

    public String[] getFltrTxtWsParams()
    {
        return fltrTxtWsParams;
    }

    public void setFltrTxtWsParams(String[] fltrTxtWsParams)
    {
        this.fltrTxtWsParams = fltrTxtWsParams;
    }

    public String[] getFltrTxtWsExps()
    {
        return fltrTxtWsExps;
    }

    public void setFltrTxtWsExps(String[] fltrTxtWsExps)
    {
        this.fltrTxtWsExps = fltrTxtWsExps;
    }  

    public String[] getFltrCmbCommonQuery()
    {
        return fltrCmbCommonQuery;
    }

    public void setFltrCmbCommonQuery(String[] fltrCmbCommonQuery)
    {
        this.fltrCmbCommonQuery = fltrCmbCommonQuery;
    }
}
