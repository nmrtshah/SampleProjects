package com.finlogic.util;

import com.finlogic.apps.finstudio.linkchecker.LinkCheckerFormBean;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LinkChecker
{

    private static List<String> startTag = null;
    private static List<String> endTag = null;
    private static List<String> propertyTag = null;
    private List<String> urls = null;
    private int maximum = 0;
    private boolean external = false;
    private String mainDomain = null;

    static
    {
        startTag = new ArrayList<String>();
        startTag.add("<a");
        startTag.add("<link");
        startTag.add("<img");
        startTag.add("<script");
        startTag.add("<frame");
        startTag.add("<iframe");

        endTag = new ArrayList<String>();
        endTag.add(">");
        endTag.add(">");
        endTag.add(">");
        endTag.add(">");
        endTag.add(">");
        endTag.add(">");

        propertyTag = new ArrayList<String>();
        propertyTag.add("href");
        propertyTag.add("href");
        propertyTag.add("src");
        propertyTag.add("src");
        propertyTag.add("src");
        propertyTag.add("src");

    }

    public static void main(String[] args) throws IOException
    {
        //String rootUrl1 = "http://localhost:8080/autotesttool/linkchecker.fin";
//        String rootUrl1 = "http://www.njwealthadvisors.com/nav/latsnav.php";
        //String rootUrl1 = "http://www.njwealthadvisors.com//mf.php";
//        String rootUrl1 = "http://www.njgurukul.com/top.php";
//        String rootUrl1 = "http://www.njfundz.com/";
        String rootUrl1;
        rootUrl1 = "http://www.njgurukul.com/index_content.php";
//        String rootUrl1 = "http://njtest/onlinetrading/login.fin?action=showLoginForm";


        LinkChecker lchk;
        lchk = new LinkChecker();

        lchk.processURL(rootUrl1, 50, false);
    }

    public List<LinkCheckerFormBean> processURL(final String url, final int maxLinks, final boolean extrn) throws IOException
    {
        List<LinkCheckerFormBean> list;
        list = new ArrayList<LinkCheckerFormBean>();

        urls = new ArrayList<String>();

        this.maximum = maxLinks;
        this.external = extrn;

//        mainDomain = url.substring(url.indexOf("://") + 3);
//        //Logger.DataLogger("mainDomain=" + mainDomain);
//        if (mainDomain.indexOf("/") > 0)
//        {
//            mainDomain = mainDomain.substring(0, mainDomain.indexOf("/"));
//        }

        mainDomain = getDomain(url);

        //Logger.DataLogger("mainDomain=" + mainDomain);

        getUrl(url, url, list);

        return list;

    }

    public void getUrl(final String urlPassed, final String urlParent, final List<LinkCheckerFormBean> list) throws IOException
    {
        String newURL;
        newURL = urlPassed;
        //Logger.DataLogger("urlPassed="+urlPassed);

        while (newURL.endsWith("/"))
        {
            String temp;
            temp = newURL.substring(0, newURL.length() - 1);
            newURL = temp;
        }

        if (maximum > 0 && list.size() >= maximum)
        {
            return;
        }

        for (Iterator<String> it = urls.iterator(); it.hasNext();)
        {
            String str;
            str = it.next();

            if (str.equalsIgnoreCase(newURL))
            {
                return;
            }
        }

        urls.add(newURL.trim());

        String mainDomainSub = null;
        mainDomainSub = getDomain(newURL);

        if (!external)
        {
            if (!mainDomainSub.equals(mainDomain))
            {
                LinkCheckerFormBean lcm;
                lcm = new LinkCheckerFormBean();
                lcm.setParentUrl(urlParent);
                lcm.setCurrentUrl(newURL);
                lcm.setResponseCode(-1);
                lcm.setContentType("external-link");
                list.add(lcm);
                return;
            }
        }

        boolean isIndexURL = false;

        BufferedReader in = null;
        URL url = null;
        try
        {
            url = new URL(newURL);
            URLConnection con = null;
            con = url.openConnection();

            URLConnection con1 = null;
            con1 = url.openConnection();

            if (con1 instanceof HttpURLConnection)
            {
                HttpURLConnection httpConnection;
                httpConnection = (HttpURLConnection) con1;
                httpConnection.setRequestMethod("HEAD");
                httpConnection.connect();
                int rcode;
                rcode = httpConnection.getResponseCode();
                String ctype;
                ctype = httpConnection.getContentType();

                //Logger.DataLogger("urlPassed:"+urlPassed);
                String temp;
                temp = httpConnection.getURL().toString();
                newURL = temp;
                //Logger.DataLogger("urlPassed:"+urlPassed);

                if (httpConnection.getURL().toString().endsWith("/"))
                {
                    isIndexURL = true;
                }

                Logger.DataLogger("[" + (list.size() + 1) + "][" + rcode + "][" + ctype + "][" + newURL + "][" + urlParent + "]");

                LinkCheckerFormBean lcm;
                lcm = new LinkCheckerFormBean();
                lcm.setParentUrl(urlParent);
                lcm.setCurrentUrl(newURL);
                lcm.setResponseCode(rcode);
                lcm.setContentType(ctype);
                list.add(lcm);

                if (rcode == 404)
                {
                    return;
                }

                if (!ctype.contains("text/html") || newURL.endsWith(".css")) //|| urlPassed.endsWith(".gif") || urlPassed.endsWith(".jpg"))
                {
                    return;
                }
            }

            String inputLine = null;
            in = new BufferedReader(new InputStreamReader(con.getInputStream()));

            while ((inputLine = in.readLine()) != null)
            {
                String inputLine2;
                inputLine2 = inputLine.trim();
                inputLine = inputLine.trim().toLowerCase();

                for (int i = 0; i < startTag.size(); i++)
                {
                    if (inputLine.contains(startTag.get(i)) && inputLine.contains(endTag.get(i)))
                    {
                        int propertyIndex;
                        propertyIndex = inputLine2.indexOf(propertyTag.get(i));
                        if (propertyIndex > -1)
                        {
                            //Logger.DataLogger("inputLine:"+inputLine);
                            String url1 = inputLine2.substring(propertyIndex + propertyTag.get(i).length() + 1);

                            if (url1.startsWith("\""))
                            {
                                url1 = url1.substring(1);
                                url1 = url1.substring(0, url1.indexOf('\"'));

                            }
                            else if (url1.startsWith("'"))
                            {
                                url1 = url1.substring(1);
                                url1 = url1.substring(0, url1.indexOf('\''));
                            }
                            else if (url1.startsWith("/"))
                            {
                                url1 = url1.substring(1);
                            }

                            //Logger.DataLogger("url1:"+url1);


                            if (newURL.contains(url1))
                            {
                                url1 = newURL;
                            }

                            if (!url1.startsWith("http") && !isIndexURL)
                            {
                                String url2 = "";
                                url2 = newURL.substring(0, newURL.lastIndexOf('/')) + "/" + url1;

                                // check if mainDomainSub available in url1

                                if (!url2.contains(mainDomainSub))
                                {
                                    url1 = newURL + "/" + url1;
                                }
                                else
                                {
                                    url1 = url2;
                                }

                            }
                            else if (!url1.startsWith("http"))
                            {
                                url1 = newURL + "/" + url1;
                            }

                            if (url1.contains("../"))
                            {
                                String beforeStr = url1.substring(0, url1.indexOf("../"));
                                String afterStr = url1.substring(url1.indexOf("../"));
                                String url2 = null;
                                //Logger.DataLogger("beforeStr:" + beforeStr);
                                //Logger.DataLogger("afterStr:" + afterStr);

                                while (beforeStr.endsWith("/"))
                                {
                                    beforeStr = beforeStr.substring(0, beforeStr.length() - 1);
                                }
                                afterStr = afterStr.replace("../", "/");

                                url2 = beforeStr.substring(0, beforeStr.lastIndexOf('/')) + afterStr;

                                // check if mainDomainSub available in url1

                                if (!url2.contains(mainDomainSub))
                                {
                                    url2 = beforeStr + afterStr;
                                }
                                //Logger.DataLogger("beforeStr:" + beforeStr);
                                //Logger.DataLogger("afterStr:" + afterStr);
                                //Logger.DataLogger("url2:" + url2);
                                url1 = url2;
                            }
                            //Logger.DataLogger("url1:"+url1);
                            getUrl(url1, newURL, list);
                        }
                    }
                }
            }
            in.close();
        }
        catch (Exception ex)
        {
            //Logger.getLogger(TestURL2.class.getName()).log(Level.SEVERE, null, ex);
            Logger.DataLogger("Error in " + newURL + ex.getMessage());
            LinkCheckerFormBean lcm;
            lcm = new LinkCheckerFormBean();
            lcm.setParentUrl(urlParent);
            lcm.setCurrentUrl(newURL);
            lcm.setResponseCode(-1);
            lcm.setContentType(ex.getMessage());
            list.add(lcm);
            in.close();
        }
        finally
        {
            if (in != null)
            {
                in.close();
            }
        }
    }

    public String getDomain(final String u)
    {
        String strDomain = u;
        strDomain = strDomain.replace("http://", "");
        strDomain = strDomain.replace("//", "/");

        String strDomainArr[];
        strDomainArr = strDomain.split("/");

        strDomain = strDomainArr[0];

        if (!strDomain.contains("."))
        {
            strDomain = strDomain + "/" + strDomainArr[1];
        }
        Logger.DataLogger("Domain is : " + strDomain);

        return strDomain;
    }
}