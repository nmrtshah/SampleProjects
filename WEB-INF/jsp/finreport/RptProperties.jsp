<%@taglib  prefix="f" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div>
    <input type="checkbox" value="Code" id="rptchkCode" onchange="javascript:showRptCodeProperty()"/>Code Properties
    <div id ="rptCodeProperty" style="vertical-align: top;display: none" >
        <table>
            <th>Properties</th>
            <tr>
                <td>
                    id
                </td>
                <td>
                    <input type="text" style="width: 100px" id="rptTxtId" name="rptTxtId"/>
                </td>
                <td>
                    name
                </td>
                <td>
                    <input type="text" id="rptTxtName" name="rptTxtName" style="width: 100px"/>
                </td>
            </tr>
            <tr id="rptStyleSize">
                <td>
                    style
                </td>
                <td>
                    <input type="text" id="rptTxtStyle" style="width: 100px" name="rptTxtStyle"/>
                </td>

                <td>
                    size
                </td>
                <td>
                    <input type="text" id="rptTxtSize" style="width: 100px" name="rptTxtSize"/>
                </td>
            </tr>
            <tr>
                <td id="rptClassLbl">
                    class
                </td>
                <td id="rptClassVal">
                    <input type="text" id="rptTxtClass" style="width: 100px" name="rptTxtClass"/>
                </td>
                <td id="rptMultipleLbl" style="display: none">
                    multiple
                </td>
                <td id="rptMultipleVal" style="display: none">
                    <input type="radio" id="rptRbtnMultipleT" style="width: 50px" name="rbtnmultiple" value="True"/>True
                    <input type="radio" id="rptRbtnMultipleF" style="width: 50px" name="rbtnmultiple" value="False"/>False
                </td>
                <td id="rptCheckedLbl" style="display: none">
                    checked
                </td>
                <td id="rptCheckedVal" style="display: none">
                    <input type="radio" id="rptRbtnCheckedT" style="width: 50px" name="rbtnchecked" value="True"/>True
                    <input type="radio" id="rptRbtnCheckedF" style="width: 50px" name="rbtnchecked" value="False"/>False
                </td>
            </tr>
            <tr id="rptLenReadOnly" style="display:none">
                <td>
                    maxlength
                </td>
                <td>
                    <input type="text" id="rptTxtMaxLen" style="width: 100px"  name="rptTxtMaxLen"/>
                </td>
                <td>
                    readonly
                </td>
                <td>
                    <input type="radio" id="rptRbtnReadonlyT" style="width: 50px" name="rbtnreadonly" value="True"/>True
                    <input type="radio" id="rptRbtnReadonlyF" style="width: 50px" name="rbtnreadonly" value="False"/>False
                </td>
            </tr>
            <tr id="rptAlignVal" style="display: none">
                <td>
                    align
                </td>
                <td>
                    <select name="rptCmbAlign" class="custom_combo_low" style="width: 100px" id="rptCmbAlign">
                        <option value="-1">--Select--</option>
                        <option value="left">left</option>
                        <option value="center">center</option>
                        <option value="right">right</option>
                    </select>
                </td>
                <td>
                    value
                </td>
                <td>
                    <input type="text" id="rptTxtValue" style="width: 100px" name="rptTxtValue"/>
                </td>
            </tr>
            <tr id="rptRowCol" style="display: none">
                <td>
                    rows
                </td>
                <td>
                    <input type="text" id="rptTxtRows" style="width: 100px" name="rptTxtRows"/>
                </td>
                <td>
                    cols
                </td>
                <td>
                    <input type="text" id="rptTxtCols" style="width: 100px" name="rptTxtCols"/>
                </td>
            </tr>
            <tr id="rptRdoProp" style="display: none">
                <td>
                    <sup style="color: red">*</sup>No.Of Radios
                </td>
                <td>
                    <input type="text" id="rptTxtTotalRdo" name="rptTxtTotalRdo" style="width: 100px" />
                </td>
            </tr>
            <tr id="rptRdoValues" style="display: none">
                <td colspan="2">
                    <sup style="color: red">*</sup>values: e.g. first/second/third
                </td>
                <td colspan="2">
                    <input type="text" id="rptTxtRdoVal" name="rptTxtRdoVal" style="width: 150px" />
                </td>
            </tr>
            <tr id="rptRdoDefaultValue" style="display: none">
                <td colspan="3">
                    Default Selection: e.g. Enter 2 if 'second' is selected
                </td>
                <td>
                    <input type="text" id="rptTxtRdoDefVal" name="rptTxtRdoDefVal" style="width: 100px" />
                </td>
            </tr>
        </table>
    </div>
    <br>
    <input type="checkbox" value="SRS" id="chkSRS" onchange="javascript:showRptSRSProperty()"/>SRS Properties
    <div id="SRSProperty" style="vertical-align: top;display: none">
        <table>
            <tr id="rpttrValidation">
                <td width="50%" align="right">
                    Validation:
                </td>
                <td width="50%">
                    <select name="rptCmbValidation" id="rptCmbValidation" class="custom_combo_mid" style="width: 150px" onchange="javascript:showChkMndtry('rpt')">
                        <option value="-1">--Select Validation--</option>                        
                    </select>                    
                </td>
            </tr>           
            <tr id="rpttrMndtry" style="display: none">
                <td align="right">
                    Mandatory :
                </td>
                <td>
                    <input type="checkbox" id="rptchkMandatory" name="rptchkMandatory" />
                </td>
            </tr>
            <tr>
                <td align="right">
                    Remarks :
                </td>
                <td>
                    <input type="text" name="rptTxtRemarks" id="rptTxtRemarks" class="text" style="width: 150px">
                </td>
            </tr>
        </table>
    </div>
</div>
