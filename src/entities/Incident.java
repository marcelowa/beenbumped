/* src/entities/Incident.java */
package entities;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**Represent an incident event, there can be more then one incident to a user*/
@XmlRootElement
public class Incident {
	
	private int incidentId;
	private int userId;
	private Date date;
	private String notes;
	private String location;
	private String vehicleLicensePlate;
	private String vehicleBrand;
	private String vehicleModel;
	private Person driver;
	private Person owner;
	
	
	/**Incident constructor 
	 * Parameters:
	 * 		@param incidentId integer
	 *		@param userId integer
	 * 		@param date Date
	 * 		@param location String (will change to latitude and longitude in the future)
	 * 		@param note String
	 * 		@param driver Person, the "other" side of the incident
	 * 		@param owner Person, our data*/
	public Incident(int incidentId, int userId, Date date,String location, String notes, Person driver, Person owner) {
		this.incidentId = incidentId;
		this.userId = userId;
		this.date = date;
		this.location = location;
		this.notes = notes;
		this.driver = driver;
		this.owner = owner;
	}

	/**An empty constructor*/
	public Incident() {
		//this constructor is needed so jaxb doesn't throw an exception related to no-arg constructor
	}

	/**An incidentId getter
	 * Returns:
	 * 		@return integer*/
	public int getIncidentId() {
		return incidentId;
	}

	/**An incidentId setter
	 * Parameters:
	 * 		@param incidentId integer*/
	public void setIncidentId(int incidentId) {
		this.incidentId = incidentId;
	}

	/**A userId getter
	 * Returns:
	 * 		@return integer*/
	public int getUserId() {
		return userId;
	}

	/**A userId setter
	 * Parameters:
	 * 		@param userId integer*/
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**A date getter
	 * Returns:
	 * 		@return Date */
	@XmlTransient
	public Date getDate() {
		return date;
	}
	
	/**A date getter
	 * Returns:
	 * 		@return String*/
	@XmlElement(name="date")
	public String getDateString() {
		if (null == date) {
			return null;
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");//yyyy-mm-dd hh:mm:ss.fffffffff
		return df.format(date);
	}

	/**A date setter
	 * Parameters:
	 * 		@param date Date*/
	public void setDate(Date date) {
		this.date = date;
	}

	/**A location getter
	 * Returns:
	 * 		@return String*/
	public String getLocation() {
		return location;
	}

	/**A location setter
	 * Parameters:
	 * 		@param location String*/
	public void setLocation(String location) {
		this.location = location;
	}

	/**A notes getter
	 * Returns:
	 * 		@return String*/
	public String getNotes() {
		return notes;
	}

	/**A notes setter
	 * Parameters:
	 * 		@param notes String*/
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**A vehicle license plate getter
	 * Returns:
	 * 		@return String*/
	public String getVehicleLicensePlate() {
		return vehicleLicensePlate;
	}

	/**A vehicle license plate setter
	 * Parameters:
	 * 		@param vehicle license plate String*/
	public void setVehicleLicensePlate(String vehicleLicensePlate) {
		this.vehicleLicensePlate = vehicleLicensePlate;
	}

	/**A vehicle brand getter
	 * Returns:
	 * 		@return String*/
	public String getVehicleBrand() {
		return vehicleBrand;
	}

	/**A vehicle brand setter
	 * Parameters:
	 * 		@param vehicle brand String*/
	public void setVehicleBrand(String vehicleBrand) {
		this.vehicleBrand = vehicleBrand;
	}

	/**A vehicle model getter
	 * Returns:
	 * 		@return String*/
	public String getVehicleModel() {
		return vehicleModel;
	}

	/**A vehicle model setter
	 * Parameters:
	 * 		@param vehicle model String*/
	public void setVehicleModel(String vehicleModel) {
		this.vehicleModel = vehicleModel;
	}
	
	/**A driver getter, the other side of the incident
	 * Returns:
	 * 		@return Person*/
	public Person getDriver() {
		return driver;
	}

	/**An driver setter, the other side of the incident
	 * Parameters:
	 * 		@param driver Person*/
	public void setDriver(Person driver) {
		this.driver = driver;
	}

	/**A owner getter, our data
	 * Returns:
	 * 		@return Person*/
	public Person getOwner() {
		return owner;
	}

	/**A owner setter, our data
	 * Parameters:
	 * 		@param owner Person*/
	public void setOwner(Person owner) {
		this.owner = owner;
	}
}
