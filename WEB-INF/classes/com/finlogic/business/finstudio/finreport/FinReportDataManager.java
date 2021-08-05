/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.business.finstudio.finreport;

import com.finlogic.util.persistence.SQLConnService;
import com.finlogic.util.persistence.SQLService;
import com.finlogic.util.persistence.SQLUtility;
import finutils.directconn.DBConnManager;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;

/**
 *
 * @author njuser
 */
public class FinReportDataManager
{

    private static final SQLUtility SQLU = new SQLUtility();
    private static final SQLService SQLS = new SQLService();
    private static final SQLConnService SQLCS = new SQLConnService();
    private static final String ALIASNAME = "finstudio_mysql";
    private int idRptMain;
    private int idRptField;

    public int insert(final FinReportDetailEntityBean entityBean) throws ClassNotFoundException, SQLException, UnsupportedEncodingException
    {
        SqlParameterSource sps;
        sps = new BeanPropertySqlParameterSource(entityBean);
        KeyHolder keys;
        keys = new GeneratedKeyHolder();
        String insert_rpt_main;
        String insertQuery;

        insert_rpt_main = "INSERT INTO RPT_MAIN(PROJECT_NAME,MODULE_NAME,REQUEST_NO,PROBLEM_STATEMENT,SOLUTION_OBJECTIVE,EXISTING_PRACTISE,PLACEMENT,REPORT_TITLE,ALIAS_NAME,MAIN_QUERY,GROUPING,GROUP_FIELD,GROUP_FOOTER,PDF,EXCEL,GRID,HTML,PRIMARY_KEY,ADD_CONTROL,REPORT_TYPE,EXPORT,COLUMNS,CHART,FILTER,EMP_CODE,ON_DATE,DATE_SELECTION,CONNECTION_TYPE,DEVELOPMENT_SERVER,HEADER_REQUIRED,FOOTER_REQUIRED,METHOD_NAME,REF_NUMBER,REMARKS)"
                + " VALUES(:projectName,:moduleName,:requestNo,:problemStatement,:solutionObjective,:existingPractise,:placement,:reportTitle,:aliasName,:mainQuery,:grouping,:groupField,:groupFooter,:pdf,:excel,:grid,:html,:primaryKey,:addControl,:reportType,:export,:columns,:chart,:filter,:empCode,SYSDATE(),:dateTimePicker,:conType,:devServer,:headerReq,:footerReq,:methodName,:cmbRefNo,'Report Generation V2')";
        SQLS.persist(ALIASNAME, insert_rpt_main, keys, sps);
        //srno to insert other tables
        idRptMain = SQLS.getInt(ALIASNAME, "SELECT SRNO FROM RPT_MAIN ORDER BY SRNO DESC LIMIT 1");

        if (entityBean.isGroupFooter() && entityBean.getGroupFtrCol() != null)
        {
            int groupFooterColLen = entityBean.getGroupFtrCol().length;
            StringBuilder insert_rpt_footer = new StringBuilder();
            insert_rpt_footer.append("INSERT INTO RPT_GRPFOOTER(SRNO_RPT_MAIN,GROUP_FTR_COLUMN,GROUP_FTR_CALCULATION)");
            insert_rpt_footer.append("values");
            for (int i = 0; i < groupFooterColLen; i++)
            {
                insert_rpt_footer.append("(");
                insert_rpt_footer.append(idRptMain);
                insert_rpt_footer.append(",\"");
                insert_rpt_footer.append(entityBean.getGroupFtrCol()[i]);
                insert_rpt_footer.append("\",\"");
                insert_rpt_footer.append(entityBean.getGroupFtrCal()[i]);
                insert_rpt_footer.append("\"),");
            }
            insert_rpt_footer.deleteCharAt(insert_rpt_footer.length() - 1);
            SQLU.persist(ALIASNAME, insert_rpt_footer.toString());
        }
        if (entityBean.isReportType() && entityBean.getRptControl() != null)
        {
            int rptControlLen = entityBean.getRptControl().length;

            for (int i = 0; i < rptControlLen; i++)
            {
                String insert_rpt_field = "INSERT INTO RPT_FIELD(SRNO_RPT_MAIN,TAB_NAME,LABEL,CONTROL,VALIDATION,SELECTION_NATURE,REMARKS,MANDATORY)"
                        + " VALUES(" + idRptMain + ",'Report Type',\"" + entityBean.getRptLabel()[i] + "\",\"" + entityBean.getRptControl()[i] + "\",\"" + entityBean.getRptValidation()[i] + "\",\"" + entityBean.getRptSelNature()[i] + "\",\"" + entityBean.getRptRemarks()[i] + "\"," + entityBean.getRptMandatory()[i] + ")";
                SQLU.persist(ALIASNAME, insert_rpt_field);
                idRptField = SQLS.getInt(ALIASNAME, "SELECT SRNO FROM RPT_FIELD WHERE SRNO_RPT_MAIN = " + idRptMain + " ORDER BY SRNO DESC LIMIT 1");

                //insert rpt code properties to database                
                insertIntoRptData("rptTxtId", entityBean.getRptTxtId()[i], idRptField);
                insertIntoRptData("rptTxtName", entityBean.getRptTxtName()[i], idRptField);
                insertIntoRptData("rptTxtValue", entityBean.getRptTxtValue()[i], idRptField);
                insertIntoRptData("rptTxtTabIndex", entityBean.getRptTxtTabIndex()[i], idRptField);
                insertIntoRptData("rptTxtClass", entityBean.getRptTxtClass()[i], idRptField);
                insertIntoRptData("rptRbtnChecked", entityBean.getRptRbtnChecked()[i], idRptField);
                insertIntoRptData("rptTxtMaxLen", entityBean.getRptTxtMaxLen()[i], idRptField);
                insertIntoRptData("rptRbtnMultiple", entityBean.getRptRbtnMultiple()[i], idRptField);
                insertIntoRptData("rptCmbAlign", entityBean.getRptCmbAlign()[i], idRptField);
                insertIntoRptData("rptTxtSize", entityBean.getRptTxtSize()[i], idRptField);
                insertIntoRptData("rptTxtStyle", entityBean.getRptTxtStyle()[i], idRptField);
                insertIntoRptData("rptRbtnReadOnly", entityBean.getRptRbtnReadOnly()[i], idRptField);
                insertIntoRptData("rptTxtRows", entityBean.getRptTxtRows()[i], idRptField);
                insertIntoRptData("rptTxtCols", entityBean.getRptTxtCols()[i], idRptField);
                insertIntoRptData("rptTxtTotalRadio", entityBean.getRptTxtTotalRadio()[i], idRptField);
                insertIntoRptData("rptTxtRdoValue", entityBean.getRptTxtRdoValue()[i], idRptField);
                insertIntoRptData("rptTxtDefRdoValue", entityBean.getRptTxtDefRdoValue()[i], idRptField);
            }
        }
        if (entityBean.isFilter() && entityBean.getFltrControl() != null)
        {
            int filterControlLen;
            filterControlLen = entityBean.getFltrControl().length;
            for (int i = 0; i < filterControlLen; i++)
            {
                String insert_fltr_field;
                insert_fltr_field = "INSERT INTO RPT_FIELD(SRNO_RPT_MAIN,TAB_NAME,LABEL,CONTROL,VALIDATION,SELECTION_NATURE,REMARKS,MANDATORY)"
                        + " VALUES(" + idRptMain + ",'Filter',\"" + entityBean.getFltrLabel()[i] + "\",\"" + entityBean.getFltrControl()[i] + "\",\"" + entityBean.getFltrValidation()[i] + "\",\"" + entityBean.getFltrSelNature()[i] + "\",\"" + entityBean.getFltrRemarks()[i] + "\"," + entityBean.getFltrMandatory()[i] + ")";
                SQLU.persist(ALIASNAME, insert_fltr_field);
                idRptField = SQLS.getInt(ALIASNAME, "SELECT SRNO FROM RPT_FIELD WHERE SRNO_RPT_MAIN = " + idRptMain + " ORDER BY SRNO DESC LIMIT 1");
                //insert filter code properties to database                
                insertIntoRptData("fltrTxtId", entityBean.getFltrTxtId()[i], idRptField);
                insertIntoRptData("fltrTxtName", entityBean.getFltrTxtName()[i], idRptField);
                insertIntoRptData("fltrTxtValue", entityBean.getFltrTxtValue()[i], idRptField);
                insertIntoRptData("fltrTxtTabIndex", entityBean.getFltrTxtTabIndex()[i], idRptField);
                insertIntoRptData("fltrTxtClass", entityBean.getFltrTxtClass()[i], idRptField);
                insertIntoRptData("fltrRbtnChecked", entityBean.getFltrRbtnChecked()[i], idRptField);
                insertIntoRptData("fltrTxtMaxLen", entityBean.getFltrTxtMaxLen()[i], idRptField);

                insertIntoRptData("fltrRbtnMultiple", entityBean.getFltrRbtnMultiple()[i], idRptField);
                insertIntoRptData("fltrCmbSource", entityBean.getFltrCmbSource()[i], idRptField);
                insertIntoRptData("fltrTxtSrcQuery", URLDecoder.decode(entityBean.getFltrTxtSrcQuery()[i], "UTF-8"), idRptField);
                insertIntoRptData("fltrTxtSrcStatic", entityBean.getFltrTxtSrcStatic()[i], idRptField);
                //for combo fill using webservice
                insertIntoRptData("fltrTxtWsdlUrl", entityBean.getFltrTxtWsdlUrl()[i], idRptField);
                insertIntoRptData("fltrCmbWsMethod", entityBean.getFltrCmbWsMethod()[i], idRptField);
                insertIntoRptData("fltrTxtWsCmbValue", entityBean.getFltrTxtWsCmbValue()[i], idRptField);
                insertIntoRptData("fltrTxtWsCmbText", entityBean.getFltrTxtWsCmbText()[i], idRptField);
                insertIntoRptData("fltrTxtWsProject", entityBean.getFltrTxtWsProject()[i], idRptField);
                insertIntoRptData("fltrTxtWsIntrface", entityBean.getFltrTxtWsIntrface()[i], idRptField);
                insertIntoRptData("fltrTxtWsRetType", entityBean.getFltrTxtWsRetType()[i], idRptField);
                insertIntoRptData("fltrTxtWsParams", entityBean.getFltrTxtWsParams()[i], idRptField);
                insertIntoRptData("fltrTxtWsExps", entityBean.getFltrTxtWsExps()[i], idRptField);

                insertIntoRptData("fltrCmbCommonQuery", entityBean.getFltrCmbCommonQuery()[i], idRptField);

                insertIntoRptData("fltrCmbAlign", entityBean.getFltrCmbAlign()[i], idRptField);
                insertIntoRptData("fltrTxtSize", entityBean.getFltrTxtSize()[i], idRptField);
                insertIntoRptData("fltrTxtStyle", entityBean.getFltrTxtStyle()[i], idRptField);
                insertIntoRptData("fltrRbtnReadOnly", entityBean.getFltrRbtnReadOnly()[i], idRptField);
                insertIntoRptData("fltrTxtRows", entityBean.getFltrTxtRows()[i], idRptField);
                insertIntoRptData("fltrTxtCols", entityBean.getFltrTxtCols()[i], idRptField);
                insertIntoRptData("fltrTxtTotalRadio", entityBean.getFltrTxtTotalRadio()[i], idRptField);
                insertIntoRptData("fltrTxtRdoValue", entityBean.getFltrTxtRdoValue()[i], idRptField);
                insertIntoRptData("fltrTxtDefRdoValue", entityBean.getFltrTxtDefRdoValue()[i], idRptField);
            }
        }
        if (entityBean.isAddControl() && entityBean.getControl() != null)
        {
            int addControlLen = entityBean.getControl().length;
            StringBuilder insert_add_control = new StringBuilder();
            insert_add_control.append("INSERT INTO RPT_ADDCONTROL(SRNO_RPT_MAIN,CONTROL,COL)");
            insert_add_control.append("VALUES");
            for (int i = 0; i < addControlLen; i++)
            {
                insert_add_control.append("(");
                insert_add_control.append(idRptMain);
                insert_add_control.append(",\"");
                insert_add_control.append(entityBean.getControl()[i]);
                insert_add_control.append("\",\"");
                insert_add_control.append(entityBean.getCol()[i]);
                insert_add_control.append("\"),");
            }
            insert_add_control.deleteCharAt(insert_add_control.length() - 1);
            SQLU.persist(ALIASNAME, insert_add_control.toString());
        }
        if (entityBean.getChildQueryAlias().length > 1 && entityBean.getChildQuery().length > 1)
        {
            int childQueryLen = entityBean.getChildQuery().length;
            int idChildQuery;
            for (int i = 1; i < childQueryLen; i++)
            {
                entityBean.getChildQuery()[i] = entityBean.getChildQuery()[i].replace("\"", "'");
                insertQuery = "INSERT INTO RPT_CHILD_QUERY(SRNO_RPT_MAIN,CHILD_QUERY_ALIAS,CHILD_QUERY)"
                        + " VALUES(" + idRptMain + ",\"" + entityBean.getChildQueryAlias()[i] + "\",\"" + entityBean.getChildQuery()[i] + "\")";
                SQLU.persist(ALIASNAME, insertQuery);

                idChildQuery = SQLS.getInt(ALIASNAME, "SELECT SRNO FROM RPT_CHILD_QUERY WHERE SRNO_RPT_MAIN = " + idRptMain + " ORDER BY SRNO DESC LIMIT 1");
                insertIntoRptData("childQueryColumn", entityBean.getChildQueryColumns(), idChildQuery);
            }
        }
        //adds remaing details to database
        if (entityBean.isPageFooter())
        {
            insertIntoRptData("pageFooter", String.valueOf(entityBean.isPageFooter()), 0);
            insertIntoRptData("pageFooterColumn", entityBean.getPageFooterColumn(), 0);
        }
        if (entityBean.isGrandTotal())
        {
            insertIntoRptData("grandTotal", String.valueOf(entityBean.isGrandTotal()), 0);
        }
        insertIntoRptData("selectedColumn", entityBean.getSelectedColumns(), 0);
        insertIntoRptData("mainQueryColumn", entityBean.getMainQueryColumns(), 0);
        insertIntoRptData("allColumns", entityBean.getAllColumns(), 0);
        insertIntoRptData("allColumnTypes", entityBean.getAllColumnTypes(), 0);
        //all query and alias
        insertIntoRptData("allAlias", entityBean.getAlias(), 0);
        insertIntoRptData("allQuery", entityBean.getQuery(), 0);

        if (entityBean.isReportType())
        {
            insertIntoRptData("rptControlPosition", entityBean.getRptControlPos(), 0);
        }
        if (entityBean.isFilter())
        {
            insertIntoRptData("fltrControlPosition", entityBean.getFltrControlPos(), 0);
        }
        if (entityBean.getChartName() != null)
        {
            for (int i = 0; i < entityBean.getChartName().size(); i++)
            {
                insertQuery = "INSERT INTO RPT_DATA(SRNO_RPT_MAIN,KEYNAME,VALUE)"
                        + " VALUES(" + idRptMain + ",\"chartName\",\"" + entityBean.getChartName().get(i) + "\")";
                SQLU.persist(ALIASNAME, insertQuery);
            }
            if (entityBean.getChartName().contains("pieChart"))
            {
                insertIntoRptData("pieChartTitle", entityBean.getPieTxtChartTitle(), 0);
                insertIntoRptData("pieChartXColumn", entityBean.getPieCmbXaxisColumn(), 0);
                insertIntoRptData("pieChartYColumn", entityBean.getPieCmbYaxisColumn(), 0);
            }
            if (entityBean.getChartName().contains("barChart"))
            {
                insertIntoRptData("barChartTitle", entityBean.getBarTxtChartTitle(), 0);
                insertIntoRptData("barChartXColumn", entityBean.getBarCmbXaxisColumn(), 0);
                insertIntoRptData("barChartYColumn", entityBean.getBarCmbYaxisColumn(), 0);
                insertIntoRptData("barChartXLabel", entityBean.getBarTxtXLabel(), 0);
                insertIntoRptData("barChartYLabel", entityBean.getBarTxtYLabel(), 0);
                insertIntoRptData("barChartLegendName", entityBean.getBartxtLegendName(), 0);
            }
            if (entityBean.getChartName().contains("lineChart"))
            {
                insertIntoRptData("lineChartTitle", entityBean.getLineTxtChartTitle(), 0);
                insertIntoRptData("lineChartXColumn", entityBean.getLineCmbXaxisColumn(), 0);
                insertIntoRptData("lineChartYColumn", entityBean.getLineCmbYaxisColumn(), 0);
                insertIntoRptData("lineChartXLabel", entityBean.getLineTxtXLabel(), 0);
                insertIntoRptData("lineChartYLable", entityBean.getLineTxtYLabel(), 0);
                insertIntoRptData("lineChartLegendName", entityBean.getLinetxtLegendName(), 0);
            }
            if (entityBean.getChartName().contains("areaChart"))
            {
                insertIntoRptData("areaChartTitle", entityBean.getAreaTxtChartTitle(), 0);
                insertIntoRptData("areaChartXColumn", entityBean.getAreaCmbXaxisColumn(), 0);
                insertIntoRptData("areaChartYColumn", entityBean.getAreaCmbYaxisColumn(), 0);
                insertIntoRptData("areaChartXLabel", entityBean.getAreaTxtXLabel(), 0);
                insertIntoRptData("areaChartYLabel", entityBean.getAreaTxtYLabel(), 0);
                insertIntoRptData("areaChartLegendName", entityBean.getAreatxtLegendName(), 0);
            }
        }
        return idRptMain;
    }

    public SqlRowSet getReportData(final String aliasnm, final String query, final String conType, final Connection con) throws ClassNotFoundException, SQLException
    {
        SqlRowSet srs;
        if ("usingAlias".equals(conType))
        {
            srs = SQLU.getRowSet(aliasnm, query);
        }
        else
        {
            srs = SQLCS.getRowSet(con, query);
        }
        return srs;
    }

    public SqlRowSet getProjectArray() throws ClassNotFoundException, SQLException
    {
        SqlRowSet srs;
        String query;
        query = "SELECT PRJ_ID,PRJ_NAME,DOMAIN_NAME FROM PROJECT_MST WHERE DOMAIN_NAME IS NOT NULL";
        srs = SQLU.getRowSet("wfms2", query);
        return srs;
    }

    public String[] getConnAliasArray() throws SQLException
    {
        DBConnManager dbcm = new DBConnManager();
        String[] strAllAlias = dbcm.getConnAliasArray();
        int aliasLen = strAllAlias.length;
        String tmpStr;
        for (int i = 0, j = 0, idx = 0; i < aliasLen - 1; i++)
        {
            for (j = i, idx = i; j < aliasLen; j++)
            {
                if (strAllAlias[j].compareTo(strAllAlias[idx]) < 0)
                {
                    idx = j;
                }
            }
            if (idx != i)
            {
                tmpStr = strAllAlias[i];
                strAllAlias[i] = strAllAlias[idx];
                strAllAlias[idx] = tmpStr;
            }
        }
        return strAllAlias;
    }

    public void insertIntoRptData(String keyName, String[] inputValues, int idChildQuery) throws ClassNotFoundException, SQLException
    {
        if (inputValues != null)
        {
            for (int i = 0; i < inputValues.length; i++)
            {
                StringBuilder insertQuery = new StringBuilder();
                insertQuery.append("INSERT INTO RPT_DATA(SRNO_RPT_MAIN,KEYNAME,VALUE");
                if (idChildQuery != 0)
                {
                    insertQuery.append(",SRNO_CHILD_QUERY");
                }
                insertQuery.append(")");
                insertQuery.append(" VALUES(");
                insertQuery.append(idRptMain);
                insertQuery.append(",\"");
                insertQuery.append(keyName);
                insertQuery.append("\",\"");
                insertQuery.append(inputValues[i]);
                insertQuery.append("\"");
                if (idChildQuery != 0)
                {
                    insertQuery.append(",");
                    insertQuery.append(idChildQuery);
                }
                insertQuery.append(")");
                SQLU.persist(ALIASNAME, insertQuery.toString());
            }
        }
    }

    public void insertIntoRptData(String KeyName, String Value, int idRptField) throws ClassNotFoundException, SQLException
    {
        String strValue = Value;
        if (Value == null || "".equals(Value) || "-1".equals(Value))
        {
            //Value = "-";
            strValue = "-";
        }

        StringBuilder insertQuery = new StringBuilder();
        insertQuery.append("INSERT INTO RPT_DATA(SRNO_RPT_MAIN,KEYNAME,VALUE");
        if (idRptField != 0)
        {
            insertQuery.append(",SRNO_RPT_FIELD");
        }
        insertQuery.append(")");
        insertQuery.append(" VALUES(");
        insertQuery.append(idRptMain);
        insertQuery.append(",\"");
        insertQuery.append(KeyName);
        insertQuery.append("\",\"");
        //insertQuery.append(Value);
        insertQuery.append(strValue);
        insertQuery.append("\"");
        if (idRptField != 0)
        {
            insertQuery.append(",");
            insertQuery.append(idRptField);
        }
        insertQuery.append(")");
        SQLU.persist(ALIASNAME, insertQuery.toString());

    }

    public SqlRowSet getDetailReportData(int srno) throws ClassNotFoundException, SQLException
    {
        StringBuilder query = new StringBuilder();
        query.append("SELECT RM.*,RA.GRID_CONTROL,RA.GRID_COLUMN,RGF.FOOTER_COLUMN,RGF.FOOTER_CALC,RCQ.CHILD_QUERY_ALIAS,RF.*,RD.*");
        query.append("FROM RPT_MAIN RM");
        query.append(" LEFT JOIN");
        query.append("(");
        query.append("        SELECT RGF.SRNO_RPT_MAIN,IFNULL(GROUP_CONCAT(RGF.GROUP_FTR_COLUMN),'') FOOTER_COLUMN,IFNULL(GROUP_CONCAT(RGF.GROUP_FTR_CALCULATION),'') FOOTER_CALC");
        query.append("        FROM RPT_GRPFOOTER RGF");
        query.append("        WHERE RGF.SRNO_RPT_MAIN =");
        query.append(srno);
        query.append("        GROUP BY RGF.SRNO_RPT_MAIN");
        query.append(")RGF ON RGF.SRNO_RPT_MAIN =  RM.SRNO");
        query.append(" LEFT JOIN");
        query.append("(");
        query.append("        SELECT RCQ.SRNO_RPT_MAIN, IFNULL(GROUP_CONCAT(RCQ.CHILD_QUERY_ALIAS),'') CHILD_QUERY_ALIAS");
        query.append("        FROM RPT_CHILD_QUERY RCQ");
        query.append("        WHERE RCQ.SRNO_RPT_MAIN = ");
        query.append(srno);
        query.append("        GROUP BY RCQ.SRNO_RPT_MAIN");
        query.append(")RCQ ON RCQ.SRNO_RPT_MAIN = RM.SRNO");
        query.append(" LEFT JOIN");
        query.append("(");
        query.append("        SELECT RA.SRNO_RPT_MAIN, IFNULL(GROUP_CONCAT( RA.CONTROL),'') GRID_CONTROL,IFNULL(GROUP_CONCAT( RA.COL),'') GRID_COLUMN");
        query.append("        FROM RPT_ADDCONTROL RA");
        query.append("        WHERE RA.SRNO_RPT_MAIN = ");
        query.append(srno);
        query.append("        GROUP BY RA.SRNO_RPT_MAIN");
        query.append(")RA ON RA.SRNO_RPT_MAIN = RM.SRNO");
        query.append(" LEFT JOIN");
        query.append("(");
        query.append("        SELECT RF.SRNO_RPT_MAIN, IFNULL(GROUP_CONCAT(CASE WHEN RF.TAB_NAME = 'Report Type' THEN RF.LABEL ELSE NULL END),'') RPT_TYPE_LABEL,");
        query.append("            IFNULL(GROUP_CONCAT(CASE WHEN RF.TAB_NAME = 'Filter' THEN RF.LABEL ELSE NULL END),'') FLTR_LABEL,");
        query.append("            IFNULL(GROUP_CONCAT(CASE WHEN RF.TAB_NAME = 'Report Type' THEN RF.CONTROL ELSE NULL END),'') RPT_CONTROL,");
        query.append("            IFNULL(GROUP_CONCAT(CASE WHEN RF.TAB_NAME = 'Filter' THEN RF.CONTROL ELSE NULL END),'') FLTR_CONTROL,");
        query.append("            IFNULL(GROUP_CONCAT(CASE WHEN RF.TAB_NAME = 'Report Type' THEN RF.VALIDATION ELSE NULL END),'') RPT_VALIDATION,");
        query.append("            IFNULL(GROUP_CONCAT(CASE WHEN RF.TAB_NAME = 'Filter' THEN RF.VALIDATION ELSE NULL END),'') FLTR_VALIDATION,");
        query.append("            IFNULL(GROUP_CONCAT(CASE WHEN RF.TAB_NAME = 'Report Type' THEN RF.SELECTION_NATURE ELSE NULL END),'') RPT_SEL_NATURE,");
        query.append("            IFNULL(GROUP_CONCAT(CASE WHEN RF.TAB_NAME = 'Filter' THEN RF.SELECTION_NATURE ELSE NULL END),'') FLTR_SEL_NATURE,");
        query.append("            IFNULL(GROUP_CONCAT(CASE WHEN RF.TAB_NAME = 'Report Type' THEN RF.REMARKS ELSE NULL END SEPARATOR '#$'),'') RPT_REMARKS,");
        query.append("            IFNULL(GROUP_CONCAT(CASE WHEN RF.TAB_NAME = 'Filter' THEN RF.REMARKS ELSE NULL END SEPARATOR '#$'),'') FLTR_REMARKS,");
        query.append("            IFNULL(GROUP_CONCAT(CASE WHEN RF.TAB_NAME = 'Report Type' THEN RF.MANDATORY ELSE NULL END),'') RPT_MANDATORY,");
        query.append("            IFNULL(GROUP_CONCAT(CASE WHEN RF.TAB_NAME = 'Filter' THEN RF.MANDATORY ELSE NULL END),'') FLTR_MANDATORY");
        query.append("        FROM RPT_FIELD RF");
        query.append("        WHERE RF.SRNO_RPT_MAIN = ");
        query.append(srno);
        query.append("        GROUP BY RF.SRNO_RPT_MAIN");
        query.append("    )RF ON RF.SRNO_RPT_MAIN = RM.SRNO");
        query.append(" LEFT JOIN");
        query.append("(");
        query.append("    SELECT RD.SRNO_RPT_MAIN,RD.KEYNAME,RD.VALUE,");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'pageFooter' THEN RD.VALUE ELSE NULL END),'') PAGE_FOOTER,");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'grandTotal' THEN RD.VALUE ELSE NULL END),'') GRAND_TOTAL,");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'fltrControlPosition' THEN RD.VALUE ELSE NULL END),'') FLTR_CNTRL_POS,");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'rptControlPosition' THEN RD.VALUE ELSE NULL END),'') RPT_CNTRL_POS,");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'childQueryColumn' THEN RD.VALUE ELSE NULL END),'') CHILD_QUERY_COLUMNS,  ");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'pageFooterColumn' THEN RD.VALUE ELSE NULL END),'') PAGE_FOOTER_COLUMN,");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'selectedColumn' THEN RD.VALUE ELSE NULL END),'') SELECTED_COLUMNS,");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'mainQueryColumn' THEN RD.VALUE ELSE NULL END),'') MAIN_QUERY_COLUMNS,");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'allColumns' THEN RD.VALUE ELSE NULL END),'') ALL_COLUMNS,");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'allColumnTypes' THEN RD.VALUE ELSE NULL END),'') ALL_COLUMN_TYPES,");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'pieChartTitle' THEN RD.VALUE ELSE NULL END),'') PC_TITLE,");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'pieChartXColumn' THEN RD.VALUE ELSE NULL END),'') PC_XCOLUMN,");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'pieChartYColumn' THEN RD.VALUE ELSE NULL END),'') PC_YCOLUMN,");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'barChartTitle' THEN RD.VALUE ELSE NULL END),'') BC_TITLE,");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'barChartXColumn' THEN RD.VALUE ELSE NULL END),'') BC_XCOLUMN,");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'barChartYColumn' THEN RD.VALUE ELSE NULL END),'') BC_YCOLUMN,");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'barChartXLabel' THEN RD.VALUE ELSE NULL END),'') BC_XLABEL,");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'barChartYLabel' THEN RD.VALUE ELSE NULL END),'') BC_YLABEL,");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'barChartLegendName' THEN RD.VALUE ELSE NULL END),'') BC_LEGEND_NAME,");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'lineChartTitle' THEN RD.VALUE ELSE NULL END),'') LC_TITLE,");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'lineChartXColumn' THEN RD.VALUE ELSE NULL END),'') LC_XCOLUMN,");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'lineChartYColumn' THEN RD.VALUE ELSE NULL END),'') LC_YCOLUMN,");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'lineChartXLabel' THEN RD.VALUE ELSE NULL END),'') LC_XLABEL,");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'lineChartYLabel' THEN RD.VALUE ELSE NULL END),'') LC_YLABEL,");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'lineChartLegendName' THEN RD.VALUE ELSE NULL END),'')LC_LEGEND_NAME,");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'areaChartTitle' THEN RD.VALUE ELSE NULL END),'') AC_TITLE,");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'areaChartXColumn' THEN RD.VALUE ELSE NULL END),'') AC_XCOLUMN,");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'areaChartYColumn' THEN RD.VALUE ELSE NULL END),'') AC_YCOLUMN,");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'areaChartXLabel' THEN RD.VALUE ELSE NULL END),'') AC_XLABEL,");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'areaChartYLabel' THEN RD.VALUE ELSE NULL END),'') AC_YLABEL,");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'areaChartLegendName' THEN RD.VALUE ELSE NULL END),'') AC_LEGEND_NAME,");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'rptTxtId' THEN RD.VALUE ELSE NULL END),'') RPT_ID,");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'rptTxtName' THEN RD.VALUE ELSE NULL END),'') RPT_NAME,");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'rptTxtValue' THEN RD.VALUE ELSE NULL END),'') RPT_VALUE,");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'rptTxtTabIndex' THEN RD.VALUE ELSE NULL END),'') RPT_TABINDEX,");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'rptTxtClass' THEN RD.VALUE ELSE NULL END),'') RPT_CLASS,");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'rptRbtnChecked' THEN RD.VALUE ELSE NULL END),'') RPT_CHECKED,");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'rptTxtMaxLen' THEN RD.VALUE ELSE NULL END),'') RPT_MAXLEN,");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'rptRbtnMultiple' THEN RD.VALUE ELSE NULL END),'') RPT_MULTIPLE,");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'rptCmbAlign' THEN RD.VALUE ELSE NULL END),'') RPT_ALIGN,");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'rptTxtSize' THEN RD.VALUE ELSE NULL END),'') RPT_SIZE,");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'rptTxtStyle' THEN RD.VALUE ELSE NULL END),'') RPT_STYLE,");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'rptRbtnReadOnly' THEN RD.VALUE ELSE NULL END),'') RPT_READONLY,");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'rptTxtRows' THEN RD.VALUE ELSE NULL END),'') RPT_ROWS,");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'rptTxtCols' THEN RD.VALUE ELSE NULL END),'') RPT_COLS,");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'rptTxtTotalRadio' THEN RD.VALUE ELSE NULL END),'') RPT_TOTAL_RADIO,");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'rptTxtRdoValue' THEN RD.VALUE ELSE NULL END),'') RPT_RADIO_VALUE,");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'rptTxtDefRdoValue' THEN RD.VALUE ELSE NULL END),'') RPT_DEF_RADIO,");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'fltrTxtId' THEN RD.VALUE ELSE NULL END),'') FLTR_ID,");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'fltrTxtName' THEN RD.VALUE ELSE NULL END),'') FLTR_NAME,");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'fltrTxtValue' THEN RD.VALUE ELSE NULL END),'') FLTR_VALUE,");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'fltrTxtTabIndex' THEN RD.VALUE ELSE NULL END),'') FLTR_TABINDEX,");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'fltrTxtClass' THEN RD.VALUE ELSE NULL END),'') FLTR_CLASS,");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'fltrRbtnChecked' THEN RD.VALUE ELSE NULL END),'') FLTR_CHECKED,");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'fltrTxtMaxLen' THEN RD.VALUE ELSE NULL END),'') FLTR_MAXLEN,");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'fltrRbtnMultiple' THEN RD.VALUE ELSE NULL END),'') FLTR_MULTIPLE,");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'fltrCmbAlign' THEN RD.VALUE ELSE NULL END),'') FLTR_ALIGN,");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'fltrTxtSize' THEN RD.VALUE ELSE NULL END),'') FLTR_SIZE,");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'fltrTxtStyle' THEN RD.VALUE ELSE NULL END),'') FLTR_STYLE,");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'fltrRbtnReadOnly' THEN RD.VALUE ELSE NULL END),'') FLTR_READONLY,");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'fltrTxtRows' THEN RD.VALUE ELSE NULL END),'') FLTR_ROWS,");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'fltrTxtCols' THEN RD.VALUE ELSE NULL END),'') FLTR_COLS,");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'fltrTxtTotalRadio' THEN RD.VALUE ELSE NULL END),'') FLTR_TOTAL_RADIO,");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'fltrTxtRdoValue' THEN RD.VALUE ELSE NULL END),'') FLTR_RADIO_VALUE,");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'fltrTxtDefRdoValue' THEN RD.VALUE ELSE NULL END),'') FLTR_DEF_RADIO,");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'fltrCmbSource' THEN RD.VALUE ELSE NULL END),'') FLTR_CMB_SOURCE,");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'fltrTxtSrcQuery' THEN RD.VALUE ELSE NULL END SEPARATOR '#$'),'') FLTR_SRC_QUERY,");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'fltrTxtSrcStatic' THEN RD.VALUE ELSE NULL END SEPARATOR '#$'),'') FLTR_SRC_STATIC,");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'allAlias' THEN RD.VALUE ELSE NULL END),'') ALL_ALIAS,");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'fltrTxtWsdlUrl' THEN RD.VALUE ELSE NULL END),'') WSDL_URL,");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'fltrCmbWsMethod' THEN RD.VALUE ELSE NULL END),'') WS_METHOD,");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'fltrTxtWsCmbValue' THEN RD.VALUE ELSE NULL END),'') CMB_VALUE,");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'fltrTxtWsCmbText' THEN RD.VALUE ELSE NULL END),'') CMB_TEXT,");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'fltrTxtWsProject' THEN RD.VALUE ELSE NULL END),'') WS_PROJECT,");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'fltrTxtWsIntrface' THEN RD.VALUE ELSE NULL END),'') WS_INTERFACE,");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'fltrTxtWsRetType' THEN RD.VALUE ELSE NULL END),'') WS_RETTYPE,");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'fltrTxtWsParams' THEN RD.VALUE ELSE NULL END SEPARATOR '#$'),'') WS_PARAM,");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'fltrTxtWsExps' THEN RD.VALUE ELSE NULL END),'') WS_EXPS,");
        query.append("        IFNULL(GROUP_CONCAT(CASE WHEN RD.KEYNAME = 'fltrCmbCommonQuery' THEN RD.VALUE ELSE NULL END),'') COMMON_QUERY");
        query.append("    FROM RPT_DATA RD");
        query.append("    WHERE RD.SRNO_RPT_MAIN = ");
        query.append(srno);
        query.append("    GROUP BY RD.SRNO_RPT_MAIN");
        query.append(")RD ON RD.SRNO_RPT_MAIN = RM.SRNO");
        query.append(" WHERE RM.SRNO = ");
        query.append(srno);
        return SQLU.getRowSet(ALIASNAME, query.toString());
    }

    public SqlRowSet getAllQuery(int srno, String keyName) throws ClassNotFoundException, SQLException
    {
        return SQLU.getRowSet(ALIASNAME, "SELECT VALUE FROM RPT_DATA WHERE SRNO_RPT_MAIN =" + srno + " AND KEYNAME = '" + keyName + "'");
    }

    public List getRefNumber(String prjctName) throws ClassNotFoundException, SQLException
    {
        return SQLU.getList(ALIASNAME, "SELECT DISTINCT RM.SRNO, RM.MODULE_NAME FROM RPT_MAIN RM INNER JOIN RPT_DATA RD ON (RD.SRNO_RPT_MAIN = RM.SRNO) WHERE RM.PROJECT_NAME = '" + prjctName + "' AND RM.REF_NUMBER IS NULL AND RM.MODULE_NAME != ''");
    }

    public SqlRowSet getChildQuery(int srno) throws ClassNotFoundException, SQLException
    {
        StringBuilder query = new StringBuilder();
        query.append("        SELECT CHILD_QUERY");
        query.append("        FROM RPT_CHILD_QUERY");
        query.append("        WHERE SRNO_RPT_MAIN = ");
        query.append(srno);
        return SQLU.getRowSet(ALIASNAME, query.toString());
    }
}
