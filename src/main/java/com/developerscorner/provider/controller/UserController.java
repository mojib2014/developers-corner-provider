package com.developerscorner.provider.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.developerscorner.provider.dto.UserRegistrationDto;
import com.developerscorner.provider.model.User;
import com.developerscorner.provider.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	// -------------------- Retreive Logged In user ----------------------------
	@GetMapping("/loggedin-user")
	public ResponseEntity<User> getLoggedInUser(
			@CurrentSecurityContext(expression = "authentication") Authentication authentication) {
		User user = userService.getLoggedinUser(authentication);

		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(user);
	}

	// -------------------- Retrieve All Users ---------------------------------
	@GetMapping
	public ResponseEntity<List<User>> getAllUsers(Exception e) {
		List<User> users = userService.findAll();

		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}

	// ------------------------- Retrieve A User By Id-----------------------------
	@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long id) {
		User user = userService.findById(id);

		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	// ------------------------- Retrieve A User By Email
	// -----------------------------
	@GetMapping("/email/{email}")
	public ResponseEntity<User> getUserByEmail(@PathVariable(value = "email") String email,
			HttpServletRequest request) {
		System.out.println("/users/email -----------------------==========" + request.getHeader("Authorization"));
		User user = userService.findByEmail(email);

		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	// ----------------------- Update a user by id -------------------
	@PutMapping("/{id}")
	public ResponseEntity<Void> updateUserById(@PathVariable(value = "id") Long id,
			@RequestBody @Valid UserRegistrationDto dto) {
		System.out.println("updating -------------" + dto.toString());
		userService.update(id, dto);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUserById(@PathVariable(value = "id") Long id) {
		userService.delete(id);

		return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
	}

}
