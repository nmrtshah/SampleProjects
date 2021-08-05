/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.business.finstudio.findhtmlreport;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;

/**
 *
 * @author njuser
 */
public class FinDhtmlReportManager
{

    private final FinDhtmlReportDataManager datamgr = new FinDhtmlReportDataManager();

    public int insert(final FinDhtmlReportDetailEntityBean entity) throws ClassNotFoundException, SQLException, UnsupportedEncodingException
    {
        return datamgr.insert(entity);
    }

    public String validateQuery(final String aliasnm, final String query, final String conType, final Connection con)
    {
        String result = "Valid";
        try
        {
            SqlRowSet srs = datamgr.getReportData(aliasnm, query, conType, con);
            SqlRowSetMetaData srsmd = srs.getMetaData();
            if (srsmd.getColumnCount() != 2)
            {
                result = "column length";
            }
        }
        catch (Exception e)
        {
            result = "Invalid Query......" + e.getMessage();
        }
        return result;
    }

    public Map<String, List<String>> getColumnDetail(final String alias, final String query, final String conType, final Connection con) throws ClassNotFoundException, SQLException
    {
        List<String> columnNames;
        columnNames = new ArrayList<String>();
        List<String> columnTypes;
        columnTypes = new ArrayList<String>();

        SqlRowSet srs;
        srs = datamgr.getReportData(alias, query, conType, con);
        SqlRowSetMetaData srmd;
        srmd = srs.getMetaData();
        int numberOfColumns;
        numberOfColumns = srmd.getColumnCount();
        for (int i = 1; i <= numberOfColumns; i++)
        {
            columnNames.add(srmd.getColumnName(i));
            if (srmd.getColumnTypeName(i).equalsIgnoreCase("VARCHAR")
                    || srmd.getColumnTypeName(i).equalsIgnoreCase("TEXT")
                    || srmd.getColumnTypeName(i).equalsIgnoreCase("MEDIUMTEXT")
                    || srmd.getColumnTypeName(i).equalsIgnoreCase("LONGTEXT")
                    || srmd.getColumnTypeName(i).equalsIgnoreCase("TINYTEXT")
                    || srmd.getColumnTypeName(i).equalsIgnoreCase("CHAR")
                    || srmd.getColumnTypeName(i).equalsIgnoreCase("VARCHAR2"))
            {
                columnTypes.add("String");
            }
            else if (srmd.getColumnTypeName(i).equalsIgnoreCase("BOOLEAN")
                    || srmd.getColumnTypeName(i).equalsIgnoreCase("TINYINT"))
            {
                columnTypes.add("Boolean");
            }
            else if (srmd.getColumnTypeName(i).equalsIgnoreCase("DATE")
                    || srmd.getColumnTypeName(i).equalsIgnoreCase("DATETIME"))
            {
                columnTypes.add("Date");
            }
            else if (srmd.getColumnTypeName(i).equalsIgnoreCase("TIME"))
            {
                columnTypes.add("Time");
            }
            else if (srmd.getColumnTypeName(i).equalsIgnoreCase("TIMESTAMP"))
            {
                columnTypes.add("Timestamp");
            }
            else if (srmd.getColumnTypeName(i).equalsIgnoreCase("BIGINT")
                    || srmd.getColumnTypeName(i).equalsIgnoreCase("LONG"))
            {
                columnTypes.add("Long");
            }
            else if (srmd.getColumnTypeName(i).equalsIgnoreCase("NUMBER"))
            {
                columnTypes.add("Number");
            }
            else if (srmd.getColumnTypeName(i).equalsIgnoreCase("DECIMAL"))
            {
                columnTypes.add("BigDecimal");
            }
            else if (srmd.getColumnTypeName(i).equalsIgnoreCase("DOUBLE")
                    || srmd.getColumnTypeName(i).equalsIgnoreCase("NUMERIC")
                    || srmd.getColumnTypeName(i).equalsIgnoreCase("REAL")
                    || srmd.getColumnTypeName(i).equalsIgnoreCase("VARBINARY"))
            {
                columnTypes.add("Double");
            }
            else if (srmd.getColumnTypeName(i).equalsIgnoreCase("INT")
                    || srmd.getColumnTypeName(i).equalsIgnoreCase("INTEGER")
                    || srmd.getColumnTypeName(i).equalsIgnoreCase("SMALLINT")
                    || srmd.getColumnTypeName(i).equalsIgnoreCase("MEDIUMINT"))
            {
                columnTypes.add("Integer");
            }
            else if (srmd.getColumnTypeName(i).equalsIgnoreCase("FLOAT"))
            {
                columnTypes.add("Float");
            }
            else
            {
                columnTypes.add(srmd.getColumnTypeName(i));
            }
        }
        Map<String, List<String>> colDetails;
        colDetails = new HashMap<String, List<String>>();
        colDetails.put("colNames", columnNames);
        colDetails.put("colTypes", columnTypes);
        return colDetails;
    }

    public SqlRowSet getProjectList() throws ClassNotFoundException, SQLException
    {
        return datamgr.getProjectArray();
    }

    public String[] getAliasList() throws SQLException
    {
        return datamgr.getConnAliasArray();
    }

    public SqlRowSet getMainData(int srno) throws SQLException, ClassNotFoundException
    {
        return datamgr.getDetailReportData(srno);
    }

    public List getRefNumber(String prjctName) throws ClassNotFoundException, SQLException
    {
        return datamgr.getRefNumber(prjctName);
    }

    public SqlRowSet getAllQuery(int srno, String KeyName) throws ClassNotFoundException, SQLException
    {
        return datamgr.getAllQuery(srno, KeyName);
    }

    public SqlRowSet getChildQuery(int srno) throws ClassNotFoundException, SQLException
    {
        return datamgr.getChildQuery(srno);
    }
}
