<%-- 
    Document   : main
    Created on : Feb 22, 2012, 12:01:11 PM
    Author     : njuser
--%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <title>FinMaster</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <link rel="stylesheet" type="text/css" href="${finlib_path}/resource/main_offline.css" />
        <script type="text/javascript" src="${finlib_path}/resource/validate.js"></script>
        <script type="text/javascript" src="${finlib_path}/resource/validate_date.js"></script>
        <script type="text/javascript" src="${finlib_path}/resource/serverCombo.js"></script>
        <script type="text/javascript" src="${finlib_path}/jquery/jquery-1.6.1/jquery-1.6.1.min.js"></script>
        <script type="text/javascript" src="${finlib_path}/jquery/jquery-ui-1.8.13.custom/js/jquery-ui-1.8.13.custom.min.js"></script>

        <link rel="stylesheet" type="text/css" href="${finlib_path}/jquery/jquery.jqGrid-3.8.2/css/ui.jqgrid.css"/>
        <script type="text/javascript" src="${finlib_path}/jquery/jquery.jqGrid-3.8.2/js/i18n/grid.locale-en.js"></script>
        <script type="text/javascript" src="${finlib_path}/jquery/jquery.jqGrid-3.8.2/js/jquery.jqGrid.min.js"></script>

        <link rel="stylesheet" type="text/css" href="${finlib_path}/jquery/jquery-ui-1.8.13.custom/css/smoothness/jquery-ui-1.8.13.custom.css"/>
        <script type="text/javascript" src="${finlib_path}/jquery/jquery-ui-1.8.13.custom/development-bundle/ui/jquery.ui.core.js"></script>
        <script type="text/javascript" src="${finlib_path}/jquery/jquery-ui-1.8.13.custom/development-bundle/ui/jquery.ui.datepicker.js"></script>
        <script type="text/javascript" src="${finlib_path}/resource/common_functions.js"></script>

        <link rel="stylesheet" type="text/css" href="${finlib_path}/jquery/jquery-ui-multiselect-widget/jquery.multiselect.css" />
        <link rel="stylesheet" type="text/css" href="${finlib_path}/jquery/jquery-ui-multiselect-widget/jquery.multiselect.filter.css"/>

        <script type="text/javascript" src="${finlib_path}/jquery/jquery-ui-multiselect-widget/src/jquery.multiselect.js"></script>
        <script type="text/javascript" src="${finlib_path}/jquery/jquery-ui-multiselect-widget/src/jquery.multiselect.filter.js"></script>
        <script type="text/javascript" src="${finlib_path}/jquery/jquery-ui-multiselect-widget/src/jquery.multiselect.filter.min.js"></script>

        <script type="text/javascript" src="${finlib_path}/resource/ajax.js"></script>
        <script type="text/javascript" src="javascript/ajaxfunctions.js"></script>
        <script type="text/javascript" src="javascript/FinMasterValidations.js"></script>
    </head>
    <body onload="onloadMyFunction();">
        <div class="container">
            <div class="content">
                <jsp:include page="ProjectMasterSpecification.jsp"></jsp:include>
            </div>
            <jsp:include page="Bottom.jsp"></jsp:include>
        </div>
    </body>
</html>
