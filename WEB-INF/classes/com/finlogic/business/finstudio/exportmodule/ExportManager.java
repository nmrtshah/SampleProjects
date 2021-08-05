/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.business.finstudio.exportmodule;

import java.sql.SQLException;
import org.springframework.jdbc.support.rowset.SqlRowSet;

/**
 *
 * @author njuser
 */
public class ExportManager 
{

    private final ExportDataManager datamanager = new ExportDataManager();

    public SqlRowSet getTableData_Company() throws Exception {
        return datamanager.getTableData_Company();
    }

    public SqlRowSet getTableData_Company_Rnt() throws Exception {
        return datamanager.getTableData_Company_Rnt();
    }

    public SqlRowSet getTableData_Portch() throws Exception {
        return datamanager.getTableData_Portch();
    }

    public SqlRowSet getTableData_Navch() throws Exception {
        return datamanager.getTableData_Navch();
    }

    public SqlRowSet getTableData_Shtype() throws Exception {
        return datamanager.getTableData_Shtype();
    }

    public SqlRowSet getTableData_Schsubtype() throws Exception {
        return datamanager.getTableData_Schsubtype();
    }

    public SqlRowSet getTableData_Opt() throws Exception {
        return datamanager.getTableData_Opt();
    }

    public SqlRowSet getTableData_Regmst() throws Exception {
        return datamanager.getTableData_Regmst();
    }

    public SqlRowSet getTableData__nav() throws Exception {
        return datamanager.getTableData__nav();
    }
    public SqlRowSet getTableData__div(String fromdate) throws Exception
    {
        return datamanager.getTableData__div(fromdate);
    }
    public SqlRowSet getTableDataBonus(String fromdate) throws Exception
    {
        return datamanager.getTableDataBonus(fromdate);
    }
    public SqlRowSet getTableData_TAC_SCHMST() throws Exception
    {
        return datamanager.getTableData_TAC_SCHMST();
    }
    public SqlRowSet getTableData_OLT_SCHMST() throws ClassNotFoundException, SQLException
    {
        return datamanager.getTableData_OLT_SCHMST();
    }
    

}
