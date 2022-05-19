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

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Objects;

public class CrapiLoadgen {
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
            System.out.println("REMOTE_SELENIUM_HOST: " + remoteHost);
        } else {
            System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
            driver = new ChromeDriver(getChromeOptions());
        }

        String crapiUrl = Objects.requireNonNullElse(System.getenv("CRAPI_URL"), "http://localhost/");
        System.out.println("CRAPI URL: " + crapiUrl);
        try {
            driver.manage().window().maximize();
            driver.get(crapiUrl);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            WebDriverWait element = new WebDriverWait(driver, Duration.ofSeconds(10));
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            //login
            login(driver);
            //DashBoard
            dashBoard(driver, element);
            //Shop
            //shop(driver,element,jse);
            //Community
            community(driver, element);
            //logout
            logOut(driver, element);
        } finally {
            driver.quit();
        }
    }

    private ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("start-maximized");
        options.addArguments("window-size=1200,600");
        options.setAcceptInsecureCerts(true);
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        return options;
    }

    private void login(WebDriver driver) {
        driver.findElement(By.id("basic_email")).sendKeys("hacker@darkweb.com");
        driver.findElement(By.id("basic_password")).sendKeys("Hack3r$$$");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        System.out.println("Login Successful");
    }

    private void dashBoard(WebDriver driver, WebDriverWait element) {
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
        element.until(ExpectedConditions.invisibilityOfElementLocated(By.className("spiner")));
        System.out.println("Dashboard action Successful");
    }

    private void shop(WebDriver driver, WebDriverWait element, JavascriptExecutor jse) throws InterruptedException {
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
        System.out.println("Shop action Successful");
    }

    private void community(WebDriver driver, WebDriverWait element) throws InterruptedException {
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
        System.out.println("Community action Successful");
    }

    private void logOut(WebDriver driver, WebDriverWait element) throws InterruptedException {
        element.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='ant-dropdown-trigger nav-items']")));
        driver.findElement(By.className("ant-dropdown-trigger")).click();
        Thread.sleep(2000);
        element.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[contains(@data-menu-id,'logout')]")));
        driver.findElement(By.xpath("//li[contains(@data-menu-id,'logout')]")).click();
        System.out.println("LogOut Successful");
    }

}
