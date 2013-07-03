package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import db.MySql;
import entities.ResourceError;
import entities.User;

public class UserDao {

	static private UserDao instance = null;
	private Connection connection;

	protected UserDao() {
		connection = MySql.getInstance().getConnection();
	}

	public User getById(int id) {
		try {
			CallableStatement callable = connection.prepareCall("call sp_getUserById(?)");
			callable.setInt(1, id);
			ResultSet result = callable.executeQuery(); 
			return getByResult(result);
		}
		catch (SQLException e) {
			ResourceError err = ResourceError.getInstance();
			err.setMessage("internal error");
			err.setStatusCode(500);
			err.setReasonCode(ResourceError.REASON_UNKNOWN);
			return null;
		}
	}
	
	public User authenticate(String username, String password) {
		try {
			CallableStatement callable = connection.prepareCall("call sp_authenticateUser(?,?,?,?)");
			callable.setString(1, username);
			callable.setString(2, password);
			callable.registerOutParameter(3, java.sql.Types.INTEGER);
			callable.registerOutParameter(4, java.sql.Types.VARCHAR);
			callable.execute(); 
			int userId = callable.getInt("userIdOut");
			String hashResult = callable.getString("hashResultOut");
			if (0 >= userId || "" == hashResult) {
				ResourceError err = ResourceError.getInstance();
				err.setMessage("authentication failed, wrong username or password");
				err.setStatusCode(400);
				err.setReasonCode(ResourceError.REASON_AUTHENTICATION_FAILED);
				return null;
			}
			User user = getById(userId);
			if (null == user) {
				ResourceError err = ResourceError.getInstance();
				if (!err.isSet()) {
					err.setMessage("user retrieval failed with provided input");
					err.setStatusCode(400);
					err.setReasonCode(ResourceError.REASON_INVALID_INPUT);
				}
				return null;
			}
			user.setAuthHash(hashResult);
			return user;
		}
		catch (SQLException e) {
			ResourceError err = ResourceError.getInstance();
			if (!err.isSet()) {
				err.setMessage("internal error");
				err.setStatusCode(500);
				err.setReasonCode(ResourceError.REASON_UNKNOWN);
			}
			return null;
		}
	}
	
	public boolean isAuthorized(int userId, String hash) {
		try {
			CallableStatement callable = connection.prepareCall("call sp_authorizeUser(?,?,?)");
			callable.setInt(1, userId);
			callable.setString(2, hash);
			callable.registerOutParameter(3, java.sql.Types.BOOLEAN);
			callable.execute(); 
			return callable.getBoolean("authResultOut");

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
	
	public boolean save(User user) {
		// insert the user to the db
		try {

			CallableStatement callable;
			int i = 0;
			boolean updateMode = false;
			if (0 < user.getUserId() && 0 < user.getPersonId()) {
				updateMode = true;
				callable = connection.prepareCall("call sp_updateUser(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				callable.setInt(++i,user.getPersonId());
				callable.setInt(++i,user.getUserId());
			}
			else {
				callable = connection.prepareCall("call sp_createUser(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			}
			
			callable.setString(++i,user.getEmail());
			callable.setString(++i,user.getFirstName());
			callable.setString(++i,user.getLastName());
			callable.setString(++i,user.getStreetName());
			callable.setString(++i,user.getCity());
			callable.setInt(++i,user.getHouseNumber());
			callable.setString(++i,user.getAddressDetails());
			callable.setInt(++i,user.getZipcode());
			callable.setString(++i,user.getPhone1());
			callable.setString(++i,user.getPhone2());
			callable.setString(++i,user.getInsuranceCompany());
			callable.setString(++i,user.getInsuranceAgentName());
			callable.setString(++i,user.getInsurancePhone1());
			callable.setString(++i,user.getInsurancePhone2());
			callable.setString(++i,user.getInsuranceNumber());
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
	
	private User getByResult(ResultSet result) {
		User user = new User();
		try {
			if (!result.first()) {
				ResourceError err = ResourceError.getInstance();
				err.setMessage("user retrieval failed with provided input");
				err.setStatusCode(400);
				err.setReasonCode(ResourceError.REASON_INVALID_INPUT);
				return null;
			}
			user.setUserId(result.getInt("userId"));
			user.setPersonId(result.getInt("personId"));
			user.setUsername(result.getString("username"));
			user.setPassword(result.getString("password"));
			user.setEmail(result.getString("email"));
			user.setFirstName(result.getString("firstName"));
			user.setLastName(result.getString("lastName"));
			user.setCity(result.getString("city"));
			user.setStreetName(result.getString("streetName"));
			user.setHouseNumber(result.getInt("houseNumber"));
			user.setAddressDetails(result.getString("addressDetails"));
			user.setZipcode(result.getInt("zipcode"));
			user.setPhone1(result.getString("phone1"));
			user.setPhone2(result.getString("phone2"));
			user.setInsuranceCompany(result.getString("insuranceCompany"));
			user.setInsuranceAgentName(result.getString("insuranceAgentName"));
			user.setInsurancePhone1(result.getString("insurancePhone1"));
			user.setInsurancePhone2(result.getString("insurancePhone2"));
			user.setInsuranceNumber(result.getString("insuranceNumber"));
		} catch (SQLException e) {
			ResourceError err = ResourceError.getInstance();
			err.setMessage("internal error");
			err.setStatusCode(500);
			err.setReasonCode(ResourceError.REASON_UNKNOWN);
			return null;
		}
		return user;
	}

	static public UserDao getInstance() {
		if (null == instance) {
			instance = new UserDao();
		}
		return instance;
	}

}
