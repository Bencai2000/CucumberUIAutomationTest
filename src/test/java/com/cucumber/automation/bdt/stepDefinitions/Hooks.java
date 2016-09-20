package com.cucumber.automation.bdt.stepDefinitions;

import org.apache.log4j.Logger;
import com.cucumber.automation.utils.GithubTestBase;
import cucumber.api.java.After;
import cucumber.api.java.Before;


public class Hooks extends GithubTestBase{
	
//	public GithubTestBase driverFactory = new GithubTestBase();
	private static Logger logger = Logger.getLogger(Hooks.class);
//	private static boolean close = false;
	
    @Before
    public void before() {
    	logger.info("Hook Before - Scenario Start");
    	
    	logger.info("Navigate to Base URL");
    	navigateTo(getBaseUrl());	
    }
    
    
    @After
    public void after() {
    	logger.info("Hook After");
    	
//    	createScreenshot();
//    	clearPopup();
//    	refreshPage();
    	
    	logger.info("Hook After - Scenario Done");
    }
	
	
}

