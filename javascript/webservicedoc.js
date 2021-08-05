function WebService()
{
    document.getElementById("webservicepage").style.display = "";
    document.getElementById("success").style.display = "none";
    getSynchronousData('webservicedoc.fin?cmdAction=getWebServiceForm', 'type=soap', 'webservicepage');
    loadCombo('consumerPro');
    document.getElementById("rdosoap").checked = true;
}

var rowId = 1;
var oldNameValue = "";
var oldParamCount = 0;
function AddParam(tableName, type) {
    var table = document.getElementById(tableName);
    var row = document.createElement("tr");
    var cell0 = document.createElement("td");
    row.appendChild(cell0);

    var cell5 = document.createElement("td");
    var sel = document.createElement("select");
    sel.setAttribute("class", "custom_combo_mid");
    sel.setAttribute("name", tableName + "_nature");
    sel.setAttribute("id", tableName + "_nature" + rowId);
    sel.innerHTML += document.getElementById("natureList").innerHTML;
    cell5.appendChild(sel);
    row.appendChild(cell5);

    var cell1 = document.createElement("td");
    var text = document.createElement("input");
    text.setAttribute("type", "text");
    text.setAttribute("name", tableName + "_name");
    text.setAttribute("maxlength", "50");
    text.setAttribute("style", "float: none!important; width: 136px;");
    text.setAttribute("id", tableName + "_name" + rowId);
    text.setAttribute("onchange", "javascript:toLowerFirstLetter(this);onChangeName(this," + tableName + "_dataType" + rowId + ",'" + type + "','" + tableName + "')");
    text.setAttribute("onfocus", "javascript:getOldValue(this)");
    cell1.appendChild(text);
    row.appendChild(cell1);

    var cell2 = document.createElement("td");
    var sel = document.createElement("select");
    sel.setAttribute("class", "custom_combo_mid");
    sel.setAttribute("name", tableName + "_dataType");
    sel.setAttribute("id", tableName + "_dataType" + rowId);
//    sel.setAttribute("onchange", "javascript:AddBean('" + tableName + "', this.parentNode.parentNode, this.parentNode.parentNode.rowIndex, this.value)");
    sel.setAttribute("onchange", "javascript:checkDetails(this," + tableName + "_name" + rowId + "," + tableName + "_format" + rowId + "," + tableName + "_default" + rowId + ",'" + type + "','" + tableName + "')");
    sel.innerHTML += document.getElementById("dataTypeList").innerHTML;
    cell2.appendChild(sel);
    row.appendChild(cell2);

    var cell3 = document.createElement("td");
    var text = document.createElement("input");
    text.setAttribute("type", "text");
    text.setAttribute("name", tableName + "_default");
    text.setAttribute("value", "null");
    text.setAttribute("maxlength", "50");
    text.setAttribute("id", tableName + "_default" + rowId);
    text.setAttribute("onchange", "javascript:onChangeDefaultValue(this," + tableName + "_dataType" + rowId + ")");
    text.setAttribute("style", "float: none!important; width: 136px;");
    cell3.appendChild(text);
    row.appendChild(cell3);
    var cell4 = document.createElement("td");
    var text = document.createElement("input");
    text.setAttribute("type", "text");
    text.setAttribute("name", tableName + "_format");
    text.setAttribute("id", tableName + "_format" + rowId);
    text.setAttribute("readonly", "readonly");
    text.setAttribute("maxlength", "50");
    text.setAttribute("value", "-");
    text.setAttribute("style", "float: none!important; width: 170px;");
    cell4.appendChild(text);
    row.appendChild(cell4);
    table.appendChild(row);
//    if (!(tableName === 'inparamtable' || tableName === 'outparamtable'))
//    {
//        document.getElementById(tableName + "_dataType" + rowId).remove(8);
//        document.getElementById(tableName + "_dataType" + rowId).remove(18);
//    }
    rowId++;
}

function AddBean(beanTableName, type) {
//    var table = document.getElementById(tableName).getElementsByTagName('tbody')[0];
    var row = document.createElement("tr");
    var cell0 = document.createElement("td");
    var beanTable = "<table border=\"2\" rules=\"NONE\" frame=\"box\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"2\" id=" + type + "_" + beanTableName + " class=\"menu_subcaption\" width=\"100%\">";
    beanTable += "<tr>";
    beanTable += "<td colspan=\"6\">";
    beanTable += "<div class=\"menu_caption_bg\">";
    beanTable += "<div class=\"menu_caption_text\">" + firstupperCase(beanTableName) + "</div>";
//    beanTable += "<select class=\"custom_combo_mid\" id=\"" + beanTableName + "_BeanType\" name=\"" + beanTableName + "_BeanType\" tabindex=\"2\">";
//    beanTable += "<option value=\"Single\">Single</option>";
//    beanTable += "<option value=\"List\">List</option>";
//    beanTable += "</select>";
    beanTable += "</div>";
    beanTable += "</td>";
    beanTable += "</tr>";
    beanTable += "<tr>";
    beanTable += "<td align=\"left\" style=\"float: none!important; width: 50%;\" >";
    beanTable += "Numbers of Parameter : ";
    beanTable += "</td>";
    beanTable += "<td colspan=\"5\" class=\"report_content_value\">";
    beanTable += "<input style=\"float: none!important; width: 136px;\" type=\"text\" id=\"beanParam\" name=\"beanParam\" value=\"\" onkeyup=\"javascript:onlyNumbersInForm(this.id);\" onchange=\"javascript:insertParam(this.value, '" + type + "_" + beanTableName + "','','" + type + "')\" onfocus=\"javascript:getLastCount(this.value)\" />";
    beanTable += "</td>";
    beanTable += "</tr>";
    beanTable += "<tr>";
    beanTable += "<td align=\"left\" class=\"report_content_caption\">";
    beanTable += "Parameter <sup class=\"astriek\">*</sup>: ";
    beanTable += "</td>";
    beanTable += "<td class=\"report_content_value\">";
    beanTable += "Nature <sup class=\"astriek\">*</sup>";
    beanTable += "</td>";
    beanTable += "<td class=\"report_content_value\">";
    beanTable += "Name <sup class=\"astriek\">*</sup>";
    beanTable += "</td>";
    beanTable += "<td class=\"report_content_value\">";
    beanTable += "Datatype <sup class=\"astriek\">*</sup>";
    beanTable += "</td>";
    beanTable += "<td class=\"report_content_value\">";
    beanTable += "Default Value <sup class=\"astriek\">*</sup>";
    beanTable += "</td>";
    beanTable += "<td class=\"report_content_value\">";
    beanTable += "Format";
    beanTable += "</td>";
    beanTable += "</tr>";
    beanTable += "</table>";
    cell0.setAttribute("colspan", "6");
    cell0.innerHTML = beanTable;
    row.appendChild(cell0);
//    buttons.before(row);
//    table.appendChild(row);
//    var rowCount = table.rows.length - 1;
//    alert(rowCount);
    document.getElementById("buttons").parentNode.insertBefore(row, document.getElementById("buttons").previousSibling);
}

function validateData()
{
    if (document.getElementById("btnAdd").value === "Generate xls" && document.getElementById("rdosoap").checked)
    {
        if (!validate_dropdown(document.webserviceform, "producerPro", "Producer Project", true))
        {
            return false;
        }
        if (!validate_dropdown(document.webserviceform, "consumerPro", "Consumer Project", true))
        {
            return false;
        }
        if (document.getElementById("webserviceName").value === "" || document.getElementById("webserviceName").value === null)
        {
            alert("Please Enter WebService Name");
            return false;
        }

        var nameId = document.getElementById('webserviceName');
        if (!nameId.value.toString().toLowerCase().endsWith("service"))
        {
            alert("WebService Name should be ends with 'Service'");
            document.getElementById(nameId.id).focus();
            return false;
        }
        else if (nameId.value.toString().endsWith("service"))
        {
            var serviceName = document.getElementById(nameId.id).value;
            serviceName = serviceName.replace("service", "Service");
            document.getElementById(nameId.id).value = serviceName;
        }

        if (document.getElementById("methodName").value === "" || document.getElementById("methodName").value === null)
        {
            alert("Please Enter Method Name");
            return false;
        }

        if (document.getElementById("inParameters").value === "" || document.getElementById("inParameters").value === null)
        {
            alert("Please Enter Numbers of In Parameters");
            document.getElementById("inParameters").focus();
            return false;
        }
        if (document.getElementById("outParameters").value === "" || document.getElementById("outParameters").value === null)
        {
            alert("Please Enter Numbers of Out Parameters");
            return false;
        }
//        if (document.getElementById("inParam").value === "" || document.getElementById("inParam").value === null)
//        {
//            return false;
//        }
        if (document.getElementById("inParameters").value.length > 0)
        {
            for (var i = 1; i <= document.getElementById("inParameters").value; i++)
            {
                if (document.getElementById("inParameters").value > 1)
                {
//                    if (document.getElementById("inParam").value === "" || document.getElementById("inParam").value === null)
//                    {
//                        alert("Please Enter value for 'In parameter Bean Name'");
//                        document.getElementById("inParam").focus();
//                        return false;
//                    }
//                    if (!document.getElementById("inParam").value.endsWith("Bean"))
//                    {
//                        alert("Name of In Parameter Bean should be ends with 'Bean'");
//                        document.getElementById("inParam").focus();
//                        return false;
//                    }
//                    if (document.getElementById("inParam").value.toLowerCase() === "inputbean" || document.getElementById("inParam").value.toLowerCase() === "outputbean")
//                    {
//                        alert("Please Enter valid In Parameter Bean");
//                        document.getElementById("inParam").value = "";
//                        document.getElementById("inParam").focus();
//                        return false;
//                    }
                }
                if (document.getElementById("inparamtable_name" + i).value === "" || document.getElementById("inparamtable_name" + i).value === null)
                {
                    alert("Please Enter value for 'In parameter Name'");
                    document.getElementById("inparamtable_name" + i).focus();
                    return false;
                }
                if (document.getElementById("inparamtable_default" + i).value === "" || document.getElementById("inparamtable_default" + i).value === null)
                {
                    alert("Please Enter value for 'In parameter Default Value'");
                    document.getElementById("inparamtable_default" + i).focus();
                    return false;
                }
            }
        }

        if (document.getElementById("outParameters").value.length > 0)
        {
            for (var i = 1; i <= document.getElementById("outParameters").value; i++)
            {
                if (document.getElementById("outParameters").value > 1)
                {
//                    if (document.getElementById("outParam").value === "" || document.getElementById("outParam").value === null)
//                    {
//                        alert("Please Enter value for 'Out parameter Bean Name'");
//                        document.getElementById("outParam").focus();
//                        return false;
//                    }
//                    if (!document.getElementById("outParam").value.endsWith("Bean"))
//                    {
//                        alert("Name of Out Parameter Bean should be ends with 'Bean'");
//                        document.getElementById("outParam").focus();
//                        return false;
//                    }
//                    if (document.getElementById("outParam").value.toLowerCase() === "inputbean" || document.getElementById("outParam").value.toLowerCase() === "outputbean")
//                    {
//                        alert("Please Enter valid Out Parameter Bean");
//                        document.getElementById("outParam").value = "";
//                        document.getElementById("outParam").focus();
//                        return false;
//                    }
                }
                if (document.getElementById("outparamtable_name" + i).value === "" || document.getElementById("outparamtable_name" + i).value === null)
                {
                    alert("Please Enter value for 'Out parameter Name'");
                    document.getElementById("outparamtable_name" + i).focus();
                    return false;
                }
                if (document.getElementById("outparamtable_default" + i).value === "" || document.getElementById("outparamtable_default" + i).value === null)
                {
                    alert("Please Enter value for 'Out parameter Default Value'");
                    document.getElementById("outparamtable_default" + i).focus();
                    return false;
                }
            }
        }

        var table = document.getElementById('captiontable');
        var beanTableData = "";
        var beanAllTable = "";
//        var bean_type = "";
        for (var i = 8; i <= table.rows.length - 2; i++)
        {
            var noOfParam = 0;
            var beanTableName = table.rows[i].cells[0].childNodes[0].id;
            var beanTable = table.rows[i].cells[0].childNodes[0].childNodes[0].childNodes[0].childNodes[0].childNodes[0].childNodes[0].innerHTML;
            noOfParam = parseInt(document.getElementById(beanTableName).rows[1].cells[1].getElementsByTagName("input")[0].value);
//            bean_type += document.getElementById(beanTableName).rows[0].getElementsByTagName("select")[0].value + ":" + beanTable + ",";
            beanAllTable += beanTable + ",";
            if (isNaN(noOfParam) || noOfParam === 0)
            {
                alert("Please Enter Number of Parameters in " + beanTableName);
                return false;
            }
            else
            {
                var paramLength = noOfParam + 3;

                for (var j = 3; j < paramLength; j++)
                {
                    var bean_nature = (document.getElementById(beanTableName).rows[j].getElementsByTagName("select")[0].value);
                    var bean_name = (document.getElementById(beanTableName).rows[j].getElementsByTagName("input")[0].value);
                    var bean_datatype = (document.getElementById(beanTableName).rows[j].getElementsByTagName("select")[1].value);
                    var bean_default = (document.getElementById(beanTableName).rows[j].getElementsByTagName("input")[1].value);
                    var bean_format = (document.getElementById(beanTableName).rows[j].getElementsByTagName("input")[2].value);

                    if (bean_name === "" || bean_name === null)
                    {
                        alert("Please Enter value for 'Name' in " + beanTableName);
                        return false;
                    }
                    if (bean_name.toLowerCase() === "inputbean" || bean_name.toLowerCase() === "outputbean")
                    {
                        alert("inputBean or outputBean is not Valid 'Name' in " + beanTableName);
                        return false;
                    }
                    if (bean_default === "" || bean_default === null)
                    {
                        alert("Please Enter value for 'Default Value' in " + beanTableName);
                        return false;
                    }

//                    if (bean_datatype.contains("boolean[]") || bean_datatype.contains("date[]") || bean_datatype.contains("time[]") || bean_datatype.contains("datetime[]"))
//                    {
//                        bean_datatype = "String[]";
//                    }
//                    if (bean_datatype.contains("boolean") || bean_datatype.contains("date") || bean_datatype.contains("time") || bean_datatype.contains("datetime"))
//                    {
//                        bean_datatype = "String";
//                    }

//                    alert(beanTableName + "(" + bean_name + "," + bean_datatype + "," + bean_default + "," + bean_format + ")");
                    beanTableData += bean_nature + "," + bean_name + "," + bean_datatype + "," + bean_default + "," + bean_format + ";";
                }
                beanTableData += "|";
            }
        }
        document.getElementById("webservicepage").style.display = "none";
        document.getElementById("success").style.display = "";
        document.getElementById("beanAllTable").value = beanAllTable;
//        document.getElementById("beanType").value = bean_type;
        document.getElementById("beanTableData").value = beanTableData;
//        alert(bean_type);
        var param = getFormData(document.webserviceform);
        getSynchronousData('webservicedoc.fin?cmdAction=generateXLS', param, 'success');
    }
    else if (document.getElementById("btnAdd").value === "Generate xls" && document.getElementById("rdorest").checked)
    {
        if (!validate_dropdown(document.webserviceform, "producerPro", "Producer Project", true))
        {
            return false;
        }
        if (document.getElementById("webserviceName").value === "" || document.getElementById("webserviceName").value === null)
        {
            alert("Please Enter WebService Name");
            document.getElementById("webserviceName").focus();
            return false;
        }
        if (document.getElementById("webserviceURL").value === "" || document.getElementById("webserviceURL").value === null)
        {
            alert("Please Enter webservice URL");
            return false;
        }
        if (!validateRestWebserviceURL(document.getElementById('webserviceURL'))) {
            return false;
        }

        if(document.getElementsByName("httpMethod")[0].checked == false
                && document.getElementsByName("httpMethod")[1].checked == false
                && document.getElementsByName("httpMethod")[2].checked == false)
        {
            alert("Please select atleast one HTTP Method.");
            return false;
        }

        if (document.getElementById("inParameters").value === "" || document.getElementById("inParameters").value === null)
        {
            alert("Please Enter Numbers of In Parameters");
            document.getElementById("inParameters").focus();
            return false;
        }
        if (parseInt(document.getElementById("inParameters").value) < 0)
        {
            alert("Please Enter Numbers of In Parameters in positive number.");
            document.getElementById("inParameters").focus();
            return false;
        }

        if (document.getElementById("outParameters").value === "" || document.getElementById("outParameters").value === null)
        {
            alert("Please Enter Numbers of Out Parameters");
            document.getElementById("outParameters").focus();
            return false;
        }
        if (parseInt(document.getElementById("outParameters").value) < 0)
        {
            alert("Please Enter Numbers of Out Parameters in positive number.");
            document.getElementById("outParameters").focus();
            return false;
        }

        if (document.getElementById("inParameters").value.length > 0)
        {
            for (var i = 1; i <= document.getElementById("inParameters").value; i++)
            {
                if (document.getElementById("inparamtable_name" + i).value === "" || document.getElementById("inparamtable_name" + i).value === null)
                {
                    alert("Please Enter value for 'In parameter Name'");
                    document.getElementById("inparamtable_name" + i).focus();
                    return false;
                }
                if (document.getElementById("inparamtable_default" + i).value === "" || document.getElementById("inparamtable_default" + i).value === null)
                {
                    alert("Please Enter value for 'In parameter Default Value'");
                    document.getElementById("inparamtable_default" + i).focus();
                    return false;
                }
            }
        }
        if (document.getElementById("outParameters").value.length > 0)
        {
            for (var i = 1; i <= document.getElementById("outParameters").value; i++)
            {
                if (document.getElementById("outparamtable_name" + i).value === "" || document.getElementById("outparamtable_name" + i).value === null)
                {
                    alert("Please Enter value for 'Out parameter Name'");
                    document.getElementById("outparamtable_name" + i).focus();
                    return false;
                }
                if (document.getElementById("outparamtable_default" + i).value === "" || document.getElementById("outparamtable_default" + i).value === null)
                {
                    alert("Please Enter value for 'Out parameter Default Value'");
                    document.getElementById("outparamtable_default" + i).focus();
                    return false;
                }
            }
        }

        var table = document.getElementById('captiontable');
        var beanTableData = "";
        var beanAllTable = "";
        for (var i = 8; i <= table.rows.length - 2; i++)
        {
            var noOfParam = 0;
            var beanTableName = table.rows[i].cells[0].childNodes[0].id;
            var beanTable = table.rows[i].cells[0].childNodes[0].childNodes[0].childNodes[0].childNodes[0].childNodes[0].childNodes[0].innerHTML;
            noOfParam = parseInt(document.getElementById(beanTableName).rows[1].cells[1].getElementsByTagName("input")[0].value);
            beanAllTable += beanTable + ",";
            if (isNaN(noOfParam) || noOfParam === 0)
            {
                alert("Please Enter Number of Parameters in " + beanTableName);
                return false;
            }
            else
            {
                var paramLength = noOfParam + 3;

                for (var j = 3; j < paramLength; j++)
                {
                    var bean_nature = (document.getElementById(beanTableName).rows[j].getElementsByTagName("select")[0].value);
                    var bean_name = (document.getElementById(beanTableName).rows[j].getElementsByTagName("input")[0].value);
                    var bean_datatype = (document.getElementById(beanTableName).rows[j].getElementsByTagName("select")[1].value);
                    var bean_default = (document.getElementById(beanTableName).rows[j].getElementsByTagName("input")[1].value);
                    var bean_format = (document.getElementById(beanTableName).rows[j].getElementsByTagName("input")[2].value);

                    if (bean_name === "" || bean_name === null)
                    {
                        alert("Please Enter value for 'Name' in " + beanTableName);
                        return false;
                    }
                    if (bean_name.toLowerCase() === "inputbean" || bean_name.toLowerCase() === "outputbean")
                    {
                        alert("inputBean or outputBean is not Valid 'Name' in " + beanTableName);
                        return false;
                    }
                    if (bean_default === "" || bean_default === null)
                    {
                        alert("Please Enter value for 'Default Value' in " + beanTableName);
                        return false;
                    }
                    beanTableData += bean_nature + "," + bean_name + "," + bean_datatype + "," + bean_default + "," + bean_format + ";";
                }
                beanTableData += "|";
            }
        }
        document.getElementById("webservicepage").style.display = "none";
        document.getElementById("success").style.display = "";
        document.getElementById("beanAllTable").value = beanAllTable;
        document.getElementById("beanTableData").value = beanTableData;
        var param = getFormData(document.webserviceform);
        getSynchronousData('webservicedoc.fin?cmdAction=generateXLS', param, 'success');
    }
}
function insertParam(parameters, tableName, beanName, type)
{
    rowId = parseInt(oldParamCount) + 1;
    var table = document.getElementById(tableName);

    if (parameters.length <= 0)
    {
        parameters = 0;
    }
    for (var j = table.rows.length - 1; j >= (3 + parseInt(parameters)); j--)
    {
//        if (beanName.length > 0)
//        {
//            document.getElementById(beanName).style.display = "none";
//            if (tableName === 'inparamtable')
//            {
//                document.getElementById("inBeanType").selectedIndex = 0;
////                document.getElementById("inList").style.display = "none";
//            }
//            if (tableName === 'outparamtable')
//            {
//                document.getElementById("outBeanType").selectedIndex = 0;
////                document.getElementById("outList").style.display = "none";
//            }
//        }

        // for delete bean-tables after refresh column numbers
//        if (tableName === 'inparamtable' || tableName === 'outparamtable')
//        {
        var bean_name = (document.getElementById(tableName).rows[j].getElementsByTagName("input")[0].value);
        var bean_datatype = (document.getElementById(tableName).rows[j].getElementsByTagName("select")[1].value);

        if (bean_datatype === 'Bean' || bean_datatype === 'List<Bean>')
        {
            var tbl = document.getElementById(type + "_" + bean_name);
            if (tbl)
            {
                tbl.parentNode.parentNode.remove(tbl.parentNode);
            }
        }
//        }

        table.deleteRow(j);
    }
//
//    if (parameters === '1' && tableName === 'inparamtable')
//    {
//        onChangeInCombo(document.getElementById("inBeanType"));
//    }
//    if (parameters === '1' && tableName === 'outparamtable')
//    {
//        onChangeOutCombo(document.getElementById("outBeanType"));
//    }
//
//    if (parameters > 1 && beanName.length > 0 && tableName === 'inparamtable')
//    {
//        document.getElementById(beanName).style.display = "";
//        document.getElementById(beanName).cells[1].getElementsByTagName("input")[0].value = "";
////            document.getElementById("inList").style.display = "";
//    }
//    else if (parameters <= 0 && beanName.length > 0 && tableName === 'inparamtable')
//    {
//        document.getElementById(beanName).style.display = "none";
//        document.getElementById(beanName).cells[1].getElementsByTagName("input")[0].value = "";
//    }
//    else if (parameters > 1 && beanName.length > 0 && tableName === 'outparamtable')
//    {
//        document.getElementById(beanName).style.display = "";
//        onChangeOutCombo(document.getElementById("outBeanType"));
//        document.getElementById(beanName).cells[1].getElementsByTagName("input")[0].value = "";
////            document.getElementById("outList").style.display = "";
//    }
//    else if (parameters <= 0 && beanName.length > 0 && tableName === 'outparamtable')
//    {
//        document.getElementById(beanName).style.display = "none";
//        document.getElementById(beanName).cells[1].getElementsByTagName("input")[0].value = "";
////            document.getElementById("outList").style.display = "";
//    }
    for (var i = oldParamCount; i < parameters; i++)
    {
        AddParam(tableName, type);
    }
//    }
    oldParamCount = 0;
}

function checkDetails(combo, nameId, formatId, defaultId, type, tableName)
{
    var comboValue = combo.value;
//    if (nameId.value === "" && (comboValue === 'Bean' || comboValue === 'Bean[]'))
    if (nameId.value === "" && (comboValue === 'Bean' || comboValue === 'List<Bean>'))
    {
        alert("Please Enter Bean Name");
        document.getElementById(combo.id).selectedIndex = 0;
        return false;
    }
    if (comboValue === 'Bean' || comboValue === 'List<Bean>')
    {
        if (nameId.value.endsWith("bean"))
        {
            nameId.value = nameId.value.replace("bean", "Bean");
        }
        else if (!nameId.value.endsWith("Bean"))
        {
            alert("Name of Bean should be ends with 'Bean'");
            document.getElementById(combo.id).selectedIndex = 0;
            return false;
        }
    }
    if (nameId.value.toLowerCase() === "inputbean" || nameId.value.toLowerCase() === "outputbean")
    {
        alert("inputBean or outputBean is not Valid 'Bean Name'");
        nameId.value = "";
        document.getElementById(combo.id).selectedIndex = 0;
        document.getElementById(nameId.id).focus();
        return false;
    }
    //insert bean table
//    if (tableName === 'inparamtable' || tableName === 'outparamtable')
//    {
//        if (nameId.value.length > 0 && (comboValue === 'Bean' || comboValue === 'Bean[]'))
    if (nameId.value.length > 0 && (comboValue === 'Bean' || comboValue === 'List<Bean>'))
    {
        var tbl = document.getElementById(type + "_" + nameId.value);
        if (tbl)
        {
            tbl.parentNode.parentNode.remove(tbl.parentNode);
        }
        AddBean(nameId.value, type);
    }
    else
    {
        var tbl = document.getElementById(type + "_" + nameId.value);
        if (tbl)
        {
            tbl.parentNode.parentNode.remove(tbl.parentNode);
        }
    }
//    }
    if (comboValue === 'date' || comboValue === 'date[]')
    {
        document.getElementById(formatId.id).value = "DD/MM/YYYY";
    }
    else if (comboValue === 'datetime' || comboValue === 'datetime[]')
    {
        document.getElementById(formatId.id).value = "DD/MM/YYYY 24HH:MM:SS";
    }
    else if (comboValue === 'time' || comboValue === 'time[]')
    {
        document.getElementById(formatId.id).value = "24HH:MM:SS";
    }
    else
    {
        document.getElementById(formatId.id).value = "-";
    }

    if (comboValue === 'int' || comboValue === 'long' || comboValue === 'float')
    {
        document.getElementById(defaultId.id).readOnly = false;
        document.getElementById(defaultId.id).value = "0";
    }
    else if (comboValue === 'double')
    {
        document.getElementById(defaultId.id).readOnly = false;
        document.getElementById(defaultId.id).value = "0.0";
    }
    else if (comboValue === 'boolean')
    {
        document.getElementById(defaultId.id).readOnly = false;
        document.getElementById(defaultId.id).value = "N";
    }
    else if (comboValue === 'String')
    {
        document.getElementById(defaultId.id).readOnly = false;
        document.getElementById(defaultId.id).value = "null";
    }
    else
    {
        document.getElementById(defaultId.id).readOnly = true;
        document.getElementById(defaultId.id).value = "null";
    }
}
function onChangeName(nameId, combo, type, tableName)
{

    var table = document.getElementById(tableName);
    var rowCount = table.rows.length;
    var celldata1;
    for (var i = 3; i < rowCount; i++)
    {
        celldata1 = table.rows[i].cells[2].getElementsByTagName("input")[0];
        if (celldata1.value === nameId.value && nameId.id !== celldata1.id)
        {
            alert("Duplicate Value found in 'Name'");
            nameId.value = "";
            document.getElementById(nameId.id).focus();
            return false;
        }
        if (nameId.value.endsWith("bean"))
        {
            nameId.value = nameId.value.replace("bean", "Bean");
        }
    }
    var comboValue = combo.value;

//    if (nameId.value.length > 0 && (comboValue === 'Bean' || comboValue === 'Bean[]'))
    if (nameId.value.length > 0 && (comboValue === 'Bean' || comboValue === 'List<Bean>'))
    {
        var tbl = document.getElementById(type + "_" + oldNameValue);
        if (tbl)
        {
            tbl.parentNode.parentNode.remove(tbl.parentNode);
        }
        AddBean(nameId.value, type);
    }
}
function onChangeDefaultValue(dafault_value, data_typeId)
{
    if (dafault_value.value.length > 0 && (document.getElementById(data_typeId.id).value === "String"
            || document.getElementById(data_typeId.id).value === "String[]"
            || document.getElementById(data_typeId.id).value === "date"
            || document.getElementById(data_typeId.id).value === "time"
            || document.getElementById(data_typeId.id).value === "datetime"
            || document.getElementById(data_typeId.id).value === "date[]"
            || document.getElementById(data_typeId.id).value === "time[]"
            || document.getElementById(data_typeId.id).value === "datetime[]"))
    {
        var str = dafault_value.value;
        str = str.replace(/[^a-zA-Z0-9]/g, " ");
        str = str.replace(/\s/g, '');
        document.getElementById(dafault_value.id).value = str;
    }
    if (dafault_value.value.length > 0 && (document.getElementById(data_typeId.id).value === "int"
            || document.getElementById(data_typeId.id).value === "int[]"
            || document.getElementById(data_typeId.id).value === "long"
            || document.getElementById(data_typeId.id).value === "long[]"))
    {
        var str = dafault_value.value;
        str = str.replace(/[^0-9]/g, " ");
        str = str.replace(/\s/g, '');
        document.getElementById(dafault_value.id).value = str;
    }
    if (dafault_value.value.length > 0 && (document.getElementById(data_typeId.id).value === "float"
            || document.getElementById(data_typeId.id).value === "float[]"
            || document.getElementById(data_typeId.id).value === "double"
            || document.getElementById(data_typeId.id).value === "double[]"))
    {
        var str = dafault_value.value;
        str = str.replace(/[^0-9.]/g, " ");
        str = str.replace(/\s/g, '');
        document.getElementById(dafault_value.id).value = str;
    }
    if (dafault_value.value.length > 0 && document.getElementById(data_typeId.id).value === "boolean")
    {
        var str = dafault_value.value;
        str = str.replace(/[^ynYN]/g, " ");
        str = str.replace(/\s/g, '');
        str = str.substr(0, 1).toUpperCase();
        document.getElementById(dafault_value.id).value = str;
    }
}
function toLowerFirstLetter(nameId)
{
    if (nameId.value.length > 0)
    {
        var str = nameId.value;
        str = str.replace(/[^a-zA-Z0-9]/g, " ");
        str = str.replace(/\s/g, '');
        var firstChar = str.substr(0, 1).toLowerCase();
        str = firstChar + str.substr(1);
        document.getElementById(nameId.id).value = str;
    }
}

function toUpperFirstLetter(nameId)
{
    if (nameId.value.length > 0)
    {
        var str = nameId.value;
        str = str.replace(/[^a-zA-Z]/g, " ");
        str = str.replace(/\s/g, '');
        var firstChar = str.substr(0, 1).toUpperCase();
        str = firstChar + str.substr(1);
        document.getElementById(nameId.id).value = str;
    }
}
function validateWebserviceName(nameId)
{
    if (nameId.value.length > 0)
    {
//        alert(nameId.value.endsWith("service"));
        if (!nameId.value.toString().toLowerCase().endsWith("service"))
        {
            alert("WebService Name should be ends with 'Service'");
            document.getElementById(nameId.id).focus();
            return false;
        }
        else if (nameId.value.toString().endsWith("service"))
        {
            var serviceName = document.getElementById(nameId.id).value;
            serviceName = serviceName.replace("service", "Service");
            document.getElementById(nameId.id).value = serviceName;
        }
    }
}
function refreshConsumerName(producerId)
{
    var producer = producerId.value;
    for (var i = 0; i < document.getElementById("consumerPro").length; i++)
    {
        if (producer === document.getElementById("consumerPro").options[i].value)
        {
            document.getElementById("consumerPro").options[i].style.display = "none";
            document.getElementById("consumerPro").options[i].disabled = true;
        }
        else
        {
            document.getElementById("consumerPro").options[i].style.display = "";
            document.getElementById("consumerPro").options[i].disabled = false;
        }
        document.getElementById("consumerPro").options[i].selected = false;
    }
    document.getElementById("inputconsumerPro").value = "Select options";
}
function onlyNumbersInForm(textBoxId)
{
    var objRegExp = new RegExp('[^0-9]', 'g');
    document.getElementById(textBoxId).value = (document.getElementById(textBoxId).value).replace(objRegExp, '');
}
function onlyNumbersOutForm(textBoxId)
{
    var objRegExp = new RegExp('[^0-1]', 'g');
    document.getElementById(textBoxId).value = (document.getElementById(textBoxId).value).replace(objRegExp, '');
}
function getOldValue(name)
{
    if (document.getElementById(name.id).value.length > 0)
    {
        oldNameValue = document.getElementById(name.id).value;
    }
}

function getLastCount(lastCount)
{
    if (lastCount.length > 0)
    {
        oldParamCount = lastCount;
    }
}
function firstupperCase(str)
{
    var firstChar = str.substr(0, 1).toUpperCase();
    str = firstChar + str.substr(1);
    return str;
}
function firstlowerCase(str)
{
    var firstChar = str.substr(0, 1).toLowerCase();
    str = firstChar + str.substr(1);
    return str;
}

function downloadFile(zipfile)
{
    getSynchronousData('webservicedoc.fin?cmdAction=getZipDoc&zipfile=' + zipfile, '', 'webservicepage');
}
function onChangeInCombo(comboId)
{
    if (comboId.value === 'Single' && document.getElementById("inParameters").value <= 1)
    {
        document.getElementById("inParamName").style.display = "none";
    }
    else
    {
        document.getElementById("inParamName").style.display = "";
    }
}

function onChangeOutCombo(comboId)
{
    if (comboId.value === 'Single' && document.getElementById("outParameters").value <= 1)
    {
        document.getElementById("outParamName").style.display = "none";
    }
    else
    {
        document.getElementById("outParamName").style.display = "";
    }
}

function serviceType(type)
{
    if (type == 'soap')
    {
        WebService();
    }
    else if (type == 'rest')
    {
        document.getElementById("webservicepage").style.display = "";
        document.getElementById("success").style.display = "none";
        getSynchronousData('webservicedoc.fin?cmdAction=getWebServiceForm', 'type=rest', 'webservicepage');
        document.getElementById("rdorest").checked = true;
    }
}

function validateRestWebserviceURL(nameId)
{
    var strvalue = document.getElementById(nameId.id).value.toString().split("/");
    var projectName = document.getElementById("producerPro");
    document.getElementById("projectName").value = projectName.options[projectName.selectedIndex].text;

    if (strvalue[0] != projectName.value.toString())
    {
        alert("WebService URL start with '" + projectName.value + "'.");
        document.getElementById(nameId.id).focus();
        return false;
    }
    if (strvalue[1] == undefined || strvalue[1] != "ws")
    {
        alert("WebService URL not well formed.");
        document.getElementById(nameId.id).focus();
        return false;
    }
//    var regex = /^v(\d+\.)?(\d+\.)?(\d+)$/;
//    if (strvalue[2] == undefined || regex.test(strvalue[2].toString()) != true)
//    {
//        alert("WebService URL not well formed.");
//        document.getElementById(nameId.id).focus();
//        return false;
//    }
//    if (strvalue[3] == undefined || strvalue[3].trim() == "")
//    {
//        alert("WebService URL not well formed.");
//        document.getElementById(nameId.id).focus();
//        return false;
//    }
    if (strvalue[2] == undefined || strvalue[2].trim() == "")
    {
        alert("WebService URL endpoint required.");
        document.getElementById(nameId.id).focus();
        return false;
    }
    var urlValue = document.getElementById(nameId.id).value.toString().substring(document.getElementById(nameId.id).value.toString().indexOf("/", document.getElementById(nameId.id).value.toString().indexOf("/") + 2) + 1);
    if (strvalue[2] != undefined && (urlValue.includes("add") || urlValue.includes("upadte") || urlValue.includes("delete") || urlValue.includes("get")
            || urlValue.includes("put") || urlValue.includes(".") || urlValue.includes(" ")))
    {
        if(urlValue.includes(" ")){
            alert("Space is not allow in WebService URL.");
        }else{
            alert("WebService URL not contain of such a keywords like add, delete, update, get, put and '.' .");
        }
        document.getElementById(nameId.id).focus();
        return false;
    }

    return true;
}

function validateRestWebserviceName() {
    $('#webserviceName').keydown(function (e) {
        // Allow: backspace, delete, tab, space
        if ($.inArray(e.keyCode, [46, 8, 9, 32]) !== -1 ||
                // Allow: Ctrl+A, Command+A
                        (e.keyCode === 65 && (e.ctrlKey === true || e.metaKey === true)) ||
                        // Allow: home, end, left, right, down, up
                                (e.keyCode >= 35 && e.keyCode <= 40)) {
                    // let it happen, don't do anything
                    return;
                }
                // Ensure that it is alphabets
                if (((e.keyCode < 65 || e.keyCode > 90)) && (e.keyCode >= 96 || e.keyCode <= 105)) {
                    e.preventDefault();
                }
            });
}

var inParamcount = 0;
var outParamcount = 0;
function insertParamRest(parameters, tableName, beanName, type)
{
    var count = 0;
    if (type == 'in')
    {
        rowId = parseInt(inParamcount) + 1;
        count = inParamcount;
    }
    else
    {
        rowId = parseInt(outParamcount) + 1;
        count = outParamcount;
    }
    var table = document.getElementById(tableName);

    if (parameters.length <= 0)
    {
        parameters = 0;
    }

    for (var j = table.rows.length - 1; j >= (3 + parseInt(parameters)); j--)
    {
        var bean_name = (document.getElementById(tableName).rows[j].getElementsByTagName("input")[0].value);
        var bean_datatype = (document.getElementById(tableName).rows[j].getElementsByTagName("select")[1].value);

        if (bean_datatype === 'Bean' || bean_datatype === 'List<Bean>')
        {
            var tbl = document.getElementById(type + "_" + bean_name);
            if (tbl)
            {
                tbl.parentNode.parentNode.remove(tbl.parentNode);
            }
        }
        table.deleteRow(j);
        if (type == 'in')
        {
            inParamcount--;
        }
        else
        {
            outParamcount--;
        }
    }
    for (var i = count; i < parameters; i++)
    {
        AddParam(tableName, type);
        if (type == 'in')
        {
            inParamcount++;
        }
        else
        {
            outParamcount++;
        }
    }
}

function setWebServiceURLName(project){
    document.getElementById("webserviceURL").value = project.value+"/ws/";
}