<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

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
        <option value="OTABLE">Table | View | MView</option>
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
    <c:when test="${process eq 'ListDefination'}">
        <div class="menu_caption_bg cursor-pointer" onclick="javascript:hide_menu('show_hide','load', 'nav_show','nav_hide')" id="divCaption" >
            <div class="menu_caption_text">Definition</div>
            <span><a href="javascript:void(0)" name="show_hide" id="show_hide" class="nav_hide"></a></span>
        </div>
        <div id="load" class="report-main-content report_content" align="center" style="display: block; width: 100%;">
            <c:choose>
                <c:when test="${Columns.size() > 0}">
                    <table width="100%" id="rptDefGrid" class="tbl_h4_bg1 rpt_tbl" cellpadding="0" cellspacing="0">
                        <tr class="report_caption" align="center" >
                            <td>COLUMN NAME</td>
                            <td>DATA TYPE</td>
                            <td>DATA LENGTH</td>
                            <td>NULLABLE</td>
                            <td>DATA DEFAULT</td>
                        </tr>
                        <c:forEach items="${Columns}" var="list">
                            <tr align="center">
                                <td>${list.COLUMN_NAME}</td>
                                <td>${list.DATA_TYPE}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${list.DATA_LENGTH == null or list.DATA_LENGTH eq ''}">
                                            -
                                        </c:when>
                                        <c:otherwise>
                                            ${list.DATA_LENGTH}
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>${list.NULLABLE}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${list.DATA_DEFAULT == null or list.DATA_DEFAULT eq ''}">
                                            -
                                        </c:when>
                                        <c:otherwise>
                                            ${list.DATA_DEFAULT}
                                        </c:otherwise>
                                    </c:choose>
                                </td>
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
        <div class="menu_caption_bg cursor-pointer" onclick="javascript:hide_menu('show_hide2','loadIndex', 'nav_show','nav_hide')" id="divCaption2" >
            <div class="menu_caption_text">Index</div>
            <span><a href="javascript:void(0)" name="show_hide2" id="show_hide2" class="nav_hide"></a></span>
        </div>
        <div id="loadIndex" class="report-main-content report_content" align="center" style="display: block; width: 100%;">
            <c:choose>
                <c:when test="${Indexes.size() > 0}">
                    <table width="100%" id="rptIndexGrid" class="tbl_h4_bg1 rpt_tbl" cellpadding="0" cellspacing="0">
                        <tr class="report_caption" align="center" >
                            <td>COLUMN NAME</td>
                            <td>INDEX NAME</td>
                        </tr>
                        <c:forEach items="${Indexes}" var="indexlist">
                            <tr align="center">
                                <td>${indexlist.COLUMN_NAME}</td>
                                <td>${indexlist.INDEX_NAME}</td>
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
        <div class="menu_caption_bg cursor-pointer" onclick="javascript:hide_menu('show_hide3','loadCons', 'nav_show','nav_hide')" id="divCaption3" >
            <div class="menu_caption_text">Constraints</div>
            <span><a href="javascript:void(0)" name="show_hide3" id="show_hide3" class="nav_hide"></a></span>
        </div>
        <div id="loadCons" class="report-main-content report_content" align="center" style="display: block; width: 100%;">
            <c:choose>
                <c:when test="${Constraints.size() > 0}">
                    <table width="100%" id="rptConsGrid" class="tbl_h4_bg1 rpt_tbl" cellpadding="0" cellspacing="0">
                        <tr class="report_caption" align="center" >
                            <td>CONSTRAINT TYPE</td>
                            <td>CONSTRAINT NAME </td>
                            <td>CONSTRAINT_COLUMN</td>
                            <td>CONSTRAINT DETAIL</td>
                            <td>REFERENCED TABLE</td>
                            <td>REFERENCED COLUMN</td>
                        </tr>
                        <c:forEach items="${Constraints}" var="conslist">
                            <tr align="center">
                                <td>
                                    <c:choose>
                                        <c:when test="${conslist.Constraint_Type == null or conslist.Constraint_Type eq ''}">
                                            -
                                        </c:when>
                                        <c:otherwise>
                                            ${conslist.Constraint_Type}
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${conslist.Constraint_Name == null or conslist.Constraint_Name eq ''}">
                                            -
                                        </c:when>
                                        <c:otherwise>
                                            ${conslist.Constraint_Name}
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${conslist.Constraint_Column == null or conslist.Constraint_Column eq ''}">
                                            -
                                        </c:when>
                                        <c:otherwise>
                                            ${conslist.Constraint_Column}
                                        </c:otherwise>
                                    </c:choose>
                                </td>                                
                                <td>
                                    <c:choose>
                                        <c:when test="${conslist.Constraint_Detail == null or conslist.Constraint_Detail eq ''}">
                                            -
                                        </c:when>
                                        <c:otherwise>
                                            ${conslist.Constraint_Detail}
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${conslist.Referenced_Table == null or conslist.Referenced_Table eq ''}">
                                            -
                                        </c:when>
                                        <c:otherwise>
                                            ${conslist.Referenced_Table}
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${conslist.Referenced_Column == null or conslist.Referenced_Column eq ''}">
                                            -
                                        </c:when>
                                        <c:otherwise>
                                            ${conslist.Referenced_Column}
                                        </c:otherwise>
                                    </c:choose>
                                </td>
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
        <div class="menu_caption_bg cursor-pointer" onclick="javascript:hide_menu('show_hide4','loadTrig', 'nav_show','nav_hide')" id="divCaption3" >
            <div class="menu_caption_text">Triggers</div>
            <span><a href="javascript:void(0)" name="show_hide4" id="show_hide4" class="nav_hide"></a></span>
        </div>
        <div id="loadTrig" class="report-main-content report_content" align="center" style="display: block; width: 100%;">
            <c:choose>
                <c:when test="${Triggers.size() > 0}">
                    <table width="100%" id="rptTrigGrid" class="tbl_h4_bg1 rpt_tbl" cellpadding="0" cellspacing="0">
                        <tr class="report_caption" align="center" >
                            <td>TRIGGER NAME</td>
                            <td>TRIGGER TYPE</td>
                            <td>TRIGGER EVENT</td>
                            <td>STATUS</td>                            
                        </tr>
                        <c:forEach items="${Triggers}" var="triglist">
                            <tr align="left">
                                <td>${triglist.trigger_name}</td>
                                <td>${triglist.trigger_type}</td>
                                <td>${triglist.triggering_event}</td>
                                <td>${triglist.status}</td>
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
    </c:when>
    <c:when test="${process eq 'tblDependencies'}">
        <div>&nbsp;</div>
        <div class="menu_caption_bg cursor-pointer" onclick="javascript:hide_menu('show_hide5','loadTblDep', 'nav_show','nav_hide')" id="divCaption4" >
            <div class="menu_caption_text">Table Dependencies</div>
            <span><a href="javascript:void(0)" name="show_hide5" id="show_hide5" class="nav_hide"></a></span>
        </div>
        <div id="loadTblDep" class="report-main-content report_content" align="center" style="display: block; width: 100%;">
            <table width="100%" id="rptDepGrid" class="tbl_h4_bg1 rpt_tbl" cellpadding="0" cellspacing="0">
                <c:if test="${oraDep.size() > 0 or oraLogDep.size() > 0 or oraDUDep.size() > 0 or msqlLogDep.size() > 0 or msqlViewDep.size() > 0 or msqlInfoBrightDep.size() > 0}">
                    <tr class="report_caption" align="center" >
                        <td>Dependency</td>
                        <td>Dep. Server</td>
                        <td>Dep. Shcema</td>
                        <td>Dep. ObjectType</td>
                        <td>Dep. ObjectName</td>
                    </tr>
                </c:if>
                <c:if test="${oraDep.size() > 0}">
                    <c:forEach items="${oraDep}" var="list">
                        <tr align="left">
                            <td>
                                ${list.dependency}
                            </td>
                            <td>
                                ${list.depServer}
                            </td>
                            <td>
                                ${list.depSchema}
                            </td>
                            <td>
                                ${list.depObjType}
                            </td>
                            <td>
                                ${list.depObjName}
                            </td>
                        </tr>
                    </c:forEach>
                </c:if>
                <c:if test="${oraLogDep.size() > 0}">
                    <c:forEach items="${oraLogDep}" var="list">
                        <tr align="left">
                            <td>
                                ${list.dependency}
                            </td>
                            <td>
                                ${list.depServer}
                            </td>
                            <td>
                                ${list.depSchema}
                            </td>
                            <td>
                                ${list.depObjType}
                            </td>
                            <td>
                                ${list.depObjName}
                            </td>
                        </tr>
                    </c:forEach>
                </c:if>
                <c:if test="${oraDUDep.size() > 0}">
                    <c:forEach items="${oraDUDep}" var="list">
                        <tr align="left">
                            <td>
                                ${list.dependency}
                            </td>
                            <td>
                                ${list.depServer}
                            </td>
                            <td>
                                ${list.depSchema}
                            </td>
                            <td>
                                ${list.depObjType}
                            </td>
                            <td>
                                ${list.depObjName}
                            </td>
                        </tr>
                    </c:forEach>
                </c:if>
                <c:if test="${msqlLogDep.size() > 0}">
                    <c:forEach items="${msqlLogDep}" var="list">
                        <tr align="left">
                            <td>
                                ${list.dependency}
                            </td>
                            <td>
                                ${list.depServer}
                            </td>
                            <td>
                                ${list.depSchema}
                            </td>
                            <td>
                                ${list.depObjType}
                            </td>
                            <td>
                                ${list.depObjName}
                            </td>
                        </tr>
                    </c:forEach>
                </c:if>
                <c:if test="${msqlViewDep.size() > 0}">
                    <c:forEach items="${msqlViewDep}" var="list">
                        <tr align="left">
                            <td>
                                ${list.dependency}
                            </td>
                            <td>
                                ${list.depServer}
                            </td>
                            <td>
                                ${list.depSchema}
                            </td>
                            <td>
                                ${list.depObjType}
                            </td>
                            <td>
                                ${list.depObjName}
                            </td>
                        </tr>
                    </c:forEach>
                </c:if>
                <c:if test="${msqlInfoBrightDep.size() > 0}">
                    <c:forEach items="${msqlInfoBrightDep}" var="list">
                        <tr align="left">
                            <td>
                                ${list.dependency}
                            </td>
                            <td>
                                ${list.depServer}
                            </td>
                            <td>
                                ${list.depSchema}
                            </td>
                            <td>
                                ${list.depObjType}
                            </td>
                            <td>
                                ${list.depObjName}
                            </td>
                        </tr>
                    </c:forEach>
                </c:if>
                <c:if test="${MViewDep.size() > 0}">
                    <c:forEach items="${MViewDep}" var="list">
                        <tr align="left">
                            <td>
                                ${list.dependency}
                            </td>
                            <td>
                                ${list.depServer}
                            </td>
                            <td>
                                ${list.depSchema}
                            </td>
                            <td>
                                ${list.depObjType}
                            </td>
                            <td>
                                ${list.depObjName}
                            </td>
                        </tr>
                    </c:forEach>
                </c:if>
                <c:if test="${msqlSameNmTabDep.size() > 0}">
                    <c:forEach items="${msqlSameNmTabDep}" var="list">
                        <tr align="left">
                            <td>
                                ${list.dependency}
                            </td>
                            <td>
                                ${list.depServer}
                            </td>
                            <td>
                                ${list.depSchema}
                            </td>
                            <td>
                                ${list.depObjType}
                            </td>
                            <td>
                                ${list.depObjName}
                            </td>
                        </tr>
                    </c:forEach>
                </c:if>
                <c:if test="${sensitiveObj.size() > 0}">
                    <c:forEach items="${sensitiveObj}" var="list">
                        <tr align="left">
                            <td>
                                ${list.dependency}
                            </td>
                            <td>
                                ${list.depServer}
                            </td>
                            <td>
                                ${list.depSchema}
                            </td>
                            <td>
                                ${list.depObjType}
                            </td>
                            <td>
                                ${list.depObjName}
                            </td>
                        </tr>
                    </c:forEach>
                </c:if>
                <c:if test="${sensitiveKeyword.size() > 0}">
                    <c:forEach items="${sensitiveKeyword}" var="list">
                        <tr align="left">
                            <td>
                                ${list.dependency}
                            </td>
                            <td>
                                ${list.depServer}
                            </td>
                            <td>
                                ${list.depSchema}
                            </td>
                            <td>
                                ${list.depObjType}
                            </td>
                            <td>
                                ${list.depObjName} - ( ${list.depColName} )
                            </td>
                        </tr>
                    </c:forEach>
                </c:if>
                <c:if test="${oraDep.size() <= 0 and oraLogDep.size() <= 0 and oraDUDep.size() <= 0 or msqlLogDep.size() <= 0 and msqlViewDep.size() <= 0 and msqlInfoBrightDep.size() <= 0 and MViewDep.size() <= 0 and msqlSameNmTabDep.size() <= 0 and sensitiveObj.size() <= 0 and sensitiveKeyword.size() <= 0}">
                    <tr><td>No Dependencies Found</td></tr>
                </c:if>
            </table>
        </div>
    </c:when>
    <c:when test="${process eq 'objDefination'}">
        <c:set var="chk" value="0"></c:set>
        <c:if test="${objDef ne null and objDef ne ''}">
            <textarea id="txtaOtherObjDetail" name="txtaOtherObjDetail" rows="6" cols="8" style="width: 493px;height:130px">${objDef}</textarea>
        </c:if>
        <c:if test="${objDef eq null or objDef eq null}">
            <c:set var="chk" value="${chk + 1}"></c:set>
        </c:if>
        <c:if test="${chk ge 1}">
            No Records Found
        </c:if>
    </c:when>
    <c:when test="${process eq 'seqDefination'}">
        <div class="menu_caption_bg cursor-pointer" onclick="javascript:hide_menu('show_hide4','loadSeq', 'nav_show','nav_hide')" id="divCaption3" >
            <div class="menu_caption_text">Sequences</div>
            <span><a href="javascript:void(0)" name="show_hide4" id="show_hide4" class="nav_hide"></a></span>
        </div>
        <div id="loadSeq" class="report-main-content report_content" align="center" style="display: block; width: 100%;">
            <table width="100%" id="rptSeqGrid" class="tbl_h4_bg1 rpt_tbl" cellpadding="0" cellspacing="0">
                <tr class="report_caption" align="center">
                    <td>Sequence Properties</td>
                    <td>Sequence Value</td>                            
                </tr>                        
                <tr align="center">
                    <td>MinValue</td>
                    <td>${minValue}</td>
                </tr>
                <tr align="center">
                    <td>MaxValue</td>
                    <td>${maxValue}</td>                                
                </tr>
                <tr align="center">
                    <td>IncrementBy</td>
                    <td>${incrementBy}</td>                                
                </tr>
                <tr align="center">
                    <td>Cycle</td>
                    <td>${cycleFlag}</td>                                
                </tr>
                <tr align="center">
                    <td>Order</td>
                    <td>${orderFlag}</td>                                
                </tr>
                <tr align="center">
                    <td>Cache</td>
                    <td>${cacheSize}</td>                                
                </tr>
                <tr align="center">
                    <td>LastNumber</td>
                    <td>${lastNumber}</td>                                
                </tr>                                           
            </table>
        </div>
    </c:when>
</c:choose>