<%-- TCIGBF --%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="report_text">Filter</div>
<div class="report_content" align="center">
    <table align="center" cellpadding="2" cellspacing="2" class="menu_subcaption" width="50%">
        <tr>
            <td class="report_content_caption">
                Group Name : 
            </td>
            <td class="report_content_value">
                <select id="groupname"  name="groupname"  tabindex="1"  >
                    <option value="-1">-- Select Group Name --</option>
                    <c:forEach items="${groupnameList}" var="lst">
                        <option value="${lst.GROUP_NAME}">${lst.GROUP_NAME}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
    </table>
</div>
