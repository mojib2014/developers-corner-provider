package com.developerscorner.provider.service;

import java.time.LocalDateTime;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.developerscorner.provider.dto.AuthResponse;
import com.developerscorner.provider.dto.UserLoginDto;
import com.developerscorner.provider.dto.UserRegistrationDto;
import com.developerscorner.provider.exception.ConflictException;
import com.developerscorner.provider.model.User;
import com.developerscorner.provider.repository.UserRepository;
import com.developerscorner.provider.security.JwtService;

@Service
@Transactional
public class AuthService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;

	public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService,
			AuthenticationManager authManager) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
		this.authenticationManager = authManager;
	}

	public AuthResponse register(UserRegistrationDto dto) {
		User user = userRepository.findByEmail(dto.getEmail()).orElse(null);
		if (user != null)
			throw new ConflictException("User already registered please login");
		else {
			user = User.builder().firstName(dto.getFirstName()).lastName(dto.getLastName()).nickName(dto.getNickName())
					.email(dto.getEmail()).password(passwordEncoder.encode(dto.getPassword())).type(dto.getType())
					.createdAt(LocalDateTime.now()).build();

			userRepository.save(user);
			String jwtToken = jwtService.generateToken(user);

			return new AuthResponse(jwtToken);
		}
	}

	public AuthResponse authenticate(UserLoginDto dto) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));
		User user = userRepository.findByEmail(dto.getEmail())
				.orElseThrow(() -> new UsernameNotFoundException("Username not found"));
		;
		String jwtToken = jwtService.generateToken(user);
		return new AuthResponse(jwtToken);
	}
}