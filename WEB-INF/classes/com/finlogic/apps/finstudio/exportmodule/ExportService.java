/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.apps.finstudio.exportmodule;

import com.finlogic.business.finstudio.exportmodule.ExportManager;
import com.finlogic.util.Logger;
import com.finlogic.util.disposition.ContentDisposition;
import com.finlogic.util.disposition.ContentDispositionType;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;

/**
 *
 * @author njuser
 */
public class ExportService
{

    private final ExportManager manager = new ExportManager();

    protected String generateCsvFile_company(String exportfilename) throws Exception
    {
        String result = "success";
//        try
//        {
            File sourceFile = new File(exportfilename);
            FileWriter fileWriter = null;

            if (sourceFile.exists())
            {
                sourceFile.delete();
            }

            if (!sourceFile.exists())
            {
                try
                {
                    fileWriter = new FileWriter(sourceFile);
                    SqlRowSet tablename = manager.getTableData_Company();
                    if (tablename!=null && tablename.next())
                    {
                        fileWriter.append("CCODE,CDESC,WEBSITE,ENTRY_DATE,EFORM,PEDIGREE,AMC_CODE_BSE,AMC_CODE_NSE");
                        fileWriter.append("\n");
                        
                        while(tablename.next())
                        {
                            fileWriter.append(tablename.getString("CCODE") == null ? null : '"' + tablename.getString("CCODE").trim() + '"');
                            fileWriter.append( ",");
                            fileWriter.append(tablename.getString("CDESC") == null ? null : '"' + tablename.getString("CDESC").trim() + '"');
                            fileWriter.append( ",");
                            fileWriter.append(tablename.getString("WEBSITE") == null ? null : '"' + tablename.getString("WEBSITE") + '"');
                            fileWriter.append( ",");
                            fileWriter.append(tablename.getString("ENTRY_DATE").equals("null") ? "N/A" : '"' + tablename.getString("ENTRY_DATE") + '"');
                            fileWriter.append( ",");
                            fileWriter.append(tablename.getString("EFORM") == null ? null : '"' + tablename.getString("EFORM").trim() + '"');
                            fileWriter.append( ",");
                            fileWriter.append(tablename.getString("PEDIGREE") == null ? null : '"' + tablename.getString("PEDIGREE") + '"');
                            fileWriter.append( ",");
                            fileWriter.append(tablename.getString("AMC_CODE_BSE") == null ? null : '"' + tablename.getString("AMC_CODE_BSE") + '"');
                            fileWriter.append( ",");
                            fileWriter.append(tablename.getString("AMC_CODE_NSE") == null ? null : '"' + tablename.getString("AMC_CODE_NSE") + '"');
                            fileWriter.append("\n");
                        }
                    }
                }
                catch (Exception e)
                {
                    result = "failure";
                    Logger.DataLogger("Error in CsvFileWriter !!!" + e.getMessage());
                }
                finally
                {
                    try
                    {
                        fileWriter.flush();
                        fileWriter.close();
                    }
                    catch (IOException e)
                    {
                        result = "failure";
                        Logger.DataLogger("Error while flushing/closing fileWriter !!!" + e.getMessage());
                    }
                }

            }
            else
            {
                if (!sourceFile.exists())
                {
                    fileWriter = new FileWriter(exportfilename);
                    fileWriter.append("CCODE,CDESC,WEBSITE,ENTRY_DATE,EFORM,PEDIGREE,AMC_CODE_BSE,AMC_CODE_NSE");
                    fileWriter.append("\n");
                    fileWriter.append("No Records Found.");
                    fileWriter.flush();
                    fileWriter.close();
                }
            }
//        }
//        catch (Exception e)
//        {
//            result = "failure";
//            String s = e.toString();
//            for (int exi = 0; exi < e.getStackTrace().length; exi++)
//            {
//                s = s + exi + " ClassName  : " + e.getStackTrace()[exi].getClassName() + "\n";
//                s = s + exi + " MethodName : " + e.getStackTrace()[exi].getMethodName() + "\n";
//                s = s + exi + " LineNumber : " + e.getStackTrace()[exi].getLineNumber() + "\n";
//            }
//            Logger.DataLogger("in export service method name generateCsvFile_company " + s);
//        }
        
        return result;
    }

    protected String generateCsvFile_companyrnt(String exportfilename) throws Exception
    {
        String result = "success";
//        try
//        {
            File sourceFile = new File(exportfilename);
            FileWriter fileWriter = null;

            if (sourceFile.exists())
            {

                sourceFile.delete();
            }

            if (!sourceFile.exists())
            {
                try
                {
                    fileWriter = new FileWriter(sourceFile);
                    SqlRowSet tablename = manager.getTableData_Company_Rnt();
                    if (tablename!=null && tablename.next())
                    {
                        fileWriter.append("RNTCODE,AMCCODE,CCODE");
                        fileWriter.append("\n");
                        while(tablename.next())
                        {
                            fileWriter.append(tablename.getString("RNTCODE") == null ? null : '"' + tablename.getString("RNTCODE").trim() + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("AMCCODE") == null ? null : '"' + tablename.getString("AMCCODE").trim() + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("CCODE") == null ? null : '"' + tablename.getString("CCODE").trim() + '"');
                            fileWriter.append("\n");
                        }
                    }

                }
                catch (Exception e)
                {
                    result = "failure";
                    Logger.DataLogger("Error in CsvFileWriter !!!" + e.getMessage());
                }
                finally
                {
                    try
                    {
                        fileWriter.flush();
                        fileWriter.close();
                    }
                    catch (IOException e)
                    {
                        result = "failure";
                        Logger.DataLogger("Error while flushing/closing fileWriter !!!" + e.getMessage());
                    }
                }
            }
            else
            {
                if (!sourceFile.exists())
                {
                    fileWriter = new FileWriter(exportfilename);
                    fileWriter.append("RNTCODE,AMCCODE,CCODE");
                    fileWriter.append("\n");
                    fileWriter.append("No Records Found.");
                    fileWriter.flush();
                    fileWriter.close();
                }
            }
//        }
//        catch (Exception e)
//        {
//            result = "failure";
//            String s = e.toString();
//            for (int exi = 0; exi < e.getStackTrace().length; exi++)
//            {
//                s = s + exi + " ClassName  : " + e.getStackTrace()[exi].getClassName() + "\n";
//                s = s + exi + " MethodName : " + e.getStackTrace()[exi].getMethodName() + "\n";
//                s = s + exi + " LineNumber : " + e.getStackTrace()[exi].getLineNumber() + "\n";
//            }
//            Logger.DataLogger("in export service method name generateCsvFile_companyrnt " + s);
//        }
        return result;
    }

    protected String generateCsvFile_portch(String exportfilename) throws Exception
    {
        String result = "success";
//        try
//        {
            File sourceFile = new File(exportfilename);
            FileWriter fileWriter = null;

            if (sourceFile.exists())
            {

                sourceFile.delete();
            }

            if (!sourceFile.exists())
            {
                try
                {
                    fileWriter = new FileWriter(sourceFile);
                    SqlRowSet tablename = manager.getTableData_Portch();

                    if (tablename!=null && tablename.next())
                    {
                        fileWriter.append("CCODE,SCHCODE,SCHNAME,ENTRY_DATE,MARK4DELETE,MARKET_CAP,PORT_SCHTYPE,PORT_SCHSUBTYPE,NFOSTART_DT,NFOEND_DT,SCND_INS_DT,PORT_SCHSTATUS,PORT_REGSTRAR,PORT_TERMINATE_DT,ALLOTE_INC_DT,OBJECTIVE,FUND_COMMENTARY,MECODE,FUNDTYPE,TERMINATION_LEVEL,INCEPTION_LEVEL,COLOR_ID");
                        fileWriter.append("\n");

                        while (tablename.next())
                        {
                            

                            fileWriter.append(tablename.getString("CCODE") == null ? null : '"' + tablename.getString("CCODE") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("SCHCODE") == null ? null : '"' + tablename.getString("SCHCODE") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("SCHNAME") == null ? null : '"' + tablename.getString("SCHNAME") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("ENTRY_DATE").equals("null") ? "N/A" : '"' + tablename.getString("ENTRY_DATE") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("MARK4DELETE") == null ? null : '"' + tablename.getString("MARK4DELETE") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("MARKET_CAP") == null ? null : '"' + tablename.getString("MARKET_CAP") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("PORT_SCHTYPE") == null ? null : '"' + tablename.getString("PORT_SCHTYPE") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("PORT_SCHSUBTYPE") == null ? null : '"' + tablename.getString("PORT_SCHSUBTYPE") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("NFOSTART_DT").equals("null") ? "N/A" : '"' + tablename.getString("NFOSTART_DT") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("NFOEND_DT").equals("null") ? "N/A" : '"' + tablename.getString("NFOEND_DT") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("SCND_INS_DT").equals("null") ? "N/A" : '"' + tablename.getString("SCND_INS_DT") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("PORT_SCHSTATUS") == null ? null : '"' + tablename.getString("PORT_SCHSTATUS") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("PORT_REGSTRAR") == null ? null : '"' + tablename.getString("PORT_REGSTRAR") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("PORT_TERMINATE_DT").equals("null") ? "N/A" : '"' + tablename.getString("PORT_TERMINATE_DT") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("ALLOTE_INC_DT").equals("null") ? "N/A" : '"' + tablename.getString("ALLOTE_INC_DT") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("OBJECTIVE") == null ? null : '"' + tablename.getString("OBJECTIVE") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("FUND_COMMENTARY") == null ? null : '"' + tablename.getString("FUND_COMMENTARY") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("MECODE") == null ? null : '"' + tablename.getString("MECODE") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("FUNDTYPE") == null ? null : '"' + tablename.getString("FUNDTYPE") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("TERMINATION_LEVEL") == null ? null : '"' + tablename.getString("TERMINATION_LEVEL") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("INCEPTION_LEVEL") == null ? null : '"' + tablename.getString("INCEPTION_LEVEL") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("COLOR_ID") == null ? null : '"' + tablename.getString("COLOR_ID") + '"');
                            fileWriter.append("\n");
                        }
                    }
                }
                catch (Exception e)
                {
                    result = "failure";
                    Logger.DataLogger("Error in CsvFileWriter !!!" + e.getMessage());
                }
                finally
                {
                    try
                    {
                        fileWriter.flush();
                        fileWriter.close();
                    }
                    catch (IOException e)
                    {
                        result = "failure";
                        Logger.DataLogger("Error while flushing/closing fileWriter !!!" + e.getMessage());
                    }
                }
            }
            else
            {
                if (!sourceFile.exists())
                {
                    fileWriter = new FileWriter(exportfilename);
                    fileWriter.append("CCODE,SCHCODE,SCHNAME,ENTRY_DATE,MARK4DELETE,MARKET_CAP,PORT_SCHTYPE,PORT_SCHSUBTYPE,NFOSTART_DT,NFOEND_DT,SCND_INS_DT,PORT_SCHSTATUS,PORT_REGSTRAR,PORT_TERMINATE_DT,ALLOTE_INC_DT,OBJECTIVE,FUND_COMMENTARY,MECODE,FUNDTYPE,TERMINATION_LEVEL,INCEPTION_LEVEL,COLOR_ID");
                    fileWriter.append("\n");
                    fileWriter.append("No Records Found.");
                    fileWriter.flush();
                    fileWriter.close();
                }
            }
//        }
//        catch (Exception e)
//        {
//            result = "failure";
//            String s = e.toString();
//            for (int exi = 0; exi < e.getStackTrace().length; exi++)
//            {
//                s = s + exi + " ClassName  : " + e.getStackTrace()[exi].getClassName() + "\n";
//                s = s + exi + " MethodName : " + e.getStackTrace()[exi].getMethodName() + "\n";
//                s = s + exi + " LineNumber : " + e.getStackTrace()[exi].getLineNumber() + "\n";
//            }
//            Logger.DataLogger("in export service method name generateCsvFile_portch " + s);
//        }
        return result;
    }

    protected String generateCsvFile_navch(String exportfilename) throws Exception
    {
        String result = "success";
//        try
//        {
            File sourceFile = new File(exportfilename);
            FileWriter fileWriter = null;

            if (sourceFile.exists())
            {

                sourceFile.delete();
            }

            if (!sourceFile.exists())
            {
                try
                {
                    fileWriter = new FileWriter(sourceFile);
                    SqlRowSet tablename = manager.getTableData_Navch();
                     

                     if(tablename!=null && tablename.next())
                     {
                         fileWriter.append("CCODE,SCHCODE,SCHNAME,PORTSCH,SCHTYPE,SCHSUBTYPE,OPT,UPCODE,INVTYPE,BROKCODE,TERMINATED,TDATE,ENTRY_DATE,SUBOPT,TRNUPCODE,IPODATE,AMFCODE,REGCODE,DIV_FREQ,IPODATE_TRN,MECODE,INCEPTION_DT,TRNUPCODE_2,BROKCODE_2,PLAN");
                         fileWriter.append("\n");
                                
                        while (tablename.next())
                        {
                            

                            fileWriter.append(tablename.getString("CCODE") == null ? null : '"' + tablename.getString("CCODE") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("SCHCODE") == null ? null : '"' + tablename.getString("SCHCODE") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("SCHNAME") == null ? null : '"' + tablename.getString("SCHNAME") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("PORTSCH") == null ? null : '"' + tablename.getString("PORTSCH") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("SCHTYPE") == null ? null : '"' + tablename.getString("SCHTYPE") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("SCHSUBTYPE") == null ? null : '"' + tablename.getString("SCHSUBTYPE") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("OPT") == null ? null : '"' + tablename.getString("OPT") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("UPCODE") == null ? null : '"' + tablename.getString("UPCODE") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("INVTYPE") == null ? null : '"' + tablename.getString("INVTYPE") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("BROKCODE") == null ? null : '"' + tablename.getString("BROKCODE") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("TERMINATED") == null ? null : '"' + tablename.getString("TERMINATED") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("TDATE").equals("null") ? "N/A" : '"' + tablename.getString("TDATE") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("ENTRY_DATE").equals("null") ? "N/A" : '"' + tablename.getString("ENTRY_DATE") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("SUBOPT") == null ? null : '"' + tablename.getString("SUBOPT") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("TRNUPCODE") == null ? null : '"' + tablename.getString("TRNUPCODE") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("IPODATE").equals("null") ? "N/A" : '"' + tablename.getString("IPODATE") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("AMFCODE") == null ? null : '"' + tablename.getString("AMFCODE") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("REGCODE") == null ? null : '"' + tablename.getString("REGCODE") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("DIV_FREQ") == null ? null : '"' + tablename.getString("DIV_FREQ") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("IPODATE_TRN").equals("null") ? "N/A" : '"' + tablename.getString("IPODATE_TRN") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("MECODE") == null ? null : '"' + tablename.getString("MECODE") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("INCEPTION_DT").equals("null") ? "N/A" : '"' + tablename.getString("INCEPTION_DT") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("TRNUPCODE_2") == null ? null : '"' + tablename.getString("TRNUPCODE_2") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("BROKCODE_2") == null ? null : '"' + tablename.getString("BROKCODE_2") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("PLAN") == null ? null : '"' + tablename.getString("PLAN") + '"');
                            fileWriter.append("\n");
                             
                        }
                    }
                }
                catch (Exception e)
                {
                    result = "failure";
                    Logger.DataLogger("Error in CsvFileWriter !!!" + e.getMessage());
                }
                finally
                {
                    try
                    {
                        fileWriter.flush();
                        fileWriter.close();
                    }
                    catch (IOException e)
                    {
                        result = "failure";
                        Logger.DataLogger("Error while flushing/closing fileWriter !!!" + e.getMessage());
                    }
                }
            }
            else
            {
                if (!sourceFile.exists())
                {
                    fileWriter = new FileWriter(exportfilename);
                    fileWriter.append("CCODE,SCHCODE,SCHNAME,PORTSCH,SCHTYPE,SCHSUBTYPE,OPT,UPCODE,INVTYPE,BROKCODE,TERMINATED,TDATE,ENTRY_DATE,SUBOPT,TRNUPCODE,IPODATE,AMFCODE,REGCODE,DIV_FREQ,IPODATE_TRN,MECODE,INCEPTION_DT,TRNUPCODE_2,BROKCODE_2,PLAN");
                    fileWriter.append("\n");
                    fileWriter.append("No Records Found.");
                    fileWriter.flush();
                    fileWriter.close();
                }
            }
//        }
//        catch (Exception e)
//        {
//            result = "failure";
//            String s = e.toString();
//            for (int exi = 0; exi < e.getStackTrace().length; exi++)
//            {
//                s = s + exi + " ClassName  : " + e.getStackTrace()[exi].getClassName() + "\n";
//                s = s + exi + " MethodName : " + e.getStackTrace()[exi].getMethodName() + "\n";
//                s = s + exi + " LineNumber : " + e.getStackTrace()[exi].getLineNumber() + "\n";
//            }
//            Logger.DataLogger("in export service method name generateCsvFile_navch " + s);
//        }
        return result;
    }

    protected String generateCsvFile_schtype(String exportfilename) throws Exception
    {
        String result = "success";
//        try
//        {
            File sourceFile = new File(exportfilename);
            FileWriter fileWriter = null;

            if (sourceFile.exists())
            {

                sourceFile.delete();
            }

            if (!sourceFile.exists())
            {
                try
                {
                    fileWriter = new FileWriter(sourceFile);
                    SqlRowSet tablename = manager.getTableData_Shtype();
                    if (tablename != null && tablename.next())
                    {
                        fileWriter.append("SCHTYPE,SCHDESC,DEBT,EQUITY,ENTRY_DATE");
                        fileWriter.append("\n");
                       while(tablename.next())
                        {
                            fileWriter.append(tablename.getString("SCHTYPE") == null ? null : '"' + tablename.getString("SCHTYPE") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("SCHDESC") == null ? null : '"' + tablename.getString("SCHDESC") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("DEBT") == null ? null : '"' + tablename.getString("DEBT") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("EQUITY") == null ? null : '"' + tablename.getString("EQUITY") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("ENTRY_DATE").equals("null") ? "N/A" : '"' + tablename.getString("ENTRY_DATE") + '"');
                            fileWriter.append("\n");
                        }
                    }
                }
                catch (Exception e)
                {
                    result = "failure";
                    Logger.DataLogger("Error in CsvFileWriter !!!" + e.getMessage());
                }
                finally
                {
                    try
                    {
                        fileWriter.flush();
                        fileWriter.close();
                    }
                    catch (IOException e)
                    {
                        result = "failure";
                        Logger.DataLogger("Error while flushing/closing fileWriter !!!" + e.getMessage());
                    }
                }
            }
            else
            {
                if (!sourceFile.exists())
                {
                    fileWriter = new FileWriter(exportfilename);
                    fileWriter.append("SCHTYPE,SCHDESC,DEBT,EQUITY,ENTRY_DATE");
                    fileWriter.append("\n");
                    fileWriter.append("No Records Found.");
                    fileWriter.flush();
                    fileWriter.close();
                }
            }
//        }
//        catch (Exception e)
//        {
//            result = "failure";
//            String s = e.toString();
//            for (int exi = 0; exi < e.getStackTrace().length; exi++)
//            {
//                s = s + exi + " ClassName  : " + e.getStackTrace()[exi].getClassName() + "\n";
//                s = s + exi + " MethodName : " + e.getStackTrace()[exi].getMethodName() + "\n";
//                s = s + exi + " LineNumber : " + e.getStackTrace()[exi].getLineNumber() + "\n";
//            }
//            Logger.DataLogger("in export service method name generateCsvFile_schtype " + s);
//        }
        return result;
    }

    protected String generateCsvFile_schsubtype(String exportfilename) throws Exception
    {
        String result = "success";
//        try
//        {
            File sourceFile = new File(exportfilename);
            FileWriter fileWriter = null;

            if (sourceFile.exists())
            {

                sourceFile.delete();
            }

            if (!sourceFile.exists())
            {
                try
                {
                    fileWriter = new FileWriter(sourceFile);
                    SqlRowSet tablename = manager.getTableData_Schsubtype();
                    if (tablename != null && tablename.next())
                    {
                        fileWriter.append("SCHTYPE,SCHSUBTYPE,SUBDESC,ENTRY_DATE,AUDIT_DIFF,SUBSHORTDESC,TAX_NATURE,ASSET_CLASS,EMP_CODE,INT_RATE_SENSITIVITY");
                        fileWriter.append("\n");
                         while(tablename.next())
                        {
                            fileWriter.append(tablename.getString("SCHTYPE") == null ? null : '"' + tablename.getString("SCHTYPE") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("SCHSUBTYPE") == null ? null : '"' + tablename.getString("SCHSUBTYPE") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("SUBDESC") == null ? null : '"' + tablename.getString("SUBDESC") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("ENTRY_DATE").equals("null") ? "N/A" : '"' + tablename.getString("ENTRY_DATE") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("AUDIT_DIFF") == null ? null : '"' + tablename.getString("AUDIT_DIFF") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("SUBSHORTDESC") == null ? null : '"' + tablename.getString("SUBSHORTDESC") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("TAX_NATURE") == null ? null : '"' + tablename.getString("TAX_NATURE") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("ASSET_CLASS") == null ? null : '"' + tablename.getString("ASSET_CLASS") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("EMP_CODE") == null ? null : '"' + tablename.getString("EMP_CODE") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("INT_RATE_SENSITIVITY") == null ? null : '"' + tablename.getString("INT_RATE_SENSITIVITY") + '"');
                            fileWriter.append("\n");
                        }
                    }
                }
                catch (Exception e)
                {
                    result = "failure";
                    Logger.DataLogger("Error in CsvFileWriter !!!" + e.getMessage());
                }
                finally
                {
                    try
                    {
                        fileWriter.flush();
                        fileWriter.close();
                    }
                    catch (IOException e)
                    {
                        result = "failure";
                        Logger.DataLogger("Error while flushing/closing fileWriter !!!" + e.getMessage());
                    }
                }
            }
            else
            {
                if (!sourceFile.exists())
                {
                    fileWriter = new FileWriter(exportfilename);
                    fileWriter.append("SCHTYPE,SCHSUBTYPE,SUBDESC,ENTRY_DATE,AUDIT_DIFF,SUBSHORTDESC,TAX_NATURE,ASSET_CLASS,EMP_CODE,INT_RATE_SENSITIVITY");
                    fileWriter.append("\n");
                    fileWriter.append("No Records Found.");
                    fileWriter.flush();
                    fileWriter.close();
                }
            }
//        }
//        catch (Exception e)
//        {
//            result = "failure";
//            String s = e.toString();
//            for (int exi = 0; exi < e.getStackTrace().length; exi++)
//            {
//                s = s + exi + " ClassName  : " + e.getStackTrace()[exi].getClassName() + "\n";
//                s = s + exi + " MethodName : " + e.getStackTrace()[exi].getMethodName() + "\n";
//                s = s + exi + " LineNumber : " + e.getStackTrace()[exi].getLineNumber() + "\n";
//            }
//            Logger.DataLogger("in export service method name generateCsvFile_schsubtype " + s);
//        }
        return result;
    }

    protected String generateCsvFile_opt(String exportfilename) throws Exception
    {
        String result = "success";
//        try
//        {
            File sourceFile = new File(exportfilename);
            FileWriter fileWriter = null;

            if (sourceFile.exists())
            {

                sourceFile.delete();
            }

            if (!sourceFile.exists())
            {
                try
                {
                    fileWriter = new FileWriter(sourceFile);
                    SqlRowSet tablename = manager.getTableData_Opt();
                    if (tablename != null && tablename.next())
                    {
                        fileWriter.append("OPT,ENTRY_DATE");
                        fileWriter.append("\n");
                        while(tablename.next())
                        {
                            fileWriter.append(tablename.getString("OPT") == null ? null :'"' + tablename.getString("OPT") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("ENTRY_DATE").equals("null") ? "N/A" : '"' + tablename.getString("ENTRY_DATE") + '"');
                            fileWriter.append("\n");
                        }
                    }
                }
                catch (Exception e)
                {
                    result = "failure";
                    Logger.DataLogger("Error in CsvFileWriter !!!" + e.getMessage());
                }
                finally
                {
                    try
                    {
                        fileWriter.flush();
                        fileWriter.close();
                    }
                    catch (IOException e)
                    {
                        result = "failure";
                        Logger.DataLogger("Error while flushing/closing fileWriter !!!" + e.getMessage());
                    }
                }
            }
            else
            {
                if (!sourceFile.exists())
                {
                    fileWriter = new FileWriter(exportfilename);
                    fileWriter.append("OPT,ENTRY_DATE");
                    fileWriter.append("\n");
                    fileWriter.append("No Records Found.");
                    fileWriter.flush();
                    fileWriter.close();
                }
            }
//        }
//        catch (Exception e)
//        {
//            result = "failure";
//            String s = e.toString();
//            for (int exi = 0; exi < e.getStackTrace().length; exi++)
//            {
//                s = s + exi + " ClassName  : " + e.getStackTrace()[exi].getClassName() + "\n";
//                s = s + exi + " MethodName : " + e.getStackTrace()[exi].getMethodName() + "\n";
//                s = s + exi + " LineNumber : " + e.getStackTrace()[exi].getLineNumber() + "\n";
//            }
//            Logger.DataLogger("in export service method name generateCsvFile_opt " + s);
//        }
        return result;
    }

    protected String generateCsvFile_regmst(String exportfilename) throws Exception
    {
        String result = "success";
//        try
//        {
            File sourceFile = new File(exportfilename);
            FileWriter fileWriter = null;

            if (sourceFile.exists())
            {

                sourceFile.delete();
            }

            if (!sourceFile.exists())
            {
                try
                {
                    fileWriter = new FileWriter(sourceFile);
                    SqlRowSet tablename = manager.getTableData_Regmst();
                    if (tablename != null && tablename.next())
                    {
                        fileWriter.append("REGCODE,REGNAME,ADD1,ADD2,ADD3,E_MAIL,PHONE,WEBSITE");
                        fileWriter.append("\n");
                       while(tablename.next())
                        {
                            fileWriter.append(tablename.getString("REGCODE") == null ? null : '"' + tablename.getString("REGCODE") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("REGNAME") == null ? null : '"' + tablename.getString("REGNAME") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("ADD1") == null ? null : '"' + tablename.getString("ADD1") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("ADD2") == null ? null : '"' + tablename.getString("ADD2") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("ADD3") == null ? null : '"' + tablename.getString("ADD3") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("E_MAIL") == null ? null : '"' + tablename.getString("E_MAIL") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("PHONE") == null ? null : '"' + tablename.getString("PHONE") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("WEBSITE") == null ? null : '"' + tablename.getString("WEBSITE") + '"');
                            fileWriter.append("\n");
                        }
                    }
                }
                catch (Exception e)
                {
                    result = "failure";
                    Logger.DataLogger("Error in CsvFileWriter !!!" + e.getMessage());
                }
                finally
                {
                    try
                    {
                        fileWriter.flush();
                        fileWriter.close();
                    }
                    catch (IOException e)
                    {
                        result = "failure";
                        Logger.DataLogger("Error while flushing/closing fileWriter !!!" + e.getMessage());
                    }
                }
            }
            else
            {
                if (!sourceFile.exists())
                {
                    fileWriter = new FileWriter(exportfilename);
                    fileWriter.append("REGCODE,REGNAME,ADD1,ADD2,ADD3,E_MAIL,PHONE,WEBSITE");
                    fileWriter.append("\n");
                    fileWriter.append("No Records Found.");
                    fileWriter.flush();
                    fileWriter.close();
                }
            }
//        }
//        catch (Exception e)
//        {
//            result = "failure";
//            String s = e.toString();
//            for (int exi = 0; exi < e.getStackTrace().length; exi++)
//            {
//                s = s + exi + " ClassName  : " + e.getStackTrace()[exi].getClassName() + "\n";
//                s = s + exi + " MethodName : " + e.getStackTrace()[exi].getMethodName() + "\n";
//                s = s + exi + " LineNumber : " + e.getStackTrace()[exi].getLineNumber() + "\n";
//            }
//            Logger.DataLogger("in export service method name generateCsvFile_regmst" + s);
//        }
        return result;
    }

    protected String generateCsvFile_nav(String exportfilename) throws Exception
    {
        String result = "success";
//        try
//        {
            File sourceFile = new File(exportfilename);
            FileWriter fileWriter = null;

            if (sourceFile.exists())
            {

                sourceFile.delete();
            }

            if (!sourceFile.exists())
            {
                try
                {
                    fileWriter = new FileWriter(sourceFile);

                    SqlRowSet tablename = manager.getTableData__nav();
                    if (tablename != null && tablename.next()) {
                        fileWriter.append("SCHCODE,NAVDATE,SALENAV,ENTRYDATE,ENTTYPE");
                        fileWriter.append("\n");

                        while (tablename.next()) {
                            fileWriter.append(tablename.getString("SCHCODE") == null ? null : '"' + tablename.getString("SCHCODE") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("NAVDATE").equals("null") ? "N/A" : '"' + tablename.getString("NAVDATE") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("SALENAV") == null ? null : '"' + tablename.getString("SALENAV") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("ENTRYDATE").equals("null") ? "N/A" : '"' + tablename.getString("ENTRYDATE") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("ENTTYPE") == null ? null : '"' + tablename.getString("ENTTYPE") + '"');
                            fileWriter.append("\n");
                        }
                    }
                }
                catch (Exception e)
                {
                    result = "failure";
                    Logger.DataLogger("Error in CsvFileWriter !!!" + e.getMessage());
                }
                finally
                {
                    try
                    {
                        fileWriter.flush();
                        fileWriter.close();
                    }
                    catch (IOException e)
                    {
                        result = "failure";
                        Logger.DataLogger("Error while flushing/closing fileWriter !!!" + e.getMessage());
                    }
                }
            }
            else
            {
                if (!sourceFile.exists())
                {
                    fileWriter = new FileWriter(exportfilename);
                    fileWriter.append("SCHCODE,NAVDATE,SALENAV,ENTRYDATE,ENTTYPE");
                    fileWriter.append("\n");
                    fileWriter.append("No Records Found.");
                    fileWriter.flush();
                    fileWriter.close();
                }
            }
//        }
//        catch (Exception e)
//        {
//            result = "failure";
//            String s = e.toString();
//            for (int exi = 0; exi < e.getStackTrace().length; exi++)
//            {
//                s = s + exi + " ClassName  : " + e.getStackTrace()[exi].getClassName() + "\n";
//                s = s + exi + " MethodName : " + e.getStackTrace()[exi].getMethodName() + "\n";
//                s = s + exi + " LineNumber : " + e.getStackTrace()[exi].getLineNumber() + "\n";
//            }
//            Logger.DataLogger("in export service method name generateCsvFile_nav" + s);
//        }
        return result;
    }

    @SuppressWarnings("null")
    protected String generateCsvFile_div(String exportfilename, String frmdate) throws Exception
    {
        String result = "success";
//        try
//        {
            File sourceFile = new File(exportfilename);
            FileWriter fileWriter = null;

            if (sourceFile.exists())
            {

                sourceFile.delete();
            }

            if (!sourceFile.exists())
            {
                try
                {
                    fileWriter = new FileWriter(sourceFile);
                    SqlRowSet tablename = manager.getTableData__div(frmdate);

                      if (tablename != null && tablename.next()) {
                        fileWriter.append("DIVDATE,DIVUNIT,SCHCODE,NAV,ENTRY_DATE,DAYDIFF");
                        fileWriter.append("\n");

                        while (tablename.next()) {

                            fileWriter.append(tablename.getString("DIVDATE").equals("null") ? "N/A" : '"' + tablename.getString("DIVDATE") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("DIVUNIT") == null ? null : '"' + tablename.getString("DIVUNIT") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("SCHCODE") == null ? null : '"' + tablename.getString("SCHCODE") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("NAV") == null ? null : '"' + tablename.getString("NAV") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("ENTRY_DATE").equals("null") ? "N/A" : '"' + tablename.getString("ENTRY_DATE") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("DAYDIFF") == null ? null : '"' + tablename.getString("DAYDIFF") + '"');
                            fileWriter.append("\n");
                        }
                    }
         
                }
                catch (Exception e)
                {
                    result = "failure";
                    Logger.DataLogger("Error in CsvFileWriter !!!" + e.getMessage());
                }
                finally
                {
                    try
                    {
                        fileWriter.flush();
                        fileWriter.close();
                    }
                    catch (IOException e)
                    {
                        result = "failure";
                        Logger.DataLogger("Error while flushing/closing fileWriter !!!" + e.getMessage());
                    }
                }
            }
            else
            {
                if (!sourceFile.exists())
                {
                    fileWriter = new FileWriter(exportfilename);
                    fileWriter.append("DIVDATE,DIVUNIT,SCHCODE,NAV,ENTRY_DATE,DAYDIFF");
                    fileWriter.append("\n");
                    fileWriter.append("No Records Found.");
                    fileWriter.flush();
                    fileWriter.close();
                }
            }
//        }
//        catch (Exception e)
//        {
//            result = "failure";
//            String s = e.toString();
//            for (int exi = 0; exi < e.getStackTrace().length; exi++)
//            {
//                s = s + exi + " ClassName  : " + e.getStackTrace()[exi].getClassName() + "\n";
//                s = s + exi + " MethodName : " + e.getStackTrace()[exi].getMethodName() + "\n";
//                s = s + exi + " LineNumber : " + e.getStackTrace()[exi].getLineNumber() + "\n";
//            }
//            Logger.DataLogger("in export service method name generateCsvFile_div" + s);
//        }
        return result;
    }
    
     @SuppressWarnings("null")
    protected String generateCsvFile_bonus(String exportfilename, String frmdate) throws Exception
    {
        String result = "success";
//        try
//        {
            File sourceFile = new File(exportfilename);
            FileWriter fileWriter = null;

            if (sourceFile.exists())
            {

                sourceFile.delete();
            }

            if (!sourceFile.exists())
            {
                try
                {
                    fileWriter = new FileWriter(sourceFile);
                    SqlRowSet tablename = manager.getTableDataBonus(frmdate);

                     if (tablename != null && tablename.next()) {
                        fileWriter.append("SCHCODE,BONUSDATE,BONUSUNIT,ENTRY_DATE");
                        fileWriter.append("\n");

                        while (tablename.next()) {
                            fileWriter.append(tablename.getString("SCHCODE") == null ? null : '"' + tablename.getString("SCHCODE") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("BONUSDATE").equals("null") ? "N/A" : '"' + tablename.getString("BONUSDATE") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("BONUSUNIT") == null ? null : '"' + tablename.getString("BONUSUNIT") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("ENTRY_DATE").equals("null") ? "N/A" : '"' + tablename.getString("ENTRY_DATE") + '"');
                            fileWriter.append("\n");
                        }
                    }
                }
                catch (Exception e)
                {
                    result = "failure";
                    Logger.DataLogger("Error in CsvFileWriter !!!" + e.getMessage());
                }
                finally
                {
                    try
                    {
                        fileWriter.flush();
                        fileWriter.close();
                    }
                    catch (IOException e)
                    {
                        result = "failure";
                        Logger.DataLogger("Error while flushing/closing fileWriter !!!" + e.getMessage());
                    }
                }
            }
            else
            {
                if (!sourceFile.exists())
                {
                    fileWriter = new FileWriter(exportfilename);
                    fileWriter.append("SCHCODE,BONUSDATE,BONUSUNIT,ENTRY_DATE");
                    fileWriter.append("\n");
                    fileWriter.append("No Records Found.");
                    fileWriter.flush();
                    fileWriter.close();
                }
            }
//        }
//        catch (Exception e)
//        {
//            result = "failure";
//            String s = e.toString();
//            for (int exi = 0; exi < e.getStackTrace().length; exi++)
//            {
//                s = s + exi + " ClassName  : " + e.getStackTrace()[exi].getClassName() + "\n";
//                s = s + exi + " MethodName : " + e.getStackTrace()[exi].getMethodName() + "\n";
//                s = s + exi + " LineNumber : " + e.getStackTrace()[exi].getLineNumber() + "\n";
//            }
//            Logger.DataLogger("in export service method name generateCsvFile_bonus" + s);
//        }
        return result;
    }

    @SuppressWarnings("null")
    protected String getTableData_TAC_SCHMST(String exportfilename) throws Exception
    {
        String result = "success";
//        try
//        {
            File sourceFile = new File(exportfilename);
            StringBuilder FILE_HEADER = new StringBuilder();
                    
            FILE_HEADER.append("SCHCODE,EX_CODE,SCHCODE_DIVR_GR,SCHCODE_DIVP,")
                    .append("ISIN_DIVR_GR,ISIN_DIVP,MECODE,ENT_DATE,")
                    .append("LIQUID_SCHCODE_DIVR_GR,LIQUID_SCHCODE_DIVP,TAT,OFFLINE_TRXN,AMT_WITHOUT_RECO,")
                    .append("PIP_DIVR_GR,PIP_DIVP,RED_DIVR_GR,RED_DIVP,")
                    .append("SIP_DIVR_GR,SIP_DIVP,STP_DIVR_GR,STP_DIVP,")
                    .append("NFO_DIVR_GR,NFO_DIVP,NFO_STARTDATE_ONLINE,NFO_ENDDATE_ONLINE,")
                    .append("NFO_STARTDATE_OFFLINE,NFO_ENDDATE_OFFLINE,NFO_STARTDATE_CNT,NFO_ENDDATE_CNT,")
                    .append("ALLOTMENTDATE,NFO_SIP_STARTDATE,DPC_DIVR_GR,DPC_DIVP,")
                    .append("NFO_SIP_DIVR_GR,NFO_SIP_DIVP,NFO_STARTDATE_ONLINE_PHY,NFO_ENDDATE_ONLINE_PHY,")
                    .append("NFO_STARTDATE_OFFLINE_PHY,NFO_ENDDATE_OFFLINE_PHY,NFO_STARTDATE_CNT_PHY,NFO_ENDDATE_CNT_PHY,")
                    .append("ALLOTMENTDATE_PHY,NFO_SIP_STARTDATE_PHY,INSURE_SCHCODE_DIVR_GR,INSURE_SCHCODE_DIVP,")
                    .append("L1_SCHCODE_DIVR_GR,L1_SCHCODE_DIVP,PIP_DIVR_GR_PHY,PIP_DIVP_PHY,")
                    .append("RED_DIVR_GR_PHY,RED_DIVP_PHY,SWITCH_DIVR_GR,SWITCH_DIVP,")
                    .append("SWITCH_DIVR_GR_PHY,SWITCH_DIVP_PHY,SIP_DIVR_GR_PHY,SIP_DIVP_PHY,")
                    .append("INSURE_SIP_DIVR_GR,INSURE_SIP_DIVP,INSURE_SIP_DIVR_GR_PHY,INSURE_SIP_DIVP_PHY,")
                    .append("STP_DIVR_GR_PHY,STP_DIVP_PHY,SWP_DIVR_GR,SWP_DIVP,")
                    .append("SWP_DIVR_GR_PHY,SWP_DIVP_PHY,NFO_DIVR_GR_PHY,NFO_DIVP_PHY,NFO_SIP_DIVR_GR_PHY,NFO_SIP_DIVP_PHY");
            
            FileWriter fileWriter = null;

            if (sourceFile.exists())
            {

                sourceFile.delete();
            }

            if (!sourceFile.exists())
            {
                try
                {
                    fileWriter = new FileWriter(sourceFile);
                    SqlRowSet tablename = manager.getTableData_TAC_SCHMST();
                    if (tablename != null && tablename.next()) {
                        fileWriter.append(FILE_HEADER.toString());
                        fileWriter.append("\n");

                        while (tablename.next()) {

                            fileWriter.append(tablename.getString("SCHCODE") == null ? null : '"' + tablename.getString("SCHCODE") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("EX_CODE") == null ? null : '"' + tablename.getString("EX_CODE") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("SCHCODE_DIVR_GR") == null ? null : '"' + tablename.getString("SCHCODE_DIVR_GR") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("SCHCODE_DIVP") == null ? null : '"' + tablename.getString("SCHCODE_DIVP") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("ISIN_DIVR_GR") == null ? null : '"' + tablename.getString("ISIN_DIVR_GR") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("ISIN_DIVP") == null ? null : '"' + tablename.getString("ISIN_DIVP") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("MECODE") == null ? null : '"' + tablename.getString("MECODE") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("ENT_DATE").equals("null") ? "N/A" : '"' + tablename.getString("ENT_DATE") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("LIQUID_SCHCODE_DIVR_GR") == null ? null : '"' + tablename.getString("LIQUID_SCHCODE_DIVR_GR") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("LIQUID_SCHCODE_DIVP") == null ? null : '"' + tablename.getString("LIQUID_SCHCODE_DIVP") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("TAT") == null ? null : '"' + tablename.getString("TAT") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("OFFLINE_TRXN") == null ? null : '"' + tablename.getString("OFFLINE_TRXN") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("AMT_WITHOUT_RECO") == null ? null : '"' + tablename.getString("AMT_WITHOUT_RECO") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("PIP_DIVR_GR") == null ? null : '"' + tablename.getString("PIP_DIVR_GR") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("PIP_DIVP") == null ? null : '"' + tablename.getString("PIP_DIVP") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("RED_DIVR_GR") == null ? null : '"' + tablename.getString("RED_DIVR_GR") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("RED_DIVP") == null ? null : '"' + tablename.getString("RED_DIVP") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("SIP_DIVR_GR") == null ? null : '"' + tablename.getString("SIP_DIVR_GR") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("SIP_DIVP") == null ? null : '"' + tablename.getString("SIP_DIVP") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("STP_DIVR_GR") == null ? null : '"' + tablename.getString("STP_DIVR_GR") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("STP_DIVP") == null ? null : '"' + tablename.getString("STP_DIVP") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("NFO_DIVR_GR") == null ? null : '"' + tablename.getString("NFO_DIVR_GR") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("NFO_DIVP") == null ? null : '"' + tablename.getString("NFO_DIVP") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("NFO_STARTDATE_ONLINE").equals("null") ? "N/A" : '"' + tablename.getString("NFO_STARTDATE_ONLINE") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("NFO_ENDDATE_ONLINE").equals("null") ? "N/A" : '"' + tablename.getString("NFO_ENDDATE_ONLINE") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("NFO_STARTDATE_OFFLINE").equals("null") ? "N/A" : '"' + tablename.getString("NFO_STARTDATE_OFFLINE") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("NFO_ENDDATE_OFFLINE").equals("null") ? "N/A" : '"' + tablename.getString("NFO_ENDDATE_OFFLINE") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("NFO_STARTDATE_CNT").equals("null") ? "N/A" : '"' + tablename.getString("NFO_STARTDATE_CNT") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("NFO_ENDDATE_CNT").equals("null") ? "N/A" : '"' + tablename.getString("NFO_ENDDATE_CNT") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("ALLOTMENTDATE").equals("null") ? "N/A" : '"' + tablename.getString("ALLOTMENTDATE") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("NFO_SIP_STARTDATE").equals("null") ? "N/A" : '"' + tablename.getString("NFO_SIP_STARTDATE") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("DPC_DIVR_GR") == null ? null : '"' + tablename.getString("DPC_DIVR_GR") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("DPC_DIVP") == null ? null : '"' + tablename.getString("DPC_DIVP") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("NFO_SIP_DIVR_GR") == null ? null : '"' + tablename.getString("NFO_SIP_DIVR_GR") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("NFO_SIP_DIVP") == null ? null : '"' + tablename.getString("NFO_SIP_DIVP") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("NFO_STARTDATE_ONLINE_PHY").equals("null") ? "N/A" : '"' + tablename.getString("NFO_STARTDATE_ONLINE_PHY") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("NFO_ENDDATE_ONLINE_PHY").equals("null") ? "N/A" : '"' + tablename.getString("NFO_ENDDATE_ONLINE_PHY") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("NFO_STARTDATE_OFFLINE_PHY").equals("null") ? "N/A" : '"' + tablename.getString("NFO_STARTDATE_OFFLINE_PHY") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("NFO_ENDDATE_OFFLINE_PHY").equals("null") ? "N/A" : '"' + tablename.getString("NFO_ENDDATE_OFFLINE_PHY") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("NFO_STARTDATE_CNT_PHY").equals("null") ? "N/A" : '"' + tablename.getString("NFO_STARTDATE_CNT_PHY") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("NFO_ENDDATE_CNT_PHY").equals("null") ? "N/A" : '"' + tablename.getString("NFO_ENDDATE_CNT_PHY") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("ALLOTMENTDATE_PHY").equals("null") ? "N/A" : '"' + tablename.getString("ALLOTMENTDATE_PHY") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("NFO_SIP_STARTDATE_PHY").equals("null") ? "N/A" : '"' + tablename.getString("NFO_SIP_STARTDATE_PHY") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("INSURE_SCHCODE_DIVR_GR") == null ? null : '"' + tablename.getString("INSURE_SCHCODE_DIVR_GR") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("INSURE_SCHCODE_DIVP") == null ? null : '"' + tablename.getString("INSURE_SCHCODE_DIVP") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("L1_SCHCODE_DIVR_GR") == null ? null : '"' + tablename.getString("L1_SCHCODE_DIVR_GR") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("L1_SCHCODE_DIVP") == null ? null : '"' + tablename.getString("L1_SCHCODE_DIVP") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("PIP_DIVR_GR_PHY") == null ? null : '"' + tablename.getString("PIP_DIVR_GR_PHY") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("PIP_DIVP_PHY") == null ? null : '"' + tablename.getString("PIP_DIVP_PHY") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("RED_DIVR_GR_PHY") == null ? null : '"' + tablename.getString("RED_DIVR_GR_PHY") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("RED_DIVP_PHY") == null ? null : '"' + tablename.getString("RED_DIVP_PHY") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("SWITCH_DIVR_GR") == null ? null : '"' + tablename.getString("SWITCH_DIVR_GR") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("SWITCH_DIVP") == null ? null : '"' + tablename.getString("SWITCH_DIVP") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("SWITCH_DIVR_GR_PHY") == null ? null : '"' + tablename.getString("SWITCH_DIVR_GR_PHY") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("SWITCH_DIVP_PHY") == null ? null : '"' + tablename.getString("SWITCH_DIVP_PHY") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("SIP_DIVR_GR_PHY") == null ? null : '"' + tablename.getString("SIP_DIVR_GR_PHY") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("SIP_DIVP_PHY") == null ? null : '"' + tablename.getString("SIP_DIVP_PHY") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("INSURE_SIP_DIVR_GR") == null ? null : '"' + tablename.getString("INSURE_SIP_DIVR_GR") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("INSURE_SIP_DIVP") == null ? null : '"' + tablename.getString("INSURE_SIP_DIVP") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("INSURE_SIP_DIVR_GR_PHY") == null ? null : '"' + tablename.getString("INSURE_SIP_DIVR_GR_PHY") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("INSURE_SIP_DIVP_PHY") == null ? null : '"' + tablename.getString("INSURE_SIP_DIVP_PHY") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("STP_DIVR_GR_PHY") == null ? null : '"' + tablename.getString("STP_DIVR_GR_PHY") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("STP_DIVP_PHY") == null ? null : '"' + tablename.getString("STP_DIVP_PHY") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("SWP_DIVR_GR") == null ? null : '"' + tablename.getString("SWP_DIVR_GR") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("SWP_DIVP") == null ? null : '"' + tablename.getString("SWP_DIVP") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("SWP_DIVR_GR_PHY") == null ? null : '"' + tablename.getString("SWP_DIVR_GR_PHY") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("SWP_DIVP_PHY") == null ? null : '"' + tablename.getString("SWP_DIVP_PHY") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("NFO_DIVR_GR_PHY") == null ? null : '"' + tablename.getString("NFO_DIVR_GR_PHY") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("NFO_DIVP_PHY") == null ? null : '"' + tablename.getString("NFO_DIVP_PHY") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("NFO_SIP_DIVR_GR_PHY") == null ? null : '"' + tablename.getString("NFO_SIP_DIVR_GR_PHY") + '"');
                            fileWriter.append(",");
                            fileWriter.append(tablename.getString("NFO_SIP_DIVP_PHY") == null ? null : '"' + tablename.getString("NFO_SIP_DIVP_PHY") + '"');
                            fileWriter.append("\n");
                        }
                    }

                }
                catch (Exception e)
                {
                    result = "failure";
                    Logger.DataLogger("Error in CsvFileWriter !!!" + e.getMessage());
                }
                finally
                {
                    try
                    {
                        fileWriter.flush();
                        fileWriter.close();
                    }
                    catch (IOException e)
                    {
                        result = "failure";
                        Logger.DataLogger("Error while flushing/closing fileWriter !!!" + e.getMessage());
                    }
                }
            }
            else
            {
                if (!sourceFile.exists())
                {
                    fileWriter = new FileWriter(exportfilename);
                    fileWriter.append(FILE_HEADER.toString());
                    fileWriter.append("\n");
                    fileWriter.append("No Records Found.");
                    fileWriter.flush();
                    fileWriter.close();
                }
            }
//        }
//        catch (Exception e)
//        {
//            result = "failure";
//            String s = e.toString();
//            for (int exi = 0; exi < e.getStackTrace().length; exi++)
//            {
//                s = s + exi + " ClassName  : " + e.getStackTrace()[exi].getClassName() + "\n";
//                s = s + exi + " MethodName : " + e.getStackTrace()[exi].getMethodName() + "\n";
//                s = s + exi + " LineNumber : " + e.getStackTrace()[exi].getLineNumber() + "\n";
//            }
//            Logger.DataLogger("in export service method name getTableData_TAC_SCHMST" + s);
//        }
        return result;
    }

    public void downloadFileCommon(String filepath, HttpServletResponse res) throws IOException
    {
        File file =new File(filepath);
        Logger.DataLogger("file size bytes "+file.length());
        Logger.DataLogger("filepath "+filepath);
        ContentDisposition condis = new ContentDisposition();
        condis.setFilePath(filepath);
        condis.setRequestType(ContentDispositionType.ATTACHMENT);
        condis.setResponse(res);
        condis.process();
      
        }

    protected static void addFileToZip(String OrignalName, String SourceFileName, ZipOutputStream zos) throws IOException {
        try {
            ZipEntry ze = new ZipEntry(OrignalName + ".csv");
            zos.putNextEntry(ze);
            FileInputStream in = new FileInputStream(SourceFileName);
            byte[] buffer = new byte[2048]; // Buffer size increased- Nitin
            int len;
            while ((len = in.read(buffer)) > 0) {
                zos.write(buffer, 0, len);
            }

            if (in != null) {
                in.close();
            }

        } finally {
            zos.closeEntry();
            zos.close();
        }
    }

    public static void deletefile(String filename) throws Exception
    {
//        try
//        {

            File file = new File(filename);
            if (filename != null && !filename.equalsIgnoreCase(""))
            {
                file.delete();
            }

//        }
//        catch (Exception e)
//        {
//            String s = e.toString();
//            for (int exi = 0; exi < e.getStackTrace().length; exi++)
//            {
//                s = s + exi + " ClassName  : " + e.getStackTrace()[exi].getClassName() + "\n";
//                s = s + exi + " MethodName : " + e.getStackTrace()[exi].getMethodName() + "\n";
//                s = s + exi + " LineNumber : " + e.getStackTrace()[exi].getLineNumber() + "\n";
//            }
//            Logger.DataLogger("deletefile method in export service" + s);
//        }

    }

    /**
     * 
     * @param exportfilename
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws Exception 
     */
    protected String generateCsvFile_OLC_SCHMST(String exportfilename) 
            throws ClassNotFoundException,SQLException,Exception  
    {
            String result = "success";
            File sourceFile = new File(exportfilename);
            FileWriter fileWriter = null;
            
            StringBuilder FILE_HEADER = new StringBuilder();
                    
            FILE_HEADER.append("OLTSCHCODE, SCHCODE, UNITFACEVAL, DIVOPTFLAG, INITPURAMT")
                    .append(", MININITMULTAMT, MINPURAMT, PURMULTAMT, ENTRYLOAD, EXITLOAD, MINREDAMT, REDMULTAMT")
                    .append(", MINBALAMT, MINREDUNIT, REDMULTUNIT, MINBALUNIT, CUTOFFPUR")
                    .append(", CUTOFFRED, PURFLAG, REDFLAG, ISSIPAVAIL, ISSWPAVAIL")
                    .append(", ISSWIAVAIL, ISTRANSFERAVAIL, TRANSFERSTARTDATE")
                    .append(", CUTOFFPURAMC, CUTOFFREDAMC, ISSTPAVAIL, SCH_MODE ")
                    .append(", OLTSCHCODE_DIVP, BUSSDAY, MECODE, ENTRY_DATE")
                    .append(", SWISTARTDATE");
            
            if (sourceFile.exists())
            {
               sourceFile.delete();
            }

            if (!sourceFile.exists())
            {
                try
                {
                    fileWriter = new FileWriter(sourceFile);
                    SqlRowSet tablename = manager.getTableData_OLT_SCHMST();
                    
                    if (tablename!=null && tablename.next())
                    {
                        fileWriter.append(FILE_HEADER.toString());
                        fileWriter.append("\n");
                       
                        while(tablename.next())
                        {
                            
                                fileWriter.append(tablename.getString("OLTSCHCODE") == null ? null : '"' + tablename.getString("OLTSCHCODE").trim() + '"');
                                fileWriter.append(",");
                                fileWriter.append(tablename.getString("SCHCODE") == null ? null : '"' + tablename.getString("SCHCODE").trim() + '"');
                                fileWriter.append(",");
                                fileWriter.append(tablename.getString("UNITFACEVAL") == null ? null : '"' + tablename.getString("UNITFACEVAL").trim() + '"');
                                fileWriter.append(",");
                                fileWriter.append(tablename.getString("DIVOPTFLAG") == null ? null : '"' + tablename.getString("DIVOPTFLAG").trim() + '"');
                                fileWriter.append(",");
                                fileWriter.append(tablename.getString("INITPURAMT") == null ? null : '"' + tablename.getString("INITPURAMT").trim() + '"');
                                fileWriter.append(",");
                                fileWriter.append(tablename.getString("MININITMULTAMT") == null ? null : '"' + tablename.getString("MININITMULTAMT").trim() + '"');
                                fileWriter.append(",");
                                fileWriter.append(tablename.getString("MINPURAMT") == null ? null : '"' + tablename.getString("MINPURAMT").trim() + '"');
                                fileWriter.append(",");
                                fileWriter.append(tablename.getString("PURMULTAMT") == null ? null : '"' + tablename.getString("PURMULTAMT").trim() + '"');
                                fileWriter.append(",");
                                fileWriter.append(tablename.getString("ENTRYLOAD") == null ? null : '"' + tablename.getString("ENTRYLOAD").trim() + '"');
                                fileWriter.append(",");
                                fileWriter.append(tablename.getString("EXITLOAD") == null ? null : '"' + tablename.getString("EXITLOAD").trim() + '"');
                                fileWriter.append(",");
                                fileWriter.append(tablename.getString("MINREDAMT") == null ? null : '"' + tablename.getString("MINREDAMT").trim() + '"');
                                fileWriter.append(",");
                                fileWriter.append(tablename.getString("REDMULTAMT") == null ? null : '"' + tablename.getString("REDMULTAMT").trim() + '"');
                                fileWriter.append(",");
                                fileWriter.append(tablename.getString("MINBALAMT") == null ? null : '"' + tablename.getString("MINBALAMT").trim() + '"');
                                fileWriter.append(",");
                                fileWriter.append(tablename.getString("MINREDUNIT") == null ? null : '"' + tablename.getString("MINREDUNIT").trim() + '"');
                                fileWriter.append(",");
                                fileWriter.append(tablename.getString("REDMULTUNIT") == null ? null : '"' + tablename.getString("REDMULTUNIT").trim() + '"');
                                fileWriter.append(",");
                                fileWriter.append(tablename.getString("MINBALUNIT") == null ? null : '"' + tablename.getString("MINBALUNIT").trim() + '"');
                                fileWriter.append(",");
                                fileWriter.append(tablename.getString("CUTOFFPUR") == null ? null : '"' + tablename.getString("CUTOFFPUR").trim() + '"');
                                fileWriter.append(",");
                                fileWriter.append(tablename.getString("CUTOFFRED") == null ? null : '"' + tablename.getString("CUTOFFRED").trim() + '"');
                                fileWriter.append(",");
                                fileWriter.append(tablename.getString("PURFLAG") == null ? null : '"' + tablename.getString("PURFLAG").trim() + '"');
                                fileWriter.append(",");
                                fileWriter.append(tablename.getString("REDFLAG") == null ? null : '"' + tablename.getString("REDFLAG").trim() + '"');
                                fileWriter.append(",");
                                fileWriter.append(tablename.getString("ISSIPAVAIL") == null ? null : '"' + tablename.getString("ISSIPAVAIL").trim() + '"');
                                fileWriter.append(",");
                                fileWriter.append(tablename.getString("ISSWPAVAIL") == null ? null : '"' + tablename.getString("ISSWPAVAIL").trim() + '"');
                                fileWriter.append(",");
                                fileWriter.append(tablename.getString("ISSWIAVAIL") == null ? null : '"' + tablename.getString("ISSWIAVAIL").trim() + '"');
                                fileWriter.append(",");
                                fileWriter.append(tablename.getString("ISTRANSFERAVAIL") == null ? null : '"' + tablename.getString("ISTRANSFERAVAIL").trim() + '"');
                                fileWriter.append(",");
                                fileWriter.append(tablename.getString("TRANSFERSTARTDATE") == null ? null : '"' + tablename.getString("TRANSFERSTARTDATE").trim() + '"');
                                fileWriter.append(",");
                                fileWriter.append(tablename.getString("CUTOFFPURAMC") == null ? null : '"' + tablename.getString("CUTOFFPURAMC").trim() + '"');
                                fileWriter.append(",");
                                fileWriter.append(tablename.getString("CUTOFFREDAMC") == null ? null : '"' + tablename.getString("CUTOFFREDAMC").trim() + '"');
                                fileWriter.append(",");
                                fileWriter.append(tablename.getString("ISSTPAVAIL") == null ? null : '"' + tablename.getString("ISSTPAVAIL").trim() + '"');
                                fileWriter.append(",");
                                fileWriter.append(tablename.getString("SCH_MODE") == null ? null : '"' + tablename.getString("SCH_MODE").trim() + '"');
                                fileWriter.append(",");
                                fileWriter.append(tablename.getString("OLTSCHCODE_DIVP") == null ? null : '"' + tablename.getString("OLTSCHCODE_DIVP").trim() + '"');
                                fileWriter.append(",");
                                fileWriter.append(tablename.getString("BUSSDAY") == null ? null : '"' + tablename.getString("BUSSDAY").trim() + '"');
                                fileWriter.append(",");
                                fileWriter.append(tablename.getString("MECODE") == null ? null : '"' + tablename.getString("MECODE").trim() + '"');
                                fileWriter.append(",");
                                fileWriter.append(tablename.getString("ENTRY_DATE") == null ? null : '"' + tablename.getString("ENTRY_DATE").trim() + '"');
                                fileWriter.append(",");
                                fileWriter.append(tablename.getString("SWISTARTDATE") == null ? null : '"' + tablename.getString("SWISTARTDATE").trim() + '"');
                                fileWriter.append("\n");
                        }
                    }

                }
                catch (Exception e)
                {
                    result = "failure";
                    Logger.DataLogger("Error in CsvFileWriter !!!" + e.getMessage());
                }
                finally
                {
                    try
                    {
                        fileWriter.flush();
                        fileWriter.close();
                    }
                    catch (IOException e)
                    {
                        result = "failure";
                        Logger.DataLogger("Error while flushing/closing fileWriter !!!" + e.getMessage());
                    }
                }
            }
            else
            {
                if (!sourceFile.exists())
                {
                    fileWriter = new FileWriter(exportfilename);
                    fileWriter.append(FILE_HEADER.toString());
                    fileWriter.append("\n");
                    fileWriter.append("No Records Found.");
                    fileWriter.flush();
                    fileWriter.close();
                }
            }
        return result;
    }
}
