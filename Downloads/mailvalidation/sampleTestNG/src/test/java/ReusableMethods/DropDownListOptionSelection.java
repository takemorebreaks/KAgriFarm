package ReusableMethods;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;

import GlobalexceptionHandler.NoSuchElementException;
import sampleTestNG.TestSuit.configurationsetup;

public class DropDownListOptionSelection {
	String expectedResult2 = "Automaton Tester";

	public void selectOptionFromDropdownClassSpan(WebDriver driver,String dropdownXPath, String expectedResult) throws InterruptedException {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	    // Locate the dropdown element
	    WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(dropdownXPath)));
	    dropdown.click();
	    Thread.sleep(5000); // Wait for the dropdown options to load
	    List<WebElement> optionElements = driver.findElements(By.cssSelector(".oxd-select-option > span"));
	    Thread.sleep(5000);
	    System.out.println("List Size : "+optionElements.size());
	    boolean found = false; // Flag to track if a matching option is found
	    for (WebElement optionElement : optionElements) {
	        System.out.println(optionElements.size());
	        if (optionElement.getText().equals(expectedResult.trim())) { // Use equals() method for content comparison
	            System.out.println("matching...");
	            found = true; // Set the flag to true if a matching option is found
	            optionElement.click();
	            break;
	        }
	    }
	    if (!found) {
	        throw new NoSuchElementException("Option '" + expectedResult + "' not found in the dropdown list");
	    }
	}
	public void selectOptionFromDropdownOptionalValues(WebDriver driver,String dropdownXPath, String expectedResult) throws InterruptedException {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	    // Locate the dropdown element
	    WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(dropdownXPath)));
	    dropdown.click();
	    Thread.sleep(5000); // Wait for the dropdown options to load

	    // Get all the options in the dropdown
	    java.util.List<WebElement> optionElements = dropdown.findElements(By.tagName("option"));


	    Thread.sleep(5000);
	    System.out.println("List Size : "+optionElements.size());
	    boolean found = false; // Flag to track if a matching option is found
	    for (WebElement optionElement : optionElements) {
	        System.out.println(optionElements.size());
	        if (optionElement.getText().equals(expectedResult.trim())) { // Use equals() method for content comparison
	            System.out.println("matching...");
	            found = true; // Set the flag to true if a matching option is found
	            optionElement.click();
	            break;
	        }
	    }
	    if (!found) {
	        throw new NoSuchElementException("Option '" + expectedResult + "' not found in the dropdown list");
	    }
	}

}
