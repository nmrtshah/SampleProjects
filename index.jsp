<%

//set session expire time
session.setMaxInactiveInterval(24*60*60);
session.setAttribute("emp_name","TEST");
session.setAttribute("username","TEST");
session.setAttribute("mecode","TEST");
session.setAttribute("ACLEmpCode","TEST");
session.setAttribute("aclurl","TEST");
session.setAttribute("sid","TEST");
response.sendRedirect("welcome.fin?cmdAction=welcome");

%>