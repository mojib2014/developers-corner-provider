package com.developerscorner.provider.service;

import java.util.List;

import com.developerscorner.provider.dto.UserRegistrationDto;
import com.developerscorner.provider.model.User;

public interface UserService {
	List<User> findAll();
	User findById(Long id);
	User findByEmail(String email);
	User save(UserRegistrationDto dto);
	void update(Long id, UserRegistrationDto dto);
	void delete(Long id);
}
