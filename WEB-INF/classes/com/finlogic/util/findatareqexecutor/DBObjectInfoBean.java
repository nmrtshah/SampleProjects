/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.findatareqexecutor;

/**
 *
 * @author Sonam Patel
 */
public class DBObjectInfoBean
{

    private String schema;
    private String objType;
    private String objName;

    public String getSchema()
    {
        return schema;
    }

    public void setSchema(final String schema)
    {
        this.schema = schema;
    }

    public String getObjType()
    {
        return objType;
    }

    public void setObjType(final String objType)
    {
        this.objType = objType;
    }

    public String getObjName()
    {
        return objName;
    }

    public void setObjName(final String objName)
    {
        this.objName = objName;
    }
}
