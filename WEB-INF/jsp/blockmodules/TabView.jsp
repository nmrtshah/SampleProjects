<%-- 
    Document   : TabView
    Created on : 23 Feb, 2016, 2:47:54 PM
    Author     : Jigna Patel
--%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${action eq 'addTab'}">
    <div class="report_content" id="addModuleDiv">
        <table align="center" border="0" cellpadding="0" cellspacing="2" class="menu_subcaption" width="100%">
            <tr>
                <td align="right" class="report_content_caption">
                    Server Name <span class="astriek">*</span>:
                </td>
                <td class="report_content_value">
                    <select class="custom_combo_showSSWT " id="serverName" name="serverName">
                        <option value="-1">--Select Server Name--</option>
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
                    URI <span class="astriek">*</span>:
                </td>
                <td class="report_content_value">
                    <input type="text" name="uri" id="uri"/>
                </td>
            </tr>
            <tr>
                <td align="right" class="report_content_caption"></td>
                <td class="report_content_value">
                    <div>Examples: /finstudio/</div>
                    <div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;/finstudio/blockmodules.fin</div>
                    <div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(without Parameters)</div>
                </td>
            </tr>
            <tr>
                <td align="right" class="report_content_caption">
                    Parameter Names :
                </td>
                <td class="report_content_value">
                    <input type="text" name="paramNames" id="paramNames" value="NA" onblur="javascript: setDefaultValue('paramNames');"/>
                </td>
            </tr>
            <tr>
                <td align="right" class="report_content_caption"></td>
                <td class="report_content_value">
                    <div>Examples: Param1,Param2</div>
                </td>
            </tr>
            <tr>
                <td align="right" class="report_content_caption">
                    Parameter Values :
                </td>
                <td class="report_content_value">
                    <input type="text" name="paramValues" id="paramValues" value="NA" onblur="javascript: setDefaultValue('paramValues');"/>
                </td>
            </tr>
            <tr>
                <td align="right" class="report_content_caption"></td>
                <td class="report_content_value">
                    <div>Examples: A1,105</div>
                </td>
            </tr>
            <tr>
                <td align="right" class="report_content_caption">
                    Session Variable Names :
                </td>
                <td class="report_content_value">
                    <input type="text" name="sesVarNames" id="sesVarNames" value="NA" onblur="javascript: setDefaultValue('sesVarNames');"/>
                </td>
            </tr>
            <tr>
                <td align="right" class="report_content_caption"></td>
                <td class="report_content_value">
                    <div>Examples: SesNameA, SesNameB</div>
                </td>
            </tr>
            <tr>
                <td align="right" class="report_content_caption">
                    Session Variable Values :
                </td>
                <td class="report_content_value">
                    <input type="text" name="sesVarValues" id="sesVarValues" value="NA" onblur="javascript: setDefaultValue('sesVarValues');"/>
                </td>
            </tr>
            <tr>
                <td align="right" class="report_content_caption"></td>
                <td class="report_content_value">
                    <div>Examples: SesValueA1;SesValueA2;SesValueA3,</div>
                    <div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;SesValueB1;SesValueB2</div>
                </td>
            </tr>
            <tr>
                <td align="right" class="report_content_caption">
                    Access Flag <span class="astriek">*</span>:
                </td>
                <td class="report_content_value">
                    <!--<input type="radio" id="accessAllowed" name="accessLog" value="Grant" class="radio"/><label>Grant</label>-->
                    <input type="radio" id="accessDenied" name="accessLog" value="Denied" class="radio" checked="checked"/><label>Denied</label>
                </td>
            </tr>
            <tr>
                <td align="right" class="report_content_caption">
                    Allow IP <span class="astriek">*</span>:
                </td>
                <td class="report_content_value">
                    <input type="text" name="allowIpAddress" id="allowIpAddress" value="NA" onblur="javascript: setDefaultValue('allowIpAddress');"/>
                </td>
            </tr>
            <tr>
                <td align="right" class="report_content_caption"></td>
                <td class="report_content_value">
                    <div>Examples: 192.168.3.61,192.168.3.62</div>
                </td>
            </tr>
            <tr>
                <td align="right" class="report_content_caption">
                    Block Period <span class="astriek">*</span>:
                </td>
                <td class="report_content_value">
                    <input type="radio" id="blockPeriodOpt1" name="blockPeriodOptn" 
                           value="urgent" class="radio" checked="checked"
                           onclick="onBlockPeriodClick();"/><label>Urgently</label>
                    <input type="radio" id="blockPeriodOpt2" name="blockPeriodOptn" 
                           value="schedule" class="radio"
                           onclick="onBlockPeriodClick();"/><label>Scheduled</label>
                </td>
            </tr>
            <tr id="trFromDateId" style="display: none;">
                <td align="right" class="report_content_caption">
                    Block Period - From Date <span class="astriek">*</span>:
                </td>
                <td class="report_content_value">
                    <select class="custom_combo_showSSWT " id="monthFromDate" name="monthFromDate"
                            onchange="javascript:onChangeFromMonth('dayFromDate');"
                            style="width: 137px !important;margin-right:5px;margin-bottom: 5px;">
                        <option value="-1">--Select Month--</option>
                        <c:forEach begin="1" end="12" step="1" varStatus="month">
                            <option value="${month.count}">${month.count}</option>
                        </c:forEach>
                    </select>
                    <select class="custom_combo_showSSWT " id="dayFromDate" name="dayFromDate" style="width: 137px !important;margin-right:5px;margin-bottom: 5px;">
                        <option value="-1">--Select Day--</option>
                        <c:forEach begin="1" end="31" step="1" varStatus="day">
                            <option value="${day.count}">${day.count}</option>
                        </c:forEach>
                    </select>
                    <br>
                    <select class="custom_combo_showSSWT " id="hourFromDate" name="hourFromDate" style="width: 137px !important;margin-right:5px;margin-bottom: 5px;">
                        <option value="-1">--Select Hour--</option>
                        <c:forEach begin="1" end="24" step="1" varStatus="hour">
                            <c:choose>
                                <c:when test="${(hour.count-1) < 10}">
                                    <option value="${hour.count-1}">0${hour.count-1}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${hour.count-1}">${hour.count-1}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                    <select class="custom_combo_showSSWT " id="minFromDate" name="minFromDate" style="width: 137px !important;margin-right:5px;margin-bottom: 5px;">
                        <option value="-1">--Select Min--</option>
                        <c:forEach begin="1" end="60" step="1" varStatus="min">
                            <c:choose>
                                <c:when test="${(min.count-1) < 10}">
                                    <option value="${min.count-1}">0${min.count-1}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${min.count-1}">${min.count-1}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr id="trToDateId" style="display: none;">
                <td align="right" class="report_content_caption">
                    Block Period - To Date <span class="astriek">*</span>:
                </td>
                <td class="report_content_value">
                    <select class="custom_combo_showSSWT " id="monthToDate" name="monthToDate"  
                            onchange="javascript:onChangeToMonth('dayToDate');"
                            style="width: 137px !important;margin-right:5px;margin-bottom: 5px;">
                        <option value="-1">--Select Month--</option>
                        <c:forEach begin="1" end="12" step="1" varStatus="month">
                            <option value="${month.count}">${month.count}</option>
                        </c:forEach>
                    </select>
                    <select class="custom_combo_showSSWT " id="dayToDate" name="dayToDate" style="width: 137px !important;margin-right:5px;margin-bottom: 5px;">
                        <option value="-1">--Select Day--</option>
                        <c:forEach begin="1" end="12" step="1" varStatus="day">
                            <option value="${day.count}">${day.count}</option>
                        </c:forEach>
                    </select>
                    <br>
                    <select class="custom_combo_showSSWT " id="hourToDate" name="hourToDate" style="width: 137px !important;margin-right:5px;margin-bottom: 5px;">
                        <option value="-1">--Select Hour--</option>
                        <c:forEach begin="1" end="24" step="1" varStatus="hour">
                            <c:choose>
                                <c:when test="${(hour.count-1) < 10}">
                                    <option value="${hour.count-1}">0${hour.count-1}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${hour.count-1}">${hour.count-1}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                    <select class="custom_combo_showSSWT " id="minToDate" name="minToDate" style="width: 137px !important;margin-right:5px;margin-bottom: 5px;">
                        <option value="-1">--Select Min--</option>
                        <c:forEach begin="1" end="60" step="1" varStatus="min">
                            <c:choose>
                                <c:when test="${(min.count-1) < 10}">
                                    <option value="${min.count-1}">0${min.count-1}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${min.count-1}">${min.count-1}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td align='right' class="report_content_caption">
                    Block Message <span class="astriek">*</span>:
                </td>
                <td class="report_content_value">
                    <textarea rows="3" cols="2" id="message" name="message">System is unavailable due to maintenance activity. It will be available soon.</textarea>
                </td>
            </tr>
            <tr>
                <td align="center" colspan="2">
                    <input class="button" type="button" id="btnSubmit" name="btnSubmit" value="Submit" onclick="javascript: insertBlockEntry();" />
                    <input class="button" type="button" id="btnReset" name="btnReset" value="Reset" onclick="javascript: resetAllFields();" />
                </td>
            </tr>
        </table>
    </div>
</c:if>
<c:if test="${action eq 'insertEntry' || action eq 'updateEntry'}">
    <div id="divMsg" style="display: none">
        <input type="hidden" id="hdnDbMsg" name="hdnDbMsg" value="${DBopration}" />
    </div>
</c:if>
<c:if test="${action eq 'monthwiseDays'}">
    <option value="-1">--Select Day--</option>
    <c:forEach begin="1" end="${daysCount}" step="1" varStatus="day">
        <option value="${day.count}">${day.count}</option>
    </c:forEach>
</c:if>
<c:if test="${action eq 'reportTab'}">
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
                            <option value="-1">--Select Server Name--</option>
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
                        User Name : 
                    </td>
                    <td class="report_content_value">
                        <select class="custom_combo_showSSWT" id="userName" name="userName">
                            <option value="-1">-- Select User Name --</option>
                            <c:forEach items="${userNameList}" var="usrLst">
                                <option value="${usrLst.USER_NAME}">${usrLst.USER_NAME}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td align="right" class="report_content_caption">
                        Action :
                    </td>
                    <td class="report_content_value">
                        <input type="radio" id="actionNone" name="blockAction" value="actnNone" class="radio" checked="checked"/><label>All</label>
                        <input type="radio" id="actionBlock" name="blockAction" value="actnBlock" class="radio"/><label>Block</label>
                        <input type="radio" id="actionUnblock" name="blockAction" value="actnUnblock" class="radio"/><label>Un-Block</label>
                    </td>
                </tr>
                <tr>
                    <td align="right" class="report_content_caption">
                        Status :
                    </td>
                    <td class="report_content_value">
                        <select class="custom_combo_showSSWT " id="blockStatus" name="blockStatus">
                            <option value="-1">--Select Status--</option>
                            <option value="sts_pen">Pending</option>
                            <option value="sts_auth">Authorized</option>
                            <option value="sts_config">Configured</option>
                            <option value="sts_rej">Rejected</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td align="right" class="report_content_caption">
                        From Date : 
                    </td>
                    <td class="report_content_value">
                        <input type="text" class="datepickerclass" id="filterFromDate" name="filterFromDate" value="${none}"/>
                    </td>
                </tr>
                <tr>
                    <td align="right" class="report_content_caption">
                        To Date : 
                    </td>
                    <td class="report_content_value">
                        <input type="text" class="datepickerclass" id="filterToDate" name="filterToDate" value="${none}"/>
                    </td>
                </tr>
                <tr>
                    <td align="center" colspan="2">
                        <input class="button" type="button" id="btnSubmit" name="btnSubmit" value="Submit" onclick="javascript: getReportData();" />
                    </td>
                </tr>
            </table>
        </div>
        <div class="clear" style="clear: both"></div>
            <div id="reportGridDiv" style="display: block">
                <div id="reportGrid"></div>
                <div id="reportPagingArea" align="center" style="display: block"></div>
                <div id="reportRecinfoArea" align="center" style="display: none;"></div>
            </div>
        </div>
</c:if>
<c:if test="${action eq 'reportTabJson'}">
    ${reportJson}
</c:if>
<c:if test="${action eq 'authoriseTab'}">
    <div class="clear" style="clear: both"></div>
    <div id="authoriseGridDiv" style="display: block">
        <div id="authoriseGrid"></div>
        <div id="authorisePagingArea" align="center" style="display: block"></div>
        <div id="authoriseRecinfoArea" align="center" style="display: none;"></div>
    </div>
</c:if>
<c:if test="${action eq 'authoriseTabJson'}">
    ${reportJson}
</c:if>