<%-- 
    Document   : Main
    Created on : Feb 22, 2012, 12:01:11 PM
    Author     : Sonam Patel
--%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <title>Web Service Generation</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <link rel="stylesheet" type="text/css" href="${finlib_path}/resource/main_offline.css" />
        <script type="text/javascript" src="${finlib_path}/resource/serverCombo.js"></script>
        <script type="text/javascript" src="${finlib_path}/resource/common_functions.js"></script>

        <script type="text/javascript" src="javascript/ajaxfunctions.js"></script>
        <script type="text/javascript" src="javascript/WebServiceValidations.js"></script>
        <script type="text/javascript" src="${finlib_path}/resource/ajax.js"></script>
    </head>
    <body onload="javascript: onload();">
        <div class="container">
            <div class="content">
                <jsp:include page="ServiceSpecification.jsp"></jsp:include>
            </div>
            <jsp:include page="Bottom.jsp"></jsp:include>
        </div>
        <div id="pdf_out"></div>
    </body>
</html>
