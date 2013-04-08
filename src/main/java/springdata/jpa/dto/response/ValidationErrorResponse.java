package springdata.jpa.dto.response;

import springdata.jpa.common.ErrorType;
import springdata.jpa.dto.ResponseBean;

public class ValidationErrorResponse extends ResponseBean {

	public ValidationErrorResponse(ErrorType error) {
		super(error);
	}
}
