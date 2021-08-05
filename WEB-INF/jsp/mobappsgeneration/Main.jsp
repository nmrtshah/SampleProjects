<%-- 
    Document   : main
    Created on : Feb 22, 2012, 12:01:11 PM
    Author     : njuser
--%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <title>MobileApps</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <link rel="stylesheet" type="text/css" href="${finlib_path}/resource/main_offline.css" />

        <link rel="stylesheet" type="text/css" href="${finlib_path}/jquery/jquery-ui-1.8.13.custom/css/smoothness/jquery-ui-1.8.13.custom.css"/>
        <script type="text/javascript" src="${finlib_path}/resource/common_functions.js"></script>

        <script type="text/javascript" src="javascript/ajaxfunctions.js"></script>
        <script type="text/javascript" src="${finlib_path}/resource/ajax.js"></script>
        <script type="text/javascript" src="javascript/MobAppValidations.js"></script>
    </head>
    <body>
        <div class="container">
            <div class="content">
                <jsp:include page="MobAppsSpecification.jsp"></jsp:include>
            </div>
            <jsp:include page="Bottom.jsp"></jsp:include>
        </div>
    </body>
</html>
