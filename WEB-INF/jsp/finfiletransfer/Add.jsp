<%-- TCIGBF --%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="common" uri="http://www.njtechnologies.in/tags/filebox" %>
<div class="report_content" id="rpt">
    <c:if test="${action eq 'Main'}">
        <div class="menu_caption_bg">
            <div id="divAddCaption" class="menu_caption_text">Add</div>
        </div>
    </c:if>

    <c:if test="${action eq 'Main'}">
        <table align="center" border="0" cellpadding="0" cellspacing="2" class="menu_subcaption" width="100%">
            <tr>
                <td align="right" class="report_content_caption">
                    Language : <sup class="astriek">*</sup>: 
                </td>
                <td class="report_content_value">
                    <input type="radio" id="lang" name="lang" value="java" />JAVA
                    <input type="radio" id="lang" name="lang" value="php"/>PHP
                </td>
            </tr>
            <tr>
                <td align="right" class="report_content_caption">
                    Source-Destination<sup class="astriek">*</sup> : 
                </td>
                <td class="report_content_value">
                    <select class="custom_combo_showSSWT " id="srcdest" name="srcdest" tabindex="1">
                        <option value="-1">-- Select Source-Destination --</option>
                        <optgroup label="Dev-Test">
                            <option value="devhoweb1.njtechdesk.com-testhoweb1.njtechdesk.com">devhoweb1.nj - testhoweb1.nj</option>
                            <option value="devhoweb2.njtechdesk.com-testhoweb2.njtechdesk.com">devhoweb2.nj - testhoweb2.nj</option>
                            <option value="devhoweb3.njtechdesk.com-testhoweb3.njtechdesk.com">devhoweb3.nj - testhoweb3.nj</option>
                            <option value="devhoweb4.njtechdesk.com-testhoweb4.njtechdesk.com">devhoweb4.nj - testhoweb4.nj</option>
                            <option value="devimfsp.nj:8090-testimfsp.nj:8090">devexchange.nj - testexchange.nj</option>
                            <option value="devpaymentagg.nj:8090-testpaymentagg.nj:8090">devpaymentagg.nj - testpaymentagg.nj</option>
                        </optgroup>
                        <optgroup label="Test-Prod">
                            <option value="testhoweb1.njtechdesk.com-prodhoweb1.njtechdesk.com">testhoweb1.nj - prodhoweb1.nj</option>
                            <option value="testhoweb2.njtechdesk.com-prodhoweb2.njtechdesk.com">testhoweb2.nj - prodhoweb2.nj</option>
                           <option value="testhoweb3.njtechdesk.com-prodhoweb3.njtechdesk.com">testhoweb3.nj - prodhoweb3.nj</option>
                            <option value="testhoweb3.njtechdesk.com-prodhoweb4.njtechdesk.com">testhoweb3.nj - prodhoweb4.nj</option>
                            <option value="testhoweb4.njtechdesk.com-prodhoweb4.njtechdesk.com">testhoweb4.nj - prodhoweb4.nj</option>
                            <option value="testpaymentagg.nj:8090-prodpaymentagg.nj:8090">testpaymentagg.nj - prodpaymentagg.nj</option>
                        </optgroup>
                    </select>
                </td>
            </tr>
            <tr>
                <td align="right" class="report_content_caption">
                    Project <sup class="astriek">*</sup>: 
                </td>
                <td class="report_content_value">
                    <select id="project" name="project" tabindex="2" >
                        <option value="-1">-- Select Project --</option>
                        <c:forEach items="${projects}" var="project">
                            <option value="${project.PRJ_ID}-${project.DOMAIN_NAME}">${project.PRJ_NAME} - ${project.DOMAIN_NAME}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td align="right" class="report_content_caption">
                    Request No : 
                </td>
                <td class="report_content_value">
                    <div id="reqNoDiv">
                        <select id="reqno" name="reqno" tabindex="3" >
                            <option value="-1">-- Select Request No --</option>
                            <c:forEach items="${requests}" var="request">
                                <option value="${request.WORK_REQUEST_ID}">${request.WORK_REQUEST_ID} - ${request.TITLE}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <!--<input type="text" id="reqnoTxtLike" name="reqnoTxtLike" tabindex="4" onblur="javascript: reqNoTxtLk();"/>-->
                </td>
            </tr>
            <tr>
                <td align="right" class="report_content_caption">
                    Purpose <sup class="astriek">*</sup>: 
                </td>
                <td class="report_content_value">
                    <textarea rows="2" cols="2" id="purpose" name="purpose" tabindex="5" ></textarea>
                </td>
            </tr>
            <tr>
                <td align="right" class="report_content_caption"></td>
                <td>Example: finstudio/jsp/repositoryList.jsp</td>
            </tr>
            <tr>
                <td align="right" class="report_content_caption" style="vertical-align:top">
                    File List : 
                </td>
                <td>
                    <textarea rows="5" style="width: 600px;"  id="filelist" name="filelist" tabindex="6" ></textarea>
                </td>
            </tr>
            <tr>
                <td align="right" class="report_content_caption" style="vertical-align:top">
                    File List For Delete : 
                </td>
                <td>
                    <input type="checkbox" id="fileDltChk" name="fileDltChk" onchange="javascript: fileBoxValidate();"/>
                    <div id="fileDlt" style="display: none">
                        <textarea rows="5" style="width: 600px;"  id="filelistdlt" name="filelistdlt" tabindex="7" ></textarea>
                    </div>

                </td>
            </tr>
            <tr>
                <td align="right" class="report_content_caption" style="vertical-align:top">
                    Upload Local Files : 
                </td>
                <td>
                    <input type="checkbox" id="upldChk" name="upldChk" onchange="javascript: fileBoxValidate();"/>
                    <div id="fileBox" style="display: none">
                        <table>
                            <tr>
                                <td align="right">Example: </td>
                                <td>finstudio/jsp/uploadFile.txt</td>
                            </tr>
                            <tr>
                                <td><common:filebox controlid="btnUpload1" name="btnUpload1" formid="finfiletransferForm" type="xml,txt,properties,jar" elementname="filelistupld1" maxfiles="1" maxsize="2048" onremovecallback="javascript: onRemove1();"/></td>
                                <td><input type="text" name="fileLoc1" id="fileLoc1" style="width: 150px;"/></td>
                            </tr>
                            <tr>
                                <td><common:filebox controlid="btnUpload2" name="btnUpload2" formid="finfiletransferForm" type="xml,txt,properties,jar" elementname="filelistupld2" maxfiles="1" maxsize="2048" onremovecallback="javascript: onRemove2();"/></td>
                                <td><input type="text" name="fileLoc2" id="fileLoc2" style="width: 150px;"/></td>
                            </tr>
                            <tr>
                                <td><common:filebox controlid="btnUpload3" name="btnUpload3" formid="finfiletransferForm" type="xml,txt,properties,jar" elementname="filelistupld3" maxfiles="1" maxsize="2048" onremovecallback="javascript: onRemove3();"/></td>
                                <td><input type="text" name="fileLoc3" id="fileLoc3" style="width: 150px;"/></td>
                            </tr>
                        </table>
                    </div>
                    <%--<common:filebox controlId="btnUpload" name="btnUpload" formid="finfiletransferForm" type="xml,txt,properties" elementname="filelistupld" maxfiles="5" maxsize="1024"/>--%>                     
                </td>
                <!--<textarea rows="5" style="width: 600px;"  id="filelistupld" name="filelistupld" tabindex="7" ></textarea>-->
            </tr>
            <tr>
                <td align="center" colspan="2">
                    <input class="button" type="button" id="btnAdd" name="btnAdd" Value="Add" onclick="javascript: validateData();" />
                    <input class="button" type="reset" id="btnReset" name="btnReset" Value="Reset" onclick="javascript: resetAll();"/>
                </td>
            </tr>

        </table>
    </c:if>
    <c:if test="${action eq 'success'}">
        <input type="hidden" id="AddCount" name="AddCount" value="${successValue}">
    </c:if>
    <c:if test="${action eq 'failure'}">
        <input type="hidden" id="AddCount" name="AddCount" value="${failureValue}">
        <div id="serverStatus" style="font-weight: normal; color: red;">${serverResponse}</div>
    </c:if>

    <c:if test="${action eq 'Report'}">

        <c:set var="flag1" value="0"/>
        <c:set var="flag2" value="0"/>
        <c:set var="flag3" value="0"/>
        <c:set var="deststatus" value="true"/>
        <c:set var="strstatus" value="true"/>
        <c:if test="${ListStatus1 eq 'display'}">
            <table width="80%" border="1">
                <tr>
                    <th>File Name</th>
                    <th>Source Status</th>
                    <th>Dest Status</th>
                    <th>File Type</th>
                </tr>

                <c:forEach items="${List}" var="list">
                    <input type="hidden" id="hdnGenId" name="hdnGenId" value="${list.GEN_ID}" />
                    <c:set var="flag1" value="1"/>
                    <c:if test="${list.SRC_EXIST eq 'NotExist'}">
                        <c:set var="strstatus" value="false"/>
                        <c:set var="flag1" value="2"/>
                    </c:if>
                    <tr>
                        <td>${list.FILE_NAME}</td>
                        <td>${list.SRC_EXIST}</td>
                        <td>${list.DEST_EXIST}</td>
                        <td>${list.FILE_TYPE}</td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
        <br/>   
        <c:if test="${ListStatus2 eq 'display'}">
            <table width="80%" border="1">
                <tr>
                    <th>File Name</th>
                    <th>Dest Status</th>
                    <th>File Type</th>
                </tr>

                <c:forEach items="${ListDlt}" var="list">
                    <c:set var="flag2" value="1"/>
                    <input type="hidden" id="hdnGenId" name="hdnGenId" value="${list.GEN_ID}" />
                    <c:if test="${list.DEST_EXIST eq 'NotExist'}">
                        <c:set var="flag2" value="2"/>
                        <c:set var="deststatus" value="false"/>
                    </c:if>
                    <tr>
                        <td>${list.FILE_NAME}</td>
                        <td>${list.DEST_EXIST}</td>
                        <td>${list.FILE_TYPE}</td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
        <br/>
        <c:if test="${ListStatus3 eq 'display'}">
            <table width="80%" border="1">
                <tr>
                    <th>File Name</th>
                </tr>

                <c:forEach items="${ListUpld}" var="list">
                    <c:set var="flag3" value="1"/>
                    <input type="hidden" id="hdnGenId" name="hdnGenId" value="${list.GEN_ID}" />
                    <tr>
                        <td>${list.FILE_NAME}</td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
        <c:choose>
            <c:when test="${(flag1 eq '0' || flag1 eq '1') && (flag2 eq '0' || flag2 eq '1') && (flag3 eq '0' || flag3 eq '1')}">
                <input class="button" type="button" id="btnConfirm" name="btnConfirm" Value="Confirm" onclick="javascript: insertData();" />
            </c:when>

            <c:otherwise>
                <input class="button" type="button" id="btnBack" name="btnBack" Value="Back" onclick="javascript: backButton();" />
            </c:otherwise>
        </c:choose>

    </c:if>
    <div id="divMsg" style="display: none">
        <input type="hidden" id="hdnDbMsg" name="hdnDbMsg" value="${DBopration}" />
    </div>
</div>
<c:if test="${action eq 'reqCombo'}">
    <select id="reqno" name="reqno" tabindex="3" >
        <option value="-1">-- Select Request No --</option>
        <c:forEach items="${requests}" var="request">
            <option value="${request.WORK_REQUEST_ID}">${request.WORK_REQUEST_ID} - ${request.TITLE}</option>
        </c:forEach>
    </select>
</c:if>
<div id="fileStatus" style="display: none"></div>
