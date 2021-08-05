<%-- 
    Document   : ServiceSpecification
    Created on : Feb 22, 2012, 12:01:11 PM
    Author     : Sonam Patel
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<form name="MenuForm" id="MenuForm" method="post" action="" enctype="multipart/form-data">
    <%--ServiceSpecification--%>
    <div class="container" id="ServiceSpecification">
        <div class="content" id="menuLoader1">
            <div class="menu_new">
                <div class="menu_caption_bg">
                    <div class="menu_caption_text">Service Specification</div>
                </div>
                <table align="center">
                    <tr>
                        <td width="25%" align="right">
                            <sup style="color: red">*</sup>Project Name :
                        </td>
                        <td width="75%">
                            <select id="cmbProjectName" name="cmbProjectName" tabindex="1">
                                <option value="-1" selected>-- Select Project Name --</option>
                                <c:forEach items="${projects}" var="project" varStatus="loop">
                                    <option value="${values[loop.index]}">${projects[loop.index]}</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            <sup style="color: red">*</sup>Module Name :
                        </td>
                        <td>
                            <input type="text" tabindex="2" id="txtModuleName" name="txtModuleName" />
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            Request No :
                        </td>
                        <td>
                            <input type="text" id="txtReqNo" tabindex="3" name="txtReqNo" />
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            <sup style="color: red">*</sup>Interface File :
                        </td>
                        <td>
                            <input type="file" id="interfaceFile" name="interfaceFile" class="input_brows" tabindex="4" />
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td align="center">
                            <input class="button" type="button" id="btnFinish" name="btnFinish" value="Finish" tabindex="5" onclick="showFinish(this.form)" />
                            <input class="button" type="button" id="btnCancel" name="btnCancel" value="Cancel" tabindex="6" onclick="window.history.back()" />
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td>
                            <div id="divError">${errors}</div>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
</form>
