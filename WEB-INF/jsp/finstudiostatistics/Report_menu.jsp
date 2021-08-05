<%-- TCIGBF --%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:choose>
    <c:when test="${process eq 'getmenu'}">
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
                <link rel="stylesheet" type="text/css" href="${finlibPath}/dhtmlxSuite/dhtmlxGrid/codebase/skins/dhtmlxgrid_dhx_black.css">
                <link rel="stylesheet" type="text/css" href="${finlibPath}/dhtmlxSuite/dhtmlxGrid/codebase/skins/dhtmlxgrid_dhx_terrace.css">
                <link rel="stylesheet" type="text/css" href="${finlibPath}/dhtmlxSuite/dhtmlxToolbar/codebase/skins/dhtmlxtoolbar_dhx_web.css">
                <link rel="stylesheet" type="text/css" href="${finlibPath}/dhtmlxSuite/dhtmlxChart/codebase/dhtmlxchart.css">
                <link rel="stylesheet" type="text/css" href="${finlibPath}/jquery/jquery.jqGrid-3.8.2/css/ui.jqgrid.css">
                <script type="text/javascript" src="${finlibPath}/dhtmlxSuite/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
                <script type="text/javascript" src="${finlibPath}/dhtmlxSuite/dhtmlxGrid/codebase/dhtmlxcommonfunctions.js"></script>
                <script type="text/javascript" src="${finlibPath}/dhtmlxSuite/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
                <script type="text/javascript" src="${finlibPath}/dhtmlxSuite/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
                <%--For splitting,serial number,grouping--%>
                <script type="text/javascript" src="${finlibPath}/dhtmlxSuite/dhtmlxGrid/codebase/ext/dhtmlxgrid_splt.js"></script>
                <%--For PDF & Excel--%>
                <script type="text/javascript" src="${finlibPath}/dhtmlxSuite/dhtmlxGrid/codebase/ext/dhtmlxgrid_export.js"></script>
                <%--Group by--%>
                <script type="text/javascript" src="${finlibPath}/dhtmlxSuite/dhtmlxGrid/codebase/ext/dhtmlxgrid_group.js"></script>
                <%--Filtering--%>
                <script type="text/javascript" src="${finlibPath}/dhtmlxSuite/dhtmlxGrid/codebase/ext/dhtmlxgrid_filter.js"></script>
                <%--Smart Rendering--%>
                <script type="text/javascript" src="${finlibPath}/dhtmlxSuite/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
                <%--Paging--%>
                <script type="text/javascript" src="${finlibPath}/dhtmlxSuite/dhtmlxToolbar/codebase/dhtmlxtoolbar.js"></script>
                <script type="text/javascript" src="${finlibPath}/dhtmlxSuite/dhtmlxGrid/codebase/ext/dhtmlxgrid_pgn.js"></script>
                <%--For links in rows--%>
                <script type="text/javascript" src="${finlibPath}/dhtmlxSuite/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_link.js"></script>
                <%--For serial Number--%>
                <script type="text/javascript" src="${finlibPath}/dhtmlxSuite/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_cntr.js"></script>
                <%--For printable view--%>
                <script type="text/javascript" src="${finlibPath}/dhtmlxSuite/dhtmlxGrid/codebase/ext/dhtmlxgrid_nxml.js"></script>
                <%--For rowspan in record--%>
                <script type="text/javascript" src="${finlibPath}/dhtmlxSuite/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
                <%--Drag n Drop--%>
                <script type="text/javascript" src="${finlibPath}/dhtmlxSuite/dhtmlxGrid/codebase/ext/dhtmlxgrid_drag.js"></script>
                <%--Move Columns--%>
                <script type="text/javascript" src="${finlibPath}/dhtmlxSuite/dhtmlxGrid/codebase/ext/dhtmlxgrid_mcol.js"></script>
                <script type="text/javascript" src="${finlibPath}/dhtmlxSuite/dhtmlxGrid/codebase/ext/dhtmlxgrid_post.js"></script>
                <%--for dhtml charts--%>
                <script src="${finlibPath}/dhtmlxSuite/dhtmlxChart/codebase/dhtmlxchart.js" type="text/javascript"></script>
                <script type="text/javascript" src="${finlibPath}/jquery/jquery-1.6.1/jquery-1.6.1.min.js"></script>
                <script type="text/javascript" src="${finlibPath}/jquery/jquery-ui-1.8.13.custom/js/jquery-ui-1.8.13.custom.min.js"></script>
                <script type="text/javascript" src="${finlibPath}/resource/common_functions.min.js"></script>
                <script type="text/javascript" src="${finlibPath}/resource/serverCombo.js"></script>
                <script type="text/javascript" src="${finlibPath}/resource/validations.min.js"></script>
                <script type="text/javascript" src="${finlibPath}/resource/ajax.js"></script>
                <script type="text/javascript" src="javascript/finstudiostatistics.js"></script>
            </head>
            <body>
                <table align="center" width="100%">
                    <tr>
                        <td>
                            <div class="container">
                                <div class="content">
                                    <form name="finstudiostatisticsForm" id="finstudiostatisticsForm" method="post" action="#" onsubmit="return false;">
                                        <div class="menu_new">
                                            <div class="menu_caption_bg cursor-pointer" onclick="javascript:hide_menu('show_hide','hide_menu', 'nav_show','nav_hide')">
                                                <div class="menu_caption_text">Finstudio Statistics Report</div>
                                                <span>
                                                    <a href="javascript:void(0)" name="show_hide" id="show_hide" class="nav_hide"></a>
                                                </span>
                                            </div>
                                            <div class="report_filter_bg">
                                                <table cellpadding="0" cellspacing="0" border="0" class="report_filter">
                                                    <tr>
                                                        <td><a href="javascript:void(0);" onclick="displayReportMenu('report_filter_out');">Filter</a>
                                                            <span id="filter" class="report_filter"></span>
                                                        </td>
                                                        <td><a href="javascript:void(0);" onclick="displayReportMenu('report_export_out');">Export</a>
                                                            <span id="export" class="report_filter"></span>
                                                        </td>
                                                    </tr>
                                                </table>
                                            </div>
                                            <div id="hide_menu" style="display: block;">
                                                <div id="report_filter_out" class="report_out" style="display: none">
                                                    <jsp:include page="Filter.jsp"/>
                                                </div>
                                                <div id="report_export_out" class="report_out" style="display: none">
                                                    <jsp:include page="Export.jsp"/>
                                                </div>
                                                <div class="report_but">
                                                    <input type="hidden" name="rptfor" id="rptfor" >
                                                    <input class="button" type="button" value="Apply" id="apply" name="apply" onclick="javascript: showReport('View');" >
                                                    <input class="button" type="button" value="Print" id="btnPrint" name="print" onclick="javascript: showReport('Print');">
                                                    <input class="button" type="button" value="Close" name="close"
                                                           onclick="javascript: hide_menu('show_hide', 'hide_menu', 'nav_show', 'nav_hide')" >
                                                </div>
                                            </div>
                                        </div>
                                        <div class="clear"></div>
                                        <jsp:include page="Grid.jsp"/>
                                    </form>
                                </div>
                                <div id="html_out"></div>
                            </div>
                        </td>
                    </tr>
                </table>
                <div style="display: none;" id="loadingImageID" class="cmbLoading" align="center"><span>Loading...</span></div>
            </body>
        </html>
    </c:when>
    <c:when test="${process eq 'getreportGrid'}">
        ${json}
    </c:when>
</c:choose>
