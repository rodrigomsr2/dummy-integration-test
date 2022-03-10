package com.integration.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CustomerAlreadyExistsException extends BusinessException {

	private static final long serialVersionUID = -4341163110320411787L;
	
	private static final String DEFAULT_ERROR_MESSAGE = "Customer cannot be registered twice";
	
	public CustomerAlreadyExistsException(String message) {
		super(message);
	}
	
	public CustomerAlreadyExistsException() {
		this(DEFAULT_ERROR_MESSAGE);
	}
	
}
