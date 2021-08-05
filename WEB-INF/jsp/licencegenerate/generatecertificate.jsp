<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<c:choose>
    <c:when test="${action eq 'generateCertificate'}">
        <form name="generatecertificateform" id="generatecertificateform" method="post" action="">
            <div class="menu_caption_bg cursor-pointer" style="width: 100%" onclick="javascript:hide_menu('show_hide3', 'genpdf', 'nav_show', 'nav_hide')">
                <div id="divAddCaption" class="menu_caption_text">Generate PDF</div>
                <span><a href="javascript:void(0)" name="show_hide3" id="show_hide3" class="nav_hide"></a></span>
            </div>
            <div class="report_content" id="divCertType" >
                <table align="center" border="0" cellpadding="0" cellspacing="2" class="menu_subcaption" width="100%">
                    <tr>
                        <td align="right" class="report_content_caption">
                            Certificate for <sup class="astriek">*</sup>: 
                        </td>
                        <td class="report_content_value">
                            <select class="custom_combo_showSSWT" id="cmbProject" name="cmbProject" tabindex="2" onchange="javascript:onProjectChange();" >
                                <option value="-1">-- Select Project Name --</option>
                                <option value="mfbrok">MFBROK</option>
                                <option value="eservice">eService</option>
                            </select>
                        </td>
                    </tr>                  
                </table>
            </div>
            <div class="report_content" id="genpdf" style="display: none;">
                <table align="center" border="0" cellpadding="0" cellspacing="2" class="menu_subcaption" width="100%">
                    <tr>
                        <td align="right" class="report_content_caption">
                            Client Name <sup class="astriek">*</sup>: 
                        </td>
                        <td class="report_content_value">
                            <select class="custom_combo_showSSWT" id="clientcmbgen" name="clientcmb" tabindex="2" >
                                <option value="-1">-- Select Client Name --</option>
                                <c:forEach items="${clientList}" var="b" >
                                    <option value="${b.CLIENT_ID}" >${b.CLIENT_NAME}</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" class="report_content_caption">
                            Key <sup class="astriek">*</sup>: 
                        </td>
                        <td class="report_content_value">
                            <input type="text" id="syskey" name="syskey" value="" maxlength="50" tabindex="2"/>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" class="report_content_caption">
                            Activation Date <sup class="astriek">*</sup>: 
                        </td>
                        <td class="report_content_value">
                            <input readonly="readonly" type="text" id="activation_date" name="activation_date" value="${cur_date}" tabindex="2">
                        </td>
                    </tr>
                    <tr>
                        <td align="right" class="report_content_caption">
                            Expiry Date <sup class="astriek">*</sup>: 
                        </td>
                        <td class="report_content_value">
                            <input readonly="readonly" type="text" id="expiry_date" name="expiry_date" value="${cur_date}" tabindex="2">
                        </td>
                    </tr>
                    <tr>
                        <td align="right" class="report_content_caption">
                            Comment <sup class="astriek">*</sup>: 
                        </td>
                        <td class="report_content_value">
                            <input type="text" maxlength="100" id="comment" name="comment" value="" tabindex="2">
                        </td>
                    </tr>
                    <tr>
                        <td align="center" colspan="2">
                            <input class="button" type="button" id="genPdf" name="genPdf" Value="Generate Pdf" onclick="javascript: validateClientDetail();" tabindex="2"/>
                            <input class="button" type="reset" id="btnReset" name="btnReset" Value="Reset" tabindex="2"/>
                            <input type="hidden" id="hiddenDate" name="hiddenDate" value="${cur_date}">
                        </td>
                    </tr>
                </table>
            </div>

            <div class="report_content" id="genLicense" style="display: none;">
                <table align="center" border="0" cellpadding="0" cellspacing="2" class="menu_subcaption" width="100%">

                    <tr>
                        <td align="right" class="report_content_caption">
                            Tenant ID <sup class="astriek">*</sup>: 
                        </td>
                        <td class="report_content_value">
                            <input type="text" id="txtTenantId" name="tenantId" value="" maxlength="50" tabindex="2"/>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" class="report_content_caption">
                            Server Name <sup class="astriek">*</sup>: 
                        </td>
                        <td class="report_content_value">
                            <select class="custom_combo_showSSWT" id="cmbServerName" name="serverName" tabindex="2" >
                                <option value="-1">-- Select Server Name --</option>
                                <c:forEach items="${serverList}" var="rec" varStatus="loop" >
                                    <option value="${serverList[loop.index]}" >${serverList[loop.index]}</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" class="report_content_caption">
                            Activation Date <sup class="astriek">*</sup>: 
                        </td>
                        <td class="report_content_value">
                            <input readonly="readonly" type="text" id="activationdate" name="activationDate" value="${cur_date}" tabindex="2">
                        </td>
                    </tr>
                    <tr>
                        <td align="right" class="report_content_caption">
                            Expiry Date <sup class="astriek">*</sup>: 
                        </td>
                        <td class="report_content_value">
                            <input readonly="readonly" type="text" id="expirydate" name="expiryDate" value="${cur_date}" tabindex="2">
                        </td>
                    </tr>
                    <tr>
                        <td align="right" class="report_content_caption">
                            Comment <sup class="astriek">*</sup>: 
                        </td>
                        <td class="report_content_value">
                            <input type="text" maxlength="100" id="txtComment" name="comment" value="" tabindex="2">
                        </td>
                    </tr>
                    <tr>
                        <td align="center" colspan="2">
                            <input class="button" type="button" id="genPdf" name="genPdf" Value="Generate Pdf" onclick="javascript: validateTenantDetail();" tabindex="2"/>
                            <input class="button" type="reset" id="btnReset" name="btnReset" Value="Reset" tabindex="2"/>
                            <input type="hidden" id="hiddenDate" name="hiddenDate" value="${cur_date}">
                        </td>
                    </tr>
                </table>
            </div>
        </form>
        <c:choose>
            <c:when test="${process eq 'linkGenerate'}">
                <div style="display: none" id="successmsg">
                    <div class="report_content">
                        <table align="center" border="0" cellpadding="0" cellspacing="2" id="captiontable" class="menu_subcaption" width="100%">
                            <tr>
                                <td align="right" class="report_content_caption">
                                    Download Licence Certificate zip file : 
                                </td>
                                <td class="report_content_value">
                                    <a href="generatepdf.fin?cmdAction=downloadFile&amp;fileName=${fileName}.pdf&amp;folder=${folderPath}">${fileName}.pdf</a>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </c:when>
            <c:when test="${process eq 'error'}">
                <div id="errmsg">
                    <div class="report_content">
                        <table align="center" border="0" cellpadding="0" cellspacing="2" id="captiontable" class="table_subcaption" width="100%">
                            <tr>
                                <td align="right" class="report_content_caption">
                                    Error to generate pdf file 
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </c:when>
            <c:when test="${process eq 'genLicenseLink'}">
                <div style="display: none" id="successmsg">
                    <div class="report_content">
                        <table align="center" border="0" cellpadding="0" cellspacing="2" id="captiontable" class="menu_subcaption" width="100%">
                            <tr>
                                <td align="right" class="report_content_caption">
                                    Download License file : 
                                </td>
                                <td class="report_content_value">
                                    <a  href="generatepdf.fin?cmdAction=downloadLicFile&FileName=${fileName}" >${fileName}</a> 
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </c:when>
        </c:choose>
    </c:when>
</c:choose>