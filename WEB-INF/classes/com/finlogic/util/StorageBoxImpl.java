/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util;

import finpack.FinPack;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InvalidPropertiesFormatException;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author namrata
 */
public class StorageBoxImpl
{

    private final int projectId;
    private final DateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private final DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private final String sbNullMsg = "Provided null or empty storage filepath ";
    private final String sbRelMsg = "Provided storage relative filepath ";
    private final String notFileMsg = "Not a file ";
    private final String dirProbMsg = "Local temp directory can not be created ";
    private final String sbNAMsg = "StorageBox not available ";
    private final String localSbNAMsg = "Specified local storagebox is not available ";
    private final String localFileNFMsg = "File not found ";
    private final String localPropMsg = "Invalid property value ";
    private final String FAILURE = "failure";
    private final String SUCCESS = "success";
    private final String NA = "-";
    private final String utcEncode = "UTF-8";
    private final String sbApp = "/sbagent/";
    private final int nanoTimeout = 5000;
    private final String urlDelimiter = "/";
    private final String logDelimiter = "\",";
    private final String file = "File";
    private final String dir = "Directory";
    private final String tempFilesPath = FinPack.getProperty("tempfiles_path");
    private final String logDataFilePath = FinPack.getProperty("tomcat1_path") + "/webapps/log/log/sbutility-data-";
    private final String logErrorFilePath = FinPack.getProperty("tomcat1_path") + "/webapps/log/log/sbutility-error-";
    private static long counter;
    private static Random randomno = new Random();
    private final static String refNoStr = "(Ref No.: ";
    private final static String refNoEndStr = ")";

    public StorageBoxImpl(int projectId)
    {
        this.projectId = projectId;
    }

    public boolean isFileExist(String sbFilePath, String envType) throws Exception
    {
        String receivedOn = dateTimeFormat.format(new Date());
        String isCompleted = FAILURE;
        String exceptionCause = NA;

        final long startTime = System.nanoTime();

        try
        {
            String refNo = refNoStr + startTime + refNoEndStr;

            if (sbFilePath == null || sbFilePath.isEmpty())
            {
                exceptionCause = sbNullMsg;
                throw new IllegalArgumentException(sbNullMsg + refNo);
            }
            String vSBFilePath;
            if ((vSBFilePath = sbFilePath.trim()).startsWith("/"))
            {
                exceptionCause = sbRelMsg;
                throw new IllegalArgumentException(sbRelMsg + refNo);
            }
            String[] sbIsExistAgent = null;
            if (envType.equals("devhosb1.nj"))
            {
                sbIsExistAgent = FinPack.getProperty("sbisexistdevagent").split(";");
            }
            else if (envType.equals("testhosb1.nj"))
            {
                sbIsExistAgent = FinPack.getProperty("sbisexisttestagent").split(";");
            }

            //  String[] sbIsExistAgent = FinPack.getProperty("sbisexistagent").split(";");
            if (sbIsExistAgent != null)
            {
                if (sbIsExistAgent[0].startsWith("http"))
                {
                    boolean status = false;
                    StringBuilder urlString = new StringBuilder(sbIsExistAgent[0]);
                    urlString.append(sbApp).append("isfileexist.do?sbfilepath=").append(URLEncoder.encode(vSBFilePath, utcEncode))
                            .append("&cacheid=").append(startTime)
                            .append("&projectid=").append(projectId);

                    HttpClient httpClient = new HttpClient();
                    httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(nanoTimeout);
                    httpClient.getHttpConnectionManager().getParams().setSoTimeout(nanoTimeout);
                    PostMethod ps = new PostMethod(urlString.toString());
                    try
                    {
                        httpClient.executeMethod(ps);
                        // Retry concept

                        if ((ps.getStatusCode()) == HttpURLConnection.HTTP_OK)
                        {
                            if ((ps.getResponseBodyAsString()).equals("true"))
                            {
                                isCompleted = SUCCESS;
                                status = true;
                            }
                            return status;
                        }
                        else
                        {
                            exceptionCause = ps.getResponseBodyAsString();
                            throw new IOException(exceptionCause.replaceAll("\\<.*?>", ""));
                        }

                    }
                    catch (IOException e)
                    {
                        logError(e);
                        if (!exceptionCause.equals("-"))
                        {
                            exceptionCause = e.getMessage() + refNo;
                        }

                        throw new IOException(exceptionCause);

                    }
                    finally
                    {
                        ps.releaseConnection();
                    }
                }
                else
                {
                    throw new InvalidPropertiesFormatException(localPropMsg + refNo);
                }
            }
            else
            {
                exceptionCause = sbNAMsg;
                throw new IllegalStateException(sbNAMsg + refNo);
            }
        }
        finally
        {
            long seconds = getProcessTime(startTime);
            //Log Writing
            StringBuilder logString = new StringBuilder();
            logString.append("\"").append(projectId).append(logDelimiter)
                    .append("\"isFileExist\",")
                    .append("\"").append(receivedOn).append(logDelimiter)
                    .append("\"cache-").append(startTime).append(logDelimiter)
                    .append("\"NA\",")
                    .append("\"").append(sbFilePath).append(logDelimiter)
                    .append("\"NA\",")
                    .append("\"").append(isCompleted).append(logDelimiter)
                    .append("\"").append(exceptionCause).append(logDelimiter)
                    .append("\"").append(seconds).append("s\"");
            logData(logString.toString());
        }
    }

    public String getFile(String sbFilePath, String envType) throws IllegalArgumentException, UnsupportedEncodingException, IOException
    {
        String receivedOn = dateTimeFormat.format(new Date());
        String isCompleted = FAILURE;
        String exceptionCause = NA;

        final long nanoTime = System.nanoTime();
        final String startTime = getUniqNanoTime(nanoTime);
        try
        {
            //Validation
            String refNo = refNoStr + startTime + refNoEndStr;

            if (sbFilePath == null || sbFilePath.isEmpty())
            {
                exceptionCause = sbNullMsg;
                throw new IllegalArgumentException(sbNullMsg + refNo);
            }
            String vSBFilePath;
            if ((vSBFilePath = sbFilePath.trim()).startsWith("/"))
            {
                exceptionCause = sbRelMsg;
                throw new IllegalArgumentException(sbRelMsg + refNo);
            }

            String sbFileName = vSBFilePath.substring(vSBFilePath.lastIndexOf(urlDelimiter) + 1);

            String[] sbGetAgent = null;
            if (envType.equals("devhosb1.nj"))
            {
                sbGetAgent = FinPack.getProperty("sbgetdevagent").split(";");
            }
            else if (envType.equals("testhosb1.nj"))
            {
                sbGetAgent = FinPack.getProperty("sbgettestagent").split(";");
            }

            if (sbGetAgent != null)
            {
                if (sbGetAgent[0].startsWith("http"))
                {
                    StringBuilder urlString = new StringBuilder(sbGetAgent[0]);
                    urlString.append(sbApp).append("getfile.do?sbfilepath=").append(URLEncoder.encode(vSBFilePath, utcEncode))
                            .append("&cacheid=").append(startTime)
                            .append("&projectid=").append(projectId);

                    HttpClient httpClient = new HttpClient();
                    httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(nanoTimeout);
                    httpClient.getHttpConnectionManager().getParams().setSoTimeout(nanoTimeout);
                    PostMethod ps = new PostMethod(urlString.toString());
                    try
                    {
                        httpClient.executeMethod(ps);
                        // Retry concept

                        if ((ps.getStatusCode()) == HttpURLConnection.HTTP_OK)
                        {
                            byte[] responseBody = ps.getResponseBody();
                            StringBuilder tempCachePath = new StringBuilder(tempFilesPath);
                            tempCachePath.append("cache").append(urlDelimiter).append("cache-").append(startTime);
                            boolean dirCreate = new File(tempCachePath.toString()).mkdirs();

                            if (!dirCreate)
                            {
                                exceptionCause = dirProbMsg;
                                throw new IOException(dirProbMsg + refNo);
                            }
                            //create above folder
                            String tempFile = tempCachePath.append(urlDelimiter).append(sbFileName).toString();
                            FileOutputStream output = new FileOutputStream(tempFile);
                            IOUtils.write(responseBody, output);
                            output.close();
                            isCompleted = SUCCESS;

                            return tempFile;
                        }
                        else
                        {
                            exceptionCause = ps.getResponseBodyAsString();
                            throw new IOException(exceptionCause.replaceAll("\\<.*?>", ""));
                        }
                    }
                    catch (IOException e)
                    {
                        logError(e);
                        if (!exceptionCause.equals("-"))
                        {
                            exceptionCause = e.getMessage();
                        }
                        throw new IOException(exceptionCause + refNo);

                    }
                    finally
                    {
                        ps.releaseConnection();
                    }
                }
                else
                {
                    exceptionCause = localPropMsg;
                    throw new InvalidPropertiesFormatException(localPropMsg + refNo);
                }
            }
            else
            {
                exceptionCause = sbNAMsg;
                throw new IllegalStateException(sbNAMsg + refNo);
            }
        }
        finally
        {
            long seconds = getProcessTime(nanoTime);
            //Log Writing
            StringBuilder logString = new StringBuilder();
            logString.append("\"").append(projectId).append(logDelimiter)
                    .append("\"getFile\",")
                    .append("\"").append(receivedOn).append(logDelimiter)
                    .append("\"cache-").append(startTime).append(logDelimiter)
                    .append("\"NA\",")
                    .append("\"").append(sbFilePath).append(logDelimiter)
                    .append("\"NA\",")
                    .append("\"").append(isCompleted).append(logDelimiter)
                    .append("\"").append(exceptionCause).append(logDelimiter)
                    .append("\"").append(seconds).append("s\"");
            logData(logString.toString());
        }
    }

    public String getUniqNanoTime(long nanoTime)
    {
        synchronized (this)
        {
            counter++;
            return Long.toString(nanoTime) + counter + randomno.nextInt(90000);
        }
    }

    private long getProcessTime(long startTime)
    {
        final long elapsedTime = System.nanoTime() - startTime;
        long seconds = TimeUnit.SECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS);
        return seconds;
    }

    private void logData(String data)
    {
        String logFileName = logDataFilePath + dateFormat.format(new Date()) + ".txt";
        try
        {
            FileWriter fw = new FileWriter(logFileName, true);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(data);
            pw.close();
            fw.close();
        }
        catch (IOException ex)
        {
            //System.out.println("Error in FinLogger.logData " + ex.getMessage());
        }
    }

    private void logError(Exception e)
    {
        String logFileName = logErrorFilePath + dateFormat.format(new Date()) + ".txt";
        try
        {
            FileWriter fw = new FileWriter(logFileName, true);
            PrintWriter pw = new PrintWriter(fw);
            e.printStackTrace(pw);
            pw.close();
            fw.close();
        }
        catch (IOException ex)
        {
//           / System.out.println("Error in FinLogger.logError " + ex.getMessage());
        }
    }   

}
