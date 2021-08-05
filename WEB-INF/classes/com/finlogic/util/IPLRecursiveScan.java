package com.finlogic.util;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author njuser
 */
import com.finlogic.util.persistence.SQLUtility;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.FileUtils;

public class IPLRecursiveScan
{

    /**
     * @param args the command line arguments
     */
    private static String alias = "NA";
    private static String database = "NA";
    private static String database_type = "NA";
    private static String destFile = "/home/bhatu/";

    public static void main(String[] args) throws Exception
    {
        // get user input

        alias = getParam("Enter Database Alias : ");
        database = getParam("Enter Database Name : ");
        database_type = getParam("Enter Database Type (oracle,mysql) : ");
        //destFile = getParam("Enter Destination Folder : ");

        String[] ext =
        {
            "jsp", "java", "sql"
        };
        String path = null;
        File f = null;

        path = "/opt/apache-tomcat1/webapps";
        f = new File(path);
        if (f.exists())
        {
            String[] fArray = f.list();
            for (int i = 0; i < fArray.length; i++)
            {
                searchFileRecursive(path, ext);
            }

        }
        path = "/opt/apache-tomcat2/webapps";
        f = new File(path);
        if (f.exists())
        {
            String[] fArray = f.list();
            for (int i = 0; i < fArray.length; i++)
            {
                searchFileRecursive(path, ext);
            }

        }

    }

    public static void searchFileRecursive(String dirPath, String[] extensions) throws Exception
    {
        File root = new File(dirPath);
        File dest = new File(destFile + alias + "-" + database_type + ".csv");
        boolean recursive = true;

        // Find Files

        Collection files = FileUtils.listFiles(root, extensions, recursive);

        // Get Tables

        SQLUtility util = new SQLUtility();

        StringBuilder sb = new StringBuilder();
        if (database_type.equalsIgnoreCase("oracle"))
        {
            sb.append("SELECT TNAME as TABLE_NAME FROM SYS.TAB");
        }
        else
        {
            sb.append("SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA='" + database + "'");
        }

        List ls = util.getList(alias, sb.toString());
        Map map = null;

        for (Iterator iterator = files.iterator(); iterator.hasNext();)
        {
            File file = (File) iterator.next();

            FileReader freader = null;
            LineNumberReader lnreader = null;
            String line = null;

            freader = new FileReader(file);
            lnreader = new LineNumberReader(freader);


            while ((line = lnreader.readLine()) != null)
            {
                line = line.replaceAll("\n", " ");
                line = line.replaceAll("\r", "'");
                line = line.replaceAll("\t", " ");
                line = line.replaceAll("\"", "'");

                while (line.contains("  "))
                {
                    line = line.replaceAll("  ", " ");
                }
                line = line.trim();
                String line1 = line.toUpperCase();

                for (int i = 0; i < ls.size(); i++)
                {
                    map = (HashMap) ls.get(i);

                    String tableName = map.get("TABLE_NAME").toString().toUpperCase();

                    if ((line1.contains("." + tableName) || line1.contains(" " + tableName))
                            && (line1.contains(tableName + " ") || line1.contains(tableName + "'")))
                    {
                        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(dest, true));
                        bufferedWriter.append("\"" + file.getAbsolutePath() + "\",\"" + map.get("TABLE_NAME").toString() + "\",\"" + lnreader.getLineNumber() + "\",\"" + line + "\"");
                        bufferedWriter.newLine();
                        bufferedWriter.flush();
                        bufferedWriter.close();
                    }
                }
            }
            freader.close();
            lnreader.close();
        }
    }

    public static String getParam(String message)
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String out = null;
        try
        {
            out = br.readLine();
        }
        catch (IOException e)
        {
            out = "NA";
        }
        if (out == null)
        {
            out = "NA";
        }
        return out;
    }
}
