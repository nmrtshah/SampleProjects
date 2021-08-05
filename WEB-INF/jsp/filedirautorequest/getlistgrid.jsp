<%-- 
    Document   : getlistgrid
    Created on : 5 Feb, 2019, 6:31:43 PM
    Author     : Jigna Patel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>File Dir Auto Request</title>
        
        <link rel="stylesheet" type="text/css" href="${finlib_path}/resource/main_offline.css" />
        <link rel="stylesheet" type="text/css" href="${finlib_path}/dhtmlxSuite4/codebase/dhtmlx.css">
    </head>
    <body onload="onGetListLoad()">
        <div class="container">
            <div class="menu_new">
                <div class="menu_caption_bg menu_caption_text" id="divAddCaption">File Dir Auto Request</div>
                <input type="hidden" id="inpGetListPath" value="${filePath}">
                <div id="getListGridDiv" style="display: block">
                    <div id="getListGrid"></div>
                    <div id="getListPagingArea" align="center" style="display: block"></div>
                    <div id="getListRecinfoArea" align="center" style="display: none;"></div>
                </div>
            </div>
        </div>
        
        <script type="text/javascript" src="${finlib_path}/jquery/jquery-1.6.1/jquery-1.6.1.min.js"></script>
        <script type="text/javascript" src="${finlib_path}/dhtmlxSuite4/codebase/dhtmlx.js"></script>
        <script type="text/javascript" src="${finlib_path}/resource/common_functions.min.js"></script>
        <script type="text/javascript" src="javascript/filedirautorequest.js"></script>
    </body>
</html>