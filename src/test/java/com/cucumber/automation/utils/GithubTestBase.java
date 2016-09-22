package com.cucumber.automation.utils;

import com.tab.qa.framework.core.TestBase;
import com.tab.qa.framework.verify.Verify;

import org.apache.log4j.Logger;
//import org.openqa.selenium.support.ui.WebDriverWait; 

public class GithubTestBase extends TestBase{
	private static Logger logger = Logger.getLogger(GithubTestBase.class);	
	private static String baseURL = "";
	public static String browser = "";//Chrome, Firefox, IE	
//	public static String _driverPath = "C:\\Selenium\\Drivers\\"; //TODO: Use TestNG.XML;	

//	baseUrl=https://github.com/
//	browser=Chrome
//	user=anyzer
//	password=password
	
	public Github Github() {
		return new Github();
	}
	
	public static void initialGithubTest() {
		logger.info("Initial Github Test - GithubTestBase");	
		
		baseURL = getTestPropertyValue("baseUrl");
		browser = getTestPropertyValue("browser");
		setBaseUrl(baseURL);
		setBrowser(browser);
		
		
		logger.info("Base URL: " + baseURL);
		logger.info("Browser: " + browser);
		
		initialiseTest("Table", "1");	
		
		if(navigateToBaseURL()){
			Verify.verifyTrue(false, "Cannot navigate to base URL");
		}
		
		logger.info("Test initialised.");
	}
	
		
	public static boolean teardown() {
		logger.info("Tear Down - GithubTestBase");
		finaliseTest();
		return true;
	
	}
	
	
}
