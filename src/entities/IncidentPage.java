/* src/entities/IncidentPage.java */
package entities;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import entities.Incident;

/**Represent a single page (web page) of incidents*/
@XmlRootElement
public class IncidentPage {
	
	private Incident[] incidents;
    
	private int totalLines;
	
	/**An empty constructor*/
	public IncidentPage () {
		//this constructor is needed so jaxb doesn't throw an exception related to no-arg constructor
	}
	
	/**Incident constructor
	 * Parameters:
	 * 		@param incidents Incident[]
	 * 		@param totalLines integer
	 * */
	public IncidentPage (Incident[] incidents, int totalLines){
		this.incidents = incidents;
		this.totalLines = totalLines;
	}

	/**An incidents getter
	 * Returns:
	 * 		@return Incident[]*/
	@XmlElement(name="incident")
	@XmlElementWrapper(name="incidents")
	public Incident[] getIncidents() {
		return incidents;
	}

	/**An incidents setter
	 * Parameters:
	 * 		@param incidents Incident[]*/
	public void setIncidents(Incident[] incidents) {
		this.incidents = incidents;
	}

	/**An total lines getter, return the total number of incidents that we have, for the pager.
	 * Returns:
	 * 		@return integer*/
	public int getTotalLines() {
		return totalLines;
	}

	/**An total lines setter, sets the total number of incidents that we have, for the pager.
	 * Parameters:
	 * 		@param total lines integer*/
	public void setTotalLines(int totalLines) {
		this.totalLines = totalLines;
	}
	
	
	
}
