package springdata.jpa.exception;

public class ValidationException extends Throwable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2063208264910334320L;

	public ValidationException() {
	}
	
	public ValidationException(String message) {
		super(message);
	}
}
