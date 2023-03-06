package com.developerscorner.provider.uiTests.forms;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class RegisterForm {

	WebDriver driver;

	public RegisterForm(WebDriver driver) {
		this.driver = driver;
	}

	@FindBy(how = How.ID, using = "registerBtn")
	public WebElement registerBtn;
	@FindBy(how = How.ID, using = "firstName")
	public WebElement firstName;
	@FindBy(how = How.ID, using = "lastName")
	public WebElement lastName;
	@FindBy(how = How.ID, using = "nickName")
	public WebElement nickName;
	@FindBy(how = How.ID, using = "type")
	public WebElement type;
	@FindBy(how = How.ID, using = "email")
	public WebElement email;
	@FindBy(how = How.ID, using = "password")
	public WebElement password;

	public void fillForm(String fName, String lName, String nName, String testType, String testEmail, String pass) {
		firstName.sendKeys(fName);
		lastName.sendKeys(lName);
		nickName.sendKeys(nName);
		type.sendKeys(testType);
		email.sendKeys(testEmail);
		password.sendKeys(pass);
	}

	public void clear() {
		firstName.clear();
		lastName.clear();
		nickName.clear();
		type.clear();
		email.clear();
		password.clear();
	}

	public void submit() {
		registerBtn.click();
	}
}
