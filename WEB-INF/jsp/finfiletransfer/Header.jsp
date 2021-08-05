<%-- TCIGBF --%>

<script language="javascript" type="text/javascript">
    var currenttime = "${mydate}"; //Java method of getting server date
    var serverdate = new Date(currenttime);
</script>

<div id="header_print">
    <div class="header_bg">
        <div class="header header_bg">
            <div style="float: left">
                <a href="#"><img width="228" height="50" class="logo_company" src="${finlib_path}/resource/images/logo_njindiainvest.gif" alt="NJ India Invest"/></a>
            </div>
            <div style="float: right;margin-top: 30px;font-size:16px;font-weight:bolder;color: white">
                finstudio
            </div>
        </div>
    </div>
    <table border="0" cellpadding="0" cellspacing="0" width="100%" >
        <tr class="Row_Header">
            <td align="left" height="20">&nbsp;</td>
            <td align="left" nowrap class="clsbtnon" >&nbsp;&nbsp;Welcome To finstudio</td>
            <td align="right" nowrap class="clsbtnon" colspan="100%" >
                <span id="servertime"></span>&nbsp;&nbsp;
            </td>
        </tr>
        <tr class="Line_Header">
            <td align="center" colspan="100%" nowrap></td>
        </tr>
    </table>
</div>
<div class="deskbar">
    <span>finstudio-FinFileTransfer</span>
    <div class="deskbar_logout">Logout</div>
</div>
<div class="breadcrum">Home<a href="javascript:history.back();" style="float:right; padding-right:10px; text-decoration:none;">Back</a></div>
