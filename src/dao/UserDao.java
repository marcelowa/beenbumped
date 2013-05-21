package dao;

import java.sql.Connection;

import db.MySql;

import entities.User;

public class UserDao {
	
	static private UserDao instance = null;
	private Connection connection;
	
	protected UserDao() {
		connection = MySql.getInstance().getConnection(); 
	}
	
	public User get(int id) {
		User user = new User();
		return user;
	}
	
	public boolean create(User user) {
		// insert the user to the db
		return false;
	}
	
	public boolean update(User user) {
		// update the user to the db
		return false;
	}
	
	static public UserDao getInstance() {
		if (null == instance) {
			instance = new UserDao();
		}
		return instance;
	}

}
