package com.salesforceApp.utilities;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.salesforce.base.BaseTest;


public class GenerateReportsListener implements ITestListener {

	public static ExtentReports extent;
	public GenerateReports report=GenerateReports.getInstance();

	
	public void onStart(ITestContext context) {
		System.out.println("inside GenerateReportsListener onStart() method - creating the entire test report");
		report.startExtentReport();
	}

	public void onTestStart(ITestResult result) {
		
		System.out.println("inside GenerateReportsListener onTestStart() method creating individual testcase report");
		report.startSingleTestReport(result.getMethod().getMethodName());
	}

	public void onTestSuccess(ITestResult result) {
		System.out.println(" inside onTestSuccess() listener method");
		report.logTestPassed(result.getMethod().getMethodName() );	
	}

	public void onTestFailure(ITestResult result) {
	
		System.out.println(" inside onTestFailure() listener method");
		WebDriver driver = BaseTest.getDriverInstance();
		String screenshotPath = CommonUtilities.takescreenshot(driver, result.getMethod().getMethodName());
		report.logTestFailed(result.getMethod().getMethodName());
		//report.logTestFailedWithException(result.getMethod().getMethodName(), new Exception());
		try {
			report.attachScreenshot(screenshotPath);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}

	public void onTestSkipped(ITestResult result) {
		System.out.println(" inside onTestSkipped() listener method");
		report.logTestSkipped(result.getMethod().getMethodName());
	}

	public void onFinish(ITestContext context) {
		System.out.println("inside GenerateReportsListener onFinish() method - completing generation of HTML report ");
		report.endReport();
	}

}
