
function padlength(what)
{
    var output = (what.toString().length == 1)? "0"+what : what;
    return output;
}

//function displaytime()
//{
//    serverdate.setSeconds(serverdate.getSeconds()+1);
//    var datestring = padlength(serverdate.getDate()) + "/" + (serverdate.getMonth()+1) + "/" + serverdate.getFullYear();
//    var timestring = padlength(serverdate.getHours()) + ":" + padlength(serverdate.getMinutes()) + ":" + padlength(serverdate.getSeconds());
//    document.getElementById("servertime").innerHTML = datestring + " " + timestring;
//}

//window.onload = function()
//{
//    setInterval("displaytime()", 1000);
//}

function isSessionExpired(responseText)
{
    if (responseText.indexOf("ACCESS DENIED",0) > 0)
    {
        return true;
    }
    else
    {
        return false;
    }
    
}

function displayGridOnLoad()
{
    document.getElementById("viewAll").style.display = "";
    
    var param = getFormData(document.finfiletransferForm);
    param += "&masterTask=view";
    $('#mstgrid').GridUnload();
    document.getElementById("errorBox").innerHTML = "";
    $("#mstgrid").jqGrid({
        url:'add_finfiletransfer.fin?cmdAction=getMasterGrid&flag=1&'+param,
        datatype: "json",
        colNames:["Id","Emp Name","Src Dest","Project","Req No","Purpose","Sysdate","ExecuteCancel_Status","Status","GenId","LANG"],
        //        colNames:["DETAIL_PK","EMPCODE","SRC_DEST","PROJECT","REQNO","PURPOSE","SYSDATE","FILE_NAME","SRC_SERVER_EXIST","DEST_SERVER_EXIST","Execute"],
        colModel:[
        {
            name:'MASTER_PK',
            index:'MASTER_PK',
            width:'20',
            sorttype:'int',
            jsonmap:'cell.0',
            align:'right'
        },

        {
            name:'EMP_NAME',
            index:'EMP_NAME',
            width:'70',
            jsonmap:'cell.1'
        },

        {
            name:'SRC_DEST',
            index:'SRC_DEST',
            width:'100',
            jsonmap:'cell.2'
        },

        {
            name:'PROJECT',
            index:'PROJECT',
            width:'100',
            jsonmap:'cell.3'
        },

        {
            name:'REQNO',
            index:'REQNO',
            width:'30',
            sorttype:'int',
            jsonmap:'cell.4'
        //align:'right'
        },

        {
            name:'PURPOSE',
            index:'PURPOSE',
            width:'50',
            jsonmap:'cell.5'
        },

        {
            name:'SYSDATE',
            index:'SYSDATE',
            width:'80',
            jsonmap:'cell.6'
        },
        
        {
            name:'ExecuteCancel_Status',
            index:'ExecuteCancel_Status',
            width:'180',
            align:'center'
        //            jsonmap:'cell.7'
        },
        //        {
        //            name:'Cancel',
        //            index:'Cancel',
        //            width:'80',
        //            align:'center'
        //        //            jsonmap:'cell.7'
        //        },
        {
            name:'EXECUTE_STATUS',
            index:'EXECUTE_STATUS',
            width:'20',
            align:'center',
            jsonmap:'cell.7',
            hidden:'true'
        },
        {
            name:'GEN_ID',
            index:'GEN_ID',
            width:'20',
            align:'center',
            jsonmap:'cell.8',
            hidden:'true'
        },
        {
            name:'LANG',
            index:'LANG',
            width:'20',
            align:'center',
            jsonmap:'cell.9',
            hidden:'true'
        }
        ],
        jsonReader: {
            repeatitems : false
        },
        rowNum:10,
        rowList:[10,20,30],
        rowTotal: 10000,
        rownumbers: true,
        gridview: true,
        height: 'auto',
        width: '1000',
        altRows: true,
        pager: '#pagermstgrid',
        sortname: 'MASTER_PK',
        viewrecords: true,        
        sortorder: "asc",
        loadonce: true,
        userDataOnFooter: true,
        footerrow: false,
        grouping: false,
        loadError : function(xhr,st,err)
        {
            $('#errorBox').show();
            if (isSessionExpired(xhr.responseText))
            {
                $('#errorBox').html(xhr.responseText);
            }
            else
            {
                $('#errorBox').html("<center><b>Under Maintenance. Please Try Later.</b></center>");
            }
            $('#gbox_mstgrid').hide();
        },
        gridComplete:function() {
            var ids = jQuery("#mstgrid").jqGrid("getDataIDs");
            var masterPKSet="";
            for(var i=0; i<ids.length; i++)
            {
                var cl = ids[i];
                var vcontrol0="";
                var cancelBtn="";
                //                var vcontrol1;
                var column0 = $('#mstgrid').getCell(cl, 'MASTER_PK');
                masterPKSet=masterPKSet+column0+",";
                var column1 = $('#mstgrid').getCell(cl, 'EXECUTE_STATUS');
                //var column2 = $('#mstgrid').getCell(cl, 'GEN_ID');
                var column3 = $('#mstgrid').getCell(cl, 'SRC_DEST');
                var lang = $('#mstgrid').getCell(cl, 'LANG');
                //var column4 = $('#mstgrid').getCell(cl, 'PROJECT');
                var srcDestAry=column3.split("-");
                var src=srcDestAry[0].substring(0,srcDestAry[0].indexOf(":"));
                var dest=srcDestAry[1].substring(0,srcDestAry[1].indexOf(":"));
                var srcDest= src + " - " + dest;
                //                alert(column1);
                if(column1=='Y')
                {
                    vcontrol0="Done";
                }
                else if(column1=='N')
                {
                    vcontrol0="<div id='div"+column0+"' style='width:285px;'><input class=\"button\" type=\"button\" id=\"btn\" name=\"btn\" Value=\"Execute\" onclick=\"javascript: transfer('"+column0+"','"+lang+"'); \" /> "+
                    "<input class=\"button\" type=\"button\" id=\"btnCncl\" name=\"btnCncl\" Value=\"Cancel\" onclick=\"javascript: cancel('"+column0+"'); \" /></div>";
                //                    alert(vcontrol0);
                }
                else if(column1=='C')
                {
                    vcontrol0="Cancelled";
                }
                document.getElementById("viewAll").innerHTML="<a href='#' onclick=\"javascript : viewAll('"+masterPKSet+"');\">View All</a>";
                var vcontrol1="<center><div style='color:#0000FF;' onclick=\"javascript: getDetail("+column0+"); \"><u>"+column0+"</u></div></center>";
                                    
                jQuery("#mstgrid").jqGrid('setRowData',ids[i],{
                    MASTER_PK:vcontrol1,
                    ExecuteCancel_Status:vcontrol0,
                    //                    Cancel:cancelBtn,
                    SRC_DEST:srcDest
                });
            }
        }
    });
}

function displayViewMasterGrid()
{
    document.getElementById("viewAll").style.display = "";
    if (!validateViewFilter())
    {
        return;
    }
    //    EMPCODE, SRC_DEST, PROJECT, REQNO, PURPOSE, SYSDATE,FILE_NAME,SRC_SERVER_EXIST,DEST_SERVER_EXIST
    var param = getFormData(document.finfiletransferForm);
    param += "&masterTask=view";
    $('#mstgrid').GridUnload();
    document.getElementById("errorBox").innerHTML = "";
    $("#mstgrid").jqGrid({
        url:'add_finfiletransfer.fin?cmdAction=getMasterGrid&flag=2&'+param,
        datatype: "json",
        colNames:["Id","Emp Name","Src Dest","Project","Req No","Purpose","Sysdate","ExecuteCancel_Status","Status","GenId","LANG"],
        //        colNames:["DETAIL_PK","EMPCODE","SRC_DEST","PROJECT","REQNO","PURPOSE","SYSDATE","FILE_NAME","SRC_SERVER_EXIST","DEST_SERVER_EXIST","Execute"],
        colModel:[
        {
            name:'MASTER_PK',
            index:'MASTER_PK',
            width:'20',
            sorttype:'int',
            jsonmap:'cell.0',
            align:'right'
        },

        {
            name:'EMP_NAME',
            index:'EMP_NAME',
            width:'70',
            jsonmap:'cell.1'
        },

        {
            name:'SRC_DEST',
            index:'SRC_DEST',
            width:'100',
            jsonmap:'cell.2'
        },

        {
            name:'PROJECT',
            index:'PROJECT',
            width:'100',
            jsonmap:'cell.3'
        },

        {
            name:'REQNO',
            index:'REQNO',
            width:'30',
            sorttype:'int',
            jsonmap:'cell.4'
        //align:'right'
        },

        {
            name:'PURPOSE',
            index:'PURPOSE',
            width:'50',
            jsonmap:'cell.5'
        },

        {
            name:'SYSDATE',
            index:'SYSDATE',
            width:'80',
            jsonmap:'cell.6'
        },
        
        {
            name:'ExecuteCancel_Status',
            index:'ExecuteCancel_Status',
            width:'180',
            align:'center'
        //            jsonmap:'cell.7'
        },
        //        {
        //            name:'Cancel',
        //            index:'Cancel',
        //            width:'80',
        //            align:'center'
        //        //            jsonmap:'cell.7'
        //        },
        {
            name:'EXECUTE_STATUS',
            index:'EXECUTE_STATUS',
            width:'20',
            align:'center',
            jsonmap:'cell.7',
            hidden:'true'
        },
        {
            name:'GEN_ID',
            index:'GEN_ID',
            width:'20',
            align:'center',
            jsonmap:'cell.8',
            hidden:'true'
        },
        {
            name:'LANG',
            index:'LANG',
            width:'20',
            align:'center',
            jsonmap:'cell.9',
            hidden:'true'
        }
        ],
        jsonReader: {
            repeatitems : false
        },
        rowNum:10,
        rowList:[10,20,30],
        rowTotal: 10000,
        rownumbers: true,
        gridview: true,
        height: 'auto',
        width: '1000',
        altRows: true,
        pager: '#pagermstgrid',
        sortname: 'MASTER_PK',
        viewrecords: true,        
        sortorder: "asc",
        loadonce: true,
        userDataOnFooter: true,
        footerrow: false,
        grouping: false,
        loadError : function(xhr,st,err)
        {
            $('#errorBox').show();
            if (isSessionExpired(xhr.responseText))
            {
                $('#errorBox').html(xhr.responseText);
            }
            else
            {
                $('#errorBox').html("<center><b>Under Maintenance. Please Try Later.</b></center>");
            }
            $('#gbox_mstgrid').hide();
        },
        gridComplete:function() {
            var ids = jQuery("#mstgrid").jqGrid("getDataIDs");
            var masterPKSet="";
            for(var i=0; i<ids.length; i++)
            {
                var cl = ids[i];
                var vcontrol0="";
                var cancelBtn="";
                //                var vcontrol1;
                var column0 = $('#mstgrid').getCell(cl, 'MASTER_PK');
                masterPKSet=masterPKSet+column0+",";
                var column1 = $('#mstgrid').getCell(cl, 'EXECUTE_STATUS');
                //var column2 = $('#mstgrid').getCell(cl, 'GEN_ID');
                var column3 = $('#mstgrid').getCell(cl, 'SRC_DEST');
                var lang = $('#mstgrid').getCell(cl, 'LANG');
                //var column4 = $('#mstgrid').getCell(cl, 'PROJECT');
                var srcDestAry=column3.split("-");
                var src=srcDestAry[0].substring(0,srcDestAry[0].indexOf(":"));
                var dest=srcDestAry[1].substring(0,srcDestAry[1].indexOf(":"));
                var srcDest= src + " - " + dest;
                //                alert(column1);
                if(column1=='Y')
                {
                    vcontrol0="Done";
                }
                else if(column1=='N')
                {
                    vcontrol0="<div id='div"+column0+"' style='width:285px;'><input class=\"button\" type=\"button\" id=\"btn\" name=\"btn\" Value=\"Execute\" onclick=\"javascript: transfer('"+column0+"','"+lang+"'); \" /> "+
                    "<input class=\"button\" type=\"button\" id=\"btnCncl\" name=\"btnCncl\" Value=\"Cancel\" onclick=\"javascript: cancel('"+column0+"'); \" /></div>";
                //                    alert(vcontrol0);
                }
                else if(column1=='C')
                {
                    vcontrol0="Cancelled";
                }
                document.getElementById("viewAll").innerHTML="<a href='#' onclick=\"javascript : viewAll('"+masterPKSet+"');\">View All</a>";
                var vcontrol1="<center><div style='color:#0000FF;' onclick=\"javascript: getDetail("+column0+"); \"><u>"+column0+"</u></div></center>";
                                    
                jQuery("#mstgrid").jqGrid('setRowData',ids[i],{
                    MASTER_PK:vcontrol1,
                    ExecuteCancel_Status:vcontrol0,
                    //                    Cancel:cancelBtn,
                    SRC_DEST:srcDest
                });
            }
        }
    });
}
function viewAll(masterPKSet)
{
    var len=$("#mstgrid").getRowData().length;
    if(len > 0)
    {
        getDetail(masterPKSet);
    }
    else
    {
        alert("No Records Found.");
    }
    
}

function getDetail(masterPK)
{
    document.getElementById("detail").style.display = "";
    var param = getFormData(document.finfiletransferForm);
    getSynchronousData('add_finfiletransfer.fin?cmdAction=getDetail&masterPK='+masterPK, param, 'detail');
}

function chkChange()
{
    if(document.getElementById("chk1").checked == true)
    {
        document.getElementById("all1").style.display = "";
        document.getElementById("default1").style.display = "none";
    }
    else if(document.getElementById("chk1").checked == false)
    {
        document.getElementById("all1").style.display = "none";
        document.getElementById("default1").style.display = "";
    }
    if(document.getElementById("chk2").checked == true)
    {
        document.getElementById("all2").style.display = "";
        document.getElementById("default2").style.display = "none";
    }
    else if(document.getElementById("chk2").checked == false)
    {
        document.getElementById("all2").style.display = "none";
        document.getElementById("default2").style.display = "";
    }
    if(document.getElementById("chk3").checked == true)
    {
        document.getElementById("all3").style.display = "";
        document.getElementById("default3").style.display = "none";
    }
    else if(document.getElementById("chk3").checked == false)
    {
        document.getElementById("all3").style.display = "none";
        document.getElementById("default3").style.display = "";
    }
}

function reqNoTxtLk()
{
    //    document.getElementById("rpt").style.display=""; 
    var param = getFormData(document.finfiletransferForm);
    var reqNo=document.getElementById("reqnoTxtLike").value;
    getSynchronousData('add_finfiletransfer.fin?cmdAction=getReq&reqNo='+reqNo, param,'reqNoDiv');
}

function cancel(masterPK)
{
    document.getElementById("detail").style.display = "";
    var param = getFormData(document.finfiletransferForm);
    getSynchronousData('view_finfiletransfer.fin?cmdAction=cancelReq&masterPK='+masterPK, param, 'detail');
    
    if(((document.getElementById("detail").innerHTML).indexOf("Success") !=-1) || ((document.getElementById("detail").innerHTML).indexOf("Request Already Executed.") !=-1))
    {
        document.getElementById("div" + masterPK).innerHTML= "Processing";
        document.getElementById("div" + masterPK).innerHTML= "Cancelled";
    }
}

function transfer(masterPK,lang)
{
    document.getElementById("detail").style.display = "";
    var param = getFormData(document.finfiletransferForm);
    getSynchronousData('view_finfiletransfer.fin?cmdAction=execute&masterPK='+masterPK+'&lang='+lang, param, 'detail');
    if(((document.getElementById("detail").innerHTML).indexOf("Success") !=-1) || ((document.getElementById("detail").innerHTML).indexOf("Request Already Executed.") !=-1))
    {
        document.getElementById("div" + masterPK).innerHTML= "Processing";
        document.getElementById("div" + masterPK).innerHTML= "Done";
    }
}

function AddLoader()
{
    document.getElementById("viewAll").innerHTML="";
    document.getElementById("detail").innerHTML="";
    document.getElementById("detail").style.display="none"; 
    $('#mstgrid').GridUnload();
    document.getElementById("errorBox").innerHTML = "";
    var param = getFormData(document.finfiletransferForm);
    getSynchronousData('add_finfiletransfer.fin?cmdAction=addLoader', param, 'load');
    loadComboNew("src_dest", "", "", "finfiletransferForm");
    loadComboNew("project", "", "", "finfiletransferForm");
}


function ViewLoader()
{
    document.getElementById("viewAll").innerHTML="";
    document.getElementById("detail").innerHTML="";
    document.getElementById("detail").style.display="none"; 
    $('#mstgrid').GridUnload();
    document.getElementById("errorBox").innerHTML = "";
    var param = getFormData(document.finfiletransferForm);
    getSynchronousData('add_finfiletransfer.fin?cmdAction=viewLoader', param, 'load');
    loadComboNew("empcode", "", "", "finfiletransferForm");
    loadComboNew("project", "", "", "finfiletransferForm");
    loadDatePicker("fromdate");
    loadDatePicker("todate");
    
}

function showTab(tab)
{
    document.getElementById("viewAll").innerHTML="";
    document.getElementById("detail").innerHTML="";
    $('#mstgrid').GridUnload();
    document.getElementById("errorBox").innerHTML = "";
    document.getElementById("divViewFilter").style.display = "none";
    document.getElementById("divViewExport").style.display = "none";
    document.getElementById(tab).style.display = "";
    document.getElementById("divButton").style.display = "";
    displayGridOnLoad();
}

function validateViewFilter()
{

    if ((document.getElementById("project").value == "-1") && (document.getElementById("empcode").value == "-1") && (document.getElementById("view_reqno").value == "-1"))
    {
        alert("Please Select Project or Employee or Request No.");
        return false;
    }
    else
    {
        var purpose=document.getElementById("view_purpose").value;
        if(compare_string_purpose(purpose)==false)
        {
            alert("Please enter valid value for Purpose [A-Za-z0-9._&-<space>].");
            return false;
        }
    }
    return true;
}

function radioValidate()
{
    var radios = document.getElementsByName('lang');
        
    for (var i = 0; i < radios.length; i++) 
    {
        if (radios[i].checked) 
        {
            return true; // checked
        }
    }

    // not checked, show error
    alert("Please make selection of language");
    return false;
}

function validateData()
{
    if (document.getElementById("btnAdd").value == "Add")
    {
        if(!radioValidate())
        {
            return false;
        }
        
        if (!validate_dropdown(document.finfiletransferForm,"src_dest","Source-Destination",true))
        {
            return false;
        }
        if (!validate_dropdown(document.finfiletransferForm,"project","Project",true))
        {
            return false;
        }
        //        if (!validate_dropdown(document.finfiletransferForm,"reqno","Request No",false))
        //        {
        //            return false;
        //        }
        if (document.getElementById("purpose").value == null || document.getElementById("purpose").value == "")
        {
            alert("Please Enter Value For 'Purpose'");
            return false;
        }
        else
        {
            var purpose=document.getElementById("purpose").value;
            if(purpose.length > 100)
            {
                alert("Purpose Value Should Not Exceed 100 Characters.");
                return false;
            }
            if(compare_string_purpose(purpose)==false)
            {
                alert("Please enter valid value for Purpose [A-Za-z0-9._&-<space>].");
                return false;
            }
        } 

        
        if((document.getElementById("filelist").value == null || document.getElementById("filelist").value == "") && 
            (document.getElementById("filelistdlt").value == null || document.getElementById("filelistdlt").value == "")
            && document.getElementById("upldChk").checked == false)
            {
            alert("Please Enter Value For 'Filelist' or 'File List For Delete' or 'Upload Local Files'. ");
            return false;
        }
        else
        {
            if(Trim(document.getElementById("filelist").value) != null && Trim(document.getElementById("filelist").value) != "") 
            {
                var fileList=(document.getElementById("filelist").value).split("\n");
                var projectNm=document.getElementById("project").value;
		projectNm=projectNm.substring(projectNm.indexOf("-") + 1 ,projectNm.length);
                for(var i = 0; i < fileList.length; i++)
                {
                    var str=fileList[i];
                    var n=str.indexOf(projectNm); 
                    if(n!=0)
                    {
                        alert("Files Path Should Start With the Selected Project Name");
                        return false;
                    }
                }
            }
            if(document.getElementById("filelistdlt").value != null && document.getElementById("filelistdlt").value != "") 
            {
                var fileList=(document.getElementById("filelistdlt").value).split("\n");
                var projectNm=(document.getElementById("project").value).split("-");
                for(var i = 0; i < fileList.length; i++)
                {
                    var str=fileList[i];
                    var n=str.indexOf(projectNm[1]); 
                    if(n!=0)
                    {
                        alert("Files Path For Deletion Should Start With the Selected Project Name");
                        return false;
                    }
                }
            }
        
            if(document.getElementById("upldChk").checked == true)
            {
                //                alert("len :  "+document.getElementsByName("filelistupld1").length);
                if(document.getElementsByName("filelistupld1").length == 0
                    && document.getElementsByName("filelistupld2").length == 0
                    && document.getElementsByName("filelistupld3").length == 0
                    && (document.getElementById("fileLoc1").value == null || document.getElementById("fileLoc1").value == "")
                    && (document.getElementById("fileLoc2").value == null || document.getElementById("fileLoc2").value == "")
                    && (document.getElementById("fileLoc3").value == null || document.getElementById("fileLoc3").value == ""))
                    {
                    alert("You Have Checked Checkbox But not Uploaded a File.");
                    return false;
                }
                if (document.getElementsByName("filelistupld1").length > 0)
                {
                    if (document.getElementById("fileLoc1").value == null || document.getElementById("fileLoc1").value == "")
                    {
                        alert("Please Enter Value For 'Location1' For Uploaded File");
                        return false;
                    }
                    else
                    {
                        var fileLoc=(document.getElementById("fileLoc1").value);
                        var projectNm=(document.getElementById("project").value).split("-");
                        
                        var n=fileLoc.indexOf(projectNm[1]); 
                        if(n!=0)
                        {
                            alert("File Path 1 For Upload Should Start With the Selected Project Name");
                            return false;
                        }
                    }
                }
                else
                {
                    if ( ! (document.getElementById("fileLoc1").value == null 
                        || document.getElementById("fileLoc1").value == "" ))
                        {
                        alert("Please Upload File For Entered Location1");
                        return false;
                    }
                }
      
                if (document.getElementsByName("filelistupld2").length > 0)
                {
                    if (document.getElementById("fileLoc2").value == null || document.getElementById("fileLoc2").value == "")
                    {
                        alert("Please Enter Value For 'Location2' For Uploaded File");
                        return false;
                    }
                    else
                    {
                        var fileLoc=(document.getElementById("fileLoc2").value);
                        var projectNm=(document.getElementById("project").value).split("-");
                        
                        var n=fileLoc.indexOf(projectNm[1]); 
                        if(n!=0)
                        {
                            alert("File Path 2 For Upload Should Start With the Selected Project Name");
                            return false;
                        }
                    }
                }
                else
                {
                    if ( ! (document.getElementById("fileLoc2").value == null 
                        || document.getElementById("fileLoc2").value == "" ))
                        {
                        alert("Please Upload File For Entered Location2");
                        return false;
                    }
                }
        
                if (document.getElementsByName("filelistupld3").length > 0)
                {
                    if (document.getElementById("fileLoc3").value == null || document.getElementById("fileLoc3").value == "")
                    {
                        alert("Please Enter Value For 'Location3' For Uploaded File");
                        return false;
                    }
                    else
                    {
                        var fileLoc=(document.getElementById("fileLoc3").value);
                        var projectNm=(document.getElementById("project").value).split("-");
                        
                        var n=fileLoc.indexOf(projectNm[1]); 
                        if(n!=0)
                        {
                            alert("File Path 3 For Upload Should Start With the Selected Project Name");
                            return false;
                        }
                    }
                }
                else
                {
                    if ( ! (document.getElementById("fileLoc3").value == null 
                        || document.getElementById("fileLoc3").value == "" ))
                        {
                        alert("Please Upload File For Entered Location3");
                        return false;
                    }
                }
            }
        }
        checkStatus();
    }
}

var rule_global_comp_name ="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789._&- ";
function compare_string_purpose(strdata)
{
    return compare_string(rule_global_comp_name,strdata);
}

function compare_string(strallowed,strdata)
{
    //alert("strallowed :" + strallowed + " Length :" +strallowed.length);
    //alert("strdata :" + strdata + " Length :" +strdata.length);

    var flag=false;
    for(var i=0;i<strdata.length;i++)
    {
        flag=false;

        for(var j=0;j<strallowed.length;j++)
        {
            if(strdata.charAt(i) == strallowed.charAt(j))
            {
                flag=true;
            }
        }
        if(flag==false)
        {
            return false;
        }
    }
    return true;
}

function checkStatus()
{
    if (document.getElementById("btnAdd").value == "Add")
    {
        var param = getFormData(document.finfiletransferForm);
        document.getElementById("rpt").style.display="none";
        document.getElementById("fileStatus").style.display="";
        getSynchronousData('add_finfiletransfer.fin?cmdAction=checkStatus', param, 'fileStatus');
    //        if (document.getElementById("divMsg"))
    //        {
    //            if (document.getElementById("hdnDbMsg").value == "success")
    //            {
    //                alert("Record has been Inserted Successfully");
    //            }
    //            else if (document.getElementById("hdnDbMsg").value == "failure")
    //            {
    //                alert("Problem In Record Insertion");
    //            }
    //            loadComboNew("src_dest", "", "", "finfiletransferForm");
    //        }
    //loadComboNew("src_dest", "", "", "finfiletransferForm");
    }
}
function backButton()
{
    document.getElementById("rpt").style.display="";
    document.getElementById("fileStatus").style.display="none"; 
}

function onRemove1()
{
    document.getElementById("fileLoc1").value="";
}
function onRemove2()
{
    document.getElementById("fileLoc2").value="";
}
function onRemove3()
{
    document.getElementById("fileLoc3").value="";
}
function insertData()
{
    if (document.getElementById("btnConfirm").value == "Confirm")
    {
        var param = getFormData(document.finfiletransferForm);
        getSynchronousData('add_finfiletransfer.fin?cmdAction=insertData', param, 'load');
        //        getDataValid('add_finfiletransfer.fin?cmdAction=insertData', param);
        if (document.getElementById("divMsg"))
        {
            if (document.getElementById("hdnDbMsg").value == "success")
            {
                alert("Record has been Inserted Successfully");
            }
            else if (document.getElementById("hdnDbMsg").value == "failure")
            {
                alert("Problem In Record Insertion");
            }
        //            //loadComboNew("src_dest", "", "", "finfiletransferForm");
        }
    }
// AddLoader();
//    else if (document.getElementById("btnAdd").value == "Update")
//    {
//        param = getFormData(document.finfiletransferForm);
//        getSynchronousData('edit_finfiletransfer.fin?cmdAction=editData', param, 'load');
//        if (document.getElementById("divMsg"))
//        {
//            if (document.getElementById("hdnDbMsg").value == "success")
//            {
//                alert("Record has been Updated Successfully");
//            }
//            else if (document.getElementById("hdnDbMsg").value == "failure")
//            {
//                alert("Problem In Record Updation");
//            }
//        }
//    }
}

function showReport(rptfor,f)
{
    document.getElementById("detail").style.display="none"; 
    sts=1;
    if (rptfor == 'View')
    {
        if (document.getElementById("rdoOnScreen").checked)          /*~~~~~~~ ON SCREEN ~~~~~~~~*/
        {
            document.getElementById("btnApply").type = "button";
            displayViewMasterGrid();
        }
        else                                                     /*~~~~~~~ PDF OR EXCEL ~~~~~~~~*/
        {
            if (checkRptName())
            {
                document.getElementById("btnApply").type = "submit";
                f.action = "add_finfiletransfer.fin?cmdAction=getReportFile";
                f.submit();
            }
        }
    }
    else if (rptfor == 'Print')
    {
        jQuery("#mstgrid").setGridParam({
            rowNum:10000
        }).trigger("reloadGrid");
        if (confirm("Please Confirm That You Want To Print Report"))
        {
            printReport("report_display","Report");
        }
    }
}

/*function hideReport(){
     document.getElementById('report_display').style.display='none';    

}*/

function checkRptName()
{
    if (document.getElementById("reportName").value == "")
    {
        alert("Please Enter Report Name");
        document.getElementById("reportName").focus();
        return false;
    }
    return true;
}

function fileBoxValidate()
{
    if(document.getElementById("fileDltChk").checked == true)
    {
        document.getElementById("fileDlt").style.display="";
    }
    else
    {
        document.getElementById("fileDlt").style.display="none";
    }
    if(document.getElementById("upldChk").checked == true)
    {
        document.getElementById("fileBox").style.display="";
    }
    else
    {
        document.getElementById("fileBox").style.display="none";
    }
}
function resetAll()
{
    resetFileUploadControl("btnUpload1");
    resetFileUploadControl("btnUpload2");
    resetFileUploadControl("btnUpload3");
    document.getElementById("fileDlt").style.display="none";
    document.getElementById("fileBox").style.display="none";
}
