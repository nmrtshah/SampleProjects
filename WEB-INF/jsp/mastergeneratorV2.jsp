<%--
    Document   : mastergenerator
    Created on : Mar 4, 2011, 11:31:52 AM
    Author     : njuser
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:choose>
    <c:when test="${process eq 'menu'}">
        <html>
            <head>
                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                <link href="css/main.css" rel="stylesheet" type="text/css"/>
                <link rel="stylesheet" type="text/css" href="${finlibPath}/jquery/jquery-ui-1.8.10.custom/css/blitzer/jquery-ui-1.8.10.custom.css"/>
                <script type="text/javascript" src="${finlibPath}/jquery/jquery-ui-1.8.10.custom/js/jquery-1.4.4.min.js"></script>
                <script type="text/javascript" src="${finlibPath}/jquery/jquery-ui-1.8.10.custom/js/jquery-ui-1.8.10.custom.min.js"></script>
                <script type="text/javascript" src="${finlibPath}/resource/ajax.js"></script>
                <script type="text/javascript" src="javascript/common_validations.js"></script>
                <script type="text/javascript" src="javascript/validate.js"></script>
                <script type="text/javascript" src="javascript/ajaxfunctions.js"></script>
                <%--<script type="text/javascript" src="javascript/reportgenerator.js"></script>--%>
                <script type="text/javascript" src="javascript/mastergeneratorV2.js"></script>
                <%--<script type="text/javascript" src="javascript/mastergenerator.js"></script>--%>
                
                <title>Master Generator</title>
            </head>
            <body>
                <form id="menuForm" name ="menuForm" action="" method="post">
                    <br>
                    <div id ="menu">
                        <input type="hidden" name="userName" value="${ACLEmpCode}" >
                        <table border="0" id="menuTable" align="center" class="tbl_border1" width="52%">
                            <tr class="tbl_h1_bg">
                                <th colspan="100%">Master Generation Version 2.0</th>
                            </tr>
                            <tr>
                                <th colspan="100%">&nbsp;</th>
                            </tr>
                            <tr>
                                <td align="right" width="50%">Project Name:</td>
                                <td align="left"><input type="text" id="projectName" name="projectName" onblur="javascript: compare_string_rule_alpha(document.getElementById('projectName').value);"></td>
                            </tr>
                            <tr>
                                <td align="right" width="50%">Module Name:</td>
                                <td align="left"><input type="text" id="moduleName" name="moduleName" onblur=""></td>
                            </tr>
                            <tr>
                                <td align="right">Database Type:</td>
                                <td align="left">
                                    <select id="databaseType" name="databaseType">
                                        <option value="Oracle">Oracle</option>
                                        <option value="MYSQL">MYSQL</option>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td align="right" valign="top">Alias Name:</td>
                                <td align="left"><input id="aliasName" type="text" name="aliasName" onblur="javascript: checkAliasName(this.form);"><div id="alias"></div></td>
                            </tr>

                            <tr>
                                <td align="right">Master Table Name:</td>
                                <td align="left"><input id="masterTableName" type="text" name="masterTableName" onblur="javascript: checkMasterTableName();"></td>
                            </tr>
                            <tr>
                                <td align="right" valign="top">Select Master Table Name:</td>
                                <td align="left" id="selectMasterTableNameTd">
                                    <select name="selectMasterTableName" id="selectMasterTableName">
                                        <option value="Select Table">Select Table</option>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td align="right">Detail Table Name:</td>
                                <td align="left"><input id="detailTableName" type="text" name="detailTableName" onblur="javascript: checkDetailTableName();"></td>
                            </tr>
                            <tr>
                                <td align="right" valign="top">Select Detail Table Name:</td>
                                <td align="left" id="selectDetailTableNameTd">
                                    <select name="selectDetailTableName" id="selectDetailTableName">
                                        <option value="Select Table">Select Table</option>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="100%">&nbsp;</td>
                            </tr>
                            <tr>
                                <td align="center" colspan="100%" id="validation">
                                    <input type="button"  id="submitbtn" name="submit" value="Submit" onclick="javascript: submitMenu();">
                                    <input type="reset"  id="resetbtn" name="reset" value="Reset" onclick="">
                                </td>
                            </tr>
                        </table>
                    </div>
                    <div id ="specifications">

                    </div>
                    <div id ="generateMaster" align="center">

                    </div>
                </form>
            </body>
        </html>
    </c:when>
    <c:when test="${process eq 'showSpecification'}">
        <div id="specificationMasterTable">
            <table border="0" id="specificationsTable" align="center" class="tbl_border1" width="52%">
                <tr class="tbl_h1_bg">
                    <th colspan="100%">Master Menu Specifications</th>
                </tr>
                <tr>
                    <th colspan="100%">&nbsp;</th>
                </tr>
                <tr>
                    <td align="right" width="50%" style="font-size: 12px">Primarykey :</td>
                    <td align="left">
                        <select id="primarykeyMaster" name="primarykeyMaster">
                            <c:forEach var="column" items="${columnNamesMaster}">
                                <option value="${column}">${column}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td colspan="100%">&nbsp;</td>
                </tr>
                <c:forEach var="column" items="${columnNamesMaster}">
                    <tr>
                        <td align="right" valign="top" width="50%">
                            ${column} :
                        </td>
                        <td align="left">
                            <select id="inputControlMaster" name="inputControlMaster">
                                <option value="--Select Control--">--Select Control--</option>
                                <option value="Textbox">Textbox</option>
                                <option value="Checkbox">Checkbox</option>
                                <option value="Combobox">Combobox</option>
                                <option value="File">File</option>
                                <option value="Radio">Radio</option>
                            </select>
                        </td>
                    </tr>
                </c:forEach>
                <tr>
                    <td align="center" colspan="100%">
                        <input name="Next" id="Next" type="button" value="Next" onclick="javascript:showSpecificationDetailTable();">
                        <input name="reset" id="reset" type="reset" value="Reset">
                        <input name="back" id="back" type="button" value="Back" onclick="javascript:showMenu();">
                    </td>
                </tr>
            </table>
        </div>
        <div id="specificationDetailTable" style="display: none">
            <table border="0" id="specificationsTable" align="center" class="tbl_border1" width="52%">
                <tr class="tbl_h1_bg">
                    <th colspan="100%">Detail Menu Specifications</th>
                </tr>
                <tr>
                    <th colspan="100%">&nbsp;</th>
                </tr>
                <tr>
                    <td align="right" width="50%" style="font-size: 12px">Primarykey :</td>
                    <td align="left">
                        <select id="primarykeyDetail" name="primarykeyDetail">
                            <c:forEach var="column" items="${columnNamesDetail}">
                                <option value="${column}">${column}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td align="right" width="50%" style="font-size: 12px">Foreignkey :</td>
                    <td align="left">
                        <select id="foreignkeyDetail" name="foreignkeyDetail">
                            <c:forEach var="column" items="${columnNamesDetail}">
                                <option value="${column}">${column}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td colspan="100%">&nbsp;</td>
                </tr>
                <c:forEach var="column" items="${columnNamesDetail}">
                    <tr>
                        <td align="right" valign="top" width="50%">
                            ${column} :
                        </td>
                        <td align="left">
                            <select id="inputControlDetail" name="inputControlDetail">
                                <option value="--Select Control--">--Select Control--</option>
                                <option value="Textbox">Textbox</option>
                                <option value="Checkbox">Checkbox</option>
                                <option value="Combobox">Combobox</option>
                                <option value="File">File</option>
                                <option value="Radio">Radio</option>
                            </select>
                        </td>
                    </tr>
                </c:forEach>
                <tr>
                    <td align="center" colspan="100%">
                        <input name="submit" id="submit" type="button" value="Submit" onclick="javascript:submitSpecification();">
                        <input name="reset" id="reset" type="reset" value="Reset">
                        <input name="back" id="back" type="button" value="Back" onclick="javascript:showSpecificationMasterTable();">
                    </td>
                </tr>
            </table>
        </div>
    </c:when>
    <c:when test="${process eq 'getMasterTableNames'}">
        <c:if test="${masterTableNames eq null}">
            <select name="selectMasterTableName" id="selectMasterTableName">
                <option value="Select Table">Select Table</option>
            </select>
            <br>
            Invalid Alias Name or Database Type
        </c:if>
        <c:if test="${masterTableNames ne null}">
            <select name="selectMasterTableName" id="selectMasterTableName">
                <option value="Select Table">Select Table</option>
                <c:forEach var="column" items="${masterTableNames}">
                    <option value="${column.TABLE_NAME}">${column.TABLE_NAME}</option>
                </c:forEach>
            </select>
        </c:if>
    </c:when>
    <c:when test="${process eq 'getDetailTableNames'}">
        <c:if test="${detailTableNames eq null}">
            <select name="selectDetailTableName" id="selectDetailTableName">
                <option value="Select Table">Select Table</option>
            </select>
            <br>
            Invalid Alias Name or Database Type
        </c:if>
        <c:if test="${detailTableNames ne null}">
            <select name="selectDetailTableName" id="selectDetailTableName">
                <option value="Select Table">Select Table</option>
                <c:forEach var="column" items="${detailTableNames}">
                    <option value="${column.TABLE_NAME}">${column.TABLE_NAME}</option>
                </c:forEach>
            </select>
        </c:if>
    </c:when>
    <c:when test="${process eq 'validate'}">
        <c:if test="${validation eq 'InvalidAlias'}">

            Invalid Alias Name

        </c:if>
    </c:when>
    <c:when test="${process eq 'generateMasterV2'}">
        <c:if test="${moduleNo ne null}">
            Module Successfully Generated <br/>
            Module Number: ${moduleNo}<br/>
            <a href="generated/${moduleNo}.zip" >Download</a>
        </c:if>
        <br/>
        <input name="back" value="Back" type="button" onclick="javascript: showMenu();"/>
    </c:when>
</c:choose>