/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.finfiletransfer;

import com.asprise.util.jtwain.web.FileUploader;
import com.finlogic.business.finstudio.finfiletransfer.FileTransIntermediateDataManager;
import com.finlogic.util.FolderZipper;
import com.finlogic.util.Logger;
import java.io.*;
import java.net.*;
import java.sql.SQLException;
import java.util.List;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

/**
 *
 * @author njuser
 */
public class FileTransfer {

    public static void fileTrans(String genId, String srcdest, String lang) throws FileNotFoundException, UnsupportedEncodingException, ClassNotFoundException, SQLException, IOException, ZipException, Exception {
        String server[] = new String[2];
        server = srcdest.split("-");
        String address;
        switch (server[0]) {
            case "devhoweb1.njtechdesk.com":
            case "devhoweb2.njtechdesk.com":
            case "devhoweb3.njtechdesk.com":
            case "devhoweb4.njtechdesk.com":
            case "testhoweb1.njtechdesk.com":
            case "testhoweb2.njtechdesk.com":
            case "testhoweb3.njtechdesk.com":
            case "testhoweb4.njtechdesk.com":
                address = "https://";
                break;
            default:
                address = "http://";
                break;
        }
        address += server[0] + "/finstudio/FinFileTransferServlet.do?action=downloadSrc&id=" + genId + "&lang=" + lang;

        FileTransIntermediateDataManager dataManager = new FileTransIntermediateDataManager();
        String finstudioPath = dataManager.getTomcatByProject("finstudio");
        dataManager.updateStatusAll(Long.parseLong(genId));
        String localFileName = finstudioPath + "webapps/finstudio/download/Download-File-" + genId + ".zip";
        List data = dataManager.getData(Long.parseLong(genId));
        URLConnection conn = null;
        InputStream in = null;
        OutputStream outputStream = null;
        try {
            Logger.DataLogger("data len : " + data.size());
            if (data.size() != 0 && dataManager.getDataForOnluFolder(Long.parseLong(genId)) != 0) {
                URL url = new URL(address);
                outputStream = new FileOutputStream(localFileName);
                conn = url.openConnection();
                in = conn.getInputStream();

                // Get the data
                byte[] buffer = new byte[1024];
                int numRead;
                while ((numRead = in.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, numRead);
                }
            }
            File f = new File(localFileName);
            File src, dest;
            String[] fileList;
            src = new File(finpack.FinPack.getProperty("filebox_path") + genId);
            String folderNm = localFileName.substring(0, localFileName.lastIndexOf('.')) + "/";
            Logger.DataLogger("Exist : " + f.exists());
            Logger.DataLogger("Len : " + f.length());
            if (f.length() == 0 || !f.exists()) {
                File folderCrt = new File(folderNm);
                folderCrt.mkdir();
            } else {
                String path = localFileName.substring(0, localFileName.lastIndexOf('/') + 1);
                ZipFile zipFile = new ZipFile(localFileName);
                zipFile.extractAll(path);
            }

            Logger.DataLogger("src.exists() : " + src.exists());
            if (src.exists()) {
                fileList = src.list();
                for (int i = 0; i < fileList.length; i++) {
                    src = new File(finpack.FinPack.getProperty("filebox_path") + genId + "/" + fileList[i]);
                    dest = new File(folderNm + fileList[i]);
                    if (src.exists()) {
                        src.renameTo(dest);
                    }
                }
            } else {
                if (data.size() == 0 || f.length() == 0) {
                    src = new File(folderNm + "Temp.txt");
                    src.createNewFile();
                }
            }

            FolderZipper.zipFolder(folderNm, localFileName);

            Logger.DataLogger("File: " + f.exists());
            FileUploader fileUploader = new FileUploader();
            switch (server[1]) {
                case "testhoweb1.njtechdesk.com":
                case "testhoweb2.njtechdesk.com":
                case "testhoweb3.njtechdesk.com":
                case "testhoweb4.njtechdesk.com":
                case "prodhoweb1.njtechdesk.com":
                case "prodhoweb2.njtechdesk.com":
                case "prodhoweb3.njtechdesk.com":
                case "prodhoweb4.njtechdesk.com":
                    address = "https://";
                    break;
                default:
                    address = "http://";
                    break;
            }
            address += server[1] + "/finstudio/FinFileTransferServlet.do?action=uploadDest&lang=" + lang;
            Logger.DataLogger("uploadUrl: " + address);

            fileUploader.upload(address, null, "Download-File-" + genId + ".zip", f, null);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException ioe) {
            }
        }
    }
}
