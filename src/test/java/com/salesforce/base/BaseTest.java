package com.salesforce.base;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import com.salesforce.pages.home.HomePage;
import com.salesforce.pages.login.CheckEmailPage;
import com.salesforce.pages.login.ForgotPasswordPage;
import com.salesforce.pages.login.LoginPage;
import com.salesforceApp.utilities.CommonUtilities;
import com.salesforceApp.utilities.Constants;
import com.salesforceApp.utilities.GenerateReports;

import io.github.bonigarcia.wdm.WebDriverManager;


public class BaseTest {

	protected static WebDriver driver;												//static class members
	protected static WebDriverWait wait;
	public static GenerateReports report;
	public static SoftAssert soft;
	protected static LoginPage objLogin;
	protected static HomePage objHome;
	protected static ForgotPasswordPage objFrgtPwd;
	protected static CheckEmailPage objChkEmail;
	
	@BeforeTest
	public void initialTestSetup() {
		System.out.println("@BeforeTest execution started");
		report = GenerateReports.getInstance();
	}
	
	@Parameters("browser")
	@BeforeMethod 
	public void setUp(String browserName) throws IOException {

		System.out.println("@BeforeMethod execution started and browser name=" +browserName);
		String url = CommonUtilities.getPropertyValue("url");
		setDriver(browserName);
		gotoUrl(url); 
		maximizeWindow();
		waitforPageToLoad();
	}
		
	@AfterMethod
	public void tearDown() throws InterruptedException {
		System.out.println("@AfterMethod execution started");
		Thread.sleep(5000);
		closeAllDrivers();
	}
	
	@AfterTest
	public void finalTearDown() {
		System.out.println("@AfterTest execution started");
	}
	
	
	public static void setDriver(String browserName) {
		
		if(browserName.equalsIgnoreCase("edge")) {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
			System.out.println("Edge driver instance created");
		}
			
		else if(browserName.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			System.out.println("Firefox driver instance created");
		}
			
		else if(browserName.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			System.out.println("Chrome driver instance created");
		}
	}
	
	public static WebDriver getDriverInstance() {
		return driver;
	}
	
	public static void getEdgeDriver() {
		
		WebDriverManager.edgedriver().setup();
		driver = new EdgeDriver();
		System.out.println("Edge driver instance created");
		//report.logTestInfo("Edge driver instance created");      //to log onto the Extent report
	}
	
	public static void getChromeDriver() {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			System.out.println("Chrome driver instance created");
			//report.logTestInfo("Chrome driver instance created");
	}
	
	public static void getFirefoxDriver() {
		WebDriverManager.firefoxdriver().setup();
		driver = new FirefoxDriver();
		System.out.println("Firefox driver instance created");
		//report.logTestInfo("Firefox driver instance created");
	}
	
	public static void gotoUrl(String url) {
		driver.get(url);
		System.out.println("url entered is " + url);
		//report.logTestInfo("url entered is " + url);
	}
	
	public static void maximizeWindow() {
		driver.manage().window().maximize();
		System.out.println("Browser window maximized");
		//report.logTestInfo("Browser window maximized");
	}
	
	public static void closeDriver() {
		implicitWait();
		driver.close();
		System.out.println("Driver instance closed");
		//report.logTestInfo("Driver instance closed");
	}
	
	public static void closeAllDrivers() {
		implicitWait();
		driver.quit();
		System.out.println("All driver instances closed");
		//report.logTestInfo("All driver instances closed");
	}
	
	public static void waitforPageToLoad() {
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
	}
	
	public static void implicitWait() {
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}
	
	
//	public static String getCurrentMethodName() {
//		
//		String methodName = new Object() {}
//		.getClass()
//		.getEnclosingClass()
//		.getName();
//		return methodName;
//	}
	
	public static void captureScreenShot(String methodName) throws IOException {					//pass name of the method in which screenshot needs to be taken	
		
		String fdate = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date());
					
		TakesScreenshot screenCapture = (TakesScreenshot) driver;				//typecast driver to the type TakesScreenshot
		File sourceFile = screenCapture.getScreenshotAs(OutputType.FILE);		//Take the screenshot and convert it to some File (jpg, png, etc.) format
		String path = Constants.SCREENSHOT_PATH + methodName + "_" + fdate+ ".jpg";			//path of file where screenshot needs to be saved
		File destFile = new File(path);
		FileUtils.copyFile(sourceFile, destFile);
		System.out.println("Captured screenshot of the page");
	}

}
