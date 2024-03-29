<div class="user-edit">
	<h1>User</h1>
	<form name="user-form" class="form-horizontal" >
		<div class="navbar navbar-fixed-bottom">
			<div class="navbar-inner">
				<button class="btn" ng-click="cancel()" >Cancel</button>
				<button class="btn" ng-click="save(user)" ng-disabled="form.$invalid || isInvalid(user)">Save</button>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="first-name">First Name</label>
			<div class="controls">
				<input type="text" ng-model="user.firstName" name="first-name" id="first-name">
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label" for="last-name">Last Name</label>
			<div class="controls">
				<input type="text" ng-model="user.lastName" name="last-name" id="last-name">
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label" for="id-number">ID Number</label>
			<div class="controls">
				<input type="text" ng-model="user.idNumber" name="id-number" id="id-number">
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label" for="city">City</label>
			<div class="controls">
				<input type="text" ng-model="user.city" name="city" id="city">
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label" for="street-name">Street Name</label>
			<div class="controls">
				<input type="text" ng-model="user.streetName" name="street-name" id="street-name">
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label" for="house-number">House Number</label>
			<div class="controls">
				<input type="text" ng-model="user.houseNumber" name="house-number" id="house-number">
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label" for="address-details">Address Details</label>
			<div class="controls">
				<input type="text" ng-model="user.addressDetails" name="address-details" id="address-details">
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label" for="zipcode">Zipcode</label>
			<div class="controls">
				<input type="text" ng-model="user.zipcode" name="zipcode" id="zipcode">
			</div>
		</div>
	
		<div class="control-group">
			<label class="control-label" for="phone1">Phone 1</label>
			<div class="controls">
				<input type="text" ng-model="user.phone1" name="phone1" id="phone1">
			</div>
		</div>
	
		<div class="control-group">
			<label class="control-label" for="phone2">Phone 2</label>
			<div class="controls">
				<input type="text" ng-model="user.phone2" name="phone2" id="phone2">
			</div>
		</div>
	
		<div class="control-group">
			<label class="control-label" for="insurance-company">Insurance Company</label>
			<div class="controls">
				<input type="text" ng-model="user.insuranceCompany" name="insurance-company" id="insurance-company">
			</div>
		</div>
	
		<div class="control-group">
			<label class="control-label" for="insurance-agentName">Insurance AgentName</label>
			<div class="controls">
				<input type="text" ng-model="user.insuranceAgentName" name="insurance-agentName" id="insurance-agentName">
			</div>
		</div>
	
		<div class="control-group">
			<label class="control-label" for="insurance-phone1">Insurance Phone 1</label>
			<div class="controls">
				<input type="text" ng-model="user.insurancePhone1" name="insurance-phone1" id="insurance-phone1">
			</div>
		</div>
	
		<div class="control-group">
			<label class="control-label" for="insurance-phone2">Insurance Phone 2</label>
			<div class="controls">
				<input type="text" ng-model="user.insurancePhone2" name="insurance-phone2" id="insurance-phone2">
			</div>
		</div>
	
		<div class="control-group">
			<label class="control-label" for="insurance-number">Insurance Number</label>
			<div class="controls">
				<input type="text" ng-model="user.insuranceNumber" name="insurance-number" id="insurance-number">
			</div>
		</div>

	</form>
</div>