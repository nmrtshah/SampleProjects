<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <title>SubMenu JSP</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <link href="css/header.css" rel="stylesheet" type="text/css" />
    </head>

    <body>
        <br>
        <table align="left" border="0" cellpadding="2" cellspacing="2" width="160">
            <c:choose>
                <c:when test="${action eq 'monitoring'}">
                    <tr>
                        <td class="menuHead">
                            Monitoring
                        </td>
                    </tr>
                    <tr>
                        <td nowrap class="menuMst_td"><img alt="->" src="images/bullet_Link.gif">
                            <a href="connaudit.fin?cmdAction=connAuditMenu">
                                <span class="menu_link">Connection Audit Report</span>
                            </a>
                        </td>
                    </tr>
                    <tr>
                        <td nowrap class="menuMst_td"><img alt="->" src="images/bullet_Link.gif">
                            <a href="connaudit.fin?cmdAction=chartMainMenu">
                                <span class="menu_link">Connection Audit Report (Chart)</span>
                            </a>
                        </td>
                    </tr>
                    <tr>
                        <td nowrap class="menuMst_td"><img alt="->" src="images/bullet_Link.gif">
                            <a href="serverAccessAuditReport.fin?cmdAction=getMenu">
                                <span class="menu_link">Server Access Audit Report</span>
                            </a>
                        </td>
                    </tr>
                    <tr>
                        <td nowrap class="menuMst_td"><img alt="->" src="images/bullet_Link.gif">
                            <a href="aliasconnpool.fin?cmdAction=getAlias">
                                <span class="menu_link">Connection Alias Report</span>
                            </a>
                        </td>
                    </tr>
                    <tr>
                        <td nowrap class="menuMst_td"><img alt="->" src="images/bullet_Link.gif">
                            <a href="aliasconnpool.fin?cmdAction=getConnPool">
                                <span class="menu_link">Connection Pool Report</span>
                            </a>
                        </td>
                    </tr>
                    <tr>
                        <td nowrap class="menuMst_td"><img alt="->" src="images/bullet_Link.gif">
                            <a href="jsp/repositoryList.jsp">
                                <span class="menu_link">Repository List</span>
                            </a>
                        </td>
                    </tr>

                    <tr>
                        <td nowrap class="menuMst_td"><img alt="->" src="images/bullet_Link.gif">
                            <a href="jsp/serviceList.jsp">
                                <span class="menu_link">Service List</span>
                            </a>
                        </td>
                    </tr>
                    <tr>
                        <td nowrap class="menuMst_td"><img alt="->" src="images/bullet_Link.gif">
                            <a href="finstudiomur.fin?cmdAction=getMenu">
                                <span class="menu_link">Finstudio Module Usage Report</span>
                            </a>
                        </td>
                    </tr>
                </c:when>

                <c:when test="${action eq 'development'}">
                    <tr>
                        <td class="menuHead">
                            Development
                        </td>
                    </tr>
                    <tr>
                        <td nowrap class="menuMst_td"><img alt="->" src="images/bullet_Link.gif">
                            <a href="repgenv2.fin?cmdAction=setFinReport">
                                <span class="menu_link">Report Generation Utility V2</span>
                            </a>
                        </td>
                    </tr>
                    <tr>
                        <td nowrap class="menuMst_td"><img alt="->" src="images/bullet_Link.gif">
                            <a href="mstgenv2.fin">
                                <span class="menu_link">Master Generation Utility V2</span>
                            </a>
                        </td>
                    </tr>
                    <tr>
                        <td nowrap class="menuMst_td"><img alt="->" src="images/bullet_Link.gif">
                            <a href="dataimport.fin?cmdAction=selectFile">
                                <span class="menu_link">Data Import Utility</span>
                            </a>
                        </td>
                    </tr>
                    <tr>
                        <td nowrap class="menuMst_td"><img alt="->" src="images/bullet_Link.gif">
                            <a href="dataexport.fin?cmdAction=selectTable">
                                <span class="menu_link">Data Export Utility</span>
                            </a>
                        </td>
                    </tr>
                    <tr>
                        <td nowrap class="menuMst_td"><img alt="->" src="images/bullet_Link.gif">
                            <a href="mobapps.fin">
                                <span class="menu_link">MobileApps Generation</span>
                            </a>
                        </td>
                    </tr>
                    <tr>
                        <td nowrap class="menuMst_td"><img alt="->" src="images/bullet_Link.gif">
                            <a href="webservicegen.fin">
                                <span class="menu_link">Web Service Generation Utility</span>
                            </a>
                        </td>
                    </tr>
                </c:when>

                <c:when test="${action eq 'autotestingtool'}">
                    <tr>
                        <td class="menuHead">
                            Auto Testing Tool
                        </td>
                    </tr>
                    <tr>
                        <td nowrap class="menuMst_td"><img alt="->" src="images/bullet_Link.gif">
                            <a href="linkchecker.fin">
                                <span class="menu_link">Link Checker</span>
                            </a>
                        </td>
                    </tr>
                    <tr>
                        <td nowrap class="menuMst_td"><img alt="->" src="images/bullet_Link.gif">
                            <a href="scriptgen.fin?cmdAction=showRequest">
                                <span class="menu_link">Script Generator</span>
                            </a>
                        </td>
                    </tr>
                </c:when>

                <c:when test="${action eq 'demos'}">
                    <tr>
                        <td class="menuHead">
                            JQuery
                        </td>
                    </tr>
                    <tr>
                        <td nowrap class="menuMst_td"><img alt="->" src="images/bullet_Link.gif">
                            <a href="${finlibPath}/jquery/jquery-ui-1.8.10.custom/index.html">
                                <span class="menu_link">JQuery UI</span>
                            </a>
                        </td>
                    </tr>
                    <tr>
                        <td nowrap class="menuMst_td"><img alt="->" src="images/bullet_Link.gif">
                            <a href="jquerydemo.fin?cmdAction=autoComplete">
                                <span class="menu_link">JQuery UI - Auto Complete</span>
                            </a>
                        </td>
                    </tr>
                </c:when>
            </c:choose>
        </table>
    </body>
</html>
