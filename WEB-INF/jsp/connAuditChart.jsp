<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
            String finlibPath = finpack.FinPack.getProperty("finlib_path");
%>

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

        <link href="css/main.css" rel="stylesheet" type="text/css"/>

        <c:if test="${action eq 'ChartMainMenu'}">
            <link type="text/css" href="<%=finlibPath%>/jquery/jquery-ui-1.8.10.custom/css/blitzer/jquery-ui-1.8.10.custom.css" rel="stylesheet" />
            <script type="text/javascript" src="<%=finlibPath%>/jquery/jquery-ui-1.8.10.custom/js/jquery-1.4.4.min.js"></script>
            <script type="text/javascript" src="<%=finlibPath%>/jquery/jquery-ui-1.8.10.custom/js/jquery-ui-1.8.10.custom.min.js"></script>
            <script type="text/javascript">
                $(function() {
                    $("#datePicker").datepicker(
                    {
                        showOn: 'button',
                        buttonImage: 'images/calendar.gif',
                        buttonImageOnly: true,
                        changeMonth: true,
                        changeYear: true ,
                        dateFormat:'d-m-yy',
                        showButtonPanel: false
                    });

                });

                function chkMaxPrj()
                {
                    var combo=document.getElementById("prjList");
                    var cnt=0;
                    for( var i=0; i< combo.options.length; i++)
                    {
                        if(combo.options[i].selected==true)
                        {
                            cnt++;
                        }
                    }
                    if(cnt > 8)
                    {
                        alert("You can select Maximum 8 projects");
                        return false;
                    }
                    else if(cnt==0)
                    {
                        alert("Please Select atleast One Project.");
                        return false;
                    }
                    return true;
                }
            </script>
        </c:if>
        <c:if test="${action eq 'ChartMainMenu2'}">
            <link rel="stylesheet" type="text/css" href="<%=finlibPath%>/jquery/jquery-ui-1.8.10.custom/development-bundle/themes/blitzer/jquery.ui.all.css"  />
            <link rel="stylesheet" type="text/css" href="<%=finlibPath%>/jquery/jquery-ui-1.8.10.custom/css/blitzer/jquery-ui-1.8.10.custom.css"  />
            <script type="text/javascript" src="<%=finlibPath%>/jquery/jquery-ui-1.8.10.custom/development-bundle/jquery-1.4.4.js"></script>
            <script type="text/javascript" src="<%=finlibPath%>/jquery/jquery-ui-1.8.10.custom/development-bundle/ui/jquery.ui.core.js"></script>
            <script type="text/javascript" src="<%=finlibPath%>/jquery/jquery-ui-1.8.10.custom/development-bundle/ui/jquery.ui.datepicker.js"></script>
            <script type="text/javascript" src="javascript/ajaxfunctions.js"></script>
            <link type="text/css" href="" rel="stylesheet" />
            <script type="text/javascript" src="js/jquery-1.4.4.min.js"></script>
            <script type="text/javascript" src="js/jquery-ui-1.8.10.custom.min.js"></script>
        </c:if>
        <c:if test="${action eq 'ChartMainMenu1'}">

            <link type="text/css" href="<%=finlibPath%>/jquery/jquery-ui-1.8.10.multiselect/css/ui.multiselect.css" rel="stylesheet" />
            <script type="text/javascript" src="<%=finlibPath%>/jquery/jquery-ui-1.8.10.multiselect/js/jquery-1.4.2.min.js"></script>
            <script type="text/javascript" src="<%=finlibPath%>/jquery/jquery-ui-1.8.10.multiselect/js/jquery-ui-1.8.custom.min.js"></script>
            <script type="text/javascript" src="<%=finlibPath%>/jquery/jquery-ui-1.8.10.multiselect/js/plugins/localisation/jquery.localisation-min.js"></script>
            <script type="text/javascript" src="<%=finlibPath%>/jquery/jquery-ui-1.8.10.multiselect/js/plugins/scrollTo/jquery.scrollTo-min.js"></script>
            <script type="text/javascript" src="<%=finlibPath%>/jquery/jquery-ui-1.8.10.multiselect/js/ui.multiselect.js"></script>
            <script type="text/javascript">
                $(function(){
                    $.localise('ui-multiselect', {language: 'en',path: '<%=finlibPath%>/jquery/jquery-ui-1.8.10.multiselect/js/locale/'});
                    $(".multiselect").multiselect({ dividerLocation: 0.5 });
                   
                });
            </script>
        </c:if>
        <c:if test="${action eq 'drawChart'}">
            <link type="text/css" href="<%=finlibPath%>/jquery/jquery-ui-1.8.10.custom/css/blitzer/jquery-ui-1.8.10.custom.css" rel="stylesheet" />
            <script type="text/javascript" src="<%=finlibPath%>/jquery/jquery-ui-1.8.10.custom/js/jquery-1.4.4.min.js"></script>
            <script type="text/javascript" src="<%=finlibPath%>/jquery/jquery-ui-1.8.10.custom/js/jquery-ui-1.8.10.custom.min.js"></script>

            <script language="javascript" type="text/javascript" src="<%=finlibPath%>/jquery/jquery.jqplot.1.0.0/excanvas.min.js"></script>
            <script language="javascript" type="text/javascript" src="<%=finlibPath%>/jquery/jquery.jqplot.1.0.0/jquery.jqplot.min.js"></script>
            <script language="javascript" type="text/javascript" src="<%=finlibPath%>/jquery/jquery.jqplot.1.0.0/plugins/jqplot.categoryAxisRenderer.min.js"></script>
            <script language="javascript" type="text/javascript" src="<%=finlibPath%>/jquery/jquery.jqplot.1.0.0/plugins/jqplot.highlighter.min.js"></script>
            <link rel="stylesheet" type="text/css" href="<%=finlibPath%>/jquery/jquery.jqplot.1.0.0/jquery.jqplot.css" />

            <%
                        out.println("<script type=\"text/javascript\" language=\"javascript\">");
                        out.println("");

                        List projectListP = (List) request.getAttribute("prjList");
                        List dateListP = (List) request.getAttribute("dateList");
                        List projectWiseLeaksP = (List) request.getAttribute("leakList");

                        out.print("var jsonObj = {");
                        for (int i = 0; i < projectListP.size(); i++)
                        {
                            String prjName = projectListP.get(i).toString().replace(',', '_');
                            prjName = prjName.replace('-', '_');
                            prjName = prjName.replace('.', '_');

                            out.print("\"" + prjName + "\" : [");

                            List lst = (List) projectWiseLeaksP.get(i);

                            for (int j = 0; j < lst.size(); j++)
                            {
                                if (j == 0)
                                {
                                    out.print(lst.get(j));
                                }
                                else
                                {
                                    out.print("," + lst.get(j));
                                }
                            }
                            out.print(" ],");
                        }
                        out.print(" \"xAxis\": [");
                        for (int k = 0; k < dateListP.size(); k++)
                        {
                            if (k == 0)
                            {
                                out.print("'" + dateListP.get(k) + "'");
                            }
                            else
                            {
                                out.print(", '" + dateListP.get(k) + "'");
                            }
                        }
                        out.print(" ] };");
                        out.println();
                        out.println();
                        out.println("$(function()");
                        out.println("{");
                        out.print("$.jqplot('chart', [");
                        for (int i = 0; i < projectListP.size(); i++)
                        {
                            String prjName = projectListP.get(i).toString().replace(',', '_');
                            prjName = prjName.replace('-', '_');
                            prjName = prjName.replace('.', '_');

                            if (i == 0)
                            {
                                out.print("jsonObj." + prjName);
                            }
                            else
                            {
                                out.print(",jsonObj." + prjName);
                            }
                        }
                        out.print("],CreateLineChartOptions());");
                        out.println("});");
                        out.println();
                        out.println();
                        out.println("function CreateLineChartOptions()");
                        out.println("{");
                        out.println("var optionsObj = {");
                        out.println("title: 'Connection Audit Report',");
                        out.println("axes: {");
                        out.println("xaxis: {");
                        out.println("renderer: $.jqplot.CategoryAxisRenderer,");
                        out.println("ticks: jsonObj.xAxis");
                        out.println("}");
                        out.println("},");
                        out.print("series: [");
                        for (int j = 0; j < projectListP.size(); j++)
                        {
                            if (j == 0)
                            {
                                out.print(" {label:'" + projectListP.get(j) + "'}");
                            }
                            else
                            {
                                out.print(",{label:'" + projectListP.get(j) + "'}");
                            }
                        }
                        out.println("],");
                        out.println("seriesDefaults:{");
                        out.println("markerOptions:{");
                        out.println("show: true,");
                        out.println("style: 'diamond'");
                        out.println("}");
                        out.println("},");
                        out.println("legend: {");
                        out.println("show: true,");
                        out.println("location: 'nw'");
                        out.println("},");
                        out.println("highlighter: {");
                        out.println("showTooltip: true,");
                        out.println("tooltipFade: true");
                        out.println("}");
                        out.println("};");
                        out.println("return optionsObj;");
                        out.println("}");
                        out.println("</script>");

            %>
        </c:if>
    </head>
    <body>
        <c:if test="${action eq 'ChartMainMenu'}">
            <form id="connauditchart" name="connauditchart" method="POST" action="connaudit.fin?cmdAction=chartData">
                <div id="mainform">
                    <br/>
                    <table align="center" class="tbl_border1" id="selReportType">

                        <tr class="tbl_h1_bg">
                            <th colspan="100%">Connection Audit Report(Chart)</th>
                        </tr>
                        <tr>
                            <th colspan="100%">&nbsp;</th>
                        </tr>

                        <tr>
                            <td align="right" width="40%">
                                Select Date :
                            </td>
                            <td align="left">
                                <jsp:useBean id="now" class="java.util.Date" scope="request"></jsp:useBean>
                                <%
                                            now.setDate(now.getDate() - 1);
                                %>
                                <input type="text" name="datePicker" id="datePicker" readonly value='<fmt:formatDate pattern="d-M-yyyy" value="${now}"/>' />
                            </td>
                        </tr>
                        <tr>
                            <td align="right">
                                Select Projects :
                            </td>

                            <td align="left">
                                <c:choose>
                                    <c:when test="${prjList ne null}">
                                        <select name="prjList" id="prjList" multiple="true" size="8" _class="multiselect" _style="width: 600px; height:100px">
                                                <c:forEach items="${prjList}" var="list">
                                                    <option value="${list.PROJECT_NAME}">${list.PROJECT_LABEL}</option>
                                                </c:forEach>

                                        </select>
                                    </c:when>
                                </c:choose>

                            </td>
                        </tr>
                        <tr>
                            <td colspan="100%" align="center">
                                <input type="submit" name="btnsubmit" value="Submit" onclick="javascript :return chkMaxPrj();" />
                                &nbsp;
                                <input type="reset" name="action2" value="Reset"/>
                            </td>
                        </tr>
                    </table>

                </div>
            </form>
        </c:if>
        <c:if test="${action eq 'drawChart'}">

            <c:choose>
                <c:when test="${dateList ne null && fn:length(dateList) > 0}">
                    <br/>
                    <center><div id="chart" style="width:1000px; height:400px;" align="center"></div></center>
                </c:when>
                <c:otherwise>
                    <br/>
                    <div class="tbl_h2_bg" align="center" style="font-size: large">No Data Found</div>
                </c:otherwise>
            </c:choose>
            <div align="center">
                <br/>
                <br/>
                <input onclick="javascript:history.back();" name="back" id="back" type="button" value="Back" />
            </div>

        </c:if>
    </body>
</html>
