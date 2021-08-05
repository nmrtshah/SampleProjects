/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.findhtmlreport;

import com.finlogic.business.finstudio.findhtmlreport.FinDhtmlReportDetailEntityBean;
import com.finlogic.business.finstudio.findhtmlreport.FinDhtmlReportSummaryEntityBean;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Locale;

/**
 *
 * @author njuser
 */
public class FinDhtmlReportControllerGenerator
{

    private String tomcatPath = finpack.FinPack.getProperty("tomcat1_path");

    public void generateReportController(final FinDhtmlReportDetailEntityBean dEntityBean, final FinDhtmlReportSummaryEntityBean sEntityBean) throws FileNotFoundException
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
//        pw.println("import java.util.Locale;");
//        pw.println("import java.sql.SQLException;");
        if (dEntityBean.isPdf())
        {
            pw.println("import java.util.HashMap;");
            pw.println("import java.util.Map;");
            pw.println("import com.dhtmlx.xml2pdf.PDFParam;");
            pw.println("import com.dhtmlx.xml2pdf.PDFParam.PDFParameter;");
        }
        if (dEntityBean.isExcel())
        {
            pw.println("import com.dhtmlx.xml2excel.ExcelParam;");
            pw.println("import com.dhtmlx.xml2excel.ExcelParam.EXCELParameter;");
        }

        if (dEntityBean.isDateTimePicker())
        {
            pw.println("import java.text.DateFormat;");
            pw.println("import java.text.SimpleDateFormat;");
            pw.println("import java.util.Date;");
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
        //pw.println("    private String tomcatPath = finpack.FinPack.getProperty(\"tomcat1_path\");");
        pw.println();
//        pw.println("    public ModelAndView getMenu(final HttpServletRequest request,final HttpServletResponse response) throws SQLException, ClassNotFoundException");
        pw.println("    public ModelAndView getMenu(final HttpServletRequest request,final HttpServletResponse response, final " + moduleName + "FormBean formBean)");
        pw.println("    {");
        pw.println("        ModelAndView mav = new ModelAndView(\"" + moduleName.toLowerCase(Locale.getDefault()) + "/report_menu\");");
        pw.println("        try");
        pw.println("        {");
        pw.println("            String finlibPath = finpack.FinPack.getProperty(\"finlib_path\");");
        String depComboArry[] = null;
        if (dEntityBean.getFltrCmbSource() != null)
        {
            String control[] = dEntityBean.getFltrControl();
            int controlLen = control.length;
            for (int i = 0; i < controlLen; i++)
            {
                if (("ComboBox".equals(control[i]) || "TextLikeCombo".equals(control[i])) && dEntityBean.getFltrCmbSource() != null && ("fltrCmbSrcQuery".equals(dEntityBean.getFltrCmbSource()[i]) || "fltrCmbSrcWS".equals(dEntityBean.getFltrCmbSource()[i])))
                {
                    if (dEntityBean.getFltrchkForDependentCombo() != null && !dEntityBean.getFltrchkForDependentCombo()[i].equalsIgnoreCase("false") && dEntityBean.getFltrchkForDependentCombo().length > 0)
                    {
                        if (dEntityBean.getFltrCmbDependent() != null)
                        {
                            depComboArry = dEntityBean.getFltrCmbDependent().split(",");
                            if (!depComboArry[i].equalsIgnoreCase(""))
                            {
                                if (depComboArry[i].equalsIgnoreCase(dEntityBean.getFltrTxtName()[i]))
                                {
                                    pw.println("             mav.addObject(\"" + dEntityBean.getFltrTxtName()[i] + "List\", service.get" + dEntityBean.getFltrTxtName()[i] + "List());");
                                }
                                else
                                {
                                    pw.println("             mav.addObject(\"" + dEntityBean.getFltrTxtName()[i] + "List\", service.get" + dEntityBean.getFltrTxtName()[i] + "List(formBean));");
                                }
                            }
                            else
                            {
                                pw.println("              mav.addObject(\"" + dEntityBean.getFltrTxtName()[i] + "List\", service.get" + dEntityBean.getFltrTxtName()[i] + "List());");
                            }
                        }
                    }
                    else
                    {
                        pw.println("               mav.addObject(\"" + dEntityBean.getFltrTxtName()[i] + "List\", service.get" + dEntityBean.getFltrTxtName()[i] + "List());");
                    }
                }
                else if (("ComboBox".equals(control[i]) || "TextLikeCombo".equals(control[i])) && dEntityBean.getFltrCmbSource() != null && "fltrCmbSrcCommonCmb".equals(dEntityBean.getFltrCmbSource()[i]))
                {
                    String str = dEntityBean.getFltrCmbCommonQuery()[i].substring(0, 1).toUpperCase().concat(dEntityBean.getFltrCmbCommonQuery()[i].substring(1));
                    pw.println("               mav.addObject(\"" + dEntityBean.getFltrTxtName()[i] + "List\", service.get" + str + "());");
                }
            }
        }
        if (dEntityBean.isDateTimePicker())
        {
            pw.println("        DateFormat dateFormat = new SimpleDateFormat(\"dd-MM-yyyy\",Locale.getDefault());");
            pw.println("        Date date = new Date();");
            pw.println("        String dateString = dateFormat.format(date);");
            pw.println("        mav.addObject(\"dateString\", dateString);");
        }
        pw.println("             mav.addObject(\"finlibPath\", finlibPath);");
        pw.println("             mav.addObject(\"process\", \"getmenu\");");
        pw.println("        }");
        pw.println("        catch (Exception e)");
        pw.println("        {");
        pw.println("            mav.addObject(\"error\", e.getMessage());");
        pw.println("            mav.setViewName(\"" + moduleName.toLowerCase(Locale.getDefault()) + "/error\");");
        pw.println("            finutils.errorhandler.ErrorHandler.PrintInFile(e, request);");
        pw.println("        }");
        pw.println("        return mav;");
        pw.println("    }");
        pw.println();

        pw.println("    public ModelAndView getReportGrid(final HttpServletRequest request, final HttpServletResponse response, final " + moduleName + "FormBean formBean)");
        pw.println("    {");
        pw.println("        ModelAndView mav = new ModelAndView(\"" + moduleName.toLowerCase(Locale.getDefault()) + "/report_menu\");");
        pw.println("        try");
        pw.println("        {");
        pw.println("            mav.addObject(\"process\", \"getreportGrid\");");
        pw.println("            mav.addObject(\"json\", service.getReportData(formBean));");
        pw.println("        }");
        pw.println("        catch (Exception e)");
        pw.println("        {");
        pw.println("            mav.addObject(\"error\", e.getMessage());");
        pw.println("            mav.setViewName(\"" + moduleName.toLowerCase(Locale.getDefault()) + "/error\");");
        pw.println("            finutils.errorhandler.ErrorHandler.PrintInFile(e, request);");
        pw.println("        }");
        pw.println("        return mav;");
        pw.println("    }");
        pw.println();

        // for generate method for dependent combo box
        if (dEntityBean.getFltrCmbSource() != null)
        {
            String control[] = dEntityBean.getFltrControl();
            int controlLen = control.length;
            for (int i = 0; i < controlLen; i++)
            {
                if (("ComboBox".equals(control[i]) || "TextLikeCombo".equals(control[i])))
                {
                    if (dEntityBean.getFltrchkForDependentCombo() != null && !dEntityBean.getFltrchkForDependentCombo()[i].equalsIgnoreCase("false") && dEntityBean.getFltrchkForDependentCombo().length > 0)
                    {
                        if (dEntityBean.getFltrCmbDependent() != null)
                        {
                            depComboArry = dEntityBean.getFltrCmbDependent().split(",");
                            if (!depComboArry[i].equalsIgnoreCase(""))
                            {
                                if (!depComboArry[i].equalsIgnoreCase(dEntityBean.getFltrTxtName()[i]))
                                {
                                    pw.println("    public ModelAndView get" + dEntityBean.getFltrTxtName()[i] + "(final HttpServletRequest request, final HttpServletResponse response, final " + moduleName + "FormBean formBean)");
                                    pw.println("    {");
                                    pw.println("        ModelAndView mav = new ModelAndView(\"" + moduleName.toLowerCase(Locale.getDefault()) + "/ajax\");");
                                    pw.println("        try");
                                    pw.println("        {");
                                    pw.println("            mav.addObject(\"process\", \"" + dEntityBean.getFltrTxtName()[i] + "\");");
                                    pw.println("            mav.addObject(\"" + dEntityBean.getFltrTxtName()[i] + "List\", service.get" + dEntityBean.getFltrTxtName()[i]+"List(formBean));");
                                    pw.println("        }");
                                    pw.println("        catch (Exception e)");
                                    pw.println("        {");
                                    pw.println("            mav.addObject(\"error\", e.getMessage());");
                                    pw.println("            mav.setViewName(\"" + moduleName.toLowerCase(Locale.getDefault()) + "/error\");");
                                    pw.println("            finutils.errorhandler.ErrorHandler.PrintInFile(e, request);");
                                    pw.println("        }");
                                    pw.println("        return mav;");
                                    pw.println("    }");
                                    pw.println();
                                }
                            }
                        }
                    }
                }
            }
        }

        //for report file
        if (dEntityBean.isPdf())
        {
            pw.println("    public void generatePDF(HttpServletRequest req, HttpServletResponse res, " + moduleName + "FormBean formBean) throws Exception, Throwable");
            pw.println("    {");
            pw.println("        try");
            pw.println("        {");
            pw.println("            PDFParam ppObj = new PDFParam();");
            pw.println("            Map paramMap = new HashMap();");
            pw.println("            String reportTitle = formBean.getReportTitle();");
            pw.println("            paramMap.put(PDFParameter.FILENAME.getPdfParam(), formBean.getReportName().trim().replace(\" \", \"_\"));");
            pw.println("            paramMap.put(PDFParameter.DISPLAYMODE.getPdfParam(), formBean.getDisplayMode());");
            pw.println("            paramMap.put(PDFParameter.REPORTTITLE.getPdfParam(), reportTitle);");
            pw.println("            //paramMap.put(PDFParameter.HEADERIMG.getPdfParam(), \"/home/njuser/Desktop/logo_njindiainvest.jpeg\");");
            pw.println("            //paramMap.put(PDFParameter.FOOTERIMG.getPdfParam(), \"/home/njuser/Desktop/logo_njindiainvest.jpeg\");");
            pw.println("            paramMap.put(PDFParameter.GRIDXML.getPdfParam(), req.getParameter(\"grid_xml\") == null ? \"\" : req.getParameter(\"grid_xml\"));");
            pw.println("            paramMap.put(PDFParameter.HTTPRESPONSE.getPdfParam(), res);");
            pw.println("            ppObj.generatePDF(paramMap);");
            pw.println("        }");
            pw.println("        catch (Exception e)");
            pw.println("        {");
            pw.println("            finutils.errorhandler.ErrorHandler.PrintInFile(e, req);");
            pw.println("        }");
            pw.println("    }");
            pw.println("");
        }
        if (dEntityBean.isExcel())
        {
            pw.println("    public void generateExcel(HttpServletRequest req, HttpServletResponse res, " + moduleName + "FormBean formBean) throws Exception");
            pw.println("    {");
            pw.println("        try");
            pw.println("        {");
            pw.println("            ExcelParam epObj = new ExcelParam();");
            pw.println("            Map paramMap = new HashMap();");
            pw.println("            paramMap.put(EXCELParameter.FILENAME.getExcelParam(), formBean.getReportName().trim().replace(\" \", \"_\"));");
            pw.println("            paramMap.put(EXCELParameter.GRIDXML.getExcelParam(), req.getParameter(\"grid_xml\"));");
            pw.println("            paramMap.put(EXCELParameter.HTTPRESPONSE.getExcelParam(), res);");
            pw.println("            epObj.generateExcel(paramMap);");
            pw.println("        }");
            pw.println("        catch (Exception e)");
            pw.println("        {");
            pw.println("            finutils.errorhandler.ErrorHandler.PrintInFile(e, req);");
            pw.println("        }");
            pw.println("    }");
            pw.println("");
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
