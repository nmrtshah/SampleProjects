/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.business.finstudio.oracletomysql;

/**
 *
 * @author njuser
 */
public class OracleToMysqlEntityBean
{

    private String server = null;
    private String schema = null;
    private String item = null;
    private String itemNmCmb = null;
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

    public String getEmp_code()
    {
        return emp_code;
    }

    public void setEmp_code(String emp_code)
    {
        this.emp_code = emp_code;
    }
}
