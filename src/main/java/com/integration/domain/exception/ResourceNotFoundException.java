package com.integration.domain.exception;

public class ResourceNotFoundException extends BusinessException {

	private static final long serialVersionUID = -4341163110320411787L;
	
	private static final String DEFAULT_ERROR_MESSAGE = "The resource was not found";
	
	public ResourceNotFoundException(String message) {
		super(message);
	}
	
	public ResourceNotFoundException() {
		this(DEFAULT_ERROR_MESSAGE);
	}
	
}
