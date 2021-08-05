<%--
    Document   : testcasevaluemaster
    Created on : Feb 2, 2009, 2:14:56 PM
    Author     : Swapnil Solanki
--%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8" import="java.sql.*,resources.*"%>

<script language="javascript" type="text/javascript">
    
    function TextboxValidation()
    {
        var flag=0;
        var empty=document.getElementById("rooturl").value;
        if (empty=="")
        {
            alert("Please enter a url ");
            flag=1;

//            return false;
        }
        
        var flag1=0;
        var intCharCode=document.getElementById("maxlinks").value;
        for (var i = 0; i < intCharCode.length; i++)
        {
            max = intCharCode.charCodeAt(i);

            if (!((max >= 48 && max <= 57)))
            {
//                alert("Please enter an integer number otherwise it will ckeck all the links ");
                alert("Please enter an integer number ");
                flag1=1;
                break;

            }

        }
        
        if(flag==1 )//|| flag1==1)
        {            
            return false;
        }
        else
        {
             return true;
        }
    }

</script>
<link rel="stylesheet" type="text/css" href="./css/header.css" />

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<table height="100%" width="100%" border="0" cellpadding="0" cellspacing="0" >
<%--<tr height="8%" >
<td bgcolor="#ff0000">

<%@include file="header.jsp"%>

</td>
</tr>

<tr height="2%" >
<td>

<%@include file="panel.jsp"%>

</td>
</tr>--%>

<tr height="80%">
    <td valign="top">

    <h4 align="center" style="font-family:arial;color:#670507">Link Checker</h4>

    <form action="linkchecker.fin" method="post" name="frmlinkcheckermaster" onsubmit="javascript:TextboxValidation();">
            <table align="center" border="0">
                <tbody>
                    <tr>
                        <td  width="30%" class="tdleft" colspan="100%">Enter Root Url:</td>
                        <td class="tdleftdata" colspan="20%">
                            <input id="rooturl" type="text" value="" name="rooturl" size="70"/>
                        </td>

                    </tr>

                    <tr>
                        <td width="30%" class="tdleft" colspan="100%">Maximum Links:</td>
                        <td class="tdleftdata" colspan="50%">
                            <input id="maxlinks" type="text" value="0" name="maxlinks"/>
                        </td>
                     </tr>

                     <tr>
                        <td width="30%" class="tdleft" colspan="100%">Check External Links:</td>
                        <td class="tdleftdata" colspan="50%">
                            <input type="checkbox" value="yes" name="external"/>
                        </td>
                    </tr>

                    <tr>
                        <td class="tdleft" colspan="100%">

                        </td>

                        <td class="tdleftdata" align="center" colspan="50%" >
                            <input id="control" value="Start" type="submit" name="submit" align="center" />
                        </td>

                        <td></td>
                    </tr>

                    <tr>

                    <table align="left" border="1" width="100%" cellpadding="0" cellspacing="0">

                        <tr>
                        <b><th class="tdleft">No.</th></b>
                        <b><th class="tdleft">Parent Url</th></b>
                        <b><th class="tdleft">Current Url</th></b>
                        <b><th class="tdleft">Response Code</th></b>
                        <b><th class="tdleft">Content Type</th></b>
                        </tr>

                        <c:forEach var="link1" items="${list}" varStatus="st">

                            <tr>
                                <td>${st.count}</td>
<!--
                                <td><a href="${link1.parentUrl}" target="_blank">${link1.parentUrl}</a></td> -->
                                <td class="tdleftdata">${link1.parentUrl}</td>
                                <td class="tdleftdata">${link1.currentUrl}</td>
                                <td class="tdleftdata">${link1.responseCode}</td>
                                <td class="tdleftdata">${link1.contentType}</td>
                            </tr>

                        </c:forEach>

                    </table>
                    </tr>

                </tbody>

             </table>

        </form>  

    </td>
</tr>

<tr height="10%">
<td>

<%--<%@include file="footer.jsp"%>--%>

</td>
</tr>
</table>

</body>