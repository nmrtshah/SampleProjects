/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.finlogic.apps.finstudio.service;

import com.finlogic.apps.finstudio.formbean.MasterGeneratorV2FormBean;
import com.finlogic.business.finstudio.entitybean.MasterGeneratorV2EntityBean;
import com.finlogic.business.finstudio.model.MasterGeneratorV2Model;
import com.finlogic.util.FolderZipper;
import com.finlogic.util.mastergeneratorv2.MasterGeneratorV2;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author njuser
 */
public class MasterGeneratorV2Service {
    public String validate(MasterGeneratorV2FormBean formBean)
    {
        MasterGeneratorV2Model mgm = new MasterGeneratorV2Model();
        return mgm.validate(formBean);
    }
    public List getTableNames(MasterGeneratorV2FormBean formBean,String tableType) throws Exception
    {   
        MasterGeneratorV2Model mgm = new MasterGeneratorV2Model();
        return mgm.getTableNames(formBean,tableType);
    }
    public ArrayList<String> getColumnNamesOfTable(MasterGeneratorV2FormBean formBean,String tableType) throws Exception
    {
        MasterGeneratorV2Model mgm = new MasterGeneratorV2Model();
        return mgm.getColumnNamesOfTable(formBean,tableType);
    }
    public long generateMaster(MasterGeneratorV2FormBean formBean) throws Exception
    {
        MasterGeneratorV2Model mgm = new MasterGeneratorV2Model();
        ArrayList<String> masterColumns = mgm.getColumnNamesOfTable(formBean,"master");
        ArrayList<String> detailColumns = mgm.getColumnNamesOfTable(formBean,"detail");
        formBean.setMasterColumns(masterColumns);
        formBean.setDetailColumns(detailColumns);
        formBean.setSelectedMasterColumns(mgm.getSelectedColumns(formBean,"master"));
        formBean.setSelectedDetailColumns(mgm.getSelectedColumns(formBean,"detail"));
        formBean.setSelectedMasterControls(mgm.getSelectedControls(formBean,"master"));
        formBean.setSelectedDetailControls(mgm.getSelectedControls(formBean,"detail"));

        MasterGeneratorV2EntityBean entityBean = formBeanToEntityBean(formBean);
        formBean.setSerialNo(mgm.insertRecord(entityBean));
        
        MasterGeneratorV2 mg = new MasterGeneratorV2();
        String path = mg.generateMaster(formBean);
        new FolderZipper(path,path+".zip");
        
        return formBean.getSerialNo();
    }

    public MasterGeneratorV2EntityBean formBeanToEntityBean(MasterGeneratorV2FormBean formBean) throws Exception
    {
        MasterGeneratorV2EntityBean entityBean = new MasterGeneratorV2EntityBean();
        entityBean.setProjectName(formBean.getProjectName());
        entityBean.setModuleName(formBean.getModuleName());
        entityBean.setAliasName(formBean.getAliasName());
        entityBean.setUserName(formBean.getUserName());
        entityBean.setDatabaseType(formBean.getDatabaseType());
        entityBean.setSelectMasterTableName(formBean.getSelectMasterTableName());
        entityBean.setSelectDetailTableName(formBean.getSelectDetailTableName());
        entityBean.setPrimarykeyMaster(formBean.getPrimarykeyMaster());
        entityBean.setPrimarykeyDetail(formBean.getPrimarykeyDetail());
        entityBean.setForeignkeyDetail(formBean.getForeignkeyDetail());

        return entityBean;
    }
}
