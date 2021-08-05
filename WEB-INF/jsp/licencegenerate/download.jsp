<%@page import="java.util.*" %>
<%@page import="java.io.*" %>
<%@page import="javax.servlet.*" %>
<%@page import="javax.servlet.http.*" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <title>Download File</title>
    </head>
    <body>
        <%
            String fileName = request.getAttribute("path").toString();
            String fName = request.getAttribute("fileName").toString();

            try
            {
                File f = new File (fileName +"/" +fName);
                int length = 0;
                ServletContext context = getServletConfig().getServletContext();
                String mimeType = context.getMimeType(fileName +"/" +fName);

                if(f.exists() && ((int) f.length()) >0)
                {
                    response.setContentType((mimeType != null) ? mimeType : "application/octet-stream");
                    response.setContentLength((int)f.length());
                    response.setHeader("Content-Disposition","attachment; filename=\"" + fName + "\";");

                    ServletOutputStream outs = response.getOutputStream();
                    byte[] bbuf = new byte[(int)f.length()];
                    DataInputStream in = new DataInputStream(new FileInputStream(f));
                    while((in!=null) && ((length = in.read(bbuf)) != -1))
                    {
                        outs.write(bbuf,0,length);
                    }
                    outs.flush();
                    outs.close();
                    in.close();
                }
            }
            catch(Exception ex)
            {
                //utility.Logger.log("Error in plcyimage : " + ex.toString());
            }
        %>
        <center><BR><font color="RED">File not found.</font></center>
    </body>

</html>



