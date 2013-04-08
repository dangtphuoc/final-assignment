package springdata.jpa.exception;

public class RestResponseEntityException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public RestResponseEntityException() {
	}
	
	public RestResponseEntityException(String message) {
		super(message);
	}
}
