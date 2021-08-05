/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.finlogic.apps.finstudio.service;

import com.finlogic.apps.finstudio.formbean.MasterGeneratorFormBean;
import com.finlogic.business.finstudio.entitybean.MasterGeneratorEntityBean;
import com.finlogic.business.finstudio.model.MasterGeneratorModel;
import com.finlogic.util.FolderZipper;
import com.finlogic.util.mastergenerator.MasterGenerator;
import java.util.List;
import java.util.ArrayList;
/**
 *
 * @author njuser
 */
public class MasterGeneratorService {
    public String validate(MasterGeneratorFormBean formBean)
    {
        MasterGeneratorModel mgm = new MasterGeneratorModel();
        return mgm.validate(formBean);
    }
    public List getTableNames(MasterGeneratorFormBean formBean) throws Exception
    {   
        MasterGeneratorModel mgm = new MasterGeneratorModel();
        return mgm.getTableNames(formBean);
    }
    public ArrayList<String> getColumnNamesOfTable(MasterGeneratorFormBean formBean) throws Exception
    {
        MasterGeneratorModel mgm = new MasterGeneratorModel();
        return mgm.getColumnNamesOfTable(formBean);
    }
    public long generateMaster(MasterGeneratorFormBean formBean) throws Exception
    {
        MasterGeneratorModel mgm = new MasterGeneratorModel();
        MasterGenerator mg = new MasterGenerator();
        ArrayList<String> allColumns = mgm.getColumnNamesOfTable(formBean);
        ArrayList<String> selectedColumns = mgm.getSelectedColumns(formBean,allColumns);
        ArrayList<String> selectedControls = mgm.getSelectedControls(formBean);
        
        MasterGeneratorEntityBean entityBean = formBeanToEntityBean(formBean);
        formBean.setSerialNo(mgm.insertRecord(entityBean));
        entityBean.setSerialNo(formBean.getSerialNo());
      
        String path = mg.generateMaster(formBean,allColumns,selectedColumns,selectedControls);
        new FolderZipper(path,path+".zip");
        return formBean.getSerialNo();
    }
    public MasterGeneratorEntityBean formBeanToEntityBean(MasterGeneratorFormBean formBean) throws Exception
    {
        MasterGeneratorEntityBean entityBean = new MasterGeneratorEntityBean();
        entityBean.setProjectName(formBean.getProjectName());
        entityBean.setModuleName(formBean.getModuleName());
        entityBean.setAliasName(formBean.getAliasName());
        entityBean.setDatabaseType(formBean.getDatabaseType());
        entityBean.setSelectTableName(formBean.getSelectTableName());
        entityBean.setUserName(formBean.getUserName());
        entityBean.setPrimarykey(formBean.getPrimarykey());
        return entityBean;
    }
}
