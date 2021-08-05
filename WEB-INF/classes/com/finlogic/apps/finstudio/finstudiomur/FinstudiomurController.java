package com.finlogic.apps.finstudio.finstudiomur;

import com.dhtmlx.xml2excel.ExcelParam;
import com.dhtmlx.xml2pdf.PDFParam;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class FinstudiomurController extends MultiActionController
{

    public ModelAndView getMenu(HttpServletRequest request, HttpServletResponse response)
    {
        ModelAndView mv = new ModelAndView();
        try
        {
            String finlibPath = finpack.FinPack.getProperty("finlib_path");
            mv.addObject("finlibPath", finlibPath);
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date date = new Date();
            String dateString = dateFormat.format(date);
            mv.addObject("dateString", dateString);
            mv.addObject("process", "getmenu");
            mv.setViewName("finstudiomur/main");
        }
        catch (Exception e)
        {
            mv.addObject("error", e.getMessage());
            mv.setViewName("finstudiomur/error");
        }
        return mv;
    }

    public ModelAndView getReportGrid(HttpServletRequest request, HttpServletResponse response, FinstudiomurFormBean formBean)
    {
        ModelAndView mv = new ModelAndView();
        try
        {
            FinstudiomurService service = new FinstudiomurService();
            mv.addObject("process", "getreportGrid");
            mv.addObject("json", service.getDataGrid(formBean));
            mv.setViewName("finstudiomur/report");
        }
        catch (Exception e)
        {
            mv.addObject("error", e.getMessage());
            mv.setViewName("finstudiomur/error");
        }
        return mv;
    }

    public void generatePDF(HttpServletRequest req, HttpServletResponse res, FinstudiomurFormBean formBean) throws Exception, Throwable
    {
        try
        {
            PDFParam ppObj = new PDFParam();
            Map paramMap = new HashMap();
            String reportTitle = formBean.getReportName();
            String tomcat_path = finpack.FinPack.getProperty("tomcat1_path");
            
            paramMap.put(PDFParam.PDFParameter.FILENAME.getPdfParam(), formBean.getReportName());
            paramMap.put(PDFParam.PDFParameter.DISPLAYMODE.getPdfParam(), formBean.getDisplayMode());
            paramMap.put(PDFParam.PDFParameter.REPORTTITLE.getPdfParam(), reportTitle);
            paramMap.put(PDFParam.PDFParameter.HEADERIMG.getPdfParam(), tomcat_path + "/finstudio/images/header.jpg");
            paramMap.put(PDFParam.PDFParameter.FOOTERIMG.getPdfParam(), tomcat_path + "/finstudio/images/footer.jpg");
            paramMap.put(PDFParam.PDFParameter.GRIDXML.getPdfParam(), req.getParameter("grid_xml") == null ? "" : req.getParameter("grid_xml"));
            paramMap.put(PDFParam.PDFParameter.HTTPRESPONSE.getPdfParam(), res);
            ppObj.generatePDF(paramMap);
        }
        catch (Exception e)
        {
            finutils.errorhandler.ErrorHandler.PrintInFile(e, req);
        }
    }

    public void generateExcel(HttpServletRequest req, HttpServletResponse res, FinstudiomurFormBean formBean) throws Exception
    {
        try
        {
            ExcelParam epObj = new ExcelParam();
            Map paramMap = new HashMap();
            paramMap.put(ExcelParam.EXCELParameter.FILENAME.getExcelParam(), formBean.getReportName());
            paramMap.put(ExcelParam.EXCELParameter.GRIDXML.getExcelParam(), req.getParameter("grid_xml"));
            paramMap.put(ExcelParam.EXCELParameter.HTTPRESPONSE.getExcelParam(), res);
            epObj.generateExcel(paramMap);
        }
        catch (Exception e)
        {
            finutils.errorhandler.ErrorHandler.PrintInFile(e, req);
        }
    }   
}
