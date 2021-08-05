/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
function checkAliasName(f)
{
    var params=getFormData(f);
    getSynchronousData("mastergenerator.fin?cmdAction=validateAlias" , params, "alias");
}
function checkTableName()
{
    var tableName = document.getElementById('tableName').value;
    if(compare_string_rule_01(tableName)&&tableName!=null&&tableName!="")
    {
        var params=getFormData(document.menuForm);
        getAsynchronousData("mastergenerator.fin?cmdAction=getTableNames", params, "selectTableNameTd");
    }
}
function submitMenu()
{
    if(checkMenu())
    {
        showSpecifications();
    }
}
function checkMenu(){
    var projectname = document.getElementById('projectName').value;
    var module = document.getElementById('moduleName').value;
    var alias = document.getElementById('aliasName').value;
    var table = document.getElementById('selectTableName').value;
    var valid = true;
    if(projectname!=null&&projectname!=""&&compare_string_rule_alpha_number(projectname))
    {
        if(compare_string_rule_alpha(module)&&module!=null&&module!="")
        {
            if(compare_string_rule_01(alias)&&alias!=null&&alias!=""&&valid==true)
            {
                if(!(table=="Select Table"))
                {
                    return true;
                }
                else
                {
                    alert("Select Table");
                    return false;
                }
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
function showSpecifications()
{
    document.getElementById("menu").style.display="none";
    document.getElementById("specifications").style.display="";
    //document.getElementById("generateMaster").style.display="";
    var params=getFormData(document.menuForm);
    getAsynchronousData("mastergenerator.fin?cmdAction=showSpecifications", params, "specifications");
}
function submitSpecifications()
{
    generateMaster();
}

function generateMaster()
{
    document.getElementById("specifications").style.display="none";
    var params = getFormData(document.menuForm);
    getAsynchronousData("mastergenerator.fin?cmdAction=generateMaster", params, "generateMaster");
}
function showMenu()
{
    document.getElementById("menu").style.display="";
    document.getElementById("specifications").style.display="none";
    document.getElementById("generateMaster").style.display="none";
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
    $( "#aliasName" ).autocomplete({
        source: availableAlias
    });
});