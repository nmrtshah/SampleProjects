/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.business.finstudio.blockmodules;

import java.util.List;
import com.finlogic.util.persistence.SQLTranUtility;

/**
 *
 * @author Jigna Patel
 */
public class BlockModulesManager 
{
    private final BlockModulesDataManager dataMgr = new BlockModulesDataManager();
    private static final String ALIASNAME = "finstudio_dbaudit_common";

    public int insertBlockEntry(final BlockModulesEntityBean bmeb) throws Exception 
    {
        return dataMgr.insertBlockEntry(bmeb);
    }

    public int updateBlockEntry(final String unBlockedId) throws Exception 
    {
        SQLTranUtility tran = null;
        int result = 0;
        try 
        {
            tran = new SQLTranUtility();
            tran.openConn(ALIASNAME);
            result = dataMgr.updateBlockEntry(unBlockedId, tran);
        }
        catch (Exception e) 
        {
            if (tran != null) 
            {
                tran.rollbackChanges();
            }
            throw e;
        } 
        finally 
        {
            try 
            {
                if (tran != null && !tran.getConnection().isClosed()) 
                {
                    tran.closeConn();
                }
            }
            catch (Exception e) 
            {
                throw e;
            }
        }
        return result;
    }

    public int updateAuthStatus(final String unBlockedId) throws Exception 
    {
        SQLTranUtility tran = null;
        int result = 0;
        try 
        {
            tran = new SQLTranUtility();
            tran.openConn(ALIASNAME);
            result = dataMgr.updateAuthStatus(unBlockedId, tran);
        }
        catch (Exception e) 
        {
            if (tran != null) 
            {
                tran.rollbackChanges();
            }
            throw e;
        }
        finally
        {
            try
            {
                if (tran != null && !tran.getConnection().isClosed())
                {
                    tran.closeConn();
                }
            }
            catch (Exception e) 
            {
                throw e;
            }
        }
        return result;
    }

    public int updateRejectStatus(final String unBlockedId, final String remarks) throws Exception 
    {
        SQLTranUtility tran = null;
        int result = 0;
        try 
        {
            tran = new SQLTranUtility();
            tran.openConn(ALIASNAME);
            result = dataMgr.updateRejectStatus(unBlockedId, remarks, tran);
        }
        catch (Exception e) 
        {
            if (tran != null)
            {
                tran.rollbackChanges();
            }
            throw e;
        }
        finally
        {
            try
            {
                if (tran != null && !tran.getConnection().isClosed())
                {
                    tran.closeConn();
                }
            }
            catch (Exception e)
            {
                throw e;
            }
        }
        return result;
    }

    public List getReportTabData(final BlockModulesEntityBean bmeb) throws Exception 
    {
        return dataMgr.getReportTabData(bmeb);
    }

    public List getAuthoriseTabData(final BlockModulesEntityBean bmeb) throws Exception 
    {
        return dataMgr.getAuthoriseTabData(bmeb);
    }
    
    public List getUserNameList() throws Exception
    {
        return dataMgr.getUserNameList();
    }
    
    public int setBlockEntry() throws Exception
    {
        SQLTranUtility tran = null;
        int result = 0;
        try 
        {
            tran = new SQLTranUtility();
            tran.openConn(ALIASNAME);
            result = dataMgr.setBlockEntry(tran);
        }
        catch (Exception e) 
        {
            if (tran != null)
            {
                tran.rollbackChanges();
            }
            throw e;
        }
        finally
        {
            try
            {
                if (tran != null && !tran.getConnection().isClosed())
                {
                    tran.closeConn();
                }
            }
            catch (Exception e)
            {
                throw e;
            }
        }
        return result;
    }
}