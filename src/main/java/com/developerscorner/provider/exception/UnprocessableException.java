package com.developerscorner.provider.exception;

public class UnprocessableException extends ResponseEntityException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UnprocessableException(String message) {
        super(message);
    }
}
