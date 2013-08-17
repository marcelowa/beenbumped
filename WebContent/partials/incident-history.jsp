<div class="incident-history">
	<jsp:include page="navbar.jsp" />
	<h1>Incidents</h1>
	
	<div class="navbar navbar-fixed-bottom">
		<div class="navbar-inner">
			<a class="btn" href="#!/menu">Menu</a>
			<a class="btn" href="#!/incident/edit">New Incident</a>
		</div>
	</div>
	<table class="table table-striped">
		<tr  ng-repeat="incident in incidentsDisplay">
			<td><div><small>{{incident.incidentDate}}</small> {{incident.text}}</div></td>
			<td><div><a class="btn btn-small btn-primary" href="{{incident.url}}">edit</a></div></td>
		</tr>
	</table>
</div>