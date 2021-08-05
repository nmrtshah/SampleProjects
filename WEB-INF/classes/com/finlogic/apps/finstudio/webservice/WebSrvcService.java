/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.apps.finstudio.webservice;

import com.finlogic.business.finstudio.webservice.WebServiceEntityBean;
import com.finlogic.business.finstudio.webservice.WebServiceManager;
import com.finlogic.util.DirectoryService;
import com.finlogic.util.webservice.FileService;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Sonam Patel
 */
public class WebSrvcService
{

    private final WebServiceManager manager = new WebServiceManager();

    public WebServiceEntityBean formBeanToEntityBean(final WebServiceFormBean formBean)
    {
        WebServiceEntityBean entityBean;
        entityBean = new WebServiceEntityBean();
        entityBean.setProjectName(formBean.getCmbProjectName());
        entityBean.setModuleName(formBean.getTxtModuleName());
        if (formBean.getInterfaceFile() == null)
        {
            entityBean.setInterfaceName("");
        }
        else
        {
            entityBean.setInterfaceName(formBean.getInterfaceFile().getOriginalFilename());
        }
        entityBean.setRequestNo(formBean.getTxtReqNo());
        entityBean.setEmpCode(formBean.getEmpCode());
        return entityBean;
    }

    public SqlRowSet getProjectArray() throws ClassNotFoundException, SQLException
    {
        return manager.getProjectArray();
    }

    public String isInterface(final String infcPath) throws IOException
    {
        return FileService.isInterface(infcPath);
    }

    public String uploadInterface(final WebServiceFormBean formBean, final int srno) throws IOException
    {
        MultipartFile file;
        file = formBean.getInterfaceFile();
        String strDestFileName = "";

        if (file != null && !file.getOriginalFilename().equals(""))
        {
            formBean.setIntrfcName(file.getOriginalFilename());
            strDestFileName = WebServiceFormBean.getWSLOC() + "/" + srno + "/";
            File destFile;
            destFile = new File(strDestFileName);
            destFile.mkdirs();
            strDestFileName = strDestFileName + file.getOriginalFilename();
            file.transferTo(new File(strDestFileName));
        }
        return strDestFileName;
    }

    public int insertIntoDataBase(final WebServiceFormBean formBean) throws ClassNotFoundException, SQLException
    {
        return manager.insertIntoDataBase(formBeanToEntityBean(formBean));
    }

    public List getBeanList(final String beans)
    {
        List<String> ls = new ArrayList<String>();
        String[] bean = beans.split(",");
        for (int i = 0; i < bean.length; i++)
        {
            if (!"".equals(bean[i].replace("class ", "")))
            {
                ls.add(bean[i].replace("class ", "").trim());
            }
        }
        return ls;
    }

    public WebServiceFormBean setFormBean(final String srno) throws ClassNotFoundException, SQLException
    {
        WebServiceFormBean formBean;
        formBean = new WebServiceFormBean();
        SqlRowSet srs;
        srs = manager.fetchRecord(srno);
        if (srs.next())
        {
            formBean.setTxtSrno(srs.getString("SRNO"));
            formBean.setCmbProjectName(srs.getString("PROJECT_NAME"));
            formBean.setTxtModuleName(srs.getString("MODULE_NAME"));
            formBean.setIntrfcName(srs.getString("INTERFACE_NAME"));
        }
        return formBean;
    }

    public void deleteRecord(final String srno) throws ClassNotFoundException, SQLException
    {
        manager.deleteRecord(srno);
    }

    public void deleteSRNOfromUpload(final String srno) throws IOException
    {
        DirectoryService dirSrvc;
        dirSrvc = new DirectoryService();
        String path;
        path = WebServiceFormBean.getWSLOC() + "/" + srno;
        dirSrvc.deleteFolder(path);
    }

    public void deleteExtraFiles(final String srno) throws IOException
    {
        DirectoryService dirSrvc;
        dirSrvc = new DirectoryService();
        String path;

        //Delete SRNOtmp From 'upload/ws', If Exist
        path = WebServiceFormBean.getWSLOC() + "/" + srno + "tmp";
        dirSrvc.deleteFolder(path);

        //Delete Compiled Files From 'classes', If Exist
        path = WebServiceFormBean.getTOMCAT() + "/webapps/finstudio/WEB-INF/classes/";
        FileService.deleteClassFiles(path);

        //Delete SRNO From 'generated', If Exist
        path = WebServiceFormBean.getTOMCAT() + "/webapps/finstudio/generated/" + srno + "WS";
        dirSrvc.deleteFolder(path);
    }
}
