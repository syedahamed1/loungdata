package Base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import Utilities.ExcelReader;
import Utilities.ExtentManager;

public class Main {
//syedahm
	public static WebDriver driver;
	public static FileInputStream fis;
	public static Properties config= new Properties();
	public static Properties or= new Properties();
	public static ExcelReader excel = new ExcelReader(
			System.getProperty("user.dir") + "\\src\\test\\resources\\excel\\testdata.xlsx");
	public static Logger log = Logger.getLogger("devpinoyLogger");
	public ExtentReports rep = ExtentManager.getInstance();
	public static ExtentTest test;
	
	@BeforeSuite
	public void setup() {
		
		if(driver==null) {
			
			try {
				fis = new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/properties/Config.properties");
				try {
					config.load(fis);
					log.debug("config property loaded.");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				fis = new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/properties/OR.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				or.load(fis);
				log.debug("or property loaded.");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(config.getProperty("browser").equals("firefox")) {
			System.setProperty("webdriver.firefox.marionette",System.getProperty("user.dir") + "/src/test/resources/executables/geckodriver.exe");
			driver=new FirefoxDriver();
			log.debug("Firfox is opened.");
		}
		if(config.getProperty("browser").equals("chrome")) {
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/test/resources/executables/chromedriver.exe");
			driver=new ChromeDriver();
			log.debug("chrome is opened.");
		}
		if(config.getProperty("browser").equals("ie")) {
			System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") + "/src/test/resources/executables/IEDriverServer.exe");
			driver = new InternetExplorerDriver();
			log.debug("ie is opened.");
		}
		
		driver.get(config.getProperty("URL"));
		log.debug("Url is opened in browser.");
		driver.manage().window().maximize();
			
		
	}
	
	public void sendkeys(String locator,String value) {
		if(locator.endsWith("_css")) {
			driver.findElement(By.cssSelector(or.getProperty(locator))).sendKeys(value);
		}
		if(locator.endsWith("_xpath")) {
			driver.findElement(By.xpath(or.getProperty(locator))).sendKeys(value);
		}
	}
	
	public void click(String locator) {
		if(locator.endsWith("_css")) {
			driver.findElement(By.cssSelector(or.getProperty(locator))).click();
		}
		
		if(locator.endsWith("_xpath")) {
			driver.findElement(By.xpath(or.getProperty(locator))).click();
		}
	}
	
	
	
	@AfterSuite
	public void teardown() {
		if(driver!=null) {
			//driver.close();
			//driver.quit();
		}
	}
	
}
