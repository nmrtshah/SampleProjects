<%-- TCIGBF --%>

<%-- 
    Document   : Main
    Created on : May 8, 2013, 11:17:57 AM
    Author     : Sonam Patel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <title>Fin Data Request Executor</title>
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
        <script type="text/javascript" src="${finlib_path}/dhtmlxSuite/dhtmlxPopup/codebase/dhtmlxpopup.js"></script>
        <script type="text/javascript" src="${finlib_path}/dhtmlxSuite/dhtmlxForm/codebase/dhtmlxcommon.js"></script>
        <script type="text/javascript" src="${finlib_path}/dhtmlxSuite/dhtmlxForm/codebase/dhtmlxform.js"></script>
        <script type="text/javascript" src="${finlib_path}/dhtmlxSuite/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
        <script type="text/javascript" src="${finlib_path}/dhtmlxSuite/dhtmlxWindows/codebase/dhtmlxcontainer.js"></script>
        <script type="text/javascript" src="javascript/FinDataReqExecutor.js"></script>
    </head>
    <body onload="javascript:AddLoader();">
        <div class="container">
            <div class="content" id="menuLoader">
                <form name="datareqexecutorForm" id="datareqexecutorForm" method="post" action="">
                    <div class="menu_new">
                        <div class="menu_caption_bg cursor-pointer" onclick="javascript:hide_menu('show_hide','load', 'nav_show','nav_hide')">
                            <div class="menu_caption_text">Data Request Executor</div>
                            <span><a href="javascript:void(0)" name="show_hide" id="show_hide" class="nav_hide"></a></span>
                        </div>
                        <div class="collapsible_menu_tab fullwidth">
                            <ul id="mainTab1">
                                <li class="" id="mainTab_1" onclick="selectTab(this.id);">
                                    <a rel="rel0" href="javascript:void(0)" onclick="javascript:AddLoader();">Add</a>
                                </li>
                                <li class="" id="mainTab_2" onclick="selectTab(this.id);">
                                    <a rel="rel0" href="javascript:void(0)" onclick="javascript:ViewLoader();">View</a>
                                </li>
                                <li class="" id="mainTab_3" onclick="selectTab(this.id);">
                                    <a rel="rel0" href="javascript:void(0)" onclick="javascript:reportLoader();">Mapping Report</a>                                 
                                </li>
                            </ul>
                        </div>                  
                        <div id="divFinLibPath" style="display: none">${finlib_path}</div>
                        <div align="center" class="report_content">
                            <div id="load" style="display: block;"></div>
                        </div>
                    </div>                        
                    <table align="center" border="0" cellpadding="0" cellspacing="0" class="html-main-report" id="tblAddReport">
                    </table>
                    <table align="center" border="0" cellpadding="0" cellspacing="0" class="html-main-report fullwidth" id="tblViewReport">
                    </table>                     
                </form>
            </div>
        </div>
    </body>
</html>
