package entities;

import entities.Incident;

public class IncidentPage {
	
	private Incident[] incedents;
	private int totalLines;
	
	public IncidentPage () {
		//this constructor is needed so jaxb doesn't throw an exception related to no-arg constructor
	}
	
	public IncidentPage (Incident[] incedents, int totalLines){
		this.incedents = incedents;
		this.totalLines = totalLines;
	}

	public Incident[] getIncedents() {
		return incedents;
	}

	public void setIncedents(Incident[] incedents) {
		this.incedents = incedents;
	}

	public int getTotalLines() {
		return totalLines;
	}

	public void setTotalLines(int totalLines) {
		this.totalLines = totalLines;
	}
	
	
	
}
