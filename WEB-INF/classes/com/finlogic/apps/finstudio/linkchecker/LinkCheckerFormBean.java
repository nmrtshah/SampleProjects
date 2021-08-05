/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.finlogic.apps.finstudio.linkchecker;


/**
 *
 * @author njuser
 */

public class LinkCheckerFormBean 
{
    private String parentUrl;
    private String currentUrl;
    private String contentType;
    private int responseCode;

    public String getContentType()
    {
        return contentType;
    }

    public void setContentType(String contentType)
    {
        this.contentType = contentType;
    }

    public String getCurrentUrl()
    {
        return currentUrl;
    }

    public void setCurrentUrl(String currentUrl)
    {
        this.currentUrl = "<a href="+currentUrl+" target=\"_blank\">"+currentUrl+"</a>";
    }

    public String getParentUrl()
    {
        return parentUrl;
    }

    public void setParentUrl(String parentUrl)
    {
//        this.parentUrl = parentUrl;
        this.parentUrl = "<a href="+parentUrl+" target=\"_blank\">"+parentUrl+"</a>";
    }

    public int getResponseCode()
    {
        return responseCode;
    }

    public void setResponseCode(int responseCode)
    {
        this.responseCode = responseCode;
    }
  

}
