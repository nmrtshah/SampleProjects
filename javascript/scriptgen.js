/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
var controlEventMap = new Array();
controlEventMap[0] = new Array();
controlEventMap[0][0] = 1;
controlEventMap[0][1] = 1;

function validateTestScriptValues() {
    try {
        
        if( document.getElementById("prjID").value == -1)   {
            alert("Select Project Name.");
            document.getElementById("prjID").focus();
            return false;
        }
        if ( document.getElementById("moduleID").value == -1 )   {
            alert("Select Module Name.");
            document.getElementById("moduleID").focus();
            return false;
        }       /*
        if ( document.getElementById("testCaseID").value == -1 )    {
            alert("Select Test Case.");
            document.getElementById("testCaseID").focus();
            return false;
        }
        if ( document.getElementById("browserID").value == -1 )     {
            alert("Select Browser Name.");
            document.getElementById("browserID").focus();
            return false;
        }
        if ( Trim(document.getElementById("validationMessage").value) == ""){
            alert("Enter Validation Message.");
            document.getElementById("validationMessage").focus();
            return false;
        }   */

        /*
        var totalControlCount = document.getElementsByName("htmlControlID").length;
        var totalEventCount = document.getElementsByName("eventID").length;

        var controlArray = document.getElementsByName("htmlControlID");
        var controlNameArray = document.getElementsByName("controlNameID");
        var accessArray = document.getElementsByName("accessID");
        var valueArray = document.getElementsByName("controlValue");
        var eventArray = document.getElementsByName("eventID");
        var messageArray = document.getElementsByName("message");
        var testCaseArray = document.getElementsByName("testCaseNatureID");

        for ( var ccount = 0 ; ccount < totalControlCount ; ccount++ )  {

            if ( controlArray[ccount].value == -1 ) {
                alert("Select HTML Control Type.");
                controlArray[ccount].focus();
                return false;
            }
            if ( Trim(controlNameArray[ccount].value) == "" )   {
                alert("Invalid Control Name.");
                controlArray[ccount].focus();
                return false;
            } else  {
                var cntval = Trim(controlNameArray[ccount].value);
                if ( cntval.indexOf(' ', 0) > 0 ||
                     cntval.indexOf('\"',0) > 0 ||
                     cntval.indexOf('\'',0) > 0 )  {
                     alert("Invalid Control Name.");
                     controlNameArray[ccount].focus();
                     return false;
                }
            }
            if ( accessArray[ccount].value == -1 )    {
                alert("Select Control Type.");
                controlArray[ccount].focus();
                return false;
            } else  {
                
            }
            for ( var ecount = 0 ; ecount < totalEventCount ; ecount++ )    {
                
            }
        }   */

        
        var count;
        var controlArray = document.getElementsByName("htmlControlID");
        for ( count = 0 ; count < controlArray.length ; count++ )    {
            if ( controlArray[count].value == -1 )  {
                alert("Select Control Type.");
                controlArray[count].focus();
                return false;
            }
        }
            
        controlArray = document.getElementsByName("controlNameID");
        for ( count = 0 ; count < controlArray.length ; count++ )   {
            if ( Trim(controlArray[count].value) == "" )  {
                alert("Invalid Control Name.");
                controlArray[count].focus();
                return false;
            } else  {
                var nameid = Trim(controlArray[count].value);
                if ( ( nameid.toString().indexOf(" ", 0) > 0 ) ||
                    ( nameid.toString().indexOf("\"",0) > 0 ) ||
                    ( nameid.toString().indexOf("'",0) > 0 ) )  {
                    alert ("Invalid Control Name.");
                    controlArray[count].focus();
                    return false;
                }
            }
        }

        controlArray = document.getElementsByName("accessID");
        for ( count = 0 ; count < controlArray.length ; count++ )   {
            if ( controlArray[count].value == -1 )    {
                alert("Select Control Type.");
                document.getElementsByName("htmlControlID")[count].focus();
                return false;
            }
            if ( controlArray[count].value == 7 || controlArray[count].value == 10 ) {
                var val = document.getElementsByName("controlValue");
                if ( isNaN(Trim(val[count].value)) )  {
                    alert("Invalid index number.");
                    val[count].focus();
                    return false;
                } else  {
                    if ( ! ( Trim(val[count].value) >=0 && Trim(val[count].value) <= 150 ) ) {
                        alert("Enter valid index.");
                        val[count].focus();
                        return false;
                    }
                }
            }
        }

        controlArray = document.getElementsByName("controlValue");
        var htmlcontrolid = document.getElementsByName("htmlControlID");
        for ( count = 0 ; count < controlArray.length ; count++ )    {
            
            if ( Trim(controlArray[count].value) == "" )    {
                alert("Invalid Control Value.");
                controlArray[count].focus();
                return false;
            } else {
                var cntval = controlArray[count].value;
                if ( ( cntval.toString().indexOf("\"",0) > 0 ) ||
                    ( cntval.toString().indexOf("'",0) > 0 ) )  {
                    alert ("Invalid control value.");
                    controlArray[count].focus();
                    return false;
                }                

                //Number 7 is for DateTime picker
                if ( htmlcontrolid[count].value == 7 )   {
                    if ( ! isValidDate(controlArray[count].value) )   {
                        alert("Invalid date. Enter in 'DD/MM/YYYY' or\n\n 'DD/MM/YYYY HH:MM' format & valid range.");
                        controlArray[count].focus();
                        return false;
                    }
                }
                if ( htmlcontrolid[count].value == 8 ) {
                    var cval = controlArray[count].value;
                    var flag = false;
                    if ( cval.indexOf('.jpg',1) > 0  ) flag = true;
                    if ( cval.indexOf('.jpeg',1) > 0 ) flag = true;
                    if ( cval.indexOf('.png',1) > 0 ) flag = true;
                    if ( cval.indexOf('.gif',1) > 0 ) flag = true;
                    if ( cval.indexOf('.bmp',1) > 0 ) flag = true;
                    if ( ! flag )    {
                        alert("Invalid Image Source.");
                        controlArray[count].focus();
                        return false;
                    }
                }
            }
        }   
                     
        controlArray = document.getElementsByName("eventID");
        var messageArray = document.getElementsByName("message");
        var testcasenature = document.getElementsByName("testCaseNatureID");
        for ( count = 0 ; count < controlArray.length ; count++ )   {

            if ( controlArray[count].value != "-1" ) {

                if ( Trim(messageArray[count].value) == "" ) {
                    alert("Invalid value.");
                    messageArray[count].focus();
                    return false;
                }
                if ( testcasenature[count].value == -1 )    {
                    alert("Select Test Case Nature.");
                    testcasenature[count].focus();
                    return false;
                }
            }
            if ( Trim(messageArray[count].value) != "" )   {

                var mval = Trim(messageArray[count].value);
                if ( ( mval.toString().indexOf("\"",0) > 0 ) ||
                    ( mval.toString().indexOf("'",0) > 0 ) )  {
                    alert ("Invalid control value.");
                    messageArray[count].focus();
                    return false;
                }
                /*
                if ( controlArray[count].value == -1 ) {
                    alert("Select Event Type.");
                    controlArray[count].focus();
                    return false;
                }   */
                if ( testcasenature[count].value == -1 )    {
                    alert("Select Test Case Nature.");
                    testcasenature[count].focus();
                    return false;
                }
            }
            if ( testcasenature[count].value != -1 )    {

                /*
                if ( controlArray[count].value == -1 ) {
                    alert("Select Event Type.");
                    controlArray[count].focus();
                    return false;
                }   */
                if ( Trim(messageArray[count].value) == "" ) {
                    alert("Invalid validation message.");
                    messageArray[count].focus();
                    return false;
                }
            }

        }

        for ( count = 0 ; count < testcasenature.length ; count++ ) {
            if ( testcasenature[count].value == 7 )    {
                if ( isNaN(messageArray[count].value) || (messageArray[count].value.toString().indexOf(".", 0) > 0 ) )    {
                    alert('Enter Numeric value');
                    messageArray[count].focus();
                    return false;
                }
            }            
        }

        var namearr = document.getElementsByName("controlNameID");
        var valuearr = document.getElementsByName("controlValue");
        var htmlarr = document.getElementsByName("htmlControlID");
        for ( count = 0 ;count < namearr.length ; count++  )    {
            if ( htmlarr[count].value == 3 || htmlarr[count].value == 4 )   {
                valuearr[count].value = namearr[count].value;
            }
        }
            
        var totalControlGroup = document.getElementById("controlsCount").value;
        document.getElementById("process").value = "generate";
        var arraydata = "";
        for ( var k = 0 ; k < totalControlGroup ; k++ ) {
            if ( controlEventMap[k][0] == 0 )   continue;
            
            arraydata += controlEventMap[k][1].toString();            

            if ( ! ( k == (totalControlGroup-1) ) ) arraydata += ",";
        }
        document.getElementById("arr").value = arraydata;
        var url = "scriptgen.fin?cmdAction=generateScript";
        document.forms[0].action = url;
        return confirmSubmit();
    }
    catch (err)   {
        //        alert("main try-catch block : "+err.message);
        return false;
    }
}

function confirmSubmit()    {
    return confirm("Are you sure to submit the form?");
}

function isValidDate(sdate)  {

    var dateregex = /^(0[1-9]|(1|2)[0-9]|3[0-1]){1}\/(0[1-9]{1}|1[0-2]{1}){1}\/(19[0-9]{2}|2[0-9]{3})$/
    var datetimeregex = /^(0[1-9]|(1|2)[0-9]|3[0-1]){1}\/(0[1-9]{1}|1[0-2]{1}){1}\/(20[0-9]{2}|2[0-9]{3}) (0[0-9]|1[0-9]|2[0-3]){1}:(0[1-9]|(1|2|3|4|5)[0-9]){1}$/

    if ( sdate.length == 10 )   return dateregex.test(sdate);
    if ( sdate.length == 16 )   return datetimeregex.test(sdate);
    return false;
}

function changeValidateValue(context)   {
    if ( context.checked )  {
        document.getElementById("validate").value = "true";
    } else {
        document.getElementById("validate").value = "false";
    }
}

function resetAll() {

    document.getElementById("controlsCount").value = "1";
    document.getElementById("eventsCount").value = "1";
    document.getElementById("arr").value = "";
    document.getElementById("process").value = "load";
    //    document.getElementById("validate").value = "false";
    document.scriptgenform.reset();
}

function isUrl(s) {
    var regexp = /(http):\/\/(\w+:{0,1}\w*@)?(\S+)(:[0-9]+)?(\/|\/([\w#!:.?+=&%@!\-\/ ]))?/
    return regexp.test(s);
}

//AJAX Call to scriptgen.fin
function onChangeProject(projectID)  {
    if ( projectID == -1 ) {
        document.getElementById("moduleID").options.length = 0;
        document.getElementById("testCaseID").options.length = 0;
        document.getElementById("moduleID").options.add(new Option("--Select Module Name--","-1"));
        document.getElementById("testCaseID").options.add(new Option("--Select Test Case--","-1"));
    } else  {
        document.getElementById("testCaseID").options.length = 0;
        document.getElementById("testCaseID").options.add(new Option("--Select Test Case--","-1"));
        var url = 'scriptgen.fin?cmdAction=getModuleTreeList';
        var params = getalldata(document.scriptgenform);
        getData_sync(url, 'modulelist', params);
    }
}

function onChangeModule(moduleID)   {
    if ( moduleID == -1 ) {
        document.getElementById("testCaseID").options.length = 0;
        document.getElementById("testCaseID").options.add(new Option("--Select Test Case--","-1"));
    } else  {
        var url = 'scriptgen.fin?cmdAction=getTestCaseList&';
        var params = getalldata(document.scriptgenform);
        getData_sync(url, 'testcaselist' , params);
    }
}

function onChangeControl(controlID,counter) {
    if ( controlID != -1 ) {
        var url = 'scriptgen.fin?cmdAction=getAccessTypeList&controlID='+controlID+'&';
        var params = getalldata(document.scriptgenform);
        getData_sync(url, "accesslist"+counter, params);
    }
}

function removeControlGroup(counter)    {

    if ( confirmControlDelete() )  {

        var element = document.getElementById(counter);
        element.parentNode.removeChild(element);
        controlEventMap[counter][0] = 0;
        controlEventMap[counter][1] = 0;
    }
}

function removeControlValueGroup(context,counter)    {

    if ( confirmEventDelete() )   {

        var element = document.getElementById(context);
        element.parentNode.removeChild(element);
        controlEventMap[counter][1]--;
    }
}

function confirmControlDelete()    {
    return confirm("Are you sure to remove this control group?");
}

function confirmEventDelete()   {
    return confirm("Are you sure to remove this event group?");
}

function addControlGroup(context,counter){
   
    var local = counter;
    var val = local;
    val++;
   
    controlEventMap[counter] = new Array();
    controlEventMap[counter][0] = val;
    controlEventMap[counter][1] = 1;

    var mainTableNode;

    //Reach to mainformtable TABLE ELEMENT to add additional row of control group
    while( ! ( context.parentNode.id.toString() == "controleventtable" ) )  {
        context = context.parentNode;
    }

    mainTableNode = context.parentNode;    

    var rowCount = mainTableNode.rows.length;

    var mainRow = mainTableNode.insertRow(rowCount);
    mainRow.setAttribute("id", counter);
    var mainRowCell1 = mainRow.insertCell(0);
    mainRowCell1.setAttribute("class", "tdleftdata");
    mainRowCell1.setAttribute("valign", "top");

    var mainRowCell2 = mainRow.insertCell(1);
    mainRowCell2.setAttribute("class", "tdleftdata");


    //First Table [control details group]
    var innerTable1 = document.createElement("table");
    innerTable1.setAttribute("width", "100%");
    innerTable1.setAttribute("cellpadding", "0px");
    innerTable1.setAttribute("cellspacing", "0px");
    innerTable1.setAttribute("align", "center");

    var innerTable1RowCount = innerTable1.rows.length;

    var innerTable1Row1 = innerTable1.insertRow(innerTable1RowCount);

    var t1cell0 = innerTable1Row1.insertCell(0);

    t1cell0.setAttribute("align", "left");
    var imgElement = document.createElement("img");
    imgElement.setAttribute("alt", "");
    imgElement.setAttribute("src","./images/delete_1.gif");
    imgElement.setAttribute("width","20px");
    imgElement.setAttribute("height","20px");
    imgElement.setAttribute("align","left");
    imgElement.setAttribute("onclick", "removeControlGroup("+local+")");
    t1cell0.appendChild(imgElement);


    var t1cell1 = innerTable1Row1.insertCell(1);
    t1cell1.setAttribute("class", "tdleftdata");
    var selectionElement = document.createElement("SELECT");    
    selectionElement.name = "htmlControlID";
    selectionElement.id = "htmlControlID";
    selectionElement.setAttribute("onchange", "onChangeControl(this.value,"+val+")");
    selectionElement.setAttribute("style", "width:auto");
    var controlList = document.getElementById("htmlControlID");
    selectionElement.innerHTML = controlList.innerHTML;
    t1cell1.appendChild(selectionElement);

    var t1cell2 = innerTable1Row1.insertCell(2);
    t1cell2.setAttribute("class", "tdleftdata");
    var textElement1 = document.createElement("input");
    textElement1.setAttribute("type", "text");
    textElement1.setAttribute("style", "width:130px");
    textElement1.name = "controlNameID";
    textElement1.id = "controlNameID";
    t1cell2.appendChild(textElement1);

    var t1cell3 = innerTable1Row1.insertCell(3);
    t1cell3.setAttribute("class", "tdleftdata");
    t1cell3.setAttribute("id","accesslist"+val);
    t1cell3.setAttribute("width", "90px");
    var t1selectElement1 = document.createElement("SELECT");    
    t1selectElement1.name = "accessID";
    t1selectElement1.id = "accessID";
    t1selectElement1.setAttribute("style", "width:auto");
    t1selectElement1.options.add(new Option("Type","-1"));
    t1cell3.appendChild(t1selectElement1);

    var t1cell4 = innerTable1Row1.insertCell(4);
    t1cell4.setAttribute("class", "tdleftdata");
    t1cell4.setAttribute("width", "110px");
    var textElement2 = document.createElement("input");
    textElement2.setAttribute("type", "text");
    textElement2.setAttribute("style", "width:130px");
    textElement2.name = "controlValue";
    textElement2.id = "controlValue";
    t1cell4.appendChild(textElement2);


    //Second Table [control event, test case nature details]
    var innerTable2 = document.createElement("table");
    innerTable2.setAttribute("width", "100%");
    innerTable2.setAttribute("cellpadding", "0px");
    innerTable2.setAttribute("cellspacing", "0px");
    innerTable2.setAttribute("align", "center");

    var innerTable2RowCount = innerTable2.rows.length;

    var innerTable2Row1 = innerTable2.insertRow(innerTable2RowCount);

    var t2cell1 = innerTable2Row1.insertCell(0);
    var addEventImage = document.createElement("img");
    addEventImage.setAttribute("src", "./images/add.gif");
    addEventImage.setAttribute("width", "20px");
    addEventImage.setAttribute("height", "20px");
    addEventImage.setAttribute("align", "middle");
    t2cell1.setAttribute("class", "tdleftdata");

    var t2cell2 = innerTable2Row1.insertCell(1);
    t2cell2.setAttribute("class", "tdleftdata");
    var selectElement1 = document.createElement("SELECT");    
    selectElement1.name = "eventID";
    selectElement1.id = "eventID";
    selectElement1.setAttribute("style", "width:auto");
    var eventListCombo = document.getElementById("eventID");
    selectElement1.innerHTML = eventListCombo.innerHTML;
    t2cell2.appendChild(selectElement1);

    var t2cell3 = innerTable2Row1.insertCell(2);
    t2cell3.setAttribute("class", "tdleftdata");
    var inputElement = document.createElement("input");
    inputElement.type = "text";
    inputElement.setAttribute("style", "width:130px");
    inputElement.name = "message";
    inputElement.id = "message";
    t2cell3.appendChild(inputElement);

    var t2cell4 = innerTable2Row1.insertCell(3);
    t2cell4.setAttribute("class", "tdleftdata");
    var selectElement2 = document.createElement("SELECT");    
    selectElement2.name = "testCaseNatureID";
    selectElement2.id = "testCaseNatureID";
    selectElement2.setAttribute("style", "width:auto");
    var testCaseNatureListCombo = document.getElementById("testCaseNatureID");
    selectElement2.innerHTML = testCaseNatureListCombo.innerHTML;
    t2cell4.appendChild(selectElement2);    

    var eventlocal = local;
    eventlocal++;
    addEventImage.setAttribute("onclick","addControlValueGroup(this,"+eventlocal+")");
    t2cell1.appendChild(addEventImage);

    mainRowCell1.appendChild(innerTable1);
    mainRowCell2.appendChild(innerTable2);

    ++local;
    document.getElementById("btnAddControlGroup").setAttribute("onclick","addControlGroup(this,"+local+")" );

    var cnt = document.getElementById("controlsCount").value;
    var ecnt = document.getElementById("eventsCount").value;
    document.getElementById("controlsCount").value = ++cnt;
    document.getElementById("eventsCount").value = ++ecnt;
}

function addControlValueGroup(context,controlcount)  {

    var local = controlcount;
    --local;

    controlEventMap[local][1]++;

    var baseTableNode;

    //Reach to controlEventValueDetailTable Table ELEMENT to add additional row of control value event
    baseTableNode = context.parentNode.parentNode.parentNode.parentNode;

    var rowCount = baseTableNode.rows.length;

    var newRowID = controlcount + "" + ++(document.getElementById("eventsCount").value) ;

    var newRow = baseTableNode.insertRow(rowCount);
    newRow.setAttribute("id", newRowID);
    //    var newCell = newRow.insertCell(0);
    //    newCell.setAttribute("colspan", "100%");
    //
    //    var innerTable = document.createElement("table");
    //    innerTable.setAttribute("width", "100%");
    //    innerTable.setAttribute("align", "center");
    //    innerTable.setAttribute("cellpadding", "0px");
    //    innerTable.setAttribute("cellspacing", "0px");
    //
    //    var innerTableRowCount = innerTable.rows.length;
    //
    //    var innerRow1 = innerTable.insertRow(innerTableRowCount);
    
    
    var cell1 = newRow.insertCell(0);
    cell1.setAttribute("class", "tdleftdata");
    var imgElement = document.createElement("img");
    imgElement.setAttribute("alt", "");
    imgElement.setAttribute("src","./images/delete_1.gif");
    imgElement.setAttribute("width","20px");
    imgElement.setAttribute("height","20px");
    imgElement.setAttribute("align", "middle");
    imgElement.setAttribute("onclick", "removeControlValueGroup("+newRowID+","+local+")");
    cell1.appendChild(imgElement);
    
    var cell2 = newRow.insertCell(1);
    cell2.setAttribute("class", "tdleftdata");
    var selectElement1 = document.createElement("SELECT");    
    selectElement1.name = "eventID";
    selectElement1.id = "eventID";
    selectElement1.setAttribute("style", "width:auto");
    var eventListCombo = document.getElementById("eventID");
    selectElement1.innerHTML = eventListCombo.innerHTML;
    cell2.appendChild(selectElement1);

    var cell3 = newRow.insertCell(2);
    cell3.setAttribute("class", "tdleftdata");
    var inputElement = document.createElement("input");
    inputElement.type = "text";
    inputElement.setAttribute("style", "width:130px");
    inputElement.name = "message";
    inputElement.id = "message";
    cell3.appendChild(inputElement);

    var cell4 = newRow.insertCell(3);
    cell4.setAttribute("class", "tdleftdata");
    var selectElement2 = document.createElement("SELECT");    
    selectElement2.name = "testCaseNatureID";
    selectElement2.id = "testCaseNatureID";
    selectElement2.setAttribute("style", "width:auto");
    var testCaseNatureListCombo = document.getElementById("testCaseNatureID");
    selectElement2.innerHTML = testCaseNatureListCombo.innerHTML;
    cell4.appendChild(selectElement2);

//    newCell.appendChild(innerTable);    
}

function Trim(TRIM_VALUE)
{
    if(TRIM_VALUE.length < 1)
    {
        return "";
    }
    TRIM_VALUE = RTrim(TRIM_VALUE);
    TRIM_VALUE = LTrim(TRIM_VALUE);
    if(TRIM_VALUE=="")
    {
        return "";
    }
    else
    {
        return TRIM_VALUE;
    }
}
function RTrim(VALUE)
{
    var w_space = String.fromCharCode(32);
    var v_length = VALUE.length;
    var strTemp = "";
    if(v_length < 0)
    {
        return "";
    }
    var iTemp = v_length -1;

    while(iTemp > -1)
    {
        if(VALUE.charAt(iTemp) != w_space)
        {
            strTemp = VALUE.substring(0,iTemp +1);
            break;
        }
        iTemp = iTemp-1;
    }
    return strTemp;
}
function LTrim(VALUE)
{
    var w_space = String.fromCharCode(32);
    if(v_length < 1)
    {
        return "";
    }
    var v_length = VALUE.length;
    var strTemp = "";
    var iTemp = 0;

    while(iTemp < v_length)
    {
        if(VALUE.charAt(iTemp) != w_space)
        {
            strTemp = VALUE.substring(iTemp,v_length);
            break;
        }
        iTemp = iTemp + 1;
    }
    return strTemp;
}

function runSeleniumTest()  {
    if ( confirmTest() )    {
        var url = "scriptgen.fin?cmdAction=runTest";
        var param = getalldata(document.seleniumscriptform);
        getData_sync(url , 'scriptdetail' , param);
    }
}

function confirmTest()  {
    return confirm("Are you sure to run test?");
}