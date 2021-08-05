/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.scriptgen;

import com.finlogic.apps.wfms2.scriptgen.HTMLControlDetailBean;
import com.finlogic.business.wfms2.scriptgen.ScriptGenDataManager;
import com.finlogic.util.Logger;
import finpack.FinPack;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.text.html.HTML;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.ITagInfoProvider;
import org.htmlcleaner.TagInfo;
import org.htmlcleaner.TagNode;

/**
 *
 * @author Ankur Mistry
 */
public class HTMLParserService
{

    /**
     *
     * @param pageURL
     * @return List
     * @throws SQLException
     * @throws NullPointerException
     */
    public List getControlList(final boolean isURL, final String pageSource) throws SQLException
    {

        List<HTMLControlDetailBean> controlList;
        controlList = new ArrayList<HTMLControlDetailBean>();
        ScriptGenDataManager dataMan;
        dataMan = new ScriptGenDataManager();
        String[] EVENTS =
        {
            "onblur", "onchange", "onclick", "ondblclick",
            "onfocus", "onkeydown", "onkeypress", "onkeyup",
            "onmousedown", "onmousemove", "onmouseout", "onmouseover",
            "onmouseup", "onselect"
        };

        try
        {
            String pageContent;
            CleanerProperties cp = new CleanerProperties();
            cp.setAllowHtmlInsideAttributes(true);            
            
            HtmlCleaner cleaner = new HtmlCleaner(cp);
            if (!isURL)
            {
                pageContent = pageSource;
            }
            else
            {
                pageContent = getHtmlSource(pageSource);
            }

            TagNode rootNode = cleaner.clean(pageContent);
            TagNode[] allTags = rootNode.getAllElements(true);

            List eventID;
            List eventName;
            String tag;
            String type = "";
            String nameid;
            String access;
            String value;
            List xpath = new ArrayList();

            Set unique_names = new HashSet();
            Set repeat_names = new HashSet();

            for (int tagCount = 0; tagCount < allTags.length; tagCount++)
            {
                value = "";
                tag = allTags[tagCount].getName();
                if (allTags[tagCount].getAttributeByName(HTML.Attribute.ID.toString()) == null
                        && allTags[tagCount].getAttributeByName(HTML.Attribute.NAME.toString()) != null)
                {
                    nameid = allTags[tagCount].getAttributeByName(HTML.Attribute.NAME.toString());
                    access = "name";
                }
                else
                {
                    nameid = allTags[tagCount].getAttributeByName(HTML.Attribute.ID.toString());
                    access = "id";
                }

                if (allTags[tagCount].getAttributeByName(HTML.Attribute.ID.toString()) == null
                        && allTags[tagCount].getAttributeByName(HTML.Attribute.NAME.toString()) == null)
                {
                    nameid = "";
                    access = "";
                }

                if (allTags[tagCount].getAttributeByName(HTML.Attribute.ID.toString()) != null
                        && allTags[tagCount].getAttributeByName(HTML.Attribute.NAME.toString()) != null
                        && allTags[tagCount].getAttributeByName(HTML.Attribute.ID.toString()).equalsIgnoreCase(allTags[tagCount].getAttributeByName(HTML.Attribute.NAME.toString())))
                {
                    access = "name";
                }

                //if controls name same then get xpath                
                type = allTags[tagCount].getAttributeByName(HTML.Attribute.TYPE.toString());
                if (allTags[tagCount].getAttributeByName("id") == null && allTags[tagCount].getAttributeByName("name") != null
                        && allTags[tagCount].getAttributeByName("value") != null && type.equalsIgnoreCase("radio"))
                {
                    nameid = allTags[tagCount].getAttributeByName(HTML.Attribute.NAME.toString());
                    access = "name";
                }
                else if (allTags[tagCount].getAttributeByName("id") == null && allTags[tagCount].getAttributeByName("name") != null)
                {
                    String control_name = allTags[tagCount].getAttributeByName("name");
                    if (unique_names.contains(control_name))
                    {
                        repeat_names.add(control_name);
                    }
                    else
                    {
                        unique_names.add(control_name);
                    }
                }

                if (allTags[tagCount].getAttributeByName("id") == null && repeat_names.contains((String) allTags[tagCount].getAttributeByName("name")))
                {
                    access = "xpath";
                    xpath.add("//" + getXPath(allTags[tagCount]));
                }
                else if (allTags[tagCount].getAttributeByName(HTML.Attribute.ID.toString()) == null
                        && allTags[tagCount].getAttributeByName(HTML.Attribute.NAME.toString()) == null
                        && allTags[tagCount].getAttributeByName(HTML.Attribute.HREF.toString()) == null
                        && allTags[tagCount].getAttributeByName(HTML.Attribute.SRC.toString()) == null)
                {
                    access = "xpath";
                    String xPath = "//" + getXPath(allTags[tagCount]);
                    xpath.add(xPath);
                    nameid = allTags[tagCount].getText().toString();
                }
                else
                {
                    xpath.add("-");
                }
                //getting xpath ends

                if (allTags[tagCount].hasAttribute(HTML.Attribute.VALUE.toString()))
                {
                    value = allTags[tagCount].getAttributeByName(HTML.Attribute.VALUE.toString());
                }
                eventID = new ArrayList();
                eventName = new ArrayList();
                for (String ename : EVENTS)
                {
                    if (allTags[tagCount].hasAttribute(ename))
                    {
                        eventID.add(dataMan.getControlEventID(ename.substring(2)));
                        eventName.add(dataMan.getControlEventName(dataMan.getControlEventID(ename.substring(2))));
                    }
                }

                if (tag.equals(HTML.Tag.INPUT.toString()))
                {
                    type = allTags[tagCount].getAttributeByName(HTML.Attribute.TYPE.toString());

                    if (type.equalsIgnoreCase("button") || type.equalsIgnoreCase("submit") || type.equalsIgnoreCase("reset"))
                    {
                        controlList.add(new HTMLControlDetailBean(dataMan.getHTMLControlID("BUTTON"),
                                dataMan.getHTMLControlName(dataMan.getHTMLControlID("BUTTON")), nameid, value,
                                access, dataMan.getAccessTypeList(dataMan.getHTMLControlID("BUTTON")), null, null,
                                eventID, eventName, xpath.get(tagCount).toString()));
                    }
                    else
                    {
                        if (type.equalsIgnoreCase("checkbox"))
                        {
                            controlList.add(new HTMLControlDetailBean(dataMan.getHTMLControlID("CHECK BOX"),
                                    dataMan.getHTMLControlName(dataMan.getHTMLControlID("CHECK BOX")), nameid, value,
                                    access, dataMan.getAccessTypeList(dataMan.getHTMLControlID("CHECK BOX")), null, null,
                                    eventID, eventName, xpath.get(tagCount).toString()));
                        }
                        else
                        {
                            if (type.equalsIgnoreCase("radio"))
                            {
//                                if (value != null && !access.equals("id"))
//                                {
//                                    controlList.add(new HTMLControlDetailBean(dataMan.getHTMLControlID("RADIO BUTTON"),
//                                            dataMan.getHTMLControlName(dataMan.getHTMLControlID("RADIO BUTTON")), nameid, value,
//                                            "name", dataMan.getAccessTypeList(dataMan.getHTMLControlID("RADIO BUTTON")), null, null,
//                                            eventID, eventName, xpath.get(tagCount).toString()));
//                                }
//                                else
                                {
                                    controlList.add(new HTMLControlDetailBean(dataMan.getHTMLControlID("RADIO BUTTON"),
                                            dataMan.getHTMLControlName(dataMan.getHTMLControlID("RADIO BUTTON")), nameid, value,
                                            access, dataMan.getAccessTypeList(dataMan.getHTMLControlID("RADIO BUTTON")), null, null,
                                            eventID, eventName, xpath.get(tagCount).toString()));
                                }
                            }
                            else
                            {
                                if (type.equalsIgnoreCase("text") || type.equalsIgnoreCase("password"))
                                {
                                    controlList.add(new HTMLControlDetailBean(dataMan.getHTMLControlID("TEXT BOX"),
                                            dataMan.getHTMLControlName(dataMan.getHTMLControlID("TEXT BOX")), nameid, value,
                                            access, dataMan.getAccessTypeList(dataMan.getHTMLControlID("TEXT BOX")), null, null,
                                            eventID, eventName, xpath.get(tagCount).toString()));
                                }
                                else if (type.equalsIgnoreCase("file"))
                                {
                                    controlList.add(new HTMLControlDetailBean(dataMan.getHTMLControlID("FILE"),
                                            dataMan.getHTMLControlName(dataMan.getHTMLControlID("FILE")), nameid, value,
                                            access, dataMan.getAccessTypeList(dataMan.getHTMLControlID("FILE")), null, null,
                                            eventID, eventName, xpath.get(tagCount).toString()));
                                }
                            }
                        }
                    }
                }
                else
                {
                    if (tag.equals(HTML.Tag.SELECT.toString()))
                    {
                        List valueList;
                        valueList = new ArrayList();
                        List labelList;
                        labelList = new ArrayList();

                        if (allTags[tagCount].getChildTags().length > 1)
                        {

                            for (int i = 0; i < allTags[tagCount].getChildTags().length; i++)
                            {
                                valueList.add(allTags[tagCount].getChildTags()[i].getAttributeByName(HTML.Attribute.VALUE.toString()));
                                labelList.add(allTags[tagCount].getChildTags()[i].getText().toString());
                            }

                            if (allTags[tagCount].getAttributeByName(HTML.Attribute.MULTIPLE.toString()) != null)
                            {
                                controlList.add(new HTMLControlDetailBean(dataMan.getHTMLControlID("COMBO BOX (MULTI SELECT)"),
                                        dataMan.getHTMLControlName(dataMan.getHTMLControlID("COMBO BOX (MULTI SELECT)")), nameid, value,
                                        access, dataMan.getAccessTypeList(dataMan.getHTMLControlID("COMBO BOX (MULTI SELECT)")), valueList, labelList,
                                        eventID, eventName, xpath.get(tagCount).toString()));
                            }
                            else
                            {
                                controlList.add(new HTMLControlDetailBean(dataMan.getHTMLControlID("COMBO BOX (SINGLE SELECT)"),
                                        dataMan.getHTMLControlName(dataMan.getHTMLControlID("COMBO BOX (SINGLE SELECT)")), nameid, value,
                                        access, dataMan.getAccessTypeList(dataMan.getHTMLControlID("COMBO BOX (SINGLE SELECT)")), valueList, labelList,
                                        eventID, eventName, xpath.get(tagCount).toString()));
                            }
                        }
                        else
                        {
                            if (allTags[tagCount].getAttributeByName(HTML.Attribute.MULTIPLE.toString()) != null)
                            {
                                controlList.add(new HTMLControlDetailBean(dataMan.getHTMLControlID("COMBO BOX (MULTI SELECT)"),
                                        dataMan.getHTMLControlName(dataMan.getHTMLControlID("COMBO BOX (MULTI SELECT)")), nameid, value,
                                        access, dataMan.getAccessTypeList(dataMan.getHTMLControlID("COMBO BOX (MULTI SELECT)")), null, null,
                                        eventID, eventName, xpath.get(tagCount).toString()));
                            }
                            else
                            {
                                controlList.add(new HTMLControlDetailBean(dataMan.getHTMLControlID("COMBO BOX (SINGLE SELECT)"),
                                        dataMan.getHTMLControlName(dataMan.getHTMLControlID("COMBO BOX (SINGLE SELECT)")), nameid, value,
                                        access, dataMan.getAccessTypeList(dataMan.getHTMLControlID("COMBO BOX (SINGLE SELECT)")), null, null,
                                        eventID, eventName, xpath.get(tagCount).toString()));
                            }
                        }
                    }
                    else
                    {
                        if (tag.equals(HTML.Tag.A.toString()))
                        {
                            nameid = allTags[tagCount].getText().toString();
                            if (nameid.trim().equals(""))
                            {
                                continue;
                            }
                            controlList.add(new HTMLControlDetailBean(dataMan.getHTMLControlID("LINK"),
                                    dataMan.getHTMLControlName(dataMan.getHTMLControlID("LINK")), nameid, value,
                                    access, dataMan.getAccessTypeList(dataMan.getHTMLControlID("LINK")), null, null,
                                    eventID, eventName, xpath.get(tagCount).toString()));
                        }
                        else
                        {
                            if (tag.equals(HTML.Tag.IMG.toString()))
                            {
                                nameid = "image";
                                value = allTags[tagCount].getAttributeByName(HTML.Attribute.SRC.toString());
                                controlList.add(new HTMLControlDetailBean(dataMan.getHTMLControlID("IMAGE"),
                                        dataMan.getHTMLControlName(dataMan.getHTMLControlID("IMAGE")), nameid, value,
                                        access, dataMan.getAccessTypeList(dataMan.getHTMLControlID("IMAGE")), null, null,
                                        eventID, eventName, xpath.get(tagCount).toString()));
                            }
                            else
                            {
                                if (tag.equals(HTML.Tag.TEXTAREA.toString()))
                                {
                                    controlList.add(new HTMLControlDetailBean(dataMan.getHTMLControlID("TEXT AREA"),
                                            dataMan.getHTMLControlName(dataMan.getHTMLControlID("TEXT AREA")), nameid, value,
                                            access, dataMan.getAccessTypeList(dataMan.getHTMLControlID("TEXT AREA")), null, null,
                                            eventID, eventName, xpath.get(tagCount).toString()));
                                }
                            }
                        }
                    }
                }
            }
        }
        catch (SQLException exp)
        {
            throw new Exception(exp);
        }
        finally
        {
            return controlList;
        }
    }

    /**
     *
     * @param pageURL
     * @return String
     * @throws IOException
     * @throws NullPointerException
     */
    private String getHtmlSource(final String pageURL) throws IOException
    {
        StringBuilder content;
        content = new StringBuilder();
        HttpURLConnection conn;
        BufferedReader reader = null;

        try
        {
            URL url;
            url = new URL(pageURL);
            conn = (HttpURLConnection) url.openConnection();
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;

            line = reader.readLine();
            while (line != null)
            {
                content.append(line);
                line = reader.readLine();
            }
            reader.close();
        }
        catch (Exception exp)
        {
            throw new IOException(exp);
        }
        finally
        {
            reader.close();
            return content.toString().trim();
        }
    }

    public String getXPath(TagNode node)
    {
        TagNode parent = node.getParent();
        if (parent == null)
        {
            return node.getName();
        }
        String suffix = "";

        if (node.getAttributeByName("id") != null)
        {
            suffix = "[@id='" + node.getAttributeByName("id") + "']";
        }
        else
        {
            int i = 0;
            TagNode[] children = parent.getChildTags();
            for (; (i < children.length && !children[i].equals(node)); i++)
            {
                continue;
            }
            suffix = "[" + (i + 1) + "]";
        }
        if (node.getName().equalsIgnoreCase("body"))
        {
            suffix = "";
        }

        return getXPath(parent) + "/" + node.getName() + suffix;
    }

    public static String readPageSource(String fileName) throws IOException
    {
        FileReader in = null;
        BufferedReader br = null;
        StringBuilder sbuild = new StringBuilder();
        try
        {
            in = new FileReader(FinPack.getProperty("tomcat1_path") + "/webapps/finstudio/upload/" + fileName);
            br = new BufferedReader(in);
            String s;
            //Read a Line
            while ((s = br.readLine()) != null)
            {
                sbuild.append(s.trim()).append("\n");
            }
            new File(FinPack.getProperty("tomcat1_path") + "/webapps/finstudio/upload/" + fileName).delete();
            return sbuild.toString();
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

    public static void copyPageSourceFile(String fileName)
    {
        File src = new File(FinPack.getProperty("filebox_path") + fileName);
        File dest = new File(FinPack.getProperty("tomcat1_path") + "/webapps/finstudio/upload/" + fileName);
        src.renameTo(dest);
    }
}
