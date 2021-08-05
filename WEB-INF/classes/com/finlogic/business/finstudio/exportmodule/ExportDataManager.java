/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.business.finstudio.exportmodule;

import com.finlogic.util.persistence.SQLService;
import java.sql.SQLException;
import org.springframework.jdbc.support.rowset.SqlRowSet;

/**
 *
 * @author njuser
 */
public class ExportDataManager {

    private static final String ALIASNAME = "mftran";
    SQLService sqlUtility = new SQLService();

    public SqlRowSet getTableData_Company() throws Exception
    {
            StringBuilder SQL = new StringBuilder();
            
            SQL.append("SELECT  CCODE , \n");
            SQL.append("   CDESC , \n");
            SQL.append("   WEBSITE , \n");
            SQL.append("  NVL(TO_CHAR(ENTRY_DATE,'DD-MM-YYYY'),'N/A') ENTRY_DATE, \n");
            SQL.append("   EFORM , \n");
            SQL.append("   PEDIGREE , \n");
            SQL.append("   AMC_CODE_BSE , \n");
            SQL.append("   AMC_CODE_NSE \n");
            SQL.append("FROM COMPANY ORDER BY CCODE ");

         return sqlUtility.getRowSet(ALIASNAME, SQL.toString());
         
    }

    public SqlRowSet getTableData_Company_Rnt() throws Exception
    {
            StringBuilder SQL = new StringBuilder();
            
            SQL.append("SELECT  RNTCODE, \n");
            SQL.append("   AMCCODE, \n");
            SQL.append("   CCODE \n");
            SQL.append("FROM COMPANY_RNT \n");
            SQL.append("ORDER BY RNTCODE");
        
            return sqlUtility.getRowSet(ALIASNAME, SQL.toString());
    }

    public SqlRowSet getTableData_Portch() throws Exception
    {
    
            StringBuilder SQL = new StringBuilder();
            
            SQL.append("SELECT  CCODE, \n");
            SQL.append("   SCHCODE, \n");
            SQL.append("   SCHNAME, \n");
            SQL.append("  NVL(TO_CHAR(ENTRY_DATE,'DD-MM-YYYY'),'N/A') ENTRY_DATE, \n");
            SQL.append("   MARK4DELETE, \n");
            SQL.append("   MARKET_CAP, \n");
            SQL.append("   PORT_SCHTYPE, \n");
            SQL.append("   PORT_SCHSUBTYPE, \n");
            SQL.append("  NVL(TO_CHAR(NFOSTART_DT,'DD-MM-YYYY'),'N/A') NFOSTART_DT , \n");
            SQL.append("  NVL(TO_CHAR(NFOEND_DT,'DD-MM-YYYY'),'N/A') NFOEND_DT, \n");
            SQL.append("  NVL(TO_CHAR(SCND_INS_DT,'DD-MM-YYYY'),'N/A') SCND_INS_DT, \n");
            SQL.append("  PORT_SCHSTATUS, \n");
            SQL.append("   PORT_REGSTRAR, \n");
            SQL.append("  NVL(TO_CHAR(PORT_TERMINATE_DT,'DD-MM-YYYY'),'N/A') PORT_TERMINATE_DT, \n");
            SQL.append("  NVL(TO_CHAR(ALLOTE_INC_DT,'DD-MM-YYYY'),'N/A') ALLOTE_INC_DT , \n");
            SQL.append("   OBJECTIVE, \n");
            SQL.append("   FUND_COMMENTARY, \n");
            SQL.append("   MECODE, \n");
            SQL.append("   FUNDTYPE, \n");
            SQL.append("   TERMINATION_LEVEL, \n");
            SQL.append("   INCEPTION_LEVEL , \n");
            SQL.append("   COLOR_ID \n");
            SQL.append(" FROM PORTSCH \n");
            SQL.append(" ORDER BY CCODE");
        
            return sqlUtility.getRowSet(ALIASNAME, SQL.toString());
            

    }

    public SqlRowSet getTableData_Navch() throws Exception
    {
    
            StringBuilder SQL = new StringBuilder();
            
            SQL.append("SELECT  CCODE, \n");
            SQL.append("   SCHCODE, \n");
            SQL.append("   SCHNAME, \n");
            SQL.append("   PORTSCH, \n");
            SQL.append("   SCHTYPE, \n");
            SQL.append("   SCHSUBTYPE, \n");
            SQL.append("   OPT, \n");
            SQL.append("   UPCODE, \n");
            SQL.append("   INVTYPE, \n");
            SQL.append("   BROKCODE, \n");
            SQL.append("   TERMINATED, \n");
            SQL.append("  NVL(TO_CHAR(TDATE,'DD-MM-YYYY'),'N/A') TDATE, \n");
            SQL.append("  NVL(TO_CHAR(ENTRY_DATE,'DD-MM-YYYY'),'N/A') ENTRY_DATE, \n");
            SQL.append("   SUBOPT, \n");
            SQL.append("  TRNUPCODE, \n");
            SQL.append("  NVL(TO_CHAR(IPODATE,'DD-MM-YYYY'),'N/A') IPODATE, \n");
            SQL.append("  AMFCODE, \n");
            SQL.append("   REGCODE, \n");
            SQL.append("   DIV_FREQ, \n");
            SQL.append("  NVL(TO_CHAR(IPODATE_TRN,'DD-MM-YYYY'),'N/A') IPODATE_TRN , \n");
            SQL.append("   MECODE, \n");
            SQL.append("  NVL(TO_CHAR(INCEPTION_DT,'DD-MM-YYYY'),'N/A') INCEPTION_DT , \n");
            SQL.append("   TRNUPCODE_2, \n");
            SQL.append("   BROKCODE_2, \n");
            SQL.append("   PLAN \n");
            SQL.append(" FROM NAVSCH \n");
            SQL.append(" WHERE 1=1");
            SQL.append(" ORDER BY CCODE");
             
            return sqlUtility.getRowSet(ALIASNAME, SQL.toString());

        
    }

    public SqlRowSet getTableData_Shtype() throws Exception
    {
            StringBuilder SQL = new StringBuilder();
            
            SQL.append("SELECT SCHTYPE, \n");
            SQL.append("  SCHDESC, \n");
            SQL.append("  DEBT, \n");
            SQL.append("  EQUITY, \n");
            SQL.append("  NVL(TO_CHAR(ENTRY_DATE,'DD-MM-YYYY'),'N/A') ENTRY_DATE \n");
            SQL.append(" FROM SCHTYPE \n");
            SQL.append(" ORDER BY SCHTYPE");
            
            return sqlUtility.getRowSet(ALIASNAME, SQL.toString());
   
    }

    public SqlRowSet getTableData_Schsubtype() throws Exception
    {
        
            StringBuilder SQL = new StringBuilder();
            
            SQL.append("SELECT SCHTYPE, \n");
            SQL.append("  SCHSUBTYPE, \n");
            SQL.append("  SUBDESC, \n");
            SQL.append("  NVL(TO_CHAR(ENTRY_DATE,'DD-MM-YYYY'),'N/A') ENTRY_DATE , \n");
            SQL.append("  AUDIT_DIFF, \n");
            SQL.append("  SUBSHORTDESC, \n");
            SQL.append("  TAX_NATURE, \n");
            SQL.append("  ASSET_CLASS, \n");
            SQL.append("  EMP_CODE, \n");
            SQL.append("  INT_RATE_SENSITIVITY \n");
            SQL.append(" FROM SCHSUBTYPE \n");
            SQL.append(" ORDER BY SCHTYPE");
            
            return sqlUtility.getRowSet(ALIASNAME, SQL.toString());

        
    }

    public SqlRowSet getTableData_Opt()  throws Exception
    {
            StringBuilder SQL = new StringBuilder();
            
            SQL.append(" SELECT OPT, \n");
            SQL.append(" NVL(TO_CHAR(ENTRY_DATE,'DD-MM-YYYY'),'N/A') ENTRY_DATE \n");
            SQL.append(" FROM OPT \n");
            SQL.append(" ORDER BY OPT");
        
            return sqlUtility.getRowSet(ALIASNAME, SQL.toString());
    }

    public SqlRowSet getTableData_Regmst() throws Exception
    {
    
            StringBuilder SQL = new StringBuilder();
            
            SQL.append("SELECT  REGCODE, \n");
            SQL.append("  REGNAME, \n");
            SQL.append("   ADD1, \n");
            SQL.append("  ADD2, \n");
            SQL.append("  ADD3, \n");
            SQL.append("  E_MAIL, \n");
            SQL.append("  PHONE, \n");
            SQL.append("  WEBSITE \n");
            SQL.append(" FROM REGMST \n");
            SQL.append(" ORDER BY REGCODE");
    
            return sqlUtility.getRowSet(ALIASNAME, SQL.toString());
    }

    public SqlRowSet getTableData__nav() throws Exception 
    {
        
            StringBuilder frmdate = new StringBuilder();
            StringBuilder tdate = new StringBuilder();
            
            frmdate.append("SELECT TO_CHAR(NVL(MAX(ENTRYDATE),SYSDATE)-5,'DD-MM-YYYY') ENTRYDATE FROM MUTRATE");
            tdate.append("SELECT TO_CHAR(SYSDATE,'DD-MM-YYYY HH24:MI')  FROM DUAL");
            
            String passfromdate = sqlUtility.getString(ALIASNAME, frmdate.toString());
            String passtodate = sqlUtility.getString(ALIASNAME, tdate.toString());
            
            StringBuilder SQL = new StringBuilder();
            
            SQL.append("SELECT SCHCODE, \n");
            SQL.append(" NVL(TO_CHAR(NAVDATE,'DD-MM-YYYY'),'N/A') NAVDATE, \n");
            SQL.append(" SALENAV, \n");
            SQL.append(" NVL(TO_CHAR(ENTRYDATE,'DD-MM-YYYY'),'N/A') ENTRYDATE, \n");
            SQL.append(" ENTTYPE \n");
            SQL.append(" FROM MUTRATE  \n");
            SQL.append(" WHERE 1=1 ");
            
            if (passfromdate != null) {
                String newfrmdate=passfromdate+" 00:01";
                 SQL.append(" AND TO_DATE(TO_CHAR(ENTRYDATE,'DD-MM-YYYY HH24:MI'),'DD-MM-YYYY HH24:MI') >= TO_DATE('").append(newfrmdate).append("','DD-MM-YYYY HH24:MI')");
            }
            if (passtodate != null) {
                SQL.append(" AND  TO_DATE(TO_CHAR(ENTRYDATE,'DD-MM-YYYY HH24:MI'),'DD-MM-YYYY HH24:MI') <= TO_DATE('").append(passtodate).append("','DD-MM-YYYY HH24:MI')");
            }

            return sqlUtility.getRowSet(ALIASNAME, SQL.toString());

        
    }
     public SqlRowSet getTableData__div(String frmdate) throws Exception
     {
            StringBuilder SQL = new StringBuilder();
            
            SQL.append("SELECT NVL(TO_CHAR(DIVDATE,'DD-MM-YYYY'),'N/A') DIVDATE, \n");
            SQL.append(" DIVUNIT, \n");
            SQL.append(" SCHCODE, \n");
            SQL.append(" NAV, \n");
            SQL.append(" NVL(TO_CHAR(ENTRY_DATE,'DD-MM-YYYY'),'N/A') ENTRY_DATE, \n");
            SQL.append(" DAYDIFF \n");
            SQL.append(" FROM DIVENT  \n");
            SQL.append(" WHERE 1=1 ");
            
            if (!frmdate.equals("null") && !frmdate.equalsIgnoreCase("NA") && !"".equals(frmdate)) {
                SQL.append(" AND ENTRY_DATE >= ").append("to_date('").append(frmdate).append("','dd-MM-yyyy')");
            }
            SQL.append(" ORDER BY SCHCODE");
        
            return  sqlUtility.getRowSet(ALIASNAME, SQL.toString());
        
    }
     
    public SqlRowSet getTableDataBonus(String frmdate) throws Exception
    {
        
            StringBuilder SQL = new StringBuilder();
            
            SQL.append("SELECT SCHCODE, \n");
            SQL.append(" NVL(TO_CHAR(BONUSDATE,'DD-MM-YYYY'),'N/A') BONUSDATE, \n");
            SQL.append(" BONUSUNIT, \n");
            SQL.append(" NVL(TO_CHAR(ENTRY_DATE,'DD-MM-YYYY'),'N/A') ENTRY_DATE \n");
            SQL.append(" FROM BONUS  \n");
            SQL.append(" WHERE 1=1 ");
            
            if (!frmdate.equals("null") && !frmdate.equalsIgnoreCase("NA") && !"".equals(frmdate)) {
                SQL.append(" AND ENTRY_DATE >= ").append("to_date('").append(frmdate).append("','dd-MM-yyyy')");
            }
            SQL.append(" ORDER BY ENTRY_DATE");
            
            return  sqlUtility.getRowSet(ALIASNAME, SQL.toString());
        
    } 
    
     public SqlRowSet getTableData_TAC_SCHMST() throws Exception 
     {
     
            StringBuilder SQL = new StringBuilder();
            
            SQL.append("SELECT  SCHCODE, \n");
            SQL.append("   EX_CODE, \n");
            SQL.append("   SCHCODE_DIVR_GR, \n");
            SQL.append("   SCHCODE_DIVP, \n");
            SQL.append("   ISIN_DIVR_GR, \n");
            SQL.append("   ISIN_DIVP, \n");
            SQL.append("   MECODE, \n");
            SQL.append("  NVL(TO_CHAR(ENT_DATE,'DD-MM-YYYY'),'N/A') ENT_DATE, \n");
            SQL.append("   LIQUID_SCHCODE_DIVR_GR, \n");
            SQL.append("   LIQUID_SCHCODE_DIVP, \n");
            SQL.append("   TAT, \n");
            SQL.append("   OFFLINE_TRXN, \n");
            SQL.append("   AMT_WITHOUT_RECO, \n");
            SQL.append("   PIP_DIVR_GR, \n");
            SQL.append("   PIP_DIVP, \n");
            SQL.append("   RED_DIVR_GR, \n");
            SQL.append("   RED_DIVP, \n");
            SQL.append("   SIP_DIVR_GR, \n");
            SQL.append("   SIP_DIVP, \n");
            SQL.append("   STP_DIVR_GR, \n");
            SQL.append("   STP_DIVP, \n");
            SQL.append("   NFO_DIVR_GR, \n");
            SQL.append("   NFO_DIVP, \n");
            SQL.append("  NVL(TO_CHAR(NFO_STARTDATE_ONLINE,'DD-MM-YYYY'),'N/A') NFO_STARTDATE_ONLINE, \n");
            SQL.append("  NVL(TO_CHAR(NFO_ENDDATE_ONLINE,'DD-MM-YYYY'),'N/A') NFO_ENDDATE_ONLINE, \n");
            SQL.append("  NVL(TO_CHAR(NFO_STARTDATE_OFFLINE,'DD-MM-YYYY'),'N/A') NFO_STARTDATE_OFFLINE, \n");
            SQL.append("  NVL(TO_CHAR(NFO_ENDDATE_OFFLINE,'DD-MM-YYYY'),'N/A') NFO_ENDDATE_OFFLINE, \n");
            SQL.append("  NVL(TO_CHAR(NFO_STARTDATE_CNT,'DD-MM-YYYY'),'N/A') NFO_STARTDATE_CNT, \n");
            SQL.append("  NVL(TO_CHAR(NFO_ENDDATE_CNT,'DD-MM-YYYY'),'N/A') NFO_ENDDATE_CNT, \n");
            SQL.append("  NVL(TO_CHAR(ALLOTMENTDATE,'DD-MM-YYYY'),'N/A') ALLOTMENTDATE, \n");
            SQL.append("  NVL(TO_CHAR(NFO_SIP_STARTDATE,'DD-MM-YYYY'),'N/A') NFO_SIP_STARTDATE, \n");
            SQL.append("   DPC_DIVR_GR, \n");
            SQL.append("   DPC_DIVP, \n");
            SQL.append("   NFO_SIP_DIVR_GR, \n");
            SQL.append("   NFO_SIP_DIVP, \n");
            SQL.append("   NVL(TO_CHAR(NFO_STARTDATE_ONLINE_PHY,'DD-MM-YYYY'),'N/A') NFO_STARTDATE_ONLINE_PHY, \n"); //
            SQL.append("   NVL(TO_CHAR(NFO_ENDDATE_ONLINE_PHY,'DD-MM-YYYY'),'N/A') NFO_ENDDATE_ONLINE_PHY, \n"); 
            SQL.append("   NVL(TO_CHAR(NFO_STARTDATE_OFFLINE_PHY,'DD-MM-YYYY'),'N/A') NFO_STARTDATE_OFFLINE_PHY, \n");
            SQL.append("   NVL(TO_CHAR(NFO_ENDDATE_OFFLINE_PHY,'DD-MM-YYYY'),'N/A') NFO_ENDDATE_OFFLINE_PHY, \n");
            SQL.append("   NVL(TO_CHAR(NFO_STARTDATE_CNT_PHY,'DD-MM-YYYY'),'N/A') NFO_STARTDATE_CNT_PHY, \n");
            SQL.append("   NVL(TO_CHAR(NFO_ENDDATE_CNT_PHY,'DD-MM-YYYY'),'N/A') NFO_ENDDATE_CNT_PHY, \n");
            SQL.append("   NVL(TO_CHAR(ALLOTMENTDATE_PHY,'DD-MM-YYYY'),'N/A') ALLOTMENTDATE_PHY, \n");
            SQL.append("   NVL(TO_CHAR(NFO_SIP_STARTDATE_PHY,'DD-MM-YYYY'),'N/A') NFO_SIP_STARTDATE_PHY, \n");//
            SQL.append("   INSURE_SCHCODE_DIVR_GR, \n");
            SQL.append("   INSURE_SCHCODE_DIVP, \n"); 
            SQL.append("   L1_SCHCODE_DIVR_GR, \n");
            SQL.append("   L1_SCHCODE_DIVP, \n");
            SQL.append("   PIP_DIVR_GR_PHY, \n");
            SQL.append("   PIP_DIVP_PHY, \n");
            SQL.append("   RED_DIVR_GR_PHY, \n");
            SQL.append("   RED_DIVP_PHY, \n");
            SQL.append("   SWITCH_DIVR_GR, \n");
            SQL.append("   SWITCH_DIVP, \n");
            SQL.append("   SWITCH_DIVR_GR_PHY, \n");
            SQL.append("   SWITCH_DIVP_PHY, \n");
            SQL.append("   SIP_DIVR_GR_PHY, \n");
            SQL.append("   SIP_DIVP_PHY, \n");
            SQL.append("   INSURE_SIP_DIVR_GR, \n");
            SQL.append("   INSURE_SIP_DIVP, \n");
            SQL.append("   INSURE_SIP_DIVR_GR_PHY, \n");
            SQL.append("   INSURE_SIP_DIVP_PHY, \n");
            SQL.append("   STP_DIVR_GR_PHY, \n");
            SQL.append("   STP_DIVP_PHY, \n");
            SQL.append("   SWP_DIVR_GR, \n");
            SQL.append("   SWP_DIVP, \n");
            SQL.append("   SWP_DIVR_GR_PHY, \n");
            SQL.append("   SWP_DIVP_PHY, \n");
            SQL.append("   NFO_DIVR_GR_PHY, \n");
            SQL.append("   NFO_DIVP_PHY, \n");
            SQL.append("   NFO_SIP_DIVR_GR_PHY, \n");
            SQL.append("   NFO_SIP_DIVP_PHY  \n");
            SQL.append(" FROM TAC_SCHMST \n");
            SQL.append(" ORDER BY SCHCODE");
            
            return sqlUtility.getRowSet(ALIASNAME, SQL.toString());

    }
     
    /**
     * <b> Develop By Hiren Bhuva : </b> This function is used to return mftran.olt_schmst data. <br>
     * 
     * @return  - SQL Rowset with olt_schmst data
     * @throws ClassNotFoundException
     * @throws SQLException
     */ 
    public SqlRowSet getTableData_OLT_SCHMST() throws ClassNotFoundException,SQLException
    {
            StringBuilder SQL = new StringBuilder();
            
            SQL.append(" SELECT OLTSCHCODE, SCHCODE, UNITFACEVAL, DIVOPTFLAG, INITPURAMT \n")
               .append("  , MININITMULTAMT, MINPURAMT, PURMULTAMT, ENTRYLOAD, EXITLOAD, MINREDAMT, REDMULTAMT \n")
               .append("  , MINBALAMT, MINREDUNIT, REDMULTUNIT, MINBALUNIT, CUTOFFPUR \n")
               .append("  , CUTOFFRED, PURFLAG, REDFLAG, ISSIPAVAIL, ISSWPAVAIL \n")
               .append("  , ISSWIAVAIL, ISTRANSFERAVAIL, NVL(TO_CHAR(TRANSFERSTARTDATE,'DD-MM-YYYY'),'N/A') TRANSFERSTARTDATE  \n")
               .append("  , CUTOFFPURAMC, CUTOFFREDAMC, ISSTPAVAIL, SCH_MODE \n")
               .append("  , OLTSCHCODE_DIVP, BUSSDAY, MECODE, NVL(TO_CHAR(ENTRY_DATE,'DD-MM-YYYY'),'N/A') ENTRY_DATE  \n")
               .append("  , NVL(TO_CHAR(SWISTARTDATE,'DD-MM-YYYY'),'N/A') SWISTARTDATE  \n")
               .append(" FROM OLT_SCHMST \n")
               .append(" ORDER BY OLTSCHCODE ");
            
            return sqlUtility.getRowSet(ALIASNAME, SQL.toString());
   
    }

}
