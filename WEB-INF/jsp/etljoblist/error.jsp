<%-- 
    Document   : error
    Created on : 25 Apr, 2017, 4:08:44 AM
    Author     : Siddharth Patel
--%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div id="report_error" class="report-table">
    <table border="0" width="100%" class="menu_subcaption"
           style="margin: auto;">
        <c:if test="${error ne null}">
            <tr align="center">
                <td class="report_content_text" style="color: red">
                    Technical Problem Occurred while processing Request. Please Try Later.
                </td>
            </tr>
        </c:if>
    </table>
</div>

