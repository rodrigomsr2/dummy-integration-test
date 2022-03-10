package com.integration.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidNameException extends BusinessException {

	private static final long serialVersionUID = -4341163110320411787L;
	
	private static final String DEFAULT_ERROR_MESSAGE = "Name must have at least 10 characters";
	
	public InvalidNameException(String message) {
		super(message);
	}
	
	public InvalidNameException() {
		this(DEFAULT_ERROR_MESSAGE);
	}
	
}
