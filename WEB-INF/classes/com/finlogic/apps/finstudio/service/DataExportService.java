/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.finlogic.apps.finstudio.service;

import com.csvreader.CsvWriter.FinalizedException;
import com.finlogic.apps.finstudio.formbean.DataExportFormbean;
import com.finlogic.business.finstudio.model.DataExportModel;
import com.svcon.jdbf.JDBFException;
import java.io.IOException;
import jxl.write.WriteException;

/**
 *
 * @author njuser
 */
public class DataExportService
{
    public String submitQuery(DataExportFormbean defb) throws FinalizedException, IOException, JDBFException, WriteException
    {
        return new DataExportModel().submitQuery(defb);
    }
}
