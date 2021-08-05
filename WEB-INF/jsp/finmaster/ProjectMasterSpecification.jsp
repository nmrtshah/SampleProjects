<%--
    Document   : ProjectMasterSpecification
    Created on : Feb 22, 2012, 12:01:11 PM
    Author     : Sonam Patel
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:choose>
    <c:when test="${process eq 'main'}">
        <form name="MenuForm" id="MenuForm" method="post" action="">
            <%--ProjectSpecification--%>
            <div class="container" id="ProjectSpecification">
                <div class="content" id="menuLoader1">
                    <div class="menu_new">
                        <div class="menu_caption_bg">
                            <div class="menu_caption_text">Project Master Specification</div>
                        </div>
                        <table align="center">
                            <tr>
                                <td width="25%" align="right">
                                    <sup style="color: red">*</sup>Project Name :
                                </td>
                                <td width="75%">
                                    <select id="cmbProjectName" name="cmbProjectName" tabindex="1">
                                        <option value="-1" selected>-- Select Project Name --</option>
                                        <c:forEach items="${projects}" var="project" varStatus="loop">
                                            <option value="${values[loop.index]}">${projects[loop.index]}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td align="right">
                                    <sup style="color: red">*</sup>Module Name :
                                </td>
                                <td>
                                    <input type="text" tabindex="2" id="txtModuleName" name="txtModuleName" />
                                    <input type="text" id="txtOrigModulName" name="txtOrigModulName" style="display: none" />
                                </td>
                            </tr>
                            <tr>
                                <td align="right">
                                    Request No :
                                </td>
                                <td>
                                    <input type="text" id="txtReqNo" tabindex="3" name="txtReqNo" />
                                </td>
                            </tr>
                            <tr>
                                <td></td>
                                <td align="center">
                                    <input class="button" type="button" name="btnNext" value="Next" tabindex="4" onclick="showSRSSpecification()" />
                                    <input class="button" type="button" name="btnCancel" value="Cancel" tabindex="5" onclick="window.history.back()" />
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
            <%--SRSSpecification--%>
            <div class="container" id="SRSSpecification" style="display:none">
                <div class="content" id="menuLoader2">
                    <div class="menu_new">
                        <div class="menu_caption_bg">
                            <div class="menu_caption_text">SRS Specification</div>
                        </div>
                        <table align="center">
                            <tr>
                                <td width="25%" align="right">
                                    Problem Statement :
                                </td>
                                <td width="75%">
                                    <textarea id="txtProblemStmt" name="txtProblemStmt" rows="4" cols="5" tabindex="6"></textarea>
                                </td>
                            </tr>
                            <tr>
                                <td align="right">
                                    Solution / Objective :
                                </td>
                                <td>
                                    <textarea id="txtSolution" name="txtSolution" rows="4" cols="5" tabindex="7"></textarea>
                                </td>
                            </tr>
                            <tr>
                                <td align="right">
                                    Existing Practice :
                                </td>
                                <td>
                                    <input type="text" id="txtExistPractice" name="txtExistPractice" tabindex="8" />
                                </td>
                            </tr>
                            <tr>
                                <td align="right">
                                    Placement :
                                </td>
                                <td>
                                    <input type="text" id="txtPlacement" name="txtPlacement" tabindex="9" />
                                </td>
                            </tr>
                            <tr>
                                <td></td>
                                <td>
                                    <input class="button" type="button" name="btnPrevious" value="Previous" tabindex="10" onclick="javascript: showProjectMasterSpecification()" />
                                    <input class="button" type="button" name="btnNext" value="Next" tabindex="11" onclick="javascript: showTableSelection()" />
                                    <input class="button" type="button" name="btnCancel" value="Cancel" tabindex="12" onclick="window.history.back()" />
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
            <%--Table Selection--%>
            <div class="container" id="TableSelection" style="display:none">
                <div class="content" id="menuLoader3">
                    <div class="menu_new">
                        <div class="menu_caption_bg">
                            <div class="menu_caption_text">Table Selection</div>
                        </div>
                        <table align="center">
                            <tr>
                                <td width="40%" align="right">
                                    <sup style="color: red">*</sup>Alias Name :
                                </td>
                                <td width="60%">
                                    <select id="cmbAliasName" name="cmbAliasName" tabindex="1" onchange="javascript: setFocus()">
                                        <option value="-1" selected>-- Select Alias Name --</option>
                                        <c:forEach items="${aliases}" var="alias">
                                            <option value="${alias}">${alias}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td align="right">
                                    <sup style="color: red">*</sup>Connection using Alias :
                                    <input type="radio" id="rdoAliasName" name="rdoAliasServer" checked onclick="javascript: showDevServer();" tabindex="2" value="Alias" />
                                </td>
                                <td>
                                </td>
                            </tr>
                            <tr>
                                <td align="right">
                                    <sup style="color: red">*</sup>Connection using Development Server :
                                    <input type="radio" id="rdoServerName" name="rdoAliasServer" onclick="javascript: showDevServer();" tabindex="3" value="Server" />
                                </td>
                                <td>
                                    <select id="cmbServerName" name="cmbServerName" disabled onchange="javascript: setFocus()" tabindex="4">
                                        <option value="-1" selected>-- Select Server --</option>
                                        <option value="dev_mysql">dev-njindiainvest-MySQL</option>
                                        <option value="dev_db2_mysql">dev-db2-njindiainvest-MySQL</option>
                                        <option value="dev_tran">dev-mftran-ORACLE</option>
                                        <option value="dev_brok">dev-mfund-ORACLE</option>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td align="right">
                                    <sup style="color: red">*</sup>Master Table :
                                </td>
                                <td>
                                    <input type="text" id="txtMasterTable" name="txtMasterTable" style="width: 100px" tabindex="5" onblur="javascript: fillMstTableCombo();" />
                                    <select id="cmbMasterTable" name="cmbMasterTable" tabindex="6" class="custom_combo_mid" style="float: none; width: 172px !important"
                                            onchange="javascript: fillMstTableColumnCombo();" style="float: none">
                                        <option value="-1" selected>-- Select Table --</option>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td align="right">
                                    <sup style="color: red">*</sup>Primary Key :
                                </td>
                                <td>
                                    <select id="cmbMasterTablePrimKey" name="cmbMasterTablePrimKey" tabindex="7">
                                        <option value="-1" selected>-- Select Field --</option>
                                    </select>
                                </td>
                            </tr>
                            <tr id="trSequence" style="display: none">
                                <td align="right">
                                    Sequence For Primary Key :
                                </td>
                                <td>
                                    <input type="text" id="txtSequence" name="txtSequence" tabindex="8" />
                                </td>
                            </tr>
                            <tr style="display: none">
                                <td>
                                </td>
                                <td>
                                    <div id="divMstColumnWidth"></div>
                                </td>
                            </tr>
                            <tr>
                                <td></td>
                                <td id="tdDataBaseType" style="display: none">
                                </td>
                            </tr>
                            <tr>
                                <td></td>
                                <td>
                                    <input class="button" type="button" name="btnPrevious" value="Previous" tabindex="9" onclick="javascript: showPrevSRSSpecification()" />
                                    <input class="button" type="button" name="btnNext" value="Next" tabindex="10" onclick="javascript: showTabSelection()" />
                                    <input class="button" type="button" name="btnCancel" value="Cancel" tabindex="11" onclick="window.history.back()" />
                                </td>
                            </tr>
                        </table>
                        <div id="divSequence" style="display: none"></div>
                    </div>
                </div>
            </div>
            <%--TabSelection--%>
            <div class="container" id="TabSelection" style="display:none">
                <div class="content" id="menuLoader4">
                    <div class="menu_new">
                        <div class="menu_caption_bg">
                            <div class="menu_caption_text">Tab Selection</div>
                        </div>
                        <table align="center">
                            <tr>
                                <td align="right" width="25%">
                                    <sup style="color: red">*</sup>paramName :
                                </td>
                                <td width="75%">
                                    <input type="text" id="txtParamName" name="txtParamName" style="width: 150px" tabindex="12" value="cmdAction" />
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    Header Required ?
                                    <input type="checkbox" id="chkHeader" name="chkHeader" tabindex="13" />
                                    &nbsp;&nbsp;&nbsp;&nbsp;Footer Required ?
                                    <input type="checkbox" id="chkFooter" name="chkFooter" tabindex="14" />
                                </td>
                            </tr>
                            <tr>
                                <td></td>
                                <td>
                                    <sup style="color: red">*</sup>Tab Selection :
                                </td>
                            </tr>
                            <tr>
                                <td></td>
                                <td>
                                    <input type="checkbox" id="chkAdd" name="chkAdd" checked disabled tabindex="15" />Add
                                </td>
                            </tr>
                            <tr>
                                <td></td>
                                <td>
                                    <input type="checkbox" id="chkEdit" name="chkEdit" checked disabled tabindex="16" />Edit
                                </td>
                            </tr>
                            <tr>
                                <td></td>
                                <td>
                                    <input type="checkbox" id="chkDelete" name="chkDelete" tabindex="17" />Delete
                                </td>
                            </tr>
                            <tr>
                                <td></td>
                                <td>
                                    <input type="checkbox" id="chkView" name="chkView" tabindex="18" />View
                                </td>
                            </tr>
                            <tr>
                                <td></td>
                                <td>
                                    <input class="button" type="button" name="btnPrevious" value="Previous" tabindex="19" onclick="javascript: showTableSelection()" />
                                    <input class="button" type="button" name="btnNext" value="Next" tabindex="20" onclick="javascript: showMasterDataSelection()" />
                                    <input class="button" type="button" name="btnCancel" value="Cancel" tabindex="21" onclick="window.history.back()" />
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
            <%--MasterDataSelection--%>
            <div class="container" id="MasterDataSelection" align="center" style="border:1px solid #9A9A9A; display:none">
                <div class="content" id="menuLoader5">
                    <div class="menu_new" style="border: 2px">
                        <div class="menu_caption_bg">
                            <div class="menu_caption_text">Master Menu Specifications</div>
                        </div>
                        <div class="collapsible_menu_tab">
                            <ul style="vertical-align: text-top;" id="menutab0"></ul>
                        </div>
                        <div class="report_content">
                            <div id="addMenuTab" style="border:1px solid #9A9A9A; display:none">
                                <div class="report_text" align="center" style="font-size: large; font-weight: bold">Menu Specification</div>
                                <div class="report_content" align="right" style="float: left;width: 50%;">
                                    <table cellpadding="0" cellspacing="2">
                                        <tbody>
                                            <tr>
                                                <td align="right">
                                                    <sup style="color: red">*</sup>Field :
                                                </td>
                                                <td align="left">
                                                    <select name="cmbAddField" id="cmbAddField" onblur="javascript: setIdNameProperties('Add')">
                                                        <option value="-1">--Select Field--</option>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="right">
                                                    <sup style="color: red">*</sup>Control :
                                                </td>
                                                <td align="left">
                                                    <select name="cmbAddControl" id="cmbAddControl" onchange="javascript: showAddControlProperties()">
                                                        <option value="-1">--Select Control--</option>
                                                        <option value="TextBox">TextBox</option>
                                                        <option value="Password">Password</option>
                                                        <option value="TextArea">TextArea</option>
                                                        <option value="ComboBox">ComboBox</option>
                                                        <option value="TextLikeCombo">TextLike Combo</option>
                                                        <option value="CheckBox">CheckBox</option>
                                                        <option value="Radio">Radio</option>
                                                        <option value="DatePicker">DatePicker</option>
                                                        <option value="File">File</option>
                                                        <option value="FileBox">FileBox</option>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="right">
                                                    <sup style="color: red">*</sup>Tab Index :
                                                </td>
                                                <td align="left">
                                                    <input type="text" id="txtAddTabIndex" name="txtAddTabIndex" />
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="right">
                                                    Label :
                                                </td>
                                                <td align="left">
                                                    <input type="text" id="txtAddLabel" name="txtAddLabel" />
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="right">
                                                    <sup style="color: red">*</sup>Show On :
                                                </td>
                                                <td align="center">
                                                    <div id="div1" style="float: left; width: 90px;">
                                                        <input type="checkbox" id="chkShowAdd" name="chkShowAdd" />Add
                                                    </div>
                                                    <div id="div2" style="float: left; width: 90px;">
                                                        <input type="checkbox" id="chkShowEdit" name="chkShowEdit" />Edit
                                                    </div>
                                                    <div id="div3" style="float: left; width: 90px;">
                                                        <input type="checkbox" id="chkShowDel" name="chkShowDel" />Delete
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td></td>
                                                <td align="center">
                                                    <input class="button" align="middle" type="button" value="Add"
                                                           id="btnAddTabAdd" name="btnAddTabAdd" onclick="validateAddTabData()" />
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                    <div id="addTabList" align="right" style="display:none">
                                        <table id="addTabListTable" align="right" style="border:1px solid #9A9A9A;" width="65%">
                                            <th align="center" colspan="8" style="color: #9A9A9A">Field List</th>
                                            <tr>
                                                <td style="color: #9A9A9A; font-weight: bold">
                                                    Field
                                                </td>
                                                <td style="color: #9A9A9A; font-weight: bold;">
                                                    Control
                                                </td>
                                                <td width="5%" align="right" style="color: #9A9A9A; font-weight: bold">
                                                    Position
                                                </td>
                                                <td width="5%" style="color: #9A9A9A; font-weight: bold">
                                                    Add
                                                </td>
                                                <td width="5%" style="color: #9A9A9A; font-weight: bold">
                                                    Edit
                                                </td>
                                                <td width="5%" style="color: #9A9A9A; font-weight: bold">
                                                    Delete
                                                </td>
                                                <td width="10%" style="color: #9A9A9A; font-weight: bold">
                                                    Modify
                                                </td>
                                                <td width="10%" style="color: #9A9A9A; font-weight: bold">
                                                    Remove
                                                </td>
                                            </tr>
                                        </table>
                                    </div>
                                    <br clear="both"/><br />
                                    <div style="font-size: 11px; letter-spacing: 1px; line-height: 26px">
                                        <sup style="color: red">*</sup>Control Position:
                                        <select name="cmbAddControlPos" id="cmbAddControlPos" style="float: none">
                                            <option value="-1">--Select Control Position--</option>
                                            <option value="1">Single Column</option>
                                            <option value="2">Double Column</option>
                                        </select>
                                    </div>
                                </div>
                                <div id="divAddProperties" style="float: left; display:none; width: 50%;">
                                    <jsp:include page="AddTabProperties.jsp"/>
                                </div>
                                <br style="clear: both"/><br />
                                <div id="hiddenDivAddTab" style="display: none"></div>
                            </div>
                            <div id="viewMenuTab" style="border:1px solid #9A9A9A; display:none">
                                <div class="report_text" align="center" style="font-size: large; font-weight: bold">View</div>
                                <sup style="color: red">*</sup>Check Fields To Display On View
                                <table id="viewTabColumns"></table>
                            </div>
                            <div>
                                <input class="button" type="button" name="btnPrevious" value="Previous" onclick="javascript: showPrevTabSelection();" />
                                <input class="button" type="button" name="btnNext" value="Next" onclick="javascript: showMasterFilterSelection();" />
                                <input class="button" type="button" name="btnCancel" value="Cancel" onclick="window.history.back()" />
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <%--MasterFilterSelection--%>
            <div class="container" id="MasterFilterSelection" align="center" style="border:1px solid #9A9A9A; display:none">
                <div class="content" id="menuLoader6">
                    <div class="menu_new" style="border: 2px">
                        <div class="menu_caption_bg">
                            <div class="menu_caption_text">Master Filter Specifications</div>
                        </div>
                        <div class="collapsible_menu_tab">
                            <ul style="vertical-align: text-top;" id="maintab1"></ul>
                        </div>
                        <div class="report_content">
                            <div id="editFilterTab" style="border: 1px solid #9A9A9A; display: none">
                                <div id="divETabCopy" style="font-size: 10pt; float: left; width: 500px;">
                                    <div style="float: left">
                                        Copy Tab Filters To :
                                    </div>
                                    <div id="divCopyETabToDel" style="font-size: 10pt; float: left; width: 70px;">
                                        <input type="checkbox" id="chkCopyETabToDel" name="chkCopyETabToDel" onclick="javascript: showHideTab('E', 'Del');" />Delete
                                    </div>
                                    <div id="divCopyETabToView" style="font-size: 10pt; float: left; width: 60px;">
                                        <input type="checkbox" id="chkCopyETabToView" name="chkCopyETabToView" onclick="javascript: showHideTab('E', 'View');" />View
                                    </div>
                                    <br/>
                                    <br/>
                                    <br/>
                                </div>
                                <div class="report_text" align="center" style="font-size: large; font-weight: bold">Filtering On Edit</div>
                                <div class="report_content" align="right" style="float: left; width: 50%">
                                    <table cellpadding="0" cellspacing="2">
                                        <tbody>
                                            <tr>
                                                <td align="right">
                                                    <sup style="color: red">*</sup>Field :
                                                </td>
                                                <td align="left">
                                                    <select name="cmbEditField" id="cmbEditField" onblur="javascript: setIdNameProperties('Edit')">
                                                        <option value="-1">--Select Field--</option>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="right">
                                                    <sup style="color: red">*</sup>Control :
                                                </td>
                                                <td>
                                                    <select name="cmbEditControl" id="cmbEditControl" onchange="javascript: showEditControlProperties()">
                                                        <option value="-1">--Select Control--</option>
                                                        <option value="TextBox">TextBox</option>
                                                        <option value="Password">Password</option>
                                                        <option value="TextArea">TextArea</option>
                                                        <option value="ComboBox">ComboBox</option>
                                                        <option value="TextLikeCombo">TextLike Combo</option>
                                                        <option value="CheckBox">CheckBox</option>
                                                        <option value="Radio">Radio</option>
                                                        <option value="DatePicker">DatePicker</option>
                                                        <option value="File">File</option>
                                                        <option value="FileBox">FileBox</option>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="right">
                                                    <sup style="color: red">*</sup>Tab Index :
                                                </td>
                                                <td>
                                                    <input type="text" id="txtEditTabIndex" name="txtEditTabIndex" />
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="right">
                                                    Label :
                                                </td>
                                                <td align="left">
                                                    <input type="text" id="txtEditLabel" name="txtEditLabel" />
                                                </td>
                                            </tr>
                                            <tr>
                                                <td></td>
                                                <td align="center">
                                                    <input class="button" align="middle" type="button" value="Add"
                                                           id="btnAddTabEdit" name="btnAddTabEdit" onclick="validateEditTabData()" />
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                    <div id="editTabList" align="right" style="display:none">
                                        <table id="editTabListTable" align="right" style="border:1px solid #9A9A9A;" width="45%">
                                            <th align="center" colspan="5" style="color: #9A9A9A">Field List</th>
                                            <tr>
                                                <td style="color: #9A9A9A; font-weight: bold">
                                                    Field
                                                </td>
                                                <td style="color: #9A9A9A; font-weight: bold">
                                                    Control
                                                </td>
                                                <td width="10%" align="right" style="color: #9A9A9A; font-weight: bold">
                                                    Position
                                                </td>
                                                <td width="10%" style="color: #9A9A9A; font-weight: bold">
                                                    Modify
                                                </td>
                                                <td width="10%" style="color: #9A9A9A; font-weight: bold">
                                                    Remove
                                                </td>
                                            </tr>
                                        </table>
                                    </div>
                                    <br clear="both"/><br />
                                    <div style="font-size: 11px; letter-spacing: 1px; line-height: 26px;">
                                        <sup style="color: red">*</sup>Control Position:
                                        <select name="cmbEditControlPos" id="cmbEditControlPos" style="float: none">
                                            <option value="-1">--Select Control Position--</option>
                                            <option value="1">Single Column</option>
                                            <option value="2">Double Column</option>
                                        </select>
                                    </div>
                                </div>
                                <div id="divEditProperties" style="float: left; display:none; width: 50%">
                                    <jsp:include page="EditTabProperties.jsp"/>
                                </div>
                                <br style="clear: both"/><br />
                                <div id="hiddenDivEditTab" style="display: none"></div>
                            </div>
                            <div id="deleteFilterTab" style="border:1px solid #9A9A9A; display:none">
                                <div id="divDTabCopy" style="font-size: 10pt; float: left; width: 500px;">
                                    <div style="float: left">
                                        Copy Tab Filters To :
                                    </div>
                                    <div id="divCopyDTabToView" style="font-size: 10pt; float: left; width: 60px;">
                                        <input type="checkbox" id="chkCopyDTabToView" name="chkCopyDTabToView" onclick="javascript: showHideTab('D', 'View');" />View
                                    </div>
                                    <br/>
                                    <br/>
                                    <br/>
                                </div>
                                <div class="report_text" align="center" style="font-size: large; font-weight: bold">Filtering On Delete</div>
                                <div class="report_content" align="right" style="float: left; width: 50%">
                                    <table cellpadding="0" cellspacing="2">
                                        <tbody>
                                            <tr>
                                                <td align="right">
                                                    <sup style="color: red">*</sup>Field :
                                                </td>
                                                <td>
                                                    <select name="cmbDeleteField" id="cmbDeleteField" onblur="javascript: setIdNameProperties('Delete')">
                                                        <option value="-1">--Select Field--</option>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="right">
                                                    <sup style="color: red">*</sup>Control :
                                                </td>
                                                <td>
                                                    <select name="cmbDeleteControl" id="cmbDeleteControl" onchange="javascript: showDeleteControlProperties()">
                                                        <option value="-1">--Select Control--</option>
                                                        <option value="TextBox">TextBox</option>
                                                        <option value="Password">Password</option>
                                                        <option value="TextArea">TextArea</option>
                                                        <option value="ComboBox">ComboBox</option>
                                                        <option value="TextLikeCombo">TextLike Combo</option>
                                                        <option value="CheckBox">CheckBox</option>
                                                        <option value="Radio">Radio</option>
                                                        <option value="DatePicker">DatePicker</option>
                                                        <option value="File">File</option>
                                                        <option value="FileBox">FileBox</option>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="right">
                                                    <sup style="color: red">*</sup>Tab Index :
                                                </td>
                                                <td>
                                                    <input type="text" id="txtDeleteTabIndex" name="txtDeleteTabIndex" />
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="right">
                                                    Label :
                                                </td>
                                                <td align="left">
                                                    <input type="text" id="txtDeleteLabel" name="txtDeleteLabel" />
                                                </td>
                                            </tr>
                                            <tr>
                                                <td></td>
                                                <td align="center">
                                                    <input class="button" align="middle" type="button" value="Add"
                                                           id="btnAddTabDelete" name="btnAddTabDelete" onclick="validateDeleteTabData()" />
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                    <div id="deleteTabList" align="right" style="display:none">
                                        <table id="deleteTabListTable" align="right" style="border:1px solid #9A9A9A;" width="45%">
                                            <th align="center" colspan="5" style="color: #9A9A9A">Field List</th>
                                            <tr>
                                                <td style="color: #9A9A9A; font-weight: bold">
                                                    Field
                                                </td>
                                                <td style="color: #9A9A9A; font-weight: bold">
                                                    Control
                                                </td>
                                                <td width="10%" align="right" style="color: #9A9A9A; font-weight: bold">
                                                    Position
                                                </td>
                                                <td width="10%" style="color: #9A9A9A; font-weight: bold">
                                                    Modify
                                                </td>
                                                <td width="10%" style="color: #9A9A9A; font-weight: bold">
                                                    Remove
                                                </td>
                                            </tr>
                                        </table>
                                    </div>
                                    <br clear="both"/><br />
                                    <div style="font-size: 11px; letter-spacing: 1px; line-height: 26px;">
                                        <sup style="color: red">*</sup>Control Position:
                                        <select name="cmbDeleteControlPos" id="cmbDeleteControlPos" style="float: none">
                                            <option value="-1">--Select Control Position--</option>
                                            <option value="1">Single Column</option>
                                            <option value="2">Double Column</option>
                                        </select>
                                    </div>
                                </div>
                                <div id="divDeleteProperties" style="float: left; display:none; width: 50%">
                                    <jsp:include page="DeleteTabProperties.jsp"/>
                                </div>
                                <br style="clear: both"/><br />
                                <div id="hiddenDivDeleteTab" style="display: none"></div>
                            </div>
                            <div id="viewFilterTab" style="border:1px solid #9A9A9A; display:none">
                                <div class="report_text" align="center" style="font-size: large; font-weight: bold">Filtering On View</div>
                                <div class="report_content" align="right" style="float: left; width: 50%;">
                                    <table cellpadding="0" cellspacing="2">
                                        <tbody style="font-weight: bold">
                                            <tr>
                                                <td></td>
                                                <td>
                                                    <input type="checkbox" id="chkColumns" name="chkColumns" />Columns Tab
                                                </td>
                                            </tr>
                                            <tr>
                                                <td></td>
                                                <td>
                                                    <input type="checkbox" id="chkExport" name="chkExport" checked disabled />Export Tab
                                                </td>
                                            </tr>
                                        </tbody>
                                        <tbody>
                                            <tr>
                                                <td></td>
                                                <td>
                                                    <input type="checkbox" id="chkOnScreen" name="chkOnScreen" checked disabled />On Screen
                                                    <input type="checkbox" id="chkPdf" name="chkPdf" />PDF
                                                    <input type="checkbox" id="chkXls" name="chkXls" />XLS
                                                </td>
                                            </tr>
                                        </tbody>
                                        <tbody style="font-weight: bold">
                                            <tr>
                                                <td></td>
                                                <td>
                                                    <input type="checkbox" id="chkFilter" name="chkFilter" checked disabled />Filter Tab
                                                </td>
                                            </tr>
                                        </tbody>
                                        <tbody>
                                            <tr>
                                                <td align="right">
                                                    <sup style="color: red">*</sup>Field :
                                                </td>
                                                <td>
                                                    <select name="cmbViewField" id="cmbViewField" onblur="javascript: setIdNameProperties('View')">
                                                        <option value="-1">--Select Field--</option>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="right">
                                                    <sup style="color: red">*</sup>Control :
                                                </td>
                                                <td>
                                                    <select name="cmbViewControl" id="cmbViewControl" onchange="javascript: showViewControlProperties()">
                                                        <option value="-1">--Select Control--</option>
                                                        <option value="TextBox">TextBox</option>
                                                        <option value="Password">Password</option>
                                                        <option value="TextArea">TextArea</option>
                                                        <option value="ComboBox">ComboBox</option>
                                                        <option value="TextLikeCombo">TextLike Combo</option>
                                                        <option value="CheckBox">CheckBox</option>
                                                        <option value="Radio">Radio</option>
                                                        <option value="DatePicker">DatePicker</option>
                                                        <option value="File">File</option>
                                                        <option value="FileBox">FileBox</option>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="right">
                                                    <sup style="color: red">*</sup>Tab Index :
                                                </td>
                                                <td>
                                                    <input type="text" id="txtViewTabIndex" name="txtViewTabIndex" />
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="right">
                                                    Label :
                                                </td>
                                                <td align="left">
                                                    <input type="text" id="txtViewLabel" name="txtViewLabel" />
                                                </td>
                                            </tr>
                                            <tr>
                                                <td></td>
                                                <td align="center">
                                                    <input class="button" align="middle" type="button" value="Add"
                                                           id="btnAddTabView" name="btnAddTabView" onclick="validateViewTabData()" />
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                    <div id="viewTabList" align="right" style="display:none">
                                        <table id="viewTabListTable" align="right" style="border:1px solid #9A9A9A;" width="45%">
                                            <th align="center" colspan="5" style="color: #9A9A9A">Field List</th>
                                            <tr>
                                                <td style="color: #9A9A9A; font-weight: bold">
                                                    Field
                                                </td>
                                                <td style="color: #9A9A9A; font-weight: bold;">
                                                    Control
                                                </td>
                                                <td width="10%" align="right" style="color: #9A9A9A; font-weight: bold">
                                                    Position
                                                </td>
                                                <td width="10%" style="color: #9A9A9A; font-weight: bold">
                                                    Modify
                                                </td>
                                                <td width="10%" style="color: #9A9A9A; font-weight: bold">
                                                    Remove
                                                </td>
                                            </tr>
                                        </table>
                                    </div>
                                    <br clear="both"/><br />
                                    <div style="font-size: 11px; letter-spacing: 1px; line-height: 26px">
                                        <sup style="color: red">*</sup>Control Position:
                                        <select name="cmbViewControlPos" id="cmbViewControlPos" style="float: none">
                                            <option value="-1">--Select Control Position--</option>
                                            <option value="1">Single Column</option>
                                            <option value="2">Double Column</option>
                                        </select>
                                    </div>
                                </div>
                                <div id="divViewProperties" style="float: left; display:none; width: 50%">
                                    <jsp:include page="ViewTabProperties.jsp"/>
                                </div>
                                <br style="clear: both"/><br />
                                <div id="hiddenDivViewTab" style="display: none"></div>
                            </div>
                        </div>
                        <div>
                            <input class="button" type="button" name="btnPrevious" value="Previous" onclick="javascript: showPrevMasterDataSelection();" />
                            <input class="button" type="button" name="btnNext" value="Next" onclick="javascript: showFinish();" />
                            <input class="button" type="button" name="btnCancel" value="Cancel" onclick="window.history.back()" />
                        </div>
                    </div>
                </div>
            </div>
            <%--Finish--%>
            <div class="container" id="Finish" style="display: none">
                <div class="content" id="menuLoader7">
                    <div class="menu_new">
                        <div class="menu_caption_bg">
                            <div class="menu_caption_text">Finish</div>
                        </div>
                        <table align="center">
                            <tr>
                                <td></td>
                                <td align="center">
                                    Click Finish To Generate Master.
                                </td>
                            </tr>
                            <tr>
                                <td></td>
                                <td align="center">
                                    <input class="button" type="button" name="btnPrevious" value="Previous" onclick="javascript: showPrevMasterFilterSelection()" />
                                    <input class="button" type="button" id="btnFinish" name="btnFinish" value="Finish" onclick="javascript: onFinish();" />
                                    <input class="button" type="button" name="btnCancel" value="Cancel" onclick="window.history.back()" />
                                </td>
                            </tr>
                        </table>
                        <div align="center" id="divFinish"></div>
                        <div align="center" id="divColumnTypes" style="display: none"></div>
                    </div>
                </div>
            </div>
        </form>
    </c:when>
    <c:when test="${process eq 'databaseType'}">
        <input type="hidden" id="txtDataBaseType" name="txtDataBaseType" readonly value="${database}" />
    </c:when>
    <c:when test="${process eq 'tables'}">
        <option value="-1" selected>-- Select Table --</option>
        <c:forEach items="${tableNames}" var="table">
            <option value="${table}">${table}</option>
        </c:forEach>
    </c:when>
    <c:when test="${process eq 'msttablecolumns'}">
        <option value="-1" selected>-- Select Field --</option>
        <c:forEach items="${mstColumnNames}" var="column">
            <c:choose>
                <c:when test="${primeKey ne 'null' && primeKey eq column}">
                    <option selected value="${column}">${column}</option>
                </c:when>
                <c:otherwise>
                    <option value="${column}">${column}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>
    <c:when test="${process eq 'msttablecolumnwidth'}">
        <c:if test="${mstColumnWidth ne null and mstColumnWidth ne ''}">
            <input type="hidden" id="mstColumnWidth" name="mstColumnWidth" value="${mstColumnWidth}" disabled />
        </c:if>
    </c:when>
    <c:when test="${process eq 'chkSequence'}">
        ${seqStatus}
    </c:when>
    <c:when test="${process eq 'chkQuery'}">
        ${qStatus}
    </c:when>
    <c:when test="${process eq 'mstColumnTypes'}">
        ${mstColumnTypes}
    </c:when>
    <c:when test="${process eq 'wsdlParse'}">
        ${wsdlData}
    </c:when>
    <c:when test="${process eq 'finish'}">
        Module Successfully Generated <br/>
        Your Master Reference No. is ${SRNO} <br/>
        <a href="generated/${codeFileName}" >Download CodeFile</a>
        <a href="generated/${FileName}" >Download SRSFile</a>
    </c:when>
</c:choose>
