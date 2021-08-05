/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.mobappsgeneration;

import com.finlogic.apps.finstudio.mobappsgeneration.MobAppsFormBean;
import com.finlogic.util.Logger;
import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 *
 * @author njuser
 */
public class DirectoryService
{

    public void copyTemplate(final File src, File dest, final String packge) throws IOException
    {

        if (!src.getAbsolutePath().contains("/.svn"))
        {
            if (src.isDirectory())
            {
                if (src.toString().startsWith(MobAppsFormBean.getTEMPLATE() + "/Android/src/com"))
                {
                    //change folder name accoring to package input for Andriod
                    String newPackge;
                    newPackge = packge.replaceAll("\\.", "/");
                    StringBuilder newDest;
                    newDest = new StringBuilder();
                    newDest.append(dest.getAbsolutePath());
                    newDest.delete(newDest.length() - 3, newDest.length());
                    dest = new File(newDest + newPackge);
                    dest.mkdirs();
                }
                //if directory not exists, create it
                if (!dest.exists())
                {
                    dest.mkdir();
                }
                //list all the directory contents
                String files[];
                files = src.list();
                for (String file : files)
                {
                    //construct the src and dest file structure
                    File srcFile;
                    srcFile = new File(src, file);
                    File destFile;
                    destFile = new File(dest, file);
                    //recursive copy
                    copyTemplate(srcFile, destFile, packge);
                }
            }
            else
            {
                //if file, then copy it
                //Use bytes stream to support all file types
                InputStream in = null;
                OutputStream out = null;
                try
                {
                    in = new FileInputStream(src);
                    out = new FileOutputStream(dest);
                    byte[] buffer;
                    buffer = new byte[1024];
                    int length;

                    //copy the file content in bytes
                    while ((length = in.read(buffer)) > 0)
                    {
                        out.write(buffer, 0, length);
                    }
                }
                finally
                {
                    try
                    {
                        if (in != null)
                        {
                            in.close();
                        }
                    }
                    catch (Exception e)
                    {
                        Logger.ErrorLogger(e);
                    }
                    try
                    {
                        if (out != null)
                        {
                            out.close();
                        }
                    }
                    catch (Exception e)
                    {
                        Logger.ErrorLogger(e);
                    }
                }
            }
        }
    }

    public void mergeFolder(final File src, final File dest) throws IOException
    {
        if (src.isDirectory())
        {
            //if directory not exists, create it
            if (!dest.exists())
            {
                dest.mkdir();
            }
            //list all the directory contents
            String files[];
            files = src.list();
            for (String file : files)
            {
                //construct the src and dest file structure
                File srcFile;
                srcFile = new File(src, file);
                File destFile;
                destFile = new File(dest, file);
                //recursive copy
                mergeFolder(srcFile, destFile);
            }
        }
        else
        {
            //if file, then copy it
            //Use bytes stream to support all file types
            InputStream in = null;
            OutputStream out = null;
            try
            {
                in = new FileInputStream(src);
                out = new FileOutputStream(dest);
                byte[] buffer;
                buffer = new byte[1024];
                int length;

                //copy the file content in bytes
                while ((length = in.read(buffer)) > 0)
                {
                    out.write(buffer, 0, length);
                }
            }
            finally
            {
                try
                {
                    if (in != null)
                    {
                        in.close();
                    }
                }
                catch (Exception e)
                {
                    Logger.ErrorLogger(e);
                }
                try
                {
                    if (out != null)
                    {
                        out.close();
                    }
                }
                catch (Exception e)
                {
                    Logger.ErrorLogger(e);
                }
            }
        }
    }

    public void extractFolder(final String zipFile, final String destination) throws IOException
    {
        int BUFFER = 1024;
        File file;
        file = new File(zipFile);
        ZipFile zip;
        zip = new ZipFile(file);

        Enumeration zipFileEntries;
        zipFileEntries = zip.entries();
        // Process each entry
        while (zipFileEntries.hasMoreElements())
        {
            // grab a zip file entry
            ZipEntry entry;
            entry = (ZipEntry) zipFileEntries.nextElement();
            String currentEntry;
            currentEntry = entry.getName();
            if (currentEntry.startsWith("finhtml/"))
            {
                File destFile;
                destFile = new File(destination, currentEntry);
                File destinationParent;
                destinationParent = destFile.getParentFile();

                // create the parent directory structure if needed
                destinationParent.mkdirs();

                if (!entry.isDirectory())
                {
                    BufferedInputStream is = null;
                    BufferedOutputStream dest = null;
                    try
                    {
                        is = new BufferedInputStream(zip.getInputStream(entry));
                        int currentByte;
                        // establish buffer for writing file
                        byte data[];
                        data = new byte[BUFFER];

                        // write the current file to disk
                        FileOutputStream fos;
                        fos = new FileOutputStream(destFile);
                        dest = new BufferedOutputStream(fos, BUFFER);

                        // read and write until last byte is encountered
                        while ((currentByte = is.read(data, 0, BUFFER)) != -1)
                        {
                            dest.write(data, 0, currentByte);
                        }
                        dest.flush();
                    }
                    finally
                    {
                        try
                        {
                            if (dest != null)
                            {
                                dest.close();
                            }
                        }
                        catch (Exception e)
                        {
                            Logger.ErrorLogger(e);
                        }
                        try
                        {
                            if (is != null)
                            {
                                is.close();
                            }
                        }
                        catch (Exception e)
                        {
                            Logger.ErrorLogger(e);
                        }
                    }
                }
            }
        }
    }
}
