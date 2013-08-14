<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="he-IL" xml:lang="he-IL" ng-app="beenbumped">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=100" />
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<title>Beenbumped Application Client</title>
<link rel="stylesheet" href="/beenbumped/lib/bootstrap/css/bootstrap.css">
<link rel="stylesheet" href="/beenbumped/lib/bootstrap/css/bootstrap-responsive.css">
<link rel="stylesheet" href="/beenbumped/lib/bootstrap-datetimepicker/css/datetimepicker.css">
<link rel="stylesheet" href="/beenbumped/css/main.css">
<script src="/beenbumped/lib/jquery/jquery-2.0.2.js"></script>
<script src="/beenbumped/lib/jquery/jquery.cookie.js"></script>
<script src="/beenbumped/lib/angular-1.1.5/angular.js"></script>
<script src="/beenbumped/lib/angular-1.1.5/angular-resource.min.js"></script>
<script src="/beenbumped/lib/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script src="/beenbumped/js/controllers.js"></script>
<script src="/beenbumped/js/services.js"></script>
<script src="/beenbumped/js/app.js"></script>
</head>
<body>
<div class="container beenbumped" ng-view></div>
</body>
</html>