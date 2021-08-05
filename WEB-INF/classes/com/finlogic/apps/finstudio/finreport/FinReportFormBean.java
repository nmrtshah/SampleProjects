/*
 * To change FinReportFormBean template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.apps.finstudio.finreport;

/**
 *
 * @author njuser
 */
public class FinReportFormBean
{
    private String cmbProjectName;
    private String txtModuleName;
    private String txtReqNo;
    private String cmbAliasName[];
    private String txtQuery[];
    private boolean chkGrouping = false;
    private String cmbGroupField;
    private boolean chkGroupFooter = false;
    private String hdncmbGrpFooterColumn[];
    private String hdncmbGrpFooterCalculation[];
    private String txtProblemStmt;
    private String txtSolution;
    private String txtExistingPractice;
    private String txtPlacement;
    private String txtReportTitle;
    private boolean chkDateSelection = false;
    private boolean chkReportType = false;
    private boolean chkFilters = false;
    private boolean chkExport = false;
    private boolean chkChart = false;
    private boolean chkColumns = false;
    //report type
    private String rptHdnLabel[];
    private String rptHdnControl[];
    private String rptHdnValidation[];
    private String rptHdnRemarks[];
    //filter
    private String fltrHdnLabel[];
    private String fltrHdnControl[];
    private String fltrHdnValidation[];
    private String fltrHdnRemarks[];
    //Export
    private boolean chkOnScreen = false;
    private boolean chkPDF = false;
    private boolean chkExcel = false;
    private boolean chkHtml = false;
    //Chart
    private boolean noChart = false;
    private boolean pieChart = false;
    private boolean barChart = false;
    private boolean lineChart = false;
    private boolean areaChart = false;
    //Columns
    private String cmbTabColumn[];
    //for property file for ReportType
    private String rptHdnTxtId[];
    private String rptHdnTxtName[];
    private String rptHdnTxtValue[];
    private String rptHdnTxtTabindex[];
    private String rptHdnTxtClass[];
    private String rptHdnRbtnChecked[];
    private String rptHdnTxtMaxLen[];
    private String rptHdnRbtnMultiple[];
    private String rptHdnCmbAlign[];
    private String rptHdnTxtSize[];
    private String rptHdnTxtStyle[];
    private String rptHdnRbtnReadonly[];
    private String rptHdnTxtRows[];
    private String rptHdnTxtCols[];
    private String rptHdnTxtTotalRdo[];
    private String rptHdnTxtRdoVal[];
    private String rptHdnTxtRdoDefVal[];
    //for property file for Filter
    private String fltrHdnTxtId[];
    private String fltrHdnTxtName[];
    private String fltrHdnTxtValue[];
    private String fltrHdnTxtTabindex[];
    private String fltrHdnTxtClass[];
    private String fltrHdnRbtnChecked[];
    private String fltrHdnTxtMaxLen[];
    private String fltrHdnRbtnMultiple[];
    private String fltrHdnCmbAlign[];
    private String fltrHdnTxtSize[];
    private String fltrHdnTxtStyle[];
    private String fltrHdnRbtnReadonly[];
    private String fltrHdnTxtRows[];
    private String fltrHdnTxtCols[];
    private String fltrHdnTxtTotalRdo[];
    private String fltrHdnTxtRdoVal[];
    private String fltrHdnTxtRdoDefVal[];
    //srno
    private int SRNo;
    private String hdnAllColumns[];
    private String hdnAllDataTypes[];
    //for pagefooter2
    private boolean chkPageFooter = false;
    private boolean chkGrandTotal = false;
    private String hdnPageFooterColumns[];
    private String cmbControlPos;
    private String cmbRptControlPos;
    private String cmbPrimaryKey;
    private String emp_code;
    private boolean chkAddControl = false;
    private String hdncmbColumnGrid[];
    private String hdncmbColumnControl[];
    private String queryIndex;
    private String cmbMainQueryColumn[];
    private String cmbChildQueryColumns[];
    //for connection
    private String rdoConType;
    private String cmbDevServer;
    private boolean chkHeaderReq;
    private boolean chkFooterReq;
    private String txtMethodNm;
    //for combofill
    private String fltrTxtSrcQuery;
    private String fltrTxtSrcStatic;
    private String fltrHdnCmbSource[];
    private String fltrHdnTxtSrcQuery[];
    private String fltrHdnTxtSrcStatic[];
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
    //for validation
    private String rptHdnChkMandatory[];
    private String fltrHdnChkMandatory[];
    //for summary report
    private String cmbRefNo;
    //for combo fill using webservice
    private String fltrTxtWsdlUrl;
    private String fltrCmbWsMethod;
    private String fltrWSCmbValue;
    private String fltrWSCmbText;
    private String fltrTxtWsProject;
    private String fltrTxtWsIntrface;
    private String fltrHdnTxtWsdlUrl[];
    private String fltrHdnCmbWsMethod[];
    private String fltrHdnTxtWsCmbValue[];
    private String fltrHdnTxtWsCmbText[];
    private String fltrHdnTxtWsProject[];
    private String fltrHdnTxtWsIntrface[];
    private String fltrHdnTxtWsRetType[];
    private String fltrHdnTxtWsParams[];
    private String fltrHdnTxtWsExps[];
    //for combo fill using Common Combo 
    private String fltrHdnCmbCommonQuery[];

    public String[] getFltrHdnChkMandatory()
    {
        return fltrHdnChkMandatory;
    }

    public void setFltrHdnChkMandatory(String[] fltrHdnChkMandatory)
    {
        this.fltrHdnChkMandatory = fltrHdnChkMandatory;
    }

    public String[] getRptHdnChkMandatory()
    {
        return rptHdnChkMandatory;
    }

    public void setRptHdnChkMandatory(String[] rptHdnChkMandatory)
    {
        this.rptHdnChkMandatory = rptHdnChkMandatory;
    }

    public String[] getAreatxtLegendName()
    {
        return areatxtLegendName;
    }

    public void setAreatxtLegendName(String[] areatxtLegendName)
    {
        this.areatxtLegendName = areatxtLegendName;
    }

    public String[] getBartxtLegendName()
    {
        return bartxtLegendName;
    }

    public void setBartxtLegendName(String[] bartxtLegendName)
    {
        this.bartxtLegendName = bartxtLegendName;
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

    public String getAreaTxtChartTitle()
    {
        return areaTxtChartTitle;
    }

    public void setAreaTxtChartTitle(String areaTxtChartTitle)
    {
        this.areaTxtChartTitle = areaTxtChartTitle;
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

    public String getBarTxtChartTitle()
    {
        return barTxtChartTitle;
    }

    public void setBarTxtChartTitle(String barTxtChartTitle)
    {
        this.barTxtChartTitle = barTxtChartTitle;
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

    public String getLineTxtChartTitle()
    {
        return lineTxtChartTitle;
    }

    public void setLineTxtChartTitle(String lineTxtChartTitle)
    {
        this.lineTxtChartTitle = lineTxtChartTitle;
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

    public String getPieTxtChartTitle()
    {
        return pieTxtChartTitle;
    }

    public void setPieTxtChartTitle(String pieTxtChartTitle)
    {
        this.pieTxtChartTitle = pieTxtChartTitle;
    }

    public String[] getFltrHdnTxtSrcStatic()
    {
        return fltrHdnTxtSrcStatic;
    }

    public void setFltrHdnTxtSrcStatic(String[] fltrHdnTxtSrcStatic)
    {
        this.fltrHdnTxtSrcStatic = fltrHdnTxtSrcStatic;
    }

    public String getFltrTxtSrcStatic()
    {
        return fltrTxtSrcStatic;
    }

    public void setFltrTxtSrcStatic(String fltrTxtSrcStatic)
    {
        this.fltrTxtSrcStatic = fltrTxtSrcStatic;
    }

    public String getFltrTxtSrcQuery()
    {
        return fltrTxtSrcQuery;
    }

    public void setFltrTxtSrcQuery(String fltrTxtSrcQuery)
    {
        this.fltrTxtSrcQuery = fltrTxtSrcQuery;
    }

    public String[] getFltrHdnTxtSrcQuery()
    {
        return fltrHdnTxtSrcQuery;
    }

    public void setFltrHdnTxtSrcQuery(String[] fltrHdnTxtSrcQuery)
    {
        this.fltrHdnTxtSrcQuery = fltrHdnTxtSrcQuery;
    }

    public void setFltrHdnCmbSource(String[] fltrHdnCmbSource)
    {
        this.fltrHdnCmbSource = fltrHdnCmbSource;
    }

    public String[] getFltrHdnCmbSource()
    {
        return fltrHdnCmbSource;
    }

    public String[] getFltrHdnTxtRdoVal()
    {
        return fltrHdnTxtRdoVal;
    }

    public void setFltrHdnTxtRdoVal(String[] fltrHdnTxtRdoVal)
    {
        this.fltrHdnTxtRdoVal = fltrHdnTxtRdoVal;
    }

    public String[] getFltrHdnTxtRdoDefVal()
    {
        return fltrHdnTxtRdoDefVal;
    }

    public void setFltrHdnTxtRdoDefVal(String[] fltrHdnTxtRdoDefVal)
    {
        this.fltrHdnTxtRdoDefVal = fltrHdnTxtRdoDefVal;
    }

    public String[] getFltrHdnTxtTotalRdo()
    {
        return fltrHdnTxtTotalRdo;
    }

    public void setFltrHdnTxtTotalRdo(String[] fltrHdnTxtTotalRdo)
    {
        this.fltrHdnTxtTotalRdo = fltrHdnTxtTotalRdo;
    }

    public String[] getRptHdnTxtRdoVal()
    {
        return rptHdnTxtRdoVal;
    }

    public void setRptHdnTxtRdoVal(String[] rptHdnTxtRdoVal)
    {
        this.rptHdnTxtRdoVal = rptHdnTxtRdoVal;
    }

    public String[] getRptHdnTxtRdoDefVal()
    {
        return rptHdnTxtRdoDefVal;
    }

    public void setRptHdnTxtRdoDefVal(String[] rptHdnTxtRdoDefVal)
    {
        this.rptHdnTxtRdoDefVal = rptHdnTxtRdoDefVal;
    }

    public String[] getRptHdnTxtTotalRdo()
    {
        return rptHdnTxtTotalRdo;
    }

    public void setRptHdnTxtTotalRdo(String[] rptHdnTxtTotalRdo)
    {
        this.rptHdnTxtTotalRdo = rptHdnTxtTotalRdo;
    }

    public boolean isChkFooterReq()
    {
        return chkFooterReq;
    }

    public void setChkFooterReq(boolean chkFooterReq)
    {
        this.chkFooterReq = chkFooterReq;
    }

    public boolean isChkHeaderReq()
    {
        return chkHeaderReq;
    }

    public void setChkHeaderReq(boolean chkHeaderReq)
    {
        this.chkHeaderReq = chkHeaderReq;
    }

    public String getTxtMethodNm()
    {
        return txtMethodNm;
    }

    public void setTxtMethodNm(String txtMethodNm)
    {
        this.txtMethodNm = txtMethodNm;
    }

    public String getCmbDevServer()
    {
        return cmbDevServer;
    }

    public void setCmbDevServer(final String cmbDevServer)
    {
        this.cmbDevServer = cmbDevServer;
    }

    public String getRdoConType()
    {
        return rdoConType;
    }

    public void setRdoConType(final String rdoConType)
    {
        this.rdoConType = rdoConType;
    }

    public String[] getCmbMainQueryColumn()
    {
        return cmbMainQueryColumn;
    }

    public void setCmbMainQueryColumn(final String[] cmbMainQueryColumn)
    {
        this.cmbMainQueryColumn = cmbMainQueryColumn;
    }

    public String[] getCmbChildQueryColumns()
    {
        return cmbChildQueryColumns;
    }

    public void setCmbChildQueryColumns(final String[] cmbChildQueryColumns)
    {
        this.cmbChildQueryColumns = cmbChildQueryColumns;
    }

    public String getQueryIndex()
    {
        return queryIndex;
    }

    public void setQueryIndex(final String queryIndex)
    {
        this.queryIndex = queryIndex;
    }

    public boolean isChkHtml()
    {
        return chkHtml;
    }

    public void setChkHtml(final boolean chkHtml)
    {
        this.chkHtml = chkHtml;
    }

    public boolean isChkAddControl()
    {
        return chkAddControl;
    }

    public void setChkAddControl(final boolean chkAddControl)
    {
        this.chkAddControl = chkAddControl;
    }

    public String[] getHdncmbColumnControl()
    {
        return hdncmbColumnControl;
    }

    public void setHdncmbColumnControl(String[] hdncmbColumnControl)
    {
        this.hdncmbColumnControl = hdncmbColumnControl;
    }

    public String[] getHdncmbColumnGrid()
    {
        return hdncmbColumnGrid;
    }

    public void setHdncmbColumnGrid(String[] hdncmbColumnGrid)
    {
        this.hdncmbColumnGrid = hdncmbColumnGrid;
    }

    public String getCmbRptControlPos()
    {
        return cmbRptControlPos;
    }

    public void setCmbRptControlPos(final String cmbRptControlPos)
    {
        this.cmbRptControlPos = cmbRptControlPos;
    }

    public String getCmbControlPos()
    {
        return cmbControlPos;
    }

    public void setCmbControlPos(final String cmbControlPos)
    {
        this.cmbControlPos = cmbControlPos;
    }

    public String getCmbPrimaryKey()
    {
        return cmbPrimaryKey;
    }

    public void setCmbPrimaryKey(final String cmbPrimaryKey)
    {
        this.cmbPrimaryKey = cmbPrimaryKey;
    }

    public String[] getHdnAllColumns()
    {
        return hdnAllColumns;
    }

    public void setHdnAllColumns(final String[] hdnAllColumns)
    {
        this.hdnAllColumns = hdnAllColumns;
    }

    public String[] getHdnAllDataTypes()
    {
        return hdnAllDataTypes;
    }

    public void setHdnAllDataTypes(final String[] hdnAllDataTypes)
    {
        this.hdnAllDataTypes = hdnAllDataTypes;
    }

    public boolean isChkGrandTotal()
    {
        return chkGrandTotal;
    }

    public void setChkGrandTotal(final boolean chkGrandTotal)
    {
        this.chkGrandTotal = chkGrandTotal;
    }

    public boolean isChkPageFooter()
    {
        return chkPageFooter;
    }

    public void setChkPageFooter(final boolean chkPageFooter)
    {
        this.chkPageFooter = chkPageFooter;
    }

    public int getSRNo()
    {
        return SRNo;
    }

    public void setSRNo(final int SRNo)
    {
        this.SRNo = SRNo;
    }

    public String getCmbProjectName()
    {
        return cmbProjectName;
    }

    public void setCmbProjectName(String cmbProjectName)
    {
        this.cmbProjectName = cmbProjectName;
    }

    public String[] getFltrHdnCmbAlign()
    {
        return fltrHdnCmbAlign;
    }

    public void setFltrHdnCmbAlign(String[] fltrHdnCmbAlign)
    {
        this.fltrHdnCmbAlign = fltrHdnCmbAlign;
    }

    public String[] getFltrHdnRbtnChecked()
    {
        return fltrHdnRbtnChecked;
    }

    public void setFltrHdnRbtnChecked(String[] fltrHdnRbtnChecked)
    {
        this.fltrHdnRbtnChecked = fltrHdnRbtnChecked;
    }

    public String[] getFltrHdnTxtClass()
    {
        return fltrHdnTxtClass;
    }

    public void setFltrHdnTxtClass(String[] fltrHdnTxtClass)
    {
        this.fltrHdnTxtClass = fltrHdnTxtClass;
    }

    public String[] getFltrHdnTxtCols()
    {
        return fltrHdnTxtCols;
    }

    public void setFltrHdnTxtCols(String[] fltrHdnTxtCols)
    {
        this.fltrHdnTxtCols = fltrHdnTxtCols;
    }

    public String[] getFltrHdnTxtId()
    {
        return fltrHdnTxtId;
    }

    public void setFltrHdnTxtId(String[] fltrHdnTxtId)
    {
        this.fltrHdnTxtId = fltrHdnTxtId;
    }

    public String[] getFltrHdnTxtMaxLen()
    {
        return fltrHdnTxtMaxLen;
    }

    public void setFltrHdnTxtMaxLen(String[] fltrHdnTxtMaxLen)
    {
        this.fltrHdnTxtMaxLen = fltrHdnTxtMaxLen;
    }

    public String[] getFltrHdnTxtName()
    {
        return fltrHdnTxtName;
    }

    public void setFltrHdnTxtName(String[] fltrHdnTxtName)
    {
        this.fltrHdnTxtName = fltrHdnTxtName;
    }

    public String[] getFltrHdnTxtRows()
    {
        return fltrHdnTxtRows;
    }

    public void setFltrHdnTxtRows(String[] fltrHdnTxtRows)
    {
        this.fltrHdnTxtRows = fltrHdnTxtRows;
    }

    public String[] getFltrHdnTxtSize()
    {
        return fltrHdnTxtSize;
    }

    public void setFltrHdnTxtSize(String[] fltrHdnTxtSize)
    {
        this.fltrHdnTxtSize = fltrHdnTxtSize;
    }

    public String[] getFltrHdnTxtStyle()
    {
        return fltrHdnTxtStyle;
    }

    public void setFltrHdnTxtStyle(String[] fltrHdnTxtStyle)
    {
        this.fltrHdnTxtStyle = fltrHdnTxtStyle;
    }

    public String[] getFltrHdnTxtTabindex()
    {
        return fltrHdnTxtTabindex;
    }

    public void setFltrHdnTxtTabindex(String[] fltrHdnTxtTabindex)
    {
        this.fltrHdnTxtTabindex = fltrHdnTxtTabindex;
    }

    public String[] getFltrHdnTxtValue()
    {
        return fltrHdnTxtValue;
    }

    public void setFltrHdnTxtValue(String[] fltrHdnTxtValue)
    {
        this.fltrHdnTxtValue = fltrHdnTxtValue;
    }

    public String[] getRptHdnControl()
    {
        return rptHdnControl;
    }

    public void setRptHdnControl(String[] rptHdnControl)
    {
        this.rptHdnControl = rptHdnControl;
    }

    public String[] getRptHdnLabel()
    {
        return rptHdnLabel;
    }

    public void setRptHdnLabel(String[] rptHdnLabel)
    {
        this.rptHdnLabel = rptHdnLabel;
    }

    public String[] getRptHdnRemarks()
    {
        return rptHdnRemarks;
    }

    public void setRptHdnRemarks(String[] rptHdnRemarks)
    {
        this.rptHdnRemarks = rptHdnRemarks;
    }

    public String[] getRptHdnValidation()
    {
        return rptHdnValidation;
    }

    public void setRptHdnValidation(String[] rptHdnValidation)
    {
        this.rptHdnValidation = rptHdnValidation;
    }

    public String[] getRptHdnCmbAlign()
    {
        return rptHdnCmbAlign;
    }

    public void setRptHdnCmbAlign(String[] rptHdnCmbAlign)
    {
        this.rptHdnCmbAlign = rptHdnCmbAlign;
    }

    public String[] getRptHdnRbtnChecked()
    {
        return rptHdnRbtnChecked;
    }

    public void setRptHdnRbtnChecked(String[] rptHdnRbtnChecked)
    {
        this.rptHdnRbtnChecked = rptHdnRbtnChecked;
    }

    public String[] getRptHdnTxtClass()
    {
        return rptHdnTxtClass;
    }

    public void setRptHdnTxtClass(String[] rptHdnTxtClass)
    {
        this.rptHdnTxtClass = rptHdnTxtClass;
    }

    public String[] getRptHdnTxtCols()
    {
        return rptHdnTxtCols;
    }

    public void setRptHdnTxtCols(String[] rptHdnTxtCols)
    {
        this.rptHdnTxtCols = rptHdnTxtCols;
    }

    public String[] getRptHdnTxtMaxLen()
    {
        return rptHdnTxtMaxLen;
    }

    public void setRptHdnTxtMaxLen(String[] rptHdnTxtMaxLen)
    {
        this.rptHdnTxtMaxLen = rptHdnTxtMaxLen;
    }

    public String[] getRptHdnTxtName()
    {
        return rptHdnTxtName;
    }

    public void setRptHdnTxtName(String[] rptHdnTxtName)
    {
        this.rptHdnTxtName = rptHdnTxtName;
    }

    public String[] getRptHdnTxtRows()
    {
        return rptHdnTxtRows;
    }

    public void setRptHdnTxtRows(String[] rptHdnTxtRows)
    {
        this.rptHdnTxtRows = rptHdnTxtRows;
    }

    public String[] getRptHdnTxtSize()
    {
        return rptHdnTxtSize;
    }

    public void setRptHdnTxtSize(String[] rptHdnTxtSize)
    {
        this.rptHdnTxtSize = rptHdnTxtSize;
    }

    public String[] getRptHdnTxtStyle()
    {
        return rptHdnTxtStyle;
    }

    public void setRptHdnTxtStyle(String[] rptHdnTxtStyle)
    {
        this.rptHdnTxtStyle = rptHdnTxtStyle;
    }

    public String[] getRptHdnTxtTabindex()
    {
        return rptHdnTxtTabindex;
    }

    public void setRptHdnTxtTabindex(String[] rptHdnTxtTabindex)
    {
        this.rptHdnTxtTabindex = rptHdnTxtTabindex;
    }

    public String[] getRptHdnTxtValue()
    {
        return rptHdnTxtValue;
    }

    public void setRptHdnTxtValue(String[] rptHdnTxtValue)
    {
        this.rptHdnTxtValue = rptHdnTxtValue;
    }

    public String[] getRptHdnTxtId()
    {
        return rptHdnTxtId;
    }

    public void setRptHdnTxtId(String[] rptHdnTxtId)
    {
        this.rptHdnTxtId = rptHdnTxtId;
    }

    public String[] getFltrHdnControl()
    {
        return fltrHdnControl;
    }

    public void setFltrHdnControl(String[] fltrHdnControl)
    {
        this.fltrHdnControl = fltrHdnControl;
    }

    public String[] getFltrHdnLabel()
    {
        return fltrHdnLabel;
    }

    public void setFltrHdnLabel(String[] fltrHdnLabel)
    {
        this.fltrHdnLabel = fltrHdnLabel;
    }

    public String[] getFltrHdnRemarks()
    {
        return fltrHdnRemarks;
    }

    public void setFltrHdnRemarks(String[] fltrHdnRemarks)
    {
        this.fltrHdnRemarks = fltrHdnRemarks;
    }

    public String[] getFltrHdnValidation()
    {
        return fltrHdnValidation;
    }

    public void setFltrHdnValidation(String[] fltrHdnValidation)
    {
        this.fltrHdnValidation = fltrHdnValidation;
    }

    public boolean isChkChart()
    {
        return chkChart;
    }

    public void setChkChart(final boolean chkChart)
    {
        this.chkChart = chkChart;
    }

    public boolean isChkColumns()
    {
        return chkColumns;
    }

    public void setChkColumns(final boolean chkColumns)
    {
        this.chkColumns = chkColumns;
    }

    public boolean isChkDateSelection()
    {
        return chkDateSelection;
    }

    public void setChkDateSelection(final boolean chkDateSelection)
    {
        this.chkDateSelection = chkDateSelection;
    }

    public boolean isChkExport()
    {
        return chkExport;
    }

    public void setChkExport(final boolean chkExport)
    {
        this.chkExport = chkExport;
    }

    public boolean isChkFilters()
    {
        return chkFilters;
    }

    public void setChkFilters(final boolean chkFilters)
    {
        this.chkFilters = chkFilters;
    }

    public boolean isChkGroupFooter()
    {
        return chkGroupFooter;
    }

    public void setChkGroupFooter(final boolean chkGroupFooter)
    {
        this.chkGroupFooter = chkGroupFooter;
    }

    public boolean isChkGrouping()
    {
        return chkGrouping;
    }

    public void setChkGrouping(final boolean chkGrouping)
    {
        this.chkGrouping = chkGrouping;
    }

    public boolean isChkReportType()
    {
        return chkReportType;
    }

    public void setChkReportType(final boolean chkReportType)
    {
        this.chkReportType = chkReportType;
    }

    public String getCmbGroupField()
    {
        return cmbGroupField;
    }

    public void setCmbGroupField(final String cmbGroupField)
    {
        this.cmbGroupField = cmbGroupField;
    }

    public String getTxtReqNo()
    {
        return txtReqNo;
    }

    public void setTxtReqNo(final String txtReqNo)
    {
        this.txtReqNo = txtReqNo;
    }

    public String getTxtExistingPractice()
    {
        return txtExistingPractice;
    }

    public void setTxtExistingPractice(final String txtExistingPractice)
    {
        this.txtExistingPractice = txtExistingPractice;
    }

    public String getTxtModuleName()
    {
        return txtModuleName;
    }

    public void setTxtModuleName(final String txtModuleName)
    {
        this.txtModuleName = txtModuleName;
    }

    public String getTxtPlacement()
    {
        return txtPlacement;
    }

    public void setTxtPlacement(final String txtPlacement)
    {
        this.txtPlacement = txtPlacement;
    }

    public String getTxtProblemStmt()
    {
        return txtProblemStmt;
    }

    public void setTxtProblemStmt(final String txtProblemStmt)
    {
        this.txtProblemStmt = txtProblemStmt;
    }

    public String[] getCmbAliasName()
    {
        return cmbAliasName;
    }

    public void setCmbAliasName(final String[] cmbAliasName)
    {
        this.cmbAliasName = cmbAliasName;
    }

    public String[] getTxtQuery()
    {
        return txtQuery;
    }

    public void setTxtQuery(final String[] txtQuery)
    {
        this.txtQuery = txtQuery;
    }

    public String getTxtReportTitle()
    {
        return txtReportTitle;
    }

    public void setTxtReportTitle(final String txtReportTitle)
    {
        this.txtReportTitle = txtReportTitle;
    }

    public String getTxtSolution()
    {
        return txtSolution;
    }

    public void setTxtSolution(final String txtSolution)
    {
        this.txtSolution = txtSolution;
    }

    public String[] getHdncmbGrpFooterCalculation()
    {
        return hdncmbGrpFooterCalculation;
    }

    public void setHdncmbGrpFooterCalculation(String[] hdncmbGrpFooterCalculation)
    {
        this.hdncmbGrpFooterCalculation = hdncmbGrpFooterCalculation;
    }

    public String[] getHdncmbGrpFooterColumn()
    {
        return hdncmbGrpFooterColumn;
    }

    public void setHdncmbGrpFooterColumn(String[] hdncmbGrpFooterColumn)
    {
        this.hdncmbGrpFooterColumn = hdncmbGrpFooterColumn;
    }

    public String[] getCmbTabColumn()
    {
        return cmbTabColumn;
    }

    public void setCmbTabColumn(final String[] cmbTabColumn)
    {
        this.cmbTabColumn = cmbTabColumn;
    }

    public boolean isChkExcel()
    {
        return chkExcel;
    }

    public void setChkExcel(final boolean chkExcel)
    {
        this.chkExcel = chkExcel;
    }

    public boolean isChkOnScreen()
    {
        return chkOnScreen;
    }

    public void setChkOnScreen(final boolean chkOnScreen)
    {
        this.chkOnScreen = chkOnScreen;
    }

    public boolean isChkPDF()
    {
        return chkPDF;
    }

    public void setChkPDF(final boolean chkPDF)
    {
        this.chkPDF = chkPDF;
    }

    public String getEmp_code()
    {
        return emp_code;
    }

    public void setEmp_code(final String emp_code)
    {
        this.emp_code = emp_code;
    }

    public String[] getHdnPageFooterColumns()
    {
        return hdnPageFooterColumns;
    }

    public void setHdnPageFooterColumns(final String[] hdnPageFooterColumns)
    {
        this.hdnPageFooterColumns = hdnPageFooterColumns;
    }

    public boolean isAreaChart()
    {
        return areaChart;
    }

    public void setAreaChart(boolean areaChart)
    {
        this.areaChart = areaChart;
    }

    public boolean isBarChart()
    {
        return barChart;
    }

    public void setBarChart(boolean barChart)
    {
        this.barChart = barChart;
    }

    public boolean isLineChart()
    {
        return lineChart;
    }

    public void setLineChart(boolean lineChart)
    {
        this.lineChart = lineChart;
    }

    public boolean isNoChart()
    {
        return noChart;
    }

    public void setNoChart(boolean noChart)
    {
        this.noChart = noChart;
    }

    public boolean isPieChart()
    {
        return pieChart;
    }

    public void setPieChart(boolean pieChart)
    {
        this.pieChart = pieChart;
    }

    public String[] getFltrHdnRbtnMultiple()
    {
        return fltrHdnRbtnMultiple;
    }

    public void setFltrHdnRbtnMultiple(String[] fltrHdnRbtnMultiple)
    {
        this.fltrHdnRbtnMultiple = fltrHdnRbtnMultiple;
    }

    public String[] getFltrHdnRbtnReadonly()
    {
        return fltrHdnRbtnReadonly;
    }

    public void setFltrHdnRbtnReadonly(String[] fltrHdnRbtnReadonly)
    {
        this.fltrHdnRbtnReadonly = fltrHdnRbtnReadonly;
    }

    public String[] getRptHdnRbtnMultiple()
    {
        return rptHdnRbtnMultiple;
    }

    public void setRptHdnRbtnMultiple(String[] rptHdnRbtnMultiple)
    {
        this.rptHdnRbtnMultiple = rptHdnRbtnMultiple;
    }

    public String[] getRptHdnRbtnReadonly()
    {
        return rptHdnRbtnReadonly;
    }

    public void setRptHdnRbtnReadonly(String[] rptHdnRbtnReadonly)
    {
        this.rptHdnRbtnReadonly = rptHdnRbtnReadonly;
    }

    public String getCmbRefNo()
    {
        return cmbRefNo;
    }

    public void setCmbRefNo(String cmbRefNo)
    {
        this.cmbRefNo = cmbRefNo;
    }

    public String getFltrTxtWsdlUrl()
    {
        return fltrTxtWsdlUrl;
    }

    public void setFltrTxtWsdlUrl(String fltrTxtWsdlUrl)
    {
        this.fltrTxtWsdlUrl = fltrTxtWsdlUrl;
    }

    public String getFltrCmbWsMethod()
    {
        return fltrCmbWsMethod;
    }

    public void setFltrCmbWsMethod(String fltrCmbWsMethod)
    {
        this.fltrCmbWsMethod = fltrCmbWsMethod;
    }

    public String getFltrWSCmbValue()
    {
        return fltrWSCmbValue;
    }

    public void setFltrWSCmbValue(String fltrWSCmbValue)
    {
        this.fltrWSCmbValue = fltrWSCmbValue;
    }

    public String getFltrWSCmbText()
    {
        return fltrWSCmbText;
    }

    public void setFltrWSCmbText(String fltrWSCmbText)
    {
        this.fltrWSCmbText = fltrWSCmbText;
    }

    public String getFltrTxtWsProject()
    {
        return fltrTxtWsProject;
    }

    public void setFltrTxtWsProject(String fltrTxtWsProject)
    {
        this.fltrTxtWsProject = fltrTxtWsProject;
    }

    public String getFltrTxtWsIntrface()
    {
        return fltrTxtWsIntrface;
    }

    public void setFltrTxtWsIntrface(String fltrTxtWsIntrface)
    {
        this.fltrTxtWsIntrface = fltrTxtWsIntrface;
    }

    public String[] getFltrHdnTxtWsdlUrl()
    {
        return fltrHdnTxtWsdlUrl;
    }

    public void setFltrHdnTxtWsdlUrl(String[] fltrHdnTxtWsdlUrl)
    {
        this.fltrHdnTxtWsdlUrl = fltrHdnTxtWsdlUrl;
    }

    public String[] getFltrHdnCmbWsMethod()
    {
        return fltrHdnCmbWsMethod;
    }

    public void setFltrHdnCmbWsMethod(String[] fltrHdnCmbWsMethod)
    {
        this.fltrHdnCmbWsMethod = fltrHdnCmbWsMethod;
    }

    public String[] getFltrHdnTxtWsCmbValue()
    {
        return fltrHdnTxtWsCmbValue;
    }

    public void setFltrHdnTxtWsCmbValue(String[] fltrHdnTxtWsCmbValue)
    {
        this.fltrHdnTxtWsCmbValue = fltrHdnTxtWsCmbValue;
    }

    public String[] getFltrHdnTxtWsCmbText()
    {
        return fltrHdnTxtWsCmbText;
    }

    public void setFltrHdnTxtWsCmbText(String[] fltrHdnTxtWsCmbText)
    {
        this.fltrHdnTxtWsCmbText = fltrHdnTxtWsCmbText;
    }

    public String[] getFltrHdnTxtWsProject()
    {
        return fltrHdnTxtWsProject;
    }

    public void setFltrHdnTxtWsProject(String[] fltrHdnTxtWsProject)
    {
        this.fltrHdnTxtWsProject = fltrHdnTxtWsProject;
    }

    public String[] getFltrHdnTxtWsIntrface()
    {
        return fltrHdnTxtWsIntrface;
    }

    public void setFltrHdnTxtWsIntrface(String[] fltrHdnTxtWsIntrface)
    {
        this.fltrHdnTxtWsIntrface = fltrHdnTxtWsIntrface;
    }

    public String[] getFltrHdnTxtWsRetType()
    {
        return fltrHdnTxtWsRetType;
    }

    public void setFltrHdnTxtWsRetType(String[] fltrHdnTxtWsRetType)
    {
        this.fltrHdnTxtWsRetType = fltrHdnTxtWsRetType;
    }

    public String[] getFltrHdnTxtWsParams()
    {
        return fltrHdnTxtWsParams;
    }

    public void setFltrHdnTxtWsParams(String[] fltrHdnTxtWsParams)
    {
        this.fltrHdnTxtWsParams = fltrHdnTxtWsParams;
    }

    public String[] getFltrHdnTxtWsExps()
    {
        return fltrHdnTxtWsExps;
    }

    public void setFltrHdnTxtWsExps(String[] fltrHdnTxtWsExps)
    {
        this.fltrHdnTxtWsExps = fltrHdnTxtWsExps;
    }

    public String[] getFltrHdnCmbCommonQuery()
    {
        return fltrHdnCmbCommonQuery;
    }

    public void setFltrHdnCmbCommonQuery(String[] fltrHdnCmbCommonQuery)
    {
        this.fltrHdnCmbCommonQuery = fltrHdnCmbCommonQuery;
    }
}
