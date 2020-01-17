package spectool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ATSSpecToolTest {
	
	WebDriver driver = null;
	
	int page_load_timeout = 40; //in seconds
	int implicit_wait = 30;  //in seconds
	
	@BeforeClass(description ="Run Before Our test")
	public void beforeClass() {
		
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		  driver= new ChromeDriver();
		  driver.manage().window().maximize();
		  driver.get("https://spec.atsspecsolutions.com/");
		  
		  //Clear session cookies every time test case runs so that Login page is loaded first
		  driver.manage().deleteAllCookies();
		  
		  driver.manage().timeouts().pageLoadTimeout(page_load_timeout, TimeUnit.SECONDS);
		  driver.manage().timeouts().implicitlyWait(implicit_wait, TimeUnit.SECONDS);
	}
	
	/**
	 * The DataProvider provides user-name and password
	 * @return
	 */
	@DataProvider(name ="LoginCredentialProvider")
	public Object[][] getLoginData(){
		
		return new Object[][] {{"dovini5510@one-mail.top", "Ats123456" }};
	}
	
	/**
	 * This method does login to the URL using the credentials provided by {@link #getLoginData()}
	 * @param username
	 * @param password
	 * @throws InterruptedException
	 */
	@Test(priority = 1, dataProvider ="LoginCredentialProvider")
	public void testLoginPage(String username, String password) throws InterruptedException {
		
		//Wait until submit button has loaded
		WebDriverWait wait = new WebDriverWait(driver, page_load_timeout);
		WebElement submit_button = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@type='submit']")));

		//Enter username and password
		driver.findElement(By.xpath("//input[@name='username' and @type='text']")).sendKeys(username);
		driver.findElement(By.xpath("//input[@name='password' and @type='password']")).sendKeys(password);

		//Wait for 1.5 seconds for user to view the details entered before submitting 
		Thread.sleep(1500);
		
		//Click the submit button
		submit_button.click();
	}	

	/**
	 * This method clicks on "New Project" on "Start New Project" widget
	 * @throws InterruptedException
	 */
	@Test(priority =2, description ="Start creating a new Project")
	public void testStartNewProject() throws InterruptedException {
		
		//Wait until NEW PROJECT button has loaded
		WebDriverWait wait = new WebDriverWait(driver, page_load_timeout);
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/createNewProject']")));
		Thread.sleep(1000);
		
		List<WebElement> spanElements = element.findElements(By.tagName("span"));
		for(WebElement spElement : spanElements) {
			if("New Project".equalsIgnoreCase(spElement.getText())){
			    element.click();
				break;
			}
		}

		
	}
	
	/**
	 * This dataProvider provides Internal Number, Project name, County, Province, City, Address, Bid Date and Area per SqFt.
	 * @return
	 */
	@DataProvider(name="CreateNewProject")
	public Object[][] getCreateNewProjectData(){
		return new Object[][] {{"1234", "SpecTool", "Canada", "Ontario", "Toronto", "151 Dundas", "2020-01-05", "1200"}};
	}
	
	/**
	 * This method returns Internal Number, Project name, County, Province, City, Address, Bid Date and Area per SqFt. details 
	 * @param internalno
	 * @param projectName
	 * @param country
	 * @param province
	 * @param city
	 * @param address
	 * @param bidDate
	 * @param areaSqFt
	 * @throws InterruptedException 
	 */
	
	@Test(priority =3, dataProvider="CreateNewProject")
	public void testCreateNewProject(String internalno, String projectName, String country, String province,
			String city, String address, String bidDate, String areaSqFt) throws InterruptedException {

		Thread.sleep(3000);
		//Fetch form
		WebElement form = driver.findElement(By.tagName("form"));
		
		WebElement div_root = form.findElement(By.xpath(".//div[contains(@class,'items')]"));
		
	    List<WebElement> div_list = div_root.findElements(By.xpath(".//div[contains(@class,'item')]"));
		
		for(WebElement div_elem : div_list) {
			
			WebElement label = div_elem.findElement(By.xpath(".//label"));
			String label_txt = label.getText().toUpperCase();
			
			if(label_txt !=null && label_txt.contains("Internal No.".toUpperCase())) {
				WebElement input = div_elem.findElement(By.xpath(".//input"));
				input.click();
				Thread.sleep(1000);
				input.sendKeys(internalno);
				continue;				
			}
			
			if(label_txt !=null && label_txt.contains("Project Name".toUpperCase())) {
				WebElement input = div_elem.findElement(By.xpath(".//input"));
				input.click();
				Thread.sleep(1000);
				input.sendKeys(projectName);
				continue;
			}
			
			if(label_txt !=null && label_txt.contains("Country".toUpperCase())) {
				WebElement input = div_elem.findElement(By.xpath(".//input"));
				input.click();
				Thread.sleep(1000);
				input.sendKeys(country);
				continue;
			}
			
			if(label_txt !=null && label_txt.contains("State/Province".toUpperCase())) {
				WebElement input = div_elem.findElement(By.xpath(".//input"));
				input.click();
				Thread.sleep(1500);
				input.sendKeys(province);
				continue;
			}
			
			if(label_txt !=null && label_txt.contains("City".toUpperCase())) {
				WebElement input = div_elem.findElement(By.xpath(".//input"));
				input.click();
				Thread.sleep(1500);
				input.sendKeys(city);
				continue;
			}
			
			if(label_txt !=null && label_txt.contains("Address".toUpperCase())) {
				WebElement tArea = div_elem.findElement(By.xpath(".//textarea"));
				tArea.click();
				Thread.sleep(1000);
				tArea.sendKeys(address);
				continue;
			}
			
			if(label_txt !=null && label_txt.contains("Bid Date".toUpperCase())) {
				WebElement input = div_elem.findElement(By.xpath(".//input"));
				input.click();
				Thread.sleep(1000);
				input.sendKeys(bidDate);
				continue;
			}
			
			if(label_txt !=null && label_txt.contains("Area".toUpperCase())) {
				WebElement input = div_elem.findElement(By.xpath(".//input"));
				input.click();
				Thread.sleep(1000);
				input.sendKeys(areaSqFt);
			}
			
		}
		
		WebElement form_button = form.findElement(By.xpath(".//button[@type='submit']"));
		Thread.sleep(2000);
		form_button.click();
	}
	/**
	 * This dataProvider provides select Commercial Building of Mall and Retail and Residential Building of Apartment and Condominium
	 * @return
	 */
	@DataProvider(name="MultipleBuildingClasses")
	public Object[][] getMultipleBuildingClassesData(){
		return new Object[][] {{"Commercial,Mall,Retail", "Residential,Apartment,Condominium"}};
	}
		
	/**
	 * This method returns Commercial Building of Mall and Retail and Residential Building of Apartment and Condominium
	 * @param buildingclassA
	 * @param buildingclassB
	 * @throws InterruptedException
	 */
	@Test(priority = 4, dataProvider="MultipleBuildingClasses")
	public void testMultipleBuildingClasses(String buildingclassA,String buildingclassB) throws InterruptedException {

		List<String> building_classes = new ArrayList<>();
		building_classes.add(buildingclassA);
		building_classes.add(buildingclassB);

		// Select multi_checkbox
		WebElement multi_checkbox = driver.findElement(By.xpath("//input[@name='isMulti' and @type='checkbox']"));
		Thread.sleep(3000);
		multi_checkbox.click();
		Thread.sleep(2000);

		// Fetch form
		WebElement form = driver.findElement(By.tagName("form"));
		List<WebElement> div_root = form.findElements(By.xpath(".//div[contains(@class,'MuiGrid-item')]"));

		for (String building : building_classes) {

			String[] arr = building.split(",");

			for (WebElement elem : div_root) {

				WebElement building_class_label = elem.findElement(By.xpath(".//p"));
				String building_class_txt = building_class_label.getText();

				if (arr[0].equalsIgnoreCase(building_class_txt)) {

					elem.click();
					Thread.sleep(2000);
					
					List<WebElement> building_types = form.findElements(By.xpath(".//div[contains(@aria-describedby,'helper-text')]"));

					for (WebElement building_element : building_types) {

						WebElement building_label = building_element.findElement(By.xpath(".//span[contains(@class,'MuiFormControlLabel')]"));

						for (int i = 1; i < arr.length; i++) {

							if (arr[i].equalsIgnoreCase(building_label.getText())) {

								WebElement building_checkbox = building_element.findElement(By.xpath(".//input"));
								building_checkbox.click();
								Thread.sleep(1500);
							}
						}

					}
				}
			}

			Thread.sleep(2000);

		}
		
		WebElement form_button = form.findElement(By.xpath(".//button[@type='submit']"));
		Thread.sleep(3000);
		form_button.click();
				
	}

	
	@Test(priority = 5, description="Clicking the finish button")
	public void testFinish() throws InterruptedException {
		
		Thread.sleep(3000);
		WebElement finish_button = driver.findElement(By.xpath("//button[@type='submit']"));
		
		Thread.sleep(3000);
		finish_button.click();
	}
	
	@AfterClass(description ="Run after our test")
	  public void tearDown() throws InterruptedException {
		  Thread.sleep(1000);
		  driver.quit();
	  }

}
