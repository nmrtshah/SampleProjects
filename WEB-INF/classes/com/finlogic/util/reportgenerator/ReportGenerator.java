/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.reportgenerator;

import com.finlogic.apps.finstudio.formbean.ReportGeneratorFormBean;
import java.util.ArrayList;

/**
 *
 * @author njuser
 */
public class ReportGenerator
{
    public String generateReportFiles(ReportGeneratorFormBean reportGeneratorForm,ArrayList<String> columnNames,ArrayList<String> columnTypes) throws Exception
    {
        String tomcatPath = finpack.FinPack.getProperty("tomcat1_path"); 
        
        new DataManagerGenerator(reportGeneratorForm);
        new ModelGenerator(reportGeneratorForm);
        new ServiceGenerator(reportGeneratorForm);
        new ControllerGenerator(reportGeneratorForm);
        new JSPGenerator(reportGeneratorForm,columnNames,columnTypes);
        new FormBeanGenerator(reportGeneratorForm);
        if((reportGeneratorForm.isPdf()==true)||(reportGeneratorForm.isXls()==true))
        {
            new JrxmlGenerator(reportGeneratorForm,columnNames,columnTypes);
        }
        
        return tomcatPath+"/webapps/finstudio/generated/"+reportGeneratorForm.getSerialNo();
    }
}
