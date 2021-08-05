/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.cron;

import com.finlogic.util.Logger;
import com.finlogic.util.SBCommonOperation;
import com.finlogic.util.persistence.SQLUtility;
import finpack.FinPack;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.attribute.GroupPrincipal;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.nio.file.attribute.UserPrincipal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author njuser
 */
public class FileDirAutoRequestCron
{

    private static final SQLUtility sqlUtility = new SQLUtility();
    private static final String FINALIAS = "finstudio_dbaudit_common";
    private static final String sbApp = "/sbagent/";
    private static final int projectId = 47;
    private static final int nanoTimeout = 5000;
    private static final Random randomNo = new Random();
    private static final String urlDelimiter = "/";
    private static final String utcEncode = "UTF-8";
    private static final String dirProbMsg = "Local temp directory can not be created ";
    private static final String maxFileSizeMsg = "The zip file size is greater than 2MB";
    private static final String refNoStr = "(Ref No.: ";
    private static final String refNoEndStr = ")";
    private static long counter;
    private static final String tempFilesPath = FinPack.getProperty("tempfiles_path");
    private static final String serverNames = "x,devhoweb1.nj,devhoweb2.nj,devhoweb3.nj,devhoweb4.nj,testhoweb1.nj,testhoweb2.nj,testhoweb3.nj,testhoweb4.nj,prodhoweb1.nj,prodhoweb2.nj,prodhoweb3.nj,prodhoweb4.nj,x";
    private static final String serverNamesRep = "x,devhoweb1rep.nj,devhoweb2rep.nj,devhoweb3rep.nj,testhoweb1rep.nj,testhoweb2rep.nj,testhoweb3rep.nj,testhoweb4rep.nj,prodhoweb1rep.nj,prodhoweb2rep.nj,prodhoweb3rep.nj,prodhoweb4rep.nj,x";

    public static void main(String[] args)
    {
        try
        {
            org.apache.log4j.Logger.getRootLogger().setLevel(org.apache.log4j.Level.ERROR);

//            /devweb1.nj, testweb1.nj
            // Getting Servername
            //testweb3
            String server = getServerName();
            String retrieveRecords = " SELECT SRNO, PATH, SERVER_NAME, PROCESS FROM FILEDIR_AUTO_REQUEST WHERE STATUS = 'authorized' AND (SERVER_NAME = '" + server + "')";
            //devweb3 -> devsb1.nj, testweb3 -> testsb1.nj

            //namrata: howeb3
            if (server.equals("prodhoweb3.nj"))
            {
                //retrieveRecords = retrieveRecords.replace(")", " OR SERVER_NAME like '%sb1.nj') AND PROCESS = 'GetFile'");
                retrieveRecords = retrieveRecords.replace(")", " OR SERVER_NAME like '%sb1.nj' OR PROCESS IN ('GetFile','GetList'))");

            }
            // namrata: howeb4,2,1,devweb1/2/3/4,testweb1/2/3/4
            else
            {
                retrieveRecords = retrieveRecords.replace(")", " AND PROCESS NOT IN ('GetFile','GetList'))");
            }

            //howeb3- create/isexist/delete/ & dev/test/prodSB - get/isfile
            //Getting Resultset
            List list = sqlUtility.getList(FINALIAS, retrieveRecords);
            //Create folders
            if (list.size() > 0)
            {
                for (Object row : list)
                {
                    Map m = (Map) row;

                    int SRNO = (Integer) m.get("SRNO");
                    String PROCESS = (String) m.get("PROCESS");
                    String PATH = (String) m.get("PATH");

                    if (!isValidPath(PATH))
                    {
                        sqlUtility.persist(FINALIAS, " UPDATE FILEDIR_AUTO_REQUEST SET STATUS = 'done', COMMENT = 'invalid path' WHERE SRNO = " + SRNO);
                        Logger.DataLogger("Path is invalid : " + PATH);
                        continue;
                    }
                    if (!checkRep(m))
                    {
                        sqlUtility.persist(FINALIAS, " UPDATE FILEDIR_AUTO_REQUEST SET STATUS = 'done', COMMENT = 'invalid path for replication server' WHERE SRNO = " + SRNO);
                        Logger.DataLogger("Path is invalid : " + PATH);
                        continue;
                    }
                    if ("Create".equals(PROCESS))
                    {
                        create(m);
                    }
                    if ("CheckExist".equals(PROCESS)) //howeb3-applicationwise or dev/test/prod SB - isfileexist // for except howeb3-applicationwise
                    {
                        checkExist(m);
                    }
                    if ("Delete".equals(PROCESS))
                    {
                        delete(m);
                    }
                    if ("GetFile".equals(PROCESS)) //howeb3-applicationwise or dev/test/prodSB - getfile
                    {
                        getFile(m);
                    }
                    if ("GetList".equals(PROCESS)) //howeb3-applicationwise or dev/test/prodSB - getfilelist
                    {
                        getFileList(m);
                    }
                }
            }
            else
            {
                if (args != null && args.length > 0 && args[0].equals("debug"))
                {
                    Logger.DataLogger("No data found.");
                }
            }
        }
        catch (Exception e)
        {
            Logger.DataLogger("FileDirAutoRequestCron | Error : " + e.toString());
            Logger.ErrorLogger(e);
        }
    }

    public static void create(Map m) throws ClassNotFoundException, SQLException, IOException
    {
        int SRNO = (Integer) m.get("SRNO");
        String PATH = (String) m.get("PATH");
        String Decimalpattern = "^[a-zA-Z0-9/\\-_\\^]+$";
        String[] splitPath = PATH.split("/");
        String existPath = "/" + splitPath[1] + "/" + splitPath[2] + "/" + splitPath[3];

        if (PATH.trim().matches(Decimalpattern) && new File(existPath).exists())
        {
            File f = new File(PATH);

            if (f.exists())
            {
                sqlUtility.persist(FINALIAS, " UPDATE FILEDIR_AUTO_REQUEST SET STATUS = 'done', COMMENT = 'already exist' WHERE SRNO = " + SRNO);
                Logger.DataLogger("Folder already Exist : " + PATH);
            }
            else
            {
                f.mkdirs();
                Runtime.getRuntime().exec("/bin/chmod 777 -R " + f.getAbsolutePath());
                sqlUtility.persist(FINALIAS, " UPDATE FILEDIR_AUTO_REQUEST SET STATUS = 'done', COMMENT = 'created' WHERE SRNO = " + SRNO);
                Logger.DataLogger("created : " + PATH);
            }
        }
        else
        {
            sqlUtility.persist(FINALIAS, " UPDATE FILEDIR_AUTO_REQUEST SET STATUS = 'done', COMMENT = 'invalid path' WHERE SRNO = " + SRNO);
            Logger.DataLogger("Path is invalid : " + PATH);
        }
    }

    public static void checkExist(Map m) throws ClassNotFoundException, SQLException
    {
        String comment;
        int SRNO = (Integer) m.get("SRNO");
        String PATH = (String) m.get("PATH");
        String ServerName = (String) m.get("SERVER_NAME");

        if (ServerName.contains("sb1.nj"))
        {
            boolean existance;
            try
            {
                if (ServerName.equals("prodhosb1.nj"))
                {
                    SBCommonOperation sbCommon = SBCommonOperation.getSBCommonOperation();
                    existance = sbCommon.isFileExist(PATH.replaceFirst("/opt/application_storage/storage_box/", ""));
                }
                else
                {
                    com.finlogic.util.StorageBoxImpl sb = SBCommonOperation.getStorageBoxImpl();
                    existance = sb.isFileExist(PATH.replaceFirst("/opt/application_storage/storage_box/", ""), ServerName);
                }
                String exist;
                if (existance)
                {
                    exist = "exist";
                }
                else
                {
                    exist = "not exist";
                }
                sqlUtility.persist(FINALIAS, " UPDATE FILEDIR_AUTO_REQUEST SET STATUS = 'done', COMMENT = '" + exist + "' WHERE SRNO = " + SRNO);
                //Logger.DataLogger(exist + " Folder : " + PATH);
            }
            catch (Exception ex)
            {
                String dirStatus;
                if (ex.getMessage().contains("Exception: Not a file"))
                {
                    dirStatus = "exist";
                }
                else
                {
                    dirStatus = "error";
                }
                sqlUtility.persist(FINALIAS, " UPDATE FILEDIR_AUTO_REQUEST SET STATUS = 'done', COMMENT = '" + dirStatus + "' WHERE SRNO = " + SRNO);

                Logger.DataLogger("FileDirAutoRequestCron | Error : " + ex.toString());
                Logger.ErrorLogger(ex);
            }
        }
        else
        {
            File f = new File(PATH);
            if (f.exists())
            {
                try
                {
                    PosixFileAttributes attrs = Files.readAttributes(f.toPath(), PosixFileAttributes.class, LinkOption.NOFOLLOW_LINKS);
                    GroupPrincipal group = attrs.group();
                    UserPrincipal owner = attrs.owner();
                    Set<PosixFilePermission> filePermission = attrs.permissions();

                    Calendar cal = Calendar.getInstance();
                    cal.setTimeInMillis(f.lastModified());
                    String lastMod = cal.get(Calendar.DAY_OF_MONTH) + "/" + (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.YEAR);
                    lastMod += " " + cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE);

                    comment = ",Owner:".concat(owner.getName())
                            .concat(",Group:").concat(group.getName())
                            .concat(",Perm:").concat(PosixFilePermissions.toString(filePermission))
                            .concat(",ModifiedTime:").concat(lastMod);
                }
                catch (IOException ioe)
                {
                    comment = "";
                }
                sqlUtility.persist(FINALIAS, " UPDATE FILEDIR_AUTO_REQUEST SET STATUS = 'done', COMMENT = 'exist"
                        + comment + "' WHERE SRNO = " + SRNO);
                //Logger.DataLogger("Exist Folder : " + PATH);
            }
            else
            {
                sqlUtility.persist(FINALIAS, " UPDATE FILEDIR_AUTO_REQUEST SET STATUS = 'done', COMMENT = 'not exist' WHERE SRNO = " + SRNO);
                //Logger.DataLogger("Not Exist Folder : " + PATH);
            }
        }
    }

    public static void getFile(Map m) throws ClassNotFoundException, SQLException
    {
        int SRNO = (Integer) m.get("SRNO");
        String PATH = (String) m.get("PATH");
        String ServerName = (String) m.get("SERVER_NAME");
        String comment;
        String filePath;

        try {
            if (ServerName.contains("sb1.nj"))
            {
                String sbfilepath = PATH.replaceFirst("/opt/application_storage/storage_box/", "");
                if (ServerName.equals("prodhosb1.nj"))
                {
                    SBCommonOperation sbCommon = SBCommonOperation.getSBCommonOperation();
                    filePath = sbCommon.getFile(sbfilepath);
                }
                else
                {
                    com.finlogic.util.StorageBoxImpl sb = SBCommonOperation.getStorageBoxImpl();
                    filePath = sb.getFile(sbfilepath, ServerName);

                }
                comment = filePath.replaceFirst("/opt/application_storage/temp_files/cache/", "");
            }
            else
            {
                final long nanoTime = System.nanoTime();
                final String startTime = new FileDirAutoRequestCron().getUniqNanoTime(nanoTime);
                String refNo = refNoStr + startTime + refNoEndStr;

                StringBuilder localCachePath = new StringBuilder(tempFilesPath);
                String cachFolder = "cache-" + startTime;
                localCachePath.append("cache").append(urlDelimiter).append(cachFolder);
                boolean dirCreate = new File(localCachePath.toString()).mkdirs();
                if (!dirCreate)
                {
                    throw new IOException(dirProbMsg + refNo);
                }
                String zipFileName = startTime + ".zip";
                
                StringBuilder urlString = new StringBuilder(getDomain(ServerName));
                urlString.append("/finstudio/getprojectfile.do?filepath=");
                urlString.append(URLEncoder.encode(PATH, utcEncode));
                urlString.append("&cacheid=").append(startTime);
                HttpClient httpClient = new HttpClient();
                httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(nanoTimeout);
                httpClient.getHttpConnectionManager().getParams().setSoTimeout(nanoTimeout);
                PostMethod ps = new PostMethod(urlString.toString());
                try {
                    httpClient.executeMethod(ps);
                    // Retry concept
                    if ((ps.getStatusCode()) == HttpURLConnection.HTTP_OK) {
                        byte[] responseBody = ps.getResponseBody();
                        if(ps.getResponseBodyAsString().contains("Invalid Request")) {
                            throw new IOException(ps.getResponseBodyAsString());
                        }
                        else if (ps.getResponseBodyAsString().equals("Null") || ps.getResponseBodyAsString().contains(refNoStr)) {
                            throw new IOException(ps.getResponseBodyAsString());
                        }
                        else {
                            String tempFile = localCachePath + urlDelimiter + zipFileName;
                            FileOutputStream output = new FileOutputStream(tempFile);
                            IOUtils.write(responseBody, output);
                        }
                    }
                    else {
                        throw new IOException(ps.getResponseBodyAsString().replaceAll("\\<.*?>", ""));
                    }
                }
                catch (Exception e) {
                    throw e;
                }
                finally {
                    ps.releaseConnection();
                }
                comment = cachFolder + urlDelimiter + zipFileName;
            }
        }
        catch (Exception ex)
        {
            String dirStatus;
            if (ex.toString().contains("File not found")) {
                dirStatus = "File not found";
            } else if(ex.toString().contains("temp directory")) {
                dirStatus = "Directory cannot be created";
            } else if(ex.toString().contains("Zip not found")) {
                dirStatus = "Zip not found";
            } else if(ex.toString().contains("MB")) {
                dirStatus = maxFileSizeMsg;
            } else if(ex.toString().contains("Permission")) {
                dirStatus = "Permission denied for given Path";
            } else {
                dirStatus = "error";
            }
            comment = dirStatus;
            Logger.DataLogger("FileDirAutoRequestCron | Error : " + ex);
        }
        sqlUtility.persist(FINALIAS, " UPDATE FILEDIR_AUTO_REQUEST SET STATUS = 'done', COMMENT = '" + comment + "' WHERE SRNO = " + SRNO);
    }

    public static void delete(Map m) throws ClassNotFoundException, SQLException
    {
        int SRNO = (Integer) m.get("SRNO");
        String PATH = (String) m.get("PATH");

        File f = new File(PATH);

        if (f.exists())
        {
            if (deleteFilesAndFolders(f))
            {
                sqlUtility.persist(FINALIAS, " UPDATE FILEDIR_AUTO_REQUEST SET STATUS = 'done', COMMENT = 'deleted' WHERE SRNO = " + SRNO);
                Logger.DataLogger("Deleted Folder : " + PATH);
            }
        }
        else
        {
            sqlUtility.persist(FINALIAS, " UPDATE FILEDIR_AUTO_REQUEST SET STATUS = 'done', COMMENT = 'not exist' WHERE SRNO = " + SRNO);
            Logger.DataLogger("Not Exist Folder for Delete : " + PATH);
        }
    }

    public static boolean checkRep(Map m)
    {
        String PATH = (String) m.get("PATH");
        String SERVER_NAME = (String) m.get("SERVER_NAME");
        String PROCESS = (String) m.get("PROCESS");

        if (SERVER_NAME.contains("rep.nj"))
        {
            if (PROCESS.equals("Create") || PROCESS.equals("Delete"))
            {
                if (!PATH.startsWith("/opt/application_storage/temp_files/"))
                {
                    return false;
                }
            }

        }
        return true;
    }

    public static boolean deleteFilesAndFolders(final File fileP)
    {
        if (fileP.isDirectory())
        {
            for (final File fileEntry : fileP.listFiles())
            {
                if (fileEntry.isDirectory())
                {
                    deleteFilesAndFolders(fileEntry);
                }
                else
                {
                    fileEntry.delete();
                }
            }
        }
        fileP.delete();
        return true;
    }

    public static String getServerName() throws FileNotFoundException, IOException
    {

        String servername = InetAddress.getLocalHost().getHostName();

        if (servername == null)
        {
            servername = "";
        }
        servername = servername.trim().toLowerCase();
        if (!servername.contains(".nj"))
        {
            servername = servername + ".nj";
        }
        if (serverNames.contains("," + servername + ",") || serverNamesRep.contains("," + servername + ","))
        {
            return servername;
        }
        else
        {
            Logger.DataLogger("Invalid server name : " + servername);
            return "NA";
        }
    }

    public static boolean isValidPath(String PATH)
    {
        String[] filterPaths =
        {
            "/opt/apache-tomcat1/webapps/", "/var/www/html/", "/opt/application_storage/"
        };
        boolean flag = false;

        for (String filterPath : filterPaths)
        {
            if (PATH.startsWith(filterPath))
            {
                flag = true;
            }
        }
        return flag;
    }
    
    private String getUniqNanoTime(long nanoTime)
    {
        synchronized (this)
        {
            counter++;
            return Long.toString(nanoTime) + counter + randomNo.nextInt(90000);
        }
    }
    
    private static void getFileList(Map m) throws ClassNotFoundException, SQLException
    {
        int srno = (Integer) m.get("SRNO");
        String path = (String) m.get("PATH");
        String serverName = (String) m.get("SERVER_NAME");
        String comment = "";
        
        final long nanoTime = System.nanoTime();
        final String startTime = new FileDirAutoRequestCron().getUniqNanoTime(nanoTime);
        String refNo = refNoStr + startTime + refNoEndStr;
        
        StringBuilder urlString = new StringBuilder();

        try {
            StringBuilder localCachePath = new StringBuilder(tempFilesPath);
            String cachFolder = "cache-" + startTime;
            localCachePath.append("cache").append(urlDelimiter).append(cachFolder);
            boolean dirCreate = new File(localCachePath.toString()).mkdirs();
            if (!dirCreate)
            {
                throw new IOException(dirProbMsg + refNo);
            }
            
            if (serverName.contains("sb1.nj"))
            {
                String sbFilePath = path.replaceFirst("/opt/application_storage/storage_box/", "");
                String[] sbGetAgent;
                switch (serverName) {
                    case "devhosb1.nj":
                        sbGetAgent = FinPack.getProperty("sblistdevagent").split(";");
                        break;
                    case "testhosb1.nj":
                        sbGetAgent = FinPack.getProperty("sblisttestagent").split(";");
                        break;
                    default:
                        sbGetAgent = FinPack.getProperty("sblistagent").split(";");
                        break;
                }
                urlString.append(sbGetAgent[0]);
                urlString.append(sbApp);
                urlString.append("getlist.do?sbdirpath=").append(URLEncoder.encode(sbFilePath, utcEncode));
                urlString.append("&cacheid=").append(startTime);
                urlString.append("&projectid=").append(projectId);
            }
            else
            {
                urlString.append(getDomain(serverName));
                urlString.append("/finstudio/getprojectfilelist.do?dirpath=");
                urlString.append(URLEncoder.encode(path, utcEncode));
                urlString.append("&cacheid=").append(startTime);
            }
            String listDtlFileName = "";
            HttpClient httpClient = new HttpClient();
            httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(nanoTimeout);
            httpClient.getHttpConnectionManager().getParams().setSoTimeout(nanoTimeout);
            PostMethod ps = new PostMethod(urlString.toString());
            try {
                httpClient.executeMethod(ps);
                if ((ps.getStatusCode()) == HttpURLConnection.HTTP_OK) {
                    String responseBody = ps.getResponseBodyAsString();
                    if(ps.getResponseBodyAsString().contains("Invalid Request")) {
                        throw new IOException(ps.getResponseBodyAsString());
                    }
                    else if (responseBody.equals("Null") || responseBody.contains(refNoStr)) {
                        throw new IOException(responseBody);
                    }
                    else {
                        listDtlFileName = startTime + ".csv";
                        File listDtlFile = new File(localCachePath.toString() + urlDelimiter + listDtlFileName);
                        listDtlFile.createNewFile();
                        FileWriter writer = new FileWriter(listDtlFile);

                        String[] listFiles = responseBody.split(";");
                        for (int i=0; i<listFiles.length; i++) {
                            String currRec = listFiles[i];
                            String[] currRecList = currRec.split(",");
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a");

                            if(i != 0) 
                                writer.write("\n");
                            writer.write(currRecList[0] + ",");
                            writer.write(currRecList[1] + ",");
                            writer.write(currRecList[2] + ",");
                            writer.write(sdf.format(Long.parseLong(currRecList[3])));
                        }
                        writer.close();
                    }
                }
                else {
                    throw new IOException(ps.getResponseBodyAsString().replaceAll("\\<.*?>", ""));
                }
            }
            catch(Exception e) {
                throw e;
            }
            finally {
                ps.releaseConnection();
            }
            comment = cachFolder + urlDelimiter + listDtlFileName;
        }
        catch(Exception e) {
            String dirStatus;
            if (e.getMessage().contains("File not found")) {
                dirStatus = "Not Exist";
            } else if (e.getMessage().contains("Dir not found") || e.getMessage().contains("Not a directory")) {
                dirStatus = "Directory not Exist";
            } else if (e.getMessage().contains("Limit Exceeded")) {
                dirStatus = "Limit Exceeded";
            } else if(e.toString().contains("Permission")) {
                dirStatus = "Permission denied for given Path";
            } else {
                dirStatus = "error";
            }
            comment = dirStatus;
            Logger.DataLogger("FileDirAutoRequestCron | Error : " + e.toString());
        }
        sqlUtility.persist(FINALIAS, " UPDATE FILEDIR_AUTO_REQUEST SET STATUS = 'done', COMMENT = '" + comment + "' WHERE SRNO = " + srno);
    }
    
    private static String getDomain(String serverName) {
        
        String domainUrl;
        switch(serverName) {
            case "prodhoweb1.nj":
            case "prodhoweb1rep.nj":
                domainUrl = "http://" + serverName + ":8233"; //finstudio tomcat port
                break;
            case "prodhoweb2.nj":
            case "prodhoweb2rep.nj":
                domainUrl = "http://" + serverName + ":8190"; //finstudio tomcat port
                break;
            case "prodhoweb3.nj":
            case "prodhoweb3rep.nj":
                domainUrl = "http://" + serverName + ":8138"; //finstudio tomcat port
                break;
            case "prodhoweb4.nj":
            case "prodhoweb4rep.nj":
                domainUrl = "http://" + serverName + ":8222"; //finstudio tomcat port
                break;
            case "testhoweb1.nj":
                domainUrl = "https://testhoweb1.njtechdesk.com";
                break;
            case "testhoweb2.nj":
                domainUrl = "https://testhoweb2.njtechdesk.com";
                break;
            case "testhoweb3.nj":
                domainUrl = "https://testhoweb3.njtechdesk.com";
                break;
            case "testhoweb4.nj":
                domainUrl = "https://testhoweb4.njtechdesk.com";
                break;
            case "testhoweb1rep.nj":
                domainUrl = "https://testhoweb1rep.njtechdesk.com";
                break;
            case "testhoweb2rep.nj":
                domainUrl = "https://testhoweb2rep.njtechdesk.com";
                break;
            case "testhoweb3rep.nj":
                domainUrl = "https://testhoweb3rep.njtechdesk.com";
                break;
            case "testhoweb4rep.nj":
                domainUrl = "https://testhoweb4rep.njtechdesk.com";
                break;
            case "devhoweb1.nj":
                domainUrl = "https://devhoweb1.njtechdesk.com";
                break;
            case "devhoweb2.nj":
                domainUrl = "https://devhoweb2.njtechdesk.com";
                break;
            case "devhoweb3.nj":
                domainUrl = "https://devhoweb3.njtechdesk.com";
                break;
            case "devhoweb4.nj":
                domainUrl = "https://devhoweb4.njtechdesk.com";
                break;
            default:
                domainUrl = "http://" + serverName + ":8090";
                break;
        }
        return domainUrl;
    }
}