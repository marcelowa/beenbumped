package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;

import entities.User;

public class UserDao {
	
	private Connection connect = null;
	private Statement statement = null;
	private CallableStatement callableS = null;
	private ResultSet resultSet = null;
	private final String CONNECTION_STRING  = "jdbc:mysql://localhost/beenbumped?" + "user=root&password=bitter";
	private final String MySQL_DRIVER = "com.mysql.jdbc.Driver";

	/*	public User get(int id) {
		// find the user by id in the db and return a constructed user
		
		//return new User(userId, username, email, password, firstName, lastName, city, streetName, houseNumber, addressDetails, zipcode, phone1, phone2)
		
	}
*/

	/**Insert a new user to the database assume that all fields been checked*/
	public boolean create(User user) throws Exception {
		// insert the user to the db
		try {
			// This will load the MySQL driver, each DB has its own driver
			Class.forName(MySQL_DRIVER);
			// Setup the connection with the DB
			connect = DriverManager.getConnection(CONNECTION_STRING);

			// Statements allow to issue SQL queries to the database
			/*Example Call:
			 * call usp_createNewUser(
				`userName` VARCHAR(100) not null,
				`email` VARCHAR(320) not null,
				`password` VARCHAR(45) not null,
				`firstName` VARCHAR(30) not null,
				`lastName` VARCHAR(30) not null,
				`city` VARCHAR(30) null,
				`streetName` VARCHAR(50) null,
				`houseNumber` SMALLINT null,
				`addressDetails` VARCHAR(64) null,
				`zipcode` INT null,
				`phone1` VARCHAR(20) null,
				`phone2` VARCHAR(20) null
				)*/
			statement = connect.createStatement();	
			callableS = connect.prepareCall("call usp_createNewUser (?,?,?,?,?,?,?,?,?,?,?,?)");
			
			callableS.setString(1,user.getUsername());
			callableS.setString(2, user.getEmail());
			callableS.setString(3, user.getPassword());
			callableS.setString(4, user.getFirstName());
			callableS.setString(5, user.getLastName());
			callableS.setString(6, user.getCity());
			callableS.setString(7, user.getStreetName());
			callableS.setString(8, user.getCity());
			callableS.setInt(9, user.getHouseNumber());
			callableS.setString(10,user.getAddressDetails());
			callableS.setInt(11, user.getZipcode());
			callableS.setString(12, user.getPhone1());
			callableS.setString(13, user.getPhone2());
			callableS.execute();
			
			return true;
		} catch (Exception e) {
			throw e;
		} finally {
			close();
		}
	}
	
/*	
	public boolean update(User user) {
		// update the user to the db
		return false;
	}
*/	

	/** connects to the DB and checks if the password matches the user*/
	public boolean authenticate(User user, String password) throws Exception{
		// authenticate user password with the db
		try {
			// This will load the MySQL driver, each DB has its own driver
			Class.forName(MySQL_DRIVER);
			// Setup the connection with the DB
			connect = DriverManager.getConnection(CONNECTION_STRING);

			// Statements allow to issue SQL queries to the database
			/*Example Call:
			 *  Example call: SET @uId = 1;
				 SET @pass = '6F9619FF-8B86-D011-B42D-00C04FC964FF';
				 PREPARE s FROM 'CALL usp_userAuthentication(@uId, @pass,@result)';
				 EXECUTE s;
				 SELECT @uId, @pass,@result;
				)*/
			statement = connect.createStatement();	
			callableS = connect.prepareCall("? = call usp_userAuthentication (?,?)");
			
			callableS.registerOutParameter(1,java.sql.Types.BOOLEAN);
			callableS.setInt(2,user.getUserId());
			callableS.setString(3, password);
			
			callableS.execute();
			
			boolean result = callableS.getBoolean(1);
			
			return result;
			
		} catch (Exception e) {
			throw e;
		} finally {
			close();
		}
	}
	
	// You need to close the resultSet
		private void close() {
			try {
				if (resultSet != null) {
					resultSet.close();
				}

				if (statement != null) {
					statement.close();
				}

				if (connect != null) {
					connect.close();
				}
			}
			catch (Exception e) {

			}
		}
}
