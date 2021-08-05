<%-- 
    Document   : DeleteTabProperties
    Created on : Feb 22, 2012, 12:01:11 PM
    Author     : Sonam Patel
--%>

<div>
    <input type="checkbox" value="Code" id="chkDeleteCode" name="chkDeleteCode" onchange="javascript:showCodeProperty('Delete')" />Code Properties
    <div id ="DeleteCodeProperty" style="vertical-align: top; display: none; height: 200px; overflow-x: hidden; overflow-y: auto">
        <table style="width: 90%">
            <th>Properties</th>
            <tr>
                <td style="width: 25%">
                    <sup style="color: red">*</sup>id
                </td>
                <td style="width: 25%">
                    <input type="text" id="txtDeleteId" name="txtDeleteId" style="width: 100px" />
                </td>
                <td style="width: 25%">
                    <sup style="color: red">*</sup>name
                </td>
                <td style="width: 25%">
                    <input type="text" id="txtDeleteName" name="txtDeleteName" style="width: 100px" />
                </td>
            </tr>
            <tr id="trDeleteStyleSizeProp" style="display: none">
                <td>
                    style
                </td>
                <td>
                    <input type="text" id="txtDeleteStyle" name="txtDeleteStyle" style="width: 100px" />
                </td>
                <td id="DeleteSize1">
                    size
                </td>
                <td id="DeleteSize2">
                    <input type="text" id="txtDeleteSize" name="txtDeleteSize" style="width: 100px" />
                </td>
            </tr>
            <tr id="trDeleteClassProp" style="display: none">
                <td>
                    class
                </td>
                <td>
                    <input type="text" id="txtDeleteClass" name="txtDeleteClass" style="width: 100px" />
                </td>
                <td id="tdDeleteOnchange1" style="display: none">
                    onchange
                </td>
                <td id="tdDeleteOnchange2" style="display: none">
                    <select type="text" id="cmbDeleteOnchange" name="cmbDeleteOnchange" class="custom_combo_low" onchange="javascript: disableFillCombo('Delete');">
                        <option value="-1" selected>-- Select --</option>
                    </select>
                </td>
            </tr>
            <tr id="trDeleteCmbProp" style="display: none">
                <td>
                    multiple
                </td>
                <td>
                    <input type="radio" id="rbtnDeleteMultipleTrue" name="rbtnDeleteMultiple" style="width: 50px" />True
                    <input type="radio" id="rbtnDeleteMultipleFalse" name="rbtnDeleteMultiple" style="width: 50px" />False
                </td>
            </tr>
            <tr id="trDeleteIndepSource" style="display: none">
                <td align="right">
                    <input type="checkbox" id="chkDeleteSrc" name="chkDeleteSrc" onchange="showSrc('Delete')" />Data Source :
                </td>
                <td id="tdDeleteSrc" colspan="3" style="display: none">
                    <input type="radio" id="rdoDeleteSrcStatic" name="rdoDeleteDataSrc" onchange="showSrcQuery('Delete')" />Static Data
                    <input type="radio" id="rdoDeleteSrcQuery" name="rdoDeleteDataSrc" onchange="showSrcQuery('Delete')" />Query
                    <input type="radio" id="rdoDeleteSrcWS" name="rdoDeleteDataSrc" onchange="showSrcQuery('Delete')" />Web Service
                    <input type="radio" id="rdoDeleteSrcCommonCmb" name="rdoDeleteDataSrc" onchange="showSrcQuery('Delete')" />Common Combo
                </td>
            </tr>
            <tr id="trDeleteIndepSrcStatic" style="display: none">
                <td>
                    <sup style="color: red">*</sup>Static Data :
                </td>
                <td colspan="3">
                    Data Format<br>
                    [ option value1,option text1;option value2,option text2 ]
                    <div id="DeleteStaticResult" style="color: blue; width: 250px"></div>
                    <textarea id="txtDeleteSrcStatic" name="txtDeleteSrcStatic" rows="4" cols="5" onblur="checkDataFormat('Delete')"></textarea>
                </td>
            </tr>
            <tr id="trDeleteIndepSrcQuery" style="display: none">
                <td>
                    <sup style="color: red">*</sup>Query :
                </td>
                <td colspan="3">
                    Query Result must contain only two columns<br>
                    [ COL1->option value, COL2->option text ]
                    <div id="DeleteQueryResult" style="color: blue; width: 250px"></div>
                    <textarea id="txtDeleteSrcQuery" name="txtDeleteSrcQuery" rows="4" cols="5" onblur="checkQuery('Delete')"></textarea>
                </td>
            </tr>
            <tr id="trDeleteIndepSrcCommonCmb" style="display: none">
                <td>
                    <sup style="color: red">*</sup>Select :
                </td>
                <td colspan="3">
                    <select id="cmbDeleteCommonQuery" name="cmbDeleteCommonQuery">
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
            <tbody id="tbdDeleteWS" style="display: none">
                <tr>
                    <td>
                        <sup style="color: red">*</sup>wsdl URL :
                    </td>
                    <td colspan="3">
                        <input type="text" id="txtDeleteWsdlUrl" name="txtDeleteWsdlUrl" onblur="getWsdlMethods('Delete')" />
                    </td>
                </tr>
                <tr>
                    <td>
                        <sup style="color: red">*</sup>Method :
                    </td>
                    <td colspan="3">
                        <select id="cmbDeleteWsMethod" name="cmbDeleteWsMethod" onchange="javascript: getWsMethodParams('Delete')">
                            <option value="-1">-- Select Method --</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>
                        <sup style="color: red">*</sup>combo value :
                    </td>
                    <td>
                        <input type="text" id="txtDeleteWsCmbValue" name="txtDeleteWsCmbValue" style="width: 100px" />
                    </td>
                    <td>
                        <sup style="color: red">*</sup>combo text :
                    </td>
                    <td>
                        <input type="text" id="txtDeleteWsCmbText" name="txtDeleteWsCmbText" style="width: 100px" />
                    </td>
                </tr>
                <tr>
                    <td id="tdDeleteWsdl" style="display: none">
                    </td>
                </tr>
            </tbody>
            <tr id="trDeleteChkProp" style="display: none">
                <td>
                    checked
                </td>
                <td>
                    <input type="radio" id="rbtnDeleteCheckedTrue" name="rbtnDeleteChecked" style="width: 50px" value="True" />True
                    <input type="radio" id="rbtnDeleteCheckedFalse" name="rbtnDeleteChecked" style="width: 50px" value="False" />False
                </td>
            </tr>
            <tr id="trDeleteRdoProp" style="display: none">
                <td id="DeleteTotalRdo1">
                    <sup style="color: red">*</sup>No.Of Radios
                </td>
                <td id="DeleteTotalRdo2">
                    <input type="text" id="txtDeleteTotalRdo" name="txtDeleteTotalRdo" style="width: 100px" />
                </td>
            </tr>
            <tr id="trDeleteRdoCaptions" style="display: none">
                <td colspan="2">
                    <sup style="color: red">*</sup>captions: e.g. first/second/third
                </td>
                <td colspan="2">
                    <input type="text" id="txtDeleteRdoCap" name="txtDeleteRdoCap" style="width: 200px" />
                </td>
            </tr>
            <tr id="trDeleteRdoValues" style="display: none">
                <td colspan="2">
                    <sup style="color: red">*</sup>values: e.g. first/second/third
                </td>
                <td colspan="2">
                    <input type="text" id="txtDeleteRdoVal" name="txtDeleteRdoVal" style="width: 200px" />
                </td>
            </tr>
            <tr id="trDeleteRdoDefaultValue" style="display: none">
                <td colspan="3">
                    Default Selection: e.g. Enter 2 if 'second' is selected
                </td>
                <td>
                    <input type="text" id="txtDeleteRdoDefVal" name="txtDeleteRdoDefVal" style="width: 100px" />
                </td>
            </tr>
            <tr id="trDeleteChkValue" style="display: none">
                <td>
                    <sup style="color: red">*</sup>Value Format
                </td>
                <td colspan="3">
                    <input type="radio" id="rbtnDeleteCheckedOneZero" name="rbtnDeleteValue" style="width: 25px" />1-0
                    <input type="radio" id="rbtnDeleteCheckedYesNo" name="rbtnDeleteValue" style="width: 25px" />Yes-No
                    <input type="radio" id="rbtnDeleteCheckedYN" name="rbtnDeleteValue" style="width: 25px" />Y-N
                    <input type="radio" id="rbtnDeleteCheckedTF" name="rbtnDeleteValue" style="width: 25px" />TRUE-FALSE
                </td>
            </tr>
            <tr id="trDeleteTxt_LabelProp" style="display:none">
                <td>
                    maxlength
                </td>
                <td>
                    <input type="text" id="txtDeleteMaxLength" name="txtDeleteMaxLength" style="width: 100px" />
                </td>
                <td>
                    readonly
                </td>
                <td>
                    <input type="radio" id="rbtnDeleteReadonlyTrue" name="rbtnDeleteReadonly" style="width: 50px" value="True" />True
                    <input type="radio" id="rbtnDeleteReadonlyFalse" name="rbtnDeleteReadonly" style="width: 50px" value="False" />False
                </td>
            </tr>
            <tr id="trDeleteAlignProp" style="display: none">
                <td>
                    align
                </td>
                <td>
                    <select id="cmbDeleteAlign" name="cmbDeleteAlign" class="custom_combo_low">
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
                    <input type="text" id="txtDeleteValue" name="txtDeleteValue" style="width: 100px" />
                </td>
            </tr>
            <tr id="trDeleteTextAreaProp" style="display: none">
                <td>
                    rows
                </td>
                <td>
                    <input type="text" id="txtDeleteRows" name="txtDeleteRows" style="width: 100px" />
                </td>
                <td>
                    cols
                </td>
                <td>
                    <input type="text" id="txtDeleteCols" name="txtDeleteCols" style="width: 100px" />
                </td>
            </tr>
            <tr id="trDeleteSizeType" style="display: none">
                <td>
                    maxsize (kb)
                </td>
                <td>
                    <input type="text" id="txtDeleteMaxsize" name="txtDeleteMaxsize" style="width: 100px" />
                </td>
                <td>
                    <sup style="color: red">*</sup>type (e.g. txt,pdf)
                </td>
                <td>
                    <input type="text" id="txtDeleteType" name="txtDeleteType" style="width: 100px" />
                </td>
            </tr>
            <tr id="trDeleteFilesEle" style="display: none">
                <td>
                    maxfiles
                </td>
                <td>
                    <input type="text" id="txtDeleteMaxfiles" name="txtDeleteMaxfiles" style="width: 100px" />
                </td>
                <td>
                    <sup style="color: red">*</sup>elementname
                </td>
                <td>
                    <input type="text" id="txtDeleteEleName" name="txtDeleteEleName" style="width: 100px" />
                </td>
            </tr>
            <tr id="trDeleteDispTxt" style="display: none">
                <td>
                    value
                </td>
                <td>
                    <input type="text" id="txtDeleteDispTxt" name="txtDeleteDispTxt" style="width: 100px" />
                </td>
                <td>
                    onremovecallback
                </td>
                <td>
                    <input type="text" id="txtDeleteOnremoveCall" name="txtDeleteOnremoveCall" style="width: 100px" />
                </td>
            </tr>
        </table>
    </div>
    <br>
    <input type="checkbox" value="SRS" id="chkDeleteSRS" name="chkDeleteSRS" onchange="javascript:showSRSProperty('Delete')" />SRS Properties
    <div id="DeleteSRSProperty" style="vertical-align: top;display: none">
        <table>
            <tr>
                <td width="50%" align="right">
                    Validation :
                </td>
                <td width="50%">
                    <select id="cmbDeleteValidation" name="cmbDeleteValidation" class="custom_combo_mid"
                            onchange="javascript:showChkMndtry('Delete')" >
                    </select>
                </td>
            </tr>
            <tr id="trDeleteMndtry">
                <td align="right">
                    Mandatory :
                </td>
                <td>
                    <input type="checkbox" id="chkDeleteMandatory" name="chkDeleteMandatory" />
                </td>
            </tr>
            <tr>
                <td align="right">
                    Remarks :
                </td>
                <td>
                    <input type="text" id="txtDeleteRemarks" name="txtDeleteRemarks" class="text" style="width: 150px">
                </td>
            </tr>
        </table>
    </div>
</div>
