<div class="user-login">
	<form name="login-form" class="form-horizontal">
		<div class="control-group">
			<label class="control-label" for="username">Email</label>
			<div class="controls">
				<input type="text" ng-model="login.username" name="username" required>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="password">Password</label>
			<div class="controls">
				<input type="text" ng-model="login.password" name="password" required>
			</div>
		</div>
		<div class="control-group">
			<div class="controls">
				<button class="btn" ng-click="authenticate(login)">login</button>
			</div>
		</div>
	</form>
	<br />
	or <a href="#!/user/register">register</a>
</div>