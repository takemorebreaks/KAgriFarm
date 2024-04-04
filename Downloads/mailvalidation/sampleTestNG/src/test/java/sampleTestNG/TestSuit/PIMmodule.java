package sampleTestNG.TestSuit;

import java.util.List;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import GlobalexceptionHandler.NoSuchElementException;
import GlobalexceptionHandler.TimeOutExceptionoccured;
import ModelclassForDataStoring.EmployeeList;
import ReusableMethods.DropDownListOptionSelection;
import ReusableMethods.SelectNavigationBarOption;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.support.ui.Select;
import java.time.Duration;
import java.util.ArrayList;
public class PIMmodule {
	public WebDriver driver;
	configurationsetup configurationsetup =new configurationsetup();
	DropDownListOptionSelection dropDownSelection = 	new DropDownListOptionSelection();
	String PimNavBar="//nav[@role='navigation' and @aria-label='Topbar Menu']";
	String SideNaviationAllOptions="//*[@class='oxd-main-menu']";
	String FirstNameTextBox="//input[@name=\"firstName\"]";
	String LastNameTextBox="//input[@name=\"lastName\"]";
	String EmployeeIdTextBox="//div[@class='oxd-input-group__label-wrapper']//following::input[@class=\"oxd-input oxd-input--active\"]";
	String UserNameTextBox="//label[text()='Username']//following::input[1]";
	String PasswordTextBox="//label[text()='Username']//following::input[4]";
	String ConfirmPasswordTextBox="//label[text()='Username']//following::input[5]";
	String CheckBox="//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/form/div[1]/div[2]/div[2]/div/label/span";
	String SaveButton="//button[@type='submit']";
	String EmployementStatusDropDownTextBox="//label[text()='Employment Status']/following::div[4][@class='oxd-select-text-input']";
	String EmployeeName="//label[text()='Employee Name']//following::div[3]//input";
	String Employee_Id="//label[text()='Employee Id']//following::div[1]/input";
	String SupervisorName="//label[text()='Supervisor Name']//following::div[1]//child::div//input";
	String JobTitle="//label[text()='Job Title']//following::div[@class='oxd-select-text--after'][1]";
	String EmpJobRoleSubRole="//label[text()='Sub Unit']//following::div[4]";
	String JobTitledropdownList="//label[text()='Job Title']//following::div[4]";
	String SearchButton="//button[@type='submit' and text()=' Search ']";
	String ResetButton="//button[@type='reset' and text()=' Reset ']";
	String SelectOptionforEmpStatus="//span[text()='Freelance']";
	String SelectOptionforempRole="//span[text()='Quality Assurance']";
	String TableInEmployeeSearchResult="//div[@class='orangehrm-container']//child::div[@role='table']";
	String TableEmployeeListHeadLine="//div[@role='table']//following::div[1][@role='row']";
	String TableEmployeeListData="//div[@class='oxd-table-body' and @role='rowgroup']";
	//Variable and values...
	String FirstName="Shiva";
	String LastName="Prasad";
	String UserName="ShivaPrasad";
	String Password="ShivaPrasad@123";
	String EmployeeId="0330";
	String SuperViceName="Odis Adalwin";
	String expectedResultForEmpStatus = "Part-Time Internship";
    String expectedResultForJobTitle = "Quality Assurance";
    String expectedResultforSubUnit= "Automaton Tester";
	SelectNavigationBarOption navigationBarOption= new SelectNavigationBarOption();
	WebDriverWait driverWait = new WebDriverWait(driver,Duration.ofSeconds(10));
	WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(15));
	@BeforeClass 
	public void LoadApplication() throws Exception  {
		configurationsetup.loadUrl();
		driver = configurationsetup.driver;
		navigationBarOption.SelectSideNavOption("viewPimModule");
				Thread.sleep(5000);
	}
//	@AfterClass
//	public void AfterTestCompletion() throws Exception {
//		configurationsetup.ExitBrowser();
//	}

	@Test(enabled = false)
	public void AddNewEmployee() throws InterruptedException {
		
				List<WebElement>UserNameielement=new ArrayList<WebElement>();
				UserNameielement= driver.findElements(By.xpath(UserNameTextBox));
				if(!UserNameielement.isEmpty()) {
					WebElement UserNameIElement = UserNameielement.get(0);
					if(UserNameIElement.isDisplayed()) {
						Fillalluserdetails();
					}
				}
				else {
					try {
						//driver.findElement(By.xpath(CheckBox)).click();
					
						WebElement CheckBoxElement=driverWait.until(ExpectedConditions.elementToBeClickable(By.xpath(CheckBox)));
						CheckBoxElement.click();
						Thread.sleep(2000);
						Fillalluserdetails();
						Thread.sleep(8000);
						}
					catch(ElementNotInteractableException e){
						throw new ElementNotInteractableException("Element not interacting..."+e.getMessage());
					}
		}
	}
	
   
	private void Fillalluserdetails() throws InterruptedException, NoSuchElementException {
	    try {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	        
	        WebElement firstNameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(FirstNameTextBox)));
	        firstNameElement.sendKeys(FirstName);
	        
	        WebElement lastNameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LastNameTextBox)));
	        lastNameElement.sendKeys(LastName);
	        
	        WebElement employeeIdElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(EmployeeIdTextBox)));
	        employeeIdElement.click();
	        employeeIdElement.clear();
	        employeeIdElement.sendKeys(EmployeeId);
	        
	        WebElement userNameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(UserNameTextBox)));
	        userNameElement.sendKeys(UserName);
	        
	        WebElement passwordElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PasswordTextBox)));
	        passwordElement.sendKeys(Password);
	        
	        WebElement confirmPasswordElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ConfirmPasswordTextBox)));
	        confirmPasswordElement.sendKeys(Password);
	        
	        WebElement saveButtonElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(SaveButton)));
	        saveButtonElement.click();
	    } catch (TimeOutExceptionoccured e) {
	        throw new NoSuchElementException("Timeout waiting for element: " + e.getMessage());
	    }
	}
	 @Test(enabled = false)
	private void EmployeeInfoSearch() throws InterruptedException,NoSuchElementException {
		try {
			navigationBarOption.SelectTopNavOption("Employee List");
			
			WebElement EmpInfoPageEmployeeNameText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(EmployeeName))); 
			EmpInfoPageEmployeeNameText.sendKeys(FirstName);
			Thread.sleep(5000);
			
			WebElement EmpInfoPageEmployee_IdText=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Employee_Id)));
			EmpInfoPageEmployee_IdText.sendKeys(EmployeeId);
			Thread.sleep(5000);
			
			WebElement EmpInfoPageSupervisorNameText=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(SupervisorName)));
			EmpInfoPageSupervisorNameText.sendKeys(SuperViceName);
			Thread.sleep(5000);
			
			WebElement EmpInfoEmployementStatusDropDown=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(EmployementStatusDropDownTextBox)));
			EmpInfoEmployementStatusDropDown.click();
			Thread.sleep(5000);
	      
	        
	     // Iterate through each option
	        for (int i = 0; i < 10; i++) {
	            // Move down to the desired option
	            EmpInfoEmployementStatusDropDown.sendKeys(Keys.ARROW_DOWN);
	            Thread.sleep(1000); // Add a small delay to allow the dropdown to update
	            // Get the currently selected option
	            String selectedOption = EmpInfoEmployementStatusDropDown.getText();
	            System.out.println(selectedOption);

	            // Check if the selected option matches the expected result
	            if (selectedOption.equals(expectedResultForEmpStatus)) {
	                // If it matches, select the option
	                EmpInfoEmployementStatusDropDown.sendKeys(Keys.ENTER);
	                break; // Exit the loop
	            }
	        }
//	        WebElement EmpInfoEmpRole=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(EmpJobRole)));
//	        EmpInfoEmpRole.click();
//	        Thread.sleep(2000);
//	        WebElement EmpInfoSelectOptionforempRole=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(SelectOptionforempRole)));
//	        EmpInfoEmpRole.click();
//	        Thread.sleep(8000);
	        dropDownSelection.selectOptionFromDropdownClassSpan(driver,JobTitle,expectedResultforSubUnit);     
	        dropDownSelection.selectOptionFromDropdownClassSpan(driver,EmpJobRoleSubRole,expectedResultForJobTitle); 
	        WebElement SearchButtonEmployeeListOption=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(SearchButton)));
	        SearchButtonEmployeeListOption.click();
	        Thread.sleep(5000);
		}
		catch(NoSuchElementException e) {
			throw new NoSuchElementException("Element not found..."+e.getMessage());
		}

	}
	    @Test()
		private void EmployeeListTableDataStoring() throws InterruptedException,NoSuchElementException {
		 try {
			 navigationBarOption.SelectTopNavOption("Employee List");
			 Thread.sleep(2000);
			  // Locate the row containing the column headers
		        WebElement columnHeaderRow = driver.findElement(By.xpath(TableEmployeeListHeadLine));
		        Thread.sleep(5000);
		        WebElement colunmDataRow = driver.findElement(By.xpath(TableEmployeeListData));
		     // Find all child elements representing the column headers using XPath
		     List<WebElement> columnHeaderElements = columnHeaderRow.findElements(By.xpath(".//div[@role='columnheader']"));
		     List<WebElement> columnDataElements = colunmDataRow.findElements(By.xpath("//div[@class='oxd-table-card']"));
		    
		        // Create a list to store the column headers
		        List<String> columnHeaders = new ArrayList<String>();
		        List<String> columnRowData = new ArrayList<String>();

		        // Iterate through each column header element and extract the text
		        for (WebElement columnHeaderElement : columnHeaderElements) {
		            String columnHeaderText = columnHeaderElement.getText();
		            columnHeaders.add(columnHeaderText);
		            System.out.println(columnHeaderText);
		        }

		        List<Object> TrailList = new ArrayList<Object>();
		        System.out.println("size of columnDataElements :" + columnDataElements.size());
		        for (WebElement Trail : columnDataElements) {
		        	EmployeeList employeeList = new EmployeeList();
		        	  List<WebElement> children = Trail.findElements(By.xpath("./div/child::div")); // Use ./ to select child elements
		        	  for (int i = 0; i < children.size(); i++) {
		        	        WebElement child = children.get(i);
		        	        String text = child.getText().trim();
		        	       // System.out.println("column text value :"+text);
		        	        switch(i) {
		        	            case 0:
	        	            	    employeeList.setCheckBox((Boolean) (text.isEmpty()?false:text));
	        	                    break;
		        	            case 1:
		        	            	employeeList.setId(text.isEmpty() ? "empty field" : text);
		        	                break;
		        	            case 2:
		        	            	employeeList.setFirstName(text.isEmpty() ? "empty field" : text);
		        	                break;
		        	            case 3:
		        	            	employeeList.setLastName(text.isEmpty() ? "empty field" : text);
		        	                break;
		        	            case 4:
		        	            	employeeList.setJonTitle(text.isEmpty() ? "empty field" : text);
		        	                break;
		        	            case 5:
		        	            	employeeList.setEmploymentStatus(text.isEmpty() ? "empty field" : text);
		        	                break;
		        	            case 6:
		        	            	employeeList.setSubUnit(text.isEmpty() ? "empty field" : text);
		        	                break;
		        	            case 7:
		        	            	employeeList.setSuperVisor(text.isEmpty() ? "empty field" : text);
		        	                break;
		        	            case 8:
		        	            	employeeList.setAction(text.isEmpty() ? "empty field" : text);
		        	                break;
		        	            case 9:
		        	            	employeeList.setAction(text.isEmpty() ? "empty field" : text);
		        	                break;
		        	            default:
		        	                // Handle any additional columns here
		        	                break;
		        	        }
		        	    }
		        	  TrailList.add(employeeList);
		        	    }
		        
		        for (Object obj : TrailList) {
		            EmployeeList employeeList = (EmployeeList) obj;
		            System.out.println("Id : " + employeeList.getId());
		            System.out.println("First Name : " + employeeList.getFirstName());
		            System.out.println("Last Name : " + employeeList.getLastName());
		            System.out.println("Job Title : " + employeeList.getJonTitle());
		            System.out.println("Employment Status : " + employeeList.getEmploymentStatus());
		            System.out.println("Sub Unit : " + employeeList.getSubUnit());
		            System.out.println("Supervisor : " + employeeList.getSuperVisor());
		            System.out.println("Action : " + employeeList.getAction());
		            // Print additional attributes if needed
		        }
		        
		 }
		 catch(NoSuchElementException e) {
				throw new NoSuchElementException("Element not found..."+e.getMessage());
			}

	 }
}
