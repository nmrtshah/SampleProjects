/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.business.finstudio.ETLJobList;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Siddharth Patel
 */
public class ETLJobListManager
{

    ETLJobListDataManager datamanager = new ETLJobListDataManager();

    public List getGirdData() throws ClassNotFoundException, SQLException
    {
        return datamanager.getGirdData();
    }

    public int insertETLJob(ETLJobListEntityBean entityBean) throws ClassNotFoundException, SQLException
    {
        return datamanager.insertETLJob(entityBean);
    }

}
