<%
            String url = (String) session.getAttribute("aclurl");
            session.invalidate();   //Invalidate Current User Session
            response.sendRedirect(url);
%>