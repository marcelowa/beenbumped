'use strict';

angular.module('beenbumped', ['beenbumpedServices', 'ngResource']).
config(['$routeProvider','$locationProvider', function($routeProvider, $locationProvider) {
	$routeProvider
		.when('/menu', {templateUrl: 'partials/menu.html', controller: MenuCtrl})
		.when('/user/login', {templateUrl: 'partials/user-login.html', controller: UserLoginCtrl})
		.when('/user/register', {templateUrl: 'partials/user-register.html', controller: UserRegisterCtrl})
		.when('/user/edit', {templateUrl: 'partials/user-edit.html', controller: UserEditCtrl})
		.when('/incident/edit', {templateUrl: 'partials/incident-edit.html', controller: IncidentEditCtrl})
		.when('/incident/edit/:incidentId', {templateUrl: 'partials/incident-edit.html', controller: IncidentEditCtrl})
		.when('/incident/history', {templateUrl: 'partials/incident-history.html', controller: IncidentHistoryCtrl})
		.otherwise({redirectTo: '/menu'});
	
	$locationProvider.hashPrefix('!');
}]);