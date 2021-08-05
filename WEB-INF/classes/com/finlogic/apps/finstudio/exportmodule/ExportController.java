/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.apps.finstudio.exportmodule;

import com.finlogic.util.Logger;
import com.finlogic.util.properties.HardCodeProperty;
import java.io.FileOutputStream;
import java.util.zip.ZipOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 *
 * @author njuser
 */
public class ExportController extends MultiActionController
{

    @SuppressWarnings("static-access")
    public synchronized void export(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        try
        {
            response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
            response.setHeader("Cache-Control", "no-store"); //Directs caches not to store the page under any circumstance
            response.setHeader("Pragma", "no-cache"); //HTTP 1.0
            response.setDateHeader("Expires", 0); //prevents caching at the proxy server
            
            String ipaddr = finpack.FinPack.getIpAddr(request);
            Logger.DataLogger("incoming ip " + ipaddr);
            
            boolean flag = false;
            
            final ExportService service = new ExportService();
            HardCodeProperty hp = new HardCodeProperty();
            final String uploadpath = hp.getProperty("uploadpath");
            
            
            if (ipaddr != null)
            {
                Logger.DataLogger("in if cond");
                
                String[] res = hp.getProperty("mfbrs_croniplist").split(",");
                
                for (String re : res)
                {
                    Logger.DataLogger("in if cond ip address from property is " + re);
                    if (re.trim().equals(ipaddr))
                    {
                        Logger.DataLogger("ip match");
                        flag = true;
                        break;
                    }
                }
            }
            FileOutputStream fos = null;
            ZipOutputStream zos = null;

            try
            {
            
            if (flag == true)
            {
                Logger.DataLogger("if flag true then flag " + flag);
                
                if (request.getParameter("flag").equalsIgnoreCase("8"))
                {
                    Logger.DataLogger("if flag equals 8 ");
                    
                    fos = new FileOutputStream(uploadpath + "REGISTAR.zip");
                    zos = new ZipOutputStream(fos);
                    
                    if (service.generateCsvFile_regmst(uploadpath + "REGISTAR.csv").equalsIgnoreCase("success"))
                    {
                        service.addFileToZip("REGISTAR", uploadpath + "REGISTAR.csv", zos);
                        service.deletefile(uploadpath + "REGISTAR.csv");
                        service.downloadFileCommon(uploadpath + "REGISTAR.zip", response);
                    }
                    
                }

                else if (request.getParameter("flag").equalsIgnoreCase("1"))
                {
                    Logger.DataLogger("if flag equals 1 ");
                    
                    fos = new FileOutputStream(uploadpath + "COMPANY_MASTER.zip");
                    zos = new ZipOutputStream(fos);
                    
                    if (service.generateCsvFile_company(uploadpath + "COMPANY_MASTER.csv").equalsIgnoreCase("success"))
                    {
                        service.addFileToZip("COMPANY_MASTER", uploadpath + "COMPANY_MASTER.csv", zos);
                        service.deletefile(uploadpath + "COMPANY_MASTER.csv");
                        service.downloadFileCommon(uploadpath + "COMPANY_MASTER.zip", response);
                    }
                    
                }

                else if (request.getParameter("flag").equalsIgnoreCase("2"))
                {
                    Logger.DataLogger("if flag equals 2 ");
                    
                    fos = new FileOutputStream(uploadpath + "RNT_MASTER.zip");
                    zos = new ZipOutputStream(fos);
                    
                    if (service.generateCsvFile_companyrnt(uploadpath + "RNT_MASTER.csv").equalsIgnoreCase("success"))
                    {
                        service.addFileToZip("RNT_MASTER", uploadpath + "RNT_MASTER.csv", zos);
                        service.deletefile(uploadpath + "RNT_MASTER.csv");
                        service.downloadFileCommon(uploadpath + "RNT_MASTER.zip", response);
                    }
                    
                }

               else if (request.getParameter("flag").equalsIgnoreCase("7"))
                {
                    Logger.DataLogger("if flag equals 7 ");
                    
                    fos = new FileOutputStream(uploadpath + "SCHEME_OPTION.zip");
                    zos = new ZipOutputStream(fos);
                    
                    if (service.generateCsvFile_opt(uploadpath + "SCHEME_OPTION.csv").equalsIgnoreCase("success"))
                    {
                        service.addFileToZip("SCHEME_OPTION", uploadpath + "SCHEME_OPTION.csv", zos);
                        service.deletefile(uploadpath + "SCHEME_OPTION.csv");
                        service.downloadFileCommon(uploadpath + "SCHEME_OPTION.zip", response);
                    }
                    
                }

               else if (request.getParameter("flag").equalsIgnoreCase("5"))
                {
                    Logger.DataLogger("if flag equals 5 ");
                    
                    fos = new FileOutputStream(uploadpath + "INVESTMENT_CLASS.zip");
                    zos = new ZipOutputStream(fos);
                    
                    if (service.generateCsvFile_schtype(uploadpath + "INVESTMENT_CLASS.csv").equalsIgnoreCase("success"))
                    {
                        service.addFileToZip("INVESTMENT_CLASS", uploadpath + "INVESTMENT_CLASS.csv", zos);
                        service.deletefile(uploadpath + "INVESTMENT_CLASS.csv");
                        service.downloadFileCommon(uploadpath + "INVESTMENT_CLASS.zip", response);
                    }
                    
                }

               else if (request.getParameter("flag").equalsIgnoreCase("6"))
                {
                    Logger.DataLogger("if flag equals 6 ");
                    
                    fos = new FileOutputStream(uploadpath + "INVESTMENT_SUB_CLASS.zip");
                    zos = new ZipOutputStream(fos);
                    
                    if (service.generateCsvFile_schsubtype(uploadpath + "INVESTMENT_SUB_CLASS.csv").equalsIgnoreCase("success"))
                    {
                        service.addFileToZip("INVESTMENT_SUB_CLASS", uploadpath + "INVESTMENT_SUB_CLASS.csv", zos);
                        service.deletefile(uploadpath + "INVESTMENT_SUB_CLASS.csv");
                        service.downloadFileCommon(uploadpath + "INVESTMENT_SUB_CLASS.zip", response);
                    }
                    
                }

               else if (request.getParameter("flag").equalsIgnoreCase("3"))
                {
                    Logger.DataLogger("if flag equals 3 ");
                    
                    fos = new FileOutputStream(uploadpath + "PORTFOLIO_SCHEME_MASTER.zip");
                    zos = new ZipOutputStream(fos);
                    
                    if (service.generateCsvFile_portch(uploadpath + "PORTFOLIO_SCHEME_MASTER.csv").equalsIgnoreCase("success"))
                    {
                        service.addFileToZip("PORTFOLIO_SCHEME_MASTER", uploadpath + "PORTFOLIO_SCHEME_MASTER.csv", zos);
                        service.deletefile(uploadpath + "PORTFOLIO_SCHEME_MASTER.csv");
                        service.downloadFileCommon(uploadpath + "PORTFOLIO_SCHEME_MASTER.zip", response);
                    }
                    
                }

               else if (request.getParameter("flag").equalsIgnoreCase("4"))
                {
                    Logger.DataLogger("if flag equals 4 ");
                    
                    fos = new FileOutputStream(uploadpath + "SCHEME_MASTER.zip");
                    zos = new ZipOutputStream(fos);
                    
                    if (service.generateCsvFile_navch(uploadpath + "SCHEME_MASTER.csv").equalsIgnoreCase("success"))
                    {
                        service.addFileToZip("SCHEME_MASTER", uploadpath + "SCHEME_MASTER.csv", zos);
                        service.deletefile(uploadpath + "SCHEME_MASTER.csv");
                        service.downloadFileCommon(uploadpath + "SCHEME_MASTER.zip", response);
                    }
                    
                }

               else if (request.getParameter("flag").equalsIgnoreCase("9"))
                {
                    Logger.DataLogger("if flag equals 9 ");
                    
                    fos = new FileOutputStream(uploadpath + "NAV.zip");
                    zos = new ZipOutputStream(fos);
                    
                    if (service.generateCsvFile_nav(uploadpath + "NAV.csv").equalsIgnoreCase("success"))
                    {
                        service.addFileToZip("NAV", uploadpath + "NAV.csv", zos);
                        service.deletefile(uploadpath + "NAV.csv");
                        service.downloadFileCommon(uploadpath + "NAV.zip", response);
                    }
                    
                }
               else if (request.getParameter("flag").equalsIgnoreCase("DIVIDEND"))
                {
                    Logger.DataLogger("if flag equals DIVIDEND ");
                    Logger.DataLogger("if flag equals DIVIDEND then passdate" + request.getParameter("passdate"));
                    
                    
                    fos = new FileOutputStream(uploadpath + "DIVIDEND.zip");
                    zos = new ZipOutputStream(fos);
                    
                    if (service.generateCsvFile_div(uploadpath + "DIVIDEND.csv", 
                            request.getParameter("passdate")).equalsIgnoreCase("success"))
                    {
                        service.addFileToZip("DIVIDEND", uploadpath + "DIVIDEND.csv", zos);
                        service.deletefile(uploadpath + "DIVIDEND.csv");
                        service.downloadFileCommon(uploadpath + "DIVIDEND.zip", response);
                    }
                    
                }
               else if (request.getParameter("flag").equalsIgnoreCase("bonus"))
                {
                    Logger.DataLogger("if flag equals BONUS ");
                    Logger.DataLogger("if flag equals BONUS then passdate" + request.getParameter("passdate"));
                    
                    fos = new FileOutputStream(uploadpath + "BONUS.zip");
                    zos = new ZipOutputStream(fos);
                    
                    if (service.generateCsvFile_bonus(uploadpath + "BONUS.csv", 
                            request.getParameter("passdate")).equalsIgnoreCase("success"))
                    {
                        service.addFileToZip("BONUS", uploadpath + "BONUS.csv", zos);
                        service.deletefile(uploadpath + "BONUS.csv");
                        service.downloadFileCommon(uploadpath + "BONUS.zip", response);
                    }
                    
                }
               else if (request.getParameter("flag").equalsIgnoreCase("10"))
                {
                    Logger.DataLogger("if flag equals 10 ");
                    
                    fos = new FileOutputStream(uploadpath + "SCHMST.zip");
                    zos = new ZipOutputStream(fos);
                    
                    if (service.getTableData_TAC_SCHMST(uploadpath + "SCHMST.csv").equalsIgnoreCase("success"))
                    {
                        service.addFileToZip("SCHMST", uploadpath + "SCHMST.csv", zos);
                        service.deletefile(uploadpath + "SCHMST.csv");
                        service.downloadFileCommon(uploadpath + "SCHMST.zip", response);
                    }
                    
                }
                //13
                // [Start] By Hiren Bhuva @12-12-2018
                // Changes  :- Added One new table In data dump as per requested by Pradeep bhai
                else if (request.getParameter("flag").equalsIgnoreCase("13"))
                {
                    Logger.DataLogger("if flag equals 13 ");
                    
                    fos = new FileOutputStream(uploadpath + "OLT_SCHMST.zip");
                    zos = new ZipOutputStream(fos);
                    
                    if (service.generateCsvFile_OLC_SCHMST(uploadpath + "OLT_SCHMST.csv").equalsIgnoreCase("success"))
                    {
                        service.addFileToZip("OLT_SCHMST", uploadpath + "OLT_SCHMST.csv", zos);
                        service.deletefile(uploadpath + "OLT_SCHMST.csv");
                        service.downloadFileCommon(uploadpath + "OLT_SCHMST.zip", response);
                    }
                    
                }
                //[End] By Hiren Bhuva
            }
            else
            {
                Logger.DataLogger("in export method if flag false" + flag);
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().write("<html><head><title>Access Denied</title></head>"
                        + "<body><center><br><h3 style='text-align:center;'>Access Denied</h3><br>"
                        + hp.getProperty("mfbrs_croniperrormsg") + "</center></body></html>");
            }
            }catch(Exception e)
            {
                String s = e.toString();
                
                for (int exi = 0; exi < e.getStackTrace().length; exi++)
                {
                    s = s + exi + " ClassName  : " + e.getStackTrace()[exi].getClassName() + "\n";
                    s = s + exi + " MethodName : " + e.getStackTrace()[exi].getMethodName() + "\n";
                    s = s + exi + " LineNumber : " + e.getStackTrace()[exi].getLineNumber() + "\n";
                }
                Logger.DataLogger(s);
            }
           finally 
            {
                if (fos != null) {
                    fos.close();
                }
                if (zos != null) {
                    zos.close();
                }

            }
           
        }catch (Exception e)
        {
            String s = e.toString();
            for (int exi = 0; exi < e.getStackTrace().length; exi++)
            {
                s = s + exi + " ClassName  : " + e.getStackTrace()[exi].getClassName() + "\n";
                s = s + exi + " MethodName : " + e.getStackTrace()[exi].getMethodName() + "\n";
                s = s + exi + " LineNumber : " + e.getStackTrace()[exi].getLineNumber() + "\n";
            }
            Logger.DataLogger(s);
        }
    }
}
