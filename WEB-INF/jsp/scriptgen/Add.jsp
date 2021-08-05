<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="common" uri="http://www.njtechnologies.in/tags/filebox" %>

<form action="" id="scriptgenAddform" name="scriptgenAddform" method="POST">                             
    <input type="hidden" name="controlsCount" id="controlsCount" value="1">
    <input type="hidden" name="eventsCount" id="eventsCount" value="1">
    <input type="hidden" id="arr" name="arr" value="">
    <input type="hidden" id="process" name="process" value="load">
    <table width="1024" cellpadding="0" cellspacing="0" align="center">
        <tbody>
            <tr>
                <td class="tdleftdata">
                    <table align="center" id="mainformtable" cellpadding="0" cellspacing="0" width="1024">
                        <tbody>
                            <tr>
                                <td class="tdleftdata" colspan="100">
                                    <div id="genDetail" style="color:teal;font-size: 14px"><b>&nbsp;&nbsp;&nbsp;General Detail</b></div>
                                </td>
                            </tr>
                            <tr>
                                <td class="tdleftdata">
                                    <table id="miscTable" width="100%">
                                        <tbody>                                                    
                                            <tr>
                                                <td class="tdleft"><sup style="color: red">*</sup>Project Name:</td>
                                                <td class="tdleftdata">
                                                    <select name="prjID" id="prjID" onchange="javascript:onChangeProject(this.value)">
                                                        <option value="-1">-- Select Project --</option>
                                                        <c:forEach items="${projectList}" var="projectItem">
                                                            <option value="${projectItem.PRJ_ID}">${projectItem.PRJ_NAME}</option>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="tdleft"><sup style="color: red">*</sup>Module Name:</td>
                                                <td class="tdleftdata" id="modulelist">
                                                    <select name="moduleID" id="moduleID">
                                                        <option value="-1">--Select Module Name--</option>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="tdleft">Test Case:</td>
                                                <td class="tdleftdata" id="testcaselist">
                                                    <select name="testCaseID" id="testCaseID">
                                                        <option value="-1">--Select Test Case--</option>
                                                    </select>
                                                </td>
                                            </tr>                                                    
                                            <tr>
                                                <td class="tdleft"><sup style="color: red">*</sup>Server Name:</td>
                                                <td class="tdleftdata">
                                                    <select id="cmbServerName" name="cmbServerName">
                                                        <option value="-1">--Select Server Name--</option>
                                                        <option value="njapps">njapps</option>
                                                        <option value="test.njtechdesk.com">test.njtechdesk.com</option>                                                                
                                                        <option value="dev.njtechdesk.com">dev.njtechdesk.com</option>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="tdleft"><sup style="color: red">*</sup>User Name:</td>
                                                <td class="tdleftdata">
                                                    <input type="text" id="txtUserName" name="txtUserName">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="tdleft"><sup style="color: red">*</sup>Password:</td>
                                                <td class="tdleftdata">
                                                    <input type="password" id="txtPassword" name="txtPassword">
                                                    <input id="chkloginOnce" type="checkbox" value="0" name="chkloginOnce" onclick ="javascript:is_Login_Select()"><b>Login Once</b>
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </td>
                            </tr>
                            <tr>
                                <td class="tdleft" style="height:25px">
                                    <input type="radio" name="operation" id="match" onchange="javascript:changeOperation('M')">Match Page Source
                                    <input type="radio" name="operation" id="generate" onchange="javascript:changeOperation('G')" checked>Generate Script
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </td>
            </tr>
        </tbody>
    </table>
    <table align="center" width="1024" id="headertable" cellpadding="0" cellspacing="0" style="display:none">
        <tbody>
            <tr>
                <td class="tdleftdata" colspan="12">
                    <div id="controlGroupID" style="color: teal;font-size:14px"><b>&nbsp;&nbsp;&nbsp;Control(s)</b>
                    </div>
                    <div align="right">
                        <i style="font-weight:lighter;color: teal;text-align:right">
                            <sup style="color: red">*</sup>To enter multiple values in Combo Box (Multi Select) use Text Box and enter Comma Separated Values.
                        </i>
                    </div>
                </td>
            </tr>
            <tr>
                <td class="tdleft" width="50px">
                    <input type="checkbox" id="chkDeleteAll" name="chkDeleteAll" onchange="javascript:selectAll()">
                    <img alt="Remove Control" src="./images/delete.gif" id="btnRemoveControlGroup" name="btnRemoveControlGroup"
                         width="20px" height="20px" onclick="removeControl()">
                </td>
                <td class="tdleft" width="205">Control Type</td>
                <td class="tdleft" width="125">Control Name|ID</td>
                <td class="tdleft" width="90">Access</td>
                <td class="tdleft" width="100">Control Value</td>
                <td class="tdleft" width="20"></td>
                <td class="tdleft" width="20"></td>
                <td class="tdleft" width="130">Event</td>
                <td class="tdleft" width="150">Test Case Nature</td>
                <td class="tdleft" width="135">Message</td>
                <td class="tdleft" width="20"></td>
            </tr>
        </tbody>
    </table>
    <table align="center" width="1024" id="matchheadertable" cellpadding="0" cellspacing="0" style="display:none">
        <tbody>
            <tr>
                <td class="tdleftdata" colspan="8">
                    <br>
                    <div id="controlGroupID" style="color: teal;font-size:14px"><b>&nbsp;&nbsp;&nbsp;Match UI Control(s)</b>
                    </div>
                    <br>
                </td>
            </tr>
            <tr>
                <td class="tdleftdata" width="20"></td>
                <td class="tdleftdata" style="color: teal;font-size:12px;padding-left:20px" colspan="4"><b>Old Page Controls</b></td>
                <td class="tdleftdata" width="20"></td>
                <td class="tdleftdata" style="color: teal;font-size:12px" colspan="4"><b>New Page Controls</b></td>
            </tr>
            <tr>
                <td class="tdleft" width="20"></td>
                <td class="tdleft" width="20">&nbsp;#</td>
                <td class="tdleft" width="200">Control Type</td>
                <td class="tdleft" width="200">Control Name | ID</td>
                <td class="tdleft" width="20"></td>
                <td class="tdleft" width="20">&nbsp;#</td>
                <td class="tdleft" width="200">Control Type</td>
                <td class="tdleft" width="200">Control Name | ID</td>
            </tr>
        </tbody>
    </table>
    <div id="controllistdiv" align="center">
    </div>
    <div id="controlmatchdiv" align="center">
    </div>
    <div id="matchuicontrolsdiv" style="display:none" align="center">
        <table align="center" width="1024">
            <tbody>
                <tr>
                    <td colspan="4" class="tdleftdata">
                        <input type="radio" name="opcode" id="control" value="c" checked>Check UI Controls Presence<br>
                        <input type="radio" name="opcode" id="position" value="p">Check UI Controls Position
                    </td>
                </tr>
                <tr>
                    <td valign="top" class="tdleft">
                        Old Source:
                    </td>
                    <td class="tdleftdata">
                        <textarea id="oldSource" name="oldSource" rows="7" cols="75" style="width:425px"></textarea>
                    </td>
                    <td valign="top" class="tdleft">
                        New Source:
                    </td>
                    <td class="tdleftdata">
                        <textarea id="newSource" name="newSource" rows="7" cols="75" style="width:425px"></textarea>
                    </td>
                </tr>
                <tr>
                    <td colspan="4" class="tdleftdata">
                        <div align="center">
                            <input type="button" id="matchUIControlsBtn" name="matchUIControlsBtn" value="Match UI Controls" onclick="javascript:matchUIControls()">
                        </div>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
    <div id="generatescriptdiv" align="center">
        <table align="center" width="1024">
            <tbody>
                <tr>
                    <td class="tdleft" valign="top" width="264">
                        <input type="radio" id="url" value="url" name="option" checked onchange="javascript:changeInput('U')">URL<br>
                        <input type="radio" id="source" value="html" name="option" onchange="javascript:changeInput('S')">HTML Source
                    </td>
                    <td id="getter" class="tdleftdata" valign="top">
                        <div id="urlinput">
                            <input type="text" id="pageURL" name="pageURL" style="width:600px">
                        </div>
                        <div id="sourceinput" style="display: none">
                            <textarea id="pageSource" name="pageSource" rows="7" cols="150" style="width:750px"></textarea>
                            <br><br><br>
                            <center><b>OR</b></center>
                            <br>
                            <center><common:filebox controlid="pageSourceF" name="pageSourceF" maxsize="10240" maxfiles="1" elementname="pageSourceFile" formid="scriptgenAddform" type="txt" value="Upload File"></common:filebox></center>
                        </div>
                        <br>
                        <input type="button" id="getBtn" name="getBtn" value="Get Controls" onclick="javascript: getControlList()">
                        <i style="font-weight:lighter;color: teal"><sup style="color: red">*</sup>If all controls are not listed using URL then try HTML source.</i>
                    </td>
                </tr>
            </tbody>
        </table>
        <table width="100%" align="center">
            <tr>
                <td align="center" colspan="100">
                    <input type="submit" name="btnSubmit" id="btnSubmit" value="Generate Script" onclick="javascript:return validateTestScriptValues()"/>
<!--                    <input type="reset" name="btnreset" id="btnReset" value="Reset" onclick="resetFileUploadControl('pageSourceF');" />-->
                    <input type="reset" name="btnreset" id="btnReset" value="Reset" onclick="reset_filebox('pageSourceF');" />
                    <br><br>
                </td>
            </tr>
            </tbody>
        </table>
        <br><br>
    </div>
</form>