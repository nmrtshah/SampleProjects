/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.mobappsgeneration;

import com.finlogic.apps.finstudio.mobappsgeneration.MobAppsFormBean;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author njuser
 */
public class XmlReader
{

    public XMLProperties readXML(final MobileFactory platform) throws ParserConfigurationException, IOException, SAXException
    {
        File xmlFile = null;

        DocumentBuilderFactory docBuilderFactory;
        docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder;
        docBuilder = docBuilderFactory.newDocumentBuilder();
        xmlFile = new File(MobAppsFormBean.getTEMPLATE() + "/MobAppProperties.xml");
        Document doc;
        doc = docBuilder.parse(xmlFile);

        XMLProperties xmlProps;
        xmlProps = new XMLProperties();

        Node parentNode;
        parentNode = doc.getElementsByTagName(platform.toString()).item(0);//platform node

        Element element;
        element = (Element) parentNode;
        if (element.hasChildNodes())
        {
            Node tagNode;
            NodeList tagNodes;

            //reading template tag
            tagNode = element.getElementsByTagName("template").item(0);
            xmlProps.setTemplate(tagNode.getTextContent().trim());

            //reading webroot tag
            tagNode = element.getElementsByTagName("webroot").item(0);
            xmlProps.setWebroot(tagNode.getTextContent().trim());

            //reading appName tag
            tagNodes = element.getElementsByTagName("appName");
            if (tagNodes != null)
            {
                List<Map<String, String>> ls;
                ls = new ArrayList<Map<String, String>>();
                for (int i = 0; i < tagNodes.getLength(); i++)
                {
                    Element paramElem;
                    paramElem = (Element) tagNodes.item(i);
                    if (paramElem.hasChildNodes())
                    {
                        Map<String, String> map;
                        map = new HashMap<String, String>();
                        Node fileNode;
                        fileNode = paramElem.getElementsByTagName("file").item(0);//file node
                        map.put("file", fileNode.getTextContent().trim());
                        Node patternNode;
                        patternNode = paramElem.getElementsByTagName("pattern").item(0);//pattern node
                        map.put("pattern", patternNode.getTextContent().trim());

                        ls.add(map);
                    }
                }
                xmlProps.setAppName(ls);
            }

            //reading version tag
            tagNodes = element.getElementsByTagName("version");
            if (tagNodes != null)
            {
                List<Map<String, String>> ls;
                ls = new ArrayList<Map<String, String>>();
                for (int i = 0; i < tagNodes.getLength(); i++)
                {
                    Element paramElem;
                    paramElem = (Element) tagNodes.item(i);
                    if (paramElem.hasChildNodes())
                    {
                        Map<String, String> map;
                        map = new HashMap<String, String>();
                        Node fileNode;
                        fileNode = paramElem.getElementsByTagName("file").item(0);//file node
                        map.put("file", fileNode.getTextContent().trim());
                        Node patternNode;
                        patternNode = paramElem.getElementsByTagName("pattern").item(0);//pattern node
                        map.put("pattern", patternNode.getTextContent().trim());

                        ls.add(map);
                    }
                }
                xmlProps.setVersion(ls);
            }

            //reading packageName tag
            tagNodes = element.getElementsByTagName("packageName");
            if (tagNodes != null)
            {
                List<Map<String, String>> ls;
                ls = new ArrayList<Map<String, String>>();
                for (int i = 0; i < tagNodes.getLength(); i++)
                {
                    Element paramElem;
                    paramElem = (Element) tagNodes.item(i);
                    if (paramElem.hasChildNodes())
                    {
                        Map<String, String> map;
                        map = new HashMap<String, String>();
                        Node fileNode;
                        fileNode = paramElem.getElementsByTagName("file").item(0);//file node
                        map.put("file", fileNode.getTextContent().trim());
                        Node patternNode;
                        patternNode = paramElem.getElementsByTagName("pattern").item(0);//pattern node
                        map.put("pattern", patternNode.getTextContent().trim());

                        ls.add(map);
                    }
                }
                xmlProps.setPackageName(ls);
            }

            //reading welcomeFile tag
            tagNodes = element.getElementsByTagName("welcomeFile");
            if (tagNodes != null)
            {
                List<Map<String, String>> ls;
                ls = new ArrayList<Map<String, String>>();
                for (int i = 0; i < tagNodes.getLength(); i++)
                {
                    Element paramElem;
                    paramElem = (Element) tagNodes.item(i);
                    if (paramElem.hasChildNodes())
                    {
                        Map<String, String> map;
                        map = new HashMap<String, String>();
                        Node fileNode;
                        fileNode = paramElem.getElementsByTagName("file").item(0);//file node
                        map.put("file", fileNode.getTextContent().trim());
                        Node patternNode;
                        patternNode = paramElem.getElementsByTagName("pattern").item(0);//pattern node
                        map.put("pattern", patternNode.getTextContent().trim());

                        ls.add(map);
                    }
                }
                xmlProps.setWelcomeFile(ls);
            }

            //reading commands tag
            tagNode = element.getElementsByTagName("commands").item(0);
            if (tagNode != null)
            {
                List<String> ls;
                ls = new ArrayList<String>();
                tagNodes = element.getElementsByTagName("command");
                if (tagNodes != null)
                {
                    for (int i = 0; i < tagNodes.getLength(); i++)
                    {
                        ls.add(tagNodes.item(i).getTextContent().trim());
                    }
                    xmlProps.setCommands(ls);
                }
            }

            //reading output tag
            tagNode = element.getElementsByTagName("output").item(0);
            if (tagNode != null)
            {
                String path;
                path = tagNode.getTextContent().trim();
                xmlProps.setOutput(path);
            }
        }
        return xmlProps;
    }
}
