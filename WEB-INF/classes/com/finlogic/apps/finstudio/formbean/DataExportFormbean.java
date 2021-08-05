/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.finlogic.apps.finstudio.formbean;

/**
 *
 * @author njuser
 */
public class DataExportFormbean
{
    String aliasname;
    String dbtype;
    String query;
    String filenm;

    public String getFilenm()
    {
        return filenm;
    }

    public void setFilenm(String filenm)
    {
        this.filenm = filenm;
    }

    public String getAliasname()
    {
        return aliasname;
    }

    public void setAliasname(String aliasname)
    {
        this.aliasname = aliasname;
    }

    public String getDbtype()
    {
        return dbtype;
    }

    public void setDbtype(String dbtype)
    {
        this.dbtype = dbtype;
    }

    public String getQuery()
    {
        return query;
    }

    public void setQuery(String query)
    {
        this.query = query;
    }
}
