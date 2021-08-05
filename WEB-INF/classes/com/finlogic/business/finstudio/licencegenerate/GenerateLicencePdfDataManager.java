/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.business.finstudio.licencegenerate;

import com.finlogic.util.persistence.SQLUtility;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

/**
 *
 * @author njuser
 */
public class GenerateLicencePdfDataManager
{

//    private final String FINALIAS = "finstudio";
    private final String FINALIAS = "finstudio_mysql";
    private final SQLUtility sqlUtility = new SQLUtility();

    protected int insertClient(Map m) throws Exception
    {
        StringBuilder query = new StringBuilder();
        m.put("PASSWORD", createPassword());
        query.append("INSERT INTO CER_CLIENT_MST(CLIENT_NAME,ADDRESS,ENTRY_BY,PASSWORD) ");
        query.append("VALUES(:CLIENT_NAME,:ADDRESS,:ENTRY_BY,:PASSWORD)");
        return sqlUtility.persist(FINALIAS, query.toString(), new MapSqlParameterSource(m));
    }

    protected int insertClientDetails(Map m) throws Exception
    {
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO CER_CLIENT_DETAIL(CLIENT_ID,SYS_KEY,ACTIVATION_DATE,EXPIRY_DATE,ENTRY_BY,COMMENT) ");
        query.append("VALUES(:CLIENT_ID,:SYS_KEY,STR_TO_DATE(:ACTIVATION_DATE,'%d-%m-%Y'),STR_TO_DATE(:EXPIRY_DATE,'%d-%m-%Y'),:ENTRY_BY,:COMMENT)");
        return sqlUtility.persist(FINALIAS, query.toString(), new MapSqlParameterSource(m));
    }

    public List getClientList() throws Exception
    {
        return sqlUtility.getList(FINALIAS, "SELECT CLIENT_ID,CLIENT_NAME FROM CER_CLIENT_MST ORDER BY UPPER(CLIENT_NAME)");
    }

    public String getPassword(String clientid) throws Exception
    {
        Map m = new HashMap();
        m.put("CLIENT_ID", clientid);
        return sqlUtility.getString(FINALIAS, "SELECT PASSWORD FROM CER_CLIENT_MST WHERE CLIENT_ID=:CLIENT_ID", new MapSqlParameterSource(m));
    }

    public List reportLoader(Map m) throws Exception
    {
        StringBuilder query = new StringBuilder();
        query.append("SELECT @SRNO :=@SRNO+1 SRNO, CCM.CLIENT_ID, CCM.CLIENT_NAME, CCM.ADDRESS, \n"
                + "IFNULL(DATE_FORMAT(CCD.ACTIVATION_DATE,'%d-%m-%Y'),'-') ACTIVATION_DATE, \n"
                + "IFNULL(DATE_FORMAT(CCD.EXPIRY_DATE,'%d-%m-%Y'),'-') EXPIRY_DATE, \n"
                + "IFNULL(DATE_FORMAT(CCD.ENTRY_DATE,'%d-%m-%Y'),'-') 'CERTIFICATE GENERATE DATE', \n"
                + "IFNULL(CCD.ENTRY_BY,'-') 'CERTIFICATE ENTRY BY', CCM.PASSWORD, \n"
                + "IFNULL(CCD.SYS_KEY,'-') SYS_KEY, DATE_FORMAT(CCM.ENTRY_DATE,'%d-%m-%Y') 'CLIENT ENTRY DATE', \n"
                + "CCM.ENTRY_BY 'CLIENT ENTRY BY'\n"
                + "FROM CER_CLIENT_MST CCM\n"
                + "LEFT JOIN CER_CLIENT_DETAIL CCD ON CCD.CLIENT_ID = CCM.CLIENT_ID,(SELECT @SRNO := 0) s");
        if (m.get("CLIENT_ID") != null)
        {
            query.append(" WHERE CCM.CLIENT_ID = :CLIENT_ID");
        }
        query.append(" ORDER BY CCD.EXPIRY_DATE DESC");
        return sqlUtility.getList(FINALIAS, query.toString(), new MapSqlParameterSource(m));
    }

    public String createPassword() throws Exception
    {
        String password = convertToMD5(String.valueOf(System.currentTimeMillis()));
        return password.substring(0, 20);
    }

    public String convertToMD5(String strPassword) throws NoSuchAlgorithmException, UnsupportedEncodingException
    {
        String sesId = "";
        MessageDigest md;
        md = MessageDigest.getInstance("MD5");
        md.update(strPassword.getBytes("iso-8859-1"), 0, strPassword.length());
        sesId = convertToHex(md.digest());
        return sesId;
    }

    public String convertToHex(byte[] data)
    {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++)
        {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do
            {
                if ((0 <= halfbyte) && (halfbyte <= 9))
                {
                    buf.append((char) ('0' + halfbyte));
                }
                else
                {
                    buf.append((char) ('a' + (halfbyte - 10)));
                }
                halfbyte = data[i] & 0x0F;
            }
            while (two_halfs++ < 1);
        }
        return buf.toString();
    }
}
