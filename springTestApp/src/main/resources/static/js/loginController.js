




var app = angular.module('myApp', []);
app.controller('loginCtrl', function($scope,$http) {
	
    $scope.author={};
    $scope.authors=[];
    
    $scope.author.fname="sameer";
    $scope.author.lname="lname";
    $scope.author.email="email";
    $scope.author.mobile="9876543215";
    $scope.author.password="password";
    $scope.author.line1="line1";
    $scope.author.line2="line2";
    $scope.author.city="city";
    $scope.author.state="state";
    $scope.author.country="country";
    $scope.author.pincode="pincode";
    
    $scope.registerAuthor = function(){
    	
    	console.log(angular.toJson($scope.author));
    	
    	$http({
            url: '/author/register',
            method: "POST",
            data: angular.toJson($scope.author),
            headers: {
                'Content-Type': 'application/json'
            }
            	 
        })
        .then(function(response) {
                // success
        	
        	console.log(response);
        	$scope.authors.push(response.data);
        	
        }, 
        function(response) { // optional
                // failed
        	console.log("failed");
        	console.log(response);
        });
    	
    }
    
});