/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.findatareqexecutor;

import java.io.Serializable;

/**
 *
 * @author Sonam Patel
 */
public class DependencyInfoBean implements Serializable
{

    private String dependency;
    private String depServer;
    private String depSchema;
    private String depObjType;
    private String depObjName;
    private String depColName;

    public String getDependency()
    {
        return dependency;
    }

    public void setDependency(final String dependency)
    {
        this.dependency = dependency;
    }

    public String getDepServer()
    {
        return depServer;
    }

    public void setDepServer(final String depServer)
    {
        this.depServer = depServer;
    }

    public String getDepSchema()
    {
        return depSchema;
    }

    public void setDepSchema(final String depSchema)
    {
        this.depSchema = depSchema;
    }

    public String getDepObjType()
    {
        return depObjType;
    }

    public void setDepObjType(final String depObjType)
    {
        this.depObjType = depObjType;
    }

    public String getDepObjName()
    {
        return depObjName;
    }

    public void setDepObjName(final String depObjName)
    {
        this.depObjName = depObjName;
    }

    public String getDepColName()
    {
        return depColName;
    }

    public void setDepColName(final String depColName)
    {
        this.depColName = depColName;
    }
}
