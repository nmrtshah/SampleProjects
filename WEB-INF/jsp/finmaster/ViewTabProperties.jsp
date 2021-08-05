<%-- 
    Document   : ViewTabProperties
    Created on : Feb 22, 2012, 12:01:11 PM
    Author     : Sonam Patel
--%>

<div>
    <input type="checkbox" value="Code" id="chkViewCode" name="chkViewCode" onchange="javascript:showCodeProperty('View')" />Code Properties
    <div id ="ViewCodeProperty" style="vertical-align: top; display: none; height: 200px; overflow-x: hidden; overflow-y: auto">
        <table style="width: 90%">
            <th>Properties</th>
            <tr>
                <td style="width: 25%">
                    <sup style="color: red">*</sup>id
                </td>
                <td style="width: 25%">
                    <input type="text" id="txtViewId" name="txtViewId" style="width: 100px" />
                </td>
                <td style="width: 25%">
                    <sup style="color: red">*</sup>name
                </td>
                <td style="width: 25%">
                    <input type="text" id="txtViewName" name="txtViewName" style="width: 100px" />
                </td>
            </tr>
            <tr id="trViewStyleSizeProp" style="display: none">
                <td>
                    style
                </td>
                <td>
                    <input type="text" id="txtViewStyle" name="txtViewStyle" style="width: 100px" />
                </td>
                <td id="ViewSize1">
                    size
                </td>
                <td id="ViewSize2">
                    <input type="text" id="txtViewSize" name="txtViewSize" style="width: 100px" />
                </td>
            </tr>
            <tr id="trViewClassProp" style="display: none">
                <td>
                    class
                </td>
                <td>
                    <input type="text" id="txtViewClass" name="txtViewClass" style="width: 100px" />
                </td>
                <td id="tdViewOnchange1" style="display: none">
                    onchange
                </td>
                <td id="tdViewOnchange2" style="display: none">
                    <select type="text" id="cmbViewOnchange" name="cmbViewOnchange" class="custom_combo_low" onchange="javascript: disableFillCombo('View');">
                        <option value="-1" selected>-- Select --</option>
                    </select>
                </td>
            </tr>
            <tr id="trViewCmbProp" style="display: none">
                <td>
                    multiple
                </td>
                <td>
                    <input type="radio" id="rbtnViewMultipleTrue" name="rbtnViewMultiple" style="width: 50px" />True
                    <input type="radio" id="rbtnViewMultipleFalse" name="rbtnViewMultiple" style="width: 50px" />False
                </td>
            </tr>
            <tr id="trViewIndepSource" style="display: none">
                <td align="right">
                    <input type="checkbox" id="chkViewSrc" name="chkViewSrc" onchange="showSrc('View')" />Data Source :
                </td>
                <td id="tdViewSrc" colspan="3" style="display: none">
                    <input type="radio" id="rdoViewSrcStatic" name="rdoViewDataSrc" onchange="showSrcQuery('View')" />Static Data
                    <input type="radio" id="rdoViewSrcQuery" name="rdoViewDataSrc" onchange="showSrcQuery('View')" />Query
                    <input type="radio" id="rdoViewSrcWS" name="rdoViewDataSrc" onchange="showSrcQuery('View')" />Web Service
                    <input type="radio" id="rdoViewSrcCommonCmb" name="rdoViewDataSrc" onchange="showSrcQuery('View')" />Common Combo
                </td>
            </tr>
            <tr id="trViewIndepSrcStatic" style="display: none">
                <td>
                    <sup style="color: red">*</sup>Static Data :
                </td>
                <td colspan="3">
                    Data Format<br>
                    [ option value1,option text1;option value2,option text2 ]
                    <div id="ViewStaticResult" style="color: blue; width: 250px"></div>
                    <textarea id="txtViewSrcStatic" name="txtViewSrcStatic" rows="4" cols="5" onblur="checkDataFormat('View')"></textarea>
                </td>
            </tr>
            <tr id="trViewIndepSrcQuery" style="display: none">
                <td>
                    <sup style="color: red">*</sup>Query :
                </td>
                <td colspan="3">
                    Query Result must contain only two columns<br>
                    [ COL1->option value, COL2->option text ]
                    <div id="ViewQueryResult" style="color: blue; width: 250px"></div>
                    <textarea id="txtViewSrcQuery" name="txtViewSrcQuery" rows="4" cols="5" onblur="checkQuery('View')"></textarea>
                </td>
            </tr>
            <tr id="trViewIndepSrcCommonCmb" style="display: none">
                <td>
                    <sup style="color: red">*</sup>Select :
                </td>
                <td colspan="3">
                    <select id="cmbViewCommonQuery" name="cmbViewCommonQuery">
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
            <tbody id="tbdViewWS" style="display: none">
                <tr>
                    <td>
                        <sup style="color: red">*</sup>wsdl URL :
                    </td>
                    <td colspan="3">
                        <input type="text" id="txtViewWsdlUrl" name="txtViewWsdlUrl" onblur="getWsdlMethods('View')" />
                    </td>
                </tr>
                <tr>
                    <td>
                        <sup style="color: red">*</sup>Method :
                    </td>
                    <td colspan="3">
                        <select id="cmbViewWsMethod" name="cmbViewWsMethod" onchange="javascript: getWsMethodParams('View')">
                            <option value="-1">-- Select Method --</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>
                        <sup style="color: red">*</sup>combo value :
                    </td>
                    <td>
                        <input type="text" id="txtViewWsCmbValue" name="txtViewWsCmbValue" style="width: 100px" />
                    </td>
                    <td>
                        <sup style="color: red">*</sup>combo text :
                    </td>
                    <td>
                        <input type="text" id="txtViewWsCmbText" name="txtViewWsCmbText" style="width: 100px" />
                    </td>
                </tr>
                <tr>
                    <td id="tdViewWsdl" style="display: none">
                    </td>
                </tr>
            </tbody>
            <tr id="trViewChkProp" style="display: none">
                <td>
                    checked
                </td>
                <td>
                    <input type="radio" id="rbtnViewCheckedTrue" name="rbtnViewChecked" style="width: 50px" value="True" />True
                    <input type="radio" id="rbtnViewCheckedFalse" name="rbtnViewChecked" style="width: 50px" value="False" />False
                </td>
            </tr>
            <tr id="trViewRdoProp" style="display: none">
                <td id="ViewTotalRdo1">
                    <sup style="color: red">*</sup>No.Of Radios
                </td>
                <td id="ViewTotalRdo2">
                    <input type="text" id="txtViewTotalRdo" name="txtViewTotalRdo" style="width: 100px" />
                </td>
            </tr>
            <tr id="trViewRdoCaptions" style="display: none">
                <td colspan="2">
                    <sup style="color: red">*</sup>captions: e.g. first/second/third
                </td>
                <td colspan="2">
                    <input type="text" id="txtViewRdoCap" name="txtViewRdoCap" style="width: 200px" />
                </td>
            </tr>
            <tr id="trViewRdoValues" style="display: none">
                <td colspan="2">
                    <sup style="color: red">*</sup>values: e.g. first/second/third
                </td>
                <td colspan="2">
                    <input type="text" id="txtViewRdoVal" name="txtViewRdoVal" style="width: 200px" />
                </td>
            </tr>
            <tr id="trViewRdoDefaultValue" style="display: none">
                <td colspan="3">
                    Default Selection: e.g. Enter 2 if 'second' is selected
                </td>
                <td>
                    <input type="text" id="txtViewRdoDefVal" name="txtViewRdoDefVal" style="width: 100px" />
                </td>
            </tr>
            <tr id="trViewChkValue" style="display: none">
                <td>
                    <sup style="color: red">*</sup>Value Format
                </td>
                <td colspan="3">
                    <input type="radio" id="rbtnViewCheckedOneZero" name="rbtnViewValue" style="width: 25px" />1-0
                    <input type="radio" id="rbtnViewCheckedYesNo" name="rbtnViewValue" style="width: 25px" />Yes-No
                    <input type="radio" id="rbtnViewCheckedYN" name="rbtnViewValue" style="width: 25px" />Y-N
                    <input type="radio" id="rbtnViewCheckedTF" name="rbtnViewValue" style="width: 25px" />TRUE-FALSE
                </td>
            </tr>
            <tr id="trViewTxt_LabelProp" style="display:none">
                <td>
                    maxlength
                </td>
                <td>
                    <input type="text" id="txtViewMaxLength" name="txtViewMaxLength" style="width: 100px" />
                </td>
                <td>
                    readonly
                </td>
                <td>
                    <input type="radio" id="rbtnViewReadonlyTrue" name="rbtnViewReadonly" style="width: 50px" value="True" />True
                    <input type="radio" id="rbtnViewReadonlyFalse" name="rbtnViewReadonly" style="width: 50px" value="False" />False
                </td>
            </tr>
            <tr id="trViewAlignProp" style="display: none">
                <td>
                    align
                </td>
                <td>
                    <select id="cmbViewAlign" name="cmbViewAlign" class="custom_combo_low">
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
                    <input type="text" id="txtViewValue" name="txtViewValue" style="width: 100px" />
                </td>
            </tr>
            <tr id="trViewTextAreaProp" style="display: none">
                <td>
                    rows
                </td>
                <td>
                    <input type="text" id="txtViewRows" name="txtViewRows" style="width: 100px" />
                </td>
                <td>
                    cols
                </td>
                <td>
                    <input type="text" id="txtViewCols" name="txtViewCols" style="width: 100px" />
                </td>
            </tr>
            <tr id="trViewSizeType" style="display: none">
                <td>
                    maxsize (kb)
                </td>
                <td>
                    <input type="text" id="txtViewMaxsize" name="txtViewMaxsize" style="width: 100px" />
                </td>
                <td>
                    <sup style="color: red">*</sup>type (e.g. txt,pdf)
                </td>
                <td>
                    <input type="text" id="txtViewType" name="txtViewType" style="width: 100px" />
                </td>
            </tr>
            <tr id="trViewFilesEle" style="display: none">
                <td>
                    maxfiles
                </td>
                <td>
                    <input type="text" id="txtViewMaxfiles" name="txtViewMaxfiles" style="width: 100px" />
                </td>
                <td>
                    <sup style="color: red">*</sup>elementname
                </td>
                <td>
                    <input type="text" id="txtViewEleName" name="txtViewEleName" style="width: 100px" />
                </td>
            </tr>
            <tr id="trViewDispTxt" style="display: none">
                <td>
                    value
                </td>
                <td>
                    <input type="text" id="txtViewDispTxt" name="txtViewDispTxt" style="width: 100px" />
                </td>
                <td>
                    onremovecallback
                </td>
                <td>
                    <input type="text" id="txtViewOnremoveCall" name="txtViewOnremoveCall" style="width: 100px" />
                </td>
            </tr>
        </table>
    </div>
    <br>
    <input type="checkbox" value="SRS" id="chkViewSRS" name="chkViewSRS" onchange="javascript:showSRSProperty('View')" />SRS Properties
    <div id="ViewSRSProperty" style="vertical-align: top;display: none">
        <table>
            <tr>
                <td width="50%" align="right">
                    Validation :
                </td>
                <td width="50%">
                    <select id="cmbViewValidation" name="cmbViewValidation" class="custom_combo_mid"
                            onchange="javascript:showChkMndtry('View')" >
                    </select>
                </td>
            </tr>
            <tr id="trViewMndtry">
                <td align="right">
                    Mandatory :
                </td>
                <td>
                    <input type="checkbox" id="chkViewMandatory" name="chkViewMandatory" />
                </td>
            </tr>
            <tr>
                <td align="right">
                    Remarks :
                </td>
                <td>
                    <input type="text" id="txtViewRemarks" name="txtViewRemarks" class="text" style="width: 150px">
                </td>
            </tr>
        </table>
    </div>
</div>
