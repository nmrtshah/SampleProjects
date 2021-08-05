<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:choose>
    <c:when test="${process eq 'main'}">
        <form name="MenuForm" id="MenuForm" method="post" action="" enctype="multipart/form-data">
            <%--MobileAppsSpecification--%>
            <div class="container" id="MobAppsSpecification">
                <div class="content" id="menuLoader1">                    
                    <div class="menu_new">
                        <div class="menu_caption_bg">
                            <div class="menu_caption_text">Mobile Application Specification</div>
                        </div>
                        <table align="center">
                            <tr>
                                <td width="32%" align="right">
                                    <sup style="color: red">*</sup>Application Name :
                                </td>
                                <td width="68%">
                                    <input type="text" tabindex="1" id="txtAppName" name="txtAppName" maxlength="25" onblur="getVersion()" />
                                </td>
                            </tr>
                            <tr>
                                <td align="right">
                                    <sup style="color: red">*</sup>Version :
                                </td>
                                <td>
                                    <input type="text" tabindex="2" id="txtVersion" name="txtVersion" />
                                    <div id="divVersion" style="display: none"></div>
                                </td>
                            </tr>
                            <tr>
                                <td align="right">
                                    <sup style="color: red">*</sup>Package :
                                </td>
                                <td>
                                    <input type="text" tabindex="3" id="txtPackage" name="txtPackage" />
                                </td>
                            </tr>
                            <tr>
                                <td align="right">
                                    <sup style="color: red">*</sup>Welcome File : finhtml/
                                </td>
                                <td>
                                    <input type="text" id="txtWelcomeFile" tabindex="4" name="txtWelcomeFile" value="index.html" />
                                </td>
                            </tr>
                            <tr>
                                <td align="right">
                                    <sup style="color: red">*</sup>Source(finhtml) :
                                </td>
                                <td>
                                    <input type="file" id="fSource" name="fSource" class="input_brows" tabindex="5" />
                                </td>
                            </tr>
                            <tr>
                                <td align="right">
                                    <sup style="color: red">*</sup>Target OS :
                                </td>
                                <td align="center" colspan="3">
                                    <input type="checkbox" id="chkAndroid" name="chkAndroid" tabindex="6" />Android
                                    <input type="checkbox" id="chkBBerry" name="chkBBerry" tabindex="7" />BlackBerry
                                    <input type="checkbox" id="chkSymbian" name="chkSymbian" tabindex="8" />Symbian
                                </td>
                            </tr>
                            <tr>
                                <td></td>
                                <td align="center">
                                    <input class="button" type="button" id="btnGenerate" name="btnGenerate" value="Generate Apps" tabindex="9"
                                           onclick="javascript: return generateApps(this.form)" />
                                    <input class="button" type="button" id="btnCancel" name="btnCancel" value="Cancel" tabindex="10" onclick="javascript: onCancel()" />
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
        </form>
    </c:when>
    <c:when test="${process eq 'getversion'}">
        ${version}
    </c:when>
</c:choose>
