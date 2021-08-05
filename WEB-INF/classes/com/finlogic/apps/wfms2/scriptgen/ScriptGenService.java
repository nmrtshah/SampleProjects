/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.apps.wfms2.scriptgen;

import com.finlogic.business.wfms2.scriptgen.ScriptGenEntityBean;
import com.finlogic.business.wfms2.scriptgen.ScriptGenManager;
import com.finlogic.util.scriptgen.HTMLParserService;
import com.finlogic.util.scriptgen.SeleniumJavaClassGenerator;
import com.finlogic.util.scriptgen.SeleniumScriptGenService;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.support.rowset.SqlRowSet;

/**
 *
 * @author Bhumika Dodiya
 */
public class ScriptGenService
{

    private ScriptGenManager manager = new ScriptGenManager();

    public ScriptGenEntityBean convertFormBeanToEntityBean(final ScriptGenFormBean formBean)
    {
        ScriptGenEntityBean entityBean = new ScriptGenEntityBean();

        entityBean.setAccess(formBean.getAccess());
        entityBean.setAccessID(formBean.getAccessID());
        //  entityBean.setControlNameID(formBean.getControlNameID());
        String Accss[] = formBean.getAccess();
        String CntrlName[] = formBean.getControlNameID();
        for (int i = 0; i < Accss.length; i++)
        {
            if (Accss[i].equalsIgnoreCase("xpath"))
            {
                CntrlName[i] = "-";
                entityBean.setControlNameID(CntrlName);
            }
            else
            {
                entityBean.setControlNameID(formBean.getControlNameID());
            }
        }
        entityBean.setControlValue(formBean.getControlValue());
        entityBean.setEventID(formBean.getEventID());
        entityBean.setHtmlControlID(formBean.getHtmlControlID());
        entityBean.setMessage(formBean.getMessage());
        entityBean.setModuleID(formBean.getModuleID());
        entityBean.setPrjID(formBean.getPrjID());
        entityBean.setTestCaseID(formBean.getTestCaseID());
        entityBean.setTestCaseNatureID(formBean.getTestCaseNatureID());
        entityBean.setConfirmMsg(formBean.getConfirmMsg());
        entityBean.setCmbServerName(formBean.getCmbServerName());
        entityBean.setTxtUserName(formBean.getTxtUserName());
        entityBean.setTxtPassword(formBean.getTxtPassword());
        if (formBean.getChkloginOnce() != null)
        {
            entityBean.setChkloginOnce(formBean.getChkloginOnce());
        }
        else
        {
            entityBean.setChkloginOnce("0");
        }
        entityBean.setXpath(formBean.getXpath());
        entityBean.setEventArray(formBean.getEventArray());
        entityBean.setEmpCode(formBean.getEmpCode());
        entityBean.setFromDate(formBean.getFromDate());
        entityBean.setToDate(formBean.getToDate());
        return entityBean;
    }

    public List getProjectList() throws SQLException
    {
        return manager.getProjectList();
    }

    public List getModuleTreeList(final String projectID) throws SQLException
    {
        return manager.getModuleTreeList(projectID);
    }

    public List getHTMLControlList() throws SQLException
    {
        return manager.getHTMLControlList();
    }

    public List getTestCaseList(final String projectID, final String moduleID) throws SQLException
    {
        return manager.getTestCaseList(projectID, moduleID);
    }

    public List getControlEventList() throws SQLException
    {
        return manager.getControlEventList();
    }

    public List getTestCaseNatureList() throws SQLException
    {
        return manager.getTestCaseNatureList();
    }

    public List getAccessTypeList(final int controlID) throws SQLException
    {
        return manager.getAccessTypeList(controlID);
    }

    public List getAccessTypeList() throws SQLException
    {
        return manager.getAccessTypeList();
    }

    public List getTestCaseValuesList(final String projectID, final String moduleID, final String testCaseID) throws SQLException
    {
        return manager.getTestCaseValuesList(projectID, moduleID, testCaseID);
    }

    public boolean generateScipt(final int TransactionID, final int pageID, final int[] eventArray, final ScriptGenFormBean formBean) throws SQLException
    {
        return new SeleniumScriptGenService().generateScript(TransactionID, pageID, eventArray, convertFormBeanToEntityBean(formBean));
    }

    public List getControlList(final boolean isURL, final String pageURL) throws SQLException
    {
        return new HTMLParserService().getControlList(isURL, pageURL);
    }

    public SqlRowSet getParentModuleList(final int moduleID) throws SQLException
    {
        return manager.getParentModuleList(moduleID);
    }

    public int insertIntoDB(ScriptGenFormBean formBean) throws ClassNotFoundException, SQLException
    {
        return manager.inertIntoDB(convertFormBeanToEntityBean(formBean));
    }

    public String generateScript(int srno, int[] event, ScriptGenFormBean formBean) throws SQLException, IOException
    {
        return new SeleniumJavaClassGenerator().generateScript(srno, event, convertFormBeanToEntityBean(formBean));
    }

    public List<Map> getGenClassList(int projectID, int moduleID, String fromDate, String toDate) throws ClassNotFoundException, SQLException
    {
        List<Map> mastList = manager.getGenClassList(projectID, moduleID, fromDate, toDate);
        //get moduleid list n projectid list
        StringBuilder moduleid = new StringBuilder();
        StringBuilder projectid = new StringBuilder();

        //  StringBuilder testcasid = new StringBuilder();

        boolean flag = false;
        for (int i = 0; i < mastList.size(); i++)
        {
            projectid.append(mastList.get(i).get("PROJECT_ID").toString());
            projectid.append(",");

            moduleid.append(mastList.get(i).get("MODULE_ID").toString());
            moduleid.append(",");
            // testcasid.append(mastList.get(i).get("TEST_CASE_ID").toString());
            //  testcasid.append(",");
            flag = true;
        }
        if (flag)
        {
            projectid.deleteCharAt(projectid.length() - 1);
            moduleid.deleteCharAt(moduleid.length() - 1);
            //  testcasid.deleteCharAt(testcasid.length() - 1);
        }
        //get projectname n modulename n testcase
        List<Map> projectList = new ArrayList();
        List moduleNameList = new ArrayList();
        // List<Map> testCaseList = new ArrayList();
        if (flag)
        {
            projectList = manager.getPrjctModule(projectid.toString());
            moduleNameList = manager.getModuleList(projectid.toString(), moduleid.toString());
            //   testCaseList = manager.getTestCaseList(testcasid.toString());
        }
        //put all data in one map
        for (int i = 0; i < mastList.size(); i++)
        {
            Map mMast = mastList.get(i);
            mMast.put("NO", i + 1);
            mMast.put("PROJECT_NAME", getName(projectList, mastList.get(i).get("PROJECT_ID").toString(), "PRJ_ID", "PRJ_NAME"));
            mMast.put("MODULE_NAME", getName(moduleNameList, mastList.get(i).get("MODULE_ID").toString(), "MODULE_ID", "MODULE_NAME"));
//            if (mastList.get(i).get("TEST_CASE_ID").toString().equals("-1"))
//            {
//                mMast.put("TEST_CASE", "-");
//            }
//            else
//            {
//                mMast.put("TEST_CASE", getName(testCaseList, mastList.get(i).get("TEST_CASE_ID").toString(), "TEST_CASE_ID", "TEST_CASE"));
//            }
        }
        return mastList;
    }

    public List getAllData(int srno) throws ClassNotFoundException, SQLException
    {
        return manager.getAllData(srno);
    }

    public String generateFiles(int srno) throws ClassNotFoundException, SQLException, IOException
    {
        ScriptGenEntityBean entityBean = new ScriptGenEntityBean();
        List<Map> list = manager.getAllData(srno);
        for (int i = 0; i < list.size(); i++)
        {
            Map mMast = list.get(i);
            entityBean.setPrjID(Integer.parseInt(mMast.get("PROJECT_ID").toString()));
            entityBean.setModuleID(Integer.parseInt(mMast.get("MODULE_ID").toString()));
            entityBean.setTestCaseNatureID(convertToInteger(mMast.get("TEST_CASE_ID").toString().split(",")));
            entityBean.setCmbServerName(mMast.get("SERVER_NAME").toString());
            entityBean.setTxtUserName(mMast.get("USER_NAME").toString());
            entityBean.setTxtPassword(mMast.get("PASSWORD").toString());
            entityBean.setChkloginOnce(mMast.get("IS_LOGIN_ONCE").toString());
            entityBean.setHtmlControlID(convertToInteger(mMast.get("CONTROL_ID").toString().split(",")));
            entityBean.setControlNameID(mMast.get("CONTROL_NAME").toString().split(","));
            entityBean.setAccessID(convertToInteger(mMast.get("ACCESS_ID").toString().split(",")));

            int totalControlLength = (mMast.get("CONTROL_ID").toString().split(",")).length;
            String controlvalue = mMast.get("CONTROL_VALUE").toString();
            String controlvalueArray[] = new String[totalControlLength];
            String splitControlvalueArray[] = controlvalue.split("#\\$");
            for (int k = 0; k < totalControlLength; k++)
            {
                if (k < controlvalue.split("#\\$").length)
                {
                    controlvalueArray[k] = splitControlvalueArray[k];
                }
                else
                {
                    controlvalueArray[k] = "";
                }
            }
            entityBean.setControlValue(controlvalueArray);
            entityBean.setEventID(convertToInteger(mMast.get("EVENT_ID").toString().split(",")));
            entityBean.setMessage(mMast.get("MESSAGE").toString().split("#\\$"));
            entityBean.setConfirmMsg(mMast.get("CONFIRM_MESSAGE").toString().split("#\\$"));

            String access = mMast.get("ACCESS").toString();
            String accessArray[] = new String[totalControlLength];
            String splitAccessArray[] = access.split(",");
            for (int k = 0; k < totalControlLength; k++)
            {
                if (k < access.split(",").length)
                {
                    accessArray[k] = splitAccessArray[k];
                }
                else
                {
                    accessArray[k] = "";
                }
            }
            entityBean.setAccess(accessArray);
            entityBean.setXpath(mMast.get("XPATH").toString().split(","));
            entityBean.setEventArray(convertToInteger(mMast.get("TOTAL_EVENTS").toString().split(",")));

        }
        String[] tmp = new String[entityBean.getConfirmMsg().length];
        int count = 0;
        for (int i = 0; i < entityBean.getConfirmMsg().length; i++)
        {
            if (!entityBean.getConfirmMsg()[i].equals("-"))
            {
                tmp[count] = entityBean.getConfirmMsg()[i];
            }
            else
            {
                tmp[count] = "";
            }
            count++;
        }

        entityBean.setConfirmMsg(tmp);
        return new SeleniumJavaClassGenerator().generateScript(srno, entityBean.getEventArray(), entityBean);
    }

    public int[] convertToInteger(String[] tempData)
    {
        int[] temp = new int[tempData.length];

        for (int i = 0; i < tempData.length; i++)
        {
            temp[i] = Integer.parseInt(tempData[i]);
        }
        return temp;
    }

    public String getName(List<Map> dataList, String id, String key, String value)
    {
        String str = "";
        for (int i = 0; i < dataList.size(); i++)
        {
            if (dataList.get(i).get(key).toString().equals(id))
            {
                str = dataList.get(i).get(value).toString();
                break;
            }
        }
        return str;
    }

    public int countComma(String value)
    {
        int count = 0;
        if (value.indexOf("#") > 0)
        {
            count = count + 1;
        }
        return count;
    }
}
