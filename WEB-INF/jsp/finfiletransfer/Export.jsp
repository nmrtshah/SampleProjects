<%-- TCIGBF --%>

<div class="menu_caption_bg">
    <div class="menu_caption_text">Export</div>
</div>
<div class="report_content">
    <table align="center" border="0" cellpadding="0" cellspacing="2" class="menu_subcaption" width="60%">
        <tr>
            <td width="20%">Report name :</td>
            <td width="80%"><input type="text" name="reportName" id="reportName" /></td>
        </tr>
        <tr>
            <td>Format :</td>
            <td>
                <div class="export_radio">
                    <input type="radio" class="radio" id="rdoOnScreen" name="rdoRptFormate" value="Onscreen" checked />
                    <a href="#" id="export_onscreen">On screen</a>
                </div>
                <div class="export_radio">
                    <input type="radio" class="radio" id="rdoPdf" name="rdoRptFormate" value="Pdf"/>
                    <a href="#" id="export_pdf"><span>&nbsp;</span>Pdf</a>
                </div>
                <div class="export_radio">
                    <input type="radio" class="radio" id="rdoXLS" name="rdoRptFormate" value="XLS"/>
                    <a href="#" id="export_xls"><span>&nbsp;</span>XLS</a>
                </div>
            </td>
        </tr>
    </table>
</div>
