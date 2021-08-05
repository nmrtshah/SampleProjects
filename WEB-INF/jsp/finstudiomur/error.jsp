<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP error Page</title>
    </head>
    <body>
        <c:if test="${error ne null}">
            Error : ${error}
        </c:if>
    </body>
</html>
