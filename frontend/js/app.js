'use strict'

var app = angular.module('lokalizator', ['ngRoute']);
app.config(['$routeProvider', function($routeProvider) {
    $routeProvider
        .when('/',{
            templateUrl: 'login.html'
        })
        .when('/register',{
            templateUrl: 'register.html'
        })
        .when('/index',{
            resolve : {
                "check" : function($location,$rootScope){
                    if(!$rootScope.loggedIn) {
                        $location.path ('login.html');
                    }
                }
            },
            templateUrl: 'index.html'
        })
        .otherwise({
            redirectTo : '/'
        })
}]);
app.service("PromiseUtils", function($q) {
    return {
        getPromiseHttpResult: function (httpPromise) {
            var deferred = $q.defer();
            httpPromise.success(function (data) {
                deferred.resolve(data);
            }).error(function () {
                deferred.reject(arguments);
            });
            return deferred.promise;
        }
    }
});

app.controller("loginCtrl", ['$scope', 'PromiseUtils', '$http','$location','$rootScope','$window', function ($scope, PromiseUtils, $http, $location,$rootScope,$window) {
    $scope.login = "";
    $scope.password = "";

    $scope.zaloguj = function () {
        var loginJson = {
            "userLogin": $scope.login,
            "userPassword": $scope.password
        }

        var login = $http({
            method: 'POST',
            url: 'http://207.154.222.0:8080/log4/taxiservice/login',
            data: loginJson,
            headers: {
                'Content-Type': 'application/json'
            }
        });
        PromiseUtils.getPromiseHttpResult(login).then(function (result) {
           if(result.valid){
               $rootScope.loggedIn = true;
               $rootScope.userId = result.systemUser.user_id;
               $location.path ('/index');
               console.log(result);
            }
            else{
               $rootScope.loggedIn = false;
            }
        },function(result){
            console.log('cos nie poszlo');
        });


        console.log($rootScope.loggedIn, '$rootScope.loggedIn');
    }
}]);

app.controller("myCtrl",['$scope', 'PromiseUtils','$http','$rootScope', function($scope,PromiseUtils,$http,$rootScope) {
    $scope.cost=0;
    $scope.start = "Sieradz";
    $scope.end  = "Wroclaw";
    $scope.resultDistance=[];
    $scope.resultTime=[];
    $scope.apiStatus="test";


    $scope.szukaj= function(){
        $scope.resultDistance=[];
        $scope.resultTime=[];
        var distance  = $http({
            method: 'GET',
            url: 'http://207.154.222.0:8080/Localization/taxiservice/localization/getDistance?originAddress='+$scope.start+'&destinationAddress='+$scope.end,
            headers: {
                'Content-Type': 'application/json'
            }
        });
        var time  = $http({
            method: 'GET',
            url: 'http://207.154.222.0:8080/Localization/taxiservice/localization/getTravelTime?originAddress='+$scope.start+'&destinationAddress='+$scope.end,
            headers: {
                'Content-Type': 'application/json'
            }
        });
        PromiseUtils.getPromiseHttpResult(distance).then(function(result){
            Object.keys(result).forEach(function(key) {
                $scope.resultDistance.push(result[key]);
            });
            var str = $scope.resultDistance[3].match(/\d+/g, "")+'';
            $scope.cost = str.split(',').join('.');
            $scope.cost=$scope.cost*2 +' zł';
        });
        PromiseUtils.getPromiseHttpResult(time).then(function(result){
            Object.keys(result).forEach(function(key) {
                $scope.resultTime.push(result[key]);
            });


        });
    }

    $scope.zamow= function(){
        var order  = $http({
            method: 'POST',
            url: 'http://207.154.222.0:8080/orderservice/taxiservice/order',
            headers: {
                'Content-Type': 'application/json'
            }
        });
        var orderJson = {
            "user_id": $rootScope.userId,
            "location_start" :$scope.start,
            "location_end" :$scope.stop
    }
        PromiseUtils.getPromiseHttpResult(time).then(function(result){
            Object.keys(result).forEach(function(key) {
                $scope.resultTime.push(result[key]);
            });


        });
    }
}]);

app.controller("registerCtrl",['$scope', 'PromiseUtils','$http','$location','$rootScope', function($scope,PromiseUtils,$http,$location,$rootScope){
    $scope.login = "";
    $scope.password = "";
    $scope.first_name= "";
    $scope.last_name= "";
    $scope.email= "";
    $scope.join_date= "";
    $scope.phone_number= "";

    $scope.rejestracja = function () {
        var registerJson = {
        "first_name":$scope.first_name,
        "last_name":$scope.last_name,
        "email":$scope.email,
        "login": $scope.login,
        "password": $scope.password,
        "join_date":new Date().getTime(),
        "phone_number":$scope.phone_number
        }

        var register = $http({
            method: 'POST',
            url: 'http://207.154.222.0:8080/log4/taxiservice/register',
            data: registerJson,
            headers: {
                'Content-Type': 'application/json'
            }
        });
        PromiseUtils.getPromiseHttpResult(register).then(function (result) {
            if(result.isSuccess){
                $location.path ('/');
            }
            else{
                $rootScope.loggedIn = false;
            }
        },function(result){
            console.log('cos nie poszlo');
        });



    }
}]);