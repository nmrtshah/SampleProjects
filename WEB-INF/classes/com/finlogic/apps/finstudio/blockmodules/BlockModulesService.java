/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.apps.finstudio.blockmodules;

import com.finlogic.business.finstudio.blockmodules.BlockModulesEntityBean;
import com.finlogic.business.finstudio.blockmodules.BlockModulesManager;
import com.finlogic.util.datastructure.JSONParser;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author Jigna Patel
 */
public class BlockModulesService {
    
    private final BlockModulesManager mgr = new BlockModulesManager();
    
    public String getBlockDateStr(String monthFromDate, String dayFromDate, String hourFromDate, String minFromDate)
    {
        int currYear = (Calendar.getInstance()).get(Calendar.YEAR);
        if(monthFromDate != null && !monthFromDate.isEmpty() && !monthFromDate.equalsIgnoreCase("-1")
           && dayFromDate != null && !dayFromDate.isEmpty() && !dayFromDate.equalsIgnoreCase("-1")
           && hourFromDate != null && !hourFromDate.isEmpty() && !hourFromDate.equalsIgnoreCase("-1")
           && minFromDate != null && !minFromDate.isEmpty() && !minFromDate.equalsIgnoreCase("-1")) 
        {
            return (currYear + "-" + monthFromDate + "-" + dayFromDate + " " + hourFromDate + ":" + minFromDate + ":00");
        }
        return "";
    }
    
    private BlockModulesEntityBean convertToEntityBean(BlockModulesFormBean bmfb) 
    {
        BlockModulesEntityBean bmeb = new BlockModulesEntityBean();
        bmeb.setFromDateStr(this.getBlockDateStr(bmfb.getMonthFromDate(), bmfb.getDayFromDate(), bmfb.getHourFromDate(), bmfb.getMinFromDate()));
        bmeb.setToDateStr(this.getBlockDateStr(bmfb.getMonthToDate(), bmfb.getDayToDate(), bmfb.getHourToDate(), bmfb.getMinToDate()));
        bmeb.setAccessLog(bmfb.getAccessLog());
        bmeb.setAllowIpAddress(bmfb.getAllowIpAddress());
        bmeb.setDayFromDate(bmfb.getDayFromDate());
        bmeb.setDayToDate(bmfb.getDayToDate());
        bmeb.setHourFromDate(bmfb.getHourFromDate());
        bmeb.setHourToDate(bmfb.getHourToDate());
        String blockMsg = (bmfb.getMessage() != null) ? bmfb.getMessage().replaceAll("&", "and") : bmfb.getMessage();
        bmeb.setMessage(blockMsg);
        bmeb.setMinFromDate(bmfb.getMinFromDate());
        bmeb.setMinToDate(bmfb.getMinToDate());
        bmeb.setMonthFromDate(bmfb.getMonthFromDate());
        bmeb.setMonthToDate(bmfb.getMonthToDate());
        if(bmfb.getParamNames() != null)
            bmeb.setParamNames(bmfb.getParamNames().equalsIgnoreCase("") ? "NA" : bmfb.getParamNames());
        if(bmfb.getParamValues() != null)
            bmeb.setParamValues(bmfb.getParamValues().equalsIgnoreCase("") ? "NA" : bmfb.getParamValues());
        bmeb.setRemarks(bmfb.getRemarks());
        bmeb.setServerName(bmfb.getServerName());
        if(bmfb.getSesVarNames() != null)
            bmeb.setSesVarNames(bmfb.getSesVarNames().equalsIgnoreCase("") ? "NA" : bmfb.getSesVarNames());
        if(bmfb.getSesVarValues() != null)
            bmeb.setSesVarValues(bmfb.getSesVarValues().equalsIgnoreCase("") ? "NA" : bmfb.getSesVarValues());
        bmeb.setUri(bmfb.getUri());
        bmeb.setUserName(bmfb.getUserName());
        bmeb.setBlockPeriodOptn(bmfb.getBlockPeriodOptn());
        bmeb.setBlockStatus(bmfb.getBlockStatus());
        bmeb.setBlockAction(bmfb.getBlockAction());
        bmeb.setFilterFromDate(bmfb.getFilterFromDate());
        bmeb.setFilterToDate(bmfb.getFilterToDate());
        return bmeb;
    }
    
    public int insertBlockEntry(final BlockModulesFormBean bmfb) throws Exception 
    {
        BlockModulesEntityBean bmeb = convertToEntityBean(bmfb);
        return mgr.insertBlockEntry(bmeb);
    }
    
    public int updateBlockEntry(final String unBlockedId) throws Exception 
    {
        return mgr.updateBlockEntry(unBlockedId);
    }
    
    public int updateAuthStatus(final String unBlockedId) throws Exception 
    {
        return mgr.updateAuthStatus(unBlockedId);
    }
    
    public int updateRejectStatus(final String unBlockedId, final String remarks) throws Exception 
    {
        return mgr.updateRejectStatus(unBlockedId,remarks);
    }
    
    public String getReportTabData(final BlockModulesFormBean bmfb) throws Exception 
    {
        JSONParser jsonParser = new JSONParser();
        return jsonParser.parse(mgr.getReportTabData(convertToEntityBean(bmfb)), "ID", true, false);
    }
    
    public String getAuthoriseTabData(final BlockModulesFormBean bmfb) throws Exception 
    {
        JSONParser jsonParser = new JSONParser();
        return jsonParser.parse(mgr.getAuthoriseTabData(convertToEntityBean(bmfb)), "ID", true, false);
    }
    
    public List getUserNameList() throws Exception
    {
        return mgr.getUserNameList();
    }
    
    public int setBlockEntry() throws Exception
    {
        return mgr.setBlockEntry();
    }
}