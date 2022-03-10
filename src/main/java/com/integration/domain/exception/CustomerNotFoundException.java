package com.integration.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CustomerNotFoundException extends ResourceNotFoundException {

	private static final long serialVersionUID = -4341163110320411787L;
	
	private static final String DEFAULT_ERROR_MESSAGE = "Customer was not found";
	
	public CustomerNotFoundException(String message) {
		super(message);
	}
	
	public CustomerNotFoundException() {
		this(DEFAULT_ERROR_MESSAGE);
	}
	
}
