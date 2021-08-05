<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="${finlib_path}/resource/main_offline.css" />
        <script type="text/javascript" src="${finlib_path}/resource/validate.js"></script>
        <script type="text/javascript" src="${finlib_path}/resource/serverCombo.js"></script>
        <script type="text/javascript" src="${finlib_path}/resource/ajax.js"></script>
        <script type="text/javascript" src="${finlib_path}/resource/common_functions.min.js"></script>
        <script type="text/javascript" src="javascript/dbobjversionrpt.js" ></script>
        <link rel="stylesheet" type="text/css" href="css/diffview.css"/>
        <script type="text/javascript" src="javascript/difflib.js"></script>
        <script type="text/javascript" src="javascript/diffview.js"></script>
        <style type="text/css">
            .top {text-align: center;}
            #diffoutput {width: 100%;}
            .textInput {display: block;width: 49%;float: left;}
            .spacer {margin-left: 10px;}
        </style>
        <title>Database Object Version Report</title>
    </head>
    <body onload="diffJS(0);">

        <div class="container"  >
            <div class="content" id="menuLoader3">
                <div class="menu_new" style="width:100%">
                    <div class="menu_caption_bg" style="width: 100%">
                        <div class="menu_caption_text">Database Object Version Report</div>
                    </div>
                    <div id="divDetail">
                        <style type="text/css" rel="stylesheet">

                            .Diff_Row_color
                            {
                                background-color: #FFBAB9;
                            }
                            .Diff_Column_color
                            {
                                background-color: #FF6D6B;
                            }
                        </style>

                        <div style="float:left;margin-left: 10px;
                             display:block;width:15px;height:13px;
                             background-color:#DDD;">
                        </div>
                        <div style="float:left;margin-left: 10px;">Empty</div>
                        <div style="float:left;margin-left: 10px;
                             display:block;width:15px;height:13px;
                             background-color:#9E9;">
                        </div>
                        <div style="float:left;margin-left: 10px;">Insert</div>
                        <div style="float:left;margin-left: 10px;
                             display:block;width:15px;height:13px;
                             background-color:#FD8;">
                        </div>
                        <div style="float:left;margin-left: 10px;">Replace</div>
                        <div style="float:left;margin-left: 10px;
                             display:block;width:15px;height:13px;
                             background-color:#E99;">
                        </div>
                        <div style="float:left;margin-left: 10px;">Delete</div>
                        <div style="float:left;margin-left: 10px;
                             display:block;width:15px;height:13px;
                             background-color:#EFEFEF;">
                        </div>
                        <div style="float:left;margin-left: 10px;">Skip</div>
                        <div id="loadAllData" class="report-main-content report_content" align="center" style="display: block; width: 100%;overflow-x:auto;overflow-y:hidden;">
                            <input type="hidden" id="oldDef" value="${oldDef}">
                            <input type="hidden" id="newDef" value="${newDef}">
                            <div id="datadiv">
                                <div id="diffoutput" style="overflow-y:scroll;max-height:400px"> </div>
                            </div>

                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>
</body>
</html>