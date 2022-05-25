package com.salesforce.pages.login;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.salesforce.base.BasePage;


public class CheckEmailPage extends BasePage{

	@FindBy(id = "header") WebElement pageHeader;
	@FindBy(xpath = "//a[text()=Return to Login]") WebElement returnToLoginBtn;
	
	public CheckEmailPage(WebDriver driver) {
		super(driver);
	}
	
	public String getCheckEmailHeader() {
		String headerText = fetchText(pageHeader, "Check Email header");
		return headerText;
	}
	
	public void clickReturnToLoginButton() {
		singleClick(returnToLoginBtn, "Return to Login button");
	}
	
//	public String getPageTitle() {
//		return driver.getTitle();
//	}
}
