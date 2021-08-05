package com.finlogic.apps.finstudio.finstudiostatistics;

/**
 *
 * @author Sonam Patel
 */
public class FinstudiostatisticsFormBean
{

    private String groupname;
    private String reportName;
    private String rdoRptFormate;
    private String displayMode;
    private String sidx;
    private int rows;
    private int page;

    public String getGroupname()
    {
        return groupname;
    }

    public void setGroupname(final String GroupnameVar)
    {
        groupname = GroupnameVar;
    }

    public String getReportName()
    {
        return reportName;
    }

    public void setReportName(String reportName)
    {
        this.reportName = reportName;
    }

    public String getRdoRptFormate()
    {
        return rdoRptFormate;
    }

    public void setRdoRptFormate(String rdoRptFormate)
    {
        this.rdoRptFormate = rdoRptFormate;
    }

    public String getDisplayMode()
    {
        return displayMode;
    }

    public void setDisplayMode(String displayMode)
    {
        this.displayMode = displayMode;
    }

    public int getPage()
    {
        return page;
    }

    public void setPage(final int page)
    {
        this.page = page;
    }

    public int getRows()
    {
        return rows;
    }

    public void setRows(final int rows)
    {
        this.rows = rows;
    }

    public String getSidx()
    {
        return sidx;
    }

    public void setSidx(final String sidx)
    {
        this.sidx = sidx;
    }
}
