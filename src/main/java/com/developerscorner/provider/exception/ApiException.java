package com.developerscorner.provider.exception;

import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;

public class ApiException {

	private String message;
	private HttpStatus httpStatus;
	private ZonedDateTime timestamp;
	
	public ApiException(String message, HttpStatus httpStatus, ZonedDateTime timestamp) {
		super();
		this.message = message;
		this.httpStatus = httpStatus;
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public ZonedDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(ZonedDateTime timestamp) {
		this.timestamp = timestamp;
	}

}
