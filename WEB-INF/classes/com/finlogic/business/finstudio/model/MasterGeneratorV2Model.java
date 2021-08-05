/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.business.finstudio.model;

import com.finlogic.apps.finstudio.formbean.MasterGeneratorV2FormBean;
import com.finlogic.business.finstudio.entitybean.MasterGeneratorV2EntityBean;
import finutils.directconn.DBConnManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author njuser
 */
public class MasterGeneratorV2Model
{

    public String validate(MasterGeneratorV2FormBean formBean)
    {
        boolean validAlias = false;
        DBConnManager dbcon = new DBConnManager();
        String aliases[] = dbcon.getConnAliasArray();

        for (String alias : aliases)
        {
            if (alias.equalsIgnoreCase(formBean.getAliasName()))
            {
                validAlias = true;
                break;
            }
        }
        if (validAlias == false)
        {
            return "InvalidAlias";
        }
        return "ValidAlias";
    }

    public List getTableNames(MasterGeneratorV2FormBean formBean, String tableType) throws Exception
    {
        MasterGeneratorV2DataManager dataManager = new MasterGeneratorV2DataManager();
        return dataManager.getTableNames(formBean, tableType);
    }

    public ArrayList<String> getColumnNamesOfTable(MasterGeneratorV2FormBean formBean, String tableType) throws Exception
    {
        MasterGeneratorV2DataManager dataManager = new MasterGeneratorV2DataManager();
        List columnNames = dataManager.getColumnNamesOfTable(formBean, tableType);
        ArrayList<String> colNames = new ArrayList<String>();
        HashMap map = null;
        for (int i = 0; i < columnNames.size(); i++)
        {
            map = (HashMap) columnNames.get(i);
            colNames.add(map.get("COLUMN_NAME").toString());
        }
        return colNames;
    }

    public ArrayList<String> getSelectedColumns(MasterGeneratorV2FormBean formBean, String tableType) throws Exception
    {
        ArrayList<String> allColumns = null;
        String[] allControls = null;
        if (tableType.equals("master"))
        {
            allColumns = formBean.getMasterColumns();
            allControls = formBean.getInputControlMaster();
        }
        else if (tableType.equals("detail"))
        {
            allColumns = formBean.getDetailColumns();
            allControls = formBean.getInputControlDetail();
        }
        ArrayList<String> selectedColumns = new ArrayList<String>();
        for (int i = 0; i < allControls.length; i++)
        {
            if (!(allControls[i].equals("--Select Control--")))
            {
                selectedColumns.add(allColumns.get(i));
            }
        }
        return selectedColumns;
    }

    public ArrayList<String> getSelectedControls(MasterGeneratorV2FormBean formBean, String tableType)
    {
        String[] allControls = null;
        if (tableType.equals("master"))
        {
            allControls = formBean.getInputControlMaster();
        }
        else if (tableType.equals("detail"))
        {
            allControls = formBean.getInputControlDetail();
        }

        ArrayList<String> selectedControls = new ArrayList<String>();
        for (int i = 0; i < allControls.length; i++)
        {

            if (!allControls[i].equals("--Select Control--"))
            {
                selectedControls.add(allControls[i]);
            }
        }
        return selectedControls;
    }

    public long insertRecord(MasterGeneratorV2EntityBean entityBean) throws Exception
    {
        MasterGeneratorV2DataManager dataManager = new MasterGeneratorV2DataManager();
        return dataManager.insertRecord(entityBean);
    }
}
