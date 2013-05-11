package entities;

import java.util.Calendar;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Incident {
	
	private int incidentId;
	private int userId;
	private Calendar date;
	private int satausId;
	private String location;
	private String notes;
	private Person driver;
	private Person vehicleOwner;
	
	
	public Incident(int incidentId, int userId, Calendar date, int satausId,
			String location, String notes, Person driver,
			Person vehicleOwner) {
		
		this.incidentId = incidentId;
		this.userId = userId;
		this.date = date;
		this.satausId = satausId;
		this.location = location;
		this.notes = notes;
		this.driver = driver;
		this.vehicleOwner = vehicleOwner;
	}

	public Incident() {
		//this constructor is needed so jaxb doesn't throw an exception related to no-arg constructor
	}

	public int getIncidentId() {
		return incidentId;
	}

	public void setIncidentId(int incidentId) {
		this.incidentId = incidentId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public int getSatausId() {
		return satausId;
	}

	public void setSatausId(int satausId) {
		this.satausId = satausId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Person getDriver() {
		return driver;
	}

	public void setDriver(Person driver) {
		this.driver = driver;
	}

	public Person getVehicleOwner() {
		return vehicleOwner;
	}

	public void setVehicleOwner(Person vehicleOwner) {
		this.vehicleOwner = vehicleOwner;
	}
	
	
	
}
