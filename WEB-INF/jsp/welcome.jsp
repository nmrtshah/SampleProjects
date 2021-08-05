<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
    <head>
        <title>Fin Studio</title>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
        <link rel="stylesheet" href="css/header.css" type="text/css">                
    </head>
    <script type="text/javascript" language="JavaScript">
        var iHeaderFrameHeight = 120;
        if ( document.layers ) {
            iHeaderFrameHeight += 18; }
        document.write("<FRAMESET ROWS='" + iHeaderFrameHeight + ",*' BORDER=0 FRAMESPACING=0 TOPMARGIN=0 LEFTMARGIN=0 MARGINHEIGHT=0 MARGINWIDTH=0>");
    </script>
        <FRAME NAME="header" ID="header" src="welcome.fin?cmdAction=header" SCROLLING="auto" MARGINHEIGHT="0" MARGINWIDTH="0" frameborder="0"></FRAME>
        <FRAME NAME="content" ID="content" src="welcome.fin?cmdAction=content" SCROLLING="auto" MARGINHEIGHT="0" MARGINWIDTH="0" frameborder="0"></FRAME>
    </FRAMESET>
</html>