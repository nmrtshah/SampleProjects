<%-- TCIGBF --%>

<%-- 
    Document   : Report
    Created on : May 8, 2013, 11:17:57 AM
    Author     : Sonam Patel
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:choose>
    <c:when test="${records ne null && AccessMsg eq null}">
        <tr>
            <td align="center">
                <div class="tbl_h1_bg">
                    <div class="menu_caption_text" id="viewRptTitle" style="color: orange">Data Request Report</div>
                </div>
            </td>
        </tr>
        <tr>
            <td>
                <div>
                    <table id="rptgrid" class="rpt_tbl tbl_h4_bg1 fullwidth" cellpadding="0" cellspacing="0">
                        <thead>
                            <tr class="report_caption">
                                <th align="center" id="thDataReqId" width="20"><b>Data Request Id</b></th>
                                <th align="center" id="thRequestor" width="50"><b>Requestor</b></th>
                                <th align="center" id="thProject" width="50"><b>Project</b></th>
                                <th align="center" id="thReqId" width="20"><b>WFMS Request ID</b></th>
                                <th align="center" id="thPurpose" width="60"><b>Purpose</b></th>
                                <th align="center" id="thRollbkOnErr" width="10"><b>Execution Type</b></th>
                                <th align="center" id="thEntryDate" width="50"><b>Entry Date</b></th>
                                <th align="center" id="thStatus" width="15"><b>Status</b></th>
                                <th align="center" id="thQuery" width="10"><b>Query Details</b></th>
                                <th align="center" id="thExecBy" width="20"><b>Executed/Cancelled By</b></th>
                                <th align="center" id="thExecHost" style="display: none" width="25"><b>Execution Host</b></th>
                                <th align="center" id="thExecDate" width="50"><b>Execution Date</b></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:set var="cnt" value="0"></c:set>
                            <c:forEach items="${records}" var="record">
                                <c:if test="${record.IS_CRITICAL eq 'Y'}">
                                    <tr style="background-color: #F17373; height: 40px;">
                                    </c:if>
                                    <c:if test="${record.IS_CRITICAL eq 'N'}">
                                    <tr style="height: 40px;">        
                                    </c:if>
                                    <td align="center">${record.DATA_REQ_ID}<input type="hidden" id="hdnConfirmation${record.DATA_REQ_ID}" name="hdnConfirmation${record.DATA_REQ_ID}" value="${record.IS_CRITICAL}" disabled /></td>
                                    <td align="center">${record.ENTRY_BY}</td>
                                    <td align="center">${record.PRJ_NAME}</td>
                                    <td align="center">${record.REQ_ID}</td>
                                    <td align="center">${fn:escapeXml(record.PURPOSE)}</td>
                                    <td align="center">${record.EXECUTION_TYPE}</td>
                                    <td align="center"><fmt:formatDate pattern="dd-MM-yyyy" value="${record.ENTRY_DATE}"></fmt:formatDate></td>
                                    <td align="center" nowrap>
                                        <c:choose>
                                            <c:when test="${record.EXECUTION_STATUS eq 'Done'}">
                                                Done
                                            </c:when>
                                            <c:when test="${record.EXECUTION_STATUS eq 'Pending'}">
                                                <input class="button" type="button" value="Execute" title="Execute Request" onclick="executeRequest(${record.DATA_REQ_ID});">
                                                <input class="button" type="button" value="Halt" title="Halt Request" onclick="haltViewRequest(${record.DATA_REQ_ID});">
                                                <input class="button" type="button" value="Cancel" title="Cancel Request" onclick="cancelViewRequest(${record.DATA_REQ_ID});">
                                            </c:when>
                                            <c:when test="${record.REPLACEMENT_ID eq null and (record.EXECUTION_STATUS eq 'Partial' or record.EXECUTION_STATUS eq 'Rollbacked')}">
                                                ${record.EXECUTION_STATUS} - <input class="button" type="button" Value="New Request" 
                                                         <c:if test="${record.EXECUTION_STATUS eq 'Partial'}">title="Create new request with failed queries" </c:if>
                                                         <c:if test="${record.EXECUTION_STATUS eq 'Rollbacked'}">title="Create new request with all queries" </c:if>
                                                         onclick="createNewRequest(${record.DATA_REQ_ID});">
                                            </c:when>
                                            <c:when test="${record.REPLACEMENT_ID ne null and (record.EXECUTION_STATUS eq 'Partial' or record.EXECUTION_STATUS eq 'Rollbacked')}">
                                                New Request ID is ${record.REPLACEMENT_ID}
                                            </c:when>
                                            <c:when test="${record.EXECUTION_STATUS eq 'Halted'}" >
                                                <input class="button" type="button" value="Resume" title="Resume Request" onclick="resumeViewRequest(${record.DATA_REQ_ID})">
                                            </c:when>
                                            <c:otherwise>
                                                ${record.EXECUTION_STATUS}
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td align="center"><a href="javascript:void(0);" onclick="javascript: viewQuery(${record.DATA_REQ_ID}, '${record.EXECUTION_STATUS}');">View</a></td>
                                    <td align="center">${record.EXECUTED_BY}</td>
                                    <td align="center" style="display: none">${record.EXECUTED_FROM_HOST}</td>
                                    <td align="center">
                                        <c:if test="${record.EXECUTION_DATE ne null}">
                                            <fmt:formatDate pattern="dd-MM-yyyy" value="${record.EXECUTION_DATE}"></fmt:formatDate>
                                        </c:if>
                                        <c:if test="${record.EXECUTION_DATE eq null}">
                                            ---
                                        </c:if>
                                    </td>
                                </tr>
                                <c:set var="cnt" value="${cnt + 1}"></c:set>
                            </c:forEach>
                            <c:if test="${cnt eq 0}">
                                <tr>
                                    <td align="right" colspan="13">
                                        No Records Found.
                                    </td>
                                </tr>
                            </c:if>
                        </tbody>
                        <tfoot id="tfooter">
                            <tr>
                                <td colspan="13" align="center" id="closedPagerDiv"></td>
                            </tr>
                        </tfoot>
                    </table>
                </div>
            </td>
        </tr>
    </c:when>
    <c:when test="${AccessMsg ne null}">
        <tr style="display: none">
            <td>
                <h2> ${AccessMsg} </h2>
            </td>
        </tr>
    </c:when>
    <c:when test="${result ne null}">
        <tr style="display: none">
            <td id="trResult">${result}</td>
        </tr>
    </c:when>
    <c:when test="${action eq 'reportLoad'}">
        <table style="width:100%" class="rpt_tbl tbl_h4_bg1 fullwidth" cellspacing="0" cellpadding="0"> 
                    <tr class="report_caption">
                        <th align="center" class="th-header" style="background: #A4A4A4"><b>Server Id</b></th>    
                        <th align="center" class="th-header" style="background: #A4A4A4"><b>Server Name</b></th>    
                        <th align="center" class="th-header" style="background: #A4A4A4"><b>Server Type</b></th>   
                        <th align="center" class="th-header" style="background: #A4A4A4"><b>Database Id</b></th>
                        <th align="center" class="th-header" style="background: #A4A4A4"><b>Database Name</b></th>
                        <th align="center" class="th-header" style="background: #A4A4A4"><b>Project Id</b></th>
                        <th align="center" class="th-header" style="background: #A4A4A4"><b>Project Name</b></th>
                    </tr>
                <c:forEach items="${jsonmaster}" var="json">
                    <tr>
                        <td align="center">${json.SERVER_ID}</td>
                        <td align="center">${json.SERVER_NAME}</td>
                        <td align="center">${json.SERVER_TYPE}</td>
                        <td align="center">${json.DATABASE_ID}</td>
                        <td align="center">${json.DATABASE_NAME}</td>
                        <td align="center">${json.PRJ_ID}</td>
                        <td align="center">${json.PRJ_NAME}</td>
                    </tr>
                </c:forEach>
        </table>
    </c:when>    
</c:choose>
