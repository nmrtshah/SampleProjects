<%-- 
    Document   : Links
    Created on : Jul 18, 2012, 1:51:32 PM
    Author     : njuser
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Link Page</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <link rel="stylesheet" type="text/css" href="${finlib_path}/resource/main_offline.css" />
        <script type="text/javascript" src="javascript/MobAppValidations.js"></script>
    </head>
    <body>
        <div class="container">
            <div class="content">
                <table class="tbl_h4_bg1 rpt_tbl" align="center" width="60%">
                    <tr class="report_caption">
                        <td colspan="3" align="center">Your Mobile Application's Reference No. is ${SRNO}</td>
                    </tr>
                    <tr>
                        <td style="width: 20%"></td>
                        <td style="width: 40%">Download Application Code</td>
                        <td style="width: 40%">Download Application</td>
                    </tr>
                    <c:if test="${androidapp ne null or androidbuild ne null}">
                        <tr>
                            <td>Android: </td>
                            <td>
                            <c:if test="${androidapp ne null}">
                                <a href="${androidapp}" >${andapp}</a>
                            </c:if>
                                &nbsp;
                            </td>
                            <td>
                            <c:if test="${androidbuild ne null}">
                                <a href="${androidbuild}" >${andbuild}</a>
                            </c:if>
                                &nbsp;
                            </td>
                        </tr>
                    </c:if>
                    <c:if test="${blackberryapp ne null or blackberrybuild ne null}">
                        <tr>
                            <td>BlackBerry: </td>
                            <td>
                            <c:if test="${blackberryapp ne null}">
                                <a href="${blackberryapp}" >${bbapp}</a>
                            </c:if>
                                &nbsp;
                            </td>                            
                            <td>
                            <c:if test="${blackberrybuild ne null}">
                                <a href="${blackberrybuild}" >${bbbuild}</a>
                            </c:if>
                                &nbsp;
                            </td>
                        </tr>
                    </c:if>
                    <c:if test="${symbianapp ne null or symbianbuild ne null}">
                        <tr>
                            <td>Symbian: </td>
                            <td>
                            <c:if test="${symbianapp ne null}">
                                <a href="${symbianapp}" >${symapp}</a>
                            </c:if>
                                &nbsp;
                            </td>
                            <td>
                            <c:if test="${symbianbuild ne null}">
                                <a href="${symbianbuild}" >${symbuild}</a>
                            </c:if>
                                &nbsp;
                            </td>
                        </tr>
                    </c:if>
                    <tr>
                        <td align="center" colspan="3">
                            <a href="mobapps.fin">Back</a>
                        </td>
                    </tr>
                </table>
            </div>
            <jsp:include page="Bottom.jsp"></jsp:include>
        </div>
    </body>
</html>
