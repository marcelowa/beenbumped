<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="he-IL" xml:lang="he-IL" ng-app>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=100" />
<meta charset="utf-8" />
<title>Beenbumped Application Client</title>
<link rel="stylesheet" href="/beenbumped/css/normalize.css">
<script src="//code.jquery.com/jquery-2.0.0.min.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/angularjs/1.0.7/angular.min.js"></script>
<script src="/beenbumped/js/controllers.js"></script>
</head>
<body ng-controller="testCtrl">
	Search: <input ng-model="query.name">
	<ul>
		<li ng-repeat="stamx in stam | filter:query">
			{{stamx.name}}<p>{{stamx.desc}}</p>
		</li>
	</ul>
</body>
</html>