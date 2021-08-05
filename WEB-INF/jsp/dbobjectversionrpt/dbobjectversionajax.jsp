<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:choose>
    <c:when test="${process eq 'DBComboFill'}">
        <option value="-1" selected>-- Database --</option>
        <c:forEach items="${DBList}" var="list" >
            <option value="${list.DATABASE_ID}" >${list.DATABASENAME}</option>
        </c:forEach>
    </c:when>
    <c:when test="${process eq 'ObjComboFill'}">
        <option value="-1" selected>-- Object Name --</option>
        <c:forEach items="${ObjList}" var="list" >
                <option value="${list.OBJ_NAME}" >${list.OBJ_NAME}</option>
        </c:forEach>
    </c:when>
    <c:when test="${process eq 'fromObjversionComboFill'}">
        <option value="-1" selected>-- Object Version --</option>
        <c:forEach items="${ObjversList}" var="list" >
            <option value="${list.QUERY_ID}" >${list.OBJ_VERSION}</option>
        </c:forEach>
    </c:when>
    <c:when test="${process eq 'toObjversionComboFill'}">
        <option value="-1" selected>-- Object Version --</option>
        <c:forEach items="${ObjversList}" var="list" >
            <option value="${list.QUERY_ID}" >${list.OBJ_VERSION}</option>
        </c:forEach>
        <option value="Current" >Current</option>
    </c:when>
    <c:when test="${process eq 'MYSQL'}">
        <option value="-1" selected>-- Object Type --</option>
        <option value="MTABLE">Table | View</option>
        <option value="MFUNCTION">Function</option>
        <option value="MPROCEDURE">Procedure</option>
        <option value="MTRIGGER">Trigger</option>

    </c:when>
    <c:when test="${process eq 'ORACLE'}">
        <option value="-1" selected>-- Object Type --</option>
        <option value="OTABLE">Table | View</option>
        <option value="OFUNCTION">Function</option>
        <option value="OPROCEDURE">Procedure</option>
        <option value="OTRIGGER">Trigger</option>
        <option value="OSEQUENCE">Sequence</option>
        <option value="OPACKAGE">Package</option>
        <option value="OPACKAGEBODY">Package Body</option>
    </c:when>
    <c:when test="${process eq 'NoDBType'}">
        <option value="-1" selected>-- Object Type --</option>
    </c:when>
    <c:when test="${process eq 'Report'}">
        <tr>
            <td align="center">
                <div class="tbl_h1_bg">
                    <div class="menu_caption_text" id="viewRptTitle" style="color: orange">Data Request Report</div>
                </div>
            </td>
        </tr>
        <tr>
            <td>
                <div>
                    <table id="rptgrid" class="rpt_tbl tbl_h4_bg1 fullwidth" cellpadding="0" cellspacing="0">
                        <thead>
                            <tr class="report_caption" style="height: 35px;">
                                <th align="center" id="thDataReqId"><b>Data Request Id</b></th>
                                <th align="center" id="thRequestor"><b>Requestor</b></th>
                                <th align="center" id="thPurpose"><b>Purpose</b></th>
                                <th align="center" id="thsubDate"><b>Request Submit Date</b></th>
                                <th align="center" id="thExeDate"><b>Execution Date</b></th>
                                <th align="center" id="thExeDate"><b>Executor</b></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:set var="cnt" value="0"></c:set>
                            <c:forEach items="${rptLst}" var="record">
                                <tr style="height: 40px;">
                                    <td align="center">${record.QUERY_ID}</td>
                                    <td align="center">${record.REQUESTER}</td>
                                    <td align="center">${record.PURPOSE}</td>
                                    <td align="center">${record.ENTRYDATE}</td>
                                    <td align="center">${record.EXECUTION_DATE}</td>
                                    <td align="center">${record.Executor}</td>
                                    <td align="center">${record.LINK}</td>
                                </tr>
                                <c:set var="cnt" value="${cnt + 1}"></c:set>
                            </c:forEach>
                            <c:if test="${cnt eq 0}">
                                <tr>
                                    <td align="right" colspan="13">
                                        No Records Found.
                                    </td>
                                </tr>
                            </c:if>
                        </tbody>
                        <tfoot id="tfooter">
                            <tr>
                                <td colspan="13" align="center" id="closedPagerDiv"></td>
                            </tr>
                        </tfoot>
                    </table>
                </div>
            </td>
        </tr>
    </c:when>
</c:choose>