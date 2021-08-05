<%--
    Document   : newjsp
    Created on : 10 MAY, 2014, 9:31:37 AM
    Author     : Jeegarkumar Patel
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<style type="text/css" rel="stylesheet">

    .Diff_Row_color
    {
        background-color: #FFBAB9;
    }
    .Diff_Column_color
    {
        background-color: #FF6D6B;
    }
</style>

<c:choose>
    <c:when test="${process eq 'DBComboFill'}">
        <option value="-1" selected>-- Database --</option>
        <c:forEach items="${DBList}" var="list" >
            <option value="${list.DATABASE_ID}" >${list.DATABASENAME}</option>
        </c:forEach>
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
        <!--<option value="OSEQUENCE">Sequence</option>
            <option value="OPACKAGE">Package</option>
            <option value="OPACKAGEBODY">Package Body</option>-->
    </c:when>
    <c:when test="${process eq 'NoDBType'}">
        <option value="-1" selected>-- Object Type --</option>
    </c:when>
    <c:when test="${process eq 'ObjComboFill'}">
        <option value="-1" selected>-- Object Name --</option>
        <c:forEach items="${ObjList}" var="list" >
            <c:if test="${list.OBJECT_TYPE eq 'VIEW'}">
                <option value="${list.OPT_VALUE}" >${list.OPT_TEXT} (${list.OBJECT_TYPE})</option>
            </c:if>
            <c:if test="${list.OBJECT_TYPE eq 'MVIEW'}">
                <option value="${list.OPT_VALUE}" >${list.OPT_TEXT} (${list.OBJECT_TYPE})</option>
            </c:if>
            <c:if test="${list.OBJECT_TYPE ne 'VIEW' and list.OBJECT_TYPE ne 'MVIEW'}">
                <option value="${list.OPT_VALUE}" >${list.OPT_TEXT}</option>
            </c:if>
        </c:forEach>
    </c:when>
    <c:when test="${process eq 'ListDefination1'}">
        <div> <label style="display:block;width:15px;height:15px;background-color:#FFBAB9;" ></label>   Row Difference </div>
        <div>  <label style="display:block;width:15px;height:15px;background-color:#FF6D6B;" ></label>   Column Difference </div>

        <div class="menu_caption_bg cursor-pointer" onclick="javascript:hide_menu('show_hide', 'load', 'nav_show', 'nav_hide')" id="divCaption" style="width: 100%" >
            <div class="menu_caption_text"> ${server1} - Definition --------------------------------------------------------------------------------------||----------------------------------------------------------- ${server2} - Definition</div>
            <span><a href="javascript:void(0)" name="show_hide" id="show_hide" class="nav_hide"></a></span>
        </div>
        <div id="load" class="report-main-content report_content" align="center" style="display: block; width: 100%;overflow-x:auto;overflow-y:hidden;">
            <c:choose>
                <c:when test="${Columns.size() > 0}">
                    <table width="100%" id="rptDefGrid" class="tbl_h4_bg1 rpt_tbl" cellpadding="0" cellspacing="0">
                        <tr class="report_caption" align="center" >
                            <td>COLUMN NAME</td>
                            <td>DATA TYPE</td>
                            <td>DATA LENGTH</td>
                            <td>NULLABLE</td>
                            <td>DATA DEFAULT</td>
                            <td>||</td>
                            <td>COLUMN NAME</td>
                            <td>DATA TYPE</td>
                            <td>DATA LENGTH</td>
                            <td>NULLABLE</td>
                            <td>DATA DEFAULT</td>
                        </tr>
                        <c:forEach items="${Columns}" var="list" varStatus="i">
                            <tr align="center" <c:if test="${list.FLAG eq '1' }"> style="background-color: #FFBAB9"</c:if>>
                                <c:choose>
                                    <c:when test="${list_flag1 eq 1}">
                                        <c:if test="${i.index eq 0}">
                                            <td colspan="5" rowspan="${fn:length(Columns)}">Table does not exist</td>
                                        </c:if>
                                    </c:when>
                                    <c:otherwise>
                                        <td <c:if test="${list.COLUMN_NAME_FLAG eq '1' }"> class="Diff_Column_color"</c:if>>${list.COLUMN_NAME1}</td>
                                        <td <c:if test="${list.DATA_TYPE_FLAG eq '1' }"> class="Diff_Column_color"</c:if>>${list.DATA_TYPE1}</td>
                                        <td <c:if test="${list.DATA_LENGTH_FLAG eq '1' }"> class="Diff_Column_color"</c:if>>
                                            <c:choose>
                                                <c:when test="${list.DATA_LENGTH1 == null or list.DATA_LENGTH1 eq ''}">
                                                    -
                                                </c:when>
                                                <c:otherwise>
                                                    ${list.DATA_LENGTH1}
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td <c:if test="${list.NULLABLE_FLAG eq '1' }"> class="Diff_Column_color"</c:if>>${list.NULLABLE1}</td>
                                        <td <c:if test="${list.DATA_DEFAULT_FLAG eq '1' }"> class="Diff_Column_color"</c:if>>
                                            <c:choose>
                                                <c:when test="${list.DATA_DEFAULT1 == null or list.DATA_DEFAULT1 eq ''}">
                                                    -
                                                </c:when>
                                                <c:otherwise>
                                                    ${list.DATA_DEFAULT1}
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                    </c:otherwise>
                                </c:choose>
                                <td>||</td>
                                <c:choose>
                                    <c:when test="${list_flag2 eq 1}">
                                        <c:if test="${i.index eq 0}">
                                            <td colspan="5" rowspan="${fn:length(Columns)}">Table does not exist</td>
                                        </c:if>
                                    </c:when>
                                    <c:otherwise>
                                        <td <c:if test="${list.COLUMN_NAME_FLAG eq '1' }"> class="Diff_Column_color"</c:if>>${list.COLUMN_NAME2}</td>
                                        <td <c:if test="${list.DATA_TYPE_FLAG eq '1' }"> class="Diff_Column_color"</c:if>>${list.DATA_TYPE2}</td>
                                        <td <c:if test="${list.DATA_LENGTH_FLAG eq '1' }"> class="Diff_Column_color"</c:if>>
                                            <c:choose>
                                                <c:when test="${list.DATA_LENGTH2 == null or list.DATA_LENGTH2 eq ''}">
                                                    -
                                                </c:when>
                                                <c:otherwise>
                                                    ${list.DATA_LENGTH2}
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td <c:if test="${list.NULLABLE_FLAG eq '1' }"> class="Diff_Column_color"</c:if>>${list.NULLABLE2}</td>
                                        <td <c:if test="${list.DATA_DEFAULT_FLAG eq '1' }"> class="Diff_Column_color"</c:if>>
                                            <c:choose>
                                                <c:when test="${list.DATA_DEFAULT2 == null or list.DATA_DEFAULT2 eq ''}">
                                                    -
                                                </c:when>
                                                <c:otherwise>
                                                    ${list.DATA_DEFAULT2}
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                    </c:otherwise>
                                </c:choose>
                            </tr>
                        </c:forEach>
                    </table>
                </c:when>
                <c:otherwise>
                    <table width="100%" id="rptDefGrid" class="tbl_h4_bg1 rpt_tbl" cellpadding="0" cellspacing="0">
                        <tr><td>No Record Found</td></tr>
                    </table>
                </c:otherwise>
            </c:choose>
        </div>
        <div>&nbsp;</div>
        <div class="menu_caption_bg cursor-pointer" onclick="javascript:hide_menu('show_hide2', 'loadIndex1', 'nav_show', 'nav_hide')" id="divCaption2" style="width:100%" >
            <div class="menu_caption_text">Index</div>
            <span><a href="javascript:void(0)" name="show_hide2" id="show_hide2" class="nav_hide"></a></span>
        </div>
        <div id="loadIndex1" class="report-main-content report_content" align="center" style="display: block; width: 100%;overflow-x:auto;overflow-y:hidden;">
            <c:choose>
                <c:when test="${Indexes.size() > 0}">
                    <table width="100%" id="rptIndexGrid" class="tbl_h4_bg1 rpt_tbl" cellpadding="0" cellspacing="0">
                        <tr class="report_caption" align="center" >
                            <td>COLUMN NAME</td>
                            <td>INDEX NAME</td>
                            <td>||</td>
                            <td>COLUMN NAME</td>
                            <td>INDEX NAME</td>
                        </tr>
                        <c:forEach items="${Indexes}" var="indexlist" varStatus="i">
                            <tr align="center" <c:if test="${indexlist.FLAG eq '1' }"> style="background-color: #FFBAB9"</c:if>>
                                <c:choose>
                                    <c:when test="${list_flag1 eq 1}">
                                        <c:if test="${i.index eq 0}">
                                            <td colspan="2" rowspan="${fn:length(Indexes)}">Table does not exist</td>
                                        </c:if>
                                    </c:when>
                                    <c:otherwise>
                                        <td <c:if test="${indexlist.COLUMN_NAME_FLAG eq '1' }"> class="Diff_Column_color"</c:if>>${indexlist.COLUMN_NAME1}</td>
                                        <td <c:if test="${indexlist.INDEX_NAME_FLAG eq '1' }"> class="Diff_Column_color"</c:if>>${indexlist.INDEX_NAME1}</td>
                                    </c:otherwise>
                                </c:choose>
                                <td>||</td>
                                <c:choose>
                                    <c:when test="${list_flag2 eq 1}">
                                        <c:if test="${i.index eq 0}">
                                            <td colspan="2" rowspan="${fn:length(Indexes)}">Table does not exist</td>
                                        </c:if>
                                    </c:when>
                                    <c:otherwise>
                                        <td <c:if test="${indexlist.COLUMN_NAME_FLAG eq '1' }"> class="Diff_Column_color"</c:if>>${indexlist.COLUMN_NAME2}</td>
                                        <td <c:if test="${indexlist.INDEX_NAME_FLAG eq '1' }"> class="Diff_Column_color"</c:if>>${indexlist.INDEX_NAME2}</td>

                                    </c:otherwise>
                                </c:choose>
                            </tr>
                        </c:forEach>
                    </table>
                </c:when>
                <c:otherwise>
                    <table width="100%" id="rptIndexGrid" class="tbl_h4_bg1 rpt_tbl" cellpadding="0" cellspacing="0">
                        <tr><td>No Record Found</td></tr>
                    </table>
                </c:otherwise>
            </c:choose>
        </div>
        <div>&nbsp;</div>
        <div class="menu_caption_bg cursor-pointer" onclick="javascript:hide_menu('show_hide3', 'loadCons1', 'nav_show', 'nav_hide')" id="divCaption3" style="width:100%">
            <div class="menu_caption_text">Constraints</div>
            <span><a href="javascript:void(0)" name="show_hide3" id="show_hide3" class="nav_hide"></a></span>
        </div>
        <div id="loadCons1" class="report-main-content report_content" align="center" style="display: block; width: 100%;overflow-x:auto;overflow-y:hidden;">
            <c:choose>
                <c:when test="${Constraints.size() > 0}">
                    <table width="100%" id="rptConsGrid" class="tbl_h4_bg1 rpt_tbl" cellpadding="0" cellspacing="0">
                        <tr class="report_caption" align="center" >
                            <td>CONSTRAINT TYPE</td>
                            <td>CONSTRAINT NAME </td>
                            <td>CONSTRAINT DETAIL</td>
                            <td>REFERENCED TABLE</td>
                            <td>REFERENCED COLUMN</td>
                            <td>||</td>
                            <td>CONSTRAINT TYPE</td>
                            <td>CONSTRAINT NAME </td>
                            <td>CONSTRAINT DETAIL</td>
                            <td>REFERENCED TABLE</td>
                            <td>REFERENCED COLUMN</td>
                        </tr>
                        <c:forEach items="${Constraints}" var="conslist" varStatus="i">
                            <tr align="center" <c:if test="${conslist.FLAG eq '1' }"> style="background-color: #FFBAB9"</c:if>>
                                <c:choose>
                                    <c:when test="${list_flag1 eq 1}">
                                        <c:if test="${i.index eq 0}">
                                            <td colspan="5" rowspan="${fn:length(Constraints)}">Table does not exist</td>
                                        </c:if>
                                    </c:when>
                                    <c:otherwise>
                                        <td <c:if test="${conslist.CONSTRAINT_TYPE_FLAG eq '1' }"> class="Diff_Column_color"</c:if>>
                                            <c:choose>
                                                <c:when test="${conslist.CONSTRAINT_TYPE1 == null or conslist.CONSTRAINT_TYPE1 eq ''}">
                                                    -
                                                </c:when>
                                                <c:otherwise>
                                                    ${conslist.CONSTRAINT_TYPE1}
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td <c:if test="${conslist.CONSTRAINT_NAME_FLAG eq '1' }"> class="Diff_Column_color"</c:if>>
                                            <c:choose>
                                                <c:when test="${conslist.CONSTRAINT_NAME1 == null or conslist.CONSTRAINT_NAME1 eq ''}">
                                                    -
                                                </c:when>
                                                <c:otherwise>
                                                    ${conslist.CONSTRAINT_NAME1}
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td <c:if test="${conslist.CONSTRAINT_DETAIL_FLAG eq '1' }"> class="Diff_Column_color"</c:if>>
                                            <c:choose>
                                                <c:when test="${conslist.CONSTRAINT_DETAIL1 == null or conslist.CONSTRAINT_DETAIL1 eq ''}">
                                                    -
                                                </c:when>
                                                <c:otherwise>
                                                    ${conslist.CONSTRAINT_DETAIL1}
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td <c:if test="${conslist.REFERENCED_TABLE_FLAG eq '1' }">  class="Diff_Column_color"</c:if>>
                                            <c:choose>
                                                <c:when test="${conslist.REFERENCED_TABLE1 == null or conslist.REFERENCED_TABLE1 eq ''}">
                                                    -
                                                </c:when>
                                                <c:otherwise>
                                                    ${conslist.REFERENCED_TABLE1}
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td <c:if test="${conslist.REFERENCED_COLUMN_FLAG eq '1' }"> class="Diff_Column_color"</c:if>>
                                            <c:choose>
                                                <c:when test="${conslist.REFERENCED_COLUMN1 == null or conslist.REFERENCED_COLUMN1 eq ''}">
                                                    -
                                                </c:when>
                                                <c:otherwise>
                                                    ${conslist.REFERENCED_COLUMN1}
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                    </c:otherwise>
                                </c:choose>
                                <td>||</td>
                                <c:choose>
                                    <c:when test="${list_flag2 eq 1}">
                                        <c:if test="${i.index eq 0}">
                                            <td colspan="5" rowspan="${fn:length(Constraints)}">Table does not exist</td>
                                        </c:if>
                                    </c:when>
                                    <c:otherwise>
                                        <td <c:if test="${conslist.CONSTRAINT_TYPE_FLAG eq '1' }"> class="Diff_Column_color"</c:if>>
                                            <c:choose>
                                                <c:when test="${conslist.CONSTRAINT_TYPE2 == null or conslist.CONSTRAINT_TYPE2 eq ''}">
                                                    -
                                                </c:when>
                                                <c:otherwise>
                                                    ${conslist.CONSTRAINT_TYPE2}
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td <c:if test="${conslist.CONSTRAINT_NAME_FLAG eq '1' }"> class="Diff_Column_color"</c:if>>
                                            <c:choose>
                                                <c:when test="${conslist.CONSTRAINT_NAME2 == null or conslist.CONSTRAINT_NAME2 eq ''}">
                                                    -
                                                </c:when>
                                                <c:otherwise>
                                                    ${conslist.CONSTRAINT_NAME2}
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td <c:if test="${conslist.CONSTRAINT_DETAIL_FLAG eq '1' }"> class="Diff_Column_color"</c:if>>
                                            <c:choose>
                                                <c:when test="${conslist.CONSTRAINT_DETAIL2 == null or conslist.CONSTRAINT_DETAIL2 eq ''}">
                                                    -
                                                </c:when>
                                                <c:otherwise>
                                                    ${conslist.CONSTRAINT_DETAIL2}
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td <c:if test="${conslist.REFERENCED_TABLE_FLAG eq '1' }"> class="Diff_Column_color"</c:if>>
                                            <c:choose>
                                                <c:when test="${conslist.REFERENCED_TABLE2 == null or conslist.REFERENCED_TABLE2 eq ''}">
                                                    -
                                                </c:when>
                                                <c:otherwise>
                                                    ${conslist.REFERENCED_TABLE2}
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td <c:if test="${conslist.REFERENCED_COLUMN_FLAG eq '1' }"> class="Diff_Column_color"</c:if>>
                                            <c:choose>
                                                <c:when test="${conslist.REFERENCED_COLUMN2 == null or conslist.REFERENCED_COLUMN2 eq ''}">
                                                    -
                                                </c:when>
                                                <c:otherwise>
                                                    ${conslist.REFERENCED_COLUMN2}
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                    </c:otherwise>
                                </c:choose>
                            </tr>
                        </c:forEach>
                    </table>
                </c:when>
                <c:otherwise>
                    <table width="100%" id="rptConsGrid" class="tbl_h4_bg1 rpt_tbl" cellpadding="0" cellspacing="0">
                        <tr><td>No Record Found</td></tr>
                    </table>
                </c:otherwise>
            </c:choose>
        </div>
        <div>&nbsp;</div>
        <div class="menu_caption_bg cursor-pointer" onclick="javascript:hide_menu('show_hide4', 'loadTrig1', 'nav_show', 'nav_hide')" id="divCaption3" style="width:100%" >
            <div class="menu_caption_text">Triggers</div>
            <span><a href="javascript:void(0)" name="show_hide4" id="show_hide4" class="nav_hide"></a></span>
        </div>
        <div id="loadTrig1" class="report-main-content report_content" align="center" style="display: block; width: 100%;overflow-x:auto;overflow-y:hidden;">
            <c:choose>
                <c:when test="${Triggers.size() > 0}">
                    <table width="100%" id="rptTrigGrid" class="tbl_h4_bg1 rpt_tbl" cellpadding="0" cellspacing="0">
                        <c:forEach items="${Triggers}" var="triglist" varStatus="i">
                            <tr align="left" <c:if test="${triglist.FLAG eq '1' }"> style="background-color: #FFBAB9"</c:if>>
                                <c:choose>
                                    <c:when test="${list_flag1 eq 1}">
                                        <c:if test="${i.index eq 0}">
                                            <td colspan="1" rowspan="${fn:length(Triggers)}">Table does not exist</td>
                                        </c:if>
                                    </c:when>
                                    <c:otherwise>
                                        <td <c:if test="${triglist.TRIGGER_FLAG eq '1' }"> class="Diff_Column_color"</c:if>>${triglist.TRIGGER1}</td>
                                    </c:otherwise>
                                </c:choose>
                                <td>||</td>
                                <c:choose>
                                    <c:when test="${list_flag2 eq 1}">
                                        <c:if test="${i.index eq 0}">
                                            <td colspan="1" rowspan="${fn:length(Triggers)}">Table does not exist</td>
                                        </c:if>
                                    </c:when>
                                    <c:otherwise>
                                        <td <c:if test="${triglist.TRIGGER_FLAG eq '1' }"> class="Diff_Column_color"</c:if>>${triglist.TRIGGER2}</td>
                                    </c:otherwise>
                                </c:choose>
                            </tr>
                        </c:forEach>
                    </table>
                </c:when>
                <c:otherwise>
                    <table width="100%" id="rptTrigGrid" class="tbl_h4_bg1 rpt_tbl" cellpadding="0" cellspacing="0">
                        <tr><td>No Record Found</td></tr>
                    </table>
                </c:otherwise>
            </c:choose>
        </div>
        <div>&nbsp;</div>

    </c:when>
    <c:when test="${process eq 'uniqueData'}">

        <div style="float:left;margin-left: 10px;"> <label style="display:block;width:15px;height:15px;background-color:#DDD;" ></label>Empty</div>
        <div style="float:left;margin-left: 25px;">  <label style="display:block;width:15px;height:15px;background-color:#FD8;" ></label>Replace</div>
        <div style="float:left;margin-left: 25px;"> <label style="display:block;width:15px;height:15px;background-color:#E99;" ></label>Delete </div>
        <div style="float:left;margin-left: 25px;">  <label style="display:block;width:15px;height:15px;background-color:#EFEFEF;" ></label>Skip</div>
        <div style="float:left;margin-left: 25px;"> <label style="display:block;width:15px;height:15px;background-color:#9E9;" ></label>Insert</div>
        <div class="top" style="display:none">
            <strong>Context size (optional):</strong> <input type="text" id="contextSize" value="" />
        </div>
             <div class="menu_caption_bg cursor-pointer"  id="divCaption1" style="width: 100%" onclick="javascript:hide_menu('show_hide14', 'loadAllData', 'nav_show', 'nav_hide')">
                <div class="menu_caption_text"> ${server1} - Definition -------------------------------------------------------------------------------------||----------------------------------------------------------- ${server2} - Definition</div>
             <span><a href="javascript:void(0)" name="show_hide14" id="show_hide14" class="nav_hide"></a></span>
             </div>
        <div id="loadAllData" class="report-main-content report_content" align="center" style="display: block; width: 100%;overflow-x:auto;overflow-y:hidden;">
          
            <table width="100%" id="rptTrigGrid" class="tbl_h4_bg1 rpt_tbl" cellpadding="0" cellspacing="0">
                <tr><td> <textarea id="baseText" name="txtaOtherObjDetail" rows="6" cols="8" style="width:100%;height:200px;"readonly="true" class="textInput"><c:forEach items="${DevData}" var="devlist">${devlist.Data1}</c:forEach>
                            </textarea></td>

                        <td> <textarea id="newText" name="txtaOtherObjDetail" rows="6" cols="8" style="width:100%;height:200px;" readonly="true" textInput spacer><c:forEach items="${TestData}" var="testlist">${testlist.Data1}</c:forEach>
                            </textarea></td>
                    </tr>

                </table>
                <div id="datadiv">
                    <div id="diffoutput" style="overflow-y:scroll;max-height:400px"> </div>
                </div>

            </div>
    </c:when>

</c:choose>