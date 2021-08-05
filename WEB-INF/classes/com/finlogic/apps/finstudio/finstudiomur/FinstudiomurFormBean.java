package com.finlogic.apps.finstudio.finstudiomur;

public class FinstudiomurFormBean
{

    private String reportName;
    private String rdoRptFormate;
    private String sidx;
    private int rows;
    private int page;
    private String fromDate;
    private String toDate;
    private String[] cmbModlUsgName;
    private String displayMode;

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

    public int getPage()
    {
        return page;
    }

    public void setPage(int page)
    {
        this.page = page;
    }

    public int getRows()
    {
        return rows;
    }

    public void setRows(int rows)
    {
        this.rows = rows;
    }

    public String getSidx()
    {
        return sidx;
    }

    public void setSidx(String sidx)
    {
        this.sidx = sidx;
    }

    public String getFromDate()
    {
        return fromDate;
    }

    public void setFromDate(String fromDate)
    {
        this.fromDate = fromDate;
    }

    public String getToDate()
    {
        return toDate;
    }

    public void setToDate(String toDate)
    {
        this.toDate = toDate;
    }

    public String[] getCmbModlUsgName()
    {
        return cmbModlUsgName;
    }

    public void setCmbModlUsgName(String[] cmbModlUsgName)
    {
        this.cmbModlUsgName = cmbModlUsgName;
    }

    public String getDisplayMode()
    {
        return displayMode;
    }

    public void setDisplayMode(String displayMode)
    {
        this.displayMode = displayMode;
    }
}
