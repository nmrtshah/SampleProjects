<%-- 
    Document   : Main
    Created on : 22 Feb, 2016, 6:57:39 PM
    Author     : Jigna Patel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Auto Block / Unblock</title>

        <link rel="stylesheet" type="text/css" href="${finlib_path}/resource/main_offline.css" />
        <link rel="stylesheet" type="text/css" href="${finlib_path}/jquery/jquery-ui-1.8.13.custom/css/smoothness/jquery-ui-1.8.13.custom.css" />
        
        <script type="text/javascript" src="${finlib_path}/jquery/jquery-1.6.1/jquery-1.6.1.min.js"></script>
        <script type="text/javascript" src="${finlib_path}/jquery/jquery-ui-1.8.13.custom/js/jquery-ui-1.8.13.custom.min.js"></script>
        <script type="text/javascript" src="${finlib_path}/jquery/jquery-ui-1.8.13.custom/development-bundle/ui/jquery.ui.datepicker.js"></script>
        <script type="text/javascript" src="${finlib_path}/resource/common_functions.min.js"></script>
        
        <link rel="stylesheet" type="text/css" href="${finlib_path}/dhtmlxSuite4/codebase/dhtmlx.css">
        <script type="text/javascript" src="${finlib_path}/dhtmlxSuite4/codebase/dhtmlx.js"></script>
        
        <script type="text/javascript" src="${finlib_path}/resource/serverCombo.js"></script>
        <script type="text/javascript" src="${finlib_path}/resource/finstudio-utility-functions.js"></script>
        <script type="text/javascript" src="${finlib_path}/resource/validate.js"></script>
        <script type="text/javascript" src="${finlib_path}/resource/ajax.js"></script>
        <script type="text/javascript" src="javascript/blockModules.js"></script>
    </head>
    <body>
        <div class="container">
            <div class="content" id="menuLoader">
                <div class="menu_new">
                    <div class="menu_caption_bg cursor-pointer" onclick="javascript:hide_menu('show_hide', 'load', 'nav_show', 'nav_hide')">
                        <div class="menu_caption_text">Auto Block / Unblock</div>
                        <span><a href="javascript:void(0)" name="show_hide" id="show_hide" class="nav_hide"></a></span>
                    </div>
                    <form name="blockModulesForm" id="blockModulesForm" method="post" action="">
                        <div class="collapsible_menu_tab fullwidth">
                            <ul id="mainTab1">
                                <ul id="mainTab1">
                                    <li class="" id="mainTab_1" onclick="selectTab(this.id);">
                                        <a rel="rel0" href="javascript:void(0)" onclick="javascript:addLoader();">Add</a>
                                    </li>
                                    <li class="" id="mainTab_4" onclick="selectTab(this.id);">
                                        <a rel="rel0" href="javascript:void(0)" onclick="javascript:reportLoader();">Report</a>
                                    </li>
                                    <li class="" id="mainTab_3" onclick="selectTab(this.id);">
                                        <a rel="rel0" href="javascript:void(0)" onclick="javascript:authoriseLoader();">Authorize</a>
                                    </li>
                                </ul>
                            </ul>
                        </div>
                        <div id="load" align="center" class="report_content">
                            <div id="resultMsg" style="display: block;"></div>
                            <input type="hidden" id="hdnUnBlockedId" name="hdnUnBlockedId"/>
                            <input type="hidden" id="hdnRemarks" name="hdnRemarks"/>
                            <div id="contentView" style="display: block;"></div>
                        </div>
                        <div id="finLibPath" style="display: none">${finlib_path}</div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>