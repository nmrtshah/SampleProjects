<%-- 
    Document   : etljobreport
    Created on : 25 Apr, 2017, 3:13:32 PM
    Author     : Siddharth Patel
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ETL Job Report</title>
        <meta http-equiv="Content-Type" content="text/html" />
        <link rel="stylesheet" type="text/css" href="${finlibPath}/resource/main_offline.css" />
        <script type="text/javascript" src="${finlibPath}/jquery/jquery-1.6.1/jquery-1.6.1.min.js"></script>
        <script type="text/javascript" src="${finlibPath}/resource/common_functions.min.js"></script>
        <script type="text/javascript" src="${finlibPath}/jquery/jquery-ui-1.8.13.custom/js/jquery-ui-1.8.13.custom.min.js"></script>
        <script type="text/javascript" src="${finlibPath}/jquery/jquery-ui-1.8.13.custom/development-bundle/ui/jquery.ui.core.js"></script>
        <script type="text/javascript" src="${finlibPath}/resource/serverCombo.js"></script>
        <script type="text/javascript" src="javascript/ajaxfunctions.js"></script>
        <script type="text/javascript" src="javascript/ETLJobList.js"></script>
        <script type="text/javascript" src="${finlibPath}/resource/ajax.js"></script>
        <link rel="stylesheet" type="text/css" href="${finlibPath}/dhtmlxSuite4/sources/dhtmlxToolbar/codebase/skins/dhtmlxtoolbar_dhx_web.css">
        <link rel="stylesheet" type="text/css" href="${finlibPath}/dhtmlxSuite4/codebase/dhtmlx.css">
        <script type="text/javascript" src="${finlibPath}/dhtmlxSuite4/codebase/dhtmlx.js"></script>
    </head>
    <body onload="onloadETLGrid();">
        <div class="container">
            <div class="content">
                <div id="gridbox"></div>
                <div valign="top" id="pagingArea" align="center" style="display:block;">
                </div>
                <div  valign="top" id="recinfoArea" align="center"  style="display:block;">
                </div>
            </div>
        </div>
    </body>
</html>