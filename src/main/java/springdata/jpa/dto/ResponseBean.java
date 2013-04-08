package springdata.jpa.dto;

import javax.xml.bind.annotation.XmlRootElement;

import springdata.jpa.common.ErrorType;


@XmlRootElement(name = "response")
public class ResponseBean {
	private int code;
	private String message;
	
	public ResponseBean() {
	}
	public ResponseBean(ErrorType error) {
		this.code = error.getCode();
		this.message = error.getMessage();
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
