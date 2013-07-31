package entities;

import entities.Incident;

/**Represent a single page (web page) of incidents*/
public class IncidentPage {
	
	private Incident[] incedents;
	private int totalLines;
	
	/**An empty constructor*/
	public IncidentPage () {
		//this constructor is needed so jaxb doesn't throw an exception related to no-arg constructor
	}
	
	/**Incident constructor
	 * Parameters:
	 * 		@param incident Incident[]
	 * 		@param totalLines integer
	 * */
	public IncidentPage (Incident[] incident, int totalLines){
		this.incedents = incident;
		this.totalLines = totalLines;
	}

	/**An incidents getter
	 * Returns:
	 * 		@return Incident[]*/
	public Incident[] getIncidents() {
		return incedents;
	}

	/**An incidents setter
	 * Parameters:
	 * 		@param incidents Incident[]*/
	public void setIncidents(Incident[] incedents) {
		this.incedents = incedents;
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
