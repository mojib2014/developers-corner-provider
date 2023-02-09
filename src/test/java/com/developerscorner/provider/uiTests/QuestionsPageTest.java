package com.developerscorner.provider.uiTests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import com.developerscorner.provider.uiTests.configuration.SeleniumConfig;
import com.developerscorner.provider.uiTests.forms.HomePageForm;
import com.developerscorner.provider.uiTests.forms.QuestionsPageForm;

public class QuestionsPageTest extends SeleniumConfig {

	private static final String baseUrl = "http://localhost:4200/questions";
	private static final String homePageUrl = "http://localhost:4200/";

	public QuestionsPageTest() {
	}

	/**
	 * Positive tests
	 */
	@Test
	void shouldGetQuestionsPage() {
		driver.navigate().to(baseUrl);

		QuestionsPageForm form = PageFactory.initElements(driver, QuestionsPageForm.class);

		assertEquals("Your Questions", form.title.getText());
		assertEquals(2, form.list.size());
	}

	@Test(priority = -1)
	void shouldCreateQuestion() {
		driver.get(homePageUrl);

		HomePageForm form = PageFactory.initElements(driver, HomePageForm.class);

		form.fillForm("testuser", "Java", "Generics example");

		assertTrue(form.questionBtn.isEnabled(), "Button should be eanbled at this point");
		form.submit();

		form.newQuestionBtn.click();

		assertTrue(form.questionFormTitle.isDisplayed());
	}

	@Test
	void shouldEditAQuestion() {
		driver.get(baseUrl);
		QuestionsPageForm form = PageFactory.initElements(driver, QuestionsPageForm.class);

		form.openModal.click();

		assertEquals("Question update", form.modalTitle.getText());

		form.clear();
		form.fillForm("testuser", "Student", "Java", "Spring mvc unit test with testNg");

		assertTrue(form.submitBtn.isEnabled(), "The submit button is enalbed at this point");
		form.submit();

	}

	@Test
	void shouldDeleteAQuestion() {
		driver.navigate().to(baseUrl);
		QuestionsPageForm form = PageFactory.initElements(driver, QuestionsPageForm.class);

		form.removeBtn.click();

		assertEquals(2, form.list.size());
	}

	/**
	 * Negative tests
	 */
	@Test
	void shouldDisplayFieldRequiredIfOneOfTheFieldsIsBlank() {
		driver.get(baseUrl);
		QuestionsPageForm form = PageFactory.initElements(driver, QuestionsPageForm.class);

		form.openModal.click();

		form.clear();
		form.fillForm("", "Student", "", "JavaScript scope");

		assertEquals("", form.username.getText());
		// assertFalse(form.submitBtn.isEnabled(), "The submit button should be disabled
		// at this point");
	}
}
