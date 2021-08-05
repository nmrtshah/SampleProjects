/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.business.finstudio.model;

import com.finlogic.apps.finstudio.formbean.MasterGeneratorFormBean;
import com.finlogic.business.finstudio.entitybean.MasterGeneratorEntityBean;
import finutils.directconn.DBConnManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author njuser
 */
public class MasterGeneratorModel
{

    public String validate(MasterGeneratorFormBean formBean)
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

    public List getTableNames(MasterGeneratorFormBean formBean) throws Exception
    {
        MasterGeneratorDataManager dataManager = new MasterGeneratorDataManager();
        return dataManager.getTableNames(formBean);
    }

    public ArrayList<String> getColumnNamesOfTable(MasterGeneratorFormBean formBean) throws Exception
    {
        MasterGeneratorDataManager dataManager = new MasterGeneratorDataManager();
        List columnNames = dataManager.getColumnNamesOfTable(formBean);
        ArrayList<String> colNames = new ArrayList<String>();
        HashMap map = null;
        for (int i = 0; i < columnNames.size(); i++)
        {
            map = (HashMap) columnNames.get(i);
            colNames.add(map.get("COLUMN_NAME").toString());
        }
        return colNames;
    }

    public ArrayList<String> getSelectedColumns(MasterGeneratorFormBean formBean, ArrayList<String> allColumns)
    {
        String[] controls = formBean.getInputControl();

        ArrayList<String> selectedColumns = new ArrayList<String>();
        for (int i = 0; i < controls.length; i++)
        {
            if (!(controls[i].equals("--Select Control--")))
            {
                selectedColumns.add(allColumns.get(i));
            }
        }
        return selectedColumns;
    }

    public ArrayList<String> getSelectedControls(MasterGeneratorFormBean formBean)
    {
        String[] controls = formBean.getInputControl();

        ArrayList<String> selectedControls = new ArrayList<String>();
        for (int i = 0; i < controls.length; i++)
        {

            if (!controls[i].equals("--Select Control--"))
            {
                selectedControls.add(controls[i]);
            }
        }
        return selectedControls;
    }

    public long insertRecord(MasterGeneratorEntityBean entityBean) throws Exception
    {
        MasterGeneratorDataManager dataManager = new MasterGeneratorDataManager();
        return dataManager.insertRecord(entityBean);
    }
}
