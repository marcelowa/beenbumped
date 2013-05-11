package entities;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User extends Person {

	private int userId;
	
	private String username;
	
	private String password;
	

	public User(int userId, String username, String password, int personId, String email, String firstName,
			String lastName, String city, String streetName, int houseNumber,
			String addressDetails, int zipcode, String phone1, String phone2,
			String insuranceCompany, String insuranceAgentName, String insurancePhone1,
			String insurancePhone2, String insuranceId) {
		
		super(personId, email, firstName,
				lastName, city, streetName, houseNumber,
				addressDetails, zipcode, phone1, phone2,
				insuranceCompany, insuranceAgentName, insurancePhone1,
				insurancePhone2, insuranceId);
		
		this.userId = userId;
		this.username = username;
		this.password = password;
	}
	
	public User() {
		//this constructor is needed so jaxb doesn't throw an exception related to no-arg constructor
	}
	
	public int getUserId() {
		return userId;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
