/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.business.finstudio.finreport;
/**
 *
 * @author njuser
 */ 
public class FinReportEntityBean { 
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
    private boolean hmtl = false;
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

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(final String projectName) {
        this.projectName = projectName;
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(final String aliasName) {
        this.aliasName = aliasName;
    }

    public String getExistingPractise() {
        return existingPractise;
    }

    public void setExistingPractise(final String existingPractise) {
        this.existingPractise = existingPractise;
    }

    public String getGroupField() {
        return groupField;
    }

    public void setGroupField(final String groupField) {
        this.groupField = groupField;
    }

    public boolean isGroupFooter() {
        return groupFooter;
    }

    public void setGroupFooter(final boolean groupFooter) {
        this.groupFooter = groupFooter;
    }

    public String[] getGroupFtrCal() {
        return groupFtrCal;
    }

    public void setGroupFtrCal(final String[] groupFtrCal) {
        this.groupFtrCal = groupFtrCal;
    }

    public String[] getGroupFtrCol() {
        return groupFtrCol;
    }

    public void setGroupFtrCol(final String[] groupFtrCol) {
        this.groupFtrCol = groupFtrCol;
    }

    public boolean isGrouping() {
        return grouping;
    }

    public void setGrouping(final boolean grouping) {
        this.grouping = grouping;
    }

    public String getMainQuery() {
        return mainQuery;
    }

    public void setMainQuery(final String mainQuery) {
        this.mainQuery = mainQuery;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(final String moduleName) {
        this.moduleName = moduleName;
    }

    public String getPlacement() {
        return placement;
    }

    public void setPlacement(final String placement) {
        this.placement = placement;
    }

    public String getProblemStatement() {
        return problemStatement;
    }

    public void setProblemStatement(final String problemStatement) {
        this.problemStatement = problemStatement;
    }

    public String getReportTitle() {
        return reportTitle;
    }

    public void setReportTitle(final String reportTitle) {
        this.reportTitle = reportTitle;
    }

    public String getRequestNo() {
        return requestNo;
    }

    public void setRequestNo(final String requestNo) {
        this.requestNo = requestNo;
    }

    public String getSolutionObjective() {
        return solutionObjective;
    }

    public void setSolutionObjective(final String solutionObjective) {
        this.solutionObjective = solutionObjective;
    }

    public boolean isDateTimePicker() {
        return dateTimePicker;
    }

    public void setDateTimePicker(final boolean dateTimePicker) {
        this.dateTimePicker = dateTimePicker;
    }

    public boolean isAddControl() {
        return addControl;
    }

    public void setAddControl(final boolean addControl) {
        this.addControl = addControl;
    }

    public boolean isBarChart() {
        return barChart;
    }

    public void setBarChart(final boolean barChart) {
        this.barChart = barChart;
    }

    public boolean isChart() {
        return chart;
    }

    public void setChart(final boolean chart) {
        this.chart = chart;
    }

    public String[] getChildQuery() {
        return childQuery;
    }

    public String[] getChildQueryAlias() {
        return childQueryAlias;
    }

    public String[] getCol() {
        return col;
    }

    public void setChildQuery(final String[] childQuery) {
        this.childQuery = childQuery;
    }

    public void setChildQueryAlias(final String[] childQueryAlias) {
        this.childQueryAlias = childQueryAlias;
    }

    public void setCol(final String[] col) {
        this.col = col;
    }

    public void setControl(final String[] control) {
        this.control = control;
    }

    public void setFltrControl(final String[] fltrControl) {
        this.fltrControl = fltrControl;
    }

    public void setFltrLabel(final String[] fltrLabel) {
        this.fltrLabel = fltrLabel;
    }

    public void setFltrRemarks(final String[] fltrRemarks) {
        this.fltrRemarks = fltrRemarks;
    }

    public void setFltrSelNature(final String[] fltrSelNature) {
        this.fltrSelNature = fltrSelNature;
    }

    public void setFltrValidation(final String[] fltrValidation) {
        this.fltrValidation = fltrValidation;
    }

    public void setReportColumns(final String[] reportColumns) {
        this.reportColumns = reportColumns;
    }

    public void setRptControl(final String[] rptControl) {
        this.rptControl = rptControl;
    }

    public void setRptRemarks(final String[] rptRemarks) {
        this.rptRemarks = rptRemarks;
    } 

    public void setRptSelNature(final String[] rptSelNature) {
        this.rptSelNature = rptSelNature;
    }

    public void setRptValidation(final String[] rptValidation) {
        this.rptValidation = rptValidation;
    }

    public boolean isColumns() {
        return columns;
    }

    public void setColumns(final boolean columns) {
        this.columns = columns;
    }

    public String[] getControl() {
        return control;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(final String empCode) {
        this.empCode = empCode;
    }

    public boolean isExcel() {
        return excel;
    }

    public void setExcel(final boolean excel) {
        this.excel = excel;
    }

    public boolean isExport() {
        return export;
    }

    public void setExport(final boolean export) {
        this.export = export;
    }

    public boolean isFilter() {
        return filter;
    }

    public void setFilter(final boolean filter) {
        this.filter = filter;
    }

    public String[] getFltrControl() {
        return fltrControl;
    }

    public String[] getFltrLabel() {
        return fltrLabel;
    }

    public String[] getFltrRemarks() {
        return fltrRemarks;
    }

    public String[] getFltrSelNature() {
        return fltrSelNature;
    }

    public String[] getFltrValidation() {
        return fltrValidation;
    }

    public boolean isGrid() {
        return grid;
    }

    public void setGrid(final boolean grid) {
        this.grid = grid;
    }

    public boolean isHmtl() {
        return hmtl;
    }

    public void setHmtl(final boolean hmtl) {
        this.hmtl = hmtl;
    }

    public boolean isNoChart() {
        return noChart;
    }

    public void setNoChart(final boolean noChart) {
        this.noChart = noChart;
    }

    public boolean isPdf() {
        return pdf;
    }

    public void setPdf(final boolean pdf) {
        this.pdf = pdf;
    }

    public boolean isPieChart() {
        return pieChart;
    }

    public void setPieChart(final boolean pieChart) {
        this.pieChart = pieChart;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(final String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String[] getReportColumns() {
        return reportColumns;
    }

    public boolean isReportType() {
        return reportType;
    }

    public void setReportType(final boolean reportType) {
        this.reportType = reportType;
    }

    public String[] getRptControl() {
        return rptControl;
    }

    public String[] getRptLabel() {
        return rptLabel;
    }

    public void setRptLabel(final String[] rptLabel) {
        this.rptLabel = rptLabel;
    }

    public String[] getRptRemarks() {
        return rptRemarks;
    }

    public String[] getRptSelNature() {
        return rptSelNature;
    }

    public String[] getRptValidation() {
        return rptValidation;
    }

    public boolean isSymbolLineChart() {
        return symbolLineChart;
    }

    public void setSymbolLineChart(final boolean symbolLineChart) {
        this.symbolLineChart = symbolLineChart;
    }

    public boolean isThreedLineChart() {
        return threedLineChart;
    }

    public void setThreedLineChart(final boolean threedLineChart) {
        this.threedLineChart = threedLineChart;
    }
}
