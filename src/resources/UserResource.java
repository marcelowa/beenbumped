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
import entities.User;

@Path("/user")
public class UserResource {

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public User authenticate(@QueryParam("username") String username,
			@QueryParam("password") String password) {
		User user = UserDao.getInstance().authenticate(username, password);
		if (null == user) {
			throw new ResourceException("User::get wrong username or password", "", 400, 400);
		}
		return user;
	}
	
	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public User getById(@PathParam("id") int id, @QueryParam("authHash") String authHash) {
		UserDao userDao = UserDao.getInstance();
		if (!userDao.isAuthorized(id, authHash)){
			throw new RuntimeException("User::getById unathorized, reason:mismatch userId, authHash");
		}
		User user = userDao.getById(id);
		if (null == user) {
			throw new ResourceException("User::get with id " + id + " not found", "", 400, 400);
		}
		user.setAuthHash(authHash);
		return user;
	}
	
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
			throw new ResourceException("user not saved, one of the following is not an int (personId, userId, houseNumber, zipcode)", "", 400 , 400);
		}
		
		if (username == "") {
			throw new ResourceException("user not saved, username is missing)", "", 400 , 400);
		}
		
		UserDao userDao = UserDao.getInstance();
		User user = new User();
		
		if (personIdOk > 0 && userIdOk > 0) { // if we know the userId and personId it means we intend to update a user, therefore check if authorized to make such a change
			if (!userDao.isAuthorized(userIdOk, authHash)) {
				throw new ResourceException("user not updated, reason:mismatch userId, authHash", "", 400, 400);
			}
			user.setPersonId(personIdOk);
			user.setUserId(userIdOk);
			user.setAuthHash(authHash);
		}
	
		user.setEmail((String)email);
		user.setFirstName(firstName);
		user.setLastName(lastName);
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
			throw new ResourceException("user not saved", "", 400 , 400);
		}
		
		URI userUri = uriInfo.getBaseUriBuilder().path(UserResource.class).path("/" + user.getUserId()).queryParam("authHash", user.getAuthHash()).build();
		servletResponse.sendRedirect(userUri.toString());
	}
}
