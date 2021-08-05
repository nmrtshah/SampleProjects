package com.finlogic.apps.finstudio.finstudiostatistics;

import com.finlogic.util.persistence.SQLUtility;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Sonam Patel
 */
public class FinstudiostatisticsReport
{

    public static final String ALIAS = "finstudio_mysql";
    private final SQLUtility sqlService = new SQLUtility();

    public List<Map> getDetailReportData(FinstudiostatisticsFormBean formBean) throws SQLException, ClassNotFoundException
    {
        StringBuilder queryMast;
        queryMast = new StringBuilder();
        queryMast.append("SELECT SRNO, GROUP_NAME, STATS_STRING, DATE_FORMAT(ON_DATE,'%d-%m-%Y') FROM FINSTUDIO_STATISTICS WHERE IS_ACTIVE = 'Y'");
        if (formBean.getGroupname() != null && !"-1".equals(formBean.getGroupname()))
        {
            queryMast.append(" AND GROUP_NAME = '").append(formBean.getGroupname()).append("'");
        }
        queryMast.append(" ORDER BY GROUP_NAME");
        List<Map> lsMast;
        lsMast = sqlService.getList(ALIAS, queryMast.toString());
        return lsMast;
    }

    public List getgroupnameList() throws SQLException, ClassNotFoundException
    {
        StringBuilder queryMast = new StringBuilder();
        queryMast.append("SELECT DISTINCT GROUP_NAME FROM FINSTUDIO_STATISTICS WHERE IS_ACTIVE = 'Y' ORDER BY GROUP_NAME");
        return sqlService.getList(ALIAS, queryMast.toString());
    }
}
