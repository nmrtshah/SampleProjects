<%
            HttpSession ses = request.getSession(true);
            ses.setAttribute("emp_name", request.getParameter("username"));
            ses.setAttribute("username", request.getParameter("userid"));
            ses.setAttribute("mecode", request.getParameter("mecode"));
            ses.setAttribute("ACLEmpCode", request.getParameter("mecode"));
            ses.setAttribute("aclurl", request.getParameter("aclurl"));
            ses.setAttribute("sid", request.getParameter("sessionId"));
            ses.setAttribute("loginName", request.getParameter("loginName"));
            ses.setAttribute("Type", request.getParameter("Type"));

            response.sendRedirect("welcome.fin?cmdAction=welcome");

%>
