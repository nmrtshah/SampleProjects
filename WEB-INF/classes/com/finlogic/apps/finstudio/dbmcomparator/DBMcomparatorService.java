/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.apps.finstudio.dbmcomparator;

import com.finlogic.business.finstudio.dbmetadatacomparator.DBMcomparatorManager;
import com.finlogic.business.finstudio.dbmetadatacomparator.DBMcomparatorEntityBean;
import com.finlogic.business.finstudio.dbmetadatainspector.DBMetadataManager;
import com.finlogic.util.persistence.SQLUtility;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Jeegar Kumar Patel
 */
public class DBMcomparatorService
{

    private DBMetadataManager manager = new DBMetadataManager();
    private DBMcomparatorManager mngr = new DBMcomparatorManager();
    private final String CACHE_LOCATION = finpack.FinPack.getProperty("filebox_path");

    private DBMcomparatorEntityBean convertFormBeanToEntityBean(DBMcomparatorFormBean formBean)
    {
        DBMcomparatorEntityBean entityBean = new DBMcomparatorEntityBean();
        entityBean.setTxtDB(formBean.getTxtDB());
        entityBean.setCmbDB(formBean.getCmbDB());
        entityBean.setCmbObjType(formBean.getCmbObjType());
        entityBean.setTxtObjName(formBean.getTxtObjName());
        entityBean.setCmbObjName(formBean.getCmbObjName());
        entityBean.setServer(formBean.getServer());
        return entityBean;
    }

    public List getDBNames(final DBMcomparatorFormBean formbean) throws SQLException, ClassNotFoundException
    {
        DBMcomparatorEntityBean entityBean = convertFormBeanToEntityBean(formbean);
        return mngr.getDBNames(entityBean);
    }

    public Set getObjName(final DBMcomparatorFormBean formbean, String server) throws ClassNotFoundException, SQLException
    {
        DBMcomparatorEntityBean entityBean = convertFormBeanToEntityBean(formbean);
        return mngr.getObjName(entityBean, server);
    }

    public String getDBType(String dbId) throws ClassNotFoundException, SQLException
    {
        return manager.getDBType(dbId);
    }

    public Map<String, List> getDBDefinition(DBMcomparatorFormBean formBean, String serv) throws SQLException, ClassNotFoundException
    {
        DBMcomparatorEntityBean entityBean = convertFormBeanToEntityBean(formBean);
        return mngr.getDBDefinition(entityBean, serv);
    }

    Map<String, List> getTableDiff(Map<String, List> res1, Map<String, List> res2)
    {
        Map<String, List> finalList = new HashMap<String, List>();

///////////////////////////////////////Column List/////////////////////////////////////////////////////////
        List<Map> colList = new LinkedList<Map>();

        int colSize = 0;
        int type = 0;
        int maxSize = 0;

        if (res1.get("Columns").size() >= res2.get("Columns").size())
        {
            colSize = res2.get("Columns").size();
            type = 2;
            maxSize = res1.get("Columns").size();
        }
        else
        {
            colSize = res1.get("Columns").size();
            type = 1;
            maxSize = res2.get("Columns").size();
        }

        for (int i = 0; i < colSize; i++)
        {
            Map m1 = null;
            Map m2 = null;

            m1 = (Map) res1.get("Columns").get(i);
            m2 = (Map) res2.get("Columns").get(i);

            Map m3 = new HashMap();

            m3.put("COLUMN_NAME1", m1.get("COLUMN_NAME"));
            m3.put("DATA_TYPE1", m1.get("DATA_TYPE"));
            //m3.put("DATA_LENGTH1", m1.get("DATA_LENGTH"));
            if (m1.get("DATA_LENGTH") != null && m1.get("DATA_LENGTH") != "")
            {
                m3.put("DATA_LENGTH1", m1.get("DATA_LENGTH"));
            }
            else
            {
                m3.put("DATA_LENGTH1", "-");
            }
            m3.put("NULLABLE1", m1.get("NULLABLE"));
            if (m1.get("DATA_DEFAULT") != null && m1.get("DATA_DEFAULT") != "")
            {
                m3.put("DATA_DEFAULT1", m1.get("DATA_DEFAULT"));
            }
            else
            {
                m3.put("DATA_DEFAULT1", "-");
            }
            m3.put("COLUMN_NAME2", m2.get("COLUMN_NAME"));
            m3.put("DATA_TYPE2", m2.get("DATA_TYPE"));
            if (m2.get("DATA_LENGTH") != null && m2.get("DATA_LENGTH") != "")
            {
                m3.put("DATA_LENGTH2", m2.get("DATA_LENGTH"));
            }
            else
            {
                m3.put("DATA_LENGTH2", "-");
            }
            m3.put("NULLABLE2", m2.get("NULLABLE"));
            if (m2.get("DATA_DEFAULT") != null && m2.get("DATA_DEFAULT") != "")
            {
                m3.put("DATA_DEFAULT2", m2.get("DATA_DEFAULT"));
            }
            else
            {
                m3.put("DATA_DEFAULT2", "-");
            }

            if (!res1.get("Columns").get(i).equals(res2.get("Columns").get(i)))
            {
                m3.put("FLAG", 1);
            }
            else
            {
                m3.put("FLAG", 0);
            }
            colList.add(m3);
        }

        if (type == 2)
        {
            for (int j = colSize; j < maxSize; j++)
            {
                Map m1 = null;

                m1 = (Map) res1.get("Columns").get(j);

                Map m3 = new HashMap();

                m3.put("COLUMN_NAME1", m1.get("COLUMN_NAME"));
                m3.put("DATA_TYPE1", m1.get("DATA_TYPE"));
                //m3.put("DATA_LENGTH1", m1.get("DATA_LENGTH"));
                if (m1.get("DATA_LENGTH") != null && m1.get("DATA_LENGTH") != "")
                {
                    m3.put("DATA_LENGTH1", m1.get("DATA_LENGTH"));
                }
                else
                {
                    m3.put("DATA_LENGTH1", "-");
                }
                m3.put("NULLABLE1", m1.get("NULLABLE"));
                if (m1.get("DATA_DEFAULT") != null && m1.get("DATA_DEFAULT") != "")
                {
                    m3.put("DATA_DEFAULT1", m1.get("DATA_DEFAULT"));
                }
                else
                {
                    m3.put("DATA_DEFAULT1", "-");
                }
                m3.put("COLUMN_NAME2", "-");
                m3.put("DATA_TYPE2", "-");
                m3.put("DATA_LENGTH2", "-");
                m3.put("NULLABLE2", "-");
                m3.put("DATA_DEFAULT2", "-");

                m3.put("FLAG", 1);

                colList.add(m3);
            }
        }
        else if (type == 1)
        {
            for (int j = colSize; j < maxSize; j++)
            {
                Map m2 = null;

                m2 = (Map) res2.get("Columns").get(j);

                Map m3 = new HashMap();
                m3.put("COLUMN_NAME1", "-");
                m3.put("DATA_TYPE1", "-");
                m3.put("DATA_LENGTH1", "-");
                m3.put("NULLABLE1", "-");
                m3.put("DATA_DEFAULT1", "-");

                m3.put("COLUMN_NAME2", m2.get("COLUMN_NAME"));
                m3.put("DATA_TYPE2", m2.get("DATA_TYPE"));
                if (m2.get("DATA_LENGTH") != null && m2.get("DATA_LENGTH") != "")
                {
                    m3.put("DATA_LENGTH2", m2.get("DATA_LENGTH"));
                }
                else
                {
                    m3.put("DATA_LENGTH2", "-");
                }
                m3.put("NULLABLE2", m2.get("NULLABLE"));

                if (m2.get("DATA_DEFAULT") != null && m2.get("DATA_DEFAULT") != "")
                {
                    m3.put("DATA_DEFAULT2", m2.get("DATA_DEFAULT"));
                }
                else
                {
                    m3.put("DATA_DEFAULT2", "-");
                }
                m3.put("FLAG", 1);

                colList.add(m3);
            }
        }

        finalList.put("Columns", colList);
///////////////////////////////////////Column List/////////////////////////////////////////////////////////

///////////////////////////////////////Index List/////////////////////////////////////////////////////////
        List<Map> indexList = new LinkedList<Map>();

        int indexSize = 0;
        type = 0;
        maxSize = 0;

        if (res1.get("Indexes").size() >= res2.get("Indexes").size())
        {
            indexSize = res2.get("Indexes").size();
            type = 2;
            maxSize = res1.get("Indexes").size();
        }
        else
        {
            indexSize = res1.get("Indexes").size();
            type = 1;
            maxSize = res2.get("Indexes").size();
        }

        for (int i = 0; i < indexSize; i++)
        {
            Map m1 = null;
            Map m2 = null;

            m1 = (Map) res1.get("Indexes").get(i);
            m2 = (Map) res2.get("Indexes").get(i);

            Map m3 = new HashMap();

            m3.put("INDEX_NAME1", m1.get("INDEX_NAME"));
            m3.put("COLUMN_NAME1", m1.get("COLUMN_NAME"));

            m3.put("INDEX_NAME2", m2.get("INDEX_NAME"));
            m3.put("COLUMN_NAME2", m2.get("COLUMN_NAME"));

            if (!res1.get("Indexes").get(i).equals(res2.get("Indexes").get(i)))
            {
                m3.put("FLAG", 1);
            }
            else
            {
                m3.put("FLAG", 0);
            }
            indexList.add(m3);
        }

        if (type == 2)
        {
            for (int j = indexSize; j < maxSize; j++)
            {
                Map m1 = null;

                m1 = (Map) res1.get("Indexes").get(j);

                Map m3 = new HashMap();

                m3.put("INDEX_NAME1", m1.get("INDEX_NAME"));
                m3.put("COLUMN_NAME1", m1.get("COLUMN_NAME"));

                m3.put("INDEX_NAME2", "-");
                m3.put("COLUMN_NAME2", "-");

                m3.put("FLAG", 1);

                indexList.add(m3);
            }
        }
        else if (type == 1)
        {
            for (int j = indexSize; j < maxSize; j++)
            {
                Map m2 = null;

                m2 = (Map) res2.get("Indexes").get(j);

                Map m3 = new HashMap();

                m3.put("INDEX_NAME1", "-");
                m3.put("COLUMN_NAME1", "-");

                m3.put("INDEX_NAME2", m2.get("INDEX_NAME"));
                m3.put("COLUMN_NAME2", m2.get("COLUMN_NAME"));

                m3.put("FLAG", 1);

                indexList.add(m3);
            }
        }

        finalList.put("Indexes", indexList);
///////////////////////////////////////Index List/////////////////////////////////////////////////////////

///////////////////////////////////////Constraint List/////////////////////////////////////////////////////////
        List<Map> constList = new LinkedList<Map>();

        int constSize = 0;
        type = 0;
        maxSize = 0;

        if (res1.get("Constraints").size() >= res2.get("Constraints").size())
        {
            constSize = res2.get("Constraints").size();
            type = 2;
            maxSize = res1.get("Constraints").size();
        }
        else
        {
            constSize = res1.get("Constraints").size();
            type = 1;
            maxSize = res2.get("Constraints").size();
        }

        for (int i = 0; i < constSize; i++)
        {
            Map m1 = null;
            Map m2 = null;

            m1 = (Map) res1.get("Constraints").get(i);
            m2 = (Map) res2.get("Constraints").get(i);

            Map m3 = new HashMap();

            m3.put("CONSTRAINT_TYPE1", m1.get("Constraint_Type"));
            m3.put("CONSTRAINT_NAME1", m1.get("Constraint_Name"));
            if (m1.get("Constraint_Detail") != null && m1.get("Constraint_Detail") != "")
            {
                m3.put("CONSTRAINT_DETAIL1", m1.get("Constraint_Detail"));
            }
            else
            {
                m3.put("CONSTRAINT_DETAIL1", "-");
            }
            if (m1.get("Referenced_Table") != null && m1.get("Referenced_Table") != "")
            {
                m3.put("REFERENCED_TABLE1", m1.get("Referenced_Table"));
            }
            else
            {
                m3.put("REFERENCED_TABLE1", "-");
            }
            if (m1.get("Referenced_Column") != null && m1.get("Referenced_Column") != "")
            {
                m3.put("REFERENCED_COLUMN1", m1.get("Referenced_Column"));
            }
            else
            {
                m3.put("REFERENCED_COLUMN1", "-");
            }

            m3.put("CONSTRAINT_TYPE2", m2.get("Constraint_Type"));
            m3.put("CONSTRAINT_NAME2", m2.get("Constraint_Name"));
            if (m2.get("Constraint_Detail") != null && m2.get("Constraint_Detail") != "")
            {
                m3.put("CONSTRAINT_DETAIL2", m2.get("Constraint_Detail"));
            }
            else
            {
                m3.put("CONSTRAINT_DETAIL2", "-");
            }
            if (m2.get("Referenced_Table") != null && m2.get("Referenced_Table") != "")
            {
                m3.put("REFERENCED_TABLE2", m2.get("Referenced_Table"));
            }
            else
            {
                m3.put("REFERENCED_TABLE2", "-");
            }
            if (m2.get("Referenced_Column") != null && m2.get("Referenced_Column") != "")
            {
                m3.put("REFERENCED_COLUMN2", m2.get("Referenced_Column"));
            }
            else
            {
                m3.put("REFERENCED_COLUMN2", "-");
            }

            if (!res1.get("Constraints").get(i).equals(res2.get("Constraints").get(i)))
            {
                m3.put("FLAG", 1);
            }
            else
            {
                m3.put("FLAG", 0);
            }
            constList.add(m3);
        }

        if (type == 2)
        {
            for (int j = constSize; j < maxSize; j++)
            {
                Map m1 = null;

                m1 = (Map) res1.get("Constraints").get(j);

                Map m3 = new HashMap();

                m3.put("CONSTRAINT_TYPE1", m1.get("Constraint_Type"));
                m3.put("CONSTRAINT_NAME1", m1.get("Constraint_Name"));
                if (m1.get("Constraint_Detail") != null && m1.get("Constraint_Detail") != "")
                {
                    m3.put("CONSTRAINT_DETAIL1", m1.get("Constraint_Detail"));
                }
                else
                {
                    m3.put("CONSTRAINT_DETAIL1", "-");
                }
                if (m1.get("Referenced_Table") != null && m1.get("Referenced_Table") != "")
                {
                    m3.put("REFERENCED_TABLE1", m1.get("Referenced_Table"));
                }
                else
                {
                    m3.put("REFERENCED_TABLE1", "-");
                }
                if (m1.get("Referenced_Column") != null && m1.get("Referenced_Column") != "")
                {
                    m3.put("REFERENCED_COLUMN1", m1.get("Referenced_Column"));
                }
                else
                {
                    m3.put("REFERENCED_COLUMN1", "-");
                }

                m3.put("CONSTRAINT_TYPE2", "-");
                m3.put("CONSTRAINT_NAME2", "-");
                m3.put("CONSTRAINT_DETAIL2", "-");
                m3.put("REFERENCED_TABLE2", "-");
                m3.put("REFERENCED_COLUMN2", "-");

                m3.put("FLAG", 1);

                constList.add(m3);
            }
        }
        else if (type == 1)
        {
            for (int j = constSize; j < maxSize; j++)
            {
                Map m2 = null;

                m2 = (Map) res2.get("Constraints").get(j);

                Map m3 = new HashMap();

                m3.put("CONSTRAINT_TYPE1", "-");
                m3.put("CONSTRAINT_NAME1", "-");
                m3.put("CONSTRAINT_DETAIL1", "-");
                m3.put("REFERENCED_TABLE1", "-");
                m3.put("REFERENCED_COLUMN1", "-");

                m3.put("CONSTRAINT_TYPE2", m2.get("Constraint_Type"));
                m3.put("CONSTRAINT_NAME2", m2.get("Constraint_Name"));
                if (m2.get("Constraint_Detail") != null && m2.get("Constraint_Detail") != "")
                {
                    m3.put("CONSTRAINT_DETAIL2", m2.get("Constraint_Detail"));
                }
                else
                {
                    m3.put("CONSTRAINT_DETAIL2", "-");
                }
                if (m2.get("Referenced_Table") != null && m2.get("Referenced_Table") != "")
                {
                    m3.put("REFERENCED_TABLE2", m2.get("Referenced_Table"));
                }
                else
                {
                    m3.put("REFERENCED_TABLE2", "-");
                }
                if (m2.get("Referenced_Column") != null && m2.get("Referenced_Column") != "")
                {
                    m3.put("REFERENCED_COLUMN2", m2.get("Referenced_Column"));
                }
                else
                {
                    m3.put("REFERENCED_COLUMN2", "-");
                }

                m3.put("FLAG", 1);

                constList.add(m3);
            }
        }

        finalList.put("Constraints", constList);

///////////////////////////////////////Constraint List/////////////////////////////////////////////////////////
///////////////////////////////////////Trigger List/////////////////////////////////////////////////////////
        List<Map> triggerList = new LinkedList<Map>();

        int triggerSize = 0;
        type = 0;
        maxSize = 0;

        if (res1.get("Triggers").size() >= res2.get("Triggers").size())
        {
            triggerSize = res2.get("Triggers").size();
            type = 2;
            maxSize = res1.get("Triggers").size();
        }
        else
        {
            triggerSize = res1.get("Triggers").size();
            type = 1;
            maxSize = res2.get("Triggers").size();
        }

        for (int i = 0; i < triggerSize; i++)
        {

            Map m3 = new HashMap();

            m3.put("TRIGGER1", res1.get("Triggers").get(i));
            m3.put("TRIGGER2", res2.get("Triggers").get(i));

            if (!res1.get("Triggers").get(i).equals(res2.get("Triggers").get(i)))
            {
                m3.put("FLAG", 1);
            }
            else
            {
                m3.put("FLAG", 0);
            }
            triggerList.add(m3);
        }

        if (type == 2)
        {
            for (int j = triggerSize; j < maxSize; j++)
            {

                Map m3 = new HashMap();

                m3.put("TRIGGER1", res1.get("Triggers").get(j));
                m3.put("TRIGGER2", "-");
                m3.put("FLAG", 1);

                triggerList.add(m3);
            }
        }
        else if (type == 1)
        {
            for (int j = triggerSize; j < maxSize; j++)
            {

                Map m3 = new HashMap();

                m3.put("TRIGGER1", "-");
                m3.put("TRIGGER2", res2.get("Triggers").get(j));
                m3.put("FLAG", 1);

                triggerList.add(m3);
            }
        }

        finalList.put("Triggers", triggerList);

///////////////////////////////////////Trigger List/////////////////////////////////////////////////////////
        return finalList;
    }

    Map<String, List> getColumnDiff(Map<String, List> prefinallist)
    {
        Map<String, List> finalList = new HashMap<String, List>();

///////////////////////////////////////Column List/////////////////////////////////////////////////////////
        int colSize = prefinallist.get("Columns").size();

        for (int i = 0; i < colSize; i++)
        {
            Map m1 = null;

            m1 = (Map) (prefinallist.get("Columns").get(i));

            if (m1.get("FLAG").toString().equals("1"))
            {
                if (!m1.get("COLUMN_NAME1").equals(m1.get("COLUMN_NAME2")))
                {
                    m1.put("COLUMN_NAME_FLAG", 1);
                }
                else
                {
                    m1.put("COLUMN_NAME_FLAG", 0);
                }

                if (!m1.get("DATA_TYPE1").equals(m1.get("DATA_TYPE2")))
                {
                    m1.put("DATA_TYPE_FLAG", 1);
                }
                else
                {
                    m1.put("DATA_TYPE_FLAG", 0);
                }

                if (!m1.get("DATA_LENGTH1").equals(m1.get("DATA_LENGTH2")))
                {
                    m1.put("DATA_LENGTH_FLAG", 1);
                }
                else
                {
                    m1.put("DATA_LENGTH_FLAG", 0);
                }

                if (!m1.get("NULLABLE1").equals(m1.get("NULLABLE2")))
                {
                    m1.put("NULLABLE_FLAG", 1);
                }
                else
                {
                    m1.put("NULLABLE_FLAG", 0);
                }

                if (!m1.get("DATA_DEFAULT1").equals(m1.get("DATA_DEFAULT2")))
                {
                    m1.put("DATA_DEFAULT_FLAG", 1);
                }
                else
                {
                    m1.put("DATA_DEFAULT_FLAG", 0);
                }
            }
            else
            {
                m1.put("COLUMN_NAME_FLAG", 0);
                m1.put("DATA_TYPE_FLAG", 0);
                m1.put("DATA_LENGTH_FLAG", 0);
                m1.put("NULLABLE_FLAG", 0);
                m1.put("DATA_DEFAULT_FLAG", 0);
            }
        }

///////////////////////////////////////Column List/////////////////////////////////////////////////////////
///////////////////////////////////////Index List/////////////////////////////////////////////////////////
        int indexSize = prefinallist.get("Indexes").size();

        for (int i = 0; i < indexSize; i++)
        {

            Map m1 = null;

            m1 = (Map) (prefinallist.get("Indexes").get(i));

            if (m1.get("FLAG").toString().equals("1"))
            {
                if (!m1.get("COLUMN_NAME1").equals(m1.get("COLUMN_NAME2")))
                {
                    m1.put("COLUMN_NAME_FLAG", 1);
                }
                else
                {
                    m1.put("COLUMN_NAME_FLAG", 0);
                }

                if (!m1.get("INDEX_NAME1").equals(m1.get("INDEX_NAME2")))
                {
                    m1.put("INDEX_NAME_FLAG", 1);
                }
                else
                {
                    m1.put("INDEX_NAME_FLAG", 0);
                }
            }
            else
            {
                m1.put("COLUMN_NAME_FLAG", 0);
                m1.put("INDEX_NAME_FLAG", 0);
            }
        }

///////////////////////////////////////Index List/////////////////////////////////////////////////////////
///////////////////////////////////////Constraint List/////////////////////////////////////////////////////////
        int constSize = prefinallist.get("Constraints").size();

        for (int i = 0; i < constSize; i++)
        {

            Map m1 = null;
            m1 = (Map) (prefinallist.get("Constraints").get(i));

            if (m1.get("FLAG").toString().equals("1"))
            {
                if (!m1.get("CONSTRAINT_TYPE1").equals(m1.get("CONSTRAINT_TYPE2")))
                {
                    m1.put("CONSTRAINT_TYPE_FLAG", 1);
                }
                else
                {
                    m1.put("CONSTRAINT_TYPE_FLAG", 0);
                }

                if (!m1.get("CONSTRAINT_NAME1").equals(m1.get("CONSTRAINT_NAME2")))
                {
                    m1.put("CONSTRAINT_NAME_FLAG", 1);
                }
                else
                {
                    m1.put("CONSTRAINT_NAME_FLAG", 0);
                }

                if (!m1.get("CONSTRAINT_DETAIL1").equals(m1.get("CONSTRAINT_DETAIL2")))
                {
                    m1.put("CONSTRAINT_DETAIL_FLAG", 1);
                }
                else
                {
                    m1.put("CONSTRAINT_DETAIL_FLAG", 0);
                }

                if (!m1.get("REFERENCED_TABLE1").equals(m1.get("REFERENCED_TABLE2")))
                {
                    m1.put("REFERENCED_TABLE_FLAG", 1);
                }
                else
                {
                    m1.put("REFERENCED_TABLE_FLAG", 0);
                }

                if (!m1.get("REFERENCED_COLUMN1").equals(m1.get("REFERENCED_COLUMN2")))
                {
                    m1.put("REFERENCED_COLUMN_FLAG", 1);
                }
                else
                {
                    m1.put("REFERENCED_COLUMN_FLAG", 0);
                }
            }
            else
            {
                m1.put("CONSTRAINT_TYPE_FLAG", 0);
                m1.put("CONSTRAINT_NAME_FLAG", 0);
                m1.put("CONSTRAINT_DETAIL_FLAG", 0);
                m1.put("REFERENCED_TABLE_FLAG", 0);
                m1.put("REFERENCED_COLUMN_FLAG", 0);
            }
        }

///////////////////////////////////////Constraint List/////////////////////////////////////////////////////////
///////////////////////////////////////Triggers List/////////////////////////////////////////////////////////
        int trigeerSize = prefinallist.get("Triggers").size();

        for (int i = 0; i < trigeerSize; i++)
        {

            Map m1 = null;
            m1 = (Map) prefinallist.get("Triggers").get(i);

            if (m1.get("FLAG").toString().equals("1"))
            {

                m1.put("TRIGGER_FLAG", 1);
            }
            else
            {
                m1.put("TRIGGER_FLAG", 0);
            }

        }

///////////////////////////////////////Triggers List/////////////////////////////////////////////////////////
        return prefinallist;
    }

    public Map<String, List> getObjDefination(String objType, String objName, String dbId, String hdnObjType, String server, DBMcomparatorFormBean formBean) throws ClassNotFoundException, SQLException
    {
        DBMcomparatorEntityBean entityBean = convertFormBeanToEntityBean(formBean);
        return mngr.getObjDefination(objType, objName, dbId, hdnObjType, server, entityBean);
    }

    public String getProjectList()
            throws Exception
    {
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT PRJ_ID,PRJ_NAME FROM WFM.PROJECT_MST");

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> data = new SQLUtility().getList("wfm", sql.toString());
        StringBuilder arraydata = new StringBuilder();
        arraydata.append("[");
        Iterator<Map<String, Object>> ite = data.iterator();
        Map<String, Object> map;
        while (ite.hasNext())
        {
            map = ite.next();
            arraydata.append("{\"id\":\"").append(map.get("PRJ_ID").toString())
                    .append("\",\"value\":\"").append(map.get("PRJ_NAME").toString())
                    .append("\"}");
            if (ite.hasNext())
            {
                arraydata.append(",");
            }
        }
        arraydata.append("]");

        return arraydata.toString();
    }

    public String getModuleList(String projectid)
            throws Exception
    {
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT MODULE_ID , MODULE_NAME FROM WFM.MODULE_MST WHERE PROJECT_ID = ").append(projectid);

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> data = new SQLUtility().getList("wfm", sql.toString());
        StringBuilder arraydata = new StringBuilder();
        arraydata.append("[");
        Iterator<Map<String, Object>> ite = data.iterator();
        Map<String, Object> map;
        while (ite.hasNext())
        {
            map = ite.next();
            arraydata.append("{\"id\":\"").append(map.get("MODULE_ID").toString())
                    .append("\",\"value\":\"").append(map.get("MODULE_NAME").toString())
                    .append("\"}");
            if (ite.hasNext())
            {
                arraydata.append(",");
            }
        }
        arraydata.append("]");

        return arraydata.toString();
    }

    public String getReportData()
            throws Exception
    {
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT SRNO, GROUP_NAME, STATS_STRING, DATE_FORMAT(ON_DATE,'%d-%m-%Y') ON_DATE FROM FINSTUDIO_STATISTICS WHERE IS_ACTIVE = 'Y' limit 50");

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> data = new SQLUtility().getList("finstudio_mysql", sql.toString());
        StringBuilder arraydata = new StringBuilder();
        arraydata.append("[");
        Iterator<Map<String, Object>> ite = data.iterator();
        Map<String, Object> map;
        while (ite.hasNext())
        {
            map = ite.next();
            arraydata.append("{\"srno\":\"").append(map.get("SRNO").toString())
                    .append("\",\"group_name\":\"").append(map.get("GROUP_NAME").toString())
                    .append("\",\"stats_string\":\"").append(map.get("STATS_STRING").toString())
                    .append("\",\"on_date\":\"").append(map.get("ON_DATE").toString())
                    .append("\"}");
            if (ite.hasNext())
            {
                arraydata.append(",");
            }
        }

        arraydata.append("]");

        return arraydata.toString();
    }

    public String getBigReportData()
            throws Exception
    {
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT SM.ALLOCATION_ID , EM.EMP_NAME , SM.TITLE , TTM.TASK_TYPE_NAME , ")
                .append(" PM.PRJ_NAME , MM.MODULE_NAME , STM.STATUS , ")
                .append(" date_format(SM.START_DATE,'%d-%m-%Y %h:%i %p') START_DATE , ")
                .append(" date_format(SM.END_DATE,'%d-%m-%Y %h:%i %p') END_DATE ")
                .append(" FROM WFM.SCHEDULE_MST SM ")
                .append(" INNER JOIN WFM.TASK_TYPE_MST TTM ON SM.TASK_TYPE_ID = TTM.TASK_TYPE_ID ")
                .append(" INNER JOIN WFM.EMP_MST EM ON SM.RESOURCE_MECODE = EM.EMP_CODE ")
                .append(" INNER JOIN WFM.STATUS_MST STM ON SM.STATUS_ID = STM.STATUS_ID ")
                .append(" INNER JOIN WFM.PROJECT_MST PM ON SM.PRJ_ID = PM.PRJ_ID ")
                .append(" INNER JOIN WFM.MODULE_MST MM ON SM.MODULE_ID = MM.MODULE_ID ")
                .append(" WHERE SM.RESOURCE_MECODE IS NOT NULL AND SM.START_DATE IS NOT NULL ")
                .append(" AND SM.END_DATE IS NOT NULL AND STM.STATUS IN ( 'ALLOCATED' , 'ON HALT' ) ")
                .append(" ORDER BY SM.ALLOCATION_ID , PM.PRJ_NAME , MM.MODULE_NAME ")
                .append(" LIMIT 250 ");

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> data = new SQLUtility().getList("wfm", sql.toString());
        StringBuilder arraydata = new StringBuilder();
        arraydata.append("[");
        Iterator<Map<String, Object>> ite = data.iterator();
        Map<String, Object> map;
        while (ite.hasNext())
        {
            map = ite.next();

            arraydata.append("{\"allocation_id\":\"").append(map.get("ALLOCATION_ID").toString())
                    .append("\",\"title\":\"").append(map.get("TITLE").toString())
                    .append("\",\"emp_name\":\"").append(map.get("EMP_NAME").toString())
                    .append("\",\"task_type\":\"").append(map.get("TASK_TYPE_NAME").toString())
                    .append("\",\"prj_name\":\"").append(map.get("PRJ_NAME").toString())
                    .append("\",\"module_name\":\"").append(map.get("MODULE_NAME").toString())
                    .append("\",\"status\":\"").append(map.get("STATUS").toString())
                    .append("\",\"start_date\":\"").append(map.get("START_DATE").toString())
                    .append("\",\"end_date\":\"").append(map.get("END_DATE").toString())
                    .append("\"}");

            if (ite.hasNext())
            {
                arraydata.append(",");
            }
        }

        arraydata.append("]");

        return arraydata.toString();
    }
}