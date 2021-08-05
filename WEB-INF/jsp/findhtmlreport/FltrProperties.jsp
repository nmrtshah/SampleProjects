<%@taglib  prefix="f" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div>
    <input type="checkbox" value="Code" id="fltrchkCode" onchange="javascript:showFltrCodeProperty()"/>Code Properties
    <div id ="fltrCodeProperty" style="vertical-align: top;display: none;height: 150px;overflow-y:auto;overflow-x: hidden" >
        <table>
            <th>Properties</th>
            <tr>
                <td>
                    id
                </td>
                <td>
                    <input type="text" style="width: 100px" id="fltrTxtId" name="fltrTxtId"/>
                </td>
                <td>
                    name
                </td>
                <td>
                    <input type="text" id="fltrTxtName" name="fltrTxtName" style="width: 100px"/>
                </td>
            </tr>
            <tr id="fltrStyleSize">
                <td>
                    style
                </td>
                <td>
                    <input type="text" id="fltrTxtStyle" style="width: 100px" name="fltrTxtStyle"/>
                </td>

                <td>
                    size
                </td>
                <td>
                    <input type="text" id="fltrTxtSize" style="width: 100px" name="fltrTxtSize"/>
                </td>
            </tr>
            <tr>
                <td id="fltrClassLbl">
                    class
                </td>
                <td id="fltrClassVal">
                    <input type="text" id="fltrTxtClass" style="width: 100px" name="fltrTxtClass"/>
                </td>
                <td id="fltrMultipleLbl" style="display: none">
                    multiple
                </td>
                <td id="fltrMultipleVal" style="display: none">
                    <input type="radio" id="fltrRbtnMultipleT" style="width: 50px" name="rbtnfmultiple" value="True"/>True
                    <input type="radio" id="fltrRbtnMultipleF" style="width: 50px" name="rbtnfmultiple" value="False"/>False
                </td>
                <td id="fltrCheckedLbl" style="display: none">
                    checked
                </td>
                <td id="fltrCheckedVal" style="display: none">
                    <input type="radio" id="fltrRbtnCheckedT" style="width: 50px" name="rbtnfchecked" value="True"/>True
                    <input type="radio" id="fltrRbtnCheckedF" style="width: 50px" name="rbtnfchecked" value="False"/>False
                </td>
            </tr>         
            <tr id="fltrLenReadOnly" style="display:none">
                <td>
                    maxlength
                </td>
                <td>
                    <input type="text" id="fltrTxtMaxLen" style="width: 100px"  name="fltrTxtMaxLen"/>
                </td>
                <td>
                    readonly
                </td>
                <td>
                    <input type="radio" id="fltrRbtnReadonlyT" style="width: 50px" name="rbtnfreadonly" value="True"/>True
                    <input type="radio" id="fltrRbtnReadonlyF" style="width: 50px" name="rbtnfreadonly" value="False"/>False
                </td>
            </tr>
            <tr id="fltrAlignVal" style="display: none">
                <td>
                    align
                </td>
                <td>
                    <select name="fltrCmbAlign" style="width: 100px" class="custom_combo_low" id="fltrCmbAlign">
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
                    <input type="text" id="fltrTxtValue" style="width: 100px" name="fltrTxtValue"/>
                </td>
            </tr>
            <tr id="fltrRowCol" style="display: none">
                <td>
                    rows
                </td>
                <td>
                    <input type="text" id="fltrTxtRows" style="width: 100px" name="fltrTxtRows"/>
                </td>
                <td>
                    cols
                </td>
                <td>
                    <input type="text" id="fltrTxtCols" style="width: 100px" name="fltrTxtCols"/>
                </td>
            </tr>
            <tr id="fltrRdoProp" style="display: none">
                <td>
                    <sup style="color: red">*</sup>No.Of Radios
                </td>
                <td>
                    <input type="text" id="fltrTxtTotalRdo" name="fltrTxtTotalRdo" style="width: 100px" />
                </td>
            </tr>
            <tr id="fltrRdoCaptions" style="display: none">
                <td colspan="2">
                    <sup style="color: red">*</sup>Captions: e.g. first/second/third
                </td>
                <td colspan="2">
                    <input type="text" id="fltrTxtRdoCaption" name="fltrTxtRdoCaption" style="width: 150px" />
                </td>
            </tr>
            <tr id="fltrRdoValues" style="display: none">
                <td colspan="2">
                    <sup style="color: red">*</sup>values: e.g. first/second/third
                </td>
                <td colspan="2">
                    <input type="text" id="fltrTxtRdoVal" name="fltrTxtRdoVal" style="width: 150px" />
                </td>
            </tr>
            <tr id="fltrRdoDefaultValue" style="display: none">
                <td colspan="3">
                    Default Selection: e.g. Enter 2 if 'second' is selected
                </td>
                <td>
                    <input type="text" id="fltrTxtRdoDefVal" name="fltrTxtRdoDefVal" style="width: 100px" />
                </td>
            </tr>
            <tbody id="fltrComboFill" style="display:none">
                <tr>
                    <td>
                        DataSource                        
                    </td>
                    <td>
                        <input type="checkbox" id="fltrchkDataSource" name="fltrchkDataSource" style="width: 20px" onchange="javascript:showFltrComboFill()" >
                    </td>
                    <td>
                        Dependent                        
                    </td>
                    <td>
                        <input type="checkbox" id="fltrchkForDependentCombo" name="fltrchkForDependentCombo" style="width: 20px" onchange="javascript:showFltrForDependentCombo()" >
                    </td>
                </tr>
                <tr id="fltrIndepSource" style="display: none">
                    <td>
                        <input type="radio" id="fltrRdoSrcStatic" name="fltrCmbSource" style="width: 10px" onchange="showFltrSrcQuery()" >Static
                    </td>
                    <td>
                        <input type="radio" id="fltrRdoSrcQuery" name="fltrCmbSource" style="width: 10px" onchange="showFltrSrcQuery()" >Query
                    </td>
                    <td>
                        <input type="radio" id="fltrRdoSrcWS" name="fltrCmbSource" style="width: 10px" onchange="showFltrSrcQuery()">Web Service             
                    </td>
                    <td>
                        <input type="radio" id="fltrRdoSrcCommonCmb" name="fltrCmbSource" style="width: 10px" onchange="showFltrSrcQuery()">Common Combo
                    </td>
                </tr>
                <tr id="fltrIndepSrcStatic" style="display: none">
                    <td>
                        <sup style="color: red">*</sup>Static
                    </td>
                    <td colspan="4">
                        <div><sup style="color: red">*</sup>DataFormat[value1,text1;value2,text2]</div>
                        <textarea id="fltrTxtSrcStatic" name="fltrTxtSrcStatic" rows="2" cols="1" style="width: 220px"></textarea>                        
                    </td>                    
                </tr>
                <tr id="fltrIndepSrcQuery" style="display: none">
                    <td>   
                        <sup style="color: red">*</sup>Query
                    </td>
                    <td colspan="4">
                        <div id="msgDiv"><sup style="color: red">*</sup>Query result must contain only two columns<br>[ COL1->option value, COL2->option text ]</div>
                        <textarea id="fltrTxtSrcQuery" name="fltrTxtSrcQuery" rows="2" cols="1" style="width: 220px"></textarea>
                        <div id="cmbFillQueryResult" style="color: #9A9A9A"></div>
                    </td>
                </tr>
                <tr id="fltrIndepSrcCommonCmb" style="display: none">
                    <td>
                        <sup style="color: red">*</sup>Select
                    </td>
                    <td colspan="4">
                        <select id="fltrCmbCommonQuery" name="fltrCmbCommonQuery">
                            <option value="-1" selected>-- Select --</option>
                            <option value="AMC">AMC</option>  
                            <option value="bank">Bank</option>   
                            <option value="exchange">Exchange</option>   
                            <option value="firm">Firm</option>   
                            <option value="function">Function</option>
                            <option value="location">Location</option>   
                            <option value="RNT">RNT</option>  
                            <option value="scheme">Scheme</option>  
                            <option value="segment">Segment</option>   
                        </select>
                    </td>                
                </tr>
            <tbody id="fltrIndepSrcWS" style="display: none">
                <tr>
                    <td>
                        <sup style="color: red">*</sup>wsdl URL :
                    </td>
                    <td colspan="4">
                        <input type="text" id="fltrTxtWsdlUrl" name="fltrTxtWsdlUrl" onblur="getWsdlMethods('fltr')" />
                    </td>                    
                </tr>
                <tr>
                    <td>
                        <sup style="color: red">*</sup>Method :
                    </td>
                    <td colspan="4">
                        <select id="fltrCmbWsMethod" name="fltrCmbWsMethod" onchange="javascript: getWsMethodParams('fltr')">
                            <option value="-1">-- Select Method --</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>
                        <sup style="color: red">*</sup>combo value :
                    </td>
                    <td>
                        <input type="text" id="fltrWSCmbValue" name="fltrWSCmbValue" style="width: 80px">
                    </td>
                    <td>
                        <sup style="color: red">*</sup>combo text :
                    </td>
                    <td>
                        <input type="text" id="fltrWSCmbText" name="fltrWSCmbText" style="width: 80px">
                    </td>
                </tr>
                <tr>
                    <td id="fltrtdWsdl" style="display: none">
                    </td>
                </tr>
            </tbody>
            <tr id="fltrDepCombo" style="display: none;">
                <td>
                    <sup style="color: red">*</sup>Dependent Combo 
                </td>
                <td colspan="4">
                    <select id="fltrCmbDependent" name="fltrCmbDependent">
                        <option value="-1">-- Select Combo --</option>
                    </select>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
    <br>
    <input type="checkbox" value="SRS" id="chkfSRS" onchange="javascript:showFltrSRSProperty()"/>SRS Properties
    <div id="SRSfProperty" style="vertical-align: top;display: none">
        <table>
            <tr id="fltrtrValidation">
                <td width="50%" align="right">
                    Validation:
                </td>
                <td width="50%">
                    <select name="fltrCmbValidation" id="fltrCmbValidation" class="custom_combo_mid" style="width: 150px" onchange="javascript:showChkMndtry('fltr')">
                        <option value="-1">--Select Validation--</option>                        
                    </select>
                </td>
            </tr>
            <tr id="fltrtrMndtry" style="display: none">
                <td align="right">
                    Mandatory :
                </td>
                <td>
                    <input type="checkbox" id="fltrchkMandatory" name="fltrchkMandatory" />
                </td>
            </tr>
            <tr>
                <td align="right">
                    Remarks :
                </td>
                <td>
                    <input type="text" name="fltrTxtRemarks" id="fltrTxtRemarks" class="text" style="width: 150px">
                </td>
            </tr>
        </table>
    </div>
</div>