var mygrid;

function showReport(rptfor)
{
    //    var fDate = document.getElementById('fromDate').value;
    //    var fArr = fDate.split("-");
    //    if(fArr.length!=3)
    //    {
    //        alert('Please Enter Valid From Date (DD-MM-YYYY)');
    //        document.getElementById('fromDate').select();
    //        return false;
    //    }
    //    var from_date = fArr[0]+"/"+fArr[1]+"/"+fArr[2];
    //    var tDate = document.getElementById('toDate').value;
    //    var tArr = tDate.split("-");
    //    if(tArr.length!=3)
    //    {
    //        alert('Please Enter Valid To Date (DD-MM-YYYY)');
    //        document.getElementById('toDate').select();
    //        return false;
    //    }
    //    var to_date = tArr[0]+"/"+tArr[1]+"/"+tArr[2];    
    //    if(!validateDate_txt(from_date))
    //    {
    //        alert('Please Select Valid From Date');
    //        document.getElementById('fromDate').select();
    //        return false;
    //    }
    //    if(!validateDate_txt(to_date))
    //    {
    //        alert('Please Select Valid To Date');
    //        document.getElementById('toDate').select();
    //        return false;
    //    }

    document.getElementById('basicReportDiv').style.display = "";
    showGrid(rptfor);    
    hide_menu('show_hide','hide_menu', 'nav_show','nav_hide');
    return true;
}

function showGrid(rptfor)
{       
    var param = getFormData(document.reportMenuForm);
    mygrid = new dhtmlXGridObject("gridbox");
    mygrid.setImagePath(getFinLibPath()+"dhtmlxSuite/dhtmlxGrid/codebase/imgs/");
    mygrid.setHeader("<b>SrNo.</b>,<b>SrNo.</b>,<b>FINSTUDIO MODULE NAME</b>,<b>PROJECT NAME</b>,<b>MODULE NAME</b>,<b>EMP NAME</b>,<b>DATE</b>");
    mygrid.setInitWidths("40,0,192,192,192,192,192");
    mygrid.setColAlign("left,left,left,left,left,left,center");
    mygrid.setColSorting("int,int,str,str,str,str,date");
    mygrid.setColTypes("ro,ro,ro,ro,ro,ro,ro");
    mygrid.enableAutoWidth(true);
    mygrid.enableAutoHeight(true);
    mygrid.enableMultiline(true);
    mygrid.setDateFormat("%d-%m-%Y");
    mygrid.enableDragAndDrop(true);
    mygrid.enableColumnMove(true);
    mygrid.enableMultiselect(true);
    mygrid.enableCollSpan(true);
    mygrid.setSkin("dhx_web");
    mygrid.setColumnHidden(1,true);
    document.getElementById("pagingArea").innerHTML = "";
    document.getElementById("recinfoArea").innerHTML = "";
    mygrid.enablePaging(true,10,10,"pagingArea",true,"recinfoArea");
    mygrid.setPagingWTMode(true,true,true,[50,100,200]);
    mygrid.setPagingSkin("toolbar","dhx_web");
    mygrid.init();
    if(rptfor == "Print")
    {
        mygrid.load("finstudiomur.fin?cmdAction=getReportGrid&"+param,printGridReport,"json");
    }
    else
    {
        if(document.getElementById("rdoOnScreen").checked)
        {
            showLoadingImg();
            mygrid.load("finstudiomur.fin?cmdAction=getReportGrid&"+param,hideLoadingImg,"json");
        }
        else if(document.getElementById("rdoPdf").checked)
        {
            mygrid.post("finstudiomur.fin?cmdAction=getReportGrid",param,generatePDF,"json");
        }
        else if(document.getElementById("rdoExcel").checked)
        {
            mygrid.post("finstudiomur.fin?cmdAction=getReportGrid",param,generateEXCEL,"json");
        }
    }

    $('div.gridbox div.ftr table').css('padding-right','0');
    $('div.gridbox table.hdr ').css('padding-right','0');   
    dhtmlxError.catchError("ALL","<b>Under Maintenance. Please Try Later.</b>");    
}

function checkRptName()
{
    if(document.getElementById("reportName").value == "")
    {
        alert("Please Enter Report Name");
        document.getElementById("reportName").focus();
        return false;
    }
    return true;
}

function printGridReport()
{
    var paramArr = new Array();
    paramArr["footerContentId"] = "footer_print";
    paramArr["headerContentId"] = "header_print";
    paramArr["projectName"] = "hr";
    paramArr["headerImgSrc"] = "http://dev.njtechdesk.com/finlibrary/resource/images/logo_njindiainvest.gif";
    paramArr["footerImgSrc"] = "";
    paramArr["breadcrumId"] = "";
    paramArr["printTitleContent"] = "Employee Detail Report";
    mygrid.printView(getHeaderForPrint(paramArr),getFooterForPrint(paramArr));
}

function changeExport()
{
    if(document.getElementById("rdoOnScreen").checked)
    {
        document.getElementById("btnPrint").disabled = false;
    }
    else
    {
        document.getElementById("btnPrint").disabled = true;
    }
    if(document.getElementById("rdoPdf").checked == true)
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
    var param = getFormData(document.reportMenuForm);
    mygrid.toPDF('finstudiomur.fin?cmdAction=generatePDF&'+param,"gray",true,true);
    if(!document.getElementById("rdoOnScreen").checked)
    {
        hideDiv("basicReportDiv");
    }
}

function generateEXCEL()
{
    var param = getFormData(document.reportMenuForm);
    mygrid.toExcel('finstudiomur.fin?cmdAction=generateExcel&'+param,"gray");
    if(!document.getElementById("rdoOnScreen").checked)
    {
        hideDiv("basicReportDiv");
    }
}

function hideLoadingImg()
{
    document.getElementById("loadingImageID").style.display = "none";
}

function showLoadingImg()
{
    document.getElementById("loadingImageID").style.display = "";
}