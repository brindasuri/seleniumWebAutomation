package standAloneApplication;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.reporters.Files;

public class automatedAllTestCases {

	public static void main(String[] args) throws InterruptedException, IOException {
		// TODO Auto-generated method stub
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		Actions actions = new Actions(driver);
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	    
		driver.get("https://www.kayak.com/");
		driver.manage().window().maximize();
		Assert.assertEquals(driver.getTitle(), "Search Flights, Hotels & Rental Cars | KAYAK");
		System.out.println("Kayak Application Opened Successfully");
		
		
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
		screenshotCapture(driver,"testCase1.png");
		System.out.println("Login to the Application is Successful");
		
		
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
		Thread.sleep(3000);
		screenshotCapture(driver,"testCase2.png");
		System.out.println("List of Flights are displayed successfully");
		System.out.println();
		
        //Increase the number of adults and search flights
		System.out.println("*****Begin of Test Case 3*******");
		System.out.println("Increase the number of adults to 2 from dropdown and search the results for the same");
		driver.findElement(By.xpath("//span[@class='S9tW-title']")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("(//button[@class='bCGf-mod-theme-default'])[1]")).click();
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		Thread.sleep(6000);
		String numberOfAdults=driver.findElement(By.cssSelector("span[class*='-title']")).getText();
		screenshotCapture(driver,"testCase3.png");
		Assert.assertEquals(numberOfAdults, "2 travelers");
		System.out.println("Flights shown based on two adults selected from Dropdown");
		
		//Navigate Home Page and select the one way from drop down and search the windows
		System.out.println("*****Begin of Test Case 4*******");
		System.out.println("Navigate back to Home Page, selected one traveller with one checkin bag");
		driver.findElement(By.xpath("(//span[@class='logo-image'])")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//div[@class='zcIg zcIg-pres-default']/div[1]")).click();
		driver.findElement(By.xpath("//*[@aria-label='One-way']")).click();
		
		driver.findElement(By.xpath("//div[@class='zcIg zcIg-pres-default']/div[4]")).click();
		driver.findElement(By.xpath("(//button[@class='bCGf-mod-theme-default'])[2]")).click();
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		Thread.sleep(3000);
		String tripType=driver.findElement(By.cssSelector("span[class*='wIIH-mod-alignment-left']")).getText();
		Assert.assertEquals(tripType, "One-way");
		System.out.println("One-way flights are shown");
		System.out.println();

		String bagType=driver.findElement(By.xpath("(//span[contains(@class,'-title')])[2]")).getText();
		screenshotCapture(driver,"testCase4.png");
		Assert.assertEquals(bagType, "1 carry-on bag");
		System.out.println("Flights based on 1 carry-on bag are shown");
		System.out.println("Flights based on selection has been shown successfully");
		System.out.println();
		
		//Navigate back and view profile information
		System.out.println("*****Begin of Test Case 5*******");
		System.out.println("Navigate back and go to profile details and retrieve user email");
		driver.navigate().back();
		Thread.sleep(3000);
		driver.findElement(By.className("account-label__inner")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[text()='Your account']")).click();
		Thread.sleep(3000);
		driver.findElement(By.cssSelector("button[data-tab='account']")).click();
		Thread.sleep(3000);
		System.out.println("Scroll to view the user account info");
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("window.scrollBy(0,100)");
		Thread.sleep(3000);
		String accountEmail = driver.findElement(By.cssSelector("div[id*='emailBlock'] div:nth-child(2)")).getText();
		Assert.assertEquals(accountEmail, "seleniumapp07@gmail.com");
		screenshotCapture(driver,"testCase5.png");
		System.out.println("Current Profile of kayak is of "+accountEmail );
		System.out.println();
		
		//Logout from the session
		System.out.println("*****Begin of Test Case 6*******");
		System.out.println("Logout from the current account");
		driver.findElement(By.className("account-label__inner")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//div[text()='Sign out']")).click();
		Thread.sleep(2000);
		String lgout = driver.findElement(By.xpath("//div[@class='sign-in-nav-link']")).getText();
		screenshotCapture(driver,"testCase6.png");
	    Assert.assertEquals(lgout, "Sign in");
	    System.out.println("Logout from the account successfully");
	    driver.quit();
	}
	
	public static void screenshotCapture(WebDriver driver, String testCaseNumber) throws IOException
	{
        TakesScreenshot sourceObject =((TakesScreenshot)driver);
        File source =sourceObject.getScreenshotAs(OutputType.FILE);
        String filePath = "C:\\Users\\brind\\Documents\\Documents\\automate_application_Selenium\\TravelApplication\\screenshot\\"+testCaseNumber;
        //Store Images in the output folder
          File destination = new File(filePath);

         //Copy output as screenshot
          FileUtils.copyFile(source, destination);
	}
}