package dao;

import entities.User;

public class UserDao {
	
	public User get(int id) {
		// find the user by id in the db and return a constructed user
		
		//return new User(userId, username, email, password, firstName, lastName, city, streetName, houseNumber, addressDetails, zipcode, phone1, phone2)
		
	}
	
	public boolean create(User user) {
		// insert the user to the db
		return false;
	}
	
	public boolean update(User user) {
		// update the user to the db
		return false;
	}
	
	public boolean authenticate(User user, String password) {
		// authenticate user password with the db
		return false;
	}
}
