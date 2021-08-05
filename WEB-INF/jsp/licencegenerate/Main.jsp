<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <title>Certificate Generate Module</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <link rel="stylesheet" type="text/css" href="${finlib_path}/jquery/jquery-ui-1.8.13.custom/css/smoothness/jquery-ui-1.8.13.custom.css">
        <script type="text/javascript" src="${finlib_path}/jquery/jquery-1.6.1/jquery-1.6.1.min.js"></script>
        <script type="text/javascript" src="${finlib_path}/jquery/jquery-ui-1.8.13.custom/js/jquery-ui-1.8.13.custom.min.js"></script>
        <link rel="stylesheet" type="text/css" href="${finlib_path}/jquery/jquery-ui-1.8.13.custom/css/smoothness/jquery-ui-1.8.13.custom.css" />
        <link rel="stylesheet" type="text/css" href="${finlib_path}/jquery/jquery.jqGrid-3.8.2/css/ui.jqgrid.css" />
        <script type="text/javascript" src="${finlib_path}/jquery/jquery.jqGrid-3.8.2/js/i18n/grid.locale-en.js"></script>
        <script type="text/javascript" src="${finlib_path}/jquery/jquery.jqGrid-3.8.2/js/jquery.jqGrid.min.js"></script>
        <script type="text/javascript" src="${finlib_path}/jquery/jquery-ui-multiselect-widget/src/jquery.multiselect.js"></script>
        <script type="text/javascript" src="${finlib_path}/jquery/jquery-ui-multiselect-widget/src/jquery.multiselect.filter.js"></script>
        <script type="text/javascript" src="${finlib_path}/jquery/jquery-ui-multiselect-widget/src/jquery.multiselect.filter.min.js"></script>
        <script type="text/javascript" src="${finlib_path}/resource/validate.js"></script>
        <script type="text/javascript" src="${finlib_path}/resource/validate_date.js"></script>
        <script type="text/javascript" src="${finlib_path}/resource/serverCombo.js"></script>
        <script type="text/javascript" src="${finlib_path}/resource/finstudio-utility-functions.js"></script>
        <script type="text/javascript" src="${finlib_path}/resource/ajax.js"></script>
        <script type="text/javascript" src="javascript/generatepdf.js"></script>

        <link rel="stylesheet" type="text/css" href="${finlib_path}/resource/main_offline.css">
        <%--DHTMLX--%>
        <link rel="stylesheet" type="text/css" href="${finlib_path}/dhtmlxSuite/dhtmlxGrid/codebase/dhtmlxgrid.css">
        <link rel="stylesheet" type="text/css" href="${finlib_path}/dhtmlxSuite/dhtmlxGrid/codebase/skins/dhtmlxgrid_dhx_web.css">
        <link rel="stylesheet" type="text/css" href="${finlib_path}/dhtmlxSuite/dhtmlxToolbar/codebase/skins/dhtmlxtoolbar_dhx_web.css">
        <link rel="stylesheet" type="text/css" href="${finlib_path}/dhtmlxSuite/dhtmlxGrid/codebase/ext/dhtmlxgrid_pgn_bricks.css">
        <link rel="stylesheet" type="text/css" href="${finlib_path}/dhtmlxSuite/dhtmlxGrid/codebase/skins/dhtmlxgrid_dhx_skyblue.css">
        <link rel="stylesheet" href="${finlib_path}/dhtmlxSuite/dhtmlxCalendar/codebase/dhtmlxcalendar.css" type="text/css">
        <link rel="stylesheet" type="text/css" href="${finlib_path}/dhtmlxSuite/dhtmlxCalendar/codebase/skins/dhtmlxcalendar_omega.css">
        <link rel="stylesheet" type="text/css" href="${finlib_path}/dhtmlxSuite/dhtmlxCalendar/codebase/skins/dhtmlxcalendar_dhx_skyblue.css">
        <%--COMMON--%>
        <script type="text/javascript" src="${finlib_path}/resource/topbar.min.js"></script>
        <script type="text/javascript" src="${finlib_path}/resource/common_functions.min.js"></script>
        <script type="text/javascript" src="${finlib_path}/resource/common_functions.min.js"></script>
        <%--DHTMLX--%>
        <script type="text/javascript" src="${finlib_path}/resource/codegenfunc.js"></script>
        <script type="text/javascript" src="${finlib_path}/dhtmlxSuite/dhtmlxGrid/codebase/dhtmlxcommonfunctions.js"></script>
        <script type="text/javascript" src="${finlib_path}/dhtmlxSuite/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
        <script type="text/javascript" src="${finlib_path}/dhtmlxSuite/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
        <script type="text/javascript" src="${finlib_path}/dhtmlxSuite/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
        <script type="text/javascript" src="${finlib_path}/dhtmlxSuite/dhtmlxGrid/codebase/ext/dhtmlxgrid_export.js"></script>
        <script type="text/javascript" src="${finlib_path}/dhtmlxSuite/dhtmlxGrid/codebase/ext/dhtmlxgrid_post.js"></script>
        <script type="text/javascript" src="${finlib_path}/dhtmlxSuite/dhtmlxCalendar/codebase/dhtmlxcalendar.js"></script>
        <script type="text/javascript" src="${finlib_path}/dhtmlxSuite/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_cntr.js"></script>
        <script type="text/javascript" src="${finlib_path}/dhtmlxSuite/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_dhxcalendar.js"></script>
        <script type="text/javascript" src="${finlib_path}/dhtmlxSuite/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
        <script type="text/javascript" src="${finlib_path}/resource/common_functions.js"></script>
        <script type="text/javascript" src="${finlib_path}/resource/validations.min.js"></script>
        <script type="text/javascript" src="${finlib_path}/jquery/jquery-ui-1.8.13.custom/development-bundle/ui/jquery.ui.datepicker.js"></script>
    </head>

    <!--<body onload="javascript:AddLoader();">-->
    <body>
        <div class="container">
            <div class="content" id="menuLoader">
                <div class="menu_new" style="width: 95%">
                    <div class="menu_caption_bg cursor-pointer" style="width: 100%" onclick="javascript:hide_menu('show_hide1', 'mainpage', 'nav_show', 'nav_hide')">
                        <div class="menu_caption_text">Certificate Generation</div>
                        <span><a href="javascript:void(0)" name="show_hide1" id="show_hide1" class="nav_hide"></a></span>
                    </div>

                    <div id="mainpage">
                        <div class="collapsible_menu_tab fullwidth" >
                            <form name="generatePdfForm" id="generatePdfForm" method="post" action="">
                                <ul id="mainTab1">
                                    <li id="mainTab_1" onclick="selectTab(this.id);">
                                        <a rel="rel0" href="javascript:void(0);" onclick="javascript:AddLoader();">Client Add</a>
                                    </li>

                                    <li class="" id="mainTab_2" onclick="selectTab(this.id);">
                                        <a rel="rel0" href="javascript:void(0)" onclick="javascript:GeneratePdfLoader();">Generate PDF</a>
                                    </li>

                                    <li class="" id="mainTab_3" onclick="selectTab(this.id);">
                                        <a rel="rel0" href="javascript:void(0)" onclick="javascript:ReportLoader();">Report</a>
                                    </li>
                                </ul>
                            </form>
                        </div>           
                        <div align="center" style="width: 95%" class="report_content">
                            <div id="clientDiv" style="display: block;"></div>
                            <div id="generatePdfDiv" style="display: none"></div>
                            <div id="reportDiv" style="display: none"></div>
                        </div>
                    </div>
                </div>
            </div>
            <div id="divFinLibPath" style="display: none">${finlib_path}</div>
        </div>
    </div>
</body>
</html>