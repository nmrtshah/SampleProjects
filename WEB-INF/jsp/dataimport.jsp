
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%! static int i = 0;%>

<c:choose>
    <c:when test="${process eq 'selectFile'}">
        <% i = 0;%>
        <html>
            <head>
                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                <title>JSP Page</title>
                <link href="css/main.css" rel="stylesheet" type="text/css">
                <link rel="stylesheet" type="text/css" href="${finlibPath}/jquery/jquery.jqGrid-3.8.2/css/ui.jqgrid.css">
                <link rel="stylesheet" type="text/css" href="${finlibPath}/jquery/jquery-ui-1.8.10.custom/development-bundle/themes/blitzer/jquery.ui.all.css"  />
                <script type="text/javascript" language="javascript">
                    function checkExt()
                    {
                        var fnm=document.getElementById("filename").value;
                        var temp=fnm.substring(fnm.length-4).toLowerCase();

                        switch(temp)
                        {
                            case '.csv':
                            case '.xls':
                            case '.dbf':return true;
                            case '':alert('Please Select File');
                                document.getElementById("filename").value="";
                                document.getElementById("filename").focus();
                                return false;
                            default:alert('Please Select .csv/.xls/.dbf File ');
                                document.getElementById("filename").value="";
                                document.getElementById("filename").focus();
                                return false;
                        }
                    }
                    function hideTable()
                    {
                        document.getElementById("selectFile").style.display="none";
                    }
                    function checkEmpty()
                    {
                        if(document.getElementById("filename").value=="")
                        {
                            alert("Please Select File");
                            return false;
                        }
                        return true;
                    }
                    function hideHeader()
                    {
                        var fnm=document.getElementById("filename").value;
                        var temp=fnm.substring(fnm.length-4).toLowerCase();

                        if(temp=='.dbf' || temp=='.xls')
                        {
                            document.getElementById("header").disabled=true;
                        }
                        /*else if(temp=='.dbf' && temp=='.xls')
                        {
                               document.getElementById("del").disabled=true;
                               document.getElementById("quote").disabled=true;
                        }*/
                    }
                </script>
            </head>
            <body>
                <form id="selectfile" name="selectfile" method="POST"  action="dataimport.fin?cmdAction=selectTable" enctype="multipart/form-data" onsubmit="return checkExt();">
                    <br/>
                    <table align="center" class="tbl_border1" id="selectFile">
                        <tr class="tbl_h1_bg">
                            <th colspan="100%">Import Data</th>
                        </tr>
                        <tr>
                            <th colspan="100%">&nbsp;</th>
                        </tr>
                        <tr>
                            <td align="right">
                                Select File Name :
                            </td>
                            <td align="left">
                                <input type="file" name="filename" id="filename" onblur="javascript: hideHeader();"/>
                            </td>
                        </tr>
                        <tr>
                            <td align="right">
                                Delimeter :
                            </td>
                            <td align="left">
                                <select id="del" name="del">
                                    <option value=",">,</option>
                                    <option value=";">;</option>
                                    <option value="tab">{tab}</option>
                                    <option value="space">{space}</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td align="right">
                                String Quoted Identifier :
                            </td>
                            <td align="left">
                                <select id="quote" name="quote">
                                    <option value="'">'</option>
                                    <option value='"'>"</option>
                                    <option value='N' selected>{none}</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td align="right">
                                First Row Contains Column Headers :
                            </td>
                            <td align="left">
                                <input type="checkbox" name="header" id="header" />
                            </td>
                        </tr>

                        <tr>
                            <td colspan="100%" align="center">
                                <input type="submit" name="next" id="next" value="Next" onclick="javascript: return checkEmpty();"/>
                                <input type="button" name="close" id="close" value="Close" onclick="hideTable();"/>
                            </td>
                        </tr>
                    </table>
                </form>
            </body>
        </html>
    </c:when>
    <c:when test="${process eq 'selectTable'}">
        <% i = 0;%>
        <html>
            <head>
                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                <title>JSP Page</title>
                <link href="css/main.css" rel="stylesheet" type="text/css">
                <link rel="stylesheet" type="text/css" href="${finlibPath}/jquery/jquery.jqGrid-3.8.2/css/ui.jqgrid.css">
                <link rel="stylesheet" type="text/css" href="${finlibPath}/jquery/jquery-ui-1.8.10.custom/development-bundle/themes/blitzer/jquery.ui.all.css"  />
                <script type="text/javascript" src="javascript/ajaxfunctions.js"></script>

                <script type="text/javascript" language="javascript">
                    function getTables()
                    {
                        if(document.getElementById("tablename").value=="")
                        {
                            alert("Please Enter Table Name");
                        }
                        else
                        {
                            var params=getalldata(document.selectfile);
                            getData("dataimport.fin?cmdAction=fillTable","divtables", params);
                        }
                    }

                    function getColumns()
                    {
                        var params=getalldata(document.selectfile);
                        getData("dataimport.fin?cmdAction=fillColumn","selCol", params);
                    }
                    function checkEmpty()
                    {
                        if(document.getElementById("aliasname").value=="")
                        {
                            //alert(document.getElementById("tables").value);
                            alert("Please Enter Alias Name");
                            return false;
                        }
                        else if(document.getElementById("tablename").value=="")
                        {
                            alert("Please Enter Table Name");
                            return false;
                        }
                        else if(document.getElementById("tables").value==0)
                        {
                            alert("Please Select Table Name");
                            return false;
                        }
                        return true;
                    }
                    function checkSelection()
                    {
                        var flds=new Array();
                        flds=document.getElementsByName("fields");
                        
                        var i,cnt=0;
                        for(i=0;i<flds.length;i++)
                        {
                            if((document.getElementsByName("fields")[i].value)==0 && (document.getElementsByName("tabfields")[i].value)!=0)
                            {
                                //alert(document.getElementsByName("fields")[i].value);
                                cnt=1;
                                break;
                            }
                            else if((document.getElementsByName("fields")[i].value)!=0 && (document.getElementsByName("tabfields")[i].value)==0)
                            {
                                //alert(document.getElementsByName("fields")[i].value);
                                cnt=1;
                                break;
                            }
                            else
                            {
                                continue;
                            }
                        }
                        if(cnt==1)
                        {
                            alert("Please Do Proper Selection");
                            return false;
                        }
                        else if(cnt==0)
                        {
                            return true;
                        }
                    }
                    function checkEmptyCol()
                    {
                        
                        var flds=document.getElementsByName("fields");
                        var i,cnt=0;
                        // alert(flds.length);
                        for(i=0;i<flds.length;i++)
                        {
                            if((document.getElementsByName("fields")[i].value)==0 && (document.getElementsByName("tabfields")[i].value)==0)
                            {
                                cnt=1;
                            }
                            else
                            {
                                cnt=0;
                                break;
                            }
                        }
                        if(cnt==1)
                        {
                            alert("Please Select atleast One Column");
                            return false;
                        }
                        else if(cnt==0)
                        {
                            return true;
                        }
                    }
                  
                    function check()
                    {
                        if(checkEmpty() && checkSelection() && checkEmptyCol())
                        {
                            return true;
                        }
                        else
                        {
                            return false;
                        }
                    }
                    function hideTable()
                    {
                        document.getElementById("selectFile").style.display="none";
                    }
                   
                    
                </script>
            </head>
            <body>
                <form id="selectfile" name="selectfile" method="POST" 
                      action="dataimport.fin?cmdAction=importData">

                    <br/>
                    <input type="hidden" name="FileName1" value="${FileName}"/>
                    <input type="hidden" name="header1" value="${header1}"/>
                    <table align="center" class="tbl_border1" id="selectFile" width="40%">
                        <tr class="tbl_h1_bg">
                            <th colspan="100%">Import Data</th>
                        </tr>
                        <tr>
                            <th colspan="100%">&nbsp;</th>
                        </tr>
                        <tr>
                            <td align="right">
                                Enter Alias Name :
                            </td>
                            <td align="left">
                                <input type="text" name="aliasname" id="aliasname" />
                            </td>
                        </tr>
                        <tr>
                            <td align="right">
                                Select Database Server Type :
                            </td>
                            <td align="left">
                                <select id="dbtype" name="dbtype" >
                                    <option value="Mysql">Mysql</option>
                                    <option value="Oracle">Oracle</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td align="right">
                                Enter Table Name :
                            </td>
                            <td align="left">
                                <input type="text" name="tablename" id="tablename" onblur="javascript: getTables();"/>
                            </td>
                        </tr>
                        <tr>
                            <td align="right">
                                Select Table :
                            </td>
                            <td align="left">
                                <div id="divtables">
                                    <select name="tables" id="tables">
                                        <option value="0">Select Table</option>
                                    </select>
                                </div>

                            </td>
                        </tr>
                        <tr>
                            <td align="right">
                                Destination :
                            </td>
                            <td align="left">
                                <select id="dest" name="dest">
                                    <option value="Import Into Database">Import Into Database</option>
                                    <option value="Save Insert Statement Into File">Save Insert Statement Into File</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td align="left">
                                <br/>
                                File Columns<br/>

                                <c:forEach var="column" items="${fields}">
                                    <% i++;%>
                                    <select id="fields" name="fields">
                                        <option value="0">Select Column</option>
                                        <c:forEach var="column" items="${fields}" varStatus="status">
                                            <option value="${status.count}">${column}</option>
                                        </c:forEach>
                                    </select>
                                    <br/>
                                </c:forEach>
                            </td>
                            <td align="right">
                                <br/>
                                Table Columns<br/>
                                <div id="selCol">
                                </div>

                            </td>
                        </tr>
                        <tr>
                            <td colspan="100%" align="center">
                                <input type="submit" name="next" id="next" value="Next" onclick="javascript: return check();" />
                                <input type="button" name="close" id="close" value="Close" onclick="javascript: hideTable();"/>
                            </td>
                        </tr>
                    </table>

                </form>
            </body>
        </html>
    </c:when>
    <c:when test="${process eq 'fillTable'}">
        <select name="tables" id="tables" onchange="javascript: getColumns();">
            <option value="0">Select Table</option>
            <c:forEach var="column" items="${table_list}">
                <option value="${column.TABLE_NAME}">${column.TABLE_NAME}</option>
            </c:forEach>
        </select>
    </c:when>
    <c:when test="${process eq 'fillColumn'}">
        <c:forEach var="column" begin="1" end="<%= i%>">
            <select id="tabfields" name="tabfields">
                <option value="0">Select Column</option>
                <c:forEach var="column" items="${column_list}">
                    <option value="${column.COLUMN_NAME}">${column.COLUMN_NAME}</option>
                </c:forEach>
            </select>
            <br/>
        </c:forEach>

    </c:when>
    <c:when test="${process eq 'importData'}">
        <html>
            <head>
                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                <title>JSP Page</title>
                <link href="css/main.css" rel="stylesheet" type="text/css">
                <link rel="stylesheet" type="text/css" href="${finlibPath}/jquery/jquery.jqGrid-3.8.2/css/ui.jqgrid.css">
                <link rel="stylesheet" type="text/css" href="${finlibPath}/jquery/jquery-ui-1.8.10.custom/development-bundle/themes/blitzer/jquery.ui.all.css"  />
                <script language="javascript" type="text/javascript">
                    function hideTable()
                    {
                        document.getElementById("selectFile").style.display="none";
                    }
                </script>
            </head>
            <body>
                <form id="selectfile" name="selectfile" method="POST">
                    <br/>
                    <c:if test="${destination eq 'Save Insert Statement Into File'}">
                        <table align="center" class="tbl_border1" id="selectFile" width="40%">
                            <tr class="tbl_h1_bg">
                                <th colspan="100%">Import Data</th>
                            </tr>
                            <tr>
                                <th colspan="100%">&nbsp;</th>
                            </tr>
                            <tr>
                                <td>
                                    ${path} File Generated.
                                    <a href="log/${path}" >Download</a>
                                </td>
                            </tr>
                            <tr>
                                <th colspan="100%">&nbsp;</th>
                            </tr>
                            <tr>
                                <td>
                                    <input type="button" name="close" value="Close"/>
                                </td>
                            </tr>
                            <tr>
                                <th colspan="100%">&nbsp;</th>
                            </tr>
                        </table>
                    </c:if>
                    <c:if test="${destination eq 'Import Into Database'}">
                        <table align="center" class="tbl_border1" id="selectFile" width="40%">
                            <tr class="tbl_h1_bg">
                                <th colspan="100%">Import Data</th>
                            </tr>
                            <tr>
                                <th colspan="100%">&nbsp;</th>
                            </tr>
                            <tr>
                                <td>
                                    Import Completed...
                                </td>
                            </tr>
                            <tr>
                                <th colspan="100%">&nbsp;</th>
                            </tr>
                            <tr>
                                <td>
                                    <input type="button" name="close" value="Close" onclick="javascript:hideTable();"/>
                                </td>
                            </tr>
                            <tr>
                                <th colspan="100%">&nbsp;</th>
                            </tr>
                        </table>
                    </c:if>
                </form>
            </body>
        </html>
    </c:when>
</c:choose>
