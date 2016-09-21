package com.tab.qa.framework.core;

import org.testng.ITestContext;
import org.testng.log4testng.Logger;

import com.jayway.restassured.response.Response;
import com.tab.qa.framework.verify.CustomAssert;

public class TestBase extends SeleniumBase {

	private static Logger logger = Logger.getLogger(TestBase.class);
	
//	private static boolean log4jInitialised = false;
	private static boolean browserOpen = false;
//	private static boolean dbInitialised = false;
//	protected static Database db; 
	
	protected ITestContext myTestContext;
	
	private static Response response;
	public static boolean Continue;
	
	protected static void initialiseEnvironment() {
		// Initialise test environment according to the test record. 
	}
	
	protected static void initialiseTestEnvironment(String recordRowNumber){
		logger.info("Environment is initialised -> " + getBrowser() + ", Base url:" + getBaseUrl());
	}
	
	
	public static void initialiseTest(String table, String recordRowNumber) {
		
		initialiseTestEnvironment(recordRowNumber);
		
		logger.info("initialise Test  - TestBase");
		
		if(!isDriverInitialised()){
			try{
				initialiseDriver();
			}catch (Exception e) {
				logger.error("Exception occured while initialising driver!", e);
				e.printStackTrace();
			}
		}
		
//		navigateToBaseURL();
		logger.info("Test initialised.");
		
	}
	
	
	public static void finaliseTest() {
		logger.info("-> FinaliseTest() <Started>");
		
//		log("Take Screen Shot when after class");
//		createScreenshotAlways();
		
    	// Set suite mode to false
        setSuiteMode(false);
        
		//The ChromeDriver *requires* that you call close and then quit to not leak resources. 
		//However, the firefoxdriver crashes if you call close before calling quit with any time delay. 
        if (isDriverInitialised())
        {
        	logger.info("-> FinaliseTest() Quitting Driver...");
        	try {
        		finaliseDriver();
        	} catch(Exception e) {
        		
        	}
            browserOpen = false;
    		logger.info("Browser closed.");		
        }
//        logger.info("-> FinaliseTest() Closing Database connection...");
////		closeDatabaseConnection();
//		logger.info("Database connection closed.");	
		
		logger.info("-> FinaliseTest() <Completed>");
	}
	
	
	public static Boolean navigateToBaseURL() {
		logger.info(String.format("launchApp() called..."));
		
		if(getBaseUrl() == null) { CustomAssert.assertFalse(true, "Base URL is null"); }
		
		navigateTo(getBaseUrl());
		
		if(getDriver().getTitle().contains("Certificate Error")) {
			logger.info("Certificate Required");
			getDriver().get("javascript:document.getElementById('overridelink').click();");
		}
		
		maximize();
		browserOpen = true;		
		log("Application loaded successfully.");
		return browserOpen;
	}
	
	public static void closebrowser() {
		getDriver().quit();
		browserOpen = false;
		logger.info("Browser closed.");		
	}

	public static Response getResponse() {
		return response;
	}

	public static void setResponse(Response response) {
		TestBase.response = response;
	}
	
	
	
}
