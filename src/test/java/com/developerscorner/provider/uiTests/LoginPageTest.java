// package com.developerscorner.provider.uiTests;

// import static org.testng.Assert.assertEquals;
// import static org.testng.Assert.assertFalse;
// import static org.testng.Assert.assertTrue;

// import java.time.Duration;

// import org.openqa.selenium.support.PageFactory;
// import org.openqa.selenium.support.ui.ExpectedConditions;
// import org.openqa.selenium.support.ui.WebDriverWait;
// import org.testng.annotations.Test;

// import com.developerscorner.provider.uiTests.configuration.SeleniumConfig;
// import com.developerscorner.provider.uiTests.forms.LoginForm;

// public class LoginPageTest extends SeleniumConfig {

// private String baseUrl = "http://localhost:4200/login";
// private String homePageUrl = "http://localhost:4200/";

// public LoginPageTest() {
// }

// /**
// * Positive tests
// */
// @Test
// public void shouldGetLoginPage() {
// driver.get(baseUrl);

// LoginForm form = PageFactory.initElements(driver, LoginForm.class);

// assertEquals(form.title.getText(), "Login Form");
// }

// @Test
// public void shouldLogInAUser() {
// try {
// driver.get(baseUrl);

// LoginForm form = PageFactory.initElements(driver, LoginForm.class);

// form.fillForm("testuser@email.com", "123456");
// assertTrue(form.loginBtn.isEnabled(), "Login button should be enabled at this
// point");

// form.submit();
// new WebDriverWait(driver,
// Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(homePageUrl));
// assertEquals(driver.getCurrentUrl(), homePageUrl, "Should be redirected to
// the home page at this point");
// } catch (Exception e) {
// e.printStackTrace();
// }
// }

// /**
// * Negative tests
// */
// @Test
// void shouldDisplayFieldRequiredIfEmailNotFilled() {
// driver.get(baseUrl);

// LoginForm form = PageFactory.initElements(driver, LoginForm.class);

// form.fillForm("", "123456");

// assertFalse(form.loginBtn.isEnabled(), "Login button should be disabled at
// this point");

// assertEquals(form.loginRequired.getText(), "This is a required field");
// }
// }
