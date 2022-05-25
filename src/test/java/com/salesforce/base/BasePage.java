package com.salesforce.base;

import java.awt.Robot;
import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.salesforceApp.utilities.GenerateReports;


public class BasePage {

	protected static WebDriver driver;
	protected static WebDriverWait wait;
	protected static Alert alert;
	protected static Actions action;
	protected static Robot robot;
	public static GenerateReports report=GenerateReports.getInstance();
	
	public BasePage(WebDriver driver) {

		BasePage.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public static String fetchText(WebElement element, String objName) {
		waitUntilElementVisible(element);
		String text = element.getText();
		System.out.println("Text retrieved from " +objName +" is: " +text);
		report.logTestInfo("Text retrieved from " +objName +" is: " +text);
		return text;
	}
	
	public static void clearElement(WebElement element, String objName) {		//object name is any user-defined name just to identify what the web element is
		if(element.isDisplayed()) {
			element.clear();
			System.out.println("pass: " +objName+ " element cleared");
			report.logTestInfo(objName + " field cleared");
		}
		else
			System.out.println("fail: " +objName+ " element not displayed");
	}
	
	public static void enterText(WebElement element, String text, String objName) {
		if (element.isDisplayed()) {
			element.clear();
			element.sendKeys(text);
			System.out.println("pass: text entered in " +objName);
			report.logTestInfo("text entered in " + objName + " field");
		}
		else
			System.out.println("fail: " + objName + " element not displayed");			
	}
	
	public static boolean isElementChecked(WebElement element, String objName) {
		
		boolean flag = element.isSelected();
		if (flag == true) {
			System.out.println("pass: " + objName + " is checked");
			report.logTestInfo(objName + " is checked");
		}
		else {
			System.out.println("fail: " + objName + " is not checked");
			report.logTestInfo(objName + " is not checked");
		}
		return flag;
	}
	
	public static void singleClick(WebElement element, String objName) {
		waitUntilElementVisible(element);
		if (element.isDisplayed()) {
			element.click();
			System.out.println("pass: " + objName+ " element clicked");
			report.logTestInfo(objName + " clicked");
		}
		else {
			System.out.println("fail: " + objName + " element not displayed");
			report.logTestInfo(objName + "not displayed");
		}
	}
	
	public static void doubleClick(WebElement element, String objName) {
		waitUntilElementVisible(element);
		if (element.isDisplayed()) {
			Actions action = new Actions(driver);
			action.doubleClick(element).build().perform();
			System.out.println("pass: " + objName+ " element double clicked");
			report.logTestInfo(objName + " double clicked");
		}
		else {
			System.out.println("fail: " + objName + " element not displayed");	
			report.logTestInfo(objName + "not displayed");
		}
	}
	
	public static void rightClick(WebElement element, String objName) {
		waitUntilElementVisible(element);
		if (element.isDisplayed()) {
			Actions action = new Actions(driver);
			action.contextClick(element).build().perform();
			System.out.println("pass: " + objName+ " element right clicked");
			report.logTestInfo(objName + " right clicked");
		}
		else
			System.out.println("fail: " + objName + " element not displayed");
	}
	
	public static void selectByValueData(WebElement element, String value, String objName) {
		Select option = new Select(element);
		option.selectByValue(value);
		System.out.println(objName + " selected " + value);
		report.logTestInfo(objName + " selected " + value);
	}
	
	public static void selectByTextDisplayed(WebElement element, String text, String objName) {
		Select option = new Select(element);
		option.selectByVisibleText(text);
		System.out.println(objName + " selected " +text);
		report.logTestInfo(objName + " selected " +text);
	}
	
	public static void selectByIndexData(WebElement element, int index, String objName) {
		Select option = new Select(element);
		option.selectByIndex(index);
		System.out.println(objName + " selected option with index " + index);
		report.logTestInfo(objName + " selected option with index " + index);
	}
	
	public static void mouseHover(WebElement element) {
		waitUntilElementVisible(element);
		action = new Actions(driver);
		action.moveToElement(element).build().perform();	
	}
	
	private static Alert switchToAlert() {				//this method is defined as private because it will be used only by this BaseScripts class
		return driver.switchTo().alert();
	}
	
	public static void acceptWindowAlert() {
		waitUntilAlertIsPresent();
		alert = switchToAlert();
		System.out.println("alert text=" + alert.getText());
		report.logTestInfo("Alert text: " + alert.getText());
		alert.accept();
	}
	
	public static void dismissWindowAlert() {
		waitUntilAlertIsPresent();
		alert = switchToAlert();
		System.out.println("alert text=" + alert.getText());
		report.logTestInfo("Alert text: " + alert.getText());
		alert.dismiss();
	}
	
	public static void handlePromptAlert(String inputValue) {
		waitUntilAlertIsPresent();
		alert = switchToAlert();
		alert.sendKeys(inputValue);
		System.out.println("alert text=" + alert.getText());
		report.logTestInfo("Alert text: " + alert.getText());
		alert.accept();
	}
	
	public static void closeDriver() {
		implicitWait();
		driver.close();
		System.out.println("Driver instance closed");
		report.logTestInfo("Driver instance closed");
	}
	
	public static void closeAllDrivers() {
		implicitWait();
		driver.quit();
		System.out.println("All driver instances closed");
		report.logTestInfo("All driver instances closed");
	}
	
	public static void waitforPageToLoad() {
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
	}
	
	public static void implicitWait() {
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}
	
	public static void waitUntilVisibleByLocator(By locator) {
		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	
	public static void waitUntilElementVisible(WebElement element) {
		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.visibilityOf(element));
	}
	
	public static void waitUntilAlertIsPresent() {
		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.alertIsPresent());
	}
	
	public static void waitUntilElementIsClickable(WebElement element) {
		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}
	
	public static void fluentWait(By locator) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(30))
				.pollingEvery(Duration.ofSeconds(5))
				.ignoring(NoSuchElementException.class, ElementNotInteractableException.class);
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	
	public static void switchToNewWindow(String baseWindowHandle) {
		Set<String> allWindowHandles = driver.getWindowHandles();
		for (String handle : allWindowHandles) {
			if(!baseWindowHandle.equalsIgnoreCase(handle))
				driver.switchTo().window(handle);
		}
	}
	
	public static void readWebTable(WebElement table) {	
		List<WebElement> allRows = table.findElements(By.tagName("tr"));					//or use By.xpath("tbody/tr")
		System.out.println("number of rows=" + allRows.size());
		for (WebElement row : allRows) {
			
			List<WebElement> header = row.findElements(By.tagName("th"));
			for (WebElement head : header) {
				System.out.println(head.getText());
			}
			List<WebElement> allColData = row.findElements(By.tagName("td"));				//or use By.xpath("td")
			for (WebElement data : allColData) {
				System.out.print(data.getText() + "\t");	
			}
			System.out.println();
		}	
	}
}
