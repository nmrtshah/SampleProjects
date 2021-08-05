<%-- 
    Document   : EditTabProperties
    Created on : Feb 22, 2012, 12:01:11 PM
    Author     : Sonam Patel
--%>

<div>
    <input type="checkbox" value="Code" id="chkEditCode" name="chkEditCode" onchange="javascript:showCodeProperty('Edit')" />Code Properties
    <div id ="EditCodeProperty" style="vertical-align: top; display: none; height: 200px; overflow-x: hidden; overflow-y: auto">
        <table style="width: 90%">
            <th>Properties</th>
            <tr>
                <td style="width: 25%">
                    <sup style="color: red">*</sup>id
                </td>
                <td style="width: 25%">
                    <input type="text" id="txtEditId" name="txtEditId" style="width: 100px" />
                </td>
                <td style="width: 25%">
                    <sup style="color: red">*</sup>name
                </td>
                <td style="width: 25%">
                    <input type="text" id="txtEditName" name="txtEditName" style="width: 100px" />
                </td>
            </tr>
            <tr id="trEditStyleSizeProp" style="display: none">
                <td>
                    style
                </td>
                <td>
                    <input type="text" id="txtEditStyle" name="txtEditStyle" style="width: 100px" />
                </td>
                <td id="EditSize1">
                    size
                </td>
                <td id="EditSize2">
                    <input type="text" id="txtEditSize" name="txtEditSize" style="width: 100px" />
                </td>
            </tr>
            <tr id="trEditClassProp" style="display: none">
                <td>
                    class
                </td>
                <td>
                    <input type="text" id="txtEditClass" name="txtEditClass" style="width: 100px" />
                </td>
                <td id="tdEditOnchange1" style="display: none">
                    onchange
                </td>
                <td id="tdEditOnchange2" style="display: none">
                    <select type="text" id="cmbEditOnchange" name="cmbEditOnchange" class="custom_combo_low" onchange="javascript: disableFillCombo('Edit');">
                        <option value="-1" selected>-- Select --</option>
                    </select>
                </td>
            </tr>
            <tr id="trEditCmbProp" style="display: none">
                <td>
                    multiple
                </td>
                <td>
                    <input type="radio" id="rbtnEditMultipleTrue" name="rbtnEditMultiple" style="width: 50px" />True
                    <input type="radio" id="rbtnEditMultipleFalse" name="rbtnEditMultiple" style="width: 50px" />False
                </td>
            </tr>
            <tr id="trEditIndepSource" style="display: none">
                <td align="right">
                    <input type="checkbox" id="chkEditSrc" name="chkEditSrc" onchange="showSrc('Edit')" />Data Source :
                </td>
                <td id="tdEditSrc" colspan="3" style="display: none">
                    <input type="radio" id="rdoEditSrcStatic" name="rdoEditDataSrc" onchange="showSrcQuery('Edit')" />Static Data
                    <input type="radio" id="rdoEditSrcQuery" name="rdoEditDataSrc" onchange="showSrcQuery('Edit')" />Query
                    <input type="radio" id="rdoEditSrcWS" name="rdoEditDataSrc" onchange="showSrcQuery('Edit')" />Web Service
                    <input type="radio" id="rdoEditSrcCommonCmb" name="rdoEditDataSrc" onchange="showSrcQuery('Edit')" />Common Combo
                </td>
            </tr>
            <tr id="trEditIndepSrcStatic" style="display: none">
                <td>
                    <sup style="color: red">*</sup>Static Data :
                </td>
                <td colspan="3">
                    Data Format<br>
                    [ option value1,option text1;option value2,option text2 ]
                    <div id="EditStaticResult" style="color: blue; width: 250px"></div>
                    <textarea id="txtEditSrcStatic" name="txtEditSrcStatic" rows="4" cols="5" onblur="checkDataFormat('Edit')"></textarea>
                </td>
            </tr>
            <tr id="trEditIndepSrcQuery" style="display: none">
                <td>
                    <sup style="color: red">*</sup>Query :
                </td>
                <td colspan="3">
                    Query Result must contain only two columns<br>
                    [ COL1->option value, COL2->option text ]
                    <div id="EditQueryResult" style="color: blue; width: 250px"></div>
                    <textarea id="txtEditSrcQuery" name="txtEditSrcQuery" rows="4" cols="5" onblur="checkQuery('Edit')"></textarea>
                </td>
            </tr>
            <tr id="trEditIndepSrcCommonCmb" style="display: none">
                <td>
                    <sup style="color: red">*</sup>Select :
                </td>
                <td colspan="3">
                    <select id="cmbEditCommonQuery" name="cmbEditCommonQuery">
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
            <tbody id="tbdEditWS" style="display: none">
                <tr>
                    <td>
                        <sup style="color: red">*</sup>wsdl URL :
                    </td>
                    <td colspan="3">
                        <input type="text" id="txtEditWsdlUrl" name="txtEditWsdlUrl" onblur="getWsdlMethods('Edit')" />
                    </td>
                </tr>
                <tr>
                    <td>
                        <sup style="color: red">*</sup>Method :
                    </td>
                    <td colspan="3">
                        <select id="cmbEditWsMethod" name="cmbEditWsMethod" onchange="javascript: getWsMethodParams('Edit')">
                            <option value="-1">-- Select Method --</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>
                        <sup style="color: red">*</sup>combo value :
                    </td>
                    <td>
                        <input type="text" id="txtEditWsCmbValue" name="txtEditWsCmbValue" style="width: 100px" />
                    </td>
                    <td>
                        <sup style="color: red">*</sup>combo text :
                    </td>
                    <td>
                        <input type="text" id="txtEditWsCmbText" name="txtEditWsCmbText" style="width: 100px" />
                    </td>
                </tr>
                <tr>
                    <td id="tdEditWsdl" style="display: none">
                    </td>
                </tr>
            </tbody>
            <tr id="trEditChkProp" style="display: none">
                <td>
                    checked
                </td>
                <td>
                    <input type="radio" id="rbtnEditCheckedTrue" name="rbtnEditChecked" style="width: 50px" value="True" />True
                    <input type="radio" id="rbtnEditCheckedFalse" name="rbtnEditChecked" style="width: 50px" value="False" />False
                </td>
            </tr>
            <tr id="trEditRdoProp" style="display: none">
                <td id="EditTotalRdo1">
                    <sup style="color: red">*</sup>No.Of Radios
                </td>
                <td id="EditTotalRdo2">
                    <input type="text" id="txtEditTotalRdo" name="txtEditTotalRdo" style="width: 100px" />
                </td>
            </tr>
            <tr id="trEditRdoCaptions" style="display: none">
                <td colspan="2">
                    <sup style="color: red">*</sup>captions: e.g. first/second/third
                </td>
                <td colspan="2">
                    <input type="text" id="txtEditRdoCap" name="txtEditRdoCap" style="width: 200px" />
                </td>
            </tr>
            <tr id="trEditRdoValues" style="display: none">
                <td colspan="2">
                    <sup style="color: red">*</sup>values: e.g. first/second/third
                </td>
                <td colspan="2">
                    <input type="text" id="txtEditRdoVal" name="txtEditRdoVal" style="width: 200px" />
                </td>
            </tr>
            <tr id="trEditRdoDefaultValue" style="display: none">
                <td colspan="3">
                    Default Selection: e.g. Enter 2 if 'second' is selected
                </td>
                <td>
                    <input type="text" id="txtEditRdoDefVal" name="txtEditRdoDefVal" style="width: 100px" />
                </td>
            </tr>
            <tr id="trEditChkValue" style="display: none">
                <td>
                    <sup style="color: red">*</sup>Value Format
                </td>
                <td colspan="3">
                    <input type="radio" id="rbtnEditCheckedOneZero" name="rbtnEditValue" style="width: 25px" />1-0
                    <input type="radio" id="rbtnEditCheckedYesNo" name="rbtnEditValue" style="width: 25px" />Yes-No
                    <input type="radio" id="rbtnEditCheckedYN" name="rbtnEditValue" style="width: 25px" />Y-N
                    <input type="radio" id="rbtnEditCheckedTF" name="rbtnEditValue" style="width: 25px" />TRUE-FALSE
                </td>
            </tr>
            <tr id="trEditTxt_LabelProp" style="display:none">
                <td>
                    maxlength
                </td>
                <td>
                    <input type="text" id="txtEditMaxLength" name="txtEditMaxLength" style="width: 100px" />
                </td>
                <td>
                    readonly
                </td>
                <td>
                    <input type="radio" id="rbtnEditReadonlyTrue" name="rbtnEditReadonly" style="width: 50px" value="True" />True
                    <input type="radio" id="rbtnEditReadonlyFalse" name="rbtnEditReadonly" style="width: 50px" value="False" />False
                </td>
            </tr>
            <tr id="trEditAlignProp" style="display: none">
                <td>
                    align
                </td>
                <td>
                    <select id="cmbEditAlign" name="cmbEditAlign" class="custom_combo_low">
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
                    <input type="text" id="txtEditValue" name="txtEditValue" style="width: 100px" />
                </td>
            </tr>
            <tr id="trEditTextAreaProp" style="display: none">
                <td>
                    rows
                </td>
                <td>
                    <input type="text" id="txtEditRows" name="txtEditRows" style="width: 100px" />
                </td>
                <td>
                    cols
                </td>
                <td>
                    <input type="text" id="txtEditCols" name="txtEditCols" style="width: 100px" />
                </td>
            </tr>
            <tr id="trEditSizeType" style="display: none">
                <td>
                    maxsize (kb)
                </td>
                <td>
                    <input type="text" id="txtEditMaxsize" name="txtEditMaxsize" style="width: 100px" />
                </td>
                <td>
                    <sup style="color: red">*</sup>type (e.g. txt,pdf)
                </td>
                <td>
                    <input type="text" id="txtEditType" name="txtEditType" style="width: 100px" />
                </td>
            </tr>
            <tr id="trEditFilesEle" style="display: none">
                <td>
                    maxfiles
                </td>
                <td>
                    <input type="text" id="txtEditMaxfiles" name="txtEditMaxfiles" style="width: 100px" />
                </td>
                <td>
                    <sup style="color: red">*</sup>elementname
                </td>
                <td>
                    <input type="text" id="txtEditEleName" name="txtEditEleName" style="width: 100px" />
                </td>
            </tr>
            <tr id="trEditDispTxt" style="display: none">
                <td>
                    value
                </td>
                <td>
                    <input type="text" id="txtEditDispTxt" name="txtEditDispTxt" style="width: 100px" />
                </td>
                <td>
                    onremovecallback
                </td>
                <td>
                    <input type="text" id="txtEditOnremoveCall" name="txtEditOnremoveCall" style="width: 100px" />
                </td>
            </tr>
        </table>
    </div>
    <br>
    <input type="checkbox" value="SRS" id="chkEditSRS" name="chkEditSRS" onchange="javascript:showSRSProperty('Edit')" />SRS Properties
    <div id="EditSRSProperty" style="vertical-align: top;display: none">
        <table>
            <tr>
                <td width="50%" align="right">
                    Validation :
                </td>
                <td width="50%">
                    <select id="cmbEditValidation" name="cmbEditValidation" class="custom_combo_mid"
                            onchange="javascript:showChkMndtry('Edit')" >
                    </select>
                </td>
            </tr>
            <tr id="trEditMndtry">
                <td align="right">
                    Mandatory :
                </td>
                <td>
                    <input type="checkbox" id="chkEditMandatory" name="chkEditMandatory" />
                </td>
            </tr>
            <tr>
                <td align="right">
                    Remarks :
                </td>
                <td>
                    <input type="text" id="txtEditRemarks" name="txtEditRemarks" class="text" style="width: 150px">
                </td>
            </tr>
        </table>
    </div>
</div>
