package com.developerscorner.provider.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.developerscorner.provider.dto.AuthResponse;
import com.developerscorner.provider.dto.UserLoginDto;
import com.developerscorner.provider.dto.UserRegistrationDto;
import com.developerscorner.provider.model.User;
import com.developerscorner.provider.service.AuthService;
import com.developerscorner.provider.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	private final AuthService authService;
	private final UserService userService;
	private static Logger logger = LoggerFactory.getLogger(UserController.class);

	public UserController(AuthService authService, UserService userService) {
		this.authService = authService;
		this.userService = userService;
	}

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}

	// ----------------- Register user ----------------------------
	@PostMapping("/register")
	public ResponseEntity<Void> register(@RequestBody @Valid UserRegistrationDto form) {
		logger.info("Registering user {}", form);
		userService.save(form);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	// ------------------- Login user --------------------------
	@PostMapping("/login")
	@ResponseBody
	public ModelAndView login(@Valid @ModelAttribute("loginForm") UserLoginDto form, BindingResult bindingResult) {
		ModelAndView mv = new ModelAndView();
		if (bindingResult.hasErrors()) {
			bindingResult
					.getFieldErrors()
					.stream()
					.forEach(f -> mv.addObject("errors", f.getField() + ": " + f.getDefaultMessage()));
			mv.setViewName("loginForm");
			return mv;
		} else {
			logger.info("Loggin in user {}", form);
			AuthResponse authResponse = authService.authenticate(form);
			// UserLoginDto newUser = new UserLoginDto(form.getEmail(), form.getPassword());

			mv.setViewName("loginForm");
			mv.addObject("user", authResponse);

			return mv;
		}

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
