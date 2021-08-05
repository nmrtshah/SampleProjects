/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.webservice;

import com.finlogic.apps.finstudio.webservice.WebServiceFormBean;
import com.finlogic.util.Logger;
import java.io.*;

/**
 *
 * @author Sonam Patel
 */
public final class FileService
{

    private FileService()
    {
    }

    public static void copyFiles(final File src, final File dest) throws IOException
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
                copyFiles(srcFile, destFile);
            }
        }
        else
        {
            //if file, then copy it
            //Use character stream to support all file types
            FileReader in = null;
            FileWriter out = null;
            BufferedReader br = null;
            try
            {
                in = new FileReader(src);
                out = new FileWriter(dest);
                br = new BufferedReader(in);
                //Read a Line
                StringBuilder sbuild;
                sbuild = new StringBuilder();
                String s;
                while ((s = br.readLine()) != null)
                {
                    sbuild.append(s.trim()).append("\n");
                }
                s = sbuild.toString();
                sbuild.delete(0, sbuild.length() - 1);

                //remove single-line comments from File
                sbuild.append(s.replaceAll("//.*\n", "\n"));
                s = sbuild.toString();
                sbuild.delete(0, sbuild.length() - 1);

                //remove multi-line comments from File
                sbuild.append(s.replaceAll("/\\*([^*]|[\r\n]|(\\*+([^*/]|[\r\n])))*\\*+/", ""));
                s = sbuild.toString();
                sbuild.delete(0, sbuild.length() - 1);

                //rename package
                sbuild.append(s.replaceFirst("package[ ]+[a-z_]{1}[a-z0-9_]*[ ]*(\\.[ ]*[a-z_]{1}[a-z0-9_]*[ ]*)*;", ""));
                s = sbuild.toString();

                out.write(s);
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
                    if (out != null)
                    {
                        out.close();
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

    public static void copyClassFiles(final File src, final File dest) throws IOException
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
                copyClassFiles(srcFile, destFile);
            }
        }
        else if (src.getAbsolutePath().endsWith(".class"))
        {
            //if .class file, then copy it
            //Use byte stream to support all file types
            FileOutputStream out = null;
            try
            {
                out = new FileOutputStream(dest);
                //Read All Bytes
                RandomAccessFile f = new RandomAccessFile(src, "r");
                byte[] b = new byte[(int) f.length()];
                f.read(b);
                out.write(b);
            }
            finally
            {
                //Close stream
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

    public static void deleteClassFiles(final String path) throws IOException
    {
        File folder;
        folder = new File(path);
        //make sure directory exists
        if (folder.exists())
        {
            if (folder.isDirectory())
            {
                //list all the directory contents
                String files[];
                files = folder.list();
                for (String temp : files)
                {
                    if (temp.endsWith(".class"))
                    {
                        new File(folder.getAbsolutePath() + "/" + temp).delete();
                    }
                }
            }
        }
    }

    public static String isInterface(final String file)
    {
        FileInputStream fstream;
        DataInputStream in = null;
        BufferedReader br = null;
        boolean pkgflag = false;
        boolean notation = false;
        String strRet = "";
        try
        {
            fstream = new FileInputStream(file);
            // Get the object of DataInputStream
            in = new DataInputStream(fstream);
            br = new BufferedReader(new InputStreamReader(in));

            String strLine;
            //Read File Line By Line
            while ((strLine = br.readLine()) != null)
            {
                strLine = strLine.trim();

                if (strLine.matches(".*package[ ]+com[ ]*\\.[ ]*finlogic[ ]*\\.[ ]*eai[ ]*\\.[ ]*[a-z]([a-z0-9]*)\\.[ ]*ws[ ]*\\.[ ]*producer[ ]*(.[ ]*[a-z]([a-z0-9]*)?)?[ ]*;.*"))
                {
                    pkgflag = true;
                }
                if ("@WebService".equals(strLine))
                {
                    notation = true;
                }
                if (strLine.matches("public interface [A-Za-z]+([A-Za-z0-9]*)Service.*"))
                {
                    if (!pkgflag)
                    {
                        strRet = "Invalid Package Name. Pattern -->  com.finlogic.eai.(ProjectName).ws.producer[.ModuleName] ";
                    }
                    if (!notation)
                    {
                        strRet = strRet + "Missing WebSerive Notation.";
                    }
                    return strRet;
                }
            }
            return "This Is Not An Interface or Invalid Interface Name.";
        }
        catch (Exception e)
        {
            Logger.ErrorLogger(e);
            return "Error While Reading File.";
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

    public static String getPackage(final String file) throws IOException
    {
        FileInputStream fstream;
        DataInputStream in = null;
        BufferedReader br = null;

        try
        {
            fstream = new FileInputStream(file);
            // Get the object of DataInputStream
            in = new DataInputStream(fstream);
            br = new BufferedReader(new InputStreamReader(in));

            String strLine;
            //Read File Line By Line
            while ((strLine = br.readLine()) != null)
            {
                strLine = strLine.trim();

                if (strLine.matches("package[ ]+com[ ]*\\.[ ]*finlogic[ ]*\\.[ ]*eai[ ]*\\.[ ]*[a-z]([a-z0-9]*)\\.[ ]*ws[ ]*\\.[ ]*producer[ ]*(.[ ]*[a-z]([a-z0-9]*)?)?[ ]*;"))
                {
                    strLine = strLine.replaceFirst("package", "");
                    strLine = strLine.trim();
                    strLine = strLine.replace(" ", "");
                    return strLine.substring(0, strLine.length() - 1);
                }
            }
            return "";
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

    public static boolean copyFileContent(final String file, final String srno) throws IOException
    {
        FileInputStream fstream;
        DataInputStream in = null;
        BufferedReader br = null;

        try
        {
            fstream = new FileInputStream(file);
            // Get the object of DataInputStream
            in = new DataInputStream(fstream);
            br = new BufferedReader(new InputStreamReader(in));

            //Read Whole File And Copy Content In Another File
            StringBuffer buffer;
            buffer = new StringBuffer();
            String strLine;
            while ((strLine = br.readLine()) != null)
            {
                buffer.append(strLine.trim()).append("\n");
            }

            String s = buffer.toString();
            buffer.delete(0, buffer.length() - 1);

            //remove single-line comments from File
            buffer.append(s.replaceAll("//.*\n", "\n"));
            s = buffer.toString();
            buffer.delete(0, buffer.length() - 1);

            //remove multi-line comments from File
            buffer.append(s.replaceAll("/\\*([^*]|[\r\n]|(\\*+([^*/]|[\r\n])))*\\*+/", ""));
            s = buffer.toString();

            StringBuffer content;
            content = new StringBuffer();
            s = s.replaceFirst("\\{", "{\n");
            content.append(s.replace(";", ";\n"));
            String filePath;
            filePath = WebServiceFormBean.getWSLOC() + "/" + srno + "tmp/" + srno + ".txt";
            File txtFile = new File(filePath);
            txtFile.mkdirs();
            if (txtFile.exists())
            {
                txtFile.delete();
            }
            File newTxtFile;
            newTxtFile = new File(filePath.substring(0, filePath.lastIndexOf("/")));
            newTxtFile.mkdirs();
            PrintWriter pw;
            pw = new PrintWriter(filePath);
            pw.println(content);
            pw.close();
            return true;
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

    public static String getMethod(final String file, final String regexp) throws IOException
    {
        FileInputStream fstream;
        DataInputStream in = null;
        BufferedReader br = null;

        try
        {
            fstream = new FileInputStream(file);
            // Get the object of DataInputStream
            in = new DataInputStream(fstream);
            br = new BufferedReader(new InputStreamReader(in));

            String strLine;
            StringBuilder sentence;
            sentence = new StringBuilder();
            //Read File Line By Line
            while ((strLine = br.readLine()) != null && !strLine.contains("{"))
            {
                continue;
            }
            while ((strLine = br.readLine()) != null)
            {
                strLine = strLine.trim();
                if (sentence.length() > 0)
                {
                    sentence.delete(0, sentence.length());
                }
                if (strLine.length() > 0 && strLine.charAt(0) == '@')
                {
                    int space;
                    space = strLine.indexOf(' ');
                    if (space != -1)
                    {
                        strLine = strLine.substring(space + 1);
                        strLine = strLine.trim();
                    }
                    else
                    {
                        strLine = "";
                    }
                }
                sentence.append(strLine);
                while (strLine != null && !strLine.contains(";"))
                {
                    strLine = br.readLine();
                    if (strLine != null)
                    {
                        strLine = strLine.trim();
                        if (strLine.length() > 0 && strLine.charAt(0) == '@')
                        {
                            int space;
                            space = strLine.indexOf(' ');
                            if (space != -1)
                            {
                                strLine = strLine.substring(space);
                                strLine = strLine.trim();
                            }
                            else
                            {
                                strLine = "";
                            }
                        }
                        sentence.append(strLine).append(" ");
                    }
                }
                if (strLine != null && strLine.contains(";"))
                {
                    String method;
                    method = sentence.toString();
                    method = method.trim();
                    method = method.replaceAll("[ ]+", " ");
                    method = method.replaceAll("[ ]+\\(", "(");
                    method = method.replaceAll("\\([ ]+", "(");
                    method = method.replaceAll("[ ]+\\)", ") ");
                    method = method.replaceAll("\\)[ ]+", ") ");
                    method = method.replaceAll("[ ]+,", ",");

                    if (method.matches(regexp))
                    {
                        String rexp;
                        rexp = method.substring(0, method.length() - 1).trim();
                        return rexp;
                    }
                }
            }
            return "";
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

    public static String getBeanPackage(final String file) throws IOException
    {
        FileInputStream fstream;
        DataInputStream in = null;
        BufferedReader br = null;

        try
        {
            fstream = new FileInputStream(file);
            // Get the object of DataInputStream
            in = new DataInputStream(fstream);
            br = new BufferedReader(new InputStreamReader(in));

            String strLine;
            //Read File Line By Line
            while ((strLine = br.readLine()) != null)
            {
                strLine = strLine.trim();

                if (strLine.matches(".*package[ ]+[a-z_]{1}[a-z0-9_]*[ ]*(\\.[ ]*[a-z_]{1}[a-z0-9_]*[ ]*)*;.*"))
                {
                    int idx = strLine.indexOf("package ");
                    strLine = strLine.substring(idx);

                    idx = strLine.indexOf(';');
                    strLine = strLine.substring(0, idx + 1);
                    strLine = strLine.replaceFirst("package", "");
                    strLine = strLine.trim();
                    strLine = strLine.replace(" ", "");
                    return strLine.substring(0, strLine.length() - 1);
                }
            }
            return "";
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
