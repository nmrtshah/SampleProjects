/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function onloadETLGrid() {

    var myGrid;
    myGrid = new dhtmlXGridObject('gridbox');

    myGrid.setImagePath(getFinLibPath() + "/dhtmlxSuite4/sources/dhtmlxGrid/codebase/imgs/");
    myGrid.setSkin("dhx_web");
    myGrid.setHeader("<b>Sr. No.</b>,<b>SRNO</b>,<b>Table Name</b>,<b>From Server</b>,<b>To server</b>,<b>From Schema</b>,<b>To Table</b>,<b>To Schema</b>,<b>KJB</b>,<b>KTR</b>,<b>Paths</b>,<b>Cron File</b>,<b>Main Job</b>,<b>Type</b>,<b>Cron Tab</b>");
    myGrid.setInitWidths("50,50,100,100,100,100,100,100,200,200,300,200,400,100,450");
    myGrid.setColAlign("center,center,left,left,left,left,left,left,left,left,left,left,left,left,left");
    myGrid.setColTypes("ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro");
    myGrid.setColSorting("na,na,na,na,na,na,na,na,na,na,na,na,na,na,na");
    myGrid.enableAutoHeight(true);
    myGrid.enableAutoWidth(true);
    myGrid.setColumnHidden(1, true);
    myGrid.enablePaging(true, 20, 5, "pagingArea", true, "recinfoArea");
    myGrid.setPagingWTMode(true, true, true, [25, 50, 75, 100]);
    myGrid.setPagingSkin("toolbar", "dhx_web");
    myGrid.setSkin("dhx_web");
    myGrid.init();
    myGrid.post("etljoblist.fin?cmdAction=viewReport", "", "", "json");
}

function onApplyClick() {

    if (document.getElementById("tableName").value.trim() === "")
    {
        value_common_alert("Table Name");
        return false;
    }
    if (document.getElementById("fromServer").value.trim() === "")
    {
        value_common_alert("From Server");
        return false;
    }
    if (document.getElementById("toServer").value.trim() === "")
    {
        value_common_alert("To server");
        return false;
    }
    if (document.getElementById("fromSchema").value.trim() === "")
    {
        value_common_alert("From Schema");
        return false;
    }
    if (document.getElementById("toTable").value.trim() === "")
    {
        value_common_alert("To Table");
        return false;
    }
    if (document.getElementById("toSchema").value.trim() === "")
    {
        value_common_alert("To Schema");
        return false;
    }
    if (document.getElementById("kjb").value.trim() === "")
    {
        value_common_alert("KJB");
        return false;
    }
    if (document.getElementById("paths").value.trim() === "")
    {
        value_common_alert("Paths");
        return false;
    }

    var param = getalldata(document.MenuForm);
    getData_sync('etljobmaster.fin?cmdAction=insertETLJob', 'response', param, false);
    if (document.getElementById("statusinsert") !== null && document.getElementById("statusinsert").value === "true") {
        alert("ETL Job Added Successfully!");
        window.location = "etljobmaster.fin?cmdAction=getMenu&page=master";
    } else {
        document.getElementById("insertMenu").style.display = 'none';
    }
}

function onResetClick() {
    window.location = "etljobmaster.fin?cmdAction=getMenu&page=master";
}