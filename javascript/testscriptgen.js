/**
 *  @author njuser
 */
var controlEventMap = new Array();

function validateTGetControl()
{
    if (document.getElementById("prjID").value == -1)
    {
        alert("Plase select project name.");
        document.getElementById("prjID").focus();
        return false;
    }
    else if (document.getElementById("moduleID").value == -1)
    {
        alert("Please select module name.");
        document.getElementById("moduleID").focus();
        return false;
    }
    else if (document.getElementById("cmbServerName").value == "-1")
    {
        alert("Plase select server name.");
        document.getElementById("cmbServerName").focus();
        return false;
    }
    else if (document.getElementById("txtUserName").value == "")
    {
        alert("Please enter user name");
        document.getElementById("txtUserName").focus();
        return false;
    }
    else if (document.getElementById("txtPassword").value == "")
    {
        alert("Plase enter password");
        document.getElementById("txtPassword").focus();
        return false;
    }
    else
    {
        return true;
    }
}

//function validateTestScriptValues1()
//{
//    try
//    {
//        var count;
//        var controlArray;
//        
//        var tempcontrolArray = document.getElementsByName("controlValue");
//        var vcontrolArray = new Array();
//        var i = 0;
//        for (count = 0 ; count < tempcontrolArray.length ; count++)
//        {
//            if (tempcontrolArray[count].disabled == true) continue;
//            vcontrolArray[i++] = tempcontrolArray[count];
//        }
//        
//        controlArray = document.getElementsByName("accessID");
//              
//        for (count = 0 ; count < controlArray.length ; count++)
//        {
//                  
//            if (controlArray[count].value == 7)
//            {
//                if (isNaN(vcontrolArray[count].value) || vcontrolArray[count].value < 0 || vcontrolArray[count].value.toString().indexOf('.', 0) > 0)
//                {
//                    alert("Enter valid index. single combox index.line no : 69 ");
//                    vcontrolArray[count].focus();
//                    return false;
//                }
//            }
//            if (controlArray[count].value == 10 && !vcontrolArray[count].value.match(0))
//            {alert("vcontrolarray value ="+ vcontrolArray[count].value);
//                if (!vcontrolArray[count].value.match(/[0-9]/) || vcontrolArray[count].value.match(/,,/i) || vcontrolArray[count].value.match(/,$/i))
//                {
//                    alert("Enter valid index. multi combox index..line no : 78 ");
//                    vcontrolArray[count].focus();
//                    return false;
//                }
//            }
//        }
//        
//        tempcontrolArray = document.getElementsByName("controlValue");
//        controlArray = new Array();
//        i = 0;
//        for (count = 0 ; count < tempcontrolArray.length ; count++)
//        {
//            if (tempcontrolArray[count].disabled == true) continue;
//            controlArray[i++] = tempcontrolArray[count];
//        }
//        var htmlcontrolid = document.getElementsByName("htmlControlID");
//        for (count = 0 ; count < controlArray.length ; count++)
//        {
//            if (htmlcontrolid[count].value == 3 || htmlcontrolid[count].value == 9 || htmlcontrolid[count].value == 10)
//                continue;
//            
//            if (htmlcontrolid[count].value == 1 || htmlcontrolid[count].value == 2)
//            {
//                if (controlArray[count].value == '')
//                {
//                    alert("Invalid control value. line number : 103");
//                    controlArray[count].focus();
//                    return false;
//                }
//            }
//            
//            if (htmlcontrolid[count].value == 4)
//            {
//                if (controlArray[count].value == '')
//                {
//                    alert("Invalid control value. line no : 113");
//                    controlArray[count].focus();
//                    return false;
//                }
//                
//                if (controlArray[count].value != '')
//                {
//                    if (controlArray[count].value.toString().indexOf('.', 0) > 0)
//                    {
//                        alert("Enter valid index. line no : 122");
//                        controlArray[count].focus();
//                        return false;
//                    }
//                }
//            }
//            
//            if (htmlcontrolid[count].value == 5 || htmlcontrolid[count].value == 6)
//            {
//                if (controlArray[count].value == '')
//                {
//                    alert("Invalid Control value. line no : 133");
//                    controlArray[count].focus();
//                    return false;
//                }
//            }
//            if (controlArray[count].value != '')
//            {
//                var cntval = controlArray[count].value;
//                if ((cntval.toString().indexOf("\"",0) > 0) ||
//                    (cntval.toString().indexOf("'",0) > 0) || 
//                    (htmlcontrolid[count].value == 5 && cntval.toString().indexOf(",",0) > 0) ||
//                    (htmlcontrolid[count].value == 6 && controlArray[count].value.match(/,$/i)))
//                {
//                    alert ("Invalid control value. line no : 146");
//                    controlArray[count].focus();
//                    return false;
//                }
//            }
//            
//            //Number 7 is for DateTime picker
//            if (htmlcontrolid[count].value == 7)
//            {
//                if (controlArray[count].value != "")
//                {
//                    if (!isValidDate(controlArray[count].value))
//                    {
//                        alert("Invalid date. Enter in '31-12-9999 or 31/12/9999' OR\n\n '31-12-9999 24:59 or 31/12/9999 24:59' format.");
//                        controlArray[count].focus();
//                        return false;
//                    }
//                }
//            }
//            
//            if (htmlcontrolid[count].value == 8)
//            {
//                var cval = controlArray[count].value;
//                var flag = false;
//                if (cval.indexOf('.jpg',1) > 0) flag = true;
//                if (cval.indexOf('.jpeg',1) > 0) flag = true;
//                if (cval.indexOf('.png',1) > 0) flag = true;
//                if (cval.indexOf('.gif',1) > 0) flag = true;
//                if (cval.indexOf('.bmp',1) > 0) flag = true;
//                if (!flag)
//                {
//                    alert("Invalid Image Source.");
//                    controlArray[count].focus();
//                    return false;
//                }
//            }
//        }
//        
//        var messageArray = document.getElementsByName("message");
//        var testcasenature = document.getElementsByName("testCaseNatureID");
//        for (count = 0 ; count < (messageArray.length-1) ; count++)
//        {
//            //if (testcasenature[count].value == 8) continue;
//            
//            if (messageArray[count+1].value != "-1" || messageArray[count+1].value != "")
//            {
//                var mval = messageArray[count+1].value;
//                if ((mval.toString().indexOf("\"",0) > 0) ||
//                    (mval.toString().indexOf("'",0) > 0))
//                {
//                    alert ("Invalid control name. line no : 196");
//                    messageArray.item(count+1).focus();
//                    return false;
//                }
//            }
//            if (testcasenature[count].value != "-1")
//            {
//                if (messageArray[count+1].value == "-1" || messageArray[count+1].value == "")
//                {
//                    alert("Invalid control Name ID or text value.");
//                    messageArray.item(count+1).focus();
//                    return false;
//                }
//            }
//        }
//        
//        for (count = 0 ; count < testcasenature.length ; count++)
//        {
//            if (testcasenature[count].value == 7)
//            {
//                if (isNaN(messageArray[count+1].value) || (messageArray[count+1].value.toString().indexOf(".", 0) > 0))
//                {
//                    alert('Enter Numeric value line no : 218');
//                    messageArray[count+1].focus();
//                    return false;
//                }
//            }
//        }
//        
//        var totalControlGroup = document.getElementById("controlsCount").value;
//        document.getElementById("process").value = "generate";
//        var arraydata = "";
//        for (var k = 0 ; k < totalControlGroup ; k++)
//        {
//            if (controlEventMap[k][0] == 0) continue;
//            
//            arraydata += controlEventMap[k][1].toString() + ",";
//        }
//        document.getElementById("arr").value = arraydata.substring(0, (arraydata.length-1));
//        var url = "scriptgen.fin?cmdAction=generateScript";
//        document.scriptgenAddform.action = url;
//        if (arraydata == "")
//            return false;
//        return confirmSubmit();
//    }
//  
//    catch (err) {
//        return false;
//    }
//    return true;
//}

function validateTestScriptValues()
{
    try
    {
        if (validateTGetControl())
        {
            if (document.getElementById('htmlControlID') == null)
            {
                alert('No control is available to test.');
                return false;
            }
            else
            {
                var count;
                var controlArray;
        
                var tempcontrolArray = document.getElementsByName("controlValue");
                var vcontrolArray = new Array();
                var i = 0;
                for (count = 0 ; count < tempcontrolArray.length ; count++)
                {
                    if (tempcontrolArray[count].disabled == true) continue;
                    vcontrolArray[i++] = tempcontrolArray[count]; 
                }

                controlArray = document.getElementsByName("accessID");
                for (count = 0 ; count < controlArray.length ; count++)
                {
                    //                    if (controlArray[count].value == 7 || controlArray[count].value == 10)
                    //                    {
                    //                        if (isNaN(vcontrolArray[count].value))
                    //                        {
                    //                            alert("Invalid index number.");
                    //                            vcontrolArray[count].focus();
                    //                            return false;
                    //                        }
                    //                        else
                    //                        {
                    //                            if (vcontrolArray[count].value < 0)
                    //                            {
                    //                                alert("Enter valid index.");
                    //                                vcontrolArray[count].focus();
                    //                                return false;
                    //                            }
                    //                            if (vcontrolArray[count].value.toString().indexOf('.', 0) > 0)
                    //                            {
                    //                                alert("Enter valid index.");
                    //                                vcontrolArray[count].focus();
                    //                                return false;
                    //                            }
                    //                        }
                    //                    }
                    
                    if (controlArray[count].value == 7)
                    {
                        if (isNaN(vcontrolArray[count].value))
                        {
                            alert("Invalid index number.");
                            vcontrolArray[count].focus();
                            return false;
                        }
                        else
                        {
                            if (vcontrolArray[count].value < 0 || vcontrolArray[count].value.toString().indexOf('.', 0) > 0)
                            {
                                alert("Enter valid index.");
                                vcontrolArray[count].focus();
                                return false;
                            }
                        }
                    }
                    if (controlArray[count].value == 10 && !vcontrolArray[count].value.match(0))
                    {
                        if (!vcontrolArray[count].value.match(/[0-9]/) || vcontrolArray[count].value.match(/,,/i) || vcontrolArray[count].value.match(/,$/i))
                        {
                            alert("Enter valid index.");
                            vcontrolArray[count].focus();
                            return false;
                        }
                    }
                }

                tempcontrolArray = document.getElementsByName("controlValue");
                controlArray = new Array();
                i = 0;
                for (count = 0 ; count < tempcontrolArray.length ; count++)
                {
                    if (tempcontrolArray[count].disabled == true) continue;
                    controlArray[i++] = tempcontrolArray[count];
                }
                var htmlcontrolid = document.getElementsByName("htmlControlID");
                for (count = 0 ; count < controlArray.length ; count++)
                {
                    if (htmlcontrolid[count].value == 3 || htmlcontrolid[count].value == 9 || htmlcontrolid[count].value == 10)
                    {
                        continue;
                    }

                    if (htmlcontrolid[count].value == 1 || htmlcontrolid[count].value == 2)
                    {
                        if (controlArray[count].value == '')
                        {
                            alert("Invalid control value.");
                            controlArray[count].focus();
                            return false;
                        }
                    }

                    if (htmlcontrolid[count].value == 4)
                    {
                        if (controlArray[count].value == '')
                        {
                            alert("Invalid control value.");
                            controlArray[count].focus();
                            return false;
                        }
                        if (controlArray[count].value != '')
                        {
                            if (controlArray[count].value.toString().indexOf('.', 0) > 0)
                            {
                                alert("Enter valid index.");
                                controlArray[count].focus();
                                return false;
                            }
                        }
                    }

                    if (htmlcontrolid[count].value == 5 || htmlcontrolid[count].value == 6)
                    {
                        if (controlArray[count].value == '')
                        {
                            alert("Invalid Control value.");
                            controlArray[count].focus();
                            return false;
                        }
                    }
                    if (controlArray[count].value != '')
                    {
                        var cntval = controlArray[count].value;
                        if ((cntval.toString().indexOf("\"",0) > 0) ||
                            (cntval.toString().indexOf("'",0) > 0) || 
                            (htmlcontrolid[count].value == 5 && cntval.toString().indexOf(",",0) > 0) ||
                            (htmlcontrolid[count].value == 6 && controlArray[count].value.match(/,$/i)))
                            {
                            alert ("Invalid control value.");
                            controlArray[count].focus();
                            return false;
                        }
                    }

                    //Number 7 is for DateTime picker
                    if (htmlcontrolid[count].value == 7)
                    {
                        if (controlArray[count].value != "")
                        {
                            if (!isValidDate(controlArray[count].value))
                            {
                                alert("Invalid date. Enter in '31-12-9999 or 31/12/9999' OR\n\n '31-12-9999 24:59 or 31/12/9999 24:59' format.");
                                controlArray[count].focus();
                                return false;
                            }
                        }
                    }

                    if (htmlcontrolid[count].value == 8)
                    {
                        var cval = controlArray[count].value;
                        var flag = false;
                        if (cval.indexOf('.jpg',1) > 0) flag = true;
                        if (cval.indexOf('.jpeg',1) > 0) flag = true;
                        if (cval.indexOf('.png',1) > 0) flag = true;
                        if (cval.indexOf('.gif',1) > 0) flag = true;
                        if (cval.indexOf('.bmp',1) > 0) flag = true;
                        if (!flag)
                        {
                            alert("Invalid Image Source.");
                            controlArray[count].focus();
                            return false;
                        }
                    }
                }

                var messageArray = document.getElementsByName("message");
                var testcasenature = document.getElementsByName("testCaseNatureID");
                for (count = 0 ; count < (messageArray.length-1) ; count++)
                {
                    //if (testcasenature[count].value == 8) continue;
                    if (messageArray[count+1].value != "-1" || messageArray[count+1].value != "")
                    {
                        var mval = messageArray[count+1].value;
                        if ((mval.toString().indexOf("\"",0) > 0) ||
                            (mval.toString().indexOf("'",0) > 0))
                            {
                            alert ("Invalid control name.");
                            messageArray.item(count+1).focus();
                            return false;
                        }
                    }
                    if (testcasenature[count].value != "-1")
                    {
                        if (messageArray[count+1].value == "-1" || messageArray[count+1].value == "")
                        {
                            alert("Invalid control Name ID or text value.");
                            messageArray.item(count+1).focus();
                            return false;
                        }
                    }
                }

                for (count = 0 ; count < testcasenature.length ; count++)
                {
                    if (testcasenature[count].value == 7)
                    {
                        if (isNaN(messageArray[count+1].value) || (messageArray[count+1].value.toString().indexOf(".", 0) > 0))
                        {
                            alert('Enter Numeric value');
                            messageArray[count+1].focus();
                            return false;
                        }
                    }
                }

                var totalControlGroup = document.getElementById("controlsCount").value;
                document.getElementById("process").value = "generate";
                var arraydata = "";
                for (var k = 0 ; k < totalControlGroup ; k++)
                {
                    if (controlEventMap[k][0] == 0)
                    {
                        continue;
                    }
                    arraydata += controlEventMap[k][1].toString() + ",";
                }
                document.getElementById("arr").value = arraydata.substring(0, (arraydata.length-1));
                var url = "scriptgen.fin?cmdAction=generateScript";
                document.scriptgenAddform.action = url;
                if (arraydata == "")
                {
                    return false;
                }
                return confirmSubmit();
            }
        }
        else
        {
            return false;
        }
    }
    catch (err)
    {
        return false;
    }
    return true;
}

function confirmSubmit()
{
    var ans = confirm("Are you sure to submit the form?");
    if (ans == true)
    {
        document.getElementById("etmpdata").innerHTML = '';
        document.getElementById("ctmpdata").innerHTML = '';
        return true;
    }
    return false;
}

function isValidDate(sdate)
{
    var dateregex = /^(0[1-9]|(1|2)[0-9]|3[0-1]){1}(\/|-)(0[1-9]{1}|1[0-2]{1}){1}(\/|-)(19[0-9]{2}|2[0-9]{3})$/
    var datetimeregex = /^(0[1-9]|(1|2)[0-9]|3[0-1]){1}(\/|-)(0[1-9]{1}|1[0-2]{1}){1}(\/|-)(20[0-9]{2}|2[0-9]{3}) (0[0-9]|1[0-9]|2[0-3]){1}:(0[1-9]|(1|2|3|4|5)[0-9]){1}$/

    if (sdate.length == 10)
    {
        return dateregex.test(sdate);
    }
    if (sdate.length == 16)
    {
        return datetimeregex.test(sdate);
    }
    return false;
}

function Addload(page)
{
    //    document.getElementById("controlsCount").value = "1";
    //    document.getElementById("eventsCount").value = "1";
    //    document.getElementById("arr").value = "";
    //    document.getElementById("process").value = "load";
    //    document.scriptgenAddform.reset();
    if (page != null && page != "")
    {
        showTab(page);
    }
}

function isUrl(s)
{
    var regexp = /(http|https):\/\/(\w+:{0,1}\w*@)?(\S+)(:[0-9]+)?(\/|\/([\w#!:.?+=&%@!\-\/ ]))?/
    return regexp.test(s);
}

//AJAX Call to scriptgen.fin
function onChangeProject(projectID)
{
    if (projectID == -1)
    {
        document.getElementById("moduleID").options.length = 0;
        if (document.getElementById("testCaseID") != null)
        {
            document.getElementById("testCaseID").options.length = 0;
            document.getElementById("testCaseID").options.add(new Option("--Select Test Case--","-1"));
        }
        document.getElementById("moduleID").options.add(new Option("--Select Module Name--","-1"));
    }
    else
    {
        if (document.getElementById("testCaseID") != null)
        {
            document.getElementById("testCaseID").options.length = 0;
            document.getElementById("testCaseID").options.add(new Option("--Select Test Case--","-1"));
        }
        var url = 'scriptgen.fin?cmdAction=getModuleTreeList&prjID='+projectID;
        getData (url , "modulelist");
    }
}

function onChangeTestCase(projectID,moduleID,testCaseID)
{
    if (projectID != -1 && moduleID != -1 && testCaseID != -1)
    {
        var url = 'scriptgen.fin?cmdAction=getTestCaseValuesList&prjID='+projectID+'&moduleID='+moduleID+'&testCaseID='+testCaseID;
        getData(url,'testcasevaluelist');
    }
}

function onChangeModule(moduleID)
{
    if (document.getElementById("testCaseID") != null)
    {
        if (moduleID == -1)
        {
            document.getElementById("testCaseID").options.length = 0;
            document.getElementById("testCaseID").options.add(new Option("--Select Test Case--","-1"));
        }
        else
        {
            var url = 'scriptgen.fin?cmdAction=getTestCaseList&prjID='+document.getElementById("prjID").value+'&moduleID='+moduleID;
            getData (url , "testcaselist");
        }
    }
}

function changeInput(type)
{
    if (type == 'U')
    {
        document.getElementById("urlinput").style.display = "";
        document.getElementById("sourceinput").style.display = "none";
    }
    else if (type == 'S')
    {
        document.getElementById("urlinput").style.display = "none";
        document.getElementById("sourceinput").style.display = "";
    }
}

function changeSelectList(context)
{
    var access = context.options[context.selectedIndex].text;
    if (access == "index")
    {
        context.parentNode.parentNode.cells[4].childNodes[1].disabled = false;
        context.parentNode.parentNode.cells[4].childNodes[1].setAttribute("style", "width:103px");
        context.parentNode.parentNode.cells[4].childNodes[3].disabled = true;
        context.parentNode.parentNode.cells[4].childNodes[3].setAttribute("style", "display:none;width:103px");
        context.parentNode.parentNode.cells[4].childNodes[5].disabled = true;
        context.parentNode.parentNode.cells[4].childNodes[5].setAttribute("style", "display:none;width:103px");
        context.parentNode.parentNode.cells[4].childNodes[7].disabled = true;
        context.parentNode.parentNode.cells[4].childNodes[7].setAttribute("style", "display:none;width:100px");
        context.parentNode.parentNode.cells[5].childNodes[1].value = "T";
    }
    else if (access == "value")
    {
        context.parentNode.parentNode.cells[4].childNodes[3].disabled = false;
        context.parentNode.parentNode.cells[4].childNodes[3].setAttribute("style", "width:103px");
        context.parentNode.parentNode.cells[4].childNodes[1].disabled = true;
        context.parentNode.parentNode.cells[4].childNodes[1].setAttribute("style", "display:none;width:103px");
        context.parentNode.parentNode.cells[4].childNodes[5].disabled = true;
        context.parentNode.parentNode.cells[4].childNodes[5].setAttribute("style", "display:none;width:103px");
        context.parentNode.parentNode.cells[4].childNodes[7].disabled = true;
        context.parentNode.parentNode.cells[4].childNodes[7].setAttribute("style", "display:none;width:100px");
        context.parentNode.parentNode.cells[5].childNodes[1].value = "T";
    }
    else if (access == "label")
    {
        context.parentNode.parentNode.cells[4].childNodes[5].disabled = false;
        context.parentNode.parentNode.cells[4].childNodes[5].setAttribute("style", "width:103px");
        context.parentNode.parentNode.cells[4].childNodes[3].disabled = true;
        context.parentNode.parentNode.cells[4].childNodes[3].setAttribute("style", "display:none;width:103px");
        context.parentNode.parentNode.cells[4].childNodes[1].disabled = true;
        context.parentNode.parentNode.cells[4].childNodes[1].setAttribute("style", "display:none;width:103px");
        context.parentNode.parentNode.cells[4].childNodes[7].disabled = true;
        context.parentNode.parentNode.cells[4].childNodes[7].setAttribute("style", "display:none;width:100px");
        context.parentNode.parentNode.cells[5].childNodes[1].value = "T";
    }
}

function changeOperation(op)
{
    if (op == 'M')
    {
        document.getElementById('generatescriptdiv').setAttribute("style", "display:none");
        document.getElementById('matchuicontrolsdiv').setAttribute("style", "display:block");
        document.getElementById('headertable').setAttribute("style", "display:none");
        document.getElementById('matchheadertable').setAttribute("style", "display:none");
        document.getElementById('controlmatchdiv').innerHTML = '';
        document.getElementById('controllistdiv').innerHTML = '';
        controlEventMap = new Array();
    }
    else if (op == 'G')
    {
        document.getElementById('matchuicontrolsdiv').setAttribute("style", "display:none");
        document.getElementById('generatescriptdiv').setAttribute("style", "display:block");
        document.getElementById('headertable').setAttribute("style", "display:none");
        document.getElementById('matchheadertable').setAttribute("style", "display:none");
        document.getElementById('controlmatchdiv').innerHTML = '';
        document.getElementById('controllistdiv').innerHTML = '';
        controlEventMap = new Array();
    }
}

function getControlList()
{
    if (!validateTGetControl())
    {
        return;
    }

    var purl = "", option = "";
    if (document.getElementById("url").checked)
    {
        purl = document.getElementById("pageURL").value;
        option = "opturl";
    }
    else if (document.getElementById("source").checked)
    {
        if (document.getElementById("pageSource").value != "")
        {
            purl = document.getElementById("pageSource").value;
            option = "optsource";
        }
        else if (document.getElementsByName("pageSourceFile") && document.getElementsByName("pageSourceFile")[0])
        {
            purl = document.getElementsByName("pageSourceFile")[0].value;
            option = "optsourcefile";
        }
        else
        {
            return;
        }
    }
    if (purl != "")
    {
        if (document.getElementById("url").checked)
        {
            if (purl.length >= 255)
            {
                alert("Invalid URL.");
                return;
            }
            if (!isUrl(purl))
            {
                alert("Invalid URL.");
                return;
            }
        }
        var url = 'scriptgen.fin?cmdAction=getControlList&option=' + option;
        var params = getalldata(document.scriptgenAddform);
        document.getElementById("headertable").setAttribute("style", "display:table");
        getData_sync(url, 'controllistdiv', params);
        document.getElementById("pageURL").value = '';
        document.getElementById("pageSource").value = '';
        //resetFileUploadControl("pageSourceF");
        reset_filebox("pageSourceF");
        var count = document.getElementsByName("htmlControlID").length;

        for (var i = 0 ; i < count ; i++)
        {
            controlEventMap[i] = new Array();
            controlEventMap[i][0] = (i+1);
            controlEventMap[i][1] = document.getElementById((i+1)).cells[1].childNodes[1].rows.length;
        }

        document.getElementById("url").checked = true;
        changeInput('U');
        document.getElementById("controlsCount").value = count;
        document.getElementById("eventsCount").value = count;
        document.getElementById('match').checked = false;
        document.getElementById('generate').checked = false;
    }
}

function matchUIControls()
{
    var oldSource = document.getElementById("oldSource").value;
    var newSource = document.getElementById("newSource").value;
    var opcode;
    if (Trim(oldSource.toString()) != '' && Trim(newSource.toString()) != '')
    {
        if (Trim(oldSource.toString()) == Trim(newSource.toString()))
        {
            alert('Old Source & New Source is Identical.');
        }
        else
        {
            if (oldSource != "" && newSource != "")
            {
                if (document.getElementById('control').checked == true)
                {
                    opcode = 'c';
                }
                else
                {
                    opcode = 'p';
                }
                var url = 'scriptgen.fin?cmdAction=matchUIControl&opcode='+opcode+'&';
                var params = getalldata(document.scriptgenAddform);
                getData_sync(url, 'controlmatchdiv', params);
                document.getElementById("oldSource").value = '';
                document.getElementById("newSource").value = '';
                document.getElementById('matchheadertable').setAttribute("style", "display:table");
                document.getElementById('generatescriptdiv').setAttribute("style", "display:none");
                document.getElementById('matchuicontrolsdiv').setAttribute("style", "display:none");
                document.getElementById('control').checked = true;
                document.getElementById('match').checked = false;
                document.getElementById('generate').checked = false;
            }
        }
    }
}

function removeControlGroup(counter)
{
    if (confirmControlDelete())
    {
        var element = document.getElementById(counter);
        element.parentNode.removeChild(element);
        var row = --counter;
        controlEventMap[row][0] = 0;
        controlEventMap[row][1] = 0;
    }
}

function removeControlValueGroup(context,counter)
{
    if (confirmEventDelete())
    {
        var element = document.getElementById(context);
        element.parentNode.removeChild(element);
        controlEventMap[counter][1]--;
    }
}

function convert(context,val)
{
    var child1;
    if (val == 1)
    {
        child1 = 1;
    }
    else
    {
        child1 = 0;
    }
    var cntrl;
    if (val == 1)
    {
        cntrl = context.parentNode.parentNode.cells[2].childNodes[1];
    }
    else
    {
        cntrl = context.parentNode.parentNode.cells[2].childNodes[0];
    }

    if (cntrl.value == 9 || cntrl.value == 3)
    {
        if (context.value == 9 || context.value == 3)
        {
            var confirmTCN = context.parentNode.parentNode.cells[3];
            confirmTCN.innerHTML = "";
            //for textbox
            var text_box = document.createElement("INPUT");
            text_box.type = "text";
            text_box.style.width = "128px";
            text_box.style.display = "none";
            text_box.id = "confirmMsg";
            text_box.name = "confirmMsg";
            confirmTCN.appendChild(text_box);
            //for combobox
            var combo_box = document.createElement("SELECT");
            combo_box.id = "message"
            combo_box.name = "message";
            //combo_box.style.display = "inline";
            combo_box.style.width = "135px";
            if (context.value == 9)
            {
                var options1 = document.createElement("OPTION");
                options1.value = "-1";
                options1.text = "--Select--";
                combo_box.options.add(options1);

                var options2 = document.createElement("OPTION");
                options2.value = "ok";
                options2.text = "Ok";
                combo_box.options.add(options2);

                var options3 = document.createElement("OPTION");
                options3.value = "cancel";
                options3.text = "Cancel";
                combo_box.options.add(options3);
            }
            else
            {
                combo_box.innerHTML = document.getElementById("ctmpdata").innerHTML;
            }

            context.parentNode.parentNode.cells[4].childNodes[0].value = "T";
            confirmTCN.appendChild(combo_box);
        }
        else if (context.value == "T")
        {
            confirmTCN = context.parentNode.parentNode.cells[3];
            confirmTCN.childNodes[0].style.display = "";
            confirmTCN.childNodes[1].style.display = "none";
            if (val == 1)
            {
                context.parentNode.parentNode.cells[4].childNodes[1].value = "S";
            }
            else
            {
                context.parentNode.parentNode.cells[4].childNodes[0].value = "S";
            }
        }
        else
        {
            confirmTCN = context.parentNode.parentNode.cells[3];
            confirmTCN.childNodes[0].style.display = "none";
            confirmTCN.childNodes[1].style.display = "";
            if (val == 1)
            {
                context.parentNode.parentNode.cells[4].childNodes[1].value = "T";
            }
            else
            {
                context.parentNode.parentNode.cells[4].childNodes[0].value = "T";
            }
        }
        context.parentNode.parentNode.cells[4].childNodes[child1].style.display = "";
        return;
    }

    if (context.value == "T" || (context.value == 2 || context.value == 4 || context.value == 6 || context.value == 7 || context.value == 10 || context.value== 8))
    {
        context.parentNode.parentNode.cells[3].innerHTML='<input type="text" id="message" name="message" style="width:128px">';
        context.parentNode.parentNode.cells[4].childNodes[child1].value = "S";
    }
    else
    {
        context.parentNode.parentNode.cells[3].innerHTML = document.getElementById("ctmpdata").innerHTML;
        context.parentNode.parentNode.cells[3].childNodes[1].setAttribute("style", "display:inline;width:135px");
        context.parentNode.parentNode.cells[4].childNodes[child1].value = "T";
    }
    //for hidding convert button
    if (context.value == 1 || context.value == 2 || context.value == 4 || context.value == 6 
        || context.value == 7 || context.value == 10 || context.value == 8)
        {
        //context.parentNode.parentNode.cells[4].childNodes[child1].disabled = true;
        context.parentNode.parentNode.cells[4].childNodes[child1].style.display = "none";
        if (context.value == 1)
        {
            context.parentNode.parentNode.cells[3].childNodes[1].style.width = "150px";
        }
        else
        {
            context.parentNode.parentNode.cells[3].childNodes[0].style.width = "150px";
        }
    }
    else if (context.value == 5 || context.value == -1)
    {
        context.parentNode.parentNode.cells[4].childNodes[child1].style.display = "";
    }
}

function change(context)
{
    if (context.value == "T")
    {
        context.parentNode.parentNode.cells[4].childNodes[1].disabled = true;
        context.parentNode.parentNode.cells[4].childNodes[1].setAttribute("style", "display:none;width:103px");
        context.parentNode.parentNode.cells[4].childNodes[3].disabled = true;
        context.parentNode.parentNode.cells[4].childNodes[3].setAttribute("style", "display:none;width:103px");
        context.parentNode.parentNode.cells[4].childNodes[5].disabled = true;
        context.parentNode.parentNode.cells[4].childNodes[5].setAttribute("style", "display:none;width:103px");
        context.parentNode.parentNode.cells[4].childNodes[7].disabled = false;
        context.parentNode.parentNode.cells[4].childNodes[7].setAttribute("style", "width:100px");
        context.value = "S";
    }
    else
    {
        context.parentNode.parentNode.cells[3].childNodes[1].selectedIndex = 0;
        context.parentNode.parentNode.cells[4].childNodes[1].disabled = false;
        context.parentNode.parentNode.cells[4].childNodes[1].setAttribute("style", "width:103px");
        context.parentNode.parentNode.cells[4].childNodes[3].disabled = true;
        context.parentNode.parentNode.cells[4].childNodes[3].setAttribute("style", "display:none;width:103px");
        context.parentNode.parentNode.cells[4].childNodes[5].disabled = true;
        context.parentNode.parentNode.cells[4].childNodes[5].setAttribute("style", "display:none;width:103px");
        context.parentNode.parentNode.cells[4].childNodes[7].disabled = true;
        context.parentNode.parentNode.cells[4].childNodes[7].setAttribute("style", "display:none;width:100px");
        context.value = "T";
    }
}

function confirmControlDelete()
{
    return confirm("Are you sure to remove this control group?");
}

function confirmEventDelete()
{
    return confirm("Are you sure to remove this event group?");
}

function addControlValueGroup(context,controlcount)
{
    var local = controlcount;
    --local;

    controlEventMap[local][1]++;

    var baseTableNode;

    //Reach to controlEventValueDetailTable Table ELEMENT to add additional row of control value event
    baseTableNode = context.parentNode.parentNode.parentNode.parentNode;

    var rowCount = baseTableNode.rows.length;
    var newRowID = controlcount + "" + ++(document.getElementById("eventsCount").value) +"000000";
    var newRow = baseTableNode.insertRow(rowCount);
    newRow.setAttribute("id", newRowID);
    var cell1 = newRow.insertCell(0);
    cell1.setAttribute("class", "tdleftdata");
    var imgElement = document.createElement("img");
    imgElement.setAttribute("alt", "");
    imgElement.setAttribute("src","./images/delete.gif");
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
    var eventListCombo = document.getElementById("eventID");
    selectElement1.setAttribute("style", "width:132px");
    selectElement1.innerHTML = eventListCombo.innerHTML;
    cell2.appendChild(selectElement1);

    var cell3 = newRow.insertCell(2);
    cell3.setAttribute("class", "tdleftdata");
    var selectElement2 = document.createElement("SELECT");
    selectElement2.name = "testCaseNatureID";
    selectElement2.id = "testCaseNatureID";
    var testCaseNatureListCombo = document.getElementById("testCaseNatureID");
    selectElement2.innerHTML = testCaseNatureListCombo.innerHTML;
    selectElement2.setAttribute("style","width:auto");
    selectElement2.setAttribute("onchange", "javascript:convert(this,2)")
    cell3.appendChild(selectElement2);

    var cell4 = newRow.insertCell(3);
    cell4.setAttribute("class", "tdleftdata");
    var controllist = document.createElement("SELECT");
    controllist.id = "message";
    controllist.name = "message";
    controllist.setAttribute("style", "width:135px");
    controllist.innerHTML = document.getElementById("message").innerHTML;
    cell4.appendChild(controllist);

    var cell5 = newRow.insertCell(4);
    cell5.setAttribute("class", "tdleftdata");
    var inputbtn = document.createElement("input");
    inputbtn.type = "button";
    inputbtn.id = "convertBtn";
    inputbtn.name = "convertBtn";
    inputbtn.value = "T";
    inputbtn.setAttribute("style", "width:20px");
    inputbtn.setAttribute("onclick", "convert(this,2)");
    cell5.appendChild(inputbtn);
}

function showTab(tab)
{
    var params = getalldata(document.scriptgenform);
    params += "&showTab="+tab;
    getData_sync("scriptgen.fin?cmdAction=getAddViewPage", 'divContent', params);
    if (tab == "View")
    {
        loadDatePicker("fromDate");
        loadDatePicker("toDate");
    }
}

function getGenClassList()
{
    if (document.getElementById("prjID").value == "-1" && document.getElementById("fromDate").value == "" && document.getElementById("toDate").value == "")
    {
        alert("Please Select ProjectName or CreationDate");
        document.getElementById("prjID").focus();
        return;
    }
    else if (document.getElementById("prjID").value == "-1" && document.getElementById("fromDate").value == "")
    {
        alert("Please Select FromDate");
        document.getElementById("fromDate").focus();
    }
    else if (document.getElementById("prjID").value == "-1" && document.getElementById("toDate").value == "")
    {
        alert("Please Select ToDate");
        document.getElementById("toDate").focus();
    }
    else
    {
        var params = getalldata(document.scriptgenAddform);
        getData_sync("scriptgen.fin?cmdAction=getGenClassList", 'tblGenClassList', params);
    }
}

function getFiles(divID)
{
    if (!document.getElementById(divID).innerHTML.match(".java"))
    {
        var params = "&srno=" + divID;
        getData_sync("scriptgen.fin?cmdAction=generateFiles", divID, params);
    //document.getElementById(divID).innerHTML = "<br>"+ document.getElementById("divTempData").innerHTML;
    }
}

function selectAll()
{
    var flag = false;
    if (document.getElementById("chkDeleteAll").checked)
    {
        flag = true;
    }
    var chkDelete = document.getElementsByName("chkDelete");
    for (var i = 0; i < chkDelete.length; i++)
    {
        chkDelete[i].checked = flag;
    }
}

function removeControl()
{
    var deleteControl = document.getElementsByName("chkDelete");
    var flag = true;
    for (var i = deleteControl.length-1; i >= 0; i--)
    {
        if (deleteControl.item(i).checked)
        {
            if (flag)
            {
                var ans = confirmControlDelete();
            }
            if (ans == true)
            {
                var counter = deleteControl.item(i).value;
                var element = document.getElementById(deleteControl.item(i).value);
                element.parentNode.removeChild(element);
                var row = --counter;
                controlEventMap[row][0] = 0;
                controlEventMap[row][1] = 0;
            }
            flag = false;
        }
    }
    document.getElementById("chkDeleteAll").checked = false;
}

function dateValidationtest()
{
    var fdt = document.getElementById("fromDate").value;
    var tdt = document.getElementById("toDate").value;
    if (is_valid_textdate(fdt,'From Date'))
    {
        if (is_valid_textdate(tdt,'To Date'))
        {
            if (check_textdate_less_or_equal_current_date(fdt,'From Date'))
            {
                if (check_textdate_less_or_equal_current_date(tdt,'To Date'))
                {
                    if (!compare_two_textdate(fdt,tdt,'From Date should not be greater than To Date.'))
                    {
                        document.getElementById("fromDate").focus();
                        return false;
                    }
                }
                else
                {
                    document.getElementById("toDate").focus();
                    return false;
                }
            }
            else
            {
                document.getElementById("fromDate").focus();
                return false;
            }
        }
        else
        {
            document.getElementById("toDate").focus();
            return false;
        }
    }
    else
    {
        document.getElementById("fromDate").focus();
        return false;
    }
    return true;
}

function check_textdate_less_or_equal_current_date(dt,caption)
{
    var cur_date=new Date();
    var cur_year=cur_date.getYear();

    if (cur_year<999)
    {
        cur_year+=1900;
    }

    var cur_month=cur_date.getMonth();
    var cur_day=cur_date.getDate();
    var cdate=new Date(cur_year,cur_month,cur_day);
    var dt_day=dt.split('-')[0];
    var dt_mon=dt.split('-')[1];
    var dt_year=dt.split('-')[2];
    //alert(dt_year+"-"+dt_mon+"-"+dt_day);
    var todt = new Date(dt_year,dt_mon-1,dt_day);
    if (todt <= cdate)
    {
        return true;
    }
    else
    {
        alert("'"+caption + "'" +" "+"should not be greater than Current Date.");
        return false;
    }
}

function compare_two_textdate(from_dt,to_dt,caption)
{
    var from_dt_day=from_dt.split('-')[0];
    var from_dt_mon=from_dt.split('-')[1];
    var from_dt_year=from_dt.split('-')[2];
    var from_date=new Date(from_dt_year,from_dt_mon,from_dt_day);
    var to_dt_day=to_dt.split('-')[0];
    var to_dt_mon=to_dt.split('-')[1];
    var to_dt_year=to_dt.split('-')[2];
    var to_date=new Date(to_dt_year,to_dt_mon,to_dt_day);
    // return(from_date > to_date);
    if (from_date > to_date)
    {
        alert(caption);
        return false;
    }
    return true;
}

function is_valid_textdate(dt,caption)
{
    var day=dt.split('-')[0];
    var mn=dt.split('-')[1];
    var yr=dt.split('-')[2];

    if (yr == '' || day == '' || mn == '')
    {
        alert("Please enter valid"+" " +caption);
        return false;
    }
    if (yr == 0000 || day == 00 | mn == 00 || yr < 1990)
    {
        alert("Please enter valid" +" "+caption);
        return false;
    }
    if (day > 31 || day < 0 || mn > 12 || mn < 0 || yr < 0)
    {
        alert("Please enter valid" +" "+caption);
        return false;
    }
    if ((mn == '02') && (day > 28))
    {
        if ((((yr % 4 == 0) && (yr % 100 != 0)) || (yr % 400 == 0)) && (day < 30))
        {
            return true;
        }
        else
        {
            alert("Please enter valid"+" " +caption);
            return false;
        }
    }
    else if ((mn == '04' || mn == '06' || mn == '09' || mn == '11') && (day > 30))
    {
        alert("Please enter valid " + caption);
        return false;
    }
    else
    {
        return true;
    }
}

function is_Login_Select()
{
    if (document.getElementById("chkloginOnce").checked)
    {
        document.getElementById("chkloginOnce").value = '1';
    }
}
