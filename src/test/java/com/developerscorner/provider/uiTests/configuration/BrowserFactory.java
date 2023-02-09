package com.developerscorner.provider.uiTests.configuration;

import java.time.Duration;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BrowserFactory {

	public static WebDriver driver;

	public BrowserFactory() {

	}

	public static WebDriver getDriver() throws InterruptedException {// avoids interruption during execution
		if (driver == null) {
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--disable notifications");// stop potential notifications during executions
			options.setPageLoadStrategy(PageLoadStrategy.NONE);// so page does not load while execution
			driver = new ChromeDriver(options);
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
		}
		return driver;
	}

	public static WebDriver getDriver(String browserName) {
		if (driver == null) {
			if (browserName.equalsIgnoreCase("firefox")) {
				driver = new FirefoxDriver();
				driver.manage().window().maximize();
				driver.manage().deleteAllCookies();
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(45));
				driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(45));
			} else if (browserName.equalsIgnoreCase("chrome")) {
				System.out.println("in chrome");
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver();
				driver.manage().window().maximize();
				driver.manage().deleteAllCookies();
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(45));
				driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(45));
			} else if (browserName.equalsIgnoreCase("IE")) {
				driver = new InternetExplorerDriver();
				driver.manage().window().maximize();
				driver.manage().deleteAllCookies();
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
				driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
			}
		}
		return driver;
	}
}
