<%-- 
    Document   : newjsp
    Created on : 26 Aug, 2013, 2:17:37 PM
    Author     : njuser
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="${finlib_path}/resource/main_offline.css" />
        <script type="text/javascript" src="${finlib_path}/resource/validate.js"></script>
        <script type="text/javascript" src="${finlib_path}/resource/serverCombo.js"></script>
        <script type="text/javascript" src="${finlib_path}/resource/ajax.js"></script>
        <script type="text/javascript" src="${finlib_path}/resource/common_functions.min.js"></script>
        <script type="text/javascript" src="javascript/dbmdi.js" ></script>
        <title>Database Metadata Inspector</title>
    </head>
    <body onload="javascript : onloadMyFunction();">
         <div class="container">
            <div class="content">
                <jsp:include page="dbMetadataMenu.jsp"></jsp:include>
            </div>
        </div>
    </body>
</html>
