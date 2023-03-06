package com.developerscorner.provider;

import static org.testng.Assert.assertNotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.developerscorner.provider.controller.AuthController;

@SpringBootTest
class ProviderApplicationTests extends AbstractTestNGSpringContextTests {

	@Autowired
	private AuthController authController;
	
	@Test
	void contextLoads() {
		assertNotNull(authController);
	}

}
