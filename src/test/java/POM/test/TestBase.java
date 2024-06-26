package POM.test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import java.util.concurrent.TimeUnit;

public class TestBase
{
    static WebDriver driver = null;
    public  String baseUrl = "https://katalon-demo-cura.herokuapp.com/";
    public static ExtentReports extent;
    public static ExtentSparkReporter spark;
    private static Logger logger = LogManager.getLogger("Info");

    @BeforeTest
    public void report1()
    {
        extent = new ExtentReports();
        spark = new ExtentSparkReporter("./Reports/MyReport.html");
        extent.attachReporter(spark);
    }
    @BeforeTest
    @Parameters("browser")
    public void setupBrowser(String browser)
    {
        try
        {
            if(browser.equalsIgnoreCase("chrome"))
            {
                WebDriverManager.chromedriver().setup();

            } else if (browser.equalsIgnoreCase("FireFox"))
            {
                WebDriverManager.chromedriver().setup();
                driver = new FirefoxDriver();
            } else if (browser.equalsIgnoreCase("Safari"))
            {
                WebDriverManager.safaridriver().setup();
                driver = new SafariDriver();
            } else if (browser.equalsIgnoreCase("Microsoft Edge"))
            {
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
            }
            else
            {
                throw new Exception("Incorrect Browser");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @BeforeSuite
    public void initialization()
    {
        try
        {
            logger.info("<<<<<<<<<<Initialization started>>>>>>>>>>");
            driver = new ChromeDriver();
            driver.get(baseUrl);
            driver.manage().deleteAllCookies();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            String expectedUrl = baseUrl;
            Assert.assertEquals(driver.getCurrentUrl(), expectedUrl);

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    @AfterSuite
    public void tearDown()
    {
        logger.info("<<<<<<<<<< Test Completed >>>>>>>>>>");
        driver.quit();
    }
}