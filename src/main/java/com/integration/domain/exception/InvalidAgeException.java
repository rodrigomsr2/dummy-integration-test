package com.integration.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidAgeException extends BusinessException {

	private static final long serialVersionUID = -4341163110320411787L;
	
	private static final String DEFAULT_ERROR_MESSAGE = "Age must be over 18";
	
	public InvalidAgeException(String message) {
		super(message);
	}
	
	public InvalidAgeException() {
		this(DEFAULT_ERROR_MESSAGE);
	}
	
}
