<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href='../css/main.css' rel='stylesheet' type='text/css'/>
    </head>
    <body>

<%

    finutils.connpool.DBConnManager dbconnmngr = new finutils.connpool.DBConnManager();

    int counter1=0;
    int counter2=0;

    String[] s1=dbconnmngr.getConnAliasArray();
    String[] s2=dbconnmngr.getConnPoolAliasArray();

    out.println("<br/><table border=1 align='center' width='40%'>");
    out.println("<thead><tr class='tbl_h1_bg'><td colspan='100%'>Connection Pool Audit</td></tr></thead>");

    for(int i=0;i<s1.length;i++)
    {
	counter1=0;

        for(int j=0;j<s2.length;j++)
        {

            if(s1[i].equalsIgnoreCase(s2[j]))
            {
                counter1++;
            }
        }

        counter2=0;

        for(int j=0;j<s2.length;j++)
        {

            if(s2[j].equalsIgnoreCase(s1[i] + "-Allocated" ))
            {
                counter2++;
            }
        }
        
        if(counter1!=0 || counter2!=0)
        {
            out.println("<tr><td>" + s1[i] + "</td><td>Allocated (" + counter2 + ")</td><td>Free(" + counter1 + ")</td></tr>");
        }

    }

    out.println("</table>");

    out.println("<br>");

    out.println("<br>CntAllocateFinConn :"+ dbconnmngr.getCntAllocateFinConn() );
    out.println("<br>CntReleaseFinConn  :"+ dbconnmngr.getCntReleaseFinConn() );

    out.println("<br>getConnAliasArray len=" + s1.length);
    out.println("<br>getConnPoolAliasArray len=" + s2.length);

    out.println("<br>Date=" + (new java.util.Date().toString()));



%>

    </body>
</html>
