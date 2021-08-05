<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <title>JSP Page</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <link href="css/header.css" rel="stylesheet" type="text/css" />
        <style type="text/css">
            .ticker {
                font-size: 15px;
                font-weight: normal;                
                font-family: Arial;
/*                 border: 0px solid #DDDDDD;
                border-radius: 5px 5px 5px 5px;
                box-shadow: 0 0 5px #DDDDDD;*/
                height: 18px;
                list-style: none outside none;
                margin: 0;
                overflow: hidden;
                padding: 0;
                width: 98%;
            }
        </style>
        <script type="text/javascript" src="${finlibPath}/jquery/jquery-1.6.1/jquery-1.6.1.min.js"></script>
        <script type="text/javascript" src="javascript/welcome.js"></script>
        <script language="javascript" type="text/javascript">
            var currenttime = "${mydate}"; //Java method of getting server date
            var serverdate = new Date(currenttime);        
            function tick(){
		$('#ticker li:first').slideUp( function () { $(this).appendTo($('#ticker')).slideDown(); });
            }
            setInterval(function(){ tick () }, 10000);
        </script>
    </head>
    <body onload="startTimer();">
        <table align="center" cellpadding="0" cellspacing="0" width="100%">
            <tr height="75" >
                <td valign="middle" class="BgImage_HeaderTable"><img src="images/logo_njindiainvest.gif"  alt="Fin Studio" longdesc="Fin Studio" /></td>
                <td align="right" style="padding-right: 50px" width="70%" class="BgImage_HeaderTable">
                    <ul id="ticker" class="ticker">
                        <c:forEach items="${statistics}" var="list" >
                            <li>${list.STATS_STRING}</li>
                        </c:forEach>
                    </ul></td>
                <td align="right" class="BgImage_HeaderTable" nowrap style="font-size:16px;font-weight:bolder;">
                    FinStudio
                </td>
            </tr>
            <tr class="Line_Header"><td align="center" colspan="100%" nowrap></td></tr>
        </table>
        <table border="0" cellpadding="0" cellspacing="0" width="100%" >
            <tr class="Row_Header">
                <td align="left" height="20">&nbsp;</td>
                <td align="left" nowrap class="clsbtnon" >&nbsp;&nbsp;Welcome : ${emp_name}  </td>
                <td align="right" nowrap class="clsbtnon" colspan="100%" >
                    <span id="servertime"></span>&nbsp;&nbsp;</td>
            </tr>
            <tr class="Line_Header"><td align="center" colspan="100%" nowrap></td></tr>
        </table>
        <table border="0" cellpadding="0" cellspacing="0" width="100%" >
            <tr class="Row_Header">
                <td align="left" id="currentUser" height="20"></td>

                <td align="center" nowrap class="clsbtnon" >&nbsp;&nbsp;
                    <a class="clsbtnon" href="welcome.fin?cmdAction=subMenu&action=development" target="content">Development</a>&nbsp;&nbsp;</td>

                <td align="center" nowrap class="clsbtnon" >&nbsp;&nbsp;
                    <a class="clsbtnon" href="welcome.fin?cmdAction=subMenu&action=monitoring" target="content">Monitoring</a>&nbsp;&nbsp;</td>

                <td align="center" nowrap class="clsbtnon" >&nbsp;&nbsp;
                    <a class="clsbtnon" href="welcome.fin?cmdAction=subMenu&action=autotestingtool" target="content">Auto Testing Tool</a>&nbsp;&nbsp;</td>

                <%--<td align="center" nowrap class="clsbtnon" >&nbsp;&nbsp;
                    <a class="clsbtnon" href="welcome.fin?cmdAction=subMenu&action=demos" target="content">Demos</a>&nbsp;&nbsp;</td>--%>

                <td align="center" nowrap class="clsbtnon" >&nbsp;&nbsp;
                    <a class="clsbtnon" href="logout.fin" target="_top" >Home</a>&nbsp;&nbsp;</td>
            </tr>
            <tr class="Line_Header"><td align="center" colspan="100%" nowrap></td></tr>
        </table>
    </body>
</html>
