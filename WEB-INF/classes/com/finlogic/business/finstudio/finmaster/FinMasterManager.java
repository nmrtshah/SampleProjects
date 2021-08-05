/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.business.finstudio.finmaster;

import com.finlogic.apps.finstudio.finmaster.FinMasterFormBean;
import com.finlogic.util.Logger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;

/**
 *
 * @author Sonam Patel
 */
public class FinMasterManager
{

    private final FinMasterDataManager datamgr = new FinMasterDataManager();

    public SqlRowSet getProjectArray() throws ClassNotFoundException, SQLException
    {
        return datamgr.getProjectArray();
    }

    public String[] getAliasArray()
    {
        String[] strAllAlias = datamgr.getAliasArray();
        String tmpStr;
        int j;
        int idx;
        for (int i = 0; i < strAllAlias.length - 1; i++)
        {
            for (j = i, idx = i; j < strAllAlias.length; j++)
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

    public String getDataBaseType(final FinMasterFormBean formBean) throws ClassNotFoundException, SQLException
    {
        return datamgr.getDatabaseType(formBean);
    }

    public List getTableNames(final FinMasterFormBean formBean) throws ClassNotFoundException, SQLException
    {
        SqlRowSet srs;
        srs = datamgr.getTableNames(formBean);
        ArrayList<String> allTables;
        allTables = new ArrayList<String>();
        if (srs != null)
        {
            while (srs.next())
            {
                allTables.add(srs.getString("TABLE_NAME"));
            }
        }
        Object[] tArr = allTables.toArray();
        Arrays.sort(tArr);
        allTables.clear();
        for (int i = 0; i < tArr.length; i++)
        {
            allTables.add(tArr[i].toString());
        }
        return allTables;
    }

    public List getTableColumnsNames(final FinMasterFormBean formBean) throws ClassNotFoundException, SQLException
    {
        SqlRowSet srs;
        srs = datamgr.getTableColumnsNames(formBean);
        ArrayList<String> allColumns;
        allColumns = new ArrayList<String>();
        if (srs != null)
        {
            while (srs.next())
            {
                allColumns.add(srs.getString("COLUMN_NAME"));
            }
        }
        return allColumns;
    }

    public String getPrimaryColumn(final FinMasterFormBean formBean) throws ClassNotFoundException, SQLException
    {
        return datamgr.getPrimaryColumn(formBean);
    }

    public String getMstTableColumnWidth(final FinMasterFormBean formBean) throws ClassNotFoundException, SQLException
    {
        SqlRowSet srs = datamgr.getMstTableColumnWidth(formBean);
        String allWidth = "";
        if (srs != null)
        {
            while (srs.next())
            {
                if (srs.getString("LENGTH") != null)
                {
                    allWidth += srs.getString("COLUMN_NAME").trim() + ":" + srs.getString("LENGTH").trim() + ":" + srs.getString("DATA_TYPE").trim().toUpperCase() + ",";
                }
                else
                {
                    allWidth += srs.getString("COLUMN_NAME").trim() + ":0:" + srs.getString("DATA_TYPE").trim().toUpperCase() + ",";
                }
            }
            if (!"".equals(allWidth))
            {
                allWidth = allWidth.substring(0, allWidth.length() - 1);
            }
        }
        return allWidth;
    }

    public String checkSequence(final FinMasterFormBean formBean)
    {
        try
        {
            SqlRowSet srs;
            srs = datamgr.checkSequence(formBean);
            if (srs.next())
            {
                return "Exist";
            }
            else
            {
                return "Not Exist";
            }
        }
        catch (Exception e)
        {
            if (e.getMessage().contains(".CURRVAL is not yet defined in this session"))
            {
                return "Exist";
            }
            else
            {
                return "Not Exist";
            }
        }
    }

    public String checkQuery(final FinMasterFormBean formBean, final String tab)
    {
        try
        {
            SqlRowSet srs;
            srs = datamgr.checkQuery(formBean, tab);
            int col;
            col = srs.getMetaData().getColumnCount();
            if (col == 2)
            {
                return "Valid Query";
            }
            else
            {
                return "Query Must Return 2 Columns";
            }
        }
        catch (Exception e)
        {
            return "Invalid Query....." + e.getMessage();
        }
    }

    public int insertIntoDataBase(final FinMasterEntityBean entityean) throws ClassNotFoundException, SQLException
    {
        return datamgr.insertIntoDataBase(entityean);
    }

    public Map<String, List<String>> getColumnsTypes(final FinMasterFormBean formBean) throws ClassNotFoundException, SQLException
    {
        SqlRowSet srs;
        srs = datamgr.getRecordsByTable(formBean);
        SqlRowSetMetaData srmd;
        srmd = srs.getMetaData();
        int numberOfColumns;
        numberOfColumns = srmd.getColumnCount();
        List<String> columnNames;
        columnNames = new ArrayList<String>();
        List<String> columnTypes;
        columnTypes = new ArrayList<String>();
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
            else if (srmd.getColumnTypeName(i).equalsIgnoreCase("BOOLEAN"))
            {
                columnTypes.add("boolean");
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
                    || srmd.getColumnTypeName(i).equalsIgnoreCase("MEDIUMINT")
                    || srmd.getColumnTypeName(i).equalsIgnoreCase("TINYINT"))
            {
                columnTypes.add("Integer");
            }
            else if (srmd.getColumnTypeName(i).equalsIgnoreCase("FLOAT"))
            {
                columnTypes.add("Float");
            }
            else
            {
                columnTypes.add(srmd.getColumnTypeName(i).toUpperCase());
            }
        }
        Map<String, List<String>> map;
        map = new HashMap<String, List<String>>();
        map.put("colNames", columnNames);
        map.put("colTypes", columnTypes);
        return map;
    }

    public String[] getColumnsOfQuery(final FinMasterFormBean formBean, final String query)
    {
        String[] cols;
        try
        {
            SqlRowSetMetaData metaData;
            metaData = datamgr.getColumnsOfQuery(formBean, query).getMetaData();
            cols = metaData.getColumnNames();
        }
        catch (Exception e)
        {
            Logger.ErrorLogger(e);
            cols = new String[0];
        }
        return cols;
    }

    public void setSchema(final FinMasterFormBean formBean) throws ClassNotFoundException, SQLException
    {
        datamgr.setSchema(formBean);
    }
}
