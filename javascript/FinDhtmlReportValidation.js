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

function showProjectSpecification()
{
    showHide("ProjectSpecification");
}

function validateProjectSpecification()
{
    if(document.getElementById('cmbProjectName').value == '-1')
    {
        alert("Please select project name");
        document.getElementById('cmbProjectName').focus();
        return false;
    }
    else if(document.getElementById('txtModuleName').value == '' || document.getElementById('txtModuleName').value.replace(/\s/g,'') == '')
    {
        alert("Please enter module name");
        document.getElementById('txtModuleName').focus();
        return false;
    }
    else if(!document.getElementById('txtModuleName').value.match(/^[A-Za-z0-9 ]+$/))
    {
        alert("Please enter [A-Za-z0-9 ] only in module name");
        document.getElementById('txtModuleName').focus();
        return false;
    }
    else if(document.getElementById('cmbAliasName').value == '-1')
    {
        alert("Please select alias name");        
        document.getElementById('cmbAliasName').focus();        
        return false;
    }
    else if(document.getElementById('txtReqNo').value != "" && !document.getElementById('txtReqNo').value.match(/^[0-9]+$/))
    {
        alert("Invalid request number! please enter numeric characters only");
        document.getElementById('txtReqNo').focus();
        return false;
    }
    else if(!document.getElementById('rdoAlias').checked && !document.getElementById('rdoDevServer').checked)
    {
        alert("Please select connection type");
        document.getElementById('rdoAlias').focus();
        return false;
    }
    else if(document.getElementById('rdoDevServer').checked && document.getElementById('cmbDevServer').value == '-1')
    {
        alert("Please select server type");
        document.getElementById('cmbDevServer').focus();
        return false;
    }
    else if(document.getElementById('txtQuery').value == '')
    {
        alert("Please enter query");
        document.getElementById('txtQuery').focus();
        return false;
    }
    else
    {
        document.getElementById("txtModuleName").value = document.getElementById("txtModuleName").value.split(' ').join('');
        var module = document.getElementById("txtModuleName").value;
        for (var i = 0; i < keywords.length; i++)
        {
            if (module == keywords[i])
            {
                alert("Module name cannot be any java keyword");
                document.getElementById("txtModuleName").focus();
                return false;
            }
        }
    }
    return true;
}

function showColumnSelection()
{
    if(document.getElementById("cmbRefNo").value != "")
    {
        if(validateSummaryReportData())
        {
            validateQueryAndAlias();
        }
    }
    else
    {
        if(!validateProjectSpecification())
        {
            return;    
        }
        document.getElementById("txtModuleName").value = document.getElementById("txtModuleName").value.split(' ').join('');
        validateQueryAndAlias();
    }
}

function validateQueryAndAlias()
{
    if(document.getElementById("divresult").innerHTML.match("Invalid Alias or Query") == null)
    {
        var len = document.getElementsByName("txtQuery").length;
        for(var i = 0; i < len; i++)
        {
            if(document.getElementsByName("txtQuery").item(i).value == "" && document.getElementsByName("cmbAliasName").item(i).value == "-1")
            {
                alert("Please enter or remove child query");
                return;
            }
            else if(document.getElementsByName("cmbAliasName").item(i).value == "-1")
            {
                alert("Please select child query alias");
                document.getElementsByName("cmbAliasName").item(i).focus();
                return;
            }
            else if(document.getElementsByName("txtQuery").item(i).value == "")
            {
                alert("Please enter child query");
                document.getElementsByName("txtQuery").item(i).focus();
                return;
            }
        }
        
        var mainQueryColumnlen = document.getElementsByName("cmbMainQueryColumn").length;
        for(i = 0; i < mainQueryColumnlen; i++)
        {
            if(document.getElementsByName("cmbMainQueryColumn").item(i).value == "-1" && document.getElementsByName("cmbChildQueryColumns").item(i).value == "-1")
            {
            }
            else
            {
                if(document.getElementsByName("cmbMainQueryColumn").item(i).value == "-1")
                {
                    alert("Please select main query column");
                    document.getElementsByName("cmbMainQueryColumn").item(i).focus();
                    return;
                }
                
                if(document.getElementsByName("cmbChildQueryColumns").item(i).value == "-1")
                {
                    alert("Please select child query column");
                    document.getElementsByName("cmbChildQueryColumns").item(i).focus();
                    return;
                }
            }
        }
    }
    if(getColumnDetails())
    {
        document.getElementById("chkSelectAll").checked = false;
        disableCombo();
        showHide("ColumnSelection");
    }
}

function getColumnDetails()
{
    if(document.getElementById("divresult").innerHTML.match("Invalid Alias or Query") == null)
    {
        var params=getFormData(document.MenuForm);
        getData_sync("repgenv3.fin?cmdAction=getColumnDetail", "divqueryresult", params, false);
        var qColumn = document.getElementById("divqueryresult").innerHTML;
        var column =  eval( "(" + qColumn + ")" );
        var selObj = document.getElementById('cmbShowColumns');
        selObj.innerHTML = "";
        for (var j=0; j<column.colNames.length; j++)
        {
            var options1 = document.createElement("OPTION");
            options1.value = column.colNames[j];
            options1.text = column.colNames[j];
            selObj.options.add(options1);
        }
        return true;
    }
    return false;
}

function getMainQueryColumns()
{
    var params=getFormData(document.MenuForm);
    params += "&queryIndex=";
    params += 0;
    if(document.getElementById("cmbAliasName").value == '-1')
    {
        alert("Please select alias name");
        document.getElementById("cmbAliasName").focus();
        return;
    }
    else if(document.getElementById("txtQuery").value == "" ||  document.getElementById("txtQuery").value.replace(/\s/g,'') == '')
    {
        alert("Please enter query");
        document.getElementById("txtQuery").focus();
        return;
    }
    if(checkQuery(params))
    {
        getDataFilterNew("repgenv3.fin?cmdAction=getColumnNames", "cmbTempColResult", params, false);
        if(document.getElementsByName("cmbMainQueryColumn") != null)
        {
            var len = document.getElementsByName("cmbMainQueryColumn").length;
            for(var i = 0; i < len; i++)
            {
                document.getElementsByName("cmbMainQueryColumn").item(i).innerHTML = document.getElementById("cmbTempColResult").innerHTML;
            }
        }
    }
    else
    {
        if(document.getElementsByName("cmbMainQueryColumn") != null)
        {
            len = document.getElementsByName("cmbMainQueryColumn").length;
            for(i = 0; i < len; i++)
            {
                document.getElementsByName("cmbMainQueryColumn").item(i).innerHTML = "";
            }
        }
    }
}

function getChildQueryColumns(elem)
{    
    var len = document.getElementsByName("txtQuery").length;
    for(var i = 1; i < len; i++)
    {
        if(document.getElementsByName("txtQuery").item(i) == elem)
        {
            if(document.getElementsByName("cmbAliasName").item(i).value == '-1')
            {
                alert("Please select child query alias name");
                document.getElementsByName("cmbAliasName").item(i).focus();
                return false;
            }
            else if(document.getElementsByName("txtQuery").item(i).value == "" ||  document.getElementsByName("txtQuery").item(i).value.replace(/\s/g,'') == '')
            {
                alert("Please enter child query");
                document.getElementsByName("txtQuery").item(i).focus();
                return false;
            }
            var params=getFormData(document.MenuForm);
            params += "&queryIndex=";
            params += i;            
            if(checkQuery(params))
            {
                getDataFilterNew("repgenv3.fin?cmdAction=getColumnNames", "cmbChildQueryColumn", params, false);
                return true;
            }
        }
    }
    return false;
}

function checkQuery(params)
{
    var str=document.getElementById("txtQuery").value;
    if(!document.getElementById("rdoAlias").checked && !document.getElementById("rdoDevServer").checked)
    {
        alert("Please select connection type");
        document.getElementById("rdoAlias").focus();
        return false;
    }
    else if(document.getElementById("rdoDevServer").checked && document.getElementById("cmbDevServer").value == "-1")
    {
        alert("Please select server type");
        document.getElementById("cmbDevServer").focus();
        return false;
    }
    else if(str.toLowerCase().match("where rownum") == null && str.toLowerCase().match("limit") == null)
    {
        var ans = confirm("Your query doesn't have any 'LIMIT' or 'WHERE ROWNUM < #' condition,which may take long time to load if it returns thousands of records. Are you sure you want to continue ?");
        if (ans == true)
        {
            getData_sync("repgenv3.fin?cmdAction=checkQuery", "divresult", params, false);
            if(document.getElementById("divresult").innerHTML.match("Invalid Alias or Query") == null)
            {
                return true;
            }
        }
        else
        {
            document.getElementById("txtQuery").focus();
            return false;
        }
    }
    else
    {
        getData_sync("repgenv3.fin?cmdAction=checkQuery", "divresult", params, false);
        if(document.getElementById("divresult").innerHTML.match("Invalid Alias or Query") == null)
        {
            return true;
        }
    }
    return false;
}

function disableCombo()
{
    if(document.getElementById("chkSelectAll").checked)
    {
        for(var i=0;i<document.getElementById("cmbShowColumns").options.length;i++)
        {
            document.getElementById('cmbShowColumns').options[i].selected = true;
        }
        document.getElementById("cmbShowColumns").disabled = true;
    }
    else
    {
        for(i=0;i<document.getElementById("cmbShowColumns").options.length;i++)
        {
            document.getElementById('cmbShowColumns').options[i].selected = false;
        }
        document.getElementById("cmbShowColumns").disabled = false;
    }
}

function showSRSSpecification()
{
    if(!validateReportColumns())
    {
        return;
    }
    showHide("SRSSpecification");
    
    getSelectedColumns("cmbGroupField");
    getSelectedColumns("cmbGrpFooterColumn");
    getSelectedColumns("cmbPageFooterColumn");
    getSelectedColumns("cmbColumnGrid");
    getAllColumns("cmbPrimaryKey");    
    getAllColumns("pieCmbXaxisColumn");    
    getAllColumns("pieCmbYaxisColumn");    
    getAllColumns("barCmbXaxisColumn");    
    getAllColumns("barCmbYaxisColumn");    
    getAllColumns("lineCmbXaxisColumn");    
    getAllColumns("lineCmbYaxisColumn");    
    getAllColumns("areaCmbXaxisColumn");    
    getAllColumns("areaCmbYaxisColumn");    
    if(document.getElementsByName("barCmbYaxisColumn") != undefined)
    {
        var len = document.getElementsByName("barCmbYaxisColumn").length;        
        for(i=0;i<len;i++){
            document.getElementsByName("barCmbYaxisColumn").item(i).innerHTML = document.getElementById("pieCmbXaxisColumn").innerHTML;        
        }
    }
    if(document.getElementsByName("lineCmbYaxisColumn") != undefined)
    {
        len = document.getElementsByName("lineCmbYaxisColumn").length;        
        for(i=0;i<len;i++){
            document.getElementsByName("lineCmbYaxisColumn").item(i).innerHTML = document.getElementById("pieCmbXaxisColumn").innerHTML;        
        }
    }
    if(document.getElementsByName("areaCmbYaxisColumn") != undefined)
    {
        len = document.getElementsByName("areaCmbYaxisColumn").length;        
        for(i=0;i<len;i++){
            document.getElementsByName("areaCmbYaxisColumn").item(i).innerHTML = document.getElementById("pieCmbXaxisColumn").innerHTML;        
        }
    }
}

function validateReportColumns()
{
    for(var i=0;i<document.getElementById("cmbShowColumns").options.length;i++)
    {
        if(document.getElementById('cmbShowColumns').options[i].selected)
        {
            break;
        }
    }
    if(i == document.getElementById("cmbShowColumns").options.length)
    {
        alert("Please select column(s) to add into report");
        document.getElementById('cmbShowColumns').focus();
        return false;
    }
    return true;
}


function getAllColumns(cmbToFill)
{
    var cmb = document.getElementById(cmbToFill);
    cmb.innerHTML = "";
    cmb.innerHTML = "<option value=\"-1\">-- Select Field --</option>";
    var selObj = document.getElementById('cmbShowColumns');
    for (var i=0; i<selObj.options.length; i++) {
        var options1 = document.createElement("OPTION");
        options1.value = selObj.options[i].value;
        options1.text =  selObj.options[i].value;
        cmb.options.add(options1);
    }
}

function showPrevSRSSpecification()
{
    showHide("SRSSpecification");
}

function getSelectedColumns(cmbToFill)
{
    var cmb = document.getElementById(cmbToFill);
    cmb.innerHTML = "";
    cmb.innerHTML = "<option value=\"-1\">-- Select Field --</option>";
    var selObj = document.getElementById('cmbShowColumns');
    for (var i=0; i<selObj.options.length; i++) {
        if (selObj.options[i].selected) {
            var options1 = document.createElement("OPTION");
            options1.value = selObj.options[i].value;
            options1.text =  selObj.options[i].value;
            cmb.options.add(options1);
        }
    }
}

function matchColumns(tablenm)
{
    var selObj = document.getElementById('cmbShowColumns');
    var table = document.getElementById(tablenm);
    var rowCount = table.rows.length;
    var flag = "notmatched";
    for(var j=2;j<rowCount;j++)
    {
        flag = "notmatched";
        for (var i=0; i<selObj.options.length; i++)
        {
            if (selObj.options[i].selected)
            {
                if(table.rows[j].cells[0].childNodes[0].data == selObj.options[i].value)
                {
                    flag = "matched";
                    break;
                }
            }
        }
        if(flag == "notmatched")
        {
            deleteRow(tablenm,table.rows[j].rowIndex);
        }
    }
}

function removeRptSpace(tabnm)
{
    document.getElementById(tabnm+"TxtId").value = document.getElementById(tabnm+"TxtLabel").value.split(' ').join('').toLowerCase();
    document.getElementById(tabnm+"TxtName").value = document.getElementById(tabnm+"TxtLabel").value.split(' ').join('').toLowerCase();
}

function onChangeGrouping()
{
    var ans = true;
    if(document.getElementById("chkGrouping").checked == true)
    {
        ans = confirm("You will loose pagging in report continue?");
    }    
    if(document.getElementById("chkGrouping").checked)
    {
        document.getElementById("divGrouping").style.display = "";
    }
    else
    {
        document.getElementById("divGrouping").style.display = "none";
        document.getElementById("divGroupFooter").style.display = "none";
    }    
    if(ans == false)
    {
        document.getElementById("chkGrouping").checked = false;
        document.getElementById("divGrouping").style.display = "none";
        document.getElementById("divGroupFooter").style.display = "none";
    }
} 

function onChangeGroupFooter()
{
    showHideOnChecked("chkGroupFooter","divGroupFooter");
}

function onAdd()
{
    if(document.getElementById('cmbGrpFooterColumn').value == '-1')
    {
        alert("Please select group footer column");
        document.getElementById('cmbGrpFooterColumn').focus();
        return false;
    }
    else if(document.getElementById('cmbGrpFooterCalculation').value == '-1')
    {
        alert("Please select group footer calculation");
        document.getElementById('cmbGrpFooterCalculation').focus();
        return false;
    }
    else
    {
        if(document.getElementById('cmbGrpFooterCalculation').value == "stat_count")
        {
            addNewRow('grpFooter','cmbGrpFooterColumn','cmbGrpFooterCalculation',gfid);
            document.getElementById('cmbGrpFooterColumn').value = '-1';
            document.getElementById('cmbGrpFooterCalculation').value = '-1';
        }
        else
        {
            var qColumn = document.getElementById("divqueryresult").innerHTML;
            var column =  eval( "(" + qColumn + ")" );
            for(var i=0;i<column.colNames.length;i++)
            {
                if(document.getElementById('cmbGrpFooterColumn').value == column.colNames[i])
                {
                    if(column.colTypes[i] == "Long" || column.colTypes[i] == "Integer" || column.colTypes[i] == "Double" || column.colTypes[i] == "Float" || column.colTypes[i] == "Number" || column.colTypes[i] == "BigDecimal")
                    {
                        addNewRow('grpFooter','cmbGrpFooterColumn','cmbGrpFooterCalculation',gfid);
                        document.getElementById('cmbGrpFooterColumn').value = '-1';
                        document.getElementById('cmbGrpFooterCalculation').value = '-1';
                    }
                    else
                    {
                        alert("Can't apply this operation.");
                    }
                }
            }
        }
        return true;
    }
}

function validateSRSSpecification()
{
    if(document.getElementById('txtProblemStmt').value == '')
    {
        document.getElementById('txtProblemStmt').value = 'N/A';
    }
    if(document.getElementById('txtSolution').value == '')
    {
        document.getElementById('txtSolution').value = 'N/A';
    }
    if(document.getElementById('txtExistingPractice').value == '')
    {
        document.getElementById('txtExistingPractice').value = 'N/A';
    }
    if(document.getElementById('txtPlacement').value == '')
    {
        document.getElementById('txtPlacement').value = 'N/A';
    }
    return true;
}

function validateTabSelection()
{
    if(document.getElementById('txtReportTitle').value == '')
    {
        alert("Please enter report title");
        document.getElementById('txtReportTitle').focus();
        return false;
    }
    var cnt = 0;
    if(document.getElementById('chkReportType').checked)
    {
        cnt++;
    }
    if(document.getElementById('chkFilters').checked)
    {
        cnt++;
    }
    if(document.getElementById('chkExport').checked)
    {
        cnt++;
    }
    if(document.getElementById('chkChart').checked)
    {
        cnt++;
    }
    if(document.getElementById('chkColumns').checked)
    {
        cnt++;
    }
    if(cnt == 0)
    {
        alert("Please select atleast 1 tab");
        return false;
    }  
    if(document.getElementById('txtMethodNm').value == '')
    {
        alert("Please enter methodname resolver");
        document.getElementById('txtMethodNm').focus();
        return false;
    }    
    return true;
}

function showTabSelection()
{
    if(validateSRSSpecification())
    {
        showHide("TabSelection");
    }
}

function show(tab)
{
    document.getElementById("type").style.display = "none";
    document.getElementById("filter").style.display = "none";
    document.getElementById("export").style.display = "none";
    document.getElementById("chart").style.display = "none";
    if(tab != null)
    {
        document.getElementById(tab).style.display = "";
    }
    else
    {
        showHide("PageFooter");
    }
}

function showTab()
{
    var reporttype="<li class='' ><a rel='rel0' href='javascript:show(\"type\");' onclick=''>Report Type</a></li>";
    var filter="<li class='' ><a rel='rel0' href='javascript:show(\"filter\");' onclick=''>Filter</a></li>";
    var Export="<li class='' ><a rel='rel0' href='javascript:show(\"export\");' onclick=''>Export</a></li>";
    var chart="<li class='' ><a rel='rel0' href='javascript:show(\"chart\");' onclick=''>Chart</a></li>";
    var tab = null;
    document.getElementById("maintab0").innerHTML ="";
    if(document.getElementById("chkReportType").checked)
    {
        document.getElementById("maintab0").innerHTML = reporttype;
        tab = "type";
    }
    if(document.getElementById("chkFilters").checked)
    {
        document.getElementById("maintab0").innerHTML += filter;
        if(tab == null)
        {
            tab = "filter";
        }
    }
    if(document.getElementById("chkExport").checked)
    {
        document.getElementById("maintab0").innerHTML += Export;
        if(tab == null)
        {
            tab = "export";
        }
    
    }
    if(document.getElementById("chkChart").checked)
    {
        document.getElementById("maintab0").innerHTML += chart;
        if(tab == null)
        {
            tab = "chart";
        }
    }
    show(tab);
}

function showReportData()
{    
    matchColumns('columnControlListTable');
    if(document.getElementById("cmbRefNo").value != "")
    {
        if(validateSummaryReportTabData())
        {
            showHideReportData();
        }        
    }
    else
    {
        if(validateTabSelection())
        {
            showHideReportData();
        }        
    }    
    setGroupFooterCalc();
}

function showHideReportData()
{
    if(document.getElementById("chkReportType").checked ||
        document.getElementById("chkFilters").checked ||
        document.getElementById("chkExport").checked ||
        document.getElementById("chkChart").checked)
        {
        showTab();            
        showHide("ReportDataSelection");
    }
    else
    {
        showHide("PageFooter");
    }
}

function showPrevReportData()
{
    if(document.getElementById("chkReportType").checked ||
        document.getElementById("chkFilters").checked ||
        document.getElementById("chkExport").checked ||
        document.getElementById("chkChart").checked)
        {
        showTab();
        showHide("ReportDataSelection");
    }
    else
    {
        showHide("TabSelection");
    }
}

function showFinish()
{
    if(validatePageFooter())
    {
        showHide("Finish");
    }    
}

function showPageFooter()
{
    matchColumns('grpFooterListTable');
    if(document.getElementById("cmbRefNo").value == "")
    {
        if(!validateTab())
        {
            return;
        }
    }
    if(document.getElementById("chkExport").checked)
    {
        if(!document.getElementById("chkOnScreen").checked && !document.getElementById("chkPDF").checked && !document.getElementById("chkExcel").checked)
        {
            alert("You must select one choice for export data..");
            show("export");
            return false;
        }
    }
    if(document.getElementById("cmbRefNo").value == "")
    {
        
    
        if(document.getElementById("chkChart").checked)
        {        
            if(!document.getElementById("noChart").checked && !document.getElementById("pieChart").checked && !document.getElementById("barChart").checked && !document.getElementById("lineChart").checked && !document.getElementById("areaChart").checked)
            {
                alert("You must choose atleast one option for chart..");
                show("chart");
                return false;
            }        
            else if(validateChartData("pie"))
            {
                if(validateChartData("bar"))
                {
                    if(validateChartData("line"))
                    {
                        if(validateChartData("area"))
                        {
                            showHide("PageFooter");
                            return true;
                        }
                        return false;
                    }
                    return false;
                }
                return false;
            }
            return false;
        }
    }
    showHide("PageFooter");
    return true;
}

function validateTab()
{
    if(document.getElementById("chkReportType").checked)
    {
        if(document.getElementById("rptListTable").rows.length <= 2)
        {
            alert("You must enter data for report type..");
            show("type");
            return false;
        }
        else
        {
            if(document.getElementById("cmbRptControlPos").value == "-1")
            {
                alert("Please select control position");
                show("type");
                document.getElementById("cmbRptControlPos").focus();
                return false;
            }
        }
    }
    if(document.getElementById("chkFilters").checked)
    {
        if(document.getElementById("fltrListTable").rows.length <= 2)
        {
            alert("You must enter data for filter..");
            show("filter");
            return false;
        }
        else
        {
            if(document.getElementById("cmbControlPos").value == "-1")
            {
                alert("Please select control position");
                show("filter");
                document.getElementById("cmbControlPos").focus();
                return false;
            }
        }
    }
    return true;
}


function showRptCodeProperty()
{
    showHideOnChecked("rptchkCode","rptCodeProperty");
}

function showRptSRSProperty()
{
    showHideOnChecked("chkSRS","SRSProperty");
}

function showHideOnChecked(controlnm,divnm)
{
    if(document.getElementById(controlnm).checked)
    {
        document.getElementById(divnm).style.display = "";
    }
    else
    {
        document.getElementById(divnm).style.display = "none";
    }
}

function validateControl(tabnm)
{
    if (document.getElementById(tabnm+"CmbControl").value == "ComboBox" || document.getElementById(tabnm+"CmbControl").value == "TextLikeCombo")
    {
        document.getElementById(tabnm+"LenReadOnly").style.display = "none";
        document.getElementById(tabnm+"AlignVal").style.display = "none";
        if(document.getElementById(tabnm+"CmbControl").value == "ComboBox")
        {
            document.getElementById(tabnm+"MultipleLbl").style.display = "";
            document.getElementById(tabnm+"MultipleVal").style.display = "";
        }
        else
        {
            document.getElementById(tabnm+"MultipleLbl").style.display = "none";
            document.getElementById(tabnm+"MultipleVal").style.display = "none";
        }          
        
        document.getElementById(tabnm+"RowCol").style.display = "none";
        document.getElementById(tabnm+"CheckedLbl").style.display = "none";
        document.getElementById(tabnm+"CheckedVal").style.display = "none";
        document.getElementById(tabnm+"RdoProp").style.display = "none";
        document.getElementById(tabnm+"RdoValues").style.display = "none";
        document.getElementById(tabnm+"RdoCaptions").style.display = "none";
        document.getElementById(tabnm+"RdoDefaultValue").style.display = "none";
        document.getElementById(tabnm+"StyleSize").style.display = "";
        document.getElementById(tabnm+"ClassLbl").style.display = "";
        document.getElementById(tabnm+"ClassVal").style.display = "";
        if(tabnm == "fltr")
        {          
            document.getElementById(tabnm+"ComboFill").style.display = ""; 
            //            document.getElementById(tabnm+"chkForDependentCombo").checked=false;
            
            if(document.getElementById(tabnm+"chkDataSource").checked)
            {
                document.getElementById(tabnm+"IndepSource").style.display = ""; 
            }
            if(document.getElementById(tabnm+"RdoSrcStatic").checked)
            {
                document.getElementById(tabnm+"IndepSrcStatic").style.display = ""; 
            }
            if(document.getElementById(tabnm+"RdoSrcQuery").checked)
            {
                document.getElementById(tabnm+"IndepSrcQuery").style.display = "";            
            }       
            if(document.getElementById(tabnm+"RdoSrcWS").checked)
            {
                document.getElementById(tabnm+"IndepSrcWS").style.display = "";            
            }           
            if(document.getElementById(tabnm+"RdoSrcCommonCmb").checked)
            {
                document.getElementById(tabnm+"IndepSrcCommonCmb").style.display = "";            
            }
            if(document.getElementById(tabnm+"chkForDependentCombo").checked)
            {
                document.getElementById(tabnm+"DepCombo").style.display = "";            
            }
        }
    }
    else if (document.getElementById(tabnm+"CmbControl").value == "TextArea")
    {
        document.getElementById(tabnm+"LenReadOnly").style.display = "none";
        document.getElementById(tabnm+"AlignVal").style.display = "none";
        document.getElementById(tabnm+"MultipleLbl").style.display = "none";
        document.getElementById(tabnm+"MultipleVal").style.display = "none";
        document.getElementById(tabnm+"RowCol").style.display = "";
        document.getElementById(tabnm+"CheckedLbl").style.display = "none";
        document.getElementById(tabnm+"CheckedVal").style.display = "none";        
        document.getElementById(tabnm+"RdoProp").style.display = "none";
        document.getElementById(tabnm+"RdoValues").style.display = "none";
        document.getElementById(tabnm+"RdoCaptions").style.display = "none";
        document.getElementById(tabnm+"RdoDefaultValue").style.display = "none";
        document.getElementById(tabnm+"StyleSize").style.display = "";
        document.getElementById(tabnm+"ClassLbl").style.display = "";
        document.getElementById(tabnm+"ClassVal").style.display = "";
        document.getElementById(tabnm+"DepCombo").style.display = "none";            

        if(tabnm == "fltr")
        {
            document.getElementById(tabnm+"ComboFill").style.display = "none";  
            document.getElementById(tabnm+"DepCombo").style.display = "none";
        }
    }
    else if (document.getElementById(tabnm+"CmbControl").value == "CheckBox")
    {
        document.getElementById(tabnm+"LenReadOnly").style.display = "none";
        document.getElementById(tabnm+"AlignVal").style.display = "none";
        document.getElementById(tabnm+"MultipleLbl").style.display = "none";
        document.getElementById(tabnm+"MultipleVal").style.display = "none";
        document.getElementById(tabnm+"RowCol").style.display = "none";
        document.getElementById(tabnm+"CheckedLbl").style.display = "";
        document.getElementById(tabnm+"CheckedVal").style.display = "";
        document.getElementById(tabnm+"RdoProp").style.display = "none";
        document.getElementById(tabnm+"RdoValues").style.display = "none";
        document.getElementById(tabnm+"RdoCaptions").style.display = "none";
        document.getElementById(tabnm+"RdoDefaultValue").style.display = "none";
        document.getElementById(tabnm+"StyleSize").style.display = "";
        document.getElementById(tabnm+"ClassLbl").style.display = "";
        document.getElementById(tabnm+"ClassVal").style.display = "";
        document.getElementById(tabnm+"DepCombo").style.display = "none";            
        if(tabnm == "fltr")
        {
            document.getElementById(tabnm+"ComboFill").style.display = "none"; 
            document.getElementById(tabnm+"DepCombo").style.display = "none";
        }
    }
    else if (document.getElementById(tabnm+"CmbControl").value == "Radio")
    {
        document.getElementById(tabnm+"LenReadOnly").style.display = "none";
        document.getElementById(tabnm+"AlignVal").style.display = "none";
        document.getElementById(tabnm+"MultipleLbl").style.display = "none";
        document.getElementById(tabnm+"MultipleVal").style.display = "none";
        document.getElementById(tabnm+"RowCol").style.display = "none";
        document.getElementById(tabnm+"CheckedLbl").style.display = "none";
        document.getElementById(tabnm+"CheckedVal").style.display = "none";
        document.getElementById(tabnm+"RdoProp").style.display = "";
        document.getElementById(tabnm+"RdoValues").style.display = "";
        document.getElementById(tabnm+"RdoCaptions").style.display = "";
        document.getElementById(tabnm+"RdoDefaultValue").style.display = "";
        document.getElementById(tabnm+"StyleSize").style.display = "";
        document.getElementById(tabnm+"ClassLbl").style.display = "";
        document.getElementById(tabnm+"ClassVal").style.display = "";
        document.getElementById(tabnm+"DepCombo").style.display = "none";            
        if(tabnm == "fltr")
        {
            document.getElementById(tabnm+"ComboFill").style.display = "none"; 
            document.getElementById(tabnm+"DepCombo").style.display = "none";
        }
    }
    else if (document.getElementById(tabnm+"CmbControl").value == "DatePicker")
    {
        document.getElementById(tabnm+"LenReadOnly").style.display = "none";
        document.getElementById(tabnm+"AlignVal").style.display = "none";
        document.getElementById(tabnm+"MultipleLbl").style.display = "none";
        document.getElementById(tabnm+"MultipleVal").style.display = "none";
        document.getElementById(tabnm+"RowCol").style.display = "none";
        document.getElementById(tabnm+"CheckedLbl").style.display = "none";
        document.getElementById(tabnm+"CheckedVal").style.display = "none";
        document.getElementById(tabnm+"RdoProp").style.display = "none";
        document.getElementById(tabnm+"RdoValues").style.display = "none";
        document.getElementById(tabnm+"RdoCaptions").style.display = "none";
        document.getElementById(tabnm+"RdoDefaultValue").style.display = "none";
        document.getElementById(tabnm+"StyleSize").style.display = "none";
        document.getElementById(tabnm+"ClassLbl").style.display = "none";
        document.getElementById(tabnm+"ClassVal").style.display = "none";
        document.getElementById(tabnm+"DepCombo").style.display = "none";
        if(tabnm == "fltr")
        {
            document.getElementById(tabnm+"ComboFill").style.display = "none";                
            document.getElementById(tabnm+"DepCombo").style.display = "none";
        }
    }
    else
    {
        document.getElementById(tabnm+"LenReadOnly").style.display = "";
        document.getElementById(tabnm+"AlignVal").style.display = "";
        document.getElementById(tabnm+"MultipleLbl").style.display = "none";
        document.getElementById(tabnm+"MultipleVal").style.display = "none";
        document.getElementById(tabnm+"RowCol").style.display = "none";
        document.getElementById(tabnm+"CheckedLbl").style.display = "none";
        document.getElementById(tabnm+"CheckedVal").style.display = "none";        
        document.getElementById(tabnm+"RdoProp").style.display = "none";
        document.getElementById(tabnm+"RdoValues").style.display = "none";
        document.getElementById(tabnm+"RdoCaptions").style.display = "none";
        document.getElementById(tabnm+"RdoDefaultValue").style.display = "none";
        document.getElementById(tabnm+"StyleSize").style.display = "";
        document.getElementById(tabnm+"ClassLbl").style.display = "";
        document.getElementById(tabnm+"ClassVal").style.display = "";
        if(tabnm == "fltr")
        {
            document.getElementById(tabnm+"ComboFill").style.display = "none";                
            document.getElementById(tabnm+"DepCombo").style.display = "none";
        }
    }
    validateControlValidation(tabnm);     
    document.getElementById(tabnm+"DivProperties").style.display = "";
}

function validateTabData(tabnm)
{    
    if(document.getElementById(tabnm+"TxtLabel").value == "")
    {
        alert("Please enter label");
        document.getElementById(tabnm+"TxtLabel").focus();
        return false;
    }
    else if(document.getElementById(tabnm+"CmbControl").value == "-1")
    {
        alert("Please select control");
        document.getElementById(tabnm+"CmbControl").focus();
        return false;
    }
    else if(document.getElementById(tabnm+"TxtTabIndex").value == "")
    {
        alert("Please enter tabindex");
        document.getElementById(tabnm+"TxtTabIndex").focus();
        return false;
    }
    else if(document.getElementById(tabnm+"TxtId").value == "")
    {
        alert("Please enter control id");
        document.getElementById(tabnm+"TxtId").focus();
        return false;
    }
    else if(document.getElementById(tabnm+"TxtName").value == "")
    {
        alert("Please enter control name");
        document.getElementById(tabnm+"TxtName").focus();
        return false;
    } 
    else if(tabnm == "fltr" && (document.getElementById(tabnm+"CmbControl").value == "ComboBox" || document.getElementById(tabnm+"CmbControl").value == "TextLikeCombo"))
    {
        if(validateComboFill(tabnm))
        {
            addNewRptFltrRow('fltr',fltrid);
        }
    }   
    else
    {        
        if(validateTabIndex(tabnm+"TxtTabIndex") && validateNoOfRadio(tabnm))  //&& validateStaticDataFormate(tabnm)
        {
            if(tabnm == "rpt")
            {                
                addNewRptFltrRow('rpt',rptid);
            }
            else if(tabnm == "fltr" && validateComboFill(tabnm))
            {
                addNewRptFltrRow('fltr',fltrid);
            }            
        }
    }
    return true;
}

function showFltrCodeProperty()
{
    showHideOnChecked("fltrchkCode","fltrCodeProperty");
}

function showFltrSRSProperty()
{
    showHideOnChecked("chkfSRS","SRSfProperty");
}

function onFinish()
{
    //adding selected columns
    var text = "";
    var selectedCol = document.getElementById("cmbShowColumns").options.length;
    for(var i=0; i<selectedCol; i++)
    {
        if(document.getElementById("cmbShowColumns").options[i].selected)
        {
            text += "<input type='hidden' name='cmbTabColumn' value='" + document.getElementById("cmbShowColumns").options[i].value +"'>";
        }
    }
    document.getElementById("lstCol").innerHTML = text;
    //adding column names and types
    var allcolumn = document.createElement("div");
    var allcolumntypes =document.createElement("div");
    var qColumn = document.getElementById("divqueryresult").innerHTML;
    var column =  eval( "(" + qColumn + ")" );
    for(i=0; i<column.colNames.length; i++)
    {
        allcolumn.innerHTML += "<input type='hidden' id='AllColumns' name='hdnAllColumns' value='" + column.colNames[i] +"'>";
        allcolumntypes.innerHTML += "<input type='hidden' id='AllDataTypes' name='hdnAllDataTypes' value='" + column.colTypes[i] +"'>";
    }
    document.getElementById("hdngrpFooter").appendChild(allcolumn);
    document.getElementById("hdngrpFooter").appendChild(allcolumntypes);
    
    if(!document.getElementById("chkExport").checked)
    {
        document.getElementById("chkOnScreen").checked = true;
    }
    
    var pagefootercol = document.createElement("div");
    pagefootercol.innerHTML = "";
    for(i=0;i<document.getElementById("cmbPageFooterColumn").length;i++)
    {
        if(document.getElementById("cmbPageFooterColumn")[i].selected)
        {
            pagefootercol.innerHTML += "<input type='hidden' name='hdnPageFooterColumns' id='hdnPageFooterColumns' value='"+ document.getElementById("cmbPageFooterColumn")[i].value +"'/>";
        }
    }
    document.getElementById("hdngrpFooter").appendChild(pagefootercol);
    
    var params=getFormData(document.MenuForm);
    getData_sync("repgenv3.fin?cmdAction=setFinish", "divFinish", params, false);
    
    if(document.getElementById("divFinish").innerHTML.match(".zip"))
    {
            document.getElementById("btnFinish").disabled = true;
    }
    else
    {
        alert("You have error in code generation");
    }
}

function chkRowExists(tablenm,column1)
{
    try
    {
        var newData,rowData;
        var table = document.getElementById(tablenm);
        var rowCount = table.rows.length;
        newData = document.getElementById(column1).value;
        for(var i=2; i<rowCount; i++)
        {
            rowData = table.rows[i].cells[0].childNodes[0].data;
            if(rowData == newData)
            {
                if(tablenm == "grpFooterListtable")
                {
                    alert("Can't apply more than one operation on same field");
                }
                else
                {
                    alert("Item already exist");
                }
                
                return false;
            }
        }
        return true;
    }
    catch(e)
    {
        alert("Error: " + e);
    }
}

function showtTotalCols()
{
    if(document.getElementById("chkPageFooter").checked  || document.getElementById("chkGrandTotal").checked)
    {
        document.getElementById("divPageFooterColumn").style.display = "";
    }
    else
    {
        document.getElementById("divPageFooterColumn").style.display = "none";
    }
}

function showHide(hideDivId)
{
    var vDivIds=new Array("ProjectSpecification",
        "ColumnSelection",
        "SRSSpecification",
        "TabSelection",
        "ReportDataSelection",
        "Finish",
        "PageFooter"
        );
    
    for(var i=0;i<vDivIds.length;i++)
    {
        if(hideDivId == vDivIds[i])
        {
            document.getElementById(vDivIds[i]).style.display = "";
        }
        else
        {
            document.getElementById(vDivIds[i]).style.display = "none";
        }
    }
}

function validateColumnControl()
{
    if(document.getElementById("cmbColumnGrid").value == "-1")
    {
        alert("Please select column");
        document.getElementById("cmbColumnGrid").focus();
        return false;
    }
    else if(document.getElementById("cmbColumnControl").value == "-1")
    {
        alert("Please select control");
        document.getElementById("cmbColumnControl").focus();
        return false;
    }
    else
    {
        addNewRow('columnControl','cmbColumnGrid','cmbColumnControl',ccid);
    }
    return true;
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

var child_query_counter = 0;
function showAddMoreQuery()
{
    if(document.getElementById("divresult").innerHTML.match("Invalid Alias or Query") == null)
    {
        //tr for alias name
        var trAlias = document.createElement('tr');
        var tdAliasLbl = document.createElement('td');
        tdAliasLbl.innerHTML = "Alias Name :";
        tdAliasLbl.align = "right";
        
        var tdAliasCntrl = document.createElement('td');
        var combo_box = document.createElement('select');
        combo_box.id = "cmbChildAlias"+ child_query_counter;
        combo_box.name = "cmbAliasName";
        combo_box.innerHTML = document.getElementById("cmbAliasName").innerHTML;
        tdAliasCntrl.appendChild(combo_box);
        
        //main query column combo
        var tdMainQueryColumnCntrl = document.createElement('td');
        tdMainQueryColumnCntrl.innerHTML = "MainQueryColumn : ";
        var main_query_result_combo = document.createElement('select');
        main_query_result_combo.id = "cmbMainQueryColumn"+child_query_counter;
        main_query_result_combo.name = "cmbMainQueryColumn";
        main_query_result_combo.style.width = "150px";
        main_query_result_combo.innerHTML = document.getElementById("cmbTempColResult").innerHTML;
        tdMainQueryColumnCntrl.appendChild(main_query_result_combo);
        
        trAlias.appendChild(tdAliasLbl);
        trAlias.appendChild(tdAliasCntrl);
        trAlias.appendChild(tdMainQueryColumnCntrl);
        document.getElementById("addMoreQuery").appendChild(trAlias);
        
        //tr for query
        var trQuery = document.createElement('tr');
        var tdQueryLbl = document.createElement('td');
        tdQueryLbl.innerHTML = "Child Query :";
        tdQueryLbl.align = "right";
        
        var tdQueryCntrl = document.createElement('td');
        var text_area = document.createElement('textarea');
        text_area.name = "txtQuery";
        text_area.onblur = function()
        {
            var tabel= document.getElementById("tblprojectmodule");
            var rowid = this.parentNode.parentNode.rowIndex;
            if(getChildQueryColumns(this))
            {
                tabel.rows[rowid].cells[2].childNodes[1].innerHTML = document.getElementById("cmbChildQueryColumn").innerHTML;
            }
            else
            {
                tabel.rows[rowid].cells[2].childNodes[1].innerHTML = "";
            //this.focus();
            }
        };
        tdQueryCntrl.appendChild(text_area);
        
        //child query column combo
        var tdQueryResultCntrl = document.createElement('td');
        tdQueryResultCntrl.innerHTML = "ChildQueryColumn : ";
        var query_result_combo = document.createElement('select');
        query_result_combo.id = "cmbChildQueryColumns"+child_query_counter;
        query_result_combo.name = "cmbChildQueryColumns";
        query_result_combo.style.width = "150px";
        tdQueryResultCntrl.appendChild(query_result_combo);
        
        trQuery.appendChild(tdQueryLbl);
        trQuery.appendChild(tdQueryCntrl);
        trQuery.appendChild(tdQueryResultCntrl);
        
        document.getElementById("addMoreQuery").appendChild(trQuery);
        
        //for delete query button
        var delTR = document.createElement('tr');
        var delTD1 = document.createElement('td');
        var delTD2 = document.createElement('td');
        delTD2.align = "center";
        var btnRem = document.createElement('input');
        btnRem.value = "Remove";
        btnRem.type = "button";
        btnRem.className = "button";
        btnRem.style.width = "100px";
        btnRem.onclick = function()
        {
            var idx = this.parentNode.parentNode.rowIndex - 11;
            document.getElementById("addMoreQuery").deleteRow(idx--);
            document.getElementById("addMoreQuery").deleteRow(idx--);
            document.getElementById("addMoreQuery").deleteRow(idx);
        };
        delTD2.appendChild(btnRem);
        delTR.appendChild(delTD1);
        delTR.appendChild(delTD2);
        
        document.getElementById("addMoreQuery").appendChild(delTR);
        loadComboNew("cmbMainQueryColumn"+child_query_counter,"","","MenuForm");
        loadComboNew("cmbChildAlias"+child_query_counter,"","","MenuForm");
        loadComboNew("cmbChildQueryColumns"+child_query_counter,"","","MenuForm");
        child_query_counter++;
    }
}

function showDevServer(controlnm)
{
    if(document.getElementById(controlnm).checked && controlnm == 'rdoDevServer')
    {
        document.getElementById("cmbDevServer").disabled = false;
        document.getElementById("divConNote").style.display = "";
    }
    else
    {
        document.getElementById("cmbDevServer").options[0].selected = true;
        document.getElementById("cmbDevServer").disabled = true;
        document.getElementById("divConNote").style.display = "none";
    }
    onChageServerType();
}

function onChageServerType()
{
    var tblChildQuery = document.getElementById("addMoreQuery");
    tblChildQuery.innerHTML = "";
    
    document.getElementById("cmbAliasName").options[0].selected = true;
    document.getElementById("txtQuery").value = "";
    document.getElementById("divresult").innerHTML = "";
    document.getElementById("cmbAliasName").focus();
}

var ccid = 301;
var gfid = 201;

function addNewRow(listnm,control1,control2,divid)
{
    document.getElementById(listnm+'List').style.display = "";
    if(chkRowExists(listnm+"ListTable",control1) == false)
    {
        return;
    }
    var table = document.getElementById(listnm+'ListTable');
    var rowCount = table.rows.length;
    var row = table.insertRow(rowCount);
    
    var newCell0 = row.insertCell(0);    
    var newCell1 = row.insertCell(1);
    var newCell2 = row.insertCell(2);
    var newCell3 = row.insertCell(3);
    var newCell4 = row.insertCell(4);
    newCell4.style.display = "none";
    
    newCell0.innerHTML = document.getElementById(control1).value;    
    if(control2 == "cmbColumnControl" || control2 == "cmbGrpFooterCalculation")
    {
        var cntrl = document.getElementById(control2);
        newCell1.innerHTML = cntrl.options[cntrl.selectedIndex].text;        
    }
    else
    {        
        newCell1.innerHTML = document.getElementById(control2).value;
    }
    //newCell1.innerHTML = document.getElementById(control2).value;
    newCell2.innerHTML = "<a onclick=\"javascript:editRow(this.parentNode.parentNode.rowIndex,'"+control1+"','"+control2+"','"+listnm+"')\"><img style=\"width:20px\" align =\"middle\" src=\"images/edit.gif\" alt=\"Edit\"></a>";
    newCell3.innerHTML = "<a onclick=\"javascript:deleteRow('"+listnm+"ListTable',this.parentNode.parentNode.rowIndex)\"><img style=\"width:20px\" align='center' src=\"images/delete.gif\" alt=\"Delete\"></a>";
    newCell4.innerHTML = divid;
    
    var columnControlMainDiv = document.getElementById("hdn"+listnm);
    var ccdiv = document.createElement("div");
    ccdiv.id = divid;
    ccdiv.innerHTML =  "<input type='hidden' name=\"hdn"+control1+"\" id=\"hdn"+control1+"\" value='"+document.getElementById(control1).value +"'/>";
    ccdiv.innerHTML += "<input type='hidden' name=\"hdn"+control2+"\" id=\"hdn"+control2+"\" value='"+document.getElementById(control2).value +"'/>";
    
    columnControlMainDiv.appendChild(ccdiv);
    if(listnm == "columnControl")
    {
        ccid++;
    }
    else
    {
        gfid++;
    }
    
    clearControl(control1,control2);
}

function editRow(rowid,control1,control2,listnm)
{
    document.getElementById(control1).value = document.getElementById(listnm+'ListTable').rows[rowid].cells[0].childNodes[0].data;
    document.getElementById(control2).value = document.getElementById(listnm+'ListTable').rows[rowid].cells[1].childNodes[0].data;
    
    deleteRow(listnm+"ListTable",rowid);
}

function clearControl(control1,control2)
{
    document.getElementById(control1).value = "-1";
    document.getElementById(control2).value = "-1";
}
var rptid = 2;
var fltrid = 101;
function addNewRptFltrRow(tabnm,divid)
{
    document.getElementById(tabnm+'List').style.display = "";
    
    if(!chkRowExists(tabnm+"ListTable",tabnm+"TxtLabel"))
    {
        return;
    }
    if(!validateCntrlIDName(tabnm))
    {
        return;
    }
    
    var id = document.getElementById(tabnm+"TxtId").value;
    var name = document.getElementById(tabnm+"TxtName").value;
    if(checkID(id))
    {
        alert("You cannot enter duplicate control id");
        document.getElementById(tabnm+"chkCode").checked = true;
        showHideOnChecked(tabnm+"chkCode", tabnm+"CodeProperty");
        document.getElementById(tabnm+"TxtId").focus();
        return;
    }
    if(checkControlName(name))
    {
        alert("You cannot enter duplicate control name");
        document.getElementById(tabnm+"chkCode").checked = true;
        showHideOnChecked(tabnm+"chkCode", tabnm+"CodeProperty");
        document.getElementById(tabnm+"TxtName").focus();
        return;
    }
    var table = document.getElementById(tabnm+'ListTable');
    var rowCount = table.rows.length;
    //for duplicate tabindex
    var tabIdx = document.getElementById(tabnm+"TxtTabIndex").value;
    for (var i = 2; i < rowCount; i++)
    {
        var rowtab = table.rows[i].cells[2].innerHTML;
        if (parseInt(tabIdx, 10) == rowtab)
        {
            alert("Duplicate tab index");
            document.getElementById(tabnm+"TxtTabIndex").focus();
            return;
        }
    }
    var row = table.insertRow(rowCount);
    
    var newCell0 = row.insertCell(0);
    var newCell1 = row.insertCell(1);
    var newCell2 = row.insertCell(2);
    var newCell3 = row.insertCell(3);
    var newCell4 = row.insertCell(4);
    var newCell5 = row.insertCell(5);
    newCell5.style.display = "none";
    
    newCell0.innerHTML = document.getElementById(tabnm+"TxtLabel").value;
    newCell1.innerHTML = document.getElementById(tabnm+"CmbControl").value;
    newCell2.innerHTML = document.getElementById(tabnm+"TxtTabIndex").value;
    newCell3.innerHTML = "<a onclick=\"javascript:editRptFltrRow('"+tabnm+"',this.parentNode.parentNode.rowIndex)\"><img style=\"width:20px\" align =\"middle\" src=\"images/edit.gif\" alt=\"Edit\"></a>";
    newCell4.innerHTML = "<a onclick=\"javascript:deleteRow('"+tabnm+"ListTable',this.parentNode.parentNode.rowIndex)\"><img style=\"width:20px\" align='center' src=\"images/delete.gif\" alt=\"Delete\"></a>";
    newCell5.innerHTML = divid;
    
    var Rmaindiv = document.getElementById(tabnm+"HiddenDiv");
    var Rdiv = document.createElement("div");
    Rdiv.id = divid;
    
    // add combo in dependent combo box
    if(document.getElementById(tabnm+"CmbControl").value == 'ComboBox' || document.getElementById(tabnm+"CmbControl").value == 'TextLikeCombo')
    {        
        if(tabnm == "fltr")
        {
            var x=document.getElementById(tabnm+"CmbDependent");
            var options = document.createElement("option");
            options.text = document.getElementById(tabnm+"TxtLabel").value;
            options.value = document.getElementById(tabnm+"TxtLabel").value.split(' ').join('');
            for(var i=0;i<x.options.length;i++)
            {
                if(x.options[i].value != options.value)
                {
                    x.add(options, x[1]);
                }
            }
        }
    }
    
    //srs properties
    Rdiv.innerHTML =  "<input type='hidden' name='"+tabnm+"HdnLabel' id='"+tabnm+"hdnLabel' value='"+document.getElementById(tabnm+"TxtLabel").value +"'/>";
    Rdiv.innerHTML += "<input type='hidden' name='"+tabnm+"HdnControl' id='"+tabnm+"HdnControl' value='"+document.getElementById(tabnm+"CmbControl").value +"'/>";
    Rdiv.innerHTML += "<input type='hidden' name='"+tabnm+"HdnValidation' id='"+tabnm+"HdnValidation' value='"+document.getElementById(tabnm+"CmbValidation").value +"'/>";    
    Rdiv.innerHTML += "<input type='hidden' name='"+tabnm+"HdnRemarks' id='"+tabnm+"HdnRemarks' value='"+document.getElementById(tabnm+"TxtRemarks").value + "'/>";
    //for validation
    Rdiv.innerHTML += "<input type='hidden' name='"+tabnm+"HdnChkMandatory' id='"+tabnm+"HdnChkMandatory' value='"+document.getElementById(tabnm+"chkMandatory").checked + "'/>";
    //code properties
    Rdiv.innerHTML += "<input type='hidden' name='"+tabnm+"HdnTxtId' id='"+tabnm+"HdnTxtId' value='"+ id + "'/>";
    Rdiv.innerHTML += "<input type='hidden' name='"+tabnm+"HdnTxtName' id='"+tabnm+"HdnTxtName' value='"+ name + "'/>";
    Rdiv.innerHTML += "<input type='hidden' name='"+tabnm+"HdnTxtValue' id='"+tabnm+"HdnTxtValue' value='"+document.getElementById(tabnm+"TxtValue").value + "'/>";
    Rdiv.innerHTML += "<input type='hidden' name='"+tabnm+"HdnTxtClass' id='"+tabnm+"HdnTxtClass' value='"+document.getElementById(tabnm+"TxtClass").value + "'/>";
    Rdiv.innerHTML += "<input type='hidden' name='"+tabnm+"HdnRbtnChecked' id='"+tabnm+"HdnRbtnChecked' value='"+document.getElementById(tabnm+"RbtnCheckedT").checked + "'/>";
    Rdiv.innerHTML += "<input type='hidden' name='"+tabnm+"HdnTxtMaxLen' id='"+tabnm+"HdnTxtMaxLen' value='"+document.getElementById(tabnm+"TxtMaxLen").value + "'/>";
    Rdiv.innerHTML += "<input type='hidden' name='"+tabnm+"HdnTxtSize' id='"+tabnm+"HdnTxtSize' value='"+document.getElementById(tabnm+"TxtSize").value + "'/>";
    Rdiv.innerHTML += "<input type='hidden' name='"+tabnm+"HdnRbtnMultiple' id='"+tabnm+"HdnRbtnMultiple' value='"+document.getElementById(tabnm+"RbtnMultipleT").checked + "'/>";
    Rdiv.innerHTML += "<input type='hidden' name='"+tabnm+"HdnCmbAlign' id='"+tabnm+"HdnCmbAlign' value='"+document.getElementById(tabnm+"CmbAlign").value + "'/>";
    Rdiv.innerHTML += "<input type='hidden' name='"+tabnm+"HdnTxtStyle' id='"+tabnm+"HdnTxtStyle' value='"+document.getElementById(tabnm+"TxtStyle").value + "'/>";
    Rdiv.innerHTML += "<input type='hidden' name='"+tabnm+"HdnRbtnReadonly' id='"+tabnm+"HdnRbtnReadonly' value='"+document.getElementById(tabnm+"RbtnReadonlyT").checked + "'/>";    
    Rdiv.innerHTML += "<input type='hidden' name='"+tabnm+"HdnTxtTabindex' id='"+tabnm+"HdnTxtTabindex' value='"+document.getElementById(tabnm+"TxtTabIndex").value + "'/>";
    Rdiv.innerHTML += "<input type='hidden' name='"+tabnm+"HdnTxtRows' id='"+tabnm+"HdnTxtRows' value='"+document.getElementById(tabnm+"TxtRows").value + "'/>";
    Rdiv.innerHTML += "<input type='hidden' name='"+tabnm+"HdnTxtCols' id='"+tabnm+"HdnTxtCols' value='"+document.getElementById(tabnm+"TxtCols").value + "'/>";
    Rdiv.innerHTML += "<input type='hidden' name='"+tabnm+"HdnTxtTotalRdo' id='"+tabnm+"HdnTxtTotalRdo' value='"+document.getElementById(tabnm+"TxtTotalRdo").value + "'/>";
    Rdiv.innerHTML += "<input type='hidden' name='"+tabnm+"HdnTxtRdoVal' id='"+tabnm+"HdnTxtRdoVal' value='"+document.getElementById(tabnm+"TxtRdoVal").value + "'/>";
    Rdiv.innerHTML += "<input type='hidden' name='"+tabnm+"HdnTxtRdoCaption' id='"+tabnm+"HdnTxtRdoCaption' value='"+document.getElementById(tabnm+"TxtRdoCaption").value + "'/>";
    Rdiv.innerHTML += "<input type='hidden' name='"+tabnm+"HdnTxtRdoDefVal' id='"+tabnm+"HdnTxtRdoDefVal' value='"+document.getElementById(tabnm+"TxtRdoDefVal").value + "'/>";
    //for fill combo    
    if(tabnm == "fltr")
    {
        Rdiv.innerHTML += "<input type='hidden' name='"+tabnm+"HdnChkDataSource' id='"+tabnm+"HdnChkDataSource' value='"+document.getElementById(tabnm+"chkDataSource").checked + "'/>";
        
        if(document.getElementById(tabnm+"chkDataSource").checked && document.getElementById(tabnm+"RdoSrcStatic").checked)
        {
            Rdiv.innerHTML += "<input type='hidden' name='"+tabnm+"HdnCmbSource' id='"+tabnm+"HdnCmbSource' value='fltrCmbSrcStatic'/>";
        }
        else if(document.getElementById(tabnm+"chkDataSource").checked && document.getElementById(tabnm+"RdoSrcQuery").checked)
        {
            Rdiv.innerHTML += "<input type='hidden' name='"+tabnm+"HdnCmbSource' id='"+tabnm+"HdnCmbSource' value='fltrCmbSrcQuery'/>";
        }
        else if(document.getElementById(tabnm+"chkDataSource").checked && document.getElementById(tabnm+"RdoSrcWS").checked)
        {
            Rdiv.innerHTML += "<input type='hidden' name='"+tabnm+"HdnCmbSource' id='"+tabnm+"HdnCmbSource' value='fltrCmbSrcWS'/>";
        }
        else if(document.getElementById(tabnm+"chkDataSource").checked && document.getElementById(tabnm+"RdoSrcCommonCmb").checked)
        {
            Rdiv.innerHTML += "<input type='hidden' name='"+tabnm+"HdnCmbSource' id='"+tabnm+"HdnCmbSource' value='fltrCmbSrcCommonCmb'/>";
        }
        else
        {
            Rdiv.innerHTML += "<input type='hidden' name='"+tabnm+"HdnCmbSource' id='"+tabnm+"HdnCmbSource' value=''/>";
        }       
        Rdiv.innerHTML += "<input type='hidden' name='"+tabnm+"HdnTxtSrcQuery' id='"+tabnm+"HdnTxtSrcQuery' value='"+escape(document.getElementById(tabnm+"TxtSrcQuery").value) + "'/>";       
        Rdiv.innerHTML += "<input type='hidden' name='"+tabnm+"HdnTxtSrcStatic' id='"+tabnm+"HdnTxtSrcStatic' value='"+document.getElementById(tabnm+"TxtSrcStatic").value + "'/>";
        
        Rdiv.innerHTML += "<input type='hidden' name='"+tabnm+"HdnTxtWsdlUrl' id='"+tabnm+"HdnTxtWsdlUrl' value='" + document.getElementById(tabnm+"TxtWsdlUrl").value + "'/>";       
        Rdiv.innerHTML += "<input type='hidden' name='"+tabnm+"HdnCmbWsMethod' id='"+tabnm+"HdnCmbWsMethod' value='"+document.getElementById(tabnm+"CmbWsMethod").value + "'/>";
        Rdiv.innerHTML += "<input type='hidden' name='"+tabnm+"HdnTxtWsCmbValue' id='"+tabnm+"HdnTxtWsCmbValue' value='" + document.getElementById(tabnm+"WSCmbValue").value + "'/>";       
        Rdiv.innerHTML += "<input type='hidden' name='"+tabnm+"HdnTxtWsCmbText' id='"+tabnm+"HdnTxtWsCmbText' value='"+document.getElementById(tabnm+"WSCmbText").value + "'/>";
        
        if(document.getElementById(tabnm+"RdoSrcWS").checked)
        {
            Rdiv.innerHTML += "<input type='hidden' name='"+tabnm+"HdnTxtWsProject' id='"+tabnm+"HdnTxtWsProject' value='" + document.getElementById(tabnm+"TxtWsProject").value + "'/>";       
            Rdiv.innerHTML += "<input type='hidden' name='"+tabnm+"HdnTxtWsIntrface' id='"+tabnm+"HdnTxtWsIntrface' value='"+document.getElementById(tabnm+"TxtWsIntrface").value + "'/>";       
            Rdiv.innerHTML += "<input type='hidden' name='"+tabnm+"HdnTxtWsRetType' id='"+tabnm+"HdnTxtWsRetType' value='" + document.getElementById(tabnm+"TxtWsRetType").value + "'/>";               
            Rdiv.innerHTML += "<input type='hidden' name='"+tabnm+"HdnTxtWsParams' id='"+tabnm+"HdnTxtWsParams' value='"+document.getElementById(tabnm+"TxtWsParams").value + "'/>";        
            Rdiv.innerHTML += "<input type='hidden' name='"+tabnm+"HdnTxtWsExps' id='"+tabnm+"HdnTxtWsExps' value='" + document.getElementById(tabnm+"TxtWsExps").value + "'/>";       
        }
        else
        {
            Rdiv.innerHTML += "<input type='hidden' name='"+tabnm+"HdnTxtWsProject' id='"+tabnm+"HdnTxtWsProject' value=''/>";       
            Rdiv.innerHTML += "<input type='hidden' name='"+tabnm+"HdnTxtWsIntrface' id='"+tabnm+"HdnTxtWsIntrface' value=''/>";       
            Rdiv.innerHTML += "<input type='hidden' name='"+tabnm+"HdnTxtWsRetType' id='"+tabnm+"HdnTxtWsRetType' value=''/>";               
            Rdiv.innerHTML += "<input type='hidden' name='"+tabnm+"HdnTxtWsParams' id='"+tabnm+"HdnTxtWsParams' value=''/>";        
            Rdiv.innerHTML += "<input type='hidden' name='"+tabnm+"HdnTxtWsExps' id='"+tabnm+"HdnTxtWsExps' value=''/>";                       
        }
       
        Rdiv.innerHTML += "<input type='hidden' name='"+tabnm+"HdnCmbCommonQuery' id='"+tabnm+"HdnCmbCommonQuery' value='"+document.getElementById(tabnm+"CmbCommonQuery").value + "'/>";
        
        Rdiv.innerHTML += "<input type='hidden' name='"+tabnm+"HdnChkForDependentCombo' id='"+tabnm+"HdnChkForDependentCombo' value='"+document.getElementById(tabnm+"chkForDependentCombo").checked + "'/>";
        if(document.getElementById(tabnm+"chkForDependentCombo").checked)
        {
            Rdiv.innerHTML += "<input type='hidden' name='"+tabnm+"HdnCmbDependent' id='"+tabnm+"HdnCmbDependent' value='"+document.getElementById(tabnm+"CmbDependent").value + "'/>";
        }
        else
        {
            Rdiv.innerHTML += "<input type='hidden' name='"+tabnm+"HdnCmbDependent' id='"+tabnm+"HdnCmbDependent' value=''/>";
        }
    }
    Rmaindiv.appendChild(Rdiv);
    if(tabnm == "rpt")
    {
        rptid++;
    }
    else
    {
        fltrid++;
    }
    clearRptFltrData(tabnm);
    validateControl(tabnm);
    //document.getElementById(tabnm+"trMndtry").style.display = "none";
    showChkMndtry(tabnm);
}

function editRptFltrRow(tabnm,rowid)
{    
    var editdivid = document.getElementById(tabnm+'ListTable').rows[rowid].cells[5].childNodes[0].data;    
    //srs properties
    document.getElementById(tabnm+"TxtLabel").value = document.getElementById(editdivid).childNodes[0].value;
    document.getElementById(tabnm+"CmbControl").value = document.getElementById(editdivid).childNodes[1].value;    
    document.getElementById(tabnm+"TxtRemarks").value = document.getElementById(editdivid).childNodes[3].value;
    //code properties
    document.getElementById(tabnm+"TxtId").value = document.getElementById(editdivid).childNodes[5].value;
    document.getElementById(tabnm+"TxtName").value = document.getElementById(editdivid).childNodes[6].value;
    document.getElementById(tabnm+"TxtValue").value = document.getElementById(editdivid).childNodes[7].value;
    document.getElementById(tabnm+"TxtClass").value = document.getElementById(editdivid).childNodes[8].value;
    if(document.getElementById(editdivid).childNodes[9].value == "true")
    {
        document.getElementById(tabnm+"RbtnCheckedT").checked = true;
    }
    else
    {
        document.getElementById(tabnm+"RbtnCheckedT").checked = false;
    }
    document.getElementById(tabnm+"TxtMaxLen").value = document.getElementById(editdivid).childNodes[10].value;
    document.getElementById(tabnm+"TxtSize").value = document.getElementById(editdivid).childNodes[11].value;
    if(document.getElementById(editdivid).childNodes[12].value == "true")
    {
        document.getElementById(tabnm+"RbtnMultipleT").checked = true;        
    }
    else
    {
        document.getElementById(tabnm+"RbtnMultipleF").checked = true;
    }
    
    document.getElementById(tabnm+"CmbAlign").value = document.getElementById(editdivid).childNodes[13].value;
    document.getElementById(tabnm+"TxtStyle").value = document.getElementById(editdivid).childNodes[14].value;
    if(document.getElementById(editdivid).childNodes[15].value == "true")
    {
        document.getElementById(tabnm+"RbtnReadonlyT").checked = true;
    }
    else
    {
        document.getElementById(tabnm+"RbtnReadonlyF").checked = true;
    }
    document.getElementById(tabnm+"TxtTabIndex").value = document.getElementById(editdivid).childNodes[16].value;
    document.getElementById(tabnm+"TxtRows").value = document.getElementById(editdivid).childNodes[17].value;
    document.getElementById(tabnm+"TxtCols").value = document.getElementById(editdivid).childNodes[18].value;
    document.getElementById(tabnm+"TxtTotalRdo").value = document.getElementById(editdivid).childNodes[19].value;
    document.getElementById(tabnm+"TxtRdoVal").value = document.getElementById(editdivid).childNodes[20].value;         
    document.getElementById(tabnm+"TxtRdoCaption").value = document.getElementById(editdivid).childNodes[21].value;
    document.getElementById(tabnm+"TxtRdoDefVal").value = document.getElementById(editdivid).childNodes[22].value;         
    if(tabnm == "fltr")
    {
        //        if(document.getElementById(editdivid).childNodes[24].value == "fltrCmbTypeIndep")
        //        {
        //            document.getElementById(tabnm+"RdoCmbTypeIndep").checked = true;
        //        }
        //        else if(document.getElementById(editdivid).childNodes[24].value == "fltrCmbTypeDep")
        //        {
        //            document.getElementById(tabnm+"RdoCmbTypeDep").checked = true;
        //        }
        if(document.getElementById(editdivid).childNodes[23].value == "true")
        {
            document.getElementById(tabnm+"chkDataSource").checked = true;
        }        
        else
        {
            document.getElementById(tabnm+"chkDataSource").checked = false;    
        }
        
        if(document.getElementById(editdivid).childNodes[24].value == "fltrCmbSrcStatic")
        {            
            document.getElementById(tabnm+"RdoSrcStatic").checked = true;
        }
        else if(document.getElementById(editdivid).childNodes[24].value == "fltrCmbSrcQuery")
        {
            document.getElementById(tabnm+"RdoSrcQuery").checked = true;
        }
        else if(document.getElementById(editdivid).childNodes[24].value == "fltrCmbSrcWS")
        {
            document.getElementById(tabnm+"RdoSrcWS").checked = true;
        }
        else if(document.getElementById(editdivid).childNodes[24].value == "fltrCmbSrcCommonCmb")
        {
            document.getElementById(tabnm+"RdoSrcCommonCmb").checked = true;
        }
        else
        {
            document.getElementById(tabnm+"RdoSrcStatic").checked = false;
            document.getElementById(tabnm+"RdoSrcQuery").checked = false;
            document.getElementById(tabnm+"RdoSrcWS").checked = false;
            document.getElementById(tabnm+"RdoSrcCommonCmb").checked = false;
        }
        
        document.getElementById(tabnm+"TxtSrcQuery").value = unescape(document.getElementById(editdivid).childNodes[25].value);
        document.getElementById(tabnm+"TxtSrcStatic").value = document.getElementById(editdivid).childNodes[26].value;        
        
        document.getElementById(tabnm+"TxtWsdlUrl").value = document.getElementById(editdivid).childNodes[27].value;        
        document.getElementById(tabnm+"CmbWsMethod").value = document.getElementById(editdivid).childNodes[28].value;        
        document.getElementById(tabnm+"WSCmbValue").value = document.getElementById(editdivid).childNodes[29].value;        
        document.getElementById(tabnm+"WSCmbText").value = document.getElementById(editdivid).childNodes[30].value;            
                
        if(document.getElementById(tabnm+"chkDataSource").checked && document.getElementById(tabnm+"RdoSrcWS").checked)
        {
            document.getElementById(tabnm+"TxtWsProject").value = document.getElementById(editdivid).childNodes[31].value;        
            document.getElementById(tabnm+"TxtWsIntrface").value = document.getElementById(editdivid).childNodes[32].value;        
            document.getElementById(tabnm+"TxtWsRetType").value = document.getElementById(editdivid).childNodes[33].value;        
            document.getElementById(tabnm+"TxtWsParams").value = document.getElementById(editdivid).childNodes[34].value;      
            document.getElementById(tabnm+"TxtWsExps").value = document.getElementById(editdivid).childNodes[35].value;      
        } 
        
        document.getElementById(tabnm+"CmbCommonQuery").value = document.getElementById(editdivid).childNodes[36].value;     
        
        if(document.getElementById(editdivid).childNodes[37].value == "true")
        {
            document.getElementById(tabnm+"chkForDependentCombo").checked = true;
        }
        else
        {
            document.getElementById(tabnm+"chkForDependentCombo").checked = false;
        }
        document.getElementById(tabnm+"CmbDependent").value = document.getElementById(editdivid).childNodes[38].value;     
        
    }     
    validateControl(tabnm);
    document.getElementById(tabnm+"CmbValidation").value = document.getElementById(editdivid).childNodes[2].value;    
    showChkMndtry(tabnm);
    if(document.getElementById(editdivid).childNodes[4].value == "true")
    {
        document.getElementById(tabnm+"chkMandatory").checked = true;
    }
    else
    {
        document.getElementById(tabnm+"chkMandatory").checked = false;
    }
    deleteRow(tabnm+"ListTable",rowid);

}

function clearRptFltrData(tabnm)
{
    //srs properties
    document.getElementById(tabnm+"TxtLabel").value = '';
    document.getElementById(tabnm+"CmbControl").value = '-1';
    document.getElementById(tabnm+"CmbValidation").value = '-1';    
    document.getElementById(tabnm+"TxtRemarks").value = '';
    //code properties
    document.getElementById(tabnm+"TxtId").value = '';
    document.getElementById(tabnm+"TxtName").value = '';
    document.getElementById(tabnm+"TxtValue").value = '';
    document.getElementById(tabnm+"TxtClass").value = '';
    document.getElementById(tabnm+"RbtnCheckedT").checked = false;
    document.getElementById(tabnm+"RbtnCheckedF").checked = false;
    document.getElementById(tabnm+"TxtMaxLen").value = '';
    document.getElementById(tabnm+"TxtSize").value = '';
    document.getElementById(tabnm+"RbtnMultipleT").checked = false;
    document.getElementById(tabnm+"RbtnMultipleF").checked = false;
    document.getElementById(tabnm+"CmbAlign").value = "-1";
    document.getElementById(tabnm+"TxtStyle").value = '';
    document.getElementById(tabnm+"RbtnReadonlyT").checked = false;
    document.getElementById(tabnm+"RbtnReadonlyF").checked = false;
    document.getElementById(tabnm+"TxtTabIndex").value = '';
    document.getElementById(tabnm+"TxtRows").value = '';
    document.getElementById(tabnm+"TxtCols").value = '';
    document.getElementById(tabnm+"TxtTotalRdo").value = '';
    document.getElementById(tabnm+"TxtRdoVal").value = '';
    document.getElementById(tabnm+"TxtRdoCaption").value = '';
    document.getElementById(tabnm+"TxtRdoDefVal").value = '';
    if(tabnm == "fltr")
    {
        document.getElementById("fltrTxtSrcQuery").value = '';
        document.getElementById(tabnm+"chkDataSource").checked = false;
        document.getElementById(tabnm+"RdoSrcStatic").checked = false;
        document.getElementById(tabnm+"RdoSrcQuery").checked = false;
        document.getElementById(tabnm+"RdoSrcWS").checked = false;
        document.getElementById(tabnm+"RdoSrcCommonCmb").checked = false;
        document.getElementById(tabnm+"TxtSrcStatic").value = "";        
        document.getElementById(tabnm+"TxtWsdlUrl").value = "";
        document.getElementById(tabnm+"CmbWsMethod").value = "-1";
        document.getElementById(tabnm+"WSCmbValue").value = "";
        document.getElementById(tabnm+"WSCmbText").value = "";        
        document.getElementById(tabnm+"CmbCommonQuery").value = "-1";
        
        showFltrComboFill();
        showFltrSrcQuery();        
    }
    document.getElementById(tabnm+"chkMandatory").checked = false;
}

function deleteRow(tablenm,rowid)
{
    var deletedivid;
    if(tablenm == "columnControlListTable" ||tablenm == "grpFooterListTable")
    {
        deletedivid = document.getElementById(tablenm).rows[rowid].cells[4].childNodes[0].data;
    }
    else
    {
        deletedivid = document.getElementById(tablenm).rows[rowid].cells[5].childNodes[0].data;
    }
    
    if(tablenm.split("ListTable")[0] == 'fltr')
    {
        // remove combo in dependent combo box
        var val=document.getElementById(tablenm).rows[rowid].cells[0].childNodes[0].data.split(' ').join('');
        var sel=tablenm.split("ListTable")[0]+"CmbDependent";
        $(document).ready(function() {
            $('#'+sel+' option[value="'+val+'"]').remove();
        });
    }
    
    var div = document.getElementById(deletedivid);
    div.parentNode.removeChild(div);
    document.getElementById(tablenm).deleteRow(rowid); 
}

function checkID(tabid)
{
    if(document.getElementById("chkReportType").checked)
    {
        for (var i = 2; i < rptid; i++)
        {
            if(document.getElementById(i) != null)
            {
                if (tabid == document.getElementById(i).childNodes[5].value)
                {
                    return true;
                }
            }            
        }
    }    
    if(document.getElementById("chkFilters").checked)
    {
        for (i = 101; i < fltrid; i++)
        {
            if(document.getElementById(i) != null)
            {
                if (tabid == document.getElementById(i).childNodes[5].value)
                {
                    return true;
                }
            }           
        }
    }    
    return false;
}

function loadAll()
{
    loadComboNew("cmbProjectName","","","MenuForm");
    loadComboNew("cmbRefNo","","","MenuForm");
    loadComboNew("cmbAliasName","","","MenuForm");
    loadComboNew("cmbDevServer","","","MenuForm");
    loadComboNew("cmbShowColumns","","","MenuForm");
    loadComboNew("rptCmbControl","","","MenuForm");
    loadComboNew("cmbRptControlPos","","","MenuForm");
    loadComboNew("fltrCmbControl","","","MenuForm");
    loadComboNew("cmbControlPos","","","MenuForm");
    loadComboNew("cmbColumnControl","","","MenuForm");
    loadComboNew("cmbGroupField","","","MenuForm");
    loadComboNew("cmbGrpFooterColumn","","","MenuForm");
    loadComboNew("cmbGrpFooterCalculation","","","MenuForm");
    loadComboNew("cmbPageFooterColumn","","","MenuForm");
    loadComboNew("cmbPrimaryKey","","","MenuForm");
    loadComboNew("cmbColumnGrid","","","MenuForm");
    loadComboNew("rptCmbAlign","","","MenuForm");
    loadComboNew("fltrCmbAlign","","","MenuForm");
    loadComboNew("rptCmbValidation","","","MenuForm");
    loadComboNew("fltrCmbValidation","","","MenuForm");    
    loadComboNew("pieCmbXaxisColumn","","","MenuForm");    
    loadComboNew("pieCmbYaxisColumn","","","MenuForm");    
    loadComboNew("barCmbXaxisColumn","","","MenuForm");
    loadComboNew("barCmbYaxisColumn","","","MenuForm");
    loadComboNew("lineCmbXaxisColumn","","","MenuForm");
    loadComboNew("lineCmbYaxisColumn","","","MenuForm");
    loadComboNew("areaCmbXaxisColumn","","","MenuForm");
    loadComboNew("areaCmbYaxisColumn","","","MenuForm");
    loadComboNew("fltrCmbDependent","","","MenuForm");
    loadComboNew("fltrCmbCommonQuery","","","MenuForm");
    loadComboNew("fltrCmbWsMethod","","","MenuForm");
}

function checkControlName(controlname)
{
    if(document.getElementById("chkReportType").checked)
    {
        for (var i = 2; i < rptid; i++)
        {
            if(document.getElementById(i) != null)
            {
                if (controlname == document.getElementById(i).childNodes[6].value)
                {
                    return true;
                }
            }
        }
    }
    if(document.getElementById("chkFilters").checked)
    {
        for (i = 101; i < fltrid; i++)
        {
            if(document.getElementById(i) != null)
            {
                if (controlname == document.getElementById(i).childNodes[6].value)
                {
                    return true;
                }
            }
        }
    }
    return false;
}

function onload()
{
    loadComboNew("cmbProjectName","","","MenuForm");
    loadComboNew("cmbAliasName","","","MenuForm");
    loadComboNew("cmbDevServer","","","MenuForm");
    loadComboNew("cmbShowColumns","","","MenuForm");
    loadComboNew("rptCmbControl","","","MenuForm");
    loadComboNew("cmbRptControlPos","","","MenuForm");
    loadComboNew("fltrCmbControl","","","MenuForm");
    loadComboNew("cmbControlPos","","","MenuForm");
    loadComboNew("cmbColumnControl","","","MenuForm");
    loadComboNew("cmbGroupField","","","MenuForm");
    loadComboNew("cmbGrpFooterColumn","","","MenuForm");
    loadComboNew("cmbGrpFooterCalculation","","","MenuForm");
    loadComboNew("cmbPageFooterColumn","","","MenuForm");
    loadComboNew("cmbPrimaryKey","","","MenuForm");
    loadComboNew("cmbColumnGrid","","","MenuForm");
    loadComboNew("rptCmbAlign","","","MenuForm");
    loadComboNew("fltrCmbAlign","","","MenuForm");
    loadComboNew("rptCmbValidation","","","MenuForm");
    loadComboNew("fltrCmbValidation","","","MenuForm");    
    loadComboNew("pieCmbXaxisColumn","","","MenuForm");    
    loadComboNew("pieCmbYaxisColumn","","","MenuForm");    
    loadComboNew("barCmbXaxisColumn","","","MenuForm");
    loadComboNew("barCmbYaxisColumn","","","MenuForm");
    loadComboNew("lineCmbXaxisColumn","","","MenuForm");
    loadComboNew("lineCmbYaxisColumn","","","MenuForm");
    loadComboNew("areaCmbXaxisColumn","","","MenuForm");
    loadComboNew("areaCmbYaxisColumn","","","MenuForm");
    loadComboNew("fltrCmbDependent","","","MenuForm");
    loadComboNew("fltrCmbCommonQuery","","","MenuForm");
    loadComboNew("fltrCmbWsMethod","","","MenuForm");
}

function validateNoOfRadio(tabnm)
{
    var flag=false;
    if(document.getElementById(tabnm+"CmbControl").value == "Radio" )
    {
        document.getElementById(tabnm+"TxtTotalRdo").value = document.getElementById(tabnm+"TxtTotalRdo").value.split(' ').join('');
        document.getElementById(tabnm+"TxtRdoVal").value = document.getElementById(tabnm+"TxtRdoVal").value.split(' ').join('');
        if (document.getElementById(tabnm+"TxtTotalRdo").value == "")
        {
            alert("Please Enter No. Of Radio Buttons");
            document.getElementById(tabnm+"chkCode").checked = true;
            document.getElementById(tabnm+"CodeProperty").style.display = "";
            document.getElementById(tabnm+"TxtTotalRdo").focus();
            return false;
        }
        else if (!document.getElementById(tabnm+"TxtTotalRdo").value.match(/^[0-9]+$/) || parseInt(document.getElementById(tabnm+"TxtTotalRdo").value, 10) == 0)
        {
            alert("Please Enter Positive Digits For No. Of Radio Buttons");
            document.getElementById(tabnm+"chkCode").checked = true;
            document.getElementById(tabnm+"CodeProperty").style.display = "";
            document.getElementById(tabnm+"TxtTotalRdo").focus();
            return false;
        }
        else if (document.getElementById(tabnm+"TxtRdoCaption").value == "")
        {
            alert("Please Enter Caption name For Radio Buttons");
            document.getElementById(tabnm+"chkCode").checked = true;
            document.getElementById(tabnm+"CodeProperty").style.display = "";
            document.getElementById(tabnm+"TxtRdoCaption").focus();
            return false;
        }
        else 
        {
            var caption = document.getElementById(tabnm+"TxtRdoCaption").value;
            while (caption.charAt(0) == "/")
            {
                caption = caption.substr(1);
            }
            while (caption.charAt(caption.length - 1) == "/")
            {
                caption = caption.substr(0, caption.length - 1);
            }
            document.getElementById(tabnm+"TxtRdoCaption").value = caption;
            caption = document.getElementById(tabnm+"TxtRdoCaption").value;
            while (caption.indexOf("//") >= 0)
            {
                caption = val.replace('//', '/');
            }
            document.getElementById(tabnm+'TxtRdoCaption').value = caption;
            if (caption.split("/").length == document.getElementById(tabnm+"TxtTotalRdo").value)
            {
                if (document.getElementById(tabnm+'TxtRdoDefVal').value != "")
                {
                    var defVal = document.getElementById(tabnm+'TxtRdoDefVal').value;
                    if (defVal.match(/^[0-9]+$/))
                    {
                        if (parseInt(defVal, 10) >= 1 && parseInt(defVal, 10) <= parseInt(document.getElementById(tabnm+'TxtTotalRdo').value, 10))
                        {
                            flag=true;
                        //                            return true;
                        }
                        else
                        {
                            alert("Please Enter Number Between 1 And " + parseInt(document.getElementById(tabnm+'TxtTotalRdo').value, 10));
                            document.getElementById(tabnm+'TxtRdoDefVal').focus();
                            return false;
                        }
                    }
                    else
                    {
                        alert("Please Enter Number For Default Selection Of Radio");
                        document.getElementById(tabnm+'TxtRdoDefVal').focus();
                        return false;
                    }
                }
                else
                {
                    flag=true;
                //                    return true;
                }
            }
            else
            {
                alert("No. Of Radio And No. Of Captions Do Not Match");
                document.getElementById(tabnm+"TxtRdoCaption").focus();
                return false;
            }
        }
        
        if (document.getElementById(tabnm+"TxtRdoVal").value == "")
        {
            alert("Please Enter Values For Radio Buttons");
            document.getElementById(tabnm+"chkCode").checked = true;
            document.getElementById(tabnm+"CodeProperty").style.display = "";
            document.getElementById(tabnm+"TxtRdoVal").focus();
            return false;
        }
        else
        {
            var val = document.getElementById(tabnm+"TxtRdoVal").value;
            while (val.charAt(0) == "/")
            {
                val = val.substr(1);
            }
            while (val.charAt(val.length - 1) == "/")
            {
                val = val.substr(0, val.length - 1);
            }
            document.getElementById(tabnm+"TxtRdoVal").value = val;
            val = document.getElementById(tabnm+"TxtRdoVal").value;
            while (val.indexOf("//") >= 0)
            {
                val = val.replace('//', '/');
            }
            document.getElementById(tabnm+'TxtRdoVal').value = val;
            if (val.split("/").length == document.getElementById(tabnm+"TxtTotalRdo").value)
            {
                if (document.getElementById(tabnm+'TxtRdoDefVal').value != "")
                {
                    var defVal = document.getElementById(tabnm+'TxtRdoDefVal').value;
                    if (defVal.match(/^[0-9]+$/))
                    {
                        if (parseInt(defVal, 10) >= 1 && parseInt(defVal, 10) <= parseInt(document.getElementById(tabnm+'TxtTotalRdo').value, 10))
                        {
                            flag=true;
                        //                            return true;
                        }
                        else
                        {
                            alert("Please Enter Number Between 1 And " + parseInt(document.getElementById(tabnm+'TxtTotalRdo').value, 10));
                            document.getElementById(tabnm+'TxtRdoDefVal').focus();
                            return false;
                        }
                    }
                    else
                    {
                        alert("Please Enter Number For Default Selection Of Radio");
                        document.getElementById(tabnm+'TxtRdoDefVal').focus();
                        return false;
                    }
                }
                else
                {
                    flag=true;
                //                    return true;
                }
            }
            else
            {
                alert("No. Of Radio And No. Of Values Do Not Match");
                document.getElementById(tabnm+"TxtRdoVal").focus();
                return false;
            }
        }
        if(flag)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    else
    {
        return true;
    }
}

function showAddControl()
{
    showHideOnChecked("chkAddControl","tbladdCotrolDetail");
}

function showFltrComboFill()
{        
    if (document.getElementById("fltrchkDataSource").checked)
    {
        document.getElementById("fltrIndepSource").style.display = "";      
        document.getElementById("cmbFillQueryResult").innerHTML = "";
        
        if (document.getElementById("fltrRdoSrcQuery").checked)
        {
            document.getElementById("fltrIndepSrcQuery").style.display = "";      
        }
        else
        {
            document.getElementById("fltrIndepSrcQuery").style.display = "none";      
        }
    }
    else
    {        
        document.getElementById("fltrIndepSource").style.display = "none";        
        document.getElementById("fltrIndepSrcStatic").style.display = "none";
        document.getElementById("fltrIndepSrcQuery").style.display = "none";
        document.getElementById("fltrIndepSrcWS").style.display = "none";
        document.getElementById("fltrIndepSrcCommonCmb").style.display = "none";
    }
}

function showFltrForDependentCombo()
{
    if (document.getElementById("fltrchkForDependentCombo").checked)
    {
        document.getElementById("fltrDepCombo").style.display = "";        
        document.getElementById("fltrchkDataSource").checked=true;
        document.getElementById("fltrIndepSource").style.display="";
        document.getElementById("fltrIndepSrcCommonCmb").style.display="none";
        document.getElementById("fltrRdoSrcStatic").disabled=true;
        document.getElementById("fltrRdoSrcWS").disabled=true;
        document.getElementById("fltrRdoSrcCommonCmb").disabled=true;
        document.getElementById("fltrRdoSrcQuery").checked=true;
        document.getElementById("fltrIndepSrcQuery").style.display = "";
        document.getElementById("fltrCmbDependent").options[0].selected = true;
        document.getElementById("msgDiv").innerHTML = "<sup style=\"color: red\">*</sup>Query result must contain only two columns with single \"WHERE\" clause<br>[ COL1->option value, COL2->option text ]";
        
    }
    else
    {
        document.getElementById("fltrDepCombo").style.display = "none";        
        document.getElementById("fltrchkDataSource").checked=false;
        document.getElementById("fltrIndepSource").style.display="none";
        document.getElementById("fltrRdoSrcStatic").disabled=false;
        document.getElementById("fltrRdoSrcWS").disabled=false;
        document.getElementById("fltrRdoSrcCommonCmb").disabled=false;
        document.getElementById("fltrRdoSrcQuery").checked=false;
        document.getElementById("fltrIndepSrcQuery").style.display = "none";
        document.getElementById("msgDiv").innerHTML = "<sup style=\"color: red\">*</sup>Query result must contain only two columns<br>[ COL1->option value, COL2->option text ]";
    }
}

function showFltrSrcQuery()
{    
    if (document.getElementById("fltrRdoSrcQuery").checked)
    {
        document.getElementById("fltrIndepSrcQuery").style.display = "";
        document.getElementById("fltrIndepSrcStatic").style.display = "none";
        document.getElementById("fltrIndepSrcWS").style.display = "none";  
        document.getElementById("fltrIndepSrcCommonCmb").style.display = "none"; 
    }
    else if (document.getElementById("fltrRdoSrcStatic").checked)
    {
        document.getElementById("fltrIndepSrcStatic").style.display = "";
        document.getElementById("fltrIndepSrcQuery").style.display = "none";
        document.getElementById("fltrIndepSrcWS").style.display = "none";
        document.getElementById("fltrIndepSrcCommonCmb").style.display = "none"; 
    }
    else if(document.getElementById("fltrRdoSrcWS").checked)
    {
        document.getElementById("fltrIndepSrcWS").style.display = "";
        document.getElementById("fltrIndepSrcStatic").style.display = "none";
        document.getElementById("fltrIndepSrcQuery").style.display = "none";  
        document.getElementById("fltrIndepSrcCommonCmb").style.display = "none"; 
    }   
    else if(document.getElementById("fltrRdoSrcCommonCmb").checked)
    {
        document.getElementById("fltrIndepSrcCommonCmb").style.display = ""; 
        document.getElementById("fltrIndepSrcQuery").style.display = "none";
        document.getElementById("fltrIndepSrcStatic").style.display = "none";
        document.getElementById("fltrIndepSrcWS").style.display = "none";  
    } 
}

function validateComboFill(tabnm)
{
    if(document.getElementById(tabnm+"CmbControl").value == "ComboBox" || document.getElementById(tabnm+"CmbControl").value == "TextLikeCombo")
    {
        if(tabnm == "fltr" && !document.getElementById(tabnm+"chkDataSource").checked)
        {
            return true;
        }
        else if(tabnm == "fltr" && document.getElementById(tabnm+"chkDataSource").checked && !document.getElementById("fltrRdoSrcQuery").checked && !document.getElementById("fltrRdoSrcStatic").checked && !document.getElementById("fltrRdoSrcWS").checked && !document.getElementById("fltrRdoSrcCommonCmb").checked)
        {
            alert("Please select datasource to fill combobox");
            document.getElementById(tabnm+"chkCode").checked = true;
            document.getElementById(tabnm+"CodeProperty").style.display = "";
            document.getElementById("fltrRdoSrcStatic").focus();
            return false;
        }
        else if(tabnm == "fltr" && document.getElementById(tabnm+"chkDataSource").checked && document.getElementById("fltrRdoSrcStatic").checked && document.getElementById("fltrTxtSrcStatic").value == "")
        {
            alert("Please enter static data to fill combobox");
            document.getElementById(tabnm+"chkCode").checked = true;
            document.getElementById(tabnm+"CodeProperty").style.display = "";
            document.getElementById("fltrTxtSrcStatic").focus();
            return false;
        } 
        else if(tabnm == "fltr" && document.getElementById(tabnm+"chkDataSource").checked  && document.getElementById("fltrRdoSrcStatic").checked && document.getElementById("fltrTxtSrcStatic").value != "")
        {
            //            validateStaticDataFormate(tabnm);
            if(validateStaticDataFormate(tabnm))
            {
                if(validateTabIndex(tabnm+"TxtTabIndex"))
                {
                    addNewRptFltrRow('fltr',fltrid);
                }
            }            
        }
        else if(tabnm == "fltr"  && document.getElementById(tabnm+"chkDataSource").checked && document.getElementById("fltrRdoSrcQuery").checked && document.getElementById("fltrTxtSrcQuery").value == "")
        {
            alert("Please enter query to fill combobox");
            document.getElementById(tabnm+"chkCode").checked = true;
            document.getElementById(tabnm+"CodeProperty").style.display = "";
            document.getElementById("fltrTxtSrcQuery").focus();
            return false;
        }
        else if(tabnm == "fltr"  && document.getElementById(tabnm+"chkForDependentCombo").checked)
        {
            if(document.getElementById("fltrTxtSrcQuery").value.indexOf("WHERE")==-1)
            {
                alert("Query result must contain one WHERE clause");   
                document.getElementById("fltrTxtSrcQuery").focus();
                return false;
            }            
            else if((document.getElementById("fltrTxtSrcQuery").value.match(/WHERE/g)).length > 1)
            {
                alert("Query result must contain only one WHERE clause");   
                document.getElementById("fltrTxtSrcQuery").focus();
                return false;
            }            
            if (document.getElementById("fltrCmbDependent").value == "-1")
            {
                alert("Please select any one option from Dependent Combo.");
                document.getElementById("fltrCmbDependent").focus();
                return false;  
            }
            else
            {
                if(validateTabIndex(tabnm+"TxtTabIndex"))
                {
                    addNewRptFltrRow('fltr',fltrid);
                }
            }
        }
        else if(tabnm == "fltr" && document.getElementById("fltrRdoSrcQuery").checked && document.getElementById("fltrTxtSrcQuery").value != "")
        {
            var params=getFormData(document.MenuForm);
            getData_sync("repgenv3.fin?cmdAction=comboFillCheckQuery", "cmbFillQueryResult", params, false);
            if(document.getElementById("cmbFillQueryResult").innerHTML.match("Invalid Alias or Query") != null)
            {
                alert("Please check query to fill combobox");
                document.getElementById(tabnm+"chkCode").checked = true;
                document.getElementById(tabnm+"CodeProperty").style.display = "";
                document.getElementById("fltrTxtSrcQuery").focus();
                return false;
            }
            else if(document.getElementById("cmbFillQueryResult").innerHTML.match("two columns") != null)
            {
                alert("Please check query to fill combo it contains only two columns");
                document.getElementById(tabnm+"chkCode").checked = true;
                document.getElementById(tabnm+"CodeProperty").style.display = "";
                document.getElementById("fltrTxtSrcQuery").focus();
                return false;
            }
            else
            {
                if(validateTabIndex(tabnm+"TxtTabIndex") && validateNoOfRadio(tabnm))
                {
                    addNewRptFltrRow('fltr',fltrid);
                }
            }
        }
        else if(tabnm == "fltr" && document.getElementById("fltrRdoSrcWS").checked )
        {
            if(document.getElementById("fltrTxtWsdlUrl").value == "")
            {
                alert("Please enter wsdl url");
                document.getElementById(tabnm+"chkCode").checked = true;
                document.getElementById(tabnm+"CodeProperty").style.display = "";
                document.getElementById("fltrTxtWsdlUrl").focus();
                return false;                
            }
            else if(document.getElementById("fltrTxtWsdlUrl").value != "" && document.getElementById("fltrCmbWsMethod").value == "-1")
            {
                alert("Please select method to fill combobox");
                document.getElementById(tabnm+"chkCode").checked = true;
                document.getElementById(tabnm+"CodeProperty").style.display = "";
                document.getElementById("fltrCmbWsMethod").focus();
                return false;                
            }
            else if(document.getElementById("fltrCmbWsMethod").value != "-1" && document.getElementById("fltrWSCmbValue").value == "")
            {
                alert("Please enter field for value in combo option");
                document.getElementById(tabnm+"chkCode").checked = true;
                document.getElementById(tabnm + "CodeProperty").style.display = "";
                document.getElementById("fltrWSCmbValue").focus();
                return false;
            }
            else if(document.getElementById("fltrCmbWsMethod").value != "-1" && (!document.getElementById("fltrWSCmbValue").value.match(/^[A-Za-z_]+$/) || document.getElementById("fltrWSCmbValue").value.match(/^[_]+$/)))
            {
                alert("Please Enter Valid Field For Value In Combo Option");
                document.getElementById(tabnm+"chkCode").checked = true;
                document.getElementById(tabnm + "CodeProperty").style.display = "";
                document.getElementById("fltrWSCmbValue").focus();
                return false;
            }  
            else if(document.getElementById("fltrCmbWsMethod").value != "-1" && document.getElementById("fltrWSCmbText").value == "")
            {
                alert("Please enter field for text in combo option");
                document.getElementById(tabnm+"chkCode").checked = true;
                document.getElementById(tabnm + "CodeProperty").style.display = "";
                document.getElementById("fltrWSCmbText").focus();
                return false;
            }  
            else if(document.getElementById("fltrCmbWsMethod").value != "-1" && (!document.getElementById("fltrWSCmbText").value.match(/^[A-Za-z_]+$/) || document.getElementById("fltrWSCmbText").value.match(/^[_]+$/)))
            {
                alert("Please Enter Valid Field For Text In Combo Option");
                document.getElementById(tabnm+"chkCode").checked = true;
                document.getElementById(tabnm + "CodeProperty").style.display = "";
                document.getElementById("fltrWSCmbText").focus();
                return false;
            }    
            else
            {
                if(validateTabIndex(tabnm+"TxtTabIndex"))
                {
                    addNewRptFltrRow('fltr',fltrid);
                }
            }
        }
        else if(tabnm == "fltr"  && document.getElementById(tabnm+"chkDataSource").checked && document.getElementById("fltrRdoSrcCommonCmb").checked)
        {
            if (document.getElementById("fltrCmbCommonQuery").value == "-1")
            {
                alert("Please select any one option to fill combobox");
                document.getElementById(tabnm+"chkCode").checked = true;
                document.getElementById(tabnm+"CodeProperty").style.display = "";
                document.getElementById("cmbCommonQuery").focus();
                return false;  
            }
            else
            {
                if(validateTabIndex(tabnm+"TxtTabIndex"))
                {
                    addNewRptFltrRow('fltr',fltrid);
                }   
            }   
        }
    }
    else
    {
        return true;
    }
}

function validateStaticDataFormate(tabnm)
{
    if(tabnm == "fltr" && document.getElementById("fltrRdoSrcStatic").checked && document.getElementById("fltrTxtSrcStatic").value != "")
    {
        var staticData = Trim(document.getElementById("fltrTxtSrcStatic").value);
        
        if(!staticData.match(/^(([A-Za-z0-9\.\-\_ ]+),([A-Za-z0-9\.\-\_ ]+);)*([A-Za-z0-9\.\-\_ ]+),([A-Za-z0-9\.\-\_ ]+){1}$/))
        {
            alert("Please check static data formate");
            document.getElementById("fltrTxtSrcStatic").focus();
            return false;
        }
        else
        {
            return true;
        }
    }
}

function validatePageFooter()
{  
    if(document.getElementById('chkGrouping').checked && document.getElementById('cmbGroupField').value == "-1")
    {
        alert("Please select grouping column");
        document.getElementById('cmbGroupField').focus();
        return false;
    }  
    if(document.getElementById('chkGroupFooter').checked && document.getElementById('grpFooterListTable').rows.length < 3)
    {
        alert("Please add atleast one group footer column");
        document.getElementById('cmbGrpFooterColumn').focus();
        return false;
    }
    if(document.getElementById('chkPageFooter').checked || document.getElementById('chkGrandTotal').checked)
    {
        for(var i=0;i<document.getElementById("cmbPageFooterColumn").options.length;i++)
        {
            if(document.getElementById('cmbPageFooterColumn').options[i].selected && document.getElementById('cmbPageFooterColumn').options[i].value != "-1")
            {
                break;
            }
        }
        if(i == document.getElementById("cmbPageFooterColumn").options.length)
        {
            alert("Please select column(s) to add into pagefooter");
            document.getElementById('cmbPageFooterColumn').focus();
            return false;
        }
    }
    if(document.getElementById('cmbPrimaryKey').value == '-1')
    {
        alert("Please select primary key column");
        document.getElementById('cmbPrimaryKey').focus();
        return false;
    }
    else
    {
        return true;
    }
}

function showChartDetails(chartnm)
{
    showHideOnChecked(chartnm+"Chart",chartnm+"ChartDetail");
}

var chartColumnId = 0;
function addNewYaxisColumn(controlnm,chartnm)
{
    var trYaxisColumn = document.createElement('tr');
    var tdYaxisLabel = document.createElement('td');
    tdYaxisLabel.colSpan="2";
    tdYaxisLabel.innerHTML = "Y-axis Column :";
    tdYaxisLabel.align = "right";
    
    var tdYaxisControl = document.createElement('td');
    var combo_box = document.createElement('select');
    combo_box.id = controlnm + chartColumnId;
    combo_box.name = controlnm;      
    combo_box.innerHTML = document.getElementById("pieCmbXaxisColumn").innerHTML;
    combo_box.onchange = function() {
        checkYColType(combo_box.id);
    };
    tdYaxisControl.appendChild(combo_box);
    
    var trLegendLabel = document.createElement('tr');
    var tdLegendLabel = document.createElement('td');    
    tdLegendLabel.innerHTML = "Legend Label :";
    tdLegendLabel.align = "right";
    tdLegendLabel.colSpan="2";
    
    var tdLegendControl = document.createElement('td');
    var text_box = document.createElement('input');
    text_box.type = "text";
    text_box.id = chartnm+"txtLegendName"+chartColumnId;
    text_box.name = chartnm+"txtLegendName";    
    tdLegendControl.appendChild(text_box);
    
    var tdRemoveColumn = document.createElement('td');
    var remove_icon = document.createElement('image');
    remove_icon.innerHTML = "<img align='middle' width='20px' height='20px' alt='' src='./images/delete.gif' onclick=\"removeColumn(this.parentNode.parentNode.parentNode.rowIndex,'"+controlnm+"')\">";
    tdRemoveColumn.appendChild(remove_icon);
    
    trYaxisColumn.appendChild(tdYaxisLabel);
    trYaxisColumn.appendChild(tdYaxisControl);
    trYaxisColumn.appendChild(tdRemoveColumn);
    trLegendLabel.appendChild(tdLegendLabel);
    trLegendLabel.appendChild(tdLegendControl);
    document.getElementById(chartnm+"ChartDetail").appendChild(trLegendLabel);
    document.getElementById(chartnm+"ChartDetail").appendChild(trYaxisColumn);
    loadComboNew(controlnm+chartColumnId,"","","MenuForm");
    chartColumnId++;
}

function removeColumn(rowid)
{    
    document.getElementById("showGraphDetails").deleteRow(rowid--);
    document.getElementById("showGraphDetails").deleteRow(rowid);
}

function validateChartData(chartnm)
{
    if(document.getElementById(chartnm+"Chart").checked)
    {
        show("chart");
        if(document.getElementById(chartnm+"TxtChartTitle").value == "")
        {
            alert("Please enter "+chartnm+ "chart title");
            document.getElementById(chartnm+"TxtChartTitle").focus(); 
            return false;                
        }
        if(chartnm != "pie")
        {
            if(document.getElementById(chartnm+"TxtXLabel").value == "")
            {
                alert("Please enter "+chartnm+ "chart X-axis label");
                document.getElementById(chartnm+"TxtXLabel").focus(); 
                return false;                
            }
        }        
        if(document.getElementById(chartnm+"CmbXaxisColumn").value == "-1")
        {
            alert("Please select "+chartnm+ "chart name column");
            document.getElementById(chartnm+"CmbXaxisColumn").focus(); 
            return false;                
        }
        if(chartnm != "pie")
        {
            if(document.getElementById(chartnm+"TxtYLabel").value == "")
            {
                alert("Please enter "+chartnm+ "chart Y-axis label");
                document.getElementById(chartnm+"TxtYLabel").focus(); 
                return false;                
            }
        }
        if(document.getElementById(chartnm+"CmbYaxisColumn").value == "-1")
        {
            alert("Please select "+chartnm+ "chart value column");
            document.getElementById(chartnm+"CmbYaxisColumn").focus(); 
            return false;                
        }  
        if(checkYColType(chartnm+"CmbYaxisColumn"))
        {
            if(document.getElementsByName(chartnm+"txtLegendName") != null)
            {
                var obj = document.getElementsByName(chartnm+"CmbYaxisColumn");
                for(var i=1;i<obj.length;i++)
                {        
                    if(document.getElementsByName(chartnm+"txtLegendName").item(i-1).value == "")
                    {
                        alert("Please enter "+chartnm+ "chart legend label");
                        document.getElementsByName(chartnm+"txtLegendName").item(i-1).focus(); 
                        return false;                
                    }       
                    else if(document.getElementsByName(chartnm+"CmbYaxisColumn").item(i).value == "-1")
                    {
                        alert("Please select "+chartnm+ "chart Y-axis column");
                        document.getElementsByName(chartnm+"CmbYaxisColumn").item(i).focus(); 
                        return false;                
                    }
                    else
                    {
                        if(!checkYColType(obj.item(i).id))
                        {
                            return false;
                        }
                    }
                }
            }
        }  
        else
        {
            return false;
        }
    }
    return true;
}

function checkYColType(yColControl)
{
    var qColumn = document.getElementById("divqueryresult").innerHTML;
    var column =  eval( "(" + qColumn + ")" );
    for(var i=0;i<column.colNames.length;i++)
    {        
        if(document.getElementById(yColControl).value == column.colNames[i])
        {  
            if(column.colTypes[i] != "Float" && column.colTypes[i] != "Integer" && column.colTypes[i] != "Double" && column.colTypes[i] != "Long" && column.colTypes[i] != "Number" && column.colTypes[i] != "BigDecimal")
            {
                alert("Please select numeric field for Y-column.");
                document.getElementById(yColControl).focus();
                return false;
            }               
        }
    }    
    return true;      
}

function validateControlValidation(tabnm)
{
    var selObj = document.getElementById(tabnm+"CmbValidation");    
    if (document.getElementById(tabnm+"CmbControl").value == "TextBox" || document.getElementById(tabnm+"CmbControl").value == "TextArea")
    {       
        showTextValidations(selObj);
    }    
    else if (document.getElementById(tabnm+"CmbControl").value == "Password")
    {
        showPasswordValidations(selObj);
    }    
    else if (document.getElementById(tabnm+"CmbControl").value == "ComboBox" || document.getElementById(tabnm+"CmbControl").value == "TextLikeCombo")
    {        
        showComboValidations(selObj);
    }        
    else if (document.getElementById(tabnm+"CmbControl").value == "CheckBox")
    {     
        showCheckBoxValidations(selObj);
    }    
    else if (document.getElementById(tabnm+"CmbControl").value == "Radio")
    {     
        showRadioValidations(selObj);
    }    
    else if (document.getElementById(tabnm+"CmbControl").value == "DatePicker")
    {
        showDateValidations(selObj);
    }    
    else
    {        
        removeAllValidations(selObj);
    }        
}

function showHideValidation(tabnm,hideOptionIDs)
{
    var selObj = document.getElementById(tabnm+"CmbValidation").options;      
    for(var i=0;i<selObj.length;i++)
    {   
        for(var j=0;j<hideOptionIDs.length;j++)
        {
            if(selObj[i].value == hideOptionIDs[j])
            {
                selObj[i].style.display = "";
            }            
        }        
    }
}

function showChkMndtry(tabnm)
{
    document.getElementById(tabnm+"chkMandatory").checked = false;    
    if (document.getElementById(tabnm+"CmbValidation").value != "-1")
    {
        document.getElementById(tabnm+"trMndtry").style.display = "";
    }
    else
    {
        document.getElementById(tabnm+"trMndtry").style.display = "none";
    }
}

function showDateValidations(combo)
{    
    combo.innerHTML = "<option value=\"-1\" selected=\"true\">--Select Validation--</option>";
    combo.innerHTML += "<option value=\"DateValidation\">Date</option>";
    combo.innerHTML += "<option value=\"LessThanFutureDate\">Less Than Future Date</option>";
    combo.innerHTML += "<option value=\"GreaterThanFutureDate\">Greater Than Future Date</option>";
}

function showPasswordValidations(combo)
{
    combo.innerHTML = "<option value=\"-1\" selected=\"true\">--Select Validation--</option>";
    combo.innerHTML += "<option value=\"Password\">Password</option>";
}

function showTextValidations(combo)
{
    combo.innerHTML = "<option value=\"-1\" selected=\"true\">--Select Validation--</option>";
    combo.innerHTML += "<option value=\"Numeric\">Only Numeric</option>";
    combo.innerHTML += "<option value=\"Alphabet\">Only Alphabet</option>";
    combo.innerHTML += "<option value=\"Company\">Company Validation</option>";
    combo.innerHTML += "<option value=\"Age\">Age</option>";
    combo.innerHTML += "<option value=\"PANNo\">PAN No</option>";
    combo.innerHTML += "<option value=\"FolioNo\">Folio No</option>";
    combo.innerHTML += "<option value=\"FAXNo\">FAX No</option>";
    combo.innerHTML += "<option value=\"MobileNo\">Mobile No</option>";
    combo.innerHTML += "<option value=\"PhoneNo\">Phone No</option>";
    combo.innerHTML += "<option value=\"Pincode\">Pincode</option>";
    combo.innerHTML += "<option value=\"NAV\">NAV</option>";
    combo.innerHTML += "<option value=\"ChequeNo\">Cheque No</option>";
    combo.innerHTML += "<option value=\"Email\">Email</option>";
    combo.innerHTML += "<option value=\"NJbrcode\">NJ brcode</option>";
}

function showComboValidations(combo)
{
    combo.innerHTML = "<option value=\"-1\" selected=\"true\">--Select Validation--</option>";
    combo.innerHTML += "<option value=\"DropDown\">DropDown Validation</option>";
    combo.innerHTML += "<option value=\"SelectLimit\">Select Limit</option>";
}

function showCheckBoxValidations(combo)
{
    combo.innerHTML = "<option value=\"-1\" selected=\"true\">--Select Validation--</option>";
    combo.innerHTML += "<option value=\"CheckBoxValidation\">CheckBox Validation</option>";
}

function showRadioValidations(combo)
{    
    combo.innerHTML = "<option value=\"-1\" selected=\"true\">--Select Validation--</option>";
    combo.innerHTML += "<option value=\"RadioValidation\">Radio Validation</option>";
}

function removeAllValidations(combo)
{
    combo.innerHTML = "<option value=\"-1\" selected=\"true\">--Select Validation--</option>";
}

function setGroupFooterCalc()
{
    var combo = document.getElementById("cmbGrpFooterCalculation");
    combo.innerHTML = "<option value=\"-1\">-- Select Calculation --</option>";
    combo.innerHTML += "<option value=\"stat_total\">Sum</option>";
    combo.innerHTML += "<option value=\"stat_average\">Average</option>";
    combo.innerHTML += "<option value=\"stat_min\">Minimum</option>";
    combo.innerHTML += "<option value=\"stat_max\">Maximum</option>";
    combo.innerHTML += "<option value=\"stat_count\">Count</option>";
}

function validateCntrlIDName(tab)
{
    if (document.getElementById(tab + "TxtId").value == "")
    {
        alert("Please Enter Control Id");
        document.getElementById(tab + "TxtId").focus();
        return false;
    }
    if (document.getElementById(tab + "TxtName").value == "")
    {
        alert("Please Enter Control Name");
        document.getElementById(tab + "TxtName").focus();
        return false;
    }
    
    var id = document.getElementById(tab + "TxtId").value;
    id = id.toString().toLowerCase();
    id = id.split(' ').join('')
    document.getElementById(tab + "TxtId").value = id;
    var name = document.getElementById(tab + "TxtName").value;
    name = name.toString().toLowerCase();
    name = name.split(' ').join('')
    document.getElementById(tab + "TxtName").value = name;
    
    if (!id.match("^[A-Za-z]{1}[A-Za-z0-9_]*$"))
    {
        alert("Invalid Control Id");
        document.getElementById(tab + "TxtId").focus();
        return false;
    }
    if (!name.match("^[A-Za-z]{1}[A-Za-z0-9_]*$"))
    {
        alert("Invalid Control Name");
        document.getElementById(tab + "TxtName").focus();
        return false;
    }
    
    for (var i = 0; i < keywords.length; i++)
    {
        if (id == keywords[i])
        {
            alert("Control Id Cannot Be Any Java Keyword");
            document.getElementById(tab + "TxtId").focus();
            return false;
        }
        if (name == keywords[i])
        {
            alert("Control Name Cannot Be Any Java Keyword");
            document.getElementById(tab + "TxtName").focus();
            return false;
        }
    }
    return true;
}

function validateSummaryReportData()
{
    if(document.getElementById("cmbProjectName").value ==  "-1")
    {
        alert("Please select project name");
        document.getElementById("cmbProjectName").focus();
        return false;
    }
    else if(document.getElementById("cmbAliasName").value ==  "-1")
    {
        alert("Please select alias name");
        document.getElementById("cmbAliasName").focus();
        return false;
    }
    else if(document.getElementById("txtQuery").value == "")
    {
        alert("Please enter query");
        document.getElementById("txtQuery").focus();
        return false;
    }
    return true;
}

function validateSummaryReportTabData()
{
    if(document.getElementById('txtReportTitle').value == '')
    {
        alert("Please enter report title");
        document.getElementById('txtReportTitle').focus();
        return false;
    }
    //    else if(document.getElementById("chkAddControl").checked && !validateColumnControl())
    //    {
    //        return false;
    //    }
    else if(!document.getElementById("noChart").checked && !document.getElementById("pieChart").checked && !document.getElementById("barChart").checked && !document.getElementById("lineChart").checked && !document.getElementById("areaChart").checked)
    {
        alert("You must choose atleast one option for chart..");
        show("chart");
        return false;
    }        
    else if(validateChartData("pie"))
    {
        if(validateChartData("bar"))
        {
            if(validateChartData("line"))
            {
                if(validateChartData("area"))
                {
                    showHide("PageFooter");
                    return true;
                }
                return false;
            }
            return false;
        }
        return false;
    }
    return true;
}

function getRefNumber()
{
    if(document.getElementById("cmbProjectName").value != "-1")
    {
        var params=getFormData(document.MenuForm);        
        getDataFilterNew("repgenv3.fin?cmdAction=getRefNumber", "cmbRefNo", params, false);
    }
}

function getWsdlMethods(tabnm)
{
    document.getElementById("fltrTxtWsdlUrl").value = document.getElementById("fltrTxtWsdlUrl").value.replace(/^\s+|\s+$/g,'');
    document.getElementById("fltrCmbWsMethod").innerHTML = "<option value=\"-1\">-- Select Method --</option>";
    if (document.getElementById("fltrTxtWsRetType") != null)
    {
        document.getElementById("fltrTxtWsRetType").value = "";
    }
    if (document.getElementById("fltrTxtWsParams") != null)
    {
        document.getElementById("fltrTxtWsParams").value = "";
    }
    if (document.getElementById("fltrTxtWsExps") != null)
    {
        document.getElementById("fltrTxtWsExps").value = "";
    }
    if (document.getElementById("fltrTxtWsdlUrl").value == "")
    {
        alert("Please Enter wsdl URL");
        return;
    }
    var params = getFormData(document.MenuForm);
    getSynchronousData("repgenv3.fin?cmdAction=getWsDetails", params, "fltrtdWsdl");
    if (document.getElementById("fltrTxtWsProject") == null || document.getElementById("fltrTxtWsIntrface") == null)
    {
        alert("WSDL Entry is Not As Per Standard");
        return;
    }
    else
    {
        params = getFormData(document.MenuForm);
        getSynchronousData("repgenv3.fin?cmdAction=getWsMethods", params, "fltrtdWsdl");
        if (document.getElementById("fltrTmpCmbWsMethod") != null)
        {
            document.getElementById("fltrCmbWsMethod").innerHTML = "<option value=\"-1\">-- Select Method --</option>";
            document.getElementById("fltrCmbWsMethod").innerHTML += document.getElementById("fltrTmpCmbWsMethod").innerHTML;
        }
    }
}

function getWsMethodParams(tabnm)
{
    if (document.getElementById("fltrCmbWsMethod").value == "-1")
    {
        alert("Please Select Method to Fill ComboBox");
        document.getElementById("fltrCmbWsMethod").focus();
        if (document.getElementById("fltrTxtWsRetType") != "null")
        {
            document.getElementById("fltrTxtWsRetType").value = "";
        }
        if (document.getElementById("fltrTxtWsParams") != "null")
        {
            document.getElementById("fltrTxtWsParams").value = "";
        }
        if (document.getElementById("fltrTxtWsExps") != "null")
        {
            document.getElementById("fltrTxtWsExps").value = "";
        }
        return;
    }
    else
    {        
        var params = getFormData(document.MenuForm);
        getSynchronousData("repgenv3.fin?cmdAction=getWsMethodParams", params, "fltrtdWsdl");
    }
}