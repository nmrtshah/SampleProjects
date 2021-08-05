/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.findhtmlreport;

import com.finlogic.business.finstudio.findhtmlreport.FinDhtmlReportDetailEntityBean;
import com.finlogic.business.finstudio.findhtmlreport.FinDhtmlReportSummaryEntityBean;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

/**
 *
 * @author njuser
 */
public class FinDhtmlReportGenerator
{

    public String generateReportFiles(final FinDhtmlReportDetailEntityBean dEntityBean, final FinDhtmlReportSummaryEntityBean sEntityBean) throws ClassNotFoundException, FileNotFoundException, SQLException, UnsupportedEncodingException
    {
        String tomcatPath = finpack.FinPack.getProperty("tomcat1_path");
        //service class generator
        FinDhtmlReportServiceGenerator rsg = new FinDhtmlReportServiceGenerator();
        rsg.generateReportService(dEntityBean, sEntityBean);
        //controller class generator
        FinDhtmlReportControllerGenerator rcg = new FinDhtmlReportControllerGenerator();
        rcg.generateReportController(dEntityBean, sEntityBean);
        //formbean class generator
        FinDhtmlReportFormBeanGenerator rfbg = new FinDhtmlReportFormBeanGenerator();
        rfbg.generateReportFormBean(dEntityBean, sEntityBean);
        //header jsp generator
        if (dEntityBean.isHeaderReq())
        {
            FinDhtmlReportHeaderGenerator hjspg = new FinDhtmlReportHeaderGenerator();
            hjspg.generateHeaderJSP(dEntityBean, sEntityBean);
        }
        //footer jsp generator
        if (dEntityBean.isFooterReq())
        {
            FinDhtmlReportBottomGenerator bjg = new FinDhtmlReportBottomGenerator();
            bjg.generateBottomJsp(dEntityBean, sEntityBean);
        }
        //js file generator
        FinDhtmlReportJSGenerator rjsg = new FinDhtmlReportJSGenerator();
        rjsg.generateReportJS(dEntityBean, sEntityBean);
        //main jsp generator
        FinDhtmlReportJSPGenerator rjspg = new FinDhtmlReportJSPGenerator();
        rjspg.generateReportJSP(dEntityBean, sEntityBean);
        //error jsp generator
        FinDhtmlReportErrorGenerator reg = new FinDhtmlReportErrorGenerator();
        reg.generateReportError(dEntityBean, sEntityBean);        
        //report class generator
        FinDhtmlReportClassGenerator classGenerator = new FinDhtmlReportClassGenerator();
        classGenerator.generateReportClass(dEntityBean, sEntityBean);

        FinDhtmlReportReadMeGenerator rmg = new FinDhtmlReportReadMeGenerator();
        rmg.generateReadMe(dEntityBean, sEntityBean);
        return tomcatPath + "/webapps/finstudio/generated/" + dEntityBean.getSRNo() + "RGV2";
    }
}
