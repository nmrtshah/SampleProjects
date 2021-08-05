<%--
<%--
    Document   : newjsp
    Created on : 10 MAY, 2014, 9:57:37 AM
    Author     : Jeegarkumar Patel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="${finlib_path}/resource/main_offline.css" />
        <script type="text/javascript" src="${finlib_path}/resource/validate.js"></script>
        <script type="text/javascript" src="${finlib_path}/resource/serverCombo.js"></script>
        <script type="text/javascript" src="${finlib_path}/resource/ajax.js"></script>
        <script type="text/javascript" src="${finlib_path}/resource/common_functions.min.js"></script>
        <script type="text/javascript" src="javascript/dbmc.js" ></script>
        <link rel="stylesheet" type="text/css" href="css/diffview.css"/>
        <script type="text/javascript" src="javascript/difflib.js"></script>
        <script type="text/javascript" src="javascript/diffview.js"></script>
        <style type="text/css">
            .top {text-align: center;}
            #diffoutput {width: 100%;}
            .textInput {display: block;width: 49%;float: left;}
            .spacer {margin-left: 10px;}
        </style>
        <title>Database Metadata Comparator</title>
        <script type="text/javascript">
            function diffUsingJS(viewType) {

                document.getElementById('datadiv').style.display = "block";
                "use strict";
                var byId = function (id) {
                    return document.getElementById(id);
                },
                base = difflib.stringAsLines(byId("baseText").value),
                newtxt = difflib.stringAsLines(byId("newText").value),
                sm = new difflib.SequenceMatcher(base, newtxt),
                opcodes = sm.get_opcodes(),
                diffoutputdiv = byId("diffoutput"),
                contextSize = byId("contextSize").value;

                diffoutputdiv.innerHTML = "";
                contextSize = contextSize || null;
                var valData = '';
                var name = document.getElementsByName("server").length;
                for (var i = 0; i < name; i++)
                {
                    if (document.getElementsByName("server")[i].checked)
                    {
                        valData = document.getElementsByName("server")[i].value;
                    }

                }

                var basetxt, newdtxt;

                if (valData == 'DT')
                {
                    basetxt = 'Dev-Text';
                    newdtxt = 'Test-Text';
                }
                else if (valData == 'TP')
                {
                    basetxt = 'Test-Text';
                    newdtxt = 'Prod-Text';
                }

                diffoutputdiv.appendChild(diffview.buildView({
                    baseTextLines: base,
                    newTextLines: newtxt,
                    opcodes: opcodes,
                    baseTextName: basetxt,
                    newTextName: newdtxt,
                    contextSize: contextSize,
                    viewType: viewType
                }));
            }
        </script>
    </head>
    <body onload="javascript : onloadMyFunction();">
        <div class="container">
            <div class="content">
                <jsp:include page="dbmComparatorMenu.jsp"></jsp:include>
            </div>
        </div>
    </body>
</html>