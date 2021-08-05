/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.apps.finstudio.controller;

import com.finlogic.util.Logger;
import java.io.File;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author njuser
 */
public class CommonController extends MultiActionController
{

    public ModelAndView getServiceList(final HttpServletRequest request, final HttpServletResponse response)
    {
        ModelAndView mav;
        mav = new ModelAndView();
        try
        {
            List<Map<String, String>> serviceList = new ArrayList();

            File fileXML = new File("/tomcat/WSConsumer.xml");

            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(fileXML);

            doc.getDocumentElement().normalize();

            NodeList listOfConnInfo = doc.getElementsByTagName("bean");

            NodeList Nodelist1;
            Element Element1;
            Element Element2;

            for (int s = 0; s < listOfConnInfo.getLength(); s++)
            {

                Node firstPersonNode = listOfConnInfo.item(s);

                if (firstPersonNode.getNodeType() == Node.ELEMENT_NODE)
                {

                    Element firstElement = (Element) firstPersonNode;

                    Nodelist1 = firstElement.getElementsByTagName("property");
                    Element1 = (Element) Nodelist1.item(0);
                    Element2 = (Element) Nodelist1.item(1);
                    try
                    {
                        String s1 = Element1.getAttribute("value");
                        String s2 = Element2.getAttribute("value");

                        String[] sa1 = s1.split("\\.");
                        String[] sa2 = s2.replaceAll("//", "/").split("/");

                        Map<String, String> m = new HashMap();
                        m.put("serviceClass", sa1[sa1.length - 1]);
                        m.put("server", sa2[1]);
                        m.put("project", sa2[2]);
                        m.put("serviceName", sa2[4].replaceAll("\\?wsdl", ""));
                        m.put("url", s2);

                        serviceList.add(m);

                    }
                    catch (Exception e)
                    {
                    }

                }
            }
            mav.addObject("serviceList", serviceList);
            mav.setViewName("serviceList");
        }
        catch (Exception e)
        {
            mav.addObject("error", Logger.ErrorLogger(e));
            mav.setViewName("error");
        }
        return mav;
    }
}
