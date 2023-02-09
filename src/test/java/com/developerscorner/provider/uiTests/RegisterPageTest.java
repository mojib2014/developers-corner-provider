package com.developerscorner.provider.uiTests;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import com.developerscorner.provider.uiTests.configuration.SeleniumConfig;
import com.developerscorner.provider.uiTests.forms.HomePageForm;
import com.developerscorner.provider.uiTests.forms.RegisterForm;

public class RegisterPageTest extends SeleniumConfig {

	private static final String baseUrl = "http://localhost:4200/register";
	private static final String homePageUrl = "http://localhost:4200/";

	public RegisterPageTest() {
	}

	@Test
	public void shouldRegisterAUser() {
		driver.get(baseUrl);
		RegisterForm form = PageFactory.initElements(driver, RegisterForm.class);

		form.fillForm("new user", "user new", "nick n", "Mentor", "new.user@email.com", "123456");
		assertTrue(form.registerBtn.isEnabled());
		form.submit();
	}

	/**
	 * Negative tests
	 */

	@Test
	void shouldDisplayFieldIsRequiredIfOneOfTheFieldIsBlank() {
		driver.get(homePageUrl);

		HomePageForm homeForm = PageFactory.initElements(driver, HomePageForm.class);
		homeForm.logoutBtn.click();

		driver.get(baseUrl);

		RegisterForm form = PageFactory.initElements(driver, RegisterForm.class);

		form.fillForm("", "user new", "nick n", "Mentor", "new.user@email.com", "123456");
		assertFalse(form.registerBtn.isEnabled());
	}
}
