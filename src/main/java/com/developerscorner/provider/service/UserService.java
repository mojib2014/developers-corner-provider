package com.developerscorner.provider.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.developerscorner.provider.dto.UserRegistrationDto;
import com.developerscorner.provider.exception.NotFoundException;
import com.developerscorner.provider.model.User;
import com.developerscorner.provider.repository.UserRepository;

@Service
@Transactional
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public List<User> findAllUsers() {
		return userRepository.findAll();
	}

	public User findById(Long id) {
		return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
	}

	public User findByEmail(String email) {
		return userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User not found"));
	}

	public void saveUser(UserRegistrationDto user) {
		User newUser = User.builder()
				.firstName(user.getFirstName())
				.lastName(user.getLastName())
				.nickName(user.getNickName())
				.email(user.getEmail())
				.password(user.getPassword())
				.type(user.getType())
				.createdAt(LocalDate.now()).build();
		
		userRepository.save(newUser);
	}

	public void updateUser(Long id, UserRegistrationDto dto) {
		User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
		user.setFirstName(dto.getFirstName());
		user.setLastName(dto.getLastName());
		user.setNickName(dto.getNickName());
		user.setEmail(dto.getEmail());
		user.setPassword(dto.getPassword());
		user.setType(dto.getType());

		userRepository.save(user);
	}

	public void deleteById(Long id) {
		userRepository.deleteById(id);
	}

}
