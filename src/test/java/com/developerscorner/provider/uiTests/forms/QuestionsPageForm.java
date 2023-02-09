package com.developerscorner.provider.uiTests.forms;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class QuestionsPageForm {

	WebDriver driver;

	public QuestionsPageForm(WebDriver driver) {
		this.driver = driver;
	}

	@FindBy(how = How.ID, using = "title")
	public WebElement title;
	@FindBy(how = How.ID, using = "list")
	public List<WebElement> list;
	@FindBy(how = How.XPATH, using = "//*[@id=\"openModal\"]")
	public WebElement openModal;
	@FindBy(how = How.CLASS_NAME, using = "modal-title")
	public WebElement modalTitle;
	@FindBy(how = How.ID, using = "username")
	public WebElement username;
	@FindBy(how = How.ID, using = "role")
	public WebElement mentorRole;
	@FindBy(how = How.ID, using = "tags")
	public WebElement tags;
	@FindBy(how = How.ID, using = "question")
	public WebElement question;
	@FindBy(how = How.ID, using = "submitBtn")
	public WebElement submitBtn;
	@FindBy(how = How.XPATH, using = "//*[@id=\"removeBtn\"]")
	public WebElement removeBtn;

	public void fillForm(String testUsername, String role, String testTags, String testQuestion) {
		username.sendKeys(testUsername);
		mentorRole.sendKeys(role);
		tags.sendKeys(testTags);
		question.sendKeys(testQuestion);
	}

	public void submit() {
		submitBtn.click();
	}

	public void clear() {
		username.clear();
		tags.clear();
		mentorRole.clear();
		question.clear();
	}
}
