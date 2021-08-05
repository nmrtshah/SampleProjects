/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function addLoader()
{
    var param = getFormData(document.blockModulesForm);
    getSynchronousData('blockmodulesAdd.fin?cmdAction=getAddTab', param, "contentView");
}

function onBlockPeriodClick()
{
    var blockPeriodOpt = $("input[name=blockPeriodOptn]:checked").val();
    if(blockPeriodOpt === "urgent")
    {
        $("#trFromDateId").hide();
        $("#trToDateId").hide();
    }
    else
    {
        $("#trFromDateId").show();
        $("#trToDateId").show();
    }
}

function setDefaultValue(inputId)
{
    var inputValue = Trim($("#"+inputId).val());
    if(inputValue === "")
    {
        $("#"+inputId).val("NA");
    }
}

function validateBlockEntry()
{
    // validation for 'Server Name'
    var serverName = $("#serverName").val();
    if (serverName == -1)
    {
        valid_select_alert("Server Name");
        $("#serverName").focus();
        return false;
    }
    // validation for 'URI'
    var uriValue = Trim($("#uri").val());
    var firstUriChar = uriValue.substr(0, 1);
    var lastUriChar = uriValue.substr(uriValue.length - 1);
    var decimalIndex = uriValue.lastIndexOf(".");
    var extValue = uriValue.substr(decimalIndex + 1);
    if (uriValue == "")
    {
        value_common_alert("URI");
        $("#uri").focus();
        return false;
    }
//    if (uriValue.length < 3)
//    {
//        alert("'URI' should not be less than 3 characters.");
//        $("#uri").focus();
//        return false;
//    }
    if (firstUriChar != "/" || ((lastUriChar != "/" && extValue != "fin") && (lastUriChar != "/" && extValue != "php")))
    {
        value_common_alert("URI");
        $("#uri").focus();
        return false;
    }
    var strRegExp = /^([a-zA-Z]+[0-9]*)$/;
    var strValueRegExp = /^([a-zA-Z0-9_]+)$/;
    if(Trim($("#paramNames").val()) != "" || Trim($("#paramValues").val()) != "")
    {
        // validation for 'Parameter Names'
        var paramNm = Trim($("#paramNames").val());
        var lastParamNmChar = paramNm.substr(paramNm.length - 1);
        var paramNmArr = paramNm.split(",");
        var paramNmLen = paramNmArr.length;
        if (paramNm == "")
        {
            value_common_alert("Parameter Names");
            $("#paramNames").focus();
            return false;
        }
        if(lastParamNmChar == ",")
        {
            alert("Please enter proper 'Parameter Names'");
            $("#paramNames").focus();
            return false;
        }
        for(var i=0; i<paramNmLen; i++)
        {
            if(Trim(paramNmArr[i]) == "")
            {
                alert("Please enter proper 'Parameter Names'");
                $("#paramNames").focus();
                return false;
            }
            if(!strRegExp.test(Trim(paramNmArr[i])))
            {
                alert("Please enter proper 'Parameter Names'");
                $("#paramNames").focus();
                return false;
            }
        }
        // validation for 'Parameter Values'
        var paramVal = Trim($("#paramValues").val());
        var lastParamValChar = paramVal.substr(paramVal.length - 1);
        var paramValArr = paramVal.split(",");
        var paramValLen = paramValArr.length;
        if (paramVal == "")
        {
            value_common_alert("Parameter Values");
            $("#paramValues").focus();
            return false;
        }
        if(lastParamValChar == ",")
        {
            alert("Please enter proper 'Parameter Values'");
            $("#paramValues").focus();
            return false;
        }
        for(var i=0; i<paramValLen; i++)
        {
            if(Trim(paramValArr[i]) == "")
            {
                alert("Please enter proper 'Parameter Values'");
                $("#paramValues").focus();
                return false;
            }
            if(!strValueRegExp.test(Trim(paramValArr[i])))
            {
                alert("Please enter proper 'Parameter Values'");
                $("#paramValues").focus();
                return false;
            }
        }
        if(paramNmLen != paramValLen)
        {
            alert("Please enter 'Parameter Names' wise 'Parameter Values'");
            $("#paramNames").focus();
            return false;
        }
    }
    if(Trim($("#sesVarNames").val()) != "" || Trim($("#sesVarValues").val()) != "")
    {
        // validation for 'Session Variable Names'
        var sesNm = Trim($("#sesVarNames").val());
        var lastSesNmChar = sesNm.substr(sesNm.length - 1);
        var sesNmArr = sesNm.split(",");
        var sesNmLen = sesNmArr.length;
        if (sesNm == "")
        {
            value_common_alert("Session Variable Names");
            $("#sesVarNames").focus();
            return false;
        }
        if(lastSesNmChar == ",")
        {
            alert("Please enter proper 'Session Variable Names'");
            $("#sesVarNames").focus();
            return false;
        }
        for(var i=0; i<sesNmLen; i++)
        {
            if(Trim(sesNmArr[i]) == "")
            {
                alert("Please enter proper 'Session Variable Names'");
                $("#sesVarNames").focus();
                return false;
            }
            if(!strRegExp.test(Trim(sesNmArr[i])))
            {
                alert("Please enter proper 'Session Variable Names'");
                $("#sesVarNames").focus();
                return false;
            }
        }
        // validation for 'Session Variable Values'
        var sesVal = Trim($("#sesVarValues").val());
        var lastSesValChar = sesVal.substr(sesVal.length - 1);
        var sesValArr = sesVal.split(",");
        var sesValLen = sesValArr.length;
        if (sesVal == "")
        {
            value_common_alert("Session Variable Values");
            $("#sesVarValues").focus();
            return false;
        }
        if(lastSesValChar == ",")
        {
            alert("Please enter proper 'Session Variable Values'");
            $("#sesVarValues").focus();
            return false;
        }
        for(var i=0; i<sesValLen; i++)
        {
            if(Trim(sesValArr[i]) == "")
            {
                alert("Please enter proper 'Session Variable Values'");
                $("#sesVarValues").focus();
                return false;
            }
            var sesNameVal = sesValArr[i];
            var lastSesNameVal = sesNameVal.substr(sesNameVal.length - 1);
            var sesNameValArr = sesNameVal.split(";");
            var sesNameValLen = sesNameValArr.length;
            if(lastSesNameVal == ";")
            {
                alert("Please enter proper 'Session Variable Values'");
                $("#sesVarValues").focus();
                return false;
            }
            for(var j=0; j<sesNameValLen; j++)
            {
                if(Trim(sesNameValArr[j]) == "")
                {
                    alert("Please enter proper 'Session Variable Values'");
                    $("#sesVarValues").focus();
                    return false;
                }
                if(!strValueRegExp.test(Trim(sesNameValArr[j])))
                {
                    alert("Please enter proper 'Session Variable Values'");
                    $("#sesVarValues").focus();
                    return false;
                }
            }
        }
        if(sesNmLen != sesValLen)
        {
            alert("Please enter 'Session Variable Names' wise 'Session Variable Values'");
            $("#sesVarNames").focus();
            return false;
        }
    }
    // validation for 'Allow IP'
    var ipAdrRegExp = /^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/;
    var allowIpVal = Trim($("#allowIpAddress").val());
    var lastAllowIpChar = allowIpVal.substr(allowIpVal.length - 1);
    var allowIpValArr = allowIpVal.split(",");
    if (allowIpVal == "")
    {
        value_common_alert("Allow IP");
        $("#allowIpAddress").focus();
        return false;
    }
    if(lastAllowIpChar == ",")
    {
        alert("Please enter proper 'Allow IP'");
        $("#allowIpAddress").focus();
        return false;
    }
    if(allowIpVal != "NA")
    {
        for(var i=0; i<allowIpValArr.length; i++)
        {
            if(!ipAdrRegExp.test(allowIpValArr[i]))
            {
                alert("Please enter proper 'Allow IP'");
                $("#allowIpAddress").focus();
                return false;
            }
        }
    }
    var blockPeriodOpt = $("input[name=blockPeriodOptn]:checked").val();
    if(blockPeriodOpt === "schedule")
    {
        // validation for 'Month' - From Date
        if ($("#monthFromDate").val() == -1)
        {
            valid_select_alert("Month");
            $("#monthFromDate").focus();
            return false;
        }
        // validation for 'Day' - From Date
        if ($("#dayFromDate").val() == -1)
        {
            valid_select_alert("Day");
            $("#dayFromDate").focus();
            return false;
        }
        // validation for 'Hour' - From Date
        if ($("#hourFromDate").val() == -1)
        {
            valid_select_alert("Hour");
            $("#hourFromDate").focus();
            return false;
        }
        // validation for 'Minute' - From Date
        if ($("#minFromDate").val() == -1)
        {
            valid_select_alert("Minute");
            $("#minFromDate").focus();
            return false;
        }
        // validation for 'Month' - To Date
        if ($("#monthToDate").val() == -1)
        {
            valid_select_alert("Month");
            $("#monthToDate").focus();
            return false;
        }
        // validation for 'Day' - To Date
        if ($("#dayToDate").val() == -1)
        {
            valid_select_alert("Day");
            $("#dayToDate").focus();
            return false;
        }
        // validation for 'Hour' - To Date
        if ($("#hourToDate").val() == -1)
        {
            valid_select_alert("Hour");
            $("#hourToDate").focus();
            return false;
        }
        // validation for 'Minute' - To Date
        if ($("#minToDate").val() == -1)
        {
            valid_select_alert("Minute");
            $("#minToDate").focus();
            return false;
        }
    }
    // validation for 'Message'
    var blockMsg = Trim($("#message").val());
    if (blockMsg == "")
    {
        value_common_alert("Block Message");
        $("#message").focus();
        return false;
    }
    return true;
}

function numbersonly(e)
{
    var unicode=e.charCode? e.charCode : e.keyCode
    if (unicode!=8 && unicode!=9)
    { //if the key isn't the backspace key (which we should allow)
        if (unicode==47||unicode==45||unicode<44||unicode>57)  //if not a number
            return false; //disable key press
    }
}

function insertBlockEntry()
{
    if (!validateBlockEntry())
        return false;
    var param = getFormData(document.blockModulesForm);
    getSynchronousData('blockmodulesAdd.fin?cmdAction=insertBlockEntry', param, 'resultMsg');
    if (document.getElementById("divMsg"))
    {
        if (document.getElementById("hdnDbMsg").value === "success")
        {
            alert("Record has been Inserted Successfully");
            $("#serverName").val(-1);
            $("#uri").val("");
        } 
        else if (document.getElementById("hdnDbMsg").value === "failure")
        {
            alert("Problem In Record Insertion");
            $("#serverName").val(-1);
            $("#uri").val("");
        }
    }
}

function resetAllFields()
{
    $("#serverName").val(-1);
    $("#uri").val("");
    $("#paramNames").val("NA");
    $("#paramValues").val("NA");
    $("#sesVarNames").val("NA");
    $("#sesVarValues").val("NA");
    $("#blockPeriodOpt1").prop('checked', true);
    $("#accessDenied").prop('checked', true);
    $("#trFromDateId").hide();
    $("#trToDateId").hide();
    $("#allowIpAddress").val("NA");
    $("#monthFromDate").val(-1);
    $("#dayFromDate").val(-1);
    $("#hourFromDate").val(-1);
    $("#minFromDate").val(-1);
    $("#monthToDate").val(-1);
    $("#dayToDate").val(-1);
    $("#hourToDate").val(-1);
    $("#minToDate").val(-1);
    $("#message").val("System is unavailable due to maintenance activity. It will be available soon.");
}

function onChangeFromMonth(divId)
{
    var param = getFormData(document.blockModulesForm);
    getSynchronousData('blockmodulesAdd.fin?cmdAction=getFromMonthwiseDays', param, divId);
}

function onChangeToMonth(divId)
{
    var param = getFormData(document.blockModulesForm);
    getSynchronousData('blockmodulesAdd.fin?cmdAction=getToMonthwiseDays', param, divId);
}

function reportLoader()
{
    var param = getFormData(document.blockModulesForm);
    getSynchronousData('blockmodulesRpt.fin?cmdAction=getReportTab', param, "contentView");
    loadDatePicker("filterFromDate");
    loadDatePicker("filterToDate");
}

var reportGrid;
function getReportData()
{
    var param = getFormData(document.blockModulesForm);
    reportGrid = new dhtmlXGridObject("reportGrid");
    reportGrid.setImagePath(getFinLibPath()+"/dhtmlxSuite4/codebase/imgs/");
    reportGrid.setHeader("<b>SR NO</b>,<b>ID</b>,<b>DETAILS</b>,<b>ENTDATE</b>,<b>LASTUPDATE</b>,<b>STATUS</b>,<b>ACTION</b>");
    reportGrid.setInitWidths("50,50,410,120,120,150,100");
    reportGrid.setColAlign("center,center,left,left,left,center,center");
    reportGrid.setColTypes("ro,ro,ro,ro,ro,ro,ro");
    reportGrid.setColumnHidden(1, true);
    reportGrid.enableAutoWidth(true);
    reportGrid.enableAutoHeight(true);
    reportGrid.enablePaging(true,10,5, "reportPagingArea",true,"reportRecinfoArea");
    reportGrid.setPagingWTMode(true,true,true,[10,20,50,100]);
    reportGrid.setPagingSkin("toolbar");
    reportGrid.enableMultiline(true);
    reportGrid.init();
    reportGrid.post("blockmodulesRpt.fin?cmdAction=getReportData",param,"","json");
}

var authoriseGrid;
function authoriseLoader() 
{
    var param = getFormData(document.blockModulesForm);
    getSynchronousData('blockmodulesAuth.fin?cmdAction=getAuthoriseTab', param, "contentView");
    
    authoriseGrid = new dhtmlXGridObject("authoriseGrid");
    authoriseGrid.setImagePath(getFinLibPath()+"/dhtmlxSuite4/codebase/imgs/");
    authoriseGrid.setHeader("<b>SR NO</b>,<b>ID</b>,<b>DETAILS</b>,<b>STATUS</b>,<b>REMARKS</b>,<b>ACTION</b>");
    authoriseGrid.setInitWidths("50,50,360,110,230,200");
    authoriseGrid.setColAlign("center,center,left,center,center,center");
    authoriseGrid.setColTypes("ro,ro,ro,ro,ro,ro");
    authoriseGrid.setColumnHidden(1, true);
    authoriseGrid.enableAutoWidth(true);
    authoriseGrid.enableAutoHeight(true);
    authoriseGrid.enablePaging(true,10,5, "authorisePagingArea",true,"authoriseRecinfoArea");
    authoriseGrid.setPagingWTMode(true,true,true,[10,20,50,100]);
    authoriseGrid.setPagingSkin("toolbar");
    authoriseGrid.enableMultiline(true);
    authoriseGrid.init();
    authoriseGrid.post("blockmodulesAuth.fin?cmdAction=getAuthoriseData",param,"","json");
}

function updateReportTabEntry(id)
{
    $("#hdnUnBlockedId").val(id);
    var param = getFormData(document.blockModulesForm);
    getSynchronousData('blockmodulesRpt.fin?cmdAction=updateBlockEntry', param, 'resultMsg');
    if (document.getElementById("divMsg"))
    {
        if (document.getElementById("hdnDbMsg").value === "success")
        {
            alert("Record has been Updated Successfully");
            reportLoader();
        } 
        else if (document.getElementById("hdnDbMsg").value === "failure")
        {
            alert("Problem In Record Updation");
        }
    }
}

function updateAuthoriseTabEntry(updatedStatus,id)
{
    var blockedId = id;
    var blockedRemarks = "rem_"+id;
    if(updatedStatus == 'rejected')
    {
        var remValue = $("#"+blockedRemarks).val();
        if(Trim(remValue) == "")
        {
            value_common_alert("Remarks");
            $("#"+blockedRemarks).focus();
            return false;
        }        
        $("#hdnRemarks").val(remValue);
    }
    $("#hdnUnBlockedId").val(blockedId);
    var param = getFormData(document.blockModulesForm);
    
    if(updatedStatus == 'authorised')
        getSynchronousData('blockmodulesAuth.fin?cmdAction=updateAuthStatus', param, 'resultMsg');
    else
        getSynchronousData('blockmodulesAuth.fin?cmdAction=updateRejectStatus', param, 'resultMsg');
    if (document.getElementById("divMsg"))
    {
        if (document.getElementById("hdnDbMsg").value === "success")
        {
            alert("Record has been Updated Successfully");
            authoriseLoader();
        }
        else if (document.getElementById("hdnDbMsg").value === "failure")
        {
            alert("Problem In Record Updation");
        }
    }
}