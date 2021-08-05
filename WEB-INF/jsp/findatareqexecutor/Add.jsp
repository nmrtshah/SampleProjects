<%-- TCIGBF --%>

<%-- 
    Document   : Add
    Created on : May 8, 2013, 11:17:57 AM
    Author     : Sonam Patel
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="common" uri="http://www.njtechnologies.in/tags/filebox" %>

<c:choose>
    <c:when test="${process eq 'main'}">
        <div class="menu_caption_bg">
            <div class="menu_caption_text">Add</div>
        </div>
        <div class="content" id="ProjectSpecification" style="min-height: 0px;">
            <table align="center" border="0" cellpadding="0" cellspacing="2" class="menu_subcaption" width="80%">
                <tr>
                    <td align="right" class="report_content_caption">
                        <sup style="color: red">*</sup>Project : 
                    </td>
                    <td class="report_content_value">
                        <select id="cmbAddProjId" name="cmbAddProjId" onchange="javascript: fillAddRequest();" tabindex="1">
                            <option value="-1">-- Select Project --</option>
                            <c:forEach items="${proj_ids}" var="varproj_id">
                                <option value="${varproj_id.PRJ_ID}">${varproj_id.PRJ_NAME}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td align="right" class="report_content_caption">
                        Request No : 
                    </td>
                    <td class="report_content_value">
                        <input type="text" id="txtAddReqId" name="txtAddReqId" onblur="javascript: fillAddRequest();" style="width: 80px" tabindex="2" />
                        <select id="cmbAddReqId" name="cmbAddReqId" style="float: none; width: 189px !important" tabindex="3">
                            <option value="-1">-- Select Request No --</option>
                            <c:forEach items="${req_nos}" var="varreq_no">
                                <option value="${varreq_no.WORK_REQUEST_ID}">${varreq_no.WORK_REQUEST_ID} - ${varreq_no.TITLE}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td align="right" class="report_content_caption">
                        Purpose : 
                    </td>
                    <td class="report_content_value">
                        <textarea rows="3" cols="2" id="txtAddPurpose" name="txtAddPurpose" tabindex="4">${purpose}</textarea>
                    </td>
                </tr>
                <tr>
                    <td align="right" class="report_content_caption">
                        <sup style="color: red">*</sup>Execution Type : 
                    </td>
                    <td class="report_content_value">
                        <select id="cmbAddExecType" name="cmbAddExecType" tabindex="5">
                            <option value="-1">-- Select Execution Type --</option>
                            <option value="1" selected>Execute ALL</option>
                            <option value="2">Stop on Error</option>
                            <option value="3">Rollback on Error (Only DML & Single Server)</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td align="center" colspan="2">
                        <input class="button" type="button" id="btnNext" name="btnNext" Value="Next" tabindex="6" onclick="javascript: showQuerySpecification();">
                        <div id="divPrjMapResult" style="display: none"></div>
                    </td>
                </tr>
            </table>
        </div>
        <div class="content" id="QuerySpecification" style="min-height: 0px;">
            <input type="hidden" id="hdnUSessionId" name="hdnUSessionId" value="${u_session_id}" disabled>
            <div id="divDbType" style="display: none"></div>
            <table align="center" border="0" cellpadding="0" cellspacing="2" class="menu_subcaption" width="80%">
                <tr>
                    <td align="right" class="report_content_caption" style="width: 30%">
                        <sup style="color: red">*</sup>Server : 
                    </td>
                    <td class="report_content_value" style="width: 70%">
                        <select id="cmbAddDatabase" name="cmbAddDatabase" tabindex="7">
                            <option value="-1">-- Select Server --</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td align="right" class="report_content_caption" style="width: 30%">
                        Verify Syntax On : 
                    </td>
                    <td class="report_content_value" style="width: 70%">
                        <input type="radio" checked id="rdoAddVerifyNone" name="rdoAddVerifyServer" tabindex="8" value="none"> None
                        <input type="radio" id="rdoAddVerifyTest" name="rdoAddVerifyServer" onchange="javascript: alertForDDL();" value="test"> Test
                        <input type="radio" id="rdoAddVerifyDev" name="rdoAddVerifyServer" onchange="javascript: alertForDDL();" value="dev"> Dev
                    </td>
                </tr>
                <tr>
                    <td align="right" style="vertical-align: top">
                        Notes:
                    </td>
                    <td>
                        <div style="font-weight: normal !important;font-size: 9pt;color: red;">
                            <span style="text-decoration: underline">Mysql</span><br>
                            * First statement must be : USE &lt;schema_name&gt;<br>
                            * Schema Name <b>MUST NOT</b> be specified with ANY table name in any statement.<br>
                            <span style="text-decoration: underline">Oracle</span><br>
                            * Schema Name <b>MUST</b> be specified with EVERY table name in every statement.<br>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td class="report_content_value" style="width: 70%">
                        <input type="radio" id="rdoTextarea" name="rdoInput" checked onchange="javascript: showInputTr();" value="textarea" tabindex="9"> Textarea
                        <input type="radio" id="rdoFile" name="rdoInput" onchange="javascript: showInputTr();" value="file"> File Upload
                        <span id="supportState" name="supportState" style="float: right; width: 50px; cursor: pointer; text-align: center" onclick="javascript: showPopup();">
                            <img src="images/question.png" alt="Supported Statements" />
                        </span>
                    </td>
                </tr>
                <tr id="trTextarea">
                    <td align="right" class="report_content_caption" style="width: 30%">
                        <sup style="color: red">*</sup>Query : <br>
                        (Separated by 'GO')
                    </td>
                    <td class="report_content_value" style="width: 70%">
                        <textarea rows="8" cols="2" id="txtAddQuery" name="txtAddQuery" style="width: 500px" tabindex="10"></textarea>
                    </td>
                </tr>
                <tr id="trFile" style="display: none;">
                    <td align="right" class="report_content_caption" style="width: 30%">
                        File : <br>
                        (Queries Separated by 'GO')
                    </td>
                    <td class="report_content_value" style="width: 70%; padding-left:20px;">
                        <common:filebox controlid="fileQuery" name="fileQuery" maxfiles="1" maxsize="10240" elementname="queryFileName" formid="datareqexecutorForm" type="txt" value="Upload File"></common:filebox>
                    </td>
                </tr>
                <tr>
                    <td align="center" colspan="2">
                        <input class="button" type="button" id="btnAdd" name="btnAdd" Value="Add More" tabindex="11" onclick="javascript: insertTempData();">
                    </td>
                </tr>
            </table>
        </div>
        <div class="content" id="Finish" style="min-height: 0px;">
            <table align="center">
                <tr>
                    <td align="center">
                        Your Data Request ID is : ${dataReqID}
                    </td>
                </tr>
                <tr>
                    <td align="center">
                        <input class="button" type="button" name="btnBack" value="Back" onclick="javascript: AddLoader();" />
                    </td>
                </tr>
            </table>
        </div>
        <div id="divformatResult"></div>
        <div id="divAnalysisResult"></div>
    </c:when>
    <c:when test="${process eq 'addReq'}">
        <option value="-1">-- Select Request No --</option>
        <c:forEach items="${req_nos}" var="req_no">
            <option value="${req_no.WORK_REQUEST_ID}">${req_no.WORK_REQUEST_ID} - ${req_no.TITLE}</option>
        </c:forEach>
    </c:when>
    <c:when test="${process eq 'actualServer'}">
        <c:if test="${actual_server ne null and actual_server ne ''}">
            <select id="cmbAddTmpDb" name="cmbAddTmpDb">
                <option value="-1">-- Select Server --</option>
                <c:forEach items="${actual_server}" var="server">
                    <option value="${server.DATABASE_ID}">${server.SERVER_NAME}(${server.SERVER_TYPE}) - ${server.DATABASE_NAME}</option>
                </c:forEach>
            </select>
        </c:if>
        <c:if test="${prjUsrMapResult ne null and prjUsrMapResult ne ''}">
            <input type="hidden" id="tmpPrjUsrMapRes" name="tmpPrjUsrMapRes" value="${prjUsrMapResult}" disabled />
        </c:if>
    </c:when>
    <c:when test="${process eq 'formatQuery'}">
        <textarea id="txtAddFmtQry" name="txtAddFmtQry" disabled style="display: none">${queryText}</textarea>
    </c:when>
    <c:when test="${process eq 'analysis'}">
        <c:set var="search" value='"' />
        <c:set var="replace" value='&QUOT;' />
        <c:if test="${prjDbMapResult ne null and prjDbMapResult ne ''}">
            <input type="hidden" id="tmpPrjDbMapRes" name="tmpPrjDbMapRes" value="${prjDbMapResult}" disabled />
        </c:if>
        <c:if test="${result.message ne null and result.message ne ''}">
            <input type="hidden" id="analysisMsg" name="analysisMsg" value="${fn:replace(result.message, search, replace)}" disabled />
        </c:if>
        <c:if test="${result.query ne null and result.query ne ''}">
            <input type="hidden" id="analysisQuery" name="analysisQuery" value="${fn:replace(result.query, search, replace)}" disabled />
        </c:if>
        <c:if test="${(result.message eq null or result.message eq '') and (prjDbMapResult eq null or prjDbMapResult eq '')}">
            <input type="hidden" id="analysisClear" name="analysisClear" />
        </c:if>
    </c:when>
</c:choose>
