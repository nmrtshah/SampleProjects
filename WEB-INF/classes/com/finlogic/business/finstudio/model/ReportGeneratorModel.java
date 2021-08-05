/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.business.finstudio.model;

import com.finlogic.apps.finstudio.formbean.ReportGeneratorFormBean;
import com.finlogic.business.finstudio.entitybean.ReportGeneratorDetailEntityBean;
import com.finlogic.business.finstudio.entitybean.ReportGeneratorEntityBean;
import com.finlogic.util.Logger;
import finutils.directconn.DBConnManager;
import java.util.ArrayList;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;

/**
 *
 * @author njuser  
 */
public class ReportGeneratorModel
{

    private ReportGeneratorDataManager rgdm = new ReportGeneratorDataManager();

    public String validate(ReportGeneratorFormBean formBean)
    {
       boolean validAlias = false;
       DBConnManager dbcon = new DBConnManager();
       String aliases[] = dbcon.getConnAliasArray();
       for(String alias : aliases)
       {
            if(alias.equalsIgnoreCase(formBean.getAliasName()))
            { validAlias = true; break; }
       }
       if(validAlias == false)
       {
            return "Invalid Alias Name";
       }
       try
       {
            rgdm.getReportData(formBean);
       }
       catch(Exception e)
       {
            Logger.DataLogger(e.toString());
            return "Invalid Query";
       }
       return "valid";
    }
    public ArrayList<String> getColumnNames(ReportGeneratorFormBean grFormBean) throws Exception
    {
        SqlRowSet srs = rgdm.getReportData(grFormBean);

        ArrayList<String> columnNames = new ArrayList<String>();

        SqlRowSetMetaData srmd = srs.getMetaData();
        int numberOfColumns = srmd.getColumnCount();
        for (int i = 1; i <= numberOfColumns; i++)
        {
            columnNames.add(srmd.getColumnName(i));
        }
        return columnNames;
    }

    public ArrayList<String> getColumnTypes(ReportGeneratorFormBean grFormBean) throws Exception
    {
        SqlRowSet srs = rgdm.getReportData(grFormBean);
        ArrayList<String> columnDataTypes = new ArrayList<String>();
        SqlRowSetMetaData srmd = srs.getMetaData();
        int numberOfColumns = srmd.getColumnCount();
        for (int i = 1; i <= numberOfColumns; i++)
        {
            if(srmd.getColumnTypeName(i).toUpperCase().equals("VARCHAR") ||
                    srmd.getColumnTypeName(i).toUpperCase().equals("TEXT")
                    ||srmd.getColumnTypeName(i).toUpperCase().equals("MEDIUMTEXT")
                    ||srmd.getColumnTypeName(i).toUpperCase().equals("LONGTEXT")
                    ||srmd.getColumnTypeName(i).toUpperCase().equals("TINYTEXT")
                    ||srmd.getColumnTypeName(i).toUpperCase().equals("CHAR")
                    ||srmd.getColumnTypeName(i).toUpperCase().equals("VARCHAR2")
                    ||srmd.getColumnTypeName(i).toUpperCase().equals("BOOLEAN"))
                columnDataTypes.add("java.lang.String");
            else if(srmd.getColumnTypeName(i).toUpperCase().equals("BIGINT")||
                    srmd.getColumnTypeName(i).toUpperCase().equals("NUMBER") ||
                    srmd.getColumnTypeName(i).toUpperCase().equals("LONG"))
                columnDataTypes.add("java.lang.Long");
            else if (srmd.getColumnTypeName(i).toUpperCase().equals("DECIMAL") ||
                    srmd.getColumnTypeName(i).toUpperCase().equals("DOUBLE")||
                    srmd.getColumnTypeName(i).toUpperCase().equals("NUMERIC")||
                    srmd.getColumnTypeName(i).toUpperCase().equals("REAL"))
                columnDataTypes.add("java.lang.Double");
            else if(srmd.getColumnTypeName(i).toUpperCase().equals("DATE")
                    ||srmd.getColumnTypeName(i).toUpperCase().equals("DATETIME"))
                columnDataTypes.add("java.util.Date");
            else if(srmd.getColumnTypeName(i).toUpperCase().equals("TIME"))
                columnDataTypes.add("java.sql.Time");
            else if(srmd.getColumnTypeName(i).toUpperCase().equals("INT")
                    || srmd.getColumnTypeName(i).toUpperCase().equals("INTEGER")
                    || srmd.getColumnTypeName(i).toUpperCase().equals("SMALLINT")
                    || srmd.getColumnTypeName(i).toUpperCase().equals("MEDIUMINT")
                    || srmd.getColumnTypeName(i).toUpperCase().equals("TINYINT"))
                columnDataTypes.add("java.lang.Integer");
            else if(srmd.getColumnTypeName(i).toUpperCase().equals("FLOAT"))
                columnDataTypes.add("java.lang.Float");
            else if(srmd.getColumnTypeName(i).toUpperCase().equals("TIMESTAMP"))
                columnDataTypes.add("java.sql.Timestamp");
            else
                columnDataTypes.add(srmd.getColumnTypeName(i).toUpperCase());
        }
        
        return columnDataTypes;
    } 

    public void insert(ReportGeneratorEntityBean reportGeneratorEntity) throws Exception
    {
        rgdm.insert(reportGeneratorEntity);
    }

    public void insertRGD(ReportGeneratorDetailEntityBean[] rgdEntityArray) throws Exception
    {
        for(ReportGeneratorDetailEntityBean rgdEntity :rgdEntityArray)
        {
            rgdm.insertRGD(rgdEntity);
        }
    }
}
