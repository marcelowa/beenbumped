/* src/resources/UserResource.java */
package resources;

import java.net.URI;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import dao.UserDao;
import entities.ResourceError;
import entities.User;

/**Handles the User entity, retrieve and save a user*/
@Path("/user")
public class UserResource {

	/**Authenticate a user password
	 * Parameters:
	 * 		@param username String
	 * 		@param password String
	 * Returns:
	 * 		@return user
	 *  Throws:
	 * 		@exception ResourceException*/
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public User authenticate(@QueryParam("username") String username,
			@QueryParam("password") String password) {
		User user = UserDao.getInstance().authenticate(username, password);
		if (null == user) {
			ResourceError err = ResourceError.getInstance();
			if (!err.isSet()) {
				err.setMessage("unable to get user");
				err.setStatusCode(500);
				err.setReasonCode(ResourceError.REASON_UNKNOWN);
			}
			throw new ResourceException(err);
		}
		return user;
	}
	
	/**Return a user given an Id, verify with local hash
	 * Parameters:
	 * 		@param userId integer
	 * 		@param authHash String
	 * Returns:
	 * 		@return user
	 * Throws:
	 * 		@exception ResourceException*/
	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public User getById(@PathParam("id") int id, @QueryParam("authHash") String authHash) {
		UserDao userDao = UserDao.getInstance();
		if (!userDao.isAuthorized(id, authHash)){
			ResourceError err = ResourceError.getInstance();
			err.setMessage("unauthorized reason:mismatch userId, authHash");
			err.setStatusCode(400);
			err.setReasonCode(ResourceError.REASON_AUTHENTICATION_FAILED);
			throw new ResourceException(err);
		}
		User user = userDao.getById(id);
		if (null == user) {
			ResourceError err = ResourceError.getInstance();
			if (!err.isSet()) {
				err.setMessage("unable to get user");
				err.setStatusCode(500);
				err.setReasonCode(ResourceError.REASON_UNKNOWN);
			}
			throw new ResourceException(err);
		}
		user.setAuthHash(authHash);
		return user;
	}
	
	/**Inserts a user data to the DB
	 * Parameters:
	 *  	@param UriInfo uriInfo
	 *  	@param HttpServletRequest servletRequest
	 * 		@param HttpServletResponse servletResponse
	 * 		@param personeId String, default value -1
	 * 		@param userid String, default -1
	 * 		@param email String
	 *		@param firstName String
	 *		@param lastName String 
	 *		@param idNumber String, "teudat zehut" 
	 *		@param city String 
	 *		@param streetName String 
	 *		@param houseNumber String
	 *		@param addressDetails String, additional information, like if you have more then one entrance to the building 
	 *		@param zipcode String
	 *		@param phone1 String 
	 *		@param phone2 String
	 *		@param insuranceCompany String, insurance company name
	 *		@param insuranceAgentName String 
	 *		@param insurancePhone1 String
	 *		@param insurancePhone2 String
	 *		@param insuranceNumber String
	 *		@param username String
	 *		@param password String
	 *		@param authHash String
	 * Throws:
	 * 		@exception ResourceException
	 **/
	@POST
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void save(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest servletRequest,
			@Context HttpServletResponse servletResponse,
			@FormParam("personId") @DefaultValue("-1") String personId,  // validate integer
			@FormParam("userId") @DefaultValue("-1") String userId,  // validate integer
			@FormParam("email") @DefaultValue("") String email ,
			@FormParam("firstName") @DefaultValue("") String firstName,
			@FormParam("lastName") @DefaultValue("") String lastName,
			@FormParam("idNumber") @DefaultValue("") String idNumber,
			@FormParam("city") @DefaultValue("") String city,
			@FormParam("streetName") @DefaultValue("") String streetName,
			@FormParam("houseNumber") @DefaultValue("-1") String houseNumber,  // validate integer
			@FormParam("addressDetails") @DefaultValue("") String addressDetails,
			@FormParam("zipcode") @DefaultValue("-1") String zipcode, // validate integer
			@FormParam("phone1") @DefaultValue("") String phone1,
			@FormParam("phone2") @DefaultValue("") String phone2,
			@FormParam("insuranceCompany") @DefaultValue("") String insuranceCompany,
			@FormParam("insuranceAgentName") @DefaultValue("") String insuranceAgentName,
			@FormParam("insurancePhone1") @DefaultValue("") String insurancePhone1,
			@FormParam("insurancePhone2") @DefaultValue("") String insurancePhone2,
			@FormParam("insuranceNumber") @DefaultValue("") String insuranceNumber,
			@FormParam("username") @DefaultValue("") String username,
			@FormParam("password") @DefaultValue("") String password,
			@FormParam("authHash") @DefaultValue("") String authHash
			) throws Exception {

		// validate input:
		int personIdOk, userIdOk, houseNumberOk, zipcodeOk;
		try {
			personIdOk = Integer.parseInt(personId);
			userIdOk = Integer.parseInt(userId);
			houseNumberOk = Integer.parseInt(houseNumber);
			zipcodeOk = Integer.parseInt(zipcode);
		}
		catch(Exception e) {
			
			ResourceError err = ResourceError.getInstance();
			if (!err.isSet()) {
				err.setMessage("user not saved, one of the following is not an int (personId, userId, houseNumber, zipcode)");
				err.setStatusCode(400);
				err.setReasonCode(ResourceError.REASON_INVALID_INPUT);
			}
			throw new ResourceException(err);
		}
		
		UserDao userDao = UserDao.getInstance();
		User user = new User();
		
		if (personIdOk <= 0 || userIdOk <= 0) { // if we dont't know the userId or personId it means we intend to create a new user
			if (username == "" || password == "") { // therefor verify that username and password are not empty
				ResourceError err = ResourceError.getInstance();
				err.setMessage("user not saved, username or password is missing)");
				err.setStatusCode(400);
				err.setReasonCode(ResourceError.REASON_INVALID_INPUT);
				throw new ResourceException(err);
			}
		}
		else  { // check if authorized to make a change to the user 
			if (!userDao.isAuthorized(userIdOk, authHash)) {
				ResourceError err = ResourceError.getInstance();
				if (!err.isSet()) {
					err.setMessage("user not save, reason:authorization failed");
					err.setStatusCode(400);
					err.setReasonCode(ResourceError.REASON_AUTHENTICATION_FAILED);
				}
				throw new ResourceException(err);
			}
			user.setPersonId(personIdOk);
			user.setUserId(userIdOk);
			user.setAuthHash(authHash);
		}
	
		user.setEmail((String)email);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setIdNumber(idNumber); //TODO handle length
		user.setCity(city);
		user.setStreetName(streetName);
		//TODO handle better integers, it crushes the application if non integer string is passed here
		user.setHouseNumber(houseNumberOk);
		user.setAddressDetails(addressDetails);
		//TODO handle better integers, it crushes the application if non integer string is passed here
		user.setZipcode(zipcodeOk);
		user.setPhone1(phone1);
		user.setPhone2(phone2);
		user.setInsuranceCompany(insuranceCompany);
		user.setInsuranceAgentName(insuranceAgentName);
		user.setInsurancePhone1(insurancePhone1);
		user.setInsurancePhone2(insurancePhone2);
		user.setInsuranceNumber(insuranceNumber);
		user.setUsername(username);
		user.setPassword(password); //TODO hash the password before persisting to the database 
		
		if (!userDao.save(user) || 0 >= user.getUserId() || "" == user.getAuthHash()) {
			ResourceError err = ResourceError.getInstance();
			if (!err.isSet()) {
				err.setMessage("user not saved");
				err.setStatusCode(500);
				err.setReasonCode(ResourceError.REASON_UNKNOWN);
			}
			throw new ResourceException(err);
		}
		
		URI userUri = uriInfo.getBaseUriBuilder().path(UserResource.class).path("/" + user.getUserId()).queryParam("authHash", user.getAuthHash()).build();
		servletResponse.sendRedirect(userUri.toString());
	}
}
