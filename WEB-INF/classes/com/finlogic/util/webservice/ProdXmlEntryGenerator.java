/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.webservice;

import com.finlogic.apps.finstudio.webservice.WebServiceFormBean;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

/**
 *
 * @author Sonam Patel
 */
public class ProdXmlEntryGenerator
{

    public void createEntry(final WebServiceFormBean formBean) throws IOException
    {
        String fileName;
        fileName = formBean.getIntrfcName().substring(0, formBean.getIntrfcName().length() - 5);

        String filePath;
        filePath = WebServiceFormBean.getTOMCAT() + "/webapps/finstudio/generated/" + formBean.getTxtSrno() + "WS/producer/";
        File file;
        file = new File(filePath);
        file.mkdirs();

        String pkg;
        pkg = FileService.getPackage(WebServiceFormBean.getWSLOC() + "/" + formBean.getTxtSrno() + "tmp/" + formBean.getTxtSrno() + ".txt");

        PrintWriter pw;
        pw = new PrintWriter(filePath + "ProdXmlEntry.txt");
        //Make web.xml Entry
        pw.println("/* Copy these Entries and paste in web.xml, if web.xml does not contain these. */");
        pw.println("/* web.xml Entries Start */");
        pw.println("    <servlet>");
        pw.println("        <servlet-name>CXFServlet</servlet-name>");
        pw.println("        <servlet-class>org.apache.cxf.transport.servlet.CXFServlet</servlet-class>");
        pw.println("    </servlet>");
        pw.println("    <servlet-mapping>");
        pw.println("        <servlet-name>CXFServlet</servlet-name>");
        pw.println("        <url-pattern>/ws/*</url-pattern>");
        pw.println("    </servlet-mapping>");
        pw.println("/* web.xml Entries Over */");
        pw.println();
        pw.println();

        //Make applicationContext.xml Entry
        pw.println("/* Copy these Entries and paste in applicationContext.xml, if applicationContext.xml does not contain these. */");
        pw.println("/* applicationContext.xml Entries Start */");
        pw.println("<beans xmlns=\"http://www.springframework.org/schema/beans\"");
        pw.println("       xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
        pw.println("       xmlns:context=\"http://www.springframework.org/schema/context\"");
        pw.println("       xmlns:cxf=\"http://cxf.apache.org/core\"");
        pw.println("       xmlns:jaxws=\"http://cxf.apache.org/jaxws\"");
        pw.println("       xsi:schemaLocation=\"http://www.springframework.org/schema/beans");
        pw.println("       http://www.springframework.org/schema/beans/spring-beans-2.5.xsd");
        pw.println("       http://www.springframework.org/schema/context");
        pw.println("       http://www.springframework.org/schema/context/spring-context-2.5.xsd");
        pw.println("       http://cxf.apache.org/core");
        pw.println("       http://cxf.apache.org/schemas/core.xsd");
        pw.println("       http://cxf.apache.org/jaxws");
        pw.println("       http://cxf.apache.org/schemas/jaxws.xsd\"");
        pw.println("       default-autowire=\"byName\">");
        pw.println();

        pw.println("<!-- Load CXF modules from cxf.jar -->");
        pw.println("<import resource=\"classpath:META-INF/cxf/cxf.xml\"/>");
        pw.println("<import resource=\"classpath:META-INF/cxf/cxf-extension-soap.xml\"/>");
        pw.println("<import resource=\"classpath:META-INF/cxf/cxf-servlet.xml\"/>");
        pw.println();

        pw.println("<!-- Enable message logging using the CXF logging feature -->");
        pw.println("<cxf:bus>");
        pw.println("    <cxf:features>");
        pw.println("        <cxf:logging/>");
        pw.println("    </cxf:features>");
        pw.println("</cxf:bus>");
        pw.println();

        pw.println("<!-- Aegis data binding -->");
        pw.println("<bean id=\"aegisBean\" class=\"org.apache.cxf.aegis.databinding.AegisDatabinding\" scope=\"prototype\"/>");
        pw.println();

        pw.println("<bean id=\"jaxws-and-aegis-service-factory\" class=\"org.apache.cxf.jaxws.support.JaxWsServiceFactoryBean\" scope=\"prototype\">");
        pw.println("    <property name=\"dataBinding\" ref=\"aegisBean\"/>");
        pw.println("    <property name=\"serviceConfigurations\">");
        pw.println("        <list>");
        pw.println("            <bean class=\"org.apache.cxf.jaxws.support.JaxWsServiceConfiguration\"/>");
        pw.println("            <bean class=\"org.apache.cxf.aegis.databinding.AegisServiceConfiguration\"/>");
        pw.println("            <bean class=\"org.apache.cxf.service.factory.DefaultServiceConfiguration\"/>");
        pw.println("        </list>");
        pw.println("    </property>");
        pw.println("</bean>");
        pw.println();

        String endPointId;
        endPointId = Character.toLowerCase(fileName.charAt(0)) + fileName.substring(1);
        pw.println("<bean id=\"" + endPointId + "Impl\" class=\"" + pkg + "." + fileName + "Impl\"/>");
        pw.println();

        pw.println("<!-- Service endpoint -->");
        pw.println("<jaxws:endpoint id=\"" + endPointId + "\"");
        pw.println("                implementorClass=\"" + pkg + "." + fileName + "Impl\"");
        pw.println("                implementor=\"#" + endPointId + "Impl\"");
        pw.println("                address=\"/" + endPointId.toLowerCase(Locale.getDefault()) + "\">");
        pw.println("    <jaxws:serviceFactory>");
        pw.println("        <ref bean=\"jaxws-and-aegis-service-factory\"/>");
        pw.println("    </jaxws:serviceFactory>");
        pw.println("</jaxws:endpoint>");
        pw.println("/* applicationContext.xml Entries Over */");
        pw.println();
        pw.close();
    }
}
