<%--
    Document   : masterjenerator
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
                <script type="text/javascript" src="javascript/mastergenerator.js"></script>
                <script type="text/javascript" src="javascript/validate.js"></script>
                <script type="text/javascript" src="javascript/ajaxfunctions.js"></script>
                <title>Master Generator</title>
            </head>
            <body>
                <form id="menuForm" name ="menuForm" action="" method="post">
                    <br>
                    <div id ="menu">
                        <input type="hidden" name="userName" value="${ACLEmpCode}" >
                        <table border="0" id="menuTable" align="center" class="tbl_border1" width="52%">
                            <tr class="tbl_h1_bg">
                                <th colspan="100%">Master Generation</th>
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
                                <td align="right">Table Name:</td>
                                <td align="left"><input id="tableName" type="text" name="tableName" onblur="javascript: checkTableName();"></td>
                            </tr>
                            <tr>
                            <td align="right" valign="top">Select Table Name:</td>
                            <td align="left" id="selectTableNameTd">
                                <select name="selectTableName" id="selectTableName">
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
    <c:when test="${process eq 'specifications'}">
        <table border="0" id="specificationsTable" align="center" class="tbl_border1" width="52%">
            <tr class="tbl_h1_bg">
                <th colspan="100%">Menu Specifications</th>
            </tr>
            <tr>
                <th colspan="100%">&nbsp;</th>
            </tr>
            <tr>
                <td align="right" width="50%" style="font-size: 12px">Primarykey :</td>
                <td align="left">
                    <select id="primarykey" name="primarykey">
                        <c:forEach var="column" items="${columnNames}">
                            <option value="${column}">${column}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td colspan="100%">&nbsp;</td>
            </tr>
            <c:forEach var="column" items="${columnNames}">
                <tr>
                    <td align="right" valign="top" width="50%">
                        ${column} :
                    </td>
                    <td align="left">
                        <select id="inputControl" name="inputControl">
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
                    <input name="Submit" id="Submit" type="button" value="Submit" onclick="javascript:submitSpecifications();">
                    <input name="reset" id="reset" type="reset" value="Reset">
                    <input name="back" id="back" type="button" value="Back" onclick="javascript:showMenu();">
                </td>
            </tr>
        </table>
    </c:when>
    <c:when test="${process eq 'validate'}">
        <c:if test="${validation eq 'InvalidAlias'}">
            <%                        //Thread.sleep(10000);
%>
            Invalid Alias Name

        </c:if>
    </c:when>
    <c:when test="${process eq 'getTableNames'}">
        <c:if test="${tableNames eq null}">
            <select name="selectTableName" id="selectTableName">
                <option value="Select Table">Select Table</option>
            </select>
            <br>
            Invalid Alias Name or Database Type
        </c:if>
        <c:if test="${tableNames ne null}">
            <select name="selectTableName" id="selectTableName">
                <option value="Select Table">Select Table</option>
                <c:forEach var="column" items="${tableNames}">
                    <option value="${column.TABLE_NAME}">${column.TABLE_NAME}</option>
                </c:forEach>
            </select>
        </c:if>
    </c:when>
    <c:when test="${process eq 'generateMaster'}">
        <c:if test="${moduleNo ne null}">
            Module Successfully Generated <br/>
            Module Number: ${moduleNo}<br/>
            <a href="generated/${moduleNo}.zip" >Download</a>
        </c:if>
        <br/>
        <input name="back" value="Back" type="button" onclick="javascript: showMenu();"/>
    </c:when>
</c:choose>
