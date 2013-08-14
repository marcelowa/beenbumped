<div class="incident-edit">
	<h1>Edit Incident</h1>
	<form name="incident-form" class="form-horizontal" >
		<div class="navbar navbar-fixed-bottom">
			<div class="navbar-inner">
				<button class="btn" ng-click="cancel()" >Cancel</button>
				<button class="btn" ng-click="save(incident)" ng-disabled="form.$invalid || isInvalid(incident)">Save</button>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label" for="incident-date">Date</label>
			<div class="controls">
				<input type="text" ng-model="incident.date" name="incident-date" id="incident-date" data-format="yyyy-MM-dd hh:ii">
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label" for="driver-first-name">Driver's First Name</label>
			<div class="controls">
				<input type="text" ng-model="incident.driverFirstName" name="driver-first-name" id="driver-first-name">
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label" for="driver-last-name">Driver's Last Name</label>
			<div class="controls">
				<input type="text" ng-model="incident.driverLastName" name="driver-last-name" id="driver-last-name">
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label" for="driver-id-number">Driver's ID Number</label>
			<div class="controls">
				<input type="text" ng-model="incident.driverIdNumber" name="driver-id-number" id="driver-id-number">
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label" for="driver-phone1">Driver's Phone 1</label>
			<div class="controls">
				<input type="text" ng-model="incident.driverPhone1" name="driver-phone1" id="driver-phone1">
			</div>
		</div>
	
		<div class="control-group">
			<label class="control-label" for="driver-phone2">Driver's Phone 2</label>
			<div class="controls">
				<input type="text" ng-model="incident.driverPhone2" name="driver-phone2" id="driver-phone2">
			</div>
		</div>
	
		<div class="control-group">
			<label class="control-label" for="driver-insurance-company">Driver's Insurance Company</label>
			<div class="controls">
				<input type="text" ng-model="incident.driverInsuranceCompany" name="driver-insurance-company" id="driver-insurance-company">
			</div>
		</div>
	
		<div class="control-group">
			<label class="control-label" for="driver-insurance-agentName">Driver's Insurance AgentName</label>
			<div class="controls">
				<input type="text" ng-model="incident.driverInsuranceAgentName" name="driver-insurance-agentName" id="driver-insurance-agentName">
			</div>
		</div>
	
		<div class="control-group">
			<label class="control-label" for="driver-insurance-phone1">Driver's Insurance Phone 1</label>
			<div class="controls">
				<input type="text" ng-model="incident.driverInsurancePhone1" name="driver-insurance-phone1" id="driver-insurance-phone1">
			</div>
		</div>
	
		<div class="control-group">
			<label class="control-label" for="driver-insurance-phone2">Driver's Insurance Phone 2</label>
			<div class="controls">
				<input type="text" ng-model="incident.driverInsurancePhone2" name="driver-insurance-phone2" id="driver-insurance-phone2">
			</div>
		</div>
	
		<div class="control-group">
			<label class="control-label" for="driver-insurance-number">Insurance Number</label>
			<div class="controls">
				<input type="text" ng-model="incident.driverInsuranceNumber" name="driver-insurance-number" id="driver-insurance-number">
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label" for="driver-email">Driver's Email</label>
			<div class="controls">
				<input type="email" ng-model="incident.driverEmail" name="driver-email" id="driver-email">
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label" for="owner-first-name">owner's First Name</label>
			<div class="controls">
				<input type="text" ng-model="incident.ownerFirstName" name="owner-first-name" id="owner-first-name">
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label" for="owner-last-name">owner's Last Name</label>
			<div class="controls">
				<input type="text" ng-model="incident.ownerLastName" name="owner-last-name" id="owner-last-name">
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label" for="owner-id-number">owner's ID Number</label>
			<div class="controls">
				<input type="text" ng-model="incident.ownerIdNumber" name="owner-id-number" id="owner-id-number">
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label" for="owner-phone1">owner's Phone 1</label>
			<div class="controls">
				<input type="text" ng-model="incident.ownerPhone1" name="owner-phone1" id="owner-phone1">
			</div>
		</div>
	
		<div class="control-group">
			<label class="control-label" for="owner-phone2">owner's Phone 2</label>
			<div class="controls">
				<input type="text" ng-model="incident.ownerPhone2" name="owner-phone2" id="owner-phone2">
			</div>
		</div>
	
		<div class="control-group">
			<label class="control-label" for="owner-insurance-company">owner's Insurance Company</label>
			<div class="controls">
				<input type="text" ng-model="incident.ownerInsuranceCompany" name="owner-insurance-company" id="owner-insurance-company">
			</div>
		</div>
	
		<div class="control-group">
			<label class="control-label" for="owner-insurance-agentName">owner's Insurance AgentName</label>
			<div class="controls">
				<input type="text" ng-model="incident.ownerInsuranceAgentName" name="owner-insurance-agentName" id="owner-insurance-agentName">
			</div>
		</div>
	
		<div class="control-group">
			<label class="control-label" for="owner-insurance-phone1">owner's Insurance Phone 1</label>
			<div class="controls">
				<input type="text" ng-model="incident.ownerInsurancePhone1" name="owner-insurance-phone1" id="owner-insurance-phone1">
			</div>
		</div>
	
		<div class="control-group">
			<label class="control-label" for="owner-insurance-phone2">owner's Insurance Phone 2</label>
			<div class="controls">
				<input type="text" ng-model="incident.ownerInsurancePhone2" name="owner-insurance-phone2" id="owner-insurance-phone2">
			</div>
		</div>
	
		<div class="control-group">
			<label class="control-label" for="owner-insurance-number">Insurance Number</label>
			<div class="controls">
				<input type="text" ng-model="incident.ownerInsuranceNumber" name="owner-insurance-number" id="owner-insurance-number">
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label" for="owner-email">owner's Email</label>
			<div class="controls">
				<input type="email" ng-model="incident.ownerEmail" name="owner-email" id="owner-email">
			</div>
		</div>
	</form>
</div>