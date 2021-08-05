<%-- 
    Document   : dataexport
    Created on : Feb 23, 2011, 10:51:44 AM
    Author     : njuser
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>

        <link href="css/main.css" rel="stylesheet" type="text/css">
        <link rel="stylesheet" type="text/css" href="${finlibPath}/jquery/jquery.jqGrid-3.8.2/css/ui.jqgrid.css">
        <link rel="stylesheet" type="text/css" href="${finlibPath}/jquery/jquery-ui-1.8.10.custom/development-bundle/themes/blitzer/jquery.ui.all.css"  />

        <script type="text/javascript" src="javascript/ajaxfunctions.js"></script>
        <script type="text/javascript" language="javascript">

            function checkFile()
            {
                var fnm=document.getElementById('filenm').value;
                var temp=fnm.substring(fnm.length-4).toLowerCase();

                switch(temp)
                {
                    case '.csv':
                    case '.xls':
                    case '.dbf':return true;
                    case '':alert('Please Enter FileName');
                        document.getElementById('filenm').value="";
                        document.getElementById('filenm').focus();
                        return false;
                    default:alert('Please Enter .csv/.xls/.dbf File ');
                        document.getElementById('filenm').value="";
                        document.getElementById('filenm').focus();
                        return false;
                }
            }

            function SubmitQuery()
            {
                var f=document.exportdata;
                var data=getalldata(f);

                if(f.aliasname.value=='')
                {
                    alert("Please Enter Alias Name");
                    return false;
                }
                var query=document.getElementById('query').value;
                if(query=='')
                {
                    alert("Please Enter Query");
                    return false;
                }
                
                getData_sync("dataexport.fin?cmdAction=submitQuery",'message', data);
            }

                    

        </script>
    </head>
    <body>
        <c:if test="${process eq 'selectTable'}">
            <form id="exportdata" name="exportdata" method="POST" >

                <br/>

                <table align="center" class="tbl_border1" id="selecttable" width="40%">
                    <tr class="tbl_h1_bg">
                        <th colspan="100%">Export Data</th>
                    </tr>
                    <tr>
                        <th colspan="100%">&nbsp;</th>
                    </tr>
                    <tr>
                        <td align="right">
                            Enter Alias Name :
                        </td>
                        <td align="left">
                            <input type="text" name="aliasname" id="aliasname"/>
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
                            Enter Query :
                        </td>
                        <td align="left">
                            <textarea id="query" name="query" rows="3" cols="18"></textarea>
                        </td>
                    </tr>

                    <tr>
                        <td align="right">
                            Enter File Name :
                        </td>
                        <td align="left">
                            <input type="text" name="filenm" id="filenm" onblur="javascript: checkFile();" />
                        </td>
                    </tr>
                    <tr>
                        <td colspan="100%" align="center">
                            <input type="button" name="next" id="next" value="Next" onclick="javascript: SubmitQuery();"/>
                            <input type="button" name="close" id="close" value="Close" onclick="javascript: hideTable();"/>
                        </td>
                    </tr>
                </table>

            </form>
        </c:if>
        <c:if test="${process eq 'message'}">
            <table width="95%" align="center">
                <c:if test="${message eq 'SuccessFully Exported'}">
                    <tr>
                        <td colspan="100%">
                            <a href="download/${filename}">Download File</a>
                        </td>
                    </tr>
                </c:if>
                <tr>
                    <td colspan="100%" class="tbl_h2_bg" align="center" style="font-size: large">${message}</td>
                </tr>

            </table>
        </c:if>
        <div id="message">

        </div>
    </body>

</html>


