package com.finlogic.apps.finstudio.finstudiomur;

import com.finlogic.util.persistence.SQLUtility;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

public class FinstudiomurReport
{

    private SQLUtility sqlService = new SQLUtility();

    public List<Map> getDataGrid(FinstudiomurFormBean formBean) throws ClassNotFoundException, SQLException
    {
        StringBuilder queryMast = new StringBuilder();
        queryMast.append(" SELECT X.SRNO SR_NO, FINSTUDIO_MODULE_NAME,PROJECT_NAME,MODULE_NAME,IFNULL(EM.MENAME,'-') EMP_NAME,DATE_FORMAT(ON_DATE,'%d-%m-%Y') ON_DATE ");
        queryMast.append(" FROM ");
        queryMast.append(" ( ");
        queryMast.append(" SELECT RM.SRNO, ");
        queryMast.append(" REMARKS 'FINSTUDIO_MODULE_NAME',  ");
        queryMast.append(" PROJECT_NAME,MODULE_NAME, ");
        queryMast.append(" RM.ON_DATE, ");
        queryMast.append(" RM.EMP_CODE ");
        queryMast.append(" FROM RPT_MAIN RM ");
        queryMast.append(" UNION ALL ");
        queryMast.append(" SELECT MM.SRNO, ");
        queryMast.append(" 'Master Generation V2' FINSTUDIO_MODULE_NAME,  ");
        queryMast.append(" PROJECT_NAME,MODULE_NAME, ");
        queryMast.append(" MM.ON_DATE, ");
        queryMast.append(" MM.EMP_CODE ");
        queryMast.append(" FROM MST_MAIN MM ");
        queryMast.append(" UNION ALL ");
        queryMast.append(" SELECT OTM.SRNO, ");
        queryMast.append(" 'Oracle To MySQL' FINSTUDIO_MODULE_NAME, ");
        queryMast.append(" CONCAT(SCHEMA_NAME,'(',SERVER_NAME,')') PROJECT_NAME, ");
        queryMast.append(" CONCAT(ITEM_NAME,'(',ITEM_TYPE,')') MODULE_NAME, ");
        queryMast.append(" OTM.ON_DATE, ");
        queryMast.append(" OTM.EMP_CODE ");
        queryMast.append(" FROM ORACLETOMYSQL OTM ");
        queryMast.append(" UNION ALL");
        queryMast.append(" SELECT WSM.SRNO,  'Web Service Generation' FINSTUDIO_MODULE_NAME,   PROJECT_NAME,MODULE_NAME,  WSM.ON_DATE,  WSM.EMP_CODE  ");
        queryMast.append(" FROM WEBSRVC_MAIN WSM");
        queryMast.append(" ) X ");
        queryMast.append(" LEFT JOIN njindiainvest.ME_MAST EM ON EM.MECODE=X.EMP_CODE  ");
        queryMast.append(" WHERE X.ON_DATE BETWEEN STR_TO_DATE(:fromDate,'%d-%m-%Y %k:%i') AND STR_TO_DATE(:toDate,'%d-%m-%Y %k:%i') ");
        if (formBean.getCmbModlUsgName() != null)
        {
            queryMast.append(" AND FINSTUDIO_MODULE_NAME IN (");
            for (int i = 0; i < formBean.getCmbModlUsgName().length; i++)
            {
                queryMast.append("'");
                queryMast.append(formBean.getCmbModlUsgName()[i]);
                queryMast.append("'");
                queryMast.append(",");
            }
            queryMast.deleteCharAt(queryMast.length()-1);
            queryMast.append(")");
        }
        queryMast.append(" ORDER BY X.ON_DATE DESC ");

        HashMap hm = new HashMap();
        hm.put("fromDate", formBean.getFromDate() + " 0:0");
        hm.put("toDate", formBean.getToDate() + " 23:59");

        MapSqlParameterSource param = new MapSqlParameterSource(hm);

        return sqlService.getList("finstudio_mysql", queryMast.toString(), param);
    }
}
