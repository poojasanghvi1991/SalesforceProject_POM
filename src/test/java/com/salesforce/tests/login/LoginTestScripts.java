package com.salesforce.tests.login;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.salesforce.base.BaseTest;
import com.salesforce.pages.home.HomePage;
import com.salesforce.pages.login.CheckEmailPage;
import com.salesforce.pages.login.ForgotPasswordPage;
import com.salesforce.pages.login.LoginPage;
import com.salesforceApp.utilities.CommonUtilities;


public class LoginTestScripts extends BaseTest {

	
	@Test (priority=0, enabled=true)
	public static void TC01_testnoPasswordError() throws IOException {
		
		String expectedError = "Please enter your password.";
		objLogin = new LoginPage(driver);
		String username = CommonUtilities.getPropertyValue("username");
		objLogin.setUsername(username);
		objLogin.clearPasswordField();
		objLogin.clickLoginButton();
		String actualError = objLogin.getNoPwdError();
		Assert.assertTrue(actualError.equalsIgnoreCase(expectedError), "Expected error message is NOT displayed");
		
		String methodName = new Object() {} .getClass().getEnclosingMethod().getName();
		captureScreenShot(methodName);
	}

	@Test (priority=1, enabled=true)
	public static void TC02_validLogin() throws IOException, InterruptedException {
		
		soft = new SoftAssert();
		objLogin = new LoginPage(driver);
		String username = CommonUtilities.getPropertyValue("username");
		String password = CommonUtilities.getPropertyValue("password");
		objLogin.loginToSalesforce(username, password);
		waitforPageToLoad();
		
		Thread.sleep(3000);
		objHome = new HomePage(driver);
		String pageTitle = driver.getTitle();
		System.out.println("Page Title:" +pageTitle);
		report.logTestInfo("Page Title after clicking on LogIn:" +pageTitle);
		
		String expectedUserName = "Pooja Sanghvi";
		String userFullName = objHome.getHomePageDashboardUserName();
		System.out.println("Username appearing on the SFDC Home dashboard: " +userFullName);
		soft.assertEquals(userFullName, expectedUserName, "User's fullname on the dashboard does NOT match with the expected name");
		soft.assertAll();
		
		String methodName = new Object() {} .getClass().getEnclosingMethod().getName();
		captureScreenShot(methodName);
	}
	
	@Test(priority=2, enabled=true)
	public static void TC03_checkRememberMe() throws IOException, InterruptedException {
		
		soft = new SoftAssert();
		objLogin = new LoginPage(driver);
		String username = CommonUtilities.getPropertyValue("username");
		String password = CommonUtilities.getPropertyValue("password");
		objLogin.setUsername(username);
		objLogin.setPassword(password);
		objLogin.checkRemeberMe();
		objLogin.clickLoginButton();
		waitforPageToLoad();
		String expectedTitle = "Home Page";
		String actualTitle = driver.getTitle();
		System.out.println("Page redirected to after clicking on LogIn button: " + actualTitle);
		report.logTestInfo("Page redirected to after clicking on LogIn button: " + actualTitle);
		soft.assertTrue(actualTitle.contains(expectedTitle), "Home Page is not displayed");
		
		objHome = new HomePage(driver);
		objHome.clickUserMenuDropdown();
		objHome.clickLogoutLink();
		waitforPageToLoad();
		
		String expectedTitle1 = "Login | Salesforce";
		Thread.sleep(2000);
		String actualTitle1 = driver.getTitle();
		System.out.println("Page redirected to after logging out of Salesforce: " + actualTitle1);
		report.logTestInfo("Page redirected to after logging out of Salesforce: " + actualTitle1);
		soft.assertEquals(actualTitle1, expectedTitle1, "Login page is not displayed");
		
		boolean flag = objLogin.isRememberMeChecked();
		soft.assertTrue(flag, "Remember Me checkbox is not checked");
		
		String displayedUsername = objLogin.getDisplayedUsername();
		Assert.assertEquals(displayedUsername, username, "Displayed username is not correct");
		soft.assertAll();
		
		String methodName = new Object() {} .getClass().getEnclosingMethod().getName();
		captureScreenShot(methodName);
	}
	
	@Test(priority=3, enabled=true)
	public static void TC04A_forgotPasswordLink() throws IOException, InterruptedException {
		
		soft = new SoftAssert();
		objLogin = new LoginPage(driver);
		objLogin.clickForgotPwdLink();
		
		objFrgtPwd = new ForgotPasswordPage(driver);
		String expectedHeader = "Forgot Your Password";
		String actualHeader = objFrgtPwd.getForgotPwdHeaderText();
		soft.assertEquals(actualHeader, expectedHeader, "Header text is different from the expected one");
		
		String username = CommonUtilities.getPropertyValue("username");
		objFrgtPwd.enterEmail(username);
		objFrgtPwd.clickContinueButton();
		
		waitforPageToLoad();
		System.out.println("Page Title: "+ driver.getTitle());
		report.logTestInfo("Page Title: "+ driver.getTitle());
		String expectedHeader1 = "Check Your Email";
		objChkEmail = new CheckEmailPage(driver);
		String actualHeader1 = objChkEmail.getCheckEmailHeader();
		soft.assertEquals(actualHeader1, expectedHeader1, "Header text is different from the expected one");
		soft.assertAll();
		
		String methodName = new Object() {} .getClass().getEnclosingMethod().getName();
		captureScreenShot(methodName);
	}
	
	@Test(priority=4, enabled=true)
	public static void TC04B_loginFailedError() throws IOException {
	
		String methodName = new Object() {} .getClass().getEnclosingMethod().getName();
		objLogin = new LoginPage(driver);
		objLogin.setUsername("123");
		objLogin.setPassword("22131");
		objLogin.clickLoginButton();
		String expectedError = "Please check your username and password. If you still can't log in, contact your Salesforce administrator.";
		String actualError = objLogin.getLoginError();
		try {
		Assert.assertTrue(actualError.equalsIgnoreCase(expectedError), "Expected login error message is not displayed");
		}
		catch(Exception e) {
			report.logTestFailedWithException(methodName, e);
		}
		captureScreenShot(methodName);
	}
	
	@Test(priority=5, enabled=true)
	public static void TC05_validateUserMenuDropdown() throws IOException, InterruptedException {
		
		soft = new SoftAssert();
		objLogin = new LoginPage(driver);
		objHome = new HomePage(driver);
		String username = CommonUtilities.getPropertyValue("username");
		String password = CommonUtilities.getPropertyValue("password");
		objLogin.loginToSalesforce(username, password);
		waitforPageToLoad();
		
		String expectedTitle = "Home Page";
		Thread.sleep(3000);
		String actualTitle = driver.getTitle();
		System.out.println("Page redirected to after clicking on LogIn button: " + actualTitle);
		report.logTestInfo("Page redirected to after clicking on LogIn button: " + actualTitle);
		soft.assertTrue(actualTitle.contains(expectedTitle), "Home Page is not displayed");
		
		String expectedUserName = "Pooja Sanghvi";
		String userFullName = objHome.getHomePageDashboardUserName();
		System.out.println("Username appearing on the SFDC Home dashboard: " +userFullName);
		soft.assertEquals(userFullName, expectedUserName, "User's fullname on the dashboard does NOT match with the expected name");
		
		objHome.clickUserMenuDropdown();
		String [] userMenuArray = {"My Profile", "My Settings", "Developer Console", "Switch to Lightning Experience", "Logout"};
		List<String> expectedUserMenuList = new ArrayList<String>(Arrays.asList(userMenuArray));
		System.out.println("expected: " + expectedUserMenuList);
		List<String> actualUserMenuList = objHome.getUserMenuList();
		System.out.println("User Menu Dropdown List in Salesforce: " + actualUserMenuList);
		report.logTestInfo("User Menu Dropdown List in Salesforce: " + actualUserMenuList);
		soft.assertTrue(actualUserMenuList.containsAll(expectedUserMenuList), "User Menu dropdown list does NOT contain the expected options");
		soft.assertAll();
		
		String methodName = new Object() {} .getClass().getEnclosingMethod().getName();
		captureScreenShot(methodName);
	}
}
