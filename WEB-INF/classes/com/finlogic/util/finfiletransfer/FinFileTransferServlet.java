/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.finfiletransfer;

import com.finlogic.business.finstudio.finfiletransfer.FileTransIntermediateDataManager;
import com.finlogic.util.FileBox;
import com.finlogic.util.FolderZipper;
import com.finlogic.util.Logger;
import com.finlogic.util.properties.HardCodeProperty;
import com.oreilly.servlet.MultipartRequest;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.lingala.zip4j.core.ZipFile;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author njuser
 */
public class FinFileTransferServlet extends HttpServlet {

    private String varJava = "java";
    private String varPhp = "php";
    private String phpPath = "/var/www/html/";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ClassNotFoundException, SQLException {
        PrintWriter out = null;
        try {
            String param = request.getParameter("action");
            if ("filebox".equalsIgnoreCase(param)
                    || "fileboxmenu".equalsIgnoreCase(param)
                    || "fileboxsave".equalsIgnoreCase(param)) {
                FileBox box = new FileBox();
                box.upload(request, response);
            } else {

                String autheticHosts = new HardCodeProperty().getProperty("fin_file_verify_domain_name");
                InetAddress addr = InetAddress.getByName(request.getRemoteAddr());
                String remoteHost = addr.getHostName();
                try {
                    Logger.DataLogger("Req Remote Host : " + remoteHost);
                } catch (Exception e) {
                    System.err.println(e);
                }
                boolean authentic = false;

                for (String host : autheticHosts.split(",")) {

                    if (host.equalsIgnoreCase(remoteHost)) {
                        authentic = true;
                        break;
                    }
                }
                
                try {
                    Logger.DataLogger("authentic : " + authentic);                    
                } catch (Exception e) {
                    System.err.println(e);
                }

                if (authentic) {
                    String id = request.getParameter("id");
                    String lang = request.getParameter("lang");
                    try {
                        Logger.DataLogger("id : " + id);
                        Logger.DataLogger("land : " + lang);
                        Logger.DataLogger("param : " + param);
                    } catch (Exception e) {
                        System.err.println(e);
                    }
                    if (param.equalsIgnoreCase("downloadSrc")) {
                        downloadSrc(Long.parseLong(id), response, lang);
                    } else {
                        response.setContentType("text/html;charset=UTF-8");
                        out = response.getWriter();

                        if (param.equalsIgnoreCase("srcChecking")) {
                            srcChecking(Long.parseLong(id), lang);
                        } else if (param.equalsIgnoreCase("destChecking")) {
                            //Logger.DataLogger("destChecking : " + id);
                            destChecking(Long.parseLong(id), lang);
                        } else if (param.equalsIgnoreCase("srcCheckingConfirm")) {
                            out.println(srcCheckingConfirm(Long.parseLong(id), lang));
                        } else if (param.equalsIgnoreCase("uploadDest")) {
                            out.println(uploadDest(request, lang));
                        }
                    }
                } else {
                    out = response.getWriter();
                    out.println("Machine Authentication Failed...!!!!");
                }
            }
        } catch (Exception e) {
            try {
                Logger.ErrorLogger(e);
            } catch (Exception ex) {
                System.err.println(ex);
            }
            throw new IOException(e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    private void srcChecking(Long id, String lang) throws ClassNotFoundException, SQLException {        
        String srcExist = null;
        String fileType = null;
        String fileNm = null;
        String tomcatPath = "";
        String fullPath = "";
        File f = null;
        Map m;
        FileTransIntermediateDataManager dataManager = new FileTransIntermediateDataManager();
        
        try {
            Logger.DataLogger("srcChecking : ");
        } catch (Exception e) {
            System.err.println(e);
        }
        
        try {
            try {
                Logger.DataLogger("srcChecking 1");
            } catch (Exception e) {
                System.err.println(e);
            }
            List<Map<String, String>> list = dataManager.getData(id);
            try {
                Logger.DataLogger("srcChecking 2");
            } catch (Exception e) {
                System.err.println(e);
            }

            //Logger.DataLogger("Source checking list" + list);
            if (list.size() > 0) {
                m = list.get(0);
                if (lang.equalsIgnoreCase(varJava)) {
                    tomcatPath = dataManager.getTomcatByProject(m.get("FILE_NAME").toString().split("/")[0]) + "webapps/";
                    try {
                        Logger.DataLogger("Source checking tomcat path " + tomcatPath);
                    } catch (Exception e) {
                        Logger.ErrorLogger(e);
                        System.err.println(e);
                    }
                } else if (lang.equalsIgnoreCase(varPhp)) {
                    tomcatPath = phpPath;
                }

                for (int i = 0; i < list.size(); i++) {
                    m = list.get(i);
                    fileNm = m.get("FILE_NAME").toString();
                    fullPath = tomcatPath + fileNm;
                    f = new File(fullPath);
                    if (f.exists() && f.isDirectory()) {
                        srcExist = "Exist";
                        fileType = "dir";
                    } else if (f.exists() && f.isFile()) {
                        srcExist = "Exist";
                        fileType = "file";
                    } else {
                        srcExist = "NotExist";
                        fileType = "NotDefined";
                    }
                    dataManager.updateData(id, fileNm, srcExist, null, fileType);
                }

            }
            Logger.DataLogger("Source Done");
        } catch (Exception e) {
            Logger.ErrorLogger(e);
            throw new SQLException(e);
        } finally {
            dataManager.connClose();
        }
    }

    private void destChecking(Long id, String lang) throws ClassNotFoundException, SQLException {
        String destExist = null;
        String fileNm = null;
        String tomcatPath = "";
        String tomcatPathWeb = "";
        File f = null;
        StringBuilder nextFile = new StringBuilder();
        Set oSet = new TreeSet<String>();
        Map m;
        FileTransIntermediateDataManager dataManager = new FileTransIntermediateDataManager();
        try {
            List<Map<String, String>> list = dataManager.getData(id);
            //Logger.DataLogger("dest checking list" + list);
            if (list.size() > 0) {
                m = list.get(0);
                if (lang.equalsIgnoreCase(varJava)) {
                    tomcatPathWeb = dataManager.getTomcatByProject(m.get("FILE_NAME").toString().split("/")[0]);
                    tomcatPath = tomcatPathWeb + "webapps/";
                    try {
                        Logger.DataLogger("dest tomcat path" + tomcatPath);
                    } catch (Exception e) {
                        System.err.println(e);
                    }
                } else if (lang.equalsIgnoreCase(varPhp)) {
                    tomcatPathWeb = phpPath;
                    tomcatPath = phpPath;
                }

                for (int i = 0; i < list.size(); i++) {
                    m = list.get(i);
                    fileNm = m.get("FILE_NAME").toString();
                    fileNm = tomcatPath + fileNm;
                    oSet.add(fileNm);
                    nextFile.replace(0, nextFile.length(), fileNm);
                    f = new File(nextFile.toString());

                    while (!f.exists()) {
                        oSet.add(nextFile.toString());
                        nextFile.replace(0, nextFile.length(), nextFile.substring(0, nextFile.lastIndexOf("/")));
                        f = new File(nextFile.toString());
                    }
                }

                Iterator iterator = oSet.iterator();
                dataManager.deleteData(id);

                while (iterator.hasNext()) {
                    fileNm = iterator.next().toString();
                    File f1 = new File(fileNm);
                    if (f1.exists()) {
                        destExist = "Exist";
                    } else {
                        destExist = "NotExist";
                    }
                    fileNm = fileNm.replace(tomcatPath, "");
                    dataManager.insertData(id, fileNm, null, destExist, null);
                }
            }
            List<Map<String, String>> listDlt = dataManager.getDataDlt(id);
            String localFile;
            String prjNm;
            if (listDlt.size() > 0) {
                m = listDlt.get(0);
                prjNm = m.get("FILE_NAME").toString();
                prjNm = (prjNm.substring(prjNm.indexOf(':') + 1)).split("/")[0];

                if (lang.equalsIgnoreCase(varJava)) {
                    tomcatPath = dataManager.getTomcatByProject(prjNm) + "webapps/";
                } else if (lang.equalsIgnoreCase(varPhp)) {
                    tomcatPath = phpPath;
                }

                for (int i = 0; i < listDlt.size(); i++) {
                    m = listDlt.get(i);
                    fileNm = m.get("FILE_NAME").toString();
                    localFile = fileNm.substring(fileNm.indexOf(':') + 1);
                    localFile = tomcatPath + localFile;
                    f = new File(localFile);
                    if (f.exists()) {
                        if (f.isFile()) {
                            dataManager.updateData(id, fileNm, null, "Exist", "file");
                        } else if (f.isDirectory()) {
                            dataManager.updateData(id, fileNm, null, "Exist", "dir");
                        }
                    } else {
                        dataManager.updateData(id, fileNm, null, "NotExist", "NotDefined");
                    }
                }
            }
            Logger.DataLogger("dest Done");
        } catch (Exception e) {
            Logger.ErrorLogger(e);
            throw new SQLException(e);
        } finally {
            dataManager.connClose();
        }
    }

    private String srcCheckingConfirm(Long id, String lang) throws ClassNotFoundException, SQLException {
        String srcExist = "Y";
        String fileNm;
        String fullPath = "";
        String tomcatPath = "";
        FileTransIntermediateDataManager dataManager = new FileTransIntermediateDataManager();
        Map m;

        try {
            List<Map<String, String>> list = dataManager.getData(id);
            if (list.size() > 0) {
                m = list.get(0);
                if (lang.equalsIgnoreCase(varJava)) {
                    tomcatPath = dataManager.getTomcatByProject(m.get("FILE_NAME").toString().split("/")[0]) + "webapps/";
                } else if (lang.equalsIgnoreCase(varPhp)) {
                    tomcatPath = phpPath;
                }
            }
            for (int i = 0; i < list.size(); i++) {
                m = list.get(i);
                fileNm = m.get("FILE_NAME").toString();
                fullPath = tomcatPath + fileNm;
                //Logger.DataLogger("fullpath : " + fullPath);
                File f = new File(fullPath);
                if (f.exists()) {
                    srcExist = "Y";
                } else {
                    srcExist = "N";
                }
                if (srcExist.equals("N")) {
                    break;
                }
            }
            return srcExist;
        } catch (Exception e) {
            throw new SQLException(e);
        } finally {
            dataManager.connClose();
        }
    }

    private void downloadSrc(Long id, HttpServletResponse response, String lang) throws ClassNotFoundException, IOException {
        try {
            Logger.DataLogger("downloadSrc1");
            FileTransIntermediateDataManager dataManager = new FileTransIntermediateDataManager();
            List<Map<String, String>> list = dataManager.getData(id);
            Map m;
            String fileNm;
            String outFileName;
            String tomcatPath = "";
            Logger.DataLogger("downloadSrc2");
            if (list.size() > 0) {
                m = list.get(0);
                if (lang.equalsIgnoreCase(varJava)) {
                    tomcatPath = dataManager.getTomcatByProject(m.get("FILE_NAME").toString().split("/")[0]) + "webapps/";
                } else if (lang.equalsIgnoreCase(varPhp)) {
                    tomcatPath = phpPath;
                }

            }
            Logger.DataLogger("downloadSrc3");
            String finstudioPath = dataManager.getTomcatByProject("finstudio");
            String strOutFolder = finstudioPath + "webapps/finstudio/download/Download-File-" + id;
            Logger.DataLogger("finstudioPath : " + finstudioPath);
            File outFolder = new File(strOutFolder);
            outFolder.mkdir();
            Logger.DataLogger("List : " + list.size());
            for (int i = 0; i < list.size(); i++) {
                m = list.get(i);
                fileNm = tomcatPath + m.get("FILE_NAME").toString();

                String f1 = fileNm.replace("/", "_-_");

                outFileName = finstudioPath + "webapps/finstudio/download/Download-File-" + id + "/" + f1;
                OutputStream outputStream = null;
                DataInputStream in = null;
                Logger.DataLogger("outfilename : "+outFileName);
                try {
                    File f = new File(fileNm);
                    if (f.isFile()) {
                        int length = 0;
                        Logger.DataLogger("adding file : "+f.getAbsolutePath());
                        if (f.exists()) {
                            byte[] bbuf = new byte[(int) f.length()];
                            in = new DataInputStream(new FileInputStream(fileNm));
                            outputStream = new FileOutputStream(outFileName);
                            if (f.length() > 0) {
                                while ((in != null) && ((length = in.read(bbuf)) != -1)) {
                                    outputStream.write(bbuf, 0, length);
                                }
                                outputStream.flush();
                            }
                        }
                    }
                } catch(Exception re)    {
                    Logger.ErrorLogger(re);
                } finally {
                    try {
                        if (outputStream != null) {
                            outputStream.close();
                        }
                    } catch (Exception e) {
                    }
                    try {
                        if (in != null) {
                            in.close();
                        }
                    } catch (Exception e) {
                    }
                }
            }

            FolderZipper.zipFolder(finstudioPath
                    + "webapps/finstudio/download/Download-File-" + id, finstudioPath + "webapps/finstudio/download/Download-File-" + id + ".zip");
            String fileName;
            fileName = finstudioPath + "webapps/finstudio/download/Download-File-" + id + ".zip";
            fileNm = (fileName.substring(fileName.lastIndexOf('/') + 1)).trim();
            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;
            ServletOutputStream outputStr = response.getOutputStream();

            try {
                File f = new File(fileName);
                //int length = 0;
                ServletContext context = getServletConfig().getServletContext();
                String mimeType = context.getMimeType(fileName);

                if (f.exists()) {
                    response.setContentType((mimeType != null) ? mimeType : "application/zip");
                    response.setContentLength((int) f.length());
                    response.setHeader("Content-Disposition", "attachment; filename=" + fileNm);
                    bis = new BufferedInputStream(new FileInputStream(f));
                    bos = new BufferedOutputStream(outputStr);
                    byte[] buff = new byte[2048];
                    int bytesRead;
                    while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                        bos.write(buff, 0, bytesRead);
                    }
                }
            } finally {
                try {
                    if (bis != null) {
                        bis.close();
                    }
                } catch (Exception e) {
                }
                try {
                    if (bos != null) {
                        bos.close();
                    }
                } catch (Exception e) {
                }
                outputStr.close();
            }
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    private String uploadDest(HttpServletRequest request, String lang) throws IOException {
        MultipartRequest multi = null;
        ZipFile zipFile = null;
        String[] filesList = null;
        String filename = "";
        String uploadPath = null;
        String status = null;
        FileUtils fileUtils = new FileUtils();

        try {
            FileTransIntermediateDataManager dataManager = new FileTransIntermediateDataManager();
            uploadPath = dataManager.getTomcatByProject("finstudio") + "webapps/finstudio/download";
            //Logger.DataLogger("here1 ; " + uploadPath);
            // to upload files
            multi = new MultipartRequest(request, uploadPath, 209715200, "ISO-8859-1");

            Enumeration files = multi.getFileNames();
            while (files.hasMoreElements()) {
                String name = (String) files.nextElement();
                filename = multi.getFilesystemName(name);
                //Logger.DataLogger("here2 ; " + filename);
            }
            long id = 0;
            if (filename != null) {
                id = Long.parseLong(filename.substring(filename.lastIndexOf('-') + 1, filename.lastIndexOf('.')));
                //Logger.DataLogger("id= " + id);
            }
            List<Map<String, String>> list = dataManager.getData(id);
            List<Map<String, String>> listUpld = dataManager.getDataUpld(id);
            Map m;
            String tomcatPath = "";
            File f1;
            String fileNm;
            if (list.size() > 0 || listUpld.size() > 0) {
                if (list.size() > 0) {
                    m = list.get(0);
                    if (lang.equalsIgnoreCase(varJava)) {
                        tomcatPath = dataManager.getTomcatByProject(m.get("FILE_NAME").toString().split("/")[0]) + "webapps/";
                    } else if (lang.equalsIgnoreCase(varPhp)) {
                        tomcatPath = phpPath;
                    }

                    //Logger.DataLogger("tomcatPath= " + tomcatPath);
                }
                if (listUpld.size() > 0) {
                    m = listUpld.get(0);
                    String prjNm = m.get("FILE_NAME").toString();
                    prjNm = (prjNm.substring(prjNm.indexOf(':') + 1)).split("/")[0];

                    if (lang.equalsIgnoreCase(varJava)) {
                        tomcatPath = dataManager.getTomcatByProject(prjNm) + "webapps/";
                    } else if (lang.equalsIgnoreCase(varPhp)) {
                        tomcatPath = phpPath;
                    }

                    //Logger.DataLogger("tomcatPath= " + tomcatPath);
                    String localFile;
                    File f = null;
                    for (int i = 0; i < listUpld.size(); i++) {
                        m = listUpld.get(i);
                        fileNm = m.get("FILE_NAME").toString();
                        localFile = fileNm.substring(fileNm.indexOf(':') + 1);
                        localFile = tomcatPath + localFile;
                        localFile = localFile.substring(0, localFile.lastIndexOf('/'));
                        f = new File(localFile);
                        //Logger.DataLogger("f : " + f.exists());
                        if (!f.exists()) {
                            f.mkdirs();
                        }
                    }
                }

                String fileNmTrans = "";
                for (int i = 0; i < list.size(); i++) {
                    m = (Map) list.get(i);
                    fileNm = m.get("FILE_NAME").toString();
                    fileNmTrans = tomcatPath + fileNm;
                    //Logger.DataLogger("fileNm= " + fileNmTrans);
                    f1 = new File(fileNmTrans);
                    if (m.get("FILE_TYPE").toString().equals("dir")) {
                        f1.mkdirs();
                        dataManager.updateStatus(id, fileNm);
                    }
                }
                String folderNm = filename.substring(0, filename.lastIndexOf('.'));

                // to extract files
                zipFile = new ZipFile(uploadPath + "/" + filename);
                Logger.DataLogger("Valid :" + zipFile.isValidZipFile());
                if (zipFile.isValidZipFile()) {
                    zipFile.extractAll(uploadPath + "/Download-File");
                    File extractedDir = new File(uploadPath + "/Download-File/" + folderNm);

                    // Moving files at correct place
                    if (extractedDir.isDirectory()) {
                        String filePathUs = "";
                        filesList = extractedDir.list();
                        for (int i = 0; i < filesList.length; i++) {
                            String dFilePath = filesList[i].replace("_-_", "/");

                            //Logger.DataLogger("dFilePath : " + dFilePath);
                            if (lang.equalsIgnoreCase(varJava)) {
                                dFilePath = dFilePath.substring(dFilePath.indexOf("webapps") + 8);
                            } else if (lang.equalsIgnoreCase(varPhp)) {
                                dFilePath = dFilePath.substring(dFilePath.indexOf("html") + 5);
                            }

                            filePathUs = dFilePath;
                            filePathUs = filePathUs.substring(filePathUs.indexOf('/') + 1);
                            dFilePath = tomcatPath + dFilePath;
                            String bFilePath = dFilePath + "_backup_" + id;
                            String sFilePath = uploadPath + "/Download-File/" + folderNm + "/" + filesList[i];

                            //Logger.DataLogger("filePathUs : " + filePathUs);
                            File sFile = new File(sFilePath);
                            File dFile = new File(dFilePath);
                            File bFile = new File(bFilePath);

                            //Logger.DataLogger("sFile : " + sFile.getPath());
                            //Logger.DataLogger("dFile : " + dFile.getPath());
                            //Logger.DataLogger("bFile : " + bFile.getPath());
                            if (dFile.isFile() && dFile.exists()) {
                                try {
                                    //fileUtils.moveFile(dFile, bFile);
                                    Files.move(Paths.get(dFilePath), Paths.get(bFilePath),StandardCopyOption.REPLACE_EXISTING);
                                } catch (Exception e) {
                                    try {
                                        Logger.ErrorLogger(e);
                                    } catch (Exception ee) {
                                        System.err.println(ee);
                                    }
                                }
                            }
                            //fileUtils.moveFile(sFile, dFile);
                            Files.move(Paths.get(sFilePath), Paths.get(dFilePath), StandardCopyOption.REPLACE_EXISTING);
                            //Logger.DataLogger("here : " + sFile.exists());
                            if (!sFile.exists()) {
                                dataManager.updateStatus(id, filePathUs);
                            }
                        }
                    }
                }
            }
            File f = null;
            File fRnm = null;
            String prjNm;
            List<Map<String, String>> listDtl = dataManager.getDataDlt(id);

            if (listDtl.size() > 0) {
                m = listDtl.get(0);
                prjNm = m.get("FILE_NAME").toString();
                prjNm = (prjNm.substring(prjNm.indexOf(':') + 1)).split("/")[0];

                if (lang.equalsIgnoreCase(varJava)) {
                    tomcatPath = dataManager.getTomcatByProject(prjNm) + "webapps/";
                } else if (lang.equalsIgnoreCase(varPhp)) {
                    tomcatPath = phpPath;
                }

                String localFile;
                for (int i = 0; i < listDtl.size(); i++) {
                    m = listDtl.get(i);
                    fileNm = m.get("FILE_NAME").toString();
                    localFile = fileNm.substring(fileNm.indexOf(':') + 1);
                    localFile = tomcatPath + localFile;
                    f = new File(localFile);
                    //Logger.DataLogger("" + f.exists());
                    //Logger.DataLogger("" + f.exists());
                    if (f.exists()) {
                        fRnm = new File(localFile + "_deleted_backup_" + id);
                        //FileUtils.moveFile(f, fRnm);
                        Files.move(Paths.get(localFile), Paths.get(localFile + "_deleted_backup_" + id), StandardCopyOption.REPLACE_EXISTING);
                    }
                }
            }

        } catch (Exception ex) {
            try {
                Logger.ErrorLogger(ex);
            } catch (Exception e) {
                System.err.println(e);
            }
            throw new IOException(ex);
        }
        return status;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);

        } catch (ServletException | IOException | ClassNotFoundException | SQLException ex) {
            try {
                Logger.ErrorLogger(ex);
            } catch (Exception e) {
                System.err.println(e);
            }
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);

        } catch (ServletException | IOException | ClassNotFoundException | SQLException ex) {
            try {
                Logger.ErrorLogger(ex);
            } catch (Exception e) {
                System.err.println(e);
            }
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}