/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.mobappsgeneration;

import com.finlogic.util.Logger;
import java.io.*;

/**
 *
 * @author njuser
 */
public class FileService
{

    public String searchFile(final File folder, String fileName)
    {
        if (folder.isDirectory())
        {
            //list all the directory contents
            String files[];
            files = folder.list();
            for (String file : files)
            {
                //construct the src file structure
                File srcFile;
                srcFile = new File(folder, file);
                //recursive search
                fileName = searchFile(srcFile, fileName);
            }
        }
        else
        {
            //if file, then match it with fileName and return path
            if (folder.getAbsolutePath().endsWith(fileName))
            {
                return folder.getAbsolutePath();
            }
        }
        return fileName;
    }

    public void findAndReplace(final String folderPath, final String fileName, final String searchText, final String replaceText) throws IOException
    {
        String filePath;
        File folder;
        folder = new File(folderPath);
        filePath = searchFile(folder, fileName);
        File file;
        file = new File(filePath);

        FileReader fstream = null;
        BufferedReader br = null;

        FileWriter fwstream = null;
        BufferedWriter bw = null;

        String strLine = "";
        StringBuffer strbuff;
        strbuff = new StringBuffer("");

        if (file.exists())
        {
            try
            {
                //read file
                fstream = new FileReader(file);
                br = new BufferedReader(fstream);

                while (true)
                {
                    strLine = br.readLine();
                    if (strLine == null)
                    {
                        break;
                    }
                    strbuff.append(strLine).append("\r\n");
                }

                //relpace text
                String finalstr = strbuff.toString();
                finalstr = finalstr.replace(searchText, replaceText);

                //write file
                fwstream = new FileWriter(file);
                bw = new BufferedWriter(fwstream);

                bw.write(finalstr, 0, finalstr.length());
                bw.flush();
            }
            finally
            {
                try
                {
                    if (br != null)
                    {
                        br.close();
                    }
                }
                catch (Exception e)
                {
                    Logger.DataLogger("Error " + Logger.ErrorLogger(e));
                }
                try
                {
                    if (fstream != null)
                    {
                        fstream.close();
                    }
                }
                catch (Exception e)
                {
                    Logger.DataLogger("Error " + Logger.ErrorLogger(e));
                }

                try
                {
                    if (bw != null)
                    {
                        bw.close();
                    }
                }
                catch (Exception e)
                {
                    Logger.DataLogger("Error " + Logger.ErrorLogger(e));
                }
                try
                {
                    if (fwstream != null)
                    {
                        fwstream.close();
                    }
                }
                catch (Exception e)
                {
                    Logger.DataLogger("Error " + Logger.ErrorLogger(e));
                }
            }
        }
    }
}
