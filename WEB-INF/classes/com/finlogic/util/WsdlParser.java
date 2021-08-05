/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 *
 * @author njuser
 */
public class WsdlParser
{

    private String url;
    private String xmlFilePath;

    public WsdlParser()
    {
    }

    public WsdlParser(String strurl, String xmlFile)
    {
        this.url = strurl;
        this.xmlFilePath = xmlFile;
    }

    public Map getDetail() throws IOException, ParserConfigurationException, SAXException
    {
        Map detail = null;

        if (getDataFromURL())
        {
            detail = readXML();
        }
        File file = new File(xmlFilePath);
        if (file.exists())
        {
            file.delete();
        }
        return detail;
    }

    private boolean getDataFromURL()
    {
        StringBuffer s = new StringBuffer();
        try
        {
            java.net.URL neturl = new java.net.URL(url);
            java.net.URLConnection conn = neturl.openConnection();
            conn.connect();
            java.io.InputStreamReader content = new java.io.InputStreamReader(conn.getInputStream());

            //read file
            for (int i = 0; i != -1; i = content.read())
            {
                s.append((char) i);
            }

            int index = s.indexOf("<wsdl:definitions");
            if (index != -1)
            {
                s.delete(0, index);
            }
            content.close();

            //write file
            File file;
            file = new File(xmlFilePath.substring(0, xmlFilePath.lastIndexOf('/')));
            file.mkdirs();
            PrintWriter pw;
            pw = new PrintWriter(xmlFilePath);
            pw.println(s);
            pw.close();
            return true;
        }
        catch (Exception e)
        {
            finutils.errorhandler.ErrorHandler.PrintInFile(e, finpack.FinPack.getProperty("tomcat1_path") + "/webapps/finstudio/log/wsdl_log.txt");
            return false;
        }
    }

    private Map readXML() throws IOException, ParserConfigurationException, SAXException
    {
        DocumentBuilderFactory docBuilderFactory;
        docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder;
        docBuilder = docBuilderFactory.newDocumentBuilder();
        Document doc;
        doc = docBuilder.parse(xmlFilePath);

        Map<String, String> detail = new HashMap<String, String>();

        Node node;
        node = doc.getElementsByTagName("wsdl:definitions").item(0);//for package
        if (node != null)
        {
            Element paramElem;
            paramElem = (Element) node;
            String pkg = paramElem.getAttribute("targetNamespace");
            pkg = pkg.replace("http://", "");
            pkg = pkg.replace("https://", "");
            pkg = pkg.replace("/", "");
            detail.put("package", pkg);
        }

        node = doc.getElementsByTagName("wsdl:portType").item(0);//for interface
        if (node != null)
        {
            Element paramElem;
            paramElem = (Element) node;
            detail.put("interface", paramElem.getAttribute("name"));
        }
        return detail;
    }

    public Set getWsThrows(final String exception)
    {
        Set result = new HashSet();
        String expStr = exception.replace("[", "");
        expStr = expStr.replace("]", "");
        expStr = expStr.replace(" ", "");

        String[] exceptions = expStr.split(",");
        //get all datatypes in one return type
        for (int i = 0; i < exceptions.length; i++)
        {
            result.add(exceptions[exceptions.length - 1]);
        }
        return result;
    }

    public Set getWsImports(final String element, Set set)
    {
        String elemnt = element.replace("[", "");
        elemnt = elemnt.replace("]", "");
        elemnt = elemnt.replace(" ", "");
        elemnt = elemnt.replace(">", "<");
        elemnt = elemnt.replace(",", "<");
        String[] pkg;
        pkg = elemnt.split("<");

        for (int i = 0; i < pkg.length; i++)
        {
            String elem;
            elem = pkg[i];
            elem = elem.replace("[]", "");
            String retype[];
            retype = elem.split("\\.");

            if (!(retype.length == 3 && retype[0].endsWith("java") && "lang".equals(retype[1])) && retype.length != 1 && !(retype[retype.length - 1].trim().equals("List")))
            {
                set.add(elem);
            }
        }
        return set;
    }
}
