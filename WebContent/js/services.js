'use strict';

angular.module("beenbumpedServices", ["ngResource"]).config(function ($httpProvider) {
    $httpProvider.defaults.transformRequest = function (data) {
        var str = [];
        for (var p in data) {
            data[p] !== undefined && str.push(encodeURIComponent(p) + "=" + encodeURIComponent(data[p]));
        }
        return str.join('&');
    };
    $httpProvider.defaults.headers.put["Content-Type"] = $httpProvider.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded; charset=UTF-8";
})

.factory("User", function($resource, $http){
	return $resource("/beenbumped/rest/user/:userId");
})

/**
 * incident resource service
 */
.factory("Incident", function($resource, $http) {
	
	/**
	 * utility function
	 * flatten an incident:
	 * driver and owner arrive inside inner objects so we flatten them
	 * we also add all the other key values to $scope.incident
	 */
	var flattenIncident = function(incidentOrig) {

		var incident = {};
		angular.forEach(incidentOrig, function(value, key) {
			switch (true) {
				case key == 'date':
					
					break;
				case (key.match(/^(?:driver|owner)$/) && typeof value == 'object'):
					angular.forEach(value, function(innerValue, innerKey) {
						innerKey = key + (innerKey[0].toUpperCase() + innerKey.substring(1));
						this[innerKey] = innerValue;
					}, incident);
					break;
				default:
					this[key] = value;
			}

		}, incident);
		incident['date'] = 0;
		
		return incident;
	};
	
	return $resource("/beenbumped/rest/incident/:incidentId", {}, {
		
		get : {
			method:'GET',
			transformResponse : function(data) {
				var dataObj = angular.fromJson(data);
				var incident = flattenIncident(dataObj);
				return incident;
			}
		},
		
		history : {
			method:'GET',
			params	: {incidentId:'history'},
			transformResponse : function(data) {
				var dataObj = angular.fromJson(data);
				var incidents = [];
				angular.forEach(dataObj.incidents, function(value, key) {
					incidents.push(flattenIncident(value));
				}, incidents);
				
				var incidentsObj = {
					'totalLines' : dataObj.totalLines,
					'incidents' : incidents
				};
				return incidentsObj;
			}
		}
	});
})

.factory('registry', function() {
	var store = [];
	return {
		fetch : function(key, defaultValue) {
			defaultValue = defaultValue || null;
			return store[key] || defaultValue;
		},
		store : function(key, value) {
			store[key] = value;
			return this;
		}
	};
})

.factory('UserProvider', function(User) {
	var user = null;
	
	return {
		resetUser : function() {
			user = null;
		},
		authorize : function(callback, failCallback) {
			if (!$.isFunction(callback)) {
				throw 'the callback is missing here';
			}

			if(user) {
				callback(user);
			}
			else {
				var login = null;
				try {
					login = angular.fromJson($.cookie('login') || null);			
				} catch(e) {;}
				if (!login || !login.userId || !login.authHash) {
					if ($.isFunction(failCallback)) {
						failCallback();
					}
					else {
						return false;
					}
				}
				
				user = User.get(login, function(response) {
					callback(user);
				}, function(response) {
					if ($.isFunction(failCallback)) {
						failCallback();
					}
					else {
						return false;
					}
				});
			}
		}
	};
})

;