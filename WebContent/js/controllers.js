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
	$.cookie('login', "" , {expires: -1});
	registry.store('user', null);
	$scope.user = {
		//username : "marc@walla.com",
		//email : "marc@walla.com",
		//password : "stamstam",
		//passwordConfirm : "stamstam"
	};
	
	$scope.save = function(user) {
		$scope.user = User.save(user, function(response){
			registry.store('user', $scope.user);
			$.cookie('login', angular.toJson({userId:$scope.user.userId, authHash: $scope.user.authHash}), { expires: 1 });
			$location.path("/menu");
		}, function(response){
			console.error(response);
			//TODO show failure in register form
		});
	};
	
	$scope.cancel = function() {
		$location.path("/user/login");
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

function UserEditCtrl($scope, $location, $filter, User, registry) {
	
	$scope.user = registry.fetch('user', null);
	if (!$scope.user) {
		return $location.path("/user/login");
	}
	
	$scope.save = function(user) {
		$scope.user = User.save(user, function(response){
			//registry.store('user', $scope.user);
			//$.cookie('login', angular.toJson({userId:$scope.user.userId, authHash: $scope.user.authHash}), { expires: 1 });
			$location.path("/menu");
		}, function(response){
			console.error(response);
			//TODO show failure in edit form
		});
	};
	
	$scope.cancel = function() {
		$location.path("/user/menu");
	};
	
	$scope.isInvalid = function(user) {
		return false;
	};
}

function IncidentEditCtrl($scope, $location, $routeParams, $filter, Incident, User, registry) {
	$scope.params = $routeParams;
	$scope.user = registry.fetch('user', null);
	if (!$scope.user) {
		return $location.path("/user/login");
	}
	
	$scope.incident = {
		incidentId : $scope.params.incidentId || -1,
		userId : $scope.user.userId,
		authHash : $scope.user.authHash
	};
	
	if (0 < $scope.params.incidentId) {
		Incident.get($scope.incident, function(response) {
			/**
			 * flatten response:
			 * driver and owner arrive inside inner objects so we flatten them
			 * we also add all the other key values to $scope.incident
			 */
			angular.forEach(response, function(value, key) {
				switch (true) {
					case key == 'date':
						
						break;
					case (key.match(/^(?:driver|owner)$/) && typeof value == 'object'):
						angular.forEach(value, function(innerValue, innerKey) {
							innerKey = key + (innerKey[0].toUpperCase() + innerKey.substring(1));
							this[innerKey] = innerValue;
						}, $scope.incident);
						break;
					default:
						this[key] = value;
				}

			}, $scope.incident);
			$scope.incident['date'] = 0;
		}, function(response) {
			console.error(response);
		});
	}
	
	
	$scope.save = function(incident) {
		$scope.incident = Incident.save(incident, function(response){
			//registry.store('user', $scope.user);
			//$.cookie('login', angular.toJson({userId:$scope.user.userId, authHash: $scope.user.authHash}), { expires: 1 });
			$location.path("/menu");
		}, function(response){
			console.error(response);
			//TODO show failure in edit form
		});
	};
	
	$scope.cancel = function() {
		$location.path("/user/menu");
	};
	
	$scope.isInvalid = function(user) {
		return false;
	};
	
}

function IncidentHistoryCtrl($scope, $location, $filter, Incident, User, registry) {
	
}
