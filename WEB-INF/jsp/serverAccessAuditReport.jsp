<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.io.*" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:choose>
    <c:when test="${process eq 'getreportGrid'}">
        ${json}
    </c:when>
    <c:when test="${process eq 'getmenu'}">
        <html>
            <head>
                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                <title>Welcome to Spring Web MVC project</title>
                <link rel="stylesheet" type="text/css" href="${finlibPath}/jquery/jquery.jqGrid-3.8.2/css/ui.jqgrid.css">
                <link rel="stylesheet" type="text/css" href="${finlibPath}/jquery/jquery-ui-1.8.10.custom/development-bundle/themes/blitzer/jquery.ui.all.css"  />
                <link href="css/main.css" rel="stylesheet" type="text/css"/>
                <script type="text/javascript" src="${finlibPath}/jquery/jquery-ui-1.8.10.custom/development-bundle/jquery-1.4.4.js"></script>
                <script type="text/javascript" src="${finlibPath}/jquery/jquery-ui-1.8.10.custom/development-bundle/ui/jquery.ui.core.js"></script>
                <script type="text/javascript" src="${finlibPath}/jquery/jquery-ui-1.8.10.custom/development-bundle/ui/jquery.ui.datepicker.js"></script>
                <script type="text/javascript" src="${finlibPath}/jquery/jquery.jqGrid-3.8.2/js/i18n/grid.locale-en.js"></script>
                <script type="text/javascript" src="${finlibPath}/jquery/jquery.jqGrid-3.8.2/js/jquery.jqGrid.min.js"></script>
                <script type="text/javascript" src="${finlibPath}/jquery/jquery.jqGrid-3.8.2/js/jquery.jqGrid.min.js"></script>
                <script type="text/javascript" src="${finlibPath}/jquery/jquery.jqGrid-3.8.2/src/grid.celledit.js"></script>
                <script type="text/javascript" src="javascript/ajaxfunctions.js"></script>
                <script type="text/javascript">

                    function showRequestURLWiseReport()
                    {
                        $('#list2').GridUnload();
                        var params=getalldata(document.viewform);
                        jQuery("#list2").jqGrid({
                            url:'serverAccessAuditReport.fin?cmdAction=getRequestURLWiseReport&'+params,
                            datatype: "json",
                            colNames:['SERVER', 'SERVER PATH', 'PROJECT', 'REQUEST URL', 'HTTP STATUS', 'TOTAL REQ', 'TOT BYTES SENT', 'TOT EXE TIME', ],
                            colModel:[
                                {name:'SERVER_NAME',index:'SERVER_NAME', width:70},
                                {name:'SERVER_PATH',index:'SERVER_PATH', width:120},
                                {name:'PROJECT_NAME',index:'PROJECT_NAME', width:70},
                                {name:'REQUEST_URL',index:'REQUEST_URL', width:250},
                                {name:'HTTPSTATUS',index:'HTTPSTATUS', align:"center",width:100,sorttype:'int'},
                                {name:'TOTAL_REQUEST',index:'TOTAL_REQUEST',  align:"center", width:100,sorttype:'int',summaryType:'sum', summaryTpl : '({0})'},
                                {name:'TOT_BYTES_SENT',index:'TOT_BYTES_SENT',  align:"right", width:150,sorttype:'int'},
                                {name:'TOT_EXECUTION_TIME',index:'TOT_EXECUTION_TIME', align:"right", width:100, formatter:'number', formatoptions:{decimalPlaces: 4},sorttype:'int'},
                            ],
                            rowNum: 1000,
                            height:'300',
                            sortname: 'TOTAL_REQUEST',
                            viewrecords: true,
                            loadonce:true,
                            sortorder: "desc",
                            caption:"Server Access Report - (Page Summary) Page and Http Stauts Wise Total Request,Total Bytes Sent & Totat Execution Time",
                            grouping: true,
                            groupingView : {
                                groupField : ['PROJECT_NAME'],
                                groupSummary : [true],
                                groupColumnShow : [true],
                                groupText : ['{0}'],
                                groupCollapse : false,
                                groupOrder: ['asc']
                            },
                            footerrow: true,
                            userDataOnFooter: true
                        });
                    }
                    function showProjectWiseReport()
                    {
                        $('#list2').GridUnload();
                        var params=getalldata(document.viewform);
                        jQuery("#list2").jqGrid({
                            url:'serverAccessAuditReport.fin?cmdAction=getProjectWiseReport&'+params,
                            datatype: "json",
                            colNames:['SERVER', 'SERVER_PATH', 'PROJECT', 'HTTP STATUS', 'TOTAL REQUEST', 'TOT BYTES SENT', 'TOT EXECUTION TIME', ],
                            colModel:[
                                {name:'SERVER_NAME',index:'SERVER_NAME', width:150},
                                {name:'SERVER_PATH',index:'SERVER_PATH', width:150},
                                {name:'PROJECT_NAME',index:'PROJECT_NAME', width:150},
                                {name:'HTTPSTATUS',index:'HTTPSTATUS', width:150,align:"center",sorttype:'int'},
                                {name:'TOTAL_REQUEST',index:'TOTAL_REQUEST', width:150,align:"right",sorttype:'int'},
                                {name:'TOT_BYTES_SENT',index:'TOT_BYTES_SENT', width:150,align:"right",sorttype:'int'},
                                {name:'TOT_EXECUTION_TIME',index:'TOT_EXECUTION_TIME', width:150,align:"right",sorttype:'int'},
                            ],
                            rowNum: 1000,
                            height: '300',
                            sortname: 'TOTAL_REQUEST',
                            viewrecords: true,
                            loadonce:true,
                            sortorder: "desc",
                            caption:"Server Access Report - (Project Summary) Project and Http Status Wise Total Request,Bytes Sent and Execution Time",
                            grouping: false,
                            footerrow: true,
                            userDataOnFooter: true
                        });
                    }
                    function showRequestURLReport()
                    {
                        $('#list2').GridUnload();
                        var params=getalldata(document.viewform);
                        jQuery("#list2").jqGrid({
                            url:'serverAccessAuditReport.fin?cmdAction=getRequestURLReport&'+params,
                            datatype: "json",
                            colNames:['SERVER', 'SERVER', 'IPADDRESS', 'REQUEST_TIME', 'PROJECT', 'REQUEST_URL', 'EXEC.TIME.sec', 'EXEC.TIME.min', 'BYTES_SENT', 'KB_SENT', 'MB_SENT', ],
                            colModel:[
                                {name:'SERVER_NAME',index:'SERVER_NAME', width:70,height:50},
                                {name:'SERVER_PATH',index:'SERVER_PATH', width:120},
                                {name:'IPADDRESS',index:'IPADDRESS', width:70},
                                {name:'REQUEST_TIME',index:'REQUEST_TIME', width:150},
                                {name:'PROJECT_NAME',index:'PROJECT_NAME', width:70},
                                {name:'REQUEST_URL',index:'REQUEST_URL', width:300},
                                {name:'TOTAL_EXECUTION_TIME',index:'TOTAL_EXECUTION_TIME', width:100,align:"right",sorttype:'int'},
                                {name:'TOTAL_EXECUTION_MIN',index:'TOTAL_EXECUTION_MIN', width:100,align:"right",sorttype:'int'},
                                {name:'BYTES_SENT',index:'BYTES_SENT', width:90,align:"right",sorttype:'int'},
                                {name:'KB_SENT',index:'KB_SENT', width:70,align:"right",sorttype:'int'},
                                {name:'MB_SENT',index:'MB_SENT', width:70,align:"right",sorttype:'int'},
                            ],
                            rowNum: 1000,
                            height: '300',
                            sortname: 'REQUEST_URL',
                            viewrecords: true,
                            loadonce:true,
                            sortorder: "desc",
                            caption:"Server Access Report - (Page Detail)",
                            grouping: false,
                            footerrow: false
                        });
                    }

                    function displayRequestURLWiseReport()
                    {
                        document.getElementById('griddiv').style.display="";
                        showRequestURLWiseReport();
                        return true;
                    }
                    function displayProjectWiseReport()
                    {
                        document.getElementById('griddiv').style.display="";
                        showProjectWiseReport();
                        return true;
                    }
                    function displayRequestURLReport()
                    {
                        document.getElementById('griddiv').style.display="";
                        showRequestURLReport();
                        return true;
                    }
                    function showReport()
                    {
                        var reportType = document.getElementById('selectreport').value;
                        if(reportType == "RequestURLWiseReport")
                        {
                            displayRequestURLWiseReport();
                            document.getElementById("showRemoveGrouping").style.display="";
                        }
                        else if(reportType == "ProjectWiseReport")
                        {
                            displayProjectWiseReport();
                            document.getElementById("showRemoveGrouping").style.display="none";
                        }
                        else if(reportType == "RequestURLReport")
                        {
                            displayRequestURLReport();
                            document.getElementById("showRemoveGrouping").style.display="none";
                        }
                    }
                    function removeGrouping()
                    {
                        jQuery("#list2").jqGrid('groupingRemove',false);
                    }
                    function showMenu()
                    {
                        document.getElementById("viewdiv").style.display="";
                        document.getElementById("griddiv").style.display="none";
                    }
                    $(function() {
                        $("#accessdate").datepicker(
                        {
                            showOn: 'button',
                            buttonImage: 'images/calendar.gif',
                            buttonImageOnly: true,
                            changeMonth: true,
                            changeYear: true ,
                            dateFormat:'dd-mm-yy',
                            showButtonPanel: false
                        });
                    });
                </script>
            </head>
            <body>
                <br>
                <form name="viewform" id="viewform" method="post" action="">
                    <div id="viewdiv">
                        <table border="0"align="center" class="tbl_border1" id="viewtable" width="45%">
                            <tr class="tbl_h1_bg">
                                <th colspan="100%">Server Access Audit Report</th>
                            </tr>
                            <tr>
                                <td width="50%" align="right">
                                    Enter accessDate :
                                </td>
                                <td align="left">
                                    <jsp:useBean id="now" class="java.util.Date" scope="request"></jsp:useBean>
                                    <input align="left" type="text" name="accessdate" id="accessdate" readonly value='<fmt:formatDate pattern="dd-MM-yyyy" value="${now}"/>' />
                                </td>
                            </tr>
                            <tr>
                                <td align="right" valign="top">
                                    Select Report Type:
                                </td>
                                <td colspan="100%" align="left">
                                    <select id="selectreport" name="selectreport">
                                        <option value="--Select Type--">--Select Type--</option>
                                        <option value="RequestURLWiseReport">RequestURLWiseReport</option>
                                        <option value="ProjectWiseReport">ProjectWiseReport</option>
                                        <option value="RequestURLReport">RequestURLReport</option>
                                    </select>

                                </td>
                            </tr>
                            <tr>
                                <td colspan="100%" align="center">
                                    <input type="button" name="submit" id="submit" value="Show" onclick="javascript: showReport();">
                                    <input type="reset" name="reset" id="reset" value="Reset">
                                </td>
                            </tr>
                        </table>
                    </div>
                    <br><div id="griddiv" align="center" style="display: none">
                        <table id="list2" align="center">
                        </table>
                        <br>
                        <div id="showRemoveGrouping" style="display: none">
                            <input type="button" id ="removegrouping" name="removegrouping" value="Remove Grouping" onclick="javascript: removeGrouping();">
                        </div>
                        <br>
                        <input type="button" name="back" id="back" value="Back" onclick="javascript:showMenu();">
                    </div>
                </form>
            </body>
        </html>
    </c:when>
</c:choose>

<%--

--sample queris

--page wise summary
SELECT SERVER_NAME,SERVER_PATH,PROJECT_NAME,REQUEST_URL,HTTPSTATUS,COUNT(1) TOTAL_REQUEST,SUM(BYTES_SENT) TOT_BYTES_SENT,ROUND(SUM(TOTAL_EXECUTION_TIME),4) TOT_EXECUTION_TIME  FROM SERVER_ACCESS_AUDIT
group by SERVER_NAME,SERVER_PATH,PROJECT_NAME,REQUEST_URL,HTTPSTATUS
having TOTAL_REQUEST > 10
or TOT_BYTES_SENT > 10000
or TOT_EXECUTION_TIME > 1
GO

--project wise summary
SELECT SERVER_NAME,SERVER_PATH,PROJECT_NAME,HTTPSTATUS,COUNT(1) TOTAL_REQUEST,SUM(BYTES_SENT) TOT_BYTES_SENT,ROUND(SUM(TOTAL_EXECUTION_TIME),4) TOT_EXECUTION_TIME  FROM SERVER_ACCESS_AUDIT
group by SERVER_NAME,SERVER_PATH,PROJECT_NAME,HTTPSTATUS
GO

--project wise summary - 404
SELECT SERVER_NAME,SERVER_PATH,PROJECT_NAME,HTTPSTATUS,COUNT(1) TOTAL_REQUEST,SUM(BYTES_SENT) TOT_BYTES_SENT,ROUND(SUM(TOTAL_EXECUTION_TIME),4) TOT_EXECUTION_TIME  FROM SERVER_ACCESS_AUDIT
where HTTPSTATUS >= 400
group by SERVER_NAME,SERVER_PATH,PROJECT_NAME,HTTPSTATUS
GO

--top max execution time wise report - desc
SELECT SERVER_NAME,SERVER_PATH,IPADDRESS,REQUEST_TIME, PROJECT_NAME,REQUEST_URL,
ROUND(TOTAL_EXECUTION_TIME,4) TOTAL_EXECUTION_TIME,
ROUND(TOTAL_EXECUTION_TIME/60,1) TOTAL_EXECUTION_MIN,
BYTES_SENT,BYTES_SENT/1024 KB_SENT,BYTES_SENT/(1024*1024) MB_SENT
FROM SERVER_ACCESS_AUDIT
where TOTAL_EXECUTION_TIME > 60
or BYTES_SENT > 10000
order by TOTAL_EXECUTION_TIME desc
limit 100

GO

--top max byte sent wise report desc
SELECT SERVER_NAME,SERVER_PATH,IPADDRESS,REQUEST_TIME, PROJECT_NAME,REQUEST_URL,BYTES_SENT,BYTES_SENT,BYTES_SENT/1024 KB_SENT,BYTES_SENT/(1024*1024) MB_SENT
FROM SERVER_ACCESS_AUDIT
order by BYTES_SENT desc
limit 100
GO

--%>