package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
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
		// insert the user to the db
		try {
			CallableStatement callable;
			int i = 0;
			boolean updateMode = false;
			PersonDao personDao = PersonDao.getInstance();
			Person driver = incident.getDriver();
			Person owner = incident.getOwner();
			
			if (0 < incident.getIncidentId()) {
				updateMode = true;
				callable = connection.prepareCall("call sp_updateUser(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
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
			
			if (!updateMode) { // pass username to the sp only if we are saving it in the first time
				callable.setString(++i,user.getUsername());
				callable.setString(++i,user.getPassword()); //TODO password should be md5ed before inserting or updating
			}
			callable.registerOutParameter(++i, java.sql.Types.INTEGER); // register personIdOut or rowsUpdatedPersonOut (if in updateMode)
			callable.registerOutParameter(++i, java.sql.Types.INTEGER); // register userIdOut or rowsUpdatedUserOut (if in updateMode)
			if (!updateMode) {
				callable.registerOutParameter(++i, java.sql.Types.VARCHAR); // register authHashOut
			}
			callable.execute();
			
			if (updateMode) { // update mode
				int rowsUpdatedPerson = callable.getInt("rowsUpdatedPersonOut");
				int rowsUpdatedUser = callable.getInt("rowsUpdatedUserOut");
				//TODO should roll back if rowsUpdated greater then 1
				if (1 != rowsUpdatedPerson || 1 != rowsUpdatedUser) {
					ResourceError err = ResourceError.getInstance();
					if (!err.isSet()) {
						err.setMessage("user update failed, invalid input");
						err.setStatusCode(400);
						err.setReasonCode(ResourceError.REASON_INVALID_INPUT);
					}
					return false;
				}
			}
			else { // insert mode
				int personId = callable.getInt("personIdOut");
				int userId = callable.getInt("userIdOut");
				String authHash = callable.getString("authHashOut");
				user.setUserId(userId);
				user.setPersonId(personId);
				user.setAuthHash(authHash);
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
