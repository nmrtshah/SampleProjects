/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.business.finstudio.model;

import com.finlogic.apps.finstudio.formbean.MasterGeneratorFormBean;
import com.finlogic.business.finstudio.entitybean.MasterGeneratorEntityBean;
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
public class MasterGeneratorDataManager
{

    SQLService sqlService = new SQLService();

    public List getTableNames(MasterGeneratorFormBean formBean) throws Exception
    {
        String query = null;

        if ((formBean.getDatabaseType()).equals("MYSQL"))
        {
            query = "SELECT concat(TABLE_SCHEMA,'-',TABLE_NAME) TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME LIKE '" + formBean.getTableName() + "%'";
        }
        else if (formBean.getDatabaseType().equals("Oracle"))
        {
            query = "SELECT TNAME TABLE_NAME FROM SYS.TAB WHERE TNAME LIKE '" + formBean.getTableName() + "%' ORDER BY TNAME";
        }
        return sqlService.getList(formBean.getAliasName(), query);
    }

    public List getColumnNamesOfTable(MasterGeneratorFormBean formBean) throws Exception
    {
        String query = null;
        if ((formBean.getDatabaseType()).equals("MYSQL"))
        {
            query = "SELECT COLUMN_NAME  FROM INFORMATION_SCHEMA.COLUMNS WHERE concat(TABLE_SCHEMA,'-',TABLE_NAME)='" + formBean.getSelectTableName() + "' ";
        }
        else if (formBean.getDatabaseType().equals("Oracle"))
        {
            query = "SELECT CNAME COLUMN_NAME FROM SYS.COL where TNAME = '" + formBean.getSelectTableName() + "' ";
        }
        return sqlService.getList(formBean.getAliasName(), query);
    }

    public long insertRecord(MasterGeneratorEntityBean entityBean) throws Exception
    {
        SqlParameterSource sps = null;
        sps = new BeanPropertySqlParameterSource(entityBean);
        KeyHolder keys = new GeneratedKeyHolder();

        String sqlQueryMG = "INSERT INTO MASTER_GENERATOR(USER_NAME,PROJECT_NAME,MODULE_NAME,DATABASE_TYPE,ALIAS_NAME,SELECTED_TABLE_NAME,PRIMARYKEY)"
                + "VALUES(:userName,:projectName,:moduleName,:databaseType,:aliasName,:selectTableName,:primarykey)";

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
