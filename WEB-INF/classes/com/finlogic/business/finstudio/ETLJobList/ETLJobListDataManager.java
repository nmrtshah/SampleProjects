/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.business.finstudio.ETLJobList;

import com.finlogic.util.persistence.SQLUtility;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;

/**
 *
 * @author Siddharth Patel
 */
public class ETLJobListDataManager
{

    private static final String ALIASNAME = "njindiainvest_offline_dbadmin";
    private final SQLUtility sqlUtility = new SQLUtility();

    public List getGirdData() throws ClassNotFoundException, SQLException
    {
        StringBuilder query = new StringBuilder();
        query.append("  SELECT *  FROM dbadmin.ETLDATAUPLOAD order by SRNO");
        return sqlUtility.getList(ALIASNAME, query.toString());
    }

    public int insertETLJob(ETLJobListEntityBean entityBean) throws ClassNotFoundException, SQLException
    {
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO dbadmin.ETLDATAUPLOAD\n"
                + " ( TABLENAME, FROMSERVER, TOSERVER, FROMSCHEMA, TO_TABLE, TOSCHEMA, KJB, KTR, PATHS, CRONFILE, "
                + " MAINJOB, TYPE, CRONTAB )\n"
                + " VALUES( :tableName, :fromServer, :toServer, :fromSchema, \n"
                + " :toTable, :toSchema, :kjb, :ktr, :paths, :cronFile, :mainJob, \n"
                + " :type, :cronTab )");
        return sqlUtility.persist(ALIASNAME, query.toString(), new BeanPropertySqlParameterSource(entityBean));

    }
}
