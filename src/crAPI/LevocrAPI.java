package crAPI;

import java.net.MalformedURLException;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class LevocrAPI {

	public static void main(String[] args) throws InterruptedException, MalformedURLException {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless");
		options.addArguments("start-maximized");
		options.addArguments("headless");
		options.addArguments("--no-sandbox");
		options.addArguments("window-size=1920,1080");
		options.addArguments("--disable-dev-shm-usage");
		options.addArguments("--verbose");
		options.addArguments("--whitelisted-ips=''");
		options.setAcceptInsecureCerts(true);
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/chromedriver");
		WebDriver driver=new ChromeDriver(options);
		try
		{
		driver.manage().window().maximize();
		driver.get("http://35.225.176.150/");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		WebDriverWait element = new WebDriverWait(driver, Duration.ofSeconds(10));
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		//login
		login(driver);
		//DashBoard
		dashBoard(driver,element,jse);		
		//Shop
		//shop(driver,element,jse);		
		//Community
		community(driver,element,jse);	
		//logout
		logOut(driver,element,jse);	
		}
		finally {
		driver.quit();
		}
	}
	
	public static void login(WebDriver driver)
	{
		driver.findElement(By.id("basic_email")).sendKeys("hacker@darkweb.com");
		driver.findElement(By.id("basic_password")).sendKeys("Hack3r$$$");
		driver.findElement(By.cssSelector("button[type='submit']")).click();
		System.out.println("Login Successfull");
	}
	public static void dashBoard(WebDriver driver, WebDriverWait element,JavascriptExecutor jse) throws InterruptedException
	{
		driver.findElement(By.cssSelector("span[class='ant-menu-title-content']")).click();
		driver.findElement(By.xpath("//span[text()='Contact Mechanic']")).click();
		driver.findElement(By.id("add-vehicle_mechanicCode")).click();
		driver.findElement(By.xpath("//div[@title='TRAC_MECH1']")).click();
		driver.findElement(By.id("add-vehicle_problemDetails")).click();
		driver.findElement(By.id("add-vehicle_problemDetails")).sendKeys("Test");
		driver.findElement(By.cssSelector("[type='submit']")).click();
		element.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='OK']")));
		driver.findElement(By.xpath("//span[text()='OK']")).click();
		driver.findElement(By.xpath("//span[text()='View Service Reports']")).click();
		WebElement element1 = (new WebDriverWait(driver, Duration.ofSeconds(60))).until(ExpectedConditions.elementToBeClickable(By.className("ant-table-row")));
		driver.findElement(By.xpath("//span[text()='Back to Dashboard']")).click();
		System.out.println("Dashboard action Successfull");
	}
	public static void shop(WebDriver driver, WebDriverWait element,JavascriptExecutor jse) throws InterruptedException
	{
		driver.findElement(By.xpath("//span[text()='Shop']")).click();
		element.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Buy']")));
		driver.findElement(By.xpath("//span[text()='Buy']")).click();			
		Thread.sleep(5000);
		driver.findElement(By.xpath("//span[text()='OK']")).click();
		element.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Return']")));
		driver.findElement(By.xpath("//span[text()='Return']")).click();
		element.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='OK']")));
		driver.findElement(By.xpath("//span[text()='OK']")).click();
		jse.executeScript("window.scrollBy(0,-500)");
		System.out.println("Shop action Successfull");
	}
	public static void community(WebDriver driver, WebDriverWait element,JavascriptExecutor jse) throws InterruptedException
	{
		driver.findElement(By.xpath("//span[text()='Community']")).click();
		driver.findElement(By.xpath("//span[text()='New Post']")).click();
		driver.findElement(By.id("new-post_title")).click();
		driver.findElement(By.id("new-post_title")).sendKeys("Test");
		driver.findElement(By.id("new-post_content")).click();
		driver.findElement(By.id("new-post_content")).sendKeys("Test");
		element.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Add New Post']")));
		driver.findElement(By.xpath("//span[text()='Add New Post']")).click();
		element.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='OK']")));
        driver.findElement(By.xpath("//span[text()='OK']")).click();
        Thread.sleep(2000);
		element.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div[class='ant-row'] div")));
		driver.findElement(By.cssSelector("div[class='ant-row'] div")).click();
		Thread.sleep(2000);
		element.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Add Comment']")));
		driver.findElement(By.xpath("//span[text()='Add Comment']")).click();
		driver.findElement(By.id("basic_comment")).click();
		driver.findElement(By.id("basic_comment")).sendKeys("Test1");
		Thread.sleep(2000);
		element.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Add a Comment']")));
		driver.findElement(By.xpath("//span[text()='Add a Comment']")).click();
		element.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='OK']")));
		driver.findElement(By.xpath("//span[text()='OK']")).click();
		System.out.println("Community action Successfull");
	}
	public static void logOut(WebDriver driver, WebDriverWait element,JavascriptExecutor jse) throws InterruptedException
	{
		element.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='ant-dropdown-trigger nav-items']")));
		driver.findElement(By.className("ant-dropdown-trigger")).click();
		element.until(ExpectedConditions.elementToBeClickable(By.cssSelector("li[data-menu-id*='logout']")));
		Thread.sleep(3000);
		driver.findElement(By.cssSelector("li[data-menu-id*='logout']")).click();
		System.out.println("LogOut Successfull");
	}

}
