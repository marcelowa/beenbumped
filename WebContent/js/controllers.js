'use strict';

function MenuCtrl($scope, $location, User, registry) {
	$scope.user = registry.fetch('user', null);
	if (!$scope.user) {
		var login = null;
		try {
			login = angular.fromJson($.cookie('login') || null);			
		} catch(e) {}
		if (!login || !login.userId || !login.authHash) {
			return $location.path("/user/login");
		}
		
		$scope.user = User.get(login, function(response) {
			registry.store('user', $scope.user);
		}, function(response) {
			console.log('or here?');
			return $location.path("/user/login");
		});
	}
}

function UserLoginCtrl($scope, $location, User, registry) {
	$scope.login = {};
	$.cookie('login', "" , {expires: -1});
	registry.store('user', null);
	$scope.authenticate = function(login) {
		$scope.user = User.get({username: login.username, password: login.password}, function(response){
			registry.store('user', $scope.user);
			$.cookie('login', angular.toJson({userId:$scope.user.userId, authHash: $scope.user.authHash}), { expires: 1 });
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
			$.cookie('login', angular.toJson({userId:$scope.user.userId, authHash: $scope.user.authHash}), { expires: 1 });
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
