<%-- 
    Document   : Add
    Created on : 4 May, 2015, 1:33:03 PM
    Author     : njuser
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <title>Web Service Document Generate</title>
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
        <script type="text/javascript" src="javascript/webservicedoc.js"></script>

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

        <style type="text/css" >
            .report_content1 {
                clear: both;
                color: #000000;
                font-size: 11px;
                margin: 0 auto;
                width: 1000px;
            }
            .table_subcaption {
                color: #000000;
                font-size: 11px;
                font-weight: bold;
                letter-spacing: 1px;
                line-height: 26px;
                margin: 4px 0;
                text-align: left;
            }
        </style>
    </head>

    <body onload="javascript:WebService();">
        <div class="container">
            <div class="content" id="menuLoader">
                <div class="menu_new" style="width: 1070px">
                    <div class="menu_caption_bg cursor-pointer nowrap" style="width: 1070px" onclick="javascript:hide_menu('show_hide', 'maintab1', 'nav_show', 'nav_hide')">
                        <div class="menu_caption_text">Web Service Document Generate</div>
                        <span><a href="javascript:void(0)" name="show_hide" id="show_hide" class="nav_hide"></a></span>
                    </div>

                    <div class="collapsible_menu_tab fullwidth" id="maintab1">
                        <ul id="mainTab1">
                            <li class="selected" id="mainTab_1" onclick="selectTab(this.id);">
                                <a rel="rel0" href="javascript:void(0);" onclick="javascript:WebService();">New Web-Service</a>
                            </li>
                        </ul>
                        <div align="center" class="report_content1">
                            <div class="menu_caption_bg">
                                <div id="divAddCaption" class="menu_caption_text">Generate Document for Web Service</div>
                            </div>
                            <div id="webservicepage" style="display: block;"></div>
                            <div id="success" style="display: block;"></div>
                        </div>
                    </div>
                </div>
                <div id="divFinLibPath" style="display: none">${finlib_path}</div>
            </div>
        </div>           
    </body>
</html>
