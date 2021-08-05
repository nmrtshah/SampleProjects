/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.apps.finstudio.ETLJobList;

import com.finlogic.business.finstudio.ETLJobList.ETLJobListEntityBean;
import com.finlogic.business.finstudio.ETLJobList.ETLJobListManager;
import com.finlogic.util.datastructure.JSONParser;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Siddharth Patel
 */
public class ETLJobListService
{

    ETLJobListManager manager = new ETLJobListManager();

    public ETLJobListEntityBean convertFormBeanToEntityBean(ETLJobListFormBean formBean)
    {
        ETLJobListEntityBean entityBean = new ETLJobListEntityBean();
        if (formBean.getTableName() != null && !formBean.getTableName().equalsIgnoreCase(""))
        {
            entityBean.setTableName(formBean.getTableName());
        }
        if (formBean.getFromServer() != null && !formBean.getFromServer().equalsIgnoreCase(""))
        {
            entityBean.setFromServer(formBean.getFromServer());
        }
        if (formBean.getToServer() != null && !formBean.getToServer().equalsIgnoreCase(""))
        {
            entityBean.setToServer(formBean.getToServer());
        }
        if (formBean.getFromSchema() != null && !formBean.getFromSchema().equalsIgnoreCase(""))
        {
            entityBean.setFromSchema(formBean.getFromSchema());
        }
        if (formBean.getToTable() != null && !formBean.getToTable().equalsIgnoreCase(""))
        {
            entityBean.setToTable(formBean.getToTable());
        }
        if (formBean.getToSchema() != null && !formBean.getToSchema().equalsIgnoreCase(""))
        {
            entityBean.setToSchema(formBean.getToSchema());
        }
        if (formBean.getKjb() != null && !formBean.getKjb().equalsIgnoreCase(""))
        {
            entityBean.setKjb(formBean.getKjb());
        }
        if (formBean.getKtr() != null && !formBean.getKtr().equalsIgnoreCase(""))
        {
            entityBean.setKtr(formBean.getKtr());
        }
        if (formBean.getPaths() != null && !formBean.getPaths().equalsIgnoreCase(""))
        {
            entityBean.setPaths(formBean.getPaths());
        }
        if (formBean.getCronFile() != null && !formBean.getCronFile().equalsIgnoreCase(""))
        {
            entityBean.setCronFile(formBean.getCronFile());
        }
        if (formBean.getMainJob() != null && !formBean.getMainJob().equalsIgnoreCase(""))
        {
            entityBean.setMainJob(formBean.getMainJob());
        }
        if (formBean.getType() != null && !formBean.getType().equalsIgnoreCase(""))
        {
            entityBean.setType(formBean.getType());
        }
        if (formBean.getCronTab() != null && !formBean.getCronTab().equalsIgnoreCase(""))
        {
            entityBean.setCronTab(formBean.getCronTab());
        }
        return entityBean;
    }

    public String getGirdData() throws ClassNotFoundException, SQLException
    {
        List data = manager.getGirdData();
        return new JSONParser().parse(data, "SRNO", true, false);
    }

    public int insertETLJob(ETLJobListFormBean formBean) throws ClassNotFoundException, SQLException
    {
        return manager.insertETLJob(convertFormBeanToEntityBean(formBean));
    }
}
