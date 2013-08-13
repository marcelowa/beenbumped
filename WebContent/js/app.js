'use strict';

angular.module('beenbumped', ['beenbumpedServices', 'ngResource']).
config(['$routeProvider','$locationProvider', function($routeProvider, $locationProvider) {
	$routeProvider
		.when('/menu', {templateUrl: 'partials/menu.jsp', controller: MenuCtrl})
		.when('/user/login', {templateUrl: 'partials/user-login.jsp', controller: UserLoginCtrl})
		.when('/user/register', {templateUrl: 'partials/user-register.jsp', controller: UserRegisterCtrl})
		.when('/user/edit', {templateUrl: 'partials/user-edit.jsp', controller: UserEditCtrl})
		.when('/incident/edit', {templateUrl: 'partials/incident-edit.jsp', controller: IncidentEditCtrl})
		.when('/incident/edit/:incidentId', {templateUrl: 'partials/incident-edit.jsp', controller: IncidentEditCtrl})
		.when('/incident/history', {templateUrl: 'partials/incident-history.jsp', controller: IncidentHistoryCtrl})
		.otherwise({redirectTo: '/menu'});
	
	$locationProvider.hashPrefix('!');
}]);