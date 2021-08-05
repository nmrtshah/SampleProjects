/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function onloadMyFunction()
{
    loadComboNew("cmbDB","","","MenuForm");
    loadComboNew("cmbObjType","","","MenuForm");
    loadComboNew("cmbObjName","","","MenuForm");
}

function fillDBCombo()
{
    document.getElementById("divDetail").innerHTML = "";
    document.getElementById("divTblDep").innerHTML = "";
    document.getElementById("divOtherObjDetail").innerHTML = "";
    
    document.getElementById("cmbDB").value = "-1";
    document.getElementById("cmbObjType").value = "-1";
    document.getElementById("txtObjName").value = "";
    document.getElementById("cmbObjName").value = "-1";
    
    document.getElementById("tblObjExtraFields").style.display = "none";
    document.getElementById("chkShowDef").checked = false;
    document.getElementById("chkShowDepend").checked = false;
    
    var frm = document.MenuForm;
    if (Trim(frm.txtDB.value).length >= 2)
    {
        var params = getFormData(frm);
        getDataFilterNew("dbmdi.fin?cmdAction=getDBNames", "cmbDB", params, false);
    }
    else
    {
        frm.cmbDB.options.length = 1;
        frm.cmbObjType.options.length = 1;
        frm.txtObjName.value = "";
        frm.cmbObjName.options.length = 1;
    }
}

function fillObjType()
{
    document.getElementById("divDetail").innerHTML = "";
    document.getElementById("divTblDep").innerHTML = "";
    document.getElementById("divOtherObjDetail").innerHTML = "";
    document.getElementById("tblObjExtraFields").style.display = "none";
    document.getElementById("chkShowDef").checked = false;
    document.getElementById("chkShowDepend").checked = false;

    var frm = document.MenuForm;
    frm.cmbObjName.options.length = 1;
    frm.txtObjName.value = "";
    var params = getFormData(frm);
    getDataFilterNew("dbmdi.fin?cmdAction=getDBType", "cmbObjType" ,params,false);
}

function fillObjName()
{
    document.getElementById("divDetail").innerHTML = "";
    document.getElementById("divTblDep").innerHTML = "";
    document.getElementById("divOtherObjDetail").innerHTML = "";
    document.getElementById("tblObjExtraFields").style.display = "none";

    var frm = document.MenuForm;
    //frm.cmbObjName.options.length=1;
    // frm.txtObjName.value="";
    if (document.getElementById("cmbDB").value == -1)
    {
        alert("Please Select Database");
        document.getElementById("cmbDB").focus();
        return false;
    }
    else if (document.getElementById("cmbObjType").value == -1)
    {
        alert("Please Select Object Type");
        document.getElementById("cmbObjType").focus();
        return false;
    }
    if (Trim(frm.txtObjName.value).length > 2)
    {
        var params = getFormData(frm);
        getDataFilterNew("dbmdi.fin?cmdAction=getObjName", "cmbObjName" , params,false);        
    }
    else
    {
        frm.cmbObjName.options.length = 1;
    }
    return true;
}

function resetObjName()
{
    document.getElementById("divDetail").innerHTML = "";
    document.getElementById("divTblDep").innerHTML = "";
    document.getElementById("divOtherObjDetail").innerHTML = "";

    var frm = document.MenuForm;
    frm.cmbObjName.options.length = 1;
    frm.txtObjName.value = "";

    if (document.getElementById("cmbObjType").value == "MTRIGGER" || document.getElementById("cmbObjType").value == "OTRIGGER")
    {
        document.getElementById("tdObjName").innerHTML = "Table Name :";
    }
    else
    {
        document.getElementById("tdObjName").innerHTML = "Object Name :";
    }

    if (document.getElementById("cmbObjType").value != "MTABLE" || document.getElementById("cmbObjType").value != "OTABLE")
    {
        document.getElementById("tblObjExtraFields").style.display = "none";
    }
    if(document.getElementById("cmbObjType").value == "MTABLE" || document.getElementById("cmbObjType").value == "OTABLE"
        || document.getElementById("cmbObjType").value == "MTRIGGER" || document.getElementById("cmbObjType").value == "OTRIGGER"
        || document.getElementById("cmbObjType").value == "OSEQUENCE")
        {
        document.getElementById("cmbObjName").multiple = false;                
        document.getElementById("cmbObjName").style.height = "20px";
    }
    else
    {
        document.getElementById("cmbObjName").multiple = true;        
        document.getElementById("cmbObjName").style.height = "50px";
    }
}

function getDetail()
{
    if (document.getElementById("cmbDB").value == -1)
    {
        alert("Please Select Database");
        document.getElementById("cmbDB").focus();
        return false;
    }
    else if (document.getElementById("cmbObjType").value == -1)
    {
        alert("Please Select Object Type");
        document.getElementById("cmbObjType").focus();
        return false;
    }
    var cmbObj = document.getElementById("cmbObjName");
    var flag = false;
    for(var i = 0; i < cmbObj.options.length; i++ )
    {
        if(cmbObj.options[i].selected)
        {
            flag = true;
        }
    }    
    if (!flag || cmbObj.value == "-1")
    {
        alert("Please Select Object Name");
        document.getElementById("cmbObjName").focus();
        return false;
    }
    
    var cmbSeltext = cmbObj.options[cmbObj.selectedIndex].innerHTML;
    if ((document.getElementById("cmbObjType").value == "MTABLE" || document.getElementById("cmbObjType").value == "OTABLE")
        && (cmbSeltext.match(" \\(VIEW\\)") == null && cmbSeltext.match(" \\(MVIEW\\)") == null))
        {
        if (document.getElementById("chkShowDef").checked == false && document.getElementById("chkShowDepend").checked == false)
        {
            alert("Please Select Show Data");
            document.getElementById("chkShowDef").focus();
            return false;
        }
    }

    document.getElementById("divDetail").innerHTML = "";
    document.getElementById("divTblDep").innerHTML = "";
    document.getElementById("divDetail").style.display = "";

    var param = getFormData(document.MenuForm);

    if ((document.getElementById("cmbObjType").value == "MTABLE" || document.getElementById("cmbObjType").value == "OTABLE")
        && (cmbSeltext.match(" \\(VIEW\\)") != null || cmbSeltext.match(" \\(MVIEW\\)") != null))
        {
        document.getElementById("divOtherObjDetail").style.display = "";
        if (cmbSeltext.match(" \\(MVIEW\\)") != null)
        {
            param += "&type=MVIEW";
        }
        else
        {
            param += "&type=VIEW";
        }
        getSynchronousData("dbmdi.fin?cmdAction=getObjDefination", param, "divOtherObjDetail");
    }
    else if (document.getElementById("cmbObjType").value != "MTABLE" && document.getElementById("cmbObjType").value != "OTABLE")
    {
        document.getElementById("divOtherObjDetail").style.display = "";
        getSynchronousData("dbmdi.fin?cmdAction=getObjDefination", param, "divOtherObjDetail");
    }
    else if ((document.getElementById("cmbObjType").value == "MTABLE" || document.getElementById("cmbObjType").value == "OTABLE")
        && (cmbSeltext.match(" \\(VIEW\\)") == null || cmbSeltext.match(" \\(MVIEW\\)") == null))
        {
        if (document.getElementById("chkShowDef").checked)
        {
            getSynchronousData("dbmdi.fin?cmdAction=getDBDefinition",param,"divDetail");
        }
        //if dependecy checked get all dependencies
        if (document.getElementById("chkShowDepend").checked)
        {
            document.getElementById("divTblDep").style.display = "";
            getSynchronousData("dbmdi.fin?cmdAction=getDependencies", param, "divTblDep");
        }
    }
    return true;
}

function showTablePro()
{
       
    document.getElementById("divDetail").innerHTML = "";
    document.getElementById("divTblDep").innerHTML = "";
    document.getElementById("divOtherObjDetail").innerHTML = "";
    document.getElementById("chkShowDef").checked = false;
    document.getElementById("chkShowDepend").checked = false;

    var cmbObj = document.getElementById("cmbObjName");
    var flag = false;
    for(var i = 0; i < cmbObj.options.length; i++ )
    {
        if(cmbObj.options[i].selected)
        {
            flag = true;
        }
    }    
    if (flag)
    {        
        //show def-dependent prop        
        var cmbSeltext = cmbObj.options[cmbObj.selectedIndex].innerHTML;

        if ((document.getElementById("cmbObjType").value == "MTABLE" || document.getElementById("cmbObjType").value == "OTABLE")
            && cmbSeltext.match(" \\(VIEW\\)") == null && cmbSeltext.match(" \\(MVIEW\\)") == null)
            {
            document.getElementById("tblObjExtraFields").style.display = "";
            document.getElementById("divOtherObjDetail").innerHTML = "";
        }
        else
        {
            document.getElementById("tblObjExtraFields").style.display = "none";
        }
    }
}

function onChkDepend(isChk)
{
    if (isChk)
    {
        document.getElementById("cacheRow").style.display = "";
    }
    else
    {
        document.getElementById("cacheRow").style.display = "none";
    }
    document.getElementById("chkChaching").checked = false;
}
