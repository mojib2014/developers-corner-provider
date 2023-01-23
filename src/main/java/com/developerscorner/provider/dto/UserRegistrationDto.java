package com.developerscorner.provider.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserRegistrationDto {

	@NotNull(message = "First Name is required and must be at least 5 characters long")
	@Size(min = 5, max = 20, message = "Last Name must be between 5 to 20 charactars long.")
	private String firstName;

	@NotNull(message = "Last Name is required and must be at least 5 characters long")
	@Size(min = 5, max = 20, message = "Last Name must be between 5 to 20 charactars long.")
	private String lastName;

	private String nickName;

	@NotNull(message = "email is required and must be a valid email")
	private String email;

	@NotNull(message = "Password is required and must be at least 6 characters long")
	@Size(min = 6, max = 20, message = "Password must be between 6 to 20 charactars long.")
	private String password;
	
	@NotNull(message = "Type is a required field")
	private String type;
}
