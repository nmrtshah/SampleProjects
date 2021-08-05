/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.persistence.finstudio;

import com.finlogic.util.persistence.ProcedureParam;
import com.mockrunner.mock.jdbc.MockResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.ResultSetWrappingSqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSet;

/**
 *
 * @author Sonam Patel
 */
public class SQLTranUtility
{

    private Connection conn = null;

    /**
     * Develop By Sonam Patel : This function is used to set connection
     *
     * @param sqlQuery Query
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public void openConn(final Connection connection) throws ClassNotFoundException, SQLException
    {
        conn = connection;
        conn.setAutoCommit(false);
    }

    public Connection getConnection()
    {
        return conn;
    }

    /**
     *
     * Develop By Sonam Patel : This function is used to commit changes
     *
     *
     * @throws SQLException
     */
    public void commitChanges() throws SQLException
    {
        conn.commit();
    }

    /**
     *
     * Develop By Sonam Patel : This function is used to rollback changes
     *
     *
     * @throws SQLException
     */
    public void rollbackChanges() throws SQLException
    {
        conn.rollback();
    }

    /**
     *
     * Develop By Sonam Patel : This function is used to close connection
     *
     *
     * @throws SQLException
     */
    public void closeConn() throws SQLException
    {
        conn.close();
    }

    /**
     *
     * Develop By Sonam Patel : This function is used to fire insert, update and
     * delete statements. <br>
     *
     * @param sqlQuery : SQL query to fire.
     * @return int : either (1) the row count for SQL Data Manipulation Language
     * (DML) statements or (2) 0 for SQL statements that return nothing
     */
    public int persist(final String sqlQuery)
    {
        DataSource ds = new SingleConnectionDataSource(conn, true);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
        return jdbcTemplate.update(sqlQuery);
    }

    /**
     *
     * Develop By Sonam Patel : This function is used to fire insert, update and
     * delete statements. <br>
     *
     *
     * @param sqlQuery : SQL query to fire.
     * @param paramSource : Bind variables for query.
     * @return int : either (1) the row count for SQL Data Manipulation Language
     * (DML) statements or (2) 0 for SQL statements that return nothing
     */
    public int persist(final String sqlQuery, final SqlParameterSource paramSource)
    {
        DataSource ds = new SingleConnectionDataSource(conn, true);
        NamedParameterJdbcTemplate npJdbcTemplate = new NamedParameterJdbcTemplate(ds);
        return npJdbcTemplate.update(sqlQuery, paramSource);
    }

    /**
     *
     * Develop By Sonam Patel : This function is used to fire insert statements
     * from which Key can be returned. <br>
     *
     *
     * @param sqlQuery : SQL query to fire.
     * @param generatedKeys : Auto Generated Keys
     * @param paramSource : Bind variables for query.
     * @return int : either (1) the row count for SQL Data Manipulation Language
     * (DML) statements or (2) 0 for SQL statements that return nothing
     */
    public int persist(final String sqlQuery, KeyHolder generatedKeys, final SqlParameterSource paramSource)
    {
        DataSource ds = new SingleConnectionDataSource(conn, true);
        NamedParameterJdbcTemplate npJdbcTemplate = new NamedParameterJdbcTemplate(ds);
        return npJdbcTemplate.update(sqlQuery, paramSource, generatedKeys);
    }

    /**
     *
     * Develop By Sonam Patel : This function is used to return single value of
     * type int. <br>
     *
     *
     * @param sqlQuery : SQL query to fire.
     * @return int : Integer result of given query.
     */
    public int getInt(final String sqlQuery)
    {
        DataSource ds = new SingleConnectionDataSource(conn, true);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
        //return jdbcTemplate.queryForInt(sqlQuery);
        try {
            return ((Integer) jdbcTemplate.queryForObject(sqlQuery, Integer.class)).intValue();
        } catch (EmptyResultDataAccessException | NullPointerException erdae) {
        }
        return 0;
    }

    /**
     *
     * Develop By Sonam Patel : This function is used to return single value of
     * type int. <br>
     *
     *
     * @param sqlQuery : SQL query to fire.
     * @param paramSource : Bind variables for query.
     * @return int : Integer result of given query.
     */
    public int getInt(final String sqlQuery, final SqlParameterSource paramSource)
    {
        DataSource ds = new SingleConnectionDataSource(conn, true);
        NamedParameterJdbcTemplate npJdbcTemplate = new NamedParameterJdbcTemplate(ds);
        //return npJdbcTemplate.queryForInt(sqlQuery, paramSource);
         try {
            return ((Integer) npJdbcTemplate.queryForObject(sqlQuery, paramSource, Integer.class)).intValue();
        } catch (EmptyResultDataAccessException | NullPointerException erdae) {
        }
        return 0;
    }

    /**
     *
     * Develop By Sonam Patel : This function is used to return single value of
     * type long. <br>
     *
     *
     * @param sqlQuery : SQL query to fire.
     * @return long : Long result of given query.
     */
    public long getLong(final String sqlQuery)
    {
        DataSource ds = new SingleConnectionDataSource(conn, true);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
        //return jdbcTemplate.queryForLong(sqlQuery);
         try {
            return ((Long) jdbcTemplate.queryForObject(sqlQuery, Long.class)).longValue();
        } catch (EmptyResultDataAccessException | NullPointerException erdae) {
        }
        return 0L;
    }

    /**
     *
     * Develop By Sonam Patel : This function is used to return single value of
     * type long. <br>
     *
     *
     * @param sqlQuery : SQL query to fire.
     * @param paramSource : Bind variables for query.
     * @return long : Long result of given query.
     */
    public long getLong(final String sqlQuery, final SqlParameterSource paramSource)
    {
        DataSource ds = new SingleConnectionDataSource(conn, true);
        NamedParameterJdbcTemplate npJdbcTemplate = new NamedParameterJdbcTemplate(ds);
        try {
            return ((Long) npJdbcTemplate.queryForObject(sqlQuery, paramSource, Long.class)).longValue();
        } catch (EmptyResultDataAccessException | NullPointerException erdae) {
        }
        return 0L;
        //return npJdbcTemplate.queryForLong(sqlQuery, paramSource);
    }

    /**
     *
     * Develop By Sonam Patel : This function is used to return single value of
     * type String. <br>
     *
     *
     * @param sqlQuery : SQL query to fire.
     * @return String : String result of given query.
     */
    public String getString(final String sqlQuery)
    {
        try
        {
            DataSource ds = new SingleConnectionDataSource(conn, true);
            JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
            return (String) jdbcTemplate.queryForObject(sqlQuery, String.class);
        }
        catch (EmptyResultDataAccessException e)
        {
            return null;
        }
    }

    /**
     *
     * Develop By Sonam Patel : This function is used to return single value of
     * type String. <br>
     *
     *
     * @param sqlQuery : SQL query to fire.
     * @param paramSource : Bind variables for query.
     * @return String : String result of given query.
     */
    public String getString(final String sqlQuery, final SqlParameterSource paramSource)
    {
        try
        {
            DataSource ds = new SingleConnectionDataSource(conn, true);
            NamedParameterJdbcTemplate npJdbcTemplate = new NamedParameterJdbcTemplate(ds);
            return (String) npJdbcTemplate.queryForObject(sqlQuery, paramSource, String.class);
        }
        catch (EmptyResultDataAccessException e)
        {
            return null;
        }
    }

    /**
     *
     * Develop By Sonam Patel : This function is used to return List. <br>
     *
     *
     * @param sqlQuery : SQL query to fire.
     * @return List : List result of given query.
     */
    public List getList(final String sqlQuery)
    {
        DataSource ds = new SingleConnectionDataSource(conn, true);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
        return jdbcTemplate.queryForList(sqlQuery);
    }

    /**
     *
     * Develop By Sonam Patel : This function is used to return List. <br>
     *
     *
     * @param sqlQuery : SQL query to fire.
     * @param paramSource : Bind variables for query.
     * @return List : List result of given query.
     */
    public List getList(final String sqlQuery, final SqlParameterSource paramSource)
    {
        DataSource ds = new SingleConnectionDataSource(conn, true);
        NamedParameterJdbcTemplate npJdbcTemplate = new NamedParameterJdbcTemplate(ds);
        return npJdbcTemplate.queryForList(sqlQuery, paramSource);
    }

    /**
     *
     * Develop By Sonam Patel : This function is used to return SqlRowSet. <br>
     *
     *
     * @param sqlQuery : SQL query to fire.
     * @return SqlRowSet : SqlRowSet of given query.
     */
    public SqlRowSet getRowSet(final String sqlQuery)
    {
        try
        {
            DataSource ds = new SingleConnectionDataSource(conn, true);
            JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
            SqlRowSet srs = jdbcTemplate.queryForRowSet(sqlQuery);
            return srs;
        }
        catch (BadSqlGrammarException ex)
        {
            // code to solve integrationsolution problem
            if (ex.getMessage().contains("Table") && ex.getMessage().contains("doesn't exist"))
            {
                List l = getList(sqlQuery);
                MockResultSet mockResultSet = new MockResultSet("myResultSet");
                Map map = null;

                int size = l.size();
                for (int i = 0; i < size; i++)
                {
                    map = (Map) l.get(i);
                    Object[] cells = map.values().toArray();
                    mockResultSet.addRow(cells);
                }

                Object[] cols = map.keySet().toArray();
                for (int i = 0; i < cols.length; i++)
                {
                    mockResultSet.addColumn((String) cols[i]);
                }
                SqlRowSet srs = new ResultSetWrappingSqlRowSet(mockResultSet);
                return srs;
            }
            else
            {
                throw new IllegalArgumentException(ex);
            }
        }
    }

    /**
     *
     * Develop By Sonam Patel : This function is used to return SqlRowSet. <br>
     *
     *
     * @param sqlQuery : SQL query to fire.
     * @param paramSource : Bind variables for query.
     * @return SqlRowSet : SqlRowSet of given query.
     */
    public SqlRowSet getRowSet(final String sqlQuery, final SqlParameterSource paramSource)
    {
        try
        {
            DataSource ds = new SingleConnectionDataSource(conn, true);
            NamedParameterJdbcTemplate npJdbcTemplate = new NamedParameterJdbcTemplate(ds);
            SqlRowSet srs = npJdbcTemplate.queryForRowSet(sqlQuery, paramSource);
            return srs;
        }
        catch (BadSqlGrammarException ex)
        {
            // code to solve integrationsolution problem
            if (ex.getMessage().contains("Table") && ex.getMessage().contains("doesn't exist"))
            {
                List l = getList(sqlQuery, paramSource);
                MockResultSet mockResultSet = new MockResultSet("myResultSet");
                Map map = null;

                int size = l.size();
                for (int i = 0; i < size; i++)
                {
                    map = (Map) l.get(i);
                    Object[] cells = map.values().toArray();
                    mockResultSet.addRow(cells);
                }

                Object[] cols = map.keySet().toArray();
                for (int i = 0; i < cols.length; i++)
                {
                    mockResultSet.addColumn((String) cols[i]);
                }
                SqlRowSet srs = new ResultSetWrappingSqlRowSet(mockResultSet);
                return srs;
            }
            else
            {
                throw new IllegalArgumentException(ex);
            }
        }
    }

    /**
     *
     * Develop By Sonam Patel : This function is used to call Oracle store
     * Procedure. <br>
     *
     *
     * @param procName : Procedure name to call.
     * @param paramValues : Parameter values to pass.
     * @param params : Parameters information object.
     * @return Map : out parameters, if any. <br><br> <b>Example:</b><br>
     * <u>Imports</u> <br><br>import java.sql.Types; <br>import java.util.Map;
     * <br>import java.util.Map; <br>import
     * com.finlogic.util.persistence.ProcedureParam; <br>import
     * com.finlogic.util.persistence.SQLUtility; <br><br><u>Code</u> <br><br>Map
     * paramValues=new HashMap(); <br>paramValues.put("P1", "Test Value"); <br>
     * <br>ProcedureParam params[]=new ProcedureParam[1]; <br>params[0]=new
     * ProcedureParam(); <br>params[0].setParamDataType(Types.VARCHAR);
     * <br>params[0].setParamInOutType("IN"); <br>params[0].setParamName("P1");
     * <br> <br>SQLUtility sqlUtility = new SQLUtility(); <br>Map results =
     * sqlUtility.callProcedure("mftran","TEST2",paramValues,params);
     */
    public Map callProcedure(final String procName, final Map paramValues, final ProcedureParam params[])
    {
        CallStoredProcedure sproc = new CallStoredProcedure();
        DataSource ds = new SingleConnectionDataSource(conn, true);
        return sproc.execute(ds, procName, paramValues, params);
    }

    private class CallStoredProcedure extends StoredProcedure
    {

        public Map execute(DataSource ds, String procSQL, Map paramValues, ProcedureParam params[])
        {
            setDataSource(ds);
            setSql(procSQL);

            if (params != null)
            {
                for (int i = 0; i < params.length; i++)
                {
                    if (params[i].getParamInOutType().equalsIgnoreCase("IN"))
                    {
                        declareParameter(new SqlParameter(params[i].getParamName(), params[i].getParamDataType()));
                    }
                    else if (params[i].getParamInOutType().equalsIgnoreCase("OUT"))
                    {
                        declareParameter(new SqlOutParameter(params[i].getParamName(), params[i].getParamDataType()));
                    }
                    else if (params[i].getParamInOutType().equalsIgnoreCase("INOUT"))
                    {
                        declareParameter(new SqlInOutParameter(params[i].getParamName(), params[i].getParamDataType()));
                    }
                }
            }
            compile();
            return execute(paramValues);
        }
    }
}
