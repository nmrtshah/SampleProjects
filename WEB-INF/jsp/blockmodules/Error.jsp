<%-- 
    Document   : Error
    Created on : 24 Feb, 2016, 10:24:21 AM
    Author     : Jigna Patel
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>JSP Error Page</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    </head>
    <body>
        <div>
            <c:if test="${error ne null}">
                Technical Problem Occurred while processing Request. Please Try Later.
            </c:if>
        </div>
    </body>
</html>