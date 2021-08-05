<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <title>Report-Menu</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" >
        <link rel="stylesheet" type="text/css" href="${finlibPath}/resource/main_offline.css" >
        <%--DhtmlxGrid--%>
        <link rel="stylesheet" type="text/css" href="${finlibPath}/dhtmlxSuite/dhtmlxGrid/codebase/dhtmlxgrid.css">
        <link rel="stylesheet" type="text/css" href="${finlibPath}/dhtmlxSuite/dhtmlxGrid/codebase/skins/dhtmlxgrid_dhx_web.css">
        <link rel="stylesheet" type="text/css" href="${finlibPath}/dhtmlxSuite/dhtmlxToolbar/codebase/skins/dhtmlxtoolbar_dhx_web.css">
        <link rel="STYLESHEET" type="text/css" href="${finlibPath}/dhtmlxSuite/dhtmlxChart/codebase/dhtmlxchart.css">
        <script type="text/javascript" src="${finlibPath}/dhtmlxSuite/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
        <script type="text/javascript" src="${finlibPath}/dhtmlxSuite/dhtmlxGrid/codebase/dhtmlxcommonfunctions.js"></script>
        <script type="text/javascript" src="${finlibPath}/dhtmlxSuite/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
        <script type="text/javascript" src="${finlibPath}/dhtmlxSuite/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
        <%--For splitting,serial number,grouping--%>
        <script type="text/javascript" src="${finlibPath}/dhtmlxSuite/dhtmlxGrid/codebase/ext/dhtmlxgrid_splt.js"></script>
        <%--For PDF & Excel--%>
        <script type="text/javascript" src="${finlibPath}/dhtmlxSuite/dhtmlxGrid/codebase/ext/dhtmlxgrid_export.js"></script>
        <%--Paging--%>
        <script type="text/javascript" src="${finlibPath}/dhtmlxSuite/dhtmlxToolbar/codebase/dhtmlxtoolbar.js"></script>
        <script type="text/javascript" src="${finlibPath}/dhtmlxSuite/dhtmlxGrid/codebase/ext/dhtmlxgrid_pgn.js"></script>
        <%--For serial Number--%>
        <script type="text/javascript" src="${finlibPath}/dhtmlxSuite/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_cntr.js"></script>
        <%--For printable view--%>
        <script type="text/javascript" src="${finlibPath}/dhtmlxSuite/dhtmlxGrid/codebase/ext/dhtmlxgrid_nxml.js"></script>
        <%--Drag n Drop--%>
        <script type="text/javascript" src="${finlibPath}/dhtmlxSuite/dhtmlxGrid/codebase/ext/dhtmlxgrid_drag.js"></script>
        <%--Move Columns--%>
        <script type="text/javascript" src="${finlibPath}/dhtmlxSuite/dhtmlxGrid/codebase/ext/dhtmlxgrid_mcol.js"></script>
        <script type="text/javascript" src="${finlibPath}/dhtmlxSuite/dhtmlxGrid/codebase/ext/dhtmlxgrid_post.js"></script>

        <script type="text/javascript" src="${finlibPath}/jquery/jquery-1.6.1/jquery-1.6.1.min.js"></script>
        <link rel="stylesheet" type="text/css" href="${finlibPath}/jquery/jquery-ui-1.8.13.custom/css/smoothness/jquery-ui-1.8.13.custom.css"/>
        <script type="text/javascript" src="${finlibPath}/jquery/jquery-ui-1.8.13.custom/development-bundle/ui/jquery.ui.core.js"></script>        
        <script type="text/javascript" src="${finlibPath}/jquery/jquery-ui-1.8.13.custom/development-bundle/ui/jquery.ui.datepicker.js"></script>                
        <script type="text/javascript" src="${finlibPath}/resource/common_functions.min.js"></script>
        <script type="text/javascript" src="${finlibPath}/resource/serverCombo.js"></script>
        <script type="text/javascript" src="javascript/finstudiomur.js"></script>
        <script type="text/javascript" src="${finlibPath}/resource/validations.min.js"></script>
        <script type="text/javascript" src="${finlibPath}/resource/ajax.js"></script>            
    </head>
    <body>
        <div class="container">
            <div class="content">
                <jsp:include page="report_menu.jsp"/>
                <div id="pdf_out"></div>
            </div>
        </div>
    </body>
</html>
