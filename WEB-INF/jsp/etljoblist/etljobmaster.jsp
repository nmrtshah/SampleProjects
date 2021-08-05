<%-- 
    Document   : etljobmaster
    Created on : 25 Apr, 2017, 5:31:22 PM
    Author     : Siddharth Patel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ETL Job Master</title>
        <meta http-equiv="Content-Type" content="text/html" />
        <link rel="stylesheet" type="text/css" href="${finlibPath}/resource/main_offline.css" />
        <script type="text/javascript" src="${finlibPath}/jquery/jquery-1.6.1/jquery-1.6.1.min.js"></script>
        <script type="text/javascript" src="${finlibPath}/resource/common_functions.min.js"></script>
        <script type="text/javascript" src="${finlibPath}/jquery/jquery-ui-1.8.13.custom/js/jquery-ui-1.8.13.custom.min.js"></script>
        <script type="text/javascript" src="${finlibPath}/jquery/jquery-ui-1.8.13.custom/development-bundle/ui/jquery.ui.core.js"></script>
        <script type="text/javascript" src="javascript/ajaxfunctions.js"></script>
        <script type="text/javascript" src="javascript/ETLJobList.js"></script>
        <script type="text/javascript" src="${finlibPath}/resource/ajax.js"></script>
        <script type="text/javascript" src="${finlibPath}/resource/validate.js"></script>

    </head>
    <body>
        <div class="container">
            <form  name="MenuForm" id="MenuForm" method="post" action="" onsubmit="return false;">
                <div class="report_content" align="center">
                    <div class="menu_new">
                        <div class="menu_caption_bg" align="center">
                            <div class="menu_caption_text"> ETL File Master</div>
                        </div>

                        <div id="insertMenu">
                            <table align="center" border="0" cellpadding="0" cellspacing="2" class="menu_subcaption"  width="65%">
                                <tr>
                                    <td class="report_content_text">
                                        Table Name<span class="astriek">*</span>:
                                    </td>
                                    <td class="report_content_form">
                                        <input type="text" id="tableName" name="tableName" maxlength="100">
                                    </td>
                                </tr>

                                <tr>
                                    <td class="report_content_text">
                                        From Server<span class="astriek">*</span>:
                                    </td>
                                    <td class="report_content_form">
                                        <input type="text" id="fromServer" name="fromServer" maxlength="100">
                                    </td>
                                </tr>

                                <tr>
                                    <td class="report_content_text">
                                        To server<span class="astriek">*</span>:
                                    </td>
                                    <td class="report_content_form">
                                        <input type="text" id="toServer" name="toServer" maxlength="100">
                                    </td>
                                </tr>

                                <tr>
                                    <td class="report_content_text">
                                        From Schema<span class="astriek">*</span>:
                                    </td>
                                    <td class="report_content_form">
                                        <input type="text" id="fromSchema" name="fromSchema" maxlength="100">
                                    </td>
                                </tr>

                                <tr>
                                    <td class="report_content_text">
                                        To Table<span class="astriek">*</span>:
                                    </td>
                                    <td class="report_content_form">
                                        <input type="text" id="toTable" name="toTable" maxlength="100">
                                    </td>
                                </tr>

                                <tr>
                                    <td class="report_content_text">
                                        To Schema<span class="astriek">*</span>:
                                    </td>
                                    <td class="report_content_form">
                                        <input type="text" id="toSchema" name="toSchema" maxlength="100">
                                    </td>
                                </tr>

                                <tr>
                                    <td class="report_content_text">
                                        KJB<span class="astriek">*</span>:
                                    </td>
                                    <td class="report_content_form">
                                        <input type="text" id="kjb" name="kjb" maxlength="100">
                                    </td>
                                </tr>

                                <tr>
                                    <td class="report_content_text">
                                        KTR :
                                    </td>
                                    <td class="report_content_form">
                                        <input type="text" id="ktr" name="ktr" maxlength="100">
                                    </td>
                                </tr>

                                <tr>
                                    <td class="report_content_text">
                                        Paths<span class="astriek">*</span>:
                                    </td>
                                    <td class="report_content_form">
                                        <input type="text" id="paths" name="paths" maxlength="2000">
                                    </td>
                                </tr>
                                <tr>
                                    <td class="report_content_text">
                                        Cron File :
                                    </td>
                                    <td class="report_content_form">
                                        <input type="text" id="cronFile" name="cronFile" maxlength="100">
                                    </td>
                                </tr>
                                <tr>
                                    <td class="report_content_text">
                                        Main Job :
                                    </td>
                                    <td class="report_content_form">
                                        <input type="text" id="mainJob" name="mainJob" maxlength="2000">
                                    </td>
                                </tr>
                                <tr>
                                    <td class="report_content_text">
                                        Type :
                                    </td>
                                    <td class="report_content_form">
                                        <input type="text" id="type" name="type" maxlength="100">
                                    </td>
                                </tr>
                                <tr>
                                    <td class="report_content_text">
                                        Cron Tab :
                                    </td>
                                    <td class="report_content_form">
                                        <input type="text" id="cronTab" name="cronTab" maxlength="2000">
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center" id="tdAddButtons" colspan="2">
                                        <input class="button" type="button" id="apply" name="apply" Value="Apply" onclick="javascript: onApplyClick();" style="width: 100px">
                                        <input class="button" type="button" id="reset" name="reset" Value="Reset" onclick="javascript: onResetClick();" style="width: 100px">
                                    </td>
                                </tr>
                            </table>
                        </div>
                        <div id="response"></div>
                    </div>
                </div>
            </form>
        </div>
    </body>
</html>
