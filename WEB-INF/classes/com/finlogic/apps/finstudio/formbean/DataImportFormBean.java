/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.apps.finstudio.formbean;

import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author njuser
 */
public class DataImportFormBean
{

    MultipartFile filename;
    String del;
    char quote;
    String aliasname;
    String tablename;
    Boolean header;
    String dbtype;
    String tables;
    String[] fields;
    String[] tabfields;
    String dest;

    public String getDest()
    {
        return dest;
    }

    public void setDest(String dest)
    {
        this.dest = dest;
    }

    public String[] getTabfields()
    {
        return tabfields;
    }

    public void setTabfields(String[] tabfields)
    {
        this.tabfields = tabfields;
    }

    public String getTables()
    {
        return tables;
    }

    public void setTables(String tables)
    {
        this.tables = tables;
    }

    public MultipartFile getFilename()
    {
        return filename;
    }

    public void setFilename(MultipartFile filename)
    {
        this.filename = filename;
    }

    public String getDel()
    {
        return del;
    }

    public void setDel(String del)
    {
        this.del = del;
    }

    public Boolean getHeader()
    {
        return header;
    }

    public void setHeader(Boolean header)
    {
        this.header = header;
    }

    public char getQuote()
    {
        return quote;
    }

    public void setQuote(char quote)
    {
        this.quote = quote;
    }

    public String getAliasname()
    {
        return aliasname;
    }

    public void setAliasname(String aliasname)
    {
        this.aliasname = aliasname;
    }

    public String getTablename()
    {
        return tablename;
    }

    public void setTablename(String tablename)
    {
        this.tablename = tablename;
    }

    public String getDbtype()
    {
        return dbtype;
    }

    public void setDbtype(String dbtype)
    {
        this.dbtype = dbtype;
    }

    public String[] getFields()
    {
        return fields;
    }

    public void setFields(String[] fields)
    {
        this.fields = fields;
    }
}
