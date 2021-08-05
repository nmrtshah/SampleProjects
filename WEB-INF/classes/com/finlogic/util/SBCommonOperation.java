/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util;

import com.finlogic.util.sbutility.SBFileBean;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 *
 * @author njuser
 */
public class SBCommonOperation extends com.finlogic.util.sbutility.StorageBoxImpl
{

    private static SBCommonOperation sbCommon = new SBCommonOperation();
    private static StorageBoxImpl sb = new StorageBoxImpl(47);

    private SBCommonOperation()
    {
        super(47);
       
    }

    public static SBCommonOperation getSBCommonOperation()
    {
        return sbCommon;
    }

    public static StorageBoxImpl getStorageBoxImpl()
    {
        return sb;
    }
    
    @Override
    public boolean isFileExist(String sbFilePath) throws Exception
    {
        return super.isFileExist(sbFilePath);
    }

    @Override
    public String getFile(String sbFilePath) throws IllegalArgumentException, UnsupportedEncodingException, IOException
    {
        return super.getFile(sbFilePath);
    }
    
    @Override
    public List<SBFileBean> getList(String sbDirPath) throws IllegalArgumentException, UnsupportedEncodingException, IOException
    {
        return super.getList(sbDirPath);
    }
}