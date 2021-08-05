/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.finmaster;

import com.finlogic.apps.finstudio.finmaster.FinMasterFormBean;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author njuser
 */
public class CommonFunctions
{

    public List<String> getAllEDVFieldsByControl(final FinMasterFormBean formBean, final String control)
    {
        List<String> allEDVComboFields;
        allEDVComboFields = new ArrayList<String>();
        int EDVComboLen = 0;
        if (formBean.isChkEdit())
        {
            String[] editFields;
            editFields = formBean.getHdnEditField();
            String[] editControls;
            editControls = formBean.getHdnEditControl();
            int editLen;
            editLen = editFields.length;
            for (int i = 0; i < editLen; i++)
            {
                if (editControls[i].equals(control))
                {
                    allEDVComboFields.add(editFields[i]);
                }
            }
        }
        if (!allEDVComboFields.isEmpty())
        {
            EDVComboLen = allEDVComboFields.size();
        }

        if (formBean.isChkDelete())
        {
            String[] delFields;
            delFields = formBean.getHdnDeleteField();
            String[] delControls;
            delControls = formBean.getHdnDeleteControl();
            int delLen;
            delLen = delFields.length;
            for (int i = 0; i < delLen; i++)
            {
                if (delControls[i].equals(control))
                {
                    int j = 0;
                    for (; j < EDVComboLen; j++)
                    {
                        if (delFields[i].equals(allEDVComboFields.get(j)))
                        {
                            break;
                        }
                    }
                    if (j == EDVComboLen)
                    {
                        allEDVComboFields.add(delFields[i]);
                    }
                }
            }
        }
        if (!allEDVComboFields.isEmpty())
        {
            EDVComboLen = allEDVComboFields.size();
        }

        if (formBean.isChkView())
        {
            String[] viewFields;
            viewFields = formBean.getHdnViewField();
            String[] viewControls;
            viewControls = formBean.getHdnViewControl();
            int viewLen;
            viewLen = viewFields.length;
            for (int i = 0; i < viewLen; i++)
            {
                if (viewControls[i].equals(control))
                {
                    int j = 0;
                    for (; j < EDVComboLen; j++)
                    {
                        if (allEDVComboFields.get(j).equals(viewFields[i]))
                        {
                            break;
                        }
                    }
                    if (j == EDVComboLen)
                    {
                        allEDVComboFields.add(viewFields[i]);
                    }
                }
            }
        }
        return allEDVComboFields;
    }
}
