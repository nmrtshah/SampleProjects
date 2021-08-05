/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.apps.finstudio.webservicedoc;

import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Font;

/**
 *
 * @author njuser
 */
public class CommonExcelFunctions
{

    HSSFWorkbook sampleWorkBook = null;

    public CommonExcelFunctions(HSSFWorkbook sampleWorkBook)
    {
        this.sampleWorkBook = sampleWorkBook;
    }

    public HSSFCellStyle setHeaderStyleNormalCenter(HSSFCellStyle cellStyle)
    {
        cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
        cellStyle.setFont(this.getNormalFont());
        return cellStyle;
    }

    public HSSFCellStyle setHeaderStyleBoldLeft(HSSFCellStyle cellStyle)
    {
        cellStyle.setAlignment(cellStyle.ALIGN_LEFT);
        cellStyle.setFont(this.getBoldFont());
        return cellStyle;
    }

    public List getCommonListForExcel()
    {
        List testList = new ArrayList();
        testList.add(0, "A");
        testList.add(1, "B");
        testList.add(2, "C");
        testList.add(3, "D");
        testList.add(4, "E");
        testList.add(5, "F");
        testList.add(6, "G");
        testList.add(7, "H");
        testList.add(8, "I");
        testList.add(9, "J");
        testList.add(10, "K");
        testList.add(11, "L");
        testList.add(12, "M");
        testList.add(13, "N");
        testList.add(14, "O");
        testList.add(15, "P");
        testList.add(16, "Q");
        testList.add(17, "R");
        testList.add(18, "S");
        testList.add(19, "T");
        testList.add(20, "U");
        testList.add(21, "V");
        testList.add(22, "W");
        testList.add(23, "X");
        testList.add(24, "Y");
        testList.add(25, "Z");
        testList.add(26, "AA");
        testList.add(27, "AB");
        testList.add(28, "AC");
        testList.add(29, "AD");
        testList.add(30, "AE");
        testList.add(31, "AF");
        testList.add(32, "AG");
        testList.add(33, "AH");
        testList.add(34, "AI");
        testList.add(35, "AJ");
        testList.add(36, "AK");
        testList.add(37, "AL");
        testList.add(38, "AM");
        testList.add(39, "AN");
        testList.add(40, "A0");
        testList.add(41, "AP");
        testList.add(42, "AQ");
        testList.add(43, "AR");
        testList.add(44, "AS");
        testList.add(45, "AT");
        testList.add(46, "AU");
        testList.add(47, "AV");
        testList.add(48, "AW");
        testList.add(49, "AX");
        testList.add(50, "AY");
        testList.add(51, "AZ");
        testList.add(52, "BA");
        testList.add(53, "BB");
        testList.add(54, "BC");
        testList.add(55, "BD");
        testList.add(56, "BE");
        testList.add(57, "BF");
        testList.add(58, "BG");
        testList.add(59, "BH");
        testList.add(60, "BI");
        return testList;
    }

    public HSSFCellStyle getColHeadCellFmt(HSSFCellStyle colHeadCellFmt)
    {
        colHeadCellFmt.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        colHeadCellFmt.setLeftBorderColor(HSSFColor.GREY_50_PERCENT.index);
        colHeadCellFmt.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        colHeadCellFmt.setBottomBorderColor(HSSFColor.GREY_50_PERCENT.index);
        colHeadCellFmt.setBorderRight(HSSFCellStyle.BORDER_THIN);
        colHeadCellFmt.setRightBorderColor(HSSFColor.GREY_50_PERCENT.index);
        colHeadCellFmt.setBorderTop(HSSFCellStyle.BORDER_THIN);
        colHeadCellFmt.setTopBorderColor(HSSFColor.GREY_50_PERCENT.index);
        colHeadCellFmt.setFont(this.getBoldFont());
        colHeadCellFmt.setFillForegroundColor(setColor((byte) 240, (byte) 240, (byte) 240));
        colHeadCellFmt.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        colHeadCellFmt.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        return colHeadCellFmt;
    }

    public HSSFCellStyle getColTitleCellFmtLeft(HSSFCellStyle colTitleCellFmt)
    {
        colTitleCellFmt.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        colTitleCellFmt.setLeftBorderColor(HSSFColor.GREY_50_PERCENT.index);
        colTitleCellFmt.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        colTitleCellFmt.setBottomBorderColor(HSSFColor.GREY_50_PERCENT.index);
        colTitleCellFmt.setBorderRight(HSSFCellStyle.BORDER_THIN);
        colTitleCellFmt.setRightBorderColor(HSSFColor.GREY_50_PERCENT.index);
        colTitleCellFmt.setBorderTop(HSSFCellStyle.BORDER_THIN);
        colTitleCellFmt.setTopBorderColor(HSSFColor.GREY_50_PERCENT.index);
        colTitleCellFmt.setFont(this.getBoldFont());
        colTitleCellFmt.setFillForegroundColor(setColor((byte) 240, (byte) 240, (byte) 240));
        colTitleCellFmt.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        colTitleCellFmt.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        return colTitleCellFmt;
    }

    public Font getBoldFont()
    {
        Font boldFont = sampleWorkBook.createFont();
        boldFont.setFontName("Arial");
        boldFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        return boldFont;
    }

    public Font getNormalFont()
    {
        Font normalFont = sampleWorkBook.createFont();
        normalFont.setFontName("Arial");
        normalFont.setBoldweight(Font.BOLDWEIGHT_NORMAL);
        return normalFont;
    }

    public short setColor(byte red, byte green, byte blue)
    {
        HSSFPalette palette = sampleWorkBook.getCustomPalette();
        HSSFColor hssfColor = null;

        hssfColor = palette.findColor(red, green, blue);
        if (hssfColor == null)
        {
            palette.setColorAtIndex(HSSFColor.LAVENDER.index, red, green, blue);
            hssfColor = palette.getColor(HSSFColor.LAVENDER.index);
        }
        return hssfColor.getIndex();
    }

    public void printCell(HSSFRow dataRow, int indexContent, HSSFCellStyle cellStyleNormalCenter, String value)
    {
        HSSFCell dataCell = dataRow.createCell(indexContent);
        dataCell.setCellStyle(cellStyleNormalCenter);

        dataCell.setCellValue(new HSSFRichTextString(value));
    }

    public void printSingleRowHeader(HSSFSheet sampleDataSheet, HSSFRow headerRow, HSSFCellStyle headerFormat, String[] header)
    {
        HSSFCell headerCell = null;
        for (int i = 0; i < header.length; i++)
        {
            sampleDataSheet.setColumnWidth(i, 4000);
            headerCell = headerRow.createCell(i);
            headerCell.setCellValue(new HSSFRichTextString(header[i]));
            headerCell.setCellStyle(headerFormat);
        }
    }

    public void printCellWithColSpan(HSSFSheet sampleDataSheet, HSSFRow row, HSSFCellStyle format, String value, String region, int colNo)
    {
        HSSFCell cell = row.createCell(colNo);
        org.apache.poi.ss.util.CellRangeAddress SD = org.apache.poi.ss.util.CellRangeAddress.valueOf(region);
        cell.setCellValue(new HSSFRichTextString(value));
        cell.setCellStyle(format);
        sampleDataSheet.addMergedRegion(SD);
    }

    public void mergeCell(HSSFSheet sampleDataSheet, HSSFRow row, HSSFCellStyle format, String region, int colNo)
    {
        HSSFCell cell = row.getCell(colNo);
        org.apache.poi.ss.util.CellRangeAddress SD = org.apache.poi.ss.util.CellRangeAddress.valueOf(region);
        cell.setCellStyle(format);
        sampleDataSheet.addMergedRegion(SD);
    }

    public HSSFCellStyle getColTitleCellFmtBoldRight(HSSFCellStyle colTitleCellFmt)
    {
        colTitleCellFmt.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        colTitleCellFmt.setLeftBorderColor(HSSFColor.GREY_50_PERCENT.index);
        colTitleCellFmt.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        colTitleCellFmt.setBottomBorderColor(HSSFColor.GREY_50_PERCENT.index);
        colTitleCellFmt.setBorderRight(HSSFCellStyle.BORDER_THIN);
        colTitleCellFmt.setRightBorderColor(HSSFColor.GREY_50_PERCENT.index);
        colTitleCellFmt.setBorderTop(HSSFCellStyle.BORDER_THIN);
        colTitleCellFmt.setTopBorderColor(HSSFColor.GREY_50_PERCENT.index);
        colTitleCellFmt.setFont(this.getBoldFont());
        colTitleCellFmt.setFillForegroundColor(setColor((byte) 240, (byte) 240, (byte) 240));
        colTitleCellFmt.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        colTitleCellFmt.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        return colTitleCellFmt;
    }

    public HSSFCellStyle getSilverCellFmtCenter(HSSFCellStyle cellStyle)
    {
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setLeftBorderColor(HSSFColor.GREY_50_PERCENT.index);
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBottomBorderColor(HSSFColor.GREY_50_PERCENT.index);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle.setRightBorderColor(HSSFColor.GREY_50_PERCENT.index);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyle.setTopBorderColor(HSSFColor.GREY_50_PERCENT.index);
        cellStyle.setFont(this.getNormalFont());
        cellStyle.setFillForegroundColor(setColor((byte) 192, (byte) 192, (byte) 192));
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        return cellStyle;
    }

    public HSSFCellStyle getSilverCellFmtLeft(HSSFCellStyle cellStyle)
    {
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setLeftBorderColor(HSSFColor.GREY_50_PERCENT.index);
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBottomBorderColor(HSSFColor.GREY_50_PERCENT.index);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle.setRightBorderColor(HSSFColor.GREY_50_PERCENT.index);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyle.setTopBorderColor(HSSFColor.GREY_50_PERCENT.index);
        cellStyle.setFont(this.getBoldFont());
        cellStyle.setFillForegroundColor(setColor((byte) 192, (byte) 192, (byte) 192));
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        return cellStyle;
    }

    public HSSFCellStyle getSilverCellFmtRight(HSSFCellStyle cellStyle)
    {
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setLeftBorderColor(HSSFColor.GREY_50_PERCENT.index);
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBottomBorderColor(HSSFColor.GREY_50_PERCENT.index);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle.setRightBorderColor(HSSFColor.GREY_50_PERCENT.index);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyle.setTopBorderColor(HSSFColor.GREY_50_PERCENT.index);
        cellStyle.setFont(this.getBoldFont());
        cellStyle.setFillForegroundColor(setColor((byte) 192, (byte) 192, (byte) 192));
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        return cellStyle;
    }

    public HSSFCellStyle getBlackCellFmtCenter(HSSFCellStyle cellStyle)
    {
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setLeftBorderColor(HSSFColor.GREY_50_PERCENT.index);
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBottomBorderColor(HSSFColor.GREY_50_PERCENT.index);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle.setRightBorderColor(HSSFColor.GREY_50_PERCENT.index);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyle.setTopBorderColor(HSSFColor.GREY_50_PERCENT.index);
        cellStyle.setFont(this.getBoldFont());
        cellStyle.setFillForegroundColor(setColor((byte) 60, (byte) 60, (byte) 60));
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        return cellStyle;
    }

    public HSSFCellStyle getBlackCellFmtLeft(HSSFCellStyle cellStyle)
    {
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setLeftBorderColor(HSSFColor.GREY_50_PERCENT.index);
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBottomBorderColor(HSSFColor.GREY_50_PERCENT.index);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle.setRightBorderColor(HSSFColor.GREY_50_PERCENT.index);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyle.setTopBorderColor(HSSFColor.GREY_50_PERCENT.index);
        cellStyle.setFont(this.getBoldFont());
        cellStyle.setFillForegroundColor(setColor((byte) 60, (byte) 60, (byte) 60));
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        return cellStyle;
    }

    public HSSFCellStyle getBlackCellFmtRight(HSSFCellStyle cellStyle)
    {
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setLeftBorderColor(HSSFColor.GREY_50_PERCENT.index);
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBottomBorderColor(HSSFColor.GREY_50_PERCENT.index);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle.setRightBorderColor(HSSFColor.GREY_50_PERCENT.index);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyle.setTopBorderColor(HSSFColor.GREY_50_PERCENT.index);
        cellStyle.setFont(this.getNormalFont());
        cellStyle.setFillForegroundColor(setColor((byte) 60, (byte) 60, (byte) 60));
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        return cellStyle;
    }

    public HSSFCellStyle getWhiteCellFmtCenter(HSSFCellStyle cellStyle)
    {
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setLeftBorderColor(HSSFColor.GREY_50_PERCENT.index);
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBottomBorderColor(HSSFColor.GREY_50_PERCENT.index);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle.setRightBorderColor(HSSFColor.GREY_50_PERCENT.index);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyle.setTopBorderColor(HSSFColor.GREY_50_PERCENT.index);
        cellStyle.setFont(this.getNormalFont());
        cellStyle.setFillForegroundColor(setColor((byte) 255, (byte) 255, (byte) 255));
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        return cellStyle;
    }

    public HSSFCellStyle getWhiteCellNormalFmtCenter(HSSFCellStyle cellStyle)
    {
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setLeftBorderColor(HSSFColor.GREY_50_PERCENT.index);
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBottomBorderColor(HSSFColor.GREY_50_PERCENT.index);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle.setRightBorderColor(HSSFColor.GREY_50_PERCENT.index);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyle.setTopBorderColor(HSSFColor.GREY_50_PERCENT.index);
        cellStyle.setFont(this.getNormalFont());
        cellStyle.setFillForegroundColor(setColor((byte) 150, (byte) 150, (byte) 150));
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        return cellStyle;
    }

    public HSSFCellStyle getVerticalAlignFmtCenter(HSSFCellStyle cellStyle)
    {
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setLeftBorderColor(HSSFColor.GREY_50_PERCENT.index);
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBottomBorderColor(HSSFColor.GREY_50_PERCENT.index);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle.setRightBorderColor(HSSFColor.GREY_50_PERCENT.index);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyle.setTopBorderColor(HSSFColor.GREY_50_PERCENT.index);
        cellStyle.setFont(this.getNormalFont());
        cellStyle.setFillForegroundColor(setColor((byte) 255, (byte) 255, (byte) 255));
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        return cellStyle;
    }

    public HSSFCellStyle getWhiteCellFmtLeft(HSSFCellStyle cellStyle)
    {
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setLeftBorderColor(HSSFColor.GREY_50_PERCENT.index);
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBottomBorderColor(HSSFColor.GREY_50_PERCENT.index);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle.setRightBorderColor(HSSFColor.GREY_50_PERCENT.index);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyle.setTopBorderColor(HSSFColor.GREY_50_PERCENT.index);
        cellStyle.setFont(this.getNormalFont());
        cellStyle.setFillForegroundColor(setColor((byte) 255, (byte) 255, (byte) 255));
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        return cellStyle;
    }

    public HSSFCellStyle getWhiteCellFmtRight(HSSFCellStyle cellStyle)
    {
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setLeftBorderColor(HSSFColor.GREY_50_PERCENT.index);
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBottomBorderColor(HSSFColor.GREY_50_PERCENT.index);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle.setRightBorderColor(HSSFColor.GREY_50_PERCENT.index);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyle.setTopBorderColor(HSSFColor.GREY_50_PERCENT.index);
        cellStyle.setFont(this.getNormalFont());
        cellStyle.setFillForegroundColor(setColor((byte) 255, (byte) 255, (byte) 255));
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        return cellStyle;
    }

    public HSSFCellStyle getRedBoldLeft(HSSFCellStyle cellStyle)
    {
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setLeftBorderColor(HSSFColor.GREY_50_PERCENT.index);
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBottomBorderColor(HSSFColor.GREY_50_PERCENT.index);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle.setRightBorderColor(HSSFColor.GREY_50_PERCENT.index);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyle.setTopBorderColor(HSSFColor.GREY_50_PERCENT.index);
        cellStyle.setFont(this.getRedFont());
        cellStyle.setFillForegroundColor(setColor((byte) 255, (byte) 255, (byte) 255));
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        return cellStyle;
    }

    public HSSFCellStyle getLeftBorder(HSSFCellStyle cellStyle)
    {
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setLeftBorderColor(HSSFColor.GREY_50_PERCENT.index);
        return cellStyle;
    }

    public Font getRedFont()
    {
        Font headerFont = sampleWorkBook.createFont();
        headerFont.setFontName("Arial");
        headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        headerFont.setColor(setColor((byte) 255, (byte) 0, (byte) 0));
        return headerFont;
    }

    public String decimalFormat(String value, int minFractionDigits)
    {
        if (value.equalsIgnoreCase("0"))
        {
            value = "0." + appendZeros(minFractionDigits);;
            return value;
        }
        if (value.equalsIgnoreCase("N/A"))
        {
            value = "N/A";
            return value;
        }
        if (value.indexOf(',') != -1)
        {
            value = value.replace(",", "");
        }
        double tempValue = Double.parseDouble(value);
        boolean isNegative = false;
        if (tempValue < 0)
        {
            tempValue = tempValue * -1;
            isNegative = true;
        }
        int decimal = minFractionDigits;
        String digitFormat = makeFormat(decimal);
        DecimalFormat formatter = new DecimalFormat(digitFormat);
        String formattedValue = formatter.format(tempValue);
        String integral = formattedValue.replaceAll("\\D\\d++", "");
        String fraction = ".";
        if (decimal > 0)
        {
            DecimalFormat decimalFormatter = new DecimalFormat(digitFormat);
            String value1 = decimalFormatter.format(tempValue);
            if (value1.indexOf('.') != -1)
            {
                fraction += value1.substring(value1.indexOf('.') + 1);
            }
            else
            {
                fraction += appendZeros(decimal);
            }
        }
        if (integral.length() <= 3)
        {
            formattedValue = String.valueOf(Double.parseDouble(formattedValue));
            if (isNegative)
            {
                formattedValue = "-" + formattedValue;
            }
            value = formattedValue;
            if (decimal > 0)
            {
                value = value.substring(0, value.indexOf('.'));
                value += fraction;
            }
            else
            {
                Long amt = (Long) Math.round(Double.parseDouble(value));
                value = Long.toString(amt);
            }
            return value;
        }
        char lastDigitOfIntegral = integral.charAt(integral.length() - 1);
        integral = integral.replaceAll("\\d$", "");
        integral = integral.replaceAll("(?<=.)(?=(?:\\d{2})+$)", "") + lastDigitOfIntegral;

        if (decimal > 0)
        {
            integral += fraction;
        }

        if (isNegative)
        {
            integral = "-" + integral;
        }
        value = integral;
        return value;
    }

    public String makeFormat(int decimals)
    {
        StringBuilder num = null;
        if (decimals > 0)
        {
            num = new StringBuilder("0.");
            for (int i = 0; i < decimals; i++)
            {
                num.append("0");
            }
        }
        else
        {
            num = new StringBuilder("######");
        }

        return num.toString();
    }

    public String appendZeros(int decimals)
    {
        StringBuilder num = new StringBuilder();
        for (int i = 0; i < decimals; i++)
        {
            num.append("0");
        }

        return num.toString();
    }

    public void downloadExcel(HttpServletResponse response, String fileName) throws IOException
    {
        OutputStream outstream = response.getOutputStream();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/ms-excel");
        response.setHeader("Content-disposition", "attachment; filename=\"" + fileName + ".xls\"");
        response.setHeader("Cache-Control", "max-age=0");
        sampleWorkBook.write(outstream);
        outstream.flush();
        outstream.close();
    }
}
