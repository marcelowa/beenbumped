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

function UserRegisterCtrl($scope) {

}

function UserEditCtrl($scope, $routeParams, $cookies) {

}
