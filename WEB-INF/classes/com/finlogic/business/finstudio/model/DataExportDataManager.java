/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.business.finstudio.model;

import com.finlogic.apps.finstudio.formbean.DataExportFormbean;
import com.finlogic.util.Logger;
import com.finlogic.util.persistence.SQLService;
import org.springframework.jdbc.support.rowset.SqlRowSet;

/**
 *
 * @author njuser
 */
public class DataExportDataManager
{

    SQLService sqlser = new SQLService();

    public SqlRowSet getData(DataExportFormbean defb) throws Exception
    {
        SqlRowSet srs = sqlser.getRowSet(defb.getAliasname(), defb.getQuery());
        return srs;
    }
}
