<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="report_content1" id="rpt">
    <table align="center" border="0" cellpadding="0" cellspacing="2" id="captiontable" class="table_subcaption" width="100%">
        <tr>
            <td align="right" class="report_content_caption">
                <c:if test="${type eq 'soap'}">Download WebService zip file : </c:if>
                <c:if test="${type eq 'rest'}">Download WebService xls file : </c:if>
            </td>
            <td colspan="2" class="report_content_value">
                <c:if test="${type eq 'soap'}">
                    <a href="webservicedoc.fin?cmdAction=downloadFile&amp;fileName=${zipPath}.zip&type=${type}">${zipPath}.zip</a>
                </c:if>
                <c:if test="${type eq 'rest'}">
                    <a href="webservicedoc.fin?cmdAction=downloadFile&amp;fileName=${zipPath}.xls&type=${type}">${zipPath}.xls</a>
                </c:if>
            </td>
        </tr>
    </table>
</div>