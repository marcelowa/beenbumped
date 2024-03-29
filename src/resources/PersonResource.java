/* src/resources/PersonResource.java */
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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import dao.PersonDao;
import entities.Person;

/**Handles the inserts of a person to the DB*/
@Path("/person")
public class PersonResource {

	/**Retrieve a person data from the DB
	 * Parameters:
	 * 		@param PersonId integer
	 * Returns:
	 * 		@return Person
	 * Throws:
	 * 		@exception RuntimeException
	 * */
	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Person getById(@PathParam("id") int id) {
		Person person = PersonDao.getInstance().getById(id);
		if (null == person) {
			throw new RuntimeException("Person::get with id " + id
					+ " not found");
		}
		return person;
	}

	/**Inserts a person data to the DB
	 * Parameters:
	 *  	@param UriInfo uriInfo
	 *  	@param HttpServletRequest servletRequest
	 * 		@param HttpServletResponse servletResponse
	 * 		@param personeId integer, default value -1
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
	 * Throws:
	 * 		@exception Exception
	 **/
	@POST
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void save(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest servletRequest,
			@Context HttpServletResponse servletResponse,
			@FormParam("personId") @DefaultValue("-1") int personId,
			@FormParam("email") @DefaultValue("") String email ,
			@FormParam("firstName") @DefaultValue("") String firstName,
			@FormParam("lastName") @DefaultValue("") String lastName,
			@FormParam("idNumber") @DefaultValue("") String idNumber,
			@FormParam("city") @DefaultValue("") String city,
			@FormParam("streetName") @DefaultValue("") String streetName,
			@FormParam("houseNumber") @DefaultValue("-1") int houseNumber,
			@FormParam("addressDetails") @DefaultValue("") String addressDetails,
			@FormParam("zipcode") @DefaultValue("-1") int zipcode,
			@FormParam("phone1") @DefaultValue("") String phone1,
			@FormParam("phone2") @DefaultValue("") String phone2,
			@FormParam("insuranceCompany") @DefaultValue("") String insuranceCompany,
			@FormParam("insuranceAgentName") @DefaultValue("") String insuranceAgentName,
			@FormParam("insurancePhone1") @DefaultValue("") String insurancePhone1,
			@FormParam("insurancePhone2") @DefaultValue("") String insurancePhone2,
			@FormParam("insuranceNumber") @DefaultValue("") String insuranceNumber
			) throws Exception {
		
		Person person = new Person();
		person.setPersonId(personId);
		person.setEmail((String)email);
		person.setFirstName(firstName);
		person.setLastName(lastName);
		person.setIdNumber(idNumber);
		person.setCity(city);
		person.setStreetName(streetName);
		//TODO handle better integers, it crushes the application if non integer string is passed here
		person.setHouseNumber(houseNumber);
		person.setAddressDetails(addressDetails);
		//TODO handle better integers, it crushes the application if non integer string is passed here
		person.setZipcode(zipcode);
		person.setPhone1(phone1);
		person.setPhone2(phone2);
		person.setInsuranceCompany(insuranceCompany);
		person.setInsuranceAgentName(insuranceAgentName);
		person.setInsurancePhone1(insurancePhone1);
		person.setInsurancePhone2(insurancePhone2);
		person.setInsuranceNumber(insuranceNumber);
		if (!PersonDao.getInstance().save(person)){
			throw new Exception("Person was not saved succesfully");
		}
		URI personUri = uriInfo.getBaseUriBuilder().path(PersonResource.class).path("/" + person.getPersonId()).build();
		servletResponse.sendRedirect(personUri.toString());
	}
	
	
}
