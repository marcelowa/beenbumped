package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import resources.ResourceException;
import db.MySql;
import entities.Incident;
import entities.Person;
import entities.ResourceError;
import dao.UserDao;
import entities.IncidentPage;

public class IncidentDao {

	static private IncidentDao instance = null;
	private Connection connection;

	protected IncidentDao() {
		connection = MySql.getInstance().getConnection();
	}

	/**Return a Incident object given an id
	 * Parameters:
	 * 		@param incidentId integer
	 * 		@param userId integer
	 * Returns:
	 * 		@return Incident
	 * Throws:
	 * 		@exception SQLException
	 * */
	public Incident getById(int incidentId, int userId) {
		Incident incident = new Incident();
		ResourceError err = ResourceError.getInstance();
		try {
			CallableStatement callable = connection.prepareCall("call sp_getIncidentById(?, ?)");
			callable.setInt(1, incidentId);
			callable.setInt(2, userId);
			ResultSet result = callable.executeQuery(); 
			if (!result.first()) {
				return null;
			}

			incident.setIncidentId(result.getInt("incidentId"));
			incident.setUserId(result.getInt("userId"));
			incident.setDate(result.getDate("date"));
			incident.setVehicleLicensePlate(result.getString("vehicleLicensePlate"));
			incident.setVehicleBrand(result.getString("vehicleBrand"));
			incident.setVehicleModel(result.getString("vehicleModel"));
			
			int driverId = result.getInt("driverPersonId");
			int ownerId = result.getInt("ownerPersonId");
			
			PersonDao personDao = PersonDao.getInstance();
			if (0 < driverId) {
				Person driver = personDao.getById(driverId);
				if (null != driver) {
					incident.setDriver(driver);
				}
			}
			
			if (0 < ownerId) {
				Person owner = personDao.getById(ownerId);
				if (null != owner) {
					incident.setOwner(owner);
				}
			}
			
			return incident;
		}
		catch (SQLException e) {
			//e.printStackTrace();
			err.setMessage("internal error");
			err.setStatusCode(500);
			err.setReasonCode(ResourceError.REASON_UNKNOWN);
			return null;
		}
	}
	
	/**Insert a Incident object to the DB
	 * Parameters:
	 * 		@param incident Incident
	 * Returns:
	 * 		@return boolean
	 * Throws:
	 * 		@exception SQLException
	 * */
	public boolean save(Incident incident) {
		// insert or update the incident to the db
		ResourceError err = ResourceError.getInstance();
		try {
			CallableStatement callable;
			int i = 0;
			boolean updateMode = false;
			PersonDao personDao = PersonDao.getInstance();
			Person driver = incident.getDriver();
			Person owner = incident.getOwner();
			
			if (driver != null) {
				personDao.save(driver);
				if (err.isSet()) {
					return false;
				}
				
			}
			
			if (owner != null) {
				personDao.save(owner);
				if (err.isSet()) {
					return false;
				}
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
			callable.setDate(++i, new java.sql.Date(incident.getDate().getTime()));
			callable.setString(++i,incident.getNotes());
			callable.setString(++i,incident.getLocation());
			callable.setString(++i,incident.getVehicleLicensePlate());
			callable.setString(++i,incident.getVehicleBrand());
			callable.setString(++i,incident.getVehicleModel());
			callable.setInt(++i, null == driver ? 0 : driver.getPersonId());
			callable.setInt(++i, null == owner ? 0 : owner.getPersonId());

			callable.registerOutParameter(++i, java.sql.Types.INTEGER); // register incidentIdOut or rowsUpdatedOut

			callable.execute();
			
			if (updateMode) { // update mode
				int rowsUpdatedOut = callable.getInt("rowsUpdatedOut");
				if (1 != rowsUpdatedOut) {
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
			if (!err.isSet()) {
				err.setMessage("internal error");
				err.setStatusCode(500);
				err.setReasonCode(ResourceError.REASON_UNKNOWN);
			}
			return false;
		}
	}
	
	/**Return an Incident page from the DB
	 * Parameters:
	 * 		@param userId integer
	 * 		@param authHash String
	 * 		@param linesInPage short
	 *		@param pageNumber short
	 * Returns:
	 * 		@return IncidentPage
	 * Throws:
	 * 		@exception SQLException
	 * */
	public IncidentPage getIncidentHistory(int userId, String authHash, short linesInPage, short pageNumber){
		IncidentPage result = new IncidentPage();
		Incident[] incidents;
		incidents = new Incident[linesInPage];
		short totalLines;
		CallableStatement callable;
		ResultSet rs;
		UserDao userDao = UserDao.getInstance();
		ResourceError err = ResourceError.getInstance();
		// validate input:			
		if (!userDao.isAuthorized(userId, authHash)){
			err.setMessage("unauthorized reason:mismatch userId, authHash");
			err.setStatusCode(400);
			err.setReasonCode(ResourceError.REASON_AUTHENTICATION_FAILED);
			throw new ResourceException(err);
		}
		try {
			callable = connection.prepareCall("call sp_createIncident(?,?,?,?)");

			callable.setInt(1,userId);
			callable.setShort(2,linesInPage);
			callable.setShort(3,pageNumber);
			callable.registerOutParameter(3, java.sql.Types.SMALLINT);

			rs=callable.executeQuery();
			totalLines = rs.getShort("numberOfLines");

			int counter = 0;
			//incidentId, userId, date, notes, location, vehicleLicensePlate, vehicleBrand, vehicleModel, driverPersonId, ownerPersonId
			while(rs.next()) {
				incidents[counter].setIncidentId(rs.getInt("incidentId"));
				incidents[counter].setUserId(rs.getInt("UserId"));
				incidents[counter].setDate(rs.getDate("Date")); 
				incidents[counter].setNotes(rs.getString("Notes"));
				incidents[counter].setLocation(rs.getString("Location"));
				incidents[counter].setVehicleLicensePlate(rs.getString("VehicleLicensePlate"));
				incidents[counter].setVehicleBrand(rs.getString("VehicleBrand"));
				incidents[counter].setVehicleModel(rs.getString("VehicleModel"));
				incidents[counter].setDriver(PersonDao.getInstance().getById(rs.findColumn("driverPersonId")));
				incidents[counter].setOwner(PersonDao.getInstance().getById(rs.findColumn("ownerPersonId")));

				counter++;
			}
			result = new IncidentPage(incidents,totalLines);

		}
		catch (SQLException e) {
			if (!err.isSet()) {
				err.setMessage("internal error");
				err.setStatusCode(500);
				err.setReasonCode(ResourceError.REASON_UNKNOWN);

			}
			return null;
		}

		return result;
	}
	
	/**Handle a instance object, created so we won't have to use static methods 
	 * Return:
	 * 		@return User
	 * */
	static public IncidentDao getInstance() {
		if (null == instance) {
			instance = new IncidentDao();
		}
		return instance;
	}

}
