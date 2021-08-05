/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.apps.finstudio.finreport;

import com.finlogic.business.finstudio.finreport.FinReportDataManager;
import com.finlogic.business.finstudio.finreport.FinReportEntityBean;
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
public class FinReportManager
{

    private FinReportDataManager datamgr = new FinReportDataManager();

    public int getSRNO()
    {
        return datamgr.id_rpt_main;
    }

    public boolean insert(FinReportEntityBean entity) throws Exception
    {
        return datamgr.insert(entity);
    }

    public String validateQuery(String aliasnm, String query)
    {
        try
        {
            datamgr.getReportData(aliasnm, query);
        }
        catch (Exception e)
        {
            return "Invalid Query";
        }
        return "valid";
    }

    public Map<String, List<String>> getColumnDetail(String alias, String query) throws Exception
    {
        SqlRowSet srs = datamgr.getReportData(alias, query);
        SqlRowSetMetaData srmd = srs.getMetaData();
        int numberOfColumns = srmd.getColumnCount();
        List<String> columnNames = new ArrayList<String>();
        List<String> columnTypes = new ArrayList<String>();
        for (int i = 1; i <= numberOfColumns; i++)
        {
            columnNames.add(srmd.getColumnName(i));

            if (srmd.getColumnTypeName(i).toUpperCase().equals("VARCHAR")
                    || srmd.getColumnTypeName(i).toUpperCase().equals("TEXT")
                    || srmd.getColumnTypeName(i).toUpperCase().equals("MEDIUMTEXT")
                    || srmd.getColumnTypeName(i).toUpperCase().equals("LONGTEXT")
                    || srmd.getColumnTypeName(i).toUpperCase().equals("TINYTEXT")
                    || srmd.getColumnTypeName(i).toUpperCase().equals("CHAR")
                    || srmd.getColumnTypeName(i).toUpperCase().equals("VARCHAR2")
                    || srmd.getColumnTypeName(i).toUpperCase().equals("BOOLEAN"))
            {
                columnTypes.add("String");
            }
            else if (srmd.getColumnTypeName(i).toUpperCase().equals("DATE")
                    || srmd.getColumnTypeName(i).toUpperCase().equals("DATETIME"))
            {
                columnTypes.add("Date");
            }
            else if (srmd.getColumnTypeName(i).toUpperCase().equals("TIME"))
            {
                columnTypes.add("Time");
            }
            else if (srmd.getColumnTypeName(i).toUpperCase().equals("TIMESTAMP"))
            {
                columnTypes.add("Timestamp");
            }
            else if (srmd.getColumnTypeName(i).toUpperCase().equals("BIGINT")
                    || srmd.getColumnTypeName(i).toUpperCase().equals("LONG"))
            {
                columnTypes.add("Long");
            }
            else if (srmd.getColumnTypeName(i).toUpperCase().equals("NUMBER"))
            {
                int pres = srmd.getPrecision(i);
                if(pres > 0)
                {
                    columnTypes.add("Float");
                }
                else
                {
                    columnTypes.add("Long");
                }
            }
            else if (srmd.getColumnTypeName(i).toUpperCase().equals("DECIMAL")
                    || srmd.getColumnTypeName(i).toUpperCase().equals("DOUBLE")
                    || srmd.getColumnTypeName(i).toUpperCase().equals("NUMERIC")
                    || srmd.getColumnTypeName(i).toUpperCase().equals("REAL"))
            {
                columnTypes.add("Double");
            }
            else if (srmd.getColumnTypeName(i).toUpperCase().equals("INT")
                    || srmd.getColumnTypeName(i).toUpperCase().equals("INTEGER")
                    || srmd.getColumnTypeName(i).toUpperCase().equals("SMALLINT")
                    || srmd.getColumnTypeName(i).toUpperCase().equals("MEDIUMINT")
                    || srmd.getColumnTypeName(i).toUpperCase().equals("TINYINT"))
            {
                columnTypes.add("Integer");
            }
            else if (srmd.getColumnTypeName(i).toUpperCase().equals("FLOAT"))
            {
                columnTypes.add("Float");
            }
            else
            {
                columnTypes.add(srmd.getColumnTypeName(i).toUpperCase());
            }
        }
        Map<String, List<String>> m = new HashMap<String, List<String>>();
        m.put("colNames", columnNames);
        m.put("colTypes", columnTypes);
        return m;
    }

    public boolean isReqNoExist(String reqNo)
    {
        return datamgr.isReqNoExist(reqNo);
    }
}
