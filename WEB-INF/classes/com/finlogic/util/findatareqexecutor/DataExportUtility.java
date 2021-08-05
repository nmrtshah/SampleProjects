/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.findatareqexecutor;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.*;
import oracle.sql.TIMESTAMP;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;

/**
 *
 * @author Sonam Patel
 */
public class DataExportUtility
{

    private WritableCellFormat contentstringcf = null;
    private WritableCellFormat headercf = null;

    public void createExcel(final String fileName, final SqlRowSet srs, final String databaseType) throws IOException, WriteException
    {
        SqlRowSetMetaData srmd;
        srmd = srs.getMetaData();

        File file;
        file = new File(fileName);

        WorkbookSettings ws = null;
        WritableWorkbook workbook = null;
        WritableSheet sheet = null, curSheet = null;

        headercf = new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD));
        headercf.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
        headercf.setAlignment(jxl.format.Alignment.CENTRE);
        contentstringcf = new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 8, WritableFont.NO_BOLD));
        contentstringcf.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
        contentstringcf.setAlignment(jxl.format.Alignment.CENTRE);

        ws = new WorkbookSettings();
        ws.setLocale(new Locale("en", "EN"));

        int sheetNo = 1;
        workbook = Workbook.createWorkbook(file, ws);
        sheet = workbook.createSheet("Sheet" + sheetNo, sheetNo - 1);
        sheetNo++;
        curSheet = sheet;

        List<String> dataTypes = new ArrayList<String>();
        int colCount = srmd.getColumnCount();
        for (int i = 0; i < colCount; i++)
        {
            writeStringHeader(i, 0, srmd.getColumnName(i + 1), curSheet);
            dataTypes.add(srmd.getColumnTypeName(i + 1));
        }

        srs.beforeFirst();
        for (int j = 1; srs.next(); j++)
        {
            if (j == 65001)
            {
                sheet = workbook.createSheet("Sheet" + sheetNo, sheetNo - 1);
                sheetNo++;
                curSheet = sheet;
                j = 1;
                for (int i = 0; i < colCount; i++)
                {
                    writeStringHeader(i, 0, srmd.getColumnName(i + 1), curSheet);
                }
            }
            for (int k = 0; k < colCount; k++)
            {
                if ("MYSQL".equals(databaseType) && (dataTypes.get(k).equals("DOUBLE") || dataTypes.get(k).equals("REAL")))
                {
                    String strDouble = srs.getString(k + 1);
                    if (strDouble != null)
                    {
                        double d = Double.parseDouble(strDouble);
                        writeString(k, j, BigDecimal.valueOf(d).toPlainString(), curSheet);
                    }
                    else
                    {
                        writeString(k, j, null, curSheet);
                    }
                }
                else if ("MYSQL".equals(databaseType) && dataTypes.get(k).equals("FLOAT"))
                {
                    String strFloat = srs.getString(k + 1);
                    if (strFloat != null)
                    {
                        float f = Float.parseFloat(srs.getString(k + 1));
                        writeString(k, j, BigDecimal.valueOf(f).toPlainString(), curSheet);
                    }
                    else
                    {
                        writeString(k, j, null, curSheet);
                    }
                }
                else if ("ORACLE".equals(databaseType) && dataTypes.get(k).equals("TIMESTAMP"))
                {
                    Object obj = srs.getObject(k + 1);
                    if (obj != null)
                    {
                        TIMESTAMP t = (TIMESTAMP) obj;
                        writeString(k, j, t.stringValue(), curSheet);
                    }
                    else
                    {
                        writeString(k, j, null, curSheet);
                    }
                }
                else
                {
                    writeString(k, j, srs.getString(k + 1), curSheet);
                }
            }
        }

        workbook.write();
        workbook.close();
    }

    private void writeStringHeader(final int column, final int row, final String content, final WritableSheet s) throws WriteException
    {
        s.addCell(new Label(column, row, content, headercf));
    }

    private void writeString(final int column, final int row, final String content, final WritableSheet s) throws WriteException
    {
        s.addCell(new Label(column, row, content, contentstringcf));
    }

    public void createTextFile(final String fileName, final SqlRowSet srs) throws IOException, WriteException
    {
        PrintWriter pw;
        pw = new PrintWriter(fileName);
        String str = "Object Does Not Exist.";
        if (srs != null)
        {
            SqlRowSetMetaData metaData = srs.getMetaData();
            int totalColumns = metaData.getColumnCount();
            String columnName = "";
            if (totalColumns == 1)
            {
                columnName = "BACKUP";
            }
            else if (totalColumns > 1)
            {
                for (int i = 1; i <= totalColumns; i++)
                {
                    if ("Create Function".equals(metaData.getColumnName(i)))
                    {
                        columnName = "Create Function";
                        break;
                    }
                    if ("Create Procedure".equals(metaData.getColumnName(i)))
                    {
                        columnName = "Create Procedure";
                        break;
                    }
                    if ("SQL Original Statement".equals(metaData.getColumnName(i)))
                    {
                        columnName = "SQL Original Statement";
                        break;
                    }
                }
            }
            srs.beforeFirst();
            if (!"".equals(columnName))
            {
                while (srs.next())
                {
                    pw.println(srs.getString(columnName));
                    str = "";
                }
            }
            if (!"".equals(str))
            {
                pw.println(str);
            }
        }
        pw.close();
    }
}
