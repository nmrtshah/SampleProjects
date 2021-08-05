/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.apps.finstudio.finmaster;

import com.finlogic.business.finstudio.finmaster.FinMasterEntityBean;
import com.finlogic.business.finstudio.finmaster.FinMasterManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.support.rowset.SqlRowSet;

/**
 *
 * @author Sonam Patel
 */
public class FinMasterService
{

    private final FinMasterManager manager = new FinMasterManager();

    public FinMasterEntityBean formBeanToEntityBean(FinMasterFormBean formBean)
    {
        FinMasterEntityBean entityBean;
        entityBean = new FinMasterEntityBean();
        entityBean.setProjectName(formBean.getCmbProjectName());
        entityBean.setModuleName(formBean.getTxtModuleName());
        entityBean.setRequestNo(formBean.getTxtReqNo());

        String strna;
        strna = "N/A";
        String strSingle;
        strSingle = "Single";
        String strMulti;
        strMulti = "Multiple";
        int len;
        if (formBean.getTxtProblemStmt().equals(""))
        {
            entityBean.setProblemStatement(strna);
            formBean.setTxtProblemStmt(strna);
        }
        else
        {
            entityBean.setProblemStatement(formBean.getTxtProblemStmt());
        }
        if (formBean.getTxtSolution().equals(""))
        {
            entityBean.setSolutionObjective(strna);
            formBean.setTxtSolution(strna);
        }
        else
        {
            entityBean.setSolutionObjective(formBean.getTxtSolution());
        }
        if (formBean.getTxtExistPractice().equals(""))
        {
            entityBean.setExistingPractice(strna);
            formBean.setTxtExistPractice(strna);
        }
        else
        {
            entityBean.setExistingPractice(formBean.getTxtExistPractice());
        }
        if (formBean.getTxtPlacement().equals(""))
        {
            entityBean.setPlacement(strna);
            formBean.setTxtPlacement(strna);
        }
        else
        {
            entityBean.setPlacement(formBean.getTxtPlacement());
        }

        entityBean.setAliasName(formBean.getCmbAliasName());
        entityBean.setMasterTableName(formBean.getCmbMasterTable());
        entityBean.setMasterPrimaryKey(formBean.getCmbMasterTablePrimKey());

        entityBean.setAddTab(formBean.isChkAdd());
        entityBean.setEditTab(formBean.isChkEdit());
        entityBean.setDeleteTab(formBean.isChkDelete());
        entityBean.setViewTab(formBean.isChkView());
        entityBean.setEmpCode(formBean.getEmpCode());

        entityBean.setViewColumns(formBean.isChkColumns());
        entityBean.setViewOnScreen(formBean.isChkOnScreen());
        entityBean.setViewPDF(formBean.isChkPdf());
        entityBean.setViewXLS(formBean.isChkXls());

        entityBean.setMstAddFieldName(formBean.getHdnAddField());
        entityBean.setMstAddControl(formBean.getHdnAddControl());
        String[] arr = formBean.getHdncmbAddValidation();
        if (arr != null)
        {
            for (int i = 0; i < arr.length; i++)
            {
                if (arr[i].equals("-1"))
                {
                    arr[i] = strna;
                }
            }
        }
        entityBean.setMstAddValidation(arr);
        len = formBean.getHdnrbtnAddMultiple().length;
        arr = new String[len];
        for (int i = 0; i < len; i++)
        {
            if ("ComboBox".equals(formBean.getHdnAddControl()[i]))
            {
                if ("true".equals(formBean.getHdnrbtnAddMultiple()[i]))
                {
                    arr[i] = strMulti;
                }
                else
                {
                    arr[i] = strSingle;
                }
            }
            else
            {
                arr[i] = strna;
            }
        }
        entityBean.setMstAddSelNature(arr);
        arr = formBean.getHdntxtAddRemarks();
        if (arr != null)
        {
            for (int i = 0; i < arr.length; i++)
            {
                if (arr[i].equals(""))
                {
                    arr[i] = strna;
                }
            }
        }
        entityBean.setMstAddRemarks(arr);

        entityBean.setMstEditFieldName(formBean.getHdnEditField());
        entityBean.setMstEditControl(formBean.getHdnEditControl());
        arr = formBean.getHdncmbEditValidation();
        if (arr != null)
        {
            for (int i = 0; i < arr.length; i++)
            {
                if (arr[i].equals("-1"))
                {
                    arr[i] = strna;
                }
            }
        }
        entityBean.setMstEditValidation(arr);
        len = formBean.getHdnrbtnEditMultiple().length;
        arr = new String[len];
        for (int i = 0; i < len; i++)
        {
            if ("ComboBox".equals(formBean.getHdnEditControl()[i]))
            {
                if ("true".equals(formBean.getHdnrbtnEditMultiple()[i]))
                {
                    arr[i] = strMulti;
                }
                else
                {
                    arr[i] = strSingle;
                }
            }
            else
            {
                arr[i] = strna;
            }
        }
        entityBean.setMstEditSelNature(arr);
        arr = formBean.getHdntxtEditRemarks();
        if (arr != null)
        {
            for (int i = 0; i < arr.length; i++)
            {
                if (arr[i].equals(""))
                {
                    arr[i] = strna;
                }
            }
        }
        entityBean.setMstEditRemarks(arr);

        entityBean.setMstDelFieldName(formBean.getHdnDeleteField());
        entityBean.setMstDelControl(formBean.getHdnDeleteControl());
        arr = formBean.getHdncmbDeleteValidation();
        if (arr != null)
        {
            for (int i = 0; i < arr.length; i++)
            {
                if (arr[i].equals("-1"))
                {
                    arr[i] = strna;
                }
            }
        }
        entityBean.setMstDelValidation(arr);
        len = 0;
        if (formBean.getHdnrbtnDeleteMultiple() != null)
        {
            len = formBean.getHdnrbtnDeleteMultiple().length;
            arr = new String[len];
        }
        for (int i = 0; i < len; i++)
        {
            if ("ComboBox".equals(formBean.getHdnDeleteControl()[i]))
            {
                if ("true".equals(formBean.getHdnrbtnDeleteMultiple()[i]))
                {
                    arr[i] = strMulti;
                }
                else
                {
                    arr[i] = strSingle;
                }
            }
            else
            {
                arr[i] = strna;
            }
        }
        entityBean.setMstDelSelNature(arr);
        arr = formBean.getHdntxtDeleteRemarks();
        if (arr != null)
        {
            for (int i = 0; i < arr.length; i++)
            {
                if (arr[i].equals(""))
                {
                    arr[i] = strna;
                }
            }
        }
        entityBean.setMstDelRemarks(arr);

        entityBean.setMstViewFieldName(formBean.getHdnViewField());
        entityBean.setMstViewControl(formBean.getHdnViewControl());
        arr = formBean.getHdncmbViewValidation();
        if (arr != null)
        {
            for (int i = 0; i < arr.length; i++)
            {
                if (arr[i].equals("-1"))
                {
                    arr[i] = strna;
                }
            }
        }
        entityBean.setMstViewValidation(arr);
        len = 0;
        if (formBean.getHdnrbtnViewMultiple() != null)
        {
            len = formBean.getHdnrbtnViewMultiple().length;
            arr = new String[len];
        }
        for (int i = 0; i < len; i++)
        {
            if ("ComboBox".equals(formBean.getHdnViewControl()[i]))
            {
                if ("true".equals(formBean.getHdnrbtnViewMultiple()[i]))
                {
                    arr[i] = strMulti;
                }
                else
                {
                    arr[i] = strSingle;
                }
            }
            else
            {
                arr[i] = strna;
            }
        }
        entityBean.setMstViewSelNature(arr);
        arr = formBean.getHdntxtViewRemarks();
        if (arr != null)
        {
            for (int i = 0; i < arr.length; i++)
            {
                if (arr[i].equals(""))
                {
                    arr[i] = strna;
                }
            }
        }
        entityBean.setMstViewRemarks(arr);
        return entityBean;
    }

    public SqlRowSet getProjectArray() throws ClassNotFoundException, SQLException
    {
        return manager.getProjectArray();
    }

    public String[] getAliasArray()
    {
        return manager.getAliasArray();
    }

    public String getDataBaseType(final FinMasterFormBean formBean) throws ClassNotFoundException, SQLException
    {
        return manager.getDataBaseType(formBean);
    }

    public List getTableNames(final FinMasterFormBean formBean) throws ClassNotFoundException, SQLException
    {
        return manager.getTableNames(formBean);
    }

    public List getTableColumnsNames(final FinMasterFormBean formBean) throws ClassNotFoundException, SQLException
    {
        return manager.getTableColumnsNames(formBean);
    }

    public String getPrimaryColumn(final FinMasterFormBean formBean) throws ClassNotFoundException, SQLException
    {
        return manager.getPrimaryColumn(formBean);
    }

    public String getMstTableColumnWidth(final FinMasterFormBean formBean) throws ClassNotFoundException, SQLException
    {
        return manager.getMstTableColumnWidth(formBean);
    }

    public String checkSequence(final FinMasterFormBean formBean)
    {
        return manager.checkSequence(formBean);
    }

    public String checkQuery(final FinMasterFormBean formBean, final String tab)
    {
        return manager.checkQuery(formBean, tab);
    }

    public int insertIntoDataBase(final FinMasterFormBean formBean) throws ClassNotFoundException, SQLException
    {
        return manager.insertIntoDataBase(formBeanToEntityBean(formBean));
    }

    public String getColumnsTypes(final FinMasterFormBean formBean) throws ClassNotFoundException, SQLException
    {
        Map<String, List<String>> map;
        map = manager.getColumnsTypes(formBean);

        List<String> colNames;
        colNames = map.get("colNames");
        List<String> colTypes;
        colTypes = map.get("colTypes");

        StringBuilder sbNames;
        sbNames = new StringBuilder();
        StringBuilder sbTypes;
        sbTypes = new StringBuilder();

        for (int i = 0; i < colNames.size(); i++)
        {
            sbNames.append("\"").append(colNames.get(i)).append("\",");
            sbTypes.append("\"").append(colTypes.get(i)).append("\",");
        }

        sbNames.deleteCharAt(sbNames.length() - 1);
        sbTypes.deleteCharAt(sbTypes.length() - 1);

        return "{colNames:[" + sbNames.toString() + "],colTypes:[" + sbTypes.toString() + "]}";
    }

    public void setSchema(final FinMasterFormBean formBean) throws ClassNotFoundException, SQLException
    {
        manager.setSchema(formBean);
    }
}
