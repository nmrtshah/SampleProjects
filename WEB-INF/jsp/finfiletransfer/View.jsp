<%-- TCIGBF --%>

<div class="collapsible_menu_tab">
    <ul id="mainTab2">
        <li class="" id="mainTab2_1" onclick="selectTab(this.id);" style="display: none">
            <a rel="rel1" href="javascript:void(0)" onclick="javascript:showTab('divViewFilter');">Filter</a>
        </li>
        <li class="" id="mainTab2_2" onclick="selectTab(this.id);" style="display: none">
            <a rel="rel1" href="javascript:void(0)" onclick="javascript:showTab('divViewExport');">Export</a>
        </li>
    </ul>
</div>
<div id="divViewFilter" style="display: none;">
    <jsp:include page="Filter.jsp"/>
</div>
<div id="divViewExport" style="display: none;">
    <jsp:include page="Export.jsp"/>
</div>
<div id="divButton" style="display: none">
    <input class="button" type="button" id="btnApply" name="btnApply" Value="Apply" onclick="javascript: showReport('View', this.form);" />
    <input class="button" type="button" id="btnPrint" name="btnPrint" value="Print" onclick="javascript: showReport('Print', this.form);" />
</div>
