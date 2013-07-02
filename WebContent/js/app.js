'use strict';

angular.module('beenbumped', ['beenbumpedServices', 'ngResource']).
config(['$routeProvider','$locationProvider', function($routeProvider, $locationProvider) {
	$routeProvider
		.when('/menu', {templateUrl: 'partials/menu.html', controller: MenuCtrl})
		.when('/user/login', {templateUrl: 'partials/user-login.html', controller: UserLoginCtrl})
		.when('/user/register', {templateUrl: 'partials/user-register.html', controller: UserRegisterCtrl})
		.when('/user/edit', {templateUrl: 'partials/user-edit.html', controller: UserEditCtrl})
		.otherwise({redirectTo: '/user/login'});
	
	$locationProvider.hashPrefix('!');
}]);