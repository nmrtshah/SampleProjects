/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.apps.finstudio.filedirautorequest;

import com.finlogic.util.Logger;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Jigna Patel
 */
public class GetProjectFileListServlet extends HttpServlet
{
    private final DateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private final String SUCCESS = "success";
    private final String FAILURE = "failure";
    private final String NA = "-";
    private final String file = "File,";
    private final String dir = "Directory,0";
    private final String dnfMsg = "Dir not found ";
    private final String notDirMsg = "Not a directory ";
    private final String limitMsg = "ListFile Limit Exceeded ";
    private final int listLimitSize = Integer.parseInt(finpack.FinPack.getProperty("listlimitsize"));
    private final String logDelimiter = "\",";
    private final String commaDelimiter = ",";
    private final String recDelimiter = ";";
    private final static String refNoStr = " (Ref No.: ";
    private final static String refNoEndStr = ")";
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, FileNotFoundException, IllegalArgumentException
    {
        String receivedOn = dateTimeFormat.format(new Date());
        String dirPath = request.getParameter("dirpath");
        String cacheId = request.getParameter("cacheid");
        String isCompleted = FAILURE;
        String exceptionCause = NA;
        String refNo = refNoStr + cacheId + refNoEndStr;
        
        boolean dirExist = false;
        boolean isDir = false;
        int counter = 0;
        
        try {
            StringBuilder fileListStr = new StringBuilder();
            
            File dirFile = new File(dirPath);
            if(dirFile.exists()) {
                dirExist = true;
                
                if (dirFile.isDirectory()) {
                    isDir = true;
                    
                    String replacePath = dirPath.substring(0,dirPath.lastIndexOf('/')+1);
                    
                    File[] fileList = dirFile.listFiles();
                    for (File subFile : fileList) {
                        counter++;
                        if (counter > listLimitSize)
                        {
                            exceptionCause = limitMsg + dirPath;
                            throw new IOException(exceptionCause);
                        }
                        fileListStr.append(recDelimiter)
                                   .append(subFile.getAbsolutePath().replaceFirst(replacePath, ""))
                                   .append(commaDelimiter).append(subFile.isFile() ? file + subFile.length() : dir)
                                   .append(commaDelimiter).append(subFile.lastModified());
                    }
                }
            }
            
            if (!dirExist) {
                exceptionCause = dnfMsg + dirPath;
                throw new FileNotFoundException(exceptionCause);
            }

            if (!isDir) {
                exceptionCause = notDirMsg + dirPath;
                throw new IllegalArgumentException(exceptionCause);
            }
            
            if (counter == 0) {
                response.getOutputStream().print("Null");
            }
            else {
                response.getOutputStream().print(fileListStr.substring(1));
            }
            isCompleted = SUCCESS;
        }
        catch(Exception e) {
            exceptionCause = e.getMessage() + " " + refNo;
            try {
                response.getOutputStream().print(exceptionCause);
            } catch (IOException ex) {
                exceptionCause = ex.getMessage() + " " + refNo;
            }
        }
        finally {
            StringBuilder logString = new StringBuilder();
            logString.append("\"getProjectFileList\",")
                     .append("\"").append(receivedOn).append(logDelimiter)
                     .append("\"").append(cacheId).append(logDelimiter)
                     .append("\"").append(dirPath).append(logDelimiter)
                     .append("\"").append(isCompleted).append(logDelimiter)
                     .append("\"").append(exceptionCause).append("\"")
                     .append("\n");
            Logger.DataLogger("GetProjectFileListServlet Log : "+logString.toString());
        }
    }
}