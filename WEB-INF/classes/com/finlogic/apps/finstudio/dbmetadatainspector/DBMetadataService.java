/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.apps.finstudio.dbmetadatainspector;

import com.finlogic.business.finstudio.DBDependencyDataManager;
import com.finlogic.business.finstudio.dbmetadatainspector.DBMetadataEntityBean;
import com.finlogic.business.finstudio.dbmetadatainspector.DBMetadataManager;
import com.finlogic.util.findatareqexecutor.DBObjectInfoBean;
import com.finlogic.util.findatareqexecutor.DependencyInfoBean;
import com.finlogic.util.findatareqexecutor.QueryInfoBean;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.support.rowset.SqlRowSet;

/**
 *
 * @author Nehal
 */
public class DBMetadataService
{

    private DBMetadataManager manager = new DBMetadataManager();
    private final String CACHE_LOCATION = finpack.FinPack.getProperty("filebox_path");            

    private DBMetadataEntityBean convertFormBeanToEntityBean(DBMetadataFormBean formBean)
    {
        DBMetadataEntityBean entityBean = new DBMetadataEntityBean();
        entityBean.setTxtDB(formBean.getTxtDB());
        entityBean.setCmbDB(formBean.getCmbDB());
        entityBean.setCmbObjType(formBean.getCmbObjType());
        entityBean.setTxtObjName(formBean.getTxtObjName());
        entityBean.setCmbObjName(formBean.getCmbObjName());
        entityBean.setHdnObjType(formBean.getHdnObjType());
        return entityBean;
    }

    public List getDBNames(final DBMetadataFormBean formbean) throws SQLException, ClassNotFoundException
    {
        DBMetadataEntityBean entityBean = convertFormBeanToEntityBean(formbean);
        return manager.getDBNames(entityBean);
    }

    public String getDBType(String dbId) throws ClassNotFoundException, SQLException
    {
        return manager.getDBType(dbId);
    }

    public List getObjName(final DBMetadataFormBean formbean) throws ClassNotFoundException, SQLException
    {
        DBMetadataEntityBean entityBean = convertFormBeanToEntityBean(formbean);
        return manager.getObjName(entityBean);
    }

    public Map<String, List> getDBDefinition(DBMetadataFormBean formBean) throws SQLException, ClassNotFoundException
    {
        DBMetadataEntityBean entityBean = convertFormBeanToEntityBean(formBean);
        return manager.getDBDefinition(entityBean);
    }
//to get table dependencies

    public Map<String, List<DependencyInfoBean>> getTableDependencies(DBMetadataFormBean formBean) throws SQLException, ClassNotFoundException, IOException
    {
        Map<String, List<DependencyInfoBean>> depMap = new HashMap();
        DBObjectInfoBean dbInfoBean = new DBObjectInfoBean();
        dbInfoBean.setObjName(formBean.getCmbObjName()[0]);
        dbInfoBean.setObjType("TABLE");
        dbInfoBean.setSchema(manager.getDataBaseName(formBean.getCmbDB()));
        //if caching write data to file        
        File f = new File(CACHE_LOCATION + formBean.getCmbDB() + "_" + dbInfoBean.getObjName() + ".dre");
        if (formBean.isChkCaching())
        {
            if (f.exists())
            {
                depMap = getCachingData(CACHE_LOCATION + formBean.getCmbDB() + "_" + dbInfoBean.getObjName() + ".dre");
                return depMap;
            }
        }
        String dbType = manager.getDBType(formBean.getCmbDB());
        formBean.setDbType(dbType);
        //get All Servers
        SqlRowSet serverRec = manager.getAllServers();
        List allServers = new ArrayList();
        if (serverRec != null)
        {
            while (serverRec.next())
            {
                allServers.add(serverRec.getString("SERVER_ID") + ":" + serverRec.getString("SERVER_NAME"));
            }
        }
        //chek for all dependencies
        DBDependencyDataManager depMgr = new DBDependencyDataManager();
        List<DependencyInfoBean> depBeanList = null;

        if ("ORACLE".equals(dbType))
        {
            depBeanList = depMgr.checkOracleDep(allServers, dbInfoBean);
            if (depBeanList != null && depBeanList.size() > 0)
            {
                depMap.put("oraDep", depBeanList);
            }
            else
            {
                depMap.put("oraDep", new ArrayList<DependencyInfoBean>());
            }

            depBeanList = depMgr.checkOracleLogTableDep(allServers, dbInfoBean);
            if (depBeanList != null && depBeanList.size() > 0)
            {
                depMap.put("oraLogDep", depBeanList);
            }
            else
            {
                depMap.put("oraLogDep", new ArrayList<DependencyInfoBean>());
            }

//            depBeanList = depMgr.checkOracleNjtranETLDep(allServers, dbInfoBean);
//            if (depBeanList != null && depBeanList.size() > 0)
//            {
//                depMap.put("oraEtlDep", depBeanList);
//            }
//            else
//            {
            depMap.put("oraEtlDep", new ArrayList<DependencyInfoBean>());
//            }

            depBeanList = depMgr.checkOracleNjtranIncreDataUploadDep(allServers, dbInfoBean);
            if (depBeanList != null && depBeanList.size() > 0)
            {
                depMap.put("oraDUDep", depBeanList);
            }
            else
            {
                depMap.put("oraDUDep", new ArrayList<DependencyInfoBean>());
            }

            depBeanList = depMgr.checkMysqlSameNmTableDep(allServers, dbInfoBean);
            if (depBeanList != null && depBeanList.size() > 0)
            {
                depMap.put("msqlSameNmTabDep", depBeanList);
            }
            else
            {
                depMap.put("msqlSameNmTabDep", new ArrayList<DependencyInfoBean>());
            }
        }
        else
        {
            depBeanList = depMgr.checkMySQLLogTableDep(allServers, dbInfoBean, dbInfoBean.getSchema());
            if (depBeanList != null && depBeanList.size() > 0)
            {
                depMap.put("msqlLogDep", depBeanList);
            }
            else
            {
                depMap.put("msqlLogDep", new ArrayList<DependencyInfoBean>());
            }

            depBeanList = depMgr.checkMySQLViewDep(allServers, dbInfoBean);
            if (depBeanList != null && depBeanList.size() > 0)
            {
                depMap.put("msqlViewDep", depBeanList);
            }
            else
            {
                depMap.put("msqlViewDep", new ArrayList<DependencyInfoBean>());
            }

//            depBeanList = depMgr.checkMySQLInfrobrightServer(allServers, dbInfoBean, dbInfoBean.getSchema());
//            if(depBeanList!=null && depBeanList.size()>0)
//            {
//                depMap.put("msqlInfoBrightDep", depBeanList);
//            }
//            else
//            {
            depMap.put("msqlInfoBrightDep", new ArrayList<DependencyInfoBean>());
//            }

            depBeanList = depMgr.checkOracleMViewDep(allServers, dbInfoBean);
            if (depBeanList != null && depBeanList.size() > 0)
            {
                depMap.put("MViewDep", depBeanList);
            }
            else
            {
                depMap.put("MViewDep", new ArrayList<DependencyInfoBean>());
            }
        }

        QueryInfoBean qInfoBean = new QueryInfoBean();
        qInfoBean.setDbObjInfo(dbInfoBean);
        depBeanList = depMgr.isSensitive(formBean.getCmbDB(), qInfoBean);
        if (depBeanList != null && depBeanList.size() > 0)
        {
            depMap.put("sensitiveObj", depBeanList);
        }
        else
        {
            depMap.put("sensitiveObj", new ArrayList<DependencyInfoBean>());
        }

        qInfoBean.setClause("DROP");
        depMgr.checkSensitiveKeyword(formBean.getCmbDB(), qInfoBean, formBean.getDbType(), dbInfoBean.getSchema());
        if (qInfoBean.getDepInfo() != null && qInfoBean.getDepInfo().size() > 0)
        {
            depMap.put("sensitiveKeyword", qInfoBean.getDepInfo());
        }
        else
        {
            depMap.put("sensitiveKeyword", new ArrayList<DependencyInfoBean>());
        }

        f.createNewFile();
        writeCacheData(depMap, f);

        return depMap;
    }

    private void writeCacheData(Map<String, List<DependencyInfoBean>> depBeanMap, File f) throws IOException
    {
        ObjectOutputStream oos = null;
        try
        {
            oos = new ObjectOutputStream(new FileOutputStream(f));
            oos.writeObject(depBeanMap);
        }
        finally
        {
            if (oos != null)
            {
                oos.close();
            }
        }
    }

    public Map<String, List<DependencyInfoBean>> getCachingData(String filePath) throws FileNotFoundException, IOException, ClassNotFoundException
    {
        ObjectInputStream ois = null;
        try
        {
            ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filePath)));
            return (Map<String, List<DependencyInfoBean>>) ois.readObject();
        }
        finally
        {
            if (ois != null)
            {
                ois.close();
            }
        }
    }

    public String getObjDefination(String objType, String[] objName, String dbId, String hdnObjType) throws ClassNotFoundException, SQLException
    {
        return manager.getObjDefination(objType, objName, dbId, hdnObjType);
    }

    public String getTriggerDef(String dbId, String objType, String[] objName) throws ClassNotFoundException, SQLException
    {
        return manager.getTriggerDef(dbId, objType, objName);
    }

    public String getDataBaseName(String dbId) throws ClassNotFoundException, SQLException
    {
        return manager.getDataBaseName(dbId);
    }

    public List getSequenceDef(String dbId, String objType, String[] objName) throws ClassNotFoundException, SQLException
    {
        return manager.getSequenceDef(dbId, objType, objName);
    }
}
