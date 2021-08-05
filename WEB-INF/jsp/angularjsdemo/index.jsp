<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>

<html ng-app="demo">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>AngularJS Demo</title>
        <link type="text/css" rel="stylesheet" href="./css/jquery-ui.css"/>
        <link type="text/css" rel="stylesheet" href="./css/default.css"/>
        <link type="text/css" rel="stylesheet" href="./css/main_offline.css"/>
    </head>

    <body>
        <div ng-controller="democontroller as dc">
            <center>
                <div>
                    <span style="text-align:center" class="heading"><h1>AngularJS Demo</h1></span>
                </div>
                <div>
                    <table style="width:900px">
                        <tr onclick="toggle(1)">
                            <td class="list">1</td>
                            <td class="subject"><h3>Expressions</h3></td>
                        </tr>
                        <tr id="1" class="hidden">
                            <td></td>
                            <td>
                                <div class="example">
                                    <ol>
                                        <li>4 + 5 = {{ 4 + 5 }}</li>
                                        <li>"hello" + " world" = {{"hello" + " world"}}</li>
                                    </ol>
                                </div>
                                <div class="code">
                                    <a href="#" onclick="$('#code1').dialog({maxWidth:600,maxHeight: 350,width: 600,height:350,modal: true})">Show Code</a>
                                    <div style="display:none;width:500px;min-height:100px;font-size:14px;" title="Code" id="code1">
                                        <textarea ng-non-bindable style="width:100%;height:95%" xcols="50" xrows="30" wrap="logical" id="textareaCode" autocomplete="off"><span>4 + 5 = {{ 4 + 5 }}</span>
<span>"hello" + " world" = {{"hello" + " world"}}</span></textarea>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr onclick="toggle(2)">
                            <td class="list">2</td>
                            <td class="subject"><h3>Two Way Data Binding</h3></td>
                        </tr>
                        <tr id="2" class="hidden">
                            <td></td>
                            <td>
                                <div class="example">
                                    <label for="inputtext"><b>Value</b></label>&nbsp;&nbsp;
                                    <input type="text" ng-model="textvalue"><br>
                                    {{textvalue}}
                                </div>
                                <div class="code">
                                    <a href="#" onclick="$('#code2').dialog({maxWidth:600,maxHeight: 350,width: 600,height: 350,modal: true})">Show Code</a>
                                    <div style="display:none;width:500px;min-height:100px;font-size:14px;" title="Code" id="code2">
                                        <textarea ng-non-bindable style="width:100%;height:95%" xcols="50" xrows="30" wrap="logical" id="textareaCode" autocomplete="off"><input type="text" ng-model="textvalue">
{{textvalue}}

or

<input type="text" ng-model="textvalue">
<label ng-bind="textvalue"></lable></textarea>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr onclick="toggle(3)">
                            <td class="list">3</td>
                            <td class="subject"><h3>Angular Directives</h3></td>
                        </tr>
                        <tr id="3" class="hidden">
                            <td></td>
                            <td>
                                <div class="example">
                                    <div ng-init="quantity=1;price=5">
                                        <h3>Cost Calculator</h3>
                                        Quantity: <input style="width:50px" type="number" ng-model="quantity">
                                        Price: <input style="width:50px" type="number" ng-model="price">
                                        <p><b>Total Cost:</b> {{quantity * price}}</p>
                                    </div>
                                </div>
                                <div class="code">
                                    <a href="#" onclick="$('#code3').dialog({maxWidth:600,maxHeight: 350,width: 600,height: 350,modal: true})">Show Code</a>
                                    <div style="display:none;width:500px;min-height:100px;font-size:14px;" title="Code" id="code3">
                                        <textarea ng-non-bindable style="width:100%;height:95%" xcols="50" xrows="30" wrap="logical" id="textareaCode" autocomplete="off"><div ng-init="quantity=1;price=5">

    <input type="number" ng-model="quantity">
    <input type="number" ng-model="price">

    Total Cost: {{quantity * price}}

    Some of directives,
    ng-app           ng-hide
    ng-controller   ng-show
    ng-init            ng-bind

</div></textarea>

                                    </div>
                            </td>
                        </tr>
                        <tr onclick="toggle(4)">
                            <td class="list">4</td>
                            <td class="subject"><h3>Controllers</h3></td>
                        </tr>
                        <tr id="4" class="hidden">
                            <td></td>
                            <td>
                                <div class="example">
                                    <div ng-controller="personController">
                                        First Name: <input type="text" ng-model="firstName"><br>
                                        Last Name: <input type="text" ng-model="lastName"><br>
                                        <br>
                                        Full Name: {{firstName + " " + lastName}}
                                    </div>
                                </div>
                                <div class="code">
                                    <a href="#" onclick="$('#code4').dialog({maxWidth:600,maxHeight: 350,width: 600,height: 350,modal: true})">Show Code</a>
                                    <div style="display:none;width:500px;min-height:100px;font-size:14px;" title="Code" id="code4">
                                        <textarea ng-non-bindable style="width:100%;height:95%" xcols="50" xrows="30" wrap="logical" id="textareaCode" autocomplete="off"><div ng-app="" ng-controller="personController">

First Name: <input type="text" ng-model="firstName"><br>
Last Name: <input type="text" ng-model="lastName"><br>
<br>
Full Name: {{firstName + " " + lastName}}

</div>

<script>
function personController($scope)
{
    $scope.firstName = "Steve";
    $scope.lastName = "Jobs";
}
</script></textarea>
                                    </div>
                            </td>
                        </tr>
                        <tr onclick="toggle(5)">
                            <td class="list">5</td>
                            <td class="subject"><h3>Ajax Data Fill</h3></td>
                        </tr>
                        <tr id="5" class="hidden">
                            <td></td>
                            <td>
                                <div class="example">
                                    <table style="width:400px;">
                                        <tr>
                                            <td><b>Project</b></td>
                                            <td>
                                                <select style="width:250px;max-width:250px" ng-model="project" ng-change="getmoduledata(project)" ng-init="project=-1">
                                                    <option value="-1" selected>Select Project</option>
                                                    <option ng-repeat="data in projectdata" value="{{data.id}}">{{data.value}}</option>
                                                </select>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td><b>Module</b></td>
                                            <td>
                                                <select style="width:250px;max-width:250px">
                                                    <option value="-1">Select Module</option>
                                                    <option ng-repeat="data in moduledata" value="{{data.id}}">{{data.value}}</option>
                                                </select>
                                            </td>
                                        </tr>
                                    </table>
                                </div>
                                <div class="code">
                                    <a href="#" onclick="$('#code5').dialog({maxWidth:650,maxHeight: 4000,width: 650,height: 400,modal: true})">Show Code</a>
                                    <div style="display:none;width:650px;min-height:100px;font-size:14px;" title="Code" id="code5">
                                        <textarea ng-non-bindable style="width:100%;height:95%" xcols="50" xrows="30" wrap="logical" id="textareaCode" autocomplete="off"><select ng-model="project" ng-change="getmoduledata(project)" ng-init="project=-1">
    <option value="-1" selected>Select Project</option>
    <option ng-repeat="data in projectdata" value="{{data.id}}">{{data.value}}</option>
</select>
<select style="width:250px;max-width:250px">
    <option value="-1">Select Module</option>
    <option ng-repeat="data in moduledata" value="{{data.id}}">{{data.value}}</option>
</select>


$scope.getmoduledata = function(project){
    $http.get('getmdata.htm?p='+project).success(function(data){
        $scope.moduledata = eval(data);
    });
}</textarea>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr onclick="toggle(6)">
                            <td class="list">6</td>
                            <td class="subject"><h3>HTML Table Generation</h3></td>
                        </tr>
                        <tr id="6" class="hidden">
                            <td></td>
                            <td>
                                <div class="example">
                                    <p>Sort By&nbsp;&nbsp;<select  ng-init="sortorder=srno" ng-model="sortorder">
                                            <option value="srno">Sr No</option>
                                            <option value="group_name">Group Name</option>
                                            <option value="stats_string">Stats String</option>
                                            <option value="on_date">On Date</option>
                                        </select></p>
                                    <p>Search&nbsp;&nbsp;<input type="text" ng-model="query"></p>
                                    <table class="tbl_border1 tbl_h4_bg1 table-stripeclass:alternate" style="width:100%;">
                                        <thead>
                                            <tr class="report_caption">
                                                <td>Sr No</td>
                                                <td>Group Name</td>
                                                <td>Stats String</td>
                                                <td>On Date</td>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr ng-repeat="data in tabledata | orderBy:sortorder | filter:{group_name:query}">
                                                <td>{{data.srno}}</td>
                                                <td>{{data.group_name}}</td>
                                                <td>{{data.stats_string}}</td>
                                                <td>{{data.on_date}}</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                                <div class="code">
                                    <a href="#" onclick="$('#code6').dialog({maxWidth:650,maxHeight: 475,width: 650,height: 475,modal: true})">Show Code</a>
                                    <div style="display:none;width:650px;min-height:100px;font-size:14px;" title="Code" id="code6">
                                        <textarea ng-non-bindable style="width:100%;height:95%" xcols="50" xrows="30" wrap="logical" id="textareaCode" autocomplete="off"><table class="tbl_border1 tbl_h4_bg1 table-stripeclass:alternate">
<thead>
    <tr class="report_caption">
        <td>Sr No</td>
        <td>Group Name</td>
        <td>Stats String</td>
        <td>On Date</td>
    </tr>
</thead>
<tbody>
    <tr ng-repeat="data in tabledata">
        <td>{{data.srno}}</td>
        <td>{{data.group_name}}</td>
        <td>{{data.stats_string}}</td>
        <td>{{data.on_date}}</td>
    </tr>
</tbody>
</table>

$http.get('gettdata.htm').success(function(data){
    $scope.tabledata = eval(data);
});</textarea>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr onclick="toggle(7)">
                            <td class="list">7</td>
                            <td class="subject"><h3>Filters</h3></td>
                        </tr>
                        <tr id="7" class="hidden">
                            <td></td>
                            <td>
                                <div class="example">
                                    <p style="font-size:small">Code Taken from <b>DDSD > Performance Assessment > Investment Employee Scorecard</b></p>
                                    <b>Designation<span class="astriek">*</span> :</b>&nbsp;
                                    <select ng-model="des" ng-init="des=0">
                                        <option value="0">Select Designation</option>
                                        <option value="D1">Deputy General Manager</option>
                                        <option value="D2">Manager</option>
                                        <option value="D3">Assistant Manager</option>
                                        <option value="D4">Sr. Executive</option>
                                        <option value="AM">Agency Manager</option>
                                        <option value="XP">Fundz Express</option>
                                    </select>
                                    <div>
                                        <b class="check">Report For <span class="astriek">*</span> :</b>
                                        &nbsp;&nbsp;<br>
                                        <span>
                                            <div class="check" ng-show="des == 'D1'">
                                                <input class="checkbox" value="'D1'" checked="checked" type="checkbox">Deputy General Manager
                                            </div>
                                            <div class="check" ng-show="des == 'D1' || des == 'D2'">
                                                <input class="checkbox" value="'D2'" checked="checked" type="checkbox">Manager
                                            </div>
                                            <div class="check" ng-show="des == 'D1' || des == 'D2' || des == 'D3'">
                                                <input class="checkbox" value="'D3'" checked="checked" type="checkbox">Assistant Manager
                                            </div>
                                            <div class="check" ng-show="des == 'D1' || des == 'D2' || des == 'D3' || des == 'D4'">
                                                <input class="checkbox" value="'D4'" checked="checked" type="checkbox">Sr. Executive
                                            </div>
                                            <div class="check" ng-show="des == 'D1' || des == 'D2' || des == 'D3' || des == 'AM'">
                                                <input class="checkbox" value="'AM'" checked="checked" type="checkbox">Agency Manager
                                            </div>
                                            <div class="check" ng-show="des == 'D1' || des == 'D2' || des == 'D3' || des == 'XP'">
                                                <input class="checkbox" value="'XP'" checked="checked" type="checkbox">Fundz Express
                                            </div>
                                        </span>
                                    </div>
                                </div>
                                <div class="code">
                                    <a href="#" onclick="$('#code7').dialog({maxWidth:650,maxHeight: 475,width: 650,height: 475,modal: true})">Show Code</a>
                                    <div style="display:none;width:650px;min-height:100px;font-size:14px;" title="Code" id="code7">
                                        <textarea ng-non-bindable style="width:100%;height:95%" xcols="50" xrows="30" wrap="logical" id="textareaCode" autocomplete="off">List of filters
ng-show , ng-hide , currency , lowercase , orderBy , uppercase

Designation * :
<select ng-model="des" ng-init="des=0">
    <option value="0">Select Designation</option>
    <option value="D1">Deputy General Manager</option>
    <option value="D2">Manager</option>
    <option value="D3">Assistant Manager</option>
    <option value="D4">Sr. Executive</option>
    <option value="AM">Agency Manager</option>
    <option value="XP">Fundz Express</option>
</select>

Report For * :
<span>
    <div class="check" ng-show="des == 'D1'">
        <input class="checkbox" value="'D1'" checked="checked" type="checkbox">Deputy General Manager
    </div>
    <div class="check" ng-show="des == 'D1' || des == 'D2'">
        <input class="checkbox" value="'D2'" checked="checked" type="checkbox">Manager
    </div>
    <div class="check" ng-show="des == 'D1' || des == 'D2' || des == 'D3'">
        <input class="checkbox" value="'D3'" checked="checked" type="checkbox">Assistant Manager
    </div>
    <div class="check" ng-show="des == 'D1' || des == 'D2' || des == 'D3' || des == 'D4'">
        <input class="checkbox" value="'D4'" checked="checked" type="checkbox">Sr. Executive
    </div>
    <div class="check" ng-show="des == 'D1' || des == 'D2' || des == 'D3' || des == 'AM'">
        <input class="checkbox" value="'AM'" checked="checked" type="checkbox">Agency Manager
    </div>
    <div class="check" ng-show="des == 'D1' || des == 'D2' || des == 'D3' || des == 'XP'">
        <input class="checkbox" value="'XP'" checked="checked" type="checkbox">Fundz Express
    </div>
</span></textarea>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr onclick="toggle(8)">
                            <td class="list">8</td>
                            <td class="subject"><h3>Validations</h3></td>
                        </tr>
                        <tr id="8" class="hidden">
                            <td></td>
                            <td>
                                <div class="example">
                                    <form ng-controller="validateCtrl" name="myForm" novalidate>
                                        <p>Username:<br>
                                            <input style="width:150px" type="text" name="user" ng-model="user" required>
                                            <span style="color:red" ng-show="myForm.user.$dirty && myForm.user.$invalid">
                                                <span ng-show="myForm.user.$error.required">Username is required.</span>
                                            </span>
                                        </p>
                                        <p>Email:<br>
                                            <input style="width:150px" type="email" name="email" ng-model="email" required>
                                            <span style="color:red" ng-show="myForm.email.$dirty && myForm.email.$invalid">
                                                <span ng-show="myForm.email.$error.required">Email is required.</span>
                                                <span ng-show="myForm.email.$error.email">Invalid email address.</span>
                                            </span>
                                        </p>
                                        <p>
                                            <input class="button" style="background-color:#6F6F6F" type="submit" ng-disabled="myForm.user.$dirty && myForm.user.$invalid || myForm.email.$dirty && myForm.email.$invalid">
                                        </p>

                                    </form>
                                </div>
                                <div class="code">
                                    <a href="#" onclick="$('#code8').dialog({maxWidth:650,maxHeight: 475,width: 650,height: 475,modal: true})">Show Code</a>
                                    <div style="display:none;width:650px;min-height:100px;font-size:14px;" title="Code" id="code8">
                                        <textarea ng-non-bindable style="width:100%;height:95%" xcols="50" xrows="30" wrap="logical" id="textareaCode" autocomplete="off"><form ng-app="" ng-controller="validateCtrl" name="myForm" novalidate>
<p>Username:<br>
  <input type="text" name="user" ng-model="user" required>
  <span style="color:red" ng-show="myForm.user.$dirty && myForm.user.$invalid">
  <span ng-show="myForm.user.$error.required">Username is required.</span>
<p>Email:<br>
  <input type="email" name="email" ng-model="email" required>
  <span style="color:red" ng-show="myForm.email.$dirty && myForm.email.$invalid">
  <span ng-show="myForm.email.$error.required">Email is required.</span>
  <span ng-show="myForm.email.$error.email">Invalid email address.</span>
  <input type="submit" ng-disabled="myForm.user.$dirty && myForm.user.$invalid || myForm.email.$dirty && myForm.email.$invalid">
</form>

<script>
    function validateCtrl($scope) {
        $scope.user = 'Steve Jobs';
        $scope.email = 'steve.jobs@apple.com';
    }
</script></textarea>
</div>
</div>
</td>
</tr>
</table>
</div>
</center>
<br><br><br>
</div>
<script type="text/javascript" src="./javascript/jquery.min.js"></script>
<script type="text/javascript" src="./javascript/angular.js"></script>
<script type="text/javascript" src="./javascript/application.js"></script>
<script type="text/javascript" src="./javascript/jquery-ui.js"></script>
</body>
</html>