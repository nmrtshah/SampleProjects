<%-- TCIGBF --%>

<div class="menu_caption_bg">
    <div class="menu_caption_text">Edit</div>
</div>

<div class="report_content">
    <table align="center" border="0" cellpadding="0" cellspacing="2" class="menu_subcaption" width="100%">
        <tr>
            <td align="right" class="report_content_caption">
                Master : 
            </td>
            <td class="report_content_value">
                <input type="text" id="master_pk" name="master_pk" tabindex="1" />
            </td>
        </tr>
        <tr>
            <td align="center" colspan="2">
                <input class="button" type="button" id="btnApply" name="btnApply" Value="Apply" onclick="javascript: displayEditMasterGrid()" />
            </td>
        </tr>
    </table>
    <div id="divMsg" style="display: none">
        <input type="hidden" id="hdnDbMsg" name="hdnDbMsg" value="${DBopration}" />
    </div>
</div>
