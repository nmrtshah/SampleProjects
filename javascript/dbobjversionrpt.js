/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* global Trim */

function onloadMyFunction()
{
    loadComboNew("cmbDB", "", "", "MenuForm");
    loadComboNew("cmbObjType", "", "", "MenuForm");
    loadComboNew("cmbObjName", "", "", "MenuForm");
    loadComboNew("cmbfrmObjVersion", "", "", "MenuForm");
    loadComboNew("cmbtoObjVersion", "", "", "MenuForm");

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
        getDataFilterNew("dbobjversionrpt.fin?cmdAction=getDBNames", "cmbDB", params, false);
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
    getDataFilterNew("dbobjversionrpt.fin?cmdAction=getDBType", "cmbObjType", params, false);
}

function fillObjName()
{
    document.getElementById("divDetail").innerHTML = "";
    document.getElementById("divTblDep").innerHTML = "";
    document.getElementById("divOtherObjDetail").innerHTML = "";


    var frm = document.MenuForm;
    if (document.getElementById("cmbDB").value === -1)
    {
        alert("Please Select Database");
        document.getElementById("cmbDB").focus();
        return false;
    }
    else if (document.getElementById("cmbObjType").value === -1)
    {
        alert("Please Select Object Type");
        document.getElementById("cmbObjType").focus();
        return false;
    }
    if (Trim(frm.txtObjName.value).length > 2)
    {
        var params = getFormData(frm);
        getDataFilterNew("dbobjversionrpt.fin?cmdAction=getObjName", "cmbObjName", params, false);
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

function showReport()
{
    if (document.getElementById("cmbDB").value === '-1')
    {
        alert("Please Select Database.");
        return  false;
    }
    if (document.getElementById("cmbObjType").value === '-1')
    {
        alert("Please Select Object Type.");
        return  false;
    }
    if (document.getElementById("cmbObjName").value === '-1')
    {
        alert("Please Select Object Name.");
        return  false;
    }
    var param = getFormData(document.MenuForm);
    getSynchronousData("dbobjversionrpt.fin?cmdAction=getReport", param, "divDetail");
    $(document).ready(function ()
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

function getDiff(reqID)
{
    window.open('dbobjversionrpt.fin?cmdAction=getdiff&dataReqid=' + reqID, '_blank');

}

function diffJS(viewType) {
    document.getElementById('datadiv').style.display = "block";
    "use strict";
    var byId = function (id) {
        return document.getElementById(id);
    },
            base = difflib.stringAsLines(byId("oldDef").value),
            newtxt = difflib.stringAsLines(byId("newDef").value),
            sm = new difflib.SequenceMatcher(base, newtxt),
            opcodes = sm.get_opcodes(),
            diffoutputdiv = byId("diffoutput");

    diffoutputdiv.innerHTML = "";


    var basetxt, newdtxt;

    basetxt = 'Old-Definition';
    newdtxt = 'New-Definition';

    diffoutputdiv.appendChild(diffview.buildView({
        baseTextLines: base,
        newTextLines: newtxt,
        opcodes: opcodes,
        baseTextName: basetxt,
        newTextName: newdtxt,
        viewType: viewType
    }));
}