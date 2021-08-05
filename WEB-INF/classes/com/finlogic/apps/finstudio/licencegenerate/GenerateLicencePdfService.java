/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.apps.finstudio.licencegenerate;

import com.finlogic.business.finstudio.licencegenerate.GenerateLicencePdfManager;
import com.finlogic.util.datastructure.JSONParser;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author njuser
 */
public class GenerateLicencePdfService
{

    private GenerateLicencePdfManager manager = new GenerateLicencePdfManager();

    public int insertClient(GenerateLicencePdfFormBean formbean, String empName) throws Exception
    {
        Map m = new HashMap();
        m.put("CLIENT_NAME", formbean.getClientname());
        m.put("ADDRESS", formbean.getAddress());
        m.put("ENTRY_BY", empName);

        return manager.insertClient(m);
    }

    public int insertClientDetails(GenerateLicencePdfFormBean formbean, String empName) throws Exception
    {
        Map m = new HashMap();
        m.put("CLIENT_ID", formbean.getClientcmb());
        m.put("SYS_KEY", formbean.getSyskey());
        m.put("ACTIVATION_DATE", formbean.getActivation_date());
        m.put("EXPIRY_DATE", formbean.getExpiry_date());
        m.put("COMMENT", formbean.getComment());
        m.put("ENTRY_BY", empName);

        return manager.insertClientDetails(m);
    }

    public String reportLoader(final GenerateLicencePdfFormBean formBean) throws Exception
    {
        Map m = new HashMap();
        if (!"".equals(formBean.getClientcmb().trim()))
        {
            m.put("CLIENT_ID", formBean.getClientcmb());
        }
        List ListReport = manager.reportLoader(m);
        String strReport;
        if (!ListReport.isEmpty())
        {
            JSONParser js = new JSONParser();
            strReport = js.parse(ListReport, "SRNO", false, false);
        }
        else
        {
            strReport = "data = {total_count:1,rows: [{\"id\":\"1\",\"data\":[{\"value\":\"<center><b>No Record Found.</b></center>\",\"colspan\":\"7\"}]}]}";
        }
        return strReport;
    }

    public List getClientList() throws Exception
    {
        return manager.getClientList();
    }

    public String getPassword(String clientid) throws Exception
    {
        return manager.getPassword(clientid);
    }

}
