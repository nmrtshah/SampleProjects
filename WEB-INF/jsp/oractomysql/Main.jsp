<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:choose>    
    <c:when test="${process eq 'query'}">        
        <textarea name="query" style="width: 493px;height:154px" id="query" cols="150" rows="20">${itemText}</textarea>
    </c:when>
    <c:when test="${process eq 'main'}">
        <%-- 
            Document   : Main
            Created on : 30 Jan, 2013, 3:14:45 PM
            Author     : njuser
        --%>
        <!DOCTYPE html>
        <html>
            <head>
                <title>JSP Page</title>                                
                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
                <link rel="stylesheet" type="text/css" href="${finlib_path}/resource/main_offline.css" />
                <link rel="stylesheet" type="text/css" href="${finlib_path}/jquery/jquery-ui-1.8.13.custom/css/smoothness/jquery-ui-1.8.13.custom.css" />
                <link rel="stylesheet" type="text/css" href="${finlib_path}/jquery/jquery.jqGrid-3.8.2/css/ui.jqgrid.css" />
                <script type="text/javascript" src="${finlib_path}/jquery/jquery-1.6.1/jquery-1.6.1.min.js"></script>
                <script type="text/javascript" src="${finlib_path}/resource/common_functions.min.js"></script>
                <script type="text/javascript" src="${finlib_path}/jquery/jquery-ui-1.8.13.custom/js/jquery-ui-1.8.13.custom.min.js"></script>
                <script type="text/javascript" src="${finlib_path}/jquery/jquery.jqGrid-3.8.2/js/i18n/grid.locale-en.js"></script>
                <script type="text/javascript" src="${finlib_path}/jquery/jquery.jqGrid-3.8.2/js/jquery.jqGrid.min.js"></script>
                <script type="text/javascript" src="${finlib_path}/resource/validate.js"></script>
                <script type="text/javascript" src="${finlib_path}/resource/validate_date.js"></script>
                <script type="text/javascript" src="${finlib_path}/jquery/jquery-ui-1.8.13.custom/development-bundle/ui/jquery.ui.datepicker.js"></script>
                <script type="text/javascript" src="${finlib_path}/resource/serverCombo.js"></script>
                <script type="text/javascript" src="${finlib_path}/resource/ajax.js"></script>
                <script type="text/javascript" src="${finlib_path}/resource/finstudio-utility-functions.js"></script>
                <script type="text/javascript" src="javascript/OracleToMysqlConverter.js"></script>
            </head>
            <body>                
                <form id="mainform" name="mainform" method="post">
                    <br>
                    <table align="center" style="width: 100%">
                        <tr>
                            <td width="40%" align="right">
                                <input type="radio" id="rdoConn" name="rdoGetSourceType" value="usingConn" onchange="javascript:showHide();"/>
                                : Using Development Connection

                            </td>                                                        
                            <td width="60%">
                                <input type="radio" id="rdoBlock" name="rdoGetSourceType" value="usingBlock" onchange="javascript:showHide();"/>
                                : Using PL/SQL Block                                
                            </td>                                
                        </tr>
                        <tbody id="usingConn" style="display: none">
                            <tr>
                                <td  align="right"><sup style="color: red">*</sup>Select Server : </td>
                                <td>
                                    <select id="server" name="server" onchange="javascript: getSchema();">
                                        <option value="-1" selected="true">-- Select Server --</option>
                                        <option value="dev_brok">Brok</option>
                                        <option value="dev_tran">Tran</option>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td  align="right"><sup style="color: red">*</sup>Select Schema : </td>
                                <td>
                                    <select id="schema" name="schema" onchange="javascript:getItemList();">
                                        <option value="-1" selected="true">-- Select Schema --</option>
                                    </select>
                                </td>                                
                            </tr>
                            <tr>
                                <td  align="right"><sup style="color: red">*</sup>Select Item : </td>
                                <td>
                                    <select id="item" name="item" onchange="javascript:getItemList();">
                                        <option value="-1" selected="true">-- Select Item --</option>
                                        <option value="PROCEDURE">Procedure</option>
                                        <option value="FUNCTION">Function</option>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td  align="right"><sup style="color: red">*</sup>Select Item Name : </td>
                                <td id="itemId">
                                    <select id="itemNmCmb" name="itemNmCmb">
                                        <option value="-1">-- Select Item Name --</option>
                                    </select>
                                </td>
                            </tr>                            
                            <tr>
                                <td colspan="2" align="center">
                                    <input type="button" value="Submit" class="button" onclick="javascript: getQuery();"/>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2" align="center">
                                    <div id="queryUsingConn">
                                        <textarea id="txtaUsingConn" cols="150" rows="20" style="width: 493px;height:154px"></textarea>
                                    </div>                                       
                                </td>
                            </tr>
                        </tbody>
                        <tbody id="usingBlock"  style="display: none">
                            <tr>
                                <td colspan="2" align="center" style="color:#6F6F6F">
                                    Each query should be terminated by a semi-colon <span class="astriek"> ; </span>
                                </td>
                            </tr>
                            <tr>                                
                                <td colspan="2" align="center">                                    
                                    <textarea style="width: 493px;height:154px" rows="20" cols="150" name="txtaOraQuery" id="txtaOraQuery"></textarea>                                    
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2" align="center">
                                    <input type="button" value="Get Mysql Query" class="button" onclick="javascript: getQuery();"/>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2" align="center">
                                    <div id="queryUsingBlock">
                                        <textarea id="txtaUsingBlock" cols="150" rows="20" style="width: 493px;height:154px"></textarea>
                                    </div> 
                                </td>
                            </tr>
                        </tbody>
                        <tbody id="tbformartCode" style="display: none">
                            <tr>
                                <td colspan="2" align="center">
                                    For PL/SQL Code Formatting, Use any of below links :<br>
                                    <ul>
                                        <li>
                                            <a href="http://192.168.71.81:8080/sqlformatter/test.jsp" target="_blank">SQL Formatter Link 1</a>
                                        </li>
                                        <li>
                                            <a href="http://192.168.71.122:8080/sqlformatter/test.jsp" target="_blank">SQL Formatter Link 2</a>
                                        </li>
                                        <li>
                                            <a href="http://192.168.71.123:8080/sqlformatter/test.jsp" target="_blank">SQL Formatter Link 3</a>
                                        </li>
                                    </ul>                                 
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </form>
            </body>
        </html>
    </c:when>   
    <c:when test="${process eq 'item'}">
        <select id="itemNmCmb" name="itemNmCmb">
            <option value="-1" selected="true">-- Select Item Name --</option>
            <c:forEach items="${itemNm}" var="itemNm">
                <option value="${itemNm.OBJECT_NAME}">${itemNm.OBJECT_NAME}</option>
            </c:forEach>
        </select>
    </c:when>
</c:choose>