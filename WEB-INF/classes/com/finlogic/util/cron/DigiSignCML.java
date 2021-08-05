/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.cron;

import com.finlogic.util.Logger;
import com.finlogic.util.pdfsign.PDFSignEToken;
import com.finlogic.util.pdfsign.SignPDFConfigBean;
import com.lowagie.text.pdf.PdfReader;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author Jigna Patel
 */
public class DigiSignCML
{    
    public static void main(String[] args)
    {
        try
        {
            String srcPath = args[0];
            String destPath = args[1];
            int llx = Integer.parseInt(args[2]);
            int lly = Integer.parseInt(args[3]);
            int urx = Integer.parseInt(args[4]);
            int ury = Integer.parseInt(args[5]);
            int pageno = Integer.parseInt(args[6]);
            String reason = args[7];
            String location = args[8];
            File dirFile = new File(srcPath);
            if(dirFile.exists()) {
                if(dirFile.isDirectory()) {
                    File[] fileList = dirFile.listFiles();
                    for (File subFile : fileList) {
                        makeSign(subFile.getName(),llx,lly,urx,ury,pageno,srcPath,destPath,reason,location);
                        System.out.println(subFile.getName() + " - signed.");
                        subFile.delete();
                    }
                }
            }
        }
        catch(Exception e)
        {
            Logger.DataLogger("DigiSignCML | Error : " + e.toString());
            Logger.ErrorLogger(e);
        }
    }
    
    private static void makeSign(String fileName,int llx,int lly,int urx,int ury,int pageno,String srcPath,String destPath,String reason,String location) throws IOException
    {
        SignPDFConfigBean snConfig = new SignPDFConfigBean();
        PdfReader reader = new PdfReader(srcPath + fileName);
        int pageNo;
        if(pageno == -1)
        {
            pageNo = reader.getNumberOfPages();
        }
        else
        {
            pageNo = pageno;
        }
        snConfig.setSrcPDFPath(srcPath + fileName);
        snConfig.setDestPDFPath(destPath + "Signed_" + fileName);
        snConfig.setReason(reason);
        snConfig.setLocation(location);
        snConfig.setLlx(llx); //650
        snConfig.setLly(lly);  //30
        snConfig.setUrx(urx); //400 
        snConfig.setUry(ury);  //70
        snConfig.setOnPageNo(pageNo);
        snConfig.setProjectId(47); //finstudio project id

        PDFSignEToken pdfETokenObj = new PDFSignEToken();
        pdfETokenObj.signPDF(snConfig);
    }
}