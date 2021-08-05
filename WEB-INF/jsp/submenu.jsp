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
                        <td class="menuHead">
                            Useful Links
                        </td>
                    </tr>
                    <tr>
                        <td nowrap class="menuMst_td"><img alt="->" src="images/bullet_Link.gif">
                            <a href="connaudit.fin?cmdAction=connAuditMenu">
                                <span class="menu_link">Connection Audit Report</span>
                            </a>
                        </td>
                        <td nowrap class="menuMst_td"><img alt="->" src="images/bullet_Link.gif">
                            <a href="http://prodhoweb1.nj:8090/log/log/cron-list.txt" target="_blank">
                                <span class="menu_link">Cronjob List on HOWEB1</span>
                            </a>
                        </td>
                    </tr>
                    <tr>
                        <td nowrap class="menuMst_td"><img alt="->" src="images/bullet_Link.gif">
                            <a href="connaudit.fin?cmdAction=chartMainMenu">
                                <span class="menu_link">Connection Audit Report (Chart)</span>
                            </a>
                        </td>
                        <td nowrap class="menuMst_td"><img alt="->" src="images/bullet_Link.gif">
                            <a href="http://prodhoweb2.nj:8090/log/log/cron-list.txt" target="_blank">
                                <span class="menu_link">Cronjob List on HOWEB2</span>
                            </a>
                        </td>
                    </tr>
                    <tr>
                        <td nowrap class="menuMst_td"><img alt="->" src="images/bullet_Link.gif">
                            <a href="serverAccessAuditReport.fin?cmdAction=getMenu">
                                <span class="menu_link">Server Access Audit Report</span>
                            </a>
                        </td>
                        <td nowrap class="menuMst_td"><img alt="->" src="images/bullet_Link.gif">
                            <a href="http://prodhoweb3.nj:8090/log/log/cron-list.txt" target="_blank">
                                <span class="menu_link">Cronjob List on HOWEB3</span>
                            </a>
                        </td>
                    </tr>
                    <tr>
                        <td nowrap class="menuMst_td"><img alt="->" src="images/bullet_Link.gif">
                            <a href="aliasconnpool.fin?cmdAction=getAlias">
                                <span class="menu_link">Connection Alias Report</span>
                            </a>
                        </td>
                        <td nowrap class="menuMst_td"><img alt="->" src="images/bullet_Link.gif">
                            <a href="http://prodhoweb1rep.nj:8090/log/log/cron-list.txt" target="_blank">
                                <span class="menu_link">Cronjob List on HOWEB1REP</span>
                            </a>
                        </td>
                    </tr>
                    <tr>
                        <td nowrap class="menuMst_td"><img alt="->" src="images/bullet_Link.gif">
                            <a href="aliasconnpool.fin?cmdAction=getConnPool">
                                <span class="menu_link">Connection Pool Report</span>
                            </a>
                        </td>
                        <td nowrap class="menuMst_td"><img alt="->" src="images/bullet_Link.gif">
                            <a href="http://prodhoweb2rep.nj:8090/log/log/cron-list.txt" target="_blank">
                                <span class="menu_link">Cronjob List on HOWEB2REP</span>
                            </a>
                        </td>
                    </tr>
                    <tr>
                        <td nowrap class="menuMst_td"><img alt="->" src="images/bullet_Link.gif">
                            <a href="jsp/serviceList.jsp">
                                <span class="menu_link">Service List</span>
                            </a>
                        </td>
                        <td nowrap class="menuMst_td"><img alt="->" src="images/bullet_Link.gif">
                            <a href="http://prodhoweb3rep.nj:8090/log/log/cron-list.txt" target="_blank">
                                <span class="menu_link">Cronjob List on HOWEB3REP</span>
                            </a>
                        </td>
                    </tr>
                    <tr>
                        <td nowrap class="menuMst_td"><img alt="->" src="images/bullet_Link.gif">
                            <a href="finstudiomur.fin?cmdAction=getMenu">
                                <span class="menu_link">Finstudio Module Usage Report</span>
                            </a>
                        </td>
                        <td nowrap class="menuMst_td"><img alt="->" src="images/bullet_Link.gif">
                            <a href="./documents/rnd/fincodemerge.zip" target="_blank">
                                <span class="menu_link">Fin Code Merge</span>
                            </a>
                        </td>
                    </tr>
                    <tr>
                        <td nowrap class="menuMst_td"><img alt="->" src="images/bullet_Link.gif">
                            <a href="configaudit.do">
                                <span class="menu_link">Configuration Audit Report</span>
                            </a>
                        </td>
                        <td nowrap class="menuMst_td"><img alt="->" src="images/bullet_Link.gif">
                            <a href="./documents/rnd/JSBeautify.zip" target="_blank">
                                <span class="menu_link">JSBeautify</span>
                            </a>
                        </td>
                    </tr>
                    <tr>
                        <td nowrap class='menuMst_td'><img alt="->" src='images/bullet_Link.gif'>
                            <a href="dbmdi.fin?cmdAction=viewPage">
                                <font class='menu_link'>Database Metadata Inspector</font>
                            </a>
                        </td>
                        <td nowrap class="menuMst_td"><img alt="->" src="images/bullet_Link.gif">
                            <a href="mysqlsesmonitoring.fin" target="_blank">
                                <span class="menu_link">MySQL Session Monitoring</span>
                            </a>
                        </td>
                    </tr>
                    <tr>
                        <td nowrap class='menuMst_td'><img alt="->" src='images/bullet_Link.gif'>
                            <a href="dbmc.fin?cmdAction=viewPage">
                                <font class='menu_link'>Database Metadata Comparator</font>
                            </a>
                        </td>

                        <td nowrap class="menuMst_td"><img alt="->" src="images/bullet_Link.gif">
                            <a href="etljoblist.fin?cmdAction=getMenu&page=report">
                                <span class="menu_link">ETL Job List</span>
                            </a>
                        </td>
                    </tr>
                    <tr>
                        <td nowrap class='menuMst_td'><img alt="->" src='images/bullet_Link.gif'>
                            <a href="finstudiostatistics.fin?cmdAction=getMenu">
                                <font class='menu_link'>FinStudio Statistics Report</font>
                            </a>
                        </td>
                        <td nowrap class='menuMst_td'><img alt="->" src='images/bullet_Link.gif'>
                            <a href="tinyurl.fin?cmdAction=inputTinyUrl">
                                <font class='menu_link'>Generate Tiny URL</font>
                            </a>
                        </td>
                    </tr>
                    <tr>
                        <td nowrap class='menuMst_td'><img alt="->" src='images/bullet_Link.gif'>
                            <a href="dbobjversionrpt.fin?cmdAction=viewPage">
                                <font class='menu_link'>Database Object Version Report</font>
                            </a>
                        </td>
                    </tr>
                    <tr>
                        <td nowrap class='menuMst_td'><img alt="->" src='images/bullet_Link.gif'>
                            <a href="dbusergrants.fin?cmdAction=dbUserGrantsLoader">
                                <font class='menu_link'>Database User Grants</font>
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
                            <a href="repgenv2.fin">
                                <span class="menu_link">Report Generation Utility V2 - <i>JqGrid</i></span>
                            </a>
                        </td>
                    </tr>
                    <tr>
                        <td nowrap class='menuMst_td'><img alt="->" src='images/bullet_Link.gif'>
                            <a href="repgenv3.fin?cmdAction=setFinReport">
                                <font class='menu_link'>Report Generation Utility V3 - <i>DhtmlxGrid</i></font>
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
                            <a href="mstgenv3.fin">
                                <span class="menu_link">Master Generation Utility V3 - <i>DhtmlxGrid</i></span>
                            </a>
                        </td>
                    </tr>
                    <tr>
                        <td nowrap class="menuMst_td"><img alt="->" src="images/bullet_Link.gif">
                            <a href="codegen.fin?cmdAction=codeGen">
                                <span class="menu_link">Package Generator</span>
                            </a>
                        </td>
                    </tr>
                    <tr>
                        <td nowrap class="menuMst_td"><img alt="->" src="images/bullet_Link.gif">
                            <a href="codedigger.fin?cmdAction=codeDigger">
                                <span class="menu_link">Annotation Converter</span>
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
                    <!--                    <tr>
                                            <td nowrap class="menuMst_td"><img alt="->" src="images/bullet_Link.gif">
                                                <a href="mobapps.fin">
                                                    <span class="menu_link">MobileApps Generation</span>
                                                </a>
                                            </td>
                                        </tr>-->
                    <tr>
                        <td nowrap class="menuMst_td"><img alt="->" src="images/bullet_Link.gif">
                            <a href="webservicegen.fin">
                                <span class="menu_link">Web Service Generation Utility</span>
                            </a>
                        </td>
                    </tr>
                    <tr>
                        <td nowrap class='menuMst_td'><img alt="->" src='images/bullet_Link.gif'>
                            <a href="oracleToMySql.fin?cmdAction=getMenu">
                                <span class='menu_link'>Oracle To Mysql</span>
                            </a>
                        </td>
                    </tr>
                    <tr>
                        <td nowrap class='menuMst_td'><img alt="->" src='images/bullet_Link.gif'>
                            <a href="add_finfiletransfer.fin?cmdAction=getMenu">
                                <span class='menu_link'>Fin File Deployment</span>
                            </a>
                        </td>
                    </tr>
                    <tr>
                        <td nowrap class='menuMst_td'><img alt="->" src='images/bullet_Link.gif'>
                            <a href="findatareqexec.fin">
                                <span class='menu_link'>Data Request Executor</span>
                            </a>
                        </td>
                    </tr>
                    <tr>
                        <td nowrap class="menuMst_td"><img alt="->" src="images/bullet_Link.gif">
                            <a href="filedirar.fin?cmdAction=getMainPage">
                                <span class="menu_link">File Dir Auto Request</span>
                            </a>
                        </td>
                    </tr>
                    <tr>
                        <td nowrap class='menuMst_td'><img alt="->" src='images/bullet_Link.gif'>
                            <a href="blockmodules.fin?cmdAction=showMenu">
                                <span class='menu_link'>Auto Block / Unblock</span>
                            </a>
                        </td>
                    </tr>
                    <tr>
                        <td nowrap class="menuMst_td"><img alt="->" src="images/bullet_Link.gif">
                            <a href="generatepdfmodule.fin?cmdAction=getClientPage">
                                <span class="menu_link">Certificate Generation</span>
                            </a>
                        </td>
                    </tr>
                    <tr>
                        <td nowrap class="menuMst_td"><img alt="->" src="images/bullet_Link.gif">
                            <a href="webservicedoc.fin?cmdAction=getMainPage">
                                <span class="menu_link">Auto Web Service Generator</span>
                            </a>
                        </td>
                    </tr>
                    <tr>
                        <td nowrap class='menuMst_td'><img alt="->" src='images/bullet_Link.gif'>
                            <a href="dbmc.fin?cmdAction=angularDemo">
                                <span class='menu_link'>AngularJS Demo</span>
                            </a>
                        </td>
                    </tr>
                    <tr>
                        <td nowrap class='menuMst_td'><img alt="->" src='images/bullet_Link.gif'>
                            <a href="etljobmaster.fin?cmdAction=getMenu&page=master">
                                <span class='menu_link'>ETL Job master</span>
                            </a>
                        </td>
                    </tr>

                    <tr>
                        <td nowrap class='menuMst_td'><img alt="->" src='images/bullet_Link.gif'>
                            <a href="longrunningrequest.fin?cmdAction=showMenu">
                                <span class='menu_link'>Long Running Request</span>
                            </a>
                        </td>
                    </tr>
                    
                    <tr>
                        <td nowrap class="menuMst_td"><img alt="->" src="images/bullet_Link.gif">
                            <a href="automatedsvn.fin?cmdAction=getMainPage">
                                <span class='menu_link'>SVN Auto Creator</span>
                            </a>
                        </td>
                    </tr>
                    <!--DB MAP REMAP -->
                    <tr>
                        <td nowrap class="menuMst_td"><img alt="->" src="images/bullet_Link.gif">
                            <a href="dbmapremap.fin">
                                <span class='menu_link'>Database Map-Remap</span>
                            </a>
                        </td>
                    </tr>
                    <!-- Encryption Utility -->
                    <tr>
                        <td nowrap class="menuMst_td"><img alt="->" src="images/bullet_Link.gif">
                            <a href="encdec.fin">
                                <span class='menu_link'>Encryption Utility</span>
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