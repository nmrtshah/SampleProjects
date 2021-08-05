/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.findatareqexecutor;

import java.util.List;

/**
 *
 * @author Sonam Patel
 */
public class QueryInfoBean
{

    private String query;
    private String clause;
    private String subClause;
    private String subClauseTarget;
    private boolean isWherePresent;
    private boolean needConfirmation;
    private DBObjectInfoBean dbObjInfo;
    private List<DependencyInfoBean> depInfo;

    public String getQuery()
    {
        return query;
    }

    public void setQuery(final String query)
    {
        this.query = query;
    }

    public String getClause()
    {
        return clause;
    }

    public void setClause(final String clause)
    {
        this.clause = clause;
    }

    public String getSubClause()
    {
        return subClause;
    }

    public void setSubClause(final String subClause)
    {
        this.subClause = subClause;
    }

    public String getSubClauseTarget()
    {
        return subClauseTarget;
    }

    public void setSubClauseTarget(final String subClauseTarget)
    {
        this.subClauseTarget = subClauseTarget;
    }

    public boolean isIsWherePresent()
    {
        return isWherePresent;
    }

    public void setIsWherePresent(final boolean isWherePresent)
    {
        this.isWherePresent = isWherePresent;
    }

    public boolean isNeedConfirmation()
    {
        return needConfirmation;
    }

    public void setNeedConfirmation(final boolean needConfirmation)
    {
        this.needConfirmation = needConfirmation;
    }

    public DBObjectInfoBean getDbObjInfo()
    {
        return dbObjInfo;
    }

    public void setDbObjInfo(final DBObjectInfoBean dbObjInfo)
    {
        this.dbObjInfo = dbObjInfo;
    }

    public List<DependencyInfoBean> getDepInfo()
    {
        return depInfo;
    }

    public void setDepInfo(List<DependencyInfoBean> depInfo)
    {
        this.depInfo = depInfo;
    }
}
