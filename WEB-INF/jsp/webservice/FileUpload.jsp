<%-- 
    Document   : FileUpload
    Created on : Aug 24, 2012, 12:25:23 PM
    Author     : Sonam Patel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Beans Upload Page</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <link rel="stylesheet" type="text/css" href="${finlib_path}/resource/main_offline.css" />
        <script type="text/javascript" src="javascript/ajaxfunctions.js"></script>
        <script type="text/javascript" src="javascript/WebServiceValidations.js"></script>
        <script type="text/javascript" src="${finlib_path}/resource/ajax.js"></script>
    </head>
    <body>
        <c:choose>
            <c:when test="${process eq 'beanupload'}">
                <form name="BeanUploadForm" id="BeanUploadForm" method="post" action="" enctype="multipart/form-data">
                    <%--BeanUploadSpecification--%>
                    <div class="container" id="BeanUploadSpecification">
                        <div class="content" id="menuLoader2">
                            <div class="menu_new">
                                <div class="menu_caption_bg">
                                    <div class="menu_caption_text">Bean Upload Specification</div>
                                </div>
                                <table align="center">
                                    <c:forEach items="${beans}" var="bean" varStatus="loop">
                                        <tr>
                                            <td align="right">
                                                <sup style="color: red">*</sup>${bean} :
                                            </td>
                                            <td>
                                                <input type="hidden" id="bean${loop.index}" name="bean${loop.index}" value="${bean}" />
                                                <input type="file" id="beanFile" name="beanFile" class="input_brows" />
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    <tr>
                                        <td></td>
                                        <td align="center">
                                            <input class="button" type="button" id="btnFinish" name="btnFinish" value="Finish" onclick="lastFinish(this.form);" />
                                        </td>
                                    </tr>
                                </table>
                            </div>
                        </div>
                    </div>
                    <input type="hidden" id="beans" name="beans" />
                    <input type="hidden" id="txtSrno" name="txtSrno" value="${SRNO}" />
                </form>
            </c:when>
        </c:choose>
    </body>
</html>
