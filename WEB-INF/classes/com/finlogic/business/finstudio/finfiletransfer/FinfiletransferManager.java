package com.finlogic.business.finstudio.finfiletransfer;

import java.sql.SQLException;
import java.util.List;

public class FinfiletransferManager
{

    private final FinfiletransferDataManager dataManager = new FinfiletransferDataManager();

    public List getDataMaster(FinfiletransferEntityBean entityBean) throws ClassNotFoundException, SQLException
    {
        return dataManager.getDataMaster(entityBean);
    }

    public int insertMaster(FinfiletransferEntityBean entityBean) throws ClassNotFoundException, SQLException
    {
        return dataManager.insertMaster(entityBean);
    }

    public List getViewEmpcode() throws ClassNotFoundException, SQLException
    {
        return dataManager.getViewEmpcode();
    }

    public List getViewProject(int flag) throws ClassNotFoundException, SQLException
    {
        return dataManager.getViewProject(flag);
    }

    public List getViewRequest() throws ClassNotFoundException, SQLException
    {
        return dataManager.getViewRequest();
    }

    public List getDataGrid(FinfiletransferEntityBean entityBean) throws ClassNotFoundException, SQLException
    {
        return dataManager.getDataGrid(entityBean);
    }

    public List getDetail(String masterPK) throws ClassNotFoundException, SQLException
    {
        return dataManager.getDetail(masterPK);
    }

    public List execute(String masterPK) throws ClassNotFoundException, SQLException
    {
        return dataManager.execute(masterPK);
    }

    public void changeStatus(String masterPK, String flag, String emp, String ip) throws ClassNotFoundException, SQLException
    {
        dataManager.changeStatus(masterPK, flag, emp, ip);
    }

    public int checkPrj(String empCode, String prjNm) throws ClassNotFoundException, SQLException
    {
        return dataManager.checkPrj(empCode, prjNm);
    }

    public List getRequests(String flag, String reqNo) throws ClassNotFoundException, SQLException
    {
        return dataManager.getRequests(flag, reqNo);
    }

    public List getMasterData(String masterPk) throws ClassNotFoundException, SQLException
    {
        return dataManager.getMasterData(masterPk);
    }

    public List getMasterDataLoad(String emp) throws ClassNotFoundException, SQLException
    {
        return dataManager.getMasterDataLoad(emp);
    }

    public List getReportDataLoad(FinfiletransferEntityBean entityBean) throws ClassNotFoundException, SQLException
    {
        return dataManager.getReportDataLoad(entityBean);
    }

    public String getReqStatus(String masterPK) throws ClassNotFoundException, SQLException
    {
        return dataManager.getReqStatus(masterPK);
    }
}