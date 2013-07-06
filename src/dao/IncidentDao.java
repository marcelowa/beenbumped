package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import db.MySql;
import entities.Incident;
import entities.Person;
import entities.ResourceError;

public class IncidentDao {

	static private IncidentDao instance = null;
	private Connection connection;

	protected IncidentDao() {
		connection = MySql.getInstance().getConnection();
	}

	public boolean save(Incident incident) {
		// insert or update the incident to the db
		try {
			CallableStatement callable;
			int i = 0;
			boolean updateMode = false;
			PersonDao personDao = PersonDao.getInstance();
			Person driver = incident.getDriver();
			Person owner = incident.getOwner();
			
			if (driver != null) {
				personDao.save(driver);
			}
			if (owner != null) {
				personDao.save(owner);
			}
			
			if (0 < incident.getIncidentId()) {
				updateMode = true;
				callable = connection.prepareCall("call sp_updateIncident(?,?,?,?,?,?,?,?,?,?,?)");
				callable.setInt(++i,incident.getIncidentId());
			}
			else {
				callable = connection.prepareCall("call sp_createIncident(?,?,?,?,?,?,?,?,?,?)");
			}
			
			callable.setInt(++i,incident.getUserId());
			callable.setDate(++i, new java.sql.Date(incident.getDate().getTimeInMillis()));
			callable.setString(++i,incident.getNotes());
			callable.setString(++i,incident.getLocation());
			callable.setString(++i,incident.getVehicleLicensePlate());
			callable.setString(++i,incident.getVehicleBrand());
			callable.setString(++i,incident.getVehicleModel());
			
			if (null == driver) {
				callable.setNull(++i, Types.INTEGER);	
			}
			else {
				callable.setInt(++i, driver.getPersonId());
			}
			
			if (null == owner) {
				callable.setNull(++i, Types.INTEGER);	
			}
			else {
				callable.setInt(++i, owner.getPersonId());
			}

			callable.registerOutParameter(++i, java.sql.Types.INTEGER); // register incidentIdOut or rowsUpdatedOut

			callable.execute();
			
			if (updateMode) { // update mode
				int rowsUpdatedOut = callable.getInt("rowsUpdatedOut");
				if (1 != rowsUpdatedOut) {
					ResourceError err = ResourceError.getInstance();
					if (!err.isSet()) {
						err.setMessage("incident update failed, invalid input");
						err.setStatusCode(400);
						err.setReasonCode(ResourceError.REASON_INVALID_INPUT);
					}
					return false;
				}
			}
			else { // insert mode
				int incidentId = callable.getInt("incidentIdOut");
				incident.setIncidentId(incidentId);
			}
			return true;
		}
		catch (SQLException e) {
			ResourceError err = ResourceError.getInstance();
			if (!err.isSet()) {
				err.setMessage("internal error");
				err.setStatusCode(500);
				err.setReasonCode(ResourceError.REASON_UNKNOWN);
			}
			return false;
		}
	}
	
	static public IncidentDao getInstance() {
		if (null == instance) {
			instance = new IncidentDao();
		}
		return instance;
	}

}
