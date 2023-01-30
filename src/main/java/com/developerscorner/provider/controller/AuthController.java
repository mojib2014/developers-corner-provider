package com.developerscorner.provider.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.developerscorner.provider.dto.AuthResponse;
import com.developerscorner.provider.dto.UserLoginDto;
import com.developerscorner.provider.dto.UserRegistrationDto;
import com.developerscorner.provider.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final AuthService authService;
	private static Logger logger = LoggerFactory.getLogger(UserController.class);
	
	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	/**
	 * Registers a user and returns a token in the response body
	 * @param UserRegistrationDto
	 * @return AuthResponse with JWT token
	 */
	@Operation(summary = "Register a user")
	@ApiResponse(responseCode = "200", description = "User registered succefully")
	@PostMapping(
			value = "/register", 
			consumes = {MediaType.APPLICATION_JSON_VALUE}, 
			produces = {MediaType.APPLICATION_JSON_VALUE}
			)
	public ResponseEntity<AuthResponse> register(@Valid @RequestBody UserRegistrationDto dto) {
		logger.info("register contrroler {}", dto);
		AuthResponse authResponse = authService.register(dto);
	
		return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.OK);
	}

	@Operation(summary = "Logs in a user")
	@ApiResponse(responseCode = "200", description = "User logged in succefully")
	@PostMapping(
			value = "/login", 
			consumes = {MediaType.APPLICATION_JSON_VALUE}, 
			produces = {MediaType.APPLICATION_JSON_VALUE}
			)
	public ResponseEntity<AuthResponse> login(@Valid @RequestBody UserLoginDto dto) {
		AuthResponse authResponse = authService.authenticate(dto);
		
		return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.OK);
	}

}

