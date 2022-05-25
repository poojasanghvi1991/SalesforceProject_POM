package com.salesforce.pages.home;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.salesforce.base.BasePage;

public class HomePage extends BasePage {

	
	@FindBy(id = "userNavButton") WebElement userMenuButton;
	@FindBy(id = "userNavLabel") WebElement userNameLabel;
	@FindBy(linkText = "Logout") WebElement logoutLink;
	@FindBy(xpath = "//div[@id='userNav-menuItems']//child::a") List<WebElement> userMenuList;
	
	public HomePage(WebDriver driver) {
		
		super(driver);
	}
	
	public void clickUserMenuDropdown() {
		waitforPageToLoad();
		singleClick(userMenuButton, "user menu dropdown");
	}
	
	public void clickLogoutLink() {
		singleClick(logoutLink, "Logout link");
	}
	
	public String getHomePageDashboardUserName() {
		waitUntilElementVisible(userNameLabel);
		String userFullName = fetchText(userNameLabel,"Username label");
		return userFullName;
	}
	
	public String getHomePageTitle() {
		String title = driver.getTitle();
		System.out.println("Page title: " + title);
		return title;
	}
	
	public List<String> getUserMenuList() {
		
		List<String> arrayList = new ArrayList<String>();
		for(WebElement menu : userMenuList) {
			String menuText = menu.getText();
			arrayList.add(menuText);
		}
		return arrayList;
	}
	
}
