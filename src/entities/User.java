package entities;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**Represent a user, which is an extend of a person*/
@XmlRootElement
public class User extends Person {

	private int userId;
	private String username;
	private String password;
	private String authHash;

	/**Incident constructor 
	 * Parameters:
	 * 		@param int userId
	 *		@param username String 
	 *		@param password String 
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
	 *		@param insuranceNumber String
	 * */
	public User(int userId, String username, String password, int personId, String email, String firstName,
			String lastName, String idNumber, String city, String streetName, int houseNumber,
			String addressDetails, int zipcode, String phone1, String phone2,
			String insuranceCompany, String insuranceAgentName, String insurancePhone1,
			String insurancePhone2, String insuranceId) {
		
		super(personId, email, firstName,
				lastName, idNumber, city, streetName, houseNumber,
				addressDetails, zipcode, phone1, phone2,
				insuranceCompany, insuranceAgentName, insurancePhone1,
				insurancePhone2, insuranceId);
		
		this.userId = userId;
		this.username = username;
		this.password = password;
	}
	
	/**An empty constructor*/
	public User() {
		//this constructor is needed so jaxb doesn't throw an exception related to no-arg constructor
	}
	
	/**A user id getter
	 * Returns:
	 * 		@return integer*/
	public int getUserId() {
		return userId;
	}
	
	/**A user id setter
	 * Parameters:
	 * 		@param user id integer*/
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	/**A user name getter
	 * Returns:
	 * 		@return String*/
	public String getUsername() {
		return username;
	}
	
	/**A user name setter
	 * Parameters:
	 * 		@param user name String*/
	public void setUsername(String username) {
		this.username = username;
	}

	/**A password getter
	 * Returns:
	 * 		@return String*/
	@XmlTransient
	public String getPassword() {
		return password;
	}

	/**A password setter
	 * Parameters:
	 * 		@param password String*/
	public void setPassword(String password) {
		this.password = password;
	}

	/**An authentication Hash getter
	 * Returns:
	 * 		@return String*/
	public String getAuthHash() {
		return authHash;
	}

	/**An authentication Hash setter
	 * Parameters:
	 * 		@param authentication Hash String*/
	public void setAuthHash(String authHash) {
		this.authHash = authHash;
	}

}
