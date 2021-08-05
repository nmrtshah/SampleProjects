<%-- 
    Document   : Parameter
    Created on : 9 May, 2015, 4:56:07 PM
    Author     : njuser
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<table border="2" rules="NONE" frame="box" align="center" border="0" cellpadding="0" cellspacing="2" id="inparamtable" class="table_subcaption" width="100%">
    <tr>
        <td colspan="7">
            <div class="menu_caption_bg">
                <div class="menu_caption_text">In Parameter</div>
                <!--                    <select class="custom_combo_mid" id="inBeanType" name="inBeanType" tabindex="2" onchange="javascript:onChangeInCombo(this)">
                                        <option value="Single">Single</option>
                                        <option value="List">List</option>
                                    </select>-->
            </div>
        </td>
    </tr>
    <tr id="" style="display: none">
        <!--            <td align="left" style="float: none!important; width: 50%;" class="">
                        In Parameter Bean Name <sup class="astriek">*</sup>:  
                    </td>
                    <td colspan="6" class="report_content_value">
                        <input style="float: none!important; width: 136px;" type="text" id="inParam" name="inParam" value="" onchange="javascript:toUpperFirstLetter(this)"/>
                    </td>-->
    </tr>
    <tr>
        <td align="left" class="report_content_caption">
            Parameter <sup class="astriek">*</sup>: 
        </td>
        <td class="report_content_value">
            Nature <sup class="astriek">*</sup>
        </td>
        <td class="report_content_value">
            Name <sup class="astriek">*</sup>
        </td>
        <td class="report_content_value">
            Datatype <sup class="astriek">*</sup>
        </td>
        <td class="report_content_value">
            Default Value <sup class="astriek">*</sup>
        </td>
        <td class="report_content_value">
            Format
        </td>
    </tr>
</table>

<table border="2" rules="NONE" frame="box" align="center" border="0" cellpadding="0" cellspacing="2" id="outparamtable" class="table_subcaption" width="100%">
    <tr>
        <td  colspan="7">
            <div class="menu_caption_bg">
                <div class="menu_caption_text">Out Parameter</div>

                <!--                    <select class="custom_combo_mid" id="outBeanType" name="outBeanType" tabindex="2" onchange="javascript:onChangeOutCombo(this)">
                                        <option value="Single">Single</option>
                                        <option value="List">List</option>
                                    </select>-->
            </div>
        </td>
    </tr>
    <tr id="" style="display: none">
        <!--            <td align="left" style="float: none!important; width: 50%;" class="">
                        Out Parameter Bean Name <sup class="astriek">*</sup>: 
                    </td>
                    <td colspan="6" class="report_content_value">
                        <input style="float: none!important; width: 136px;" type="text" id="outParam" name="outParam" value="" onchange="javascript:toUpperFirstLetter(this)"/>
                    </td>-->
    </tr>
    <tr>
        <td align="left" class="report_content_caption">
            Parameter <sup class="astriek">*</sup>: 
        </td>
        <td class="report_content_value">
            Nature <sup class="astriek">*</sup>
        </td>
        <td class="report_content_value">
            Name <sup class="astriek">*</sup>
        </td>
        <td class="report_content_value">
            Datatype <sup class="astriek">*</sup>
        </td>
        <td class="report_content_value">
            Default Value <sup class="astriek">*</sup>
        </td>
        <td class="report_content_value">
            Format
        </td>
    </tr>   
</table>
<div id="dataTypeList" style="display: none">
    <option value="String">String</option>
    <option value="int">int</option>
    <option value="long">long</option>
    <option value="float">float</option>
    <option value="double">double</option>
    <option value="date">date</option>
    <option value="time">time</option>
    <option value="boolean">boolean</option>
    <option value="Bean">Bean</option>
    <option value="List<Bean>">List < Bean ></option>
    <option value="datetime">datetime</option>
    <option value="String[]">String[]</option>
    <option value="int[]">int[]</option>
    <option value="long[]">long[]</option>
    <option value="float[]">float[]</option>
    <option value="double[]">double[]</option>
    <option value="date[]">date[]</option>
    <option value="time[]">time[]</option>
    <option value="datetime[]">datetime[]</option>
    <option value="boolean[]">boolean[]</option>
</div>

<div id="natureList" style="display: none">
    <option value="Optional">Optional</option>
    <option value="Mandatory">Mandatory</option>
</div>