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
}).factory("User", function($resource, $http){
	return $resource("/beenbumped/rest/user/:userId");
}).factory('registry', function() {
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
});
