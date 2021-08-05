<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<form  name="MenuForm" id="MenuForm" method="post" action="">
    <div class="container"  >
        <div class="content" id="menuLoader3">
            <div class="menu_new">
                <div class="menu_caption_bg">
                    <div class="menu_caption_text">Database Metadata Inspector</div>
                </div>
                <table class="menu_subcaption fullwidth" align="center">
                    <tr>
                        <td align="right" class="report_content_caption">
                            Database :
                        </td>
                        <td class="" colspan="2">
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
                        <td class="" colspan="2">
                            <select id="cmbObjType" name="cmbObjType" tabindex="3" onchange="javascript : resetObjName();" >
                                <option value="-1" selected>-- Object Type --</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" class="report_content_caption" id="tdObjName" style="vertical-align: top">
                            Object Name :
                        </td>
                        <td style="vertical-align: top;width: 10px">                           
                            <input type="text" id="txtObjName" name="txtObjName" tabindex="4" style="width: 100px" onblur="javascript : fillObjName();" />                            
                        </td>
                        <td>
                            <select id="cmbObjName" name="cmbObjName" tabindex="5" onchange="javascript : showTablePro()" class="custom_combo_mid" style="float: none ;width: 171px !important" >
                                <option value="-1" selected>-- Object Name --</option>
                            </select>                            
                        </td>
                    </tr>
                    <tbody id="tblObjExtraFields" style="display: none">
                        <tr>
                            <td align="right" class="report_content_caption">
                                Show :
                            </td>
                            <td class="" colspan="2">
                                <input type="checkbox" id="chkShowDef" name="chkShowDef" tabindex="5" checked>Definition &nbsp;
                                <input type="checkbox" id="chkShowDepend" name="chkShowDepend" tabindex="6" onchange="javascript:onChkDepend(this.checked);">Dependencies
                            </td>
                        </tr>
                        <tr id="cacheRow" style="display:none">
                            <td align="right" class="report_content_caption">
                                Caching :
                            </td>
                            <td class="" colspan="2">
                                <input type="checkbox" id="chkChaching" name="chkCaching" tabindex="7"> Use cache
                            </td>
                        </tr>
                    </tbody>
                    <tr>
                        <td align="center" colspan="3">
                            <input class="button" type="button" name="btnSubmit" value="Submit" tabindex="8" onclick="javascript : getDetail();" />
                            <input type="hidden" id="hdnObjType" name="hdnObjType">
                        </td>
                    </tr>
                    <tr>
                        <td colspan="3">&nbsp;</td>
                    </tr>
                </table>
                <div id="divDetail" style="display: none;"></div>
                <div id="divTblDep" style="display: none;"></div>
                <div id="divOtherObjDetail" style="display: none;text-align: center"></div>
            </div>
        </div>
    </div>
</form>