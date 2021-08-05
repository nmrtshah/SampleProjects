/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author Sonam Patel
 */
public class DirectoryService
{

    public void deleteFolder(final String path) throws IOException
    {
        File folder;
        folder = new File(path);
        //make sure directory exists
        if (folder.exists())
        {
            delete(folder);
        }
    }

    public void delete(final File file) throws IOException
    {
        if (file.isDirectory())
        {
            //directory is empty, then delete it
            if (file.list().length == 0)
            {
                file.delete();
            }
            else
            {
                //list all the directory contents
                String files[];
                files = file.list();
                for (String temp : files)
                {
                    //recursive delete
                    delete(new File(file, temp));
                }
                //check the directory again, if empty then delete it
                if (file.list().length == 0)
                {
                    file.delete();
                }
            }
        }
        else
        {
            //if file, then delete it
            file.delete();
        }
    }
}
