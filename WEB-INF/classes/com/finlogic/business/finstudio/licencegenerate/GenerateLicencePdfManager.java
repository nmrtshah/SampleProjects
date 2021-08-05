/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.business.finstudio.licencegenerate;

import java.util.List;
import java.util.Map;

/**
 *
 * @author njuser
 */
public class GenerateLicencePdfManager
{

    private GenerateLicencePdfDataManager datamanager = new GenerateLicencePdfDataManager();

    public int insertClient(Map m) throws Exception
    {
        return datamanager.insertClient(m);
    }

    public int insertClientDetails(Map m) throws Exception
    {
        return datamanager.insertClientDetails(m);
    }

    public List getClientList() throws Exception
    {
        return datamanager.getClientList();
    }

    public String getPassword(String clientid) throws Exception
    {
        return datamanager.getPassword(clientid);
    }

    public List reportLoader(Map m) throws Exception
    {
        return datamanager.reportLoader(m);
    }
}
