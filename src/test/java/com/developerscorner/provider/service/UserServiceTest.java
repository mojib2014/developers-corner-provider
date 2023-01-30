package com.developerscorner.provider.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.testng.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyString;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.developerscorner.provider.ProviderMocksConfig;
import com.developerscorner.provider.dto.UserRegistrationDto;
import com.developerscorner.provider.exception.NotFoundException;
import com.developerscorner.provider.model.User;
import com.developerscorner.provider.repository.UserRepository;

@ActiveProfiles("test")
@SpringBootTest(classes = { UserService.class, UserServiceImple.class, ProviderMocksConfig.class })
@TestPropertySource(locations = "classpath:test.yml")
public class UserServiceTest extends AbstractTestNGSpringContextTests {

	@Autowired
	private UserService userService;
	@Autowired
	private UserRepository userRepository;

	private List<User> users;
	private User user = null;

	@BeforeClass
	public void setup() {
		MockitoAnnotations.openMocks(this);

		user = User.builder().id(1L).firstName("Johnn").lastName("Doeee").type("Mentor").email("john.doeee@email.com")
				.password("123456").build();

		users = new ArrayList<>();
		User user1 = User.builder().firstName("user1").lastName("user1").type("Student").email("user1.user1@email.com")
				.password("123456").build();
		User user2 = User.builder().firstName("user2").lastName("user2").type("Student").email("user2.user2@email.com")
				.password("123456").build();
		User user3 = User.builder().firstName("user3").lastName("user3").type("Student").email("user3.user3@email.com")
				.password("123456").build();

		users.add(user1);
		users.add(user2);
		users.add(user3);
	}

	@Test
	public void shouldFindAllUsers() {
		when(userRepository.findAll()).thenReturn(users);
		
		userService.findAll();
		
		verify(userRepository).findAll();
	}
	
	@Test
	public void shouldFindUserById() {
		when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
		
		User result = userService.findById(user.getId());
		
		verify(userRepository, atMost(2)).findById(anyLong());
		assertNotNull(result);
	}

	@Test
	public void shouldFindUserByEmail() {
		when(userRepository.findByEmail(anyString())).thenReturn(user);
		
		User result = userService.findByEmail(user.getEmail());
		
		verify(userRepository).findByEmail(user.getEmail());
		assertNotNull(result);
	}
	
	@Test
	public void shouldSaveAUser() {
		UserRegistrationDto dto = UserRegistrationDto.builder()
				.firstName("my new user")
				.lastName("user new")
				.type("user type")
				.email("my.new.user@email.com")
				.password("123456").build();
		
		when(userRepository.saveAndFlush(any(User.class))).thenReturn(any(User.class));
		
		User user = userService.save(dto);
		
		verify(userRepository).saveAndFlush(any(User.class));
		assertNotNull(user);
	}
	
	@Test
	public void shouldUpdateAUser() {
		UserRegistrationDto dto = UserRegistrationDto.builder()
				.firstName("my new user")
				.lastName("user new")
				.type("user type")
				.email("my.new.user@email.com")
				.password("123456").build();
		
		User updated = User.builder()
				.id(1L)
				.firstName("my new user")
				.lastName("user new")
				.type("user type")
				.email("my.new.user@email.com").build();
		
		//when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
		when(userRepository.save(any(User.class))).thenReturn(updated);
		
		userService.update(user.getId(), dto);
		
		verify(userRepository).save(updated);
	}
	
	@Test
	public void shouldDeleteAUser() {
		when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
		
		userService.delete(user.getId());
		
		verify(userRepository).deleteById(anyLong());
	}
	
	// ===================== Negative tests =====================
	
	@Test(expectedExceptions = NotFoundException.class, expectedExceptionsMessageRegExp = "User not found") 
	public void shouldThrowNotFoundExceptionIfUserByIdNotExists() {
		when(userRepository.findById(1000L)).thenReturn(Optional.empty());
		
		userService.findById(1000L);
		
		verify(userRepository).findById(1000L);
	}
	
	@Test(expectedExceptions = NotFoundException.class) 
	public void shouldThrowNotFoundExceptionIfUserByEmailNotExists() {
		when(userRepository.findByEmail(anyString())).thenReturn(null);
		
		userService.findByEmail("new.user@email.com");
		verify(userRepository, atMost(2)).findByEmail(anyString());
	}
}
