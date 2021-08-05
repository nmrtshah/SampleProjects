/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.apps.finstudio.filedirautorequest;

import com.finlogic.util.Logger;
import com.finlogic.util.disposition.ContentDisposition;
import com.finlogic.util.disposition.ContentDispositionType;
import finpack.FinPack;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Jigna Patel
 */
public class GetProjectFileServlet extends HttpServlet {

    private final DateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private final String FAILURE = "failure";
    private final String SUCCESS = "success";
    private final String NA = "-";
    private final String logDelimiter = "\",";
    private final String tempFilesPath_finstudio = FinPack.getProperty("tempfiles_path") + "finstudio/";
    private final static String refNoStr = " (Ref No.: ";
    private final static String refNoEndStr = ")";

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, FileNotFoundException, IllegalArgumentException {
        String receivedOn = dateTimeFormat.format(new Date());
        String filePath = request.getParameter("filepath");
        String cacheId = request.getParameter("cacheid");
        String zipFilePath = tempFilesPath_finstudio + cacheId + ".zip";
        String refNo = refNoStr + cacheId + refNoEndStr;
        String isCompleted = FAILURE;
        String exceptionCause = NA;
        long sizeBytes = 0;

        try {
            Set<String> filePathSet = new HashSet<>(Arrays.asList(filePath.split(";")));
            String[] filePathArr = filePathSet.toArray(new String[filePathSet.size()]);
            File currFile;
            FileOutputStream fos = new FileOutputStream(zipFilePath);
            ZipOutputStream zos = new ZipOutputStream(fos);
            try {
                for (int i=0; i<filePathArr.length; i++) {
                    String currFilePath = filePathArr[i].trim();
                    currFile = new File(currFilePath);

                    if (currFile.exists() && currFile.isFile() && currFile.length() > 0) {
                        sizeBytes += currFile.length();
                        if(sizeBytes > 2097152) { //only 2mb zip file allowed
                            throw new IOException("The zip file size is greater than 2MB");
                        }
                        FileInputStream fin = new FileInputStream(currFile);
                        BufferedInputStream bis = new BufferedInputStream(fin);

                        zos.putNextEntry(new ZipEntry(currFile.getName()));
                        int count;
                        byte xlbytes[] = new byte[2048];
                        while ((count = bis.read(xlbytes)) != -1) {
                            zos.write(xlbytes, 0, count);
                        }
                        zos.closeEntry();
                        bis.close();
                        fin.close();
                    }
                    else {
                        if(i == 0 && filePathArr.length == 1)
                            throw new IOException("File not found");
                        else
                            Logger.DataLogger("GetProjectFileServlet | Problem with File Path: " + currFilePath);
                    }
                }
            }
            catch (Exception e) {
                throw e;
            }
            finally {
                zos.flush();
                fos.flush();
                zos.close();
                fos.close();
            }

            ContentDisposition cd = new ContentDisposition();
            cd.setFilePath(zipFilePath);
            cd.setResponse(response);
            cd.setRequestType(ContentDispositionType.ATTACHMENT);
            cd.process();
            isCompleted = SUCCESS;
        }
        catch (Exception e) {
            exceptionCause = e.getMessage() + " " + refNo;
            try {
                response.getOutputStream().print(exceptionCause);
            } catch (IOException ex) {
                exceptionCause = ex.getMessage() + " " + refNo;
            }
        }
        finally {
            StringBuilder logString = new StringBuilder();
            logString.append("\"getProjectFile\",")
                    .append("\"").append(receivedOn).append(logDelimiter)
                    .append("\"").append(cacheId).append(logDelimiter)
                    .append("\"").append(filePath).append(logDelimiter)
                    .append("\"").append(sizeBytes).append(logDelimiter)
                    .append("\"").append(isCompleted).append(logDelimiter)
                    .append("\"").append(exceptionCause).append("\"")
                    .append("\n");
            Logger.DataLogger("GetProjectFileServlet Log : " + logString.toString());
        }
    }
}