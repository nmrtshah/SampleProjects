<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:choose>
    <c:when test="${action eq 'reportPage'}">
        <form name="generatereportform" id="generatecertificateform" method="post" action="">
            <div class="menu_caption_bg cursor-pointer" style="width: 100%" onclick="javascript:hide_menu('show_hide2', 'mainreport', 'nav_show', 'nav_hide')">
                <div id="divAddCaption" class="menu_caption_text">Report</div>
                <span><a href="javascript:void(0)" name="show_hide2" id="show_hide2" class="nav_hide"></a></span>
            </div>
            <div class="report_content" id="mainreport">
                <table align="center" border="0" cellpadding="0" cellspacing="2" class="menu_subcaption" width="100%">
                    <tr>
                        <td align="right" class="report_content_caption">
                            Client Name : 
                        </td>
                        <td class="report_content_value">
                            <select class="custom_combo_showSSWT" id="clientcmbrpt" name="clientcmb" tabindex="2" >
                                <option value="">-- Select Client Name --</option>
                                <c:forEach items="${clientList}" var="b" >
                                    <option value="${b.CLIENT_ID}" >${b.CLIENT_NAME}</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td align="center" colspan="2">
                            <input class="button" type="button" id="viewRpt" name="viewRpt" Value="Submit" onclick="javascript: getReport();" tabindex="2"/>
                            <input class="button" type="reset" id="btnReset" name="btnReset" Value="Reset" onclick="javascript:ReportLoader()" tabindex="2"/>
                        </td>
                    </tr>
                </table>
            </div>
            <div class="report_content" id="rpt">
                <table cellpadding="0" cellspacing="0" style="width: 100%" class="dhtml-main-report">
                    <tbody id="maingridDiv">
                        <tr>
                            <td valign="top">
                                <div id="gridbox" style="width:100%;background-color:white;margin: 5px auto;"></div>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </c:when>
    </c:choose>