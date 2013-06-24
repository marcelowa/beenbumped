package entities;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ResourceError {

	private int code;
	private String message;
	
	public ResourceError() {
		//this constructor is needed so jaxb doesn't throw an exception related to no-arg constructor
	}
	
	public ResourceError(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}
	
	public void setCode(int code) {
		this.code = code;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
}
