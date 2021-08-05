function validateAppName()
{
    document.getElementById("txtAppName").value = document.getElementById("txtAppName").value.replace(/^\s+|\s+$/g,'');
    if (document.getElementById("txtAppName").value == '')
    {
        alert("Please Enter Application Name");
        return false;
    }
    else if (!document.getElementById("txtAppName").value.match(/^[A-Za-z0-9 ]+$/))
    {
        alert("Please Enter Only [A-Za-z0-9 ] characters in Application Name");
        document.getElementById("txtAppName").focus();
        return false;
    }
    else
    {
        return true;
    }
}

function getVersion()
{
    if (validateAppName())
    {
        var params = getFormData(document.MenuForm);
        getData_sync("mobapps.fin?cmdAction=getVersion", "divVersion", params, false);
        document.getElementById("txtVersion").value = document.getElementById("hdnVersion").value;
    }
}

function validateVersion()
{
    document.getElementById("txtVersion").value = document.getElementById("txtVersion").value.replace(/\s/g,'');
    if (document.getElementById("txtVersion").value == '')
    {
        alert("Please Enter Application Version");
        document.getElementById("txtVersion").focus();
        return false;
    }
    else if (!document.getElementById("txtVersion").value.match(/^[0-9]{1,2}.[0-9]{1,2}$/))
    {
        alert("Please Enter Proper Version");
        document.getElementById("txtVersion").focus();
        return false;
    }
    else
    {
        var first = document.getElementById("txtVersion").value;
        var second = document.getElementById("hdnVersion").value;
        if (parseFloat(first) < parseFloat(second))
        {
            alert("You Can't Choose Version Less Than " + second);
            document.getElementById("txtVersion").focus();
            return false;
        }
        else
        {
            return true;
        }
    }
}

function validatePackage()
{
    document.getElementById("txtPackage").value = document.getElementById("txtPackage").value.replace(/\s/g,'');
    if (document.getElementById("txtPackage").value == '')
    {
        alert("Please Enter Package Name");
        document.getElementById("txtPackage").focus();
        return false;
    }
    else if (!document.getElementById("txtPackage").value.match(/^([A-Za-z0-9]+.)*([A-Za-z0-9]+)$/))
    {
        alert("Please Enter Valid Package Name");
        document.getElementById("txtPackage").focus();
        return false;
    }
    else
    {
        return true;
    }
}

function validateWelcomeFile()
{
    document.getElementById("txtWelcomeFile").value = document.getElementById("txtWelcomeFile").value.replace(/^\s+|\s+$/g,'');
    if (document.getElementById("txtWelcomeFile").value == '')
    {
        alert("Please Enter Welcome File From Your Source");
        document.getElementById("txtWelcomeFile").focus();
        return false;
    }
    else
    {
        var file = document.getElementById("txtWelcomeFile").value;
        if (file.substr(file.length - 5, file.length - 1).toLowerCase() != ".html" && file.substr(file.length - 4, file.length - 1).toLowerCase() != ".htm")
        {
            alert("Please Enter .html Page as your Welcome Page");
            document.getElementById("txtWelcomeFile").focus();
            return false;
        }
        else
        {
            file = file.substr(0, file.length - 4) + file.substr(file.length - 4, file.length - 1).toLowerCase();
            if (!file.match(/^(([A-Za-z0-9 ]+)\/)*[A-Za-z0-9 ]+.(html|htm)$/))
            {
                alert("Please Enter Valid Path for .html Page as your Welcome Page");
                document.getElementById("txtWelcomeFile").focus();
                return false;
            }
            else
            {
                return true;
            }
        }
    }
}

function validateSource()
{
    if (document.getElementById("fSource").value == '')
    {
        alert("Please Select Source Folder");
        document.getElementById("fSource").focus();
        return false;
    }
    else
    {
        var file = document.getElementById("fSource").value;
        if (file.substr(file.length - 4, file.length - 1).toLowerCase() != ".zip")
        {
            alert("Please Enter .zip Folder");
            document.getElementById("fSource").focus();
            return false;
        }
        else
        {
            var size = getFileSize(document.getElementById("fSource"));
            if (typeof size != "undefined")
            {
                if (size > 10485760)
                {
                    alert("Source Folder's Size is Very Large.");
                    document.getElementById("fSource").focus();
                    return false;
                }
                else
                {
                    return true;
                }
            }
            else
            {
                return false;
            }
        }
    }
}

function getFileSize(input)
{
    if (typeof window.FileReader !== 'function')
    {
        alert("The file API isn't supported on this browser yet.");
        return;
    }
    var file = input.files[0];
    return file.size;
}

function generateApps(f)
{
    if (!validateAppName())
    {
        return false;
    }
    else if (!validateVersion())
    {
        return false;
    }
    else if (!validatePackage())
    {
        return false;
    }
    else if (!validateWelcomeFile())
    {
        return false;
    }
    else if (!validateSource())
    {
        return false;
    }
    else if (!(document.getElementById("chkAndroid").checked || document.getElementById("chkBBerry").checked || document.getElementById("chkSymbian").checked))
    {
        alert("Please Select OS for Which You Want To Generate Application");
        document.getElementById("chkAndroid").focus();
        return false;
    }
    document.getElementById("btnGenerate").disabled = true;
    f.action = "mobapps.fin?cmdAction=createApp";
    f.submit();
}

function onCancel()
{
    window.location = "welcome.fin?cmdAction=subMenu&action=development";
}
