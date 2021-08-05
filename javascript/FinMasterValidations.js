/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

var keywords = new Array("abstract",
        "assert",
        "boolean",
        "break",
        "byte",
        "case",
        "catch",
        "char",
        "class",
        "const",
        "continue",
        "default",
        "do",
        "double",
        "else",
        "enum",
        "extends",
        "final",
        "finally",
        "float",
        "for",
        "goto",
        "if",
        "implements",
        "import",
        "instanceof",
        "int",
        "interface",
        "long",
        "native",
        "new",
        "package",
        "private",
        "protected",
        "public",
        "return",
        "short",
        "static",
        "strictfp",
        "super",
        "switch",
        "synchronized",
        "this",
        "throw",
        "throws",
        "transient",
        "try",
        "void",
        "volatile",
        "while"
        );

var invalidExt = new Array("run",
        "exe",
        "sh",
        "jsp",
        "java",
        "class",
        "php",
        "html",
        "js",
        "c",
        "cpp"
        );

function onloadMyFunction()
{
    loadComboNew("cmbProjectName", "", "", "MenuForm");
    loadComboNew("cmbAliasName", "", "", "MenuForm");
    loadComboNew("cmbServerName", "", "", "MenuForm");
    loadComboNew("cmbMasterTable", "", "", "MenuForm");
    loadComboNew("cmbMasterTablePrimKey", "", "", "MenuForm");

    loadComboNew("cmbAddField", "", "", "MenuForm");
    loadComboNew("cmbAddControl", "", "", "MenuForm");
    loadComboNew("cmbAddControlPos", "", "", "MenuForm");
    loadComboNew("cmbAddOnchange", "", "", "MenuForm");
    loadComboNew("cmbAddAlign", "", "", "MenuForm");
    loadComboNew("cmbAddValidation", "", "", "MenuForm");
    loadComboNew("cmbAddWsMethod", "", "", "MenuForm");
    loadComboNew("cmbAddCommonQuery", "", "", "MenuForm");

    loadComboNew("cmbEditField", "", "", "MenuForm");
    loadComboNew("cmbEditControl", "", "", "MenuForm");
    loadComboNew("cmbEditControlPos", "", "", "MenuForm");
    loadComboNew("cmbEditOnchange", "", "", "MenuForm");
    loadComboNew("cmbEditAlign", "", "", "MenuForm");
    loadComboNew("cmbEditValidation", "", "", "MenuForm");
    loadComboNew("cmbEditWsMethod", "", "", "MenuForm");
    loadComboNew("cmbEditCommonQuery", "", "", "MenuForm");

    loadComboNew("cmbDeleteField", "", "", "MenuForm");
    loadComboNew("cmbDeleteControl", "", "", "MenuForm");
    loadComboNew("cmbDeleteControlPos", "", "", "MenuForm");
    loadComboNew("cmbDeleteOnchange", "", "", "MenuForm");
    loadComboNew("cmbDeleteAlign", "", "", "MenuForm");
    loadComboNew("cmbDeleteValidation", "", "", "MenuForm");
    loadComboNew("cmbDeleteWsMethod", "", "", "MenuForm");
    loadComboNew("cmbDeleteCommonQuery", "", "", "MenuForm");

    loadComboNew("cmbViewField", "", "", "MenuForm");
    loadComboNew("cmbViewControl", "", "", "MenuForm");
    loadComboNew("cmbViewControlPos", "", "", "MenuForm");
    loadComboNew("cmbViewOnchange", "", "", "MenuForm");
    loadComboNew("cmbViewAlign", "", "", "MenuForm");
    loadComboNew("cmbViewValidation", "", "", "MenuForm");
    loadComboNew("cmbViewWsMethod", "", "", "MenuForm");
    loadComboNew("cmbViewCommonQuery", "", "", "MenuForm");
}

function showProjectMasterSpecification()
{
    showHide("ProjectSpecification");
}

function showSRSSpecification()
{
    document.getElementById("txtModuleName").value = document.getElementById("txtModuleName").value.replace(/^\s+|\s+$/g, '');
    if (document.getElementById("cmbProjectName").value == '-1')
    {
        alert("Please Select Project Name");
        document.getElementById("cmbProjectName").focus();
        return;
    }
    if (document.getElementById("txtModuleName").value == '')
    {
        alert("Please Enter Module Name");
        document.getElementById("txtModuleName").focus();
        return;
    }
    else if (!document.getElementById("txtModuleName").value.match(/^[A-Za-z0-9 ]+$/))
    {
        alert("Please Enter Only [A-Za-z0-9 ] characters in Module Name");
        document.getElementById("txtModuleName").focus();
        return;
    }
    else
    {
        document.getElementById("txtOrigModulName").value = document.getElementById("txtModuleName").value;
        document.getElementById("txtModuleName").value = document.getElementById("txtModuleName").value.split(' ').join('');
        var module = document.getElementById("txtModuleName").value;
        for (var i = 0; i < keywords.length; i++)
        {
            if (module == keywords[i])
            {
                alert("Module Name Cannot Be Any Java Keyword");
                document.getElementById("txtModuleName").focus();
                return;
            }
        }
    }
    if (checkReqNo())
    {
        showHide("SRSSpecification");
    }
}

function showPrevSRSSpecification()
{
    showHide("SRSSpecification");
}

function checkReqNo()
{
    if (document.getElementById("txtReqNo").value != '')
    {
        var id = document.getElementById("txtReqNo").value;
        if (!(id.match(/^[0-9]+$/)))
        {
            alert("Invalid Request Number! Please Enter Numeric characters only...");
            document.getElementById("txtReqNo").focus();
            return false;
        }
    }
    return true;
}

function showTableSelection()
{
    showHide("TableSelection");
}

function showDevServer()
{
    document.getElementById("txtMasterTable").value = "";
    document.getElementById("cmbMasterTable").innerHTML = "<option value=\"-1\" selected=\"true\">-- Select Table --</option>";
    document.getElementById("cmbMasterTablePrimKey").innerHTML = "<option value=\"-1\" selected=\"true\">-- Select Field --</option>";
    if (document.getElementById("rdoAliasName").checked)
    {
        document.getElementById("cmbServerName").disabled = true;
        document.getElementById("cmbServerName").value = "-1";
    }
    else if (document.getElementById("rdoServerName").checked)
    {
        document.getElementById("cmbServerName").disabled = false;
    }
}

function setFocus()
{
    document.getElementById("txtMasterTable").focus();
}

function fillMstTableCombo()
{
    if (document.getElementById("cmbAliasName").value == "-1")
    {
        alert("Please Select Alias Name");
        document.getElementById("cmbAliasName").focus();
        return;
    }
    else if (document.getElementById("txtMasterTable").value != "")
    {
        if (document.getElementById("rdoServerName").checked && document.getElementById("cmbServerName").value == "-1")
        {
            document.getElementById("cmbServerName").focus();
            return;
        }
        document.getElementById("cmbMasterTablePrimKey").innerHTML = "<option value=\"-1\" selected=\"true\">-- Select Field --</option>";
        var params = getFormData(document.MenuForm);
        getData_sync("mstgenv2.fin?cmdAction=getDataBaseType", "tdDataBaseType", params, false);
        params = getFormData(document.MenuForm);
        //getData_sync("mstgenv2.fin?cmdAction=getMstTableNames", "cmbMasterTable", params, false);
        getDataFilterNew("mstgenv2.fin?cmdAction=getMstTableNames", "cmbMasterTable", params, false);
    }
    else
    {
        document.getElementById("cmbMasterTable").innerHTML = "<option value=\"-1\" selected=\"true\">-- Select Table --</option>";
        document.getElementById("cmbMasterTablePrimKey").innerHTML = "<option value=\"-1\" selected=\"true\">-- Select Field --</option>";
    }
}

function fillMstTableColumnCombo()
{
    if (document.getElementById("cmbAliasName").value == '-1')
    {
        alert("Please Select Alias Name");
        document.getElementById("cmbAliasName").focus();
        return;
    }
    if (document.getElementById("cmbMasterTable").value == '-1')
    {
        document.getElementById("cmbMasterTable").focus();
        return;
    }
    var params = getFormData(document.MenuForm);
    //getData_sync("mstgenv2.fin?cmdAction=getMstTableColumnsNames", "cmbMasterTablePrimKey", params, false);
    getDataFilterNew("mstgenv2.fin?cmdAction=getMstTableColumnsNames", "cmbMasterTablePrimKey", params, false);
    showSequence();
    document.getElementById("divMstColumnWidth").innerHTML = "";
    getDataFilterNew("mstgenv2.fin?cmdAction=getMstTableColumnWidth", "divMstColumnWidth", params, false);
}

function showSequence()
{
    if (document.getElementById("txtDataBaseType") && document.getElementById("txtDataBaseType").value == "ORACLE")
    {
        document.getElementById("trSequence").style.display = "";
    }
    else
    {
        document.getElementById("trSequence").style.display = "none";
    }
}

function showTabSelection()
{
    document.getElementById("txtSequence").value = document.getElementById("txtSequence").value.replace(/^\s+|\s+$/g, '');
    if (document.getElementById("cmbAliasName").value == "-1")
    {
        alert("Please Select Alias Name");
        document.getElementById("cmbAliasName").focus();
        return;
    }
    else if (document.getElementById("cmbMasterTable").value == "-1")
    {
        alert("Please Select Master Table");
        document.getElementById("cmbMasterTable").focus();
        return;
    }
    else if (document.getElementById("cmbMasterTablePrimKey").value == "-1")
    {
        alert("Please Select Master Table's Prime Key");
        document.getElementById("cmbMasterTablePrimKey").focus();
        return;
    }
    else if (document.getElementById("txtDataBaseType") && document.getElementById("txtDataBaseType").value == "ORACLE" && document.getElementById("txtSequence").value != "")
    {
        var params = getFormData(document.MenuForm);
        getData_sync("mstgenv2.fin?cmdAction=checkSequence", "divSequence", params, false);
        if (document.getElementById("divSequence").innerHTML == "Not Exist")
        {
            alert("Please Enter Valid Sequence");
            document.getElementById("txtSequence").focus();
            return;
        }
    }
    showHide("TabSelection");
}

function showPrevTabSelection()
{
    showHide("TabSelection");
}

function checkTabSelection()
{
    var cnt = 0;
    if (document.getElementById("chkAdd").checked)
    {
        cnt++;
    }
    if (document.getElementById("chkEdit").checked)
    {
        cnt++;
    }
    if (document.getElementById("chkDelete").checked)
    {
        cnt++;
    }
    if (document.getElementById("chkView").checked)
    {
        cnt++;
    }
    if (cnt == 0)
    {
        alert("Please Select Atleast 1 Tab");
        return false;
    }
    return true;
}

function showAllMenuTab()
{
    var add = "<li class='' ><a rel='rel0' href='javascript: showOneMenuTab(\"addMenuTab\");' onclick=''>Menu</a></li>";
    var view = "<li class='' ><a rel='rel0' href='javascript: showOneMenuTab(\"viewMenuTab\");' onclick=''>View</a></li>";

    var tab = "";
    document.getElementById("menutab0").innerHTML = "";
    if (document.getElementById("chkAdd").checked)
    {
        document.getElementById("div1").style.display = "";
        document.getElementById("menutab0").innerHTML = add;
        tab = "addMenuTab";
    }
    else
    {
        document.getElementById("div1").style.display = "none";
    }
    if (document.getElementById("chkEdit").checked)
    {
        document.getElementById("div2").style.display = "";
    }
    else
    {
        document.getElementById("div2").style.display = "none";
    }
    if (document.getElementById("chkDelete").checked)
    {
        document.getElementById("div3").style.display = "";
    }
    else
    {
        document.getElementById("div3").style.display = "none";
    }
    if (document.getElementById("chkView").checked)
    {
        document.getElementById("menutab0").innerHTML += view;
        if (tab == "")
        {
            tab = "viewMenuTab";
        }
    }
    showOneMenuTab(tab);
}

function showOneMenuTab(tab)
{
    document.getElementById("addMenuTab").style.display = "none";
    document.getElementById("viewMenuTab").style.display = "none";
    document.getElementById(tab).style.display = "";
}

function showMasterDataSelection()
{
    if (checkTabSelection())
    {
        if (document.getElementById("txtParamName").value != "")
        {
            showHide("MasterDataSelection");
            showAllMenuTab();
            if (document.getElementById("chkAdd").checked)
            {
                document.getElementById("cmbAddField").innerHTML = document.getElementById("cmbMasterTablePrimKey").innerHTML;
                document.getElementById("cmbAddField").innerHTML += "<option value=\"None\">None</option>";
                document.getElementById("cmbAddField").value = "-1";
                var table = document.getElementById("addTabListTable");
                var rowCount = table.rows.length;
                var combo = document.getElementById("cmbAddField");

                for (var i = 2; i < rowCount; i++)
                {
                    var field = table.rows[i].cells[0].innerHTML;
                    if (field != "None")
                    {
                        for (var j = 0; j < combo.options.length; j++)
                        {
                            if (combo.options[j].value == field)
                            {
                                combo.options[j].disabled = true;
                                break;
                            }
                        }
                    }
                }
            }
            if (document.getElementById("chkEdit").checked)
            {
                document.getElementById("cmbEditField").innerHTML = document.getElementById("cmbMasterTablePrimKey").innerHTML;
                document.getElementById("cmbEditField").innerHTML += "<option value=\"None\">None</option>";
                document.getElementById("cmbEditField").value = "-1";
                table = document.getElementById("editTabListTable");
                rowCount = table.rows.length;
                combo = document.getElementById("cmbEditField");

                for (i = 2; i < rowCount; i++)
                {
                    field = table.rows[i].cells[0].innerHTML;
                    if (field != "None")
                    {
                        for (j = 0; j < combo.options.length; j++)
                        {
                            if (combo.options[j].value == field)
                            {
                                combo.options[j].disabled = true;
                                break;
                            }
                        }
                    }
                }
            }
            if (document.getElementById("chkDelete").checked)
            {
                document.getElementById("cmbDeleteField").innerHTML = document.getElementById("cmbMasterTablePrimKey").innerHTML;
                document.getElementById("cmbDeleteField").innerHTML += "<option value=\"None\">None</option>";
                document.getElementById("cmbDeleteField").value = "-1";
                table = document.getElementById("deleteTabListTable");
                rowCount = table.rows.length;
                combo = document.getElementById("cmbDeleteField");

                for (i = 2; i < rowCount; i++)
                {
                    field = table.rows[i].cells[0].innerHTML;
                    if (field != "None")
                    {
                        for (j = 0; j < combo.options.length; j++)
                        {
                            if (combo.options[j].value == field)
                            {
                                combo.options[j].disabled = true;
                                break;
                            }
                        }
                    }
                }
            }
            if (document.getElementById("chkView").checked)
            {
                createTable("viewTabColumns", "View");
                document.getElementById("cmbViewField").innerHTML = document.getElementById("cmbMasterTablePrimKey").innerHTML;
                document.getElementById("cmbViewField").innerHTML += "<option value=\"None\">None</option>";
                document.getElementById("cmbViewField").value = "-1";
                table = document.getElementById("viewTabListTable");
                rowCount = table.rows.length;
                combo = document.getElementById("cmbViewField");

                for (i = 2; i < rowCount; i++)
                {
                    field = table.rows[i].cells[0].innerHTML;
                    if (field != "None")
                    {
                        for (j = 0; j < combo.options.length; j++)
                        {
                            if (combo.options[j].value == field)
                            {
                                combo.options[j].disabled = true;
                                break;
                            }
                        }
                    }
                }
            }
        }
        else
        {
            alert("Please Enter ParamName For Dispatcher Entry");
            document.getElementById("txtParamName").focus();
            return;
        }
    }
}

function createTable(tableId, tab)
{
    var table = document.getElementById(tableId);
    table.innerHTML = "";
    var combo = document.getElementById("cmbMasterTablePrimKey");

    //Add Select All CheckBox
    var tr = document.createElement('tr');
    var td1 = document.createElement('td');
    td1.align = "right";
    var chkSelAll = document.createElement('input');
    chkSelAll.name = "chkSelectAll";
    chkSelAll.id = "chkSelectAll";
    chkSelAll.type = "checkbox";
    chkSelAll.onclick = function()
    {
        if (this.checked)
        {
            var rows = table.rows.length;
            for (var i = 2; i < rows; i++)
            {
                table.rows[i].cells[0].childNodes[0].checked = true;
            }
        }
    };
    td1.appendChild(chkSelAll);

    var td2 = document.createElement('td');
    td2.innerHTML = "Select All";

    tr.appendChild(td1);
    tr.appendChild(td2);
    table.appendChild(tr);
    //Select All CheckBox Is Added

    //Add Blank Row in Table
    tr = document.createElement('tr');
    tr.style.height = "10px";
    td1 = document.createElement('td');
    td2 = document.createElement('td');
    tr.appendChild(td1);
    tr.appendChild(td2);
    table.appendChild(tr);

    //Add All Fields in Table
    for (var i = 0; i < combo.options.length; i++)
    {
        if (combo.options[i].value != "-1")
        {
            tr = document.createElement('tr');

            td1 = document.createElement('td');
            td1.align = "right";
            var chkCol = document.createElement('input');
            chkCol.name = "chk" + tab + "Column";
            chkCol.id = "chk" + tab + "Column";
            chkCol.type = "checkbox";
            chkCol.value = combo.options[i].value;
            td1.appendChild(chkCol);

            td2 = document.createElement('td');
            td2.innerHTML = combo.options[i].value;

            tr.appendChild(td1);
            tr.appendChild(td2);
            table.appendChild(tr);
        }
    }
}

function showPrevMasterDataSelection()
{
    showHide("MasterDataSelection");
}

function showAllFilterTab()
{
    var edit = "<li class='' ><a rel='rel1' id='tabFltrEdit' href='javascript: showOneFilterTab(\"editFilterTab\");' onclick=''>Edit</a></li>";
    if (document.getElementById("chkCopyETabToDel").checked)
    {
        var deletetab = "<li class='' ><a rel='rel1' id='tabFltrDel' href='javascript: void(\"0\");' style=\"color: #FFFFFF;\" onclick=''>Delete</a></li>";
    }
    else
    {
        deletetab = "<li class='' ><a rel='rel1' id='tabFltrDel' href='javascript: showOneFilterTab(\"deleteFilterTab\");' onclick=''>Delete</a></li>";
    }
    if (document.getElementById("chkCopyETabToView").checked || document.getElementById("chkCopyDTabToView").checked)
    {
        var view = "<li class='' ><a rel='rel1' id='tabFltrView' href='javascript: void(\"0\");' style=\"color: #FFFFFF;\" onclick=''>View</a></li>";
    }
    else
    {
        view = "<li class='' ><a rel='rel1' id='tabFltrView' href='javascript: showOneFilterTab(\"viewFilterTab\");' onclick=''>View</a></li>";
    }

    var tab = "";
    document.getElementById("maintab1").innerHTML = "";
    if (document.getElementById("chkEdit").checked)
    {
        document.getElementById("maintab1").innerHTML = edit;
        if (tab == "")
        {
            tab = "editFilterTab";
        }
    }
    if (document.getElementById("chkDelete").checked)
    {
        document.getElementById("maintab1").innerHTML += deletetab;
        if (tab == "")
        {
            tab = "deleteFilterTab";
        }
        document.getElementById("divCopyETabToDel").style.display = "";
    }
    else
    {
        document.getElementById("divCopyETabToDel").style.display = "none";
    }
    if (document.getElementById("chkView").checked)
    {
        document.getElementById("maintab1").innerHTML += view;
        if (tab == "")
        {
            tab = "viewFilterTab";
        }
        document.getElementById("divCopyETabToView").style.display = "";
        document.getElementById("divCopyDTabToView").style.display = "";
    }
    else
    {
        document.getElementById("divCopyETabToView").style.display = "none";
        document.getElementById("divCopyDTabToView").style.display = "none";
    }
    if (document.getElementById("chkDelete").checked || document.getElementById("chkView").checked)
    {
        document.getElementById("divETabCopy").style.display = "";
    }
    else
    {
        document.getElementById("divETabCopy").style.display = "none";
    }
    if (document.getElementById("chkView").checked)
    {
        document.getElementById("divDTabCopy").style.display = "";
    }
    else
    {
        document.getElementById("divDTabCopy").style.display = "none";
    }
    showOneFilterTab(tab);
}

function showOneFilterTab(tab)
{
    document.getElementById("editFilterTab").style.display = "none";
    document.getElementById("deleteFilterTab").style.display = "none";
    document.getElementById("viewFilterTab").style.display = "none";
    document.getElementById(tab).style.display = "";
}

function showMasterFilterSelection()
{
    if (document.getElementById("chkAdd").checked)
    {
        if (document.getElementById("addTabListTable").rows.length <= 2)
        {
            alert("You Must Enter Data For Menu Tab");
            showOneMenuTab("addMenuTab");
            return;
        }
        else
        {
            if (document.getElementById("cmbAddControlPos").value == "-1")
            {
                alert("Please Select Control Position");
                showOneMenuTab("addMenuTab");
                document.getElementById("cmbAddControlPos").focus();
                return;
            }
        }
        count = 0;
        for (j = 1; j < addListDivId; j++)
        {
            if (document.getElementById("divAdd" + j) != null)
            {
                if (document.getElementById("divAdd" + j).childNodes[24].value == "true" && document.getElementById("divAdd" + j).childNodes[0].value != "None")
                {
                    count++;
                }
            }
        }
        if (count == 0)
        {
            alert("You Must Choose Atleast One Table Field For Add Tab.");
            showOneMenuTab("addMenuTab");
            return;
        }
    }
    if (document.getElementById("chkEdit").checked)
    {
        var count = 0;
        var prime = 0;
        for (var j = 1; j < addListDivId; j++)
        {
            if (document.getElementById("divAdd" + j) != null)
            {
                if (document.getElementById("divAdd" + j).childNodes[25].value == "true" && document.getElementById("divAdd" + j).childNodes[0].value != "None")
                {
                    if (document.getElementById("divAdd" + j).childNodes[0].value != document.getElementById("cmbMasterTablePrimKey").value)
                    {
                        count++;
                    }
                    else
                    {
                        prime = 1;
                    }
                }
            }
        }
        if (count == 0)
        {
            if (prime == 1)
            {
                alert("You Can't Choose Only Prime Field For Edit Tab. Because Prime Field Can't be Edited.");
            }
            else
            {
                alert("You Must Choose Atleast One Table Field For Edit Tab.");
            }
            showOneMenuTab("addMenuTab");
            return;
        }
    }
    if (document.getElementById("chkDelete").checked)
    {
        count = 0;
        for (j = 1; j < addListDivId; j++)
        {
            if (document.getElementById("divAdd" + j) != null)
            {
                if (document.getElementById("divAdd" + j).childNodes[26].value == "true" && document.getElementById("divAdd" + j).childNodes[0].value != "None")
                {
                    count++;
                }
            }
        }
        if (count == 0)
        {
            alert("You Must Choose Atleast One Table Field For Delete Tab.");
            showOneMenuTab("addMenuTab");
            return;
        }
    }
    if (document.getElementById("chkView").checked)
    {
        var table = document.getElementById("viewTabColumns");
        var row = table.rows.length;
        var i = 2;
        for (; i < row; i++)
        {
            if (table.rows[i].cells[0].childNodes[0].checked)
            {
                break;
            }
        }
        if (i == row)
        {
            alert("Please Select Atleast One Field To Show On View");
            showOneMenuTab("viewMenuTab");
            return;
        }
    }
    showHide("MasterFilterSelection");
    showAllFilterTab();
}

function showHideTab(from, to)
{
    if (to == "Del")
    {
        if (document.getElementById("chkCopy" + from + "TabTo" + to).checked)
        {
            document.getElementById("tabFltr" + to).href = "javascript: void(\"0\");";
            //document.getElementById("tabFltr" + to).style.backgroundColor = "#E5E5E5";
            document.getElementById("tabFltr" + to).style.color = "#FFFFFF";
            if (from == "E" && !document.getElementById("chkCopyETabToView").checked)
            {
                document.getElementById("chkCopyDTabToView").checked = false;
                if (document.getElementById("tabFltrView"))
                {
                    document.getElementById("tabFltrView").href = "javascript: showOneFilterTab(\"viewFilterTab\");";
                    //document.getElementById("tabFltrView").style.backgroundColor = "";
                    document.getElementById("tabFltrView").style.color = "";
                }
                if (document.getElementById("chkCopyETabToView").disabled)
                {
                    document.getElementById("chkCopyETabToView").disabled = false;
                }
            }
        }
        else
        {
            document.getElementById("tabFltr" + to).href = "javascript: showOneFilterTab(\"deleteFilterTab\");";
            //document.getElementById("tabFltr" + to).style.backgroundColor = "";
            document.getElementById("tabFltr" + to).style.color = "";
        }
    }
    else if (to == "View")
    {
        if (document.getElementById("chkCopy" + from + "TabToView").checked)
        {
            document.getElementById("tabFltr" + to).href = "javascript: void(\"0\");";
            //document.getElementById("tabFltr" + to).style.backgroundColor = "#E5E5E5";
            document.getElementById("tabFltr" + to).style.color = "#FFFFFF";
            if (from == "E")
            {
                document.getElementById("chkCopyDTabToView").checked = false;
                document.getElementById("chkCopyDTabToView").disabled = true;
            }
            else if (from == "D")
            {
                document.getElementById("chkCopyETabToView").checked = false;
                document.getElementById("chkCopyETabToView").disabled = true;
            }
        }
        else
        {
            document.getElementById("tabFltr" + to).href = "javascript: showOneFilterTab(\"viewFilterTab\");";
            //document.getElementById("tabFltr" + to).style.backgroundColor = "";
            document.getElementById("tabFltr" + to).style.color = "";
            if (from == "E")
            {
                document.getElementById("chkCopyDTabToView").checked = false;
                document.getElementById("chkCopyDTabToView").disabled = false;
            }
            else if (from == "D")
            {
                document.getElementById("chkCopyETabToView").checked = false;
                document.getElementById("chkCopyETabToView").disabled = false;
            }
        }
    }
}

function showPrevMasterFilterSelection()
{
    if (document.getElementById("chkCopyETabToDel").checked)
    {
        document.getElementById("hiddenDivDeleteTab").innerHTML = "";
    }
    if (document.getElementById("chkCopyETabToView").checked || document.getElementById("chkCopyDTabToView").checked)
    {
        document.getElementById("hiddenDivViewTab").innerHTML = "";
    }
    showHide("MasterFilterSelection");
}

function showHide(hideDivId)
{
    var vDivIds = new Array("ProjectSpecification",
            "SRSSpecification",
            "TableSelection",
            "TabSelection",
            "MasterDataSelection",
            "MasterFilterSelection",
            "Finish"
            );

    for (var i = 0; i < vDivIds.length; i++)
    {
        if (hideDivId == vDivIds[i])
        {
            document.getElementById(vDivIds[i]).style.display = "";
        }
        else
        {
            document.getElementById(vDivIds[i]).style.display = "none";
        }
    }
}

function showFinish()
{
//    if (document.getElementById("chkEdit").checked)
//    {
//        if (document.getElementById("editTabListTable").rows.length <= 2)
//        {
//            alert("You Must Enter Data For Edit Tab");
//            showOneFilterTab("editFilterTab");
//            return;
//        }
//        else
//        {
//            if (document.getElementById("cmbEditControlPos").value == "-1")
//            {
//                alert("Please Select Control Position");
//                showOneFilterTab("editFilterTab");
//                document.getElementById("cmbEditControlPos").focus();
//                return;
//            }
//        }
//    }
//    if (document.getElementById("chkDelete").checked && !document.getElementById("chkCopyETabToDel").checked)
//    {
//        if (document.getElementById("deleteTabListTable").rows.length <= 2)
//        {
//            alert("You Must Enter Data For Delete Tab");
//            showOneFilterTab("deleteFilterTab");
//            return;
//        }
//        else
//        {
//            if (document.getElementById("cmbDeleteControlPos").value == "-1")
//            {
//                alert("Please Select Control Position");
//                showOneFilterTab("deleteFilterTab");
//                document.getElementById("cmbDeleteControlPos").focus();
//                return;
//            }
//        }
//    }
//    else
//    {
//        if (document.getElementById("cmbDeleteControlPos").value == "-1")
//        {
//            document.getElementById("cmbDeleteControlPos").value = document.getElementById("cmbEditControlPos").value;
//        }
//    }
//    if (document.getElementById("chkView").checked && !document.getElementById("chkCopyETabToView").checked && !document.getElementById("chkCopyDTabToView").checked)
//    {
//        document.getElementById("chkOnScreen").checked = true;
//        if (document.getElementById("viewTabListTable").rows.length <= 2)
//        {
//            alert("You Must Enter Data For View Tab");
//            showOneFilterTab("viewFilterTab");
//            return;
//        }
//        else
//        {
//            if (document.getElementById("cmbViewControlPos").value == "-1")
//            {
//                alert("Please Select Control Position");
//                showOneFilterTab("viewFilterTab");
//                document.getElementById("cmbViewControlPos").focus();
//                return;
//            }
//        }
//    }
//    else
//    {
//        if (document.getElementById("cmbViewControlPos").value == "-1")
//        {
//            if (document.getElementById("chkCopyETabToView").checked)
//            {
//                document.getElementById("cmbViewControlPos").value = document.getElementById("cmbEditControlPos").value;
//            }
//            else if (document.getElementById("chkCopyDTabToView").checked)
//            {
//                document.getElementById("cmbViewControlPos").value = document.getElementById("cmbDeleteControlPos").value;
//            }
//        }
//    }
    //Copy Tab Controls
    copyFilters();
    showHide("Finish");
}

function copyFilters()
{
    if (document.getElementById("chkDelete").checked && document.getElementById("chkCopyETabToDel").checked)
    {
        document.getElementById("hiddenDivDeleteTab").innerHTML = "";
        var table = document.getElementById("deleteTabListTable");
        var rowCount = table.rows.length;
        for (var i = 2; i < rowCount; i++)
        {
            table.deleteRow(i);
        }

        var combo = document.getElementById("cmbDeleteField");
        for (i = 0; i < combo.options.length; i++)
        {
            combo.options[i].disabled = false;
        }

        var flag = false;
        for (i = 1; i < editListDivId; i++)
        {
            if (document.getElementById("divEdit" + i) != null)
            {
                var editDiv = document.getElementById("divEdit" + i);
                copyOneFilterControl(editDiv, document.getElementById("hiddenDivDeleteTab"), "Delete");
                flag = true;
            }
        }
        if (flag == true)
        {
            document.getElementById("cmbDeleteControlPos").value = document.getElementById("cmbEditControlPos").value;
        }
    }
    if (document.getElementById("chkView").checked && document.getElementById("chkCopyETabToView").checked)
    {
        document.getElementById("hiddenDivViewTab").innerHTML = "";
        table = document.getElementById("viewTabListTable");
        rowCount = table.rows.length;
        for (i = 2; i < rowCount; i++)
        {
            table.deleteRow(i);
        }

        combo = document.getElementById("cmbViewField");
        for (i = 0; i < combo.options.length; i++)
        {
            combo.options[i].disabled = false;
        }

        flag = false;
        for (i = 1; i < editListDivId; i++)
        {
            if (document.getElementById("divEdit" + i) != null)
            {
                editDiv = document.getElementById("divEdit" + i);
                copyOneFilterControl(editDiv, document.getElementById("hiddenDivViewTab"), "View");
                flag = true;
            }
        }
        if (flag == true)
        {
            document.getElementById("cmbViewControlPos").value = document.getElementById("cmbEditControlPos").value;
        }
    }
    else if (document.getElementById("chkView").checked && document.getElementById("chkCopyDTabToView").checked)
    {
        document.getElementById("hiddenDivViewTab").innerHTML = "";
        table = document.getElementById("viewTabListTable");
        rowCount = table.rows.length;
        for (i = 2; i < rowCount; i++)
        {
            table.deleteRow(i);
        }

        combo = document.getElementById("cmbViewField");
        for (i = 0; i < combo.options.length; i++)
        {
            combo.options[i].disabled = false;
        }

        flag = false;
        for (i = 1; i < deleteListDivId; i++)
        {
            if (document.getElementById("divDel" + i) != null)
            {
                var delDiv = document.getElementById("divDel" + i);
                copyOneFilterControl(delDiv, document.getElementById("hiddenDivViewTab"), "View");
                flag = true;
            }
        }
        if (flag == true)
        {
            document.getElementById("cmbViewControlPos").value = document.getElementById("cmbDeleteControlPos").value;
        }
    }
}

function copyOneFilterControl(srcDiv, destDiv, destTab)
{
    var div = document.createElement("div");
    if (destTab == "Delete")
    {
        div.id = "divDel" + deleteListDivId;
    }
    else if (destTab == "View")
    {
        div.id = "divView" + viewListDivId;
    }

    div.innerHTML = "<input type='hidden' name='hdn" + destTab + "Field' id='hdn" + destTab + "Field' value='" + srcDiv.childNodes[0].value + "'/>";
    div.innerHTML += "<input type='hidden' name='hdn" + destTab + "Control' id='hdn" + destTab + "Control' value='" + srcDiv.childNodes[1].value + "'/>";
    //srs properties
    div.innerHTML += "<input type='hidden' name='hdncmb" + destTab + "Validation' id='hdncmb" + destTab + "Validation' value='" + srcDiv.childNodes[2].value + "'/>";
    div.innerHTML += "<input type='hidden' name='hdntxt" + destTab + "Remarks' id='hdntxt" + destTab + "Remarks' value='" + srcDiv.childNodes[3].value + "'/>";
    //code properties

    if (destTab == "Delete")
    {
        var cntrlID = "del" + srcDiv.childNodes[4].value;
        var cntrlName = "del" + srcDiv.childNodes[5].value;
    }
    else if (destTab == "View")
    {
        cntrlID = "view" + srcDiv.childNodes[4].value;
        cntrlName = "view" + srcDiv.childNodes[5].value;
    }

    if (checkControlID(cntrlID) || checkElementName(cntrlID))
    {
        var cnt = 1;
        while (checkControlID(cntrlID + "_" + cnt) || checkElementName(cntrlID + "_" + cnt))
        {
            cnt++;
        }
        cntrlID = cntrlID + "_" + cnt;
    }

    if (checkControlName(cntrlName))
    {
        cnt = 1;
        while (checkControlName(cntrlName + "_" + cnt))
        {
            cnt++;
        }
        cntrlName = cntrlName + "_" + cnt;
    }

    div.innerHTML += "<input type='hidden' name='hdntxt" + destTab + "Id' id='hdntxt" + destTab + "Id' value='" + cntrlID + "'/>";
    div.innerHTML += "<input type='hidden' name='hdntxt" + destTab + "Name' id='hdntxt" + destTab + "Name' value='" + cntrlName + "'/>";
    div.innerHTML += "<input type='hidden' name='hdntxt" + destTab + "Style' id='hdntxt" + destTab + "Style' value='" + srcDiv.childNodes[6].value + "'/>";
    div.innerHTML += "<input type='hidden' name='hdntxt" + destTab + "Size' id='hdntxt" + destTab + "Size' value='" + srcDiv.childNodes[7].value + "'/>";
    div.innerHTML += "<input type='hidden' name='hdntxt" + destTab + "Class' id='hdntxt" + destTab + "Class' value='" + srcDiv.childNodes[8].value + "'/>";
    if (destTab == "Delete" && srcDiv.childNodes[9].value != "" && srcDiv.childNodes[9].value != "-1")
    {
        div.innerHTML += "<input type='hidden' name='hdncmb" + destTab + "Onchange' id='hdncmb" + destTab + "Onchange' value='del" + srcDiv.childNodes[9].value + "'/>";
    }
    else if (destTab == "View" && srcDiv.childNodes[9].value != "" && srcDiv.childNodes[9].value != "-1")
    {
        div.innerHTML += "<input type='hidden' name='hdncmb" + destTab + "Onchange' id='hdncmb" + destTab + "Onchange' value='view" + srcDiv.childNodes[9].value + "'/>";
    }
    else
    {
        div.innerHTML += "<input type='hidden' name='hdncmb" + destTab + "Onchange' id='hdncmb" + destTab + "Onchange' value='" + srcDiv.childNodes[9].value + "'/>";
    }
    div.innerHTML += "<input type='hidden' name='hdnrbtn" + destTab + "Multiple' id='hdnrbtn" + destTab + "Multiple' value='" + srcDiv.childNodes[10].value + "'/>";
    div.innerHTML += "<input type='hidden' name='hdnrbtn" + destTab + "Checked' id='hdnrbtn" + destTab + "Checked' value='" + srcDiv.childNodes[11].value + "'/>";
    div.innerHTML += "<input type='hidden' name='hdntxt" + destTab + "MaxLength' id='hdntxt" + destTab + "MaxLength' value='" + srcDiv.childNodes[12].value + "'/>";
    div.innerHTML += "<input type='hidden' name='hdnrbtn" + destTab + "Readonly' id='hdnrbtn" + destTab + "Readonly' value='" + srcDiv.childNodes[13].value + "'/>";
    div.innerHTML += "<input type='hidden' name='hdncmb" + destTab + "Align' id='hdncmb" + destTab + "Align' value='" + srcDiv.childNodes[14].value + "'/>";
    div.innerHTML += "<input type='hidden' name='hdntxt" + destTab + "Rows' id='hdntxt" + destTab + "Rows' value='" + srcDiv.childNodes[15].value + "'/>";
    div.innerHTML += "<input type='hidden' name='hdntxt" + destTab + "Cols' id='hdntxt" + destTab + "Cols' value='" + srcDiv.childNodes[16].value + "'/>";
    div.innerHTML += "<input type='hidden' name='hdntxt" + destTab + "Value' id='hdntxt" + destTab + "Value' value='" + srcDiv.childNodes[17].value + "'/>";
    div.innerHTML += "<input type='hidden' name='hdn" + destTab + "TabIndex' id='hdn" + destTab + "TabIndex' value='" + srcDiv.childNodes[18].value + "'/>";
    div.innerHTML += "<input type='hidden' name='hdnrbtn" + destTab + "ValueFormat' id='hdnrbtn" + destTab + "ValueFormat' value='" + srcDiv.childNodes[19].value + "'/>";
    div.innerHTML += "<input type='hidden' name='hdntxt" + destTab + "TotalRdo' id='hdntxt" + destTab + "TotalRdo' value='" + srcDiv.childNodes[20].value + "'/>";
    div.innerHTML += "<input type='hidden' name='hdntxt" + destTab + "RdoCap' id='hdntxt" + destTab + "RdoCap' value='" + srcDiv.childNodes[21].value + "'/>";
    div.innerHTML += "<input type='hidden' name='hdntxt" + destTab + "RdoVal' id='hdntxt" + destTab + "RdoVal' value='" + srcDiv.childNodes[22].value + "'/>";
    div.innerHTML += "<input type='hidden' name='hdntxt" + destTab + "RdoDefVal' id='hdntxt" + destTab + "RdoDefVal' value='" + srcDiv.childNodes[23].value + "'/>";
    div.innerHTML += "<input type='hidden' name='hdntxt" + destTab + "Label' id='hdntxt" + destTab + "Label' value='" + srcDiv.childNodes[24].value + "'/>";
    div.innerHTML += "<input type='hidden' name='hdnchk" + destTab + "Src' id='hdnchk" + destTab + "Src' value='" + srcDiv.childNodes[25].value + "'/>";
    div.innerHTML += "<input type='hidden' name='hdnrdo" + destTab + "DataSrc' id='hdnrdo" + destTab + "DataSrc' value='" + srcDiv.childNodes[26].value + "'/>";
    div.innerHTML += "<input type='hidden' name='hdntxt" + destTab + "SrcStatic' id='hdntxt" + destTab + "SrcStatic' value='" + srcDiv.childNodes[27].value + "'/>";
    div.innerHTML += "<input type='hidden' name='hdntxt" + destTab + "SrcQuery' id='hdntxt" + destTab + "SrcQuery' value='" + srcDiv.childNodes[28].value + "'/>";
    div.innerHTML += "<input type='hidden' name='hdntxt" + destTab + "WsdlUrl' id='hdntxt" + destTab + "WsdlUrl' value='" + srcDiv.childNodes[29].value + "'/>";
    div.innerHTML += "<input type='hidden' name='hdncmb" + destTab + "WsMethod' id='hdncmb" + destTab + "WsMethod' value='" + srcDiv.childNodes[30].value + "'/>";
    div.innerHTML += "<input type='hidden' name='hdntxt" + destTab + "WsCmbValue' id='hdntxt" + destTab + "WsCmbValue' value='" + srcDiv.childNodes[31].value + "'/>";
    div.innerHTML += "<input type='hidden' name='hdntxt" + destTab + "WsCmbText' id='hdntxt" + destTab + "WsCmbText' value='" + srcDiv.childNodes[32].value + "'/>";
    div.innerHTML += "<input type='hidden' name='hdntxt" + destTab + "WsProject' id='hdntxt" + destTab + "WsProject' value='" + srcDiv.childNodes[33].value + "'/>";
    div.innerHTML += "<input type='hidden' name='hdntxt" + destTab + "WsIntrface' id='hdntxt" + destTab + "WsIntrface' value='" + srcDiv.childNodes[34].value + "'/>";
    div.innerHTML += "<input type='hidden' name='hdntxt" + destTab + "WsRetType' id='hdntxt" + destTab + "WsRetType' value='" + srcDiv.childNodes[35].value + "'/>";
    div.innerHTML += "<input type='hidden' name='hdntxt" + destTab + "WsParams' id='hdntxt" + destTab + "WsParams' value='" + srcDiv.childNodes[36].value + "'/>";
    div.innerHTML += "<input type='hidden' name='hdntxt" + destTab + "WsExps' id='hdntxt" + destTab + "WsExps' value='" + srcDiv.childNodes[37].value + "'/>";
    div.innerHTML += "<input type='hidden' name='hdncmb" + destTab + "CommonQuery' id='hdncmb" + destTab + "CommonQuery' value='" + srcDiv.childNodes[38].value + "'/>";
    div.innerHTML += "<input type='hidden' name='hdntxt" + destTab + "Maxsize' id='hdntxt" + destTab + "Maxsize' value='" + srcDiv.childNodes[39].value + "'/>";
    div.innerHTML += "<input type='hidden' name='hdntxt" + destTab + "Type' id='hdntxt" + destTab + "Type' value='" + srcDiv.childNodes[40].value + "'/>";
    div.innerHTML += "<input type='hidden' name='hdntxt" + destTab + "Maxfiles' id='hdntxt" + destTab + "Maxfiles' value='" + srcDiv.childNodes[41].value + "'/>";
    div.innerHTML += "<input type='hidden' name='hdntxt" + destTab + "EleName' id='hdntxt" + destTab + "EleName' value='" + srcDiv.childNodes[42].value + "'/>";
    div.innerHTML += "<input type='hidden' name='hdntxt" + destTab + "DispTxt' id='hdntxt" + destTab + "DispTxt' value='" + srcDiv.childNodes[43].value + "'/>";
    div.innerHTML += "<input type='hidden' name='hdntxt" + destTab + "OnremoveCall' id='hdntxt" + destTab + "OnremoveCall' value='" + srcDiv.childNodes[44].value + "'/>";
    div.innerHTML += "<input type='hidden' name='hdnchk" + destTab + "Mandatory' id='hdnchk" + destTab + "Mandatory' value='" + srcDiv.childNodes[45].value + "'/>";

    destDiv.appendChild(div);
    if (destTab == "Delete")
    {
        deleteListDivId++;
    }
    else if (destTab == "View")
    {
        viewListDivId++;
    }
}

function showDateValidations(combo)
{
    var cmb = document.getElementById(combo);

    cmb.innerHTML = "<option value=\"-1\" selected=\"true\">--Select Validation--</option>";
    cmb.innerHTML += "<option value=\"DateValidation\">Date</option>";
    cmb.innerHTML += "<option value=\"LessThanFutureDate\">Less Than Future Date</option>";
    cmb.innerHTML += "<option value=\"GreaterThanFutureDate\">Greater Than Future Date</option>";
}

function showPasswordValidations(combo)
{
    var cmb = document.getElementById(combo);

    cmb.innerHTML = "<option value=\"-1\" selected=\"true\">--Select Validation--</option>";
    cmb.innerHTML += "<option value=\"Password\">Password</option>";
}

function showTextValidations(combo)
{
    var cmb = document.getElementById(combo);

    cmb.innerHTML = "<option value=\"-1\" selected=\"true\">--Select Validation--</option>";
    cmb.innerHTML += "<option value=\"Numeric\">Only Numeric</option>";
    cmb.innerHTML += "<option value=\"Alphabet\">Only Alphabet</option>";
    cmb.innerHTML += "<option value=\"Company\">Company Validation</option>";
    cmb.innerHTML += "<option value=\"Company\">Alphanumeric Validation</option>";
    cmb.innerHTML += "<option value=\"Age\">Age</option>";
    cmb.innerHTML += "<option value=\"PANNo\">PAN No</option>";
    cmb.innerHTML += "<option value=\"FolioNo\">Folio No</option>";
    cmb.innerHTML += "<option value=\"FAXNo\">FAX No</option>";
    cmb.innerHTML += "<option value=\"MobileNo\">Mobile No</option>";
    cmb.innerHTML += "<option value=\"PhoneNo\">Phone No</option>";
    cmb.innerHTML += "<option value=\"Pincode\">Pincode</option>";
    cmb.innerHTML += "<option value=\"NAV\">NAV</option>";
    cmb.innerHTML += "<option value=\"ChequeNo\">Cheque No</option>";
    cmb.innerHTML += "<option value=\"Email\">Email</option>";
    cmb.innerHTML += "<option value=\"NJbrcode\">NJ brcode</option>";
}

function showComboValidations(combo)
{
    var cmb = document.getElementById(combo);

    cmb.innerHTML = "<option value=\"-1\" selected=\"true\">--Select Validation--</option>";
    cmb.innerHTML += "<option value=\"DropDown\">DropDown Validation</option>";
    cmb.innerHTML += "<option value=\"SelectLimit\">Select Limit</option>";
}

function showCheckBoxValidations(combo)
{
    var cmb = document.getElementById(combo);

    cmb.innerHTML = "<option value=\"-1\" selected=\"true\">--Select Validation--</option>";
    cmb.innerHTML += "<option value=\"CheckBoxValidation\">CheckBox Validation</option>";
}

function showRadioValidations(combo)
{
    var cmb = document.getElementById(combo);

    cmb.innerHTML = "<option value=\"-1\" selected=\"true\">--Select Validation--</option>";
    cmb.innerHTML += "<option value=\"RadioValidation\">Radio Validation</option>";
}

function removeAllValidations(combo)
{
    document.getElementById(combo).innerHTML = "<option value=\"-1\" selected=\"true\">--Select Validation--</option>";
}

function showChkMndtry(tab)
{
    document.getElementById("chk" + tab + "Mandatory").checked = false;
    if (document.getElementById("cmb" + tab + "Validation").value != "-1")
    {
        document.getElementById("tr" + tab + "Mndtry").style.display = "";
    }
    else
    {
        document.getElementById("tr" + tab + "Mndtry").style.display = "none";
    }
}

function showAddControlProperties()
{
    document.getElementById("divAddProperties").style.display = "";

    document.getElementById("trAddStyleSizeProp").style.display = "none";
    document.getElementById("trAddClassProp").style.display = "";
    document.getElementById("tdAddOnchange1").style.display = "none";
    document.getElementById("tdAddOnchange2").style.display = "none";
    document.getElementById("trAddTxt_LabelProp").style.display = "none";
    document.getElementById("trAddAlignProp").style.display = "none";
    document.getElementById("trAddCmbProp").style.display = "none";
    document.getElementById("trAddIndepSource").style.display = "none";
    document.getElementById("tdAddSrc").style.display = "none";
    document.getElementById("trAddIndepSrcStatic").style.display = "none";
    document.getElementById("trAddIndepSrcQuery").style.display = "none";
    document.getElementById("trAddIndepSrcCommonCmb").style.display = "none";
    document.getElementById("tbdAddWS").style.display = "none";
    document.getElementById("trAddTextAreaProp").style.display = "none";
    document.getElementById("trAddChkProp").style.display = "none";
    document.getElementById("trAddRdoProp").style.display = "none";
    document.getElementById("trAddChkValue").style.display = "none";
    document.getElementById("AddTotalRdo1").style.display = "none";
    document.getElementById("AddTotalRdo2").style.display = "none";
    document.getElementById("trAddRdoCaptions").style.display = "none";
    document.getElementById("trAddRdoValues").style.display = "none";
    document.getElementById("trAddRdoDefaultValue").style.display = "none";
    document.getElementById("AddSize1").style.display = "";
    document.getElementById("AddSize2").style.display = "";
    document.getElementById("trAddMndtry").style.display = "none";
    document.getElementById("cmbAddValidation").value = "-1";
    document.getElementById("trAddSizeType").style.display = "none";
    document.getElementById("trAddFilesEle").style.display = "none";
    document.getElementById("trAddDispTxt").style.display = "none";

    if (document.getElementById("cmbAddControl").value == "-1")
    {
        document.getElementById("trAddStyleSizeProp").style.display = "";
        removeAllValidations("cmbAddValidation");
    }
    else if (document.getElementById("cmbAddControl").value == "TextBox")
    {
        document.getElementById("trAddStyleSizeProp").style.display = "";
        document.getElementById("trAddTxt_LabelProp").style.display = "";
        document.getElementById("trAddAlignProp").style.display = "";
        showTextValidations("cmbAddValidation");
    }
    else if (document.getElementById("cmbAddControl").value == "ComboBox" || document.getElementById("cmbAddControl").value == "TextLikeCombo")
    {
        document.getElementById("tdAddOnchange1").style.display = "";
        document.getElementById("tdAddOnchange2").style.display = "";
        document.getElementById("trAddStyleSizeProp").style.display = "";
        if (document.getElementById("cmbAddControl").value == "ComboBox")
        {
            document.getElementById("trAddCmbProp").style.display = "";
        }
        document.getElementById("trAddIndepSource").style.display = "";
        if (document.getElementById("chkAddSrc").checked)
        {
            document.getElementById("tdAddSrc").style.display = "";
            if (document.getElementById("rdoAddSrcStatic").checked)
            {
                document.getElementById("trAddIndepSrcStatic").style.display = "";
            }
            else if (document.getElementById("rdoAddSrcQuery").checked)
            {
                document.getElementById("trAddIndepSrcQuery").style.display = "";
            }
            else if (document.getElementById("rdoAddSrcWS").checked)
            {
                document.getElementById("tbdAddWS").style.display = "";
            }
            else if (document.getElementById("rdoAddSrcCommonCmb").checked)
            {
                document.getElementById("trAddIndepSrcCommonCmb").style.display = "";
            }
        }
        showComboValidations("cmbAddValidation");
    }
    else if (document.getElementById("cmbAddControl").value == "TextArea")
    {
        document.getElementById("trAddStyleSizeProp").style.display = "";
        document.getElementById("trAddTextAreaProp").style.display = "";
        document.getElementById("AddSize1").style.display = "none";
        document.getElementById("AddSize2").style.display = "none";
        showTextValidations("cmbAddValidation");
    }
    else if (document.getElementById("cmbAddControl").value == "CheckBox")
    {
        document.getElementById("trAddStyleSizeProp").style.display = "";
        document.getElementById("trAddChkProp").style.display = "";
        document.getElementById("trAddChkValue").style.display = "";
        showCheckBoxValidations("cmbAddValidation");
    }
    else if (document.getElementById("cmbAddControl").value == "Radio")
    {
        document.getElementById("trAddStyleSizeProp").style.display = "";
        document.getElementById("trAddRdoProp").style.display = "";
        document.getElementById("AddTotalRdo1").style.display = "";
        document.getElementById("AddTotalRdo2").style.display = "";
        document.getElementById("trAddRdoCaptions").style.display = "";
        document.getElementById("trAddRdoValues").style.display = "";
        document.getElementById("trAddRdoDefaultValue").style.display = "";
        showRadioValidations("cmbAddValidation");
    }
    else if (document.getElementById("cmbAddControl").value == "DatePicker")
    {
        document.getElementById("trAddClassProp").style.display = "none";
        showDateValidations("cmbAddValidation");
    }
    else if (document.getElementById("cmbAddControl").value == "File")
    {
        document.getElementById("trAddClassProp").style.display = "none";
        removeAllValidations("cmbAddValidation");
    }
    else if (document.getElementById("cmbAddControl").value == "Password")
    {
        document.getElementById("trAddStyleSizeProp").style.display = "";
        document.getElementById("trAddTxt_LabelProp").style.display = "";
        document.getElementById("trAddAlignProp").style.display = "";
        showPasswordValidations("cmbAddValidation");
    }
    else if (document.getElementById("cmbAddControl").value == "FileBox")
    {
        document.getElementById("trAddClassProp").style.display = "none";
        document.getElementById("AddSize1").style.display = "none";
        document.getElementById("AddSize2").style.display = "none";
        document.getElementById("trAddSizeType").style.display = "";
        document.getElementById("trAddFilesEle").style.display = "";
        document.getElementById("trAddDispTxt").style.display = "";
        removeAllValidations("cmbAddValidation");
    }
}

function disableFillCombo(tab)
{
    if (document.getElementById("cmb" + tab + "Onchange").value == "-1")
    {
        document.getElementById("rdo" + tab + "SrcStatic").disabled = false;
        document.getElementById("rdo" + tab + "SrcWS").disabled = false;
        document.getElementById("rdo" + tab + "SrcCommonCmb").disabled = false;
    }
    else
    {
        document.getElementById("rdo" + tab + "SrcStatic").checked = false;
        document.getElementById("rdo" + tab + "SrcWS").checked = false;
        document.getElementById("rdo" + tab + "SrcCommonCmb").checked = false;
        document.getElementById("txt" + tab + "SrcStatic").value = "";
        document.getElementById("txt" + tab + "WsdlUrl").value = "";
        document.getElementById("cmb" + tab + "WsMethod").value = "-1";
        document.getElementById("txt" + tab + "WsCmbValue").value = "";
        document.getElementById("txt" + tab + "WsCmbText").value = "";
        document.getElementById("cmb" + tab + "CommonQuery").value = "-1";
        document.getElementById("rdo" + tab + "SrcStatic").disabled = true;
        document.getElementById("rdo" + tab + "SrcWS").disabled = true;
        document.getElementById("rdo" + tab + "SrcCommonCmb").disabled = true;
    }
    showSrc(tab);
}

function showCodeProperty(tab)
{
    if (document.getElementById("chk" + tab + "Code").checked)
    {
        document.getElementById(tab + "CodeProperty").style.display = "";
    }
    else
    {
        document.getElementById(tab + "CodeProperty").style.display = "none";
    }
}

function showSRSProperty(tab)
{
    if (document.getElementById("chk" + tab + "SRS").checked)
    {
        document.getElementById(tab + "SRSProperty").style.display = "";
    }
    else
    {
        document.getElementById(tab + "SRSProperty").style.display = "none";
    }
}

function showSrc(tab)
{
    if (document.getElementById("chk" + tab + "Src").checked)
    {
        document.getElementById("td" + tab + "Src").style.display = "";
    }
    else
    {
        document.getElementById("td" + tab + "Src").style.display = "none";
    }
    showSrcQuery(tab);
}

function showSrcQuery(tab)
{
    if (document.getElementById("chk" + tab + "Src").checked)
    {
        if (document.getElementById("rdo" + tab + "SrcStatic").checked)
        {
            document.getElementById("tr" + tab + "IndepSrcStatic").style.display = "";
            document.getElementById("tr" + tab + "IndepSrcQuery").style.display = "none";
            document.getElementById("tbd" + tab + "WS").style.display = "none";
            document.getElementById("tr" + tab + "IndepSrcCommonCmb").style.display = "none";
        }
        else if (document.getElementById("rdo" + tab + "SrcQuery").checked)
        {
            document.getElementById("tr" + tab + "IndepSrcStatic").style.display = "none";
            document.getElementById("tr" + tab + "IndepSrcQuery").style.display = "";
            document.getElementById("tbd" + tab + "WS").style.display = "none";
            document.getElementById("tr" + tab + "IndepSrcCommonCmb").style.display = "none";
        }
        else if (document.getElementById("rdo" + tab + "SrcWS").checked)
        {
            document.getElementById("tr" + tab + "IndepSrcStatic").style.display = "none";
            document.getElementById("tr" + tab + "IndepSrcQuery").style.display = "none";
            document.getElementById("tbd" + tab + "WS").style.display = "";
            document.getElementById("tr" + tab + "IndepSrcCommonCmb").style.display = "none";
        }
        else if (document.getElementById("rdo" + tab + "SrcCommonCmb").checked)
        {
            document.getElementById("tr" + tab + "IndepSrcStatic").style.display = "none";
            document.getElementById("tr" + tab + "IndepSrcQuery").style.display = "none";
            document.getElementById("tbd" + tab + "WS").style.display = "none";
            document.getElementById("tr" + tab + "IndepSrcCommonCmb").style.display = "";
        }
        else
        {
            document.getElementById("tr" + tab + "IndepSrcStatic").style.display = "none";
            document.getElementById("tr" + tab + "IndepSrcQuery").style.display = "none";
            document.getElementById("tbd" + tab + "WS").style.display = "none";
            document.getElementById("tr" + tab + "IndepSrcCommonCmb").style.display = "none";
        }
    }
    else
    {
        document.getElementById("tr" + tab + "IndepSrcStatic").style.display = "none";
        document.getElementById("tr" + tab + "IndepSrcQuery").style.display = "none";
        document.getElementById("tbd" + tab + "WS").style.display = "none";
        document.getElementById("tr" + tab + "IndepSrcCommonCmb").style.display = "none";
    }
}

function setIdNameProperties(tab)
{
    document.getElementById("txt" + tab + "MaxLength").value = "";
    if (document.getElementById("cmb" + tab + "Field").value != "-1" && document.getElementById("cmb" + tab + "Field").value != "None")
    {
        document.getElementById("txt" + tab + "Id").value = document.getElementById("cmb" + tab + "Field").value.toLowerCase();
        document.getElementById("txt" + tab + "Name").value = document.getElementById("cmb" + tab + "Field").value.toLowerCase();
        if (document.getElementById("mstColumnWidth") && document.getElementById("mstColumnWidth").value != "")
        {
            var widths = document.getElementById("mstColumnWidth").value.split(',');
            var field = document.getElementById("cmb" + tab + "Field").value;
            var maxLen = "", datatype = "";

            for (var i = 0; i < widths.length; i++)
            {
                if (field == widths[i].split(':')[0])
                {
                    maxLen = widths[i].split(':')[1];
                    datatype = widths[i].split(':')[2];
                }
            }
            if (maxLen != 0)
            {
                document.getElementById("txt" + tab + "MaxLength").value = maxLen;
            }
            if (datatype != null && datatype != "")
            {
                if (datatype == "DATE" || datatype == "DATETIME" || datatype == "TIME" || datatype == "TIMESTAMP")
                {
                    document.getElementById("cmb" + tab + "Control").value = "DatePicker";
                }
                else if (datatype == "TEXT" || ((datatype == "VARCHAR" || datatype == "VARCHAR2") && maxLen > 100))
                {
                    document.getElementById("cmb" + tab + "Control").value = "TextArea";
                }
                else if ((datatype == "VARCHAR" || datatype == "VARCHAR2") && maxLen <= 100)
                {
                    document.getElementById("cmb" + tab + "Control").value = "TextBox";
                }

                if (document.getElementById("cmb" + tab + "Control").value != "-1")
                {
                    if (tab == "Add")
                    {
                        showAddControlProperties();
                    }
                    else if (tab == "Edit")
                    {
                        showEditControlProperties();
                    }
                    else if (tab == "Delete")
                    {
                        showDeleteControlProperties();
                    }
                    else if (tab == "View")
                    {
                        showViewControlProperties();
                    }
                }
            }
        }
    }
    else
    {
        document.getElementById("txt" + tab + "Id").value = "";
        document.getElementById("txt" + tab + "Name").value = "";
    }
}

function validateTabIndex(tabindex)
{
    if (!document.getElementById(tabindex).value.match(/^[0-9]+$/) || parseInt(document.getElementById(tabindex).value, 10) == 0)
    {
        alert("Please Enter Only Positive Digits In Tab Index");
        document.getElementById(tabindex).focus();
        return false;
    }
    return true;
}

function validateValueFormat(rdo1, rdo2, rdo3, rdo4, chkCode, divCodeProp)
{
    if (document.getElementById(rdo1).checked || document.getElementById(rdo2).checked || document.getElementById(rdo3).checked || document.getElementById(rdo4).checked)
    {
        return true;
    }
    else
    {
        alert("Please Select Value Format of Database");
        document.getElementById(chkCode).checked = true;
        document.getElementById(divCodeProp).style.display = "";
        return false;
    }
}

function validateNoOfRadio(totRdo, rdoCap, rdoVal, chkCode, divCodeProp)
{
    document.getElementById(totRdo).value = document.getElementById(totRdo).value.split(' ').join('');
    var caption = document.getElementById(rdoCap).value;
    while (caption.indexOf("  ") >= 0)
    {
        caption = caption.replace('  ', ' ');
    }
    document.getElementById(rdoCap).value = caption.trim();
    document.getElementById(rdoVal).value = document.getElementById(rdoVal).value.split(' ').join('');
    if (document.getElementById(totRdo).value == "")
    {
        alert("Please Enter No. Of Radio Buttons");
        document.getElementById(chkCode).checked = true;
        document.getElementById(divCodeProp).style.display = "";
        document.getElementById(totRdo).focus();
        return false;
    }
    else if (!document.getElementById(totRdo).value.match(/^[0-9]+$/) || parseInt(document.getElementById(totRdo).value, 10) == 0)
    {
        alert("Please Enter Positive Digits For No. Of Radio Buttons");
        document.getElementById(chkCode).checked = true;
        document.getElementById(divCodeProp).style.display = "";
        document.getElementById(totRdo).focus();
        return false;
    }
    else
    {
        if (document.getElementById(rdoCap).value == "")
        {
            alert("Please Enter Captions For Radio Buttons");
            document.getElementById(chkCode).checked = true;
            document.getElementById(divCodeProp).style.display = "";
            document.getElementById(rdoCap).focus();
            return false;
        }
        else
        {
            var val = document.getElementById(rdoCap).value;
            while (val.charAt(0) == "/")
            {
                val = val.substr(1).trim();
            }
            while (val.charAt(val.length - 1) == "/")
            {
                val = val.substr(0, val.length - 1).trim();
            }
            document.getElementById(rdoCap).value = val;
            while (val.indexOf("/ /") >= 0)
            {
                val = val.replace('/ /', '/').trim();
            }
            while (val.indexOf("//") >= 0)
            {
                val = val.replace('//', '/').trim();
            }
            while (val.indexOf("/ ") >= 0)
            {
                val = val.replace('/ ', '/').trim();
            }
            while (val.indexOf(" /") >= 0)
            {
                val = val.replace(' /', '/').trim();
            }
            document.getElementById(rdoCap).value = val;
            if (val.split("/").length != document.getElementById(totRdo).value)
            {
                alert("No. Of Radio And No. Of Captions Do Not Match");
                return false;
            }
        }
        if (document.getElementById(rdoVal).value == "")
        {
            alert("Please Enter Values For Radio Buttons");
            document.getElementById(chkCode).checked = true;
            document.getElementById(divCodeProp).style.display = "";
            document.getElementById(rdoVal).focus();
            return false;
        }
        else
        {
            var val = document.getElementById(rdoVal).value;
            while (val.charAt(0) == "/")
            {
                val = val.substr(1);
            }
            while (val.charAt(val.length - 1) == "/")
            {
                val = val.substr(0, val.length - 1);
            }
            document.getElementById(rdoVal).value = val;
            while (val.indexOf("//") >= 0)
            {
                val = val.replace('//', '/');
            }
            document.getElementById(rdoVal).value = val;
            if (val.split("/").length != document.getElementById(totRdo).value)
            {
                alert("No. Of Radio And No. Of Values Do Not Match");
                return false;
            }
        }
    }
    return true;
}

function validateRowCol(row, col, chkCode, divCodeProp)
{
    document.getElementById(row).value = document.getElementById(row).value.split(' ').join('');
    document.getElementById(col).value = document.getElementById(col).value.split(' ').join('');
    if (document.getElementById(row).value != "")
    {
        if (!document.getElementById(row).value.match(/^[0-9]+$/) || parseInt(document.getElementById(row).value, 10) == 0)
        {
            alert("Please Enter Positive Digits For Rows");
            document.getElementById(chkCode).checked = true;
            document.getElementById(divCodeProp).style.display = "";
            document.getElementById(row).focus();
            return false;
        }
    }
    if (document.getElementById(col).value != "")
    {
        if (!document.getElementById(col).value.match(/^[0-9]+$/) || parseInt(document.getElementById(col).value, 10) == 0)
        {
            alert("Please Enter Positive Digits For Columns");
            document.getElementById(chkCode).checked = true;
            document.getElementById(divCodeProp).style.display = "";
            document.getElementById(col).focus();
            return false;
        }
    }
    return true;
}

function validateAddTabData()
{
    if (document.getElementById("cmbAddField").value == "-1")
    {
        alert("Please Select Field");
        document.getElementById("cmbAddField").focus();
        return;
    }
    else if (document.getElementById("cmbAddControl").value == "-1")
    {
        alert("Please Select Control");
        document.getElementById("cmbAddControl").focus();
        return;
    }
    else if (document.getElementById("txtAddTabIndex").value == "")
    {
        alert("Please Enter Tab Index");
        document.getElementById("txtAddTabIndex").focus();
        return;
    }
    else if (!document.getElementById("chkShowAdd").checked && !document.getElementById("chkShowEdit").checked && !document.getElementById("chkShowDel").checked)
    {
        alert("Please Select Atleast One Tab To Show Field");
        return;
    }
    else
    {
        if (validateTabIndex("txtAddTabIndex"))
        {
            if (document.getElementById("cmbAddControl").value == "CheckBox")
            {
                if (document.getElementById("cmbAddField").value != "None")
                {
                    if (validateValueFormat("rbtnAddCheckedOneZero", "rbtnAddCheckedYesNo", "rbtnAddCheckedYN", "rbtnAddCheckedTF", "chkAddCode", "AddCodeProperty"))
                    {
                        validateBeforeAdd();
                    }
                }
                else
                {
                    validateBeforeAdd();
                }
            }
            else if (document.getElementById("cmbAddControl").value == "Radio")
            {
                if (validateNoOfRadio("txtAddTotalRdo", "txtAddRdoCap", "txtAddRdoVal", "chkAddCode", "AddCodeProperty"))
                {
                    if (document.getElementById("txtAddRdoDefVal").value != "")
                    {
                        var defVal = document.getElementById("txtAddRdoDefVal").value;
                        if (defVal.match(/^[0-9]+$/))
                        {
                            if (parseInt(defVal, 10) >= 1 && parseInt(defVal, 10) <= parseInt(document.getElementById("txtAddTotalRdo").value, 10))
                            {
                                validateBeforeAdd();
                            }
                            else
                            {
                                alert("Please Enter Number Between 1 And " + parseInt(document.getElementById("txtAddTotalRdo").value, 10));
                                document.getElementById("txtAddRdoDefVal").focus();
                                return;
                            }
                        }
                        else
                        {
                            alert("Please Enter Number For Default Selection Of Radio");
                            document.getElementById("txtAddRdoDefVal").focus();
                            return;
                        }
                    }
                    else
                    {
                        validateBeforeAdd();
                    }
                }
            }
            else if (document.getElementById("cmbAddControl").value == "TextArea")
            {
                if (validateRowCol("txtAddRows", "txtAddCols", "chkAddCode", "AddCodeProperty"))
                {
                    validateBeforeAdd();
                }
            }
            else if (document.getElementById("cmbAddControl").value == "FileBox")
            {
                if (document.getElementById("txtAddMaxsize").value != "")
                {
                    if (!document.getElementById("txtAddMaxsize").value.match(/^[0-9]+$/) || parseInt(document.getElementById("txtAddMaxsize").value, 10) == 0)
                    {
                        alert("Please Enter Positive Digits For Maxsize");
                        document.getElementById("chkAddCode").checked = true;
                        document.getElementById("AddCodeProperty").style.display = "";
                        document.getElementById("txtAddMaxsize").focus();
                        return;
                    }
                    else
                    {
                        validateBeforeAdd();
                    }
                }
                else
                {
                    validateBeforeAdd();
                }
            }
            else if (document.getElementById("cmbAddControl").value == "ComboBox" || document.getElementById("cmbAddControl").value == "TextLikeCombo")
            {
                if (document.getElementById("cmbAddOnchange").value != "-1")
                {
                    if (document.getElementById("txtAddSrcQuery").value == "")
                    {
                        document.getElementById("chkAddSrc").checked = true;
                        document.getElementById("rdoAddSrcQuery").checked = true;
                        showSrc('Add');
                        alert("Please Enter Query To Fill Combo");
                        return;
                    }
                    else
                    {
                        if (document.getElementById("txtAddSrcQuery").value.indexOf("|VARIABLE|") <= 0)
                        {
                            alert("You must specify where to match master combo's value in the Query by |VARIABLE| Keyword");
                            return;
                        }
                        else
                        {
                            validateBeforeAdd();
                        }
                    }
                }
                else
                {
                    validateBeforeAdd();
                }
            }
            else
            {
                validateBeforeAdd();
            }
        }
    }
}

function checkDataFormat(tab)
{
    var staticData = document.getElementById("txt" + tab + "SrcStatic").value;
    if (!staticData.match(/^(([A-Za-z0-9\-\_\.& ]+),([A-Za-z0-9\-\_\.& ]+);)*([A-Za-z0-9\-\_\.& ]+),([A-Za-z0-9\-\_\.& ]+){1}$/))
    {
        alert("Please Enter Data In Valid Format To Fill Combo");
        document.getElementById(tab + "StaticResult").innerHTML = "Invalid Data Format";
        return;
    }
    else
    {
        document.getElementById(tab + "StaticResult").innerHTML = "";
        return;
    }
}

function checkQuery(tab)
{
    if (document.getElementById("txt" + tab + "SrcQuery").value == "")
    {
        alert("Please Enter Query To Fill Combo");
        return;
    }
    else
    {
        var params = getFormData(document.MenuForm);
        params += "&qTab=";
        params += tab;
        getData_sync("mstgenv2.fin?cmdAction=checkQuery", tab + "QueryResult", params, false);
        if (document.getElementById(tab + "QueryResult").innerHTML.match("Valid Query") != null)
        {
            document.getElementById(tab + "QueryResult").innerHTML = "";
            return;
        }
    }
}

function getWsdlMethods(tab)
{
    document.getElementById("txt" + tab + "WsdlUrl").value = document.getElementById("txt" + tab + "WsdlUrl").value.replace(/^\s+|\s+$/g, '');
    document.getElementById("cmb" + tab + "WsMethod").innerHTML = "<option value=\"-1\">-- Select Method --</option>";
    if (document.getElementById("txt" + tab + "WsRetType") != null)
    {
        document.getElementById("txt" + tab + "WsRetType").value = "";
    }
    if (document.getElementById("txt" + tab + "WsParams") != null)
    {
        document.getElementById("txt" + tab + "WsParams").value = "";
    }
    if (document.getElementById("txt" + tab + "WsExps") != null)
    {
        document.getElementById("txt" + tab + "WsExps").value = "";
    }
    if (document.getElementById("txt" + tab + "WsdlUrl").value == "")
    {
        alert("Please Enter wsdl URL");
        return;
    }
    var params = getFormData(document.MenuForm);
    params += "&ForTab=";
    params += tab;
    getSynchronousData("mstgenv2.fin?cmdAction=getWsDetails", params, "td" + tab + "Wsdl");
    if (document.getElementById("txt" + tab + "WsProject") == null || document.getElementById("txt" + tab + "WsIntrface") == null)
    {
        alert("WSDL Entry is Not As Per Standard");
        return;
    }
    else
    {
        params = getFormData(document.MenuForm);
        params += "&ForTab=";
        params += tab;
        getSynchronousData("mstgenv2.fin?cmdAction=getWsMethods", params, "td" + tab + "Wsdl");
        if (document.getElementById("tmpCmb" + tab + "WsMethod") != null)
        {
            document.getElementById("cmb" + tab + "WsMethod").innerHTML = "<option value=\"-1\">-- Select Method --</option>";
            document.getElementById("cmb" + tab + "WsMethod").innerHTML += document.getElementById("tmpCmb" + tab + "WsMethod").innerHTML;
        }
    }
}

function getWsMethodParams(tab)
{
    if (document.getElementById("cmb" + tab + "WsMethod").value == "-1")
    {
        alert("Please Select Method to Fill ComboBox");
        document.getElementById("cmb" + tab + "WsMethod").focus();
        if (document.getElementById("txt" + tab + "WsRetType") != "null")
        {
            document.getElementById("txt" + tab + "WsRetType").value = "";
        }
        if (document.getElementById("txt" + tab + "WsParams") != "null")
        {
            document.getElementById("txt" + tab + "WsParams").value = "";
        }
        if (document.getElementById("txt" + tab + "WsExps") != "null")
        {
            document.getElementById("txt" + tab + "WsExps").value = "";
        }
        return;
    }
    else
    {
        var params = getFormData(document.MenuForm);
        params += "&ForTab=";
        params += tab;
        getSynchronousData("mstgenv2.fin?cmdAction=getWsMethodParams", params, "td" + tab + "Wsdl");
    }
}

function validateCntrlIDName(tab)
{
    if (document.getElementById("txt" + tab + "Id").value == "")
    {
        alert("Please Enter Control Id");
        if (!document.getElementById("chk" + tab + "Code").checked)
        {
            document.getElementById("chk" + tab + "Code").checked = true;
            document.getElementById(tab + "CodeProperty").style.display = "";
        }
        document.getElementById("txt" + tab + "Id").focus();
        return false;
    }
    if (document.getElementById("txt" + tab + "Name").value == "")
    {
        alert("Please Enter Control Name");
        if (!document.getElementById("chk" + tab + "Code").checked)
        {
            document.getElementById("chk" + tab + "Code").checked = true;
            document.getElementById(tab + "CodeProperty").style.display = "";
        }
        document.getElementById("txt" + tab + "Name").focus();
        return false;
    }

    var id = document.getElementById("txt" + tab + "Id").value;
    id = id.toString().toLowerCase();
    id = id.split(' ').join('')
    document.getElementById("txt" + tab + "Id").value = id;
    var name = document.getElementById("txt" + tab + "Name").value;
    name = name.toString().toLowerCase();
    name = name.split(' ').join('')
    document.getElementById("txt" + tab + "Name").value = name;

    if (!id.match("^[A-Za-z]{1}[A-Za-z0-9_]*$"))
    {
        alert("Invalid Control Id");
        document.getElementById("txt" + tab + "Id").focus();
        return false;
    }
    if (!name.match("^[A-Za-z]{1}[A-Za-z0-9_]*$"))
    {
        alert("Invalid Control Name");
        document.getElementById("txt" + tab + "Name").focus();
        return false;
    }

    for (var i = 0; i < keywords.length; i++)
    {
        if (id == keywords[i])
        {
            alert("Control Id Cannot Be Any Java Keyword");
            document.getElementById("txt" + tab + "Id").focus();
            return false;
        }
        if (name == keywords[i])
        {
            alert("Control Name Cannot Be Any Java Keyword");
            document.getElementById("txt" + tab + "Name").focus();
            return false;
        }
    }
    return true;
}

function checkControlID(id)
{
    for (var i = 1; i < addListDivId; i++)
    {
        if (document.getElementById("divAdd" + i) != null)
        {
            if (id == document.getElementById("divAdd" + i).childNodes[4].value)
            {
                return true;
            }
        }
    }
    for (i = 1; i < editListDivId; i++)
    {
        if (document.getElementById("divEdit" + i) != null)
        {
            if (id == document.getElementById("divEdit" + i).childNodes[4].value)
            {
                return true;
            }
        }
    }
    for (i = 1; i < deleteListDivId; i++)
    {
        if (document.getElementById("divDel" + i) != null)
        {
            if (id == document.getElementById("divDel" + i).childNodes[4].value)
            {
                return true;
            }
        }
    }
    for (i = 1; i < viewListDivId; i++)
    {
        if (document.getElementById("divView" + i) != null)
        {
            if (id == document.getElementById("divView" + i).childNodes[4].value)
            {
                return true;
            }
        }
    }
    return false;
}

function checkControlName(name)
{
    for (var i = 1; i < addListDivId; i++)
    {
        if (document.getElementById("divAdd" + i) != null)
        {
            if (name == document.getElementById("divAdd" + i).childNodes[5].value)
            {
                return true;
            }
        }
    }
    for (i = 1; i < editListDivId; i++)
    {
        if (document.getElementById("divEdit" + i) != null)
        {
            if (name == document.getElementById("divEdit" + i).childNodes[5].value)
            {
                return true;
            }
        }
    }
    for (i = 1; i < deleteListDivId; i++)
    {
        if (document.getElementById("divDel" + i) != null)
        {
            if (name == document.getElementById("divDel" + i).childNodes[5].value)
            {
                return true;
            }
        }
    }
    for (i = 1; i < viewListDivId; i++)
    {
        if (document.getElementById("divView" + i) != null)
        {
            if (name == document.getElementById("divView" + i).childNodes[5].value)
            {
                return true;
            }
        }
    }
    return false;
}

function validateBeforeAdd()
{
    var table = document.getElementById("addTabListTable");
    var rowCount = table.rows.length;

    var tabIdx = document.getElementById("txtAddTabIndex").value;
    for (var i = 2; i < rowCount; i++)
    {
        var rowtab = table.rows[i].cells[2].innerHTML;
        if (parseInt(tabIdx, 10) == rowtab)
        {
            alert("Duplicate Tab Index");
            document.getElementById("txtAddTabIndex").value = parseInt(tabIdx, 10);
            document.getElementById("txtAddTabIndex").focus();
            return;
        }
    }

    if (validateCntrlIDName("Add"))
    {
        if (checkControlID(document.getElementById("txtAddId").value))
        {
            alert("You Cannot Enter Duplicate Control ID");
            if (!document.getElementById("chkAddCode").checked)
            {
                document.getElementById("chkAddCode").checked = true;
                document.getElementById("AddCodeProperty").style.display = "";
            }
            document.getElementById("txtAddId").focus();
            return;
        }
        else if (checkElementName(document.getElementById("txtAddId").value))
        {
            alert("Control ID Cannot be same as Element Name");
            if (!document.getElementById("chkAddCode").checked)
            {
                document.getElementById("chkAddCode").checked = true;
                document.getElementById("AddCodeProperty").style.display = "";
            }
            document.getElementById("txtAddId").focus();
            return;
        }
        else if (checkControlName(document.getElementById("txtAddName").value))
        {
            alert("You Cannot Enter Duplicate Control Name");
            if (!document.getElementById("chkAddCode").checked)
            {
                document.getElementById("chkAddCode").checked = true;
                document.getElementById("AddCodeProperty").style.display = "";
            }
            document.getElementById("txtAddName").focus();
            return;
        }
    }
    else
    {
        return;
    }

    if (document.getElementById("cmbAddControl").value == "FileBox")
    {
        if (document.getElementById("txtAddType").value == "")
        {
            alert("Please Enter Type");
            if (!document.getElementById("chkAddCode").checked)
            {
                document.getElementById("chkAddCode").checked = true;
                document.getElementById("AddCodeProperty").style.display = "";
            }
            document.getElementById("txtAddType").focus();
            return;
        }
        else if (!checkFileTypeFormat("Add"))
        {
            alert("Please Enter Type In Valid Format");
            if (!document.getElementById("chkAddCode").checked)
            {
                document.getElementById("chkAddCode").checked = true;
                document.getElementById("AddCodeProperty").style.display = "";
            }
            document.getElementById("txtAddType").focus();
            return;
        }
        else if (!checkRestrictedFileType("Add"))
        {
            return;
        }
        else if (document.getElementById("txtAddEleName").value == "")
        {
            alert("Please Enter Element Name");
            if (!document.getElementById("chkAddCode").checked)
            {
                document.getElementById("chkAddCode").checked = true;
                document.getElementById("AddCodeProperty").style.display = "";
            }
            document.getElementById("txtAddEleName").focus();
            return;
        }
    }
    else if (document.getElementById("cmbAddControl").value == "ComboBox" || document.getElementById("cmbAddControl").value == "TextLikeCombo")
    {
        if (document.getElementById("chkAddSrc").checked)
        {
            if (document.getElementById("rdoAddSrcStatic").checked)
            {
                if (document.getElementById("txtAddSrcStatic").value == "")
                {
                    alert("Please Enter Data To Fill Combo");
                    return;
                }
                else if (document.getElementById("AddStaticResult").innerHTML != "")
                {
                    return;
                }
            }
            else if (document.getElementById("rdoAddSrcQuery").checked)
            {
                if (document.getElementById("txtAddSrcQuery").value == "")
                {
                    alert("Please Enter Query To Fill Combo");
                    return;
                }
                else if (document.getElementById("AddQueryResult").innerHTML != "")
                {
                    return;
                }
            }
            else if (document.getElementById("rdoAddSrcWS").checked)
            {
                if (document.getElementById("txtAddWsdlUrl").value == "")
                {
                    alert("Please Enter wsdl URL");
                    return;
                }
                if (document.getElementById("cmbAddWsMethod").value == "-1")
                {
                    alert("Please Select Method to Fill ComboBox");
                    document.getElementById("cmbAddWsMethod").focus();
                    return;
                }
                if (document.getElementById("txtAddWsRetType") == null || document.getElementById("txtAddWsParams") == null)
                {
                    alert("Please Enter Valid Values");
                    return;
                }
                document.getElementById("txtAddWsCmbValue").value = document.getElementById("txtAddWsCmbValue").value.replace(/\s+|\s+/g, '');
                document.getElementById("txtAddWsCmbText").value = document.getElementById("txtAddWsCmbText").value.replace(/\s+|\s+/g, '');
                if (document.getElementById("txtAddWsCmbValue").value == "")
                {
                    alert("Please Enter Field For Value In Combo Option");
                    document.getElementById("txtAddWsCmbValue").focus();
                    return;
                }
                else if (!document.getElementById("txtAddWsCmbValue").value.match(/^[A-Za-z_]+$/) || document.getElementById("txtAddWsCmbValue").value.match(/^[_]+$/))
                {
                    alert("Please Enter Valid Field For Value In Combo Option");
                    document.getElementById("txtAddWsCmbValue").focus();
                    return;
                }
                if (document.getElementById("txtAddWsCmbText").value == "")
                {
                    alert("Please Enter Field For Text In Combo Option");
                    document.getElementById("txtAddWsCmbText").focus();
                    return;
                }
                else if (!document.getElementById("txtAddWsCmbText").value.match(/^[A-Za-z_]+$/) || document.getElementById("txtAddWsCmbText").value.match(/^[_]+$/))
                {
                    alert("Please Enter Valid Field For Text In Combo Option");
                    document.getElementById("txtAddWsCmbText").focus();
                    return;
                }
            }
            else if (document.getElementById("rdoAddSrcCommonCmb").checked)
            {
                if (document.getElementById("cmbAddCommonQuery").value == "-1")
                {
                    alert("Please Select any one option To Fill Combo");
                    document.getElementById("cmbAddCommonQuery").focus();
                    return;
                }
            }
            else
            {
                alert("Please Enter Data Source");
                return;
            }
        }
    }
    if (document.getElementById("cmbAddControl").value == "FileBox")
    {
        var eleName = document.getElementById("txtAddEleName").value;
        eleName = eleName.toString().toLowerCase();
        eleName = eleName.split(' ').join('')
        document.getElementById("txtAddEleName").value = eleName;

        if (checkElementName(eleName))
        {
            alert("You Cannot Enter Duplicate Element Name");
            if (!document.getElementById("chkAddCode").checked)
            {
                document.getElementById("chkAddCode").checked = true;
                document.getElementById("AddCodeProperty").style.display = "";
            }
            document.getElementById("txtAddEleName").focus();
            return;
        }
        else if (checkControlID(eleName))
        {
            alert("Element Name Cannot be same as Control Id");
            if (!document.getElementById("chkAddCode").checked)
            {
                document.getElementById("chkAddCode").checked = true;
                document.getElementById("AddCodeProperty").style.display = "";
            }
            document.getElementById("txtAddEleName").focus();
            return;
        }
    }
    addNewAddTabRow();
}

var addListDivId = 1;
function addNewAddTabRow()
{
    document.getElementById("addTabList").style.display = "";
    var table = document.getElementById("addTabListTable");
    var rowCount = table.rows.length;
    var row = table.insertRow(rowCount);

    var newCell0 = row.insertCell(0);
    var newCell1 = row.insertCell(1);
    var newCell2 = row.insertCell(2);
    var newCell3 = row.insertCell(3);
    var newCell4 = row.insertCell(4);
    var newCell5 = row.insertCell(5);
    var newCell6 = row.insertCell(6);
    var newCell7 = row.insertCell(7);
    var newCell8 = row.insertCell(8);
    newCell8.style.display = "none";

    var field = document.getElementById("cmbAddField").value;
    var combo = document.getElementById("cmbAddField");

    if (field != "None")
    {
        for (var i = 0; i < combo.options.length; i++)
        {
            if (combo.options[i].value == field)
            {
                combo.options[i].disabled = true;
                break;
            }
        }
    }

    newCell0.innerHTML = field;
    newCell1.innerHTML = document.getElementById("cmbAddControl").value;
    var tabIdx = document.getElementById("txtAddTabIndex").value;
    newCell2.innerHTML = parseInt(tabIdx, 10);
    if (document.getElementById("chkShowAdd").checked)
    {
        newCell3.innerHTML = "<input type='checkbox' checked disabled />";
    }
    else
    {
        newCell3.innerHTML = "<input type='checkbox' disabled />";
    }
    if (document.getElementById("chkShowEdit").checked)
    {
        newCell4.innerHTML = "<input type='checkbox' checked disabled />";
    }
    else
    {
        newCell4.innerHTML = "<input type='checkbox' disabled />";
    }
    if (document.getElementById("chkShowDel").checked)
    {
        newCell5.innerHTML = "<input type='checkbox' checked disabled />";
    }
    else
    {
        newCell5.innerHTML = "<input type='checkbox' disabled />";
    }
    newCell6.innerHTML = "<a onclick='javascript: editAddTabRow(this.parentNode.parentNode.rowIndex)'><img style=\"width:20px\" align =\"middle\" src=\"images/edit.gif\" alt=\"Edit\"></a>";
    newCell7.innerHTML = "<a onclick='javascript: checkAddDeletePossible(this.parentNode.parentNode.rowIndex)'><img style=\"width:20px\" align='center' src=\"images/delete.gif\" alt=\"Delete\"></a>";
    newCell8.innerHTML = "divAdd" + addListDivId;

    var ADDmaindiv = document.getElementById("hiddenDivAddTab");
    var ADDdiv = document.createElement("div");
    ADDdiv.id = "divAdd" + addListDivId;

    ADDdiv.innerHTML = "<input type='hidden' name='hdnAddField' id='hdnAddField' value='" + field + "'/>";
    ADDdiv.innerHTML += "<input type='hidden' name='hdnAddControl' id='hdnAddControl' value='" + document.getElementById("cmbAddControl").value + "'/>";
    //srs properties
    ADDdiv.innerHTML += "<input type='hidden' name='hdncmbAddValidation' id='hdncmbAddValidation' value='" + document.getElementById("cmbAddValidation").value + "'/>";
    ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddRemarks' id='hdntxtAddRemarks' value='" + document.getElementById("txtAddRemarks").value + "'/>";
    //code properties
    ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddId' id='hdntxtAddId' value='" + document.getElementById("txtAddId").value + "'/>";
    ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddName' id='hdntxtAddName' value='" + document.getElementById("txtAddName").value + "'/>";
    ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddStyle' id='hdntxtAddStyle' value='" + document.getElementById("txtAddStyle").value + "'/>";
    ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddSize' id='hdntxtAddSize' value='" + document.getElementById("txtAddSize").value + "'/>";
    ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddClass' id='hdntxtAddClass' value='" + document.getElementById("txtAddClass").value + "'/>";
    ADDdiv.innerHTML += "<input type='hidden' name='hdncmbAddOnchange' id='hdncmbAddOnchange' value='" + document.getElementById("cmbAddOnchange").value + "'/>";
    if (document.getElementById("cmbAddControl").value == "ComboBox" || document.getElementById("cmbAddControl").value == "TextLikeCombo")
    {
        document.getElementById("cmbAddOnchange").innerHTML += "<option value=\"" + document.getElementById("txtAddId").value + "\">" + document.getElementById("txtAddId").value + "</option>";
    }
    ADDdiv.innerHTML += "<input type='hidden' name='hdnrbtnAddMultiple' id='hdnrbtnAddMultiple' value='" + document.getElementById("rbtnAddMultipleTrue").checked + "'/>";
    ADDdiv.innerHTML += "<input type='hidden' name='hdnrbtnAddChecked' id='hdnrbtnAddChecked' value='" + document.getElementById("rbtnAddCheckedTrue").checked + "'/>";
    ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddMaxLength' id='hdntxtAddMaxLength' value='" + document.getElementById("txtAddMaxLength").value + "'/>";
    ADDdiv.innerHTML += "<input type='hidden' name='hdnrbtnAddReadonly' id='hdnrbtnAddReadonly' value='" + document.getElementById("rbtnAddReadonlyTrue").checked + "'/>";
    ADDdiv.innerHTML += "<input type='hidden' name='hdncmbAddAlign' id='hdncmbAddAlign' value='" + document.getElementById("cmbAddAlign").value + "'/>";
    ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddRows' id='hdntxtAddRows' value='" + document.getElementById("txtAddRows").value + "'/>";
    ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddCols' id='hdntxtAddCols' value='" + document.getElementById("txtAddCols").value + "'/>";
    ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddValue' id='hdntxtAddValue' value='" + document.getElementById("txtAddValue").value + "'/>";
    ADDdiv.innerHTML += "<input type='hidden' name='hdnAddTabIndex' id='hdnAddTabIndex' value='" + parseInt(tabIdx, 10) + "'/>";

    if (document.getElementById("rbtnAddCheckedOneZero").checked)
    {
        ADDdiv.innerHTML += "<input type='hidden' name='hdnrbtnAddValueFormat' id='hdnrbtnAddValueFormat' value='onezero'/>";
    }
    else if (document.getElementById("rbtnAddCheckedYesNo").checked)
    {
        ADDdiv.innerHTML += "<input type='hidden' name='hdnrbtnAddValueFormat' id='hdnrbtnAddValueFormat' value='yesno'/>";
    }
    else if (document.getElementById("rbtnAddCheckedYN").checked)
    {
        ADDdiv.innerHTML += "<input type='hidden' name='hdnrbtnAddValueFormat' id='hdnrbtnAddValueFormat' value='yn'/>";
    }
    else if (document.getElementById("rbtnAddCheckedTF").checked)
    {
        ADDdiv.innerHTML += "<input type='hidden' name='hdnrbtnAddValueFormat' id='hdnrbtnAddValueFormat' value='truefalse'/>";
    }
    else
    {
        ADDdiv.innerHTML += "<input type='hidden' name='hdnrbtnAddValueFormat' id='hdnrbtnAddValueFormat' value='NA'/>";
    }
    ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddTotalRdo' id='hdntxtAddTotalRdo' value='" + document.getElementById("txtAddTotalRdo").value + "'/>";
    ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddRdoCap' id='hdntxtAddRdoCap' value='" + document.getElementById("txtAddRdoCap").value + "'/>";
    ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddRdoVal' id='hdntxtAddRdoVal' value='" + document.getElementById("txtAddRdoVal").value + "'/>";
    ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddRdoDefVal' id='hdntxtAddRdoDefVal' value='" + document.getElementById("txtAddRdoDefVal").value + "'/>";
    ADDdiv.innerHTML += "<input type='hidden' name='hdnChkShowAdd' id='hdnChkShowAdd' value='" + document.getElementById("chkShowAdd").checked + "'/>";
    ADDdiv.innerHTML += "<input type='hidden' name='hdnChkShowEdit' id='hdnChkShowEdit' value='" + document.getElementById("chkShowEdit").checked + "'/>";
    ADDdiv.innerHTML += "<input type='hidden' name='hdnChkShowDel' id='hdnChkShowDel' value='" + document.getElementById("chkShowDel").checked + "'/>";
    ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddLabel' id='hdntxtAddLabel' value='" + document.getElementById("txtAddLabel").value + "'/>";

    if (document.getElementById("cmbAddControl").value == "ComboBox" || document.getElementById("cmbAddControl").value == "TextLikeCombo")
    {
        ADDdiv.innerHTML += "<input type='hidden' name='hdnchkAddSrc' id='hdnchkAddSrc' value='" + document.getElementById("chkAddSrc").checked + "'/>";
        if (document.getElementById("chkAddSrc").checked)
        {
            if (document.getElementById("rdoAddSrcStatic").checked)
            {
                ADDdiv.innerHTML += "<input type='hidden' name='hdnrdoAddDataSrc' id='hdnrdoAddDataSrc' value='Static'/>";
                ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddSrcStatic' id='hdntxtAddSrcStatic' value='" + document.getElementById("txtAddSrcStatic").value + "'/>";
                ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddSrcQuery' id='hdntxtAddSrcQuery' value=''/>";
                ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddWsdlUrl' id='hdntxtAddWsdlUrl' value=''/>";
                ADDdiv.innerHTML += "<input type='hidden' name='hdncmbAddWsMethod' id='hdncmbAddWsMethod' value=''/>";
                ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddWsCmbValue' id='hdntxtAddWsCmbValue' value=''/>";
                ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddWsCmbText' id='hdntxtAddWsCmbText' value=''/>";
                ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddWsProject' id='hdntxtAddWsProject' value=''/>";
                ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddWsIntrface' id='hdntxtAddWsIntrface' value=''/>";
                ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddWsRetType' id='hdntxtAddWsRetType' value=''/>";
                ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddWsParams' id='hdntxtAddWsParams' value=''/>";
                ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddWsExps' id='hdntxtAddWsExps' value=''/>";
                ADDdiv.innerHTML += "<input type='hidden' name='hdncmbAddCommonQuery' id='hdncmbAddCommonQuery' value=''/>";
            }
            else if (document.getElementById("rdoAddSrcQuery").checked)
            {
                ADDdiv.innerHTML += "<input type='hidden' name='hdnrdoAddDataSrc' id='hdnrdoAddDataSrc' value='Query'/>";
                ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddSrcStatic' id='hdntxtAddSrcStatic' value=''/>";
                ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddSrcQuery' id='hdntxtAddSrcQuery' value='" + escape(document.getElementById("txtAddSrcQuery").value) + "'/>";
                ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddWsdlUrl' id='hdntxtAddWsdlUrl' value=''/>";
                ADDdiv.innerHTML += "<input type='hidden' name='hdncmbAddWsMethod' id='hdncmbAddWsMethod' value=''/>";
                ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddWsCmbValue' id='hdntxtAddWsCmbValue' value=''/>";
                ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddWsCmbText' id='hdntxtAddWsCmbText' value=''/>";
                ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddWsProject' id='hdntxtAddWsProject' value=''/>";
                ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddWsIntrface' id='hdntxtAddWsIntrface' value=''/>";
                ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddWsRetType' id='hdntxtAddWsRetType' value=''/>";
                ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddWsParams' id='hdntxtAddWsParams' value=''/>";
                ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddWsExps' id='hdntxtAddWsExps' value=''/>";
                ADDdiv.innerHTML += "<input type='hidden' name='hdncmbAddCommonQuery' id='hdncmbAddCommonQuery' value=''/>";
            }
            else if (document.getElementById("rdoAddSrcWS").checked)
            {
                ADDdiv.innerHTML += "<input type='hidden' name='hdnrdoAddDataSrc' id='hdnrdoAddDataSrc' value='WebService'/>";
                ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddSrcStatic' id='hdntxtAddSrcStatic' value=''/>";
                ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddSrcQuery' id='hdntxtAddSrcQuery' value=''/>";
                ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddWsdlUrl' id='hdntxtAddWsdlUrl' value='" + document.getElementById("txtAddWsdlUrl").value + "'/>";
                ADDdiv.innerHTML += "<input type='hidden' name='hdncmbAddWsMethod' id='hdncmbAddWsMethod' value='" + document.getElementById("cmbAddWsMethod").value + "'/>";
                ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddWsCmbValue' id='hdntxtAddWsCmbValue' value='" + document.getElementById("txtAddWsCmbValue").value + "'/>";
                ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddWsCmbText' id='hdntxtAddWsCmbText' value='" + document.getElementById("txtAddWsCmbText").value + "'/>";
                ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddWsProject' id='hdntxtAddWsProject' value='" + document.getElementById("txtAddWsProject").value + "'/>";
                ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddWsIntrface' id='hdntxtAddWsIntrface' value='" + document.getElementById("txtAddWsIntrface").value + "'/>";
                ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddWsRetType' id='hdntxtAddWsRetType' value='" + document.getElementById("txtAddWsRetType").value + "'/>";
                ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddWsParams' id='hdntxtAddWsParams' value='" + document.getElementById("txtAddWsParams").value + "'/>";
                ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddWsExps' id='hdntxtAddWsExps' value='" + document.getElementById("txtAddWsExps").value + "'/>";
                ADDdiv.innerHTML += "<input type='hidden' name='hdncmbAddCommonQuery' id='hdncmbAddCommonQuery' value=''/>";
            }
            else if (document.getElementById("rdoAddSrcCommonCmb").checked)
            {
                ADDdiv.innerHTML += "<input type='hidden' name='hdnrdoAddDataSrc' id='hdnrdoAddDataSrc' value='CommonCmb'/>";
                ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddSrcStatic' id='hdntxtAddSrcStatic' value=''/>";
                ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddSrcQuery' id='hdntxtAddSrcQuery' value=''/>";
                ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddWsdlUrl' id='hdntxtAddWsdlUrl' value=''/>";
                ADDdiv.innerHTML += "<input type='hidden' name='hdncmbAddWsMethod' id='hdncmbAddWsMethod' value=''/>";
                ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddWsCmbValue' id='hdntxtAddWsCmbValue' value=''/>";
                ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddWsCmbText' id='hdntxtAddWsCmbText' value=''/>";
                ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddWsProject' id='hdntxtAddWsProject' value=''/>";
                ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddWsIntrface' id='hdntxtAddWsIntrface' value=''/>";
                ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddWsRetType' id='hdntxtAddWsRetType' value=''/>";
                ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddWsParams' id='hdntxtAddWsParams' value=''/>";
                ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddWsExps' id='hdntxtAddWsExps' value=''/>";
                ADDdiv.innerHTML += "<input type='hidden' name='hdncmbAddCommonQuery' id='hdncmbAddCommonQuery' value='" + document.getElementById("cmbAddCommonQuery").value + "'/>";
            }
        }
        else
        {
            ADDdiv.innerHTML += "<input type='hidden' name='hdnrdoAddDataSrc' id='hdnrdoAddDataSrc' value=''/>";
            ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddSrcStatic' id='hdntxtAddSrcStatic' value=''/>";
            ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddSrcQuery' id='hdntxtAddSrcQuery' value=''/>";
            ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddWsdlUrl' id='hdntxtAddWsdlUrl' value=''/>";
            ADDdiv.innerHTML += "<input type='hidden' name='hdncmbAddWsMethod' id='hdncmbAddWsMethod' value=''/>";
            ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddWsCmbValue' id='hdntxtAddWsCmbValue' value=''/>";
            ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddWsCmbText' id='hdntxtAddWsCmbText' value=''/>";
            ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddWsProject' id='hdntxtAddWsProject' value=''/>";
            ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddWsIntrface' id='hdntxtAddWsIntrface' value=''/>";
            ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddWsRetType' id='hdntxtAddWsRetType' value=''/>";
            ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddWsParams' id='hdntxtAddWsParams' value=''/>";
            ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddWsExps' id='hdntxtAddWsExps' value=''/>";
            ADDdiv.innerHTML += "<input type='hidden' name='hdncmbAddCommonQuery' id='hdncmbAddCommonQuery' value=''/>";
        }
    }
    else
    {
        ADDdiv.innerHTML += "<input type='hidden' name='hdnchkAddSrc' id='hdnchkAddSrc' value=''/>";
        ADDdiv.innerHTML += "<input type='hidden' name='hdnrdoAddDataSrc' id='hdnrdoAddDataSrc' value=''/>";
        ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddSrcStatic' id='hdntxtAddSrcStatic' value=''/>";
        ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddSrcQuery' id='hdntxtAddSrcQuery' value=''/>";
        ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddWsdlUrl' id='hdntxtAddWsdlUrl' value=''/>";
        ADDdiv.innerHTML += "<input type='hidden' name='hdncmbAddWsMethod' id='hdncmbAddWsMethod' value=''/>";
        ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddWsCmbValue' id='hdntxtAddWsCmbValue' value=''/>";
        ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddWsCmbText' id='hdntxtAddWsCmbText' value=''/>";
        ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddWsProject' id='hdntxtAddWsProject' value=''/>";
        ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddWsIntrface' id='hdntxtAddWsIntrface' value=''/>";
        ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddWsRetType' id='hdntxtAddWsRetType' value=''/>";
        ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddWsParams' id='hdntxtAddWsParams' value=''/>";
        ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddWsExps' id='hdntxtAddWsExps' value=''/>";
        ADDdiv.innerHTML += "<input type='hidden' name='hdncmbAddCommonQuery' id='hdncmbAddCommonQuery' value=''/>";
    }
    ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddMaxsize' id='hdntxtAddMaxsize' value='" + document.getElementById("txtAddMaxsize").value + "'/>";
    ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddType' id='hdntxtAddType' value='" + document.getElementById("txtAddType").value + "'/>";
    ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddMaxfiles' id='hdntxtAddMaxfiles' value='" + document.getElementById("txtAddMaxfiles").value + "'/>";
    ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddEleName' id='hdntxtAddEleName' value='" + document.getElementById("txtAddEleName").value + "'/>";
    ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddDispTxt' id='hdntxtAddDispTxt' value='" + document.getElementById("txtAddDispTxt").value + "'/>";
    ADDdiv.innerHTML += "<input type='hidden' name='hdntxtAddOnremoveCall' id='hdntxtAddOnremoveCall' value='" + document.getElementById("txtAddOnremoveCall").value + "'/>";
    ADDdiv.innerHTML += "<input type='hidden' name='hdnchkAddMandatory' id='hdnchkAddMandatory' value='" + document.getElementById("chkAddMandatory").checked + "'/>";

    ADDmaindiv.appendChild(ADDdiv);
    addListDivId++;
    clearAddTabData();
}

function editAddTabRow(rowid)
{
    var editdivid = document.getElementById("addTabListTable").rows[rowid].cells[8].childNodes[0].data;

    var field = document.getElementById(editdivid).childNodes[0].value;
    document.getElementById("cmbAddControl").value = document.getElementById(editdivid).childNodes[1].value;

    //srs properties
    var validation = document.getElementById(editdivid).childNodes[2].value;
    document.getElementById("txtAddRemarks").value = document.getElementById(editdivid).childNodes[3].value;

    //code properties
    document.getElementById("txtAddId").value = document.getElementById(editdivid).childNodes[4].value;
    document.getElementById("txtAddName").value = document.getElementById(editdivid).childNodes[5].value;
    document.getElementById("txtAddStyle").value = document.getElementById(editdivid).childNodes[6].value;
    document.getElementById("txtAddSize").value = document.getElementById(editdivid).childNodes[7].value;
    document.getElementById("txtAddClass").value = document.getElementById(editdivid).childNodes[8].value;
    var options = document.getElementById("cmbAddOnchange").options;
    for (var i = 0; i < options.length; i++)
    {
        if (options[i].value != "-1" && options[i].value == document.getElementById(editdivid).childNodes[4].value)
        {
            options[i].remove();
        }
    }
    document.getElementById("cmbAddOnchange").value = document.getElementById(editdivid).childNodes[9].value;
    if (document.getElementById(editdivid).childNodes[10].value == "true")
    {
        document.getElementById("rbtnAddMultipleTrue").checked = true;
        document.getElementById("rbtnAddMultipleFalse").checked = false;
    }
    else
    {
        document.getElementById("rbtnAddMultipleTrue").checked = false;
        document.getElementById("rbtnAddMultipleFalse").checked = true;
    }
    if (document.getElementById(editdivid).childNodes[11].value == "true")
    {
        document.getElementById("rbtnAddCheckedTrue").checked = true;
        document.getElementById("rbtnAddCheckedFalse").checked = false;
    }
    else
    {
        document.getElementById("rbtnAddCheckedTrue").checked = false;
        document.getElementById("rbtnAddCheckedFalse").checked = true;
    }
    document.getElementById("txtAddMaxLength").value = document.getElementById(editdivid).childNodes[12].value;
    if (document.getElementById(editdivid).childNodes[13].value == "true")
    {
        document.getElementById("rbtnAddReadonlyTrue").checked = true;
        document.getElementById("rbtnAddReadonlyFalse").checked = false;
    }
    else
    {
        document.getElementById("rbtnAddReadonlyTrue").checked = false;
        document.getElementById("rbtnAddReadonlyFalse").checked = true;
    }
    document.getElementById("cmbAddAlign").value = document.getElementById(editdivid).childNodes[14].value;
    document.getElementById("txtAddRows").value = document.getElementById(editdivid).childNodes[15].value;
    document.getElementById("txtAddCols").value = document.getElementById(editdivid).childNodes[16].value;
    document.getElementById("txtAddValue").value = document.getElementById(editdivid).childNodes[17].value;
    document.getElementById("txtAddTabIndex").value = document.getElementById(editdivid).childNodes[18].value;
    document.getElementById("rbtnAddCheckedOneZero").checked = false;
    document.getElementById("rbtnAddCheckedYesNo").checked = false;
    document.getElementById("rbtnAddCheckedYN").checked = false;
    document.getElementById("rbtnAddCheckedTF").checked = false;
    if (document.getElementById(editdivid).childNodes[19].value == "onezero")
    {
        document.getElementById("rbtnAddCheckedOneZero").checked = true;
    }
    else if (document.getElementById(editdivid).childNodes[19].value == "yesno")
    {
        document.getElementById("rbtnAddCheckedYesNo").checked = true;
    }
    else if (document.getElementById(editdivid).childNodes[19].value == "yn")
    {
        document.getElementById("rbtnAddCheckedYN").checked = true;
    }
    else if (document.getElementById(editdivid).childNodes[19].value == "truefalse")
    {
        document.getElementById("rbtnAddCheckedTF").checked = true;
    }
    document.getElementById("txtAddTotalRdo").value = document.getElementById(editdivid).childNodes[20].value;
    document.getElementById("txtAddRdoCap").value = document.getElementById(editdivid).childNodes[21].value;
    document.getElementById("txtAddRdoVal").value = document.getElementById(editdivid).childNodes[22].value;
    document.getElementById("txtAddRdoDefVal").value = document.getElementById(editdivid).childNodes[23].value;
    if (document.getElementById(editdivid).childNodes[24].value == "true")
    {
        document.getElementById("chkShowAdd").checked = true;
    }
    else
    {
        document.getElementById("chkShowAdd").checked = false;
    }
    if (document.getElementById(editdivid).childNodes[25].value == "true")
    {
        document.getElementById("chkShowEdit").checked = true;
    }
    else
    {
        document.getElementById("chkShowEdit").checked = false;
    }
    if (document.getElementById(editdivid).childNodes[26].value == "true")
    {
        document.getElementById("chkShowDel").checked = true;
    }
    else
    {
        document.getElementById("chkShowDel").checked = false;
    }
    document.getElementById("txtAddLabel").value = document.getElementById(editdivid).childNodes[27].value;
    if (document.getElementById(editdivid).childNodes[28].value == "true")
    {
        document.getElementById("chkAddSrc").checked = true;
    }
    else
    {
        document.getElementById("chkAddSrc").checked = false;
    }
    if (document.getElementById(editdivid).childNodes[29].value == "Static")
    {
        document.getElementById("rdoAddSrcStatic").checked = true;
    }
    else if (document.getElementById(editdivid).childNodes[29].value == "Query")
    {
        document.getElementById("rdoAddSrcQuery").checked = true;
    }
    else if (document.getElementById(editdivid).childNodes[29].value == "WebService")
    {
        document.getElementById("rdoAddSrcWS").checked = true;
    }
    else if (document.getElementById(editdivid).childNodes[29].value == "CommonCmb")
    {
        document.getElementById("rdoAddSrcCommonCmb").checked = true;
    }
    else
    {
        document.getElementById("rdoAddSrcStatic").checked = false;
        document.getElementById("rdoAddSrcQuery").checked = false;
        document.getElementById("rdoAddSrcWS").checked = false;
        document.getElementById("rdoAddSrcCommonCmb").checked = false;
    }
    document.getElementById("txtAddSrcStatic").value = document.getElementById(editdivid).childNodes[30].value;
    document.getElementById("txtAddSrcQuery").value = unescape(document.getElementById(editdivid).childNodes[31].value);
    document.getElementById("txtAddWsdlUrl").value = document.getElementById(editdivid).childNodes[32].value;
    document.getElementById("cmbAddWsMethod").innerHTML += "<option value=\"" + document.getElementById(editdivid).childNodes[33].value + "\">" + document.getElementById(editdivid).childNodes[33].value + "</option>";
    document.getElementById("cmbAddWsMethod").value = document.getElementById(editdivid).childNodes[33].value;
    document.getElementById("txtAddWsCmbValue").value = document.getElementById(editdivid).childNodes[34].value;
    document.getElementById("txtAddWsCmbText").value = document.getElementById(editdivid).childNodes[35].value;
    if (document.getElementById("txtAddWsProject") != null)
    {
        document.getElementById("txtAddWsProject").value = document.getElementById(editdivid).childNodes[36].value;
    }
    if (document.getElementById("txtAddWsIntrface") != null)
    {
        document.getElementById("txtAddWsIntrface").value = document.getElementById(editdivid).childNodes[37].value;
    }
    if (document.getElementById("txtAddWsRetType") != null)
    {
        document.getElementById("txtAddWsRetType").value = document.getElementById(editdivid).childNodes[38].value;
    }
    if (document.getElementById("txtAddWsParams") != null)
    {
        document.getElementById("txtAddWsParams").value = document.getElementById(editdivid).childNodes[39].value;
    }
    if (document.getElementById("txtAddWsExps") != null)
    {
        document.getElementById("txtAddWsExps").value = document.getElementById(editdivid).childNodes[40].value;
    }
    document.getElementById("cmbAddCommonQuery").value = document.getElementById(editdivid).childNodes[41].value;
    document.getElementById("txtAddMaxsize").value = document.getElementById(editdivid).childNodes[42].value;
    document.getElementById("txtAddType").value = document.getElementById(editdivid).childNodes[43].value;
    document.getElementById("txtAddMaxfiles").value = document.getElementById(editdivid).childNodes[44].value;
    document.getElementById("txtAddEleName").value = document.getElementById(editdivid).childNodes[45].value;
    document.getElementById("txtAddDispTxt").value = document.getElementById(editdivid).childNodes[46].value;
    document.getElementById("txtAddOnremoveCall").value = document.getElementById(editdivid).childNodes[47].value;
    if (document.getElementById(editdivid).childNodes[48].value == "true")
    {
        document.getElementById("chkAddMandatory").checked = true;
    }
    else
    {
        document.getElementById("chkAddMandatory").checked = false;
    }

    deleteAddTabRow(rowid);
    document.getElementById("cmbAddField").value = "-1";
    document.getElementById("cmbAddField").value = field;
    showAddControlProperties();
    document.getElementById("cmbAddValidation").value = validation;
    if (validation != "-1")
    {
        document.getElementById("trAddMndtry").style.display = "";
    }
}

function checkAddDeletePossible(rowid)
{
    var deletedivid = document.getElementById("addTabListTable").rows[rowid].cells[8].childNodes[0].data;
    var div = document.getElementById(deletedivid);
    if (document.getElementById("addTabListTable").rows[rowid].cells[1].innerHTML == "ComboBox" || document.getElementById("addTabListTable").rows[rowid].cells[1].innerHTML == "TextLikeCombo")
    {
        var id = div.innerHTML;
        id = id.substr(id.indexOf("<input name=\"hdntxtAddId\" id=\"hdntxtAddId\" value=\"") + 50).trim();
        id = id.substr(0, id.indexOf("\" type=\"hidden\"><")).trim();
        if (document.getElementById("hdncmbAddOnchange"))
        {
            var onchanges = document.getElementsByName("hdncmbAddOnchange");
            for (var i = 0; i < onchanges.length; i++)
            {
                if (onchanges[i].value == id)
                {
                    alert("You can not remove this Field. Another ComboBox is dependent on this ComboBox.");
                    return;
                }
            }
        }
    }
    deleteAddTabRow(rowid);
}

function deleteAddTabRow(rowid)
{
    var deletedivid = document.getElementById("addTabListTable").rows[rowid].cells[8].childNodes[0].data;
    var div = document.getElementById(deletedivid);
    if (document.getElementById("addTabListTable").rows[rowid].cells[1].innerHTML == "ComboBox" || document.getElementById("addTabListTable").rows[rowid].cells[1].innerHTML == "TextLikeCombo")
    {
        var id = div.innerHTML;
        id = id.substr(id.indexOf("<input name=\"hdntxtAddId\" id=\"hdntxtAddId\" value=\"") + 50).trim();
        id = id.substr(0, id.indexOf("\" type=\"hidden\"><")).trim();
        var options = document.getElementById("cmbAddOnchange").options;
        for (var i = 0; i < options.length; i++)
        {
            if (options[i].value != "-1" && options[i].value == id)
            {
                options[i].remove();
            }
        }
    }
    div.parentNode.removeChild(div);
    var field = document.getElementById("addTabListTable").rows[rowid].cells[0].innerHTML;
    var sel = document.getElementById("cmbAddField").value;

    var combo = document.getElementById("cmbAddField");
    if (field != "None")
    {
        for (var j = 0; j < combo.options.length; j++)
        {
            if (combo.options[j].value == field)
            {
                combo.options[j].disabled = false;
                break;
            }
        }
    }
    document.getElementById("cmbAddField").value = sel;
    document.getElementById("addTabListTable").deleteRow(rowid);
}

function clearAddTabData()
{
    document.getElementById("cmbAddField").value = '-1';
    document.getElementById("cmbAddControl").value = '-1';
    //srs properties
    removeAllValidations("cmbAddValidation");
    document.getElementById("txtAddRemarks").value = "";
    //code properties
    document.getElementById("txtAddId").value = "";
    document.getElementById("txtAddName").value = "";
    document.getElementById("txtAddStyle").value = "";
    document.getElementById("txtAddSize").value = "";
    document.getElementById("txtAddClass").value = "";
    document.getElementById("cmbAddOnchange").value = '-1';
    document.getElementById("rbtnAddMultipleTrue").checked = false;
    document.getElementById("rbtnAddMultipleFalse").checked = false;
    document.getElementById("rbtnAddCheckedTrue").checked = false;
    document.getElementById("rbtnAddCheckedFalse").checked = false;
    document.getElementById("txtAddMaxLength").value = "";
    document.getElementById("rbtnAddReadonlyTrue").checked = false;
    document.getElementById("rbtnAddReadonlyFalse").checked = false;
    document.getElementById("cmbAddAlign").value = "-1";
    document.getElementById("txtAddRows").value = "";
    document.getElementById("txtAddCols").value = "";
    document.getElementById("txtAddValue").value = "";
    document.getElementById("txtAddTabIndex").value = "";
    document.getElementById("rbtnAddCheckedOneZero").checked = false;
    document.getElementById("rbtnAddCheckedYesNo").checked = false;
    document.getElementById("rbtnAddCheckedYN").checked = false;
    document.getElementById("rbtnAddCheckedTF").checked = false;
    document.getElementById("txtAddTotalRdo").value = "";
    document.getElementById("txtAddRdoCap").value = "";
    document.getElementById("txtAddRdoVal").value = "";
    document.getElementById("txtAddRdoDefVal").value = "";
    document.getElementById("chkShowAdd").checked = false;
    document.getElementById("chkShowEdit").checked = false;
    document.getElementById("chkShowDel").checked = false;
    document.getElementById("txtAddLabel").value = "";

    document.getElementById("chkAddSrc").checked = false;
    document.getElementById("rdoAddSrcStatic").checked = false;
    document.getElementById("rdoAddSrcQuery").checked = false;
    document.getElementById("rdoAddSrcWS").checked = false;
    document.getElementById("rdoAddSrcCommonCmb").checked = false;
    document.getElementById("txtAddSrcStatic").value = "";
    document.getElementById("txtAddSrcQuery").value = "";
    document.getElementById("txtAddWsdlUrl").value = "";
    document.getElementById("cmbAddCommonQuery").value = "-1";
    document.getElementById("cmbAddWsMethod").innerHTML = "<option value=\"-1\">-- Select Method --</option>";
    document.getElementById("txtAddWsCmbValue").value = "";
    document.getElementById("txtAddWsCmbText").value = "";
    document.getElementById("trAddIndepSrcStatic").style.display = "none";
    document.getElementById("trAddIndepSrcQuery").style.display = "none";
    document.getElementById("tbdAddWS").style.display = "none";
    document.getElementById("trAddIndepSrcCommonCmb").style.display = "none";
    document.getElementById("tdAddSrc").style.display = "none";
    document.getElementById("AddQueryResult").innerHTML = "";
    if (document.getElementById("txtAddWsProject") != null)
    {
        document.getElementById("txtAddWsProject").value = "";
    }
    if (document.getElementById("txtAddWsIntrface") != null)
    {
        document.getElementById("txtAddWsIntrface").value = "";
    }
    if (document.getElementById("txtAddWsRetType") != null)
    {
        document.getElementById("txtAddWsRetType").value = "";
    }
    if (document.getElementById("txtAddWsParams") != null)
    {
        document.getElementById("txtAddWsParams").value = "";
    }
    if (document.getElementById("txtAddWsExps") != null)
    {
        document.getElementById("txtAddWsExps").value = "";
    }
    document.getElementById("txtAddMaxsize").value = "";
    document.getElementById("txtAddType").value = "";
    document.getElementById("txtAddMaxfiles").value = "";
    document.getElementById("txtAddEleName").value = "";
    document.getElementById("txtAddDispTxt").value = "";
    document.getElementById("txtAddOnremoveCall").value = "";
    document.getElementById("chkAddMandatory").checked = false;
}

function showEditControlProperties()
{
    document.getElementById("divEditProperties").style.display = "";

    document.getElementById("trEditStyleSizeProp").style.display = "none";
    document.getElementById("trEditClassProp").style.display = "";
    document.getElementById("tdEditOnchange1").style.display = "none";
    document.getElementById("tdEditOnchange2").style.display = "none";
    document.getElementById("trEditTxt_LabelProp").style.display = "none";
    document.getElementById("trEditAlignProp").style.display = "none";
    document.getElementById("trEditCmbProp").style.display = "none";
    document.getElementById("trEditIndepSource").style.display = "none";
    document.getElementById("tdEditSrc").style.display = "none";
    document.getElementById("trEditIndepSrcStatic").style.display = "none";
    document.getElementById("trEditIndepSrcQuery").style.display = "none";
    document.getElementById("trEditIndepSrcCommonCmb").style.display = "none";
    document.getElementById("tbdEditWS").style.display = "none";
    document.getElementById("trEditTextAreaProp").style.display = "none";
    document.getElementById("trEditChkProp").style.display = "none";
    document.getElementById("trEditRdoProp").style.display = "none";
    document.getElementById("trEditChkValue").style.display = "none";
    document.getElementById("EditTotalRdo1").style.display = "none";
    document.getElementById("EditTotalRdo2").style.display = "none";
    document.getElementById("trEditRdoCaptions").style.display = "none";
    document.getElementById("trEditRdoValues").style.display = "none";
    document.getElementById("trEditRdoDefaultValue").style.display = "none";
    document.getElementById("EditSize1").style.display = "";
    document.getElementById("EditSize2").style.display = "";
    document.getElementById("trEditMndtry").style.display = "none";
    document.getElementById("cmbEditValidation").value = "-1";
    document.getElementById("trEditSizeType").style.display = "none";
    document.getElementById("trEditFilesEle").style.display = "none";
    document.getElementById("trEditDispTxt").style.display = "none";

    if (document.getElementById("cmbEditControl").value == "-1")
    {
        document.getElementById("trEditStyleSizeProp").style.display = "";
        removeAllValidations("cmbEditValidation");
    }
    else if (document.getElementById("cmbEditControl").value == "TextBox")
    {
        document.getElementById("trEditStyleSizeProp").style.display = "";
        document.getElementById("trEditTxt_LabelProp").style.display = "";
        document.getElementById("trEditAlignProp").style.display = "";
        showTextValidations("cmbEditValidation");
    }
    else if (document.getElementById("cmbEditControl").value == "ComboBox" || document.getElementById("cmbEditControl").value == "TextLikeCombo")
    {
        document.getElementById("tdEditOnchange1").style.display = "";
        document.getElementById("tdEditOnchange2").style.display = "";
        document.getElementById("trEditStyleSizeProp").style.display = "";
        if (document.getElementById("cmbEditControl").value == "ComboBox")
        {
            document.getElementById("trEditCmbProp").style.display = "";
        }
        document.getElementById("trEditIndepSource").style.display = "";
        if (document.getElementById("chkEditSrc").checked)
        {
            document.getElementById("tdEditSrc").style.display = "";
            if (document.getElementById("rdoEditSrcStatic").checked)
            {
                document.getElementById("trEditIndepSrcStatic").style.display = "";
            }
            else if (document.getElementById("rdoEditSrcQuery").checked)
            {
                document.getElementById("trEditIndepSrcQuery").style.display = "";
            }
            else if (document.getElementById("rdoEditSrcWS").checked)
            {
                document.getElementById("tbdEditWS").style.display = "";
            }
            else if (document.getElementById("rdoEditSrcCommonCmb").checked)
            {
                document.getElementById("trEditIndepSrcCommonCmb").style.display = "";
            }
        }
        showComboValidations("cmbEditValidation");
    }
    else if (document.getElementById("cmbEditControl").value == "TextArea")
    {
        document.getElementById("trEditStyleSizeProp").style.display = "";
        document.getElementById("trEditTextAreaProp").style.display = "";
        document.getElementById("EditSize1").style.display = "none";
        document.getElementById("EditSize2").style.display = "none";
        showTextValidations("cmbEditValidation");
    }
    else if (document.getElementById("cmbEditControl").value == "CheckBox")
    {
        document.getElementById("trEditStyleSizeProp").style.display = "";
        document.getElementById("trEditChkProp").style.display = "";
        document.getElementById("trEditChkValue").style.display = "";
        showCheckBoxValidations("cmbEditValidation");
    }
    else if (document.getElementById("cmbEditControl").value == "Radio")
    {
        document.getElementById("trEditStyleSizeProp").style.display = "";
        document.getElementById("trEditRdoProp").style.display = "";
        document.getElementById("EditTotalRdo1").style.display = "";
        document.getElementById("EditTotalRdo2").style.display = "";
        document.getElementById("trEditRdoCaptions").style.display = "";
        document.getElementById("trEditRdoValues").style.display = "";
        document.getElementById("trEditRdoDefaultValue").style.display = "";
        showRadioValidations("cmbEditValidation");
    }
    else if (document.getElementById("cmbEditControl").value == "DatePicker")
    {
        document.getElementById("trEditClassProp").style.display = "none";
        showDateValidations("cmbEditValidation");
    }
    else if (document.getElementById("cmbEditControl").value == "File")
    {
        document.getElementById("trEditClassProp").style.display = "none";
        removeAllValidations("cmbEditValidation");
    }
    else if (document.getElementById("cmbEditControl").value == "Password")
    {
        document.getElementById("trEditStyleSizeProp").style.display = "";
        document.getElementById("trEditTxt_LabelProp").style.display = "";
        document.getElementById("trEditAlignProp").style.display = "";
        showPasswordValidations("cmbEditValidation");
    }
    else if (document.getElementById("cmbEditControl").value == "FileBox")
    {
        document.getElementById("trEditClassProp").style.display = "none";
        document.getElementById("EditSize1").style.display = "none";
        document.getElementById("EditSize2").style.display = "none";
        document.getElementById("trEditSizeType").style.display = "";
        document.getElementById("trEditFilesEle").style.display = "";
        document.getElementById("trEditDispTxt").style.display = "";
        removeAllValidations("cmbEditValidation");
    }
}

function validateEditTabData()
{
    if (document.getElementById("cmbEditField").value == "-1")
    {
        alert("Please Select Field");
        document.getElementById("cmbEditField").focus();
        return;
    }
    else if (document.getElementById("cmbEditControl").value == "-1")
    {
        alert("Please Select Control");
        document.getElementById("cmbEditControl").focus();
        return;
    }
    else if (document.getElementById("txtEditTabIndex").value == "")
    {
        alert("Please Enter Tab Index");
        document.getElementById("txtEditTabIndex").focus();
        return;
    }
    else
    {
        if (validateTabIndex("txtEditTabIndex"))
        {
            if (document.getElementById("cmbEditControl").value == "CheckBox")
            {
                if (document.getElementById("cmbEditField").value != "None")
                {
                    if (validateValueFormat("rbtnEditCheckedOneZero", "rbtnEditCheckedYesNo", "rbtnEditCheckedYN", "rbtnEditCheckedTF", "chkEditCode", "EditCodeProperty"))
                    {
                        validateBeforeEdit();
                    }
                }
                else
                {
                    validateBeforeEdit();
                }
            }
            else if (document.getElementById("cmbEditControl").value == "Radio")
            {
                if (validateNoOfRadio("txtEditTotalRdo", "txtEditRdoCap", "txtEditRdoVal", "chkEditCode", "EditCodeProperty"))
                {
                    if (document.getElementById("txtEditRdoDefVal").value != "")
                    {
                        var defVal = document.getElementById("txtEditRdoDefVal").value;
                        if (defVal.match(/^[0-9]+$/))
                        {
                            if (parseInt(defVal, 10) >= 1 && parseInt(defVal, 10) <= parseInt(document.getElementById("txtEditTotalRdo").value, 10))
                            {
                                validateBeforeEdit();
                            }
                            else
                            {
                                alert("Please Enter Number Between 1 And " + parseInt(document.getElementById("txtEditTotalRdo").value, 10));
                                document.getElementById("txtEditRdoDefVal").focus();
                                return;
                            }
                        }
                        else
                        {
                            alert("Please Enter Number For Default Selection Of Radio");
                            document.getElementById("txtEditRdoDefVal").focus();
                            return;
                        }
                    }
                    else
                    {
                        validateBeforeEdit();
                    }
                }
            }
            else if (document.getElementById("cmbEditControl").value == "TextArea")
            {
                if (validateRowCol("txtEditRows", "txtEditCols", "chkEditCode", "EditCodeProperty"))
                {
                    validateBeforeEdit();
                }
            }
            else if (document.getElementById("cmbEditControl").value == "FileBox")
            {
                if (document.getElementById("txtEditMaxsize").value != "")
                {
                    if (!document.getElementById("txtEditMaxsize").value.match(/^[0-9]+$/) || parseInt(document.getElementById("txtEditMaxsize").value, 10) == 0)
                    {
                        alert("Please Enter Positive Digits For Maxsize");
                        document.getElementById("chkEditCode").checked = true;
                        document.getElementById("EditCodeProperty").style.display = "";
                        document.getElementById("txtEditMaxsize").focus();
                        return;
                    }
                    else
                    {
                        validateBeforeEdit();
                    }
                }
                else
                {
                    validateBeforeEdit();
                }
            }
            else if (document.getElementById("cmbEditControl").value == "ComboBox" || document.getElementById("cmbEditControl").value == "TextLikeCombo")
            {
                if (document.getElementById("cmbEditOnchange").value != "-1")
                {
                    if (document.getElementById("txtEditSrcQuery").value == "")
                    {
                        document.getElementById("chkEditSrc").checked = true;
                        document.getElementById("rdoEditSrcQuery").checked = true;
                        showSrc('Edit');
                        alert("Please Enter Query To Fill Combo");
                        return;
                    }
                    else
                    {
                        if (document.getElementById("txtEditSrcQuery").value.indexOf("|VARIABLE|") <= 0)
                        {
                            alert("You must specify where to match master combo's value in the Query by |VARIABLE| Keyword");
                            return;
                        }
                        else
                        {
                            validateBeforeEdit();
                        }
                    }
                }
                else
                {
                    validateBeforeEdit();
                }
            }
            else
            {
                validateBeforeEdit();
            }
        }
    }
}

function validateBeforeEdit()
{
    var table = document.getElementById("editTabListTable");
    var rowCount = table.rows.length;

    var tabIdx = document.getElementById("txtEditTabIndex").value;
    for (var i = 2; i < rowCount; i++)
    {
        var rowtab = table.rows[i].cells[2].innerHTML;
        if (parseInt(tabIdx, 10) == rowtab)
        {
            alert("Duplicate Tab Index");
            document.getElementById("txtEditTabIndex").focus();
            return;
        }
    }

    if (validateCntrlIDName("Edit"))
    {
        if (checkControlID(document.getElementById("txtEditId").value))
        {
            alert("You Cannot Enter Duplicate Control ID");
            if (!document.getElementById("chkEditCode").checked)
            {
                document.getElementById("chkEditCode").checked = true;
                document.getElementById("EditCodeProperty").style.display = "";
            }
            document.getElementById("txtEditId").focus();
            return;
        }
        else if (checkElementName(document.getElementById("txtEditId").value))
        {
            alert("Control ID Cannot be same as Element Name");
            if (!document.getElementById("chkEditCode").checked)
            {
                document.getElementById("chkEditCode").checked = true;
                document.getElementById("EditCodeProperty").style.display = "";
            }
            document.getElementById("txtEditId").focus();
            return;
        }
        else if (checkControlName(document.getElementById("txtEditName").value))
        {
            alert("You Cannot Enter Duplicate Control Name");
            if (!document.getElementById("chkEditCode").checked)
            {
                document.getElementById("chkEditCode").checked = true;
                document.getElementById("EditCodeProperty").style.display = "";
            }
            document.getElementById("txtEditName").focus();
            return;
        }
    }
    else
    {
        return;
    }

    if (document.getElementById("cmbEditControl").value == "FileBox")
    {
        if (document.getElementById("txtEditType").value == "")
        {
            alert("Please Enter Type");
            if (!document.getElementById("chkEditCode").checked)
            {
                document.getElementById("chkEditCode").checked = true;
                document.getElementById("EditCodeProperty").style.display = "";
            }
            document.getElementById("txtEditType").focus();
            return;
        }
        else if (!checkFileTypeFormat("Edit"))
        {
            alert("Please Enter Type In Valid Format");
            if (!document.getElementById("chkEditCode").checked)
            {
                document.getElementById("chkEditCode").checked = true;
                document.getElementById("EditCodeProperty").style.display = "";
            }
            document.getElementById("txtEditType").focus();
            return;
        }
        else if (!checkRestrictedFileType("Edit"))
        {
            return;
        }
        else if (document.getElementById("txtEditEleName").value == "")
        {
            alert("Please Enter Element Name");
            if (!document.getElementById("chkEditCode").checked)
            {
                document.getElementById("chkEditCode").checked = true;
                document.getElementById("EditCodeProperty").style.display = "";
            }
            document.getElementById("txtEditEleName").focus();
            return;
        }
    }
    else if (document.getElementById("cmbEditControl").value == "ComboBox" || document.getElementById("cmbEditControl").value == "TextLikeCombo")
    {
        if (document.getElementById("chkEditSrc").checked)
        {
            if (document.getElementById("rdoEditSrcStatic").checked)
            {
                if (document.getElementById("txtEditSrcStatic").value == "")
                {
                    alert("Please Enter Data To Fill Combo");
                    return;
                }
                else if (document.getElementById("EditStaticResult").innerHTML != "")
                {
                    return;
                }
            }
            else if (document.getElementById("rdoEditSrcQuery").checked)
            {
                if (document.getElementById("txtEditSrcQuery").value == "")
                {
                    alert("Please Enter Query To Fill Combo");
                    return;
                }
                else if (document.getElementById("EditQueryResult").innerHTML != "")
                {
                    return;
                }
            }
            else if (document.getElementById("rdoEditSrcWS").checked)
            {
                if (document.getElementById("txtEditWsdlUrl").value == "")
                {
                    alert("Please Enter wsdl URL");
                    return;
                }
                if (document.getElementById("cmbEditWsMethod").value == "-1")
                {
                    alert("Please Select Method to Fill ComboBox");
                    document.getElementById("cmbEditWsMethod").focus();
                    return;
                }
                if (document.getElementById("txtEditWsRetType") == null || document.getElementById("txtEditWsParams") == null)
                {
                    alert("Please Enter Valid Values");
                    return;
                }
                document.getElementById("txtEditWsCmbValue").value = document.getElementById("txtEditWsCmbValue").value.replace(/\s+|\s+/g, '');
                document.getElementById("txtEditWsCmbText").value = document.getElementById("txtEditWsCmbText").value.replace(/\s+|\s+/g, '');
                if (document.getElementById("txtEditWsCmbValue").value == "")
                {
                    alert("Please Enter Field For Value In Combo Option");
                    document.getElementById("txtEditWsCmbValue").focus();
                    return;
                }
                else if (!document.getElementById("txtEditWsCmbValue").value.match(/^[A-Za-z_]+$/) || document.getElementById("txtEditWsCmbValue").value.match(/^[_]+$/))
                {
                    alert("Please Enter Valid Field For Value In Combo Option");
                    document.getElementById("txtEditWsCmbValue").focus();
                    return;
                }
                if (document.getElementById("txtEditWsCmbText").value == "")
                {
                    alert("Please Enter Field For Text In Combo Option");
                    document.getElementById("txtEditWsCmbText").focus();
                    return;
                }
                else if (!document.getElementById("txtEditWsCmbText").value.match(/^[A-Za-z_]+$/) || document.getElementById("txtEditWsCmbText").value.match(/^[_]+$/))
                {
                    alert("Please Enter Valid Field For Text In Combo Option");
                    document.getElementById("txtEditWsCmbText").focus();
                    return;
                }
            }
            else if (document.getElementById("rdoEditSrcCommonCmb").checked)
            {
                if (document.getElementById("cmbEditCommonQuery").value == "-1")
                {
                    alert("Please Select any one option to Fill ComboBox");
                    document.getElementById("cmbEditCommonQuery").focus();
                    return;
                }
            }
            else
            {
                alert("Please Enter Data Source");
                return;
            }
        }
    }

    if (document.getElementById("cmbEditControl").value == "FileBox")
    {
        var eleName = document.getElementById("txtEditEleName").value;
        eleName = eleName.toString().toLowerCase();
        eleName = eleName.split(' ').join('')
        document.getElementById("txtEditEleName").value = eleName;

        if (checkElementName(eleName))
        {
            alert("You Cannot Enter Duplicate Element Name");
            if (!document.getElementById("chkEditCode").checked)
            {
                document.getElementById("chkEditCode").checked = true;
                document.getElementById("EditCodeProperty").style.display = "";
            }
            document.getElementById("txtEditEleName").focus();
            return;
        }
        else if (checkControlID(eleName))
        {
            alert("Element Name Cannot be same as Control Id");
            if (!document.getElementById("chkEditCode").checked)
            {
                document.getElementById("chkEditCode").checked = true;
                document.getElementById("EditCodeProperty").style.display = "";
            }
            document.getElementById("txtEditEleName").focus();
            return;
        }
    }
    addNewEditTabRow();
}

var editListDivId = 1;
function addNewEditTabRow()
{
    document.getElementById("editTabList").style.display = "";
    var table = document.getElementById("editTabListTable");
    var rowCount = table.rows.length;
    var row = table.insertRow(rowCount);

    var newCell0 = row.insertCell(0);
    var newCell1 = row.insertCell(1);
    var newCell2 = row.insertCell(2);
    var newCell3 = row.insertCell(3);
    var newCell4 = row.insertCell(4);
    var newCell5 = row.insertCell(5);
    newCell5.style.display = "none";

    var field = document.getElementById("cmbEditField").value;
    var combo = document.getElementById("cmbEditField");

    if (field != "None")
    {
        for (var i = 0; i < combo.options.length; i++)
        {
            if (combo.options[i].value == field)
            {
                combo.options[i].disabled = true;
                break;
            }
        }
    }

    newCell0.innerHTML = field;
    newCell1.innerHTML = document.getElementById("cmbEditControl").value;
    var tabIdx = document.getElementById("txtEditTabIndex").value;
    newCell2.innerHTML = parseInt(tabIdx, 10);
    newCell3.innerHTML = "<a onclick='javascript: editEditTabRow(this.parentNode.parentNode.rowIndex)'><img style=\"width:20px\" align =\"middle\" src=\"images/edit.gif\" alt=\"Edit\"></a>";
    newCell4.innerHTML = "<a onclick='javascript: checkEditDeletePossible(this.parentNode.parentNode.rowIndex)'><img style=\"width:20px\" align='center' src=\"images/delete.gif\" alt=\"Delete\"></a>";
    newCell5.innerHTML = "divEdit" + editListDivId;

    var EDITmaindiv = document.getElementById("hiddenDivEditTab");
    var EDITdiv = document.createElement("div");
    EDITdiv.id = "divEdit" + editListDivId;

    EDITdiv.innerHTML = "<input type='hidden' name='hdnEditField' id='hdnEditField' value='" + field + "'/>";
    EDITdiv.innerHTML += "<input type='hidden' name='hdnEditControl' id='hdnEditControl' value='" + document.getElementById("cmbEditControl").value + "'/>";
    //srs properties
    EDITdiv.innerHTML += "<input type='hidden' name='hdncmbEditValidation' id='hdncmbEditValidation' value='" + document.getElementById("cmbEditValidation").value + "'/>";
    EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditRemarks' id='hdntxtEditRemarks' value='" + document.getElementById("txtEditRemarks").value + "'/>"
    //code properties
    EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditId' id='hdntxtEditId' value='" + document.getElementById("txtEditId").value + "'/>";
    EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditName' id='hdntxtEditName' value='" + document.getElementById("txtEditName").value + "'/>";
    EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditStyle' id='hdntxtEditStyle' value='" + document.getElementById("txtEditStyle").value + "'/>";
    EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditSize' id='hdntxtEditSize' value='" + document.getElementById("txtEditSize").value + "'/>";
    EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditClass' id='hdntxtEditClass' value='" + document.getElementById("txtEditClass").value + "'/>";
    EDITdiv.innerHTML += "<input type='hidden' name='hdncmbEditOnchange' id='hdncmbEditOnchange' value='" + document.getElementById("cmbEditOnchange").value + "'/>";
    if (document.getElementById("cmbEditControl").value == "ComboBox" || document.getElementById("cmbEditControl").value == "TextLikeCombo")
    {
        document.getElementById("cmbEditOnchange").innerHTML += "<option value=\"" + document.getElementById("txtEditId").value + "\">" + document.getElementById("txtEditId").value + "</option>";
    }
    EDITdiv.innerHTML += "<input type='hidden' name='hdnrbtnEditMultiple' id='hdnrbtnEditMultiple' value='" + document.getElementById("rbtnEditMultipleTrue").checked + "'/>";
    EDITdiv.innerHTML += "<input type='hidden' name='hdnrbtnEditChecked' id='hdnrbtnEditChecked' value='" + document.getElementById("rbtnEditCheckedTrue").checked + "'/>";
    EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditMaxLength' id='hdntxtEditMaxLength' value='" + document.getElementById("txtEditMaxLength").value + "'/>";
    EDITdiv.innerHTML += "<input type='hidden' name='hdnrbtnEditReadonly' id='hdnrbtnEditReadonly' value='" + document.getElementById("rbtnEditReadonlyTrue").checked + "'/>";
    EDITdiv.innerHTML += "<input type='hidden' name='hdncmbEditAlign' id='hdncmbEditAlign' value='" + document.getElementById("cmbEditAlign").value + "'/>";
    EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditRows' id='hdntxtEditRows' value='" + document.getElementById("txtEditRows").value + "'/>";
    EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditCols' id='hdntxtEditCols' value='" + document.getElementById("txtEditCols").value + "'/>";
    EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditValue' id='hdntxtEditValue' value='" + document.getElementById("txtEditValue").value + "'/>";
    EDITdiv.innerHTML += "<input type='hidden' name='hdnEditTabIndex' id='hdnEditTabIndex' value='" + parseInt(tabIdx, 10) + "'/>";

    if (document.getElementById("rbtnEditCheckedOneZero").checked)
    {
        EDITdiv.innerHTML += "<input type='hidden' name='hdnrbtnEditValueFormat' id='hdnrbtnEditValueFormat' value='onezero'/>";
    }
    else if (document.getElementById("rbtnEditCheckedYesNo").checked)
    {
        EDITdiv.innerHTML += "<input type='hidden' name='hdnrbtnEditValueFormat' id='hdnrbtnEditValueFormat' value='yesno'/>";
    }
    else if (document.getElementById("rbtnEditCheckedYN").checked)
    {
        EDITdiv.innerHTML += "<input type='hidden' name='hdnrbtnEditValueFormat' id='hdnrbtnEditValueFormat' value='yn'/>";
    }
    else if (document.getElementById("rbtnEditCheckedTF").checked)
    {
        EDITdiv.innerHTML += "<input type='hidden' name='hdnrbtnEditValueFormat' id='hdnrbtnEditValueFormat' value='truefalse'/>";
    }
    else
    {
        EDITdiv.innerHTML += "<input type='hidden' name='hdnrbtnEditValueFormat' id='hdnrbtnEditValueFormat' value='NA'/>";
    }
    EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditTotalRdo' id='hdntxtEditTotalRdo' value='" + document.getElementById("txtEditTotalRdo").value + "'/>";
    EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditRdoCap' id='hdntxtEditRdoCap' value='" + document.getElementById("txtEditRdoCap").value + "'/>";
    EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditRdoVal' id='hdntxtEditRdoVal' value='" + document.getElementById("txtEditRdoVal").value + "'/>";
    EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditRdoDefVal' id='hdntxtEditRdoDefVal' value='" + document.getElementById("txtEditRdoDefVal").value + "'/>";
    EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditLabel' id='hdntxtEditLabel' value='" + document.getElementById("txtEditLabel").value + "'/>";

    if (document.getElementById("cmbEditControl").value == "ComboBox" || document.getElementById("cmbEditControl").value == "TextLikeCombo")
    {
        EDITdiv.innerHTML += "<input type='hidden' name='hdnchkEditSrc' id='hdnchkEditSrc' value='" + document.getElementById("chkEditSrc").checked + "'/>";
        if (document.getElementById("chkEditSrc").checked)
        {
            if (document.getElementById("rdoEditSrcStatic").checked)
            {
                EDITdiv.innerHTML += "<input type='hidden' name='hdnrdoEditDataSrc' id='hdnrdoEditDataSrc' value='Static'/>";
                EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditSrcStatic' id='hdntxtEditSrcStatic' value='" + document.getElementById("txtEditSrcStatic").value + "'/>";
                EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditSrcQuery' id='hdntxtEditSrcQuery' value=''/>";
                EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditWsdlUrl' id='hdntxtEditWsdlUrl' value=''/>";
                EDITdiv.innerHTML += "<input type='hidden' name='hdncmbEditWsMethod' id='hdncmbEditWsMethod' value=''/>";
                EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditWsCmbValue' id='hdntxtEditWsCmbValue' value=''/>";
                EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditWsCmbText' id='hdntxtEditWsCmbText' value=''/>";
                EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditWsProject' id='hdntxtEditWsProject' value=''/>";
                EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditWsIntrface' id='hdntxtEditWsIntrface' value=''/>";
                EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditWsRetType' id='hdntxtEditWsRetType' value=''/>";
                EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditWsParams' id='hdntxtEditWsParams' value=''/>";
                EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditWsExps' id='hdntxtEditWsExps' value=''/>";
                EDITdiv.innerHTML += "<input type='hidden' name='hdncmbEditCommonQuery' id='hdncmbEditCommonQuery' value=''/>";
            }
            else if (document.getElementById("rdoEditSrcQuery").checked)
            {
                EDITdiv.innerHTML += "<input type='hidden' name='hdnrdoEditDataSrc' id='hdnrdoEditDataSrc' value='Query'/>";
                EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditSrcStatic' id='hdntxtEditSrcStatic' value=''/>";
                EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditSrcQuery' id='hdntxtEditSrcQuery' value='" + escape(document.getElementById("txtEditSrcQuery").value) + "'/>";
                EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditWsdlUrl' id='hdntxtEditWsdlUrl' value=''/>";
                EDITdiv.innerHTML += "<input type='hidden' name='hdncmbEditWsMethod' id='hdncmbEditWsMethod' value=''/>";
                EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditWsCmbValue' id='hdntxtEditWsCmbValue' value=''/>";
                EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditWsCmbText' id='hdntxtEditWsCmbText' value=''/>";
                EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditWsProject' id='hdntxtEditWsProject' value=''/>";
                EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditWsIntrface' id='hdntxtEditWsIntrface' value=''/>";
                EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditWsRetType' id='hdntxtEditWsRetType' value=''/>";
                EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditWsParams' id='hdntxtEditWsParams' value=''/>";
                EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditWsExps' id='hdntxtEditWsExps' value=''/>";
                EDITdiv.innerHTML += "<input type='hidden' name='hdncmbEditCommonQuery' id='hdncmbEditCommonQuery' value=''/>";
            }
            else if (document.getElementById("rdoEditSrcWS").checked)
            {
                EDITdiv.innerHTML += "<input type='hidden' name='hdnrdoEditDataSrc' id='hdnrdoEditDataSrc' value='WebService'/>";
                EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditSrcStatic' id='hdntxtEditSrcStatic' value=''/>";
                EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditSrcQuery' id='hdntxtEditSrcQuery' value=''/>";
                EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditWsdlUrl' id='hdntxtEditWsdlUrl' value='" + document.getElementById("txtEditWsdlUrl").value + "'/>";
                EDITdiv.innerHTML += "<input type='hidden' name='hdncmbEditWsMethod' id='hdncmbEditWsMethod' value='" + document.getElementById("cmbEditWsMethod").value + "'/>";
                EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditWsCmbValue' id='hdntxtEditWsCmbValue' value='" + document.getElementById("txtEditWsCmbValue").value + "'/>";
                EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditWsCmbText' id='hdntxtEditWsCmbText' value='" + document.getElementById("txtEditWsCmbText").value + "'/>";
                EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditWsProject' id='hdntxtEditWsProject' value='" + document.getElementById("txtEditWsProject").value + "'/>";
                EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditWsIntrface' id='hdntxtEditWsIntrface' value='" + document.getElementById("txtEditWsIntrface").value + "'/>";
                EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditWsRetType' id='hdntxtEditWsRetType' value='" + document.getElementById("txtEditWsRetType").value + "'/>";
                EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditWsParams' id='hdntxtEditWsParams' value='" + document.getElementById("txtEditWsParams").value + "'/>";
                EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditWsExps' id='hdntxtEditWsExps' value='" + document.getElementById("txtEditWsExps").value + "'/>";
                EDITdiv.innerHTML += "<input type='hidden' name='hdncmbEditCommonQuery' id='hdncmbEditCommonQuery' value=''/>";
            }
            else if (document.getElementById("rdoEditSrcCommonCmb").checked)
            {
                EDITdiv.innerHTML += "<input type='hidden' name='hdnrdoEditDataSrc' id='hdnrdoEditDataSrc' value='CommonCmb'/>";
                EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditSrcStatic' id='hdntxtEditSrcStatic' value=''/>";
                EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditSrcQuery' id='hdntxtEditSrcQuery' value=''/>";
                EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditWsdlUrl' id='hdntxtEditWsdlUrl' value=''/>";
                EDITdiv.innerHTML += "<input type='hidden' name='hdncmbEditWsMethod' id='hdncmbEditWsMethod' value=''/>";
                EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditWsCmbValue' id='hdntxtEditWsCmbValue' value=''/>";
                EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditWsCmbText' id='hdntxtEditWsCmbText' value=''/>";
                EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditWsProject' id='hdntxtEditWsProject' value=''/>";
                EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditWsIntrface' id='hdntxtEditWsIntrface' value=''/>";
                EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditWsRetType' id='hdntxtEditWsRetType' value=''/>";
                EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditWsParams' id='hdntxtEditWsParams' value=''/>";
                EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditWsExps' id='hdntxtEditWsExps' value=''/>";
                EDITdiv.innerHTML += "<input type='hidden' name='hdncmbEditCommonQuery' id='hdncmbEditCommonQuery' value='" + document.getElementById("cmbEditCommonQuery").value + "'/>";
            }
        }
        else
        {
            EDITdiv.innerHTML += "<input type='hidden' name='hdnrdoEditDataSrc' id='hdnrdoEditDataSrc' value=''/>";
            EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditSrcStatic' id='hdntxtEditSrcStatic' value=''/>";
            EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditSrcQuery' id='hdntxtEditSrcQuery' value=''/>";
            EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditWsdlUrl' id='hdntxtEditWsdlUrl' value=''/>";
            EDITdiv.innerHTML += "<input type='hidden' name='hdncmbEditWsMethod' id='hdncmbEditWsMethod' value=''/>";
            EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditWsCmbValue' id='hdntxtEditWsCmbValue' value=''/>";
            EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditWsCmbText' id='hdntxtEditWsCmbText' value=''/>";
            EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditWsProject' id='hdntxtEditWsProject' value=''/>";
            EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditWsIntrface' id='hdntxtEditWsIntrface' value=''/>";
            EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditWsRetType' id='hdntxtEditWsRetType' value=''/>";
            EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditWsParams' id='hdntxtEditWsParams' value=''/>";
            EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditWsExps' id='hdntxtEditWsExps' value=''/>";
            EDITdiv.innerHTML += "<input type='hidden' name='hdncmbEditCommonQuery' id='hdncmbEditCommonQuery' value=''/>";
        }
    }
    else
    {
        EDITdiv.innerHTML += "<input type='hidden' name='hdnchkEditSrc' id='hdnchkEditSrc' value=''/>";
        EDITdiv.innerHTML += "<input type='hidden' name='hdnrdoEditDataSrc' id='hdnrdoEditDataSrc' value=''/>";
        EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditSrcStatic' id='hdntxtEditSrcStatic' value=''/>";
        EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditSrcQuery' id='hdntxtEditSrcQuery' value=''/>";
        EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditWsdlUrl' id='hdntxtEditWsdlUrl' value=''/>";
        EDITdiv.innerHTML += "<input type='hidden' name='hdncmbEditWsMethod' id='hdncmbEditWsMethod' value=''/>";
        EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditWsCmbValue' id='hdntxtEditWsCmbValue' value=''/>";
        EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditWsCmbText' id='hdntxtEditWsCmbText' value=''/>";
        EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditWsProject' id='hdntxtEditWsProject' value=''/>";
        EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditWsIntrface' id='hdntxtEditWsIntrface' value=''/>";
        EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditWsRetType' id='hdntxtEditWsRetType' value=''/>";
        EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditWsParams' id='hdntxtEditWsParams' value=''/>";
        EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditWsExps' id='hdntxtEditWsExps' value=''/>";
        EDITdiv.innerHTML += "<input type='hidden' name='hdncmbEditCommonQuery' id='hdncmbEditCommonQuery' value=''/>";
    }
    EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditMaxsize' id='hdntxtEditMaxsize' value='" + document.getElementById("txtEditMaxsize").value + "'/>";
    EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditType' id='hdntxtEditType' value='" + document.getElementById("txtEditType").value + "'/>";
    EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditMaxfiles' id='hdntxtEditMaxfiles' value='" + document.getElementById("txtEditMaxfiles").value + "'/>";
    EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditEleName' id='hdntxtEditEleName' value='" + document.getElementById("txtEditEleName").value + "'/>";
    EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditDispTxt' id='hdntxtEditDispTxt' value='" + document.getElementById("txtEditDispTxt").value + "'/>";
    EDITdiv.innerHTML += "<input type='hidden' name='hdntxtEditOnremoveCall' id='hdntxtEditOnremoveCall' value='" + document.getElementById("txtEditOnremoveCall").value + "'/>";
    EDITdiv.innerHTML += "<input type='hidden' name='hdnchkEditMandatory' id='hdnchkEditMandatory' value='" + document.getElementById("chkEditMandatory").checked + "'/>";

    EDITmaindiv.appendChild(EDITdiv);
    editListDivId++;
    clearEditTabData();
}

function editEditTabRow(rowid)
{
    var editdivid = document.getElementById("editTabListTable").rows[rowid].cells[5].childNodes[0].data;

    var field = document.getElementById(editdivid).childNodes[0].value;
    document.getElementById("cmbEditControl").value = document.getElementById(editdivid).childNodes[1].value;
    //srs properties
    var validation = document.getElementById(editdivid).childNodes[2].value;
    document.getElementById("txtEditRemarks").value = document.getElementById(editdivid).childNodes[3].value;

    //code properties
    document.getElementById("txtEditId").value = document.getElementById(editdivid).childNodes[4].value;
    document.getElementById("txtEditName").value = document.getElementById(editdivid).childNodes[5].value;
    document.getElementById("txtEditStyle").value = document.getElementById(editdivid).childNodes[6].value;
    document.getElementById("txtEditSize").value = document.getElementById(editdivid).childNodes[7].value;
    document.getElementById("txtEditClass").value = document.getElementById(editdivid).childNodes[8].value;
    var options = document.getElementById("cmbEditOnchange").options;
    for (var i = 0; i < options.length; i++)
    {
        if (options[i].value != "-1" && options[i].value == document.getElementById(editdivid).childNodes[4].value)
        {
            options[i].remove();
        }
    }
    document.getElementById("cmbEditOnchange").value = document.getElementById(editdivid).childNodes[9].value;
    if (document.getElementById(editdivid).childNodes[10].value == "true")
    {
        document.getElementById("rbtnEditMultipleTrue").checked = true;
        document.getElementById("rbtnEditMultipleFalse").checked = false;
    }
    else
    {
        document.getElementById("rbtnEditMultipleTrue").checked = false;
        document.getElementById("rbtnEditMultipleFalse").checked = true;
    }
    if (document.getElementById(editdivid).childNodes[11].value == "true")
    {
        document.getElementById("rbtnEditCheckedTrue").checked = true;
        document.getElementById("rbtnEditCheckedFalse").checked = false;
    }
    else
    {
        document.getElementById("rbtnEditCheckedTrue").checked = false;
        document.getElementById("rbtnEditCheckedFalse").checked = true;
    }
    document.getElementById("txtEditMaxLength").value = document.getElementById(editdivid).childNodes[12].value;
    if (document.getElementById(editdivid).childNodes[13].value == "true")
    {
        document.getElementById("rbtnEditReadonlyTrue").checked = true;
        document.getElementById("rbtnEditReadonlyFalse").checked = false;
    }
    else
    {
        document.getElementById("rbtnEditReadonlyTrue").checked = false;
        document.getElementById("rbtnEditReadonlyFalse").checked = true;
    }
    document.getElementById("cmbEditAlign").value = document.getElementById(editdivid).childNodes[14].value;
    document.getElementById("txtEditRows").value = document.getElementById(editdivid).childNodes[15].value;
    document.getElementById("txtEditCols").value = document.getElementById(editdivid).childNodes[16].value;
    document.getElementById("txtEditValue").value = document.getElementById(editdivid).childNodes[17].value;
    document.getElementById("txtEditTabIndex").value = document.getElementById(editdivid).childNodes[18].value;
    document.getElementById("rbtnEditCheckedOneZero").checked = false;
    document.getElementById("rbtnEditCheckedYesNo").checked = false;
    document.getElementById("rbtnEditCheckedYN").checked = false;
    document.getElementById("rbtnEditCheckedTF").checked = false;
    if (document.getElementById(editdivid).childNodes[19].value == "onezero")
    {
        document.getElementById("rbtnEditCheckedOneZero").checked = true;
    }
    else if (document.getElementById(editdivid).childNodes[19].value == "yesno")
    {
        document.getElementById("rbtnEditCheckedYesNo").checked = true;
    }
    else if (document.getElementById(editdivid).childNodes[19].value == "yn")
    {
        document.getElementById("rbtnEditCheckedYN").checked = true;
    }
    else if (document.getElementById(editdivid).childNodes[19].value == "truefalse")
    {
        document.getElementById("rbtnEditCheckedTF").checked = true;
    }
    document.getElementById("txtEditTotalRdo").value = document.getElementById(editdivid).childNodes[20].value;
    document.getElementById("txtEditRdoCap").value = document.getElementById(editdivid).childNodes[21].value;
    document.getElementById("txtEditRdoVal").value = document.getElementById(editdivid).childNodes[22].value;
    document.getElementById("txtEditRdoDefVal").value = document.getElementById(editdivid).childNodes[23].value;
    document.getElementById("txtEditLabel").value = document.getElementById(editdivid).childNodes[24].value;
    if (document.getElementById(editdivid).childNodes[25].value == "true")
    {
        document.getElementById("chkEditSrc").checked = true;
    }
    else
    {
        document.getElementById("chkEditSrc").checked = false;
    }
    if (document.getElementById(editdivid).childNodes[26].value == "Static")
    {
        document.getElementById("rdoEditSrcStatic").checked = true;
    }
    else if (document.getElementById(editdivid).childNodes[26].value == "Query")
    {
        document.getElementById("rdoEditSrcQuery").checked = true;
    }
    else if (document.getElementById(editdivid).childNodes[26].value == "WebService")
    {
        document.getElementById("rdoEditSrcWS").checked = true;
    }
    else if (document.getElementById(editdivid).childNodes[26].value == "CommonCmb")
    {
        document.getElementById("rdoEditSrcCommonCmb").checked = true;
    }
    else
    {
        document.getElementById("rdoEditSrcStatic").checked = false;
        document.getElementById("rdoEditSrcQuery").checked = false;
        document.getElementById("rdoEditSrcWS").checked = false;
        document.getElementById("rdoEditSrcCommonCmb").checked = false;
    }
    document.getElementById("txtEditSrcStatic").value = document.getElementById(editdivid).childNodes[27].value;
    document.getElementById("txtEditSrcQuery").value = unescape(document.getElementById(editdivid).childNodes[28].value);
    document.getElementById("txtEditWsdlUrl").value = document.getElementById(editdivid).childNodes[29].value;
    document.getElementById("cmbEditWsMethod").innerHTML += "<option value=\"" + document.getElementById(editdivid).childNodes[30].value + "\">" + document.getElementById(editdivid).childNodes[30].value + "</option>";
    document.getElementById("cmbEditWsMethod").value = document.getElementById(editdivid).childNodes[30].value;
    document.getElementById("txtEditWsCmbValue").value = document.getElementById(editdivid).childNodes[31].value;
    document.getElementById("txtEditWsCmbText").value = document.getElementById(editdivid).childNodes[32].value;
    if (document.getElementById("txtEditWsProject") != null)
    {
        document.getElementById("txtEditWsProject").value = document.getElementById(editdivid).childNodes[33].value;
    }
    if (document.getElementById("txtEditWsIntrface") != null)
    {
        document.getElementById("txtEditWsIntrface").value = document.getElementById(editdivid).childNodes[34].value;
    }
    if (document.getElementById("txtEditWsRetType") != null)
    {
        document.getElementById("txtEditWsRetType").value = document.getElementById(editdivid).childNodes[35].value;
    }
    if (document.getElementById("txtEditWsParams") != null)
    {
        document.getElementById("txtEditWsParams").value = document.getElementById(editdivid).childNodes[36].value;
    }
    if (document.getElementById("txtEditWsExps") != null)
    {
        document.getElementById("txtEditWsExps").value = document.getElementById(editdivid).childNodes[37].value;
    }
    document.getElementById("cmbEditCommonQuery").value = document.getElementById(editdivid).childNodes[38].value;
    document.getElementById("txtEditMaxsize").value = document.getElementById(editdivid).childNodes[39].value;
    document.getElementById("txtEditType").value = document.getElementById(editdivid).childNodes[40].value;
    document.getElementById("txtEditMaxfiles").value = document.getElementById(editdivid).childNodes[41].value;
    document.getElementById("txtEditEleName").value = document.getElementById(editdivid).childNodes[42].value;
    document.getElementById("txtEditDispTxt").value = document.getElementById(editdivid).childNodes[43].value;
    document.getElementById("txtEditOnremoveCall").value = document.getElementById(editdivid).childNodes[44].value;

    if (document.getElementById(editdivid).childNodes[45].value == "true")
    {
        document.getElementById("chkEditMandatory").checked = true;
    }
    else
    {
        document.getElementById("chkEditMandatory").checked = false;
    }

    deleteEditTabRow(rowid);
    document.getElementById("cmbEditField").value = "-1";
    document.getElementById("cmbEditField").value = field;
    showEditControlProperties();
    document.getElementById("cmbEditValidation").value = validation;
    if (validation != "-1")
    {
        document.getElementById("trEditMndtry").style.display = "";
    }
}

function checkEditDeletePossible(rowid)
{
    var deletedivid = document.getElementById("editTabListTable").rows[rowid].cells[5].childNodes[0].data;
    var div = document.getElementById(deletedivid);
    if (document.getElementById("editTabListTable").rows[rowid].cells[1].innerHTML == "ComboBox" || document.getElementById("editTabListTable").rows[rowid].cells[1].innerHTML == "TextLikeCombo")
    {
        var id = div.innerHTML;
        id = id.substr(id.indexOf("<input name=\"hdntxtEditId\" id=\"hdntxtEditId\" value=\"") + 52).trim();
        id = id.substr(0, id.indexOf("\" type=\"hidden\"><")).trim();
        if (document.getElementById("hdncmbEditOnchange"))
        {
            var onchanges = document.getElementsByName("hdncmbEditOnchange");
            for (var i = 0; i < onchanges.length; i++)
            {
                if (onchanges[i].value == id)
                {
                    alert("You can not remove this Field. Another ComboBox is dependent on this ComboBox.");
                    return;
                }
            }
        }
    }
    deleteEditTabRow(rowid);
}

function deleteEditTabRow(rowid)
{
    var deletedivid = document.getElementById("editTabListTable").rows[rowid].cells[5].childNodes[0].data;
    var div = document.getElementById(deletedivid);
    if (document.getElementById("editTabListTable").rows[rowid].cells[1].innerHTML == "ComboBox" || document.getElementById("editTabListTable").rows[rowid].cells[1].innerHTML == "TextLikeCombo")
    {
        var id = div.innerHTML;
        id = id.substr(id.indexOf("<input name=\"hdntxtEditId\" id=\"hdntxtEditId\" value=\"") + 52).trim();
        id = id.substr(0, id.indexOf("\" type=\"hidden\"><")).trim();
        var options = document.getElementById("cmbEditOnchange").options;
        for (var i = 0; i < options.length; i++)
        {
            if (options[i].value != "-1" && options[i].value == id)
            {
                options[i].remove();
            }
        }
    }
    div.parentNode.removeChild(div);
    var field = document.getElementById("editTabListTable").rows[rowid].cells[0].innerHTML;
    var sel = document.getElementById("cmbEditField").value;

    var combo = document.getElementById("cmbEditField");
    if (field != "None")
    {
        for (var j = 0; j < combo.options.length; j++)
        {
            if (combo.options[j].value == field)
            {
                combo.options[j].disabled = false;
                break;
            }
        }
    }
    document.getElementById("cmbEditField").value = sel;
    document.getElementById("editTabListTable").deleteRow(rowid);
}

function clearEditTabData()
{
    document.getElementById("cmbEditField").value = '-1';
    document.getElementById("cmbEditControl").value = '-1';
    //srs properties
    removeAllValidations("cmbEditValidation");
    document.getElementById("txtEditRemarks").value = "";
    //code properties
    document.getElementById("txtEditId").value = "";
    document.getElementById("txtEditName").value = "";
    document.getElementById("txtEditStyle").value = "";
    document.getElementById("txtEditSize").value = "";
    document.getElementById("txtEditClass").value = "";
    document.getElementById("cmbEditOnchange").value = '-1';
    document.getElementById("rbtnEditMultipleTrue").checked = false;
    document.getElementById("rbtnEditMultipleFalse").checked = false;
    document.getElementById("rbtnEditCheckedTrue").checked = false;
    document.getElementById("rbtnEditCheckedFalse").checked = false;
    document.getElementById("txtEditMaxLength").value = "";
    document.getElementById("rbtnEditReadonlyTrue").checked = false;
    document.getElementById("rbtnEditReadonlyFalse").checked = false;
    document.getElementById("cmbEditAlign").value = "-1";
    document.getElementById("txtEditRows").value = "";
    document.getElementById("txtEditCols").value = "";
    document.getElementById("txtEditValue").value = "";
    document.getElementById("txtEditTabIndex").value = "";
    document.getElementById("rbtnEditCheckedOneZero").checked = false;
    document.getElementById("rbtnEditCheckedYesNo").checked = false;
    document.getElementById("rbtnEditCheckedYN").checked = false;
    document.getElementById("rbtnEditCheckedTF").checked = false;
    document.getElementById("txtEditTotalRdo").value = "";
    document.getElementById("txtEditRdoCap").value = "";
    document.getElementById("txtEditRdoVal").value = "";
    document.getElementById("txtEditRdoDefVal").value = "";
    document.getElementById("txtEditLabel").value = "";

    document.getElementById("chkEditSrc").checked = false;
    document.getElementById("rdoEditSrcStatic").checked = false;
    document.getElementById("rdoEditSrcQuery").checked = false;
    document.getElementById("rdoEditSrcWS").checked = false;
    document.getElementById("rdoEditSrcCommonCmb").checked = false;
    document.getElementById("txtEditSrcStatic").value = "";
    document.getElementById("txtEditSrcQuery").value = "";
    document.getElementById("txtEditWsdlUrl").value = "";
    document.getElementById("cmbEditCommonQuery").value = "-1";
    document.getElementById("cmbEditWsMethod").innerHTML = "<option value=\"-1\">-- Select Method --</option>";
    document.getElementById("txtEditWsCmbValue").value = "";
    document.getElementById("txtEditWsCmbText").value = "";
    document.getElementById("trEditIndepSrcStatic").style.display = "none";
    document.getElementById("trEditIndepSrcQuery").style.display = "none";
    document.getElementById("trEditIndepSrcCommonCmb").style.display = "none";
    document.getElementById("tbdEditWS").style.display = "none";
    document.getElementById("tdEditSrc").style.display = "none";
    document.getElementById("EditQueryResult").innerHTML = "";
    if (document.getElementById("txtEditWsProject") != null)
    {
        document.getElementById("txtEditWsProject").value = "";
    }
    if (document.getElementById("txtEditWsIntrface") != null)
    {
        document.getElementById("txtEditWsIntrface").value = "";
    }
    if (document.getElementById("txtEditWsRetType") != null)
    {
        document.getElementById("txtEditWsRetType").value = "";
    }
    if (document.getElementById("txtEditWsParams") != null)
    {
        document.getElementById("txtEditWsParams").value = "";
    }
    if (document.getElementById("txtEditWsExps") != null)
    {
        document.getElementById("txtEditWsExps").value = "";
    }
    document.getElementById("txtEditMaxsize").value = "";
    document.getElementById("txtEditType").value = "";
    document.getElementById("txtEditMaxfiles").value = "";
    document.getElementById("txtEditEleName").value = "";
    document.getElementById("txtEditDispTxt").value = "";
    document.getElementById("txtEditOnremoveCall").value = "";
    document.getElementById("chkEditMandatory").checked = false;
}

function showDeleteControlProperties()
{
    document.getElementById("divDeleteProperties").style.display = "";

    document.getElementById("trDeleteStyleSizeProp").style.display = "none";
    document.getElementById("trDeleteClassProp").style.display = "";
    document.getElementById("tdDeleteOnchange1").style.display = "none";
    document.getElementById("tdDeleteOnchange2").style.display = "none";
    document.getElementById("trDeleteTxt_LabelProp").style.display = "none";
    document.getElementById("trDeleteAlignProp").style.display = "none";
    document.getElementById("trDeleteCmbProp").style.display = "none";
    document.getElementById("trDeleteIndepSource").style.display = "none";
    document.getElementById("trDeleteIndepSrcStatic").style.display = "none";
    document.getElementById("trDeleteIndepSrcQuery").style.display = "none";
    document.getElementById("trDeleteIndepSrcCommonCmb").style.display = "none";
    document.getElementById("tbdDeleteWS").style.display = "none";
    document.getElementById("trDeleteTextAreaProp").style.display = "none";
    document.getElementById("trDeleteChkProp").style.display = "none";
    document.getElementById("trDeleteRdoProp").style.display = "none";
    document.getElementById("trDeleteChkValue").style.display = "none";
    document.getElementById("DeleteTotalRdo1").style.display = "none";
    document.getElementById("DeleteTotalRdo2").style.display = "none";
    document.getElementById("trDeleteRdoCaptions").style.display = "none";
    document.getElementById("trDeleteRdoValues").style.display = "none";
    document.getElementById("trDeleteRdoDefaultValue").style.display = "none";
    document.getElementById("DeleteSize1").style.display = "";
    document.getElementById("DeleteSize2").style.display = "";
    document.getElementById("trDeleteMndtry").style.display = "none";
    document.getElementById("cmbDeleteValidation").value = "-1";
    document.getElementById("trDeleteSizeType").style.display = "none";
    document.getElementById("trDeleteFilesEle").style.display = "none";
    document.getElementById("trDeleteDispTxt").style.display = "none";

    if (document.getElementById("cmbDeleteControl").value == "-1")
    {
        document.getElementById("trDeleteStyleSizeProp").style.display = "";
        removeAllValidations("cmbDeleteValidation");
    }
    else if (document.getElementById("cmbDeleteControl").value == "TextBox")
    {
        document.getElementById("trDeleteStyleSizeProp").style.display = "";
        document.getElementById("trDeleteTxt_LabelProp").style.display = "";
        document.getElementById("trDeleteAlignProp").style.display = "";
        showTextValidations("cmbDeleteValidation");
    }
    else if (document.getElementById("cmbDeleteControl").value == "ComboBox" || document.getElementById("cmbDeleteControl").value == "TextLikeCombo")
    {
        document.getElementById("tdDeleteOnchange1").style.display = "";
        document.getElementById("tdDeleteOnchange2").style.display = "";
        document.getElementById("trDeleteStyleSizeProp").style.display = "";
        if (document.getElementById("cmbDeleteControl").value == "ComboBox")
        {
            document.getElementById("trDeleteCmbProp").style.display = "";
        }
        document.getElementById("trDeleteIndepSource").style.display = "";
        if (document.getElementById("chkDeleteSrc").checked)
        {
            document.getElementById("tdDeleteSrc").style.display = "";
            if (document.getElementById("rdoDeleteSrcStatic").checked)

            {
                document.getElementById("trDeleteIndepSrcStatic").style.display = "";
            }
            else if (document.getElementById("rdoDeleteSrcQuery").checked)
            {
                document.getElementById("trDeleteIndepSrcQuery").style.display = "";
            }
            else if (document.getElementById("rdoDeleteSrcWS").checked)
            {
                document.getElementById("tbdDeleteWS").style.display = "";
            }
            else if (document.getElementById("rdoDeleteSrcCommonCmb").checked)
            {
                document.getElementById("trDeleteIndepSrcCommonCmb").style.display = "";
            }
        }
        showComboValidations("cmbDeleteValidation");
    }
    else if (document.getElementById("cmbDeleteControl").value == "TextArea")
    {
        document.getElementById("trDeleteStyleSizeProp").style.display = "";
        document.getElementById("trDeleteTextAreaProp").style.display = "";
        document.getElementById("DeleteSize1").style.display = "none";
        document.getElementById("DeleteSize2").style.display = "none";
        showTextValidations("cmbDeleteValidation");
    }
    else if (document.getElementById("cmbDeleteControl").value == "CheckBox")
    {
        document.getElementById("trDeleteStyleSizeProp").style.display = "";
        document.getElementById("trDeleteChkProp").style.display = "";
        document.getElementById("trDeleteChkValue").style.display = "";
        showCheckBoxValidations("cmbDeleteValidation");
    }
    else if (document.getElementById("cmbDeleteControl").value == "Radio")
    {
        document.getElementById("trDeleteStyleSizeProp").style.display = "";
        document.getElementById("trDeleteRdoProp").style.display = "";
        document.getElementById("DeleteTotalRdo1").style.display = "";
        document.getElementById("DeleteTotalRdo2").style.display = "";
        document.getElementById("trDeleteRdoCaptions").style.display = "";
        document.getElementById("trDeleteRdoValues").style.display = "";
        document.getElementById("trDeleteRdoDefaultValue").style.display = "";
        showRadioValidations("cmbDeleteValidation");
    }
    else if (document.getElementById("cmbDeleteControl").value == "DatePicker")
    {
        document.getElementById("trDeleteClassProp").style.display = "none";
        showDateValidations("cmbDeleteValidation");
    }
    else if (document.getElementById("cmbDeleteControl").value == "File")
    {
        document.getElementById("trDeleteClassProp").style.display = "none";
        removeAllValidations("cmbDeleteValidation");
    }
    else if (document.getElementById("cmbDeleteControl").value == "Password")
    {
        document.getElementById("trDeleteStyleSizeProp").style.display = "";
        document.getElementById("trDeleteTxt_LabelProp").style.display = "";
        document.getElementById("trDeleteAlignProp").style.display = "";
        showPasswordValidations("cmbDeleteValidation");
    }
    else if (document.getElementById("cmbDeleteControl").value == "FileBox")
    {
        document.getElementById("trDeleteClassProp").style.display = "none";
        document.getElementById("DeleteSize1").style.display = "none";
        document.getElementById("DeleteSize2").style.display = "none";
        document.getElementById("trDeleteSizeType").style.display = "";
        document.getElementById("trDeleteFilesEle").style.display = "";
        document.getElementById("trDeleteDispTxt").style.display = "";
        removeAllValidations("cmbDeleteValidation");
    }
}

function validateDeleteTabData()
{
    if (document.getElementById("cmbDeleteField").value == "-1")
    {
        alert("Please Select Field");
        document.getElementById("cmbDeleteField").focus();
        return;
    }
    else if (document.getElementById("cmbDeleteControl").value == "-1")
    {
        alert("Please Select Control");
        document.getElementById("cmbDeleteControl").focus();
        return;
    }
    else if (document.getElementById("txtDeleteTabIndex").value == "")
    {
        alert("Please Enter Tab Index");
        document.getElementById("txtDeleteTabIndex").focus();
        return;
    }
    else
    {
        if (validateTabIndex("txtDeleteTabIndex"))
        {
            if (document.getElementById("cmbDeleteControl").value == "CheckBox")
            {
                if (document.getElementById("cmbDeleteField").value != "None")
                {
                    if (validateValueFormat("rbtnDeleteCheckedOneZero", "rbtnDeleteCheckedYesNo", "rbtnDeleteCheckedYN", "rbtnDeleteCheckedTF", "chkDeleteCode", "DeleteCodeProperty"))
                    {
                        validateBeforeDelete();
                    }
                }
                else
                {
                    validateBeforeDelete();
                }
            }
            else if (document.getElementById("cmbDeleteControl").value == "Radio")
            {
                if (validateNoOfRadio("txtDeleteTotalRdo", "txtDeleteRdoCap", "txtDeleteRdoVal", "chkDeleteCode", "DeleteCodeProperty"))
                {
                    if (document.getElementById("txtDeleteRdoDefVal").value != "")
                    {
                        var defVal = document.getElementById("txtDeleteRdoDefVal").value;
                        if (defVal.match(/^[0-9]+$/))
                        {
                            if (parseInt(defVal, 10) >= 1 && parseInt(defVal, 10) <= parseInt(document.getElementById("txtDeleteTotalRdo").value, 10))
                            {
                                validateBeforeDelete();
                            }
                            else
                            {
                                alert("Please Enter Number Between 1 And " + parseInt(document.getElementById("txtDeleteTotalRdo").value, 10));
                                document.getElementById("txtDeleteRdoDefVal").focus();
                                return;
                            }
                        }
                        else
                        {
                            alert("Please Enter Number For Default Selection Of Radio");
                            document.getElementById("txtDeleteRdoDefVal").focus();
                            return;
                        }
                    }
                    else
                    {
                        validateBeforeDelete();
                    }
                }
            }
            else if (document.getElementById("cmbDeleteControl").value == "TextArea")
            {
                if (validateRowCol("txtDeleteRows", "txtDeleteCols", "chkDeleteCode", "DeleteCodeProperty"))
                {
                    validateBeforeDelete();
                }
            }
            else if (document.getElementById("cmbDeleteControl").value == "FileBox")
            {
                if (document.getElementById("txtDeleteMaxsize").value != "")
                {
                    if (!document.getElementById("txtDeleteMaxsize").value.match(/^[0-9]+$/) || parseInt(document.getElementById("txtDeleteMaxsize").value, 10) == 0)
                    {
                        alert("Please Enter Positive Digits For Maxsize");
                        document.getElementById("chkDeleteCode").checked = true;
                        document.getElementById("DeleteCodeProperty").style.display = "";
                        document.getElementById("txtDeleteMaxsize").focus();
                        return;
                    }
                    else
                    {
                        validateBeforeDelete();
                    }
                }
                else
                {
                    validateBeforeDelete();
                }
            }
            else if (document.getElementById("cmbDeleteControl").value == "ComboBox" || document.getElementById("cmbDeleteControl").value == "TextLikeCombo")
            {
                if (document.getElementById("cmbDeleteOnchange").value != "-1")
                {
                    if (document.getElementById("txtDeleteSrcQuery").value == "")
                    {
                        document.getElementById("chkDeleteSrc").checked = true;
                        document.getElementById("rdoDeleteSrcQuery").checked = true;
                        showSrc('Delete');
                        alert("Please Enter Query To Fill Combo");
                        return;
                    }
                    else
                    {
                        if (document.getElementById("txtDeleteSrcQuery").value.indexOf("|VARIABLE|") <= 0)
                        {
                            alert("You must specify where to match master combo's value in the Query by |VARIABLE| Keyword");
                            return;
                        }
                        else
                        {
                            validateBeforeDelete();
                        }
                    }
                }
                else
                {
                    validateBeforeDelete();
                }
            }
            else
            {
                validateBeforeDelete();
            }
        }
    }
}

function validateBeforeDelete()
{
    var table = document.getElementById("deleteTabListTable");
    var rowCount = table.rows.length;

    var tabIdx = document.getElementById("txtDeleteTabIndex").value;
    for (var i = 2; i < rowCount; i++)
    {
        var rowtab = table.rows[i].cells[2].innerHTML;
        if (parseInt(tabIdx, 10) == rowtab)
        {
            alert("Duplicate Tab Index");
            document.getElementById("txtDeleteTabIndex").focus();
            return;
        }
    }

    if (validateCntrlIDName("Delete"))
    {
        if (checkControlID(document.getElementById("txtDeleteId").value))
        {
            alert("You Cannot Enter Duplicate Control ID");
            if (!document.getElementById("chkDeleteCode").checked)
            {
                document.getElementById("chkDeleteCode").checked = true;
                document.getElementById("DeleteCodeProperty").style.display = "";
            }
            document.getElementById("txtDeleteId").focus();
            return;
        }
        else if (checkElementName(document.getElementById("txtDeleteId").value))
        {
            alert("Control ID Cannot be same as Element Name");
            if (!document.getElementById("chkDeleteCode").checked)
            {
                document.getElementById("chkDeleteCode").checked = true;
                document.getElementById("DeleteCodeProperty").style.display = "";
            }
            document.getElementById("txtDeleteId").focus();
            return;
        }
        else if (checkControlName(document.getElementById("txtDeleteName").value))
        {
            alert("You Cannot Enter Duplicate Control Name");
            if (!document.getElementById("chkDeleteCode").checked)
            {
                document.getElementById("chkDeleteCode").checked = true;
                document.getElementById("DeleteCodeProperty").style.display = "";
            }
            document.getElementById("txtDeleteName").focus();
            return;
        }
    }
    else
    {
        return;
    }

    if (document.getElementById("cmbDeleteControl").value == "FileBox")
    {
        if (document.getElementById("txtDeleteType").value == "")
        {
            alert("Please Enter Type");
            if (!document.getElementById("chkDeleteCode").checked)
            {
                document.getElementById("chkDeleteCode").checked = true;
                document.getElementById("DeleteCodeProperty").style.display = "";
            }
            document.getElementById("txtDeleteType").focus();
            return;
        }
        else if (!checkFileTypeFormat("Delete"))
        {
            alert("Please Enter Type In Valid Format");
            if (!document.getElementById("chkDeleteCode").checked)
            {
                document.getElementById("chkDeleteCode").checked = true;
                document.getElementById("DeleteCodeProperty").style.display = "";
            }
            document.getElementById("txtDeleteType").focus();
            return;
        }
        else if (!checkRestrictedFileType("Delete"))
        {
            return;
        }
        else if (document.getElementById("txtDeleteEleName").value == "")
        {
            alert("Please Enter Element Name");
            if (!document.getElementById("chkDeleteCode").checked)
            {
                document.getElementById("chkDeleteCode").checked = true;
                document.getElementById("DeleteCodeProperty").style.display = "";
            }
            document.getElementById("txtDeleteEleName").focus();
            return;
        }
    }
    else if (document.getElementById("cmbDeleteControl").value == "ComboBox" || document.getElementById("cmbDeleteControl").value == "TextLikeCombo")
    {
        if (document.getElementById("chkDeleteSrc").checked)
        {
            if (document.getElementById("rdoDeleteSrcStatic").checked)
            {
                if (document.getElementById("txtDeleteSrcStatic").value == "")
                {
                    alert("Please Enter Data To Fill Combo");
                    return;
                }
                else if (document.getElementById("DeleteStaticResult").innerHTML != "")
                {
                    return;
                }
            }
            else if (document.getElementById("rdoDeleteSrcQuery").checked)
            {
                if (document.getElementById("txtDeleteSrcQuery").value == "")
                {
                    alert("Please Enter Query To Fill Combo");
                    return;
                }
                else if (document.getElementById("DeleteQueryResult").innerHTML != "")
                {
                    return;
                }
            }
            else if (document.getElementById("rdoDeleteSrcWS").checked)
            {
                if (document.getElementById("txtDeleteWsdlUrl").value == "")
                {
                    alert("Please Enter wsdl URL");
                    return;
                }
                if (document.getElementById("cmbDeleteWsMethod").value == "-1")
                {
                    alert("Please Select Method to Fill ComboBox");
                    document.getElementById("cmbDeleteWsMethod").focus();
                    return;
                }
                if (document.getElementById("txtDeleteWsRetType") == null || document.getElementById("txtDeleteWsParams") == null)
                {
                    alert("Please Enter Valid Values");
                    return;
                }
                document.getElementById("txtDeleteWsCmbValue").value = document.getElementById("txtDeleteWsCmbValue").value.replace(/\s+|\s+/g, '');
                document.getElementById("txtDeleteWsCmbText").value = document.getElementById("txtDeleteWsCmbText").value.replace(/\s+|\s+/g, '');
                if (document.getElementById("txtDeleteWsCmbValue").value == "")
                {
                    alert("Please Enter Field For Value In Combo Option");
                    document.getElementById("txtDeleteWsCmbValue").focus();
                    return;
                }
                else if (!document.getElementById("txtDeleteWsCmbValue").value.match(/^[A-Za-z_]+$/) || document.getElementById("txtDeleteWsCmbValue").value.match(/^[_]+$/))
                {
                    alert("Please Enter Valid Field For Value In Combo Option");
                    document.getElementById("txtDeleteWsCmbValue").focus();
                    return;
                }
                if (document.getElementById("txtDeleteWsCmbText").value == "")
                {
                    alert("Please Enter Field For Text In Combo Option");
                    document.getElementById("txtDeleteWsCmbText").focus();
                    return;
                }
                else if (!document.getElementById("txtDeleteWsCmbText").value.match(/^[A-Za-z_]+$/) || document.getElementById("txtDeleteWsCmbText").value.match(/^[_]+$/))
                {
                    alert("Please Enter Valid Field For Text In Combo Option");
                    document.getElementById("txtDeleteWsCmbText").focus();
                    return;
                }
            }
            else if (document.getElementById("rdoDeleteSrcCommonCmb").checked)
            {
                if (document.getElementById("cmbDeleteCommonQuery").value == "-1")
                {
                    alert("Please Select any one option to Fill ComboBox");
                    document.getElementById("cmbDeleteCommonQuery").focus();
                    return;
                }
            }
            else
            {
                alert("Please Enter Data Source");
                return;
            }
        }
    }

    if (document.getElementById("cmbDeleteControl").value == "FileBox")
    {
        var eleName = document.getElementById("txtDeleteEleName").value;
        eleName = eleName.toString().toLowerCase();
        eleName = eleName.split(' ').join('')
        document.getElementById("txtDeleteEleName").value = eleName;

        if (checkElementName(eleName))
        {
            alert("You Cannot Enter Duplicate Element Name");
            if (!document.getElementById("chkDeleteCode").checked)
            {
                document.getElementById("chkDeleteCode").checked = true;
                document.getElementById("DeleteCodeProperty").style.display = "";
            }
            document.getElementById("txtDeleteEleName").focus();
            return;
        }
        else if (checkControlID(eleName))
        {
            alert("Element Name Cannot be same as Control Id");
            if (!document.getElementById("chkDeleteCode").checked)
            {
                document.getElementById("chkDeleteCode").checked = true;
                document.getElementById("DeleteCodeProperty").style.display = "";
            }
            document.getElementById("txtDeleteEleName").focus();
            return;
        }
    }
    addNewDeleteTabRow();
}

var deleteListDivId = 1;
function addNewDeleteTabRow()
{
    document.getElementById("deleteTabList").style.display = "";
    var table = document.getElementById("deleteTabListTable");
    var rowCount = table.rows.length;
    var row = table.insertRow(rowCount);

    var newCell0 = row.insertCell(0);
    var newCell1 = row.insertCell(1);
    var newCell2 = row.insertCell(2);
    var newCell3 = row.insertCell(3);
    var newCell4 = row.insertCell(4);
    var newCell5 = row.insertCell(5);
    newCell5.style.display = "none";

    var field = document.getElementById("cmbDeleteField").value;
    var combo = document.getElementById("cmbDeleteField");

    if (field != "None")
    {
        for (var i = 0; i < combo.options.length; i++)
        {
            if (combo.options[i].value == field)
            {
                combo.options[i].disabled = true;
                break;
            }
        }
    }

    newCell0.innerHTML = field;
    newCell1.innerHTML = document.getElementById("cmbDeleteControl").value;
    var tabIdx = document.getElementById("txtDeleteTabIndex").value;
    newCell2.innerHTML = parseInt(tabIdx, 10);
    newCell3.innerHTML = "<a onclick='javascript: editDeleteTabRow(this.parentNode.parentNode.rowIndex)'><img style=\"width:20px\" align =\"middle\" src=\"images/edit.gif\" alt=\"Edit\"></a>";
    newCell4.innerHTML = "<a onclick='javascript: checkDeleteDeletePossible(this.parentNode.parentNode.rowIndex)'><img style=\"width:20px\" align='center' src=\"images/delete.gif\" alt=\"Delete\"></a>";
    newCell5.innerHTML = "divDel" + deleteListDivId;

    var DELETEmaindiv = document.getElementById("hiddenDivDeleteTab");
    var DELETEdiv = document.createElement("div");
    DELETEdiv.id = "divDel" + deleteListDivId;

    DELETEdiv.innerHTML = "<input type='hidden' name='hdnDeleteField' id='hdnDeleteField' value='" + field + "'/>";
    DELETEdiv.innerHTML += "<input type='hidden' name='hdnDeleteControl' id='hdnDeleteControl' value='" + document.getElementById("cmbDeleteControl").value + "'/>";
    //srs properties
    DELETEdiv.innerHTML += "<input type='hidden' name='hdncmbDeleteValidation' id='hdncmbDeleteValidation' value='" + document.getElementById("cmbDeleteValidation").value + "'/>";
    DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteRemarks' id='hdntxtDeleteRemarks' value='" + document.getElementById("txtDeleteRemarks").value + "'/>";
    //code properties
    DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteId' id='hdntxtDeleteId' value='" + document.getElementById("txtDeleteId").value + "'/>";
    DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteName' id='hdntxtDeleteName' value='" + document.getElementById("txtDeleteName").value + "'/>";
    DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteStyle' id='hdntxtDeleteStyle' value='" + document.getElementById("txtDeleteStyle").value + "'/>";
    DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteSize' id='hdntxtDeleteSize' value='" + document.getElementById("txtDeleteSize").value + "'/>";
    DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteClass' id='hdntxtDeleteClass' value='" + document.getElementById("txtDeleteClass").value + "'/>";
    DELETEdiv.innerHTML += "<input type='hidden' name='hdncmbDeleteOnchange' id='hdncmbDeleteOnchange' value='" + document.getElementById("cmbDeleteOnchange").value + "'/>";
    if (document.getElementById("cmbDeleteControl").value == "ComboBox" || document.getElementById("cmbDeleteControl").value == "TextLikeCombo")
    {
        document.getElementById("cmbDeleteOnchange").innerHTML += "<option value=\"" + document.getElementById("txtDeleteId").value + "\">" + document.getElementById("txtDeleteId").value + "</option>";
    }
    DELETEdiv.innerHTML += "<input type='hidden' name='hdnrbtnDeleteMultiple' id='hdnrbtnDeleteMultiple' value='" + document.getElementById("rbtnDeleteMultipleTrue").checked + "'/>";
    DELETEdiv.innerHTML += "<input type='hidden' name='hdnrbtnDeleteChecked' id='hdnrbtnDeleteChecked' value='" + document.getElementById("rbtnDeleteCheckedTrue").checked + "'/>";
    DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteMaxLength' id='hdntxtDeleteMaxLength' value='" + document.getElementById("txtDeleteMaxLength").value + "'/>";
    DELETEdiv.innerHTML += "<input type='hidden' name='hdnrbtnDeleteReadonly' id='hdnrbtnDeleteReadonly' value='" + document.getElementById("rbtnDeleteReadonlyTrue").checked + "'/>";
    DELETEdiv.innerHTML += "<input type='hidden' name='hdncmbDeleteAlign' id='hdncmbDeleteAlign' value='" + document.getElementById("cmbDeleteAlign").value + "'/>";
    DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteRows' id='hdntxtDeleteRows' value='" + document.getElementById("txtDeleteRows").value + "'/>";
    DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteCols' id='hdntxtDeleteCols' value='" + document.getElementById("txtDeleteCols").value + "'/>";
    DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteValue' id='hdntxtDeleteValue' value='" + document.getElementById("txtDeleteValue").value + "'/>";
    DELETEdiv.innerHTML += "<input type='hidden' name='hdnDeleteTabIndex' id='hdnDeleteTabIndex' value='" + parseInt(tabIdx, 10) + "'/>";

    if (document.getElementById("rbtnDeleteCheckedOneZero").checked)
    {
        DELETEdiv.innerHTML += "<input type='hidden' name='hdnrbtnDeleteValueFormat' id='hdnrbtnDeleteValueFormat' value='onezero'/>";
    }
    else if (document.getElementById("rbtnDeleteCheckedYesNo").checked)
    {
        DELETEdiv.innerHTML += "<input type='hidden' name='hdnrbtnDeleteValueFormat' id='hdnrbtnDeleteValueFormat' value='yesno'/>";
    }
    else if (document.getElementById("rbtnDeleteCheckedYN").checked)
    {
        DELETEdiv.innerHTML += "<input type='hidden' name='hdnrbtnDeleteValueFormat' id='hdnrbtnDeleteValueFormat' value='yn'/>";
    }
    else if (document.getElementById("rbtnDeleteCheckedTF").checked)
    {
        DELETEdiv.innerHTML += "<input type='hidden' name='hdnrbtnDeleteValueFormat' id='hdnrbtnDeleteValueFormat' value='truefalse'/>";
    }
    else
    {
        DELETEdiv.innerHTML += "<input type='hidden' name='hdnrbtnDeleteValueFormat' id='hdnrbtnDeleteValueFormat' value='NA'/>";
    }
    DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteTotalRdo' id='hdntxtDeleteTotalRdo' value='" + document.getElementById("txtDeleteTotalRdo").value + "'/>";
    DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteRdoCap' id='hdntxtDeleteRdoCap' value='" + document.getElementById("txtDeleteRdoCap").value + "'/>";
    DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteRdoVal' id='hdntxtDeleteRdoVal' value='" + document.getElementById("txtDeleteRdoVal").value + "'/>";
    DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteRdoDefVal' id='hdntxtDeleteRdoDefVal' value='" + document.getElementById("txtDeleteRdoDefVal").value + "'/>";
    DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteLabel' id='hdntxtDeleteLabel' value='" + document.getElementById("txtDeleteLabel").value + "'/>";

    if (document.getElementById("cmbDeleteControl").value == "ComboBox" || document.getElementById("cmbDeleteControl").value == "TextLikeCombo")
    {
        DELETEdiv.innerHTML += "<input type='hidden' name='hdnchkDeleteSrc' id='hdnchkDeleteSrc' value='" + document.getElementById("chkDeleteSrc").checked + "'/>";
        if (document.getElementById("chkDeleteSrc").checked)
        {
            if (document.getElementById("rdoDeleteSrcStatic").checked)
            {
                DELETEdiv.innerHTML += "<input type='hidden' name='hdnrdoDeleteDataSrc' id='hdnrdoDeleteDataSrc' value='Static'/>";
                DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteSrcStatic' id='hdntxtDeleteSrcStatic' value='" + document.getElementById("txtDeleteSrcStatic").value + "'/>";
                DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteSrcQuery' id='hdntxtDeleteSrcQuery' value=''/>";
                DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteWsdlUrl' id='hdntxtDeleteWsdlUrl' value=''/>";
                DELETEdiv.innerHTML += "<input type='hidden' name='hdncmbDeleteWsMethod' id='hdncmbDeleteWsMethod' value=''/>";
                DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteWsCmbValue' id='hdntxtDeleteWsCmbValue' value=''/>";
                DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteWsCmbText' id='hdntxtDeleteWsCmbText' value=''/>";
                DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteWsProject' id='hdntxtDeleteWsProject' value=''/>";
                DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteWsIntrface' id='hdntxtDeleteWsIntrface' value=''/>";
                DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteWsRetType' id='hdntxtDeleteWsRetType' value=''/>";
                DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteWsParams' id='hdntxtDeleteWsParams' value=''/>";
                DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteWsExps' id='hdntxtDeleteWsExps' value=''/>";
                DELETEdiv.innerHTML += "<input type='hidden' name='hdncmbDeleteCommonQuery' id='hdncmbDeleteCommonQuery' value=''/>";
            }
            else if (document.getElementById("rdoDeleteSrcQuery").checked)
            {
                DELETEdiv.innerHTML += "<input type='hidden' name='hdnrdoDeleteDataSrc' id='hdnrdoDeleteDataSrc' value='Query'/>";
                DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteSrcStatic' id='hdntxtDeleteSrcStatic' value=''/>";
                DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteSrcQuery' id='hdntxtDeleteSrcQuery' value='" + escape(document.getElementById("txtDeleteSrcQuery").value) + "'/>";
                DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteWsdlUrl' id='hdntxtDeleteWsdlUrl' value=''/>";
                DELETEdiv.innerHTML += "<input type='hidden' name='hdncmbDeleteWsMethod' id='hdncmbDeleteWsMethod' value=''/>";
                DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteWsCmbValue' id='hdntxtDeleteWsCmbValue' value=''/>";
                DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteWsCmbText' id='hdntxtDeleteWsCmbText' value=''/>";
                DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteWsProject' id='hdntxtDeleteWsProject' value=''/>";
                DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteWsIntrface' id='hdntxtDeleteWsIntrface' value=''/>";
                DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteWsRetType' id='hdntxtDeleteWsRetType' value=''/>";
                DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteWsParams' id='hdntxtDeleteWsParams' value=''/>";
                DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteWsExps' id='hdntxtDeleteWsExps' value=''/>";
                DELETEdiv.innerHTML += "<input type='hidden' name='hdncmbDeleteCommonQuery' id='hdncmbDeleteCommonQuery' value=''/>";
            }
            else if (document.getElementById("rdoDeleteSrcWS").checked)
            {
                DELETEdiv.innerHTML += "<input type='hidden' name='hdnrdoDeleteDataSrc' id='hdnrdoDeleteDataSrc' value='WebService'/>";
                DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteSrcStatic' id='hdntxtDeleteSrcStatic' value=''/>";
                DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteSrcQuery' id='hdntxtDeleteSrcQuery' value=''/>";
                DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteWsdlUrl' id='hdntxtDeleteWsdlUrl' value='" + document.getElementById("txtDeleteWsdlUrl").value + "'/>";
                DELETEdiv.innerHTML += "<input type='hidden' name='hdncmbDeleteWsMethod' id='hdncmbDeleteWsMethod' value='" + document.getElementById("cmbDeleteWsMethod").value + "'/>";
                DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteWsCmbValue' id='hdntxtDeleteWsCmbValue' value='" + document.getElementById("txtDeleteWsCmbValue").value + "'/>";
                DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteWsCmbText' id='hdntxtDeleteWsCmbText' value='" + document.getElementById("txtDeleteWsCmbText").value + "'/>";
                DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteWsProject' id='hdntxtDeleteWsProject' value='" + document.getElementById("txtDeleteWsProject").value + "'/>";
                DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteWsIntrface' id='hdntxtDeleteWsIntrface' value='" + document.getElementById("txtDeleteWsIntrface").value + "'/>";
                DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteWsRetType' id='hdntxtDeleteWsRetType' value='" + document.getElementById("txtDeleteWsRetType").value + "'/>";
                DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteWsParams' id='hdntxtDeleteWsParams' value='" + document.getElementById("txtDeleteWsParams").value + "'/>";
                DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteWsExps' id='hdntxtDeleteWsExps' value='" + document.getElementById("txtDeleteWsExps").value + "'/>";
                DELETEdiv.innerHTML += "<input type='hidden' name='hdncmbDeleteCommonQuery' id='hdncmbDeleteCommonQuery' value=''/>";
            }
            else if (document.getElementById("rdoDeleteSrcCommonCmb").checked)
            {
                DELETEdiv.innerHTML += "<input type='hidden' name='hdnrdoDeleteDataSrc' id='hdnrdoDeleteDataSrc' value='CommonCmb'/>";
                DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteSrcStatic' id='hdntxtDeleteSrcStatic' value=''/>";
                DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteSrcQuery' id='hdntxtDeleteSrcQuery' value='" + escape(document.getElementById("txtDeleteSrcQuery").value) + "'/>";
                DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteWsdlUrl' id='hdntxtDeleteWsdlUrl' value=''/>";
                DELETEdiv.innerHTML += "<input type='hidden' name='hdncmbDeleteWsMethod' id='hdncmbDeleteWsMethod' value=''/>";
                DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteWsCmbValue' id='hdntxtDeleteWsCmbValue' value=''/>";
                DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteWsCmbText' id='hdntxtDeleteWsCmbText' value=''/>";
                DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteWsProject' id='hdntxtDeleteWsProject' value=''/>";
                DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteWsIntrface' id='hdntxtDeleteWsIntrface' value=''/>";
                DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteWsRetType' id='hdntxtDeleteWsRetType' value=''/>";
                DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteWsParams' id='hdntxtDeleteWsParams' value=''/>";
                DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteWsExps' id='hdntxtDeleteWsExps' value=''/>";
                DELETEdiv.innerHTML += "<input type='hidden' name='hdncmbDeleteCommonQuery' id='hdncmbDeleteCommonQuery' value='" + document.getElementById("cmbDeleteCommonQuery").value + "'/>";
            }
        }
        else
        {
            DELETEdiv.innerHTML += "<input type='hidden' name='hdnrdoDeleteDataSrc' id='hdnrdoDeleteDataSrc' value=''/>";
            DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteSrcStatic' id='hdntxtDeleteSrcStatic' value=''/>";
            DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteSrcQuery' id='hdntxtDeleteSrcQuery' value=''/>";
            DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteWsdlUrl' id='hdntxtDeleteWsdlUrl' value=''/>";
            DELETEdiv.innerHTML += "<input type='hidden' name='hdncmbDeleteWsMethod' id='hdncmbDeleteWsMethod' value=''/>";
            DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteWsCmbValue' id='hdntxtDeleteWsCmbValue' value=''/>";
            DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteWsCmbText' id='hdntxtDeleteWsCmbText' value=''/>";
            DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteWsProject' id='hdntxtDeleteWsProject' value=''/>";
            DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteWsIntrface' id='hdntxtDeleteWsIntrface' value=''/>";
            DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteWsRetType' id='hdntxtDeleteWsRetType' value=''/>";
            DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteWsParams' id='hdntxtDeleteWsParams' value=''/>";
            DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteWsExps' id='hdntxtDeleteWsExps' value=''/>";
            DELETEdiv.innerHTML += "<input type='hidden' name='hdncmbDeleteCommonQuery' id='hdncmbDeleteCommonQuery' value=''/>";
        }
    }
    else
    {
        DELETEdiv.innerHTML += "<input type='hidden' name='hdnchkDeleteSrc' id='hdnchkDeleteSrc' value=''/>";
        DELETEdiv.innerHTML += "<input type='hidden' name='hdnrdoDeleteDataSrc' id='hdnrdoDeleteDataSrc' value=''/>";
        DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteSrcStatic' id='hdntxtDeleteSrcStatic' value=''/>";
        DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteSrcQuery' id='hdntxtDeleteSrcQuery' value=''/>";
        DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteWsdlUrl' id='hdntxtDeleteWsdlUrl' value=''/>";
        DELETEdiv.innerHTML += "<input type='hidden' name='hdncmbDeleteWsMethod' id='hdncmbDeleteWsMethod' value=''/>";
        DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteWsCmbValue' id='hdntxtDeleteWsCmbValue' value=''/>";
        DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteWsCmbText' id='hdntxtDeleteWsCmbText' value=''/>";
        DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteWsProject' id='hdntxtDeleteWsProject' value=''/>";
        DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteWsIntrface' id='hdntxtDeleteWsIntrface' value=''/>";
        DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteWsRetType' id='hdntxtDeleteWsRetType' value=''/>";
        DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteWsParams' id='hdntxtDeleteWsParams' value=''/>";
        DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteWsExps' id='hdntxtDeleteWsExps' value=''/>";
        DELETEdiv.innerHTML += "<input type='hidden' name='hdncmbDeleteCommonQuery' id='hdncmbDeleteCommonQuery' value=''/>";
    }
    DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteMaxsize' id='hdntxtDeleteMaxsize' value='" + document.getElementById("txtDeleteMaxsize").value + "'/>";
    DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteType' id='hdntxtDeleteType' value='" + document.getElementById("txtDeleteType").value + "'/>";
    DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteMaxfiles' id='hdntxtDeleteMaxfiles' value='" + document.getElementById("txtDeleteMaxfiles").value + "'/>";
    DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteEleName' id='hdntxtDeleteEleName' value='" + document.getElementById("txtDeleteEleName").value + "'/>";
    DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteDispTxt' id='hdntxtDeleteDispTxt' value='" + document.getElementById("txtDeleteDispTxt").value + "'/>";
    DELETEdiv.innerHTML += "<input type='hidden' name='hdntxtDeleteOnremoveCall' id='hdntxtDeleteOnremoveCall' value='" + document.getElementById("txtDeleteOnremoveCall").value + "'/>";
    DELETEdiv.innerHTML += "<input type='hidden' name='hdnchkDeleteMandatory' id='hdnchkDeleteMandatory' value='" + document.getElementById("chkDeleteMandatory").checked + "'/>";

    DELETEmaindiv.appendChild(DELETEdiv);
    deleteListDivId++;
    clearDeleteTabData();
}

function editDeleteTabRow(rowid)
{
    var editdivid = document.getElementById("deleteTabListTable").rows[rowid].cells[5].childNodes[0].data;

    var field = document.getElementById(editdivid).childNodes[0].value;
    document.getElementById("cmbDeleteControl").value = document.getElementById(editdivid).childNodes[1].value;
    //srs properties
    var validation = document.getElementById(editdivid).childNodes[2].value;
    document.getElementById("txtDeleteRemarks").value = document.getElementById(editdivid).childNodes[3].value;

    //code properties
    document.getElementById("txtDeleteId").value = document.getElementById(editdivid).childNodes[4].value;
    document.getElementById("txtDeleteName").value = document.getElementById(editdivid).childNodes[5].value;
    document.getElementById("txtDeleteStyle").value = document.getElementById(editdivid).childNodes[6].value;
    document.getElementById("txtDeleteSize").value = document.getElementById(editdivid).childNodes[7].value;
    document.getElementById("txtDeleteClass").value = document.getElementById(editdivid).childNodes[8].value;
    var options = document.getElementById("cmbDeleteOnchange").options;
    for (var i = 0; i < options.length; i++)
    {
        if (options[i].value != "-1" && options[i].value == document.getElementById(editdivid).childNodes[4].value)
        {
            options[i].remove();
        }
    }
    document.getElementById("cmbDeleteOnchange").value = document.getElementById(editdivid).childNodes[9].value;
    if (document.getElementById(editdivid).childNodes[10].value == "true")
    {
        document.getElementById("rbtnDeleteMultipleTrue").checked = true;
        document.getElementById("rbtnDeleteMultipleFalse").checked = false;
    }
    else
    {
        document.getElementById("rbtnDeleteMultipleTrue").checked = false;
        document.getElementById("rbtnDeleteMultipleFalse").checked = true;
    }
    if (document.getElementById(editdivid).childNodes[11].value == "true")
    {
        document.getElementById("rbtnDeleteCheckedTrue").checked = true;
        document.getElementById("rbtnDeleteCheckedFalse").checked = false;
    }
    else
    {
        document.getElementById("rbtnDeleteCheckedTrue").checked = false;
        document.getElementById("rbtnDeleteCheckedFalse").checked = true;
    }
    document.getElementById("txtDeleteMaxLength").value = document.getElementById(editdivid).childNodes[12].value;
    if (document.getElementById(editdivid).childNodes[13].value == "true")
    {
        document.getElementById("rbtnDeleteReadonlyTrue").checked = true;
        document.getElementById("rbtnDeleteReadonlyFalse").checked = false;
    }
    else
    {
        document.getElementById("rbtnDeleteReadonlyTrue").checked = false;
        document.getElementById("rbtnDeleteReadonlyFalse").checked = true;
    }
    document.getElementById("cmbDeleteAlign").value = document.getElementById(editdivid).childNodes[14].value;
    document.getElementById("txtDeleteRows").value = document.getElementById(editdivid).childNodes[15].value;
    document.getElementById("txtDeleteCols").value = document.getElementById(editdivid).childNodes[16].value;
    document.getElementById("txtDeleteValue").value = document.getElementById(editdivid).childNodes[17].value;
    document.getElementById("txtDeleteTabIndex").value = document.getElementById(editdivid).childNodes[18].value;
    document.getElementById("rbtnDeleteCheckedOneZero").checked = false;
    document.getElementById("rbtnDeleteCheckedYesNo").checked = false;
    document.getElementById("rbtnDeleteCheckedYN").checked = false;
    document.getElementById("rbtnDeleteCheckedTF").checked = false;
    if (document.getElementById(editdivid).childNodes[19].value == "onezero")
    {
        document.getElementById("rbtnDeleteCheckedOneZero").checked = true;
    }
    else if (document.getElementById(editdivid).childNodes[19].value == "yesno")
    {
        document.getElementById("rbtnDeleteCheckedYesNo").checked = true;
    }
    else if (document.getElementById(editdivid).childNodes[19].value == "yn")
    {
        document.getElementById("rbtnDeleteCheckedYN").checked = true;
    }
    else if (document.getElementById(editdivid).childNodes[19].value == "truefalse")
    {
        document.getElementById("rbtnDeleteCheckedTF").checked = true;
    }
    document.getElementById("txtDeleteTotalRdo").value = document.getElementById(editdivid).childNodes[20].value;
    document.getElementById("txtDeleteRdoCap").value = document.getElementById(editdivid).childNodes[21].value;
    document.getElementById("txtDeleteRdoVal").value = document.getElementById(editdivid).childNodes[22].value;
    document.getElementById("txtDeleteRdoDefVal").value = document.getElementById(editdivid).childNodes[23].value;
    document.getElementById("txtDeleteLabel").value = document.getElementById(editdivid).childNodes[24].value;
    if (document.getElementById(editdivid).childNodes[25].value == "true")
    {
        document.getElementById("chkDeleteSrc").checked = true;
    }
    else
    {
        document.getElementById("chkDeleteSrc").checked = false;
    }
    if (document.getElementById(editdivid).childNodes[26].value == "Static")
    {
        document.getElementById("rdoDeleteSrcStatic").checked = true;
    }
    else if (document.getElementById(editdivid).childNodes[26].value == "Query")
    {
        document.getElementById("rdoDeleteSrcQuery").checked = true;
    }
    else if (document.getElementById(editdivid).childNodes[26].value == "WebService")
    {
        document.getElementById("rdoDeleteSrcWS").checked = true;
    }
    else if (document.getElementById(editdivid).childNodes[26].value == "CommonCmb")
    {
        document.getElementById("rdoDeleteSrcCommonCmb").checked = true;
    }
    else
    {
        document.getElementById("rdoDeleteSrcStatic").checked = false;
        document.getElementById("rdoDeleteSrcQuery").checked = false;
        document.getElementById("rdoDeleteSrcWS").checked = false;
        document.getElementById("rdoDeleteSrcCommonCmb").checked = false;
    }
    document.getElementById("txtDeleteSrcStatic").value = document.getElementById(editdivid).childNodes[27].value;
    document.getElementById("txtDeleteSrcQuery").value = unescape(document.getElementById(editdivid).childNodes[28].value);
    document.getElementById("txtDeleteWsdlUrl").value = document.getElementById(editdivid).childNodes[29].value;
    document.getElementById("cmbDeleteWsMethod").innerHTML += "<option value=\"" + document.getElementById(editdivid).childNodes[30].value + "\">" + document.getElementById(editdivid).childNodes[30].value + "</option>";
    document.getElementById("cmbDeleteWsMethod").value = document.getElementById(editdivid).childNodes[30].value;
    document.getElementById("txtDeleteWsCmbValue").value = document.getElementById(editdivid).childNodes[31].value;
    document.getElementById("txtDeleteWsCmbText").value = document.getElementById(editdivid).childNodes[32].value;
    if (document.getElementById("txtDeleteWsProject") != null)
    {
        document.getElementById("txtDeleteWsProject").value = document.getElementById(editdivid).childNodes[33].value;
    }
    if (document.getElementById("txtDeleteWsIntrface") != null)
    {
        document.getElementById("txtDeleteWsIntrface").value = document.getElementById(editdivid).childNodes[34].value;
    }
    if (document.getElementById("txtDeleteWsRetType") != null)
    {
        document.getElementById("txtDeleteWsRetType").value = document.getElementById(editdivid).childNodes[35].value;
    }
    if (document.getElementById("txtDeleteWsParams") != null)
    {
        document.getElementById("txtDeleteWsParams").value = document.getElementById(editdivid).childNodes[36].value;
    }
    if (document.getElementById("txtDeleteWsExps") != null)
    {
        document.getElementById("txtDeleteWsExps").value = document.getElementById(editdivid).childNodes[37].value;
    }

    document.getElementById("cmbDeleteCommonQuery").value = document.getElementById(editdivid).childNodes[38].value;

    document.getElementById("txtDeleteMaxsize").value = document.getElementById(editdivid).childNodes[39].value;
    document.getElementById("txtDeleteType").value = document.getElementById(editdivid).childNodes[40].value;
    document.getElementById("txtDeleteMaxfiles").value = document.getElementById(editdivid).childNodes[41].value;
    document.getElementById("txtDeleteEleName").value = document.getElementById(editdivid).childNodes[42].value;
    document.getElementById("txtDeleteDispTxt").value = document.getElementById(editdivid).childNodes[43].value;
    document.getElementById("txtDeleteOnremoveCall").value = document.getElementById(editdivid).childNodes[44].value;

    if (document.getElementById(editdivid).childNodes[45].value == "true")
    {
        document.getElementById("chkDeleteMandatory").checked = true;
    }
    else
    {
        document.getElementById("chkDeleteMandatory").checked = false;
    }

    deleteDeleteTabRow(rowid);
    document.getElementById("cmbDeleteField").value = "-1";
    document.getElementById("cmbDeleteField").value = field;
    showDeleteControlProperties();
    document.getElementById("cmbDeleteValidation").value = validation;
    if (validation != "-1")
    {
        document.getElementById("trDeleteMndtry").style.display = "";
    }
}

function checkDeleteDeletePossible(rowid)
{
    var deletedivid = document.getElementById("deleteTabListTable").rows[rowid].cells[5].childNodes[0].data;
    var div = document.getElementById(deletedivid);
    if (document.getElementById("deleteTabListTable").rows[rowid].cells[1].innerHTML == "ComboBox" || document.getElementById("deleteTabListTable").rows[rowid].cells[1].innerHTML == "TextLikeCombo")
    {
        var id = div.innerHTML;
        id = id.substr(id.indexOf("<input name=\"hdntxtDeleteId\" id=\"hdntxtDeleteId\" value=\"") + 56).trim();
        id = id.substr(0, id.indexOf("\" type=\"hidden\"><")).trim();
        if (document.getElementById("hdncmbDeleteOnchange"))
        {
            var onchanges = document.getElementsByName("hdncmbDeleteOnchange");
            for (var i = 0; i < onchanges.length; i++)
            {
                if (onchanges[i].value == id)
                {
                    alert("You can not remove this Field. Another ComboBox is dependent on this ComboBox.");
                    return;
                }
            }
        }
    }
    deleteDeleteTabRow(rowid);
}

function deleteDeleteTabRow(rowid)
{
    var deletedivid = document.getElementById("deleteTabListTable").rows[rowid].cells[5].childNodes[0].data;
    var div = document.getElementById(deletedivid);
    if (document.getElementById("deleteTabListTable").rows[rowid].cells[1].innerHTML == "ComboBox" || document.getElementById("deleteTabListTable").rows[rowid].cells[1].innerHTML == "TextLikeCombo")
    {
        var id = div.innerHTML;
        id = id.substr(id.indexOf("<input name=\"hdntxtDeleteId\" id=\"hdntxtDeleteId\" value=\"") + 56).trim();
        id = id.substr(0, id.indexOf("\" type=\"hidden\"><")).trim();
        var options = document.getElementById("cmbDeleteOnchange").options;
        for (var i = 0; i < options.length; i++)
        {
            if (options[i].value != "-1" && options[i].value == id)
            {
                options[i].remove();
            }
        }
    }
    div.parentNode.removeChild(div);
    var field = document.getElementById("deleteTabListTable").rows[rowid].cells[0].innerHTML;
    var sel = document.getElementById("cmbDeleteField").value;

    var combo = document.getElementById("cmbDeleteField");
    if (field != "None")
    {
        for (var j = 0; j < combo.options.length; j++)
        {
            if (combo.options[j].value == field)
            {
                combo.options[j].disabled = false;
                break;
            }
        }
    }
    document.getElementById("cmbDeleteField").value = sel;
    document.getElementById("deleteTabListTable").deleteRow(rowid);
}

function clearDeleteTabData()
{
    document.getElementById("cmbDeleteField").value = '-1';
    document.getElementById("cmbDeleteControl").value = '-1';
    //srs properties
    removeAllValidations("cmbDeleteValidation");
    document.getElementById("txtDeleteRemarks").value = "";
    //code properties
    document.getElementById("txtDeleteId").value = "";
    document.getElementById("txtDeleteName").value = "";
    document.getElementById("txtDeleteStyle").value = "";
    document.getElementById("txtDeleteSize").value = "";
    document.getElementById("txtDeleteClass").value = "";
    document.getElementById("cmbDeleteOnchange").value = '-1';
    document.getElementById("rbtnDeleteMultipleTrue").checked = false;
    document.getElementById("rbtnDeleteMultipleFalse").checked = false;
    document.getElementById("rbtnDeleteCheckedTrue").checked = false;
    document.getElementById("rbtnDeleteCheckedFalse").checked = false;
    document.getElementById("txtDeleteMaxLength").value = "";
    document.getElementById("rbtnDeleteReadonlyTrue").checked = false;
    document.getElementById("rbtnDeleteReadonlyFalse").checked = false;
    document.getElementById("cmbDeleteAlign").value = "-1";
    document.getElementById("txtDeleteRows").value = "";
    document.getElementById("txtDeleteCols").value = "";
    document.getElementById("txtDeleteValue").value = "";
    document.getElementById("txtDeleteTabIndex").value = "";
    document.getElementById("rbtnDeleteCheckedOneZero").checked = false;
    document.getElementById("rbtnDeleteCheckedYesNo").checked = false;
    document.getElementById("rbtnDeleteCheckedYN").checked = false;
    document.getElementById("rbtnDeleteCheckedTF").checked = false;
    document.getElementById("txtDeleteTotalRdo").value = "";
    document.getElementById("txtDeleteRdoCap").value = "";
    document.getElementById("txtDeleteRdoVal").value = "";
    document.getElementById("txtDeleteRdoDefVal").value = "";
    document.getElementById("txtDeleteLabel").value = "";

    document.getElementById("chkDeleteSrc").checked = false;
    document.getElementById("rdoDeleteSrcStatic").checked = false;
    document.getElementById("rdoDeleteSrcQuery").checked = false;
    document.getElementById("rdoDeleteSrcWS").checked = false;
    document.getElementById("rdoDeleteSrcCommonCmb").checked = false;
    document.getElementById("txtDeleteSrcStatic").value = "";
    document.getElementById("txtDeleteSrcQuery").value = "";
    document.getElementById("txtDeleteWsdlUrl").value = "";
    document.getElementById("cmbDeleteCommonQuery").value = "-1";
    document.getElementById("cmbDeleteWsMethod").innerHTML = "<option value=\"-1\">-- Select Method --</option>";
    document.getElementById("txtDeleteWsCmbValue").value = "";
    document.getElementById("txtDeleteWsCmbText").value = "";
    document.getElementById("trDeleteIndepSrcStatic").style.display = "none";
    document.getElementById("trDeleteIndepSrcQuery").style.display = "none";
    document.getElementById("trDeleteIndepSrcCommonCmb").style.display = "none";
    document.getElementById("tbdDeleteWS").style.display = "none";
    document.getElementById("tdDeleteSrc").style.display = "none";
    document.getElementById("DeleteQueryResult").innerHTML = "";
    if (document.getElementById("txtDeleteWsProject") != null)
    {
        document.getElementById("txtDeleteWsProject").value = "";
    }
    if (document.getElementById("txtDeleteWsIntrface") != null)
    {
        document.getElementById("txtDeleteWsIntrface").value = "";
    }
    if (document.getElementById("txtDeleteWsRetType") != null)
    {
        document.getElementById("txtDeleteWsRetType").value = "";
    }
    if (document.getElementById("txtDeleteWsParams") != null)
    {
        document.getElementById("txtDeleteWsParams").value = "";
    }
    if (document.getElementById("txtDeleteWsExps") != null)
    {
        document.getElementById("txtDeleteWsExps").value = "";
    }

    document.getElementById("txtDeleteMaxsize").value = "";
    document.getElementById("txtDeleteType").value = "";
    document.getElementById("txtDeleteMaxfiles").value = "";
    document.getElementById("txtDeleteEleName").value = "";
    document.getElementById("txtDeleteDispTxt").value = "";
    document.getElementById("txtDeleteOnremoveCall").value = "";
    document.getElementById("chkDeleteMandatory").checked = false;
}

function showViewControlProperties()
{
    document.getElementById("divViewProperties").style.display = "";

    document.getElementById("trViewStyleSizeProp").style.display = "none";
    document.getElementById("trViewClassProp").style.display = "";
    document.getElementById("tdViewOnchange1").style.display = "none";
    document.getElementById("tdViewOnchange2").style.display = "none";
    document.getElementById("trViewTxt_LabelProp").style.display = "none";
    document.getElementById("trViewAlignProp").style.display = "none";
    document.getElementById("trViewCmbProp").style.display = "none";
    document.getElementById("trViewIndepSource").style.display = "none";
    document.getElementById("trViewIndepSrcStatic").style.display = "none";
    document.getElementById("trViewIndepSrcQuery").style.display = "none";
    document.getElementById("trViewIndepSrcCommonCmb").style.display = "none";
    document.getElementById("tbdViewWS").style.display = "none";
    document.getElementById("trViewTextAreaProp").style.display = "none";
    document.getElementById("trViewChkProp").style.display = "none";
    document.getElementById("trViewRdoProp").style.display = "none";
    document.getElementById("trViewChkValue").style.display = "none";
    document.getElementById("ViewTotalRdo1").style.display = "none";
    document.getElementById("ViewTotalRdo2").style.display = "none";
    document.getElementById("trViewRdoCaptions").style.display = "none";
    document.getElementById("trViewRdoValues").style.display = "none";
    document.getElementById("trViewRdoDefaultValue").style.display = "none";
    document.getElementById("ViewSize1").style.display = "";
    document.getElementById("ViewSize2").style.display = "";
    document.getElementById("trViewMndtry").style.display = "none";
    document.getElementById("cmbViewValidation").value = "-1";
    document.getElementById("trViewSizeType").style.display = "none";
    document.getElementById("trViewFilesEle").style.display = "none";
    document.getElementById("trViewDispTxt").style.display = "none";

    if (document.getElementById("cmbViewControl").value == "-1")
    {
        document.getElementById("trViewStyleSizeProp").style.display = "";
        removeAllValidations("cmbViewValidation");
    }
    else if (document.getElementById("cmbViewControl").value == "TextBox")
    {
        document.getElementById("trViewStyleSizeProp").style.display = "";
        document.getElementById("trViewTxt_LabelProp").style.display = "";
        document.getElementById("trViewAlignProp").style.display = "";
        showTextValidations("cmbViewValidation");
    }
    else if (document.getElementById("cmbViewControl").value == "ComboBox" || document.getElementById("cmbViewControl").value == "TextLikeCombo")
    {
        document.getElementById("tdViewOnchange1").style.display = "";
        document.getElementById("tdViewOnchange2").style.display = "";
        document.getElementById("trViewStyleSizeProp").style.display = "";
        if (document.getElementById("cmbViewControl").value == "ComboBox")
        {
            document.getElementById("trViewCmbProp").style.display = "";
        }
        document.getElementById("trViewIndepSource").style.display = "";
        if (document.getElementById("chkViewSrc").checked)
        {
            document.getElementById("tdViewSrc").style.display = "";
            if (document.getElementById("rdoViewSrcStatic").checked)

            {
                document.getElementById("trViewIndepSrcStatic").style.display = "";
            }
            else if (document.getElementById("rdoViewSrcQuery").checked)
            {
                document.getElementById("trViewIndepSrcQuery").style.display = "";
            }
            else if (document.getElementById("rdoViewSrcWS").checked)
            {
                document.getElementById("tbdViewWS").style.display = "";
            }
            else if (document.getElementById("rdoViewSrcCommonCmb").checked)
            {
                document.getElementById("trViewIndepSrcCommonCmb").style.display = "";
            }
        }
        showComboValidations("cmbViewValidation");
    }
    else if (document.getElementById("cmbViewControl").value == "TextArea")
    {
        document.getElementById("trViewStyleSizeProp").style.display = "";
        document.getElementById("trViewTextAreaProp").style.display = "";
        document.getElementById("ViewSize1").style.display = "none";
        document.getElementById("ViewSize2").style.display = "none";
        showTextValidations("cmbViewValidation");
    }
    else if (document.getElementById("cmbViewControl").value == "CheckBox")
    {
        document.getElementById("trViewStyleSizeProp").style.display = "";
        document.getElementById("trViewChkProp").style.display = "";
        document.getElementById("trViewChkValue").style.display = "";
        showCheckBoxValidations("cmbViewValidation");
    }
    else if (document.getElementById("cmbViewControl").value == "Radio")
    {
        document.getElementById("trViewStyleSizeProp").style.display = "";
        document.getElementById("trViewRdoProp").style.display = "";
        document.getElementById("ViewTotalRdo1").style.display = "";
        document.getElementById("ViewTotalRdo2").style.display = "";
        document.getElementById("trViewRdoCaptions").style.display = "";
        document.getElementById("trViewRdoValues").style.display = "";
        document.getElementById("trViewRdoDefaultValue").style.display = "";
        showRadioValidations("cmbViewValidation");
    }
    else if (document.getElementById("cmbViewControl").value == "DatePicker")
    {
        document.getElementById("trViewClassProp").style.display = "none";
        showDateValidations("cmbViewValidation");
    }
    else if (document.getElementById("cmbViewControl").value == "File")
    {
        document.getElementById("trViewClassProp").style.display = "none";
        removeAllValidations("cmbViewValidation");
    }
    else if (document.getElementById("cmbViewControl").value == "Password")
    {
        document.getElementById("trViewStyleSizeProp").style.display = "";
        document.getElementById("trViewTxt_LabelProp").style.display = "";
        document.getElementById("trViewAlignProp").style.display = "";
        showPasswordValidations("cmbViewValidation");
    }
    else if (document.getElementById("cmbViewControl").value == "FileBox")
    {
        document.getElementById("trViewClassProp").style.display = "none";
        document.getElementById("ViewSize1").style.display = "none";
        document.getElementById("ViewSize2").style.display = "none";
        document.getElementById("trViewSizeType").style.display = "";
        document.getElementById("trViewFilesEle").style.display = "";
        document.getElementById("trViewDispTxt").style.display = "";
        removeAllValidations("cmbViewValidation");
    }
}

function validateViewTabData()
{
    if (document.getElementById("cmbViewField").value == "-1")
    {
        alert("Please Select Field");
        document.getElementById("cmbViewField").focus();
        return;
    }
    else if (document.getElementById("cmbViewControl").value == "-1")
    {
        alert("Please Select Control");
        document.getElementById("cmbViewControl").focus();
        return;
    }
    else if (document.getElementById("txtViewTabIndex").value == "")
    {
        alert("Please Enter Tab Index");
        document.getElementById("txtViewTabIndex").focus();
        return;
    }
    else
    {
        if (validateTabIndex("txtViewTabIndex"))
        {
            if (document.getElementById("cmbViewControl").value == "CheckBox")
            {
                if (document.getElementById("cmbViewField").value != "None")
                {
                    if (validateValueFormat("rbtnViewCheckedOneZero", "rbtnViewCheckedYesNo", "rbtnViewCheckedYN", "rbtnViewCheckedTF", "chkViewCode", "ViewCodeProperty"))
                    {
                        validateBeforeView();
                    }
                }
                else
                {
                    validateBeforeView();
                }
            }
            else if (document.getElementById("cmbViewControl").value == "Radio")
            {
                if (validateNoOfRadio("txtViewTotalRdo", "txtViewRdoCap", "txtViewRdoVal", "chkViewCode", "ViewCodeProperty"))
                {
                    if (document.getElementById("txtViewRdoDefVal").value != "")
                    {
                        var defVal = document.getElementById("txtViewRdoDefVal").value;
                        if (defVal.match(/^[0-9]+$/))
                        {
                            if (parseInt(defVal, 10) >= 1 && parseInt(defVal, 10) <= parseInt(document.getElementById("txtViewTotalRdo").value, 10))
                            {
                                validateBeforeView();
                            }
                            else
                            {
                                alert("Please Enter Number Between 1 And " + parseInt(document.getElementById("txtViewTotalRdo").value, 10));
                                document.getElementById("txtViewRdoDefVal").focus();
                                return;
                            }
                        }
                        else
                        {
                            alert("Please Enter Number For Default Selection Of Radio");
                            document.getElementById("txtViewRdoDefVal").focus();
                            return;
                        }
                    }
                    else
                    {
                        validateBeforeView();
                    }
                }
            }
            else if (document.getElementById("cmbViewControl").value == "TextArea")
            {
                if (validateRowCol("txtViewRows", "txtViewCols", "chkViewCode", "ViewCodeProperty"))
                {
                    validateBeforeView();
                }
            }
            else if (document.getElementById("cmbViewControl").value == "FileBox")
            {
                if (document.getElementById("txtViewMaxsize").value != "")
                {
                    if (!document.getElementById("txtViewMaxsize").value.match(/^[0-9]+$/) || parseInt(document.getElementById("txtViewMaxsize").value, 10) == 0)
                    {
                        alert("Please Enter Positive Digits For Maxsize");
                        document.getElementById("chkViewCode").checked = true;
                        document.getElementById("ViewCodeProperty").style.display = "";
                        document.getElementById("txtViewMaxsize").focus();
                        return;
                    }
                    else
                    {
                        validateBeforeView();
                    }
                }
                else
                {
                    validateBeforeView();
                }
            }
            else if (document.getElementById("cmbViewControl").value == "ComboBox" || document.getElementById("cmbViewControl").value == "TextLikeCombo")
            {
                if (document.getElementById("cmbViewOnchange").value != "-1")
                {
                    if (document.getElementById("txtViewSrcQuery").value == "")
                    {
                        document.getElementById("chkViewSrc").checked = true;
                        document.getElementById("rdoViewSrcQuery").checked = true;
                        showSrc('View');
                        alert("Please Enter Query To Fill Combo");
                        return;
                    }
                    else
                    {
                        if (document.getElementById("txtViewSrcQuery").value.indexOf("|VARIABLE|") <= 0)
                        {
                            alert("You must specify where to match master combo's value in the Query by |VARIABLE| Keyword");
                            return;
                        }
                        else
                        {
                            validateBeforeView();
                        }
                    }
                }
                else
                {
                    validateBeforeView();
                }
            }
            else
            {
                validateBeforeView();
            }
        }
    }
}

function validateBeforeView()
{
    var table = document.getElementById("viewTabListTable");
    var rowCount = table.rows.length;

    var tabIdx = document.getElementById("txtViewTabIndex").value;
    for (var i = 2; i < rowCount; i++)
    {
        var rowtab = table.rows[i].cells[2].innerHTML;
        if (parseInt(tabIdx, 10) == rowtab)
        {
            alert("Duplicate Tab Index");
            document.getElementById("txtViewTabIndex").focus();
            return;
        }
    }

    if (validateCntrlIDName("View"))
    {
        if (checkControlID(document.getElementById("txtViewId").value))
        {
            alert("You Cannot Enter Duplicate Control ID");
            if (!document.getElementById("chkViewCode").checked)
            {
                document.getElementById("chkViewCode").checked = true;
                document.getElementById("ViewCodeProperty").style.display = "";
            }
            document.getElementById("txtViewId").focus();
            return;
        }
        else if (checkElementName(document.getElementById("txtViewId").value))
        {
            alert("Control ID Cannot be same as Element Name");
            if (!document.getElementById("chkViewCode").checked)
            {
                document.getElementById("chkViewCode").checked = true;
                document.getElementById("ViewCodeProperty").style.display = "";
            }
            document.getElementById("txtViewId").focus();
            return;
        }
        else if (checkControlName(document.getElementById("txtViewName").value))
        {
            alert("You Cannot Enter Duplicate Control Name");
            if (!document.getElementById("chkViewCode").checked)
            {
                document.getElementById("chkViewCode").checked = true;
                document.getElementById("ViewCodeProperty").style.display = "";
            }
            document.getElementById("txtViewName").focus();
            return;
        }
    }
    else
    {
        return;
    }

    if (document.getElementById("cmbViewControl").value == "FileBox")
    {
        if (document.getElementById("txtViewType").value == "")
        {
            alert("Please Enter Type");
            if (!document.getElementById("chkViewCode").checked)
            {
                document.getElementById("chkViewCode").checked = true;
                document.getElementById("ViewCodeProperty").style.display = "";
            }
            document.getElementById("txtViewType").focus();
            return;
        }
        else if (!checkFileTypeFormat("View"))
        {
            alert("Please Enter Type In Valid Format");
            if (!document.getElementById("chkViewCode").checked)
            {
                document.getElementById("chkViewCode").checked = true;
                document.getElementById("ViewCodeProperty").style.display = "";
            }
            document.getElementById("txtViewType").focus();
            return;
        }
        else if (!checkRestrictedFileType("View"))
        {
            return;
        }
        else if (document.getElementById("txtViewEleName").value == "")
        {
            alert("Please Enter Element Name");
            if (!document.getElementById("chkViewCode").checked)
            {
                document.getElementById("chkViewCode").checked = true;
                document.getElementById("ViewCodeProperty").style.display = "";
            }
            document.getElementById("txtViewEleName").focus();
            return;
        }
    }
    else if (document.getElementById("cmbViewControl").value == "ComboBox" || document.getElementById("cmbViewControl").value == "TextLikeCombo")
    {
        if (document.getElementById("chkViewSrc").checked)
        {
            if (document.getElementById("rdoViewSrcStatic").checked)
            {
                if (document.getElementById("txtViewSrcStatic").value == "")
                {
                    alert("Please Enter Data To Fill Combo");
                    return;
                }
                else if (document.getElementById("ViewStaticResult").innerHTML != "")
                {
                    return;
                }
            }
            else if (document.getElementById("rdoViewSrcQuery").checked)
            {
                if (document.getElementById("txtViewSrcQuery").value == "")
                {
                    alert("Please Enter Query To Fill Combo");
                    return;
                }
                else if (document.getElementById("ViewQueryResult").innerHTML != "")
                {
                    return;
                }
            }
            else if (document.getElementById("rdoViewSrcWS").checked)
            {
                if (document.getElementById("txtViewWsdlUrl").value == "")
                {
                    alert("Please Enter wsdl URL");
                    return;
                }
                if (document.getElementById("cmbViewWsMethod").value == "-1")
                {
                    alert("Please Select Method to Fill ComboBox");
                    document.getElementById("cmbViewWsMethod").focus();
                    return;
                }
                if (document.getElementById("txtViewWsRetType") == null || document.getElementById("txtViewWsParams") == null)
                {
                    alert("Please Enter Valid Values");
                    return;
                }
                document.getElementById("txtViewWsCmbValue").value = document.getElementById("txtViewWsCmbValue").value.replace(/\s+|\s+/g, '');
                document.getElementById("txtViewWsCmbText").value = document.getElementById("txtViewWsCmbText").value.replace(/\s+|\s+/g, '');
                if (document.getElementById("txtViewWsCmbValue").value == "")
                {
                    alert("Please Enter Field For Value In Combo Option");
                    document.getElementById("txtViewWsCmbValue").focus();
                    return;
                }
                else if (!document.getElementById("txtViewWsCmbValue").value.match(/^[A-Za-z_]+$/) || document.getElementById("txtViewWsCmbValue").value.match(/^[_]+$/))
                {
                    alert("Please Enter Valid Field For Value In Combo Option");
                    document.getElementById("txtViewWsCmbValue").focus();
                    return;
                }
                if (document.getElementById("txtViewWsCmbText").value == "")
                {
                    alert("Please Enter Field For Text In Combo Option");
                    document.getElementById("txtViewWsCmbText").focus();
                    return;
                }
                else if (!document.getElementById("txtViewWsCmbText").value.match(/^[A-Za-z_]+$/) || document.getElementById("txtViewWsCmbText").value.match(/^[_]+$/))
                {
                    alert("Please Enter Valid Field For Text In Combo Option");
                    document.getElementById("txtViewWsCmbText").focus();
                    return;
                }
            }
            else if (document.getElementById("rdoViewSrcCommonCmb").checked)
            {
                if (document.getElementById("cmbViewCommonQuery").value == "-1")
                {
                    alert("Please Select any one option to Fill ComboBox");
                    document.getElementById("cmbViewCommonQuery").focus();
                    return;
                }
            }
            else
            {
                alert("Please Enter Data Source");
                return;
            }
        }
    }

    if (document.getElementById("cmbViewControl").value == "FileBox")
    {
        var eleName = document.getElementById("txtViewEleName").value;
        eleName = eleName.toString().toLowerCase();
        eleName = eleName.split(' ').join('')
        document.getElementById("txtViewEleName").value = eleName;

        if (checkElementName(eleName))
        {
            alert("You Cannot Enter Duplicate Element Name");
            if (!document.getElementById("chkViewCode").checked)
            {
                document.getElementById("chkViewCode").checked = true;
                document.getElementById("ViewCodeProperty").style.display = "";
            }
            document.getElementById("txtViewEleName").focus();
            return;
        }
        else if (checkControlID(eleName))
        {
            alert("Element Name Cannot be same as Control Id");
            if (!document.getElementById("chkViewCode").checked)
            {
                document.getElementById("chkViewCode").checked = true;
                document.getElementById("ViewCodeProperty").style.display = "";
            }
            document.getElementById("txtViewEleName").focus();
            return;
        }
    }
    addNewViewTabRow();
}

var viewListDivId = 1;
function addNewViewTabRow()
{
    document.getElementById("viewTabList").style.display = "";
    var table = document.getElementById("viewTabListTable");
    var rowCount = table.rows.length;
    var row = table.insertRow(rowCount);

    var newCell0 = row.insertCell(0);
    var newCell1 = row.insertCell(1);
    var newCell2 = row.insertCell(2);
    var newCell3 = row.insertCell(3);
    var newCell4 = row.insertCell(4);
    var newCell5 = row.insertCell(5);
    newCell5.style.display = "none";

    var field = document.getElementById("cmbViewField").value;
    var combo = document.getElementById("cmbViewField");

    if (field != "None")
    {
        for (var i = 0; i < combo.options.length; i++)
        {
            if (combo.options[i].value == field)
            {
                combo.options[i].disabled = true;
                break;
            }
        }
    }

    newCell0.innerHTML = field;
    newCell1.innerHTML = document.getElementById("cmbViewControl").value;
    var tabIdx = document.getElementById("txtViewTabIndex").value;
    newCell2.innerHTML = parseInt(tabIdx, 10);
    newCell3.innerHTML = "<a onclick='javascript: editViewTabRow(this.parentNode.parentNode.rowIndex)'><img style=\"width:20px\" align =\"middle\" src=\"images/edit.gif\" alt=\"Edit\"></a>";
    newCell4.innerHTML = "<a onclick='javascript: checkViewDeletePossible(this.parentNode.parentNode.rowIndex)'><img style=\"width:20px\" align='center' src=\"images/delete.gif\" alt=\"Delete\"></a>";
    newCell5.innerHTML = "divView" + viewListDivId;

    var VIEWmaindiv = document.getElementById("hiddenDivViewTab");
    var VIEWdiv = document.createElement("div");
    VIEWdiv.id = "divView" + viewListDivId;

    VIEWdiv.innerHTML = "<input type='hidden' name='hdnViewField' id='hdnViewField' value='" + field + "'/>";
    VIEWdiv.innerHTML += "<input type='hidden' name='hdnViewControl' id='hdnViewControl' value='" + document.getElementById("cmbViewControl").value + "'/>";
    //srs properties
    VIEWdiv.innerHTML += "<input type='hidden' name='hdncmbViewValidation' id='hdncmbViewValidation' value='" + document.getElementById("cmbViewValidation").value + "'/>";
    VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewRemarks' id='hdntxtViewRemarks' value='" + document.getElementById("txtViewRemarks").value + "'/>";
    //code properties
    VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewId' id='hdntxtViewId' value='" + document.getElementById("txtViewId").value + "'/>";
    VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewName' id='hdntxtViewName' value='" + document.getElementById("txtViewName").value + "'/>";
    VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewStyle' id='hdntxtViewStyle' value='" + document.getElementById("txtViewStyle").value + "'/>";
    VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewSize' id='hdntxtViewSize' value='" + document.getElementById("txtViewSize").value + "'/>";
    VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewClass' id='hdntxtViewClass' value='" + document.getElementById("txtViewClass").value + "'/>";
    VIEWdiv.innerHTML += "<input type='hidden' name='hdncmbViewOnchange' id='hdncmbViewOnchange' value='" + document.getElementById("cmbViewOnchange").value + "'/>";
    if (document.getElementById("cmbViewControl").value == "ComboBox" || document.getElementById("cmbViewControl").value == "TextLikeCombo")
    {
        document.getElementById("cmbViewOnchange").innerHTML += "<option value=\"" + document.getElementById("txtViewId").value + "\">" + document.getElementById("txtViewId").value + "</option>";
    }
    VIEWdiv.innerHTML += "<input type='hidden' name='hdnrbtnViewMultiple' id='hdnrbtnViewMultiple' value='" + document.getElementById("rbtnViewMultipleTrue").checked + "'/>";
    VIEWdiv.innerHTML += "<input type='hidden' name='hdnrbtnViewChecked' id='hdnrbtnViewChecked' value='" + document.getElementById("rbtnViewCheckedTrue").checked + "'/>";
    VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewMaxLength' id='hdntxtViewMaxLength' value='" + document.getElementById("txtViewMaxLength").value + "'/>";
    VIEWdiv.innerHTML += "<input type='hidden' name='hdnrbtnViewReadonly' id='hdnrbtnViewReadonly' value='" + document.getElementById("rbtnViewReadonlyTrue").checked + "'/>";
    VIEWdiv.innerHTML += "<input type='hidden' name='hdncmbViewAlign' id='hdncmbViewAlign' value='" + document.getElementById("cmbViewAlign").value + "'/>";
    VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewRows' id='hdntxtViewRows' value='" + document.getElementById("txtViewRows").value + "'/>";
    VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewCols' id='hdntxtViewCols' value='" + document.getElementById("txtViewCols").value + "'/>";
    VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewValue' id='hdntxtViewValue' value='" + document.getElementById("txtViewValue").value + "'/>";
    VIEWdiv.innerHTML += "<input type='hidden' name='hdnViewTabIndex' id='hdnViewTabIndex' value='" + parseInt(tabIdx, 10) + "'/>";

    if (document.getElementById("rbtnViewCheckedOneZero").checked)
    {
        VIEWdiv.innerHTML += "<input type='hidden' name='hdnrbtnViewValueFormat' id='hdnrbtnViewValueFormat' value='onezero'/>";
    }
    else if (document.getElementById("rbtnViewCheckedYesNo").checked)
    {
        VIEWdiv.innerHTML += "<input type='hidden' name='hdnrbtnViewValueFormat' id='hdnrbtnViewValueFormat' value='yesno'/>";
    }
    else if (document.getElementById("rbtnViewCheckedYN").checked)
    {
        VIEWdiv.innerHTML += "<input type='hidden' name='hdnrbtnViewValueFormat' id='hdnrbtnViewValueFormat' value='yn'/>";
    }
    else if (document.getElementById("rbtnViewCheckedTF").checked)
    {
        VIEWdiv.innerHTML += "<input type='hidden' name='hdnrbtnViewValueFormat' id='hdnrbtnViewValueFormat' value='truefalse'/>";
    }
    else
    {
        VIEWdiv.innerHTML += "<input type='hidden' name='hdnrbtnViewValueFormat' id='hdnrbtnViewValueFormat' value='NA'/>";
    }
    VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewTotalRdo' id='hdntxtViewTotalRdo' value='" + document.getElementById("txtViewTotalRdo").value + "'/>";
    VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewRdoCap' id='hdntxtViewRdoCap' value='" + document.getElementById("txtViewRdoCap").value + "'/>";
    VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewRdoVal' id='hdntxtViewRdoVal' value='" + document.getElementById("txtViewRdoVal").value + "'/>";
    VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewRdoDefVal' id='hdntxtViewRdoDefVal' value='" + document.getElementById("txtViewRdoDefVal").value + "'/>";
    VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewLabel' id='hdntxtViewLabel' value='" + document.getElementById("txtViewLabel").value + "'/>";

    if (document.getElementById("cmbViewControl").value == "ComboBox" || document.getElementById("cmbViewControl").value == "TextLikeCombo")
    {
        VIEWdiv.innerHTML += "<input type='hidden' name='hdnchkViewSrc' id='hdnchkViewSrc' value='" + document.getElementById("chkViewSrc").checked + "'/>";
        if (document.getElementById("chkViewSrc").checked)
        {
            if (document.getElementById("rdoViewSrcStatic").checked)
            {
                VIEWdiv.innerHTML += "<input type='hidden' name='hdnrdoViewDataSrc' id='hdnrdoViewDataSrc' value='Static'/>";
                VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewSrcStatic' id='hdntxtViewSrcStatic' value='" + document.getElementById("txtViewSrcStatic").value + "'/>";
                VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewSrcQuery' id='hdntxtViewSrcQuery' value=''/>";
                VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewWsdlUrl' id='hdntxtViewWsdlUrl' value=''/>";
                VIEWdiv.innerHTML += "<input type='hidden' name='hdncmbViewWsMethod' id='hdncmbViewWsMethod' value=''/>";
                VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewWsCmbValue' id='hdntxtViewWsCmbValue' value=''/>";
                VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewWsCmbText' id='hdntxtViewWsCmbText' value=''/>";
                VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewWsProject' id='hdntxtViewWsProject' value=''/>";
                VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewWsIntrface' id='hdntxtViewWsIntrface' value=''/>";
                VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewWsRetType' id='hdntxtViewWsRetType' value=''/>";
                VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewWsParams' id='hdntxtViewWsParams' value=''/>";
                VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewWsExps' id='hdntxtViewWsExps' value=''/>";
                VIEWdiv.innerHTML += "<input type='hidden' name='hdncmbViewCommonQuery' id='hdncmbViewCommonQuery' value=''/>";
            }
            else if (document.getElementById("rdoViewSrcQuery").checked)
            {
                VIEWdiv.innerHTML += "<input type='hidden' name='hdnrdoViewDataSrc' id='hdnrdoViewDataSrc' value='Query'/>";
                VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewSrcStatic' id='hdntxtViewSrcStatic' value=''/>";
                VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewSrcQuery' id='hdntxtViewSrcQuery' value='" + escape(document.getElementById("txtViewSrcQuery").value) + "'/>";
                VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewWsdlUrl' id='hdntxtViewWsdlUrl' value=''/>";
                VIEWdiv.innerHTML += "<input type='hidden' name='hdncmbViewWsMethod' id='hdncmbViewWsMethod' value=''/>";
                VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewWsCmbValue' id='hdntxtViewWsCmbValue' value=''/>";
                VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewWsCmbText' id='hdntxtViewWsCmbText' value=''/>";
                VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewWsProject' id='hdntxtViewWsProject' value=''/>";
                VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewWsIntrface' id='hdntxtViewWsIntrface' value=''/>";
                VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewWsRetType' id='hdntxtViewWsRetType' value=''/>";
                VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewWsParams' id='hdntxtViewWsParams' value=''/>";
                VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewWsExps' id='hdntxtViewWsExps' value=''/>";
                VIEWdiv.innerHTML += "<input type='hidden' name='hdncmbViewCommonQuery' id='hdncmbViewCommonQuery' value=''/>";
            }
            else if (document.getElementById("rdoViewSrcWS").checked)
            {
                VIEWdiv.innerHTML += "<input type='hidden' name='hdnrdoViewDataSrc' id='hdnrdoViewDataSrc' value='WebService'/>";
                VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewSrcStatic' id='hdntxtViewSrcStatic' value=''/>";
                VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewSrcQuery' id='hdntxtViewSrcQuery' value=''/>";
                VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewWsdlUrl' id='hdntxtViewWsdlUrl' value='" + document.getElementById("txtViewWsdlUrl").value + "'/>";
                VIEWdiv.innerHTML += "<input type='hidden' name='hdncmbViewWsMethod' id='hdncmbViewWsMethod' value='" + document.getElementById("cmbViewWsMethod").value + "'/>";
                VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewWsCmbValue' id='hdntxtViewWsCmbValue' value='" + document.getElementById("txtViewWsCmbValue").value + "'/>";
                VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewWsCmbText' id='hdntxtViewWsCmbText' value='" + document.getElementById("txtViewWsCmbText").value + "'/>";
                VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewWsProject' id='hdntxtViewWsProject' value='" + document.getElementById("txtViewWsProject").value + "'/>";
                VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewWsIntrface' id='hdntxtViewWsIntrface' value='" + document.getElementById("txtViewWsIntrface").value + "'/>";
                VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewWsRetType' id='hdntxtViewWsRetType' value='" + document.getElementById("txtViewWsRetType").value + "'/>";
                VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewWsParams' id='hdntxtViewWsParams' value='" + document.getElementById("txtViewWsParams").value + "'/>";
                VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewWsExps' id='hdntxtViewWsExps' value='" + document.getElementById("txtViewWsExps").value + "'/>";
                VIEWdiv.innerHTML += "<input type='hidden' name='hdncmbViewCommonQuery' id='hdncmbViewCommonQuery' value=''/>";
            }
            else if (document.getElementById("rdoViewSrcCommonCmb").checked)
            {
                VIEWdiv.innerHTML += "<input type='hidden' name='hdnrdoViewDataSrc' id='hdnrdoViewDataSrc' value='CommonCmb'/>";
                VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewSrcStatic' id='hdntxtViewSrcStatic' value=''/>";
                VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewSrcQuery' id='hdntxtViewSrcQuery' value='" + escape(document.getElementById("txtViewSrcQuery").value) + "'/>";
                VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewWsdlUrl' id='hdntxtViewWsdlUrl' value=''/>";
                VIEWdiv.innerHTML += "<input type='hidden' name='hdncmbViewWsMethod' id='hdncmbViewWsMethod' value=''/>";
                VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewWsCmbValue' id='hdntxtViewWsCmbValue' value=''/>";
                VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewWsCmbText' id='hdntxtViewWsCmbText' value=''/>";
                VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewWsProject' id='hdntxtViewWsProject' value=''/>";
                VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewWsIntrface' id='hdntxtViewWsIntrface' value=''/>";
                VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewWsRetType' id='hdntxtViewWsRetType' value=''/>";
                VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewWsParams' id='hdntxtViewWsParams' value=''/>";
                VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewWsExps' id='hdntxtViewWsExps' value=''/>";
                VIEWdiv.innerHTML += "<input type='hidden' name='hdncmbViewCommonQuery' id='hdncmbViewCommonQuery' value='" + document.getElementById("cmbViewCommonQuery").value + "'/>";
            }
        }
        else
        {
            VIEWdiv.innerHTML += "<input type='hidden' name='hdnrdoViewDataSrc' id='hdnrdoViewDataSrc' value=''/>";
            VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewSrcStatic' id='hdntxtViewSrcStatic' value=''/>";
            VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewSrcQuery' id='hdntxtViewSrcQuery' value=''/>";
            VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewWsdlUrl' id='hdntxtViewWsdlUrl' value=''/>";
            VIEWdiv.innerHTML += "<input type='hidden' name='hdncmbViewWsMethod' id='hdncmbViewWsMethod' value=''/>";
            VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewWsCmbValue' id='hdntxtViewWsCmbValue' value=''/>";
            VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewWsCmbText' id='hdntxtViewWsCmbText' value=''/>";
            VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewWsProject' id='hdntxtViewWsProject' value=''/>";
            VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewWsIntrface' id='hdntxtViewWsIntrface' value=''/>";
            VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewWsRetType' id='hdntxtViewWsRetType' value=''/>";
            VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewWsParams' id='hdntxtViewWsParams' value=''/>";
            VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewWsExps' id='hdntxtViewWsExps' value=''/>";
            VIEWdiv.innerHTML += "<input type='hidden' name='hdncmbViewCommonQuery' id='hdncmbViewCommonQuery' value=''/>";
        }
    }
    else
    {
        VIEWdiv.innerHTML += "<input type='hidden' name='hdnchkViewSrc' id='hdnchkViewSrc' value=''/>";
        VIEWdiv.innerHTML += "<input type='hidden' name='hdnrdoViewDataSrc' id='hdnrdoViewDataSrc' value=''/>";
        VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewSrcStatic' id='hdntxtViewSrcStatic' value=''/>";
        VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewSrcQuery' id='hdntxtViewSrcQuery' value=''/>";
        VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewWsdlUrl' id='hdntxtViewWsdlUrl' value=''/>";
        VIEWdiv.innerHTML += "<input type='hidden' name='hdncmbViewWsMethod' id='hdncmbViewWsMethod' value=''/>";
        VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewWsCmbValue' id='hdntxtViewWsCmbValue' value=''/>";
        VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewWsCmbText' id='hdntxtViewWsCmbText' value=''/>";
        VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewWsProject' id='hdntxtViewWsProject' value=''/>";
        VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewWsIntrface' id='hdntxtViewWsIntrface' value=''/>";
        VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewWsRetType' id='hdntxtViewWsRetType' value=''/>";
        VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewWsParams' id='hdntxtViewWsParams' value=''/>";
        VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewWsExps' id='hdntxtViewWsExps' value=''/>";
        VIEWdiv.innerHTML += "<input type='hidden' name='hdncmbViewCommonQuery' id='hdncmbViewCommonQuery' value=''/>";
    }
    VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewMaxsize' id='hdntxtViewMaxsize' value='" + document.getElementById("txtViewMaxsize").value + "'/>";
    VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewType' id='hdntxtViewType' value='" + document.getElementById("txtViewType").value + "'/>";
    VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewMaxfiles' id='hdntxtViewMaxfiles' value='" + document.getElementById("txtViewMaxfiles").value + "'/>";
    VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewEleName' id='hdntxtViewEleName' value='" + document.getElementById("txtViewEleName").value + "'/>";
    VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewDispTxt' id='hdntxtViewDispTxt' value='" + document.getElementById("txtViewDispTxt").value + "'/>";
    VIEWdiv.innerHTML += "<input type='hidden' name='hdntxtViewOnremoveCall' id='hdntxtViewOnremoveCall' value='" + document.getElementById("txtViewOnremoveCall").value + "'/>";
    VIEWdiv.innerHTML += "<input type='hidden' name='hdnchkViewMandatory' id='hdnchkViewMandatory' value='" + document.getElementById("chkViewMandatory").checked + "'/>";

    VIEWmaindiv.appendChild(VIEWdiv);
    viewListDivId++;
    clearViewTabData();
}

function editViewTabRow(rowid)
{
    var editdivid = document.getElementById("viewTabListTable").rows[rowid].cells[5].childNodes[0].data;

    var field = document.getElementById(editdivid).childNodes[0].value;
    document.getElementById("cmbViewControl").value = document.getElementById(editdivid).childNodes[1].value;
    //srs properties
    var validation = document.getElementById(editdivid).childNodes[2].value
    document.getElementById("txtViewRemarks").value = document.getElementById(editdivid).childNodes[3].value;

    //code properties
    document.getElementById("txtViewId").value = document.getElementById(editdivid).childNodes[4].value;
    document.getElementById("txtViewName").value = document.getElementById(editdivid).childNodes[5].value;
    document.getElementById("txtViewStyle").value = document.getElementById(editdivid).childNodes[6].value;
    document.getElementById("txtViewSize").value = document.getElementById(editdivid).childNodes[7].value;
    document.getElementById("txtViewClass").value = document.getElementById(editdivid).childNodes[8].value;
    var options = document.getElementById("cmbViewOnchange").options;
    for (var i = 0; i < options.length; i++)
    {
        if (options[i].value != "-1" && options[i].value == document.getElementById(editdivid).childNodes[4].value)
        {
            options[i].remove();
        }
    }
    document.getElementById("cmbViewOnchange").value = document.getElementById(editdivid).childNodes[9].value;
    if (document.getElementById(editdivid).childNodes[10].value == "true")
    {
        document.getElementById("rbtnViewMultipleTrue").checked = true;
        document.getElementById("rbtnViewMultipleFalse").checked = false;
    }
    else
    {
        document.getElementById("rbtnViewMultipleTrue").checked = false;
        document.getElementById("rbtnViewMultipleFalse").checked = true;
    }
    if (document.getElementById(editdivid).childNodes[11].value == "true")
    {
        document.getElementById("rbtnViewCheckedTrue").checked = true;
        document.getElementById("rbtnViewCheckedFalse").checked = false;
    }
    else
    {
        document.getElementById("rbtnViewCheckedTrue").checked = false;
        document.getElementById("rbtnViewCheckedFalse").checked = true;
    }
    document.getElementById("txtViewMaxLength").value = document.getElementById(editdivid).childNodes[12].value;
    if (document.getElementById(editdivid).childNodes[13].value == "true")
    {
        document.getElementById("rbtnViewReadonlyTrue").checked = true;
        document.getElementById("rbtnViewReadonlyFalse").checked = false;
    }
    else
    {
        document.getElementById("rbtnViewReadonlyTrue").checked = false;
        document.getElementById("rbtnViewReadonlyFalse").checked = true;
    }
    document.getElementById("cmbViewAlign").value = document.getElementById(editdivid).childNodes[14].value;
    document.getElementById("txtViewRows").value = document.getElementById(editdivid).childNodes[15].value;
    document.getElementById("txtViewCols").value = document.getElementById(editdivid).childNodes[16].value;
    document.getElementById("txtViewValue").value = document.getElementById(editdivid).childNodes[17].value;
    document.getElementById("txtViewTabIndex").value = document.getElementById(editdivid).childNodes[18].value;
    document.getElementById("rbtnViewCheckedOneZero").checked = false;
    document.getElementById("rbtnViewCheckedYesNo").checked = false;
    document.getElementById("rbtnViewCheckedYN").checked = false;
    document.getElementById("rbtnViewCheckedTF").checked = false;
    if (document.getElementById(editdivid).childNodes[19].value == "onezero")
    {
        document.getElementById("rbtnViewCheckedOneZero").checked = true;
    }
    else if (document.getElementById(editdivid).childNodes[19].value == "yesno")
    {
        document.getElementById("rbtnViewCheckedYesNo").checked = true;
    }
    else if (document.getElementById(editdivid).childNodes[19].value == "yn")
    {
        document.getElementById("rbtnViewCheckedYN").checked = true;
    }
    else if (document.getElementById(editdivid).childNodes[19].value == "truefalse")
    {
        document.getElementById("rbtnViewCheckedTF").checked = true;
    }
    document.getElementById("txtViewTotalRdo").value = document.getElementById(editdivid).childNodes[20].value;
    document.getElementById("txtViewRdoCap").value = document.getElementById(editdivid).childNodes[21].value;
    document.getElementById("txtViewRdoVal").value = document.getElementById(editdivid).childNodes[22].value;
    document.getElementById("txtViewRdoDefVal").value = document.getElementById(editdivid).childNodes[23].value;
    document.getElementById("txtViewLabel").value = document.getElementById(editdivid).childNodes[24].value;
    if (document.getElementById(editdivid).childNodes[25].value == "true")
    {
        document.getElementById("chkViewSrc").checked = true;
    }
    else
    {
        document.getElementById("chkViewSrc").checked = false;
    }
    if (document.getElementById(editdivid).childNodes[26].value == "Static")
    {
        document.getElementById("rdoViewSrcStatic").checked = true;
    }
    else if (document.getElementById(editdivid).childNodes[26].value == "Query")
    {
        document.getElementById("rdoViewSrcQuery").checked = true;
    }
    else if (document.getElementById(editdivid).childNodes[26].value == "WebService")
    {
        document.getElementById("rdoViewSrcWS").checked = true;
    }
    else if (document.getElementById(editdivid).childNodes[26].value == "CommonCmb")
    {
        document.getElementById("rdoViewSrcCommonCmb").checked = true;
    }
    else
    {
        document.getElementById("rdoViewSrcStatic").checked = false;
        document.getElementById("rdoViewSrcQuery").checked = false;
        document.getElementById("rdoViewSrcWS").checked = false;
        document.getElementById("rdoViewSrcCommonCmb").checked = false;
    }
    document.getElementById("txtViewSrcStatic").value = document.getElementById(editdivid).childNodes[27].value;
    document.getElementById("txtViewSrcQuery").value = unescape(document.getElementById(editdivid).childNodes[28].value);
    document.getElementById("txtViewWsdlUrl").value = document.getElementById(editdivid).childNodes[29].value;
    document.getElementById("cmbViewWsMethod").innerHTML += "<option value=\"" + document.getElementById(editdivid).childNodes[30].value + "\">" + document.getElementById(editdivid).childNodes[30].value + "</option>";
    document.getElementById("cmbViewWsMethod").value = document.getElementById(editdivid).childNodes[30].value;
    document.getElementById("txtViewWsCmbValue").value = document.getElementById(editdivid).childNodes[31].value;
    document.getElementById("txtViewWsCmbText").value = document.getElementById(editdivid).childNodes[32].value;
    if (document.getElementById("txtViewWsProject") != null)
    {
        document.getElementById("txtViewWsProject").value = document.getElementById(editdivid).childNodes[33].value;
    }
    if (document.getElementById("txtViewWsIntrface") != null)
    {
        document.getElementById("txtViewWsIntrface").value = document.getElementById(editdivid).childNodes[34].value;
    }
    if (document.getElementById("txtViewWsRetType") != null)
    {
        document.getElementById("txtViewWsRetType").value = document.getElementById(editdivid).childNodes[35].value;
    }
    if (document.getElementById("txtViewWsParams") != null)
    {
        document.getElementById("txtViewWsParams").value = document.getElementById(editdivid).childNodes[36].value;
    }
    if (document.getElementById("txtViewWsExps") != null)
    {
        document.getElementById("txtViewWsExps").value = document.getElementById(editdivid).childNodes[37].value;
    }
    document.getElementById("cmbViewCommonQuery").value = document.getElementById(editdivid).childNodes[38].value;
    document.getElementById("txtViewMaxsize").value = document.getElementById(editdivid).childNodes[39].value;
    document.getElementById("txtViewType").value = document.getElementById(editdivid).childNodes[40].value;
    document.getElementById("txtViewMaxfiles").value = document.getElementById(editdivid).childNodes[41].value;
    document.getElementById("txtViewEleName").value = document.getElementById(editdivid).childNodes[42].value;
    document.getElementById("txtViewDispTxt").value = document.getElementById(editdivid).childNodes[43].value;
    document.getElementById("txtViewOnremoveCall").value = document.getElementById(editdivid).childNodes[44].value;

    if (document.getElementById(editdivid).childNodes[45].value == "true")
    {
        document.getElementById("chkViewMandatory").checked = true;
    }
    else
    {
        document.getElementById("chkViewMandatory").checked = false;
    }

    deleteViewTabRow(rowid);
    document.getElementById("cmbViewField").value = "-1";
    document.getElementById("cmbViewField").value = field;
    showViewControlProperties();
    document.getElementById("cmbViewValidation").value = validation;
    if (validation != "-1")
    {
        document.getElementById("trViewMndtry").style.display = "";
    }
}

function checkViewDeletePossible(rowid)
{
    var deletedivid = document.getElementById("viewTabListTable").rows[rowid].cells[5].childNodes[0].data;
    var div = document.getElementById(deletedivid);
    if (document.getElementById("viewTabListTable").rows[rowid].cells[1].innerHTML == "ComboBox" || document.getElementById("viewTabListTable").rows[rowid].cells[1].innerHTML == "TextLikeCombo")
    {
        var id = div.innerHTML;
        id = id.substr(id.indexOf("<input name=\"hdntxtViewId\" id=\"hdntxtViewId\" value=\"") + 52).trim();
        id = id.substr(0, id.indexOf("\" type=\"hidden\"><")).trim();
        if (document.getElementById("hdncmbViewOnchange"))
        {
            var onchanges = document.getElementsByName("hdncmbViewOnchange");
            for (var i = 0; i < onchanges.length; i++)
            {
                if (onchanges[i].value == id)
                {
                    alert("You can not remove this Field. Another ComboBox is dependent on this ComboBox.");
                    return;
                }
            }
        }
    }
    deleteViewTabRow(rowid);
}

function deleteViewTabRow(rowid)
{
    var deletedivid = document.getElementById("viewTabListTable").rows[rowid].cells[5].childNodes[0].data;
    var div = document.getElementById(deletedivid);
    if (document.getElementById("viewTabListTable").rows[rowid].cells[1].innerHTML == "ComboBox" || document.getElementById("viewTabListTable").rows[rowid].cells[1].innerHTML == "TextLikeCombo")
    {
        var id = div.innerHTML;
        id = id.substr(id.indexOf("<input name=\"hdntxtViewId\" id=\"hdntxtViewId\" value=\"") + 52).trim();
        id = id.substr(0, id.indexOf("\" type=\"hidden\"><")).trim();
        var options = document.getElementById("cmbViewOnchange").options;
        for (var i = 0; i < options.length; i++)
        {
            if (options[i].value != "-1" && options[i].value == id)
            {
                options[i].remove();
            }
        }
    }
    div.parentNode.removeChild(div);
    var field = document.getElementById("viewTabListTable").rows[rowid].cells[0].innerHTML;
    var sel = document.getElementById("cmbViewField").value;

    var combo = document.getElementById("cmbViewField");
    if (field != "None")
    {
        for (var j = 0; j < combo.options.length; j++)
        {
            if (combo.options[j].value == field)
            {
                combo.options[j].disabled = false;
                break;
            }
        }
    }
    document.getElementById("cmbViewField").value = sel;
    document.getElementById("viewTabListTable").deleteRow(rowid);
}

function clearViewTabData()
{
    document.getElementById("cmbViewField").value = '-1';
    document.getElementById("cmbViewControl").value = '-1';
    //srs properties
    removeAllValidations("cmbViewValidation");
    document.getElementById("txtViewRemarks").value = "";
    //code properties
    document.getElementById("txtViewId").value = "";
    document.getElementById("txtViewName").value = "";
    document.getElementById("txtViewStyle").value = "";
    document.getElementById("txtViewSize").value = "";
    document.getElementById("txtViewClass").value = "";
    document.getElementById("cmbViewOnchange").value = '-1';
    document.getElementById("rbtnViewMultipleTrue").checked = false;
    document.getElementById("rbtnViewMultipleFalse").checked = false;
    document.getElementById("rbtnViewCheckedTrue").checked = false;
    document.getElementById("rbtnViewCheckedFalse").checked = false;
    document.getElementById("txtViewMaxLength").value = "";
    document.getElementById("rbtnViewReadonlyTrue").checked = false;
    document.getElementById("rbtnViewReadonlyFalse").checked = false;
    document.getElementById("cmbViewAlign").value = "-1";
    document.getElementById("txtViewRows").value = "";
    document.getElementById("txtViewCols").value = "";
    document.getElementById("txtViewValue").value = "";
    document.getElementById("txtViewTabIndex").value = "";
    document.getElementById("rbtnViewCheckedOneZero").checked = false;
    document.getElementById("rbtnViewCheckedYesNo").checked = false;
    document.getElementById("rbtnViewCheckedYN").checked = false;
    document.getElementById("rbtnViewCheckedTF").checked = false;
    document.getElementById("txtViewTotalRdo").value = "";
    document.getElementById("txtViewRdoCap").value = "";
    document.getElementById("txtViewRdoVal").value = "";
    document.getElementById("txtViewRdoDefVal").value = "";
    document.getElementById("txtViewLabel").value = "";

    document.getElementById("chkViewSrc").checked = false;
    document.getElementById("rdoViewSrcStatic").checked = false;
    document.getElementById("rdoViewSrcQuery").checked = false;
    document.getElementById("rdoViewSrcWS").checked = false;
    document.getElementById("rdoViewSrcCommonCmb").checked = false;
    document.getElementById("txtViewSrcStatic").value = "";
    document.getElementById("txtViewSrcQuery").value = "";
    document.getElementById("txtViewWsdlUrl").value = "";
    document.getElementById("cmbViewCommonQuery").value = "-1";
    document.getElementById("cmbViewWsMethod").innerHTML = "<option value=\"-1\">-- Select Method --</option>";
    document.getElementById("txtViewWsCmbValue").value = "";
    document.getElementById("txtViewWsCmbText").value = "";
    document.getElementById("trViewIndepSrcStatic").style.display = "none";
    document.getElementById("trViewIndepSrcQuery").style.display = "none";
    document.getElementById("trViewIndepSrcCommonCmb").style.display = "none";
    document.getElementById("tbdViewWS").style.display = "none";
    document.getElementById("tdViewSrc").style.display = "none";
    document.getElementById("ViewQueryResult").innerHTML = "";
    if (document.getElementById("txtViewWsProject") != null)
    {
        document.getElementById("txtViewWsProject").value = "";
    }
    if (document.getElementById("txtViewWsIntrface") != null)
    {
        document.getElementById("txtViewWsIntrface").value = "";
    }
    if (document.getElementById("txtViewWsRetType") != null)
    {
        document.getElementById("txtViewWsRetType").value = "";
    }
    if (document.getElementById("txtViewWsParams") != null)
    {
        document.getElementById("txtViewWsParams").value = "";
    }
    if (document.getElementById("txtViewWsExps") != null)
    {
        document.getElementById("txtViewWsExps").value = "";
    }
    document.getElementById("txtViewMaxsize").value = "";
    document.getElementById("txtViewType").value = "";
    document.getElementById("txtViewMaxfiles").value = "";
    document.getElementById("txtViewEleName").value = "";
    document.getElementById("txtViewDispTxt").value = "";
    document.getElementById("txtViewOnremoveCall").value = "";
    document.getElementById("chkViewMandatory").checked = false;
}

function checkFileTypeFormat(tab)
{
    var fileType = document.getElementById("txt" + tab + "Type").value;
    if (!((fileType.match(/^(([A-Za-z]+),([A-Za-z]+))*$/)) || (fileType.match(/^([A-Za-z]+)+$/))))
    {
        return false;
    }
    else
    {
        return true;
    }
}

function checkRestrictedFileType(tab)
{
    var fileType = document.getElementById("txt" + tab + "Type").value;
    var type = fileType.split(",");

    for (var i = 0; i < type.length; i++)
    {
        var templ = type[i].toLowerCase();
        for (var j = 0; j < invalidExt.length; j++)
        {
            if (templ == invalidExt[j])
            {
                alert("'" + invalidExt[j] + "' is a Restricted File Type.");
                if (!document.getElementById("chk" + tab + "Code").checked)
                {
                    document.getElementById("chk" + tab + "Code").checked = true;
                    document.getElementById(tab + "CodeProperty").style.display = "";
                }
                document.getElementById("txt" + tab + "Type").focus();
                return false;
            }
        }
    }
    return true;
}

function checkElementName(eleName)
{
    for (var i = 1; i < addListDivId; i++)
    {
        if (document.getElementById("divAdd" + i) != null)
        {
            if (eleName == document.getElementById("divAdd" + i).childNodes[44].value)
            {
                return true;
            }
        }
    }
    for (i = 1; i < editListDivId; i++)
    {
        if (document.getElementById("divEdit" + i) != null)
        {
            if (eleName == document.getElementById("divEdit" + i).childNodes[41].value)
            {
                return true;
            }
        }
    }
    for (i = 1; i < deleteListDivId; i++)
    {
        if (document.getElementById("divDel" + i) != null)
        {
            if (eleName == document.getElementById("divDel" + i).childNodes[41].value)
            {
                return true;
            }
        }
    }
    for (i = 1; i < viewListDivId; i++)
    {
        if (document.getElementById("divView" + i) != null)
        {
            if (eleName == document.getElementById("divView" + i).childNodes[39].value)
            {
                return true;
            }
        }
    }
    return false;
}

function onFinish()
{
    var params = getFormData(document.MenuForm);
    getData_sync("mstgenv2.fin?cmdAction=getColumnTypes", "divColumnTypes", params, false);
    var qColumn = document.getElementById("divColumnTypes").innerHTML;
    var column = eval("(" + qColumn + ")");

    var allColumn = document.createElement("div");
    var allColumnTypes = document.createElement("div");

    for (var i = 0; i < column.colNames.length; i++)
    {
        allColumn.innerHTML += "<input type='hidden' id='hdnMstAllColumns' name='hdnMstAllColumns' value='" + column.colNames[i] + "'>";
        allColumnTypes.innerHTML += "<input type='hidden' id='hdnMstAllDataTypes' name='hdnMstAllDataTypes' value='" + column.colTypes[i] + "'>";
    }
    document.getElementById("divColumnTypes").innerHTML = "";
    document.getElementById("divColumnTypes").appendChild(allColumn);
    document.getElementById("divColumnTypes").appendChild(allColumnTypes);

    params = getFormData(document.MenuForm);
    getData_sync("mstgenv2.fin?cmdAction=setFinish", "divFinish", params, false);
    if (document.getElementById("divFinish").innerHTML.match(".zip"))
    {
        document.getElementById("btnFinish").disabled = true;
    }
    else
    {
        alert("You Have Error In Code Generation");
    }
}
