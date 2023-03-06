package com.developerscorner.provider;

import java.time.LocalDateTime;
import javax.transaction.Transactional;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.developerscorner.provider.model.Question;
import com.developerscorner.provider.model.Role;
import com.developerscorner.provider.model.User;
import com.developerscorner.provider.repository.QuestionRepository;
import com.developerscorner.provider.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AppUserDataLoader implements ApplicationListener<ContextRefreshedEvent> {

	boolean alreadySetup = false;
	private final UserRepository userRepository;
	private final QuestionRepository questionRepository;
	private final PasswordEncoder passwordEncoder;
	
	@Override
	@Transactional
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if(alreadySetup) return;
		
		createUserIfNotExists();
	}

	@Transactional
	void createUserIfNotExists() {
		User user = userRepository.findByEmail("testuser@email.com");
		
		if(user == null) {
			user = User.builder()
					.firstName("testuser")
					.lastName("usertest")
					.nickName("test")
					.createdAt(LocalDateTime.now())
					.type("Student")
					.email("testuser@email.com")
					.role(Role.USER)
					.password(passwordEncoder.encode("123456")).build();
		}
		userRepository.save(user);
		createQuestionForUser(user);
	}
	
	@Transactional
	void createQuestionForUser(User user) {
		Question q1 = Question.builder()
				.user(user)
				.createdAt(LocalDateTime.now()) 
				.username("testuser")
				.tags("Java")
				.question("Java data types")
				.build();
		Question q2 = Question.builder()
				.user(user)
				.createdAt(LocalDateTime.now()) 
				.username("testuser")
				.tags("Java")
				.question("Java spring mvc")
				.build();
		questionRepository.save(q1);
		questionRepository.save(q2);
	}
}
