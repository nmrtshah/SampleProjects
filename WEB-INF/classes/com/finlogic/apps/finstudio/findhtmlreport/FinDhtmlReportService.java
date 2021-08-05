/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.apps.finstudio.findhtmlreport;

import com.finlogic.business.finstudio.findhtmlreport.FinDhtmlReportDetailEntityBean;
import com.finlogic.business.finstudio.findhtmlreport.FinDhtmlReportManager;
import com.finlogic.business.finstudio.findhtmlreport.FinDhtmlReportSummaryEntityBean;
import com.finlogic.util.DirectoryService;
import com.finlogic.util.FolderZipper;
import com.finlogic.util.findhtmlreport.FinDhtmlReportGenerator;
import com.finlogic.util.findhtmlreport.FinDhtmlReportSRSGeneration;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jxl.write.WriteException;
import org.springframework.jdbc.support.rowset.SqlRowSet;

/**
 *
 * @author njuser
 */
public class FinDhtmlReportService
{

    private final FinDhtmlReportManager manager = new FinDhtmlReportManager();

    public FinDhtmlReportDetailEntityBean formBeanToDetailEntityBean(final FinDhtmlReportFormBean formBean) throws SQLException
    {
        FinDhtmlReportDetailEntityBean entityBean = new FinDhtmlReportDetailEntityBean();

        entityBean.setProjectName(formBean.getCmbProjectName());
        if (formBean.getTxtReqNo().equals(""))
        {
            entityBean.setRequestNo(null);
        }
        else
        {
            entityBean.setRequestNo(formBean.getTxtReqNo());
        }
        entityBean.setModuleName(formBean.getTxtModuleName());
        entityBean.setAliasName(formBean.getCmbAliasName()[0]);
        entityBean.setMainQuery(formBean.getTxtQuery()[0]);
        entityBean.setAlias(formBean.getCmbAliasName());
        entityBean.setQuery(formBean.getTxtQuery());
        entityBean.setGrouping(formBean.isChkGrouping());
        if (formBean.isChkGrouping())
        {
            entityBean.setGroupField(formBean.getCmbGroupField());
        }
        else
        {
            entityBean.setGroupField(null);
        }
        entityBean.setGroupFooter(formBean.isChkGroupFooter());
        if (formBean.isChkGroupFooter())
        {
            entityBean.setGroupFtrCol(formBean.getHdncmbGrpFooterColumn());
            entityBean.setGroupFtrCal(formBean.getHdncmbGrpFooterCalculation());
        }
        else
        {
            entityBean.setGroupFtrCol(null);
            entityBean.setGroupFtrCal(null);
        }

        entityBean.setProblemStatement(formBean.getTxtProblemStmt());
        entityBean.setSolutionObjective(formBean.getTxtSolution());
        entityBean.setExistingPractise(formBean.getTxtExistingPractice());
        entityBean.setPlacement(formBean.getTxtPlacement());

        entityBean.setReportTitle(formBean.getTxtReportTitle());
        entityBean.setDateTimePicker(formBean.isChkDateSelection());
        entityBean.setReportType(formBean.isChkReportType());
        entityBean.setExport(formBean.isChkExport());
        entityBean.setColumns(formBean.isChkColumns());
        entityBean.setChart(formBean.isChkChart());
        entityBean.setFilter(formBean.isChkFilters());
        entityBean.setRptLabel(formBean.getRptHdnLabel());
        String emptyStr;
        emptyStr = "N/A";
        entityBean.setRptControl(formBean.getRptHdnControl());
        String[] arr = formBean.getRptHdnValidation();
        if (arr != null)
        {
            for (int i = 0; i < arr.length; i++)
            {
                if (arr[i].equals("-1"))
                {
                    arr[i] = emptyStr;
                }
            }
        }
        entityBean.setRptValidation(arr);
        arr = formBean.getRptHdnRbtnMultiple();
        if (arr != null)
        {
            for (int i = 0; i < arr.length; i++)
            {
                if ((arr[i].equals("true") || arr[i].equals("Multiple")) && formBean.getRptHdnControl()[i].equals("ComboBox"))
                {
                    arr[i] = "true";
                }
                else if ((arr[i].equals("false") || arr[i].equals("Single")) && formBean.getRptHdnControl()[i].equals("ComboBox"))
                {
                    arr[i] = "false";
                }
                else
                {
                    arr[i] = "N/A";
                }
            }
        }
        entityBean.setRptSelNature(arr);
        arr = formBean.getRptHdnRemarks();
        if (arr != null)
        {
            for (int i = 0; i < arr.length; i++)
            {
                if (arr[i].equals(""))
                {
                    arr[i] = emptyStr;
                }
            }
        }
        entityBean.setRptRemarks(arr);

        entityBean.setFltrLabel(formBean.getFltrHdnLabel());
        entityBean.setFltrControl(formBean.getFltrHdnControl());
        arr = formBean.getFltrHdnValidation();
        if (arr != null)
        {
            for (int i = 0; i < arr.length; i++)
            {
                if (arr[i].equals("-1"))
                {
                    arr[i] = emptyStr;
                }
            }
        }
        entityBean.setFltrValidation(arr);
        arr = formBean.getFltrHdnRbtnMultiple();
        if (arr != null)
        {
            for (int i = 0; i < arr.length; i++)
            {
                if ((arr[i].equals("true") || arr[i].equals("Multiple")) && formBean.getFltrHdnControl()[i].equals("ComboBox"))
                {
                    arr[i] = "true";
                }
                else if ((arr[i].equals("false") || arr[i].equals("Single")) && formBean.getFltrHdnControl()[i].equals("ComboBox"))
                {
                    arr[i] = "false";
                }
                else
                {
                    arr[i] = "N/A";
                }
            }
        }
        entityBean.setFltrSelNature(arr);
        arr = formBean.getFltrHdnRemarks();
        if (arr != null)
        {
            for (int i = 0; i < arr.length; i++)
            {
                if (arr[i].equals(""))
                {
                    arr[i] = emptyStr;
                }
            }
        }
        entityBean.setFltrRemarks(arr);

        entityBean.setGrid(formBean.isChkOnScreen());
        entityBean.setPdf(formBean.isChkPDF());
        entityBean.setExcel(formBean.isChkExcel());        

        entityBean.setPrimaryKey(formBean.getCmbPrimaryKey());

        entityBean.setNoChart(formBean.isNoChart());
        entityBean.setPieChart(formBean.isPieChart());
        entityBean.setBarChart(formBean.isBarChart());
        entityBean.setSymbolLineChart(formBean.isLineChart());
        entityBean.setThreedLineChart(formBean.isAreaChart());

        entityBean.setReportColumns(formBean.getCmbTabColumn());
        entityBean.setEmpCode(formBean.getEmp_code());
        entityBean.setAddControl(formBean.isChkAddControl());
        if (formBean.isChkAddControl())
        {
            entityBean.setControl(formBean.getHdncmbColumnControl());
            entityBean.setCol(formBean.getHdncmbColumnGrid());
        }
        else
        {
            entityBean.setControl(null);
            entityBean.setCol(null);
        }
        entityBean.setChildQuery(formBean.getTxtQuery());
        entityBean.setChildQueryAlias(formBean.getCmbAliasName());
        //add remaining fields to database
        entityBean.setConType(formBean.getRdoConType());
        entityBean.setDevServer(formBean.getCmbDevServer());
        entityBean.setHeaderReq(formBean.isChkHeaderReq());
        entityBean.setFooterReq(formBean.isChkFooterReq());
        entityBean.setMethodName(formBean.getTxtMethodNm());
        entityBean.setSelectedColumns(formBean.getCmbTabColumn());
        entityBean.setAllColumns(formBean.getHdnAllColumns());
        entityBean.setAllColumnTypes(formBean.getHdnAllDataTypes());
        entityBean.setPageFooter(formBean.isChkPageFooter());
        entityBean.setGrandTotal(formBean.isChkGrandTotal());

        entityBean.setRptMandatory(formBean.getRptHdnChkMandatory());
        entityBean.setFltrMandatory(formBean.getFltrHdnChkMandatory());
        entityBean.setMainQueryColumns(formBean.getCmbMainQueryColumn());
        entityBean.setChildQueryColumns(formBean.getCmbChildQueryColumns());

        entityBean.setPageFooterColumn(formBean.getHdnPageFooterColumns());


        if (formBean.isNoChart())
        {
            entityBean.setChartName("noChart");
        }
        if (formBean.isPieChart())
        {
            entityBean.setChartName("pieChart");
            entityBean.setPieTxtChartTitle(formBean.getPieTxtChartTitle());
            entityBean.setPieCmbXaxisColumn(formBean.getPieCmbXaxisColumn());
            entityBean.setPieCmbYaxisColumn(formBean.getPieCmbYaxisColumn());
        }
        if (formBean.isBarChart())
        {
            entityBean.setChartName("barChart");
            entityBean.setBarTxtChartTitle(formBean.getBarTxtChartTitle());
            entityBean.setBarCmbXaxisColumn(formBean.getBarCmbXaxisColumn());
            entityBean.setBarCmbYaxisColumn(formBean.getBarCmbYaxisColumn());
            entityBean.setBarTxtXLabel(formBean.getBarTxtXLabel());
            entityBean.setBarTxtYLabel(formBean.getBarTxtYLabel());
            entityBean.setBartxtLegendName(formBean.getBartxtLegendName());
        }
        if (formBean.isLineChart())
        {
            entityBean.setChartName("lineChart");
            entityBean.setLineTxtChartTitle(formBean.getLineTxtChartTitle());
            entityBean.setLineCmbXaxisColumn(formBean.getLineCmbXaxisColumn());
            entityBean.setLineCmbYaxisColumn(formBean.getLineCmbYaxisColumn());
            entityBean.setLineTxtXLabel(formBean.getLineTxtXLabel());
            entityBean.setLineTxtYLabel(formBean.getLineTxtYLabel());
            entityBean.setLinetxtLegendName(formBean.getLinetxtLegendName());
        }
        if (formBean.isAreaChart())
        {
            entityBean.setChartName("areaChart");
            entityBean.setAreaTxtChartTitle(formBean.getAreaTxtChartTitle());
            entityBean.setAreaCmbXaxisColumn(formBean.getAreaCmbXaxisColumn());
            entityBean.setAreaCmbYaxisColumn(formBean.getAreaCmbYaxisColumn());
            entityBean.setAreaTxtXLabel(formBean.getAreaTxtXLabel());
            entityBean.setAreaTxtYLabel(formBean.getAreaTxtYLabel());
            entityBean.setAreatxtLegendName(formBean.getAreatxtLegendName());
        }
        //report type code properties        
        entityBean.setRptTxtId(formBean.getRptHdnTxtId());
        entityBean.setRptTxtName(formBean.getRptHdnTxtName());
        entityBean.setRptTxtValue(formBean.getRptHdnTxtValue());
        entityBean.setRptTxtTabIndex(formBean.getRptHdnTxtTabindex());
        entityBean.setRptTxtClass(formBean.getRptHdnTxtClass());
        entityBean.setRptRbtnChecked(formBean.getRptHdnRbtnChecked());
        entityBean.setRptTxtMaxLen(formBean.getRptHdnTxtMaxLen());
        entityBean.setRptRbtnMultiple(formBean.getRptHdnRbtnMultiple());
        entityBean.setRptCmbAlign(formBean.getRptHdnCmbAlign());
        entityBean.setRptTxtSize(formBean.getRptHdnTxtSize());
        entityBean.setRptTxtStyle(formBean.getRptHdnTxtStyle());
        entityBean.setRptRbtnReadOnly(formBean.getRptHdnRbtnReadonly());
        entityBean.setRptTxtRows(formBean.getRptHdnTxtRows());
        entityBean.setRptTxtCols(formBean.getRptHdnTxtCols());
        entityBean.setRptTxtTotalRadio(formBean.getRptHdnTxtTotalRdo());
        entityBean.setRptTxtRdoValue(formBean.getRptHdnTxtRdoVal());
        entityBean.setRptTxtRdoCaption(formBean.getRptHdnTxtRdoCaption());
        entityBean.setRptTxtDefRdoValue(formBean.getRptHdnTxtRdoDefVal());
        //filter code properties
        entityBean.setFltrTxtId(formBean.getFltrHdnTxtId());
        entityBean.setFltrTxtName(formBean.getFltrHdnTxtName());
        entityBean.setFltrTxtValue(formBean.getFltrHdnTxtValue());
        entityBean.setFltrTxtTabIndex(formBean.getFltrHdnTxtTabindex());
        entityBean.setFltrTxtClass(formBean.getFltrHdnTxtClass());
        entityBean.setFltrRbtnChecked(formBean.getFltrHdnRbtnChecked());
        entityBean.setFltrTxtMaxLen(formBean.getFltrHdnTxtMaxLen());
        entityBean.setFltrRbtnMultiple(formBean.getFltrHdnRbtnMultiple());
        entityBean.setFltrCmbAlign(formBean.getFltrHdnCmbAlign());
        entityBean.setFltrTxtSize(formBean.getFltrHdnTxtSize());
        entityBean.setFltrTxtStyle(formBean.getFltrHdnTxtStyle());
        entityBean.setFltrRbtnReadOnly(formBean.getFltrHdnRbtnReadonly());
        entityBean.setFltrTxtRows(formBean.getFltrHdnTxtRows());
        entityBean.setFltrTxtCols(formBean.getFltrHdnTxtCols());
        entityBean.setFltrTxtTotalRadio(formBean.getFltrHdnTxtTotalRdo());
        entityBean.setFltrTxtRdoValue(formBean.getFltrHdnTxtRdoVal());
        entityBean.setFltrTxtRdoCaption(formBean.getFltrHdnTxtRdoCaption());
        entityBean.setFltrTxtDefRdoValue(formBean.getFltrHdnTxtRdoDefVal());
        entityBean.setFltrCmbSource(formBean.getFltrHdnCmbSource());
        entityBean.setFltrTxtSrcQuery(formBean.getFltrHdnTxtSrcQuery());
        entityBean.setFltrTxtSrcStatic(formBean.getFltrHdnTxtSrcStatic());
        //for fill combo using webservice
        entityBean.setFltrTxtWsCmbText(formBean.getFltrHdnTxtWsCmbText());
        entityBean.setFltrTxtWsCmbValue(formBean.getFltrHdnTxtWsCmbValue());
        entityBean.setFltrTxtWsExps(formBean.getFltrHdnTxtWsExps());
        entityBean.setFltrTxtWsIntrface(formBean.getFltrHdnTxtWsIntrface());
        entityBean.setFltrTxtWsParams(formBean.getFltrHdnTxtWsParams());
        entityBean.setFltrTxtWsProject(formBean.getFltrHdnTxtWsProject());
        entityBean.setFltrTxtWsRetType(formBean.getFltrHdnTxtWsRetType());
        entityBean.setFltrTxtWsdlUrl(formBean.getFltrHdnTxtWsdlUrl());
        entityBean.setFltrCmbWsMethod(formBean.getFltrHdnCmbWsMethod());

        //for fill combo using Common Combo
        entityBean.setFltrCmbCommonQuery(formBean.getFltrHdnCmbCommonQuery());
        
        //for dependent combo
        entityBean.setFltrCmbDependent(formBean.getFltrHdnCmbDependent());
        entityBean.setFltrchkForDependentCombo(formBean.getFltrHdnChkForDependentCombo());

        entityBean.setRptControlPos(formBean.getCmbRptControlPos());
        entityBean.setFltrControlPos(formBean.getCmbControlPos());
        entityBean.setSRNo(formBean.getSRNo());
        if (formBean.getCmbRefNo().equals(""))
        {
            entityBean.setCmbRefNo(null);
        }
        else
        {
            entityBean.setCmbRefNo(formBean.getCmbRefNo());
        }
        return entityBean;
    }

    public FinDhtmlReportSummaryEntityBean formBeanToSummaryEntityBean(final FinDhtmlReportFormBean formBean)
    {
        FinDhtmlReportSummaryEntityBean entityBean = new FinDhtmlReportSummaryEntityBean();
        entityBean.setAliasName(formBean.getCmbAliasName());
        entityBean.setAllQuery(formBean.getTxtQuery());
        entityBean.setGrouping(formBean.isChkGrouping());
        entityBean.setGroupField(formBean.getCmbGroupField());
        entityBean.setGroupFooter(formBean.isChkGroupFooter());
        entityBean.setGroupFtrCol(formBean.getHdncmbGrpFooterColumn());
        entityBean.setGroupFtrCal(formBean.getHdncmbGrpFooterCalculation());
        entityBean.setReportTitle(formBean.getTxtReportTitle());
        entityBean.setColumns(formBean.isChkColumns());
        entityBean.setReportColumns(formBean.getCmbTabColumn());
        entityBean.setPrimaryKey(formBean.getCmbPrimaryKey());
        entityBean.setAddControl(formBean.isChkAddControl());
        entityBean.setColumnControl(formBean.getHdncmbColumnControl());
        entityBean.setGridColumn(formBean.getHdncmbColumnGrid());
        entityBean.setMainQueryColumns(formBean.getCmbMainQueryColumn());
        entityBean.setChildQueryColumns(formBean.getCmbChildQueryColumns());
        entityBean.setAllColumns(formBean.getHdnAllColumns());
        entityBean.setAllColumnTypes(formBean.getHdnAllDataTypes());
        entityBean.setPageFooter(formBean.isChkPageFooter());
        entityBean.setGrandTotal(formBean.isChkGrandTotal());
        entityBean.setPageFooterColumn(formBean.getHdnPageFooterColumns());
        entityBean.setConType(formBean.getRdoConType());
        entityBean.setDevServer(formBean.getCmbDevServer());
        entityBean.setSelectedColumns(formBean.getCmbTabColumn());
        entityBean.setMethodName(formBean.getTxtMethodNm());
        entityBean.setPdf(formBean.isChkPDF());
        entityBean.setExcel(formBean.isChkExcel());        
        entityBean.setDetailRefNo(formBean.getCmbRefNo());
        entityBean.setSrno(formBean.getSRNo());
        return entityBean;

    }

    public int insertData(final FinDhtmlReportFormBean formBean) throws SQLException, ClassNotFoundException, UnsupportedEncodingException
    {
        return manager.insert(formBeanToDetailEntityBean(formBean));
    }

    public Map getColumnDetail(final String alias, final String query, final String conType, final Connection con) throws ClassNotFoundException, SQLException
    {
        Map<String, List<String>> colDetail;
        colDetail = manager.getColumnDetail(alias, query, conType, con);

        List<String> colNames;
        colNames = colDetail.get("colNames");
        List<String> colTypes;
        colTypes = colDetail.get("colTypes");
        int colNamesLen;
        colNamesLen = colNames.size();
        StringBuilder sbNames;
        sbNames = new StringBuilder();
        StringBuilder sbTypes;
        sbTypes = new StringBuilder();

        for (int i = 0; i < colNamesLen; i++)
        {
            sbNames.append("\"").append(colNames.get(i)).append("\",");
            sbTypes.append("\"").append(colTypes.get(i)).append("\",");
        }

        Map<String, StringBuilder> colDeatilsMap;
        colDeatilsMap = new HashMap<String, StringBuilder>();
        colDeatilsMap.put("colNames", sbNames);
        colDeatilsMap.put("colTypes", sbTypes);

        return colDeatilsMap;
    }

    public List getColumnNames(final String alias, final String query, final String conType, final Connection con) throws SQLException, ClassNotFoundException
    {
        Map<String, List<String>> colDetails;
        colDetails = manager.getColumnDetail(alias, query, conType, con);
        return colDetails.get("colNames");
    }

    public SqlRowSet getProjectList() throws ClassNotFoundException, SQLException
    {
        return manager.getProjectList();
    }

    public String[] getAliasList() throws SQLException
    {
        return manager.getAliasList();
    }

    public String validateQuery(final String aliasnm, final String query, final String conType, final Connection con) throws ClassNotFoundException, SQLException
    {
        return manager.validateQuery(aliasnm, query, conType, con);
    }

    public String generateReportFiles(FinDhtmlReportFormBean formBean) throws SQLException, ClassNotFoundException, FileNotFoundException, UnsupportedEncodingException
    {
        String str = "";
        if (formBean.getCmbRefNo() != null && !formBean.getCmbRefNo().equals(""))
        {
            FinDhtmlReportDetailEntityBean dEntityBean = getDetailReportData(Integer.parseInt(formBean.getCmbRefNo()));
            dEntityBean.setSRNo(formBean.getSRNo());
            str = new FinDhtmlReportGenerator().generateReportFiles(dEntityBean, formBeanToSummaryEntityBean(formBean));
            //str = new ReportGenerator().generateReportFiles(getDetailReportData(Integer.parseInt(formBean.getCmbRefNo())), formBeanToSummaryEntityBean(formBean));
        }
        else
        {
            str = new FinDhtmlReportGenerator().generateReportFiles(formBeanToDetailEntityBean(formBean), null);
        }
        return str;
    }

    public String generateSRSFiles(FinDhtmlReportFormBean formBean) throws SQLException, IOException, WriteException, ClassNotFoundException
    {
        String str = "";
        if (formBean.getCmbRefNo() != null && !formBean.getCmbRefNo().equals(""))
        {
            FinDhtmlReportDetailEntityBean dEntityBean = getDetailReportData(Integer.parseInt(formBean.getCmbRefNo()));
            dEntityBean.setSRNo(formBean.getSRNo());
            str = new FinDhtmlReportSRSGeneration().writeSRS(dEntityBean, formBeanToSummaryEntityBean(formBean));
        }
        else
        {
            str = new FinDhtmlReportSRSGeneration().writeSRS(formBeanToDetailEntityBean(formBean), null);
        }
        return str;
    }

    public void deleteFolder(String codeFile) throws IOException
    {
        DirectoryService dSrvc = new DirectoryService();
        dSrvc.deleteFolder(codeFile);
    }

    public void zipFolders(String codeFile, String tomcatPath, int srno) throws IOException, Exception
    {
        FolderZipper.zipFolder(codeFile, tomcatPath + "/webapps/finstudio/generated/" + srno + "RGV2.zip");
    }

    public FinDhtmlReportDetailEntityBean getDetailReportData(int srno) throws SQLException, ClassNotFoundException
    {
        FinDhtmlReportDetailEntityBean entityBean = new FinDhtmlReportDetailEntityBean();
        SqlRowSet allData = manager.getMainData(srno);
        while (allData.next())
        {
            //main table data
            entityBean.setCmbRefNo(allData.getString("SRNO"));
            entityBean.setSRNo(allData.getInt("SRNO"));
            entityBean.setProjectName(allData.getString("PROJECT_NAME"));
            entityBean.setModuleName(allData.getString("MODULE_NAME"));
            entityBean.setRequestNo(allData.getString("REQUEST_NO"));
            entityBean.setProblemStatement(allData.getString("PROBLEM_STATEMENT"));
            entityBean.setSolutionObjective(allData.getString("SOLUTION_OBJECTIVE"));
            entityBean.setExistingPractise(allData.getString("EXISTING_PRACTISE"));
            entityBean.setPlacement(allData.getString("PLACEMENT"));
            entityBean.setReportTitle(allData.getString("REPORT_TITLE"));
            entityBean.setAliasName(allData.getString("ALIAS_NAME"));
            entityBean.setMainQuery(allData.getString("MAIN_QUERY"));
            entityBean.setGrouping(allData.getBoolean("GROUPING"));
            entityBean.setGroupField(allData.getString("GROUP_FIELD"));
            entityBean.setGroupFooter(allData.getBoolean("GROUP_FOOTER"));
            entityBean.setPdf(allData.getBoolean("PDF"));
            entityBean.setExcel(allData.getBoolean("EXCEL"));
            entityBean.setGrid(allData.getBoolean("GRID"));
            entityBean.setHtml(allData.getBoolean("HTML"));
            entityBean.setPrimaryKey(allData.getString("PRIMARY_KEY"));
            entityBean.setAddControl(allData.getBoolean("ADD_CONTROL"));
            entityBean.setReportType(allData.getBoolean("REPORT_TYPE"));
            entityBean.setFilter(allData.getBoolean("FILTER"));
            entityBean.setExport(allData.getBoolean("EXPORT"));
            entityBean.setChart(allData.getBoolean("CHART"));
            entityBean.setColumns(allData.getBoolean("COLUMNS"));
            entityBean.setDateTimePicker(allData.getBoolean("DATE_SELECTION"));
            entityBean.setConType(allData.getString("CONNECTION_TYPE"));
            entityBean.setDevServer(allData.getString("DEVELOPMENT_SERVER"));
            entityBean.setHeaderReq(allData.getBoolean("HEADER_REQUIRED"));
            entityBean.setFooterReq(allData.getBoolean("FOOTER_REQUIRED"));
            entityBean.setMethodName(allData.getString("METHOD_NAME"));
            //set add control field
            if (allData.getBoolean("ADD_CONTROL"))
            {
                entityBean.setControl(allData.getString("GRID_CONTROL").split(","));
                entityBean.setCol(allData.getString("GRID_COLUMN").split(","));
            }
            //set group footer field
            if (allData.getBoolean("GROUP_FOOTER"))
            {
                entityBean.setGroupFtrCol(allData.getString("FOOTER_COLUMN").split(","));
                entityBean.setGroupFtrCal(allData.getString("FOOTER_CALC").split(","));
            }
            //set child query field
            if (allData.getString("CHILD_QUERY_ALIAS") != null)
            {
                entityBean.setChildQueryAlias(allData.getString("CHILD_QUERY_ALIAS").split(","));
            }
            //set report type field
            entityBean.setRptLabel(allData.getString("RPT_TYPE_LABEL").split(","));
            entityBean.setRptControl(allData.getString("RPT_CONTROL").split(","));
            entityBean.setRptValidation(allData.getString("RPT_VALIDATION").split(","));
            entityBean.setRptSelNature(allData.getString("RPT_SEL_NATURE").split(","));
            entityBean.setRptRemarks(allData.getString("RPT_REMARKS").split("#\\$"));
            entityBean.setRptMandatory(allData.getString("RPT_MANDATORY").split(","));
            //set filter field
            entityBean.setFltrLabel(allData.getString("FLTR_LABEL").split(","));
            entityBean.setFltrControl(allData.getString("FLTR_CONTROL").split(","));
            entityBean.setFltrValidation(allData.getString("FLTR_VALIDATION").split(","));
            entityBean.setFltrSelNature(allData.getString("FLTR_SEL_NATURE").split(","));
            entityBean.setFltrRemarks(allData.getString("FLTR_REMARKS").split("#\\$"));
            entityBean.setFltrMandatory(allData.getString("FLTR_MANDATORY").split(","));
            //set rptdata field
            if (allData.getString("PAGE_FOOTER") != null && allData.getString("PAGE_FOOTER").equals("true"))
            {
                entityBean.setPageFooter(true);
            }
            else
            {
                entityBean.setPageFooter(false);
            }
            if (allData.getString("GRAND_TOTAL") != null && allData.getString("GRAND_TOTAL").equals("true"))
            {
                entityBean.setGrandTotal(true);
            }
            else
            {
                entityBean.setGrandTotal(false);
            }
            entityBean.setFltrControlPos(allData.getString("FLTR_CNTRL_POS"));
            entityBean.setRptControlPos(allData.getString("RPT_CNTRL_POS"));
            entityBean.setChildQueryColumns(allData.getString("CHILD_QUERY_COLUMNS").split(","));
            if (allData.getString("PAGE_FOOTER").equals("true") || allData.getString("GRAND_TOTAL").equals("true"))
            {
                entityBean.setPageFooterColumn(allData.getString("PAGE_FOOTER_COLUMN").split(","));
            }
            entityBean.setSelectedColumns(allData.getString("SELECTED_COLUMNS").split(","));
            entityBean.setMainQueryColumns(allData.getString("MAIN_QUERY_COLUMNS").split(","));
            entityBean.setAllColumns(allData.getString("ALL_COLUMNS").split(","));
            entityBean.setAllColumnTypes(allData.getString("ALL_COLUMN_TYPES").split(","));
            //set chart data
            entityBean.setPieTxtChartTitle(allData.getString("PC_TITLE"));
            entityBean.setPieCmbXaxisColumn(allData.getString("PC_XCOLUMN"));
            entityBean.setPieCmbXaxisColumn(allData.getString("PC_YCOLUMN"));
            entityBean.setBarTxtChartTitle(allData.getString("BC_TITLE"));
            entityBean.setBarCmbXaxisColumn(allData.getString("BC_XCOLUMN"));
            entityBean.setBarCmbYaxisColumn(allData.getString("BC_YCOLUMN").split(","));
            entityBean.setBarTxtXLabel(allData.getString("BC_XLABEL"));
            entityBean.setBarTxtYLabel(allData.getString("BC_YLABEL"));
            entityBean.setBartxtLegendName(allData.getString("BC_LEGEND_NAME").split(","));
            entityBean.setLineTxtChartTitle(allData.getString("LC_TITLE"));
            entityBean.setLineCmbXaxisColumn(allData.getString("LC_XCOLUMN"));
            entityBean.setLineCmbYaxisColumn(allData.getString("LC_YCOLUMN").split(","));
            entityBean.setLineTxtXLabel(allData.getString("LC_XLABEL"));
            entityBean.setLineTxtYLabel(allData.getString("LC_YLABEL"));
            entityBean.setLinetxtLegendName(allData.getString("LC_LEGEND_NAME").split(","));
            entityBean.setAreaTxtChartTitle(allData.getString("AC_TITLE"));
            entityBean.setAreaCmbXaxisColumn(allData.getString("AC_XCOLUMN"));
            entityBean.setAreaCmbYaxisColumn(allData.getString("AC_YCOLUMN").split(","));
            entityBean.setAreaTxtXLabel(allData.getString("AC_XLABEL"));
            entityBean.setAreaTxtYLabel(allData.getString("AC_YLABEL"));
            entityBean.setAreatxtLegendName(allData.getString("AC_LEGEND_NAME").split(","));
            //set report type code properties
            entityBean.setRptTxtId(allData.getString("RPT_ID").split(","));
            entityBean.setRptTxtName(allData.getString("RPT_NAME").split(","));
            entityBean.setRptTxtValue(allData.getString("RPT_VALUE").split(","));
            entityBean.setRptTxtTabIndex(allData.getString("RPT_TABINDEX").split(","));
            entityBean.setRptTxtClass(allData.getString("RPT_CLASS").split(","));
            entityBean.setRptRbtnChecked(allData.getString("RPT_CHECKED").split(","));
            entityBean.setRptTxtMaxLen(allData.getString("RPT_MAXLEN").split(","));
            entityBean.setRptRbtnMultiple(allData.getString("RPT_MULTIPLE").split(","));
            entityBean.setRptCmbAlign(allData.getString("RPT_ALIGN").split(","));
            entityBean.setRptTxtSize(allData.getString("RPT_SIZE").split(","));
            entityBean.setRptTxtStyle(allData.getString("RPT_STYLE").split(","));
            entityBean.setRptRbtnReadOnly(allData.getString("RPT_READONLY").split(","));
            entityBean.setRptTxtRows(allData.getString("RPT_ROWS").split(","));
            entityBean.setRptTxtCols(allData.getString("RPT_COLS").split(","));
            entityBean.setRptTxtTotalRadio(allData.getString("RPT_TOTAL_RADIO").split(","));
            entityBean.setRptTxtRdoValue(allData.getString("RPT_RADIO_VALUE").split(","));
            entityBean.setRptTxtRdoCaption(allData.getString("RPT_RADIO_CAPTION").split(","));
            entityBean.setRptTxtDefRdoValue(allData.getString("RPT_DEF_RADIO").split(","));
            //set filter code properties
            entityBean.setFltrTxtId(allData.getString("FLTR_ID").split(","));
            entityBean.setFltrTxtName(allData.getString("FLTR_NAME").split(","));
            entityBean.setFltrTxtValue(allData.getString("FLTR_VALUE").split(","));
            entityBean.setFltrTxtTabIndex(allData.getString("FLTR_TABINDEX").split(","));
            entityBean.setFltrTxtClass(allData.getString("FLTR_CLASS").split(","));
            entityBean.setFltrRbtnChecked(allData.getString("FLTR_CHECKED").split(","));
            entityBean.setFltrTxtMaxLen(allData.getString("FLTR_MAXLEN").split(","));
            entityBean.setFltrRbtnMultiple(allData.getString("FLTR_MULTIPLE").split(","));
            entityBean.setFltrCmbAlign(allData.getString("FLTR_ALIGN").split(","));
            entityBean.setFltrTxtSize(allData.getString("FLTR_SIZE").split(","));
            entityBean.setFltrTxtStyle(allData.getString("FLTR_STYLE").split(","));
            entityBean.setFltrRbtnReadOnly(allData.getString("FLTR_READONLY").split(","));
            entityBean.setFltrTxtRows(allData.getString("FLTR_ROWS").split(","));
            entityBean.setFltrTxtCols(allData.getString("FLTR_COLS").split(","));
            entityBean.setFltrTxtTotalRadio(allData.getString("FLTR_TOTAL_RADIO").split(","));
            entityBean.setFltrTxtRdoValue(allData.getString("FLTR_RADIO_VALUE").split(","));
            entityBean.setFltrTxtRdoCaption(allData.getString("FLTR_RADIO_CAPTION").split(","));
            entityBean.setFltrTxtDefRdoValue(allData.getString("FLTR_DEF_RADIO").split(","));
            entityBean.setFltrCmbSource(allData.getString("FLTR_CMB_SOURCE").split(","));
            entityBean.setFltrTxtSrcQuery(allData.getString("FLTR_SRC_QUERY").split("#\\$"));
            entityBean.setFltrTxtSrcStatic(allData.getString("FLTR_SRC_STATIC").split("#\\$"));

            entityBean.setAlias(allData.getString("ALL_ALIAS").split(","));
            //for fill combo using webservice            
            entityBean.setFltrTxtWsdlUrl(allData.getString("WSDL_URL").split(","));
            entityBean.setFltrCmbWsMethod(allData.getString("WS_METHOD").split(","));
            entityBean.setFltrTxtWsCmbValue(allData.getString("CMB_VALUE").split(","));
            entityBean.setFltrTxtWsCmbText(allData.getString("CMB_TEXT").split(","));
            entityBean.setFltrTxtWsProject(allData.getString("WS_PROJECT").split(","));
            entityBean.setFltrTxtWsIntrface(allData.getString("WS_INTERFACE").split(","));
            entityBean.setFltrTxtWsRetType(allData.getString("WS_RETTYPE").split(","));
            entityBean.setFltrTxtWsParams(allData.getString("WS_PARAM").split("#\\$"));
            entityBean.setFltrTxtWsExps(allData.getString("WS_EXPS").split(","));

            entityBean.setFltrCmbCommonQuery(allData.getString("COMMON_QUERY").split(","));
            entityBean.setFltrCmbDependent(allData.getString("DEPENDENT_COMBO"));
            entityBean.setFltrchkForDependentCombo(allData.getString("FLTR_CHK_DEPENDENT_COMBO").split(","));
        }
        //for all query
        SqlRowSet srs;
        srs = manager.getAllQuery(srno, "allquery");
        List tmpList = new ArrayList();
        while (srs.next())
        {
            tmpList.add(srs.getString("VALUE"));
        }
        String[] tempQuery = new String[tmpList.size()];
        for (int i = 0; i < tmpList.size(); i++)
        {
            tempQuery[i] = tmpList.get(i).toString();
        }
        entityBean.setQuery(tempQuery);
        //for chart names
        srs = manager.getAllQuery(srno, "chartName");
        while (srs.next())
        {
            entityBean.setChartName(srs.getString("VALUE"));
        }
        if (entityBean.getChartName() != null)
        {
            if (entityBean.getChartName().contains("noChart"))
            {
                entityBean.setNoChart(true);
            }
            if (entityBean.getChartName().contains("pieChart"))
            {
                entityBean.setPieChart(true);
            }
            if (entityBean.getChartName().contains("barChart"))
            {
                entityBean.setBarChart(true);
            }
            if (entityBean.getChartName().contains("lineChart"))
            {
                entityBean.setSymbolLineChart(true);
            }
            if (entityBean.getChartName().contains("areaChart"))
            {
                entityBean.setThreedLineChart(true);
            }
        }
        //for child query
        srs = manager.getChildQuery(srno);
        while (srs.next())
        {
            tmpList.add(srs.getString("CHILD_QUERY"));
        }
        tempQuery = new String[tmpList.size()];
        for (int i = 0; i < tmpList.size(); i++)
        {
            tempQuery[i] = tmpList.get(i).toString();
        }
        entityBean.setChildQuery(tempQuery);
        return entityBean;
    }

    public List getRefNumber(String prjctName) throws ClassNotFoundException, SQLException
    {
        return manager.getRefNumber(prjctName);
    }
}
