package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import db.MySql;
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
			e.printStackTrace();
			return null;
		}
	}
	
	public User authenticate(String username, String password) {
		//TODO completely change this to return an authentication object instead of a user
		try {
			CallableStatement callable = connection.prepareCall("call sp_getUserByUsernameAndPassword(?,?)");
			callable.setString(1, username);
			callable.setString(2, password);
			ResultSet result = callable.executeQuery(); 
			User user = getByResult(result);
			
			return user;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean save(User user) {
		// insert the user to the db
		try {

			CallableStatement callable;
			int i = 0;
			if (0 < user.getUserId() && 0 < user.getPersonId()) {
				callable = connection.prepareCall("call sp_updateUser(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				callable.setInt(++i,user.getPersonId());
				callable.setInt(++i,user.getUserId());
			}
			else {
				callable = connection.prepareCall("call sp_createUser(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
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
			callable.setString(++i,user.getUsername());
			callable.setString(++i,user.getPassword()); //TODO password should be md5ed before inserting or updating
			callable.registerOutParameter(++i, java.sql.Types.INTEGER);
			callable.registerOutParameter(++i, java.sql.Types.INTEGER);
			callable.execute();
			
			if (0 < user.getUserId() && 0 < user.getPersonId()) { // update mode
				int rowsUpdatedPerson = callable.getInt(i-1);
				int rowsUpdatedUser = callable.getInt(i);
				if (1 != rowsUpdatedPerson || 1 != rowsUpdatedUser) {
					//TODO should roll back if rowsUpdated greater then 1
					return false;
				}
			}
			else { // insert mode
				int personId = callable.getInt(i-1);
				int userId = callable.getInt(i);
				user.setUserId(userId);
				user.setPersonId(personId);
			}
			
			return true;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private User getByResult(ResultSet result) {
		User user = new User();
		try {
			if (!result.first()) {
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
			e.printStackTrace();
			return null;
		}
		return user;
	}

	/** connects to the DB and checks if the password matches the user */
	public boolean authenticate(User user, String password) throws Exception {
		CallableStatement callableS = null;
		// authenticate user password with the db
		try {

			// Statements allow to issue SQL queries to the database
			/*
			 * Example Call: Example call: SET @uId = 1; SET @pass =
			 * '6F9619FF-8B86-D011-B42D-00C04FC964FF'; PREPARE s FROM 'CALL
			 * usp_userAuthentication(@uId, @pass,@result)'; EXECUTE s; SELECT
			 * @uId, @pass,@result; )
			 */
			callableS = connection.prepareCall("? = call usp_userAuthentication (?,?)");

			callableS.registerOutParameter(1, java.sql.Types.BOOLEAN);
			callableS.setInt(2, user.getUserId());
			callableS.setString(3, password);

			callableS.execute();

			boolean result = callableS.getBoolean(1);

			return result;

		} catch (Exception e) {
			throw e;
		} finally {
			if (callableS != null) {
				callableS.close();
			}
		}
	}

	/*
	 * public User get(int id) { User user = new User(); return user; } public
	 * boolean create(User user) { // insert the user to the db return false; }
	 * 
	 * public boolean update(User user) { // update the user to the db return
	 * false; }
	 */

	static public UserDao getInstance() {
		if (null == instance) {
			instance = new UserDao();
		}
		return instance;
	}

}
