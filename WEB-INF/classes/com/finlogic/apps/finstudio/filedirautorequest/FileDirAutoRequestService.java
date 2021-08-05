/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.apps.finstudio.filedirautorequest;

import com.finlogic.business.finstudio.filedirautorequest.FileDirAutoRequestManager;
import com.finlogic.util.datastructure.JSONParser;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author njuser
 */
public class FileDirAutoRequestService
{

    private final FileDirAutoRequestManager manager = new FileDirAutoRequestManager();

    public int insertData(final FileDirAutoRequestBean formBean, String empCode, String userName) throws ClassNotFoundException, SQLException
    {
        String pathComboValue = formBean.getPathCombo();

        switch (pathComboValue) {
        case "tomcat":
            formBean.setPathCombo("/opt/apache-tomcat1/webapps/");
            break;
        case "apache":
            formBean.setPathCombo("/var/www/html/");
            break;
        case "storage_box":
            formBean.setPathCombo("/opt/application_storage/storage_box/");
            break;
        case "temp_files":
            formBean.setPathCombo("/opt/application_storage/temp_files/");
            break;
        case "email_attachment":
            formBean.setPathCombo("/opt/application_storage/email_attachment/");
            break;
        }
        return manager.insertData(formBean, empCode, userName);
    }

    public String reportLoader(final FileDirAutoRequestBean formBean) throws ClassNotFoundException, SQLException
    {
        List ListReport = manager.reportLoader(formBean);
        String strReport;
        if (!ListReport.isEmpty())
        {
            JSONParser js = new JSONParser();
            strReport = js.parse(ListReport, "SRNO", false, false);
        }
        else
        {
            strReport = "data = {total_count:1,rows: [{\"id\":\"1\",\"data\":[{\"value\":\"<center><b>No Record Found.</b></center>\",\"colspan\":\"6\"}]}]}";
        }
        return strReport;
    }

    public String authorizeLoader() throws ClassNotFoundException, SQLException
    {
        List ListReport = manager.authorizeLoader();
        String strReport;
        if (!ListReport.isEmpty())
        {
            JSONParser js = new JSONParser();
            strReport = js.parse(ListReport, "SRNO", false, false);
        }
        else
        {
            strReport = "data = {total_count:1,rows: [{\"id\":\"1\",\"data\":[{\"value\":\"<center><b>No Record Found.</b></center>\",\"colspan\":\"6\"}]}]}";
        }
        return strReport;
    }

    public int authorizeUpdate(String id) throws ClassNotFoundException, SQLException
    {
        return manager.authorizeUpdate(id);
    }

    public int rejectUpdate(String id, String comment) throws ClassNotFoundException, SQLException
    {
        return manager.rejectUpdate(id, comment);
    }

    public List getUserList() throws ClassNotFoundException, SQLException
    {
        return manager.getUserList();
    }

    public List getProjectNames() throws ClassNotFoundException, SQLException
    {
        return manager.getProjectNames();
    }
    
    public List getWFMProjectList(int flag) throws ClassNotFoundException, SQLException
    {
        return manager.getWFMProjectList(flag);
    }
    
    public String getDesigCode(String empCode) throws ClassNotFoundException, SQLException
    {
        return manager.getDesigCode(empCode);
    }
}