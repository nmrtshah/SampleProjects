<%-- TCIGBF --%>

<div class="report_text">Export</div>
<div class="report_content" align="center">
    <table cellspacing="2" cellpadding="0" border="0" align="center" width="60%" class="menu_subcaption">
        <tr>
            <td>Report name :</td>
            <td><input type="text" name="reportName" id="reportName" ></td>
        </tr>
        <tr>
            <td>Format</td>
            <td>
                <div class="export_radio"><input type="radio" class="radio" id="rdoOnScreen" onchange="changeExport()" name="rdoRptFormate" value="Onscreen" checked >
                    <label><a href="#" id="export_onscreen">On screen</a></label></div>
                <div class="export_radio"><input type="radio" class="radio" id="rdoPdf" name="rdoRptFormate" onchange="changeExport()" value="Pdf"><label><a href="#" id="export_pdf"><span></span>Pdf</a></label></div>
                <div class="export_radio"><input type="radio" class="radio" value="XLS" id="rdoExcel" onchange="changeExport()" name="rdoRptFormate"><label><a href="#" id="export_xls"><span></span>XLS</a></label></div>
            </td>
        </tr>
        <tr style="display:none" id="pdfdisplaymode">
            <td>Display mode:</td>
            <td>
                <select class="" name="displayMode" id="displayMode">
                    <option value="PORTRAIT">Portrait</option>
                    <option value="LANDSCAPE">Landscape</option>
                </select>
            </td>
        </tr>
    </table>
</div>
