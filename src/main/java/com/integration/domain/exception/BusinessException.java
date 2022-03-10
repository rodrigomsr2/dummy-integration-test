package com.integration.domain.exception;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = -4341163110320411787L;
	
	private static final String DEFAULT_ERROR_MESSAGE = "A business error occoured";
	
	public BusinessException(String message) {
		super(message);
	}
	
	public BusinessException() {
		this(DEFAULT_ERROR_MESSAGE);
	}

}
