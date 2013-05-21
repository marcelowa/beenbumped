package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import db.MySql;
import entities.User;

public class UserDao {

	static private UserDao instance = null;
	private Connection connection;

	protected UserDao() {
		connection = MySql.getInstance().getConnection();
	}

	/** Insert a new user to the database assume that all fields been checked */
	public boolean create(User user) throws Exception {
		CallableStatement callableS = null;

		// insert the user to the db
		try {

			// Statements allow to issue SQL queries to the database
			/*
			 * Example Call: call usp_createNewUser( `userName` VARCHAR(100) not
			 * null, `email` VARCHAR(320) not null, `password` VARCHAR(45) not
			 * null, `firstName` VARCHAR(30) not null, `lastName` VARCHAR(30)
			 * not null, `city` VARCHAR(30) null, `streetName` VARCHAR(50) null,
			 * `houseNumber` SMALLINT null, `addressDetails` VARCHAR(64) null,
			 * `zipcode` INT null, `phone1` VARCHAR(20) null, `phone2`
			 * VARCHAR(20) null )
			 */
			callableS = connection.prepareCall("call usp_createNewUser (?,?,?,?,?,?,?,?,?,?,?,?)");

			callableS.setString(1, user.getUsername());
			callableS.setString(2, user.getEmail());
			callableS.setString(3, user.getPassword());
			callableS.setString(4, user.getFirstName());
			callableS.setString(5, user.getLastName());
			callableS.setString(6, user.getCity());
			callableS.setString(7, user.getStreetName());
			callableS.setString(8, user.getCity());
			callableS.setInt(9, user.getHouseNumber());
			callableS.setString(10, user.getAddressDetails());
			callableS.setInt(11, user.getZipcode());
			callableS.setString(12, user.getPhone1());
			callableS.setString(13, user.getPhone2());
			callableS.execute();

			
			return true; //TODO does this happen before finally?
		} catch (Exception e) {
			throw e;
		} finally {
			if (callableS != null) {
				callableS.close();
			}
		}
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
