/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function viewLoader() { }

function validateEntry()
{
    // validation for 'Server Name'
    var serverName = $("#serverName").val();
    if (serverName == -1)
    {
        valid_select_alert("Server Name");
        $("#serverName").focus();
        return false;
    }
    // validation for 'Plain Text'
    var plainTextValue = Trim($("#plainText").val());
    if (plainTextValue == "")
    {
        value_common_alert("Plain Text");
        $("#plainText").focus();
        return false;
    }
    return true;
}

function encrypt()
{
    if (!validateEntry())
        return false;
    var param = getFormData(document.encUtilForm);
    
    getSynchronousData("encdec.fin?action=encrypt", param, "resultMsg");
    $("#encText").val(Trim($("#resultMsg").text()));
}

function resetAll()
{
    $("#serverName").val(-1);
    $("#plainText").val("");
    $("#encText").val("");
}