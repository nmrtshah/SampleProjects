<%-- TCIGBF --%>

<%-- 
    Document   : AddReport
    Created on : 23 Jul, 2013, 11:32:15 AM
    Author     : Sonam Patel
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:choose>
    <c:when test="${records ne null}">
        <tr>
            <td align="center">
                <div class="tbl_h1_bg">
                    <div class="menu_caption_text" style="color: orange;">Data Request Report</div>
                </div>
            </td>
        </tr>
        <tr>
            <td>
                <div>
                    <table class="rpt_tbl tbl_h4_bg1" cellpadding="0" cellspacing="0">
                        <thead>
                            <tr class="report_caption">
                                <th class="th-header" style="background-image: none;" align="center" width="50"><b>Server</b></th>
                                <th class="th-header" style="background-image: none;" align="center" width="50"><b>Database</b></th>
                                <th class="th-header" style="background-image: none;" align="center" width="250"><b>Query</b></th>
                                <th class="th-header" style="background-image: none;" align="center" width="20"><b>Status</b></th>
                                <th class="th-header" style="background-image: none;" align="center" width="100"><b>Result</b></th>
                                <th class="th-header" style="background-image: none;" align="center" width="50"><b>Time Taken (ms)</b></th>
                                <th class="th-header" style="background-image: none;" align="center" width="30">
                                    <b>Backup Required?</b>
                                    <c:if test="${backup eq 'YES'}">
                                        <input type="checkbox" id="chkSelAllBkup" name="chkSelAllBkup" onclick="javascript: selectAllBackupChk();" />
                                    </c:if>
                                </th>
                                <th class="th-header" style="background-image: none;" align="center" width="30">
                                    <b>Log Table Required?</b>
                                    <c:if test="${logTable eq 'YES'}">
                                        <input type="checkbox" id="chkSelAllLTab" name="chkSelAllLTab" onclick="javascript: selectAllLogTableChk();" />
                                    </c:if>
                                </th>
                                <th class="th-header" style="background-image: none;" align="center" width="30"><b>Dependency</b></th>
                                <th class="th-header" style="background-image: none;" align="center" width="20"><b>Remove</b></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${records}" var="record">
                                <c:if test="${record.rowspan ne -1}">
                                    <tr>
                                        <td align="left" rowspan="${record.rowspan+1}" style="vertical-align: top;">${record.SERVER_NAME}</td>
                                        <td align="left" rowspan="${record.rowspan+1}" style="vertical-align: top;">${record.DATABASE_NAME}</td>
                                        <td colspan="7" align="center" style="background-color: darkgray;"><b>Executed On ${record.SYNTAX_VERIFIED_ON}</b></td>
                                        <td align="center" rowspan="${record.rowspan+1}" style="vertical-align: top;">
                                            <a href="javascript:void(0);" onclick="javascript: deleteBatch(${record.BATCH_TMP_ID});">
                                                <img src="${finlib_path}/resource/images/delete.gif" alt="Delete" style="height: 20px; width: 20px;">
                                            </a>
                                        </td>
                                    </tr>
                                </c:if>
                                <tr<c:if test="${record.DEV_EXE_STATUS eq 'FAIL'}"> style="color: red;"</c:if>>
                                    <td align="left"><pre>${fn:escapeXml(record.QUERY_TEXT)}</pre></td>
                                    <td align="center" style="vertical-align: top;">${record.DEV_EXE_STATUS}</td>
                                    <td align="left" style="vertical-align: top;">${record.DEV_EXE_RESULT}</td>
                                    <td align="center" style="vertical-align: top;">${record.TIMETAKEN}</td>
                                    <td align="center" style="vertical-align: top;">
                                        <c:if test="${record.BACKUP eq 'NA'}">
                                            NA
                                        </c:if>
                                        <c:if test="${record.BACKUP eq 'Backup Required'}">
                                            <input type="checkbox" checked disabled />
                                        </c:if>
                                        <c:if test="${record.BACKUP eq 'Backup Not Required'}">
                                            <input type="checkbox" id="chkAddBackup${record.QUERY_TMP_ID}" name="chkAddBackup" onclick="javascript: chkAddBackupOnclick(this, ${record.QUERY_TMP_ID});" />
                                            <input type="hidden" id="hdnAddBackup${record.QUERY_TMP_ID}" name="hdnAddBackup" value="NA" />
                                        </c:if>
                                        <c:if test="${record.BACKUP eq 'Backup Not Possible'}">
                                            <label style="color: red;" title=" Either Query Or Table Structure is Incorrert">Backup Not Possible</label>
                                        </c:if>
                                    </td>
                                    <td align="center" style="vertical-align: top;">
                                        <c:if test="${record.LOG_TABLE eq 'NA'}">
                                            NA
                                        </c:if>
                                        <c:if test="${record.LOG_TABLE eq 'Log Table Not Required'}">
                                            <input type="checkbox" id="chkAddLogTable${record.QUERY_TMP_ID}" name="chkAddLogTable" onclick="javascript: chkAddLogTableOnclick(this, ${record.QUERY_TMP_ID});" />
                                            <input type="hidden" id="hdnAddLogTable${record.QUERY_TMP_ID}" name="hdnAddLogTable" value="NA" />
                                        </c:if>
                                        <c:if test="${record.LOG_TABLE eq 'Log Schema Not Available'}">
                                            <span style="color: red;">Log Schema Not Available</span>
                                        </c:if>
                                    </td>
                                    <td align="center" style="vertical-align: top;">
                                        <c:if test="${record.DEPENDENCY eq 'YES'}">
                                            <span id="dep${record.QUERY_TMP_ID}" name="dep${record.QUERY_TMP_ID}" style="width: 50px; cursor: pointer; text-align: center;" onclick="javascript: showDepPopup('dep${record.QUERY_TMP_ID}', ${record.QUERY_TMP_ID}, 'Add');">
                                                <img src="images/info.png" alt="Dependency" />
                                            </span>
                                        </c:if>
                                        <c:if test="${record.DEPENDENCY eq 'NO'}">
                                            NA
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </td>
        </tr>
        <tr>
            <td align="center" colspan="0">
                <input class="button" type="button" id="btnConfirm" name="btnConfirm" Value="Submit Request" onclick="javascript: confirmRequest('');">
                <input class="button" type="button" id="btnConfirm" name="btnConfirm" Value="Submit & Halt Request" onclick="javascript: confirmRequest('Halt');">
                <input class="button" type="button" id="btnCancel" name="btnCancel" Value="Cancel" onclick="javascript: cancelAddRequest();">
            </td>
        </tr>
        <tr style="display: none;">
            <td align="center" colspan="0">
                <div id="divDepResult"></div>
            </td>
        </tr>
        <tr style="display: none;">
            <td align="center" colspan="0">
                <input type="hidden" id="hdnConfirmation" name="hdnConfirmation" value="${confirmation}" disabled />
            </td>
        </tr>
    </c:when>
    <c:when test="${depRecords ne null}">
        <c:forEach items="${depRecords}" var="depRecord" varStatus="loop">
            <c:if test="${loop.index gt 0}">
                <br>
            </c:if>
            ${depRecord.DEPENDENCY} [${depRecord.SERVER_NAME} : ${depRecord.SCHEMA_NAME}<c:if test="${depRecord.OBJ_NAME ne null}">.${depRecord.OBJ_NAME}<c:if test="${depRecord.COLUMN_NAME ne null}">.${depRecord.COLUMN_NAME}</c:if></c:if>]
        </c:forEach>
    </c:when>
</c:choose>
