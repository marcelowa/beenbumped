package dao;

import java.sql.Connection;

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
		return false;
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
