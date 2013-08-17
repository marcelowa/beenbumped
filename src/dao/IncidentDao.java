package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import db.MySql;
import entities.Incident;
import entities.Person;
import entities.ResourceError;
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
			incident.setDate(new Date(result.getTimestamp("date").getTime()));
			incident.setNotes(result.getString("notes"));
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
			callable.setTimestamp(++i, new java.sql.Timestamp(incident.getDate().getTime()));
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
	 *		@param page short
	 * Returns:
	 * 		@return IncidentPage
	 * Throws:
	 * 		@exception SQLException
	 * */
	public IncidentPage getIncidentHistory(int userId, int page, int linesInPage){
		IncidentPage result = new IncidentPage();
		Incident[] incidentsTmp = new Incident[linesInPage];
		Incident[] incidents;
		short totalLines;
		CallableStatement callable;
		ResultSet rs;
		int counter = 0;
		Incident incident;
		PersonDao personDao = PersonDao.getInstance();
		int driverId;
		int ownerId;
		Person driver;
		Person owner;
		ResourceError err = ResourceError.getInstance();

		try {
			callable = connection.prepareCall("call sp_getIncidentHistory(?,?,?,?)");

			callable.setInt(1,userId);
			callable.setInt(2,page);
			callable.setInt(3,linesInPage);
			callable.registerOutParameter(4, java.sql.Types.SMALLINT);

			rs=callable.executeQuery();
			totalLines = callable.getShort("numberOfLines");

			while(rs.next()) {
				incident = new Incident();
				incidentsTmp[counter] = incident;
				incident.setIncidentId(rs.getInt("incidentId"));
				incident.setUserId(rs.getInt("userId"));
				incident.setDate(new Date(rs.getTimestamp("date").getTime()));
				incident.setNotes(rs.getString("notes"));
				incident.setLocation(rs.getString("location"));
				incident.setVehicleLicensePlate(rs.getString("vehicleLicensePlate"));
				incident.setVehicleBrand(rs.getString("vehicleBrand"));
				incident.setVehicleModel(rs.getString("vehicleModel"));


				driverId = rs.getInt("driverPersonId");
				ownerId = rs.getInt("ownerPersonId");
				
				
				if (0 < driverId) {
					driver = personDao.getById(driverId);
					if (null != driver) {
						incident.setDriver(driver);
					}
				}
				
				if (0 < ownerId) {
					owner = personDao.getById(ownerId);
					if (null != owner) {
						incident.setOwner(owner);
					}
				}

				counter++;
			}
			incidents = new Incident[counter];
			int i = 0;
			for (i=0;i<counter;i++) {
				incidents[i]=incidentsTmp[i];
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
