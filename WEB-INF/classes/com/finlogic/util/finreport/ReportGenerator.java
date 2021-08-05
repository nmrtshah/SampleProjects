/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.finreport;

import com.finlogic.business.finstudio.finreport.FinReportDetailEntityBean;
import com.finlogic.business.finstudio.finreport.FinReportSummaryEntityBean;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

/**
 *
 * @author njuser
 */
public class ReportGenerator
{

    public String generateReportFiles(final FinReportDetailEntityBean dEntityBean, final FinReportSummaryEntityBean sEntityBean) throws ClassNotFoundException, FileNotFoundException, SQLException, UnsupportedEncodingException
    {
        String tomcatPath = finpack.FinPack.getProperty("tomcat1_path");
        //service class generator
        ReportServiceGenerator rsg = new ReportServiceGenerator();
        rsg.generateReportService(dEntityBean, sEntityBean);
        //controller class generator
        ReportControllerGenerator rcg = new ReportControllerGenerator();
        rcg.generateReportController(dEntityBean, sEntityBean);
        //formbean class generator
        ReportFormBeanGenerator rfbg = new ReportFormBeanGenerator();
        rfbg.generateReportFormBean(dEntityBean, sEntityBean);
        //header jsp generator
        if (dEntityBean.isHeaderReq())
        {
            HeaderJSPGenerator hjspg = new HeaderJSPGenerator();
            hjspg.generateHeaderJSP(dEntityBean, sEntityBean);
        }
        //footer jsp generator
        if (dEntityBean.isFooterReq())
        {
            BottomJspGenerator bjg = new BottomJspGenerator();
            bjg.generateBottomJsp(dEntityBean, sEntityBean);
        }
        //js file generator
        ReportJSGenerator rjsg = new ReportJSGenerator();
        rjsg.generateReportJS(dEntityBean, sEntityBean);
        //main jsp generator
        ReportJSPGenerator rjspg = new ReportJSPGenerator();
        rjspg.generateReportJSP(dEntityBean, sEntityBean);
        //error jsp generator
        ReportErrorGenerator reg = new ReportErrorGenerator();
        reg.generateReportError(dEntityBean, sEntityBean);

        if (dEntityBean.isPdf() || dEntityBean.isExcel() || dEntityBean.isHtml())
        {
            boolean chart = false;
            ReportJrxmlGenerator rjg = new ReportJrxmlGenerator();
            rjg.generateReportJrxml(dEntityBean, sEntityBean);
            if (dEntityBean.isPieChart() || dEntityBean.isBarChart() || dEntityBean.isSymbolLineChart() || dEntityBean.isThreedLineChart())
            {
                chart = true;
            }
            if (sEntityBean != null && !sEntityBean.getDetailRefNo().equals(""))
            {
                SummaryReportJrxmlGenerator srjg = new SummaryReportJrxmlGenerator();
                srjg.generateSummaryReportJrxml(dEntityBean.getProjectName(), dEntityBean.getModuleName(), dEntityBean.getSRNo(), chart, sEntityBean);
            }
        }
        //report class generator
        ReportClassGenerator classGenerator = new ReportClassGenerator();
        classGenerator.generateReportClass(dEntityBean, sEntityBean);
        
        ReadMeGenerator rmg = new ReadMeGenerator();
        rmg.generateReadMe(dEntityBean, sEntityBean);
        return tomcatPath + "/webapps/finstudio/generated/" + dEntityBean.getSRNo() + "RGV2";
    }
}
