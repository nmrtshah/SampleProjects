<%--
    Document   : newjsp
    Created on : 10 MAY, 2014, 3:01:37 PM
    Author     : Jeegarkumar Patel
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<form  name="MenuForm" id="MenuForm" method="post" action="">
    <div class="container"  >
        <div class="content" id="menuLoader3">
            <div class="menu_new" style="width:100%">
                <div class="menu_caption_bg" style="width: 100%">
                    <div class="menu_caption_text">Database Metadata Comparator</div>
                </div>
                <table width="80%" class="menu_subcaption fullwidth" align="center">
                    <tr>
                        <td align="right" class="report_content_caption">
                            Server  <sup class="astriek">*</sup>:
                        </td>
                        <td class="report_content_value">
                            <input type="radio" id="server" name="server" value="DT" onchange="javascript : resetObjName();"/>Dev-Test
                            <input type="radio" id="server" name="server" value="TP" onchange="javascript : resetObjName();"/>Test-Prod
                        </td>
                    </tr>
                    <tr>
                        <td align="right" class="report_content_caption">
                            Database :
                        </td>
                        <td class="report_content_value">
                            <input type="text" id="txtDB" name="txtDB" style="width: 100px" tabindex="1"  onblur="javascript : fillDBCombo();">
                            <select id="cmbDB" name="cmbDB" tabindex="2" class="custom_combo_mid"
                                    style="float: none; width: 171px !important" onchange="javascript : fillObjType();" >
                                <option value="-1" selected>-- Database --</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" class="report_content_caption">
                            Object Type :
                        </td>
                        <td class="report_content_value">
                            <select id="cmbObjType" name="cmbObjType" tabindex="3" onchange="javascript : resetObjName();" >
                                <option value="-1" selected>-- Object Type --</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" class="report_content_caption" id="tdObjName">
                            <span id="lblObjName" style="float:right"> Object Name :</span>
                        </td>

                        <td class="report_content_value">
                            <input type="text" id="txtObjName" name="txtObjName" tabindex="4" style="width: 100px" onblur="javascript : fillObjName();" />
                            <select id="cmbObjName" name="cmbObjName" tabindex="5" onchange="javascript : showTablePro()" class="custom_combo_mid" style="float: none ;width: 171px !important" >
                                <option value="-1" selected>-- Object Name --</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td align="center" colspan="2">
                            <input class="button" type="button" name="btnSubmit" value="Submit" tabindex="8" onclick="javascript : getDetail();" />
                            <input type="hidden" id="hdnObjType" name="hdnObjType">
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">&nbsp;</td>
                    </tr>
                </table>
                <div id="divDetail" style="display: none;"></div>
                <div id="divTblDep" style="display: none;"></div>
                <div id="divOtherObjDetail" style="display: none;text-align: center"></div>
            </div>
        </div>
    </div>
</form>