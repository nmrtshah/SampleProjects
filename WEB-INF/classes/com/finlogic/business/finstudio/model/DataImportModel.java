
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.business.finstudio.model;

import com.csvreader.CsvReader;
import com.finlogic.apps.finstudio.formbean.DataImportFormBean;
import com.finlogic.util.Logger;
import com.svcon.jdbf.DBFReader;
import com.svcon.jdbf.JDBFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Locale;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;

/**
 *
 * @author njuser
 */
public class DataImportModel
{

    public static char delimeter, txtidentifier;

    public String[] getCsvHeader(String fname1, DataImportFormBean data_import_frmbn) throws Exception
    {
        String dirName = finpack.FinPack.getProperty("tomcat1_path") + "/webapps/finstudio/upload";
        String filepath = dirName + "/" + fname1;

        CsvReader reader = new CsvReader(filepath);
        String del = data_import_frmbn.getDel();

        if (del.equals(";"))
        {
            reader.setDelimiter(';');
        }
        else if (del.equals("tab"))
        {
            reader.setDelimiter('\t');
        }
        else if (del.equals("space"))
        {
            reader.setDelimiter(' ');
        }
        delimeter = reader.getDelimiter();
        char identifier = data_import_frmbn.getQuote();
        if (identifier != 'N')
        {
            reader.setTextQualifier(identifier);
        }
        txtidentifier = reader.getTextQualifier();
        reader.readHeaders();
        String mf_field[] = null;

        String fileheader[] = reader.getHeaders();
        mf_field = new String[fileheader.length];

        if (data_import_frmbn.getHeader() == null)
        {
            for (int i = 0; i < fileheader.length; i++)
            {
                mf_field[i] = "Column " + (i + 1);
            }
        }
        else if (data_import_frmbn.getHeader())
        {
            for (int i = 0; i < fileheader.length; i++)
            {
                mf_field[i] = fileheader[i];
            }
        }
        reader.close();
        return mf_field;
    }

    public String[] getXlsHeader(String fname1, DataImportFormBean data_import_frmbn) throws Exception
    {
        String dirName = finpack.FinPack.getProperty("tomcat1_path") + "/webapps/finstudio/upload";
        String filepath = dirName + "/" + fname1;
        String[] mf_field = null;
        FileInputStream finput = null;
        WorkbookSettings ws = null;
        Workbook workbook = null;
        Sheet s = null;

        try
        {
            finput = new FileInputStream(filepath);
            ws = new WorkbookSettings();
            ws.setLocale(new Locale("en", "EN"));
            workbook = Workbook.getWorkbook(finput, ws);

            s = workbook.getSheet(0);

            int columnCount = s.getColumns();
            mf_field = new String[columnCount];

            if (data_import_frmbn.getHeader() == null)
            {
                for (int i = 0; i < columnCount; i++)
                {
                    mf_field[i] = "Column " + (i + 1);
                }
            }
            else if (data_import_frmbn.getHeader())
            {
                for (int i = 0; i < columnCount; i++)
                {
                    mf_field[i] = s.getCell(i, 0).getContents();
                }
            }
        }
        finally
        {
            try
            {
                finput.close();
            }
            catch (Exception e)
            {
            }
            try
            {
                workbook.close();
            }
            catch (Exception e)
            {
            }
        }
        return mf_field;
    }

    public String[] getDbfHeader(String fname1, DataImportFormBean data_import_frmbn) throws FileNotFoundException, JDBFException
    {
        String dirName = finpack.FinPack.getProperty("tomcat1_path") + "/webapps/finstudio/upload";
        String filepath = dirName + "/" + fname1;
        String[] mf_field = null;

        DBFReader dbf = new DBFReader(filepath);

        int totalField = dbf.getFieldCount();
        mf_field = new String[totalField];

        for (int i = 0; i < totalField; i++)
        {
            mf_field[i] = dbf.getField(i).toString();
        }

        return mf_field;
    }

    public String uploadFile(DataImportFormBean data_import_frmbn) throws Exception
    {
        MultipartFile file1 = data_import_frmbn.getFilename();

        String dirName = finpack.FinPack.getProperty("tomcat1_path") + "/webapps/finstudio/upload";

        String fname1 = file1.getOriginalFilename();


        String fname = fname1.substring(0, fname1.indexOf(".")) + "_" + System.currentTimeMillis() + fname1.substring(fname1.indexOf("."));
        String filepath = dirName + "/" + fname;

        File newfile = new File(filepath);
        file1.transferTo(newfile);
        return fname;
    }

    public List getTables(DataImportFormBean data_import_frmbn) throws Exception
    {
        DataImportDataManager dm = new DataImportDataManager();
        return dm.getTables(data_import_frmbn);
    }

    public List getColumn(DataImportFormBean data_import_frmbn) throws Exception
    {
        DataImportDataManager dm = new DataImportDataManager();
        return dm.getColumn(data_import_frmbn);
    }

    public String importData(DataImportFormBean data_import_frmbn, String filenm, String header1) throws Exception
    {
        String dirName = finpack.FinPack.getProperty("tomcat1_path") + "/webapps/finstudio/upload";
        String filepath = dirName + "/" + filenm;


        return fileImport(data_import_frmbn, filepath, header1);

        // return dm.importData(data_import_frmbn, filepath, header1);
    }

    public String fileImport(DataImportFormBean data_import_frmbn, String fpath, String header1) throws Exception
    {
        String dir = null, filenm = null, path1 = null;
        DataImportDataManager dm = new DataImportDataManager();
        int flen = data_import_frmbn.getTabfields().length;
        String[] tabnm = data_import_frmbn.getTables().split("-");
        String[] flds = data_import_frmbn.getFields();
        dir = finpack.FinPack.getProperty("tomcat1_path") + "/webapps/finstudio/log";
        filenm = "File-" + System.currentTimeMillis() + ".txt";
        path1 = dir + "/" + filenm;

        if (fpath.substring(fpath.length() - 4).toLowerCase().equals(".csv"))
        {
            CsvReader reader = new CsvReader(fpath);
            String del = data_import_frmbn.getDel();

            reader.setDelimiter(delimeter);
            reader.setTextQualifier(txtidentifier);

            reader.readHeaders();
            String fileheader[] = reader.getHeaders();
            String query1 = null;

            if (header1.equals(""))
            {
                if (data_import_frmbn.getDbtype().equalsIgnoreCase("oracle"))
                {
                    String tableName = data_import_frmbn.getTables();
                    query1 = "insert into " + tableName + "(";
                }
                else if (data_import_frmbn.getDbtype().equalsIgnoreCase("mysql"))
                {
                    query1 = "insert into " + tabnm[1] + "(";
                }

                for (int i = 0; i < flen; i++)
                {
                    if (data_import_frmbn.getTabfields()[i].equals("0") && i == (flen - 1))
                    {
                        int n = query1.length() - 1;
                        query1 = query1.substring(0, n);
                        continue;
                    }
                    else if (data_import_frmbn.getTabfields()[i].equals("0"))
                    {
                        continue;
                    }
                    else
                    {
                        if (i == (flen - 1))
                        {
                            query1 += data_import_frmbn.getTabfields()[i];
                        }
                        else
                        {
                            query1 += data_import_frmbn.getTabfields()[i] + ",";
                        }
                    }
                }
                query1 += ") values(";

                for (int i = 0; i < flen; i++)
                {
                    if (Integer.parseInt(flds[i]) == 0 && i == (flen - 1))
                    {
                        int n = query1.length() - 1;
                        query1 = query1.substring(0, n);
                        continue;
                    }
                    else if (Integer.parseInt(flds[i]) == 0)
                    {

                        continue;
                    }
                    else
                    {
                        if (i == (flen - 1))
                        {
                            query1 += "'" + fileheader[Integer.parseInt(flds[i]) - 1] + "'";
                        }
                        else
                        {
                            query1 += "'" + fileheader[Integer.parseInt(flds[i]) - 1] + "',";
                        }
                    }
                }

                query1 += ")";
                if ((data_import_frmbn.getDest()).equals("Import Into Database"))
                {
                    dm.importData(data_import_frmbn, query1);
                    // sqlUtility.persist(data_import_frmbn.getAliasname(), query1);
                }
                else if ((data_import_frmbn.getDest()).equals("Save Insert Statement Into File"))
                {
                    insertLogData(path1, query1 + " \n GO");
                }
            }

            for (int j = 0; reader.readRecord(); j++)
            {
                String query2 = null;
                if (data_import_frmbn.getDbtype().equalsIgnoreCase("oracle"))
                {
                    String tableName = data_import_frmbn.getTables();
                    query2 = "insert into " + tableName + "(";
                }
                else if (data_import_frmbn.getDbtype().equalsIgnoreCase("mysql"))
                {
                    query2 = "insert into " + tabnm[1] + "(";
                }

                for (int i = 0; i < flen; i++)
                {
                    if (data_import_frmbn.getTabfields()[i].equals("0") && i == (flen - 1))
                    {
                        int n = query2.length() - 1;
                        query2 = query2.substring(0, n);
                        continue;
                    }
                    else if (data_import_frmbn.getTabfields()[i].equals("0"))
                    {
                        continue;
                    }
                    else
                    {
                        if (i == (flen - 1))
                        {
                            query2 += data_import_frmbn.getTabfields()[i];
                        }
                        else
                        {
                            query2 += data_import_frmbn.getTabfields()[i] + ",";
                        }
                    }
                }
                query2 += ") values(";

                for (int i = 0; i < flen; i++)
                {
                    if (Integer.parseInt(flds[i]) == 0 && i == (flen - 1))
                    {
                        int n = query2.length() - 1;
                        query2 = query2.substring(0, n);
                        continue;
                    }
                    else if (Integer.parseInt(flds[i]) == 0)
                    {
                        continue;
                    }
                    else
                    {
                        if (i == (flen - 1))
                        {
                            query2 += "'" + reader.get(Integer.parseInt(flds[i]) - 1) + "'";
                        }
                        else
                        {
                            query2 += "'" + reader.get(Integer.parseInt(flds[i]) - 1) + "',";
                        }
                    }
                }

                query2 += ")";
                if ((data_import_frmbn.getDest()).equals("Import Into Database"))
                {
                    dm.importData(data_import_frmbn, query2);
                    //sqlUtility.persist(data_import_frmbn.getAliasname(), query2);
                }
                else if ((data_import_frmbn.getDest()).equals("Save Insert Statement Into File"))
                {
                    insertLogData(path1, query2 + " \n GO");
                }
            }
            reader.close();
            ;
        }
        else if (fpath.substring(fpath.length() - 4).toLowerCase().equals(".xls"))
        {
            FileInputStream finput = new FileInputStream(fpath);
            WorkbookSettings ws = null;
            Workbook workbook = null;
            Sheet s = null;

            ws = new WorkbookSettings();
            ws.setLocale(new Locale("en", "EN"));
            workbook = Workbook.getWorkbook(finput, ws);

            s = workbook.getSheet(0);

            int rowCount = s.getRows();

            int j = 0;
            if (header1.equals(""))
            {
                j = 0;
            }
            else
            {
                j = 1;
            }
            while (j < rowCount)
            {
                String query2 = "insert into " + tabnm[1] + "(";

                for (int i = 0; i < flen; i++)
                {
                    if (data_import_frmbn.getTabfields()[i].equals("0") && i == (flen - 1))
                    {
                        int n = query2.length() - 1;
                        query2 = query2.substring(0, n);
                        continue;
                    }
                    else if (data_import_frmbn.getTabfields()[i].equals("0"))
                    {
                        continue;
                    }
                    else
                    {
                        if (i == (flen - 1))
                        {
                            query2 += data_import_frmbn.getTabfields()[i];
                        }
                        else
                        {
                            query2 += data_import_frmbn.getTabfields()[i] + ",";
                        }
                    }
                }
                query2 += ") values(";

                for (int i = 0; i < flen; i++)
                {
                    if (Integer.parseInt(flds[i]) == 0 && i == (flen - 1))
                    {
                        int n = query2.length() - 1;
                        query2 = query2.substring(0, n);
                        continue;
                    }
                    else if (Integer.parseInt(flds[i]) == 0)
                    {
                        continue;
                    }
                    else
                    {
                        if (i == (flen - 1))
                        {
                            query2 += "'" + s.getCell((Integer.parseInt(flds[i]) - 1), j).getContents() + "'";
                        }
                        else
                        {
                            query2 += "'" + s.getCell((Integer.parseInt(flds[i]) - 1), j).getContents() + "',";
                        }
                    }
                }

                query2 += ")";
                if ((data_import_frmbn.getDest()).equals("Import Into Database"))
                {
                    dm.importData(data_import_frmbn, query2);
                    //sqlUtility.persist(data_import_frmbn.getAliasname(), query2);
                }
                else if ((data_import_frmbn.getDest()).equals("Save Insert Statement Into File"))
                {
                    insertLogData(path1, query2 + " \n GO");
                }
                j++;
            }
            finput.close();
            workbook.close();
        }
        else if (fpath.substring(fpath.length() - 4).toLowerCase().equals(".dbf"))
        {
            DBFReader dbf = new DBFReader(fpath);
            Object[] record = null;

            for (int j = 0; dbf.hasNextRecord(); j++)
            {
                record = dbf.nextRecord();
                String query2 = "insert into " + tabnm[1] + "(";

                for (int i = 0; i < flen; i++)
                {
                    if (data_import_frmbn.getTabfields()[i].equals("0") && i == (flen - 1))
                    {
                        int n = query2.length() - 1;
                        query2 = query2.substring(0, n);
                        continue;
                    }
                    else if (data_import_frmbn.getTabfields()[i].equals("0"))
                    {
                        continue;
                    }
                    else
                    {
                        if (i == (flen - 1))
                        {
                            query2 += data_import_frmbn.getTabfields()[i];
                        }
                        else
                        {
                            query2 += data_import_frmbn.getTabfields()[i] + ",";
                        }
                    }
                }
                query2 += ") values(";

                for (int i = 0; i < flen; i++)
                {
                    if (Integer.parseInt(flds[i]) == 0 && i == (flen - 1))
                    {
                        int n = query2.length() - 1;
                        query2 = query2.substring(0, n);
                        continue;
                    }
                    else if (Integer.parseInt(flds[i]) == 0)
                    {
                        continue;
                    }
                    else
                    {
                        if (i == (flen - 1))
                        {
                            query2 += "'" + record[Integer.parseInt(flds[i]) - 1] + "'";
                        }
                        else
                        {
                            query2 += "'" + record[Integer.parseInt(flds[i]) - 1] + "',";
                        }
                    }
                }

                query2 += ")";
                if ((data_import_frmbn.getDest()).equals("Import Into Database"))
                {
                    dm.importData(data_import_frmbn, query2);
                    //sqlUtility.persist(data_import_frmbn.getAliasname(), query2);
                }
                else if ((data_import_frmbn.getDest()).equals("Save Insert Statement Into File"))
                {
                    insertLogData(path1, query2 + " \n GO");
                }
            }
        }
        return filenm;
    }

    void insertLogData(String path, String data)
    {
        finutils.errorhandler.ErrorHandler.PrintInLog(path, data);

    }
}

