'use strict';

function MenuCtrl($scope, $location, UserProvider, registry) {
	UserProvider.getUser(function(user){
		$scope.user = user;
			if (!$scope.user) {
				return $location.path("/user/login");
			}
	});

}

function UserLoginCtrl($scope, $location, User, UserProvider) {
	$scope.login = {};
	UserProvider.resetUser();
	$.cookie('login', "" , {expires: -1});
	$scope.authenticate = function(login) {
		$scope.user = User.get({username: login.username, password: login.password}, function(response){
			$.cookie('login', angular.toJson({userId:$scope.user.userId, authHash: $scope.user.authHash}), { expires: 1 });
			$location.path("/menu");
		}, function(response){
			console.error(response);
			//TODO show failure in login form
		});
	};
}

function UserRegisterCtrl($scope, $location, $filter, User, UserProvider) {
	UserProvider.resetUser();
	$.cookie('login', "" , {expires: -1});
	$scope.user = {
		//username : "marc@walla.com",
		//email : "marc@walla.com",
		//password : "stamstam",
		//passwordConfirm : "stamstam"
	};
	
	$scope.save = function(user) {
		$scope.user = User.save(user, function(response){
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

function UserEditCtrl($scope, $location, $filter, UserProvider, registry) {
	UserProvider.getUser(function(user){
		$scope.user = user;
		if (!$scope.user) {
			return $location.path("/user/login");
		}
		
		$scope.save = function(user) {
			$scope.user = User.save(user, function(response){
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
	});
}

function IncidentEditCtrl($scope, $location, $routeParams, $filter, Incident, UserProvider, registry) {
	UserProvider.getUser(function(user){
		$scope.user = user;
		$scope.params = $routeParams;
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
				$scope.incident = response;
			}, function(response) {
				console.error(response);
			});
		}
		
		$scope.save = function(incident) {
			$scope.incident = Incident.save(incident, function(response){
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
	});
}

function IncidentHistoryCtrl($scope, $location, $filter, Incident, UserProvider, registry) {
	UserProvider.getUser(function(user){
		$scope.user = user;
		if (!$scope.user) {
			return $location.path("/user/login");
		}
		
		$scope.req = {
			userId : $scope.user.userId,
			authHash : $scope.user.authHash
		};
		
		Incident.history($scope.req, function(response) {
			console.log(response);
		}, function(response) {
			console.error(response);
		});
	});
}
