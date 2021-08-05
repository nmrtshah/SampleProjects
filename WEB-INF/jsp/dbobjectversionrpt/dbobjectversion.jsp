<%-- 
    Document   : dbObjectVersion
    Created on : 18 May, 2016, 2:46:58 PM
    Author     : njuser
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="${finlib_path}/resource/main_offline.css">
        <link rel="stylesheet" type="text/css" href="${finlib_path}/resource/jquery.tablesorter.min.css">
        <link rel="stylesheet" type="text/css" href="${finlib_path}/jquery/jquery-ui-1.8.13.custom/css/smoothness/jquery-ui-1.8.13.custom.css">
        <link rel="stylesheet" type="text/css" href="${finlib_path}/dhtmlxSuite/dhtmlxPopup/codebase/skins/dhtmlxpopup_dhx_web.css">
        <script type="text/javascript" src="${finlib_path}/jquery/jquery-1.6.1/jquery-1.6.1.min.js"></script>
        <script type="text/javascript" src="${finlib_path}/resource/jquery.tablesorter.min.js"></script>
        <script type="text/javascript" src="${finlib_path}/resource/common_functions.min.js"></script>
        <script type="text/javascript" src="${finlib_path}/jquery/jquery-ui-1.8.13.custom/js/jquery-ui-1.8.13.custom.min.js"></script>
        <script type="text/javascript" src="${finlib_path}/resource/validate.js"></script>
        <script type="text/javascript" src="${finlib_path}/resource/validate_date.js"></script>
        <script type="text/javascript" src="${finlib_path}/jquery/jquery-ui-1.8.13.custom/development-bundle/ui/jquery.ui.datepicker.js"></script>
        <script type="text/javascript" src="${finlib_path}/resource/serverCombo.js"></script>
        <script type="text/javascript" src="${finlib_path}/resource/ajax.js"></script>
        <script type="text/javascript" src="${finlib_path}/resource/finstudio-utility-functions.js"></script>
        <script type="text/javascript" src="${finlib_path}/resource/validate.js"></script>
        <script type="text/javascript" src="${finlib_path}/resource/serverCombo.js"></script>
        <script type="text/javascript" src="${finlib_path}/resource/ajax.js"></script>
        <script type="text/javascript" src="javascript/dbobjversionrpt.js" ></script>
        <link rel="stylesheet" type="text/css" href="css/diffview.css"/>
        <script type="text/javascript" src="javascript/difflib.js"></script>
        <script type="text/javascript" src="javascript/diffview.js"></script>
        <style type="text/css">
            .top {text-align: center;}
            #diffoutput {width: 100%;}
            .textInput {display: block;width: 49%;float: left;}
            .spacer {margin-left: 10px;}
            .Diff_Row_color{background-color: #FFBAB9;}
            .Diff_Column_color{background-color: #FF6D6B;}
        </style>
        <title>Database Object Version Report</title>
    </script>
</head>
<body onload="javascript : onloadMyFunction();">
    <div class="container">
        <div class="content">
            <jsp:include page="dbobjectversionmenu.jsp"></jsp:include>
        </div>
    </div>
</body>
</html>
