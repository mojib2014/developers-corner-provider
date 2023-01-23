package com.developerscorner.provider.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.RequiredArgsConstructor;


@Data
@RequiredArgsConstructor
public class UserLoginDto {

	@NotNull(message = "Email is required and must be a valid email")
	private String email;
	
	@NotNull(message = "Password is required and must be at least 6 characters")
	@Size(message = "Password must be between 6 to 20 charactars long.", min = 6, max = 20)
	private String password;
	
}
