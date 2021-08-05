/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.business.finstudio.model;

import com.csvreader.CsvWriter;
import com.csvreader.CsvWriter.FinalizedException;
import com.finlogic.apps.finstudio.formbean.DataExportFormbean;
import com.finlogic.util.Logger;
import com.svcon.jdbf.DBFWriter;
import com.svcon.jdbf.JDBFException;
import com.svcon.jdbf.JDBField;
import java.io.File;
import java.io.IOException;
import java.util.*;
import jxl.*;
import jxl.write.*;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;

/**
 *
 * @author njuser
 */
public class DataExportModel
{

    private static WritableCellFormat contentnumbercf = null;
    private static WritableCellFormat contentstringcf = null;
    private static WritableCellFormat headercf = null;
    DataExportDataManager dedm = new DataExportDataManager();

    public String submitQuery(DataExportFormbean defb) throws FinalizedException, IOException, JDBFException, WriteException
    {
        finutils.directconn.DBConnManager dbConnManager = new finutils.directconn.DBConnManager();
        String[] aliasList = dbConnManager.getConnAliasArray();
        boolean flagChkAlias = false;
        String message = null;
        String dbType = defb.getDbtype();

        for (int i = 0; i < aliasList.length; i++)
        {
            if (aliasList[i].toLowerCase().equalsIgnoreCase(defb.getAliasname()))
            {
                flagChkAlias = true;
                break;
            }
        }

        if (flagChkAlias == true)
        {
            SqlRowSet srs = null;

            try
            {
                srs = dedm.getData(defb);
            }
            catch (Exception ex)
            {
                return ex.getMessage();
            }
            if (srs.next() == false)
            {
                return "No Records Found";
            }
            SqlRowSetMetaData srmd = srs.getMetaData();

            String filename = defb.getFilenm();
            String dirName = finpack.FinPack.getProperty("tomcat1_path") + "/webapps/finstudio/download";
            String fileName = dirName + "/" + defb.getFilenm();

            int colCount = srmd.getColumnCount();

            boolean flag = false;
            srs.beforeFirst();
            if (filename.substring(filename.length() - 4).equalsIgnoreCase(".csv"))
            {
                CsvWriter csv = new CsvWriter(fileName);
                while (srs.next())
                {
                    for (int n = 0; n < colCount; n++)
                    {
                        csv.write(srs.getString(n + 1));
                    }
                    csv.endRecord();
                }
                csv.close();
                flag = true;
            }
            else if (filename.substring(filename.length() - 4).equalsIgnoreCase(".dbf"))
            {
                int precesion = 0;
                Object[] obj = new Object[colCount];

                JDBField[] jdbfs = new JDBField[colCount];

                for (int i = 0; i < colCount; i++)
                {
                    if (dbType.equalsIgnoreCase("mysql")) 
                    {
                        if (srmd.getColumnTypeName(i + 1).equalsIgnoreCase("VARCHAR2")
                                || srmd.getColumnTypeName(i + 1).equalsIgnoreCase("CHAR"))
                        {

                            jdbfs[i] = new JDBField(srmd.getColumnTypeName(i + 1), 'C', srmd.getPrecision(i + 1), 0);
                        }
                        else if (srmd.getColumnTypeName(i + 1).equalsIgnoreCase("INT")
                                || srmd.getColumnTypeName(i + 1).equalsIgnoreCase("BIGINT"))
                        {
                            if (srmd.getPrecision(i + 1) > 18)
                            {
                                precesion = 18;
                            }
                            else
                            {
                                precesion = srmd.getPrecision(i + 1);
                            }
                            jdbfs[i] = new JDBField(srmd.getColumnName(i + 1), 'N', precesion, 0);
                        }
                        else if (srmd.getColumnTypeName(i + 1).equalsIgnoreCase("DATE")
                                || srmd.getColumnTypeName(i + 1).equalsIgnoreCase("DATETIME"))
                        {
                            jdbfs[i] = new JDBField(srmd.getColumnName(i + 1), 'D', 8, 0);
                        }
                        else if (srmd.getColumnTypeName(i + 1).equalsIgnoreCase("DOUBLE")
                                || srmd.getColumnTypeName(i + 1).equalsIgnoreCase("FLOAT"))
                        {
                            int scale = 0;
                            if (srmd.getPrecision(i + 1) > 20)
                            {
                                precesion = 20;
                            }
                            else
                            {
                                precesion = srmd.getPrecision(i + 1);
                            }
                            if (srmd.getScale(i + 1) > precesion)
                            {
                                scale = precesion - 1;
                            }
                            else
                            {
                                scale = srmd.getScale(i + 1);
                            }
                            jdbfs[i] = new JDBField(srmd.getColumnName(i + 1), 'F', precesion, scale);
                        }
                        else
                        {
                            message = srmd.getColumnTypeName(i + 1) + "MySQL Type not Known.";
                            return message;
                        }
                    }
                    else if (dbType.equalsIgnoreCase("oracle"))
                    {
                        if (srmd.getColumnTypeName(i + 1).equalsIgnoreCase("VARCHAR2")
                                || srmd.getColumnTypeName(i + 1).equalsIgnoreCase("CHAR"))
                        {
                            String value = srmd.getColumnName(i + 1);
                            if (value.length() > 10)
                            {
                                value = srmd.getColumnName(i + 1).substring(0, 9);
                            }
                            jdbfs[i] = new JDBField(value, 'C', srmd.getPrecision(i + 1), 0);
                        }
                        else if (srmd.getColumnTypeName(i + 1).equalsIgnoreCase("NUMBER"))
                        {
                            String value = srmd.getColumnName(i + 1);
                            int scale = 0;

                            if (srmd.getPrecision(i + 1) > 18)
                            {
                                precesion = 18;
                                scale=5;
                            }
                            else
                            {
                                precesion = srmd.getPrecision(i + 1);
                                scale=srmd.getScale(i+1);
                            }
                            
                            if (value.length() > 10)
                            {
                                value = srmd.getColumnName(i + 1).substring(0, 9);
                            }
                            jdbfs[i] = new JDBField(value, 'N', precesion, scale);
                        }
                        else if (srmd.getColumnTypeName(i + 1).equalsIgnoreCase("DATE")
                                || srmd.getColumnTypeName(i + 1).equalsIgnoreCase("DATETIME"))
                        {
                            String value = srmd.getColumnName(i + 1);
                            if (value.length() > 10)
                            {
                                value = srmd.getColumnName(i + 1).substring(0, 9);
                            }
                            jdbfs[i] = new JDBField(value, 'D', 8, 0);
                        }
                        else
                        {
                            message = srmd.getColumnTypeName(i + 1) + "Oracle Type not Known.";
                            return message;
                        }
                    }
                }
                DBFWriter dbf = new DBFWriter(fileName, jdbfs);
                Date dt = new Date();
                dt.setDate(28);
                dt.setMonth(5);
                dt.setYear(1988);

                while (srs.next())
                {
                    for (int n = 0; n < colCount; n++)
                    {
                        if (jdbfs[n].getType() == 'C')
                        {
                            obj[n] = srs.getString(n + 1);
                        }
                        else if (jdbfs[n].getType() == 'N')
                        {
                            if (defb.getDbtype().equalsIgnoreCase("oracle"))
                            {
                                obj[n] = srs.getDouble(n + 1);
                            }
                            else
                            {
                                obj[n] = srs.getLong(n + 1);
                            }
                        }
                        else if (jdbfs[n].getType() == 'D')
                        {
                            if (srs.getDate(n + 1) == null)
                            {
                                obj[n] = dt;
                            }
                            else
                            {
                                obj[n] = srs.getDate(n + 1);
                            }
                        }
                        else if (jdbfs[n].getType() == 'F')
                        {
                            obj[n] = srs.getDouble(n + 1);
                        }
                    }

                    dbf.addRecord(obj);
                }

                dbf.close();
                flag = true;
            }
            else if (filename.substring(filename.length() - 4).equalsIgnoreCase(".xls"))
            {

                File f = new File(fileName);

                WorkbookSettings ws = null;
                WritableWorkbook workbook = null;
                WritableSheet sheet1 = null, sheet2 = null, s = null;

                headercf = new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD));
                headercf.setAlignment(jxl.format.Alignment.CENTRE);
                contentnumbercf = new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 8, WritableFont.NO_BOLD));
                contentnumbercf.setAlignment(jxl.format.Alignment.CENTRE);
                contentnumbercf.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
                contentstringcf = new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 8, WritableFont.NO_BOLD));
                contentstringcf.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
                contentstringcf.setAlignment(jxl.format.Alignment.CENTRE);

                ws = new WorkbookSettings();
                ws.setLocale(new Locale("en", "EN"));

                int sheetNo = 1;
                workbook = Workbook.createWorkbook(f, ws);
                sheet1 = workbook.createSheet("Sheet" + sheetNo, 0);
                s = sheet1;


                for (int i = 0; i < colCount; i++)
                {
                    writestringheader(i, 0, srmd.getColumnName(i + 1), s);
                }
                for (int j = 1; srs.next(); j++)
                {
                    if (j == 65000)
                    {
                        sheet2 = workbook.createSheet("Sheet" + sheetNo, 0);
                        s = sheet2;
                        j = 1;
                    }
                    for (int k = 0; k < colCount; k++)
                    {
                        writestring(k, j, srs.getString(k + 1), s);
                    }
                }

                workbook.write();
                workbook.close();
                flag = true;
            }

            if (flag == true)
            {
                message = "SuccessFully Exported";
            }
            else if (flag == false)
            {
                message = "Fail to Export";
            }
        }
        else
        {
            message = "Alias Does not Exist.";
        }
        return message;
    }

    public static void writestringheader(int column, int row, String content, WritableSheet s) throws WriteException
    {
        s.addCell(new Label(column, row, content, headercf));
    }

    public static void writestring(int column, int row, String content, WritableSheet s) throws WriteException
    {
        s.addCell(new Label(column, row, content, contentstringcf));
    }

    public static void writenumber(int column, int row, String content, WritableSheet s) throws WriteException
    {
        s.addCell(new Label(column, row, content, contentnumbercf));
    }
}
