package com.developerscorner.provider.uiTests.configuration;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import com.developerscorner.provider.uiTests.forms.LoginForm;

public class SeleniumConfig {

	public static WebDriver driver;
	BrowserFactory obj1;

	public SeleniumConfig() {

	}

	@BeforeSuite
	public void beforeSuite() {

	}

	@BeforeClass
	public void beforeMethodClass() {
		System.out.println("in @beforeMethodClass");
		SeleniumConfig.driver = BrowserFactory.getDriver("chrome");

		SeleniumConfig.driver.get("http://localhost:4200/login"); // ('#!/login') cause of angularJs routing with spring
																	// mvc.

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript(String.format("window.localStorage.removeItem('token')"));

		LoginForm form = PageFactory.initElements(driver, LoginForm.class);

		form.clear();
		form.fillForm("testuser@email.com", "123456");
		form.submit();
		new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe("http://localhost:4200/"));
	}

	@AfterMethod
	public void close() {

	}

	@AfterSuite
	public void afterSuite() throws IOException {
		driver.close();
		driver.quit();
	}
}
