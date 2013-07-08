package resources;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import dao.IncidentDao;
import dao.PersonDao;
import dao.UserDao;
import entities.Incident;
import entities.Person;
import entities.ResourceError;

@Path("/incident")
public class IncidentResource {

	
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
			@FormParam("date") @DefaultValue("") String date,
			@FormParam("notes") @DefaultValue("") String notes,
			@FormParam("location") @DefaultValue("") String location,
			@FormParam("vehicleLicensePlate") @DefaultValue("") String vehicleLicensePlate,
			@FormParam("vehicleBrand") @DefaultValue("") String vehicleBrand,
			@FormParam("vehicleModel") @DefaultValue("") String vehicleModel,
			
			// car's driver data
			// @FormParam("driverPersonId") @DefaultValue("-1") String driverPersonId,
			@FormParam("driverEmail") @DefaultValue("") String driverEmail ,
			@FormParam("driverFirstName") @DefaultValue("") String driverFirstName,
			@FormParam("driverLastName") @DefaultValue("") String driverLastName,
			@FormParam("driverPhone1") @DefaultValue("") String driverPhone1,
			@FormParam("driverPhone2") @DefaultValue("") String driverPhone2,
			@FormParam("driverInsuranceCompany") @DefaultValue("") String driverInsuranceCompany,  
			@FormParam("driverInsuranceAgentName") @DefaultValue("") String driverInsuranceAgentName,
			@FormParam("driverInsurancePhone1") @DefaultValue("") String driverInsurancePhone1, 
			@FormParam("driverInsurancePhone2") @DefaultValue("") String driverInsurancePhone2,
			@FormParam("driverInsuranceNumber") @DefaultValue("") String driverInsuranceNumber,
			
			// car's owner data
			// @FormParam("ownerPersonId") @DefaultValue("-1") String ownerPersonId,
			@FormParam("ownerEmail") @DefaultValue("") String ownerEmail ,
			@FormParam("ownerFirstName") @DefaultValue("") String ownerFirstName,
			@FormParam("ownerLastName") @DefaultValue("") String ownerLastName,
			@FormParam("ownerPhone1") @DefaultValue("") String ownerPhone1,
			@FormParam("ownerPhone2") @DefaultValue("") String ownerPhone2,
			@FormParam("ownerInsuranceCompany") @DefaultValue("") String ownerInsuranceCompany,  
			@FormParam("ownerInsuranceAgentName") @DefaultValue("") String ownerInsuranceAgentName,
			@FormParam("ownerInsurancePhone1") @DefaultValue("") String ownerInsurancePhone1, 
			@FormParam("ownerInsurancePhone2") @DefaultValue("") String ownerInsurancePhone2,
			@FormParam("ownerInsuranceNumber") @DefaultValue("") String ownerInsuranceNumber
			
			) throws Exception {

		// validate input:
		int userIdOk, incidentIdOk; //, driverPersonIdOk, ownerPersonIdOk;
		try {
			userIdOk = Integer.parseInt(userId);
			incidentIdOk = Integer.parseInt(incidentId);
			//driverPersonIdOk = Integer.parseInt(driverPersonId);
			//ownerPersonIdOk = Integer.parseInt(ownerPersonId);
		}
		catch(Exception e) {
			
			ResourceError err = ResourceError.getInstance();
			if (!err.isSet()) {
				err.setMessage("incident not saved, one of the following is not an int (userId, incidentId)");//, driverPersonId, ownerPersonId)");
				err.setStatusCode(400);
				err.setReasonCode(ResourceError.REASON_INVALID_INPUT);
			}
			throw new ResourceException(err);
		}
		
		UserDao userDao = UserDao.getInstance();
		
		if (!userDao.isAuthorized(userIdOk, authHash)) {
			ResourceError err = ResourceError.getInstance();
			if (!err.isSet()) {
				err.setMessage("incident not save, reason:authorization failed");
				err.setStatusCode(400);
				err.setReasonCode(ResourceError.REASON_AUTHENTICATION_FAILED);
			}
			throw new ResourceException(err);
		}
		
		PersonDao personDao = PersonDao.getInstance();
		Person driver = new Person();
		Person owner = new Person();
		Incident incident = new Incident();
		incident.setUserId(userIdOk);
		
		if (incidentIdOk > 0) {
			//TODO fetch real incident and set Incident, Person,Driver instead of initiating a new ones
			//TODO  also check that the incident actually belongs to that userId
		}
		
		//incident.setDate(date); //TODO convert the date to calendar and set it
		incident.setLocation(location);
		incident.setNotes(notes);
		incident.setVehicleLicensePlate(vehicleLicensePlate);
		incident.setVehicleBrand(vehicleBrand);
		incident.setVehicleModel(vehicleModel);
		
		if (driver.getPersonId() > 0
				|| driverEmail != ""
				|| driverFirstName != ""
				|| driverLastName != ""
				|| driverPhone1 != ""
				|| driverPhone2 != ""
				|| driverInsuranceCompany != ""
				|| driverInsuranceAgentName != ""
				|| driverInsurancePhone1 != ""
				|| driverInsurancePhone2 != ""
				|| driverInsuranceNumber != ""
			) {
			driver.setEmail(driverEmail);
			driver.setFirstName(driverFirstName);
			driver.setLastName(driverLastName);
			driver.setPhone1(driverPhone1);
			driver.setPhone2(driverPhone2);
			driver.setInsuranceCompany(driverInsuranceCompany);
			driver.setInsuranceAgentName(driverInsuranceAgentName);
			driver.setInsurancePhone1(driverInsurancePhone1);
			driver.setInsurancePhone2(driverInsurancePhone2);
			driver.setInsuranceNumber(driverInsuranceNumber);
			
			if (!personDao.save(driver) || 0 >= driver.getPersonId()) {
				ResourceError err = ResourceError.getInstance();
				if (!err.isSet()) {
					err.setMessage("incident not saved");
					err.setStatusCode(500);
					err.setReasonCode(ResourceError.REASON_UNKNOWN);
				}
				throw new ResourceException(err);
			}
			incident.setDriver(driver);
		}
		
		if (owner.getPersonId() > 0
				|| ownerEmail != ""
				|| ownerFirstName != ""
				|| ownerLastName != ""
				|| ownerPhone1 != ""
				|| ownerPhone2 != ""
				|| ownerInsuranceCompany != ""
				|| ownerInsuranceAgentName != ""
				|| ownerInsurancePhone1 != ""
				|| ownerInsurancePhone2 != ""
				|| ownerInsuranceNumber != ""
				) {
			owner.setEmail(ownerEmail);
			owner.setFirstName(ownerFirstName);
			owner.setLastName(ownerLastName);
			owner.setPhone1(ownerPhone1);
			owner.setPhone2(ownerPhone2);
			owner.setInsuranceCompany(ownerInsuranceCompany);
			owner.setInsuranceAgentName(ownerInsuranceAgentName);
			owner.setInsurancePhone1(ownerInsurancePhone1);
			owner.setInsurancePhone2(ownerInsurancePhone2);
			owner.setInsuranceNumber(ownerInsuranceNumber);
			
			if (!personDao.save(owner) || 0 >= owner.getPersonId()) {
				ResourceError err = ResourceError.getInstance();
				if (!err.isSet()) {
					err.setMessage("incident not saved");
					err.setStatusCode(500);
					err.setReasonCode(ResourceError.REASON_UNKNOWN);
				}
				throw new ResourceException(err);
			}
			incident.setDriver(owner);
		}
		
		IncidentDao incidentDao = IncidentDao.getInstance();
		
		if (!incidentDao.save(incident) || 0 >= incident.getIncidentId()) {
			ResourceError err = ResourceError.getInstance();
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
