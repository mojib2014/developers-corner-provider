package com.developerscorner.provider.uiTests.forms;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class LoginForm {

	WebDriver driver;

	public LoginForm(WebDriver driver) {
		this.driver = driver;
	}

	@FindBy(how = How.ID, using = "loginBtn")
	public WebElement loginBtn;
	@FindBy(how = How.ID, using = "title")
	public WebElement title;
	@FindBy(how = How.ID, using = "email-required")
	public WebElement loginRequired;
	@FindBy(how = How.ID, using = "email")
	public WebElement email;
	@FindBy(how = How.ID, using = "password")
	public WebElement password;

	public void fillForm(String testEmail, String pass) {
		email.sendKeys(testEmail);
		password.sendKeys(pass);
	}

	public void clear() {
		email.clear();
		password.clear();
	}

	public void submit() {
		loginBtn.click();
	}
}
