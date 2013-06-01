package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import db.MySql;

import entities.Person;

public class PersonDao {
	
	static private PersonDao instance = null;
	private Connection connection;
	
	protected PersonDao() {
		connection = MySql.getInstance().getConnection(); 
	}
	
	public Person get(int id) {
		Person person = new Person();
		return person;
	}
	
	public boolean create(Person person) {
		// insert the user to the db
		try {
			CallableStatement callable = connection.prepareCall("call sp_createNewPerson(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			callable.setString(1,person.getEmail());
			callable.setString(2,person.getFirstName());
			callable.setString(3,person.getLastName());
			callable.setString(4,person.getStreetName());
			callable.setString(5,person.getCity());
			callable.setInt(6,person.getHouseNumber());
			callable.setString(7,person.getAddressDetails());
			callable.setInt(8,person.getZipcode());
			callable.setString(9,person.getPhone1());
			callable.setString(10,person.getPhone2());
			callable.setString(11,person.getInsuranceCompany());
			callable.setString(12,person.getInsuranceAgentName());
			callable.setString(13,person.getInsurancePhone1());
			callable.setString(14,person.getInsurancePhone2());
			callable.setString(15,person.getInsuranceNumber());
			callable.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean update(Person person) {
		// update the user to the db
		return false;
	}
	
	static public PersonDao getInstance() {
		if (null == instance) {
			instance = new PersonDao();
		}
		return instance;
	}

}
