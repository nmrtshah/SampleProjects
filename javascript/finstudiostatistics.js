var mygrid;

window.onload = function()
{
    loadAllCombo();
}

function loadAllCombo()
{
    loadComboNew("groupname","","","finstudiostatisticsForm");
}

function showReport(rptfor)
{
    displayReportMenu('report_filter_out');
    document.getElementById('basicReportDiv').style.display = "";
    showDetailReportGrid(rptfor);
    hide_menu('show_hide','hide_menu', 'nav_show','nav_hide');
}

function printGridReport()
{
    var paramArr = new Array();
    paramArr["footerContentId"] = "footer_print";
    paramArr["headerContentId"] = "header_print";
    paramArr["projectName"] = "finstudio";
    paramArr["headerImgSrc"] = "http://dev.njtechdesk.com/finlibrary/resource/images/logo_njindiainvest.gif";
    paramArr["footerImgSrc"] = "";
    paramArr["breadcrumId"] = "";
    paramArr["printTitleContent"] = "Finstudio Statistics Report";
    mygrid.printView(getHeaderForPrint(paramArr),getFooterForPrint(paramArr));
}

function hideLoadingImg()
{
    document.getElementById("loadingImageID").style.display = "none";
}

function showLoadingImg()
{
    document.getElementById("loadingImageID").style.display = "";
}

function showDetailReportGrid(rptfor)
{
    var param = getFormData(document.finstudiostatisticsForm);
    mygrid = new dhtmlXGridObject("gridbox");
    mygrid.setImagePath(getFinLibPath()+"dhtmlxSuite/dhtmlxGrid/codebase/imgs/");
    mygrid.setHeader("<b>SrNo</b>,<b>Module Name</b>,<b>Statistics</b>,<b>On Date</b>");
    mygrid.setInitWidths("0,200,674,150");
    mygrid.setColAlign("left,left,left,left");
    mygrid.setColSorting("int,str,str,str");
    mygrid.setColumnHidden("0","true");
    mygrid.enableAutoWidth(true);
    mygrid.enableAutoHeight(true);
    mygrid.enableMultiline(true);
    mygrid.setDateFormat("%d-%m-%Y");
    mygrid.enableDragAndDrop(true);
    mygrid.enableColumnMove(true);
    mygrid.enableMultiselect(true);
    mygrid.enableCollSpan(true);
    mygrid.setSkin("dhx_web");
    document.getElementById("pagingArea").innerHTML = "";
    document.getElementById("recinfoArea").innerHTML = "";
    mygrid.enablePaging(true,15,15,"pagingArea",true,"recinfoArea");
    mygrid.setPagingWTMode(true,true,true,[15,20,25]);
    mygrid.setPagingSkin("toolbar","dhx_web");
    mygrid.init();
    if (rptfor == "Print")
    {
        mygrid.load("finstudiostatistics.fin?cmdAction=getReportGrid&"+param, printGridReport, "json");
    }
    else
    {
        if (document.getElementById("rdoOnScreen").checked)
        {
            showLoadingImg();
            mygrid.load("finstudiostatistics.fin?cmdAction=getReportGrid&"+param, hideLoadingImg, "json");
        }
        else if (document.getElementById("rdoPdf").checked)
        {
            mygrid.post("finstudiostatistics.fin?cmdAction=getReportGrid",param,generatePDF,"json");
        }
        else if (document.getElementById("rdoExcel").checked)
        {
            mygrid.post("finstudiostatistics.fin?cmdAction=getReportGrid",param,generateEXCEL,"json");
        }
    }
    $('div.gridbox div.ftr table').css('padding-right','0');
    $('div.gridbox table.hdr ').css('padding-right','0');
    dhtmlxError.catchError("ALL","<b>Under Maintenance. Please Try Later.</b>");
}

function changeExport()
{
    if (document.getElementById("rdoOnScreen").checked)
    {
        document.getElementById("btnPrint").disabled = false;
    }
    else
    {
        document.getElementById("btnPrint").disabled = true;
    }
    if (document.getElementById("rdoPdf").checked == true)
    {
        document.getElementById("pdfdisplaymode").style.display = "";
    }
    else
    {
        document.getElementById("pdfdisplaymode").style.display = "none";
        document.getElementById("displayMode").options[0].selected = true;
    }
}

function generatePDF()
{
    var param = getFormData(document.finstudiostatisticsForm);
    mygrid.toPDF('finstudiostatistics.fin?cmdAction=generatePDF&'+param, "gray", true, true);
    if (!document.getElementById("rdoOnScreen").checked)
    {
        hideDiv("basicReportDiv");
    }
}

function generateEXCEL()
{
    var param = getFormData(document.finstudiostatisticsForm);
    mygrid.toExcel('finstudiostatistics.fin?cmdAction=generateExcel&'+param, "gray");
    if (!document.getElementById("rdoOnScreen").checked)
    {
        hideDiv("basicReportDiv");
    }
}