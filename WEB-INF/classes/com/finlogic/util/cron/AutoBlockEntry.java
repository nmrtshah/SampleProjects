/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.cron;

import com.finlogic.util.Logger;
import com.finlogic.util.persistence.SQLTranUtility;
import java.net.InetAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

/**
 *
 * @author Jigna Patel
 */
public class AutoBlockEntry
{
    private static final String ALIAS_NAME = "finstudio_dbaudit_common";
    private static final String BLOCK_URL_FILEPATH = "/tomcat/BlockURL.xml";
    
    public static void main(String[] args)
    {
        configureBlockEntry();
        removeBlockEntry();
    }
    
    private static void configureBlockEntry()
    {
        SQLTranUtility tran = null;
        try
        {
            tran = new SQLTranUtility();
            tran.openConn(ALIAS_NAME);
            String hostName = InetAddress.getLocalHost().getHostName();
            Logger.DataLogger("AutoBlockEntry | HOST NAME => "+hostName);
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT ID,BLOCK_ENTRY ");
            sb.append(" FROM BLOCK_MODULE_DETAIL ");
            sb.append(" WHERE STATUS = 'B-AUTHORISED' ");
            sb.append(" AND SERVER_NAME = :hostName ");

            List entryList = tran.getList(sb.toString(),new MapSqlParameterSource("hostName", hostName));
            for(Object entryObj:entryList)
            {
                Map map = (Map) entryObj;
                String block = map.get("BLOCK_ENTRY").toString();
                String blockId = map.get("ID").toString();
                Logger.DataLogger("AutoBlockEntry | Add block entry in file - start ");
                replaceFileContent("</urlconfig>",block+"\n</urlconfig>");
                Logger.DataLogger("AutoBlockEntry | Add block entry in file - end ");

                sb.setLength(0);
                sb.append(" UPDATE BLOCK_MODULE_DETAIL ");
                sb.append(" SET STATUS = 'B-CONFIGURED', ");
                sb.append("     LAST_UPDATED_DATE = SYSDATE() ");
                sb.append(" WHERE ID =  :blockedId ");
                int flag = tran.persist(sb.toString(),new MapSqlParameterSource("blockedId", blockId));
                Logger.DataLogger("AutoBlockEntry | add block entry updated flag => "+flag);
            }
            tran.commitChanges();
        }
        catch(Exception e)
        {
            Logger.DataLogger("AutoBlockEntry | Exception => "+e);
            try
            {
                if (tran != null) 
                {
                    tran.rollbackChanges();
                }
            }
            catch(Exception e1)
            {
                Logger.DataLogger("AutoBlockEntry | Exception => "+e1);
            }
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
                Logger.DataLogger("AutoBlockEntry | Exception => "+e);
            }
        }
    }
    
    private static void removeBlockEntry()
    {
        SQLTranUtility tran = null;
        try
        {
            tran = new SQLTranUtility();
            tran.openConn(ALIAS_NAME);
            String hostName = InetAddress.getLocalHost().getHostName();
            Logger.DataLogger("AutoBlockEntry | HOST NAME => "+hostName);
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT ID,BLOCK_ENTRY ");
            sb.append(" FROM BLOCK_MODULE_DETAIL ");
            sb.append(" WHERE STATUS = 'UB-AUTHORISED' ");
            sb.append(" AND SERVER_NAME = :hostName ");

            List entryList = tran.getList(sb.toString(),new MapSqlParameterSource("hostName", hostName));
            for(Object entryObj:entryList)
            {
                Map map = (Map) entryObj;
                String block = map.get("BLOCK_ENTRY").toString();
                String blockId = map.get("ID").toString();
                Logger.DataLogger("AutoBlockEntry | Remove block entry in file - start ");
                replaceFileContent(block+"\n","");
                Logger.DataLogger("AutoBlockEntry | Remove block entry in file - end ");

                sb.setLength(0);
                sb.append(" UPDATE BLOCK_MODULE_DETAIL ");
                sb.append(" SET STATUS = 'UB-CONFIGURED', ");
                sb.append("     LAST_UPDATED_DATE = SYSDATE() ");
                sb.append(" WHERE ID =  :blockedId ");
                int flag = tran.persist(sb.toString(),new MapSqlParameterSource("blockedId", blockId));
                Logger.DataLogger("AutoBlockEntry | remove block entry updated flag => "+flag);
            }
            tran.commitChanges();
        }
        catch(Exception e)
        {
            Logger.DataLogger("AutoBlockEntry | Exception => "+e);
            try
            {
                if (tran != null) 
                {
                    tran.rollbackChanges();
                }
            }
            catch(Exception e1)
            {
                Logger.DataLogger("AutoBlockEntry | Exception => "+e1);
            }
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
                Logger.DataLogger("AutoBlockEntry | Exception => "+e);
            }
        }
    }
    
    private static void replaceFileContent(String originContent,String replaceContent) throws Exception
    {
        Path path = Paths.get(BLOCK_URL_FILEPATH);
        Charset charset = StandardCharsets.UTF_8;
        String content = new String(Files.readAllBytes(path),charset);
        
        StringBuilder sb = new StringBuilder(content);
        int sbIndex = sb.indexOf(originContent);
        sb = sb.replace(sbIndex, sb.indexOf(originContent)+originContent.length(), replaceContent);
        content = sb.toString();
        
        Files.write(path, content.getBytes(charset));
    }
}