package com.developerscorner.provider.exception;

/**
 * Super class for conflict exceptions.
 * <p>Status Code: <code>409 CONFLICT</code></p>
 * <p>
 *     <em>Ex. Email already exists.</em>
 * </p>
 * <p>
 *     <em>Extends <code>{@link RuntimeException}</code> to allow it to be caught by the {@link GlobalExceptionHandler}.</em>
 * </p>
 */
public class ConflictException extends ResponseEntityException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ConflictException(String message) {
        super(message);
    }
}
