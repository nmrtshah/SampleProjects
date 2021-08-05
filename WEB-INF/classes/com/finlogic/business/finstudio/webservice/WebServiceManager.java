/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.business.finstudio.webservice;

import java.sql.SQLException;
import org.springframework.jdbc.support.rowset.SqlRowSet;

/**
 *
 * @author Sonam Patel
 */
public class WebServiceManager
{
    private final WebServiceDataManager datamgr = new WebServiceDataManager();

    public SqlRowSet getProjectArray() throws ClassNotFoundException, SQLException
    {
        return datamgr.getProjectArray();
    }

    public int insertIntoDataBase(final WebServiceEntityBean entityean) throws ClassNotFoundException, SQLException
    {
        return datamgr.insertIntoDataBase(entityean);
    }

    public SqlRowSet fetchRecord(final String srno) throws ClassNotFoundException, SQLException
    {
        return datamgr.fetchRecord(srno);
    }

    public void deleteRecord(final String srno) throws ClassNotFoundException, SQLException
    {
        datamgr.deleteRecord(srno);
    }
}
