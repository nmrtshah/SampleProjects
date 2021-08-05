/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util;

import org.springframework.jdbc.support.rowset.SqlRowSet;
/*//////////////////////////////////////////////////////////////////////////////////
CREATED BY  	:  Ammar Patel
OBJECTIVE   	:  Parse SqlRowSet to Json String
REQUEST ID     	:  10584 - WFMS 2 Phase 1
CREATED DATE    :  19 July, 2010
//////////////////////////////////////////////////////////////////////////////////*/

public class JSONParser
{

    public String Parse(SqlRowSet rs, String CellIDColName) throws Exception
    {
        return parse(rs, CellIDColName);
    }

    public String jsonParser(SqlRowSet rs, String CellIDColName, int Rowcount, int page, int rows) throws Exception
    {
        return parse(rs, CellIDColName, Rowcount, page, rows);
    }

    public String parse(SqlRowSet rs, String cellIdColName) throws Exception
    {

        StringBuffer json = new StringBuffer("");

        // count records

        int totalRecords = 0;
        while (rs.next())
        {
            totalRecords++;
        }

        json.append("{\"page\":1,\"total\":1,\"records\":\"" + totalRecords + "\",\"rows\":[");

        int columncount = rs.getMetaData().getColumnCount();

        rs.beforeFirst();

        while (rs.next())
        {
            json.append("{\"id\":\"");
            json.append(rs.getString(cellIdColName));
            json.append("\",\"cell\":[\"");

            for (int j = 0; j < columncount; j++)
            {
                json.append(rs.getString(j + 1));
                if (columncount != (j + 1));
                {
                    json.append("\",\"");
                }
            }

            json.append("\"]},");
        }
        return json.toString().substring(0, json.length() - 1) + "]}";
    }

    public String parse(SqlRowSet rs, String cellIdColName, int pageNo, int rowsPerPage) throws Exception
    {

        StringBuffer json = new StringBuffer("");

        // count records

        int totalRecords = 0;
        while (rs.next())
        {
            totalRecords++;
        }

        // count total pages

        int totalPages = totalRecords / rowsPerPage;
        if (totalPages % rowsPerPage > 0)
        {
            totalPages++;
        }

        json.append("{\"page\":\"" + pageNo + "\",\"total\":" + totalPages + ",\"records\":\"" + totalRecords + "\",\"rows\":[");

        int columncount = rs.getMetaData().getColumnCount();

        rs.beforeFirst();

        while (rs.next())
        {
            json.append("{\"id\":\"");
            json.append(rs.getString(cellIdColName));
            json.append("\",\"cell\":[\"");

            for (int j = 0; j < columncount; j++)
            {
                json.append(rs.getString(j + 1));
                if (columncount != (j + 1));
                {
                    json.append("\",\"");
                }
            }

            json.append("\"]},");
        }
        return json.toString().substring(0, json.length() - 1) + "]}";
    }

    public String parse(SqlRowSet rs, String cellIdColName, int totalRecords, int pageNo, int rowsPerPage) throws Exception
    {

        StringBuffer json = new StringBuffer("");

        // count total pages

        int totalPages = totalRecords / rowsPerPage;
        if (totalPages % rowsPerPage > 0)
        {
            totalPages++;
        }

        json.append("{\"page\":\"" + pageNo + "\",\"total\":" + totalPages + ",\"records\":\"" + totalRecords + "\",\"rows\":[");

        int columncount = rs.getMetaData().getColumnCount();

        rs.beforeFirst();

        while (rs.next())
        {
            json.append("{\"id\":\"");
            json.append(rs.getString(cellIdColName));
            json.append("\",\"cell\":[\"");

            for (int j = 0; j < columncount; j++)
            {
                json.append(rs.getString(j + 1));
                if (columncount != (j + 1));
                {
                    json.append("\",\"");
                }
            }

            json.append("\"]},");
        }
        return json.toString().substring(0, json.length() - 1) + "]}";
    }
}
