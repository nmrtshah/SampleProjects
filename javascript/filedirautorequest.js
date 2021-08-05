function AddLoader()
{
    document.getElementById("add_load").style.display = "";
    document.getElementById("basicReportDiv").style.display = "none";
    document.getElementById("basicAuthorizeDiv").style.display = "none";

    getSynchronousData('filedirar.fin?cmdAction=addLoader', '', 'add_load');
}

function ResetMenu()
{
    document.getElementById("SRNO").value = '';
    document.getElementById("rpt_servername").selectedIndex = 0;
    document.getElementById("rpt_project").selectedIndex = 0;
    document.getElementById("from_date").value = document.getElementById("hiddenDate").value;
    document.getElementById("to_date").value = document.getElementById("hiddenDate").value;
    ReportLoader();
}

function ReportLoader()
{
    document.getElementById("basicReportDiv").style.display = "";
    document.getElementById("basicReportDiv").style.width = "1300px";
    document.getElementById("add_load").style.display = "none";
    document.getElementById("basicAuthorizeDiv").style.display = "none";

    var param = getFormData(document.filedirreportform);
    var hiddenDate = document.getElementById("hiddenDate").value;

    if (document.getElementById("btnView").value === "View")
    {
        if (document.getElementById("SRNO").value !== "")
        {
            var trvalue = document.getElementById("SRNO").value;
            var reg = new RegExp('^[0-9]*$');
            if (!reg.test(trvalue))
            {
                alert("Please Enter Valid Request Id");
                return false;
            }
        }
        else if (!checkFutureDateNotAllow(document.filedirreportform, "from_date", "From Date", "Current Date", hiddenDate) ||
                !checkFutureDateNotAllow(document.filedirreportform, "to_date", "To Date", "Current Date", hiddenDate) ||
                !checkFutureDateNotAllow(document.filedirreportform, "from_date", "From Date", "To Date", document.filedirreportform.to_date.value))
        {
            return false;
        }

        mygrid = new dhtmlXGridObject("gridbox");
//        mygrid.setImagePath(getFinLibPath() + "dhtmlxSuite/dhtmlxGrid/codebase/imgs/");
        mygrid.setImagePath(getFinLibPath() + "dhtmlxSuite4/sources/dhtmlxGrid/codebase/imgs/");
        mygrid.setHeader("<b>ID</b>,<b>SERVER NAME</b>,<b>PROJECT NAME</b>,<b>PROCESS</b>,<b>PATH</b>,<b>PURPOSE</b>,<b>USER</b>,<b>ENTDATE</b>,<b>LASTUPDATE</b>,<b>STATUS</b>");
        mygrid.setInitWidths("30,80,150,80,260,260,120,100,110,110,90");
        mygrid.setColAlign("center,left,center,left,left,left,left,center,left,left,left");
        mygrid.setColTypes("ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro");
        mygrid.enableAutoWidth(true);
        mygrid.enableAutoHeight(true);
        mygrid.enableColSpan(true);
        mygrid.setSkin("dhx_web");
        mygrid.init();
        mygrid.post("filedirar.fin?cmdAction=reportLoader", param, "", "json");
    }
}

function ReportFilter()
{
    document.getElementById("basicReportDiv").style.display = "";
    document.getElementById("add_load").style.display = "none";
    document.getElementById("basicAuthorizeDiv").style.display = "none";

    ReportLoader();
}

function AuthorizeLoader()
{
    getSynchronousData('filedirarexe.fin?cmdAction=authorizeMainPage', "", 'basicAuthorizeDiv');

    document.getElementById("basicAuthorizeDiv").style.display = "";
    document.getElementById("basicAuthorizeDiv").style.width = "1300px";
    document.getElementById("basicReportDiv").style.display = "none";
    document.getElementById("add_load").style.display = "none";

    mygrid = new dhtmlXGridObject("authorizegridbox");
    mygrid.setImagePath(getFinLibPath() + "dhtmlxSuite4/sources/dhtmlxGrid/codebase/imgs/");
    mygrid.setHeader("<b>ID</b>,<b>SERVER NAME</b>,<b>PROJECT NAME</b>,<b>PROCESS</b>,<b>PATH</b>,<b>STATUS</b>,<b>PURPOSE</b>,<b>USER</b>,<b>ENTDATE</b>,<b>CHECK</b>,<b>REASON</b>");
    mygrid.attachHeader("#rspan,#rspan,#rspan,#rspan,#rspan,#rspan,#rspan,#rspan,#rspan,<input type='CheckBox' id='ch_' onclick='javascript:checkAll(this.id);'>,#rspan");
    mygrid.setInitWidths("30,80,120,80,260,60,160,120,120,70,200");
    mygrid.setColAlign("center,left,center,left,left,left,left,center,left,center,center");
    mygrid.setColTypes("ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro");
    mygrid.enableAutoWidth(true);
    mygrid.enableAutoHeight(true);
    mygrid.enableColSpan(true);
    mygrid.setSkin("dhx_web");
    mygrid.init();
    mygrid.post("filedirarexe.fin?cmdAction=authorizeLoader", "", "", "json");
}

function disableother(id)
{
    var flag;

    var checkedIds = $(":checkbox").map(function () {
        return this.id;
    }).get();

    $("input:checkbox[name=type]").each(function ()
    {
        checkedIds = $(this).val();
    });

    var checkId = checkedIds.toString().split(",");

    if (document.getElementById(id).checked == false)
    {
        document.getElementById("ch_").checked = false;
    }
    else
    {
        for (var m = 1; m < checkId.length; m++)
        {
            if (document.getElementById(checkId[m]) && document.getElementById(checkId[m]).checked == false)
            {
                flag = false;
            }
        }
        if (flag == false)
        {
            document.getElementById("ch_").checked = false;
        }
        else
        {
            document.getElementById("ch_").checked = true;
        }
    }
}

function checkAll(id)
{
    var allEdit = document.getElementsByName(id);
    var j;
    if (document.getElementById(id).checked === true)
    {
        if (allEdit.length !== 0)
        {
            for (j = 0; j < allEdit.length; j++)
            {
                allEdit[j].checked = true;
            }
        }
    }
    else
    {
        if (allEdit.length !== 0)
        {
            for (j = 0; j < allEdit.length; j++)
            {
                allEdit[j].checked = false;
            }
        }
    }
}

function AuthorizeUpdate()
{
    document.getElementById("basicReportDiv").style.display = "none";
    document.getElementById("add_load").style.display = "none";

    var param = getFormData(document.filedirauthorizeform);
    var id;
    var idVal = "";

    if (mygrid.getRowsNum() > 0)
    {
        for (var m = 0; m < mygrid.getRowsNum(); m++)
        {
            id = mygrid.getRowId(m);
            if (document.getElementById(mygrid.cells(id, 0).getValue()))
            {
                if (document.getElementById(mygrid.cells(id, 0).getValue()).checked)
                {
                    idVal += mygrid.cells(id, 0).getValue() + ",";
                }
            }
        }
    }
    param = param + "&idVal=" + idVal.substring(0, idVal.length - 1);
    if (idVal.length === 0)
    {
        alert("Please Check atleast one Checkbox");
        return false;
    }
    getSynchronousData('filedirarexe.fin?cmdAction=authorizeUpdate', param, 'update_msg');
    if ((document.getElementById("update_msg").innerHTML.indexOf("record(s) Authorized") !== -1)
            || (document.getElementById("update_msg").innerHTML.indexOf("Access Denied") !== -1))
    {
        alert((document.getElementById("update_msg").innerHTML).trim());
    }
    else
    {
        alert("Error to Authorize record(s)");
    }

    AuthorizeLoader();
}

function RejectUpdate()
{
    document.getElementById("add_load").style.display = "none";
    var param = getFormData(document.filedirauthorizeform);
    var id;
    var idVal = "";
    var txtVal = "";
    var flag = true;
    if (mygrid.getRowsNum() > 0)
    {
        for (var m = 0; m < mygrid.getRowsNum(); m++)
        {
            id = mygrid.getRowId(m);
            if (document.getElementById(mygrid.cells(id, 0).getValue()))
            {
                if (document.getElementById(mygrid.cells(id, 0).getValue()).checked)
                {
                    idVal += mygrid.cells(id, 0).getValue() + ",";
                    if (document.getElementById('txt_' + mygrid.cells(id, 0).getValue()).value.trim().length <= 0)
                    {
                        flag = false;
                    }
                    txtVal += document.getElementById('txt_' + mygrid.cells(id, 0).getValue()).value.trim() + ",";
                }
            }
        }
    }
    if (flag === false)
    {
        alert("Please Enter Comment(s) for Reject..!!");
        return false;
    }
    param = param + "&idVal=" + idVal.substring(0, idVal.length - 1) + "&txtVal=" + txtVal.substring(0, txtVal.length - 1);
    if (idVal.length === 0)
    {
        alert("Please Check atleast one Checkbox");
        return false;
    }
    getSynchronousData('filedirarexe.fin?cmdAction=rejectUpdate', param, 'reject_msg');
    if ((document.getElementById("reject_msg").innerHTML.indexOf("record(s) Rejected") !== -1)
            || (document.getElementById("reject_msg").innerHTML.indexOf("Access Denied") !== -1))
    {
        alert((document.getElementById("reject_msg").innerHTML).trim());
    }
    else
    {
        alert("Error to Reject record(s)");
    }
    AuthorizeLoader();
}

function validateData()
{
    if (document.getElementById("btnAdd").value === "Add")
    {

        if (!validate_dropdown(document.filediraddform, "servername", "Server name", true))
        {
            return false;
        }
        if (!validate_dropdown(document.filediraddform, "project", "Project", true))
        {
            return false;
        }
        if (!validate_dropdown(document.filediraddform, "process", "Process", true))
        {
            return false;
        }
        if (!validate_dropdown(document.filediraddform, "pathCombo", "Location", true))
        {
            return false;
        }
        var serverName = document.getElementById("servername").value;
        var process = document.getElementById("process").value;
        if (!(serverName.indexOf("sb1.nj") !== -1) && process === "GetFile")
        {
            if (document.getElementById("pathlist").value === null || document.getElementById("pathlist").value === "")
            {
                alert("Please Enter Proper Value For 'Path'");
                return false;
            }

            if (Trim(document.getElementById("pathlist").value) !== null && Trim(document.getElementById("pathlist").value) !== "")
            {
                var fileList = (document.getElementById("pathlist").value).split("\n");
                var projectNm = document.getElementById("project").value;
                projectNm = projectNm.split("|")[2];
                if (fileList.length > 10) {
                    alert("Please enter 10 'Path' only");
                    document.getElementById("pathlist").focus();
                    return false;
                }
                for (var i = 0; i < fileList.length; i++)
                {
                    var str = fileList[i];
                    var n = str.indexOf(projectNm);
                    if (n != 0)
                    {
                        alert("'Path' Should Start With the Selected Project Name");
                        document.getElementById("pathlist").focus();
                        return false;
                    }
//                    var fileExt = str.substring(str.lastIndexOf('.') + 1).toLowerCase();
//                    if (!(fileExt === "java" || fileExt === "class" || fileExt === "js" || fileExt === "css" || fileExt === "jsp"
//                            || fileExt === "xml" || fileExt === "properties" || fileExt === "html" || fileExt === "php")) {
//                        alert("'Path' only allows file types of 'java','class','js','css','jsp','xml','properties','html' and 'php'");
//                        document.getElementById("pathlist").focus();
//                        return false;
//                    }
                }
            }
        }
        else
        {
            if (document.getElementById("path").value.startsWith("/") || document.getElementById("path").value.startsWith("&frasl;"))
            {
                alert("'Path' should not be start with '/'");
                return false;
            }
            if (document.getElementById("path").value === null || document.getElementById("path").value === "")
            {
                alert("Please Enter Proper Value For 'Path'");
                return false;
            }
        }

        if (document.getElementById("process").value === "Create")
        {
            var createpath = document.getElementById("path").value;
            var rule_path = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789/_-^";
            if (compare_string(rule_path, createpath) === false)
            {
                alert("Please enter valid value for Path [A-Za-z0-9]/_-^");
                return false;
            }
        }

        if (document.getElementById("purpose").value === null || document.getElementById("purpose").value === "")
        {
            alert("Please Enter Value For 'Purpose'");
            return false;
        }
        else
        {
            var purpose = document.getElementById("purpose").value;
            if (purpose.length > 100)
            {
                alert("Purpose Value Should Not Exceed 100 Characters.");
                return false;
            }
            if (compare_string_purpose(purpose) === false)
            {
                alert("Please enter valid value for Purpose [A-Za-z0-9._&-<space>].");
                return false;
            }
        }
        if (document.getElementById("process").value === "Delete")
        {
            var r = confirm("Do you want to delete " + document.getElementById("path").value + " ?");
            if (r === false)
            {
                return false;
            }
        }
        checkStatus();
    }
}

var rule_global_comp_name = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789._&- ";
function compare_string_purpose(strdata)
{
    return compare_string(rule_global_comp_name, strdata);
}

function compare_string(strallowed, strdata)
{
    var flag = false;
    for (var i = 0; i < strdata.length; i++)
    {
        flag = false;
        for (var j = 0; j < strallowed.length; j++)
        {
            if (strdata.charAt(i) === strallowed.charAt(j))
            {
                flag = true;
            }
        }
        if (flag === false)
        {
            return false;
        }
    }
    return true;
}

function checkStatus()
{
    if (document.getElementById("btnAdd").value === "Add")
    {
        var param = getFormData(document.filediraddform);
        document.getElementById("servername").selectedIndex = 0;
        getSynchronousData('filedirar.fin?cmdAction=insertData', param, 'msg');
        if ($.trim(document.getElementById("msg").innerHTML.toString()).indexOf("Request is inserted successfully") > -1)
        {
            alert((document.getElementById("msg").innerHTML).trim());
        }
        else
        {
            alert("Request is not inserted successfully");
        }
        AddLoader();
    }
}
function getLocation()
{
    document.getElementById("pathCombo").selectedIndex = 0;
    document.getElementById("pathCombo").options[1].style.display = "";
    document.getElementById("pathCombo").options[2].style.display = "";
    document.getElementById("pathCombo").options[3].style.display = "";
    document.getElementById("pathCombo").options[4].style.display = "";
    document.getElementById("pathCombo").options[5].style.display = "";

    var serverName = document.getElementById("servername").value;
    if (serverName.indexOf("sb1.nj") !== -1)
    {
        document.getElementById("process").options[1].style.display = "none";
        document.getElementById("process").options[3].style.display = "none";
        document.getElementById("pathCombo").options[1].style.display = "none";
        document.getElementById("pathCombo").options[2].style.display = "none";
        document.getElementById("pathCombo").options[4].style.display = "none";
        document.getElementById("pathCombo").options[5].style.display = "none";
    }
    else
    {
        var process = document.getElementById("process").value;
        if (process === "GetFile")
        {
            document.getElementById("tdTxtInput").style.display = "none";
            document.getElementById("tdTextArea").style.display = "";
        }
        else
        {
            document.getElementById("tdTxtInput").style.display = "";
            document.getElementById("tdTextArea").style.display = "none";
        }

        document.getElementById("process").options[1].style.display = "";
        document.getElementById("process").options[2].style.display = "";
        document.getElementById("process").options[3].style.display = "";
        document.getElementById("process").options[4].style.display = "";

        if (serverName.indexOf("rep") !== -1)
        {
            if (process === "Create")
            {
                document.getElementById("pathCombo").options[1].style.display = "none";
                document.getElementById("pathCombo").options[2].style.display = "none";
            }

            if (process === "Delete")
            {
                document.getElementById("pathCombo").options[1].style.display = "none";
                document.getElementById("pathCombo").options[2].style.display = "none";
            }
        }
    }
}

function viewAddTab()
{
    document.getElementById("add_load").style.display = "";
    document.getElementById("basicReportDiv").style.display = "none";
    document.getElementById("basicAuthorizeDiv").style.display = "none";
    document.getElementById("tdTxtInput").style.display = "";
    document.getElementById("tdTextArea").style.display = "none";
}
function viewReportTab()
{
    ReportLoader();
}

function downloadFile(status)
{
    window.open("filedirar.fin?cmdAction=provideFile&outPutFile=" + status);
}

function resetData()
{
    viewAddTab();
}

function showGetListFile(filePath)
{
    window.open("filedirar.fin?cmdAction=provideGetListFile&outPutFile="+filePath, "_blank");
}

function onGetListLoad()
{
    var reportGrid = new dhtmlXGridObject("getListGrid");
    reportGrid.setImagePath(getFinLibPath()+"/dhtmlxSuite4/codebase/imgs/");
    reportGrid.setHeader("<b>PATH</b>,<b>TYPE</b>,<b>SIZE (BYTES)</b>,<b>LAST MODIFIED</b>");
    reportGrid.setInitWidths("485,150,150,200");
    reportGrid.setColAlign("left,center,center,center");
    reportGrid.setColTypes("ro,ro,ro,ro");
    reportGrid.setColSorting("str,str,int,subdate_custom"); 
    reportGrid.enableAutoWidth(true);
    reportGrid.enableAutoHeight(true);
    reportGrid.enablePaging(true,10,5, "getListPagingArea",true,"getListRecinfoArea");
    reportGrid.setPagingWTMode(true,true,true,[10,20,50,100]);
    reportGrid.setPagingSkin("toolbar");
    reportGrid.enableMultiline(true);
    reportGrid.init();
    reportGrid.enableCSVAutoID(true);
    reportGrid.post("filedirar.fin?cmdAction=provideFile&outPutFile="+document.getElementById("inpGetListPath").value,"","", "csv");
}