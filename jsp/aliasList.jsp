<%-- 
    Document   : aliasList
    Created on : Apr 29, 2011, 12:15:14 PM
    Author     : njuser
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <title>Alias List JSP</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <link href="../css/main.css" rel="stylesheet" type="text/css"/>
        <script type="text/javascript" language="javascript">
            function show(id)
            {
                document.getElementById("div1").innerHTML = document.getElementById(id).innerHTML;
                    }
        </script>
    </head>
    <body>
        &nbsp;
        <div align="center">Alias Name : Name used to get connection.</div>
        <div align="center">For Example DBConnManager1.getFinConn("mfund")</div>
        &nbsp;
        <div align="center">Total Connections ${totalAlias}</div>
        &nbsp;
        <table align="center" border="1">
            <thead class="tbl_h1_bg">
                <tr bgcolor="grey">
                    <td align="center">Alias Name</td>
                    <td align="center">Schema Name</td>
                    <td align="center">Status</td>
                </tr>
            </thead>
            <tbody align="left">
                ${tablecode}
            </tbody>
        </table>
        <div id="div1"></div>
    </body>
</html>
