function displayViewMasterGrid()
{
    document.getElementById("txtViewDataReqId").value = document.getElementById("txtViewDataReqId").value.replace(/^\s+|\s+$/g,'');
    document.getElementById("txtViewPurpose").value = document.getElementById("txtViewPurpose").value.replace(/^\s+|\s+$/g,'');
    if (document.getElementById("txtViewDataReqId").value == "")
    {
        if (document.getElementById("cmbViewStatus").value != "Pending" && document.getElementById("cmbViewProjId").value == "-1" && document.getElementById("cmbViewEmp").value == "-1" && document.getElementById("cmbViewReqId").value == "-1")
        {
            alert("        Please Enter Data Request ID\n                           Or\nSelect Project Or Employee Or Request");
            document.getElementById("txtViewDataReqId").focus();
            document.getElementById("tblViewReport").innerHTML = "";
            return;
        }
    }
    else
    {
        var id = document.getElementById("txtViewDataReqId").value;
        if (!(id.match(/^[0-9]+$/)))
        {
            alert("Invalid Data Request ID! Please Enter Numeric Characters Only...");
            document.getElementById("txtViewDataReqId").focus();
            document.getElementById("tblViewReport").innerHTML = "";
            return;
        }
    }
    var param = getFormData(document.datareqexecutorForm);
    getSynchronousData('findatareqexec.fin?cmdAction=viewReport', param, 'tblViewReport');
    $(document).ready(function()
    {
        $("#rptgrid").tablesorter({
            pagingPlace:'closedPagerDiv',
            pagingRows:[10,20,30],
            pagingID:'pagerId',
            //filter:true,
            //filterSkipColumns:new Array('thPurpose','thRollbkOnErr','thEntryDate','thStatus','thQuery','thExecBy','thExecHost','thExecDate'),
            size:10
        });
    });
}

function loadAddCombos()
{
    loadComboNew("cmbAddProjId", "", "", "datareqexecutorForm");
    loadComboNew("cmbAddReqId", "", "", "datareqexecutorForm");
    loadComboNew("cmbAddExecType", "", "", "datareqexecutorForm");
    loadComboNew("cmbAddServer", "", "", "datareqexecutorForm");
    loadComboNew("cmbAddDatabase", "", "", "datareqexecutorForm");
    loadComboNew("cmbAddVerifyServer", "", "", "datareqexecutorForm");
}

function AddLoader()
{
    document.getElementById("tblViewReport").innerHTML = "";
    var param = getFormData(document.datareqexecutorForm);
    getSynchronousData('findatareqexec.fin?cmdAction=addLoader', param, 'load');
    document.getElementById("ProjectSpecification").style.display = "";
    document.getElementById("QuerySpecification").style.display = "none";
    document.getElementById("Finish").style.display = "none";
    loadAddCombos();
}

function fillAddRequest()
{
    if (document.getElementById("cmbAddProjId").value != "-1" || document.getElementById("txtAddReqId").value != "")
    {
        var param = "";
        if (document.getElementById("txtAddReqId").value != "")
        {
            param = "reqFilter=" + document.getElementById("txtAddReqId").value;
        }
        else
        {
            param = "projId=" + document.getElementById("cmbAddProjId").value;
        }
        getDataFilterNew('findatareqexec.fin?cmdAction=getAddRequests', 'cmbAddReqId', param, false);
    }
}

function showQuerySpecification()
{
    document.getElementById("txtAddPurpose").value = document.getElementById("txtAddPurpose").value.replace(/^\s+|\s+$/g,'');
    if (document.getElementById("cmbAddProjId").value == "-1")
    {
        alert("Please Select Project");
        document.getElementById("cmbAddProjId").focus();
        return;
    }
    else if (document.getElementById("cmbAddExecType").value == "-1")
    {
        alert("Please Select Execution Type");
        document.getElementById("cmbAddExecType").focus();
        return;
    }
    else
    {
        document.getElementById("hdnUSessionId").value = new Date().getTime();
        document.getElementById("ProjectSpecification").style.display = "none";
        document.getElementById("QuerySpecification").style.display = "";
        document.getElementById("Finish").style.display = "none";
    }
}

function fillDatabases()
{
    if (document.getElementById("cmbAddServer").value == "-1")
    {
        alert("Please Select Server");
        document.getElementById("cmbAddServer").focus();
        document.getElementById("cmbAddDatabase").innerHTML = "<option value=\"-1\">-- Select Database --</option>";
        return;
    }
    else
    {
        var param = getFormData(document.datareqexecutorForm);
        getDataFilterNew('findatareqexec.fin?cmdAction=getDatabaseType', 'divDbType', param, false);
        getDataFilterNew('findatareqexec.fin?cmdAction=getDatabases', 'cmbAddDatabase', param, false);
    }
}

function ViewLoader()
{
    document.getElementById("tblViewReport").innerHTML = "";
    var param = getFormData(document.datareqexecutorForm);
    getSynchronousData('findatareqexec.fin?cmdAction=viewLoader', param, 'load');
    loadComboNew("cmbViewProjId", "", "", "datareqexecutorForm");
    loadComboNew("cmbViewEmp", "", "", "datareqexecutorForm");
    loadComboNew("cmbViewReqId", "", "", "datareqexecutorForm");
    loadComboNew("cmbViewStatus", "", "", "datareqexecutorForm");
    loadDatePicker("dtFromDate");
    loadDatePicker("dtToDate");
    var today = document.getElementById("dtFromDate").value;
    document.getElementById("dtFromDate").value = "01-01-2013";
    displayViewMasterGrid();
    document.getElementById("dtFromDate").value = today;
}

function insertTempData()
{
    document.getElementById("tblAddReport").innerHTML = document.getElementById("tblAddReport").innerHTML.replace(/^\s+|\s+$/g,'');
    if (document.getElementById("cmbAddExecType").value != "3" || (document.getElementById("cmbAddExecType").value == "3" && document.getElementById("tblAddReport").innerHTML == ""))
    {
        document.getElementById("txtAddQuery").value = document.getElementById("txtAddQuery").value.replace(/^\s+|\s+$/g,'');
        if (document.getElementById("cmbAddServer").value == "-1")
        {
            alert("Please Select Server");
            document.getElementById("cmbAddServer").focus();
            document.getElementById("cmbAddDatabase").innerHTML = "<option value=\"-1\">-- Select Database --</option>";
            return;
        }
        else if (document.getElementById("cmbAddDatabase").value == "-1")
        {
            alert("Please Select Database");
            document.getElementById("cmbAddDatabase").focus();
            return;
        }
        var veriServCap = "-";
        if (document.getElementById("cmbAddVerifyServer").value != "-1")
        {
            var server1 = document.getElementById("cmbAddServer").value.split("-")[1];
            var server2 = document.getElementById("cmbAddVerifyServer").value.split("-")[1];
            if (server1 != server2)
            {
                alert("Server and Syntax Verification Server are of Different Type. Please Make Proper Selection.");
                return;
            }
            var combo = document.getElementById("cmbAddVerifyServer");
            veriServCap = combo.options[combo.selectedIndex].text;
        }
        if (document.getElementById("txtAddQuery").value == "")
        {
            alert("Please Enter Query");
            document.getElementById("txtAddQuery").focus();
            return;
        }

        //Format Query Text
        var param = "queryText=" + encodeURIComponent(document.getElementById("txtAddQuery").value);
        getSynchronousData('findatareqexec.fin?cmdAction=formatQuery', param, 'divformatResult');
        document.getElementById("txtAddQuery").value = Trim(document.getElementById("txtAddFmtQry").value);
        document.getElementById("txtAddFmtQry").value = "";
        
        //Analyze Query
        param = "&dbType=" + document.getElementById("hdnDBType").value + "&queryText=" + encodeURIComponent(document.getElementById("txtAddQuery").value) + "&database=" + document.getElementById("cmbAddDatabase").value;
        getSynchronousData('findatareqexec.fin?cmdAction=analyzeQuery', param, 'divAnalysisResult');
        loadAddCombos();
        document.getElementById("divAnalysisResult").innerHTML = Trim(document.getElementById("divAnalysisResult").innerHTML);
        if (document.getElementById("analysisMsg") && document.getElementById("analysisMsg").value != "")
        {
            var alertMsg = "";
            var searchText = "";
            if (document.getElementById("analysisQuery") && document.getElementById("analysisQuery").value != "")
            {
                alertMsg = "Invalid Query :\n" + document.getElementById("analysisQuery").value + "\nReason : ";
                searchText = document.getElementById("analysisQuery").value;
            }
            alertMsg += document.getElementById("analysisMsg").value;
            alert(alertMsg);
            if (searchText != "")
            {
                var txt = document.getElementById("txtAddQuery");
                var l = txt.value.indexOf(searchText);
                if (l != -1)
                {
                    txt.focus();
                    txt.selectionStart = l;
                    txt.selectionEnd = l + searchText.length;
                }
            }
        }
        else
        {
            if (document.getElementById("cmbAddVerifyServer").value != "-1")
            {
                if (!confirm("DDL statements will not Rollback on " + document.getElementById("cmbAddVerifyServer").options[document.getElementById("cmbAddVerifyServer").selectedIndex].text + ". Continue?"))
                {
                    return;
                }
            }
            document.getElementById("txtAddQuery").value = encodeURIComponent(document.getElementById("txtAddQuery").value);
            document.getElementById("tblAddReport").innerHTML = "";
            param = getFormData(document.datareqexecutorForm);
            combo = document.getElementById("cmbAddServer");
            var servCap = combo.options[combo.selectedIndex].text;
            getSynchronousData('findatareqexec.fin?cmdAction=insertTempData&cmbAddServerCaption=' + servCap + '&cmbAddVerifyServerCaption=' + veriServCap, param, 'load');
            document.getElementById("ProjectSpecification").style.display = "none";
            document.getElementById("QuerySpecification").style.display = "";
            document.getElementById("Finish").style.display = "none";
            loadAddCombos();
        }
    }
    else if (document.getElementById("cmbAddExecType").value == "3")
    {
        alert("You cannot add Multiple Batches when 'Rollback on Error (Only DML & Single Server)' is Selected");
        return;
    }
}

function chkAddBackupOnclick(checkbox, id)
{
    if (checkbox.checked)
    {
        document.getElementById("hdnAddBackup" + id).value = id;
    }
    else
    {
        document.getElementById("hdnAddBackup" + id).value = 'NA';
    }
}

function deleteBatch(batchId)
{
    if (confirm("Are you sure you want to Remove this batch ?"))
    {
        document.getElementById("tblAddReport").innerHTML = "";
        var param = getFormData(document.datareqexecutorForm);
        param += "&batchId=" + batchId;
        getSynchronousData('findatareqexec.fin?cmdAction=deleteBatch', param, 'load');
        document.getElementById("ProjectSpecification").style.display = "none";
        document.getElementById("QuerySpecification").style.display = "";
        document.getElementById("Finish").style.display = "none";
        loadAddCombos();
    }
}

function confirmRequest(status)
{
    if (confirm("Are you sure you want to Submit Request ?"))
    {
        var param = getFormData(document.datareqexecutorForm);
        if (status != '')
        {
            param += "&currentStatus=" + status;
        }
        document.getElementById("tblAddReport").innerHTML = "";
        getSynchronousData('findatareqexec.fin?cmdAction=confirmRequest', param, 'load');
        document.getElementById("ProjectSpecification").style.display = "none";
        document.getElementById("QuerySpecification").style.display = "none";
        document.getElementById("Finish").style.display = "";
    }
}

function cancelAddRequest()
{
    if (confirm("Are you sure you want to Cancel Request ?"))
    {
        document.getElementById("tblAddReport").innerHTML = "";
        var param = getFormData(document.datareqexecutorForm);
        getSynchronousData('findatareqexec.fin?cmdAction=deleteAddRequest', param, 'load');
        document.getElementById("ProjectSpecification").style.display = "";
        document.getElementById("QuerySpecification").style.display = "none";
        document.getElementById("Finish").style.display = "none";
        loadAddCombos();
    }
}

function fillViewRequest()
{
    var param = "projId=" + document.getElementById("cmbViewProjId").value;
    if (document.getElementById("txtViewReqId").value != "")
    {
        param += "&reqFilter=" + document.getElementById("txtViewReqId").value;
    }
    getDataFilterNew('findatareqexec.fin?cmdAction=getViewRequests', 'cmbViewReqId', param, false);
}

function viewQuery(id, status)
{
    window.open("findatareqexec.fin?cmdAction=viewQueryReport&dataReqId=" + id + "&exeStatus=" + status);
}

function executeRequest(id)
{
    if (confirm("Are you sure you want to Execute Request ?"))
    {
        getSynchronousData('exec_findatareqexec.fin?cmdAction=executeRequest&dataReqId=' + id, null, 'tblViewReport');
        if (document.getElementById("tblViewReport").innerHTML.toString().indexOf("!! ACCESS DENIED !!", 0) != -1)
        {
            alert("!! Access Denied !!");
        }
        else if (document.getElementById("executeResult"))
        {
            alert(document.getElementById("executeResult").value);
        }
        displayViewMasterGrid();
    }
}

function cancelViewRequest(id)
{
    if (confirm("Are you sure you want to Cancel Request ?"))
    {
        getSynchronousData('findatareqexec.fin?cmdAction=cancelViewRequest&dataReqId=' + id, null, 'tblViewReport');
        if (document.getElementById("cancelViewResult"))
        {
            alert(document.getElementById("cancelViewResult").value);
        }
        displayViewMasterGrid();
    }
}

function haltViewRequest(id)
{
    if (confirm("Are you sure you want to Halt Request ?"))
    {
        getSynchronousData('findatareqexec.fin?cmdAction=haltViewRequest&dataReqId=' + id, null, 'tblViewReport');
        if (document.getElementById("haltViewResult"))
        {
            alert(document.getElementById("haltViewResult").value);
        }
        displayViewMasterGrid();
    }
}

function resumeViewRequest(id)
{
    if (confirm("Are you sure you want to Resume Request ?"))
    {
        getSynchronousData('findatareqexec.fin?cmdAction=resumeViewRequest&dataReqId=' + id, null, 'tblViewReport');
        if (document.getElementById("resumeViewResult"))
        {
            alert(document.getElementById("resumeViewResult").value);
        }
        displayViewMasterGrid();
    }
}

function createNewRequest(id)
{
    if (confirm("Are you sure you want to Create New Request ?"))
    {
        getSynchronousData('findatareqexec.fin?cmdAction=createNewRequest&dataReqId=' + id, null, 'tblViewReport');
        if (document.getElementById("createNewReqResult"))
        {
            alert(document.getElementById("createNewReqResult").value);
        }
        displayViewMasterGrid();
    }
}

function showVerificationDetail()
{
    var flag = false;
    var synVeri = document.getElementsByName("tdSynVeri");
    for (var i = 0; i < synVeri.length; i++)
    {
        if (synVeri[i].innerHTML.replace("<b>Executed On ", "").replace(" ( for syntax verification )</b>", "") != "-")
        {
            flag = true;
        }
    }
    if (flag)
    {
        var display = "";
        var colspan = "4";
        if (document.getElementById("linkShowHide").innerHTML == "Hide Syntax Verification Details")
        {
            display = "none";
            colspan = "5";
        }
        document.getElementById("thStatus").style.display = display;
        document.getElementById("thResult").style.display = display;
        document.getElementById("thTimeTaken").style.display = display;
        var status = document.getElementsByName("tdStatus");
        var result = document.getElementsByName("tdResult");
        var startTime = document.getElementsByName("tdTimeTaken");
        for (i = 0; i < status.length; i++)
        {
            status[i].style.display = display;
            result[i].style.display = display;
            startTime[i].style.display = display;
        }
        var server = document.getElementsByName("tdServer");
        for (i = 0; i < synVeri.length; i++)
        {
            synVeri[i].style.display = display;
            server[i].colSpan = colspan;
        }
        if (display == "none")
        {
            document.getElementById("linkShowHide").innerHTML = "Show Syntax Verification Details";
        }
        else
        {
            document.getElementById("linkShowHide").innerHTML = "Hide Syntax Verification Details";
        }
    }
}

function onDataRequestEnter(event)
{
    if (event.which != null && event.which == 13)
    {
        displayViewMasterGrid();
    }
}
