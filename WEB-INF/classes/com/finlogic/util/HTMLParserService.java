/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util;

import com.finlogic.apps.wfms2.scriptgen.HTMLControlDetailBean;
import com.finlogic.business.wfms2.scriptgen.ScriptGenDataManager;
import java.io.BufferedReader;
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
import org.htmlcleaner.HtmlCleaner;
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
    public List getControlList(final String pageURL) throws SQLException
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
            HtmlCleaner cleaner;
            cleaner = new HtmlCleaner();
            if (pageURL.length() >= 255)
            {
                pageContent = pageURL;
            }
            else
            {
                pageContent = getHtmlSource(pageURL);
            }

            TagNode rootNode;
            rootNode = cleaner.clean(pageContent);
            TagNode[] allTags;
            allTags = rootNode.getAllElements(true);

            List eventID;
            List eventName;
            String tag;
            String type;
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

                if (allTags[tagCount].getAttributeByName(HTML.Attribute.ID.toString()) == null)
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
                if (allTags[tagCount].getAttributeByName("id") == null && allTags[tagCount].getAttributeByName("name") != null)
                {
                    String control_name = allTags[tagCount].getAttributeByName("name").toString();
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

                    if (type.equalsIgnoreCase("button") || type.equalsIgnoreCase("submit"))
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
                                controlList.add(new HTMLControlDetailBean(dataMan.getHTMLControlID("RADIO BUTTON"),
                                        dataMan.getHTMLControlName(dataMan.getHTMLControlID("RADIO BUTTON")), nameid, value,
                                        access, dataMan.getAccessTypeList(dataMan.getHTMLControlID("RADIO BUTTON")), null, null,
                                        eventID, eventName, xpath.get(tagCount).toString()));
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
            for (; (i < children.length && !children[i].equals(node)); i++);
            suffix = "[" + String.valueOf(i + 1) + "]";
        }
        if (node.getName().equalsIgnoreCase("body"))
        {
            suffix = "";
        }

        return getXPath(parent) + "/" + node.getName() + suffix;
    }
}
