package com.finlogic.business.finstudio.model;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import com.finlogic.apps.finstudio.formbean.ServerAccessAuditReportFormBean;

public class ServerAccessAuditReportManager
{

    ServerAccessAuditReportDataManager dataManager = new ServerAccessAuditReportDataManager();
    String tomcatPath = finpack.FinPack.getProperty("tomcat1_path");

    public SqlRowSet getRequestURLWiseReport(ServerAccessAuditReportFormBean formBean) throws Exception
    {
        return dataManager.getRequestURLWiseReport(formBean);
    }
    public SqlRowSet getProjectWiseReport(ServerAccessAuditReportFormBean formBean) throws Exception
    {
        return dataManager.getProjectWiseReport(formBean);
    }
    public SqlRowSet getRequestURLReport(ServerAccessAuditReportFormBean formBean) throws Exception
    {
        return dataManager.getRequestURLReport(formBean);
    }
}
