/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.apps.wfms2.scriptgen;

import java.util.List;

/**
 *
 * @author Ankur Mistry
 */
public class HTMLControlDetailBean
{

    private int htmlControlID;
    private String access;
    private String htmlControlName;
    private String controlNameID;
    private String controlValue;
    private List eventID;
    private List eventName;
    private List accessList;
    private List valueList;
    private List labelList;
    private String xpath;

    /**
     *
     * @param htmlControlID
     * @param htmlControlName
     * @param controlNameID
     * @param controlValue
     * @param access
     * @param accessList
     * @param valueList
     * @param labelList
     * @param eventID
     * @param eventName
     * @param xpath
     */
    public HTMLControlDetailBean(final int htmlControlID, final String htmlControlName, final String controlNameID, final String controlValue, final String access, final List accessList, final List valueList, final List labelList, final List eventID, final List eventName, final String xpath)
    {
        this.htmlControlID = htmlControlID;
        this.htmlControlName = htmlControlName;
        this.controlNameID = controlNameID;
        this.controlValue = controlValue;
        this.access = access;
        this.accessList = accessList;
        this.valueList = valueList;
        this.labelList = labelList;
        this.eventID = eventID;
        this.eventName = eventName;
        this.xpath = xpath;
    }

    public String getAccess()
    {
        return access;
    }

    public List getAccessList()
    {
        return accessList;
    }

    public String getControlNameID()
    {
        return controlNameID;
    }

    public String getControlValue()
    {
        return controlValue;
    }

    public List getEventID()
    {
        return eventID;
    }

    public List getEventName()
    {
        return eventName;
    }

    public int getHtmlControlID()
    {
        return htmlControlID;
    }

    public String getHtmlControlName()
    {
        return htmlControlName;
    }

    public List getLabelList()
    {
        return labelList;
    }

    public List getValueList()
    {
        return valueList;
    }

    public String getXpath()
    {
        return xpath;
    }

    public void setXpath(String xpath)
    {
        this.xpath = xpath;
    }
}
