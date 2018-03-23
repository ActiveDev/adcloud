package com.activedevsolutions.cloud.templateservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Provides a central spot to handle any exceptions that may arise from the REST controller.
 *
 */
@Component
@ControllerAdvice
public class TemplateExceptionHandler {
	/**
	 * Exception handler that will handle anything that derives from the
	 * ResourceNotFoundException class. It will return a HTTP 404.
	 * 
	 * @param e is the Exception object containing the exception thrown
	 * @return RestError containing the exception message
	 */
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(value = ResourceNotFoundException.class)
	public RestError handleNotFoundException(ResourceNotFoundException e) {
		final RestError restError = new RestError();
		restError.setMessage(e.getMessage());

		return restError;
	}
	
	/**
	 * Exception handler that will handle anything that derives from the
	 * Exception class. It will return a HTTP 400 for all exceptions
	 * 
	 * @param e is the Exception object containing the exception thrown
	 * @return RestError containing the exception message
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = Exception.class)
	public RestError handleBaseException(Exception e) {
		final RestError restError = new RestError();
		restError.setMessage(e.getMessage());

		return restError;
	}
}
