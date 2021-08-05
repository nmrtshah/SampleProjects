package com.finlogic.apps.finstudio.finfiletransfer;

import com.finlogic.business.finstudio.finfiletransfer.FinfiletransferEntityBean;
import com.finlogic.business.finstudio.finfiletransfer.FinfiletransferManager;
import com.finlogic.util.Logger;
import com.finlogic.util.datastructure.JSONParser;
import java.sql.SQLException;
import java.util.List;

public class FinfiletransferService
{

    private final FinfiletransferManager manager = new FinfiletransferManager();

    public FinfiletransferEntityBean formBeanToEntityBean(FinfiletransferFormBean formBean)
    {
        //finutils.errorhandler.ErrorHandler.PrintInLog("/home/njuser/Desktop/njp.txt", "Len : "+formBean.getMainList().length);
        FinfiletransferEntityBean entityBean;
        entityBean = new FinfiletransferEntityBean();
        if (formBean.getSrcdest() != null && !formBean.getSrcdest().equals("-1"))
        {
            entityBean.setSrcdestDB(formBean.getSrcdest());
        }
        if (formBean.getReqno() != null && !formBean.getReqno().equals("") && !formBean.getReqno().equals("-1"))
        {
            entityBean.setReqnoDB(Integer.parseInt(formBean.getReqno()));
        }
        entityBean.setPurposeDB(formBean.getPurpose());
        if (formBean.getMasterpkPrimeKey() != null && !formBean.getMasterpkPrimeKey().equals(""))
        {
            entityBean.setMasterpkDB(Integer.parseInt(formBean.getMasterpkPrimeKey()));
        }
        entityBean.setMasterpk(formBean.getMasterpk());
        if (formBean.getEmpcode() != null && !formBean.getEmpcode().equals("-1"))
        {
            entityBean.setEmpcode(formBean.getEmpcode());
        }
        if (formBean.getProject() != null && !formBean.getProject().equals("-1"))
        {
            String projId = formBean.getProject().split("-")[0];
            entityBean.setProject(projId);
        }
        entityBean.setHdnGenId(formBean.getHdnGenId());
//        List<Map<String, String>> l=formBean.getL();

//        entityBean.setL(formBean.getL());
        if (formBean.getFromdate() != null && !formBean.getFromdate().equals(""))
        {
            entityBean.setFromdate(formBean.getFromdate());
        }
        if (formBean.getTodate() != null && !formBean.getTodate().equals(""))
        {
            entityBean.setTodate(formBean.getTodate());
        }
        if (formBean.getViewreqno() != null && !formBean.getViewreqno().equals("-1"))
        {
            entityBean.setViewreqno(formBean.getViewreqno());
        }
//        entityBean.setView_reqno(formBean.getView_reqno());
        if (formBean.getViewpurpose() != null && !formBean.getViewpurpose().equals(""))
        {
            entityBean.setViewpurpose(formBean.getViewpurpose());
        }
        if (formBean.getLang() != null && !formBean.getLang().equals(""))
        {
            entityBean.setLang(formBean.getLang());
        }



        if ( formBean.getCmbsrcdest() != null ) {
            String srcdestarray[] = (formBean.getCmbsrcdest()).split(",");
            StringBuilder sbsrcdest = new StringBuilder();
            for (int i = 0; i < srcdestarray.length; i++)
            {
                sbsrcdest.append("'").append(srcdestarray[i]).append("',");
            }
            entityBean.setSrcdest(sbsrcdest.substring(0, (sbsrcdest.length() - 1)));
        }


        entityBean.setMasterTask(formBean.getMasterTask());
        entityBean.setTxtReqId(formBean.getTxtReqId());
        entityBean.setFileName(formBean.getTxtFilename());
        entityBean.setProjectname(formBean.getCmbprojectName());

        return entityBean;
    }

    public String getDataMaster(FinfiletransferFormBean formBean) throws ClassNotFoundException, SQLException
    {
        List lst;
        lst = manager.getDataMaster(formBeanToEntityBean(formBean));
        Logger.DataLogger("Service List : " + lst.size());
        JSONParser jsonParser;
        jsonParser = new JSONParser();
        String sidx;
        sidx = formBean.getSidx();
        int page;
        page = formBean.getPage();
        int rows;
        rows = formBean.getRows();

        return jsonParser.parse(lst, sidx, page, rows);
    }

    public int insertMaster(FinfiletransferFormBean formBean) throws ClassNotFoundException, SQLException
    {
        //Logger.DataLogger("Len :"+formBean.getMainList().length);
        return manager.insertMaster(formBeanToEntityBean(formBean));
    }

    public List getRequests(String flag, String reqNo) throws ClassNotFoundException, SQLException
    {
        return manager.getRequests(flag, reqNo);
    }

    public List getViewEmpcode() throws ClassNotFoundException, SQLException
    {
        return manager.getViewEmpcode();
    }

    public List getViewProject(int flag) throws ClassNotFoundException, SQLException
    {
        return manager.getViewProject(flag);
    }

    public List getViewRequest() throws ClassNotFoundException, SQLException
    {
        return manager.getViewRequest();
    }

    public List getDetail(String masterPK) throws ClassNotFoundException, SQLException
    {
        return manager.getDetail(masterPK);
    }

    public List getDataGrid(FinfiletransferFormBean formBean) throws ClassNotFoundException, SQLException
    {
        return manager.getDataGrid(formBeanToEntityBean(formBean));
    }

    public List execute(String masterPK) throws ClassNotFoundException, ClassNotFoundException, ClassNotFoundException, SQLException
    {
        return manager.execute(masterPK);
    }

    public void changeStatus(String masterPK, String flag, String emp, String ip) throws ClassNotFoundException, SQLException
    {
        manager.changeStatus(masterPK, flag, emp, ip);
    }

    public int checkPrj(String empCode, String prjNm) throws ClassNotFoundException, SQLException
    {
        return manager.checkPrj(empCode, prjNm);
    }

    public List getMasterData(String masterPk) throws ClassNotFoundException, SQLException
    {
        return manager.getMasterData(masterPk);
    }

    public String getMasterDataLoad(String emp, FinfiletransferFormBean formBean) throws ClassNotFoundException, SQLException
    {
        List lst;
        lst = manager.getMasterDataLoad(emp);
        Logger.DataLogger("Service List : " + lst.size());
        JSONParser jsonParser;
        jsonParser = new JSONParser();
        String sidx;
        sidx = formBean.getSidx();
        int page;
        page = formBean.getPage();
        int rows;
        rows = formBean.getRows();

        return jsonParser.parse(lst, sidx, page, rows);

    }

    public String getReportDataLoad(FinfiletransferFormBean formBean) throws ClassNotFoundException, SQLException
    {
        List lst;
        lst = manager.getReportDataLoad(formBeanToEntityBean(formBean));
        JSONParser jsonParser;
        jsonParser = new JSONParser();
        String sidx;
        sidx = formBean.getSidx();
        int page;
        page = formBean.getPage();
        int rows;
        rows = formBean.getRows();

        return jsonParser.parse(lst, sidx, page, rows);

    }

    public String getReqStatus(String masterPK) throws ClassNotFoundException, SQLException
    {
        return manager.getReqStatus(masterPK);
    }
}