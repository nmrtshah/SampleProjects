<%-- 
    Document   : Links
    Created on : Jul 18, 2012, 1:51:32 PM
    Author     : Sonam Patel
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Link Page</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <link rel="stylesheet" type="text/css" href="${finlib_path}/resource/main_offline.css" />
        <script type="text/javascript" src="javascript/WebServiceValidations.js"></script>
    </head>
    <body>
        <div class="container">
            <div class="content">
                <div class="menu_new">
                    <div class="menu_caption_bg">
                        <div class="menu_caption_text">Code</div>
                    </div>
                    <div align="center" id="divFinish">
                        <c:if test="${codePath ne null}">
                            Module Successfully Generated <br/>
                            Your Web Service Reference No. is ${SRNO} <br/>
                            <a href="generated/${codePath}" >Download CodeFile</a>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
        <jsp:include page="Bottom.jsp"></jsp:include>
    </body>
</html>
