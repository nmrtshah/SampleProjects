<%-- 
    Document   : seleniumscript
    Created on : Mar 6, 2012, 4:30:26 PM
    Author     : njuser
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <title>Selenium Script</title> 
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    </head>
    <body>    
        <form name="seleniumscriptform" action="" method="POST">
            <div  name="disp" id="divdownloadzip" align="center" style="color: navy;font-size: medium;font-weight: bolder;font-style: normal;padding-left: 25px">
                <br> Selenium Script Generated Successfully<br> <br>
                 <a href="generated/${getzipfile}">Download ZIP File</a><br><br>
                 <a href="scriptgen.fin?cmdAction=showRequest&pageLoad=Add" >Back</a>
            </div>
        </form>

    </body>
</html>
