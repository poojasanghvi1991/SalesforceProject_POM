package com.salesforce.pages.login;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.salesforce.base.BasePage;

public class ForgotPasswordPage extends BasePage {

	@FindBy(id = "header") WebElement ForgotPwdHeader;
	@FindBy(xpath = "//input[@type='email']") WebElement emailField;
	@FindBy(name = "continue") WebElement continueButton;
	
	public ForgotPasswordPage(WebDriver driver) {
		super(driver);
	}
	
	public String getForgotPwdHeaderText() {
		String headerText = fetchText(ForgotPwdHeader, "Forgot Password header");
		return headerText;
	}
	
	public void enterEmail(String username) {
		enterText(emailField, username, "Username textbox");
	}
	
	public void clickContinueButton() {
		singleClick(continueButton, "Continue button");
	}
}
