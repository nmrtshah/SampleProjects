<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<form name="reportMenuForm" id="reportMenuForm" method="post">
    <div class="menu_new">
        <div class="menu_caption_bg">
            <div class="menu_caption_text">Finstudio Module Usage Report</div>
            <span><a href="javascript:void(0)" name="show_hide" id="show_hide" class="nav_hide" onclick="javascript:hide_menu('show_hide','hide_menu', 'nav_show','nav_hide')" ></a></span>
        </div>
        <div class="report_filter_bg">
            <table cellpadding="0" cellspacing="0" border="0" class="report_filter">              
                <tr>
                    <td><a href="javascript:void(0);" onclick="displayReportMenu('report_filter_out');">Filter</a>
                        <span id="filter" class="report_filter"></span>
                    </td>
                    <td><a href="javascript:void(0);" onclick="displayReportMenu('report_export_out');">Export</a>
                        <span id="graph" class="report_filter"></span>
                    </td>
                </tr>
            </table>
            <table class="input_date" border="0" cellpadding="0" cellspacing="0">
                <tr>   
                    <td class="from_date" style="padding: 0; float: none;">
                        <label>From :</label><input type="text" value="${dateString}" class="datepickerclass" id="fromDate" name="fromDate" readonly="readonly" />
                        <script type="text/javascript">loadDatePicker("fromDate");</script>
                    </td>
                    <td class="to_date" style="padding: 0; float: none;">
                        <label>To :</label><input type="text" value="${dateString}" class="datepickerclass" id="toDate" name="toDate" readonly="readonly" />
                        <script type="text/javascript">loadDatePicker("toDate");</script>
                    </td>
                    <td class="submit_date" style="padding: 0; float: none;">
                        <input type="button" value="GO" class="button" onclick="javascript: showReport('view');" />
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
                <input type="hidden" name="rptfor" id="rptfor" />
                <input class="button" type="button" value="Apply" name="apply" onclick="javascript: showReport('view');" />
                <input class="button" type="button" id="btnPrint" value="Print" name="print" onclick="javascript: showReport('Print');"/>
                <input class="button" type="button" value="Close" name="close" onclick="javascript: hide_menu('show_hide', 'hide_menu', 'nav_show', 'nav_hide')" />
            </div>
        </div>
    </div>
    <div class="clear"></div>
    <jsp:include page="Grid.jsp"/>
    <div style="display: none;" id="loadingImageID" class="cmbLoading" align="center"><span>Loading...</span></div>
    <div id="footer_print">&nbsp;</div>
    <div id="header_print">&nbsp;</div>
</form>
