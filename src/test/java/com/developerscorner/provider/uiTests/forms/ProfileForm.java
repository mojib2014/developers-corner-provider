package com.developerscorner.provider.uiTests.forms;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class ProfileForm {

	WebDriver driver;

	public ProfileForm(WebDriver driver) {
		this.driver = driver;
	}

	@FindBy(how = How.ID, using = "title")
	public WebElement title;
	@FindBy(how = How.XPATH, using = "//*[@id=\"fName\"]")
	public WebElement fName;
	@FindBy(how = How.XPATH, using = "//*[@id=\"openModalBtn\"]")
	public WebElement openModalBtn;
	@FindBy(how = How.ID, using = "closeModalBtn")
	public WebElement closeModalBtn;
	@FindBy(how = How.CLASS_NAME, using = "modal-title")
	public WebElement modalTitle;
	@FindBy(how = How.ID, using = "firstName")
	public WebElement firstName;
	@FindBy(how = How.ID, using = "lastName")
	public WebElement lastName;
	@FindBy(how = How.ID, using = "nickName")
	public WebElement nickName;
	@FindBy(how = How.ID, using = "formEmail")
	public WebElement formEmail;
	@FindBy(how = How.ID, using = "password")
	@CacheLookup
	public WebElement password;
	@FindBy(how = How.ID, using = "submitBtn")
	public WebElement submitBtn;

	public void fillForm(String fName, String lName, String nName, String email, String pass) {
		firstName.sendKeys(fName);
		lastName.sendKeys(lName);
		nickName.sendKeys(nName);
		formEmail.sendKeys(email);
		password.sendKeys(pass);
	}

	public void submit() {
		submitBtn.click();
	}

	public void clear() {
		firstName.clear();
		lastName.clear();
		nickName.clear();
		formEmail.clear();
		password.clear();
	}
}
