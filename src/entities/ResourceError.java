package entities;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ResourceError {

	static final public int REASON_UNKNOWN = -1;
	static final public int REASON_INVALID_INPUT = 1;
	static final public int REASON_AUTHENTICATION_FAILED = 2;
	
	static private ResourceError instance; 
	private int statusCode;
	private int reasonCode;
	private String message;
	
	public ResourceError() {
		//this constructor is needed so jaxb doesn't throw an exception related to no-arg constructor
	}
	
	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public int getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(int reasonCode) {
		this.reasonCode = reasonCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSet() {
		return getStatusCode() == 0;
	}
	
	static public ResourceError getInstance() {
		if (null == instance) {
			instance = new ResourceError();
		}
		return instance;
	}
}
