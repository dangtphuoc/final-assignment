package springdata.jpa.common;

public enum ErrorType {
	
	SUCCESS(0, "Success"), FAIL(-1, "Failed");
	
	private final int code;
	private final String message;
	
	private ErrorType(int code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public int getCode() {
		return code;
	}
	
	public String getMessage() {
		return message;
	}
	
	@Override
	public String toString() {
		return code + ": " + message;
	}
}
