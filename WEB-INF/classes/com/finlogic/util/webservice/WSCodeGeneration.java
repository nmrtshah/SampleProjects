/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.webservice;

import com.finlogic.apps.finstudio.webservice.WebServiceFormBean;
import java.io.IOException;

/**
 *
 * @author Sonam Patel
 */
public class WSCodeGeneration
{

    public String generate(final WebServiceFormBean formBean) throws ClassNotFoundException, IOException
    {
        //Generate Implementor Class
        ImplementorGenerator impl;
        impl = new ImplementorGenerator();
        impl.implement(formBean);

        //Generate Consumer Class
        ConsumerGenerator cons;
        cons = new ConsumerGenerator();
        cons.generateCons(formBean);

        //Generate XML Entries For Producer
        ProdXmlEntryGenerator proxml;
        proxml = new ProdXmlEntryGenerator();
        proxml.createEntry(formBean);

        //Generate XML Entries For Consumer
        ConsXmlEntryGenerator conxml;
        conxml = new ConsXmlEntryGenerator();
        conxml.createEntry(formBean);

        return WebServiceFormBean.getTOMCAT() + "/webapps/finstudio/generated/" + formBean.getTxtSrno() + "WS/";
    }
}
