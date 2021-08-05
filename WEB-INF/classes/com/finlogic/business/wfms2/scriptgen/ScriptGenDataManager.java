/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.business.wfms2.scriptgen;

import com.finlogic.util.persistence.SQLService;
import com.finlogic.util.persistence.SQLUtility;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;

/**
 *
 * @author Ankur Mistry
 */
public class ScriptGenDataManager
{

    private static final String ALIAS = "wfms2";
    private static final String FINSTUDIO_ALIAS = "finstudio_mysql";
//    private static final String ALIAS = "wfms2_test";
//    private static final String FINSTUDIO_ALIAS = "finstudio_test";
    private static final SQLService SQLSERVICE = new SQLService();
    private static final SQLUtility SQLUTILS = new SQLUtility();

    /**
     *
     * @return List
     * @throws SQLException
     */
    public List getProjectList() throws SQLException
    {
        List projectList = null;
        String PROJECTLISTQUERY;

        try
        {
            PROJECTLISTQUERY = "SELECT PM.PRJ_ID, PM.PRJ_NAME "
                    + "FROM WFMS.PROJECT_MST PM "
                    + "WHERE UPPER ( NVL ( PM.ISACTIVE , 'Y' ) ) = 'Y' AND "
                    + "PM.PRJ_ID != 0 ORDER BY UPPER ( PM.PRJ_NAME )";
            projectList = SQLSERVICE.getList(ALIAS, PROJECTLISTQUERY);
        }
        catch (Exception exp)
        {
            throw new SQLException(exp);
        }
        return projectList;
    }

    /**
     *
     * @param projectID
     * @return List
     * @throws SQLException
     */
    public List getModuleTreeList(final String projectID) throws SQLException
    {
        String MODULELISTQUERY;
        List moduleList = null;

        try
        {
            MODULELISTQUERY = "SELECT LPAD ( '-->' , 12 * LEVEL + 3 , '&nbsp;' ) || MODULE_NAME MNAME , "
                    + "MODULE_ID , MODULE_PARENT_ID "
                    + "FROM WFMS.MODULE_MST "
                    + "WHERE PROJECT_ID = :PROJECT_ID "
                    + "START WITH MODULE_PARENT_ID = -1 "
                    + "CONNECT BY PRIOR MODULE_ID = MODULE_PARENT_ID "
                    + "ORDER SIBLINGS BY MODULE_NAME";
            Map param;
            param = new HashMap();
            param.put("PROJECT_ID", projectID);
            SqlParameterSource paramSource;
            paramSource = new MapSqlParameterSource(param);
            SqlRowSet rowSet;
            rowSet = SQLUTILS.getRowSet(ALIAS, MODULELISTQUERY, paramSource);
            moduleList = new ArrayList<HashMap>();
            while (rowSet.next())
            {
                HashMap hashMap;
                hashMap = new HashMap();
                hashMap.put("MODULE_ID", rowSet.getString("MODULE_ID"));
                hashMap.put("MODULE_NAME", rowSet.getString("MNAME"));
                moduleList.add(hashMap);
            }
        }
        catch (Exception exp)
        {
            throw new SQLException(exp);
        }
        return moduleList;
    }

    /**
     *
     * @param projectID
     * @param moduleID
     * @return List
     * @throws SQLException
     */
    public List getTestCaseList(final String projectID, final String moduleID) throws SQLException
    {
        String TESTCASELISTQUERY;
        List testCaseList = null;

        try
        {
            TESTCASELISTQUERY = "SELECT TCM.TEST_CASE_ID , TCM.TEST_CASE "
                    + "FROM WFMS.TEST_CASE_MST TCM "
                    + "WHERE TCM.MODULE_ID = :MODULE_ID AND TCM.PRJ_ID = :PROJECT_ID "
                    + "ORDER BY UPPER ( TCM.TEST_CASE )";
            Map param;
            param = new HashMap();
            param.put("MODULE_ID", moduleID);
            param.put("PROJECT_ID", projectID);
            SqlParameterSource paramSource;
            paramSource = new MapSqlParameterSource(param);
            testCaseList = SQLSERVICE.getList(ALIAS, TESTCASELISTQUERY, paramSource);
        }
        catch (Exception exp)
        {
            throw new SQLException(exp);
        }
        return testCaseList;
    }

    /**
     *
     * @return List
     * @throws SQLException
     */
    public List getHTMLControlList() throws SQLException
    {

        String HTMLLISTQUERY;
        List htmlControlList = null;

        try
        {
            HTMLLISTQUERY = "SELECT CM.CONTROL_ID , INITCAP ( CM.CONTROL_NAME ) CONTROL_NAME "
                    + "FROM WFMS.CONTROL_MST CM "
                    + "ORDER BY CM.CONTROL_NAME";
            htmlControlList = SQLSERVICE.getList(ALIAS, HTMLLISTQUERY);
        }
        catch (Exception exp)
        {
            throw new SQLException(exp);
        }
        return htmlControlList;
    }

    /**
     *
     * @return List
     * @throws SQLException
     */
    public List getControlEventList() throws SQLException
    {
        List eventList = null;
        String EVENTLISTQUERY;

        try
        {
            EVENTLISTQUERY = "SELECT CEM.EVENT_ID , 'On' || CEM.EVENT_NAME EVENT_NAME "
                    + "FROM FINLOGIC.CONTROL_EVENT_MASTER CEM";
            eventList = SQLSERVICE.getList(ALIAS, EVENTLISTQUERY);
        }
        catch (Exception exp)
        {
            throw new SQLException(exp);
        }
        return eventList;
    }

    /**
     *
     * @return List
     * @throws SQLException
     */
    public List getTestCaseNatureList() throws SQLException
    {
        List caseList = null;
        String CASELISTQUERY;

        try
        {
            CASELISTQUERY = "SELECT TCNM.TEST_CASE_NATURE_ID , TCNM.TEST_CASE_NATURE "
                    + "FROM FINLOGIC.TEST_CASE_NATURE_MST TCNM";
            caseList = SQLSERVICE.getList(ALIAS, CASELISTQUERY);
        }
        catch (Exception exp)
        {
            throw new SQLException(exp);
        }
        return caseList;
    }

    /**
     *
     * @param projectID
     * @param moduleID
     * @param testCaseID
     * @return List
     * @throws SQLException
     */
    public List getTestCaseValuesList(final String projectID, final String moduleID, final String testCaseID) throws SQLException
    {
        List valueList = null;
        String VALUELISTQUERY;

        try
        {
            VALUELISTQUERY = "SELECT TCV.F_VALUE VALUE, TCV.F_NATURE NATURE "
                    + "FROM FINLOGIC.TAB_TESTCASEVALUES TCV "
                    + "INNER JOIN WFMS.TEST_CASE_MST TCM "
                    + "ON TCM.TEST_CASE_ID = TCV.F_TESTCASEID "
                    + "INNER JOIN WFMS.PROJECT_MST PM "
                    + "ON TCM.PRJ_ID = PM.PRJ_ID "
                    + "INNER JOIN WFMS.MODULE_MST MM "
                    + "ON TCM.MODULE_ID = MM.MODULE_ID "
                    + "WHERE PM.PRJ_ID = :PROJECT_ID AND MM.MODULE_ID = :MODULE_ID "
                    + "AND TCM.TEST_CASE_ID = :TEST_CASE_ID";
            Map param;
            param = new HashMap();
            param.put("PROJECT_ID", projectID);
            param.put("MODULE_ID", moduleID);
            param.put("TEST_CASE_ID", testCaseID);
            SqlParameterSource paramSource;
            paramSource = new MapSqlParameterSource(param);
            valueList = SQLSERVICE.getList(ALIAS, VALUELISTQUERY, paramSource);
        }
        catch (Exception exp)
        {
            throw new SQLException(exp);
        }
        return valueList;
    }

    /**
     *
     * @param htmlControlID
     * @return List
     * @throws SQLException
     */
    public List getAccessTypeList(final int htmlControlID) throws SQLException
    {
        List accessTypeList = null;
        String ACCESSLISTQUERY;

        try
        {
            ACCESSLISTQUERY = "SELECT CAM.ACCESS_ID , CAM.ACCESS_TYPE "
                    + "FROM FINLOGIC.CONTROL_ACCESS_MST CAM "
                    + "WHERE CAM.CONTROL_ID = :CONTROL_ID";
            Map param;
            param = new HashMap();
            param.put("CONTROL_ID", htmlControlID);
            SqlParameterSource paramSource;
            paramSource = new MapSqlParameterSource(param);
            accessTypeList = SQLSERVICE.getList(ALIAS, ACCESSLISTQUERY, paramSource);
        }
        catch (Exception exp)
        {
            throw new SQLException(exp);
        }
        return accessTypeList;
    }

    /**
     *
     * @return List
     * @throws SQLException
     */
    public List getAccessTypeList() throws SQLException
    {
        List accessList = null;
        String ACCESSLISTQUERY;

        try
        {
            ACCESSLISTQUERY = "SELECT CAM.ACCESS_ID, CAM.ACCESS_TYPE "
                    + "FROM FINLOGIC.CONTROL_ACCESS_MASTER CAM ";
            accessList = SQLSERVICE.getList(ALIAS, ACCESSLISTQUERY);
        }
        catch (Exception exp)
        {
            throw new SQLException(exp);
        }
        return accessList;
    }

    /**
     *
     * @param accessID
     * @return String
     * @throws SQLException
     */
    public String getAccessType(final int accessID) throws SQLException
    {
        String accessType = null;
        String ACCESSTYPEQUERY;

        try
        {
            ACCESSTYPEQUERY = "SELECT CAM.ACCESS_TYPE "
                    + "FROM FINLOGIC.CONTROL_ACCESS_MST CAM "
                    + "WHERE CAM.ACCESS_ID = :ACCESS_ID";
            Map param;
            param = new HashMap();
            param.put("ACCESS_ID", accessID);
            SqlParameterSource paramSource;
            paramSource = new MapSqlParameterSource(param);
            accessType = SQLSERVICE.getString(ALIAS, ACCESSTYPEQUERY, paramSource);
        }
        catch (Exception exp)
        {
            throw new SQLException(exp);
        }
        return accessType;
    }

    /**
     *
     * @param accessType
     * @return int
     * @throws SQLException
     */
    public int getAccessID(final String accessType) throws SQLException
    {
        int accessID = 0;
        String ACCESSIDQUERY;

        try
        {
            ACCESSIDQUERY = "SELECT UNIQUE CAM.ACCESS_ID "
                    + "FROM FINLOGIC.CONTROL_ACCESS_MST CAM "
                    + "WHERE CAM.ACCESS_TYPE = :ACCESS_TYPE";
            Map param;
            param = new HashMap();
            param.put("ACCESS_TYPE", accessType);
            SqlParameterSource paramSource;
            paramSource = new MapSqlParameterSource(param);
            accessID = SQLSERVICE.getInt(ALIAS, ACCESSIDQUERY, paramSource);
        }
        catch (Exception exp)
        {
            throw new SQLException(exp);
        }
        return accessID;
    }

    /**
     *
     * @param prjID
     * @return String
     * @throws SQLException
     */
    public String getProjectName(final int prjID) throws SQLException
    {

        String PROJECTNAMEQUERY;
        String projectName = null;

        try
        {
            PROJECTNAMEQUERY = "SELECT PM.PRJ_NAME "
                    + "FROM WFMS.PROJECT_MST PM "
                    + "WHERE PM.PRJ_ID = :PROJECT_ID";
            Map param;
            param = new HashMap();
            param.put("PROJECT_ID", prjID);
            SqlParameterSource paramSource;
            paramSource = new MapSqlParameterSource(param);
            projectName = SQLSERVICE.getString(ALIAS, PROJECTNAMEQUERY, paramSource);
        }
        catch (Exception exp)
        {
            throw new SQLException(exp);
        }
        return projectName;
    }

    /**
     *
     * @param moduleID
     * @return String
     * @throws SQLException
     */
    public String getModuleName(final int moduleID) throws SQLException
    {

        String MODULENAMEQUERY;
        String moduleName = null;

        try
        {
            MODULENAMEQUERY = "SELECT MM.MODULE_NAME "
                    + "FROM WFMS.MODULE_MST MM "
                    + "WHERE MM.MODULE_ID = :MODULE_ID";
            Map param;
            param = new HashMap();
            param.put("MODULE_ID", moduleID);
            SqlParameterSource paramSource;
            paramSource = new MapSqlParameterSource(param);
            moduleName = SQLSERVICE.getString(ALIAS, MODULENAMEQUERY, paramSource);
        }
        catch (Exception exp)
        {
            throw new SQLException(exp);
        }
        return moduleName;
    }

    /**
     *
     * @param testCaseID
     * @return String
     * @throws SQLException
     */
    public String getTestCase(final String testCaseID) throws SQLException
    {

        String TESTCASEQUERY;
        String testCase = null;

        try
        {
            TESTCASEQUERY = "SELECT TCM.TEST_CASE "
                    + "FROM WFMS.TEST_CASE_MST TCM "
                    + "WHERE TCM.TEST_CASE_ID = :TEST_CASE_ID";
            Map param;
            param = new HashMap();
            param.put("TEST_CASE_ID", testCaseID);
            SqlParameterSource paramSource;
            paramSource = new MapSqlParameterSource(param);
            testCase = SQLSERVICE.getString(ALIAS, TESTCASEQUERY, paramSource);
        }
        catch (Exception exp)
        {
            throw new SQLException(exp);
        }
        return testCase;
    }

    /**
     *
     * @param browserID
     * @return String
     * @throws SQLException
     */
    public String getBrowserName(final int browserID) throws SQLException
    {
        String BROWSERNAMEQUERY;
        String browserName = null;

        try
        {
            BROWSERNAMEQUERY = "SELECT BM.BROWSER_NAME "
                    + "FROM WFMS.BROWSER_MST BM "
                    + "WHERE BM.BROWSER_ID = :BROWSER_ID";
            Map param;
            param = new HashMap();
            param.put("BROWSER_ID", browserID);
            SqlParameterSource paramSource;
            paramSource = new MapSqlParameterSource(param);
            browserName = SQLSERVICE.getString(ALIAS, BROWSERNAMEQUERY, paramSource);
        }
        catch (Exception exp)
        {
            throw new SQLException(exp);
        }
        return browserName;
    }

    /**
     *
     * @param htmlControlID
     * @return String
     * @throws SQLException
     */
    public String getHTMLControlName(final int htmlControlID) throws SQLException
    {
        String NAMEQUERY;
        String htmlControlName = null;

        try
        {
            NAMEQUERY = "SELECT INITCAP ( CM.CONTROL_NAME ) "
                    + "FROM WFMS.CONTROL_MST CM "
                    + "WHERE CM.CONTROL_ID = :CONTROL_ID";
            Map param;
            param = new HashMap();
            param.put("CONTROL_ID", htmlControlID);
            SqlParameterSource paramSource;
            paramSource = new MapSqlParameterSource(param);
            htmlControlName = SQLSERVICE.getString(ALIAS, NAMEQUERY, paramSource);
        }
        catch (Exception exp)
        {
            throw new SQLException(exp);
        }
        return htmlControlName;
    }

    /**
     *
     * @param htmlControlName
     * @return int
     * @throws SQLException
     */
    public int getHTMLControlID(final String htmlControlName) throws SQLException
    {
        int htmlControlID = 0;
        String HTMLIDQUERY;

        try
        {
            HTMLIDQUERY = "SELECT CM.CONTROL_ID "
                    + "FROM WFMS.CONTROL_MST CM "
                    + "WHERE CM.CONTROL_NAME = :CONTROL_NAME";
            Map param;
            param = new HashMap();
            param.put("CONTROL_NAME", htmlControlName);
            SqlParameterSource paramSource;
            paramSource = new MapSqlParameterSource(param);
            htmlControlID = SQLSERVICE.getInt(ALIAS, HTMLIDQUERY, paramSource);
        }
        catch (Exception exp)
        {
            throw new SQLException(exp);
        }
        return htmlControlID;
    }

    /**
     *
     * @param eventID
     * @return String
     * @throws SQLException
     */
    public String getControlEventName(final int eventID) throws SQLException
    {
        String eventName = null;
        String EVENTNAMEQUERY;

        try
        {
            EVENTNAMEQUERY = "SELECT 'On' || CEM.EVENT_NAME "
                    + "FROM FINLOGIC.CONTROL_EVENT_MASTER CEM "
                    + "WHERE CEM.EVENT_ID = :EVENT_ID";
            Map param;
            param = new HashMap();
            param.put("EVENT_ID", eventID);
            SqlParameterSource paramSource;
            paramSource = new MapSqlParameterSource(param);
            eventName = SQLSERVICE.getString(ALIAS, EVENTNAMEQUERY, paramSource);
        }
        catch (Exception exp)
        {
            throw new SQLException(exp);
        }
        return eventName;
    }

    /**
     *
     * @param eventName
     * @return int
     * @throws SQLException,Exception
     */
    public int getControlEventID(final String eventName) throws SQLException
    {
        int eventID = 0;
        String EVENTIDQUERY;

        try
        {
            EVENTIDQUERY = "SELECT CEM.EVENT_ID "
                    + "FROM FINLOGIC.CONTROL_EVENT_MASTER CEM "
                    + "WHERE LOWER ( CEM.EVENT_NAME ) = LOWER ( :EVENT_NAME )";
            Map param;
            param = new HashMap();
            param.put("EVENT_NAME", eventName);
            SqlParameterSource paramSource;
            paramSource = new MapSqlParameterSource(param);
            eventID = SQLSERVICE.getInt(ALIAS, EVENTIDQUERY, paramSource);
        }
        catch (Exception exp)
        {
            throw new SQLException(exp);
        }
        return eventID;
    }

    /**
     *
     * @param testCaseNatureID
     * @return String
     * @throws SQLException
     */
    public String getTestCaseNature(final int testCaseNatureID) throws SQLException
    {
        String caseNature = null;
        String NATUREQUERY;

        try
        {
            NATUREQUERY = "SELECT TCNM.TEST_CASE_NATURE "
                    + "FROM FINLOGIC.TEST_CASE_NATURE_MST TCNM "
                    + "WHERE TCNM.TEST_CASE_NATURE_ID = :TEST_CASE_NATURE_ID";
            Map param;
            param = new HashMap();
            param.put("TEST_CASE_NATURE_ID", testCaseNatureID);
            SqlParameterSource paramSource;
            paramSource = new MapSqlParameterSource(param);
            caseNature = SQLSERVICE.getString(ALIAS, NATUREQUERY, paramSource);
        }
        catch (Exception exp)
        {
            throw new SQLException(exp);
        }
        return caseNature;
    }

    public SqlRowSet getParentModuleList(int moduleID) throws SQLException
    {
        SqlRowSet parentModuleList = null;
        String MODULELISTQUERY;

        try
        {
            MODULELISTQUERY = "SELECT MM.MODULE_NAME"
                    + " FROM WFMS.MODULE_MST MM  "
                    + " START WITH MODULE_ID = :MODULE_ID"
                    + " CONNECT BY PRIOR MODULE_PARENT_ID = MODULE_ID"
                    + " ORDER SIBLINGS BY MODULE_NAME";

            Map param;
            param = new HashMap();
            param.put("MODULE_ID", moduleID);
            SqlParameterSource paramSource;
            paramSource = new MapSqlParameterSource(param);
            parentModuleList = SQLSERVICE.getRowSet(ALIAS, MODULELISTQUERY, paramSource);
        }
        catch (Exception exp)
        {
            throw new SQLException(exp);
        }
        return parentModuleList;
    }

    public int insertIntoDB(ScriptGenEntityBean entityBean) throws ClassNotFoundException, SQLException
    {
        SqlParameterSource sps = new BeanPropertySqlParameterSource(entityBean);
        KeyHolder keys = new GeneratedKeyHolder();
//        
        String insertQuery = "INSERT INTO TEST_TOOL_MAST(PROJECT_ID,MODULE_ID,TEST_CASE_ID,SERVER_NAME,USER_NAME,PASSWORD,IS_LOGIN_ONCE,EMP_CODE,ON_DATE)"
                + " VALUES(:prjID,:moduleID,:testCaseID,:cmbServerName,:txtUserName,:txtPassword," + entityBean.getChkloginOnce() + ",:empCode,SYSDATE())";

        SQLService SQLS = new SQLService();
        SQLS.persist(FINSTUDIO_ALIAS, insertQuery, keys, sps);
        int idTestToolMast = SQLS.getInt(FINSTUDIO_ALIAS, "SELECT SRNO FROM TEST_TOOL_MAST ORDER BY SRNO DESC LIMIT 1");
        //insert into test_tool_control_mast 
        int confirm_msg_count = 0, eventCounter = 0;
        for (int i = 0; i < entityBean.getHtmlControlID().length; i++)
        {
            String insert_control = "INSERT INTO TEST_TOOL_CONTROL_MAST(SRNO_TEST_TOOL_MAST,HTML_CONTROL_ID,CONTROL_NAME,ACCESSID,CONTROL_VALUE,ACCESS,XPATH) VALUES("
                    + idTestToolMast + "," + entityBean.getHtmlControlID()[i] + ",\"" + entityBean.getControlNameID()[i] + "\"," + entityBean.getAccessID()[i] + ",\"" + entityBean.getControlValue()[i] + "\",\"" + entityBean.getAccess()[i] + "\",\"" + entityBean.getXpath()[i] + "\" )";
            SQLS.persist(FINSTUDIO_ALIAS, insert_control);
            int idControlMast = SQLS.getInt(FINSTUDIO_ALIAS, "SELECT SRNO FROM TEST_TOOL_CONTROL_MAST ORDER BY SRNO DESC LIMIT 1");
            int count = 0;
            while (count < entityBean.getEventArray()[i])
            {
                String confirm_message = "-";
                if ((entityBean.getTestCaseNatureID()[eventCounter] == 9 || entityBean.getTestCaseNatureID()[eventCounter] == 3) && entityBean.getConfirmMsg() != null && confirm_msg_count < entityBean.getConfirmMsg().length)
                {
                    if (!"".equals(entityBean.getConfirmMsg()[confirm_msg_count]))
                    {
                        confirm_message = entityBean.getConfirmMsg()[confirm_msg_count];
                    }
                    confirm_msg_count++;
                }
                //insert into test tool event mast
                String insert_event = "INSERT INTO TEST_TOOL_EVENT_MAST(SRNO_CONTROL_MAST,EVENT_ID,TEST_CASE_ID,MESSAGE,CONFIRM_MESSAGE) VALUES("
                        + idControlMast + "," + entityBean.getEventID()[eventCounter] + "," + entityBean.getTestCaseNatureID()[eventCounter] + ",\"" + entityBean.getMessage()[eventCounter] + "\",\"" + confirm_message + "\")";
                SQLS.persist(FINSTUDIO_ALIAS, insert_event);
                eventCounter++;
                count++;
            }
        }
        return idTestToolMast;
    }

    public List getAllData1(int srno) throws ClassNotFoundException, SQLException
    {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM TEST_TOOL_MAST TTM ");
        query.append(" INNER JOIN  ( ");
        query.append("    SELECT TTCM.SRNO,TTCM.SRNO_TEST_TOOL_MAST,GROUP_CONCAT(TTCM.HTML_CONTROL_ID) CONTROL_ID,GROUP_CONCAT(TTCM.CONTROL_NAME) CONTROL_NAME ,GROUP_CONCAT(TTCM.ACCESSID) ACCESS_ID,GROUP_CONCAT(TTCM.CONTROL_VALUE  SEPARATOR '#$') CONTROL_VALUE , GROUP_CONCAT(TTCM.ACCESS) ACCESS,GROUP_CONCAT(TTCM.XPATH) XPATH,  TTEM.* ");
        query.append("    FROM TEST_TOOL_CONTROL_MAST TTCM ");
        query.append("    LEFT JOIN ");
        query.append("    ( ");
        query.append("        SELECT TTEM.SRNO_CONTROL_MAST,GROUP_CONCAT(TTEM.EVENT_ID) EVENT_ID,GROUP_CONCAT(TTEM.TEST_CASE_ID) TEST_CASE_ID,GROUP_CONCAT(TTEM.MESSAGE SEPARATOR '#$') MESSAGE,GROUP_CONCAT(TTEM.CONFIRM_MESSAGE SEPARATOR '#$') CONFIRM_MESSAGE ,GROUP_CONCAT(CNT.TOTAL) TOTAL_EVENTS ");
        query.append("        FROM TEST_TOOL_EVENT_MAST TTEM ");
        query.append("        LEFT JOIN ");
        query.append("        ( ");
        query.append("            SELECT COUNT(CNT.EVENT_ID) TOTAL,CNT.SRNO ");
        query.append("            FROM TEST_TOOL_EVENT_MAST CNT ");
        query.append("            WHERE CNT.SRNO_CONTROL_MAST IN ");
        query.append("            ( ");
        query.append("                SELECT SRNO FROM TEST_TOOL_CONTROL_MAST  WHERE SRNO_TEST_TOOL_MAST =  ");
        query.append(srno);
        query.append("            ) ");
        query.append("            GROUP BY CNT.SRNO_CONTROL_MAST ");
        query.append("         ) CNT ");
        query.append("        ON CNT.SRNO = TTEM.SRNO ");
        query.append("        WHERE TTEM.SRNO_CONTROL_MAST IN (SELECT SRNO FROM TEST_TOOL_CONTROL_MAST WHERE SRNO_TEST_TOOL_MAST = ");
        query.append(srno);
        query.append(" ) ");
        query.append("    )TTEM ");
        query.append("    ON TTEM.SRNO_CONTROL_MAST = TTCM.SRNO ");
        query.append("    WHERE TTCM.SRNO_TEST_TOOL_MAST = ");
        query.append(srno);
        query.append(" ) TTCM ");
        query.append(" ON TTCM.SRNO_TEST_TOOL_MAST =  TTM.SRNO ");
        query.append(" WHERE TTM.SRNO = ");
        query.append(srno);
        return SQLSERVICE.getList(FINSTUDIO_ALIAS, query.toString());
    }

    public List getAllData(int srno) throws ClassNotFoundException, SQLException
    {
        String SR_NO_CONTROL_MAST = "";
        StringBuilder GRP_CONCAT_EVENT_ID = new StringBuilder("");
        StringBuilder GRP_CONCAT_TEST_CASE_ID = new StringBuilder("");
        StringBuilder GRP_CONCAT_MESSAGE = new StringBuilder("");
        StringBuilder GRP_CONCAT_CONFIRM_MESSAGE = new StringBuilder("");
        StringBuilder GRP_CONCAT_TOTAL_EVENTS = new StringBuilder("");



        StringBuilder query = new StringBuilder();

        //query 1
        query.append("        SELECT TTEM.SRNO_CONTROL_MAST,TTEM.EVENT_ID EVENT_ID,TTEM.TEST_CASE_ID TEST_CASE_ID,TTEM.MESSAGE MESSAGE,TTEM.CONFIRM_MESSAGE CONFIRM_MESSAGE,CNT.TOTAL TOTAL_EVENTS ");
        query.append("        FROM TEST_TOOL_EVENT_MAST TTEM ");
        query.append("        LEFT JOIN ");
        query.append("        ( ");
        query.append("            SELECT COUNT(CNT.EVENT_ID) TOTAL,CNT.SRNO ");
        query.append("            FROM TEST_TOOL_EVENT_MAST CNT ");
        query.append("            WHERE CNT.SRNO_CONTROL_MAST IN ");
        query.append("            ( ");
        query.append("                SELECT SRNO FROM TEST_TOOL_CONTROL_MAST  WHERE SRNO_TEST_TOOL_MAST =  ");
        query.append(srno);
        query.append("            ) ");
        query.append("            GROUP BY CNT.SRNO_CONTROL_MAST ");
        query.append("         ) CNT ");
        query.append("        ON CNT.SRNO = TTEM.SRNO ");
        query.append("        WHERE TTEM.SRNO_CONTROL_MAST IN (SELECT SRNO FROM TEST_TOOL_CONTROL_MAST WHERE SRNO_TEST_TOOL_MAST = ");
        query.append(srno);
        query.append(" ) ");
        List resultquery1 = SQLSERVICE.getList(FINSTUDIO_ALIAS, query.toString());

        int len = resultquery1.size();
        Map m = null;
        for (int i = 0; i < len; i++)
        {

            m = (Map) resultquery1.get(i);
            if (i == 0)
            {
                SR_NO_CONTROL_MAST = m.get("SRNO_CONTROL_MAST").toString();
            }

            if (m.get("EVENT_ID") != null)
            {
                GRP_CONCAT_EVENT_ID.append(m.get("EVENT_ID").toString()).append(",");
            }
            if (m.get("TEST_CASE_ID") != null)
            {
                GRP_CONCAT_TEST_CASE_ID.append(m.get("TEST_CASE_ID").toString()).append(",");
            }
            if (m.get("MESSAGE") != null)
            {
                GRP_CONCAT_MESSAGE.append(encodeSingleQuote(m.get("MESSAGE").toString())).append("#$");
            }

            if (m.get("CONFIRM_MESSAGE") != null && !m.get("CONFIRM_MESSAGE").equals("-"))
            {
                GRP_CONCAT_CONFIRM_MESSAGE.append(encodeSingleQuote(m.get("CONFIRM_MESSAGE").toString())).append("#$");
            }
//            else
//            {
//                GRP_CONCAT_CONFIRM_MESSAGE.append("");
//            }
//            if (m.get("CONFIRM_MESSAGE") != null) 
//            {
//                GRP_CONCAT_CONFIRM_MESSAGE.append(encodeSingleQuote(m.get("CONFIRM_MESSAGE").toString())).append("#$");
//            }

            if (m.get("TOTAL_EVENTS") != null)
            {
                GRP_CONCAT_TOTAL_EVENTS.append(m.get("TOTAL_EVENTS")).append(",");
            }

        }



// for substring        

        String String_GROUP_CONCAT_EVENT_ID = "";
        String String_GROUP_CONCAT_TEST_CASE_ID = "";
        String String_GROUP_CONCAT_MESSAGE = "";
        String String_GROUP_CONCAT_CONFIRM_MESSAGE = "";
        String String_GROUP_CONCAT_TOTAL_EVENTS = "";

        if (GRP_CONCAT_EVENT_ID != null)
        {
            String_GROUP_CONCAT_EVENT_ID = GRP_CONCAT_EVENT_ID.toString().substring(0, GRP_CONCAT_EVENT_ID.length() - 1);
        }
        else
        {
            String_GROUP_CONCAT_EVENT_ID = (GRP_CONCAT_EVENT_ID).toString();
        }

        if (GRP_CONCAT_TEST_CASE_ID != null)
        {
            String_GROUP_CONCAT_TEST_CASE_ID = GRP_CONCAT_TEST_CASE_ID.toString().substring(0, GRP_CONCAT_TEST_CASE_ID.length() - 1);
        }
        else
        {
            String_GROUP_CONCAT_TEST_CASE_ID = (GRP_CONCAT_TEST_CASE_ID).toString();
        }

        if (GRP_CONCAT_MESSAGE != null)
        {
            String_GROUP_CONCAT_MESSAGE = (GRP_CONCAT_MESSAGE.toString()).substring(0, GRP_CONCAT_MESSAGE.length() - 2);
        }
        else
        {
            String_GROUP_CONCAT_MESSAGE = (GRP_CONCAT_MESSAGE).toString();
        }

        if (GRP_CONCAT_CONFIRM_MESSAGE != null && !GRP_CONCAT_CONFIRM_MESSAGE.equals("-") && GRP_CONCAT_CONFIRM_MESSAGE.length() > 0)
        {
            String_GROUP_CONCAT_CONFIRM_MESSAGE = (GRP_CONCAT_CONFIRM_MESSAGE.toString()).substring(0, GRP_CONCAT_CONFIRM_MESSAGE.length() - 2);
        }
        else
        {
            String_GROUP_CONCAT_CONFIRM_MESSAGE = "";
        }

        if (GRP_CONCAT_TOTAL_EVENTS != null)
        {
            String_GROUP_CONCAT_TOTAL_EVENTS = (GRP_CONCAT_TOTAL_EVENTS.toString()).substring(0, GRP_CONCAT_TOTAL_EVENTS.length() - 1);
        }
        else
        {
            String_GROUP_CONCAT_TOTAL_EVENTS = (GRP_CONCAT_TOTAL_EVENTS).toString();
        }


//query2
        String SR_NO_TEST_TOOL_MAST = "";
        StringBuilder GRP_CONCAT_CONTROL_ID = new StringBuilder("");
        StringBuilder GRP_CONCAT_CONTROL_NAME = new StringBuilder("");
        StringBuilder GRP_CONCAT_ACCESS_ID = new StringBuilder("");
        StringBuilder GRP_CONCAT_CONTROL_VALUE = new StringBuilder("");
        StringBuilder GRP_CONCAT_ACCESS = new StringBuilder("");
        StringBuilder GRP_CONCAT_XPATH = new StringBuilder("");


        StringBuilder query2 = new StringBuilder("");
        query2.append(" SELECT TTCM.SRNO,TTCM.SRNO_TEST_TOOL_MAST,TTCM.HTML_CONTROL_ID CONTROL_ID,TTCM.CONTROL_NAME CONTROL_NAME ,TTCM.ACCESSID ACCESS_ID,TTCM.CONTROL_VALUE CONTROL_VALUE ,TTCM.ACCESS ACCESS,TTCM.XPATH XPATH,  TTEM.* ");
        query2.append(" FROM TEST_TOOL_CONTROL_MAST TTCM  ");
        query2.append(" LEFT JOIN  ");
        query2.append(" (  ");
        query2.append(" SELECT  ");
        query2.append(" '").append(SR_NO_CONTROL_MAST).append("' SRNO_CONTROL_MAST, ");
        query2.append(" '").append(String_GROUP_CONCAT_EVENT_ID).append("' EVENT_ID, ");
        query2.append(" '").append(String_GROUP_CONCAT_TEST_CASE_ID).append("' TEST_CASE_ID, ");
        query2.append(" '").append(String_GROUP_CONCAT_MESSAGE).append("' MESSAGE, ");
        query2.append(" '").append(String_GROUP_CONCAT_CONFIRM_MESSAGE).append("' CONFIRM_MESSAGE, ");
        query2.append(" '").append(String_GROUP_CONCAT_TOTAL_EVENTS).append("' TOTAL_EVENTS ");
        query2.append(" FROM TEST_TOOL_MAST LIMIT 1        ");
        query2.append(" )TTEM  ");
        query2.append(" ON TTEM.SRNO_CONTROL_MAST = TTCM.SRNO  ");
        query2.append(" WHERE TTCM.SRNO_TEST_TOOL_MAST=");
        query2.append(srno);



        List resultquery2 = SQLSERVICE.getList(FINSTUDIO_ALIAS, query2.toString());
        int lenquery2list = resultquery2.size();
        Map m2 = null;
        for (int i = 0; i < lenquery2list; i++)
        {
            m2 = (Map) resultquery2.get(i);

            if (i == 0)
            {
                SR_NO_CONTROL_MAST = m2.get("SRNO").toString();
                SR_NO_TEST_TOOL_MAST = m2.get("SRNO_TEST_TOOL_MAST").toString();
            }
            if (m2.get("CONTROL_ID") != null)
            {
                GRP_CONCAT_CONTROL_ID.append(m2.get("CONTROL_ID").toString()).append(",");
            }
            if (m2.get("CONTROL_NAME") != null)
            {
                GRP_CONCAT_CONTROL_NAME.append(m2.get("CONTROL_NAME").toString()).append(",");
            }
            if (m2.get("ACCESS_ID") != null)
            {
                GRP_CONCAT_ACCESS_ID.append(m2.get("ACCESS_ID").toString()).append(",");
            }
            if (m2.get("CONTROL_VALUE") != null)
            {
                GRP_CONCAT_CONTROL_VALUE.append(encodeSingleQuote(m2.get("CONTROL_VALUE").toString())).append("#$");
            }
            if (m2.get("ACCESS") != null)
            {
                GRP_CONCAT_ACCESS.append(m2.get("ACCESS").toString()).append(",");
            }
            if (m2.get("XPATH") != null)
            {
                GRP_CONCAT_XPATH.append(encodeSingleQuote(m2.get("XPATH").toString())).append(",");
            }
        }

// for substring   

        String GROUP_CONCAT_CONTROL_ID = "";
        String GROUP_CONCAT_CONTROL_NAME = "";
        String GROUP_CONCAT_ACCESS_ID = "";
        String GROUP_CONCAT_CONTROL_VALUE = "";
        String GROUP_CONCAT_ACCESS = "";
        String GROUP_CONCAT_XPATH = "";


        if (GRP_CONCAT_CONTROL_ID != null)
        {
            GROUP_CONCAT_CONTROL_ID = GRP_CONCAT_CONTROL_ID.toString().substring(0, GRP_CONCAT_CONTROL_ID.length() - 1);
        }
        else
        {
            GROUP_CONCAT_CONTROL_ID = (GRP_CONCAT_CONTROL_ID).toString();
        }

        if (GRP_CONCAT_CONTROL_NAME != null)
        {
            GROUP_CONCAT_CONTROL_NAME = (GRP_CONCAT_CONTROL_NAME.toString()).substring(0, GRP_CONCAT_CONTROL_NAME.length() - 1);
        }
        else
        {
            GROUP_CONCAT_CONTROL_NAME = (GRP_CONCAT_CONTROL_NAME).toString();
        }

        if (GRP_CONCAT_ACCESS_ID != null)
        {
            GROUP_CONCAT_ACCESS_ID = (GRP_CONCAT_ACCESS_ID.toString()).substring(0, GRP_CONCAT_ACCESS_ID.length() - 1);
        }
        else
        {
            GROUP_CONCAT_ACCESS_ID = (GRP_CONCAT_ACCESS_ID).toString();
        }

        if (GRP_CONCAT_CONTROL_VALUE != null)
        {
            GROUP_CONCAT_CONTROL_VALUE = (GRP_CONCAT_CONTROL_VALUE.toString()).substring(0, GRP_CONCAT_CONTROL_VALUE.length() - 2);
        }
        else
        {
            GROUP_CONCAT_CONTROL_VALUE = (GRP_CONCAT_CONTROL_VALUE).toString();
        }

        if (GRP_CONCAT_ACCESS != null)
        {
            GROUP_CONCAT_ACCESS = (GRP_CONCAT_ACCESS.toString()).substring(0, GRP_CONCAT_ACCESS.length() - 1);
        }
        else
        {
            GROUP_CONCAT_ACCESS = (GRP_CONCAT_ACCESS).toString();
        }

        if (GRP_CONCAT_XPATH != null)
        {
            GROUP_CONCAT_XPATH = (GRP_CONCAT_XPATH.toString()).substring(0, GRP_CONCAT_XPATH.length() - 1);
        }
        else
        {
            GROUP_CONCAT_XPATH = (GRP_CONCAT_XPATH).toString();
        }


        StringBuilder query3 = new StringBuilder("");
        query3.append(" SELECT * FROM TEST_TOOL_MAST TTM ");
        query3.append("INNER JOIN ");
        query3.append("( ");
        query3.append("  SELECT");
        query3.append(" '").append(SR_NO_CONTROL_MAST).append("' SRNO, ");
        query3.append(" '").append(SR_NO_TEST_TOOL_MAST).append("' SRNO_TEST_TOOL_MAST, ");
        query3.append(" '").append(GROUP_CONCAT_CONTROL_ID).append("' CONTROL_ID, ");
        query3.append(" '").append(GROUP_CONCAT_CONTROL_NAME).append("' CONTROL_NAME, ");
        query3.append(" '").append(GROUP_CONCAT_ACCESS_ID).append("' ACCESS_ID, ");
        query3.append(" '").append(GROUP_CONCAT_CONTROL_VALUE).append("' CONTROL_VALUE, ");
        query3.append(" '").append(GROUP_CONCAT_ACCESS).append("' ACCESS, ");
        query3.append(" '").append(GROUP_CONCAT_XPATH).append("' XPATH, ");
        query3.append(" '").append(SR_NO_CONTROL_MAST).append("' SRNO_CONTROL_MAST, ");
        query3.append(" '").append(String_GROUP_CONCAT_EVENT_ID).append("' EVENT_ID, ");
        query3.append(" '").append(String_GROUP_CONCAT_TEST_CASE_ID).append("' TEST_CASE_ID, ");
        query3.append(" '").append(String_GROUP_CONCAT_MESSAGE).append("' MESSAGE, ");
        query3.append(" '").append(String_GROUP_CONCAT_CONFIRM_MESSAGE).append("' CONFIRM_MESSAGE, ");
        query3.append(" '").append(String_GROUP_CONCAT_TOTAL_EVENTS).append("' TOTAL_EVENTS ");
        query3.append("  FROM TEST_TOOL_MAST LIMIT 1");
        query3.append(") TTCM ");
        query3.append("ON TTCM.SRNO_TEST_TOOL_MAST =  TTM.SRNO ");
        query3.append("WHERE TTM.SRNO = ");
        query3.append(srno);

        List resultquery3 = SQLSERVICE.getList(FINSTUDIO_ALIAS, query3.toString());

        return resultquery3;
    }

    public List getGenClassList(int projectID, int moduleID, String fromDate, String toDate) throws ClassNotFoundException, SQLException
    {

        StringBuilder query = new StringBuilder();
        query.append("SELECT TM.SRNO,IFNULL(TM.EMP_CODE,'-') EMP_CODE,TM.ON_DATE, TM.TEST_CASE_ID, TM.MODULE_ID,TM.PROJECT_ID ,IFNULL(EMP.EMP_NAME,'-') EMP_NAME");
        query.append(" FROM TEST_TOOL_MAST TM ");
        query.append("LEFT JOIN  ( ");
        query.append("  SELECT EMP.EMP_NAME,EMP.EMP_CODE FROM NJHR.EMP_MAST EMP ");
        query.append(")EMP ");
        query.append(" ON EMP.EMP_CODE = TM.EMP_CODE ");
        query.append(" WHERE 1=1 ");
        if (projectID != -1)
        {
            query.append(" AND PROJECT_ID = ");
            query.append(projectID);
        }
        if (moduleID != -1)
        {
            query.append(" AND MODULE_ID = ");
            query.append(moduleID);
        }
        if (!fromDate.equals("") && !toDate.equals(""))
        {
///*------- Edited by Divyang Kankotiya -------- */
//            query.append(" AND DATE_FORMAT(TM.ON_DATE,'%d-%m-%Y') BETWEEN '");
//            query.append(fromDate);
//            query.append("' AND '");
//            query.append(toDate);
//            query.append("'");

            query.append(" AND DATE_FORMAT(ON_DATE , '%Y-%m-%d') >= DATE_FORMAT(DATE(STR_TO_DATE('").append(fromDate).append("','%d-%m-%Y')), '%Y-%m-%d')");
            query.append(" AND DATE_FORMAT(ON_DATE , '%Y-%m-%d') <= DATE_FORMAT(DATE(STR_TO_DATE('").append(toDate).append("','%d-%m-%Y')), '%Y-%m-%d')");
//            query.append(" AND DATE_FORMAT(TM.ON_DATE,'%y-%m-%d') >= ");
//            query.append(" DATE(STR_TO_DATE('" + fromDate + "', '%y-%m-%d'))");
//            query.append(" AND ");
//            query.append("DATE_FORMAT(TM.ON_DATE,'%y-%m-%d') <=");
//            query.append(" DATE(STR_TO_DATE('" + toDate + "', '%y-%m-%d'))");
//          
/*-------End Edited by Divyang Kankotiya -------- */
        }


        return SQLSERVICE.getList(FINSTUDIO_ALIAS, query.toString());
    }

    public List getPrjctModule(String projectID) throws ClassNotFoundException, SQLException
    {
        StringBuilder query = new StringBuilder();
        query.append("SELECT PM.PRJ_ID,PM.PRJ_NAME FROM WFMS.PROJECT_MST PM ");
        query.append(" WHERE UPPER ( NVL ( PM.ISACTIVE , 'Y' ) ) = 'Y' AND PM.PRJ_ID IN (");
        query.append(projectID);
        query.append(" ) ");
        query.append("  ORDER BY UPPER ( PM.PRJ_NAME )");
        return SQLSERVICE.getList(ALIAS, query.toString());
    }
    /*------- Edited by Divyang Kankotiya -------- */

    public List getTestCaseList(String testCaseID) throws ClassNotFoundException, SQLException
    {
        StringBuilder query = new StringBuilder();
        query.append("SELECT TM.TEST_CASE_ID, TM.TEST_CASE FROM WFMS.TEST_CASE_MST TM");
        query.append(" WHERE TM.TEST_CASE_ID IN (");
        query.append(testCaseID);
        query.append(")");
        return SQLSERVICE.getList(ALIAS, query.toString());
    }
    /*-------End Edited by Divyang Kankotiya -------- */

    public List getModuleList(String projectID, String moduleID) throws ClassNotFoundException, SQLException
    {
        StringBuilder query = new StringBuilder();
        query.append("  SELECT MM.MODULE_ID, MM.MODULE_NAME,MM.PROJECT_ID FROM WFMS.MODULE_MST MM");
        query.append(" WHERE MM.PROJECT_ID IN ( ");
        query.append(projectID);
        query.append(" ) ");
        if (!moduleID.equals(""))
        {
            query.append("  AND MM.MODULE_ID IN(");
            query.append(moduleID);
            query.append(")");
        }
        return SQLSERVICE.getList(FINSTUDIO_ALIAS, query.toString());
    }

    private String encodeSingleQuote(final String value)
    {
        String temp = value;

        if (value.indexOf('\'') > -1)
        {
            temp = temp.replaceAll("'", "''");
        }
        return temp;
    }
}
