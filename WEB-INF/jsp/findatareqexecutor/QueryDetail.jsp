<%-- TCIGBF --%>

<%-- 
    Document   : QueryDetail
    Created on : May 8, 2013, 11:17:57 AM
    Author     : Sonam Patel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <title>Fin Data Request Executor</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="${finlib_path}/resource/main_offline.css">
        <link rel="stylesheet" type="text/css" href="${finlib_path}/resource/jquery.tablesorter.min.css">
        <link rel="stylesheet" type="text/css" href="${finlib_path}/dhtmlxSuite/dhtmlxPopup/codebase/skins/dhtmlxpopup_dhx_web.css">
        <script type="text/javascript" src="${finlib_path}/resource/ajax.js"></script>
        <script type="text/javascript" src="${finlib_path}/resource/validate.js"></script>
        <script type="text/javascript" src="${finlib_path}/dhtmlxSuite/dhtmlxPopup/codebase/dhtmlxpopup.js"></script>
        <script type="text/javascript" src="${finlib_path}/dhtmlxSuite/dhtmlxForm/codebase/dhtmlxcommon.js"></script>
        <script type="text/javascript" src="${finlib_path}/dhtmlxSuite/dhtmlxForm/codebase/dhtmlxform.js"></script>
        <script type="text/javascript" src="${finlib_path}/dhtmlxSuite/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
        <script type="text/javascript" src="${finlib_path}/dhtmlxSuite/dhtmlxWindows/codebase/dhtmlxcontainer.js"></script>
        <script type="text/javascript" src="javascript/FinDataReqExecutor.js"></script>
    </head>
    <body>
        <table cellspacing="0" cellpadding="0" border="0" align="center" class="html-main-report fullwidth">
            <c:choose>
                <c:when test="${records ne null}">
                    <c:set var="srno" value="1"></c:set>
                        <tr>
                            <td>
                                <div class="tbl_h1_bg">
                                    <div style="float: left;">
                                        <a href="javascript:void(0);" id="linkShowHide" onclick="javascript: showVerificationDetail();" 
                                        <c:if test="${showLink eq 'Y'}">style="color: #FFFFFF;"</c:if>
                                        <c:if test="${showLink eq 'N'}">style="color: #FFFFFF; display: none;"</c:if>>Show Syntax Verification Details</a>
                                    </div>
                                    <div align="center" style="color: orange;">
                                    <c:if test="${exeStatus eq 'Halted'}">
                                        ${dataReqId} - On Halt
                                    </c:if>
                                    <c:if test="${exeStatus ne 'Halted'}">
                                        ${dataReqId} - ${exeStatus}
                                    </c:if>
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div>
                                <table id="rptgrid" class="rpt_tbl tbl_h4_bg1 fullwidth" cellpadding="0" cellspacing="0">
                                    <thead>
                                        <tr class="report_caption">
                                            <th align="center" class="th-header" style="background-image: none;" width="20"><b>Sr No.</b></th>
                                            <th align="center" class="th-header" style="background-image: none;" width="50"><b>Server</b></th>
                                            <th align="center" class="th-header" style="background-image: none;" width="50"><b>Database</b></th>
                                            <th align="center" class="th-header" style="background-image: none;" width="100"><b>Query</b></th>
                                            <th align="center" class="th-header" id="thStatus" style="background-image: none; display: none;" width="10"><b>Status</b></th>
                                            <th align="center" class="th-header" id="thResult" style="background-image: none; display: none;" width="100"><b>Result</b></th>
                                            <th align="center" class="th-header" id="thTimeTaken" style="background-image: none; display: none;" width="50"><b>Time Taken (ms)</b></th>
                                            <th align="center" class="th-header" style="background-image: none;" width="10"><b>Status</b></th>
                                            <th align="center" class="th-header" style="background-image: none;" width="100"><b>Result</b></th>
                                            <th align="center" class="th-header" style="background-image: none;" width="50"><b>Time Taken (ms)</b></th>
                                            <th align="center" class="th-header" style="background-image: none;" width="50"><b>Backup</b></th>
                                            <th align="center" class="th-header" style="background-image: none;" width="50"><b>Log Table</b></th>
                                            <th align="center" class="th-header" style="background-image: none;" width="50"><b>Dependency</b></th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${records}" var="record">
                                            <c:if test="${record.rowspan ne -1}">
                                                <tr>
                                                    <td align="center" rowspan="${record.rowspan+1}" style="vertical-align: top;">${srno}</td>
                                                    <td align="left" rowspan="${record.rowspan+1}" style="vertical-align: top;">${record.SERVER_NAME}</td>
                                                    <td align="left" rowspan="${record.rowspan+1}" style="vertical-align: top;">${record.DATABASE_NAME}</td>
                                                    <td align="center" colspan="4" name="tdSynVeri" style="background-color: darkgrey; display: none;">
                                                        <b>Executed On ${record.SYNTAX_VERIFIED_ON} ( for syntax verification )</b>
                                                    </td>
                                                    <td align="center" colspan="7" name="tdServer" style="background-color: darkgrey;"><b>Executed On ${record.SERVER_NAME}</b></td>
                                                </tr>
                                                <c:set var="srno" value="${srno + 1}"></c:set>
                                            </c:if>
                                            <tr>
                                                <td align="left"><pre>${fn:escapeXml(record.QUERY_TEXT)}</pre></td>
                                                <td align="center" name="tdStatus" style="display: none;vertical-align: top;">${record.DEV_EXE_STATUS}</td>
                                                <td align="left" name="tdResult" style="display: none;vertical-align: top;">${record.DEV_EXE_RESULT}</td>
                                                <td align="center" name="tdTimeTaken" style="display: none;vertical-align: top;">${record.DEV_TIMETAKEN}</td>
                                                <c:if test="${record.PROD_EXE_STATUS ne 'FAIL'}">
                                                    <td align="center" style="vertical-align: top;">${record.PROD_EXE_STATUS}</td>
                                                    <td align="left" style="vertical-align: top;">${record.PROD_EXE_RESULT}</td>
                                                    <td align="center" style="vertical-align: top;">${record.PROD_TIMETAKEN}</td>
                                                </c:if>
                                                <c:if test="${record.PROD_EXE_STATUS eq 'FAIL'}">
                                                    <td align="center" style="color: red;vertical-align: top;">${record.PROD_EXE_STATUS}</td>
                                                    <td align="left" style="color: red;vertical-align: top;">${record.PROD_EXE_RESULT}</td>
                                                    <td align="center" style="color: red;vertical-align: top;">${record.PROD_TIMETAKEN}</td>
                                                </c:if>
                                                <td align="center" style="vertical-align: top;">
                                                    <c:if test="${record.BACKUP eq 'Backup Taken' and (fn:startsWith(fn:toUpperCase(record.QUERY_TEXT), 'UPDATE ') or fn:startsWith(fn:toUpperCase(record.QUERY_TEXT), 'DELETE '))}">                                                        
                                                        <c:if test="${validUser eq 'YES'}">
                                                            <a href="generated/DRE_${record.QUERY_ID}.xls">Download</a>
                                                        </c:if>
                                                        <c:if test="${validUser ne 'YES'}">
                                                            <img src="images/secured.png" alt="Access Denied">
                                                        </c:if>
                                                    </c:if>
                                                    <c:if test="${record.BACKUP eq 'Backup Taken' and (fn:startsWith(fn:toUpperCase(record.QUERY_TEXT), 'CREATE OR REPLACE ') or fn:startsWith(fn:toUpperCase(record.QUERY_TEXT), 'DROP '))}">
                                                        <a href="generated/DRE_${record.QUERY_ID}.txt">Download</a>
                                                    </c:if>
                                                    <c:if test="${record.BACKUP ne 'Backup Taken'}">
                                                        <c:if test="${record.BACKUP eq 'Backup Not Possible'}">
                                                            <label style="color: red;" title=" Either Query Or Table Structure is Incorrert">Backup Not Possible</label>
                                                        </c:if>
                                                        <c:if test="${record.BACKUP ne 'Backup Not Possible'}">
                                                            ${record.BACKUP}
                                                        </c:if>
                                                    </c:if>
                                                </td>
                                                <td align="center" style="vertical-align: top;">
                                                    ${record.LOG_TABLE}
                                                </td>
                                                <td align="center" style="vertical-align: top;">
                                                    <c:if test="${record.DEPENDENCY eq 'YES'}">
                                                        <span id="dep${record.QUERY_ID}" name="dep${record.QUERY_ID}" style="width: 50px; cursor: pointer; text-align: center;" onclick="javascript: showDepPopup('dep${record.QUERY_ID}', ${record.QUERY_ID}, 'View');">
                                                            <img src="images/info.png" alt="Dependency" />
                                                        </span>
                                                    </c:if>
                                                    <c:if test="${record.DEPENDENCY eq 'NO'}">
                                                        NA
                                                    </c:if>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                        <tr>
                                            <td align="center" colspan="13">
                                                <input class="button" type="button" value="Close" onclick="window.close();" />
                                            </td>
                                        </tr>
                                        <tr style="display: none;">
                                            <td align="center" colspan="13">
                                                <div id="divDepResult"></div>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </td>
                    </tr>
                </c:when>
            </c:choose>
        </table>
    </body>
</html>
