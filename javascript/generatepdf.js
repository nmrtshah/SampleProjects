function AddLoader()
{
    document.getElementById("clientDiv").style.display = "";
    document.getElementById("generatePdfDiv").style.display = "none";
    document.getElementById("reportDiv").style.display = "none";
    getSynchronousData('generatepdfClientAdd.fin?cmdAction=clientAddPage', '', 'clientDiv');
}

function GeneratePdfLoader()
{
    document.getElementById("generatePdfDiv").style.display = "";
    document.getElementById("clientDiv").style.display = "none";
    document.getElementById("reportDiv").style.display = "none";
    getSynchronousData('generatepdf.fin?cmdAction=generateCertificatePage', '', 'generatePdfDiv');
    loadDatePicker('activation_date');
    loadDatePicker('expiry_date');
}

function ReportLoader()
{
    document.getElementById("reportDiv").style.display = "";
    document.getElementById("clientDiv").style.display = "none";
    document.getElementById("generatePdfDiv").style.display = "none";

    getSynchronousData('generatepdfReport.fin?cmdAction=getReportPage', '', 'reportDiv');
}
function getReport()
{
    document.getElementById("reportDiv").style.display = "";
    document.getElementById("clientDiv").style.display = "none";
    document.getElementById("generatePdfDiv").style.display = "none";

    mygrid = new dhtmlXGridObject("gridbox");
    mygrid.setImagePath(getFinLibPath() + "dhtmlxSuite/dhtmlxGrid/codebase/imgs/");
//    mygrid.setImagePath(getFinLibPath() + "dhtmlxSuite4/sources/dhtmlxGrid/codebase/imgs/");
    mygrid.setHeader("<b>SRNO</b>,<b>VENDOR ID</b>,<b>CLIENT NAME</b>,<b>ADDRESS</b>,<b>ACTIVATION DATE</b>,<b>EXPIRY DATE</b>,<b>CERTIFICATE GENERATE DATE</b>,<b>CERTIFICATE ENTRY</b>,<b>PASSWORD</b>,<b>SYSTEM KEY</b>,<b>CLIENT ENTRY DATE</b>,<b>CLIENT ENTRY BY</b>");
    mygrid.setInitWidths("0,70,70,80,80,80,80,80,150,200,70,70,70");
    mygrid.setColAlign("center,center,left,center,left,left,left,left,center,left,left,left,left");
    mygrid.setColTypes("ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro");
    mygrid.enableAutoWidth(true);
    mygrid.enableAutoHeight(true);
    mygrid.enableColSpan(true);
    mygrid.setSkin("dhx_web");
    mygrid.init();
    mygrid.setDateFormat("%d.%m.%Y");
    var param = getFormData(document.generatereportform);
    mygrid.post("generatepdfReport.fin?cmdAction=reportLoader", param, "", "json");
}

function validateData(process)
{
    if (process === 'client')
    {
        if (document.getElementById("clientname").value === null || document.getElementById("clientname").value === "")
        {
            alert("Please Enter 'Client Name'");
            document.getElementById("clientname").focus();
            return false;
        }
        if (document.getElementById("address").value === null || document.getElementById("address").value === "")
        {
            alert("Please Enter 'Address'");
            document.getElementById("address").focus();
            return false;
        }
        return true;
    }

    if (process === 'clientdetail')
    {
        if (!validate_dropdown(document.generatecertificateform, "clientcmbgen", "Client name", true))
        {
            return false;
        }
        if (document.getElementById("syskey").value === null || document.getElementById("syskey").value === "")
        {
            alert("Please Enter 'Key'");
            document.getElementById("syskey").focus();
            return false;
        }
        var actDateValue = document.getElementById("activation_date").value.split('-');
        var expDateValue = document.getElementById("expiry_date").value.split('-');
        var actDate = new Date(actDateValue[2], actDateValue[1] - 1, actDateValue[0]);
        var expDate = new Date(expDateValue[2], expDateValue[1] - 1, expDateValue[0]);

        if (actDate > expDate)
        {
            alert("Expiry Date should be greater than Activation Date.")
            return false;
        }
        if (document.getElementById("comment").value === null || document.getElementById("comment").value === "")
        {
            alert("Please Enter 'Comment'");
            document.getElementById("comment").focus();
            return false;
        }
        return true;
    }

    if (process === 'GenLicense')
    {
        if (document.getElementById("txtTenantId").value === null || document.getElementById("txtTenantId").value === "")
        {
            alert("Please Enter 'Tenant ID'");
            document.getElementById("txtTenantId").focus();
            return false;
        }

        if (!validate_dropdown(document.generatecertificateform, "cmbServerName", "Server name", true))
        {
            return false;
        }

        var actDateValue = document.getElementById("activationdate").value.split('-');
        var expDateValue = document.getElementById("expirydate").value.split('-');
        var actDate = new Date(actDateValue[2], actDateValue[1] - 1, actDateValue[0]);
        var expDate = new Date(expDateValue[2], expDateValue[1] - 1, expDateValue[0]);

        if (actDate >= expDate)
        {
            alert("Expiry Date should be greater than Activation Date.")
            return false;
        }
        if (document.getElementById("txtComment").value === null || document.getElementById("txtComment").value === "")
        {
            alert("Please Enter 'Comment'");
            document.getElementById("txtComment").focus();
            return false;
        }
        return true;
    }
    return false;
}

function validateClientData()
{
    if (validateData("client"))
    {
        var param = getFormData(document.clientaddform);
        getSynchronousData('generatepdfClientAdd.fin?cmdAction=insertClient', param, 'msg');
        if (document.getElementById("msg").innerHTML.toString().indexOf("successfully") > -1)
        {
            alert((document.getElementById("msg").innerHTML).trim());
        }
        else
        {
            alert("Client is not added successfully");
        }
        AddLoader();
    }
}

function validateClientDetail()
{
    if (validateData("clientdetail"))
    {
        var param = getFormData(document.generatecertificateform);
        document.getElementById("generatePdfDiv").style.display = "";
        document.getElementById("clientDiv").style.display = "none";
        document.getElementById("reportDiv").style.display = "none";

        getSynchronousData('generatepdf.fin?cmdAction=insertClientDetail', param, 'generatePdfDiv');
        loadDatePicker('activation_date');
        loadDatePicker('expiry_date');
        document.getElementById("successmsg").style.display = "block";
    }
}


function onProjectChange()
{
    if (document.getElementById("cmbProject").selectedIndex == 0)
    {
        alert("Please make selection for 'Certificate for'. ");
        document.getElementById("genpdf").style.display = "none";
        document.getElementById("genLicense").style.display = "none";
        document.getElementById("cmbProject").focus();
        return false;
    }
    else
    {
        if (document.getElementById("cmbProject").value == 'mfbrok')
        {
            document.getElementById("genpdf").style.display = "block";
            document.getElementById("genLicense").style.display = "none";
        }
        else if (document.getElementById("cmbProject").value == 'eservice')
        {
            document.getElementById("genLicense").style.display = "block";
            document.getElementById("genpdf").style.display = "none";
        }
        loadDatePicker('activationdate');
        loadDatePicker('expirydate');
        if (document.getElementById("errmsg"))
        {
            document.getElementById("errmsg").innerHTML = "";
        }
        if (document.getElementById("successmsg")) {
            document.getElementById("successmsg").innerHTML = "";
        }
    }

}


function validateTenantDetail()
{
    if (validateData("GenLicense"))
    {
        var param = getFormData(document.generatecertificateform);
        document.getElementById("generatePdfDiv").style.display = "";
        document.getElementById("clientDiv").style.display = "none";
        document.getElementById("reportDiv").style.display = "none";

        getSynchronousData('generatepdf.fin?cmdAction=generateLicense', param, 'generatePdfDiv');
        loadDatePicker('activation_date');
        loadDatePicker('expiry_date');
        document.getElementById("successmsg").style.display = "block";
    }
}