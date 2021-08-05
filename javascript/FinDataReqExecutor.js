






var myPopup;

function displayViewMasterGrid()
{
    document.getElementById("tblAddReport").innerHTML = "";
    document.getElementById("txtViewDataReqId").value = document.getElementById("txtViewDataReqId").value.replace(/^\s+|\s+$/g, '');
    document.getElementById("txtViewPurpose").value = document.getElementById("txtViewPurpose").value.replace(/^\s+|\s+$/g, '');
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
            pagingPlace: 'closedPagerDiv',
            pagingRows: [10, 20, 30],
            pagingID: 'pagerId',
            //filter: true,
            //filterSkipColumns: new Array('thPurpose','thRollbkOnErr','thEntryDate','thStatus','thQuery','thExecBy','thExecHost','thExecDate'),
            size: 10
        });
    });
}

function loadAddCombos()
{
    loadComboNew("cmbAddProjId", "", "", "datareqexecutorForm");
    loadComboNew("cmbAddReqId", "", "", "datareqexecutorForm");
    loadComboNew("cmbAddExecType", "", "", "datareqexecutorForm");
    loadComboNew("cmbAddDatabase", "", "", "datareqexecutorForm");
}

function AddLoader()
{    
    document.getElementById("tblAddReport").innerHTML = "";
    document.getElementById("tblViewReport").innerHTML = "";
    var param = getFormData(document.datareqexecutorForm);
    getSynchronousData('findatareqexec.fin?cmdAction=addLoader', param, 'load');
    document.getElementById("ProjectSpecification").style.display = "";
    document.getElementById("QuerySpecification").style.display = "none";
    document.getElementById("Finish").style.display = "none";
    document.getElementById("mainTab_1").className = "selected";
    document.getElementById("mainTab_2").className = "";
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
    document.getElementById("txtAddPurpose").value = document.getElementById("txtAddPurpose").value.replace(/^\s+|\s+$/g, '');
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
        var param = "projId=" + document.getElementById("cmbAddProjId").value;
        getSynchronousData('findatareqexec.fin?cmdAction=getActualServers', param, 'divPrjMapResult');
        document.getElementById("divPrjMapResult").innerHTML = document.getElementById("divPrjMapResult").innerHTML.replace(/^\s+|\s+$/g, '');
        if (document.getElementById("cmbAddTmpDb"))
        {
            document.getElementById("cmbAddDatabase").innerHTML = document.getElementById("cmbAddTmpDb").innerHTML.replace(/^\s+|\s+$/g, '');
            document.getElementById("hdnUSessionId").value = new Date().getTime();
            document.getElementById("ProjectSpecification").style.display = "none";
            document.getElementById("QuerySpecification").style.display = "";
            document.getElementById("Finish").style.display = "none";
        }
        else if (document.getElementById("tmpPrjUsrMapRes"))
        {
            alert(document.getElementById("tmpPrjUsrMapRes").value);
            return;
        }
        document.getElementById("divPrjMapResult").innerHTML = "";
    }
}

function getLeftPosition(inputObj)
{
    var left = inputObj.offsetLeft;
    while ((inputObj = inputObj.offsetParent) != null)
    {
        left += inputObj.offsetLeft;
    }
    return left;
}

function getTopPosition(inputObj)
{
    var top = inputObj.offsetTop;
    while ((inputObj = inputObj.offsetParent) != null)
    {
        top += inputObj.offsetTop;
    }
    return top;
}

function showPopup()
{
    var element = document.getElementById("supportState");
    var x = getLeftPosition(element) + (element.offsetWidth / 2) - 8;
    var y = getTopPosition(element) - element.offsetHeight + 5;

    var w = 200;
    var h = 70;

    if (!myPopup)
    {
        myPopup = new dhtmlXPopup({
            mode: "left"
        });

        myPopup.attachHTML(
                "<html>" +
                "    <body>" +
                "        <ul>" +
                "            <li>" +
                "                <span style='text-decoration: underline'><b>DDL Statements</b></span>" +
                "                <ul>" +
                "                    <li style='padding-left: 20px;'>" +
                "                        <b>CREATE</b>" +
                "                    </li>" +
                "                    <ul>" +
                "                        <li style='padding-left: 40px;'>" +
                "                            FUNCTION, INDEX, PROCEDURE, TABLE, TRIGGER [ MYSQL ]" +
                "                        </li>" +
                "                        <li style='padding-left: 40px;'>" +
                "                            FUNCTION, INDEX, PACKAGE, PACKAGE BODY, PROCEDURE, SEQUENCE, TABLE, TRIGGER, TYPE, TYPE BODY [ ORACLE ]" +
                "                        </li>" +
                "                    </ul>" +
                "                    <li style='padding-left: 20px;'>" +
                "                        <b>CREATE OR REPLACE</b>" +
                "                    </li>" +
                "                    <ul>" +
                "                        <li style='padding-left: 40px;'>" +
                "                            FUNCTION, PACKAGE, PACKAGE BODY, PROCEDURE, TRIGGER, TYPE, TYPE BODY [ ORACLE ]" +
                "                        </li>" +
                "                    </ul>" +
                "                    <li style='padding-left: 20px;'>" +
                "                        <b>ALTER</b>" +
                "                    </li>" +
                "                    <ul>" +
                "                        <li style='padding-left: 40px;'>" +
                "                            TABLE, VIEW [ MYSQL ]" +
                "                        </li>" +
                "                        <li style='padding-left: 40px;'>" +
                "                            INDEX, SEQUENCE, TABLE, VIEW [ ORACLE ]" +
                "                        </li>" +
                "                    </ul>" +
                "                    <li style='padding-left: 20px;'>" +
                "                        <b>DROP</b>" +
                "                    </li>" +
                "                    <ul>" +
                "                        <li style='padding-left: 40px;'>" +
                "                            FUNCTION, PROCEDURE, TRIGGER [ MYSQL ]" +
                "                        </li>" +
                "                        <li style='padding-left: 40px;'>" +
                "                            FUNCTION, PROCEDURE, TRIGGER [ ORACLE ]" +
                "                        </li>" +
                "                    </ul>" +
                "                </ul>" +
                "            </li>" +
                "            <li>" +
                "                <span style='text-decoration: underline'><b>DML Statements</b></span>" +
                "                <ul>" +
                "                    <li style='padding-left: 20px;'>" +
                "                        <b>INSERT</b>" +
                "                    </li>" +
                "                    <li style='padding-left: 20px;'>" +
                "                        <b>UPDATE</b>" +
                "                    </li>" +
                "                    <li style='padding-left: 20px;'>" +
                "                        <b>DELETE</b>" +
                "                    </li>" +
                "                </ul>" +
                "            </li>" +
                "        </ul>" +
                "    </body>" +
                "</html>"
                );
    }

    if (!myPopup.isVisible())
    {
        myPopup.show(x, y, w, h);
    }
    else
    {
        myPopup.hide();
    }
}

var depPopup;
function showDepPopup(elementID, queryID, tab)
{
    var element = document.getElementById(elementID);
    var x = getLeftPosition(element);
    var y = getTopPosition(element) - (element.offsetHeight * 2) - 10;
    var w = 200;
    var h = 70;

    if (!depPopup)
    {
        depPopup = new dhtmlXPopup({
            mode: "left"
        });
    }

    if (depPopup && depPopup.isVisible())
    {
        depPopup.hide();
    }
    else
    {
        document.getElementById("divDepResult").innerHTML = "";
        var param = "queryId=" + queryID;
        param += "&tab=" + tab;
        getSynchronousData('findatareqexec.fin?cmdAction=getDependencyRecords', param, 'divDepResult');
        document.getElementById("divDepResult").innerHTML = Trim(document.getElementById("divDepResult").innerHTML);
        depPopup.attachHTML(document.getElementById("divDepResult").innerHTML);
        depPopup.show(x, y, w, h);
    }
}

function alertForDDL()
{
    if (!document.getElementById("rdoAddVerifyNone").checked)
    {
        if (!confirm("DDL statements will not Rollback on Syntax Verification Server. Continue?"))
        {
            document.getElementById("rdoAddVerifyNone").checked = true;
        }
    }
}

function showInputTr()
{
    if (document.getElementById("rdoTextarea").checked)
    {
        document.getElementById("trTextarea").style.display = "";
        document.getElementById("trFile").style.display = "none";
    }
    else if (document.getElementById("rdoFile").checked)
    {
        document.getElementById("trTextarea").style.display = "none";
        document.getElementById("trFile").style.display = "";
    }
}

function ViewLoader()
{
    document.getElementById("tblAddReport").innerHTML = "";
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
    document.getElementById("mainTab_2").className = "selected";
    document.getElementById("mainTab_1").className = "";
}

function insertTempData()
{
    document.getElementById("tblAddReport").innerHTML = document.getElementById("tblAddReport").innerHTML.replace(/^\s+|\s+$/g, '');
    if (document.getElementById("cmbAddExecType").value != "3" || (document.getElementById("cmbAddExecType").value == "3" && document.getElementById("tblAddReport").innerHTML == ""))
    {
        document.getElementById("txtAddQuery").value = document.getElementById("txtAddQuery").value.replace(/^\s+|\s+$/g, '');
        if (document.getElementById("cmbAddDatabase").value == "-1")
        {
            alert("Please Select Server");
            document.getElementById("cmbAddDatabase").focus();
            return;
        }

        if (document.getElementById("rdoTextarea").checked && document.getElementById("txtAddQuery").value == "")
        {
            alert("Please Enter Query");
            document.getElementById("txtAddQuery").focus();
            return;
        }
        else if (document.getElementById("rdoFile").checked && (!document.getElementsByName("queryFileName")[0] || document.getElementsByName("queryFileName")[0].value == ""))
        {
            alert("Please Upload File");
            document.getElementById("fileQuery").focus();
            return;
        }

        if (document.getElementById("rdoTextarea").checked)
        {
            if (document.getElementsByName("queryFileName")[0])
            {
                delete_current_row_filebox(document.getElementsByName("queryFileName")[0], 'tbl_fileQuery');
            }
            //Format Query Text
            var param = "rdoInput=" + document.getElementById("rdoTextarea").value + "&queryText=" + encodeURIComponent(document.getElementById("txtAddQuery").value);
            getSynchronousData('findatareqexec.fin?cmdAction=formatQuery', param, 'divformatResult');
            document.getElementById("txtAddQuery").value = Trim(document.getElementById("txtAddFmtQry").value);
            document.getElementById("txtAddFmtQry").value = "";

            //Analyze Query & Insert Data
            document.getElementById("txtAddQuery").value = encodeURIComponent(document.getElementById("txtAddQuery").value);
            param = getFormData(document.datareqexecutorForm);
            document.getElementById("txtAddQuery").value = decodeURIComponent(document.getElementById("txtAddQuery").value);
            getSynchronousData('findatareqexec.fin?cmdAction=insertTempData', param, 'divAnalysisResult');
            document.getElementById("tblAddReport").innerHTML = "";
            param = "hdnUserSession=" + document.getElementById("hdnUSessionId").value;
            getSynchronousData('findatareqexec.fin?cmdAction=getAddRecords', param, 'tblAddReport');
            loadAddCombos();
            document.getElementById("divAnalysisResult").innerHTML = Trim(document.getElementById("divAnalysisResult").innerHTML);
            if (document.getElementById("tmpPrjDbMapRes") && document.getElementById("tmpPrjDbMapRes").value != "")
            {
                alert(document.getElementById("tmpPrjDbMapRes").value);
            }
            else if (document.getElementById("analysisMsg") && document.getElementById("analysisMsg").value != "")
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
            else if (document.getElementById("analysisClear"))
            {
                document.getElementById("cmbAddDatabase").value = "-1";
                document.getElementById("rdoAddVerifyNone").checked = true;
                document.getElementById("txtAddQuery").value = "";
            }
            document.getElementById("divAnalysisResult").innerHTML = "";
        }
        else if (document.getElementById("rdoFile").checked)
        {
            //Analyze Query & Insert Data
            document.getElementById("txtAddQuery").value = "";
            param = getFormData(document.datareqexecutorForm);
            getSynchronousData('findatareqexec.fin?cmdAction=insertTempData', param, 'divAnalysisResult');
            document.getElementById("tblAddReport").innerHTML = "";
            param = "hdnUserSession=" + document.getElementById("hdnUSessionId").value;
            getSynchronousData('findatareqexec.fin?cmdAction=getAddRecords', param, 'tblAddReport');
            loadAddCombos();
            delete_current_row_filebox(document.getElementsByName("queryFileName")[0], 'tbl_fileQuery');
            document.getElementById("divAnalysisResult").innerHTML = Trim(document.getElementById("divAnalysisResult").innerHTML);
            if (document.getElementById("tmpPrjDbMapRes") && document.getElementById("tmpPrjDbMapRes").value != "")
            {
                alert(document.getElementById("tmpPrjDbMapRes").value);
            }
            else if (document.getElementById("analysisMsg") && document.getElementById("analysisMsg").value != "")
            {
                document.getElementById("rdoFile").checked = true;
                showInputTr();
                alertMsg = "";
                if (document.getElementById("analysisQuery") && document.getElementById("analysisQuery").value != "")
                {
                    alertMsg = "Invalid Query :\n" + document.getElementById("analysisQuery").value + "\nReason : ";
                }
                alertMsg += document.getElementById("analysisMsg").value;
                alert(alertMsg);
            }
            else if (document.getElementById("analysisClear"))
            {
                document.getElementById("cmbAddDatabase").value = "-1";
                document.getElementById("rdoAddVerifyNone").checked = true;
                document.getElementById("txtAddQuery").value = "";
            }
            document.getElementById("divAnalysisResult").innerHTML = "";
        }
    }
    else if (document.getElementById("cmbAddExecType").value == "3")
    {
        alert("You cannot add Multiple Batches when 'Rollback on Error (Only DML & Single Server)' is Selected");
        return;
    }
}

function selectAllBackupChk()
{
    var hdnBkups = document.getElementsByName("hdnAddBackup");
    var chks = document.getElementsByName("chkAddBackup");
    for (var i = 0; i < hdnBkups.length; i++)
    {
        if (document.getElementById("chkSelAllBkup").checked)
        {
            document.getElementById(hdnBkups.item(i).id).value = hdnBkups.item(i).id.replace("hdnAddBackup", "");
            document.getElementById(chks.item(i).id).checked = true;
        }
        else
        {
            document.getElementById(hdnBkups.item(i).id).value = "NA";
            document.getElementById(chks.item(i).id).checked = false;
        }
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

function selectAllLogTableChk()
{
    if (document.getElementById("chkSelAllLTab").checked)
    {
        if (!confirm("Log trigger(s) will be created for CREATE TABLE query for below events :\n - AFTER INSERT\n - AFTER UPDATE\n - AFTER DELETE\nAre You sure you want to continue ?"))
        {
            document.getElementById("chkSelAllLTab").checked = false;
            return;
        }
    }

    var hdnLogTabReq = document.getElementsByName("hdnAddLogTable");
    var chks = document.getElementsByName("chkAddLogTable");
    for (var i = 0; i < hdnLogTabReq.length; i++)
    {
        if (document.getElementById("chkSelAllLTab").checked)
        {
            document.getElementById(hdnLogTabReq.item(i).id).value = hdnLogTabReq.item(i).id.replace("hdnAddLogTable", "");
            document.getElementById(chks.item(i).id).checked = true;
        }
        else
        {
            document.getElementById(hdnLogTabReq.item(i).id).value = "NA";
            document.getElementById(chks.item(i).id).checked = false;
        }
    }
}

function chkAddLogTableOnclick(checkbox, id)
{
    if (checkbox.checked)
    {
        if (confirm("Log trigger(s) will be created on this table for below events :\n - AFTER INSERT\n - AFTER UPDATE\n - AFTER DELETE\nAre You sure you want to continue ?"))
        {
            document.getElementById("hdnAddLogTable" + id).value = id;
        }
        else
        {
            checkbox.checked = false;
        }
    }
    else
    {
        document.getElementById("hdnAddLogTable" + id).value = "NA";
    }
}

function deleteBatch(batchId)
{
    if (confirm("Are you sure you want to Remove this batch ?"))
    {
        document.getElementById("tblAddReport").innerHTML = "";
        var param = getFormData(document.datareqexecutorForm);
        param += "&batchId=" + batchId;
        getSynchronousData('findatareqexec.fin?cmdAction=deleteBatch', param, 'tblAddReport');
        loadAddCombos();
    }
}

function confirmRequest(status)
{
    if (document.getElementById("hdnConfirmation") && document.getElementById("hdnConfirmation").value == "YES")
    {
        if (confirm("Your data request is marked as Critical as it contains dependencies. (See Info to know more)\nAre you sure you want to Submit Request ?"))
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
    else
    {
        if (confirm("Are you sure you want to Submit Request ?"))
        {
            param = getFormData(document.datareqexecutorForm);
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
    if (document.getElementById("hdnConfirmation" + id) && document.getElementById("hdnConfirmation" + id).value == "Y")
    {
        if (confirm("This data request is marked as Critical as it contains dependencies. (See Info to know more)\nAre you sure you want to Execute Request ?"))
        {
            getSynchronousData('exec_findatareqexec.fin?cmdAction=executeRequest&dataReqId=' + id, null, 'tblViewReport');
            if (document.getElementById("tblViewReport").innerHTML.toString().indexOf("!! ACCESS DENIED !!", 0) != -1)
            {
                alert("Authentication Failed ! You do not have rights to execute this Data Request.");
            }
            else if (document.getElementById("executeResult"))
            {
                alert(document.getElementById("executeResult").value);
            }
            displayViewMasterGrid();
        }
    }
    else
    {
        if (confirm("Are you sure you want to Execute Request ?"))
        {
            getSynchronousData('exec_findatareqexec.fin?cmdAction=executeRequest&dataReqId=' + id, null, 'tblViewReport');
            if (document.getElementById("tblViewReport").innerHTML.toString().indexOf("!! ACCESS DENIED !!", 0) != -1)
            {
                alert("Authentication Failed ! You do not have rights to execute this Data Request.");
            }
            else if (document.getElementById("executeResult"))
            {
                alert(document.getElementById("executeResult").value);
            }
            displayViewMasterGrid();
        }
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
        var colspan = "6";
        if (document.getElementById("linkShowHide").innerHTML == "Hide Syntax Verification Details")
        {
            display = "none";
            colspan = "7";
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

function reportLoader()
{
    document.getElementById("tblAddReport").innerHTML = "";
    document.getElementById("tblViewReport").innerHTML = "";
    document.getElementById("mainTab_1").className = "";
    document.getElementById("mainTab_2").className = "";
    document.getElementById("mainTab_3").className = "selected";
    document.getElementById("load").innerHTML = "";
    getSynchronousData('findatareqexec.fin?cmdAction=reportLoader',null,'load');    
}