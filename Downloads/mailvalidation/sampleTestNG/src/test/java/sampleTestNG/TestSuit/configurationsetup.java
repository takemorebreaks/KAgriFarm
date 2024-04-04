package sampleTestNG.TestSuit;

import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.util.List;

import javax.security.auth.login.CredentialException;

import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestListener;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

public class configurationsetup implements ITestListener{
	public static WebDriver driver;
	String UserNameOfOrangeHRM="//input[@name='username']";
	String PasswordOfOrangeHRM="//div[@class='oxd-form-row']//following::input[@name='password']";
	String LoginButtonOrangeHRM="//button[@type='submit']";
	String sideNavigationBarRightSide="//*[@class='oxd-icon bi-chevron-right']";
	String sideNavigationBarLeftSide="//*[@class='oxd-icon bi-chevron-left']";

	@BeforeClass
	public WebDriver getDriver() throws Exception{
		WebDriverManager.chromedriver().setup();
		// Set any additional options if needed
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		return driver;
	}
	public void loadUrl() throws Exception {
		System.out.println("Message from config class......");
		// Navigate to the URL
		driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/dashboard/index");
		System.out.println("Title of the page is " + driver.getTitle());
		Thread.sleep(5000);
		// Call AddConfiguration method after loading URL
		try {
			AddConfiguration("Admin", "admin123"); 
		} catch (CredentialException e) {
			e.printStackTrace();
		}
	}
	public void AddConfiguration(String username,String password)  throws Exception {
		System.out.println("Message from config class......");
		WebElement usernameField = driver.findElement(By.xpath(UserNameOfOrangeHRM));
		WebElement passwordField = driver.findElement(By.xpath(PasswordOfOrangeHRM));
		usernameField.sendKeys(username);
		passwordField.sendKeys(password);
		driver.findElement(By.xpath(LoginButtonOrangeHRM)).click();
		Thread.sleep(5000);
		SoftAssertions softAssertions = new SoftAssertions();
		softAssertions.assertThat("OrangeHRM").isEqualTo(driver.getTitle(),"Not matching");

		// Find the button element using findElements (no exception thrown if not found)
		List<WebElement> buttonElementsLeft= driver.findElements(By.xpath(sideNavigationBarLeftSide));
		// Check if any button elements were found
		if (!buttonElementsLeft.isEmpty()) {
			// Get the first button element from the list
			WebElement buttonElement = buttonElementsLeft.get(0);

			// Check if the button is visible
			if (buttonElement.isDisplayed()) {
				// Button is visible, click it
				System.out.println("Button is visible,no need to click...");
			} else {
				// Button is not visible, handle the case appropriately
				System.out.println("Button is not visible...");
				// Perform alternative action or log a message
			}
		} else {
			// Button element is not found, handle the case appropriately
			System.out.println("Button element not found on the page.");
			List<WebElement> buttonElementRight = driver.findElements(By.xpath(sideNavigationBarRightSide));
			WebElement buttonElement = buttonElementRight.get(0);
			buttonElement.click();
			// Perform alternative action or log a message
		}
		softAssertions.assertAll(); // All soft assertions are evaluated here

	}
	public void ExitBrowser() {
		driver.close();
	}


}
