/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.business.finstudio.filedirautorequest;

import com.finlogic.apps.finstudio.filedirautorequest.FileDirAutoRequestBean;
import com.finlogic.util.persistence.SQLTranUtility;
import com.finlogic.util.persistence.SQLUtility;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

/**
 *
 * @author njuser
 */
public class FileDirAutoRequestDataManager {

    private static final String FINALIAS = "finstudio_dbaudit_common";
    private static final String WFMALIASNAME = "wfm";
    private static final String NJINDIA_ALIASNAME = "njindiainvest_online";
    private final SQLUtility sqlUtility = new SQLUtility();

    public int insertData(final FileDirAutoRequestBean formBean, String empCode, String userName) throws ClassNotFoundException, SQLException {
        Map map = new HashMap();
        StringBuilder query;
        query = new StringBuilder();
        String[] projectData = (formBean.getProject()).split("\\|");

        query.append(" INSERT INTO FILEDIR_AUTO_REQUEST (SERVER_NAME,PROJECT_ID,PROJECT_NAME,PATH,PROCESS,PURPOSE ");

        String req_process = formBean.getProcess().toLowerCase();
        String serverName = formBean.getServername();
        if (req_process.equalsIgnoreCase("getlist") || req_process.equalsIgnoreCase("checkexist") || (req_process.equalsIgnoreCase("getfile")
                && !serverName.equalsIgnoreCase("prodhosb1.nj"))) {

            query.append(",STATUS ");
        }

        query.append(",EMPCODE,USERNAME) ");

        query.append(" VALUES ( ");

        query.append(" :servername, :projectid, :projectname, :path, :process, :purpose ");

        if (req_process.equalsIgnoreCase("getlist") || req_process.equalsIgnoreCase("checkexist") || (req_process.equalsIgnoreCase("getfile")
                && !serverName.equalsIgnoreCase("prodhosb1.nj"))) {

            query.append(",'authorized'");
        }

        query.append(" , :empcode, :username ) ");

        map.put("servername", serverName);
        map.put("projectid", projectData[0]);
        map.put("projectname", projectData[1]);
        if(!formBean.getServername().contains("sb1.nj") && formBean.getProcess().equals("GetFile")) {
            formBean.setPathlist(formBean.getPathlist().replace("\r\n", "\n").replace("\r", "\n").replace("\n",";"+formBean.getPathCombo()));
            map.put("path", formBean.getPathCombo() + formBean.getPathlist());
        }
        else {
            map.put("path", formBean.getPathCombo() + formBean.getPath());
        }
        map.put("process", formBean.getProcess());
        map.put("purpose", formBean.getPurpose());
        map.put("empcode", empCode);
        map.put("username", userName);

        return sqlUtility.persist(FINALIAS, query.toString(), new MapSqlParameterSource(map));
    }

    public List reportLoader(final FileDirAutoRequestBean formBean) throws ClassNotFoundException, SQLException {
        StringBuilder query;
        query = new StringBuilder();
        Map map = new HashMap();
        query.append(" SELECT SRNO,SERVER_NAME,PROJECT_NAME, ");
        query.append(" PROCESS,REPLACE(PATH,';','\\n') PATH, ");
        query.append(" PURPOSE,USERNAME, ");
        query.append(" DATE_FORMAT(ENTDATE,'%d-%b-%Y %H:%i'), ");
        query.append(" DATE_FORMAT(LASTUPDATE,'%d-%b-%Y %H:%i'),");
        query.append(" CASE WHEN PROCESS = 'GetFile' AND COMMENT like '%cache%' THEN ");
        query.append(" CONCAT(STATUS,'(','<a   href=javascript:void(0); onclick=javascript:downloadFile(''', COMMENT , '''); >', SUBSTRING_INDEX(COMMENT,'/',-1),'</a>)') ");
        query.append(" WHEN PROCESS = 'GetList' AND COMMENT like '%cache%' THEN ");
        query.append(" CONCAT(STATUS,'(','<a   href=javascript:void(0); onclick=javascript:showGetListFile(''', COMMENT , '''); >', SUBSTRING_INDEX(COMMENT,'/',-1),'</a>)') ");
        query.append(" ELSE  ");
        query.append(" CONCAT(STATUS,' ");
        query.append(" (',IFNULL(COMMENT,'-'),')')");
        query.append(" END AS STATUS");
        query.append(" FROM FILEDIR_AUTO_REQUEST ");
        query.append(" WHERE  1=1 ");

        if (!"".equals(formBean.getSRNO())) {
            query.append(" AND SRNO = ").append(formBean.getSRNO());

            return sqlUtility.getList(FINALIAS, query.toString());
        } else {
            if (!"-1".equals(formBean.getRpt_servername())) {
                query.append(" AND SERVER_NAME = '").append(formBean.getRpt_servername()).append("'");
            }

            if (!"-1".equals(formBean.getRpt_project())) {
                String[] projectData = (formBean.getRpt_project()).split("\\|");
                query.append(" AND PROJECT_ID = '").append(projectData[0]).append("'");
            }

            if(!(formBean.getRpt_empcode().equalsIgnoreCase("J0188") 
                || formBean.getRpt_empcode().equalsIgnoreCase("N0155")
                || formBean.getRpt_empcode().equalsIgnoreCase("J0020"))) {
                if (!formBean.getRpt_empcode().isEmpty()) {
                    String str = getChildEmpCode(formBean.getRpt_empcode());
                    query.append(" AND EMPCODE IN (").append(str).append(") ");
                }
            }

            query.append(" AND DATE(ENTDATE) BETWEEN STR_TO_DATE(:from_date,'%d-%m-%Y') AND STR_TO_DATE(:to_date,'%d-%m-%Y') ");
            map.put("from_date", formBean.getFrom_date());
            map.put("to_date", formBean.getTo_date());

            query.append(" ORDER BY ENTDATE DESC LIMIT 20 ");
            
            return sqlUtility.getList(FINALIAS, query.toString(), new MapSqlParameterSource(map));
        }
    }

    public List authorizeLoader() throws ClassNotFoundException, SQLException {
        StringBuilder query;
        query = new StringBuilder();
        query.append(" SELECT SRNO,SERVER_NAME,PROJECT_NAME ");
        query.append(" ,PROCESS,PATH,STATUS,PURPOSE,USERNAME, ");
        query.append(" DATE_FORMAT(ENTDATE,'%d-%b-%Y %H:%i'),CONCAT('<input type=''checkbox'' ");
        query.append(" class=''checkbox'' style=''float: none ! important;'' ");
        query.append(" name=''ch_'' id=',SRNO,' onclick=javascript:disableother(',SRNO,')>') CheckAll,CONCAT('<input type=''text'' ");
        query.append(" class=''textbox'' style=''float: none!important; width: 136px;'' ");
        query.append(" name=''txt_',SRNO,''' id=''txt_',SRNO,''' />') reason ");
        query.append(" FROM FILEDIR_AUTO_REQUEST ");
        query.append(" WHERE STATUS IN ('pending') ");
        query.append(" ORDER BY ENTDATE LIMIT 20 ");

        return sqlUtility.getList(FINALIAS, query.toString());
    }

    public int authorizeUpdate(String id) throws ClassNotFoundException, SQLException {
        StringBuilder query = new StringBuilder();
        query.append(" UPDATE FILEDIR_AUTO_REQUEST SET STATUS = 'authorized' WHERE SRNO IN (").append(id).append(") ");

        return sqlUtility.persist(FINALIAS, query.toString());
    }

    public int rejectUpdate(String id, String comment) throws ClassNotFoundException, SQLException {
        StringBuilder query;
        query = new StringBuilder();

        String idArray[] = id.split(",");
        String commentArray[] = comment.split(",");
        int cnt = 0;
        if (idArray.length == commentArray.length) {
            for (int i = 0; i < idArray.length; i++) {
                query.setLength(0);
                query.append(" UPDATE FILEDIR_AUTO_REQUEST SET STATUS = 'rejected', COMMENT = '");
                query.append(commentArray[i]);
                query.append("' WHERE SRNO = ").append(idArray[i]);
                cnt += sqlUtility.persist(FINALIAS, query.toString());
            }
        }
        return cnt;
    }

    public List getUserList() throws ClassNotFoundException, SQLException {
        StringBuilder query = new StringBuilder();
        query.append(" SELECT EMPCODE, USERNAME FROM FILEDIR_AUTO_REQUEST GROUP BY EMPCODE ");

        return sqlUtility.getList(FINALIAS, query.toString());
    }

    public List getProjectNames() throws ClassNotFoundException, SQLException {
        StringBuilder query = new StringBuilder();
        query.append(" SELECT DISTINCT PROJECT_ID,PROJECT_NAME ");
        query.append(" FROM FILEDIR_AUTO_REQUEST ");
        query.append(" WHERE PROJECT_NAME <> '' ");
        query.append(" ORDER BY UPPER(PROJECT_NAME) ");

        return sqlUtility.getList(FINALIAS, query.toString());
    }
    
    public List getWFMProjectList(final int flag) throws ClassNotFoundException, SQLException {
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
            fftPrjList = sqlUtility.getRowSet(FINALIAS, query.toString());

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
    
    public String getChildEmpCode(String loginEmpCode) throws ClassNotFoundException, SQLException {
        
        SQLTranUtility tran = null;
        String empCodeStr = "";
        
        try {
            tran = new SQLTranUtility();
            tran.openConn(NJINDIA_ALIASNAME);
            
            StringBuilder sb = new StringBuilder();
            sb.append(" SET SESSION group_concat_max_len = 1000000 ");
            tran.persist(sb.toString());
            
            sb.setLength(0);
            sb.append(" SELECT GROUP_CONCAT('\\'',ME_CHILD_CODE,'\\'') EMPCODE ");
            sb.append(" FROM njindiainvest.ME_CHILD MC ");
            sb.append("     INNER JOIN NJHR.EMP_DESIG ED ON ED.DESIG_ID = MC.DESIG_ID ");
            sb.append(" WHERE ME_PAR_CODE = :LOGINEMPCODE ");
            sb.append(" ORDER BY ME_CHILD_CODE ");
            empCodeStr = tran.getString(sb.toString(), new MapSqlParameterSource("LOGINEMPCODE", loginEmpCode));
        }
        catch (Exception e) {
            if (tran != null) 
            {
                tran.rollbackChanges();
            }
            throw e;
        }
        finally {
            try {
                if (tran != null && !tran.getConnection().isClosed()) {
                    tran.closeConn();
                }
            }
            catch (Exception e) {
                throw e;
            }
        }
        return empCodeStr;
    }
    
    public String getDesigCode(String empCode) throws ClassNotFoundException, SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT DESIG_ID FROM njindiainvest.ME_CHILD MC ");
        sb.append(" WHERE ME_PAR_CODE = :EMPCODE AND ME_CHILD_CODE = :EMPCODE ");
        return sqlUtility.getString(NJINDIA_ALIASNAME, sb.toString(), new MapSqlParameterSource("EMPCODE",empCode));
    }
}