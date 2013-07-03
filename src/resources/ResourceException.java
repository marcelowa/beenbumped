package resources;

import entities.ResourceError;

public class ResourceException extends RuntimeException {

	private static final long serialVersionUID = 3222518051740261790L;

	private ResourceError error;
	private int statusCode;

	public ResourceException(ResourceError error) {
    	super(error.getMessage());
    	this.error = error;
    	this.statusCode = error.getStatusCode();
    }

	public ResourceError getError() {
		return error;
	}

	public void setError(ResourceError error) {
		this.error = error;
	}
	
    public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
}
