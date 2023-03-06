package com.developerscorner.provider.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.developerscorner.provider.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);
}
