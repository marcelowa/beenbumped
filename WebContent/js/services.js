'use strict';

angular
	.module('beenbumpedServices', ['ngResource'])
	.factory('User', function($resource){
		return $resource('/beenbumped/rest/user');
	});
