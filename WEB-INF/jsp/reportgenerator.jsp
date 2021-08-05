<%--
    Document   : reportgenerator
    Created on : Jan 19, 2011, 11:07:47 AM
    Author     : njuser
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="com.finlogic.apps.finstudio.formbean.ReportGeneratorFormBean" %>
<%@page import="java.util.ArrayList" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:choose>

    <c:when test="${process eq 'generateReport'}">

        <c:if test="${reportNo ne null}">
            Module Successfully Generated <br/>
            Module Number: ${reportNo}<br/>
            <a href="generated/${reportNo}.zip" >Download</a>
        </c:if>
        <br/>
        <input name="back" value="Back" type="button" onclick="javascript: showMenuTable();"/>
    </c:when>

    <c:when test="${process eq 'createMenu'}">

        <html>
            <head>
                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                <link rel="stylesheet" type="text/css" href="${finlibPath}/jquery/jquery-ui-1.8.2/development-bundle/themes/njred/jquery.ui.all.css"/>
                <link href="css/main.css" rel="stylesheet" type="text/css"/>
                <link rel="stylesheet" type="text/css" href="${finlibPath}/jquery-ui-1.8.10.custom/css/ui-lightness/jquery-ui-1.8.10.custom.css"/>

                <script type="text/javascript" src="${finlibPath}/jquery/jquery-ui-1.8.2/development-bundle/jquery-1.4.2.js"></script>
                <script type="text/javascript" src="${finlibPath}/jquery/jquery-ui-1.8.10.custom/js/jquery-ui-1.8.10.custom.min.js"></script>
                <script type="text/javascript" src="${finlibPath}/resource/ajax.js"></script>
                <script type="text/javascript" src="javascript/common_validations.js"></script>
                <script type="text/javascript" src="javascript/validate.js"></script>
                <script type="text/javascript" src="javascript/ajaxfunctions.js"></script>
                <script type="text/javascript" src="javascript/reportgenerator.js"></script>

                <title>Report Generator</title>
            </head>
            <body>

                <div id="createMenu">
                    <form id ="reportgenerator" name ="reportgenerator"action="reportgenerator.fin?cmdAction=generateReport" method="post">

                        <input type="hidden" name="userName" value="${ACLEmpCode}" >

                        <table border="0" align="center" class="tbl_border1" id="reportgeneratortable" width="52%">
                            <tr class="tbl_h1_bg">
                                <th colspan="100%">Report Generation</th>
                            </tr>
                            <tr>
                                <th colspan="100%">&nbsp;</th>
                            </tr>

                            <tr>
                                <td align="right" width="50%">Project Name:</td>
                                <td align="left"><input type="text" id="projectnameid" name="projectName" onblur="javascript: compare_string_rule_alpha(document.getElementById('projectnameid'));"></td>
                            </tr>

                            <tr>
                                <td align="right" width="50%">Module Name:</td>
                                <td align="left"><input type="text" id="modulenameid" name="moduleName" onblur="javascript: compare_string_rule_alpha(document.getElementById('modulenameid'));"></td>
                            </tr>

                            <tr>
                                <td align="right">Alias Name:</td>
                                <td align="left"><input id="aliasnameid" type="text" name="aliasName" onblur="javascript: checkAliasName(this.form);"></td>
                            </tr>

                            <tr>
                                <td align="right">Database Type:</td>
                                <td align="left" width="44">
                                    <select id="databaseType" name="databaseType">
                                        <option value="Oracle">Oracle</option>
                                        <option value="MYSQL">MYSQL</option>
                                    </select>
                                </td>
                            </tr>

                            <tr>
                                <td align="right" valign="top">Query:</td>
                                <td align="left"><textarea name="query" id="queryid" cols="18" rows="4"></textarea></td>
                            </tr>

                            <tr>
                                <td colspan="100%">&nbsp;</td>
                            </tr>
                            <tr>
                                <td align="center" colspan="100%" id="validation">
                                    <input type="button"  id="submitbtn" name="submit" value="Submit" onclick="javascript: submitCreateMenu();">
                                    <input type="reset"  id="resetbtn" name="reset" value="Reset" onclick="">
                                </td>
                            </tr>

                        </table>

                    </form>
                </div>

                <div id="specifications" align="center">

                </div>

                <div id ="view" align="center">

                </div>

                <div id="generateReport" align="center">

                </div>

            </body>
        </html>
    </c:when>

    <c:when test="${process eq 'validateMenu'}">

        ${message}<br>
        <input type="button" name="optionBack" id="optionBack" value="Back" onclick="javascript: showMenuTable(); ">

    </c:when>

    <c:when test="${process eq 'specifications'}">
        <%
                    String columns = "";
                    ArrayList<String> colnames = (ArrayList<String>) request.getAttribute("columnnames");

                    for (int i = 0; i < colnames.size(); i++)
                    {
                        columns += "<option value='" + colnames.get(i) + "'>" + colnames.get(i) + "</option>";
                    }
        %>
        <div id="addFooterDiv" style="display: none">
            <select id='selectGroupFooter' name='selectGroupFooter'>
                <%=columns%>
            </select>
        </div>

        <form name ="specificationform" action ="">
            <table border="0" align="center" class="tbl_border1" id="specificationtable" width="52%">
                <tr class="tbl_h1_bg">
                    <th colspan="100%">Specifications</th>
                </tr>

                <tr>
                    <th colspan="100%">&nbsp;</th>
                </tr>
                <tr>
                    <td colspan="100%" align="center" style="font-size: 14px">
                        Menu Specifications
                    </td>
                </tr>
                <tr>
                    <td colspan="100%" align="center">
                        <div id ="variableDiv">
                            <table id="variableDivTable" border="0" align="center" width="100%">
                                <tr>
                                    <td align="right" width="50%">
                                        Input Name :
                                        <br>
                                        Input Control :
                                    </td>
                                    <td align="left">
                                        <input type="text" name="inputName" id="inputName" size="19" onblur="javascript: compare_string_rule_alpha_number(document.getElementById('inputName'));">
                                               <br>
                                        <select id="inputControl" name="inputControl">
                                            <option value="checkbox">Checkbox</option>
                                            <option value="combobox">Combobox</option>
                                            <option value="file">File</option>
                                            <option value="radio">Radio</option>
                                            <option value="text">Textbox</option>
                                        </select>
                                    </td>
                                </tr>
                                <tr>

                                    <td align="right">
                                        <input type="button" name="addVariable" id="addVariable" value="Add More"onclick="javascript: addRowToVariable();"
                                    </td>
                                    <td align="left">
                                        <input type="button" name="removeVariable" id="removeVariable" value="Remove" onclick="javascript: removeRowFromVariable();"
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </td>
                </tr>

                <tr>
                    <th colspan="100%">&nbsp;</th>
                </tr>
                <tr>
                    <td align="center" colspan="100%" style="font-size: 14px">
                        Report Specifications
                    </td>
                </tr>
                <tr>
                    <td colspan="100%" align="center">
                        <div>
                            <table border ="0" align="center" width="100%">
                                <tr>
                                    <td align="right" width="50%">Grouping :</td>
                                    <td align="left">
                                        <select id="grouping" name="grouping">
                                            <option value="none">none</option>
                                            <c:forEach var="column" items="${columnnames}">
                                                <option value="${column}">${column}</option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                </tr>

                                <tr>
                                    <td align="right"> Add Group Footer :</td>
                                    <td align="left"><input type="checkbox" id="groupFooter" name="groupFooter" onclick="javascript: showAddGroupFooter()"></td>
                                </tr>

                                <tr>
                                    <td colspan="100%" align="right">
                                        <div id ="addGroupFooterDiv" align="right">
                                            <table width="100%" border="0" id="addGroupFooterTable" align="center" style="display: none">
                                                <tr align="center">

                                                    <td align="right" width="50%">
                                                        Column :
                                                        <br>
                                                        Calculation :
                                                    </td>
                                                    <td align="left">
                                                        <select id="selectGroupFooter" name="selectGroupFooter">
                                                            <c:forEach var="column" items="${columnnames}">
                                                                <option value="${column}">${column}</option>
                                                            </c:forEach>
                                                        </select>
                                                        <br>
                                                        <select id="calculation" name="calculation">
                                                            <option value="min">Minimum</option>
                                                            <option value="max">Maximum</option>
                                                            <option value="sum">Sum</option>
                                                            <option value="avg">Average</option>
                                                            <option value="count">Count</option>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>

                                                    <td align="right">
                                                        <input type="button" name="addMoreFooter" value="Add More" onclick="javascript: addRowToFooterTable();">
                                                    </td>
                                                    <td align="left">
                                                        <input type="button" name="removeFooter" value="Remove" onclick="javascript: removeRowFromFooterTable();">
                                                    </td>
                                                </tr>
                                            </table>
                                        </div>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </td>
                </tr>
                <tr>
                    <th colspan="100%">&nbsp;</th>
                </tr>
                <tr>
                    <td align="right" width="45%">
                        <input type="button" name="optionSubmit" id="optionSubmit" value="Next" onclick="javascript: submitSpecifications();"
                    </td>
                    <td align="left">
                        <input type="reset" name="optionReset" id ="optionReset" value="Reset" onclick="">
                        <input type="button" name="optionBack" id="optionBack" value="Back" onclick="javascript: showMenuTable(); ">
                    </td>
                </tr>
            </table>
        </form>

    </c:when>

    <c:when test="${process eq 'view'}">

        <%
                    String column = "";
                    ArrayList<String> colname = (ArrayList<String>) request.getAttribute("columnnames");

                    for (int i = 0; i < colname.size(); i++)
                    {
                        column += "<option value='" + colname.get(i) + "'>" + colname.get(i) + "</option>";
                    }
        %>
        <div id="addControlDiv" style="display: none">
            <select id='selectColumn' name='selectColumn'>
                <%=column%>
            </select>
        </div>


        <form name="viewform"  action="">
            <table border="0" align="center" class="tbl_border1" id="selectviewtable" width="52%">
                <tr class="tbl_h1_bg">
                    <th colspan="100%">Select View</th>
                </tr>

                <tr>
                    <th colspan="100%">&nbsp;</th>
                </tr>

                <tr>
                    <td align="right" valign="top" width="50%">
                        Grid :
                    </td>
                    <td align="left">
                        <input type="checkbox" id="grid" name="grid" onclick="javascript: showGridOptions();">
                    </td>
                </tr>
                <tr>
                    <td  align="center" colspan="100%">
                        <div id="gridoptions" align="center">
                            <table width="100%" border="0" class="tbl_border1"align="left" id="gridOptionsTable" style="display: none">
                                <tr>
                                    <td align="right" width="50%">Grid Column Primarykey :</td>
                                    <td align="left" width="50%">
                                        <select id="gridColumnPK" name="gridColumnPK">
                                            <c:forEach var="column" items="${columnnames}">
                                                <option value="${column}">${column}</option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                </tr>

                                <tr>
                                    <td align="right">Paging :</td>
                                    <td align="left"><input type="checkbox" id="paging" name="paging" onclick="javascript: showRecordCountQuery();"></td>
                                </tr>
                                <tr>
                                    <td></td>
                                    <td>
                                        <div id ="recordCountDiv" align="right">
                                            <table border ="0" id="recordCountTable" align="right" style="display: none">
                                                <tr>
                                                    <td align="left">Records Count Query:</td>
                                                </tr>
                                                <tr>
                                                    <td align="left"><textarea id="recordCountQueryid" name="recordCountQuery" cols="24" rows="4">SELECT COUNT(*) FROM (<%=request.getParameter("query")%>)X</textarea></td>
                                                </tr>
                                            </table>
                                        </div>
                                    </td>
                                </tr>

                                <tr>
                                    <td align="right">Add Control :</td>
                                    <td align="left">
                                        <input type="checkbox" id="addControl" name="addControl" onclick="javascript: showAddControl();">
                                    </td>
                                </tr>
                                <tr>

                                    <td colspan="100%" align="right">
                                        <div id ="addControlDiv" align="right">
                                            <table width="100%" border="0" id="addControlTable" align="right" style="display: none">
                                                <tr>

                                                    <td align="right" width="50%">
                                                        Control :
                                                        <br>
                                                        Column :
                                                        <br>
                                                        Index :
                                                    </td>
                                                    <td align="left">
                                                        <select id="selectControl" name="selectControl">

                                                            <option value="checkbox">Checkbox</option>
                                                            <option value="link">Link</option>
                                                            <option value="radio">Radio</option>
                                                            <option value="text">Textbox</option>
                                                        </select>
                                                        <br/>

                                                        <select id="selectColumn" name="selectColumn">

                                                            <c:forEach var="column" items="${columnnames}">
                                                                <option value="${column}">${column}</option>
                                                            </c:forEach>
                                                        </select>
                                                        <br/>

                                                        <input align="left" size="19" type="text" name="indexNumber" id="indexNumber">
                                                    </td>
                                                </tr>
                                                <tr align="right">
                                                    <td align="right">
                                                        <input type="button" name="addMore" value="Add More" onclick="javascript: addRowToTable();">
                                                    </td>
                                                    <td align="left">
                                                        <input type="button" name="remove" value="Remove" onclick="javascript: removeRowFromTable();">
                                                    </td>
                                                </tr>
                                            </table>
                                        </div>
                                    </td>
                                </tr>

                            </table>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td align="right" valign="top" width="50%">
                        PDF :
                    </td>
                    <td align="left">
                        <input type="checkbox" id="pdf" name="pdf">
                    </td>
                </tr>

                <tr>
                    <td align="right" valign="top" width="50%">
                        Excel :
                    </td>
                    <td align="left">
                        <input type="checkbox" id="xls" name="xls">
                    </td>
                </tr>


                <tr>
                    <th>&nbsp;</th>
                </tr>

                <tr>
                    <td align="right" width="45%"><input type="button"  id="submitbtn" name="submit" value="Submit" onclick="javascript: submitView();"></td>
                    <td align="left">
                        <input type="reset"  id="resetbtn" name="reset" value="Reset" onclick="javascript: hideGridOptionsTable(); ">
                        <input name="back" value="Back" type="button" onclick="javascript: showSpecificationsTable();"/>
                    </td>
                </tr>
            </table>
        </form>
    </c:when>
</c:choose>