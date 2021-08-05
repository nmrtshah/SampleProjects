<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Service List</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <link href="../css/main.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <table border="1" align="center">
            <thead>
                <tr class="tbl_h1_bg">
                    <td colspan="100%">List of Services</td>
                </tr>
                <tr class="tbl_h1_bg">
                    <td>Service Name / Attachment</td>
                    <td>URL Dev</td>
                    <td>URL Test</td>
                    <td>URL Prod</td>
                </tr>
            </thead>
            <tbody align="left">
                <%
                    com.finlogic.util.finreport.DirectConnection dc = new com.finlogic.util.finreport.DirectConnection();
                    java.sql.Connection c = null;
                    try
                    {
                        c = dc.getConnection("dev_db2_mysql");
                        com.finlogic.util.persistence.SQLConnService s = new com.finlogic.util.persistence.SQLConnService();
                        String sqlQuery = "SELECT DISTINCT SERVICEID,ADDRESS FROM "
                                + "( "
                                + "SELECT ENTRYID,ENTRYVALUE SERVICEID FROM finstudio.CONFIG_AUDIT "
                                + "WHERE ENTRYFIELD='serviceId' "
                                + "AND ENTRYTYPE IN ('ws_njapps.nj','ws_njimage.nj','ws_howeb2.nj') "
                                + ") X1 "
                                + "INNER JOIN "
                                + "( "
                                + "SELECT ENTRYID,replace(ENTRYVALUE,'?wsdl','') ADDRESS FROM finstudio.CONFIG_AUDIT "
                                + "WHERE ENTRYFIELD='address' "
                                + "AND ENTRYTYPE IN ('ws_njapps.nj','ws_njimage.nj','ws_howeb2.nj') "
                                + ") X2 ON X1.ENTRYID=X2.ENTRYID "
                                + "ORDER BY ADDRESS";

                        sqlQuery = "SELECT T1.SERVICEID SERVICEID,D_ADDRESS,T_ADDRESS,P_ADDRESS FROM "
                                + "( "
                                + "    SELECT DISTINCT SERVICEID,ADDRESS D_ADDRESS FROM "
                                + "    ( "
                                + "    SELECT ENTRYID,ENTRYVALUE SERVICEID FROM finstudio.CONFIG_AUDIT "
                                + "    WHERE ENTRYFIELD='serviceId' "
                                + "    AND ENTRYTYPE LIKE 'ws_devhoweb_.nj' "
                                + "    ) X1 "
                                + "    INNER JOIN "
                                + "    ( "
                                + "    SELECT ENTRYID,replace(ENTRYVALUE,'?wsdl','') ADDRESS FROM finstudio.CONFIG_AUDIT "
                                + "    WHERE ENTRYFIELD='address' "
                                + "    AND ENTRYTYPE LIKE 'ws_devhoweb_.nj' "
                                + "    ) X2 ON X1.ENTRYID=X2.ENTRYID "
                                + ") T1 "
                                + "LEFT JOIN "
                                + "( "
                                + "    SELECT DISTINCT SERVICEID,ADDRESS T_ADDRESS FROM "
                                + "    ( "
                                + "    SELECT ENTRYID,ENTRYVALUE SERVICEID FROM finstudio.CONFIG_AUDIT "
                                + "    WHERE ENTRYFIELD='serviceId' "
                                + "    AND ENTRYTYPE LIKE 'ws_testhoweb_.nj' "
                                + "    ) X3 "
                                + "    INNER JOIN "
                                + "    ("
                                + "    SELECT ENTRYID,replace(ENTRYVALUE,'?wsdl','') ADDRESS FROM finstudio.CONFIG_AUDIT "
                                + "    WHERE ENTRYFIELD='address' "
                                + "    AND ENTRYTYPE LIKE 'ws_testhoweb_.nj' "
                                + "    ) X4 ON X3.ENTRYID=X4.ENTRYID "
                                + ") T2 ON T1.SERVICEID=T2.SERVICEID "
                                + "LEFT JOIN "
                                + "( "
                                + "    SELECT DISTINCT SERVICEID,ADDRESS P_ADDRESS FROM "
                                + "    ( "
                                + "    SELECT ENTRYID,ENTRYVALUE SERVICEID FROM finstudio.CONFIG_AUDIT "
                                + "    WHERE ENTRYFIELD='serviceId' "
                                + "    AND ENTRYTYPE IN ('ws_njapps.nj','ws_njimage.nj','ws_howeb2.nj') "
                                + "    ) X5 "
                                + "    INNER JOIN "
                                + "    ( "
                                + "    SELECT ENTRYID,replace(ENTRYVALUE,'?wsdl','') ADDRESS FROM finstudio.CONFIG_AUDIT "
                                + "    WHERE ENTRYFIELD='address' "
                                + "    AND ENTRYTYPE IN ('ws_njapps.nj','ws_njimage.nj','ws_howeb2.nj') "
                                + "    ) X6 ON X5.ENTRYID=X6.ENTRYID "
                                + ") T3 ON T1.SERVICEID=T3.SERVICEID "
                                + "ORDER BY SERVICEID";
                        java.util.List l = s.getList(c, sqlQuery);
                        request.setAttribute("res", l);

                %>
                <c:forEach items="${res}" var="row">
                    <tr>
                        <c:set var="VSERVICEID">${row.SERVICEID}</c:set>
                        <%
                            java.io.File f = new java.io.File(finpack.FinPack.getProperty("tomcat1_path")
                                    + "/webapps/finstudio/documents/ws/"
                                    + (String) pageContext.getAttribute("VSERVICEID") + ".ods");
                            if (f.exists())
                            {
                        %>
                        <td><a href="../documents/ws/${row.SERVICEID}.ods" target="_blank">${row.SERVICEID}</a></td>
                        <%                        }
                        else
                        {
                        %>
                        <td>${row.SERVICEID}</td>
                        <%                            }
                        %>
                        <td><a href="${row.D_ADDRESS}" target="_blank">${row.D_ADDRESS}</a></td>
                        <td><a href="${row.T_ADDRESS}" target="_blank">${row.T_ADDRESS}</a></td>
                        <td><a href="${row.P_ADDRESS}" target="_blank">${row.P_ADDRESS}</a></td>
                    </tr>
                </c:forEach>
                <%
                    }
                    finally
                    {
                        try
                        {
                            c.close();
                        }
                        catch (Exception e)
                        {
                        }
                    }
                %>
            </tbody>
        </table>
        <br><br>
    </body>
</html>
