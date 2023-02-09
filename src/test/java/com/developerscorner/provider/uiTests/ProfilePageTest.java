package com.developerscorner.provider.uiTests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.time.Duration;

import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.developerscorner.provider.uiTests.configuration.SeleniumConfig;
import com.developerscorner.provider.uiTests.forms.ProfileForm;

public class ProfilePageTest extends SeleniumConfig {

	private static final String baseUrl = "http://localhost:4200/profile";

	public ProfilePageTest() {
	}

	@Test
	void shouldGetProfilePage() {
		driver.get(baseUrl);
		ProfileForm form = PageFactory.initElements(driver, ProfileForm.class);
		assertTrue(form.title.getText().contains("Profile"));
	}

	@Test(priority = -1)
	void shouldCheckCurrentUserExists() {
		driver.get(baseUrl);
		ProfileForm form = PageFactory.initElements(driver, ProfileForm.class);

		new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOf(form.fName));
		assertEquals("testuser", form.fName.getText());
	}

	@Test
	void shouldEditProfile() {
		driver.get(baseUrl);
		ProfileForm form = PageFactory.initElements(driver, ProfileForm.class);

		form.openModalBtn.click();

		assertEquals("Profile update", form.modalTitle.getText());
		form.clear();
		form.fillForm("updated first", "updated last", "updated nick name", "testuser@email.com", "123456");
		form.submit();
		form.closeModalBtn.click();
		assertEquals(form.fName.getText(), "updated first");
	}

	/**
	 * Negative tests
	 */
	@Test
	void shouldDisplayFieldRequiredIfOnceOfTheFieldsIsBlank() {
		driver.get(baseUrl);

		ProfileForm form = PageFactory.initElements(driver, ProfileForm.class);

		form.openModalBtn.click();

		form.clear();
		form.fillForm(" ", " ", "nick name", "new.new@email.com", "");

		assertFalse(form.submitBtn.isEnabled(), "submit button should be disabled at this point");
	}
}
