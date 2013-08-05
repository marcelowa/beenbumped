package resources;

import java.net.URI;
import java.util.Date;
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
import dao.IncidentDao;
import dao.UserDao;
import entities.Incident;
import entities.Person;
import entities.ResourceError;

/**Handles the Incident entity, retrieve and save an incident*/
@Path("/incident")
public class IncidentResource {


	/**Retrieve an incident data from the DB by id
	 * Parameters:
	 * 		@param incidentId String
	 * 		@param userId String
	 * 		@param authHash String
	 * Returns:
	 * 		@return Incident*/
	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Incident getById(
			@PathParam("id") @DefaultValue("-1") String incidentId
			, @QueryParam("userId") @DefaultValue("-1") String userId
			, @QueryParam("authHash") @DefaultValue("") String authHash) {
		//return new Incident();
		IncidentDao incidentDao = IncidentDao.getInstance();
		UserDao userDao = UserDao.getInstance();
		ResourceError err = ResourceError.getInstance();
		int userIdOk, incidentIdOk;
		
		// validate input:
		try {
			userIdOk = Integer.parseInt(userId);
			incidentIdOk = Integer.parseInt(incidentId);
		}
		catch(Exception e) {
			if (!err.isSet()) {
				err.setMessage("invalid input, one of the following is not an int (userId, incidentId)");//, driverPersonId, ownerPersonId)");
				err.setStatusCode(400);
				err.setReasonCode(ResourceError.REASON_INVALID_INPUT);
			}
			throw new ResourceException(err);
		}
		
		if (!userDao.isAuthorized(userIdOk, authHash)){
			err.setMessage("unauthorized reason:mismatch userId, authHash");
			err.setStatusCode(400);
			err.setReasonCode(ResourceError.REASON_AUTHENTICATION_FAILED);
			throw new ResourceException(err);
		}
		
		Incident incident = incidentDao.getById(incidentIdOk, userIdOk);
		if (null == incident) {
			if (!err.isSet()) {
				err.setMessage("unable to get incident");
				err.setStatusCode(500);
				err.setReasonCode(ResourceError.REASON_UNKNOWN);
			}
			throw new ResourceException(err);
		}
		
		return incident;
	}
	
	/**Inserts an incident data to the DB
	 * Parameters:
	 *   	@param UriInfo uriInfo
	 *  	@param HttpServletRequest servletRequest
	 * 		@param HttpServletResponse servletResponse
	 * 		@param incidentId String
	 *		@param userId String
	 *		@param authHash String
	 *		@param date String
	 *		@param notes String
	 *		@param location String
	 *		@param vehicleLicensePlate String
	 *		@param vehicleBrand String
	 *		@param vehicleModel String
	 *		@param driverIdNumber String
	 *		@param driverFirstName String
	 *		@param driverLastName String
	 *		@param driverPhone1 String
	 *		@param driverPhone2 String
	 *		@param driverInsuranceCompany String  
	 *		@param driverInsuranceAgentName String
	 *		@param driverInsurancePhone1 String 
	 *		@param driverInsurancePhone2 String
	 *		@param driverInsuranceNumber String
	 *		@param driverEmail String
	 *		@param ownerIdNumber String
	 *		@param ownerFirstName String
	 *		@param ownerLastName String
	 *		@param ownerPhone1 String
	 *		@param ownerPhone2 String
	 *		@param ownerInsuranceCompany String  
	 *		@param ownerInsuranceAgentName String
	 *		@param ownerInsurancePhone1 String 
	 *		@param ownerInsurancePhone2 String
	 *		@param ownerInsuranceNumber String
	 *		@param ownerEmail String
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
			
			
			@FormParam("incidentId") @DefaultValue("-1") String incidentId,
			
			// authorization data
			@FormParam("userId") @DefaultValue("-1") String userId,
			@FormParam("authHash") @DefaultValue("") String authHash,
			
			// incident data
			@FormParam("date") @DefaultValue("0") String date,
			@FormParam("notes") @DefaultValue("") String notes,
			@FormParam("location") @DefaultValue("") String location,
			@FormParam("vehicleLicensePlate") @DefaultValue("") String vehicleLicensePlate,
			@FormParam("vehicleBrand") @DefaultValue("") String vehicleBrand,
			@FormParam("vehicleModel") @DefaultValue("") String vehicleModel,
			
			// car's driver data
			@FormParam("driverIdNumber") @DefaultValue("") String driverIdNumber,
			@FormParam("driverFirstName") @DefaultValue("") String driverFirstName,
			@FormParam("driverLastName") @DefaultValue("") String driverLastName,
			@FormParam("driverPhone1") @DefaultValue("") String driverPhone1,
			@FormParam("driverPhone2") @DefaultValue("") String driverPhone2,
			@FormParam("driverInsuranceCompany") @DefaultValue("") String driverInsuranceCompany,  
			@FormParam("driverInsuranceAgentName") @DefaultValue("") String driverInsuranceAgentName,
			@FormParam("driverInsurancePhone1") @DefaultValue("") String driverInsurancePhone1, 
			@FormParam("driverInsurancePhone2") @DefaultValue("") String driverInsurancePhone2,
			@FormParam("driverInsuranceNumber") @DefaultValue("") String driverInsuranceNumber,
			@FormParam("driverEmail") @DefaultValue("") String driverEmail,
			
			// car's owner data
			@FormParam("ownerIdNumber") @DefaultValue("") String ownerIdNumber,
			@FormParam("ownerFirstName") @DefaultValue("") String ownerFirstName,
			@FormParam("ownerLastName") @DefaultValue("") String ownerLastName,
			@FormParam("ownerPhone1") @DefaultValue("") String ownerPhone1,
			@FormParam("ownerPhone2") @DefaultValue("") String ownerPhone2,
			@FormParam("ownerInsuranceCompany") @DefaultValue("") String ownerInsuranceCompany,  
			@FormParam("ownerInsuranceAgentName") @DefaultValue("") String ownerInsuranceAgentName,
			@FormParam("ownerInsurancePhone1") @DefaultValue("") String ownerInsurancePhone1, 
			@FormParam("ownerInsurancePhone2") @DefaultValue("") String ownerInsurancePhone2,
			@FormParam("ownerInsuranceNumber") @DefaultValue("") String ownerInsuranceNumber,
			@FormParam("ownerEmail") @DefaultValue("") String ownerEmail
			
			) throws Exception {

		// declarations
		IncidentDao incidentDao = IncidentDao.getInstance();
		UserDao userDao = UserDao.getInstance();
		ResourceError err = ResourceError.getInstance();
		Person driver = new Person();
		Person owner = new Person();
		Incident incident = new Incident();
		int userIdOk, incidentIdOk, dateOk; //, driverPersonIdOk, ownerPersonIdOk;
		
		// validate input:
		try {
			userIdOk = Integer.parseInt(userId);
			incidentIdOk = Integer.parseInt(incidentId);
			dateOk = Integer.parseInt(date);
		}
		catch(Exception e) {
			if (!err.isSet()) {
				err.setMessage("incident not saved, one of the following is not an int (userId, incidentId, date)");//, driverPersonId, ownerPersonId)");
				err.setStatusCode(400);
				err.setReasonCode(ResourceError.REASON_INVALID_INPUT);
			}
			throw new ResourceException(err);
		}
		
		
		// check if user is authorized to save an incident
		if (!userDao.isAuthorized(userIdOk, authHash)) {
			if (!err.isSet()) {
				err.setMessage("incident not saved, reason:authorization failed");
				err.setStatusCode(400);
				err.setReasonCode(ResourceError.REASON_AUTHENTICATION_FAILED);
			}
			throw new ResourceException(err);
		}
		
		if (incidentIdOk > 0) {
			incident = incidentDao.getById(incidentIdOk, userIdOk);

			if (null == incident) {
				if (!err.isSet()) {
					err.setMessage("unable to save incident");
					err.setStatusCode(500);
					err.setReasonCode(ResourceError.REASON_UNKNOWN);
				}
				throw new ResourceException(err);
			}
			
			
			Person driverTmp = incident.getDriver();
			Person ownerTmp = incident.getOwner();
			
			if (null != driverTmp) {
				driver = driverTmp;
			}
			if (null != ownerTmp) {
				owner = ownerTmp;
			}
		}
		else {
			incident.setUserId(userIdOk);
		}
		
		incident.setDate(new Date(dateOk));
		incident.setLocation(location);
		incident.setNotes(notes);
		incident.setVehicleLicensePlate(vehicleLicensePlate);
		incident.setVehicleBrand(vehicleBrand);
		incident.setVehicleModel(vehicleModel);
		
		if (driver.getPersonId() > 0
				|| !driverFirstName.equals("")
				|| !driverLastName.equals("")
				|| !driverIdNumber.equals("")
				|| !driverPhone1.equals("")
				|| !driverPhone2.equals("")
				|| !driverInsuranceCompany.equals("")
				|| !driverInsuranceAgentName.equals("")
				|| !driverInsurancePhone1.equals("")
				|| !driverInsurancePhone2.equals("")
				|| !driverInsuranceNumber.equals("")
				|| !driverEmail.equals("")
			) {
			driver.setFirstName(driverFirstName);
			driver.setLastName(driverLastName);
			driver.setIdNumber(driverIdNumber);
			driver.setPhone1(driverPhone1);
			driver.setPhone2(driverPhone2);
			driver.setInsuranceCompany(driverInsuranceCompany);
			driver.setInsuranceAgentName(driverInsuranceAgentName);
			driver.setInsurancePhone1(driverInsurancePhone1);
			driver.setInsurancePhone2(driverInsurancePhone2);
			driver.setInsuranceNumber(driverInsuranceNumber);
			driver.setEmail(driverEmail);
			
			incident.setDriver(driver);
		}
		
		if (owner.getPersonId() > 0
				|| !ownerFirstName.equals("")
				|| !ownerLastName.equals("")
				|| !ownerIdNumber.equals("")
				|| !ownerPhone1.equals("")
				|| !ownerPhone2.equals("")
				|| !ownerInsuranceCompany.equals("")
				|| !ownerInsuranceAgentName.equals("")
				|| !ownerInsurancePhone1.equals("")
				|| !ownerInsurancePhone2.equals("")
				|| !ownerInsuranceNumber.equals("")
				|| !ownerEmail.equals("")
				) {
			owner.setFirstName(ownerFirstName);
			owner.setLastName(ownerLastName);
			owner.setIdNumber(ownerIdNumber);
			owner.setPhone1(ownerPhone1);
			owner.setPhone2(ownerPhone2);
			owner.setInsuranceCompany(ownerInsuranceCompany);
			owner.setInsuranceAgentName(ownerInsuranceAgentName);
			owner.setInsurancePhone1(ownerInsurancePhone1);
			owner.setInsurancePhone2(ownerInsurancePhone2);
			owner.setInsuranceNumber(ownerInsuranceNumber);
			owner.setEmail(ownerEmail);
			
			incident.setOwner(owner);
		}
		
		
		if (!incidentDao.save(incident) || 0 >= incident.getIncidentId()) {
			if (!err.isSet()) {
				err.setMessage("incident not saved");
				err.setStatusCode(500);
				err.setReasonCode(ResourceError.REASON_UNKNOWN);
			}
			throw new ResourceException(err);
		}
		
		URI uri = uriInfo.getBaseUriBuilder().path(IncidentResource.class).path("/" + incident.getIncidentId()).queryParam("userId", userId).queryParam("authHash", authHash).build();
		servletResponse.sendRedirect(uri.toString());
	}
}
