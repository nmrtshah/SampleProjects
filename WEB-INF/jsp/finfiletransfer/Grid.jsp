<%-- TCIGBF --%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${param.cmdAction eq 'getMenu'}">
    <div id="report_display" style="display: block; padding:30px 0 10px;">
        <table id="mstgrid" width="100%"></table>
        <div id="pagermstgrid" style="height:50px"></div>
        <div id="errorBox"></div>
        <h3><div id="viewAll" style="display: none" align="right">&nbsp;</div></h3>
    </div>
    <div id="detail" style="display: none">
    </div>
</c:if>
<c:if test="${param.cmdAction eq 'getDetail'}">
    <br><table align="center" class="ui-jqgrid-btable" cellspacing="0" cellpadding="0" border="1" 
               role="grid" aria-multiselectable="false" aria-labelledby="gbox_mstgrid" >
        <tr>
            <td><b>File Name</b></td>
        </tr>
        <c:forEach items="${detail}" var="detail1">
            <tr>
                <td>${detail1.FILE_NAME}</td>
            </tr>
        </c:forEach>
    </table>
</c:if>
<c:if test="${param.cmdAction eq 'execute'}">
    <center> <div style='color:#FF0000;'> <b> ${result} </b> </div></center>
</c:if>
<c:if test="${param.cmdAction eq 'cancelReq'}">
    <center> <div style='color:#FF0000;'> <b> ${result} </b> </div></center>
</c:if>    

