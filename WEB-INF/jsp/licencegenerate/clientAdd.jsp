<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<c:choose>
    <c:when test="${action eq 'clientAdd'}">
        <form name="clientaddform" id="clientaddform" method="post" action="">
            <div class="menu_caption_bg cursor-pointer" style="width: 100%" onclick="javascript:hide_menu('show_hide2', 'mainclient', 'nav_show', 'nav_hide')">
                <div id="divAddCaption" class="menu_caption_text">Client Add</div>
                <span><a href="javascript:void(0)" name="show_hide2" id="show_hide2" class="nav_hide"></a></span>
            </div>
            <div class="report_content" id="mainclient">
                <table align="center" border="0" cellpadding="0" cellspacing="2" class="menu_subcaption" width="100%">
                    <tr>
                        <td align="right" class="report_content_caption">
                            Client Name <sup class="astriek">*</sup>: 
                        </td>
                        <td class="report_content_value">
                            <input type="text" id="clientname" name="clientname" value="" maxlength="50" tabindex="2"/>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" class="report_content_caption">
                            Address <sup class="astriek">*</sup>: 
                        </td>
                        <td class="report_content_value">
                            <input type="text" id="address" name="address" value="" maxlength="100" tabindex="2"/>
                        </td>
                    </tr>
                    <tr>
                        <td align="center" colspan="2">
                            <input class="button" type="button" id="clientAdd" name="clientAdd" Value="Add" onclick="javascript: validateClientData();" tabindex="2"/>
                            <input class="button" type="reset" id="btnReset" name="btnReset" Value="Reset" tabindex="2"/>
                        </td>
                    </tr>
                </table>
            </div>
            <div style="display: none" id="msg"></div>
        </form>
    </c:when>
    <c:when test="${action eq 'insertClient'}">
        ${msg}
    </c:when>
</c:choose>
