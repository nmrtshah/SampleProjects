package com.finlogic.apps.finstudio.finstudiostatistics;

import com.dhtmlx.xml2excel.ExcelParam;
import com.dhtmlx.xml2excel.ExcelParam.EXCELParameter;
import com.dhtmlx.xml2pdf.PDFParam;
import com.dhtmlx.xml2pdf.PDFParam.PDFParameter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 *
 * @author Sonam Patel
 */
public class FinstudiostatisticsController extends MultiActionController
{

    private final FinstudiostatisticsService service = new FinstudiostatisticsService();

    public ModelAndView getMenu(final HttpServletRequest request, final HttpServletResponse response) throws SQLException, ClassNotFoundException
    {
        ModelAndView mav = new ModelAndView();
        String finlibPath = finpack.FinPack.getProperty("finlib_path");
        mav.addObject("groupnameList", service.getgroupnameList());
        mav.addObject("finlibPath", finlibPath);
        mav.addObject("process", "getmenu");
        mav.setViewName("finstudiostatistics/Report_menu");
        return mav;
    }

    public ModelAndView getReportGrid(final HttpServletRequest request, final HttpServletResponse response, final FinstudiostatisticsFormBean formBean)
    {
        ModelAndView mav = new ModelAndView();
        try
        {
            mav.addObject("process", "getreportGrid");
            mav.addObject("json", service.getReportData(formBean));
            mav.setViewName("finstudiostatistics/Report_menu");
        }
        catch (Exception e)
        {
            mav.addObject("error", e.getMessage());
            mav.setViewName("finstudiostatistics/Error");
            finutils.errorhandler.ErrorHandler.PrintInFile(e, request);
        }
        return mav;
    }

    public void generatePDF(HttpServletRequest req, HttpServletResponse res, FinstudiostatisticsFormBean formBean) throws Exception, Throwable
    {
        try
        {
            PDFParam ppObj = new PDFParam();
            Map paramMap = new HashMap();
            String reportTitle = formBean.getReportName();
            paramMap.put(PDFParameter.FILENAME.getPdfParam(), formBean.getReportName());
            paramMap.put(PDFParameter.DISPLAYMODE.getPdfParam(), formBean.getDisplayMode());
            paramMap.put(PDFParameter.REPORTTITLE.getPdfParam(), reportTitle);
//            paramMap.put(PDFParameter.HEADERIMG.getPdfParam(), "/home/njuser/Desktop/njtechnologies.jpg");
//            paramMap.put(PDFParameter.FOOTERIMG.getPdfParam(), "/home/njuser/Desktop/njtechnologies.jpg");
            paramMap.put(PDFParameter.GRIDXML.getPdfParam(), req.getParameter("grid_xml") == null ? "" : req.getParameter("grid_xml"));
            paramMap.put(PDFParameter.HTTPRESPONSE.getPdfParam(), res);
            ppObj.generatePDF(paramMap);
        }
        catch (Exception e)
        {
            finutils.errorhandler.ErrorHandler.PrintInFile(e, req);
        }
    }

    public void generateExcel(HttpServletRequest req, HttpServletResponse res, FinstudiostatisticsFormBean formBean) throws Exception
    {
        try
        {
            ExcelParam epObj = new ExcelParam();
            Map paramMap = new HashMap();
            paramMap.put(EXCELParameter.FILENAME.getExcelParam(), formBean.getReportName());
            paramMap.put(EXCELParameter.GRIDXML.getExcelParam(), req.getParameter("grid_xml"));
            paramMap.put(EXCELParameter.HTTPRESPONSE.getExcelParam(), res);
            epObj.generateExcel(paramMap);
        }
        catch (Exception e)
        {
            finutils.errorhandler.ErrorHandler.PrintInFile(e, req);
        }
    }
}
