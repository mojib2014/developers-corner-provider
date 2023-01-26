package com.developerscorner.provider.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.developerscorner.provider.dto.UserRegistrationDto;
import com.developerscorner.provider.exception.NotFoundException;
import com.developerscorner.provider.model.User;
import com.developerscorner.provider.repository.UserRepository;

@Service
@Transactional
public class UserServiceImple implements UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public User findById(Long id) {
		return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
	}

	public User findByEmail(String email) {
		User user = userRepository.findByEmail(email);
		if(user == null) new NotFoundException(String.format("User not found %s", email));
		
		return user;
	}

	@Override
	public User save(UserRegistrationDto user) {
		User newUser = User.builder()
				.firstName(user.getFirstName())
				.lastName(user.getLastName())
				.nickName(user.getNickName())
				.email(user.getEmail())
				.password(user.getPassword())
				.type(user.getType())
				.createdAt(LocalDateTime.now()).build();
		
		userRepository.saveAndFlush(newUser);
		return newUser;
	}

	@Override
	public void update(Long id, UserRegistrationDto dto) {
		User user = findById(id);
		user.setFirstName(dto.getFirstName());
		user.setLastName(dto.getLastName());
		user.setNickName(dto.getNickName());
		user.setEmail(dto.getEmail());
		user.setPassword(passwordEncoder.encode(dto.getPassword()));
		user.setType(dto.getType());

		userRepository.save(user);
	}

	@Override
	public void delete(Long id) {
		User user = findById(id);
		userRepository.deleteById(user.getId());
	}
}
