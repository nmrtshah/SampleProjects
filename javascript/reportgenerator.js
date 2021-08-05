/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
function checkAliasName(f)
{
    var params=getFormData(f);
    getDataValid("mastergeneratorV2.fin?cmdAction=validateAlias" , params);
}

function submitSpecifications()
{
    if(checkSpecifications())
    {
        showView();
    }
}

function checkSpecifications()
{
    var inputNameV=document.getElementsByName("inputName");
    for(var i=0;i<inputNameV.length;i++)
    {
        if(inputNameV[i].value.length == 0)
        {

            alert("Please enter control name (One Compulsory).");
            inputNameV[i].focus();
            return false;
        }
    }
    return true;
}
function checkView()
{
    var grid = document.getElementById('grid').checked;
    var pdf = document.getElementById('pdf').checked;
    var xls = document.getElementById('xls').checked;
    var addControl = document.getElementById('addControl').checked;

    if(grid==true)
    {
        if(addControl==true)
        {
            var indexNum= document.getElementById('indexNumber').value;
            if(compare_string_rule_number(indexNum)&& indexNum!=0)
            {
                return true;
            }
            else
            {
                alert("Incorrect Index Number");
                return false;
            }
        }
        else{
            return true;
        }
    }
    if(pdf==true)
    {
        return true;
    }
    if(xls==true)
    {
        return true;
    }
    return false;
}
function submitCreateMenu()
{
    if(checkCreateMenu())
    {
        showSpecifications();
    }
}

function submitView()
{
    if(checkView())
    {
        showReport();
    }
}
function checkCreateMenu(){
    var projectname = document.getElementById('projectnameid').value;
    var module = document.getElementById('modulenameid').value;
    var alias = document.getElementById('aliasnameid').value;
    var query = document.getElementById('queryid').value;
    var valid = true;
    if(projectname!=null&&projectname!=""&&compare_string_rule_alpha_number(projectname)){
        if(compare_string_rule_alpha(module)&&module!=null&&module!=""){
            if(compare_string_rule_01(alias)&&alias!=null&&alias!=""&&valid==true){
                if(query!=null&&query!=""){
                    return true;
                }
                else{
                    alert("Incorrect Query");
                    return false;
                }
                return true;
            }
            else{
                alert("Incorrect Alias Name");
                return false;
            }
        }
        else {
            alert("Incorrect Module Name");
            return false;
        }
    }
    else{
        alert("Incorrect Project Name");
        return false;
    }
}
function addRowToTable()
{
    var tbl = document.getElementById('addControlTable');
    var lastRow = tbl.rows.length;
    var row = tbl.insertRow(lastRow-1);

    row.innerHTML =
    "<td align=\"right\">"+
    "Control :"+
    "<br>"+
    "Column :"+
    "<br>"+
    "Index :"+
    "</td>"+
    "<td align=\"left\">"+
    "<select id=\"selectControl\" name=\"selectControl\">"+
    "<option value=\"checkbox\">Checkbox</option>"+
    "<option value=\"link\">Link</option>"+
    "<option value=\"radio\">Radio</option>"+
    "<option value=\"text\">Textbox</option>"+
    "</select>"+
    "<br/>"+
    document.getElementById('addControlDiv').innerHTML +
    "<br/>"+
    "<input align=\"left\" size=\"19\" type=\"text\" name=\"indexNumber\" id=\"indexNumber\">"+
    "</td>"
}

function addRowToFooterTable()
{
    var tbl = document.getElementById('addGroupFooterTable');
    var lastRow = tbl.rows.length;
    var row = tbl.insertRow(lastRow-1);
    row.innerHTML = ""
    +"<td align=\"right\">Column :<br>Calculation :</td><td align=\"left\">"+document.getElementById('addFooterDiv').innerHTML +"<br>"
    +"<select id=\"calculation\" name=\"calculation\">"+
    "<option value=\"min\">Minimum</option>"+
    "<option value=\"max\">Maximum</option>"+
    "<option value=\"sum\">Sum</option>"+
    "<option value=\"avg\">Average</option>"+
    "<option value=\"count\">Count</option>"+
    "</select></td>";

}
function addRowToVariable()
{
    var tbl = document.getElementById('variableDivTable');
    var lastRow = tbl.rows.length;
    var row = tbl.insertRow(lastRow-1);
    row.innerHTML = ""+
"<td align=\"right\" width=\"50%\">"+
"Input Name :"+
"<br>"+
"Input Control :"+
"</td>"+
"<td align=\"left\">"+
"<input type=\"text\" name=\"inputName\" id=\"inputName\" size=\"19\" onblur=\"javascript: compare_string_rule_alpha_number(document.getElementById(\"inputName\"));>"+
"<br>"+
"<select id=\"inputControl\" name=\"inputControl\">"+
"<option value=\"checkbox\">Checkbox</option>"+
"<option value=\"combobox\">Combobox</option>"+
"<option value=\"file\">File</option>"+
"<option value=\"radio\">Radio</option>"+
"<option value=\"text\">Textbox</option>"+
"</select>"+
"</td>"
}
function removeRowFromVariable()
{
    var tbl = document.getElementById('variableDivTable');
    var lastRow = tbl.rows.length;
    if (lastRow > 2) tbl.deleteRow(lastRow - 2);
}
function removeRowFromTable()
{
    var tbl = document.getElementById('addControlTable');
    var lastRow = tbl.rows.length;
    if (lastRow > 2) tbl.deleteRow(lastRow - 2);
}
function removeRowFromFooterTable()
{
    var tbl = document.getElementById('addGroupFooterTable');
    var lastRow = tbl.rows.length;
    if (lastRow > 2) tbl.deleteRow(lastRow - 2);
}
function showSpecifications()
{

    document.getElementById("createMenu").style.display="none";
    document.getElementById("specifications").style.display="";
    var params=getFormData(document.reportgenerator);
    //alert(params);
    getAsynchronousData("reportgenerator.fin?cmdAction=specifications", params, "specifications");
}
function showView()
{
    document.getElementById("specifications").style.display="none";
    document.getElementById("view").style.display="";
    var paramsrg=getFormData(document.reportgenerator);
    var paramsspec=getFormData(document.specificationform);
    getAsynchronousData("reportgenerator.fin?cmdAction=view", paramsrg+"&"+paramsspec, "view");
}
function showGridOptions()
{
    if(document.getElementById("grid").checked == true)
    {
        document.getElementById("gridOptionsTable").style.display="";
    }
    else
    {
        document.getElementById("gridOptionsTable").style.display="none";
    }
}
                    
function showReport()
{
    document.getElementById("view").style.display="none";
    document.getElementById("generateReport").style.display="";
    var paramsrg=getFormData(document.reportgenerator);
    var paramsspec=getFormData(document.specificationform);
    var paramsview=getFormData(document.viewform);
    //alert(paramsrg+"  "+paramsopt);
    getAsynchronousData("reportgenerator.fin?cmdAction=generateReport", paramsrg+"&"+paramsspec+"&"+paramsview, "generateReport");

}
function showMenuTable()
{
    document.getElementById("generateReport").style.display="none";
    document.getElementById("specifications").style.display="none";
    document.getElementById("createMenu").style.display="";
}
function showSpecificationsTable()
{
    document.getElementById("createMenu").style.display="none";
    document.getElementById("view").style.display="none";
    document.getElementById("specifications").style.display="";

}
function hideGridOptionsTable()
{
    document.getElementById("gridOptionsTable").style.display="none";
    document.getElementById("addControlTable").style.display="none";
    document.getElementById("recordCountTable").style.display="none";
}
function showAddControl()
{
    if(document.getElementById("addControl").checked == true)
    {
        document.getElementById("addControlTable").style.display="";
    }
    else
    {
        document.getElementById("addControlTable").style.display="none";
    }
}
function showAddGroupFooter()
{
    if(document.getElementById("groupFooter").checked == true)
    {
        document.getElementById("addGroupFooterTable").style.display="";
    }
    else
    {
        document.getElementById("addGroupFooterTable").style.display="none";
    }
}
function showRecordCountQuery()
{
    if(document.getElementById("paging").checked == true)
    {
        document.getElementById("recordCountTable").style.display="";
    }
    else
    {
        document.getElementById("recordCountTable").style.display="none";
    }
}
$(function() {
    var availableAlias = [
    "njindiainvest_offline_dbadmin",
    "mftran",
    "insurance",
    "registration_online",
    "njrealty",
    "njhr",
    "fas_portfolioplus",
    "pms",
    "portfolioplus",
    "compliance",
    "fas_gurukul",
    "inventory_offline",
    "wfms2",
    "fd_mysql",
    "mfund_mis",
    "insurance_mysql",
    "fas_upto_0607",
    "mfund",
    "inventory_online",
    "mftran",
    "njindiainvest_online",
    "finlogic",
    "acl",
    "fas",
    "nj_fundztrax_offline",
    "njmis_mfund",
    "fd",
    "registration",
    "fas_realty",
    "finutility_mfund",
    "newfas",
    "dbadmin",
    "finutility_mftran",
    "njindiainvest_offline",
    "reporting",
    "fas_partner",
    "wfms",
    "inservice",
    ];
    $( "#aliasnameid" ).autocomplete({
        source: availableAlias
    });
});
