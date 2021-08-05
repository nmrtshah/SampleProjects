<%--
    Document   : Add
    Created on : 4 May, 2015, 1:33:03 PM
    Author     : njuser
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <title>File Dir Auto Request</title>
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
        <script type="text/javascript" src="javascript/filedirautorequest.js"></script>

        <link rel="stylesheet" type="text/css" href="${finlib_path}/resource/main_offline.css">
        <%--DHTMLX--%>
        <link rel="stylesheet" type="text/css" href="${finlib_path}/dhtmlxSuite4/codebase/dhtmlx.css">
        <link rel="stylesheet" type="text/css" href="${finlib_path}/dhtmlxSuite4/sources/dhtmlxToolbar/codebase/skins/dhtmlxtoolbar_dhx_web.css">
        <%--COMMON--%>
        <script type="text/javascript" src="${finlib_path}/resource/topbar.min.js"></script>
        <script type="text/javascript" src="${finlib_path}/resource/common_functions.min.js"></script>
        <script type="text/javascript" src="${finlib_path}/resource/common_functions.min.js"></script>
        <%--DHTMLX--%>
        <script type="text/javascript" src="${finlib_path}/dhtmlxSuite4/codebase/dhtmlx.js"></script>

        <script type="text/javascript" src="${finlib_path}/resource/common_functions.js"></script>
        <script type="text/javascript" src="${finlib_path}/resource/validations.min.js"></script>
        <script type="text/javascript" src="${finlib_path}/jquery/jquery-ui-1.8.13.custom/development-bundle/ui/jquery.ui.datepicker.js"></script>

        <script type="text/javascript" >
            $(document).ready(function () {
                loadDatePicker('from_date');
                loadDatePicker('to_date');
            });
        </script>
    </head>

    <body onload="javascript:AddLoader();">
        <div class="container" ng-app="">
            <div class="content" id="menuLoader">
                <div class="menu_new">
                    <div class="menu_caption_bg cursor-pointer" onclick="javascript:hide_menu('show_hide1', 'mainpage', 'nav_show', 'nav_hide')">
                        <div class="menu_caption_text">File Dir Auto Request</div>
                        <span><a href="javascript:void(0)" name="show_hide1" id="show_hide1" class="nav_hide"></a></span>
                    </div>

                    <div class="collapsible_menu_tab fullwidth" id="mainpage">
                        <form name="finserverentryform" id="finserverentryform" method="post" action="">
                            <ul id="mainTab1">
                                <li class="selected" id="mainTab_1" onclick="selectTab(this.id);">
                                    <a rel="rel0" href="javascript:void(0);" onclick="javascript:AddLoader();">Add</a>
                                </li>

                                <li class="" id="mainTab_2" onclick="selectTab(this.id);">
                                    <a rel="rel0" href="javascript:void(0)" onclick="javascript:viewReportTab();">Report</a>
                                </li>

                                <li class="" id="mainTab_3" onclick="selectTab(this.id);">
                                    <a rel="rel0" href="javascript:void(0)" onclick="javascript:AuthorizeLoader();">Authorize</a>
                                </li>
                            </ul>
                        </form>
                    </div>
                </div>
                <div id="add_load" align="center" class="menu_new" style="width: 1300px;"></div>
                <div id="basicReportDiv" style="display: none" align="center" class="menu_new" style="width: 1300px !important;">
                    <form name="filedirreportform" id="finserverentryform" method="post" action="">

                        <div class="menu_caption_bg cursor-pointer" onclick="javascript:hide_menu('show_hide3', 'mainRpt', 'nav_show', 'nav_hide')" style="width: 1300px;">
                            <div id="divAddCaption" class="menu_caption_text">Report</div>
                            <span><a href="javascript:void(0)" name="show_hide3" id="show_hide3" class="nav_hide"></a></span>
                        </div>

                        <div class="report_content" id="mainRpt">
                            <table align="center" border="0" cellpadding="0" cellspacing="2" class="menu_subcaption" width="100%">
                                <tr>
                                    <td align="right" class="report_content_caption">
                                        Request Id :
                                    </td>
                                    <td class="report_content_value">
                                        <input type="text" id="SRNO" name="SRNO">
                                    </td>
                                </tr>
                                <tr>
                                    <td align="right" class="report_content_caption">
                                        Server :
                                    </td>
                                    <td class="report_content_value">
                                        <select class="custom_combo_showSSWT" id="rpt_servername" name="rpt_servername" tabindex="1">
                                            <option value="-1">-- Select Server Name --</option>
                                            <optgroup label="Prod">
                                                <option value="prodhoweb1.nj">prodhoweb1.nj</option>
                                                <option value="prodhoweb2.nj">prodhoweb2.nj</option>
                                                <option value="prodhoweb3.nj">prodhoweb3.nj</option>
                                                <option value="prodhoweb4.nj">prodhoweb4.nj</option>
                                                <option value="prodhoweb1rep.nj">prodhoweb1rep.nj</option>
                                                <option value="prodhoweb2rep.nj">prodhoweb2rep.nj</option>
                                                <option value="prodhoweb3rep.nj">prodhoweb3rep.nj</option>
                                                <option value="prodhoweb4rep.nj">prodhoweb4rep.nj</option>
                                            </optgroup>
                                            <optgroup label="Dev">
                                                <option value="devhoweb1.nj">devhoweb1.nj</option>
                                                <option value="devhoweb2.nj">devhoweb2.nj</option>
                                                <option value="devhoweb3.nj">devhoweb3.nj</option>
                                                <option value="devhoweb4.nj">devhoweb4.nj</option>
                                            </optgroup>
                                            <optgroup label="Test">
                                                <option value="testhoweb1.nj">testhoweb1.nj</option>
                                                <option value="testhoweb2.nj">testhoweb2.nj</option>
                                                <option value="testhoweb3.nj">testhoweb3.nj</option>
                                                <option value="testhoweb4.nj">testhoweb4.nj</option>
                                                <option value="testhoweb1rep.nj">testhoweb1rep.nj</option>
                                                <option value="testhoweb2rep.nj">testhoweb2rep.nj</option>
                                                <option value="testhoweb3rep.nj">testhoweb3rep.nj</option>
                                                <option value="testhoweb4rep.nj">testhoweb4rep.nj</option>
                                            </optgroup>
                                            <optgroup label="StorageBox">
                                                <option value="devhosb1.nj">devhosb1.nj</option>
                                                <option value="testhosb1.nj">testhosb1.nj</option>
                                                <option value="prodhosb1.nj">prodhosb1.nj</option>
                                            </optgroup>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="right" class="report_content_caption">
                                        Project :
                                    </td>
                                    <td class="report_content_value">
                                        <select class="custom_combo_showSSWT" id="rpt_project" name="rpt_project" tabindex="2" >
                                            <option value="-1">-- Select Project Name --</option>
                                            <c:forEach items="${projectListForReport}" var="c" >
                                                <option value="${c.PRJ_ID}|${c.PRJ_NAME}|${c.DOMAIN_NAME}">${c.PRJ_NAME} - ${c.DOMAIN_NAME}</option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="right" class="report_content_caption">
                                        From Date :
                                    </td>
                                    <td class="report_content_value">
                                        <input readonly="readonly" type="text" id="from_date" name="from_date" value="${cur_date}">
                                    </td>
                                </tr>
                                <tr>
                                    <td align="right" class="report_content_caption">
                                        To Date :
                                    </td>
                                    <td class="report_content_value">
                                        <input type="text" readonly="readonly" id="to_date" name="to_date" value="${cur_date}">
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center" colspan="2">
                                        <input class="button" type="button" id="btnView" name="btnView" Value="View" onclick="javascript: ReportLoader();" />
                                        <input class="button" type="button" id="btnReset" name="btnReset" Value="Reset" onclick="javascript: ResetMenu();"/>
                                        <input type="hidden" id="hiddenDate" name="hiddenDate" value="${cur_date}">
                                    </td>
                                </tr>
                            </table>
                        </div>
                        <div align="center" style="font-weight: normal; color: blue;">Note: <b>A)</b> This Report will display 20 Records only.&nbsp;&nbsp;&nbsp;<b>B)</b> Retrieved Files from Storagebox &amp; Application Servers will be available for 1 hour ONLY.&nbsp;&nbsp;&nbsp;<b>C)</b> If you do not find a requested file in zip, It means file is not available on application server.</div>
                        <div style="width: 1300px;" id="rpt">
                            <table cellpadding="0" cellspacing="0" width="100%" class="dhtml-main-report">
                                <tbody id="maingridDiv">
                                    <tr>
                                        <td valign="top">
                                            <div id="gridbox" style="width:100%;background-color:white;margin: 5px auto;"></div>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </form>
                </div>
                <div id="basicAuthorizeDiv" style="display: none" align="center" class="menu_new" style="width: 1300px;"></div>
            </div>
            <div id="divFinLibPath" style="display: none">${finlib_path}</div>
        </div>
    </div>
</body>
</html>