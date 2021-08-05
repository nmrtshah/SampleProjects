/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.apps.eservice.licensegenerator;

import com.finlogic.apps.finstudio.licencegenerate.GenerateLicencePdfFormBean;
import com.finlogic.util.Logger;
import com.finlogic.util.eservice.AESEncDec;
import com.finlogic.util.properties.HardCodeProperty;
import com.lowagie.text.pdf.codec.Base64;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Niral R. Malaviya
 */
public class EserviceLicenseGeneratorService {

    public String genLicenseFile(GenerateLicencePdfFormBean formBean) throws Exception {
        String serviceURL = "";
        String passPhrase;
        String plainText;
        String tempPath;
        String filePath;
        char[] inp = new char[8];
        Long valid_to;
        Long valid_from;
        HardCodeProperty property = new HardCodeProperty();

        passPhrase = property.getProperty("pass_phrase");
        inp = passPhrase.toCharArray();

        valid_from = GetLicenceValidDate(formBean.getActivationDate());
        valid_to = GetLicenceValidDate(formBean.getExpiryDate());

        plainText = "CLIENT_ID=," + formBean.getTenantId()
                + ",VALID_FROM=," + valid_from
                + ",VALID_TILL=," + valid_to
                + ",IP=," + formBean.getIpAddress()
                + ",MAC_ID=,25-12-36-00-12";

        filePath = property.getProperty("license_file_path") + "NJ_CL"
                + formBean.getTenantId() + "_" + formBean.getServerName() + "_ES2.njl";

        switch (formBean.getServerName().toUpperCase()) {
            case "DEV":
                serviceURL = property.getProperty("eservice_dev_url").trim();
                break;
            case "TEST":
                serviceURL = property.getProperty("eservice_test_url").trim();
                break;
            case "PROD":
                serviceURL = property.getProperty("eservice_prod_url").trim();
                break;
            case "PROD-REP":
                serviceURL = property.getProperty("eservice_prod_rep_url").trim();
                break;
            default:
                break;
        }

        InputStream is = new ByteArrayInputStream(plainText.getBytes());
        tempPath = property.getProperty("license_file_path") + System.currentTimeMillis() + ".tempnjl";
        OutputStream os = new FileOutputStream(tempPath);

        AESEncDec.encrypt(256, inp, is, os);

        ConvertEncryptedAEStoBase64(tempPath, filePath);

        String licVal = new String(Files.readAllBytes(Paths.get(filePath)));
        serviceURL = Base64.encodeBytes(serviceURL.getBytes());
        String finalVal = serviceURL + "|" + licVal;

        try (PrintWriter out = new PrintWriter(filePath)) {
            out.println(finalVal);
        }

        File f1 = new File(tempPath);

        if (f1.exists()) {
            f1.delete();
        }
        return filePath.replaceAll(property.getProperty("license_file_path"), "");
    }

    private Long GetLicenceValidDate(String date) throws ParseException {
        Date tempDate = new SimpleDateFormat("dd-mm-yyyy").parse(date);
        String finalDate = new SimpleDateFormat("yyyy-MM-dd").format(tempDate);
        Date dt = new SimpleDateFormat("yyyy-MM-dd").parse(finalDate);
        Long valid_to = dt.getTime();
        return valid_to;
    }

    private void ConvertEncryptedAEStoBase64(String tempPath, String filePath) throws IOException {
        Base64.encodeFileToFile(tempPath, filePath);

    }

    public String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        StringBuilder ips = new StringBuilder();
        if (null != ip
                && !"".equals(ip.trim())
                && !"unknown".equalsIgnoreCase(ip)) {
            // get all ips from proxy ip
            String ipArr[] = ip.split(",");

            for (String ipArr1 : ipArr) {
                if ((ips.length() + ipArr1.length()) < 50) {
                    ips.append(ipArr1).append(",");
                }
            }
        }

        if (ips.toString().isEmpty()) {
            ips.append("N/A");
        } else {
            ips = new StringBuilder(ips.substring(0, ips.length() - 1));
        }
        return ips.toString();
    }

}
