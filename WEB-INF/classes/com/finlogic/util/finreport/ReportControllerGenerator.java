/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.finreport;

import com.finlogic.business.finstudio.finreport.FinReportDetailEntityBean;
import com.finlogic.business.finstudio.finreport.FinReportSummaryEntityBean;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Locale;

/**
 *
 * @author njuser
 */
public class ReportControllerGenerator
{

    private String tomcatPath = finpack.FinPack.getProperty("tomcat1_path");

    public void generateReportController(final FinReportDetailEntityBean dEntityBean, final FinReportSummaryEntityBean sEntityBean) throws FileNotFoundException
    {
        String projectName = dEntityBean.getProjectName();
        String moduleName = Character.toUpperCase(dEntityBean.getModuleName().charAt(0)) + dEntityBean.getModuleName().substring(1).toLowerCase(Locale.getDefault());

        String number = dEntityBean.getSRNo() + "RGV2";
        String filePath = tomcatPath + "/webapps/finstudio/generated/" + number + "/";
        String projectPath = filePath + projectName.toLowerCase(Locale.getDefault()) + "/WEB-INF/classes/com/finlogic/apps/"
                + projectName.toLowerCase(Locale.getDefault()) + "/" + moduleName.toLowerCase(Locale.getDefault()) + "/";

        File file = new File(projectPath);
        file.mkdirs();
        PrintWriter pw = new PrintWriter(projectPath + moduleName + "Controller.java");

        pw.println("package com.finlogic.apps." + projectName.toLowerCase(Locale.getDefault()) + "." + moduleName.toLowerCase(Locale.getDefault()) + ";");
        pw.println();

        pw.println("import javax.servlet.http.HttpServletRequest;");
        pw.println("import javax.servlet.http.HttpServletResponse;");
        pw.println("import org.springframework.web.servlet.ModelAndView;");
        pw.println("import java.util.Locale;");
        pw.println("import java.sql.SQLException;");
//        if (dEntityBean.isHeaderReq())
//        {
//            pw.println("import java.util.Calendar;");
//        }
        if (dEntityBean.isDateTimePicker())
        {
            pw.println("import java.text.DateFormat;");
            pw.println("import java.text.SimpleDateFormat;");
            pw.println("import java.util.Date;");
        }
        if (dEntityBean.isPdf() || dEntityBean.isExcel() || dEntityBean.isHtml())
        {
            pw.println("import java.io.BufferedInputStream;");
            pw.println("import java.io.BufferedOutputStream;");
            pw.println("import java.io.FileInputStream;");
            pw.println("import javax.servlet.ServletException;");
            pw.println("import javax.servlet.ServletOutputStream;");
            pw.println("import net.sf.jasperreports.engine.JRExporter;");
            pw.println("import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;");
            pw.println("import net.sf.jasperreports.engine.JRExporterParameter;");
            pw.println("import net.sf.jasperreports.engine.JasperFillManager;");
            pw.println("import net.sf.jasperreports.engine.JasperPrint;");
            pw.println("import net.sf.jasperreports.engine.JasperCompileManager;");
            pw.println("import net.sf.jasperreports.engine.JasperReport;");
            if (dEntityBean.isPdf())
            {
                pw.println("import net.sf.jasperreports.engine.export.JRPdfExporter;");
            }
            if (dEntityBean.isExcel())
            {
                pw.println("import net.sf.jasperreports.engine.export.JRXlsExporter;");
                pw.println("import net.sf.jasperreports.engine.export.JRXlsExporterParameter;");
            }
            if (dEntityBean.isHtml())
            {
                pw.println("import net.sf.jasperreports.engine.JasperExportManager;");
            }
        }
        if (dEntityBean.isPieChart() || dEntityBean.isBarChart() || dEntityBean.isSymbolLineChart() || dEntityBean.isThreedLineChart())
        {
            pw.println("import java.util.HashMap;");
            pw.println("import java.util.Map;");
        }
        if (dEntityBean.isPieChart() || dEntityBean.isBarChart() || dEntityBean.isSymbolLineChart() || dEntityBean.isThreedLineChart())
        {
            pw.println("import org.jfree.chart.ChartFactory;");
            pw.println("import org.jfree.chart.ChartUtilities;");
            pw.println("import org.jfree.chart.JFreeChart;");
            pw.println("import java.io.File;");
            pw.println("import java.util.List;");
//            pw.println("import java.util.Map;");
            pw.println("import java.awt.Font;");
            pw.println("import java.io.IOException;");
        }
        if (dEntityBean.isPieChart())
        {
            pw.println("import org.jfree.chart.labels.StandardPieSectionLabelGenerator;");
            pw.println("import org.jfree.chart.plot.PiePlot;");
            pw.println("import org.jfree.data.general.DefaultPieDataset;");
        }
        if (dEntityBean.isBarChart() || dEntityBean.isSymbolLineChart() || dEntityBean.isThreedLineChart())
        {
            pw.println("import java.text.DecimalFormat;");
            pw.println("import org.jfree.chart.labels.CategoryItemLabelGenerator;");
            pw.println("import org.jfree.chart.labels.ItemLabelAnchor;");
            pw.println("import org.jfree.chart.labels.ItemLabelPosition;");
            pw.println("import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;");
            pw.println("import org.jfree.chart.plot.PlotOrientation;");
            pw.println("import org.jfree.data.category.DefaultCategoryDataset;");
            pw.println("import org.jfree.ui.TextAnchor;");
            pw.println("import org.jfree.chart.plot.CategoryPlot;");
            pw.println("import org.jfree.chart.axis.CategoryAxis;");
            pw.println("import org.jfree.chart.axis.ValueAxis;");
            pw.println("import org.jfree.chart.axis.CategoryLabelPositions;");
        }
        if (dEntityBean.isBarChart())
        {
            pw.println("import org.jfree.chart.renderer.category.BarRenderer;");
        }
        if (dEntityBean.isThreedLineChart())
        {
            pw.println("import org.jfree.chart.renderer.category.AreaRenderer;");
        }
        if (dEntityBean.isSymbolLineChart())
        {
            pw.println("import org.jfree.chart.renderer.category.LineAndShapeRenderer;");
        }
        pw.println("import org.springframework.web.servlet.mvc.multiaction.MultiActionController;");
        pw.println();
        pw.println("public class " + moduleName + "Controller extends MultiActionController");
        pw.println("{");
        pw.println("    private final " + moduleName + "Service service = new " + moduleName + "Service();");
        pw.println("    private String tomcatPath = finpack.FinPack.getProperty(\"tomcat1_path\");");
        pw.println();
        pw.println("    public ModelAndView getMenu(final HttpServletRequest request,final HttpServletResponse response) throws SQLException, ClassNotFoundException");
        pw.println("    {");
        pw.println("        ModelAndView mav = new ModelAndView(\"" + moduleName.toLowerCase(Locale.getDefault()) + "/report_menu\");");
        pw.println("        String finlibPath = finpack.FinPack.getProperty(\"finlib_path\");");
        if (dEntityBean.getFltrCmbSource() != null)
        {
            String control[] = dEntityBean.getFltrControl();
            int controlLen = control.length;
            for (int i = 0; i < controlLen; i++)
            {
                if (("ComboBox".equals(control[i]) || "TextLikeCombo".equals(control[i])) && dEntityBean.getFltrCmbSource() != null && ("fltrCmbSrcQuery".equals(dEntityBean.getFltrCmbSource()[i]) || "fltrCmbSrcWS".equals(dEntityBean.getFltrCmbSource()[i])))
                {
                    pw.println("        mav.addObject(\"" + dEntityBean.getFltrTxtName()[i] + "List\", service.get" + dEntityBean.getFltrTxtName()[i] + "List());");
                }
                else if (("ComboBox".equals(control[i]) || "TextLikeCombo".equals(control[i])) && dEntityBean.getFltrCmbSource() != null && "fltrCmbSrcCommonCmb".equals(dEntityBean.getFltrCmbSource()[i]))
                {
                    String str = dEntityBean.getFltrCmbCommonQuery()[i].substring(0, 1).toUpperCase().concat(dEntityBean.getFltrCmbCommonQuery()[i].substring(1));
                    pw.println("        mav.addObject(\"" + dEntityBean.getFltrTxtName()[i] + "List\", service.get" + str + "());");
                }
            }
        }
//        if (dEntityBean.isHeaderReq())
//        {
//            pw.println("        Calendar c = Calendar.getInstance();");
//            pw.println("        String mydate = getMonth(c.get(Calendar.MONTH)) + \" \"");
//            pw.println("            + c.get(Calendar.DAY_OF_MONTH) + \",\"");
//            pw.println("            + c.get(Calendar.YEAR) + \" \"");
//            pw.println("            + c.get(Calendar.HOUR_OF_DAY) + \":\"");
//            pw.println("            + c.get(Calendar.MINUTE) + \":\"");
//            pw.println("            + c.get(Calendar.SECOND);");
//            pw.println("        mav.addObject(\"mydate\", mydate);");
//        }
        if (dEntityBean.isDateTimePicker())
        {
            pw.println("        DateFormat dateFormat = new SimpleDateFormat(\"dd-MM-yyyy\",Locale.getDefault());");
            pw.println("        Date date = new Date();");
            pw.println("        String dateString = dateFormat.format(date);");
            pw.println("        mav.addObject(\"dateString\", dateString);");
        }
        pw.println("        mav.addObject(\"finlibPath\", finlibPath);");
        pw.println("        mav.addObject(\"process\", \"getmenu\");");
        pw.println("        return mav;");
        pw.println("    }");
        pw.println();

        pw.println("    public ModelAndView getReportGrid(final HttpServletRequest request, final HttpServletResponse response, final  " + moduleName + "FormBean formBean)");
        pw.println("    {");
        pw.println("        ModelAndView mav = new ModelAndView(\"" + moduleName.toLowerCase(Locale.getDefault()) + "/report_menu\");");
        pw.println("        try");
        pw.println("        {");
        pw.println("            mav.addObject(\"process\", \"getreportGrid\");");
        pw.println("            mav.addObject(\"json\", service.getReportData(formBean));");
        pw.println("        }");
        pw.println("        catch (Exception e)");
        pw.println("        {");
        pw.println("            mav.setViewName(\"" + moduleName.toLowerCase(Locale.getDefault()) + "/error\");");
        pw.println("            mav.addObject(\"error\", e.getMessage());");
        pw.println("            finutils.errorhandler.ErrorHandler.PrintInFile(e, request);");
        pw.println("        }");
        pw.println("        return mav;");
        pw.println("    }");
        pw.println();
        //for report file
        if (dEntityBean.isPdf() || dEntityBean.isExcel() || dEntityBean.isHtml())
        {
            pw.println("    public ModelAndView getReportExportFile(final HttpServletRequest request, final HttpServletResponse response, final " + moduleName + "FormBean formBean)");
            pw.println("    {");
            pw.println("        ModelAndView mav = new ModelAndView(\"" + moduleName.toLowerCase(Locale.getDefault()) + "/report_menu\");");
            pw.println("        try");
            pw.println("        {");
            pw.println("            String fileName;");
            if (sEntityBean != null && !sEntityBean.getDetailRefNo().equals(""))
            {
                pw.println("            if (formBean.getRdoReportType().equals(\"detailReport\"))");
                pw.println("            {");
                pw.println("                fileName = tomcatPath + \"/webapps/" + projectName + "/WEB-INF/jrxml/" + moduleName.toLowerCase(Locale.getDefault()) + ".jrxml\";");
                pw.println("            }");
                pw.println("            else");
                pw.println("            {");
                pw.println("                fileName = tomcatPath + \"/webapps/" + projectName + "/WEB-INF/jrxml/" + moduleName.toLowerCase(Locale.getDefault()) + "Summary.jrxml\";");
                pw.println("            }");
            }
            else
            {
                pw.println("            fileName = tomcatPath + \"/webapps/" + projectName + "/WEB-INF/jrxml/" + moduleName.toLowerCase(Locale.getDefault()) + ".jrxml\";");
            }
            pw.println("            JasperReport jasperReport = JasperCompileManager.compileReport(fileName);");
            pw.println("            String outFileName = tomcatPath + \"/webapps/" + projectName + "/\" + formBean.getReportName() + \".\" +  formBean.getRdoRptFormate().toLowerCase(Locale.getDefault());");
            //pw.println("            parameters.put(\"Name\", \"" + dEntityBean.getReportTitle() + "\");");
            if (dEntityBean.isPieChart() || dEntityBean.isBarChart() || dEntityBean.isSymbolLineChart() || dEntityBean.isThreedLineChart())
            {
                pw.println("                Map parameters = new HashMap();");
            }
            if (sEntityBean != null && (dEntityBean.isPieChart() || dEntityBean.isBarChart() || dEntityBean.isSymbolLineChart() || dEntityBean.isThreedLineChart()))
            {
                pw.println("            if (formBean.getRdoReportType().equals(\"detailReport\"))");
                pw.println("            {");
            }
            if (dEntityBean.isPieChart())
            {
                pw.println("                if (\"pie_chart\".equals(formBean.getChart()))");
                pw.println("                {");
                pw.println("                    parameters.put(\"imgPath\", tomcatPath + \"/webapps/" + projectName + "/PieChart.jpg\");");
                pw.println("                }");
            }
            if (dEntityBean.isBarChart())
            {
                pw.println("                if (\"bar_chart\".equals(formBean.getChart()))");
                pw.println("                {");
                pw.println("                    parameters.put(\"imgPath\", tomcatPath + \"/webapps/" + projectName + "/BarChart.jpg\");");
                pw.println("                }");

            }
            if (dEntityBean.isSymbolLineChart())
            {
                pw.println("                if (\"line_chart\".equals(formBean.getChart()))");
                pw.println("                {");
                pw.println("                    parameters.put(\"imgPath\", tomcatPath + \"/webapps/" + projectName + "/LineChart.jpg\");");
                pw.println("                }");
            }
            if (dEntityBean.isThreedLineChart())
            {
                pw.println("                if (\"area_chart\".equals(formBean.getChart()))");
                pw.println("                {");
                pw.println("                    parameters.put(\"imgPath\", tomcatPath + \"/webapps/" + projectName + "/AreaChart.jpg\");");
                pw.println("                }");
            }
            if (sEntityBean != null && (dEntityBean.isPieChart() || dEntityBean.isBarChart() || dEntityBean.isSymbolLineChart() || dEntityBean.isThreedLineChart()))
            {
                pw.println("            }");
            }
            pw.println("            JRBeanCollectionDataSource dataSource;");
            if (sEntityBean != null && !sEntityBean.getDetailRefNo().equals(""))
            {
                pw.println("            if (formBean.getRdoReportType().equals(\"detailReport\"))");
                pw.println("            {");
                pw.println("                dataSource = new JRBeanCollectionDataSource(service.getDetailExportData(formBean));");
                pw.println("            }");
                pw.println("            else");
                pw.println("            {");
                pw.println("                dataSource = new JRBeanCollectionDataSource(service.getSummaryExportData(formBean));");
                pw.println("            }");
            }
            else
            {
                pw.println("            dataSource = new JRBeanCollectionDataSource(service.getDetailExportData(formBean));");
            }
            if (dEntityBean.isPieChart() || dEntityBean.isBarChart() || dEntityBean.isSymbolLineChart() || dEntityBean.isThreedLineChart())
            {
                pw.println("            JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, dataSource);");
            }
            else
            {
                pw.println("            JasperPrint print = JasperFillManager.fillReport(jasperReport, null, dataSource);");
            }

            //pw.println("            JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, dataSource);");
            if (dEntityBean.isPdf() || dEntityBean.isExcel())
            {
                pw.println("            JRExporter exporter = null;");
            }
            if (dEntityBean.isPdf())
            {
                pw.println("            if (formBean.getRdoRptFormate().equals(\"Pdf\"))");
                pw.println("            {");
                pw.println("                exporter = new JRPdfExporter();");
                pw.println("            }");
            }
            if (dEntityBean.isExcel())
            {
                pw.println("            if (formBean.getRdoRptFormate().equals(\"XLS\"))");
                pw.println("            {");
                pw.println("                exporter = new JRXlsExporter();");
                pw.println("            }");
            }

            if (dEntityBean.isPdf() || dEntityBean.isExcel())
            {
                if (dEntityBean.isPdf() && dEntityBean.isExcel())
                {
                    pw.println("            if (formBean.getRdoRptFormate().equals(\"Pdf\") || formBean.getRdoRptFormate().equals(\"XLS\"))");
                }
                else
                {
                    if (dEntityBean.isPdf())
                    {
                        pw.println("            if (formBean.getRdoRptFormate().equals(\"Pdf\"))");
                    }
                    else
                    {
                        if (dEntityBean.isExcel())
                        {
                            pw.println("            if (formBean.getRdoRptFormate().equals(\"XLS\"))");
                        }
                    }
                }
                pw.println("            {");
                pw.println("                exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outFileName);");
                pw.println("                exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);");
                if (dEntityBean.isExcel())
                {
                    pw.println("                if (formBean.getRdoRptFormate().equals(\"XLS\"))");
                    pw.println("                {");
                    pw.println("                    exporter.setParameter(JRXlsExporterParameter.IS_COLLAPSE_ROW_SPAN, Boolean.TRUE);");
                    pw.println("                    exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);");
                    pw.println("                    exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);");
                    pw.println("                    exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.FALSE);");
                    pw.println("                    exporter.setParameter(JRXlsExporterParameter.IS_IGNORE_GRAPHICS, Boolean.FALSE);");
                    pw.println("                    exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);");
                    pw.println("                }");
                }

                pw.println("                exporter.exportReport();");
                pw.println("            }");
            }

            if (dEntityBean.isHtml())
            {
                pw.println("            if (formBean.getRdoRptFormate().equals(\"Html\"))");
                pw.println("            {");
                pw.println("                JasperExportManager.exportReportToHtmlFile(print,outFileName);");
                pw.println("                mav.addObject(\"filePath\",formBean.getReportName()+\".html\");");
                pw.println("                mav.addObject(\"process\", \"getHtmlReport\");");
                pw.println("            }");
            }
            ///for pop up
            if (dEntityBean.isPdf() || dEntityBean.isExcel())
            {
                if (dEntityBean.isPdf() && dEntityBean.isExcel())
                {
                    pw.println("            if (formBean.getRdoRptFormate().equals(\"Pdf\") || formBean.getRdoRptFormate().equals(\"XLS\"))");
                }
                else
                {
                    if (dEntityBean.isPdf())
                    {
                        pw.println("            if (formBean.getRdoRptFormate().equals(\"Pdf\"))");

                    }
                    else
                    {
                        if (dEntityBean.isExcel())
                        {
                            pw.println("            if (formBean.getRdoRptFormate().equals(\"XLS\"))");
                        }
                    }
                }
                pw.println("            {");
                pw.println("                ServletOutputStream out1 = response.getOutputStream();");
                if (dEntityBean.isPdf())
                {
                    pw.println("                if (formBean.getRdoRptFormate().equals(\"Pdf\"))");
                    pw.println("                {");
                    pw.println("                    response.setContentType(\"application/pdf\");");
                    pw.println("                }");
                }
                if (dEntityBean.isExcel())
                {
                    pw.println("                if (formBean.getRdoRptFormate().equals(\"XLS\"))");
                    pw.println("                {");
                    pw.println("                    response.setContentType(\"application/vnd.ms-excel\");");
                    pw.println("                }");
                }
                pw.println("                response.setHeader(\"Content-disposition\", \"attachment; filename=\\\"\"+formBean.getReportName()+\".\"+formBean.getRdoRptFormate().toLowerCase(Locale.getDefault())+\"\\\"\");");
                pw.println("                BufferedInputStream bis = null;");
                pw.println("                BufferedOutputStream bos = null;");
                pw.println("                try");
                pw.println("                {");
                pw.println("                    bis = new BufferedInputStream(new FileInputStream(outFileName));");
                pw.println("                    bos = new BufferedOutputStream(out1);");
                pw.println("                    byte[] buff = new byte[2048];");
                pw.println("                    int bytesRead;");
                pw.println("                    while (-1 != (bytesRead = bis.read(buff, 0, buff.length)))");
                pw.println("                    {");
                pw.println("                        bos.write(buff, 0, bytesRead);");
                pw.println("                    }");
                pw.println("                }");
                pw.println("                catch (Exception e)");
                pw.println("                {");
                pw.println("                    finutils.errorhandler.ErrorHandler.PrintInFile(e, request);");
                pw.println("                    throw new ServletException(e);");
                pw.println("                }");
                pw.println("                finally");
                pw.println("                {");
                pw.println("                    if (bis != null)");
                pw.println("                    {");
                pw.println("                        bis.close();");
                pw.println("                    }");
                pw.println("                    if (bos != null)");
                pw.println("                    {");
                pw.println("                        bos.close();");
                pw.println("                    }");
                pw.println("                }");
                pw.println("                out1.close();");
                pw.println("            }");
            }
            pw.println("        }");
            pw.println("        catch (Exception e)");
            pw.println("        { ");
            pw.println("            mav.addObject(\"error\", e.getMessage());");
            pw.println("            mav.setViewName(\"" + moduleName.toLowerCase(Locale.getDefault()) + "/error\");");
            pw.println("            finutils.errorhandler.ErrorHandler.PrintInFile(e, request);");
            pw.println("            return mav;");
            pw.println("        }");
            pw.println("        if (formBean.getRdoRptFormate().equals(\"Pdf\") || formBean.getRdoRptFormate().equals(\"XLS\"))");
            pw.println("        {");
            pw.println("            return null;");
            pw.println("        }");
            pw.println("        else");
            pw.println("        {");
            pw.println("            return mav;");
            pw.println("        }");
            pw.println("    }");
            pw.println();
        }
        //for pie chart
        if (dEntityBean.isPieChart())
        {
            getPieChart(pw, projectName, moduleName, dEntityBean.getPieTxtChartTitle(), dEntityBean.getPieCmbXaxisColumn(), dEntityBean.getPieCmbYaxisColumn(), "Detail");
        }
        //for bar chart
        if (dEntityBean.isBarChart())
        {
            getBarChart(pw, projectName, moduleName, dEntityBean.getBarTxtChartTitle(), dEntityBean.getBarCmbXaxisColumn(), dEntityBean.getBarCmbYaxisColumn(), dEntityBean.getBarTxtXLabel(), dEntityBean.getBarTxtYLabel(), dEntityBean.getBartxtLegendName(), "Detail");
        }
        //for line chart
        if (dEntityBean.isSymbolLineChart())
        {
            getLineChart(pw, projectName, moduleName, dEntityBean.getLineTxtChartTitle(), dEntityBean.getLineCmbXaxisColumn(), dEntityBean.getLineCmbYaxisColumn(), dEntityBean.getLineTxtXLabel(), dEntityBean.getLineTxtYLabel(), dEntityBean.getLinetxtLegendName(), "Detail");
        }
        //for area chart              
        if (dEntityBean.isThreedLineChart())
        {
            getAreaChart(pw, projectName, moduleName, dEntityBean.getAreaTxtChartTitle(), dEntityBean.getAreaCmbXaxisColumn(), dEntityBean.getAreaCmbYaxisColumn(), dEntityBean.getAreaTxtXLabel(), dEntityBean.getAreaTxtYLabel(), dEntityBean.getAreatxtLegendName(), "Detail");
        }
//        if (dEntityBean.isHeaderReq())
//        {
//            pw.println("    public String getMonth(int i)");
//            pw.println("    {");
//            pw.println("        String[] montharray = new String[]");
//            pw.println("        {");
//            pw.println("            \"January\", \"February\", \"March\", \"April\", \"May\", \"June\", \"July\", \"August\", \"September\", \"October\", \"November\", \"December\"");
//            pw.println("        };");
//            pw.println("        return montharray[i];");
//            pw.println("    }");
//            pw.println();
//        }
        pw.println("}");
        pw.close();
    }

    public void getPieChartData(PrintWriter pw, String xColumn, String yColumn)
    {
        pw.println("            for (int i = 0; i < pieData.size(); i++)");
        pw.println("            {");
        pw.println("                Map m = (Map) pieData.get(i);");
        pw.println("                String key;");
        pw.println("                Double val;");
        pw.println("                if (m.get(\"" + xColumn + "\") == null)");
        pw.println("                {");
        pw.println("                    key = \"N/A\";");
        pw.println("                }");
        pw.println("                else");
        pw.println("                {");
        pw.println("                    key = m.get(\"" + xColumn + "\").toString();");
        pw.println("                }");
        pw.println("                if (m.get(\"" + yColumn + "\") == null)");
        pw.println("                {");
        pw.println("                    val = 0.0;");
        pw.println("                }");
        pw.println("                else");
        pw.println("                {");
        pw.println("                    val = Double.parseDouble(m.get(\"" + yColumn + "\").toString());");
        pw.println("                }");
        pw.println("                data.setValue(key,val);");
        pw.println("            }");
    }

    public void getChartData(PrintWriter pw, String xColumn, String[] yColumn, String xLabel, String yLabel, String[] legenedName, String chartTitle, String chartName)
    {
        pw.println("            for (int i = 0; i < chartData.size(); i++)");
        pw.println("            {");
        pw.println("                Map m = (Map) chartData.get(i);");
        pw.println("                String key;");
        pw.println("                if (m.get(\"" + xColumn + "\") == null)");
        pw.println("                {");
        pw.println("                    key = \"N/A\";");
        pw.println("                }");
        pw.println("                else");
        pw.println("                {");
        pw.println("                    key = m.get(\"" + xColumn + "\").toString();");
        pw.println("                }");
        for (int i = 0; i < yColumn.length; i++)
        {
            pw.println("                Double val" + i + ";");
            pw.println("                if (m.get(\"" + yColumn[i] + "\") == null)");
            pw.println("                {");
            pw.println("                    val" + i + " = 0.0;");
            pw.println("                }");
            pw.println("                else");
            pw.println("                {");
            pw.println("                    val" + i + " = Double.parseDouble(m.get(\"" + yColumn[i] + "\").toString());");
            pw.println("                }");
            if (i == 0)
            {
                pw.println("                categoryDataset.setValue(val" + i + ",\"" + yLabel + "\",key);");
            }
            else
            {
                pw.println("                categoryDataset.setValue(val" + i + ",\"" + legenedName[i - 1] + "\",key);");
            }
        }
        pw.println("            }");
        pw.println("            JFreeChart chart = ChartFactory.create" + chartName + "Chart(");
        pw.println("                    \"" + chartTitle + "\",");
        pw.println("                    \"" + xLabel + "\",");
        pw.println("                    \"" + yLabel + "\",");
        pw.println("                    categoryDataset,");
        pw.println("                    PlotOrientation.VERTICAL,");
        pw.println("                    true, // legend");
        pw.println("                    true, // tooltips");
        pw.println("                    false // URLs");
        pw.println("                    );");
        pw.println("            //setting font size for axis");
        pw.println("            CategoryPlot plot = (CategoryPlot) chart.getPlot();");
        pw.println("            CategoryAxis domainAxis = plot.getDomainAxis();");
        pw.println("            domainAxis.setTickLabelFont(new Font(\"Arial\", 1, 12));");
        pw.println("            domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);");
        pw.println("            ValueAxis rangeAxis = plot.getRangeAxis();");
        pw.println("            rangeAxis.setTickLabelFont(new Font(\"Arial\", 1, 12));");
        pw.println("            rangeAxis.setUpperMargin(0.10);");
    }

    public void getPieChart(PrintWriter pw, String projectName, String moduleName, String pieChartTitle, String pieChartXComlumn, String pieChartYColumn, String methodNM)
    {
        pw.println("    public ModelAndView get" + methodNM + "PieChart(HttpServletRequest req, HttpServletResponse res, " + moduleName + "FormBean formBean) throws IOException, SQLException, ClassNotFoundException");
        pw.println("    {");
        pw.println("        ModelAndView mav = new ModelAndView(\"" + moduleName.toLowerCase(Locale.getDefault()) + "/report_menu\");");
        pw.println("        try");
        pw.println("        {");
        pw.println("            DefaultPieDataset data = new DefaultPieDataset();");
        pw.println("            List pieData = service.get" + methodNM + "ExportData(formBean);");
        getPieChartData(pw, pieChartXComlumn, pieChartYColumn);
        pw.println("            JFreeChart chart = ChartFactory.createPieChart3D(");
        pw.println("                    \"" + pieChartTitle + "\",");
        pw.println("                    data,");
        pw.println("                    true, // legend");
        pw.println("                    true, // tooltips");
        pw.println("                    false // URLs");
        pw.println("                    );");
        pw.println("            PiePlot piePlot = (PiePlot) chart.getPlot();");
        pw.println("            StandardPieSectionLabelGenerator labelGenerator = new StandardPieSectionLabelGenerator(\"{0}={2}\");");
        pw.println("            piePlot.setLabelGenerator(labelGenerator);");
        pw.println("            piePlot.setLabelFont(new Font(\"Verdana\", 0, 13));");
        pw.println("            piePlot.setLegendLabelGenerator(labelGenerator);");
        pw.println("            File outFile = new File(tomcatPath + \"/webapps/" + projectName + "/PieChart.jpg\");");
        pw.println("            ChartUtilities.saveChartAsJPEG(outFile, chart, 780, 500);");
        pw.println("            mav.addObject(\"imagePath\", \"PieChart.jpg\");");
        pw.println("            mav.addObject(\"process\", \"getChart\");");
        pw.println("            }");
        pw.println("            catch (Exception e)");
        pw.println("            { ");
        pw.println("                mav.addObject(\"error\", e.getMessage());");
        pw.println("                mav.setViewName(\"" + moduleName.toLowerCase(Locale.getDefault()) + "/error\");");
        pw.println("                finutils.errorhandler.ErrorHandler.PrintInFile(e, req);");
        pw.println("            }");
        pw.println("            return mav;");
        pw.println("        }");
        pw.println();
    }

    public void getBarChart(PrintWriter pw, String projectName, String moduleName, String barChartTitle, String barChartXComlumn, String[] barChartYColumn, String barChartXLabel, String barChartYLabel, String[] barChartLegendName, String methodNM)
    {
        pw.println("    public ModelAndView get" + methodNM + "BarChart(HttpServletRequest req, HttpServletResponse res, " + moduleName + "FormBean formBean) throws IOException, SQLException, ClassNotFoundException");
        pw.println("    {");
        pw.println("        ModelAndView mav = new ModelAndView(\"" + moduleName.toLowerCase(Locale.getDefault()) + "/report_menu\");");
        pw.println("        try");
        pw.println("        {");
        pw.println("            DefaultCategoryDataset categoryDataset = new DefaultCategoryDataset();");
        pw.println("            List chartData = service.get" + methodNM + "ExportData(formBean);");
        getChartData(pw, barChartXComlumn, barChartYColumn, barChartXLabel, barChartYLabel, barChartLegendName, barChartTitle, "Bar");
        pw.println("            //for adding bar label");
        pw.println("            BarRenderer renderer = (BarRenderer) chart.getCategoryPlot().getRenderer();");
        pw.println("            CategoryItemLabelGenerator generator = new StandardCategoryItemLabelGenerator(\"{2}\", new DecimalFormat(\"0\"));");
        pw.println("            renderer.setBaseItemLabelGenerator(generator);");
        pw.println("            ItemLabelPosition p = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE1, TextAnchor.BASELINE_LEFT, TextAnchor.BASELINE_LEFT, (Math.PI * -.100));");
        pw.println("            renderer.setItemLabelAnchorOffset(10);");
        pw.println("            renderer.setBasePositiveItemLabelPosition(p);");
        pw.println("            renderer.setBaseItemLabelFont(new Font(\"Arial\",1,12));");
        pw.println("            renderer.setBaseItemLabelsVisible(true);");
        pw.println("            //for generating image of chart");
        pw.println("            File outFile = new File(tomcatPath + \"/webapps/" + projectName + "/BarChart.jpg\");");
        pw.println("            ChartUtilities.saveChartAsJPEG(outFile, chart, 780, 500);");
        pw.println("            mav.addObject(\"imagePath\", \"BarChart.jpg\");");
        pw.println("            mav.addObject(\"process\", \"getChart\");");
        pw.println("            }");
        pw.println("            catch (Exception e)");
        pw.println("            { ");
        pw.println("                mav.addObject(\"error\", e.getMessage());");
        pw.println("                mav.setViewName(\"" + moduleName.toLowerCase(Locale.getDefault()) + "/error\");");
        pw.println("                finutils.errorhandler.ErrorHandler.PrintInFile(e, req);");
        pw.println("            }");
        pw.println("            return mav;");
        pw.println("        }");
        pw.println();
    }

    public void getLineChart(PrintWriter pw, String projectName, String moduleName, String lineChartTitle, String lineChartXComlumn, String[] lineChartYColumn, String lineChartXLabel, String lineChartYLabel, String[] lineChartLegendName, String methodNM)
    {
        pw.println("    public ModelAndView get" + methodNM + "LineChart(HttpServletRequest req, HttpServletResponse res, " + moduleName + "FormBean formBean) throws IOException, SQLException, ClassNotFoundException");
        pw.println("    {");
        pw.println("        ModelAndView mav = new ModelAndView(\"" + moduleName.toLowerCase(Locale.getDefault()) + "/report_menu\");");
        pw.println("        try");
        pw.println("        {");
        pw.println("            DefaultCategoryDataset categoryDataset = new DefaultCategoryDataset();");
        pw.println("            List chartData = service.get" + methodNM + "ExportData(formBean);");
        getChartData(pw, lineChartXComlumn, lineChartYColumn, lineChartXLabel, lineChartYLabel, lineChartLegendName, lineChartTitle, "Line");
        pw.println("            //for adding line shape label");
        pw.println("            LineAndShapeRenderer renderer = (LineAndShapeRenderer) chart.getCategoryPlot().getRenderer();");
        pw.println("            renderer.setBaseShapesFilled(true);");
        pw.println("            renderer.setBaseShapesVisible(true);");
        pw.println("            CategoryItemLabelGenerator generator = new StandardCategoryItemLabelGenerator(\"{2}\", new DecimalFormat(\"0\"));");
        pw.println("            renderer.setBaseItemLabelGenerator(generator);");
        pw.println("            ItemLabelPosition p = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE1, TextAnchor.BASELINE_LEFT, TextAnchor.BASELINE_LEFT, (Math.PI * -.100));");
        pw.println("            renderer.setBasePositiveItemLabelPosition(p);");
        pw.println("            renderer.setBaseItemLabelFont(new Font(\"Arial\",1,12));");
        pw.println("            renderer.setBaseItemLabelsVisible(true);");
        pw.println("            File outFile = new File(tomcatPath + \"/webapps/" + projectName + "/LineChart.jpg\");");
        pw.println("            ChartUtilities.saveChartAsJPEG(outFile, chart, 780, 500);");
        pw.println("            mav.addObject(\"imagePath\", \"LineChart.jpg\");");
        pw.println("            mav.addObject(\"process\", \"getChart\");");
        pw.println("            }");
        pw.println("            catch (Exception e)");
        pw.println("            { ");
        pw.println("                mav.addObject(\"error\", e.getMessage());");
        pw.println("                mav.setViewName(\"" + moduleName.toLowerCase(Locale.getDefault()) + "/error\");");
        pw.println("                finutils.errorhandler.ErrorHandler.PrintInFile(e, req);");
        pw.println("            }");
        pw.println("            return mav;");
        pw.println("        }");
        pw.println();
    }

    public void getAreaChart(PrintWriter pw, String projectName, String moduleName, String areaChartTitle, String areaChartXComlumn, String[] areaChartYColumn, String areaChartXLabel, String areaChartYLabel, String[] areaChartLegendName, String methodNM)
    {
        pw.println("    public ModelAndView get" + methodNM + "AreaChart(HttpServletRequest req, HttpServletResponse res, " + moduleName + "FormBean formBean) throws IOException, SQLException, ClassNotFoundException");
        pw.println("    {");
        pw.println("        ModelAndView mav = new ModelAndView(\"" + moduleName.toLowerCase(Locale.getDefault()) + "/report_menu\");");
        pw.println("        try");
        pw.println("        {");
        pw.println("            DefaultCategoryDataset categoryDataset = new DefaultCategoryDataset();");

        pw.println("            List chartData = service.get" + methodNM + "ExportData(formBean);");
        getChartData(pw, areaChartXComlumn, areaChartYColumn, areaChartXLabel, areaChartYLabel, areaChartLegendName, areaChartTitle, "Area");
        pw.println("            //for adding line shape label");
        pw.println("            AreaRenderer renderer = (AreaRenderer) chart.getCategoryPlot().getRenderer();");
        pw.println("            CategoryItemLabelGenerator generator = new StandardCategoryItemLabelGenerator(\"{2}\", new DecimalFormat(\"0\"));");
        pw.println("            renderer.setBaseItemLabelGenerator(generator);");
        pw.println("            ItemLabelPosition p = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE1, TextAnchor.BASELINE_LEFT, TextAnchor.BASELINE_LEFT, (Math.PI * -.25));");
        pw.println("            renderer.setBasePositiveItemLabelPosition(p);");
        pw.println("            renderer.setBaseItemLabelFont(new Font(\"Arial\",1,12));");
        pw.println("            renderer.setBaseItemLabelsVisible(true);");
        pw.println("            File outFile = new File(tomcatPath + \"/webapps/" + projectName + "/AreaChart.jpg\");");
        pw.println("            ChartUtilities.saveChartAsJPEG(outFile, chart, 780, 500);");
        pw.println("            mav.addObject(\"imagePath\", \"AreaChart.jpg\");");
        pw.println("            mav.addObject(\"process\", \"getChart\");");
        pw.println("            }");
        pw.println("            catch (Exception e)");
        pw.println("            { ");
        pw.println("                mav.addObject(\"error\", e.getMessage());");
        pw.println("                mav.setViewName(\"" + moduleName.toLowerCase(Locale.getDefault()) + "/error\");");
        pw.println("                finutils.errorhandler.ErrorHandler.PrintInFile(e, req);");
        pw.println("            }");
        pw.println("            return mav;");
        pw.println("        }");
        pw.println();
    }
}
