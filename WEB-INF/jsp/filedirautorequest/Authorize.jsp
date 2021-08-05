<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="common" uri="http://www.njtechnologies.in/tags/filebox" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<c:choose>
    <c:when test="${action eq 'authorizeMainPage'}">
        <form name="filedirauthorizeform" id="finserverentryform" method="post" action="">
            <div class="menu_caption_bg cursor-pointer" onclick="javascript:hide_menu('show_hide4', 'authorizeRpt', 'nav_show', 'nav_hide')" style="width: 1300px;">
                <div id="divAddCaption" class="menu_caption_text">Authorize</div>
                <span><a href="javascript:void(0)" name="show_hide4" id="show_hide4" class="nav_hide"></a></span>
            </div>
            <div id="authorizeRpt" style="width: 1300px;">
                <table cellpadding="0" cellspacing="0" width="100%" class="dhtml-main-report">
                    <tbody id="maingridDiv">
                        <tr>
                            <td valign="top">
                                <div id="authorizegridbox" style="width:100%;background-color:white;margin: 5px auto;"></div>
                            </td>
                        </tr>
                    </tbody>
                </table>
                <div class="report_content" id="rpt">
                    <input class="button" type="button" id="btnAuthorize" name="btnAuthorize" Value="Authorize" onclick="javascript: AuthorizeUpdate()" />
                    <input class="button" type="button" id="btnReject" name="btnReject" Value="Reject" onclick="javascript: RejectUpdate()" />
                    <input class="button" type="reset" id="btnReset" name="btnReset" Value="Reset"/>
                </div>
            </div>
            <label style="font-weight: normal; color: red;">Note : This Report will display 20 Records only</label>
            <div style="display: none" id="update_msg"></div>
            <div style="display: none" id="reject_msg"></div>
        </form>
    </c:when>
    <c:when test="${action eq 'authorizeUpdate'}">
        ${update_msg}
    </c:when>
    <c:when test="${action eq 'rejectUpdate'}">
        ${reject_msg}
    </c:when>
</c:choose>
