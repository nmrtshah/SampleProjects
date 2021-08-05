/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.apps.finstudio.webservicedoc;

//import com.finlogic.eai.ws.consumer.wfms2.ProjectConsumer;
import com.finlogic.apps.finstudio.findatareqexecutor.FinDataReqExecutorService;
import com.finlogic.util.Logger;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 *
 * @author njuser
 */
public class WebServiceDocController extends MultiActionController
{

//    private final ProjectConsumer crmLeadService = new ProjectConsumer();
    private final FinDataReqExecutorService service = new FinDataReqExecutorService();
    private final String serviceClass = "/opt/application_storage/temp_files/finstudio/wsdoc/";
    private String zipfile = "Error";

    public ModelAndView getMainPage(final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {
        ModelAndView mav;
        mav = new ModelAndView();
        String finlibPath = finpack.FinPack.getProperty("finlib_path");
        mav.addObject("finlib_path", finlibPath);
        mav.setViewName("webservicedoc/Main");
        return mav;
    }

    public ModelAndView getWebServiceForm(final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {
        ModelAndView mav;
        mav = new ModelAndView();
//        mav.addObject("projectList", crmLeadService.getProjectList(null));
        mav.addObject("projectList", service.getAddProjects());
        mav.addObject("type", request.getParameter("type"));
        mav.setViewName("webservicedoc/Webservice");
        return mav;
    }

    public ModelAndView generateXLS(final HttpServletRequest request, final HttpServletResponse response, final WebServiceDocFormBean formBean) throws Exception
    {
        ModelAndView mav;
        mav = new ModelAndView();
        try
        {
            generateExcelAndFiles(request, response, formBean);
            mav.addObject("type", formBean.getRdowebchoice());
            mav.addObject("zipPath", zipfile);
            mav.setViewName("webservicedoc/SuccessPage");
        }
        catch ( IOException | InvalidFormatException e )
        {
            Logger.ErrorLogger(e);
        }
        return mav;
    }

    public void generateExcelAndFiles(final HttpServletRequest request, final HttpServletResponse response, final WebServiceDocFormBean formBean) throws IOException, InvalidFormatException
    {

        HSSFWorkbook sampleWorkbook;
        sampleWorkbook = new HSSFWorkbook();
        CommonExcelFunctions exFn = new CommonExcelFunctions(sampleWorkbook);

        // For Left Border
        HSSFCellStyle cellStyleBorderLeft = sampleWorkbook.createCellStyle();

        //For left Silver
        HSSFCellStyle cellSilverStyleNormalLeft = sampleWorkbook.createCellStyle();
        HSSFCellStyle normalSilverStyleLeft = exFn.getSilverCellFmtLeft(cellSilverStyleNormalLeft);

        //For left Black
        HSSFCellStyle cellBlackStyleNormalLeft = sampleWorkbook.createCellStyle();
        HSSFCellStyle boldBlackStyleLeft = exFn.getBlackCellFmtLeft(cellBlackStyleNormalLeft);

        //For Normal Font Center
        HSSFCellStyle cellNormalFontObj = sampleWorkbook.createCellStyle();
        HSSFCellStyle titleFormat = exFn.getWhiteCellFmtCenter(cellNormalFontObj);

        //For Normal Font Center
        HSSFCellStyle cellBoldFontObj = sampleWorkbook.createCellStyle();
        HSSFCellStyle titleFormatNormal = exFn.getWhiteCellNormalFmtCenter(cellBoldFontObj);

        //For Normal Font Left
        HSSFCellStyle cellNormalFontLeftObj = sampleWorkbook.createCellStyle();
        HSSFCellStyle titleFormatLeft = exFn.getWhiteCellFmtLeft(cellNormalFontLeftObj);

        HSSFSheet sampleDataSheet;
        HSSFRow titleRow;
        List sheetCharList = exFn.getCommonListForExcel();
        int row = 1;
        int totalColumn = 2;
        int lastLeftLineRow = 6;

        sampleDataSheet = sampleWorkbook.createSheet("sheet1");

        titleRow = sampleDataSheet.createRow(row);
        exFn.printCellWithColSpan(sampleDataSheet, titleRow, boldBlackStyleLeft, "Producer Project", "B2:" + sheetCharList.get(totalColumn) + "2", 1);
        if (formBean.getRdowebchoice().equalsIgnoreCase("soap"))
        {
            exFn.printCellWithColSpan(sampleDataSheet, titleRow, titleFormatLeft, formBean.getProducerPro(), "D2:" + sheetCharList.get(totalColumn + 3) + "2", 3);
        }else if (formBean.getRdowebchoice().equalsIgnoreCase("rest"))
        {
            exFn.printCellWithColSpan(sampleDataSheet, titleRow, titleFormatLeft, request.getParameter("projectName").split("-")[0], "D2:" + sheetCharList.get(totalColumn + 3) + "2", 3);
        }
        exFn.printCell(titleRow, lastLeftLineRow, exFn.getLeftBorder(cellStyleBorderLeft), "");

        titleRow = sampleDataSheet.createRow(++row);
        if (formBean.getRdowebchoice().equalsIgnoreCase("soap"))
        {
            exFn.printCellWithColSpan(sampleDataSheet, titleRow, boldBlackStyleLeft, "Consumer Project", "B3:" + sheetCharList.get(totalColumn) + "3", 1);
            exFn.printCellWithColSpan(sampleDataSheet, titleRow, titleFormatLeft, formBean.getConsumerPro(), "D3:" + sheetCharList.get(totalColumn + 3) + "3", 3);
            exFn.printCell(titleRow, lastLeftLineRow, exFn.getLeftBorder(cellStyleBorderLeft), "");

            titleRow = sampleDataSheet.createRow(++row);
            exFn.printCellWithColSpan(sampleDataSheet, titleRow, boldBlackStyleLeft, "Web Service Name", "B4:" + sheetCharList.get(totalColumn) + "4", 1);
            exFn.printCellWithColSpan(sampleDataSheet, titleRow, titleFormatLeft, formBean.getWebserviceName(), "D4:" + sheetCharList.get(totalColumn + 3) + "4", 3);
            exFn.printCell(titleRow, lastLeftLineRow, exFn.getLeftBorder(cellStyleBorderLeft), "");
        }
        else if (formBean.getRdowebchoice().equalsIgnoreCase("rest"))
        {
            exFn.printCellWithColSpan(sampleDataSheet, titleRow, boldBlackStyleLeft, "Web Service Name", "B3:" + sheetCharList.get(totalColumn) + "3", 1);
            exFn.printCellWithColSpan(sampleDataSheet, titleRow, titleFormatLeft, formBean.getWebserviceName(), "D3:" + sheetCharList.get(totalColumn + 3) + "3", 3);
            exFn.printCell(titleRow, lastLeftLineRow, exFn.getLeftBorder(cellStyleBorderLeft), "");

            titleRow = sampleDataSheet.createRow(++row);
            exFn.printCellWithColSpan(sampleDataSheet, titleRow, boldBlackStyleLeft, "Web Service URL", "B4:" + sheetCharList.get(totalColumn) + "4", 1);
            exFn.printCellWithColSpan(sampleDataSheet, titleRow, titleFormatLeft, formBean.getWebserviceURL(), "D4:" + sheetCharList.get(totalColumn + 3) + "4", 3);
            exFn.printCell(titleRow, lastLeftLineRow, exFn.getLeftBorder(cellStyleBorderLeft), "");
        }

        if (formBean.getRdowebchoice().equalsIgnoreCase("rest"))
        {
            titleRow = sampleDataSheet.createRow(++row);
            exFn.printCellWithColSpan(sampleDataSheet, titleRow, boldBlackStyleLeft, "HTTP Method Allow ", "B5:" + sheetCharList.get(totalColumn) + "5", 1);
            exFn.printCellWithColSpan(sampleDataSheet, titleRow, titleFormatLeft, Arrays.toString(formBean.getHttpMethod()).substring(1, Arrays.toString(formBean.getHttpMethod()).length()-1) , "D5:" + sheetCharList.get(totalColumn + 3) + "5", 3);
            exFn.printCell(titleRow, lastLeftLineRow, exFn.getLeftBorder(cellStyleBorderLeft), "");
        }
        else
        {
            titleRow = sampleDataSheet.createRow(++row);
            exFn.printCellWithColSpan(sampleDataSheet, titleRow, titleFormat, "", "B5:" + sheetCharList.get(totalColumn + 3) + "5", 1);
            exFn.printCell(titleRow, totalColumn + 4, exFn.getLeftBorder(cellStyleBorderLeft), "");
        }

        titleRow = sampleDataSheet.createRow(++row);
        if (formBean.getRdowebchoice().equalsIgnoreCase("soap"))
        {
            exFn.printCellWithColSpan(sampleDataSheet, titleRow, normalSilverStyleLeft, "Method Name : " + formBean.getMethodName(), "B6:" + sheetCharList.get(totalColumn + 3) + "6", 1);
        }else{
            exFn.printCellWithColSpan(sampleDataSheet, titleRow, titleFormat, "", "B6:" + sheetCharList.get(totalColumn + 3) + "6", 1);
        }
        exFn.printCell(titleRow, totalColumn + 4, exFn.getLeftBorder(cellStyleBorderLeft), "");

        titleRow = sampleDataSheet.createRow(++row);
        exFn.printCellWithColSpan(sampleDataSheet, titleRow, titleFormat, "", "B7:" + sheetCharList.get(totalColumn + 3) + "7", 1);
        exFn.printCell(titleRow, totalColumn + 4, exFn.getLeftBorder(cellStyleBorderLeft), "");

        // In Parameters
        String inParam = formBean.getInParam();

        int lastRow = 8;

        titleRow = sampleDataSheet.createRow(++row);
        exFn.printCellWithColSpan(sampleDataSheet, titleRow, normalSilverStyleLeft, "In Parameter", "B" + lastRow + ":B" + lastRow, 1);
//        if (formBean.getInBeanType().equals("List") && !formBean.getInParam().equals(""))
//        {
//            exFn.printCellWithColSpan(sampleDataSheet, titleRow, normalSilverStyleLeft, "List<" + formBean.getInParam() + ">", "C" + lastRow + ":D" + lastRow, 2);
//        }
//        else
//        {
        exFn.printCellWithColSpan(sampleDataSheet, titleRow, normalSilverStyleLeft, formBean.getInParam(), "C" + lastRow + ":D" + lastRow, 2);
//        }

//        exFn.printCellWithColSpan(sampleDataSheet, titleRow, normalSilverStyleLeft, "Format : " + formBean.getInBeanType(), "E" + lastRow + ":F" + lastRow, 4);
        exFn.printCellWithColSpan(sampleDataSheet, titleRow, normalSilverStyleLeft, "", "E" + lastRow + ":F" + lastRow, 4);
        exFn.printCell(titleRow, lastLeftLineRow, exFn.getLeftBorder(cellStyleBorderLeft), "");

        lastRow++;

        titleRow = sampleDataSheet.createRow(++row);
        exFn.printCellWithColSpan(sampleDataSheet, titleRow, titleFormat, "", "B" + lastRow + ":" + sheetCharList.get(totalColumn + 3) + lastRow, 1);
        exFn.printCell(titleRow, lastLeftLineRow, exFn.getLeftBorder(cellStyleBorderLeft), "");

        lastRow++;

        titleRow = sampleDataSheet.createRow(++row);
        exFn.printCellWithColSpan(sampleDataSheet, titleRow, titleFormatNormal, "Nature", "B" + lastRow + ":B" + lastRow, 1);
        exFn.printCellWithColSpan(sampleDataSheet, titleRow, titleFormatNormal, "KeyParamName", "C" + lastRow + ":C" + lastRow, 2);
        exFn.printCellWithColSpan(sampleDataSheet, titleRow, titleFormatNormal, "DataType", "D" + lastRow + ":D" + lastRow, 3);
        exFn.printCellWithColSpan(sampleDataSheet, titleRow, titleFormatNormal, "Default Value", "E" + lastRow + ":E" + lastRow, 4);
        exFn.printCellWithColSpan(sampleDataSheet, titleRow, titleFormatNormal, "Format", "F" + lastRow + ":F" + lastRow, 5);
        exFn.printCell(titleRow, lastLeftLineRow, exFn.getLeftBorder(cellStyleBorderLeft), "");

        String[] inNatureArray = Arrays.toString(formBean.getInparamtable_nature()).substring(1, Arrays.toString(formBean.getInparamtable_nature()).length() - 1).split(",");
        String[] inNameArray = Arrays.toString(formBean.getInparamtable_name()).substring(1, Arrays.toString(formBean.getInparamtable_name()).length() - 1).split(",");
        String[] inDefaultValueArray = Arrays.toString(formBean.getInparamtable_default()).substring(1, Arrays.toString(formBean.getInparamtable_default()).length() - 1).split(",");
        String[] inFormatArray = Arrays.toString(formBean.getInparamtable_format()).substring(1, Arrays.toString(formBean.getInparamtable_format()).length() - 1).split(",");

        String[] inDataTypeArray = Arrays.toString(formBean.getInparamtable_dataType()).substring(1, Arrays.toString(formBean.getInparamtable_dataType()).length() - 1).split(",");

        for ( int arr = 0; arr < inDataTypeArray.length; arr++ )
        {
            if (inDataTypeArray[arr].trim().equals("boolean") || inDataTypeArray[arr].trim().equals("date") || inDataTypeArray[arr].trim().equals("time") || inDataTypeArray[arr].trim().equals("datetime"))
            {
                inDataTypeArray[arr] = "String";
            }
            if (inDataTypeArray[arr].trim().equals("boolean[]") || inDataTypeArray[arr].trim().equals("date[]") || inDataTypeArray[arr].trim().equals("time[]") || inDataTypeArray[arr].trim().equals("datetime[]"))
            {
                inDataTypeArray[arr] = "String[]";
            }
//            if (inDataTypeArray[arr].trim().equals("Bean[]") || inDataTypeArray[arr].trim().equals("Bean"))
            if (inDataTypeArray[arr].trim().equals("List<Bean>"))
            {
//                Logger.DataLogger("bean Type:" + formBean.getBeanType());
//                String[] beanTypeArr = formBean.getBeanType().split(",");
//                boolean listType = false;
//                for (int j = 0; j < beanTypeArr.length; j++)
//                {
//                    if (beanTypeArr[j].toLowerCase().contains(inNameArray[arr].toLowerCase().trim()) && beanTypeArr[j].startsWith("List"))
//                    {
//                        listType = true;
//                    }
//                }
//                if (listType)
//                {
                inDataTypeArray[arr] = "List<" + inNameArray[arr].trim().substring(0, 1).toUpperCase() + inNameArray[arr].trim().substring(1) + ">";
//                }
//                else
//                {
            }
            if (inDataTypeArray[arr].trim().equals("Bean"))
            {

                inDataTypeArray[arr] = inNameArray[arr].trim().substring(0, 1).toUpperCase() + inNameArray[arr].trim().substring(1);
            }
        }

        if (!"null".equals(Arrays.toString(formBean.getInparamtable_name())))
        {
            for ( int i = 0; i < inNameArray.length; i++ )
            {
                lastRow++;
                titleRow = sampleDataSheet.createRow(++row);
                exFn.printCellWithColSpan(sampleDataSheet, titleRow, titleFormat, inNatureArray[i].trim(), "B" + lastRow + ":B" + lastRow, 1);
                exFn.printCellWithColSpan(sampleDataSheet, titleRow, titleFormat, inNameArray[i].trim(), "C" + lastRow + ":C" + lastRow, 2);
                exFn.printCellWithColSpan(sampleDataSheet, titleRow, titleFormat, inDataTypeArray[i].trim(), "D" + lastRow + ":D" + lastRow, 3);
                exFn.printCellWithColSpan(sampleDataSheet, titleRow, titleFormat, inDefaultValueArray[i].trim(), "E" + lastRow + ":E" + lastRow, 4);
                exFn.printCellWithColSpan(sampleDataSheet, titleRow, titleFormat, inFormatArray[i].trim(), "F" + lastRow + ":F" + lastRow, 5);
                exFn.printCell(titleRow, lastLeftLineRow, exFn.getLeftBorder(cellStyleBorderLeft), "");
            }
        }

        else
        {
            lastRow++;
            titleRow = sampleDataSheet.createRow(++row);
            exFn.printCellWithColSpan(sampleDataSheet, titleRow, titleFormat, "-", "B" + lastRow + ":B" + lastRow, 1);
            exFn.printCellWithColSpan(sampleDataSheet, titleRow, titleFormat, "-", "C" + lastRow + ":C" + lastRow, 2);
            exFn.printCellWithColSpan(sampleDataSheet, titleRow, titleFormat, "-", "D" + lastRow + ":D" + lastRow, 3);
            exFn.printCellWithColSpan(sampleDataSheet, titleRow, titleFormat, "-", "E" + lastRow + ":E" + lastRow, 4);
            exFn.printCellWithColSpan(sampleDataSheet, titleRow, titleFormat, "-", "F" + lastRow + ":F" + lastRow, 5);
            exFn.printCell(titleRow, lastLeftLineRow, exFn.getLeftBorder(cellStyleBorderLeft), "");
        }

        // Out Parameters
        String outParam;

        lastRow++;

        titleRow = sampleDataSheet.createRow(++row);

        exFn.printCellWithColSpan(sampleDataSheet, titleRow, titleFormat, "", "B" + lastRow + ":" + sheetCharList.get(totalColumn + 3) + lastRow, 1);
        exFn.printCell(titleRow, lastLeftLineRow, exFn.getLeftBorder(cellStyleBorderLeft), "");

        lastRow++;

        titleRow = sampleDataSheet.createRow(++row);

        exFn.printCellWithColSpan(sampleDataSheet, titleRow, normalSilverStyleLeft, "Out Parameter", "B" + lastRow + ":B" + lastRow, 1);
//        if (formBean.getOutBeanType().equals("List") && !formBean.getOutParam().equals(""))
//        {
//        exFn.printCellWithColSpan(sampleDataSheet, titleRow, normalSilverStyleLeft, "List<" + formBean.getOutParam() + ">", "C" + lastRow + ":D" + lastRow, 2);
//        outParam = "List<" + formBean.getOutParam() + ">";
//        }

//        else
//        {
        exFn.printCellWithColSpan(sampleDataSheet, titleRow, normalSilverStyleLeft, formBean.getOutParam(), "C" + lastRow + ":D" + lastRow, 2);
        outParam = formBean.getOutParam();
//        }

//        exFn.printCellWithColSpan(sampleDataSheet, titleRow, normalSilverStyleLeft, "Format : " + formBean.getOutBeanType(), "E" + lastRow + ":F" + lastRow, 4);
        exFn.printCellWithColSpan(sampleDataSheet, titleRow, normalSilverStyleLeft, "", "E" + lastRow + ":F" + lastRow, 4);
        exFn.printCell(titleRow, lastLeftLineRow, exFn.getLeftBorder(cellStyleBorderLeft), "");

        lastRow++;

        titleRow = sampleDataSheet.createRow(++row);

        exFn.printCellWithColSpan(sampleDataSheet, titleRow, titleFormat, "", "B" + lastRow + ":" + sheetCharList.get(totalColumn + 3) + lastRow, 1);
        exFn.printCell(titleRow, lastLeftLineRow, exFn.getLeftBorder(cellStyleBorderLeft), "");

        lastRow++;

        titleRow = sampleDataSheet.createRow(++row);

        exFn.printCellWithColSpan(sampleDataSheet, titleRow, titleFormatNormal, "Nature", "B" + lastRow + ":B" + lastRow, 1);
        exFn.printCellWithColSpan(sampleDataSheet, titleRow, titleFormatNormal, "KeyParamName", "C" + lastRow + ":C" + lastRow, 2);
        exFn.printCellWithColSpan(sampleDataSheet, titleRow, titleFormatNormal, "DataType", "D" + lastRow + ":D" + lastRow, 3);
        exFn.printCellWithColSpan(sampleDataSheet, titleRow, titleFormatNormal, "Default Value", "E" + lastRow + ":E" + lastRow, 4);
        exFn.printCellWithColSpan(sampleDataSheet, titleRow, titleFormatNormal, "Format", "F" + lastRow + ":F" + lastRow, 5);
        exFn.printCell(titleRow, lastLeftLineRow, exFn.getLeftBorder(cellStyleBorderLeft), "");

        String[] outNatureArray = Arrays.toString(formBean.getOutparamtable_nature()).substring(1, Arrays.toString(formBean.getOutparamtable_nature()).length() - 1).split(",");
        String[] outNameArray = Arrays.toString(formBean.getOutparamtable_name()).substring(1, Arrays.toString(formBean.getOutparamtable_name()).length() - 1).split(",");
        String[] outDefaultValueArray = Arrays.toString(formBean.getOutparamtable_default()).substring(1, Arrays.toString(formBean.getOutparamtable_default()).length() - 1).split(",");
        String[] outFormatArray = Arrays.toString(formBean.getOutparamtable_format()).substring(1, Arrays.toString(formBean.getOutparamtable_format()).length() - 1).split(",");

        String[] outDataTypeArray = Arrays.toString(formBean.getOutparamtable_dataType()).substring(1, Arrays.toString(formBean.getOutparamtable_dataType()).length() - 1).split(",");

        for ( int arr = 0; arr < outDataTypeArray.length; arr++ )
        {
            if (outDataTypeArray[arr].trim().equals("boolean") || outDataTypeArray[arr].trim().equals("date") || outDataTypeArray[arr].trim().equals("time") || outDataTypeArray[arr].trim().equals("datetime"))
            {
                outDataTypeArray[arr] = "String";
            }
            if (outDataTypeArray[arr].trim().equals("boolean[]") || outDataTypeArray[arr].trim().equals("date[]") || outDataTypeArray[arr].trim().equals("time[]") || outDataTypeArray[arr].trim().equals("datetime[]"))
            {
                outDataTypeArray[arr] = "String[]";
            }
//            if (outDataTypeArray[arr].trim().equals("Bean[]") || outDataTypeArray[arr].trim().equals("Bean"))
            if (outDataTypeArray[arr].trim().equals("Bean"))
            {
                outDataTypeArray[arr] = outNameArray[arr].trim().substring(0, 1).toUpperCase() + outNameArray[arr].trim().substring(1);
            }
//                Logger.DataLogger(formBean.getBeanType());
//                String[] beanTypeArr = formBean.getBeanType().split(",");
//                boolean listType = false;
//                for (int j = 0; j < beanTypeArr.length; j++)
//                {
//                    if (beanTypeArr[j].toLowerCase().contains(outNameArray[arr].toLowerCase().trim()) && beanTypeArr[j].startsWith("List"))
//                    {
//                        listType = true;
//                    }
//                }
//                if (listType)
//                {
            if (outDataTypeArray[arr].trim().equals("List<Bean>"))
            {

                outDataTypeArray[arr] = "List<" + outNameArray[arr].trim().substring(0, 1).toUpperCase() + outNameArray[arr].trim().substring(1) + ">";
            }
//                }
//                else
//                {
//                }

        }

        if (!"null".equals(Arrays.toString(formBean.getOutparamtable_name())))
        {
            for ( int i = 0; i < outNameArray.length; i++ )
            {
                lastRow++;
                titleRow = sampleDataSheet.createRow(++row);
                exFn.printCellWithColSpan(sampleDataSheet, titleRow, titleFormat, outNatureArray[i].trim(), "B" + lastRow + ":B" + lastRow, 1);
                exFn.printCellWithColSpan(sampleDataSheet, titleRow, titleFormat, outNameArray[i].trim(), "C" + lastRow + ":C" + lastRow, 2);
                exFn.printCellWithColSpan(sampleDataSheet, titleRow, titleFormat, outDataTypeArray[i].trim(), "D" + lastRow + ":D" + lastRow, 3);
                exFn.printCellWithColSpan(sampleDataSheet, titleRow, titleFormat, outDefaultValueArray[i].trim(), "E" + lastRow + ":E" + lastRow, 4);
                exFn.printCellWithColSpan(sampleDataSheet, titleRow, titleFormat, outFormatArray[i].trim(), "F" + lastRow + ":F" + lastRow, 5);
                exFn.printCell(titleRow, lastLeftLineRow, exFn.getLeftBorder(cellStyleBorderLeft), "");
            }
        }

        else
        {
            lastRow++;
            titleRow = sampleDataSheet.createRow(++row);
            exFn.printCellWithColSpan(sampleDataSheet, titleRow, titleFormat, "-", "B" + lastRow + ":B" + lastRow, 1);
            exFn.printCellWithColSpan(sampleDataSheet, titleRow, titleFormat, "-", "C" + lastRow + ":C" + lastRow, 2);
            exFn.printCellWithColSpan(sampleDataSheet, titleRow, titleFormat, "-", "D" + lastRow + ":D" + lastRow, 3);
            exFn.printCellWithColSpan(sampleDataSheet, titleRow, titleFormat, "-", "E" + lastRow + ":E" + lastRow, 4);
            exFn.printCellWithColSpan(sampleDataSheet, titleRow, titleFormat, "-", "F" + lastRow + ":F" + lastRow, 5);
            exFn.printCell(titleRow, lastLeftLineRow, exFn.getLeftBorder(cellStyleBorderLeft), "");
        }

        String tableNamesForFile = "";
        StringBuilder tableContentForFile = new StringBuilder();

        //Bean Table Entry
        if (!"".equals(formBean.getBeanAllTable()))
        {
            String[] tableNames = formBean.getBeanAllTable().split(",");
//            String[] tableType = formBean.getBeanType().split(",");
            String[] tableContent = formBean.getBeanTableData().split("\\|");

            for ( int i = 0; i < tableNames.length; i++ )
            {
                lastRow++;

                titleRow = sampleDataSheet.createRow(++row);
                exFn.printCellWithColSpan(sampleDataSheet, titleRow, titleFormat, "", "B" + lastRow + ":" + sheetCharList.get(totalColumn + 3) + lastRow, 1);
                exFn.printCell(titleRow, lastLeftLineRow, exFn.getLeftBorder(cellStyleBorderLeft), "");

                lastRow++;

                titleRow = sampleDataSheet.createRow(++row);
                exFn.printCellWithColSpan(sampleDataSheet, titleRow, titleFormat, "", "B" + lastRow + ":" + sheetCharList.get(totalColumn + 3) + lastRow, 1);
                exFn.printCell(titleRow, lastLeftLineRow, exFn.getLeftBorder(cellStyleBorderLeft), "");

                String table = tableNames[i].substring(0, 1).toUpperCase() + tableNames[i].substring(1);
                tableNamesForFile += table + ",";
                lastRow++;

                titleRow = sampleDataSheet.createRow(++row);
                exFn.printCellWithColSpan(sampleDataSheet, titleRow, normalSilverStyleLeft, "Bean Name", "B" + lastRow + ":B" + lastRow, 1);
//                if (tableType[i].trim().startsWith("List"))
//                {
//                    exFn.printCellWithColSpan(sampleDataSheet, titleRow, normalSilverStyleLeft, "List<" + table + ">", "C" + lastRow + ":D" + lastRow, 2);
//                    exFn.printCellWithColSpan(sampleDataSheet, titleRow, normalSilverStyleLeft, "Format : List", "E" + lastRow + ":F" + lastRow, 4);
//                }
//                else
//                {
                exFn.printCellWithColSpan(sampleDataSheet, titleRow, normalSilverStyleLeft, table, "C" + lastRow + ":D" + lastRow, 2);
                exFn.printCellWithColSpan(sampleDataSheet, titleRow, normalSilverStyleLeft, "", "E" + lastRow + ":F" + lastRow, 4);
//                }
                exFn.printCell(titleRow, lastLeftLineRow, exFn.getLeftBorder(cellStyleBorderLeft), "");

                lastRow++;

                titleRow = sampleDataSheet.createRow(++row);
                exFn.printCellWithColSpan(sampleDataSheet, titleRow, titleFormat, "", "B" + lastRow + ":" + sheetCharList.get(totalColumn + 3) + lastRow, 1);
                exFn.printCell(titleRow, lastLeftLineRow, exFn.getLeftBorder(cellStyleBorderLeft), "");

                lastRow++;

                titleRow = sampleDataSheet.createRow(++row);
                exFn.printCellWithColSpan(sampleDataSheet, titleRow, titleFormatNormal, "Nature", "B" + lastRow + ":B" + lastRow, 1);
                exFn.printCellWithColSpan(sampleDataSheet, titleRow, titleFormatNormal, "KeyParamName", "C" + lastRow + ":C" + lastRow, 2);
                exFn.printCellWithColSpan(sampleDataSheet, titleRow, titleFormatNormal, "DataType", "D" + lastRow + ":D" + lastRow, 3);
                exFn.printCellWithColSpan(sampleDataSheet, titleRow, titleFormatNormal, "Default Value", "E" + lastRow + ":E" + lastRow, 4);
                exFn.printCellWithColSpan(sampleDataSheet, titleRow, titleFormatNormal, "Format", "F" + lastRow + ":F" + lastRow, 5);
                exFn.printCell(titleRow, lastLeftLineRow, exFn.getLeftBorder(cellStyleBorderLeft), "");

                String[] tableData = tableContent[i].split(";");

                for ( int l = 0; l < tableData.length; l++ )
                {

                    String[] entryValue = tableData[l].split(",");
                    String beanNatureArray = entryValue[0].trim();
                    String beanNameArray = entryValue[1].trim();
                    String beanDataTypeArray = entryValue[2].trim();
                    String beanDefaultValueArray = entryValue[3].trim();
                    String beanFormatArray = entryValue[4].trim();

                    if (beanDataTypeArray.equals("date[]") || beanDataTypeArray.equals("time[]") || beanDataTypeArray.equals("datetime[]") || beanDataTypeArray.equals("boolean[]"))
                    {
                        beanDataTypeArray = "String[]";
                    }
                    else if (beanDataTypeArray.equals("date") || beanDataTypeArray.equals("time") || beanDataTypeArray.equals("datetime") || beanDataTypeArray.equals("boolean"))
                    {
                        beanDataTypeArray = "String";
                    }
                    else if (beanDataTypeArray.toLowerCase().contains("list<bean>"))
                    {
                        beanDataTypeArray = "List<" + beanNameArray.substring(0, 1).toUpperCase() + beanNameArray.substring(1) + ">";
                    }
                    else if (beanDataTypeArray.toLowerCase().contains("bean"))
                    {
                        beanDataTypeArray = beanNameArray.substring(0, 1).toUpperCase() + beanNameArray.substring(1);
                    }

                    lastRow++;

                    titleRow = sampleDataSheet.createRow(++row);
                    exFn.printCellWithColSpan(sampleDataSheet, titleRow, titleFormat, beanNatureArray, "B" + lastRow + ":B" + lastRow, 1);
                    exFn.printCellWithColSpan(sampleDataSheet, titleRow, titleFormat, beanNameArray, "C" + lastRow + ":C" + lastRow, 2);
                    exFn.printCellWithColSpan(sampleDataSheet, titleRow, titleFormat, beanDataTypeArray, "D" + lastRow + ":D" + lastRow, 3);
                    exFn.printCellWithColSpan(sampleDataSheet, titleRow, titleFormat, beanDefaultValueArray, "E" + lastRow + ":E" + lastRow, 4);
                    exFn.printCellWithColSpan(sampleDataSheet, titleRow, titleFormat, beanFormatArray, "F" + lastRow + ":F" + lastRow, 5);
                    exFn.printCell(titleRow, lastLeftLineRow, exFn.getLeftBorder(cellStyleBorderLeft), "");

                    if ((beanDataTypeArray.equals("String") || beanDataTypeArray.equals("String[]")) && !(beanDefaultValueArray.equals("null")))
                    {
                        tableContentForFile.append("\tprivate ").append(beanDataTypeArray).append(" ").append(beanNameArray).append("=\"").append(beanDefaultValueArray).append("\";\n");
                    }
                    else if (beanDataTypeArray.toLowerCase().startsWith("list<"))
                    {
//                        String[] beanTypeArr = formBean.getBeanType().split(",");
//                        boolean listType = false;
//                        for (int j = 0; j < beanTypeArr.length; j++)
//                        {
//                            if (beanTypeArr[j].toLowerCase().contains(beanNameArray.toLowerCase().trim()) && beanTypeArr[j].startsWith("List"))
//                            {
//                                listType = true;
//                            }
//                        }
//                        if (listType)
//                        {
                        tableContentForFile.append("\tprivate List<").append(beanNameArray.trim().substring(0, 1).toUpperCase()).append(beanNameArray.substring(1).trim()).append("> ").append(beanNameArray).append("=\"").append(beanDefaultValueArray).append("\";\n");
//                        }
                    }
                    else if (beanDataTypeArray.contains(beanNameArray))
                    {
//                        else
//                        {
                        tableContentForFile.append("\tprivate ").append(beanNameArray.trim().substring(0, 1).toUpperCase()).append(beanNameArray.substring(1).trim()).append(" ").append(beanNameArray).append("=\"").append(beanDefaultValueArray).append("\";\n");
//                        }
                    }
                    else
                    {
                        tableContentForFile.append("\tprivate ").append(beanDataTypeArray).append(" ").append(beanNameArray).append("=").append(beanDefaultValueArray).append(";\n");
                    }
                }
                tableContentForFile.append(",");
            }
        }

        for ( int i = 0;
                i < 12; i++ )
        {
            sampleDataSheet.autoSizeColumn(i);
        }

        String excelName = formBean.getWebserviceName();
        File xlsfile = new File(serviceClass + excelName + ".xls");
        FileOutputStream fout = new FileOutputStream(xlsfile.getAbsolutePath());
        zipfile = excelName ;
//        if (formBean.getRdowebchoice().equalsIgnoreCase("rest"))
//            Runtime.getRuntime().exec("chmod 777 -R " + new File(serviceClass + excelName + ".xls"));

//        exFn.downloadExcel(response, excelName);

        List files = new ArrayList();
        files.add(xlsfile.getAbsolutePath());
        sampleWorkbook.write(fout);
        fout.close();

        if (formBean.getRdowebchoice().equalsIgnoreCase("soap"))
        {
            // Generating .java files
            String servicepackageName;

        servicepackageName= "package com.finlogic.eai." + formBean.getProducerPro().replace(" ", "").toLowerCase() + ".ws.producer." +
                formBean.getWebserviceName().toLowerCase().substring(0, formBean.getWebserviceName().toLowerCase().indexOf("service")) + ";\n";

//        List files = new ArrayList();

            File service = new File(serviceClass + formBean.getWebserviceName() + ".java");

//        files.add(xlsfile.getAbsolutePath());
            files.add(service.getAbsolutePath());
//        sampleWorkbook.write(fout);
//
//        fout.close();

            StringBuilder inNames = new StringBuilder();

            inNames.append("");
            StringBuilder inNamesInMethod = new StringBuilder();

            inNamesInMethod.append("");
            StringBuilder inNamesForClass = new StringBuilder();

            inNamesForClass.append("");
            if (!"null".equals(Arrays.toString(formBean.getInparamtable_name())))
            {
                for ( int i = 0; i < inNameArray.length; i++ )
                {
                    inNames.append(inNameArray[i]).append(",");
                    inNamesInMethod.append(inDataTypeArray[i]).append(" ").append(inNameArray[i]).append(",");
                    if ((inDataTypeArray[i].trim().equals("String") || inDataTypeArray[i].trim().equals("String[]")) && !(inDefaultValueArray[i].trim().equals("null")))
                    {
                        inNamesForClass.append("\tprivate ").append(inDataTypeArray[i].trim()).append(" ").append(inNameArray[i].trim()).append("=\"").append(inDefaultValueArray[i].trim()).append("\";\n");
                    }
                    else
                    {
                        inNamesForClass.append("\tprivate ").append(inDataTypeArray[i].trim()).append(" ").append(inNameArray[i].trim()).append("=").append(inDefaultValueArray[i].trim()).append(";\n");
                    }
                }
            }
            StringBuilder outNames = new StringBuilder();

            outNames.append("");
            StringBuilder outNamesInMethod = new StringBuilder();

            outNamesInMethod.append("");
            if (!"null".equals(Arrays.toString(formBean.getOutparamtable_name())))
            {
                for ( int i = 0; i < outNameArray.length; i++ )
                {
                    outNames.append(outNameArray[i]).append(",");
                    outNamesInMethod.append(outDataTypeArray[i]);
                }
            }

            StringBuilder fileData = new StringBuilder();

            fileData.append(servicepackageName);

            fileData.append("import java.util.List;\n");
            fileData.append("import javax.jws.WebService;\n");
            fileData.append("\n");
            fileData.append("@WebService\n");
            fileData.append("public interface ").append(formBean.getWebserviceName()).append("\n");
            fileData.append("{\n");
            fileData.append("\n");
            fileData.append("\t/**\n");
            fileData.append("\t* @param ");

            if (!inNames.toString().equals(""))
            {
                fileData.append(inParam).append(" (").append(inNames.substring(0, inNames.length() - 1)).append(") Or Trainee Code\n");
            }

            fileData.append("\t* Objective : Specify Objective Date : xx/xx/xxxx\n");
            fileData.append("\t* @return : ");

            if (outParam.contains("List"))
            {
                fileData.append(outParam.substring(5, outParam.length() - 1));
            }

            if (!outNames.toString().equals(""))
            {
                fileData.append(" (").append(outNames.substring(0, outNames.length() - 1)).append(")\n");
            }

            fileData.append("\t* @throws Exception\n");
            fileData.append("\t*/\n");

            fileData.append("\tpublic ");

            if (outParam.equals("") && !outNamesInMethod.toString().equals(""))
            {
                fileData.append(outNamesInMethod);
            }

            else if (outParam.equals("") && outNamesInMethod.toString().equals(""))
            {
                fileData.append("void");
            }

            else
            {
                fileData.append(outParam);
            }

//        fileData.append(" ").append(formBean.getMethodName()).append(" ");
            fileData.append(" ").append(formBean.getMethodName());

            if (inParam.equals("") && !inNamesInMethod.toString().equals(""))
            {
                fileData.append("(").append(inNamesInMethod.substring(0, inNamesInMethod.length() - 1)).append(")");
            }

            else
            {
                fileData.append("(").append(inParam).append(" ").append(inParam.toLowerCase()).append(")");
            }

            fileData.append(" throws Exception;\n");
            fileData.append("}");

            java.io.FileWriter serviceJava = new java.io.FileWriter(service, false);
            java.io.PrintWriter serviceJavaPrint = new java.io.PrintWriter(serviceJava);

            serviceJavaPrint.print(fileData);

            serviceJavaPrint.close();

            serviceJava.close();

            //In Parameter GetterSetter
            if (inParam.length() > 0)
            {
                File inParamJava = new File(serviceClass + inParam + ".java");

                java.io.FileWriter inJava = new java.io.FileWriter(inParamJava, false);
                java.io.PrintWriter inJavaPrint = new java.io.PrintWriter(inJava);

                inJavaPrint.print(servicepackageName);
                inJavaPrint.print("public class " + inParam + "\n");
                inJavaPrint.print("{\n");
                inJavaPrint.print(inNamesForClass);
                inJavaPrint.print("\n");
                String[] dataType = Arrays.toString(formBean.getInparamtable_dataType()).substring(1, Arrays.toString(formBean.getInparamtable_dataType()).length() - 1).split(",");
                inJavaPrint.print(getterSetter(inNameArray, dataType));
                inJavaPrint.print("\n}");

                inJavaPrint.close();
                inJava.close();
                files.add(inParamJava.getAbsolutePath());
            }

            //OutParameter gettersetter
            if (formBean.getOutParam().length() > 0)
            {
                StringBuilder methodVariables = new StringBuilder();
                if (!"null".equals(Arrays.toString(formBean.getOutparamtable_name())))
                {
                    for ( int i = 0; i < outNameArray.length; i++ )
                    {
                        if ((outDataTypeArray[i].trim().equals("String") || outDataTypeArray[i].trim().equals("String")) && !(outDefaultValueArray[i].trim().equals("null")))
                        {
                            methodVariables.append("\tprivate ").append(outDataTypeArray[i].trim()).append(" ").append(outNameArray[i].trim()).append("=\"").append(outDefaultValueArray[i].trim()).append("\";\n");
                        }
                        else
                        {
                            methodVariables.append("\tprivate ").append(outDataTypeArray[i].trim()).append(" ").append(outNameArray[i].trim()).append("=").append(outDefaultValueArray[i].trim()).append(";\n");
                        }
                    }
                }

                File outParamJava = new File(serviceClass + formBean.getOutParam() + ".java");

                java.io.FileWriter outJava = new java.io.FileWriter(outParamJava, false);
                java.io.PrintWriter outJavaPrint = new java.io.PrintWriter(outJava);

                outJavaPrint.print(servicepackageName);
                outJavaPrint.print("public class " + formBean.getOutParam() + "\n");
                outJavaPrint.print("{\n");
                outJavaPrint.print(methodVariables);
                outJavaPrint.print("\n");
                String[] dataType = Arrays.toString(formBean.getOutparamtable_dataType()).substring(1, Arrays.toString(formBean.getOutparamtable_dataType()).length() - 1).split(",");
                outJavaPrint.print(getterSetter(outNameArray, dataType));
//            outJavaPrint.print(getterSetter(outNameArray, dataType, formBean.getBeanType()));
                outJavaPrint.print("\n}");

                outJavaPrint.close();
                outJava.close();
                files.add(outParamJava.getAbsolutePath());
            }

            //bean java files
            if (!"".equals(tableNamesForFile))
            {
                String[] tableNames = tableNamesForFile.split(",");
                String[] beantableContent = tableContentForFile.toString().split(",");
                String[] tableContent = formBean.getBeanTableData().split("\\|");
                for ( int i = 0; i < tableNames.length; i++ )
                {
                    File beanParamJava = new File(serviceClass + tableNames[i] + ".java");

                    java.io.FileWriter beanJava = new java.io.FileWriter(beanParamJava, false);
                    java.io.PrintWriter beanJavaPrint = new java.io.PrintWriter(beanJava);

                    beanJavaPrint.print(servicepackageName);
                    beanJavaPrint.print("public class " + tableNames[i] + "\n");
                    beanJavaPrint.print("{\n");

                    beanJavaPrint.print(beantableContent[i]);

                    String[] allContent = tableContent[i].split(";");
                    StringBuilder beanData = new StringBuilder();

                    for ( int j = 0; j < allContent.length; j++ )
                    {
                        String[] str = allContent[j].split(",");
                        String beanNameData = str[1].trim();
                        String beanDataType = str[2].trim();
                        String changeFormat = "";

                        beanData.setLength(0);

                        if (beanDataType.equals("date") || beanDataType.equals("time") || beanDataType.equals("datetime") || beanDataType.equals("boolean"))
                        {
                            changeFormat = "String";
                        }
                        else if (beanDataType.equals("date[]") || beanDataType.equals("time[]") || beanDataType.equals("datetime[]") || beanDataType.equals("boolean[]"))
                        {
                            changeFormat = "String[]";
                        }
                        else if (beanDataType.toLowerCase().contains("list<bean>"))
                        {
//                        String[] beanTypeArr = formBean.getBeanType().split(",");
//                        boolean listType = false;
//                        for (int k = 0; k < beanTypeArr.length; j++)
//                        {
//                            if (beanTypeArr[k].toLowerCase().contains(beanNameData.toLowerCase().trim()) && beanTypeArr[j].startsWith("List"))
//                            {
//                                listType = true;
//                            }
//                        }
//                        if (listType)
//                        {
                            changeFormat = "List<" + beanNameData.trim().substring(0, 1).toUpperCase() + beanNameData.substring(1).trim() + ">";
//                        }
                        }
                        else if (beanDataType.toLowerCase().contains("bean"))
                        {
//                        else
//                        {
                            changeFormat = beanNameData.trim().substring(0, 1).toUpperCase() + beanNameData.substring(1).trim();
//                        }
                        }
                        else
                        {
                            changeFormat = beanDataType;
                        }

                        String firstCapName = beanNameData.trim().substring(0, 1).toUpperCase() + beanNameData.substring(1).trim();
                        beanData.append("\tpublic ").append(changeFormat).append(" get").append(firstCapName).append("()");
                        beanData.append("\n\t{\n");
                        beanData.append("\t\treturn ").append(beanNameData).append(";");
                        beanData.append("\n\t}\n");

                        beanData.append("\tpublic void").append(" set").append(firstCapName).append("(").append(changeFormat).append(" ").append(beanNameData).append(")");
                        beanData.append("\n\t{\n");
                        if (beanDataType.toLowerCase().equals("date") || beanDataType.toLowerCase().equals("date[]"))
                        {
                            beanData.append("\t\tWSValidation.isDateFormat(").append(beanNameData).append(");\n");
                        }
                        else if (beanDataType.toLowerCase().equals("datetime") || beanDataType.toLowerCase().equals("datetime[]"))
                        {
                            beanData.append("\t\tWSValidation.isDateTimeFormat(").append(beanNameData).append(");\n");
                        }
                        else if (beanDataType.toLowerCase().equals("time") || beanDataType.toLowerCase().equals("time[]"))
                        {
                            beanData.append("\t\tWSValidation.isTimeFormat(").append(beanNameData).append(");\n");
                        }
                        else if (beanDataType.toLowerCase().equals("boolean") || beanDataType.toLowerCase().equals("boolean[]"))
                        {
                            beanData.append("\t\tWSValidation.isBooleanFormat(").append(beanNameData).append(");\n");
                        }
                        beanData.append("\t\tthis.").append(beanNameData).append("=").append(beanNameData).append(";");
                        beanData.append("\n\t}\n");

                        beanJavaPrint.print(beanData);
                    }

                    beanJavaPrint.print("\n}");

                    beanJavaPrint.close();
                    beanJava.close();
                    files.add(beanParamJava.getAbsolutePath());
                }
            }

            //Consumer file
            String serviceName = formBean.getWebserviceName();
            String consumerName = formBean.getWebserviceName().replace("Service", "");
            consumerName = consumerName.substring(0, 1).toUpperCase() + consumerName.substring(1) + "Consumer";
            String beanValue = serviceName.substring(0, 1).toLowerCase() + serviceName.substring(1);
            StringBuilder consumerData = new StringBuilder();

            consumerData.append(
                    "\n");
            consumerData.append(
                    "package com.finlogic.eai.ws.consumer.").append(formBean.getProducerPro().replace(" ", "").toLowerCase()).append(";\n");
            consumerData.append(
                    "import com.finlogic.eai.ws.consumer.ContextReader;\n");
            consumerData.append(
                    "import ").append(servicepackageName.substring(8, servicepackageName.length() - 2)).append(".*").append(";\n");
            consumerData.append(
                    "import java.util.List;\n");
            consumerData.append(
                    "\n");
            consumerData.append(
                    "public class ").append(consumerName).append("\n");
            consumerData.append(
                    "{\n");
            consumerData.append(
                    "    // Read the configuration file    \n");
            consumerData.append(
                    "\n");
            consumerData.append(
                    "    private static ContextReader cr = null;\n");
            consumerData.append(
                    "    //Instantiate an object\n");
            consumerData.append(
                    "    private ").append(serviceName).append(" service = null;\n");
            consumerData.append(
                    "\n");
            consumerData.append(
                    "    static\n");
            consumerData.append(
                    "    {\n");
            consumerData.append(
                    "        cr = new ContextReader();\n");
            consumerData.append(
                    "    }\n");
            consumerData.append(
                    "\n");
            consumerData.append(
                    "\n");

            consumerData.append(
                    "    public ").append(consumerName).append("()\n");
            consumerData.append(
                    "    {\n");
            consumerData.append(
                    "        service = (").append(serviceName).append(") cr.getBean(\"").append(beanValue).append("\");\n");
            consumerData.append(
                    "    }\n");
            consumerData.append(
                    "\n");

            consumerData.append(
                    "\t/**\n");
            consumerData.append(
                    "\t* @param ");

            if (!inParam.equals(
                    "") || !inNames.toString().equals(""))
            {
                consumerData.append(inParam).append(" (").append(inNames.substring(0, inNames.length() - 1)).append(") Or Trainee Code\n");
            }

            consumerData.append(
                    "\t* Objective : Specify Objective Date : xx/xx/xxxx\n");
            consumerData.append(
                    "\t* @return : ");

            if (outParam.contains(
                    "List"))
            {
                consumerData.append(outParam.substring(5, outParam.length() - 1));
            }

            if (!outNames.toString()
                    .equals(""))
            {
                consumerData.append(" (").append(outNames.substring(0, outNames.length() - 1)).append(")\n");
            }

            consumerData.append(
                    "\t* @throws Exception\n");
            consumerData.append(
                    "\t*/\n");

            consumerData.append(
                    "    public ");

            if (outParam.equals(
                    "") && !outNamesInMethod.toString().equals(""))
            {
                consumerData.append(outNamesInMethod);
            }

            else if (outParam.equals(
                    "") && outNamesInMethod.toString().equals(""))
            {
                consumerData.append("void");
            }

            else
            {
                consumerData.append(outParam);
            }

//        consumerData.append(" ").append(formBean.getMethodName()).append(" ");
            consumerData.append(
                    " ").append(formBean.getMethodName());

            if (inParam.equals(
                    "") && !inNamesInMethod.toString().equals(""))
            {
                consumerData.append("(").append(inNamesInMethod.substring(0, inNamesInMethod.length() - 1)).append(")");
            }

            else
            {
                consumerData.append("(").append(inParam).append(" ").append(inParam.toLowerCase()).append(")");
            }

            consumerData.append(
                    " throws Exception\n");
            consumerData.append(
                    "  \t{\n"
                    + "        \ttry\n"
                    + "        \t{\n"
                    + "            \treturn service." + formBean.getMethodName());
            if (inParam.equals(
                    "") && !inNamesInMethod.toString().equals(""))
            {
                consumerData.append("(").append(inNamesInMethod.substring(inNamesInMethod.indexOf(" ") + 1, inNamesInMethod.length() - 1)).append(")");
            }

            else
            {
                consumerData.append("(").append(inParam.toLowerCase()).append(")");
            }

            consumerData.append(
                    ";\n\t}\n"
                    + "       \t catch (Exception e)\n"
                    + "        \t{\n"
                    + "          \t  service = (" + serviceName + ") cr.getBean(\"" + beanValue + "\");\n"
                    + "            \treturn service." + formBean.getMethodName());

            if (inParam.equals(
                    "") && !inNamesInMethod.toString().equals(""))
            {
                consumerData.append("(").append(inNamesInMethod.substring(inNamesInMethod.indexOf(" ") + 1, inNamesInMethod.length() - 1)).append(")");
            }

            else
            {
                consumerData.append("(").append(inParam.toLowerCase()).append(")");
            }

            consumerData.append(
                    ";\n        }\n"
                    + "}\n"
                    + "}");

            File consumer = new File(serviceClass + consumerName + ".java");
            java.io.FileWriter consumerJava = new java.io.FileWriter(consumer, false);
            java.io.PrintWriter consumerJavaPrint = new java.io.PrintWriter(consumerJava);

            consumerJavaPrint.print(consumerData);

            consumerJavaPrint.close();

            consumerJava.close();

            files.add(consumer.getAbsolutePath());

            //WSConsumer file
            StringBuilder WSEntry = new StringBuilder();

            WSEntry.append(
                    "<!-- ").append(serviceName).append(" -->\n"
                            + "\n"
                            + "    <bean id=\"" + serviceName.substring(0, 1).toLowerCase() + serviceName.substring(1) + "Factory\" class=\"org.apache.cxf.jaxws.JaxWsProxyFactoryBean\">\n"
                            + "        <property name=\"serviceClass\" value=\"" + servicepackageName.substring(8, servicepackageName.length() - 2) + "." + serviceName + "\"/>\n"
                            + "        <property name=\"address\" value=\"http://<Server Name>/" + formBean.getProducerPro().replace(" ", "").toLowerCase() + "/ws/" + serviceName.toLowerCase() + "?wsdl\"/>\n"
                            + "        <property name=\"serviceFactory\" ref=\"jaxwsAndAegisServiceFactory\"/>\n"
                            + "    </bean>\n"
                            + "\n"
                            + "    <bean id=\"" + serviceName.substring(0, 1).toLowerCase() + serviceName.substring(1) + "\"\n"
                            + "          class=\"" + servicepackageName.substring(8, servicepackageName.length() - 2) + "." + serviceName + "\"\n"
                            + "          factory-bean=\"" + serviceName.substring(0, 1).toLowerCase() + serviceName.substring(1) + "Factory\"\n"
                            + "          factory-method=\"create\"/>");

            File WSconsumer = new File(serviceClass + "WSConsumer.xml");
            java.io.FileWriter wsconsumerJava = new java.io.FileWriter(WSconsumer, false);
            java.io.PrintWriter wsconsumerJavaPrint = new java.io.PrintWriter(wsconsumerJava);

            wsconsumerJavaPrint.print(WSEntry);

            wsconsumerJavaPrint.close();

            wsconsumerJava.close();

            files.add(WSconsumer.getAbsolutePath());
            generateZip(files, formBean.getWebserviceName());
        }
    }

    public void generateZip(List files, String zipName)
    {
        try
        {
            String zipFile = serviceClass + zipName + ".zip";

            byte[] buffer = new byte[1024];

            FileOutputStream fout = new FileOutputStream(zipFile);
            ZipOutputStream zout = new ZipOutputStream(fout);

            for ( int i = 0; i < files.size(); i++ )
            {

                String fileName = (String) files.get(i);

                Logger.DataLogger("Adding : " + fileName);
                FileInputStream fin = new FileInputStream(fileName);
                zout.putNextEntry(new ZipEntry(fileName.substring(fileName.lastIndexOf("/"))));

                int length;

                while ( (length = fin.read(buffer)) > 0 )
                {
                    zout.write(buffer, 0, length);
                }

                zout.closeEntry();
                fin.close();
                new File(fileName).delete();
            }
            zout.close();
            Logger.DataLogger("Zip file has been created!");
//            Runtime.getRuntime().exec("chmod 777 -R " + new File(zipFile));

            zipfile = zipName;
        }
        catch ( Exception e )
        {
            Logger.DataLogger("Error in create Zip : " + e);
        }
    }

    public String getterSetter(String[] name, String[] dataType)
    {
        StringBuilder str = new StringBuilder();
//        Logger.DataLogger(beanType);
//        String[] beanTypeArr = beanType.split(",");
        for ( int i = 0; i < name.length; i++ )
        {
            String insertName = name[i].trim();
            String insertDataType = dataType[i].trim();
            String changeFormat = "";
            if (insertDataType.equals("date") || insertDataType.equals("time") || insertDataType.equals("datetime") || insertDataType.equals("boolean"))
            {
                changeFormat = "String";
            }
            else if (insertDataType.equals("date[]") || insertDataType.equals("time[]") || insertDataType.equals("datetime[]") || insertDataType.equals("boolean[]"))
            {
                changeFormat = "String[]";
            }
            else
            {
                changeFormat = insertDataType;
            }

            String firstCapName = insertName.substring(0, 1).toUpperCase() + insertName.substring(1);
//            if (insertDataType.toLowerCase().contains("bean") || insertDataType.toLowerCase().contains("bean[]"))
            if (insertDataType.toLowerCase().contains("list<bean>"))
            {
//                boolean listType = false;
//                for (int j = 0; j < beanTypeArr.length; j++)
//                {
//                    if (beanTypeArr[j].contains(firstCapName) && beanTypeArr[j].startsWith("List"))
//                    {
//                        listType = true;
//                    }
//                }
//                if (listType)
//                {
                str.append("\tpublic ").append("List<").append(firstCapName).append(">").append(" get").append(firstCapName).append("()");
                str.append("\n\t{\n");
                str.append("\t\treturn ").append(insertName).append(";");
                str.append("\n\t}\n");
                str.append("\tpublic void").append(" set").append(firstCapName).append("(List<").append(firstCapName).append("> ").append(insertName).append(")");
                str.append("\n\t{\n");
            }
            else if (insertDataType.toLowerCase().contains("bean"))
            {

//                }
//                else
//                {
                str.append("\tpublic ").append(firstCapName).append(" get").append(firstCapName).append("()");
                str.append("\n\t{\n");
                str.append("\t\treturn ").append(insertName).append(";");
                str.append("\n\t}\n");
                str.append("\tpublic void").append(" set").append(firstCapName).append("(").append(firstCapName).append(" ").append(insertName).append(")");
                str.append("\n\t{\n");
            }
            else
            {
                str.append("\tpublic ").append(changeFormat).append(" get").append(firstCapName).append("()");
                str.append("\n\t{\n");
                str.append("\t\treturn ").append(insertName).append(";");
                str.append("\n\t}\n");
                str.append("\tpublic void").append(" set").append(firstCapName).append("(").append(changeFormat).append(" ").append(insertName).append(")");
                str.append("\n\t{\n");
            }

            if (insertDataType.toLowerCase().equals("date") || insertDataType.toLowerCase().equals("date[]"))
            {
                str.append("\t\tWSValidation.isDateFormat(").append(insertName).append(");\n");
            }
            else if (insertDataType.toLowerCase().equals("datetime") || insertDataType.toLowerCase().equals("datetime[]"))
            {
                str.append("\t\tWSValidation.isDateTimeFormat(").append(insertName).append(");\n");
            }
            else if (insertDataType.toLowerCase().equals("time") || insertDataType.toLowerCase().equals("time[]"))
            {
                str.append("\t\tWSValidation.isTimeFormat(").append(insertName).append(");\n");
            }
            else if (insertDataType.toLowerCase().equals("boolean") || insertDataType.toLowerCase().equals("boolean[]"))
            {
                str.append("\t\tWSValidation.isBooleanFormat(").append(insertName).append(");\n");
            }
//            if (insertDataType.toLowerCase().equals("bean") || insertDataType.toLowerCase().equals("bean[]"))
//            {
//                str.append("\t\tthis.").append(insertName).append("=").append("(List<").append(firstCapName).append(">)").append(insertName).append(";");
//            }
            else
            {
                str.append("\t\tthis.").append(insertName).append("=").append(insertName).append(";");
            }
            str.append("\n\t}\n");
        }

        return str.toString();
    }

    public ModelAndView downloadFile(HttpServletRequest req, HttpServletResponse res)
    {
        ModelAndView mv = new ModelAndView("webservicedoc/download");
        try
        {
            mv.addObject("fileName", req.getParameter("fileName"));
            mv.addObject("path", serviceClass);
            mv.addObject("type", req.getParameter("type"));
        }
        catch ( Exception exception )
        {
            Logger.ErrorLogger(exception);
        }
        return mv;
    }
}
