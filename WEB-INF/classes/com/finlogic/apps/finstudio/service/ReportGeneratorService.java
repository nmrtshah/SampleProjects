/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.apps.finstudio.service;


import com.finlogic.apps.finstudio.formbean.ReportGeneratorFormBean;
import com.finlogic.business.finstudio.entitybean.ReportGeneratorDetailEntityBean;
import com.finlogic.business.finstudio.entitybean.ReportGeneratorEntityBean;
import com.finlogic.business.finstudio.model.ReportGeneratorModel;
import com.finlogic.util.reportgenerator.ReportGenerator;
import com.finlogic.util.FolderZipper;
import java.util.ArrayList;

/**
 *
 * @author njuser
 */
public class ReportGeneratorService
{
    public long insertReportGenerator(ReportGeneratorFormBean reportGeneratorForm) throws Exception
    {
        ReportGeneratorEntityBean rgEntity=formBeanToEntityBeanMaster(reportGeneratorForm);
        ReportGeneratorModel reportGeneratorModel = new ReportGeneratorModel();
        reportGeneratorModel.insert(rgEntity);
        reportGeneratorForm.setSerialNo(rgEntity.getSerialNo());
        if(reportGeneratorForm.isAddControl()==true)
        {
            ReportGeneratorDetailEntityBean[] rgdEntity=formBeanToEntityBeanDetail(reportGeneratorForm,rgEntity.getSerialNo());
            reportGeneratorModel.insertRGD(rgdEntity);
        }
        
        ArrayList<String> columnNames = getColumnNames(reportGeneratorForm);
        ArrayList<String> columnTypes = getColumnTypes(reportGeneratorForm);
        String path = new ReportGenerator().generateReportFiles(reportGeneratorForm, columnNames, columnTypes);

        new FolderZipper(path,path+".zip");

        return rgEntity.getSerialNo();

    }

    public  ArrayList<String> getColumnNames(ReportGeneratorFormBean reportGeneratorForm) throws Exception
    {
        ReportGeneratorModel reportGeneratorModel = new ReportGeneratorModel();
        return reportGeneratorModel.getColumnNames(reportGeneratorForm);
    }
    public  ArrayList<String> getColumnTypes(ReportGeneratorFormBean reportGeneratorForm) throws Exception
    {
        ReportGeneratorModel reportGeneratorModel = new ReportGeneratorModel();
        ArrayList<String> columnTypes = reportGeneratorModel.getColumnTypes(reportGeneratorForm);
        return columnTypes;
    }
    public ReportGeneratorEntityBean formBeanToEntityBeanMaster(ReportGeneratorFormBean rgForm) throws Exception
    {
        ReportGeneratorEntityBean rgEntity = new ReportGeneratorEntityBean();

        rgEntity.setAliasName(rgForm.getAliasName());
        rgEntity.setModuleName(rgForm.getModuleName());
        rgEntity.setQuery(rgForm.getQuery());
        rgEntity.setUserName(rgForm.getUserName());
        
        rgEntity.setGrid(rgForm.isGrid());
        if(rgForm.isGrid()==true){
            rgEntity.setGridColumnPK(rgForm.getGridColumnPK());
            rgEntity.setPaging(rgForm.isPaging());
            if(rgForm.isPaging()==true)
                rgEntity.setRecordCountQuery(rgForm.getRecordCountQuery());
            else
                rgEntity.setRecordCountQuery(null);
            rgEntity.setAddControl(rgForm.isAddControl());
        }
        else
        {
            rgEntity.setGridColumnPK(null);
            rgEntity.setRecordCountQuery(null);
        }

        rgEntity.setPdf(rgForm.isPdf());

        rgEntity.setXls(rgForm.isXls());

        rgEntity.setGrouping(rgForm.getGrouping());

        rgEntity.setGroupFooter(rgForm.isGroupFooter());
        
        return rgEntity;
    }
    public ReportGeneratorDetailEntityBean[] formBeanToEntityBeanDetail(ReportGeneratorFormBean rgForm,Long serialNo) throws Exception
    {
        String indexNumber[]=rgForm.getIndexNumber();
        String selectColumn[]=rgForm.getSelectColumn();
        String selectControl[]=rgForm.getSelectControl();
        ReportGeneratorDetailEntityBean[] rgdEntity = new ReportGeneratorDetailEntityBean[selectControl.length];

        for(int i=0;i<selectControl.length;i++)
        {
            rgdEntity[i]=new ReportGeneratorDetailEntityBean();
            rgdEntity[i].setRgSerialNo(serialNo);
            rgdEntity[i].setControlField(selectColumn[i]);
            rgdEntity[i].setControlName(selectControl[i]);
            rgdEntity[i].setControlIndex(indexNumber[i]);
        }
        return rgdEntity;
    }
    public String validate(ReportGeneratorFormBean formBean)
    {
        ReportGeneratorModel reportGeneratorModel = new ReportGeneratorModel();
        return reportGeneratorModel.validate(formBean);
    }
}
