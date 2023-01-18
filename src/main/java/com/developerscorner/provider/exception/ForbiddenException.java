package com.developerscorner.provider.exception;

/**
 * 403 Forbidden
 */
public class ForbiddenException extends ResponseEntityException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ForbiddenException(String message) {
        super(message);
    }
}