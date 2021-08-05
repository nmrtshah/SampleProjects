<%-- 
    Document   : ajaxetljob
    Created on : 25 Apr, 2017, 4:07:44 PM
    Author     : Siddharth Patel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:choose>
    <c:when test="${action eq 'getReport'}">
        ${data}
    </c:when>    
    <c:when test="${action eq 'addETL'}">
        <input type="hidden" value="${status}" id="statusinsert" name="statusinsert" />
    </c:when>    
</c:choose>
