/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.apps.finstudio.licencegenerate;

import com.finlogic.apps.eservice.licensegenerator.EserviceLicenseGeneratorService;
import com.finlogic.business.finstudio.licencegenerate.CommonLicenceCode;
import com.finlogic.util.Logger;
import com.finlogic.util.properties.HardCodeProperty;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 *
 * @author njuser
 */
public class GenerateLicencePdfController extends MultiActionController {

    private GenerateLicencePdfService service = new GenerateLicencePdfService();
    private final String utilityDirPath = new HardCodeProperty().getProperty("certificatepdfpath");
    private final String emp_name = "emp_name";

    public ModelAndView getClientPage(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        ModelAndView mav;
        mav = new ModelAndView();
        String finlibPath = finpack.FinPack.getProperty("finlib_path");
        mav.addObject("finlib_path", finlibPath);
        mav.addObject("cur_date", new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime()));
        mav.setViewName("licencegenerate/Main");
        return mav;
    }

    public ModelAndView clientAddPage(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        ModelAndView mav;
        mav = new ModelAndView();
        mav.addObject("action", "clientAdd");
        mav.setViewName("licencegenerate/clientAdd");
        return mav;
    }

    public ModelAndView insertClient(final HttpServletRequest request, final HttpServletResponse response, final GenerateLicencePdfFormBean formBean) throws Exception {
        ModelAndView mav;
        mav = new ModelAndView();
//        String empName = "Janesh Bhatt";
        String empName = request.getSession().getAttribute(emp_name).toString();

        if (service.insertClient(formBean, empName) > 0) {
            mav.addObject("msg", "Client is added inserted successfully");
            mav.setViewName("licencegenerate/clientAdd");
        }
        mav.addObject("action", "insertClient");
        return mav;
    }

    public ModelAndView generateCertificatePage(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        ModelAndView mav;
        mav = new ModelAndView();
        mav.addObject("action", "generateCertificate");
        mav.addObject("cur_date", new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime()));
        mav.addObject("clientList", service.getClientList());
        mav.addObject("serverList", new HardCodeProperty().getProperty("serverlist").split(","));// Added for eService License Generation purpose
        mav.setViewName("licencegenerate/generatecertificate");
        return mav;
    }

    public ModelAndView insertClientDetail(final HttpServletRequest request, final HttpServletResponse response, final GenerateLicencePdfFormBean formBean) throws Exception {
        ModelAndView mav;
        mav = new ModelAndView();
        mav.addObject("action", "generateCertificate");
        mav.addObject("cur_date", new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime()));
        mav.addObject("clientList", service.getClientList());
        mav.addObject("serverList", new HardCodeProperty().getProperty("serverlist").split(","));// Added for eService License Generation purpose

//        String empName = "Janesh Bhatt";
        String empName = request.getSession().getAttribute(emp_name).toString();
        CommonLicenceCode clc = new CommonLicenceCode();

        try {
            mav.addObject("process", "linkGenerate");
            String[] tempDate = formBean.getExpiry_date().split("-");
            String folder = tempDate[2] + tempDate[1] + tempDate[0] + "_" + Calendar.getInstance().getTimeInMillis();
            String expDate = tempDate[2] + tempDate[1] + tempDate[0];
            String secureExpDate = clc.secureExpDate(expDate);
            String productDirPath = utilityDirPath + folder + "/";
            String sourceFolder = new HardCodeProperty().getProperty("sourceFolder");
            String logoFileName = new HardCodeProperty().getProperty("njlogoname");
            String pdfFileName = new HardCodeProperty().getProperty("njpdfname");

            File productDir = new File(productDirPath);
            if (!productDir.exists()) {
                productDir.mkdir();
            }
            String sysInfo = formBean.getSyskey();
            clc.loadSecureImage(sourceFolder, logoFileName, productDirPath, secureExpDate + "," + sysInfo);
            clc.createSecureDoc(sourceFolder + pdfFileName, productDirPath + logoFileName, productDirPath + pdfFileName, service.getPassword(formBean.getClientcmb()), formBean.getClientcmb(), expDate);
            mav.addObject("fileName", pdfFileName.substring(0, pdfFileName.length() - 4) + "Certificate" + formBean.getClientcmb());
            mav.addObject("folderPath", folder);
            service.insertClientDetails(formBean, empName);
        } catch (Exception e) {
            mav.addObject("process", "error");
        }
        mav.setViewName("licencegenerate/generatecertificate");
        return mav;
    }

    public ModelAndView getReportPage(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        ModelAndView mav;
        mav = new ModelAndView();
        mav.addObject("action", "reportPage");
        mav.addObject("clientList", service.getClientList());
        mav.setViewName("licencegenerate/report");
        return mav;
    }

    public ModelAndView reportLoader(final HttpServletRequest request, final HttpServletResponse response, final GenerateLicencePdfFormBean formBean) throws Exception {
        ModelAndView mav;
        mav = new ModelAndView();
        mav.addObject("jsonString", service.reportLoader(formBean));
        mav.setViewName("licencegenerate/reportJson");
        return mav;
    }

    public ModelAndView downloadFile(HttpServletRequest req, HttpServletResponse res) {
        ModelAndView mv = new ModelAndView("licencegenerate/download");
        String folderName = req.getParameter("folder");
        mv.addObject("fileName", req.getParameter("fileName"));
        mv.addObject("path", utilityDirPath + folderName);
        return mv;
    }

    public ModelAndView generateLicense(final HttpServletRequest request, final HttpServletResponse response, final GenerateLicencePdfFormBean formBean) throws Exception {
        ModelAndView mv = new ModelAndView();
        EserviceLicenseGeneratorService objEservice = new EserviceLicenseGeneratorService();

        mv.setViewName("licencegenerate/generatecertificate");
        mv.addObject("action", "generateCertificate");
        mv.addObject("cur_date", new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime()));
        mv.addObject("clientList", service.getClientList());
        mv.addObject("serverList", new HardCodeProperty().getProperty("serverlist").split(","));// Added for eService License Generation purpose

        try {
            mv.addObject("process", "genLicenseLink");
            formBean.setIpAddress(objEservice.getIpAddr(request));
            mv.addObject("fileName", objEservice.genLicenseFile(formBean));
        } catch (Exception e) {
            Logger.ErrorLogger(e);
            mv.addObject("process", "error");
        }
        return mv;
    }

    public ModelAndView downloadLicFile(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("licencegenerate/download");
        mv.addObject("fileName", request.getParameter("FileName"));
        String path = new HardCodeProperty().getProperty("license_file_path");
        mv.addObject("path", path.substring(0, path.lastIndexOf("/")));
        return mv;
    }

}
