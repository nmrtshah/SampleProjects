package com.finlogic.apps.finstudio.finstudiomur;

import com.finlogic.util.datastructure.JSONParser;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class FinstudiomurService
{

    private FinstudiomurReport report = new FinstudiomurReport();

    public String getDataGrid(FinstudiomurFormBean formBean) throws ClassNotFoundException, SQLException
    {
        List<Map> ls = report.getDataGrid(formBean);
        JSONParser jsonParser = new JSONParser();        
        return jsonParser.parse(ls, "SR_NO", true, false);
    }
}
