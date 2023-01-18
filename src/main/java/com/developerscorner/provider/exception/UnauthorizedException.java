package com.developerscorner.provider.exception;

public class UnauthorizedException extends ResponseEntityException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UnauthorizedException(String message) {
        super(message);
    }
}
