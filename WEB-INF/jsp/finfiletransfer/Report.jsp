<%-- TCIGBF --%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="common" uri="http://www.njtechnologies.in/tags/filebox" %>
<div class="report_content" id="rpt">
    <c:if test="${action eq 'Main'}">
        <div class="menu_caption_bg">
            <div id="divAddCaption" class="menu_caption_text">Report</div>
        </div>
    </c:if>

    <c:if test="${action eq 'Main'}">
        <table align="center" border="0" cellpadding="0" cellspacing="2" class="menu_subcaption" width="100%">
            <tr>
                <td align="right" class="report_content_caption" style="vertical-align: top" >
                    Source-Destination<sup class="astriek">*</sup> :
                </td>
                <td class="report_content_value">
                    <select class="custom_combo_showSSWT " id="cmbsrcdest" name="cmbsrcdest" tabindex="1" multiple="multiple" style="min-width: 200px; width: auto; height: 75px;">
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
                    Project <sup class="astriek">*</sup>:
                </td>
                <td class="report_content_value">
                    <select id="cmbprojectName" name="cmbprojectName" tabindex="2" >
                        <option value="-1">-- Select Project --</option>
                        <c:forEach items="${projects}" var="project">
                            <option value="${project.PRJ_ID}">${project.PRJ_NAME} - ${project.DOMAIN_NAME}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>

            <tr>
                <td align="right" class="report_content_caption">
                    File Name <sup class="astriek">*</sup>:
                </td>
                <td class="report_content_value">
                    <input type="text" name="txtFilename" id="txtFilename" tabindex="3">
                </td>
            </tr>
            <tr>
                <td style="text-align: right" class="astriek"><i>Note :</i></td>
                <td class="astriek"><i>Latest 500 matching entries will be displayed.</i></td>
            </tr>

            <tr>
                <td align="center" colspan="2">
                    <input class="button" type="button" id="btnReport" name="btnReport" Value="Report" onclick="javascript: validateReportData();" />
                    <input class="button" type="reset" id="btnReset" name="btnReset" Value="Reset" onclick="javascript: resetAll();"/>
                </td>
            </tr>

        </table>
    </c:if>
</div>
