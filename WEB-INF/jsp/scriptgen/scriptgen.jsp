<%-- 
    Document   : testscriptgen
    Created on : Mar 5, 2012, 2:24:24 PM
    Author     : njuser
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <title>Test Script Generator</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <link rel="stylesheet" href="css/header.css" type="text/css">        
        <link rel="stylesheet" type="text/css" href="${finlibPath}/jquery/jquery-ui-1.8.13.custom/css/smoothness/jquery-ui-1.8.13.custom.css"/>
        <script type="text/javascript" src="${finlibPath}/jquery/jquery-1.6.1/jquery-1.6.1.min.js"></script>
        <script type="text/javascript" src="${finlibPath}/resource/common_functions.min.js"></script>        
        <script type="text/javascript" src="${finlibPath}/jquery/jquery-ui-1.8.13.custom/js/jquery-ui-1.8.13.custom.min.js"></script>
        <script type="text/javascript" src="${finlibPath}/jquery/jquery-ui-1.8.13.custom/development-bundle/ui/jquery.ui.datepicker.js"></script>
        <script type="text/javascript" src="./javascript/ajaxfunctions.js"></script>
        <script type="text/javascript" src="./javascript/validate.js"></script>
        <script type="text/javascript" src="${finlibPath}/resource/validate_date.js"></script>
        <script type="text/javascript" src="./javascript/testscriptgen.js"></script>
        <script type="text/javascript" src="${finlibPath}/resource/finstudio-utility-functions.js"></script>
    </head>
    <body onload="Addload('${pageLoad}')">
        <form action="scriptgen.fin?cmdAction=showRequest" id="scriptgenform" name="scriptgenform" method="POST">                               
            <table width="100%" cellpadding="0" cellspacing="0" align="center">
                <tbody>
                    <tr>
                        <td valign="top">
                            <h3 align="center" style="font-family:arial;color:#670507">Script Generator</h3>
                        </td>
                    </tr>
                    <tr>
                        <td align="center">
                            <input type="button" id="btnAdd" name="btnAdd" onclick="showTab('Add')" value="Add" />
                            <input type="button" id="btnView" name="btnView" onclick="showTab('View')" value="View" />
                        </td>
                    </tr>
                </tbody>
            </table>    
        </form>        
        <div id="divContent">        
        </div>  
    </body>
</html>

