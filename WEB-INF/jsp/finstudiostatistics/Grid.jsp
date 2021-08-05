<%-- TCIGBF --%>

<div id="basicReportDiv" style="display: none">
    <table cellpadding="0" cellspacing="0" width="1000" class="dhtml-main-report">
        <tr>
            <td align="center">
                <table cellpadding="0" cellspacing="0" border="0" class="tbl_h1_bg">
                    <tr>
                        <td class="menu_caption_text" id="rptTitle">Finstudio Statistics Report</td>
                        <td width="25"><span class="export_radio" style="margin-right: 0"><a id="export_pdf" href="javascript: generatePDF();"><span>&nbsp;</span></a></span></td>
                        <td width="25"><span class="export_radio" style="margin-right: 2px"><a id="export_xls" href="javascript: generateEXCEL();"><span>&nbsp;</span></a></span></td>
                        <td width="25"><span><a href="javascript:void(0);" onclick="javascript: showReport('Print');"><img src="images/print_icon.gif" alt="Print" title="Print"/></a></span></td>
                        <td width="25" onclick="javascript: hide_menu('report_hide','maingridDiv');" ><span><a href="javascript:void(0);" class="nav_hide" id="report_hide" name="report_hide"></a></span></td>
                    </tr>
                </table>
            </td>
        </tr>
        <tbody id="maingridDiv">
            <tr>
                <td valign="top">
                    <div id="gridbox" style="width:100%;background-color:white;margin: 5px auto;"></div>
                </td>
            </tr>
            <tr>
                <td valign="top">
                    <div id="pagingArea" align="center" style="display: block"></div>
                </td>
            </tr>
            <tr>
                <td valign="top">
                    <div id="recinfoArea" align="center" style="display: block;"></div>
                </td>
            </tr>
        </tbody>
    </table>
</div>
