package springdata.jpa.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import springdata.jpa.common.ErrorType;
import springdata.jpa.dto.ErrorMessage;
import springdata.jpa.dto.ResponseBean;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends
		ResponseEntityExceptionHandler {
	
	@ExceptionHandler(value = { ValidationException.class})
	@ResponseStatus(value=HttpStatus.CONFLICT)
	@ResponseBody
	protected ResponseBean handleException(ValidationException ex) {
		return new ResponseBean(ErrorType.FAIL);
	}
	
	@ExceptionHandler(value={RestResponseEntityException.class})
	@ResponseStatus(value=(HttpStatus.EXPECTATION_FAILED))
	@ResponseBody
	protected ErrorMessage handleException(RuntimeException ex) {
		return new ErrorMessage(ex.getMessage());
	}
}