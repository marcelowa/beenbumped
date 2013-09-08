/* src/entities/Person.java */
package entities;

import javax.xml.bind.annotation.XmlRootElement;

/**Represent a person, can be us or another person in an incident*/
@XmlRootElement
public class Person {

	private int personId;
	private String email;
	private String firstName;
	private String lastName;
	private String idNumber;
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
	
	/**Person constructor, without the person id that we got back from the database 
	 * Parameters:
	 * 		@param email String
	 *		@param firstName String
	 *		@param lastName String 
	 *		@param idNumber String, "teudat zehut" 
	 *		@param city String 
	 *		@param streetName String 
	 *		@param houseNumber integer
	 *		@param addressDetails String, additional information, like if you have more then one entrance to the building 
	 *		@param zipcode integer
	 *		@param phone1 String 
	 *		@param phone2 String
	 *		@param insuranceCompany String, insurance company name
	 *		@param insuranceAgentName String 
	 *		@param insurancePhone1 String
	 *		@param insurancePhone2 String
	 *		@param insuranceNumber String*/
	public Person(String email, String firstName,
			String lastName, String idNumber,String city, String streetName, int houseNumber,
			String addressDetails, int zipcode, String phone1, String phone2,
			String insuranceCompany, String insuranceAgentName, String insurancePhone1,
			String insurancePhone2, String insuranceNumber) {
		
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.idNumber = idNumber;
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
	
	/**Person constructor, a complete one
	 * Parameters:
	 * 		@param personId integer
	 * 		@param email String
	 *		@param firstName String
	 *		@param lastName String 
	 *		@param idNumber String, "teudat zehut" 
	 *		@param city String 
	 *		@param streetName String 
	 *		@param houseNumber integer
	 *		@param addressDetails String, additional information, like if you have more then one entrance to the building 
	 *		@param zipcode integer
	 *		@param phone1 String 
	 *		@param phone2 String
	 *		@param insuranceCompany String, insurance company name
	 *		@param insuranceAgentName String 
	 *		@param insurancePhone1 String
	 *		@param insurancePhone2 String
	 *		@param insuranceNumber String*/
	public Person(int personId, String email, String firstName,
			String lastName, String idNumber, String city, String streetName, int houseNumber,
			String addressDetails, int zipcode, String phone1, String phone2,
			String insuranceCompany, String insuranceAgentName, String insurancePhone1,
			String insurancePhone2, String insuranceNumber) {
		
		this.personId = personId;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.idNumber = idNumber;
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

	/**An empty constructor*/
	public Person() {
		//this constructor is needed so jaxb doesn't throw an exception related to no-arg constructor
	}
	
	/**A PersonId getter
	 * Returns:
	 * 		@return integer*/
	public int getPersonId() {
		return personId;
	}

	/**A PersonId setter
	 * Parameters:
	 * 		@param PersonId integer*/
	public void setPersonId(int personId) {
		this.personId = personId;
	}
	
	/**An email getter
	 * Returns:
	 * 		@return integer*/
	public String getEmail() {
		return email;
	}

	/**An email setter
	 * Parameters:
	 * 		@param email String*/
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**A first name getter
	 * Returns:
	 * 		@return String*/
	public String getFirstName() {
		return firstName;
	}

	/**A first name setter
	 * Parameters:
	 * 		@param first name String*/
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**A last name getter
	 * Returns:
	 * 		@return String*/
	public String getLastName() {
		return lastName;
	}

	/**A last name setter
	 * Parameters:
	 * 		@param last name String*/
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/**An id number ("tedudat zehout") getter
	 * Returns:
	 * 		@return integer*/
	public String getIdNumber() {
		return idNumber;
	}

	/**An id number ("tedudat zehout") setter
	 * Parameters:
	 * 		@param id number integer*/
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	/**A city getter
	 * Returns:
	 * 		@return String*/
	public String getCity() {
		return city;
	}

	/**A city setter
	 * Parameters:
	 * 		@param city String*/
	public void setCity(String city) {
		this.city = city;
	}

	/**A street name getter
	 * Returns:
	 * 		@return String*/
	public String getStreetName() {
		return streetName;
	}

	/**A street name setter
	 * Parameters:
	 * 		@param street name String*/
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	/**A house number getter
	 * Returns:
	 * 		@return integer*/
	public int getHouseNumber() {
		return houseNumber;
	}

	/**A house number setter
	 * Parameters:
	 * 		@param house number integer*/
	public void setHouseNumber(int houseNumber) {
		this.houseNumber = houseNumber;
	}

	/**An address details getter
	 * Returns:
	 * 		@return String*/
	public String getAddressDetails() {
		return addressDetails;
	}

	/**An address details setter
	 * Parameters:
	 * 		@param address details String*/
	public void setAddressDetails(String addressDetails) {
		this.addressDetails = addressDetails;
	}

	/**A zipcode getter
	 * Returns:
	 * 		@return integer*/
	public int getZipcode() {
		return zipcode;
	}

	/**A zipcode setter
	 * Parameters:
	 * 		@param zipcode integer*/
	public void setZipcode(int zipcode) {
		this.zipcode = zipcode;
	}

	/**A phone 1 getter
	 * Returns:
	 * 		@return String*/
	public String getPhone1() {
		return phone1;
	}

	/**A phone 1 setter
	 * Parameters:
	 * 		@param PersonId String*/
	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	/**A phone 2 getter
	 * Returns:
	 * 		@return String*/
	public String getPhone2() {
		return phone2;
	}

	/**A phone 2 getter
	 * Returns:
	 * 		@return String*/
	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	/**An insurance company name getter
	 * Returns:
	 * 		@return String*/
	public String getInsuranceCompany() {
		return insuranceCompany;
	}

	/**An insurance company name setter
	 * Parameters:
	 * 		@param insurance company name String*/
	public void setInsuranceCompany(String insuranceCompany) {
		this.insuranceCompany = insuranceCompany;
	}


	/**An insurance agent name getter
	 * Returns:
	 * 		@return String*/
	public String getInsuranceAgentName() {
		return insuranceAgentName;
	}

	/**An insurance agent name setter
	 * Parameters:
	 * 		@param insurance agent name String*/
	public void setInsuranceAgentName(String insuranceAgentName) {
		this.insuranceAgentName = insuranceAgentName;
	}

	/**An insurance phone 1 getter
	 * Returns:
	 * 		@return String*/
	public String getInsurancePhone1() {
		return insurancePhone1;
	}

	/**An insurance phone 1 setter
	 * Parameters:
	 * 		@param insurance phone 1 String*/
	public void setInsurancePhone1(String insurancePhone1) {
		this.insurancePhone1 = insurancePhone1;
	}


	/**An insurance phone 2 getter
	 * Returns:
	 * 		@return String*/
	public String getInsurancePhone2() {
		return insurancePhone2;
	}

	/**An insurance phone 2 setter
	 * Parameters:
	 * 		@param insurance phone 2 String*/
	public void setInsurancePhone2(String insurancePhone2) {
		this.insurancePhone2 = insurancePhone2;
	}


	/**An insurance number getter
	 * Returns:
	 * 		@return String*/
	public String getInsuranceNumber() {
		return insuranceNumber;
	}

	/**An insurance number setter
	 * Parameters:
	 * 		@param insurance number String*/
	public void setInsuranceNumber(String insuranceNumber) {
		this.insuranceNumber = insuranceNumber;
	}
}
