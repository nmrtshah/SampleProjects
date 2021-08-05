<%-- 
    Document   : TabView
    Created on : 8 Nov, 2017, 6:58:49 PM
    Author     : Jigna Patel
--%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


    
<c:if test="${action eq 'viewTab'}">
    <div>
        <div class="menu_caption_bg" style="width:100% !important;">
            <div class="menu_caption_text">Filter</div>
        </div>
        <div class="report_content" id="filterModuleDiv">
            <table align="center" border="0" cellpadding="0" cellspacing="2" class="menu_subcaption" width="100%">
                <tr>
                    <td align="right" class="report_content_caption">
                        Server Name :
                    </td>
                    <td class="report_content_value">
                        <select class="custom_combo_showSSWT " id="serverName" name="serverName">
                            <option value="-1">-- Select Server Name --</option>
                            <option value="prodhoweb1.nj">prodhoweb1.nj</option>
                            <option value="prodhoweb2.nj">prodhoweb2.nj</option>
                            <option value="prodhoweb3.nj">prodhoweb3.nj</option>
                            <option value="prodhoweb4.nj">prodhoweb4.nj</option>
                            <option value="prodgroupemail.nj">prodgroupemail.nj</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td align="right" class="report_content_caption">
                        Project Name : 
                    </td>
                    <td class="report_content_value">
                        <select class="custom_combo_showSSWT" id="projectName" name="projectName">
                            <option value="-1">-- Select Project Name --</option>
                            <c:forEach items="${projectNameList}" var="prjLst">
                                <option value="${prjLst.PRJ_NAME}">${prjLst.PRJ_NAME}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td align="right" class="report_content_caption">
                        Assigned To : 
                    </td>
                    <td class="report_content_value">
                        <select class="custom_combo_showSSWT" id="viewAssignTo" name="viewAssignTo">
                            <option value="-1">-- Select Assigned To --</option>
                            <c:forEach items="${empList}" var="lst">
                                <option value="${lst.EMP_CODE}">${lst.EMP_NAME}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td align="right" class="report_content_caption">
                        Status :
                    </td>
                    <td class="report_content_value">
                        <select class="custom_combo_showSSWT " id="status" name="status">
                            <option value="-1">-- Select Status --</option>
                            <option value="sts_pending">Pending</option>
                            <option value="sts_assigned">Assigned</option>
                            <option value="sts_solved">Solved</option>
                            <option value="sts_cancelled">Cancelled</option>
                            <option value="sts_falsepositv">False Positive</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td align="center" colspan="2">
                        <input class="button" type="button" id="btnSubmit" name="btnSubmit" value="Submit" onclick="javascript: getViewData();" />
                    </td>
                </tr>
            </table>
        </div>
        <div class="clear" style="clear: both"></div>
        <div id="viewGridMainDiv" class="menu_new" style="width: 100%;display: none;">
            <div class="menu_caption_bg" onclick="javascript: hide_menu('report_hide_req_list', 'viewGridDiv');">
                <table cellspacing="0" cellpadding="0" class="tbl_h1_bg" style="width: 97%;">
                    <tbody>
                        <tr>
                            <td>
                                <div class="menu_caption_text">Request List</div>
                            </td>
                            <td width="25">
                                <span style="margin-right: 2px" class="export_radio">
                                    <a href="javascript: generateExcel();" id="export_xls"><span></span></a>
                                </span>
                            </td>
                            <td width="25">
                                <span>
                                    <a class="nav_hide" href="javascript:void(0);" name="report_hide_req_list" id="report_hide_req_list"></a>
                                </span>
                            </td>
                        </tr>
                    </tbody>
              </table>
            </div>
            <div id="viewGridDiv" style="display: block">
                <div id="viewGrid"></div>
                <div id="viewPagingArea" align="center" style="display: block"></div>
                <div id="viewRecinfoArea" align="center" style="display: none;"></div>
            </div>
        </div>
    </div>
</c:if>
<c:if test="${action eq 'updateReqStatus' || action eq 'updateAssignStatus'}">
    <div id="divMsg" style="display: none">
        <input type="hidden" id="hdnDbMsg" name="hdnDbMsg" value="${DBopration}" />
    </div>
</c:if>
<c:if test="${action eq 'assignTab'}">
    <div class="clear" style="clear: both"></div>
    <div id="assignGridDiv" style="display: block;">
        <div id="assignGrid"></div>
        <div id="assignPagingArea" align="center" style="display: block"></div>
        <div id="assignRecinfoArea" align="center" style="display: none;"></div>
        <input type="button" class="button" id="btnChangeSts"
               value="Change Status" onclick="javascript: onChangeStsClick()">
        <input type="button" class="button" id="btnReset"
               value="Reset" onclick="javascript: onResetClick()">
    </div>
</c:if>
<c:if test="${action eq 'viewGrid'}">
    <rows>
        <head>
            <column type="ro" align="center" width="50" sort="na">Sr No.</column>
            <column type="ro" align="left" width="210" sort="na">Request URI</column>
            <column type="ro" align="center" width="90" sort="na">Assigned To</column>
            <column type="ro" align="center" width="75" sort="na">Status</column>
            <column type="ro" align="center" width="75" sort="na">Since Hours</column>
            <column type="ro" align="center" width="50" sort="na">Request Count</column>
            <column type="ro" align="center" width="90" sort="na">Last Update</column>
            <column type="ro" align="center" width="153" sort="na">Remark</column>
            <column type="ro" align="center" width="160" sort="na">Action</column>
            <settings>
                <colwidth>px</colwidth>
            </settings>
        </head>
        <c:if test="${fn:length(viewList) gt 0}">
            <c:forEach var="lst" items="${viewList}" varStatus="lstStatus">
                <row id="${lstStatus.count}">
                    <cell>${lstStatus.count}</cell>
                    <cell><c:out value="${lst.NEW_URI}"/></cell>
                    <cell><c:out value="${lst.EMP_NAME}"/></cell>
                    <cell><c:out value="${lst.STATUS}"/></cell>
                    <cell><c:out value="${lst.SINCE_HOURS}"/></cell>
                    <cell><c:out value="${lst.REQUEST_COUNT}"/></cell>
                    <cell><c:out value="${lst.LAST_UPDATED_DATE}"/></cell>
                    <cell>
                        <c:choose>
                            <c:when test="${lst.STATUS eq 'ASSIGNED'}">
                                <![CDATA[<textarea id="rem_${lst.SRNO}" style="width: 98%;" rows="3"></textarea>]]>
                            </c:when>
                            <c:otherwise> - </c:otherwise>
                        </c:choose>
                    </cell>
                    <cell>
                        <c:choose>
                            <c:when test="${(lst.STATUS eq 'ASSIGNED') && (empCode eq lst.ASSIGNED_TO)}">
                                <![CDATA[<input type="button" class="button" value="Solved" onclick="javascript: updateStatus('sts_solved',${lst.SRNO})">
                                         <input type="button" class="button" value="Cancelled" onclick="javascript: updateStatus('sts_cancelled',${lst.SRNO})">]]>
                            </c:when>
                            <c:otherwise> - </c:otherwise>
                        </c:choose>
                    </cell>
                </row>
            </c:forEach>
        </c:if>
    </rows>
</c:if>
<c:if test="${action eq 'assignGrid'}">
    <rows>
        <head>
            <column type="ro" align="center" width="30" sort="na">Request Id</column>
            <column type="ro" align="center" width="50" sort="na">Sr No.</column>
            <column type="ro" align="left" width="378" sort="na">Request URI</column>
            <column type="ro" align="center" width="90" sort="na">Status</column>
            <column type="ro" align="center" width="75" sort="na">Since Hours</column>
            <column type="ro" align="center" width="50" sort="na">Request Count</column>
            <column type="ro" align="center" width="90" sort="na">Last Update</column>
            <column type="ro" align="center" width="120" sort="na">Remark</column>
            <column type="ro" align="center" width="100" sort="na"><![CDATA[<input style="float: none;margin: 5px;" class="checkbox" id="chkAll" onclick="javascript: onAllChkClicked()" type="checkbox">]]>
            </column>
            <settings>
                <colwidth>px</colwidth>
            </settings>
        </head>
        <c:if test="${fn:length(assignList) gt 0}">
            <c:forEach var="lst" items="${assignList}" varStatus="lstStatus">
                <row id="${lstStatus.count}" style="height: 50px !important;">
                    <cell><c:out value="${lst.SRNO}"></c:out></cell>
                    <cell>${lstStatus.count}</cell>
                    <cell><c:out value="${lst.NEW_URI}"/></cell>
                    <cell><c:out value="${lst.STATUS}"/></cell>
                    <cell><c:out value="${lst.SINCE_HOURS}"/></cell>
                    <cell><c:out value="${lst.REQUEST_COUNT}"/></cell>
                    <cell><c:out value="${lst.LAST_UPDATED_DATE}"/></cell>
                    <cell><c:out value="${lst.REMARKS}"/></cell>
                    <cell><![CDATA[ <input type="checkbox" class="checkbox" style="float: none !important"
                                           name="chkAssignAction" value=",'${lst.SRNO}'"
                                           onclick="javascript: onActionChkClicked()"> ]]></cell>
                </row>
            </c:forEach>
        </c:if>
    </rows>
</c:if>