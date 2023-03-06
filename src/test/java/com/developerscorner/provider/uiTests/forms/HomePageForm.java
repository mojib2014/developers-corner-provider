package com.developerscorner.provider.uiTests.forms;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class HomePageForm {

	WebDriver driver;

	public HomePageForm(WebDriver driver) {
		this.driver = driver;
	}

	@FindBy(how = How.ID, using = "questionBtn")
	public WebElement questionBtn;
	@FindBy(how = How.ID, using = "title")
	public WebElement title;
	@FindBy(how = How.ID, using = "username")
	public WebElement username;
	@FindBy(how = How.ID, using = "student")
	public WebElement studentRole;
	@FindBy(how = How.ID, using = "tags")
	public WebElement tags;
	@FindBy(how = How.ID, using = "question")
	public WebElement question;
	@FindBy(how = How.ID, using = "new-question-btn")
	public WebElement newQuestionBtn;
	@FindBy(how = How.ID, using = "question-form-title")
	public WebElement questionFormTitle;
	@FindBy(how = How.XPATH, using = "/html/body/app-root/header/div/nav/ul/li[2]/a")
	public WebElement logoutBtn;
	@FindBy(how = How.ID, using = "username-required")
	public WebElement usernameRequired;

	public void fillForm(String testusername, String testTags, String testQuestion) {
		username.sendKeys(testusername);
		studentRole.click();
		tags.sendKeys(testTags);
		question.sendKeys(testQuestion);
	}

	public void submit() {
		questionBtn.click();
	}

	public void clear() {
		username.clear();
		tags.clear();
		question.clear();
	}
}
