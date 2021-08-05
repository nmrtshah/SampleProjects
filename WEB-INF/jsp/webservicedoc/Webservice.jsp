
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<form name="webserviceform" id="webserviceform" method="post" action="">
    <div class="report_content1" id="rpt">
        <table align="center" border="0" cellpadding="0" cellspacing="2" id="captiontable" class="table_subcaption" width="100%">
            <tr>
                <td align="right" class="report_content_caption">
                    Web Service Type<sup class="astriek">*</sup> :
                </td>
                <td colspan="2" class="report_content_value">
                    <label><input type="radio" name="rdowebchoice" id="rdosoap" value="soap" checked="" onclick="javascrpt: serviceType(this.value);">SOAP</label>
                    <label><input type="radio" name="rdowebchoice" id="rdorest" value="rest" onclick="javascrpt: serviceType(this.value);">REST</label>
                </td>
            </tr>
            <tr>
                <td align="right" class="report_content_caption">
                    Producer Project<sup class="astriek">*</sup> :
                </td>
                <td colspan="2" class="report_content_value">
                    <select class="custom_combo_showSSWT" id="producerPro" name="producerPro" tabindex="1"
                            <c:if test="${type eq 'soap'}">onchange="javascript:refreshConsumerName(this)" </c:if>
                            <c:if test="${type eq 'rest'}">onchange="javascript:setWebServiceURLName(this)" </c:if>>
                                <option value="-1">-- Select Producer Project --</option>
                            <c:forEach items="${projectList}" var="b" >
                                <c:if test="${type eq 'soap'}">
                                    <option value="${b.PRJ_NAME}" >${b.PRJ_NAME}</option>
                                </c:if>
                                <c:if test="${type eq 'rest'}">
                                    <c:if test="${b.PRJ_NAME ne '---'}">
                                        <option value="${b.DOMAIN_NAME}" >${b.PRJ_NAME} - ${b.DOMAIN_NAME}</option>
                                    </c:if>
                                </c:if>
                            </c:forEach>
                    </select>
                </td>
            <input type="hidden" id="projectName" name="projectName" value="">
            </tr>
            <c:if test="${type eq 'soap'}">
                <tr>
                    <td align="right" class="report_content_caption">
                        Consumer Project<sup class="astriek">*</sup> :
                    </td>
                    <td colspan="2" class="report_content_value">
                        <select id="consumerPro" name="consumerPro" multiple="multiple" tabindex="2">
                            <!--<option value="-1">-- Select Consumer Project --</option>-->
                            <c:forEach items="${projectList}" var="b" >
                                <option value="${b.PRJ_NAME}" >${b.PRJ_NAME}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
            </c:if>
            <tr>
                <td align="right" class="report_content_caption">
                    WebService Name<sup class="astriek">*</sup> :
                </td>
                <td colspan="2" class="report_content_value" style="width:32%">
                    <input type="text" id="webserviceName" name="webserviceName" value="" tabindex="2"
                           <c:if test="${type eq 'soap'}"> onchange="javascript:toUpperFirstLetter(this);validateWebserviceName(this);"</c:if>
                           <c:if test="${type eq 'rest'}"> onkeydown="javascript:validateRestWebserviceName()"</c:if>/>
                    </td>
                </tr>
            <c:if test="${type eq 'soap'}">
                <tr>
                    <td align="right" class="report_content_caption">
                        Method Name<sup class="astriek">*</sup> :
                    </td>
                    <td colspan="2" class="report_content_value">
                        <input type="text" id="methodName" name="methodName" value="" tabindex="2" onchange="javascript:toLowerFirstLetter(this)"/>
                    </td>
                </tr>
            </c:if>
            <c:if test="${type eq 'rest'}">
                <tr>
                    <td align="right" class="report_content_caption">
                        WebService URL<sup class="astriek">*</sup> :
                    </td>
                    <td colspan="2" class="report_content_value">
                        <input type="text" id="webserviceURL" name="webserviceURL" value=""  onchange="validateRestWebserviceURL(this)"/>
                        Example: wfm/ws/endpoint
                    </td>
                </tr>

                <tr>
                    <td align="right" class="report_content_caption">
                        HTTP Method Allow<sup class="astriek">*</sup> :
                    </td>
                    <td colspan="3" class="report_content_value">
                        <label><input type="checkbox" class="checkbox" name="httpMethod" value="Get" > Get </label>
                        <label><input type="checkbox" class="checkbox" name="httpMethod" value="Post" > Post </label>
                        <label><input type="checkbox" class="checkbox" name="httpMethod" value="Put" > Put </label>
                    </td>
                </tr>
            </c:if>
            <tr>
                <td align="right" class="report_content_caption">
                    Numbers of IN Parameters<sup class="astriek">*</sup> :
                </td>
                <td colspan="2" <c:if test="${type eq 'soap'}"> class="report_content_value"</c:if>>
                    <c:if test="${type eq 'soap'}">
                        <select id="inParameters" name="inParameters" tabindex="2" onchange="javascript:insertParam(this.value, 'inparamtable', 'inParamName', 'in')">
                            <option value="0">0</option>
                            <option value="1">1</option>
                        </select>
                    </c:if>
                    <c:if test="${type eq 'rest'}">
                        <input type="number" style="height: 30px !important;" id="inParameters" name="inParameters" min="0"
                               value="0" onchange="insertParamRest(this.value, 'inparamtable', 'inParamName', 'in')">
                    </c:if>
                </td>
            </tr>

            <tr>
                <td align="right" class="report_content_caption">
                    Numbers of OUT Parameters<sup class="astriek">*</sup> :
                </td>
                <td colspan="2" <c:if test="${type eq 'soap'}"> class="report_content_value"</c:if>>
                    <c:if test="${type eq 'soap'}">
                        <select id="outParameters" name="outParameters" onchange="javascript:insertParam(this.value, 'outparamtable', 'outParamName', 'out')">
                            <option value="0">0</option>
                            <option value="1">1</option>
                        </select>
                    </c:if>
                    <c:if test="${type eq 'rest'}">
                        <input type="number" style="height: 30px !important;" id="outParameters" name="outParameters" min="0"
                               value="0"  onchange="insertParamRest(this.value, 'outparamtable', 'outParamName', 'out')">
                    </c:if>
                </td>
            </tr>
            <tr>
                <td align="center" colspan="3" style="width: 100%">
                    <!--<div id="parameter_load" align="center" style="display: block;width: 100%" >-->
                    <%@include file="../webservicedoc/Parameter.jsp" %>
                    <!--</div>-->
                </td>
            </tr>
            <tr id="buttons">
                <td align="center" colspan="3">
                    <input class="button" type="button" id="btnAdd" name="btnAdd" Value="Generate xls" onclick="javascript: validateData();" />
                    <input class="button" type="button" id="btnReset" name="btnReset" Value="Reset" onclick="javascript:WebService()"/>
                </td>
            </tr>
        </table>
    </div>
    <input type="hidden" id="beanAllTable" name="beanAllTable"/>
    <input type="hidden" id="beanType" name="beanType"/>
    <input type="hidden" id="beanTableData" name="beanTableData"/>
</form>
