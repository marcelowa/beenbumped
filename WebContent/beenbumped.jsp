<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="he-IL" xml:lang="he-IL" ng-app="beenbumped">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=100" />
<meta charset="utf-8" />
<title>Beenbumped Application Client</title>
<link rel="stylesheet" href="/beenbumped/lib/bootstrap/css/bootstrap.css">
<link rel="stylesheet" href="/beenbumped/lib/bootstrap/css/bootstrap-responsive.css">
<script src="/beenbumped/lib/jquery/jquery-2.0.2.js"></script>
<script src="/beenbumped/lib/jquery/jquery.cookie.js"></script>
<script src="/beenbumped/lib/angular/angular.min.js"></script>
<script src="/beenbumped/lib/angular/angular-resource.min.js"></script>
<script src="/beenbumped/js/controllers.js"></script>
<script src="/beenbumped/js/services.js"></script>
<script src="/beenbumped/js/app.js"></script>
</head>
<body>
<div class="container" ng-view></div>
</body>
</html>