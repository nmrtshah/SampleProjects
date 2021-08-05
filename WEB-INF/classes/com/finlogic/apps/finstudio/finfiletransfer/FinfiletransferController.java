package com.finlogic.apps.finstudio.finfiletransfer;

import com.finlogic.business.finstudio.finfiletransfer.FileTransIntermediateDataManager;
import com.finlogic.util.Logger;
import com.finlogic.util.finfiletransfer.CheckStatus;
import com.finlogic.util.finfiletransfer.FileTransfer;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class FinfiletransferController extends MultiActionController
{

    private final FinfiletransferService service = new FinfiletransferService();
    private String aclEmpCode = "ACLEmpCode";

    public ModelAndView getMenu(final HttpServletRequest request, final HttpServletResponse response)
    {
        ModelAndView mav;
        mav = new ModelAndView();
        String finlibPath;
        finlibPath = finpack.FinPack.getProperty("finlib_path");
        mav.addObject("finlib_path", finlibPath);
        Calendar c = Calendar.getInstance();
        String mydate = getMonth(c.get(Calendar.MONTH)) + " "
                + c.get(Calendar.DAY_OF_MONTH) + ","
                + c.get(Calendar.YEAR) + " "
                + c.get(Calendar.HOUR_OF_DAY) + ":"
                + c.get(Calendar.MINUTE) + ":"
                + c.get(Calendar.SECOND);
        mav.addObject("mydate", mydate);
        mav.setViewName("finfiletransfer/Main");
        return mav;
    }

    public String getMonth(int i)
    {
        String[] montharray = new String[]
        {
            "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"
        };
        return montharray[i];
    }

    public ModelAndView getMasterGrid(final HttpServletRequest req, final HttpServletResponse res, final FinfiletransferFormBean formBean1)
    {

        ModelAndView mav;
        mav = new ModelAndView();
        try
        {
            mav.setViewName("finfiletransfer/Output");
            Logger.DataLogger("here.." + req.getParameter("flag"));
            String flag = req.getParameter("flag");
            String empCode = req.getSession().getAttribute(aclEmpCode).toString();
            
            if ("1".equalsIgnoreCase(flag))
            {
                mav.addObject("jsonmaster", service.getMasterDataLoad(empCode, formBean1));
            }
            else if ("2".equalsIgnoreCase(flag))
            {
                mav.addObject("jsonmaster", service.getDataMaster(formBean1));
            }
        }
        catch (Exception e)
        {
            mav.addObject("error", e.getMessage());
            mav.setViewName("finfiletransfer/Error");
            finutils.errorhandler.ErrorHandler.PrintInFile(e, req);
        }
        return mav;
    }

    public ModelAndView getReportGrid(final HttpServletRequest req, final HttpServletResponse res, final FinfiletransferFormBean formBean)
    {

        ModelAndView mav;
        mav = new ModelAndView();
        try
        {
            mav.setViewName("finfiletransfer/Output");
            mav.addObject("jsonmaster", service.getReportDataLoad(formBean));

        }
        catch (Exception e)
        {
            mav.addObject("error", e.getMessage());
            mav.setViewName("finfiletransfer/Error");
            finutils.errorhandler.ErrorHandler.PrintInFile(e, req);
        }
        return mav;
    }

    public ModelAndView getDetail(final HttpServletRequest req, final HttpServletResponse res)
    {
        ModelAndView mav;
        mav = new ModelAndView();
        try
        {
            String temp = req.getParameter("masterPK");

            List detail = service.getDetail(temp);
            Logger.DataLogger("Execute... : " + detail.size());
            mav.addObject("detail", detail);
            mav.addObject("action", "execute");
            mav.setViewName("finfiletransfer/Grid");
        }
        catch (Exception e)
        {
            mav.addObject("error", e.getMessage());
            mav.setViewName("finfiletransfer/Error");
            finutils.errorhandler.ErrorHandler.PrintInFile(e, req);
        }
        return mav;
    }

    public ModelAndView addLoader(final HttpServletRequest req, final HttpServletResponse res)
    {
        ModelAndView mav;
        mav = new ModelAndView();
        try
        {
            mav.addObject("masterTask", "add");
            mav.addObject("action", "Main");
            mav.addObject("projects", service.getViewProject(1));
            mav.addObject("requests", service.getRequests("all", null));
            mav.setViewName("finfiletransfer/Add");
        }
        catch (Exception e)
        {
            mav.addObject("error", e.getMessage());
            mav.setViewName("finfiletransfer/Error");
            finutils.errorhandler.ErrorHandler.PrintInFile(e, req);
        }
        return mav;
    }

    public ModelAndView viewLoader(final HttpServletRequest req, final HttpServletResponse res)
    {
        ModelAndView mav;
        mav = new ModelAndView();
        try
        {
            mav.addObject("empcodes", service.getViewEmpcode());
            mav.addObject("projects", service.getViewProject(2));
            mav.addObject("requests", service.getViewRequest());
            DateFormat dateFormat;
            dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date date;
            date = new Date();
            String dateString;
            dateString = dateFormat.format(date);
            mav.addObject("none", dateString);
            mav.setViewName("finfiletransfer/View");
        }
        catch (Exception e)
        {
            mav.addObject("error", e.getMessage());
            mav.setViewName("finfiletransfer/Error");
            finutils.errorhandler.ErrorHandler.PrintInFile(e, req);
        }
        return mav;
    }

    public ModelAndView ReportLoader(final HttpServletRequest req, final HttpServletResponse res)
    {

        ModelAndView mav;
        mav = new ModelAndView();
        try
        {
            mav.addObject("masterTask", "add");
            mav.addObject("action", "Main");
            mav.addObject("projects", service.getViewProject(1));
            mav.setViewName("finfiletransfer/Report");
        }
        catch (Exception e)
        {
            mav.addObject("error", e.getMessage());
            mav.setViewName("finfiletransfer/Error");
            finutils.errorhandler.ErrorHandler.PrintInFile(e, req);
        }
        return mav;
    }

    public ModelAndView checkStatus(final HttpServletRequest req, final HttpServletResponse res, final FinfiletransferFormBean formBean)
    {
        ModelAndView mav;
        mav = new ModelAndView();

        String[] filelistupld = new String[3];
        filelistupld[0] = formBean.getFilelistupld1();
        filelistupld[1] = formBean.getFilelistupld2();
        filelistupld[2] = formBean.getFilelistupld3();

        String[] fileLoc = new String[3];
        fileLoc[0] = formBean.getFileLoc1();
        fileLoc[1] = formBean.getFileLoc2();
        fileLoc[2] = formBean.getFileLoc3();

        try
        {
            mav.addObject("action", "Report");
            String server[] = new String[2];
            server = formBean.getSrcdest().split("-");

            CheckStatus status = new CheckStatus();

            String[] fileArray = formBean.getFilelist().split("\n");
            Logger.DataLogger("Array 1: " + fileArray.length);
            Set<String> fileSet = new TreeSet<String>();
            for (int i = 0; i < fileArray.length; i++)
            {
                if ((fileArray[i].trim() != null) && (!"".equals(fileArray[i].trim())))
                {
                    fileSet.add(fileArray[i].trim());
                }
            }
            long id = System.currentTimeMillis();
            FileTransIntermediateDataManager dataManager = new FileTransIntermediateDataManager();

            for (Iterator<String> it = fileSet.iterator(); it.hasNext();)
            {
                String fileNm = it.next().toString();
                dataManager.insertData(id, fileNm, null, null, null);
            }
            String[] fileArrayDlt = formBean.getFilelistdlt().split("\n");
            Logger.DataLogger("Array 2: " + fileArrayDlt.length);
            for (int i = 0; i < fileArrayDlt.length; i++)
            {
                if ((fileArrayDlt[i].trim() != null) && (!"".equals(fileArrayDlt[i].trim())))
                {
                    String fileNm = "Deleted:" + fileArrayDlt[i];
                    dataManager.insertData(id, fileNm, "NA", null, null);
                }
            }

            File srcFile, destFile, folderCrt;
            for (int i = 0; i < 3; i++)
            {
                if (!(filelistupld[i] == null || "".equals(filelistupld[i])) && !(fileLoc[i] == null || "".equals(fileLoc[i])))
                {
                    String upldFileNm = "Uploaded:";
                    upldFileNm = upldFileNm + fileLoc[i];
                    dataManager.insertData(id, upldFileNm, "NA", "NA", "NA");
                    if (formBean.getLang().equalsIgnoreCase("java"))
                    {
                        fileLoc[i] = dataManager.getTomcatByProject(fileLoc[i].substring(0, fileLoc[i].indexOf("/"))) + "webapps/" + fileLoc[i];
                    }
                    else if (formBean.getLang().equalsIgnoreCase("php"))
                    {
                        fileLoc[i] = "/var/www/html/" + fileLoc[i];
                    }

                    fileLoc[i] = fileLoc[i].replaceAll("/", "_-_");
                    folderCrt = new File(finpack.FinPack.getProperty("filebox_path") + id);
                    folderCrt.mkdir();
                    destFile = new File(folderCrt.getPath() + "/" + fileLoc[i]);
                    srcFile = new File(finpack.FinPack.getProperty("filebox_path") + filelistupld[i]);
                    srcFile.renameTo(destFile);
                }
            }
            Logger.DataLogger("checkStatus_Dest input id: "+String.valueOf(id)+" server[1]: "+ server[1]+" lang : "+ formBean.getLang());
            Logger.DataLogger("checkStatus_Src input id: "+String.valueOf(id)+" server[0]: "+ server[1]+" lang : "+ formBean.getLang());
            status.checkStatus_Dest(String.valueOf(id), server[1], formBean.getLang());
            status.checkStatus_Src(String.valueOf(id), server[0], formBean.getLang());

            List<Map<String, String>> l1 = dataManager.getData(id);
            List<Map<String, String>> l2 = dataManager.getDataDlt(id);
            List<Map<String, String>> l3 = dataManager.getDataUpld(id);

            if (l1.size() > 0)
            {
                mav.addObject("ListStatus1", "display");
                mav.addObject("List", l1);
            }
            else
            {
                mav.addObject("ListStatus1", "");
            }

            if (l2.size() > 0)
            {
                mav.addObject("ListStatus2", "display");
                mav.addObject("ListDlt", l2);
            }
            else
            {
                mav.addObject("ListStatus2", "");
            }

            if (l3.size() > 0)
            {
                mav.addObject("ListStatus3", "display");
                mav.addObject("ListUpld", l3);
            }
            else
            {
                mav.addObject("ListStatus3", "");
            }
            mav.setViewName("finfiletransfer/Add");
        }
        catch (Exception e)
        {
            Logger.ErrorLogger(e);
            mav.addObject("error", e.getMessage());
            mav.setViewName("finfiletransfer/Error");
            finutils.errorhandler.ErrorHandler.PrintInFile(e, req);
        }
        return mav;
    }

    public ModelAndView insertData(final HttpServletRequest req, final HttpServletResponse res, FinfiletransferFormBean formBean)
    {
        ModelAndView mav;
        mav = new ModelAndView();
        String empCode = req.getSession().getAttribute(aclEmpCode).toString();
        formBean.setEmpcode(empCode);
        try
        {
            mav.addObject("masterTask", "add");
            Logger.DataLogger(empCode);
            Logger.DataLogger("Lang : " + formBean.getLang());
            if (service.insertMaster(formBean) > 0)
            {
                mav.addObject("DBopration", "success");
            }
            else
            {
                mav.addObject("DBopration", "failure");
            }

            mav.setViewName("finfiletransfer/Add");
        }
        catch (Exception e)
        {
            Logger.DataLogger("Error : " + e.getMessage());
            mav.addObject("error", e.getMessage());
            mav.setViewName("finfiletransfer/Error");
            finutils.errorhandler.ErrorHandler.PrintInFile(e, req);
        }
        return mav;
    }

    public ModelAndView cancelReq(final HttpServletRequest req, final HttpServletResponse res) throws ClassNotFoundException, SQLException
    {
        ModelAndView mav;
        mav = new ModelAndView();
        try
        {
            String empCode = req.getSession().getAttribute(aclEmpCode).toString();
            String masterPK = req.getParameter("masterPK");
            String reqStatus = service.getReqStatus(masterPK);
            if ("N".equals(reqStatus))
            {
                service.changeStatus(masterPK, "cancel", empCode, req.getRemoteAddr());
                mav.addObject("result", "Success");
            }
            else
            {
                mav.addObject("result", "Request Already Executed.");
            }
            mav.addObject("action", "execute");
            mav.setViewName("finfiletransfer/Grid");
        }
        catch (Exception e)
        {
            mav.addObject("error", e.getMessage());
            mav.setViewName("finfiletransfer/Error");
            finutils.errorhandler.ErrorHandler.PrintInFile(e, req);
        }
        return mav;
    }

    public ModelAndView execute(final HttpServletRequest req, final HttpServletResponse res) throws ClassNotFoundException, SQLException
    {
        ModelAndView mav;
        mav = new ModelAndView();
        CheckStatus status = new CheckStatus();
        try
        {
            String empCode = req.getSession().getAttribute(aclEmpCode).toString();

            String masterPK = req.getParameter("masterPK");
            String lang = req.getParameter("lang");
            Logger.DataLogger("lang : " + lang);

            String reqStatus = service.getReqStatus(masterPK);
            if ("N".equals(reqStatus))
            {
                List<Map<String, String>> masterData = service.getMasterData(masterPK);
                FileTransIntermediateDataManager dataManager = new FileTransIntermediateDataManager();
                Map masterDataMap = masterData.get(0);

                String projectNm = masterDataMap.get("PROJECT").toString();
                String srcDest = masterDataMap.get("SRC_DEST").toString();

                int prjRes = service.checkPrj(empCode, projectNm);
                if (prjRes == 1)
                {
                    String dest = srcDest.split("-")[1];
                    boolean empFlag = false;
                    if (dest.equals("prodhoweb1.njtechdesk.com") || dest.equals("prodhoweb2.njtechdesk.com"))
                    {
                        if ("B0023".equals(empCode)
                                || "S0064".equals(empCode)                                
                                || "P0043".equals(empCode)
                                || "P6065".equals(empCode)
                                || "R0024".equals(empCode)
                                || "K0028".equals(empCode)
                                || "P0039".equals(empCode)
                                || "J0020".equals(empCode)
                                || "L0012".equals(empCode)
                                || "P6156".equals(empCode)
                                || "B0022".equals(empCode)
                                || "J0141".equals(empCode)
                                || "R0024".equals(empCode)
                                || "P6156".equals(empCode)
                                || "L0012".equals(empCode)
                                || "J0040".equals(empCode)
                                || "P6070".equals(empCode)
                                || "D0053".equals(empCode)
                                || "P0041".equals(empCode)
                                || "P0040".equals(empCode)
                                || "V0040".equals(empCode)
                                || "D0038".equals(empCode)
                                || "T0058".equals(empCode)
                                || "N0155".equals(empCode)
                                || "B0115".equals(empCode))
                        {
                            empFlag = true;
                        }
                        //special condition for NJCT Project
                        else if("M0166".equals(empCode) && projectNm.equals("537"))   {
                            empFlag = true;
                        }
                        //special condition for mpbc - Unnati - Approval - KM 30/07/2019
                        else if("U0037".equals(empCode) && projectNm.equals("541")) {
                            empFlag = true;
                        }
                    }
                    else
                    {
                        empFlag = true;
                    }
                    if (empFlag)
                    {

                        String genId = masterDataMap.get("GEN_ID").toString();

                        String resultSrcCheck = status.checkStatus_SrcCnfrm(genId, srcDest, lang);
                        if (resultSrcCheck.contains("Y"))
                        {
                            int result1 = 0, result2 = 0;
                            FileTransfer.fileTrans(genId, srcDest, lang);
                            List getData = dataManager.getData(Long.parseLong(genId));
                            List getDataUpld = dataManager.getDataUpld(Long.parseLong(genId));
                            Map m;
                            String statusTemp;
                            for (int i = 0; i < getData.size(); i++)
                            {
                                m = (Map) getData.get(i);
                                statusTemp = m.get("STATUS").toString();
                                if ("N".equals(statusTemp))
                                {
                                    result1 = 1;
                                    break;
                                }
                            }
                            for (int i = 0; i < getDataUpld.size(); i++)
                            {
                                m = (Map) getDataUpld.get(i);
                                statusTemp = m.get("STATUS").toString();
                                if ("N".equals(statusTemp))
                                {
                                    result2 = 1;
                                    break;
                                }
                            }
                            if (result1 == 1 || result2 == 1)
                            {
                                mav.addObject("result", "Transfer failed, may be permission issue.");
                            }
                            else
                            {
                                service.changeStatus(masterPK, "execute", empCode, req.getRemoteAddr());
                                mav.addObject("result", "Success");
                            }

                            mav.addObject("action", "execute");
                        }
                        else
                        {
                            mav.addObject("result", "Source Files Not Exist");
                        }
                    }
                    else
                    {
                        mav.addObject("result", "You are not authorized to transfer pages on prodhoweb1 and / or prodhoweb2.");
                        mav.addObject("action", "execute");
                        mav.setViewName("finfiletransfer/Grid");
                    }
                }
                else
                {
                    mav.addObject("result", "Selected Project is not mapped With the Current User");
                    mav.addObject("action", "execute");
                }
            }
            else
            {
                mav.addObject("result", "Request Already Executed.");
                mav.addObject("action", "execute");
            }
            mav.setViewName("finfiletransfer/Grid");
        }
        catch (Exception e)
        {
            finutils.errorhandler.ErrorHandler.PrintInFile(e, req);
            mav.addObject("error", e.getMessage());
            mav.setViewName("finfiletransfer/Error");
        }
        return mav;
    }

    public ModelAndView getReq(final HttpServletRequest request, final HttpServletResponse response)
    {
        ModelAndView mav;
        mav = new ModelAndView();
        try
        {
            String req = request.getParameter("reqnoTxtLike");
            List reqParticular = service.getRequests("particular", req);
            mav.addObject("requests", reqParticular);
            mav.addObject("action", "reqCombo");
            mav.setViewName("finfiletransfer/Add");
        }
        catch (Exception e)
        {
            mav.addObject("error", e.getMessage());
            mav.setViewName("finfiletransfer/Error");
            finutils.errorhandler.ErrorHandler.PrintInFile(e, request);
        }
        return mav;
    }

    public ModelAndView getReportFile(final HttpServletRequest request, final HttpServletResponse response, final FinfiletransferFormBean formBean)
    {
        ModelAndView mav;
        mav = new ModelAndView();
        try
        {
            String fileName, tomcatPath;
            tomcatPath = finpack.FinPack.getProperty("tomcat1_path");
            fileName = tomcatPath + "/webapps/finstudio/WEB-INF/jrxml/finfiletransfer.jrxml";
            JasperReport jasperReport;
            jasperReport = JasperCompileManager.compileReport(fileName);
            StringBuilder outFileName;
            outFileName = new StringBuilder();
            if (formBean.getRdoRptFormate().equals("Pdf"))
            {
                outFileName.append(tomcatPath).append("/webapps/finstudio/").append(formBean.getReportName()).append(".pdf");
            }
            if (formBean.getRdoRptFormate().equals("XLS"))
            {
                outFileName.append(tomcatPath).append("/webapps/finstudio/").append(formBean.getReportName()).append(".xls");
            }
            Map parameters;
            parameters = new HashMap();
            parameters.put("Name", "MasterReport");
            JRBeanCollectionDataSource dataSource;
            dataSource = new JRBeanCollectionDataSource(service.getDataGrid(formBean));
            JasperPrint print = null;
            print = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
            JRExporter exporter = null;
            if (formBean.getRdoRptFormate().equals("Pdf"))
            {
                exporter = new JRPdfExporter();
            }
            if (formBean.getRdoRptFormate().equals("XLS"))
            {
                exporter = new JRXlsExporter();
            }
            exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outFileName.toString());
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
            if (formBean.getRdoRptFormate().equals("XLS"))
            {
                exporter.setParameter(JRXlsExporterParameter.IS_COLLAPSE_ROW_SPAN, Boolean.TRUE);
                exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
                exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
                exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.FALSE);
                exporter.setParameter(JRXlsExporterParameter.IS_IGNORE_GRAPHICS, Boolean.TRUE);
            }
            exporter.exportReport();
            ServletOutputStream out1;
            out1 = response.getOutputStream();
            if (formBean.getRdoRptFormate().equals("Pdf"))
            {
                response.setContentType("application/pdf");
            }
            if (formBean.getRdoRptFormate().equals("XLS"))
            {
                response.setContentType("application/vnd.ms-excel");
            }
            response.setHeader("Content-disposition", "attachment; filename=" + formBean.getReportName() + "." + formBean.getRdoRptFormate().toLowerCase(Locale.getDefault()));
            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;
            try
            {
                bis = new BufferedInputStream(new FileInputStream(outFileName.toString()));
                bos = new BufferedOutputStream(out1);
                byte[] buff;
                buff = new byte[2048];
                int bytesRead;
                while (-1 != (bytesRead = bis.read(buff, 0, buff.length)))
                {
                    bos.write(buff, 0, bytesRead);
                }
            }
            catch (Exception e)
            {
                finutils.errorhandler.ErrorHandler.PrintInFile(e, request);
                throw new ServletException(e);
            }
            finally
            {
                if (bis != null)
                {
                    bis.close();
                }
                if (bos != null)
                {
                    bos.close();
                }
            }
            out1.close();
            mav.setViewName("finfiletransfer/View");
        }
        catch (Exception e)
        {
            mav.addObject("error", e.getMessage());
            mav.setViewName("finfiletransfer/Error");
            finutils.errorhandler.ErrorHandler.PrintInFile(e, request);
        }
        return null;
    }
}