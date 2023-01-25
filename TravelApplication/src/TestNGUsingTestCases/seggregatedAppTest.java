package TestNGUsingTestCases;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class seggregatedAppTest {
	
	static WebDriver driver;
	static 	WebDriverWait wait;
	static Actions actions;
	@BeforeMethod
	public void launchApp()
	{
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver\\chromedriver.exe");
		driver = new ChromeDriver();
		actions = new Actions(driver);
		wait = new WebDriverWait(driver,Duration.ofSeconds(5));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get("https://www.kayak.com/");
		driver.manage().window().maximize();
	}
	
	@Test(priority = 1)
	public void loginApp() throws InterruptedException
	{
		//Validation of Login of the Application
		System.out.println("*****Begin of Test Case 1*******");
		System.out.println("Check the Login Functionality");
		driver.findElement(By.xpath("//div[@class='sign-in-nav-link']")).click();
		driver.findElement(By.xpath("//div[text()='Google']")).click();
		
		//Multiple window handling performed for the Web Application
		Set<String> lstWindows = driver.getWindowHandles();
		Iterator<String> windowsOpened = lstWindows.iterator(); 
		String parentWindow = windowsOpened.next();
		String childWindow = windowsOpened.next();
		
		driver.switchTo().window(childWindow);
		driver.findElement(By.xpath("//input[@type='email']")).sendKeys("seleniumapp07@gmail.com");
		driver.findElement(By.xpath("//span[text()='Next']")).click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='password']")));
		driver.findElement(By.xpath("//input[@type='password']")).sendKeys("#SeleniumApp07");
		driver.findElement(By.xpath("//span[text()='Next']")).click();

		driver.switchTo().window(parentWindow);
		
		Thread.sleep(3000);
	    driver.switchTo().frame(driver.findElement(By.xpath("//*[@title='Sign in with Google Dialog']")));
		Thread.sleep(1000);
		
		driver.findElement(By.xpath("//*[contains(@class,'LgbsSe-bN97Pc')]")).click();
		driver.switchTo().defaultContent();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Selenium']")));
		boolean loginStatus = driver.findElement(By.xpath("//span[text()='Selenium']")).isDisplayed();
		Assert.assertTrue(loginStatus,"Login is Not Successful in Kayak Application");
		System.out.println("Login to the Application is Successful");	
	}
	
	@Test(priority=2)
	public void flightSearchCalendarBased() throws InterruptedException
	{
		// Search from and to location by selecting particular dates
		System.out.println();
		System.out.println("*****Begin of Test Case 2*******");
		System.out.println("Entered the Location, From and To place, Search the flights");
		WebElement to = driver.findElement(By.cssSelector("input[placeholder='To?']"));
		
		to.sendKeys("ROA");
		Thread.sleep(3000);
		to.sendKeys(Keys.ARROW_DOWN.ENTER);
		driver.findElement(By.xpath("//*[@aria-label='Start date calendar input']")).click();
		//Action class used to perform the double click
		actions.doubleClick(driver.findElement(By.xpath("//div[@aria-label='Saturday February 25, 2023']"))).build().perform();
		driver.findElement(By.xpath("//*[@aria-label='End date calendar input']")).click();
		actions.doubleClick(driver.findElement(By.xpath("(//div[@aria-label='Wednesday March 1, 2023'])[2]"))).build().perform();
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		Thread.sleep(5000);
		System.out.println("List of Flights are displayed successfully");
		System.out.println();
	}
	
	@Test
	public void verifyTitlePage()
	{
		String title = driver.getTitle();
		System.out.println("Title of the page is : "+ title);
		Assert.assertEquals(title, "Search Flights, Hotels & Rental Cars | KAYAK");
	}
	
	@Test
	public void verifyDropdownList() throws InterruptedException
	{
		driver.findElement(By.xpath("(//span[@class='wIIH-mod-alignment-left'])[2]")).click();
		Thread.sleep(3000);
		List<String> givenDropDown = Arrays.asList("Economy", "Premium Economy","Business","First","Multiple");
			
		List<WebElement> flightTypeElements = driver.findElements(By.xpath("//ul[@role='tablist']/li"));
		for(WebElement elem : flightTypeElements)
		{
			if(!givenDropDown.contains(elem.getText()))
			{
				System.out.println("Incorrect Match");
				Assert.assertTrue(false);
			}
			
		}
		
	}
	
	@AfterMethod
	public void terminateCurrentSession()
	{
		driver.quit();
	}

}
