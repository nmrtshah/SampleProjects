<%--    Document   : testscriptgeneratorAJAX
        Created on : Mar 5, 2012, 2:10:58 PM
        Author     : njuser                     --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:if test="${fillCombo eq 'moduleList'}">
    <select id="moduleID" name="moduleID" onchange="javascript:onChangeModule(this.value)">
        <option value="-1">--Select Module Name--</option>
        <c:forEach items="${moduleList}" var="moduleItem">
            <option value="${moduleItem.MODULE_ID}">${moduleItem.MODULE_NAME}</option>
        </c:forEach>
    </select>
</c:if>
<c:if test="${fillCombo eq 'testCaseList'}">
    <select id="testCaseID" name="testCaseID">
        <option value="-1">--Select Test Case--</option>
        <c:forEach items="${testCaseList}" var="testCaseItem">
            <option value="${testCaseItem.TEST_CASE_ID}">${testCaseItem.TEST_CASE}</option>
        </c:forEach>
    </select>
</c:if>

<c:if test="${fillCombo eq 'accessTypeList'}">
    <select id="accessID" name="accessID">
        <c:forEach items="${accessList}" var="accessItem">
            <option value="${accessItem.ACCESS_ID}">${accessItem.ACCESS_TYPE}</option>
        </c:forEach>
    </select>
</c:if>

<c:if test="${fillCombo eq 'testCaseValueList'}">
    <select id="testCaseValueID" name="testCaseValueID">
        <option value="-1">--Select Test Case Value--</option>
        <c:forEach items="${testCaseValueList}" var="testCaseValueItem">
            <option value="${testCaseValueItem.NATURE}">${testCaseValueItem.VALUE}</option>
        </c:forEach>
    </select>
</c:if>

<c:if test="${fillCombo eq 'controlList'}">
    <c:set var="counter" value="1"/>
    <c:set var="cntrl" value="1"/>    
    <div id="ctmpdata">
        <select id="message" name="message" style="display:none">
            <option value="-1">-- Select --</option>
            <c:forEach items="${controlList}" var="control">
                <c:if test="${control.controlNameID ne null and control.controlNameID ne ''}">
                    <option value="${control.controlNameID}">${control.controlNameID}</option>
                </c:if>
            </c:forEach>
        </select>
    </div>
    <div id="etmpdata">
        <select id="eventID" name="eventID" style="display:none">
            <option value="-1">-- Event --</option>
            <c:forEach items="${eventList}" var="eventItem">
                <option value="${eventItem.EVENT_ID}">${eventItem.EVENT_NAME}</option>
            </c:forEach>
        </select>
    </div>
    <table align="center" id="controleventtable" width="1024px" cellpadding="0px" cellspacing="0px">
        <tbody>
            <c:forEach items="${controlList}" var="controlListItem">
                <c:if test="${controlListItem.controlNameID ne null and controlListItem.controlNameID ne ''}">
                    <tr id="${counter}">
                        <td class="tdleftdata" valign="top">
                            <table id="ControlDetailTable" width="100%" align="center" cellpadding="0px" cellspacing="0px">
                                <tr>
                                    <td class="tdleftdata">
                                        <input type="checkbox" id="chkDelete" name ="chkDelete" value="${counter}"/>
                                    </td>                                                                       
                                    <td class="tdleftdata">
                                        <select name="htmlControlID" id="htmlControlID" style="width:200px">
                                            <option value="${controlListItem.htmlControlID}">${controlListItem.htmlControlName}</option>
                                        </select>
                                    </td>
                                    <td class="tdleftdata">
                                        <input type="text" style="width:120px" name="controlNameID" value="${fn:trim(controlListItem.controlNameID)}" id="controlNameID" readonly>
                                        <input type="hidden" id="access" name="access" value="${controlListItem.access}">
                                        <input type="hidden" id="xpath" name="xpath" value="${controlListItem.xpath}">
                                    </td>
                                    <td class="tdleftdata" id="accesslist">
                                        <c:if test="${(fn:length(controlListItem.valueList)) gt 1}">
                                            <select id="accessID" name="accessID" style="width:90px" onchange="javascript:changeSelectList(this)">
                                                <c:forEach items="${controlListItem.accessList}" var="accessList">
                                                    <option value="${accessList.ACCESS_ID}">${accessList.ACCESS_TYPE}</option>
                                                </c:forEach>
                                            </select>
                                        </c:if>
                                        <c:if test="${(fn:length(controlListItem.valueList)) le 1}">
                                            <select id="accessID" name="accessID" style="width:90px">
                                                <c:forEach items="${controlListItem.accessList}" var="accessList">
                                                    <option value="${accessList.ACCESS_ID}">${accessList.ACCESS_TYPE}</option>
                                                </c:forEach>
                                            </select>
                                        </c:if>
                                    </td>
                                    <td class="tdleftdata">
                                        <c:if test="${controlListItem.valueList ne null and controlListItem.labelList ne null}">
                                            <c:if test="${(fn:length(controlListItem.valueList)) gt 1}">
                                                <select id="controlValue" name="controlValue" style="width:103px">
                                                    <c:forEach begin="0" end="${(fn:length(controlListItem.valueList))-1}" step="1" var="i">
                                                        <option value="${i}">${i}</option>
                                                    </c:forEach>
                                                </select>
                                                <select id="controlValue" name="controlValue" disabled style="display:none;width:103px">
                                                    <c:forEach items="${controlListItem.valueList}" var="valueItem">
                                                        <option value="${valueItem}">${valueItem}</option>
                                                    </c:forEach>
                                                </select>
                                                <select id="controlValue" name="controlValue" disabled style="display:none;width:103px">
                                                    <c:forEach items="${controlListItem.labelList}" var="labelItem">
                                                        <option value="${labelItem}">${labelItem}</option>
                                                    </c:forEach>
                                                </select>
                                                <input type="text" id="controlValue" name="controlValue" disabled style="display:none;width:100px">
                                            </c:if>
                                            <c:if test="${(fn:length(controlListItem.valueList)) le 1}">
                                                <input type="text" style="width:100px" name="controlValue" id="controlValue" value="${controlListItem.controlValue}">
                                            </c:if>
                                        </c:if>
                                        <c:if test="${controlListItem.valueList eq null and controlListItem.labelList eq null}">
                                            <input type="text" style="width:100px" name="controlValue" id="controlValue" value="${controlListItem.controlValue}">
                                        </c:if>
                                    </td>
                                    <td class="tdleftdata" width="20px">
                                        <c:if test="${(fn:length(controlListItem.valueList)) gt 1}">
                                            <input type="button" id="changeBtn" style="width:20px" name="changeBtn" value="T" onclick="javascript:change(this)">
                                        </c:if>                            
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td class="tdleftdata" valign="top">
                            <table id="ControlValueEventDetailTable"  width="100%" align="center" cellpadding="0px" cellspacing="0px">
                                <tbody>
                                    <c:if test="${fn:length(controlListItem.eventID) eq 0}">
                                        <tr id="${cntrl}${cntrl}${counter}0000">
                                            <td class="tdleftdata">
                                                <img src="./images/add.gif" width="20px" height="20px" id="addEvent" name="addEvent"
                                                     onclick="addControlValueGroup(this,${counter})" alt="Add Another Event">
                                            </td>
                                            <td class="tdleftdata">
                                                <select id="eventID" name="eventID" style="width:132px">
                                                    <option value="-1">-- Event --</option>
                                                    <c:forEach items="${eventList}" var="eventItem">
                                                        <option value="${eventItem.EVENT_ID}">${eventItem.EVENT_NAME}</option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                            <td class="tdleftdata">
                                                <select id="testCaseNatureID" name="testCaseNatureID" style="width:auto" onchange="javascript:convert(this,1)">
                                                    <option value="-1">-- Test Case Nature --</option>                                        
                                                    <c:forEach items="${testCaseNatureList}" var="testCaseNatureItem">
                                                        <option value="${testCaseNatureItem.TEST_CASE_NATURE_ID}">${testCaseNatureItem.TEST_CASE_NATURE}</option>
                                                    </c:forEach>                                        
                                                </select>
                                            </td>
                                            <td class="tdleftdata">
                                                <select id="message" name="message" style="width:135px">
                                                    <option value="-1">-- Select --</option>
                                                    <c:forEach items="${controlList}" var="control">
                                                        <c:if test="${control.controlNameID ne null and control.controlNameID ne ''}">
                                                            <option value="${control.controlNameID}">${control.controlNameID}</option>
                                                        </c:if>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                            <td class="tdleftdata">
                                                <input type="button" id="convertBtn" style="width:20px" name="convertBtn" value="T" onclick="convert(this,1)">
                                            </td>
                                        </tr>
                                        <c:set var="cntrl" value="${cntrl+1}"/>
                                    </c:if>
                                    <c:if test="${fn:length(controlListItem.eventID) gt 0}">
                                        <c:forEach var="cnt" begin="0" end="${fn:length(controlListItem.eventID)-1}">
                                            <tr id="${cntrl}${cntrl}${counter}0000">
                                                <td class="tdleftdata">
                                                    <img src="./images/add.gif" width="20px" height="20px" id="addEvent" name="addEvent"
                                                         onclick="addControlValueGroup(this,${counter})" alt="Add Another Event">
                                                </td>
                                                <td class="tdleftdata">                                        
                                                    <c:if test="${fn:length(controlListItem.eventID) gt 0}">
                                                        <select id="eventID" name="eventID" style="width:132px">
                                                            <option value="-1">-- Event --</option>
                                                            <option value="${controlListItem.eventID[cnt]}" selected>${controlListItem.eventName[cnt]}</option>
                                                        </select>
                                                    </c:if>
                                                </td>
                                                <td class="tdleftdata">
                                                    <select id="testCaseNatureID" name="testCaseNatureID" style="width:auto" onchange="javascript:convert(this,1)">
                                                        <option value="-1">-- Test Case Nature --</option>
                                                        <c:forEach items="${testCaseNatureList}" var="testCaseNatureItem">
                                                            <option value="${testCaseNatureItem.TEST_CASE_NATURE_ID}">${testCaseNatureItem.TEST_CASE_NATURE}</option>
                                                        </c:forEach>                                            
                                                    </select>
                                                </td>
                                                <td class="tdleftdata">                                                    
                                                    <select id="message" name="message" style="width:135px">
                                                        <option value="-1">-- Select --</option>
                                                        <c:forEach items="${controlList}" var="control">
                                                            <c:if test="${control.controlNameID ne null and control.controlNameID ne ''}">
                                                                <option value="${control.controlNameID}">${control.controlNameID}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                                <td class="tdleftdata">
                                                    <input type="button" id="convertBtn" style="width:20px" name="convertBtn" value="T" onclick="convert(this,1)">
                                                </td>
                                            </tr>
                                            <c:set var="cntrl" value="${cntrl+1}"/>
                                        </c:forEach>
                                    </c:if>
                                </tbody>
                            </table>
                        </td>
                    </tr>
                    <c:set var="counter" value="${counter+1}"/>
                </c:if>
            </c:forEach>
        </tbody>
    </table>
</c:if>

<c:if test="${fillCombo eq 'matchcontrol'}">
    <table align="center" width="1024px" cellpadding="0px" cellspacing="0px">
        <tbody>
            <c:forEach var="i" begin="0" end="${length-1}" step="1">
                <c:set var="oflag" value="0"/>
                <c:set var="nflag" value="0"/>
                <c:if test="${oldControlList[i] ne null}">
                    <c:set var="oldSet" value="${oldControlList[i]}"/>
                </c:if>
                <c:if test="${oldControlList[i] eq null}">
                    <c:set var="oldSet" value="${null}"/>
                </c:if>
                <c:if test="${newControlList[i] ne null}">
                    <c:set var="newSet" value="${newControlList[i]}"/>
                </c:if>
                <c:if test="${newControlList[i] eq null}">
                    <c:set var="newSet" value="${null}"/>
                </c:if>
                <c:if test="${opcode eq 'c'}">
                    <c:forEach items="${oldControlList}" var="oldList">
                        <c:if test="${newSet ne null}">
                            <c:if test="${newSet.htmlControlID eq oldList.htmlControlID and newSet.controlNameID eq oldList.controlNameID}">
                                <c:set var="nflag" value="1"/>
                            </c:if>
                        </c:if>
                    </c:forEach>
                    <c:forEach items="${newControlList}" var="newList">
                        <c:if test="${oldSet ne null}">
                            <c:if test="${oldSet.htmlControlID eq newList.htmlControlID and oldSet.controlNameID eq newList.controlNameID}">
                                <c:set var="oflag" value="1"/>
                            </c:if>
                        </c:if>
                    </c:forEach>
                </c:if>
                <c:if test="${opcode eq 'p'}">
                    <c:if test="${oldSet ne null and newSet ne null}">
                        <c:if test="${oldSet.htmlControlID eq newSet.htmlControlID and oldSet.controlNameID eq newSet.controlNameID}">
                            <c:set var="oflag" value="1"/>
                            <c:set var="nflag" value="1"/>
                        </c:if>
                    </c:if>
                </c:if>
                <tr>
                    <td class="tdleftdata" width="20px">
                        <c:if test="${oldSet ne null}">
                            <c:if test="${oflag eq 1}">
                                <img src="./images/i.gif" width="20px" height="20px" id="match" name="match" alt="Match">
                            </c:if>
                            <c:if test="${oflag eq 0}">
                                <img alt="Not Match" src="./images/delete.gif" id="notmatch" name="notmatch" width="20px" height="20px">
                            </c:if>
                        </c:if>
                    </td>
                    <td width="20px" align="center">
                        <c:if test="${oldSet ne null}">
                            <b>${i+1}</b>
                        </c:if>
                    </td>
                    <td width="200px" class="tdleftdata">
                        <c:if test="${oldSet ne null}">
                            <select name="htmlControlID" id="htmlControlID" style="width:200px">
                                <option value="${oldSet.htmlControlID}">${oldSet.htmlControlName}</option>
                            </select>
                        </c:if>
                    </td>
                    <td width="200px" class="tdleftdata">
                        <c:if test="${oldSet ne null}">
                            <input type="text" style="width:200px" name="controlNameID" value="${oldSet.controlNameID}" id="controlNameID" readonly>
                        </c:if>
                    </td>
                    <td class="tdleftdata" width="20px">
                        <c:if test="${newSet ne null}">
                            <c:if test="${nflag eq 1}">
                                <img src="./images/i.gif" width="20px" height="20px" id="match" name="match" alt="Present">
                            </c:if>
                            <c:if test="${nflag eq 0}">
                                <img alt="Not Present" src="./images/delete.gif" id="notmatch" name="notmatch" width="20px" height="20px">
                            </c:if>
                        </c:if>
                    </td>
                    <td width="20px" align="center">
                        <c:if test="${newSet ne null}">
                            <b>${i+1}</b>
                        </c:if>
                    </td>
                    <td width="200px" class="tdleftdata">
                        <c:if test="${newSet ne null}">
                            <select name="htmlControlID" id="htmlControlID" style="width:200px">
                                <option value="${newSet.htmlControlID}">${newSet.htmlControlName}</option>
                            </select>
                        </c:if>
                    </td>
                    <td width="200px" class="tdleftdata">
                        <c:if test="${newSet ne null}">
                            <input type="text" style="width:200px" name="controlNameID" value="${newSet.controlNameID}" id="controlNameID" readonly>
                        </c:if>
                    </td>
                </tr>                
            </c:forEach>
        </tbody>
    </table>
    <br><br><br>
</c:if>

<c:if test="${fillList eq 'genClassList'}">    
    <tr>
        <td class="tdleft">No.</td>
        <td class="tdleft">Project Name</td>
        <td class="tdleft">Module Name</td>       
        <td class="tdleft">Download File</td>        
        <td class="tdleft">Employee</td>
        <td class="tdleft">On Date</td>
    </tr>    
    <c:if test="${fn:length(getClassList) gt 0}">
        <c:forEach items="${getClassList}" var="list">
            <tr>
                <td class="tdleftdata">
                    ${list.NO}
                </td>
                <td class="tdleftdata">
                    ${list.PROJECT_NAME}
                </td>
                <td class="tdleftdata">
                    ${list.MODULE_NAME}
                </td>
                <td class="tdleftdata">
                    <a onclick="getFiles(${list.SRNO})">Download File</a>    
                    <div id="${list.SRNO}">
                    </div>
                </td>            
                <td class="tdleftdata">
                    ${list.EMP_NAME}
                </td>
                <td class="tdleftdata">
                    <fmt:formatDate pattern="dd-MM-yyyy HH:mm:ss" value="${list.ON_DATE}"></fmt:formatDate>
                    </td>
                </tr>
        </c:forEach>
    </c:if>
    <c:if test="${fn:length(getClassList) le 0}">
        <tr>
            <td colspan="7" class="tdleftdata" align="center">
                No record found
            </td>
        </tr>
    </c:if>
</c:if>

<c:if test="${classGenerated eq 'success'}">    
    <a href="generated/${getzipfile}">${getzipfile}</a>
</c:if>