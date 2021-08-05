<%-- TCIGBF --%>

<%-- 
    Document   : Error
    Created on : May 8, 2013, 11:17:57 AM
    Author     : Sonam Patel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <head>
        <title>JSP Error Page</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" >
    </head>
    <body>
        <div>
            <c:if test="${error ne null}">
                Technical Problem Occurred while processing Request. Please Try Later.
            </c:if>
        </div>
    </body>
</html>
