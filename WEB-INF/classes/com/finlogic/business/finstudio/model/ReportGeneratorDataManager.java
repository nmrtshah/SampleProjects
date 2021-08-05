/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.business.finstudio.model;

import com.finlogic.apps.finstudio.formbean.ReportGeneratorFormBean;
import com.finlogic.business.finstudio.entitybean.ReportGeneratorDetailEntityBean;
import com.finlogic.business.finstudio.entitybean.ReportGeneratorEntityBean;
import com.finlogic.util.persistence.SQLSchemaFactory;
import com.finlogic.util.persistence.SQLService;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;

/**
 *
 * @author njuser
 */
public class ReportGeneratorDataManager
{

    private SQLService sqlService = new SQLService();

    public void insert(ReportGeneratorEntityBean objEntityBean) throws Exception
    {

        SqlParameterSource sps = null;
        sps = new BeanPropertySqlParameterSource(objEntityBean);
        KeyHolder keys = new GeneratedKeyHolder();

        String sqlQueryRG = "INSERT INTO REPORT_GENERATOR(SR_NUMBER,USER_NAME,MODULE_NAME,ALIAS_NAME,QUERY,"
                + "RECORD_COUNT_QUERY,GRID,GRID_COLUMN_PK,GROUPING,PAGING,ADD_CONTROL,PDF) "
                + "VALUES(:serialNo,:userName,:moduleName,:aliasName,:query,"
                + ":recordCountQuery,:grid,:gridColumnPK,:grouping,:paging,:addControl,:pdf)";

        long serialNumber = 0;
        if (sqlService.persist(SQLSchemaFactory.getFinstudioAlias(), sqlQueryRG, keys, sps) > 0)
        {
            serialNumber = keys.getKey().longValue();
        }
        else
        {
            serialNumber = -1;
        }
        objEntityBean.setSerialNo(serialNumber);
    }

    public void insertRGD(ReportGeneratorDetailEntityBean objRGEntityBean) throws Exception
    {
        SqlParameterSource sps = null;
        sps = new BeanPropertySqlParameterSource(objRGEntityBean);

        String sqlQueryRGD = "INSERT INTO REPORT_GENERATOR_DETAIL"
                + "(SR_NUMBER,RG_SR_NUMBER,CONTROL_NAME,CONTROL_FIELD,CONTROL_INDEX)"
                + "VALUES(:serialNo,:rgSerialNo,:controlName,:controlField,:controlIndex)";
        sqlService.persist(SQLSchemaFactory.getFinstudioAlias(), sqlQueryRGD, sps);
    }

    public SqlRowSet getReportData(ReportGeneratorFormBean rgFormBean) throws Exception
    {
        return sqlService.getRowSet(rgFormBean.getAliasName(), rgFormBean.getQuery());
    }
}
