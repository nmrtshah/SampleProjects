
function checkAliasName(f)
{
    var params=getFormData(f);
    getSynchronousData("mastergeneratorV2.fin?cmdAction=validateAlias" , params, "alias");
}
function checkMasterTableName()
{
    var tableName = document.getElementById('masterTableName').value;
    if(compare_string_rule_01(tableName)&&tableName!=null&&tableName!="")
    {
        var params=getFormData(document.menuForm);
        getAsynchronousData("mastergeneratorV2.fin?cmdAction=getMasterTableNames", params, "selectMasterTableNameTd");
    }
}
function checkDetailTableName()
{
    var tableName = document.getElementById('detailTableName').value;
    if(compare_string_rule_01(tableName)&&tableName!=null&&tableName!="")
    {
        var params=getFormData(document.menuForm);
        getAsynchronousData("mastergeneratorV2.fin?cmdAction=getDetailTableNames", params, "selectDetailTableNameTd");
    }
}
function showMenu()
{
    document.getElementById("menu").style.display="";
    document.getElementById("specifications").style.display="none";
    document.getElementById("generateMaster").style.display="none";
}
function submitMenu()
{
    if(checkMenu())
    {
        showSpecification();
    }
}
function checkMenu(){
    var projectname = document.getElementById('projectName').value;
    var module = document.getElementById('moduleName').value;
    var alias = document.getElementById('aliasName').value;
    var mastertable = document.getElementById('selectMasterTableName').value;
    var detailtable = document.getElementById('selectDetailTableName').value;
    var valid = true;
    if(projectname!=null&&projectname!=""&&compare_string_rule_alpha_number(projectname))
    {
        if(compare_string_rule_alpha(module)&&module!=null&&module!="")
        {
            if(compare_string_rule_01(alias)&&alias!=null&&alias!=""&&valid==true)
            {
                if(!(mastertable=="Select Table"))
                {
                    if(!(detailtable=="Select Table"))
                    {
                        return true;
                    }
                    else
                    {
                        alert("Select Deatil Table");
                        return false;
                    }
                }
                else
                {
                    alert("Select Master Table");
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

function showSpecification()
{
    document.getElementById("menu").style.display="none";
    document.getElementById("specifications").style.display="";
    var params=getFormData(document.menuForm);
    getAsynchronousData("mastergeneratorV2.fin?cmdAction=showSpecification", params, "specifications");
}
function showSpecificationDetailTable()
{
    document.getElementById("specificationMasterTable").style.display="none";
    document.getElementById("specificationDetailTable").style.display="";
}
function showSpecificationMasterTable()
{
    document.getElementById("specificationMasterTable").style.display="";
    document.getElementById("specificationDetailTable").style.display="none";
}
function submitSpecification()
{
    document.getElementById("specificationMasterTable").style.display="none";
    document.getElementById("specificationDetailTable").style.display="none";
    document.getElementById("generateMaster").style.display="";
    var params=getFormData(document.menuForm);
    getAsynchronousData("mastergeneratorV2.fin?cmdAction=generateMaster", params, "generateMaster");
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
