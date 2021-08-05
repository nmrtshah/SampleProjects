<%--
    Document   : serviceList
    Created on : Jul 1, 2011, 1:58:02 PM
    Author     : njuser
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <title>Service List</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <link href="css/main.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <table border="1" align="center" width="40%">
            <thead>
                <tr class="tbl_h1_bg">
                    <td colspan="100%">List of Services</td>
                </tr>
                <tr class="tbl_h1_bg">
                    <td>Project</td>
                    <td>ServiceName</td>
                    <td>ServiceClass</td>
                    <td>Server</td>
                    <td>URL</td>
                </tr>
            </thead>
            <tbody align="left">
				<tr>
                    <td>ACLNEW</td>
                    <td>ACLAgentWS</td>
                    <td>DPAuthenticationResultImplService</td>
                    <td>njapps:8081</td>
                    <td><a href="http://njapps5:8081/ACLNEW/ws/ACLAgentWS?wsdl" target="_blank">DPAuthenticationResultImplService</a></td>
	            </tr>
				<tr>
                    <td>exchangetrax</td>
                    <td>tpinservice</td>
                    <td>TPINServiceImpService</td>
                    <td>njimage:8080</td>
                    <td><a href="http://njimage:8080/exchangetrax/ws/tpinservice?wsdl" target="_blank">TPINServiceImpService</a></td>
	            </tr>
                <c:forEach items="${serviceList}" var="s">
                    <tr>
                        <td>${s.project}</td>
                        <td>${s.serviceName}</td>
                        <td>${s.serviceClass}</td>
                        <td>${s.server}</td>
                        <td><a href="${s.url}" target="_blank">${s.serviceClass}</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </body>
</html>
