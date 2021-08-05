package com.finlogic.apps.finstudio.service;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import com.finlogic.util.JSONParser;
import com.finlogic.business.finstudio.model.ServerAccessAuditReportManager;
import com.finlogic.apps.finstudio.formbean.ServerAccessAuditReportFormBean;

public class ServerAccessAuditReportService
{

    ServerAccessAuditReportManager manager = new ServerAccessAuditReportManager();
    JSONParser jsonParser = new JSONParser();

    public String getRequestURLWiseReport(ServerAccessAuditReportFormBean formBean) throws Exception
    {
        SqlRowSet srs = manager.getRequestURLWiseReport(formBean);
        return jsonParser.Parse(srs, "REQUEST_URL");
    }

    public String getProjectWiseReport(ServerAccessAuditReportFormBean formBean) throws Exception
    {
        SqlRowSet srs = manager.getProjectWiseReport(formBean);
        return jsonParser.Parse(srs, "PROJECT_NAME");
    }

    public String getRequestURLReport(ServerAccessAuditReportFormBean formBean) throws Exception
    {
        SqlRowSet srs = manager.getRequestURLReport(formBean);
        return jsonParser.Parse(srs, "REQUEST_URL");
    }
}
