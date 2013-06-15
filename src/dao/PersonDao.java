package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import db.MySql;
import entities.Person;

public class PersonDao {
	
	static private PersonDao instance = null;
	private Connection connection;
	
	protected PersonDao() {
		connection = MySql.getInstance().getConnection(); 
	}
	
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
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean save(Person person) {
		// insert the person to the db
		try {

			CallableStatement callable;
			int i = 0;
			if (0 < person.getPersonId()) {
				callable = connection.prepareCall("call sp_updatePerson(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				callable.setInt(++i,person.getPersonId());
			}
			else {
				callable = connection.prepareCall("call sp_createPerson(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			}
			
			callable.setString(++i,person.getEmail());
			callable.setString(++i,person.getFirstName());
			callable.setString(++i,person.getLastName());
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
					//TODO should roll back if rowsUpdated greater then 1
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
			e.printStackTrace();
			return false;
		}
	}
	
	static public PersonDao getInstance() {
		if (null == instance) {
			instance = new PersonDao();
		}
		return instance;
	}

}
