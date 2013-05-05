package entities;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User {

	private int userId;
	
	private String username;
	
	private String email;
	
	private String password;
	
	private String firstName;
	
	private String lastName;
	
	private String city;
	
	private String streetName;
	
	private int houseNumber;
	
	private String addressDetails;
	
	private int zipcode;
	
	private String phone1;
	
	private String phone2;
	
	
	public User(int userId, String username, String email, String password, String firstName,
			String lastName, String city, String streetName, int houseNumber,
			String addressDetails, int zipcode, String phone1, String phone2) {
		
		this.userId = userId;
		this.username = username;
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.city = city;
		this.streetName = streetName;
		this.houseNumber = houseNumber;
		this.addressDetails = addressDetails;
		this.zipcode = zipcode;
		this.phone1 = phone1;
		this.phone2 = phone2;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String password) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public int getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(int houseNumber) {
		this.houseNumber = houseNumber;
	}

	public String getAddressDetails() {
		return addressDetails;
	}

	public void setAddressDetails(String addressDetails) {
		this.addressDetails = addressDetails;
	}

	public int getZipcode() {
		return zipcode;
	}

	public void setZipcode(int zipcode) {
		this.zipcode = zipcode;
	}

	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

}
