package com.developerscorner.provider.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyString;

import static org.mockito.Mockito.atMostOnce;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.developerscorner.provider.ProviderMocksConfig;
import com.developerscorner.provider.dto.AuthResponse;
import com.developerscorner.provider.dto.UserLoginDto;
import com.developerscorner.provider.dto.UserRegistrationDto;
import com.developerscorner.provider.exception.ConflictException;
import com.developerscorner.provider.model.User;
import com.developerscorner.provider.repository.UserRepository;

@ActiveProfiles("test")
@SpringBootTest(classes = { AuthService.class, ProviderMocksConfig.class })
@TestPropertySource(locations = "classpath:test.yml")
public class AuthServiceTest extends AbstractTestNGSpringContextTests {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AuthService authService;
	private UserRegistrationDto dto = null;
	private User user = null;

	@BeforeClass
	void setUp() {
		MockitoAnnotations.openMocks(this);
		user = User.builder().firstName("first name").lastName("last name").nickName("nick name").type("Mentor")
				.email("first.name@email.com").password("123456").build();
	}

	@Test
	public void shouldRegisterAUser() {
		dto = UserRegistrationDto.builder().firstName("first").lastName("lasta").nickName("test").type("Student")
				.email("last.first@email.com").password("123456").build();
		User registered = User.builder().firstName(dto.getFirstName()).lastName(dto.getLastName())
				.nickName(dto.getNickName()).type(dto.getType()).email(dto.getEmail()).password(dto.getPassword())
				.build();

		when(userRepository.findByEmail(anyString())).thenReturn(null);
		when(userRepository.save(any(User.class))).thenReturn(any(User.class));

		AuthResponse res = authService.register(dto);
		verify(userRepository, atMostOnce()).save(registered);
		assertNotNull(res);
	}

	@Test
	public void shouldAuthenticateAUser() {

		UserLoginDto loginDto = UserLoginDto.builder().email("new1.user@email.com").password("123456").build();

		when(userRepository.findByEmail(loginDto.getEmail())).thenReturn(user);

		AuthResponse res = authService.authenticate(loginDto);
		assertNotNull(res);
		verify(userRepository).findByEmail(any(String.class));
	}

	@Test(expectedExceptions = ConflictException.class)
	public void shouldThrowConflictExceptionIfUserExists() {
		dto = UserRegistrationDto.builder().firstName("first name").lastName("last name").nickName("nick name")
				.type("Mentor").email("first.name@email.com").password("123456").build();

		when(userRepository.findByEmail(dto.getEmail())).thenReturn(user);
		when(userRepository.save(user)).thenReturn(null);

		authService.register(dto);

		verify(userRepository, atMostOnce()).findByEmail(any(String.class));
	}

	@Test(expectedExceptions = UsernameNotFoundException.class)
	public void shouldThrowUsernameNotFoundExceptionIfUserNotExists() {
		UserLoginDto loginDto = UserLoginDto.builder().email("test14555.user87@email.com").password("123456").build();
		when(userRepository.findByEmail(loginDto.getEmail())).thenThrow(UsernameNotFoundException.class);
		authService.authenticate(loginDto);
		verify(userRepository).findByEmail(anyString());
	}
}
