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

function onload()
{
    if (document.getElementById("cmbProjectName"))
    {
        loadComboNew("cmbProjectName","","","MenuForm");
    }
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

function validateFile(src)
{
    if (document.getElementById(src).value == '')
    {
        alert("Please Select File");
        document.getElementById(src).focus();
        return false;
    }
    else
    {
        var file = document.getElementById(src).value;
        if (file.substr(file.length - 5, file.length) != ".java")
        {
            alert("Please Enter .java File.");
            document.getElementById(src).focus();
            return false;
        }
        else if (file.substr(file.length - 12, file.length) != "Service.java")
        {
            alert("File Name Must Ends With Service.java");
            document.getElementById(src).focus();
            return false;
        }
        else
        {
            var size = getFileSize(document.getElementById(src));
            if (typeof size != "undefined")
            {
                if (size > 5120)
                {
                    alert("Interface File's Size is Very Large.");
                    document.getElementById("interfaceFile").focus();
                    return false;
                }
                else
                {
                    return true;
                }
            }
            else
            {
                return false;
            }
        }
    }
}

function getFileSize(input)
{
    if (typeof window.FileReader !== 'function')
    {
        alert("The file API isn't supported on this browser yet.");
        return;
    }
    var file = input.files[0];
    return file.size;
}

function showFinish(f)
{
    document.getElementById("txtModuleName").value = document.getElementById("txtModuleName").value.replace(/^\s+|\s+$/g,'');
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
    if (checkReqNo() && validateFile("interfaceFile"))
    {
        onFinish(f);
    }
}

function onFinish(f)
{
    document.getElementById("btnFinish").disabled = true;
    f.action = "webservicegen.fin?cmdAction=onFinish";
    f.submit();
}

function lastFinish(f)
{
    var len = document.getElementsByName("beanFile").length;
    var beanList = "";
    for (var i = 0; i < len; i++)
    {
        beanList += document.getElementById("bean" + i).value;
        beanList += ",";
        if (document.getElementsByName("beanFile").item(i).value == '')
        {
            alert("Please Select File");
            document.getElementsByName("beanFile").item(i).focus();
            return;
        }
        else
        {
            var file = document.getElementsByName("beanFile").item(i).value;
            if (file.substr(file.length - 5, file.length) != ".java")
            {
                alert("Please Enter .java File.");
                document.getElementsByName("beanFile").item(i).focus();
                return;
            }
            else
            {
                var size = getFileSize(document.getElementsByName("beanFile").item(i));
                if (typeof size != "undefined")
                {
                    if (size > 10240)
                    {
                        alert("File's Size is Very Large.");
                        ddocument.getElementsByName("beanFile").item(i).focus();
                        return;
                    }
                }
            }
        }
    }
    document.getElementById("beans").value = beanList;
    document.getElementById("btnFinish").disabled = true;
    f.action = "webservice.do";
    f.submit();
}