/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.findhtmlreport;

import com.finlogic.business.finstudio.findhtmlreport.FinDhtmlReportDetailEntityBean;
import com.finlogic.business.finstudio.findhtmlreport.FinDhtmlReportSummaryEntityBean;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 *
 * @author njuser
 */
public class FinDhtmlReportSRSGeneration
{
    private WritableCellFormat masterfieldcf = null;
    private WritableCellFormat additionfieldcf = null, rowcf = null, valuecf = null, textcf = null, textwrapcf = null, leftcellcf = null, topcellcf = null, bottomcellcf = null, rightcellcf = null, contentgreencf = null;
    private WorkbookSettings ws = null;
    private WritableSheet sheet = null;
    private int rowNo = 2;
    private int[] maxLen = new int[5];
    private static final String RPT = "Report Type", FLTR = "Filter";

    public FinDhtmlReportSRSGeneration() throws WriteException
    {
        masterfieldcf = new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD));
        masterfieldcf.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
        masterfieldcf.setAlignment(jxl.format.Alignment.LEFT);
        masterfieldcf.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
        masterfieldcf.setBackground(Colour.GREY_25_PERCENT);
        additionfieldcf = new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD));
        additionfieldcf.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
        additionfieldcf.setAlignment(jxl.format.Alignment.CENTRE);
        additionfieldcf.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
        additionfieldcf.setBackground(Colour.GREY_25_PERCENT);
        rowcf = new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD));
        rowcf.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
        rowcf.setAlignment(jxl.format.Alignment.CENTRE);
        rowcf.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
        rowcf.setBackground(Colour.ICE_BLUE);
        valuecf = new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD));
        valuecf.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
        valuecf.setAlignment(jxl.format.Alignment.CENTRE);
        valuecf.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
        textcf = new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD));
        textcf.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
        textcf.setAlignment(jxl.format.Alignment.LEFT);
        textcf.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
        textwrapcf = new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD));
        textwrapcf.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
        textwrapcf.setAlignment(jxl.format.Alignment.LEFT);
        textwrapcf.setWrap(true);
        textwrapcf.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);

        topcellcf = new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 8, WritableFont.NO_BOLD));
        topcellcf.setBorder(jxl.format.Border.TOP, jxl.format.BorderLineStyle.THIN);
        bottomcellcf = new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 8, WritableFont.NO_BOLD));
        bottomcellcf.setBorder(jxl.format.Border.BOTTOM, jxl.format.BorderLineStyle.THIN);
        rightcellcf = new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 8, WritableFont.NO_BOLD));
        rightcellcf.setBorder(jxl.format.Border.RIGHT, jxl.format.BorderLineStyle.THIN);
        leftcellcf = new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD));
        leftcellcf.setBorder(jxl.format.Border.LEFT, jxl.format.BorderLineStyle.THIN);

        contentgreencf = new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, true, UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.GREEN));
        contentgreencf.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
        contentgreencf.setAlignment(jxl.format.Alignment.LEFT);

        ws = new WorkbookSettings();
        ws.setLocale(new Locale("en", "EN"));
    }

     public String writeSRS(final FinDhtmlReportDetailEntityBean dEntityBean, final FinDhtmlReportSummaryEntityBean sEntityBean) throws IOException, WriteException
    {
        String tomcatPath = finpack.FinPack.getProperty("tomcat1_path");
        String Path = tomcatPath + "/webapps/finstudio/generated/" + dEntityBean.getSRNo() + "RGV2.xls";
        File file = new File(Path);
        WritableWorkbook wbook;

        wbook = Workbook.createWorkbook(file, ws);
        sheet = wbook.createSheet("SRS", 0);
        sheet.getSettings().setShowGridLines(false);

        for (int i = 0; i < 5; i++)
        {
            maxLen[i] = 10;
        }

        srsSpecification(dEntityBean);
        if (dEntityBean.isReportType())
        {
            writeSpec(dEntityBean, RPT);
            writeSRSDetail(dEntityBean.getRptControl(), dEntityBean.getRptLabel(), dEntityBean.getRptSelNature(), dEntityBean.getRptValidation(), dEntityBean.getRptRemarks());
        }
        if (dEntityBean.isFilter())
        {
            writeSpec(dEntityBean, FLTR);
            writeSRSDetail(dEntityBean.getFltrControl(), dEntityBean.getFltrLabel(), dEntityBean.getFltrSelNature(), dEntityBean.getFltrValidation(), dEntityBean.getFltrRemarks());
        }
        //write Detail Report Columns
        writeReportData(dEntityBean.getSelectedColumns(), dEntityBean.isGrouping(), dEntityBean.getGroupField(), dEntityBean.isDateTimePicker(), dEntityBean.getReportTitle(), "Detail");

        //write Summary Report Columns
        if (sEntityBean != null && sEntityBean.getDetailRefNo() != null && !sEntityBean.getDetailRefNo().equals(""))
        {
            writeReportData(sEntityBean.getSelectedColumns(), sEntityBean.isGrouping(), sEntityBean.getGroupField(), true, sEntityBean.getReportTitle(), "Summary");
        }

        sheet.setColumnView(1, maxLen[0] + 2);
        sheet.setColumnView(2, maxLen[1] + 2);
        sheet.setColumnView(3, maxLen[2] + 2);
        sheet.setColumnView(4, maxLen[3] + 2);
        sheet.setColumnView(5, maxLen[4] + 2);

        wbook.write();
        wbook.close();

        return Path;
    }

    private void srsSpecification(final FinDhtmlReportDetailEntityBean entityBean) throws WriteException
    {
        //get the length of column data
        String[] srsHeader =
        {
            "Problem Statment:", "Solution / Objective:", "Existing Practice:", "Placement:"
        };
        int srsHeaderLen = srsHeader.length;
        String[] srsHeaderDetail =
        {
            entityBean.getProblemStatement(), entityBean.getSolutionObjective(), entityBean.getExistingPractise(), entityBean.getPlacement()
        };

        for (int i = 0; i < srsHeaderLen; i++)
        {
            write(1, rowNo, srsHeader[i], sheet, masterfieldcf);
            if (maxLen[0] < srsHeader[i].length())
            {
                maxLen[0] = srsHeader[i].length();
            }
            if (maxLen[1] < srsHeader[i].length())
            {
                maxLen[1] = srsHeader[i].length();
            }
            sheet.mergeCells(2, rowNo, 5, rowNo);
            sheet.setRowView(rowNo, 700);
            write(2, rowNo, srsHeaderDetail[i], sheet, textwrapcf);
            rowNo++;
        }
        maxLen[1] = (int) maxLen[1] / 4;
        if (maxLen[1] > 10)
        {
            maxLen[2] = maxLen[1];
            maxLen[3] = maxLen[1];
            maxLen[4] = maxLen[1];
        }
        else
        {
            maxLen[2] = 10;
            maxLen[3] = 10;
            maxLen[4] = 10;
            maxLen[1] = 10;
        }
        rowNo++;
    }

    private void writeSpec(final FinDhtmlReportDetailEntityBean entityBean, final String tabname) throws WriteException
    {
        int field_len = 0;
        if (RPT.equals(tabname))
        {
            field_len = entityBean.getRptLabel().length;
        }
        else if (FLTR.equals(tabname))
        {
            field_len = entityBean.getFltrLabel().length;
        }
        int startcol = 1;
        int startrow = rowNo + 1;
        int endcol = 5;
        int endrow;
        if (FLTR.equals(tabname))
        {
            endrow = rowNo + field_len + 8;
        }
        else
        {
            endrow = rowNo + field_len + 6;
        }

        for (; startcol <= endcol; startcol++)
        {
            for (int y = startrow; y <= endrow; y++)
            {
                if (startcol == endcol && y == startrow)
                {
                    select(startcol, y, sheet, topcellcf);
                    select(startcol + 1, y, sheet, leftcellcf);
                }
                else if (startcol == endcol && y == endrow)
                {
                    select(startcol, y, sheet, bottomcellcf);
                    select(startcol + 1, y, sheet, leftcellcf);
                }
                else if (startcol == 1 && y == startrow)
                {
                    select(startcol, y, sheet, topcellcf);
                    select(0, y, sheet, rightcellcf);
                }
                else if (startcol == 1 && y == endrow)
                {
                    select(startcol, y, sheet, bottomcellcf);
                    select(0, y, sheet, rightcellcf);
                }
                else if (startcol == endcol)
                {
                    select(startcol, y, sheet, rightcellcf);
                }
                else if (y == endrow)
                {
                    select(startcol, y, sheet, bottomcellcf);
                }
                else if (y == startrow)
                {
                    select(startcol, y, sheet, topcellcf);
                }
                else if (startcol == 1)
                {
                    select(startcol, y, sheet, leftcellcf);
                }
            }
        }
        rowNo++;
        write(1, rowNo, entityBean.getReportTitle(), sheet, rowcf);
        rowNo += 2;
        String[] fields = null;
        String[] controls = null;
        if (RPT.equals(tabname))
        {
            write(2, rowNo, RPT, sheet, rowcf);
            write(3, rowNo, FLTR, sheet, valuecf);
            fields = entityBean.getRptLabel();
            controls = entityBean.getRptControl();
        }
        if (FLTR.equals(tabname))
        {
            write(2, rowNo, RPT, sheet, valuecf);
            write(3, rowNo, FLTR, sheet, rowcf);

            //for writing report type in filter
            if (entityBean.getCmbRefNo() != null && !entityBean.getCmbRefNo().equals(""))
            {
                rowNo += 2;
                write(2, rowNo, "Detail Report", sheet, valuecf);
                write(3, rowNo, "Radio", sheet, textcf);
                rowNo++;
                write(2, rowNo, "Summary Report", sheet, valuecf);
                write(3, rowNo, "Radio", sheet, textcf);
            }

            fields = entityBean.getFltrLabel();
            controls = entityBean.getFltrControl();
        }
        rowNo += 2;
        for (int i = 0; i < fields.length; i++)
        {
            if (maxLen[1] < fields[i].length())
            {
                maxLen[1] = fields[i].length();
            }
            if (maxLen[2] < controls[i].length())
            {
                maxLen[2] = controls[i].length();
            }
            write(2, rowNo, fields[i], sheet, masterfieldcf);
            write(3, rowNo, controls[i], sheet, textcf);
            rowNo++;
        }
        rowNo += 4;
    }

    private void writeSRSDetail(final String[] control, final String[] fields, String[] selNature, final String[] validation, final String[] remarks) throws WriteException
    {
        setSelNature(selNature);
        sheet.mergeCells(1, rowNo, 2, rowNo);
        write(1, rowNo, "Field's Additional Details", sheet, rowcf);
        String[] menuheader =
        {
            "Field Name", "Selection Nature", "Filtering / Validation", "Remarks"
        };
        int menuHeaderLen = menuheader.length;
        rowNo++;
        write(1, rowNo, "Sr. No", sheet, additionfieldcf);
        for (int i = 0; i < menuHeaderLen; i++)
        {
            if (maxLen[i + 1] < menuheader[i].length())
            {
                maxLen[i + 1] = menuheader[i].length();
            }
            sheet.mergeCells(5, rowNo, 6, rowNo);
            write(i + 2, rowNo, menuheader[i], sheet, additionfieldcf);
        }
        rowNo++;

        for (int i = 0; i < fields.length; i++, rowNo++)
        {
            if (maxLen[1] < fields[i].length())
            {
                maxLen[1] = fields[i].length();
            }
            if (maxLen[2] < selNature[i].length())
            {
                maxLen[2] = selNature[i].length();
            }
            if (maxLen[3] < validation[i].length())
            {
                maxLen[3] = validation[i].length();
            }
            if (maxLen[4] < remarks[i].length())
            {
                maxLen[4] = remarks[i].length();
            }
            maxLen[4] = (int) maxLen[4] / 2;
            write(1, rowNo, String.valueOf(i + 1), sheet, valuecf);
            write(2, rowNo, fields[i], sheet, valuecf);
            if ("ComboBox".equals(control[i]) || "TextLikeCombo".equals(control[i]))
            {
                write(3, rowNo, selNature[i], sheet, valuecf);
            }
            else
            {
                write(3, rowNo, "N/A", sheet, valuecf);
            }
            write(4, rowNo, validation[i], sheet, valuecf);
            sheet.mergeCells(5, rowNo, 6, rowNo);
            write(5, rowNo, remarks[i], sheet, valuecf);
        }
        rowNo += 3;
    }

    private void setSelNature(String[] selNature)
    {
        int len = selNature.length;
        for (int i = 0; i < len; i++)
        {
            if ("true".equals(selNature[i]))
            {
                selNature[i] = "Multiple";
            }
            else
            {
                selNature[i] = "Single";
            }
        }
    }

    private void writeReportData(String[] selectedColumns, boolean grouping, String groupField, boolean datePicker, String reportTitle, String reportType) throws WriteException
    {
        int count = 0;
        rowNo++;

        if (reportType.equals("Detail"))
        {
            write(1, rowNo, "Detail Report Columns", sheet, rowcf);            
        }
        else
        {
            write(1, rowNo, "Summary Report Columns", sheet, rowcf);
        }
        
        String reportColumns[] = selectedColumns;
        int reportColumnLen = reportColumns.length;
        if (grouping)
        {
            rowNo++;
            sheet.mergeCells(1, rowNo, reportColumnLen, rowNo);
            write(1, rowNo, "Grouping on " + groupField, sheet, contentgreencf);
        }
        String DateFormat = "";
        if (datePicker)
        {
            DateFormat = "(DD-MM-YYYY TO DD-MM-YYYY)";
        }
        rowNo++;
        sheet.mergeCells(1, rowNo, reportColumnLen, rowNo);
        write(1, rowNo, reportTitle + " " + DateFormat, sheet, additionfieldcf);
        rowNo++;
        //for column name
        for (int i = 0; i < reportColumnLen; i++)
        {
            if (i < maxLen.length)
            {
                if (maxLen[i] < reportColumns[i].length())
                {
                    maxLen[i] = reportColumns[i].length();
                }
            }
            else
            {
                sheet.setColumnView(i + 1, reportColumns[i].length());
            }
            write(i + 1, rowNo, reportColumns[i].toLowerCase(Locale.getDefault()), sheet, additionfieldcf);
        }
        rowNo++;
        while (count < 3)
        {
            for (int i = 0; i < reportColumnLen; i++)
            {
                write(i + 1, rowNo, "", sheet, textcf);
            }
            count++;
            rowNo++;
        }
    }

    private void write(final int column, final int row, final String content, final WritableSheet s, final WritableCellFormat cf) throws WriteException
    {
        s.addCell(new Label(column, row, content, cf));
    }

    private void select(final int column, final int row, final WritableSheet s, final WritableCellFormat cf) throws WriteException
    {
        s.addCell(new Label(column, row, "", cf));
    }
}
