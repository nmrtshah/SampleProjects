<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page import="java.util.Calendar" %>

<%
            String finlibPath = finpack.FinPack.getProperty("finlib_path");
%>

<script language="javascript" type="text/javascript">

    // Current Server Time script (SSI or PHP)- By JavaScriptKit.com (http://www.javascriptkit.com)
    // For this and over 400+ free scripts, visit JavaScript Kit- http://www.javascriptkit.com/
    // This notice must stay intact for use.

    //Depending on whether your page supports SSI (.shtml) or PHP (.php), UNCOMMENT the line below your page supports and COMMENT the one it does not:
    //Default is that SSI method is uncommented, and PHP is commented:

    //var currenttime = '<!--#config timefmt="%B %d, %Y %H:%M:%S"--><!--#echo var="DATE_LOCAL" -->' //SSI method of getting server date
    //var currenttime = '<? print date("F d, Y H:i:s", time())?>' //PHP method of getting server date
    <%!
        public String getMonth(int i)
        {
            String[] montharray = new String[]
            {
                "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"
            };
            return montharray[i];
        }

    %>
    <%
                Calendar c = Calendar.getInstance();
                String mydate = getMonth(c.get(Calendar.MONTH)) + " "
                        + c.get(Calendar.DAY_OF_MONTH) + ","
                        + c.get(Calendar.YEAR) + " "
                        + c.get(Calendar.HOUR_OF_DAY) + ":"
                        + c.get(Calendar.MINUTE) + ":"
                        + c.get(Calendar.SECOND);
    %>

        var currenttime = '<%=mydate%>' //Java method of getting server date

        ///////////Stop editting here/////////////////////////////////

        //var montharray=new Array("January","February","March","April","May","June","July","August","September","October","November","December")

        var serverdate=new Date(currenttime)

        function padlength(what){
            var output=(what.toString().length==1)? "0"+what : what
            return output
        }

        function displaytime(){
            serverdate.setSeconds(serverdate.getSeconds()+1)
            var datestring=padlength(serverdate.getDate())+"/"+(serverdate.getMonth()+1)+"/" + serverdate.getFullYear()
            var timestring=padlength(serverdate.getHours())+":"+padlength(serverdate.getMinutes())+":"+padlength(serverdate.getSeconds())
            document.getElementById("servertime").innerHTML=datestring+" "+timestring
        }

        window.onload=function(){
            setInterval("displaytime()", 1000)
        }

</script>

<div id="header_print">
    <link href="<%=finlibPath%>/resource/main.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="<%=finlibPath%>/jquery/jquery-ui-1.8.13.custom/css/smoothness/jquery-ui-1.8.13.custom.css"/>
    <link rel="stylesheet" type="text/css" href="<%=finlibPath%>/jquery/jquery.jqGrid-3.8.2/css/ui.jqgrid.css"/>
    <link rel="stylesheet" type="text/css" href="<%=finlibPath%>/jquery/jquery-ui-multiselect-widget/jquery.multiselect.css" />
    <link rel="stylesheet" type="text/css" href="<%=finlibPath%>/jquery/jquery-ui-multiselect-widget/jquery.multiselect.filter.css"/>
    <%--<script type="text/javascript" src="<%=finlibPath%>/resource/ie6_warning.js"></script>--%>
    <div class="header_bg">
        <div class="header header_bg">
            <a href="#"><img width="228" height="50" class="logo_company" src='images/logo_njindiainvest.gif' alt="NJFUNDZ"/></a>
        </div>
    </div>
    <TABLE BORDER=0 CELLPADDING=0 CELLSPACING=0 WIDTH='100%' >
        <TR class='Row_Header'>
            <TD ALIGN='LEFT' HEIGHT='20'>&nbsp;</TD>
            <TD ALIGN='left' NOWRAP CLASS='clsbtnon' >&nbsp;&nbsp;Welcome To FinReport</TD>
            <TD ALIGN='RIGHT' NOWRAP CLASS='clsbtnon' COLSPAN='100%' >
                <span id="servertime"></span>&nbsp;&nbsp;</TD>
        </TR>
        <TR class='Line_Header'><TD ALIGN='center' COLSPAN='100%' NOWRAP></TD></TR>
    </TABLE>
</div>
<div class="deskbar">
    <span>FinStudio-Report Module</span>
    <font class="deskbar_logout">Logout</font>
</div>
<div class="breadcrum">Home<a href="javascript:history.back();" style="float:right; padding-right:10px; text-decoration:none;">Back</a></div>
