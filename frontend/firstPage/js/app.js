'use strict'

var app=angular.module('lokalizator', []);

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

app.controller("myCtrl",['$scope', 'PromiseUtils','$http', function($scope,PromiseUtils,$http) {
   $scope.cost=0;
   $scope.start = "Sieradz";
   $scope.end  = "Wroclaw";
   $scope.resultDistance=[];
   $scope.resultTime=[];	
$scope.apiStatus="test";


 $scope.szukaj= function(){
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
$scope.cost = str.split(',').join('');
 $scope.cost=$scope.cost*2 +' zł';
 });
 PromiseUtils.getPromiseHttpResult(time).then(function(result){
 Object.keys(result).forEach(function(key) {
$scope.resultTime.push(result[key]);
 });

console.log($scope.resultTime[0]);
    });




}}]);
