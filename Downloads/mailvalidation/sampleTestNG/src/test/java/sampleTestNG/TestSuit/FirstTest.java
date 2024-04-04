package sampleTestNG.TestSuit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import GlobalexceptionHandler.CredantialError;
import GlobalexceptionHandler.NoSuchElementException;
import GlobalexceptionHandler.UserAlreadyExist;

import org.assertj.core.api.SoftAssertions;
import static org.assertj.core.api.Assertions.assertThat; // f
import static org.testng.Assert.assertEquals;

import java.awt.Button;
import java.lang.Thread;
import java.util.List;
import java.util.ArrayList;

import io.github.bonigarcia.wdm.WebDriverManager;

public class FirstTest {
	public WebDriver driver;
	configurationsetup configurationsetup =new configurationsetup();
	String SideNaviationAllOptions="//*[@class='oxd-main-menu']";
	@BeforeClass 
	public void LoadApplication() throws Exception  {
		configurationsetup.loadUrl();
		driver = configurationsetup.driver;
	}
	@AfterClass
	public void AfterTestCompletion() throws Exception {
		configurationsetup.ExitBrowser();
	}

	@Test
	public void LeaveApplication() {
		WebElement ulElement = driver.findElement(By.xpath(SideNaviationAllOptions));
		// Find all <li> elements within the <ul>
		List<WebElement> liElements = ulElement.findElements(By.tagName("li"));
		// Create a list to store href attributes
		List<String> hrefList = new ArrayList<String>();
		// Iterate through each <li> element
		for (WebElement liElement : liElements) {
			// Find the <a> element within the <li>
			WebElement aElement = liElement.findElement(By.tagName("a"));

			// Get the value of the href attribute
			String href = aElement.getAttribute("href");

			// Print href value
			System.out.println(href);

			// Check if href ends with 'viewLeaveModule'
			if (href.endsWith("viewLeaveModule")) {
				// Click on the link if it ends with 'viewLeaveModule'
				aElement.click();
				break; // Break out of the loop since we found the target link
			}
		}
	}



}



