<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:choose>
    <c:when test="${action eq 'connAuditMenu'}">
        <html xmlns="http://www.w3.org/1999/xhtml">
            <head>
                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
                <link href="css/main.css" rel="stylesheet" type="text/css"/>


                <link rel="stylesheet" type="text/css" href="${finlibPath}/jquery/jquery.jqGrid-3.8.2/css/ui.jqgrid.css"/>
                <link rel="stylesheet" type="text/css" href="${finlibPath}/jquery/jquery-ui-1.8.10.custom/development-bundle/themes/blitzer/jquery.ui.all.css"  />

                <script type="text/javascript" src="${finlibPath}/jquery/jquery-ui-1.8.10.custom/development-bundle/jquery-1.4.4.js"></script>

                <script type="text/javascript" src="${finlibPath}/jquery/jquery-ui-1.8.10.custom/development-bundle/ui/jquery.ui.core.js"></script>
                <script type="text/javascript" src="${finlibPath}/jquery/jquery-ui-1.8.10.custom/development-bundle/ui/jquery.ui.datepicker.js"></script>
                <script type="text/javascript" src="${finlibPath}/jquery/jquery.jqGrid-3.8.2/js/i18n/grid.locale-en.js"></script>
                <script type="text/javascript" src="${finlibPath}/jquery/jquery.jqGrid-3.8.2/js/jquery.jqGrid.min.js"></script>
                <script type="text/javascript" src="${finlibPath}/jquery/jquery.jqGrid-3.8.2/src/grid.celledit.js"></script>
                <script type="text/javascript" src="javascript/ajaxfunctions.js"></script>

                <script type="text/javascript">
                    
                    $(function() {
                        $("#datePicker").datepicker(
                        {
                            showOn: 'button',
                            buttonImage: 'images/calendar.gif',
                            buttonImageOnly: true,
                            changeMonth: true,
                            changeYear: true ,
                            dateFormat:'d-m-yy',
                            showButtonPanel: false
                        });
                    });

                    
                    function showTable()
                    {
                        document.getElementById("Report").style.display="none";
                        document.getElementById("selReport").style.display="";
                    }
                    function showTimeTable()
                    {
                        document.getElementById("Report").style.display="";
                        document.getElementById("TimeReport").style.display="none";
                    }
                    function ShowGrid(reportType)
                    {
                        document.getElementById("selReport").style.display="none";
                        document.getElementById("Report").style.display="";
                        if(reportType == 'Page Wise')
                        {
                            ShowGridPageWise();
                        }
                        else if(reportType == 'Date Wise')
                        {
                            ShowGridDateWise();
                        }
                        else if(reportType == 'Time Wise')
                        {
                            ShowGridTimeWise();
                        }
                        else if(reportType == 'Open Close Time Difference')
                        {
                            ShowGridTimeDiff();
                        }
                    }
            
                    function ShowGridPageWise()
                    {
                        var formDate=document.getElementById("datePicker").value;
                        var params=getalldata(document.connaudit);
                        $('#list2').GridUnload();
                        jQuery("#list2").jqGrid({

                            url:'connaudit.fin?cmdAction=connAuditReport&'+params,

                            datatype: "json",

                            colNames:['PAGE','SERVER','TOMCAT PATH','PROJECT','CONN MECH.','DB SERVER','LEAKS','TOT CONN.','DAYS'],
                            colModel:[
                                {name:'STACK_TRACE',index:'STACK_TRACE', width:200,align:'center'},
                                {name:'SERVER_NAME',index:'SERVER_NAME', width:120,align:'center'},
                                {name:'TOMCAT_PATH',index:'TOMCAT_PATH', width:120,align:'center'},
                                {name:'PROJECT_NAME',index:'PROJECT_NAME', width: 120, summaryType:'count', summaryTpl : '({0}) total',align:'center'},
                                {name:'CONN_MECHANISM',index:'CONN_MECHANISM', width:120,align:'center'},
                                {name:'DB_SERVER_TYPE',index:'DB_SERVER_TYPE', width:120,align:'center'},
                                {name:'TOTAL_LEAKS',index:'TOTAL_LEAKS', width:70, summaryType:'sum', summaryTpl : '({0}) total',align:'center',sorttype:'int'},
                                {name:'TOTAL_CONNECTION',index:'TOTAL_CONNECTION', width:70,align:'center',sorttype:'int',summaryTpl : '({0}) total'},
                                {name:'TOTAL_DAYS',index:'TOTAL_DAYS', width:70,align:'center',sorttype:'int'},
                            ],
                            rowNum: 1000,
                            //rowList:[10,20,30],
                            //pager: '#pager2',
                            height:'300',
                            sortname: 'PROJECT NAME',
                            viewrecords: true,
                            loadonce:true,
                            sortorder: "desc",
                            caption:"Page Wise Report of "+formDate,
                            footerrow: false
                            /*grouping: true,
                            groupingView : {
                                groupField : ['PROJECT_NAME'],
                                groupSummary : [true],
                                groupColumnShow : [true],
                                groupText : ['{0}'],
                                groupCollapse : false,
                                groupOrder: ['asc']
                            }*/

                        });
                    }
                    function ShowGridDateWise()
                    {
                        var formDate=document.getElementById("datePicker").value;
                        var params=getalldata(document.connaudit);
                        $('#list2').GridUnload();
                        jQuery("#list2").jqGrid({

                            url:'connaudit.fin?cmdAction=connAuditReport&'+params,

                            datatype: "json",

                            colNames:['SERVER','TOMCAT PATH','PROJECT','DB SERVER','CONN MECH.','LEAKS','TOT CONN.'],
                            colModel:[
                                {name:'SERVER_NAME',index:'SERVER_NAME', width:150,align:'center'},
                                {name:'TOMCAT_PATH',index:'TOMCAT_PATH', width:150,align:'center'},
                                {name:'PROJECT_NAME',index:'PROJECT_NAME', width:150, summaryType:'count', summaryTpl : '({0}) total',align:'center'},
                                {name:'DB_SERVER_TYPE',index:'DB_SERVER_TYPE', width:150,align:'center'},
                                {name:'CONN_MECHANISM',index:'CONN_MECHANISM', width:150,align:'center'},
                                {name:'TOTAL_LEAKS',index:'TOTAL_LEAKS', width:70,summaryType:'sum', summaryTpl : '({0}) total',align:'center',sorttype:'int'},
                                {name:'TOTAL_CONNECTION',index:'TOTAL_CONNECTION', width:70,align:'center',sorttype:'int',summaryTpl : '({0}) total'}

                            ],
                            rowNum: 1000,
                            //rowList:[10,20,30],
                            // pager: '#pager2',
                            height:'300',
                            sortname: 'PROJECT_NAME',
                            viewrecords: true,
                            loadonce:true,
                            sortorder: "desc",
                            caption:"Date Wise Report of "+formDate,
                            grouping: false,
                            footerrow: false
                        });
                    }
                    function ShowGridTimeWise()
                    {
                        var formDate=document.getElementById("datePicker").value;
                        var params=getalldata(document.connaudit);
                        $('#list2').GridUnload();
                        jQuery("#list2").jqGrid({

                            url:'connaudit.fin?cmdAction=connAuditReport&'+params,

                            datatype: "json",

                            colNames:['HOUR MIN','NO OF CONN','DETAIL'],
                            colModel:[
                                {name:'HOUR_MIN',index:'HOUR_MIN',width: 200,align:'center'},
                                {name:'NO_OF_CONN',index:'NO_OF_CONN', width:200,align:'center',sorttype:'int'},
                                {name:'DETAIL',index:'DETAIL',width: 200,align:'center'}
                            ],
                            rowNum: 1000,
                            //rowList:[10,20,30],
                            // pager: '#pager2',
                            height:'300',
                            forceFit:true,
                            sortname: 'HOUR_MIN',
                            sortorder: "desc",
                            viewrecords: true,
                            loadonce:true,
                            caption:"Time Wise Report of "+formDate,
                            grouping: false,
                            footerrow: false,
                            gridComplete:function() {
                                var ids = jQuery("#list2").jqGrid("getDataIDs");
                                for(var i=0; i<ids.length; i++)
                                {
                                    var cl = ids[i];
                                    var column0 = $('#list2').getCell(cl, 'NO_OF_CONN');
                                    var column1=$('#list2').getCell(cl, 'HOUR_MIN');
                                    //alert(column1);
                                    var vcontrol0="<a href='#' onclick=\"javascript:showConnAuditTimeReport('"+column1+"');\"><font color='BLUE'>Detail</font></a>";
                                    
                                    jQuery("#list2").jqGrid('setRowData',ids[i],{DETAIL:vcontrol0});
                                }
                            }
                        });
                    }
                    function showConnAuditTimeReport(hourMin)
                    {
                        document.getElementById("Report").style.display="none";
                        document.getElementById("TimeReport").style.display="";
                        showGridTimeWise(hourMin);
                    }
                    function showGridTimeWise(hourMin)
                    {
                        var formDate=document.getElementById("datePicker").value;
                        //alert(hourMin);
                        $('#list1').GridUnload();
                        jQuery("#list1").jqGrid({

                            url:'connaudit.fin?cmdAction=connAuditTimeReport&hourMin='+hourMin,

                            datatype: "json",

                            colNames:['HourMin','SERVER','TOMCAT PATH','DB SERVER','CONN MECH.','PROJECT','TOT CONN.','PAGE'],
                            colModel:[
                                {name:'HourMin',index:'HourMin', width:100,align:'center'},
                                {name:'SERVER_NAME',index:'SERVER_NAME', width:100,align:'center'},
                                {name:'TOMCAT_PATH',index:'TOMCAT_PATH', width:120,align:'center'},
                                {name:'DB_SERVER_TYPE',index:'DB_SERVER_TYPE', width:120,align:'center'},
                                {name:'CONN_MECHANISM',index:'CONN_MECHANISM', width:120,align:'center'},
                                {name:'PROJECT_NAME',index:'PROJECT_NAME', width:120,align:'center'},
                                {name:'TOTAL_CONNECTION',index:'TOTAL_CONNECTION', width:50,align:'center',sorttype:'int'},
                                {name:'STACK_TRACE',index:'STACK_TRACE', width:200,align:'center'}
                            ],
                            rowNum: 1000,
                            //rowList:[10,20,30],
                            // pager: '#pager1',
                            height:'300',
                            // width: 'auto',
                            sortname: 'PROJECT_NAME',
                            loadonce:true,
                            viewrecords: true,
                            sortorder: "desc",
                            caption:"Particular Time Wise Report of "+formDate,
                            grouping: false,
                            footerrow: false
                            
                        });
                    }
                    function ShowGridTimeDiff()
                    {
                        var formDate=document.getElementById("datePicker").value;
                        var params=getalldata(document.connaudit);
                        $('#list2').GridUnload();
                        jQuery("#list2").jqGrid({

                            url:'connaudit.fin?cmdAction=connAuditReport&'+params,

                            datatype: "json",

                            colNames:['DATE','SERVER','TOMCAT PATH','PROJECT','DB SERVER','CONN MECH.','TOT MINUTES','STACK_TRACE'],
                            colModel:[
                                {name:'ON_DATE',index:'ON_DATE', width:100,align:'center'},
                                {name:'SERVER_NAME',index:'SERVER_NAME', width:100,align:'center'},
                                {name:'TOMCAT_PATH',index:'TOMCAT_PATH', width:120,align:'center'},
                                {name:'PROJECT_NAME',index:'PROJECT_NAME', width:120,align:'center'},
                                {name:'DB_SERVER_TYPE',index:'DB_SERVER_TYPE', width:120,align:'center'},
                                {name:'CONN_MECHANISM',index:'CONN_MECHANISM', width:120,align:'center'},
                                {name:'TOTAL_CONNECTION',index:'TOTAL_CONNECTION', width:100,align:'center',sorttype:'int'},
                                {name:'STACK_TRACE',index:'STACK_TRACE', width:200,align:'center'}
                            ],
                            rowNum: 1000,
                            //rowList:[10,20,30],
                            // pager: '#pager2',
                            height:'300',
                            forceFit:true,
                            sortname: 'ON_DATE',
                            sortorder: "desc",
                            viewrecords: true,
                            loadonce:true,
                            caption:"Open Close Time Difference Report of "+formDate,
                            grouping: false,
                            footerrow: false
                        });
                    }

                    function getDataSync(dataSource,param,flag)
                    {
                        // alert(dataSource+"   "+divID+"   "+param);
                        var XMLHttpRequestObject =false;
                        if(window.XMLHttpRequest)
                        {
                            XMLHttpRequestObject = new XMLHttpRequest();
                        }else if (window.ActiveXObject)
                        {
                            XMLHttpRequestObject = new ActiveXObject("Microsoft.XMLHTTP");
                        }
                        if(XMLHttpRequestObject)
                        {

                            XMLHttpRequestObject.open("POST",dataSource,flag);
                            XMLHttpRequestObject.setRequestHeader('Content-Type','application/x-www-form-urlencoded');
                            XMLHttpRequestObject.send(param);

                            if(XMLHttpRequestObject.readyState == 4)
                            {
                                return XMLHttpRequestObject.responseText;
                            }

                        }
                    }
                </script>
                <style type="text/css">
                    #ui-datepicker-div {z-index: 100;}
                </style>
            </head>
            <body>
                <br/>
                <form id="connaudit" name="connaudit" method="POST" action="connaudit.fin?cmdAction=connAuditReport">
                    <div id="selReport">
                        <table align="center" class="tbl_border1" id="selReportType">
                            <tr class="tbl_h1_bg">
                                <th colspan="100%">Report Type</th>
                            </tr>
                            <tr>
                                <th colspan="100%">&nbsp;</th>
                            </tr>
                            <tr>
                                <td align="right">
                                    Select Report Type :
                                </td>
                                <td align="left">
                                    <select name="reportType" id="reportType">
                                        <option value="Page Wise">Page Wise</option>
                                        <option value="Date Wise">Date Wise</option>
                                        <option value="Time Wise">Time Wise</option>
                                        <option value="Open Close Time Difference">Open Close Time Difference</option>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td align="right">
                                    Select Date :
                                </td>
                                <td align="left">
                                    <jsp:useBean id="now" class="java.util.Date" scope="request"></jsp:useBean>
                                    <%
                                                now.setDate(now.getDate() - 1);
                                    %>
                                    <input type="text" name="datePicker" id="datePicker" readonly value='<fmt:formatDate pattern="d-M-yyyy" value="${now}"/>' />
                                </td>
                            </tr>
                            <tr>
                                <td colspan="100%" align="center">
                                    <input type="button" name="action1" value="Submit"
                                           onclick="javascript: ShowGrid(document.getElementById('reportType').value);"/>
                                    &nbsp;
                                    <input type="reset" name="action2" value="Reset"/>
                                </td>
                            </tr>

                        </table>
                    </div>
                    <div align="center" id="Report" style="display: none">

                        <table>
                            <tr>
                                <td>
                                    <input name="back" value="Back" type="button" onclick="javascript: showTable();"/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <table id="list2" align="center" border="1" >

                                    </table>

                                    <div id="pager2" align="center"></div>
                                    <div id="temp" align="center"></div>    
                                </td>
                            </tr>


                        </table>



                        <br/>
                        <br/>

                    </div>
                    <div align="center" id="TimeReport" style="display: none">

                        <table>
                            <tr>
                                <td>
                                    <input name="back" value="Back" type="button" onclick="javascript: showTimeTable();"/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <table id="list1" align="center" border="1">

                                    </table>

                                    <div id="pager1" align="center">

                                    </div>
                                </td>
                            </tr>
                        </table>
                    </div>

                </form>
            </body>
        </html>
    </c:when>
    <c:when test="${action eq 'connAuditReport'}">
        ${json}
    </c:when>
    <c:when test="${action eq 'connAuditTimeReport'}">
        ${json}
    </c:when>

</c:choose>