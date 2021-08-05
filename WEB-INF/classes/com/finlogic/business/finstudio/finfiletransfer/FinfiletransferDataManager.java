package com.finlogic.business.finstudio.finfiletransfer;

import com.finlogic.util.Logger;
import com.finlogic.util.persistence.SQLUtility;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class FinfiletransferDataManager {

    private static final String ALIASNAME = "finstudio_mysql";
    private static final String WFMALIASNAME = "wfm";
    private final SQLUtility sqlUtility = new SQLUtility();
    private static final String FILE_PATH = finpack.FinPack.getProperty("tomcat1_path") + "/webapps/";

    public List getDataMaster(final FinfiletransferEntityBean entityBean) throws ClassNotFoundException, SQLException {
        SqlParameterSource sps = new BeanPropertySqlParameterSource(entityBean);
        StringBuilder query = new StringBuilder();
        query.append("SELECT MASTER_PK, EMP_NAME, SRC_DEST, PROJECT PROJECT_ID, REQNO, PURPOSE, ENTDATE, EXECUTE_STATUS, GEN_ID, LANG FROM FILE_TRANSFER_MASTER M ");
        query.append("INNER JOIN NJHR.EMP_MAST E ON E.EMP_CODE = M.EMPCODE ");
        query.append("INNER JOIN ");
        query.append("( ");
        query.append("SELECT DISTINCT SUBSTRING(DOMAIN_NAME1,1,INSTR(DOMAIN_NAME1,'/')-1) DOMAIN_NAME1,MASTER_PK1 FROM ");
        query.append("( ");
        query.append("SELECT REPLACE(REPLACE(FILE_NAME,'Deleted:',''),'Uploaded:','') DOMAIN_NAME1,MASTER_PK MASTER_PK1 FROM FILE_TRANSFER_DETAIL ");
        query.append(") X1 ");
        query.append(") X2 ON M.MASTER_PK = X2.MASTER_PK1 ");
        query.append("WHERE 1 = 1 ");
        if (entityBean.getTxtReqId() != null && !"".equals(entityBean.getTxtReqId())) {
            if (entityBean.getTxtReqId().contains(",")) {
                query.append(" AND M.MASTER_PK IN (").append(entityBean.getTxtReqId()).append(")");
            } else {
                query.append(" AND M.MASTER_PK = :txtReqId");
            }

        } else {
            if (entityBean.getMasterpk() != null && !entityBean.getMasterpk().equals("")) {
                query.append(" AND M.MASTER_PK = :masterpk");
            }
            if (entityBean.getEmpcode() != null) {
                query.append(" AND M.EMPCODE = :empcode");
            }
            if (entityBean.getProject() != null) {
                query.append(" AND PROJECT = :project");
            }
            if (entityBean.getViewreqno() != null && !entityBean.getViewreqno().equals("")) {
                query.append(" AND REQNO = :viewreqno");
            }
            if (entityBean.getViewpurpose() != null && !entityBean.getViewpurpose().equals("")) {
                query.append(" AND PURPOSE LIKE '%").append(entityBean.getViewpurpose()).append("%'");
            }
            if (entityBean.getFromdate() != null && !entityBean.getFromdate().equals("") && entityBean.getTodate() != null && !entityBean.getTodate().equals("")) {
                query.append(" AND STR_TO_DATE(DATE_FORMAT(ENTDATE,'%d-%m-%Y'),'%d-%m-%Y') BETWEEN STR_TO_DATE(:fromdate,'%d-%m-%Y') AND STR_TO_DATE(:todate,'%d-%m-%Y') ");
            }
            if (entityBean.getSrcdestDB() != null) {
                query.append(" AND SRC_DEST = :srcdestDB");
            }
        }
        query.append(" ORDER BY MASTER_PK DESC ");
        SqlRowSet srs = sqlUtility.getRowSet(ALIASNAME, query.toString(), sps);

        query.setLength(0);
        query.append("SELECT PRJ_ID, CONCAT(PRJ_NAME,' - ',DOMAIN_NAME) PROJECT FROM PROJECT_MST");
        if (entityBean.getProject() != null) {
            query.append(" WHERE PRJ_ID = :project ");
        }
        SqlRowSet psrs = sqlUtility.getRowSet(WFMALIASNAME, query.toString(), sps);

        List res = null;
        if (srs != null && psrs != null) {
            Map m = null;
            res = new ArrayList();
            while (srs.next()) {
                psrs.beforeFirst();
                while (psrs.next()) {
                    if (psrs.getString("PRJ_ID").equalsIgnoreCase(srs.getString("PROJECT_ID"))) {
                        m = new LinkedHashMap();
                        m.put("MASTER_PK", srs.getString("MASTER_PK"));
                        m.put("EMP_NAME", srs.getString("EMP_NAME"));
                        m.put("SRC_DEST", srs.getString("SRC_DEST"));
                        m.put("PROJECT", psrs.getString("PROJECT"));
                        m.put("REQNO", srs.getString("REQNO"));
                        m.put("PURPOSE", srs.getString("PURPOSE"));
                        m.put("ENTDATE", srs.getString("ENTDATE"));
                        m.put("EXECUTE_STATUS", srs.getString("EXECUTE_STATUS"));
                        m.put("GEN_ID", srs.getString("GEN_ID"));
                        m.put("LANG", srs.getString("LANG"));
                        res.add(m);
                    }
                }
            }
        }
        return res;
    }

    public synchronized int insertMaster(final FinfiletransferEntityBean entityBean) throws ClassNotFoundException, SQLException {
        int j = 0;
        try {
            SqlParameterSource sps = new BeanPropertySqlParameterSource(entityBean);
            StringBuilder query = new StringBuilder();
            String genID = entityBean.getHdnGenId();
            long genId;
            if (genID.contains(",")) {
                genId = Long.parseLong(genID.substring(0, genID.indexOf(',')));
            } else {
                genId = Long.parseLong(genID);
            }

            query.append("INSERT INTO FILE_TRANSFER_MASTER (EMPCODE, PROJECT, REQNO, SRC_DEST, PURPOSE, EXECUTE_STATUS, ENTDATE, GEN_ID, LANG) VALUES (");
            query.append(":empcode, :project, :reqnoDB, :srcdestDB, :purposeDB, 'N', SYSDATE(), ").append(genId).append(", :lang)");

            j = sqlUtility.persist(ALIASNAME, query.toString(), sps);

            String keyQuery = "SELECT MAX(MASTER_PK) FROM FILE_TRANSFER_MASTER";
            int key = sqlUtility.getInt(ALIASNAME, keyQuery);

            FileTransIntermediateDataManager dataManager = new FileTransIntermediateDataManager();
            List list = dataManager.getData(genId);

            Map m;

            if (list != null && !list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    m = (Map) list.get(i);
                    StringBuilder query1 = new StringBuilder();
                    query1.append("INSERT INTO FILE_TRANSFER_DETAIL(MASTER_PK, FILE_NAME, SRC_SERVER_EXIST, DEST_SERVER_EXIST, FILE_TYPE) ");
                    query1.append("VALUES(").append(key).append(",\"").append(m.get("FILE_NAME").toString()).append("\",\"").append(m.get("SRC_EXIST").toString()).append("\",\"").append(m.get("DEST_EXIST").toString()).append("\" ,\"").append(m.get("FILE_TYPE").toString()).append("\" )");
                    sqlUtility.persist(ALIASNAME, query1.toString());
                }
            }
            List listDtl = dataManager.getDataDlt(genId);

            Map mDtl;
            Map map;
            if (listDtl != null && !listDtl.isEmpty()) {
                for (int i = 0; i < listDtl.size(); i++) {
                    mDtl = (Map) listDtl.get(i);
                    String fileNameArry[] = mDtl.get("FILE_NAME").toString().split(":");
                    String filePath = fileNameArry[1].toString();
                    List retList = new ArrayList();
                    List fileNameList = getFileName(filePath, retList);
                    if (fileNameList != null && !fileNameList.isEmpty()) {
                        for (int k = 0; k < fileNameList.size(); k++) {
                            map = (Map) fileNameList.get(k);
                            StringBuilder query1 = new StringBuilder();
                            query1.append("INSERT INTO FILE_TRANSFER_DETAIL(MASTER_PK, FILE_NAME, SRC_SERVER_EXIST, DEST_SERVER_EXIST, FILE_TYPE) ");
                            query1.append("VALUES(").append(key).append(",\"").append(map.get("FILE_NAME").toString()).append("\",\"").append(mDtl.get("SRC_EXIST").toString()).append("\",\"").append(mDtl.get("DEST_EXIST").toString()).append("\" ,\"").append(map.get("FILE_TYPE").toString()).append("\" )");

//                            query1.append("VALUES(").append(key).append(",\"").append(mDtl.get("FILE_NAME").toString()).append("\",\"").append(mDtl.get("SRC_EXIST").toString()).append("\",\"").append(mDtl.get("DEST_EXIST").toString()).append("\" ,\"").append(mDtl.get("FILE_TYPE").toString()).append("\" )");
                            sqlUtility.persist(ALIASNAME, query1.toString());
                        }
                    } else {
                        StringBuilder query1 = new StringBuilder();
                        query1.append("INSERT INTO FILE_TRANSFER_DETAIL(MASTER_PK, FILE_NAME, SRC_SERVER_EXIST, DEST_SERVER_EXIST, FILE_TYPE) ");
                        query1.append("VALUES(").append(key).append(",\"").append(filePath).append("\",\"").append(mDtl.get("SRC_EXIST").toString()).append("\",\"").append(mDtl.get("DEST_EXIST").toString()).append("\" ,\"").append(mDtl.get("FILE_TYPE").toString()).append("\" )");
//                            query1.append("VALUES(").append(key).append(",\"").append(mDtl.get("FILE_NAME").toString()).append("\",\"").append(mDtl.get("SRC_EXIST").toString()).append("\",\"").append(mDtl.get("DEST_EXIST").toString()).append("\" ,\"").append(mDtl.get("FILE_TYPE").toString()).append("\" )");
                        sqlUtility.persist(ALIASNAME, query1.toString());
                    }
                }
            }
            List listUpld = dataManager.getDataUpld(genId);

            Map mUpl;
            if (listUpld != null && !listUpld.isEmpty()) {
                for (int i = 0; i < listUpld.size(); i++) {
                    mUpl = (Map) listUpld.get(i);
                    StringBuilder query1 = new StringBuilder();
                    query1.append("INSERT INTO FILE_TRANSFER_DETAIL(MASTER_PK, FILE_NAME, SRC_SERVER_EXIST, DEST_SERVER_EXIST, FILE_TYPE) ");
                    query1.append("VALUES(").append(key).append(",\"").append(mUpl.get("FILE_NAME").toString()).append("\",\"").append(mUpl.get("SRC_EXIST").toString()).append("\",\"").append(mUpl.get("DEST_EXIST").toString()).append("\" ,\"").append(mUpl.get("FILE_TYPE").toString()).append("\" )");
                    sqlUtility.persist(ALIASNAME, query1.toString());
                }
            }
        } catch (Exception e) {
            Logger.ErrorLogger(e);
        }
        return j;
    }

    public List getFileName(final String filePath, final List retList) {
        String oriFileName = "";

        String orginalFileName = filePath;

        File folder = new File(FILE_PATH + filePath);
        File[] listOfFiles = folder.listFiles();

        Map map = new HashMap();
        Map map1 = new HashMap();

        if (listOfFiles != null) {
            if (orginalFileName.equalsIgnoreCase(filePath)
                    || listOfFiles.length == 0) {
                map1.put("FILE_NAME", filePath);
                map1.put("FILE_TYPE", "dir");
                retList.add(map1);
            }

            for (int k = 0; k < listOfFiles.length; k++) {
                map = new HashMap();
                if (listOfFiles[k].isDirectory()) {
                    String oriFileName1 = filePath + "/" + listOfFiles[k].getName();
                    getFileName(oriFileName1, retList);
                } else {
                    oriFileName = filePath + "/" + listOfFiles[k].getName();
                    map.put("FILE_NAME", oriFileName);
                    map.put("FILE_TYPE", "file");
                    retList.add(map);
                }
            }
        }
        return retList;
    }

    public List getViewEmpcode() throws ClassNotFoundException, SQLException {
        StringBuilder query = new StringBuilder();
        query.append("SELECT DISTINCT E.EMP_CODE, EMP_NAME FROM NJHR.EMP_MAST E ");
        query.append("INNER JOIN FILE_TRANSFER_MASTER F ON F.EMPCODE = E.EMP_CODE ");
        query.append("ORDER BY UPPER(EMP_NAME)");
        return sqlUtility.getList(ALIASNAME, query.toString());
    }

    public List getRequests(final String flag, final String reqNo) throws ClassNotFoundException, SQLException {
        List req = null;
        StringBuilder query = new StringBuilder();
        if ("all".equals(flag)) {
            query.append("SELECT WORK_REQUEST_ID, TITLE FROM WORK_REQUEST_MST WHERE STATUS_ID IN (12,61,14) ORDER BY WORK_REQUEST_ID");
            req = sqlUtility.getList(WFMALIASNAME, query.toString());
        } else if ("particular".equals(flag)) {
            query.append("SELECT WORK_REQUEST_ID, TITLE FROM WORK_REQUEST_MST WHERE WORK_REQUEST_ID = ").append(reqNo);
            req = sqlUtility.getList(WFMALIASNAME, query.toString());
        }
        return req;
    }

    public List getViewProject(final int flag) throws ClassNotFoundException, SQLException {
        StringBuilder query = new StringBuilder();
        List res = null;
        if (flag == 1) {
            query.append("SELECT PRJ_ID, DOMAIN_NAME, PRJ_NAME FROM ");
            query.append("(");
            query.append("SELECT PRJ_ID, DOMAIN_NAME, PRJ_NAME FROM PROJECT_MST ");
            query.append("WHERE ISACTIVE = 'Y' ");
            query.append("UNION ");
            query.append("SELECT PDM.PRJ_ID, PDM.DOMAIN_NAME, PM.PRJ_NAME FROM PROJECT_DOMAIN_MST PDM ");
            query.append("INNER JOIN PROJECT_MST PM ON PM.PRJ_ID = PDM.PRJ_ID ");
            query.append("WHERE ISACTIVE = 'Y'");
            query.append(") X ");
            query.append("WHERE DOMAIN_NAME IS NOT NULL ");
            query.append("ORDER BY PRJ_NAME");
            res = sqlUtility.getList(WFMALIASNAME, query.toString());
        } else if (flag == 2) {
            SqlRowSet prjList = null, fftPrjList = null;
            query.append("SELECT DISTINCT PRJ_ID, DOMAIN_NAME, PRJ_NAME FROM ");
            query.append("(");
            query.append("SELECT PRJ_ID, DOMAIN_NAME, PRJ_NAME FROM PROJECT_MST ");
            query.append("WHERE ISACTIVE = 'Y' ");
            query.append("UNION ");
            query.append("SELECT PDM.PRJ_ID, PDM.DOMAIN_NAME, PM.PRJ_NAME FROM PROJECT_DOMAIN_MST PDM ");
            query.append("INNER JOIN PROJECT_MST PM ON PM.PRJ_ID = PDM.PRJ_ID ");
            query.append("WHERE ISACTIVE = 'Y'");
            query.append(") X ");
            query.append("WHERE DOMAIN_NAME IS NOT NULL ");
            query.append("ORDER BY PRJ_NAME");
            prjList = sqlUtility.getRowSet(WFMALIASNAME, query.toString());

            query.setLength(0);
            query.append("SELECT DISTINCT SUBSTRING(FN, 1, INSTR(FN, '/')-1) PRJ FROM ");
            query.append("(");
            query.append("SELECT REPLACE(REPLACE(FILE_NAME, 'Deleted:', ''), 'Uploaded:', '') FN FROM FILE_TRANSFER_DETAIL");
            query.append(") X ");
            query.append("ORDER BY PRJ");
            fftPrjList = sqlUtility.getRowSet(ALIASNAME, query.toString());

            if (prjList != null && fftPrjList != null) {
                Map m = null;
                res = new ArrayList();
                while (fftPrjList.next()) {
                    prjList.beforeFirst();
                    while (prjList.next()) {
                        if (prjList.getString("DOMAIN_NAME").equalsIgnoreCase(fftPrjList.getString("PRJ"))) {
                            m = new HashMap();
                            m.put("PRJ_ID", prjList.getString("PRJ_ID"));
                            m.put("DOMAIN_NAME", prjList.getString("DOMAIN_NAME"));
                            m.put("PRJ_NAME", prjList.getString("PRJ_NAME"));
                            res.add(m);
                        }
                    }
                }
            }
        }
        return res;
    }

    public List getDataGrid(final FinfiletransferEntityBean entityBean) throws ClassNotFoundException, SQLException {
        SqlParameterSource sps = new BeanPropertySqlParameterSource(entityBean);
        StringBuilder query = new StringBuilder();
        query.append("SELECT MASTER_PK, EMP_NAME, SRC_DEST, PROJECT PROJECT_ID, REQNO, PURPOSE, ENTDATE, EXECUTE_STATUS, LANG FROM FILE_TRANSFER_MASTER M ");
        query.append("INNER JOIN NJHR.EMP_MAST E ON E.EMP_CODE = M.EMPCODE ");
        query.append("WHERE 1 = 1 ");

        if (entityBean.getEmpcode() != null) {
            query.append(" AND M.EMPCODE = :empcode");
        }
        if (entityBean.getViewreqno() != null && !entityBean.getViewreqno().equals("")) {
            query.append(" AND REQNO = :viewreqno");
        }
        if (entityBean.getViewpurpose() != null && !entityBean.getViewpurpose().equals("")) {
            query.append(" AND PURPOSE = :viewpurpose");
        }
        SqlRowSet srs = sqlUtility.getRowSet(ALIASNAME, query.toString(), sps);

        query.setLength(0);
        query.append("SELECT PRJ_ID, CONCAT(PRJ_NAME,' - ',DOMAIN_NAME) PROJECT FROM PROJECT_MST");
        if (entityBean.getProject() != null) {
            query.append(" WHERE PROJECT = :project");
        }
        SqlRowSet psrs = sqlUtility.getRowSet(WFMALIASNAME, query.toString(), sps);

        List res = null;
        if (srs != null && psrs != null) {
            Map m = null;
            res = new ArrayList();
            while (srs.next()) {
                psrs.beforeFirst();
                while (psrs.next()) {
                    if (psrs.getString("PRJ_ID").equalsIgnoreCase(srs.getString("PROJECT_ID"))) {
                        m = new LinkedHashMap();
                        m.put("MASTER_PK", srs.getString("MASTER_PK"));
                        m.put("EMP_NAME", srs.getString("EMP_NAME"));
                        m.put("SRC_DEST", srs.getString("SRC_DEST"));
                        m.put("PROJECT", psrs.getString("PROJECT"));
                        m.put("REQNO", srs.getString("REQNO"));
                        m.put("PURPOSE", srs.getString("PURPOSE"));
                        m.put("ENTDATE", srs.getString("ENTDATE"));
                        m.put("EXECUTE_STATUS", srs.getString("EXECUTE_STATUS"));
                        m.put("LANG", srs.getString("LANG"));
                        res.add(m);
                    }
                }
            }
        }
        return res;
    }

    public List getDetail(String masterPK) throws ClassNotFoundException, SQLException {
        StringBuilder query = new StringBuilder();
        if (masterPK.contains(",")) {
            masterPK = masterPK.substring(0, (masterPK.lastIndexOf(',')));
            query.append("SELECT DISTINCT REPLACE( FILE_NAME, '_-_', '/' ) AS FILE_NAME FROM FILE_TRANSFER_DETAIL WHERE MASTER_PK IN ( ").append(masterPK).append(" ) ORDER BY FILE_NAME");
        } else {
            query.append("SELECT DISTINCT REPLACE( FILE_NAME, '_-_', '/' ) AS FILE_NAME FROM FILE_TRANSFER_DETAIL WHERE MASTER_PK = '").append(masterPK).append("' ORDER BY FILE_NAME");
        }
        return sqlUtility.getList(ALIASNAME, query.toString());
    }

    public List execute(final String masterPK) throws ClassNotFoundException, SQLException {
        StringBuilder query = new StringBuilder();
        query.append("SELECT FILE_NAME,DEST_SERVER_EXIST,FILE_TYPE FROM FILE_TRANSFER_DETAIL WHERE MASTER_PK = ").append(masterPK);
        return sqlUtility.getList(ALIASNAME, query.toString());
    }

    public void changeStatus(final String masterPK, final String flag, final String emp, final String ip) throws ClassNotFoundException, SQLException {
        StringBuilder query = new StringBuilder();
        if ("execute".equalsIgnoreCase(flag)) {

            query.append("UPDATE FILE_TRANSFER_MASTER SET EXECUTE_STATUS = 'Y' WHERE MASTER_PK = ").append(masterPK);
            sqlUtility.persist(ALIASNAME, query.toString());
        } else if ("cancel".equalsIgnoreCase(flag)) {
            query.append("UPDATE FILE_TRANSFER_MASTER SET EXECUTE_STATUS = 'C' WHERE MASTER_PK = ").append(masterPK);
            sqlUtility.persist(ALIASNAME, query.toString());
        }
        query = new StringBuilder();
        Map param = new HashMap();
        param.put("execute_emp", emp);
        param.put("ipAdd", ip);
        param.put("masterPk", masterPK);
        SqlParameterSource sps = new MapSqlParameterSource(param);
        query.append("UPDATE FILE_TRANSFER_MASTER SET EXECUTE_EMP = :execute_emp , IP_ADDRESS = :ipAdd WHERE MASTER_PK = :masterPk");
        sqlUtility.persist(ALIASNAME, query.toString(), sps);
    }

    public List getViewRequest() throws ClassNotFoundException, SQLException {
        StringBuilder query = new StringBuilder();
        query.append("SELECT DISTINCT REQNO FROM FILE_TRANSFER_MASTER ORDER BY REQNO");
        return sqlUtility.getList(ALIASNAME, query.toString());
    }

    public int checkPrj(final String empCode, final String prjNm) throws ClassNotFoundException, SQLException {
        StringBuilder query = new StringBuilder();
        query.append("SELECT PM.PRJ_ID PRJ_ID ");
        query.append("FROM RESOURCE_PROJECT_MAP RPM ");
        query.append("INNER JOIN RESOURCE_MST RM ON RM.RESOURCE_ID = RPM.RESOURCE_ID ");
        query.append("INNER JOIN PROJECT_MST PM ON PM.PRJ_ID = RPM.PRJ_ID ");
        query.append("WHERE RM.MECODE = '").append(empCode).append("'");

        List list = sqlUtility.getList(WFMALIASNAME, query.toString());
        Map m;
        int res = 0;
        int mapPrjNm;
        for (int i = 0; i < list.size(); i++) {
            m = (Map) list.get(i);
            if (m.get("PRJ_ID") != null) {
                mapPrjNm = Integer.parseInt(m.get("PRJ_ID").toString());
                if (Integer.parseInt(prjNm) == mapPrjNm) {
                    res = 1;
                    break;
                }
            }
        }
        return res;
    }

    public List getMasterData(final String masterPk) throws ClassNotFoundException, SQLException {
        StringBuilder query = new StringBuilder();
        Map param = new HashMap();
        param.put("masterPk", masterPk);
        SqlParameterSource sps = new MapSqlParameterSource(param);
        query.append("SELECT * FROM FILE_TRANSFER_MASTER WHERE MASTER_PK = :masterPk");
        return sqlUtility.getList(ALIASNAME, query.toString(), sps);
    }

    public List getMasterDataLoad(final String emp) throws ClassNotFoundException, SQLException {
        StringBuilder query = new StringBuilder();
        Map param = new HashMap();
        param.put("emp", emp);
        SqlParameterSource sps = new MapSqlParameterSource(param);

        query.append("SELECT MASTER_PK, EMP_NAME, SRC_DEST, PROJECT PROJECT_ID, REQNO, PURPOSE, ENTDATE, EXECUTE_STATUS, GEN_ID, LANG FROM FILE_TRANSFER_MASTER M ");
        query.append("INNER JOIN NJHR.EMP_MAST E ON E.EMP_CODE = M.EMPCODE ");
        query.append("WHERE M.PROJECT IN (SELECT PROJECT FROM FILE_TRANSFER_MASTER WHERE EXECUTE_EMP = :emp) ");
        query.append("AND M.EXECUTE_STATUS = 'N'");
        query.append(" ORDER BY MASTER_PK DESC ");

        SqlRowSet srs = sqlUtility.getRowSet(ALIASNAME, query.toString(), sps);

        query.setLength(0);
        query.append("SELECT PRJ_ID, CONCAT(PRJ_NAME,' - ',DOMAIN_NAME) PROJECT FROM PROJECT_MST");
        SqlRowSet psrs = sqlUtility.getRowSet(WFMALIASNAME, query.toString(), sps);

        List res = null;
        if (srs != null && psrs != null) {
            Map m = null;
            res = new ArrayList();
            while (srs.next()) {
                psrs.beforeFirst();
                while (psrs.next()) {
                    if (psrs.getString("PRJ_ID").equalsIgnoreCase(srs.getString("PROJECT_ID"))) {
                        m = new LinkedHashMap();
                        m.put("MASTER_PK", srs.getString("MASTER_PK"));
                        m.put("EMP_NAME", srs.getString("EMP_NAME"));
                        m.put("SRC_DEST", srs.getString("SRC_DEST"));
                        m.put("PROJECT", psrs.getString("PROJECT"));
                        m.put("REQNO", srs.getString("REQNO"));
                        m.put("PURPOSE", srs.getString("PURPOSE"));
                        m.put("ENTDATE", srs.getString("ENTDATE"));
                        m.put("EXECUTE_STATUS", srs.getString("EXECUTE_STATUS"));
                        m.put("GEN_ID", srs.getString("GEN_ID"));
                        m.put("LANG", srs.getString("LANG"));
                        res.add(m);
                    }
                }
            }
        }
        return res;
    }

    public String getReqStatus(final String masterPK) throws ClassNotFoundException, SQLException {
        StringBuilder query = new StringBuilder();
        Map param = new HashMap();
        param.put("masterPK", masterPK);
        SqlParameterSource sps = new MapSqlParameterSource(param);
        query.append("SELECT EXECUTE_STATUS FROM FILE_TRANSFER_MASTER WHERE MASTER_PK = :masterPK");
        return sqlUtility.getString(ALIASNAME, query.toString(), sps);
    }

    /**
     *
     * @param entityBean
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getReportDataLoad(final FinfiletransferEntityBean entityBean)
            throws ClassNotFoundException, SQLException {
        StringBuilder query = new StringBuilder();
        SqlParameterSource sps = new BeanPropertySqlParameterSource(entityBean);

        query.append(" SELECT FTM.SRC_DEST, PM.PRJ_NAME, IFNULL(FTM.REQNO,'0') REQNO, FTD.FILE_NAME, ");
        query.append(" EM.EMP_NAME, DATE_FORMAT(FTM.ENTDATE,'%d-%m-%Y %h:%i:%s %p') ENTDATE, \n");
        query.append(" IFNULL(FTM.PURPOSE,'-') PURPOSE ");
        query.append(" FROM finstudio.FILE_TRANSFER_MASTER FTM \n");
        query.append(" INNER JOIN WFM.PROJECT_MST PM ON PM.PRJ_ID = FTM.PROJECT \n");
        query.append(" INNER JOIN finstudio.FILE_TRANSFER_DETAIL FTD ON FTD.MASTER_PK = FTM.MASTER_PK \n");
        query.append(" INNER JOIN NJHR.EMP_MAST EM ON EM.EMP_CODE = FTM.EMPCODE \n");
        query.append(" WHERE FTM.PROJECT = :projectname \n");
        query.append(" AND SRC_DEST IN(").append(entityBean.getSrcdest()).append(") \n");
        query.append("AND FTD.FILE_NAME LIKE '%")
                .append(entityBean.getFileName().trim()).append("' \n");
        query.append("ORDER BY FTM.ENTDATE DESC LIMIT 500");

        return sqlUtility.getList(ALIASNAME, query.toString(), sps);
    }

}