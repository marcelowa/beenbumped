<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="he-IL" xml:lang="he-IL" ng-app="beenbumped">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=100" />
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<title>Beenbumped Application Client</title>
<link rel="stylesheet" href="lib/bootstrap/css/bootstrap.css">
<link rel="stylesheet" href="lib/bootstrap/css/bootstrap-responsive.css">
<link rel="stylesheet" href="lib/bootstrap-datetimepicker/css/datetimepicker.css">
<link rel="stylesheet" href="css/main.css">
<script src="lib/jquery/jquery-2.0.2.js"></script>
<script src="lib/jquery/jquery.cookie.js"></script>
<script src="lib/angular-1.1.5/angular.js"></script>
<script src="lib/angular-1.1.5/angular-resource.min.js"></script>
<script src="lib/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script src="js/controllers.js"></script>
<script src="js/services.js"></script>
<script src="js/app.js"></script>
</head>
<body>
<div class="container beenbumped" ng-view></div>
</body>
</html>