package ReusableMethods;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import GlobalexceptionHandler.NoSuchElementException;
import sampleTestNG.TestSuit.configurationsetup;

public class SelectNavigationBarOption {
    public WebDriver driver;
    configurationsetup configurationsetup = new configurationsetup();
    String SideNavigationAllOptions = "//*[@class='oxd-main-menu']";
    String TopNavigationAllOptions="//nav[@role='navigation' and @aria-label='Topbar Menu']";
    public SelectNavigationBarOption(){
    	try {
			this.driver=configurationsetup.getDriver();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public void SelectSideNavOption(String Option) {
        // Wait until the SideNavigationAllOptions element is present
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement IwebSideNav = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(SideNavigationAllOptions)));
        List<WebElement> liElements = IwebSideNav.findElements(By.tagName("li"));
        boolean optionFound = false;
        for (WebElement liElement : liElements) {
            WebElement aElement = liElement.findElement(By.tagName("a"));
            String href = aElement.getAttribute("href");
            System.out.println(href);
            if (href.endsWith(Option)) {
                aElement.click();
                optionFound = true;
                break;
            }
        }

        // Check if the option was found
        if (!optionFound) {
            throw new NoSuchElementException("Option ending with '" + Option + "' not found in the side navigation menu.");
        }
    }

public void SelectTopNavOption(String Option) {
	System.out.println("Inside SelecttOP");
	WebDriverWait wait =new WebDriverWait(driver, Duration.ofSeconds(20));
	WebElement IwebTopNavBar=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(TopNavigationAllOptions)));
	  List<WebElement> liElements = IwebTopNavBar.findElements(By.tagName("li"));
      boolean optionFound = false;
      for (WebElement liElement : liElements) {
          String NavBarOption = liElement.getText();
          if (NavBarOption.endsWith(Option)) {
        	  liElement.click();
              optionFound = true;
              break;
          }
      }

      // Check if the option was found
      if (!optionFound) {
          throw new NoSuchElementException("Option ending with '" + Option + "' not found in the side navigation menu.");
      }
  }

}