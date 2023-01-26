package com.developerscorner.provider.service;

import com.developerscorner.provider.dto.AuthResponse;
import com.developerscorner.provider.dto.UserLoginDto;
import com.developerscorner.provider.dto.UserRegistrationDto;

public interface AuthService {

	AuthResponse register(UserRegistrationDto dto);
	AuthResponse authenticate(UserLoginDto dto);
}
