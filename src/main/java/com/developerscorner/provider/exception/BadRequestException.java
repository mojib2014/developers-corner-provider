package com.developerscorner.provider.exception;

/**
 * Super class for bad request exceptions.
 * <p>Status Code: <code>400 BAD REQUEST</code></p>
 * <p>
 *     <em>Extends <code>{@link RuntimeException}</code> to allow it to be caught by the {@link GlobalExceptionHandler}.</em>
 * </p>
 */
public class BadRequestException extends ResponseEntityException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BadRequestException(String message) {
		super(message);
	}
}
