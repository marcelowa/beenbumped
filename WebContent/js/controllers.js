'use strict';

function MenuCtrl($scope) {

}

function UserLoginCtrl($scope, $location, User) {
	$scope.login = {};
	
	$scope.authenticate = function(login) {
		$scope.user = User.get({username: login.username, password: login.password}, function(response){
			console.log(response);
			console.log($scope.user);
			//TODO store userid and hash in cookies or in service for next controller
		}, function(response){
			console.error(response);
			//TODO show failure in login form
		});
		//console.log($scope.user);
		//$location.path("/user/register");
	};
}

function UserRegisterCtrl($scope, $location, User) {
	$scope.user = {
		username : "marc@walla.com",
		email : "marc@walla.com",
		password : "stamstam",
		passwordConfirm : "stamstam"
	};
	
	$scope.register = function(user) {
		$scope.user = User.save(user, function(response){
			console.log(response);
			console.log($scope.user);
			//TODO store userid and hash in cookies or in service for next controller
		}, function(response){
			console.error(response);
			//TODO show failure in login form
		});
	};
	
	$scope.isInvalid = function(user) {
		var invalid = typeof user.password == "undefined"
			|| user.password != user.passwordConfirm 
			|| user.username  == "undefined"
			|| user.username != user.email;
		return invalid;
	};
	
	/*
	$scope.isUnchanged(user) {
		return angular.equals(user, $scope.user);
	};
	*/
	
}

function UserEditCtrl($scope, $routeParams) {

}
