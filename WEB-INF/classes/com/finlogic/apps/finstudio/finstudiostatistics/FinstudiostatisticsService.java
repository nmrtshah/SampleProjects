package com.finlogic.apps.finstudio.finstudiostatistics;

import com.finlogic.util.datastructure.JSONParser;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Sonam Patel
 */
public class FinstudiostatisticsService
{

    private final FinstudiostatisticsReport report = new FinstudiostatisticsReport();

    public String getReportData(final FinstudiostatisticsFormBean formBean) throws SQLException, ClassNotFoundException
    {
        List<Map> list = new ArrayList();
        String jsonStr = "";
        JSONParser jsonParser = new JSONParser();
        list = report.getDetailReportData(formBean);
        jsonStr = jsonParser.parse(list, "SRNO", false, false);
        return jsonStr;
    }

    public List getgroupnameList() throws SQLException, ClassNotFoundException
    {
        return report.getgroupnameList();
    }
}
