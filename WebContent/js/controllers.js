'use strict';

function MenuCtrl($scope, $location, UserProvider, registry) {
	UserProvider.authorize(function(user){
		$scope.user = user;
	}, function() {
		return $location.path("/user/login");
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

function UserEditCtrl($scope, $location, $filter, User, UserProvider, registry) {
	UserProvider.authorize(function(user){
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
	}, function() {
		return $location.path("/user/login");
	});
}

function IncidentEditCtrl($scope, $location, $routeParams, $filter, Incident, UserProvider, registry) {
	UserProvider.authorize(function(user){
		$scope.user = user;
		$scope.params = $routeParams;
		if (!$scope.user) {
			return $location.path("/user/login");
		}
		
		$('#incident-date').datetimepicker({
			'autoclose' : true,
			'forceParse' : true,
		});
		
		$scope.incident = {
			incidentId : $scope.params.incidentId || -1,
			userId : $scope.user.userId,
			authHash : $scope.user.authHash
		};
		
		if (0 < $scope.params.incidentId) {
			Incident.get($scope.incident, function(response) {
				$scope.incident = response;
				$scope.incident['userId'] = $scope.user.userId;
				$scope.incident['authHash'] = $scope.user.authHash;
			}, function(response) {
				console.error(response);
			});
		}
		else {
			//$scope.incident.date = new Date().toJSON().substring(0, 16).replace("T"," ");
		}
		
		$scope.save = function(incident) {
			$scope.incident.date = $('#incident-date').val();
			$scope.incident = Incident.save(incident, function(response){
				$location.path("/incident/history");
			}, function(response){
				console.error(response);
				//TODO show failure in edit form
			});
		};
		
		$scope.cancel = function() {
			window.history.back();
		};
		
		$scope.isInvalid = function(user) {
			return false;
		};
	}, function() {
		return $location.path("/user/login");
	});
}

function IncidentHistoryCtrl($scope, $location, $filter, Incident, UserProvider, registry) {
	UserProvider.authorize(function(user){
		$scope.user = user;
		if (!$scope.user) {
			return $location.path("/user/login");
		}
		
		$scope.req = {
			userId : $scope.user.userId,
			authHash : $scope.user.authHash
		};
		$scope.incidentsDisplay = [];
		Incident.history($scope.req, function(response) {
			$scope.incidents = response;
			var incident;
			var incidentTextArr;
			for (var i=0,l = $scope.incidents.incidents.length;i<l;i++) {
				incident = $scope.incidents.incidents[i];
				incidentTextArr = [
				                   incident.date || null
				                   , incident.vehicleBrand
				                   , incident.vehicleModel || null
				                   , incident.driverFirstName  || null
				                   , incident.driverLastName  || null
				                   , incident.ownerFirstName  || null
				                   , incident.ownerLastName  || null
				                   , incident.notes  || null
				                   ];
				$scope.incidentsDisplay.push({
					incidentId : incident.incidentId,
					text : incidentTextArr.join(' '),
					url : '#!/incident/edit/'+incident.incidentId
				});
			}
		}, function(response) {
			console.error(response);
		});
	}, function() {
		return $location.path("/user/login");
	});
}
