/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.findatareqexecutor;

import com.finlogic.apps.finstudio.findatareqexecutor.FinDataReqExecutorFormBean;
import com.finlogic.util.Logger;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Sonam Patel
 */
public class FileService
{

    public boolean copyFile(final String fileName)
    {
        File srcFile = new File(finpack.FinPack.getProperty("filebox_path") + fileName);
        File destFile = new File(FinDataReqExecutorFormBean.getFILEUPLOAD_PATH() + srcFile.getName());
        return srcFile.renameTo(destFile);
    }

    public String getFileQuery(final String fileName) throws IOException
    {
        File file = new File(FinDataReqExecutorFormBean.getFILEUPLOAD_PATH() + fileName);
        //Use character stream
        FileReader in = null;
        BufferedReader br = null;
        try
        {
            in = new FileReader(file);
            br = new BufferedReader(in);
            //Read a Line
            StringBuilder sbuild;
            sbuild = new StringBuilder();
            String s;
            while ((s = br.readLine()) != null)
            {
                sbuild.append(s.trim()).append("\n");
            }
            return sbuild.toString();
        }
        finally
        {
            //Close streams
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
                if (br != null)
                {
                    br.close();
                }
            }
            catch (Exception e)
            {
                Logger.ErrorLogger(e);
            }
        }
    }
}
