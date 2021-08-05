<%-- TCIGBF --%>

<%-- 
    Document   : View
    Created on : May 8, 2013, 11:17:57 AM
    Author     : Sonam Patel
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:choose>
    <c:when test="${process eq null}">
        <div class="menu_caption_bg">
            <div class="menu_caption_text">View</div>
        </div>
        <div class="content" style="min-height: 0px;">
            <table align="center" border="0" cellpadding="0" cellspacing="2" class="menu_subcaption" width="50%">
                <tr>
                    <td align="right" class="report_content_caption">
                        Data Request ID : 
                    </td>
                    <td class="report_content_value">
                        <input type="text" id="txtViewDataReqId" name="txtViewDataReqId" tabindex="1" onkeypress="javascript: onDataRequestEnter(event);">
                    </td>
                </tr>
                <tr>
                    <td align="right" class="report_content_caption">
                    </td>
                    <td class="report_content_value">
                        -- OR --
                    </td>
                </tr>
                <tr>
                    <td align="right" class="report_content_caption">
                        Project : 
                    </td>
                    <td class="report_content_value">
                        <select id="cmbViewProjId" name="cmbViewProjId" onchange="javascript: fillViewRequest();" tabindex="2">
                            <option value="-1">-- Select Project --</option>
                            <c:forEach items="${proj_ids}" var="proj_id">
                                <option value="${proj_id.PRJ_ID}">${proj_id.PRJ_NAME}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td align="right" class="report_content_caption">
                        Employee : 
                    </td>
                    <td class="report_content_value">
                        <select id="cmbViewEmp" name="cmbViewEmp" tabindex="3">
                            <option value="-1">-- Select Employee --</option>
                            <c:forEach items="${entry_bys}" var="entry_by">
                                <option value="${entry_by.EMP_CODE}">${entry_by.EMP_NAME}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td align="right" class="report_content_caption">
                        Request No : 
                    </td>
                    <td class="report_content_value">
                        <input type="text" id="txtViewReqId" name="txtViewReqId" onblur="javascript: fillViewRequest();" style="width: 80px" tabindex="4" />
                        <select id="cmbViewReqId" name="cmbViewReqId" style="float: none; width: 189px !important" tabindex="5">
                            <option value="-1">-- Select Request No --</option>
                            <c:forEach items="${req_ids}" var="req_id">
                                <option value="${req_id.REQ_ID}">${req_id.REQ_ID}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td align="right" class="report_content_caption">
                        Purpose : 
                    </td>
                    <td class="report_content_value">
                        <textarea rows="3" cols="2" id="txtViewPurpose" name="txtViewPurpose" tabindex="6"></textarea>
                    </td>
                </tr>
                <tr>
                    <td align="right" class="report_content_caption">
                        Status : 
                    </td>
                    <td class="report_content_value">
                        <select id="cmbViewStatus" name="cmbViewStatus" tabindex="7">
                            <option value="-1">-- Select Status --</option>
                            <option value="Pending" selected>Pending</option>
                            <option value="Partial">Partial</option>
                            <option value="Rollbacked">Rollbacked</option>
                            <option value="Halted">On Halt</option>
                            <option value="Done">Done</option>
                            <option value="Cancelled">Cancelled</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td align="right" class="report_content_caption">
                        Entry Date : 
                    </td>
                    <td>
                        <table>
                            <tr>
                                <td>
                                    <input type="text" class="datepickerclass" id="dtFromDate" name="dtFromDate" readonly value="${fromDate}" tabindex="8">
                                </td>
                                <td>
                                    <input type="text" class="datepickerclass" id="dtToDate" name="dtToDate" readonly value="${toDate}" tabindex="9">
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
            <div id="divButton">
                <input class="button" type="button" id="btnApply" name="btnApply" tabindex="10" Value="Apply" onclick="javascript: displayViewMasterGrid();">
            </div>
        </div>
    </c:when>
    <c:when test="${process eq 'viewReq'}">
        <option value="-1">-- Select Request No --</option>
        <c:forEach items="${req_ids}" var="req_id">
            <option value="${req_id.REQ_ID}">${req_id.REQ_ID}</option>
        </c:forEach>
    </c:when>
</c:choose>
