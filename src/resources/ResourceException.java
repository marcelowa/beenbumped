package resources;

import entities.ResourceError;

/**Handles the project exceptions*/
public class ResourceException extends RuntimeException {

	private static final long serialVersionUID = 3222518051740261790L;

	private ResourceError error;
	private int statusCode;

	/**ResourceException constructor 
	 * Parameters:
	 * 		@param error ResourceError*/
	public ResourceException(ResourceError error) {
    	super(error.getMessage());
    	this.error = error;
    	this.statusCode = error.getStatusCode();
    }

	/**An Error getter
	 * Returns:
	 * 		@return ResourceError*/
	public ResourceError getError() {
		return error;
	}

	/**An Error setter
	 * Parameters:
	 * 		@param error ResourceError*/
	public void setError(ResourceError error) {
		this.error = error;
	}
	
	/**A StatusCode getter
	 * Returns:
	 * 		@return integer*/
    public int getStatusCode() {
		return statusCode;
	}

    /**A StatusCode setter
	 * Parameters:
	 * 		@param statusCode integer*/
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
}
