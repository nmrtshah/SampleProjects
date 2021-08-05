<%-- 
    Document   : jquerydemos
    Created on : Mar 14, 2011, 6:35:39 PM
    Author     : njuser
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%
            String finlibPath = finpack.FinPack.getProperty("finlib_path");
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JQuery </title>
        <link type="text/css" href="<%=finlibPath%>/jquery/jquery-ui-1.8.10.custom/css/blitzer/jquery-ui-1.8.10.custom.css" rel="stylesheet" />
        <script type="text/javascript" src="<%=finlibPath%>/jquery/jquery-ui-1.8.10.custom/js/jquery-1.4.4.min.js"></script>
        <script type="text/javascript" src="<%=finlibPath%>/jquery/jquery-ui-1.8.10.custom/js/jquery-ui-1.8.10.custom.min.js"></script>
        <script type="text/javascript">
            $(function() {
                var availableTags = [
                    "ActionScript",
                    "AppleScript",
                    "Asp",
                    "BASIC",
                    "C",
                    "C++",
                    "Clojure",
                    "COBOL",
                    "ColdFusion",
                    "Erlang",
                    "Fortran",
                    "Groovy",
                    "Haskell",
                    "Java",
                    "JavaScript",
                    "Lisp",
                    "Perl",
                    "PHP",
                    "Python",
                    "Ruby",
                    "Scala",
                    "Scheme"
                ];
                $( "#tags" ).autocomplete({
                    source: availableTags
                });
            });
        </script>
        <style type="text/css">
            /*demo page css*/
            body{ font: 62.5% "Trebuchet MS", sans-serif; margin: 50px;}
            .demoHeaders { margin-top: 2em; }
            #dialog_link {padding: .4em 1em .4em 20px;text-decoration: none;position: relative;}
            #dialog_link span.ui-icon {margin: 0 5px 0 0;position: absolute;left: .2em;top: 50%;margin-top: -8px;}
            ul#icons {margin: 0; padding: 0;}
            ul#icons li {margin: 2px; position: relative; padding: 4px 0; cursor: pointer; float: left;  list-style: none;}
            ul#icons span.ui-icon {float: left; margin: 0 4px;}
        </style>

    </head>
    <body>
        <h1>Welcome to jQuery UI!</h1>
        <h2 class="demoHeaders">Autocomplete</h2>
        <div class="demo">

            <div class="ui-widget">
                <label for="tags">Tags: </label>
                <input id="tags">
            </div>

        </div>



        <div style="display: none;" class="demo-description">
            <p>The Autocomplete widgets provides suggestions while you type into the field. Here the suggestions are tags for programming languages, give "ja" (for Java or JavaScript) a try.</p>
            <p>The datasource is a simple JavaScript array, provided to the widget using the source-option.</p>
        </div>
    </body>
</html>
