/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util;

import com.asprise.util.jtwain.web.FileUploader;
import com.finlogic.business.finstudio.finfiletransfer.FileTransIntermediateDataManager;
import java.io.*;
import java.net.*;
import java.sql.SQLException;

/**
 *
 * @author njuser
 */
public class FileTransfer
{
//    public static void fileTrans(String fileList)

    public static int fileTrans(String getId, String src_dest) throws FileNotFoundException, UnsupportedEncodingException, ClassNotFoundException, SQLException, Exception
    {
        String server[] = new String[2];
        server = src_dest.split("-");
        String address = "http://" + server[0] + "/finstudio/finFileTransfer/download.jsp?id=" + getId;

        FileTransIntermediateDataManager dataManager = new FileTransIntermediateDataManager();
        String finstudioPath = dataManager.getTomcatByProject("finstudio");

        String localFileName = finstudioPath + "webapps/finstudio/download/Download-File-" + getId + ".zip";
        URLConnection conn = null;
        InputStream in = null;
        OutputStream outputStream = null;
        try
        {
            // Get the URL
            URL url = new URL(address);
            // Open an output stream to the destination file on our local filesystem
            outputStream = new FileOutputStream(localFileName);
            conn = url.openConnection();
            in = conn.getInputStream();

            // Get the data
            byte[] buffer = new byte[1024];
            int numRead;
            while ((numRead = in.read(buffer)) != -1)
            {
                //out.println(in.read());
                outputStream.write(buffer, 0, numRead);
            }
            //out.println(buffer);
            // Done! Just clean up and get out

            File f = new File(localFileName);
            Logger.DataLogger("File: " + f.exists());
            FileUploader fileUploader = new FileUploader();
//            String uploadUrl="http://localhost:8080/finstudio/finfiletransfer.do";
            String uploadUrl = "http://" + server[1] + "/finstudio/finfiletransfer.do";
            Logger.DataLogger("uploadUrl: " + uploadUrl);
            fileUploader.upload(uploadUrl, null, "Download-File-" + getId + ".zip", f, null);
//            fileUploader.upload("http://localhost:8080/finstudio/upload.htm", null, "Download-File.zip", f, null);
            return 0;
        }
        catch (Exception e)
        {
            Logger.DataLogger("Error : " + e.getMessage());
            return 1;
        }
        finally
        {
            try
            {
                if (in != null)
                {
                    in.close();
                }
                if (outputStream != null)
                {
                    outputStream.close();
                }
            }
            catch (IOException ioe)
            {
            }
        }
    }
}
