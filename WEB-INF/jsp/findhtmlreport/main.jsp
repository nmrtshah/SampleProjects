<%-- 
    Document   : main
    Created on : Feb 22, 2012, 12:01:11 PM
    Author     : njuser
--%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <title>Fin Dhtml Report</title>
        <meta http-equiv="Content-Type" content="text/html" />
        <link rel="stylesheet" type="text/css" href="${finlibPath}/resource/main_offline.css" />
        <link rel="stylesheet" type="text/css" href="${finlibPath}/jquery/jquery.jqGrid-3.8.2/css/ui.jqgrid.css"/>
        <link rel="stylesheet" type="text/css" href="${finlibPath}/jquery/jquery-ui-1.8.13.custom/css/smoothness/jquery-ui-1.8.13.custom.css"/>
        <script type="text/javascript" src="${finlibPath}/jquery/jquery-1.6.1/jquery-1.6.1.min.js"></script>
        <script type="text/javascript" src="${finlibPath}/resource/common_functions.min.js"></script>
        <script type="text/javascript" src="${finlibPath}/jquery/jquery-ui-1.8.13.custom/js/jquery-ui-1.8.13.custom.min.js"></script>
        <script type="text/javascript" src="${finlibPath}/jquery/jquery.jqGrid-3.8.2/js/i18n/grid.locale-en.js"></script>
        <script type="text/javascript" src="${finlibPath}/jquery/jquery.jqGrid-3.8.2/js/jquery.jqGrid.min.js"></script>
        <script type="text/javascript" src="${finlibPath}/jquery/jquery-ui-1.8.13.custom/development-bundle/ui/jquery.ui.core.js"></script>
        <script type="text/javascript" src="${finlibPath}/resource/validate.js"></script>
        <script type="text/javascript" src="${finlibPath}/resource/validate_date.js"></script>
        <script type="text/javascript" src="${finlibPath}/resource/serverCombo.js"></script>
        <script type="text/javascript" src="javascript/ajaxfunctions.js"></script>
        <script type="text/javascript" src="javascript/FinDhtmlReportValidation.js"></script>
        <script type="text/javascript" src="${finlibPath}/resource/ajax.js"></script>
    </head>
    <body onload="javascript:loadAll();">
        <div class="container">
            <div class="content">
                <jsp:include page="ProjectModuleSpecification.jsp"></jsp:include>
            </div>
            <jsp:include page="bottom.jsp"></jsp:include>
        </div>        
    </body>
</html>
