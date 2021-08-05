/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.business.finstudio.filedirautorequest;

import com.finlogic.apps.finstudio.filedirautorequest.FileDirAutoRequestBean;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author njuser
 */
public class FileDirAutoRequestManager
{

    private final FileDirAutoRequestDataManager datamanager = new FileDirAutoRequestDataManager();

    public int insertData(final FileDirAutoRequestBean formBean, String empCode, String userName) throws ClassNotFoundException, SQLException
    {
        return datamanager.insertData(formBean, empCode, userName);
    }

    public List reportLoader(final FileDirAutoRequestBean formBean) throws ClassNotFoundException, SQLException
    {
        return datamanager.reportLoader(formBean);
    }

    public List authorizeLoader() throws ClassNotFoundException, SQLException
    {
        return datamanager.authorizeLoader();
    }

    public int authorizeUpdate(String id) throws ClassNotFoundException, SQLException
    {
        return datamanager.authorizeUpdate(id);
    }

    public int rejectUpdate(String id, String comment) throws ClassNotFoundException, SQLException
    {
        return datamanager.rejectUpdate(id, comment);
    }

    public List getUserList() throws ClassNotFoundException, SQLException
    {
        return datamanager.getUserList();
    }
    public List getProjectNames() throws ClassNotFoundException, SQLException
    {
        return datamanager.getProjectNames();
    }
    public List getWFMProjectList(int flag) throws ClassNotFoundException, SQLException
    {
        return datamanager.getWFMProjectList(flag);
    }
    public String getDesigCode(String empCode) throws ClassNotFoundException, SQLException
    {
        return datamanager.getDesigCode(empCode);
    }
}