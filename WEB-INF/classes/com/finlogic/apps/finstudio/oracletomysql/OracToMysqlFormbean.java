/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.apps.finstudio.oracletomysql;

/**
 *
 * @author njuser
 */
public class OracToMysqlFormbean
{

    private String server = null;
    private String schema = null;
    private String item = null;    
    private String itemNmCmb = null;
    private String query = null;
    private String rdoGetSourceType;
    private String txtaOraQuery;
    private String emp_code;

    public String getServer()
    {
        return server;
    }

    public void setServer(String server)
    {
        this.server = server;
    }

    public String getSchema()
    {
        return schema;
    }

    public void setSchema(String schema)
    {
        this.schema = schema;
    }

    public String getItem()
    {
        return item;
    }

    public void setItem(String item)
    {
        this.item = item;
    }
   
    public String getItemNmCmb()
    {
        return itemNmCmb;
    }

    public void setItemNmCmb(String itemNmCmb)
    {
        this.itemNmCmb = itemNmCmb;
    }

    public String getQuery()
    {
        return query;
    }

    public void setQuery(String query)
    {
        this.query = query;
    }

    public String getRdoGetSourceType()
    {
        return rdoGetSourceType;
    }

    public void setRdoGetSourceType(String rdoGetSourceType)
    {
        this.rdoGetSourceType = rdoGetSourceType;
    }

    public String getTxtaOraQuery()
    {
        return txtaOraQuery;
    }

    public void setTxtaOraQuery(String txtaOraQuery)
    {
        this.txtaOraQuery = txtaOraQuery;
    }

    public String getEmp_code()
    {
        return emp_code;
    }

    public void setEmp_code(String emp_code)
    {
        this.emp_code = emp_code;
    }   
    
}
