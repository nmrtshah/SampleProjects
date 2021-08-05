<%-- 
    Document   : AddTabProperties
    Created on : Feb 22, 2012, 12:01:11 PM
    Author     : Sonam Patel
--%>

<div>
    <input type="checkbox" value="Code" id="chkAddCode" name="chkAddCode" onchange="javascript:showCodeProperty('Add')" />Code Properties
    <div id ="AddCodeProperty" style="vertical-align: top; display: none; height: 200px; overflow-x: hidden; overflow-y: auto">
        <table style="width: 90%">
            <th>Properties</th>
            <tr>
                <td style="width: 25%">
                    <sup style="color: red">*</sup>id
                </td>
                <td style="width: 25%">
                    <input type="text" id="txtAddId" name="txtAddId" style="width: 100px" />
                </td>
                <td style="width: 25%">
                    <sup style="color: red">*</sup>name
                </td>
                <td style="width: 25%">
                    <input type="text" id="txtAddName" name="txtAddName" style="width: 100px" />
                </td>
            </tr>
            <tr id="trAddStyleSizeProp" style="display: none">
                <td>
                    style
                </td>
                <td>
                    <input type="text" id="txtAddStyle" name="txtAddStyle" style="width: 100px" />
                </td>
                <td id="AddSize1">
                    size
                </td>
                <td id="AddSize2">
                    <input type="text" id="txtAddSize" name="txtAddSize" style="width: 100px" />
                </td>
            </tr>
            <tr id="trAddClassProp" style="display: none">
                <td>
                    class
                </td>
                <td>
                    <input type="text" id="txtAddClass" name="txtAddClass" style="width: 100px" />
                </td>
                <td id="tdAddOnchange1" style="display: none">
                    onchange
                </td>
                <td id="tdAddOnchange2" style="display: none">
                    <select type="text" id="cmbAddOnchange" name="cmbAddOnchange" class="custom_combo_low" onchange="javascript: disableFillCombo('Add');">
                        <option value="-1" selected>-- Select --</option>
                    </select>
                </td>
            </tr>
            <tr id="trAddCmbProp" style="display: none">
                <td>
                    multiple
                </td>
                <td>
                    <input type="radio" id="rbtnAddMultipleTrue" name="rbtnAddMultiple" style="width: 50px" />True
                    <input type="radio" id="rbtnAddMultipleFalse" name="rbtnAddMultiple" style="width: 50px" />False
                </td>
            </tr>
            <tr id="trAddIndepSource" style="display: none">
                <td align="right">
                    <input type="checkbox" id="chkAddSrc" name="chkAddSrc" onchange="showSrc('Add')" />Data Source :
                </td>
                <td id="tdAddSrc" colspan="3" style="display: none">
                    <input type="radio" id="rdoAddSrcStatic" name="rdoAddDataSrc" onchange="showSrcQuery('Add')" />Static Data
                    <input type="radio" id="rdoAddSrcQuery" name="rdoAddDataSrc" onchange="showSrcQuery('Add')" />Query
                    <input type="radio" id="rdoAddSrcWS" name="rdoAddDataSrc" onchange="showSrcQuery('Add')" />Web Service
                    <input type="radio" id="rdoAddSrcCommonCmb" name="rdoAddDataSrc" onchange="showSrcQuery('Add')" />Common Combo
                </td>
            </tr>
            <tr id="trAddIndepSrcStatic" style="display: none">
                <td>
                    <sup style="color: red">*</sup>Static Data :
                </td>
                <td colspan="3">
                    Data Format<br>
                    [ option value1,option text1;option value2,option text2 ]
                    <div id="AddStaticResult" style="color: blue; width: 250px"></div>
                    <textarea id="txtAddSrcStatic" name="txtAddSrcStatic" rows="4" cols="5" onblur="checkDataFormat('Add')"></textarea>
                </td>
            </tr>
            <tr id="trAddIndepSrcQuery" style="display: none">
                <td>
                    <sup style="color: red">*</sup>Query :
                </td>
                <td colspan="3">
                    Query Result must contain only two columns<br>
                    [ COL1->option value, COL2->option text ]
                    <div id="AddQueryResult" style="color: blue; width: 250px"></div>
                    <textarea id="txtAddSrcQuery" name="txtAddSrcQuery" rows="4" cols="5" onblur="checkQuery('Add')"></textarea>
                </td>
            </tr>
            <tr id="trAddIndepSrcCommonCmb" style="display: none">
                <td>
                    <sup style="color: red">*</sup>Select :
                </td>
                <td colspan="3">
                    <select id="cmbAddCommonQuery" name="cmbAddCommonQuery">
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
            <tbody id="tbdAddWS" style="display: none">
                <tr>
                    <td>
                        <sup style="color: red">*</sup>wsdl URL :
                    </td>
                    <td colspan="3">
                        <input type="text" id="txtAddWsdlUrl" name="txtAddWsdlUrl" onblur="getWsdlMethods('Add')" />
                    </td>
                </tr>
                <tr>
                    <td>
                        <sup style="color: red">*</sup>Method :
                    </td>
                    <td colspan="3">
                        <select id="cmbAddWsMethod" name="cmbAddWsMethod" onchange="javascript: getWsMethodParams('Add')">
                            <option value="-1">-- Select Method --</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>
                        <sup style="color: red">*</sup>combo value :
                    </td>
                    <td>
                        <input type="text" id="txtAddWsCmbValue" name="txtAddWsCmbValue" style="width: 100px" />
                    </td>
                    <td>
                        <sup style="color: red">*</sup>combo text :
                    </td>
                    <td>
                        <input type="text" id="txtAddWsCmbText" name="txtAddWsCmbText" style="width: 100px" />
                    </td>
                </tr>
                <tr>
                    <td id="tdAddWsdl" style="display: none">
                    </td>
                </tr>
            </tbody>
            <tr id="trAddChkProp" style="display: none">
                <td>
                    checked
                </td>
                <td>
                    <input type="radio" id="rbtnAddCheckedTrue" name="rbtnAddChecked" style="width: 50px" value="True" />True
                    <input type="radio" id="rbtnAddCheckedFalse" name="rbtnAddChecked" style="width: 50px" value="False" />False
                </td>
            </tr>
            <tr id="trAddRdoProp" style="display: none">
                <td id="AddTotalRdo1">
                    <sup style="color: red">*</sup>No.Of Radios
                </td>
                <td id="AddTotalRdo2">
                    <input type="text" id="txtAddTotalRdo" name="txtAddTotalRdo" style="width: 100px" />
                </td>
            </tr>
            <tr id="trAddRdoCaptions" style="display: none">
                <td colspan="2">
                    <sup style="color: red">*</sup>captions: e.g. first/second/third
                </td>
                <td colspan="2">
                    <input type="text" id="txtAddRdoCap" name="txtAddRdoCap" style="width: 200px" />
                </td>
            </tr>
            <tr id="trAddRdoValues" style="display: none">
                <td colspan="2">
                    <sup style="color: red">*</sup>values: e.g. first/second/third
                </td>
                <td colspan="2">
                    <input type="text" id="txtAddRdoVal" name="txtAddRdoVal" style="width: 200px" />
                </td>
            </tr>
            <tr id="trAddRdoDefaultValue" style="display: none">
                <td colspan="3">
                    Default Selection: e.g. Enter 2 if 'second' is selected
                </td>
                <td>
                    <input type="text" id="txtAddRdoDefVal" name="txtAddRdoDefVal" style="width: 100px" />
                </td>
            </tr>
            <tr id="trAddChkValue" style="display: none">
                <td>
                    <sup style="color: red">*</sup>Value Format
                </td>
                <td colspan="3">
                    <input type="radio" id="rbtnAddCheckedOneZero" name="rbtnAddValue" style="width: 25px" />1-0
                    <input type="radio" id="rbtnAddCheckedYesNo" name="rbtnAddValue" style="width: 25px" />Yes-No
                    <input type="radio" id="rbtnAddCheckedYN" name="rbtnAddValue" style="width: 25px" />Y-N
                    <input type="radio" id="rbtnAddCheckedTF" name="rbtnAddValue" style="width: 25px" />TRUE-FALSE
                </td>
            </tr>
            <tr id="trAddTxt_LabelProp" style="display:none">
                <td>
                    maxlength
                </td>
                <td>
                    <input type="text" id="txtAddMaxLength" name="txtAddMaxLength" style="width: 100px" />
                </td>
                <td>
                    readonly
                </td>
                <td>
                    <input type="radio" id="rbtnAddReadonlyTrue" name="rbtnAddReadonly" style="width: 50px" value="True" />True
                    <input type="radio" id="rbtnAddReadonlyFalse" name="rbtnAddReadonly" style="width: 50px" value="False" />False
                </td>
            </tr>
            <tr id="trAddAlignProp" style="display: none">
                <td>
                    align
                </td>
                <td>
                    <select id="cmbAddAlign" name="cmbAddAlign" class="custom_combo_low">
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
                    <input type="text" id="txtAddValue" name="txtAddValue" style="width: 100px" />
                </td>
            </tr>
            <tr id="trAddTextAreaProp" style="display: none">
                <td>
                    rows
                </td>
                <td>
                    <input type="text" id="txtAddRows" name="txtAddRows" style="width: 100px" />
                </td>
                <td>
                    cols
                </td>
                <td>
                    <input type="text" id="txtAddCols" name="txtAddCols" style="width: 100px" />
                </td>
            </tr>
            <tr id="trAddSizeType" style="display: none">
                <td>
                    maxsize (kb)
                </td>
                <td>
                    <input type="text" id="txtAddMaxsize" name="txtAddMaxsize" style="width: 100px" />
                </td>
                <td>
                    <sup style="color: red">*</sup>type (e.g. txt,pdf)
                </td>
                <td>
                    <input type="text" id="txtAddType" name="txtAddType" style="width: 100px" />
                </td>
            </tr>
            <tr id="trAddFilesEle" style="display: none">
                <td>
                    maxfiles
                </td>
                <td>
                    <input type="text" id="txtAddMaxfiles" name="txtAddMaxfiles" style="width: 100px" />
                </td>
                <td>
                    <sup style="color: red">*</sup>elementname
                </td>
                <td>
                    <input type="text" id="txtAddEleName" name="txtAddEleName" style="width: 100px" />
                </td>
            </tr>
            <tr id="trAddDispTxt" style="display: none">
                <td>
                    value
                </td>
                <td>
                    <input type="text" id="txtAddDispTxt" name="txtAddDispTxt" style="width: 100px" />
                </td>
                <td>
                    onremovecallback
                </td>
                <td>
                    <input type="text" id="txtAddOnremoveCall" name="txtAddOnremoveCall" style="width: 100px" />
                </td>
            </tr>
        </table>
    </div>
    <br>
    <input type="checkbox" value="SRS" id="chkAddSRS" name="chkAddSRS" onchange="javascript:showSRSProperty('Add')" />SRS Properties
    <div id="AddSRSProperty" style="vertical-align: top;display: none">
        <table>
            <tr>
                <td width="50%" align="right">
                    Validation :
                </td>
                <td width="50%">
                    <select id="cmbAddValidation" name="cmbAddValidation" class="custom_combo_mid"
                            onchange="javascript:showChkMndtry('Add')" >
                    </select>
                </td>
            </tr>
            <tr id="trAddMndtry">
                <td align="right">
                    Mandatory :
                </td>
                <td>
                    <input type="checkbox" id="chkAddMandatory" name="chkAddMandatory" />
                </td>
            </tr>
            <tr>
                <td align="right">
                    Remarks :
                </td>
                <td>
                    <input type="text" id="txtAddRemarks" name="txtAddRemarks" class="text" style="width: 150px">
                </td>
            </tr>
        </table>
    </div>
</div>
