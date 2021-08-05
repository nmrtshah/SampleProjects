<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<form action="" id="scriptgenAddform" name="scriptgenAddform" method="POST">  
    <table align="center" id="mainformtable" cellpadding="0" cellspacing="0" width="1024">
        <tbody>
            <tr>
                <td class="tdleftdata" colspan="100">
                    <div id="genDetail" style="color:teal;font-size: 14px"><b>&nbsp;&nbsp;&nbsp;View Detail</b></div>
                </td>
            </tr>
            <tr>
                <td class="tdleftdata">
                    <table id="miscTable" width="100%">
                        <tbody>                                                    
                            <tr>
                                <td class="tdleft"><sup style="color: red">**</sup>Project Name:</td>
                                <td class="tdleftdata">
                                    <select name="prjID" id="prjID" onchange="javascript:onChangeProject(this.value)">
                                        <option value="-1">-- Select Project --</option>
                                        <c:forEach items="${projectList}" var="projectItem">
                                            <option value="${projectItem.PRJ_ID}">${projectItem.PRJ_NAME}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td class="tdleft">Module Name:</td>
                                <td class="tdleftdata" id="modulelist">
                                    <select name="moduleID" id="moduleID">
                                        <option value="-1">--Select Module Name--</option>
                                    </select>
                                </td>
                            </tr>                            
                            <tr>
                                <td class="tdleft"><sup style="color: red">**</sup>Creation Date:</td>
                                <td class="tdleftdata">
                                   From:<input type="text" value="${dateString}" id="fromDate" name="fromDate" 
                                               style="width: 100px"  />&nbsp;&nbsp;                                    
                                    To:<input type="text" value="${dateString}" id="toDate" name="toDate"  style="width: 100px" />           
                                </td>
                            </tr>
                            <tr>
                                <td class="tdleftdata" colspan="100" style="font-weight:lighter;color: teal">
                                   <sup style="color: red">**</sup> <i>Either Project Name OR Creation Date is mandatory.</i>
                                </td>
                            </tr>
                            <tr>
                                <td></td>
                                <td>
                                    <input type="button" id="btnView" name="btnView" value="View" onclick="if(dateValidationtest()){getGenClassList();}"/>      
                                    <input type="button" id="btnReset" name="btnReset" value="Reset" onclick="showTab('View')"/>
                                </td>
                            </tr>                              
                        </tbody>                        
                    </table>
                </td>
            </tr>            
            <tr>
        <table id="tblGenClassList" width="1024" align="center">            
        </table>
        </tr>
        </tbody>
    </table>    
</form>
<div id="divTempData" style="display: none"></div>