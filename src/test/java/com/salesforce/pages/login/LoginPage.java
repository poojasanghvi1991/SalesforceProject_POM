package com.salesforce.pages.login;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.salesforce.base.BasePage;


public class LoginPage extends BasePage {
	

	@FindBy(id = "username") WebElement username;
	@FindBy(id = "password") WebElement password;
	@FindBy(name = "Login")	WebElement loginButton;
	@FindBy(name = "rememberUn") WebElement rememberCheckbox;
	@FindBy(id = "forgot_password_link") WebElement forgotPwdLink;
	@FindBy(id = "error") WebElement noPwdError;
	@FindBy(xpath = "//span[@id='idcard-identity']") WebElement displayedUsername;
	@FindBy(xpath = "//div[@id='error']") WebElement loginError;
	
	public LoginPage(WebDriver driver) {
		
		super(driver);
	}
	
	public void setUsername(String userName) {
		waitUntilElementVisible(username);
		enterText(username, userName, "Username textbox");
	}
	
	public void clearPasswordField() {
		clearElement(password,"Password textbox");
	}
	
	public void setPassword(String passWord) {	
		enterText(password, passWord, "Password textbox");
	}
	
	public void clickLoginButton() {
		singleClick(loginButton, "Login button");
	}
	
	public void checkRemeberMe() {
		singleClick(rememberCheckbox, "Remember Me checkbox");
	}
	
	public void clickForgotPwdLink() {
		singleClick(forgotPwdLink, "Forgot Password Link");
	}
	
	public String getNoPwdError() {
		String errorMessage = fetchText(noPwdError, "Error message field");
		return errorMessage;
	}
	
	public void loginToSalesforce(String username, String password) {
		this.setUsername(username);
		this.setPassword(password);
		this.clickLoginButton();
	}
	
	public String getDisplayedUsername() {
		waitUntilElementVisible(displayedUsername);
		String username = fetchText(displayedUsername, "Username textbox");
		return username;
	}
	
	public boolean isRememberMeChecked() {
		boolean flag = isElementChecked(rememberCheckbox, "Remember Me checkbox");
		return flag;
	}
	
	public String getLoginError() {
		waitUntilElementVisible(loginError);
		String loginErrorText = fetchText(loginError, "Login Error alert");
		return loginErrorText;
	}
}
