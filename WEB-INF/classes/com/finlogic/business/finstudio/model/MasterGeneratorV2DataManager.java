/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.business.finstudio.model;

import com.finlogic.apps.finstudio.formbean.MasterGeneratorV2FormBean;
import com.finlogic.business.finstudio.entitybean.MasterGeneratorV2EntityBean;
import com.finlogic.util.persistence.SQLSchemaFactory;
import com.finlogic.util.persistence.SQLService;
import java.util.List;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

/**
 *
 * @author njuser
 */
public class MasterGeneratorV2DataManager
{

    SQLService sqlService = new SQLService();

    public List getTableNames(MasterGeneratorV2FormBean formBean, String tabletype) throws Exception
    {
        String query = null;
        String tableName = "";
        if (tabletype.equals("master"))
        {
            tableName = formBean.getMasterTableName();
        }
        else if (tabletype.equals("detail"))
        {
            tableName = formBean.getDetailTableName();
        }

        if ((formBean.getDatabaseType()).equals("MYSQL"))
        {
            query = "SELECT concat(TABLE_SCHEMA,'-',TABLE_NAME) TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME LIKE '" + tableName + "%'";
        }
        else if (formBean.getDatabaseType().equals("Oracle"))
        {
            query = "SELECT TNAME TABLE_NAME FROM SYS.TAB WHERE TNAME LIKE '" + tableName + "%' ORDER BY TNAME";
        }
        return sqlService.getList(formBean.getAliasName(), query);
    }

    public List getColumnNamesOfTable(MasterGeneratorV2FormBean formBean, String tabletype) throws Exception
    {
        String query = null;
        String tableName = "";
        if (tabletype.equals("master"))
        {
            tableName = formBean.getSelectMasterTableName();
        }
        else if (tabletype.equals("detail"))
        {
            tableName = formBean.getSelectDetailTableName();
        }
        if ((formBean.getDatabaseType()).equals("MYSQL"))
        {
            query = "SELECT COLUMN_NAME  FROM INFORMATION_SCHEMA.COLUMNS WHERE concat(TABLE_SCHEMA,'-',TABLE_NAME)='" + tableName + "' ";
        }
        else if (formBean.getDatabaseType().equals("Oracle"))
        {
            query = "SELECT CNAME COLUMN_NAME FROM SYS.COL where TNAME = '" + tableName + "' ";
        }
        return sqlService.getList(formBean.getAliasName(), query);
    }

    public long insertRecord(MasterGeneratorV2EntityBean entityBean) throws Exception
    {
        SqlParameterSource sps = null;
        sps = new BeanPropertySqlParameterSource(entityBean);
        KeyHolder keys = new GeneratedKeyHolder();

        String sqlQueryMG = "INSERT INTO MASTER_GENERATOR_V2(USER_NAME,PROJECT_NAME,DB_TYPE,MODULE_NAME,"
                + " ALIAS_NAME, SELECTED_MASTER_TABLE, SELECTED_DETAIL_TABLE, PK_MASTER, PK_DETAIL, FK_DETAIL)"
                + "VALUES(:userName, :projectName, :databaseType, :moduleName, "
                + " :aliasName, :selectMasterTableName, :selectDetailTableName, :primarykeyMaster, :primarykeyDetail, :foreignkeyDetail )";
        long serialNumber = 0;
        if (sqlService.persist(SQLSchemaFactory.getFinstudioAlias(), sqlQueryMG, keys, sps) > 0)
        {
            serialNumber = keys.getKey().longValue();
        }
        else
        {
            serialNumber = -1;
        }
        return serialNumber;
    }
}
