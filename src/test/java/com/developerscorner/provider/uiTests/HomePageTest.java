package com.developerscorner.provider.uiTests;

import static org.junit.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import com.developerscorner.provider.uiTests.configuration.SeleniumConfig;
import com.developerscorner.provider.uiTests.forms.HomePageForm;

public class HomePageTest extends SeleniumConfig {

	private static final String baseUrl = "http://localhost:4200/";

	public HomePageTest() {
	}

	/**
	 * Positive tests
	 */
	@Test
	void shoulGetHomePage() {
		driver.get(baseUrl);
		HomePageForm form = PageFactory.initElements(driver, HomePageForm.class);

		assertEquals("Welcome to Developers Corner", form.title.getText());
	}

	@Test(priority = -1)
	void shouldCreateQuestion() {
		driver.get(baseUrl);

		HomePageForm form = PageFactory.initElements(driver, HomePageForm.class);

		form.fillForm("testuser", "Java", "Java data types");

		assertTrue(form.questionBtn.isEnabled(), "Button should be eanbled at this point");
		form.submit();

		form.newQuestionBtn.click();

		assertTrue(form.questionFormTitle.isDisplayed());
	}

	/**
	 * Negative tests
	 */
	@Test
	void shouldDisplayFieldIsRequiredIfUsernameNotFilled() {
		driver.get(baseUrl);
		HomePageForm form = PageFactory.initElements(driver, HomePageForm.class);

		form.clear();
		form.fillForm("", "Spring MVC", "Selenium automated test in spring");

		assertEquals("Username is required.", form.usernameRequired.getText());
		assertFalse(form.questionBtn.isEnabled());
	}
}
