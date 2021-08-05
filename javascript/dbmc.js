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

    var frm = document.MenuForm;
    if (Trim(frm.txtDB.value).length >= 2)
    {
        var params = getFormData(frm);
        getDataFilterNew("dbmc.fin?cmdAction=getDBNames", "cmbDB", params, false);
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

    var frm = document.MenuForm;
    frm.cmbObjName.options.length = 1;
    frm.txtObjName.value = "";
    var params = getFormData(frm);
    getDataFilterNew("dbmc.fin?cmdAction=getDBType", "cmbObjType" ,params,false);
}
function resetObjName()
{
    
    document.getElementById("divDetail").innerHTML = "";
    document.getElementById("divTblDep").innerHTML = "";
    document.getElementById("divOtherObjDetail").innerHTML = "";

    var frm = document.MenuForm;
    frm.cmbObjName.options.length = 1;
    frm.txtObjName.value = "";
    var elem = document.getElementById("cmbObjType");
    selectedNode = elem.options[elem.selectedIndex];
    if (selectedNode.value === 'MTABLE' || selectedNode.value === 'OTABLE')
    {
        document.getElementById("lblObjName").innerHTML = "Table :";
        return true;
    }
    else if (selectedNode.value === 'MPROCEDURE' || selectedNode.value === 'OPROCEDURE')
    {
        document.getElementById("lblObjName").innerHTML = "Procedure :";
        return true;
    }
    else if (selectedNode.value === 'MFUNCTION' || selectedNode.value === 'OFUNCTION')
    {
        document.getElementById("lblObjName").innerHTML = "Function :";
        return true;
    }
    else if (selectedNode.value === 'MTRIGGER' || selectedNode.value === 'OTRIGGER')
    {
        document.getElementById("lblObjName").innerHTML = "Trigger :";
        return true;
    }
    else
    {
        document.getElementById("lblObjName").innerHTML = "Object Name :";
    }
    document.getElementById("lblObjName").innerHTML = "Object Name :";

}
function fillObjName()
{
    document.getElementById("divDetail").innerHTML = "";
    document.getElementById("divTblDep").innerHTML = "";
    document.getElementById("divOtherObjDetail").innerHTML = "";


    var frm = document.MenuForm;
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
    else if(!document.getElementsByName('server')[0].checked && !document.getElementsByName('server')[1].checked)
    {
        alert("Please Select Server");
        document.getElementById("server").focus();
        return false;
    }
    if (Trim(frm.txtObjName.value).length > 2)
    {
        var params = getFormData(frm);
        getDataFilterNew("dbmc.fin?cmdAction=getObjName", "cmbObjName" , params,false);
    }
    else
    {
        frm.cmbObjName.options.length = 1;
    }
    return true;
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
    else if (document.getElementById("cmbObjName").value == -1)
    {
        alert("Please Select Object Name");
        document.getElementById("cmbObjName").focus();
        return false;
    }

    var cmbObj = document.getElementById('cmbObjName');
    var cmbSeltext = cmbObj.options[cmbObj.selectedIndex].innerHTML;
    document.getElementById("divDetail").innerHTML = "";
    document.getElementById("divTblDep").innerHTML = "";
    document.getElementById("divDetail").style.display = "";

    var param = getFormData(document.MenuForm);

    //    if ((document.getElementById("cmbObjType").value == "MTABLE" || document.getElementById("cmbObjType").value == "OTABLE")
    //        && (cmbSeltext.match(" \\(VIEW\\)") != null || cmbSeltext.match(" \\(MVIEW\\)") != null))
    //        {
    //        document.getElementById("divOtherObjDetail").style.display = "";
    //        if (cmbSeltext.match(" \\(MVIEW\\)") != null)
    //        {
    //            param += "&type=MVIEW";
    //        }
    //        else
    //        {
    //            param += "&type=VIEW";
    //        }
    //        getSynchronousData("dbmc.fin?cmdAction=getObjDefination", param, "divOtherObjDetail");
    //    }
    //    else
    if ((document.getElementById("cmbObjType").value == "MTABLE" || document.getElementById("cmbObjType").value == "OTABLE")
        && (cmbSeltext.match(" \\(VIEW\\)") == null || cmbSeltext.match(" \\(MVIEW\\)") == null))
        {
        getSynchronousData("dbmc.fin?cmdAction=getDBDefinition",param,"divDetail");
    }
    else if((document.getElementById("cmbObjType").value == "MFUNCTION" || document.getElementById("cmbObjType").value == "MPROCEDURE" || document.getElementById("cmbObjType").value == "OFUNCTION" || document.getElementById("cmbObjType").value == "OPROCEDURE" || document.getElementById("cmbObjType").value == "OTRIGGER" || document.getElementById("cmbObjType").value == "MTRIGGER") )
    {
        getSynchronousData("dbmc.fin?cmdAction=getNewObjDefination",param,"divDetail");
         diffUsingJS(0);
       
    }
    return true;
}
function showTablePro()
{
    document.getElementById("divDetail").innerHTML = "";
    document.getElementById("divTblDep").innerHTML = "";

    var cmbObj = document.getElementById('cmbObjName');
    var cmbSeltext = cmbObj.options[cmbObj.selectedIndex].innerHTML;
}
