/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author njuser
 */
public class FileBox
{

    public void upload(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try
        {
            out.println("<html>");
            out.println("   <head>");
            out.println("       <title>File Box</title>");
            out.println("       <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />");
            out.println("       <script type=\"text/javascript\" src=\"" + finpack.FinPack.getProperty("finlib_path") + "/resource/finstudio-utility-functions.js\"></script>");
            out.println("   </head>");

            if ((request.getParameter("action")).equals("filebox"))
            {
                if (request.getParameter("encryptStr") == null)
                {
                    out.println("       <h4 align=\"center\" style=\"font-family:arial\">Invalid Access</h4>");
                }
                else
                {
                    out.println("   <body onload=\"formSubmit();\">");
                    out.println("       <form action=\"FinFileTransferServlet.do\" method=\"post\" name=\"upload\" id=\"upload\" >");
                    out.println("           <table align=\"center\" border=\"1\">");
                    out.println("               <tr>");
                    out.println("                   <td>");

                    String estr = request.getParameter("encryptStr");
                    String dstr = Utility.decodeData(estr);
                    dstr = dstr + "#" + request.getParameter("counter") + "#" + request.getParameter("ctrid");
                    estr = Utility.encodeData(dstr);

                    out.println("                       <input type=\"hidden\" readonly name=\"encryptStr\" value=\"" + estr + "\">");
                    out.println("                       <input type=\"hidden\" readonly name=\"action\" value=\"fileboxmenu\">");
                    out.println("                   </td>");
                    out.println("               </tr>");
                    out.println("           </table>");
                    out.println("       </form>");
                    out.println("<div align='center'>Loading...</div>");
                    out.println("   </body>");
                    out.println("   <script>");
                    out.println("       function formSubmit()");
                    out.println("       {");
                    out.println("           document.getElementById(\"upload\").submit(); ");
                    out.println("       }");
                    out.println("   </script>");
                }
            }
            else if ((request.getParameter("action")).equals("fileboxmenu"))
            {
                if (request.getParameter("encryptStr") == null)
                {
                    out.println("       <h4 align=\"center\" style=\"font-family:arial\">Invalid Access</h4>");
                }
                else
                {
                    String estr = request.getParameter("encryptStr");
                    String dstr = Utility.decodeData(estr);
                    String[] str = dstr.split("#");

                    out.println("   <body style=\"background:#EAEAEA\">");
                    out.println("   <table width=\"100%\" height=\"100%\" style=\"border:1px solid #9A9A9A\">");
                    out.println("   <tr><td>");
                    //out.println("       <h4 align=\"center\" style=\"font-family:arial;color:#670507\">File Box</h4>");                    
                    out.println("       <p align=\"center\" style=\"color: red;font-size: small\">Valid file types : " + str[1] + "</p>");
                    out.println("       <p align=\"center\" style=\"color: red;font-size: small\">Max. file size :  " + str[0] + " KB</p>");
                    int remainingFiles = Integer.parseInt(str[2]) - Integer.parseInt(str[6]);
                    if (Integer.parseInt(str[2]) > 1)
                    {
                        out.println("       <p align=\"center\" style=\"color: red;font-size: small\">Remaining Files :  " + remainingFiles + "</p>");
                    }
                    out.println("       <form action=\"FinFileTransferServlet.do?action=fileboxsave\" method=\"post\" name=\"uploadmenu\" id=\"uploadmenu\" enctype=\"multipart/form-data\">");
                    out.println("           <table id=\"maintable\" align=\"center\" border=\"1\">");
                    out.println("               <tr>");
                    out.println("                   <td>");
                    out.println("                       <input type=\"file\" name=\"finfileupload\" onkeypress=\"return false;\" id=\"finfileupload\">");
                    out.println("                   </td>");
                    out.println("               </tr>");
                    out.println("               <tr bgcolor=\"#cdcacd\">");
                    out.println("                   <td align=\"center\" colspan=\"2\">");
                    out.println("                       <input type=\"hidden\" readonly name=\"encryptStr\" value=\"" + request.getParameter("encryptStr") + "\">");
                    out.println("                       <input type=\"submit\" value=\"Submit\" name=\"btnsubmit\" onclick=\"javascript:return validatefiletype('" + str[1] + "'," + str[0] + ",'uploadmenu');\" />");
                    if (Integer.parseInt(str[2]) > 1)
                    {
                        out.println("                       <input type=\"button\" value=\"Add More\" name=\"btnmore\" onclick=\"javascript:return onAddMore(this," + str[2] + ",'" + str[4] + "','" + str[3] + "','maintable');\" />");
                    }
                    out.println("                       <input type=\"button\" value=\"Close Window\" name=\"btnclose\" onclick=\"javascript: window.close();\" />");
                    out.println("                   </td>");
                    out.println("               </tr>");
                    out.println("           </table>");
                    out.println("       </form>");
                    out.println("   </td></tr>");
                    out.println("   </table>");
                    out.println("   </body>");
                }
            }
            else if ((request.getParameter("action")).equals("fileboxsave"))
            {
                Enumeration files;
                String strUploadPath;
                strUploadPath = finpack.FinPack.getProperty("filebox_path");

                int maxPostSize = (2 * 1024 * 1024 * 1024 - 100 * 1024); //2147381248
                MultipartRequest multi = new MultipartRequest(request, strUploadPath, maxPostSize, "ISO-8859-1", new DefaultFileRenamePolicy());

                if (multi.getParameter("encryptStr") == null)
                {
                    out.println("       <h4 align=\"center\" style=\"font-family:arial\">Invalid Access</h4>");
                }
                else
                {
                    out.println("   <body style=\"background:#EAEAEA\">");
                    out.println("   <table width=\"100%\" height=\"100%\" style=\"border:1px solid #9A9A9A\">");
                    out.println("   <tr><td>");

                    String estr = multi.getParameter("encryptStr");
                    String dstr = Utility.decodeData(estr);
                    String[] str = dstr.split("#");

                    long fileSize = Long.parseLong(str[0]);
                    String fileType = str[1];
                    String[] allFileType = fileType.split(",");
                    int max = Integer.parseInt(str[2]);
                    int uploaded = Integer.parseInt(str[6]);
                    int counter = 0;

                    String onremoveCallbackProperty = "";
                    if (str[5].equals("null"))
                    {
                        onremoveCallbackProperty = "javascript:void(0);";
                    }
                    else
                    {
                        onremoveCallbackProperty = str[5].replaceAll("\"", "&quot;");
                    }
                    String[] invalidExt =
                    {
                        "run", "exe", "sh", "jsp", "java", "class", "php", "html", "js", "c", "cpp"
                    };

                    List uploadedFile;
                    uploadedFile = new ArrayList();

                    List invalidType;
                    invalidType = new ArrayList();

                    List invalidSize;
                    invalidSize = new ArrayList();

                    List invalidCnt;
                    invalidCnt = new ArrayList();

                    String fileName;
                    File file;
                    files = multi.getFileNames();
                    for (int i = 1; files.hasMoreElements(); i++)
                    {
                        fileName = (String) files.nextElement();
                        file = multi.getFile(fileName);

                        String renamedFileName = getValidFileName(multi.getFilesystemName(fileName));
                        String uploadPath = strUploadPath + "/" + renamedFileName;

                        File fileDest = new File(uploadPath);
                        file.renameTo(fileDest);

                        String ext = renamedFileName.substring(renamedFileName.indexOf(".") + 1, renamedFileName.length());

                        char flag1 = 'f';
                        char flag2 = 't';
                        for (int j = 0; j < allFileType.length; j++)
                        {
                            String templ = allFileType[j].toLowerCase();
                            String tempu = allFileType[j].toUpperCase();

                            if ((ext.equals(templ)) || (ext.equals(tempu)))
                            {
                                flag2 = 't';
                                for (int k = 0; k < invalidExt.length; k++)
                                {
                                    templ = invalidExt[k].toLowerCase();
                                    tempu = invalidExt[k].toUpperCase();
                                    if ((ext.equals(templ)) || (ext.equals(tempu)))
                                    {
                                        flag2 = 'f';
                                        break;
                                    }
                                }

                                if (flag2 == 'f')
                                {
                                    invalidType.add(renamedFileName);
                                    fileDest.delete();
                                }
                                else if (flag2 == 't')
                                {
                                    flag1 = 't';
                                    break;
                                }
                            }
                        }

                        if (flag1 == 'f')
                        {
                            if (flag2 != 'f')
                            {
                                invalidType.add(renamedFileName);
                                fileDest.delete();
                            }
                        }
                        else if (flag1 == 't')
                        {
                            if (fileDest.length() > (fileSize * 1024))
                            {
                                invalidSize.add(renamedFileName);
                                fileDest.delete();
                            }
                            else
                            {
                                if (counter < (max - uploaded))
                                {
                                    uploadedFile.add(renamedFileName);
                                    counter++;
                                }
                                else
                                {
                                    invalidCnt.add(renamedFileName);
                                    fileDest.delete();
                                }
                            }
                        }
                    }

                    int totalFiles = 0;
                    if (uploadedFile.size() > 0)
                    {
                        out.println("       <h4 align=\"center\">File(s) Successfully Uploaded.</h4>");
                        out.println("       <table align=\"center\" style=\"font-size:small\">");
                        for (int i = 0; i < uploadedFile.size(); i++)
                        {
                            out.println("           <tr><td>");
                            out.println("               File name is : " + uploadedFile.get(i));
                            out.println("           </td></tr>");
                        }
                        out.println("       </table>");


                        out.println("       <table id=\"filestable\" style=\"display:none\">");
                        for (int i = 0; i < uploadedFile.size(); i++)
                        {
                            out.println("       <tr><td>");
                            out.println("           <input type='hidden' name='" + str[3] + "' value='" + uploadedFile.get(i) + "' >");
                            out.println(uploadedFile.get(i));
                            String tblName = "tbl_" + str[7];
                            out.println("       <span onclick=\"" + onremoveCallbackProperty + "\">");
                            out.println("       <span id=\"cancelId_" + (i + 1) + "\" name=\"cancelId\" onclick=\"javascript:return deleteCurrentRow(this,'" + tblName + "');\" style=\"cursor:pointer;font-size:small;font-weight:bold;color:blue;\" onmouseout=\"this.style.color='blue'\" onmouseover=\"this.style.color='red'\">Remove</span>");
                            out.println("       </span>");
                            out.println("       </td></tr>");
                        }
                        totalFiles = uploadedFile.size();
                        out.println("       </table>");
                    }

                    out.println("       <div align=\"center\">");
                    if (uploadedFile.size() > 0)
                    {
                        String tblName = "tbl_" + str[7];
                        out.println("           <input type=\"button\" name=\"btnadd\" onclick=\"javascript:AddFiles(" + totalFiles + ",'" + tblName + "');\" value=\"Add File(s)\">");
                    }
                    else
                    {
                        out.println("           <input type=\"button\" name=\"btnclose\" onclick=\"javascript: window.close();\" value=\"Close Window\">");
                    }
                    out.println("       </div>");

                    out.println("       <br>");
                    out.println("       <br>");
                    out.println("       <br>");

                    if (invalidType.size() > 0)
                    {
                        out.println("       <h4 align=\"center\">File(s) not Uploaded (Restricted File Type)</h4>");
                        out.println("       <table align=\"center\" style=\"font-size:small\">");
                        for (int i = 0; i < invalidType.size(); i++)
                        {
                            out.println("           <tr><td>");
                            out.println("               File name is : " + invalidType.get(i));
                            out.println("           </td></tr>");
                        }
                        out.println("       </table>");
                    }
                    if (invalidSize.size() > 0)
                    {
                        out.println("       <h4 align=\"center\">File(s) not Uploaded (File Size greater than allowed max size)</h4>");
                        out.println("       <table align=\"center\" style=\"font-size:small\">");
                        for (int i = 0; i < invalidSize.size(); i++)
                        {
                            out.println("           <tr><td>");
                            out.println("               File name is : " + invalidSize.get(i));
                            out.println("           </td></tr>");
                        }
                        out.println("       </table>");
                    }
                    if (invalidCnt.size() > 0)
                    {
                        out.println("       <h4 align=\"center\">File(s) not Uploaded (File Count greater than allowed max files)</h4>");
                        out.println("       <table align=\"center\" style=\"font-size:small\">");
                        for (int i = 0; i < invalidCnt.size(); i++)
                        {
                            out.println("           <tr><td>");
                            out.println("               File name is : " + invalidCnt.get(i));
                            out.println("           </td></tr>");
                        }
                        out.println("       </table>");
                    }

                    out.println("   </td></tr>");
                    out.println("   </table>");
                    out.println("   </body>");
                }
            }
            out.println("</html>");
        }
        catch (Throwable t)
        {
            out.println("<!--" + t.getMessage() + "-->");
        }
        finally
        {
            out.close();
        }
    }

    public String getValidFileName(String fileName)
    {
        String outFileName = fileName;
        String ext = outFileName.substring(outFileName.indexOf(".")).toLowerCase();
        outFileName = outFileName.substring(0, outFileName.indexOf("."));

        char c[] = outFileName.toCharArray();
        for (int i = 0; i < c.length; i++)
        {
            int n = (int) c[i];
            if (!((n >= 48 && n <= 57) || (n >= 65 && n <= 90) || (n >= 97 && n <= 122)))
            {
                c[i] = '_';
            }
        }
        outFileName = new String(c);
        while (outFileName.contains("__"))
        {
            outFileName = outFileName.replaceAll("__", "_");
        }
        if (outFileName.length() > 32)
        {
            outFileName = outFileName.substring(0, 32);
        }
        if (outFileName.startsWith("_"))
        {
            outFileName = outFileName.substring(1, outFileName.length());
        }
        if (outFileName.endsWith("_"))
        {
            outFileName = outFileName.substring(0, outFileName.length() - 1);
        }

        return System.nanoTime() + "_" + outFileName + ext;
    }
}
