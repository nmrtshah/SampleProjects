<%-- TCIGBF --%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="menu_caption_bg">
    <div class="menu_caption_text">Filter</div>
</div>
<div class="report_content"> 
    <table align="center" border="0" cellpadding="0" cellspacing="2" class="menu_subcaption" width="100%">
        <tr>
            <td align="right" class="report_content_caption">
                Request Id :
            </td>
            <td class="report_content_value">
                <input type="text" id="txtReqId" name="txtReqId" maxlength="50">
                <span style="color:grey"><i>Comma separated values are valid.</i></span>
            </td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                -- OR --
            </td>
        </tr>        
        <tr>
            <td align="right" class="report_content_caption">
                Source-Destination : 
            </td>
            <td class="report_content_value">
                <select class="custom_combo_showSSWT " id="srcdest" name="srcdest" tabindex="1">
                    <option value="-1">-- Select Source-Destination --</option>
                    <optgroup label="Dev-Test">
                        <option value="devhoweb1.njtechdesk.com-testhoweb1.njtechdesk.com">devhoweb1.nj - testhoweb1.nj</option>
                        <option value="devhoweb2.njtechdesk.com-testhoweb2.njtechdesk.com">devhoweb2.nj - testhoweb2.nj</option>
                        <option value="devhoweb3.njtechdesk.com-testhoweb3.njtechdesk.com">devhoweb3.nj - testhoweb3.nj</option>
                        <option value="devhoweb4.njtechdesk.com-testhoweb4.njtechdesk.com">devhoweb4.nj - testhoweb4.nj</option>
                        <option value="devimfsp.nj:8090-testimfsp.nj:8090">devexchange.nj - testexchange.nj</option>
                        <option value="devpaymentagg.nj:8090-testpaymentagg.nj:8090">devpaymentagg.nj - testpaymentagg.nj</option>
                    </optgroup>
                    <optgroup label="Test-Prod">
                        <option value="testhoweb1.njtechdesk.com-prodhoweb1.njtechdesk.com">testhoweb1.nj - prodhoweb1.nj</option>
                        <option value="testhoweb2.njtechdesk.com-prodhoweb2.njtechdesk.com">testhoweb2.nj - prodhoweb2.nj</option>
                        <option value="testhoweb3.njtechdesk.com-prodhoweb3.njtechdesk.com">testhoweb3.nj - prodhoweb3.nj</option>
                        <option value="testhoweb3.njtechdesk.com-prodhoweb4.njtechdesk.com">testhoweb3.nj - prodhoweb4.nj</option>
                        <option value="testhoweb4.njtechdesk.com-prodhoweb4.njtechdesk.com">testhoweb4.nj - prodhoweb4.nj</option>
                        <option value="testpaymentagg.nj:8090-prodpaymentagg.nj:8090">testpaymentagg.nj - prodpaymentagg.nj</option>
                    </optgroup>
                </select>
            </td>
        </tr>
        <tr>
            <td align="right" class="report_content_caption">
                Project : 
            </td>
            <td class="report_content_value">
                <select id="project" name="project" tabindex="2" >
                    <option value="-1">-- Select Project --</option>
                    <c:forEach items="${projects}" var="project">
                        <option value="${project.PRJ_ID}-${project.DOMAIN_NAME}">${project.PRJ_NAME} - ${project.DOMAIN_NAME}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <td align="right" class="report_content_caption">
                Employee : 
            </td>
            <td class="report_content_value">
                <select id="empcode" name="empcode" tabindex="1" >
                    <option value="-1">-- Select Employee --</option>
                    <c:forEach items="${empcodes}" var="empcode">
                        <option value="${empcode.EMP_CODE}">${empcode.EMP_NAME}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <td align="right" class="report_content_caption">
                Request No : 
            </td>
            <td class="report_content_value">
                <select id="viewreqno" name="viewreqno" tabindex="3" >
                    <option value="-1">-- Select Request No --</option>
                    <c:forEach items="${requests}" var="request">
                        <option value="${request.REQNO}">${request.REQNO}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <!--        <tr>
                    <td align="right" class="report_content_caption">
                        Request No : 
                    </td>
                    <td class="report_content_value">
                        <input type="text" id="view_reqno" name="view_reqno" tabindex="3" />
                    </td>
                </tr>-->
        <tr>
            <td align="right" class="report_content_caption">
                Purpose : 
            </td>
            <td class="report_content_value">
                <textarea rows="2" cols="2" id="viewpurpose" name="viewpurpose" tabindex="4" ></textarea>
            </td>
        </tr>
        <tr>
            <td align="right" class="report_content_caption">
                From Date : 
            </td>
            <td class="report_content_value">
                <input type="text" class="datepickerclass" id="fromdate" name="fromdate" value="${none}" tabindex="5" />
            </td>
        </tr>
        <tr>
            <td align="right" class="report_content_caption">
                To Date : 
            </td>
            <td class="report_content_value">
                <input type="text" class="datepickerclass" id="todate" name="todate" value="${none}" tabindex="6" />
            </td>
        </tr>
    </table>
</div>
