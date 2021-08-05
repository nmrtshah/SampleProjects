/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.business.finstudio.findatareqexecutor;

/**
 *
 * @author Sonam Patel
 */
public class QueryEntityBean
{

    //DATA_REQUEST_QUERY Table's Fields Starts
    private String query;
    private String devExeResult;
    private String devExeStatus;
    private String devExeStartTime;
    private String devExeEndTime;
    //DATA_REQUEST_QUERY Table's Fields Ends

    public String getQuery()
    {
        return query;
    }

    public void setQuery(final String query)
    {
        this.query = query;
    }

    public String getDevExeResult()
    {
        return devExeResult;
    }

    public void setDevExeResult(final String devExeResult)
    {
        this.devExeResult = devExeResult;
    }

    public String getDevExeStatus()
    {
        return devExeStatus;
    }

    public void setDevExeStatus(final String devExeStatus)
    {
        this.devExeStatus = devExeStatus;
    }

    public String getDevExeStartTime()
    {
        return devExeStartTime;
    }

    public void setDevExeStartTime(final String devExeStartTime)
    {
        this.devExeStartTime = devExeStartTime;
    }

    public String getDevExeEndTime()
    {
        return devExeEndTime;
    }

    public void setDevExeEndTime(final String devExeEndTime)
    {
        this.devExeEndTime = devExeEndTime;
    }
}
