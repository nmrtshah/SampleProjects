/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.business.finstudio.model;

import com.finlogic.apps.finstudio.formbean.DataImportFormBean;
import java.util.List;

/**
 *
 * @author njuser
 */
public class DataImportDataManager
{

    public List getTables(DataImportFormBean data_import_frmbn) throws Exception
    {
        com.finlogic.util.persistence.SQLUtility sqlUtility = new com.finlogic.util.persistence.SQLUtility();
        String query = null;

        if ((data_import_frmbn.getDbtype()).equals("Mysql"))
        {
            query = "SELECT concat(TABLE_SCHEMA,'-',TABLE_NAME) TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME LIKE '" + data_import_frmbn.getTablename() + "%'";
        }
        else if (data_import_frmbn.getDbtype().equals("Oracle"))
        {
            query = "SELECT TNAME as TABLE_NAME FROM SYS.TAB WHERE TNAME LIKE '" + data_import_frmbn.getTablename() + "%' and TABTYPE='TABLE'";

        }
        return sqlUtility.getList(data_import_frmbn.getAliasname(), query);
    }

    public List getColumn(DataImportFormBean data_import_frmbn) throws Exception
    {
        com.finlogic.util.persistence.SQLUtility sqlUtility = new com.finlogic.util.persistence.SQLUtility();
        String query = null;
        if ((data_import_frmbn.getDbtype()).equals("Mysql"))
        {
            query = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE concat(TABLE_SCHEMA,'-',TABLE_NAME)='" + data_import_frmbn.getTables() + "'";
        }
        else if (data_import_frmbn.getDbtype().equals("Oracle"))
        {
            query = "SELECT CNAME as COLUMN_NAME FROM SYS.COL WHERE TNAME LIKE '" + data_import_frmbn.getTables() + "%' ORDER BY CNAME";
        }
        return sqlUtility.getList(data_import_frmbn.getAliasname(), query);
    }

    public void importData(DataImportFormBean data_import_frmbn, String query) throws Exception
    {
        com.finlogic.util.persistence.SQLUtility sqlUtility = new com.finlogic.util.persistence.SQLUtility();

        sqlUtility.persist(data_import_frmbn.getAliasname(), query);
    }
}
