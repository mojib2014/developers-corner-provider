package com.developerscorner.provider.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j(topic = "Global Exception Handler")
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler({ NotFoundException.class, UsernameNotFoundException.class })
	public ResponseEntity<ApiException> handleNotFoundException(ResponseEntityException e) {
		log.error("404 Not Found: {}", e.toString());
		ApiException ex = new ApiException(e.getMessage(), HttpStatus.NOT_FOUND, ZonedDateTime.now());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex);
	}

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ApiException> handleBadRequestException(ResponseEntityException e) {
		log.error("400 Bad Request: {}", e.toString());
		ApiException ex = new ApiException(e.getMessage(), HttpStatus.BAD_REQUEST, ZonedDateTime.now());
		return ResponseEntity.badRequest().body(ex);
	}

	@ExceptionHandler(ConflictException.class)
	public ResponseEntity<ApiException> handleConflictException(ResponseEntityException e) {
		log.error("409 Conflict: {}", e.toString());
		ApiException ex = new ApiException(e.getMessage(), HttpStatus.CONFLICT, ZonedDateTime.now());
		return ResponseEntity.status(HttpStatus.CONFLICT).body(ex);
	}

	@ExceptionHandler(UnprocessableException.class)
	public ResponseEntity<ApiException> handleNotCreatedException(ResponseEntityException e) {
		log.error("422 Unprocessable Entity: {}", e.toString());
		ApiException ex = new ApiException(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY, ZonedDateTime.now());
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ex);
	}

	@ExceptionHandler({ ForbiddenException.class, AccessDeniedException.class })
	public ResponseEntity<ApiException> handleForbiddenException(Exception e) {
		log.error("403 Forbidden: {}", e.getMessage());
		ApiException ex = new ApiException(e.getMessage(), HttpStatus.FORBIDDEN, ZonedDateTime.now());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex);

	}

	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<ApiException> handleUnauthorizedException(ResponseEntityException e) {
		log.error("401 Unauthorized: {}", e.getMessage());
		ApiException ex = new ApiException(e.getMessage(), HttpStatus.UNAUTHORIZED, ZonedDateTime.now());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex);

	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		log.error(e.toString());
		List<String> fieldErrors = e.getFieldErrors().stream().map(FieldError::getDefaultMessage)
				.collect(Collectors.toList());
		return ResponseEntity.badRequest().body(fieldErrors);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<List<String>> handleConstraintViolation(ConstraintViolationException e) {
		log.error(e.toString());
		List<String> violations = e.getConstraintViolations().stream().map(ConstraintViolation::getMessage)
				.collect(Collectors.toList());
		return ResponseEntity.badRequest().body(violations);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleDefaultException(Exception e) {
		e.printStackTrace();
		log.error(e.toString());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Please contact your administrator.");
	}
}
