/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.apps.finstudio.dbobjectversionrpt;

import com.finlogic.business.finstudio.dbmetadatacomparator.DBMcomparatorEntityBean;
import com.finlogic.business.finstudio.dbmetadatacomparator.DBMcomparatorManager;
import com.finlogic.business.finstudio.dbmetadatainspector.DBMetadataDataManager;
import com.finlogic.business.finstudio.dbobjectversionrpt.DBObjVersionReport;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author njuser
 */
public class DBObjVersionRptService
{
    private final DBObjVersionReport dBObjVersionReport = new DBObjVersionReport();
    private final DBMcomparatorManager dBMcomparatorManager = new DBMcomparatorManager();
    private final DBMetadataDataManager dBMetadataDataManager = new DBMetadataDataManager();
    
    public List getDBNames(final DBObjVersionRptFormBean formbean) throws SQLException, ClassNotFoundException
    {
        DBMcomparatorEntityBean entityBean = convertFormBeanToEntityBean(formbean);
        return dBMcomparatorManager.getDBNames(entityBean);
    }

    private DBMcomparatorEntityBean convertFormBeanToEntityBean(DBObjVersionRptFormBean formBean)
    {
        DBMcomparatorEntityBean entityBean = new DBMcomparatorEntityBean();
        entityBean.setTxtDB(formBean.getTxtDB());
        entityBean.setCmbDB(formBean.getCmbDB());
        entityBean.setCmbObjType(formBean.getCmbObjType());
        entityBean.setTxtObjName(formBean.getTxtObjName());
        entityBean.setCmbObjName(formBean.getCmbObjName());
        return entityBean;
    }

    public String getDBType(String dbId) throws ClassNotFoundException, SQLException
    {
        return dBMetadataDataManager.getDBType(dbId);
    }

    public List getObjName(String objName, String server) throws ClassNotFoundException, SQLException
    {
        return dBObjVersionReport.getobjName(objName, server);
    }

    public List getReport(DBObjVersionRptFormBean formBean) throws ClassNotFoundException, SQLException
    {
       return dBObjVersionReport.getReport(formBean);        
    }

    public List<Map> getDefList(String queryID) throws ClassNotFoundException, SQLException
    {
        return dBObjVersionReport.getDefList(queryID);
    }
}
