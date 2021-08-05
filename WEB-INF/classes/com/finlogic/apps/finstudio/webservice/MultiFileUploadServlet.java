/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.apps.finstudio.webservice;

import com.finlogic.util.Logger;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author Sonam Patel
 */
public class MultiFileUploadServlet extends HttpServlet
{

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        FileItemFactory factory;
        factory = new DiskFileItemFactory();
        ServletFileUpload upload;
        upload = new ServletFileUpload(factory);

        try
        {
            List items;
            items = upload.parseRequest(request);
            Iterator iterator = items.iterator();

            String srno = "";
            while (iterator.hasNext())
            {
                FileItem item;
                item = (FileItem) iterator.next();

                if (item.isFormField() && "txtSrno".equals(item.getFieldName()))
                {
                    srno = item.getString();
                }
            }

            iterator = items.iterator();
            while (iterator.hasNext())
            {
                FileItem item;
                item = (FileItem) iterator.next();

                if (!item.isFormField() && item.getName().trim().length() > 0)
                {
                    String fileName;
                    fileName = item.getName();

                    String root;
                    root = getServletContext().getRealPath("/");
                    File path;
                    path = new File(root + "/upload/ws/" + srno);
                    if (!path.exists())
                    {
                        path.mkdirs();
                    }
                    File uploadedFile;
                    uploadedFile = new File(path + "/" + fileName);
                    item.write(uploadedFile);
                }
                else
                {
                    request.setAttribute(item.getFieldName(), item.getString());
                }
            }
            request.getRequestDispatcher("webservicegen.fin?cmdAction=implWS").forward(request, response);
        }
        catch (FileUploadException e)
        {
            Logger.ErrorLogger(e);
        }
        catch (Exception e)
        {
            Logger.ErrorLogger(e);
        }
    }
}