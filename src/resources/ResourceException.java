package resources;

public class ResourceException extends RuntimeException {

	private static final long serialVersionUID = 3222518051740261790L;
	private String reason;
    private int statusCode;
    private int errorCode;

    public ResourceException(String message, String reason, int statusCode, int errorCode) {
        super(message);
        this.reason = reason;
        this.statusCode = statusCode;
        this.errorCode = errorCode;
    }

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
    
    
}
