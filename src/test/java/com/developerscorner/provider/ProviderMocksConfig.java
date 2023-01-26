package com.developerscorner.provider;

import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.developerscorner.provider.repository.ChatMessageRepository;
import com.developerscorner.provider.repository.QuestionRepository;
import com.developerscorner.provider.repository.UserRepository;
import com.developerscorner.provider.security.JwtService;
import com.developerscorner.provider.security.MyUserDetailsService;

@TestConfiguration
public class ProviderMocksConfig {
	@Bean
	@Primary
	public UserRepository userRepository() {
		return Mockito.mock(UserRepository.class);
	}
	
	@Bean
	@Primary
	public QuestionRepository questionRepository() {
		return Mockito.mock(QuestionRepository.class);
	}
	
	@Bean
	@Primary
	public ChatMessageRepository chatMessageRepository() {
		return Mockito.mock(ChatMessageRepository.class);
	}
	
	@Bean
	@Primary
	public JwtService jwtService() {
		return Mockito.mock(JwtService.class);
	}
	@Bean
	@Primary
	public PasswordEncoder passwordEncoder() {
		return Mockito.mock(BCryptPasswordEncoder.class);
	}
	@Bean
	@Primary
	public MyUserDetailsService myUserDetailsService() {
		return Mockito.mock(MyUserDetailsService.class);
	}
	@Bean
	@Primary
	public AuthenticationManager authenticationManager() {
		return Mockito.mock(AuthenticationManager.class);
	}
	
}
