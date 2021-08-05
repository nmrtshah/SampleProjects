<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<c:choose>
    <c:when test="${process eq 'main'}">
        <form  name="MenuForm" id="MenuForm" method="post" action="">
            <%--Project Menu Specification--%>
            <div class="container" id="ProjectSpecification">
                <div class="content" id="menuLoader">
                    <div class="menu_new">
                        <div class="menu_caption_bg" align="center">
                            <div class="menu_caption_text">Project & Module Specification</div>
                        </div>
                        <table align="center" width="100%" id="tblprojectmodule">
                            <tr style="border-bottom: red;border-style: solid">
                                <td align="right" width="40%">
                                    <sup style="color: red">*</sup>Project Name :
                                </td>
                                <td width="30%">
                                    <select id="cmbProjectName" name="cmbProjectName" tabindex="1" onblur="getRefNumber()">
                                        <option value="-1" selected>-- Select Project Name --</option>
                                        <c:forEach items="${values}" var="val" varStatus="loop">
                                            <option value="${val}">${projectnm[loop.index]}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td align="left"></td>
                            </tr>
                            <tr>
                                <td align="right">
                                    Reference No (for summary report only):
                                </td>
                                <td>
                                    <select id="cmbRefNo" name="cmbRefNo" tabindex="2"></select>
                                </td>

                            </tr>
                            <tr>
                                <td align="right">
                                    <sup style="color: red">*</sup>Module Name :
                                </td>
                                <td>
                                    <input type="text" tabindex="3" id="txtModuleName" name="txtModuleName">
                                </td>
                                <td></td>
                            </tr>
                            <tr>
                                <td align="right">
                                    Request No :
                                </td>
                                <td >
                                    <input type="text" id="txtReqNo" tabindex="4" name="txtReqNo">
                                    <div id="divReqNo"></div>
                                </td>
                                <td></td>
                            </tr>
                            <tr>
                                <td align="right">
                                    <sup style="color: red">*</sup>Alias Name :
                                </td>
                                <td>
                                    <select id="cmbAliasName" name="cmbAliasName" tabindex="5">
                                        <option value="-1" selected>-- Select Alias Name --</option>
                                        <c:forEach items="${aliaslist}" var="alias">
                                            <option value="${alias}">${alias}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td></td>
                            </tr>
                            <tr>
                                <td align="right">
                                    <sup style="color: red">*</sup>Connection Using Alias :
                                </td>
                                <td>
                                    <input type="radio" id="rdoAlias" tabindex="6" checked name="rdoConType" value="usingAlias" onclick="javascript:showDevServer('rdoAlias')">
                                </td>
                            </tr>
                            <tr style="height:5px">
                                <td align="right">or</td>
                                <td></td>
                            </tr>
                            <tr>
                                <td align="right">
                                    Connection Using Development Server :
                                </td>
                                <td>
                                    <input type="radio" id="rdoDevServer" style="float: left" tabindex="7" name="rdoConType" value="usingDirectCon"
                                           onclick="javascript:showDevServer('rdoDevServer')" />
                                    <select id="cmbDevServer" style="width: 250px" name="cmbDevServer" tabindex="8" disabled onchange="javascript:onChageServerType()">
                                        <option value="-1" selected>-- Select Server Name --</option>
                                        <option value="dev_mysql">dev-njindiainvest-MySQL</option>
                                        <option value="dev_db2_mysql">dev-db2-njindiainvest-MySQL</option>
                                        <option value="dev_tran">dev-mftran-ORACLE</option>
                                        <option value="dev_brok">dev-mfund-ORACLE</option>
                                    </select>
                                    <div style="display:none" id="divConNote"><sup style="color: red">*</sup>Please specify database with table name in query</div>
                                </td>
                            </tr>
                            <tr>
                                <td align="right">
                                    <sup style="color: red">*</sup>Main Query :
                                </td>
                                <td>
                                    <textarea id="txtQuery" name="txtQuery" rows="4" cols="1" tabindex="10" onblur="javascript:getMainQueryColumns()"></textarea>
                                </td>
                                <td></td>
                            </tr>
                            <tr>
                                <td></td>
                                <td align="center">
                                    <input class="button" type="button" id="btnAddQuery" name="btnAddQuery" value="Add Child Query" onclick="javascript:showAddMoreQuery()">
                                </td>
                                <td></td>
                            </tr>
                            <tbody id="addMoreQuery" style="width:100%"></tbody>
                            <tr>
                                <td>
                                </td>
                                <td>
                                    <div id="divresult"></div>
                                    <div id="divqueryresult" style="display: none"></div>
                                </td>
                                <td></td>
                            </tr>
                            <tr>
                                <td></td>
                                <td align="center">
                                    <input class="button" type="button" name="btnNext" value="Next" onclick="javascript:return showColumnSelection()">
                                    <input class="button" type="button" name="btnCancel" value="Cancel" onclick="window.history.back()">
                                </td>
                                <td></td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
            <%--Column Selection--%>
            <div class="container" id="ColumnSelection" style="display:none" >
                <div class="content" id="menuLoader2">
                    <div class="menu_new">
                        <div class="menu_caption_bg">
                            <div class="menu_caption_text">Columns selection to display on report</div>
                        </div>
                        <table align="center" width="40%">
                            <tr>
                                <td align="right" width="20%"></td>
                                <td align="left" width="15%" style="border-style: solid">
                                    <input type="checkbox" id="chkSelectAll" name="chkSelectAll" onclick="javascript: disableCombo();">Select All
                                </td>
                            </tr>
                            <tr>
                                <td width="20%">
                                    <sup style="color: red">*</sup>Select Columns: </td>
                                <td width="15%">
                                    <select multiple id="cmbShowColumns" name="cmbShowColumns" style="height: 100px"></select>
                                </td>
                            </tr>
                            <tr>
                                <td></td>
                                <td align="center">
                                    <input class="button" type="button" name="btnPrevious" value="Previous" onclick="javascript:return showProjectSpecification()">
                                    <input class="button" type="button" name="btnNext" value="Next" onclick="javascript: showSRSSpecification()">
                                    <input class="button" type="button" name="btnCancel" value="Cancel" onclick="window.history.back()">
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
            <%--SRSSpecification--%>
            <div class="container" id="SRSSpecification" style="display:none">
                <div class="content" id="menuLoader3">
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
                                    <textarea id="txtProblemStmt" name="txtProblemStmt" rows="4" cols="5"></textarea>
                                </td>
                            </tr>
                            <tr>
                                <td align="right">
                                    Solution / Objective :
                                </td>
                                <td>
                                    <textarea id="txtSolution" name="txtSolution" rows="4" cols="5"></textarea>
                                </td>
                            </tr>
                            <tr>
                                <td align="right">
                                    Existing Practice :
                                </td>
                                <td>
                                    <input type="text" id="txtExistingPractice" name="txtExistingPractice">
                                </td>
                            </tr>
                            <tr>
                                <td align="right">
                                    Placement :
                                </td>
                                <td>
                                    <input type="text" id="txtPlacement" name="txtPlacement">
                                </td>
                            </tr>
                            <tr>
                                <td></td>
                                <td align="center">
                                    <input class="button" type="button" name="btnPrevious" value="Previous" onclick="javascript:  showHide('ColumnSelection');">
                                    <input class="button" type="button" name="btnNext" value="Next" onclick="javascript: showTabSelection()">
                                    <input class="button" type="button" name="btnCancel" value="Cancel" onclick="window.history.back()">
                                </td>
                            </tr>
                        </table>
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
                                <td width="25%" align="right">
                                    <sup style="color: red">*</sup>Report Title :
                                </td>
                                <td width="75%">
                                    <input type="text" id="txtReportTitle" name="txtReportTitle" value="">
                                </td>
                            </tr>
                            <tr>
                                <td align="right">Date Selection : </td>
                                <td>
                                    <input type="checkbox" id="chkDateSelection" name="chkDateSelection">
                                </td>
                            </tr>
                            <tr>
                                <td align="right">
                                    <sup style="color: red">*</sup>Tab Selection :
                                </td>
                                <td>
                                    <input type="checkbox" id="chkReportType" name="chkReportType">Report Type
                                    <input type="checkbox" id="chkFilters" name="chkFilters" checked disabled>Filters
                                    <input type="checkbox" id="chkExport" name="chkExport" checked disabled>Export
                                    <input type="checkbox" id="chkChart" name="chkChart">Chart
                                    <input type="checkbox" id="chkColumns" name="chkColumns">Columns
                                </td>
                            </tr>
                            <tr>
                                <td align="right">
                                    Header Required? :
                                </td>
                                <td>
                                    <input type="checkbox" id="chkHeaderReq" name="chkHeaderReq">
                                </td>
                            </tr>
                            <tr>
                                <td align="right">
                                    Footer Required? :
                                </td>
                                <td>
                                    <input type="checkbox" id="chkFooterReq" name="chkFooterReq">
                                </td>
                            </tr>
                            <tr>
                                <td align="right">
                                    <sup style="color: red">*</sup>MethodName Resolver :
                                </td>
                                <td>
                                    <input type="text" id="txtMethodNm" name="txtMethodNm" value="cmdAction" />
                                </td>
                            </tr>
                            <tr>
                                <td></td>
                                <td align="center">
                                    <input class="button" type="button" name="btnPrevious" value="Previous" onclick="javascript:showPrevSRSSpecification()">
                                    <input class="button" type="button" name="btnNext" value="Next" onclick="javascript:showReportData();">
                                    <input class="button" type="button" name="btnCancel" value="Cancel" onclick="window.history.back()">
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
            <%--ReportDataSelection--%>
            <div class="container" id="ReportDataSelection" align="center" style="border:1px solid #9A9A9A;display:none;">
                <div class="content" id="menuLoader5">
                    <div class="menu_new" style="border: 2px">
                        <div class="menu_caption_bg cursor-pointer" onclick="javascript:hide_menu('show_hide','hide_menu', 'nav_show','nav_hide')">
                            <div class="menu_caption_text">Report Data Selection</div>
                        </div>
                        <div class="collapsible_menu_tab">
                            <ul style="vertical-align: text-top;" id="maintab0"></ul>
                        </div>
                        <div class="report_content">
                            <div id="type" style="border:1px solid #9A9A9A;display: none">
                                <div class="report_text" align="center" style="font-size: large;font-weight: bold;">Report Type</div>
                                <div align="right" class="report_content" id="div1" style="float: left;width: 50%;color:#6F6F6F">
                                    <table cellspacing="2" cellpadding="0" border="0" width="60%">
                                        <tbody>
                                            <tr>
                                                <td width="20%" align="right">
                                                    <sup style="color: red">*</sup>Label :
                                                </td>
                                                <td width="30%">
                                                    <input type="text" name="rptTxtLabel" id="rptTxtLabel" class="text" onblur="javascript:removeRptSpace('rpt');">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="right">
                                                    <sup style="color: red">*</sup>Control :
                                                </td>
                                                <td>
                                                    <select name="rptCmbControl" id="rptCmbControl" onchange="javascript:validateControl('rpt')">
                                                        <option value="-1">--Select Control--</option>
                                                        <option value="TextBox">TextBox</option>
                                                        <option value="Password">Password</option>
                                                        <option value="TextArea">TextArea</option>
                                                        <option value="ComboBox">ComboBox</option>
                                                        <option value="TextLikeCombo">TextLike Combo</option>
                                                        <option value="CheckBox">CheckBox</option>
                                                        <option value="Radio">Radio</option>
                                                        <option value="DatePicker">DatePicker</option>
                                                        <option value="Submit Button">Submit Button</option>
                                                        <option value="Reset Button">Reset Button</option>
                                                        <option value="File">File</option>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="right">
                                                    <sup style="color: red">*</sup>TabIndex:
                                                </td>
                                                <td>
                                                    <input type="text" id="rptTxtTabIndex" name="rptTxtTabIndex">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td ></td>
                                                <td align="center">
                                                    <input class="button" type="button" value="Add More" id="rptBtnAdd" onclick="javascript:return validateTabData('rpt');">
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                    <div id="rptList" align="right" style="display:none">
                                        <table align="right" id="rptListTable"  style="border:1px solid #9A9A9A" width="45%">
                                            <th align="center" colspan="4" style="color: #9A9A9A">Label List</th>
                                            <tr>
                                                <td style="color: #9A9A9A;font-weight: bold">
                                                    Label
                                                </td>
                                                <td style="color: #9A9A9A;font-weight: bold">
                                                    Control
                                                </td>
                                                <td width="10%" align="right" style="font-weight: bold; color: #9A9A9A">
                                                    Position
                                                </td>
                                                <td width="10%" style="font-weight: bold;color: #9A9A9A">
                                                    Edit
                                                </td>
                                                <td width="10%" style="font-weight: bold;color: #9A9A9A">
                                                    Delete
                                                </td>
                                            </tr>
                                        </table>
                                    </div>
                                    <br clear="all"/><br />
                                    <div style="font-size: 11px;font-weight: bold;letter-spacing: 1px;line-height: 26px">
                                        <sup style="color: red">*</sup>ControlPosition:
                                        <select style="float: none" name="cmbRptControlPos" id="cmbRptControlPos">
                                            <option value="-1">--Select ControlPosition--</option>
                                            <option value="1">Single Column</option>
                                            <option value="2">Double Columns</option>
                                        </select>
                                    </div>
                                </div>
                                <div id="rptDivProperties" style="width: 50%;float: left;display:none">
                                    <jsp:include page="RptProperties.jsp"/>
                                </div>
                                <div class="clear"></div>
                                <div id="rptHiddenDiv" style="display: none"></div>
                            </div>
                            <div id="filter" style="border:1px solid #9A9A9A;display:none">
                                <div class="report_text" align="center" style="font-weight: bold;font-size: large;">Filter</div>
                                <div class="report_content" align="right" id="div2" style="float: left;width: 50%;color:#6F6F6F">
                                    <table cellspacing="2" cellpadding="0" border="0" width="60%">
                                        <tbody>
                                            <tr>
                                                <td width="20%" align="right">
                                                    <sup style="color: red">*</sup>Label :
                                                </td>
                                                <td width="30%">
                                                    <input type="text" name="fltrTxtLabel" id="fltrTxtLabel" class="text" onblur="javascript:removeRptSpace('fltr');">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="right">
                                                    <sup style="color: red">*</sup>Control :
                                                </td>
                                                <td>
                                                    <select name="fltrCmbControl" id="fltrCmbControl" onchange="javascript:validateControl('fltr')">
                                                        <option value="-1">--Select Control--</option>
                                                        <option value="TextBox">TextBox</option>
                                                        <option value="Password">Password</option>
                                                        <option value="TextArea">TextArea</option>
                                                        <option value="ComboBox">ComboBox</option>
                                                        <option value="TextLikeCombo">TextLike Combo</option>
                                                        <option value="CheckBox">CheckBox</option>
                                                        <option value="Radio">Radio</option>
                                                        <option value="DatePicker">DatePicker</option>
                                                        <option value="Submit Button">Submit Button</option>
                                                        <option value="Reset Button">Reset Button</option>
                                                        <option value="File">File</option>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="right">
                                                    <sup style="color: red">*</sup>TabIndex:
                                                </td>
                                                <td>
                                                    <input type="text" id="fltrTxtTabIndex" name="fltrTxtTabIndex">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td></td>
                                                <td align="center">
                                                    <input class="button" type="button" value="Add More" id="fltrBtnAdd" onclick="javascript:return validateTabData('fltr');">
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                    <div id="fltrList" align="right" style="display: none">
                                        <table id="fltrListTable" align="right" style="border:1px solid #9A9A9A;" width="45%">
                                            <th align="center" colspan="4" style="color: #9A9A9A">Label List</th>
                                            <tr>
                                                <td style="font-weight: bold; color: #9A9A9A">
                                                    Label
                                                </td>
                                                <td style="font-weight: bold; color: #9A9A9A">
                                                    Control
                                                </td>
                                                <td width="10%" align="right" style="font-weight: bold; color: #9A9A9A">
                                                    Position
                                                </td>
                                                <td width="10%" style="font-weight: bold; color: #9A9A9A">
                                                    Edit
                                                </td>
                                                <td width="10%" style="font-weight: bold; color: #9A9A9A">
                                                    Delete
                                                </td>
                                            </tr>
                                        </table>
                                    </div>
                                    <br clear="all"><br>
                                    <div style="font-size: 11px;font-weight: bold;letter-spacing: 1px;line-height: 26px;">
                                        <sup style="color: red">*</sup>ControlPosition:
                                        <select style="float: none" name="cmbControlPos" id="cmbControlPos">
                                            <option value="-1">--Select ControlPosition--</option>
                                            <option value="1">Single Column</option>
                                            <option value="2">Double Columns</option>
                                        </select>
                                    </div>
                                </div>
                                <div id="fltrDivProperties" style="width: 50%;float: left;display:none">
                                    <jsp:include page="FltrProperties.jsp"/>
                                </div>
                                <div id="fltrHiddenDiv" style="display: none"></div>
                                <div class="clear"></div>
                            </div>
                            <div id="export" style="display:none;border:1px solid #9A9A9A">
                                <div class="report_text" align="center" style="font-weight: bold;font-size: large;">Export</div>
                                <div class="report_content" align="center" style="color:#6F6F6F">
                                    <table align="center" cellspacing="2" cellpadding="0" border="0" width="100%" >
                                        <tr>
                                            <td align="right" width="40%">
                                                <sup style="color: red">*</sup>Formate :
                                            </td>
                                            <td width="60%">
                                                <input type="checkbox" id="chkOnScreen" name="chkOnScreen" checked>On screen
                                                <input type="checkbox" id="chkPDF" name="chkPDF">PDF
                                                <input type="checkbox" id="chkExcel" name="chkExcel">XLS
                                            </td>
                                        </tr>
                                        <tbody id="tbladdControl">
                                            <tr>
                                                <td align="right">
                                                    Add Controls :
                                                </td>
                                                <td>
                                                    <input type="checkbox" id="chkAddControl" name="chkAddControl" onchange="javascript:showAddControl()">
                                                </td>
                                            </tr>
                                        </tbody>
                                        <tbody id="tbladdCotrolDetail" style="display: none">
                                            <tr>
                                                <td align="right">
                                                    <sup style="color: red">*</sup>Column :
                                                </td>
                                                <td>
                                                    <select name="cmbColumnGrid" id="cmbColumnGrid"></select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="right">
                                                    <sup style="color: red">*</sup>Control :
                                                </td>
                                                <td>
                                                    <select name="cmbColumnControl" id="cmbColumnControl">
                                                        <option value="-1">--Select Control--</option>
                                                        <option value="txt">TextBox</option>
                                                        <option value="link">Link</option>
                                                        <option value="coro">ComboBox</option>
                                                        <option value="ch">CheckBox</option>
                                                        <option value="ra">Radio</option>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td ></td>
                                                <td align="left">
                                                    <input class="button" type="button" value="Add More" id="btnAddColumnControl" onclick="javascript:validateColumnControl();">
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                    <div id="columnControlList" align="center" style="display: none">
                                        <table id="columnControlListTable" align="center" style="border:1px solid #9A9A9A;" width="45%">
                                            <th align="center" colspan="4" style="color: #9A9A9A">Label List</th>
                                            <tr>
                                                <td style="font-weight: bold; color: #9A9A9A">
                                                    Column
                                                </td>
                                                <td style="font-weight: bold; color: #9A9A9A">
                                                    Control
                                                </td>
                                                <td width="10%" style="font-weight: bold; color: #9A9A9A">
                                                    Edit
                                                </td>
                                                <td width="10%" style="font-weight: bold; color: #9A9A9A">
                                                    Delete
                                                </td>
                                            </tr>
                                        </table>
                                    </div>
                                    <div id="hdncolumnControl" style="display: none"></div>
                                </div>
                                <div class="clear"></div>
                            </div>
                            <div id="chart" style="display:none;border:1px solid #9A9A9A">
                                <div class="report_text" align="center" style="font-weight: bold;font-size: large">Chart</div><br><br><br>
                                <div class="report_content" align="center" style="color:#6F6F6F">
                                    <table align="center" cellpadding="0" cellspacing="0" class="menu_subcaption graph_selection" style="color:#6F6F6F">
                                        <tr>
                                            <td align="center">
                                                <input type="checkbox" name="noChart" id="noChart" checked>
                                            </td>
                                            <td align="center">
                                                <input type="checkbox" name="pieChart" id="pieChart" onchange="javascript:showChartDetails('pie')">
                                            </td>
                                            <td align="center">
                                                <input type="checkbox" name="barChart" id="barChart" onchange="javascript:showChartDetails('bar')">
                                            </td>
                                            <td align="center">
                                                <input type="checkbox" name="lineChart" id="lineChart" onchange="javascript:showChartDetails('line')">
                                            </td>
                                            <td align="center">
                                                <input type="checkbox" name="areaChart" id="areaChart" onchange="javascript:showChartDetails('area')">
                                            </td>
                                        </tr>
                                        <tr>
                                            <td valign="top" align="center">No Chart</td>
                                            <td valign="top" align="center"><span id="graph1"></span></td>
                                            <td valign="top" align="center"><span id="graph2"></span></td>
                                            <td valign="top" nowrap align="center"><span id="graph3"></span></td>
                                            <td valign="top" align="center"><span id="graph4"></span></td>
                                        </tr>
                                        <tr>
                                            <td valign="top"></td>
                                            <td valign="top" align="center">Pie Chart</td>
                                            <td valign="top" align="center">Bar Chart</td>
                                            <td valign="top" nowrap align="center">Symbol Line Chart</td>
                                            <td valign="top" align="center">3d Line Chart</td>
                                        </tr>
                                    </table>
                                    <table id="showGraphDetails" cellpadding="0" cellspacing="0" style="color:#6F6F6F">
                                        <tbody id="pieChartDetail" style="display: none">
                                            <tr>
                                                <td rowspan="4" width="100">
                                                    Pie Chart
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="right">
                                                    <sup style="color: red">*</sup>Title :
                                                </td>
                                                <td>
                                                    <input type="text" id="pieTxtChartTitle" name="pieTxtChartTitle">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <sup style="color: red">*</sup>Pie Name :
                                                </td>
                                                <td>
                                                    <select id="pieCmbXaxisColumn" style="float: none"  name="pieCmbXaxisColumn"></select>
                                                </td>
                                            </tr>
                                            <tr >
                                                <td style="padding-top: ">
                                                    <sup style="color: red">*</sup>Pie Value :
                                                </td>
                                                <td>
                                                    <select id="pieCmbYaxisColumn" style="float: none" name="pieCmbYaxisColumn"
                                                            onchange="javascript:return checkYColType('pieCmbYaxisColumn')"></select>
                                                </td>
                                            </tr>
                                        </tbody>
                                        <tr style="height: 20px"><td></td></tr>
                                        <tbody id="barChartDetail" style="display: none">
                                            <tr>
                                                <td rowspan="6" width="100">
                                                    Bar Chart
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="right">
                                                    <sup style="color: red">*</sup>Title :
                                                </td>
                                                <td>
                                                    <input type="text" id="barTxtChartTitle" name="barTxtChartTitle">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="right">
                                                    <sup style="color: red">*</sup>X-axis Label :
                                                </td>
                                                <td>
                                                    <input type="text" id="barTxtXLabel" name="barTxtXLabel">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="right">
                                                    <sup style="color: red">*</sup>X-axis :
                                                </td>
                                                <td>
                                                    <select id="barCmbXaxisColumn" style="float: none"  name="barCmbXaxisColumn"></select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="right">
                                                    <sup style="color: red">*</sup>Y-axis Label :
                                                </td>
                                                <td>
                                                    <input type="text" id="barTxtYLabel" name="barTxtYLabel">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="right">
                                                    <sup style="color: red">*</sup>Y-axis :
                                                </td>
                                                <td>
                                                    <select id="barCmbYaxisColumn" style="float: none"  name="barCmbYaxisColumn"
                                                            onchange="javascript:return checkYColType('barCmbYaxisColumn')"></select>
                                                </td>
                                                <td>
                                                    <img width="20" height="20" alt="Add Another Y-axis Column" onclick="javascript:addNewYaxisColumn('barCmbYaxisColumn','bar')"
                                                         name="addYaxisColumn" id="addYaxisColumn" src="./images/add.gif">
                                                </td>
                                            </tr>
                                        </tbody>
                                        <tr style="height: 20px"><td></td></tr>
                                        <tbody id="lineChartDetail" style="display: none">
                                            <tr>
                                                <td rowspan="6" width="100">
                                                    Line Chart
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="right">
                                                    <sup style="color: red">*</sup>Title :
                                                </td>
                                                <td>
                                                    <input type="text" id="lineTxtChartTitle" name="lineTxtChartTitle">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="right">
                                                    <sup style="color: red">*</sup>X-axis Label :
                                                </td>
                                                <td>
                                                    <input type="text" id="lineTxtXLabel" name="lineTxtXLabel">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="right">
                                                    <sup style="color: red">*</sup>X-axis :
                                                </td>
                                                <td>
                                                    <select id="lineCmbXaxisColumn" style="float: none"  name="lineCmbXaxisColumn"></select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="right">
                                                    <sup style="color: red">*</sup>Y-axis Label :
                                                </td>
                                                <td>
                                                    <input type="text" id="lineTxtYLabel" name="lineTxtYLabel">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="right">
                                                    <sup style="color: red">*</sup>Y-axis :
                                                </td>
                                                <td>
                                                    <select id="lineCmbYaxisColumn" style="float: none"  name="lineCmbYaxisColumn"
                                                            onchange="javascript:return checkYColType('lineCmbYaxisColumn')"></select>
                                                </td>
                                                <td>
                                                    <img width="20" height="20" alt="Add Another Y-axis Column" onclick="javascript:addNewYaxisColumn('lineCmbYaxisColumn','line')"
                                                         name="addYaxisColumn" id="addYaxisColumn" src="./images/add.gif">
                                                </td>
                                            </tr>
                                        </tbody>
                                        <tr style="height: 20px"><td></td></tr>
                                        <tbody id="areaChartDetail" style="display: none">
                                            <tr>
                                                <td rowspan="6" width="100">
                                                    Area Chart
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="right">
                                                    <sup style="color: red">*</sup>Title :
                                                </td>
                                                <td>
                                                    <input type="text" id="areaTxtChartTitle" name="areaTxtChartTitle">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="right">
                                                    <sup style="color: red">*</sup>X-axis Label :
                                                </td>
                                                <td>
                                                    <input type="text" id="areaTxtXLabel" name="areaTxtXLabel">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="right">
                                                    <sup style="color: red">*</sup>X-axis :
                                                </td>
                                                <td>
                                                    <select id="areaCmbXaxisColumn" style="float: none"  name="areaCmbXaxisColumn"></select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="right">
                                                    <sup style="color: red">*</sup>Y-axis Label :
                                                </td>
                                                <td>
                                                    <input type="text" id="areaTxtYLabel" name="areaTxtYLabel">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="right">
                                                    <sup style="color: red">*</sup>Y-axis :
                                                </td>
                                                <td>
                                                    <select id="areaCmbYaxisColumn" style="float: none"  name="areaCmbYaxisColumn"
                                                            onchange="javascript:return checkYColType('areaCmbYaxisColumn')"></select>
                                                </td>
                                                <td>
                                                    <img width="20" height="20" alt="Add Another Y-axis Column" onclick="javascript:addNewYaxisColumn('areaCmbYaxisColumn','area')"
                                                         name="addYaxisColumn" id="addYaxisColumn" src="./images/add.gif">
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                                <div class="clear"><br>
                                </div>
                            </div>
                            <label id="lstCol"></label>
                            <div>
                                <input class="button" type="button" name="btnPrevious" value="Previous" onclick="javascript:showTabSelection();">
                                <input class="button" type="button" name="btnNext" value="Next" onclick="javascript:showPageFooter();">
                                <input class="button" type="button" name="btnCancel" value="Cancel" onclick="window.history.back()">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <%--PageFooter--%>
            <div class="container" id="PageFooter" style="display:none">
                <div class="content" id="menuLoader6">
                    <div class="menu_new">
                        <div class="menu_caption_bg">
                            <div class="menu_caption_text">PageFooter</div>
                        </div>
                        <table align="center">
                            <tr>
                                <td width="25%" align="right">
                                    Grouping :
                                </td>
                                <td width="75%">
                                    <input type="checkbox" id="chkGrouping" name="chkGrouping" onchange="javascript:onChangeGrouping();">
                                </td>
                            </tr>
                            <tbody id="divGrouping" style="display: none">
                                <tr>
                                    <td align="right">
                                        Group Field :
                                    </td>
                                    <td>
                                        <select id="cmbGroupField" name="cmbGroupField"></select>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="right">
                                        Group Footer :
                                    </td>
                                    <td>
                                        <input type="checkbox" id="chkGroupFooter" name="chkGroupFooter" onchange="javascript:onChangeGroupFooter();">
                                    </td>
                                </tr>
                            </tbody>
                            <tbody id="divGroupFooter" style="display: none">
                                <tr>
                                    <td></td>
                                    <td>
                                        <label id="list" for="btnAdd"></label>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="right">
                                        Column :
                                    </td>
                                    <td>
                                        <select id="cmbGrpFooterColumn" name="cmbGrpFooterColumn"></select>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="right">
                                        Calculation :
                                    </td>
                                    <td>
                                        <select id="cmbGrpFooterCalculation" name="cmbGrpFooterCalculation">
                                            <option value="-1">-- Select Calculation --</option>

                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td></td>
                                    <td align="center">
                                        <input class="button" type="button" id="btnGrpFooterAdd" name="btnGrpFooterAdd" value="Add More" onclick="javascript:onAdd();">
                                    </td>
                                </tr>
                            </tbody>
                            <tbody id="divpagefooter">
                                <tr>
                                    <td align="right" valign="top">
                                        Totals :
                                    </td>
                                    <td ><input type="checkbox" id="chkPageFooter" name="chkPageFooter" onchange="javascript: showtTotalCols();"> Page Wise Total
                                        <br><input type="checkbox" id="chkGrandTotal" name="chkGrandTotal" onchange="javascript: showtTotalCols();"> GrandTotal
                                        <div id="divPageFooterColumn" style="display:none">
                                            <select id="cmbPageFooterColumn" multiple name="cmbPageFooterColumn" size="4" style="height:80px;width:200px;"></select>
                                        </div>
                                    </td>
                                </tr>
                            </tbody>
                            <tr>
                                <td align="right">
                                    <sup style="color: red">*</sup>PrimaryKey :
                                </td>
                                <td>
                                    <select id="cmbPrimaryKey" name="cmbPrimaryKey"></select>
                                </td>
                            </tr>
                            <tr>
                                <td></td>
                                <td align="center">
                                    <input class="button" type="button" name="btnPrevious" value="Previous" onclick="javascript:showPrevReportData();">
                                    <input class="button" type="button" name="btnNext" value="Next" onclick="javascript:return showFinish();">
                                    <input class="button" type="button" name="btnCancel" value="Cancel" onclick="window.history.back()">
                                </td>
                            </tr>
                        </table>
                        <div id="grpFooterList" align="center" style="display:none">
                            <table id="grpFooterListTable" align="center" style="border:1px solid #9A9A9A;" width="45%">
                                <th align="center" colspan="4">Footer Columns List</th>
                                <tr>
                                    <td style="font-weight: bold">
                                        Column
                                    </td>
                                    <td style="font-weight: bold">
                                        Calculation
                                    </td>
                                    <td width="10%" style="font-weight: bold">
                                        Edit
                                    </td>
                                    <td width="10%" style="font-weight: bold">
                                        Delete
                                    </td>
                                </tr>
                            </table>
                        </div>
                        <div id="hdngrpFooter" style="display: none"></div>
                    </div>
                </div>
            </div>
            <%--finish--%>
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
                                    You have Successfully Created a Report.
                                    Click Finish to Generate it.
                                </td>
                            </tr>
                            <tr>
                                <td></td>
                                <td align="center">
                                    <input class="button" type="button" name="btnPrevious" value="Previous" onclick="javascript: showPageFooter();">
                                    <input class="button" type="button" id="btnFinish" name="btnFinish" value="Finish" onclick="onFinish();">
                                    <input class="button" type="button" name="btnCancel" value="Cancel" onclick="window.history.back()">
                                </td>
                            </tr>
                        </table>
                        <div align="center" id="divFinish"></div>
                        <select id="cmbTempColResult" name="cmbTempColResult" style="display: none"></select>
                        <select id="cmbChildQueryColumn" name="cmbChildQueryColumn" style="display: none"></select>
                    </div>
                </div>
            </div>
        </form>
    </c:when>
    <c:when test="${process eq 'reqno'}">
        ${ans}
    </c:when>
    <c:when test="${process eq 'check'}">
        ${qStatus}
    </c:when>
    <c:when test="${process eq 'group'}">
        ${columnDetail}
    </c:when>
    <c:when test="${process eq 'columnname'}">
        <option value="-1">-- Select Column --</option>
        <c:forEach items="${columnNames}" var="column">
            <option value="${column}">${column}</option>
        </c:forEach>
    </c:when>
    <c:when test="${process eq 'referenceNo'}">
        <option value="">-- Select Reference Number --</option>
        <c:forEach items="${refNo}" var="number">
            <option value="${number.SRNO}">${number.SRNO}-${number.MODULE_NAME}</option>
        </c:forEach>
    </c:when>
    <c:when test="${process eq 'wsdlParse'}">
        ${wsdlData}
    </c:when>
    <c:when test="${process eq 'finish'}">
        <c:if test="${codeFileName ne null }">
            Module Successfully Generated <br/>
            Your Report Reference No. is ${SRNo} <br/>
            <a href="generated/${codeFileName}">Download CodeFile</a>
            <a href="generated/${FileName}">Download SRSFile</a>
        </c:if>
    </c:when>
</c:choose>
