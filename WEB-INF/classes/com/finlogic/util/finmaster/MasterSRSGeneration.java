/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.finmaster;

import com.finlogic.apps.finstudio.finmaster.FinMasterFormBean;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Colour;
import jxl.write.*;

/**
 *
 * @author Sonam Patel
 */
public class MasterSRSGeneration
{

    private WritableCellFormat masterfieldcf = null;
    private WritableCellFormat additionfieldcf = null;
    private WritableCellFormat rowcf = null;
    private WritableCellFormat valuecf = null;
    private WritableCellFormat textcf = null;
    private WritableCellFormat textwrapcf = null;
    private WritableCellFormat leftcellcf = null;
    private WritableCellFormat topcellcf = null;
    private WritableCellFormat bottomcellcf = null;
    private WritableCellFormat rightcellcf = null;
    private WorkbookSettings ws = null;
    private WritableWorkbook workbook = null;
    private WritableSheet sheet1 = null, sheet2 = null, sheet3 = null, sheet4 = null, sheet = null;
    private int rowNo = 2;
    private int[] maxLen = new int[6];
    private static final String ADD = "Add", EDIT = "Edit", DEL = "Delete", VIEW = "View", MST = "Master";

    public MasterSRSGeneration() throws WriteException
    {
        WritableFont wFont10;
        wFont10 = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD);
        WritableFont wFont8;
        wFont8 = new WritableFont(WritableFont.ARIAL, 8, WritableFont.NO_BOLD);
        masterfieldcf = new WritableCellFormat(wFont10);
        masterfieldcf.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
        masterfieldcf.setAlignment(jxl.format.Alignment.LEFT);
        masterfieldcf.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
        masterfieldcf.setBackground(Colour.GREY_25_PERCENT);
        additionfieldcf = new WritableCellFormat(wFont10);
        additionfieldcf.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
        additionfieldcf.setAlignment(jxl.format.Alignment.CENTRE);
        additionfieldcf.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
        additionfieldcf.setBackground(Colour.GREY_25_PERCENT);
        rowcf = new WritableCellFormat(wFont10);
        rowcf.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
        rowcf.setAlignment(jxl.format.Alignment.CENTRE);
        rowcf.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
        rowcf.setBackground(Colour.ICE_BLUE);
        valuecf = new WritableCellFormat(wFont10);
        valuecf.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
        valuecf.setAlignment(jxl.format.Alignment.CENTRE);
        valuecf.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
        textcf = new WritableCellFormat(wFont10);
        textcf.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
        textcf.setAlignment(jxl.format.Alignment.LEFT);
        textcf.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
        textwrapcf = new WritableCellFormat(wFont10);
        textwrapcf.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
        textwrapcf.setAlignment(jxl.format.Alignment.LEFT);
        textwrapcf.setWrap(true);
        textwrapcf.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);

        topcellcf = new WritableCellFormat(wFont8);
        topcellcf.setBorder(jxl.format.Border.TOP, jxl.format.BorderLineStyle.THIN);
        bottomcellcf = new WritableCellFormat(wFont8);
        bottomcellcf.setBorder(jxl.format.Border.BOTTOM, jxl.format.BorderLineStyle.THIN);
        rightcellcf = new WritableCellFormat(wFont8);
        rightcellcf.setBorder(jxl.format.Border.RIGHT, jxl.format.BorderLineStyle.THIN);
        leftcellcf = new WritableCellFormat(wFont10);
        leftcellcf.setBorder(jxl.format.Border.LEFT, jxl.format.BorderLineStyle.THIN);

        ws = new WorkbookSettings();
        ws.setLocale(new Locale("en", "EN"));
    }

    private void setSheet(final String tab)
    {
        if (ADD.equals(tab))
        {
            sheet = sheet1;
        }
        else if (EDIT.equals(tab))
        {
            sheet = sheet2;
        }
        else if (DEL.equals(tab))
        {
            sheet = sheet3;
        }
        else if (VIEW.equals(tab))
        {
            sheet = sheet4;
        }
        rowNo = 2;
    }

    public String writeSRS(final FinMasterFormBean formBean) throws IOException, WriteException
    {
        String tomcatPath;
        tomcatPath = finpack.FinPack.getProperty("tomcat1_path");
        String filePath;
        filePath = tomcatPath + "/webapps/finstudio/generated/" + formBean.getSrNo() + "MGV2.xls";
        File file;
        file = new File(filePath);

        workbook = Workbook.createWorkbook(file, ws);
        if (formBean.isChkView())
        {
            sheet4 = workbook.createSheet("VIEW", 0);
            sheet4.getSettings().setShowGridLines(false);
        }
        if (formBean.isChkDelete())
        {
            sheet3 = workbook.createSheet("DELETE", 0);
            sheet3.getSettings().setShowGridLines(false);
        }
        sheet2 = workbook.createSheet("EDIT", 0);
        sheet2.getSettings().setShowGridLines(false);
        sheet1 = workbook.createSheet("ADD", 0);
        sheet1.getSettings().setShowGridLines(false);

        setSheet(ADD);
        for (int i = 0; i < 6; i++)
        {
            maxLen[i] = 10;
        }

        srsSpecification(formBean);
        writeSpec(formBean, MST, ADD);
        writeSRSDetail(formBean, MST, ADD);

        sheet.setColumnView(1, maxLen[0] + 2);
        sheet.setColumnView(2, maxLen[1] + 2);
        sheet.setColumnView(3, maxLen[2] + 2);
        sheet.setColumnView(4, maxLen[3] + 2);
        sheet.setColumnView(5, maxLen[4] + 2);
        sheet.setColumnView(6, maxLen[5] + 2);

        setSheet(EDIT);
        for (int i = 0; i < 6; i++)
        {
            maxLen[i] = 10;
        }

        srsSpecification(formBean);
        writeSpec(formBean, MST, EDIT);
        writeSRSDetail(formBean, MST, EDIT);

        sheet.setColumnView(1, maxLen[0] + 2);
        sheet.setColumnView(2, maxLen[1] + 2);
        sheet.setColumnView(3, maxLen[2] + 2);
        sheet.setColumnView(4, maxLen[3] + 2);
        sheet.setColumnView(5, maxLen[4] + 2);
        sheet.setColumnView(6, maxLen[5] + 2);

        if (formBean.isChkDelete())
        {
            setSheet(DEL);
            for (int i = 0; i < 6; i++)
            {
                maxLen[i] = 10;
            }

            srsSpecification(formBean);
            writeSpec(formBean, MST, DEL);
            writeSRSDetail(formBean, MST, DEL);

            sheet.setColumnView(1, maxLen[0] + 2);
            sheet.setColumnView(2, maxLen[1] + 2);
            sheet.setColumnView(3, maxLen[2] + 2);
            sheet.setColumnView(4, maxLen[3] + 2);
            sheet.setColumnView(5, maxLen[4] + 2);
            sheet.setColumnView(6, maxLen[5] + 2);
        }

        if (formBean.isChkView())
        {
            setSheet(VIEW);
            for (int i = 0; i < 6; i++)
            {
                maxLen[i] = 10;
            }

            srsSpecification(formBean);
            writeSpec(formBean, MST, VIEW);
            writeSRSDetail(formBean, MST, VIEW);

            sheet.setColumnView(1, maxLen[0] + 2);
            sheet.setColumnView(2, maxLen[1] + 2);
            sheet.setColumnView(3, maxLen[2] + 2);
            sheet.setColumnView(4, maxLen[3] + 2);
            sheet.setColumnView(5, maxLen[4] + 2);
            sheet.setColumnView(6, maxLen[5] + 2);
        }
        closeFile();
        return filePath;
    }

    private void srsSpecification(final FinMasterFormBean formBean) throws WriteException
    {
        //get the length of column data
        String[] srsHeader =
        {
            "Problem Statment:", "Solution / Objective:", "Existing Practice:", "Placement:"
        };
        String problem;
        problem = formBean.getTxtProblemStmt();
        String solution;
        solution = formBean.getTxtSolution();
        String practice;
        practice = formBean.getTxtExistPractice();
        String placement;
        placement = formBean.getTxtPlacement();
        int srsHdrLen;
        srsHdrLen = srsHeader.length;

        int i;
        for (i = 0; i < srsHdrLen; i++)
        {
            if (maxLen[0] < srsHeader[i].length())
            {
                maxLen[0] = srsHeader[i].length();
            }
        }

        for (i = 0; i < srsHdrLen; i++)
        {
            write(1, rowNo, srsHeader[i], sheet, masterfieldcf);
            rowNo++;
        }
        rowNo = rowNo - i;
        if (maxLen[1] < problem.length())
        {
            maxLen[1] = problem.length();
        }
        if (maxLen[1] < solution.length())
        {
            maxLen[1] = solution.length();
        }
        if (maxLen[1] < practice.length())
        {
            maxLen[1] = practice.length();
        }
        if (maxLen[1] < placement.length())
        {
            maxLen[1] = placement.length();
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
            maxLen[1] = 10;
            maxLen[2] = 10;
            maxLen[3] = 10;
            maxLen[4] = 10;
        }
        sheet.mergeCells(2, rowNo, 5, rowNo);
        sheet.setRowView(rowNo, 700);
        write(2, rowNo++, problem, sheet, textwrapcf);
        sheet.mergeCells(2, rowNo, 5, rowNo);
        sheet.setRowView(rowNo, 700);
        write(2, rowNo++, solution, sheet, textwrapcf);
        sheet.mergeCells(2, rowNo, 5, rowNo);
        sheet.setRowView(rowNo, 700);
        write(2, rowNo++, practice, sheet, textwrapcf);
        sheet.mergeCells(2, rowNo, 5, rowNo);
        sheet.setRowView(rowNo, 700);
        write(2, rowNo++, placement, sheet, textwrapcf);
        rowNo++;
    }

    private void writeSpec(final FinMasterFormBean formBean, final String tableType, final String tab) throws WriteException
    {
        int startcol = 1;
        int startrow;
        startrow = rowNo + 1;
        int endcol;
        endcol = 6;
        int endrow = rowNo + 6;
        if (MST.equals(tableType))
        {
            if (ADD.equals(tab))
            {
                endrow += formBean.getHdnAddField().length;
            }
            else if (EDIT.equals(tab))
            {
                if (formBean.getHdnEditField() != null)
                {
                    endrow += formBean.getHdnEditField().length;
                }
            }
            else if (DEL.equals(tab))
            {
                if (formBean.getHdnDeleteField() != null)
                {
                    endrow += formBean.getHdnDeleteField().length;
                }
            }
            else if (VIEW.equals(tab))
            {
                if (formBean.getHdnViewField() != null)
                {
                    endrow += formBean.getHdnViewField().length;
                }
            }
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
        if (MST.equals(tableType))
        {
            write(1, rowNo, MST, sheet, rowcf);
            write(2, rowNo, "Detail", sheet, valuecf);
        }
        else
        {
            write(1, rowNo, MST, sheet, valuecf);
            write(2, rowNo, "Detail", sheet, rowcf);
        }
        rowNo += 2;
        String[] fields = null;
        String[] labels = null;
        String[] controls = null;
        String[] tabIndexes = null;
        if (ADD.equals(tab))
        {
            write(2, rowNo, ADD, sheet, rowcf);
            write(3, rowNo, EDIT, sheet, valuecf);
            write(4, rowNo, DEL, sheet, valuecf);
            write(5, rowNo, VIEW, sheet, valuecf);
            if (MST.equals(tableType))
            {
                fields = formBean.getHdnAddField();
                labels = formBean.getHdntxtAddLabel();
                controls = formBean.getHdnAddControl();
                tabIndexes = new String[fields.length];
                for (int i = 0; i < fields.length; i++)
                {
                    tabIndexes[i] = formBean.getHdnAddTabIndex()[i];
                }
            }
        }
        else if (EDIT.equals(tab))
        {
            write(2, rowNo, ADD, sheet, valuecf);
            write(3, rowNo, EDIT, sheet, rowcf);
            write(4, rowNo, DEL, sheet, valuecf);
            write(5, rowNo, VIEW, sheet, valuecf);
            if (MST.equals(tableType))
            {
                if (formBean.getHdnEditField() != null)
                {
                    fields = formBean.getHdnEditField();
                    labels = formBean.getHdntxtEditLabel();
                    controls = formBean.getHdnEditControl();
                    tabIndexes = new String[fields.length];
                    for (int i = 0; i < fields.length; i++)
                    {
                        tabIndexes[i] = formBean.getHdnEditTabIndex()[i];
                    }
                }
            }
        }
        else if (DEL.equals(tab))
        {
            write(2, rowNo, ADD, sheet, valuecf);
            write(3, rowNo, EDIT, sheet, valuecf);
            write(4, rowNo, DEL, sheet, rowcf);
            write(5, rowNo, VIEW, sheet, valuecf);
            if (MST.equals(tableType))
            {
                if (formBean.getHdnDeleteField() != null)
                {
                    fields = formBean.getHdnDeleteField();
                    labels = formBean.getHdntxtDeleteLabel();
                    controls = formBean.getHdnDeleteControl();
                    tabIndexes = new String[fields.length];
                    for (int i = 0; i < fields.length; i++)
                    {
                        tabIndexes[i] = formBean.getHdnDeleteTabIndex()[i];
                    }
                }
            }
        }
        else if (VIEW.equals(tab))
        {
            write(2, rowNo, ADD, sheet, valuecf);
            write(3, rowNo, EDIT, sheet, valuecf);
            write(4, rowNo, DEL, sheet, valuecf);
            write(5, rowNo, VIEW, sheet, rowcf);
            if (MST.equals(tableType))
            {
                if (formBean.getHdnViewField() != null)
                {
                    fields = formBean.getHdnViewField();
                    labels = formBean.getHdntxtViewLabel();
                    controls = formBean.getHdnViewControl();
                    tabIndexes = new String[fields.length];
                    for (int i = 0; i < fields.length; i++)
                    {
                        tabIndexes[i] = formBean.getHdnViewTabIndex()[i];
                    }
                }
            }
        }
        rowNo += 2;
        if (fields != null)
        {
            int fieldsLen;
            fieldsLen = fields.length;
            int[] tabIndex = new int[fieldsLen];
            for (int i = 0; i < fieldsLen; i++)
            {
                tabIndex[i] = i;
            }
            for (int i = 0; i < fieldsLen; i++)
            {
                for (int j = i; j < fieldsLen; j++)
                {
                    if (Integer.parseInt(tabIndexes[j]) < Integer.parseInt(tabIndexes[i]))
                    {
                        String tmpStr;
                        tmpStr = tabIndexes[j];
                        tabIndexes[j] = tabIndexes[i];
                        tabIndexes[i] = tmpStr;
                        int tmpStr1;
                        tmpStr1 = tabIndex[j];
                        tabIndex[j] = tabIndex[i];
                        tabIndex[i] = tmpStr1;
                    }
                }
            }

            for (int i = 0; i < fieldsLen; i++, rowNo++)
            {
                if (!"".equals(labels[tabIndex[i]]) && maxLen[1] < labels[tabIndex[i]].length())
                {
                    maxLen[1] = labels[tabIndex[i]].length();
                }
                else if (maxLen[1] < fields[i].length())
                {
                    maxLen[1] = fields[tabIndex[i]].length();
                }
                if (maxLen[2] < controls[tabIndex[i]].length())
                {
                    maxLen[2] = controls[tabIndex[i]].length();
                }

                if (!"".equals(labels[tabIndex[i]]))
                {
                    write(2, rowNo, labels[tabIndex[i]], sheet, masterfieldcf);
                }
                else
                {
                    write(2, rowNo, fields[tabIndex[i]], sheet, masterfieldcf);
                }
                write(3, rowNo, controls[tabIndex[i]], sheet, textcf);
            }
            rowNo += 4;
        }
    }

    private void setSelNature(String[] selNature)
    {
        int len;
        len = selNature.length;
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

    private void writeSRSDetail(final FinMasterFormBean formBean, final String tableType, final String tab) throws WriteException
    {
        String tmp = "";
        if (MST.equals(tableType))
        {
            tmp = "Field's Additional Details in " + formBean.getCmbMasterTable();
        }
        if ((maxLen[0] + maxLen[1]) < tmp.length())
        {
            int diff;
            diff = tmp.length() - (maxLen[0] + maxLen[1]);
            maxLen[0] += (int) diff / 2;
            maxLen[1] += (int) diff / 2;
        }
        sheet.mergeCells(1, rowNo, 2, rowNo);
        write(1, rowNo, tmp, sheet, rowcf);
        rowNo++;
        if (maxLen[1] < "Label".length())
        {
            maxLen[1] = "Label".length();
        }
        if (maxLen[2] < "Field Name".length())
        {
            maxLen[2] = "Field Name".length();
        }
        if (maxLen[3] < "Selection Nature".length())
        {
            maxLen[3] = "Selection Nature".length();
        }
        if (maxLen[4] < "Filtering / Validation".length())
        {
            maxLen[4] = "Filtering / Validation".length();
        }
        if (maxLen[5] < "Remarks".length())
        {
            maxLen[5] = "Remarks".length();
        }
        write(1, rowNo, "Sr. No", sheet, additionfieldcf);
        write(2, rowNo, "Label", sheet, additionfieldcf);
        write(3, rowNo, "Field Name", sheet, additionfieldcf);
        write(4, rowNo, "Selection Nature", sheet, additionfieldcf);
        write(5, rowNo, "Filtering / Validation", sheet, additionfieldcf);
        write(6, rowNo, "Remarks", sheet, additionfieldcf);
        rowNo++;

        String[] fields = null;
        String[] controls = null;
        String[] labels = null;
        String[] selNature = null;
        String[] validation = null;
        String[] remarks = null;
        String[] tabIndexes = null;
        int fieldsLen = 0;
        if (MST.equals(tableType))
        {
            if (ADD.equals(tab))
            {
                fields = formBean.getHdnAddField();
                controls = formBean.getHdnAddControl();
                labels = formBean.getHdntxtAddLabel();
                int len;
                len = formBean.getHdnrbtnAddMultiple().length;
                selNature = new String[len];
                for (int i = 0; i < len; i++)
                {
                    selNature[i] = formBean.getHdnrbtnAddMultiple()[i];
                }
                setSelNature(selNature);
                validation = formBean.getHdncmbAddValidation();
                remarks = formBean.getHdntxtAddRemarks();
                tabIndexes = new String[len];
                for (int i = 0; i < fields.length; i++)
                {
                    tabIndexes[i] = formBean.getHdnAddTabIndex()[i];
                }
                for (int i = 0; i < controls.length; i++)
                {
                    if (controls[i].equals("FileBox"))
                    {
                        StringBuilder str;
                        str = new StringBuilder();
                        str.append("Valid file type : ");
                        str.append(formBean.getHdntxtAddType()[i]);
                        str.append("\n");
                        str.append("Maximum File size : ");
                        if (formBean.getHdntxtAddMaxsize() != null && formBean.getHdntxtAddMaxsize()[i] != null && formBean.getHdntxtAddMaxsize()[i].trim().length() > 0)
                        {
                            str.append(formBean.getHdntxtAddMaxsize()[i]);
                        }
                        else
                        {
                            str.append("512");
                        }
                        str.append(" KB \n");
                        str.append("Maximum Files allowed : ");
                        if (formBean.getHdntxtAddMaxfiles() != null && formBean.getHdntxtAddMaxfiles()[i] != null && formBean.getHdntxtAddMaxfiles()[i].trim().length() > 0)
                        {
                            str.append(formBean.getHdntxtAddMaxfiles()[i]);
                        }
                        else
                        {
                            str.append("5");
                        }
                        validation[i] = str.toString();
                    }
                }
            }
            else if (EDIT.equals(tab))
            {
                if (formBean.getHdnEditField() != null)
                {
                    fields = formBean.getHdnEditField();
                    controls = formBean.getHdnEditControl();
                    labels = formBean.getHdntxtEditLabel();
                    int len;
                    len = formBean.getHdnrbtnEditMultiple().length;
                    selNature = new String[len];
                    for (int i = 0; i < len; i++)
                    {
                        selNature[i] = formBean.getHdnrbtnEditMultiple()[i];
                    }
                    setSelNature(selNature);
                    validation = formBean.getHdncmbEditValidation();
                    remarks = formBean.getHdntxtEditRemarks();
                    tabIndexes = new String[len];
                    for (int i = 0; i < fields.length; i++)
                    {
                        tabIndexes[i] = formBean.getHdnEditTabIndex()[i];
                    }
                    for (int i = 0; i < controls.length; i++)
                    {
                        if (controls[i].equals("FileBox"))
                        {
                            StringBuilder str;
                            str = new StringBuilder();
                            str.append("Valid file type : ");
                            str.append(formBean.getHdntxtEditType()[i]);
                            str.append("\n");
                            str.append("Maximum File size : ");
                            if (formBean.getHdntxtEditMaxsize() != null && formBean.getHdntxtEditMaxsize()[i] != null && formBean.getHdntxtEditMaxsize()[i].trim().length() > 0)
                            {
                                str.append(formBean.getHdntxtEditMaxsize()[i]);
                            }
                            else
                            {
                                str.append("512");
                            }
                            str.append(" KB \n");
                            str.append("Maximum Files allowed : ");
                            if (formBean.getHdntxtEditMaxfiles() != null && formBean.getHdntxtEditMaxfiles()[i] != null && formBean.getHdntxtEditMaxfiles()[i].trim().length() > 0)
                            {
                                str.append(formBean.getHdntxtEditMaxfiles()[i]);
                            }
                            else
                            {
                                str.append("5");
                            }
                            validation[i] = str.toString();
                        }
                    }
                }
            }
            else if (DEL.equals(tab))
            {
                if (formBean.getHdnDeleteField() != null)
                {
                    fields = formBean.getHdnDeleteField();
                    controls = formBean.getHdnDeleteControl();
                    labels = formBean.getHdntxtDeleteLabel();
                    int len;
                    len = formBean.getHdnrbtnDeleteMultiple().length;
                    selNature = new String[len];
                    for (int i = 0; i < len; i++)
                    {
                        selNature[i] = formBean.getHdnrbtnDeleteMultiple()[i];
                    }
                    setSelNature(selNature);
                    validation = formBean.getHdncmbDeleteValidation();
                    remarks = formBean.getHdntxtDeleteRemarks();
                    tabIndexes = new String[len];
                    for (int i = 0; i < fields.length; i++)
                    {
                        tabIndexes[i] = formBean.getHdnDeleteTabIndex()[i];
                    }
                    for (int i = 0; i < controls.length; i++)
                    {
                        if (controls[i].equals("FileBox"))
                        {
                            StringBuilder str;
                            str = new StringBuilder();
                            str.append("Valid file type : ");
                            str.append(formBean.getHdntxtDeleteType()[i]);
                            str.append("\n");
                            str.append("Maximum File size : ");
                            if (formBean.getHdntxtDeleteMaxsize() != null && formBean.getHdntxtDeleteMaxsize()[i] != null && formBean.getHdntxtDeleteMaxsize()[i].trim().length() > 0)
                            {
                                str.append(formBean.getHdntxtDeleteMaxsize()[i]);
                            }
                            else
                            {
                                str.append("512");
                            }
                            str.append(" KB \n");
                            str.append("Maximum Files allowed : ");
                            if (formBean.getHdntxtDeleteMaxfiles() != null && formBean.getHdntxtDeleteMaxfiles()[i] != null && formBean.getHdntxtDeleteMaxfiles()[i].trim().length() > 0)
                            {
                                str.append(formBean.getHdntxtDeleteMaxfiles()[i]);
                            }
                            else
                            {
                                str.append("5");
                            }
                            validation[i] = str.toString();
                        }
                    }
                }
            }
            else if (VIEW.equals(tab))
            {
                if (formBean.getHdnViewField() != null)
                {
                    fields = formBean.getHdnViewField();
                    controls = formBean.getHdnViewControl();
                    labels = formBean.getHdntxtViewLabel();
                    int len;
                    len = formBean.getHdnrbtnViewMultiple().length;
                    selNature = new String[len];
                    for (int i = 0; i < len; i++)
                    {
                        selNature[i] = formBean.getHdnrbtnViewMultiple()[i];
                    }
                    setSelNature(selNature);
                    validation = formBean.getHdncmbViewValidation();
                    remarks = formBean.getHdntxtViewRemarks();
                    tabIndexes = new String[len];
                    for (int i = 0; i < fields.length; i++)
                    {
                        tabIndexes[i] = formBean.getHdnViewTabIndex()[i];
                    }
                    for (int i = 0; i < controls.length; i++)
                    {
                        if (controls[i].equals("FileBox"))
                        {
                            StringBuilder str;
                            str = new StringBuilder();
                            str.append("Valid file type : ");
                            str.append(formBean.getHdntxtViewType()[i]);
                            str.append("\n");
                            str.append("Maximum File size : ");
                            if (formBean.getHdntxtViewMaxsize() != null && formBean.getHdntxtViewMaxsize()[i] != null && formBean.getHdntxtViewMaxsize()[i].trim().length() > 0)
                            {
                                str.append(formBean.getHdntxtViewMaxsize()[i]);
                            }
                            else
                            {
                                str.append("512");
                            }
                            str.append(" KB \n");
                            str.append("Maximum Files allowed : ");
                            if (formBean.getHdntxtViewMaxfiles() != null && formBean.getHdntxtViewMaxfiles()[i] != null && formBean.getHdntxtViewMaxfiles()[i].trim().length() > 0)
                            {
                                str.append(formBean.getHdntxtViewMaxfiles()[i]);
                            }
                            else
                            {
                                str.append("5");
                            }
                            validation[i] = str.toString();
                        }
                    }
                }
            }
        }
        if (fields != null)
        {
            fieldsLen = fields.length;
            int[] tabIndex = new int[fieldsLen];
            for (int i = 0; i < fieldsLen; i++)
            {
                tabIndex[i] = i;
            }
            for (int i = 0; i < fieldsLen; i++)
            {
                for (int j = i; j < fieldsLen; j++)
                {
                    if (Integer.parseInt(tabIndexes[j]) < Integer.parseInt(tabIndexes[i]))
                    {
                        String tmpStr;
                        tmpStr = tabIndexes[j];
                        tabIndexes[j] = tabIndexes[i];
                        tabIndexes[i] = tmpStr;
                        int tmpStr1;
                        tmpStr1 = tabIndex[j];
                        tabIndex[j] = tabIndex[i];
                        tabIndex[i] = tmpStr1;
                    }
                }
            }
            for (int i = 0; i < fieldsLen; i++)
            {
                //formbean set
            }
            for (int i = 0; i < fieldsLen; i++, rowNo++)
            {
                tmp = String.valueOf(i + 1);
                write(1, rowNo, tmp, sheet, valuecf);
                if (!"".equals(labels[tabIndex[i]]) && maxLen[1] < labels[tabIndex[i]].length())
                {
                    maxLen[1] = labels[tabIndex[i]].length();
                }
                else if (maxLen[1] < fields[tabIndex[i]].length())
                {
                    maxLen[1] = fields[tabIndex[i]].length();
                }
                if (maxLen[2] < fields[tabIndex[i]].length())
                {
                    maxLen[2] = fields[tabIndex[i]].length();
                }
                if (maxLen[3] < selNature[tabIndex[i]].length())
                {
                    maxLen[3] = selNature[tabIndex[i]].length();
                }
                if (maxLen[4] < validation[tabIndex[i]].length())
                {
                    maxLen[4] = validation[tabIndex[i]].length();
                }
                if (maxLen[5] < remarks[tabIndex[i]].length())
                {
                    maxLen[5] = remarks[tabIndex[i]].length();
                }

                if (!"".equals(labels[tabIndex[i]]))
                {
                    write(2, rowNo, labels[tabIndex[i]], sheet, valuecf);
                }
                else
                {
                    write(2, rowNo, fields[tabIndex[i]], sheet, valuecf);
                }
                write(3, rowNo, fields[tabIndex[i]], sheet, valuecf);
                if ("ComboBox".equals(controls[tabIndex[i]]) || "TextLikeCombo".equals(controls[tabIndex[i]]))
                {
                    write(4, rowNo, selNature[tabIndex[i]], sheet, valuecf);
                }
                else
                {
                    write(4, rowNo, "N/A", sheet, valuecf);
                }
                write(5, rowNo, validation[tabIndex[i]], sheet, valuecf);
                write(6, rowNo, remarks[tabIndex[i]], sheet, valuecf);
            }
        }
        rowNo += 3;
    }

    private void write(final int column, final int row, final String content, final WritableSheet sheet, final WritableCellFormat cellFormate) throws WriteException
    {
        sheet.addCell(new Label(column, row, content, cellFormate));
    }

    private void select(final int column, final int row, final WritableSheet sheet, final WritableCellFormat cellFormate) throws WriteException
    {
        sheet.addCell(new Label(column, row, "", cellFormate));
    }

    public void closeFile() throws IOException, WriteException
    {
        workbook.write();
        workbook.close();
    }
}
