<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<c:choose>
    <c:when test="${action eq 'addLoader'}">
        <form name="filediraddform" id="finserverentryform" method="post" action="">
            <div class="menu_caption_bg cursor-pointer" onclick="javascript:hide_menu('show_hide21', 'rpt', 'nav_show', 'nav_hide')" style="width: 1300px;">
                <div id="divAddCaption" class="menu_caption_text">Add</div>
                <span><a href="javascript:void(0)" name="show_hide21" id="show_hide21" class="nav_hide"></a></span>
            </div>
            <div class="report_content" id="rpt" style="width: 1300px;">
                <table align="center" border="0" cellpadding="0" cellspacing="2" class="menu_subcaption" width="100%">
                    <tr>
                        <td align="right" class="report_content_caption">
                            Server<sup class="astriek">*</sup> :
                        </td>
                        <td class="report_content_value">
                            <select class="custom_combo_showSSWT" id="servername" name="servername" tabindex="1" onchange="document.getElementById('process').selectedIndex = 0;
                                    javascript:getLocation()">
                                <option value="-1">-- Select Server Name --</option>
                                <optgroup label="Prod">
                                    <option value="prodhoweb1.nj">prodhoweb1.nj</option>
                                    <option value="prodhoweb2.nj">prodhoweb2.nj</option>
                                   <option value="prodhoweb3.nj">prodhoweb3.nj</option>
                                    <option value="prodhoweb4.nj">prodhoweb4.nj</option>
                                    <option value="prodhoweb1rep.nj">prodhoweb1rep.nj</option>
                                    <option value="prodhoweb2rep.nj">prodhoweb2rep.nj</option>
                                   <option value="prodhoweb3rep.nj">prodhoweb3rep.nj</option>
                                    <option value="prodhoweb4rep.nj">prodhoweb4rep.nj</option>
                                </optgroup>
                                <optgroup label="Dev">
                                    <option value="devhoweb1.nj">devhoweb1.nj</option>
                                    <option value="devhoweb2.nj">devhoweb2.nj</option>
                                    <option value="devhoweb3.nj">devhoweb3.nj</option>
                                    <option value="devhoweb4.nj">devhoweb4.nj</option>
                                </optgroup>
                                <optgroup label="Test">
                                    <option value="testhoweb1.nj">testhoweb1.nj</option>
                                    <option value="testhoweb2.nj">testhoweb2.nj</option>
                                    <option value="testhoweb3.nj">testhoweb3.nj</option>
                                    <option value="testhoweb4.nj">testhoweb4.nj</option>
                                    <option value="testhoweb1rep.nj">testhoweb1rep.nj</option>
                                    <option value="testhoweb2rep.nj">testhoweb2rep.nj</option>
                                    <option value="testhoweb3rep.nj">testhoweb3rep.nj</option>
                                    <option value="testhoweb4rep.nj">testhoweb4rep.nj</option>
                                </optgroup>

                                <optgroup label="StorageBox">
                                    <option value="devhosb1.nj">devhosb1.nj</option>
                                    <option value="testhosb1.nj">testhosb1.nj</option>
                                    <option value="prodhosb1.nj">prodhosb1.nj</option>
                                </optgroup>

                            </select>
                            <input type="hidden" name="desigCode" value="${desigCode}">
                        </td>
                    </tr>
                    <tr>
                        <td align="right" class="report_content_caption">
                            Project <sup class="astriek">*</sup>:
                        </td>
                        <td class="report_content_value">
                            <select class="custom_combo_showSSWT" id="project" name="project" tabindex="2" >
                                <option value="-1">-- Select Project Name --</option>
                                <c:forEach items="${projectList}" var="b" >
                                    <option value="${b.PRJ_ID}|${b.PRJ_NAME}|${b.DOMAIN_NAME}">${b.PRJ_NAME} - ${b.DOMAIN_NAME}</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" class="report_content_caption">
                            Process <sup class="astriek">*</sup>:
                        </td>
                        <td class="report_content_value">
                            <select class="custom_combo_showSSWT " id="process" name="process" tabindex="2" onchange="javascript:getLocation()">
                                <option value="-1">-- Select Process --</option>
                                <option value="Create">Create</option>
                                <option value="CheckExist">Check Exist</option> 
                                <option value="Delete">Delete</option>
                                <option value="GetFile">Get File</option>
                                <option value="GetList">Get List</option>
                            </select>

                        </td>
                    </tr>
                    <tr>
                        <td align="right" class="report_content_caption">
                            Location <sup class="astriek">*</sup>:
                        </td>
                        <td class="report_content_value">
                            <select class="custom_combo_showSSWT " id="pathCombo" name="pathCombo" tabindex="2">
                                <option value="-1">-- Select Location --</option>
                                <option value="tomcat">tomcat (/opt/apache-tomcat1/webapps/)</option>
                                <option value="apache">apache (/var/www/html/)</option>
                                <option value="storage_box">storage_box (/opt/application_storage/storage_box/)</option>
                                <option value="temp_files">temp_files (/opt/application_storage/temp_files/)</option>
                                <option value="email_attachment">email_attachment (/opt/application_storage/email_attachment/)</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" class="report_content_caption">
                            Path <sup class="astriek">*</sup>:
                        </td>
                        <td id="tdTxtInput">
                            <input type="text" id="path" name="path" value="" tabindex="2"/>
                        </td>
                        <td id="tdTextArea" style="display: none;">
                            <textarea id="pathlist" tabindex="2" name="pathlist" id="filelist" style="width: 400px;" rows="4"></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" class="report_content_caption">
                            Purpose <sup class="astriek">*</sup>:
                        </td>
                        <td class="report_content_value">
                            <input type="text" id="purpose" name="purpose" tabindex="2"/>
                        </td>
                    </tr>
                    <tr>
                        <td align="center" colspan="2">
                            <input class="button" type="button" id="btnAdd" name="btnAdd" Value="Add" onclick="javascript: validateData();" tabindex="2"/>
                            <input class="button" type="reset" id="btnReset" name="btnReset" Value="Reset" tabindex="2" onclick="resetData();"/>
                        </td>
                    </tr>
                </table>
            </div>
            <div style="display: none" id="msg"></div>
        </form>
    </c:when>
    <c:when test="${action eq 'insertData'}">
        ${msg}
    </c:when>
</c:choose>
