/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.business.finstudio.finreport;

import com.finlogic.apps.finstudio.finreport.FinReportFormBean;
import com.finlogic.apps.finstudio.finreport.FinReportManager;
import java.util.List;
import java.util.Map;

/**
 *
 * @author njuser
 */
public class FinReportService
{

    private FinReportManager manager = new FinReportManager();

    public FinReportEntityBean formBeanToEntityBean(FinReportFormBean formBean) throws Exception
    {
        FinReportEntityBean entityBean = new FinReportEntityBean();
        entityBean.setPROJECT_NAME(formBean.getCmbProjectName());
        entityBean.setMODULE_NAME(formBean.getTxtModuleName());
        entityBean.setREQUEST_NO(formBean.getTxtReqNo());
        entityBean.setALIAS_NAME(formBean.getCmbAliasName());
        entityBean.setMAIN_QUERY(formBean.getTxtQuery());
        entityBean.setGROUPING(formBean.isChkGrouping());
        if (formBean.isChkGrouping())
        {
            entityBean.setGROUP_FIELD(formBean.getCmbGroupField());
        }
        else
        {
            entityBean.setGROUP_FIELD(null);
        }
        entityBean.setGROUP_FOOTER(formBean.isChkGroupFooter());
        if (formBean.isChkGroupFooter())
        {
            entityBean.setGROUP_FTR_COLUMN(formBean.getCmbGrpFooterColumn());
            entityBean.setGROUP_FTR_CALCULATION(formBean.getCmbGrpFooterCalculation());
        }
        else
        {
            entityBean.setGROUP_FTR_COLUMN(null);
            entityBean.setGROUP_FTR_CALCULATION(null);
        }

        entityBean.setPROBLEM_STATEMENT(formBean.getTxtProblemStmt());
        entityBean.setSOLUTION_OBJECTIVE(formBean.getTxtSolution());
        entityBean.setEXISTING_PRACTISE(formBean.getTxtExistingPractice());
        entityBean.setPLACEMENT(formBean.getTxtPlacement());

        entityBean.setREPORT_TITLE(formBean.getTxtReportTitle());
        entityBean.setDateTimePicker(formBean.isChkDateSelection());
        entityBean.setREPORT_TYPE(formBean.isChkReportType());
        entityBean.setEXPORT(formBean.isChkExport());
        entityBean.setCOLUMNS(formBean.isChkColumns());
        entityBean.setCHART(formBean.isChkChart());
        entityBean.setFILTER(formBean.isChkFilters());
        entityBean.setRPT_LABEL(formBean.getHdnRptLabel());
        String na = "N/A";
        entityBean.setRPT_CONTROL(formBean.getHdnRptControl());
        String[] arr = formBean.getHdnRptValidation();
        if (arr != null)
        {
            for (int i = 0; i < arr.length; i++)
            {
                if (arr[i].equals("-1"))
                {
                    arr[i] = na;
                }
            }
        }
        entityBean.setRPT_VALIDATION(arr);
        arr = formBean.getHdnRptSelNature();
        if (arr != null)
        {
            for (int i = 0; i < arr.length; i++)
            {
                if (arr[i].equals("-1"))
                {
                    arr[i] = na;
                }
            }
        }
        entityBean.setRPT_SELECTION_NATURE(arr);
        arr = formBean.getHdnRptRemarks();
        if (arr != null)
        {
            for (int i = 0; i < arr.length; i++)
            {
                if (arr[i].equals(""))
                {
                    arr[i] = na;
                }
            }
        }
        entityBean.setRPT_REMARKS(arr);

        entityBean.setFLTR_LABEL(formBean.getHdnFltrLabel());
        entityBean.setFLTR_CONTROL(formBean.getHdnFltrControl());
        arr = formBean.getHdnFltrValidation();
        if (arr != null)
        {
            for (int i = 0; i < arr.length; i++)
            {
                if (arr[i].equals("-1"))
                {
                    arr[i] = na;
                }
            }
        }
        entityBean.setFLTR_VALIDATION(arr);
        arr = formBean.getHdnFltrSelNature();
        if (arr != null)
        {
            for (int i = 0; i < arr.length; i++)
            {
                if (arr[i].equals("-1"))
                {
                    arr[i] = na;
                }
            }
        }
        entityBean.setFLTR_SELECTION_NATURE(arr);
        arr = formBean.getHdnFltrRemarks();
        if (arr != null)
        {
            for (int i = 0; i < arr.length; i++)
            {
                if (arr[i].equals(""))
                {
                    arr[i] = na;
                }
            }
        }
        entityBean.setFLTR_REMARKS(arr);

        entityBean.setGRID(formBean.isChkOnScreen());
        entityBean.setPDF(formBean.isChkPDF());
        entityBean.setEXCEL(formBean.isChkExcel());

        entityBean.setPRIMARY_KEY(formBean.getCmbPrimaryKey());

        entityBean.setNoChart(formBean.isNoChart());
        entityBean.setPieChart(formBean.isPieChart());
        entityBean.setBarChart(formBean.isBarChart());
        entityBean.setSymbolLineChart(formBean.isSymbolLineChart());
        entityBean.setThreedLineChart(formBean.isThreeLineChart());

        entityBean.setReportColumns(formBean.getCmbTabColumn());
        entityBean.setEMP_CODE(formBean.getEmp_code());
        return entityBean;
    }

    public boolean insertData(FinReportFormBean formBean) throws Exception
    {
        return manager.insert(formBeanToEntityBean(formBean));
    }

    public String getColumnDetail(String alias, String query) throws Exception
    {
        Map<String, List<String>> m=manager.getColumnDetail(alias, query);

        List<String> colNames=m.get("colNames");
        List<String> colTypes=m.get("colTypes");

        StringBuilder sbNames=new StringBuilder();
        StringBuilder sbTypes=new StringBuilder();


        for (int i = 0; i < colNames.size(); i++)
        {
            sbNames.append("\"").append(colNames.get(i)).append("\",");
            sbTypes.append("\"").append(colTypes.get(i)).append("\",");
        }

        sbNames.deleteCharAt(sbNames.length()-1);
        sbTypes.deleteCharAt(sbTypes.length()-1);
 
        return "{colNames:[" + sbNames.toString() + "],colTypes:[" + sbTypes.toString() + "]}";
    }

    public int getSRNO()
    {
        return manager.getSRNO();
    }
}
