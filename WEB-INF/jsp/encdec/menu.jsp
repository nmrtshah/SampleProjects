<%-- 
    Document   : menu
    Created on : 09-Sep-2019, 12:03:25 PM
    Author     : Jigna Patel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<c:choose>
    <c:when test="${view eq 'encOutput'}">
        ${result}
    </c:when>
    <c:otherwise>
        <html>
            <head>
                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                <title>Encryption Utility</title>
                <link rel="stylesheet" type="text/css" href="${finlib_path}/resource/main_offline.css">
                
                <script type="text/javascript" src="${finlib_path}/jquery/jquery-1.6.1/jquery-1.6.1.min.js"></script>
                <script type="text/javascript" src="${finlib_path}/resource/validate.js"></script>
                <script type="text/javascript" src="${finlib_path}/resource/ajax.js"></script>
                <script type="text/javascript" src="javascript/encdec.js"></script>
            </head>
            <body>
                <div class="container">
                    <div class="content" id="menuLoader">
                        <div class="menu_new">
                            <div class="menu_caption_bg cursor-pointer" onclick="javascript:hide_menu('show_hide', 'load', 'nav_show', 'nav_hide')">
                                <div class="menu_caption_text">Encryption Utility</div>
                                <span><a href="javascript:void(0)" name="show_hide" id="show_hide" class="nav_hide"></a></span>
                            </div>
                            
                            <form name="encUtilForm" id="encUtilForm" method="post" action="">
                                <div class="collapsible_menu_tab fullwidth">
                                    <ul id="mainTab1">
                                        <ul id="mainTab1">
                                            <li class="" id="mainTab_1" onclick="selectTab(this.id);">
                                                <a rel="rel0" href="javascript:void(0)" onclick="javascript:viewLoader();">View</a>
                                            </li>
                                        </ul>
                                    </ul>
                                </div>
                                <div id="load" align="center" class="report_content">
                                    <div id="resultMsg" style="display: none;"></div>
                                    <div id="contentView" style="display: block;">
                                        <div class="report_content" id="viewModuleDiv">
                                            <table align="center" border="0" cellpadding="0" cellspacing="2" class="menu_subcaption" width="100%">
                                                <tr>
                                                    <td align="right" class="report_content_caption">
                                                        Server Name <span class="astriek">*</span>:
                                                    </td>
                                                    <td class="report_content_value">
                                                        <select class="custom_combo_showSSWT " id="serverName" name="serverName">
                                                            <option value="-1">--Select Server Name--</option>
                                                            <c:forEach items="${serverList}" var="sl">
                                                                <option value="${sl}">${sl}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td align="right" class="report_content_caption">
                                                        Plain Text <span class="astriek">*</span>:
                                                    </td>
                                                    <td class="report_content_value">
                                                        <input type="text" name="plainText" id="plainText"/>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td align="center" colspan="2">
                                                        <input class="button" type="button" id="btnSubmit" name="btnSubmit" value="Submit" onclick="javascript: encrypt();" />
                                                        <input class="button" type="button" id="btnReset" name="btnReset" value="Reset" onclick="javascript: resetAll();" />
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td align="right" class="report_content_caption">
                                                        Enc. Text <span class="astriek">*</span>:
                                                    </td>
                                                    <td class="report_content_value">
                                                        <textarea rows="3" cols="2" name="encText" id="encText" style="resize:none;"></textarea>
                                                    </td>
                                                </tr>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </body>
        </html>
    </c:otherwise>
</c:choose>