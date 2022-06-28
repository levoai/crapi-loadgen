package ai.levo.crapi.loadgen;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Objects;

public class CrapiLoadgen {
    private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(CrapiLoadgen.class);

    public static void main(String[] args) throws InterruptedException, MalformedURLException {
        new CrapiLoadgen().generateLoad();
    }

    public void generateLoad() throws InterruptedException, MalformedURLException {
        System.setProperty("webdriver.chrome.whitelistedIps", "");
        WebDriverManager.chromedriver().setup();

        String remote = System.getenv("REMOTE_DRIVER_MODE");
        boolean isRemote = remote != null && remote.equalsIgnoreCase("true");
        WebDriver driver;
        if (isRemote) {
            String remoteHost = Objects.requireNonNullElse(System.getenv("SELENIUM_HOST"), "host.docker.internal");
            String remote_url_chrome = "http://" + remoteHost + ":4444/wd/hub";
            driver = new RemoteWebDriver(new URL(remote_url_chrome), getChromeOptions());
            LOG.info("REMOTE_SELENIUM_HOST: " + remoteHost);
        } else {
        	//Comment below line for local testing
        	 System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
            driver = new ChromeDriver(getChromeOptions());
        }
        //Comment below line for local testing
        String crapiUrl = Objects.requireNonNullElse((System.getenv("CRAPI_URL")), "http://localhost/");
        //Uncomment below line for local testing
        //String crapiUrl = Objects.requireNonNullElse("http://35.225.176.150", "");
        LOG.info("CRAPI URL: " + crapiUrl);
        try {
            driver.manage().window().maximize();
            driver.get(crapiUrl);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            WebDriverWait element = new WebDriverWait(driver, Duration.ofSeconds(20));
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            //login
            login(driver);

            int numIterations = Integer.parseInt(Objects.requireNonNullElse(System.getenv("NUM_ITERATIONS"), "5"));
            for (int i = 0; i < numIterations; i++) {
                dashBoard(driver, element, jse);
                shop(driver, element, jse);
                community(driver, element, jse);
            }

            //logout
            logOut(driver, element, jse);
        } finally {
            driver.quit();
        }
    }

    private ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("start-maximized");
        options.addArguments("--window-size=1200,600");
        options.setAcceptInsecureCerts(true);
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        // Check if there is a proxy configured.
        String proxy = System.getenv("HTTP_PROXY");
        if (proxy != null) {
            options.addArguments("--proxy-server=" + proxy);
        }

        return options;
    }

    private void login(WebDriver driver) {
        driver.findElement(By.id("basic_email")).sendKeys("hacker@darkweb.com");
        driver.findElement(By.id("basic_password")).sendKeys("Hack3r$$$");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        LOG.info("Login Successful");
    }

    private void dashBoard(WebDriver driver, WebDriverWait element, JavascriptExecutor jse) {
        //driver.findElement(By.cssSelector("span[class='ant-menu-title-content']")).click();
    	element.until(ExpectedConditions.elementToBeClickable(By.cssSelector("li.ant-menu-overflow-item.ant-menu-item.ant-menu-item-only-child")));
    	WebElement el=driver.findElement(By.cssSelector("li.ant-menu-overflow-item.ant-menu-item.ant-menu-item-only-child"));
    	jse.executeScript("arguments[0].click();", el);
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
        element.until(ExpectedConditions.invisibilityOfElementLocated(By.className("spiner")));
        LOG.info("Dashboard action Successful");
    }

    private void shop(WebDriver driver, WebDriverWait element, JavascriptExecutor jse) {
//    	element.until(ExpectedConditions.elementToBeClickable(By.cssSelector("li.ant-menu-overflow-item.ant-menu-item.ant-menu-item-only-child")));
//    	WebElement el=driver.findElement(By.cssSelector("li.ant-menu-overflow-item.ant-menu-item.ant-menu-item-only-child:"));
    	WebElement el=driver.findElement(By.xpath("//ul[contains(@class,'ant-menu-dark')]/li[2]"));
    	jse.executeScript("arguments[0].click();", el);
        element.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Buy']")));
//        driver.findElement(By.xpath("//span[text()='Buy']")).click();
//        Thread.sleep(5000);
//        driver.findElement(By.xpath("//span[text()='OK']")).click();
//        element.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Return']")));
//        driver.findElement(By.xpath("//span[text()='Return']")).click();
//        element.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='OK']")));
//        driver.findElement(By.xpath("//span[text()='OK']")).click();
//        jse.executeScript("window.scrollBy(0,-500)");
        LOG.info("Shop action Successful");
    }

    private void community(WebDriver driver, WebDriverWait element, JavascriptExecutor jse) throws InterruptedException {
      // driver.findElement(By.xpath("//span[text()='Community']")).click();
    	//element.until(ExpectedConditions.elementToBeClickable(By.cssSelector("li.ant-menu-overflow-item.ant-menu-item.ant-menu-item-only-child")));
    	WebElement el=driver.findElement(By.xpath("//ul[contains(@class,'ant-menu-dark')]/li[3]"));
    	jse.executeScript("arguments[0].click();", el);
//    	WebElement el=driver.findElement(By.cssSelector("li[data-menu-id*='-forum']"));
//    	JavascriptExecutor executor = (JavascriptExecutor) driver;
//    	executor.executeScript("arguments[0].click();", el);
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
        element.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='ant-row']/div[2]")));
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
        LOG.info("Community action Successful");
    }

    private void logOut(WebDriver driver, WebDriverWait element, JavascriptExecutor jse) throws InterruptedException {
//    	element.until(ExpectedConditions.elementToBeClickable(By.xpath("//header[@class='ant-layout-header']/div[2]/div[3]/div")));
//    	driver.findElement(By.xpath("//header[@class='ant-layout-header']/div[2]/div[3]/div")).click();
    	//element.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.ant-dropdown-trigger.nav-items")));
    	driver.navigate().refresh();
    	Thread.sleep(5000);
    	driver.findElement(By.cssSelector("div.ant-dropdown-trigger.nav-items")).click();
//        Thread.sleep(2000);
//        element.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[contains(@data-menu-id,'logout')]")));
//        WebElement el= driver.findElement(By.xpath("//li[contains(@data-menu-id,'logout')]"));
        element.until(ExpectedConditions.elementToBeClickable(By.cssSelector("li.ant-dropdown-menu-item:nth-child(2)")));
        WebElement el= driver.findElement(By.cssSelector("li.ant-dropdown-menu-item:nth-child(2)"));
        jse.executeScript("arguments[0].click();", el);
        LOG.info("LogOut Successful");
    }

}
