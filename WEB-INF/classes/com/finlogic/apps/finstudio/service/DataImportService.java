/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.apps.finstudio.service;

import com.finlogic.apps.finstudio.formbean.DataImportFormBean;
import com.finlogic.business.finstudio.model.DataImportModel;
import java.util.List;

/**
 *
 * @author njuser
 */
public class DataImportService
{
    public List getTables(DataImportFormBean data_import_frmbn) throws Exception
    {
        DataImportModel md = new DataImportModel();
        return md.getTables(data_import_frmbn);
    }

    public List getColumn(DataImportFormBean data_import_frmbn) throws Exception
    {
        DataImportModel md = new DataImportModel();
        return md.getColumn(data_import_frmbn);
    }
    public String importData(DataImportFormBean data_import_frmbn,String filenm,String hdr) throws Exception
    {
        DataImportModel md = new DataImportModel();
        return md.importData(data_import_frmbn, filenm, hdr);
    }
}
