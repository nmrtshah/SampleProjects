(function(){
    var app = angular.module('demo',[]);

    app.controller('personController',function($scope) {
        $scope.firstName = "Steve";
        $scope.lastName = "Jobs";
    });

    app.controller('validateCtrl',function($scope){
        $scope.user = 'Steve Jobs';
        $scope.email = 'steve.jobs@apple.com';
    });

    app.controller('democontroller',function($scope,$http){

        $http.get('dbmc.fin?cmdAction=gettdata').success(function(data){
            $scope.tabledata = eval(data);
        }).
        error(function(data){
            console.log('error : '+data);
        });

        $http.get('dbmc.fin?cmdAction=getdata').success(function(data){
            $scope.projectdata = eval(data);
        }).
        error(function(data){
            console.log('error : '+data);
        });

        $scope.getmoduledata = function(project){
            var startime = new Date();
            $http.get('dbmc.fin?cmdAction=getmdata&p='+project).success(function(data){
                $scope.moduledata = eval(data);
                console.log('module response in '+((new Date()-startime))/100+' sec.');
            }).
            error(function(data){
                console.log('error : '+data);
            });
        }
    });
})();
var prev = undefined;
function toggle(id)
{
    if ( prev === undefined )
    {
        $('#'+id).slideToggle('fast');
        prev = id;
    }
    else if ( prev == id )
    {
        $('#'+id).slideToggle('fast');
        prev = undefined;
    }
    else
    {
        $('#'+prev).slideToggle('fast');
        $('#'+id).slideToggle('fast');
        prev = id;
    }

}
