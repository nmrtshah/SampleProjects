<%-- TCIGBF --%>

<%--
    Document   : Main
    Created on : Dec 29, 2012, 13:14:11 PM
    Author     : njuser
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <title>Fin File Transfer</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <link rel="stylesheet" type="text/css" href="${finlib_path}/resource/main_offline.css" />
        <link rel="stylesheet" type="text/css" href="${finlib_path}/jquery/jquery-ui-1.8.13.custom/css/smoothness/jquery-ui-1.8.13.custom.css" />
        <link rel="stylesheet" type="text/css" href="${finlib_path}/jquery/jquery.jqGrid-3.8.2/css/ui.jqgrid.css" />
        <script type="text/javascript" src="${finlib_path}/jquery/jquery-1.6.1/jquery-1.6.1.min.js"></script>
        <script type="text/javascript" src="${finlib_path}/resource/common_functions.min.js"></script>
        <script type="text/javascript" src="${finlib_path}/jquery/jquery-ui-1.8.13.custom/js/jquery-ui-1.8.13.custom.min.js"></script>
        <script type="text/javascript" src="${finlib_path}/jquery/jquery.jqGrid-3.8.2/js/i18n/grid.locale-en.js"></script>
        <script type="text/javascript" src="${finlib_path}/jquery/jquery.jqGrid-3.8.2/js/jquery.jqGrid.min.js"></script>
        <script type="text/javascript" src="${finlib_path}/resource/validate.js"></script>
        <script type="text/javascript" src="${finlib_path}/resource/validate_date.js"></script>
        <script type="text/javascript" src="${finlib_path}/jquery/jquery-ui-1.8.13.custom/development-bundle/ui/jquery.ui.datepicker.js"></script>
        <script type="text/javascript" src="${finlib_path}/resource/serverCombo.js"></script>
        <script type="text/javascript" src="${finlib_path}/resource/finstudio-utility-functions.js"></script>
        <script type="text/javascript" src="${finlib_path}/resource/ajax.js"></script>
        <script type="text/javascript" src="javascript/finfiletransfer.js?v=1"></script>

    </head>
    <body>
        <div class="container">
            <div class="content" id="menuLoader">
                <div class="menu_new">
                    <div class="menu_caption_bg cursor-pointer" onclick="javascript:hide_menu('show_hide','load', 'nav_show','nav_hide')">
                        <div class="menu_caption_text">Fin File Transfer</div>
                        <span><a href="javascript:void(0)" name="show_hide" id="show_hide" class="nav_hide"></a></span>
                    </div>
                    <form name="finfiletransferForm" id="finfiletransferForm" method="post" action="">
                        <div class="collapsible_menu_tab fullwidth">
                            <ul id="mainTab1">
                                <li class="" id="mainTab_1" onclick="selectTab(this.id);">
                                    <a rel="rel0" href="javascript:void(0)" onclick="javascript:AddLoader();">Add</a>
                                </li>

                                <li class="" id="mainTab_4" onclick="selectTab(this.id);">
                                    <a rel="rel0" href="javascript:void(0)" onclick="javascript:ViewLoader();">View</a>
                                </li>
                                 <li class="" id="mainTab_3" onclick="selectTab(this.id);">
                                    <a rel="rel0" href="javascript:void(0)" onclick="javascript:ReportLoader();">Report</a>
                                </li>
                            </ul>
                        </div>
                        <div align="center" class="report_content">
                            <div id="load" style="display: block;"></div>
                        </div>
                        <jsp:include page="Grid.jsp" />
                        <div id="divFinLibPath" style="display: none">${finlib_path}</div>
                    </form>
                </div>
            </div>
        </div>

    </body>
    <script type="text/javascript" >
        ViewLoader();
    </script>
</html>
