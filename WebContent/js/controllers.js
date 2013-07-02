'use strict';

function MenuCtrl($scope, registry) {
	$scope.user = registry.fetch('user') || {};
	console.log($scope.user);
}

function UserLoginCtrl($scope, $location, $filter, User, registry) {
	$scope.login = {};
	
	$scope.authenticate = function(login) {
		$scope.user = User.get({username: login.username, password: login.password}, function(response){
			registry.store('user', $scope.user);
			$.cookie('login', $filter('json')({userId:$scope.user.userId, authHash: $scope.user.authHash}), { expires: 1 });
			$location.path("/menu");
		}, function(response){
			console.error(response);
			//TODO show failure in login form
		});
	};
}

function UserRegisterCtrl($scope, $location, $filter, User, registry) {
	$scope.user = {
		username : "marc@walla.com",
		email : "marc@walla.com",
		password : "stamstam",
		passwordConfirm : "stamstam"
	};
	
	$scope.register = function(user) {
		$scope.user = User.save(user, function(response){
			registry.store('user', $scope.user);
			$.cookie('login', $filter('json')({userId:$scope.user.userId, authHash: $scope.user.authHash}), { expires: 1 });
			$location.path("/menu");
		}, function(response){
			console.error(response);
			//TODO show failure in register form
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
