/* src/dao/PersonDao.java */
package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import db.MySql;
import entities.Person;
import entities.ResourceError;

public class PersonDao {
	
	static private PersonDao instance = null;
	private Connection connection;
	
	protected PersonDao() {
		connection = MySql.getInstance().getConnection(); 
	}
	
	/**Return a Person object given an id
	 * Parameters:
	 * 		@param personId integer
	 * Returns:
	 * 		@return Person
	 * Throws:
	 * 		@exception SQLException
	 * */
	public Person getById(int id) {
		Person person = new Person();
		try {
			CallableStatement callable = connection.prepareCall("call sp_getPersonById(?)");
			callable.setInt(1, id);
			ResultSet result = callable.executeQuery(); 
			if (!result.first()) {
				return null;
			}
			person.setPersonId(result.getInt("personId"));
			person.setEmail(result.getString("email"));
			person.setFirstName(result.getString("firstName"));
			person.setLastName(result.getString("lastName"));
			person.setIdNumber(result.getString("idNumber"));
			person.setCity(result.getString("city"));
			person.setStreetName(result.getString("streetName"));
			person.setHouseNumber(result.getInt("houseNumber"));
			person.setAddressDetails(result.getString("addressDetails"));
			person.setZipcode(result.getInt("zipcode"));
			person.setPhone1(result.getString("phone1"));
			person.setPhone2(result.getString("phone2"));
			person.setInsuranceCompany(result.getString("insuranceCompany"));
			person.setInsuranceAgentName(result.getString("insuranceAgentName"));
			person.setInsurancePhone1(result.getString("insurancePhone1"));
			person.setInsurancePhone2(result.getString("insurancePhone2"));
			person.setInsuranceNumber(result.getString("insuranceNumber"));
			return person;
		}
		catch (SQLException e) {
			//e.printStackTrace();
			ResourceError err = ResourceError.getInstance();
			err.setMessage("internal error");
			err.setStatusCode(500);
			err.setReasonCode(ResourceError.REASON_UNKNOWN);
			return null;
		}
	}
	
	/**Insert a Person object to the DB
	 * Parameters:
	 * 		@param person Person
	 * Returns:
	 * 		@return boolean
	 * Throws:
	 * 		@exception SQLException*/
	public boolean save(Person person) {
		// insert the person to the db
		try {

			CallableStatement callable;
			int i = 0;
			if (0 < person.getPersonId()) {
				callable = connection.prepareCall("call sp_updatePerson(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				callable.setInt(++i,person.getPersonId());
			}
			else {
				callable = connection.prepareCall("call sp_createPerson(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			}
			
			callable.setString(++i,person.getEmail());
			callable.setString(++i,person.getFirstName());
			callable.setString(++i,person.getLastName());
			callable.setString(++i,person.getIdNumber());
			callable.setString(++i,person.getStreetName());
			callable.setString(++i,person.getCity());
			callable.setInt(++i,person.getHouseNumber());
			callable.setString(++i,person.getAddressDetails());
			callable.setInt(++i,person.getZipcode());
			callable.setString(++i,person.getPhone1());
			callable.setString(++i,person.getPhone2());
			callable.setString(++i,person.getInsuranceCompany());
			callable.setString(++i,person.getInsuranceAgentName());
			callable.setString(++i,person.getInsurancePhone1());
			callable.setString(++i,person.getInsurancePhone2());
			callable.setString(++i,person.getInsuranceNumber());
			callable.registerOutParameter(++i, java.sql.Types.INTEGER);
			callable.execute();
			
			if (0 < person.getPersonId()) { // update mode
				int rowsUpdated = callable.getInt(i);
				if (1 != rowsUpdated) {
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
				int personId = callable.getInt(i);
				person.setPersonId(personId);
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
	
	/**Handle a person object, created so we won't have to use static methods 
	 * Return:
	 * 		@return Person
	 * */
	static public PersonDao getInstance() {
		if (null == instance) {
			instance = new PersonDao();
		}
		return instance;
	}

}
