package entities;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Person {

	private int personId;
	private String email;
	private String firstName;
	private String lastName;
	private String city;
	private String streetName;
	private int houseNumber = -1;
	private String addressDetails;
	private int zipcode = -1;
	private String phone1;
	private String phone2;
	private String insuranceCompany;
	private String insuranceAgentName;
	private String insurancePhone1;
	private String insurancePhone2;
	private String insuranceNumber;
	
	
	public Person(String email, String firstName,
			String lastName, String city, String streetName, int houseNumber,
			String addressDetails, int zipcode, String phone1, String phone2,
			String insuranceCompany, String insuranceAgentName, String insurancePhone1,
			String insurancePhone2, String insuranceNumber) {
		
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.city = city;
		this.streetName = streetName;
		this.houseNumber = houseNumber;
		this.addressDetails = addressDetails;
		this.zipcode = zipcode;
		this.phone1 = phone1;
		this.phone2 = phone2;
		this.insuranceCompany = insuranceCompany;
		this.insuranceAgentName = insuranceAgentName;
		this.insurancePhone1 = insurancePhone1;
		this.insurancePhone2 = insurancePhone2;
		this.insuranceNumber = insuranceNumber;
		
		
	}
	
	public Person(int personId, String email, String firstName,
			String lastName, String city, String streetName, int houseNumber,
			String addressDetails, int zipcode, String phone1, String phone2,
			String insuranceCompany, String insuranceAgentName, String insurancePhone1,
			String insurancePhone2, String insuranceNumber) {
		
		this.personId = personId;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.city = city;
		this.streetName = streetName;
		this.houseNumber = houseNumber;
		this.addressDetails = addressDetails;
		this.zipcode = zipcode;
		this.phone1 = phone1;
		this.phone2 = phone2;
		this.insuranceCompany = insuranceCompany;
		this.insuranceAgentName = insuranceAgentName;
		this.insurancePhone1 = insurancePhone1;
		this.insurancePhone2 = insurancePhone2;
		this.insuranceNumber = insuranceNumber;
	}

	
	public Person() {
		//this constructor is needed so jaxb doesn't throw an exception related to no-arg constructor
	}
	
	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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


	public String getInsuranceCompany() {
		return insuranceCompany;
	}


	public void setInsuranceCompany(String insuranceCompany) {
		this.insuranceCompany = insuranceCompany;
	}


	public String getInsuranceAgentName() {
		return insuranceAgentName;
	}


	public void setInsuranceAgentName(String insuranceAgentName) {
		this.insuranceAgentName = insuranceAgentName;
	}


	public String getInsurancePhone1() {
		return insurancePhone1;
	}


	public void setInsurancePhone1(String insurancePhone1) {
		this.insurancePhone1 = insurancePhone1;
	}


	public String getInsurancePhone2() {
		return insurancePhone2;
	}


	public void setInsurancePhone2(String insurancePhone2) {
		this.insurancePhone2 = insurancePhone2;
	}


	public String getInsuranceNumber() {
		return insuranceNumber;
	}


	public void setInsuranceNumber(String insuranceNumber) {
		this.insuranceNumber = insuranceNumber;
	}
}
